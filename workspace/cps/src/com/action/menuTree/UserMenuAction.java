package com.action.menuTree;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import com.action.BaseAction;
import com.model.menu.Menu;
import com.model.usermenu.UserMenu;
import com.service.menu.MenuManager;
import com.service.usermenu.UserMenuManager;
import com.util.Constant;
import com.util.JSONUtil;

@SuppressWarnings("serial")
public class UserMenuAction extends BaseAction {

	protected static final String USERMENU_JSP   = "/pages/board/mymenu.jsp";
	
	private Menu menu=new Menu();
	private UserMenu userMenu=new UserMenu();
	@Autowired
	private UserMenuManager usermenuManager;
	@Autowired
	private MenuManager menuManager;
	
	private List<UserMenu> usermenulist=new ArrayList<UserMenu>();
	private List<Menu> menulist=new ArrayList<Menu>();
	private List<Menu> menudislist=new ArrayList<Menu>();
	public String list()
	{
		String userid=getRequest().getSession().getAttribute(Constant.SESSION_USERID).toString();
		usermenulist=this.usermenuManager.getByUserId(userid);//根据用户id查询配置表中是否有数据
		if(usermenulist==null)//初始配置
		{
			menulist=menuManager.findAll();
		}
		else//配置表中有数据,即禁用的菜单
		{
			//Usermenu中有数据，配置禁用的菜单
			for (UserMenu umenu : usermenulist) {
				menudislist.add(menuManager.findById(umenu.getFmenu()));//禁用的菜单
				menulist=menuManager.findByDisId(umenu.getFuid());//启用的菜单
			}
		}
		JSONUtil.getJson(menudislist);
		return USERMENU_JSP;
	}
	
	public String getUserMenuList()
	{
		String userid=getRequest().getSession().getAttribute(Constant.SESSION_USERID).toString();
		usermenulist=this.usermenuManager.getByUserId(userid);//根据用户id查询配置表中是否有数据
		if(usermenulist==null)//初始配置
		{
			menulist=menuManager.findAll();
		}
		else//配置表中有数据,即禁用的菜单
		{
			//Usermenu中有数据，配置禁用的菜单
			for (UserMenu umenu : usermenulist) {
				menudislist.add(menuManager.findById(umenu.getFmenu()));//禁用的菜单
				menulist=menuManager.findByDisId(umenu.getFuid());//启用的菜单
			}
		}
		HashMap<String,Object> map =new HashMap<String,Object>();
		map.put("menudislist", menudislist);
		map.put("menulist", menulist);
		return writeAjaxResponse(JSONUtil.getJson(map));
	}
	
	
	public String saveOrUpdate()
	{
		String userId =getRequest().getSession().getAttribute(Constant.SESSION_USERID).toString();
		String datas=getRequest().getParameter("datas");
		String type=getRequest().getParameter("type");
		String[] fmid=datas.split(",");
		for (String fmenu : fmid) {
			userMenu=new UserMenu(userId, fmenu);
			if("disable".equals(type))
			{
				usermenuManager.saveOrupdateUserMenu(userMenu,type);
			}else
			{
				usermenuManager.saveOrupdateUserMenu(usermenuManager.getIdByInfo(userId, fmenu),type);
			}
		}
		return writeAjaxResponse("success");
	}

	public UserMenu getUserMenu() {
		return userMenu;
	}

	public void setUserMenu(UserMenu userMenu) {
		this.userMenu = userMenu;
	}

	public List<UserMenu> getUsermenulist() {
		return usermenulist;
	}

	public void setUsermenulist(List<UserMenu> usermenulist) {
		this.usermenulist = usermenulist;
	}

	public MenuManager getMenuManager() {
		return menuManager;
	}

	public void setMenuManager(MenuManager menuManager) {
		this.menuManager = menuManager;
	}

	public List<Menu> getMenulist() {
		return menulist;
	}

	public void setMenulist(List<Menu> menulist) {
		this.menulist = menulist;
	}

	public Menu getMenu() {
		return menu;
	}

	public void setMenu(Menu menu) {
		this.menu = menu;
	}

	public List<Menu> getMenudislist() {
		return menudislist;
	}

	public void setMenudislist(List<Menu> menudislist) {
		this.menudislist = menudislist;
	}
	
	
}
