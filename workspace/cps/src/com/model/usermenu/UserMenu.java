package com.model.usermenu;

/**
 * 用户菜单配置表
 *
 */
public class UserMenu {
	private String fid;//配置编号
	private String fuid;//用户编号
	private String fmenu;//配置的菜单功能
	public String getFid() {
		return fid;
	}
	public void setFid(String fid) {
		this.fid = fid;
	}
	public String getFmenu() {
		return fmenu;
	}
	public void setFmenu(String fmenu) {
		this.fmenu = fmenu;
	}
	
	public UserMenu(String fid, String fuid, String fmenu) {
		super();
		this.fid = fid;
		this.fuid = fuid;
		this.fmenu = fmenu;
	}
	public String getFuid() {
		return fuid;
	}
	public void setFuid(String fuid) {
		this.fuid = fuid;
	}
	public UserMenu() {
		super();
	}
	public UserMenu(String fuid, String fmenu) {
		super();
		this.fuid = fuid;
		this.fmenu = fmenu;
	}
	
	
}