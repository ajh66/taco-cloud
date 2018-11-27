package com.ajh.taco.hateoas;

import java.util.Date;
import java.util.List;

import org.springframework.hateoas.ResourceSupport;

import com.ajh.taco.common.Ingredient;
import com.ajh.taco.domainobject.Taco;

public class TacoResource extends ResourceSupport {
	private String name;
	private Date createdAt;
	private List<Ingredient> ingredients;

	public TacoResource(Taco taco) {
		this.name = taco.getName();
		this.createdAt = taco.getCreatedAt();
		this.ingredients = taco.getIngredients();
	}

	public String getName() {
		return name;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public List<Ingredient> getIngredients() {
		return ingredients;
	}

}
