package com.pc.query.carLine;

import java.io.Serializable;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.pc.query.aBase.BaseQuery;

public class CarLineQuery extends BaseQuery implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer fcar_line_id;// 主键
	private Integer fdriver_id;// 司机id关联
	private Integer factory_id;// 工厂
	private Integer fstatus;// 状态：1排队中，2已取消，3已配货
	private Date fsign_time;// 签到时间
	private Date foperate_time;// 操作时间
	private Integer foperator;// 操作人
	private String fremark;// 备注

	/**前端显示*/
	private Integer sort;// 名次
	private Integer activeArea;// 所属区域
	private Integer carSpec;// 车辆规格
	private String carNum;//车牌号
	private String driverName;//司机名字
	private Integer carType;//车辆类型
	private String vmiUserPhone;//联系电话
	private String factory;//签到工厂
	private String keyWord;//关键字
	@DateTimeFormat(pattern="yyyy-MM-dd HH")
	private Date operatedTimeBegin;
	@DateTimeFormat(pattern="yyyy-MM-dd HH")
	private Date operatedTimeEnd;
	
	
	public Date getOperatedTimeBegin() {
		return operatedTimeBegin;
	}
	public void setOperatedTimeBegin(Date operatedTimeBegin) {
		this.operatedTimeBegin = operatedTimeBegin;
	}
	public Date getOperatedTimeEnd() {
		return operatedTimeEnd;
	}
	public void setOperatedTimeEnd(Date operatedTimeEnd) {
		this.operatedTimeEnd = operatedTimeEnd;
	}
	public String getKeyWord() {
		return keyWord;
	}
	public void setKeyWord(String keyWord) {
		this.keyWord = keyWord;
	}
	public Integer getFcar_line_id() {
		return fcar_line_id;
	}
	public void setFcar_line_id(Integer fcar_line_id) {
		this.fcar_line_id = fcar_line_id;
	}
	public Integer getFdriver_id() {
		return fdriver_id;
	}
	public void setFdriver_id(Integer fdriver_id) {
		this.fdriver_id = fdriver_id;
	}
	public Integer getFactory_id() {
		return factory_id;
	}
	public void setFactory_id(Integer factory_id) {
		this.factory_id = factory_id;
	}
	public Integer getFstatus() {
		return fstatus;
	}
	public void setFstatus(Integer fstatus) {
		this.fstatus = fstatus;
	}
	public Date getFsign_time() {
		return fsign_time;
	}
	public void setFsign_time(Date fsign_time) {
		this.fsign_time = fsign_time;
	}
	public Date getFoperate_time() {
		return foperate_time;
	}
	public void setFoperate_time(Date foperate_time) {
		this.foperate_time = foperate_time;
	}
	public Integer getFoperator() {
		return foperator;
	}
	public void setFoperator(Integer foperator) {
		this.foperator = foperator;
	}
	public String getFremark() {
		return fremark;
	}
	public void setFremark(String fremark) {
		this.fremark = fremark;
	}
	public Integer getSort() {
		return sort;
	}
	public void setSort(Integer sort) {
		this.sort = sort;
	}
	public Integer getActiveArea() {
		return activeArea;
	}
	public void setActiveArea(Integer activeArea) {
		this.activeArea = activeArea;
	}
	public Integer getCarSpec() {
		return carSpec;
	}
	public void setCarSpec(Integer carSpec) {
		this.carSpec = carSpec;
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
	public Integer getCarType() {
		return carType;
	}
	public void setCarType(Integer carType) {
		this.carType = carType;
	}
	public String getVmiUserPhone() {
		return vmiUserPhone;
	}
	public void setVmiUserPhone(String vmiUserPhone) {
		this.vmiUserPhone = vmiUserPhone;
	}
	public String getFactory() {
		return factory;
	}
	public void setFactory(String factory) {
		this.factory = factory;
	}
	

}
