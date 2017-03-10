package com.arnav.model.coupon;

import java.math.BigDecimal;

public class CollectedCouponResponse {
	
	private String date;
	
	private BigDecimal amount;
	
	private Integer numOfCoupons;

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public Integer getNumOfCoupons() {
		return numOfCoupons;
	}

	public void setNumOfCoupons(Integer numOfCoupons) {
		this.numOfCoupons = numOfCoupons;
	}
	

}
