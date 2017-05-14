package com.arnav.controllers.question;

import java.util.Date;

import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;

import com.arnav.exceptions.AllPropertyRequiredException;
import com.arnav.model.question.Question;
import com.arnav.repository.question.QuestionRepository;

@Path("/secured/question")
public class QuestionController {

	@Autowired
	private QuestionRepository questionRepository;
	
	@POST
	@Produces("application/json")
	public Question create(Question question){
		
		if(question.getTitle() == null || question.getDescription() == null || question.getType() == null){
			throw new AllPropertyRequiredException("Title or description or Type are requied.");
		}
		if(question.getIsDefault() == null){
			question.setIsDefault(false);
		}
		question.setCreateDate(new Date());
		question.setLastUpdate(new Date());
				
		return questionRepository.save(question);		
	}
	
	@GET
	@Path("/{id}")
	@Produces("application/json")
	public Question findOne(@PathParam("id") String id){
		return questionRepository.findOne(id);
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Page<Question> findAll(Pageable pageble,@DefaultValue("0") @QueryParam("page") int page,
			@DefaultValue("id") @QueryParam("sort") String sort, 
			@DefaultValue("20") @QueryParam("size") int size){
		
		final PageRequest page1 = new PageRequest(
				  page, size, new Sort(
						    new Order(sort.equals("id")? Direction.DESC : Direction.ASC, sort)));
		return questionRepository.findAll(page1);
	}
	
	@PUT
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Question update(@PathParam(value="id")String id, Question question){
		Question preQuestion = questionRepository.findOne(id);
		question.setId(id);
		question.setCreateDate(preQuestion.getCreateDate());
		question.setLastUpdate(new Date());
		
		if(question.getTitle() == null) {
			question.setTitle(preQuestion.getTitle());
		} if(question.getDescription() == null){
			question.setDescription(preQuestion.getDescription());
		} if(question.getType() == null) {
			question.setType(preQuestion.getType());
		} if(question.getIsDefault() == null){
			question.setIsDefault(preQuestion.getIsDefault());
		}
		
		return questionRepository.save(question);
	}
	
	@DELETE
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Question delete(@PathParam(value="id")String id){
		Question question = questionRepository.findOne(id);
		questionRepository.delete(question);
		return question;
	}
	
	@GET
	@Path("/type/{type}")
	@Produces(MediaType.APPLICATION_JSON)
	public Page<Question> findByType(@PathParam(value="type")String type, Pageable pageble,
			@DefaultValue("0") @QueryParam("page") int page,
			@DefaultValue("id") @QueryParam("sort") String sort, 
			@DefaultValue("20") @QueryParam("size") int size){
		
		final PageRequest page1 = new PageRequest(
				  page, size, new Sort(
						    new Order(sort.equals("id")? Direction.DESC : Direction.ASC, sort)));
		return new PageImpl<Question>(questionRepository.findByType(type), page1, size);
		
	}
}
