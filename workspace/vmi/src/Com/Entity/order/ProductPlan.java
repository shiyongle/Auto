package Com.Entity.order;
// default package
// Generated 2015-2-3 14:11:09 by Hibernate Tools 3.4.0.CR1

import java.math.BigDecimal;
import java.util.Date;

/**
 * TOrdProductplan generated by hbm2java
 */
public class ProductPlan implements java.io.Serializable {

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
	private Integer faffirmed = 0;
	private Integer fstockoutqty = 0;
	private Integer fstockinqty = 0;
	private Integer fstoreqty = 0;
	private Integer fassemble;
	private Integer fiscombinecrosssubs;
	private String fparentorderid;
	private String feasorderentryid;
	private String feasorderid;
	private int fcloseed;
	private BigDecimal purchaseprice;
	private BigDecimal perpurchaseprice;
	private Integer ftype;
	private Integer fissync;
	private String fschemedesignid;
	private Long fboxlength;
	private Long fboxwidth;
	private Long fboxheight;
	private String fdescription;
	private Integer fstate;
	private Date faffirmtime;
	private String faffirmer;
	private Integer fboxtype = 0;
	private String fdeliverapplyid;
	private String fpcmordernumber;
	private int fcreateboard;
	private String fdeliversboardid;
	private Integer fisfinished=0;
	
	public String getFdeliversboardid() {
		return fdeliversboardid;
	}

	public void setFdeliversboardid(String fdeliversboardid) {
		this.fdeliversboardid = fdeliversboardid;
	}

	public int isFcreateboard() {
		return fcreateboard;
	}

	public void setFcreateboard(int fcreateboard) {
		this.fcreateboard = fcreateboard;
	}

	public ProductPlan() {
		fstate=0;
		fisfinished=0;
	}

	public ProductPlan(String fid, int faudited, int fcloseed) {
		this.fid = fid;
		this.faudited = faudited;
		this.fcloseed = fcloseed;
	}

	public ProductPlan(String fid, String fnumber, String fcreatorid,
			Date fcreatetime, String flastupdateuserid, Date flastupdatetime,
			String fcustomerid, String fcustproduct, Date farrivetime,
			Date fbizdate, Integer famount, int faudited, String fauditorid,
			Date faudittime, Integer fordertype, String fsuitProductId,
			Integer fseq, String fparentOrderEntryId, String forderid,
			Integer fimportEas, String fproductdefid, String fsupplierid,
			Integer fentryProductType, String fimportEasuserid,
			Date fimportEastime, Integer famountrate, Integer faffirmed,
			Integer fstockoutqty, Integer fstockinqty, Integer fstoreqty,
			Integer fassemble, Integer fiscombinecrosssubs,
			String fparentorderid, String feasorderentryid, String feasorderid,
			int fcloseed, BigDecimal purchaseprice,
			BigDecimal perpurchaseprice, Integer ftype, Integer fissync,
			String fschemedesignid, Long fboxlength, Long fboxwidth,
			Long fboxheight, String fdescription, Integer fstate,
			Date faffirmtime, String faffirmer, Integer fboxtype,
			String fdeliverapplyid,String fpcmordernumber,int fcreateboard,String fdeliversboardid) {
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
		this.fparentorderid = fparentorderid;
		this.feasorderentryid = feasorderentryid;
		this.feasorderid = feasorderid;
		this.fcloseed = fcloseed;
		this.purchaseprice = purchaseprice;
		this.perpurchaseprice = perpurchaseprice;
		this.ftype = ftype;
		this.fissync = fissync;
		this.fschemedesignid = fschemedesignid;
		this.fboxlength = fboxlength;
		this.fboxwidth = fboxwidth;
		this.fboxheight = fboxheight;
		this.fdescription = fdescription;
		this.fstate = fstate;
		this.faffirmtime = faffirmtime;
		this.faffirmer = faffirmer;
		this.fboxtype = fboxtype;
		this.fdeliverapplyid = fdeliverapplyid;
		this.fpcmordernumber=fpcmordernumber;
		this.fcreateboard = fcreateboard;
		this.fdeliversboardid = fdeliversboardid;
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

	public String getFparentorderid() {
		return this.fparentorderid;
	}

	public void setFparentorderid(String fparentorderid) {
		this.fparentorderid = fparentorderid;
	}

	public String getFeasorderentryid() {
		return this.feasorderentryid;
	}

	public void setFeasorderentryid(String feasorderentryid) {
		this.feasorderentryid = feasorderentryid;
	}

	public String getFeasorderid() {
		return this.feasorderid;
	}

	public void setFeasorderid(String feasorderid) {
		this.feasorderid = feasorderid;
	}

	public int getFcloseed() {
		return this.fcloseed;
	}

	public void setFcloseed(int fcloseed) {
		this.fcloseed = fcloseed;
	}

	public BigDecimal getPurchaseprice() {
		return this.purchaseprice;
	}

	public void setPurchaseprice(BigDecimal purchaseprice) {
		this.purchaseprice = purchaseprice;
	}

	public BigDecimal getPerpurchaseprice() {
		return this.perpurchaseprice;
	}

	public void setPerpurchaseprice(BigDecimal perpurchaseprice) {
		this.perpurchaseprice = perpurchaseprice;
	}

	public Integer getFtype() {
		return this.ftype;
	}

	public void setFtype(Integer ftype) {
		this.ftype = ftype;
	}

	public Integer getFissync() {
		return this.fissync;
	}

	public void setFissync(Integer fissync) {
		this.fissync = fissync;
	}

	public String getFschemedesignid() {
		return this.fschemedesignid;
	}

	public void setFschemedesignid(String fschemedesignid) {
		this.fschemedesignid = fschemedesignid;
	}

	public Long getFboxlength() {
		return this.fboxlength;
	}

	public void setFboxlength(Long fboxlength) {
		this.fboxlength = fboxlength;
	}

	public Long getFboxwidth() {
		return this.fboxwidth;
	}

	public void setFboxwidth(Long fboxwidth) {
		this.fboxwidth = fboxwidth;
	}

	public Long getFboxheight() {
		return this.fboxheight;
	}

	public void setFboxheight(Long fboxheight) {
		this.fboxheight = fboxheight;
	}

	public String getFdescription() {
		return this.fdescription;
	}

	public void setFdescription(String fdescription) {
		this.fdescription = fdescription;
	}

	public Integer getFstate() {
		return this.fstate;
	}

	public void setFstate(Integer fstate) {
		this.fstate = fstate;
	}

	public Date getFaffirmtime() {
		return this.faffirmtime;
	}

	public void setFaffirmtime(Date faffirmtime) {
		this.faffirmtime = faffirmtime;
	}

	public String getFaffirmer() {
		return this.faffirmer;
	}

	public void setFaffirmer(String faffirmer) {
		this.faffirmer = faffirmer;
	}

	public Integer getFboxtype() {
		return this.fboxtype;
	}

	public void setFboxtype(Integer fboxtype) {
		this.fboxtype = fboxtype;
	}

	public String getFdeliverapplyid() {
		return this.fdeliverapplyid;
	}

	public void setFdeliverapplyid(String fdeliverapplyid) {
		this.fdeliverapplyid = fdeliverapplyid;
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

	/**
	 * @return the fisfinished
	 */
	public Integer getFisfinished() {
		return fisfinished;
	}

	/**
	 * @param fisfinished the fisfinished to set
	 */
	public void setFisfinished(Integer fisfinished) {
		this.fisfinished = fisfinished;
	}

}