package com.ajh.taco.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.ajh.taco.common.Ingredient;
import com.ajh.taco.common.Ingredient.Type;
import com.ajh.taco.dao.abst.IngredientRepository;
import com.ajh.taco.dao.abst.TacoRepository;
import com.ajh.taco.domainobject.Order;
import com.ajh.taco.domainobject.Taco;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/design")
@SessionAttributes("order") // An order attribute is kept in session and available across multiple requests.
public class DesignTacoController {
	private final IngredientRepository ingredientRepo;
	private final TacoRepository designRepo;

	@Autowired
	public DesignTacoController(IngredientRepository ingredientRepo, TacoRepository designRepo) {
		this.ingredientRepo = ingredientRepo;
		this.designRepo = designRepo;
	}

	@ModelAttribute(name="order")
	public Order order() {
		return new Order();
	}

	@ModelAttribute(name="taco")
	public Taco taco() {
		return new Taco();
	}

	@GetMapping
	public String showDesignForm(Model model) {
		List<Ingredient> ingredients = new ArrayList<>();
		this.ingredientRepo.findAll().forEach(i -> ingredients.add(i));

		Type[] types = Ingredient.Type.values();
		for (Type type : types) {
			model.addAttribute(type.toString().toLowerCase(), filterByType(ingredients, type));
		}

		model.addAttribute("taco", new Taco()); // Object road show: controller(GET) -> view(th:object="${taco}") -> controller(POST)
		return "design";
	}

	@PostMapping
	public String processDesign(@Valid Taco taco, Errors errors, @ModelAttribute Order order) {
		log.info("Processing design: " + taco);

		if (errors.hasErrors()) {
			log.info("Error: " + errors);
			return "design";
		}
		Taco saved = designRepo.save(taco);
		order.addDesign(saved);
		log.info("Order list: " + order);

		return "redirect:/orders/current";
	}

	private List<Ingredient> filterByType(List<Ingredient> ingredients, Type type) {
		return ingredients.stream().filter(x -> x.getType().equals(type)).collect(Collectors.toList());
	}
}
