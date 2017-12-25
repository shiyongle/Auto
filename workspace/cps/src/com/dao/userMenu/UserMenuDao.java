package com.dao.userMenu;

import java.util.List;

import com.dao.IBaseDao;
import com.model.usermenu.UserMenu;

public interface UserMenuDao extends IBaseDao<UserMenu, java.lang.String>{

	public UserMenu getIdByInfo(String fuid,String fmenu);
	public List<UserMenu> getByUserId(String fid);
	public void saveOrupdateUserMenu(UserMenu userMenu,String type) throws Exception;
}
