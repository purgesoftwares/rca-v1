package com.arnav.model.coupon;

import com.arnav.model.openings.OpeningTime;
import com.arnav.model.provider.Provider;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Shankar on 2/19/2017.
 */
public class CouponPackage {

    @Id
    private String id;

    @NotEmpty(message="Coupon Number is required.")
    @Indexed(unique=true)
    private Long couponNumber;

    @NotEmpty
    private List<Provider> providers = new ArrayList<Provider>();

    @NotNull
    private BigDecimal price;

    @NotNull
    private BigDecimal radius;

    @NotNull
    private Date startTime;

    @NotNull
    private Date endTime;

    @NotNull
    private BigDecimal redeemableTime;

    public CouponPackage(){

    }

    public CouponPackage (String id,Long couLong){
        this.id = id;
        this.couponNumber = couLong;
    }

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

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public List<Provider> getProviders() {
        return providers;
    }

    public void setProviders(List<Provider> providers) {
        this.providers = providers;
    }

    public BigDecimal getRadius() {
        return radius;
    }

    public void setRadius(BigDecimal radius) {
        this.radius = radius;
    }

    public BigDecimal getRedeemableTime() {
        return redeemableTime;
    }

    public void setRedeemableTime(BigDecimal redeemableTime) {
        this.redeemableTime = redeemableTime;
    }
}
