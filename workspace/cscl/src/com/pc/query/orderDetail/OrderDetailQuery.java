package com.pc.query.orderDetail;

import java.io.Serializable;
import java.util.Calendar;

import com.pc.query.aBase.BaseQuery;

/*** 订单查询对象*/
public class OrderDetailQuery extends BaseQuery implements Serializable{

	/***
	 *<p>Description: </p>
	 *<p>Company: CPS-TEAM</p> 
	 * @author WANGC
	 * @date 2016-1-25 下午2:37:13
	*/
	private static final long serialVersionUID = 3019571844509462031L;
	
	public java.lang.Integer getId() {
		return id;
	}
	public void setId(java.lang.Integer id) {
		this.id = id;
	}
	public java.lang.Integer getOrderId() {
		return orderId;
	}
	public void setOrderId(java.lang.Integer orderId) {
		this.orderId = orderId;
	}
	public java.lang.Integer getDetailType() {
		return detailType;
	}
	public void setDetailType(java.lang.Integer detailType) {
		this.detailType = detailType;
	}
	public java.lang.String getLinkman() {
		return linkman;
	}
	public void setLinkman(java.lang.String linkman) {
		this.linkman = linkman;
	}
	public java.lang.String getPhone() {
		return phone;
	}
	public void setPhone(java.lang.String phone) {
		this.phone = phone;
	}
	public java.lang.String getAddressName() {
		return addressName;
	}
	public void setAddressName(java.lang.String addressName) {
		this.addressName = addressName;
	}
	public java.lang.Integer getSnumber() {
		return snumber;
	}
	public void setSnumber(java.lang.Integer snumber) {
		this.snumber = snumber;
	}
	private java.lang.Integer id;
	private java.lang.Integer orderId;
	private java.lang.Integer detailType;
	private java.lang.String linkman;
	private java.lang.String phone;
	private java.lang.String addressName;
	private java.lang.Integer snumber;
	private java.lang.String securityCode;
	private java.lang.Integer  pass;
	private java.lang.String  longitude;
	private java.lang.String  latitude;

	public java.lang.String getSecurityCode() {
		return securityCode;
	}
	public void setSecurityCode(java.lang.String securityCode) {
		this.securityCode = securityCode;
	}
	public java.lang.Integer getPass() {
		return pass;
	}
	public void setPass(java.lang.Integer pass) {
		this.pass = pass;
	}
	public java.lang.String getLongitude() {
		return longitude;
	}
	public void setLongitude(java.lang.String longitude) {
		this.longitude = longitude;
	}
	public java.lang.String getLatitude() {
		return latitude;
	}
	public void setLatitude(java.lang.String latitude) {
		this.latitude = latitude;
	}
	
}
