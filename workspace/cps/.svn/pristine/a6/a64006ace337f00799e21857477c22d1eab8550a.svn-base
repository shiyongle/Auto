package com.model.productplan;

import java.util.Date;

import com.util.StringUitl;

public class ProductPlanQuery {
	
	private String searchKey;//关键字
	private String farrivetimeBegin;//出库时间
	private String farrivetimeEnd;//出库时间
	private String timeQuantum;//时间段
	private String fstate;  


	public String getFstate() {
		return fstate;
	}


	public void setFstate(String fstate) {
		this.fstate = fstate;
	}

	public String getSearchKey() {
		return searchKey;
	}

	public void setSearchKey(String searchKey) {
		this.searchKey = searchKey;
	}
	


	
	public String getFarrivetimeBegin() {
		return farrivetimeBegin;
	}


	public void setFarrivetimeBegin(String farrivetimeBegin) {
		this.farrivetimeBegin = farrivetimeBegin;
	}

	public String getFarrivetimeEnd() {
		return farrivetimeEnd;
	}

	public void setFarrivetimeEnd(String farrivetimeEnd) {
		this.farrivetimeEnd = farrivetimeEnd;
	}

	public String getTimeQuantum() {
		return timeQuantum;
	}

	public void setTimeQuantum(String timeQuantum) {
		this.timeQuantum = timeQuantum;
		if(!StringUitl.isNullOrEmpty(timeQuantum)&&timeQuantum.split(" 到 ").length>1)
		{
			this.farrivetimeBegin=timeQuantum.split(" 到 ")[0].trim();
			this.farrivetimeEnd=timeQuantum.split(" 到 ")[1].trim()+" 23:59:59";
		}
	}


}
