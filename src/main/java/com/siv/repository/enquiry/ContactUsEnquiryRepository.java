package com.siv.repository.enquiry;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.siv.model.enquiry.ContactUsEnquiry;

@RepositoryRestResource
public interface ContactUsEnquiryRepository extends CrudRepository<ContactUsEnquiry, String>, MongoRepository<ContactUsEnquiry, String>{

}
