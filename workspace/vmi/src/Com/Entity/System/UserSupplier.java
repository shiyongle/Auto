package Com.Entity.System;
// default package
// Generated 2013-8-28 14:07:36 by Hibernate Tools 3.4.0.CR1

/**
 * TBdUsersupplier generated by hbm2java
 */
public class UserSupplier implements java.io.Serializable {

	private String fid;
	private String fuserid;
	private String fsupplierid;

	public UserSupplier() {
	}

	public UserSupplier(String fid) {
		this.fid = fid;
	}

	public UserSupplier(String fid, String fuserid, String fsupplierid) {
		this.fid = fid;
		this.fuserid = fuserid;
		this.fsupplierid = fsupplierid;
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

	public String getFsupplierid() {
		return this.fsupplierid;
	}

	public void setFsupplierid(String fsupplierid) {
		this.fsupplierid = fsupplierid;
	}

}