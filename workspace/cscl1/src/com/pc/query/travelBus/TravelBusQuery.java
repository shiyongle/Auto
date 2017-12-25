package com.pc.query.travelBus;

import java.io.Serializable;
import java.util.Calendar;

import org.springframework.format.annotation.DateTimeFormat;

import cn.org.rapid_framework.util.DateConvertUtils;

import com.pc.query.aBase.BaseQuery;

public class TravelBusQuery extends BaseQuery implements Serializable {
	/**
	 *<p>Description: </p>
	 *<p>Company: CSCL-TEAM</p> 
	 * @author CD
	 * @date 2016-8-29 P.M 15:30:58
	 */
	private static final long serialVersionUID = 2860341960190845496L;
	//columns START
    private java.lang.Integer id;//主键
    //日期 
    @DateTimeFormat(pattern="yyyy-MM-dd HH")
	private java.util.Date fbizDateBegin;
	@DateTimeFormat(pattern="yyyy-MM-dd HH")
	private java.util.Date fbizDateEnd;
	
    private java.lang.String fbizManager; //业务经理 
    private java.lang.String fpickupSite; //提货站点 
    private java.math.BigDecimal famount; //数量 
    private java.math.BigDecimal fkilo; //公斤 
    private java.lang.String fdeliverySite; //送达站点 
    private java.lang.String freceiptAddress; //收货地址 
    private java.lang.String fbusPlate; //货运班车车牌 
    private java.lang.String ftransferPlate; //转运班车车牌
    private java.math.BigDecimal fallCost; //总费用 
    private java.math.BigDecimal fdeliverySiteCost; //提货站点费用%50 
    private java.math.BigDecimal fbusCost; //货运班车费用 
    private java.math.BigDecimal ftransferCost; //转运班车费用 
    private java.math.BigDecimal fsendCost; //派送站点费用%30 
    private java.math.BigDecimal fcollectionDelivery; //代收货款 
    private java.lang.String fFreightPaymentParty; //运费支付方 
    private java.lang.String fFreightSettlement; //运费结算情况 
    private java.lang.String fnumber; //单据编号 
    private java.lang.Integer fcreator; //下单人员
    //下单时间
    @DateTimeFormat(pattern="yyyy-MM-dd HH")
	private java.util.Date fcreateTimeBegin;
	@DateTimeFormat(pattern="yyyy-MM-dd HH")
	private java.util.Date fcreateTimeEnd;
    
    private java.lang.String fremark; //备注
	
	//=============================非数据库字段=====================
    private java.lang.String userName;
    private java.lang.String userPhone;
    private java.lang.String searchKey;//关键字
    
	public java.lang.String getSearchKey() {
		return searchKey;
	}
	public java.util.Date getFbizDateBegin() {
		return fbizDateBegin;
	}
	public void setFbizDateBegin(java.util.Date fbizDateBegin) {
		this.fbizDateBegin = fbizDateBegin;
	}
	public java.util.Date getFbizDateEnd() {
		return fbizDateEnd;
	}
	public void setFbizDateEnd(java.util.Date value) {
//		this.fbizDateEnd = fbizDateEnd;
		if(value != null){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(value);
//		calendar.add(Calendar.DAY_OF_MONTH, 1);
		this.fbizDateEnd = calendar.getTime();
	}else {
		this.fbizDateEnd = value;
	}
	}
	public java.util.Date getFcreateTimeBegin() {
		return fcreateTimeBegin;
	}
	public void setFcreateTimeBegin(java.util.Date fcreateTimeBegin) {
		this.fcreateTimeBegin = fcreateTimeBegin;
	}
	public java.util.Date getFcreateTimeEnd() {
		return fcreateTimeEnd;
	}
	public void setFcreateTimeEnd(java.util.Date value) {
//		this.fcreateTimeEnd = fcreateTimeEnd;
		if(value != null){
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(value);
			calendar.add(Calendar.DAY_OF_MONTH, 1);
			this.fcreateTimeEnd = calendar.getTime();
		}else {
			this.fcreateTimeEnd = value;
		}
	}
	
	public void setSearchKey(java.lang.String searchKey) {
		this.searchKey = searchKey;
	}
	public static final String FORMAT_CREATE_TIME = "yyyy-MM-dd HH:mm:ss";
    
	public java.lang.String getUserName() {
		return userName;
	}
	public void setUserName(java.lang.String userName) {
		this.userName = userName;
	}
	
	public java.lang.Integer getId() {
		return id;
	}
	public void setId(java.lang.Integer id) {
		this.id = id;
	}
 
	public java.lang.String getUserPhone() {
		return userPhone;
	}
	public java.lang.String getFbizManager() {
		return fbizManager;
	}
	public void setFbizManager(java.lang.String fbizManager) {
		this.fbizManager = fbizManager;
	}
	public java.lang.String getFpickupSite() {
		return fpickupSite;
	}
	public void setFpickupSite(java.lang.String fpickupSite) {
		this.fpickupSite = fpickupSite;
	}
	public java.math.BigDecimal getFamount() {
		return famount;
	}
	public void setFamount(java.math.BigDecimal famount) {
		this.famount = famount;
	}
	public java.math.BigDecimal getFkilo() {
		return fkilo;
	}
	public void setFkilo(java.math.BigDecimal fkilo) {
		this.fkilo = fkilo;
	}
	public java.lang.String getFdeliverySite() {
		return fdeliverySite;
	}
	public void setFdeliverySite(java.lang.String fdeliverySite) {
		this.fdeliverySite = fdeliverySite;
	}
	public java.lang.String getFreceiptAddress() {
		return freceiptAddress;
	}
	public void setFreceiptAddress(java.lang.String freceiptAddress) {
		this.freceiptAddress = freceiptAddress;
	}
	public java.lang.String getFbusPlate() {
		return fbusPlate;
	}
	public void setFbusPlate(java.lang.String fbusPlate) {
		this.fbusPlate = fbusPlate;
	}
	public java.lang.String getFtransferPlate() {
		return ftransferPlate;
	}
	public void setFtransferPlate(java.lang.String ftransferPlate) {
		this.ftransferPlate = ftransferPlate;
	}
	public java.math.BigDecimal getFallCost() {
		return fallCost;
	}
	public void setFallCost(java.math.BigDecimal fallCost) {
		this.fallCost = fallCost;
	}
	public java.math.BigDecimal getFdeliverySiteCost() {
		return fdeliverySiteCost;
	}
	public void setFdeliverySiteCost(java.math.BigDecimal fdeliverySiteCost) {
		this.fdeliverySiteCost = fdeliverySiteCost;
	}
	public java.math.BigDecimal getFbusCost() {
		return fbusCost;
	}
	public void setFbusCost(java.math.BigDecimal fbusCost) {
		this.fbusCost = fbusCost;
	}
	public java.math.BigDecimal getFtransferCost() {
		return ftransferCost;
	}
	public void setFtransferCost(java.math.BigDecimal ftransferCost) {
		this.ftransferCost = ftransferCost;
	}
	public java.math.BigDecimal getFsendCost() {
		return fsendCost;
	}
	public void setFsendCost(java.math.BigDecimal fsendCost) {
		this.fsendCost = fsendCost;
	}
	public java.math.BigDecimal getFcollectionDelivery() {
		return fcollectionDelivery;
	}
	public void setFcollectionDelivery(java.math.BigDecimal fcollectionDelivery) {
		this.fcollectionDelivery = fcollectionDelivery;
	}
	public java.lang.String getfFreightPaymentParty() {
		return fFreightPaymentParty;
	}
	public void setfFreightPaymentParty(java.lang.String fFreightPaymentParty) {
		this.fFreightPaymentParty = fFreightPaymentParty;
	}
	public java.lang.String getfFreightSettlement() {
		return fFreightSettlement;
	}
	public void setfFreightSettlement(java.lang.String fFreightSettlement) {
		this.fFreightSettlement = fFreightSettlement;
	}
	public java.lang.String getFnumber() {
		return fnumber;
	}
	public void setFnumber(java.lang.String fnumber) {
		this.fnumber = fnumber;
	}
	public java.lang.Integer getFcreator() {
		return fcreator;
	}
	public void setFcreator(java.lang.Integer fcreator) {
		this.fcreator = fcreator;
	}
	public java.lang.String getFremark() {
		return fremark;
	}
	public void setFremark(java.lang.String fremark) {
		this.fremark = fremark;
	}
	public void setUserPhone(java.lang.String userPhone) {
		this.userPhone = userPhone;
	}
    
}