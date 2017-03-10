package com.arnav.model.openings;

public enum OpeningTime {
	MONDAY("Monday"),
	TUESDAY("Tuesday"),
	WEDNESDAY("Wednesday"),
	THURSDAY("Thursday"),
	FRIDAY("Friday"),
	SATURDAY("Saturday"),
	SUNDAY("Sunday");
	
	private final String name;
	
	OpeningTime(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
}
