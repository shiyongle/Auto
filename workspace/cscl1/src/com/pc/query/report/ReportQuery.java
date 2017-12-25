package com.pc.query.report;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

import com.pc.query.aBase.BaseQuery;

public class ReportQuery extends BaseQuery implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2525144355960500487L;
	// Ingeter默认为null   int默认为0
	private Integer fuserid;            //货主id
	private Integer fcustomerid;        //客户id
	private Integer fcarid;       		//车辆id
	private String fdriverName; 	    //司机
	private String fsaleman;			//业务员
	private String fsalesmandept;		//业务员部门
	private String fregion;			    //客户区域
	private Date fstarttime;			//订单时间
	private Date fendtime;              
	//private Integer fromIndex;			//第几页
	public Integer getFuserid() {
		return fuserid;
	}
	public void setFuserid(Integer fuserid) {
		this.fuserid = fuserid;
	}
	private String fmonth;
	
	public String getFmonth() {
		return fmonth;
	}
	public void setFmonth(String fmonth) {
		this.fmonth = fmonth;
	}
	public String getFstartMonth() {
		return fstartMonth;
	}
	public void setFstartMonth(String fstartMonth) {
		this.fstartMonth = fstartMonth;
	}
	public String getFendMonth() {
		return fendMonth;
	}
	public void setFendMonth(String fendMonth) {
		this.fendMonth = fendMonth;
	}
	private String fstartMonth;         //订单开始月份
	private String fendMonth;           //订单结束月份
	
	public Integer getFcustomerid() {
		return fcustomerid;
	}
	public void setFcustomerid(Integer fcustomerid) {
		this.fcustomerid = fcustomerid;
	}
	public Integer getFcarid() {
		return fcarid;
	}
	public void setFcarid(Integer fcarid) {
		this.fcarid = fcarid;
	}
	public String getFsaleman() {
		return fsaleman;
	}
	public void setFsaleman(String fsaleman) {
		this.fsaleman = fsaleman;
	}
	public String getFsalesmandept() {
		return fsalesmandept;
	}
	public void setFsalesmandept(String fsalesmandept) {
		this.fsalesmandept = fsalesmandept;
	}
	public String getFregion() {
		return fregion;
	}
	public void setFregion(String fregion) {
		this.fregion = fregion;
	}
	public Date getFstarttime() {
		return fstarttime;
	}
	public Date getFendtime() {
		return fendtime;
	}
	public String getFdriverName() {
		return fdriverName;
	}
	public void setFdriverName(String fdriverName) {
		this.fdriverName = fdriverName;
	}
	public void setFstarttime(java.util.Date value) {
		this.fstarttime = value;
	}
	public void setFendtime(java.util.Date value) {
		if(value != null){
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(value);
			calendar.add(Calendar.DAY_OF_MONTH, 1);
			this.fendtime = calendar.getTime();
		}else {
			this.fendtime = value;
		}
	}
	
}
