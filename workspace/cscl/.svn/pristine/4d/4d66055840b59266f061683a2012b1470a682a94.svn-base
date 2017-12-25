package com.pc.model;

import cn.org.rapid_framework.util.DateConvertUtils;

/**
 * @author Cici
 */

public class CL_Finance {

	private java.lang.Integer fid;
	private int fuserId;//司机ID
	private String number;//提现单号
	private String fhandlerId;//处理人ID
	private java.util.Date fcreateTime;//申请时间
	private java.math.BigDecimal famount;//申请金额
	private java.lang.Integer fwithdrawType;//提现方式1支付宝2银行
	private String falipayId;//支付宝帐号
	private String fbankAccount;//银行卡帐号
	private String fbankName;//开户行
	private String fbankAddress;//开户行所在地
	private java.util.Date fpaymentTime;//支付时间
	private String ftreatment;//处理方式
	private String fserialNum;//支付流水号
	private java.lang.Integer fstate;//申请状态0待处理1成功2驳回
	private java.lang.Integer frejectType;//驳回理由
	
	public static final String FORMAT_CREATE_TIME = "yyyy-MM-dd HH:mm";
	public static final String FORMAT_PAYMENT_TIME = "yyyy-MM-dd HH:mm:ss";
	
	private String fusername;
	private String fhandlername;

	public String getFusername() {
		return fusername;
	}
	public void setFusername(String fusername) {
		this.fusername = fusername;
	}
	public String getFhandlername() {
		return fhandlername;
	}
	public void setFhandlername(String fhandlername) {
		this.fhandlername = fhandlername;
	}
	public java.lang.Integer getFrejectType() {
		return frejectType;
	}
	public void setFrejectType(java.lang.Integer frejectType) {
		this.frejectType = frejectType;
	}
	public String getCreateTimeString() {
		return DateConvertUtils.format(getFcreateTime(), FORMAT_CREATE_TIME);
	}
	public void setCreateTimeString(String value) {
		setFcreateTime(DateConvertUtils.parse(value, FORMAT_CREATE_TIME,java.util.Date.class));
	}
	public String getPaymentTimeString() {
		return DateConvertUtils.format(getFpaymentTime(), FORMAT_PAYMENT_TIME);
	}
	public void setPaymentTimeString(String value) {
		if(value!=null)
		{
			setFpaymentTime(DateConvertUtils.parse(value, FORMAT_PAYMENT_TIME,java.util.Date.class));
		}
	}
	
	public java.lang.Integer getFid() {
		return fid;
	}
	public void setFid(java.lang.Integer fid) {
		this.fid = fid;
	}
	public int getFuserId() {
		return fuserId;
	}
	public void setFuserId(int fuserId) {
		this.fuserId = fuserId;
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public String getFhandlerId() {
		return fhandlerId;
	}
	public void setFhandlerId(String fhandlerId) {
		this.fhandlerId = fhandlerId;
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
	public java.lang.Integer getFwithdrawType() {
		return fwithdrawType;
	}
	public void setFwithdrawType(java.lang.Integer fwithdrawType) {
		this.fwithdrawType = fwithdrawType;
	}
	public String getFalipayId() {
		return falipayId;
	}
	public void setFalipayId(String falipayId) {
		this.falipayId = falipayId;
	}
	public String getFbankAccount() {
		return fbankAccount;
	}
	public void setFbankAccount(String fbankAccount) {
		this.fbankAccount = fbankAccount;
	}
	public String getFbankName() {
		return fbankName;
	}
	public void setFbankName(String fbankName) {
		this.fbankName = fbankName;
	}
	public String getFbankAddress() {
		return fbankAddress;
	}
	public void setFbankAddress(String fbankAddress) {
		this.fbankAddress = fbankAddress;
	}
	public java.util.Date getFpaymentTime() {
		return fpaymentTime;
	}
	public void setFpaymentTime(java.util.Date fpaymentTime) {
			this.fpaymentTime = fpaymentTime;
	}
	public String getFtreatment() {
		return ftreatment;
	}
	public void setFtreatment(String ftreatment) {
		this.ftreatment = ftreatment;
	}
	public String getFserialNum() {
		return fserialNum;
	}
	public void setFserialNum(String fserialNum) {
		this.fserialNum = fserialNum;
	}
	public java.lang.Integer getFstate() {
		return fstate;
	}
	public void setFstate(java.lang.Integer fstate) {
		this.fstate = fstate;
	}
}
