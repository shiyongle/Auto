package com.pc.controller.factory;

import java.util.Date;
import java.util.HashMap;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.org.rapid_framework.page.Page;

import com.pc.controller.BaseController;
import com.pc.dao.UserRole.impl.UserRoleDao;
import com.pc.dao.factory.impl.FactoryDao;
import com.pc.model.CL_Factory;
import com.pc.model.CL_UserRole;
import com.pc.query.factory.FactoryQuery;
import com.pc.util.JSONUtil;
import com.pc.util.ServerContext;

@Controller
public class FactoryController extends BaseController {
	
	protected static final String LIST_JSP = "/pages/pc/factory/factory_list.jsp";
	protected static final String CREATE_JSP = "/pages/pc/factory/factory_create.jsp";
	protected static final String EDIT_JSP = "/pages/pc/factory/factory_edit.jsp";
	protected static final String view_JSP = "/pages/pc/factory/factory_view.jsp";
	
	@Resource
	private FactoryDao factoryDao;
	
	@Resource
	private UserRoleDao userRoleDao;
	
	@RequestMapping("/factory/list")
	public String list(HttpServletRequest request,HttpServletResponse response){
		return LIST_JSP;
	}
	
	@RequestMapping("/factory/create")
	public String create(HttpServletRequest request,HttpServletResponse response){
		return CREATE_JSP;
	}
	
	@RequestMapping("/factory/edit")
	public String edit(HttpServletRequest request,HttpServletResponse response,Integer id){
		CL_Factory factory = factoryDao.getById(id);
		request.setAttribute("factory", factory);
		return EDIT_JSP;
	}
	
	@RequestMapping("/factory/view")
	public String view(HttpServletRequest request,HttpServletResponse response,Integer id){
		CL_Factory factory = factoryDao.getById(id);
		request.setAttribute("factory", factory);
		return view_JSP;
	}
	
	@RequestMapping("/factory/load")
	public String load(HttpServletRequest request,HttpServletResponse response,@ModelAttribute FactoryQuery factoryQuery){
		String pageNum = request.getParameter("page");
		String pageSize = request.getParameter("rows");
		if (factoryQuery == null) {
			factoryQuery = newQuery(FactoryQuery.class, null);
		}
		if (pageNum != null) {
			factoryQuery.setPageNumber(Integer.parseInt(pageNum));
		}
		if (pageSize != null) {
			factoryQuery.setPageSize(Integer.parseInt(pageSize));
		}
		Page<CL_Factory> page = factoryDao.findPage(factoryQuery);
		HashMap<String, Object> m = new HashMap<String, Object>();
		m.put("total", page.getTotalCount());
		m.put("rows", page.getResult());
		return writeAjaxResponse(response, JSONUtil.getJson(m));
	}
	
	@RequestMapping("/factory/save")
	public String save(HttpServletRequest request,HttpServletResponse response,@ModelAttribute CL_Factory factory){
		HashMap<String, Object> m = new HashMap<>();
		String vmi_user_fid = ServerContext.getUseronline().get(request.getSession().getId()).getFuserid();
		CL_UserRole user = userRoleDao.getByVmiUserFidAndRoleId(vmi_user_fid, 3);
		if(user == null){
			m.put("success", "false");
			m.put("msg", "非我族类！");
		}
		factory.setFcreator(user.getId());
		factory.setFcreat_time(new Date());
		factory.setFeditor(user.getId());
		factoryDao.save(factory);
		m.put("success", "success");
		m.put("msg", "保存成功!");
		return writeAjaxResponse(response, JSONUtil.getJson(m));
	}
	
	@RequestMapping("/factory/update")
	public String update(HttpServletRequest request,HttpServletResponse response,@ModelAttribute CL_Factory factory){
		HashMap<String, Object> m = new HashMap<>();
		String vmi_user_fid = ServerContext.getUseronline().get(request.getSession().getId()).getFuserid();
		CL_UserRole user = userRoleDao.getByVmiUserFidAndRoleId(vmi_user_fid, 3);
		if(user == null){
			m.put("success", "false");
			m.put("msg", "非我族类！");
		}
		factory.setFeditor(user.getId());
		factoryDao.update(factory);
		m.put("success", "success");
		m.put("msg", "修改成功!");
		return writeAjaxResponse(response, JSONUtil.getJson(m));
	}
	
}
