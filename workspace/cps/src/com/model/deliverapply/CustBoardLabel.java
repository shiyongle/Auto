package com.model.deliverapply;

import java.util.Date;

/**
 * CustBoardLabel entity. @author MyEclipse Persistence Tools
 */

public class CustBoardLabel implements java.io.Serializable {

	// Fields

	private String fid;
	private String fname;
	private String fcustomerid;
	private Date fcreatetime;

	// Constructors

	/** default constructor */
	public CustBoardLabel() {
	}

	/** minimal constructor */
	public CustBoardLabel(String fid) {
		this.fid = fid;
	}


	// Property accessors

	public String getFid() {
		return this.fid;
	}

	public void setFid(String fid) {
		this.fid = fid;
	}

	public String getFname() {
		return this.fname;
	}

	public void setFname(String fname) {
		this.fname = fname;
	}

	public String getFcustomerid() {
		return this.fcustomerid;
	}

	public void setFcustomerid(String fcustomerid) {
		this.fcustomerid = fcustomerid;
	}

	public Date getFcreatetime() {
		return fcreatetime;
	}

	public void setFcreatetime(Date fcreatetime) {
		this.fcreatetime = fcreatetime;
	}

	

}