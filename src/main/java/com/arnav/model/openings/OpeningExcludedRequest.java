package com.arnav.model.openings;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotNull;

public class OpeningExcludedRequest {

	@NotNull
	private OpeningTime day;

	@NotNull
	private String openingTime;

	@NotNull
	private String endingTime;

	private List<ExcludedTime> excludedTimeList	= new ArrayList<ExcludedTime>();

	public OpeningTime getDay() {
		return day;
	}

	public void setDay(OpeningTime day) {
		this.day = day;
	}

	public String getOpeningTime() {
		return openingTime;
	}

	public void setOpeningTime(String openingTime) {
		this.openingTime = openingTime;
	}

	public String getEndingTime() {
		return endingTime;
	}

	public void setEndingTime(String endingTime) {
		this.endingTime = endingTime;
	}

	public List<ExcludedTime> getExcludedTimeList() {
		return excludedTimeList;
	}

	public void setExcludedTimeList(List<ExcludedTime> excludedTimeList) {
		this.excludedTimeList = excludedTimeList;
	}
}
