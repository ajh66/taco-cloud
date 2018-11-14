package com.ajh.taco.domainobject;

import java.util.List;

import lombok.Data;

@Data
public class Taco { // Correspond to form fields in page design.html
	private String name;
	private List<String> ingredients;
}
