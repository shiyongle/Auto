package com.pc.model;

import java.util.Date;

public class CL_PayLogs {
	
	private Integer fid;		//主键FID
  	private String fpayUrl; 	//请求url
  	private String fpayData;	//请求数据 所有参数
  	private String frespone;	//支付服务返回信息
  	private Date fcreationTime;	//请求时间
  	private Integer fcreatorID;	//请求人ID
  	private String fcreator;	//请求人
  	private String ffinanceNum;	//业务支付订单编号
  	private int flevel;	//日志错误级别 1-致命错误(重点分析) 2-Bug错误(分析) 3-一般错误(不影响系统) 4-警告(不影响系统)
	
	public int getFlevel() {
		return flevel;
	}
	public void setFlevel(int flevel) {
		this.flevel = flevel;
	}
	public String getFpayUrl() {
		return fpayUrl;
	}
	public void setFpayUrl(String fpayUrl) {
		this.fpayUrl = fpayUrl;
	}
	public String getFpayData() {
		return fpayData;
	}
	public void setFpayData(String fpayData) {
		this.fpayData = fpayData;
	}
	public String getFrespone() {
		return frespone;
	}
	public void setFrespone(String frespone) {
		this.frespone = frespone;
	}
	public Date getFcreationTime() {
		return fcreationTime;
	}
	public void setFcreationTime(Date fcreationTime) {
		this.fcreationTime = fcreationTime;
	}
	public Integer getFid() {
		return fid;
	}
	public void setFid(Integer fid) {
		this.fid = fid;
	}
	public Integer getFcreatorID() {
		return fcreatorID;
	}
	public void setFcreatorID(Integer fcreatorID) {
		this.fcreatorID = fcreatorID;
	}
	public String getFcreator() {
		return fcreator;
	}
	public void setFcreator(String fcreator) {
		this.fcreator = fcreator;
	}
	public String getFfinanceNum() {
		return ffinanceNum;
	}
	public void setFfinanceNum(String ffinanceNum) {
		this.ffinanceNum = ffinanceNum;
	}
	
	
}
