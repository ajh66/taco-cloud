package com.ajh.taco.domainobject;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class Taco { // Correspond to form fields in page design.html
	private Long id;
	private Date createdAt;

	@NotNull
	@Size(min=5, message="Name must be at least 5 characters long")
	private String name;

	@NotEmpty(message="You must choose at least 1 ingredient")
//	@Size(min=1, message="You must choose at least 1 ingredient")
	private List<String> ingredients;
}
