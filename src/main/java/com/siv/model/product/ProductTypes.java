package com.siv.model.product;

import org.springframework.data.annotation.Id;

public class ProductTypes {
	
	@Id
	private String id;
	
	private String drink;
	
	private String desert;
	
	private String main;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDrink() {
		return drink;
	}

	public void setDrink(String drink) {
		this.drink = drink;
	}

	public String getDesert() {
		return desert;
	}

	public void setDesert(String desert) {
		this.desert = desert;
	}

	public String getMain() {
		return main;
	}

	public void setMain(String main) {
		this.main = main;
	}
	

}
