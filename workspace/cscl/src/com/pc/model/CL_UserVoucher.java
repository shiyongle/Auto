package com.pc.model;

public class CL_UserVoucher {
	private java.lang.Integer fid;
	private java.lang.Integer fcreator;
	private java.util.Date fcreatorTime;
	private java.lang.String number;
	private java.lang.Integer fstatus;
	private java.math.BigDecimal fmoeny;
    private java.lang.Integer fisover;// 0未过期 1过期
    private java.lang.Integer ftype;//1.微信  2,支付宝  3,银联
    private java.lang.String fbankName;//银行卡名称      
    private java.lang.String fdata;//保存李阔 用银行卡充值的信息
    private java.lang.Integer fpayorRecharge;// 1充值 2支付;
    private java.lang.String forderNumber;// 订单编号
    public java.lang.Integer getFpayorRecharge() {
		return fpayorRecharge;
	}
	public void setFpayorRecharge(java.lang.Integer fpayorRecharge) {
		this.fpayorRecharge = fpayorRecharge;
	}
	public java.lang.String getForderNumber() {
		return forderNumber;
	}
	public void setForderNumber(java.lang.String forderNumber) {
		this.forderNumber = forderNumber;
	}
	public java.lang.String getFdata() {
		return fdata;
	}
	public void setFdata(java.lang.String fdata) {
		this.fdata = fdata;
	}
	public java.lang.String getFbankName() {
		return fbankName;
	}
	public void setFbankName(java.lang.String fbankName) {
		this.fbankName = fbankName;
	}
	//============非数据字段===//
    private  java.lang.String vmiUserPhone;//用户手机号码
 
	public java.lang.String getVmiUserPhone() {
		return vmiUserPhone;
	}
	public void setVmiUserPhone(java.lang.String vmiUserPhone) {
		this.vmiUserPhone = vmiUserPhone;
	}
	public java.lang.Integer getFid() {
		return fid;
	}
	public void setFid(java.lang.Integer fid) {
		this.fid = fid;
	}
	public java.lang.Integer getFisover() {
		return fisover;
	}
	public void setFisover(java.lang.Integer fisover) {
		this.fisover = fisover;
	}
	public java.lang.Integer getFtype() {
		return ftype;
	}
	public void setFtype(java.lang.Integer ftype) {
		this.ftype = ftype;
	}
	public java.lang.Integer getFcreator() {
		return fcreator;
	}
	public void setFcreator(java.lang.Integer fcreator) {
		this.fcreator = fcreator;
	}
	public java.util.Date getFcreatorTime() {
		return fcreatorTime;
	}
	public void setFcreatorTime(java.util.Date fcreatorTime) {
		this.fcreatorTime = fcreatorTime;
	}
	 
	public java.lang.String getNumber() {
		return number;
	}
	public void setNumber(java.lang.String number) {
		this.number = number;
	}
	public java.lang.Integer getFstatus() {
		return fstatus;
	}
	public void setFstatus(java.lang.Integer fstatus) {
		this.fstatus = fstatus;
	}
	public java.math.BigDecimal getFmoeny() {
		return fmoeny;
	}
 
	public void setFmoeny(java.math.BigDecimal fmoeny) {
		this.fmoeny = fmoeny;
	}
 
}
