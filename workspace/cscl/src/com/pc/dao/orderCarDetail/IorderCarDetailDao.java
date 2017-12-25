package com.pc.dao.orderCarDetail;

import java.util.List;

import com.pc.dao.aBase.IBaseDao;
import com.pc.model.CL_OrderCarDetail;

public interface IorderCarDetailDao extends IBaseDao<CL_OrderCarDetail, java.lang.Integer> {
	/**根据订单ID查询订单车型明细表所有车型ID*/
	public List<CL_OrderCarDetail> getByOrderId(Integer OrderId);
}
