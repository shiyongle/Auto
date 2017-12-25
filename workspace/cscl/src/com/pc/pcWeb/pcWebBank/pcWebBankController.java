package com.pc.pcWeb.pcWebBank;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.pc.controller.BaseController;
import com.pc.dao.UserRole.IUserRoleDao;
import com.pc.dao.bank.IbankDao;
import com.pc.dao.bankInfo.IbankInfoDao;
import com.pc.dao.financeStatement.IFinanceStatementDao;
import com.pc.model.CL_Bank;
import com.pc.model.CL_Bank_Info;
import com.pc.model.CL_UserRole;
import com.pc.model.Util_Option;
import com.pc.query.bankQuery.bankQuery;
import com.pc.query.order.OrderQuery;
import com.pc.util.CacheUtilByCC;
import com.pc.util.JSONUtil;
import com.pc.util.MD5Util;
import com.pc.util.ServerContext;
import com.pc.util.String_Custom;
import com.pc.util.pay.PayContext;
import com.pc.util.pay.PayUtil;
import com.pc.util.pay.pcWebBankInfo;
import com.pc.util.pay.pcWebBankModel;

@Controller
public class pcWebBankController extends BaseController {
	@Resource
	private IbankDao bankDao;
	@Resource
	private IbankInfoDao ibankInfoDao;
	@Resource
	private IFinanceStatementDao FinanceDao;
	@Resource
	private IUserRoleDao userDao;

	private bankQuery bankquery;

	public bankQuery getBankquery() {
		return bankquery;
	}

	public void setBankquery(bankQuery bankquery) {
		this.bankquery = bankquery;
	}

	/*** 根据银行卡号 获取是什么银行 */
	@RequestMapping("/pcWeb/bank/findBankName")
	public String findBankName(HttpServletRequest request,
			HttpServletResponse response, String bankCard) throws IOException {
		HashMap<String, Object> map = new HashMap<>();
		CL_Bank_Info bankInfo;
		if (bankCard.length() < 10) {
			map.put("success", "false");
			map.put("msg", "银行卡输入有误");
			return writeAjaxResponse(response, JSONUtil.getJson(map));
		}
		int l = 9;
		boolean f = true;
		while (f) {
			String card = bankCard.substring(0, l);
			List<CL_Bank_Info> info = ibankInfoDao.getByBankInfo(card, false);
			if (info.size() > 0) {
				bankInfo = info.get(0);
				map.put("success", "true");
				map.put("data", bankInfo);
				return writeAjaxResponse(response, JSONUtil.getJson(map));
			}
			l--;
			if (l == 2) {
				f = false;
			}
		}
		map.put("success", "false");
		map.put("data", "银行卡输入有误");
		return writeAjaxResponse(response, JSONUtil.getJson(map));

	}

	/*** 绑定银行卡号 */
	@RequestMapping("/pcWeb/bank/BankBanDing")
	public String BankBanDing(HttpServletRequest request,
			HttpServletResponse response, CL_Bank bank) throws IOException {
		LinkedHashMap<String, Object> modellist = new LinkedHashMap<>();
		if (!ServerContext.getUseronline().containsKey(
				request.getSession().getId().toString())) {
			modellist.put("success", "false");
			modellist.put("msg", "未登录！");
			return writeAjaxResponse(response, JSONUtil.getJson(modellist));
		}
		String fbankName = request.getParameter("fbankName"); // 银行显示名字(userBankName.do接口获取)
		String fbankCardType = request.getParameter("fbankCardType"); // 银行卡类型（1借记卡2信用卡）
		String fcardNumber = request.getParameter("fcardNumber"); // 银行卡号（不可空）
		String ftel = request.getParameter("ftel"); // 预留手机号码（不可空，会校验格式，支持中国手机号码）
		String fname = request.getParameter("fname"); // 用户姓名（不可空-只有支持中文，最长15最小1）
		String fcard = request.getParameter("fcard"); // 用户身份证
		String fbankCarID = request.getParameter("fbankCardID"); // 卡类型ID（userBankName.do接口获取）
		String fcardpaytype = request.getParameter("fcardpaytype"); // 银行类型验证码（userBankName.do接口获取）
		String fcreditCardCvn = request.getParameter("fcreditCardCvn"); // 信用卡Cvn（只有当类型为信用卡传输）
		String fcreditCardYear = request.getParameter("fcreditCardYear"); // 信用卡有效年份（只有当类型为信用卡传输）
		String fcreditCardMonth = request.getParameter("fcreditCardMonth"); // 信用卡有限月份（只有当类型为信用卡传输）

		// 必要参数不为空 判断
		String pIsEmpty = String_Custom.parametersEmpty(new String[] {
				fbankName, fbankCardType, fcardNumber, ftel, fname, fcard,
				fbankCarID, fcardpaytype }, "缺少必要参数");
		if ("2".equals(fbankCardType) && pIsEmpty.length() == 0)
			pIsEmpty = String_Custom.parametersEmpty(new String[] {
					fcreditCardCvn, fcreditCardYear, fcreditCardMonth },
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

		// 模型赋值
		CL_Bank model = new CL_Bank();
		model.setFbankName(fbankName);
		model.setFcard(fcard);
		model.setFtel(ftel);
		model.setFcardpaytype(fcardpaytype);
		model.setFcardNumber(fcardNumber);
		model.setFtype(fbankCardType);

		int fcusid = ServerContext.getUseronline()
				.get(request.getSession().getId().toString()).getFuserId();
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
		System.out.println(msg);
		JSONObject js = JSONObject.fromObject(msg);
		if ("false".equals(js.get("success").toString()))
			return this.poClient(response, false, js.get("msg").toString());

		// 查询数据库中 是否存在此卡信息
		CL_Bank info = bankDao.getByUserIdAndFcardNumber(
				fcardNumber,
				ServerContext.getUseronline()
						.get(request.getSession().getId().toString())
						.getFuserId());
		int status = 0; // 此变量记录 插入或更新是否成功
		if (info == null){
			model.setFbankpayTime(new Date(System.currentTimeMillis() - 60));
		 	//2017-01-11 cd number存每个卡号的bindPayerId
//			model.setNumber(MD5Util.getMD5String(System.nanoTime()+"ylhy").substring(0, 30));
			status = bankDao.save(model);
		}
		else {
			// 判断时间 fbankpayTime
			long dateTimer = (System.currentTimeMillis() - model.getFbankpayTime().getTime()) / 1000;
			if (dateTimer < 60) {
				dateTimer = 60 - dateTimer;
				return this.poClient(response, false, "请等待" + dateTimer + "秒后再试");
			}
			model.setFbankpayTime(new Date());
			model.setFid(info.getFid());
			
//			if(model.getNumber()==null || "".equals(model.getNumber())){
//				//2017-01-11 cd number存每个卡号的bindPayerId
//				model.setNumber(MD5Util.getMD5String(System.nanoTime()+"ylhy").substring(0, 30));
//			}
			
			status = bankDao.update(model);
		}

		if (status == 1){
			CL_Bank info2 = bankDao.getByUserIdAndFcardNumber(
					fcardNumber,
					ServerContext.getUseronline()
							.get(request.getSession().getId().toString())
							.getFuserId());
			return this.poClient(response, true ,info2.getFid().toString());
		}
		else
			return this.poClient(response, false, "银行信息更新失败！");

	}

	/** 绑定银行卡号 短信校验 */
	@RequestMapping("/pcWeb/bank/VerificationCode")
	public String VerificationCode(HttpServletRequest request,
			HttpServletResponse response, Integer fid, String VerificationCode,
			String BankCardNumber) throws IOException {
		Integer userId; String vmifid;
		HashMap<String, Object> m = new HashMap<String, Object>();
		LinkedHashMap<String, Object> data = new LinkedHashMap<String, Object>();
		if (!ServerContext.getUseronline().containsKey(
				request.getSession().getId().toString())) {
			m.put("success", "false");
			m.put("msg", "未登录！");
			return writeAjaxResponse(response, JSONUtil.getJson(m));
		}
//		System.out.println(ServerContext.getUseronline()
//				.get(request.getSession().getId().toString()).getFuserId());
		userId = ServerContext.getUseronline()
				.get(request.getSession().getId().toString()).getFuserId();
		vmifid = ServerContext.getUseronline()
				.get(request.getSession().getId().toString()).getFuserid();
		CL_Bank clbank = bankDao.getByUserIdAndFcardNumber(BankCardNumber,
				userId);
		if (clbank == null) {
			return this.poClient(response, false, "未获取到相关银行卡信息");
		}
		HashMap<String, Object> param = new HashMap<>();
		param.put("vmifid", vmifid);
		param.put("fcusid", userId);
		param.put("userDao", userDao);
		param.put("FinanceDao", FinanceDao);
		param.put("ftype", 1);
		// 去支付系统校验
		LinkedHashMap<String, Object> modellist = new LinkedHashMap<>();
		modellist.put("bindPayerId", MD5Util.getMD5String(userId + "ylhy").substring(0, 30));
		modellist.put("bankCardNumber", BankCardNumber);
		modellist.put("verifyCode", VerificationCode);
		modellist.put("orderNumber", "");
		String msg = PayUtil.bindBankCheckToPaySystem(param, modellist);//绑定成功，返回所有数据
		System.out.println("成功数据：" + msg);
		JSONObject jo = JSONObject.fromObject(msg);
		int code = Integer.parseInt(jo.get("code").toString());
		if("true".equals(jo.get("success").toString())){
			String number = JSONObject.fromObject(jo.get("data")).get("bindId").toString();
			if(code == 1008){//1008操作成功，即绑定成功，且扣款成功，需要在余额中返还给用户0.1元；
				CL_UserRole clu = userDao.getById(userId);
				BigDecimal balance = clu.getFbalance().add(new BigDecimal(0.1));
				clu.setFbalance(balance);
				userDao.update(clu);
				bankDao.updateByStatus(1, 2,number,fid, userId);
			}else {
				//1018银行卡绑定成功，但是没有扣款,不需要返还；
				bankDao.updateByStatus(1, 2,number, fid, userId);
			}
			m.put("success", "true");
			m.put("msg", "绑定成功");
			return writeAjaxResponse(response, JSONUtil.getJson(m));
		} else {
			m.put("success", "false");
			m.put("msg", PayContext.responeCode(code));
			return writeAjaxResponse(response, JSONUtil.getJson(m));
		}
//		if (msg.length() > 0) {
//			m.put("success", "false");
//			m.put("msg", msg);
//			return writeAjaxResponse(response, JSONUtil.getJson(m));
//		} else {
//			bankDao.updateByStatus(0, 2, clbank.getFid(), userId);
//			m.put("success", "true");
//			m.put("msg", "绑定成功");
//			return writeAjaxResponse(response, JSONUtil.getJson(m));
//		}
	}

	/*** 查询银行卡列表 */
	@RequestMapping("/pcWeb/bank/findByBankList")
	public String findByBankList(HttpServletRequest request,
			HttpServletResponse response, Integer type) throws IOException {
		Integer userId;
		HashMap<String, Object> m = new HashMap<String, Object>();
		if (!ServerContext.getUseronline().containsKey(
				request.getSession().getId().toString())) {
			m.put("success", "false");
			m.put("msg", "未登录！");
			return writeAjaxResponse(response, JSONUtil.getJson(m));
			// if(ServerContext.getUseronline().get(request.getSession().getId())!=null)
		}
		System.out.println(ServerContext.getUseronline()
				.get(request.getSession().getId().toString()).getFuserId());
		userId = ServerContext.getUseronline()
				.get(request.getSession().getId().toString()).getFuserId();
		bankquery = newQuery(bankQuery.class, null);
		bankquery.setFuserId(userId);
//		bankquery.setFeffect(1);
		bankquery.setFeffect(2);
		bankquery.setFdel(0);
		if (type != null) {
			bankquery.setSortColumns("fid");
			bankquery.setLimit(2);

		}
		List<CL_Bank> banks = bankDao.find(bankquery);
		for (CL_Bank bank : banks) {
			bank.setFname("");
			bank.setFcard("");
			bank.setFcardNumber(bank.getFcardNumber().substring(
					bank.getFcardNumber().length() - 4,
					bank.getFcardNumber().length()));
		}
		m.put("success", "true");
		m.put("data", banks);
		return writeAjaxResponse(response, JSONUtil.getJson(m));

	}

	/*** 删除银行卡 */
	@RequestMapping("/pcWeb/bank/delByBank")
	public String delByBank(HttpServletRequest request,
			HttpServletResponse response, Integer fid) throws IOException {
		Integer userId;
		HashMap<String, Object> m = new HashMap<String, Object>();
		if (!ServerContext.getUseronline().containsKey(
				request.getSession().getId().toString())) {
			m.put("success", "false");
			m.put("msg", "未登录！");
			return writeAjaxResponse(response, JSONUtil.getJson(m));
			// if(ServerContext.getUseronline().get(request.getSession().getId())!=null)
		}
		System.out.println(ServerContext.getUseronline()
				.get(request.getSession().getId().toString()).getFuserId());
		userId = ServerContext.getUseronline()
				.get(request.getSession().getId().toString()).getFuserId();
		bankDao.updateByFdel(1, fid, userId);
		m.put("success", "true");
		m.put("msg", "删除成功！");
		return writeAjaxResponse(response, JSONUtil.getJson(m));

	}

	/*** 添加银行卡 */
	/*
	 * @RequestMapping("/pcWeb/bank/addByBank") public String
	 * addByBank(HttpServletRequest request,HttpServletResponse
	 * response,pcWebBankModel bankModel) throws IOException{ Integer userId;
	 * HashMap<String,Object> m =new HashMap<String,Object>();
	 * if(!ServerContext.
	 * getUseronline().containsKey(request.getSession().getId().toString())){
	 * m.put("success", "false"); m.put("msg","未登录！"); return
	 * writeAjaxResponse(response, JSONUtil.getJson(m));
	 * //if(ServerContext.getUseronline
	 * ().get(request.getSession().getId())!=null) }
	 * System.out.println(ServerContext
	 * .getUseronline().get(request.getSession().
	 * getId().toString()).getFuserId()); userId
	 * =ServerContext.getUseronline().get
	 * (request.getSession().getId().toString()).getFuserId(); CL_Bank bank=
	 * bankDao.getByUserIdAndFcardNumberAndV(bankModel.getBankBindingIDNumber(),
	 * userId); if(bank!=null){ bank.setFdel(0); bankDao.update(bank);
	 * m.put("success", "true"); m.put("msg", "添加成功！"); return
	 * writeAjaxResponse(response, JSONUtil.getJson(m)); }else{
	 * if(bankModel.getBankBindingIDNumber
	 * ()!=null&&!"".equals(bankModel.getBankBindingIDNumber())){
	 * if(bankModel.getBankBindingIDNumber().length()>=6){ String
	 * bankstr=bankModel.getBankBindingIDNumber().substring(0, 6); char[]
	 * cardNumber = bankstr.toCharArray();//卡号 String
	 * asda=pcWebBankInfo.getNameOfBank(cardNumber, 0);
	 * if(asda==null||"".equals(asda)){ m.put("success", "false");
	 * m.put("msg","请输入正确的银行卡或者该银行卡类型不支持"); return writeAjaxResponse(response,
	 * JSONUtil.getJson(m)); } String[] o=asda.split("\\.");
	 * if(asda.contains("信用卡")||asda.contains("贷记卡")){ o[0]=o[0]+"信用卡"; }else{
	 * o[0]=o[0]+"借记卡"; } bankModel.setBankName(o[0]); }
	 * 
	 * }
	 * bank=bankDao.getByUserIdAndFcardNumber(bankModel.getBankBindingIDNumber(
	 * ), userId); String onumber=""; if(bank==null){
	 * bank.setFbankName(bankModel.getBankName()); bank.setFcreateTime(new
	 * Date()); bank.setFname(bankModel.getBankBindingUserName());
	 * bank.setFtel(bankModel.getBankBindingMobileNumber());
	 * bank.setFcardNumber(bankModel.getBankBindingIDNumber());
	 * bank.setFcard(bankModel.getBankBindingUserIDCard()); bank.setFdefault(0);
	 * bank.setFeffect(0); bank.setFuserId(userId);
	 * onumber=CacheUtilByCC.getOrderNumber("cl_bank", "B", 8);
	 * bank.setNumber(onumber); bankDao.save(bank); }else{
	 * onumber=bank.getNumber(); } String
	 * red=PayUtil.BindDingBanKAndPay(onumber, "0.01", bankModel); JSONObject ad
	 * =JSONObject.fromObject(red); if(ad.get("success").equals("true")){
	 * System.out.println(ad.get("data"));
	 * bank.setFcardpaytype(ad.get("data").toString()); bankDao.update(bank);
	 * m.put("success", "true"); m.put("data",ad.get("data")); m.put("fid",
	 * bank.getFid()); return writeAjaxResponse(response, JSONUtil.getJson(m));
	 * }
	 * 
	 * return null; } }
	 */

	/*** 绑定银行卡 再次发送短信 */
	/*
	 * @RequestMapping("/pcWeb/bank/addByBankAgain") public String
	 * addByBankAgain(HttpServletRequest request,HttpServletResponse
	 * response,Integer fid) throws IOException{ Integer userId;
	 * HashMap<String,Object> m =new HashMap<String,Object>();
	 * if(!ServerContext.
	 * getUseronline().containsKey(request.getSession().getId().toString())){
	 * m.put("success", "false"); m.put("msg","未登录！"); return
	 * writeAjaxResponse(response, JSONUtil.getJson(m));
	 * //if(ServerContext.getUseronline
	 * ().get(request.getSession().getId())!=null) }
	 * System.out.println(ServerContext
	 * .getUseronline().get(request.getSession().
	 * getId().toString()).getFuserId()); userId
	 * =ServerContext.getUseronline().get
	 * (request.getSession().getId().toString()).getFuserId(); CL_Bank bank=
	 * bankDao.getByIdAndFcreator(fid, userId); pcWebBankModel bankModel=new
	 * pcWebBankModel();
	 * bankModel.setBankBindingIDNumber(bank.getFcardNumber());
	 * bankModel.setBankBindingMobileNumber(bank.getFtel());
	 * bankModel.setBankBindingUserName(bank.getFname());
	 * bankModel.setBankBindingUserIDCard(bank.getFcard());
	 * bankModel.setBankName(bank.getFbankName()); String
	 * red=PayUtil.BindDingBanKAndPay(bank.getNumber(), "0.01", bankModel);
	 * JSONObject ad =JSONObject.fromObject(red);
	 * if(ad.get("success").equals("true")){ System.out.println(ad.get("data"));
	 * bank.setFcardpaytype(ad.get("data").toString()); bankDao.update(bank);
	 * m.put("success", "true"); m.put("data",ad.get("data")); m.put("fid",
	 * bank.getFid()); return writeAjaxResponse(response, JSONUtil.getJson(m));
	 * }
	 * 
	 * return null; }
	 */

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
		HashMap<String, Object> param = new HashMap<>();
		modellist.put("bindPayerId", MD5Util.getMD5String(fcusid + "ylhy").substring(0, 30));
		modellist.put("bankServiceProvider", "PAB");
		modellist.put("bankID", model.getFcardpaytype());
		modellist.put("bankCardType", model.getFtype());
		modellist.put("bankCardNumber", model.getFcardNumber());
		modellist.put("reservedMobileNumber", model.getFtel());
		modellist.put("bankCardUserName", model.getFname());
		modellist.put("bankCardUserIDCard", model.getFcard());
		modellist.put("creditCardCvn", model.getFcreditCardCvn());
		modellist.put("creditCardYear", "20"+model.getFcreditCardYear());
		modellist.put("creditCardMonth", model.getFcreditCardMonth());
		if ("1".equals(model.getFtype())) {
			modellist.put("creditCardCvn", "");
			modellist.put("creditCardYear", "");
			modellist.put("creditCardMonth", "");
		}
		param.put("fcusid", fcusid);
		param.put("userDao", userDao);
		param.put("FinanceDao", FinanceDao);

		return PayUtil.bindBankInfoToPaySystem(param,modellist);
	}

}
