package com.siv.controllers.pages;

import java.util.Date;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.siv.exceptions.AllPropertyRequiredException;
import com.siv.model.pages.CMSPages;
import com.siv.repository.pages.CMSPagesRepository;

@Path("/secured/cms-pages")
public class CMSPagesController {
	
	@Autowired
	private CMSPagesRepository cmsRepository;
	
	@POST
	@Produces("application/json")
	public CMSPages create(CMSPages pages){
		
		if(pages.getTitle() == null || pages.getStatus() == null || pages.getContent() == null){
			throw new AllPropertyRequiredException("Title ,Context text and status is required.");
		}
		pages.setCreateDate(new Date());
		pages.setLastUpdate(new Date());
		return cmsRepository.save(pages);		
	}
	
	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public CMSPages findOne(@PathParam(value="id")String id){
		return cmsRepository.findOne(id);
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Page<CMSPages> findAll(Pageable pageble){
		return cmsRepository.findAll(pageble);
	}
	
	@PUT
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public CMSPages update(@PathParam(value="id")String id, CMSPages pages){
		CMSPages preProduct = cmsRepository.findOne(id);
		pages.setId(preProduct.getId());
		pages.setCreateDate(preProduct.getCreateDate());
		pages.setLastUpdate(new Date());
		
		if(pages.getTitle() == null) {
			pages.setTitle(preProduct.getTitle());
		} if(pages.getStatus() == null){
			pages.setStatus(preProduct.getStatus());
		} if(pages.getContent() == null) {
			pages.setContent(preProduct.getContent());
		}
		return cmsRepository.save(pages);
	}
	
	@DELETE
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public CMSPages delete(@PathParam(value="id")String id){
		CMSPages product = cmsRepository.findOne(id);
		cmsRepository.delete(product);
		return product;
	}

}
