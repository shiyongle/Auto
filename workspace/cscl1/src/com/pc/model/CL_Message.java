package com.pc.model;




import cn.org.rapid_framework.util.DateConvertUtils;

public class CL_Message  {
	
	//alias
	public static final String TABLE_ALIAS = "CL_Message";
	public static final String ALIAS_ID = "id";
	public static final String ALIAS_TITLE = "标题";
	public static final String ALIAS_CONTENT = "发送内容";
	public static final String ALIAS_TYPE = "类型:1货主推送2司机推送3货主4司机5全部";
	public static final String ALIAS_RECEIVER = "接收人";
	public static final String ALIAS_CREATOR = "创建人";
	public static final String ALIAS_CREATE_TIME = "创建时间";
	
	//date formats
	public static final String FORMAT_CREATE_TIME = "yyyy-MM-dd HH:mm";
	
	//columns START
	private java.lang.Integer id;
	private java.lang.String title;
	private java.lang.String content;
	private java.lang.Integer type;
	private java.lang.Integer receiver;
	private String receiverPhone;
	private java.lang.String creator;
	private java.util.Date createTime;
	/*********************非数据字段,前端封装显示数据*******begin************************/
	private java.lang.String fid;//vmi数据的主键t_sys_user
	
	private java.lang.String receiverName;
	private java.lang.String creatorName;
	private java.lang.Integer fisread;
	private Integer fbusinessType;
	//columns END

 

	public java.lang.String getReceiverName() {
		return receiverName;
	}


	public Integer getFbusinessType() {
		return fbusinessType;
	}


	public void setFbusinessType(Integer fbusinessType) {
		this.fbusinessType = fbusinessType;
	}


	public java.lang.Integer getFisread() {
		return fisread;
	}


	public void setFisread(java.lang.Integer fisread) {
		this.fisread = fisread;
	}


	public void setReceiverName(java.lang.String receiverName) {
		this.receiverName = receiverName;
	}

	public java.lang.String getCreatorName() {
		return creatorName;
	}

	public void setCreatorName(java.lang.String creatorName) {
		this.creatorName = creatorName;
	}

	public java.lang.String getFid() {
		return fid;
	}

	public void setFid(java.lang.String fid) {
		this.fid = fid;
	}

	public CL_Message(){
	}

	public CL_Message(java.lang.Integer id){
		this.id = id;
	}

	public void setId(java.lang.Integer value) {
		this.id = value;
	}
	
	public java.lang.Integer getId() {
		return this.id;
	}
	
	public java.lang.String getTitle() {
		return title;
	}

	public void setTitle(java.lang.String title) {
		this.title = title;
	}

	public void setContent(java.lang.String value) {
		this.content = value;
	}
	
	public java.lang.String getContent() {
		return this.content;
	}
	public void setType(java.lang.Integer value) {
		this.type = value;
	}
	
	public java.lang.Integer getType() {
		return this.type;
	}
	public void setReceiver(java.lang.Integer value) {
		this.receiver = value;
	}
	
	public java.lang.Integer getReceiver() {
		return this.receiver;
	}
	
	public String getReceiverPhone() {
		return receiverPhone;
	}


	public void setReceiverPhone(String receiverPhone) {
		this.receiverPhone = receiverPhone;
	}


	public void setCreator(java.lang.String value) {
		this.creator = value;
	}
	
	public java.lang.String getCreator() {
		return this.creator;
	}
	public String getCreateTimeString() {
		return DateConvertUtils.format(getCreateTime(), FORMAT_CREATE_TIME);
	}
	public void setCreateTimeString(String value) {
		setCreateTime(DateConvertUtils.parse(value, FORMAT_CREATE_TIME,java.util.Date.class));
	}
	
	public void setCreateTime(java.util.Date value) {
		this.createTime = value;
	}
	
	public java.util.Date getCreateTime() {
		return this.createTime;
	}

	
	
}

