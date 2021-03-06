package com.arnav.model.coupon;

import java.math.BigDecimal;
import java.util.Date;

import javax.validation.constraints.NotNull;

import com.arnav.model.address.Address;
import com.arnav.model.provider.Provider;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Coupon {
	
	@Id
	private String id;
	
	@NotNull(message="Coupon code should not be blank")
	private String couponCode;
	
	@NotEmpty(message="Coupon Number is required.")
	private Long couponNumber;

	@NotEmpty(message="Coupon Package ID is required.")
	private String couponPackageId;

	@NotEmpty
	private String providerId;

	@NotEmpty
	@DBRef
	private Provider provider;

	@Transient
	private Address address;

	@NotEmpty
	private String purchasedCouponId;


	@NotNull
	private BigDecimal price;

	private Integer availability;

	private Integer used;

	private Date collectionDate;

	@NotNull
	private Date startTime;

	@NotNull
	private Date endTime;

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

	public String getProviderId() {
		return providerId;
	}

	public void setProviderId(String providerId) {
		this.providerId = providerId;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public Integer getAvailability() {
		return availability;
	}

	public void setAvailability(Integer availability) {
		this.availability = availability;
	}

	public Integer getUsed() {
		return used;
	}

	public void setUsed(Integer used) {
		this.used = used;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public String getPurchasedCouponId() {
		return purchasedCouponId;
	}


	public void setPurchasedCouponId(String purchasedCouponId) {
		this.purchasedCouponId = purchasedCouponId;
	}

	public Provider getProvider() {
		return provider;
	}

	public void setProvider(Provider provider) {
		this.provider = provider;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public String getCouponPackageId() {
		return couponPackageId;
	}

	public void setCouponPackageId(String couponPackageId) {
		this.couponPackageId = couponPackageId;
	}

	@Override
	public String toString() {
		return "Coupon{" +
				"id='" + id + '\'' +
				", couponCode='" + couponCode + '\'' +
				", couponNumber=" + couponNumber +
				", couponPackageId='" + couponPackageId + '\'' +
				", providerId='" + providerId + '\'' +
				", provider=" + provider +
				", address=" + address +
				", purchasedCouponId='" + purchasedCouponId + '\'' +
				", price=" + price +
				", availability=" + availability +
				", used=" + used +
				", startTime=" + startTime +
				", endTime=" + endTime +
				'}';
	}

	public Date getCollectionDate() {
		return collectionDate;
	}

	public void setCollectionDate(Date collectionDate) {
		this.collectionDate = collectionDate;
	}
}
