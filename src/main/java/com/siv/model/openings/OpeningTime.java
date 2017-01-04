package com.siv.model.openings;

public enum OpeningTime {
	MONDAY("MONDAY"),
	TUESDAY("TUESDAY"),
	WEDNESDAY("WEDNESDAY"),
	THURSDAY("THURSDAY"),
	FRIDAY("FRIDAY"),
	SATURDAY("SATURDAY"),
	SUNDAY("SUNDAY");
	
	private final String name;
	
	OpeningTime(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
}
