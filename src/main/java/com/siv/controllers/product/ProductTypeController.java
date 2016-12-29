package com.siv.controllers.product;

import java.util.Arrays;
import java.util.List;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import org.springframework.web.bind.annotation.PathVariable;
import com.siv.model.product.ProductType;

@Path("/product-type")
public class ProductTypeController {
	
	@GET
	@Produces
	public List<ProductType> findAll() {
		return Arrays.asList(ProductType.values());
	}
	
	@GET
	@Produces
	@Path("/{id}")
	public ProductType findOne(@PathVariable(value="id") String id) {
		return ProductType.valueOf(id);
	}

}
