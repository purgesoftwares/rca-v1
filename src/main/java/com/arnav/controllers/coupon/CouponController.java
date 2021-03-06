package com.arnav.controllers.coupon;

import java.math.BigDecimal;
import java.util.*;

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
import com.arnav.exceptions.UsernameIsNotAnEmailException;
import com.arnav.model.CouponCollectedResponse;
import com.arnav.model.address.Address;
import com.arnav.model.coupon.CollectCouponRequest;
import com.arnav.model.coupon.CouponPackage;
import com.arnav.model.coupon.PurchasedCoupon;
import com.arnav.model.customer.Customer;
import com.arnav.model.provider.Provider;
import com.arnav.model.user.User;
import com.arnav.repository.address.AddressRepository;
import com.arnav.repository.coupon.CouponPackageRepository;
import com.arnav.repository.coupon.PurchasedCouponRepository;
import com.arnav.repository.customer.CustomerRepository;
import com.arnav.repository.provider.ProviderRepository;
import com.arnav.repository.user.UserRepository;
import com.arnav.services.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import com.arnav.model.coupon.Coupon;
import com.arnav.repository.coupon.CouponRepository;
import org.springframework.security.core.context.SecurityContextHolder;

@Path("/secured/coupon")
public class CouponController {
	
	@Autowired
	private CouponRepository couponRepository;

	@Autowired
	UserRepository userRepository;

	@Autowired
	private ProviderRepository providerRepository;

	@Autowired
	private AddressRepository addressRepository;

	@Autowired
	private PurchasedCouponRepository purchasedCouponRepository;

	@Autowired
	private CouponPackageRepository couponPackageRepository;

	@Autowired
	private CustomerRepository customerRepository;

	@Autowired
	private EmailService emailService;


	@POST
	@Produces("application/json")
	public Coupon create(Coupon coupon){
		
		//coupon.setCouponCode(UUID.randomUUID().toString());
		//coupon.setCouponNumber(UUID.randomUUID().node());
		return couponRepository.save(coupon);		
	}

	@POST
	@Path("/collect-coupon")
	@Produces("application/json")
	public Coupon collectCoupon(CollectCouponRequest collectCouponRequest)
			throws CustomNotFoundException, UsernameIsNotAnEmailException, MessagingException {

		Coupon coupon = couponRepository.findByCouponCodeAndProviderId(collectCouponRequest.getCouponCode(),
				collectCouponRequest.getProviderId());
		if(coupon == null){
			System.out.print("coupon null");
			throw new UsernameIsNotAnEmailException("Invalid Coupon Code!");
		}

		if(coupon.getUsed() != null && coupon.getUsed()!=0){
			throw new UsernameIsNotAnEmailException("This Coupon is Already Used or Expired!");
		}

		Provider provider = this.findCurrentProvider();
		System.out.println(provider);
		System.out.println(collectCouponRequest);

		PurchasedCoupon purchasedCoupon = purchasedCouponRepository.findOne(coupon.getPurchasedCouponId());

		if(provider == null){
			throw new UsernameIsNotAnEmailException("Unauthorized!");
		}

		if(!provider.getId().equals(collectCouponRequest.getProviderId())){
			throw new UsernameIsNotAnEmailException("Unauthorized!");
		}

		if(!coupon.getProviderId().equals(collectCouponRequest.getProviderId())){
			throw new UsernameIsNotAnEmailException("Invalid Coupon Code!");
		}

		coupon.setCollectionDate(new Date());
		coupon.setUsed(1);


		if(purchasedCoupon != null){
			Customer customer = customerRepository.findOne(purchasedCoupon.getCustomerId());

			if(customer != null){

				String feedbackUrl = "http://54.161.216.233:4201/dashboard/feedback/" + coupon.getId();

				String emailString = "Hello "
						+customer.getFirstName()
						+",<br/><br/><table><tr><td colspan='2' > <strong>Your coupon is redeemed at : "
						+provider.getProvider_name()
						+"</strong></td></tr><tr><td colspan='2' > <br/><br/><b>So Please Provide Your Feedback to this provider"
						+"</b></td></tr><tr><td colspan='2' > <br/><br/><b>Click on this link or open in URL in browser to submit your feedback." +
						"<a href='" + feedbackUrl + "'>" + feedbackUrl + "</a>"
						+"</b></td></tr>";

				emailString += "</table> <br/><br/> Thanks <br/> Sales Team";
				this.sendEmailToInviteForRateReview(emailString, customer, customer.getMainEmail());;

			}

			if(purchasedCoupon.getStartTime() == null || purchasedCoupon.getStartTime().equals("")){

				CouponPackage couponPackage = couponPackageRepository.findOne(coupon.getCouponPackageId());

				if(couponPackage != null){

					if(couponPackage.getRedeemableTime() == null || couponPackage.getRedeemableTime().equals("")
							|| couponPackage.getRedeemableTime().equals(0))
						couponPackage.setRedeemableTime(BigDecimal.valueOf(1.00));
					purchasedCoupon.setStartTime(new Date());

					Calendar c = Calendar.getInstance(); // starts with today's date and time
					c.add(Calendar.DAY_OF_YEAR, couponPackage.getRedeemableTime().intValue());  // advances day by 2
					Date date = c.getTime(); // gets modified time

					purchasedCoupon.setEndTime(date);

					purchasedCouponRepository.save(purchasedCoupon);
				}
			}

		}

		return couponRepository.save(coupon);
	}


	@GET
	@Path("/get-collect-coupon-details/{couponId}")
	@Produces("application/json")
	public CouponCollectedResponse getCollectCouponDetails(@PathParam(value="couponId")String couponId)
			throws CustomNotFoundException, UsernameIsNotAnEmailException, MessagingException {

		Coupon coupon = couponRepository.findOne(couponId);
		if(coupon == null){
			throw new UsernameIsNotAnEmailException("Invalid Coupon!");
		}


		PurchasedCoupon purchasedCoupon = purchasedCouponRepository.findOne(coupon.getPurchasedCouponId());

		Customer customer = customerRepository.findOne(purchasedCoupon.getCustomerId());
		Address address = addressRepository.findOne(customer.getAddressId());
		CouponCollectedResponse couponCollectedResponse = new CouponCollectedResponse(customer, address);

		return couponCollectedResponse;
	}

	private void sendEmailToInviteForRateReview(final String html,
												final Customer customer,
												final String email) throws MessagingException {

		Map<String, String> processData = new HashMap<String, String>();

		processData.put("html", html);

		emailService.sendMailWithHtml(customer.getFullName(),
				email,
				"You are Invited to give Feedback",
				html,
				processData);

	}

	public Provider findCurrentProvider(){
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		User activeUser = userRepository.findByUsername(username);
		Provider provider = null;

		if(!username.equals("anonymousUser") && activeUser != null) {

			provider = providerRepository.findByUserId(activeUser.getId());
		} else {
			throw new NoCurrentProviderException("There is no current provider, please first login.");
		}


		return provider;
	}

	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Coupon findOne(@PathParam(value="id")String id){
		return couponRepository.findOne(id);
	}

	@GET
	@Path("/find-by-purchased/{PurchasedId}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Coupon> findByPurchasedId(@PathParam(value="PurchasedId")String id){
		List<Coupon> coupons = couponRepository.findByPurchasedCouponId(id);

		for (Coupon coupon: coupons
			 ) {
			if(coupon.getProvider() != null && coupon.getProvider().getAddressId() != null){
				Address address = addressRepository.findOne(coupon.getProvider().getAddressId());
				coupon.setAddress(address);
			}
		}
		return coupons;
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Page<Coupon> findAll(Pageable pageble){
		
		return new PageImpl<Coupon>(couponRepository.findAll(pageble).getContent(), pageble, 20);
	}

	@GET
	@Path("/by-coupon-package/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Page<Coupon> findAll(@PathParam(value="id")String id, Pageable pageble){

		return new PageImpl<Coupon>(couponRepository.findByCouponPackageId(id), pageble, 20);
	}
	
	@PUT
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Coupon update(@PathParam(value="id")String id, @Valid Coupon coupon){
		Coupon preCoupon = couponRepository.findOne(id);
		coupon.setId(id);
		
		if(coupon.getCouponCode() == null){
			coupon.setCouponCode(preCoupon.getCouponCode());
		} if(coupon.getCouponNumber() == null){
			coupon.setCouponNumber(preCoupon.getCouponNumber());
		} if(coupon.getAvailability() == null){
			coupon.setAvailability(preCoupon.getAvailability());
		} if(coupon.getPrice() == null) {
			coupon.setPrice(preCoupon.getPrice());
		} if(coupon.getUsed() == null) {
			coupon.setUsed(preCoupon.getUsed());
		} if(coupon.getProviderId() == null){
			coupon.setProviderId(preCoupon.getProviderId());
		} if(coupon.getStartTime() == null) {
			coupon.setStartTime(preCoupon.getStartTime());
		} if(coupon.getEndTime() == null) {
			coupon.setEndTime(preCoupon.getEndTime());
		}
		return couponRepository.save(coupon);	
	}
	
	@DELETE
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Coupon delete(@PathParam(value="id")String id){
		Coupon coupon = couponRepository.findOne(id);
		couponRepository.delete(coupon);
		return coupon;
	}

}
