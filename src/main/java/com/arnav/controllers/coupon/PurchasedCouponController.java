package com.arnav.controllers.coupon;


import com.arnav.exceptions.NoCurrentProviderException;
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
import com.arnav.repository.customer.CustomerRepository;
import com.arnav.repository.user.UserRepository;
import com.arnav.services.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;

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

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @POST
    @Produces("application/json")
    public PurchasedCoupon create(PurchasedCoupon purchasedCoupon) throws MessagingException {

        List<JoinedFriend> joinedFriends = new ArrayList<JoinedFriend>();

        String code = this.randomCode();

        if (purchasedCoupon.getJoinedFriends() != null && !purchasedCoupon.getJoinedFriends().isEmpty()){
            joinedFriendRepository.save(purchasedCoupon.getJoinedFriends());
        }
        purchasedCoupon.setCouponCode(code);
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
                coupon.setCouponCode(code);
                coupon.setPrice(couponPackage.getPrice());
                coupon.setCouponPackageId(couponPackage.getId());
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
        while (salt.length() < 8) {
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

    @GET
    @Path("/my")
    @Produces(MediaType.APPLICATION_JSON)
    public Page<PurchasedCoupon> findAllCustomerPurchasedCoupon(Pageable pageble){

        Customer customer = this.findCurrentCustomer();

        return new PageImpl<PurchasedCoupon>(purchasedCouponRepository.findByCustomerId(customer.getId()), pageble, 20);
    }

    @GET
    @Path("/active")
    @Produces(MediaType.APPLICATION_JSON)
    public Page<PurchasedCoupon> findAllActiveCustomerPurchasedCoupon(Pageable pageble){

        Customer customer = this.findCurrentCustomer();

        List<PurchasedCoupon> purchasedCoupons = purchasedCouponRepository.findByCustomerId(customer.getId());

        List<PurchasedCoupon> activePurchasedCoupons = new ArrayList<PurchasedCoupon>();

        for (PurchasedCoupon purchasedCoupon: purchasedCoupons){
            if(purchasedCoupon.getEndTime() == null || purchasedCoupon.getEndTime().after(new Date())){
                activePurchasedCoupons.add(purchasedCoupon);
            }
        }


        return new PageImpl<PurchasedCoupon>(activePurchasedCoupons, pageble, 20);
    }

    @GET
    @Path("/previous")
    @Produces(MediaType.APPLICATION_JSON)
    public Page<PurchasedCoupon> findAllPreviousCustomerPurchasedCoupon(Pageable pageble){

        Customer customer = this.findCurrentCustomer();

        List<PurchasedCoupon> purchasedCoupons = purchasedCouponRepository.findByCustomerId(customer.getId());

        List<PurchasedCoupon> previousPurchasedCoupons = new ArrayList<PurchasedCoupon>();

        for (PurchasedCoupon purchasedCoupon: purchasedCoupons){
            if(purchasedCoupon.getEndTime() != null && purchasedCoupon.getEndTime().before(new Date())){
                previousPurchasedCoupons.add(purchasedCoupon);
            }
        }

        return new PageImpl<PurchasedCoupon>(previousPurchasedCoupons, pageble, 20);
    }

    public Customer findCurrentCustomer(){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User activeUser = userRepository.findByUsername(username);
        Customer customer = null;

        if(!username.equals("anonymousUser") && activeUser != null) {

            customer = customerRepository.findByUserId(activeUser.getId());
        } else {
            throw new NoCurrentProviderException("There is no current provider, please first login.");
        }

        return customer;
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
