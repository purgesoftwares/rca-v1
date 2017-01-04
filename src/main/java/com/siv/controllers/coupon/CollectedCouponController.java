package com.siv.controllers.coupon;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.siv.model.coupon.Coupon;
import com.siv.model.coupon.CollectedCoupon;
import com.siv.repository.coupon.CollectedCouponRepository;
import com.siv.repository.coupon.CouponRepository;
import com.siv.repository.provider.ProviderRepository;

@Path("/secured/select-coupon")
public class CollectedCouponController {
	
	@Autowired
	private CouponRepository couponRepository;
	
	@Autowired
	private ProviderRepository providerRepository;
	
	@Autowired
	private CollectedCouponRepository collCouponRepository;
	
	@POST
	@Produces("application/json")
	public CollectedCoupon create(CollectedCoupon collectCoupon){
		List<Coupon> coupons = couponRepository.findByProviderId(collectCoupon.getProviderId());
		collectCoupon.setStartDate(new Date());
		collectCoupon.setNumOfCoupons(coupons.size());
		BigDecimal totalValues = new BigDecimal(0.0);
		
		for(Coupon coupon : coupons){
			totalValues = totalValues.add(coupon.getPrice());
		}
		collectCoupon.setValue(totalValues);
			
			
		return collCouponRepository.save(collectCoupon);		
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Page<CollectedCoupon> findAll(Pageable pageble){
		return collCouponRepository.findAll(pageble);
	}

}
