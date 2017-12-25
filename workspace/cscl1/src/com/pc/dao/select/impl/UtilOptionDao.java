package com.pc.dao.select.impl;

import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Repository;

import cn.org.rapid_framework.page.Page;
import cn.org.rapid_framework.page.PageRequest;

import com.pc.dao.aBase.impl.BaseDao;
import com.pc.dao.select.IUtilOptionDao;
import com.pc.model.Util_Option;


@Repository("optionDao")
public class UtilOptionDao extends BaseDao<Util_Option, java.lang.Integer> implements IUtilOptionDao {

	@Override
	public String getIbatisSqlMapNamespace() {
		return "Util_Option";
	}
	
	public List<Util_Option>  getAllCarType(){
		return this.getSqlSession().selectList(getIbatisSqlMapNamespace()+".getAllCarType");
	}
	
	public List<Util_Option>  getAllCarType1(){
		return this.getSqlSession().selectList(getIbatisSqlMapNamespace()+".getAllCarType1");
	}

	@Override
	public List<Util_Option> getAllCarSpecByCarType(Integer carSpecId) {
		return this.getSqlSession().selectList(getIbatisSqlMapNamespace()+".getAllCarSpecByCarType",carSpecId);
	}

	@Override
	public List<Util_Option> getOtherByCarSpec(Integer carSpecId) {
		 
		return this.getSqlSession().selectList(getIbatisSqlMapNamespace()+".getOtherByCarSpec",carSpecId);
	}

	@Override
	public List<Util_Option> getAllGoodsType() {
		return this.getSqlSession().selectList(getIbatisSqlMapNamespace()+".getAllGoodsType");
	}
	
	@Override
	public List<Util_Option> getCarSpecByOrderId(Integer id) {
		return this.getSqlSession().selectList(getIbatisSqlMapNamespace()+".getCarSpecByOrderId",id);
	}



	@Override
	public List<Util_Option> getCarTypeByOrderId(Integer id) {
		return this.getSqlSession().selectList(getIbatisSqlMapNamespace()+".getCarTypeByOrderId",id);
	}

	@Override
	public List<Util_Option> getDriverByCarTypeId(Integer carTypeId,Integer carSpecId) {
		HashMap<String,Object> map = new HashMap<String,Object>();
		map.put("carSpecId", carSpecId);
		map.put("carTypeId", carTypeId);
		return this.getSqlSession().selectList(getIbatisSqlMapNamespace()+".getDriverByCarTypeId",map);
	}

	@Override
	public List<Util_Option> getAllConsignor(Integer couponsId) {
		HashMap<String,Object> map = new HashMap<String,Object>();
		map.put("couponsId", couponsId);
		return this.getSqlSession().selectList(getIbatisSqlMapNamespace()+".getAllConsignor",map);
	}

	@Override
	public List<Util_Option> getByOtherTypeByUnit() {
		return this.getSqlSession().selectList(getIbatisSqlMapNamespace()+".getByOtherTypeByUnit");
	}

	@Override
	public List<Util_Option> getByRoleId() {
		return this.getSqlSession().selectList(getIbatisSqlMapNamespace()+".getByRoleId");
	}
	
	@Override
	public List<Util_Option> getAll() {
		return this.getSqlSession().selectList(getIbatisSqlMapNamespace()+".getAll");
	}
	
	@Override
	public List<Util_Option> getByIdentify() {
		return this.getSqlSession().selectList(getIbatisSqlMapNamespace()+".getByIdentify");
	}

	@Override
	public List<Util_Option> getByUnit() {
		return this.getSqlSession().selectList(getIbatisSqlMapNamespace()+".getByUnit");
	}

	@Override
	public List<Util_Option> getCarTypeByOrderIdName(Integer orderId) {
		return this.getSqlSession().selectList(getIbatisSqlMapNamespace()+".getCarTypeByOrderIdName",orderId);
	}
	
	@Override
	public List<Util_Option> getCarTypeByOrderIdName2(Integer orderId) {
		return this.getSqlSession().selectList(getIbatisSqlMapNamespace()+".getCarTypeByOrderIdName2",orderId);
	}

	@Override
	public List<Util_Option> getAllCustId() {
		// TODO Auto-generated method stub
		return this.getSqlSession().selectList(getIbatisSqlMapNamespace()+".getAllCustId");
	}

	@Override
	public List<Util_Option> getFinishOrders() {
		// TODO Auto-generated method stub
		return this.getSqlSession().selectList(getIbatisSqlMapNamespace()+".getFinishOrders");
	}
	
	@Override
	public List<Util_Option> getAllDrivers() {
		// TODO Auto-generated method stub
		return this.getSqlSession().selectList(getIbatisSqlMapNamespace()+".getAllDrivers");
	}

	@Override
	public List<Util_Option> getByRoleCarId() {
		return this.getSqlSession().selectList(getIbatisSqlMapNamespace()+".getByRoleCarId");
	}

	/*@Override
	public List<Util_Option> getVersion(Integer ftype) {
		return this.getSqlSession().selectList(getIbatisSqlMapNamespace()+".getVersion",ftype);
	}*/
	
	@Override
	public Util_Option getVersion2(Integer ftype) {
		return this.getSqlSession().selectOne(getIbatisSqlMapNamespace()+".getVersion2",ftype);
	}

	@Override
	public Page<Util_Option> findOrderByType(PageRequest pr) {
		return this.pageQuery(getIbatisSqlMapNamespace()+".findOrderByType",pr);
	}

	@Override
	public List<Util_Option> getAllCustomer(Integer optionTemp,Integer userRoleId) {
		HashMap<String, Object> m = new HashMap<String, Object>();
		m.put("type", optionTemp);
		m.put("userRoleId", userRoleId);
		return this.getSqlSession().selectList(getIbatisSqlMapNamespace()+".getAllCustomer",m);
	}
	
	@Override
	public List<Util_Option> getAllCustomerNew(){
		return this.getSqlSession().selectList(getIbatisSqlMapNamespace()+".getAllCustomerNew");
	}

	@Override
	public List<Util_Option> getAllCus() {
		return this.getSqlSession().selectList(getIbatisSqlMapNamespace()+".getAllCus");
	}

	@Override
	public List<Util_Option> getAllDriver() {
		return this.getSqlSession().selectList(getIbatisSqlMapNamespace()+".getAllDriver");
	}

	@Override
	public List<Util_Option> getUseFactory() {
		// TODO Auto-generated method stub
		return this.getSqlSession().selectList(getIbatisSqlMapNamespace()+".getUseFactory");
	}

	@Override
	public List<Util_Option> getAllProvince() {
		// TODO Auto-generated method stub
		return this.getSqlSession().selectList(getIbatisSqlMapNamespace()+".getAllProvince");
	}

	@Override
	public List<Util_Option> getAllCity(Integer fprovince_id) {
		// TODO Auto-generated method stub
		HashMap<String, Object> m = new HashMap<>();
		m.put("fprovince_id", fprovince_id);
		return this.getSqlSession().selectList(getIbatisSqlMapNamespace()+".getAllCity",m);
	}

	@Override
	public List<Util_Option> getAllArea(Integer fcity_id) {
		// TODO Auto-generated method stub
		HashMap<String, Object> m = new HashMap<>();
		m.put("fcity_id", fcity_id);
		return this.getSqlSession().selectList(getIbatisSqlMapNamespace()+".getAllArea",m);
	}
	
	@Override
	public List<Util_Option> getAllArea() {
		// TODO Auto-generated method stub
		return this.getSqlSession().selectList(getIbatisSqlMapNamespace()+".getAllArea2");
	}

	@Override
	public List<Util_Option> getEffectiveAllGeneralAdd() {
		// TODO Auto-generated method stub
		return this.getSqlSession().selectList(getIbatisSqlMapNamespace()+".getEffectiveAllGeneralAdd");
	}

	@Override
	public List<Util_Option> getAllCoupons() {
		// TODO Auto-generated method stub
		return this.getSqlSession().selectList(getIbatisSqlMapNamespace()+".getAllCoupons");
	}
	
 
	
}
