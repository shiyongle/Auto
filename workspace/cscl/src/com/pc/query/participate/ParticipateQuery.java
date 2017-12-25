package com.pc.query.participate;

import java.io.Serializable;

import com.pc.query.aBase.BaseQuery;

public class ParticipateQuery extends BaseQuery implements Serializable{

	private java.lang.Integer fid;  //主键id
	private java.lang.Integer ftype;//司机性质
	private java.lang.String fname;//申请人姓名
	private java.lang.String ftel;//联系电话
	private java.lang.String fphone;//固定电话
	private java.lang.String faddress;//联系地址
	public java.lang.Integer getFid() {
		return fid;
	}
	public void setFid(java.lang.Integer fid) {
		this.fid = fid;
	}
	public java.lang.Integer getFtype() {
		return ftype;
	}
	public void setFtype(java.lang.Integer ftype) {
		this.ftype = ftype;
	}
	public java.lang.String getFname() {
		return fname;
	}
	public void setFname(java.lang.String fname) {
		this.fname = fname;
	}
	public java.lang.String getFtel() {
		return ftel;
	}
	public void setFtel(java.lang.String ftel) {
		this.ftel = ftel;
	}
	public java.lang.String getFphone() {
		return fphone;
	}
	public void setFphone(java.lang.String fphone) {
		this.fphone = fphone;
	}
	public java.lang.String getFaddress() {
		return faddress;
	}
	public void setFaddress(java.lang.String faddress) {
		this.faddress = faddress;
	}
	
	
}
