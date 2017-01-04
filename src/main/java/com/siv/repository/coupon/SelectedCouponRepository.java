package com.siv.repository.coupon;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import com.siv.model.coupon.SelectedCoupon;

@RepositoryRestResource
public interface SelectedCouponRepository extends MongoRepository<SelectedCoupon, String>, CrudRepository<SelectedCoupon, String>{

}
