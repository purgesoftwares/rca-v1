package com.arnav.repository.coupon;

import com.arnav.model.coupon.JoinedFriend;
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
public interface JoinedFriendRepository
        extends MongoRepository<JoinedFriend, String>, CrudRepository<JoinedFriend, String>,
        PagingAndSortingRepository<JoinedFriend, String> {

    @Transactional
    public List<JoinedFriend> findByCouponNumber(String couponNumber);

    @Transactional
    public List<JoinedFriend> findByCustomerId(String customerId);


    @Transactional
    public List<JoinedFriend> findByCustomerIdAndCouponNumber(String customerId, String couponNumber);

}
