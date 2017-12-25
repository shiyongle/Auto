package com.pc.query.abnormity;

import java.io.Serializable;

import com.pc.query.aBase.BaseQuery;

public class abnormityQuery extends BaseQuery implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7081656517381913156L;

	private java.lang.Integer fid;//主键ID
	private java.util.Date fcreatTime;//创建时间
	private java.lang.Integer forderId;//订单Id
    private java.lang.Integer ftakeproblem;//// 发货人问题 1.货品不符 2.少件 3.质量问题
    private java.lang.Integer frecproblem;//// 发货人问题 1.货品不符 2.少件 3.质量问题
    private java.lang.Integer fcarproblem;//司机问题 1.晚点 2.破损 3.丢失 4.服务
    private java.lang.Integer fuploadId;// //上传 表的id  上传mode cl_abnormity
	private java.lang.String fremark; //备注
	private java.lang.String searchKey;//关键字
	public java.lang.Integer getFid() {
		return fid;
	}
	public void setFid(java.lang.Integer fid) {
		this.fid = fid;
	}
	public java.util.Date getFcreatTime() {
		return fcreatTime;
	}
	public void setFcreatTime(java.util.Date fcreatTime) {
		this.fcreatTime = fcreatTime;
	}
	public java.lang.Integer getForderId() {
		return forderId;
	}
	public void setForderId(java.lang.Integer forderId) {
		this.forderId = forderId;
	}
	public java.lang.Integer getFtakeproblem() {
		return ftakeproblem;
	}
	public void setFtakeproblem(java.lang.Integer ftakeproblem) {
		this.ftakeproblem = ftakeproblem;
	}
	public java.lang.Integer getFrecproblem() {
		return frecproblem;
	}
	public void setFrecproblem(java.lang.Integer frecproblem) {
		this.frecproblem = frecproblem;
	}
	public java.lang.Integer getFcarproblem() {
		return fcarproblem;
	}
	public void setFcarproblem(java.lang.Integer fcarproblem) {
		this.fcarproblem = fcarproblem;
	}
	public java.lang.Integer getFuploadId() {
		return fuploadId;
	}
	public void setFuploadId(java.lang.Integer fuploadId) {
		this.fuploadId = fuploadId;
	}
	public java.lang.String getFremark() {
		return fremark;
	}
	public void setFremark(java.lang.String fremark) {
		this.fremark = fremark;
	}
	public java.lang.String getSearchKey() {
		return searchKey;
	}
	public void setSearchKey(java.lang.String searchKey) {
		this.searchKey = searchKey;
	}
}