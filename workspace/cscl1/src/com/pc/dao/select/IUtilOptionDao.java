package com.pc.dao.select;

import java.util.List;

import cn.org.rapid_framework.page.Page;
import cn.org.rapid_framework.page.PageRequest;

import com.pc.dao.aBase.IBaseDao;
import com.pc.model.Util_Option;

public interface IUtilOptionDao extends IBaseDao<Util_Option, java.lang.Integer> {
	
	/***获取所有车辆类型*/
	public List<Util_Option>  getAllCarType();
	
	/***获取所有货物类型*/
	public List<Util_Option>  getAllGoodsType();
	
	/***根据所属的车辆类型获取对应的所有车辆规格*/
	public List<Util_Option>  getAllCarSpecByCarType(Integer carSpecId);
	
	/***根据所属的车辆规格获取所有的其他要求*/
	public List<Util_Option>  getOtherByCarSpec(Integer carSpecId);
	
	/***通过订单id获取订单对应车辆规格*/
	public List<Util_Option> getCarSpecByOrderId(Integer id);
	
	/***通过订单id获取订单对应车辆类型-去掉重复*/
	public List<Util_Option> getCarTypeByOrderId(Integer id);
	
	/***通过订单id获取订单对应车辆规格*/
	public List<Util_Option> getDriverByCarTypeId(Integer carTypeId,Integer carSpecId);
	
	/***获取排除已指派优惠券剩余的所有货主*/
	public List<Util_Option> getAllConsignor(Integer couponsId);
	
	/****获取所有的零担协议规则类型***/
	public List<Util_Option> getByOtherTypeByUnit();
	
	/*****获得所有的 货主类型Id****/
	public List<Util_Option> getByRoleId();
	
	/*****获得所有的 用户Id****/
	public List<Util_Option> getAll();
	
	/*****获得所有的 货主类型Id****/
	public List<Util_Option> getByIdentify();
	
	/******获得所有协议零担单位用车**/
	public List<Util_Option> getByUnit();
	
	/***通过订单Id获得订单对应的 车辆规格名字和其他类型Name*/
	 public List<Util_Option> getCarTypeByOrderIdName(Integer orderId);
	 
	 public List<Util_Option> getCarTypeByOrderIdName2(Integer orderId);
	 
	 /***获取所有客户*/
	 public List<Util_Option> getAllCustId();
	 
	 /***完成订单下拉款*/
	 public List<Util_Option> getFinishOrders();
	 
	 /***所有司机下拉款*/
	 public List<Util_Option> getAllDrivers();	 
	 
	 /***根据类型获得所有司机**/ 
	 public List<Util_Option> getByRoleCarId();
	 
	 /***查询版本号**/
//	 public List<Util_Option> getVersion(Integer ftype);
	 public Util_Option getVersion2(Integer ftype);

 	 /** 查询 订单类型分组  根据司机或者货主  **/
     public  Page<Util_Option> findOrderByType(PageRequest pr);
	 
     /** 查询所有客户名称下拉框**/
     public List<Util_Option> getAllCustomer(Integer optionTemp,Integer userRoleId);
     
     public List<Util_Option> getAllCustomerNew();
     
     public List<Util_Option> getAllCus();
     
     public List<Util_Option> getAllDriver();
     
     public List<Util_Option> getUseFactory();
     
     public List<Util_Option> getAllProvince();
     
     public List<Util_Option> getAllCity(Integer fprovince_id);
     
     public List<Util_Option> getAllArea(Integer fcity_id);
     
     public List<Util_Option> getAllArea();
     
     //查询所有有效的通用增值服务
     public List<Util_Option> getEffectiveAllGeneralAdd();
     
     //查询所有优惠券
     public List<Util_Option> getAllCoupons();
     
}
