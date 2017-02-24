package com.arnav.controllers.coupon;


import com.arnav.model.coupon.Coupon;
import com.arnav.model.coupon.CouponPackage;
import com.arnav.model.coupon.JoinedFriend;
import com.arnav.model.coupon.PurchasedCoupon;
import com.arnav.model.customer.Customer;
import com.arnav.model.provider.Provider;
import com.arnav.model.user.User;
import com.arnav.repository.coupon.CouponRepository;
import com.arnav.repository.coupon.JoinedFriendRepository;
import com.arnav.repository.coupon.PurchasedCouponRepository;
import com.arnav.services.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import javax.mail.MessagingException;
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
    private EmailService emailService;

    @Autowired
    private PurchasedCouponRepository purchasedCouponRepository;

    @Autowired
    private JoinedFriendRepository joinedFriendRepository;

    @Autowired
    private CouponRepository couponRepository;

    @POST
    @Produces("application/json")
    public PurchasedCoupon create(PurchasedCoupon purchasedCoupon) throws MessagingException {

        List<JoinedFriend> joinedFriends = new ArrayList<JoinedFriend>();

        if (purchasedCoupon.getJoinedFriends() != null && !purchasedCoupon.getJoinedFriends().isEmpty()){
            joinedFriendRepository.save(purchasedCoupon.getJoinedFriends());
        }
        purchasedCoupon = purchasedCouponRepository.save(purchasedCoupon);

        String couponString = "Hello "
                +purchasedCoupon.getCustomer().getFirstName()
                +",<br/><br/><table><tr><td colspan='2' > <strong>Thanks for buying the Coupon Package with Coupon Number : "
                +purchasedCoupon.getCouponNumber()
                +"</strong></td></tr><tr><td colspan='2' > <br/><br/><b>Your Coupons : </b></td></tr>";

        CouponPackage couponPackage = purchasedCoupon.getCouponPackage();
        if(couponPackage != null && !couponPackage.getProviders().isEmpty()){
            for (Provider provider: couponPackage.getProviders()
                 ) {

                Coupon coupon  = new Coupon();
                coupon.setCouponNumber(purchasedCoupon.getCouponNumber());
                coupon.setProviderId(provider.getId());
                coupon.setCouponCode(this.randomCode());
                coupon.setPrice(couponPackage.getPrice());
                coupon.setEndTime(couponPackage.getEndTime());
                coupon.setStartTime(couponPackage.getStartTime());
                coupon.setPurchasedCouponId(purchasedCoupon.getId());
                coupon.setProvider(provider);
                couponRepository.save(coupon);
                couponString += "<tr><td>Coupon Code: "+coupon.getCouponCode()+ "</td><td> Provider Name: "
                        + coupon.getProvider().getProvider_name() + "</td></tr>";
            }
        }
        couponString += "</table> <br/><br/> Thanks <br/> Sales Team";
        this.sendPurchasedCouponEmail(couponString, purchasedCoupon.getCustomer(),
                purchasedCoupon.getCustomer().getMainEmail());
        return purchasedCoupon;
    }


    @GET
    @Path("/{purchasedCouponId}/{email}")
    @Produces("application/json")
    public String create(@PathParam(value="purchasedCouponId") String purchasedCouponId,
                         @PathParam(value="email") String email ) throws MessagingException {

        PurchasedCoupon purchasedCoupon = purchasedCouponRepository.findOne(purchasedCouponId);

        String couponString = "Hello "
                +purchasedCoupon.getCustomer().getFirstName()
                +",<br/><br/><table><tr><td colspan='2' > <strong>Thanks for buying the Coupon Package with Coupon Number : "
                +purchasedCoupon.getCouponNumber()
                +"</strong></td></tr><tr><td colspan='2' > <br/><br/><b>Your Coupons : </b></td></tr>";

        CouponPackage couponPackage = purchasedCoupon.getCouponPackage();
        if(couponPackage != null && !couponPackage.getProviders().isEmpty()){
            List<Coupon> coupons = couponRepository.findByPurchasedCouponId(purchasedCouponId);
            for (Coupon coupon: coupons
                    ) {
                couponString += "<tr><td>Coupon Code: "+coupon.getCouponCode()+ "</td><td> Provider Name: "
                        + coupon.getProvider().getProvider_name() + "</td></tr>";
            }
        }
        couponString += "</table> <br/><br/> Thanks <br/> Sales Team";
        this.sendPurchasedCouponEmail(couponString, purchasedCoupon.getCustomer(), email);
        return "success";
    }

    private void sendPurchasedCouponEmail(final String html,
                                     final Customer customer, final String email) throws MessagingException {

        Map<String, String> processData = new HashMap<String, String>();

        processData.put("html", html);

        emailService.sendMailWithHtml(customer.getFullName(),
                email,   //enter valid email like - "mamta.soni@xtreemsolution.com"
                "Your Coupon Package",
                html,
                processData);

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
