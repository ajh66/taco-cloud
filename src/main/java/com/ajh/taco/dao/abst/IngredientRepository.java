package com.ajh.taco.dao.abst;

import org.springframework.data.repository.CrudRepository;

import com.ajh.taco.common.Ingredient;

public interface IngredientRepository extends CrudRepository<Ingredient, String> {
}
