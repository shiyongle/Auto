package com.service.usermenu;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dao.IBaseDao;
import com.dao.userMenu.UserMenuDao;
import com.model.usermenu.UserMenu;
import com.service.IBaseManagerImpl;

@Service("usermenuManager")
@Transactional
public class UserMenuManagerImpl extends IBaseManagerImpl<UserMenu, java.lang.String> implements UserMenuManager {

	@Autowired
	private UserMenuDao usermenuDao;
	
	@Override
	public UserMenu getIdByInfo(String fuid,String fmenu) {
		return this.usermenuDao.getIdByInfo(fuid,fmenu);
	}

	@Override
	public List<UserMenu> getByUserId(String fid) {
		return this.usermenuDao.getByUserId(fid);
	}
	@Override
	public void saveOrupdateUserMenu(UserMenu userMenu,String type) {
		try {
			this.usermenuDao.saveOrupdateUserMenu(userMenu,type);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	protected IBaseDao<UserMenu, String> getEntityDao() {
		return this.usermenuDao;
	}
}
