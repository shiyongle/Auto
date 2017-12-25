package com.pc.model;

import cn.org.rapid_framework.util.DateConvertUtils;

public class CL_MessageSend  {
	
	//alias
	public static final String TABLE_ALIAS = "CL_MessageSend";
	public static final String ALIAS_FID = "id";
	public static final String ALIAS_FMESSAGEID = "消息ID";
	public static final String ALIAS_FRECEIVERID = "收件人ID";
	public static final String ALIAS_FISREAD = "读取状态:0未读,1已读";
	public static final String ALIAS_FREADTIME = "读取时间";
	
	//date formats
	public static final String FORMAT_CREATE_TIME = "yyyy-MM-dd HH:mm";
	
	//columns START
	private java.lang.Integer fid;
	private java.lang.Integer fmessageid;
	private java.lang.Integer freceiverid;
	private java.lang.Integer fisread;
	private java.lang.Integer freadtime;
	/*********************非数据字段,前端封装显示数据*******begin************************/
	private java.lang.String title;//message表消息标题
	private java.lang.String content;//message表消息文本
	private java.lang.String type;//message表类型3货主-发送消息，4司机-发送消息
	private java.lang.String creator;//message表消息发布人
	private java.util.Date createtime;//message表消息发布时间
	//columns END

	public CL_MessageSend(){
	}

	public CL_MessageSend(java.lang.Integer fid){
		this.fid = fid;
	}

	public String getCreateTimeString() {
		return DateConvertUtils.format(getCreatetime(), FORMAT_CREATE_TIME);
	}
	public void setCreateTimeString(String value) {
		setCreatetime(DateConvertUtils.parse(value, FORMAT_CREATE_TIME,java.util.Date.class));
	}

	public java.lang.Integer getFid() {
		return fid;
	}

	public void setFid(java.lang.Integer fid) {
		this.fid = fid;
	}

	public java.lang.Integer getFmessageid() {
		return fmessageid;
	}

	public void setFmessageid(java.lang.Integer fmessageid) {
		this.fmessageid = fmessageid;
	}

	public java.lang.Integer getFreceiverid() {
		return freceiverid;
	}

	public void setFreceiverid(java.lang.Integer freceiverid) {
		this.freceiverid = freceiverid;
	}

	public java.lang.Integer getFisread() {
		return fisread;
	}

	public void setFisread(java.lang.Integer fisread) {
		this.fisread = fisread;
	}

	public java.lang.Integer getFreadtime() {
		return freadtime;
	}

	public void setFreadtime(java.lang.Integer freadtime) {
		this.freadtime = freadtime;
	}

	public java.lang.String getTitle() {
		return title;
	}

	public void setTitle(java.lang.String title) {
		this.title = title;
	}

	public java.lang.String getContent() {
		return content;
	}

	public void setContent(java.lang.String content) {
		this.content = content;
	}

	public java.lang.String getType() {
		return type;
	}

	public void setType(java.lang.String type) {
		this.type = type;
	}

	public java.lang.String getCreator() {
		return creator;
	}

	public void setCreator(java.lang.String creator) {
		this.creator = creator;
	}

	public java.util.Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(java.util.Date createtime) {
		this.createtime = createtime;
	}
	
	
	
}

