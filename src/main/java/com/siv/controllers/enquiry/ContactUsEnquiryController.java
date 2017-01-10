package com.siv.controllers.enquiry;

import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.siv.exceptions.AllPropertyRequiredException;
import com.siv.exceptions.UserNotFoundException;
import com.siv.exceptions.UsernameIsNotAnEmailException;
import com.siv.model.enquiry.ContactUsEnquiry;
import com.siv.model.user.User;
import com.siv.repository.enquiry.ContactUsEnquiryRepository;
import com.siv.repository.user.UserRepository;

@Path("/secured/contact-us")
public class ContactUsEnquiryController {
	
	@Autowired
	private ContactUsEnquiryRepository enquiryRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	
	@POST
	@Produces("application/json")
	public ContactUsEnquiry create(ContactUsEnquiry enquiry) throws UsernameIsNotAnEmailException{
		
		if(enquiry.getContactName() == null || enquiry.getUserEmail() == null || enquiry.getMessage() == null){
			throw new AllPropertyRequiredException("Contact name ,email and message is required.");
		}
		
		boolean isValid = checkStringIsEmamil(enquiry.getUserEmail());
		
		if(!isValid ){
			throw new UsernameIsNotAnEmailException("Please input correct email type.");
		}
		
		User user = userRepository.findByUsername(enquiry.getUserEmail());

		if(user == null) {
			throw new UserNotFoundException("This email is not exists");
		}
		enquiry.setCreateDate(new Date());
		enquiry.setLastUpdate(new Date());
		return enquiryRepository.save(enquiry);		
	}
	
	private boolean checkStringIsEmamil(String username) {
		Pattern p = Pattern.compile(".+@.+\\.[a-z]+");
		Matcher m = p.matcher(username);
		boolean matchFound = m.matches();
		if (matchFound) {
		    return true;
		}
		return false;
	}
	
	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public ContactUsEnquiry findOne(@PathParam(value="id")String id){
		return enquiryRepository.findOne(id);
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Page<ContactUsEnquiry> findAll(Pageable pageble){
		return enquiryRepository.findAll(pageble);
	}
	
	@PUT
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public ContactUsEnquiry update(@PathParam(value="id")String id, ContactUsEnquiry enquiry){
		ContactUsEnquiry preEnquiry = enquiryRepository.findOne(id);
		enquiry.setId(preEnquiry.getId());
		enquiry.setCreateDate(preEnquiry.getCreateDate());
		enquiry.setLastUpdate(new Date());
		
		if(enquiry.getContactName() == null){
			enquiry.setContactName(preEnquiry.getContactName());
		} if(enquiry.getMessage() == null) {
			enquiry.setMessage(preEnquiry.getMessage());
		} if(enquiry.getStatus() == null) {
			enquiry.setStatus(preEnquiry.getStatus());
		} if(enquiry.getUserEmail() == null){
			enquiry.setUserEmail(preEnquiry.getUserEmail());
		}
		return enquiryRepository.save(enquiry);
	}
	
	@DELETE
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public ContactUsEnquiry delete(@PathParam(value="id")String id){
		ContactUsEnquiry enquiry = enquiryRepository.findOne(id);
		enquiryRepository.delete(enquiry);
		return enquiry;
	}

}
