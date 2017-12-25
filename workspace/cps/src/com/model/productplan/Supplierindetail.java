package com.model.productplan;

import java.util.Date;

public class Supplierindetail {

	private String fid;
	private String fsupplierid;
	private String fpcmordernumber;
	private Integer famount;
	private Integer finqty;
	private Date fintime;
	private Integer fstate;

	public Supplierindetail() {
	}

	public Supplierindetail(String fid) {
		this.fid = fid;
	}

	public Supplierindetail(String fid, String fsupplierid,
			String fpcmordernumber, Integer famount, Integer finqty,
			Date fintime, Integer fstate) {
		this.fid = fid;
		this.fsupplierid = fsupplierid;
		this.fpcmordernumber = fpcmordernumber;
		this.famount = famount;
		this.finqty = finqty;
		this.fintime = fintime;
		this.fstate = fstate;
	}

	public String getFid() {
		return this.fid;
	}

	public void setFid(String fid) {
		this.fid = fid;
	}

	public String getFsupplierid() {
		return this.fsupplierid;
	}

	public void setFsupplierid(String fsupplierid) {
		this.fsupplierid = fsupplierid;
	}

	public String getFpcmordernumber() {
		return this.fpcmordernumber;
	}

	public void setFpcmordernumber(String fpcmordernumber) {
		this.fpcmordernumber = fpcmordernumber;
	}

	public Integer getFamount() {
		return this.famount;
	}

	public void setFamount(Integer famount) {
		this.famount = famount;
	}

	public Integer getFinqty() {
		return this.finqty;
	}

	public void setFinqty(Integer finqty) {
		this.finqty = finqty;
	}

	public Date getFintime() {
		return this.fintime;
	}

	public void setFintime(Date fintime) {
		this.fintime = fintime;
	}

	public Integer getFstate() {
		return this.fstate;
	}

	public void setFstate(Integer fstate) {
		this.fstate = fstate;
	}

}
