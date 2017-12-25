package com.pc.controller.order;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.SheetSettings;
import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.Colour;
import jxl.format.VerticalAlignment;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import net.sf.json.JSONObject;

import org.apache.commons.codec.binary.Base64;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.org.rapid_framework.page.Page;

import com.pc.appInterface.api.DongjingClient;
import com.pc.controller.BaseController;
import com.pc.dao.Car.ICarDao;
import com.pc.dao.UserRole.impl.UserRoleDao;
import com.pc.dao.addto.IaddtoDao;
import com.pc.dao.couponsDetail.ICouponsDetailDao;
import com.pc.dao.financeStatement.IFinanceStatementDao;
import com.pc.dao.order.IorderDao;
import com.pc.dao.orderCarDetail.IorderCarDetailDao;
import com.pc.dao.orderDetail.IorderDetailDao;
import com.pc.dao.protocol.impl.ProtocolDao;
import com.pc.dao.rule.impl.RuleDao;
import com.pc.dao.select.IUtilOptionDao;
import com.pc.model.CL_Addto;
import com.pc.model.CL_Car;
import com.pc.model.CL_CouponsDetail;
import com.pc.model.CL_FinanceStatement;
import com.pc.model.CL_Order;
import com.pc.model.CL_OrderDetail;
import com.pc.model.CL_Protocol;
import com.pc.model.CL_Rule;
import com.pc.model.CL_UserRole;
import com.pc.model.Util_Option;
import com.pc.query.order.OrderQuery;
import com.pc.query.userRole.UserRoleQuery;
import com.pc.util.CacheUtilByCC;
import com.pc.util.CalcTotalField;
import com.pc.util.DJException;
import com.pc.util.JSONUtil;
import com.pc.util.LatitudeLongitudeDI;
import com.pc.util.RSA;
import com.pc.util.ServerContext;
import com.pc.util.pay.OrderMsg;

@Controller
public class OrderController extends BaseController{
	protected static final String PUBLICKEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDl5nbvmL8Q8tYGcJAgwS4qdqp2" +
            "Rwme5FKaR+11vXy89Biu8ruF/KmdS4pk+4gEmoPuHLFc6V6VQ77CgtpgboBDjveU" +
            "n3HnsN1N2LH/hmn8gDvw+0e7lLDFVEGC6L8d9z+yj0zGe0XMDeEW5zJlVCA2FOYq" +
            "oQAOkIntynv/nfyP6wIDAQAB";

	protected static final String PERSONALPUBLICKEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCcu9tqg+XyDB7qygFxn4UQu00T" +
            "WrnbQmIDUnE8zhDf9ZuCr5Czil1XVR4ApnpcVUTTXHoW2WpzBw1gm53OdKjgPc2Q" +
            "yRq+ROlo7NhYCRFan+b4p+RqGL+U+alMH1zv1Q+LSgQP6QF9loKAsC3i70KdBw4G" +
            "n7K8fTzoY8PtMqHlSwIDAQAB";

	public static final String OrderCreate=ServerContext.getBaseurl()+"/Action_Pay/DJPay/Pay/OrderCreate?XDEBUG_SESSION_START=17402";

	protected static final String UPLOAD_JSP= "/pages/pc/receipt/receipt_list.jsp";
	protected static final String LIST_JSP= "/pages/pc/order/order_list.jsp";
	protected static final String VIEW_LIST_JSP= "/pages/pc/order/order_view_list.jsp";
	protected static final String DETAIL_JSP= "/pages/pc/order/orderDetail.jsp";
	protected static final String CONTROL_JSP= "/pages/pc/order/orderControl.jsp";
	protected static final String SPECIALIZED_CONTROL_JSP= "/pages/pc/order/specializedControl.jsp";
	protected static final String ORDER_ARRIVE_PAY_JSP = "/pages/pc/finance/order_arrivePay.jsp";
	protected static final String UPDATE_FREIGHT_JSP= "/pages/pc/order/editFreight.jsp";
	protected static final String FREIGHT_VERIFY_JSP = "/pages/pc/finance/freightVerify.jsp";

	@Resource
	private IorderDao orderDao;
	@Resource
	private IorderCarDetailDao orderCarDetailDao;
	@Resource
	private IorderDetailDao orderDetailDao;
	@Resource
	private ICarDao carDao;
	@Resource
	private UserRoleDao userRoleDao;
	@Resource
	private IUtilOptionDao optionDao;
	@Resource
	private IFinanceStatementDao statementDao;
	@Resource
	private IaddtoDao iaddtoDao;
	@Resource
	private IFinanceStatementDao financeStatementDao;
	@Resource
	private ICouponsDetailDao icouponsDetailDao;
	@Resource
	private ProtocolDao protocolDao;
	@Resource
	private RuleDao ruleDao;
	
	private UserRoleQuery userRoleQuery;
	public UserRoleQuery getUserRoleQuery() {
		return userRoleQuery;
	}
	public void setUserRoleQuery(UserRoleQuery userRoleQuery) {
		this.userRoleQuery = userRoleQuery;
	}

	@RequestMapping("/order/list")
	public String list(HttpServletRequest request,HttpServletResponse reponse) throws Exception{
		return LIST_JSP;
	}

	@RequestMapping("/order/viewlist")
	public String viewlist(HttpServletRequest request,HttpServletResponse reponse) throws Exception{
		return VIEW_LIST_JSP;
	}
	
	@RequestMapping("/order/arrivepaylist")
	public String arrivepaylist(HttpServletRequest request,HttpServletResponse reponse) throws Exception{
		return ORDER_ARRIVE_PAY_JSP;
	}
	
	/***调度**/
	@RequestMapping("/order/control")
	public String control(HttpServletRequest request,HttpServletResponse reponse,Integer id) throws Exception{
		request.getSession().setAttribute("orderid",id);//把需要指派的订单id保存在request中
		return CONTROL_JSP;
	}
	
	/**指派司机**/
	@RequestMapping("/order/assign")
	public String assign(HttpServletRequest request, HttpServletResponse reponse)
			throws Exception {
		HashMap<String, Object> map = new HashMap<String, Object>();
		int id = Integer.parseInt(request.getParameter("userRoleId"));// 获取被指派司机的id
		int orderid = (int) request.getSession().getAttribute("orderid");// 获取被指派订单的id
		String return_car = request.getParameter("freturn_car");
		if (return_car == null || "".equals(return_car)) {
			map.put("success", "false");
			map.put("msg", "请选择回程车！");
			return writeAjaxResponse(reponse, JSONUtil.getJson(map));
		}
		int freturn_car = Integer.parseInt(return_car);
		// 判断订单是否为未抢状态
		CL_Order orderInfo = orderDao.IsExistIdByStatus(orderid);
		if (orderInfo != null && !orderInfo.equals("")) {
			CL_Order clo = orderDao.getById(orderid);
			clo.setFreturn_car(freturn_car);
			clo.setUserRoleId(id);// 修改被指派订单的司机的id
			clo.setStatus(3);// 修改订单状态从派车中变更为运输中
			clo.setOperator(Integer.parseInt(request.getSession()
					.getAttribute("userRoleId").toString()));
			clo.setFoperate_time(new Date());// 调度开始时间

			// 司机价格分离需求变动---------------------by lancher begin
			// 判断订单司机是否存在协议规则
			Integer carSpecId = carDao.getByUserRoleId(id).get(0)
					.getCarSpecId();
			if (carSpecId == 4) {// 5m2的车型暂时不计规则
				map.put("success", "false");
				map.put("msg", "没有该司机对应的计费规则");
				return writeAjaxResponse(reponse, JSONUtil.getJson(map));
			}

			// 包天订单没有公里数，司机费用单独去找司机的包天协议，找不到就提示创建
			if (clo.getType() == 3) {
				List<CL_Protocol> list = protocolDao.getBySpecAndTypeAndDriver(
						carSpecId, clo.getCreator(), 4, 1);
				if (list.size() > 0) {
					CL_Protocol protocol = list.get(0);
					clo.setForigin_driverfee(protocol.getStartPrice());
					clo.setFdriverfee(protocol.getStartPrice());
				} else {
					map.put("success", "false");
					map.put("msg", "该订单为包天订单，请先创建包天司机规则！");
					return writeAjaxResponse(reponse, JSONUtil.getJson(map));
				}
			} else {
				// BigDecimal origin_driverfee = clo.getForigin_driverfee();
				// if(origin_driverfee ==
				// null){//判断是否有原司机运价，没有则表示该客户没有司机协议，需要重新计算司机运价
				BigDecimal driverfee = this.driverfee(carSpecId,
						clo.getCreator(), clo);
				if (driverfee == new BigDecimal(-1)){//新需求代码
					map.put("success", "false");
					map.put("msg", "坏账，请联系客服");
					return writeAjaxResponse(reponse, JSONUtil.getJson(map));
				} else if (driverfee.compareTo(new BigDecimal(0)) != 0) {
					clo.setForigin_driverfee(driverfee);
					clo.setFdriverfee(driverfee);
				} else {
					List<CL_Rule> rulelist = ruleDao.getDriverBySpec(carSpecId);
					if (rulelist.size() == 0) {
						map.put("success", "false");
						map.put("msg", "请先创建对应的计费规则");
						return writeAjaxResponse(reponse, JSONUtil.getJson(map));
					}
					Integer ruleId = rulelist.get(0).getId();
					CL_Rule rule = ruleDao.getById(ruleId);
					// 超出是每公里单价
//					BigDecimal kilometrePrice = rule.getKilometrePrice();
					// 起步公里数
					BigDecimal startKilometre = rule.getStartKilometre();
					// 起步价
					BigDecimal startPrice = rule.getStartPrice();
					// 订单公里数
					BigDecimal mileage = clo.getMileage();
					String fincrementServe = clo.getFincrementServe();
					
					//新需求----------------begin
					BigDecimal outM = BigDecimal.ZERO;//超出公里
					BigDecimal out_5_20_K = rule.getKilometre5_20_price();//5-20公里单价(包含20公里)
					BigDecimal out_20_50_K = rule.getKilometre20_50_price();//20-50公里单价(包含50公里)
					BigDecimal out_50_K = rule.getKilometre50_price();//50公里单价
					int s = 0;
					if(rule.getCarSpecId() == 3){
						if(mileage.subtract(startKilometre).compareTo(BigDecimal.ZERO) <= 0){
							s = 1;//未超出
						} else {//超出
							outM = mileage.subtract(startKilometre);
							if(outM.subtract(new BigDecimal(15)).compareTo(BigDecimal.ZERO) <= 0){//5-20公里
								s = 2;
							} else if (outM.subtract(new BigDecimal(45)).compareTo(BigDecimal.ZERO) <= 0) {
								s = 3;
							} else {
								s = 4;
							}
						}
					} else if (rule.getCarSpecId() == 5) {
						if(mileage.subtract(startKilometre).compareTo(BigDecimal.ZERO) <= 0){
							s = 1;//未超出
						} else {//超出
							outM = mileage.subtract(startKilometre);
							if(outM.subtract(new BigDecimal(30)).compareTo(BigDecimal.ZERO) <= 0){//20-50公里
								s = 2;
							} else {//50-
								s = 3;
							} 
						}
					} else {
						map.put("success", "false");
						map.put("msg", "公司没有该司机的车型车辆");
						return writeAjaxResponse(reponse, JSONUtil.getJson(map));
					}
					//新需求----------------end by lancher
					
					if (fincrementServe == null) {
						fincrementServe = "";
					}
					
					int outOpint = clo.getFopint() - 1;
					BigDecimal opintMoney = BigDecimal.ZERO;
					if(outOpint > 0){
						opintMoney = rule.getOutfopint().multiply(new BigDecimal(outOpint));
					}

					if (fincrementServe.indexOf("装货") != -1 && fincrementServe.indexOf("卸货") != -1 && carSpecId != 6) {
						
						//新需求----------------begin
						if(rule.getCarSpecId() == 3){
							switch (s) {
							case 1://0-5
								clo.setFdriverfee(startPrice.add(rule.getFadd_service_1()).add(rule.getFadd_service_2()).add(opintMoney));
								clo.setForigin_driverfee(clo.getFdriverfee());
								break;
							case 2://5-20
								clo.setFdriverfee(startPrice.add(outM.multiply(out_5_20_K)).add(rule.getFadd_service_1()).add(rule.getFadd_service_2()).add(opintMoney).setScale(2, BigDecimal.ROUND_HALF_UP));
								clo.setForigin_driverfee(clo.getFdriverfee());
								break;
							case 3://20-50
								clo.setFdriverfee(startPrice.add(new BigDecimal(15).multiply(out_5_20_K)).add(mileage.subtract(new BigDecimal(20)).multiply(out_20_50_K)).add(rule.getFadd_service_1()).add(rule.getFadd_service_2()).add(opintMoney).setScale(2, BigDecimal.ROUND_HALF_UP));
								clo.setForigin_driverfee(clo.getFdriverfee());
								break;
							case 4://50-
								clo.setFdriverfee(startPrice.add(new BigDecimal(15).multiply(out_5_20_K)).add(new BigDecimal(30).multiply(out_20_50_K)).add(mileage.subtract(new BigDecimal(50)).multiply(out_50_K)).add(rule.getFadd_service_1()).add(rule.getFadd_service_2()).add(opintMoney).setScale(2, BigDecimal.ROUND_HALF_UP));
								clo.setForigin_driverfee(clo.getFdriverfee());
								break;
								
							default:
								map.put("success", "false");
								map.put("msg", "坏账，请联系客服");
								return writeAjaxResponse(reponse, JSONUtil.getJson(map));
							}
						} else if (rule.getCarSpecId() == 5){
							switch (s) {
							case 1://0-20
								clo.setFdriverfee(startPrice.add(rule.getFadd_service_1()).add(rule.getFadd_service_2()).add(opintMoney));
								clo.setForigin_driverfee(clo.getFdriverfee());
								break;
							case 2://20-50
								clo.setFdriverfee(startPrice.add(outM.multiply(out_20_50_K)).add(rule.getFadd_service_1()).add(rule.getFadd_service_2()).add(opintMoney).setScale(2, BigDecimal.ROUND_HALF_UP));
								clo.setForigin_driverfee(clo.getFdriverfee());
								break;
							case 3://50-
								clo.setFdriverfee(startPrice.add(new BigDecimal(30).multiply(out_20_50_K)).add(mileage.subtract(new BigDecimal(50)).multiply(out_50_K)).add(rule.getFadd_service_1()).add(rule.getFadd_service_2()).add(opintMoney).setScale(2, BigDecimal.ROUND_HALF_UP));
								clo.setForigin_driverfee(clo.getFdriverfee());
								break;
								
							default:
								map.put("success", "false");
								map.put("msg", "坏账，请联系客服");
								return writeAjaxResponse(reponse, JSONUtil.getJson(map));
							}
						}
						
						clo.setDriver_serve_1(rule.getFadd_service_1());//存入司机装卸费
						clo.setDriver_serve_2(rule.getFadd_service_2());
						//新需求----------------end by lancher
						
						/*// 有增值服务
						if (mileage.compareTo(startKilometre) == -1) {// 未超出起步公里
							(startPrice.add(rule
									.getFadd_service()));
							clo.setFdriverfee(clo.getForigin_driverfee());
						} else {// 超出起步公里
							System.out.println("增值" + rule.getFadd_service());
							clo.setForigin_driverfee(startPrice
									.add((mileage.subtract(startKilometre))
											.multiply(kilometrePrice))
									.add(rule.getFadd_service())
									.setScale(1, BigDecimal.ROUND_HALF_UP));
							clo.setFdriverfee(clo.getForigin_driverfee());
						}*/
					} else if (fincrementServe.indexOf("装货") != -1 && fincrementServe.indexOf("卸货") == -1 && carSpecId != 6) {
						if(rule.getCarSpecId() == 3){
							switch (s) {
							case 1://0-5
								clo.setFdriverfee(startPrice.add(rule.getFadd_service_1()).add(opintMoney));
								clo.setForigin_driverfee(clo.getFdriverfee());
								break;
							case 2://5-20
								clo.setFdriverfee(startPrice.add(outM.multiply(out_5_20_K)).add(rule.getFadd_service_1()).add(opintMoney).setScale(2, BigDecimal.ROUND_HALF_UP));
								clo.setForigin_driverfee(clo.getFdriverfee());
								break;
							case 3://20-50
								clo.setFdriverfee(startPrice.add(new BigDecimal(15).multiply(out_5_20_K)).add(mileage.subtract(new BigDecimal(20)).multiply(out_20_50_K)).add(rule.getFadd_service_1()).add(opintMoney).setScale(2, BigDecimal.ROUND_HALF_UP));
								clo.setForigin_driverfee(clo.getFdriverfee());
								break;
							case 4://50-
								clo.setFdriverfee(startPrice.add(new BigDecimal(15).multiply(out_5_20_K)).add(new BigDecimal(30).multiply(out_20_50_K)).add(mileage.subtract(new BigDecimal(50)).multiply(out_50_K)).add(rule.getFadd_service_1()).add(opintMoney).setScale(2, BigDecimal.ROUND_HALF_UP));
								clo.setForigin_driverfee(clo.getFdriverfee());
								break;
								
							default:
								map.put("success", "false");
								map.put("msg", "坏账，请联系客服");
								return writeAjaxResponse(reponse, JSONUtil.getJson(map));
							}
						} else if (rule.getCarSpecId() == 5){
							switch (s) {
							case 1://0-20
								clo.setFdriverfee(startPrice.add(rule.getFadd_service_1()).add(opintMoney));
								clo.setForigin_driverfee(clo.getFdriverfee());
								break;
							case 2://20-50
								clo.setFdriverfee(startPrice.add(outM.multiply(out_20_50_K)).add(rule.getFadd_service_1()).add(opintMoney).setScale(2, BigDecimal.ROUND_HALF_UP));
								clo.setForigin_driverfee(clo.getFdriverfee());
								break;
							case 3://50-
								clo.setFdriverfee(startPrice.add(new BigDecimal(30).multiply(out_20_50_K)).add(mileage.subtract(new BigDecimal(50)).multiply(out_50_K)).add(rule.getFadd_service_1()).add(opintMoney).setScale(2, BigDecimal.ROUND_HALF_UP));
								clo.setForigin_driverfee(clo.getFdriverfee());
								break;
								
							default:
								map.put("success", "false");
								map.put("msg", "坏账，请联系客服");
								return writeAjaxResponse(reponse, JSONUtil.getJson(map));
							}
						}
						clo.setDriver_serve_1(rule.getFadd_service_1());//存入司机装卸费
					} else if (fincrementServe.indexOf("装货") == -1 && fincrementServe.indexOf("卸货") != -1 && carSpecId != 6) {
						if(rule.getCarSpecId() == 3){
							switch (s) {
							case 1://0-5
								clo.setFdriverfee(startPrice.add(rule.getFadd_service_2()).add(opintMoney));
								clo.setForigin_driverfee(clo.getFdriverfee());
								break;
							case 2://5-20
								clo.setFdriverfee(startPrice.add(outM.multiply(out_5_20_K)).add(rule.getFadd_service_2()).add(opintMoney).setScale(2, BigDecimal.ROUND_HALF_UP));
								clo.setForigin_driverfee(clo.getFdriverfee());
								break;
							case 3://20-50
								clo.setFdriverfee(startPrice.add(new BigDecimal(15).multiply(out_5_20_K)).add(mileage.subtract(new BigDecimal(20)).multiply(out_20_50_K)).add(rule.getFadd_service_2()).add(opintMoney).setScale(2, BigDecimal.ROUND_HALF_UP));
								clo.setForigin_driverfee(clo.getFdriverfee());
								break;
							case 4://50-
								clo.setFdriverfee(startPrice.add(new BigDecimal(15).multiply(out_5_20_K)).add(new BigDecimal(30).multiply(out_20_50_K)).add(mileage.subtract(new BigDecimal(50)).multiply(out_50_K)).add(rule.getFadd_service_2()).add(opintMoney).setScale(2, BigDecimal.ROUND_HALF_UP));
								clo.setForigin_driverfee(clo.getFdriverfee());
								break;
								
							default:
								map.put("success", "false");
								map.put("msg", "坏账，请联系客服");
								return writeAjaxResponse(reponse, JSONUtil.getJson(map));
							}
						} else if (rule.getCarSpecId() == 5){
							switch (s) {
							case 1://0-20
								clo.setFdriverfee(startPrice.add(rule.getFadd_service_2()).add(opintMoney));
								clo.setForigin_driverfee(clo.getFdriverfee());
								break;
							case 2://20-50
								clo.setFdriverfee(startPrice.add(outM.multiply(out_20_50_K)).add(rule.getFadd_service_2()).add(opintMoney).setScale(2, BigDecimal.ROUND_HALF_UP));
								clo.setForigin_driverfee(clo.getFdriverfee());
								break;
							case 3://50-
								clo.setFdriverfee(startPrice.add(new BigDecimal(30).multiply(out_20_50_K)).add(mileage.subtract(new BigDecimal(50)).multiply(out_50_K)).add(rule.getFadd_service_2()).add(opintMoney).setScale(2, BigDecimal.ROUND_HALF_UP));
								clo.setForigin_driverfee(clo.getFdriverfee());
								break;
								
							default:
								map.put("success", "false");
								map.put("msg", "坏账，请联系客服");
								return writeAjaxResponse(reponse, JSONUtil.getJson(map));
							}
						}
						clo.setDriver_serve_2(rule.getFadd_service_2());//存入司机装卸费
					} else {// 无增值服务

						//新需求----------------begin
						if (rule.getCarSpecId() == 3) {
							switch (s) {
							case 1:// 0-5
								clo.setFdriverfee(startPrice.add(opintMoney));
								clo.setForigin_driverfee(clo.getFdriverfee());
								break;
							case 2:// 5-20
								clo.setFdriverfee(startPrice.add(
										outM.multiply(out_5_20_K)).add(opintMoney).setScale(2,
										BigDecimal.ROUND_HALF_UP));
								clo.setForigin_driverfee(clo.getFdriverfee());
								break;
							case 3:// 20-50
								clo.setFdriverfee(startPrice
										.add(new BigDecimal(15)
												.multiply(out_5_20_K))
										.add(mileage.subtract(
												new BigDecimal(20)).multiply(
												out_20_50_K))
										.add(opintMoney)
										.setScale(2, BigDecimal.ROUND_HALF_UP));
								clo.setForigin_driverfee(clo.getFdriverfee());
								break;
							case 4:// 50-
								clo.setFdriverfee(startPrice
										.add(new BigDecimal(15)
												.multiply(out_5_20_K))
										.add(new BigDecimal(30)
												.multiply(out_20_50_K))
										.add(mileage.subtract(
												new BigDecimal(50)).multiply(
												out_50_K))
										.add(opintMoney)
										.setScale(2, BigDecimal.ROUND_HALF_UP));
								clo.setForigin_driverfee(clo.getFdriverfee());
								break;

							default:
								map.put("success", "false");
								map.put("msg", "坏账，请联系客服");
								return writeAjaxResponse(reponse,
										JSONUtil.getJson(map));
							}
						} else if (rule.getCarSpecId() == 5) {
							switch (s) {
							case 1:// 0-20
								clo.setFdriverfee(startPrice.add(opintMoney));
								clo.setForigin_driverfee(clo.getFdriverfee());
								break;
							case 2:// 20-50
								clo.setFdriverfee(startPrice.add(
										outM.multiply(out_20_50_K)).add(opintMoney).setScale(2,
										BigDecimal.ROUND_HALF_UP));
								clo.setForigin_driverfee(clo.getFdriverfee());
								break;
							case 3:// 50-
								clo.setFdriverfee(startPrice
										.add(new BigDecimal(30)
												.multiply(out_20_50_K))
										.add(mileage.subtract(
												new BigDecimal(50)).multiply(
												out_50_K))
										.add(opintMoney)
										.setScale(2, BigDecimal.ROUND_HALF_UP));
								clo.setForigin_driverfee(clo.getFdriverfee());
								break;

							default:
								map.put("success", "false");
								map.put("msg", "坏账，请联系客服");
								return writeAjaxResponse(reponse,
										JSONUtil.getJson(map));
							}
						}
						//新需求----------------end by lancher
						
						/*if (mileage.compareTo(startKilometre) == -1) {// 未超出起步公里
							clo.setForigin_driverfee(startPrice);
							clo.setFdriverfee(startPrice);
						} else {
							clo.setForigin_driverfee(startPrice.add(
									(mileage.subtract(startKilometre))
											.multiply(kilometrePrice))
									.setScale(2, BigDecimal.ROUND_HALF_UP));
							clo.setFdriverfee(clo.getForigin_driverfee());
						}*/
					}

				}
				// } else {
				// clo.setFdriverfee(origin_driverfee);
				// }

			}

			// 司机价格分离需求变动---------------------by lancher end
			System.out.println("调度后的司机费用" + clo.getFdriverfee());
			orderDao.update(clo);// 修改
			List<CL_Car> cars = carDao.getByUserRoleId(id);
			List<CL_OrderDetail> detailList = orderDetailDao.getByOrderId(
					orderid, 2);
			CL_UserRole userRole = userRoleDao.getById(id);
			CL_OrderDetail detailOne = orderDetailDao
					.getByOrderIdForDeliverAddress(orderid);
			DongjingClient djcn = ServerContext.createVmiClient();
			String msgString1 = "%s (车牌号:%s),请于" + clo.getLoadedTimeString()
					+ "前到 " + detailOne.getAddressName() + "提货。您辛苦了,请做好服务工作!";
			djcn.setMethod("getDeatilCode");
			djcn.setRequestProperty("ftel", userRole.getVmiUserPhone());
			djcn.setRequestProperty("fname", cars.get(0).getDriverName());
			djcn.setRequestProperty("fcode", cars.get(0).getCarNum());
			djcn.setRequestProperty("msgString", msgString1);
			djcn.SubmitData();
			// String
			// msgString="您的货物在运输途中,为您服务的 %s,电话:"+userRole.getVmiUserPhone()+",车牌号:"+cars.get(0).getCarNum()+",客服:0577-85391111,更多优惠下载手机APP,http://fir.im/d1q4";
			String msgString = "您的货物在运输途中,为您服务的 %s,电话:"
					+ userRole.getVmiUserPhone() + ",车牌号:"
					+ cars.get(0).getCarNum() + ",客服:0577-85391111";
			for (CL_OrderDetail detail : detailList) {
				djcn.setMethod("getDeatilCode");
				djcn.setRequestProperty("ftel", detail.getPhone());
				djcn.setRequestProperty("fname", cars.get(0).getDriverName());
				djcn.setRequestProperty("fcode", detail.getSecurityCode());
				djcn.setRequestProperty("msgString", msgString);
				djcn.SubmitData();
			}
			String userRoleString = Integer
					.valueOf(cars.get(0).getUserRoleId()).toString();
			// CsclPushUtil.SendPushToAllSound(userRoleString , "CPS:您有新的订单 !");
			map.put("success", "true");
			map.put("msg", "操作成功！");
		} else {
			map.put("success", "false");
			map.put("msg", "该订单已指定司机");
		}
		return writeAjaxResponse(reponse, JSONUtil.getJson(map));
	}
	
	/***专车调度**/
	@RequestMapping("/order/specializedControl")
	public String specializedControl(HttpServletRequest request,HttpServletResponse reponse,Integer id) throws Exception{
		request.getSession().setAttribute("orderid",id);//把需要指派的订单id保存在request中
		return SPECIALIZED_CONTROL_JSP;
	}
	

	/***加载司机姓名**/
	@RequestMapping("/order/queryDriver")
	public String queryDriver(HttpServletRequest request,HttpServletResponse reponse) throws Exception{
		int orderid=(int) request.getSession().getAttribute("orderid");
		CL_Order order =this.orderDao.getById(orderid);
		List<CL_Car> ls=new ArrayList<CL_Car>();
		if(order.getType()==1){
			//根据订单ID,获取其所需车型（去掉重复）
			List<Util_Option> cartype = this.optionDao.getCarTypeByOrderId(orderid);
			if(cartype.size()>0){
				if(cartype.size()==1){
						if(("任意车型").equals(cartype.get(0).getOptionName())){
							List<CL_Car> carlist = this.carDao.getByCarSpecId(cartype.get(0).getId());
							if (carlist!=null) {
								for (CL_Car car : carlist) {
									ls.add(car);
								}
							}
						}else{
							List<CL_Car> temp=carDao.getUrIdByCarType(cartype.get(0).getOptionId());
							if (temp!=null) {
								for (CL_Car car : temp) {
									ls.add(car);
								}
							}
						}
				}else{
					for(Util_Option option :cartype){
						List<CL_Car> temp=carDao.getUrIdByCarType(option.getOptionId());
						if (temp!=null) {
							for (CL_Car car : temp) {
								ls.add(car);
							}
						}
					}
				}
			}
		}else{
			List<CL_Car> carlist = this.carDao.getAllCar();
			if(carlist.size()>0){
				for(CL_Car car:carlist){
						ls.add(car);
				}
			}
		}
		
		return writeAjaxResponse(reponse,JSONUtil.getJson(ls));
	}

	/***加载司机姓名**/
	@RequestMapping("/order/controlList")
	public String controlList(HttpServletRequest request,HttpServletResponse reponse) throws Exception{
		int orderid=(int) request.getSession().getAttribute("orderid");
		CL_Order order =this.orderDao.getById(orderid);
		CL_OrderDetail  detailInfo=orderDetailDao.getByOrderId(orderid, 1).get(0);
		List<CL_Car> ls=new ArrayList<CL_Car>();
		if(order.getType()==1){
			//根据订单ID,获取其所需车型（去掉重复）
			List<Util_Option> cartype = this.optionDao.getCarTypeByOrderId(orderid);
			if(cartype.size()>0){
				if(cartype.size()==1){
						if(("任意车型").equals(cartype.get(0).getOptionName())){
							List<CL_Car> carlist = this.carDao.getByCarSpecId(cartype.get(0).getId());
							if (carlist!=null) {
//								for (CL_Car car : carlist) {
//									ls.add(car);
//								}
								ls = controlCar(detailInfo,carlist);
							}
						}else{
							List<CL_Car> temp=carDao.getUrIdByCarType(cartype.get(0).getOptionId());
							if (temp!=null) {
//								for (CL_Car car : temp) {
//									ls.add(car);
//								}
								ls = controlCar(detailInfo,temp);
							}
						}
				}else{
					for(Util_Option option :cartype){
						List<CL_Car> temp=carDao.getUrIdByCarType(option.getOptionId());
						if (temp!=null) {
//							for (CL_Car car : temp) {
//								ls.add(car);
//							}
							ls = controlCar(detailInfo,temp);
						}
					}
				}
			}
		}else{
			List<CL_Car> carlist = this.carDao.getAllCar();
			if(carlist.size()>0){
//				for(CL_Car car:carlist){
//						ls.add(car);
//				}
				ls = controlCar(detailInfo,carlist);
			}
		}
		
		return writeAjaxResponse(reponse,JSONUtil.getJson(ls));
	}
	
	/***构建司机直线距离及是否在线**/
	public List<CL_Car> controlCar(CL_OrderDetail detailInfo,List<CL_Car> carlist) throws Exception{
		List<CL_Car> carlistN = new ArrayList<CL_Car>();
		for(int i=(carlist.size()-1);i>-1;i--){
			CL_Car car = carlist.get(i);
			CL_UserRole ur = userRoleDao.getById(car.getUserRoleId());
			if(ur!=null && ur.getVmiUserPhone()!=null){
				car.setCarFtel(ur.getVmiUserPhone());
			}else{
				carlist.remove(i);
				continue;
			}
			
			String userRoleid = Integer.toString(car.getUserRoleId());
			if(!ServerContext.getDriverPosition().containsKey(userRoleid)){
				car.setFisOnline(0);//司机是否在线(0为否，1为是);
				car.setFstraightStretch(new BigDecimal(0));
				carlistN.add(car);
				carlist.remove(i);
			}else{
				//司机与提货点的直线距离;
				String Longitude = ServerContext.getDriverPosition().get(userRoleid)[0];
				String Latitude = ServerContext.getDriverPosition().get(userRoleid)[1];
				BigDecimal straightStretch = LatitudeLongitudeDI.GetDistance(Double.parseDouble(detailInfo.getLatitude()), Double.parseDouble(detailInfo.getLongitude()), Double.parseDouble(Latitude), Double.parseDouble(Longitude));
				
				//在当前时间1分钟之前则为不在线;
				SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
				String PositionTime = ServerContext.getDriverPosition().get(userRoleid)[2];
				Date afterDate = new Date((new Date()) .getTime() - 60000);
				if(sdf.parse(PositionTime).before(afterDate)){
					car.setFisOnline(0);//司机是否在线(0为否，1为是);
					car.setFstraightStretch(new BigDecimal(0));
					carlistN.add(car);
					carlist.remove(i);
				}else{
					car.setFstraightStretch(straightStretch);
					car.setFisOnline(1);//司机是否在线(0为否，1为是);
				}
			}
		}
		Collections.sort(carlist);
		carlist.addAll(carlistN);
		return carlist;
	}
	
	/**针对派车中的订单提醒**/
	@RequestMapping("/order/show")
	public String show(HttpServletRequest request,HttpServletResponse reponse ,@ModelAttribute OrderQuery orderQuery) throws Exception{
		//先获取所有派车中的订单信息
		List<CL_Order> lst=orderDao.getOrdersByStatus(2);
		//循环所以派车中的订单，当前时间与派车时间的差大于30分钟，那么就开始提醒
		boolean flag=false;
		for (CL_Order order : lst) {
			Date orderDate=order.getLoadedTime();//派车时间
			Date date=new Date();//当前时间
			if((orderDate.getTime()-date.getTime())/(1000*60)<30 && (orderDate.getTime()-date.getTime())/(1000*60)>0){
				flag=true;
			}
//			java.util.Calendar c1=java.util.Calendar.getInstance();
//			java.util.Calendar c2=java.util.Calendar.getInstance();
//			c1.setTime(date);
//			c2.setTime(orderDate);
//			int result=c1.compareTo(c2);
			//result大于0，说明已经过了派车时间了，需要提醒
//			if (result>0) {
//				flag=true;
//				break;
//			//如果大于当前时间，则需要判断差值是否在30分钟以内
//			}else if (result<0) {
//				long l1=orderDate.getTime()-date.getTime();
//				if ((l1/(1000*60))<30) {
//					flag=true;
//				}
//			}
		}
		if (flag) {
			return writeAjaxResponse(reponse, "success");
		}
		return writeAjaxResponse(reponse, "fail");
	}
	
	/*** 加载订单信息*/
	@RequestMapping("/order/load")
	public String load(HttpServletRequest request,HttpServletResponse reponse ,@ModelAttribute OrderQuery orderQuery) throws Exception{
		String pageNum = request.getParameter("page");
		String pageSize = request.getParameter("rows");
		if (orderQuery == null) {
			orderQuery = newQuery(OrderQuery.class, null);
		}
		if (pageNum != null) {
			orderQuery.setPageNumber(Integer.parseInt(pageNum));
		}
		if (pageSize != null) {
			orderQuery.setPageSize(Integer.parseInt(pageSize));
		}
		if(orderQuery.getCreator()!=null)
		{
			if(orderQuery.getCreator()== -1){
				orderQuery.setCreator(null);
			}
		}
		Page<CL_Order> page = orderDao.findPage(orderQuery);
		HashMap<String, Object> m = new HashMap<String, Object>();
		m.put("total", page.getTotalCount());
		for(CL_Order coinfo : page.getResult()){
			BigDecimal faddNumber = BigDecimal.ZERO;
			List<CL_Addto> addto=iaddtoDao.getByOrderId(coinfo.getId());
			BigDecimal AllCost=coinfo.getFreight();
			if(addto.size()>0){
				for(CL_Addto ad:addto){
					faddNumber=faddNumber.add(ad.getFcost());
		    	}
			}
			coinfo.setFaddNumber(faddNumber);
			AllCost=AllCost.add(faddNumber);
			if(coinfo.getFispass_audit() != null && coinfo.getFispass_audit() == 1){//审批通过的
				coinfo.setFtotal(coinfo.getFreight().add(coinfo.getFaddNumber()).add(coinfo.getServe_1() == null?BigDecimal.ZERO:coinfo.getServe_1()).add(coinfo.getServe_2() == null?BigDecimal.ZERO:coinfo.getServe_2()));
			} else {//审批不同过，或者未审批的
				coinfo.setFtotal(coinfo.getForiginfreight().add(coinfo.getFaddNumber()).add(coinfo.getServe_1() == null?BigDecimal.ZERO:coinfo.getServe_1()).add(coinfo.getServe_2() == null?BigDecimal.ZERO:coinfo.getServe_2()));
			}
//			//司机价格分离需求变动---------------------by lancher begin
//			//判断订单司机是否存在协议规则
//			Integer driverId = coinfo.getUserRoleId();
//			if(driverId != null){
//				Integer carSpecId = carDao.getByUserRoleId(driverId).get(0).getCarSpecId();
//				List<CL_Protocol> list = protocolDao.getBySpecAndType(carSpecId, driverId, 4);
//				if(list.size() > 0){//判断司机是否有协议
//					//起步公里数
//					BigDecimal startK = list.get(0).getStartKilometre();
//					//起步价
//					BigDecimal startP = list.get(0).getStartPrice();
//					//超出公里计价
//					BigDecimal outK = list.get(0).getOutKilometre();
//					//订单公里数
//					BigDecimal mileage = coinfo.getMileage();
//					//增值服务是否有装卸
//					String fincrementServe = coinfo.getFincrementServe();
//					if(fincrementServe == null){
//						fincrementServe = "";
//					}
//					if(fincrementServe.indexOf("装卸") != -1){//有装卸
//						//判断公里数是否超出起步公里
//						if(mileage.compareTo(startK) == -1){//未超出
//							//9.6米不支持
//							if(coinfo.getCar_spec_id() != 6){
//								coinfo.setFdriverAllin(startP.add(new BigDecimal(list.get(0).getFadd_service())));//未超出
//							} else {
//								coinfo.setFdriverAllin(startP);
//							}
//						} else {
//							if(coinfo.getCar_spec_id() != 6){
//								coinfo.setFdriverAllin(startP.add((mileage.subtract(startK)).multiply(outK)).add(new BigDecimal(list.get(0).getFadd_service())).setScale(1, BigDecimal.ROUND_HALF_UP));
//							} else {
//								coinfo.setFdriverAllin(startP.add((mileage.subtract(startK)).multiply(outK)).setScale(1, BigDecimal.ROUND_HALF_UP));//超出
//							}
//						}
//					} else {
//						//判断公里数是否超出起步公里
//						if(mileage.compareTo(startK) == -1){
//							coinfo.setFdriverAllin(startP);//未超出
//						} else {
//							coinfo.setFdriverAllin(startP.add((mileage.subtract(startK)).multiply(outK)).setScale(1, BigDecimal.ROUND_HALF_UP));//超出
//						}
//						
//					}
//					
//					
//				} else{
//					//没有协议则实行通用规则
//					if(coinfo.getCar_spec_id() != null){
//						Integer ruleId = ruleDao.getDriverBySpec(coinfo.getCar_spec_id()).get(0).getId();
//						CL_Rule rule = ruleDao.getById(ruleId);
//						//超出是每公里单价
//						BigDecimal kilometrePrice = rule.getKilometrePrice();
//						//起步公里数
//						BigDecimal startKilometre = rule.getStartKilometre();
//						//起步价
//						BigDecimal startPrice = rule.getStartPrice();
//						//订单公里数
//						BigDecimal mileage = coinfo.getMileage();
//						String fincrementServe = coinfo.getFincrementServe();
//						if(fincrementServe == null){
//							fincrementServe = "";
//						}
//						if(fincrementServe.indexOf("装卸") != -1){//有增值服务
//							if(mileage.compareTo(startKilometre) == -1){//未超出起步公里
//								//9.6米不支持
//								if(coinfo.getCar_spec_id() != 6){
//									coinfo.setFdriverAllin(startPrice.add(new BigDecimal(rule.getFadd_service())));
//								} else {
//									coinfo.setFdriverAllin(startPrice);
//								}
//							} else {//超出起步公里
//								//9.6米不支持
//								if(coinfo.getCar_spec_id() != 6){
//									System.out.println("增值"+new BigDecimal(rule.getFadd_service()));
//									coinfo.setFdriverAllin(startPrice.add((mileage.subtract(startKilometre)).multiply(kilometrePrice)).add(new BigDecimal(rule.getFadd_service())).setScale(1, BigDecimal.ROUND_HALF_UP));
//								} else {
//									coinfo.setFdriverAllin(startPrice.add((mileage.subtract(startKilometre)).multiply(kilometrePrice)).setScale(1, BigDecimal.ROUND_HALF_UP));
//								}
//							}
//						} else {//无增值服务
//							if(mileage.compareTo(startKilometre) == -1){//未超出起步公里
//								coinfo.setFdriverAllin(startPrice);
//							} else {
//								coinfo.setFdriverAllin(startPrice.add((mileage.subtract(startKilometre)).multiply(kilometrePrice)).setScale(1, BigDecimal.ROUND_HALF_UP));
//							}
//						}
//					}
//				}
//			}else {
//				coinfo.setFdriverAllin(new BigDecimal(0));
//			}
//			//司机价格分离需求变动---------------------by lancher end
			//旧数据因计算格式不同，会存在出入，加控制备份数据,手动加载一次就性了
//			if(coinfo.getForigin_driverfee() == null){
//				coinfo.setForigin_driverfee(AllCost.multiply(CalcTotalField.calDriverFee(AllCost)).setScale(1,BigDecimal.ROUND_HALF_UP));
//				orderDao.update(coinfo);
//			}
//			if(coinfo.getForiginfreight() == null){
//				coinfo.setForiginfreight(coinfo.getFreight());
//				orderDao.update(coinfo);
//			}
//			coinfo.setFdriverAllin(AllCost.multiply(CalcTotalField.calDriverFee(AllCost)).setScale(1,BigDecimal.ROUND_HALF_UP));
		}
		m.put("rows", page.getResult());
		return writeAjaxResponse(reponse, JSONUtil.getJson(m));
	}
	/*** 加载订单明细信息*/
	@RequestMapping("/order/detailLoader")
	public String detailLoader(HttpServletRequest request,HttpServletResponse reponse) throws Exception{
		Integer orderId = Integer.parseInt(request.getParameter("orderId"));
		List<CL_OrderDetail> detail = orderDetailDao.getByPCOrderIdForDeliver(orderId);
		HashMap<String, Object> m = new HashMap<String, Object>();
		m.put("total", detail.size());
		m.put("rows", detail);
		return writeAjaxResponse(reponse, JSONUtil.getJson(m));
	}
	
	/*** 取消订单*/
	//TODO:缺少退款步骤
	@RequestMapping("/order/cancelOrder")
	public String cancelOrder(HttpServletRequest request,HttpServletResponse reponse,Integer[] ids) throws Exception{
		HashMap<String, Object> map=new HashMap<String,Object>();
		HashMap<String, Object> param=new HashMap<String,Object>();
		for(Integer id:ids){
			CL_Order order = this.orderDao.getById(id);
			if(order.getStatus()>2){
				map.put("success", "false");
				map.put("msg", "编号为"+order.getNumber()+"取消失败,请选择状态为代付款、派车中的订单");
				return writeAjaxResponse(reponse,JSONUtil.getJson(map));
			}
			if(order.getStatus()==2){//派车中，运输中
				if(order.getFispass_audit() != null && order.getFispass_audit() == 0){//建议使用分情况退款，因为审批并没有驳回功能，这样就必须审批再退款，做了无用功
					map.put("success", "false");
					map.put("msg", "该订单的运费调整尚处于待审核状态，请先审批");
					return writeAjaxResponse(reponse,JSONUtil.getJson(map));
				}
				if(order.getFpayMethod()!=5)//非5月结
				{
					List<CL_Addto> adds=iaddtoDao.getByOrderId(order.getId());//该订单的所有追加
					String AllNumber="";
					BigDecimal AllCost=new BigDecimal(0);
					BigDecimal CouponsDollars=new BigDecimal(0);
					CL_CouponsDetail cpsDetail=null;
					CL_CouponsDetail cpsDetails=null;

					/*if(adds.size()>0){
						for(CL_Addto add:adds){
							AllNumber+=AllNumber+add.getNumber()+",";//追加费用流水
							AllCost=AllCost.add(add.getFcost());
						}
					} */
					
					//AllCost  某订单所有追加费用
					/***** 月结、运费到付支持运营后台取消订单  by Cici 2016/9/5*****/
					if(order.getFpayMethod()!=4) //非4运费到付
					{
						if(order.getCouponsDetailId()!=null){
							cpsDetail=icouponsDetailDao.getById(order.getCouponsDetailId());
							if(cpsDetail!=null){//订单有使用好运卷
								param.put("cpsDetail", cpsDetail);
								/*CouponsDollars=cpsDetail.getDollars();
								//新逻辑
								if(order.getFreight().compareTo(CouponsDollars) > 0){
									AllCost=AllCost.subtract(CouponsDollars); //减好运卷面额 by twr 2016-6-21 
								}*/
								//新逻辑
//								AllCost=AllCost.subtract(CouponsDollars); //减好运卷面额 by twr 2016-6-21 
							}
						}
						if(order.getCouponsId()!=null){
							cpsDetails = icouponsDetailDao.getById(order.getCouponsId());
							if(cpsDetails!=null){
								param.put("cpsDetails", cpsDetails);
							}
						}
						//新逻辑 若果好运券的金额比运费高，那么货主并未付钱，所以只需退款追加的金额即可
//						if(order.getFreight().compareTo(CouponsDollars) > 0){
							AllCost=order.getFtotalFreight();//实付款+追加费用							
//						}
						//新逻辑
//						AllCost=AllCost.add(order.getFreight());//订单运费-好运卷金额+追加费用							
					}
					//AllCost 需要返还的金额
					if(!"".equals(AllNumber)){
						AllNumber=AllNumber+order.getNumber();
					}	
					if("".equals(AllNumber)){
						AllNumber=order.getNumber();
					}
					if(AllCost.compareTo(new BigDecimal(0))!=0)
					{
						int userId = orderDao.getById(id).getCreator();
						CL_UserRole user = userRoleDao.getById(userId);
						CL_FinanceStatement statement = new CL_FinanceStatement();
						String number = CacheUtilByCC.getOrderNumber("cl_finance_statement", "L", 8);
						statement.setNumber(number);
						statement.setFrelatedId(order.getId().toString());
						statement.setForderId(order.getNumber());
						statement.setFbusinessType(8);
						statement.setFamount(AllCost);
						statement.setFtype(1);
						statement.setFuserroleId(userId);
						statement.setFuserid(user.getVmiUserFid());
						statement.setFpayType(0);
						statement.setFbalance(BigDecimal.ZERO);
						statement.setFcreateTime(new Date());
						statement.setFreight(AllCost);
						statement.setFremark(order.getNumber()+"取消订单");
						financeStatementDao.save(statement);
						OrderMsg orderMsg = new OrderMsg();
						orderMsg.setOrder(number);
						orderMsg.setPayState("1");
						orderMsg.setServiceProviderType("0");
						orderMsg.setServiceProvider("0");
						orderMsg.setUserId(Integer.parseInt(request.getSession().getAttribute("userRoleId").toString()));
						param.put("is_update", 0);//不需要调整业务
						param.put("order", order);
						int result = financeStatementDao.updateBusinessType(orderMsg,param);
						if(result != 1){
							return writeAjaxResponse(reponse, "false");
						}
					} else {//运费到付，客户还没付过钱，直接取消订单即可
						order.setStatus(6);
						if(order.getCouponsDetailId()!=null){
							cpsDetail=icouponsDetailDao.getById(order.getCouponsDetailId());
							cpsDetail.setIsUse(0);
							icouponsDetailDao.update(cpsDetail);
							order.setCouponsDetailId(null);
						}
						if(order.getCouponsId()!=null){
							cpsDetails = icouponsDetailDao.getById(order.getCouponsId());
							cpsDetails.setIsUse(0);
							icouponsDetailDao.update(cpsDetails);
							order.setCouponsId(null);
						}
						order.setOperator(Integer.parseInt(request.getSession().getAttribute("userRoleId").toString()));
						order.setFoperate_time(new Date());
						orderDao.update(order);
					}

				} else {
					List<CL_Addto> addList = iaddtoDao.getByOrderId(order.getId());
					BigDecimal allCost = BigDecimal.ZERO;
					for (CL_Addto add : addList) {
						if(add.getFpayMethod() == null){//月结的有支付方式
							allCost = allCost.add(add.getFcost());//这部分是要退还的
						}
					}
					if(allCost.compareTo(BigDecimal.ZERO) > 0){
						int userId = orderDao.getById(id).getCreator();
						CL_UserRole user = userRoleDao.getById(userId);
						CL_FinanceStatement statement = new CL_FinanceStatement();
						String number = CacheUtilByCC.getOrderNumber("cl_finance_statement", "L", 8);
						statement.setNumber(number);
						statement.setFrelatedId(order.getId().toString());
						statement.setForderId(order.getNumber());
						statement.setFbusinessType(8);
						statement.setFamount(allCost);
						statement.setFtype(1);
						statement.setFuserroleId(userId);
						statement.setFuserid(user.getVmiUserFid());
						statement.setFpayType(0);
						statement.setFbalance(BigDecimal.ZERO);
						statement.setFcreateTime(new Date());
						statement.setFreight(allCost);
						statement.setFremark(order.getNumber()+"取消月结订单，退还非月结的追加费用");
						financeStatementDao.save(statement);
						OrderMsg orderMsg = new OrderMsg();
						orderMsg.setOrder(number);
						orderMsg.setPayState("1");
						orderMsg.setServiceProviderType("0");
						orderMsg.setServiceProvider("0");
						orderMsg.setUserId(Integer.parseInt(request.getSession().getAttribute("userRoleId").toString()));
						param.put("is_update", 0);//不需要调整业务
						param.put("order", order);
						int result = financeStatementDao.updateBusinessType(orderMsg,param);
						if(result != 1){
							return writeAjaxResponse(reponse, "false");
						}
					}
					order.setStatus(6);
					order.setOperator(Integer.parseInt(request.getSession().getAttribute("userRoleId").toString()));
					order.setFoperate_time(new Date());
					orderDao.update(order);
				}
			}
			
		}
		return writeAjaxResponse(reponse, "success");
	}
	
	
	/**导出excel**/
	@RequestMapping("/order/exportExecl")
	public String exportExecl(HttpServletRequest request,HttpServletResponse reponse,@ModelAttribute OrderQuery orderQuery,Integer[] ids) throws Exception{
		boolean ises =true;
		/***************/
		Map<String, Object> result = new HashMap<String, Object>();
		WritableWorkbook wwb = null;
		OutputStream os = null;
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
		String fileName = "ORDER_"+format.format(new Date())+".xls";
		String path  = OrderController.class.getResource("").toURI().getPath();
		path = path.substring(0,path.lastIndexOf("/WEB-INF"))+"/excel/"+fileName;
		System.out.println(path);
		
		try {
			File file = new File(path);
			if(!file.isFile()){
				file.createNewFile();
			}
			os = new FileOutputStream(file);
			wwb = Workbook.createWorkbook(os);
			WritableSheet wsheet = wwb.createSheet("物流订单", 0);//创建一个工作页，第一个参数的页名，第二个参数表示该工作页在excel中处于哪一页
			SheetSettings ss = wsheet.getSettings();
            ss.setVerticalFreeze(2);  // 设置行冻结前2行
            WritableFont font1 =new WritableFont(WritableFont.createFont("微软雅黑"), 10 ,WritableFont.BOLD);
            WritableFont font2 =new WritableFont(WritableFont.createFont("微软雅黑"), 9 ,WritableFont.NO_BOLD);
            WritableCellFormat wcf = new WritableCellFormat(font1);	  //设置样式，字体
	            wcf.setAlignment(Alignment.CENTRE);                   //平行居中
	            wcf.setVerticalAlignment(VerticalAlignment.CENTRE);   //垂直居中
            WritableCellFormat wcf2 = new WritableCellFormat(font2);  //设置样式，字体
	            wcf2.setBackground(Colour.LIGHT_ORANGE);
	            wcf2.setAlignment(Alignment.CENTRE);                  //平行居中
	            wcf2.setVerticalAlignment(VerticalAlignment.CENTRE);  //垂直居中
	            wcf2.setWrap(true);  
            wsheet.mergeCells( 0 , 0 , 14 , 0 ); // 合并单元格  
            Label titleLabel = new Label( 0 , 0 , " 同城物流调度平台 ",wcf);
            wsheet.addCell(titleLabel);
            wsheet.setRowView(0, 1000); // 设置第一行的高度
//            int[] headerArrHight = {10,15,25,25,20,20,20,20,20,20,20,20,20,20,60,25,20,20,20,25,20,25,20,20,60,60,20,20,15,15,15,25,15,15};
            int[] headerArrHight = {10,15,10,10,25,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,25,20,20,25,20,20,25,20,25,20,10,20};
            String headerArr[] = {"序号","订单类型","协议用车","回程车","订单编号","所需车辆类型","所需车辆规格","用户","客户","客户类型","业务员","里程","装货费","卸货费","增值优惠券","订单优惠券","实付运费","运费","总运费","货物类型","装车时间","司机名称","车牌号","司机电话","创建时间","司机运费","状态","提货点","卸货点","提货联系方式","支付方式","卸货点数","增值服务项目"};
//            String headerArr[] = {"序号","订单类型","协议用车","订单编号","所需车辆类型","所需车辆规格","用户","客户","客户编码","客户类型","业务员","里程","运费","货物类型","装车时间","重量","体积","长度","司机名称","司机电话","创建时间","司机运费","状态","提货点","卸货点","支付方式","卸货点数","数量","单位类型","线下支付","增值服务项目","车型","车牌号"};
//            String headerArr[] = {"序号","协议用车","订单类型","订单编号","客户","客户编码","客户类型","业务员","提货点","提货联系方式","增值服务费用","总运费","里程","运费","货物类型","装车时间","到达提货点时间","离开提货点时间","重量","体积","长度","司机电话"};
            for (int i = 0; i < headerArr.length; i++) {
            	wsheet.addCell(new Label( i , 1 , headerArr[i],wcf));
            	wsheet.setColumnView(i, headerArrHight[i]);	
            }
            if (orderQuery == null) {
    			orderQuery = newQuery(OrderQuery.class, null);
    		}
            if(ids!=null){
            	orderQuery.setIds(ids);
            }
    		List<CL_Order> list = orderDao.find(orderQuery);
    		int conut = 2;
    		System.out.println("list长度-----"+list.size());//EAS--list=0 
    		for(int i=0;i<list.size();i++){
    			//2016-5-17 by  lu 多个卸货点  需要多条显示
    			List<CL_OrderDetail> listdts = orderDetailDao.getByOrderId(list.get(i).getId(), 2);
    			
    			//订单管理导出新增提货点及司机总运费;
    			List<CL_OrderDetail> listthd = orderDetailDao.getByOrderId(list.get(i).getId(), 1);
    			String addressNameThd = listthd.get(0).getAddressName();
    			String linkPhone = listthd.get(0).getPhone();
    			/*String xiehuo = "";
    			orderQuery.setDetail_type(2);//卸货
    			orderQuery.setId(list.get(i).getId());;//卸货
        		List<CL_Order> list2 = orderDao.find(orderQuery);
        		if(list2.size() > 0){
        			xiehuo = list2.get(0).getAddressName() == null?"":list2.get(0).getAddressName();
        		}*/
    			
    			BigDecimal faddNumber = BigDecimal.ZERO;
    			List<CL_Addto> addto=iaddtoDao.getByOrderId(list.get(i).getId());
    			BigDecimal AllCost=list.get(i).getFreight();
    			if(addto.size()>0){
    				for(CL_Addto ad:addto){
    					faddNumber=faddNumber.add(ad.getFcost());
    		    	}
    			}
    			AllCost=AllCost.add(faddNumber);
    			String FdriverAllin = AllCost.multiply(CalcTotalField.calDriverFee(AllCost)).setScale(2,BigDecimal.ROUND_HALF_UP).toString();
    			
    			if(listdts.size()>0){
//    				for(int j=0;j<listdts.size();j++){
    				for(int j=0;j<1;j++){
    					
    	    			jxl.write.NumberFormat nf2 = new jxl.write.NumberFormat("0.00");//设置数字格式djchd
    	    			jxl.write.WritableCellFormat wcfN2 = new jxl.write.WritableCellFormat(nf2);//设置表单格式   
    	    			wcfN2.setBackground(Colour.LIGHT_ORANGE);
    	    			wcfN2.setAlignment(Alignment.CENTRE);                  //平行居中
    	    			wcfN2.setVerticalAlignment(VerticalAlignment.CENTRE);  //垂直居中
    	    			wsheet.addCell(new Label( 0 , conut ,String.valueOf(i+1),wcf2));//序号
    	    			wsheet.addCell(new Label( 1 , conut ,typeName(list.get(i).getType().toString()),wcf2));//1订单类型
            			wsheet.addCell(new Label( 2 , conut ,typeProtocol(list.get(i).getProtocolType().toString()) ,wcf2));//2协议用车（0 否1是）
            			wsheet.addCell(new Label( 3 , conut ,typeReturn(list.get(i).getFreturn_car()) ,wcf2));//3回程车
    	    			wsheet.addCell(new Label( 4 , conut ,list.get(i).getNumber(),wcf2));//4订单编号
    	    			wsheet.addCell(new Label( 5 , conut ,applyToCarType(list.get(i).getCarTypeId()),wcf2));//5所需车辆类型
    	    			wsheet.addCell(new Label( 6 , conut ,typeCarSpec(list.get(i).getCarSpecId()),wcf2));//6所需车辆规格
    	    			wsheet.addCell(new Label( 7 , conut ,list.get(i).getCreatorName() ,wcf2));//7用户名
            			wsheet.addCell(new Label( 8 , conut ,list.get(i).getCustName() ,wcf2));//8客户
//            			wsheet.addCell(new Label( 8 , conut ,list.get(i).getCustNumber() ,wcf2));//客户编码
            			wsheet.addCell(new Label( 9 , conut ,list.get(i).getCustType() ,wcf2));//9客户类型
            			wsheet.addCell(new Label( 10 , conut ,list.get(i).getCustSaleMan() ,wcf2));//10业务员
            			//------------------
            			wsheet.addCell(new Label( 11 , conut ,list.get(i).getMileage() == null?"":list.get(i).getMileage().toString() ,wcf2));//里程
            			wsheet.addCell(new Label( 12 , conut ,list.get(i).getServe_1() == null?"":list.get(i).getServe_1().toString() ,wcf2));//装货费
            			wsheet.addCell(new Label( 13 , conut ,list.get(i).getServe_2() == null?"":list.get(i).getServe_2().toString() ,wcf2));//卸货费
            			wsheet.addCell(new Label( 14 , conut ,list.get(i).getSubtract() == null?"":list.get(i).getSubtract().toString() ,wcf2));//增值服务券
            			wsheet.addCell(new Label( 15 , conut ,list.get(i).getDiscount() == null?"":list.get(i).getDiscount().toString() ,wcf2));//订单优惠券
            			wsheet.addCell(new Label( 16, conut ,list.get(i).getFtotalFreight() == null?"":list.get(i).getFtotalFreight().toString(),wcf2));//11实际运费
            			wsheet.addCell(new Label( 17 , conut ,list.get(i).getFreight().toString() ,wcf2));//12运费(优惠券分离需求设计到运费，改动)
            			list.get(i).setFaddNumber(faddNumber);
            			if(list.get(i).getFispass_audit() != null && list.get(i).getFispass_audit() == 1){//审批通过的
            				list.get(i).setFtotal(list.get(i).getFreight().add(list.get(i).getFaddNumber()).add(list.get(i).getServe_1() == null?BigDecimal.ZERO:list.get(i).getServe_1()).add(list.get(i).getServe_2() == null?BigDecimal.ZERO:list.get(i).getServe_2()));
            			} else {//审批不同过，或者未审批的
            				list.get(i).setFtotal(list.get(i).getForiginfreight().add(list.get(i).getFaddNumber()).add(list.get(i).getServe_1() == null?BigDecimal.ZERO:list.get(i).getServe_1()).add(list.get(i).getServe_2() == null?BigDecimal.ZERO:list.get(i).getServe_2()));
            			}
            			wsheet.addCell(new Label( 18 , conut ,list.get(i).getFtotal().toString() ,wcf2));//总运费
            			//----------------------
            			wsheet.addCell(new Label( 19 , conut ,list.get(i).getGoodsTypeName() ,wcf2));//13货物类型
            			wsheet.addCell(new Label( 20 , conut ,list.get(i).getLoadedTimeString() ,wcf2));//14装车时间
            			wsheet.addCell(new Label( 21 , conut ,list.get(i).getOrderDriverName() == null?"":list.get(i).getOrderDriverName() ,wcf2));//15司机名称
            			wsheet.addCell(new Label( 22 , conut ,list.get(i).getOrderDriverCarNumber() == null?"":list.get(i).getOrderDriverCarNumber() ,wcf2));//16车牌号
            			wsheet.addCell(new Label( 23 , conut ,list.get(i).getOrderDriverphone() == null?"":list.get(i).getOrderDriverphone() ,wcf2));//17司机电话
            			wsheet.addCell(new Label( 24 , conut ,list.get(i).getCreateTimeString() ,wcf2));//18创建时间
            			wsheet.addCell(new Label( 25 , conut ,list.get(i).getFdriverfee() == null?"":list.get(i).getFdriverfee().toString() ,wcf2));//19司机运费
            			wsheet.addCell(new Label( 26 , conut ,typeStatus(list.get(i).getStatus()) ,wcf2));//20状态
            			wsheet.addCell(new Label( 27 , conut ,addressNameThd ,wcf2));//21提货点
            			wsheet.addCell(new Label( 28 , conut ,listdts.get(j).getAddressName() ,wcf2));//23卸货点
            			wsheet.addCell(new Label( 29 , conut ,list.get(i).getTakephone() ,wcf2));//22提货联系方式
            			wsheet.addCell(new Label( 30 , conut ,paymethodName(list.get(i).getFpayMethod()==null?"":list.get(i).getFpayMethod().toString()) ,wcf2));//24支付方式
            			wsheet.addCell(new Label( 31 , conut ,list.get(i).getFopint() == null?"":list.get(i).getFopint().toString() ,wcf2));//25卸货点数
            			wsheet.addCell(new Label( 32 , conut ,list.get(i).getFincrementServe() ,wcf2));//26增值服务项目
            			
            			/*
            			wsheet.addCell(new Label( 13, conut ,linkPhone ,wcf2));//13提货联系方式
            			wsheet.addCell(new Label( 14, conut ,"0" ,wcf2));//14增值服务费
            			wsheet.addCell(new Label( 15, conut ,list.get(i).getFincrementServe(),wcf2));//15增值服务项目
            			wsheet.addCell(new Label( 16, conut ,typeAudit(list.get(i).getFispass_audit()),wcf2));//16审批状态
            			wsheet.addCell(new Label( 18, conut ,list.get(i).getFdriverfee().toString(),wcf2));//18司机运费
*/            			//19里程
            			//20运费
            			//21追加费用
            			//22审核人
            			//23审核时间
            			//24调整人
            			//25调整时间
            			//26货物类型
            			//27装车时间
            			//28到达提货点时间
            			//29离开提货点时间
            			//30重量
            			//31体积
            			//32长度
            			//33司机名称
            			//34司机电话
            			//35车型
            			//36车牌号
            			//37创建时间
            			//38状态
            			//39代收金额
            			//40支付方式
            			//41卸货点数
            			//42操作人
            			//43数量
            			//44单位类型
            			//45线下支付
            			//46备注
            			//47初始运费
            			//48修改业务员
            			//49修改时间
            			
    	    			//订单包天类型判断;
    	    			/*if(list.get(i).getType()!=3){
    	    				wsheet.addCell(new jxl.write.Number(11,conut,Double.parseDouble(list.get(i).getMileage()==null?"0":list.get(i).getMileage().toString()), wcfN2));
    	    				wsheet.addCell(new jxl.write.Number(15,conut,Double.parseDouble(list.get(i).getWeight()==null?"0":list.get(i).getWeight().toString()), wcfN2));
    	        			wsheet.addCell(new jxl.write.Number(16,conut,Double.parseDouble(list.get(i).getVolume()==null?"0":list.get(i).getVolume().toString()), wcfN2));
    	        			wsheet.addCell(new jxl.write.Number(17,conut,Double.parseDouble(list.get(i).getLength()==null?"0":list.get(i).getLength().toString()), wcfN2));
    	    			}else{
    	    				wsheet.addCell(new jxl.write.Number(11,conut,0, wcfN2));
    	    				wsheet.addCell(new jxl.write.Number(15,conut,0, wcfN2));
    	        			wsheet.addCell(new jxl.write.Number(16,conut,0, wcfN2));
    	        			wsheet.addCell(new jxl.write.Number(17,conut,0, wcfN2));
    	    			}*/
    	    			
    	    			/*wsheet.addCell(new jxl.write.Number(12,conut,Double.parseDouble(list.get(i).getFreight()==null?"0":list.get(i).getFreight().toString()), wcfN2));
    	    			wsheet.addCell(new Label( 13 , conut ,list.get(i).getGoodsTypeName() ,wcf2));
    	    			wsheet.addCell(new Label( 14 , conut ,list.get(i).getLoadedTimeString() ,wcf2));
    	    			
    	    			wsheet.addCell(new Label( 18 , conut ,list.get(i).getOrderDriverName(),wcf2));
    	    			wsheet.addCell(new Label( 19 , conut ,list.get(i).getOrderDriverphone(),wcf2));
    	    			wsheet.addCell(new Label( 20 , conut ,list.get(i).getCreateTimeString() ,wcf2));
    	    			wsheet.addCell(new Label( 21 , conut, list.get(i).getFdriverfee() == null?"":list.get(i).getFdriverfee().toString(), wcfN2));//司机总运费改成司机运费，跟追加分开
    	    			wsheet.addCell(new Label( 22, conut ,statusName(list.get(i).getStatus().toString()) ,wcf2));
        				wsheet.addCell(new Label( 23, conut ,addressNameThd ,wcf2));
        				wsheet.addCell(new Label( 24, conut ,listdts.get(j).getAddressName() ,wcf2));
        				wsheet.addCell(new Label( 25, conut ,paymethodName(list.get(i).getFpayMethod()==null?"":list.get(i).getFpayMethod().toString()) ,wcf2));
        				wsheet.addCell(new Label( 26, conut ,list.get(i).getFopint()==null?"":list.get(i).getFopint().toString() ,wcf2));
        				wsheet.addCell(new Label( 27, conut ,list.get(i).getFamount()==null?"":list.get(i).getFamount().toString() ,wcf2));
        				wsheet.addCell(new Label( 28, conut ,list.get(i).getFunitId()==null?"":funitToName(list.get(i).getFunitId().toString()) ,wcf2));
        				wsheet.addCell(new Label( 29, conut ,list.get(i).getFonlinePay()==0?"未选择":(list.get(i).getFonlinePay()==1?"线上":"线下") ,wcf2));
        				wsheet.addCell(new Label( 30, conut ,list.get(i).getFincrementServe(),wcf2));
        				wsheet.addCell(new Label( 31, conut ,list.get(i).getCarType()==null?"":applyToCarType(list.get(i).getCarType()),wcf2));
        				wsheet.addCell(new Label( 32, conut ,list.get(i).getOrderDriverCarNumber(),wcf2));*/
        				conut++;
        			}
    			}else{
        			jxl.write.NumberFormat nf2 = new jxl.write.NumberFormat("0.00");//设置数字格式
        			jxl.write.WritableCellFormat wcfN2 = new jxl.write.WritableCellFormat(nf2);//设置表单格式   
        			wcfN2.setBackground(Colour.LIGHT_ORANGE);
        			wcfN2.setAlignment(Alignment.CENTRE);                  //平行居中
        			wcfN2.setVerticalAlignment(VerticalAlignment.CENTRE);  //垂直居中
        			
        			wsheet.addCell(new Label( 0 , conut ,String.valueOf(i+1),wcf2));//序号
	    			wsheet.addCell(new Label( 1 , conut ,typeName(list.get(i).getType().toString()),wcf2));//1订单类型
        			wsheet.addCell(new Label( 2 , conut ,typeProtocol(list.get(i).getProtocolType().toString()) ,wcf2));//2协议用车（0 否1是）
        			wsheet.addCell(new Label( 3 , conut ,typeReturn(list.get(i).getFreturn_car()) ,wcf2));//3回程车
	    			wsheet.addCell(new Label( 4 , conut ,list.get(i).getNumber(),wcf2));//4订单编号
	    			wsheet.addCell(new Label( 5 , conut ,applyToCarType(list.get(i).getCarTypeId()),wcf2));//5所需车辆类型
	    			wsheet.addCell(new Label( 6 , conut ,typeCarSpec(list.get(i).getCarSpecId()),wcf2));//6所需车辆规格
	    			wsheet.addCell(new Label( 7 , conut ,list.get(i).getCreatorName() ,wcf2));//7用户名
        			wsheet.addCell(new Label( 8 , conut ,list.get(i).getCustName() ,wcf2));//8客户
//        			wsheet.addCell(new Label( 8 , conut ,list.get(i).getCustNumber() ,wcf2));//客户编码
        			wsheet.addCell(new Label( 9 , conut ,list.get(i).getCustType() ,wcf2));//9客户类型
        			wsheet.addCell(new Label( 10 , conut ,list.get(i).getCustSaleMan() ,wcf2));//10业务员
        			//------------------
        			wsheet.addCell(new Label( 11 , conut ,list.get(i).getMileage() == null?"":list.get(i).getMileage().toString() ,wcf2));//里程
        			wsheet.addCell(new Label( 12 , conut ,list.get(i).getServe_1() == null?"":list.get(i).getServe_1().toString() ,wcf2));//装货费
        			wsheet.addCell(new Label( 13 , conut ,list.get(i).getServe_2() == null?"":list.get(i).getServe_2().toString() ,wcf2));//卸货费
        			wsheet.addCell(new Label( 14 , conut ,list.get(i).getSubtract() == null?"":list.get(i).getSubtract().toString() ,wcf2));//增值服务券
        			wsheet.addCell(new Label( 15 , conut ,list.get(i).getDiscount() == null?"":list.get(i).getDiscount().toString() ,wcf2));//订单优惠券
        			wsheet.addCell(new Label( 16, conut ,list.get(i).getFtotalFreight() == null?"":list.get(i).getFtotalFreight().toString(),wcf2));//11实际运费
        			wsheet.addCell(new Label( 17 , conut ,list.get(i).getFreight().toString() ,wcf2));//12运费(优惠券分离需求设计到运费，改动)
        			list.get(i).setFaddNumber(faddNumber);
        			if(list.get(i).getFispass_audit() != null && list.get(i).getFispass_audit() == 1){//审批通过的
        				list.get(i).setFtotal(list.get(i).getFreight().add(list.get(i).getFaddNumber()).add(list.get(i).getServe_1() == null?BigDecimal.ZERO:list.get(i).getServe_1()).add(list.get(i).getServe_2() == null?BigDecimal.ZERO:list.get(i).getServe_2()));
        			} else {//审批不同过，或者未审批的
        				list.get(i).setFtotal(list.get(i).getForiginfreight().add(list.get(i).getFaddNumber()).add(list.get(i).getServe_1() == null?BigDecimal.ZERO:list.get(i).getServe_1()).add(list.get(i).getServe_2() == null?BigDecimal.ZERO:list.get(i).getServe_2()));
        			}
        			wsheet.addCell(new Label( 18 , conut ,list.get(i).getFtotal().toString() ,wcf2));//总运费
        			//----------------------
        			wsheet.addCell(new Label( 19 , conut ,list.get(i).getGoodsTypeName() ,wcf2));//13货物类型
        			wsheet.addCell(new Label( 20 , conut ,list.get(i).getLoadedTimeString() ,wcf2));//14装车时间
        			wsheet.addCell(new Label( 21 , conut ,list.get(i).getOrderDriverName() == null?"":list.get(i).getOrderDriverName() ,wcf2));//15司机名称
        			wsheet.addCell(new Label( 22 , conut ,list.get(i).getOrderDriverCarNumber() == null?"":list.get(i).getOrderDriverCarNumber() ,wcf2));//16车牌号
        			wsheet.addCell(new Label( 23 , conut ,list.get(i).getOrderDriverphone() == null?"":list.get(i).getOrderDriverphone() ,wcf2));//17司机电话
        			wsheet.addCell(new Label( 24 , conut ,list.get(i).getCreateTimeString() ,wcf2));//18创建时间
        			wsheet.addCell(new Label( 25 , conut ,list.get(i).getFdriverfee() == null?"":list.get(i).getFdriverfee().toString() ,wcf2));//19司机运费
        			wsheet.addCell(new Label( 26 , conut ,typeStatus(list.get(i).getStatus()) ,wcf2));//20状态
        			wsheet.addCell(new Label( 27 , conut ,addressNameThd ,wcf2));//21提货点
        			wsheet.addCell(new Label( 28 , conut ,"" ,wcf2));//23卸货点
        			wsheet.addCell(new Label( 29 , conut ,list.get(i).getTakephone() ,wcf2));//22提货联系方式
        			wsheet.addCell(new Label( 30 , conut ,paymethodName(list.get(i).getFpayMethod()==null?"":list.get(i).getFpayMethod().toString()) ,wcf2));//24支付方式
        			wsheet.addCell(new Label( 31 , conut ,list.get(i).getFopint()==null?"":list.get(i).getFopint().toString() ,wcf2));//25卸货点数
        			wsheet.addCell(new Label( 32 , conut ,list.get(i).getFincrementServe() ,wcf2));//26增值服务项目
        			
        			/*wsheet.addCell(new Label( 0 , conut ,String.valueOf(i+1),wcf2));
        			wsheet.addCell(new Label( 1 , conut ,typeName(list.get(i).getType().toString()),wcf2));
        			//协议用车（0 否1是）
        			wsheet.addCell(new Label( 2 , conut ,typeProtocol(list.get(i).getProtocolType().toString()) ,wcf2));
        			
        			wsheet.addCell(new Label( 3 , conut ,list.get(i).getNumber(),wcf2));
        			wsheet.addCell(new Label( 4 , conut ,applyToCarType(list.get(i).getCarTypeId()),wcf2));
	    			wsheet.addCell(new Label( 5 , conut ,typeCarSpec(list.get(i).getCarSpecId()),wcf2));
        			wsheet.addCell(new Label( 6 , conut ,list.get(i).getCreatorName() ,wcf2));
        			wsheet.addCell(new Label( 7 , conut ,list.get(i).getCustName() ,wcf2));
        			wsheet.addCell(new Label( 8 , conut ,list.get(i).getCustNumber() ,wcf2));
        			wsheet.addCell(new Label( 9 , conut ,list.get(i).getCustType() ,wcf2));
        			wsheet.addCell(new Label( 10 , conut ,list.get(i).getCustSaleMan() ,wcf2));
       
        			//订单包天类型判断;
        			if(list.get(i).getType()!=3){
        				wsheet.addCell(new jxl.write.Number(11,conut,Double.parseDouble(list.get(i).getMileage()==null?"0":list.get(i).getMileage().toString()), wcfN2));
        				wsheet.addCell(new jxl.write.Number(15,conut,Double.parseDouble(list.get(i).getWeight()==null?"0":list.get(i).getWeight().toString()), wcfN2));
            			wsheet.addCell(new jxl.write.Number(16,conut,Double.parseDouble(list.get(i).getVolume()==null?"0":list.get(i).getVolume().toString()), wcfN2));
            			wsheet.addCell(new jxl.write.Number(17,conut,Double.parseDouble(list.get(i).getLength()==null?"0":list.get(i).getLength().toString()), wcfN2));
        			}else{
        				wsheet.addCell(new jxl.write.Number(11,conut,0, wcfN2));
        				wsheet.addCell(new jxl.write.Number(15,conut,0, wcfN2));
            			wsheet.addCell(new jxl.write.Number(16,conut,0, wcfN2));
            			wsheet.addCell(new jxl.write.Number(17,conut,0, wcfN2));
        			}
        			
        			wsheet.addCell(new jxl.write.Number(12,conut,Double.parseDouble(list.get(i).getFreight()==null?"0":list.get(i).getFreight().toString()), wcfN2));
        			wsheet.addCell(new Label( 13, conut ,list.get(i).getGoodsTypeName() ,wcf2));
        			wsheet.addCell(new Label( 14, conut ,list.get(i).getLoadedTimeString() ,wcf2));
        			
        			wsheet.addCell(new Label( 18 , conut ,list.get(i).getOrderDriverName(),wcf2));
        			wsheet.addCell(new Label( 19 , conut ,list.get(i).getOrderDriverphone(),wcf2));
        			wsheet.addCell(new Label( 20 , conut ,list.get(i).getCreateTimeString() ,wcf2));
        			wsheet.addCell(new Label( 21 , conut, list.get(i).getFdriverfee() == null?"":list.get(i).getFdriverfee().toString(), wcfN2));//司机总运费改成司机运费，跟追加分开
	    			wsheet.addCell(new Label( 22, conut ,statusName(list.get(i).getStatus().toString()) ,wcf2));
	    			wsheet.addCell(new Label( 23, conut ,addressNameThd ,wcf2));
	    			wsheet.addCell(new Label( 24, conut ,"" ,wcf2));
	    			wsheet.addCell(new Label( 25, conut ,paymethodName(list.get(i).getFpayMethod()==null?"":list.get(i).getFpayMethod().toString()) ,wcf2));
    				wsheet.addCell(new Label( 26, conut ,list.get(i).getFopint()==null?"":list.get(i).getFopint().toString() ,wcf2));
    				wsheet.addCell(new Label( 27, conut ,list.get(i).getFamount()==null?"":list.get(i).getFamount().toString() ,wcf2));
    				wsheet.addCell(new Label( 28, conut ,list.get(i).getFunitId()==null?"":funitToName(list.get(i).getFunitId().toString()) ,wcf2));
    				wsheet.addCell(new Label( 29, conut ,list.get(i).getFonlinePay()==0?"未选择":(list.get(i).getFonlinePay()==1?"线上":"线下") ,wcf2));
    				wsheet.addCell(new Label( 30, conut ,list.get(i).getFincrementServe(),wcf2));
    				wsheet.addCell(new Label( 31, conut ,list.get(i).getCarType()==null?"":applyToCarType(list.get(i).getCarType()),wcf2));
    				wsheet.addCell(new Label( 32, conut ,list.get(i).getOrderDriverCarNumber(),wcf2));*/
    				
    				conut++;
    			}
    			
    		}
			wwb.write();
			os.flush();
		} catch (Exception e) {
			ises =false;
			e.printStackTrace();
		}finally{
			try {
				if(wwb != null){
					wwb.close();
				}
				if(os != null){
					os.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		result.put("url", fileName);
		if(ises==true){
			result.put("success", true);
		}else{
			result.put("success", false);
		}
		return writeAjaxResponse(reponse,JSONUtil.getJson(result));
	}
	
	public String typeName(String value){
		 if(value.equals("1")){
			return "整车订单";
		}else if(value.equals("2")){
			return "零担订单";
		} else{
			return "包天订单";
		}

	}
	
	public String typeStatus(Integer value){
		if(value==1){
			return "待付款";
		}else if(value==2){
			return "派车中";
		}else if(value==3){
			return "运输中";
		}else if(value==4){
			return "待评价";
		}else if(value==5){
			return "已完成";
		}else if(value==6){
			return "已取消";
		}else if(value==7){
			return "异常关闭";
		}else {
			return "";
		}
	}
	
	public String typeAudit(Integer value){
		if(value == null){
			return "";
		} else if(value == 1){
			return "已审批";
		} else if(value == 0){
			return "未通过";
		} else {
			return "";
		}
	}
	
	public String applyToCarType(Integer value){
		if(value==1){
			return "小面7座";
		}
		if(value==2){
			return "中面9座";
		}
		if(value==3||value==7||value==11||value==15||value==19){
			return "任意车型";
		}
		if(value==4||value==8||value==12||value==16||value==20){
			return "厢式货车";
		}
		if(value==5||value==9||value==13||value==17||value==21){
			return "高栏货车";
		}
		if(value==6||value==10||value==14||value==18||value==22){
			return "低栏货车";
		}
		return null;
	}
	
	public String typeCarSpec(Integer value){
		if(value == null){
			return "";
		} else if(value == 1){
			return "面包车";
		} else if (value == 2) {
			return "2.5";
		} else if (value == 3) {
			return "4.2";
		} else if (value == 4) {
			return "5.2";
		} else if (value == 5) {
			return "6.8";
		} else if (value == 6){
			return "9.6";
		} else {
			return "规格有误";
		}
	}
	
	//协议用车类型（0 否1是）
	 public String typeProtocol(String value){
		 if(value.equals("0")){
			 return"否";
		 }else {
			 return"是";
		}
	 }
	 
	 public String typeReturn(Integer value){
		 if(value == null){
			 return "";
		 } else if (value == 1) {
			return "是";
		} else if(value == 2){
			return "否";
		} else {
			return "";
		}
	 }
	 
	public String paymethodName(String value){
		 if(value.equals("0")){
			return "余额";
		}else if(value.equals("1")){
			return "支付宝";
		}else if(value.equals("2")){
			return "微信";
		}else if(value.equals("3")){
			return "银联";
		}else if(value.equals("4")){
			return "运费到付";
		}else if(value.equals("5")){
			return "月结";
		}else
		{
			return "";
		}
	}
	
	//零担单位id 1:件;2:托;3:面积;4:体积;5:重量
	public String funitToName(String value){
		 if(value.equals("0")){
			return "无";
		}else if(value.equals("1")){
			return "件";
		}else if(value.equals("2")){
			return "托";
		}else if(value.equals("3")){
			return "平方米";
		}else if(value.equals("4")){
			return "立方米";
		}else if(value.equals("5")){
			return "吨";
		}else
		{
			return "";
		}
	}
	
	
	public String statusName(String value){
		if(value.equals("1")){
			return "待付款";
		}else if(value.equals("2")){
			return "派车中";
		}else if(value.equals("3")){
			return "运输中";
		}else if(value.equals("4")){
			return "待评价";
		}else if(value.equals("5")){
			return "已完成";
		}else if(value.equals("6")){
			return "已取消";
		}else{
			return "已关闭";
		}
	}
	
    private HashMap<String,String> cancelParams(CL_Order order,String AllNumber,BigDecimal addAllCost){
    	JSONObject jo ;
		JSONObject jo1;
		try{
			jo=new JSONObject();
			jo1=new JSONObject();
			jo1.put("OrderNumber",RSA.encrypt(order.getNumber(),PUBLICKEY));
        	jo1.put("TextCode", RSA.encrypt("utf-8", PUBLICKEY));
			jo1.put("OrderNumbers",AllNumber);
			jo1.put("OrderType",RSA.encrypt("退款", PUBLICKEY));
	        Calendar date=Calendar.getInstance();
	        SimpleDateFormat format= new SimpleDateFormat("yyyyMMddHHmm");
	        jo1.put("OrderTime", RSA.encrypt(format.format(date.getTime()), PUBLICKEY));
		    jo1.put("Developer", RSA.encrypt("CS", PUBLICKEY));
		    jo1.put("Drawee", RSA.encrypt("DJ", PERSONALPUBLICKEY));
		    //order.getCreatorPhone()
		    jo1.put("Payee", RSA.encrypt(order.getCreatorPhone(), PERSONALPUBLICKEY));
		    jo1.put("PayType", RSA.encrypt("系统", PUBLICKEY));
		    jo1.put("BankName", RSA.encrypt("DJ银行退款", PUBLICKEY));
		    jo1.put("BankAccountNo", RSA.encrypt("0000", PUBLICKEY));
		    jo1.put("Money", RSA.encrypt(addAllCost+"", PERSONALPUBLICKEY));
		    jo1.put("PayPassword", RSA.encrypt("123456", PERSONALPUBLICKEY));
		    jo.put("data", jo1);
		    HashMap<String, String> params=new HashMap<String,String>();
		    params.put("PayInfo", Base64.encodeBase64String(jo.toString().getBytes()));
		    params.put("url",OrderCreate);
		    return params;
		} catch(Exception e){
			e.printStackTrace();
		}
		return null;
  }
    
    @RequestMapping("/order/uploadlist")
	public String uploadlist(HttpServletRequest request,HttpServletResponse reponse,@ModelAttribute OrderQuery orderQuery) throws Exception{
    	return UPLOAD_JSP;
    }
    
    @RequestMapping("/order/loadupload")
	public String loadupload(HttpServletRequest request,HttpServletResponse reponse,@ModelAttribute OrderQuery orderQuery) throws Exception{
    	String pageNum = request.getParameter("page");
		String pageSize = request.getParameter("rows");
//		String fusername=request.getParameter("fusername");
		if (orderQuery == null) {
			orderQuery = newQuery(OrderQuery.class, null);
		}
		if (pageNum != null) {
			orderQuery.setPageNumber(Integer.parseInt(pageNum));
		}
		if (pageSize != null) {
			orderQuery.setPageSize(Integer.parseInt(pageSize));
		}
//		if(orderQuery.getCreator()!=null)
//		{
//			if(orderQuery.getCreator()== -1){
//				orderQuery.setCreator(null);
//			}
//		}
		orderQuery.setSortColumns("o.number desc");
		Page<CL_Order> page = orderDao.findUploadPage(orderQuery);
		HashMap<String, Object> m = new HashMap<String, Object>();
		m.put("total", page.getTotalCount());
		m.put("rows", page.getResult());
		return writeAjaxResponse(reponse, JSONUtil.getJson(m));
    }
    
	/*** 加载到付订单信息*/
	@RequestMapping("/order/arriveload")
	public String arriveload(HttpServletRequest request,HttpServletResponse reponse ,@ModelAttribute OrderQuery orderQuery) throws Exception{
		String pageNum = request.getParameter("page");
		String pageSize = request.getParameter("rows");
		if (orderQuery == null) {
			orderQuery = newQuery(OrderQuery.class, null);
		}
		if (pageNum != null) {
			orderQuery.setPageNumber(Integer.parseInt(pageNum));
		}
		if (pageSize != null) {
			orderQuery.setPageSize(Integer.parseInt(pageSize));
		}
		if(orderQuery.getCreator()!=null)
		{
			if(orderQuery.getCreator()== -1){
				orderQuery.setCreator(null);
			}
		}
		//只显示运费到付且是线下支付的订单
		orderQuery.setFonlinePay(2);
		//修改成只要是线下支付就要通过上缴来打运费给司机；
//		orderQuery.setFpayMethod(4);
		
		Page<CL_Order> page = orderDao.findPage(orderQuery);
		HashMap<String, Object> m = new HashMap<String, Object>();
		m.put("total", page.getTotalCount());
		for(CL_Order coinfo : page.getResult()){
//			BigDecimal faddNumber = BigDecimal.ZERO;
//			List<CL_Addto> addto=iaddtoDao.getByOrderId(coinfo.getId());
//			BigDecimal AllCost=coinfo.getFreight();
//			if(addto.size()>0){
//				for(CL_Addto ad:addto){
//					faddNumber=faddNumber.add(ad.getFcost());
//		    	}
//			}
//			coinfo.setFaddNumber(faddNumber);
//			AllCost=AllCost.add(faddNumber);
			coinfo.setFdriverAllin(coinfo.getFdriverfee());
		}
		m.put("rows", page.getResult());
		return writeAjaxResponse(reponse, JSONUtil.getJson(m));
	}
	
	//上缴运费校验（待改）
	@RequestMapping("/order/freightVerify")
	public String verify(HttpServletRequest request,HttpServletResponse response,Integer id){
		BigDecimal collectMoney = BigDecimal.ZERO;
		CL_Order order = orderDao.getById(id);
		BigDecimal allCost = BigDecimal.ZERO;//总额
		BigDecimal freight = BigDecimal.ZERO;//运费
		/**
		 * 运费调整问题：
		 * 	1、当司机未完成订单时，运费被调整，则实收的货款应该是最终调整后的金额，即order.getFreight()
		 * 	2、当司机在已完成订单后，运费被调整，司机实收的货款任然是还未调整的金额，即order.getForiginFreight()
		 * 解决思路：通过订单完成时间与运费调整的审核时间的先后做判断。(额，同时不存在的)
		 */
		if(order.getFpayMethod().equals(4)){//只有运费到付需要上缴订单运费，其余如余额，第三方，月结，只需要上缴代收货款
			if (order.getFispass_audit() != null
					&& order.getFispass_audit() == 1) {
				if (order.getFcopTime().before(order.getFaudit_time())) {
					freight = order.getForiginfreight().add(order.getServe_1() == null?BigDecimal.ZERO:order.getServe_1()).add(order.getServe_2() == null?BigDecimal.ZERO:order.getServe_2());
				} else {
					freight = order.getFreight().add(order.getServe_1() == null?BigDecimal.ZERO:order.getServe_1()).add(order.getServe_2() == null?BigDecimal.ZERO:order.getServe_2());
				}
			} else {
				freight = order.getForiginfreight().add(order.getServe_1() == null?BigDecimal.ZERO:order.getServe_1()).add(order.getServe_2() == null?BigDecimal.ZERO:order.getServe_2());
			}
		
		}
		if(order.getCollection() != null && order.getCollection() != 0){
			collectMoney = order.getPurposeAmount();//代收货款
		}
		allCost = freight.add(collectMoney);
		request.setAttribute("allCost", allCost);
		request.setAttribute("id", id);
		return FREIGHT_VERIFY_JSP;
	}
	
	//2016-6-14  运费上缴
	// type-> 0:取消上缴    1：上缴（待该）
	@RequestMapping("/order/offlinepay")
	@Transactional(propagation = Propagation.REQUIRED)
	public String offlinepay(HttpServletRequest request, HttpServletResponse reponse,Integer id,Integer type) throws DJException {
		HashMap<String, Object> map = new HashMap<String, Object>();
		HashMap<String, Object> param = new HashMap<String, Object>();
		BigDecimal collectMoney = BigDecimal.ZERO;
		CL_Order coinfo = orderDao.getById(id);
		if(coinfo.getFofflinePay()==type){
			return writeAjaxResponse(reponse,"{\"flag\":\"false\",\"msg\":\"不能多次相同操作！\"}");
		}
		CL_UserRole sjinfo = userRoleDao.getById(coinfo.getUserRoleId());	
		BigDecimal allCost = BigDecimal.ZERO;//总额
		BigDecimal freight = BigDecimal.ZERO;//运费
		/**
		 * 运费调整问题：
		 * 	1、当司机未完成订单时，运费被调整，则实收的货款应该是最终调整后的金额，即order.getFreight()
		 * 	2、当司机在已完成订单后，运费被调整，司机实收的货款任然是还未调整的金额，即order.getForiginFreight()
		 * 解决思路：通过订单完成时间与运费调整的审核时间的先后做判断。(额，同时怎么判断？)
		 */
		if (coinfo.getFispass_audit() != null
				&& coinfo.getFispass_audit() == 1) {
			if (coinfo.getFcopTime().before(coinfo.getFaudit_time())) {
				freight = coinfo.getForiginfreight().add(coinfo.getServe_1() == null?BigDecimal.ZERO:coinfo.getServe_1()).add(coinfo.getServe_2() == null?BigDecimal.ZERO:coinfo.getServe_2());
			} else {
				freight = coinfo.getFreight().add(coinfo.getServe_1() == null?BigDecimal.ZERO:coinfo.getServe_1()).add(coinfo.getServe_2() == null?BigDecimal.ZERO:coinfo.getServe_2());
			}
		} else {
			freight = coinfo.getForiginfreight().add(coinfo.getServe_1() == null?BigDecimal.ZERO:coinfo.getServe_1()).add(coinfo.getServe_2() == null?BigDecimal.ZERO:coinfo.getServe_2());
		}
		
		if(coinfo.getCollection() != null && coinfo.getCollection() != 0){
			collectMoney = coinfo.getPurposeAmount();//代收货款
		}
		allCost = freight.add(collectMoney);
	
		/**
		 * 运费上缴的订单，要司机把客户线下给的钱上缴之后，司机才会增加余额
		 */
		CL_FinanceStatement statement0 = this.sta(coinfo, sjinfo, coinfo.getFdriverfee(), type);//司机收支明细
		CL_FinanceStatement statement = this.sta(coinfo, sjinfo, allCost, 1);//运费上缴明细
		statement.setFbusinessType(11);//运费上缴
		statement0.setFbusinessType(4);//司机收入
		statement.setFremark(coinfo.getNumber()+"运费上缴");
		statement0.setFremark(coinfo.getNumber()+"司机收入");
		financeStatementDao.save(statement);
		financeStatementDao.save(statement0);
		OrderMsg orderMsg = new OrderMsg();
		OrderMsg orderMsg0 = new OrderMsg();
		orderMsg.setOrder(statement.getNumber());
		orderMsg0.setOrder(statement0.getNumber());
		orderMsg.setPayState("1");
		orderMsg0.setPayState("1");
		orderMsg.setServiceProviderType("0");
		orderMsg0.setServiceProviderType("0");
		orderMsg.setServiceProvider("0");
		orderMsg0.setServiceProvider("0");
		orderMsg.setUserId(Integer.parseInt(request.getSession().getAttribute("userRoleId").toString()));
		param.put("id", id);
		param.put("type", type);
		int dri = financeStatementDao.updateBusinessType(orderMsg, param);
		if (coinfo.getFpayMethod() != 5) {
			int dri0 = financeStatementDao.updateBusinessType(orderMsg0, param);
			if (dri == 1 && dri0 == 1) {
				map.put("flag", "success");
				map.put("msg", "上缴成功");
			} else {
				map.put("flag", "false");
				map.put("msg", "上缴失败");
			}
		} else {
			if (dri == 1) {
				map.put("flag", "success");
				map.put("msg", "上缴成功");
			} else {
				map.put("flag", "false");
				map.put("msg", "上缴失败");
			}
		}
		return writeAjaxResponse(reponse,JSONUtil.getJson(map));

	}
	
	/*     
	 *直接操作用户余额方法  
	 * fid : 相关id
	 * fuserid : 相关用户id
	 * phone： 用户手机
	 * type： 0、扣款  1、返利 
	 * orderNumber：相关订单号
	 * famount:操作金额
	 * */
	/*public boolean doMakeUserBalance(int fid,int fuserid,String phone,int type,String orderNumber,BigDecimal famount) throws DJException {
		if(type==0){
			String Developer = RSA.encrypt("CS",PUBLICKEY);
			String Userphone = RSA.encrypt(phone,PERSONALPUBLICKEY);
			String requestDataString = "{\"data\": {\"Developer\": \""+Developer+"\",\"Usename\": \""+Userphone+"\"}}";
			requestDataString = Base64.encode(requestDataString.getBytes());
			String resString = ServerContext.UsePayBalance(requestDataString);
			JSONObject jo = JSONObject.fromObject(resString);
			BigDecimal fbalance =new BigDecimal(jo.get("data").toString());
			if(famount.compareTo(fbalance)>0){
				throw new DJException("余额不足无法扣款！");
			}
		}		
		CL_UserRole urinfo = userRoleDao.getById(fuserid);
		//默认theType为收款
		int theType = 1;
		//2016-5-20   不校验余额了
		JSONObject joe=new JSONObject();
		JSONObject jo1=new JSONObject();
		jo1.put("Fid",fid);
		jo1.put("Phone",phone);
		jo1.put("Amount",famount);
		jo1.put("Type","Add");
		if(type==0){
			theType = -1;
			jo1.put("Type","Minus");
		}
		joe.put("data", jo1);
		 if(ServerContext.checkWithdrawStatus(Base64.encode(joe.toString().getBytes()))){							 
			CL_FinanceStatement statement=new CL_FinanceStatement();
			statement.setFcreateTime(new Date());
			statement.setNumber(CacheUtilByCC.getOrderNumber("cl_finance_statement", "L", 8));
			statement.setFrelatedId(String.valueOf(fid));
			statement.setForderId(orderNumber);
			statement.setFbusinessType(3);
			statement.setFamount(famount);
			statement.setFtype(theType);
			statement.setFuserroleId(fuserid);
			statement.setFuserid(urinfo.getVmiUserFid());
			statement.setFpayType(0);
			statement.setFremark("到付运费上缴");
			if(type==0){		
				statement.setFremark("取消上缴运费");
			}
			financeStatementDao.save(statement);
			
		 }else{
			 throw new DJException(phone+":处理余额报错！");
		 }
		return true;
	}
	*/
	private static String encryptBASE64(byte[] data) throws Exception {
		return Base64.encodeBase64String(data).replace("\r\n", "");
	}
	@SuppressWarnings("unused")
	private static String encryptBASE64(String data) throws Exception {
		if (data == null) {
			return null;
		}
		return encryptBASE64(data.getBytes("utf-8"));
	}

	/***调整运费**/
	@RequestMapping("/order/updateFreight")
	public String updateFreight(HttpServletRequest request,HttpServletResponse reponse,Integer id) throws Exception{
//		request.getSession().setAttribute("orderid",id);//把需要指派的订单id保存在request中
		HashMap<String, Object> map = new HashMap<String, Object>();
		CL_Order order = orderDao.getById(id);
		//订单取消状态不能调整;
		if(order.getStatus() >= 6 ){
			map.put("success", "false");
			map.put("msg", "订单取消或待确认状态不能调整运费！");
			return writeAjaxResponse(reponse, JSONUtil.getJson(map));
		}
		//客户余额不足不得调整
		CL_UserRole user = userRoleDao.getById(order.getCreator());
		BigDecimal zero = new BigDecimal(0);
		
		if(zero.compareTo(user.getFbalance())>0){
			map.put("success", "false");
			map.put("msg", "余额不足无法操作");
			return writeAjaxResponse(reponse, JSONUtil.getJson(map));
		}
		//订单管理新需求----BY LANCHER
		if(order.getFispass_audit() == null || order.getFispass_audit() == 0){//判断该订单是否已经审批
			request.getSession().setAttribute("order",order);
//			return UPDATE_FREIGHT_JSP;
			map.put("success", "true");
			map.put("data", UPDATE_FREIGHT_JSP);
			return writeAjaxResponse(reponse, JSONUtil.getJson(map));
		}else {
			map.put("success", "false");
			map.put("msg", "订单已审批，不能再次调整运费！");
//			return writeAjaxResponse(reponse, "false");
			return writeAjaxResponse(reponse, JSONUtil.getJson(map));
		}
	}
	
	/***调整运费**/
	@RequestMapping("/order/editfreight")
	public String editfreight(HttpServletRequest request,HttpServletResponse response,Integer id) throws Exception{
		HashMap<String,Object> map =new HashMap<String,Object>();
		if(request.getSession().getId()==null || request.getSession().getId().equals("")){
			map.put("success", "false");
			map.put("data", "未登录！");
			return writeAjaxResponse(response, JSONUtil.getJson(map));
		}
		String vmi_user_fid = ServerContext.getUseronline().get(request.getSession().getId()).getFuserid();
		CL_UserRole user = userRoleDao.getByVmiUserFidAndRoleId(vmi_user_fid, 3);
		CL_Order order = orderDao.getById(id);
		order.setFreight(new BigDecimal(request.getParameter("freight")));
		if(order.getStatus() == 4 || order.getStatus() == 5 ){
			CL_UserRole driver = userRoleDao.getById(order.getUserRoleId());
			BigDecimal zero = new BigDecimal(0);
			if(zero.compareTo(driver.getFbalance())>0){//如果司机账户的余额不足，则只能增，不能减
				BigDecimal fdriverfee = new BigDecimal(request.getParameter("fdriverfee"));
				BigDecimal balance = fdriverfee.subtract(order.getForigin_driverfee());
				if(balance.compareTo(zero)<0){
					map.put("success", "false");
					map.put("data", "司机已欠费，无法操作");
					return writeAjaxResponse(response, JSONUtil.getJson(map));
				}
			}
			order.setFdriverfee(new BigDecimal(request.getParameter("fdriverfee")));
		}
		if(order.getStatus() == 3){
			order.setFdriverfee(new BigDecimal(request.getParameter("fdriverfee")));
		}
		order.setFregulator(user.getId());
		order.setFregulate_time(new Date());
		order.setFispass_audit(0);
		orderDao.update(order);
		map.put("success", "success");
		map.put("data", "修改成功！");
		return writeAjaxResponse(response, JSONUtil.getJson(map));
	}
	
	/**
	 * 审核运费
	 * @param request
	 * @param response
	 * @param ids
	 * @return
	 */
	@RequestMapping("/order/auditfreight")
	public String audit(HttpServletRequest request,
			HttpServletResponse response, Integer[] ids) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		HashMap<String, Object> param = new HashMap<String, Object>();//用于传参
		if (request.getSession().getId() == null
				|| request.getSession().getId().equals("")) {
			map.put("success", "false");
			map.put("data", "未登录！");
			return writeAjaxResponse(response, JSONUtil.getJson(map));
		}
		String vmi_user_fid = ServerContext.getUseronline().get(request.getSession().getId()).getFuserid();
		int auditorId = userRoleDao.getByVmiUserFidAndRoleId(vmi_user_fid, 3).getId();
		for (Integer id : ids) {
			CL_Order order = orderDao.getById(id);
			if (order.getFispass_audit() == null || order.getFispass_audit() == 1) {
				map.put("success", "false");
				map.put("data", "请正确选择要审批的订单");
				return writeAjaxResponse(response, JSONUtil.getJson(map));
			}
			if (order.getFregulator() == auditorId) {
				map.put("success", "false1");
				map.put("data", "运费调整与审核不可为同一人！");
				return writeAjaxResponse(response, JSONUtil.getJson(map));
			}
			// 调整用户余额
			BigDecimal freight = order.getFreight().subtract(order.getForiginfreight());// 调整的货主运费额度
			CL_UserRole customer = userRoleDao.getById(order.getCreator());
			BigDecimal zero = new BigDecimal(0);
			//新逻辑 如果原运费+调整的运费 < 好运券的金额，则不扣钱
			Integer coupons = order.getCouponsId();//订单优惠券
			if(coupons != null){
				BigDecimal dollars = BigDecimal.ZERO;
				dollars = order.getDiscount();//订单优惠券减少的钱
				//只有直减的会出现金额大于运费
				if(order.getFreight().compareTo(order.getDiscount()) < 0 && order.getFreight().compareTo(order.getForiginfreight()) != 0){
					map.put("success", "false");
					map.put("data", "运费不得调整后依旧比优惠券还小！没意义啊~");
					return writeAjaxResponse(response, JSONUtil.getJson(map));
				}
				
				if(dollars.compareTo(order.getFreight()) >= 0  && freight.compareTo(BigDecimal.ZERO) < 0){//原运费+调整的运费 < 好运券的金额，则不扣钱
					if (order.getStatus()==4 || order.getStatus() == 5 ){
						
						/*BigDecimal driverfee = order.getFdriverfee().subtract(order.getForigin_driverfee());// 调整的司机运价额度
						CL_UserRole driver = userRoleDao.getById(order.getUserRoleId());
						if (driverfee.compareTo(zero) > 0) {//现司机运价大于原司机运价，则表示需要多给司机钱，即增加余额
							CL_FinanceStatement statement_1 = this.sta(order, driver, driverfee, 1);
							financeStatementDao.save(statement_1);
							OrderMsg orderMsg_1 = new OrderMsg();
							orderMsg_1.setOrder(statement_1.getNumber());
							orderMsg_1.setPayState("1");
							orderMsg_1.setServiceProviderType("0");
							orderMsg_1.setServiceProvider("0");
							orderMsg_1.setUserId(auditorId);
							param.put("is_update", 1);
							param.put("order", order);
							int dri = financeStatementDao.updateBusinessType(orderMsg_1,param);
							
							if(dri != 1){
								map.put("success", "false");
								map.put("data", "支付失败！");
								return writeAjaxResponse(response, JSONUtil.getJson(map));
							}
						} else if (driverfee.compareTo(zero) < 0) {//现司机运价大于原司机运价，则表示需要多给司机钱，即增加余额
							CL_FinanceStatement statement_2 = this.sta(order, driver, driverfee, -1);
							financeStatementDao.save(statement_2);
							OrderMsg orderMsg_2 = new OrderMsg();
							orderMsg_2.setOrder(statement_2.getNumber());
							orderMsg_2.setPayState("1");
							orderMsg_2.setServiceProviderType("0");
							orderMsg_2.setServiceProvider("0");
							orderMsg_2.setUserId(auditorId);
							param.put("is_update", 1);
							param.put("order", order);
							int dri = financeStatementDao.updateBusinessType(orderMsg_2,param);
							
							if(dri != 1){
								map.put("success", "false");
								map.put("data", "支付失败！");
								return writeAjaxResponse(response, JSONUtil.getJson(map));
							}
						}*/
				
				if(order.getForigin_driverfee().compareTo(order.getFdriverfee()) != 0){
					map.put("success", "false");
					map.put("data", "特殊订单，不得操作司机运费！");
					return writeAjaxResponse(response, JSONUtil.getJson(map));
				}
			}
			order.setFauditor(auditorId);
			order.setFaudit_time(new Date());
			order.setFispass_audit(1);
			orderDao.update(order);
			map.put("success", "success");
			map.put("data", "完成审批！");
			return writeAjaxResponse(response, JSONUtil.getJson(map));
		} else {
			if(dollars.compareTo(order.getForiginfreight()) >= 0){//原运费+调整的运费 > 好运券的金额，则扣超出好运券的钱(必须是好运券金额>原运费)
				if (order.getStatus()==4 || order.getStatus() == 5 ){
					
					/*BigDecimal driverfee = order.getFdriverfee().subtract(order.getForigin_driverfee());// 调整的司机运价额度
								CL_UserRole driver = userRoleDao.getById(order.getUserRoleId());
								if (driverfee.compareTo(zero) > 0) {//现司机运价大于原司机运价，则表示需要多给司机钱，即增加余额
									CL_FinanceStatement statement_1 = this.sta(order, driver, driverfee, 1);
									financeStatementDao.save(statement_1);
									OrderMsg orderMsg_1 = new OrderMsg();
									orderMsg_1.setOrder(statement_1.getNumber());
									orderMsg_1.setPayState("1");
									orderMsg_1.setServiceProviderType("0");
									orderMsg_1.setServiceProvider("0");
									orderMsg_1.setUserId(auditorId);
									param.put("is_update", 1);
									param.put("order", order);
									int dri = financeStatementDao.updateBusinessType(orderMsg_1,param);
									
									if(dri != 1){
										map.put("success", "false");
										map.put("data", "支付失败！");
										return writeAjaxResponse(response, JSONUtil.getJson(map));
									}
								} else if (driverfee.compareTo(zero) < 0) {//现司机运价大于原司机运价，则表示需要多给司机钱，即增加余额
									CL_FinanceStatement statement_2 = this.sta(order, driver, driverfee, -1);
									financeStatementDao.save(statement_2);
									OrderMsg orderMsg_2 = new OrderMsg();
									orderMsg_2.setOrder(statement_2.getNumber());
									orderMsg_2.setPayState("1");
									orderMsg_2.setServiceProviderType("0");
									orderMsg_2.setServiceProvider("0");
									orderMsg_2.setUserId(auditorId);
									param.put("is_update", 1);
									param.put("order", order);
									int dri = financeStatementDao.updateBusinessType(orderMsg_2,param);
									
									if(dri != 1){
										map.put("success", "false");
										map.put("data", "支付失败！");
										return writeAjaxResponse(response, JSONUtil.getJson(map));
									}
								}*/
					
					if(order.getForigin_driverfee().compareTo(order.getFdriverfee()) != 0){
						map.put("success", "false");
						map.put("data", "特殊订单，不得操作司机运费！");
						return writeAjaxResponse(response, JSONUtil.getJson(map));
					}
				}
				if(freight.compareTo(zero) > 0){//现运费比原运费多，要多收钱，扣钱走支付
					if(order.getStatus() != 1){
						BigDecimal auditfreight = order.getFreight().subtract(dollars);
						CL_FinanceStatement statement = this.sta(order, customer, auditfreight, -1);
						financeStatementDao.save(statement);
						OrderMsg orderMsg = new OrderMsg();
						orderMsg.setOrder(statement.getNumber());
						orderMsg.setPayState("1");
						orderMsg.setServiceProviderType("0");
						orderMsg.setServiceProvider("0");
						orderMsg.setUserId(auditorId);
						param.put("is_update", 1);//调整业务
						param.put("order", order);
						int cus = financeStatementDao.updateBusinessType(orderMsg,param);
						if(cus != 1){
							map.put("success", "false");
							map.put("data", "支付失败！");
							return writeAjaxResponse(response, JSONUtil.getJson(map));
						} else {
							map.put("success", "success");
							map.put("data", "完成审批！");
							return writeAjaxResponse(response, JSONUtil.getJson(map));
						}
						
					} else {
						order.setFauditor(auditorId);
						order.setFaudit_time(new Date());
						order.setFispass_audit(1);
						order.setFtotalFreight(order.getFtotalFreight().add(order.getFreight().subtract(dollars)));
						orderDao.update(order);
						map.put("success", "success");
						map.put("data", "完成审批！");
						return writeAjaxResponse(response, JSONUtil.getJson(map));
					}
				} else {//现运费比原运费少，要少收钱，实际客户是没有付过钱的，即不扣钱不走支付
					order.setFauditor(auditorId);
					order.setFaudit_time(new Date());
					order.setFispass_audit(1);
					orderDao.update(order);
					map.put("success", "success");
					map.put("data", "完成审批！");
					return writeAjaxResponse(response, JSONUtil.getJson(map));
				}
			}
		}
					
				
			}
			if(order.getFpayMethod() == null ||order.getFpayMethod() != 5){//不考虑月结
				
				if(order.getStatus()!=1 && order.getStatus()!=2){//未付款跟派车中没有司机
					BigDecimal driverfee = order.getFdriverfee().subtract(order.getForigin_driverfee());// 调整的司机运价额度
					if(freight.compareTo(zero) == 0 && driverfee.compareTo(zero) == 0){//运价与运费调整额度都为0者没意义，不予以审批
						map.put("success", "false2");
						map.put("data", "调整不合理！");
						return writeAjaxResponse(response,JSONUtil.getJson(map));
					}
					//只有订单状态是4待评价，5已完成时调整运费需要改司机余额
					if (order.getStatus()==4 || order.getStatus() == 5 ) {
						
						CL_UserRole driver = userRoleDao.getById(order.getUserRoleId());
						if(order.getFonlinePay() == 2){//已完成的运费到付，如果不是走线上，跟系统不搭嘎，直接调订单上的费用就好
//							if(order.getFofflinePay() == 0){
								// 货主司机的余额调整必定一增一减
								if (freight.compareTo(zero) > 0) {//现运费比原运费多，则表示需要多扣客户的钱，即减少余额
									
									CL_FinanceStatement statement = this.sta(order, customer, freight, -1);
									financeStatementDao.save(statement);
									OrderMsg orderMsg = new OrderMsg();
									orderMsg.setOrder(statement.getNumber());
									orderMsg.setPayState("1");
									orderMsg.setServiceProviderType("0");
									orderMsg.setServiceProvider("0");
									orderMsg.setUserId(auditorId);
									param.put("is_update", 1);//调整业务
									param.put("order", order);
									
									if(driverfee.compareTo(zero) < 0){
										map.put("success", "false2");
										map.put("data", "调整不合理！");
										return writeAjaxResponse(response,
												JSONUtil.getJson(map));
									}
									
									int cus = financeStatementDao.updateBusinessType(orderMsg,param);
									if(cus != 1){
										map.put("success", "false");
										map.put("data", "支付失败！");
										return writeAjaxResponse(response, JSONUtil.getJson(map));
									}
									
									if (driverfee.compareTo(zero) > 0) {//现司机运价大于原司机运价，则表示需要多给司机钱，即增加余额
										
										CL_FinanceStatement statement1 = this.sta(order, driver, driverfee, 1);
										financeStatementDao.save(statement1);
										OrderMsg orderMsg1 = new OrderMsg();
										orderMsg1.setOrder(statement1.getNumber());
										orderMsg1.setPayState("1");
										orderMsg1.setServiceProviderType("0");
										orderMsg1.setServiceProvider("0");
										orderMsg1.setUserId(auditorId);
										param.put("is_update", 1);
										param.put("order", order);
										int dri = financeStatementDao.updateBusinessType(orderMsg1,param);
										
										if(dri != 1){
											map.put("success", "false");
											map.put("data", "支付失败！");
											return writeAjaxResponse(response, JSONUtil.getJson(map));
										}
									} else if(driverfee.compareTo(zero) < 0){
										map.put("success", "false2");
										map.put("data", "调整不合理！");
										return writeAjaxResponse(response,
												JSONUtil.getJson(map));
									}
									
									
									
								} else {//freight.compareTo(zero)<0现运费比原运费少，则表示需要多收了客户的钱，需要退钱，即增加余额
									freight = BigDecimal.ZERO.subtract(freight);
									CL_FinanceStatement statement = this.sta(order, customer, freight, 1);
									statement.setFremark(order.getNumber()+"运费调整");
									financeStatementDao.save(statement);
									OrderMsg orderMsg = new OrderMsg();
									orderMsg.setOrder(statement.getNumber());
									orderMsg.setPayState("1");
									orderMsg.setServiceProviderType("0");
									orderMsg.setServiceProvider("0");
									orderMsg.setUserId(auditorId);
									param.put("is_update", 1);
									param.put("order", order);
									
									if(driverfee.compareTo(zero) > 0){
										map.put("success", "false2");
										map.put("data", "调整不合理！");
										return writeAjaxResponse(response,
												JSONUtil.getJson(map));
									}
									int cus = financeStatementDao.updateBusinessType(orderMsg,param);
									if(cus != 1){
										map.put("success", "false");
										map.put("data", "支付失败！");
										return writeAjaxResponse(response, JSONUtil.getJson(map));
									}
									
									//运费调整额度为0时，司机运价要么增要么减
									int dri;
									if (driverfee.compareTo(zero) > 0) {//给老司机加余额
										CL_FinanceStatement statement1 = this.sta(order, driver, driverfee, 1);
										financeStatementDao.save(statement1);
										OrderMsg orderMsg1 = new OrderMsg();
										orderMsg1.setOrder(statement1.getNumber());
										orderMsg1.setPayState("1");
										orderMsg1.setServiceProviderType("0");
										orderMsg1.setServiceProvider("0");
										orderMsg1.setUserId(auditorId);
										param.put("is_update", 1);
										param.put("order", order);
										dri = financeStatementDao.updateBusinessType(orderMsg1,param);
									}else {//给老司机减余额、
										driverfee = BigDecimal.ZERO.subtract(driverfee);
										CL_FinanceStatement statement1 = this.sta(order, driver, driverfee, -1);
										financeStatementDao.save(statement1);
										OrderMsg orderMsg1 = new OrderMsg();
										orderMsg1.setOrder(statement1.getNumber());
										orderMsg1.setPayState("1");
										orderMsg1.setServiceProviderType("0");
										orderMsg1.setServiceProvider("0");
										orderMsg1.setUserId(auditorId);
										param.put("is_update", 1);
										param.put("order", order);
										dri = financeStatementDao.updateBusinessType(orderMsg1,param);
									}
									if(dri == 1){
										map.put("success", "success");
									} else{
										map.put("success", "false");
										map.put("data", "支付失败！");
										return writeAjaxResponse(response, JSONUtil.getJson(map));
									}
									
								}
//							}
							map.put("success", "success");
							map.put("data", "完成审批！");
							return writeAjaxResponse(response, JSONUtil.getJson(map));
						}/*else if(order.getFonlinePay() == 0){
							map.put("success", "false3");
							map.put("data", "数据异常！");
							return writeAjaxResponse(response, JSONUtil.getJson(map));
						}*/
						
						// 货主司机的余额调整必定一增一减
						if (freight.compareTo(zero) > 0) {//现运费比原运费多，则表示需要多扣客户的钱，即减少余额
							
							CL_FinanceStatement statement = this.sta(order, customer, freight, -1);
							financeStatementDao.save(statement);
							OrderMsg orderMsg = new OrderMsg();
							orderMsg.setOrder(statement.getNumber());
							orderMsg.setPayState("1");
							orderMsg.setServiceProviderType("0");
							orderMsg.setServiceProvider("0");
							orderMsg.setUserId(auditorId);
							if(driverfee.compareTo(zero) == 0){//支付不允许调整额度为0，如果运费与运价其中一个为0
								param.put("is_update", 1);//调整业务
								param.put("order", order);
							}else{
								param.put("is_update", 0);//不调
							}
							
							int cus = financeStatementDao.updateBusinessType(orderMsg,param);
							if(cus != 1){
								map.put("success", "false");
								map.put("data", "支付失败！");
								return writeAjaxResponse(response, JSONUtil.getJson(map));
							}
							
							if (driverfee.compareTo(zero) > 0) {//现司机运价大于原司机运价，则表示需要多给司机钱，即增加余额
								
								CL_FinanceStatement statement1 = this.sta(order, driver, driverfee, 1);
								financeStatementDao.save(statement1);
								OrderMsg orderMsg1 = new OrderMsg();
								orderMsg1.setOrder(statement1.getNumber());
								orderMsg1.setPayState("1");
								orderMsg1.setServiceProviderType("0");
								orderMsg1.setServiceProvider("0");
								orderMsg1.setUserId(auditorId);
								param.put("is_update", 1);
								param.put("order", order);
								int dri = financeStatementDao.updateBusinessType(orderMsg1,param);
								
								if(dri != 1){
									map.put("success", "false");
									map.put("data", "支付失败！");
									return writeAjaxResponse(response, JSONUtil.getJson(map));
								}
							} else if(driverfee.compareTo(zero) < 0){
								map.put("success", "false2");
								map.put("data", "调整不合理！");
								return writeAjaxResponse(response,
										JSONUtil.getJson(map));
							}
							
						} else if (freight.compareTo(zero) == 0) {//支付不允许调整额度为0，如果运费与运价其中一个为0
							//运费调整额度为0时，司机运价要么增要么减
							int dri;
							if (driverfee.compareTo(zero) > 0) {//给老司机加余额
								CL_FinanceStatement statement = this.sta(order, driver, driverfee, 1);
								financeStatementDao.save(statement);
								OrderMsg orderMsg = new OrderMsg();
								orderMsg.setOrder(statement.getNumber());
								orderMsg.setPayState("1");
								orderMsg.setServiceProviderType("0");
								orderMsg.setServiceProvider("0");
								orderMsg.setUserId(auditorId);
								param.put("is_update", 1);
								param.put("order", order);
								dri = financeStatementDao.updateBusinessType(orderMsg,param);
							}else {//给老司机减余额、
								driverfee = BigDecimal.ZERO.subtract(driverfee);
								CL_FinanceStatement statement = this.sta(order, driver, driverfee, -1);
								financeStatementDao.save(statement);
								OrderMsg orderMsg = new OrderMsg();
								orderMsg.setOrder(statement.getNumber());
								orderMsg.setPayState("1");
								orderMsg.setServiceProviderType("0");
								orderMsg.setServiceProvider("0");
								orderMsg.setUserId(auditorId);
								param.put("is_update", 1);
								param.put("order", order);
								dri = financeStatementDao.updateBusinessType(orderMsg,param);
							}
							if(dri == 1){
								map.put("success", "success");
							} else{
								map.put("success", "false");
								map.put("data", "支付失败！");
								return writeAjaxResponse(response, JSONUtil.getJson(map));
							}
							
						}else {//freight.compareTo(zero)<0现运费比原运费少，则表示需要多收了客户的钱，需要退钱，即增加余额
							freight = BigDecimal.ZERO.subtract(freight);
							CL_FinanceStatement statement = this.sta(order, customer, freight, 1);
							statement.setFremark(order.getNumber()+"运费调整");
							financeStatementDao.save(statement);
							OrderMsg orderMsg = new OrderMsg();
							orderMsg.setOrder(statement.getNumber());
							orderMsg.setPayState("1");
							orderMsg.setServiceProviderType("0");
							orderMsg.setServiceProvider("0");
							if (driverfee.compareTo(zero) == 0){
								orderMsg.setUserId(auditorId);
								param.put("is_update", 1);
								param.put("order", order);
							}else {
								param.put("is_update", 0);
							}
							int cus = financeStatementDao.updateBusinessType(orderMsg,param);
							if(cus != 1){
								map.put("success", "false");
								map.put("data", "支付失败！");
								return writeAjaxResponse(response, JSONUtil.getJson(map));
							}
							
							if (driverfee.compareTo(zero) < 0) {//运费减少了运价跟着也减少了，老司机的收入也减少了，即减少老司机的余额
								driverfee = BigDecimal.ZERO.subtract(driverfee);//负负得正
								CL_FinanceStatement statement1 = this.sta(order, driver, driverfee, -1);
								statement.setFremark(order.getNumber()+"运费调整");
								financeStatementDao.save(statement1);
								OrderMsg orderMsg1 = new OrderMsg();
								orderMsg1.setOrder(statement1.getNumber());
								orderMsg1.setPayState("1");
								orderMsg1.setServiceProviderType("0");
								orderMsg1.setServiceProvider("0");
								orderMsg1.setUserId(auditorId);
								param.put("is_update", 1);
								param.put("order", order);
								int dri = financeStatementDao.updateBusinessType(orderMsg1,param);
								if(dri != 1){
									map.put("success", "false");
									map.put("data", "支付失败！");
									return writeAjaxResponse(response, JSONUtil.getJson(map));
								}
								
							}else if(driverfee.compareTo(zero) > 0){
								map.put("success", "false2");
								map.put("data", "调整不合理！");
								return writeAjaxResponse(response,
										JSONUtil.getJson(map));
							}
							
						}
						
					} else if (order.getStatus() == 3) {//3.运输中，有老司机，但司机还未完成，钱未到账，所以不论增减，都不需要调整老司机的余额，只需要调整客户的
						if(order.getFpayMethod()==4||order.getFpayMethod()==5){//支付方式如果是运费到付或者月结，则不需要改动客户与司机的余额
							order.setFauditor(auditorId);
							order.setFaudit_time(new Date());
							order.setFispass_audit(1);
							order.setFtotalFreight(order.getFtotalFreight().add(freight));
							orderDao.update(order);//单纯的该业务需要进入么
						}else{
							if (freight.compareTo(zero) > 0) {
								
								CL_FinanceStatement statement = this.sta(order, customer, freight, -1);
								statement.setFremark(order.getNumber()+"运费调整");
								financeStatementDao.save(statement);
								OrderMsg orderMsg = new OrderMsg();
								orderMsg.setOrder(statement.getNumber());
								orderMsg.setPayState("1");
								orderMsg.setServiceProviderType("0");
								orderMsg.setServiceProvider("0");
								orderMsg.setUserId(auditorId);
								param.put("is_update", 1);
								param.put("order", order);
								int cus = financeStatementDao.updateBusinessType(orderMsg,param);
								if(cus != 1){
									map.put("success", "false");
									map.put("data", "支付失败！");
									return writeAjaxResponse(response, JSONUtil.getJson(map));
								}
							} else if (freight.compareTo(zero) == 0 ) {
								order.setFauditor(auditorId);
								order.setFaudit_time(new Date());
								order.setFispass_audit(1);
								orderDao.update(order);//单纯的该业务需要进入么
								map.put("success", "success");
								map.put("data", "完成审批！");
								return writeAjaxResponse(response, JSONUtil.getJson(map));
							}else {
								freight = BigDecimal.ZERO.subtract(freight);
								CL_FinanceStatement statement = this.sta(order, customer, freight, 1);
								statement.setFremark(order.getNumber()+"运费调整");
								financeStatementDao.save(statement);
								OrderMsg orderMsg = new OrderMsg();
								orderMsg.setOrder(statement.getNumber());
								orderMsg.setPayState("1");
								orderMsg.setServiceProviderType("0");
								orderMsg.setServiceProvider("0");
								orderMsg.setUserId(auditorId);
								param.put("is_update", 1);
								param.put("order", order);
								int cus = financeStatementDao.updateBusinessType(orderMsg,param);
								if(cus != 1){
									map.put("success", "false");
									map.put("data", "支付失败！");
									return writeAjaxResponse(response, JSONUtil.getJson(map));
								}
							}
						
						}
					}
				} else if (order.getStatus() == 1) {
					if (freight.compareTo(zero) == 0) {
						map.put("success", "false2");
						map.put("data", "调整不合理！");
						return writeAjaxResponse(response, JSONUtil.getJson(map));
					}
					order.setFauditor(auditorId);
					order.setFaudit_time(new Date());
					order.setFispass_audit(1);
					order.setFtotalFreight(order.getFtotalFreight().add(freight));
					orderDao.update(order);
				} else if (order.getStatus() == 2) {// 2.已付款未派车,还没有老司机接单，只需要对客户余额调整即可
					if(order.getFpayMethod()==4||order.getFpayMethod()==5){
						order.setFauditor(auditorId);
						order.setFaudit_time(new Date());
						order.setFispass_audit(1);
						orderDao.update(order);
					}else {
						if (freight.compareTo(zero) > 0) {
							
							CL_FinanceStatement statement = this.sta(order, customer, freight, -1);
							statement.setFremark(order.getNumber()+"运费调整");
							financeStatementDao.save(statement);
							OrderMsg orderMsg = new OrderMsg();
							orderMsg.setOrder(statement.getNumber());
							orderMsg.setPayState("1");
							orderMsg.setServiceProviderType("0");
							orderMsg.setServiceProvider("0");
							orderMsg.setUserId(auditorId);
							param.put("is_update", 1);
							param.put("order", order);
							int cus = financeStatementDao.updateBusinessType(orderMsg,param);
							
							if (cus != 1) {
								map.put("success", "false");
								map.put("data", "支付失败！");
								return writeAjaxResponse(response,JSONUtil.getJson(map));
							}
						} else if (freight.compareTo(zero) == 0) {
							order.setFauditor(auditorId);
							order.setFaudit_time(new Date());
							order.setFispass_audit(1);
							orderDao.update(order);
							map.put("success", "success");
							map.put("data", "完成审批！");
							return writeAjaxResponse(response, JSONUtil.getJson(map));
						} else {
							freight = BigDecimal.ZERO.subtract(freight);
							CL_FinanceStatement statement = this.sta(order, customer, freight, 1);
							statement.setFremark(order.getNumber()+"运费调整");
							financeStatementDao.save(statement);
							OrderMsg orderMsg = new OrderMsg();
							orderMsg.setOrder(statement.getNumber());
							orderMsg.setPayState("1");
							orderMsg.setServiceProviderType("0");
							orderMsg.setServiceProvider("0");
							orderMsg.setUserId(auditorId);
							param.put("is_update", 1);
							param.put("order", order);
							int cus = financeStatementDao.updateBusinessType(orderMsg,param);
							
							if (cus != 1) {
								map.put("success", "false");
								map.put("data", "支付失败！");
								return writeAjaxResponse(response,JSONUtil.getJson(map));
							}
						}
					}
				}
				
			}else {
				if(order.getStatus() < 3){
					order.setFauditor(auditorId);
					order.setFaudit_time(new Date());
					order.setFispass_audit(1);
					orderDao.update(order);
					order.setFtotalFreight(order.getFtotalFreight().add(freight));
					map.put("success", "success");
					map.put("data", "完成审批！");
					return writeAjaxResponse(response, JSONUtil.getJson(map));
				}
				BigDecimal driverfee = order.getFdriverfee().subtract(order.getForigin_driverfee());// 调整的司机运价额度
				if(freight.compareTo(zero) == 0 && driverfee.compareTo(zero) == 0){//运价与运费调整额度都为0者没意义，不予以审批
					map.put("success", "false2");
					map.put("data", "调整不合理！");
					return writeAjaxResponse(response,JSONUtil.getJson(map));
				}
				if(order.getStatus() == 4 || order.getStatus() == 5){//月结的客户运费调动随意，司机的需要判断
					CL_UserRole driver = userRoleDao.getById(order.getUserRoleId());
					Integer dri;
					if (driverfee.compareTo(zero) > 0) {//给老司机加余额
						CL_FinanceStatement statement = this.sta(order, driver, driverfee, 1);
						statement.setFremark(order.getNumber()+"运费调整");
						financeStatementDao.save(statement);
						OrderMsg orderMsg = new OrderMsg();
						orderMsg.setOrder(statement.getNumber());
						orderMsg.setPayState("1");
						orderMsg.setServiceProviderType("0");
						orderMsg.setServiceProvider("0");
						orderMsg.setUserId(auditorId);
						param.put("is_update", 1);
						param.put("order", order);
						dri = financeStatementDao.updateBusinessType(orderMsg,param);
					}else {//给老司机减余额、
						driverfee = BigDecimal.ZERO.subtract(driverfee);
						CL_FinanceStatement statement = this.sta(order, driver, driverfee, -1);
						statement.setFremark(order.getNumber()+"运费调整");
						financeStatementDao.save(statement);
						OrderMsg orderMsg = new OrderMsg();
						orderMsg.setOrder(statement.getNumber());
						orderMsg.setPayState("1");
						orderMsg.setServiceProviderType("0");
						orderMsg.setServiceProvider("0");
						orderMsg.setUserId(auditorId);
						param.put("is_update", 1);
						param.put("order", order);
						dri = financeStatementDao.updateBusinessType(orderMsg,param);
					}
					if(dri == 1){
						map.put("success", "success");
					} else{
						map.put("success", "false");
						map.put("data", "支付失败！");
						return writeAjaxResponse(response, JSONUtil.getJson(map));
					}
				}
				order.setFauditor(auditorId);
				order.setFaudit_time(new Date());
				order.setFispass_audit(1);
				order.setFtotalFreight(order.getFtotalFreight().add(freight));
				orderDao.update(order);
			}
		}
		map.put("success", "success");
		map.put("data", "完成审批！");
		return writeAjaxResponse(response, JSONUtil.getJson(map));
	}
	
	/**
	 * 生成明细公共方法
	 * @param order调整运费的订单
	 * @param user被调整的用户
	 * @param fee调整的费用
	 * @param ftype收支情况
	 * @return
	 */
	public CL_FinanceStatement sta(CL_Order order,CL_UserRole user,BigDecimal fee,int ftype){
		CL_FinanceStatement statement = new CL_FinanceStatement();
		String number = CacheUtilByCC.getOrderNumber("cl_finance_statement", "L", 8);
		statement.setNumber(number);
		statement.setFrelatedId(order.getId().toString());
		statement.setForderId(order.getNumber());
		statement.setFbusinessType(10);
		statement.setFamount(fee);
		if(ftype == 0){
			statement.setFtype(-1);
		} else {
			statement.setFtype(ftype);
		}
		statement.setFuserroleId(user.getId());
		statement.setFuserid(user.getVmiUserFid());
		statement.setFpayType(0);
		statement.setFbalance(BigDecimal.ZERO);
		statement.setFcreateTime(new Date());
		statement.setFreight(fee);//???
		return statement;
	}
	
	//通过该客户的id跟车型判断是否有司机协议(type=4)
	public BigDecimal driverfee(Integer specId, Integer userroleId,
			CL_Order order) {
		BigDecimal driverfee = new BigDecimal(0);
		List<CL_Protocol> list = protocolDao.getBySpecAndType(specId,
				userroleId, 4);
		if (list.size() > 0) {// 有协议
			CL_Protocol protocol = list.get(0);
			BigDecimal mileage = order.getMileage();// 订单公里数
			BigDecimal startK = protocol.getStartKilometre();// 协议起步公里数
			BigDecimal startP = protocol.getStartPrice();// 起步价
			BigDecimal outK = protocol.getOutKilometre();// 超出公里单价
			String fincrementServe = order.getFincrementServe();// 增值服务
			if (fincrementServe == null) {
				fincrementServe = "";
			}
			
			if (fincrementServe.indexOf("装货") != -1 && fincrementServe.indexOf("卸货") != -1 && specId != 6) {// 有装卸 待改
				// 判断公里数是否超出起步公里
				if (mileage.compareTo(startK) == -1) {// 未超出
					driverfee = startP.add(protocol.getFadd_service_1()).add(protocol.getFadd_service_2());// 未超出
				} else {
					driverfee = startP
							.add((mileage.subtract(startK)).multiply(outK))
							.add(protocol.getFadd_service_1())
							.add(protocol.getFadd_service_2())
							.setScale(2, BigDecimal.ROUND_HALF_UP);
				}
				order.setDriver_serve_1(protocol.getFadd_service_1());//存入司机的装卸费
				order.setDriver_serve_2(protocol.getFadd_service_2());
			} else if (fincrementServe.indexOf("装货") != -1 && fincrementServe.indexOf("卸货") == -1 && specId != 6) {
				// 判断公里数是否超出起步公里
				if (mileage.compareTo(startK) == -1) {// 未超出
					driverfee = startP.add(protocol.getFadd_service_1());// 未超出
				} else {
					driverfee = startP
							.add((mileage.subtract(startK)).multiply(outK))
							.add(protocol.getFadd_service_1())
							.setScale(2, BigDecimal.ROUND_HALF_UP);
				}
				order.setDriver_serve_1(protocol.getFadd_service_1());//存入司机的装卸费
			} else if (fincrementServe.indexOf("装货") == -1 && fincrementServe.indexOf("卸货") != -1 && specId != 6) {
				// 判断公里数是否超出起步公里
				if (mileage.compareTo(startK) == -1) {// 未超出
					driverfee = startP.add(protocol.getFadd_service_2());// 未超出
				} else {
					driverfee = startP
							.add((mileage.subtract(startK)).multiply(outK))
							.add(protocol.getFadd_service_2())
							.setScale(2, BigDecimal.ROUND_HALF_UP);
				}
				order.setDriver_serve_2(protocol.getFadd_service_2());//存入司机的装卸费
			} else {
				// 判断公里数是否超出起步公里
				if (mileage.compareTo(startK) == -1) {
					driverfee = startP;// 未超出
				} else {
					driverfee = startP.add(
							(mileage.subtract(startK)).multiply(outK))
							.setScale(2, BigDecimal.ROUND_HALF_UP);// 超出
				}
			}
			
			//司机新增超出点数计费
			int outOpint = order.getFopint() - 1;
			if(outOpint > 0){
				driverfee = driverfee.add(protocol.getFoutopint().multiply(new BigDecimal(outOpint)));
			}
		}

		return driverfee;
	}
	
	/*
	 * 新需求的运价阶梯写死
	 * 5公里以内为保底
	 * 5-20公里
	 * 20-50公里
	 * 50公里以上
	 */
	/*public BigDecimal driverfee(Integer specId,Integer userroleId,CL_Order order){
		BigDecimal driverfee = new BigDecimal(0);
		BigDecimal outM = BigDecimal.ZERO;//超出公里
		List<CL_Protocol> list = protocolDao.getBySpecAndType(specId, userroleId, 4);
		if(list.size() > 0){//有协议
			CL_Protocol protocol = list.get(0);
			BigDecimal mileage = order.getMileage();//订单公里数
			BigDecimal startK = protocol.getStartKilometre();//协议起步公里数，现在定死5公里
			int s = 0;
			if(mileage.subtract(startK).compareTo(BigDecimal.ZERO) <= 0){
				s = 1;//未超出
			} else {//超出
				outM = mileage.subtract(startK);
				if(outM.subtract(new BigDecimal(15)).compareTo(BigDecimal.ZERO) <= 0){//5-20公里
					s = 2;
				} else if (outM.subtract(new BigDecimal(45)).compareTo(BigDecimal.ZERO) <= 0) {
					s = 3;
				} else {
					s = 4;
				}
			}
			BigDecimal startP = protocol.getStartPrice();//起步价
			BigDecimal out_5_20_K = protocol.getOut5_20_kilometre();//5-20公里单价(包含20公里)
			BigDecimal out_20_50_K = protocol.getOut20_50_kilometre();//20-50公里单价(包含50公里)
			BigDecimal out_50_K = protocol.getOut50_kilometre();//50公里单价
			String fincrementServe = order.getFincrementServe();//增值服务
			if(fincrementServe == null){
				fincrementServe = "";
			}
			if(fincrementServe.indexOf("装卸") != -1 && specId != 6){//有装卸
				switch (s) {
				case 1://0-5
					driverfee = startP.add(protocol.getFadd_service());
					break;
				case 2://5-20
					driverfee = startP.add(outM.multiply(out_5_20_K)).add(protocol.getFadd_service()).setScale(2, BigDecimal.ROUND_HALF_UP);
					break;
				case 3://20-50
					driverfee = startP.add(new BigDecimal(15).multiply(out_5_20_K)).add(outM.subtract(new BigDecimal(20)).multiply(out_20_50_K)).add(protocol.getFadd_service()).setScale(2, BigDecimal.ROUND_HALF_UP);
					break;
				case 4://50-
					driverfee = startP.add(new BigDecimal(15).multiply(out_5_20_K)).add(new BigDecimal(30).multiply(out_20_50_K)).add(outM.subtract(new BigDecimal(50)).multiply(out_50_K)).add(protocol.getFadd_service()).setScale(2, BigDecimal.ROUND_HALF_UP);
					break;

				default:
					driverfee = new BigDecimal(-1);//坏账
				}
				
			} else {
				switch (s) {
				case 1://0-5
					driverfee = startP;
					break;
				case 2://5-20
					driverfee = startP.add(outM.multiply(out_5_20_K)).setScale(2, BigDecimal.ROUND_HALF_UP);
					break;
				case 3://20-50
					driverfee = startP.add(new BigDecimal(15).multiply(out_5_20_K)).add(outM.subtract(new BigDecimal(20)).multiply(out_20_50_K)).setScale(2, BigDecimal.ROUND_HALF_UP);
					break;
				case 4://50-
					driverfee = startP.add(new BigDecimal(15).multiply(out_5_20_K)).add(new BigDecimal(30).multiply(out_20_50_K)).add(outM.subtract(new BigDecimal(50)).multiply(out_50_K)).setScale(2, BigDecimal.ROUND_HALF_UP);
					break;

				default:
					driverfee = new BigDecimal(-1);//坏账
				}
				
			}
		}

		return driverfee;
	}*/
	
}