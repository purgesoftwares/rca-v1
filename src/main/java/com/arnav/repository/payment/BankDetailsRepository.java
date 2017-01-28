package com.arnav.repository.payment;

import javax.ws.rs.PathParam;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import com.arnav.model.payment.BankDetails;


@RepositoryRestResource
public interface BankDetailsRepository extends CrudRepository<BankDetails, String>, MongoRepository<BankDetails, String>{

    public BankDetails findByProviderId(@PathParam("providerId")String providerId);
}
