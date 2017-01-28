package com.arnav.repository.question;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.transaction.annotation.Transactional;

import com.arnav.model.question.Question;

@RepositoryRestResource
public interface QuestionRepository extends MongoRepository<Question, String>, CrudRepository<Question, String>, PagingAndSortingRepository<Question, String>{

	@Transactional
	public List<Question> findByType(String type);
}
