package com.pc.pcWeb.pcWebPay;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.org.rapid_framework.page.Page;

import com.pc.controller.BaseController;
import com.pc.dao.UserRole.IUserRoleDao;
import com.pc.dao.addto.IaddtoDao;
import com.pc.dao.bank.IbankDao;
import com.pc.dao.financeStatement.IFinanceStatementDao;
import com.pc.dao.order.IorderDao;
import com.pc.dao.orderDetail.IorderDetailDao;
import com.pc.dao.select.IUtilOptionDao;
import com.pc.dao.userVoucher.IuserVoucherDao;
import com.pc.model.CL_Addto;
import com.pc.model.CL_Bank;
import com.pc.model.CL_FinanceStatement;
import com.pc.model.CL_Order;
import com.pc.model.CL_OrderDetail;
import com.pc.model.CL_UserRole;
import com.pc.model.Util_Option;
import com.pc.query.financeStatement.FinanceStatementQuery;
import com.pc.util.CacheUtilByCC;
import com.pc.util.JSONUtil;
import com.pc.util.MD5Util;
import com.pc.util.ServerContext;
import com.pc.util.pay.OrderMsg;
import com.pc.util.pay.PayContext;
import com.pc.util.pay.PayUtil;
@Controller
public class pcWebPayController extends BaseController{
	@Resource
	private IorderDao  orderDao;
	@Resource
	private IUtilOptionDao optionDao;
	@Resource 
	private IorderDetailDao orderDetailDao;
	@Resource
	private IUserRoleDao userDao;
	@Resource
	private IuserVoucherDao userVoucherDao;
	@Resource
	private IbankDao ibankDao;
	@Resource
	private IFinanceStatementDao FinanceDao;
	@Resource
	private IaddtoDao iaddtoDao;
	
	protected static final String LOAD_LIST= "/pages/pcWeb/payment/payment.jsp";
	protected static final String VOUCHER_LIST="/pages/pcWeb/wallet/wallet_recharge.jsp";
	protected static final String VOUCHER_LIST2="/pages/pcWeb/wallet/wallet_payway.jsp";
	protected static final String MIXPAY_INFO = "/pages/pcWeb/payment/payment_mix.jsp";

	
	
	/***充值跳转页面1*/
	@RequestMapping("/pcWeb/pcWebPay/pcWebVoucher")
	public String pcWebVoucher(HttpServletRequest request,HttpServletResponse response) throws IOException{
		Integer userId;
		HashMap<String,Object> m =new HashMap<String,Object>();
		if(!ServerContext.getUseronline().containsKey(request.getSession().getId().toString())){
	    	m.put("success", "false");
			m.put("msg","未登录！");
			return writeAjaxResponse(response, JSONUtil.getJson(m));
		//if(ServerContext.getUseronline().get(request.getSession().getId())!=null)
	    }
	   System.out.println(ServerContext.getUseronline().get(request.getSession().getId().toString()).getFuserId());
	   userId =ServerContext.getUseronline().get(request.getSession().getId().toString()).getFuserId();
	   CL_UserRole user=new CL_UserRole();
	   if(userId!=null&&!"".equals(userId)){
		   user=userDao.getById(userId);
		   request.getSession().setAttribute("data",user);
	    }
		return VOUCHER_LIST;
		}
	
	
	
	/***充值跳转页面步骤2*/
	@RequestMapping("/pcWeb/pcWebPay/pcWebVoucher2")
	public String pcWebVoucher2(HttpServletRequest request,HttpServletResponse response,BigDecimal money) throws IOException{
		Integer userId ;String vmifid;
		HashMap<String,Object> m =new HashMap<String,Object>();
		if(!ServerContext.getUseronline().containsKey(request.getSession().getId().toString())){
	    	m.put("success", "false");
			m.put("msg","未登录！");
			return writeAjaxResponse(response, JSONUtil.getJson(m));
	    }
		if(money.compareTo(BigDecimal.ZERO) < 1){
			m.put("success", "false");
			m.put("success", "充值金額不符合要求！");
			return writeAjaxResponse(response, JSONUtil.getJson(m));
		}
	    System.out.println(ServerContext.getUseronline().get(request.getSession().getId().toString()).getFuserId());
	    userId =ServerContext.getUseronline().get(request.getSession().getId().toString()).getFuserId();
	    vmifid= ServerContext.getUseronline().get(request.getSession().getId().toString()).getFuserid();
	    /*String numerStr=CacheUtilByCC.getOrderNumber("cl_finance_statement", "L", 8);
	    CL_UserRole user=userDao.getById(userId);
	    if(user!=null){
	    	CL_FinanceStatement financeStatement = new CL_FinanceStatement();
	    	financeStatement.setFuserroleId(userId);
	    	financeStatement.setFamount(money);
	    	financeStatement.setNumber(numerStr);
	    	financeStatement.setFstatus(0);
	    	financeStatement.setFpayType(0);//支付方式？？？
	    	financeStatement.setFtype(0);//充值时算是支出1还是收入-1？
	    	financeStatement.setFbusinessType(6);//6充值
	    	financeStatement.setFuserid(user.getVmiUserFid());
	    	financeStatement.setFremark("充值");
	    	financeStatement.setFbalance(user.getFbalance());
	    	FinanceDao.save(financeStatement);*/
	    	request.getSession().setAttribute("money",money); 
//            request.getSession().setAttribute("stateFid",FinanceDao.getByNumber(numerStr).get(0).getFid());
            m.put("success", "true");
            return VOUCHER_LIST2;
	    /*}else{
	    	m.put("success", "false");
	    	m.put("msg","数据有误 充值失败！");
	    	return writeAjaxResponse(response, JSONUtil.getJson(m));
	    }*/
	}
	
	/**
	 * 充值跳转页面步骤3、支付宝 和微信生成标签
	 * @param request
	 * @param response
	 * @param type 類型1支付寶，2微信
	 * @param money 充值金額
	 * @param orderId 下单支付时订单的fid，如果是充值，该值就是null
	 * @param add 判断是否是追加
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/pcWeb/pcWebPay/pcWebVoucher3")
	public String pcWebVoucher3(HttpServletRequest request,HttpServletResponse response, 
			Integer type, BigDecimal money, Integer orderId, Integer add) throws IOException {
		Integer userId;
		HashMap<String, Object> m = new HashMap<String, Object>();
		HashMap<String, Object> param = new HashMap<String, Object>();
		LinkedHashMap<String, Object> modellist = new LinkedHashMap<>();
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
		if (!ServerContext.getUseronline().containsKey(
				request.getSession().getId().toString())) {
			m.put("success", "false");
			m.put("msg", "未登录！");
			return writeAjaxResponse(response, JSONUtil.getJson(m));
		}
		System.out.println(ServerContext.getUseronline()
				.get(request.getSession().getId().toString()).getFuserId());
		userId = ServerContext.getUseronline()
				.get(request.getSession().getId().toString()).getFuserId();
		if (type == 1) {// 支付宝
			if(orderId == null){//充值没有订单
				modellist.put("orderNumber", "");// 订单编号
				modellist.put("orderCreateTime", format.format(new Date()));// 订单时间
				modellist.put("payerId", MD5Util.getMD5String(userId + "ylhy")
						.subSequence(0, 30));// 付款人
				modellist.put("serviceProvider", "ALI");// 支付服务商
				modellist.put("serviceProviderType", "QR");// 服务商类型
				modellist.put("bankCardBindId", "");// 银行卡绑定ID,支付宝微信空字符串
				modellist.put("payAmount", money.toString());// 支付金额
				modellist.put("description", "充值");// 商品说明
				modellist.put("notificationURL", "");// 通知URL
				param.put("fremark", "支付宝充值");
				param.put("ftype", 1);
				param.put("famount", money);
				param.put("forderId", "");
				param.put("frelatedId", "");
			} else {
				CL_Order order = orderDao.getById(orderId);
				if (add == null) {
					Date loadTime = order.getLoadedTime();
					if((loadTime.getTime()-new Date().getTime()) < 60000*60){
						return this.poClient(response, false, "距离装车时间已不足一小时，请重新下单");
					}
					List<CL_FinanceStatement> list = FinanceDao
							.getforderId(order.getNumber());
					if (list.size() > 0) {
						for (CL_FinanceStatement statement : list) {// 查询该订单所有明细，是否有已成功的，已成功则不可再支付
							if (statement.getFstatus() == 1) {
								m.put("success", "false");
								m.put("msg", "请勿重复支付！");
								return writeAjaxResponse(response,
										JSONUtil.getJson(m));
							}
						}
					}
				}
				modellist.put("orderNumber", order.getNumber());// 订单编号
				modellist.put("orderCreateTime", format.format(new Date()));// 订单时间
				modellist.put("payerId", MD5Util.getMD5String(userId + "ylhy")
						.subSequence(0, 30));// 付款人
				modellist.put("serviceProvider", "ALI");// 支付服务商
				modellist.put("serviceProviderType", "QR");// 服务商类型
				modellist.put("bankCardBindId", "");// 银行卡绑定ID,支付宝微信空字符串
				modellist.put("payAmount", order.getFreight().toString());// 支付金额
				modellist.put("description", "支付");// 商品说明
				modellist.put("notificationURL", "");// 通知URL
				param.put("fremark", "支付宝支付");
				param.put("ftype", -1);
				param.put("famount", order.getFreight());
				param.put("forderId", order.getNumber());
				param.put("frelatedId", order.getId());
				param.put("freight", order.getFreight());
				
			}
			param.put("fpayType", 1);// 明细参数
			param.put("fcusid", userId);
			param.put("userDao", userDao);
			param.put("FinanceDao", FinanceDao);
			String msg = PayUtil.webRechargeByZFBWX(param, modellist);
			System.out.println("支付宝获取支付码：" + msg);
			JSONObject jo = JSONObject.fromObject(msg);
			if ("true".equals(jo.get("success"))) {
				m.put("success", "true");
				m.put("data", jo.get("data"));
				m.put("data1", jo.get("number"));
			} else {
				m.put("success", "false");
				m.put("msg", PayContext.responeCode(Integer.parseInt(jo.get(
						"code").toString())));
			}
		} else if (type == 2) {// 微信
			if(orderId == null){//充值没有订单
				modellist.put("orderNumber", "");// 订单编号
				modellist.put("orderCreateTime", format.format(new Date()));// 订单时间
				modellist.put("payerId", MD5Util.getMD5String(userId + "ylhy")
						.subSequence(0, 30));// 付款人
				modellist.put("serviceProvider", "WX");// 支付服务商
				modellist.put("serviceProviderType", "QR");// 服务商类型
				modellist.put("bankCardBindId", "");// 银行卡绑定ID,支付宝微信空字符串
				modellist.put("payAmount", money.toString());// 支付金额
				modellist.put("description", "充值");// 商品说明
				modellist.put("notificationURL", "");// 通知URL
				param.put("fremark", "支付宝充值");
				param.put("ftype", 1);
				param.put("famount", money);
				param.put("forderId", "");
				param.put("frelatedId", "");
			} else {
				CL_Order order = orderDao.getById(orderId);
				List<CL_FinanceStatement> list = FinanceDao.getforderId(order.getNumber());
				if (add == null) {
					Date loadTime = order.getLoadedTime();
					if((loadTime.getTime()-new Date().getTime()) < 60000*60){
						return this.poClient(response, false, "距离装车时间已不足一小时，请重新下单");
					}
					if (list.size() > 0) {
						for (CL_FinanceStatement statement : list) {// 查询该订单所有明细，是否有已成功的，已成功则不可再支付
							if (statement.getFstatus() == 1) {
								m.put("success", "false");
								m.put("msg", "请勿重复支付！");
								return writeAjaxResponse(response,
										JSONUtil.getJson(m));
							}
						}
					}
				}
				modellist.put("orderNumber", order.getNumber());// 订单编号
				modellist.put("orderCreateTime", format.format(new Date()));// 订单时间
				modellist.put("payerId", MD5Util.getMD5String(userId + "ylhy")
						.subSequence(0, 30));// 付款人
				modellist.put("serviceProvider", "WX");// 支付服务商
				modellist.put("serviceProviderType", "QR");// 服务商类型
				modellist.put("bankCardBindId", "");// 银行卡绑定ID,支付宝微信空字符串
				modellist.put("payAmount", order.getFreight().toString());// 支付金额
				modellist.put("description", "支付");// 商品说明
				modellist.put("notificationURL", "");// 通知URL
				param.put("fremark", "微信支付");
				param.put("ftype", -1);
				param.put("famount", order.getFreight());
				param.put("forderId", order.getNumber());
				param.put("frelatedId", order.getId());
				param.put("freight", order.getFreight());
			}
			param.put("fpayType", 2);// 明细参数
			param.put("fcusid", userId);
			param.put("userDao", userDao);
			param.put("FinanceDao", FinanceDao);
			String msg = PayUtil.webRechargeByZFBWX(param, modellist);
			System.out.println("微信获取支付码：" + msg);
			JSONObject jo = JSONObject.fromObject(msg);
			if ("true".equals(jo.get("success"))) {
				m.put("success", "true");
				m.put("data", jo.get("data"));
				m.put("data1", jo.get("number"));
			} else {
				m.put("success", "false");
				m.put("msg", PayContext.responeCode(Integer.parseInt(jo.get(
						"code").toString())));
			}
		}
		return writeAjaxResponse(response, JSONUtil.getJson(m));
	}
	    
	/**
	 * 二维码扫描之后的轮询
	 * @param request
	 * @param response
	 * @param number 明细number
	 * @return
	 */
	@RequestMapping("/pcWeb/pcWebPay/pcWebPayPolling")
	public String polling(HttpServletRequest request,HttpServletResponse response,String number){
		HashMap<String, Object> m = new HashMap<String, Object>();
		CL_FinanceStatement statement = FinanceDao.getByNumber(number).get(0);
		int status = statement.getFstatus();//轮询支付状态
		if(status == 1){
			if(statement.getFbusinessType() == 1){
				/*CL_Order order = orderDao.getById(Integer.parseInt(statement.getFrelatedId()));
				order.setStatus(2);//派车中
				orderDao.update(order);*/
			}
			m.put("success", "true");
			m.put("msg", "支付成功");
		} else if (status == 2) {
			m.put("success", "false1");
			m.put("msg", "支付失败");
		} else if (status == 0) {
			m.put("success", "false2");
			m.put("msg", "支付中");
		}
		return writeAjaxResponse(response, JSONUtil.getJson(m));
	}
	
	/***支付订单信息*//*
	@RequestMapping("/pcWeb/pcWebPay/pcWebPayOrder")
	public String findProtocol(HttpServletRequest request,HttpServletResponse response,Integer id) throws IOException{
		Integer userId ;
		HashMap<String,Object> m =new HashMap<String,Object>();
		if(!ServerContext.getUseronline().containsKey(request.getSession().getId().toString())){
	    	m.put("success", "false");
			m.put("msg","未登录！");
			return writeAjaxResponse(response, JSONUtil.getJson(m));
		//if(ServerContext.getUseronline().get(request.getSession().getId())!=null)
	    }
	   System.out.println(ServerContext.getUseronline().get(request.getSession().getId().toString()).getFuserId());
	   userId =ServerContext.getUseronline().get(request.getSession().getId().toString()).getFuserId();
	   CL_Order ord =new CL_Order();
	   if(id!=null&&!"".equals(id)){
		   ord=orderDao.getByIdAndCreator(id, userId);
	    }
	    if(ord!=null){
			m.put("success", "true");
			m.put("data", ord);
			return writeAjaxResponse(response, JSONUtil.getJson(m));
	    }
	    m.put("success", "false");
		m.put("msg", "该用户找不到此订单！");
		return writeAjaxResponse(response, JSONUtil.getJson(m));
	}
	
	*//***跳转支付详情页面  提交订单后跳转*/
	@RequestMapping("/pcWeb/pcWebPay/pcWebPayMent")
	public String pcWebPayMent(HttpServletRequest request,HttpServletResponse response,Integer id) throws IOException{
		Integer userId ;
		HashMap<String,Object> m =new HashMap<String,Object>();
		if(!ServerContext.getUseronline().containsKey(request.getSession().getId().toString())){
	    	m.put("success", "false");
			m.put("msg","未登录！");
			return writeAjaxResponse(response, JSONUtil.getJson(m));
		//if(ServerContext.getUseronline().get(request.getSession().getId())!=null)
	    }
		 userId =ServerContext.getUseronline().get(request.getSession().getId().toString()).getFuserId();
		  CL_UserRole user=userDao.getById(userId);
		  if(user==null){
				m.put("success", "false");
				m.put("msg","该用户不存在");
				return writeAjaxResponse(response, JSONUtil.getJson(m));
		  }
//	       String o=PayUtil.UsePayBalance(user.getVmiUserPhone());
//	      JSONObject ad=JSONObject.fromObject(o);
		  BigDecimal balance = user.getFbalance();
	      request.getSession().setAttribute("balance",balance);//前端显示的用户余额
		  CL_Order ord =new CL_Order();
		   if(id!=null&&!"".equals(id)){
			   ord=orderDao.getByIdAndCreator(id, userId);
		    }
		    if(ord!=null){
		    	List<CL_OrderDetail> orde1=this.orderDetailDao.getByOrderId(ord.getId(), 1);
		    	ord.setTakeAddress(orde1.get(0).getAddressName());
		    	ord.setTakephone(orde1.get(0).getPhone());
		    	ord.setTakelinkman(orde1.get(0).getLinkman());
		    	if(ord.getType()!=3){
		    		List<CL_OrderDetail> orde2=this.orderDetailDao.getByOrderId(ord.getId(), 2);
		    	    ord.setRecAddress(orde2.get(0).getAddressName());
		    	    ord.setRecphone(orde2.get(0).getPhone());
		    	    ord.setReclinkman(orde2.get(0).getLinkman());
		    	}
		    	List<Util_Option>  ups=optionDao.getCarTypeByOrderIdName(ord.getId());
				String carSpecName="",carOtherName="";
				Integer carSpecId=null;
				if(ups.size()>0){
					carSpecId=ups.get(0).getOptionId();
					carSpecName=ups.get(0).getOptionName();
					carOtherName =ups.get(0).getOptionCarOtherName();
				}
				for(Util_Option option :ups){
					if(option.getOptionCarTypeName()!=null && !option.getOptionCarTypeName().equals("")){
	 					if(!carSpecName.contains(option.getOptionCarTypeName())){
		 					carSpecName = carSpecName+" "+option.getOptionCarTypeName();
		 				}
	 				}
					
					if(option.getOptionCarOtherName()!=null && !option.getOptionCarOtherName().equals("")){
	 					if(!carOtherName.contains(option.getOptionCarOtherName())){
	 						carOtherName = carOtherName+" "+option.getOptionCarOtherName();
		 				}
	 				}
				}
				ord.setCarTypeName(carSpecName);
				ord.setCarSpecId(carSpecId);
				ord.setCarSpecName(carOtherName);
			 	 //=====银行卡信息
//		         o= PayUtil.UseBingBankNameList(user.getVmiUserPhone());
//		         ad=JSONObject.fromObject(o);
		         request.getSession().setAttribute("bankList",ibankDao.getByUserList(userId, 2)); 
				request.getSession().setAttribute("data",ord);
				return LOAD_LIST;
		    }
			m.put("success", "false");
			m.put("msg","失败请联系客服");
			return writeAjaxResponse(response, JSONUtil.getJson(m));
	}
	/**订单支付宝  微信 签名 请求支付系统 生成支付二维码*//*
	@RequestMapping("/pcWeb/pcWebPay/newRecharge")
	public String newRechargeZFB(HttpServletRequest request,HttpServletResponse response,Integer id,Integer type) throws IOException{
	     //===支付宝签名
		Integer userId;
		HashMap<String,Object> m =new HashMap<String,Object>();
		userId =ServerContext.getUseronline().get(request.getSession().getId().toString()).getFuserId();
		CL_Order ord=orderDao.getByIdAndCreator(id, userId);
		if(ord!=null){
			CL_UserVoucher userV=userVoucherDao.getByForderNumber(ord.getNumber(), userId);
			String onumber="";
			if(userV!=null){
        		onumber=userV.getForderNumber();
        		if(type==1){
        			userV.setFtype(1); //===支付宝签名
        		}else{
        			userV.setFtype(2);//===微信签名
        		}
        	}else{
        		CL_UserVoucher userV1=new CL_UserVoucher();
        		userV1.setFcreator(userId);
        		onumber=ord.getNumber();
        		userV1.setForderNumber(onumber);
        		userV1.setFcreatorTime(new Date());
        		userV1.setFisover(0);
        		userV1.setFpayorRecharge(2);
        		if(type==1){
        			userV1.setFtype(1); //===支付宝签名
        		}else{
        			userV1.setFtype(2);//===微信签名
        		}
        		
        		userV1.setFstatus(0);
        		userV1.setFmoeny(ord.getFreight());
        		userV1.setNumber(CacheUtilByCC.getOrderNumber("cl_user_voucher", "V", 8));
        		userVoucherDao.save(userV1);
        		userV=userV1;
        	}	
			String	o=PayUtil.newRecharge(type,onumber, ord.getCreatorPhone(), ord.getFreight().toString());
	        JSONObject ad =JSONObject.fromObject(o);
	        if(ad.get("success").equals("true"));
	        userV.setFdata(ad.get("data").toString());
	        userVoucherDao.update(userV);
	        request.getSession().setAttribute("userVoucherFid", userV.getFid());
		    m.put("success", "true");
		    m.put("data",ad.get("data"));
		    m.put("userVoucherFid",userV.getFid());
			return writeAjaxResponse(response, JSONUtil.getJson(m));
		}
		 m.put("success", "false");
		 m.put("msg","订单有误客服");
		return writeAjaxResponse(response, JSONUtil.getJson(m));
	}*/
	
	/***查询用户余额、*/
	@RequestMapping("/pcWeb/pcWebPay/UsePayBalance")
	public String UsePayBalance(HttpServletRequest request,HttpServletResponse response,Integer id) throws IOException{
		Integer userId ;BigDecimal balance = new BigDecimal(0);
		HashMap<String,Object> m =new HashMap<String,Object>();
		if(!ServerContext.getUseronline().containsKey(request.getSession().getId().toString())){
	    	m.put("success", "false");
			m.put("msg","未登录！");
			return writeAjaxResponse(response, JSONUtil.getJson(m));
	    }
	    System.out.println(ServerContext.getUseronline().get(request.getSession().getId().toString()).getFuserId());
	    userId =ServerContext.getUseronline().get(request.getSession().getId().toString()).getFuserId();
	    CL_UserRole user=userDao.getById(userId);
	    if(user!=null){
	    	//新支付，走业务 2016/12/13 by lancher
	    	balance = user.getFbalance();
	    	m.put("success", "true");
	     	m.put("data", balance);
	 		return writeAjaxResponse(response, JSONUtil.getJson(m));
	    }
	    m.put("success", "false");
		m.put("msg", "该用户查询余额有误！");
		return writeAjaxResponse(response, JSONUtil.getJson(m));
	}
	
	/***查询货主收支明细、
	 * @throws ParseException */
	@RequestMapping("/pcWeb/pcWebPay/UsePayAccountStatement")
	public String UsePayAccountStatement(HttpServletRequest request,HttpServletResponse response,Integer id,Integer number,String time1,String time2,String key) throws Exception{
		Integer userId ;
		String pageNum = request.getParameter("number");
		String pageSize = request.getParameter("pagesize");
		HashMap<String,Object> m =new HashMap<String,Object>();
		if(!ServerContext.getUseronline().containsKey(request.getSession().getId().toString())){
	    	m.put("success", "false");
			m.put("msg","未登录！");
			return writeAjaxResponse(response, JSONUtil.getJson(m));
	    }
	    System.out.println(ServerContext.getUseronline().get(request.getSession().getId().toString()).getFuserId());
	   userId =ServerContext.getUseronline().get(request.getSession().getId().toString()).getFuserId();
	    CL_UserRole user=userDao.getById(userId);
	    SimpleDateFormat format= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    if(key!=null){
	     GregorianCalendar gc=new GregorianCalendar(); 
	       Date a=new Date();
	       gc.setTime(a);
	       time1=format.format(a);
	       Integer tie=0;
	    	tie=Integer.valueOf(key);
	    	if(tie==1){//一周
	    		gc.add(+3, +1);
	    		time2=format.format(gc.getTime());
	    	}
	    	if(tie==2){//一月
	    		gc.add(+2, +1);
	    		time2=format.format(gc.getTime());
	    	}
	    	if(tie==3){//一年
	    		gc.add(+1, +1);
	    		time2=format.format(gc.getTime());
	    	}
	    	
	    }
	    
	    if(user!=null){
	    	FinanceStatementQuery query = new FinanceStatementQuery();
	    	if(time1 != null ){
	    		query.setCreateTimeBegin(format.parse(time1));
	    	}
	    	if(time2 != null){
	    		query.setCreateTimeEnd(format.parse(time2));
	    	}
	    	query.setPageNumber(Integer.parseInt(pageNum));
	    	query.setPageSize(Integer.parseInt(pageSize));
	    	query.setFuserid(userId.toString());
	    	query.setFstatus(1);
	    	query.setSortColumns("fid desc");
	    	Page<CL_FinanceStatement> page = FinanceDao.findPage(query);
	    	m.put("data", page.getResult());//前端暂时沿用之前的，所以直接返回result吧，分页什么的，因为有扇形图跟折线图
	    	m.put("total", page.getTotalCount());
	    	System.out.println(page.getTotalCount());
	    	m.put("success", "true");
	     	return writeAjaxResponse(response, JSONUtil.getJson(m));
 
	    }
	    m.put("success", "false");
		m.put("msg", "该用户查询明细有误！");
		return writeAjaxResponse(response, JSONUtil.getJson(m));
	}
	
	/**
	 * 收支情况
	 * @param response
	 * @param request
	 * @param type 类型1收入-1支出
	 * @return
	 */
	@RequestMapping("/pcWeb/pcWebPay/paymentSituation")
	public String paymentSituation(HttpServletResponse response,HttpServletRequest request,Integer type){
		Integer userId;
		BigDecimal allCost = new BigDecimal(0);
		HashMap<String, Object> map = new HashMap<>();
		userId =ServerContext.getUseronline().get(request.getSession().getId().toString()).getFuserId();
		List<CL_FinanceStatement> list = FinanceDao.getByUserAndType(userId, type);
		for (CL_FinanceStatement statement : list) {
			allCost = allCost.add(statement.getFamount());
		}
		map.put("data", allCost);
		map.put("success", "success");
		return writeAjaxResponse(response, JSONUtil.getJson(map));
	}
	
	
	
	/***查询用户银行卡列表、*/
	/*@RequestMapping("/pcWeb/pcWebPay/UseBingBankNameList")
	public String UseBingBankNameList(HttpServletRequest request,HttpServletResponse response) throws IOException{
		Integer userId ;
		HashMap<String,Object> m =new HashMap<String,Object>();
		if(!ServerContext.getUseronline().containsKey(request.getSession().getId().toString())){
	    	m.put("success", "false");
			m.put("msg","未登录！");
			return writeAjaxResponse(response, JSONUtil.getJson(m));
		//if(ServerContext.getUseronline().get(request.getSession().getId())!=null)
	    }
	    System.out.println(ServerContext.getUseronline().get(request.getSession().getId().toString()).getFuserId());
	    userId =ServerContext.getUseronline().get(request.getSession().getId().toString()).getFuserId();
	    CL_UserRole user=userRoleDao.getById(userId);
	    if(user!=null){
	        String o=PayUtil.UseBingBankNameList(user.getVmiUserPhone());
	         JSONObject ob=JSONObject.fromObject(o);
	         request.getSession().setAttribute("bankList",ob.get("data"));//把需要指派的订单id保存在request中
	     	 m.put("success", "true");
	     	 m.put("data", ob.get("data"));
	 		return writeAjaxResponse(response, JSONUtil.getJson(m));
	    }
	    m.put("success", "false");
		m.put("msg", "该用户查询余额有误！");
		return writeAjaxResponse(response, JSONUtil.getJson(m));
	}
	
	
	*//**支付宝 扫一扫 微信扫一扫 确认按钮 充值*//*
  	@RequestMapping("/pcWeb/pcWebPay/CheckData")
  	public String CheckData(HttpServletRequest request,HttpServletResponse response,Integer fid,Integer type) throws IOException{
		Integer userId ;
		String onumber="";
		HashMap<String,Object> m =new HashMap<String,Object>();
		if(!ServerContext.getUseronline().containsKey(request.getSession().getId().toString())){
	    	m.put("success", "false");
			m.put("msg","未登录！");
			return writeAjaxResponse(response, JSONUtil.getJson(m));
		//if(ServerContext.getUseronline().get(request.getSession().getId())!=null)
	    }
	    System.out.println(ServerContext.getUseronline().get(request.getSession().getId().toString()).getFuserId());
	    userId =ServerContext.getUseronline().get(request.getSession().getId().toString()).getFuserId();
	    CL_UserRole user=userRoleDao.getById(userId);
	     
	    if(user!=null){
	    	 CL_UserVoucher usercher=userVoucherDao.getByIdAndFcreator(fid, userId);
	    	 JSONObject ob=new JSONObject();
	    	 String o="";
	    	 String BankName="";
	    	if(type==1){
	    	   BankName=PayUtil.PAY_ALIPAY_QR;
	    	   usercher.setFtype(1);
	    	} else if (type==2){
	    	   BankName=PayUtil.PAY_WECHAT_QR;
	    	   usercher.setFtype(2);
	    	} 
	    	if(usercher.getFstatus().equals(1)){
	    		 if(usercher.getFpayorRecharge()==2){
	        		  CL_Order ord=orderDao.getByNumber(usercher.getForderNumber());
	        		  if(ord.getFreight().compareTo(usercher.getFmoeny())!=0){
	        			  m.put("success", "false");
		    	  	      m.put("msg", "运费和支付金额不一样,支付金额已到余额请联系技术人员！");
		    	  	 	  return writeAjaxResponse(response, JSONUtil.getJson(m));
	        		  }
	        		  o=PayUtil.UserOrderPay(usercher.getForderNumber(), user.getVmiUserPhone(), usercher.getFmoeny().toString());
		        	  ob=JSONObject.fromObject(o);
		        	  if(ob.get("success").equals("true")){
		        		 try{
			        		 ord.setStatus(2);
			        		 ord.setFpayTime(new Date());
			        		 ord.setFpayMethod(type);
			        		 orderDao.update(ord);
			        		 statementDao.saveStatement(ord.getId().toString(), ord.getNumber(), 1, ord.getFreight(), -1, ord.getCreator(), ord.getFpayMethod(), user.getVmiUserFid());
		        		 }catch(Exception e){
		        			 m.put("success", "false");
		    	  	     	 m.put("msg", "存在多条订单,数据有误。请联系客服,已经扣除余额");
		    	  	 		 return writeAjaxResponse(response, JSONUtil.getJson(m));
		        		 }
		        	 }else{
		        		 m.put("success", "false");
	    	  	     	 m.put("msg", "支付金额已到余额！");
	    	  	 		 return writeAjaxResponse(response, JSONUtil.getJson(m));
		        	 }
		        	 
		        		 m.put("success", "true");
			  	     	 m.put("msg", "支付成功！");
			  	 		 return writeAjaxResponse(response, JSONUtil.getJson(m));
	        	 }
	    		 m.put("success", "true");
	  	     	 m.put("msg", "充值成功！");
	  	 		 return writeAjaxResponse(response, JSONUtil.getJson(m));
	    	}
	    	if(usercher.getFpayorRecharge()==2){
	    		onumber=usercher.getForderNumber();
	    	}else{
	    		onumber=usercher.getNumber();
	    	}
	    	o=PayUtil.CheckPayStatus(onumber, user.getVmiUserPhone().toString(), BankName) ;
	    	if(o.equals("")||o==null){
	    		 m.put("success", "false");
	  	     	 m.put("msg", ob.get("msg"));
	  	 		 return writeAjaxResponse(response, JSONUtil.getJson(m));
	    	}
	         ob=JSONObject.fromObject(o);
	        if(ob.get("success").equals("true")){
	        	 usercher.setFstatus(1);
	        	 userVoucherDao.update(usercher);
	        	 if(usercher.getFpayorRecharge()==2){
	        		  CL_Order ord=orderDao.getByNumber(usercher.getForderNumber());
	        		  if(ord.getFreight().compareTo(usercher.getFmoeny())!=0){
	        			  m.put("success", "false");
		    	  	      m.put("msg", "运费和支付金额不一样,支付金额已到余额请联系技术人员！");
		    	  	 	  return writeAjaxResponse(response, JSONUtil.getJson(m));
	        		  }
	        		  o=PayUtil.UserOrderPay(usercher.getForderNumber(), user.getVmiUserPhone(), usercher.getFmoeny().toString());
		        	  ob=JSONObject.fromObject(o);
		        	  if(ob.get("success").equals("true")){
		        		 try{
			        		 ord.setStatus(2);
			        		 ord.setFpayTime(new Date());
			        		 ord.setFpayMethod(type);
			        		 orderDao.update(ord);
			        		 statementDao.saveStatement(ord.getId().toString(), ord.getNumber(), 1, ord.getFreight(), -1, ord.getCreator(), ord.getFpayMethod(), user.getVmiUserFid());
		        		 }catch(Exception e){
		        			 m.put("success", "false");
		    	  	     	 m.put("msg", "存在多条订单,数据有误。请联系客服,已经扣除余额");
		    	  	 		 return writeAjaxResponse(response, JSONUtil.getJson(m));
		        		 }
		        	 }else{
		        		 m.put("success", "false");
	    	  	     	 m.put("msg", "支付金额已到余额！");
	    	  	 		 return writeAjaxResponse(response, JSONUtil.getJson(m));
		        	 }
	        	 }
	  	     	 m.put("success", "true");
	  	     	 m.put("msg", ob.get("msg"));
	  	 		 return writeAjaxResponse(response, JSONUtil.getJson(m));
	        }
	        
	   	}
	    m.put("success", "false");
		m.put("msg", "该请先支付！");
		return writeAjaxResponse(response, JSONUtil.getJson(m));
	}
	
  	
  	
  	*//**充值       选择已绑定银行卡**/
	@RequestMapping("/pcWeb/pcWebPay/BangDingPay")
	public String BangDingPay(HttpServletRequest request,HttpServletResponse response,Integer fid,BigDecimal fmoney,Integer orderFid, Integer add) throws IOException{
		HashMap<String,Object> m =new HashMap<String,Object>();
		 HashMap<String, Object> param =  new HashMap<String, Object>();
		Integer userId;
		SimpleDateFormat format= new SimpleDateFormat("yyyyMMdd");
		if(!ServerContext.getUseronline().containsKey(request.getSession().getId().toString())){
	    	m.put("success", "false");
			m.put("msg","未登录！");
			return writeAjaxResponse(response, JSONUtil.getJson(m));
	    }
	    System.out.println(ServerContext.getUseronline().get(request.getSession().getId().toString()).getFuserId());
	    userId =ServerContext.getUseronline().get(request.getSession().getId().toString()).getFuserId();
	    CL_Bank bank=ibankDao.getByIdAndFcreator(fid, userId);
	    CL_Order order = orderDao.getById(orderFid);
	    //先取验证码
	    LinkedHashMap<String, Object> model = new LinkedHashMap<>();
	    if(orderFid == null){
	    	model.put("orderNumber", "");//订单编号
	    	param.put("forderId", "");	
	    	param.put("frelatedId", "");	
	    } else {
			if (add == null) {
				List<CL_FinanceStatement> list = FinanceDao.getforderId(order
						.getNumber());
				if (list.size() > 0) {
					for (CL_FinanceStatement statement : list) {// 查询该订单所有明细，是否有已成功的，已成功则不可再支付
						if (statement.getFstatus() == 1) {
							m.put("success", "false");
							m.put("msg", "请勿重复支付！");
							return writeAjaxResponse(response,
									JSONUtil.getJson(m));
						}
					}
				}
			}
	    	model.put("orderNumber", order.getNumber());//订单编号
	    	param.put("forderId", order.getNumber());	
	    	param.put("frelatedId", orderFid);	
		}
		model.put("orderCreateTime", format.format(new Date()));//订单时间
		model.put("payerId", MD5Util.getMD5String(userId+"ylhy").subSequence(0, 30));//付款人
		model.put("serviceProvider", "BANK");//支付服务商
		model.put("serviceProviderType", "PAB");//服务商类型
		model.put("bankCardBindId", bank.getNumber());//银行卡绑定ID
		if(fmoney != null){
			model.put("payAmount", fmoney.toString().toString());//支付金额
			param.put("fremark", "银行卡充值");
			param.put("famount", fmoney);
		} else {
			model.put("payAmount", order.getFreight().toString());
			param.put("fremark", "银行卡支付");
			param.put("famount", order.getFreight());
		}
		model.put("description", "");//商品说明
		model.put("notificationURL", "");//通知URL
		param.put("fpayType", 3);// 明细参数
		param.put("fcusid", userId);
		param.put("userDao", userDao);
		param.put("FinanceDao", FinanceDao);
		param.put("ftype", 1);	
		String Verification = PayUtil.webRechargeCode(param,model);
		System.out.println(Verification);
		JSONObject ver = JSONObject.fromObject(Verification);
		int code = Integer.parseInt(ver.get("code").toString());
		if("true".equals(ver.get("success"))){
			m.put("success", "true");
			m.put("data",ver.get("data").toString());
			m.put("data1", ver.get("number"));
			return writeAjaxResponse(response, JSONUtil.getJson(m));
		} else {
			m.put("success", "false");
			m.put("msg",PayContext.responeCode(code));
			return writeAjaxResponse(response, JSONUtil.getJson(m));
		}
	}
	
	
	/**
	 * 充值校验验证码
	 * @param request
	 * @param response
	 * @param VerificationCode验证码
	 * @param stateFid明细FID
	 * @param fid银行FID
	 * @return
	 */
	@RequestMapping("/pcWeb/bank/VerificationCodeGo")
	public String VerificationCodeGo(HttpServletRequest request,HttpServletResponse response,String VerificationCode,String stateFid,Integer fid,String verifyCheckCode){
		HashMap<String,Object> m =new HashMap<String,Object>();
		HashMap<String,Object> param =new HashMap<String,Object>();
		Integer userId;
		CL_FinanceStatement statement = FinanceDao.getByNumber(stateFid).get(0);//找明细
		CL_Bank bank = ibankDao.getById(fid);
		userId =ServerContext.getUseronline().get(request.getSession().getId().toString()).getFuserId();
		
	    LinkedHashMap<String, Object> modellist=new LinkedHashMap<>();
	    modellist.put("orderNumber", statement.getNumber());//orderNumber订单号
	    modellist.put("payerId", MD5Util.getMD5String(userId+"ylhy").substring(0, 30));//payerId付款人
	    modellist.put("bankCardBindId", bank.getNumber());//bankCardBindId银行卡绑定ID
	    modellist.put("payAmount", statement.getFamount().toString());//payAmount支付金额
	    modellist.put("verifyCheckCode", verifyCheckCode); //	verifyCheckCode	验证码校验码
	    modellist.put("verifyCode", VerificationCode); //verifyCode验证码
	    modellist.put("notificationURL", ""); //notificationURL通知URL
	    param.put("fcusid", userId);
	    param.put("userDao", userDao);
	    param.put("FinanceDao", FinanceDao);
		String msg = PayUtil.webRecharge(modellist,param);
		System.out.println(msg);
		JSONObject jo = JSONObject.fromObject(msg);
		int code = Integer.parseInt(jo.get("code").toString());
		if("true".equals(jo.get("success"))){
			m.put("success", "true");
			m.put("msg","充值成功！");
			return writeAjaxResponse(response, JSONUtil.getJson(m));
		} else {
			m.put("success", "false");
			m.put("msg",PayContext.responeCode(code));
			return writeAjaxResponse(response, JSONUtil.getJson(m));
		}
	}
	
  	
	  /***充值 ，支付  银行 短信再次发送校验*//*
	   @RequestMapping("/pcWeb/bank/VerificationCodeAgain")
		public String VerificationCodeAgain(HttpServletRequest request,HttpServletResponse response,Integer fid,Integer money) throws IOException{
			 Integer userId;
				HashMap<String,Object> m =new HashMap<String,Object>();
				if(!ServerContext.getUseronline().containsKey(request.getSession().getId().toString())){
			    	m.put("success", "false");
					m.put("msg","未登录！");
					return writeAjaxResponse(response, JSONUtil.getJson(m));
				//if(ServerContext.getUseronline().get(request.getSession().getId())!=null)
			    }
			    System.out.println(ServerContext.getUseronline().get(request.getSession().getId().toString()).getFuserId());
			    userId =ServerContext.getUseronline().get(request.getSession().getId().toString()).getFuserId();
			    CL_Bank bank=ibankDao.getByIdAndFcreator(fid, userId);
			    if(bank==null){
			    	m.put("success", "false");
					m.put("msg","请重新选择银行卡！");
					return writeAjaxResponse(response, JSONUtil.getJson(m));
			    }
				   
			     CL_UserVoucher userV=new CL_UserVoucher();
			     if(bank!=null){
			     userV.setFbankName(bank.getFbankName());
			     userV.setFcreator(userId);
			     userV.setFcreatorTime(new Date());
			     String onumber=CacheUtilByCC.getOrderNumber("cl_user_voucher", "V", 8);
			     userV.setFmoeny(new BigDecimal(money));
			     userV.setNumber(onumber);
			     userV.setFisover(0);
			     userV.setFtype(3);
			     userV.setFstatus(0);
			     String re="";
			     JSONObject o=new JSONObject();
			     re=PayUtil.BindBanKAndPay(onumber, bank.getFtel(), money.toString(), bank.getFbankName());
			     if(re!=""&&!"".equals(re)){
			        o=JSONObject.fromObject(re);
			        if(o.get("success").equals("true")){
			        	userV.setFdata(o.get("data").toString());
			            userVoucherDao.save(userV);
			             request.getSession().setAttribute("payfid", userV.getFid());
			        	 m.put("success", "true");
			    		 m.put("msg", "成功！");
			    		 return writeAjaxResponse(response, JSONUtil.getJson(m));
			        }
			        else{ 
			        	 m.put("success", "true");
						 m.put("msg",o.get("msg"));
						 return writeAjaxResponse(response, JSONUtil.getJson(m));
			        }
			     } 
			   }
			     m.put("success", "false");
				  m.put("msg","失败！");
			   return writeAjaxResponse(response, JSONUtil.getJson(m));
	      }
	 
		
		*//***下单 用户余额支付  业务*//*
		@RequestMapping("/pcWeb/pcWebPay/UserPayByBalance")
		@Transactional
		public String UserPayByBalance(HttpServletRequest request,HttpServletResponse response,Integer fid) throws IOException{
			Integer userId ;
			HashMap<String,Object> m =new HashMap<String,Object>();
			if(!ServerContext.getUseronline().containsKey(request.getSession().getId().toString())){
		    	m.put("success", "false");
				m.put("msg","未登录！");
				return writeAjaxResponse(response, JSONUtil.getJson(m));
			//if(ServerContext.getUseronline().get(request.getSession().getId())!=null)
		    }
		    System.out.println(ServerContext.getUseronline().get(request.getSession().getId().toString()).getFuserId());
		    userId =ServerContext.getUseronline().get(request.getSession().getId().toString()).getFuserId();
		    CL_UserRole user=userRoleDao.getById(userId);
		    String onumber="";
		    JSONObject o=new JSONObject();
			String re="";
		    if(user!=null){
		        CL_Order ord=orderDao.getByIdAndCreator(fid, userId);
		        if(ord!=null){
		        	CL_UserVoucher userV=new CL_UserVoucher();
		        	userV=userVoucherDao.getByForderNumber(ord.getNumber(), userId);
		        	if(userV!=null){
		        		onumber=userV.getForderNumber();
		        	}else{
		        		CL_UserVoucher userV1=new CL_UserVoucher();
		        		userV1.setFcreator(userId);
		        		onumber=ord.getNumber();
		        		userV1.setForderNumber(onumber);
		        		userV1.setFcreatorTime(new Date());
		        		userV1.setFisover(0);
		        		userV1.setFpayorRecharge(2);
		        		userV1.setFtype(0);
		        		userV1.setFstatus(0);
		        		userV1.setFmoeny(ord.getFreight());
		        		userV1.setNumber(CacheUtilByCC.getOrderNumber("cl_user_voucher", "V", 8));
		        		userVoucherDao.save(userV1);
		        		userV=userV1;
		        	}	
		        	    re=PayUtil.UserOrderPay(onumber, user.getVmiUserPhone(), ord.getFreight().toString());
		        	    o=JSONObject.fromObject(re);
		        	    if(o.get("success").equals("true")){
				        	userV.setFdata(o.get("data").toString());
				        	userV.setFstatus(1);
				            userVoucherDao.update(userV);
				            ord.setStatus(2);
				            ord.setFpayMethod(0);
				            ord.setFpayTime(new Date());
				             orderDao.update(ord);
				             statementDao.saveStatement(ord.getId().toString(), ord.getNumber(), 1, ord.getFreight(), -1, ord.getCreator(), ord.getFpayMethod(), user.getVmiUserFid());
				        	 m.put("success", "true");
				    		 m.put("msg", "成功！");
				    		 return writeAjaxResponse(response, JSONUtil.getJson(m));
				        }else{
				          	 m.put("success", "false");
			    		     m.put("msg", o.get("msg").toString());
			    		     return writeAjaxResponse(response, JSONUtil.getJson(m));
				        }
		        }
		        
		    }
		    m.put("success", "false");
			m.put("msg", "该用户余额支付有误！");
			return writeAjaxResponse(response, JSONUtil.getJson(m));
		}*/
		 
		
		
	/*** 下单 运费到付支付 业务 */
	@RequestMapping("/pcWeb/pcWebPay/PayOrderInDao")
	public String PayOrderInDao(HttpServletRequest request,
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
		CL_UserRole user = userDao.getById(userId);
		if (user != null) {
			CL_Order ord = orderDao.getByIdAndCreator(fid, userId);
			if (ord != null) {
				ord.setStatus(2);
				ord.setFpayMethod(4);
				ord.setFpayTime(new Date());
				orderDao.update(ord);
				m.put("success", "true");
				m.put("msg", "成功！");
				return writeAjaxResponse(response, JSONUtil.getJson(m));
			} else {
				m.put("success", "false");
				m.put("msg", "修改订单失败！");
				return writeAjaxResponse(response, JSONUtil.getJson(m));
			}
		}
		m.put("success", "false");
		m.put("msg", "用户信息有误");
		return writeAjaxResponse(response, JSONUtil.getJson(m));
	}
		
		
		/***下单 用户 银行卡支付  业务*/
		/*@RequestMapping("/pcWeb/pcWebPay/UserPayByBank")
		@Transactional
		public String UserPayByBank(HttpServletRequest request,HttpServletResponse response,Integer fid,Integer bankId) throws IOException{
			Integer userId ;
			HashMap<String,Object> m =new HashMap<String,Object>();
			if(!ServerContext.getUseronline().containsKey(request.getSession().getId().toString())){
		    	m.put("success", "false");
				m.put("msg","未登录！");
				return writeAjaxResponse(response, JSONUtil.getJson(m));
		    }
		    System.out.println(ServerContext.getUseronline().get(request.getSession().getId().toString()).getFuserId());
		    userId =ServerContext.getUseronline().get(request.getSession().getId().toString()).getFuserId();
		    String onumber="";
		    JSONObject o=new JSONObject();
			String re="";
		        CL_Order ord=orderDao.getByIdAndCreator(fid, userId);
		        CL_Bank  bank=ibankDao.getByIdAndFcreator(fid, userId);
		        if(bank==null){
		        	m.put("success", "false");
		  			m.put("msg", "该银行不存在！");
		  			return writeAjaxResponse(response, JSONUtil.getJson(m));
		        }
		        if(ord!=null){
		        	CL_UserVoucher userV=new CL_UserVoucher();
		        	userV=userVoucherDao.getByForderNumber(ord.getNumber(), userId);
		        	if(userV!=null){
		        		onumber=userV.getForderNumber();
		        	}else{
		        		CL_UserVoucher userV1=new CL_UserVoucher();
		        		userV1.setFcreator(userId);
		        		onumber=ord.getNumber();
		        		userV1.setForderNumber(onumber);
		        		userV1.setFcreatorTime(new Date());
		        		userV1.setFisover(0);
		        		userV1.setFpayorRecharge(2);
		        		userV1.setFtype(3);
		        		userV1.setFstatus(0);
		        		userV1.setFbankName(bank.getFbankName());
		        		userV1.setFmoeny(ord.getFreight());
		        		userV1.setNumber(CacheUtilByCC.getOrderNumber("cl_user_voucher", "V", 8));
		        		userVoucherDao.save(userV1);
		        		userV=userV1;
		        	}	
		        	    re=PayUtil.BindBanKAndPay(onumber, ord.getCreatorPhone(), ord.getFreight().toString(),userV.getFbankName());
		        	    o=JSONObject.fromObject(re);
		        	    if(o.get("success").equals("true")){
				        	userV.setFdata(o.get("data").toString());
				            userVoucherDao.update(userV);
				            request.getSession().setAttribute("bankFid", o.get("data").toString());
				            request.getSession().setAttribute("payfid", userV.getFid());
				        	 m.put("success", "true");
				    		 m.put("msg", "成功！");
				    		 return writeAjaxResponse(response, JSONUtil.getJson(m));
				        }else{
				          	 m.put("success", "false");
			    		     m.put("msg", o.get("msg").toString());
			    		     return writeAjaxResponse(response, JSONUtil.getJson(m));
				        }
		        }
		        
		    m.put("success", "false");
			m.put("msg", "该用户余额支付有误！");
			return writeAjaxResponse(response, JSONUtil.getJson(m));
		}*/
		
		
		/***充值  支付 银行卡支付  业务 短信校验*//*
		   @RequestMapping("/pcWeb/bank/VerCodeByBanK")
			@Transactional
			public String VerCodeByBanK(HttpServletRequest request,HttpServletResponse response,String VerificationCode,String BankInfoFID,Integer payFid ) throws IOException{
				 Integer userId;
					HashMap<String,Object> m =new HashMap<String,Object>();
					if(!ServerContext.getUseronline().containsKey(request.getSession().getId().toString())){
				    	m.put("success", "false");
						m.put("msg","未登录！");
						return writeAjaxResponse(response, JSONUtil.getJson(m));
					//if(ServerContext.getUseronline().get(request.getSession().getId())!=null)
				    }
				    System.out.println(ServerContext.getUseronline().get(request.getSession().getId().toString()).getFuserId());
				    userId =ServerContext.getUseronline().get(request.getSession().getId().toString()).getFuserId();
				   CL_UserRole user=userRoleDao.getById(userId);
				    CL_UserVoucher userV=userVoucherDao.getByIdAndFcreator(payFid, userId);
				    if(userV==null){
					   	 m.put("success", "false");
						 m.put("msg","没有该订单支付信息！");
						 return writeAjaxResponse(response, JSONUtil.getJson(m));
				    }
				    String o="";
				    JSONObject ob=new JSONObject();
				    if(userV.getFstatus().equals(1)){
			    		 if(userV.getFpayorRecharge()==2){
			        		  CL_Order ord=orderDao.getByNumber(userV.getForderNumber());
			        		if(ord.getFreight().compareTo(userV.getFmoeny())!=0){
			        			  m.put("success", "false");
				    	  	      m.put("msg", "运费和支付金额不一样,支付金额已到余额请联系技术人员！");
				    	  	 	  return writeAjaxResponse(response, JSONUtil.getJson(m));
			        		  }
			        		  o=PayUtil.UserOrderPay(userV.getForderNumber(), userV.getVmiUserPhone(), userV.getFmoeny().toString());
				        	  ob=JSONObject.fromObject(o);
				        	  if(ob.get("success").equals("true")){
				        		 try{
					        		 ord.setStatus(2);
					        		 ord.setFpayTime(new Date());
					        		 orderDao.update(ord);
				        		 }catch(Exception e){
				        			 m.put("success", "false");
				    	  	     	 m.put("msg", "存在多条订单,数据有误。请联系客服,已经扣除余额");
				    	  	 		 return writeAjaxResponse(response, JSONUtil.getJson(m));
				        		 }
				        	 }else{
				        		 m.put("success", "false");
			    	  	     	 m.put("msg", "支付金额已到余额！");
			    	  	 		 return writeAjaxResponse(response, JSONUtil.getJson(m));
				        	 }
				        	 
				        		 m.put("success", "true");
					  	     	 m.put("msg", "支付成功！");
					  	 		 return writeAjaxResponse(response, JSONUtil.getJson(m));
			        	 }
			    		 m.put("success", "true");
			  	     	 m.put("msg", "充值、支付成功！");
			  	 		 return writeAjaxResponse(response, JSONUtil.getJson(m));
			    	}
				    o=PayUtil.VerificationCode(VerificationCode, BankInfoFID);
				    ob =JSONObject.fromObject(o);
				     if(ob.get("success").equals("true")){
				    	 userV.setFstatus(1);
			        	 userVoucherDao.update(userV);
			        	 if(userV.getFpayorRecharge()==2){
			        		  CL_Order ord=orderDao.getByNumber(userV.getForderNumber());
			        		  if(ord.getFreight().compareTo(userV.getFmoeny())!=0){
			        			  m.put("success", "false");
				    	  	      m.put("msg", "运费和支付金额不一样,支付金额已到余额请联系技术人员！");
				    	  	 	  return writeAjaxResponse(response, JSONUtil.getJson(m));
			        		  }
			        		  o=PayUtil.UserOrderPay(userV.getForderNumber(), userV.getVmiUserPhone(), userV.getFmoeny().toString());
				        	  ob=JSONObject.fromObject(o);
				        	  if(ob.get("success").equals("true")){
				        		 try{
					        		 ord.setStatus(2);
					        		 ord.setFpayTime(new Date());
					        		 ord.setFpayMethod(3);
					        		 statementDao.saveStatement(ord.getId().toString(), ord.getNumber(), 1, ord.getFreight(), -1, ord.getCreator(), ord.getFpayMethod(), user.getVmiUserFid());
					        		 orderDao.update(ord);
				        		 }catch(Exception e){
				        			 m.put("success", "false");
				    	  	     	 m.put("msg", "存在多条订单,数据有误。请联系客服,已经扣除余额");
				    	  	 		 return writeAjaxResponse(response, JSONUtil.getJson(m));
				        		 }
				        	 }else{
				        		 m.put("success", "false");
			    	  	     	 m.put("msg", "支付金额已到余额！");
			    	  	 		 return writeAjaxResponse(response, JSONUtil.getJson(m));
				        	 }
			        	 }
			  	     	 m.put("success", "true");
			  	     	 m.put("msg", ob.get("msg"));
			  	 		 return writeAjaxResponse(response, JSONUtil.getJson(m));
			        
				   }
				     m.put("success", "false");
		  	     	 m.put("msg", ob.get("msg"));
		  	 		 return writeAjaxResponse(response, JSONUtil.getJson(m));
			}
		 
}*/
	   //	/***导出明细、*/
//	@RequestMapping("/pcWeb/pcWebPay/exportExcel")
//	public String exportExcel(HttpServletRequest request,HttpServletResponse response) throws IOException{
//		Integer userId ;
//		HashMap<String,Object> m =new HashMap<String,Object>();
//		if(!ServerContext.getUseronline().containsKey(request.getSession().getId().toString())){
//	    	m.put("success", "false");
//			m.put("msg","未登录！");
//			return writeAjaxResponse(response, JSONUtil.getJson(m));
//		//if(ServerContext.getUseronline().get(request.getSession().getId())!=null)
//	    }
//	    System.out.println(ServerContext.getUseronline().get(request.getSession().getId().toString()).getFuserId());
//	    userId =ServerContext.getUseronline().get(request.getSession().getId().toString()).getFuserId();
//	  
//	}
	
	
//	/***银行卡绑定用户、*/
//	@RequestMapping("/pcWeb/pcWebPay/BingBankAndPay")
//	public String BingBankAndPay(HttpServletRequest request,HttpServletResponse response) throws IOException{
//		Integer userId ;
//		HashMap<String,Object> m =new HashMap<String,Object>();
//		if(!ServerContext.getUseronline().containsKey(request.getSession().getId().toString())){
//	    	m.put("success", "false");
//			m.put("msg","未登录！");
//			return writeAjaxResponse(response, JSONUtil.getJson(m));
//		//if(ServerContext.getUseronline().get(request.getSession().getId())!=null)
//	    }
//	    System.out.println(ServerContext.getUseronline().get(request.getSession().getId().toString()).getFuserId());
//	   userId =ServerContext.getUseronline().get(request.getSession().getId().toString()).getFuserId();
//	    CL_UserRole user=userRoleDao.getById(userId);
//	    if(user!=null){
//	    	PcWebPayUtil.UseBingBankNameList(user.getVmiUserPhone());
//	         String o=ServerContext.getReceive().toString();
//	         JSONObject ob=JSONObject.fromObject(o);
//	         request.getSession().setAttribute("bankList",ob.get("data"));//把需要指派的订单id保存在request中
//	     	 m.put("success", "true");
//	     	 m.put("data", ob.get("data"));
//	 		return writeAjaxResponse(response, JSONUtil.getJson(m));
//	    }
//	    m.put("success", "false");
//		m.put("msg", "该用户查询余额有误！");
//		return writeAjaxResponse(response, JSONUtil.getJson(m));
//	}
	
	/**
	 * 余额支付密码
	 * @param request
	 * @param response
	 * @param payPWD支付密码
	 * @param orderFid订单主键
	 * @param add 判断是否为追加
	 * @return
	 */
	@RequestMapping("/pcWeb/pcWebPay/balancePay")
	public String balancePay(HttpServletRequest request,HttpServletResponse response,String payPWD, Integer fid,Integer add){
		Integer userId,cus;
		HashMap<String, Object> m = new HashMap<String, Object>();
		HashMap<String, Object> param = new HashMap<String, Object>();
		userId = ServerContext.getUseronline().get(request.getSession().getId()).getFuserId();
		String pwd = userDao.getById(userId).getFpaypassword();
		CL_Order order = orderDao.getById(fid);
		if(add == null){//如果不是追加，则需要判断订单是否重复支付
			Date loadTime = order.getLoadedTime();
			if((loadTime.getTime()-new Date().getTime()) < 60000*60){
				return this.poClient(response, false, "距离装车时间已不足一小时，请重新下单");
			}
			List<CL_FinanceStatement> list = FinanceDao.getforderId(order.getNumber());
			if (list.size() > 0) {
				for (CL_FinanceStatement statement : list) {// 查询该订单所有明细，是否有已成功的，已成功则不可再支付
					if (statement.getFstatus() == 1) {
						m.put("success", "false");
						m.put("msg", "请勿重复支付！");
						return writeAjaxResponse(response, JSONUtil.getJson(m));
					}
				}
			}
		}
		
		if(payPWD.equals(pwd)){
			CL_UserRole user = userDao.getById(order.getCreator());
			BigDecimal balance = user.getFbalance();
			BigDecimal freight = order.getFreight();
			if(balance.compareTo(freight) < 0){
				m.put("data1", balance);//用户余额
				m.put("data2", freight.subtract(balance));//用户订单扣除余额后还需支付的金额
				m.put("data3", order.getNumber());//訂單編號
				m.put("success", "false3");
				m.put("msg", "余额不足！请选择其他支付方式！");
				return writeAjaxResponse(response, JSONUtil.getJson(m));
			}
			
			CL_FinanceStatement statement = this.sta(order, user, order.getFreight(), -1);
			if(add != null){
				String money = request.getParameter("fmoney");
//				String payNumber = "PP" + CacheUtil.getPayNumber(iaddtoDao);
				String payNumber = CacheUtilByCC.getOrderNumber("CL_Addto", "PP", 8);
				CL_Addto addto = new CL_Addto();
				addto.setFcreatTime(new Date());
				addto.setFstatus(0);
				addto.setNumber(payNumber);
				addto.setForderId(order.getId());
				addto.setFcreateor(userId);
				addto.setFcost(new BigDecimal(money));
				this.iaddtoDao.save(addto);
				statement.setFbusinessType(2);
				statement.setFrelatedId(addto.getFid().toString());
				statement.setForderId(order.getId().toString());
				statement.setFamount(addto.getFcost());
				statement.setFreight(addto.getFcost());
				statement.setFremark(order.getNumber()+"PC端订单追加");
			}
			FinanceDao.save(statement);
			OrderMsg orderMsg = new OrderMsg();
			orderMsg.setOrder(statement.getNumber());
			orderMsg.setPayState("1");
			orderMsg.setServiceProviderType("0");
			orderMsg.setServiceProvider("0");
			cus = FinanceDao.updateBusinessType(orderMsg,param);
			if(cus == 1){
				m.put("success", "true");
				m.put("msg", "支付成功");
			} else {
				m.put("success", "false2");
				m.put("msg", "支付失败");
			}
		} else {
			m.put("success", "false1");
			m.put("msg", "密码错误");
		}
		return writeAjaxResponse(response, JSONUtil.getJson(m));
	}
	
	/**
	 * 混合支付跳转接口
	 * @param request
	 * @param response
	 * @param orderId 订单主键
	 * @return
	 */
	@RequestMapping("/pcWeb/pcWebPay/mixPay")
	public String mixPay(HttpServletRequest request,HttpServletResponse response,Integer orderId){
		HashMap<String, Object> m = new HashMap<String, Object>();
		if(!ServerContext.getUseronline().containsKey(request.getSession().getId().toString())){
	    	m.put("success", "false");
			m.put("msg","未登录！");
			return writeAjaxResponse(response, JSONUtil.getJson(m));
	    }
		CL_Order order = orderDao.getById(orderId);
		CL_UserRole user = userDao.getById(order.getCreator());
		BigDecimal balance = user.getFbalance();
		BigDecimal freight = order.getFreight();
		request.setAttribute("orderId", orderId);
		request.setAttribute("data3", order.getNumber());//訂單編號
		request.setAttribute("data2", freight.subtract(balance));//還需支付的金額
		
		return MIXPAY_INFO;
	}
	
	/**
	 * 微信支付寶混合支付开始
	 * @param request
	 * @param response
	 * @param orderId
	 * @return
	 */
	@RequestMapping("/pcWeb/pcWebPay/QRmixPay")
	public String mixPayGo(HttpServletRequest request,
			HttpServletResponse response, Integer orderId, Integer type) {
		Integer userId;
		HashMap<String, Object> m = new HashMap<String, Object>();
		HashMap<String, Object> param = new HashMap<String, Object>();
		LinkedHashMap<String, Object> modellist = new LinkedHashMap<>();
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
		if (!ServerContext.getUseronline().containsKey(
				request.getSession().getId().toString())) {
			m.put("success", "false");
			m.put("msg", "未登录！");
			return writeAjaxResponse(response, JSONUtil.getJson(m));
		}
		System.out.println(ServerContext.getUseronline()
				.get(request.getSession().getId().toString()).getFuserId());
		userId = ServerContext.getUseronline()
				.get(request.getSession().getId().toString()).getFuserId();
		CL_Order order = orderDao.getById(orderId);
		Date loadTime = order.getLoadedTime();
		if((loadTime.getTime()-new Date().getTime()) < 60000*60){
			return this.poClient(response, false, "距离装车时间已不足一小时，请重新下单");
		}
		CL_UserRole user = userDao.getById(userId);
		BigDecimal payMoney = order.getFreight().subtract(user.getFbalance());// 订单扣除余额后还需支付的金额
		
		List<CL_FinanceStatement> list = FinanceDao.getforderId(order
				.getNumber());
		if (list.size() > 0) {
			for (CL_FinanceStatement statement : list) {// 查询该订单所有明细，是否有已成功的，已成功则不可再支付
				if (statement.getFstatus() == 1) {
					m.put("success", "false");
					m.put("msg", "请勿重复支付！");
					return writeAjaxResponse(response, JSONUtil.getJson(m));
				}
			}
		}
		
		if (type == 1) {// 支付宝
			
			modellist.put("orderNumber", order.getNumber());// 订单编号
			modellist.put("orderCreateTime", format.format(new Date()));// 订单时间
			modellist.put("payerId", MD5Util.getMD5String(userId + "ylhy")
					.subSequence(0, 30));// 付款人
			modellist.put("serviceProvider", "ALI");// 支付服务商
			modellist.put("serviceProviderType", "QR");// 服务商类型
			modellist.put("bankCardBindId", "");// 银行卡绑定ID,支付宝微信空字符串
			modellist.put("payAmount", payMoney.toString());// 支付金额
			modellist.put("description", "混合支付");// 商品说明
			modellist.put("notificationURL", "");// 通知URL
			
			param.put("fremark", "PC支付宝混合支付");
			param.put("ftype", -1);
			param.put("famount", payMoney);
			param.put("forderId", order.getNumber());
			param.put("frelatedId", order.getId());
			param.put("fpayType", 1);// 明细参数
			param.put("fcusid", userId);
			param.put("userDao", userDao);
			param.put("FinanceDao", FinanceDao);
			param.put("freight", order.getFreight());
			String msg = PayUtil.webRechargeByZFBWX(param, modellist);
			System.out.println("支付宝获取支付码：" + msg);
			if(msg == null){
				m.put("success", "false");
				m.put("msg", "支付炸了");
			}
			JSONObject jo = JSONObject.fromObject(msg);
			if ("true".equals(jo.get("success"))) {
				m.put("success", "true");
				m.put("data", jo.get("data"));
				m.put("data1", jo.get("number"));
			} else {
				m.put("success", "false");
				m.put("msg", PayContext.responeCode(Integer.parseInt(jo.get(
						"code").toString())));
			}
		} else if (type == 2) {// 微信
			
			modellist.put("orderNumber", order.getNumber());// 订单编号
			modellist.put("orderCreateTime", format.format(new Date()));// 订单时间
			modellist.put("payerId", MD5Util.getMD5String(userId + "ylhy")
					.subSequence(0, 30));// 付款人
			modellist.put("serviceProvider", "WX");// 支付服务商
			modellist.put("serviceProviderType", "QR");// 服务商类型
			modellist.put("bankCardBindId", "");// 银行卡绑定ID,支付宝微信空字符串
			modellist.put("payAmount", payMoney.toString());// 支付金额
			modellist.put("description", "混合支付");// 商品说明
			modellist.put("notificationURL", "");// 通知URL
			
			param.put("fremark", "PC微信混合支付");
			param.put("ftype", -1);
			param.put("famount", payMoney);
			param.put("forderId", order.getNumber());
			param.put("frelatedId", order.getId());
			param.put("fpayType", 1);// 明细参数
			param.put("fcusid", userId);
			param.put("userDao", userDao);
			param.put("FinanceDao", FinanceDao);
			param.put("freight", order.getFreight());
			String msg = PayUtil.webRechargeByZFBWX(param, modellist);
			System.out.println("微信获取支付码：" + msg);
			if(msg == null){
				m.put("success", "false");
				m.put("msg", "支付炸了");
			}
			JSONObject jo = JSONObject.fromObject(msg);
			if ("true".equals(jo.get("success"))) {
				m.put("success", "true");
				m.put("data", jo.get("data"));
				m.put("data1", jo.get("number"));
			} else {
				m.put("success", "false");
				m.put("msg", PayContext.responeCode(Integer.parseInt(jo.get(
						"code").toString())));
			}
		}
		return writeAjaxResponse(response, JSONUtil.getJson(m));
	}
	
	/**
	 * 銀行卡混合支付
	 * @param request
	 * @param response
	 * @param fid 銀行卡key
	 * @param orderId 訂單的key
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/pcWeb/pcWebPay/BankMixPay")
	public String BankMixPay(HttpServletRequest request,
			HttpServletResponse response, Integer fid, Integer orderId)
			throws IOException {
		HashMap<String, Object> m = new HashMap<String, Object>();
		HashMap<String, Object> param = new HashMap<String, Object>();
		Integer userId;
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
		if (!ServerContext.getUseronline().containsKey(
				request.getSession().getId().toString())) {
			m.put("success", "false");
			m.put("msg", "未登录！");
			return writeAjaxResponse(response, JSONUtil.getJson(m));
		}
		System.out.println(ServerContext.getUseronline()
				.get(request.getSession().getId().toString()).getFuserId());
		userId = ServerContext.getUseronline()
				.get(request.getSession().getId().toString()).getFuserId();
		CL_Bank bank = ibankDao.getByIdAndFcreator(fid, userId);
		CL_Order order = orderDao.getById(orderId);
		Date loadTime = order.getLoadedTime();
		if((loadTime.getTime()-new Date().getTime()) < 60000*60){
			return this.poClient(response, false, "距离装车时间已不足一小时，请重新下单");
		}
		BigDecimal payMoney = order.getFreight().subtract(
				userDao.getById(userId).getFbalance());// 扣除餘額后還需扣除的費用
		// 先取验证码
		LinkedHashMap<String, Object> model = new LinkedHashMap<>();
		List<CL_FinanceStatement> list = FinanceDao.getforderId(order
				.getNumber());
		if (list.size() > 0) {
			for (CL_FinanceStatement statement : list) {// 查询该订单所有明细，是否有已成功的，已成功则不可再支付
				if (statement.getFstatus() == 1) {
					m.put("success", "false");
					m.put("msg", "请勿重复支付！");
					return writeAjaxResponse(response, JSONUtil.getJson(m));
				}
			}
		}
		model.put("orderNumber", order.getNumber());// 订单编号
		param.put("forderId", order.getNumber());
		param.put("frelatedId", orderId);
		model.put("orderCreateTime", format.format(new Date()));// 订单时间
		model.put("payerId",
				MD5Util.getMD5String(userId + "ylhy").subSequence(0, 30));// 付款人
		model.put("serviceProvider", "BANK");// 支付服务商
		model.put("serviceProviderType", "PAB");// 服务商类型
		model.put("bankCardBindId", bank.getNumber());// 银行卡绑定ID
		model.put("payAmount", payMoney.toString());// 支付金额
		param.put("fremark", "PC银行卡支付");
		param.put("famount", payMoney);
		model.put("description", "");// 商品说明
		model.put("notificationURL", "");// 通知URL
		param.put("fpayType", 3);// 明细参数
		param.put("fcusid", userId);
		param.put("userDao", userDao);
		param.put("FinanceDao", FinanceDao);
		param.put("ftype", -1);
		param.put("freight", order.getFreight());
		String Verification = PayUtil.webRechargeCode(param, model);
		System.out.println(Verification);
		if (Verification == null) {
			m.put("success", "false");
			m.put("msg", "支付炸了");
		}
		JSONObject ver = JSONObject.fromObject(Verification);
		if ("true".equals(ver.get("success"))) {
			m.put("success", "true");
			m.put("data", ver.get("data").toString());
			m.put("data1", ver.get("number"));
			return writeAjaxResponse(response, JSONUtil.getJson(m));
		} else {
			int code = Integer.parseInt(ver.get("code").toString());
			m.put("success", "false");
			m.put("msg", PayContext.responeCode(code));
			return writeAjaxResponse(response, JSONUtil.getJson(m));
		}
	}
	
	/**
	 * 生成明细公共方法
	 * @param order运费的订单
	 * @param user用户
	 * @param fee费用
	 * @param ftype收支情况
	 * @return
	 */
	public CL_FinanceStatement sta(CL_Order order,CL_UserRole user,BigDecimal fee,int ftype){
		CL_FinanceStatement statement = new CL_FinanceStatement();
		String number = CacheUtilByCC.getOrderNumber("cl_finance_statement", "L", 8);
		statement.setNumber(number);
		statement.setFrelatedId(order.getId().toString());
		statement.setForderId(order.getNumber());
		statement.setFbusinessType(1);//订单支付
		statement.setFamount(fee);
		statement.setFtype(ftype);
		statement.setFuserroleId(user.getId());
		statement.setFuserid(user.getVmiUserFid());
		statement.setFpayType(0);
		statement.setFbalance(BigDecimal.ZERO);//先设0
		statement.setFcreateTime(new Date());
		statement.setFremark(order.getNumber()+"PC端订单支付");
		statement.setFreight(order.getFreight());
		return statement;
	}
	
	
}
