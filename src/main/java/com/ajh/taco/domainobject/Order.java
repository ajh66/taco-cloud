package com.ajh.taco.domainobject;

import lombok.Data;

@Data
public class Order { // Correspond to form fields in page orderForm.html
	private String name;
	private String street;
	private String city;
	private String state;
	private String zip;
	private String ccNumber;
	private String ccExpiration;
	private String ccCVV;
}
