package com.model.orderState;
// default package

import java.util.Date;


/**
 * OrderState entity. @author MyEclipse Persistence Tools
 */

public class OrderState  implements java.io.Serializable {


    // Fields    

     private String fid;
     private String fdeliverapplyid;
     private Integer fstate;
     private Date fcreatetime;

/***************页面显示***********************/
    private String fstateValue;
	public String getFstateValue() {
		return fstateValue;
	}

	
	public void setFstateValue(String fstateValue) {
		this.fstateValue = fstateValue;
	}
    // Constructors

    /** default constructor */
    public OrderState() {
    }

	/** minimal constructor */
    public OrderState(String fid) {
        this.fid = fid;
    }
    
    public OrderState(Integer fstate,Date fcreatetime,String fstateValue) {
    	this.fstate=fstate;
    	this.fcreatetime=fcreatetime;
    	this.fstateValue=fstateValue;
    }

   
    public String getFid() {
        return this.fid;
    }
    
    public void setFid(String fid) {
        this.fid = fid;
    }

    public String getFdeliverapplyid() {
        return this.fdeliverapplyid;
    }
    
    public void setFdeliverapplyid(String fdeliverapplyid) {
        this.fdeliverapplyid = fdeliverapplyid;
    }

    public Integer getFstate() {
        return this.fstate;
    }
    
    public void setFstate(Integer fstate) {
        this.fstate = fstate;
    }

	public Date getFcreatetime() {
		return fcreatetime;
	}

	public void setFcreatetime(Date fcreatetime) {
		this.fcreatetime = fcreatetime;
	}




   








}