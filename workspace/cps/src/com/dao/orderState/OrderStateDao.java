package com.dao.orderState;

import java.util.List;
import java.util.Map;

import com.dao.IBaseDao;
import com.model.PageModel;
import com.model.mystock.Mystock;
import com.model.orderState.OrderState;

public interface OrderStateDao extends IBaseDao<OrderState, java.lang.Integer> {
	
	public List<OrderState> getOrderStateInfo(String fid);
	
}
