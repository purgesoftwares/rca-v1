package com.siv.model.payment;

import java.util.Date;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Email;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class PaymentDetails {
	
	@Id
	private String id;
	
	@Email
	@NotNull(message="Username should not be blank")
	private String username;
	
	@NotNull(message="Address should not be blank")
	private String address;
	
	@NotNull(message="Bank name should not be blank")
	private String bankName;
	
	@NotNull(message="Bank Code should not be blank")
	private Long bankCode;
	
	@NotNull(message="Account Number should not be blank")
	private String accountNumber;
	
	@NotNull(message="Payment Type should not be blank")
	private PaymentType paymentType;
	
	@NotNull
	private Date createDate;
	
	@NotNull
	private Date lastUpdate;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public Long getBankCode() {
		return bankCode;
	}

	public void setBankCode(Long bankCode) {
		this.bankCode = bankCode;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public PaymentType getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(PaymentType paymentType) {
		this.paymentType = paymentType;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getLastUpdate() {
		return lastUpdate;
	}

	public void setLastUpdate(Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}
	

}
