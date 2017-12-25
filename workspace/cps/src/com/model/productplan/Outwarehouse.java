package com.model.productplan;

import java.math.BigDecimal;
import java.util.Date;

public class Outwarehouse{

	private String fid;
	private String fnumber;
	private String fcreatorid;
	private Date fcreatetime;
	private String flastupdateuserid;
	private Date flastupdatetime;
	private String fwarehouseid;
	private String fproductid;
	private BigDecimal foutqty;
	private String fremak;
	private String fsaleorderid;
	private String fwarehousesiteid;
	private String fissueenums;
	private String fdeliverid;
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

	public String getFsupplierid() {
		return fsupplierid;
	}

	public void setFsupplierid(String fsupplerid) {
		this.fsupplierid = fsupplerid;
	}

	public Outwarehouse() {
	}

	public Outwarehouse(String fid) {
		this.fid = fid;
	}

	

	public Outwarehouse(String fid, String fnumber, String fcreatorid,
			Date fcreatetime, String flastupdateuserid, Date flastupdatetime,
			String fwarehouseid, String fproductid, BigDecimal foutqty,
			String fremak, String fsaleorderid, String fwarehousesiteid,
			String fissueenums, String fdeliverid, Integer faudited,
			String fauditorid, Date faudittime, String fproductplanid,
			String forderentryid, String fsupplier) {
		super();
		this.fid = fid;
		this.fnumber = fnumber;
		this.fcreatorid = fcreatorid;
		this.fcreatetime = fcreatetime;
		this.flastupdateuserid = flastupdateuserid;
		this.flastupdatetime = flastupdatetime;
		this.fwarehouseid = fwarehouseid;
		this.fproductid = fproductid;
		this.foutqty = foutqty;
		this.fremak = fremak;
		this.fsaleorderid = fsaleorderid;
		this.fwarehousesiteid = fwarehousesiteid;
		this.fissueenums = fissueenums;
		this.fdeliverid = fdeliverid;
		this.faudited = faudited;
		this.fauditorid = fauditorid;
		this.faudittime = faudittime;
		this.fproductplanid = fproductplanid;
		this.forderentryid = forderentryid;
		this.fsupplierid = fsupplier;
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

	public String getFlastupdateuserid() {
		return this.flastupdateuserid;
	}

	public void setFlastupdateuserid(String flastupdateuserid) {
		this.flastupdateuserid = flastupdateuserid;
	}

	public Date getFlastupdatetime() {
		return this.flastupdatetime;
	}

	public void setFlastupdatetime(Date flastupdatetime) {
		this.flastupdatetime = flastupdatetime;
	}

	public String getFwarehouseid() {
		return this.fwarehouseid;
	}

	public void setFwarehouseid(String fwarehouseid) {
		this.fwarehouseid = fwarehouseid;
	}

	public String getFproductid() {
		return this.fproductid;
	}

	public void setFproductid(String fproductid) {
		this.fproductid = fproductid;
	}

	public BigDecimal getFoutqty() {
		return this.foutqty;
	}

	public void setFoutqty(BigDecimal foutqty) {
		this.foutqty = foutqty;
	}

	public String getFremak() {
		return this.fremak;
	}

	public void setFremak(String fremak) {
		this.fremak = fremak;
	}

	public String getFsaleorderid() {
		return this.fsaleorderid;
	}

	public void setFsaleorderid(String fsaleorderid) {
		this.fsaleorderid = fsaleorderid;
	}

	public String getFwarehousesiteid() {
		return this.fwarehousesiteid;
	}

	public void setFwarehousesiteid(String fwarehousesiteid) {
		this.fwarehousesiteid = fwarehousesiteid;
	}

	public String getFissueenums() {
		return this.fissueenums;
	}

	public void setFissueenums(String fissueenums) {
		this.fissueenums = fissueenums;
	}

	public String getFdeliverid() {
		return this.fdeliverid;
	}

	public void setFdeliverid(String fdeliverid) {
		this.fdeliverid = fdeliverid;
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
