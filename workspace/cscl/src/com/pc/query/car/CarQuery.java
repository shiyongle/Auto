package com.pc.query.car;

import java.io.Serializable;

import com.pc.query.aBase.BaseQuery;

public class CarQuery extends BaseQuery implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7081656517381913156L;
	private int id;// 主键
	private Integer[] ids;//批量选中主键
	private int carNum;//车牌号
	private String userName;//注册用户
	private String driverName;//司机名字
	private String carFtel;//司机电话
	private Integer carType;//车辆类型
	private String driverCode;//驾驶证编码
	private int userRoleId;//用户表Id
	private String roleName;//用户类型
	private Integer carSpecId;//车辆规格
	private Integer licenseType;//牌照类型(0:黄牌,1:蓝牌-市,2:蓝牌-县)
	private String weight;//核载
	private Integer fprovince_id;//省
	private Integer fcity_id;//市
	private Integer activeArea;//主要活动区域
	private java.lang.Integer fluckDriver;//好运司机 0 否 1 是
	private java.lang.String searchKey;
	private java.lang.Integer keyType;
	
	public Integer getFprovince() {
		return fprovince_id;
	}
	public void setFprovince(Integer fprovince) {
		this.fprovince_id = fprovince;
	}
	public Integer getFcity() {
		return fcity_id;
	}
	public void setFcity(Integer fcity) {
		this.fcity_id = fcity;
	}
	public int getId() {
		return id;
	}
	public java.lang.Integer getFluckDriver() {
		return fluckDriver;
	}
	public void setFluckDriver(java.lang.Integer fluckDriver) {
		this.fluckDriver = fluckDriver;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getCarNum() {
		return carNum;
	}
	public void setCarNum(int carNum) {
		this.carNum = carNum;
	}
	public String getDriverName() {
		return driverName;
	}
	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}
	public Integer getCarType() {
		return carType;
	}
	public void setCarType(Integer carType) {
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
	public java.lang.String getSearchKey() {
		return searchKey;
	}
	public void setSearchKey(java.lang.String searchKey) {
		this.searchKey = searchKey;
	}
	public java.lang.Integer getKeyType() {
		return keyType;
	}
	public void setKeyType(java.lang.Integer keyType) {
		this.keyType = keyType;
	}
	public Integer getCarSpecId() {
		return carSpecId;
	}
	public void setCarSpecId(Integer carSpecId) {
		this.carSpecId = carSpecId;
	}
	public Integer getLicenseType() {
		return licenseType;
	}
	public void setLicenseType(Integer licenseType) {
		this.licenseType = licenseType;
	}
	public Integer[] getIds() {
		return ids;
	}
	public void setIds(Integer[] ids) {
		this.ids = ids;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getCarFtel() {
		return carFtel;
	}
	public void setCarFtel(String carFtel) {
		this.carFtel = carFtel;
	}
	public String getWeight() {
		return weight;
	}
	public void setWeight(String weight) {
		this.weight = weight;
	}
	public Integer getActiveArea() {
		return activeArea;
	}
	public void setActiveArea(Integer activeArea) {
		this.activeArea = activeArea;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	
	
}
