package com.model.deliverapply;

import java.math.BigDecimal;
import java.util.Date;
public class Deliverapply implements java.io.Serializable,Cloneable {

	/***
	 *<p>Description: 要货订单</p>
	 *<p>Company: CPS-TEAM</p> 
	 * @author WANGC
	 * @date 2015-8-12 上午10:24:03
	*/
	private static final long serialVersionUID = -6198318577383226680L;
	public static final String ANDROID = "android";
	public static final String DESKTOPS = "desktops";
	public static final String IOS = "ios";
	
	private String fid;
	private String fcreatorid;                          /*** 创建人标识*/
	private Date fcreatetime;                           /*** 创建时间*/
	private String fupdateuserid;                       /*** 修改人标识*/
	private Date fupdatetime;                           /*** 修改时间*/
	private String fnumber;                             /*** 要货申请编号*/
	private String fcustomerid;                         /*** 客户表主键*/
	private String fcusproductid;                       /*** 客户产品表主键*/
	private Date farrivetime;                           /*** 配送时间*/
	private String flinkman;                            /*** 联系人*/
	private String flinkphone;                          /*** 联系电话*/
	private Integer famount;                            /*** 数量（只）*/
	private String faddress;                            /*** 地址信息*/
	private String fdescription;                        /*** 备注*/
	private int fordered=0;                          		/*** 是否下单*/
	private String fordermanid;                         /*** 下单人*/
	private Date fordertime;                            /*** 下单时间*/
	private String fsaleorderid;                        /*** t_ord_saleorder表FORDERID*/
	private String fordernumber;                        /*** 订单编号*/
	private String forderentryid;                       /*** t_ord_saleorder表主键FID*/
	private Integer fimportEas;                         /*** 是否导入EAS  0 否1 是*/
	private String fimportEasuserid;                    /*** 导入人*/
	private Date fimportEastime;                        /*** 导入时间*/
	private Integer fouted=0;                          	/*** 是否发货 0否 1 是*/
	private String foutorid;                          	/*** 发货人*/
	private Date fouttime;                          	/*** 发货时间*/
	private String faddressid;                          /*** t_bd_address主键FID*/
	private String feasdeliverid;                       /*** 作废*/
	private Integer fistoPlan;                          /*** 作废*/
	private Date fplanTime;                          	/*** 生成制造商订单时间*/
	private String fplanNumber;                         /*** 制造商订单编码*/
	private String fplanid;                             /*** 制造商订单标识*/
	private Integer falloted;                           /*** 是否分配 0 未 1是*/
	private Integer fiscreate=0;                          /*** 是否生成管理 0否 1是*/
	private String fcusfid;                          	/*** 要货申请导入时的fcusfid，（正泰客户内部使用）*/
	private Integer fstate=0;                          	/*** 状态：0."已创建", 1,2,3."已接收", 2，3：已排产, 4.已入库，5部分发货，6全部发货*/
	private String ftype="0";                          		/*** 类型 0 正常 1补单 2补货*/
	private String ftraitid;                          	/*** t_ord_schemedesignentry主键FID*/
	private String fcharacter="";                       /*** 特性信息*/
	private Integer foutQty=0;                          	/*** 发货数量*/
	private String fsupplierid;                         /*** t_sys_supplier 主键fid*/
	private String fmaterialfid;                        /*** t_pdt_productdef 主键fid*/
	private Integer fboxmodel=0;                        /*** 箱型：1普通 2全包,3半包,4有底无盖,5有盖无底,6围框,8立体箱*/
	private BigDecimal fboxlength;                      /*** 纸箱长*/
	private BigDecimal fboxwidth;                       /*** 纸箱宽*/
	private BigDecimal fboxheight;                      /*** 纸箱高*/
	private BigDecimal fmateriallength=BigDecimal.ZERO; /*** 落料总长*/
	private BigDecimal fmaterialwidth=BigDecimal.ZERO;	/*** 落料总高*/
	private BigDecimal fhformula=BigDecimal.ZERO;		/*** 横向公式中可变值（结舌）*/
	private BigDecimal fvformula=BigDecimal.ZERO;		/*** 纵向公式中可变值 （加边值）*/
	private String fseries;//成型方式1片
	private String fstavetype;							/*** 压线方式：不压线，普通压线,内压线,外压线,横压*/
	private Integer famountpiece=0;//配送数量（片）
	private Integer fboxtype=0;							/*** 0为纸箱；1为纸板*/
	private BigDecimal fhformula1=BigDecimal.ZERO;		/*** 横向公式中可变值 （结舌）*/
	private String fhline;								/*** 横向压线值*/
	private String fvline;								/*** 纵向压线值*/
	private BigDecimal fdefine1=BigDecimal.ZERO;        /*** 选立体箱会赋值*/
	private BigDecimal fdefine2=BigDecimal.ZERO;        /*** 选立体箱要填写纵向公式的自定义2*/
	private BigDecimal fdefine3=BigDecimal.ZERO;        /*** 选立体箱要填写纵向公式的自定义3*/
	private String fvstaveexp = "";        				/*** 纵向公式*/
	private String fhstaveexp = "";        				/*** 横向公式*/
	private String frecipient;  	      				/*** 接收人*/
	private Date freceiptTime;  	      				/*** 接收时间*/
	private String fcommonBoardOrder;  	      			/*** 常用纸板订单*/
	private boolean fiscommonorder;  	      			/*** 是否常用订单 1 是0 否*/
	private String flabel = "";  	      				/*** 客户标签（纸板用）*/
	private String fordesource = DESKTOPS;				/***订单来源 ：desktops, ios, android ,默认桌面来源*/
	
	//*********************以下属性,只做封装,非对应数据库字段***************begin********************//
	private Integer fplanamount;			/*** 计划数量*/
	private Integer faveragefamount=0;		/*** 平均发货量*/
	private Date ffinishtime;				/*** 首次发货/要求完成时间*/
	private Date fconsumetime;				/*** 备货周期/预计消耗时间*/
	private String fremark;					/*** 备注*/
	private String hours;
	//以上6个属性，主要用在下单功能表单提交
	private String fpdtname;                /*** 产品名称*/
	private String fpdtspec;                /*** 产品规格*/
	private String suppliername;            /*** 供应商*/
	private String supplierPhone;           /*** 供应商电话*/
	private String placeOrderTime;			
	private String farrivetimeString;/***交期*/
	private String latestpathImg;
	private String layer;					/*** 材料层数*/


	public String getLatestpathImg() {
		return latestpathImg;
	}

	public void setLatestpathImg(String latestpathImg) {
		this.latestpathImg = latestpathImg;
	}
	
	public String getLayer() {
		return layer;
	}

	public void setLayer(String layer) {
		this.layer = layer;
	}
	
	public Integer getFplanamount() {
		return fplanamount;
	}

	public void setFplanamount(Integer fplanamount) {
		this.fplanamount = fplanamount;
	}

	public Integer getFaveragefamount() {
		return faveragefamount;
	}

	public void setFaveragefamount(Integer faveragefamount) {
		this.faveragefamount = faveragefamount;
	}

	public Date getFfinishtime() {
		return ffinishtime;
	}

	public void setFfinishtime(Date ffinishtime) {
		this.ffinishtime = ffinishtime;
	}

	public Date getFconsumetime() {
		return fconsumetime;
	}

	public void setFconsumetime(Date fconsumetime) {
		this.fconsumetime = fconsumetime;
	}
	public String getFremark() {
		return fremark;
	}

	public void setFremark(String fremark) {
		this.fremark = fremark;
	}
	
	public String getFpdtname() {
		return fpdtname;
	}

	public void setFpdtname(String fpdtname) {
		this.fpdtname = fpdtname;
	}

	public String getFpdtspec() {
		return fpdtspec;
	}

	public void setFpdtspec(String fpdtspec) {
		this.fpdtspec = fpdtspec;
	}

	public String getSuppliername() {
		return suppliername;
	}

	public void setSuppliername(String suppliername) {
		this.suppliername = suppliername;
	}
	
	public String getSupplierPhone() {
		return supplierPhone;
	}

	public void setSupplierPhone(String supplierPhone) {
		this.supplierPhone = supplierPhone;
	}

	public String getPlaceOrderTime() {
		return placeOrderTime;
	}

	public void setPlaceOrderTime(String placeOrderTime) {
		this.placeOrderTime = placeOrderTime;
	}
	public String getHours() {
		return hours;
	}

	public void setHours(String hours) {
		this.hours = hours;
	}
	
	public String getFarrivetimeString() {
		return farrivetimeString;
	}
	
	public void setFarrivetimeString(String farrivetimeString) {
		this.farrivetimeString = farrivetimeString;
	}
	
	//*********************以上属性,只做封装,非对应数据库字段***************end**********************//
	

	


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

	public Integer getFstate() {
		return fstate;
	}

	public void setFstate(Integer fstate) {
		this.fstate = fstate;
	}
	
	public String getFtraitid() {
		return this.ftraitid;
	}

	public void setFtraitid(String ftraitid) {
		this.ftraitid = ftraitid;
	}


	public String getFcharacter() {
		return fcharacter;
	}

	public void setFcharacter(String fcharacter) {
		this.fcharacter = fcharacter;
	}


	public Integer getFoutQty() {
		return foutQty;
	}


	public void setFoutQty(Integer foutQty) {
		this.foutQty = foutQty;
	}

	public String getFmaterialfid() {
		return fmaterialfid;
	}

	public void setFmaterialfid(String fmaterialfid) {
		this.fmaterialfid = fmaterialfid;
	}

	public Integer getFboxmodel() {
		return fboxmodel;
	}

	public void setFboxmodel(Integer fboxmodel) {
		this.fboxmodel = fboxmodel;
	}

	public BigDecimal getFboxlength() {
		return fboxlength;
	}

	public void setFboxlength(BigDecimal fboxlength) {
		this.fboxlength = fboxlength;
	}

	public BigDecimal getFboxwidth() {
		return fboxwidth;
	}

	public void setFboxwidth(BigDecimal fboxwidth) {
		this.fboxwidth = fboxwidth;
	}

	public BigDecimal getFboxheight() {
		return fboxheight;
	}

	public void setFboxheight(BigDecimal fboxheight) {
		this.fboxheight = fboxheight;
	}

	public BigDecimal getFmateriallength() {
		return fmateriallength;
	}

	public void setFmateriallength(BigDecimal fmateriallength) {
		this.fmateriallength = fmateriallength;
	}

	public BigDecimal getFmaterialwidth() {
		return fmaterialwidth;
	}

	public void setFmaterialwidth(BigDecimal fmaterialwidth) {
		this.fmaterialwidth = fmaterialwidth;
	}

	public Integer getFamountpiece() {
		return famountpiece;
	}

	public void setFamountpiece(Integer famountpiece) {
		this.famountpiece = famountpiece;
	}

	public Integer getFboxtype() {
		return fboxtype;
	}

	public void setFboxtype(Integer fboxtype) {
		this.fboxtype = fboxtype;
	}

	public String getFseries() {
		return fseries;
	}

	public void setFseries(String fseries) {
		this.fseries = fseries;
	}

	public String getFstavetype() {
		return fstavetype;
	}
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
