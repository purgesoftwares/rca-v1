package com.arnav.repository.coupon;

import com.arnav.model.coupon.Coupon;
import com.arnav.model.coupon.CouponPackage;
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
public interface CouponPackageRepository
        extends MongoRepository<CouponPackage, String>, CrudRepository<CouponPackage, String>,
        PagingAndSortingRepository<CouponPackage, String> {

    @Transactional
    public List<CouponPackage> findByCouponNumber(String couponNumber);

}