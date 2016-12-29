package com.siv.config;

import javax.annotation.PostConstruct;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.mvc.jsp.JspMvcFeature;
import org.glassfish.jersey.servlet.ServletProperties;
import org.springframework.stereotype.Component;

import com.siv.controllers.address.AddressController;
import com.siv.controllers.coupon.CouponController;
import com.siv.controllers.customer.CustomerController;
import com.siv.controllers.payment.PaymentDetailsController;
import com.siv.controllers.product.ProductController;
import com.siv.controllers.product.ProductTypeController;
import com.siv.controllers.provider.ProviderInformationController;
import com.siv.controllers.user.UserController;

@Component
public class JerseyConfig extends ResourceConfig {

    @PostConstruct
    private void init() {
        registerClasses(
        		UserController.class,  
        		AddressController.class,
        		CustomerController.class,
        		CouponController.class,
        		ProductController.class,
        		ProductTypeController.class,
        		ProviderInformationController.class,
        		PaymentDetailsController.class);
        
        register(JspMvcFeature.class);
        property(JspMvcFeature.TEMPLATES_BASE_PATH, "/WEB-INF/jsp");
        property(ServletProperties.FILTER_FORWARD_ON_404, true);
    }
}