package com.pc.model;

import cn.org.rapid_framework.util.DateConvertUtils;

/**
 * @author Cici
 * 收款表
 */

public class CL_FinanceReceipt {

	private java.lang.Integer fid;
	private java.lang.Integer fuserId;//货主ID
	private java.lang.Integer fcreatorId;//创建人ID
	private java.util.Date fcreateTime;//创建时间
	private java.math.BigDecimal famount;//金额
	private java.lang.Integer fpaymentMethod;//收款方式0承兑汇票1支票2现金
	private java.math.BigDecimal fremainAmount;//未核销金额
	private java.lang.String fremark;//收款备注
	
	public static final String FORMAT_CREATE_TIME = "yyyy-MM-dd HH:mm";
	
	private String fusername;
	private String fcreatorname;

	public String getFusername() {
		return fusername;
	}
	public void setFusername(String fusername) {
		this.fusername = fusername;
	}
	
	public String getCreateTimeString() {
		return DateConvertUtils.format(getFcreateTime(), FORMAT_CREATE_TIME);
	}
	public void setCreateTimeString(String value) {
		setFcreateTime(DateConvertUtils.parse(value, FORMAT_CREATE_TIME,java.util.Date.class));
	}
	
	public java.lang.String getFremark() {
		return fremark;
	}
	public void setFremark(java.lang.String fremark) {
		this.fremark = fremark;
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
	public java.lang.Integer getFcreatorId() {
		return fcreatorId;
	}
	public void setFcreatorId(java.lang.Integer fcreatorId) {
		this.fcreatorId = fcreatorId;
	}
	public java.lang.Integer getFpaymentMethod() {
		return fpaymentMethod;
	}
	public void setFpaymentMethod(java.lang.Integer fpaymentMethod) {
		this.fpaymentMethod = fpaymentMethod;
	}
	public java.math.BigDecimal getFremainAmount() {
		return fremainAmount;
	}
	public void setFremainAmount(java.math.BigDecimal fremainAmount) {
		this.fremainAmount = fremainAmount;
	}
	public String getFcreatorname() {
		return fcreatorname;
	}
	public void setFcreatorname(String fcreatorname) {
		this.fcreatorname = fcreatorname;
	}
	public void setFuserId(java.lang.Integer fuserId) {
		this.fuserId = fuserId;
	}
	public java.lang.Integer getFuserId() {
		return fuserId;
	}
}
