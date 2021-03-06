package com.ajh.taco.domainobject;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotNull;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.PrePersist;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import org.springframework.data.rest.core.annotation.RestResource;

import com.ajh.taco.common.Ingredient;

@Entity
@RestResource(rel="tacos", path="tacos")
public class Taco { // Correspond to form fields in page design.html
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO) // Rely on the DB to automatically generate the ID value
	private Long id;

	private Date createdAt;

	@NotNull
	@Size(min=5, message="Name must be at least 5 characters long")
	private String name;

	@ManyToMany(targetEntity=Ingredient.class) // Declare relationship between Taco and Ingredient
	@NotEmpty(message="You must choose at least 1 ingredient")
//	@Size(min=1, message="You must choose at least 1 ingredient")
	private List<Ingredient> ingredients; // Auto-generate table TACO_INGREDIENTS

	@Override
	public String toString() {
		return "Taco{id='" + id + "', createdAt='" + createdAt + "', name='" + name + "', ingredients=" + ingredients + "}";
	}

	@PrePersist // Set property createdAt to the current date & time before Taco is persisted
	void createdAt() {
		this.createdAt = new Date();
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public List<Ingredient> getIngredients() {
		return ingredients;
	}

	public Date getCreatedAt() {
		return createdAt;
	}
	public void setName(String name) {
		this.name = name;
	}

	public void setIngredients(List<Ingredient> ingredients) {
		this.ingredients = ingredients;
	}

}
