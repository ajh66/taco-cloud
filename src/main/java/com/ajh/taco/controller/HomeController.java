package com.ajh.taco.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
	@GetMapping("/")
	public String home() {
		return "home"; // This value is interpreted as the logical name of a view.
					   // How that view is implemented depends on a few factors,
					   // but because Thymeleaf is in your classpath, you can define that template with Thymeleaf.
	}
}
