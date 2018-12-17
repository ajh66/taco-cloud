package com.ajh.taco.dao.abst;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import com.ajh.taco.common.Ingredient;

public interface IngredientRepository extends ReactiveCrudRepository<Ingredient, String> {
}
