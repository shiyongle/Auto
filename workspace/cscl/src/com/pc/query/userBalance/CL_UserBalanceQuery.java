package com.pc.query.userBalance;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.pc.query.aBase.BaseQuery;

public class CL_UserBalanceQuery extends BaseQuery implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3603331941756234591L;
	private Integer fid;
	private Integer fuser_role_id;//用户表key
	private String fuser_name;//用户名称
	private Integer ftype;//用户类型1司机，2客户
	private BigDecimal foperate_money;//操作金额
	private Integer foperate_type;
	private Integer foperator;//操作人
	private Date foperate_time;//操作时间
	private Integer fauditor;//审核人
	private Date faudit_time;//审核时间
	private Integer fis_pass_identify;//认证1，未认证0
	
	private String uname;
	private String money;
	
	
	
	public Integer getFoperate_type() {
		return foperate_type;
	}
	public void setFoperate_type(Integer foperate_type) {
		this.foperate_type = foperate_type;
	}
	public String getMoney() {
		return money;
	}
	public void setMoney(String money) {
		this.money = money;
	}
	public String getUname() {
		return uname;
	}
	public void setUname(String uname) {
		this.uname = uname;
	}
	public Integer getFid() {
		return fid;
	}
	public void setFid(Integer fid) {
		this.fid = fid;
	}
	public Integer getFuser_role_id() {
		return fuser_role_id;
	}
	public void setFuser_role_id(Integer fuser_role_id) {
		this.fuser_role_id = fuser_role_id;
	}
	public String getFuser_name() {
		return fuser_name;
	}
	public void setFuser_name(String fuser_name) {
		this.fuser_name = fuser_name;
	}
	public Integer getFtype() {
		return ftype;
	}
	public void setFtype(Integer ftype) {
		this.ftype = ftype;
	}
	public BigDecimal getFoperate_money() {
		return foperate_money;
	}
	public void setFoperate_money(BigDecimal foperate_money) {
		this.foperate_money = foperate_money;
	}
	public Integer getFoperator() {
		return foperator;
	}
	public void setFoperator(Integer foperator) {
		this.foperator = foperator;
	}
	public Date getFoperate_time() {
		return foperate_time;
	}
	public void setFoperate_time(Date foperate_time) {
		this.foperate_time = foperate_time;
	}
	public Integer getFauditor() {
		return fauditor;
	}
	public void setFauditor(Integer fauditor) {
		this.fauditor = fauditor;
	}
	public Date getFaudit_time() {
		return faudit_time;
	}
	public void setFaudit_time(Date faudit_time) {
		this.faudit_time = faudit_time;
	}
	public Integer getFis_pass_identify() {
		return fis_pass_identify;
	}
	public void setFis_pass_identify(Integer fis_pass_identify) {
		this.fis_pass_identify = fis_pass_identify;
	}
	
	
	
}
