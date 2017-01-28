package com.arnav.model.user;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Email;
import org.springframework.data.mongodb.core.index.Indexed;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class UserLogin {
	
	@Email
	@NotNull(message = "Username should not be blank.")
	@Indexed(unique=true)
	private String username;
	
	@NotNull(message = "Password should not be blank.")
	private String password;
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
