package com.pc.model;

import java.math.BigDecimal;
import java.util.Date;

import cn.org.rapid_framework.util.DateConvertUtils;

public class CL_Distance {
	protected static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
	private Integer fid;//主键ID
	private Integer fcustomer_id;//客户id,对应表cl_address 的user_role_id,对应表cl_user_role的id
	private String faddressDel;//发货地址
	private String faddressRec;//卸货地址
	private Integer faddressDel_id;//地址ID,对应表cl_address type=1
	private Integer faddressRec_id;//地址ID,对应表cl_address type=2
	private BigDecimal fmileage;//公里数
	private Date fcreate_time;//创建时间
	private Integer fcreater_id;//创建人id，对应表cl_user_role
	private Date fedit_time;//修改时间
	private Integer feditor_id;//修改人id，对应表cl_user_role
	private String fcustomer;//客户名称
	
	/** 用于前端数据显示字段*/
	private String fcreater;//创建人
	private String feditor;//修改人
	
	public Integer getFid() {
		return fid;
	}
	public void setFid(Integer fid) {
		this.fid = fid;
	}
	public String getFcustomer() {
		return fcustomer;
	}
	public void setFcustomer(String fcustomer) {
		this.fcustomer = fcustomer;
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
	public String getFcreater() {
		return fcreater;
	}
	public void setFcreater(String fcreater) {
		this.fcreater = fcreater;
	}
	public Date getFedit_time() {
		return fedit_time;
	}
	public void setFedit_time(Date fedit_time) {
		this.fedit_time = fedit_time;
	}
	public String getFeditor() {
		return feditor;
	}
	public void setFeditor(String feditor) {
		this.feditor = feditor;
	}
	public Integer getFcustomer_id() {
		return fcustomer_id;
	}
	public void setFcustomer_id(Integer fcustomer_id) {
		this.fcustomer_id = fcustomer_id;
	}
	public Integer getFaddressDel_id() {
		return faddressDel_id;
	}
	public void setFaddressDel_id(Integer faddressDel_id) {
		this.faddressDel_id = faddressDel_id;
	}
	public Integer getFaddressRec_id() {
		return faddressRec_id;
	}
	public void setFaddressRec_id(Integer faddressRec_id) {
		this.faddressRec_id = faddressRec_id;
	}
	public Integer getFcreater_id() {
		return fcreater_id;
	}
	public void setFcreater_id(Integer fcreater_id) {
		this.fcreater_id = fcreater_id;
	}
	public Integer getFeditor_id() {
		return feditor_id;
	}
	public void setFeditor_id(Integer feditor_id) {
		this.feditor_id = feditor_id;
	}
	public String getFcreate_timeString(){
		return DateConvertUtils.format(getFcreate_time(), DATE_TIME_FORMAT);
	}
	public void setFcreate_timeString(String value){
		setFcreate_time(DateConvertUtils.parse(value, DATE_TIME_FORMAT,java.util.Date.class));
	}
	public String getFedit_timeString(){
		return DateConvertUtils.format(getFedit_time(), DATE_TIME_FORMAT);
	}
	public void setFedit_timeString(String value){
		setFedit_time(DateConvertUtils.parse(value, DATE_TIME_FORMAT,java.util.Date.class));
	}
	
}
