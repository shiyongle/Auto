package Com.Entity.System;

import java.util.Date;

/**
 * Log entity. @author MyEclipse Persistence Tools
 */

public class SysLog implements java.io.Serializable {

	// Fields

	private String fid;
	private String fsource = "";
	private String fuser = "";
	private String fuserid = "";
	private Date ftime;
	private String faction;
	private String fdata = "";
	private Integer fsuccess = 1;
	private String fmessage = "";
	private String fip = "";

	// Constructors

	/** default constructor */
	public SysLog() {
	}

	// Property accessors

	public String getFid() {
		return this.fid;
	}

	public void setFid(String fid) {
		this.fid = fid;
	}

	public String getFsource() {
		return this.fsource;
	}

	public void setFsource(String fsource) {
		this.fsource = fsource;
	}

	public String getFuser() {
		return this.fuser;
	}

	public void setFuser(String fuser) {
		this.fuser = fuser;
	}

	public Date getFtime() {
		return this.ftime;
	}

	public void setFtime(Date ftime) {
		this.ftime = ftime;
	}

	public String getFaction() {
		return this.faction;
	}

	public void setFaction(String faction) {
		this.faction = faction;
	}

	public String getFdata() {
		return this.fdata;
	}

	public void setFdata(String fdata) {
		this.fdata = fdata;
	}

	public Integer getFsuccess() {
		return this.fsuccess;
	}

	public void setFsuccess(Integer fsuccess) {
		this.fsuccess = fsuccess;
	}

	public String getFmessage() {
		return this.fmessage;
	}

	public void setFmessage(String fmessage) {
		this.fmessage = fmessage;
	}

	public String getFip() {
		return fip;
	}

	public void setFip(String fip) {
		this.fip = fip;
	}

	public String getFuserid() {
		return fuserid;
	}

	public void setFuserid(String fuserid) {
		this.fuserid = fuserid;
	}
	
	
	

}