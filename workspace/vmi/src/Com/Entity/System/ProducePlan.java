package Com.Entity.System;

import java.util.Date;

/**
 * ProducePlan entity. @author MyEclipse Persistence Tools
 */

public class ProducePlan implements java.io.Serializable {

	// Fields

	private String fid;
	private String fweek = "";
	private String fday = "";
	private String fnoday = "";
	private Integer fisweek;
	private String fproductid;
	private String fsupplierid;
	private String fcreatorid;
	private Date fupdatetime;
	private Date fcreatetime;

	// Constructors

	/** default constructor */
	public ProducePlan() {
	}

	/** minimal constructor */
	public ProducePlan(String fid) {
		this.fid = fid;
	}

	/** full constructor */
	public ProducePlan(String fid, String fweek, String fday, String fnoday,
			Integer fisweek, String fproductid, String fsupplierid,
			String fcreatorid, Date fupdatetime) {
		this.fid = fid;
		this.fweek = fweek;
		this.fday = fday;
		this.fnoday = fnoday;
		this.fisweek = fisweek;
		this.fproductid = fproductid;
		this.fsupplierid = fsupplierid;
		this.fcreatorid = fcreatorid;
		this.fupdatetime = fupdatetime;
	}

	// Property accessors

	public String getFid() {
		return this.fid;
	}

	public void setFid(String fid) {
		this.fid = fid;
	}

	public String getFweek() {
		return this.fweek;
	}

	public void setFweek(String fweek) {
		this.fweek = fweek;
	}

	public String getFday() {
		return this.fday;
	}

	public void setFday(String fday) {
		this.fday = fday;
	}

	public String getFnoday() {
		return this.fnoday;
	}

	public void setFnoday(String fnoday) {
		this.fnoday = fnoday;
	}

	public Integer getFisweek() {
		return this.fisweek;
	}

	public void setFisweek(Integer fisweek) {
		this.fisweek = fisweek;
	}

	public String getFproductid() {
		return this.fproductid;
	}

	public void setFproductid(String fproductid) {
		this.fproductid = fproductid;
	}

	public String getFsupplierid() {
		return this.fsupplierid;
	}

	public void setFsupplierid(String fsupplierid) {
		this.fsupplierid = fsupplierid;
	}

	public String getFcreatorid() {
		return this.fcreatorid;
	}

	public void setFcreatorid(String fcreatorid) {
		this.fcreatorid = fcreatorid;
	}

	public Date getFupdatetime() {
		return fupdatetime;
	}

	public void setFupdatetime(Date fupdatetime) {
		this.fupdatetime = fupdatetime;
	}

	public Date getFcreatetime() {
		return fcreatetime;
	}

	public void setFcreatetime(Date fcreatetime) {
		this.fcreatetime = fcreatetime;
	}

	

}