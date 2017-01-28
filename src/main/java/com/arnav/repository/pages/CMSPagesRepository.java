package com.arnav.repository.pages;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.CrudRepository;
import com.arnav.model.pages.CMSPages;

public interface CMSPagesRepository extends CrudRepository<CMSPages, String>, MongoRepository<CMSPages, String>{

}
