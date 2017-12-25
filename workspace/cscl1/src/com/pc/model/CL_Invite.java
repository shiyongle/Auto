package com.pc.model;

import java.io.Serializable;
import java.util.Calendar;
import org.springframework.format.annotation.DateTimeFormat;
import com.pc.query.aBase.BaseQuery;
import cn.org.rapid_framework.util.DateConvertUtils;

/**
 * @author Cici
 * 转介绍
 */

public class CL_Invite extends BaseQuery implements Serializable {
	private static final long serialVersionUID = 5416100881345885205L;
	
	private String fid;
	private String fuserid;//邀请人
	private String finviteeid;//被邀请人
	private int fuserroleid;//用户角色ID
	private int isReward;//是否发放奖励 0否1是
	private java.util.Date fcreatetime;//创建时间
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private java.util.Date createTimeBegin;
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private java.util.Date createTimeEnd;
	
	public static final String FORMAT_CREATE_TIME = "yyyy-MM-dd HH:mm";

	private String fusername;
	private String finviteename;

	public String getCreateTimeString() {
		return DateConvertUtils.format(getFcreatetime(), FORMAT_CREATE_TIME);
	}
	public void setCreateTimeString(String value) {
		setFcreatetime(DateConvertUtils.parse(value, FORMAT_CREATE_TIME,java.util.Date.class));
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
		this.createTimeEnd = createTimeEnd;
	}
	
	public int getFuserroleid() {
		return fuserroleid;
	}
	public void setFuserroleid(int fuserroleid) {
		this.fuserroleid = fuserroleid;
	}
	public String getFuserid() {
		return fuserid;
	}
	public void setFuserid(String fuserid) {
		this.fuserid = fuserid;
	}

	public String getFid() {
		return fid;
	}
	public void setFid(String fid) {
		this.fid = fid;
	}
	
	public java.util.Date getFcreatetime() {
		return fcreatetime;
	}
	public void setFcreatetime(java.util.Date fcreatetime) {
		this.fcreatetime = fcreatetime;
	}
	public String getFinviteeid() {
		return finviteeid;
	}
	public void setFinviteeid(String finviteeid) {
		this.finviteeid = finviteeid;
	}
	
	
	public String getFinviteename() {
		return finviteename;
	}
	public void setFinviteename(String finviteename) {
		this.finviteename = finviteename;
	}
	public int getIsReward() {
		return isReward;
	}
	public void setIsReward(int isReward) {
		this.isReward = isReward;
	}
	
	public String getFusername() {
		return fusername;
	}
	public void setFusername(String fusername) {
		this.fusername = fusername;
	}
}
