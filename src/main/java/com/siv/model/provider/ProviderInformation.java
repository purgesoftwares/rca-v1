package com.siv.model.provider;

import java.util.Date;

import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Id;

public class ProviderInformation {
	
	@Id
	private String id;
	
	@NotNull(message="Product description is required.")
	private String typesOfProductDetail;
	
	private String placeInfo;
	
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

	public String getTypesOfProductDetail() {
		return typesOfProductDetail;
	}

	public void setTypesOfProductDetail(String typesOfProductDetail) {
		this.typesOfProductDetail = typesOfProductDetail;
	}

	public String getPlaceInfo() {
		return placeInfo;
	}

	public void setPlaceInfo(String placeInfo) {
		this.placeInfo = placeInfo;
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
