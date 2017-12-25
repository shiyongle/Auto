package com.pc.model;

import java.util.Date;

import cn.org.rapid_framework.util.DateConvertUtils;

/**
 *
 * @author zzm
 *
 */
public class Cl_Upload {
	public static final String FORMAT_CREATE_TIME = "yyyy-MM-dd HH:mm:ss";
     
    private Integer id;//自增主键
    private String name;//上传文件名称
    private String url;//路径
    private Integer parentId;//对应模块id
    private Date createTime;//创建时间
    private Integer createId;//创建人
    private java.lang.String modelName;//模块名称
    private Integer type;// 1是货主2是车主
    private String remark;//备注（含跳转的路径url）
    private String smallurl;//小图url
    
    //==========非数据库字段用于显示前端=================//
	public String getRemark() {
		return remark;
	}


	public void setRemark(String remark) {
		this.remark = remark;
	}


	public Integer getId() {
		return id;
	}


	public Integer getType() {
		return type;
	}


	public void setType(Integer type) {
		this.type = type;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getUrl() {
		return url;
	}


	public void setUrl(String url) {
		this.url = url;
	}


	public Integer getParentId() {
		return parentId;
	}


	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}


	public Date getCreateTime() {
		return createTime;
	}


	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}


	public Integer getCreateId() {
		return createId;
	}


	public void setCreateId(Integer createId) {
		this.createId = createId;
	}
	

	public java.lang.String getModelName() {
		return modelName;
	}


	public void setModelName(java.lang.String modelName) {
		this.modelName = modelName;
	}
	public String getCreateTimeString() {
		return DateConvertUtils.format(getCreateTime(), FORMAT_CREATE_TIME);
	}
	public void setCreateTimeString(String value) {
		setCreateTime(DateConvertUtils.parse(value, FORMAT_CREATE_TIME,java.util.Date.class));
	}

	@Override
	public String toString() {
		return "Cl_Upload [id=" + id + ", name=" + name + ", url=" + url
				+ ", parentId=" + parentId + ", createTime=" + createTime
				+ ", createId=" + createId + "]";
	}


	public String getSmallurl() {
		return smallurl;
	}


	public void setSmallurl(String smallurl) {
		this.smallurl = smallurl;
	}


}