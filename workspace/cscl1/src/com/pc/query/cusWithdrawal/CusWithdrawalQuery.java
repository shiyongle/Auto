package com.pc.query.cusWithdrawal;

import java.io.Serializable;
import java.util.Date;

import com.pc.query.aBase.BaseQuery;

public class CusWithdrawalQuery extends BaseQuery implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4974049323169232741L;
	
	private Integer fid;
	private Integer fcustomer;
	private Date start_apply_time;
	private Date end_apply_time;
	public Integer getFid() {
		return fid;
	}
	public void setFid(Integer fid) {
		this.fid = fid;
	}
	public Integer getFcustomer() {
		return fcustomer;
	}
	public void setFcustomer(Integer fcustomer) {
		this.fcustomer = fcustomer;
	}
	public Date getStart_apply_time() {
		return start_apply_time;
	}
	public void setStart_apply_time(Date start_apply_time) {
		this.start_apply_time = start_apply_time;
	}
	public Date getEnd_apply_time() {
		return end_apply_time;
	}
	public void setEnd_apply_time(Date end_apply_time) {
		this.end_apply_time = end_apply_time;
	}
	
	
	
}
