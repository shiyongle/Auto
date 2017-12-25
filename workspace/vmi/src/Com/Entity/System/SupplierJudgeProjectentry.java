package Com.Entity.System;
// default package
// Generated 2014-4-22 14:02:28 by Hibernate Tools 3.4.0.CR1

/**
 * TSysSupplierassessmentresultentry generated by hbm2java
 */
public class SupplierJudgeProjectentry implements java.io.Serializable {

	private String fid;
	private Double fhighLimit;
	private Double flowLimit;
	private Double fscore;
	private String fparentId;

	public SupplierJudgeProjectentry() {
	}

	public SupplierJudgeProjectentry(String fid) {
		this.fid = fid;
	}

	public SupplierJudgeProjectentry(String fid, Double fhighLimit,
			Double flowLimit, Double fscore, String fparentId) {
		this.fid = fid;
		this.fhighLimit = fhighLimit;
		this.flowLimit = flowLimit;
		this.fscore = fscore;
		this.fparentId = fparentId;
	}

	public String getFid() {
		return this.fid;
	}

	public void setFid(String fid) {
		this.fid = fid;
	}

	

	public Double getFhighLimit() {
		return fhighLimit;
	}

	public void setFhighLimit(Double fhighLimit) {
		this.fhighLimit = fhighLimit;
	}

	public Double getFlowLimit() {
		return this.flowLimit;
	}

	public void setFlowLimit(Double flowLimit) {
		this.flowLimit = flowLimit;
	}

	public Double getFscore() {
		return this.fscore;
	}

	public void setFscore(Double fscore) {
		this.fscore = fscore;
	}

	public String getFparentId() {
		return this.fparentId;
	}

	public void setFparentId(String fparentId) {
		this.fparentId = fparentId;
	}

}