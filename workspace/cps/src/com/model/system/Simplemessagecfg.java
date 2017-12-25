package com.model.system;
// default package
// Generated 2014-11-5 9:13:59 by Hibernate Tools 3.4.0.CR1

/**
 * 发短信验证码的实体类
 * author zzm
 */
public class Simplemessagecfg implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6246467308297249393L;
	
	private String fid = "";
	private Integer ftype;
	private Integer fneedsms;
	private String frecipient = "";
	private String fphone = "";
	private String fuserid; 

	public Simplemessagecfg() {
	}

	public Simplemessagecfg(String fid) {
		this.fid = fid;
	}

	public Simplemessagecfg(String fid, Integer ftype, Integer fneedsms,
			String frecipient, String fphone, String fuserid) {
		this.fid = fid;
		this.ftype = ftype;
		this.fneedsms = fneedsms;
		this.frecipient = frecipient;
		this.fphone = fphone;
		this.fuserid = fuserid;
	}

	public String getFid() {
		return this.fid;
	}

	public void setFid(String fid) {
		this.fid = fid;
	}

	public Integer getFtype() {
		return this.ftype;
	}

	public void setFtype(Integer ftype) {
		this.ftype = ftype;
	}

	public Integer getFneedsms() {
		return this.fneedsms;
	}

	public void setFneedsms(Integer fneedsms) {
		this.fneedsms = fneedsms;
	}

	public String getFrecipient() {
		return this.frecipient;
	}

	public void setFrecipient(String frecipient) {
		this.frecipient = frecipient;
	}

	public String getFphone() {
		return this.fphone;
	}

	public void setFphone(String fphone) {
		this.fphone = fphone;
	}

	public String getFuserid() {
		return fuserid;
	}

	public void setFuserid(String fuserid) {
		this.fuserid = fuserid;
	}
}
