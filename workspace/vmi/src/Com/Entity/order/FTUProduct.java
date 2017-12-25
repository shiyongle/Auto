package Com.Entity.order;

import java.util.Date;

/**
 * FTUProduct entity. @author MyEclipse Persistence Tools
 */

public class FTUProduct implements java.io.Serializable {

	// Fields

	private String fid;
	private String fname;
	private String fspec;
	private Double fprice;
	private String funit;
	private String fdescription;
	private String fcustomerid;
	private Date fcreatetime;
	private double fprices = 0;		//总额
	private int famount = 0;		//数量

	// Constructors

	/** default constructor */
	public FTUProduct() {
	}

	/** minimal constructor */
	public FTUProduct(String fid, String fname, String fspec, String fcustomerid) {
		this.fid = fid;
		this.fname = fname;
		this.fspec = fspec;
		this.fcustomerid = fcustomerid;
	}

	/** full constructor */
	public FTUProduct(String fid, String fname, String fspec, Double fprice,
			String funit, String fdescription, String fcustomerid) {
		this.fid = fid;
		this.fname = fname;
		this.fspec = fspec;
		this.fprice = fprice;
		this.funit = funit;
		this.fdescription = fdescription;
		this.fcustomerid = fcustomerid;
	}

	// Property accessors

	public String getFid() {
		return this.fid;
	}

	public void setFid(String fid) {
		this.fid = fid;
	}

	public String getFname() {
		return this.fname;
	}

	public void setFname(String fname) {
		this.fname = fname;
	}

	public String getFspec() {
		return this.fspec;
	}

	public void setFspec(String fspec) {
		this.fspec = fspec;
	}

	public Double getFprice() {
		return this.fprice;
	}

	public void setFprice(Double fprice) {
		this.fprice = fprice;
	}

	public String getFunit() {
		return this.funit;
	}

	public void setFunit(String funit) {
		this.funit = funit;
	}

	public String getFdescription() {
		return this.fdescription;
	}

	public void setFdescription(String fdescription) {
		this.fdescription = fdescription;
	}

	public String getFcustomerid() {
		return this.fcustomerid;
	}

	public void setFcustomerid(String fcustomerid) {
		this.fcustomerid = fcustomerid;
	}

	public double getFprices() {
		return fprices;
	}

	public void setFprices(double fprices) {
		this.fprices = fprices;
	}

	public int getFamount() {
		return famount;
	}

	public void setFamount(int famount) {
		this.famount = famount;
	}

	public Date getFcreatetime() {
		return fcreatetime;
	}

	public void setFcreatetime(Date fcreatetime) {
		this.fcreatetime = fcreatetime;
	}

	
	
}