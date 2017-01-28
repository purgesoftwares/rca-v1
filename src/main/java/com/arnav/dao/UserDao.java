package com.arnav.dao;

import java.security.Principal;
import java.util.Locale;

import com.arnav.model.customer.Customer;
import com.arnav.model.user.User;

public interface UserDao {
	
	User findByUserName(String username);

	User registerNewUserAccount(User user,Customer customer,Locale locale);

	void saveRegisteredUser(User user);
	
	User getActiveUser(Principal principal);

}
