package com.pc.dao.menu;

import java.util.List;

import com.pc.dao.aBase.IBaseDao;
import com.pc.model.CL_MenuItem;


public interface IMenuDao extends IBaseDao<CL_MenuItem,java.lang.Integer>{ 
	/**根据用户urId 获取已分配的权限*/
	public List<CL_MenuItem> getAllotPerssion(Integer urId);
	/**获取所有菜单数据*/
	public List<CL_MenuItem> getAllMenuInfo();
	/**获取所有一级菜单*/
	public List<CL_MenuItem> getAllParentMenus();
	/**根据一级菜单得到二级菜单*/
	public List<CL_MenuItem> getListbyParentid(int fparentid);
	
}
