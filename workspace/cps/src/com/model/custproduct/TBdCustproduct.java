package com.model.custproduct;

import java.math.BigDecimal;
import java.util.Date;

import cn.org.rapid_framework.util.DateConvertUtils;

public class TBdCustproduct implements java.io.Serializable {

	/***
	 *<p>Description: 快速下单</p>
	 *<p>Company: CPS-TEAM</p> 
	 * @author WANGC
	 * @date 2015-7-20 下午3:26:47
	*/
	private static final long serialVersionUID = 3929123407509028007L;
	public static final String FORMAT_TIME = "yyyy-MM-dd HH:mm:ss";
	
	
	/*** 主键*/
	private String fid;
	/*** 产品编号*/
	private String fnumber;
	/*** 产品名称*/
	private String fname;
	/*** 产品规格*/
	private String fspec;
	/*** 单位*/
	private String forderunit;
	/*** 客户主键*/
	private String fcustomerid;
	/*** 备注*/
	private String fdescription;
	/*** 创建人主键*/
	private String fcreatorid;
	/*** 创建时间*/
	private Date fcreatetime;
	/*** 修改人主键*/
	private String flastupdateuserid;
	/*** 修改时间*/
	private Date flastupdatetime;
	/*** 是否关联*/
	private Integer frelationed;
	/*** 物料*/
	private String fmaterial;
	/*** 楞型*/
	private String ftilemodel;
	/*** 特型名称*/
	private String fcharactername;
	/*** 特性主键*/
	private String fcharacterid;
	/*** 类型-套装或普通*/
	private Integer ftype;
	/*** 是否生效*/
	private Integer feffect = 1;
	/*** 产品匹配字符*/
	private String fproductmatching;
	/*** 对应产品FID*/
	private String fproductid;
	/*** 下单次数*/
	private Integer fordercount;
	/*** 下单数量*/
	private Integer forderamount;
	/*** 单价*/
	private BigDecimal fprice;
	/*** 单位*/
	private String funit;
	/*** 是否常用*/
	private Boolean fiscommon=false;
	/*** 总金额*/
	private double fprices = 0;
	/*** 最后次通过快递下单时间*/
	private Date flastordertime;
	/*** 最后次下单数量*/
	private Integer  flastorderfamount=0;
	
	///******用于前端页面显示*****************************************///
	private String supplierId;
	private String supplierName;//制造商
	private java.math.BigDecimal balanceqty;//库存
	private String pathImg;
	
	public String getSupplierName() {
		return supplierName;
	}
	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}
	
	public java.math.BigDecimal getBalanceqty() {
		return balanceqty;
	}
	public void setBalanceqty(java.math.BigDecimal balanceqty) {
		this.balanceqty = balanceqty;
	}
	
	public String getPathImg() {
		return pathImg;
	}
	public void setPathImg(String pathImg) {
		this.pathImg = pathImg;
	}
	
	public String getSupplierId() {
		return supplierId;
	}
	public void setSupplierId(String supplierId) {
		this.supplierId = supplierId;
	}
	///******用于前端页面显示*****************************************///
	public String getFid() {
		return fid;
	}
	public void setFid(String fid) {
		this.fid = fid;
	}
	public String getFnumber() {
		return fnumber;
	}
	public void setFnumber(String fnumber) {
		this.fnumber = fnumber;
	}
	public String getFname() {
		return fname;
	}
	public void setFname(String fname) {
		this.fname = fname;
	}
	public String getFspec() {
		return fspec;
	}
	public void setFspec(String fspec) {
		this.fspec = fspec;
	}
	public String getForderunit() {
		return forderunit;
	}
	public void setForderunit(String forderunit) {
		this.forderunit = forderunit;
	}
	public String getFcustomerid() {
		return fcustomerid;
	}
	public void setFcustomerid(String fcustomerid) {
		this.fcustomerid = fcustomerid;
	}
	public String getFdescription() {
		return fdescription;
	}
	public void setFdescription(String fdescription) {
		this.fdescription = fdescription;
	}
	public String getFcreatorid() {
		return fcreatorid;
	}
	public void setFcreatorid(String fcreatorid) {
		this.fcreatorid = fcreatorid;
	}
	
	public String getFcreatetimeString() {
		return DateConvertUtils.format(getFcreatetime(), FORMAT_TIME);
	}
	public void setFcreatetimeString(String value) {
		setFcreatetime(DateConvertUtils.parse(value, FORMAT_TIME,java.util.Date.class));
	}
	
	public Date getFcreatetime() {
		return fcreatetime;
	}
	public void setFcreatetime(Date fcreatetime) {
		this.fcreatetime = fcreatetime;
	}
	public String getFlastupdateuserid() {
		return flastupdateuserid;
	}
	public void setFlastupdateuserid(String flastupdateuserid) {
		this.flastupdateuserid = flastupdateuserid;
	}
	public Date getFlastupdatetime() {
		return flastupdatetime;
	}
	public void setFlastupdatetime(Date flastupdatetime) {
		this.flastupdatetime = flastupdatetime;
	}
	public Integer getFrelationed() {
		return frelationed;
	}
	public void setFrelationed(Integer frelationed) {
		this.frelationed = frelationed;
	}
	public String getFmaterial() {
		return fmaterial;
	}
	public void setFmaterial(String fmaterial) {
		this.fmaterial = fmaterial;
	}
	public String getFtilemodel() {
		return ftilemodel;
	}
	public void setFtilemodel(String ftilemodel) {
		this.ftilemodel = ftilemodel;
	}
	public String getFcharactername() {
		return fcharactername;
	}
	public void setFcharactername(String fcharactername) {
		this.fcharactername = fcharactername;
	}
	public String getFcharacterid() {
		return fcharacterid;
	}
	public void setFcharacterid(String fcharacterid) {
		this.fcharacterid = fcharacterid;
	}
	public Integer getFtype() {
		return ftype;
	}
	public void setFtype(Integer ftype) {
		this.ftype = ftype;
	}
	public Integer getFeffect() {
		return feffect;
	}
	public void setFeffect(Integer feffect) {
		this.feffect = feffect;
	}
	public String getFproductmatching() {
		return fproductmatching;
	}
	public void setFproductmatching(String fproductmatching) {
		this.fproductmatching = fproductmatching;
	}
	public String getFproductid() {
		return fproductid;
	}
	public void setFproductid(String fproductid) {
		this.fproductid = fproductid;
	}
	public Integer getFordercount() {
		return fordercount;
	}
	public void setFordercount(Integer fordercount) {
		this.fordercount = fordercount;
	}
	public Integer getForderamount() {
		return forderamount;
	}
	public void setForderamount(Integer forderamount) {
		this.forderamount = forderamount;
	}
	public BigDecimal getFprice() {
		return fprice;
	}
	public void setFprice(BigDecimal fprice) {
		this.fprice = fprice;
	}
	public Boolean getFiscommon() {
		return fiscommon;
	}
	public void setFiscommon(Boolean fiscommon) {
		this.fiscommon = fiscommon;
	}
	public String getFunit() {
		return funit;
	}
	public void setFunit(String funit) {
		this.funit = funit;
	}
	public double getFprices() {
		return fprices;
	}
	public void setFprices(double fprices) {
		this.fprices = fprices;
	}
	
	public String getFlastordertimeString() {
		return DateConvertUtils.format(getFlastordertime(), FORMAT_TIME);
	}
	public void setFlastordertimeString(String value) {
		setFlastordertime(DateConvertUtils.parse(value, FORMAT_TIME,java.util.Date.class));
	}
	
	public Date getFlastordertime() {
		return flastordertime;
	}
	public void setFlastordertime(Date flastordertime) {
		this.flastordertime = flastordertime;
	}
	public Integer getFlastorderfamount() {
		return flastorderfamount;
	}
	public void setFlastorderfamount(Integer flastorderfamount) {
		this.flastorderfamount = flastorderfamount;
	}
}
