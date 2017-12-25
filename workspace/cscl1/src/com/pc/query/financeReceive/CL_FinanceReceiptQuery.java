package com.pc.query.financeReceive;

import java.io.Serializable;
import java.util.Calendar;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.springframework.format.annotation.DateTimeFormat;

import com.pc.query.aBase.BaseQuery;

public class CL_FinanceReceiptQuery extends BaseQuery implements Serializable {
	private static final long serialVersionUID = -5950178099789806881L;
	
	/** 自增主键 */
	private java.lang.Integer fid;
	/** 货主 */
	private java.lang.Integer fuserId;
	/** 创建人ID*/
	private java.lang.Integer fcreatorId;
	/** 创建时间 */
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private java.util.Date receivablesTimeBegin;
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private java.util.Date receivablesTimeEnd; 
	/** 金额*/
	private java.math.BigDecimal famount;
	/** 收款方式：0承兑汇票1支票2现金 */
	private java.lang.Integer fpaymentMethod;
	/** 未核销金额 */
	private java.math.BigDecimal fremainAmount;
	private String fusername;
	private String fcreatorname;
	
	public String getFusername() {
		return fusername;
	}
	public void setFusername(String fusername) {
		this.fusername = fusername;
	}
	
	public java.lang.Integer getFid() {
		return fid;
	}
	public void setFid(java.lang.Integer fid) {
		this.fid = fid;
	}
	
	public java.lang.Integer getFuserId() {
		return fuserId;
	}
	public void setFuserId(java.lang.Integer fuserId) {
		this.fuserId = fuserId;
	}
	
	
	public java.util.Date getReceivablesTimeBegin() {
		return receivablesTimeBegin;
	}
	public void setReceivablesTimeBegin(java.util.Date receivablesTimeBegin) {
		this.receivablesTimeBegin = receivablesTimeBegin;
	}
	
	public java.util.Date getReceivablesTimeEnd() {
		return receivablesTimeEnd;
	}
	public void setReceivablesTimeEnd(java.util.Date receivablesTimeEnd) {
		this.receivablesTimeEnd = receivablesTimeEnd;
		if(receivablesTimeEnd != null){
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(receivablesTimeEnd);
			calendar.add(Calendar.DAY_OF_MONTH, 1);
			this.receivablesTimeEnd = calendar.getTime();
		}else {
			this.receivablesTimeEnd = receivablesTimeEnd;
		}
	}
	
	public java.lang.Integer getFcreatorId() {
		return fcreatorId;
	}
	public void setFcreatorId(java.lang.Integer fcreatorId) {
		this.fcreatorId = fcreatorId;
	}
	
	public java.math.BigDecimal getFamount() {
		return famount;
	}
	public void setFamount(java.math.BigDecimal famount) {
		this.famount = famount;
	}
	
	public java.lang.Integer getFpaymentMethod() {
		return fpaymentMethod;
	}
	public void setFpaymentMethod(java.lang.Integer fpaymentMethod) {
		this.fpaymentMethod = fpaymentMethod;
	}
	
	public java.math.BigDecimal getFremainAmount() {
		return fremainAmount;
	}
	public void setFremainAmount(java.math.BigDecimal fremainAmount) {
		this.fremainAmount = fremainAmount;
	}
	
	public String getFcreatorname() {
		return fcreatorname;
	}
	public void setFcreatorname(String fcreatorname) {
		this.fcreatorname = fcreatorname;
	}
	
	public String toString() {
		return ToStringBuilder.reflectionToString(this,ToStringStyle.MULTI_LINE_STYLE);
	}
	
}

