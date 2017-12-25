package com.model.schemedesign;
// default package
// Generated 2014-10-29 9:47:49 by Hibernate Tools 3.4.0.CR1

import java.util.Date;

import com.model.supplier.Supplier;


/**
 * TOrdSaleorder generated by hbm2java
 */
public class Saleorder implements java.io.Serializable {

	private String fid;
	private String fnumber;
	private String fcreatorid;
	private Date fcreatetime;
	private String flastupdateuserid;
	private Date flastupdatetime;
	private String fcustomerid;
	private String fcustproduct;
	private Date farrivetime;
	private Date fbizdate;
	private Integer famount;
	private int faudited;
	private String fauditorid;
	private Date faudittime;
	private Integer fordertype;
	private String fsuitProductId;
	private Integer fseq;
	private String fparentOrderEntryId;
	private String forderid;
	private Integer fimportEas = 0;
	private String fproductdefid;
	private String fsupplierid;
	private Integer fentryProductType;
	private String fimportEasuserid;
	private Date fimportEastime;
	private Integer famountrate;
	private Integer faffirmed;
	private Integer fstockoutqty = 0;
	private Integer fstockinqty  = 0;
	private Integer fstoreqty = 0;
	private Integer fassemble = 0;
	private Integer fiscombinecrosssubs = 0;
	private String feasorderid;
	private String feasorderentryid;
	private Integer fallot = 0;
	private String fallotorid;
	private Date fallottime;
	private Integer ftype;
	private String fdescription;
	private String fpcmordernumber;
	private Supplier supplier;

	public Saleorder() {
	}

	public Saleorder(String fid, int faudited) {
		this.fid = fid;
		this.faudited = faudited;
	}

	public Saleorder(String fid, String fnumber, String fcreatorid,
			Date fcreatetime, String flastupdateuserid, Date flastupdatetime,
			String fcustomerid, String fcustproduct, Date farrivetime,
			Date fbizdate, Integer famount, int faudited, String fauditorid,
			Date faudittime, Integer fordertype, String fsuitProductId,
			Integer fseq, String fparentOrderEntryId, String forderid,
			Integer fimportEas, String fproductdefid, String fsupplierid,
			Integer fentryProductType, String fimportEasuserid,
			Date fimportEastime, Integer famountrate, Integer faffirmed,
			Integer fstockoutqty, Integer fstockinqty, Integer fstoreqty,
			Integer fassemble, Integer fiscombinecrosssubs, String feasorderid,
			String feasorderentryid, Integer fallot, String fallotorid,
			Date fallottime, Integer ftype, String fdescription,String fpcmordernumber) {
		this.fid = fid;
		this.fnumber = fnumber;
		this.fcreatorid = fcreatorid;
		this.fcreatetime = fcreatetime;
		this.flastupdateuserid = flastupdateuserid;
		this.flastupdatetime = flastupdatetime;
		this.fcustomerid = fcustomerid;
		this.fcustproduct = fcustproduct;
		this.farrivetime = farrivetime;
		this.fbizdate = fbizdate;
		this.famount = famount;
		this.faudited = faudited;
		this.fauditorid = fauditorid;
		this.faudittime = faudittime;
		this.fordertype = fordertype;
		this.fsuitProductId = fsuitProductId;
		this.fseq = fseq;
		this.fparentOrderEntryId = fparentOrderEntryId;
		this.forderid = forderid;
		this.fimportEas = fimportEas;
		this.fproductdefid = fproductdefid;
		this.fsupplierid = fsupplierid;
		this.fentryProductType = fentryProductType;
		this.fimportEasuserid = fimportEasuserid;
		this.fimportEastime = fimportEastime;
		this.famountrate = famountrate;
		this.faffirmed = faffirmed;
		this.fstockoutqty = fstockoutqty;
		this.fstockinqty = fstockinqty;
		this.fstoreqty = fstoreqty;
		this.fassemble = fassemble;
		this.fiscombinecrosssubs = fiscombinecrosssubs;
		this.feasorderid = feasorderid;
		this.feasorderentryid = feasorderentryid;
		this.fallot = fallot;
		this.fallotorid = fallotorid;
		this.fallottime = fallottime;
		this.ftype = ftype;
		this.fdescription = fdescription;
		this.fpcmordernumber=fpcmordernumber;
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

	public String getFcustomerid() {
		return this.fcustomerid;
	}

	public void setFcustomerid(String fcustomerid) {
		this.fcustomerid = fcustomerid;
	}

	public String getFcustproduct() {
		return this.fcustproduct;
	}

	public void setFcustproduct(String fcustproduct) {
		this.fcustproduct = fcustproduct;
	}

	public Date getFarrivetime() {
		return this.farrivetime;
	}

	public void setFarrivetime(Date farrivetime) {
		this.farrivetime = farrivetime;
	}

	public Date getFbizdate() {
		return this.fbizdate;
	}

	public void setFbizdate(Date fbizdate) {
		this.fbizdate = fbizdate;
	}

	public Integer getFamount() {
		return this.famount;
	}

	public void setFamount(Integer famount) {
		this.famount = famount;
	}

	public int getFaudited() {
		return this.faudited;
	}

	public void setFaudited(int faudited) {
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

	public Integer getFordertype() {
		return this.fordertype;
	}

	public void setFordertype(Integer fordertype) {
		this.fordertype = fordertype;
	}

	public String getFsuitProductId() {
		return this.fsuitProductId;
	}

	public void setFsuitProductId(String fsuitProductId) {
		this.fsuitProductId = fsuitProductId;
	}

	public Integer getFseq() {
		return this.fseq;
	}

	public void setFseq(Integer fseq) {
		this.fseq = fseq;
	}

	public String getFparentOrderEntryId() {
		return this.fparentOrderEntryId;
	}

	public void setFparentOrderEntryId(String fparentOrderEntryId) {
		this.fparentOrderEntryId = fparentOrderEntryId;
	}

	public String getForderid() {
		return this.forderid;
	}

	public void setForderid(String forderid) {
		this.forderid = forderid;
	}

	public Integer getFimportEas() {
		return this.fimportEas;
	}

	public void setFimportEas(Integer fimportEas) {
		this.fimportEas = fimportEas;
	}

	public String getFproductdefid() {
		return this.fproductdefid;
	}

	public void setFproductdefid(String fproductdefid) {
		this.fproductdefid = fproductdefid;
	}

	public String getFsupplierid() {
		return this.fsupplierid;
	}

	public void setFsupplierid(String fsupplierid) {
		this.fsupplierid = fsupplierid;
	}

	public Integer getFentryProductType() {
		return this.fentryProductType;
	}

	public void setFentryProductType(Integer fentryProductType) {
		this.fentryProductType = fentryProductType;
	}

	public String getFimportEasuserid() {
		return this.fimportEasuserid;
	}

	public void setFimportEasuserid(String fimportEasuserid) {
		this.fimportEasuserid = fimportEasuserid;
	}

	public Date getFimportEastime() {
		return this.fimportEastime;
	}

	public void setFimportEastime(Date fimportEastime) {
		this.fimportEastime = fimportEastime;
	}

	public Integer getFamountrate() {
		return this.famountrate;
	}

	public void setFamountrate(Integer famountrate) {
		this.famountrate = famountrate;
	}

	public Integer getFaffirmed() {
		return this.faffirmed;
	}

	public void setFaffirmed(Integer faffirmed) {
		this.faffirmed = faffirmed;
	}

	public Integer getFstockoutqty() {
		return this.fstockoutqty;
	}

	public void setFstockoutqty(Integer fstockoutqty) {
		this.fstockoutqty = fstockoutqty;
	}

	public Integer getFstockinqty() {
		return this.fstockinqty;
	}

	public void setFstockinqty(Integer fstockinqty) {
		this.fstockinqty = fstockinqty;
	}

	public Integer getFstoreqty() {
		return this.fstoreqty;
	}

	public void setFstoreqty(Integer fstoreqty) {
		this.fstoreqty = fstoreqty;
	}

	public Integer getFassemble() {
		return this.fassemble;
	}

	public void setFassemble(Integer fassemble) {
		this.fassemble = fassemble;
	}

	public Integer getFiscombinecrosssubs() {
		return this.fiscombinecrosssubs;
	}

	public void setFiscombinecrosssubs(Integer fiscombinecrosssubs) {
		this.fiscombinecrosssubs = fiscombinecrosssubs;
	}

	public String getFeasorderid() {
		return this.feasorderid;
	}

	public void setFeasorderid(String feasorderid) {
		this.feasorderid = feasorderid;
	}

	public String getFeasorderentryid() {
		return this.feasorderentryid;
	}

	public void setFeasorderentryid(String feasorderentryid) {
		this.feasorderentryid = feasorderentryid;
	}

	public Integer getFallot() {
		return this.fallot;
	}

	public void setFallot(Integer fallot) {
		this.fallot = fallot;
	}

	public String getFallotorid() {
		return this.fallotorid;
	}

	public void setFallotorid(String fallotorid) {
		this.fallotorid = fallotorid;
	}

	public Date getFallottime() {
		return this.fallottime;
	}

	public void setFallottime(Date fallottime) {
		this.fallottime = fallottime;
	}

	public Integer getFtype() {
		return this.ftype;
	}

	public void setFtype(Integer ftype) {
		this.ftype = ftype;
	}

	public String getFdescription() {
		return this.fdescription;
	}

	public void setFdescription(String fdescription) {
		this.fdescription = fdescription;
	}

	/**
	 * @return the fpcmordernumber
	 */
	public String getFpcmordernumber() {
		return fpcmordernumber;
	}

	/**
	 * @param fpcmordernumber the fpcmordernumber to set
	 */
	public void setFpcmordernumber(String fpcmordernumber) {
		this.fpcmordernumber = fpcmordernumber;
	}

	public Supplier getSupplier() {
		return supplier;
	}

	public void setSupplier(Supplier supplier) {
		this.supplier = supplier;
	}

	
}
