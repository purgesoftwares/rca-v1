package com.arnav.controllers.coupon;


import com.arnav.model.coupon.Coupon;
import com.arnav.model.coupon.CouponPackage;
import com.arnav.model.coupon.JoinedFriend;
import com.arnav.model.coupon.PurchasedCoupon;
import com.arnav.model.provider.Provider;
import com.arnav.repository.coupon.CouponRepository;
import com.arnav.repository.coupon.JoinedFriendRepository;
import com.arnav.repository.coupon.PurchasedCouponRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.*;

/**
 * Created by Shankar on 2/19/2017.
 */
@Path("/secured/purchased-package")
public class PurchasedCouponController {

    @Autowired
    private PurchasedCouponRepository purchasedCouponRepository;

    @Autowired
    private JoinedFriendRepository joinedFriendRepository;

    @Autowired
    private CouponRepository couponRepository;

    @POST
    @Produces("application/json")
    public PurchasedCoupon create(PurchasedCoupon purchasedCoupon){

        List<JoinedFriend> joinedFriends = new ArrayList<JoinedFriend>();

        if (purchasedCoupon.getJoinedFriends() != null && !purchasedCoupon.getJoinedFriends().isEmpty()){
            joinedFriendRepository.save(purchasedCoupon.getJoinedFriends());
        }
        purchasedCoupon = purchasedCouponRepository.save(purchasedCoupon);

        CouponPackage couponPackage = purchasedCoupon.getCouponPackage();
        if(couponPackage != null && !couponPackage.getProviders().isEmpty()){
            for (Provider provider: couponPackage.getProviders()
                 ) {
                Coupon coupon  = new Coupon();
                coupon.setCouponCode(String.valueOf(purchasedCoupon.getCouponNumber()));
                coupon.setProviderId(provider.getId());
                coupon.setCouponCode(this.randomCode());
                coupon.setPrice(couponPackage.getPrice());
                coupon.setEndTime(couponPackage.getEndTime());
                coupon.setStartTime(couponPackage.getStartTime());
                couponRepository.save(coupon);
            }
        }
        return purchasedCoupon;
    }

    public String randomCode() {
        String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < 6) {
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        String saltStr = salt.toString();
        return saltStr;
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public PurchasedCoupon findOne(@PathParam(value="id")String id){
        return purchasedCouponRepository.findOne(id);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Page<PurchasedCoupon> findAll(Pageable pageble){

        return new PageImpl<PurchasedCoupon>(purchasedCouponRepository.findAll(pageble)
                .getContent(), pageble, 20);
    }

    @PUT
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public PurchasedCoupon update(@PathParam(value="id")String id, @Valid PurchasedCoupon coupon){
        PurchasedCoupon preCoupon = purchasedCouponRepository.findOne(id);
        coupon.setId(id);

        if(coupon.getCouponNumber() == null){
            coupon.setCouponNumber(preCoupon.getCouponNumber());
        }
        return purchasedCouponRepository.save(coupon);
    }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public PurchasedCoupon delete(@PathParam(value="id")String id){
        PurchasedCoupon coupon = purchasedCouponRepository.findOne(id);
        purchasedCouponRepository.delete(coupon);
        return coupon;
    }

}
