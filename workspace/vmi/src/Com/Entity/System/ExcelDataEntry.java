package Com.Entity.System;




/**
 * ExcelDataEntry entity. @author MyEclipse Persistence Tools
 */

public class ExcelDataEntry  implements java.io.Serializable {


    // Fields    

     private String fid;
     private String fparentid;
     private String ftargetfieldname;
     private String ftargetfieldvalue;
     private String fdatacolumn="";
     private String ffixedcell="";
     private String ffixedvalue="";
     private Integer findex;


    // Constructors

    /** default constructor */
    public ExcelDataEntry() {
    }

	/** minimal constructor */
    public ExcelDataEntry(String fid) {
        this.fid = fid;
    }
    
    /** full constructor */
    public ExcelDataEntry(String fid, String fparentid,
			String ftargetfieldname, String ftargetfieldvalue,
			String fdatacolumn, String ffixedcell, String ffixedvalue,
			Integer findex) {
		super();
		this.fid = fid;
		this.fparentid = fparentid;
		this.ftargetfieldname = ftargetfieldname;
		this.ftargetfieldvalue = ftargetfieldvalue;
		this.fdatacolumn = fdatacolumn;
		this.ffixedcell = ffixedcell;
		this.ffixedvalue = ffixedvalue;
		this.findex = findex;
	}
   

   
    // Property accessors

    public String getFid() {
        return this.fid;
    }
    

	public void setFid(String fid) {
        this.fid = fid;
    }

    public String getFparentid() {
        return this.fparentid;
    }
    
    public void setFparentid(String fparentid) {
        this.fparentid = fparentid;
    }


    public String getFtargetfieldname() {
		return ftargetfieldname;
	}

	public void setFtargetfieldname(String ftargetfieldname) {
		this.ftargetfieldname = ftargetfieldname;
	}

	public String getFtargetfieldvalue() {
		return ftargetfieldvalue;
	}

	public void setFtargetfieldvalue(String ftargetfieldvalue) {
		this.ftargetfieldvalue = ftargetfieldvalue;
	}

	public String getFdatacolumn() {
        return this.fdatacolumn;
    }
    
    public void setFdatacolumn(String fdatacolumn) {
        this.fdatacolumn = fdatacolumn;
    }

    public String getFfixedcell() {
        return this.ffixedcell;
    }
    
    public void setFfixedcell(String ffixedcell) {
        this.ffixedcell = ffixedcell;
    }

    public Integer getFindex() {
        return this.findex;
    }
    
    public void setFindex(Integer findex) {
        this.findex = findex;
    }

	public String getFfixedvalue() {
		return ffixedvalue;
	}

	public void setFfixedvalue(String ffixedvalue) {
		this.ffixedvalue = ffixedvalue;
	}

	@Override
	public String toString() {
		return "ExcelDataEntry [ "
				+ ", ftargetfieldname=" + ftargetfieldname
				+ ", ftargetfieldvalue=" + ftargetfieldvalue + ", fdatacolumn="
				+ fdatacolumn + ", ffixedcell=" + ffixedcell + ", ffixedvalue="
				+ ffixedvalue + ", findex=" + findex + "]";
	}
	
   
    








}