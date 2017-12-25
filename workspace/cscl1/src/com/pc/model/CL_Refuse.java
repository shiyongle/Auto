package com.pc.model;

import java.util.Date;

import cn.org.rapid_framework.util.DateConvertUtils;

/**
 *@author lancher
 *@date 2017年1月14日 下午2:56:46
 */
public class CL_Refuse {
	
	public static final String FORMAT_TIME="yyyy-MM-dd HH:mm:ss";
	
	private Integer fid;
	private Date fstart_time;//停止接单开始时间
	private Date fend_time;//结束时间
	private Integer fcreator;
	private Date fcreate_time;
	private Date fupdate_time;
	private Integer ftype;//类型,0客户，待扩充
	private String fkey;
	private String fvalues;//允许用户
	/***非数据库字段****/
	private String fcreatorName;//创建人名称
	private String fvaluesName;//允许用户名称
	
	public Integer getFtype() {
		return ftype;
	}
	public void setFtype(Integer ftype) {
		this.ftype = ftype;
	}
	public Integer getFid() {
		return fid;
	}
	public void setFid(Integer fid) {
		this.fid = fid;
	}
	public Date getFstart_time() {
		return fstart_time;
	}
	public String getFstart_timeString() {
		return DateConvertUtils.format(getFstart_time(), FORMAT_TIME);
	}
	public void setFstart_time(Date fstart_time) {
		this.fstart_time = fstart_time;
	}
	public Date getFend_time() {
		return fend_time;
	}
	public String getFend_timeString() {
		return DateConvertUtils.format(getFend_time(), FORMAT_TIME);
	}
	public void setFend_time(Date fend_time) {
		this.fend_time = fend_time;
	}
	public Integer getFcreator() {
		return fcreator;
	}
	public void setFcreator(Integer fcreator) {
		this.fcreator = fcreator;
	}
	public Date getFcreate_time() {
		return fcreate_time;
	}
	public String getFcreate_timeString() {
		return DateConvertUtils.format(getFcreate_time(), FORMAT_TIME);
	}
	public void setFcreate_time(Date fcreate_time) {
		this.fcreate_time = fcreate_time;
	}
	public Date getFupdate_time() {
		return fupdate_time;
	}
	public String getFupdate_timeString() {
		return DateConvertUtils.format(getFupdate_time(), FORMAT_TIME);
	}
	public void setFupdate_time(Date fupdate_time) {
		this.fupdate_time = fupdate_time;
	}
	public String getFvalues() {
		return fvalues;
	}
	public void setFvalues(String fvalues) {
		this.fvalues = fvalues;
	}
	public String getFkey() {
		return fkey;
	}
	public void setFkey(String fkey) {
		this.fkey = fkey;
	}
	public String getFcreatorName() {
		return fcreatorName;
	}
	public void setFcreatorName(String fcreatorName) {
		this.fcreatorName = fcreatorName;
	}
	public String getFvaluesName() {
		return fvaluesName;
	}
	public void setFvaluesName(String fvaluesName) {
		this.fvaluesName = fvaluesName;
	}
	
}