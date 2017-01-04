package com.siv.repository.coupon;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.transaction.annotation.Transactional;

import com.siv.model.coupon.Coupon;

@RepositoryRestResource
public interface CouponRepository extends MongoRepository<Coupon, String>, CrudRepository<Coupon, String> {

	@Transactional
	public Coupon findByCouponCode(String couponCode);
}
