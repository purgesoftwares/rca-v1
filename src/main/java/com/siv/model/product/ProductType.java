package com.siv.model.product;

public enum ProductType {
	
	DRINK("DRINK"),
	DESERT("DESERT"),
	MAIN("MAIN");
	
	private final String name;
	
	ProductType(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

}
