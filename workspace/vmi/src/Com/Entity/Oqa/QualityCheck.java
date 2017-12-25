package Com.Entity.Oqa;
// default package
// Generated 2013-9-3 9:22:02 by Hibernate Tools 3.4.0.CR1

import java.util.Date;

/**
 * TOqaQualitycheck generated by hbm2java
 */
public class QualityCheck implements java.io.Serializable {

	private String fid;
	private String fcreatorid;
	private Date fcreatetime;
	private String flastupdateuserid;
	private Date flastupdatetime;
	private String fdeliverid;
	private String fsaleorderid;
	private int fcheckresult;
	private String fquestdescription;
	private String fnumber;

	public QualityCheck() {
	}

	public QualityCheck(String fid, int fcheckresult) {
		this.fid = fid;
		this.fcheckresult = fcheckresult;
	}

	public QualityCheck(String fid, String fcreatorid, Date fcreatetime,
			String flastupdateuserid, Date flastupdatetime, String fdelieverid,
			String fsaleorderid, int fcheckresult, String fquestdescription,String fnumber) {
		this.fid = fid;
		this.fcreatorid = fcreatorid;
		this.fcreatetime = fcreatetime;
		this.flastupdateuserid = flastupdateuserid;
		this.flastupdatetime = flastupdatetime;
		this.fdeliverid = fdelieverid;
		this.fsaleorderid = fsaleorderid;
		this.fcheckresult = fcheckresult;
		this.fquestdescription = fquestdescription;
		this.fnumber=fnumber;
	}

	public String getFid() {
		return this.fid;
	}

	public void setFid(String fid) {
		this.fid = fid;
	}

	public String getFcreatorid() {
		return this.fcreatorid;
	}

	public void setFcreatorid(String fcreatorid) {
		this.fcreatorid = fcreatorid;
	}

	public Date getFcreatetime() {
		return this.fcreatetime;
	}

	public void setFcreatetime(Date fcreatetime) {
		this.fcreatetime = fcreatetime;
	}

	public String getFlastupdateuserid() {
		return this.flastupdateuserid;
	}

	public void setFlastupdateuserid(String flastupdateuserid) {
		this.flastupdateuserid = flastupdateuserid;
	}

	public Date getFlastupdatetime() {
		return this.flastupdatetime;
	}

	public void setFlastupdatetime(Date flastupdatetime) {
		this.flastupdatetime = flastupdatetime;
	}

	public String getFdeliverid() {
		return this.fdeliverid;
	}

	public void setFdeliverid(String fdeliverid) {
		this.fdeliverid = fdeliverid;
	}

	public String getFsaleorderid() {
		return this.fsaleorderid;
	}

	public void setFsaleorderid(String fsaleorderid) {
		this.fsaleorderid = fsaleorderid;
	}

	public int getFcheckresult() {
		return this.fcheckresult;
	}

	public void setFcheckresult(int fcheckresult) {
		this.fcheckresult = fcheckresult;
	}

	public String getFquestdescription() {
		return this.fquestdescription;
	}

	public void setFquestdescription(String fquestdescription) {
		this.fquestdescription = fquestdescription;
	}

	public String getFnumber() {
		return fnumber;
	}

	public void setFnumber(String fnumber) {
		this.fnumber = fnumber;
	}

}
