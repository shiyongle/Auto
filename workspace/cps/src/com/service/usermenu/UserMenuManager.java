package com.service.usermenu;

import java.util.List;

import com.model.usermenu.UserMenu;
import com.service.IBaseManager;

public interface UserMenuManager  extends IBaseManager<UserMenu, java.lang.String> {
	public UserMenu getIdByInfo(String fuid,String fmenu);
	public List<UserMenu> getByUserId(String fid);
	public void saveOrupdateUserMenu(UserMenu userMenu,String type);
}
