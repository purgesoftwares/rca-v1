package com.arnav.controllers.coupon;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.arnav.exceptions.CustomNotFoundException;
import com.arnav.exceptions.NoCurrentProviderException;
import com.arnav.model.coupon.ProviderCollectedCouponResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.arnav.model.coupon.CollectedCouponResponse;
import com.arnav.model.coupon.Coupon;
import com.arnav.model.coupon.CollectedCoupon;
import com.arnav.repository.coupon.CollectedCouponRepository;
import com.arnav.repository.coupon.CouponRepository;
import com.arnav.repository.provider.ProviderRepository;

@Path("/secured/collect-coupon")
public class CollectedCouponController {
	
	@Autowired
	private CouponRepository couponRepository;
	
	@Autowired
	private ProviderRepository providerRepository;
	
	@Autowired
	private CollectedCouponRepository collCouponRepository;
	
	@POST
	@Produces("application/json")
	public CollectedCoupon create(CollectedCoupon collectCoupon) throws CustomNotFoundException {
		Coupon coupon = couponRepository.findByCouponCode(collectCoupon.getCouponCode());
		if(coupon != null){
			collectCoupon.setCreatedAt(new Date());
			collectCoupon.setStatus(true);
			collectCoupon.setPrice(coupon.getPrice());
			collectCoupon =  collCouponRepository.save(collectCoupon);
			if(collectCoupon != null){
				coupon.setAvailability(coupon.getAvailability()-1);
				coupon.setUsed(coupon.getUsed()+1);
				couponRepository.save(coupon);
			}
			return collectCoupon;
		}else{
			throw new CustomNotFoundException("Not a valid Coupon.");
		}
	}

	@POST
	@Path("/check-coupon")
	@Produces("application/json")
	public Response checkCoupon(CollectedCoupon collectedCoupon) throws ParseException,CustomNotFoundException {
		Coupon coupon = couponRepository.findByCouponCode(collectedCoupon.getCouponCode());

		if(coupon != null && coupon.getAvailability()!=0 && coupon.getProviderId().equals(collectedCoupon.getProviderId())){
			return Response.ok(coupon).build();
		}else{
			throw new NoCurrentProviderException("Coupon code is not valid.");

		}
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Page<CollectedCoupon> findAll(Pageable pageble){
		return collCouponRepository.findAll(pageble);
	}
	
	@GET
	@Path("/{couponCode}")
	@Produces(MediaType.APPLICATION_JSON)
	public Coupon getCouponByCode(@PathParam("couponCode")String couponCode){
		return couponRepository.findByCouponCode(couponCode);
	}

	@GET
	@Path("/by-provider/{providerId}")
	@Produces(MediaType.APPLICATION_JSON)
	public ProviderCollectedCouponResponse getCollectedCouponByProviderId(@PathParam("providerId")String providerId){
		ProviderCollectedCouponResponse providerCollectedCouponResponse = new ProviderCollectedCouponResponse();
		providerCollectedCouponResponse.setCollectedCouponList(collCouponRepository.findByProviderId(providerId));
		return providerCollectedCouponResponse;
	}



	@Path("/{id}")
	@PUT
	@Produces("application/json")
	public CollectedCoupon update(@PathParam("id") String id, CollectedCoupon collectCoupon){
		CollectedCoupon previousCollectedCoupon = collCouponRepository.findOne(id);

		if(collectCoupon.getCustomerName()!=null){
			previousCollectedCoupon.setCustomerName(collectCoupon.getCustomerName());
		}
		if(collectCoupon.getFrom()!=null){
			previousCollectedCoupon.setFrom(collectCoupon.getFrom());
		}

		return collCouponRepository.save(previousCollectedCoupon);
	}

	@GET
	@Path("/provider-total-coupons/{providerId}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<CollectedCouponResponse> findCollectedCouponByProviderId(@PathParam("providerId")String providerId) throws ParseException{

		List<Coupon> collectedCoupons = couponRepository.
				findByUsedAndProviderId(1, providerId);

		Map<String,BigDecimal> data = new TreeMap<String,BigDecimal>();
		Map<String,Integer> couponNumbers = new TreeMap<String,Integer>();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

		for(Coupon collectedCoupon : collectedCoupons){
			Date createDate = collectedCoupon.getCollectionDate();

			if(!data.containsKey(dateFormat.format(createDate))) {
				data.put(dateFormat.format(createDate), BigDecimal.ZERO);
				couponNumbers.put(dateFormat.format(createDate), 0);
			}

			BigDecimal total = data.get(dateFormat.format(createDate));
			Integer number = couponNumbers.get(dateFormat.format(createDate));

			data.put(dateFormat.format(createDate), total.add(collectedCoupon.getPrice()));
			couponNumbers.put(dateFormat.format(createDate), number + 1);

		}

		List<CollectedCouponResponse> allCollectedCoupons = new ArrayList<CollectedCouponResponse>();


		for (Object key : data.keySet()) {

			CollectedCouponResponse response = new CollectedCouponResponse();
			response.setAmount(data.get(key));
			response.setDate(key.toString());
			response.setNumOfCoupons(couponNumbers.get(key));

			allCollectedCoupons.add(response);

		}
		return allCollectedCoupons;
	}
}
