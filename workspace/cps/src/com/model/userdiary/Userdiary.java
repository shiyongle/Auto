package com.model.userdiary;
// default package
// Generated 2014-6-9 11:20:43 by Hibernate Tools 3.4.0.CR1

import java.util.Date;

/**
 * TSysUserdiary generated by hbm2java
 */
public class Userdiary implements java.io.Serializable {

	private String fid;
	private String fuserid;
	private Date flogintime;
	private Date flastoperatetime;
	private String fusername;
	private String fsessionid;
	private String fip;
	private String fbrowser;
	private String fsystem;
	private String fremark;

	public Userdiary() {
	}



	public String getFremark() {
		return fremark;
	}



	public void setFremark(String fremark) {
		this.fremark = fremark;
	}



	public Userdiary(String fid, String fuserid, Date flogintime,
			Date flastoperatetime, String fusername, String fsessionid,
			String fip, String fbrowser, String fsystem) {
		this.fid = fid;
		this.fuserid = fuserid;
		this.flogintime = flogintime;
		this.flastoperatetime = flastoperatetime;
		this.fusername = fusername;
		this.fsessionid = fsessionid;
		this.fip = fip;
		this.fbrowser = fbrowser;
		this.fsystem = fsystem;
	}

	public String getFid() {
		return this.fid;
	}

	public void setFid(String fid) {
		this.fid = fid;
	}

	public String getFuserid() {
		return this.fuserid;
	}

	public void setFuserid(String fuserid) {
		this.fuserid = fuserid;
	}

	public Date getFlogintime() {
		return this.flogintime;
	}

	public void setFlogintime(Date flogintime) {
		this.flogintime = flogintime;
	}

	public Date getFlastoperatetime() {
		return this.flastoperatetime;
	}

	public void setFlastoperatetime(Date flastoperatetime) {
		this.flastoperatetime = flastoperatetime;
	}

	public String getFusername() {
		return this.fusername;
	}

	public void setFusername(String fusername) {
		this.fusername = fusername;
	}

	public String getFsessionid() {
		return this.fsessionid;
	}

	public void setFsessionid(String fsessionid) {
		this.fsessionid = fsessionid;
	}

	public String getFip() {
		return this.fip;
	}

	public void setFip(String fip) {
		this.fip = fip;
	}

	public String getFbrowser() {
		return this.fbrowser;
	}

	public void setFbrowser(String fbrowser) {
		this.fbrowser = fbrowser;
	}

	public String getFsystem() {
		return this.fsystem;
	}

	public void setFsystem(String fsystem) {
		this.fsystem = fsystem;
	}

}