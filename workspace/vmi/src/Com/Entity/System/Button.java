package Com.Entity.System;

// default package
// Generated 2013-4-27 10:37:44 by Hibernate Tools 3.4.0.CR1

/**
 * TSysButton generated by hbm2java
 */
public class Button implements java.io.Serializable {

	private String fid;
	private String fname;
	private String fbuttonid;
	private String fbuttonaction;
	private String fparentid;
	private String fpath;

	public String getFpath() {
		return fpath;
	}

	public void setFpath(String fpath) {
		this.fpath = fpath;
	}

	public Button() {
	}

	public Button(String fid, String fname, String fbuttonid,
			String fbuttonaction, String fparentid) {
		this.fid = fid;
		this.fname = fname;
		this.fbuttonid = fbuttonid;
		this.fbuttonaction = fbuttonaction;
		this.fparentid = fparentid;
	}

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

	public String getFbuttonid() {
		return this.fbuttonid;
	}

	public void setFbuttonid(String fbuttonid) {
		this.fbuttonid = fbuttonid;
	}

	public String getFbuttonaction() {
		return this.fbuttonaction;
	}

	public void setFbuttonaction(String fbuttonaction) {
		this.fbuttonaction = fbuttonaction;
	}

	public String getFparentid() {
		return this.fparentid;
	}

	public void setFparentid(String fparentid) {
		this.fparentid = fparentid;
	}

}