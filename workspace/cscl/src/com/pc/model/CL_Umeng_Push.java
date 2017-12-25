package com.pc.model;

import java.util.Date;

public class CL_Umeng_Push {

	private int fid;
	private int fuserid;//用户表中对应的ID
	private String fuserPhone;//用户表中对应的手机号
	private int fdeviceType;//设备类型(1-Android 2-iOS 3-iOS企业)
	private String fdevice;//设备唯一标签
	private Date fcreateTime; //创建时间戳
	private Date flastTime;//最后一次登陆时间
	private int fuserType;//用户类型(1-货主2-2-司机)
	public int getFid() {
		return fid;
	}
	public void setFid(int fid) {
		this.fid = fid;
	}
	public int getFuserid() {
		return fuserid;
	}
	public void setFuserid(int fuserid) {
		this.fuserid = fuserid;
	}
	public String getFuserPhone() {
		return fuserPhone;
	}
	public void setFuserPhone(String fuserPhone) {
		this.fuserPhone = fuserPhone;
	}
	public int getFdeviceType() {
		return fdeviceType;
	}
	public void setFdeviceType(int fdeviceType) {
		this.fdeviceType = fdeviceType;
	}
	public String getFdevice() {
		return fdevice;
	}
	public void setFdevice(String fdevice) {
		this.fdevice = fdevice;
	}
	public Date getFcreateTime() {
		return fcreateTime;
	}
	public void setFcreateTime(Date fcreateTime) {
		this.fcreateTime = fcreateTime;
	}
	public Date getFlastTime() {
		return flastTime;
	}
	public void setFlastTime(Date flastTime) {
		this.flastTime = flastTime;
	}
	public int getFuserType() {
		return fuserType;
	}
	public void setFuserType(int fuserType) {
		this.fuserType = fuserType;
	}
	
	
	
	
	
}
