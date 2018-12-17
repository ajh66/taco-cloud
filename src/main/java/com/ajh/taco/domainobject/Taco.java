package com.ajh.taco.domainobject;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import org.springframework.data.cassandra.core.cql.Ordering;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;
import org.springframework.data.rest.core.annotation.RestResource;

import com.ajh.taco.common.Ingredient;
import com.ajh.taco.common.IngredientUDT;
import com.datastax.driver.core.utils.UUIDs;

@Table("tacos")
@RestResource(rel="tacos", path="tacos")
public class Taco { // Correspond to form fields in page design.html
	@PrimaryKeyColumn(type=PrimaryKeyType.PARTITIONED)
	private UUID id = UUIDs.timeBased();

	@PrimaryKeyColumn(type=PrimaryKeyType.CLUSTERED, ordering=Ordering.DESCENDING)
	private Date createdAt = new Date();

	@NotNull
	@Size(min=5, message="Name must be at least 5 characters long")
	private String name;

	@Column("ingredients")
	@NotEmpty(message="You must choose at least 1 ingredient")
//	@Size(min=1, message="You must choose at least 1 ingredient")
	private List<IngredientUDT> ingredients; // Can’t use the Ingredient class as a user-defined type,
											 // because the @Table annotation has already mapped it as an entity
											 // for persistence in Cassandra.

	@Override
	public int hashCode() {
		return Objects.hash(createdAt, id, ingredients, name);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof Taco)) {
			return false;
		}
		Taco other = (Taco) obj;
		return Objects.equals(createdAt, other.createdAt) && Objects.equals(id, other.id)
				&& Objects.equals(ingredients, other.ingredients) && Objects.equals(name, other.name);
	}

	@Override
	public String toString() {
		return "Taco{id='" + id + "', createdAt='" + createdAt + "', name='" + name + "', ingredients=" + ingredients + "}";
	}

	public UUID getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public List<IngredientUDT> getIngredients() {
		return ingredients;
	}

	public Date getCreatedAt() {
		return createdAt;
	}
	
	public void setId(UUID id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setIngredients(List<IngredientUDT> ingredients) {
		this.ingredients = ingredients;
	}

}
