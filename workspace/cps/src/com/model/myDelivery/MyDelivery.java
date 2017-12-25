package com.model.myDelivery;

import java.math.BigInteger;

/****我的收货*/
public class MyDelivery {
	  private String sdentryid;
	  private String fsupplierid;
	  private String fsuppliername;//制造商
	  private BigInteger fboxtype;//订单类型 0纸箱 1纸板
	  private String saleorderNumber;//收货单号
	  private String fpcmordernumber;//采购订单号
	  private String cutpdtname;//产品名称
	  private String fboxspec;//纸箱规格
	  private String fmaterialspec;//下料规格
	  private Double famount;//出库数量
	  private String farrivetime;//出库时间
	  private String faddress;//收货地址
	  private String fdescription;//备注
	  private Integer faffirmed;
	  private String forderunit;
	  private String fcustoermid;
	  private String fcustname;
	  private String fid;
	  private java.math.BigDecimal famounts;
  
	public String getSdentryid() {
		return sdentryid;
	}
	public void setSdentryid(String sdentryid) {
		this.sdentryid = sdentryid;
	}
	public String getFsupplierid() {
		return fsupplierid;
	}
	public void setFsupplierid(String fsupplierid) {
		this.fsupplierid = fsupplierid;
	}
	public String getFsuppliername() {
		return fsuppliername;
	}
	public void setFsuppliername(String fsuppliername) {
		this.fsuppliername = fsuppliername;
	}
	public BigInteger getFboxtype() {
		return fboxtype;
	}
	public void setFboxtype(BigInteger fboxtype) {
		this.fboxtype = fboxtype;
	}
	public String getSaleorderNumber() {
		return saleorderNumber;
	}
	public void setSaleorderNumber(String saleorderNumber) {
		this.saleorderNumber = saleorderNumber;
	}
	public String getFpcmordernumber() {
		return fpcmordernumber;
	}
	public void setFpcmordernumber(String fpcmordernumber) {
		this.fpcmordernumber = fpcmordernumber;
	}
	public String getCutpdtname() {
		return cutpdtname;
	}
	public void setCutpdtname(String cutpdtname) {
		this.cutpdtname = cutpdtname;
	}
	public String getFboxspec() {
		return fboxspec;
	}
	public void setFboxspec(String fboxspec) {
		this.fboxspec = fboxspec;
	}
	public String getFmaterialspec() {
		return fmaterialspec;
	}
	public void setFmaterialspec(String fmaterialspec) {
		this.fmaterialspec = fmaterialspec;
	}
	public Double getFamount() {
		return famount;
	}
	public void setFamount(Double famount) {
		this.famount = famount;
	}
	public String getFarrivetime() {
		return farrivetime;
	}
	public void setFarrivetime(String farrivetime) {
		this.farrivetime = farrivetime;
	}
	public String getFaddress() {
		return faddress;
	}
	public void setFaddress(String faddress) {
		this.faddress = faddress;
	}
	public String getFdescription() {
		return fdescription;
	}
	public void setFdescription(String fdescription) {
		this.fdescription = fdescription;
	}
	public Integer getFaffirmed() {
		return faffirmed;
	}
	public void setFaffirmed(Integer faffirmed) {
		this.faffirmed = faffirmed;
	}
	public String getForderunit() {
		return forderunit;
	}
	public void setForderunit(String forderunit) {
		this.forderunit = forderunit;
	}
	public String getFcustoermid() {
		return fcustoermid;
	}
	public void setFcustoermid(String fcustoermid) {
		this.fcustoermid = fcustoermid;
	}
	public String getFcustname() {
		return fcustname;
	}
	public void setFcustname(String fcustname) {
		this.fcustname = fcustname;
	}
	public String getFid() {
		return fid;
	}
	public void setFid(String fid) {
		this.fid = fid;
	}
	public java.math.BigDecimal getFamounts() {
		return famounts;
	}
	public void setFamounts(java.math.BigDecimal famounts) {
		this.famounts = famounts;
	}
	
  
}
