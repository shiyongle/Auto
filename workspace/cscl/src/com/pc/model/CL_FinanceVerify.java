package com.pc.model;

import cn.org.rapid_framework.util.DateConvertUtils;

/**
 * @author Cici
 * 收款表
 */

public class CL_FinanceVerify {

	private java.lang.Integer fid;
	private java.lang.Integer freceiptId;//收款表ID
	private java.lang.Integer fbillId;//账单表ID
	private java.math.BigDecimal famount;//金额
	private java.util.Date fcreateTime;//创建时间
	private java.lang.Integer fcreatorId;//创建人ID
	
	public static final String FORMAT_CREATE_TIME = "yyyy-MM-dd HH:mm";
	
//	private String fcreatorname;

	public String getCreateTimeString() {
		return DateConvertUtils.format(getFcreateTime(), FORMAT_CREATE_TIME);
	}
	public void setCreateTimeString(String value) {
		setFcreateTime(DateConvertUtils.parse(value, FORMAT_CREATE_TIME,java.util.Date.class));
	}
	
	public java.lang.Integer getFid() {
		return fid;
	}
	public void setFid(java.lang.Integer fid) {
		this.fid = fid;
	}
	public java.util.Date getFcreateTime() {
		return fcreateTime;
	}
	public void setFcreateTime(java.util.Date fcreateTime) {
		this.fcreateTime = fcreateTime;
	}
	public java.math.BigDecimal getFamount() {
		return famount;
	}
	public void setFamount(java.math.BigDecimal famount) {
		this.famount = famount;
	}
	public java.lang.Integer getFreceiptId() {
		return freceiptId;
	}
	public void setFreceiptId(java.lang.Integer freceiptId) {
		this.freceiptId = freceiptId;
	}
	public java.lang.Integer getFbillId() {
		return fbillId;
	}
	public void setFbillId(java.lang.Integer fbillId) {
		this.fbillId = fbillId;
	}
	public java.lang.Integer getFcreatorId() {
		return fcreatorId;
	}
	public void setFcreatorId(java.lang.Integer fcreatorId) {
		this.fcreatorId = fcreatorId;
	}
	
}
