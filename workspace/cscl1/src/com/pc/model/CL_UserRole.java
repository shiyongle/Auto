package com.pc.model;

import java.math.BigDecimal;

import cn.org.rapid_framework.util.DateConvertUtils;

public class CL_UserRole {
   protected static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
   private int id;//主键
   private String vmiUserFid; //Vmi数据库用户表主键
   private int roleId; //角色Id
   private java.util.Date createTime;//创建时间
   private String vmiUserName;//Vmi数据库用户表的名字
   private String vmiUserPhone;//Vmi数据库用户表的手机号码
   private String alipayId;//支付宝帐号
   private String bankAccount;//银行卡帐号
   private String bankName;//开户行名称
   private String bankAddress;//开户行所在地
   private Integer endOrderTimes;//已成单次数
   private Integer rateTimes;//被评价次数
   private boolean isProtocol;//是否协议用户
   private boolean isPassIdentify;//认证标记
   private boolean isSub;//是否子帐号
   private Integer fparentid;//管理帐号Id
   private String fparentTel;//管理帐号手机号
   private String fpassword;//子帐号密码
   
   private BigDecimal fbalance=new BigDecimal("0");//余额
   private String fpaypassword;//支付密码
   

//=============非数据库字段,只做前端显示========================///
   private String fname;
   private String ftel;
   private String uname;
//   private String fpassword;
   
   public String getUname() {
	   return uname;
   }
   public BigDecimal getFbalance() {
	return fbalance;
}
public void setFbalance(BigDecimal fbalance) {
	this.fbalance = fbalance;
}
public String getFpaypassword() {
	return fpaypassword;
}
public void setFpaypassword(String fpaypassword) {
	this.fpaypassword = fpaypassword;
}
public void setUname(String uname) {
	   this.uname = uname;
   }
   
   public String getFname() { 
	return fname;
   }
   public void setFname(String fname) {
		this.fname = fname;
   }
   public String getFtel() {
		return ftel;
   }
   public void setFtel(String ftel) {
		this.ftel = ftel;
   }
   
   public String getFpassword() {
	return fpassword;
   }
   public void setFpassword(String fpassword) {
		this.fpassword = fpassword;
   }
//=============非数据库字段,只做前端显示========================///
   public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getVmiUserFid() {
		return vmiUserFid;
	}
	public void setVmiUserFid(String vmiUserFid) {
		this.vmiUserFid = vmiUserFid;
	}
	public int getRoleId() {
		return roleId;
	}
	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}
	public java.util.Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(java.util.Date createTime) {
		this.createTime = createTime;
	}
   
	public String getCreateTimeString() {
		return DateConvertUtils.format(getCreateTime(), DATE_TIME_FORMAT);
	}
	public void setCreateTimeString(String value) {
		setCreateTime(DateConvertUtils.parse(value, DATE_TIME_FORMAT,java.util.Date.class));
	}
	public String getVmiUserName() {
		return vmiUserName;
	}
	public void setVmiUserName(String vmiUserName) {
		this.vmiUserName = vmiUserName;
	}
	public String getVmiUserPhone() {
		return vmiUserPhone;
	}
	public void setVmiUserPhone(String vmiUserPhone) {
		this.vmiUserPhone = vmiUserPhone;
	}
	public String getAlipayId() {
		return alipayId;
	}
	public void setAlipayId(String alipayId) {
		this.alipayId = alipayId;
	}
	public String getBankAccount() {
		return bankAccount;
	}
	public void setBankAccount(String bankAccount) {
		this.bankAccount = bankAccount;
	}
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	public String getBankAddress() {
		return bankAddress;
	}
	public void setBankAddress(String bankAddress) {
		this.bankAddress = bankAddress;
	}
	
	public Integer getEndOrderTimes() {
		return endOrderTimes;
	}
	public void setEndOrderTimes(Integer endOrderTimes) {
		this.endOrderTimes = endOrderTimes;
	}
	public Integer getRateTimes() {
		return rateTimes;
	}
	public void setRateTimes(Integer rateTimes) {
		this.rateTimes = rateTimes;
	}
	public boolean isProtocol() {
		return isProtocol;
	}
	public void setProtocol(boolean isProtocol) {
		this.isProtocol = isProtocol;
	}
	public boolean isPassIdentify() {
		return isPassIdentify;
	}
	public void setPassIdentify(boolean isPassIdentify) {
		this.isPassIdentify = isPassIdentify;
	}
	public boolean isSub() {
		return isSub;
	}
	public void setSub(boolean isSub) {
		this.isSub = isSub;
	}
	public Integer getFparentid() {
		return fparentid;
	}
	public void setFparentid(Integer fparentid) {
		this.fparentid = fparentid;
	}
	public String getFparentTel() {
		return fparentTel;
	}
	public void setFparentTel(String fparentTel) {
		this.fparentTel = fparentTel;
	}
	
}
