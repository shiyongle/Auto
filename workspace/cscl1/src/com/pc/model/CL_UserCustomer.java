package com.pc.model;

import java.util.Date;

import cn.org.rapid_framework.util.DateConvertUtils;

/**
 * 
 * @author les
 * 
 *
 */
public class CL_UserCustomer implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5083693665289726203L;
	protected static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
	private int fid;// 主键
	private String fnumber;//客户编号
	private String fname;//客户名称
	private String ftype;//客户类型
	private String fregion;//客户所在区域
	private int fuserRoleId;//用户id 	
	private String  fsalesManId;//业务员ID
	private String  fsalesMan;//业务员
	private String fsalesManDept;//业务员所在部门
	private String fsettleCycle;//结算周期
	private String fattention;//客户相关注意事项
	private Date fcreateTime;//创建时间 
	private int fcreator;//创建者
	private Date faudittime;//认证时间
	private String fsimplename;//客户简称


	/*********************非数据字段,前端封装显示数据*******begin************************/
	private java.lang.String userName;//用户名称
	private java.lang.String userPhone;//用户手机
	
	
	public java.lang.String getUserPhone() {
		return userPhone;
	}
	public void setUserPhone(java.lang.String userPhone) {
		this.userPhone = userPhone;
	}
	public int getFid() {
		return fid;
	}
	public void setFid(int fid) {
		this.fid = fid;
	}
	public String getFnumber() {
		return fnumber;
	}
	public void setFnumber(String fnumber) {
		this.fnumber = fnumber;
	}
	public String getFname() {
		return fname;
	}
	public void setFname(String fname) {
		this.fname = fname;
	}
	public String getFtype() {
		return ftype;
	}
	public void setFtype(String ftype) {
		this.ftype = ftype;
	}
	public String getFregion() {
		return fregion;
	}
	public void setFregion(String fregion) {
		this.fregion = fregion;
	}
	public int getFuserRoleId() {
		return fuserRoleId;
	}
	public void setFuserRoleId(int fuserRoleId) {
		this.fuserRoleId = fuserRoleId;
	}
	public String getFsalesManDept() {
		return fsalesManDept;
	}
	public void setFsalesManDept(String fsalesManDept) {
		this.fsalesManDept = fsalesManDept;
	}
	public String getFsettleCycle() {
		return fsettleCycle;
	}
	public void setFsettleCycle(String fsettleCycle) {
		this.fsettleCycle = fsettleCycle;
	}
	public String getFattention() {
		return fattention;
	}
	public void setFattention(String fattention) {
		this.fattention = fattention;
	}
	public Date getFcreateTime() {
		return fcreateTime;
	}
	public void setFcreateTime(Date fcreateTime) {
		this.fcreateTime = fcreateTime;
	}
	public java.lang.String getUserName() {
		return userName;
	}
	public void setUserName(java.lang.String userName) {
		this.userName = userName;
	}
	public int getFcreator() {
		return fcreator;
	}
	public void setFcreator(int fcreator) {
		this.fcreator = fcreator;
	}
	public String getFsalesManId() {
		return fsalesManId;
	}
	public void setFsalesManId(String fsalesManId) {
		this.fsalesManId = fsalesManId;
	}
	public String getFsalesMan() {
		return fsalesMan;
	}
	public void setFsalesMan(String fsalesMan) {
		this.fsalesMan = fsalesMan;
	}
	public Date getFaudittime() {
		return faudittime;
	}
	public void setFaudittime(Date faudittime) {
		this.faudittime = faudittime;
	}
	
	public String getFsimplename() {
		return fsimplename;
	}
	public void setFsimplename(String fsimplename) {
		this.fsimplename = fsimplename;
	}
	public String getCreateTimeString() {
		return DateConvertUtils.format(getFcreateTime(), DATE_TIME_FORMAT);
	}
	public void setCreateTimeString(String value) {
		setFcreateTime(DateConvertUtils.parse(value, DATE_TIME_FORMAT,java.util.Date.class));
	}
	public String getAuditTimeString() {
		return DateConvertUtils.format(getFaudittime(), DATE_TIME_FORMAT);
	}
	public void setAuditTimeString(String value) {
		setFaudittime(DateConvertUtils.parse(value, DATE_TIME_FORMAT,java.util.Date.class));
	}
}