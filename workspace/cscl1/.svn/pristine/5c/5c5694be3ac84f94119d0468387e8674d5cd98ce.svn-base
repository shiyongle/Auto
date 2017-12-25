package com.pc.query.coupons;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Calendar;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.pc.query.aBase.BaseQuery;

public class CL_CouponsQuery extends BaseQuery implements Serializable {
    private static final long serialVersionUID = 3148176768559230877L;
    

    private Integer id;
    private String fbusinessType;
	private String fcouponsName;//优惠券名称
	private Integer ftype;//优惠券类型 1：订单优惠券  2：增值服务券
	private BigDecimal fdiscount;//订单优惠券的折扣
	private BigDecimal fsubtract;//订单优惠券的直减
	private Integer faddserviceId;//增值服务券对应的增值服务项目
	private java.lang.Integer fcreator;
	private java.util.Date fcreateTime;
	
	
	private Integer isReceive;//0未领取,1：已领取
	public Integer getIsReceive() {
		return isReceive;
	}

	
	public String getFbusinessType() {
		return fbusinessType;
	}


	public void setFbusinessType(String fbusinessType) {
		this.fbusinessType = fbusinessType;
	}


	public void setIsReceive(Integer isReceive) {
		this.isReceive = isReceive;
	}
	/*
	public java.lang.Integer getId() {
		return this.id;
	}
	
	public void setId(java.lang.Integer value) {
		this.id = value;
	}
	
	public Integer[] getIds() {
		return ids;
	}

	public void setIds(Integer[] ids) {
		this.ids = ids;
	}

	public java.lang.Integer getType() {
		return this.type;
	}
	
	public void setType(java.lang.Integer value) {
		this.type = value;
	}
	
	
	public java.lang.String getBatchNumber() {
		return batchNumber;
	}

	public void setBatchNumber(java.lang.String batchNumber) {
		this.batchNumber = batchNumber;
	}

	public java.lang.String getRedeemCode() {
		return this.redeemCode;
	}
	
	public void setRedeemCode(java.lang.String value) {
		this.redeemCode = value;
	}
	
	public java.lang.Integer getIsEffective() {
		return this.isEffective;
	}
	
	public void setIsEffective(java.lang.Integer value) {
		this.isEffective = value;
	}
	
	public java.lang.String getSalesMan() {
		return this.salesMan;
	}
	
	public void setSalesMan(java.lang.String value) {
		this.salesMan = value;
	}
	
	public java.lang.String getDescribe() {
		return describes;
	}

	public void setDescribes(java.lang.String describes) {
		this.describes = describes;
	}

	public java.lang.Integer getCreator() {
		return this.creator;
	}
	
	public void setCreator(java.lang.Integer value) {
		this.creator = value;
	}
	
	public java.util.Date getCreateTimeBegin() {
		return this.createTimeBegin;
	}
	
	public void setCreateTimeBegin(java.util.Date value) {
		this.createTimeBegin = value;
	}	
	
	public java.util.Date getCreateTimeEnd() {
		return this.createTimeEnd;
	}
	
	
	public java.lang.Integer getIsOverdue() {
		return isOverdue;
	}

	public void setIsOverdue(java.lang.Integer isOverdue) {
		this.isOverdue = isOverdue;
	}

	public void setCreateTimeEnd(java.util.Date value) {
		if(value != null){
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(value);
			calendar.add(Calendar.DAY_OF_MONTH, 1);
			this.createTimeEnd = calendar.getTime();
		}else {
			this.createTimeEnd = value;
		}
	}

	public java.util.Date getStartTime() {
		return startTime;
	}

	public void setStartTime(java.util.Date startTime) {
		this.startTime = startTime;
	}

	public java.util.Date getEndTime() {
		return endTime;
	}

	public void setEndTime(java.util.Date value) {
		if(value != null){
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(value);
			calendar.add(Calendar.DAY_OF_MONTH, 1);
			this.endTime = calendar.getTime();
		}else {
			this.endTime = value;
		}
	}*/

	public Integer getId() {
		return id;
	}



	public void setId(Integer id) {
		this.id = id;
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



	public Integer getFaddserviceId() {
		return faddserviceId;
	}



	public void setFaddserviceId(Integer faddserviceId) {
		this.faddserviceId = faddserviceId;
	}



	public java.lang.Integer getFcreator() {
		return fcreator;
	}



	public void setFcreator(java.lang.Integer fcreator) {
		this.fcreator = fcreator;
	}



	public java.util.Date getFcreateTime() {
		return fcreateTime;
	}



	public void setFcreateTime(java.util.Date fcreateTime) {
		this.fcreateTime = fcreateTime;
	}



	public String toString() {
		return ToStringBuilder.reflectionToString(this,ToStringStyle.MULTI_LINE_STYLE);
	}
	
}

