package com.arnav.repository.coupon;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.transaction.annotation.Transactional;

import com.arnav.model.coupon.Coupon;

@RepositoryRestResource
public interface CouponRepository extends MongoRepository<Coupon, String>, CrudRepository<Coupon, String>, PagingAndSortingRepository<Coupon, String> {

	@Transactional
	public List<Coupon> findByProviderId(String providerId);
	
	@Transactional
	public Coupon findByCouponCode(String couponCode);

	@Transactional
    public List<Coupon> findByPurchasedCouponId(String id);

	@Transactional
    public List<Coupon> findByCouponPackageId(String id);
	
	@Transactional
	public List<Coupon> findByUsedAndProviderId(Integer used, String providerId);

	@Transactional
	public Coupon findByCouponCodeAndProviderId(String couponCode, String providerId);
}
