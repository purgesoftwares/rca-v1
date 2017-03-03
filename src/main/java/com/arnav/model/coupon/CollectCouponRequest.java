package com.arnav.model.coupon;

/**
 * Created by Shankar on 3/4/2017.
 */
public class CollectCouponRequest {

    /**
     * providerId : id
     * couponCode : code
     */

    private String providerId;
    private String couponCode;

    public String getProviderId() {
        return providerId;
    }

    public void setProviderId(String providerId) {
        this.providerId = providerId;
    }

    public String getCouponCode() {
        return couponCode;
    }

    public void setCouponCode(String couponCode) {
        this.couponCode = couponCode;
    }

    @Override
    public String toString() {
        return "CollectCouponRequest{" +
                "providerId='" + providerId + '\'' +
                ", couponCode='" + couponCode + '\'' +
                '}';
    }
}
