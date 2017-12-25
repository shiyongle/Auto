package com.pc.model;

import java.util.Date;

import cn.org.rapid_framework.util.DateConvertUtils;


public class CL_OrderDetail implements java.io.Serializable {
	private static final long serialVersionUID = 5454155825314635342L;
	public static final String FORMAT_CREATE_TIME = "yyyy-MM-dd HH:mm:ss";
	
	//alias
	public static final String TABLE_ALIAS = "订单明细表";
	public static final String ALIAS_ID = "主键";
	public static final String ALIAS_ORDER_ID = "订单表id";
	public static final String ALIAS_DETAIL_TYPE = "明细类型ID 1，发货人  2，收货方";
	public static final String ALIAS_LINKMAN = "联系人";
	public static final String ALIAS_PHONE = "联系人电话";
	public static final String ALIAS_ADDRESS_NAME = "地址名称";
	public static final String ALIAS_SNUMBER = "地址序号";
	public static final String ALIAS_CODE="6位随机数验证码";
	public static final String ALIAS_PASS="是否通过验证";
	public static final String ALIAS_LONGITUDE="经度";
	public static final String ALIAS_LATITUDE="维度";
	
	
	//date formats
	
	//columns START
	private java.lang.Integer id;
	private java.lang.Integer orderId;
	private java.lang.Integer detailType;
	private java.lang.String linkman;
	private java.lang.String phone;
	private java.lang.String addressName;
	private java.lang.String addressId;
	private java.lang.Integer snumber;
	private java.lang.String securityCode;
	private java.lang.Integer  pass;
	private java.lang.String  longitude;
	private java.lang.String  latitude;
	private Integer fstatus;//提货卸货，确认与否  0否1是
	private Date farrived_time;
	
	
	//columns END
	//前端展示字段
	private Integer orderDetailId;//早起id用来存地址id了
	
	
	public Date getFarrived_time() {
		return farrived_time;
	}

	public void setFarrived_time(Date farrived_time) {
		this.farrived_time = farrived_time;
	}

	public Integer getOrderDetailId() {
		return orderDetailId;
	}

	public void setOrderDetailId(Integer orderDetailId) {
		this.orderDetailId = orderDetailId;
	}

	public java.lang.String getSecurityCode() {
		return securityCode;
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

	public void setSecurityCode(java.lang.String securityCode) {
		this.securityCode = securityCode;
	}

	public java.lang.Integer getPass() {
		return pass;
	}

	public void setPass(java.lang.Integer pass) {
		this.pass = pass;
	}

	public java.lang.Integer getSnumber() {
		return snumber;
	}

	public void setSnumber(java.lang.Integer snumber) {
		this.snumber = snumber;
	}

	public CL_OrderDetail(){}

	public CL_OrderDetail(java.lang.Integer id){
		this.id = id;
	}

	public void setId(java.lang.Integer value) {
		this.id = value;
	}
	
	public java.lang.Integer getId() {
		return this.id;
	}
	public void setOrderId(java.lang.Integer value) {
		this.orderId = value;
	}
	
	public java.lang.Integer getOrderId() {
		return this.orderId;
	}
	public void setDetailType(java.lang.Integer value) {
		this.detailType = value;
	}
	
	public java.lang.Integer getDetailType() {
		return this.detailType;
	}
	public void setLinkman(java.lang.String value) {
		this.linkman = value;
	}
	
	public java.lang.String getLinkman() {
		return this.linkman;
	}
	public void setPhone(java.lang.String value) {
		this.phone = value;
	}
	
	public java.lang.String getPhone() {
		return this.phone;
	}
	public void setAddressName(java.lang.String value) {
		this.addressName = value;
	}
	
	public java.lang.String getAddressName() {
		return this.addressName;
	}

	public java.lang.String getAddressId() {
		return addressId;
	}

	public void setAddressId(java.lang.String addressId) {
		this.addressId = addressId;
	}

	public Integer getFstatus() {
		return fstatus;
	}

	public void setFstatus(Integer fstatus) {
		this.fstatus = fstatus;
	}
	
	public String getFarrivedTimeString() {
		return DateConvertUtils.format(getFarrived_time(), FORMAT_CREATE_TIME);
	}
	public void setFarrivedTimeString(String value) {
		setFarrived_time(DateConvertUtils.parse(value, FORMAT_CREATE_TIME,java.util.Date.class));
	}
}

