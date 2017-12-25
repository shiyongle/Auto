/*package com.pc.controller.coupons;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.org.rapid_framework.page.Page;

import com.pc.controller.BaseController;
import com.pc.dao.coupons.ICouponsDao;
import com.pc.dao.couponsDetail.ICouponsDetailDao;
import com.pc.dao.message.ImessageDao;
import com.pc.dao.select.impl.UtilOptionDao;
import com.pc.model.CL_Coupons;
import com.pc.model.CL_CouponsDetail;
import com.pc.model.CL_Message;
import com.pc.model.Util_Option;
import com.pc.query.coupons.CL_CouponsQuery;
import com.pc.util.JSONUtil;
import com.pc.util.MD5Util;
@Controller
public class CouponsAssignController extends BaseController{
	protected static final String ASSIGN_LIST_JSP ="/pages/pc/coupons/assign_list.jsp";
	protected static final String ASSIGN_CREATE_JSP= "/pages/pc/coupons/assign_create.jsp";
	protected static final String ASSIGN_JSP= "/pages/pc/coupons/assign.jsp";
	@Resource
	private ICouponsDao couponsDao;
	@Resource
	private UtilOptionDao optionDao;
	@Resource
	private ImessageDao messageDao;
	@Resource
	private ICouponsDetailDao couponsDetailDao;
	
	
	*//***好运券指派管理*//*
	@RequestMapping("/couponsAssign/list")
	public String list(HttpServletRequest request,HttpServletResponse reponse) throws Exception{
		return ASSIGN_LIST_JSP;
	}
	
	*//***好运券指派管理*//*
	@RequestMapping("/couponsAssign/create")
	public String create(HttpServletRequest request,HttpServletResponse reponse) throws Exception{
		return ASSIGN_CREATE_JSP;
	}
	
	
	*//*** 好运券指派管理-加载列表信息*//*
	@RequestMapping("/couponsAssign/load")
	public String load(HttpServletRequest request,HttpServletResponse reponse,@ModelAttribute CL_CouponsQuery couponsQuery) throws Exception{
		String pageNum = request.getParameter("page");
		String pageSize = request.getParameter("rows");
		if (couponsQuery == null) {
			couponsQuery = newQuery(CL_CouponsQuery.class, null);
		}
		if (pageNum != null) {
			couponsQuery.setPageNumber(Integer.parseInt(pageNum));
		}
		if (pageSize != null) {
			couponsQuery.setPageSize(Integer.parseInt(pageSize));
		}
		HashMap<String, Object> m = new HashMap<String, Object>();
		couponsQuery.setType(3);
		Page<CL_Coupons> page = couponsDao.findPage(couponsQuery);
		m.put("total", page.getTotalCount());
		m.put("rows", page.getResult());
		return writeAjaxResponse(reponse, JSONUtil.getJson(m));
	}
	
	*//*** 表单提交日期绑定*//*
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");//写上你要的日期格式
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }
	*//***创建指派好运券保存方法*//*
	@RequestMapping("/couponsAssign/saveTwo")
	public String  saveOne(HttpServletRequest request,HttpServletResponse reponse,@ModelAttribute CL_Coupons coupons) throws Exception{
		HashMap<String, Object> m = new HashMap<>();
		SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
		String startTime = request.getParameter("startTime");
		Date start = date.parse(startTime);
		String now = date.format(new Date());
		if(start.before(date.parse(now))){
			m.put("success", "false");
			m.put("msg", "有效期不合逻辑");
			return writeAjaxResponse(reponse, JSONUtil.getJson(m));
		}
		if(coupons.getCompareDollars().compareTo(coupons.getDollars()) <= 0 || coupons.getDollars().compareTo(BigDecimal.ZERO) <= 0){
			return this.poClient(reponse, false, "金额不合逻辑!");
		}
		coupons.setIsEffective(1);//有效
		coupons.setIsOverdue(0);//未过期
		coupons.setType(3);//
		coupons.setCreator(Integer.parseInt(request.getSession().getAttribute("userRoleId").toString()));
		coupons.setCreateTime(new Date());
		coupons.setRedeemCode(MD5Util.getUUIDNumber());
		this.couponsDao.save(coupons);
		m.put("success", "success");
		m.put("msg", "操作成功");
		return writeAjaxResponse(reponse, JSONUtil.getJson(m));
	}
	
	*//***创建指派好运券进入界面*//*
	@RequestMapping("/couponsAssign/assign")
	public String  assign(HttpServletRequest request,HttpServletResponse reponse,Integer id) throws Exception{
		request.setAttribute("recordId", id);
		return ASSIGN_JSP;
	}
	
	*//***创建指派好运券保存方法*//*
	@RequestMapping("/couponsAssign/saveAssign")
	@Transactional
	public String  saveAssign(HttpServletRequest request,HttpServletResponse reponse) throws Exception{
		HashMap<String, Object> m = new HashMap<>();
		Integer userroleId = Integer.parseInt(request.getParameter("userRoleId"));
		Integer couponsId  = Integer.parseInt(request.getParameter("couponsId"));
		CL_Coupons coupons = this.couponsDao.getById(couponsId);
		if(coupons.getEndTime().before(new Date())){
			m.put("success", "false");
			m.put("msg", "好运券过期，无法指派");
			return writeAjaxResponse(reponse, JSONUtil.getJson(m));
		}
		if(userroleId==0){
			List<Util_Option> options= optionDao.getAllConsignor(couponsId);
			String user="";
			for(Util_Option option :options){
				 	user+=String.valueOf(option.getOptionId())+",";
				 	CL_CouponsDetail detail =new CL_CouponsDetail();
						detail.setCouponsId(couponsId);
						detail.setIsUse(0);
						detail.setIsOverdue(0);//未过期
						detail.setUserRoleId(option.getOptionId());
						detail.setCreator(Integer.parseInt(request.getSession().getAttribute("userRoleId").toString()));
						detail.setCreateTime(new Date());
						detail.setStartTime(coupons.getStartTime());
						detail.setEndTime(coupons.getEndTime());
					this.couponsDetailDao.save(detail);
					CL_Message message = new CL_Message();
						message.setReceiver(option.getOptionId());
						message.setTitle("新好运券消息");
						message.setContent("您有新的面额为："+coupons.getDollars()+"优惠券,您可以在下次付款中使用!");
						message.setType(1);//货主
						message.setCreator(request.getSession().getAttribute("userRoleId").toString());
						message.setCreateTime(new Date());
					this.messageDao.save(message);
			}
//			CsclPushUtil.SendPushToAllSound(user, "CPS:您有新的优惠券,可以在下次付款中使用!");
		}else{
			CL_CouponsDetail detail =new CL_CouponsDetail();
				detail.setCouponsId(couponsId);
				detail.setIsUse(0);
				detail.setIsOverdue(0);//未过期
				detail.setUserRoleId(userroleId);
				detail.setCreator(Integer.parseInt(request.getSession().getAttribute("userRoleId").toString()));
				detail.setCreateTime(new Date());
				detail.setStartTime(coupons.getStartTime());
				detail.setEndTime(coupons.getEndTime());
			this.couponsDetailDao.save(detail);
			CL_Message message = new CL_Message();
				message.setReceiver(userroleId);
				message.setTitle("新好运券消息");
				message.setContent("您有新的面额为："+coupons.getDollars()+"优惠券,您可以在下次付款中使用!");
				message.setType(1);//货主
				message.setCreator(request.getSession().getAttribute("userRoleId").toString());
				message.setCreateTime(new Date());
			this.messageDao.save(message);
//			CsclPushUtil.SendPushToAllSound(userroleId.toString(), "CPS:您有新的优惠券,可以在下次付款中使用!");
		}
		m.put("success", "success");
		m.put("msg", "指派成功");
		return writeAjaxResponse(reponse, JSONUtil.getJson(m));
	}
}*/
