package com.pc.model;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import net.sf.json.JSONArray;
import cn.org.rapid_framework.util.DateConvertUtils;


public class CL_Order {

	public static final String TABLE_ALIAS = "订单表";
	public static final String ALIAS_ID = "主键";
	public static final String ALIAS_NUMBER = "订单号";
	public static final String ALIAS_TYPE = "订单类型（1，整车 2，零担）";
	public static final String ALIAS_STATUS = "订单状态";
	public static final String ALIAS_GOODS_TYPE_ID = "货物类型Id";
	public static final String ALIAS_GOODS_TYPE_NAME = "货物类型";
	public static final String ALIAS_WEIGHT = "重量（吨）";
	public static final String ALIAS_VOLUME = "体积（立方米）";
	public static final String ALIAS_LENGTH = "长度（米）";
	public static final String ALIAS_LOADED_TIME = "装车时间";
	public static final String ALIAS_CREATOR = "创建人";
	public static final String ALIAS_CREATE_TIME = "创建时间";
	public static final String ALIAS_MILEAGE = "里程";
	public static final String ALIAS_FREIGHT = "运费";
	public static final String ALIAS_USERROLEID="用户角色表ID";
	public static final String ALIAS_UPLOAD="是否装卸";//增值服务 默认为0 0为否 1为是
	public static final String ALIAS_RECEIPT="是否需要回单";//增值服务 默认为0 0为否 1为是
	public static final String ALIAS_COLLECTION="是否需要代收款";//增值服务 默认为0 0为否 1为是
	public static final String ALIAS_ISCOMMON="是否设为常用";//增值服务 默认为0 0为否 1为是
	public static final String ALIAS_OPERATOR="操作人";
	public static final String ALIAS_PURPOSE_AMOUNT="代收金额";
	public static final String ALIAS_IS_FREIGHT_COLLECT="是否运费到付：0否，1是";
	//date formats
	public static final String FORMAT_LOADED_TIME = "yyyy-MM-dd HH:mm";
	public static final String FORMAT_CREATE_TIME = "yyyy-MM-dd HH:mm:ss";
	public static final String FORMAT_FPAY_TIME="yyyy-MM-dd HH:mm:ss";
	public static final String FORMAT_UPDATE_TIME="yyyy-MM-dd HH:mm:ss";
	public static final String FORMAT_OPERATE_TIME="yyyy-MM-dd HH:mm:ss";
	//columns START
	private java.lang.Integer id;//主键
	private java.lang.String number;//订单号
	private java.lang.Integer type;//订单类型（1，整车 2，零担）
	private java.lang.Integer status;//订单状态
	private java.lang.String goodsTypeId;//货物类型Id
	private java.lang.String goodsTypeName;//货物类型
	private java.math.BigDecimal weight;//重量（吨）
	private java.math.BigDecimal volume;//体积（立方米）
	private java.math.BigDecimal length;//长度（米）
	private java.util.Date loadedTime;//装车时间
	private java.lang.Integer creator;//创建人
	private java.util.Date createTime;//创建时间
	private java.math.BigDecimal mileage;//里程
	private java.math.BigDecimal freight;//运费
	private java.lang.Integer userRoleId;//用户角色表ID
	private java.lang.Integer upload;//是否装卸
	private java.lang.Integer receipt;//是否需要回单
	private java.lang.Integer collection;//是否需要代收款
	private java.lang.Integer isCommon;//是否设为常用
	private java.lang.Integer operator;//操作人
	
	private java.util.Date foperate_time;//调度的操作时间
	
	private Integer couponsId;//订单优惠券
	private java.lang.Integer couponsDetailId;//增值服务优惠券
	private java.math.BigDecimal purposeAmount;//代收金额
	private java.lang.Integer isFreightCollect;//是否运费到付：0否，1是

	private java.lang.Integer protocolType ; //协议用车（0 否1是）
	private java.lang.Integer protocolId ; //协议用车ID
	private java.lang.Integer funitId ; //零担单位id 1:件;2:托;3:面积;4:体积;5:重量
	private java.math.BigDecimal famount ; //数量 
	private java.lang.String fincrementServe ; //增值服务   0 余额1 支付宝2 微信3 银联4 运费到付5 月结
	private BigDecimal serve_1 ; //货主装货服务费
	private BigDecimal serve_2 ; //货主卸货服务费
	private BigDecimal driver_serve_1 ; //司机装货服务费
	private BigDecimal driver_serve_2 ; //司机卸货服务费
	private BigDecimal discount;//折扣和直减的的钱
	private BigDecimal subtract;//增值服务减的钱
	private BigDecimal ftotalFreight;//总运费（更新订单时计算第一个总运费，即没加上追加货款）

	private java.lang.String  payMethod;
	private JSONArray server;
	private java.lang.Integer fopint;//卸货点个数
	private java.util.Date  fpayTime;//支付成功时间
	private java.math.BigDecimal  Chargingrule;//计费规则
	private java.util.Date  fcopTime;//完成时间
	private int fdelOrder;//删除标记//0无效 1：货主删除司机在  2 司机删除货主在 3 两端都删除
	private int fonlinePay;//1线上支付 2线下支付
	private java.math.BigDecimal foriginal;//原价
	private java.math.BigDecimal faddNumber;//追加费用
	private java.math.BigDecimal fdriverfee;
	private java.math.BigDecimal forigin_driverfee;
	private java.lang.String allNumber;
	private java.math.BigDecimal fdriverAllin;//司机总运费，原始费用加上追加费用
	private String custName;//客户名称
	private String custNumber;//客户编码
	private String custType;//客户类型
	private String custSaleMan;//业务员
	private CL_Protocol protocol; //协议信息
	
	private Integer fispass_audit;//提交审核0未通过1通过
	private Integer fregulator;//运费调整者
	private Date fregulate_time;//调整时间
	private Integer fauditor;//运费审核人
	private Date faudit_time;//审批时间
	private BigDecimal totalFreight;//实际运费
	private Integer freturn_car;//回程车
	
	private java.lang.Integer subUserId;//子帐号ID
	private java.lang.String subName ; //子帐号名称,主要用于子帐号可以删除时的记录;
	private String fordesource ;				/***订单来源 ：desktops, ios, android ,默认桌面来源*/
	
	/***货到付款修改运费***/
	private java.math.BigDecimal foriginfreight;
	private java.lang.Integer fupdateman;
	private java.util.Date fupdatetime;
	private String cotherName;
	private BigDecimal ftotal;//总运费
	
	

	public BigDecimal getFtotal() {
		return ftotal;
	}

	public void setFtotal(BigDecimal ftotal) {
		this.ftotal = ftotal;
	}

	public BigDecimal getSubtract() {
		return subtract;
	}

	public void setSubtract(BigDecimal subtract) {
		this.subtract = subtract;
	}

	public BigDecimal getDiscount() {
		return discount;
	}

	public void setDiscount(BigDecimal discount) {
		this.discount = discount;
	}

	public String getCotherName() {
		return cotherName;
	}

	public void setCotherName(String cotherName) {
		this.cotherName = cotherName;
	}

	public BigDecimal getDriver_serve_1() {
		return driver_serve_1;
	}

	public void setDriver_serve_1(BigDecimal driver_serve_1) {
		this.driver_serve_1 = driver_serve_1;
	}

	public BigDecimal getDriver_serve_2() {
		return driver_serve_2;
	}

	public void setDriver_serve_2(BigDecimal driver_serve_2) {
		this.driver_serve_2 = driver_serve_2;
	}

	public BigDecimal getServe_1() {
		return serve_1;
	}

	public void setServe_1(BigDecimal serve_1) {
		this.serve_1 = serve_1;
	}

	public BigDecimal getServe_2() {
		return serve_2;
	}

	public void setServe_2(BigDecimal serve_2) {
		this.serve_2 = serve_2;
	}

	public Integer getFreturn_car() {
		return freturn_car;
	}

	public void setFreturn_car(Integer freturn_car) {
		this.freturn_car = freturn_car;
	}

	public Date getFregulate_time() {
		return fregulate_time;
	}

	public void setFregulate_time(Date fregulate_time) {
		this.fregulate_time = fregulate_time;
	}
	
	public String getFregulate_timeString(){
		return DateConvertUtils.format(getFregulate_time(), FORMAT_CREATE_TIME);
	}
	
	public void setFregulate_timeString(String value){
		setFregulate_time(DateConvertUtils.parse(value, FORMAT_CREATE_TIME, java.util.Date.class));
	}

	public Date getFaudit_time() {
		return faudit_time;
	}

	public void setFaudit_time(Date faudit_time) {
		this.faudit_time = faudit_time;
	}
	
	public String getFaudit_timeString(){
		return DateConvertUtils.format(getFaudit_time(), FORMAT_CREATE_TIME);
	}
	
	public void setFaudit_timeString(String value){
		setFaudit_time(DateConvertUtils.parse(value, FORMAT_CREATE_TIME, java.util.Date.class));
	}

	public Integer getFregulator() {
		return fregulator;
	}

	public void setFregulator(Integer fregulator) {
		this.fregulator = fregulator;
	}

	public Integer getFauditor() {
		return fauditor;
	}

	public void setFauditor(Integer fauditor) {
		this.fauditor = fauditor;
	}

	public java.math.BigDecimal getForigin_driverfee() {
		return forigin_driverfee;
	}

	public void setForigin_driverfee(java.math.BigDecimal forigin_driverfee) {
		this.forigin_driverfee = forigin_driverfee;
	}

	public BigDecimal getTotalFreight() {
		return totalFreight;
	}

	public void setTotalFreight(BigDecimal totalFreight) {
		this.totalFreight = totalFreight;
	}

	public Integer getFispass_audit() {
		return fispass_audit;
	}

	public void setFispass_audit(Integer fispass_audit) {
		this.fispass_audit = fispass_audit;
	}

	public java.math.BigDecimal getForiginfreight() {
		return foriginfreight;
	}

	public void setForiginfreight(java.math.BigDecimal foriginfreight) {
		this.foriginfreight = foriginfreight;
	}

	public java.lang.Integer getFupdateman() {
		return fupdateman;
	}

	public void setFupdateman(java.lang.Integer fupdateman) {
		this.fupdateman = fupdateman;
	}

	public java.util.Date getFupdatetime() {
		return fupdatetime;
	}

	public void setFupdatetime(java.util.Date fupdatetime) {
		this.fupdatetime = fupdatetime;
	}
	
	public String getFupdateTimeString() {
		return DateConvertUtils.format(getFupdatetime(), FORMAT_UPDATE_TIME);
	}
	public void setFupdateTimeString(String value) {
		setFupdatetime(DateConvertUtils.parse(value, FORMAT_UPDATE_TIME,java.util.Date.class));
	}

	public CL_Protocol getProtocol() {
		return protocol;
	}

	public void setProtocol(CL_Protocol protocol) {
		this.protocol = protocol;
	}

	public String getCustName() {
		return custName;
	}

	public void setCustName(String custName) {
		this.custName = custName;
	}

	public String getCustNumber() {
		return custNumber;
	}

	public void setCustNumber(String custNumber) {
		this.custNumber = custNumber;
	}

	public String getCustType() {
		return custType;
	}

	public void setCustType(String custType) {
		this.custType = custType;
	}

	public String getCustSaleMan() {
		return custSaleMan;
	}

	public void setCustSaleMan(String custSaleMan) {
		this.custSaleMan = custSaleMan;
	}
	
	public java.math.BigDecimal getFdriverAllin() {
		return fdriverAllin;
	}

	public void setFdriverAllin(java.math.BigDecimal fdriverAllin) {
		this.fdriverAllin = fdriverAllin;
	}
//	public java.math.BigDecimal getFdriverAllin() {
//		String addNumber ="";
//		if(getAllNumber()!=null && !getAllNumber().isEmpty()){
//			addNumber = getAllNumber();
//		}else{
//			addNumber = "0";
//		}
//		return CalcTotalField.calDriverFee(new BigDecimal(addNumber).add(getFreight())).
//				multiply(new BigDecimal(addNumber).add(getFreight())).setScale(1,BigDecimal.ROUND_HALF_UP);
//	}
//	public void setFdriverAllin(java.math.BigDecimal fdriverAllin) {
////		this.fdriverAllin = CalcTotalField.calDriverFee(getFreight()).multiply(getFreight());
//		this.fdriverAllin = fdriverAllin;
//	}

	public java.math.BigDecimal getFdriverfee() {
		return fdriverfee;
	}


	public void setFdriverfee(java.math.BigDecimal fdriverfee) {
		this.fdriverfee = fdriverfee;
	}

	public java.math.BigDecimal getFaddNumber() {
		return faddNumber;
	}

	public void setFaddNumber(java.math.BigDecimal faddNumber) {
		this.faddNumber = faddNumber;
	}

	public java.lang.String getAllNumber() {
		return allNumber;
	}

	public void setAllNumber(java.lang.String allNumber) {
		this.allNumber = allNumber;
	}

	public java.math.BigDecimal getForiginal() {
		return foriginal;
	}

	public void setForiginal(java.math.BigDecimal foriginal) {
		this.foriginal = foriginal;
	}

	public int getFonlinePay() {
		return fonlinePay;
	}

	public void setFonlinePay(int fonlinePay) {
		this.fonlinePay = fonlinePay;
	}

	public java.util.Date getFcopTime() {
		return fcopTime;
	}

	public void setFcopTime(java.util.Date fcopTime) {
		this.fcopTime = fcopTime;
	}

	public void setFcopTimeString(String value){
		setFcopTime(DateConvertUtils.parse(value, FORMAT_FPAY_TIME,java.util.Date.class));
	}
	public String getFcopTimeString(){
		return DateConvertUtils.format(getFcopTime(), FORMAT_FPAY_TIME);
	}

	public int getFdelOrder() {
		return fdelOrder;
	}

	public void setFdelOrder(int fdelOrder) {
		this.fdelOrder = fdelOrder;
	}

	public java.math.BigDecimal getChargingrule() {
		return Chargingrule;
	}

	public void setChargingrule(java.math.BigDecimal chargingrule) {
		Chargingrule = chargingrule;
	}

	public java.util.Date getFpayTime() {
		return fpayTime;
	}

	public void setFpayTime(java.util.Date fpayTime) {
		this.fpayTime = fpayTime;
	}

	public void setFpayTimeString(String value){
		setFpayTime(DateConvertUtils.parse(value, FORMAT_FPAY_TIME,java.util.Date.class));
	}
	public String getFpayTimeString(){
		return DateConvertUtils.format(getFpayTime(), FORMAT_FPAY_TIME);
	}

	public java.lang.Integer getFopint() {
		return fopint;
	}

	public void setFopint(java.lang.Integer fopint) {
		this.fopint = fopint;
	}

	public java.lang.String getPayMethod() {
		return payMethod;
	}

	public void setPayMethod(java.lang.String payMethod) {
		this.payMethod = payMethod;
	}



	public JSONArray getServer() {
		return server;
	}

	public void setServer(JSONArray server) {
		this.server = server;
	}

	public java.lang.Integer getFpayMethod() {
		return fpayMethod;
	}


	private java.lang.String fsalesMan ; //业务员
	private java.util.Date farrivePickUpTime ; //到达提货点时间
	private java.util.Date fleavePickUpTime ; //离开提货点时间
	private java.lang.String fremark ; //备注
	private java.lang.Integer fpayMethod;//支付方式 0:装卸1:电子回单2:回单原价3:上楼4:代收货款       ______ 可输入文本


	public void setFpayMethod(java.lang.Integer fpayMethod) {
		this.fpayMethod = fpayMethod;
	}


	//columns END
	/***非数据库字段****/
	private java.lang.String takeAddress;//发货人地址
	private java.lang.String takelinkman;//发货联系人
	private java.lang.String takephone;//发货联系人电话
	private java.lang.String recAddress;//收货人地址
	private java.lang.String reclinkman;//收货联系人
	private java.lang.String recphone;//收货联系人电话
	private java.lang.String orderDriverName;//订单司机名字
	private java.lang.String orderDriverphone;//订单司机电话
	private java.lang.String OrderDriverCarType;//订单司机车型
	private java.lang.String OrderDriverCarNumber;//订单司机车牌号
	private List<CL_OrderDetail> takeList; //多个发货人地址
	private List<CL_OrderDetail> recList;  //多个收货人地址
	private java.lang.String takelongitude;// 发货地址经度
	private java.lang.String takelatitude;//发货地址纬度
	private java.lang.String reclongitude;// 收货地址经度
	private java.lang.String reclatitude;//收货地址纬度
	private java.lang.Integer takeCount;//发货地址总行数；
	private java.lang.Integer carId;//司机ID 
	private java.lang.Integer carType; 
	private java.lang.Integer car_spec_id;
//	private java.lang.String carNum;
	private int frating  ;//货主对司机评价 0,未评价 1已评价
	private int frating_driver  ;//司机对货主评价 0,未评价 1已评价
	private int identifyType;//认证状态
	private int fofflinePay;//到付运费线下支付 司机是否上缴
	private String regulator;//运费调整者 
	private String auditor;//运费审核人
	private Integer carSpecId;//规格
	private int carTypeId;//车型
	
	
	public int getFrating_driver() {
		return frating_driver;
	}

	public void setFrating_driver(int frating_driver) {
		this.frating_driver = frating_driver;
	}

	public int getCarTypeId() {
		return carTypeId;
	}

	public void setCarTypeId(int carTypeId) {
		this.carTypeId = carTypeId;
	}

	public String getRegulator() {
		return regulator;
	}

	public void setRegulator(String regulator) {
		this.regulator = regulator;
	}

	public String getAuditor() {
		return auditor;
	}

	public void setAuditor(String auditor) {
		this.auditor = auditor;
	}

	public java.lang.Integer getCarType() {
		return carType;
	}

	public void setCarType(java.lang.Integer carType) {
		this.carType = carType;
	}

	public java.lang.Integer getCar_spec_id() {
		return car_spec_id;
	}

	public void setCar_spec_id(java.lang.Integer car_spec_id) {
		this.car_spec_id = car_spec_id;
	}


/*	public java.lang.String getCarNum() {
		return carNum;
	}

	public void setCarNum(java.lang.String carNum) {
		this.carNum = carNum;
	}*/


	private java.lang.String abnormityId;
	
	public int getFofflinePay() {
		return fofflinePay;
	}

	public void setFofflinePay(int fofflinePay) {
		this.fofflinePay = fofflinePay;
	}

	public int getFrating() {
		return frating;
	}

	public void setFrating(int frating) {
		this.frating = frating;
	}

	public java.lang.Integer getCarId() {
		return carId;
	}

	public void setCarId(java.lang.Integer carId) {
		this.carId = carId;
	}
	
	public java.lang.String getAbnormityId() {
		return abnormityId;
	}

	public void setAbnormityId(java.lang.String abnormityId) {
		this.abnormityId = abnormityId;
	}

	public java.math.BigDecimal getDollars() {
		return dollars;
	}

	public void setDollars(java.math.BigDecimal dollars) {
		this.dollars = dollars;
	}


	public java.math.BigDecimal getDollarsDet() {
		return dollarsDet;
	}

	public void setDollarsDet(java.math.BigDecimal dollarsDet) {
		this.dollarsDet = dollarsDet;
	}


	private java.lang.Integer recCount;//收货地址总行数；
	private List<CL_OrderCarDetail> carList;// 多个车型
	private java.lang.String operatorName;//操作人
	private java.lang.String carTypeName;//车型
	private java.lang.String creatorName;//创建人姓名
	private java.lang.Integer isoverdue;//是否过期 :1是,2否
	private java.lang.String creatorPhone;//创建人电话
	private java.lang.String addressName;//提货点
	private java.lang.String carSpecName;//车辆名字
	private java.math.BigDecimal average;//平均分
	private java.math.BigDecimal  dollars;//订单优惠卷面额
	private java.math.BigDecimal  dollarsDet;//增值服务优惠卷面额
	private java.lang.String funName;//单位名称
	private java.math.BigDecimal AllCost;//追加费用总价
	
	private Integer carSpec;
	

	public Integer getCarSpec() {
		return carSpec;
	}

	public void setCarSpec(Integer carSpec) {
		this.carSpec = carSpec;
	}

	public java.math.BigDecimal getAllCost() {
		return AllCost;
	}

	public void setAllCost(java.math.BigDecimal allCost) {
		AllCost = allCost;
	}

	public java.lang.String getFunName() {
		return funName;
	}

	public void setFunName(java.lang.String funName) {
		this.funName = funName;
	}

	public java.lang.Integer getCarSpecId() {
		return carSpecId;
	}

	public void setCarSpecId(java.lang.Integer carSpecId) {
		this.carSpecId = carSpecId;
	}

	public java.math.BigDecimal getAverage() {
		return average;
	}

	public void setAverage(java.math.BigDecimal average) {
		this.average = average;
	}


	public java.lang.String getCarSpecName() {
		return carSpecName;
	}

	public void setCarSpecName(java.lang.String carSpecName) {
		this.carSpecName = carSpecName;
	}

	public java.lang.String getAddressName() {
		return addressName;
	}

	public void setAddressName(java.lang.String addressName) {
		this.addressName = addressName;
	}

	public java.lang.String getCreatorPhone() {
		return creatorPhone;
	}

	public void setCreatorPhone(java.lang.String creatorPhone) {
		this.creatorPhone = creatorPhone;
	}

	public java.lang.String getCreatorName() {
		return creatorName;
	}

	public void setCreatorName(java.lang.String creatorName) {
		this.creatorName = creatorName;
	}

	public java.lang.Integer getIsCommon() {
		return isCommon;
	}

	public void setIsCommon(java.lang.Integer isCommon) {
		this.isCommon = isCommon;
	}


	public java.lang.String getCarTypeName() {
		return carTypeName;
	}

	public void setCarTypeName(java.lang.String carTypeName) {
		this.carTypeName = carTypeName;
	}

	public java.lang.Integer getTakeCount() {
		return takeCount;
	}

	public List<CL_OrderCarDetail> getCarList() {
		return carList;
	}

	public void setCarList(List<CL_OrderCarDetail> carList) {
		this.carList = carList;
	}

	public void setTakeCount(java.lang.Integer takeCount) {
		this.takeCount = takeCount;
	}

	public java.lang.Integer getRecCount() {
		return recCount;
	}

	public void setRecCount(java.lang.Integer recCount) {
		this.recCount = recCount;
	}

	public java.lang.Integer getUpload() {
		return upload;
	}

	public void setUpload(java.lang.Integer upload) {
		this.upload = upload;
	}



	public java.lang.String getTakelongitude() {
		return takelongitude;
	}

	public void setTakelongitude(java.lang.String takelongitude) {
		this.takelongitude = takelongitude;
	}

	public java.lang.String getTakelatitude() {
		return takelatitude;
	}

	public void setTakelatitude(java.lang.String takelatitude) {
		this.takelatitude = takelatitude;
	}

	public java.lang.String getReclongitude() {
		return reclongitude;
	}

	public void setReclongitude(java.lang.String reclongitude) {
		this.reclongitude = reclongitude;
	}

	public java.lang.String getReclatitude() {
		return reclatitude;
	}

	public void setReclatitude(java.lang.String reclatitude) {
		this.reclatitude = reclatitude;
	}

	public List<CL_OrderDetail> getTakeList() {
		return takeList;
	}

	public void setTakeList(List<CL_OrderDetail> takeList) {
		this.takeList = takeList;
	}

	public List<CL_OrderDetail> getRecList() {
		return recList;
	}

	public void setRecList(List<CL_OrderDetail> recList) {
		this.recList = recList;
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

	public java.lang.Integer getUserRoleId() {
		return userRoleId;
	}

	public void setUserRoleId(java.lang.Integer userRoleId) {
		this.userRoleId = userRoleId;
	}

	public java.lang.String getOrderDriverName() {
		return orderDriverName;
	}

	public void setOrderDriverName(java.lang.String orderDriverName) {
		this.orderDriverName = orderDriverName;
	}

	public java.lang.String getOrderDriverphone() {
		return orderDriverphone;
	}

	public void setOrderDriverphone(java.lang.String orderDriverphone) {
		this.orderDriverphone = orderDriverphone;
	}

	public java.lang.String getOrderDriverCarType() {
		return OrderDriverCarType;
	}

	public void setOrderDriverCarType(java.lang.String orderDriverCarType) {
		OrderDriverCarType = orderDriverCarType;
	}

	public java.lang.String getOrderDriverCarNumber() {
		return OrderDriverCarNumber;
	}

	public void setOrderDriverCarNumber(java.lang.String orderDriverCarNumber) {
		OrderDriverCarNumber = orderDriverCarNumber;
	}

	public java.lang.String getTakeAddress() {
		return takeAddress;
	}

	public void setTakeAddress(java.lang.String takeAddress) {
		this.takeAddress = takeAddress;
	}

	public java.lang.String getRecAddress() {
		return recAddress;
	}

	public void setRecAddress(java.lang.String recAddress) {
		this.recAddress = recAddress;
	}



	public java.lang.String getTakelinkman() {
		return takelinkman;
	}

	public void setTakelinkman(java.lang.String takelinkman) {
		this.takelinkman = takelinkman;
	}

	public java.lang.String getTakephone() {
		return takephone;
	}

	public void setTakephone(java.lang.String takephone) {
		this.takephone = takephone;
	}

	public java.lang.String getReclinkman() {
		return reclinkman;
	}

	public void setReclinkman(java.lang.String reclinkman) {
		this.reclinkman = reclinkman;
	}

	public java.lang.String getRecphone() {
		return recphone;
	}

	public void setRecphone(java.lang.String recphone) {
		this.recphone = recphone;
	}

	public CL_Order(){}

	public CL_Order(java.lang.Integer id){
		this.id = id;
	}

	public void setId(java.lang.Integer value) {
		this.id = value;
	}

	public java.lang.Integer getId() {
		return this.id;
	}
	public void setNumber(java.lang.String value) {
		this.number = value;
	}

	public java.lang.String getNumber() {
		return this.number;
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
	public void setGoodsTypeId(java.lang.String value) {
		this.goodsTypeId = value;
	}

	public java.lang.String getGoodsTypeId() {
		return this.goodsTypeId;
	}
	public void setGoodsTypeName(java.lang.String value) {
		this.goodsTypeName = value;
	}

	public java.lang.String getGoodsTypeName() {
		return this.goodsTypeName;
	}
	public void setWeight(java.math.BigDecimal value) {
		this.weight = value;
	}

	public java.math.BigDecimal getWeight() {
		return this.weight;
	}
	public void setVolume(java.math.BigDecimal value) {
		this.volume = value;
	}

	public java.math.BigDecimal getVolume() {
		return this.volume;
	}
	public void setLength(java.math.BigDecimal value) {
		this.length = value;
	}

	public java.math.BigDecimal getLength() {
		return this.length;
	}
	public String getLoadedTimeString() {
		return DateConvertUtils.format(getLoadedTime(), FORMAT_LOADED_TIME);
	}
	public void setLoadedTimeString(String value) {
		setLoadedTime(DateConvertUtils.parse(value, FORMAT_LOADED_TIME,java.util.Date.class));
	}

	public void setLoadedTime(java.util.Date value) {
		this.loadedTime = value;
	}

	public java.util.Date getLoadedTime() {
		return this.loadedTime;
	}
	public void setCreator(java.lang.Integer value) {
		this.creator = value;
	}

	public java.lang.Integer getCreator() {
		return this.creator;
	}
	public String getCreateTimeString() {
		return DateConvertUtils.format(getCreateTime(), FORMAT_CREATE_TIME);
	}
	public void setCreateTimeString(String value) {
		setCreateTime(DateConvertUtils.parse(value, FORMAT_CREATE_TIME,java.util.Date.class));
	}

	public void setCreateTime(java.util.Date value) {
		this.createTime = value;
	}

	public java.util.Date getCreateTime() {
		return this.createTime;
	}
	public void setMileage(java.math.BigDecimal value) {
		this.mileage = value;
	}

	public java.math.BigDecimal getMileage() {
		return this.mileage;
	}
	public void setFreight(java.math.BigDecimal value) {
		this.freight = value;
	}

	public java.math.BigDecimal getFreight() {
		return this.freight;
	}

	public java.lang.Integer getOperator() {
		return operator;
	}

	public void setOperator(java.lang.Integer operator) {
		this.operator = operator;
	}

	public java.lang.String getOperatorName() {
		return operatorName;
	}

	public void setOperatorName(java.lang.String operatorName) {
		this.operatorName = operatorName;
	}

	public java.lang.Integer getIsoverdue() {
		return isoverdue;
	}

	public void setIsoverdue(java.lang.Integer isoverdue) {
		this.isoverdue = isoverdue;
	}

	public java.lang.Integer getCouponsDetailId() {
		return couponsDetailId;
	}

	public void setCouponsDetailId(java.lang.Integer couponsDetailId) {
		this.couponsDetailId = couponsDetailId;
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

	public java.lang.Integer getProtocolType() {
		return protocolType;
	}

	public void setProtocolType(java.lang.Integer protocolType) {
		this.protocolType = protocolType;
	}

	public java.lang.Integer getProtocolId() {
		return protocolId;
	}

	public void setProtocolId(java.lang.Integer protocolId) {
		this.protocolId = protocolId;
	}

	public java.lang.Integer getFunitId() {
		return funitId;
	}

	public void setFunitId(java.lang.Integer funitId) {
		this.funitId = funitId;
	}

	public java.math.BigDecimal getFamount() {
		return famount;
	}

	public void setFamount(java.math.BigDecimal famount) {
		this.famount = famount;
	}

	public java.lang.String getFincrementServe() {
		return fincrementServe;
	}

	public void setFincrementServe(java.lang.String fincrementServe) {
		this.fincrementServe = fincrementServe;
	}

	public java.lang.String getFsalesMan() {
		return fsalesMan;
	}

	public void setFsalesMan(java.lang.String fsalesMan) {
		this.fsalesMan = fsalesMan;
	}

	public java.util.Date getFarrivePickUpTime() {
		return farrivePickUpTime;
	}

	public void setFarrivePickUpTime(java.util.Date farrivePickUpTime) {
		this.farrivePickUpTime = farrivePickUpTime;
	}

	public java.util.Date getFleavePickUpTime() {
		return fleavePickUpTime;
	}

	public void setFleavePickUpTime(java.util.Date fleavePickUpTime) {
		this.fleavePickUpTime = fleavePickUpTime;
	}

	public java.lang.String getFremark() {
		return fremark;
	}

	public void setFremark(java.lang.String fremark) {
		this.fremark = fremark;
	}
	
	//回单上传地址
	private java.lang.String url;
	public java.lang.String getUrl() {
		return url;
	}
	public void setUrl(java.lang.String url) {
		this.url = url;
	}

	public int getIdentifyType() {
		return identifyType;
	}

	public void setIdentifyType(int identifyType) {
		this.identifyType = identifyType;
	}

	public java.lang.Integer getSubUserId() {
		return subUserId;
	}

	public void setSubUserId(java.lang.Integer subUserId) {
		this.subUserId = subUserId;
	}

	public java.lang.String getSubName() {
		return subName;
	}

	public void setSubName(java.lang.String subName) {
		this.subName = subName;
	}

	public String getFordesource() {
		return fordesource;
	}

	public void setFordesource(String fordesource) {
		this.fordesource = fordesource;
	}

	public java.util.Date getFoperate_time() {
		return foperate_time;
	}

	public void setFoperate_time(java.util.Date foperate_time) {
		this.foperate_time = foperate_time;
	}
	
	public String getFoperate_timeString() {
		return DateConvertUtils.format(getFoperate_time(), FORMAT_OPERATE_TIME);
	}
	public void setFoperate_timeString(String value) {
		setFoperate_time(DateConvertUtils.parse(value, FORMAT_OPERATE_TIME,java.util.Date.class));
	}
	
	public BigDecimal getFtotalFreight() {
		return ftotalFreight;
	}

	public void setFtotalFreight(BigDecimal ftotalFreight) {
		this.ftotalFreight = ftotalFreight;
	}

	public Integer getCouponsId() {
		return couponsId;
	}

	public void setCouponsId(Integer couponsId) {
		this.couponsId = couponsId;
	}

	@Override
	public String toString() {
		return "CL_Order [id=" + id + ", number=" + number + ", type=" + type
				+ ", status=" + status + ", goodsTypeId=" + goodsTypeId
				+ ", goodsTypeName=" + goodsTypeName + ", weight=" + weight
				+ ", volume=" + volume + ", length=" + length + ", loadedTime="
				+ loadedTime + ", creator=" + creator + ", createTime="
				+ createTime + ", mileage=" + mileage + ", freight=" + freight
				+ ", userRoleId=" + userRoleId + ", upload=" + upload
				+ ", receipt=" + receipt + ", collection=" + collection
				+ ", isCommon=" + isCommon + ", operator=" + operator
				+ ", foperate_time=" + foperate_time + ", couponsDetailId="
				+ couponsDetailId + ", purposeAmount=" + purposeAmount
				+ ", isFreightCollect=" + isFreightCollect + ", protocolType="
				+ protocolType + ", protocolId=" + protocolId + ", funitId="
				+ funitId + ", famount=" + famount + ", fincrementServe="
				+ fincrementServe + ", payMethod=" + payMethod + ", server="
				+ server + ", fopint=" + fopint + ", fpayTime=" + fpayTime
				+ ", Chargingrule=" + Chargingrule + ", fcopTime=" + fcopTime
				+ ", fdelOrder=" + fdelOrder + ", fonlinePay=" + fonlinePay
				+ ", foriginal=" + foriginal + ", faddNumber=" + faddNumber
				+ ", fdriverfee=" + fdriverfee + ", allNumber=" + allNumber
				+ ", fdriverAllin=" + fdriverAllin + ", custName=" + custName
				+ ", custNumber=" + custNumber + ", custType=" + custType
				+ ", custSaleMan=" + custSaleMan + ", protocol=" + protocol
				+ ", subUserId=" + subUserId + ", subName=" + subName
				+ ", fordesource=" + fordesource + ", foriginfreight="
				+ foriginfreight + ", fupdateman=" + fupdateman
				+ ", fupdatetime=" + fupdatetime + ", fsalesMan=" + fsalesMan
				+ ", farrivePickUpTime=" + farrivePickUpTime
				+ ", fleavePickUpTime=" + fleavePickUpTime + ", fremark="
				+ fremark + ", fpayMethod=" + fpayMethod + ", takeAddress="
				+ takeAddress + ", takelinkman=" + takelinkman + ", takephone="
				+ takephone + ", recAddress=" + recAddress + ", reclinkman="
				+ reclinkman + ", recphone=" + recphone + ", orderDriverName="
				+ orderDriverName + ", orderDriverphone=" + orderDriverphone
				+ ", OrderDriverCarType=" + OrderDriverCarType
				+ ", OrderDriverCarNumber=" + OrderDriverCarNumber
				+ ", takeList=" + takeList + ", recList=" + recList
				+ ", takelongitude=" + takelongitude + ", takelatitude="
				+ takelatitude + ", reclongitude=" + reclongitude
				+ ", reclatitude=" + reclatitude + ", takeCount=" + takeCount
				+ ", carId=" + carId + ", carType=" + carType
				+ ", car_spec_id=" + car_spec_id + ", frating=" + frating
				+ ", identifyType=" + identifyType + ", fofflinePay="
				+ fofflinePay + ", abnormityId=" + abnormityId + ", recCount="
				+ recCount + ", carList=" + carList + ", operatorName="
				+ operatorName + ", carTypeName=" + carTypeName
				+ ", creatorName=" + creatorName + ", isoverdue=" + isoverdue
				+ ", creatorPhone=" + creatorPhone + ", addressName="
				+ addressName + ", carSpecName=" + carSpecName + ", average="
				+ average + ", dollars=" + dollars + ", carSpecId=" + carSpecId
				+ ", funName=" + funName + ", AllCost=" + AllCost + ", url="
				+ url + "]";
	}
	
	
}