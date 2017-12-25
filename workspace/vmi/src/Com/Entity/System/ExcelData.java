package Com.Entity.System;

import java.util.Date;




/**
 * ExcelData entity. @author MyEclipse Persistence Tools
 */

public class ExcelData  implements java.io.Serializable {


    // Fields    

     private String fid;
     private String fnumber;
     private String fcustomerid;
     private String ftarget;
     private Integer fstartrow;
     private String fendtext="";
     private Date fcreatetime;
     private String fcreatorid;


    // Constructors

    /** default constructor */
    public ExcelData() {
    }

	/** minimal constructor */
    public ExcelData(String fid) {
        this.fid = fid;
    }
    
    /** full constructor */
    public ExcelData(String fid, String fnumber, String fcustomerid, String ftarget, Integer fstartrow, String fendtext, Date fcreatetime, String fcreatorid) {
        this.fid = fid;
        this.fnumber = fnumber;
        this.fcustomerid = fcustomerid;
        this.ftarget = ftarget;
        this.fstartrow = fstartrow;
        this.fendtext = fendtext;
        this.fcreatetime = fcreatetime;
        this.fcreatorid = fcreatorid;
    }

   
    // Property accessors

    public String getFid() {
        return this.fid;
    }
    
    public void setFid(String fid) {
        this.fid = fid;
    }

    public String getFnumber() {
        return this.fnumber;
    }
    
    public void setFnumber(String fnumber) {
        this.fnumber = fnumber;
    }

    public String getFcustomerid() {
        return this.fcustomerid;
    }
    
    public void setFcustomerid(String fcustomerid) {
        this.fcustomerid = fcustomerid;
    }

    public String getFtarget() {
        return this.ftarget;
    }
    
    public void setFtarget(String ftarget) {
        this.ftarget = ftarget;
    }

    public Integer getFstartrow() {
        return this.fstartrow;
    }
    
    public void setFstartrow(Integer fstartrow) {
        this.fstartrow = fstartrow;
    }

    public String getFendtext() {
        return this.fendtext;
    }
    
    public void setFendtext(String fendtext) {
        this.fendtext = fendtext;
    }


    public String getFcreatorid() {
        return this.fcreatorid;
    }
    
    public void setFcreatorid(String fcreatorid) {
        this.fcreatorid = fcreatorid;
    }

	public Date getFcreatetime() {
		return fcreatetime;
	}

	public void setFcreatetime(Date fcreatetime) {
		this.fcreatetime = fcreatetime;
	}
   
    







}