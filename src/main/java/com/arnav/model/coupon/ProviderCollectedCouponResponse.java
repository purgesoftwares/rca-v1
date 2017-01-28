package com.arnav.model.coupon;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Shankar on 1/8/2017.
 */
public class ProviderCollectedCouponResponse {

    private List<CollectedCoupon> collectedCouponList = new ArrayList<CollectedCoupon>();

    public List<CollectedCoupon> getCollectedCouponList() {
        return collectedCouponList;
    }

    public void setCollectedCouponList(List<CollectedCoupon> collectedCouponList) {
        this.collectedCouponList = collectedCouponList;
    }

    @Override
    public String toString() {
        return "ProviderCollectedCouponResponse{" +
                "collectedCouponList=" + collectedCouponList +
                '}';
    }
}
