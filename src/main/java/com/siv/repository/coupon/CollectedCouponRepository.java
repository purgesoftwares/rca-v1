package com.siv.repository.coupon;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.siv.model.coupon.CollectedCoupon;

import javax.ws.rs.PathParam;
import java.util.List;

@RepositoryRestResource
public interface CollectedCouponRepository extends MongoRepository<CollectedCoupon, String>, CrudRepository<CollectedCoupon, String>, PagingAndSortingRepository<CollectedCoupon, String>{

    public List<CollectedCoupon> findByProviderId(@PathParam("providerId") String providerId);
}
