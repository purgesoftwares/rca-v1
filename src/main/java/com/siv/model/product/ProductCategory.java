package com.siv.model.product;

public enum ProductCategory {
	
	EAT("EAT"),
	DRINK("DRINK"),
	LOVE("LOVE");
	
	private final String name;
	
	ProductCategory(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

}
