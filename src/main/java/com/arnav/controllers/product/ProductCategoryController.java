package com.arnav.controllers.product;

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
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;

import com.arnav.exceptions.AllPropertyRequiredException;
import com.arnav.model.product.ProductCategory;
import com.arnav.repository.product.ProductCategoryRepository;

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
	public Page<ProductCategory> findAll(Pageable pageble,@DefaultValue("0") @QueryParam("page") int page,
			@DefaultValue("id") @QueryParam("sort") String sort, 
			@DefaultValue("20") @QueryParam("size") int size){
		final PageRequest page1 = new PageRequest(
				  page, size, new Sort(
						    new Order(sort.equals("id")? Direction.DESC : Direction.ASC, sort)));
		return productCategoryRepository.findAll(page1);
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
		} if(product.getStatus() == null){
			product.setStatus(preProduct.getStatus());
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
