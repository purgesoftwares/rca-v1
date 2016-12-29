package com.siv.model.user;

import java.util.Date;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Document
public class CorporateUser {

	@Id
	private String id;
	
	@NotNull(message="Name should not be blank")
	@Email
	private String name;
	
	@NotNull(message="Contact Name should not be blank")
	private String contactName;
	
	private String userId;
	
	@JsonIgnore
	@NotEmpty(message="Password should not be blank")
	private String adminPassword;
	
	@Transient
	private String confirmAdminPassword;
	
	private Date createDate;
	
	private Date lastUpdate;
	
	@DBRef
	private User user;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getContactName() {
		return contactName;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getAdminPassword() {
		return adminPassword;
	}

	public void setAdminPassword(String adminPassword) {
		this.adminPassword = adminPassword;
	}

	public String getConfirmAdminPassword() {
		return confirmAdminPassword;
	}

	public void setConfirmAdminPassword(String confirmAdminPassword) {
		this.confirmAdminPassword = confirmAdminPassword;
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

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
}
