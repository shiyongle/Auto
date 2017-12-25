package com.pc.model;

import cn.org.rapid_framework.util.DateConvertUtils;

/**
 * 
 * @author twr
 *
 */

public class CL_CouponRule {
     private int id;//主键id
     private java.math.BigDecimal dollars;//面额 
     private java.math.BigDecimal compareDollars;//比面额
     private java.math.BigDecimal consumption;//消费面额
     private Integer isEffective;//是否失效
     private  java.util.Date createTime;//创建时间
     private int creator;//创建人
     
    public static final String FORMAT_CREATE_TIME = "yyyy-MM-dd HH:mm:ss";
     
    //==========非数据库字段用于显示前端=================//
    private String vminame;//用户名
	public String getVminame() {
		return vminame;
	}
	public void setVminame(String vminame) {
		this.vminame = vminame;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public java.math.BigDecimal getDollars() {
		return dollars;
	}
	public void setDollars(java.math.BigDecimal dollars) {
		this.dollars = dollars;
	}
	public java.math.BigDecimal getCompareDollars() {
		return compareDollars;
	}
	public void setCompareDollars(java.math.BigDecimal compareDollars) {
		this.compareDollars = compareDollars;
	}
	public java.math.BigDecimal getConsumption() {
		return consumption;
	}
	public void setConsumption(java.math.BigDecimal consumption) {
		this.consumption = consumption;
	}
	public Integer getIsEffective() {
		return isEffective;
	}
	public void setIsEffective(int isEffective) {
		this.isEffective = isEffective;
	}
	public java.util.Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(java.util.Date value) {
		this.createTime = value;
	}
	public int getCreator() {
		return creator;
	}
	public void setCreator(int creator) {
		this.creator = creator;
	}
	public String getCreateTimeString() {
		return DateConvertUtils.format(getCreateTime(), FORMAT_CREATE_TIME);
	}
	public void setCreateTimeString(String value) {
		setCreateTime(DateConvertUtils.parse(value, FORMAT_CREATE_TIME,java.util.Date.class));
	}
}     