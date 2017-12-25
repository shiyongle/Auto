package com.model.productplan;

import java.math.BigDecimal;
import java.util.Date;


public class Productindetail implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String fid;
	private String fnumber;
	private String fname;
	private String fsimplename;
	private String fcreatorid;
	private Date fcreatetime;
	private String fupdateuserid;
	private Date fupdatetime;
	private int ftype;
	private String fsaleOrderId;
	private String fproductId;
	private BigDecimal finqty;
	private String fwarehouseId;
	private String fwarehouseSiteId;
	private String fdescription;
	private Integer faudited;
	private String fauditorid;
	private Date faudittime;
	private String fproductplanid;
	private String forderentryid;

	private String fsupplierid;
	private String ftraitid;

	public String getFtraitid() {
		return ftraitid;
	}

	public void setFtraitid(String ftraitid) {
		this.ftraitid = ftraitid;
	}
	public Productindetail() {
		faudited=0;
	}

	public Productindetail(String fid, int ftype) {
		this.fid = fid;
		this.ftype = ftype;
	}

	public String getFsupplierid() {
		return fsupplierid;
	}

	public void setFsupplierid(String fsupplerid) {
		this.fsupplierid = fsupplerid;
	}

	public Productindetail(String fid, String fnumber, String fname,
			String fsimplename, String fcreatorid, Date fcreatetime,
			String fupdateuserid, Date fupdatetime, int ftype,
			String fsaleOrderId, String fproductId, BigDecimal finqty,
			String fwarehouseId, String fwarehouseSiteId, String fdescription,
			Integer faudited, String fauditorid, Date faudittime,
			String fproductplanid, String forderentryid, String fsupplerid) {
		super();
		this.fid = fid;
		this.fnumber = fnumber;
		this.fname = fname;
		this.fsimplename = fsimplename;
		this.fcreatorid = fcreatorid;
		this.fcreatetime = fcreatetime;
		this.fupdateuserid = fupdateuserid;
		this.fupdatetime = fupdatetime;
		this.ftype = ftype;
		this.fsaleOrderId = fsaleOrderId;
		this.fproductId = fproductId;
		this.finqty = finqty;
		this.fwarehouseId = fwarehouseId;
		this.fwarehouseSiteId = fwarehouseSiteId;
		this.fdescription = fdescription;
		this.faudited = faudited;
		this.fauditorid = fauditorid;
		this.faudittime = faudittime;
		this.fproductplanid = fproductplanid;
		this.forderentryid = forderentryid;
		this.fsupplierid = fsupplerid;
	}

	public String getFid() {
		return this.fid;
	}

	public void setFid(String fid) {
		this.fid = fid;
	}

	public String getFnumber() {
		return this.fnumber;
	}

	public void setFnumber(String fnumber) {
		this.fnumber = fnumber;
	}

	public String getFname() {
		return this.fname;
	}

	public void setFname(String fname) {
		this.fname = fname;
	}

	public String getFsimplename() {
		return this.fsimplename;
	}

	public void setFsimplename(String fsimplename) {
		this.fsimplename = fsimplename;
	}

	public String getFcreatorid() {
		return this.fcreatorid;
	}

	public void setFcreatorid(String fcreatorid) {
		this.fcreatorid = fcreatorid;
	}

	public Date getFcreatetime() {
		return this.fcreatetime;
	}

	public void setFcreatetime(Date fcreatetime) {
		this.fcreatetime = fcreatetime;
	}

	public String getFupdateuserid() {
		return this.fupdateuserid;
	}

	public void setFupdateuserid(String fupdateuserid) {
		this.fupdateuserid = fupdateuserid;
	}

	public Date getFupdatetime() {
		return this.fupdatetime;
	}

	public void setFupdatetime(Date fupdatetime) {
		this.fupdatetime = fupdatetime;
	}

	public int getFtype() {
		return this.ftype;
	}

	public void setFtype(int ftype) {
		this.ftype = ftype;
	}

	public String getFsaleOrderId() {
		return this.fsaleOrderId;
	}

	public void setFsaleOrderId(String fsaleOrderId) {
		this.fsaleOrderId = fsaleOrderId;
	}

	public String getFproductId() {
		return this.fproductId;
	}

	public void setFproductId(String fproductId) {
		this.fproductId = fproductId;
	}

	public BigDecimal getFinqty() {
		return this.finqty;
	}

	public void setFinqty(BigDecimal finqty) {
		this.finqty = finqty;
	}

	public String getFwarehouseId() {
		return this.fwarehouseId;
	}

	public void setFwarehouseId(String fwarehouseId) {
		this.fwarehouseId = fwarehouseId;
	}

	public String getFwarehouseSiteId() {
		return this.fwarehouseSiteId;
	}

	public void setFwarehouseSiteId(String fwarehouseSiteId) {
		this.fwarehouseSiteId = fwarehouseSiteId;
	}

	public String getFdescription() {
		return this.fdescription;
	}

	public void setFdescription(String fdescription) {
		this.fdescription = fdescription;
	}

	public Integer getFaudited() {
		return this.faudited;
	}

	public void setFaudited(Integer faudited) {
		this.faudited = faudited;
	}

	public String getFauditorid() {
		return this.fauditorid;
	}

	public void setFauditorid(String fauditorid) {
		this.fauditorid = fauditorid;
	}

	public Date getFaudittime() {
		return this.faudittime;
	}

	public void setFaudittime(Date faudittime) {
		this.faudittime = faudittime;
	}

	public String getFproductplanid() {
		return this.fproductplanid;
	}

	public void setFproductplanid(String fproductplanid) {
		this.fproductplanid = fproductplanid;
	}

	public String getForderentryid() {
		return this.forderentryid;
	}

	public void setForderentryid(String forderentryid) {
		this.forderentryid = forderentryid;
	}

}
