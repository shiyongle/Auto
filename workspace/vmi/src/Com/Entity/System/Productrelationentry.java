package Com.Entity.System;
// default package
// Generated 2013-10-24 15:25:59 by Hibernate Tools 3.4.0.CR1

/**
 * TPdtProductrelationentry generated by hbm2java
 */
public class Productrelationentry implements java.io.Serializable {

	private String fid;
	private Integer fseq;
	private String fparentid;
	private String fcustproductid;
	private Integer famount;
	private String fremark;

	public Productrelationentry() {
	}

	public Productrelationentry(String fid, String fparentid) {
		this.fid = fid;
		this.fparentid = fparentid;
	}

	public Productrelationentry(String fid, Integer fseq, String fparentid,
			String fcustproductid, Integer famount, String fremark) {
		this.fid = fid;
		this.fseq = fseq;
		this.fparentid = fparentid;
		this.fcustproductid = fcustproductid;
		this.famount = famount;
		this.fremark = fremark;
	}

	public String getFid() {
		return this.fid;
	}

	public void setFid(String fid) {
		this.fid = fid;
	}

	public Integer getFseq() {
		return this.fseq;
	}

	public void setFseq(Integer fseq) {
		this.fseq = fseq;
	}

	public String getFparentid() {
		return this.fparentid;
	}

	public void setFparentid(String fparentid) {
		this.fparentid = fparentid;
	}

	public String getFcustproductid() {
		return this.fcustproductid;
	}

	public void setFcustproductid(String fcustproductid) {
		this.fcustproductid = fcustproductid;
	}

	public Integer getFamount() {
		return this.famount;
	}

	public void setFamount(Integer famount) {
		this.famount = famount;
	}

	public String getFremark() {
		return this.fremark;
	}

	public void setFremark(String fremark) {
		this.fremark = fremark;
	}

}
