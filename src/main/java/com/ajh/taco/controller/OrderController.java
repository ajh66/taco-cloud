package com.ajh.taco.controller;

import javax.validation.Valid;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
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

@Controller
@RequestMapping("/orders")
@SessionAttributes("order")
public class OrderController {
	private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(OrderController.class);

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

		User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (user != null) {
			Order order = (Order)model.asMap().get("order"); // How to avoid using this silly type cast?
			if (order != null) {
				order.setName(user.getUsername());
			} else {
				return "redirect:/design";
			}
		}

		return "orderForm";
	}

	@GetMapping("/{id}")
	public String orderFormById(@PathVariable("id") int id, Model model) {
		model.addAttribute("order", new Order() {
			/**
			 * Oops: Eclipse complains about no auto-generated serialVersionUID without definition showed below.
			 */
			private static final long serialVersionUID = 1L;
			{
				// Inline initialization block
				setName("Andy");
				setStreet("Zhongshan Road");
				setCity("Nanjing");
				setState("Jiangsu");
				setZip("210000");
			}
		});
		return "orderForm";
	}

	@GetMapping("/all")
	public String allOrders(Model model) {
		model.addAttribute("orders", orderRepo.findAll());
		return "orderList";
	}

	@PostMapping
	public String processOrder(@Valid Order order, Errors errors, SessionStatus sessionStatus, @AuthenticationPrincipal User user) {
		log.info("Order submitted: " + order);
		
		if (errors.hasErrors()) {
//			log.info("Error: " + errors);
			return "orderForm";
		}

//		order.setUser(user);
		Order saved = orderRepo.save(order);
		log.info("Order saved: " + saved);

		sessionStatus.setComplete();

		return "redirect:/";
	}
}
