package com.siv.controllers.product;

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
import com.siv.model.product.ProductCategory;
import com.siv.repository.product.ProductCategoryRepository;

@Path("/secured/product-category")
public class ProductCategoryController {
	
	@Autowired
	private ProductCategoryRepository productCategoryRepository;
	
	@POST
	@Produces("application/json")
	public ProductCategory create(ProductCategory product){
		
		if(product.getName() == null){
			throw new AllPropertyRequiredException("Produt category name is required.");
		}
		return productCategoryRepository.save(product);		
	}
	
	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public ProductCategory findOne(@PathParam(value="id")String id){
		return productCategoryRepository.findOne(id);
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Page<ProductCategory> findAll(Pageable pageble){
		return productCategoryRepository.findAll(pageble);
	}
	
	@PUT
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public ProductCategory update(@PathParam(value="id")String id, ProductCategory product){
		ProductCategory preProduct = productCategoryRepository.findOne(id);
		product.setId(preProduct.getId());
		
		if(product.getName() == null) {
			product.setName(preProduct.getName());
		} if(product.getDescription() == null){
			product.setDescription(preProduct.getDescription());
		}
		return productCategoryRepository.save(product);
	}
	
	@DELETE
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public ProductCategory delete(@PathParam(value="id")String id){
		ProductCategory product = productCategoryRepository.findOne(id);
		productCategoryRepository.delete(product);
		return product;
	}

}
