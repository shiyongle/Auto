package com.model.invite;

import java.io.Serializable;
import java.util.Date;

import cn.org.rapid_framework.util.DateConvertUtils;

/**
 * 转介绍表
 */
public class Invite implements Serializable{
	private static final long serialVersionUID = 1L;
	public static final String FORMAT_CREATE_TIME = "yyyy-MM-dd HH:mm:ss";
	/**** 主键 */
	private String fid;
	/**** 邀请人ID */
	private String fuserid;
	/**邀请人角色ID*/
	private int fuserroleid;
	/**** 被邀请人ID*/
	private String finviteeid;
	/**** 被邀请人注册时间*/
	private Date fcreatetime;
	/**** 推广奖金*/
	private String fbonus = "";
	/**** 推广APP名称	0包装交易平台APP，1一路好运APP*/
	private int fapptype=1;
	private String fusername;
	private String finviteename;
	
	public String getFusername() {
		return fusername;
	}
	public void setFusername(String fusername) {
		this.fusername = fusername;
	}
	public String getFinviteename() {
		return finviteename;
	}
	public void setFinviteename(String finviteename) {
		this.finviteename = finviteename;
	}
	public String getFid() {
		return fid;
	}
	public void setFid(String fid) {
		this.fid = fid;
	}
	public String getFuserid() {
		return fuserid;
	}
	public void setFuserid(String fuserid) {
		this.fuserid = fuserid;
	}
	public int getFuserroleid() {
		return fuserroleid;
	}
	public void setFuserroleid(int fuserroleid) {
		this.fuserroleid = fuserroleid;
	}
	public String getFinviteeid() {
		return finviteeid;
	}
	public void setFinviteeid(String finviteeid) {
		this.finviteeid = finviteeid;
	}
	public Date getFcreatetime() {
		return fcreatetime;
	}
	public void setFcreatetime(Date fcreatetime) {
		this.fcreatetime = fcreatetime;
	}
	
	public String getFcreatetimeString() {
		return DateConvertUtils.format(getFcreatetime(), FORMAT_CREATE_TIME);
	}
	public void setFcreatetimeString(String value) {
		setFcreatetime(DateConvertUtils.parse(value, FORMAT_CREATE_TIME,java.util.Date.class));
	}
	public String getFbonus() {
		return fbonus;
	}
	public String getFbonusString() {
		return fbonus.equals("0")?"否":"是";
	}
	public void setFbonusString(String fbonus) {
		setFbonus(fbonus.equals("0")?"否":"是");
	}
	public void setFbonus(String fbonus) {
		this.fbonus = fbonus;
	}
	public int getFapptype() {
		return fapptype;
	}
	public void setFapptype(int fapptype) {
		this.fapptype = fapptype;
	}
	
}