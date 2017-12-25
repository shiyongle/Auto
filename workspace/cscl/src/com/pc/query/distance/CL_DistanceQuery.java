package com.pc.query.distance;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.pc.query.aBase.BaseQuery;

public class CL_DistanceQuery extends BaseQuery implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2219121072711920502L;
	private Integer fid;//主键ID
	private Integer fcustomer_id;//客户id
	private String faddressDel;//发货地址
	private String faddressRec;//卸货地址
	private BigDecimal fmileage;//公里数
	private Date fcreate_time;//创建时间
	private Integer fcreater_id;//创建人id
	private Date fedit_time;//修改时间
	private Integer feditor_id;//修改人id
	private String fcustomer;//客户名称
	private String fcreater;//创建人
	private String feditor;//修改人
	public Integer getFid() {
		return fid;
	}
	public void setFid(Integer fid) {
		this.fid = fid;
	}
	public Integer getFcustomer_id() {
		return fcustomer_id;
	}
	public void setFcustomer_id(Integer fcustomer_id) {
		this.fcustomer_id = fcustomer_id;
	}
	public String getFaddressDel() {
		return faddressDel;
	}
	public void setFaddressDel(String faddressDel) {
		this.faddressDel = faddressDel;
	}
	public String getFaddressRec() {
		return faddressRec;
	}
	public void setFaddressRec(String faddressRec) {
		this.faddressRec = faddressRec;
	}
	public BigDecimal getFmileage() {
		return fmileage;
	}
	public void setFmileage(BigDecimal fmileage) {
		this.fmileage = fmileage;
	}
	public Date getFcreate_time() {
		return fcreate_time;
	}
	public void setFcreate_time(Date fcreate_time) {
		this.fcreate_time = fcreate_time;
	}
	public Integer getFcreater_id() {
		return fcreater_id;
	}
	public void setFcreater_id(Integer fcreater_id) {
		this.fcreater_id = fcreater_id;
	}
	public Date getFedit_time() {
		return fedit_time;
	}
	public void setFedit_time(Date fedit_time) {
		this.fedit_time = fedit_time;
	}
	public Integer getFeditor_id() {
		return feditor_id;
	}
	public void setFeditor_id(Integer feditor_id) {
		this.feditor_id = feditor_id;
	}
	public String getFcustomer() {
		return fcustomer;
	}
	public void setFcustomer(String fcustomer) {
		this.fcustomer = fcustomer;
	}
	public String getFcreater() {
		return fcreater;
	}
	public void setFcreater(String fcreater) {
		this.fcreater = fcreater;
	}
	public String getFeditor() {
		return feditor;
	}
	public void setFeditor(String feditor) {
		this.feditor = feditor;
	}
	
}
