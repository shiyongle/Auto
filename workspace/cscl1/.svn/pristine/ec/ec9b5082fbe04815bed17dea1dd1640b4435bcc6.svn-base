package com.pc.controller.menu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.org.rapid_framework.page.Page;

import com.pc.controller.BaseController;
import com.pc.dao.menu.IMenuDao;
import com.pc.dao.menupermission.IMenuPermissionDao;
import com.pc.model.CL_MenuItem;
import com.pc.model.CL_MenuPermission;
import com.pc.model.MenuTree;
import com.pc.model.Util_Option;
import com.pc.model.MenuTree.Attributes;
import com.pc.query.menuitem.MenuItemQuery;
import com.pc.query.menupermission.MenuPermissionQuery;
import com.pc.util.JSONUtil;

@Controller
public class MenuController extends BaseController {
	protected static final String LIST_JSP= "/pages/pc/menuManage/menuManage_list.jsp";
	protected static final String EDIT_JSP="/pages/pc/menuManage/edit_menu.jsp";
	protected static final String CREATE_JSP="/pages/pc/menuManage/create_menu.jsp";
	protected static final String USER_ROOT_JSP="/pages/pc/user/user_root.jsp";
	
	@Resource
	private IMenuDao menuDao;
	@Resource
	private IMenuPermissionDao menuPermissionDao;
	//打开列表
	@RequestMapping("/menu/list")
	public String list(HttpServletRequest request, HttpServletResponse reponse)
			throws Exception {
		return LIST_JSP;
	}
	
	//加载列表数据
	@RequestMapping("/menu/load")
	public String load(HttpServletRequest request,HttpServletResponse reponse,MenuItemQuery menuquery) throws Exception{
		String pageNum = request.getParameter("page");
		String pageSize = request.getParameter("rows");
		if (menuquery == null) {
			menuquery = newQuery(MenuItemQuery.class, null);
		}
		if (pageNum != null) {
			menuquery.setPageNumber(Integer.parseInt(pageNum));
		}
		if (pageSize != null) {
			menuquery.setPageSize(Integer.parseInt(pageSize));
		}
//		menuquery.setSortColumns(" fisleaf,fid desc ");
		Page<CL_MenuItem> page = menuDao.findPage(menuquery);
		List<CL_MenuItem> list = page.getResult();
		CL_MenuItem menu = null;
		for(int i=0;i<list.size();i++){
			menu = list.get(i);
			if(menu.getFparentid()!=-1){				
				menu.setFparentMenuName(menuDao.getById(menu.getFparentid()).getFname());
			}
		}
		HashMap<String, Object> m = new HashMap<String, Object>();
			m.put("total", page.getTotalCount());
			m.put("rows", page.getResult());
		return writeAjaxResponse(reponse, JSONUtil.getJson(m));
	}
	
	//打开新增页面
	@RequestMapping("/menu/add")
	public String add(HttpServletRequest request, HttpServletResponse reponse)
			throws Exception {
		return CREATE_JSP;
	}
	//新增功能
	@RequestMapping("/menu/save")
	@Transactional
	public String save(HttpServletRequest request, HttpServletResponse reponse,CL_MenuItem menu)
			throws Exception {
		menuDao.save(menu);
		return writeAjaxResponse(reponse, "success");
	}
	
	// 编辑列表
	@RequestMapping("/menu/edit")
	public String edit(HttpServletRequest request, HttpServletResponse reponse,
			Integer id) throws Exception {
		CL_MenuItem menu = this.menuDao.getById(id);
		request.setAttribute("menu", menu);
		return EDIT_JSP;
	}
	
	//新增功能
	@RequestMapping("/menu/update")
	@Transactional
	public String update(HttpServletRequest request, HttpServletResponse reponse,CL_MenuItem menu)
			throws Exception {
		menuDao.update(menu);
		return writeAjaxResponse(reponse, "success");
	}
	
	
	//删除
	@RequestMapping("/menu/del")
	@Transactional(propagation = Propagation.REQUIRED)
	public String del(HttpServletRequest request, HttpServletResponse reponse,Integer[] ids)
			throws Exception {
		for (Integer id : ids) {
			this.menuDao.deleteById(id);
		}
		return writeAjaxResponse(reponse, "success");
	}
	
	//打开菜单分配列表
	@RequestMapping("/menu/userroot")
	public String userroot(HttpServletRequest request, HttpServletResponse reponse)
			throws Exception {
		request.setAttribute("fuserRoleId", request.getParameter("fuserRoleId"));
		return USER_ROOT_JSP;
	}
	
	//分配菜单
	@RequestMapping("/menu/allotmenu")
	public String allotmenu(HttpServletRequest request, HttpServletResponse reponse) throws Exception {
		MenuPermissionQuery mpquery = new MenuPermissionQuery();
		int fparentid = Integer.parseInt(request.getParameter("fmenuitemId"));
		int urid = Integer.parseInt(request.getParameter("fuserRoleId"));
		mpquery.setFuserRoleId(urid);
		List<CL_MenuItem>  childlist = menuDao.getListbyParentid(fparentid);
		//分配一级菜单则直接分配全部二级菜单
		if(childlist.size()>0){
			for(CL_MenuItem item:childlist){
				mpquery.setFmenuitemId(item.getFid());
				//校验菜单是否分配过
				if(menuPermissionDao.find(mpquery).size()==0){
					CL_MenuPermission mpinfo = new CL_MenuPermission();
					mpinfo.setFmenuitemId(item.getFid());
					mpinfo.setFuserRoleId(urid);
					mpinfo.setFremark("分配人ID"+request.getSession().getAttribute("userRoleId").toString());
					menuPermissionDao.save(mpinfo);
				}
				
			}
		}
		else{
			mpquery.setFmenuitemId(fparentid);
			//校验菜单是否分配过
			if(menuPermissionDao.find(mpquery).size()==0){
				CL_MenuPermission mpinfo = new CL_MenuPermission();
				//没有则说明本身就是二级菜单,直接分配
				mpinfo.setFmenuitemId(fparentid);
				mpinfo.setFuserRoleId(urid);
				mpinfo.setFremark("分配人ID"+request.getSession().getAttribute("userRoleId").toString());
				menuPermissionDao.save(mpinfo);	
			}
		}
		return writeAjaxResponse(reponse, "success");
	}
	
	//取消菜单分配
	@RequestMapping("/menu/unallotmenu")
	public String unallotmenu(HttpServletRequest request, HttpServletResponse reponse) throws Exception {
		MenuPermissionQuery mpquery = new MenuPermissionQuery();
		int fparentid = Integer.parseInt(request.getParameter("fmenuitemId"));
		int urid = Integer.parseInt(request.getParameter("fuserRoleId"));
		mpquery.setFuserRoleId(urid);
		List<CL_MenuItem>  childlist = menuDao.getListbyParentid(fparentid);
		//取消分配一级菜单则直接取消分配全部二级菜单
		if(childlist.size()>0){
			for(CL_MenuItem item:childlist){
				mpquery.setFmenuitemId(item.getFid());
				//取消菜单分配记录
				if(menuPermissionDao.find(mpquery).size()>0){
					menuPermissionDao.deleteById(menuPermissionDao.find(mpquery).get(0).getFid());
				}
				
			}
		}
		else{
			mpquery.setFmenuitemId(fparentid);
			//取消菜单分配记录
			if(menuPermissionDao.find(mpquery).size()>0){
				menuPermissionDao.deleteById(menuPermissionDao.find(mpquery).get(0).getFid());
			}
		}
		return writeAjaxResponse(reponse, "success");
	}
	
	//获取所有一级菜单
	@RequestMapping("/menu/getAllParentMenus")
	public String getAllParentMenus(HttpServletRequest request,HttpServletResponse reponse) throws Exception{		
		List<Util_Option> options = new ArrayList<Util_Option>();
		List<CL_MenuItem> parentmenu = menuDao.getAllParentMenus();
		CL_MenuItem item  = null;
		for(int i=0;i<parentmenu.size();i++){
			if(i==0){
				Util_Option o = new Util_Option();
				o.setOptionName("请选择");
				o.setOptionId(-1);
				o.setOptionVal("请选择");
				options.add(0, o);
			}
			 item = parentmenu.get(i);
			 Util_Option o = new Util_Option();
			 o.setOptionId(item.getFid());
			 o.setOptionName(item.getFname());
			 options.add(o);
		}
		return writeAjaxResponse(reponse,JSONUtil.getJson(options));
	}
	
	//当前用户已(未)分配的菜单权限
	@RequestMapping("/menu/getMenuList")
	public String getMenuList(HttpServletRequest request,HttpServletResponse reponse,MenuItemQuery miquery) throws Exception{
		HashMap map = new HashMap();
		//所有菜单
		List<CL_MenuItem> alltreelist = menuDao.getAllMenuInfo();
		
		//urid为空  则直接取登录的用户 进行首页菜单动态加载
		int urid = 0;
		if(request.getParameter("urid")==null){
			urid = Integer.parseInt(request.getSession().getAttribute("userRoleId").toString());
		}
		else{
			urid = Integer.parseInt(request.getParameter("urid"));
		}
		List<CL_MenuItem> allotlist = menuDao.getAllotPerssion(urid);
		//整个菜单树木
		List<MenuTree> treeList = new ArrayList();			
		//构建一级菜单树
		if(alltreelist.size()>0){
			for(CL_MenuItem item:alltreelist){
				MenuTree tree = new MenuTree();
				tree.setId(item.getFid());
				tree.setState("true");
				tree.setText(item.getFname());
				//一级菜单
				if(item.getFparentid()<0){
					treeList.add(tree);
				}
			}
		}
		//构建未分配权限的菜单树
		if(request.getParameter("notmymenu")!=null){
			/*  -----------------------用户菜单表中只保存二级菜单记录---------------------------------*/	
		    //双重循环比较此菜单是否已经分配给当前用户
			for(CL_MenuItem item:alltreelist){
				boolean notmymenu = true;
				for(CL_MenuItem myitem:  allotlist){
					if(item.getFid()==myitem.getFid()){
						notmymenu = false;
					}
				}
				//当前用户没有分配到这个菜单,
				if(notmymenu){
					//循环一级菜单,找到未分配二级菜单的一级菜单并添加
					for(MenuTree menutree:treeList){		
						System.out.println("父--------------------------"+menutree.getId());
						System.out.println("子父------------------------"+item.getFparentid());
						if(menutree.getId()==item.getFparentid()){
							MenuTree tree = new MenuTree();
							tree.setId(item.getFid());
							tree.setState("true");
							tree.setText(item.getFname());
							//二级菜单的url
							tree.setAttributes(menutree.new Attributes(item.getFurl()));
							//二级菜单添加到一级菜单中
							if(menutree.getChildren()==null){
								List<MenuTree> childtree = new ArrayList();
								childtree.add(tree);
								menutree.setChildren(childtree);
							}else{
								menutree.getChildren().add(tree);		
							}
							
						}	
					}
				}
			}
		}
		//构建未分配权限的菜单树
		else{
			//allotlist已经分配的菜单
			for(CL_MenuItem myitem:  allotlist){
				for(MenuTree menutree:treeList){	
					if(menutree.getId()==myitem.getFparentid()){
						MenuTree tree = new MenuTree();
						tree.setId(myitem.getFid());
						tree.setState("true");
						tree.setText(myitem.getFname());
						//二级菜单的url
						tree.setAttributes(menutree.new Attributes(myitem.getFurl()));
						//二级菜单添加到一级菜单中
						if(menutree.getChildren()==null){
							List<MenuTree> childtree = new ArrayList();
							childtree.add(tree);
							menutree.setChildren(childtree);
						}else{
							menutree.getChildren().add(tree);		
						}
					}
				}
			}
		}
		return writeAjaxResponse(reponse, JSONUtil.getJson(treeList));
	}
}
