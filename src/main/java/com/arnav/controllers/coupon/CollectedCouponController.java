package com.arnav.controllers.coupon;

import java.text.ParseException;
import java.util.Date;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.arnav.exceptions.NoCurrentProviderException;
import com.arnav.model.coupon.ProviderCollectedCouponResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.arnav.model.coupon.Coupon;
import com.arnav.model.coupon.CollectedCoupon;
import com.arnav.repository.coupon.CollectedCouponRepository;
import com.arnav.repository.coupon.CouponRepository;
import com.arnav.repository.provider.ProviderRepository;

@Path("/secured/collect-coupon")
public class CollectedCouponController {
	
	@Autowired
	private CouponRepository couponRepository;
	
	@Autowired
	private ProviderRepository providerRepository;
	
	@Autowired
	private CollectedCouponRepository collCouponRepository;
	
	@POST
	@Produces("application/json")
	public CollectedCoupon create(CollectedCoupon collectCoupon) throws CustomNotFoundException {
		Coupon coupon = couponRepository.findByCouponCode(collectCoupon.getCouponCode());
		if(coupon != null){
			collectCoupon.setCreatedAt(new Date());
			collectCoupon.setStatus(true);
			collectCoupon.setPrice(coupon.getPrice());
			collectCoupon =  collCouponRepository.save(collectCoupon);
			if(collectCoupon != null){
				coupon.setAvailability(coupon.getAvailability()-1);
				coupon.setUsed(coupon.getUsed()+1);
				couponRepository.save(coupon);
			}
			return collectCoupon;
		}else{
			throw new CustomNotFoundException("Not a valid Coupon.");
		}
	}

	@POST
	@Path("/check-coupon")
	@Produces("application/json")
	public Response checkCoupon(CollectedCoupon collectedCoupon) throws ParseException,CustomNotFoundException {
		Coupon coupon = couponRepository.findByCouponCode(collectedCoupon.getCouponCode());

		if(coupon != null && coupon.getAvailability()!=0 && coupon.getProviderId().equals(collectedCoupon.getProviderId())){
			return Response.ok(coupon).build();
		}else{
			throw new NoCurrentProviderException("Coupon code is not valid.");

		}
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Page<CollectedCoupon> findAll(Pageable pageble){
		return collCouponRepository.findAll(pageble);
	}
	
	@GET
	@Path("/{couponCode}")
	@Produces(MediaType.APPLICATION_JSON)
	public Coupon getCouponByCode(@PathParam("couponCode")String couponCode){
		return couponRepository.findByCouponCode(couponCode);
	}

	@GET
	@Path("/by-provider/{providerId}")
	@Produces(MediaType.APPLICATION_JSON)
	public ProviderCollectedCouponResponse getCollectedCouponByProviderId(@PathParam("providerId")String providerId){
		ProviderCollectedCouponResponse providerCollectedCouponResponse = new ProviderCollectedCouponResponse();
		providerCollectedCouponResponse.setCollectedCouponList(collCouponRepository.findByProviderId(providerId));
		return providerCollectedCouponResponse;
	}



	@Path("/{id}")
	@PUT
	@Produces("application/json")
	public CollectedCoupon update(@PathParam("id") String id, CollectedCoupon collectCoupon){
		CollectedCoupon previousCollectedCoupon = collCouponRepository.findOne(id);

		if(collectCoupon.getCustomerName()!=null){
			previousCollectedCoupon.setCustomerName(collectCoupon.getCustomerName());
		}
		if(collectCoupon.getFrom()!=null){
			previousCollectedCoupon.setFrom(collectCoupon.getFrom());
		}

		return collCouponRepository.save(previousCollectedCoupon);
	}

}
