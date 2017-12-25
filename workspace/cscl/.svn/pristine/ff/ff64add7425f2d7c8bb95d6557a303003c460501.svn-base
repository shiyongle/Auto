package com.pc.controller.userBalance;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.org.rapid_framework.page.Page;

import com.pc.controller.BaseController;
import com.pc.dao.UserRole.impl.UserRoleDao;
import com.pc.dao.financeStatement.IFinanceStatementDao;
import com.pc.dao.userBalance.impl.UserBalanceDao;
import com.pc.model.CL_FinanceStatement;
import com.pc.model.CL_UserBalance;
import com.pc.model.CL_UserRole;
import com.pc.model.Util_UserOnline;
import com.pc.query.userBalance.CL_UserBalanceQuery;
import com.pc.util.CacheUtilByCC;
import com.pc.util.JSONUtil;
import com.pc.util.ServerContext;
import com.pc.util.pay.OrderMsg;

@Controller
public class UserBalanceController extends BaseController{
	
	protected static final String PUBLICKEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDl5nbvmL8Q8tYGcJAgwS4qdqp2" +
			"Rwme5FKaR+11vXy89Biu8ruF/KmdS4pk+4gEmoPuHLFc6V6VQ77CgtpgboBDjveU" +
			"n3HnsN1N2LH/hmn8gDvw+0e7lLDFVEGC6L8d9z+yj0zGe0XMDeEW5zJlVCA2FOYq" +
			"oQAOkIntynv/nfyP6wIDAQAB";

	protected static final String PERSONALPUBLICKEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCcu9tqg+XyDB7qygFxn4UQu00T" +
			"WrnbQmIDUnE8zhDf9ZuCr5Czil1XVR4ApnpcVUTTXHoW2WpzBw1gm53OdKjgPc2Q" +
			"yRq+ROlo7NhYCRFan+b4p+RqGL+U+alMH1zv1Q+LSgQP6QF9loKAsC3i70KdBw4G" +
			"n7K8fTzoY8PtMqHlSwIDAQAB";
	
	protected static final String BALANCE_LIST = "/pages/pc/userBalance/userBalanceList.jsp";
	protected static final String BALANCE_AUDIT_LIST = "/pages/pc/userBalance/userBalanceAuditList.jsp";
	protected static final String BALANCE_save = "/pages/pc/userBalance/userBalanceSave.jsp";
	protected static final String BALANCE_AUDIT = "/pages/pc/userBalance/userBalanceAudit.jsp";
	
	
	
	@Resource
	private UserBalanceDao balanceDao;
	
	@Resource
	private UserRoleDao userRoleDao;
	
	@Resource 
	private IFinanceStatementDao financeStatementDao;
	
	@RequestMapping("/userBalance/userBalanceList")
	public String balanceList(HttpServletRequest request,HttpServletResponse response) throws Exception{
		return BALANCE_LIST;
	}
	
	@RequestMapping("/userBalance/userBalanceAuditList")
	public String balanceAuditList(HttpServletRequest request,HttpServletResponse response) throws Exception{
		return BALANCE_AUDIT_LIST;
	}
	
	@RequestMapping("/userBalance/userBalanceSave")
	public String balanceSave(HttpServletRequest request,HttpServletResponse response) throws Exception{
		return BALANCE_save;
	}
	
	@RequestMapping("//userBalance/userBalanceAudit")
	public String balanceAudit(HttpServletRequest request,HttpServletResponse response,Integer id) throws Exception{
		request.setAttribute("balance", balanceDao.getById(id));
		return BALANCE_AUDIT;
	}
	
	@RequestMapping("/userBalance/load")
	public String load(HttpServletRequest request,HttpServletResponse response,@ModelAttribute CL_UserBalanceQuery balanceQuery) throws Exception{
		String pageNum = request.getParameter("page");
		String pageSize = request.getParameter("rows");
		if (balanceQuery == null) {
			balanceQuery = newQuery(CL_UserBalanceQuery.class, null);
		}
		if (pageNum != null) {
			balanceQuery.setPageNumber(Integer.parseInt(pageNum));
		}
		if (pageSize != null) {
			balanceQuery.setPageSize(Integer.parseInt(pageSize));
		}
		Page<CL_UserBalance> page = balanceDao.findPage(balanceQuery);
		HashMap<String, Object> m = new HashMap<String, Object>();
		m.put("total", page.getTotalCount());
		m.put("rows", page.getResult());
		return writeAjaxResponse(response, JSONUtil.getJson(m));
	}
	
	@RequestMapping("/userBalance/del")
	public String del(HttpServletRequest request,HttpServletResponse response,Integer[] ids) throws Exception{
		for (Integer id : ids) {
			CL_UserBalance balance = balanceDao.getById(id);
			if(balance.getFis_pass_identify()!=0){
				return writeAjaxResponse(response, "false");
			}else {
				balanceDao.deleteById(id);
				return writeAjaxResponse(response, "success");
			}
		}
		return writeAjaxResponse(response, "false");
	}
	
	@RequestMapping("/userBalance/save")
	public String save(HttpServletRequest request,HttpServletResponse response,@ModelAttribute CL_UserBalance balance) throws Exception{
		String vmi_user_fid = ServerContext.getUseronline().get(request.getSession().getId()).getFuserid();
		CL_UserRole user = userRoleDao.getByVmiUserFidAndRoleId(vmi_user_fid, 3);
		balance.setFoperator(user.getId());
		balance.setFis_pass_identify(0);
		balance.setFoperate_time(new Date());
		balanceDao.save(balance);
		return writeAjaxResponse(response, "success");
	}
	
	
	/**
	 * 加载用户余额
	 * @param request
	 * @param response
	 * @param userRoleId用户id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/userBalance/loadUserBill")
	public String loadBill(HttpServletRequest request,HttpServletResponse response,Integer userRoleId) throws Exception{
		HashMap<String, Object> map=new HashMap<String,Object>();
		BigDecimal fbalance=new BigDecimal(0);
		if(userRoleId != -1){
			CL_UserRole user = userRoleDao.getById(userRoleId);
			fbalance = user.getFbalance();
			if(fbalance == null){
				map.put("money", "0");
			} else {
				map.put("money", fbalance);
			}
		}else {
			map.put("money", fbalance);
		}
		return writeAjaxResponse(response,JSONUtil.getJson(map));
	}
	
	@RequestMapping("/userBalance/audit")
	public String audit(HttpServletResponse response,
			HttpServletRequest request, @ModelAttribute CL_UserBalance balance)
			throws Exception {
		HashMap<String, Object> param = new HashMap<String, Object>();
		HashMap<String, Util_UserOnline> useronline = ServerContext
				.getUseronline();
		if (request.getSession().getId() == null
				|| request.getSession().getId().equals("")) {
			return writeAjaxResponse(response, "false");
		}
		int userId = useronline.get(request.getSession().getId().toString()).getFuserId();
		CL_UserRole user = userRoleDao.getById(userId);
//		String vmiUserId = useronline.get(request.getSession().getId().toString()).getFuserid();

//		BigDecimal money = balance.getFoperate_money();
//		CL_FinanceStatement financeStatement = new CL_FinanceStatement();
//		if(balance.getFoperate_type() == 1){//增加余额
//			financeStatement.setFtype(1);
//		} else {
//			financeStatement.setFtype(-1);
//		}
//		financeStatementDao.saveStatement(balance.getFid().toString(), "", 0,
//				money, financeStatement.getFtype(), balance.getFuser_role_id(),0, vmiUserId);
		CL_FinanceStatement statement = this.sta(balance);
		statement.setFremark("余额调整");
		financeStatementDao.save(statement);
		OrderMsg orderMsg = new OrderMsg();
		orderMsg.setOrder(statement.getNumber());
		orderMsg.setPayState("1");
		orderMsg.setServiceProviderType("0");
		orderMsg.setServiceProvider("0");
		orderMsg.setUserId(userId);
		param.put("balance", balance);
		param.put("is_update", 2);
		int i = financeStatementDao.updateBusinessType(orderMsg,param);
		if(i == 1){
			return writeAjaxResponse(response, "success");
		} else {
			return writeAjaxResponse(response, "false");
		}
	}
	
	@RequestMapping("/userBalance/notAudit")
	public String notAudit(HttpServletResponse response,HttpServletRequest request,@ModelAttribute CL_UserBalance balance) throws Exception{
		String vmi_user_fid = ServerContext.getUseronline().get(request.getSession().getId()).getFuserid();
		CL_UserRole user = userRoleDao.getByVmiUserFidAndRoleId(vmi_user_fid, 3);
		balance.setFauditor(user.getId());
		balance.setFaudit_time(new Date());
		balance.setFis_pass_identify(2);
		balanceDao.update(balance);
		return writeAjaxResponse(response, "success");
	}
	
	/**
	 * 生成明细公共方法
	 * @param balance关联的余额表
	 * @param user用户
	 * @param money费用
	 * @param ftype收支情况
	 * @return
	 */
	public CL_FinanceStatement sta(CL_UserBalance balance){
		CL_FinanceStatement statement = new CL_FinanceStatement();
		String number = CacheUtilByCC.getOrderNumber("cl_finance_statement", "L", 8);
		statement.setNumber(number);
		statement.setFrelatedId(balance.getFid().toString());
		statement.setForderId("");
		statement.setFbusinessType(0);//余额调整
		statement.setFamount(balance.getFoperate_money());
		statement.setFtype(balance.getFoperate_type());
		statement.setFuserroleId(balance.getFuser_role_id());
		statement.setFuserid(userRoleDao.getById(balance.getFuser_role_id()).getVmiUserFid());
		//statement.setFpayType(0);//支付方式？null？
		statement.setFbalance(BigDecimal.ZERO);//先设0
		statement.setFcreateTime(new Date());
		statement.setFreight(balance.getFoperate_money());
		return statement;
	}
	
	
	
	
	
	
	
	
}
