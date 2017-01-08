package com.siv.controllers.coupon;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.omg.CosNaming.NamingContextPackage.NotEmpty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.siv.model.coupon.Coupon;
import com.siv.model.coupon.CollectedCoupon;
import com.siv.repository.coupon.CollectedCouponRepository;
import com.siv.repository.coupon.CouponRepository;
import com.siv.repository.provider.ProviderRepository;

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

		Date now = new Date();
//		System.out.println(coupon.getAvailability());
//		System.out.println(coupon.getProviderId());
//		System.out.println(coupon.getEndTime());
//		System.out.println(coupon.getStartTime());
		if(coupon != null && coupon.getAvailability()!=0 && coupon.getProviderId().equals(collectedCoupon.getProviderId())
			/*	&& (now.compareTo(coupon.getStartTime()) >= 0)
				&& (now.compareTo(coupon.getEndTime()) <= 0)*/){
			return Response.ok(coupon).build();
		}else{
			throw new CustomNotFoundException("Coupon not found");

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
