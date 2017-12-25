package com.model.mystock;

import java.util.Date;
/*** 
 * 	备货对象
 * */
public class Mystock implements java.io.Serializable,Cloneable{
	
	public String getFpdtname() {
		return fpdtname;
	}

	public void setFpdtname(String fpdtname) {
		this.fpdtname = fpdtname;
	}

	private static final long serialVersionUID = 5535242863531446632L;
	public static final String ANDROID = "android";
	public static final String DESKTOPS = "desktops";
	public static final String IOS = "ios";
	
	private String fid;						/*** 主键*/
	private String fnumber;					/*** 备货单号*/
	private String fcustproductid;			/*** t_bd_custproduct主键fid*/
	private Integer fplanamount;			/*** 计划数量*/
	private String funit;					/*** 单位*/
	private Date ffinishtime;				/*** 首次发货/要求完成时间*/
	private Date fconsumetime;				/*** 备货周期/预计消耗时间*/
	private Integer fisconsumed=0;			/*** 是否消耗完毕 0否 1是*/
	private String fdescription;			/*** 暂时无用*/
	private Date fcreatetime;				/*** 创建时间*/
	private String fcreateid;				/*** 创建人*/
	private String fcustomerid;				/*** t_bd_customer 主键fid*/
	private String fsaleorderid;			/*** t_ord_saleorder --forderid*/
	private Integer fordered=0;				/*** 是否已下单 0否 1是*/
	private String fordernumber;			/*** 订单编号（生产订单编号）*/
	private Date fordertime;				/*** 下单时间*/
	private String fordermanid;				/*** 下单人*/
	private String forderentryid;			/*** t_ord_saleorder 主键fid*/
	private String fcharactername;			/*** 特性名称*/
	private String fcharacterid;			/*** t_ord_schemedesignentry 主键fid*/
	private String fremark;					/*** 备注*/
	private String fsupplierid;				/*** t_sys_supplier 主键fid*/
	private Integer fstate=0;				/***默认为0：未接受；1,2：已接受*/
	private String fpcmordernumber;			/*** 采购订单号*/
	private Integer faveragefamount=0;		/*** 平均发货量*/
	//以上4个属性，主要用在下单功能表单提交
	private String fpdtname;                /*** 产品名称*/
	private String fpdtspec;                /*** 产品规格*/
	private String suppliername;            /*** 供应商*/
	private String supplierPhone;            /*** 供应商*/
	private String latestpathImg;


	public String getLatestpathImg() {
		return latestpathImg;
	}

	public void setLatestpathImg(String latestpathImg) {
		this.latestpathImg = latestpathImg;
	}
	/**非数据库对应字段,只做封装**/
	
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
	
	//*********************以上属性,只做封装,非对应数据库字段***************end**********************//
	
	public String getSupplierPhone() {
		return supplierPhone;
	}

	public void setSupplierPhone(String supplierPhone) {
		this.supplierPhone = supplierPhone;
	}

	public Mystock() {
	}

	public Mystock(String fid) {
		this.fid = fid;
	}

	public Mystock(String fid, String fnumber, String fcustproductid,
			Integer fplanamount, String funit, Date ffinishtime,
			Date fconsumetime, Integer fisconsumed, String fdescription,
			Date fcreatetime, String fcreateid, String fcustomerid,
			String fsaleorderid, Integer fordered, String fordernumber,
			Date fordertime, String fordermanid, String forderentryid,
			String fcharactername, String fcharacterid, String fremark,
			String fsupplierid, Integer fstate, String fpcmordernumber,Integer faveragefamount) {
		this.fid = fid;
		this.fnumber = fnumber;
		this.fcustproductid = fcustproductid;
		this.fplanamount = fplanamount;
		this.funit = funit;
		this.ffinishtime = ffinishtime;
		this.fconsumetime = fconsumetime;
		this.fisconsumed = fisconsumed;
		this.fdescription = fdescription;
		this.fcreatetime = fcreatetime;
		this.fcreateid = fcreateid;
		this.fcustomerid = fcustomerid;
		this.fsaleorderid = fsaleorderid;
		this.fordered = fordered;
		this.fordernumber = fordernumber;
		this.fordertime = fordertime;
		this.fordermanid = fordermanid;
		this.forderentryid = forderentryid;
		this.fcharactername = fcharactername;
		this.fcharacterid = fcharacterid;
		this.fremark = fremark;
		this.fsupplierid = fsupplierid;
		this.fstate = fstate;
		this.fpcmordernumber = fpcmordernumber;
		this.faveragefamount=faveragefamount;
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

	public String getFcustproductid() {
		return this.fcustproductid;
	}

	public void setFcustproductid(String fcustproductid) {
		this.fcustproductid = fcustproductid;
	}

	public Integer getFplanamount() {
		return this.fplanamount;
	}

	public void setFplanamount(Integer fplanamount) {
		this.fplanamount = fplanamount;
	}

	public String getFunit() {
		return this.funit;
	}

	public void setFunit(String funit) {
		this.funit = funit;
	}

	public Date getFfinishtime() {
		return this.ffinishtime;
	}

	public void setFfinishtime(Date ffinishtime) {
		this.ffinishtime = ffinishtime;
	}

	public Date getFconsumetime() {
		return this.fconsumetime;
	}

	public void setFconsumetime(Date fconsumetime) {
		this.fconsumetime = fconsumetime;
	}

	public Integer getFisconsumed() {
		return this.fisconsumed;
	}

	public void setFisconsumed(Integer fisconsumed) {
		this.fisconsumed = fisconsumed;
	}

	public String getFdescription() {
		return this.fdescription;
	}

	public void setFdescription(String fdescription) {
		this.fdescription = fdescription;
	}

	public Date getFcreatetime() {
		return this.fcreatetime;
	}

	public void setFcreatetime(Date fcreatetime) {
		this.fcreatetime = fcreatetime;
	}

	public String getFcreateid() {
		return this.fcreateid;
	}

	public void setFcreateid(String fcreateid) {
		this.fcreateid = fcreateid;
	}

	public String getFcustomerid() {
		return this.fcustomerid;
	}

	public void setFcustomerid(String fcustomerid) {
		this.fcustomerid = fcustomerid;
	}

	public String getFsaleorderid() {
		return this.fsaleorderid;
	}

	public void setFsaleorderid(String fsaleorderid) {
		this.fsaleorderid = fsaleorderid;
	}

	public Integer getFordered() {
		return this.fordered;
	}

	public void setFordered(Integer fordered) {
		this.fordered = fordered;
	}

	public String getFordernumber() {
		return this.fordernumber;
	}

	public void setFordernumber(String fordernumber) {
		this.fordernumber = fordernumber;
	}

	public Date getFordertime() {
		return this.fordertime;
	}

	public void setFordertime(Date fordertime) {
		this.fordertime = fordertime;
	}

	public String getFordermanid() {
		return this.fordermanid;
	}

	public void setFordermanid(String fordermanid) {
		this.fordermanid = fordermanid;
	}

	public String getForderentryid() {
		return this.forderentryid;
	}

	public void setForderentryid(String forderentryid) {
		this.forderentryid = forderentryid;
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

	public String getFremark() {
		return this.fremark;
	}

	public void setFremark(String fremark) {
		this.fremark = fremark;
	}

	public String getFsupplierid() {
		return this.fsupplierid;
	}

	public void setFsupplierid(String fsupplierid) {
		this.fsupplierid = fsupplierid;
	}

	public Integer getFstate() {
		return this.fstate;
	}

	public void setFstate(Integer fstate) {
		this.fstate = fstate;
	}

	public String getFpcmordernumber() {
		return this.fpcmordernumber;
	}

	public void setFpcmordernumber(String fpcmordernumber) {
		this.fpcmordernumber = fpcmordernumber;
	}

	public Integer getFaveragefamount() {
		return faveragefamount;
	}

	public void setFaveragefamount(Integer faveragefamount) {
		this.faveragefamount = faveragefamount;
	}

	

}
