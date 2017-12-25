package com.pc.model;

import java.math.BigDecimal;
import java.util.Date;

import cn.org.rapid_framework.util.DateConvertUtils;

public class CL_ComplainEntry implements java.io.Serializable {

	private static final long serialVersionUID = 4255164413675119429L;

	protected static final String FORMAT_CREATE_TIME = "yyyy-MM-dd HH:mm";
	private java.lang.Integer fid;						   // 主键
	private java.lang.Integer fparentid;                      //订单id
	private java.lang.Integer fuserid;				   //投诉类型
	private BigDecimal famount;    //司机账户返利金额
	private java.lang.Integer ftype;				   //投诉类型
	private String fremark;              //司机投诉处理结果说明
	private java.lang.Integer fcreator;                      //创建人
	private Date fcreateTime;				   //创建时间
	public java.lang.Integer getFid() {
		return fid;
	}
	public void setFid(java.lang.Integer fid) {
		this.fid = fid;
	}
	public java.lang.Integer getFparentid() {
		return fparentid;
	}
	public void setFparentid(java.lang.Integer fparentid) {
		this.fparentid = fparentid;
	}
	public java.lang.Integer getFuserid() {
		return fuserid;
	}
	public void setFuserid(java.lang.Integer fuserid) {
		this.fuserid = fuserid;
	}
	public BigDecimal getFamount() {
		return famount;
	}
	public void setFamount(BigDecimal famount) {
		this.famount = famount;
	}
	public int getFtype() {
		return ftype;
	}
	public void setFtype(int ftype) {
		this.ftype = ftype;
	}
	public String getFremark() {
		return fremark;
	}
	public void setFremark(String fremark) {
		this.fremark = fremark;
	}
	public int getFcreator() {
		return fcreator;
	}
	public void setFcreator(int fcreator) {
		this.fcreator = fcreator;
	}
	public Date getFcreateTime() {
		return fcreateTime;
	}
	public void setFcreateTime(Date fcreateTime) {
		this.fcreateTime = fcreateTime;
	}
	public String getCreateTimeString() {
		return DateConvertUtils.format(getFcreateTime(), FORMAT_CREATE_TIME);
	}
	public void setCreateTimeString(String value) {
		setFcreateTime(DateConvertUtils.parse(value, FORMAT_CREATE_TIME,java.util.Date.class));
	}
}
