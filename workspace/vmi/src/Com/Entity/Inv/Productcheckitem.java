package Com.Entity.Inv;
// default package
// Generated 2014-12-6 14:49:28 by Hibernate Tools 3.4.0.CR1

/**
 * TInvProductcheckitem generated by hbm2java
 */
public class Productcheckitem implements java.io.Serializable {

	private String fid;
	private String fproductid;
	private Integer fqty;
	private String fproductcheckid;
	private String fremark;
	
	public Productcheckitem() {
	}

	public Productcheckitem(String fid) {
		
		this.fid = fid;
		
	}
	
	public Productcheckitem(String fid, String fproductid) {
		this.fid = fid;
		this.fproductid = fproductid;
	}

	public Productcheckitem(String fid, String fproductid, Integer fqty,
			String fproductcheckid, String fremark) {
		this.fid = fid;
		this.fproductid = fproductid;
		this.fqty = fqty;
		this.fproductcheckid = fproductcheckid;
		this.fremark = fremark;
	}

	
	
	public String getFremark() {
		return fremark;
	}

	public void setFremark(String fremark) {
		this.fremark = fremark;
	}

	public String getFid() {
		return this.fid;
	}

	public void setFid(String fid) {
		this.fid = fid;
	}

	public String getFproductid() {
		return this.fproductid;
	}

	public void setFproductid(String fproductid) {
		this.fproductid = fproductid;
	}

	public Integer getFqty() {
		return this.fqty;
	}

	public void setFqty(Integer fqty) {
		this.fqty = fqty;
	}

	public String getFproductcheckid() {
		return this.fproductcheckid;
	}

	public void setFproductcheckid(String fproductcheckid) {
		this.fproductcheckid = fproductcheckid;
	}

}
