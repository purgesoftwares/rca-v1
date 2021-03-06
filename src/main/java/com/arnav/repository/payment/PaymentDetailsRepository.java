package com.arnav.repository.payment;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import com.arnav.model.payment.PaymentDetails;

@RepositoryRestResource
public interface PaymentDetailsRepository extends CrudRepository<PaymentDetails, String>, MongoRepository<PaymentDetails, String> {

}
