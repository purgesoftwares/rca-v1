package com.siv.repository.coupon;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface CouponUserAssignment extends MongoRepository<CouponUserAssignment, String>, CrudRepository<CouponUserAssignment, String>{

}
