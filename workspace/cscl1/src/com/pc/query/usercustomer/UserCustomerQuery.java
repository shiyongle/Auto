package com.pc.query.usercustomer;

import java.io.Serializable;

import org.springframework.format.annotation.DateTimeFormat;

import com.pc.query.aBase.BaseQuery;

public class UserCustomerQuery extends BaseQuery implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3786979239598136145L;
	private int urid;
	private String fname;
	private String fuserName;
	private String fsalesMan;
	private String fsalesManDept;
//	@DateTimeFormat(pattern="yyyy-MM-dd HH")
//	private java.util.Date fauditTimeBegin;
//	@DateTimeFormat(pattern="yyyy-MM-dd HH")
//	private java.util.Date fauditTimeEnd;
	private String fcreateTimeBegin;
	private String fcreateTimeEnd;
	public String getFcreateTimeBegin() {
		return fcreateTimeBegin;
	}
	public void setFcreateTimeBegin(String fcreateTimeBegin) {
		this.fcreateTimeBegin = fcreateTimeBegin;
	}
	public String getFcreateTimeEnd() {
		return fcreateTimeEnd;
	}
	public void setFcreateTimeEnd(String fcreateTimeEnd) {
		this.fcreateTimeEnd = fcreateTimeEnd;
	}
	public int getUrid() {
		return urid;
	}
	public void setUrid(int urid) {
		this.urid = urid;
	}
	public String getFname() {
		return fname;
	}
	public void setFname(String fname) {
		this.fname = fname;
	}
	public String getFuserName() {
		return fuserName;
	}
	public void setFuserName(String fuserName) {
		this.fuserName = fuserName;
	}
	public String getFsalesMan() {
		return fsalesMan;
	}
	public void setFsalesMan(String fsalesMan) {
		this.fsalesMan = fsalesMan;
	}
	public String getFsalesManDept() {
		return fsalesManDept;
	}
	public void setFsalesManDept(String fsalesManDept) {
		this.fsalesManDept = fsalesManDept;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
