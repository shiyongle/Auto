package com.service.designschemes;

import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.dao.IBaseDao;
import com.dao.designschemes.DesignSchemesDao;
import com.model.PageModel;
import com.model.designschemes.Designschemes;
import com.service.IBaseManagerImpl;

@Service("designSchemesManager")
@Transactional(rollbackFor = Exception.class)
public class DesignSchemesManagerImpl extends IBaseManagerImpl<Designschemes,java.lang.String> implements DesignSchemesManager {
	@Autowired
	private DesignSchemesDao designSchemesDao;
	@Override
	public PageModel<Designschemes> findBySql(String where, Object[] queryParams, Map<String, String> orderby, int pageNo, int maxResult) {
		return this.designSchemesDao.findBySql(where, queryParams, orderby, pageNo, maxResult);
	}

	@Override
	protected IBaseDao<Designschemes, String> getEntityDao() {
		return this.designSchemesDao;
	}

	@Override
	public PageModel<Designschemes> findAllschemeSql(String where,Object[] queryParams, Map<String, String> orderby, int pageNo, int maxResult) {
		// TODO Auto-generated method stub
		return this.designSchemesDao.findAllschemeSql(where, queryParams, orderby, pageNo, maxResult);
	}

	@Override
	public void deleteScheme(String id) {
		this.designSchemesDao.deleteScheme(id);
	}

	@Override
	public void updateshelves(String id) {
		this.designSchemesDao.updateshelves(id);
	}
	
	@Override
	public void updateImgDesc(HashMap<String,String> imgmap) {
		this.designSchemesDao.updateImgDesc(imgmap);
	}
}
