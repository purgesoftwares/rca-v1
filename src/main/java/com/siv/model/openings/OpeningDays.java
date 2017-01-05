package com.siv.model.openings;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class OpeningDays {
	
	@Id
	private String id;
	
	@NotEmpty
	private String providerId;
	
	private OpeningTime day;
	
	@NotEmpty
	@Transient
	private List<OpeningTime> days = new ArrayList<OpeningTime>();
	
	private Date openingTime;
	
	private Date endTime;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getProviderId() {
		return providerId;
	}

	public void setProviderId(String providerId) {
		this.providerId = providerId;
	}

	public Date getOpeningTime() {
		return openingTime;
	}

	public void setOpeningTime(Date openingTime) {
		this.openingTime = openingTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public List<OpeningTime> getDays() {
		return days;
	}

	public void setDays(List<OpeningTime> days) {
		this.days = days;
	}

	public OpeningTime getDay() {
		return day;
	}

	public void setDay(OpeningTime day) {
		this.day = day;
	}
	
	

}
