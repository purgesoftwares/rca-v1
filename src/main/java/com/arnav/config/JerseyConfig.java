package com.arnav.config;

import javax.annotation.PostConstruct;

import com.arnav.controller.rate.review.RateReviewController;
import com.arnav.controller.rate.review.UploadedFileController;
import com.arnav.controllers.coupon.*;
import com.arnav.controllers.provider.ExcludedTimeController;
import com.arnav.controllers.provider.ProviderController;
import com.arnav.oauth.OauthConfigurationController;
import com.arnav.repository.opening.ExcludedTimeRepository;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.mvc.jsp.JspMvcFeature;
import org.springframework.stereotype.Component;

import com.arnav.controllers.address.AddressController;
import com.arnav.controllers.city.CityController;
import com.arnav.controllers.country.CountryController;
import com.arnav.controllers.customer.CustomerController;
import com.arnav.controllers.enquiry.ContactUsEnquiryController;
import com.arnav.controllers.opening.OpeningDaysController;
import com.arnav.controllers.pages.CMSPagesController;
import com.arnav.controllers.payment.BankDetailsController;
import com.arnav.controllers.payment.PaymentDetailsController;
import com.arnav.controllers.product.ProductCategoryController;
import com.arnav.controllers.product.ProductController;
import com.arnav.controllers.provider.ProviderInformationController;
import com.arnav.controllers.question.QuestionController;
import com.arnav.controllers.user.UserController;
import com.arnav.controllers.product.ProductTypesController;
import com.arnav.exception.mapper.NoCurrentProviderMapper;
import com.arnav.exception.mapper.PasswordDidNotMatchMapper;
import com.arnav.exception.mapper.RequestedIdIsNotExistsMapper;
import com.arnav.exception.mapper.UserNotFoundMapper;
import com.arnav.exception.mapper.UsernameIsNotAnEmailMapper;
import com.arnav.exceptions.AllPropertyRequriedMapper;
import com.arnav.exceptions.DuplicateKeyFoundMapper;
import com.arnav.publicapi.ProviderSignupController;
import com.arnav.publicapi.PublicApiController;
import com.arnav.publicapi.PublicApiCustomerController;
import com.arnav.publicapi.PageController;
import com.arnav.publicapi.PublicCustomerApiController;

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
        		ProviderInformationController.class,
        		PaymentDetailsController.class,
        		ProviderController.class,
        		OpeningDaysController.class,
        		CollectedCouponController.class,
        		CROSFilter.class,
        		PublicApiController.class,
        		CollectedCouponController.class,
        		BankDetailsController.class,
				ExcludedTimeController.class,
				ExcludedTimeRepository.class,
        		DuplicateKeyFoundMapper.class,
        		ProductCategoryController.class,
        		CMSPagesController.class,
        		ContactUsEnquiryController.class,
        		PublicCustomerApiController.class,
        		AllPropertyRequriedMapper.class,
        		UsernameIsNotAnEmailMapper.class,
        		PasswordDidNotMatchMapper.class,
        		NoCurrentProviderMapper.class,
        		RequestedIdIsNotExistsMapper.class,
        		UserNotFoundMapper.class,
        		CountryController.class,
        		CityController.class,
        		ProviderSignupController.class,
				OauthConfigurationController.class,
				QuestionController.class,
				PublicApiCustomerController.class,
				PageController.class,
				CouponPackageController.class,
				PurchasedCouponController.class,
				JoinedFriendController.class,
				ProductTypesController.class,
				RateReviewController.class,
				UploadedFileController.class);
        
        register(JspMvcFeature.class);
    }
}