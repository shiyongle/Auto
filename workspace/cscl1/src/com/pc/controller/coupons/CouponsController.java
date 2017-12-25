package com.pc.controller.coupons;

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
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.org.rapid_framework.page.Page;

import com.pc.controller.BaseController;
import com.pc.dao.coupons.ICouponsDao;
import com.pc.dao.couponsDetail.ICouponsDetailDao;
import com.pc.model.CL_Coupons;
import com.pc.model.CL_CouponsDetail;
import com.pc.query.coupons.CL_CouponsQuery;
import com.pc.util.JSONUtil;
import com.pc.util.MD5Util;

@Controller
public class CouponsController extends BaseController{
	protected static final String LIST_JSP= "/pages/pc/coupons/coupons_list.jsp";
	protected static final String CREATE_JSP= "/pages/pc/coupons/create.jsp";
	protected static final String VIEW_JSP= "/pages/pc/coupons/view.jsp";
	@Resource
	private ICouponsDao couponsDao;
	@Resource
	private ICouponsDetailDao couponsDetailDao;
	
	
	/*** 表单提交日期绑定*/
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");//写上你要的日期格式
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }
    
    
    /***首页好运券管理*/
	@RequestMapping("/coupons/list")
	public String list(HttpServletRequest request,HttpServletResponse response) throws Exception{
		return LIST_JSP;
	}
	
	
	
	
	/*** 优惠券管理-加载列表信息*/
	@RequestMapping("/coupons/load")
	public String load(HttpServletRequest request,HttpServletResponse response,@ModelAttribute CL_CouponsQuery couponsQuery) throws Exception{
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
		Page<CL_Coupons> page = couponsDao.findPage(couponsQuery);
		m.put("total", page.getTotalCount());
		for (CL_Coupons coupons : page.getResult()) {
			if(coupons.getFtype() == 1){
				if(coupons.getFdiscount() != null){
					coupons.setCouponsType("折扣"+coupons.getFdiscount()+"%");
				} else {
					coupons.setCouponsType("直减"+coupons.getFsubtract()+"元");
				}
			} else {
				coupons.setCouponsType(coupons.getFaddserviceName());
			}
		}
		m.put("rows", page.getResult());
		return writeAjaxResponse(response, JSONUtil.getJson(m));
	}
	
	
	
	@RequestMapping("/coupons/create")
	public String create(HttpServletRequest request,HttpServletResponse response) throws Exception{
		return CREATE_JSP;
	}
	
	@RequestMapping("/coupons/view")
	public String view(HttpServletRequest request,HttpServletResponse response,Integer id) throws Exception{
		CL_Coupons coupons = couponsDao.getById(id);
		request.setAttribute("coupons", coupons);
		return VIEW_JSP;
	}
	
	/*@RequestMapping("/coupons/saveOne")
	public String  saveOne(HttpServletRequest request,HttpServletResponse reponse,@ModelAttribute CL_Coupons coupons) throws Exception{
		SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
		String startTime = request.getParameter("startTime");
		Date start = date.parse(startTime);
		String now = date.format(new Date());
		if(start.before(date.parse(now))){
			return this.poClient(reponse, false, "有效期不合逻辑!");
		}
		if(coupons.getCompareDollars().compareTo(coupons.getDollars()) <= 0 || coupons.getDollars().compareTo(BigDecimal.ZERO) <= 0){
			return this.poClient(reponse, false, "金额不合逻辑!");
		}
		coupons.setIsEffective(1);//有效
		coupons.setIsOverdue(0);//未过期
		coupons.setType(4);//
		coupons.setCreator(Integer.parseInt(request.getSession().getAttribute("userRoleId").toString()));
		coupons.setCreateTime(new Date());
		coupons.setRedeemCode(MD5Util.getUUIDNumber());
		this.couponsDao.save(coupons);
		return writeAjaxResponse(reponse, "success");
	}*/
	
	@RequestMapping("/coupons/save")
	public String  saveOne(HttpServletRequest request,HttpServletResponse response,@ModelAttribute CL_Coupons coupons) throws Exception{
		HashMap<String, Object> map = new HashMap<String, Object>();
		Number count = couponsDao.getNumByCouponsName(coupons.getFcouponsName());
		if(count.intValue() > 0){
			map.put("success", false);
			map.put("msg", "模板重名!");
			return writeAjaxResponse(response, JSONUtil.getJson(map));
		}
		if(coupons.getFtype() == 1){
			if(coupons.getFdiscount() != null){
				if(coupons.getFdiscount().compareTo(BigDecimal.ZERO) <= 0 || coupons.getFdiscount().compareTo(new BigDecimal(100)) >= 0){
					map.put("success", false);
					map.put("msg", "折扣不合理!");
					return writeAjaxResponse(response, JSONUtil.getJson(map));
				}
			} else {
				if(coupons.getFsubtract().compareTo(BigDecimal.ZERO) <= 0){
					map.put("success", false);
					map.put("msg", "金额不合理!");
					return writeAjaxResponse(response, JSONUtil.getJson(map));
				}
			}
		}
		coupons.setCreator(Integer.parseInt(request.getSession().getAttribute("userRoleId").toString()));
		coupons.setCreateTime(new Date());
		this.couponsDao.save(coupons);
		map.put("success", true);
		map.put("msg", "操作成功!");
		return writeAjaxResponse(response, JSONUtil.getJson(map));
	}
	
	
	/***更新有效*/
	@RequestMapping("/coupons/effective")
	public String  effective(HttpServletRequest request,HttpServletResponse reponse,Integer[] ids) throws Exception{
		this.couponsDao.updatepl(1, ids);
		return writeAjaxResponse(reponse, "success");
	}
	
	/***更新失效效*/
	@RequestMapping("/coupons/unEffective")
	public String  unEffective(HttpServletRequest request,HttpServletResponse reponse,Integer[] ids) throws Exception{
		this.couponsDao.updatepl(0, ids);
		return writeAjaxResponse(reponse, "success");
	}
	
	/***删除*/
	@RequestMapping("/coupons/del")
	public String  del(HttpServletRequest request,HttpServletResponse reponse,Integer[] ids) throws Exception{
		HashMap<String, Object> m = new HashMap<>();
		int i;Integer couponsId;
		for(i=0;i<ids.length;i++){
			couponsId = ids[i];
			List<CL_CouponsDetail> list = couponsDetailDao.getByCouponsId2(couponsId);
			if(list.size() > 0 ){
				m.put("success", "false");
				m.put("msg", "该好运券已被指派，不可删除");
				return writeAjaxResponse(reponse, JSONUtil.getJson(m));
			}
		}
		this.couponsDao.deletepl(ids);
		m.put("success", "success");
		m.put("msg", "删除成功");
		return writeAjaxResponse(reponse, JSONUtil.getJson(m));
	}

//	@RequestMapping("/coupons/saves")
//	public String  saves(HttpServletRequest request,HttpServletResponse reponse) throws Exception{
//		Integer urid=0;
//		BigDecimal dollar=new BigDecimal(5.0);
//		if(request.getParameter("urid")==null||"".equals(request.getParameter("urid")))
//		{
//			return writeAjaxResponse(reponse, "failed");
//		}
//		else
//		{
//			urid=Integer.parseInt(request.getParameter("urid"));
//		}
//		int couponsId=this.couponsDao.saveCoupons(dollar);
//		couponsDetailDao.saveCoupons(couponsId, dollar, urid);
//		return writeAjaxResponse(reponse, "success");
//	}
}

