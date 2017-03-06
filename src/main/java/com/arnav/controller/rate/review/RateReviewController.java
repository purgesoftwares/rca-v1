package com.arnav.controller.rate.review;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.mail.MessagingException;
import javax.validation.Valid;
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
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import com.arnav.exceptions.AllPropertyRequiredException;
import com.arnav.model.customer.Customer;
import com.arnav.model.rate.review.RateReview;
import com.arnav.repository.customer.CustomerRepository;
import com.arnav.repository.rate.review.RateReviewRepository;
import com.arnav.services.EmailService;

@Path("/secured/rate-review")
public class RateReviewController {
	
	@Autowired
	private RateReviewRepository rateReviewRepository;
	
	@Autowired
	private CustomerRepository customerRepository;
	
	@Autowired
	private EmailService emailService;
	
	
	@POST
	@Produces("application/json")
	public RateReview create(RateReview rateReview) throws MessagingException{
		
		if(rateReview.getProviderId() == null && rateReview.getFeedback() == null && rateReview.getRank() == null
				&& rateReview.getCustomerId() == null){
			throw new AllPropertyRequiredException("Please fill provider id, feedback, rank and customerId");
		}
		rateReview.setCreateDate(new Date());
		rateReview.setLastUpdate(new Date());
		rateReview.setCustomer(customerRepository.findOne(rateReview.getCustomerId()));
		rateReviewRepository.save(rateReview);
		
		String rateReviewString = "Hello "
                +rateReview.getCustomer().getFirstName()
                +",<br/><br/><table><tr><td colspan='2' > <strong>Thanks for giving the Rating with Your Feedback : "
                +rateReview.getRank()
                +"</strong></td></tr><tr><td colspan='2' > <br/><br/><b>Your Feedback : "
                +rateReview.getFeedback()
                +"</b></td></tr>";
		
		this.sendEmailForCreatingRateReview(rateReviewString, rateReview.getCustomer(), rateReview.getCustomer().getMainEmail());;
		return rateReview;		
	}
	
	private void sendEmailForCreatingRateReview(final String html,
            final Customer customer, final String email) throws MessagingException {

		Map<String, String> processData = new HashMap<String, String>();
		
		processData.put("html", html);
		
		emailService.sendMailWithHtml(customer.getFullName(),
		email,  
		"Your Rate and Review",
		html,
		processData);
		
	}

	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public RateReview findOne(@PathParam(value="id")String id){
		return rateReviewRepository.findOne(id);
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Page<RateReview> findAll(Pageable pageble){
		
		return new PageImpl<RateReview>(rateReviewRepository.findAll(pageble).getContent(), pageble, 20);
	}
	
	@DELETE
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public RateReview delete(@PathParam(value="id")String id){
		RateReview rateReview = rateReviewRepository.findOne(id);
		rateReviewRepository.delete(rateReview);
		return rateReview;
	}
	
	@PUT
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public RateReview update(@PathParam(value="id")String id, @Valid RateReview rateReview){
		RateReview preRateReview = rateReviewRepository.findOne(id);
		rateReview.setId(id);
		
		if(rateReview.getCouponPackageId() == null){
			rateReview.setCouponPackageId(preRateReview.getCouponPackageId());
		} if(rateReview.getCustomerId() == null) {
			rateReview.setCustomerId(preRateReview.getCustomerId());
		} if(rateReview.getCustomer() == null) {
			rateReview.setCustomer(preRateReview.getCustomer());
		} if(rateReview.getFeedback() == null){
			rateReview.setFeedback(preRateReview.getFeedback());
		} if(rateReview.getProviderId() == null) {
			rateReview.setProviderId(preRateReview.getProviderId());
		} if(rateReview.getPictureDescription() == null){
			rateReview.setPictureDescription(preRateReview.getPictureDescription());
		} if(rateReview.getRank() == null){
			rateReview.setRank(preRateReview.getRank());
		} if(rateReview.getStatus() == null){
			rateReview.setStatus(preRateReview.getStatus());
		} if(rateReview.getUploadFileId() == null){
			rateReview.setStatus(preRateReview.getStatus());
		}
		return rateReviewRepository.save(rateReview);	
	}
}
