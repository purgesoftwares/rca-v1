package com.siv.controllers.coupon;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.springframework.beans.factory.annotation.Autowired;

import com.siv.model.coupon.Coupon;
import com.siv.model.coupon.SelectedCoupon;
import com.siv.repository.coupon.CouponRepository;
import com.siv.repository.provider.ProviderRepository;

@Path("/secured/select-coupon")
public class SelectedCouponController {
	
	@Autowired
	private CouponRepository couponRepository;
	
	@Autowired
	private ProviderRepository providerRepository;
	
	@POST
	@Produces("application/json")
	public SelectedCoupon create(SelectedCoupon selectCoupon){
		
		Coupon coupon = couponRepository.findByCouponCode(selectCoupon.getCouponCode());
		
			
		return null;		
	}

}
