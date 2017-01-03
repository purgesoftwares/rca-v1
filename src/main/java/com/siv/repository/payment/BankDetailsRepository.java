package com.siv.repository.payment;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import com.siv.model.payment.BankDetails;


@RepositoryRestResource
public interface BankDetailsRepository extends CrudRepository<BankDetails, String>, MongoRepository<BankDetails, String>{

}
