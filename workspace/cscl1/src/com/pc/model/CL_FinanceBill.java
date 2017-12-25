package com.pc.model;

import cn.org.rapid_framework.util.DateConvertUtils;

public class CL_FinanceBill {
	private java.lang.Integer fid;
	private java.util.Date fbillDate;//账单日期
	private java.lang.Integer fcustId;//用户ID
	private String fcustName;//用户名称
	private java.math.BigDecimal fbillAmount;//账单金额
	private java.math.BigDecimal funPayAmount;//未付金额
	private boolean fverification;//核销状态
	public static final String FORMAT_BILL_DATE = "yyyy-MM-dd";
	public java.lang.Integer getFid() {
		return fid;
	}
	public void setFid(java.lang.Integer fid) {
		this.fid = fid;
	}
	public java.util.Date getFbillDate() {
		return fbillDate;
	}
	public void setFbillDate(java.util.Date fbillDate) {
		this.fbillDate = fbillDate;
	}
	public java.lang.Integer getFcustId() {
		return fcustId;
	}
	public void setFcustId(java.lang.Integer fcustId) {
		this.fcustId = fcustId;
	}
	public String getFcustName() {
		return fcustName;
	}
	public void setFcustName(String fcustName) {
		this.fcustName = fcustName;
	}
	public java.math.BigDecimal getFbillAmount() {
		return fbillAmount;
	}
	public void setFbillAmount(java.math.BigDecimal fbillAmount) {
		this.fbillAmount = fbillAmount;
	}
	public java.math.BigDecimal getFunPayAmount() {
		return funPayAmount;
	}
	public void setFunPayAmount(java.math.BigDecimal funPayAmount) {
		this.funPayAmount = funPayAmount;
	}
	public boolean isFverification() {
		return fverification;
	}
	public void setFverification(boolean fverification) {
		this.fverification = fverification;
	}
	public String getBillDateString() {
		return DateConvertUtils.format(getFbillDate(), FORMAT_BILL_DATE);
	}
	public void setBillDateString(String value) {
		setFbillDate(DateConvertUtils.parse(value, FORMAT_BILL_DATE,java.util.Date.class));
	}
}
