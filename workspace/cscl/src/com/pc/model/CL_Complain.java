package com.pc.model;

import java.math.BigDecimal;
import java.util.Date;

import cn.org.rapid_framework.util.DateConvertUtils;

public class CL_Complain implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 720020233246787971L;
	protected static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm";

	private int fid;						   // 主键
	private int forderId;                      //订单id
	private String fcomplainUserName;		   //投诉人
	private int fcomplainType;				   //投诉类型
	private String fcomplainContent;		   //投诉记录
	private String fcomplainCommunicateContent;//沟通记录
	private BigDecimal fdriverFineAmount;	   //司机扣款金额
	private boolean fdriverAccountDisable;     //帐号是否禁用
	private Date fdriverAccountDisableStart;   //帐号禁用开始时间
	private Date fdriverAccountDisableEnd;     //帐号禁用结束时间
	private boolean fdriverFreezeDisable;      //账户是否冻结
	private Date fdriverFreezeDisableStart;    //账户冻结开始时间
	private Date fdriverFreezeDisableEnd;      //账户冻结结束时间
	private BigDecimal fdriverRewardAmount;    //司机账户返利金额
	private String fdriverRemark;              //司机投诉处理结果说明
	private BigDecimal fshipperFineAmount;     //货主扣款金额
	//用int前端值不填入时 会报 badrequest400
	//private int fshipperRewardCouponsAmount;   //货主返利好运券数量
	private Integer fshipperRewardCouponsAmount;   //货主返利好运券数量
	private BigDecimal fshipperRewardCouponsDollars;//货主返利好运券面额
	private Date fshipperRewardCouponsStart;   //货主返利好运券有效期
	private Date fshipperRewardCouponsEnd;     //货主返利好运券有效期
	private String fshipperRemark;			   //货主投诉处理结果说明
	private int fcreator;                      //创建人
	private Date fcreateTime;				   //创建时间
	private Integer fisdeal;				   //是否受理
	private String fdealinfo;				   //受理情况1、受理人2、受理时间
	private int fdealMan;					   //受理人
	private Date fdealTime;					   //受理时间
	private String fremark;					   //退回原因
	//  private String fdriverAccountDisableStringStart;
// 	private String fdriverAccountDisableStringEnd;
//	private String fdriverFreezeDisableStringStart;    
//	private String fdriverFreezeDisableStringEnd;      
//	private String fshipperRewardCouponsStringStart;  
//	private String fshipperRewardCouponsStringEnd;     
//	private String fcreateTimeString;
	//非表字段
	private String forderNum;  //订单号
	private String fdriverName;   //司机名称
	private String fluckDriver;	 //是否好运司机
	private String fuserName;     //货主名称
	private String fcreate_name;
	private String fdeal_name;
	
	private String orderNumber;
	private String orderType;
	private String orderLoadTime;
	private String orderGoodType;
	private String carType;
	private String driverName;
	private String driverPhone;
	private String orderAddServer;
	private String orderMileage;
	private String orderPayAmount;
	private String userType;
	private String userName;
	private String userPhone;
	private String sendAddress;
	private String receiveAddress;
	private String receiveUser;
	private String receivePhone;
	private String complainType;
	private BigDecimal cusBalance;
	private BigDecimal driBalance;
	
	
	public BigDecimal getCusBalance() {
		return cusBalance;
	}
	public void setCusBalance(BigDecimal cusBalance) {
		this.cusBalance = cusBalance;
	}
	public BigDecimal getDriBalance() {
		return driBalance;
	}
	public void setDriBalance(BigDecimal driBalance) {
		this.driBalance = driBalance;
	}
	public String getComplainType() {
		return complainType;
	}
	public void setComplainType(String complainType) {
		this.complainType = complainType;
	}
	public String getOrderNumber() {
		return orderNumber;
	}
	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}
	public String getOrderType() {
		return orderType;
	}
	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}
	public String getOrderLoadTime() {
		return orderLoadTime;
	}
	public void setOrderLoadTime(String orderLoadTime) {
		this.orderLoadTime = orderLoadTime;
	}
	public String getOrderGoodType() {
		return orderGoodType;
	}
	public void setOrderGoodType(String orderGoodType) {
		this.orderGoodType = orderGoodType;
	}
	public String getCarType() {
		return carType;
	}
	public void setCarType(String carType) {
		this.carType = carType;
	}
	public String getDriverName() {
		return driverName;
	}
	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}
	public String getDriverPhone() {
		return driverPhone;
	}
	public void setDriverPhone(String driverPhone) {
		this.driverPhone = driverPhone;
	}
	public String getOrderAddServer() {
		return orderAddServer;
	}
	public void setOrderAddServer(String orderAddServer) {
		this.orderAddServer = orderAddServer;
	}
	public String getOrderMileage() {
		return orderMileage;
	}
	public void setOrderMileage(String orderMileage) {
		this.orderMileage = orderMileage;
	}
	public String getOrderPayAmount() {
		return orderPayAmount;
	}
	public void setOrderPayAmount(String orderPayAmount) {
		this.orderPayAmount = orderPayAmount;
	}
	public String getUserType() {
		return userType;
	}
	public void setUserType(String userType) {
		this.userType = userType;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserPhone() {
		return userPhone;
	}
	public void setUserPhone(String userPhone) {
		this.userPhone = userPhone;
	}
	public String getSendAddress() {
		return sendAddress;
	}
	public void setSendAddress(String sendAddress) {
		this.sendAddress = sendAddress;
	}
	public String getReceiveAddress() {
		return receiveAddress;
	}
	public void setReceiveAddress(String receiveAddress) {
		this.receiveAddress = receiveAddress;
	}
	public String getReceiveUser() {
		return receiveUser;
	}
	public void setReceiveUser(String receiveUser) {
		this.receiveUser = receiveUser;
	}
	public String getReceivePhone() {
		return receivePhone;
	}
	public void setReceivePhone(String receivePhone) {
		this.receivePhone = receivePhone;
	}
	public String getFremark() {
		return fremark;
	}
	public void setFremark(String fremark) {
		this.fremark = fremark;
	}
	public String getFcreate_name() {
		return fcreate_name;
	}
	public void setFcreate_name(String fcreate_name) {
		this.fcreate_name = fcreate_name;
	}
	public String getFdeal_name() {
		return fdeal_name;
	}
	public void setFdeal_name(String fdeal_name) {
		this.fdeal_name = fdeal_name;
	}
	public int getFdealMan() {
		return fdealMan;
	}
	public void setFdealMan(int fdealMan) {
		this.fdealMan = fdealMan;
	}
	public Date getFdealTime() {
		return fdealTime;
	}
	public void setFdealTime(Date fdealTime) {
		this.fdealTime = fdealTime;
	}
	
	public String getFdealinfo() {
		return fdealinfo;
	}
	public void setFdealinfo(String fdealinfo) {
		this.fdealinfo = fdealinfo;
	}
	public int getFid() {
		return fid;
	}
	public void setFid(int fid) {
		this.fid = fid;
	}
	public Integer getFisdeal() {
		return fisdeal;
	}
	public void setFisdeal(Integer fisdeal) {
		this.fisdeal = fisdeal;
	}
	public int getForderId() {
		return forderId;
	}
	public void setForderId(int forderId) {
		this.forderId = forderId;
	}
	public String getFcomplainUserName() {
		return fcomplainUserName;
	}
	public void setFcomplainUserName(String fcomplainUserName) {
		this.fcomplainUserName = fcomplainUserName;
	}
	public int getFcomplainType() {
		return fcomplainType;
	}
	public void setFcomplainType(int fcomplainType) {
		this.fcomplainType = fcomplainType;
	}
	public String getFcomplainContent() {
		return fcomplainContent;
	}
	public void setFcomplainContent(String fcomplainContent) {
		this.fcomplainContent = fcomplainContent;
	}
	public String getFcomplainCommunicateContent() {
		return fcomplainCommunicateContent;
	}
	public void setFcomplainCommunicateContent(String fcomplainCommunicateContent) {
		this.fcomplainCommunicateContent = fcomplainCommunicateContent;
	}
	public BigDecimal getFdriverFineAmount() {
		return fdriverFineAmount;
	}
	public void setFdriverFineAmount(BigDecimal fdriverFineAmount) {
		this.fdriverFineAmount = fdriverFineAmount;
	}
	public boolean isFdriverAccountDisable() {
		return fdriverAccountDisable;
	}
	public void setFdriverAccountDisable(boolean fdriverAccountDisable) {
		this.fdriverAccountDisable = fdriverAccountDisable;
	}
	public Date getFdriverAccountDisableStart() {
		return fdriverAccountDisableStart;
	}
	public void setFdriverAccountDisableStart(Date fdriverAccountDisableStart) {
		this.fdriverAccountDisableStart = fdriverAccountDisableStart;
	}
	public Date getFdriverAccountDisableEnd() {
		return fdriverAccountDisableEnd;
	}
	public void setFdriverAccountDisableEnd(Date fdriverAccountDisableEnd) {
		this.fdriverAccountDisableEnd = fdriverAccountDisableEnd;
	}
	public boolean isFdriverFreezeDisable() {
		return fdriverFreezeDisable;
	}
	public void setFdriverFreezeDisable(boolean fdriverFreezeDisable) {
		this.fdriverFreezeDisable = fdriverFreezeDisable;
	}
	public Date getFdriverFreezeDisableStart() {
		return fdriverFreezeDisableStart;
	}
	public void setFdriverFreezeDisableStart(Date fdriverFreezeDisableStart) {
		this.fdriverFreezeDisableStart = fdriverFreezeDisableStart;
	}
	public Date getFdriverFreezeDisableEnd() {
		return fdriverFreezeDisableEnd;
	}
	public void setFdriverFreezeDisableEnd(Date fdriverFreezeDisableEnd) {
		this.fdriverFreezeDisableEnd = fdriverFreezeDisableEnd;
	}
	public BigDecimal getFdriverRewardAmount() {
		return fdriverRewardAmount;
	}
	public void setFdriverRewardAmount(BigDecimal fdriverRewardAmount) {
		this.fdriverRewardAmount = fdriverRewardAmount;
	}
	public String getFdriverRemark() {
		return fdriverRemark;
	}
	public void setFdriverRemark(String fdriverRemark) {
		this.fdriverRemark = fdriverRemark;
	}
	public BigDecimal getFshipperFineAmount() {
		return fshipperFineAmount;
	}
	public void setFshipperFineAmount(BigDecimal fshipperFineAmount) {
		this.fshipperFineAmount = fshipperFineAmount;
	}
	public Integer getFshipperRewardCouponsAmount() {
		return fshipperRewardCouponsAmount;
	}
	public void setFshipperRewardCouponsAmount(Integer fshipperRewardCouponsAmount) {
		this.fshipperRewardCouponsAmount = fshipperRewardCouponsAmount;
	}
	public BigDecimal getFshipperRewardCouponsDollars() {
		return fshipperRewardCouponsDollars;
	}
	public void setFshipperRewardCouponsDollars(
			BigDecimal fshipperRewardCouponsDollars) {
		this.fshipperRewardCouponsDollars = fshipperRewardCouponsDollars;
	}
	public Date getFshipperRewardCouponsStart() {
		return fshipperRewardCouponsStart;
	}
	public void setFshipperRewardCouponsStart(Date fshipperRewardCouponsStart) {
		this.fshipperRewardCouponsStart = fshipperRewardCouponsStart;
	}
	public Date getFshipperRewardCouponsEnd() {
		return fshipperRewardCouponsEnd;
	}
	public void setFshipperRewardCouponsEnd(Date fshipperRewardCouponsEnd) {
		this.fshipperRewardCouponsEnd = fshipperRewardCouponsEnd;
	}
	public String getFshipperRemark() {
		return fshipperRemark;
	}
	public void setFshipperRemark(String fshipperRemark) {
		this.fshipperRemark = fshipperRemark;
	}
	public int getFcreator() {
		return fcreator;
	}
	public void setFcreator(int fcreator) {
		this.fcreator = fcreator;
	}
	public Date getFcreateTime() {
		return fcreateTime;
	}
	public void setFcreateTime(Date fcreateTime) {
		this.fcreateTime = fcreateTime;
	}
	public String getForderNum() {
		return forderNum;
	}
	public void setForderNum(String forderNum) {
		this.forderNum = forderNum;
	}
	public String getFdriverName() {
		return fdriverName;
	}
	public void setFdriverName(String fdriverName) {
		this.fdriverName = fdriverName;
	}
	public String getFluckDriver() {
		return fluckDriver;
	}
	public void setFluckDriver(String fluckDriver) {
		this.fluckDriver = fluckDriver;
	}
	public String getFuserName() {
		return fuserName;
	}
	public void setFuserName(String fuserName) {
		this.fuserName = fuserName;
	}
	public String getFdriverAccountDisableStringStart() {
		return DateConvertUtils.format(getFdriverAccountDisableStart(), DATE_TIME_FORMAT);	
	}
	public void setFdriverAccountDisableStringStart(String value) {
		setFdriverAccountDisableStart(DateConvertUtils.parse(value, DATE_TIME_FORMAT,java.util.Date.class));
	}
	public String getFdriverAccountDisableStringEnd() {
		return DateConvertUtils.format(getFdriverAccountDisableEnd(), DATE_TIME_FORMAT);	
	}
	public void setFdriverAccountDisableStringEnd(String value) {
		setFdriverAccountDisableEnd(DateConvertUtils.parse(value, DATE_TIME_FORMAT,java.util.Date.class));
	}
	public String getFdriverFreezeDisableStringStart() {
		return DateConvertUtils.format(getFdriverFreezeDisableStart(), DATE_TIME_FORMAT);	
	}
	public void setFdriverFreezeDisableStringStart(String value) {
		setFdriverFreezeDisableStart(DateConvertUtils.parse(value, DATE_TIME_FORMAT,java.util.Date.class));
	}
	public String getFdriverFreezeDisableStringEnd() {
		return DateConvertUtils.format(getFdriverFreezeDisableEnd(), DATE_TIME_FORMAT);	
	}
	public void setFdriverFreezeDisableStringEnd(String value) {
		setFdriverFreezeDisableEnd(DateConvertUtils.parse(value, DATE_TIME_FORMAT,java.util.Date.class));
	}
	public String getFshipperRewardCouponsStringStart() {
		return DateConvertUtils.format(getFshipperRewardCouponsStart(), DATE_TIME_FORMAT);	
	}
	public void setFshipperRewardCouponsStringStart(String value) {
		setFshipperRewardCouponsStart(DateConvertUtils.parse(value, DATE_TIME_FORMAT,java.util.Date.class));
	}
	public String getFshipperRewardCouponsStringEnd() {
		return DateConvertUtils.format(getFshipperRewardCouponsEnd(), DATE_TIME_FORMAT);	
	}
	public void setFshipperRewardCouponsStringEnd(String value) {
		setFshipperRewardCouponsEnd(DateConvertUtils.parse(value, DATE_TIME_FORMAT,java.util.Date.class));
	}
	public String getFcreateTimeString() {
		return DateConvertUtils.format(getFcreateTime(), DATE_TIME_FORMAT);	
	}
	public void setFcreateTimeString(String value) {
		this.setFcreateTime(DateConvertUtils.parse(value, DATE_TIME_FORMAT,java.util.Date.class));
	}
	public String getFdealTimeString(){
		return DateConvertUtils.format(getFdealTime(), DATE_TIME_FORMAT);
	}
	public void setFdealTimeString(String value){
		this.setFdealTime(DateConvertUtils.parse(value, DATE_TIME_FORMAT,java.util.Date.class));
	}
	
}
