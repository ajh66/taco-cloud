package com.ajh.taco.common;

import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

@Table("ingredients")
public class Ingredient {
	@PrimaryKey
	private String id;
	private String name;
	private Type type;
	
	public static enum Type {
		WRAP, PROTEIN, VEGGIES, CHEESE, SAUCE
	}

	// JPA requires that entities have a no-arguments constructor
	@SuppressWarnings("unused")
	private Ingredient() {
	}

	public Ingredient(String id, String name, Type type) {
		this.id = id;
		this.name = name;
		this.type = type;
	}

	@Override
	public String toString() {
		return "Ingredient{id='" + id + "', name='" + name + "', type='" + type + "'}";
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public Type getType() {
		return type;
	}

}
