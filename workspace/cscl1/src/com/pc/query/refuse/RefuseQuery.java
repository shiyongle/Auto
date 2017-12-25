package com.pc.query.refuse;

import java.io.Serializable;
import java.util.Date;

import com.pc.query.aBase.BaseQuery;

public class RefuseQuery extends BaseQuery implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7406116043815727373L;
	
	private Integer fid;
	private Date fstart_time;//停止接单开始时间
	private Date fend_time;//结束时间
	private Integer fcreator;
	private Date fcreate_time;
	private Date fupdate_time;
	private Integer ftype;//类型,0客户，待扩充
	private String fkey;
	private String fvalues;
	
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
	public void setFstart_time(Date fstart_time) {
		this.fstart_time = fstart_time;
	}
	public Date getFend_time() {
		return fend_time;
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
	public void setFcreate_time(Date fcreate_time) {
		this.fcreate_time = fcreate_time;
	}
	public Date getFupdate_time() {
		return fupdate_time;
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
	
}
