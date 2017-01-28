package com.arnav.dao;

import java.security.Principal;
import java.util.Date;
import java.util.Locale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
import com.arnav.model.customer.Customer;
import com.arnav.model.user.User;
import com.arnav.repository.customer.CustomerRepository;
import com.arnav.repository.user.UserRepository;

@Repository
public class UserDaoImpl implements UserDao {
	
	@Autowired
	private UserRepository ur;
	
	@Autowired
    private CustomerRepository cr;
	
	@Autowired
    private PasswordEncoder passwordEncoder;	

	public User findByUserName(String username) {
		User user = ur.findByUsername(username);
		return user;
	}

	public User registerNewUserAccount(User accountDto, Customer customerDto,
			Locale locale) {
		  	
			customerDto = registerCustomerAccount(customerDto);
	        final User user = new User();

	        user.setPassword(passwordEncoder.encode(accountDto.getPassword()));
	        user.setUsername(accountDto.getUsername());
			user.setRole("SuperAdmin");
			user.setEnabled(true);
			user.setCreateDate(new Date());
			user.setLastUpdate(new Date());
			user.setCustomerId(customerDto.getId());
			
	        return ur.save(user);
	}

	private Customer registerCustomerAccount(Customer customerDto) {
		final Customer customer = new Customer();
		customer.setFirstName(customerDto.getFirstName());
		customer.setLastName(customerDto.getLastName());
		
		return cr.save(customer);
		
	}
	
	public void saveRegisteredUser(User user) {
		ur.save(user);
		
	}

	public User getActiveUser(Principal principal) {
		return ur.findByUsername(principal.getName());
	}

}
