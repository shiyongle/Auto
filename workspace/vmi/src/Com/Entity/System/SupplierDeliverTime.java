package Com.Entity.System;

/**
 * SupplierDeliverTime entity. @author MyEclipse Persistence Tools
 */

public class SupplierDeliverTime implements java.io.Serializable {

	// Fields

	private String fid;
	private String fsupplierid;
	private Integer fdays = 0;
	private Integer fdefaultdays = 5;

	// Constructors

	/** default constructor */
	public SupplierDeliverTime() {
	}

	/** minimal constructor */
	public SupplierDeliverTime(String fid) {
		this.fid = fid;
	}

	/** full constructor */
	public SupplierDeliverTime(String fid, String fsupplierid, Integer fdays,
			Integer fdefaultdays) {
		this.fid = fid;
		this.fsupplierid = fsupplierid;
		this.fdays = fdays;
		this.fdefaultdays = fdefaultdays;
	}

	// Property accessors

	public String getFid() {
		return this.fid;
	}

	public void setFid(String fid) {
		this.fid = fid;
	}

	public String getFsupplierid() {
		return this.fsupplierid;
	}

	public void setFsupplierid(String fsupplierid) {
		this.fsupplierid = fsupplierid;
	}

	public Integer getFdays() {
		return this.fdays;
	}

	public void setFdays(Integer fdays) {
		this.fdays = fdays;
	}

	public Integer getFdefaultdays() {
		return this.fdefaultdays;
	}

	public void setFdefaultdays(Integer fdefaultdays) {
		this.fdefaultdays = fdefaultdays;
	}

}