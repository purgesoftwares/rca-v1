package com.arnav.controllers.product;

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

import com.arnav.model.product.ProductTypes;
import com.arnav.repository.product.ProductTypesRepository;

@Path("/secured/product-types")
public class ProductTypesController {
	
	@Autowired
	private ProductTypesRepository productTypesRepository;
	
	@POST
	@Produces("application/json")
	public ProductTypes create(ProductTypes product){
		return productTypesRepository.save(product);		
	}
	
	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public ProductTypes findOne(@PathParam(value="id")String id){
		return productTypesRepository.findOne(id);
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Page<ProductTypes> findAll(Pageable pageble){
		return productTypesRepository.findAll(pageble);
	}
	
	@PUT
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public ProductTypes update(@PathParam(value="id")String id, ProductTypes product){
		ProductTypes preProduct = productTypesRepository.findOne(id);
		product.setId(preProduct.getId());
		
		if(product.getName() == null) {
			product.setName(preProduct.getName());
		} 
		return productTypesRepository.save(product);
	}
	
	@DELETE
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public ProductTypes delete(@PathParam(value="id")String id){
		ProductTypes product = productTypesRepository.findOne(id);
		productTypesRepository.delete(product);
		return product;
	}

}
