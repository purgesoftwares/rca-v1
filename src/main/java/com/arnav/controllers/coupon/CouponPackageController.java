package com.arnav.controllers.coupon;

import javax.validation.Valid;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;

import com.arnav.model.coupon.Coupon;
import com.arnav.model.coupon.CouponPackage;
import com.arnav.repository.coupon.CouponPackageRepository;
import com.arnav.repository.coupon.CouponRepository;

/**
 * Created by Shankar on 2/19/2017.
 */
@Path("/secured/coupon-package")
public class CouponPackageController {

    @Autowired
    private CouponPackageRepository couponPackageRepository;

    @Autowired
    private CouponRepository couponRepository;

    @POST
    @Produces("application/json")
    public CouponPackage create(CouponPackage couponPackage){
        return couponPackageRepository.save(couponPackage);
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public CouponPackage findOne(@PathParam(value="id")String id){
        return couponPackageRepository.findOne(id);
    }

    @GET
    @Path("/get-by-coupon-id/{couponId}")
    @Produces(MediaType.APPLICATION_JSON)
    public CouponPackage findByCouponId(@PathParam(value="couponId")String couponId){

        Coupon coupon = couponRepository.findOne(couponId);


        return couponPackageRepository.findOne(coupon.getCouponPackageId());
    }


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Page<CouponPackage> findAll(Pageable pageble,@DefaultValue("0") @QueryParam("page") int page,
			@DefaultValue("id") @QueryParam("sort") String sort, @DefaultValue("20") @QueryParam("size") int size){
    	
    	final PageRequest page1 = new PageRequest(
				  page, size, new Sort(
						    new Order(sort.equals("id")? Direction.DESC : Direction.ASC, sort)));
        return couponPackageRepository.findAll(page1);
    }

    @PUT
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public CouponPackage update(@PathParam(value="id")String id, @Valid CouponPackage coupon){
        CouponPackage preCoupon = couponPackageRepository.findOne(id);
        coupon.setId(id);

        if(coupon.getCouponNumber() == null){
            coupon.setCouponNumber(preCoupon.getCouponNumber());
        }
        if(coupon.getPrice() == null) {
            coupon.setPrice(preCoupon.getPrice());
        }
        if(coupon.getStartTime() == null) {
            coupon.setStartTime(preCoupon.getStartTime());
        }
        if(coupon.getEndTime() == null) {
            coupon.setEndTime(preCoupon.getEndTime());
        }
        return couponPackageRepository.save(coupon);
    }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public CouponPackage delete(@PathParam(value="id")String id){
        CouponPackage coupon = couponPackageRepository.findOne(id);
        couponPackageRepository.delete(coupon);
        return coupon;
    }

}
