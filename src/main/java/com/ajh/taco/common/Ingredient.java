package com.ajh.taco.common;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor // Ensure a required arguments constructor in addition to the private no-arguments constructor below
@NoArgsConstructor(access=AccessLevel.PRIVATE, force=true) // JPA requires that entities have a no-arguments constructor
@Entity // Mark as JPA entity
public class Ingredient {
	@Id // Designate it as the property that will uniquely identify the entity in the database
	private final String id;
	private final String name;
	private final Type type;
	
	public static enum Type {
		WRAP, PROTEIN, VEGGIES, CHEESE, SAUCE
	}
}
