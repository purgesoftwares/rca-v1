package com.arnav.model.rate.review;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import com.arnav.model.customer.Customer;

@Document
public class RateReview {
	
	@Id
	private String id;

	private String providerId;
	
	private Integer rank;
	
	private String feedback;
	
	private String uploadFileId;
	
	private String pictureDescription;
	
	private String couponPackageId;
	
	private String customerId;
	
	@Indexed(unique=true)
	private Customer customer;
	
	private String status;
	
	private Date createDate;
	
	private Date lastUpdate;

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

	public Integer getRank() {
		return rank;
	}

	public void setRank(Integer rank) {
		this.rank = rank;
	}

	public String getFeedback() {
		return feedback;
	}

	public void setFeedback(String feedback) {
		this.feedback = feedback;
	}

	public String getPictureDescription() {
		return pictureDescription;
	}

	public void setPictureDescription(String pictureDescription) {
		this.pictureDescription = pictureDescription;
	}

	public String getCouponPackageId() {
		return couponPackageId;
	}

	public void setCouponPackageId(String couponPackageId) {
		this.couponPackageId = couponPackageId;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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

	public String getUploadFileId() {
		return uploadFileId;
	}

	public void setUploadFileId(String uploadFileId) {
		this.uploadFileId = uploadFileId;
	}

	
}
