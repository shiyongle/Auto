package Com.Entity.order;

import java.util.Date;

public class Statement implements java.io.Serializable {

	 String fid;
	 String fcustomerid ;
	 String fmonth;
	 String ffileid;
	 String fcreateuserid;
	 Date fcreatetime;
	public String getFid() {
		return fid;
	}
	public void setFid(String fid) {
		this.fid = fid;
	}
	public String getFcustomerid() {
		return fcustomerid;
	}
	public void setFcustomerid(String fcustomerid) {
		this.fcustomerid = fcustomerid;
	}
	public String getFmonth() {
		return fmonth;
	}
	public void setFmonth(String fyearmonth) {
		this.fmonth = fyearmonth;
	}
	public String getFfileid() {
		return ffileid;
	}
	public void setFfileid(String ffileid) {
		this.ffileid = ffileid;
	}
	public String getFcreateuserid() {
		return fcreateuserid;
	}
	public void setFcreateuserid(String fcreateuserid) {
		this.fcreateuserid = fcreateuserid;
	}
	public Date getFcreatetime() {
		return fcreatetime;
	}
	public void setFcreatetime(Date fcreatetime) {
		this.fcreatetime = fcreatetime;
	}
}
