package com.siv.model.coupon;

import java.util.Date;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Coupon {
	
	@Id
	private String id;
	
	@NotNull(message="Coupon code should not be blank")
	private String couponCode;
	
	@NotEmpty(message="Coupon Number is required.")
	private Long couponNumber;
	
	@NotNull
	private Date createDate;
	
	@NotNull
	private Date lastUpdate;
	
	public Coupon(){
	    
	}
  
	public Coupon (String id, String couponCode,Long couLong){
		this.id = id;
		this.couponCode = couponCode;
		this.couponNumber = couLong;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCouponCode() {
		return couponCode;
	}

	public void setCouponCode(String couponCode) {
		this.couponCode = couponCode;
	}

	public Long getCouponNumber() {
		return couponNumber;
	}

	public void setCouponNumber(Long couponNumber) {
		this.couponNumber = couponNumber;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getLastUpdate() {
		return lastUpdate;
	}

	public void setLastUpdate(Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}


}
