package com.siv.model.product;

public enum ProductType {
	
	EAT("EAT"),
	DRINK("DRINK"),
	DESERT("DESERT"),
	MAIN("MAIN"),
	LOVE("LOVE");
	
	private final String name;
	
	ProductType(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

}
