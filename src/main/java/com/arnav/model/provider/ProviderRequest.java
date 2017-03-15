package com.arnav.model.provider;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;

public class ProviderRequest {

	@NotBlank(message="Provier Name is required.")
	private String providerName;
	
	@NotBlank(message="Contact Name is required.")
	private String contactName;	
	
	@NotBlank(message="Main Email is required.")
	private String mainEmail;
	
	private String secondaryEmail;
	
	@NotBlank
	private String password;
	
	@NotBlank
	private String confirmPassword;
	
	@NotBlank
	private String address;

	private GeoJsonPoint location;
	
	@NotBlank
	private String city;
	
	@NotBlank
	private String country;

	public String getProviderName() {
		return providerName;
	}

	public void setProviderName(String providerName) {
		this.providerName = providerName;
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public GeoJsonPoint getLocation() {
		return location;
	}

	public void setLocation(GeoJsonPoint location) {
		this.location = location;
	}

	@Override
	public String toString() {
		return "ProviderRequest{" +
				"providerName='" + providerName + '\'' +
				", contactName='" + contactName + '\'' +
				", mainEmail='" + mainEmail + '\'' +
				", secondaryEmail='" + secondaryEmail + '\'' +
				", password='" + password + '\'' +
				", confirmPassword='" + confirmPassword + '\'' +
				", address='" + address + '\'' +
				", city='" + city + '\'' +
				", country='" + country + '\'' +
				'}';
	}
}
