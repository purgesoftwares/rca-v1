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

import com.arnav.exceptions.CustomNotFoundException;
import com.arnav.exceptions.NoCurrentProviderException;
import com.arnav.exceptions.UserNotFoundException;
import com.arnav.model.coupon.Coupon;
import com.arnav.model.coupon.CouponPackage;
import com.arnav.model.coupon.PurchasedCoupon;
import com.arnav.model.user.User;
import com.arnav.repository.coupon.CouponPackageRepository;
import com.arnav.repository.coupon.CouponRepository;
import com.arnav.repository.coupon.PurchasedCouponRepository;
import com.arnav.repository.user.UserRepository;
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
import org.springframework.security.core.context.SecurityContextHolder;

@Path("/secured/rate-review")
public class RateReviewController {
	
	@Autowired
	private RateReviewRepository rateReviewRepository;
	
	@Autowired
	private CustomerRepository customerRepository;
	
	@Autowired
	private EmailService emailService;

	@Autowired
	private CouponRepository couponRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private CouponPackageRepository couponPackageRepository;

	@Autowired
	private PurchasedCouponRepository purchasedCouponRepository;
	
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
	@Path("/get-feedback/{couponId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Boolean findFeedbackByCouponId(
			@PathParam(value="couponId")String couponId)
			throws CustomNotFoundException {

		Coupon coupon = couponRepository.findOne(couponId);

		if(coupon == null){
			throw new CustomNotFoundException("Not a valid request. May be URL expired.");
		}

		Customer customer = this.findCurrentCustomer();

		if(customer == null){
			throw new UserNotFoundException("Invalid access or Session expired");
		}

		PurchasedCoupon couponPackage = purchasedCouponRepository.findOne(coupon.getPurchasedCouponId());

		if(couponPackage == null || !couponPackage.getCustomerId().equals(customer.getId())){
			throw new CustomNotFoundException("Not a valid request now!");
		}

		RateReview rateReview = rateReviewRepository.findByCustomerIdAndCouponPackageIdAndProviderId(
				customer.getId(),
				coupon.getPurchasedCouponId(),
				coupon.getProviderId()
		);


		if(rateReview == null){
			return true;
		}else {
			return false;
		}
	}

	public Customer findCurrentCustomer(){
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		User activeUser = userRepository.findByUsername(username);
		Customer customer = null;

		if(!username.equals("anonymousUser") && activeUser != null) {

			customer = customerRepository.findByUserId(activeUser.getId());
		} else {
			throw new NoCurrentProviderException("There is no current provider, please first login.");
		}


		return customer;
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
