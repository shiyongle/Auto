package com.pc.appInterface.orderDetail;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.w3c.dom.UserDataHandler;

import com.pc.appInterface.api.DongjingClient;
import com.pc.controller.BaseController;
import com.pc.dao.Car.impl.CarDao;
import com.pc.dao.UserRole.IUserRoleDao;
import com.pc.dao.addto.IaddtoDao;
import com.pc.dao.coupons.impl.CouponsDaoImpl;
import com.pc.dao.couponsDetail.impl.CouponsDetailDaoImpl;
import com.pc.dao.financeStatement.IFinanceStatementDao;
import com.pc.dao.financeStatement.impl.FinanceStatementDaoImpl;
import com.pc.dao.message.impl.MessageDao;
import com.pc.dao.order.impl.OrderDao;
import com.pc.dao.orderCarDetail.impl.OrderCarDetailDao;
import com.pc.dao.orderDetail.impl.OrderDetailDao;
import com.pc.dao.rating.IratingDao;
import com.pc.dao.rule.IRuleDao;
import com.pc.dao.select.IUtilOptionDao;
import com.pc.model.CL_Addto;
import com.pc.model.CL_Car;
import com.pc.model.CL_Coupons;
import com.pc.model.CL_CouponsDetail;
import com.pc.model.CL_FinanceStatement;
import com.pc.model.CL_Identification;
import com.pc.model.CL_Message;
import com.pc.model.CL_Order;
import com.pc.model.CL_OrderCarDetail;
import com.pc.model.CL_OrderDetail;
import com.pc.model.CL_Rating;
import com.pc.model.CL_Rule;
import com.pc.model.CL_UserRole;
import com.pc.model.Util_Option;
import com.pc.model.Util_UserOnline;
import com.pc.query.order.OrderQuery;
import com.pc.util.Base64;
import com.pc.util.CacheUtilByCC;
import com.pc.util.CalcTotalField;
import com.pc.util.JSONUtil;
import com.pc.util.MD5Util;
import com.pc.util.ServerContext;
import com.pc.util.String_Custom;
import com.pc.util.pay.OrderMsg;
import com.pc.util.pay.PayUtil;

@Controller
public class AppOrderDetailControll extends BaseController {
	@Resource
	private IRuleDao ruleDao;
	@Resource
	private OrderDao orderDao;
	@Resource
	private OrderDetailDao orderDetailDao;
	@Resource
	private OrderCarDetailDao orderCarDetailDao;
	@Resource
	private CarDao carDao;
	@Resource
	private IUserRoleDao iuserRoleDao;
	@Resource
	private IUtilOptionDao optionDao;
	@Resource
	private IratingDao iratingDao;
	@Resource
	private CouponsDetailDaoImpl couponsDetailDao;
	@Resource
	private MessageDao messageDao;
	@Resource
	private CouponsDaoImpl couponsDao;
	@Resource
	private IaddtoDao iaddtoDao;
	@Resource
	private FinanceStatementDaoImpl statementDao;
	@Resource
	private IFinanceStatementDao financeStatementDao;
	@Resource
	private IUserRoleDao userRoleDao;

	private String payUrl = ServerContext.getBaseurl()
			+ "/Action_Pay/DJPay/PaySys/SysUseMoney?&XDEBUG_SESSION_START=17402";

	public OrderQuery getOrderQuery() {
		return orderQuery;
	}

	public void setOrderQuery(OrderQuery orderQuery) {
		this.orderQuery = orderQuery;
	}

	private OrderQuery orderQuery;

	/*** 查询货主及司机订单详情信息* */
	@RequestMapping("/app/orderDetail/loadHuoOrderDetail")
	public String loadHuoOrderDetail(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Integer orderId, userType;
		BigDecimal AllCost = new BigDecimal(0);// 货主补交运费合计 BY CC
		BigDecimal AllCostD = new BigDecimal(0);// 司机补交运费合计 BY CC
		HashMap<String, Object> map = new HashMap<String, Object>();
		if (request.getParameter("orderId") == null || "".equals(request.getParameter("orderId"))) {
			map.put("success", "false");
			map.put("msg", "订单数据有误,请联系客服");
			return writeAjaxResponse(response, JSONUtil.getJson(map));
		} else {
			orderId = Integer.parseInt(request.getParameter("orderId"));
		}
		if (request.getParameter("userType") == null || "".equals(request.getParameter("userType"))) {
			map.put("success", "false");
			map.put("msg", "无法判断用户是查询详情类型,请联系客服");
			return writeAjaxResponse(response, JSONUtil.getJson(map));
		} else {
			userType = Integer.parseInt(request.getParameter("userType"));
		}
		CL_Order order = orderDao.getById(orderId);
		order.setForiginal(order.getFreight().add(order.getServe_1() == null?BigDecimal.ZERO:order.getServe_1()).add(order.getServe_2() == null?BigDecimal.ZERO:order.getServe_2()));
		// ios再次下单调用了这里，sql类似，BY LANCHER 2016/10/10 BEGIN-------------
		// order.setTakeList(orderDetailDao.getByOrderId(orderId,1));
		order.setTakeList(orderDetailDao.getByNewOrderId(orderId, 1));
		// order.setRecList(orderDetailDao.getByOrderId(orderId,2));
		order.setRecList(orderDetailDao.getByNewOrderId(orderId, 2));
		// ios再次下单调用了这里，sql类似，BY LANCHER 2016/10/10 END-------------

		// CL_Rule rule = this.ruleDao.getOneByType();
		// if(rule ==null){
		// map.put("success", "false");
		// map.put("msg","司机计费规则丢失,请联系客服！");
		// return writeAjaxResponse(response, JSONUtil.getJson(map));
		// }
		List<CL_Addto> adds = iaddtoDao.getByOrderId(orderId);
		String AllNumber = "";
		// 事先计算货主运费和司机运费 BY CC 20160518

		if (adds.size() > 0) {
			for (CL_Addto ad : adds) {
				if (ad.getNumber() != null && !"".equals(ad.getNumber())) {
					AllNumber += ad.getNumber() + ",";
				}
				AllCost = AllCost.add(ad.getFcost());
				AllCostD = AllCostD.add(ad.getFcost());
			}
		}
		if (!"".equals(AllNumber)) {
			AllNumber = AllNumber.substring(0, AllNumber.length() - 1);
		}
		order.setAllNumber(AllNumber);
		// order.setChargingrule(CalcTotalField.calDriverFee(AllCost.add(order.getFreight())));
		if (userType == 2) { // 车主
			order.setAllCost(AllCostD.setScale(2, BigDecimal.ROUND_HALF_UP));
			// 判断运费调整是否有审批，或是通过审批 2016/11/30 by lancher
			if (order.getFispass_audit() == null || order.getFispass_audit() == 0) {
				order.setFreight(order.getForigin_driverfee().setScale(2, BigDecimal.ROUND_HALF_UP));
			} else {
				order.setFreight(order.getFdriverfee().setScale(2, BigDecimal.ROUND_HALF_UP));
			}
		}
		if (userType == 1) {// 货主
			// AllCost=AllCost.setScale(0,BigDecimal.ROUND_HALF_UP);
			order.setAllCost(AllCost.setScale(2, BigDecimal.ROUND_HALF_UP));
			// 判断运费调整是否有审批，或是通过审批 2016/11/30 by lancher
			if (order.getFispass_audit() == null || order.getFispass_audit() == 0) {
				order.setFreight(order.getForiginfreight().setScale(2, BigDecimal.ROUND_HALF_UP));
			} else {
				order.setFreight(order.getFreight().setScale(2, BigDecimal.ROUND_HALF_UP));
			}
		}
		// order.setAllCost(AllCost.setScale(0,BigDecimal.ROUND_HALF_UP));
		if (order.getType() != 2) {
			List<Util_Option> ups = optionDao.getCarTypeByOrderIdName2(orderId);
			if (ups.size() <= 0) {
				map.put("success", "false");
				map.put("msg", "订单数据有误,所需车型没有,请联系客服");
				return writeAjaxResponse(response, JSONUtil.getJson(map));
			}
			String carSpecName = ups.get(0).getOptionName();
			String carOtherName = ups.get(0).getOptionCarOtherName();
			for (Util_Option option : ups) {
				// 增加订单再次下单未选择车型导致车型错误问题修改;
				if (option.getOptionCarTypeName() != null && !option.getOptionCarTypeName().equals("")) {
					if (!carSpecName.contains(option.getOptionCarTypeName()) && option.getOptionCarTypeName() != null
							&& !"".equals(option.getOptionCarTypeName())) {
						carSpecName = carSpecName + " " + option.getOptionCarTypeName();
					}
				}
				if (carOtherName != null && "".equals(carOtherName)
						&& !carOtherName.contains(option.getOptionCarOtherName())
						&& !"".equals(option.getOptionCarOtherName()) && option.getOptionCarOtherName() != null) {
					carOtherName = carOtherName + " " + option.getOptionCarOtherName();
				}
			}
			order.setCarSpecName(carSpecName);
			order.setCarTypeName(carOtherName);

		}
		if (order.getUserRoleId() != null) {
			List<CL_Car> car = carDao.getByUserRoleId(order.getUserRoleId());
			if (car.size() > 0) {
				order.setOrderDriverphone(car.get(0).getCarFtel());
				order.setOrderDriverCarNumber(car.get(0).getCarNum());
				order.setOrderDriverCarType(car.get(0).getCarTypeName());
				order.setOrderDriverName(car.get(0).getDriverName());
			}
		}
		CL_Rating rat = iratingDao.getByOrderNumAndType(order.getNumber(), 0);
		if (rat != null) {
			if (rat.getRatingType() == 0) {
				BigDecimal a, rate = new BigDecimal(0);
				a = rat.getService().add(rat.getComplete()).add(rat.getTimeliness());
				rate = new BigDecimal(3);
				a = a.divide(rate, 0, BigDecimal.ROUND_HALF_UP);
				order.setAverage(a);
			}
			if (rat.getRatingType() == 1) {
				order.setAverage(rat.getRatingScore());
			}
		}

		// 20160718 cd 订单支付时选择了好运券就会记录到订单,所以只有已付款的才在订单详情显示
//		if (order.getStatus() > 1) {
			if (order.getCouponsDetailId() != null && order.getCouponsDetailId() != 0) {//增值
				CL_CouponsDetail cou = couponsDetailDao.getById(order.getCouponsDetailId());
				if(cou.getFaddserviceName().indexOf("装货") != -1){
					order.setDollarsDet(order.getServe_1());
				} else if (cou.getFaddserviceName().indexOf("卸货") != -1) {
					order.setDollarsDet(order.getServe_2());
				} else {
					order.setDollarsDet(BigDecimal.ZERO);
				}
			}
			if (order.getCouponsId() != null && order.getCouponsId() != 0) {//订单
				order.setDollars(order.getDiscount());
			}
//		}
		List<CL_OrderCarDetail> corderList = orderCarDetailDao.getByOrderId(orderId);
		String cotherName = "";
		for (CL_OrderCarDetail orderCarDetail : corderList) {
			cotherName = cotherName + " " + orderCarDetail.getCotherName();
		}
		order.setCotherName(cotherName);

		HashMap<String, Object> m = new HashMap<String, Object>();
		m.put("success", "true");
		m.put("data", order);
		return writeAjaxResponse(response, JSONUtil.getJson(m));
	}
	
	
	/**
	 * 确认卸货接口
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/app/orderDetail/detailConfirm")
	public String detailConfirm(HttpServletRequest request,HttpServletResponse response){
		HashMap<String, Object> m = new HashMap<String, Object>();
		CL_OrderDetail detail = new CL_OrderDetail();
		String detailId = request.getParameter("detailId");
		detail = orderDetailDao.getById(Integer.parseInt(detailId));
		CL_Order order = orderDao.getById(detail.getOrderId());
		Integer fcusid = ServerContext.getUseronline().get(request.getSession().getId().toString()).getFuserId();
		if(!order.getUserRoleId().equals(fcusid)){
			return this.poClient(response, false, "该订单已被调度予其他司机，请联系客服");
		}
		if(detail.getFstatus() != 1){
			if(detail.getDetailType() == 1){//提货不需要上传图片
				detail.setFstatus(1);
				detail.setFarrived_time(new Date());
				orderDetailDao.update(detail);
				detail = orderDetailDao.getById(Integer.parseInt(detailId));
				m.put("success", "true");
				m.put("data", detail);
			} else {
				//卸货钱先确认是否已提货
				CL_OrderDetail detail2 = orderDetailDao.getByNewOrderId(detail.getOrderId(), 1).get(0);
				if(detail2.getFstatus() != 1){
					return this.poClient(response, false, "请先提货!");
				}
				m.put("success", "true");
				m.put("data", detail);
			}
			return writeAjaxResponse(response, JSONUtil.getJson(m));
		} else {
			return this.poClient(response, false, "请勿重复操作!");
		}
	}

	//
	// /***查询订单详情信息* */
	// @RequestMapping("/app/orderDetail/loadOrderDetail")
	// public String loadOrderDetail(HttpServletRequest
	// request,HttpServletResponse response) throws Exception{
	// Integer orderId ;
	// HashMap<String,Object> map =new HashMap<String,Object>();
	// if(request.getParameter("orderId")==null ||
	// "".equals(request.getParameter("orderId"))){
	// map.put("success", "false");
	// map.put("msg","订单数据有误,请联系客服");
	// return writeAjaxResponse(response, JSONUtil.getJson(map));
	// }else{
	// orderId = Integer.parseInt(request.getParameter("orderId"));
	// }
	// CL_Order order=orderDao.getById(orderId);
	// CL_Rule rule = this.ruleDao.getOneByType();
	// if(rule ==null){
	// map.put("success", "false");
	// map.put("msg","司机计费规则丢失,请联系客服！");
	// return writeAjaxResponse(response, JSONUtil.getJson(map));
	// }
	// order.setFreight(order.getFreight().multiply(rule.getStartPrice()).setScale(0,
	// BigDecimal.ROUND_HALF_UP));
	// }
	// order.setTakeList(orderDetailDao.getByOrderId(orderId,1));
	// order.setRecList(orderDetailDao.getByOrderId(orderId,2));
	// order.setCarList(orderCarDetailDao.getByOrderId(orderId));
	// if(order.getUserRoleId()!=null){
	// List<CL_Car> car=carDao.getByUserRoleId(order.getUserRoleId());
	// if(car!=null){
	// order.setOrderDriverphone(car.get(0).getCarFtel());
	// order.setOrderDriverCarNumber(car.get(0).getCarNum());
	// order.setOrderDriverCarType(car.get(0).getCarTypeName());
	// order.setOrderDriverName(car.get(0).getDriverName());
	// }
	// }
	//
	// HashMap<String, Object> m = new HashMap<String, Object>();
	// m.put("success", "true");
	// m.put("data", order);
	// return writeAjaxResponse(response, JSONUtil.getJson(m));
	// }

	/**
	 * 线上支付生成二维码
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/app/orderDetail/createPayQR")
	public String createPayQR(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// 生成支付二维码
		String orderId = request.getParameter("orderId"); // 订单ID
		String payMeth = request.getParameter("payMeth"); // 生成支付类型(1-支付宝2-微信)

		// 必不为空判断
		String msg = String_Custom.parametersEmpty(new String[] { orderId, payMeth });
		if (msg.length() != 0)
			return this.poClient(response, false, msg);
		// 合理性判断
		if (String_Custom.noNumber(orderId))
			return this.poClient(response, false, "订单参数错误!");
		if (String_Custom.noNumber(payMeth))
			return this.poClient(response, false, "请求生成的支付方式错误!");

		CL_Order order = orderDao.getById(Integer.parseInt(orderId));
		if (order == null)
			return this.poClient(response, false, "获取不到该订单信息");

		//判断运费是否调整中
		if (order.getFispass_audit() != null && order.getFispass_audit() == 0)
			return this.poClient(response, false, "订单运费调整中，请联系客服");
		// 计算该订单 应付金额
		// 获取实际支付金额
		BigDecimal actuaAmount = BigDecimal.ZERO;
		BigDecimal payAmount = BigDecimal.ZERO;
		int pay = order.getFpayMethod();
		/**
		 * 修复逻辑：
		 * 如果订单的增值服务有代收货款
		 * 运费到付的订单：司机实收款 = 订单运费 + 代收货款
		 * 月结订单：司机实收款 = 代收货款
		 * 普通订单（即客户已经付了款的订单）：司机实收款 = 代收货款
		 *  by Liar 17/3/28
		 */
		if(pay == 4){
			actuaAmount = order.getFreight().add(order.getServe_1() == null?BigDecimal.ZERO:order.getServe_1()).add(order.getServe_2() == null?BigDecimal.ZERO:order.getServe_2()).add(order.getPurposeAmount() == null?BigDecimal.ZERO:order.getPurposeAmount());
			payAmount = order.getFreight().add(order.getServe_1() == null?BigDecimal.ZERO:order.getServe_1()).add(order.getServe_2() == null?BigDecimal.ZERO:order.getServe_2()).add(order.getPurposeAmount() == null?BigDecimal.ZERO:order.getPurposeAmount());
		} else {//月结
			actuaAmount = order.getPurposeAmount() == null?BigDecimal.ZERO:order.getPurposeAmount();
			payAmount = order.getPurposeAmount()== null?BigDecimal.ZERO:order.getPurposeAmount();
		} 

		// 减完好运券之后的金额（订单真实支付金额）
		/*CL_CouponsDetail detail = this.couponsDetailDao.getById(order.getCouponsDetailId());
		if (detail != null) {
			CL_Coupons cp = this.couponsDao.getById(detail.getCouponsId());
			if (cp != null) {
				payAmount = actuaAmount.subtract(cp.getDollars());
			}
		}*/

		Integer fcusid = ServerContext.getUseronline().get(request.getSession().getId().toString()).getFuserId();
		if(!order.getUserRoleId().equals(fcusid)){
			return this.poClient(response, false, "该订单已被调度予其他司机，请联系客服");
		}
		
		// 读取相关支付订单生成
		List<CL_FinanceStatement> fstatement = financeStatementDao.getforderId(order.getNumber(),
				Integer.parseInt(payMeth), 12);
		String FNumber = ""; // 支付明细订单号
		if (fstatement.size() > 0) {
			FNumber = fstatement.get(0).getNumber();
		} else if (fstatement.size() > 1)
			return this.poClient(response, false, "生成多条支付信息，该订单报废");

		/***************** 对接支付系统所需参数 ******************************/
		LinkedHashMap<String, Object> modellist = new LinkedHashMap<>();
		modellist.put("orderNumber", ""); // 订单编号
		modellist.put("orderCreateTime", String_Custom.getPayOrderCreateTime());
		modellist.put("payerId", MD5Util.getMD5String(fcusid + "ylhy").substring(0, 30));
		modellist.put("serviceProvider", ""); // 支付方式
		modellist.put("serviceProviderType", ""); // 支付方
		modellist.put("bankCardBindId", "");
		modellist.put("payAmount", payAmount + "");
		modellist.put("description", "东运物流有限公司");
		modellist.put("notificationURL", "");

		HashMap<String, Object> param = new HashMap<>();
		param.put("fcusid", fcusid);
		param.put("frelatedId", order.getId().toString());
		param.put("forderId", order.getNumber());
		param.put("famount", payAmount);
		param.put("userDao", userRoleDao);
		param.put("FinanceDao", financeStatementDao);
		param.put("freight", actuaAmount);
		/***********************************************/
		if (FNumber.length() > 0) {
			// 已生成 不需要再次生成数据
			param.put("fbusinessType", new Integer(0));
			modellist.put("orderNumber", FNumber); // 订单编号
		}

		String result = "";
		switch (Integer.parseInt(payMeth)) {
		case 1: {
			modellist.put("serviceProvider", "ALI"); // 支付方式
			modellist.put("serviceProviderType", "QR"); // 支付方
			param.put("fbusinessType", 12);
			param.put("ftype", -1); // 支出
			param.put("fpayType", 1);
			if (FNumber.length() > 0) {
				// 已生成 不需要再次生成数据
				param.put("fbusinessType", new Integer(0));
			}

			result = PayUtil.payOrderToPaySystem_ZFBWX(param, modellist);
		}
			break;
		case 2: {
			modellist.put("serviceProvider", "WX"); // 支付方式
			modellist.put("serviceProviderType", "QR"); // 支付方
			param.put("fbusinessType", 12);
			param.put("ftype", -1); // 支出
			param.put("fpayType", 2);

			result = PayUtil.payOrderToPaySystem_ZFBWX(param, modellist);
		}
			break;
		default:
			break;
		}

		try {
			JSONObject jo = JSONObject.fromObject(result);
			if ("true".equals(jo.get("success").toString())) {
				result = jo.get("data").toString();
				Integer code = jo.getInt("code");
				if (code == 1000 && result.length() == 0)
					return this.poClient(response, false, "用户已完成付款，请点击完成！");
			} else {
				String msg2 = jo.get("msg").toString();
				if ("未知错误".equals(jo.get("msg").toString())) {
					msg2 = "请一分钟后重试..";
				}
				return this.poClient(response, false, msg2);
			}
		} catch (StringIndexOutOfBoundsException e) {
			return this.poClient(response, false, "支付系统异常");
		}

		return this.poClient(response, true, "\"" + result + "\"");
	}

	/**
	 * 司机最终的订单完成
	 *
	 * @return
	 */
	@RequestMapping("/app/orderDetail/orderConfirm")
	public String orderConfirm(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String orderId = request.getParameter("orderId"); // 订单ID
		String payment = request.getParameter("payment"); // 支付0-正常支付 1-线上支付  2-线下支付

		// 必不为空判断
		String msg = String_Custom.parametersEmpty(new String[] { orderId, payment });
		if (msg.length() != 0)
			return this.poClient(response, false, msg);

		// 合理性判断
		if (String_Custom.noNumber(orderId))
			return this.poClient(response, false, "订单参数错误!");
		if (String_Custom.noNumber(payment))
			return this.poClient(response, false, "支付方式参数错误!");

		//获取该订单的所有明细，用于判断是否所有地址均已确认
		List<CL_OrderDetail> orderDeatilList = orderDetailDao.getByOrderId(Integer.parseInt(orderId));
		// 根据订单编号 获取订单信息
		if (orderDeatilList.size() == 0)
			return this.poClient(response, false, "获取不到该订单信息");

		CL_Order order = orderDao.getById(Integer.parseInt(orderId));
		if (order == null)
			return this.poClient(response, false, "获取不到该订单信息");

		// 判断是否所有回单是否已上传
		/*if (orderDeatil.getFreceiptSave() != null && orderDeatil.getFreceiptSave() == 1)
			return this.poClient(response, false, "请先完成回单上传!");*/
		for (CL_OrderDetail orderDetail : orderDeatilList) {
			if(orderDetail.getFstatus() == 0){
				return this.poClient(response, false, "请先确认卸货!");
			}
		}

		//判断订单是否进行了运费调整且未审批、
		if(order.getFispass_audit() != null && order.getFispass_audit() == 0){
			return this.poClient(response, false, "该订单运费正在调整，请稍后完成");
		}
		
		// 订单状态进行判断
		if (order.getStatus() == 4 || order.getStatus() == 5)
			return this.poClient(response, false, "该订单已完成，请联系客服");
		else if (order.getStatus() != 3)
			return this.poClient(response, false, "该订单状态无法进行完成");

		// 用户id
		// Integer fcusid = 4132;
		Integer fcusid = ServerContext.getUseronline().get(request.getSession().getId().toString()).getFuserId();
		if(!order.getUserRoleId().equals(fcusid)){
			return this.poClient(response, false, "该订单已被调度予其他司机，请联系客服");
		}
//		CL_UserRole role = userRoleDao.getById(fcusid);
		// 判断支付方式 是否合理
		int payType = Integer.parseInt(payment);

		switch (payType) {
		case 0: {
			// 正常订单
			// 获取实际支付金额
			BigDecimal actuaAmount = order.getFdriverfee(); // 获取司机应对运费

			List<CL_FinanceStatement> fstatement = financeStatementDao.getforderId(order.getNumber(), 0, 4);
			String FNumber = ""; // 支付明细订单号
			if (fstatement.size() == 0) {
				FNumber = CacheUtilByCC.getOrderNumber("cl_finance_statement", "L", 8);
				CL_FinanceStatement statement = new CL_FinanceStatement();
				statement.setFcreateTime(new Date());
				statement.setNumber(FNumber);
				statement.setFrelatedId(order.getId().toString());
				statement.setForderId(order.getNumber());
				statement.setFbusinessType(4); // 司机订单完成
				statement.setFamount(actuaAmount);
				statement.setFtype(1);
				statement.setFuserroleId(fcusid);
				statement.setFuserid(fcusid.toString());
				statement.setFpayType(0);
				statement.setFremark(order.getNumber() + "运费");
				statement.setFbalance(new BigDecimal("0"));
//				statement.setFbalance(role.getFbalance());
				statement.setFreight(actuaAmount);
				statement.setFstatus(0);
				financeStatementDao.save(statement);
			} else if (fstatement.size() > 0) {
				FNumber = fstatement.get(0).getNumber();
			} else if (fstatement.size() > 1)
				return this.poClient(response, false, "生成多条支付信息，该订单报废");

			OrderMsg ordermsg = new OrderMsg();
			ordermsg.setPayState("1"); // 支付成功
			ordermsg.setServiceProvider("0");// 余额支付
			ordermsg.setServiceProviderType("0");// 余额支付;
			ordermsg.setOrder(FNumber);
			ordermsg.setUserId(1);// 用于判断线上
			int status = financeStatementDao.updateBusinessType(ordermsg);
			if (status == 0)
				return this.poClient(response, false, "运费转账失败，请联系客服!");
		}
			break;
		case 1: {
			// 线上
			// 读取相关支付订单生成
			List<CL_FinanceStatement> fstatement = financeStatementDao.getforderId(order.getNumber(), 99, 12); // 99表示
																												// 所有支付类型
			String resutl = "";
			if (fstatement.size() > 0) {
				for (CL_FinanceStatement temp : fstatement) {
					if (temp.getFstatus() == 1) {
						resutl = "支付成功";
						break;
					}
				}
			} else {
				return this.poClient(response, false, "未查询到该订单相关支付信息");
			}

			if (resutl.length() == 0)
				return this.poClient(response, false, "订单未被支付成功，请稍后再试");
			else {
				// 判断是否包含货到付款
				// if (order.getFpayMethod() == 4 || order.getFpayMethod() == 5)
				// {
				// 转账金额
				BigDecimal actuaAmount = order.getFdriverfee(); // 获取司机应对运费
				List<CL_FinanceStatement> fstatement2 = financeStatementDao.getforderId(order.getNumber(), 0, 4);
				String FNumber = ""; // 支付明细订单号
				if (fstatement2.size() == 0) {
					FNumber = CacheUtilByCC.getOrderNumber("cl_finance_statement", "L", 8);
					CL_FinanceStatement statement = new CL_FinanceStatement();
					statement.setFcreateTime(new Date());
					statement.setNumber(FNumber);
					statement.setFrelatedId(order.getId().toString());
					statement.setForderId(order.getNumber());
					statement.setFbusinessType(4); // 司机订单完成
					statement.setFamount(actuaAmount);
					statement.setFtype(1);
					statement.setFuserroleId(fcusid);
					statement.setFuserid(fcusid.toString());
					statement.setFpayType(0);
					statement.setFremark(order.getNumber() + "运费");
					statement.setFbalance(new BigDecimal("0"));
					statement.setFreight(actuaAmount);
					statement.setFstatus(0);
					financeStatementDao.save(statement);
				} else
				{
					FNumber = fstatement2.get(0).getNumber();
				}

				OrderMsg ordermsg = new OrderMsg();
				ordermsg.setPayState("1"); // 支付成功
				ordermsg.setServiceProvider("0");// 余额支付
				ordermsg.setServiceProviderType("0");// 余额支付;
				ordermsg.setOrder(FNumber);
				ordermsg.setUserId(1);// 用于判断线上
				int status = financeStatementDao.updateBusinessType(ordermsg);
				if (status == 0)
					return this.poClient(response, false, "转账失败，请联系客服处理!");

				// } else {
				// order.setFonlinePay(1);
				// order.setStatus(4);
				// orderDao.update(order);
				// }
			}
		}
			break;
		case 2: {
			// 线下
			// 直接修改订单状态
			if (order.getFpayMethod() == 5) {
				// 转账金额
				BigDecimal actuaAmount = order.getFdriverfee(); // 获取司机应对运费
				List<CL_FinanceStatement> fstatement2 = financeStatementDao.getforderId(order.getNumber(), 0, 4);
				String FNumber = ""; // 支付明细订单号
				if (fstatement2.size() == 0) {
					FNumber = CacheUtilByCC.getOrderNumber("cl_finance_statement", "L", 8);
					CL_FinanceStatement statement = new CL_FinanceStatement();
					statement.setFcreateTime(new Date());
					statement.setNumber(FNumber);
					statement.setFrelatedId(order.getId().toString());
					statement.setForderId(order.getNumber());
					statement.setFbusinessType(4); // 司机订单完成
					statement.setFamount(actuaAmount);
					statement.setFtype(1);
					statement.setFuserroleId(fcusid);
					statement.setFuserid(fcusid.toString());
					statement.setFpayType(0);
					statement.setFremark(order.getNumber() + "运费");
					statement.setFbalance(new BigDecimal("0"));
					statement.setFreight(actuaAmount);
					statement.setFstatus(0);
					financeStatementDao.save(statement);
				} else {
					FNumber = fstatement2.get(0).getNumber();
				}

				OrderMsg ordermsg = new OrderMsg();
				ordermsg.setPayState("1"); // 支付成功
				ordermsg.setServiceProvider("0");// 余额支付
				ordermsg.setServiceProviderType("0");// 余额支付;
				ordermsg.setOrder(FNumber);
				ordermsg.setUserId(2);// 用于判断线下
				int status = financeStatementDao.updateBusinessType(ordermsg);
				if (status == 0)
					return this.poClient(response, false, "支付失败,请确认余额是否充足!");

			} else {
				if (order.getStatus() == 3) {
					// 司机订单完成
					order.setFonlinePay(2);
					order.setStatus(4);
					order.setFcopTime(new Date());
					orderDao.update(order);
				}
			}
		}
			break;
		default:
			break;
		}

		// 正常支付 需要
		// 代收货款判断 purpose_amount
		// 是否包天
		// 是否运费到付 fpay_Method

		// 判断正常支付是否符合要求

		// 判断是否运费到付订单

		/*
		 * 支付重构后貌似有不少东西遗漏了，比如：
		 * 		订单完成后没有更新成单次数
		 */
		CL_UserRole customer = userRoleDao.getById(order.getCreator());
		CL_UserRole driver = userRoleDao.getById(order.getUserRoleId());
		customer.setEndOrderTimes(customer.getEndOrderTimes()+1);
		driver.setEndOrderTimes(driver.getEndOrderTimes()+1);
		userRoleDao.updateTimes(customer);
		userRoleDao.updateTimes(driver);
		return this.poClient(response, true);
	}

	/**
	 * 地址明细详情 验证接口
	 * 
	 * @throws Exception
	 */
	/*
	 * @RequestMapping("/app/orderDetail/newpassRecAdd")
	 * 
	 * @Transactional public String newpassRecAdd(HttpServletRequest request,
	 * HttpServletResponse response) throws Exception { // type 0：正常的单子 1包天
	 * 2货到付款 3代收货款 4货到付款 和代收货款 onLine 1:线上支付 2：线下支付 Integer orderDetailId =
	 * null, type, onLine, orderId; String finalBill = ""; String securityCode,
	 * checkDate = "", checkUrl = "", versionCode = null, fsystem = "";
	 * HashMap<String, Object> map = new HashMap<String, Object>();
	 * CL_OrderDetail orderDeatil = new CL_OrderDetail(); List<CL_OrderDetail>
	 * passAll = new ArrayList<>(); if (request.getParameter("orderDetailId") ==
	 * null || "".equals(request.getParameter("orderDetailId"))) {
	 * map.put("success", "false"); map.put("msg", "订单数据ID有误,请联系客服"); return
	 * writeAjaxResponse(response, JSONUtil.getJson(map)); } else {
	 * orderDetailId = Integer.parseInt(request.getParameter("orderDetailId"));
	 * } // APP强制更新； HashMap<String, Util_UserOnline> useronline =
	 * ServerContext.getUseronline(); if (request.getSession().getId() == null
	 * || request.getSession().getId().equals("")) { map.put("success",
	 * "false"); map.put("msg", "登录超时！"); return writeAjaxResponse(response,
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
	 * orderDeatil = orderDetailDao.getById(orderDetailId); orderId =
	 * orderDeatil.getOrderId(); CL_Order order = orderDao.getById(orderId);
	 * 
	 * // if(request.getParameter("securityCode")==null || //
	 * "".equals(request.getParameter("securityCode"))){ // map.put("success",
	 * "false"); // map.put("msg","订单数据随机码有误,请联系客服"); // return
	 * writeAjaxResponse(response, JSONUtil.getJson(map)); // }else{ //
	 * securityCode = request.getParameter("securityCode").toString(); // } if
	 * (request.getParameter("type") == null ||
	 * "".equals(request.getParameter("type"))) { map.put("success", "false");
	 * map.put("msg", "订单数据类型有误,请联系客服"); return writeAjaxResponse(response,
	 * JSONUtil.getJson(map)); } else { type =
	 * Integer.valueOf(request.getParameter("type").toString()); } if
	 * (request.getParameter("onLine") == null ||
	 * "".equals(request.getParameter("onLine"))) { map.put("success", "false");
	 * map.put("msg", "订单数据支付方式有误,请联系客服"); return writeAjaxResponse(response,
	 * JSONUtil.getJson(map)); } else { onLine =
	 * Integer.valueOf(request.getParameter("onLine").toString()); } orderDeatil
	 * = orderDetailDao.getById(orderDetailId); //
	 * orderDeatil=orderDetailDao.getByIdAndCode(orderDetailId, //
	 * securityCode); // Integer orderId=null, Integer Allpass = 0; if
	 * (orderDeatil != null) { if (orderDeatil.getFreceiptSave() != null &&
	 * orderDeatil.getFreceiptSave() == 1) { map.put("success", "false");
	 * map.put("msg", "请先完成回单上传!"); return writeAjaxResponse(response,
	 * JSONUtil.getJson(map)); } orderDetailDao.updatePass(orderDetailId, 1); if
	 * (order == null) { map.put("success", "false"); map.put("msg",
	 * "订单异常,请联系客服"); return writeAjaxResponse(response, JSONUtil.getJson(map));
	 * 
	 * } if (order.getStatus() == 4 || order.getStatus() == 5) {
	 * orderDetailDao.updatePass(orderDetailId, 1); map.put("success", "false");
	 * map.put("msg", "订单已完成,请联系客服"); return writeAjaxResponse(response,
	 * JSONUtil.getJson(map)); } // //获得 提货点 或者卸货点的 验证信息 if (type == 1) {
	 * passAll = orderDetailDao.getByOrderId(orderId, 1); } else { passAll =
	 * orderDetailDao.getByOrderId(orderId, 2); } // //循环判断是否成功验证 for (int i =
	 * 0; i < passAll.size(); i++) { if (passAll.get(i).getPass() == 1) {
	 * Allpass = 1; } else { Allpass = 0; break; } }
	 * 
	 * // 验证通过 if (Allpass == 1) { BigDecimal ss = new BigDecimal(0); String
	 * AllNumber = order.getNumber() + ","; if
	 * (request.getParameter("finalBill") == null ||
	 * "".equals(request.getParameter("finalBill"))) {
	 * orderDetailDao.updatePass(passAll.get(passAll.size() - 1).getId(), 0);
	 * map.put("success", "false"); map.put("msg", "车主最终价格为空！"); return
	 * writeAjaxResponse(response, JSONUtil.getJson(map)); } else { finalBill =
	 * request.getParameter("finalBill"); // 比较 订单总价格和前段总价格
	 * 
	 * 司机价格分离取消追加费用的逻辑 2016-11-25 by lancher List<CL_Addto>
	 * adds=iaddtoDao.getByOrderId(orderId); BigDecimal allCost=new
	 * BigDecimal(0); if(adds.size()>0){ for(CL_Addto ad:adds){
	 * allCost=allCost.add(ad.getFdriverfee());
	 * AllNumber+=ad.getFpayNumber()+","; } } BigDecimal feiyun= new
	 * BigDecimal(0); feiyun= new BigDecimal(finalBill); // CL_Rule
	 * ru=ruleDao.getOneByType(); ss=
	 * (order.getFdriverfee().add(allCost)).setScale(1,
	 * BigDecimal.ROUND_HALF_UP); 司机价格分离取消追加费用的逻辑 2016-11-25 by lancher
	 * 
	 * 
	 * // if(ss.compareTo(feiyun)==1||ss.compareTo(feiyun)==-1){ //
	 * orderDetailDao.updatePass(orderDetailId, 0); // map.put("success",
	 * "false"); // map.put("msg","运费不匹配!"); // return
	 * writeAjaxResponse(response, // JSONUtil.getJson(map)); // } if
	 * (order.getFispass_audit() == null || order.getFispass_audit() == 0) { ss
	 * = (order.getForigin_driverfee()).setScale(1, BigDecimal.ROUND_HALF_UP); }
	 * else { ss = (order.getFdriverfee()).setScale(1,
	 * BigDecimal.ROUND_HALF_UP); } }
	 * 
	 * // onLine=1 线上 onLine=2 线下 switch (onLine) { case 1: CL_UserRole user =
	 * iuserRoleDao.getById(order.getUserRoleId()); if (type == 2 || type == 3
	 * || type == 4) { //// ==================校验 支付状态接口 开始 ============= if
	 * (request.getParameter("checkDate") == null ||
	 * "".equals(request.getParameter("checkDate"))) {
	 * orderDetailDao.updatePass(passAll.get(passAll.size() - 1).getId(), 0);
	 * map.put("success", "false"); map.put("msg", "校验参数U为空"); return
	 * writeAjaxResponse(response, JSONUtil.getJson(map)); } if
	 * (request.getParameter("checkUrl") == null ||
	 * "".equals(request.getParameter("checkUrl"))) {
	 * orderDetailDao.updatePass(passAll.get(passAll.size() - 1).getId(), 0);
	 * map.put("success", "false"); map.put("msg", "校验URl参数为空"); return
	 * writeAjaxResponse(response, JSONUtil.getJson(map)); } if
	 * (!ServerContext.cancelOrderPay(request.getParameter("checkUrl"),
	 * request.getParameter("checkDate"))) {
	 * orderDetailDao.updatePass(passAll.get(passAll.size() - 1).getId(), 0);
	 * map.put("success", "false"); // map.put("msg",ServerContext.getMsg());
	 * map.put("msg", "客户未付款，不能完成支付！"); return writeAjaxResponse(response,
	 * JSONUtil.getJson(map)); } } if (user.getVmiUserPhone() == null) {
	 * orderDetailDao.updatePass(passAll.get(passAll.size() - 1).getId(), 0);
	 * map.put("success", "false"); map.put("msg", "用户支付用户名、手机无效!"); return
	 * writeAjaxResponse(response, JSONUtil.getJson(map)); } JSONObject jo2 =
	 * new JSONObject(); JSONObject jo1 = new JSONObject(); jo2.put("Fid",
	 * order.getNumber()); jo2.put("Phone", user.getVmiUserPhone());
	 * jo2.put("Amount", ss); jo2.put("Type", "Add"); jo2.put("Info",
	 * AllNumber); jo1.put("data", jo2); String payDate =
	 * Base64.encode(jo1.toString().getBytes()); // ================== 支付 接口开始
	 * (转运费给司机)========= // if(!ServerContext.cancelOrderPay(payUrl, payDate)){
	 * // orderDetailDao.updatePass(passAll.get(passAll.size()-1).getId(), //
	 * 0); // map.put("success", "false"); //
	 * map.put("msg",ServerContext.getMsg()); // return
	 * writeAjaxResponse(response, // JSONUtil.getJson(map)); // } //
	 * ====================支付结束 ===================== // { //
	 * orderDetailDao.updatePass(passAll.get(passAll.size()-1).getId(), // 0);
	 * // map.put("success", "false"); // map.put("msg",ServerContext.getMsg());
	 * // return writeAjaxResponse(response, // JSONUtil.getJson(map)); // } //
	 * ==================校验 支付状态接口 结束 ============= // ================== 支付
	 * 接口开始 有货到付款情况==================== // if(type==2 ||type==4 ){ //
	 * HashMap<String, Object> addTo=new // HashMap<String,Object>(); //
	 * addTo.put("QrPay", "True"); // String Att=JSONUtil.getJson(addTo); //
	 * if(!ServerContext.cancelCouponsPay(request.getParameter("payUrl"), //
	 * request.getParameter("payDate"),Att)) // { //
	 * orderDetailDao.updatePass(passAll.get(passAll.size()-1).getId(), // 0);
	 * // map.put("success", "false"); // map.put("msg",ServerContext.getMsg());
	 * // return writeAjaxResponse(response, // JSONUtil.getJson(map)); // } //
	 * } // ================== 支付 接口开始 有货到付款情况结束====================
	 * 
	 * // ==================支付 接口开始 无货到付款情况 // ========================== //
	 * ===============================支付接口结结束 //
	 * 无货到付款请款========================= // ===============线上 支付 开始 //
	 * if(order.getCouponsDetailId()!=null&&!"".equals(order.getCouponsDetailId(
	 * ))&&!order.getCouponsDetailId().equals("null")){ // CL_CouponsDetail //
	 * cdet=this.couponsDetailDao.getById(order.getCouponsDetailId()); //
	 * CL_Coupons // cou=this.couponsDao.getById(cdet.getCouponsId()); //
	 * HashMap<String, Object> Attached1=new // HashMap<String,Object>(); //
	 * Attached1.put("Money", cou.getDollars()); // Attached1.put("Coupons",
	 * order.getCouponsDetailId()); // String Attached; //
	 * Attached=JSONUtil.getJson(Attached1); //
	 * if(!ServerContext.cancelCouponsPay(request.getParameter("payUrl"), //
	 * request.getParameter("payDate"),Attached)) // { //
	 * orderDetailDao.updatePass(passAll.get(passAll.size()-1).getId(), // 0);
	 * // map.put("success", "false"); // map.put("msg",ServerContext.getMsg());
	 * // return writeAjaxResponse(response, // JSONUtil.getJson(map)); // } //
	 * } //
	 * if(order.getCouponsDetailId()==null||"".equals(order.getCouponsDetailId()
	 * )){ // if(!ServerContext.cancelOrderPay(request.getParameter("payUrl"),
	 * // request.getParameter("payDate"))) // { //
	 * orderDetailDao.updatePass(passAll.get(passAll.size()-1).getId(), // 0);
	 * // map.put("success", "false"); // map.put("msg",ServerContext.getMsg());
	 * // return writeAjaxResponse(response, // JSONUtil.getJson(map)); // } //
	 * }
	 * 
	 * // =====支付 结束========================================= //
	 * ==========开始修改订单状态==========================
	 * orderDao.updateStatusOnline(orderId, 1, new Date());
	 * orderDao.updateStatusByOrderId(orderId, 4);
	 *//** 司机、货主成单次数更新； */
	/*
	 * CL_Order orderinfo = orderDao.getById(orderId); CL_UserRole driverinfo =
	 * iuserRoleDao.getById(orderinfo.getUserRoleId()); CL_UserRole shipperinfo
	 * = iuserRoleDao.getById(orderinfo.getCreator());
	 * driverinfo.setEndOrderTimes(driverinfo.getEndOrderTimes() + 1);
	 * shipperinfo.setEndOrderTimes(shipperinfo.getEndOrderTimes() + 1); //
	 * 司机成单次数； iuserRoleDao.updateTimes(driverinfo); // 货主成单次数；
	 * iuserRoleDao.updateTimes(shipperinfo);
	 *//** 司机、货主成单次数更新； */
	/*
	
	*//** 收支明细表添加 Start */
	/*
	 * // statementDao.saveStatement(frelatedId, forderId, // fbusinessType,
	 * famount, ftype, fuserroleId, fpayType, // fuserid)
	 * 
	 * if (orderinfo.getFpayMethod() == 4 && orderinfo.getFonlinePay() == 1) {
	 * statementDao.saveStatement(orderinfo.getId().toString(),
	 * orderinfo.getNumber(), 1, order.getFreight(), -1, orderinfo.getCreator(),
	 * orderinfo.getFpayMethod(), shipperinfo.getVmiUserFid());// 货主明细 }
	 * statementDao.saveStatement(orderinfo.getId().toString(),
	 * orderinfo.getNumber(), 4, ss, 1, driverinfo.getId(), 0,
	 * driverinfo.getVmiUserFid());// 车主明细
	 *//** 收支明细表添加 End */
	/*
	
	*//** 转介绍奖励 Start */
	/*
	 * // HashMap<String, Object> map = new HashMap<String, // Object>(); //
	 * CL_UserRole driverinfo = iuserRoleDao.getById(2328); // CL_UserRole
	 * shipperinfo = iuserRoleDao.getById(2306); // 转介绍奖励暂停，他们具体金额还在修改，注释代码 BY
	 * CC 2016-05-16 START //
	 * if(driverinfo.getEndOrderTimes()==1)//当前订单的车主是第一次交易 // { //
	 * DongjingClient djcn = ServerContext.createVmiClient(); //
	 * djcn.setMethod("getInviter"); //
	 * djcn.setRequestProperty("fid",String.valueOf(driverinfo.getVmiUserFid()))
	 * ;//传当前司机，即被邀请人的ID // djcn.setRequestProperty("fapptype", "1"); //
	 * djcn.SubmitData(); // net.sf.json.JSONObject jo = //
	 * net.sf.json.JSONObject.fromObject(djcn.getResponse().getResultString());
	 * // JSONArray array = JSONArray.fromObject(jo.get("invite")); //
	 * if("null".equals(array.get(0).toString()))//如果没有推荐人 // { //
	 * map.put("msg", "No inviter"); // }else{ // Integer fuserroleid //
	 * =Integer.parseInt(JSONObject.fromObject(array.get(0)).get("fuserroleid").
	 * toString());//获取到邀请人 // Boolean flag=invitePrize(fuserroleid); //
	 * if(!flag){ // map.put("success", "false"); //
	 * map.put("msg",ServerContext.getMsg()); // return
	 * writeAjaxResponse(response, // JSONUtil.getJson(map)); // } // } // } //
	 * if(shipperinfo.getEndOrderTimes()==1)//当前订单的货主是第一次交易 // { //
	 * DongjingClient djcn = ServerContext.createVmiClient(); //
	 * djcn.setMethod("getInviter"); //
	 * djcn.setRequestProperty("fid",String.valueOf(shipperinfo.getVmiUserFid())
	 * );//传当前司机，即被邀请人的ID // djcn.setRequestProperty("fapptype", "1"); //
	 * djcn.SubmitData(); // net.sf.json.JSONObject jo = //
	 * net.sf.json.JSONObject.fromObject(djcn.getResponse().getResultString());
	 * // JSONArray array = JSONArray.fromObject(jo.get("invite")); //
	 * if("null".equals(array.get(0).toString()))//如果有推荐人 // { // map.put("msg",
	 * "No inviter"); // }else // { // Integer fuserroleid //
	 * =Integer.parseInt(JSONObject.fromObject(array.get(0)).get("fuserroleid").
	 * toString());//获取到邀请人 // Boolean flag=invitePrize(fuserroleid); //
	 * if(!flag){ // map.put("success", "false"); //
	 * map.put("msg",ServerContext.getMsg()); // return
	 * writeAjaxResponse(response, // JSONUtil.getJson(map)); // } // } // } //
	 * 转介绍奖励暂停，他们具体金额还在修改，注释代码 BY CC 2016-05-16 END
	 *//** 转介绍奖励 End */
	/*
	 * 
	 * // ================== 支付 接口开始 (转运费给司机)========= if
	 * (!ServerContext.cancelOrderPay(payUrl, payDate)) {
	 * orderDetailDao.updatePass(passAll.get(passAll.size() - 1).getId(), 0);
	 * map.put("success", "false"); map.put("msg", ServerContext.getMsg());
	 * throw new Exception("支付异常！"); // return writeAjaxResponse(response, //
	 * JSONUtil.getJson(map)); } // ====================支付结束
	 * =====================
	 * 
	 * map.put("success", "true"); map.put("msg", "已完成订单所有地址配送!"); return
	 * writeAjaxResponse(response, JSONUtil.getJson(map));
	 * 
	 * case 2: if (type != 3 && type != 4 && type != 2) { map.put("success",
	 * "false"); map.put("msg", "请升级app~!"); return writeAjaxResponse(response,
	 * JSONUtil.getJson(map)); } orderDao.updateStatusOnline(orderId, 2, new
	 * Date()); orderDao.updateStatusByOrderId(orderId, 4);
	 *//** 司机、货主成单次数更新； */
	/*
	 * CL_Order orderinfo1 = orderDao.getById(orderId); CL_UserRole driverinfo1
	 * = iuserRoleDao.getById(orderinfo1.getUserRoleId()); CL_UserRole
	 * shipperinfo1 = iuserRoleDao.getById(orderinfo1.getCreator());
	 * driverinfo1.setEndOrderTimes(driverinfo1.getEndOrderTimes() + 1);
	 * shipperinfo1.setEndOrderTimes(shipperinfo1.getEndOrderTimes() + 1); //
	 * 司机成单次数； iuserRoleDao.updateTimes(driverinfo1); // 货主成单次数；
	 * iuserRoleDao.updateTimes(shipperinfo1);
	 *//** 司机、货主成单次数更新； */
	/*
	
	*//** 转介绍奖励 Start */
	/*
	 * // HashMap<String, Object> map = new HashMap<String, // Object>(); //
	 * CL_UserRole driverinfo = iuserRoleDao.getById(2328); // CL_UserRole
	 * shipperinfo = iuserRoleDao.getById(2306); //
	 * if(driverinfo1.getEndOrderTimes()==1)//当前订单的车主是第一次交易 // { //
	 * DongjingClient djcn = ServerContext.createVmiClient(); //
	 * djcn.setMethod("getInviter"); //
	 * djcn.setRequestProperty("fid",String.valueOf(driverinfo1.getVmiUserFid())
	 * );//传当前司机，即被邀请人的ID // djcn.setRequestProperty("fapptype", "1"); //
	 * djcn.SubmitData(); // net.sf.json.JSONObject jo = //
	 * net.sf.json.JSONObject.fromObject(djcn.getResponse().getResultString());
	 * // JSONArray array = JSONArray.fromObject(jo.get("invite")); //
	 * if("null".equals(array.get(0).toString()))//如果有推荐人 // { // map.put("msg",
	 * "No inviter"); // }else{ // Integer fuserroleid //
	 * =Integer.parseInt(JSONObject.fromObject(array.get(0)).get("fuserroleid").
	 * toString());//获取到邀请人 // Boolean flag=invitePrize(fuserroleid); //
	 * if(!flag){ // map.put("success", "false"); // map.put("msg",
	 * ServerContext.getMsg()); // return writeAjaxResponse(response, //
	 * JSONUtil.getJson(map)); // } // } // } //
	 * if(shipperinfo1.getEndOrderTimes()==1)//当前订单的货主是第一次交易 // { //
	 * DongjingClient djcn = ServerContext.createVmiClient(); //
	 * djcn.setMethod("getInviter"); //
	 * djcn.setRequestProperty("fid",String.valueOf(shipperinfo1.getVmiUserFid()
	 * ));//传当前司机，即被邀请人的ID // djcn.setRequestProperty("fapptype", "1"); //
	 * djcn.SubmitData(); // net.sf.json.JSONObject jo = //
	 * net.sf.json.JSONObject.fromObject(djcn.getResponse().getResultString());
	 * // JSONArray array = JSONArray.fromObject(jo.get("invite")); //
	 * if("null".equals(array.get(0).toString()))//如果有推荐人 // { // map.put("msg",
	 * "No inviter"); // }else // { // Integer fuserroleid //
	 * =Integer.parseInt(JSONObject.fromObject(array.get(0)).get("fuserroleid").
	 * toString());//获取到邀请人 // Boolean flag=invitePrize(fuserroleid); //
	 * if(!flag){ // map.put("success", "false"); // map.put("msg",
	 * ServerContext.getMsg()); // return writeAjaxResponse(response, //
	 * JSONUtil.getJson(map)); // } // } // }
	 *//** 转介绍奖励 End *//*
						 * 
						 * map.put("success", "true"); map.put("msg",
						 * "已完成订单所有地址配送!"); return writeAjaxResponse(response,
						 * JSONUtil.getJson(map));
						 * 
						 * default: map.put("success", "false"); map.put("msg",
						 * "请升级app~!");
						 * System.out.println("+++++++++++++++++++++++++++");
						 * System.out.println(JSONUtil.getJson(map)); return
						 * writeAjaxResponse(response, JSONUtil.getJson(map)); }
						 * } else { map.put("success", "true"); map.put("msg",
						 * "验证通过！"); System.out.println("++++++++" +
						 * "+++++++++++++++++++");
						 * System.out.println(JSONUtil.getJson(map)); return
						 * writeAjaxResponse(response, JSONUtil.getJson(map)); }
						 * } else { map.put("success", "false"); map.put("msg",
						 * "验证失败！");
						 * System.out.println("+++++++++++++++++++++++++++");
						 * System.out.println(JSONUtil.getJson(map)); return
						 * writeAjaxResponse(response, JSONUtil.getJson(map)); }
						 * }
						 */

	/** 转介绍方法 Start */
	/*
	 * private Boolean invitePrize(int fid) { CL_UserRole user =
	 * iuserRoleDao.getById(fid); BigDecimal fbonus = new BigDecimal(5); if
	 * (user.getRoleId() == 1)// 货主奖励好运券 { Calendar c = Calendar.getInstance();
	 * c.setTime(new Date()); // 设置当前日期 c.add(Calendar.MONTH, 1); // 日期月份加1 Date
	 * enddate = c.getTime(); // 结果 System.err.println(enddate); CL_Coupons
	 * coupon = new CL_Coupons(); coupon.setType(5); coupon.setCreateTime(new
	 * Date()); coupon.setStartTime(new Date()); coupon.setEndTime(enddate);
	 * coupon.setDollars(fbonus); coupon.setCompareDollars(new BigDecimal(10));
	 * coupon.setIsEffective(1);// 有效 coupon.setIsOverdue(0);// 未过期
	 * coupon.setDescribes("推广转介绍奖励"); couponsDao.save(coupon); Integer
	 * couponsId = coupon.getId();
	 * 
	 * CL_CouponsDetail detail = new CL_CouponsDetail();
	 * detail.setCouponsId(couponsId); detail.setIsUse(0);
	 * detail.setIsOverdue(0);// 未过期 detail.setUserRoleId(fid);
	 * detail.setCreateTime(new Date()); this.couponsDetailDao.save(detail);
	 * 
	 * CL_Message message = new CL_Message(); message.setReceiver(fid);
	 * message.setTitle("新好运券消息");
	 * message.setContent("您邀请的用户完成了第一笔订单,您获取到了一张面额为：" + fbonus +
	 * "好运券,请注意查收并使用!"); message.setType(1);// 货主 message.setCreateTime(new
	 * Date()); this.messageDao.save(message); //
	 * CsclPushUtil.SendPushToAllSound(String.valueOf(user.getId()), //
	 * "CPS:您邀请的用户完成了第一笔订单,您获取到了一张面额为："+fbonus+"好运券,请注意查收并使用!"); //
	 * CsclPushUtil.SendPushToAllSound( "2921", //
	 * "CPS:您邀请的用户完成了第一笔订单,您获取到了一张面额为："+fbonus+"好运券,请注意查收并使用!"); } else if
	 * (user.getRoleId() == 2)// 车主奖励余额 { HashMap<String, String> inviteMap =
	 * new HashMap<String, String>(); inviteMap =
	 * inviteParams(user.getVmiUserPhone(), 123456, fbonus, "Add"); String
	 * inviteInfo = inviteMap.get("inviteInfo"); if
	 * (!ServerContext.checkWithdrawStatus(inviteInfo)) { return false; }
	 * statementDao.saveStatement(null, null, 7, fbonus, 1, user.getId(), 0,
	 * user.getVmiUserFid()); } return true; }
	 */

	/** 转介绍方法 End */

	private HashMap<String, String> inviteParams(String phone, int newid, BigDecimal amount, String type) {
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
			params.put("inviteInfo", Base64.encode(jo.toString().getBytes()));
			return params;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
