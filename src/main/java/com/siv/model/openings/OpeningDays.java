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

	@Override
	public String toString() {
		return "OpeningDays{" +
				"id='" + id + '\'' +
				", providerId='" + providerId + '\'' +
				", day=" + day +
				", days=" + days +
				", openingTime=" + openingTime +
				", endingTime=" + endingTime +
				", status=" + status +
				'}';
	}

	private String openingTime;
	
	private String endingTime;

	private Integer status;

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

	public String getOpeningTime() {
		return openingTime;
	}

	public void setOpeningTime(String openingTime) {
		this.openingTime = openingTime;
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

	public String getEndingTime() {
		return endingTime;
	}

	public void setEndingTime(String endingTime) {
		this.endingTime = endingTime;
	}


	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
}
