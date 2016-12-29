package com.siv.dao;

import java.security.Principal;
import java.util.Locale;

import com.siv.model.customer.Customer;
import com.siv.model.user.User;

public interface UserDao {
	
	User findByUserName(String username);

	User registerNewUserAccount(User user,Customer customer,Locale locale);

	void saveRegisteredUser(User user);
	
	User getActiveUser(Principal principal);

}
