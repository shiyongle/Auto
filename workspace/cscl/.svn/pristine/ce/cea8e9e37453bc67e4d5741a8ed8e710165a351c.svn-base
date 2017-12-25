package com.pc.controller.carLine;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.org.rapid_framework.page.Page;

import com.pc.controller.BaseController;
import com.pc.dao.UserRole.impl.UserRoleDao;
import com.pc.dao.carLine.impl.CarLineDao;
import com.pc.model.CL_CarLine;
import com.pc.query.carLine.CarLineQuery;
import com.pc.util.JSONUtil;
import com.pc.util.ServerContext;

@Controller
@RequestMapping("/carLine")
public class CarLineController extends BaseController{
	
	protected static final String LIST_JSP = "/pages/pc/carLine/carLine_list.jsp";
	
	protected static final String LIST_JSP2 = "/pages/pc/carLine/carLine_list2.jsp";
	
	protected static final String LIST_CANCEL = "/pages/pc/carLine/carLine_cancel.jsp";
	
	@Resource
	private CarLineDao carLineDao;
	
	@Resource
	private UserRoleDao userRoleDao;
	
	@RequestMapping("/view")
	public String view(HttpServletRequest request,HttpServletResponse response,Integer id){
		CL_CarLine carLine = carLineDao.getById(id);
		request.setAttribute("carLine", carLine);
		return LIST_CANCEL;
	}
	
	@RequestMapping("/list")
	public String list(HttpServletRequest request,HttpServletResponse response){
		return LIST_JSP;
	}
	
	@RequestMapping("/list2")
	public String list2(HttpServletRequest request,HttpServletResponse response){
		return LIST_JSP2;
	}
	
	@RequestMapping("/load")
	public String load(HttpServletRequest request,HttpServletResponse response,@ModelAttribute CarLineQuery carLineQuery){
		String pageNum = request.getParameter("page");
		String pageSize = request.getParameter("rows");
		if (carLineQuery == null) {
			carLineQuery = newQuery(CarLineQuery.class, null);
		}
		if (pageNum != null) {
			carLineQuery.setPageNumber(Integer.parseInt(pageNum));
		}
		if (pageSize != null) {
			carLineQuery.setPageSize(Integer.parseInt(pageSize));
		}
		if(carLineQuery.getFactory_id() != null && carLineQuery.getFactory_id() == -1){
			carLineQuery.setFactory_id(null);
		}
		Page<CL_CarLine> page = carLineDao.findPage(carLineQuery);
		HashMap<String, Object> m = new HashMap<String, Object>();
		m.put("total", page.getTotalCount());
		m.put("rows", page.getResult());
		return writeAjaxResponse(response, JSONUtil.getJson(m));
	}
	
	@RequestMapping("/load2")
	public String load2(HttpServletRequest request,HttpServletResponse response,@ModelAttribute CarLineQuery carLineQuery){
//		String activeArea,carNum;Integer fid,spec;
		String pageNum = request.getParameter("page");
		String pageSize = "500";
		if (carLineQuery == null) {
			carLineQuery = newQuery(CarLineQuery.class, null);
		}
		if (pageNum != null) {
			carLineQuery.setPageNumber(Integer.parseInt(pageNum));
		}
		if (pageSize != null) {
			carLineQuery.setPageSize(Integer.parseInt(pageSize));
		}
		if(carLineQuery.getFactory_id() != null && carLineQuery.getFactory_id() == -1){
			carLineQuery.setFactory_id(null);
		}
		//分别查询每个区的排队情况-------------------PS:这代码他妈不是我想写的
		carLineQuery.setActiveArea(1);
		Page<CL_CarLine> page1 = carLineDao.findPage2(carLineQuery);
		int count1 = page1.getTotalCount();
		
		carLineQuery.setActiveArea(2);
		Page<CL_CarLine> page2 = carLineDao.findPage2(carLineQuery);
		int count2 = page2.getTotalCount();
		
		carLineQuery.setActiveArea(3);
		Page<CL_CarLine> page3 = carLineDao.findPage2(carLineQuery);
		int count3 = page3.getTotalCount();
		
		carLineQuery.setActiveArea(4);
		Page<CL_CarLine> page4 = carLineDao.findPage2(carLineQuery);
		int count4 = page4.getTotalCount();
		
		carLineQuery.setActiveArea(5);
		Page<CL_CarLine> page5 = carLineDao.findPage2(carLineQuery);
		int count5 = page5.getTotalCount();
		
		carLineQuery.setActiveArea(6);
		Page<CL_CarLine> page6 = carLineDao.findPage2(carLineQuery);
		int count6 = page6.getTotalCount();
		
		carLineQuery.setActiveArea(7);
		Page<CL_CarLine> page7 = carLineDao.findPage2(carLineQuery);
		int count7 = page7.getTotalCount();
		
		carLineQuery.setActiveArea(8);
		Page<CL_CarLine> page8 = carLineDao.findPage2(carLineQuery);
		int count8 = page8.getTotalCount();
		
		carLineQuery.setActiveArea(9);
		Page<CL_CarLine> page9 = carLineDao.findPage2(carLineQuery);
		int count9 = page9.getTotalCount();
		
		carLineQuery.setActiveArea(10);
		Page<CL_CarLine> page10 = carLineDao.findPage2(carLineQuery);
		int count10 = page10.getTotalCount();
		
		int arry[] = {count1,count2,count3,count4,count5,count6,count7,count8,count9,count10};
		int count = 0;
		for (int i = 0; i < arry.length; i++) {
			/*for (int j = i+1; j < arry.length; j++) {
				if(arry[i] > arry[j]){
					temp = arry[i];
					arry[i] = arry[j];
					arry[j] = temp;
				}
			}*/
			if(arry[i] > count){
				count = arry[i];
			}
		}
		//冒泡取最大的
//		int count = arry[arry.length-1];
		List<CL_CarLine> list = new ArrayList<>();
		HashMap<String, Object> m = new HashMap<String, Object>();
		for (int i = Integer.parseInt(pageSize)*(Integer.parseInt(pageNum)-1); i < count*Integer.parseInt(pageNum); i++) {
			CL_CarLine carLine = new CL_CarLine();
			CL_CarLine car1 = page1.getResult().size()>i?page1.getResult().get(i):null;
			CL_CarLine car2 = page2.getResult().size()>i?page2.getResult().get(i):null;
			CL_CarLine car3 = page3.getResult().size()>i?page3.getResult().get(i):null;
			CL_CarLine car4 = page4.getResult().size()>i?page4.getResult().get(i):null;
			CL_CarLine car5 = page5.getResult().size()>i?page5.getResult().get(i):null;
			CL_CarLine car6 = page6.getResult().size()>i?page6.getResult().get(i):null;
			CL_CarLine car7 = page7.getResult().size()>i?page7.getResult().get(i):null;
			CL_CarLine car8 = page8.getResult().size()>i?page8.getResult().get(i):null;
			CL_CarLine car9 = page9.getResult().size()>i?page9.getResult().get(i):null;
			CL_CarLine car10 = page10.getResult().size()>i?page10.getResult().get(i):null;
			if(car1 != null){
				carLine.set_1fcar_line_id(car1.getFcar_line_id());
				carLine.set_1carSpec(car1.getCarSpec());
				carLine.set_1carNum(car1.getcarNum());
			}
			if(car2 != null){
				carLine.set_2fcar_line_id(car2.getFcar_line_id());
				carLine.set_2carSpec(car2.getCarSpec());
				carLine.set_2carNum(car2.getcarNum());
			}
			if(car3 != null){
				carLine.set_3fcar_line_id(car3.getFcar_line_id());
				carLine.set_3carSpec(car3.getCarSpec());
				carLine.set_3carNum(car3.getcarNum());
			}
			if(car4 != null){
				carLine.set_4fcar_line_id(car4.getFcar_line_id());
				carLine.set_4carSpec(car4.getCarSpec());
				carLine.set_4carNum(car4.getcarNum());
			}
			if(car5 != null){
				carLine.set_5fcar_line_id(car5.getFcar_line_id());
				carLine.set_5carSpec(car5.getCarSpec());
				carLine.set_5carNum(car5.getcarNum());
			}
			if(car6 != null){
				carLine.set_6fcar_line_id(car6.getFcar_line_id());
				carLine.set_6carSpec(car6.getCarSpec());
				carLine.set_6carNum(car6.getcarNum());
			}
			if(car7 != null){
				carLine.set_7fcar_line_id(car7.getFcar_line_id());
				carLine.set_7carSpec(car7.getCarSpec());
				carLine.set_7carNum(car7.getcarNum());
			}
			if(car8 != null){
				carLine.set_8fcar_line_id(car8.getFcar_line_id());
				carLine.set_8carSpec(car8.getCarSpec());
				carLine.set_8carNum(car8.getcarNum());
			}
			if(car9 != null){
				carLine.set_9fcar_line_id(car9.getFcar_line_id());
				carLine.set_9carSpec(car9.getCarSpec());
				carLine.set_9carNum(car9.getcarNum());
			}
			if(car10 != null){
				carLine.set_10fcar_line_id(car10.getFcar_line_id());
				carLine.set_10carSpec(car10.getCarSpec());
				carLine.set_10carNum(car10.getcarNum());
			}
			list.add(carLine);
		}
		
		m.put("rows", list);
		m.put("total", count);
		return writeAjaxResponse(response, JSONUtil.getJson(m));
	}
	
	@RequestMapping("/send")
	public String send(HttpServletRequest request,HttpServletResponse response,Integer id){
		HashMap<String, Object> m = new HashMap<String, Object>();
		if (request.getSession().getId() == null
				|| request.getSession().getId().equals("")) {
			m.put("success", "false");
			m.put("msg", "未登录！");
			return writeAjaxResponse(response, JSONUtil.getJson(m));
		}
		String vmi_user_fid = ServerContext.getUseronline().get(request.getSession().getId()).getFuserid();
		int operator = userRoleDao.getByVmiUserFidAndRoleId(vmi_user_fid, 3).getId();
		CL_CarLine carLine = carLineDao.getById(id);
		if(carLine.getFstatus() != 1){
			m.put("success", "false");
			m.put("msg", "状态有误，找李政逸去！");
			return writeAjaxResponse(response, JSONUtil.getJson(m));
		}
		carLine.setFstatus(3);//派车
		carLine.setFoperator(operator);
		carLine.setFoperate_time(new Date());
		carLineDao.update(carLine);
		m.put("success", "success");
		m.put("msg", "派车成功！");
		return writeAjaxResponse(response, JSONUtil.getJson(m));
	}
	
	@RequestMapping("/cancel")
	public String cancel(HttpServletRequest request,HttpServletResponse response,Integer id) throws Exception{
		request.setCharacterEncoding("UTF-8");
		HashMap<String, Object> m = new HashMap<String, Object>();
		if (request.getSession().getId() == null
				|| request.getSession().getId().equals("")) {
			m.put("success", "false");
			m.put("msg", "未登录！");
			return writeAjaxResponse(response, JSONUtil.getJson(m));
		}
		String vmi_user_fid = ServerContext.getUseronline().get(request.getSession().getId()).getFuserid();
		int operator = userRoleDao.getByVmiUserFidAndRoleId(vmi_user_fid, 3).getId();
		CL_CarLine carLine = carLineDao.getById(id);
		if(carLine.getFstatus() != 1){
			m.put("success", "false");
			m.put("msg", "状态有误，找李政逸去！");
			return writeAjaxResponse(response, JSONUtil.getJson(m));
		}
		carLine.setFstatus(2);//取消
		String fremark = request.getParameter("fremark");
		fremark =new String(fremark.getBytes("ISO-8859-1"),"UTF-8");//转义乱码
		carLine.setFremark(fremark);
		carLine.setFoperator(operator);
		carLine.setFoperate_time(new Date());
		carLineDao.update(carLine);
		m.put("success", "success");
		m.put("msg", "取消成功！");
		return writeAjaxResponse(response, JSONUtil.getJson(m));
	}
	
	
}
