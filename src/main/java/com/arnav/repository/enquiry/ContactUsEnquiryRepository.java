package com.arnav.repository.enquiry;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.arnav.model.enquiry.ContactUsEnquiry;

@RepositoryRestResource
public interface ContactUsEnquiryRepository extends CrudRepository<ContactUsEnquiry, String>, MongoRepository<ContactUsEnquiry, String>,
				PagingAndSortingRepository<ContactUsEnquiry, String>{

}
