package com.pc.model;

import java.math.BigDecimal;
import java.util.Date;

import cn.org.rapid_framework.util.DateConvertUtils;

public class CL_CouponsActivity{
	
	public static final String FORMAT_TIME = "yyyy-MM-dd HH:mm";
	public static final String FORMAT_TIME1 = "yyyy-MM-dd HH:mm:ss";
	
	private Integer fid;
	private String fbusinessType;//业务类型（暂时默认一路好运）
	private Integer fcouponsId;//优惠券模板id
	private Integer factivityType;//活动类型：0指派（发放人群） 1充值送   2送给充值客户的（客户充值之后建立一个只对某一客户有效的优惠活动）
	private BigDecimal ftopUpDollars;//充值的金额
	private Integer feffective;//活动有效与否 1有效  2待生效  3过期
	private Date factivityStartTime;//活动开始时间
	private Date factivityEndTime;//活动结束时间
	private String fissueUser;//发放人群（暂定用户手机）
	private String fexcelName;//excel文件名
	private Integer fgetType;//领取类型  1：领取后次日生效    2：领取后即日生效
	private Date fuseStartTime;//使用有效期开始时间
	private Date fuseEndTime;//使用有效期结束时间
	private Integer fuseTime;//有效期（天）
	private BigDecimal fdollars;//满足金额条件
	private String fcarSpecId;//满足车型条件
	private String farea;//区域（提货）
	private Date createTime;
	private Integer creator;
	private Date operateTime;
	private Integer operator;
	
	
	private String fcouponsName;//优惠券名字
	private Integer fcouponsType;//优惠券类型  1：订单优惠券  2：增值服务券
	private BigDecimal fdiscount;//订单优惠券的折扣
	private BigDecimal fsubtract;//订单优惠券的直减
	private String faddserviceName;//增值服务券对应的增值服务项目
	private Integer isReceive;//0未领取,1：已领取
	
	
	public String getFexcelName() {
		return fexcelName;
	}

	public void setFexcelName(String fexcelName) {
		this.fexcelName = fexcelName;
	}

	public Integer getIsReceive() {
		return isReceive;
	}

	public void setIsReceive(Integer isReceive) {
		this.isReceive = isReceive;
	}
	
	public Integer getFcouponsType() {
		return fcouponsType;
	}
	public void setFcouponsType(Integer fcouponsType) {
		this.fcouponsType = fcouponsType;
	}
	public BigDecimal getFdiscount() {
		return fdiscount;
	}
	public void setFdiscount(BigDecimal fdiscount) {
		this.fdiscount = fdiscount;
	}
	public BigDecimal getFsubtract() {
		return fsubtract;
	}
	public void setFsubtract(BigDecimal fsubtract) {
		this.fsubtract = fsubtract;
	}
	public String getFaddserviceName() {
		return faddserviceName;
	}
	public void setFaddserviceName(String faddserviceName) {
		this.faddserviceName = faddserviceName;
	}
	public String getFcouponsName() {
		return fcouponsName;
	}
	public void setFcouponsName(String fcouponsName) {
		this.fcouponsName = fcouponsName;
	}
	
	
	
	public Integer getFid() {
		return fid;
	}
	public void setFid(Integer fid) {
		this.fid = fid;
	}
	public String getFbusinessType() {
		return fbusinessType;
	}
	public void setFbusinessType(String fbusinessType) {
		this.fbusinessType = fbusinessType;
	}
	public Integer getFactivityType() {
		return factivityType;
	}
	public void setFactivityType(Integer factivityType) {
		this.factivityType = factivityType;
	}

	public BigDecimal getFtopUpDollars() {
		return ftopUpDollars;
	}

	public void setFtopUpDollars(BigDecimal ftopUpDollars) {
		this.ftopUpDollars = ftopUpDollars;
	}

	public Integer getFcouponsId() {
		return fcouponsId;
	}
	public void setFcouponsId(Integer fcouponsId) {
		this.fcouponsId = fcouponsId;
	}
	
	public Integer getFeffective() {
		return feffective;
	}
	public void setFeffective(Integer feffective) {
		this.feffective = feffective;
	}
	public Date getFactivityStartTime() {
		return factivityStartTime;
	}
	
	public void setFactivityStartTime(Date factivityStartTime) {
		this.factivityStartTime = factivityStartTime;
	}
	
	public String getFactivityStartTimeString(){
		return DateConvertUtils.format(getFactivityStartTime(), FORMAT_TIME);
	}
	
	public void setFactivityStartTimeString(String value){
		setFactivityStartTime(DateConvertUtils.parse(value, FORMAT_TIME, java.util.Date.class));
	}
	
	public Date getFactivityEndTime() {
		return factivityEndTime;
	}
	public void setFactivityEndTime(Date factivityEndTime) {
		this.factivityEndTime = factivityEndTime;
	}
	
	public String getFactivityEndTimeString(){
		return DateConvertUtils.format(getFactivityEndTime(), FORMAT_TIME);
	}
	
	public void setFactivityEndTimeString(String value){
		setFactivityEndTime(DateConvertUtils.parse(value, FORMAT_TIME, java.util.Date.class));
	}
	
	public String getFissueUser() {
		return fissueUser;
	}
	public void setFissueUser(String fissueUser) {
		this.fissueUser = fissueUser;
	}
	public Integer getFgetType() {
		return fgetType;
	}
	public void setFgetType(Integer fgetType) {
		this.fgetType = fgetType;
	}
	public Date getFuseStartTime() {
		return fuseStartTime;
	}
	public void setFuseStartTime(Date fuseStartTime) {
		this.fuseStartTime = fuseStartTime;
	}
	
	public String getFuseStartTimeString(){
		return DateConvertUtils.format(getFuseStartTime(), FORMAT_TIME);
	}
	
	public void setFuseStartTimeString(String value){
		setFuseStartTime(DateConvertUtils.parse(value, FORMAT_TIME, java.util.Date.class));
	}
	
	public Date getFuseEndTime() {
		return fuseEndTime;
	}
	public void setFuseEndTime(Date fuseEndTime) {
		this.fuseEndTime = fuseEndTime;
	}
	
	public String getFuseEndTimeString(){
		return DateConvertUtils.format(getFuseEndTime(), FORMAT_TIME);
	}
	
	public void setFuseEndTimeString(String value){
		setFuseEndTime(DateConvertUtils.parse(value, FORMAT_TIME, java.util.Date.class));
	}
	
	public Integer getFuseTime() {
		return fuseTime;
	}
	public void setFuseTime(Integer fuseTime) {
		this.fuseTime = fuseTime;
	}
	public BigDecimal getFdollars() {
		return fdollars;
	}
	public void setFdollars(BigDecimal fdollars) {
		this.fdollars = fdollars;
	}
	public String getFcarSpecId() {
		return fcarSpecId;
	}
	public void setFcarSpecId(String fcarSpecId) {
		this.fcarSpecId = fcarSpecId;
	}
	public String getFarea() {
		return farea;
	}
	public void setFarea(String farea) {
		this.farea = farea;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Integer getCreator() {
		return creator;
	}
	public void setCreator(Integer creator) {
		this.creator = creator;
	}
	public Date getOperateTime() {
		return operateTime;
	}
	public void setOperateTime(Date operateTime) {
		this.operateTime = operateTime;
	}
	
	public String getOperateTimeString(){
		return DateConvertUtils.format(getOperateTime(), FORMAT_TIME1);
	}
	
	public void setOperateTimeString(String value){
		setOperateTime(DateConvertUtils.parse(value, FORMAT_TIME1, java.util.Date.class));
	}
	
	public Integer getOperator() {
		return operator;
	}
	public void setOperator(Integer operator) {
		this.operator = operator;
	}
	
}
