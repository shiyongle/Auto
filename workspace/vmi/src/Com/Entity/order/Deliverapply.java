package Com.Entity.order;
// default package
// Generated 2013-10-31 9:30:50 by Hibernate Tools 3.4.0.CR1

import java.math.BigDecimal;
import java.util.Date;

/**
 * TOrdDeliverapply generated by hbm2java
 */
public class Deliverapply implements java.io.Serializable,Cloneable {

	public static final String ANDROID = "android";
	
	public static final String DESKTOPS = "desktops";
	
	public static final String IOS = "ios";
	
	private String fid;
	private String fcreatorid;
	private Date fcreatetime;
	private String fupdateuserid;
	private Date fupdatetime;
	private String fnumber;
	private String fcustomerid;
	private String fcusproductid;
	private Date farrivetime;
	private String flinkman;
	private String flinkphone;
	private Integer famount;
	private String faddress;
	private String fdescription;
	private int fordered;
	private String fordermanid;
	private Date fordertime;
	private String fsaleorderid;
	private String fordernumber;
	private String forderentryid;
	private Integer fimportEas;
	private String fimportEasuserid;
	private Date fimportEastime;
	private Integer fouted;
	private String foutorid;
	private Date fouttime;
	private String faddressid;
	private String feasdeliverid;
	private Integer fistoPlan;
	private Date fplanTime;
	private String fplanNumber;
	private String fplanid;
	private Integer falloted;
	private Integer fiscreate;
	private String fcusfid;
	private Integer fstate;
	private String ftype;
	private String ftraitid;
	private String fcharacter="";
	private Integer foutQty;
	private String fsupplierid;
	private String fmaterialfid;
	private Integer fboxmodel=0;//其他
	private BigDecimal fboxlength;
	private BigDecimal fboxwidth;
	private BigDecimal fboxheight;
	private BigDecimal fmateriallength=BigDecimal.ZERO;
	private BigDecimal fmaterialwidth=BigDecimal.ZERO;
	private BigDecimal fhformula=BigDecimal.ZERO;
	private BigDecimal fvformula=BigDecimal.ZERO;
	private String fseries;//成型方式1片
	private String fstavetype;
	private Integer famountpiece=0;//配送数量（片）
	private Integer fboxtype=0;//0为纸箱；1为纸板
	private BigDecimal fhformula1=BigDecimal.ZERO;
	private String fhline;
	private String fvline;
	private BigDecimal fdefine1=BigDecimal.ZERO;
	private BigDecimal fdefine2=BigDecimal.ZERO;
	private BigDecimal fdefine3=BigDecimal.ZERO;
	private String fvstaveexp = "";
	private String fhstaveexp = "";

	private String frecipient;
	
	private Date freceiptTime;
	
	private String fcommonBoardOrder;
	
	private boolean fiscommonorder;
	
	private String flabel = "";
	
	/**
	 * 订单来源,默认桌面来源
	 */
	private String fordesource = DESKTOPS;
	
	
	
	public String getFordesource() {
		return fordesource;
	}

	public void setFordesource(String fordesource) {
		this.fordesource = fordesource;
	}

	public String getFrecipient() {
		return frecipient;
	}

	public void setFrecipient(String frecipient) {
		this.frecipient = frecipient;
	}

	public Date getFreceiptTime() {
		return freceiptTime;
	}

	public void setFreceiptTime(Date freceiptTime) {
		this.freceiptTime = freceiptTime;
	}

	public String getFsupplierid() {
		return fsupplierid;
	}

	public void setFsupplierid(String fsupplierid) {
		this.fsupplierid = fsupplierid;
	}
	
	public String getFtype() {
		return ftype;
	}


	public void setFtype(String ftype) {
		this.ftype = ftype;
	}


	@Override
	public Object clone() throws CloneNotSupportedException {
        //直接调用父类的clone()方法,返回克隆副本
        return super.clone();
    }
	

	public String getFcusfid() {
		return fcusfid;
	}

	public void setFcusfid(String fcusfid) {
		this.fcusfid = fcusfid;
	}

	public Deliverapply() {
		fordered=0;
		fimportEas=0;
		falloted=0;
		fiscreate=0;
		fouted=0;
		fstate=0;
		ftype="0";
		fboxtype=0;
		 fboxmodel=0;
	}

	public Deliverapply(String fid, int fordered) {
		this.fid = fid;
		this.fordered = fordered;
	}

	

	public Deliverapply(String fid, String fcreatorid, Date fcreatetime,
			String fupdateuserid, Date fupdatetime, String fnumber,
			String fcustomerid, String fcusproductid, Date farrivetime,
			String flinkman, String flinkphone, Integer famount,
			String faddress, String fdescription, int fordered,
			String fordermanid, Date fordertime, String fsaleorderid,
			String fordernumber, String forderentryid, Integer fimportEas,
			String fimportEasuserid, Date fimportEastime, Integer fouted,
			String foutorid, Date fouttime, String faddressid,
			String feasdeliverid, Integer fistoPlan, Date fplanTime,
			String fplanNumber, String fplanid, Integer falloted,
			Integer fiscreate, String fcusfid, Integer fstate, String ftype, String ftraitid,String fcharacter,  String frecipient,
	
	 Date freceiptTime,String fcommonBoardOrder, String fordesource) {
		super();
		
		this.fordesource = fordesource;
		
		this.frecipient = frecipient;
		this.freceiptTime = freceiptTime;
		
		this.fid = fid;
		this.fcreatorid = fcreatorid;
		this.fcreatetime = fcreatetime;
		this.fupdateuserid = fupdateuserid;
		this.fupdatetime = fupdatetime;
		this.fnumber = fnumber;
		this.fcustomerid = fcustomerid;
		this.fcusproductid = fcusproductid;
		this.farrivetime = farrivetime;
		this.flinkman = flinkman;
		this.flinkphone = flinkphone;
		this.famount = famount;
		this.faddress = faddress;
		this.fdescription = fdescription;
		this.fordered = fordered;
		this.fordermanid = fordermanid;
		this.fordertime = fordertime;
		this.fsaleorderid = fsaleorderid;
		this.fordernumber = fordernumber;
		this.forderentryid = forderentryid;
		this.fimportEas = fimportEas;
		this.fimportEasuserid = fimportEasuserid;
		this.fimportEastime = fimportEastime;
		this.fouted = fouted;
		this.foutorid = foutorid;
		this.fouttime = fouttime;
		this.faddressid = faddressid;
		this.feasdeliverid = feasdeliverid;
		this.fistoPlan = fistoPlan;
		this.fplanTime = fplanTime;
		this.fplanNumber = fplanNumber;
		this.fplanid = fplanid;
		this.falloted = falloted;
		this.fiscreate = fiscreate;
		this.fcusfid = fcusfid;
		this.fstate = fstate;
		this.ftype = ftype;
		this.ftraitid = ftraitid;
		this.fcharacter=fcharacter;
		this.fcommonBoardOrder = fcommonBoardOrder;
	}

	public String getFid() {
		return this.fid;
	}

	public void setFid(String fid) {
		this.fid = fid;
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

	public String getFnumber() {
		return this.fnumber;
	}

	public void setFnumber(String fnumber) {
		this.fnumber = fnumber;
	}

	public String getFcustomerid() {
		return this.fcustomerid;
	}

	public void setFcustomerid(String fcustomerid) {
		this.fcustomerid = fcustomerid;
	}

	public String getFcusproductid() {
		return this.fcusproductid;
	}

	public void setFcusproductid(String fcusproductid) {
		this.fcusproductid = fcusproductid;
	}

	public Date getFarrivetime() {
		return this.farrivetime;
	}

	public void setFarrivetime(Date farrivetime) {
		this.farrivetime = farrivetime;
	}

	public String getFlinkman() {
		return this.flinkman;
	}

	public void setFlinkman(String flinkman) {
		this.flinkman = flinkman;
	}

	public String getFlinkphone() {
		return this.flinkphone;
	}

	public void setFlinkphone(String flinkphone) {
		this.flinkphone = flinkphone;
	}

	public Integer getFamount() {
		return this.famount;
	}

	public void setFamount(Integer famount) {
		this.famount = famount;
	}

	public String getFaddress() {
		return this.faddress;
	}

	public void setFaddress(String faddress) {
		this.faddress = faddress;
	}

	public String getFdescription() {
		return this.fdescription;
	}

	public void setFdescription(String fdescription) {
		this.fdescription = fdescription;
	}

	public int getFordered() {
		return this.fordered;
	}

	public void setFordered(int fordered) {
		this.fordered = fordered;
	}

	public String getFordermanid() {
		return this.fordermanid;
	}

	public void setFordermanid(String fordermanid) {
		this.fordermanid = fordermanid;
	}

	public Date getFordertime() {
		return this.fordertime;
	}

	public void setFordertime(Date fordertime) {
		this.fordertime = fordertime;
	}

	public String getFsaleorderid() {
		return this.fsaleorderid;
	}

	public void setFsaleorderid(String fsaleorderid) {
		this.fsaleorderid = fsaleorderid;
	}

	public String getFordernumber() {
		return this.fordernumber;
	}

	public void setFordernumber(String fordernumber) {
		this.fordernumber = fordernumber;
	}

	public String getForderentryid() {
		return this.forderentryid;
	}

	public void setForderentryid(String forderentryid) {
		this.forderentryid = forderentryid;
	}

	public Integer getFimportEas() {
		return this.fimportEas;
	}

	public void setFimportEas(Integer fimportEas) {
		this.fimportEas = fimportEas;
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

	public Integer getFouted() {
		return this.fouted;
	}

	public void setFouted(Integer fouted) {
		this.fouted = fouted;
	}

	public String getFoutorid() {
		return this.foutorid;
	}

	public void setFoutorid(String foutorid) {
		this.foutorid = foutorid;
	}

	public Date getFouttime() {
		return this.fouttime;
	}

	public void setFouttime(Date fouttime) {
		this.fouttime = fouttime;
	}

	public String getFaddressid() {
		return this.faddressid;
	}

	public void setFaddressid(String faddressid) {
		this.faddressid = faddressid;
	}

	public String getFeasdeliverid() {
		return this.feasdeliverid;
	}

	public void setFeasdeliverid(String feasdeliverid) {
		this.feasdeliverid = feasdeliverid;
	}

	public Integer getFistoPlan() {
		return this.fistoPlan;
	}

	public void setFistoPlan(Integer fistoPlan) {
		this.fistoPlan = fistoPlan;
	}

	public Date getFplanTime() {
		return this.fplanTime;
	}

	public void setFplanTime(Date fplanTime) {
		this.fplanTime = fplanTime;
	}

	public String getFplanNumber() {
		return this.fplanNumber;
	}

	public void setFplanNumber(String fplanNumber) {
		this.fplanNumber = fplanNumber;
	}

	public String getFplanid() {
		return this.fplanid;
	}

	public void setFplanid(String fplanid) {
		this.fplanid = fplanid;
	}

	public Integer getFalloted() {
		return this.falloted;
	}

	public void setFalloted(Integer falloted) {
		this.falloted = falloted;
	}

	public Integer getFiscreate() {
		return this.fiscreate;
	}

	public void setFiscreate(Integer fiscreate) {
		this.fiscreate = fiscreate;
	}

	/**
	 * @return the fstate
	 */
	public Integer getFstate() {
		return fstate;
	}

	/**
	 * @param fstate the fstate to set
	 */
	public void setFstate(Integer fstate) {
		this.fstate = fstate;
	}
	
	public String getFtraitid() {
		return this.ftraitid;
	}

	public void setFtraitid(String ftraitid) {
		this.ftraitid = ftraitid;
	}


	/**
	 * @return the fcharacter
	 */
	public String getFcharacter() {
		return fcharacter;
	}


	/**
	 * @param fcharacter the fcharacter to set
	 */
	public void setFcharacter(String fcharacter) {
		this.fcharacter = fcharacter;
	}


	public Integer getFoutQty() {
		return foutQty;
	}


	public void setFoutQty(Integer foutQty) {
		this.foutQty = foutQty;
	}

	/**
	 * @return the fmaterialfid
	 */
	public String getFmaterialfid() {
		return fmaterialfid;
	}

	/**
	 * @param fmaterialfid the fmaterialfid to set
	 */
	public void setFmaterialfid(String fmaterialfid) {
		this.fmaterialfid = fmaterialfid;
	}

	/**
	 * @return the fboxmodel
	 */
	public Integer getFboxmodel() {
		return fboxmodel;
	}

	/**
	 * @param fboxmodel the fboxmodel to set
	 */
	public void setFboxmodel(Integer fboxmodel) {
		this.fboxmodel = fboxmodel;
	}

	/**
	 * @return the fboxlength
	 */
	public BigDecimal getFboxlength() {
		return fboxlength;
	}

	/**
	 * @param fboxlength the fboxlength to set
	 */
	public void setFboxlength(BigDecimal fboxlength) {
		this.fboxlength = fboxlength;
	}

	/**
	 * @return the fboxwidth
	 */
	public BigDecimal getFboxwidth() {
		return fboxwidth;
	}

	/**
	 * @param fboxwidth the fboxwidth to set
	 */
	public void setFboxwidth(BigDecimal fboxwidth) {
		this.fboxwidth = fboxwidth;
	}

	/**
	 * @return the fboxheight
	 */
	public BigDecimal getFboxheight() {
		return fboxheight;
	}

	/**
	 * @param fboxheight the fboxheight to set
	 */
	public void setFboxheight(BigDecimal fboxheight) {
		this.fboxheight = fboxheight;
	}

	/**
	 * @return the fmateriallength
	 */
	public BigDecimal getFmateriallength() {
		return fmateriallength;
	}

	/**
	 * @param fmateriallength the fmateriallength to set
	 */
	public void setFmateriallength(BigDecimal fmateriallength) {
		this.fmateriallength = fmateriallength;
	}

	/**
	 * @return the fmaterialwidth
	 */
	public BigDecimal getFmaterialwidth() {
		return fmaterialwidth;
	}

	/**
	 * @param fmaterialwidth the fmaterialwidth to set
	 */
	public void setFmaterialwidth(BigDecimal fmaterialwidth) {
		this.fmaterialwidth = fmaterialwidth;
	}



	/**
	 * @return the famountpiece
	 */
	public Integer getFamountpiece() {
		return famountpiece;
	}

	/**
	 * @param famountpiece the famountpiece to set
	 */
	public void setFamountpiece(Integer famountpiece) {
		this.famountpiece = famountpiece;
	}

	/**
	 * @return the fboxtype
	 */
	public Integer getFboxtype() {
		return fboxtype;
	}

	/**
	 * @param fboxtype the fboxtype to set
	 */
	public void setFboxtype(Integer fboxtype) {
		this.fboxtype = fboxtype;
	}

	/**
	 * @return the fseries
	 */
	public String getFseries() {
		return fseries;
	}

	/**
	 * @param fseries the fseries to set
	 */
	public void setFseries(String fseries) {
		this.fseries = fseries;
	}

	/**
	 * @return the fstavetype
	 */
	public String getFstavetype() {
		return fstavetype;
	}

	/**
	 * @param fstavetype the fstavetype to set
	 */
	public void setFstavetype(String fstavetype) {
		this.fstavetype = fstavetype;
	}

	public BigDecimal getFhformula() {
		return fhformula;
	}

	public void setFhformula(BigDecimal fhformula) {
		this.fhformula = fhformula;
	}

	public BigDecimal getFvformula() {
		return fvformula;
	}

	public void setFvformula(BigDecimal fvformula) {
		this.fvformula = fvformula;
	}

	public BigDecimal getFhformula1() {
		return fhformula1;
	}

	public void setFhformula1(BigDecimal fhformula1) {
		this.fhformula1 = fhformula1;
	}

	public String getFhline() {
		return fhline;
	}

	public void setFhline(String fhline) {
		this.fhline = fhline;
	}

	public String getFvline() {
		return fvline;
	}

	public void setFvline(String fvline) {
		this.fvline = fvline;
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

	public String getFvstaveexp() {
		return fvstaveexp;
	}

	public void setFvstaveexp(String fvstaveexp) {
		this.fvstaveexp = fvstaveexp;
	}

	public String getFhstaveexp() {
		return fhstaveexp;
	}

	public void setFhstaveexp(String fhstaveexp) {
		this.fhstaveexp = fhstaveexp;
	}

	public boolean isFiscommonorder() {
		return fiscommonorder;
	}

	public void setFiscommonorder(boolean fiscommonorder) {
		this.fiscommonorder = fiscommonorder;
	}

	public String getFcommonBoardOrder() {
		return fcommonBoardOrder;
	}

	public void setFcommonBoardOrder(String fcommonBoardOrder) {
		this.fcommonBoardOrder = fcommonBoardOrder;
	}

	public String getFlabel() {
		return flabel;
	}

	public void setFlabel(String flabel) {
		this.flabel = flabel;
	}
	
	

	
	
	



}
