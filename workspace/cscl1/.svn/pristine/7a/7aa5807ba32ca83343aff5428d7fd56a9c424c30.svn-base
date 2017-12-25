package com.pc.appInterface.protocol;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringEscapeUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.pc.controller.BaseController;
import com.pc.dao.UserRole.impl.UserRoleDao;
import com.pc.dao.addto.IaddtoDao;
import com.pc.dao.bank.IbankDao;
import com.pc.dao.couponsDetail.ICouponsDetailDao;
import com.pc.dao.financeStatement.IFinanceStatementDao;
import com.pc.dao.order.IorderDao;
import com.pc.dao.orderDetail.IorderDetailDao;
import com.pc.dao.protocol.IprotocolDao;
import com.pc.dao.select.IUtilOptionDao;
import com.pc.model.CL_Addto;
import com.pc.model.CL_Bank;
import com.pc.model.CL_FinanceStatement;
import com.pc.model.CL_Order;
import com.pc.model.CL_Protocol;
import com.pc.model.CL_UserRole;
import com.pc.model.Util_Option;
import com.pc.util.CacheUtilByCC;
import com.pc.util.JSONUtil;
import com.pc.util.MD5Util;
import com.pc.util.ServerContext;
import com.pc.util.String_Custom;
import com.pc.util.pay.OrderMsg;
import com.pc.util.pay.PayUtil;

@Controller
public class AppProtocolController extends BaseController {
	@Resource
	private IprotocolDao protocolDao;
	@Resource
	private IUtilOptionDao optionDao;
	@Resource
	private IorderDetailDao orderDetailDao;
	@Resource
	private IorderDao orderDao;
	@Resource
	private IaddtoDao iaddtoDao;
	@Resource
	private IFinanceStatementDao statementDao;
	@Resource
	private UserRoleDao userRoleDao;
	@Resource
	private ICouponsDetailDao couponsDetailDao;
	@Resource
	private IbankDao bankDao;

	/*** 查询用户协议信息 */
	@RequestMapping("/app/protocol/findProtocol")
	public String findProtocol(HttpServletRequest request, HttpServletResponse response) throws IOException {
		Integer userId;
		HashMap<String, Object> m = new HashMap<String, Object>();
		if (!ServerContext.getUseronline().containsKey(request.getSession().getId().toString())) {
			m.put("success", "false");
			m.put("msg", "未登录！");
			return writeAjaxResponse(response, JSONUtil.getJson(m));
			// if(ServerContext.getUseronline().get(request.getSession().getId())!=null)
		}

		System.out.println(ServerContext.getUseronline().get(request.getSession().getId().toString()).getFuserId());
		userId = ServerContext.getUseronline().get(request.getSession().getId().toString()).getFuserId();

		List<CL_Protocol> pros = protocolDao.getByUserId(userId);
		m.put("success", "true");
		m.put("data", pros);
		return writeAjaxResponse(response, JSONUtil.getJson(m));
	}

	/*** 查询零担单位表信息 */
	@RequestMapping("/app/protocol/findUnit")
	public String findUnit(HttpServletRequest request, HttpServletResponse response) throws IOException {
		List<Util_Option> list = optionDao.getByUnit();
		HashMap<String, Object> m = new HashMap<String, Object>();
		// HashMap<String, Object> map = new HashMap<String, Object>();
		m.put("success", "true");
		m.put("data", list);
		return writeAjaxResponse(response, JSONUtil.getJson(m));
	}

	/*** APP查询订单 支付方式和增值服务方式 */
	/*@RequestMapping("/app/protocol/payInfoAndServer")
	public String payInfoAndServer(HttpServletRequest request, HttpServletResponse response) throws IOException {
		HashMap<String, Object> m = new HashMap<String, Object>();
		HashMap<String, Object> map = new HashMap<String, Object>();
		List<Map<String, Object>> list = new ArrayList<>();
		Integer orderId = null, protocolType = null;
		String type = "";
		String checkDate = "", checkUrl = "";
		// String payDate="",payDateUrl ="" ;
		type = request.getParameter("type");
		if (request.getParameter("orderId") == null || "".equals(request.getParameter("orderId"))) {
			m.put("success", "false");
			m.put("msg", "订单有误！");
			return writeAjaxResponse(response, JSONUtil.getJson(m));
		} else {
			orderId = Integer.valueOf(request.getParameter("orderId"));
		}
		CL_Order ord = orderDao.getById(orderId);

		if (!"1".equals(type)) {// 获取增值服务；

			// 20160906 cd
			// APP订单记录及订单详情再次付款前，先进行订单支付校验，解决订单支付成功后由于异常导致订单没有充值记录及余额问题；
			map = orderDao.autoPayOrderFreight(ord);
			if (map.get("payed") != null && map.get("payed").equals("true")) {
				m.put("success", "false");
				m.put("msg", "该订单已经付款");
				return writeAjaxResponse(response, JSONUtil.getJson(m));
			}

			protocolType = ord.getProtocolType();
			map.put("orderNumber", ord.getNumber());
			map.put("orderFreight", ord.getFreight());
			map.put("orderMileage", ord.getMileage());
			map.put("orderId", ord.getId());

			// 20160714增加返回订单的好运券明细ID及面额;
			CL_CouponsDetail detail = null;
			detail = this.couponsDetailDao.getById(ord.getCouponsDetailId());
			if (detail != null) {
				map.put("couponId", detail.getId().toString());
				map.put("couponDollars", detail.getDollars().toString());
			} else {
				map.put("couponId", "");
				map.put("couponDollars", "");
			}

			if (request.getParameter("checkDate") == null || "".equals(request.getParameter("checkDate"))) {
				map.put("success", "false");
				map.put("msg", "校验参数有问题，请重新支付,数据有误");
				return writeAjaxResponse(response, JSONUtil.getJson(map));
			} else {
				checkDate = request.getParameter("checkDate");
			}
			if (request.getParameter("checkUrl") == null || "".equals(request.getParameter("checkUrl"))) {
				map.put("success", "false");
				map.put("msg", "校验参数有问题，请重新支付,数据有误");
				return writeAjaxResponse(response, JSONUtil.getJson(map));
			} else {
				checkUrl = request.getParameter("checkUrl");
			}
			if (ServerContext.cancelOrderPay(checkUrl, checkDate)) {
				orderDao.updateStatusByOrderId(orderId, 2);
				String fuserid = userRoleDao.getById(ord.getCreator()).getVmiUserFid();
				statementDao.saveStatement(ord.getId().toString(), ord.getNumber(), 1, ord.getFreight(), -1,
						ord.getCreator(), ord.getFpayMethod(), fuserid);
				map.put("success", "false");
				map.put("msg", ServerContext.getMsg());
				return writeAjaxResponse(response, JSONUtil.getJson(map));
			}
			JSONArray JOArry = new JSONArray();
			String[] title = new String[] { "装卸", "电子回单", "回单原件", "上楼", "代收货款" };
			String[] context = new String[] { "线下协商、支付", "免费", "线下协商、支付", "线下协商、支付", "免费" };
			for (int i = 0; i < title.length; i++) {
				JSONObject JO = new JSONObject();
				JO.put("id", i);
				JO.put("title", title[i]);
				JO.put("context", context[i]);
				JO.put("isc", 0);
				JOArry.add(i, JO);
			}
			if (protocolType == 0) {
				// 根据卸货地址数判断是否可以支持运费到付 BY CC 2016-05-17 START
				if (this.orderDetailDao.getByOrderId(orderId, 2).size() == 1) {

					map.put("payMethod", "0,1,2,3,4");
				} else {
					JOArry.remove(title.length - 1);
					map.put("payMethod", "0,1,2,3");
				}
				// 根据卸货地址数判断是否可以支持运费到付 BY CC 2016-05-17 START
				// map.put("payMethod","0,1,2,3,4");//2016-05-17 CC BAK
				map.put("server", JOArry);
				list.add(map);
				m.put("success", "true");
				m.put("data", list);
				return writeAjaxResponse(response, JSONUtil.getJson(m));
			}
			if (protocolType == 1) {
				// map.put("server","[{\"id\":\"0\",\"title\":\"装卸\",\"context\":\"线下协商、支付\",\"isc\":\"0\"},{\"id\":\"1\",\"title\":\"电子回单\",\"context\":\"免费\",\"isc\":\"0\"},{\"id\":\"2\",\"title\":\"回单原件\",\"context\":\"免费\",\"isc\":\"0\"},{\"id\":\"3\",\"title\":\"上楼\",\"context\":\"线下协商、支付\",\"isc\":\"0\"},{\"id\":\"4\",\"title\":\"代收货款\",\"context\":\"免费\",\"isc\":\"0\"}]");
				map.put("server", JOArry);
				if (userRoleDao.getById(ord.getCreator()).isProtocol()) {
					// 根据卸货地址数判断是否可以支持运费到付 BY CC 2016-05-17 START
					if (this.orderDetailDao.getByOrderId(orderId, 2).size() == 1 && ord.getType() != 3) {
						map.put("payMethod", "0,1,2,3,4,5");
					} else {
						JOArry.remove(title.length - 1);
						map.put("payMethod", "0,1,2,3,5");
					}
					// 根据卸货地址数判断是否可以支持运费到付 BY CC 2016-05-17 END
					// map.put("payMethod","0,1,2,3,4,5");//2016-05-17 CC BAK

				} else {
					// 根据卸货地址数判断是否可以支持运费到付 BY CC 2016-05-17 START
					if (this.orderDetailDao.getByOrderId(orderId, 2).size() == 1 && ord.getType() != 3) {
						map.put("payMethod", "0,1,2,3,4");
					} else {
						JOArry.remove(title.length - 1);
						map.put("payMethod", "0,1,2,3");
					}
					// 根据卸货地址数判断是否可以支持运费到付 BY CC 2016-05-17 END
					// map.put("payMethod","0,1,2,3,4");//2016-05-17 CC BAK

				}
			}
			list.add(map);
			m.put("success", "true");
			m.put("data", list);
			return writeAjaxResponse(response, JSONUtil.getJson(m));
		}
		if ("1".equals(type)) {// 生成追加费用订单
			// 20160912 cd APP 先进行订单异常追加运费支付校验，解决追加运费支付成功后由于异常导致订单没有追加记录及余额问题；
			List<CL_Addto> addtolist = iaddtoDao.getNonPaymentByOrderID(orderId);
			String payNumber = "";
			map.put("addtype", "0");
			if (addtolist.size() > 0) {

				CL_Addto addto = addtolist.get(0);

				HashMap<String, Object> tmap = iaddtoDao.autoPayAddtoFreight(addto);

				if (tmap.get("payed") != null && tmap.get("payed").equals("true")) {
					map.put("addtype", "1");
					map.put("prompt", "上次追加费用异常,已修复,是否继续追加？");
					m.put("msg", "上次追加费用异常,已修复,是否继续追加？");
				} else if (tmap.get("success").equals("true")) {
					payNumber = addto.getFpayNumber();
				} else {
					m.put("success", "false");
					m.put("msg", "存在异常追加，异常金额：" + tmap.get("fcost") + "，异常信息：" + tmap.get("msg"));
					return writeAjaxResponse(response, JSONUtil.getJson(m));
				}
			}

			if (payNumber.equals("")) {
				payNumber = "PP" + CacheUtil.getPayNumber(iaddtoDao);
				CL_Addto add = new CL_Addto();
				add.setFcreatTime(new Date());
				add.setFstatus(0);
				add.setFpayNumber(payNumber);
				add.setForderId(orderId);
				add.setFcreateor(ord.getCreator());
				this.iaddtoDao.save(add);
			}

			map.put("payNumber", payNumber);
			map.put("orderId", orderId);
			// 增加月结订单的追加费用支付方式也可以月结;
			if (ord != null) {
				if (ord.getFpayMethod() == 5) {
					map.put("payMethod", "0,1,2,3,5");
				} else {
					map.put("payMethod", "0,1,2,3");
				}
			}

			list.add(map);
			m.put("success", "true");
			m.put("data", list);
			request.getSession().setAttribute("map", map);
			return writeAjaxResponse(response, JSONUtil.getJson(m));
		}
		m.put("success", "false");
		m.put("msg", "订单有误！");
		return writeAjaxResponse(response, JSONUtil.getJson(m));
	}*/

	// @RequestMapping("/app/protocol/payOrderBank")
	// // @Transactional
	// public String payOrderBank(HttpServletRequest request,
	// HttpServletResponse response) throws IOException {
	// String number = request.getParameter("number"); // 订单号
	// String fbankId = request.getParameter("fbankId"); // 银行卡id
	// String fverifyCheckCode = request.getParameter("fverifyCheckCode"); //
	// payOrder银行支付返回参数
	// String fverifyCode = request.getParameter("fverifyCode"); // 校验码
	//
	// String msg = String_Custom.parametersEmpty(new String[] {
	// fverifyCheckCode, fverifyCode });
	// if (msg.length() > 0)
	// return this.poClient(response, false, msg);
	//
	// int fcusid =
	// ServerContext.getUseronline().get(request.getSession().getId().toString()).getFuserId();
	// // 根据订单编号 获取订单信息
	// CL_Order order = orderDao.getByNumber(number);
	// if (order == null)
	// return this.poClient(response, false, "获取不到该订单信息");
	//
	// // 读取是否有相关支付订单生成
	// List<CL_FinanceStatement> fstatement =
	// financeStatementDao.getforderId(number);
	// String FNumber = ""; // 支付明细订单号
	// if (fstatement.size() > 1)
	// return this.poClient(response, false, "该订单生成多条支付记录，已作废");
	//
	// CL_Bank userBank = bankDao.getBankInfo(Integer.parseInt(fbankId), fcusid,
	// 2);
	// if (userBank == null)
	// return this.poClient(response, false, "获取不到该银行卡");
	//
	// BigDecimal actuaAmount = order.getFreight();
	//
	// CL_CouponsDetail detail =
	// this.couponsDetailDao.getById(order.getCouponsDetailId());
	// CL_Coupons cp = this.couponsDao.getById(detail.getCouponsId());
	// if (cp != null) {
	// actuaAmount = actuaAmount.subtract(cp.getDollars());
	// }
	//
	// LinkedHashMap<String, Object> bankModel = new LinkedHashMap<>();
	// bankModel.put("orderNumber", fstatement.get(0).getNumber()); // 订单编号
	// bankModel.put("payerId", MD5Util.getMD5String(fcusid +
	// "ylhy").substring(0, 30));// 付款人
	// bankModel.put("bankCardBindId", userBank.getNumber()); // 银行卡绑定ID
	// bankModel.put("payAmount", actuaAmount + ""); // 支付金额
	// bankModel.put("verifyCheckCode", fverifyCheckCode);
	// bankModel.put("verifyCode", fverifyCode);
	// bankModel.put("notificationURL", "");
	//
	// HashMap<String, Object> param2 = new HashMap<>();
	// param2.put("fbusinessType", 0);
	// String responeData2 = PayUtil.payOrderToPaySystem_CheckOutBank(param2,
	// bankModel);
	// JSONObject jo2 = JSONObject.fromObject(responeData2);
	// if ("true".equals(jo2.get("success").toString())) {
	// return this.poClient(response, true);
	// } else {
	// return this.poClient(response, false, jo2.get("msg").toString());
	// }
	// }
	/*** 追加费用 **/
	@RequestMapping("/app/protocol/addPay")
	public String addPay(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String number = request.getParameter("number"); // 订单ID
		String payType = request.getParameter("payType"); // 支付方式
		String fcost = request.getParameter("fcost"); // 金额
		String fbankId = request.getParameter("fbankId"); // 银行卡支付ID（不使用银行卡不需要传输）
		String fpaypassword = request.getParameter("fpaypassword"); // 支付密码

		String msg = String_Custom.parametersEmpty(new String[] { number, payType, fcost });
		if (msg.length() != 0)
			return this.poClient(response, false, msg);

		// 格式判断
		if (String_Custom.noNumber(payType))
			return this.poClient(response, false, "支付方式格式不正确");
		if (String_Custom.noFloat(fcost))
			return this.poClient(response, false, "追加运费金额格式不正确");

		int payToType = Integer.parseInt(payType);
		Integer fcusid = ServerContext.getUseronline().get(request.getSession().getId().toString()).getFuserId();
		String fvmiId = ServerContext.getUseronline().get(request.getSession().getId().toString()).getFuserid();
		// 判断支付密码是否正确
		if (payToType == 0 ) {
			CL_UserRole role = userRoleDao.getById(fcusid);
			// 验证支付密码是否正确
			if (String_Custom.noNull(fpaypassword))
				return this.poClient(response, false, "支付密码不可为空!");
			// 判断用户输入支付密码是否正确

			if (!fpaypassword.equals(role.getFpaypassword()))
				return this.poClient(response, false, "支付密码错误!");

		}

		// 根据订单编号 获取订单信息
		CL_Order order = orderDao.getByNumber(number);
		if (order == null)
			return this.poClient(response, false, "获取不到该订单信息");

		if (order.getStatus() != 2 && order.getStatus() != 3)
			return this.poClient(response, false, "该订单状态不能追加费用");

		// List<CL_Addto> addtolist =
		// iaddtoDao.getNonPaymentByOrderID(order.getId());
		String payNumber = "";
		// for(int i=0;i<addtolist.size();i++)
		// {
		//
		// }
		// if (addtolist.size() > 0) {
		//
		// CL_Addto addto = addtolist.get(0);
		//
		// HashMap<String, Object> tmap = iaddtoDao.autoPayAddtoFreight(addto);
		//
		// if (tmap.get("payed") != null && tmap.get("payed").equals("true")) {
		// map.put("addtype", "1");
		// map.put("prompt", "上次追加费用异常,已修复,是否继续追加？");
		// m.put("msg", "上次追加费用异常,已修复,是否继续追加？");
		// } else if (tmap.get("success").equals("true")) {
		// payNumber = addto.getFpayNumber();
		// } else {
		// m.put("success", "false");
		// m.put("msg", "存在异常追加，异常金额：" + tmap.get("fcost") + "，异常信息：" +
		// tmap.get("msg"));
		// return writeAjaxResponse(response, JSONUtil.getJson(m));
		// }
		// }

//		payNumber = "PP" + CacheUtil.getPayNumber(iaddtoDao);
		payNumber = CacheUtilByCC.getOrderNumber("CL_Addto", "PP", 8);
		CL_Addto add = new CL_Addto();
		add.setFcreatTime(new Date());
		add.setFstatus(0);
		add.setNumber(payNumber);
		add.setForderId(order.getId());
		add.setFcreateor(fcusid);
		
		this.iaddtoDao.save(add);

		/***************** 对接支付系统所需参数 ******************************/
		LinkedHashMap<String, Object> modellist = new LinkedHashMap<>();
		modellist.put("orderNumber", ""); // 订单编号
		modellist.put("orderCreateTime", String_Custom.getPayOrderCreateTime());
		modellist.put("payerId", MD5Util.getMD5String(fcusid + "ylhy").substring(0, 30));
		modellist.put("serviceProvider", ""); // 支付方式
		modellist.put("serviceProviderType", ""); // 支付方
		modellist.put("bankCardBindId", "");
		modellist.put("payAmount", fcost + "");
		modellist.put("description", "东运物流有限公司");
		modellist.put("notificationURL", "");

		HashMap<String, Object> param = new HashMap<>();
		param.put("fcusid", fcusid);
		param.put("frelatedId", add.getFid().toString());
		param.put("forderId", order.getNumber());
		param.put("famount", new BigDecimal(fcost));
		param.put("userDao", userRoleDao);
		param.put("FinanceDao", statementDao);
		param.put("freight", new BigDecimal(fcost));
		/***********************************************/

		String respPPayURL = "";

		switch (payToType) {
		case 0: {
			
			String FNumber = CacheUtilByCC.getOrderNumber("cl_finance_statement", "L", 8);

			CL_FinanceStatement statement = new CL_FinanceStatement();
			statement.setFcreateTime(new Date());
			statement.setNumber(FNumber);
			statement.setFrelatedId(add.getFid().toString());
			statement.setForderId(order.getNumber());
			statement.setFbusinessType(2); // 补交货款/追加
			statement.setFamount(new BigDecimal(fcost));
			statement.setFtype(-1);
			statement.setFuserroleId(fcusid);
			statement.setFuserid(fvmiId);
			statement.setFpayType(0);
			statement.setFremark(order.getNumber() + "订单支付");
			statement.setFbalance(new BigDecimal("0"));
			statement.setFstatus(0);
			statement.setFreight(new BigDecimal(fcost));

			statementDao.save(statement);
			
			
			OrderMsg ordermsg = new OrderMsg();
			ordermsg.setPayState("1"); // 支付成功
			ordermsg.setServiceProvider("0");//余额支付;
			ordermsg.setServiceProviderType("0");// 余额支付;
			ordermsg.setOrder(FNumber);
			
			int status = statementDao.updateBusinessType(ordermsg,param);
			if (status == 0)
				return this.poClient(response, false, "支付失败,请确认余额是否充足!");
			respPPayURL = "{\"number\":\"" + add.getFid().toString() + "\"}";
		}
			break;
		case 1: {
			modellist.put("serviceProvider", "ALI"); // 支付方式
			modellist.put("serviceProviderType", "SDK"); // 支付方
			param.put("fbusinessType", 2);
			param.put("ftype", -1); // 支出
			param.put("fpayType", 1);

			respPPayURL = PayUtil.payOrderToPaySystem_ZFBWX(param, modellist);

		}
			break;
		case 2: {
			modellist.put("serviceProvider", "WX"); // 支付方式
			modellist.put("serviceProviderType", "SDK"); // 支付方
			param.put("fbusinessType", 2);
			param.put("ftype", -1); // 支出
			param.put("fpayType", 2);

			respPPayURL = PayUtil.payOrderToPaySystem_ZFBWX(param, modellist);
		}
			break;
		case 3: {
			// 银联
			// 判断银行卡id 是否有效
			if (String_Custom.noNull(fbankId))
				return this.poClient(response, false, "银联支付缺少必要参数");
			if (String_Custom.noNumber(fbankId))
				return this.poClient(response, false, "银联id不正确");

			CL_Bank userBank = bankDao.getBankInfo(Integer.parseInt(fbankId), fcusid, 2);
			if (userBank == null)
				return this.poClient(response, false, "获取不到该银行卡");
			//判断时间 fbankpayTime
			long dateTimer=(System.currentTimeMillis()-userBank.getFbankpayTime().getTime())/1000;
			if(dateTimer<60)
			{
				dateTimer=60-dateTimer;
				return this.poClient(response, false,"请等待"+dateTimer+"秒后再试");
			}
			userBank.setFbankpayTime(new Date());
			bankDao.update(userBank);
			modellist.put("serviceProvider", "BANK"); // 支付方
			modellist.put("serviceProviderType", "PAB"); // 支付方
			modellist.put("bankCardBindId", userBank.getNumber()); // 支付方
			param.put("fpayType", 3);
			param.put("fbusinessType", 2);
			param.put("ftype", -1); // 支出

			respPPayURL = PayUtil.payOrderToPaySystem_Bank(param, modellist);

		}
			break;
		case 5:
			//月结
			String FNumber = CacheUtilByCC.getOrderNumber("cl_finance_statement", "L", 8);

			CL_FinanceStatement statement = new CL_FinanceStatement();
			statement.setFcreateTime(new Date());
			statement.setNumber(FNumber);
			statement.setFrelatedId(add.getFid().toString());
			statement.setForderId(order.getNumber());
			statement.setFbusinessType(2); // 追加
			statement.setFamount(new BigDecimal(fcost));
			statement.setFtype(-1);
			statement.setFuserroleId(fcusid);
			statement.setFuserid(fvmiId);
			statement.setFpayType(5);
			statement.setFremark(order.getNumber() + "订单支付");
			statement.setFbalance(userRoleDao.getById(fcusid).getFbalance());
			statement.setFstatus(1);
			statement.setFreight(new BigDecimal(fcost));
			statementDao.save(statement);
			add.setFstatus(1);
			add.setFpayMethod(5);
			add.setFcost(new BigDecimal(fcost));
			iaddtoDao.update(add);
			order.setFtotalFreight(order.getFtotalFreight().add(statement.getFamount()));
			orderDao.update(order);
			break;

		default:
			break;
		}

		if (payToType != 0 && payToType != 5) {
			// 获取支付系统返回参数
			try {
				JSONObject jo = JSONObject.fromObject(respPPayURL);
				if ("true".equals(jo.get("success").toString())) {
					respPPayURL = jo.get("data").toString();
					Integer code = jo.getInt("code");
					if (code == 1000 && respPPayURL.length() == 0)
						return this.poClient(response, false, "用户已完成付款，请点击完成！");
				} else {
					String msg2 = jo.get("msg").toString();
					if ("未知错误".equals(jo.get("msg").toString())) {
						msg2 = "请一分钟后重试..";
					}

					return this.poClient(response, false, msg2);
				}
				switch (payToType) {
				case 1:
					respPPayURL = "{\"number\":\"" + jo.get("number").toString()  + "\",\"url\":\""
							+ StringEscapeUtils.escapeJava(respPPayURL) + "\"}";
					break;
				case 2:
					respPPayURL = "{\"number\":\"" + jo.get("number").toString()  + "\",\"url\":" + respPPayURL + "}";
					break;
				case 3:
					respPPayURL = "{\"number\":\"" + jo.get("number").toString()  + "\",\"url\":\"" + respPPayURL + "\"}";
					break;
				}
			} catch (StringIndexOutOfBoundsException e) {
				return this.poClient(response, false, "支付系统异常");
			}
		}

		

		return this.poClient(response, true, respPPayURL);

		// Integer orderId = null, payMethod = null, userId = null;
		// BigDecimal fcost;
		// String payNumber = null, fremark = "", checkDate2 = "", checkUrl =
		// "";
		// String checkDate = "", payDate = "", payDateUrl = "";
		//
		// HashMap<String, Object> map = new HashMap<String, Object>();
		// if (request.getParameter("orderId") == null ||
		// "".equals(request.getParameter("orderId"))) {
		// map.put("success", "false");
		// map.put("msg", "请先选择订单");
		// return writeAjaxResponse(response, JSONUtil.getJson(map));
		// } else {
		// orderId = Integer.parseInt(request.getParameter("orderId"));
		// }
		// if (request.getParameter("payDateUrl") == null ||
		// "".equals(request.getParameter("payDateUrl"))) {
		// map.put("success", "false");
		// map.put("msg", "payDateUrl为空");
		// return writeAjaxResponse(response, JSONUtil.getJson(map));
		// } else {
		// payDateUrl = request.getParameter("payDateUrl").toString();
		// }
		// if (request.getParameter("userId") == null ||
		// "".equals(request.getParameter("userId"))) {
		// map.put("success", "false");
		// map.put("msg", "未登录！");
		// return writeAjaxResponse(response, JSONUtil.getJson(map));
		// } else {
		// userId = Integer.parseInt(request.getParameter("userId"));
		// }
		// if (request.getParameter("payMethod") == null ||
		// "".equals(request.getParameter("payMethod"))) {
		// map.put("success", "false");
		// map.put("msg", "支付方式有问题");
		// return writeAjaxResponse(response, JSONUtil.getJson(map));
		// } else {
		// payMethod =
		// Integer.valueOf(request.getParameter("payMethod").toString());
		// }
		// if (request.getParameter("fcost") == null ||
		// "".equals(request.getParameter("fcost"))) {
		// map.put("success", "false");
		// map.put("msg", "请输入金额");
		// return writeAjaxResponse(response, JSONUtil.getJson(map));
		// } else {
		// fcost =
		// BigDecimal.valueOf(Double.parseDouble(request.getParameter("fcost").toString()));
		// }
		// if (request.getParameter("payNumber") == null ||
		// "".equals(request.getParameter("payNumber"))) {
		// map.put("success", "false");
		// map.put("msg", "支付方式有问题");
		// return writeAjaxResponse(response, JSONUtil.getJson(map));
		// } else {
		// payNumber = request.getParameter("payNumber").toString();
		// }
		// if (request.getParameter("fremark") == null ||
		// "".equals(request.getParameter("fremark"))) {
		// fremark = "";
		// } else {
		// fremark = request.getParameter("fremark").toString();
		// }
		//
		// List<CL_Addto> addlist = iaddtoDao.getByNumber(payNumber);
		// CL_Addto add;
		// if (addlist.size() > 0) {
		// add = addlist.get(0);
		// add.setFcost(fcost);
		// add.setFpayMethod(payMethod);
		// } else {
		// map.put("success", "false");
		// map.put("msg", "追加数据有误！");
		// return writeAjaxResponse(response, JSONUtil.getJson(map));
		// }
		//
		// if (payMethod != 5) {
		// if (request.getParameter("checkUrl") == null ||
		// "".equals(request.getParameter("checkUrl"))) {
		// map.put("success", "false");
		// map.put("msg", "校验参数有问题，请重新支付,数据有误");
		// return writeAjaxResponse(response, JSONUtil.getJson(map));
		// } else {
		// checkUrl = request.getParameter("checkUrl");
		// }
		// // 校验第三方支付充值操作是否成功 ，余额支付不需要校验 BY CC start
		//
		// // 校验第三方支付充值操作是否成功 ，余额支付不需要校验 BY CC start
		// if (payMethod != 0 && payMethod != 3) {
		// if (request.getParameter("checkDate2") == null ||
		// "".equals(request.getParameter("checkDate2"))) {
		// map.put("success", "false");
		// map.put("msg", "校验充值参数有问题，请重新支付,数据有误");
		// return writeAjaxResponse(response, JSONUtil.getJson(map));
		// } else {
		// checkDate2 = request.getParameter("checkDate2");
		// }
		// if (!ServerContext.cancelOrderPay(checkUrl, checkDate2)) {
		// map.put("success", "false");
		// map.put("msg", ServerContext.getMsg());
		// return writeAjaxResponse(response, JSONUtil.getJson(map));
		// }
		// }
		// if (request.getParameter("checkDate") == null ||
		// "".equals(request.getParameter("checkDate"))) {
		// map.put("success", "false");
		// map.put("msg", "校验参数有问题，请重新支付,数据有误");
		// return writeAjaxResponse(response, JSONUtil.getJson(map));
		// } else {
		// checkDate = request.getParameter("checkDate");
		// }
		//
		// if (ServerContext.cancelOrderPay(checkUrl, checkDate)) {
		// if (addlist.size() >= 1) {
		// String newpayNumber = CacheUtil.getPayNumber(iaddtoDao);
		// /*
		// * CL_Addto add1=new CL_Addto(); add1.setFcreatTime(new
		// * Date()); add1.setFstatus(1);
		// * add1.setFpayNumber(payNumber); add1.setForderId(orderId);
		// * add1.setFcost(fcost); add1.setFcreateor(userId);
		// * add1.setFpayMethod(payMethod); add1.setFremark(fremark);
		// * this.iaddtoDao.save(add1);
		// */
		// } else {
		// iaddtoDao.updateByNumberApp(payNumber, 1, fcost, payMethod);
		// }
		// map.put("success", "false");
		// map.put("msg", "该订单已经付款");
		// return writeAjaxResponse(response, JSONUtil.getJson(map));
		// }
		// }
		// /*
		// * CL_Addto add=new CL_Addto(); add.setFcreatTime(new Date());
		// * add.setFstatus(0); add.setFpayNumber(payNumber);
		// * add.setForderId(orderId); add.setFcost(fcost);
		// * add.setFcreateor(userId); add.setFpayMethod(payMethod);
		// * add.setFremark(fremark); this.iaddtoDao.save(add);
		// */
		//
		// if (payMethod != 5) {
		// if (request.getParameter("payDate") == null ||
		// "".equals(request.getParameter("payDate"))) {
		// map.put("success", "false");
		// map.put("msg", "支付系统未支付成功，请重新支付,数据有误");
		// return writeAjaxResponse(response, JSONUtil.getJson(map));
		// } else {
		// payDate = request.getParameter("payDate");
		// }
		// if (!ServerContext.cancelOrderPay(payDateUrl, payDate)) {
		// map.put("success", "false");
		// map.put("msg", "支付系统未支付成功，请重新支付");
		// return writeAjaxResponse(response, JSONUtil.getJson(map));
		// }
		// }
		//
		// this.iaddtoDao.updateByIdApp(add.getFid(), 1, fcost, payMethod);
		// /** 添加明细 Start */
		// CL_UserRole user = userRoleDao.getById(add.getFcreateor());
		// String userid = "";
		// if (user != null) {
		// userid = user.getVmiUserFid();
		// }
		// int orderid = add.getForderId();
		// String ordernum = "";
		// CL_Order order = orderDao.getById(orderid);
		// if (order != null) {
		// ordernum = order.getNumber();
		// }
		// statementDao.saveStatement(add.getFid().toString(), ordernum, 2,
		// add.getFcost(), -1, add.getFcreateor(),
		// add.getFpayMethod(), userid);
		// /** 添加明细 End */
		//
		// /*** 追加费用计算司机运费 Start */
		// List<CL_Addto> addto = iaddtoDao.getByOrderId(orderid);//
		// 查询当前所有支付成功的追加费用
		// BigDecimal AllCost = order.getFreight();// 初始费用取订单费用，总费=订单运费+所有追加运费
		// BY
		// // CC 2016-05-18
		// if (addto.size() > 0) {
		// for (CL_Addto ad : addto) {
		// AllCost = AllCost.add(ad.getFcost());
		// }
		// }
		// //
		// AllCost=AllCost.add(order.getFreight()).setScale(1,BigDecimal.ROUND_HALF_UP);//所有追加费用加上订单原始运费
		// // 根据总价格获取管理费比例 BY CC 2016-05-18
		// BigDecimal discount = CalcTotalField.calDriverFee(AllCost.setScale(1,
		// BigDecimal.ROUND_HALF_UP));
		// // 循环修改追加费用司机费用 BY CC 2016-05-18
		// for (CL_Addto ad : addto) {
		// iaddtoDao.updateDriverfee(ad.getFid(),
		// ad.getFcost().multiply(discount).setScale(1,
		// BigDecimal.ROUND_HALF_UP));
		// }
		// // orderDao.updateDriverFeeById(orderId,
		// //
		// order.getFreight().multiply(discount).setScale(1,BigDecimal.ROUND_HALF_UP));//更新订单表中司机的总运费
		// /*** 追加费用计算司机运费 End */
		//
		// map.put("success", "true");
		// map.put("msg", "支付系统成功");
		// return writeAjaxResponse(response, JSONUtil.getJson(map));
	}
}
