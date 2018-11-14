package com.ajh.taco;

import java.util.List;

import lombok.Data;

@Data
public class Taco {
	private String name;
	private List<String> ingredients;
}
