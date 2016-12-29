package com.siv.controllers.coupon;

import java.util.Date;

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
import org.springframework.data.domain.Pageable;

import com.siv.model.coupon.Coupon;
import com.siv.repository.coupon.CouponRepository;

@Path("/coupon")
public class CouponController {
	
	@Autowired
	private CouponRepository couponRepository;
	
	@POST
	@Produces("application/json")
	public Coupon create(Coupon coupon){
		coupon.setCreateDate(new Date());
		coupon.setLastUpdate(new Date());		
		return couponRepository.save(coupon);		
	}
	
	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Coupon findOne(@PathParam(value="id")String id){
		return couponRepository.findOne(id);
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Page<Coupon> findAll(Pageable pageble){
		return couponRepository.findAll(pageble);
	}
	
	@PUT
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Coupon update(@PathParam(value="id")String id, @Valid Coupon coupon){
		Coupon preCoupon = couponRepository.findOne(id);
		coupon.setId(id);
		coupon.setCreateDate(preCoupon.getCreateDate());
		coupon.setLastUpdate(new Date());
		
		if(coupon.getCouponCode() == null){
			coupon.setCouponCode(preCoupon.getCouponCode());
		} else if(coupon.getCouponNumber() == null){
			coupon.setCouponNumber(preCoupon.getCouponNumber());
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
