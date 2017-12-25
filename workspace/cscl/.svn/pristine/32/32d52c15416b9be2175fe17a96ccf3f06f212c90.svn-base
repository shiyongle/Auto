package com.pc.controller.couponRule;

import java.util.Date;
import java.util.HashMap;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.org.rapid_framework.page.Page;

import com.pc.controller.BaseController;
import com.pc.dao.couponRule.IcouponRuleDao;
import com.pc.model.CL_Car;
import com.pc.model.CL_CouponRule;
import com.pc.model.CL_Rule;
import com.pc.query.couponRule.CouponRuleQuery;
import com.pc.query.identification.IdentificationQuery;
import com.pc.util.JSONUtil;
@Controller
public class couponRuleController extends BaseController {
	protected static final String LIST_JSP= "/pages/pc/couponRule/couponRule_list.jsp";
	protected static final String ADD_JSP= "/pages/pc/couponRule/couponRule_add.jsp";
	protected static final String EDIT_JSP= "/pages/pc/couponRule/couponRule_edit.jsp";

	@Resource
	private IcouponRuleDao couponRuleDao;
	
	private CouponRuleQuery couponRuleQuery;
	
	
	@RequestMapping("/couponRule/list")
	public String list(HttpServletRequest request, HttpServletResponse reponse)
			throws Exception {
		return LIST_JSP;
	}
	@RequestMapping("/couponRule/createLoad")
	public String createLoad(HttpServletRequest request, HttpServletResponse reponse)
			throws Exception {
		return ADD_JSP;
	}
	@RequestMapping("/couponRule/editCouponLoad")
	public String editCouponLoad(HttpServletRequest request, HttpServletResponse reponse,Integer id)
			throws Exception {
		CL_CouponRule couponRule = this.couponRuleDao.getById(id);
		request.setAttribute("couponRule", couponRule);
		return EDIT_JSP;
	}
	
	@RequestMapping("/couponRule/load")
	public String load(HttpServletRequest request, HttpServletResponse reponse)
			throws Exception {
		String pageNum = request.getParameter("page");
		String pageSize = request.getParameter("rows");
		String isEs=request.getParameter("isEffective");
	 	if (couponRuleQuery == null) {
			couponRuleQuery = newQuery(CouponRuleQuery.class, null);
		}
		if (pageNum != null) {
			couponRuleQuery.setPageNumber(Integer.parseInt(pageNum));
		}
		if (pageSize != null) {
			couponRuleQuery.setPageSize(Integer.parseInt(pageSize));
		}
	     if(isEs!=null){
	    	couponRuleQuery.setIsEffective(Integer.parseInt(isEs));	   
	    }
		Page<CL_CouponRule> page = couponRuleDao.findPage(couponRuleQuery);
		HashMap<String, Object> m = new HashMap<String, Object>();
		m.put("total", page.getTotalCount());
		m.put("rows", page.getResult());
		return writeAjaxResponse(reponse, JSONUtil.getJson(m));

	}
	
	//新增功能
	@RequestMapping("/couponRule/save")
	@Transactional
	public String save(HttpServletRequest request, HttpServletResponse reponse,CL_CouponRule coupon)
			throws Exception {
	   int userId=(int) request.getSession().getAttribute("userRoleId") ;
       coupon.setCreator(userId);
       System.out.println(userId);
       coupon.setCreateTime(new Date());
       coupon.setIsEffective(1);
       couponRuleDao.save(coupon);
       return writeAjaxResponse(reponse, "success");
	}
	//跟新功能
	@RequestMapping("/couponRule/updata")
	public String updata(HttpServletRequest request, HttpServletResponse reponse,CL_CouponRule coupon)
			throws Exception {
		couponRuleDao.updateById(coupon);
       return writeAjaxResponse(reponse, "success");
	}
	
	// 删除功能
	@RequestMapping("/couponRule/del")
	@Transactional(propagation = Propagation.REQUIRED)
	public String del(HttpServletRequest request, HttpServletResponse reponse,
			Integer[] ids) throws Exception {
		for (Integer id : ids) {
			this.couponRuleDao.deleteById(id);
		}
		return writeAjaxResponse(reponse, "success");
	}
	
	//更改状态
	@RequestMapping("/couponRule/change")
	public String change(HttpServletRequest request,
			HttpServletResponse reponse,Integer[] ids) throws Exception {
	      for(Integer id:ids){
	    	 CL_CouponRule couponRule=this.couponRuleDao.getById(id);
	    	 int IsEffective=couponRule.getIsEffective();
	    	 if(IsEffective==1){couponRule.setIsEffective(0);	}
	    	 else{couponRule.setIsEffective(1);}
	    	 couponRuleDao.update(couponRule);
	      }
	      return writeAjaxResponse(reponse, "success");
	}

}
