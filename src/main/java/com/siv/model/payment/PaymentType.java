package com.siv.model.payment;

public enum PaymentType {
	
	PAYPAL("PAYPAL"),
	DEBIT_CARD("DEBIT_CARD");
	
	private final String name;
	
	PaymentType(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

}
