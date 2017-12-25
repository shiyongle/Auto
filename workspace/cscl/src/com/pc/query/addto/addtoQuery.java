package com.pc.query.addto;

import java.io.Serializable;

import com.pc.query.aBase.BaseQuery;

public class addtoQuery extends BaseQuery implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7081656517381913156L;
	private java.lang.Integer fid;//主键ID
	private java.lang.Integer forderId;//订单Id
	private java.util.Date fcreatTime;//创建时间
	private java.math.BigDecimal fcost;//费用
	private java.lang.String fremark; //备注
	private java.lang.Integer fpayMethod;//支付方式
	private java.lang.Integer fabnormityId;//异常问题Id
	private java.lang.Integer fcreateor;//创建人ID
	public java.lang.Integer getFid() {
		return fid;
	}
	public void setFid(java.lang.Integer fid) {
		this.fid = fid;
	}
	public java.lang.Integer getForderId() {
		return forderId;
	}
	public void setForderId(java.lang.Integer forderId) {
		this.forderId = forderId;
	}
	public java.util.Date getFcreatTime() {
		return fcreatTime;
	}
	public void setFcreatTime(java.util.Date fcreatTime) {
		this.fcreatTime = fcreatTime;
	}
	public java.math.BigDecimal getFcost() {
		return fcost;
	}
	public void setFcost(java.math.BigDecimal fcost) {
		this.fcost = fcost;
	}
	public java.lang.String getFremark() {
		return fremark;
	}
	public void setFremark(java.lang.String fremark) {
		this.fremark = fremark;
	}
	public java.lang.Integer getFpayMethod() {
		return fpayMethod;
	}
	public void setFpayMethod(java.lang.Integer fpayMethod) {
		this.fpayMethod = fpayMethod;
	}
	public java.lang.Integer getFabnormityId() {
		return fabnormityId;
	}
	public void setFabnormityId(java.lang.Integer fabnormityId) {
		this.fabnormityId = fabnormityId;
	}
	public java.lang.Integer getFcreateor() {
		return fcreateor;
	}
	public void setFcreateor(java.lang.Integer fcreateor) {
		this.fcreateor = fcreateor;
	}
		
}
