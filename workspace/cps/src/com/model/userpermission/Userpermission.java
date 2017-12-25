package com.model.userpermission;

// default package
// Generated 2013-4-28 14:41:27 by Hibernate Tools 3.4.0.CR1

/**
 * 权限表

 */
public class Userpermission implements java.io.Serializable {

	private String fid;
	private String fuserid;
	private String fresourcesid;
	private String fpath;
	
	public String getFpath() {
		return fpath;
	}

	public void setFpath(String fpath) {
		this.fpath = fpath;
	}

	public Userpermission() {
	}

	public Userpermission(String fid, String fuserid, String fresourcesid) {
		this.fid = fid;
		this.fuserid = fuserid;
		this.fresourcesid = fresourcesid;
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

	public String getFresourcesid() {
		return this.fresourcesid;
	}

	public void setFresourcesid(String fresourcesid) {
		this.fresourcesid = fresourcesid;
	}

}
