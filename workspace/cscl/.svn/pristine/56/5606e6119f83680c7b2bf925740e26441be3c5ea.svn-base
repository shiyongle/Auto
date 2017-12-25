package com.pc.model;

import cn.org.rapid_framework.util.DateConvertUtils;

public class CL_OrderCarDetail implements java.io.Serializable {
	private static final long serialVersionUID = 5454155825314635342L;
	
	//alias
	public static final String TABLE_ALIAS = "ClOrderCarDetail";
	public static final String ALIAS_ID = "id";
	public static final String ALIAS_ORDER_ID = "订单ID关联订单表";
	public static final String ALIAS_CAR_SPEC_ID = "车辆规格ID";
	public static final String ALIAS_CAR_TYPE_ID = "车辆类型id";
	public static final String ALIAS_CAR_OTHER_ID = "其他要求ID";
	public static final String ALIAS_CREATE_TIME = "创建时间";
	
	//date formats
	public static final String FORMAT_CREATE_TIME = "yyyy-MM-dd HH:mm:ss";
	
	//columns START
	private java.lang.Integer id;
	private java.lang.Integer orderId;
	private java.lang.Integer carSpecId;
	private java.lang.Integer carTypeId;
	private java.lang.Integer carOtherId;
	private java.util.Date createTime;
	//columns END

	/*********************非数据字段,前端封装显示数据*******begin************************/
	private java.lang.String or_number; 
	private java.lang.String ctpName; 
	private java.lang.String cspName; 
	private java.lang.String cotherName; 
	 


	public java.lang.String getOr_number() {
		return or_number;
	}

	public void setOr_number(java.lang.String or_number) {
		this.or_number = or_number;
	}

	public java.lang.String getCtpName() {
		return ctpName;
	}

	public void setCtpName(java.lang.String ctpName) {
		this.ctpName = ctpName;
	}

	public java.lang.String getCspName() {
		return cspName;
	}

	public void setCspName(java.lang.String cspName) {
		this.cspName = cspName;
	}

	public java.lang.String getCotherName() {
		return cotherName;
	}

	public void setCotherName(java.lang.String cotherName) {
		this.cotherName = cotherName;
	}

	public CL_OrderCarDetail(){}

	public CL_OrderCarDetail(java.lang.Integer id){
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
	public void setCarSpecId(java.lang.Integer value) {
		this.carSpecId = value;
	}
	
	public java.lang.Integer getCarSpecId() {
		return this.carSpecId;
	}
	public void setCarTypeId(java.lang.Integer value) {
		this.carTypeId = value;
	}
	
	public java.lang.Integer getCarTypeId() {
		return this.carTypeId;
	}
	public void setCarOtherId(java.lang.Integer value) {
		this.carOtherId = value;
	}
	
	public java.lang.Integer getCarOtherId() {
		return this.carOtherId;
	}
	public String getCreateTimeString() {
		return DateConvertUtils.format(getCreateTime(), FORMAT_CREATE_TIME);
	}
	public void setCreateTimeString(String value) {
		setCreateTime(DateConvertUtils.parse(value, FORMAT_CREATE_TIME,java.util.Date.class));
	}
	
	public void setCreateTime(java.util.Date value) {
		this.createTime = value;
	}
	
	public java.util.Date getCreateTime() {
		return this.createTime;
	}

}

