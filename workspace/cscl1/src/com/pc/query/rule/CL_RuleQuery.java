package com.pc.query.rule;


import java.io.Serializable;

import com.pc.query.aBase.BaseQuery;

public class CL_RuleQuery extends BaseQuery implements Serializable {
    private static final long serialVersionUID = 3148176768559230877L;
    
	/** 自增主键 */
	private java.lang.Integer id;
	/** 1为整车，2为零担 */
	private java.lang.Integer type;
	/** 车辆规格 */
	private java.lang.Integer carSpecId;
	/** 1生效，2失效 */
	private java.lang.Integer status;
	/** 备注 */
	private java.lang.String pubRemark;
	/** 其他类型 1为体积 2为重量 */
	private java.lang.Integer otherType;
	
	private java.lang.Integer   fopint;
	private java.math.BigDecimal outfopint;
	public java.lang.Integer getFopint() {
		return fopint;
	}

	public void setFopint(java.lang.Integer fopint) {
		this.fopint = fopint;
	}

	public java.math.BigDecimal getOutfopint() {
		return outfopint;
	}

	public void setOutfopint(java.math.BigDecimal outfopint) {
		this.outfopint = outfopint;
	}

	public java.lang.Integer getOtherType() {
		return otherType;
	}

	public void setOtherType(java.lang.Integer otherType) {
		this.otherType = otherType;
	}

	public java.lang.Integer getId() {
		return this.id;
	}
	
	public void setId(java.lang.Integer value) {
		this.id = value;
	}
	
	public java.lang.Integer getType() {
		return this.type;
	}
	
	public void setType(java.lang.Integer value) {
		this.type = value;
	}
	
	public java.lang.Integer getCarSpecId() {
		return this.carSpecId;
	}
	
	public void setCarSpecId(java.lang.Integer value) {
		this.carSpecId = value;
	}
	
	public java.lang.Integer getStatus() {
		return this.status;
	}
	
	public void setStatus(java.lang.Integer value) {
		this.status = value;
	}
	
	public java.lang.String getPubRemark() {
		return this.pubRemark;
	}
	
	public void setPubRemark(java.lang.String value) {
		this.pubRemark = value;
	}
	
}

