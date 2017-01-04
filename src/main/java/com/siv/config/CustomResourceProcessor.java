/*package com.siv.config;

import org.springframework.data.rest.webmvc.RepositoryLinksResource;
import org.springframework.hateoas.ResourceProcessor;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.stereotype.Component;

import com.siv.controllers.coupon.CouponController;

@Component
public class CustomResourceProcessor implements ResourceProcessor<RepositoryLinksResource> {
	
	 @Override
	 public RepositoryLinksResource process(RepositoryLinksResource resource) {
	        resource.add(ControllerLinkBuilder.linkTo(CouponController.class).withRel("c"));
	        return resource;
	 }

}
*/