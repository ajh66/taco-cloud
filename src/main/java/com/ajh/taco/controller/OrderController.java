package com.ajh.taco.controller;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import com.ajh.taco.dao.abst.OrderRepository;
import com.ajh.taco.domainobject.Order;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/orders")
@SessionAttributes("order")
public class OrderController {
	private OrderRepository orderRepo;

	public OrderController(OrderRepository orderRepo) {
		this.orderRepo = orderRepo;
	}

	@GetMapping("/current")
	public String orderForm(Model model) {
//		model.addAttribute("order", new Order() {{
//			// Inline initialization block
//			setName("Your name please");
//		}});
		return "orderForm";
	}

	@GetMapping("/{id}")
	public String orderFormById(@PathVariable("id") int id, Model model) {
		model.addAttribute("order", new Order() {{
			// Inline initialization block
			setName("Andy");
			setStreet("Zhongshan Road");
			setCity("Nanjing");
			setState("Jiangsu");
			setZip("210000");
		}});
		return "orderForm";
	}

	@PostMapping
	public String processOrder(@Valid Order order, Errors errors, SessionStatus sessionStatus) {
		log.info("Order submitted: " + order);
		
		if (errors.hasErrors()) {
//			log.info("Error: " + errors);
			return "orderForm";
		}

		Order saved = orderRepo.save(order);
		log.info("Order saved: " + saved);

		sessionStatus.setComplete();

		return "redirect:/";
	}
}
