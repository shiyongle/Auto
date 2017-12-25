package com.pc.controller.CusWithdrawal;

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
import com.pc.dao.cusWithdrawal.impl.CusWithdrawalDao;
import com.pc.model.CL_CusWithdrawal;
import com.pc.model.CL_UserBalance;
import com.pc.model.CL_UserRole;
import com.pc.query.cusWithdrawal.CusWithdrawalQuery;
import com.pc.query.userBalance.CL_UserBalanceQuery;
import com.pc.util.JSONUtil;
import com.pc.util.ServerContext;

@Controller
public class CusWithdrawalController extends BaseController {
	
	protected static final String CUSWITHDRAWAL_LIST = "pages/pc/cusWithdrawal/cusWithdrawalList.jsp";
	protected static final String CUSWITHDRAWAL_APPLY = "pages/pc/cusWithdrawal/cusWithdrawalApply.jsp";
	protected static final String CUSWITHDRAWAL_AUDIT = "pages/pc/cusWithdrawal/cusWithdrawalAudit.jsp";
	protected static final String CUSWITHDRAWAL_GRANT = "pages/pc/cusWithdrawal/cusWithdrawalGrant.jsp";
	
	@Resource
	private CusWithdrawalDao cusWithdrawalDao;
	
	@Resource
	private UserRoleDao userRoleDao;
	
	@RequestMapping("/cusWithdrawal/cusWithdrawalList")
	public String cusWithdrawalList(HttpServletRequest request,HttpServletResponse response) throws Exception{
		return CUSWITHDRAWAL_LIST;
	}
	
	@RequestMapping("/cusWithdrawal/cusWithdrawalApply")
	public String cusWithdrawalApply(HttpServletRequest request,HttpServletResponse response) throws Exception{
		return CUSWITHDRAWAL_APPLY;
	}
	
	@RequestMapping("/cusWithdrawal/cusWithdrawalAudit")
	public String cusWithdrawalAudit(HttpServletRequest request,HttpServletResponse response,Integer fid) throws Exception{
		request.setAttribute("cusWithdrawal", cusWithdrawalDao.getById(fid));
		return CUSWITHDRAWAL_AUDIT;
	}
	
	@RequestMapping("/cusWithdrawal/cusWithdrawalGrant")
	public String cusWithdrawalGrant(HttpServletRequest request,HttpServletResponse response,Integer fid) throws Exception{
		request.setAttribute("cusWithdrawal", cusWithdrawalDao.getById(fid));
		return CUSWITHDRAWAL_GRANT;
	}
	
	
	/**
	 * loading cusWithdrawal list
	 * @param request
	 * @param response
	 * @param cusWithdrawalQuery
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/cusWithdrawal/load")
	public String load(HttpServletRequest request,HttpServletResponse response,@ModelAttribute CusWithdrawalQuery cusWithdrawalQuery) throws Exception{
		String pageNum = request.getParameter("page");
		String pageSize = request.getParameter("rows");
		if (cusWithdrawalQuery == null) {
			cusWithdrawalQuery = newQuery(CusWithdrawalQuery.class, null);
		}
		if (pageNum != null) {
			cusWithdrawalQuery.setPageNumber(Integer.parseInt(pageNum));
		}
		if (pageSize != null) {
			cusWithdrawalQuery.setPageSize(Integer.parseInt(pageSize));
		}
		Page<CL_CusWithdrawal> page = cusWithdrawalDao.findPage(cusWithdrawalQuery);
		HashMap<String, Object> m = new HashMap<String, Object>();
		m.put("total", page.getTotalCount());
		m.put("rows", page.getResult());
		return writeAjaxResponse(response, JSONUtil.getJson(m));
	}
	
	@RequestMapping("/cusWithdrawal/save")
	public String save(HttpServletRequest request,HttpServletResponse response,@ModelAttribute CL_CusWithdrawal cusWithdrawal) throws Exception {
		HashMap<String, Object> m = new HashMap<String, Object>();
		String vmi_user_fid = ServerContext.getUseronline().get(request.getSession().getId()).getFuserid();
		CL_UserRole user = userRoleDao.getByVmiUserFidAndRoleId(vmi_user_fid, 3);
		cusWithdrawal.setFapplicant(user.getId());
		cusWithdrawal.setFapply_time(new Date());
		cusWithdrawalDao.save(cusWithdrawal);
		m.put("success", "success");
		m.put("msg", "success！");
		return writeAjaxResponse(response, JSONUtil.getJson(m));
	}
	
	@RequestMapping("/cusWithdrawal/del")
	public String del(HttpServletResponse response,HttpServletRequest request,Integer id) throws Exception{
		HashMap<String, Object> m = new HashMap<String, Object>();
		CL_CusWithdrawal cusWithdrawal = cusWithdrawalDao.getById(id);
		Integer is_pass = cusWithdrawal.getFis_pass();
		if(is_pass == 1){
			m.put("success", "false");
			m.put("msg", "审核通过的申请不得删除");
			return writeAjaxResponse(response, JSONUtil.getJson(m));
		} else {
			cusWithdrawalDao.deleteById(id);
			m.put("success", "success");
			m.put("msg", "success!");
			return writeAjaxResponse(response, JSONUtil.getJson(m));
		}
	}
	
	//押后再说
	@RequestMapping("/cusWithdrawal/audit")
	public String audit(HttpServletRequest request,HttpServletResponse response,Integer id) throws Exception{
		
		return null;
	}
	
	
	
	
	
	
	
}
