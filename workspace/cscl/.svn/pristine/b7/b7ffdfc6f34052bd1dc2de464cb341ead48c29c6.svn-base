package com.pc.model;




import cn.org.rapid_framework.util.DateConvertUtils;

public class CL_Feedback  {
	
	//alias
	public static final String TABLE_ALIAS = "CL_Feedback";
	public static final String ALIAS_ID = "id";
	public static final String ALIAS_CONTENT = "反馈内容";
	public static final String ALIAS_FPHONE = "联系电话";
	public static final String ALIAS_CREATOR_ID = "创建人ID";
	public static final String ALIAS_CREATE_TIME = "创建时间";
	
	//date formats
	public static final String FORMAT_CREATE_TIME = "yyyy-MM-dd HH:mm";
	
	//columns START
	private java.lang.Integer id;
	private java.lang.String content;
	private java.lang.String fphone;
	private java.lang.Integer creatorId;
	private java.util.Date createTime;
	/*********************非数据字段,前端封装显示数据*******begin************************/
	private java.lang.String fid;//vmi数据的主键t_sys_user
	
	private java.lang.String creatorName;
	//columns END

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

	public CL_Feedback(){
	}

	public CL_Feedback(java.lang.Integer id){
		this.id = id;
	}

	public void setId(java.lang.Integer value) {
		this.id = value;
	}
	
	public java.lang.Integer getId() {
		return this.id;
	}

	public void setContent(java.lang.String value) {
		this.content = value;
	}
	
	public java.lang.String getContent() {
		return this.content;
	}
	
	public java.lang.String getFphone() {
		return fphone;
	}

	public void setFphone(java.lang.String fphone) {
		this.fphone = fphone;
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

	/**
	 * @return the creatorId
	 */
	public java.lang.Integer getCreatorId() {
		return creatorId;
	}

	/**
	 * @param creatorId the creatorId to set
	 */
	public void setCreatorId(java.lang.Integer creatorId) {
		this.creatorId = creatorId;
	}
	
}

