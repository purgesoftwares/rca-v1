package com.siv.model.provider;

import java.util.Date;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Document
public class Provider {
	
	@Id
	private String id;
	
	@NotBlank(message="Provier Name is required.")
	private String providerName;
	
	@NotBlank(message="Address Id is required.")
	private String addressId;
	
	@NotBlank(message="Contact Name is required.")
	private String contactName;	
	
	@NotBlank(message="Main Email is required.")
	private String mainEmail;
	
	private String secondaryEmail;
	
	@NotBlank(message="User Id is required.")
	private String userId;
	
	private Date createDate;
	
	private Date lastUpdate;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getProvider_name() {
		return providerName;
	}

	public void setProvider_name(String provider_name) {
		this.providerName = provider_name;
	}

	public String getAddressId() {
		return addressId;
	}

	public void setAddressId(String addressId) {
		this.addressId = addressId;
	}

	public String getContactName() {
		return contactName;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	public String getMainEmail() {
		return mainEmail;
	}

	public void setMainEmail(String mainEmail) {
		this.mainEmail = mainEmail;
	}

	public String getSecondaryEmail() {
		return secondaryEmail;
	}

	public void setSecondaryEmail(String secondaryEmail) {
		this.secondaryEmail = secondaryEmail;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
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
