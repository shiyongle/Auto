package com.pc.model;

public class CL_Bank_Info {

	private Integer fid;
	private String fbankName;  //银行名称
	private String fcardIndex;  //银行卡号前缀
	
	private String ficon;  //图标
	private String fvercode;//支付识别码
	private String fdigits;//银行识别位数
	private String ftype;//银行卡类型 1-借记卡 2-信用卡
	private String fshortName; //银行短名称
	private String fbankNumber; //银行短名称
	
	public String getFbankNumber() {
		return fbankNumber;
	}
	public void setFbankNumber(String fbankNumber) {
		this.fbankNumber = fbankNumber;
	}
	public Integer getFid() {
		return fid;
	}
	public void setFid(Integer fid) {
		this.fid = fid;
	}
	public String getFbankName() {
		return fbankName;
	}
	public void setFbankName(String fbankName) {
		this.fbankName = fbankName;
	}
	public String getFcardIndex() {
		return fcardIndex;
	}
	public void setFcardIndex(String fcardIndex) {
		this.fcardIndex = fcardIndex;
	}
	public String getFicon() {
		return ficon;
	}
	public void setFicon(String ficon) {
		this.ficon = ficon;
	}
	public String getFvercode() {
		return fvercode;
	}
	public void setFvercode(String fvercode) {
		this.fvercode = fvercode;
	}
	public String getFdigits() {
		return fdigits;
	}
	public void setFdigits(String fdigits) {
		this.fdigits = fdigits;
	}
	public String getFtype() {
		return ftype;
	}
	public void setFtype(String ftype) {
		this.ftype = ftype;
	}
	public String getFshortName() {
		return fshortName;
	}
	public void setFshortName(String fshortName) {
		this.fshortName = fshortName;
	}
	
}
