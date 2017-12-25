package com.pc.appInterface.shipper;

import com.pc.controller.BaseController;
import com.pc.dao.UserRole.impl.UserRoleDao;
import com.pc.dao.bank.IbankDao;
import com.pc.dao.couponsActivity.impl.CouponsActivityDaoImpl;
import com.pc.dao.financeStatement.impl.FinanceStatementDaoImpl;
import com.pc.model.CL_Bank;
import com.pc.model.CL_CouponsActivity;
import com.pc.model.CL_FinanceStatement;
import com.pc.model.CL_UserRole;
import com.pc.util.JSONUtil;
import com.pc.util.MD5Util;
import com.pc.util.ServerContext;
import com.pc.util.String_Custom;
import com.pc.util.pay.PayUtil;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by truth on 2016-12-20.
 */
@Controller
@RequestMapping("/app/pay/")
public class ShipperChargeController extends BaseController {

	private final Logger logger = Logger.getLogger(getClass().getName());
	@Resource
	private FinanceStatementDaoImpl financeStatementDao;

	@Resource
	private UserRoleDao userRoleDao;

	@Resource
	private IbankDao bankDao;
	
	@Resource
	private CouponsActivityDaoImpl couponsActivityDao;

	/**
	 * 货主充值（收）
	 *
	 * @param fPayNum 充值金额
	 * @param fPayType 充值方式
	 * @param fBankId 银联 bankId，非必需，如果是银联支付，则需要
	 * @param session
	 * @return
	 */
	@RequestMapping("charge")
	public String shipperCharge(@RequestParam(value = "payNum", defaultValue = "0.0") final String fPayNum,
	                            @RequestParam(value = "fpayType", defaultValue = "0") final String fPayType,
	                            @RequestParam(value = "fbankId", defaultValue = "0", required = false) final String fBankId,
	                            HttpSession session,HttpServletResponse response) {

		BigDecimal payNum = new BigDecimal(0.0); int payType = 0; int bankId = 0;
		try{
			if (String_Custom.noFloat(fPayNum) ||
					(!String_Custom.noFloat(fPayNum) && (payNum = new BigDecimal(fPayNum)).compareTo(BigDecimal.ZERO) != 1)) {
				return this.poClient(response,false, "充值金额必须大于0");
			}

			if (String_Custom.noNumber(fPayType) ||
					(!String_Custom.noNumber(fPayType) && (payType = Integer.parseInt(fPayType)) <= 0)) {
				return this.poClient(response,false, "充值类型错误");
			}
			if (payType == 3 && (String_Custom.noNumber(fBankId) ||
					(!String_Custom.noNumber(fBankId) && (bankId = Integer.parseInt(fBankId)) <= 0))) {
				return this.poClient(response,false, "银联充值缺少必要参数");
			}
		}catch (NumberFormatException e){
			logger.error(e.getMessage());
			return this.poClient(response,false, "参数格式错误");
		}

		int fcusid = 0;
		try{
			fcusid = ServerContext.getUseronline().get(session.getId()).getFuserId();
		}catch (Exception e){
			logger.error(e.getMessage());
			return this.poClient(response,false, "请重新登录");
		}

		final CL_UserRole role = userRoleDao.getById(fcusid);

		// ***************** 对接充值系统所需参数 ******************************
		final LinkedHashMap<String, Object> modellist = new LinkedHashMap<>();
		modellist.put("orderNumber", ""); // 订单编号
		modellist.put("orderCreateTime", String_Custom.getPayOrderCreateTime());
		modellist.put("payerId", MD5Util.getMD5String(fcusid + "ylhy").substring(0, 30));
		modellist.put("serviceProvider", ""); // 充值方式
		modellist.put("serviceProviderType", ""); // 充值方
		modellist.put("bankCardBindId", "");
		modellist.put("payAmount", payNum + "");
		modellist.put("description", "东运物流有限公司");
		modellist.put("notificationURL", "");

		final HashMap<String, Object> param = new HashMap<>();
		param.put("fcusid", fcusid);
		param.put("frelatedId", "");
		param.put("forderId", "");
		param.put("famount", payNum);
		param.put("userDao", userRoleDao);
		param.put("FinanceDao", financeStatementDao);
		param.put("freight", payNum);
		// ***********************************************

		// 第三方请求返回链接
		String respPPayURL = "";

		JSONObject jsonObject = null;

		//判断充值方式
		switch (payType) {
			//alipay
			case 1:
				modellist.put("serviceProvider", "ALI");
				modellist.put("serviceProviderType", "SDK");
				param.put("fbusinessType", 6);
				param.put("ftype", 1); // 收入
				param.put("fpayType", 1); //充值宝

				String responeData = PayUtil.payOrderToPaySystem_ZFBWX(param, modellist);
				jsonObject = JSONObject.fromObject(responeData);
				if ("true".equals(jsonObject.get("success").toString())) {
					respPPayURL = "{\"number\":\"" + "\",\"url\":\""
							+ StringEscapeUtils.escapeJava(jsonObject.get("data").toString()) + "\"}";
				} else {
					return this.poClient(response,false, jsonObject.get("msg").toString());
				}
				break;

			case 2:
				// 微信
				modellist.put("serviceProvider", "WX"); // 充值方式
				modellist.put("serviceProviderType", "SDK"); // 充值方
				param.put("fbusinessType", 6);
				param.put("ftype", 1); // 收入
				param.put("fpayType", 2);

				responeData = PayUtil.payOrderToPaySystem_ZFBWX(param, modellist);
				jsonObject = JSONObject.fromObject(responeData);

				if ("true".equals(jsonObject.get("success").toString())) {
					respPPayURL = "{\"number\":\"\",\"url\":" + jsonObject.get("data").toString() + "}";
				} else {
					return this.poClient(response,false, jsonObject.get("msg").toString());
				}
				break;

			case 3:
				CL_Bank userBank = bankDao.getBankInfo(bankId, fcusid, 2);
				if (userBank == null)
					return this.poClient(response,false, "获取不到该银行卡");
				// 判断时间 fbankpayTime
				long dateTimer = (System.currentTimeMillis() - userBank
						.getFbankpayTime().getTime()) / 1000;
				if (dateTimer < 60) {
					dateTimer = 60 - dateTimer;
					return this.poClient(response, false, "请等待" + dateTimer
							+ "秒后再试");
				}
				userBank.setFbankpayTime(new Date());
				bankDao.update(userBank);
				
				modellist.put("serviceProvider", "BANK"); // 充值方
				modellist.put("serviceProviderType", "PAB"); // 充值方
				modellist.put("bankCardBindId", userBank.getNumber()); // 充值方
				param.put("fpayType", 3);
				param.put("fbusinessType", 6);
				param.put("ftype", 1); // 收入

				responeData = PayUtil.payOrderToPaySystem_Bank(param, modellist);
				jsonObject = JSONObject.fromObject(responeData);
				if ("true".equals(jsonObject.get("success").toString())) {
					respPPayURL = "{\"url\":\"" + jsonObject.get("data").toString() + "\",\"number\":\"" + jsonObject.get("number").toString() + "\"}";
				} else {
					String msg = jsonObject.get("msg").toString();
					if ("未知错误".equals(jsonObject.get("msg").toString())) {
						msg = "请一分钟后重试...";
					}
					return this.poClient(response, false, msg);
				}
				break;

			default:
				return this.poClient(response,false, "充值类型错误");
		}

		System.out.println(respPPayURL);
		return this.poClient(response,true, respPPayURL);
	}

	/**
	 *
	 * @param number 订单号
	 * @param fbankId 银行卡id
	 * @param fverifyCheckCode payOrder银行充值返回参数
	 * @param fverifyCode 短信校验码
	 * @param session
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("checkbank")
	@ResponseBody
	public String payOrderBank(@RequestParam(value = "number", defaultValue = "") String number,
	                           @RequestParam(value = "fbankId", defaultValue = "") String fbankId,
	                           @RequestParam(value = "fverifyCheckCode", defaultValue = "") String fverifyCheckCode,
	                           @RequestParam(value = "fverifyCode", defaultValue = "") String fverifyCode,
	                           HttpSession session) throws IOException {

		String msg = String_Custom.parametersEmpty(new String[] { fverifyCheckCode, fverifyCode });
		if (msg.length() > 0)
			return this.poClient(false, msg);

		int fcusid = ServerContext.getUseronline().get(session.getId()).getFuserId();
		// 根据订单编号 获取订单信息
		List<CL_FinanceStatement> finance= financeStatementDao.getByNumber(number);
		if (finance == null || finance.size()==0)
			return this.poClient(false, "获取不到该订单信息");
		if(finance.size()>1)
			return this.poClient(false,"存在");

		CL_Bank userBank = bankDao.getBankInfo(Integer.parseInt(fbankId), fcusid, 2);
		if (userBank == null)
			return this.poClient(false, "获取不到该银行卡");

		BigDecimal actuaAmount = finance.get(0).getFamount();

		LinkedHashMap<String, Object> bankModel = new LinkedHashMap<>();
		bankModel.put("orderNumber", number); // 订单编号
		bankModel.put("payerId", MD5Util.getMD5String(fcusid + "ylhy").substring(0, 30));// 付款人
		bankModel.put("bankCardBindId", userBank.getNumber()); // 银行卡绑定ID
		bankModel.put("payAmount", actuaAmount); // 充值金额
		bankModel.put("verifyCheckCode", fverifyCheckCode);
		bankModel.put("verifyCode", fverifyCode);
		bankModel.put("notificationURL", "");

		HashMap<String, Object> param2 = new HashMap<>();
		param2.put("fbusinessType", 0);
		String responeData2 = PayUtil.payOrderToPaySystem_CheckOutBank(param2, bankModel);
		JSONObject jo2 = JSONObject.fromObject(responeData2);
		if ("true".equals(jo2.get("success").toString())) {
			return this.poClient(true, null);
		} else {
			return this.poClient(false, jo2.get("msg").toString());
		}
	}
	
	
	/**
	 * 充值之后显示充值送弹窗
	 * @param response
	 * @param request
	 * @return
	 */
	@RequestMapping("topUpCoupons")
	public String topUpCoupons(HttpServletResponse response, HttpServletRequest request){
		HashMap<String, Object> map = new HashMap<String, Object>();
		String amount = request.getParameter("amount");
		if(amount == null || "".equals(amount)){
			map.put("success", false);
			map.put("msg", "缺少参数");
			return writeAjaxResponse(response, JSONUtil.getJson(map));
		}
		List<CL_CouponsActivity> acticityList = couponsActivityDao.getActivityByTopUpDollars(new BigDecimal(amount));
		if(acticityList.size() > 0){//可领取
			map.put("success", true);
			map.put("data", "1");
		} else {//不可领取
			map.put("success", true);
			map.put("data", "0");
		}
		return writeAjaxResponse(response, JSONUtil.getJson(map));
		
	}
	
}
