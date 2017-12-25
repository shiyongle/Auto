package com.model.productdef;
// default package
// Generated 2015-1-29 14:15:19 by Hibernate Tools 3.4.0.CR1

import java.math.BigDecimal;
import java.util.Date;

/**
 * TPdtProductdef generated by hbm2java
 */
public class Productdef implements java.io.Serializable {

	private String fid;
	private String fnumber = "";
	private String fcreatorid;
	private Date fcreatetime;
	private String flastupdateuserid;
	private Date flastupdatetime;
	private BigDecimal fboxlength;
	private BigDecimal fboxwidth;
	private BigDecimal fboxheight;
	private BigDecimal fmateriallength = BigDecimal.ZERO;
	private BigDecimal fmaterialwidth = BigDecimal.ZERO;
	private Integer feffected = 0;//材料时 为早晚班开机 早班开机0 晚班开机1
	private String fcustomerid;
	private String fboxmodelid;
	private String ftilemodelid;
	private String fmaterialcodeid;
	private Integer feffect = 0;//启用禁用
	private BigDecimal fboardlength = BigDecimal.ZERO;
	private BigDecimal fboardwidth = BigDecimal.ZERO;
	private String fstavetype = "";
	private String fhstaveexp="";
	private String fvstaveexp="";
	private String fauditorid;
	private Date faudittime;
	private String fhformula="";
	private String fvformula="";
	private Integer flayer;
	private BigDecimal fsafestorage = BigDecimal.ZERO;
	private String forderunitid;
	private BigDecimal fvolume= BigDecimal.ZERO;
	private BigDecimal fwastescalar= BigDecimal.ZERO;
	private BigDecimal fwasterate= BigDecimal.ZERO;
	private Integer fshare = 0;
	private String fquality="";
	private BigDecimal farea = BigDecimal.ZERO;
	private Integer feditable = 0;
	private BigDecimal fprice = new BigDecimal(0.0);
	private Date fadjusttime;
	private String fcharacter;
	private BigDecimal fstorageamt;
	private Integer fperiod;
	private Integer faudited = 0;
	private Integer fstatarea = 1;
	private Date fstockadjusttime;
	private String fname = "";
	private String fdescription="";
	private Integer fcombination;
	private BigDecimal fwaste1;
	private BigDecimal fwaste2;
	private BigDecimal fwaste3;
	private String fcusproductname;
	private Integer fedition;
	private Integer fsharecustcount;
	private Integer fassemble = 0;
	private int fissynctoson = 0;
	private String fnewtype = "3";
	private int freject;
	private int fissevenlayer;
	private Integer fisoldnew;
	private int fiscombinecross = 0;
	private BigDecimal fchromaticprecision = new BigDecimal(5);
	private Integer fiscolorbox;
	private Integer fishistory=0;
	private String fversion = "1.0";
	private BigDecimal fmaterialcost = new BigDecimal(0);
	private Integer ftruckpdt;
	private String fcleartype;
	private Integer forderprice;
	private String cfsrcproductid;
	private Integer fcolorcount;
	private String fmaterialcode;
	private String feasproductId;
	private Integer fiscombinecrosssubs = 0;
	private String fbaseid;
	private String fmodelmethod;
	private String schemedesignid;
	private Integer subProductAmount;
	private String fcharactername;
	private String fcharacterid;
	private Integer fboxtype = 0;
	private String fsupplierid;
	private String fqueryname;
	private String fseries;
	private BigDecimal fhformula0 = BigDecimal.ZERO;
	private BigDecimal fhformula1 = BigDecimal.ZERO;
	private BigDecimal fvformula0 = BigDecimal.ZERO;
	private BigDecimal fdefine1 = BigDecimal.ZERO;
	private BigDecimal fdefine2 = BigDecimal.ZERO;
	private BigDecimal fdefine3 = BigDecimal.ZERO;
	private String fprintcolor;
	private String fworkproc;
	private String fdescription1 = "";	//胶粘备注
	private String fdescription2 = "";	//钉针备注
	private String fcustpdtid;	//不存数据库
	private String fmaterialfid;
	private String fmtsupplierid;
	private String ffileid;
	private String fcbnumber;
	private String  fybnumber;
	private String  fpackway;
	private Integer fpinamount = 0;
	private String fcblocation;
	private String fpaperspec;
	private String ffacespec;
	private String ftmodelspec;
	private Integer ffylinamount = 0;
	private Integer fisboardcard = -1;	// 1-纸箱信息，0-内盒信息
	private String fmaterialinfo;
	
	/*****************数据查询显示**********************/
	private String fmaterialid;

	public String getFmaterialid() {
		return fmaterialid;
	}
	public void setFmaterialid(String fmaterialid) {
		this.fmaterialid = fmaterialid;
	}

	/*********************************************/
	
	public String getFcbnumber() {
		return fcbnumber;
	}

	public void setFcbnumber(String fcbnumber) {
		this.fcbnumber = fcbnumber;
	}

	public String getFybnumber() {
		return fybnumber;
	}

	public void setFybnumber(String fybnumber) {
		this.fybnumber = fybnumber;
	}

	public String getFpackway() {
		return fpackway;
	}

	public void setFpackway(String fpackway) {
		this.fpackway = fpackway;
	}

	public Integer getFpinamount() {
		return fpinamount;
	}

	public void setFpinamount(Integer fpinamount) {
		this.fpinamount = fpinamount;
	}

	public String getFcblocation() {
		return fcblocation;
	}

	public void setFcblocation(String fcblocation) {
		this.fcblocation = fcblocation;
	}

	public String getFpaperspec() {
		return fpaperspec;
	}

	public void setFpaperspec(String fpaperspec) {
		this.fpaperspec = fpaperspec;
	}

	public String getFfacespec() {
		return ffacespec;
	}

	public void setFfacespec(String ffacespec) {
		this.ffacespec = ffacespec;
	}

	public Integer getFfylinamount() {
		return ffylinamount;
	}

	public void setFfylinamount(Integer ffylinamount) {
		this.ffylinamount = ffylinamount;
	}

	public Productdef() {
	}

	public String getFmaterialfid() {
		return fmaterialfid;
	}


	public void setFmaterialfid(String fmaterialfid) {
		this.fmaterialfid = fmaterialfid;
	}

	public String getFmtsupplierid() {
		return fmtsupplierid;
	}

	public void setFmtsupplierid(String fmtsupplierid) {
		this.fmtsupplierid = fmtsupplierid;
	}

	public String getFseries() {
		return fseries;
	}

	public void setFseries(String fseries) {
		this.fseries = fseries;
	}

	public BigDecimal getFhformula0() {
		return fhformula0;
	}

	public void setFhformula0(BigDecimal fhformula0) {
		this.fhformula0 = fhformula0;
	}

	public BigDecimal getFhformula1() {
		return fhformula1;
	}

	public void setFhformula1(BigDecimal fhformula1) {
		this.fhformula1 = fhformula1;
	}

	public BigDecimal getFvformula0() {
		return fvformula0;
	}

	public void setFvformula0(BigDecimal fvformula0) {
		this.fvformula0 = fvformula0;
	}

	public BigDecimal getFdefine1() {
		return fdefine1;
	}

	public void setFdefine1(BigDecimal fdefine1) {
		this.fdefine1 = fdefine1;
	}

	public BigDecimal getFdefine2() {
		return fdefine2;
	}

	public void setFdefine2(BigDecimal fdefine2) {
		this.fdefine2 = fdefine2;
	}

	public BigDecimal getFdefine3() {
		return fdefine3;
	}

	public void setFdefine3(BigDecimal fdefine3) {
		this.fdefine3 = fdefine3;
	}

	public String getFprintcolor() {
		return fprintcolor;
	}

	public void setFprintcolor(String fprintcolor) {
		this.fprintcolor = fprintcolor;
	}

	public String getFworkproc() {
		return fworkproc;
	}

	public void setFworkproc(String fworkproc) {
		this.fworkproc = fworkproc;
	}

	public Productdef(String fid, int fissynctoson, int freject,
			int fissevenlayer, int fiscombinecross,
			BigDecimal fchromaticprecision) {
		this.fid = fid;
		this.fissynctoson = fissynctoson;
		this.freject = freject;
		this.fissevenlayer = fissevenlayer;
		this.fiscombinecross = fiscombinecross;
		this.fchromaticprecision = fchromaticprecision;
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

	public BigDecimal getFboxlength() {
		return this.fboxlength;
	}

	public void setFboxlength(BigDecimal fboxlength) {
		this.fboxlength = fboxlength;
	}

	public BigDecimal getFboxwidth() {
		return this.fboxwidth;
	}

	public void setFboxwidth(BigDecimal fboxwidth) {
		this.fboxwidth = fboxwidth;
	}

	public BigDecimal getFboxheight() {
		return this.fboxheight;
	}

	public void setFboxheight(BigDecimal fboxheight) {
		this.fboxheight = fboxheight;
	}

	public BigDecimal getFmateriallength() {
		return this.fmateriallength;
	}

	public void setFmateriallength(BigDecimal fmateriallength) {
		this.fmateriallength = fmateriallength;
	}

	public BigDecimal getFmaterialwidth() {
		return this.fmaterialwidth;
	}

	public void setFmaterialwidth(BigDecimal fmaterialwidth) {
		this.fmaterialwidth = fmaterialwidth;
	}

	public Integer getFeffected() {
		return this.feffected;
	}

	public void setFeffected(Integer feffected) {
		this.feffected = feffected;
	}

	public String getFcustomerid() {
		return this.fcustomerid;
	}

	public void setFcustomerid(String fcustomerid) {
		this.fcustomerid = fcustomerid;
	}

	public String getFboxmodelid() {
		return this.fboxmodelid;
	}

	public void setFboxmodelid(String fboxmodelid) {
		this.fboxmodelid = fboxmodelid;
	}

	public String getFtilemodelid() {
		return this.ftilemodelid;
	}

	public void setFtilemodelid(String ftilemodelid) {
		this.ftilemodelid = ftilemodelid;
	}

	public String getFmaterialcodeid() {
		return this.fmaterialcodeid;
	}

	public void setFmaterialcodeid(String fmaterialcodeid) {
		this.fmaterialcodeid = fmaterialcodeid;
	}

	public Integer getFeffect() {
		return this.feffect;
	}

	public void setFeffect(Integer feffect) {
		this.feffect = feffect;
	}

	public BigDecimal getFboardlength() {
		return this.fboardlength;
	}

	public void setFboardlength(BigDecimal fboardlength) {
		this.fboardlength = fboardlength;
	}

	public BigDecimal getFboardwidth() {
		return this.fboardwidth;
	}

	public void setFboardwidth(BigDecimal fboardwidth) {
		this.fboardwidth = fboardwidth;
	}

	public String getFstavetype() {
		return this.fstavetype;
	}

	public void setFstavetype(String fstavetype) {
		this.fstavetype = fstavetype;
	}

	public String getFhstaveexp() {
		return this.fhstaveexp;
	}

	public void setFhstaveexp(String fhstaveexp) {
		this.fhstaveexp = fhstaveexp;
	}

	public String getFvstaveexp() {
		return this.fvstaveexp;
	}

	public void setFvstaveexp(String fvstaveexp) {
		this.fvstaveexp = fvstaveexp;
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

	public String getFhformula() {
		return this.fhformula;
	}

	public void setFhformula(String fhformula) {
		this.fhformula = fhformula;
	}

	public String getFvformula() {
		return this.fvformula;
	}

	public void setFvformula(String fvformula) {
		this.fvformula = fvformula;
	}

	public Integer getFlayer() {
		return this.flayer;
	}

	public void setFlayer(Integer flayer) {
		this.flayer = flayer;
	}

	public BigDecimal getFsafestorage() {
		return this.fsafestorage;
	}

	public void setFsafestorage(BigDecimal fsafestorage) {
		this.fsafestorage = fsafestorage;
	}

	public String getForderunitid() {
		return this.forderunitid;
	}

	public void setForderunitid(String forderunitid) {
		this.forderunitid = forderunitid;
	}

	public BigDecimal getFvolume() {
		return this.fvolume;
	}

	public void setFvolume(BigDecimal fvolume) {
		this.fvolume = fvolume;
	}

	public BigDecimal getFwastescalar() {
		return this.fwastescalar;
	}

	public void setFwastescalar(BigDecimal fwastescalar) {
		this.fwastescalar = fwastescalar;
	}

	public BigDecimal getFwasterate() {
		return this.fwasterate;
	}

	public void setFwasterate(BigDecimal fwasterate) {
		this.fwasterate = fwasterate;
	}

	public Integer getFshare() {
		return this.fshare;
	}

	public void setFshare(Integer fshare) {
		this.fshare = fshare;
	}

	public String getFquality() {
		return this.fquality;
	}

	public void setFquality(String fquality) {
		this.fquality = fquality;
	}

	public BigDecimal getFarea() {
		return this.farea;
	}

	public void setFarea(BigDecimal farea) {
		this.farea = farea;
	}

	public Integer getFeditable() {
		return this.feditable;
	}

	public void setFeditable(Integer feditable) {
		this.feditable = feditable;
	}

	public BigDecimal getFprice() {
		return this.fprice;
	}

	public void setFprice(BigDecimal fprice) {
		this.fprice = fprice;
	}

	public Date getFadjusttime() {
		return this.fadjusttime;
	}

	public void setFadjusttime(Date fadjusttime) {
		this.fadjusttime = fadjusttime;
	}

	public String getFcharacter() {
		return this.fcharacter;
	}

	public void setFcharacter(String fcharacter) {
		this.fcharacter = fcharacter;
	}

	public BigDecimal getFstorageamt() {
		return this.fstorageamt;
	}

	public void setFstorageamt(BigDecimal fstorageamt) {
		this.fstorageamt = fstorageamt;
	}

	public Integer getFperiod() {
		return this.fperiod;
	}

	public void setFperiod(Integer fperiod) {
		this.fperiod = fperiod;
	}

	public Integer getFaudited() {
		return this.faudited;
	}

	public void setFaudited(Integer faudited) {
		this.faudited = faudited;
	}

	public Integer getFstatarea() {
		return this.fstatarea;
	}

	public void setFstatarea(Integer fstatarea) {
		this.fstatarea = fstatarea;
	}

	public Date getFstockadjusttime() {
		return this.fstockadjusttime;
	}

	public void setFstockadjusttime(Date fstockadjusttime) {
		this.fstockadjusttime = fstockadjusttime;
	}

	public String getFname() {
		return this.fname;
	}

	public void setFname(String fname) {
		this.fname = fname;
	}

	public String getFdescription() {
		return this.fdescription;
	}

	public void setFdescription(String fdescription) {
		this.fdescription = fdescription;
	}

	public Integer getFcombination() {
		return this.fcombination;
	}

	public void setFcombination(Integer fcombination) {
		this.fcombination = fcombination;
	}

	public BigDecimal getFwaste1() {
		return this.fwaste1;
	}

	public void setFwaste1(BigDecimal fwaste1) {
		this.fwaste1 = fwaste1;
	}

	public BigDecimal getFwaste2() {
		return this.fwaste2;
	}

	public void setFwaste2(BigDecimal fwaste2) {
		this.fwaste2 = fwaste2;
	}

	public BigDecimal getFwaste3() {
		return this.fwaste3;
	}

	public void setFwaste3(BigDecimal fwaste3) {
		this.fwaste3 = fwaste3;
	}

	public String getFcusproductname() {
		return this.fcusproductname;
	}

	public void setFcusproductname(String fcusproductname) {
		this.fcusproductname = fcusproductname;
	}

	public Integer getFedition() {
		return this.fedition;
	}

	public void setFedition(Integer fedition) {
		this.fedition = fedition;
	}

	public Integer getFsharecustcount() {
		return this.fsharecustcount;
	}

	public void setFsharecustcount(Integer fsharecustcount) {
		this.fsharecustcount = fsharecustcount;
	}

	public Integer getFassemble() {
		return this.fassemble;
	}

	public void setFassemble(Integer fassemble) {
		this.fassemble = fassemble;
	}

	public int getFissynctoson() {
		return this.fissynctoson;
	}

	public void setFissynctoson(int fissynctoson) {
		this.fissynctoson = fissynctoson;
	}

	public String getFnewtype() {
		return this.fnewtype;
	}

	public void setFnewtype(String fnewtype) {
		this.fnewtype = fnewtype;
	}

	public int getFreject() {
		return this.freject;
	}

	public void setFreject(int freject) {
		this.freject = freject;
	}

	public int getFissevenlayer() {
		return this.fissevenlayer;
	}

	public void setFissevenlayer(int fissevenlayer) {
		this.fissevenlayer = fissevenlayer;
	}

	public Integer getFisoldnew() {
		return this.fisoldnew;
	}

	public void setFisoldnew(Integer fisoldnew) {
		this.fisoldnew = fisoldnew;
	}

	public int getFiscombinecross() {
		return this.fiscombinecross;
	}

	public void setFiscombinecross(int fiscombinecross) {
		this.fiscombinecross = fiscombinecross;
	}

	public BigDecimal getFchromaticprecision() {
		return this.fchromaticprecision;
	}

	public void setFchromaticprecision(BigDecimal fchromaticprecision) {
		this.fchromaticprecision = fchromaticprecision;
	}

	public Integer getFiscolorbox() {
		return this.fiscolorbox;
	}

	public void setFiscolorbox(Integer fiscolorbox) {
		this.fiscolorbox = fiscolorbox;
	}

	public Integer getFishistory() {
		return this.fishistory;
	}

	public void setFishistory(Integer fishistory) {
		this.fishistory = fishistory;
	}

	public String getFversion() {
		return this.fversion;
	}

	public void setFversion(String fversion) {
		this.fversion = fversion;
	}

	public BigDecimal getFmaterialcost() {
		return this.fmaterialcost;
	}

	public void setFmaterialcost(BigDecimal fmaterialcost) {
		this.fmaterialcost = fmaterialcost;
	}

	public Integer getFtruckpdt() {
		return this.ftruckpdt;
	}

	public void setFtruckpdt(Integer ftruckpdt) {
		this.ftruckpdt = ftruckpdt;
	}

	public String getFcleartype() {
		return this.fcleartype;
	}

	public void setFcleartype(String fcleartype) {
		this.fcleartype = fcleartype;
	}

	public Integer getForderprice() {
		return this.forderprice;
	}

	public void setForderprice(Integer forderprice) {
		this.forderprice = forderprice;
	}

	public String getCfsrcproductid() {
		return this.cfsrcproductid;
	}

	public void setCfsrcproductid(String cfsrcproductid) {
		this.cfsrcproductid = cfsrcproductid;
	}

	public Integer getFcolorcount() {
		return this.fcolorcount;
	}

	public void setFcolorcount(Integer fcolorcount) {
		this.fcolorcount = fcolorcount;
	}

	public String getFmaterialcode() {
		return this.fmaterialcode;
	}

	public void setFmaterialcode(String fmaterialcode) {
		this.fmaterialcode = fmaterialcode;
	}

	public String getFeasproductId() {
		return this.feasproductId;
	}

	public void setFeasproductId(String feasproductId) {
		this.feasproductId = feasproductId;
	}

	public Integer getFiscombinecrosssubs() {
		return this.fiscombinecrosssubs;
	}

	public void setFiscombinecrosssubs(Integer fiscombinecrosssubs) {
		this.fiscombinecrosssubs = fiscombinecrosssubs;
	}

	public String getFbaseid() {
		return this.fbaseid;
	}

	public void setFbaseid(String fbaseid) {
		this.fbaseid = fbaseid;
	}

	public String getFmodelmethod() {
		return this.fmodelmethod;
	}

	public void setFmodelmethod(String fmodelmethod) {
		this.fmodelmethod = fmodelmethod;
	}

	public String getSchemedesignid() {
		return this.schemedesignid;
	}

	public void setSchemedesignid(String schemedesignid) {
		this.schemedesignid = schemedesignid;
	}

	public String getFcharactername() {
		return this.fcharactername;
	}

	public void setFcharactername(String fcharactername) {
		this.fcharactername = fcharactername;
	}

	public String getFcharacterid() {
		return this.fcharacterid;
	}

	public void setFcharacterid(String fcharacterid) {
		this.fcharacterid = fcharacterid;
	}

	public Integer getFboxtype() {
		return this.fboxtype;
	}

	public void setFboxtype(Integer fboxtype) {
		this.fboxtype = fboxtype;
	}

	public Integer getSubProductAmount() {
		return subProductAmount;
	}

	public void setSubProductAmount(Integer subProductAmount) {
		this.subProductAmount = subProductAmount;
	}
	
	public String getFsupplierid() {
		return this.fsupplierid;
	}

	public void setFsupplierid(String fsupplierid) {
		this.fsupplierid = fsupplierid;
	}

	public String getFqueryname() {
		return fqueryname;
	}

	public void setFqueryname(String fqueryname) {
		this.fqueryname = fqueryname;
	}

	public String getFdescription1() {
		return fdescription1;
	}

	public void setFdescription1(String fdescription1) {
		this.fdescription1 = fdescription1;
	}

	public String getFdescription2() {
		return fdescription2;
	}

	public void setFdescription2(String fdescription2) {
		this.fdescription2 = fdescription2;
	}

	public String getFcustpdtid() {
		return fcustpdtid;
	}

	public void setFcustpdtid(String fcustpdtid) {
		this.fcustpdtid = fcustpdtid;
	}

	public String getFfileid() {
		return ffileid;
	}

	public void setFfileid(String ffileid) {
		this.ffileid = ffileid;
	}

	public Integer getFisboardcard() {
		return fisboardcard;
	}

	public void setFisboardcard(Integer fisboardcard) {
		this.fisboardcard = fisboardcard;
	}

	public String getFtmodelspec() {
		return ftmodelspec;
	}

	public void setFtmodelspec(String ftmodelspec) {
		this.ftmodelspec = ftmodelspec;
	}

	public String getFmaterialinfo() {
		return fmaterialinfo;
	}

	public void setFmaterialinfo(String fmaterialinfo) {
		this.fmaterialinfo = fmaterialinfo;
	}

	
	
	
	
	

}
