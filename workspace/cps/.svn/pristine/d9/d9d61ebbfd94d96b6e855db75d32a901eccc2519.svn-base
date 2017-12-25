package com.service.mystock;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dao.IBaseDao;
import com.dao.mystock.MystockDao;
import com.model.PageModel;
import com.model.mystock.Mystock;
import com.service.IBaseManagerImpl;
@Service("mystockManager")
@Transactional
public class MystockManagerImpl extends IBaseManagerImpl<Mystock, java.lang.Integer> implements MystockManager {
	
	@Autowired
	private MystockDao mystockDao;
	@Override
	protected IBaseDao<Mystock, Integer> getEntityDao() {
		return this.mystockDao;
	}
	@Override
	public List<Mystock> execlBySql(String where, Object[] queryParams,Map<String, String> orderby){
		return this.mystockDao.execlBySql(where, queryParams, orderby);
	}
	
	@Override
	public PageModel<Mystock> findBySql(String where, Object[] queryParams,
			Map<String, String> orderby, int pageNo, int maxResult) {
		return this.mystockDao.findBySql(where, queryParams, orderby, pageNo, maxResult);
	}
	
	

}
