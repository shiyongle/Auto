package com.model.userrole;
// default package
// Generated 2013-4-29 11:58:43 by Hibernate Tools 3.4.0.CR1

/**
 * 分配角色表  //新注册用户 角色
 */
public class UserRole implements java.io.Serializable {

	private String fid;
	private String fuserid;
	private String froleid;

	public UserRole() {
	}

	public UserRole(String fid, String fuserid, String froleid) {
		this.fid = fid;
		this.fuserid = fuserid;
		this.froleid = froleid;
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

	public String getFroleid() {
		return this.froleid;
	}

	public void setFroleid(String froleid) {
		this.froleid = froleid;
	}

}
