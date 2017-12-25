package com.pc.model;
import java.math.BigDecimal;

import cn.org.rapid_framework.util.DateConvertUtils;

public class CL_Coupons implements java.io.Serializable {
	private static final long serialVersionUID = 5454155825314635342L;
	
	//alias
	public static final String TABLE_ALIAS = "优惠券表";
	public static final String ALIAS_ID = "自增主键";
//	public static final String ALIAS_TYPE = "类型 4:首页优惠券活动，3指派优惠券,2付款自动产生优惠券,1业务员优惠券";
//	public static final String ALIAS_BATCH_NUMBER ="批次号,该字段按业务员进行区分";
	public static final String ALIAS_START_TIME = "开始时间";
	public static final String ALIAS_END_TIME = "结束时间";
	public static final String ALIAS_DOLLARS = "面额";
	public static final String ALIAS_COMPARE_DOLLARS = "比面额";
	public static final String ALIAS_REDEEM_CODE = "兑换码";
	public static final String ALIAS_IS_EFFECTIVE = "是否有效:1有效，0失效";
	public static final String ALIAS_ISOVERDUE = "是否过期：0否，1是";
	public static final String ALIAS_SALES_MAN = "业务员";
	public static final String ALIAS_DESCRIBES ="描述";
	public static final String ALIAS_CREATOR = "创建人";
	public static final String ALIAS_CREATE_TIME = "创建时间";
	
	//date formats
	public static final String FORMAT_START_TIME = "yyyy-MM-dd";
	public static final String FORMAT_END_TIME = "yyyy-MM-dd";
	public static final String FORMAT_CREATE_TIME = "yyyy-MM-dd HH:mm:ss";
	
	//columns START
//	private java.lang.String batchNumber;
//	private java.util.Date startTime;
//	private java.util.Date endTime;
//	private java.math.BigDecimal dollars;
//	private java.math.BigDecimal compareDollars;
//	private java.lang.String redeemCode;
//	private java.lang.Integer isEffective;
//	private java.lang.Integer isOverdue;
//	private java.lang.String salesMan;
//	private java.lang.String describes;
	private java.lang.Integer id;
	private String fbusinessType;//业务类型（暂时默认一路好运）
	private String fcouponsName;//优惠券名称
	private Integer ftype;//优惠券类型 1：订单优惠券  2：增值服务券
	private BigDecimal fdiscount;//订单优惠券的折扣
	private BigDecimal fsubtract;//订单优惠券的直减
	private String faddserviceName;//增值服务券对应的增值服务项目
	private java.lang.Integer creator;
	private java.util.Date createTime;
	
	

	//columns END
	private java.lang.String creatorName;
	private String couponsType;//前端显示优惠券类型
//	private java.math.BigDecimal totalDollars;
//	private java.math.BigDecimal totalQuantity;
	

	
	public java.lang.Integer getId() {
		return id;
	}
	
	public String getCouponsType() {
		return couponsType;
	}

	public void setCouponsType(String couponsType) {
		this.couponsType = couponsType;
	}

	public String getFbusinessType() {
		return fbusinessType;
	}

	public void setFbusinessType(String fbusinessType) {
		this.fbusinessType = fbusinessType;
	}

	public void setId(java.lang.Integer id) {
		this.id = id;
	}
	
	public java.lang.String getCreatorName() {
		return creatorName;
	}

	public void setCreatorName(java.lang.String creatorName) {
		this.creatorName = creatorName;
	}

	public String getFcouponsName() {
		return fcouponsName;
	}

	public void setFcouponsName(String fcouponsName) {
		this.fcouponsName = fcouponsName;
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


	public java.lang.Integer getCreator() {
		return creator;
	}

	public void setCreator(java.lang.Integer creator) {
		this.creator = creator;
	}

	public java.util.Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(java.util.Date createTime) {
		this.createTime = createTime;
	}
	
	public String getCreateTimeString() {
		return DateConvertUtils.format(getCreateTime(), FORMAT_CREATE_TIME);
	}
	public void setCreateTimeString(String value) {
		setCreateTime(DateConvertUtils.parse(value, FORMAT_CREATE_TIME,java.util.Date.class));
	}

	public String getFaddserviceName() {
		return faddserviceName;
	}

	public void setFaddserviceName(String faddserviceName) {
		this.faddserviceName = faddserviceName;
	}

	
	
//	public CL_Coupons(){}

	/*public CL_Coupons(java.lang.Integer id){
		this.id = id;
	}

	public void setId(java.lang.Integer value) {
		this.id = value;
	}
	
	public java.lang.Integer getId() {
		return this.id;
	}*/
	
	/*public void setType(java.lang.Integer value) {
		this.type = value;
	}
	
	public java.lang.Integer getType() {
		return this.type;
	}*/
	
	/*public java.lang.String getBatchNumber() {
		return batchNumber;
	}

	public void setBatchNumber(java.lang.String batchNumber) {
		this.batchNumber = batchNumber;
	}*/

	/*public String getStartTimeString() {
		return DateConvertUtils.format(getStartTime(), FORMAT_START_TIME);
	}
	public void setStartTimeString(String value) {
		setStartTime(DateConvertUtils.parse(value, FORMAT_START_TIME,java.util.Date.class));
	}*/
	
	/*public void setStartTime(java.util.Date value) {
		this.startTime = value;
	}
	
	public java.util.Date getStartTime() {
		return this.startTime;
	}*/
	/*public String getEndTimeString() {
		return DateConvertUtils.format(getEndTime(), FORMAT_END_TIME);
	}
	public void setEndTimeString(String value) {
		setEndTime(DateConvertUtils.parse(value, FORMAT_END_TIME,java.util.Date.class));
	}*/
	
	/*public void setEndTime(java.util.Date value) {
		this.endTime = value;
	}
	
	public java.util.Date getEndTime() {
		return this.endTime;
	}*/
	
	/*public java.math.BigDecimal getDollars() {
		return dollars;
	}

	public void setDollars(java.math.BigDecimal dollars) {
		this.dollars = dollars;
	}

	public java.math.BigDecimal getCompareDollars() {
		return compareDollars;
	}

	public void setCompareDollars(java.math.BigDecimal compareDollars) {
		this.compareDollars = compareDollars;
	}

	public void setRedeemCode(java.lang.String value) {
		this.redeemCode = value;
	}
	
	public java.lang.String getRedeemCode() {
		return this.redeemCode;
	}
	public void setIsEffective(java.lang.Integer value) {
		this.isEffective = value;
	}
	
	public java.lang.Integer getIsEffective() {
		return this.isEffective;
	}
	public void setSalesMan(java.lang.String value) {
		this.salesMan = value;
	}
	
	public java.lang.String getSalesMan() {
		return this.salesMan;
	}
	
	public java.lang.String getDescribes() {
		return describes;
	}

	public void setDescribes(java.lang.String describes) {
		this.describes = describes;
	}

	public void setCreator(java.lang.Integer value) {
		this.creator = value;
	}
	
	public java.lang.Integer getCreator() {
		return this.creator;
	}*/
	/*public String getCreateTimeString() {
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

	public java.math.BigDecimal getTotalDollars() {
		return totalDollars;
	}

	public void setTotalDollars(java.math.BigDecimal totalDollars) {
		this.totalDollars = totalDollars;
	}

	public java.math.BigDecimal getTotalQuantity() {
		return totalQuantity;
	}

	public void setTotalQuantity(java.math.BigDecimal totalQuantity) {
		this.totalQuantity = totalQuantity;
	}
	
	public java.lang.Integer getIsOverdue() {
		return isOverdue;
	}

	public void setIsOverdue(java.lang.Integer isOverdue) {
		this.isOverdue = isOverdue;
	}

	public String getCouponsName() {
		return couponsName;
	}

	public void setCouponsName(String couponsName) {
		this.couponsName = couponsName;
	}*/
	
	

	/*public String toString(String separator) {
		StringBuffer sb = new StringBuffer();
			sb.append(ALIAS_ID+":").append(id).append(separator);
			sb.append(ALIAS_TYPE+":").append(type).append(separator);
			sb.append(ALIAS_START_TIME+":").append(getStartTimeString()).append(separator);
			sb.append(ALIAS_END_TIME+":").append(getEndTimeString()).append(separator);
			sb.append(ALIAS_DOLLARS+":").append(dollars).append(separator);
			sb.append(ALIAS_COMPARE_DOLLARS+":").append(compareDollars).append(separator);
			sb.append(ALIAS_REDEEM_CODE+":").append(redeemCode).append(separator);
			sb.append(ALIAS_IS_EFFECTIVE+":").append(isEffective).append(separator);
			sb.append(ALIAS_SALES_MAN+":").append(salesMan).append(separator);
			sb.append(ALIAS_CREATOR+":").append(creator).append(separator);
			sb.append(ALIAS_CREATE_TIME+":").append(getCreateTimeString()).append(separator);
		return sb.toString();
	}*/
	
}

