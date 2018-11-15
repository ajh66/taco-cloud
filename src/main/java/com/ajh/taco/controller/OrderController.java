package com.ajh.taco.controller;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ajh.taco.domainobject.Order;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/orders")
public class OrderController {
	@GetMapping("/current")
	public String orderForm(Model model) {
		Order order = new Order();
		order.setName("Your name please");
		model.addAttribute("order", order);
		return "orderForm";
	}
	
	@PostMapping
	public String processOrder(@Valid Order order, Errors errors) {
		log.info("Order submitted: " + order);
		
		if (errors.hasErrors()) {
//			log.info("Error: " + errors);
			return "orderForm";
		}
		
		return "redirect:/";
	}
}
