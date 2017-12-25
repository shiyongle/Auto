package com.pc.controller.orderCarDetail;

import java.util.HashMap;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.org.rapid_framework.page.Page;

import com.pc.controller.BaseController;
import com.pc.dao.orderCarDetail.IorderCarDetailDao;
import com.pc.model.CL_OrderCarDetail;
import com.pc.query.orderCarDetail.CL_OrderCarDetailQuery;
import com.pc.util.JSONUtil;

@Controller
public class OrderCarDetailController extends BaseController{
	
	@Resource
	private IorderCarDetailDao orderCarDetailDao;

	protected static final String LIST_JSP= "/pages/pc/orderCarDetail/orderCarDetail_list.jsp";
	
	@RequestMapping("/orderCarDetail/list")
	public String list(HttpServletRequest request,HttpServletResponse reponse) throws Exception{
		String orderId =request.getParameter("orderId");
		request.setAttribute("orderId", orderId);
		return LIST_JSP;
	}
	
	/*** 加载订单信息*/
	@RequestMapping("/orderCarDetail/load")
	public String load(HttpServletRequest request,HttpServletResponse reponse,@ModelAttribute CL_OrderCarDetailQuery orderCarDetailQuery) throws Exception{
		String pageNum = request.getParameter("page");
		String pageSize = request.getParameter("rows");
		if (orderCarDetailQuery == null) {
			orderCarDetailQuery = newQuery(CL_OrderCarDetailQuery.class, null);
		}
		if (pageNum != null) {
			orderCarDetailQuery.setPageNumber(Integer.parseInt(pageNum));
		}
		if (pageSize != null) {
			orderCarDetailQuery.setPageSize(Integer.parseInt(pageSize));
		}
		System.out.println(request.getParameter("orderId"));
		orderCarDetailQuery.setOrderId(Integer.parseInt(request.getParameter("orderId")));
		Page<CL_OrderCarDetail> page = orderCarDetailDao.findPage(orderCarDetailQuery);
		HashMap<String, Object> m = new HashMap<String, Object>();
			m.put("total", page.getTotalCount());
			m.put("rows", page.getResult());
		return writeAjaxResponse(reponse, JSONUtil.getJson(m));
	}
}
