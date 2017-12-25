package Com.Entity.System;

/**
 * MaterialLimit entity. @author MyEclipse Persistence Tools
 */

public class MaterialLimit implements java.io.Serializable {

	// Fields

	private String fid;
	private String fcustomerid;
	private Double fmaxlength;
	private Double fmaxwidth;
	private Double fminlength;
	private Double fminwidth;

	// Constructors

	/** default constructor */
	public MaterialLimit() {
	}

	/** full constructor */
	public MaterialLimit(String fid, String fcustomerid, Double fmaxlength,
			Double fmaxwidth, Double fminlength, Double fminwidth) {
		this.fid = fid;
		this.fcustomerid = fcustomerid;
		this.fmaxlength = fmaxlength;
		this.fmaxwidth = fmaxwidth;
		this.fminlength = fminlength;
		this.fminwidth = fminwidth;
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

	public Double getFmaxlength() {
		return this.fmaxlength;
	}

	public void setFmaxlength(Double fmaxlength) {
		this.fmaxlength = fmaxlength;
	}

	public Double getFmaxwidth() {
		return this.fmaxwidth;
	}

	public void setFmaxwidth(Double fmaxwidth) {
		this.fmaxwidth = fmaxwidth;
	}

	public Double getFminlength() {
		return this.fminlength;
	}

	public void setFminlength(Double fminlength) {
		this.fminlength = fminlength;
	}

	public Double getFminwidth() {
		return this.fminwidth;
	}

	public void setFminwidth(Double fminwidth) {
		this.fminwidth = fminwidth;
	}

}