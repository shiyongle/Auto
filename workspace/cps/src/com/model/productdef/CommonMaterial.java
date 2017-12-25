package com.model.productdef;

/**
 * CommonMaterial entity. @author MyEclipse Persistence Tools
 */

public class CommonMaterial implements java.io.Serializable {

	// Fields

	private String fid;
	private String fcustomerid;
	private String fsupplierid;
	private String fmaterialid;

	// Constructors

	/** default constructor */
	public CommonMaterial() {
	}

	/** minimal constructor */
	public CommonMaterial(String fid) {
		this.fid = fid;
	}

	/** full constructor */
	public CommonMaterial(String fid, String fcustomerid, String fsupplierid,
			String fmaterialid) {
		this.fid = fid;
		this.fcustomerid = fcustomerid;
		this.fsupplierid = fsupplierid;
		this.fmaterialid = fmaterialid;
	}

	// Property accessors

	public String getFid() {
		return this.fid;
	}

	public void setFid(String fid) {
		this.fid = fid;
	}

	public String getFcustomerid() {
		return this.fcustomerid;
	}

	public void setFcustomerid(String fcustomerid) {
		this.fcustomerid = fcustomerid;
	}

	public String getFsupplierid() {
		return this.fsupplierid;
	}

	public void setFsupplierid(String fsupplierid) {
		this.fsupplierid = fsupplierid;
	}

	public String getFmaterialid() {
		return this.fmaterialid;
	}

	public void setFmaterialid(String fmaterialid) {
		this.fmaterialid = fmaterialid;
	}

}