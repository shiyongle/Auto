package com.pc.appInterface.orderRecord;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.org.rapid_framework.page.Page;

import com.pc.controller.BaseController;
import com.pc.dao.Car.ICarDao;
import com.pc.dao.UserRole.impl.UserRoleDao;
import com.pc.dao.addto.IaddtoDao;
import com.pc.dao.couponsDetail.ICouponsDetailDao;
import com.pc.dao.financeStatement.impl.FinanceStatementDaoImpl;
import com.pc.dao.message.ImessageDao;
import com.pc.dao.order.impl.OrderDao;
import com.pc.dao.orderDetail.impl.OrderDetailDao;
import com.pc.dao.rule.IRuleDao;
import com.pc.dao.select.IUtilOptionDao;
import com.pc.model.CL_Addto;
import com.pc.model.CL_Order;
import com.pc.model.CL_OrderDetail;
import com.pc.model.Util_Option;
import com.pc.query.order.OrderQuery;
import com.pc.query.orderDetail.OrderDetailQuery;
import com.pc.util.JSONUtil;
import com.pc.util.LatitudeLongitudeDI;
import com.pc.util.ServerContext;

@Controller
public class AppOrderRecordControll extends BaseController {
	public static String msg;
	@Resource
	private IaddtoDao iaddtoDao;

	@Resource

	private IUtilOptionDao optionDao;
	@Resource
	private IRuleDao ruleDao;
	@Resource
	private OrderDao orderDao;
	@Resource
	private ICarDao carDao;
	@Resource
	private OrderDetailDao orderDetailDao;
	@Resource
	private UserRoleDao userRoleDao;

	@Resource
	private FinanceStatementDaoImpl statementDao;

	@Resource
	private ICouponsDetailDao couponsDetailDao;
	@Resource
	private ImessageDao messageDao;

	private OrderQuery orderQuery;

	public OrderQuery getOrderQuery() {
		return orderQuery;
	}

	public void setOrderQuery(OrderQuery orderQuery) {
		this.orderQuery = orderQuery;
	}

	private OrderDetailQuery orderDetailQuery;

	public OrderDetailQuery getOrderDetailQuery() {
		return orderDetailQuery;
	}

	public void setOrderDetailQuery(OrderDetailQuery orderDetailQuery) {
		this.orderDetailQuery = orderDetailQuery;
	}

	/*** 查询货主订单记录信息 */
	@RequestMapping("/app/orderRecord/newloadRecord")
	public String newloadRecord(HttpServletRequest request, HttpServletResponse response) throws IOException {
		Integer userId, type, status, pageNum, pageSize;
		List<Integer> ls = new ArrayList<Integer>();
		List<Map<String, Object>> maps = new ArrayList<>();
		// String searchKey =request.getParameter("keyword");
		HashMap<String, Object> map = new HashMap<String, Object>();
		if (request.getParameter("userId") == null || "".equals(request.getParameter("userId"))) {
			map.put("success", "false");
			map.put("msg", "未登录！");
			return writeAjaxResponse(response, JSONUtil.getJson(map));
		} else {
			userId = Integer.parseInt(request.getParameter("userId"));
		}
		if (request.getParameter("type") == null || "".equals(request.getParameter("type"))) {
			map.put("success", "false");
			map.put("msg", "无法获知订单类型");
			return writeAjaxResponse(response, JSONUtil.getJson(map));
		} else {
			type = Integer.parseInt(request.getParameter("type"));
		}
		if (request.getParameter("status") == null || "".equals(request.getParameter("status"))) {
			status = null;
		} else {
			status = Integer.parseInt(request.getParameter("status"));
		}
		if (request.getParameter("pagenum") == null || "".equals(request.getParameter("pagenum"))) {
			map.put("success", "false");
			map.put("msg", "无法获知第几页");
			return writeAjaxResponse(response, JSONUtil.getJson(map));
		} else {
			pageNum = Integer.valueOf(request.getParameter("pagenum"));
		}
		if (request.getParameter("pagesize") == null || "".equals(request.getParameter("pagesize"))) {
			map.put("success", "false");
			map.put("msg", "无法获知每页显示多少条");
			return writeAjaxResponse(response, JSONUtil.getJson(map));
		} else {
			pageSize = Integer.valueOf(request.getParameter("pagesize"));
		}
		orderQuery = newQuery(OrderQuery.class, null);
		if (pageNum != null) {
			orderQuery.setPageNumber(pageNum);
		}
		if (pageSize != null) {
			orderQuery.setPageSize(pageSize);
		}
		orderQuery.setCreator(userId);
		orderQuery.setType(type);
		orderQuery.setStatus(status);
		// orderQuery.setSearchKey(searchKey);
		// if(searchKey!=null&&!"".equals(searchKey)){
		// List<Integer> a =userRoleDao.getByVmiUserName(searchKey);
		// Integer[] b=new Integer[a.size()];
		// for(int i=0,len=a.size();i<len-1;i++){
		// b[i]=a.get(i);
		// }
		// orderQuery.setDrivers(b);
		// }
		Page<CL_Order> page = orderDao.findPage(orderQuery);
		List<CL_Order> list = page.getResult();
		// CL_Rule rule = this.ruleDao.getOneByType();
		// if(rule ==null){
		// map.put("success", "false");
		// map.put("msg","司机计费规则丢失,请联系客服！");
		// return writeAjaxResponse(response, JSONUtil.getJson(map));
		// }
		for (int i = 0; i < list.size(); i++) {
			HashMap<String, Object> mas = new HashMap<String, Object>();
			if (list.get(i).getType() != 2) {
				List<Util_Option> ups = optionDao.getCarTypeByOrderIdName(list.get(i).getId());
				String carSpecName = ups.get(0).getOptionName();
				String carOtherName = ups.get(0).getOptionCarOtherName();
				for (Util_Option option : ups) {
					if (option.getOptionCarTypeName() != null && !option.getOptionCarTypeName().equals("")) {
						if (!carSpecName.contains(option.getOptionCarTypeName())) {
							carSpecName = carSpecName + " " + option.getOptionCarTypeName();
						}
					}

					if (carOtherName != null && "".equals(carOtherName)
							&& !carOtherName.contains(option.getOptionCarOtherName())
							&& !"".equals(option.getOptionCarOtherName()) && option.getOptionCarOtherName() != null) {
						carOtherName = carOtherName + " " + option.getOptionCarOtherName();
					}
				}
				if (carOtherName == null || "".equals(carOtherName)) {
					carSpecName = carSpecName + " " + list.get(i).getGoodsTypeName();
				} else if (carOtherName != null && !"".equals(carOtherName)) {
					carSpecName = carSpecName + " " + carOtherName + " " + list.get(i).getGoodsTypeName();
				}
				mas.put("carSpecName", carSpecName);
			}
			CL_OrderDetail detailOne = this.orderDetailDao.getByOrderIdForDeliverAddress(list.get(i).getId());
			if (detailOne == null) {
				map.put("success", "false");
				map.put("msg", "订单编号为: " + list.get(i).getNumber() + "   数据遗失请联系客服");
				return writeAjaxResponse(response, JSONUtil.getJson(map));
			}
			if (list.get(i).getType() != 3) {
				CL_OrderDetail detailTwo = this.orderDetailDao.getByOrderIdForConsigneeAddress(list.get(i).getId());
				if (detailTwo == null) {
					map.put("success", "false");
					map.put("msg", "订单编号为: " + list.get(i).getNumber() + "  数据遗失请联系客服");
					return writeAjaxResponse(response, JSONUtil.getJson(map));
				}
				mas.put("recAddress", detailTwo.getAddressName());
				mas.put("mileage", list.get(i).getMileage());
			} else if (list.get(i).getType() == 3) {
				mas.put("recAddress", null);
				mas.put("mileage", null);
			}
			mas.put("number", list.get(i).getNumber());
			mas.put("type", list.get(i).getType());
			mas.put("takeAddress", detailOne.getAddressName());
			mas.put("goodsTypeName", list.get(i).getGoodsTypeName());
			mas.put("freight", list.get(i).getFreight());
			mas.put("status", list.get(i).getStatus());
			mas.put("loadedTimeString", list.get(i).getLoadedTimeString());
			maps.add(mas);
		}
		HashMap<String, Object> m = new HashMap<String, Object>();
		m.put("success", "true");
		m.put("total", page.getTotalCount());
		m.put("data", maps);
		return writeAjaxResponse(response, JSONUtil.getJson(m));
	}

	/***
	 * 更新订单记录信息增值服务 下单完成后确认支付接口
	 */
	/*@RequestMapping("/app/orderRecord/newupdataRecord")
	public String newupdataRecord(HttpServletRequest request, HttpServletResponse response) throws IOException {
		Integer orderId = null, payMethod = null, freceiptSave;
		BigDecimal purposeAmount;
		String user = "", data = "", fincrementServe = "", url = "", checkDate = "", payDate = "", checkUrl = "",
				payDateUrl = "";
		boolean existCoupons = false;
		HashMap<String, Object> map = new HashMap<String, Object>();

		*//**
		 * 临时控制支付 String temp = "0"; if(request.getParameter("temp")!=null &&
		 * !"".equals(request.getParameter("temp"))){ temp =
		 * request.getParameter("temp");
		 * if("0".equals(request.getParameter("temp"))){ map.put("success",
		 * "false"); map.put("msg","测试停止!"); return writeAjaxResponse(response,
		 * JSONUtil.getJson(map)); } }
		 *//*

		if (request.getParameter("orderId") == null || "".equals(request.getParameter("orderId"))) {
			map.put("success", "false");
			map.put("msg", "请先选择订单");
			return writeAjaxResponse(response, JSONUtil.getJson(map));
		} else {
			orderId = Integer.parseInt(request.getParameter("orderId"));
		}
		// if(request.getParameter("freight")==null||"".equals(request.getParameter("freight"))){
		// map.put("success", "false");
		// map.put("msg","运费不存在,请联系客服");
		// return writeAjaxResponse(response, JSONUtil.getJson(map));
		// }else{
		// BigDecimal
		// freight=BigDecimal.valueOf(Double.parseDouble(request.getParameter("freight").toString()));
		// if(orderDao.getById(orderId).getFreight().compareTo(freight)!=0){
		// map.put("success", "false");
		// map.put("msg","运费不一致,请联系客服");
		// return writeAjaxResponse(response, JSONUtil.getJson(map));
		// }
		// }
		if (request.getParameter("payMethod") == null || "".equals(request.getParameter("payMethod"))) {
			map.put("success", "false");
			map.put("msg", "支付方式有问题");
			return writeAjaxResponse(response, JSONUtil.getJson(map));
		} else {
			payMethod = Integer.valueOf(request.getParameter("payMethod").toString());
		}

		// if(request.getParameter("url")==null ||
		// "".equals(request.getParameter("url"))){
		// map.put("success", "false");
		// map.put("msg","支付系统未支付成功，请重新支付,数据有误");
		// return writeAjaxResponse(response, JSONUtil.getJson(map));
		// }else{
		// url=request.getParameter("url");
		// }

		if (request.getParameter("fincrementServe") == null || "".equals(request.getParameter("fincrementServe"))) {
			fincrementServe = null;
		} else {
			fincrementServe = request.getParameter("fincrementServe");
		}
		if (request.getParameter("freceiptSave") == null || "".equals(request.getParameter("freceiptSave"))) {
			freceiptSave = 0;
		} else {
			freceiptSave = Integer.valueOf(request.getParameter("freceiptSave").toString());
		}

		if (request.getParameter("purposeAmount") == null || "".equals(request.getParameter("purposeAmount"))) {
			purposeAmount = new BigDecimal(0);
		} else {
			purposeAmount = BigDecimal.valueOf(Double.parseDouble(request.getParameter("purposeAmount").toString()));
		}
		CL_Order order = this.orderDao.getById(orderId);
		// 代收货款控制；
		if (order.getProtocolType() == 0) {
			if (purposeAmount.compareTo(new BigDecimal(10000)) > 0) {
				map.put("success", "false");
				map.put("msg", "临时用车限额不能大于10000元！");
				return writeAjaxResponse(response, JSONUtil.getJson(map));
			}
		} else {
			if (purposeAmount.compareTo(new BigDecimal(20000)) > 0) {
				map.put("success", "false");
				map.put("msg", "协议用车限额不能大于20000元！");
				return writeAjaxResponse(response, JSONUtil.getJson(map));
			}
		}
		// 校验 支付
		orderDetailDao.updateFreaByOrderId(orderId, freceiptSave);
		order.setFincrementServe(fincrementServe);
		order.setPurposeAmount(purposeAmount);
		orderDao.updateServerById(order);
		if (order == null) {
			map.put("success", "false");
			map.put("msg", "该订单不存在，请刷新");
			return writeAjaxResponse(response, JSONUtil.getJson(map));
		}
		// 1代付款，6已取消
		if (order.getStatus() != 1 && order.getStatus() != 6) {
			map.put("success", "false");
			map.put("msg", "该订单已经付款，不能重复支付");
			return writeAjaxResponse(response, JSONUtil.getJson(map));
		}
		// 4运费到付，5月结
		if (payMethod != 4 && payMethod != 5) {
			if (request.getParameter("checkUrl") == null || "".equals(request.getParameter("checkUrl"))) {
				map.put("success", "false");
				map.put("msg", "校验参数有问题，请重新支付,数据有误");
				return writeAjaxResponse(response, JSONUtil.getJson(map));
			} else {
				// checkUrl=request.getParameter("checkUrl")+"?&XDEBUG_SESSION_START=17402";
				checkUrl = request.getParameter("checkUrl");
			}
			// 校验第三方支付充值操作是否成功 ，余额支付不需要校验 BY CC start
			if (payMethod != 0 && payMethod != 3) {
				if (request.getParameter("checkDate2") == null || "".equals(request.getParameter("checkDate2"))) {
					map.put("success", "false");
					map.put("msg", "校验充值参数有问题，请重新支付,数据有误");
					return writeAjaxResponse(response, JSONUtil.getJson(map));
				} else {
					checkDate = request.getParameter("checkDate2");
				}
				if (!ServerContext.cancelOrderPay(checkUrl, checkDate)) {
					map.put("success", "false");
					map.put("msg", ServerContext.getMsg());
					return writeAjaxResponse(response, JSONUtil.getJson(map));
				}
			}
			// 校验第三方支付充值操作是否成功 ，余额支付不需要校验 BY CC end

			// 校验订单是否用余额支付过;
			if (request.getParameter("checkDate") == null || "".equals(request.getParameter("checkDate"))) {
				map.put("success", "false");
				map.put("msg", "校验参数有问题，请重新支付,数据有误");
				return writeAjaxResponse(response, JSONUtil.getJson(map));
			} else {
				checkDate = request.getParameter("checkDate");
			}
			if (ServerContext.cancelOrderPay(checkUrl, checkDate)) {
				map.put("success", "false");
				map.put("msg", ServerContext.getMsg());
				return writeAjaxResponse(response, JSONUtil.getJson(map));
			}
			// 校验订单是否用余额支付过;

		}

		if (order.getStatus() == 6) {
			Date date = new Date();
			long ss = date.getTime() - order.getCreateTime().getTime();
			long time = ss - 60 * 60000;
			if (time <= 0) {
				map.put("success", "false");
				map.put("msg", "订单已自动取消,支付金额已转入余额！");
				return writeAjaxResponse(response, JSONUtil.getJson(map));
			}
		}

		*//**** ==============若选择优惠券=============逻辑判断开始============ ***//*
		*//** 20160715 cd 控制订单支付失败，好运券被其它订单使用导致失败订单再次成功使用好运券 *//*
		CL_CouponsDetail cdetail = null;
		if (request.getParameter("cdetailId") != null && !"".equals(request.getParameter("cdetailId"))) {
			existCoupons = true;
			Integer couponsDetailId = Integer.parseInt(request.getParameter("cdetailId"));
			order.setCouponsDetailId(couponsDetailId);
			cdetail = this.couponsDetailDao.getById(couponsDetailId);
			if (cdetail != null) {
				if (cdetail.getIsUse() == 1 || cdetail.getIsOverdue() == 1) {
					map.put("success", "false");
					map.put("msg", "好运券已使用，运费将转入余额！");
					return writeAjaxResponse(response, JSONUtil.getJson(map));
				}
			}
		}
		*//** 控制订单支付失败，好运券被其它订单使用导致失败订单再次成功使用好运券 *//*

		*//** 20160715 cd 好运券或协议变更导致再次支付金额不致，直接取消当前订单，并把运费转余额！ *//*
		if (request.getParameter("money") != null && !request.getParameter("money").equals("")) {
			BigDecimal money = new BigDecimal("0");
			BigDecimal freight = new BigDecimal("0");

			money = new BigDecimal(request.getParameter("money"));
			freight = new BigDecimal(request.getParameter("freight"));

			if (freight.compareTo(money) != 0) {
				map.put("success", "false");
				if (existCoupons) {
					map.put("msg", "好运券已使用，运费将转入余额！");
				} else {
					map.put("msg", "可能协议变更，订单金额有误！");
				}
				return writeAjaxResponse(response, JSONUtil.getJson(map));
			}
		}
		*//** 好运券或协议变更导致再次支付金额不致，直接取消当前订单，并把运费转余额！ *//*

		// 代收货款控制；
		// 增加付款状态校验，防止直接调用接口修改付款状态 BY CC 2016-03-03 START
		if (payMethod != 4 && payMethod != 5) {
			if (request.getParameter("payDate") == null || "".equals(request.getParameter("payDate"))) {
				map.put("success", "false");
				map.put("msg", "支付系统未支付成功，请重新支付,数据有误");
				return writeAjaxResponse(response, JSONUtil.getJson(map));
			} else {
				payDate = request.getParameter("payDate");
			}
			if (request.getParameter("payDateUrl") == null || "".equals(request.getParameter("payDateUrl"))) {
				map.put("success", "false");
				map.put("msg", "校验参数有问题，请重新支付,数据有误");
				return writeAjaxResponse(response, JSONUtil.getJson(map));
			} else {
				payDateUrl = request.getParameter("payDateUrl");
			}
			if (!ServerContext.cancelOrderPay(payDateUrl, payDate)) {
				map.put("success", "false");
				map.put("msg", ServerContext.getMsg());
				return writeAjaxResponse(response, JSONUtil.getJson(map));
			}
		} else {
			// 20160806 cd 增加倒付不能使用好运券控制;
			if (payMethod == 4 && existCoupons) {
				map.put("success", "false");
				map.put("msg", "倒付不支持好运券！");
				return writeAjaxResponse(response, JSONUtil.getJson(map));
			}
		}

		// 增加付款状态校验，防止直接调用接口修改付款状态 BY CC 2016-03-03 END
		order.setFpayMethod(payMethod);
		order.setStatus(2);
		order.setFpayTime(new Date());
		BigDecimal dos = new BigDecimal(0);
		*//**** ==============若选择优惠券=============逻辑判断开始============ ***//*
		if (existCoupons) {
			if (cdetail != null) {
				dos = cdetail.getDollars();
			}
			cdetail.setIsUse(1);
			this.couponsDetailDao.update(cdetail);
		}
		*//**** ==============若选择优惠券=============逻辑判断结束============ ***//*
		this.orderDao.update(order);
		// TODO:向满足条件的司机进行消息推送
		*//***
		 * BY CC 2016-03-05
		 * 由于后台数据异常，不同步，推送代码执行报错，导致实际支付成功，当时前端收到异常信息后提示支付失败，抢单数据有单独接口，
		 * 临时通过加try处理，后面再安排排查问题解决。
		 *//*
		try {
			CL_OrderDetail detail = this.orderDetailDao.getByOrderIdForDeliverAddress(orderId);
			if (order.getType() == 1 && order.getProtocolType() == 0) {
				// 获取订单对应的所需车型和规格
				List<Util_Option> ls = this.optionDao.getCarTypeByOrderId(orderId);
				if (ls.size() > 0 && ls.size() == 1) {
					if (("任意车型").equals(ls.get(0).getOptionName())) {
						List<CL_Car> cars = this.carDao.getByCarSpecId(ls.get(0).getId());
						if (cars.size() > 0) {
							Boolean ispass = false;
							for (int a = cars.size() - 1; a >= 0; a--) {
								ispass = getDriverPositionFun(cars.get(a).getUserRoleId(), detail);
								if (ispass == false) {
									cars.remove(a);
								}
								// 司机经纬度需要替换 记得！！
								// if(cars.get(a).getLatitude()==null ||
								// "".equals(cars.get(a).getLatitude())){
								// cars.remove(a);
								// continue;
								// }
								// if((new
								// BigDecimal(effectiveDistance)).compareTo(LatitudeLongitudeDI.GetDistance(Double.parseDouble(detail.getLatitude()),
								// Double.parseDouble(detail.getLongitude()),
								// cars.get(a).getLatitude().doubleValue(),
								// cars.get(a).getLongitude().doubleValue()))==-1){
								// cars.remove(a);
								// }
							}
						}
						if (cars.size() > 0) {
							String auser = "";
							CL_Message Amessage = null;
							for (CL_Car car : cars) {
								Amessage = new CL_Message();
								auser += String.valueOf(car.getUserRoleId()) + ",";
								Amessage.setCreateTime(new Date());
								Amessage.setCreator(order.getCreator().toString());
								Amessage.setContent("CPS:您有新的订单,可以进行抢单了!");
								Amessage.setReceiver(car.getUserRoleId());
								Amessage.setType(2);
								Amessage.setTitle("一路好运");
								messageDao.save(Amessage);
							}
							// CsclPushUtil.SendPushToAllSound(auser,
							// "CPS:您有新的订单,可以进行抢单了!");
						}
					} else {
						List<CL_Car> cars2 = this.carDao.getByCarSpecIdAndTypeId(ls.get(0).getId(),
								ls.get(0).getOptionId());
						if (cars2.size() > 0) {
							Boolean ispass = false;
							for (int b = cars2.size() - 1; b >= 0; b--) {
								// 司机缓存拿的经纬度
								ispass = getDriverPositionFun(cars2.get(b).getUserRoleId(), detail);
								if (ispass == false) {
									cars2.remove(b);
								}
							}
						}
						if (cars2.size() > 0) {
							String buser = "";
							CL_Message Bmessage = null;
							for (CL_Car car : cars2) {
								buser += String.valueOf(car.getUserRoleId()) + ",";
								Bmessage = new CL_Message();
								Bmessage.setCreateTime(new Date());
								Bmessage.setCreator(order.getCreator().toString());
								Bmessage.setContent("CPS:您有新的订单,可以进行抢单了!");
								Bmessage.setReceiver(car.getUserRoleId());
								Bmessage.setType(2);
								Bmessage.setTitle("一路好运");
								messageDao.save(Bmessage);
							}
							// CsclPushUtil.SendPushToAllSound(buser,
							// "CPS:您有新的订单,可以进行抢单了!");
						}
					}
				} else if (ls.size() > 0) {
					for (Util_Option option : ls) {
						List<CL_Car> cars3 = this.carDao.getByCarSpecIdAndTypeId(option.getId(), option.getOptionId());
						if (cars3.size() > 0) {
							Boolean ispass = false;
							for (int c = cars3.size() - 1; c >= 0; c--) {
								ispass = getDriverPositionFun(cars3.get(c).getUserRoleId(), detail);
								if (ispass == false) {
									cars3.remove(c);
								}
							}
						}
						if (cars3.size() > 0) {
							String cuser = "";
							CL_Message Cmessage = null;
							for (CL_Car car : cars3) {
								cuser += String.valueOf(car.getUserRoleId()) + ",";
								Cmessage = new CL_Message();
								Cmessage.setCreateTime(new Date());
								Cmessage.setCreator(order.getCreator().toString());
								Cmessage.setContent("CPS:您有新的订单,可以进行抢单了!");
								Cmessage.setReceiver(car.getUserRoleId());
								Cmessage.setType(2);
								Cmessage.setTitle("一路好运");
								messageDao.save(Cmessage);
							}
							// CsclPushUtil.SendPushToAllSound(cuser,
							// "CPS:您有新的订单,可以进行抢单了!");
						}
					}
				}
			}
			// else{
			// CarQuery carQuery =new CarQuery();
			// List<CL_Car> carlist = this.carDao.find(carQuery);
			// for(int i = carlist.size()-1;i>=0;i--){
			// Boolean ispass=false;
			// ispass=getDriverPositionFun(carlist.get(i).getUserRoleId(),
			// detail);
			// if(ispass==false){
			// carlist.remove(i);
			// }
			//// if(carlist.get(i).getLatitude()==null||"".equals(carlist.get(i).getLatitude())){
			//// carlist.remove(i);
			//// continue;
			//// }
			//// if((new
			// BigDecimal(effectiveDistance)).compareTo(LatitudeLongitudeDI.GetDistance(Double.parseDouble(detail.getLatitude()),
			// Double.parseDouble(detail.getLongitude()),
			// carlist.get(i).getLatitude().doubleValue(),
			// carlist.get(i).getLongitude().doubleValue()))==-1){
			//// carlist.remove(i);
			//// }
			// }
			// if(carlist.size()>0){
			// user="";
			// CL_Message message=null;
			// for(CL_Car carafter :carlist){
			// user+=String.valueOf(carafter.getUserRoleId())+",";
			// message=new CL_Message();
			// message.setCreateTime(new Date());
			// message.setCreator(order.getCreator().toString());
			// message.setContent("CPS:您有新的订单,可以进行抢单了!");
			// message.setReceiver(carafter.getUserRoleId());
			// message.setType(2);
			// message.setTitle("一路好运");
			// messageDao.save(message);
			// }
			// CsclPushUtil.SendPushToAllSound(user, "CPS:您有新的订单,可以进行抢单了!");
			// }
			// }
		} catch (Exception e) {
			e.printStackTrace();
		}
		String fuserid = userRoleDao.getById(order.getCreator()).getVmiUserFid();
		// 下单支付
		if (order.getFpayMethod() != 4) {
			if (dos.compareTo(new BigDecimal(0)) == 0) {
				statementDao.saveStatement(order.getId().toString(), order.getNumber(), 1, order.getFreight(), -1,
						order.getCreator(), order.getFpayMethod(), fuserid);
			} else {
				statementDao.saveStatement(order.getId().toString(), order.getNumber(), 1,
						order.getFreight().subtract(dos), -1, order.getCreator(), order.getFpayMethod(), fuserid);
			}
		}
		map.put("success", "true");
		map.put("msg", "操作成功！");
		System.out.println(JSONUtil.getJson(map));
		return writeAjaxResponse(response, JSONUtil.getJson(map));
	}*/

	//
	// /***更新订单记录信息增值服务*/
	// @RequestMapping("/app/orderRecord/updataRecord")
	// @Transactional
	// public String updataRecord(HttpServletRequest request,HttpServletResponse
	// response) throws IOException{
	// Integer orderId = null ,upload,receipt,collection,isfreightcollect;
	// String user="",data="";
	// HashMap<String,Object> map =new HashMap<String,Object>();
	// if(request.getParameter("orderId")==null||"".equals(request.getParameter("orderId"))){
	// map.put("success", "false");
	// map.put("msg","请先选择订单");
	// return writeAjaxResponse(response, JSONUtil.getJson(map));
	// }
	// else{
	// orderId=Integer.parseInt(request.getParameter("orderId"));
	// }
	// if(request.getParameter("data")==null ||
	// "".equals(request.getParameter("data"))){
	// map.put("success", "false");
	// map.put("msg","支付系统未支付成功，请重新支付,数据有误");
	// return writeAjaxResponse(response, JSONUtil.getJson(map));
	// }else{
	// data=request.getParameter("data");
	// }
	// if(request.getParameter("upload")==null ||
	// "".equals(request.getParameter("upload"))){
	// upload=0;
	// }else{
	// upload=Integer.parseInt(request.getParameter("upload"));
	// }
	// if(request.getParameter("receipt")==null ||
	// "".equals(request.getParameter("receipt"))){
	// receipt=0;
	// }else{
	// receipt=Integer.parseInt(request.getParameter("receipt"));
	// }
	// if(request.getParameter("collection")==null ||
	// "".equals(request.getParameter("collection"))){
	// collection=0;
	// }else{
	// collection=Integer.parseInt(request.getParameter("collection"));
	// }
	// if(request.getParameter("isfreightcollect")==null ||
	// "".equals(request.getParameter("isfreightcollect"))){
	// isfreightcollect=0;
	// }else{
	// isfreightcollect=Integer.parseInt(request.getParameter("isfreightcollect"));
	// }
	// CL_Order order=this.orderDao.getById(orderId);
	// //增加付款状态校验，防止直接调用接口修改付款状态 BY CC 2016-03-03 START
	// if(order==null){
	// map.put("success", "false");
	// map.put("msg","该订单不存在，请刷新");
	// return writeAjaxResponse(response, JSONUtil.getJson(map));
	// }
	// if(order.getStatus()!=1){
	// map.put("success", "false");
	// map.put("msg","该订单已经付款，不能重复支付");
	// System.out.println(JSONUtil.getJson(map));
	// return writeAjaxResponse(response, JSONUtil.getJson(map));
	// }
	// System.out.println("检索是否支付成功:"+ServerContext.checkPayStatus(data));
	// if(!ServerContext.checkPayStatus(data)){
	// map.put("success", "false");
	// map.put("msg","支付系统未支付成功，请重新支付");
	// return writeAjaxResponse(response, JSONUtil.getJson(map));
	// }
	// //增加付款状态校验，防止直接调用接口修改付款状态 BY CC 2016-03-03 END
	// order.setUpload(upload);//是否勾选装卸
	// order.setReceipt(receipt);//是否勾选回单
	// order.setCollection(collection);//是否勾选代收款
	// order.setIsFreightCollect(isfreightcollect);//是否运费到付
	// if(request.getParameter("pamount")!=null &&
	// !"".equals(request.getParameter("pamount"))){
	// order.setPurposeAmount(new BigDecimal(request.getParameter("pamount")));
	// }
	// order.setStatus(2);
	// /****==============若选择优惠券=============逻辑判断开始============***/
	// if(request.getParameter("cdetailId")!=null &&
	// !"".equals(request.getParameter("cdetailId"))){
	// Integer couponsDetailId
	// =Integer.parseInt(request.getParameter("cdetailId"));
	// order.setCouponsDetailId(couponsDetailId);
	// CL_CouponsDetail cdetail =
	// this.couponsDetailDao.getById(couponsDetailId);
	// cdetail.setIsUse(1);
	// this.couponsDetailDao.update(cdetail);
	// }
	// /****==============若选择优惠券=============逻辑判断结束============***/
	// this.orderDao.update(order);
	// //TODO:向满足条件的司机进行消息推送
	// /***BY CC 2016-03-05
	// * 由于后台数据异常，不同步，推送代码执行报错，导致实际支付成功，当时前端收到异常信息后提示支付失败，抢单数据有单独接口，
	// * 临时通过加try处理，后面再安排排查问题解决。
	// */
	// try{
	// int effectiveDistance = 20 ;
	// CL_OrderDetail detail =
	// this.orderDetailDao.getByOrderIdForDeliverAddress(orderId);
	// if(order.getType()==1){
	// //获取订单对应的所需车型和规格
	// List<Util_Option> ls = this.optionDao.getCarTypeByOrderId(orderId);
	// if(ls.size()>0 && ls.size()==1){
	// if(("任意车型").equals(ls.get(0).getOptionName())){
	// List<CL_Car> cars = this.carDao.getByCarSpecId(ls.get(0).getId());
	// if(cars.size()>0){
	// for(int a = cars.size()-1;a>=0;a--){
	// if(cars.get(a).getLatitude()==null ||
	// "".equals(cars.get(a).getLatitude())){
	// cars.remove(a);
	// continue;
	// }
	// if((new
	// BigDecimal(effectiveDistance)).compareTo(LatitudeLongitudeDI.GetDistance(Double.parseDouble(detail.getLatitude()),
	// Double.parseDouble(detail.getLongitude()),
	// cars.get(a).getLatitude().doubleValue(),
	// cars.get(a).getLongitude().doubleValue()))==-1){
	// cars.remove(a);
	// }
	// }
	// }
	// if(cars.size()>0){
	// String auser="";
	// CL_Message Amessage=null;
	// for(CL_Car car :cars){
	// Amessage=new CL_Message();
	// auser+=String.valueOf(car.getUserRoleId())+",";
	// Amessage.setCreateTime(new Date());
	// Amessage.setCreator(order.getCreator().toString());
	// Amessage.setContent("CPS:您有新的订单,可以进行抢单了!");
	// Amessage.setReceiver(car.getUserRoleId());
	// Amessage.setType(2);
	// Amessage.setTitle("一路好运");
	// messageDao.save(Amessage);
	// }
	// CsclPushUtil.SendPushToAllSound(auser, "CPS:您有新的订单,可以进行抢单了!");
	// }
	// }else{
	// List<CL_Car> cars2 =
	// this.carDao.getByCarSpecIdAndTypeId(ls.get(0).getId(),
	// ls.get(0).getOptionId());
	// if(cars2.size()>0){
	// for(int b = cars2.size()-1;b>=0;b--){
	// if(cars2.get(b).getLatitude()==null ||
	// "".equals(cars2.get(b).getLatitude())){
	// cars2.remove(b);
	// continue;
	// }
	// if((new
	// BigDecimal(effectiveDistance)).compareTo(LatitudeLongitudeDI.GetDistance(Double.parseDouble(detail.getLatitude()),
	// Double.parseDouble(detail.getLongitude()),
	// cars2.get(b).getLatitude().doubleValue(),
	// cars2.get(b).getLongitude().doubleValue()))==-1){
	// cars2.remove(b);
	// }
	// }
	// }
	// if(cars2.size()>0){
	// String buser="";
	// CL_Message Bmessage=null;
	// for(CL_Car car :cars2){
	// buser+=String.valueOf(car.getUserRoleId())+",";
	// Bmessage=new CL_Message();
	// Bmessage.setCreateTime(new Date());
	// Bmessage.setCreator(order.getCreator().toString());
	// Bmessage.setContent("CPS:您有新的订单,可以进行抢单了!");
	// Bmessage.setReceiver(car.getUserRoleId());
	// Bmessage.setType(2);
	// Bmessage.setTitle("一路好运");
	// messageDao.save(Bmessage);
	// }
	// CsclPushUtil.SendPushToAllSound(buser, "CPS:您有新的订单,可以进行抢单了!");
	// }
	// }
	// }else if(ls.size()>0){
	// for(Util_Option option :ls){
	// List<CL_Car> cars3 = this.carDao.getByCarSpecIdAndTypeId(option.getId(),
	// option.getOptionId());
	// if(cars3.size()>0){
	// for(int c = cars3.size()-1;c>=0;c--){
	// if(cars3.get(c).getLatitude()==null ||
	// "".equals(cars3.get(c).getLatitude())){
	// cars3.remove(c);
	// continue;
	// }
	// if((new
	// BigDecimal(effectiveDistance)).compareTo(LatitudeLongitudeDI.GetDistance(Double.parseDouble(detail.getLatitude()),
	// Double.parseDouble(detail.getLongitude()),
	// cars3.get(c).getLatitude().doubleValue(),
	// cars3.get(c).getLongitude().doubleValue()))==-1){
	// cars3.remove(c);
	// }
	// }
	// }
	// if(cars3.size()>0){
	// String cuser="";
	// CL_Message Cmessage=null;
	// for(CL_Car car :cars3){
	// cuser+=String.valueOf(car.getUserRoleId())+",";
	// Cmessage=new CL_Message();
	// Cmessage.setCreateTime(new Date());
	// Cmessage.setCreator(order.getCreator().toString());
	// Cmessage.setContent("CPS:您有新的订单,可以进行抢单了!");
	// Cmessage.setReceiver(car.getUserRoleId());
	// Cmessage.setType(2);
	// Cmessage.setTitle("一路好运");
	// messageDao.save(Cmessage);
	// }
	// CsclPushUtil.SendPushToAllSound(cuser, "CPS:您有新的订单,可以进行抢单了!");
	// }
	// }
	// }
	// }else{
	// CarQuery carQuery =new CarQuery();
	// List<CL_Car> carlist = this.carDao.find(carQuery);
	// for(int i = carlist.size()-1;i>=0;i--){
	// if(carlist.get(i).getLatitude()==null||"".equals(carlist.get(i).getLatitude())){
	// carlist.remove(i);
	// continue;
	// }
	// if((new
	// BigDecimal(effectiveDistance)).compareTo(LatitudeLongitudeDI.GetDistance(Double.parseDouble(detail.getLatitude()),
	// Double.parseDouble(detail.getLongitude()),
	// carlist.get(i).getLatitude().doubleValue(),
	// carlist.get(i).getLongitude().doubleValue()))==-1){
	// carlist.remove(i);
	// }
	// }
	// if(carlist.size()>0){
	// user="";
	// CL_Message message=null;
	// for(CL_Car carafter :carlist){
	// user+=String.valueOf(carafter.getUserRoleId())+",";
	// message=new CL_Message();
	// message.setCreateTime(new Date());
	// message.setCreator(order.getCreator().toString());
	// message.setContent("CPS:您有新的订单,可以进行抢单了!");
	// message.setReceiver(carafter.getUserRoleId());
	// message.setType(2);
	// message.setTitle("一路好运");
	// messageDao.save(message);
	// }
	// CsclPushUtil.SendPushToAllSound(user, "CPS:您有新的订单,可以进行抢单了!");
	// }
	// }
	// }catch (Exception e) {
	// e.printStackTrace();
	// }
	// map.put("success", "true");
	// map.put("msg","操作成功！");
	// System.out.println(JSONUtil.getJson(map));
	// return writeAjaxResponse(response, JSONUtil.getJson(map));
	// }
	//
	/*** 查询车主订单记录信息 */
	@RequestMapping("/app/DrorderRecord/DrloadRecord")
	public String DrloadRecord(HttpServletRequest request, HttpServletResponse response) throws IOException {
		Integer userId, status, takeCount, recCount, pageNum, pageSize, userType;
		HashMap<String, Object> map = new HashMap<String, Object>();
		if (request.getParameter("userId") == null || "".equals(request.getParameter("userId"))) {
			map.put("success", "false");
			map.put("msg", "未登录！");
			return writeAjaxResponse(response, JSONUtil.getJson(map));
		} else {
			userId = Integer.valueOf(request.getParameter("userId"));
		}
		if (request.getParameter("status") == null || "".equals(request.getParameter("status"))) {
			map.put("success", "false");
			map.put("msg", "数据有误请联系客服");
			return writeAjaxResponse(response, JSONUtil.getJson(map));
		} else {
			status = Integer.valueOf(request.getParameter("status"));
		}
		if (request.getParameter("pagenum") == null || "".equals(request.getParameter("pagenum"))) {
			map.put("success", "false");
			map.put("msg", "无法获知第几页");
			return writeAjaxResponse(response, JSONUtil.getJson(map));
		} else {
			pageNum = Integer.valueOf(request.getParameter("pagenum"));
		}
		if (request.getParameter("userType") == null || "".equals(request.getParameter("userType"))) {
			map.put("success", "false");
			map.put("msg", "无法判断用户是查询详情类型,请联系客服");
			return writeAjaxResponse(response, JSONUtil.getJson(map));
		} else {
			userType = Integer.parseInt(request.getParameter("userType"));
		}
		if (request.getParameter("pagesize") == null || "".equals(request.getParameter("pagesize"))) {
			map.put("success", "false");
			map.put("msg", "无法获知每页显示多少条");
			return writeAjaxResponse(response, JSONUtil.getJson(map));
		} else {
			pageSize = Integer.valueOf(request.getParameter("pagesize"));
		}
		orderQuery = newQuery(OrderQuery.class, null);
		if (pageNum != null) {
			orderQuery.setPageNumber(pageNum);
		}
		if (pageSize != null) {
			orderQuery.setPageSize(pageSize);
		}
		orderQuery.setDrloadCar(status);
		orderQuery.setStatus(null);
		orderQuery.setUserRoleId(userId);
		Page<CL_Order> page = orderDao.findPage(orderQuery);
		List<CL_Order> DrOrderlist = page.getResult();
		// CL_Rule rule = this.ruleDao.getOneByType();
		// if(rule ==null){
		// map.put("success", "false");
		// map.put("msg","司机计费规则丢失,请联系客服！");
		// return writeAjaxResponse(response, JSONUtil.getJson(map));
		// }
		for (CL_Order order : DrOrderlist) {
			String AllNumber = "";
			// order.setChargingrule(rule.getStartPrice());
			if (userType == 2) {
				List<CL_Addto> adds = iaddtoDao.getByOrderId(order.getId());
				BigDecimal AllCost = new BigDecimal(0);
				if (adds.size() > 0) {
					for (CL_Addto ad : adds) {
						if (ad.getNumber() != null && !"".equals(ad.getNumber())) {
							AllNumber += ad.getNumber() + ",";
						}
						AllCost = AllCost.add(ad.getFcost());
					}
				}
				if (!"".equals(AllNumber)) {
					AllNumber = AllNumber.substring(0, AllNumber.length() - 1);
				}
				order.setAllNumber(AllNumber);
				order.setAllCost(AllCost.setScale(2, BigDecimal.ROUND_HALF_UP));
				order.setForiginal(order.getFreight());
				// 判断运费调整是否有审批，或是通过审批 2016/11/30 by lancher
				if (order.getFispass_audit() == null || order.getFispass_audit() == 0) {
					order.setFreight(order.getForigin_driverfee().setScale(2, BigDecimal.ROUND_HALF_UP));
				} else {
					order.setFreight(order.getFdriverfee().setScale(2, BigDecimal.ROUND_HALF_UP));
				}
			}
			CL_OrderDetail detailOne = this.orderDetailDao.getByOrderIdForDeliverAddress(order.getId());
			takeCount = this.orderDetailDao.findCount(order.getId(), 1);
			recCount = this.orderDetailDao.findCount(order.getId(), 2);
			if (detailOne == null) {
				map.put("success", "false");
				map.put("msg", "订单编号为: " + order.getNumber() + "   数据遗失请联系客服");
				return writeAjaxResponse(response, JSONUtil.getJson(map));
			}
			order.setTakephone(detailOne.getPhone());
			order.setTakelinkman(detailOne.getLinkman());
			order.setTakeAddress(detailOne.getAddressName());
			order.setTakelatitude(detailOne.getLatitude());
			order.setTakelongitude(detailOne.getLongitude());
			order.setTakeCount(takeCount);
			CL_OrderDetail detailTwo = this.orderDetailDao.getByOrderIdForConsigneeAddress(order.getId());
			if (order.getType() != 3) {
				if (detailTwo == null) {
					map.put("success", "false");
					map.put("msg", "订单编号为: " + order.getNumber() + "  数据遗失请联系客服");
					return writeAjaxResponse(response, JSONUtil.getJson(map));
				}
				order.setRecphone(detailTwo.getPhone());
				order.setReclinkman(detailTwo.getLinkman());
				order.setRecAddress(detailTwo.getAddressName());
				order.setReclatitude(detailTwo.getLatitude());
				order.setReclongitude(detailTwo.getLongitude());
				order.setRecCount(recCount);
			}
			if (order.getType() != 2) {
				List<Util_Option> ups = optionDao.getCarTypeByOrderIdName2(order.getId());
				if (ups.size() <= 0) {
					map.put("success", "false");
					map.put("msg", order.getId() + "所需车型没有,请联系客服");
					return writeAjaxResponse(response, JSONUtil.getJson(map));
				}
				String carSpecName = ups.get(0).getOptionName();
				String carOtherName = ups.get(0).getOptionCarOtherName();
				for (Util_Option option : ups) {
					if (option.getOptionCarTypeName() != null && !option.getOptionCarTypeName().equals("")) {
						if (!carSpecName.contains(option.getOptionCarTypeName())) {
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
		}
		HashMap<String, Object> m = new HashMap<String, Object>();
		m.put("success", "true");
		m.put("total", page.getTotalCount());
		m.put("data", DrOrderlist);
		return writeAjaxResponse(response, JSONUtil.getJson(m));
	}

	/**  司机端完成订单 按钮**/
	@RequestMapping("/app/DrorderAddress/DrloadAddress")
	public String DrloadAddress(HttpServletRequest request, HttpServletResponse response) throws IOException {
		Integer orderId;
		HashMap<String, Object> map = new HashMap<String, Object>();
		List<CL_OrderDetail> DetailList = new ArrayList<>();
		if (request.getParameter("orderId") == null || "".equals(request.getParameter("orderId"))) {
			map.put("success", "false");
			map.put("msg", "订单数据有误请联系客服!");
			return writeAjaxResponse(response, JSONUtil.getJson(map));
		} else {
			orderId = Integer.valueOf(request.getParameter("orderId"));
		}
		DetailList = orderDetailDao.getByPCOrderId(orderId);
		for (CL_OrderDetail orderDetail : DetailList) {
			if(orderDetail.getFstatus() == 0){
				return this.poClient(response, false, "请先确认提货与卸货!");
			}
		}
		return this.poClient(response, true, "准备确认完成订单!");
	}
	
	@RequestMapping("/app/DrorderAddress/OrderDetailAddress")
	public String Address(HttpServletRequest request, HttpServletResponse response) throws IOException {
		Integer orderId;
		HashMap<String, Object> map = new HashMap<String, Object>();
		if (request.getParameter("orderId") == null || "".equals(request.getParameter("orderId"))) {
			map.put("success", "false");
			map.put("msg", "订单数据有误请联系客服!");
			return writeAjaxResponse(response, JSONUtil.getJson(map));
		} else {
			orderId = Integer.valueOf(request.getParameter("orderId"));
		}
		List<CL_OrderDetail> DetailList = orderDetailDao.getByPCOrderId(orderId);
		map.put("data", DetailList);
		map.put("success", "true");
		return writeAjaxResponse(response, JSONUtil.getJson(map));
	}

	public boolean getDriverPositionFun(Integer userId, CL_OrderDetail detail) {
		// 司机缓存拿的经纬度
		String[] st = null;
		String Longitude = "";
		String Latitude = "";
		Integer effectiveDistance = 10;
		st = ServerContext.getDriverPosition().get(Integer.toString(userId));
		if (st != null && st.length > 0) {
			Longitude = st[0];
			Latitude = st[1];
		} else {
			return false;
		}
		BigDecimal straightStretch = LatitudeLongitudeDI.GetDistance(Double.parseDouble(detail.getLatitude()),
				Double.parseDouble(detail.getLongitude()), Double.parseDouble(Latitude), Double.parseDouble(Longitude));
		if ((new BigDecimal(effectiveDistance)).compareTo(straightStretch) == -1) {
			return false;
		}
		return true;
	}
}
