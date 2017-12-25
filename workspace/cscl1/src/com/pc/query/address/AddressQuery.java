package com.pc.query.address;

import com.pc.query.aBase.BaseQuery;

public class AddressQuery extends BaseQuery {
	
	private static final long serialVersionUID = -1169634852793157390L;
	
	
	private java.lang.Integer id;
	private java.lang.Integer userRoleId;
	private java.lang.String addressName;
	private java.lang.String linkMan;
	private java.lang.String linkPhone;
	private java.lang.Integer type;
	
	public java.lang.Integer getId() {
		return id;
	}
	public void setId(java.lang.Integer id) {
		this.id = id;
	}
	
	public java.lang.Integer getUserRoleId() {
		return userRoleId;
	}
	public void setUserRoleId(java.lang.Integer userRoleId) {
		this.userRoleId = userRoleId;
	}
	public java.lang.String getAddressName() {
		return addressName;
	}
	public void setAddressName(java.lang.String addressName) {
		this.addressName = addressName;
	}
	public java.lang.String getLinkMan() {
		return linkMan;
	}
	public void setLinkMan(java.lang.String linkMan) {
		this.linkMan = linkMan;
	}
	public java.lang.String getLinkPhone() {
		return linkPhone;
	}
	public void setLinkPhone(java.lang.String linkPhone) {
		this.linkPhone = linkPhone;
	}
	public java.lang.Integer getType() {
		return type;
	}
	public void setType(java.lang.Integer type) {
		this.type = type;
	}
	
	
}
