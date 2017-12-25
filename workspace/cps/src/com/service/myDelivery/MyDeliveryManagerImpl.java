package com.service.myDelivery;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dao.IBaseDao;
import com.dao.myDelivery.MyDeliveryDao;
import com.model.PageModel;
import com.model.myDelivery.MyDelivery;
import com.service.IBaseManagerImpl;
@Service("myDeliveryManager")
@Transactional
public class MyDeliveryManagerImpl extends IBaseManagerImpl<MyDelivery, java.lang.Integer> implements MyDeliveryManager {
	
	@Autowired
	private MyDeliveryDao myDeliveryDao;
	@Override
	protected IBaseDao<MyDelivery, Integer> getEntityDao() {
		return this.myDeliveryDao;
	}
	@Override
	public PageModel<MyDelivery> findBySql(String where1,String where2,Object[] queryParams, Map<String, String> orderby, int pageNo,int maxResult) {
		return this.myDeliveryDao.findBySql(where1,where2, queryParams, orderby, pageNo, maxResult);
	}
	@Override
	public List<MyDelivery> getLine(String where, Object[] queryParams,Map<String, String> orderby) {
		return this.myDeliveryDao.getLine(where, queryParams, orderby);
	}
	@Override
	public List<MyDelivery> execlBySql(String where1, String where2,Object[] queryParams, Map<String, String> orderby) {
		return this.myDeliveryDao.execlBySql(where1, where2, queryParams, orderby);
	}
	@Override
	public PageModel<MyDelivery> findBySql(String where, Object[] queryParams,Map<String, String> orderby, int pageNo, int maxResult) {
		return this.myDeliveryDao.findBySql(where, queryParams, orderby, pageNo, maxResult);
	}	

}
