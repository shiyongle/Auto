// default package
// Generated 2015-9-15 11:00:59 by Hibernate Tools 3.4.0.CR1
package Com.Entity.order;
/**
 * TFtuTemplate generated by hbm2java
 */
public class FtuTemplate implements java.io.Serializable {

	private String fid;
	private String fuserid;
	private String ftemplate;

	public FtuTemplate() {
	}

	public FtuTemplate(String fid) {
		this.fid = fid;
	}

	public FtuTemplate(String fid, String fuserid, String ftemplate) {
		this.fid = fid;
		this.fuserid = fuserid;
		this.ftemplate = ftemplate;
	}

	public String getFid() {
		return this.fid;
	}

	public void setFid(String fid) {
		this.fid = fid;
	}

	public String getFuserid() {
		return this.fuserid;
	}

	public void setFuserid(String fuserid) {
		this.fuserid = fuserid;
	}

	public String getFtemplate() {
		return this.ftemplate;
	}

	public void setFtemplate(String ftemplate) {
		this.ftemplate = ftemplate;
	}

}
