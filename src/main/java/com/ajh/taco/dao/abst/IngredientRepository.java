package com.ajh.taco.dao.abst;

import com.ajh.taco.common.Ingredient;

public interface IngredientRepository {
	Iterable<Ingredient> findAll();
	Ingredient findOne(String id);
	Ingredient findById(String id);
	Ingredient save(Ingredient ingredient);
}
