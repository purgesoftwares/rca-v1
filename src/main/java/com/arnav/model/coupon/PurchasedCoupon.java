package com.arnav.model.coupon;

import com.arnav.model.customer.Customer;
import com.arnav.model.provider.Provider;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Shankar on 2/19/2017.
 */
public class PurchasedCoupon {
    @Id
    private String id;

    @NotEmpty(message="Coupon Number is required.")
    private Long couponNumber;

    @NotEmpty
    private List<JoinedFriend> joinedFriends = new ArrayList<JoinedFriend>();

    @NotNull
    private CouponPackage couponPackage;

    @NotEmpty
    private String customerId;

    @NotNull
    private Customer customer;

    private Date startTime;

    private Date endTime;

    @NotNull
    private Date createdAt;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getCouponNumber() {
        return couponNumber;
    }

    public void setCouponNumber(Long couponNumber) {
        this.couponNumber = couponNumber;
    }

    public List<JoinedFriend> getJoinedFriends() {
        return joinedFriends;
    }

    public void setJoinedFriends(List<JoinedFriend> joinedFriends) {
        this.joinedFriends = joinedFriends;
    }

    public CouponPackage getCouponPackage() {
        return couponPackage;
    }

    public void setCouponPackage(CouponPackage couponPackage) {
        this.couponPackage = couponPackage;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    @Override
    public String toString() {
        return "PurchasedCoupon{" +
                "id='" + id + '\'' +
                ", couponNumber=" + couponNumber +
                ", joinedFriends=" + joinedFriends +
                ", couponPackage=" + couponPackage +
                ", customerId='" + customerId + '\'' +
                ", customer=" + customer +
                '}';
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }
}
