package com.pc.query.financeVerify;

import java.io.Serializable;
import java.util.Calendar;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.springframework.format.annotation.DateTimeFormat;

import com.pc.query.aBase.BaseQuery;

/**
 * @author Cici
 *
 */
public class CL_FinanceVerifyQuery extends BaseQuery implements Serializable {
	private static final long serialVersionUID = -4546178927534631064L;
	
	/** 自增主键 */
	private java.lang.Integer fid;
	/** 创建时间 */
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private java.util.Date verifyTimeBegin;
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private java.util.Date verifyTimeEnd; 

	public java.lang.Integer getFid() {
		return fid;
	}
	public void setFid(java.lang.Integer fid) {
		this.fid = fid;
	}
	
	public java.util.Date getVerifyTimeBegin() {
		return verifyTimeBegin;
	}
	public void setVerifyTimeBegin(java.util.Date verifyTimeBegin) {
		this.verifyTimeBegin = verifyTimeBegin;
	}
	
	public java.util.Date getVerifyTimeEnd() {
		return verifyTimeEnd;
	}
	public void setVerifyTimeEnd(java.util.Date verifyTimeEnd) {
		this.verifyTimeEnd = verifyTimeEnd;
		if(verifyTimeEnd != null){
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(verifyTimeEnd);
			calendar.add(Calendar.DAY_OF_MONTH, 1);
			this.verifyTimeEnd = calendar.getTime();
		}else {
			this.verifyTimeEnd = verifyTimeEnd;
		}
	}
	
	public String toString() {
		return ToStringBuilder.reflectionToString(this,ToStringStyle.MULTI_LINE_STYLE);
	}
	
}

