package com.ajh.taco.common;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity // Mark as JPA entity
public class Ingredient {
	@Id // Designate it as the property that will uniquely identify the entity in the database
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
