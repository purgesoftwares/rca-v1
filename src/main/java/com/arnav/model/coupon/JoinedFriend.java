package com.arnav.model.coupon;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.data.annotation.Id;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * Created by Shankar on 2/19/2017.
 */
public class JoinedFriend {
    @Id
    private String id;

    @NotEmpty(message="Coupon Number is required.")
    private Long couponNumber;

    @NotNull
    private String name;

    @Email
    private String email;

    @NotNull
    private Date createdAt;

    @NotNull
    private String customerId;

    @NotNull
    private String purchaseId;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getPurchaseId() {
        return purchaseId;
    }

    public void setPurchaseId(String purchaseId) {
        this.purchaseId = purchaseId;
    }

    @Override
    public String toString() {
        return "JoinedFriend{" +
                "id='" + id + '\'' +
                ", couponNumber=" + couponNumber +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", createdAt=" + createdAt +
                ", customerId='" + customerId + '\'' +
                ", purchaseId='" + purchaseId + '\'' +
                '}';
    }
}