package com.pc.model;

import java.math.BigDecimal;
import java.util.Date;

public class CL_UserBalance {
	
	private Integer fid;
	
	private Integer fuser_role_id;//用户表key
	
	private String fuser_name;//用户名称
	
	private Integer ftype;//用户类型1司机，2客户
	
	private BigDecimal foperate_money;//操作金额
	
	private Integer foperate_type;//操作类型，增加1，减少-1
	
	private Integer foperator;//操作人
	
	private Date foperate_time;//操作时间
	
	private Integer fauditor;//审核人
	
	private Date faudit_time;//审核时间
	
	private Integer fis_pass_identify;//认证1，未认证0
	
	private String fremark;//备注
	/**非字段-----------------------------------------------*/
	private String operator;//操作人
	private String auditor;//审核人
	private BigDecimal money;//余额
	

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
	
	public int getFoperate_type() {
		return foperate_type;
	}

	public void setFoperate_type(int foperate_type) {
		this.foperate_type = foperate_type;
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

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public String getAuditor() {
		return auditor;
	}

	public void setAuditor(String auditor) {
		this.auditor = auditor;
	}

	public String getFremark() {
		return fremark;
	}

	public void setFremark(String fremark) {
		this.fremark = fremark;
	}

	public BigDecimal getMoney() {
		return money;
	}

	public void setMoney(BigDecimal money) {
		this.money = money;
	}

	@Override
	public String toString() {
		return "CL_UserBalance [fid=" + fid + ", fuser_role_id="
				+ fuser_role_id + ", fuser_name=" + fuser_name + ", ftype="
				+ ftype + ", foperate_money=" + foperate_money + ", foperator="
				+ foperator + ", foperate_time=" + foperate_time
				+ ", fauditor=" + fauditor + ", faudit_time=" + faudit_time
				+ ", fis_pass_identify=" + fis_pass_identify + ", fremark="
				+ fremark + ", operator=" + operator + ", auditor=" + auditor
				+ "]";
	}
	
}
