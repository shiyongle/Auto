package com.model.myDelivery;

import java.util.Date;

public class MyDeliveryQuery {
	private Date farrivetimeBegin;//出库时间
	private Date farrivetimeEnd;//出库时间
	private String searchKey;//关键字
	private String timeQuantum;//时间段
	
	public String getTimeQuantum() {
		return timeQuantum;
	}
	public void setTimeQuantum(String timeQuantum) {
		this.timeQuantum = timeQuantum;
	}
	public Date getFarrivetimeBegin() {
		return farrivetimeBegin;
	}
	public void setFarrivetimeBegin(Date farrivetimeBegin) {
		this.farrivetimeBegin = farrivetimeBegin;
	}
	public Date getFarrivetimeEnd() {
		return farrivetimeEnd;
	}
	public void setFarrivetimeEnd(Date farrivetimeEnd) {
		this.farrivetimeEnd = farrivetimeEnd;
	}
	public String getSearchKey() {
		return searchKey;
	}
	public void setSearchKey(String searchKey) {
		this.searchKey = searchKey;
	}
	 
	 
}
