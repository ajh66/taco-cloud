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

import com.ajh.taco.common.Ingredient;

import lombok.Data;

@Data
@Entity
public class Taco { // Correspond to form fields in page design.html
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;

	private Date createdAt;

	@NotNull
	@Size(min=5, message="Name must be at least 5 characters long")
	private String name;

	@ManyToMany(targetEntity=Ingredient.class)
	@NotEmpty(message="You must choose at least 1 ingredient")
//	@Size(min=1, message="You must choose at least 1 ingredient")
	private List<Ingredient> ingredients; // Auto-generate table TACO_INGREDIENTS
	
	@PrePersist
	void createdAt() {
		this.createdAt = new Date();
	}
}
