package com.siv.controllers.payment;

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
import com.siv.model.payment.BankDetails;
import com.siv.repository.payment.BankDetailsRepository;

@Path("/bank-detail")
public class BankDetailsController {
	
	@Autowired
	private BankDetailsRepository bankDetailsRepository;
	
	@POST
	@Produces("application/json")
	public BankDetails create(BankDetails bankDetails){
		
		if(!(bankDetails.getAccountNumber() !=null && bankDetails.getBankName() != null
				&& bankDetails.getBeneficiaryAddress() != null
				&& bankDetails.getBeneficiaryName() != null
				&& bankDetails.getBranchNumber() != null)) {
			throw new AllPropertyRequiredException("Please fill all the information.");
		}
		
		bankDetails.setCreateDate(new Date());
		bankDetails.setLastUpdate(new Date());		
		return bankDetailsRepository.save(bankDetails);		
	}
	
	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public BankDetails findOne(@PathParam(value="id")String id){
		return bankDetailsRepository.findOne(id);
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Page<BankDetails> findAll(Pageable pageble){
		return bankDetailsRepository.findAll(pageble);
	}
	
	@PUT
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public BankDetails update(@PathParam(value="id")String id, BankDetails bankDetails){
		BankDetails preDetail = bankDetailsRepository.findOne(id);
		bankDetails.setId(id);
		bankDetails.setCreateDate(preDetail.getCreateDate());
		bankDetails.setLastUpdate(new Date());
		
		if(bankDetails.getAccountNumber() == null) {
			bankDetails.setAccountNumber(preDetail.getAccountNumber());
		} if(bankDetails.getBankName() == null) {
			bankDetails.setBankName(preDetail.getBankName());
		} if(bankDetails.getBeneficiaryAddress() == null) {
			bankDetails.setBeneficiaryAddress(preDetail.getBeneficiaryAddress());
		} if(bankDetails.getBeneficiaryName() == null) {
			bankDetails.setBeneficiaryName(preDetail.getBeneficiaryName());
		} if(bankDetails.getBranchNumber() == null) {
			bankDetails.setBranchNumber(preDetail.getBranchNumber());
		}
		
		return bankDetailsRepository.save(bankDetails);
	}
	
	@DELETE
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public BankDetails delete(@PathParam(value="id")String id){
		BankDetails paymentDetails = bankDetailsRepository.findOne(id);
		bankDetailsRepository.delete(paymentDetails);
		return paymentDetails;
	}

}
