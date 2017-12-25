package com.pc.appInterface.finance;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.org.rapid_framework.page.Page;

import com.pc.controller.BaseController;
import com.pc.dao.UserRole.IUserRoleDao;
import com.pc.dao.couponsDetail.impl.CouponsDetailDaoImpl;
import com.pc.dao.finance.IFinanceDao;
import com.pc.dao.finance.impl.FinanceDaoImpl;
import com.pc.dao.financeStatement.IFinanceStatementDao;
import com.pc.dao.financeStatement.impl.FinanceStatementDaoImpl;
import com.pc.dao.order.impl.OrderDao;
import com.pc.model.CL_CouponsDetail;
import com.pc.model.CL_Finance;
import com.pc.model.CL_FinanceStatement;
import com.pc.model.CL_UserRole;
import com.pc.model.Util_UserOnline;
import com.pc.query.finance.CL_FinanceQuery;
import com.pc.query.financeStatement.FinanceStatementQuery;
import com.pc.util.Base64;
import com.pc.util.CacheUtilByCC;
import com.pc.util.JSONUtil;
import com.pc.util.RSA;
import com.pc.util.ServerContext;
import com.pc.util.String_Custom;
import com.pc.util.pay.OrderMsg;

@Controller
public class AppFinanceController extends BaseController {
	protected static final String PUBLICKEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDl5nbvmL8Q8tYGcJAgwS4qdqp2"
			+ "Rwme5FKaR+11vXy89Biu8ruF/KmdS4pk+4gEmoPuHLFc6V6VQ77CgtpgboBDjveU"
			+ "n3HnsN1N2LH/hmn8gDvw+0e7lLDFVEGC6L8d9z+yj0zGe0XMDeEW5zJlVCA2FOYq" + "oQAOkIntynv/nfyP6wIDAQAB";

	protected static final String PERSONALPUBLICKEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCcu9tqg+XyDB7qygFxn4UQu00T"
			+ "WrnbQmIDUnE8zhDf9ZuCr5Czil1XVR4ApnpcVUTTXHoW2WpzBw1gm53OdKjgPc2Q"
			+ "yRq+ROlo7NhYCRFan+b4p+RqGL+U+alMH1zv1Q+LSgQP6QF9loKAsC3i70KdBw4G" + "n7K8fTzoY8PtMqHlSwIDAQAB";
	@Resource
	private IFinanceDao financeDao;
	@Resource
	private OrderDao orderDao;
	@Resource
	private FinanceDaoImpl financeDaoImpl;
	@Resource
	private FinanceStatementDaoImpl statementDao;
	@Resource
	private IUserRoleDao userRoleDao;
	@Resource
	private CouponsDetailDaoImpl couponsDetailDao;
	@Resource
	private IFinanceStatementDao financeStatementDao;

	private CL_FinanceQuery financeQuery;
	private FinanceStatementQuery statementQuery;

	public FinanceStatementQuery getStatementQuery() {
		return statementQuery;
	}

	public void setStatementQuery(FinanceStatementQuery statementQuery) {
		this.statementQuery = statementQuery;
	}

	public CL_FinanceQuery getFinanceQuery() {
		return financeQuery;
	}

	public void setFinanceQuery(CL_FinanceQuery financeQuery) {
		this.financeQuery = financeQuery;
	}

	/***
	 * 提现申请
	 * 
	 * @throws ParseException
	 */
	@RequestMapping("/app/finance/withdraw")
	@Transactional
	public String financewithdraw(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ParseException {
		String type = request.getParameter("type"); // 提现方式(1-支付宝2-微信)
		String amount = request.getParameter("amount"); // 提现金额
		String fpaypassword = request.getParameter("fpaypassword"); // 支付密码

		// 必不为空判断
		String msg = String_Custom.parametersEmpty(new String[] { type, amount,fpaypassword});
		if (msg.length() != 0)
			return this.poClient(response, false, msg);

		// 合理性判断
		if (String_Custom.noNumber(type))
			return this.poClient(response, false, "提现方式格式错误!");
		if (String_Custom.noFloat(amount))
			return this.poClient(response, false, "金额有误!");
		// 验证支付密码是否正确
		if (String_Custom.noNull(fpaypassword))
			return this.poClient(response, false, "提现密码不可为空!");

		BigDecimal money = new BigDecimal(amount);

		// 获取用户id
		Integer fcusid = ServerContext.getUseronline().get(request.getSession().getId().toString()).getFuserId();

		// 获取可提现金额
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar c = Calendar.getInstance();
		c.add(Calendar.DATE, -7);
		// Date fcopTime = c.getTime();
		Date fcopTime = sdf.parse((sdf.format(c.getTime())));
		String lockedMoney = orderDao.getLockedAmount(fcusid, fcopTime);
		CL_UserRole role = userRoleDao.getById(fcusid);

		// 判断用户输入支付密码是否正确
		if (!fpaypassword.equals(role.getFpaypassword()))
			return this.poClient(response, false, "提现密码错误!");
		
		BigDecimal mentioned = role.getFbalance();// 获取余额
		mentioned = mentioned.subtract(new BigDecimal(lockedMoney));

		// 判断可提现金额是否合理
		if (money.compareTo(mentioned) > 0)
			return this.poClient(response, false, "你最多只能提取:" + mentioned + "元");

		String alipayId = null;
		String bankAccount = null;
		String bankName = null;
		String bankAddress = null;

		int payType = Integer.parseInt(type);
		switch (payType) {
		case 1: {
			// 支付宝
			alipayId = role.getAlipayId();
			if (String_Custom.noNull(alipayId))
				return this.poClient(response, false, "请确认已经添加了支付宝信息");
		}
			break;
		case 2: {
			// 银行卡
			bankAccount = role.getBankAccount();// 银行卡帐号
			bankName = role.getBankName(); // 开户行名称
			bankAddress = role.getBankAddress();// 开户行所在地
			if (String_Custom.noNull(bankAccount) || String_Custom.noNull(bankName)
					|| String_Custom.noNull(bankAddress)) {
				return this.poClient(response, false, "请确认已经添加了银行信息");
			}
		}
			break;

		default:
			break;
		}

		// 生成提现记录
		String FFnumber = CacheUtilByCC.getOrderNumber("CL_Finance", "T", 8);
		CL_Finance finance = new CL_Finance();
		finance.setFuserId(fcusid);
		finance.setNumber(FFnumber);
		finance.setFcreateTime(new Date());
		finance.setFamount(money);
		finance.setFstate(0);
		finance.setFwithdrawType(payType); // 支付金额
		if (alipayId != null) {
			finance.setFalipayId(alipayId);
		} else {
			finance.setFbankAccount(bankAccount);
			finance.setFbankName(bankName);
			finance.setFbankAddress(bankAddress);
		}
		this.financeDao.save(finance);
		/** 添加明细表 Start */
		String FNumber = CacheUtilByCC.getOrderNumber("cl_finance_statement", "L", 8);

		CL_FinanceStatement statement = new CL_FinanceStatement();
		statement.setFcreateTime(new Date());
		statement.setNumber(FNumber);
		statement.setFrelatedId(finance.getFid() + ""); // 提现记录 ID
		statement.setForderId(FFnumber);
		statement.setFbusinessType(5); // 下单支付
		statement.setFamount(money);
		statement.setFtype(-1);
		statement.setFuserroleId(fcusid);
		statement.setFuserid(fcusid.toString());
		statement.setFpayType(0);
		statement.setFremark("提现" + money + "元");
		statement.setFbalance(new BigDecimal("0"));
		statement.setFreight(money);
		statement.setFstatus(0);

		financeStatementDao.save(statement);

		OrderMsg ordermsg = new OrderMsg();
		ordermsg.setPayState("1"); // 支付成功
		ordermsg.setServiceProvider("0");// 余额支付
		ordermsg.setServiceProviderType("0");// 余额支付;
		ordermsg.setOrder(FNumber);
		int status = financeStatementDao.updateBusinessType(ordermsg);
		if (status == 0)
			return this.poClient(response, false, "支付失败,请确认余额是否充足或订单是否已支付!");

		return this.poClient(response, true);
	}

	/***
	 * 提现申请
	 * 
	 * @throws ParseException
	 */
	/*
	 * @RequestMapping("/app/finance/withdraw2")
	 * 
	 * @Transactional public String withdraw(HttpServletRequest request,
	 * HttpServletResponse response) throws IOException, ParseException {
	 * HashMap<String, Object> map = new HashMap<String, Object>();
	 * request.setCharacterEncoding("utf-8");
	 * response.setContentType("text/html;charset=utf-8"); String alipay = null,
	 * bankaccount = null, bankname = null, bankaddress = null, versionCode =
	 * null, fsystem = ""; BigDecimal amount; Integer urid; Integer withdrawType
	 * = 0;// 提现方式1支付宝2银行转账 // APP强制更新； HashMap<String, Util_UserOnline>
	 * useronline = ServerContext.getUseronline(); if
	 * (request.getSession().getId() == null ||
	 * request.getSession().getId().equals("")) { map.put("success", "false");
	 * map.put("msg", "登录超时！"); return writeAjaxResponse(response,
	 * JSONUtil.getJson(map)); } versionCode =
	 * useronline.get(request.getSession().getId().toString()).getVersionCode();
	 * if (versionCode == null || "".equals(versionCode)) { map.put("success",
	 * "false"); map.put("msg", "请重新登录更新APP后再下单，谢谢合作！"); return
	 * writeAjaxResponse(response, JSONUtil.getJson(map)); } else { BigDecimal
	 * bVersionCode = new BigDecimal(versionCode); fsystem =
	 * useronline.get(request.getSession().getId().toString()).getFsystem(); if
	 * (CacheUtilByCC.isOldVersion(bVersionCode, fsystem)) { map.put("success",
	 * "false"); if (fsystem.startsWith("ios")) { map.put("msg",
	 * "请去苹果商店下载更新，谢谢合作！"); } else { map.put("msg", "请重新登录更新APP后再下单，谢谢合作！"); }
	 * return writeAjaxResponse(response, JSONUtil.getJson(map)); } }
	 * 
	 * if (request.getParameter("urid") == null ||
	 * "".equals(request.getParameter("urid"))) { map.put("success", "false");
	 * map.put("msg", "请先登录"); return writeAjaxResponse(response,
	 * JSONUtil.getJson(map)); } else {// 申请用户id urid =
	 * Integer.parseInt(request.getParameter("urid")); } if
	 * (request.getParameter("amount") == null ||
	 * "".equals(request.getParameter("amount"))) { map.put("success", "false");
	 * map.put("msg", "金额不能为空"); return writeAjaxResponse(response,
	 * JSONUtil.getJson(map)); } else {// 申请金额 amount = new
	 * BigDecimal(request.getParameter("amount")); if (amount.compareTo(new
	 * BigDecimal("0")) < 0) { map.put("success", "false"); map.put("msg",
	 * "金额不能负数！"); return writeAjaxResponse(response, JSONUtil.getJson(map)); }
	 * } CL_UserRole role = userRoleDao.getById(urid);
	 * 
	 *//** 提现金额控制 Start */
	/*
	 * String requestDataString = ""; String resString = ""; JSONObject jo;
	 * BigDecimal fbalance = new BigDecimal(0); String Developer =
	 * RSA.encrypt("CS", PUBLICKEY); String hzUsenameE =
	 * RSA.encrypt(role.getVmiUserPhone(), PERSONALPUBLICKEY); requestDataString
	 * = "{\"data\": {\"Developer\": \"" + Developer + "\",\"Usename\": \"" +
	 * hzUsenameE + "\"}}"; requestDataString =
	 * Base64.encode(requestDataString.getBytes()); resString =
	 * ServerContext.UsePayBalance(requestDataString); if (resString == null) {
	 * map.put("success", "false"); map.put("msg", "提现失败,获取余额有误,请联系客服！"); return
	 * writeAjaxResponse(response, JSONUtil.getJson(map)); } jo =
	 * JSONObject.fromObject(resString); if
	 * ("true".equals(jo.get("success").toString())) { fbalance = new
	 * BigDecimal(jo.get("data").toString());// 取得当前余额 } SimpleDateFormat sdf =
	 * new SimpleDateFormat("yyyy-MM-dd"); Calendar c = Calendar.getInstance();
	 * c.add(Calendar.DATE, -7); // Date fcopTime = c.getTime(); Date fcopTime =
	 * sdf.parse((sdf.format(c.getTime()))); BigDecimal lockedMoney = new
	 * BigDecimal(orderDao.getLockedAmount(urid, fcopTime));// 查询锁定金额 if
	 * (fbalance.compareTo(lockedMoney.add(amount)) == -1) { map.put("success",
	 * "false"); map.put("msg", "余额不足,请刷新余额"); return
	 * writeAjaxResponse(response, JSONUtil.getJson(map)); }
	 *//** 提现金额控制 End */
	/*
	 * 
	 * // 如果支付宝帐号为空，那么必须传银行的三个参数 if (request.getParameter("alipay") == null ||
	 * "".equals(request.getParameter("alipay"))) { if
	 * (request.getParameter("bankaccount") == null ||
	 * "".equals(request.getParameter("bankaccount"))) { map.put("success",
	 * "false"); map.put("msg", "银行帐号为空"); return writeAjaxResponse(response,
	 * JSONUtil.getJson(map)); } else {// 银行卡帐号 bankaccount =
	 * request.getParameter("bankaccount"); } if
	 * (request.getParameter("bankname") == null ||
	 * "".equals(request.getParameter("bankname"))) { map.put("success",
	 * "false"); map.put("msg", "开户行为空"); return writeAjaxResponse(response,
	 * JSONUtil.getJson(map)); } else {// 开户行 bankname =
	 * request.getParameter("bankname"); // bankname=new
	 * String(bankname.getBytes("ISO-8859-1"),"UTF-8"); } if
	 * (request.getParameter("bankaddress") == null ||
	 * "".equals(request.getParameter("bankaddress"))) { map.put("success",
	 * "false"); map.put("msg", "开户行所在地为空"); return writeAjaxResponse(response,
	 * JSONUtil.getJson(map)); } else {// 开户行所在地 bankaddress =
	 * request.getParameter("bankaddress"); // bankaddress=new //
	 * String(bankaddress.getBytes("ISO-8859-1"),"UTF-8"); } withdrawType = 2; }
	 * else {// 支付宝帐号 alipay = request.getParameter("alipay"); withdrawType =
	 * 1;// 支付宝 }
	 *//** 调支付系统接口 Start */
	/*
	 * int newid = financeDaoImpl.getMaxId() + 1;// 取当前表中最大ID，加一取得新ID
	 * 
	 * String phone = ""; String userId = ""; if (role != null) { phone =
	 * role.getVmiUserPhone(); userId = role.getVmiUserFid(); } HashMap<String,
	 * String> withdrawMap = new HashMap<String, String>(); withdrawMap =
	 * withdrawParams(phone, newid, amount, "Minus"); String WithdrawInfo =
	 * withdrawMap.get("WithdrawInfo"); if
	 * (!ServerContext.checkWithdrawStatus(WithdrawInfo)) { map.put("success",
	 * "false"); map.put("msg", ServerContext.getMsg()); return
	 * writeAjaxResponse(response, JSONUtil.getJson(map)); }
	 *//** 调支付系统接口 End */
	/*
	 * CL_Finance finance = new CL_Finance(); finance.setFuserId(urid);
	 * finance.setNumber(CacheUtilByCC.getOrderNumber("CL_Finance", "T", 8));
	 * finance.setFcreateTime(new Date()); finance.setFamount(amount);
	 * finance.setFstate(0); finance.setFwithdrawType(withdrawType); if (alipay
	 * != null) { finance.setFalipayId(alipay); } else {
	 * finance.setFbankAccount(bankaccount); finance.setFbankName(bankname);
	 * finance.setFbankAddress(bankaddress); } this.financeDao.save(finance);
	 *//** 添加明细表 Start */
	/*
	 * statementDao.saveStatement(finance.getFid().toString(), null, 5,
	 * finance.getFamount(), -1, finance.getFuserId(), 0, userId);
	 *//** 添加明细表 End *//*
						 * map.put("success", "true"); map.put("msg",
						 * "提现申请提交成功!"); return writeAjaxResponse(response,
						 * JSONUtil.getJson(map)); }
						 */

	/** 提现进度详情查看 */
	@RequestMapping("/app/finance/withdrawDetail")
	@Transactional
	public String withdrawDetail(HttpServletRequest request, HttpServletResponse response) throws IOException {
		HashMap<String, Object> map = new HashMap<String, Object>();
		Integer urid, pageNum, pageSize;
		if (request.getParameter("urid") == null || "".equals(request.getParameter("famount"))) {
			map.put("success", "false");
			map.put("msg", "urid用户ID为空！");
			return writeAjaxResponse(response, JSONUtil.getJson(map));
		} else {
			urid = Integer.parseInt(request.getParameter("urid"));
		}
		if (request.getParameter("pageNum") == null || "".equals(request.getParameter("pageNum"))) {
			map.put("success", "false");
			map.put("msg", "无法获知第几页");
			return writeAjaxResponse(response, JSONUtil.getJson(map));
		} else {
			pageNum = Integer.parseInt(request.getParameter("pageNum"));
		}
		if (request.getParameter("pageSize") == null || "".equals(request.getParameter("pageSize"))) {
			map.put("success", "false");
			map.put("msg", "无法获知每页显示多少条");
			return writeAjaxResponse(response, JSONUtil.getJson(map));
		} else {
			pageSize = Integer.parseInt(request.getParameter("pageSize"));
		}
		if (financeQuery == null) {
			financeQuery = newQuery(CL_FinanceQuery.class, null);
		}
		if (pageNum != null) {
			financeQuery.setPageNumber(pageNum);
		}
		if (pageSize != null) {
			financeQuery.setPageSize(pageSize);
		}
		if (urid != null) {
			financeQuery.setFuserId(urid);
		}
		// if(status!=null){
		// financeQuery.setStatus(status);
		// }
		// if(type!=null){
		// financeQuery.setType(type);
		// }
		financeQuery.setSortColumns("fs.fcreate_time desc");
		Page<CL_Finance> page = financeDao.findPage(financeQuery);
		map.put("success", "true");
		map.put("total", page.getTotalCount());
		map.put("data", page.getResult());
		return writeAjaxResponse(response, JSONUtil.getJson(map));
	}

	/** 提现申请驳回 */
	@RequestMapping("/app/finance/reject")
	@Transactional
	public String reject(HttpServletRequest request, HttpServletResponse response, Integer fid, String famount,
			String fuserId, Integer frejecttype) throws IOException {
		HashMap<String, Object> param = new HashMap<String, Object>();
		int userId = ServerContext.getUseronline().get(request.getSession().getId().toString()).getFuserId();
		CL_Finance finance = financeDaoImpl.getById(fid);
		CL_FinanceStatement statement = this.sta(finance);
		statement.setFremark("提现驳回");
		statementDao.save(statement);
		OrderMsg orderMsg = new OrderMsg();
		orderMsg.setOrder(statement.getNumber());
		orderMsg.setPayState("1");
		orderMsg.setServiceProviderType("0");
		orderMsg.setServiceProvider("0");
		orderMsg.setUserId(userId);
		finance.setFstate(2);
		finance.setFrejectType(frejecttype);
		param.put("finance",finance);
		int i = financeStatementDao.updateBusinessType(orderMsg,param);
		if(i == 1){
			return writeAjaxResponse(response, "success");
		} else {
			return writeAjaxResponse(response, "false");
		}
	}

	/**
	 * 提现调用支付系统
	 * 
	 * @return 返回余额扣除结果
	 */
	private HashMap<String, String> withdrawParams(String phone, int newid, BigDecimal amount, String type) {
		JSONObject jo;
		JSONObject jo1;
		try {
			jo = new JSONObject();
			jo1 = new JSONObject();
			jo1.put("Fid", newid);
			jo1.put("Phone", phone);
			jo1.put("Amount", amount);
			jo1.put("Type", type);
			jo.put("data", jo1);
			HashMap<String, String> params = new HashMap<String, String>();
			params.put("WithdrawInfo", Base64.encode(jo.toString().getBytes()));
			return params;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 充值明细表添加
	 * 
	 * @throws ParseException
	 */
	@RequestMapping("/app/finance/rechargeRecord")
	@Transactional
	public String rechargeRecord(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ParseException {
		HashMap<String, Object> map = new HashMap<String, Object>();
		String fuserid = "";// VMI用户ID
		String ftel = "";// 用户手机号
		String relatedId;// 相关表ID
		BigDecimal famount;// 金额
		String isFirst;// 是否首次充值
		int fuserroleId = 0;
		// Date ftime;//充值时间
		String ftime;// 充值时间
		if (request.getParameter("famount") == null || "".equals(request.getParameter("famount"))) {
			map.put("success", "false");
			map.put("msg", "famount empty");
			return writeAjaxResponse(response, JSONUtil.getJson(map));
		} else {
			famount = new BigDecimal(request.getParameter("famount"));
		}
		if (request.getParameter("relatedId") == null || "".equals(request.getParameter("relatedId"))) {
			map.put("success", "false");
			map.put("msg", "relatedId empty");
			return writeAjaxResponse(response, JSONUtil.getJson(map));
		} else {
			relatedId = request.getParameter("relatedId");
		}
		if (request.getParameter("ftime") == null || "".equals(request.getParameter("ftime"))) {
			map.put("success", "false");
			map.put("msg", "ftime empty");
			return writeAjaxResponse(response, JSONUtil.getJson(map));
		} else {

			// ftime=sdf.parse(request.getParameter("ftime"));
			ftime = request.getParameter("ftime");
		}
		// if(request.getParameter("fuserid")==null ||
		// "".equals(request.getParameter("fuserid"))){
		// map.put("success", "false");
		// map.put("msg","VMi fuserid empty");
		// return writeAjaxResponse(response, JSONUtil.getJson(map));
		// }else{
		// fuserid=request.getParameter("fuserid");
		// CL_UserRole role=userRoleDao.getByVmiUserFidAndRoleId(fuserid, 1);
		// if(role!=null)
		// {
		// fuserroleId=role.getId();
		// }
		// else
		// {
		// map.put("success", "false");
		// map.put("msg","post VmiUserId not Exist");
		// return writeAjaxResponse(response, JSONUtil.getJson(map));
		// }
		if (request.getParameter("ftel") == null || "".equals(request.getParameter("ftel"))) {
			map.put("success", "false");
			map.put("msg", "ftel empty");
			return writeAjaxResponse(response, JSONUtil.getJson(map));
		} else {
			ftel = request.getParameter("ftel");
			CL_UserRole role = userRoleDao.getByPhoneAndRoleId(ftel, 1);
			if (role != null) {
				fuserid = role.getVmiUserFid();
				fuserroleId = role.getId();

				/*
				 * List<CL_CouponsDetail> cdDao =
				 * couponsDetailDao.IsExistByUserRoleId(fuserroleId);
				 * if(cdDao.size()>0){ map.put("success", "false");
				 * map.put("msg","is sended！"); return
				 * writeAjaxResponse(response, JSONUtil.getJson(map)); }
				 */
			} else {
				map.put("success", "false");
				map.put("msg", "post ftel not Exist");
				return writeAjaxResponse(response, JSONUtil.getJson(map));
			}
		}

		/*
		 * if(request.getParameter("isFirst")==null ||
		 * "".equals(request.getParameter("isFirst"))){ map.put("success",
		 * "false"); map.put("msg","isFirst empty"); return
		 * writeAjaxResponse(response, JSONUtil.getJson(map)); }else{
		 * isFirst=request.getParameter("isFirst"); }
		 * if("true".equals(isFirst)&&famount.compareTo(new
		 * BigDecimal(10))>=0)//是首次充值,并且充值金额大于等于10 { // int
		 * creator=Integer.parseInt(request.getSession().getAttribute(
		 * "userRoleId").toString()); Integer[] ids=new Integer[]{7,7,7,8,9};
		 * Date date=new Date(); Calendar cal=Calendar.getInstance();
		 * cal.setTime(date); cal.add(Calendar.MONTH, 1); for (Integer id : ids)
		 * { couponsDetailDao.save(new CL_CouponsDetail(id, fuserroleId,
		 * 0,fuserroleId, date, 0, date, cal.getTime())); } }
		 */

		CL_FinanceStatement fs = statementDao.getByRelatedId(relatedId, 999);
		if (fs == null) {
			statementDao.saveStatement(relatedId, null, 6, famount, 1, fuserroleId, 0, fuserid, ftime);
			map.put("success", "true");
			map.put("msg", "saved");
		} else {
			map.put("success", "true");
			map.put("msg", "not saved");
		}
		return writeAjaxResponse(response, JSONUtil.getJson(map));
	}

	/** 收支明细查询 */
	@RequestMapping("/app/statement/loadStatement")
	public String loadStatement(HttpServletRequest request, HttpServletResponse response) throws IOException {
		HashMap<String, Object> map = new HashMap<String, Object>();
		Integer pageNum, pageSize;
		Integer fid = null;
		if (request.getParameter("pageNum") == null || "".equals(request.getParameter("pageNum"))) {
			map.put("success", "false");
			map.put("msg", "无法获知第几页");
			return writeAjaxResponse(response, JSONUtil.getJson(map));
		} else {
			pageNum = Integer.parseInt(request.getParameter("pageNum"));
		}
		if (request.getParameter("pageSize") == null || "".equals(request.getParameter("pageSize"))) {
			map.put("success", "false");
			map.put("msg", "无法获知每页显示多少条");
			return writeAjaxResponse(response, JSONUtil.getJson(map));
		} else {
			pageSize = Integer.parseInt(request.getParameter("pageSize"));
		}
		if (statementQuery == null) {
			statementQuery = newQuery(FinanceStatementQuery.class, null);
		}
		if (pageNum != null) {
			statementQuery.setPageNumber(pageNum);
		}
		if (pageSize != null) {
			statementQuery.setPageSize(pageSize);
		}

		if (request.getSession() == null || request.getSession().getId() == null
				|| request.getSession().getId().equals("")) {
			map.put("success", "false");
			map.put("msg", "请重新登录！");
			return writeAjaxResponse(response, JSONUtil.getJson(map));
		}
		// String sessionId=request.getSession().getId().toString();
		Util_UserOnline useronline = ServerContext.getUseronline().get(request.getSession().getId().toString());
		String ftel = "";
		ftel = useronline.getVmiUserPhone();
		/*
		 * fid=useronline.getFuserId(); if(fid!=null) {
		 * statementQuery.setFuserid(fid.toString()); }
		 */
		if (ftel != null && !ftel.equals("")) {
			statementQuery.setFusername(ftel);
			statementQuery.setSortColumns("bt.fcreate_time desc");
			Page<CL_FinanceStatement> page = statementDao.findPagePays(statementQuery);
			map.put("total", page.getTotalCount());
			map.put("data", page.getResult());
		} else {
			map.put("total", 0);
			map.put("data", new ArrayList<>());
		}
		map.put("success", "true");
		return writeAjaxResponse(response, JSONUtil.getJson(map));
	}
	
	public CL_FinanceStatement sta(CL_Finance finance){
		CL_FinanceStatement statement = new CL_FinanceStatement();
		String number = CacheUtilByCC.getOrderNumber("cl_finance_statement", "L", 8);
		statement.setNumber(number);
		statement.setFrelatedId(finance.getFid().toString());
		statement.setForderId(finance.getNumber());
		statement.setFbusinessType(5);//提现
		statement.setFamount(finance.getFamount());
		statement.setFtype(1);//驳回加钱
		statement.setFuserroleId(finance.getFuserId());
		statement.setFuserid(userRoleDao.getById(finance.getFuserId()).getVmiUserFid());
		//statement.setFpayType(0);//支付方式？null？
		statement.setFbalance(BigDecimal.ZERO);//先设0
		statement.setFcreateTime(new Date());
		statement.setFreight(finance.getFamount());
		return statement;
	}

}