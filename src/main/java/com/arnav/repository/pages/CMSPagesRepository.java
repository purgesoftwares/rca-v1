package com.arnav.repository.pages;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.arnav.model.pages.CMSPages;

public interface CMSPagesRepository extends CrudRepository<CMSPages, String>, MongoRepository<CMSPages, String>,
		PagingAndSortingRepository<CMSPages, String>{

}
