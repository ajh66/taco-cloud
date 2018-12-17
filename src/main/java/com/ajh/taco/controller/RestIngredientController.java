package com.ajh.taco.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ajh.taco.common.Ingredient;
import com.ajh.taco.dao.abst.IngredientRepository;

import reactor.core.publisher.Flux;

@RestController
@RequestMapping(path="/ingredient", produces="application/json")
@CrossOrigin(origins="*") // Allow cross origin requests
public class RestIngredientController {
	@Autowired
	private IngredientRepository repo;

	@GetMapping
	public Flux<Ingredient> allIngredients() {
		return repo.findAll();
	}
}
