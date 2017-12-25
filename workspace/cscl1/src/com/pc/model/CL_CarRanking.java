package com.pc.model;

public class CL_CarRanking {
    private java.lang.Integer fid;//主键名字
    private java.lang.String fname;//用户名字
    private java.lang.String fnumber;//用户车牌号
    private java.math.BigDecimal fbalance;//用户余额
    //=============非数据库字段==============//
    private java.lang.Integer type;//  999 默认 固定区别抢单列表前端显示视图作用
	public java.lang.Integer getType() {
		return type;
	}
	public void setType(java.lang.Integer type) {
		this.type = type;
	}
	public java.lang.Integer getFid() {
		return fid;
	}
	public void setFid(java.lang.Integer fid) {
		this.fid = fid;
	}
	public java.lang.String getFname() {
		return fname;
	}
	public void setFname(java.lang.String fname) {
		this.fname = fname;
	}
	public java.lang.String getFnumber() {
		return fnumber;
	}
	public void setFnumber(java.lang.String fnumber) {
		this.fnumber = fnumber;
	}
	public java.math.BigDecimal getFbalance() {
		return fbalance;
	}
	public void setFbalance(java.math.BigDecimal fbalance) {
		this.fbalance = fbalance;
	}
    
}
