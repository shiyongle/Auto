package com.pc.model;

import cn.org.rapid_framework.util.DateConvertUtils;

/**
 * @author Cici
 * 收支明细
 */

public class CL_FinanceStatement {

	private int fid;
	private java.util.Date fcreateTime;//创建时间
	private String number;//交易号
	private String frelatedId;//相关业务对应的表的ID
	private String forderId;//订单号number
	private int fbusinessType;//业务类型(1下单支付2补交货款3运营异常4订单完成5提现6充值7转介绍奖励8货主退款9绑卡10订单运费调整11运费上缴12司机运费线上(代收货款、运费到付))
	private java.math.BigDecimal famount;//金额
	private int ftype;//收支(1收入,-1支出)
	private int fuserroleId;//用户ID
	private String fuserid;//用户ID
	private int fpayType;//支付类型0余额1支付宝2微信3银联4运费到付5月结
	private String fremark;
	private java.math.BigDecimal fbalance;//上次余额
	private int fstatus = 0;	//明细状态(0:支付中 1:成功 2:失败 3:退款成功6取消)
	private java.math.BigDecimal freight;//订单运费
	private String fpayOrder;//支付订单号

	public java.math.BigDecimal getFbalance() {
		return fbalance;
	}
	public void setFbalance(java.math.BigDecimal fbalance) {
		this.fbalance = fbalance;
	}
	public int getFstatus() {
		return fstatus;
	}
	public void setFstatus(int fstatus) {
		this.fstatus = fstatus;
	}
	public static final String FORMAT_CREATE_TIME = "yyyy-MM-dd HH:mm";

	/**************前端数据查询封装  Start  *****************/
	//用户表
	private String fusername;
	
	//提现表
	private java.util.Date cfapplytime;
	private Integer cfwithdrawtype;
	private String cfalipayid;
	private String cfbankaccount;
	private String cfbankname;
	private String cfbankaddress;
	private String cftreatment;
	private String fserial_num;
	private Integer cfstate;
	private Integer cfrejecttype;
	private String carNum;//司机车牌  by twr
	private String driverName;//司机姓名 by twr
	private java.math.BigDecimal sumFamount ;//司机的金额
	private java.math.BigDecimal forderyMoney ;//订单运费
	private String fname;//客户名称
	private String fuserPhone;
	
	
	
	public String getFuserPhone() {
		return fuserPhone;
	}
	public void setFuserPhone(String fuserPhone) {
		this.fuserPhone = fuserPhone;
	}
	public String getFname() {
		return fname;
	}
	public void setFname(String fname) {
		this.fname = fname;
	}
	public java.math.BigDecimal getForderyMoney() {
		return forderyMoney;
	}
	public void setForderyMoney(java.math.BigDecimal forderyMoney) {
		this.forderyMoney = forderyMoney;
	}
	public java.math.BigDecimal getSumFamount() {
		return sumFamount;
	}
	public void setSumFamount(java.math.BigDecimal sumFamount) {
		this.sumFamount = sumFamount;
	}
	public String getCarNum() {
		return carNum;
	}
	public void setCarNum(String carNum) {
		this.carNum = carNum;
	}
	public String getDriverName() {
		return driverName;
	}
	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}
	//异常表
//	private Integer catakeproblem;
//	private Integer cacarproblem;
//	private Integer carecproblem;
	/**************前端数据查询封装     End *****************/
	public String getCreateTimeString() {
		return DateConvertUtils.format(getFcreateTime(), FORMAT_CREATE_TIME);
	}
	public void setCreateTimeString(String value) {
		setFcreateTime(DateConvertUtils.parse(value, FORMAT_CREATE_TIME,java.util.Date.class));
	}

	public String getFuserid() {
		return fuserid;
	}
	public void setFuserid(String fuserid) {
		this.fuserid = fuserid;
	}
	public String getFrelatedId() {
		return frelatedId;
	}
	public void setFrelatedId(String frelatedId) {
		this.frelatedId = frelatedId;
	}
	public int getFid() {
		return fid;
	}
	public void setFid(int fid) {
		this.fid = fid;
	}

	public java.util.Date getFcreateTime() {
		return fcreateTime;
	}
	public void setFcreateTime(java.util.Date fcreateTime) {
		this.fcreateTime = fcreateTime;
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public String getForderId() {
		return forderId;
	}
	public void setForderId(String forderId) {
		this.forderId = forderId;
	}

	public int getFbusinessType() {
		return fbusinessType;
	}
	public void setFbusinessType(int fbusinessType) {
		this.fbusinessType = fbusinessType;
	}

	public java.math.BigDecimal getFamount() {
		return famount;
	}
	public void setFamount(java.math.BigDecimal famount) {
		this.famount = famount;
	}

	public int getFtype() {
		return ftype;
	}
	public void setFtype(int ftype) {
		this.ftype = ftype;
	}

	public int getFuserroleId() {
		return fuserroleId;
	}
	public void setFuserroleId(int fuserroleId) {
		this.fuserroleId = fuserroleId;
	}
	
	public int getFpayType() {
		return fpayType;
	}
	public void setFpayType(int fpayType) {
		this.fpayType = fpayType;
	}
	
	
	/***********前端数据封装*************/ 

	public String getFusername() {
		return fusername;
	}
	public void setFusername(String fusername) {
		this.fusername = fusername;
	}
	public java.util.Date getCfapplytime() {
		return cfapplytime;
	}
	public void setCfapplytime(java.util.Date cfapplytime) {
		this.cfapplytime = cfapplytime;
	}
	public Integer getCfwithdrawtype() {
		return cfwithdrawtype;
	}
	public void setCfwithdrawtype(Integer cfwithdrawtype) {
		this.cfwithdrawtype = cfwithdrawtype;
	}
	public String getCfalipayid() {
		return cfalipayid;
	}
	public void setCfalipayid(String cfalipayid) {
		this.cfalipayid = cfalipayid;
	}
	public String getCfbankaccount() {
		return cfbankaccount;
	}
	public void setCfbankaccount(String cfbankaccount) {
		this.cfbankaccount = cfbankaccount;
	}
	public String getCfbankname() {
		return cfbankname;
	}
	public void setCfbankname(String cfbankname) {
		this.cfbankname = cfbankname;
	}
	public String getCfbankaddress() {
		return cfbankaddress;
	}
	public void setCfbankaddress(String cfbankaddress) {
		this.cfbankaddress = cfbankaddress;
	}
	public String getCftreatment() {
		return cftreatment;
	}
	public void setCftreatment(String cftreatment) {
		this.cftreatment = cftreatment;
	}
	public String getFserial_num() {
		return fserial_num;
	}
	public void setFserial_num(String fserial_num) {
		this.fserial_num = fserial_num;
	}
	public Integer getCfstate() {
		return cfstate;
	}
	public void setCfstate(Integer cfstate) {
		this.cfstate = cfstate;
	}
	public Integer getCfrejecttype() {
		return cfrejecttype;
	}
	public void setCfrejecttype(Integer cfrejecttype) {
		this.cfrejecttype = cfrejecttype;
	}
	public String getFremark() {
		return fremark;
	}
	public void setFremark(String fremark) {
		this.fremark = fremark;
	}
	public java.math.BigDecimal getFreight() {
		return freight;
	}
	public void setFreight(java.math.BigDecimal freight) {
		this.freight = freight;
	}
	public String getFpayOrder() {
		return fpayOrder;
	}
	public void setFpayOrder(String fpayOrder) {
		this.fpayOrder = fpayOrder;
	}
}
