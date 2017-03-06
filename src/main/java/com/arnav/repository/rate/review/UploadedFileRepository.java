package com.arnav.repository.rate.review;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.CrudRepository;

import com.arnav.model.rate.review.UploadedFile;

public interface UploadedFileRepository extends MongoRepository<UploadedFile, String>, CrudRepository<UploadedFile, String>{

}
