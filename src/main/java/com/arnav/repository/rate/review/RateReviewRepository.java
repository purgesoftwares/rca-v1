package com.arnav.repository.rate.review;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import com.arnav.model.rate.review.RateReview;

@RepositoryRestResource
public interface RateReviewRepository extends MongoRepository<RateReview, String>, CrudRepository<RateReview, String>{

    public RateReview findByCustomerIdAndCouponPackageIdAndProviderId(String id, String purchasedCouponId, String providerId);
}
