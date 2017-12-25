package com.pc.model;

import java.util.Date;

public class CL_Bank {
	private java.lang.Integer fid;// 主键
	private java.lang.String fbankName;// 银行名称
	private java.lang.String ftel;// 用户手机号码
	private java.lang.String fcardpaytype;// 银行编码
	private java.lang.String fcardNumber;// 银行卡号
	private java.lang.Integer fuserId;// 用户id
	private java.util.Date fcreateTime;// 用户创建时间
	private java.lang.Integer fdefault;// 0非默认 1默认
	private java.lang.Integer feffect;// 1有效0无效
	private java.lang.String fcard;// 用户身份证
	private java.lang.String fname;// 用户姓名
	private java.lang.String number;// 订单编号
	private java.lang.Integer fdel;// 0未删除 1已删除
	private java.lang.Integer fdelfcl_ban_info__id;// 卡关联 银行卡数据

	private java.lang.String ftype;// 订单编号
	private java.lang.String fcreditCardCvn;// 信用卡Cvn(只有当类型为信用卡传输)
	private java.lang.String fcreditCardYear;// 信用卡有效年份(只有当类型为信用卡传输)

	private Date fbankpayTime; // 银行卡支付时间 用于60秒控制
	private String ficon;//银行卡图片
	

	public String getFicon() {
		return ficon;
	}

	public void setFicon(String ficon) {
		this.ficon = ficon;
	}

	public Date getFbankpayTime() {
		return fbankpayTime;
	}

	public void setFbankpayTime(Date fbankpayTime) {
		this.fbankpayTime = fbankpayTime;
	}

	private java.lang.String fcreditCardMonth;// 支付时间

	public java.lang.String getFtype() {
		return ftype;
	}

	public void setFtype(java.lang.String ftype) {
		this.ftype = ftype;
	}

	public java.lang.Integer getFdelfcl_ban_info__id() {
		return fdelfcl_ban_info__id;
	}

	public void setFdelfcl_ban_info__id(java.lang.Integer fdelfcl_ban_info__id) {
		this.fdelfcl_ban_info__id = fdelfcl_ban_info__id;
	}

	public java.lang.String getFcreditCardCvn() {
		return fcreditCardCvn;
	}

	public void setFcreditCardCvn(java.lang.String fcreditCardCvn) {
		this.fcreditCardCvn = fcreditCardCvn;
	}

	public java.lang.String getFcreditCardYear() {
		return fcreditCardYear;
	}

	public void setFcreditCardYear(java.lang.String fcreditCardYear) {
		this.fcreditCardYear = fcreditCardYear;
	}

	public java.lang.String getFcreditCardMonth() {
		return fcreditCardMonth;
	}

	public void setFcreditCardMonth(java.lang.String fcreditCardMonth) {
		this.fcreditCardMonth = fcreditCardMonth;
	}

	public java.lang.Integer getFdel() {
		return fdel;
	}

	public void setFdel(java.lang.Integer fdel) {
		this.fdel = fdel;
	}

	public java.lang.String getNumber() {
		return number;
	}

	public void setNumber(java.lang.String number) {
		this.number = number;
	}

	public java.lang.Integer getFid() {
		return fid;
	}

	public void setFid(java.lang.Integer fid) {
		this.fid = fid;
	}

	public java.lang.String getFbankName() {
		return fbankName;
	}

	public void setFbankName(java.lang.String fbankName) {
		this.fbankName = fbankName;
	}

	public java.lang.String getFtel() {
		return ftel;
	}

	public void setFtel(java.lang.String ftel) {
		this.ftel = ftel;
	}

	public java.lang.String getFcardpaytype() {
		return fcardpaytype;
	}

	public void setFcardpaytype(java.lang.String fcardpaytype) {
		this.fcardpaytype = fcardpaytype;
	}

	public java.lang.String getFcardNumber() {
		return fcardNumber;
	}

	public void setFcardNumber(java.lang.String fcardNumber) {
		this.fcardNumber = fcardNumber;
	}

	public java.lang.Integer getFuserId() {
		return fuserId;
	}

	public void setFuserId(java.lang.Integer fuserId) {
		this.fuserId = fuserId;
	}

	public java.util.Date getFcreateTime() {
		return fcreateTime;
	}

	public void setFcreateTime(java.util.Date fcreateTime) {
		this.fcreateTime = fcreateTime;
	}

	public java.lang.Integer getFdefault() {
		return fdefault;
	}

	public void setFdefault(java.lang.Integer fdefault) {
		this.fdefault = fdefault;
	}

	public java.lang.Integer getFeffect() {
		return feffect;
	}

	public void setFeffect(java.lang.Integer feffect) {
		this.feffect = feffect;
	}

	public java.lang.String getFcard() {
		return fcard;
	}

	public void setFcard(java.lang.String fcard) {
		this.fcard = fcard;
	}

	public java.lang.String getFname() {
		return fname;
	}

	public void setFname(java.lang.String fname) {
		this.fname = fname;
	}

}
