package com.pc.model;

import cn.org.rapid_framework.util.DateConvertUtils;

/****
 * *
 *<p>Title: CL_Identification</p>
 *<p>Description: 认证对象</p>
 *<p>Company: CPS-TEAM</p> 
 * @author WANGC
 * @date 2015-12-21 下午1:31:10
 */


public class CL_Identification  {
	protected static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm";
	/*** 主键自增*/
	private java.lang.Integer id;
	/*** 企业名称/个人名称*/
	private java.lang.String name;
	/*** 用户角色表主键*/
	private java.lang.Integer userRoleId;
	/*** 类型1:企业认证,2为个人认证*/
	private java.lang.Integer type;
	/*** 状态 0:跳过,1:待审核,2:已驳回,3:通过,4:已关闭*/
	private java.lang.Integer status;
	/*** 创建时间*/
	private java.util.Date createTime;
	/*** 原因*/
	private java.lang.String backReason;
	//审核时间
	private java.util.Date auditTime;
	
	//+++++++++++++++++++++只做前端封装+++++++++++begin+++++++++++++++++++//
	private java.lang.Integer roleId;
	private java.lang.String roleName;
	private java.lang.String fid;
	private java.lang.String fname;
	private java.lang.String vmiUserPhone;//用户手机号
	private java.lang.String carNumber;//车牌号
	private java.lang.String driverCode;//驾驶证号
	private java.lang.String driverName;//司机名
	private java.lang.String specName;//规格名词
	private java.lang.String typeName;//车型名称
	private Integer licenseType;//牌照类型
	private Integer carType;//车型id
	private Integer carSpecId;//车辆规格id
    private java.lang.Integer fluckDriver;//是否好运司机
    private java.lang.String fsaleman;//业务员
    private java.lang.String fsalemanDept;//业务部门
    private java.lang.String fregion;//客户区域
    private java.lang.String activeArea;//车辆主要活动区域
    private java.util.Date registerTime;//客户注册时间
    private java.lang.Integer passIdentify;//是否通过验证
    public java.lang.Integer getPassIdentify() {
		return passIdentify;
	}

	public void setPassIdentify(java.lang.Integer passIdentify) {
		this.passIdentify = passIdentify;
	}

	public static final String FORMAT_REGISTER_TIME = "yyyy-MM-dd";
    
	public java.lang.String getFsaleman() {
		return fsaleman;
	}

	public void setFsaleman(java.lang.String fsaleman) {
		this.fsaleman = fsaleman;
	}

	
	public java.lang.String getActiveArea() {
		return activeArea;
	}

	public void setActiveArea(java.lang.String activeArea) {
		this.activeArea = activeArea;
	}

	public java.lang.String getFsalemanDept() {
		return fsalemanDept;
	}

	public void setFsalemanDept(java.lang.String fsalemanDept) {
		this.fsalemanDept = fsalemanDept;
	}

	public String getRegisterTimeString() {
		return DateConvertUtils.format(getRegisterTime(), FORMAT_REGISTER_TIME);
	}
	public void setRegisterTimeString(String value) {
		setRegisterTime(DateConvertUtils.parse(value, FORMAT_REGISTER_TIME,java.util.Date.class));
	}
	public java.lang.String getFregion() {
		return fregion;
	}

	public void setFregion(java.lang.String fregion) {
		this.fregion = fregion;
	}

	public java.util.Date getRegisterTime() {
		return registerTime;
	}

	public void setRegisterTime(java.util.Date registerTime) {
		this.registerTime = registerTime;
	}

	public java.lang.Integer getFluckDriver() {
		return fluckDriver;
	}

	public void setFluckDriver(java.lang.Integer fluckDriver) {
		this.fluckDriver = fluckDriver;
	}

	public java.lang.Integer getRoleId() {
		return roleId;
	}

	public Integer getCarType() {
		return carType;
	}

	public void setCarType(Integer carType) {
		this.carType = carType;
	}

	
	public Integer getLicenseType() {
		return licenseType;
	}

	public void setLicenseType(Integer licenseType) {
		this.licenseType = licenseType;
	}

	public Integer getCarSpecId() {
		return carSpecId;
	}

	public void setCarSpecId(Integer carSpecId) {
		this.carSpecId = carSpecId;
	}

	public void setRoleId(java.lang.Integer roleId) {
		this.roleId = roleId;
	}

	public java.lang.String getRoleName() {
		return roleName;
	}

	public void setRoleName(java.lang.String roleName) {
		this.roleName = roleName;
	}
	
	public java.lang.String getFid() {
		return fid;
	}

	public void setFid(java.lang.String fid) {
		this.fid = fid;
	}
	
	public java.lang.String getFname() {
		return fname;
	}

	public void setFname(java.lang.String fname) {
		this.fname = fname;
	}

	//+++++++++++++++++++++只做前端封装+++++++++end++++++++++++++++++++++++//
	
	public CL_Identification(){}

	public CL_Identification(java.lang.Integer id){
		this.id = id;
	}

	public void setId(java.lang.Integer value) {
		this.id = value;
	}
	
	public java.lang.Integer getId() {
		return this.id;
	}
	
	public java.lang.String getName() {
		return name;
	}

	public void setName(java.lang.String name) {
		this.name = name;
	}

	public void setUserRoleId(java.lang.Integer value) {
		this.userRoleId = value;
	}
	
	public java.lang.Integer getUserRoleId() {
		return this.userRoleId;
	}
	public void setType(java.lang.Integer value) {
		this.type = value;
	}
	
	public java.lang.Integer getType() {
		return this.type;
	}
	
	public void setStatus(java.lang.Integer value) {
		this.status = value;
	}
	
	public java.lang.Integer getStatus() {
		return this.status;
	}

	public java.util.Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(java.util.Date createTime) {
		this.createTime = createTime;
	}
	
	public String getCreateTimeString() {
		return DateConvertUtils.format(getCreateTime(), DATE_TIME_FORMAT);
	}
	public void setCreateTimeString(String value) {
		setCreateTime(DateConvertUtils.parse(value, DATE_TIME_FORMAT,java.util.Date.class));
	}

	public java.lang.String getBackReason() {
		return backReason;
	}

	public void setBackReason(java.lang.String backReason) {
		this.backReason = backReason;
	}

	public java.util.Date getAuditTime() {
		return auditTime;
	}

	public void setAuditTime(java.util.Date auditTime) {
		this.auditTime = auditTime;
	}
	
	public String getAuditTimeString() {
		return DateConvertUtils.format(getAuditTime(), DATE_TIME_FORMAT);
	}
	public void setAuditTimeString(String value) {
		setAuditTime(DateConvertUtils.parse(value, DATE_TIME_FORMAT,java.util.Date.class));
	}

	public java.lang.String getCarNumber() {
		return carNumber;
	}

	public void setCarNumber(java.lang.String carNumber) {
		this.carNumber = carNumber;
	}

	public java.lang.String getDriverCode() {
		return driverCode;
	}

	public void setDriverCode(java.lang.String driverCode) {
		this.driverCode = driverCode;
	}

	public java.lang.String getDriverName() {
		return driverName;
	}

	public void setDriverName(java.lang.String driverName) {
		this.driverName = driverName;
	}

	public java.lang.String getSpecName() {
		return specName;
	}

	public void setSpecName(java.lang.String specName) {
		this.specName = specName;
	}

	public java.lang.String getTypeName() {
		return typeName;
	}

	public void setTypeName(java.lang.String typeName) {
		this.typeName = typeName;
	}

	public java.lang.String getVmiUserPhone() {
		return vmiUserPhone;
	}

	public void setVmiUserPhone(java.lang.String vmiUserPhone) {
		this.vmiUserPhone = vmiUserPhone;
	}
	
}

