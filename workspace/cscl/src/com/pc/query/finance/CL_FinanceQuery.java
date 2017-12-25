package com.pc.query.finance;

import java.io.Serializable;
import java.util.Calendar;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.springframework.format.annotation.DateTimeFormat;

import com.pc.query.aBase.BaseQuery;

public class CL_FinanceQuery extends BaseQuery implements Serializable {
    private static final long serialVersionUID = 3148176768559230877L;

	/** 自增主键 */
	private java.lang.Integer fid;
	/** 批量选中主键*/
	private Integer[] ids;
	/** 申请司机 */
	private java.lang.Integer fuserId;
	/** 提现单号*/
	private java.lang.String number;
	/** 处理人*/
	private java.lang.String fhandlerId;
	/** 创建申请时间 */
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private java.util.Date createTimeBegin;
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private java.util.Date createTimeEnd; 
	/** 支付时间 */
	private java.util.Date fpaymentTime;
	/** 支付流水号 */
	private java.lang.String fserialNum;
	/** 提现方式:1支付宝，2银行 */
	private java.lang.Integer fwithdrawType;
	/** 银行卡帐号 */
	private java.lang.String fbankAccount;
	/** 支付宝账号 */
	private java.lang.String falipayId;
	/** 开户行 */
	private java.lang.String fbankName;
	/** 开户行所在地 */
	private java.lang.String fbankAddress;
	/** 驳回理由 */
	private java.lang.Integer frejectType;
	/** 状态 */
	private java.lang.Integer fstate;
	
	public java.lang.Integer getFstate() {
		return fstate;
	}
	public void setFstate(java.lang.Integer fstate) {
		this.fstate = fstate;
	}
	private String fusername;
	
	public String getFusername() {
		return fusername;
	}
	public void setFusername(String fusername) {
		this.fusername = fusername;
	}
	
	public java.lang.Integer getFrejectType() {
		return frejectType;
	}
	public void setFrejectType(java.lang.Integer frejectType) {
		this.frejectType = frejectType;
	}
	public java.lang.Integer getFid() {
		return fid;
	}
	public void setFid(java.lang.Integer fid) {
		this.fid = fid;
	}
	public Integer[] getIds() {
		return ids;
	}
	public void setIds(Integer[] ids) {
		this.ids = ids;
	}
	public java.lang.Integer getFuserId() {
		return fuserId;
	}
	public void setFuserId(java.lang.Integer fuserId) {
		this.fuserId = fuserId;
	}

	public java.lang.String getNumber() {
		return number;
	}
	public void setNumber(java.lang.String number) {
		this.number = number;
	}
	public java.lang.String getFhandlerId() {
		return fhandlerId;
	}
	public void setFhandlerId(java.lang.String fhandlerId) {
		this.fhandlerId = fhandlerId;
	}

	public java.util.Date getCreateTimeBegin() {
		return createTimeBegin;
	}
	public void setCreateTimeBegin(java.util.Date createTimeBegin) {
		this.createTimeBegin = createTimeBegin;
	}
	
	public java.util.Date getCreateTimeEnd() {
		return createTimeEnd;
	}
	public void setCreateTimeEnd(java.util.Date createTimeEnd) {
		if(createTimeEnd != null){
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(createTimeEnd);
			calendar.add(Calendar.DAY_OF_MONTH, 1);
			this.createTimeEnd = calendar.getTime();
		}else {
			this.createTimeEnd = createTimeEnd;
		}
	}

	public java.lang.String getFserialNum() {
		return fserialNum;
	}
	public void setFserialNum(java.lang.String fserialNum) {
		this.fserialNum = fserialNum;
	}

	public java.lang.Integer getFwithdrawType() {
		return fwithdrawType;
	}
	public void setFwithdrawType(java.lang.Integer fwithdrawType) {
		this.fwithdrawType = fwithdrawType;
	}

	public java.lang.String getFbankAccount() {
		return fbankAccount;
	}
	public void setFbankAccount(java.lang.String fbankAccount) {
		this.fbankAccount = fbankAccount;
	}

	public java.lang.String getFalipayId() {
		return falipayId;
	}
	public void setFalipayId(java.lang.String falipayId) {
		this.falipayId = falipayId;
	}

	public java.lang.String getFbankName() {
		return fbankName;
	}
	public void setFbankName(java.lang.String fbankName) {
		this.fbankName = fbankName;
	}

	public java.lang.String getFbankAddress() {
		return fbankAddress;
	}
	public void setFbankAddress(java.lang.String fbankAddress) {
		this.fbankAddress = fbankAddress;
	}

	public java.util.Date getFpaymentTime() {
		return fpaymentTime;
	}
	public void setFpaymentTime(java.util.Date fpaymentTime) {
		if(fpaymentTime != null){
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(fpaymentTime);
			calendar.add(Calendar.DAY_OF_MONTH, 1);
			this.fpaymentTime = calendar.getTime();
		}else {
			this.fpaymentTime = fpaymentTime;
		}
	}
	public String toString() {
		return ToStringBuilder.reflectionToString(this,ToStringStyle.MULTI_LINE_STYLE);
	}
	
}

