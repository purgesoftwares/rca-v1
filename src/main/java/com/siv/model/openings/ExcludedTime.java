package com.siv.model.openings;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class ExcludedTime {
	
	@Id
	private String id;
	
	private String openingId;
	
	private String startExcludedTime;
	
	private String endExcludedTime;
	
	private String label;

	public String getOpeningId() {
		return openingId;
	}

	public void setOpeningId(String openingId) {
		this.openingId = openingId;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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
