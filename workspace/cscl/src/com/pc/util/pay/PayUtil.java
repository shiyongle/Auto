package com.pc.util.pay;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.UUID;

import org.apache.commons.codec.digest.HmacUtils;

import com.pc.dao.UserRole.IUserRoleDao;
import com.pc.dao.financeStatement.IFinanceStatementDao;
import com.pc.model.CL_FinanceStatement;
import com.pc.model.CL_PayLogs;
import com.pc.model.CL_UserRole;
import com.pc.util.CacheUtilByCC;
import com.pc.util.JSONUtil;
import com.pc.util.RSA;
import com.pc.util.ServerContext;

public class PayUtil {

	// @Resource
	// private IFinanceStatementDao FinanceDao;
	// @Resource
	// private IUserRoleDao userDao;

	protected static final String REQUESTTYPE = ".do"; // 请求类型

	/**
	 * 
	 * * 发送支付请求到支付服务
	 * 
	 * @param DataName
	 *            请求数据Name
	 * @param Data
	 *            附带数据
	 * @param interfaceName
	 *            请求接口
	 * @param isResponData
	 *            true 为直接返回支付系统返回数据 false 为错误信息则返回信息
	 * @return 返回空为请求正常 有返回数据为错误提醒
	 */
	public static String sendLK(HashMap<String, Object> param, LinkedHashMap<String, Object> Data) {
		String number = "";
		try {
			// fbusinessType等于0表示不用生成收支明细;
			
			if (Integer.parseInt(param.get("fbusinessType")+"") != 0) {
				/*
				 * int fcusid =
				 * Integer.parseInt(param.get("fcusid").toString()); // 获取用户余额
				 * CL_UserRole userrole = ((IUserRoleDao)
				 * param.get("userDao")).getById(fcusid);
				 */

				CL_UserRole userrole = (CL_UserRole) param.get("userrole");

				// 判断是否已生成此订单的支付明细
				if (param.get("frelatedId") != null && param.get("frelatedId").toString().length() > 0) {
					// 不允许多次生成明细 根据frelatedId 查询是否存在此订单明细

					List<CL_FinanceStatement> stt = ((IFinanceStatementDao) param.get("FinanceDao")).getforderId(
							param.get("frelatedId").toString(), Integer.parseInt(param.get("fbusinessType")+""),
							param.get("forderId").toString(), Integer.parseInt(param.get("fpayType").toString()),
							new BigDecimal(param.get("famount").toString()));
					// 订单判断
					if (stt.size() > 1) {
						return "{\"success\":false,\"msg\":\"生成多次支付信息，该订单失效\"}";
					} else if (stt.size() == 1) {
						Data.put("orderNumber", stt.get(0).getNumber());
						number = stt.get(0).getNumber();
					} else {
						number = createPayDetails(param, userrole.getVmiUserFid());
					}

				} else {

					number = createPayDetails(param, userrole.getVmiUserFid());
				}
				if (number.length() != 0)
					Data.put("orderNumber", number);
			}

			//临时测试1分钱;
//			if (Data.get("payAmount") != null && !"".equals(Data.get("payAmount")) ) {
//				Data.put("payAmount", "0.01");
//			}
			//临时测试1分钱;

			String token = "dj";
			LinkedHashMap<String, Object> payData = new LinkedHashMap<String, Object>();
			payData.put("Developers", "DongYun");
			payData.put("Token", token);
			payData.put("MessageID", UUID.randomUUID().toString());
			payData.put("Interface", "Interface");

			payData.put("Data", JSONUtil.getJson(Data));

			payData.put("DataName", param.get("DataName"));
			payData.put("Sign", "");

			String sign = HmacUtils.hmacSha512Hex(token, JSONUtil.getJson(payData));
			payData.put("Sign", sign);
			payData.put("Token", RSA.encrypt(token, Key.PUBLICKEY));

			String postUrl = ServerContext.getBaseurl() + "/" + param.get("interfaceName") + REQUESTTYPE;
			HashMap<String, String> params = new HashMap<String, String>();
			params.put("PayInfo", JSONUtil.getJson(payData));
			params.put("url", postUrl);

			String resultString = "";

			Boolean isOK=true;
			
//			System.out.println("支付请求URL：" + postUrl + "，time：" + new Date() +" ，支付请求Data：" + payData);
			
			resultString = PayContext.SubmitUrlData(postUrl, JSONUtil.getJson(payData));
			if (resultString != null && !resultString.equals("")) {
				resultString = "{\"number\":\"" + number + "\"," + resultString.substring(1);
			} else {
				// 写错误日志
				isOK=false;
				resultString = "{\"success\":\"false\",\"msg\":\"支付服务器错误\"}";
			}
			//请求成功
			param.put("flevel",isOK?"4":"1");   //1为 支付服务返回错误  4为支付系统返回正常
			param.put("response", resultString);
			param.put("number", number);
			param.put("data", payData.get("Data"));
			savePayLogs(param);
//			if (resultString == null || "".equals(resultString)) {
//				// 写错误日志
//				resultString = "{\"success\":\"false\",\"msg\":\"支付服务器错误\"}";
//				savePayLogs(param);
//			}

			return resultString;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String createPayDetails(HashMap<String, Object> param, String VmiUserFid) {
		int fcusid = Integer.parseInt(param.get("fcusid").toString());
		String number;
		number = CacheUtilByCC.getOrderNumber("cl_finance_statement", "L", 8);

		CL_FinanceStatement statement = new CL_FinanceStatement();
		statement.setFcreateTime(new Date());
		statement.setNumber(number);
		statement.setFrelatedId(param.get("frelatedId").toString());
		statement.setForderId(param.get("forderId").toString());
		statement.setFbusinessType(Integer.parseInt(param.get("fbusinessType")+""));
		statement.setFamount((BigDecimal) param.get("famount"));

		// 兼容 新增判断 如果ftype 不传输 默认为用户收入 传输 则为传输值
		if (param.get("ftype") == null)
			statement.setFtype(1);
		else
			statement.setFtype(Integer.parseInt(param.get("ftype").toString()));

		if (param.get("fpayOrder") != null)
			statement.setFpayOrder(param.get("fpayOrder").toString());
		;

		statement.setFuserroleId(fcusid);
		statement.setFuserid(VmiUserFid);
		statement.setFpayType(Integer.parseInt(param.get("fpayType").toString()));
		statement.setFremark((String) param.get("fremark"));
		statement.setFbalance(new BigDecimal("0.0"));
		if(param.get("fstatus") == null)
			statement.setFstatus(0);
		else
			statement.setFstatus(Integer.parseInt(param.get("fstatus").toString()));
		
		statement.setFreight((BigDecimal) param.get("freight"));

		int success = ((IFinanceStatementDao) param.get("FinanceDao")).save(statement);

		return number;
	}

	// /** 微信二维码 方式支付 */
	// public static final String PAY_WECHAT_QR = "微信扫一扫";
	// /** 支付宝二维码 方式支付 */
	// public static final String PAY_ALIPAY_QR = "支付宝扫一扫";
	//
	// protected static final String VERSION = "2";
	// protected static final String DEVELOPER = "CS"; // 开发者编号
	// protected static final String MERCHANT_NUMBER = "12345"; // 商户号
	// protected static final String PAY_PASSWORD = "123456"; // 创建支付用户的初始密码
	//
	// protected static final String TOKEN = "DongYun"; // 商户号

	//
	// /** 支付获取支付码 */
	// public static String newRecharge(int ty, String orderNumber,
	// String vmiftel, String Money) {
	// JSONObject jo;
	// JSONObject jo1;
	// try {
	// jo = new JSONObject();
	// jo1 = new JSONObject();
	// jo1.put("OrderNumber", RSA.encrypt(orderNumber, Key.PUBLICKEY));
	// jo1.put("TextCode", RSA.encrypt("utf-8", Key.PUBLICKEY));
	// jo1.put("OrderType", RSA.encrypt("收款", Key.PUBLICKEY));
	// Calendar date = Calendar.getInstance();
	// SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmm");
	// jo1.put("OrderTime",
	// RSA.encrypt(format.format(date.getTime()), Key.PUBLICKEY));
	// jo1.put("Developer", RSA.encrypt(DEVELOPER, Key.PUBLICKEY));
	// jo1.put("Drawee", RSA.encrypt(vmiftel,// 用户付款人手机号 String类型
	// Key.PERSONALPUBLICKEY));
	// jo1.put("Payee", RSA.encrypt("DJ", Key.PERSONALPUBLICKEY));
	//
	// jo1.put("PayType", RSA.encrypt("第三方", Key.PUBLICKEY));
	//
	// if (ty == 1) {
	// jo1.put("BankName", RSA.encrypt(PAY_ALIPAY_QR, Key.PUBLICKEY));
	// } else {
	// jo1.put("BankName", RSA.encrypt(PAY_WECHAT_QR, Key.PUBLICKEY));
	// }
	// jo1.put("BankAccountNo", RSA.encrypt("0000", Key.PUBLICKEY));
	// jo1.put("Money", RSA.encrypt("" + Money, Key.PERSONALPUBLICKEY));
	// jo1.put("PayPassword", RSA.encrypt("123456", Key.PERSONALPUBLICKEY));
	// jo.put("data", jo1);
	// HashMap<String, String> params = new HashMap<String, String>();
	// params.put("PayInfo", Base64.encode(jo.toString().getBytes()));
	// params.put("url", Key.OrderCreate);
	// String reString = PayContext.PaySum(params);
	// return reString;
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// return null;
	// }
	//
	// /*** 查余额 */
	// public static String UsePayBalance(String vmiftel) {
	//
	// try {
	// LinkedHashMap<String, Object> payData = new LinkedHashMap<String,
	// Object>();
	// LinkedHashMap<String, Object> Data = new LinkedHashMap<String, Object>();
	//
	// payData.put("Developers", "DongYun");
	// payData.put("Token", "lk");
	// // payData.put("MessageID","selectBalance");
	// payData.put("MessageID", UUID.randomUUID().toString());
	// payData.put("Interface", "Order");
	//
	// Data.put("Name", "15868563284");
	// Data.put("Age", "1");
	// payData.put("Data", JSONUtil.getJson(Data));
	//
	// payData.put("DataName", "DEemo");
	// payData.put("Sign", "");
	//
	// String sign = HmacUtils.hmacSha512Hex("lk",
	// JSONUtil.getJson(payData));
	// payData.put("Sign", sign);
	// payData.put("Token", RSA.encrypt("lk", Key.PUBLICKEY));
	//
	// // String postSecretData =
	// // Base64.encode(JSONUtil.getJson(payData).getBytes());
	// // String postSecretData =
	// //
	// org.apache.commons.codec.binary.Base64.encodeBase64String(JSONUtil.getJson(payData).getBytes());
	// String postUrl = ServerContext.getBaseurl() + "/SignDemo"
	// + REQUESTTYPE;
	// // String strData = "&data=" + postSecretData;
	// HashMap<String, String> params = new HashMap<String, String>();
	// params.put("PayInfo", JSONUtil.getJson(payData));
	// params.put("url", postUrl);
	// String reString = PayContext.PaySum(params);
	//
	// /*
	// JSONObject jo; JSONObject jo1; jo = new JSONObject(); jo1 = new
	// JSONObject(); jo1.put("Developer",
	// RSA.encrypt(DEVELOPER,Key.PUBLICKEY));
	// jo1.put("Usename",RSA.encrypt(vmiftel, Key.PERSONALPUBLICKEY));
	// jo.put("data", jo1); HashMap<String, String> params = new
	// HashMap<String, String>(); params.put("PayInfo",
	// Base64.encode(jo.toString().getBytes()));
	// params.put("url",Key.UsePayBalance); String
	// reString=PcWebPayContext.PaySum(params);
	// */
	// return reString;
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// return null;
	// }
	//
	//
	//
	// /*** 银行卡列表 */
	// public static String UseBingBankNameList(String vmiftel) {
	// LinkedHashMap<String, Object> Data = new LinkedHashMap<String, Object>();
	// Data.put("Name", "15868563284");
	// Data.put("Age", "1");
	//
	// return sendLK("Order", "DEemo", Data,"SignDemo");
	//
	// // try {
	// //
	// // JSONObject jo;
	// // JSONObject jo1;
	// // jo = new JSONObject();
	// // jo1 = new JSONObject();
	// // jo1.put("Developer", RSA.encrypt(DEVELOPER, Key.PUBLICKEY));
	// // jo1.put("Usename", RSA.encrypt(vmiftel, Key.PERSONALPUBLICKEY));
	// // jo.put("data", jo1);
	// // HashMap<String, String> params = new HashMap<String, String>();
	// // params.put("PayInfo", Base64.encode(jo.toString().getBytes()));
	// // params.put("url", Key.UseBingBankNameList);
	// // String reString = PcWebPayContext.PaySum(params);
	// //
	// // return reString;
	// // } catch (Exception e) {
	// // e.printStackTrace();
	// // }
	// // return null;
	// }
	//
	// /*** 用户明细列表 */
	// public static String UsePayAccountStatement(String vmiftel, int number,
	// String time1, String time2) {
	//
	// LinkedHashMap<String, Object> Data = new LinkedHashMap<String, Object>();
	// Data.put("Name", "15868563284");
	// Data.put("Age", "1");
	//
	// return sendLK("Order", "DEemo", Data,"SignDemo");
	//
	// /*try {
	// JSONObject jo; JSONObject jo1; jo = new JSONObject(); jo1 = new
	// JSONObject(); String [] time=new String[2];
	// if(time1==null||time2==null){ time[0]=""; time[1]=""; }else{
	// time[0]=time1; time[1]=time2; } jo1.put("Developer",
	// RSA.encrypt(DEVELOPER,Key.PUBLICKEY));
	// jo1.put("Usename",RSA.encrypt(vmiftel, Key.PERSONALPUBLICKEY));
	// jo1.put("Number", number); jo1.put("Time", time); jo.put("data",
	// jo1); HashMap<String, String> params = new HashMap<String,
	// String>(); params.put("PayInfo",
	// Base64.encode(jo.toString().getBytes()));
	// params.put("url",Key.UsePayAccountStatement); String
	// reString=PayContext.PaySum(params);
	// return reString;
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// return null;*/
	// }
	//
	// /*** 银行收款绑定接口1 */
	// public static Boolean SaveBank() {
	// try {
	// String uuid = IDGenerator.getUUID();
	// String re = PayContext.BingBanK(Key.GUID + uuid);
	// System.out.println(re);
	// if (re != null && !"".equals(re)) {
	// net.sf.json.JSONObject od = net.sf.json.JSONObject
	// .fromObject(re);
	// Boolean ab = (Boolean) od.get("success");
	// return ab;
	// }
	//
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// return false;
	// }
	//
	/**
	 * 绑定银行卡到支付系统
	 * 
	 * @return
	 */
	public static String bindBankInfoToPaySystem(HashMap<String, Object> param, LinkedHashMap<String, Object> model) {

		// fbusinessType等于0表示不用生成收支明细;
		param.put("fbusinessType", new Integer(0));
		param.put("interfaceName", "bindBankCard");
		param.put("DataName", "UserBankBindPo");
		
		int fcusid = Integer.parseInt(param.get("fcusid").toString());
		// 获取用户
		CL_UserRole userrole = ((IUserRoleDao) param.get("userDao")).getById(fcusid);
		param.put("userrole", userrole);
		String respons = PayUtil.sendLK(param, model);
		
		return respons;
	}

	/**
	 * 银行卡绑定校验 param 支付订单信息(收支明细); model 支付系统接口数据;
	 * 
	 * @return
	 */
	public static String bindBankCheckToPaySystem(HashMap<String, Object> param, LinkedHashMap<String, Object> model) {

		// param.put("fbusinessType", new Integer(0));
		param.put("frelatedId", "");
		param.put("forderId", "");
		param.put("fbusinessType", 9);
		param.put("famount", new BigDecimal("0.01"));
		param.put("fpayType", 3);
		param.put("fremark", "绑卡0.01");
		param.put("interfaceName", "checkBankCardBound");
		param.put("DataName", "UserBankBindCheckerPo");

		int fcusid = Integer.parseInt(param.get("fcusid").toString());
		// 获取用户
		CL_UserRole userrole = ((IUserRoleDao) param.get("userDao")).getById(fcusid);
		param.put("userrole", userrole);
		String respons = PayUtil.sendLK(param, model);


		return respons;
	}

	/**
	 * 支付订单到支付系统(支付宝和微信) SDK和URL
	 * 
	 * @param param
	 *            参数
	 * @param model
	 *            请求model
	 * @return 返回支付系统返回所有参数
	 */
	public static String payOrderToPaySystem_ZFBWX(HashMap<String, Object> param, LinkedHashMap<String, Object> model) {

		param.put("fremark", "东运物流有限公司");
		param.put("interfaceName", "AddPayOrderInfo");
		param.put("DataName", "PayOrderCreate");

		int fcusid = Integer.parseInt(param.get("fcusid").toString());
		// 获取用户
		CL_UserRole userrole = ((IUserRoleDao) param.get("userDao")).getById(fcusid);
		param.put("userrole", userrole);
		String respons = PayUtil.sendLK(param, model);

	
		return respons;
	}

	public static String payOrderToPaySystem_Bank(HashMap<String, Object> param, LinkedHashMap<String, Object> model) {
		param.put("fremark", "东运物流有限公司");
		param.put("interfaceName", "AddPayOrderInfo");
		param.put("DataName", "PayOrderCreate");

		int fcusid = Integer.parseInt(param.get("fcusid").toString());
		// 获取用户
		CL_UserRole userrole = ((IUserRoleDao) param.get("userDao")).getById(fcusid);
		param.put("userrole", userrole);
		String respons = PayUtil.sendLK(param, model);

		

		return respons;
	}

	public static String payOrderToPaySystem_CheckOutBank(HashMap<String, Object> param,
			LinkedHashMap<String, Object> model) {
		param.put("fremark", "东运物流有限公司");
		param.put("interfaceName", "PayBankDeductions");
		param.put("DataName", "PayBankDeductions");

		int fcusid = Integer.parseInt(param.get("fcusid").toString());
		// 获取用户
		CL_UserRole userrole = ((IUserRoleDao) param.get("userDao")).getById(fcusid);
		param.put("userrole", userrole);
		String respons = PayUtil.sendLK(param, model);

		
		return respons;
	}

	/**
	 * 校验订单状态 支付服务器
	 * 
	 * @param param
	 * @param model
	 * @return
	 */
	public static String orderValidationToPaySystem(HashMap<String, Object> param,
			LinkedHashMap<String, Object> model) {
		param.put("fbusinessType", new Integer(0));
		param.put("interfaceName", "PayOrderValidation");
		param.put("DataName", "PayOrderValidation");

		int fcusid = Integer.parseInt(param.get("fcusid").toString());
		// 获取用户
		CL_UserRole userrole = ((IUserRoleDao) param.get("userDao")).getById(fcusid);
		param.put("userrole", userrole);
		String respons = PayUtil.sendLK(param, model);

		return respons;
	}

	/**
	 * web银行卡充值
	 * 
	 * @param param
	 *            web银行卡充值验证码校验 by lancher
	 * @param model
	 * @return
	 */
	public static String webRecharge(LinkedHashMap<String, Object> model, HashMap<String, Object> param) {
		if (model.get("orderNumber").toString().length() == 0) {// 逻辑有点忘了
			param.put("fbusinessType", new Integer(6));
		} else {
			param.put("fbusinessType", new Integer(0));// 不再生成支付明细，之前是1，忘了
		}
		param.put("interfaceName", "PayBankDeductions");
		param.put("DataName", "PayBankDeductions");

		int fcusid = Integer.parseInt(param.get("fcusid").toString());
		// 获取用户
		CL_UserRole userrole = ((IUserRoleDao) param.get("userDao")).getById(fcusid);
		param.put("userrole", userrole);
		String respons = PayUtil.sendLK(param, model);

	

		return respons;
	}

	/**
	 * web银行卡追加
	 * 
	 * @param param
	 *            web银行卡充值验证码校验 by lancher
	 * @param model
	 * @return
	 */
	public static String webAddRecharge(LinkedHashMap<String, Object> model) {
		HashMap<String, Object> param = new HashMap<String, Object>();
		param.put("fbusinessType", 2);
		param.put("interfaceName", "PayBankDeductions");
		param.put("DataName", "PayBankDeductions");

		int fcusid = Integer.parseInt(param.get("fcusid").toString());
		// 获取用户
		CL_UserRole userrole = ((IUserRoleDao) param.get("userDao")).getById(fcusid);
		param.put("userrole", userrole);
		String response = PayUtil.sendLK(param, model);

	

		return response;
	}

	/**
	 * web银行卡充值验证码获取 by lancher
	 * 
	 * @param model
	 * @return
	 */
	public static String webRechargeCode(HashMap<String, Object> param, LinkedHashMap<String, Object> model) {
		if (model.get("orderNumber").toString().length() == 0) {
			param.put("fbusinessType", new Integer(6));
		} else {
			param.put("fbusinessType", new Integer(1));
		}
		param.put("interfaceName", "AddPayOrderInfo");
		param.put("DataName", "PayOrderCreate");

		int fcusid = Integer.parseInt(param.get("fcusid").toString());
		// 获取用户
		CL_UserRole userrole = ((IUserRoleDao) param.get("userDao")).getById(fcusid);
		param.put("userrole", userrole);
		String respons = PayUtil.sendLK(param, model);


		return respons;
	}

	/**
	 * web银行卡追加运费验证码获取 by lancher
	 * 
	 * @param model
	 * @return
	 */
	public static String webAddRechargeCode(HashMap<String, Object> param, LinkedHashMap<String, Object> model) {
		param.put("fbusinessType", new Integer(2));
		param.put("interfaceName", "AddPayOrderInfo");
		param.put("DataName", "PayOrderCreate");

		int fcusid = Integer.parseInt(param.get("fcusid").toString());
		// 获取用户
		CL_UserRole userrole = ((IUserRoleDao) param.get("userDao")).getById(fcusid);
		param.put("userrole", userrole);
		String response = PayUtil.sendLK(param, model);


		return response;
	}

	/**
	 * web支付宝微信充值
	 * 
	 * @param param
	 *            web支付宝微信充值获取支付码 by lancher
	 * @param model
	 * @return
	 */
	public static String webRechargeByZFBWX(HashMap<String, Object> param, LinkedHashMap<String, Object> model) {
		if (model.get("orderNumber").toString().length() == 0) {
			param.put("fbusinessType", 6);
		} else {
			param.put("fbusinessType", 1);
		}
		param.put("interfaceName", "AddPayOrderInfo");
		param.put("DataName", "PayOrderCreate");

		int fcusid = Integer.parseInt(param.get("fcusid").toString());
		// 获取用户
		CL_UserRole userrole = ((IUserRoleDao) param.get("userDao")).getById(fcusid);
		param.put("userrole", userrole);
		String response = PayUtil.sendLK(param, model);

		

		return response;
	}

	public static String webAddPayQuery(HashMap<String, Object> param, LinkedHashMap<String, Object> model) {
		param.put("fbusinessType", 0);
		param.put("interfaceName", "PayOrderValidation");
		param.put("DataName", "PayOrderValidation");

		int fcusid = Integer.parseInt(param.get("fcusid").toString());
		// 获取用户
		CL_UserRole userrole = ((IUserRoleDao) param.get("userDao")).getById(fcusid);
		param.put("userrole", userrole);
		String response = PayUtil.sendLK(param, model);

		return response;
	}

	public static int savePayLogs(HashMap<String, Object> param) throws Exception {
		CL_PayLogs payLogs = new CL_PayLogs();
		payLogs.setFcreationTime(new Date());
		payLogs.setFcreator(((CL_UserRole) param.get("userrole")).getVmiUserName());
		payLogs.setFcreatorID(((CL_UserRole) param.get("userrole")).getId());
		payLogs.setFpayUrl((String) param.get("interfaceName"));
		payLogs.setFpayData(param.get("data").toString());
		payLogs.setFrespone(param.get("response").toString());
		payLogs.setFfinanceNum(param.get("number").toString());
		payLogs.setFlevel(Integer.parseInt(param.get("flevel").toString()));
		payLogs.setFcreator("PayUtil");
		return ((IUserRoleDao) param.get("userDao")).savePayLogs(payLogs);
	}

	//
	// /** 银行收款订单创建/绑卡支付 接口 */
	// public static String BindBanKAndPay(String orderNumber, String vmiftel,
	// String Money, String BankName) {
	// JSONObject jo;
	// JSONObject jo1;
	// try {
	// jo = new JSONObject();
	// jo1 = new JSONObject();
	// jo1.put("OrderNumber", RSA.encrypt(orderNumber, Key.PUBLICKEY));
	// jo1.put("TextCode", RSA.encrypt("utf-8", Key.PUBLICKEY));
	// jo1.put("OrderType", RSA.encrypt("收款", Key.PUBLICKEY));
	// Calendar date = Calendar.getInstance();
	// SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmm");
	// jo1.put("OrderTime",
	// RSA.encrypt(format.format(date.getTime()), Key.PUBLICKEY));
	// jo1.put("Developer", RSA.encrypt(DEVELOPER, Key.PUBLICKEY));
	// jo1.put("Drawee", RSA.encrypt(vmiftel,// 用户付款人手机号 String类型
	// Key.PERSONALPUBLICKEY));
	// jo1.put("Payee", RSA.encrypt("DJ", Key.PERSONALPUBLICKEY));
	//
	// jo1.put("PayType", RSA.encrypt("银行", Key.PUBLICKEY));
	// jo1.put("BankName", RSA.encrypt(BankName, Key.PUBLICKEY));
	// jo1.put("BankAccountNo", RSA.encrypt("0000", Key.PUBLICKEY));
	// jo1.put("Money", RSA.encrypt("" + Money, Key.PERSONALPUBLICKEY));
	// jo1.put("PayPassword", RSA.encrypt("000000", Key.PERSONALPUBLICKEY));
	// jo.put("data", jo1);
	// HashMap<String, String> params = new HashMap<String, String>();
	// params.put("PayInfo", Base64.encode(jo.toString().getBytes()));
	// params.put("url", Key.OrderCreate);
	// String reString = PayContext.PaySum(params);
	// return reString;
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// return null;
	// }
	//
	// /** 银行收款订单创建/绑卡支付 接口2 */
	// public static String BindDingBanKAndPay(String orderNumber, String Money,
	// pcWebBankModel bankModel) {
	// JSONObject jo;
	// JSONObject jo1;
	// JSONObject jo2;
	// try {
	// jo = new JSONObject();
	// jo1 = new JSONObject();
	// jo2 = new JSONObject();
	// jo2 = BankBindingInfo(bankModel);
	// jo1.put("OrderNumber", RSA.encrypt(orderNumber, Key.PUBLICKEY));
	// jo1.put("TextCode", RSA.encrypt("utf-8", Key.PUBLICKEY));
	// jo1.put("OrderType", RSA.encrypt("绑卡支付", Key.PUBLICKEY));
	// Calendar date = Calendar.getInstance();
	// SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmm");
	// jo1.put("OrderTime",
	// RSA.encrypt(format.format(date.getTime()), Key.PUBLICKEY));
	// jo1.put("Developer", RSA.encrypt(DEVELOPER, Key.PUBLICKEY));
	// jo1.put("Drawee",
	// RSA.encrypt(bankModel.getBankBindingMobileNumber(),// 用户付款人手机号
	// // String类型
	// Key.PERSONALPUBLICKEY));
	// jo1.put("Payee", RSA.encrypt("DJ", Key.PERSONALPUBLICKEY));
	//
	// jo1.put("PayType", RSA.encrypt("银行", Key.PUBLICKEY));
	// jo1.put("BankName",
	// RSA.encrypt(bankModel.getBankName(), Key.PUBLICKEY));
	// jo1.put("BankAccountNo", RSA.encrypt("0000", Key.PUBLICKEY));
	// jo1.put("Money", RSA.encrypt("" + Money, Key.PERSONALPUBLICKEY));
	// jo1.put("PayPassword", RSA.encrypt("000000", Key.PERSONALPUBLICKEY));
	// jo1.put("BankBindingInfo", jo2);
	// jo.put("data", jo1);
	// HashMap<String, String> params = new HashMap<String, String>();
	// params.put("PayInfo", Base64.encode(jo.toString().getBytes()));
	// params.put("url", Key.OrderCreate);
	// String reString = PayContext.PaySum(params);
	// return reString;
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// return null;
	// }
	//
	// /**
	// * BankBindingInfo 构建
	// *
	// * @throws JSONException
	// */
	// public static JSONObject BankBindingInfo(pcWebBankModel bankModel)
	// throws JSONException {
	// JSONObject jo;
	// jo = new JSONObject();
	// jo.put("TextCode", "utf-8");
	// jo.put("BankBindingIDNumber", RSA.encrypt(
	// bankModel.getBankBindingIDNumber(), Key.PERSONALPUBLICKEY));
	// jo.put("BankBindingUserName", RSA.encrypt(
	// bankModel.getBankBindingUserName(), Key.PERSONALPUBLICKEY));
	// jo.put("BankBindingUserIDCard", RSA.encrypt(
	// bankModel.getBankBindingUserIDCard(), Key.PERSONALPUBLICKEY));
	// jo.put("BankBindingMobileNumber", RSA.encrypt(
	// bankModel.getBankBindingMobileNumber(), Key.PERSONALPUBLICKEY));
	// jo.put("Cvn", bankModel.getCvn());
	// jo.put("BankM", bankModel.getBankM());
	// jo.put("BankY", bankModel.getBankY());
	// return jo;
	//
	// }
	//
	// /** 查询订单是否支付完成 */
	// public static String CheckPayStatus(String orderNumber, String vmiftel,
	// String BankName) {
	// JSONObject jo;
	// JSONObject jo1;
	// try {
	// jo = new JSONObject();
	// jo1 = new JSONObject();
	// jo1.put("Developer", RSA.encrypt(DEVELOPER, Key.PUBLICKEY));
	// jo1.put("TextCode", "utf-8");
	// jo1.put("Usename", RSA.encrypt(vmiftel, Key.PERSONALPUBLICKEY));
	// jo1.put("BankName", Base64.encode(BankName.toString().getBytes()));
	// jo1.put("OrederUserNumber", orderNumber);
	// jo1.put("OrederSysNumber", "");
	// jo.put("data", jo1);
	// HashMap<String, String> params = new HashMap<String, String>();
	// params.put("PayInfo", Base64.encode(jo.toString().getBytes()));
	// params.put("url", Key.UserOrderPayOver);
	// String reString = PayContext.PaySum(params);
	// return reString;
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// return null;
	// }
	//
	// /** 用于银行收款绑定 校验短信 */
	// public static String VerificationCode(String VerificationCode, String
	// fid) {
	// try {
	// HashMap<String, String> params = new HashMap<String, String>();
	// params.put("PayInfo", VerificationCode);
	// params.put("url", Key.VerificationCode + fid
	// + "?&XDEBUG_SESSION_START=17402");
	// String reString = PayContext.PaySumCheck(params);
	// return reString;
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// return null;
	// }
	//
	// /** 用户余额支付 接口 */
	// public static String UserOrderPay(String orderNumber, String vmiftel,
	// String Money) {
	//
	// LinkedHashMap<String, Object> Data = new LinkedHashMap<String, Object>();
	// Data.put("Name", "15868563284");
	// Data.put("Age", "1");
	//
	// return sendLK("Order", "DEemo", Data,"SignDemo");
	//
	// /*try {
	// JSONObject jo; JSONObject jo1;
	//
	// jo = new JSONObject(); jo1 = new JSONObject();
	// jo1.put("OrderNumber", RSA.encrypt(orderNumber,Key.PUBLICKEY));
	// jo1.put("TextCode", RSA.encrypt("utf-8", Key.PUBLICKEY));
	// jo1.put("OrderType", RSA.encrypt("支付", Key.PUBLICKEY)); Calendar
	// date=Calendar.getInstance(); SimpleDateFormat format= new
	// SimpleDateFormat("yyyyMMddHHmm"); jo1.put("OrderTime",
	// RSA.encrypt(format.format(date.getTime()), Key.PUBLICKEY));
	// jo1.put("Developer", RSA.encrypt(DEVELOPER, Key.PUBLICKEY));
	// jo1.put("Drawee", RSA.encrypt(vmiftel,//用户付款人手机号 String类型
	// Key.PERSONALPUBLICKEY)); jo1.put("Payee", RSA.encrypt("DJ",
	// Key.PERSONALPUBLICKEY));
	//
	// jo1.put("PayType", RSA.encrypt("系统",Key.PUBLICKEY));
	// jo1.put("BankName", RSA.encrypt("DJ银行余额", Key.PUBLICKEY));
	// jo1.put("BankAccountNo", RSA.encrypt("0000", Key.PUBLICKEY));
	// jo1.put("Money", RSA.encrypt(""+Money, Key.PERSONALPUBLICKEY));
	// jo1.put("PayPassword", RSA.encrypt("123456",
	// Key.PERSONALPUBLICKEY)); jo.put("data", jo1); HashMap<String,
	// String> params = new HashMap<String, String>();
	// params.put("PayInfo", Base64.encode(jo.toString().getBytes()));
	// params.put("url",Key.OrderCreate); String
	// reString=PcWebPayContext.PaySum(params);
	//
	// return reString;
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// return null;*/
	// }
	//
	// /** 支付 注册接口 */
	// public static String UserCreate(String vmiUserPhone, String vmiUserFid) {
	//
	// LinkedHashMap<String, Object> Data = new LinkedHashMap<String, Object>();
	// Data.put("Name", "15868563284");
	// Data.put("Age", "1");
	//
	// return sendLK("Order", "DEemo", Data,"SignDemo");
	//
	// /*try {
	// JSONObject jo; JSONObject jo1; jo = new JSONObject(); jo1 = new
	// JSONObject();
	// jo1.put("Developer",RSA.encrypt("CS",Key.PUBLICKEY));
	// jo1.put("Usename",RSA.encrypt(vmiUserPhone,Key.PUBLICKEY));
	// jo1.put("Password",RSA.encrypt("123456",Key.PERSONALPUBLICKEY));
	// jo1.put("UserExternalId", RSA.encrypt(vmiUserFid,Key.PUBLICKEY));
	// jo.put("data",jo1); HashMap<String, String> params = new
	// HashMap<String, String>(); params.put("PayInfo",
	// Base64.encode(jo.toString().getBytes()));
	// params.put("url",Key.UserCreate); String
	// reString=PcWebPayContext.PaySum(params);
	//
	// return reString;
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// return null;*/
	// }

}