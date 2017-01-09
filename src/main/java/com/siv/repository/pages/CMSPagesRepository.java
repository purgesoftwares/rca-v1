package com.siv.repository.pages;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.CrudRepository;
import com.siv.model.pages.CMSPages;

public interface CMSPagesRepository extends CrudRepository<CMSPages, String>, MongoRepository<CMSPages, String>{

}
