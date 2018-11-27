package com.ajh.taco.hateoas;

import org.springframework.hateoas.mvc.ResourceAssemblerSupport;

import com.ajh.taco.common.Ingredient;
import com.ajh.taco.controller.RestIngredientController;

public class IngredientResourceAssembler extends ResourceAssemblerSupport<Ingredient, IngredientResource> {

	public IngredientResourceAssembler() {
		super(RestIngredientController.class, IngredientResource.class);
	}

	@Override
	public IngredientResource toResource(Ingredient entity) {
		return createResourceWithId(entity.getId(), entity);
	}

	// This method would be optional if IngredientResource had a default constructor. In this case, however, it doesn't have.
	@Override
	protected IngredientResource instantiateResource(Ingredient entity) {
		return new IngredientResource(entity);
	}
}
