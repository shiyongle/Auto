package com.pc.model;



import java.math.BigDecimal;
import java.util.Date;

import org.apache.commons.lang.builder.HashCodeBuilder;

import cn.org.rapid_framework.util.DateConvertUtils;

public class CL_CouponsDetail implements java.io.Serializable {
	private static final long serialVersionUID = 5454155825314635342L;
	
	//alias
	public static final String TABLE_ALIAS = "好运券使用情况表";
	public static final String ALIAS_ID = "自增主键";
	public static final String ALIAS_COUPONS_ID = "好运券主键";
	public static final String ALIAS_USER_ROLE_ID = "用户角色表主键";
	public static final String ALIAS_IS_USE = "是否使用：0未使用，1已使用";
	public static final String ALIAS_CREATOR = "领取人";
	public static final String ALIAS_CREATE_TIME = "领取时间";
	public static final String ALIAS_ISOVERDUE = "是否过期：0否，1是";
	public static final String ALIAS_FSTART_TIME = "明细开始时间";
	public static final String ALIAS_FEND_TIME = "明细结束时间";
	public static final String ALIAS_FORDER_ID = "订单ID";
	
	//date formats
	public static final String FORMAT_START_TIME = "yyyy-MM-dd HH:mm";
	public static final String FORMAT_END_TIME = "yyyy-MM-dd HH:mm";
	public static final String FORMAT_CREATE_TIME = "yyyy-MM-dd HH:mm:ss";
//	private java.lang.Integer userRoleId;
	
	private java.lang.Integer id;
	private java.lang.Integer couponsId;
	private java.lang.Integer couponsActivityId;
	private java.lang.Integer isUse;//是否使用：0未使用，1已使用
	private java.lang.Integer creator;//领取人
	private java.util.Date createTime;
	private java.lang.Integer isOverdue;//是否过期：0否，1是
	private java.math.BigDecimal dollars;//满足金额条件
	private String fcarSpecId;//满足车型条件
	private String farea;//区域（提货）
	private Integer ftype;//优惠券类型 1：订单优惠券  2：增值服务券
	private BigDecimal fdiscount;//订单优惠券的折扣
	private BigDecimal fsubtract;//订单优惠券的直减
	private String faddserviceName;//增值服务券对应的增值服务项目
	private java.util.Date startTime;//优惠券开始时间
	private java.util.Date endTime;//结束时间
	
	private BigDecimal sub;//折扣和直减最终减的钱
	private Integer status;//状态1，不符合条件的灰显，
	
	
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public BigDecimal getSub() {
		return sub;
	}
	public void setSub(BigDecimal sub) {
		this.sub = sub;
	}
	public String getFcarSpecId() {
		return fcarSpecId;
	}
	public void setFcarSpecId(String fcarSpecId) {
		this.fcarSpecId = fcarSpecId;
	}
	public String getFarea() {
		return farea;
	}
	public void setFarea(String farea) {
		this.farea = farea;
	}
	public java.lang.Integer getCouponsActivityId() {
		return couponsActivityId;
	}
	public void setCouponsActivityId(java.lang.Integer couponsActivityId) {
		this.couponsActivityId = couponsActivityId;
	}
	public Integer getFtype() {
		return ftype;
	}
	public void setFtype(Integer ftype) {
		this.ftype = ftype;
	}
	public BigDecimal getFdiscount() {
		return fdiscount;
	}
	public void setFdiscount(BigDecimal fdiscount) {
		this.fdiscount = fdiscount;
	}
	public BigDecimal getFsubtract() {
		return fsubtract;
	}
	public void setFsubtract(BigDecimal fsubtract) {
		this.fsubtract = fsubtract;
	}
	public String getFaddserviceName() {
		return faddserviceName;
	}
	public void setFaddserviceName(String faddserviceName) {
		this.faddserviceName = faddserviceName;
	}
	public String getStartTimeString() {
		return DateConvertUtils.format(getStartTime(), FORMAT_START_TIME);
	}
	public void setStartTimeString(String value) {
		setStartTime(DateConvertUtils.parse(value, FORMAT_START_TIME,java.util.Date.class));
	}
	
	public void setStartTime(java.util.Date value) {
		this.startTime = value;
	}
	
	public java.util.Date getStartTime() {
		return this.startTime;
	}
	public String getEndTimeString() {
		return DateConvertUtils.format(getEndTime(), FORMAT_END_TIME);
	}
	public void setEndTimeString(String value) {
		setEndTime(DateConvertUtils.parse(value, FORMAT_END_TIME,java.util.Date.class));
	}
	
	public void setEndTime(java.util.Date value) {
		this.endTime = value;
	}
	
	public java.util.Date getEndTime() {
		return this.endTime;
	}
	
	public java.math.BigDecimal getDollars() {
		return dollars;
	}

	public void setDollars(java.math.BigDecimal dollars) {
		this.dollars = dollars;
	}

	
	

	public CL_CouponsDetail(Integer couponsId, Integer userRoleId,
			Integer isUse, Integer creator, Date createTime, Integer isOverdue,
			Date startTime, Date endTime) {
		super();
		this.couponsId = couponsId;
//		this.userRoleId = userRoleId;
		this.isUse = isUse;
		this.creator = creator;
		this.createTime = createTime;
		this.isOverdue = isOverdue;
		this.startTime = startTime;
		this.endTime = endTime;
	}
	public CL_CouponsDetail(){
	}

	public CL_CouponsDetail(java.lang.Integer id){
		this.id = id;
	}

	public void setId(java.lang.Integer value) {
		this.id = value;
	}
	
	public java.lang.Integer getId() {
		return this.id;
	}
	public void setCouponsId(java.lang.Integer value) {
		this.couponsId = value;
	}
	
	public java.lang.Integer getCouponsId() {
		return this.couponsId;
	}
	/*public void setUserRoleId(java.lang.Integer value) {
		this.userRoleId = value;
	}
	
	public java.lang.Integer getUserRoleId() {
		return this.userRoleId;
	}*/
	public void setIsUse(java.lang.Integer value) {
		this.isUse = value;
	}
	
	public java.lang.Integer getIsUse() {
		return this.isUse;
	}
	public void setCreator(java.lang.Integer value) {
		this.creator = value;
	}
	
	public java.lang.Integer getCreator() {
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
	
	public java.lang.Integer getIsOverdue() {
		return isOverdue;
	}
	public void setIsOverdue(java.lang.Integer isOverdue) {
		this.isOverdue = isOverdue;
	}
	
	public String toString(String separator) {
		StringBuffer sb = new StringBuffer();
			sb.append(ALIAS_ID+":").append(id).append(separator);
			sb.append(ALIAS_COUPONS_ID+":").append(couponsId).append(separator);
//			sb.append(ALIAS_USER_ROLE_ID+":").append(userRoleId).append(separator);
			sb.append(ALIAS_IS_USE+":").append(isUse).append(separator);
			sb.append(ALIAS_CREATOR+":").append(creator).append(separator);
			sb.append(ALIAS_CREATE_TIME+":").append(getCreateTimeString()).append(separator);
			sb.append(ALIAS_FSTART_TIME+":").append(getStartTimeString()).append(separator);
			sb.append(ALIAS_FEND_TIME+":").append(getEndTimeString()).append(separator);
		return sb.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
}
