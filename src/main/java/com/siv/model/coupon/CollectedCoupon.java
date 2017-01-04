package com.siv.model.coupon;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class CollectedCoupon {
	
	@Id
	private String id;
	
	private String providerId;
	
	private BigDecimal value;
	
	private String status;
	
	private Date startDate;
	
	private int numOfCoupons;

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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public BigDecimal getValue() {
		return value;
	}

	public void setValue(BigDecimal value) {
		this.value = value;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public int getNumOfCoupons() {
		return numOfCoupons;
	}

	public void setNumOfCoupons(int numOfCoupons) {
		this.numOfCoupons = numOfCoupons;
	}
	
	

}
