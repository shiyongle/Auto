package com.pc.query.select;

import java.io.Serializable;

import com.pc.query.aBase.BaseQuery;

public class selectQuery extends BaseQuery implements Serializable {
	
	private static final long serialVersionUID = -2209480801964661777L;
	
	private java.lang.Integer userRoleId;//司机ID
	private java.lang.Integer creator; // 货主ID
    public java.lang.Integer getUserRoleId() {
		return userRoleId;
	}
	public void setUserRoleId(java.lang.Integer userRoleId) {
		this.userRoleId = userRoleId;
	}
	public java.lang.Integer getCreator() {
		return creator;
	}
	public void setCreator(java.lang.Integer creator) {
		this.creator = creator;
	}

    
    
    
	
    
    
}
