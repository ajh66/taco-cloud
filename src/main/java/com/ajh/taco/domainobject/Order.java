package com.ajh.taco.domainobject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.CreditCardNumber;

@Entity
@Table(name="Taco_Order") // JPA would default to persist entities to a table named "Order", but "order" is a reserved word in SQL
public class Order implements Serializable { // Correspond to form fields in page orderForm.html
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;

	private Date placedAt;

	@NotBlank(message="Name is required")
	private String name;
	
	@NotBlank(message="Street is required")
	private String street;
	
	@NotBlank(message="City is required")
	private String city;
	
	@NotBlank(message="State is required")
	private String state;
	
	@NotBlank(message="Zip code is required")
	private String zip;
	
	@CreditCardNumber(message="Not a valid credit card number")
	private String ccNumber;
	
	@Pattern(regexp="^(0[1-9]|1[0-2])([\\/])([1-9][0-9])$",
			 message="Must be formatted MM/YY")
	private String ccExpiration;
	
	@Digits(integer=3, fraction=0, message="Invalid CVV")
	private String ccCVV;

	@ManyToMany(targetEntity=Taco.class)
	private List<Taco> tacos = new ArrayList<>(); // Auto-generate table TACO_ORDER_TACOS
	
//	@ManyToOne
//	private TacoUser user;

	public Order() {
		// TODO
	}

	public Order(
			@NotBlank(message = "Name is required") String name,
			@NotBlank(message = "Street is required") String street,
			@NotBlank(message = "City is required") String city, @NotBlank(message = "State is required") String state,
			@NotBlank(message = "Zip code is required") String zip,
			@CreditCardNumber(message = "Not a valid credit card number") String ccNumber,
			@Pattern(regexp = "^(0[1-9]|1[0-2])([\\/])([1-9][0-9])$", message = "Must be formatted MM/YY") String ccExpiration,
			@Digits(integer = 3, fraction = 0, message = "Invalid CVV") String ccCVV) {
		super();
		this.name = name;
		this.street = street;
		this.city = city;
		this.state = state;
		this.zip = zip;
		this.ccNumber = ccNumber;
		this.ccExpiration = ccExpiration;
		this.ccCVV = ccCVV;
	}

	@Override
	public String toString() {
		return "Order{id='" + id + "', placedAt='" + placedAt + "', name='" + name + "', street='" + street + "', city='" + city
				+ "', state='" + state + "', zip='" + zip + "', ccNumber='" + ccNumber + "', ccExpiration='" + ccExpiration
				+ "', ccCVV='" + ccCVV + "', tacos=" + tacos + "}";
	}

	public void addDesign(Taco design) {
		tacos.add(design);
	}
	
	@PrePersist
	void placedAt() {
		this.placedAt = new Date();
	}

	public String getName() {
		return name;
	}

	public String getStreet() {
		return street;
	}

	public String getCity() {
		return city;
	}

	public String getState() {
		return state;
	}

	public String getZip() {
		return zip;
	}

	public String getCcNumber() {
		return ccNumber;
	}

	public String getCcExpiration() {
		return ccExpiration;
	}

	public String getCcCVV() {
		return ccCVV;
	}

	public List<Taco> getTacos() {
		return tacos;
	}

	public Date getPlacedAt() {
		return placedAt;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public void setState(String state) {
		this.state = state;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	public void setCcNumber(String ccNumber) {
		this.ccNumber = ccNumber;
	}

	public void setCcExpiration(String ccExpiration) {
		this.ccExpiration = ccExpiration;
	}

	public void setCcCVV(String ccCVV) {
		this.ccCVV = ccCVV;
	}
}
