package com.pc.query.financeStatement;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Calendar;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.springframework.format.annotation.DateTimeFormat;

import com.pc.query.aBase.BaseQuery;

/**
 * 收支明细
 */
public class FinanceStatementQuery extends BaseQuery implements Serializable {
	private static final long serialVersionUID = 3148176768559230877L;

	/** 自增主键 */
	private java.lang.Integer fid;
	/** 批量选中主键*/
	private Integer[] ids;
	/** 创建申请时间 */ 
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private java.util.Date createTimeBegin;
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private java.util.Date createTimeEnd; 
	/** 用户ID */
	private java.lang.String fuserid;
	private java.lang.Integer fuserroleId;
	private java.lang.String fusername;
	private String fuserPhone;
	/** 收支(1收入,-1支出) */
	private java.lang.Integer ftype;
	/** 支付类型(0在线,1月结) */
	private java.lang.Integer fpayType;
	/** 业务类型(下单支付，补交货款，运营异常，订单完成-司机) */
	private java.lang.Integer fbusinessType;
	/** 订单号*/
	private java.lang.String forderid;
	private java.lang.String frelatedId;
	/** 角色ＩＤ　1：货主　　2:司机*/
	private Integer roleid;
	
	/**充值明细查询条件 Start*/
	private java.lang.String fsaleMan;
	private java.lang.String fcustName;
	private java.lang.Integer faddMoney;
	/**充值明细查询条件 End*/
	private Integer fstatus;
	private BigDecimal freight;
	private String fpayOrder;
	
	
	
	public String getFuserPhone() {
		return fuserPhone;
	}
	public void setFuserPhone(String fuserPhone) {
		this.fuserPhone = fuserPhone;
	}
	public String getFpayOrder() {
		return fpayOrder;
	}
	public void setFpayOrder(String fpayOrder) {
		this.fpayOrder = fpayOrder;
	}
	public BigDecimal getFreight() {
		return freight;
	}
	public void setFreight(BigDecimal freight) {
		this.freight = freight;
	}
	public Integer getFstatus() {
		return fstatus;
	}
	public void setFstatus(Integer fstatus) {
		this.fstatus = fstatus;
	}
	public String getFsaleMan() {
		return fsaleMan;
	}
	public void setFsaleMan(String fsaleMan) {
		this.fsaleMan = fsaleMan;
	}
	public String getFcustName() {
		return fcustName;
	}
	public void setFcustName(String fcustName) {
		this.fcustName = fcustName;
	}
	public java.lang.Integer getFaddMoney() {
		return faddMoney;
	}
	public void setFaddMoney(java.lang.Integer faddMoney) {
		this.faddMoney = faddMoney;
	}
	
	public Integer getRoleid() {
		return roleid;
	}
	public void setRoleid(Integer roleid) {
		this.roleid = roleid;
	}
	public java.lang.String getForderid() {
		return forderid;
	}
	public void setForderid(java.lang.String forderid) {
		this.forderid = forderid;
	}
	/** 交易号*/
	//	private java.lang.String number;
	/** 相关单号(业务类型1、4存订单号，2、3存对应表的ID)*/
	//	private java.lang.String frelatedid;
	/** 金额*/
	//	private java.math.BigDecimal famount;

	public java.util.Date getCreateTimeBegin() {
		return createTimeBegin;
	}
	public void setCreateTimeBegin(java.util.Date createTimeBegin) {
		this.createTimeBegin = createTimeBegin;
	}
	
	public java.util.Date getCreateTimeEnd() {
		return createTimeEnd;
	}
	public void setCreateTimeEnd(java.util.Date createTimeEnd) {
		if(createTimeEnd != null){
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(createTimeEnd);
			calendar.add(Calendar.DAY_OF_MONTH, 1);
			this.createTimeEnd = calendar.getTime();
		}else {
			this.createTimeEnd = createTimeEnd;
		}
	}

	public java.lang.String getFusername() {
		return fusername;
	}
	public void setFusername(java.lang.String fusername) {
		this.fusername = fusername;
	}
	
	public java.lang.String getFrelatedId() {
		return frelatedId;
	}
	public void setFrelatedId(java.lang.String frelatedId) {
		this.frelatedId = frelatedId;
	}
	public java.lang.Integer getFid() {
		return fid;
	}
	public void setFid(java.lang.Integer fid) {
		this.fid = fid;
	}

	public Integer[] getIds() {
		return ids;
	}
	public void setIds(Integer[] ids) {
		this.ids = ids;
	}

	public java.lang.Integer getFbusinessType() {
		return fbusinessType;
	}
	public void setFbusinessType(java.lang.Integer fbusinessType) {
		this.fbusinessType = fbusinessType;
	}

	public java.lang.Integer getFtype() {
		return ftype;
	}
	public void setFtype(java.lang.Integer ftype) {
		this.ftype = ftype;
	}

	public java.lang.String getFuserid() {
		return fuserid;
	}
	public void setFuserid(java.lang.String fuserid) {
		this.fuserid = fuserid;
	}
	
	public java.lang.Integer getFuserroleId() {
		return fuserroleId;
	}
	public void setFuserroleId(java.lang.Integer fuserroleId) {
		this.fuserroleId = fuserroleId;
	}
	
	public java.lang.Integer getFpayType() {
		return fpayType;
	}
	public void setFpayType(java.lang.Integer fpayType) {
		this.fpayType = fpayType;
	}

	public String toString() {
		return ToStringBuilder.reflectionToString(this,ToStringStyle.MULTI_LINE_STYLE);
	}
}

