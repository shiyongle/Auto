package com.pc.query.orderCarDetail;

import java.io.Serializable;
import java.util.Calendar;

import com.pc.query.aBase.BaseQuery;

public class CL_OrderCarDetailQuery extends BaseQuery implements Serializable {
    private static final long serialVersionUID = 3148176768559230877L;
    

	/** id */
	private java.lang.Integer id;
	/** 订单ID关联订单表 */
	private java.lang.Integer orderId;
	/** 车辆规格ID */
	private java.lang.Integer carSpecId;
	/** 车辆类型id */
	private java.lang.Integer carTypeId;
	/** 其他要求ID */
	private java.lang.Integer carOtherId;
	/** createTime */
	private java.util.Date createTimeBegin;
	private java.util.Date createTimeEnd;

	public java.lang.Integer getId() {
		return this.id;
	}
	
	public void setId(java.lang.Integer value) {
		this.id = value;
	}
	
	public java.lang.Integer getOrderId() {
		return this.orderId;
	}
	
	public void setOrderId(java.lang.Integer value) {
		this.orderId = value;
	}
	
	public java.lang.Integer getCarSpecId() {
		return this.carSpecId;
	}
	
	public void setCarSpecId(java.lang.Integer value) {
		this.carSpecId = value;
	}
	
	public java.lang.Integer getCarTypeId() {
		return this.carTypeId;
	}
	
	public void setCarTypeId(java.lang.Integer value) {
		this.carTypeId = value;
	}
	
	public java.lang.Integer getCarOtherId() {
		return this.carOtherId;
	}
	
	public void setCarOtherId(java.lang.Integer value) {
		this.carOtherId = value;
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
	
}

