package com.pc.dao.orderCarDetail.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.pc.dao.aBase.impl.BaseDao;
import com.pc.dao.orderCarDetail.IorderCarDetailDao;
import com.pc.model.CL_OrderCarDetail;

@Service("orderCarDetailDao")
public class OrderCarDetailDao extends BaseDao<CL_OrderCarDetail, java.lang.Integer> implements IorderCarDetailDao {

	@Override
	public String getIbatisSqlMapNamespace() {
		return "CL_OrderCarDetail";
	}

	@Override
	public List<CL_OrderCarDetail> getByOrderId(Integer OrderId) {
		// TODO Auto-generated method stub
		return this.getSqlSession().selectList(getIbatisSqlMapNamespace()+".getByOrderId",OrderId);
	}

}
