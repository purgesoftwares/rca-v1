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
import com.siv.model.product.Product;
import com.siv.repository.product.ProductRepository;

@Path("/secured/product")
public class ProductController {
	
	@Autowired
	private ProductRepository productRepository;
	
	@POST
	@Produces("application/json")
	public Product create(Product product){
		return productRepository.save(product);		
	}
	
	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Product findOne(@PathParam(value="id")String id){
		return productRepository.findOne(id);
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Page<Product> findAll(Pageable pageble){
		return productRepository.findAll(pageble);
	}
	
	@PUT
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Product update(@PathParam(value="id")String id, Product product){
		Product preProduct = productRepository.findOne(id);
		product.setId(preProduct.getId());
		
		if(product.getPrice() == null) {
			product.setPrice(preProduct.getPrice());
		} if(product.getProductName() == null) {
			product.setProductName(preProduct.getProductName());
		} if(product.getProductType() == null) {
			product.setProductType(preProduct.getProductType());
		} if(product.getDescription() == null) {
			product.setDescription(preProduct.getDescription());
		} if(product.getProductCategory() == null){
			product.setProductCategory(preProduct.getProductCategory());
		}
		return productRepository.save(product);
	}
	
	@DELETE
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Product delete(@PathParam(value="id")String id){
		Product product = productRepository.findOne(id);
		productRepository.delete(product);
		return product;
	}

}
