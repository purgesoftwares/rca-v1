package com.arnav.repository.coupon;

import com.arnav.model.coupon.PurchasedCoupon;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Shankar on 2/19/2017.
 */

@RepositoryRestResource
public interface PurchasedCouponRepository
    extends MongoRepository<PurchasedCoupon, String>, CrudRepository<PurchasedCoupon, String>,
    PagingAndSortingRepository<PurchasedCoupon, String> {

    @Transactional
    public List<PurchasedCoupon> findByCouponNumber(String couponNumber);

    @Transactional
    public List<PurchasedCoupon> findByCustomerId(String customerId);

}
