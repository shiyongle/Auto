package com.pc.model;

import java.math.BigDecimal;
import java.util.Date;

/*
 * CPS-VMI-wangc
 */

public class CL_Rule {

	// alias
	public static final String TABLE_ALIAS = "规则表";
	public static final String ALIAS_ID = "自增主键";
	public static final String ALIAS_TYPE = "1为整车，2为零担";
	public static final String ALIAS_CAR_SPEC_ID = "车辆规格";
	public static final String ALIAS_START_KILOMETRE = "起步公里数";
	public static final String ALIAS_START_PRICE = "起步价";
	public static final String ALIAS_KILOMETRE_PRICE = "公里单价";
	public static final String ALIAS_STATUS = "1生效，2失效";
	public static final String ALIAS_PUB_REMARK = "备注";
	public static final String ALIAS_OTHER_TYPE = "其他类型 1为体积 2为重量";
	public static final String ALIAS_OUT_PRICE = "超出价格";

	private java.lang.Integer id;
	private java.lang.Integer type;
	private java.lang.Integer carSpecId;
	private java.math.BigDecimal startKilometre;
	private java.math.BigDecimal startPrice;
	private java.math.BigDecimal kilometrePrice;
	private java.math.BigDecimal kilometre5_20_price;// 超出5-20公里单价（包含20公里）
	private java.math.BigDecimal kilometre20_50_price;// 超出20-50公里单价（包含50公里）
	private java.math.BigDecimal kilometre50_price;// 超出50公里单价
	private java.lang.Integer status;
	private java.lang.String pubRemark;
	private java.lang.Integer otherType;
	private java.math.BigDecimal outPrice;
	private java.lang.Integer fopint;
	private java.math.BigDecimal outfopint;
	private BigDecimal fadd_service_1;// 装车服务费
	private BigDecimal fadd_service_2;// 卸货服务费
	private Integer fcreator;
	private Integer foperator;
	private Date foperate_time;

	public java.math.BigDecimal getKilometrePrice() {
		return kilometrePrice;
	}

	public void setKilometrePrice(java.math.BigDecimal kilometrePrice) {
		this.kilometrePrice = kilometrePrice;
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

	public java.math.BigDecimal getOutfopint() {
		return outfopint;
	}

	public void setOutfopint(java.math.BigDecimal outfopint) {
		this.outfopint = outfopint;
	}

	/********************* 非数据字段,前端封装显示数据*******begin ************************/
	private String carSpecName;// 车辆规格名称

	// columns END

	public CL_Rule() {
	}

	public String getCarSpecName() {
		return carSpecName;
	}

	public void setCarSpecName(String carSpecName) {
		this.carSpecName = carSpecName;
	}

	public CL_Rule(java.lang.Integer id) {
		this.id = id;
	}

	public void setId(java.lang.Integer value) {
		this.id = value;
	}

	public java.lang.Integer getId() {
		return this.id;
	}

	public void setType(java.lang.Integer value) {
		this.type = value;
	}

	public java.lang.Integer getType() {
		return this.type;
	}

	public void setCarSpecId(java.lang.Integer value) {
		this.carSpecId = value;
	}

	public java.lang.Integer getCarSpecId() {
		return this.carSpecId;
	}

	public void setStartKilometre(java.math.BigDecimal value) {
		this.startKilometre = value;
	}

	public java.math.BigDecimal getStartKilometre() {
		return this.startKilometre;
	}

	public void setStartPrice(java.math.BigDecimal value) {
		this.startPrice = value;
	}

	public java.math.BigDecimal getStartPrice() {
		return this.startPrice;
	}

	public void setStatus(java.lang.Integer value) {
		this.status = value;
	}

	public java.lang.Integer getStatus() {
		return this.status;
	}

	public void setPubRemark(java.lang.String value) {
		this.pubRemark = value;
	}

	public java.lang.String getPubRemark() {
		return this.pubRemark;
	}

	public java.lang.Integer getOtherType() {
		return otherType;
	}

	public void setOtherType(java.lang.Integer otherType) {
		this.otherType = otherType;
	}

	public java.math.BigDecimal getOutPrice() {
		return outPrice;
	}

	public void setOutPrice(java.math.BigDecimal outPrice) {
		this.outPrice = outPrice;
	}

	public java.math.BigDecimal getKilometre5_20_price() {
		return kilometre5_20_price;
	}

	public void setKilometre5_20_price(java.math.BigDecimal kilometre5_20_price) {
		this.kilometre5_20_price = kilometre5_20_price;
	}

	public java.math.BigDecimal getKilometre20_50_price() {
		return kilometre20_50_price;
	}

	public void setKilometre20_50_price(
			java.math.BigDecimal kilometre20_50_price) {
		this.kilometre20_50_price = kilometre20_50_price;
	}

	public java.math.BigDecimal getKilometre50_price() {
		return kilometre50_price;
	}

	public void setKilometre50_price(java.math.BigDecimal kilometre50_price) {
		this.kilometre50_price = kilometre50_price;
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
