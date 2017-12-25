package com.pc.query.order;

import java.io.Serializable;
import java.util.Calendar;

import org.springframework.format.annotation.DateTimeFormat;

import com.pc.query.aBase.BaseQuery;

/*** 订单查询对象*/
public class OrderQuery extends BaseQuery implements Serializable{

	/***
	 *<p>Description: </p>
	 *<p>Company: CPS-TEAM</p> 
	 * @author WANGC
	 * @date 2016-1-25 下午2:37:13
	*/
	private static final long serialVersionUID = 3019571844509462031L;
	
	/** 主键 */
	private java.lang.Integer id;
	
	/** 批量选中主键*/
	private Integer[] ids;
	
	/** 订单号 */
	private java.lang.String number;
	/** 订单类型（1，整车 2，零担） */
	private java.lang.Integer type;
	/** 订单状态 */
	private java.lang.Integer status;
	/** 货物类型Id */
	private java.lang.String goodsTypeId;
	/** 货物类型 */
	private java.lang.String goodsTypeName;
	/** 重量（吨） */
	private Long weight;
	/** 体积（立方米） */
	private Long volume;
	/** 长度（米） */
	private Long length;
	/** 装车时间 */
	@DateTimeFormat(pattern="yyyy-MM-dd HH")
	private java.util.Date loadedTimeBegin;
	@DateTimeFormat(pattern="yyyy-MM-dd HH")
	private java.util.Date loadedTimeEnd;
	/** 创建人 */
	private java.lang.Integer creator;
	/** 创建时间 */
	private java.util.Date createTimeBegin;
	private java.util.Date createTimeEnd;
	/** 里程 */
	private Long mileage;
	/** 运费 */
	private Long freight;
	/**用户角色表的ID*/
	private java.lang.Integer userRoleId;
	/**卸货*/
	private java.lang.Integer upload;
	/**回单*/
	private java.lang.Integer receipt;
	/**代收货款*/
	private java.lang.Integer collection;
	/***是否常用订单-0为否1为是*/
	private java.lang.Integer  isCommon;
    /** 关键字*/
	private java.lang.String searchKey;
	private java.lang.String mySearchKey;
	/** 司机*/
	private Integer[] drivers;
	private int fdelOrder;//删除字段
    private Integer frating;//车主是否评价
    private Integer fonlinePay;//0线上支付 1线下支付
    private Integer fpayMethod;//4、运费到付
    private Integer subUserId;//子帐号ID
    private java.util.Date foperate_time;//调度的操作时间
    private Integer carType;
    private Integer carSpecId;//规格
	private Integer carTypeId;//车型
	
    
    
	public Integer getCarSpecId() {
		return carSpecId;
	}

	public void setCarSpecId(Integer carSpecId) {
		this.carSpecId = carSpecId;
	}

	public Integer getCarTypeId() {
		return carTypeId;
	}

	public void setCarTypeId(Integer carTypeId) {
		this.carTypeId = carTypeId;
	}

	public Integer getCarType() {
		return carType;
	}

	public void setCarType(Integer carType) {
		this.carType = carType;
	}

	public java.util.Date getFoperate_time() {
		return foperate_time;
	}

	public void setFoperate_time(java.util.Date foperate_time) {
		this.foperate_time = foperate_time;
	}

	public java.lang.String getMySearchKey() {
		return mySearchKey;
	}

	public void setMySearchKey(java.lang.String mySearchKey) {
		this.mySearchKey = mySearchKey;
	}

	public Integer getFpayMethod() {
		return fpayMethod;
	}

	public void setFpayMethod(Integer fpayMethod) {
		this.fpayMethod = fpayMethod;
	}

	public Integer getFonlinePay() {
		return fonlinePay;
	}

	public void setFonlinePay(Integer fonlinePay) {
		this.fonlinePay = fonlinePay;
	}

	public Integer getFrating() {
		return frating;
	}

	public void setFrating(Integer frating) {
		this.frating = frating;
	}

	public int getFdelOrder() {
		return fdelOrder;
	}

	public void setFdelOrder(int fdelOrder) {
		this.fdelOrder = fdelOrder;
	}

	private java.lang.Integer  huoLoadOrder;//货主查询状态条件
 

	public java.lang.Integer getHuoLoadOrder() {
		return huoLoadOrder;
	}

	public void setHuoLoadOrder(java.lang.Integer huoLoadOrder) {
		this.huoLoadOrder = huoLoadOrder;
	}

	private java.lang.Integer fopint;
	
	public java.lang.Integer getFopint() {
		return fopint;
	}

	public void setFopint(java.lang.Integer fopint) {
		this.fopint = fopint;
	}

	/**非数据库字段*/
	private java.lang.String keyword;
	private java.lang.String OrderDriverName;//订单司机名字
	private java.lang.String OrderDriverphone;//订单司机电话
	private java.lang.Integer isVehicleSpot;//是否整车点对点：0否，1是
	private java.math.BigDecimal purposeAmount;//代收金额
	private java.lang.Integer isFreightCollect;//是否运费到付：0否，1是
	private java.lang.Integer DrloadCar;// 查司机的运单状态   1:进行中  (3) 2:已完成(4,5) 3:异常(6,7)	
	private java.lang.String fusername;
	private java.lang.String OrderDriverCarNumber;
	private java.lang.String phone;
	private java.lang.String customer;
	

	public java.lang.String getOrderDriverCarNumber() {
		return OrderDriverCarNumber;
	}

	public void setOrderDriverCarNumber(java.lang.String orderDriverCarNumber) {
		OrderDriverCarNumber = orderDriverCarNumber;
	}

	public java.lang.String getPhone() {
		return phone;
	}

	public void setPhone(java.lang.String phone) {
		this.phone = phone;
	}

	public java.lang.String getCustomer() {
		return customer;
	}

	public void setCustomer(java.lang.String customer) {
		this.customer = customer;
	}

	public java.lang.String getFusername() {
		return fusername;
	}
	public void setFusername(java.lang.String fusername) {
		this.fusername = fusername;
	}

	public java.lang.Integer getDrloadCar() {
		return DrloadCar;
	}

	public void setDrloadCar(java.lang.Integer drloadCar) {
		DrloadCar = drloadCar;
	}

	public java.lang.String getOrderDriverName() {
		return OrderDriverName;
	}

	public void setOrderDriverName(java.lang.String orderDriverName) {
		OrderDriverName = orderDriverName;
	}

	public java.lang.String getOrderDriverphone() {
		return OrderDriverphone;
	}

	public void setOrderDriverphone(java.lang.String orderDriverphone) {
		OrderDriverphone = orderDriverphone;
	}

	public java.lang.String getKeyword() {
		return keyword;
	}

	public void setKeyword(java.lang.String keyword) {
		this.keyword = keyword;
	}
	public Integer[] getDrivers() {
		return drivers;
	}

	public void setDrivers(Integer[] drivers) {
		this.drivers = drivers;
	}

	public java.lang.String getSearchKey() {
		return searchKey;
	}

	public void setSearchKey(java.lang.String searchKey) {
		this.searchKey = searchKey;
	}

	public java.lang.Integer getUserRoleId() {
		return userRoleId;
	}

	public void setUserRoleId(java.lang.Integer userRoleId) {
		this.userRoleId = userRoleId;
	}

	public java.lang.Integer getUnload() {
		return upload;
	}

	public void setUnload(java.lang.Integer upload) {
		this.upload = upload;
	}

	public java.lang.Integer getReceipt() {
		return receipt;
	}

	public void setReceipt(java.lang.Integer receipt) {
		this.receipt = receipt;
	}

	public java.lang.Integer getCollection() {
		return collection;
	}

	public void setCollection(java.lang.Integer collection) {
		this.collection = collection;
	}


	public java.lang.Integer getId() {
		return this.id;
	}
	
	public void setId(java.lang.Integer value) {
		this.id = value;
	}
	
	public java.lang.String getNumber() {
		return this.number;
	}
	
	public void setNumber(java.lang.String value) {
		this.number = value;
	}
	
	public java.lang.Integer getType() {
		return this.type;
	}
	
	public void setType(java.lang.Integer value) {
		this.type = value;
	}
	
	public java.lang.Integer getStatus() {
		return this.status;
	}
	
	public void setStatus(java.lang.Integer value) {
		this.status = value;
	}
	
	public java.lang.String getGoodsTypeId() {
		return this.goodsTypeId;
	}
	
	public void setGoodsTypeId(java.lang.String value) {
		this.goodsTypeId = value;
	}
	
	public java.lang.String getGoodsTypeName() {
		return this.goodsTypeName;
	}
	
	public void setGoodsTypeName(java.lang.String value) {
		this.goodsTypeName = value;
	}
	
	public Long getWeight() {
		return this.weight;
	}
	
	public void setWeight(Long value) {
		this.weight = value;
	}
	
	public Long getVolume() {
		return this.volume;
	}
	
	public void setVolume(Long value) {
		this.volume = value;
	}
	
	public Long getLength() {
		return this.length;
	}
	
	public void setLength(Long value) {
		this.length = value;
	}
	
	public java.util.Date getLoadedTimeBegin() {
		return this.loadedTimeBegin;
	}
	
	public void setLoadedTimeBegin(java.util.Date value) {
		this.loadedTimeBegin = value;
	}	
	
	public java.util.Date getLoadedTimeEnd() {
		return this.loadedTimeEnd;
	}
	
	public void setLoadedTimeEnd(java.util.Date value) {
		if(value != null){
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(value);
//			calendar.add(Calendar.DAY_OF_MONTH, 1);
			this.loadedTimeEnd = calendar.getTime();
		}else {
			this.loadedTimeEnd = value;
		}
	}
	
	public java.lang.Integer getCreator() {
		return this.creator;
	}
	
	public void setCreator(java.lang.Integer value) {
		this.creator = value;
	}
	
	public java.util.Date getCreateTimeBegin() {
		return this.createTimeBegin;
	}
	
	public void setCreateTimeBegin(java.util.Date value) {
		this.createTimeBegin = value;
	}	
	
	public java.util.Date getCreateTimeEnd() {
		return this.createTimeEnd;
	}
	
	public void setCreateTimeEnd(java.util.Date value) {
		if(value != null){
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(value);
			calendar.add(Calendar.DAY_OF_MONTH, 1);
			this.createTimeEnd = calendar.getTime();
		}else {
			this.createTimeEnd = value;
		}
	}
	
	public Long getMileage() {
		return this.mileage;
	}
	
	public void setMileage(Long value) {
		this.mileage = value;
	}
	
	public Long getFreight() {
		return this.freight;
	}
	
	public void setFreight(Long value) {
		this.freight = value;
	}

	public java.lang.Integer getIsCommon() {
		return isCommon;
	}

	public void setIsCommon(java.lang.Integer isCommon) {
		this.isCommon = isCommon;
	}

	public Integer[] getIds() {
		return ids;
	}

	public void setIds(Integer[] ids) {
		this.ids = ids;
	}

	public java.lang.Integer getUpload() {
		return upload;
	}

	public void setUpload(java.lang.Integer upload) {
		this.upload = upload;
	}

	public java.lang.Integer getIsVehicleSpot() {
		return isVehicleSpot;
	}

	public void setIsVehicleSpot(java.lang.Integer isVehicleSpot) {
		this.isVehicleSpot = isVehicleSpot;
	}

	public java.math.BigDecimal getPurposeAmount() {
		return purposeAmount;
	}

	public void setPurposeAmount(java.math.BigDecimal purposeAmount) {
		this.purposeAmount = purposeAmount;
	}

	public java.lang.Integer getIsFreightCollect() {
		return isFreightCollect;
	}

	public void setIsFreightCollect(java.lang.Integer isFreightCollect) {
		this.isFreightCollect = isFreightCollect;
	}

	public Integer getSubUserId() {
		return subUserId;
	}

	public void setSubUserId(Integer subUserId) {
		this.subUserId = subUserId;
	}
	
	
}
