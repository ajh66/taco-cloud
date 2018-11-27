package com.ajh.taco.hateoas;

import org.springframework.hateoas.ResourceSupport;

import com.ajh.taco.common.Ingredient;
import com.ajh.taco.common.Ingredient.Type;

public class IngredientResource extends ResourceSupport {
	private String name;
	private Type type;
	
	public IngredientResource(Ingredient ingredient) {
		this.name = ingredient.getName();
		this.type = ingredient.getType();
	}
	
	public String getName() {
		return name;
	}
	
	public Type getType() {
		return type;
	}

}
