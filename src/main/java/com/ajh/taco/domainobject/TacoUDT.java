package com.ajh.taco.domainobject;

import java.util.List;

import org.springframework.data.cassandra.core.mapping.UserDefinedType;

import com.ajh.taco.common.IngredientUDT;

@UserDefinedType("taco")
public class TacoUDT {
	private String name;
	private List<IngredientUDT> ingredients;

	public TacoUDT(String name, List<IngredientUDT> ingredients) {
		super();
		this.name = name;
		this.ingredients = ingredients;
	}

	public String getName() {
		return name;
	}

	public List<IngredientUDT> getIngredients() {
		return ingredients;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setIngredients(List<IngredientUDT> ingredients) {
		this.ingredients = ingredients;
	}
}
