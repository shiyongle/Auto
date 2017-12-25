package com.service.orderState;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dao.IBaseDao;
import com.dao.mystock.MystockDao;
import com.dao.orderState.OrderStateDao;
import com.model.PageModel;
import com.model.mystock.Mystock;
import com.model.orderState.OrderState;
import com.service.IBaseManagerImpl;
@Service("orderStateManager")
@Transactional
public class OrderStateManagerImpl extends IBaseManagerImpl<OrderState, java.lang.Integer> implements OrderStateManager {
	
	@Autowired
	private OrderStateDao orderstateDao;
	@Override
	protected IBaseDao<OrderState, Integer> getEntityDao() {
		return this.orderstateDao;
	}
	@Override
	public List<OrderState> getOrderStateInfo(String fid){
		return this.orderstateDao.getOrderStateInfo(fid);
	}
	

}
