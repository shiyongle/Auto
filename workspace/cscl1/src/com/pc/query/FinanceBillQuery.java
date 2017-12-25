package com.pc.query;

import java.io.Serializable;

import com.pc.query.aBase.BaseQuery;

public class FinanceBillQuery extends BaseQuery implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5309791407611296411L;
	private Integer fcustId;             //客户ＩＤ
	private String fstartMonth;         //账单开始月份
	private String fendMonth;           //账单结束月份
	private Integer fverification;           //账单结束月份
	
	public Integer getFcustId() {
		return fcustId;
	}
	public void setFcustId(Integer fcustId) {
		this.fcustId = fcustId;
	}
	public String getFstartMonth() {
		return fstartMonth;
	}
	public void setFstartMonth(String fstartMonth) {
		this.fstartMonth = fstartMonth;
	}
	public String getFendMonth() {
		return fendMonth;
	}
	public void setFendMonth(String fendMonth) {
		this.fendMonth = fendMonth;
	}
	public Integer getFverification() {
		return fverification;
	}
	public void setFverification(Integer fverification) {
		this.fverification = fverification;
	}
}
