package com.service.menu;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dao.IBaseDao;
import com.dao.menu.MenuDao;
import com.model.menu.Menu;
import com.service.IBaseManagerImpl;

@Service("menuManager")
@Transactional
public class MenuManagerImpl extends IBaseManagerImpl<Menu, java.lang.String> implements MenuManager {

	@Autowired
	private MenuDao menuDao;
	
	@Override
	public List<Menu> findAll() {
		return this.menuDao.findAll();
	}

	@Override
	protected IBaseDao<Menu, String> getEntityDao() {
		return this.menuDao;
	}

	@Override
	public Menu findById(String fid) {
		return this.menuDao.findById(fid);
	}
	@Override
	public List<Menu> findByDisId(String ids){
		return this.menuDao.findByDisId(ids);
	}

}
