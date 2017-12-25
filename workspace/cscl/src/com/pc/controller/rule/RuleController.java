package com.pc.controller.rule;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.org.rapid_framework.page.Page;

import com.pc.controller.BaseController;
import com.pc.dao.UserRole.impl.UserRoleDao;
import com.pc.dao.order.impl.OrderDao;
import com.pc.dao.rule.impl.RuleDao;
import com.pc.model.CL_Rule;
import com.pc.model.CL_UserRole;
import com.pc.query.rule.CL_RuleQuery;
import com.pc.util.JSONUtil;
import com.pc.util.ServerContext;

@Controller
public class RuleController extends BaseController {
	
	protected static final String DRIVER_LIST_JSP= "/pages/pc/rule/driverRule_list.jsp";
	protected static final String LIST_JSP= "/pages/pc/rule/rule_list.jsp";
	protected static final String CREATE_CARLOAD_JSP= "/pages/pc/rule/rule_createCarLoad.jsp";
	protected static final String CREATE_LESSRULE_JSP= "/pages/pc/rule/rule_createLessRule.jsp";
	protected static final String CREATE_BILLRULE_JSP= "/pages/pc/rule/rule_createBillLoad.jsp";
	protected static final String CREATE_DRIVERBILLRULE_JSP= "/pages/pc/rule/rule_createDriverBill.jsp";

	
	protected static final String EditLESS_JSP= "/pages/pc/rule/rule_editLessRule.jsp";
	protected static final String EditCARLOAD_JSP= "/pages/pc/rule/rule_editCarLoad.jsp";
	protected static final String EditBILL_JSP= "/pages/pc/rule/rule_editBillRule.jsp";
	
	@Resource
	private RuleDao ruleDao;
	@Resource
	private OrderDao orderDao;
	@Resource
	private UserRoleDao userRoleDao;
	
	@RequestMapping("/rule/driverList")
	public String driverList(HttpServletRequest request,HttpServletResponse reponse) throws Exception{
		return DRIVER_LIST_JSP;
	}
	
	//列表界面
	@RequestMapping("/rule/list")
	public String list(HttpServletRequest request,HttpServletResponse reponse) throws Exception{
		return LIST_JSP;
	}
	
	@RequestMapping("/rule/createCarLoad")
	public String createCarLoad(HttpServletRequest request,HttpServletResponse reponse) throws Exception{
		return CREATE_CARLOAD_JSP;
	}
	@RequestMapping("/rule/createBillLoad")
	public String createBillLoad(HttpServletRequest request,HttpServletResponse reponse) throws Exception{
		return CREATE_BILLRULE_JSP;
	}
	
	@RequestMapping("/rule/createLessRule")
	public String createLessRule(HttpServletRequest request,HttpServletResponse reponse) throws Exception{
		return CREATE_LESSRULE_JSP;
	}
	
	@RequestMapping("/rule/createDriverBill")
	public String createDriverBill(HttpServletRequest request,HttpServletResponse reponse) throws Exception{
		return CREATE_DRIVERBILLRULE_JSP;
	}
	
	//编辑功能整车
   @RequestMapping("/rule/editCarLoad")
	public String editCarLoad(HttpServletRequest request, HttpServletResponse reponse,
			Integer id) throws Exception {
		CL_Rule rule = this.ruleDao.getById(id);
		request.setAttribute("rule", rule);
		return EditCARLOAD_JSP;
	}
	//编辑功能零担
   @RequestMapping("/rule/editLessRule")
	public String editLessRule(HttpServletRequest request, HttpServletResponse reponse,
			Integer id) throws Exception {
		CL_Rule rule = this.ruleDao.getById(id);
		request.setAttribute("rule", rule);
		return EditLESS_JSP;
	}
   
	//编辑司机计费规则
   @RequestMapping("/rule/editBillRule")
	public String editBillRule(HttpServletRequest request, HttpServletResponse reponse,
			Integer id) throws Exception {
		CL_Rule rule = this.ruleDao.getById(id);
//		rule.setStartPrice(rule.getStartPrice().multiply(new BigDecimal(100)));
		request.setAttribute("rule", rule);
		return EditBILL_JSP;
	}
   
	//加载列表数据
	@RequestMapping("/rule/load")
	public String load(HttpServletRequest request,HttpServletResponse reponse,@ModelAttribute CL_RuleQuery ruleQuery) throws Exception{
		String pageNum = request.getParameter("page");
		String pageSize = request.getParameter("rows");
		if (ruleQuery == null) {
			ruleQuery = newQuery(CL_RuleQuery.class, null);
		}
		if (pageNum != null) {
			ruleQuery.setPageNumber(Integer.parseInt(pageNum));
		}
		if (pageSize != null) {
			ruleQuery.setPageSize(Integer.parseInt(pageSize));
		}//多条件查询的属性
		if(ruleQuery.getCarSpecId() !=null && ruleQuery.getCarSpecId().equals(-1)){
			ruleQuery.setCarSpecId(null);
		}
		//司机客户价格分离
		ruleQuery.setSortColumns("ru.type in (1,2)");
		Page<CL_Rule> page = ruleDao.findPage(ruleQuery);
		HashMap<String, Object> m = new HashMap<String, Object>();
			m.put("total", page.getTotalCount());
			m.put("rows", page.getResult());
		return writeAjaxResponse(reponse, JSONUtil.getJson(m));
	}
	//整车新建 保存
	@RequestMapping("/rule/save")
	public String save(HttpServletRequest request,HttpServletResponse reponse,CL_Rule rule) throws Exception{
		rule.setStatus(1);
		rule.setFopint(1);
		rule.setType(1);//整车规则
		String vmi_user_fid = ServerContext.getUseronline().get(request.getSession().getId()).getFuserid();
		CL_UserRole user = userRoleDao.getByVmiUserFidAndRoleId(vmi_user_fid, 3);
		rule.setFcreator(user.getId());
		rule.setFoperator(user.getId());
		rule.setFoperate_time(new Date());
		if(ruleDao.getZhengche(rule.getCarSpecId())==null){
			ruleDao.save(rule);
			return writeAjaxResponse(reponse, "success");
		}
	    return writeAjaxResponse(reponse, "failure");
	}
	//零担新建 保存
	@RequestMapping("/rule/saveLess")
	public String saveLess(HttpServletRequest request,HttpServletResponse reponse,CL_Rule rule) throws Exception{
		rule.setStatus(1);
		rule.setType(2);//零担规则
		rule.setFopint(1);
		String vmi_user_fid = ServerContext.getUseronline().get(request.getSession().getId()).getFuserid();
		CL_UserRole user = userRoleDao.getByVmiUserFidAndRoleId(vmi_user_fid, 3);
		rule.setFcreator(user.getId());
		rule.setFoperator(user.getId());
		rule.setFoperate_time(new Date());
		if(rule.getOtherType()==1){
			if(ruleDao.getLingdanForVolume()==null){
				ruleDao.save(rule);
				return writeAjaxResponse(reponse, "success");
			}
		}else{
			if(ruleDao.getLingdanForWeight()==null){
				ruleDao.save(rule);
				return writeAjaxResponse(reponse, "success");
			}
		}
	    return writeAjaxResponse(reponse, "failure");
	}
	
	//司机计费新建 保存
		@RequestMapping("/rule/saveBill")
		public String saveBill(HttpServletRequest request,HttpServletResponse reponse,CL_Rule rule) throws Exception{
			if(ruleDao.getOneByType(rule.getCarSpecId()).size() > 0){
				return writeAjaxResponse(reponse, "failure");
			}
			rule.setStatus(1);
			rule.setFopint(1);//保低点数默认1
			rule.setType(3);//通用计费规则
			String vmi_user_fid = ServerContext.getUseronline().get(request.getSession().getId()).getFuserid();
			CL_UserRole user = userRoleDao.getByVmiUserFidAndRoleId(vmi_user_fid, 3);
			rule.setFcreator(user.getId());
			rule.setFoperator(user.getId());
			rule.setFoperate_time(new Date());
//		    rule.setStartPrice(rule.getStartPrice().divide(new BigDecimal(100),2, BigDecimal.ROUND_HALF_EVEN) );
			Integer specId = rule.getCarSpecId();
			/*if(specId == 1){
				rule.setFadd_service("0");
			}
			if(specId == 2){
				rule.setFadd_service("50");
			}
			if(specId == 3){
				rule.setFadd_service("100");
			}
			if(specId == 5){
				rule.setFadd_service("150");
			}
			if(specId == 6){
				rule.setFadd_service("不支持");
			}*/
			ruleDao.save(rule);
			return writeAjaxResponse(reponse, "success");
		}
		
	// 更新整车规则
	@RequestMapping("/rule/updateCarLoad")
	public String updateCarLoad(HttpServletRequest request,
			HttpServletResponse reponse, CL_Rule rule) throws Exception {
		String vmi_user_fid = ServerContext.getUseronline().get(request.getSession().getId()).getFuserid();
		CL_UserRole user = userRoleDao.getByVmiUserFidAndRoleId(vmi_user_fid, 3);
		rule.setFoperator(user.getId());
		rule.setFoperate_time(new Date());
		ruleDao.updateCarLoad(rule);
		return writeAjaxResponse(reponse, "success");
	}
	// 更新零担规则
	@RequestMapping("/rule/updateLessRule")
	public String updateLessRule(HttpServletRequest request,
			HttpServletResponse reponse, CL_Rule rule) throws Exception {
		String vmi_user_fid = ServerContext.getUseronline().get(request.getSession().getId()).getFuserid();
		CL_UserRole user = userRoleDao.getByVmiUserFidAndRoleId(vmi_user_fid, 3);
		rule.setFoperator(user.getId());
		rule.setFoperate_time(new Date());
	    ruleDao.updataLess(rule);
	    return writeAjaxResponse(reponse, "success");
	}
	// 更新司机计费规则
	@RequestMapping("/rule/updateBillRule")
	public String updateBillRule(HttpServletRequest request,
			HttpServletResponse reponse, CL_Rule rule) throws Exception {
//		rule.setKilometrePrice(null);
//		rule.setStartKilometre(null);
//		rule.setStartPrice(rule.getStartPrice().divide(new BigDecimal(100),2, BigDecimal.ROUND_HALF_EVEN) );
//		rule.setPubRemark("司机计费规则");
		String vmi_user_fid = ServerContext.getUseronline().get(request.getSession().getId()).getFuserid();
		CL_UserRole user = userRoleDao.getByVmiUserFidAndRoleId(vmi_user_fid, 3);
		rule.setFoperator(user.getId());
		rule.setFoperate_time(new Date());
		rule.setFopint(1);//保低点数默认1
		System.out.println(rule.getCarSpecId());
	    ruleDao.updateCarLoad(rule); //用整车的规则来更新计费的 一样的
	    return writeAjaxResponse(reponse, "success");
	}
	// 删除功能
	@RequestMapping("/rule/del")
	@Transactional(propagation = Propagation.REQUIRED)
	public String del(HttpServletRequest request, HttpServletResponse reponse,
			Integer[] ids) throws Exception {
		HashMap<String, Object> m = new HashMap<String, Object>();
		for (Integer id : ids) {
			int spec = ruleDao.getById(id).getCarSpecId();
			int num = orderDao.getNumByRuleSpec(spec);
			if(num > 0){
				m.put("success", "false");
				m.put("msg", "规则已使用，不得删除");
				return writeAjaxResponse(reponse, JSONUtil.getJson(m));
			}
			this.ruleDao.deleteById(id);
		}
		m.put("success", "success");
		m.put("msg", "操作成功");
		return writeAjaxResponse(reponse, JSONUtil.getJson(m));
	}
	//更改状态
	@RequestMapping("/rule/change")
	public String change(HttpServletRequest request,
			HttpServletResponse reponse, Integer[] ids) throws Exception {
		for (Integer id : ids) {
			CL_Rule rule = this.ruleDao.getById(id);
			int stat = rule.getStatus();
			if (stat == 1) {
				rule.setStatus(2);
			} else {
				// 判断有效状态的该规则是否存在
				if (ruleDao.getBySpec(rule.getCarSpecId()).size() > 0) {
					return writeAjaxResponse(reponse, "false");
				}
				rule.setStatus(1);
			}
			String vmi_user_fid = ServerContext.getUseronline()
					.get(request.getSession().getId()).getFuserid();
			CL_UserRole user = userRoleDao.getByVmiUserFidAndRoleId(
					vmi_user_fid, 3);
			rule.setFoperator(user.getId());
			rule.setFoperate_time(new Date());
			ruleDao.update(rule);
		}
		return writeAjaxResponse(reponse, "success");
	}
	
	//更改状态
	@RequestMapping("/rule/driverChange")
	public String driverChange(HttpServletRequest request,
			HttpServletResponse reponse, Integer[] ids) throws Exception {
		for (Integer id : ids) {
			CL_Rule rule = this.ruleDao.getById(id);
			int stat = rule.getStatus();
			if (stat == 1) {
				rule.setStatus(2);
			} else {
				// 判断有效状态的该规则是否存在
				if (ruleDao.getDriverBySpec(rule.getCarSpecId()).size() > 0) {
					return writeAjaxResponse(reponse, "false");
				}
				rule.setStatus(1);
			}
			String vmi_user_fid = ServerContext.getUseronline()
					.get(request.getSession().getId()).getFuserid();
			CL_UserRole user = userRoleDao.getByVmiUserFidAndRoleId(
					vmi_user_fid, 3);
			rule.setFoperator(user.getId());
			rule.setFoperate_time(new Date());
			ruleDao.update(rule);
		}
		return writeAjaxResponse(reponse, "success");
	}
	
	//加载列表数据
		@RequestMapping("/rule/driverLoad")
		public String driverLoad(HttpServletRequest request,HttpServletResponse reponse,@ModelAttribute CL_RuleQuery ruleQuery) throws Exception{
			String pageNum = request.getParameter("page");
			String pageSize = request.getParameter("rows");
			if (ruleQuery == null) {
				ruleQuery = newQuery(CL_RuleQuery.class, null);
			}
			if (pageNum != null) {
				ruleQuery.setPageNumber(Integer.parseInt(pageNum));
			}
			if (pageSize != null) {
				ruleQuery.setPageSize(Integer.parseInt(pageSize));
			}//多条件查询的属性
			if(ruleQuery.getCarSpecId() !=null && ruleQuery.getCarSpecId().equals(-1)){
				ruleQuery.setCarSpecId(null);
			}
			//司机客户价格分离
			ruleQuery.setSortColumns("ru.type = 3");
			Page<CL_Rule> page = ruleDao.findPage(ruleQuery);
			HashMap<String, Object> m = new HashMap<String, Object>();
				m.put("total", page.getTotalCount());
				m.put("rows", page.getResult());
			return writeAjaxResponse(reponse, JSONUtil.getJson(m));
		}
}
