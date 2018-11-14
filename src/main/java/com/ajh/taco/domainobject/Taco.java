package com.ajh.taco.domainobject;

import java.util.List;

import lombok.Data;

@Data
public class Taco {
	private String name;
	private List<String> ingredients;
}
