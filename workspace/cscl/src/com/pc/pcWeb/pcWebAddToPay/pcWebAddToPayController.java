package com.pc.pcWeb.pcWebAddToPay;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.pc.controller.BaseController;
import com.pc.dao.UserRole.IUserRoleDao;
import com.pc.dao.UserRole.impl.UserRoleDao;
import com.pc.dao.addto.IaddtoDao;
import com.pc.dao.bank.IbankDao;
import com.pc.dao.couponsDetail.ICouponsDetailDao;
import com.pc.dao.financeStatement.IFinanceStatementDao;
import com.pc.dao.order.IorderDao;
import com.pc.dao.orderDetail.IorderDetailDao;
import com.pc.dao.protocol.IprotocolDao;
import com.pc.dao.select.impl.UtilOptionDao;
import com.pc.model.CL_Addto;
import com.pc.model.CL_Bank;
import com.pc.model.CL_FinanceStatement;
import com.pc.model.CL_Order;
import com.pc.model.CL_UserRole;
import com.pc.util.CacheUtilByCC;
import com.pc.util.JSONUtil;
import com.pc.util.MD5Util;
import com.pc.util.ServerContext;
import com.pc.util.pay.OrderMsg;
import com.pc.util.pay.PayContext;
import com.pc.util.pay.PayUtil;
@Controller
public class pcWebAddToPayController extends BaseController{
	
	@Resource
	private UtilOptionDao optionDao;
	@Resource
	private IUserRoleDao iuserRoleDao;
	@Resource
	private IorderDao orderDao;
	@Resource
	private IaddtoDao iaddtoDao;
	@Resource
	private IprotocolDao  protocolDao;
	@Resource
	private IorderDetailDao orderDetailDao;
	@Resource
	private IFinanceStatementDao FinanceDao;
	@Resource
	private UserRoleDao userDao;
	@Resource
	private ICouponsDetailDao couponsDetailDao;
	@Resource
	private IbankDao ibankDao;
	
	protected static final String ADDTO_PAY = "/pages/pcWeb/payment/paymentAdd.jsp";


	//根据支付单号生成  支付宝 或者微信扫一扫二维码字符串
	/*@RequestMapping("/pcWeb/pcWebPay/newRechargeAddTo")
	public String newRechargeAddTo(HttpServletRequest request,HttpServletResponse response,Integer type) throws IOException{
	     //===支付宝签名
		Integer userId,orderId;
		String fpayNumber;
	    BigDecimal fcost=new BigDecimal(0);
		HashMap<String,Object> m =new HashMap<String,Object>();
		if(!ServerContext.getUseronline().containsKey(request.getSession().getId().toString())){
	    	m.put("success", "false");
			m.put("msg","未登录！");
			return writeAjaxResponse(response, JSONUtil.getJson(m));
		//if(ServerContext.getUseronline().get(request.getSession().getId())!=null)
	    }
		userId =ServerContext.getUseronline().get(request.getSession().getId().toString()).getFuserId();
		CL_UserRole user=iuserRoleDao.getById(userId);
		 if(request.getParameter("fpayNumber")==null || "".equals(request.getParameter("fpayNumber"))){
			m.put("success", "false");
			m.put("msg","请输入联系电话！");
			return writeAjaxResponse(response, JSONUtil.getJson(m));
		 }else{
			fpayNumber = request.getParameter("fpayNumber");
			
		 }
	      if(request.getParameter("fcost")==null || "".equals(request.getParameter("fcost"))){
			m.put("success", "false");
			m.put("msg","请输入联系电话！");
			return writeAjaxResponse(response, JSONUtil.getJson(m));
		 }else{
			 fcost =new BigDecimal(request.getParameter("fcost").toString());
			
		 } 
	      if(request.getParameter("orderId")==null||"".equals(request.getParameter("orderId"))){
				m.put("success", "false");
				m.put("msg","请先选择订单");
				return  writeAjaxResponse(response, JSONUtil.getJson(m));
			}
			else{
				orderId=Integer.parseInt(request.getParameter("orderId"));
			}
			List<CL_Addto> adds=iaddtoDao.getByNumber(fpayNumber);
			if(adds.size()>1){
				m.put("success", "false");
				m.put("msg","订单有误！");
				return  writeAjaxResponse(response, JSONUtil.getJson(m));
			}
			if(adds.size()==1&&adds.get(0).getFstatus()==1){
        		m.put("success", "false");
				m.put("msg","生成失败该订单已经支付过");
				return  writeAjaxResponse(response, JSONUtil.getJson(m));
        	}
			String	o=PayUtil.newRecharge(type,fpayNumber,user.getVmiUserPhone() ,fcost.toString());
	        JSONObject ad =JSONObject.fromObject(o);
	        if(ad.get("success").equals("true")){
	        	if(adds.size()==1&&adds.get(0).getFstatus()==0){
	        		adds.get(0).setFremark(ad.get("data").toString());
	        		adds.get(0).setFpayMethod(type);
	        		adds.get(0).setForderId(orderId);
	        		adds.get(0).setFcost(fcost);
	        		this.iaddtoDao.update(adds.get(0));
	        	    m.put("success", "true");
				    m.put("data",ad.get("data"));
					return writeAjaxResponse(response, JSONUtil.getJson(m));
	        	}else {
		        	CL_Addto add1=new CL_Addto();
		        	add1.setFcreatTime(new Date());
					add1.setFstatus(0);
					add1.setFpayNumber(fpayNumber);
					add1.setForderId(orderId);
					add1.setFcost(fcost);
					add1.setFcreateor(userId);
					add1.setFpayMethod(type);
				    m.put("success", "true");
				    m.put("data",ad.get("data"));
				    add1.setFremark(ad.get("data").toString());
				    this.iaddtoDao.save(add1);
					return writeAjaxResponse(response, JSONUtil.getJson(m));
	        	}
	        }
		 m.put("success", "false");
		 m.put("msg","生成失败！");
		return writeAjaxResponse(response, JSONUtil.getJson(m));
	}*/
	/*** 追加费用**//*
	@RequestMapping("/pcWeb/addPay/pcWebaddPay")
	public String pcWebaddPay(HttpServletRequest request,HttpServletResponse response) throws IOException{
		Integer orderId = null ,payMethod=null,userId=null;
		BigDecimal  fcost;
		String   payNumber=null, fremark="" ,BankName="" ,o="";
		 JSONObject ob=new JSONObject();
		 CL_Addto add1=new CL_Addto();
		HashMap<String,Object> map =new HashMap<String,Object>();
		if(request.getParameter("orderId")==null||"".equals(request.getParameter("orderId"))){
			map.put("success", "false");
			map.put("msg","请先选择订单");
			return  writeAjaxResponse(response, JSONUtil.getJson(map));
		}
		else{
			orderId=Integer.parseInt(request.getParameter("orderId"));
		}
		if(!ServerContext.getUseronline().containsKey(request.getSession().getId().toString())){
			map.put("success", "false");
			map.put("msg","未登录！");
			return writeAjaxResponse(response, JSONUtil.getJson(map));
		//if(ServerContext.getUseronline().get(request.getSession().getId())!=null)
	    }
		userId =ServerContext.getUseronline().get(request.getSession().getId().toString()).getFuserId();
		CL_UserRole user=iuserRoleDao.getById(userId);
		
		if(request.getParameter("payMethod")==null || "".equals(request.getParameter("payMethod"))){
			map.put("success", "false");
			map.put("msg","支付方式有问题");
			return  writeAjaxResponse(response, JSONUtil.getJson(map));
		}else{
			payMethod=Integer.valueOf(request.getParameter("payMethod").toString());
		}
		if(request.getParameter("fcost")==null || "".equals(request.getParameter("fcost"))){
			map.put("success", "false");
			map.put("msg","请输入金额");
			return  writeAjaxResponse(response, JSONUtil.getJson(map));
		}else{
			fcost=BigDecimal.valueOf(Double.parseDouble(request.getParameter("fcost").toString()));
		}
		if(request.getParameter("payNumber")==null || "".equals(request.getParameter("payNumber"))){
			map.put("success", "false");
			map.put("msg","支付方式有问题");
			return  writeAjaxResponse(response, JSONUtil.getJson(map));
		}else{
			payNumber=request.getParameter("payNumber").toString();
		}
		List<CL_Addto> adds=iaddtoDao.getByNumber(payNumber);
		if(adds.size()>1){
			map.put("success", "false");
			map.put("msg","订单号重复,请重新操作!");
			return  writeAjaxResponse(response, JSONUtil.getJson(map));
		}
		if(adds.size()==1){
			if(adds.get(0).getFstatus()==1){
				map.put("success", "false");
				map.put("msg","订单号重复,请重新操作!");
				return  writeAjaxResponse(response, JSONUtil.getJson(map));	
			}
		}
	
		if(payMethod!=5){
			   //校验第三方支付充值操作是否成功 ，余额支付不需要校验  BY CC  start
			   if(payMethod==1||payMethod==2)
			   {
					if(payMethod==1){
				       BankName=PayUtil.PAY_ALIPAY_QR;
				    } else if (payMethod==2){
				       BankName=PayUtil.PAY_WECHAT_QR;
				    } 
					o=PayUtil.CheckPayStatus(payNumber, user.getVmiUserPhone().toString(), BankName) ;
					if(o.equals("")||o==null){
			    		 map.put("success", "false");
			  	     	 map.put("msg", ob.get("msg"));
			  	 		 return writeAjaxResponse(response, JSONUtil.getJson(map));
			    	}
			        ob=JSONObject.fromObject(o);
			        if(ob.get("success").equals("true")){
			        	if(adds.size()==1&&adds.get(0).getFstatus()==0){
			        	   add1=adds.get(0);
			        	}else{
							add1.setFcreatTime(new Date());
							add1.setFstatus(0);
							add1.setFpayNumber(payNumber);
							add1.setForderId(orderId); 
							add1.setFcost(fcost);
							add1.setFcreateor(userId);
							add1.setFpayMethod(payMethod);
							add1.setFremark(fremark);
							this.iaddtoDao.save(add1);
			        	}
			        }else{
			        	 map.put("success", "false");
			  	     	 map.put("msg", ob.get("msg"));
			  	 		 return writeAjaxResponse(response, JSONUtil.getJson(map));
			        }
			   }
			   o=PayUtil.UserOrderPay(payNumber, user.getVmiUserPhone(), fcost.toString());
	        	  ob=JSONObject.fromObject(o);
	        	   if(ob.get("success").equals("true")){
	        		   if(payMethod==1&&payMethod==2){
			        	 add1.setFstatus(1);
						this.iaddtoDao.update(add1);
			          }else {
			        	  if(adds.size()==1&&adds.get(0).getFstatus()==0){
				        	   add1=adds.get(0);
				        	   add1.setFstatus(1);
				        	   this.iaddtoDao.update(add1);
			        	  }else{
			        	    add1.setFcreatTime(new Date());
							add1.setFstatus(1);
							add1.setFpayNumber(payNumber);
							add1.setForderId(orderId); 
							add1.setFcost(fcost);
							add1.setFcreateor(userId);
							add1.setFpayMethod(payMethod);
							add1.setFremark(fremark);
							this.iaddtoDao.save(add1);
			        	  }
			          }
	        	 }
 				 else{
					  map.put("success", "false");
					  map.put("msg","该订单已经付款");
					  return  writeAjaxResponse(response, JSONUtil.getJson(map));
			   }
			}
				
		*//**添加明细 Start*//*
		String userid="";
		if(user!=null)
		{
			userid=user.getVmiUserFid();
		}
		int orderid=add1.getForderId();
		String ordernum="";
		CL_Order order=orderDao.getById(orderid);
		if(order!=null)
		{
			ordernum=order.getNumber();
		}
		statementDao.saveStatement(add1.getFid().toString(), ordernum, 2, add1.getFcost(), -1, add1.getFcreateor(), add1.getFpayMethod(), userid);
		*//**添加明细 End*//*
		*//*** 追加费用计算司机运费 Start *//*
		List<CL_Addto> addto=iaddtoDao.getByOrderId(orderid);//查询当前所有支付成功的追加费用
		BigDecimal AllCost=order.getFreight();//初始费用取订单费用，总费=订单运费+所有追加运费  BY CC 2016-05-18
		if(addto.size()>0){
			for(CL_Addto ad:addto){
	    		AllCost=AllCost.add(ad.getFcost());
	    	}
		}
		//AllCost=AllCost.add(order.getFreight()).setScale(1,BigDecimal.ROUND_HALF_UP);//所有追加费用加上订单原始运费
		//根据总价格获取管理费比例   BY CC 2016-05-18
		BigDecimal discount=CalcTotalField.calDriverFee(AllCost.setScale(1,BigDecimal.ROUND_HALF_UP));
		//循环修改追加费用司机费用  BY CC 2016-05-18
		for(CL_Addto ad:addto){
			iaddtoDao.updateDriverfee(ad.getFid(), ad.getFcost().multiply(discount).setScale(1,BigDecimal.ROUND_HALF_UP));
    	}
//		orderDao.updateDriverFeeById(orderId, order.getFreight().multiply(discount).setScale(1,BigDecimal.ROUND_HALF_UP));//更新订单表中司机的总运费
		*//*** 追加费用计算司机运费 End *//*
		map.put("success", "true");
		map.put("msg","支付系统成功");
		return writeAjaxResponse(response, JSONUtil.getJson(map));
	}*/
	
	/**
	 * 跳转到追加页面
	 * @param request
	 * @param response
	 * @param orderId订单ID
	 * @return
	 */
	@RequestMapping("/pcWeb/addPay/pcWebaddPay")
	public String addto(HttpServletRequest request,HttpServletResponse response,Integer orderId){
		HashMap<String, Object> m = new HashMap<String, Object>();
		if (!ServerContext.getUseronline().containsKey(
				request.getSession().getId().toString())) {
			m.put("success", "false");
			m.put("msg", "未登录！");
			return writeAjaxResponse(response, JSONUtil.getJson(m));
		}
		/*CL_Order order = orderDao.getById(orderId);
		//查询追加成功与否----需求待确认
		List<CL_FinanceStatement> list = FinanceDao.getByOrderIdAndBusBusinessType(order.getNumber(), 2);
		if(list.size() > 0){//如果有支付中的追加明细，说明还未回调，许去支付系统查询
			for (CL_FinanceStatement statement : list) {
				LinkedHashMap<String, Object> modelLinke = new LinkedHashMap<String, Object>();
				HashMap<String, Object> param = new HashMap<String, Object>();
				modelLinke.put("orderNumber", statement.getNumber());//业务系统的订单号
				switch (statement.getFpayType()) {
				case 1://支付宝
					modelLinke.put("serviceProvider", "ALI");//支付方式
					modelLinke.put("serviceProviderType", "QR");//支付类型
					break;
				case 2://微信
					modelLinke.put("serviceProvider", "WX");//支付方式
					modelLinke.put("serviceProviderType", "QR");//支付类型
					break;
				case 3://银联
					modelLinke.put("serviceProvider", "BANK");//支付方式
					modelLinke.put("serviceProviderType", "PAB");//支付类型
					break;
				default:
					break;
				}
				modelLinke.put("notificationURL", "");//通知URL
				String msg = PayUtil.webAddPayQuery(param, modelLinke);
				System.out.println("追加查询："+msg);
				JSONObject jo = JSONObject.fromObject(msg);
				if("true".equals(jo.get("success"))){
					m.put("success", "true");
					m.put("msg", "上次订单的追加已成功，是否要继续追加？");
					return writeAjaxResponse(response, JSONUtil.getJson(m));
				}
				
			}
		}*/
		
		int userId = ServerContext.getUseronline().get(request.getSession().getId()).getFuserId();
		request.setAttribute("balance", userDao.getById(userId).getFbalance());
		request.setAttribute("orderId", orderId);
		return ADDTO_PAY;
	}
	
	
	
	/**
	 * 支付宝 和微信生成标签
	 * @param request
	 * @param response
	 * @param type 類型1支付寶，2微信
	 * @param money 充值金額
	 * @param orderId 要追加订单的fid
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/pcWeb/addPay/pcWebAddVoucher")
	public String pcWebVoucher3(HttpServletRequest request,
			HttpServletResponse response, Integer type, BigDecimal money,
			Integer orderId) throws IOException {
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
		CL_UserRole user = userDao.getById(userId);
		CL_Order order = orderDao.getById(orderId);
//		String payNumber = "PP" + CacheUtil.getPayNumber(iaddtoDao);
		String payNumber = CacheUtilByCC.getOrderNumber("CL_Addto", "PP", 8);
		CL_Addto addto = new CL_Addto();
		addto.setFcreatTime(new Date());
		addto.setFstatus(0);
		addto.setNumber(payNumber);
		addto.setForderId(order.getId());
		addto.setFcreateor(userId);
		addto.setFcost(money);
		addto.setFdriverfee(order.getFdriverfee());
		this.iaddtoDao.save(addto);
		CL_FinanceStatement statement = this.sta(order, user, money, -1);
		statement.setFbusinessType(2);
		statement.setFrelatedId(addto.getFid().toString());
		statement.setForderId(order.getNumber());
		statement.setFamount(addto.getFcost());
		statement.setFreight(addto.getFcost());
		statement.setFremark(order.getNumber()+"PC端订单追加");
		
		if (type == 1) {// 支付宝

			statement.setFpayType(1);
			FinanceDao.save(statement);
			modellist.put("orderNumber", order.getNumber());// 订单编号
			modellist.put("orderCreateTime", format.format(new Date()));// 订单时间
			modellist.put("payerId", MD5Util.getMD5String(userId + "ylhy")
					.subSequence(0, 30));// 付款人
			modellist.put("serviceProvider", "ALI");// 支付服务商
			modellist.put("serviceProviderType", "QR");// 服务商类型
			modellist.put("bankCardBindId", "");// 银行卡绑定ID,支付宝微信空字符串
			modellist.put("payAmount", money.toString());// 支付金额
			modellist.put("description", "支付");// 商品说明
			modellist.put("notificationURL", "");// 通知URL
			
			param.put("fremark", "支付宝支付");
			param.put("ftype", -1);
			param.put("famount", money);
			param.put("forderId", order.getNumber());
			param.put("frelatedId", addto.getFid());
			param.put("fpayType", 1);// 明细参数
			param.put("fcusid", userId);
			param.put("userDao", userDao);
			param.put("FinanceDao", FinanceDao);
			param.put("freight", money);
			
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

			statement.setFpayType(2);
			FinanceDao.save(statement);
			modellist.put("orderNumber", order.getNumber());// 订单编号
			modellist.put("orderCreateTime", format.format(new Date()));// 订单时间
			modellist.put("payerId", MD5Util.getMD5String(userId + "ylhy")
					.subSequence(0, 30));// 付款人
			modellist.put("serviceProvider", "WX");// 支付服务商
			modellist.put("serviceProviderType", "QR");// 服务商类型
			modellist.put("bankCardBindId", "");// 银行卡绑定ID,支付宝微信空字符串
			modellist.put("payAmount", money.toString());// 支付金额
			modellist.put("description", "支付");// 商品说明
			modellist.put("notificationURL", "");// 通知URL
			
			param.put("fremark", "微信支付");
			param.put("ftype", -1);
			param.put("famount", money);
			param.put("forderId", order.getNumber());
			param.put("frelatedId", addto.getFid());
			param.put("fpayType", 1);// 明细参数
			param.put("fcusid", userId);
			param.put("userDao", userDao);
			param.put("FinanceDao", FinanceDao);
			param.put("freight", money);
			
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
	@RequestMapping("/pcWeb/pcWebPay/pcWebAddPayPolling")
	public String polling(HttpServletRequest request,HttpServletResponse response,String number){
		HashMap<String, Object> m = new HashMap<String, Object>();
		int status = FinanceDao.getByNumber(number).get(0).getFstatus();//轮询支付状态
		if(status == 1){
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
	
	/**
	 * 银行卡追加
	 * @param request
	 * @param response
	 * @param fid 银行卡id
	 * @param fmoney 追加费用
	 * @param orderFid 订单id
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/pcWeb/pcWebAddPay/BangDingPay")
	public String BangDingPay(HttpServletRequest request,
			HttpServletResponse response, Integer fid, BigDecimal fmoney,
			Integer orderFid) throws IOException {
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
		CL_Order order = orderDao.getById(orderFid);
		CL_Bank bank = ibankDao.getByIdAndFcreator(fid, userId);
		// 先取验证码
		LinkedHashMap<String, Object> model = new LinkedHashMap<>();
		model.put("orderNumber", order.getNumber());// 订单编号
		param.put("forderId", order.getNumber());
		param.put("frelatedId", orderFid);
		model.put("orderCreateTime", format.format(new Date()));// 订单时间
		model.put("payerId",
				MD5Util.getMD5String(userId + "ylhy").subSequence(0, 30));// 付款人
		model.put("serviceProvider", "BANK");// 支付服务商
		model.put("serviceProviderType", "PAB");// 服务商类型
		model.put("bankCardBindId", bank.getNumber());// 银行卡绑定ID
		model.put("payAmount", order.getFreight().toString());
		param.put("fremark", "银行卡支付");
		param.put("famount", order.getFreight());
		model.put("description", "");// 商品说明
		model.put("notificationURL", "");// 通知URL
		param.put("fpayType", 3);// 明细参数
		param.put("fcusid", userId);
		param.put("userDao", userDao);
		param.put("FinanceDao", FinanceDao);
		param.put("ftype", 1);
		String Verification = PayUtil.webAddRechargeCode(param, model);
		System.out.println(Verification);
		JSONObject ver = JSONObject.fromObject(Verification);
		int code = Integer.parseInt(ver.get("code").toString());
		if ("true".equals(ver.get("success"))) {
			m.put("success", "true");
			m.put("data", ver.get("data").toString());
			m.put("data1", ver.get("number"));
			return writeAjaxResponse(response, JSONUtil.getJson(m));
		} else {
			m.put("success", "false");
			m.put("msg", PayContext.responeCode(code));
			return writeAjaxResponse(response, JSONUtil.getJson(m));
		}
	}
	
	
	/**
	 * 追加校验验证码
	 * @param request
	 * @param response
	 * @param VerificationCode验证码
	 * @param stateFid明细FID
	 * @param fid银行FID
	 * @return
	 */
	@RequestMapping("/pcWeb/pcWebAddPay/AddVerificationCodeGo")
	public String VerificationCodeGo(HttpServletRequest request,HttpServletResponse response,String VerificationCode,String stateFid,Integer fid,String verifyCheckCode){
		HashMap<String,Object> m =new HashMap<String,Object>();
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
		String msg = PayUtil.webAddRecharge(modellist);
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
	
	/**
	 * 余额支付密码
	 * @param request
	 * @param response
	 * @param payPWD支付密码
	 * @param fid订单主键
	 * @return
	 */
	@RequestMapping("/pcWeb/pcWebAddPay/balacePay")
	public String balancePay(HttpServletRequest request,HttpServletResponse response,BigDecimal money,String payPWD, Integer fid){
		Integer userId,cus;
		HashMap<String, Object> m = new HashMap<String, Object>();
		HashMap<String, Object> param = new HashMap<String, Object>();
		userId = ServerContext.getUseronline().get(request.getSession().getId()).getFuserId();
		String pwd = userDao.getById(userId).getFpaypassword();
		if(payPWD.equals(pwd)){
			CL_Order order = orderDao.getById(fid);
			CL_UserRole user = userDao.getById(order.getCreator());
			BigDecimal balance = user.getFbalance();
			if(balance.compareTo(money) < 0){
				m.put("success", "false3");
				m.put("msg", "余额不足！");
				return writeAjaxResponse(response, JSONUtil.getJson(m));
			}
			
			CL_FinanceStatement statement = this.sta(order, user, order.getFreight(), -1);
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
		statement.setFbusinessType(2);//追加
		statement.setFamount(fee);
		statement.setFtype(ftype);
		statement.setFuserroleId(user.getId());
		statement.setFuserid(user.getVmiUserFid());
		statement.setFpayType(0);
		statement.setFbalance(BigDecimal.ZERO);
		statement.setFcreateTime(new Date());
		statement.setFreight(order.getFreight());
		return statement;
	}

}


