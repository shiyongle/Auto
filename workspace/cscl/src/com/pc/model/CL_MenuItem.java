package com.pc.model;

public class CL_MenuItem {
	private int fid;             //菜单id
	private boolean fisleaf;     //菜单是否叶子节点
	private int fparentid;       //关联父菜单
	private String fname;        //菜单名
	private String furl;         //菜单链接url
	//非表字段
	private String fuserName;     //有此权限用户
	private String fparentMenuName;//父菜单名称
	
	public int getFid() {
		return fid;
	}
	public void setFid(int fid) {
		this.fid = fid;
	}
	public boolean isFisleaf() {
		return fisleaf;
	}
	public void setFisleaf(boolean fisleaf) {
		this.fisleaf = fisleaf;
	}
	public int getFparentid() {
		return fparentid;
	}
	public void setFparentid(int fparentid) {
		this.fparentid = fparentid;
	}
	public String getFname() {
		return fname;
	}
	public void setFname(String fname) {
		this.fname = fname;
	}
	public String getFurl() {
		return furl;
	}
	public void setFurl(String furl) {
		this.furl = furl;
	}
	public String getFuserName() {
		return fuserName;
	}
	public void setFuserName(String fuserName) {
		this.fuserName = fuserName;
	}
	public String getFparentMenuName() {
		return fparentMenuName;
	}
	public void setFparentMenuName(String fparentMenuName) {
		this.fparentMenuName = fparentMenuName;
	}

}
