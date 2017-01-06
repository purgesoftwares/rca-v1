package com.siv.model.openings;

import java.util.Date;

import javax.validation.constraints.NotNull;

public class OpeningExcludedRequest {
	
	@NotNull
	private String startExcludedTime;
	
	@NotNull
	private String endExcludedTime;
	
	private String lable;
	
	@NotNull
	private OpeningTime day;
	
	@NotNull
	private Date openingTime;
	
	@NotNull
	private Date endingTime;

	public String getLable() {
		return lable;
	}

	public void setLable(String lable) {
		this.lable = lable;
	}

	public OpeningTime getDay() {
		return day;
	}

	public void setDay(OpeningTime day) {
		this.day = day;
	}

	public Date getOpeningTime() {
		return openingTime;
	}

	public void setOpeningTime(Date openingTime) {
		this.openingTime = openingTime;
	}

	public Date getEndingTime() {
		return endingTime;
	}

	public void setEndingTime(Date endingTime) {
		this.endingTime = endingTime;
	}

	public String getStartExcludedTime() {
		return startExcludedTime;
	}

	public void setStartExcludedTime(String startExcludedTime) {
		this.startExcludedTime = startExcludedTime;
	}

	public String getEndExcludedTime() {
		return endExcludedTime;
	}

	public void setEndExcludedTime(String endExcludedTime) {
		this.endExcludedTime = endExcludedTime;
	}
	
	
	
}
