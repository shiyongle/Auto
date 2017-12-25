package com.pc.appInterface.bankManage;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.pc.controller.BaseController;
import com.pc.dao.UserRole.IUserRoleDao;
import com.pc.dao.bank.IbankDao;
import com.pc.dao.bankInfo.IbankInfoDao;
import com.pc.dao.financeStatement.IFinanceStatementDao;
import com.pc.model.CL_Bank;
import com.pc.model.CL_Bank_Info;
import com.pc.model.CL_FinanceStatement;
import com.pc.model.CL_UserRole;
import com.pc.util.CacheUtilByCC;
import com.pc.util.JSONUtil;
import com.pc.util.MD5Util;
import com.pc.util.ServerContext;
import com.pc.util.String_Custom;
import com.pc.util.pay.PayUtil;

import net.sf.json.JSONObject;

@Controller
public class AppUserBankController extends BaseController {
	@Resource
	private IbankInfoDao bankInfoDao;
	@Resource
	private IbankDao bankDao;

	@Resource
	private IFinanceStatementDao FinanceDao;
	@Resource
	private IUserRoleDao userDao;

	/**
	 * 获取未绑定成功银行卡信息
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/app/query/userUnboundBankInfo")
	public String userUnboundBankInfo(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String bankId = request.getParameter("bankId");
		if (StringUtils.isEmpty(bankId))
			return this.poClient(response, false, "缺少必要参数");
		if (String_Custom.noNumber(bankId))
			this.poClient(response, false, "你传输的bankId类型错误");

		int fcusid = ServerContext.getUseronline().get(request.getSession().getId().toString()).getFuserId();
		CL_Bank clbank = bankDao.getBankInfo(Integer.parseInt(bankId), fcusid, 0);
		if (clbank == null)
			return this.poClient(response, false, "未获取到相关银行卡信息");

		List<CL_Bank> info = new ArrayList<>();
		info.add(clbank);
		return this.poClient(response, true, JSONUtil.getJson(info));
	}

	/**
	 * 根据卡号 获取银行信息
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("/app/query/userBankName")
	public String userBankName(HttpServletRequest request, HttpServletResponse response) throws IOException {

		String bankCardNumber = request.getParameter("fcardNumber");
		if (StringUtils.isEmpty(bankCardNumber))
			return this.poClient(response, false, "请输入卡号");

		// 截取卡号
		if (bankCardNumber.length() < 10)
			return this.poClient(response, false, "卡号位数异常!");

		String interception = bankCardNumber.substring(0, 6);

		List<CL_Bank_Info> info = bankInfoDao.getByBankInfo(interception, false);
		if (info.size() == 0) {
			// 不存在 卡号记录
			interception = bankCardNumber.substring(0, 3);
			info = bankInfoDao.getByBankInfo(interception, true);
			if (info.size() == 0)
				return this.poClient(response, false, "不支持此卡绑定.");

			int i = 4; // 5 7 8
			while (info.size() > 1) {
				interception = bankCardNumber.substring(0, i); // 截取银行卡号
				Iterator<CL_Bank_Info> iterator = info.iterator();
				while (iterator.hasNext()) {
					CL_Bank_Info value = iterator.next();
					if (String_Custom.noContains(value.getFcardIndex(), interception))
						iterator.remove();
				}
				i++;
				if (i == 6)
					i++;
			}
			if (info.size() != 1)
				return this.poClient(response, false, "不支持此卡绑定.");
		}

		String fbankNumber = info.get(0).getFbankNumber().toString();
		if (Integer.parseInt(fbankNumber) != bankCardNumber.length())
			return this.poClient(response, false, "卡位数不定，不支持此卡绑定.");

		return this.poClient(response, true, JSONUtil.getJson(info));
	}

	/**
	 * 获取银行卡列表
	 * 
	 * @param request
	 *            request
	 * @param response
	 *            response
	 * @throws IOException
	 */
	@RequestMapping("/app/query/userBankList")
	public String userBankList(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String feffect = request.getParameter("feffect"); // 银行显示名字(userBankName.do接口获取)
		String pIsEmpty = String_Custom.parametersEmpty(new String[] { feffect }, "缺少必要参数");
		if (pIsEmpty.length() != 0)
			feffect = "0";
		if (String_Custom.noNumber(feffect))
			this.poClient(response, false, "你传输的feffect类型错误");

		List<Map<String, Object>> info = bankDao.getByUserList(
				ServerContext.getUseronline().get(request.getSession().getId().toString()).getFuserId(),
				Integer.parseInt(feffect));
		if (info == null)
			info = new ArrayList<>();
		return this.poClient(response, true, JSONUtil.getJson(info), info.size());
	}

	/**
	 * 解除绑定银行卡
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/app/query/userBankRemove")
	public String userBankRemove(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String bankIds = request.getParameter("bankIds"); // 银行显示名字(userBankName.do接口获取)
		String pIsEmpty = String_Custom.parametersEmpty(new String[] { bankIds }, "缺少必要参数");
		if (pIsEmpty.length() != 0)
			return this.poClient(response, false, pIsEmpty);

		// 对接解除绑定银行卡

		// 修改银行卡delete 属性
		int fcusid = ServerContext.getUseronline().get(request.getSession().getId().toString()).getFuserId();
		int info = bankDao.removeUserBank(bankIds, fcusid);
		if (info == 0)
			return this.poClient(response, false, "解除失败！未知原因");
		return this.poClient(response, true);
	}

	/**
	 * 重新获取银行校验码
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/app/query/userBankAgainCode")
	public String userBankAgainCode(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String fcardNumber = request.getParameter("fcardNumber"); // 银行显示名字(userBankName.do接口获取)
		if (String_Custom.noNull(fcardNumber))
			return this.poClient(response, false, "缺少必要参数");

		int fcusid = ServerContext.getUseronline().get(request.getSession().getId().toString()).getFuserId();
		CL_Bank info = bankDao.getByUserIdAndFcardNumber(fcardNumber, fcusid);
		if (info == null)
			return this.poClient(response, false, "未获取到此银行卡信息");

		// 判断时间 fbankpayTime
		long dateTimer = (System.currentTimeMillis() - info.getFbankpayTime().getTime()) / 1000;
		if (dateTimer < 60) {
			dateTimer = 60 - dateTimer;
			return this.poClient(response, false, "请等待" + dateTimer + "秒后再试");
		}
		info.setFbankpayTime(new Date());
		bankDao.update(info);
		String msg = sendBankMessageToUser(fcusid + "", info);

		JSONObject jo = JSONObject.fromObject(msg);
		if (!"true".equals(jo.get("success").toString())) {
			String msg2 = jo.get("msg").toString();
			if ("未知错误".equals(jo.get("msg").toString())) {
				msg2 = "请一分钟后重试..";
			}
			return this.poClient(response, false, msg2);
		}

		return this.poClient(response, true);
	}

	/**
	 * 发送银行卡绑定信息
	 */
	public String sendBankMessageToUser(String fcusid, CL_Bank model) {

		// bindPayerId 绑定支付人(外部ID，不可空)
		// bankServiceProvider 银行服务接口(PAB、CMB)
		// bankID 银行ID(不可空，请参考相对应的银行支持的编码)
		// bankCardType 银行卡类型(1借记卡2信用卡)
		// bankCardNumber 银行卡号(不可空)
		// reservedMobileNumber 预留手机号码(不可空，会校验格式，支持中国手机号码)
		// bankCardUserName 姓名(不可空-只有支持中文，最长15最小1)
		// bankCardUserIDCard 用户身份证(不可空，银行接口校验)
		// creditCardCvn Cvn(借记卡可空/信用卡不可空)
		// creditCardYear 有效年(借记卡可空/信用卡不可空)
		// creditCardMonth 有效月(借记卡可空/信用卡不可空)
		LinkedHashMap<String, Object> modellist = new LinkedHashMap<>();
		modellist.put("bindPayerId", MD5Util.getMD5String(fcusid + "ylhy").substring(0, 30));
		modellist.put("bankServiceProvider", "PAB");
		modellist.put("bankID", model.getFcardpaytype());
		modellist.put("bankCardType", model.getFtype());
		modellist.put("bankCardNumber", model.getFcardNumber());
		modellist.put("reservedMobileNumber", model.getFtel());
		modellist.put("bankCardUserName", model.getFname());
		modellist.put("bankCardUserIDCard", model.getFcard());
		modellist.put("creditCardCvn", model.getFcreditCardCvn());
		modellist.put("creditCardYear", "20" + model.getFcreditCardYear());
		modellist.put("creditCardMonth", model.getFcreditCardMonth());
		if ("1".equals(model.getFtype())) {
			modellist.put("creditCardCvn", "");
			modellist.put("creditCardYear", "");
			modellist.put("creditCardMonth", "");
		}
		HashMap<String, Object> param = new HashMap<String, Object>();
		param.put("fcusid", fcusid);
		param.put("userDao", userDao);
		param.put("FinanceDao", FinanceDao);

		return PayUtil.bindBankInfoToPaySystem(param, modellist);
	}

	/**
	 * 校验银行短信验证码
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/app/query/userBankCheckCode")
	public String userBankCheckCode(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String fcardNumber = request.getParameter("fcardNumber"); // 银行卡号
		String fverifyCode = request.getParameter("fverifyCode"); // 验证码
		String pIsEmpty = String_Custom.parametersEmpty(new String[] { fcardNumber, fverifyCode, }, "缺少必要参数");
		if (pIsEmpty.length() != 0)
			return this.poClient(response, false, pIsEmpty);
		// BindPayerId 绑定支付人
		// BankCardNumber 银行卡号
		// VerifyCode 验证码
		int fcusid = ServerContext.getUseronline().get(request.getSession().getId().toString()).getFuserId();
		String vmifid = ServerContext.getUseronline().get(request.getSession().getId().toString()).getFuserid();
		CL_Bank clbank = bankDao.getByUserIdAndFcardNumber(fcardNumber, fcusid);
		if (clbank == null)
			return this.poClient(response, false, "未获取到相关银行卡信息");

		// if(success==0)
		// this.poClient(response, false,"数据库炸掉了");

		HashMap<String, Object> param = new HashMap<>();
		param.put("vmifid", vmifid);
		param.put("fcusid", fcusid);
		param.put("userDao", userDao);
		param.put("FinanceDao", FinanceDao);

		// 去支付系统校验
		LinkedHashMap<String, Object> modellist = new LinkedHashMap<>();
		modellist.put("bindPayerId", MD5Util.getMD5String(fcusid + "ylhy").substring(0, 30));
		modellist.put("bankCardNumber", fcardNumber);
		modellist.put("verifyCode", fverifyCode);
		modellist.put("orderNumber", "");

		String msg = PayUtil.bindBankCheckToPaySystem(param, modellist);
		System.out.println("成功数据：" + msg);
		JSONObject jo = JSONObject.fromObject(msg);
		// int code = Integer.parseInt(jo.get("code").toString());
		if ("true".equals(jo.get("success").toString())) {
			String number = JSONObject.fromObject(jo.get("data")).get("bindId").toString();
			bankDao.updateByStatus(1, 2, number, clbank.getFid(), fcusid);
			return this.poClient(response, true);
		} else {
			return this.poClient(response, false, jo.get("msg").toString());

		}

	}

	/**
	 * 上传银行信息
	 * 
	 * @param request
	 *            request
	 * @param response
	 *            response
	 * @throws IOException
	 */
	@RequestMapping("/app/query/userBankUpData")
	public String userBankUpData(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String fbankName = request.getParameter("fbankName"); // 银行显示名字(userBankName.do接口获取)
		String fbankCardType = request.getParameter("fbankCardType"); // 银行卡类型（1借记卡2信用卡）
		String fcardNumber = request.getParameter("fcardNumber"); // 银行卡号（不可空）
		String ftel = request.getParameter("ftel"); // 预留手机号码（不可空，会校验格式，支持中国手机号码）
		String fname = request.getParameter("fname"); // 用户姓名（不可空-只有支持中文，最长15最小1）
		String fcard = request.getParameter("fcard"); // 用户身份证
		String fbankCarID = request.getParameter("fbankCarID"); // 卡类型ID（userBankName.do接口获取）
		String fcardpaytype = request.getParameter("fcardpaytype"); // 银行类型验证码（userBankName.do接口获取）
		String fcreditCardCvn = request.getParameter("fcreditCardCvn"); // 信用卡Cvn（只有当类型为信用卡传输）
		String fcreditCardYear = request.getParameter("fcreditCardYear"); // 信用卡有效年份（只有当类型为信用卡传输）
		String fcreditCardMonth = request.getParameter("fcreditCardMonth"); // 信用卡有限月份（只有当类型为信用卡传输）

		// fbankName = "交通银行借记卡";
		// fbankCardType = "1";
		// fcardNumber = "6222623110003739792";
		// ftel = "13587653526";
		// fname = "蒋凡毅";
		// fcard = "330328199302212739";
		// fbankCarID = "492";
		// fcardpaytype = "BOCOM-D";

		// 必要参数不为空 判断
		String pIsEmpty = String_Custom.parametersEmpty(
				new String[] { fbankName, fbankCardType, fcardNumber, ftel, fname, fcard, fbankCarID, fcardpaytype },
				"缺少必要参数");
		if ("2".equals(fbankCardType) && pIsEmpty.length() == 0)
			pIsEmpty = String_Custom.parametersEmpty(new String[] { fcreditCardCvn, fcreditCardYear, fcreditCardMonth },
					"信用卡所需参数未收到");
		if (pIsEmpty.length() != 0)
			return this.poClient(response, false, pIsEmpty);

		// 判断参数是否合法
		if (String_Custom.noChinese(fname))
			return this.poClient(response, false, "姓名格式有误!");
		if (String_Custom.noMobile(ftel))
			return this.poClient(response, false, "预留手机号格式有误!");
		if (String_Custom.noIDCard(fcard))
			return this.poClient(response, false, "用户身份证格式有误!");

		int fcusid = ServerContext.getUseronline().get(request.getSession().getId().toString()).getFuserId();

		CL_Bank model = bankDao.getByUserIdAndFcardNumber(fcardNumber, fcusid);
		if (model == null) {
			// 模型赋值
			model = new CL_Bank();
			model.setFbankpayTime(new Date(System.currentTimeMillis() - 60));
		 	//2017-01-11 cd number存每个卡号的bindPayerId
//			model.setNumber(MD5Util.getMD5String(System.nanoTime()+"ylhy").substring(0, 30));
		} else {
			// 判断时间 fbankpayTime
			long dateTimer = (System.currentTimeMillis() - model.getFbankpayTime().getTime()) / 1000;
			if (dateTimer < 60) {
				dateTimer = 60 - dateTimer;
				return this.poClient(response, false, "请等待" + dateTimer + "秒后再试");
			}
			model.setFbankpayTime(new Date());
			
//			if(model.getNumber()==null || "".equals(model.getNumber())){
//				//2017-01-11 cd number存每个卡号的bindPayerId
//				model.setNumber(MD5Util.getMD5String(System.nanoTime()+"ylhy").substring(0, 30));
//			}
		}
		model.setFbankName(fbankName);
		model.setFcard(fcard);
		model.setFtel(ftel);
		model.setFcardpaytype(fcardpaytype);
		model.setFcardNumber(fcardNumber);
		model.setFtype(fbankCardType);

		model.setFuserId(fcusid);
		model.setFcreateTime(new Date());
		model.setFdelfcl_ban_info__id(Integer.parseInt(fbankCarID));
		model.setFname(fname);
		model.setFdel(0);
		model.setFdefault(0);
		model.setFeffect(0);

		if ("2".equals(fbankCardType)) {
			model.setFcreditCardCvn(fcreditCardCvn);
			model.setFcreditCardMonth(fcreditCardMonth);
			model.setFcreditCardYear(fcreditCardYear);
		}

		// 发送短信验证码 失败直接返回前端失败
		String msg = sendBankMessageToUser(fcusid + "", model);

		JSONObject jo = JSONObject.fromObject(msg);
		if ("true".equals(jo.get("success").toString())) {
			// 查询数据库中 是否存在此卡信息
			int status = 0; // 此变量记录 插入或更新是否成功
			if (model.getFid() == null)
				status = bankDao.save(model);
			else {
				// model.setFid(info.getFid());
				status = bankDao.update(model);
			}

			if (status == 1)
				return this.poClient(response, true);
			else
				return this.poClient(response, false, "银行信息更新失败！");

		} else {
			String msg2 = jo.get("msg").toString();
			if ("未知错误".equals(jo.get("msg").toString())) {
				msg2 = "请一分钟后重试..";
			}
			return this.poClient(response, false, msg2);
		}

	}

}
