package com.pc.model;

import java.math.BigDecimal;
import java.util.Date;

import cn.org.rapid_framework.util.DateConvertUtils;

public class CL_Protocol {
	// columns START
	private java.lang.Integer id;// 主键
	private java.lang.String fname;// 协议名称
	private java.lang.Integer type;// 协议类型
	private java.lang.Integer carSpecId;// 车辆规格
	private java.math.BigDecimal startPrice;// 保底价格（起始价格）
	private java.math.BigDecimal timePrice;// 包天价格
	private java.lang.Integer status;// 状态 0 失效 1 生效
	private java.lang.String pubRemark;// 备注
	private java.lang.Integer funitId;// 零担单位类型 1：件 2：托 3：面积 4：体积 5：重量;
	private java.lang.Integer userRoleId;// 用户ID
	private java.util.Date createTime;// 创建时间
	private java.math.BigDecimal outNumprice;// 超出数量
	private java.math.BigDecimal outKilometre;// 超出公里单价
	private java.math.BigDecimal out5_20_kilometre;// 超出5-20公里价格(包含20公里)
	private java.math.BigDecimal out20_50_kilometre;// 超出20-50公里价格（包含50公里）
	private java.math.BigDecimal out50_kilometre;// 超出50公里价格
	private java.math.BigDecimal startKilometre;// 保底公里
	private java.math.BigDecimal startNumber;// 保底数量
	private java.math.BigDecimal fpurposeAmount;// 代收金额
	private java.lang.String fincrementServe;// 增值服务
	private java.lang.Integer fpaymethod;// 支付方式
	private java.lang.Integer fopint;// 保底点数
	private java.math.BigDecimal fdiscount;// 折扣
	private java.math.BigDecimal foutopint;// 超出点数
	private BigDecimal fadd_service_1;// 装货服务
	private BigDecimal fadd_service_2;// 卸货服务
	private Integer fcreator;
	private Integer foperator;
	private Date foperate_time;
	private Integer fdriver_type;

	public java.math.BigDecimal getOutKilometre() {
		return outKilometre;
	}

	public void setOutKilometre(java.math.BigDecimal outKilometre) {
		this.outKilometre = outKilometre;
	}

	public void setOut5_20_kilometre(java.math.BigDecimal out5_20_kilometre) {
		this.out5_20_kilometre = out5_20_kilometre;
	}

	public Integer getFdriver_type() {
		return fdriver_type;
	}

	public void setFdriver_type(Integer fdriver_type) {
		this.fdriver_type = fdriver_type;
	}

	public Integer getFcreator() {
		return fcreator;
	}

	public void setFcreator(Integer fcreator) {
		this.fcreator = fcreator;
	}

	public BigDecimal getFadd_service_1() {
		return fadd_service_1;
	}

	public void setFadd_service_1(BigDecimal fadd_service_1) {
		this.fadd_service_1 = fadd_service_1;
	}

	public BigDecimal getFadd_service_2() {
		return fadd_service_2;
	}

	public void setFadd_service_2(BigDecimal fadd_service_2) {
		this.fadd_service_2 = fadd_service_2;
	}

	public java.lang.Integer getFopint() {
		return fopint;
	}

	public void setFopint(java.lang.Integer fopint) {
		this.fopint = fopint;
	}

	public java.math.BigDecimal getFdiscount() {
		return fdiscount;
	}

	public void setFdiscount(java.math.BigDecimal fdiscount) {
		this.fdiscount = fdiscount;
	}

	public java.math.BigDecimal getFoutopint() {
		return foutopint;
	}

	public void setFoutopint(java.math.BigDecimal foutopint) {
		this.foutopint = foutopint;
	}

	public java.math.BigDecimal getFpurposeAmount() {
		return fpurposeAmount;
	}

	public void setFpurposeAmount(java.math.BigDecimal fpurposeAmount) {
		this.fpurposeAmount = fpurposeAmount;
	}

	public java.lang.String getFincrementServe() {
		return fincrementServe;
	}

	public void setFincrementServe(java.lang.String fincrementServe) {
		this.fincrementServe = fincrementServe;
	}

	public java.lang.Integer getFpaymethod() {
		return fpaymethod;
	}

	public void setFpaymethod(java.lang.Integer fpaymethod) {
		this.fpaymethod = fpaymethod;
	}

	// =============================非数据库字段=====================
	private java.lang.String carSpecName;
	private java.lang.String userName;
	private java.lang.String userPhone;
	private java.lang.String otherTypeName;// 零担单位的名称
	private java.lang.String searchKey;// 关键字
	private String driverName;
	private String cusName;

	public String getCusName() {
		return cusName;
	}

	public void setCusName(String cusName) {
		this.cusName = cusName;
	}

	public String getDriverName() {
		return driverName;
	}

	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}

	public java.lang.String getSearchKey() {
		return searchKey;
	}

	public void setSearchKey(java.lang.String searchKey) {
		this.searchKey = searchKey;
	}

	public java.lang.String getOtherTypeName() {
		return otherTypeName;
	}

	public void setOtherTypeName(java.lang.String otherTypeName) {
		this.otherTypeName = otherTypeName;
	}

	public static final String FORMAT_CREATE_TIME = "yyyy-MM-dd HH:mm:ss";

	public java.math.BigDecimal getTimePrice() {
		return timePrice;
	}

	public void setTimePrice(java.math.BigDecimal timePrice) {
		this.timePrice = timePrice;
	}

	public String getCreateTimeString() {
		return DateConvertUtils.format(getCreateTime(), FORMAT_CREATE_TIME);
	}

	public void setCreateTimeString(String value) {
		setCreateTime(DateConvertUtils.parse(value, FORMAT_CREATE_TIME,
				java.util.Date.class));
	}

	public java.lang.String getUserName() {
		return userName;
	}

	public void setUserName(java.lang.String userName) {
		this.userName = userName;
	}

	public java.lang.String getCarSpecName() {
		return carSpecName;
	}

	public void setCarSpecName(java.lang.String carSpecName) {
		this.carSpecName = carSpecName;
	}

	public java.lang.Integer getId() {
		return id;
	}

	public void setId(java.lang.Integer id) {
		this.id = id;
	}

	public java.lang.Integer getType() {
		return type;
	}

	public java.lang.String getFname() {
		return fname;
	}

	public void setFname(java.lang.String fname) {
		this.fname = fname;
	}

	public java.lang.Integer getFunitId() {
		return funitId;
	}

	public void setFunitId(java.lang.Integer funitId) {
		this.funitId = funitId;
	}

	public void setType(java.lang.Integer type) {
		this.type = type;
	}

	public java.lang.Integer getCarSpecId() {
		return carSpecId;
	}

	public void setCarSpecId(java.lang.Integer carSpecId) {
		this.carSpecId = carSpecId;
	}

	public java.math.BigDecimal getStartPrice() {
		return startPrice;
	}

	public void setStartPrice(java.math.BigDecimal startPrice) {
		this.startPrice = startPrice;
	}

	public java.lang.Integer getStatus() {
		return status;
	}

	public void setStatus(java.lang.Integer status) {
		this.status = status;
	}

	public java.lang.String getPubRemark() {
		return pubRemark;
	}

	public void setPubRemark(java.lang.String pubRemark) {
		this.pubRemark = pubRemark;
	}

	public java.lang.Integer getUserRoleId() {
		return userRoleId;
	}

	public void setUserRoleId(java.lang.Integer userRoleId) {
		this.userRoleId = userRoleId;
	}

	public java.util.Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(java.util.Date createTime) {
		this.createTime = createTime;
	}

	public java.math.BigDecimal getOutNumprice() {
		return outNumprice;
	}

	public void setOutNumprice(java.math.BigDecimal outNumprice) {
		this.outNumprice = outNumprice;
	}

	public java.math.BigDecimal getStartKilometre() {
		return startKilometre;
	}

	public void setStartKilometre(java.math.BigDecimal startKilometre) {
		this.startKilometre = startKilometre;
	}

	public java.math.BigDecimal getStartNumber() {
		return startNumber;
	}

	public void setStartNumber(java.math.BigDecimal startNumber) {
		this.startNumber = startNumber;
	}

	public java.lang.String getUserPhone() {
		return userPhone;
	}

	public void setUserPhone(java.lang.String userPhone) {
		this.userPhone = userPhone;
	}

	public java.math.BigDecimal getOut5_20_kilometre() {
		return out5_20_kilometre;
	}

	public java.math.BigDecimal getOut20_50_kilometre() {
		return out20_50_kilometre;
	}

	public void setOut20_50_kilometre(java.math.BigDecimal out20_50_kilometre) {
		this.out20_50_kilometre = out20_50_kilometre;
	}

	public java.math.BigDecimal getOut50_kilometre() {
		return out50_kilometre;
	}

	public void setOut50_kilometre(java.math.BigDecimal out50_kilometre) {
		this.out50_kilometre = out50_kilometre;
	}

	public Integer getFoperator() {
		return foperator;
	}

	public void setFoperator(Integer foperator) {
		this.foperator = foperator;
	}

	public Date getFoperate_time() {
		return foperate_time;
	}

	public void setFoperate_time(Date foperate_time) {
		this.foperate_time = foperate_time;
	}

}
