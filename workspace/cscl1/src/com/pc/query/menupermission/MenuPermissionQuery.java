package com.pc.query.menupermission;

import java.io.Serializable;

import com.pc.query.aBase.BaseQuery;

public class MenuPermissionQuery extends BaseQuery implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7055969077398600877L;
	
	private int fmenuitemId;
	private String searchKey;
	private int fuserRoleId;
	public int getFuserRoleId() {
		return fuserRoleId;
	}

	public void setFuserRoleId(int fuserRoleId) {
		this.fuserRoleId = fuserRoleId;
	}

	public String getSearchKey() {
		return searchKey;
	}

	public void setSearchKey(String searchKey) {
		this.searchKey = searchKey;
	}

	public int getFmenuitemId() {
		return fmenuitemId;
	}

	public void setFmenuitemId(int fmenuitemId) {
		this.fmenuitemId = fmenuitemId;
	}

}
