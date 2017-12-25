package com.pc.controller.travelBus;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.org.rapid_framework.page.Page;

import com.pc.controller.BaseController;
import com.pc.dao.UserRole.IUserRoleDao;
import com.pc.dao.travelBus.ItravelBusDao;
import com.pc.model.CL_Protocol;
import com.pc.model.CL_TravelBus;
import com.pc.model.Util_UserOnline;
import com.pc.query.travelBus.TravelBusQuery;
import com.pc.util.JSONUtil;
import com.pc.util.ServerContext;

@Controller
public class TravelBusController extends BaseController{
	
	@Resource
	private ItravelBusDao travelBusDao;
	private TravelBusQuery travelBusQuery;
	@Resource 
	private IUserRoleDao userRoleDao;
	
	protected static final String LIST_JSP= "/pages/pc/travelBus/travelBus.jsp";
	protected static final String ADDTRAVELBUS_JSP= "/pages/pc/travelBus/travelBusAdd.jsp";
	protected static final String EDITTRAVELBUS_JSP= "/pages/pc/travelBus/travelBusEdit.jsp";
	
	@RequestMapping("/travelBus/list")
	public String list(HttpServletRequest request,HttpServletResponse reponse) throws Exception{
		return LIST_JSP;
	}
	
	@RequestMapping("/travelBus/travelBusAdd")
	public String travelBusAdd(HttpServletRequest request,HttpServletResponse reponse) throws Exception{
		return ADDTRAVELBUS_JSP;
	}
	
	@RequestMapping("/travelBus/travelBusEdit")
	public String travelBusEdit(HttpServletRequest request,HttpServletResponse reponse,Integer id) throws Exception{
		CL_TravelBus travelBus =travelBusDao.getById(id);
		request.setAttribute("travelBus", travelBus);
		return EDITTRAVELBUS_JSP;
	}
	
	/*** 加载班车订单信息*/
	@RequestMapping("/travelBus/load")
	public String load(HttpServletRequest request,HttpServletResponse reponse) throws Exception{
		String pageNum = request.getParameter("page");
		String pageSize = request.getParameter("rows");
		String seachKey =request.getParameter("userName");
		if (travelBusQuery == null) {
			travelBusQuery = newQuery(TravelBusQuery.class, null);
		}
		if (pageNum != null) {
			travelBusQuery.setPageNumber(Integer.parseInt(pageNum));
		}
		if (pageSize != null) {
			travelBusQuery.setPageSize(Integer.parseInt(pageSize));
		}
		travelBusQuery.setSearchKey(null);
		if(seachKey !=null && !seachKey.equals("请选择")){
			travelBusQuery.setSearchKey(seachKey);
		}
		Page<CL_TravelBus> page = travelBusDao.findPage(travelBusQuery);
		HashMap<String, Object> m = new HashMap<String, Object>();
			m.put("total", page.getTotalCount());
			m.put("rows", page.getResult());
		return writeAjaxResponse(reponse, JSONUtil.getJson(m));
	}
	
	
	/***增加班车订单信息*/
	@RequestMapping("/travelBus/saveTravelBus")
	public String saveTravelBus(HttpServletRequest request,HttpServletResponse response) throws Exception{
		Integer userroleId;
		HashMap<String, Object> map = new HashMap<String, Object>();
		if(!ServerContext.getUseronline().containsKey(request.getSession().getId().toString())){
	    	map.put("success", "false");
			map.put("msg","未登录！");
			return writeAjaxResponse(response, JSONUtil.getJson(map));
	    }
	    Util_UserOnline userInfo=ServerContext.getUseronline().get(request.getSession().getId().toString());
	    userroleId =userInfo.getFuserId();
	    
		JSONObject jsonobject = JSONObject.fromObject(request.getParameterMap());
		String a = jsonobject.toString().replace("[","").replace("]","");
		CL_TravelBus travelBus = (CL_TravelBus) JSONObject.toBean(JSONObject.fromObject(a), CL_TravelBus.class);
		DateFormat f = new SimpleDateFormat("yyyy-MM-dd");
		travelBus.setFbizDate(f.parse(JSONObject.fromObject(a).getString("fbizDate")));
//		travelBus.setFcreateTime(f.parse(JSONObject.fromObject(a).getString("fcreateTime").toString()));
		travelBus.setFcreateTime(new Date());
		travelBus.setFcreator(userroleId);
		travelBusDao.save(travelBus);
		return writeAjaxResponse(response, "success");
	}

	
	/***修改班车订单信息*/
	@RequestMapping("/travelBus/updateTravelBus")
	public String updateTravelBus(HttpServletRequest request,HttpServletResponse response) throws Exception{
		Integer userroleId;
		HashMap<String, Object> map = new HashMap<String, Object>();
		if(!ServerContext.getUseronline().containsKey(request.getSession().getId().toString())){
	    	map.put("success", "false");
			map.put("msg","未登录！");
			return writeAjaxResponse(response, JSONUtil.getJson(map));
	    }
	    Util_UserOnline userInfo=ServerContext.getUseronline().get(request.getSession().getId().toString());
	    userroleId =userInfo.getFuserId();
	    
		JSONObject jsonobject = JSONObject.fromObject(request.getParameterMap());
		String a = jsonobject.toString().replace("[","").replace("]","");
		CL_TravelBus travelBus = (CL_TravelBus) JSONObject.toBean(JSONObject.fromObject(a), CL_TravelBus.class);
		DateFormat f = new SimpleDateFormat("yyyy-MM-dd");
		travelBus.setFbizDate(f.parse(JSONObject.fromObject(a).getString("fbizDate")));
//		travelBus.setFcreateTime(f.parse(JSONObject.fromObject(a).getString("fcreateTime").toString()));
//		travelBus.setFcreateTime(new Date());
//		travelBus.setFcreator(userroleId);
		travelBusDao.update(travelBus);
		return writeAjaxResponse(response, "success");
	
	}
	

	// 删除功能
	@RequestMapping("/travelBus/del")
	@Transactional(propagation = Propagation.REQUIRED)
	public String del(HttpServletRequest request, HttpServletResponse response,
			Integer[] ids) throws Exception {
		Integer userroleId;
		HashMap<String, Object> map = new HashMap<String, Object>();
		if(!ServerContext.getUseronline().containsKey(request.getSession().getId().toString())){
	    	map.put("success", "false");
			map.put("msg","未登录！");
			return writeAjaxResponse(response, JSONUtil.getJson(map));
	    }
		Util_UserOnline userInfo=ServerContext.getUseronline().get(request.getSession().getId().toString());
	    userroleId =userInfo.getFuserId();
		
		for (Integer id : ids) {
//			CL_TravelBus travelBus=this.travelBusDao.getById(id);
			this.travelBusDao.deleteByIdAndUser(id,userroleId);
		}
		return writeAjaxResponse(response, "success");
	}
	
}
