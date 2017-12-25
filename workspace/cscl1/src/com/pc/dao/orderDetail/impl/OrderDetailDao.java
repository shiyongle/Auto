package com.pc.dao.orderDetail.impl;

import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Service;

import com.pc.dao.aBase.impl.BaseDao;
import com.pc.dao.orderDetail.IorderDetailDao;
import com.pc.model.CL_OrderDetail;

@Service("orderDetailDao")
public class OrderDetailDao extends BaseDao<CL_OrderDetail, java.lang.Integer> implements IorderDetailDao {

	@Override
	public String getIbatisSqlMapNamespace() {
		return "CL_OrderDetail";
	}

	
	@Override
	public List<CL_OrderDetail> getByPCOrderIdForDeliver(Integer orderId) {
		HashMap<String,Object> map =new HashMap<String,Object>();
		map.put("orderId", orderId);
		return this.getSqlSession().selectList(getIbatisSqlMapNamespace()+".getByPCOrderIdForDeliver", map);
	}

	
	@Override
	public List<CL_OrderDetail> getByPCOrderId(Integer orderId) {
		HashMap<String,Object> map =new HashMap<String,Object>();
		map.put("orderId", orderId);
		return this.getSqlSession().selectList(getIbatisSqlMapNamespace()+".getByPCOrderId", map);
	}
	
	@Override
	public List<CL_OrderDetail> getByOrderId(Integer orderId,Integer detailType) {
		HashMap<String,Object> map =new HashMap<String,Object>();
		map.put("orderId", orderId);
		map.put("detailType", detailType);
		return this.getSqlSession().selectList(getIbatisSqlMapNamespace()+".getByOrderId", map);
	}
	
	@Override
	public List<CL_OrderDetail> getByOrderId(Integer orderId) {
		return this.getSqlSession().selectList(getIbatisSqlMapNamespace()+".getAllByOrderId", orderId);
	}
	
	//为防止有出入，专门为app再次下单接口改一个方法 BY LANCHER------BEGIN
	@Override
	public List<CL_OrderDetail> getByNewOrderId(Integer orderId,Integer detailType) {
		HashMap<String,Object> map =new HashMap<String,Object>();
		map.put("orderId", orderId);
		map.put("detailType", detailType);
		return this.getSqlSession().selectList(getIbatisSqlMapNamespace()+".getByNewOrderId", map);
	}
	//为防止有出入，专门为app再次下单接口改一个方法 BY LANCHER------END
	
	@Override
	public CL_OrderDetail getByOrderIdForDeliverAddress(Integer orderId) {
		return this.getSqlSession().selectOne(getIbatisSqlMapNamespace()+".getByOrderIdForDeliverAddress", orderId);
	}
	
	@Override
	public CL_OrderDetail getByOrderIdForConsigneeAddress(Integer orderId) {
		return this.getSqlSession().selectOne(getIbatisSqlMapNamespace()+".getByOrderIdForConsigneeAddress", orderId);
	}


	@Override
	public int findCount(Integer orderId, Integer detailType) {
		HashMap<String,Object> map =new HashMap<String,Object>();
		map.put("orderId", orderId);
		map.put("detailType", detailType);
		return this.getSqlSession().selectOne(getIbatisSqlMapNamespace()+".findCount",map);
	}


	@Override
	public CL_OrderDetail getByIdAndCode(Integer orderDetailId, String securityCode) {
		HashMap<String,Object> map =new HashMap<String,Object>();
		map.put("orderDetailId", orderDetailId);
		map.put("securityCode", securityCode);
		return this.getSqlSession().selectOne(getIbatisSqlMapNamespace()+".getByIdAndCode",map);
	}


	@Override
	public int updatePass(Integer orderDetailId, Integer pass) {
		HashMap<String,Object> map =new HashMap<String,Object>();
		map.put("orderDetailId", orderDetailId);
		map.put("pass", pass);
		return this.getSqlSession().update(getIbatisSqlMapNamespace()+".updatePass",map);
	}

}
