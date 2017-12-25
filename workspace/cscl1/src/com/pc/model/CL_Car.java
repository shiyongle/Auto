package com.pc.model;

import java.util.Date;

/**
 * 
 * @author twr
 * 
 *
 */
public class CL_Car implements Comparable{
	private int id;// 主键
	private String carNum;//车牌号
	private String driverName;//司机名字
	private int carType;//车辆类型
	private String driverCode;//驾驶证编码
	private int userRoleId;//用户角色表Id
	private int carSpecId;//车辆规格
	private java.math.BigDecimal  longitude;//经度
	private java.math.BigDecimal  latitude;//维度
	private Date fposition_time;//到达经纬度时的时间
	private Integer licenseType;//牌照类型(0:黄牌,1:蓝牌-市,2:蓝牌-县)
	private String weight;//核载
	private Integer fprovince_id;//省
	private Integer fcity_id;//市
	private Integer activeArea;//主要活动区域
	private java.lang.Integer fluckDriver;// 好运司机
	
	/*********************非数据字段,前端封装显示数据*******begin************************/
	private String fprovince;//省
	private String fcity;//市
	private String area;//主要活动区域
	private java.lang.String userName;//用户名称
	private java.lang.String roleName;//角色类型
	private String carTypeName;//车辆类型名称 
	private String carSpecName;//车辆规格名称
	private String carFtel;//司机电话
	private java.math.BigDecimal fstraightStretch;//直线距离
	private java.lang.Integer fisOnline;//是否在线
	private String driverPhone;//司机电话
	
	
	public Date getFposition_time() {
		return fposition_time;
	}
	public void setFposition_time(Date fposition_time) {
		this.fposition_time = fposition_time;
	}
	public String getCarFtel() {
		return carFtel;
	}
	public void setCarFtel(String carFtel) {
		this.carFtel = carFtel;
	}
	public java.lang.String getUserName() {
		return userName;
	}
	public void setUserName(java.lang.String userName) {
		this.userName = userName;
	}
	public java.lang.String getRoleName() {
		return roleName;
	}
	public void setRoleName(java.lang.String roleName) {
		this.roleName = roleName;
	}
	public String getCarTypeName() {
		return carTypeName;
	}
	public void setCarTypeName(String carTypeName) {
		this.carTypeName = carTypeName;
	}
	public String getCarSpecName() {
		return carSpecName;
	}
	public void setCarSpecName(String carSpecName) {
		this.carSpecName = carSpecName;
	}
	public String getWeight() {
		return weight;
	}
	public void setWeight(String weight) {
		this.weight = weight;
	}
	public Integer getLicenseType() {
		return licenseType;
	}
	public void setLicenseType(Integer licenseType) {
		this.licenseType = licenseType;
	}
	public Integer getActiveArea() {
		return activeArea;
	}
	public void setActiveArea(Integer activeArea) {
		this.activeArea = activeArea;
	}
	
	public String getFprovince() {
		return fprovince;
	}
	public void setFprovince(String fprovince) {
		this.fprovince = fprovince;
	}
	public String getFcity() {
		return fcity;
	}
	public void setFcity(String fcity) {
		this.fcity = fcity;
	}
	/*********************非数据字段,前端封装显示数据*******end************************/
	
	public int getId() {
		return id;
	}
	public Integer getFprovince_id() {
		return fprovince_id;
	}
	public void setFprovince_id(Integer fprovince_id) {
		this.fprovince_id = fprovince_id;
	}
	public Integer getFcity_id() {
		return fcity_id;
	}
	public void setFcity_id(Integer fcity_id) {
		this.fcity_id = fcity_id;
	}
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCarNum() {
		return carNum;
	}
	public void setCarNum(String carNum) {
		this.carNum = carNum;
	}
	public String getDriverName() {
		return driverName;
	}
	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}
	public int getCarType() {
		return carType;
	}
	public void setCarType(int carType) {
		this.carType = carType;
	}
	public String getDriverCode() {
		return driverCode;
	}
	public void setDriverCode(String driverCode) {
		this.driverCode = driverCode;
	}
	public int getUserRoleId() {
		return userRoleId;
	}
	public void setUserRoleId(int userRoleId) {
		this.userRoleId = userRoleId;
	}
	public int getCarSpecId() {
		return carSpecId;
	}
	public void setCarSpecId(int carSpecId) {
		this.carSpecId = carSpecId;
	}
	public java.math.BigDecimal getLongitude() {
		return longitude;
	}
	public void setLongitude(java.math.BigDecimal longitude) {
		this.longitude = longitude;
	}
	public java.math.BigDecimal getLatitude() {
		return latitude;
	}
	public void setLatitude(java.math.BigDecimal latitude) {
		this.latitude = latitude;
	}

	public java.math.BigDecimal getFstraightStretch() {
		return fstraightStretch;
	}

	public void setFstraightStretch(java.math.BigDecimal fstraightStretch) {
		this.fstraightStretch = fstraightStretch;
	}

	public java.lang.Integer getFisOnline() {
		return fisOnline;
	}

	public void setFisOnline(java.lang.Integer fisOnline) {
		this.fisOnline = fisOnline;
	}
	@Override
	public int compareTo(Object o) {
		// TODO Auto-generated method stub
		CL_Car car = (CL_Car)o;

		java.math.BigDecimal straightStretch = car.getFstraightStretch();//直线距离
//		java.lang.Integer isOnline = car.getFisOnline();//是否在线
//		if(this.getFisOnline()<isOnline){
//			return 1;
//		}else{
			return this.fstraightStretch.compareTo(straightStretch);
//		}
	}
	public java.lang.Integer getFluckDriver() {
		return fluckDriver;
	}
	public void setFluckDriver(java.lang.Integer fluckDriver) {
		this.fluckDriver = fluckDriver;
	}
	public String getDriverPhone() {
		return driverPhone;
	}
	public void setDriverPhone(String driverPhone) {
		this.driverPhone = driverPhone;
	}
	
}
