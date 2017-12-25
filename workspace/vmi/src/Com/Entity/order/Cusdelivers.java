package Com.Entity.order;
// default package
// Generated 2013-11-20 11:17:10 by Hibernate Tools 3.4.0.CR1

import java.util.Date;

/**
 * TOrdCusdelivers generated by hbm2java
 */
public class Cusdelivers implements java.io.Serializable {

	private String fnumber;
	private String fmakno;
	private String fmaktx;
	private Integer famount;
	private Date farrivetime;
	private String freqaddress="";
	private String flinkman;
	private String flinkphone;
	private String fdescription;
	private String fcusfid;
	private String fid;
	private String fcusproduct;
	private String fcustomerid;
	private String faddress="";
	private Integer fisread=0;
	private Date freqdate;
	private Date fcreatetime = new Date();
	private String fcreatorid;
	private String fmaksupplier;
	private String fsupplierid;
	private Integer ftype;
	private int currentRow;	//不关联数据库
	private int group;			//不关联数据库
	
	public Cusdelivers() {
	}
	
	public Cusdelivers(int currentRow){
		this.currentRow = currentRow;
	}
	
	public Cusdelivers(int currentRow,int group){
		this.currentRow = currentRow;
		this.group = group;
	}
	
	
	public Cusdelivers(String fnumber, String fmakno, String fmaktx,
			Integer famount, Date farrivetime, String freqaddress,
			String flinkman, String flinkphone, String fdescription,
			String fcusfid, String fid, String fcusproduct, String fcustomerid,
			String faddress, Integer fisread, Date freqdate, Date fcreatetime,
			String fcreatorid, String fmaksupplier, String fsupplierid,
			Integer ftype, int currentRow) {
		super();
		this.fnumber = fnumber;
		this.fmakno = fmakno;
		this.fmaktx = fmaktx;
		this.famount = famount;
		this.farrivetime = farrivetime;
		this.freqaddress = freqaddress;
		this.flinkman = flinkman;
		this.flinkphone = flinkphone;
		this.fdescription = fdescription;
		this.fcusfid = fcusfid;
		this.fid = fid;
		this.fcusproduct = fcusproduct;
		this.fcustomerid = fcustomerid;
		this.faddress = faddress;
		this.fisread = fisread;
		this.freqdate = freqdate;
		this.fcreatetime = fcreatetime;
		this.fcreatorid = fcreatorid;
		this.fmaksupplier = fmaksupplier;
		this.fsupplierid = fsupplierid;
		this.ftype = ftype;
		this.currentRow = currentRow;
	}

	public int getGroup() {
		return group;
	}

	public void setGroup(int group) {
		this.group = group;
	}

	public void setCurrentRow(int currentRow) {
		this.currentRow = currentRow;
	}

	public int getCurrentRow() {
		return currentRow;
	}


	public String getFmakno() {
		return fmakno;
	}

	public void setFmakno(String fmakno) {
		this.fmakno = fmakno;
	}

	public Date getFreqdate() {
		return freqdate;
	}

	public void setFreqdate(Date freqdate) {
		this.freqdate = freqdate;
	}

	public String getFcusproduct() {
		return fcusproduct;
	}

	public void setFcusproduct(String fcusproduct) {
		this.fcusproduct = fcusproduct;
	}

	public String getFcustomerid() {
		return fcustomerid;
	}

	public void setFcustomerid(String fcustomerid) {
		this.fcustomerid = fcustomerid;
	}


	public String getFmaktx() {
		return fmaktx;
	}

	public void setFmaktx(String fmaktx) {
		this.fmaktx = fmaktx;
	}

	public String getFreqaddress() {
		return freqaddress;
	}

	public void setFreqaddress(String freqaddress) {
		this.freqaddress = freqaddress;
	}

	public String getFcusfid() {
		return fcusfid;
	}

	public void setFcusfid(String fcusfid) {
		this.fcusfid = fcusfid;
	}


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

	public Date getFarrivetime() {
		return this.farrivetime;
	}

	public void setFarrivetime(Date farrivetime) {
		this.farrivetime = farrivetime;
	}

	public String getFlinkman() {
		return this.flinkman;
	}

	public void setFlinkman(String flinkman) {
		this.flinkman = flinkman;
	}

	public String getFlinkphone() {
		return this.flinkphone;
	}

	public void setFlinkphone(String flinkphone) {
		this.flinkphone = flinkphone;
	}


	public Integer getFamount() {
		return famount;
	}

	public void setFamount(Integer famount) {
		this.famount = famount;
	}

	public String getFaddress() {
		return this.faddress;
	}

	public void setFaddress(String faddress) {
		this.faddress = faddress;
	}

	public String getFdescription() {
		return this.fdescription;
	}

	public void setFdescription(String fdescription) {
		this.fdescription = fdescription;
	}

	public int getFisread() {
		return this.fisread;
	}

	public void setFisread(Integer fisread) {
		this.fisread = fisread;
	}

	public Date getFcreatetime() {
		return fcreatetime;
	}

	public void setFcreatetime(Date fcreatetime) {
		this.fcreatetime = fcreatetime;
	}

	public String getFcreatorid() {
		return fcreatorid;
	}

	public void setFcreatorid(String fcreatorid) {
		this.fcreatorid = fcreatorid;
	}

	public String getFmaksupplier() {
		return fmaksupplier;
	}

	public void setFmaksupplier(String fmaksupplier) {
		this.fmaksupplier = fmaksupplier;
	}

	public String getFsupplierid() {
		return fsupplierid;
	}

	public void setFsupplierid(String fsupplierid) {
		this.fsupplierid = fsupplierid;
	}

	public Integer getFtype() {
		return ftype;
	}

	public void setFtype(Integer ftype) {
		this.ftype = ftype;
	}

	@Override
	public String toString() {
		return "Cusdelivers [fmaktx=" + fmaktx + ", famount=" + famount
				+ ", ftype=" + ftype + "]";
	}

	
	
}
