package com.arnav.controllers.coupon;

import com.arnav.model.coupon.CouponPackage;
import com.arnav.repository.coupon.CouponPackageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

/**
 * Created by Shankar on 2/19/2017.
 */
@Path("/secured/coupon-package")
public class CouponPackageController {

    @Autowired
    private CouponPackageRepository couponPackageRepository;

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
    @Produces(MediaType.APPLICATION_JSON)
    public Page<CouponPackage> findAll(Pageable pageble){

        return new PageImpl<CouponPackage>(couponPackageRepository.findAll(pageble)
                .getContent(), pageble, 20);
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
