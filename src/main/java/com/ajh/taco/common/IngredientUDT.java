package com.ajh.taco.common;

import java.util.Objects;

import org.springframework.data.cassandra.core.mapping.UserDefinedType;

import com.ajh.taco.common.Ingredient.Type;

@UserDefinedType("ingredient")
public class IngredientUDT {
	private String name;
	private Ingredient.Type type;

	public IngredientUDT() {}

	public IngredientUDT(String name, Type type) {
		super();
		this.name = name;
		this.type = type;
	}

	@Override
	public int hashCode() {
		return Objects.hash(name, type);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof IngredientUDT)) {
			return false;
		}
		IngredientUDT other = (IngredientUDT) obj;
		return Objects.equals(name, other.name) && (type == other.type);
	}

	@Override
	public String toString() {
		return "IngredientUDT{name=" + name + ", type=" + type + "}";
	}

	public String getName() {
		return name;
	}

	public Ingredient.Type getType() {
		return type;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setType(Ingredient.Type type) {
		this.type = type;
	}
}
