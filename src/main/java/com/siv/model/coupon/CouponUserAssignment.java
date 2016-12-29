package com.siv.model.coupon;

import java.math.BigDecimal;
import java.util.Date;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class CouponUserAssignment {
	
	@Id
	private String id;
	
	@NotBlank(message="Assign date is required")
	private Date assignDate;
	
	private Date endDate;
	
	@NotEmpty
	private int numOfCoupons;
	
	private BigDecimal value;
	
	@Email
	@NotNull(message="Username is required.")
	private String username;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Date getAssignDate() {
		return assignDate;
	}

	public void setAssignDate(Date assignDate) {
		this.assignDate = assignDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public int getNumOfCoupons() {
		return numOfCoupons;
	}

	public void setNumOfCoupons(int numOfCoupons) {
		this.numOfCoupons = numOfCoupons;
	}

	public BigDecimal getValue() {
		return value;
	}

	public void setValue(BigDecimal value) {
		this.value = value;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	

}
