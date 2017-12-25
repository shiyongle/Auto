package com.pc.appInterface.order;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringEscapeUtils;
//import org.apache.tomcat.util.http.fileupload.FileItem;
//import org.apache.tomcat.util.http.fileupload.FileUploadException;
//import org.apache.tomcat.util.http.fileupload.disk.DiskFileItemFactory;
//import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.pc.appInterface.api.DongjingClient;
import com.pc.controller.BaseController;
import com.pc.dao.Car.ICarDao;
import com.pc.dao.UserRole.IUserRoleDao;
import com.pc.dao.address.IAddressDao;
import com.pc.dao.addto.IaddtoDao;
import com.pc.dao.bank.IbankDao;
import com.pc.dao.carRanking.IcarRankingDao;
import com.pc.dao.clUpload.IuploadDao;
import com.pc.dao.coupons.ICouponsDao;
import com.pc.dao.couponsDetail.impl.CouponsDetailDaoImpl;
import com.pc.dao.distance.impl.DistanceDao;
import com.pc.dao.financeStatement.IFinanceStatementDao;
import com.pc.dao.identification.impl.IdentificationDao;
import com.pc.dao.message.ImessageDao;
import com.pc.dao.order.impl.OrderDao;
import com.pc.dao.orderCarDetail.IorderCarDetailDao;
import com.pc.dao.orderDetail.IorderDetailDao;
import com.pc.dao.protocol.IprotocolDao;
import com.pc.dao.refuse.IrefuseDao;
import com.pc.dao.rule.IRuleDao;
import com.pc.dao.select.IUtilOptionDao;
import com.pc.dao.umeng.IUMengPushDao;
import com.pc.model.CL_Addto;
import com.pc.model.CL_Bank;
import com.pc.model.CL_Car;
import com.pc.model.CL_CarRanking;
import com.pc.model.CL_Coupons;
import com.pc.model.CL_CouponsDetail;
import com.pc.model.CL_Feedback;
import com.pc.model.CL_FinanceStatement;
import com.pc.model.CL_Identification;
import com.pc.model.CL_Order;
import com.pc.model.CL_OrderCarDetail;
import com.pc.model.CL_OrderDetail;
import com.pc.model.CL_Protocol;
import com.pc.model.CL_Rule;
import com.pc.model.CL_Umeng_Push;
import com.pc.model.CL_UserRole;
import com.pc.model.Cl_Upload;
import com.pc.model.Util_Option;
import com.pc.model.Util_UserOnline;
import com.pc.query.order.OrderQuery;
import com.pc.util.CacheUtilByCC;
import com.pc.util.JSONUtil;
import com.pc.util.LatitudeLongitudeDI;
import com.pc.util.MD5Util;
import com.pc.util.RedisUtil;
import com.pc.util.ServerContext;
import com.pc.util.String_Custom;
import com.pc.util.file.FastDFSUtil;
import com.pc.util.pay.OrderMsg;
import com.pc.util.pay.PayUtil;
import com.pc.util.push.Demo;

import cn.org.rapid_framework.page.Page;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller
public class AppOrderControll extends BaseController {
	@Resource
	private IAddressDao addressdao;
	@Resource
	private IUtilOptionDao optionDao;
	@Resource
	private IRuleDao ruleDao;
	@Resource
	private OrderDao orderDao;
	@Resource
	private IorderDetailDao orderDetailDao;
	@Resource
	private IUserRoleDao userRoleDao;
	@Resource
	private IorderCarDetailDao orderCarDetailDao;
	@Resource
	private ICarDao cardao;
	@Resource
	private IprotocolDao protocolDao;
	@Resource
	private IuploadDao iuploadDao;
	@Resource
	private IaddtoDao iaddtoDao;
	@Resource
	private IdentificationDao identificationDao;
	@Resource
	private IcarRankingDao icarRankingDao;
	@Resource
	private CouponsDetailDaoImpl couponsDetailDao;
	// @Resource
	// private IFinanceStatementDao iFinanceStatementDao;
	@Resource
	private DistanceDao distanceDao;

	private OrderQuery orderQuery;
	@Resource
	private ICouponsDao couponsDao;
	@Resource
	private IFinanceStatementDao financeStatementDao;
	@Resource
	private IbankDao bankDao;
	@Resource
	private ImessageDao messageDao;
	@Resource
	private IrefuseDao refuseDao;
	
	@Resource
	private IUMengPushDao iumeng;

	public OrderQuery getOrderQuery() {
		return orderQuery;
	}

	public void setOrderQuery(OrderQuery orderQuery) {
		this.orderQuery = orderQuery;
	}

	protected static final String PUBLICKEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDl5nbvmL8Q8tYGcJAgwS4qdqp2"
			+ "Rwme5FKaR+11vXy89Biu8ruF/KmdS4pk+4gEmoPuHLFc6V6VQ77CgtpgboBDjveU"
			+ "n3HnsN1N2LH/hmn8gDvw+0e7lLDFVEGC6L8d9z+yj0zGe0XMDeEW5zJlVCA2FOYq"
			+ "oQAOkIntynv/nfyP6wIDAQAB";

	protected static final String PERSONALPUBLICKEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCcu9tqg+XyDB7qygFxn4UQu00T"
			+ "WrnbQmIDUnE8zhDf9ZuCr5Czil1XVR4ApnpcVUTTXHoW2WpzBw1gm53OdKjgPc2Q"
			+ "yRq+ROlo7NhYCRFan+b4p+RqGL+U+alMH1zv1Q+LSgQP6QF9loKAsC3i70KdBw4G"
			+ "n7K8fTzoY8PtMqHlSwIDAQAB";

	/*** 获取所有车厢（所有车辆规格） */
	@RequestMapping("/app/order/loadSpec")
	public String loadSpec(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		List<Util_Option> list = optionDao.getAllCarType();
		HashMap<String, Object> m = new HashMap<String, Object>();
		m.put("success", "true");
		m.put("data", list);
		System.out.println(m.get("data"));
		return writeAjaxResponse(response, JSONUtil.getJson(m));
	}

	/*** 根据车厢规格id获得车辆类型 */
	@RequestMapping("/app/order/loadCarType")
	public String loadCarType(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		Integer optionId;
		HashMap<String, Object> map = new HashMap<String, Object>();
		if (request.getParameter("optionId") == null
				|| "".equals(request.getParameter("optionId"))) {
			map.put("success", "false");
			map.put("msg", "请先登录");
			return writeAjaxResponse(response, JSONUtil.getJson(map));
		} else {
			optionId = Integer.parseInt(request.getParameter("optionId"));
		}
		List<Util_Option> list = optionDao.getAllCarSpecByCarType(optionId);
		map.put("success", "true");
		map.put("data", list);
		return writeAjaxResponse(response, JSONUtil.getJson(map));
	}

	/*** 根据车厢规格id获得其他要求 */
	@RequestMapping("/app/order/CarOther")
	public String CarOther(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		Integer optionId;
		HashMap<String, Object> map = new HashMap<String, Object>();
		if (request.getParameter("optionId") == null
				|| "".equals(request.getParameter("optionId"))) {
			map.put("success", "false");
			map.put("msg", "请先登录");
			return writeAjaxResponse(response, JSONUtil.getJson(map));
		} else {
			optionId = Integer.parseInt(request.getParameter("optionId"));
		}
		List<Util_Option> list = optionDao.getOtherByCarSpec(optionId);
		map.put("success", "true");
		map.put("data", list);
		return writeAjaxResponse(response, JSONUtil.getJson(map));
	}

	/*** 获取所有货物类型 */
	@RequestMapping("/app/order/loadGoods")
	public String loadGoods(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		List<Util_Option> list = optionDao.getAllGoodsType();
		HashMap<String, Object> m = new HashMap<String, Object>();
		m.put("success", "true");
		m.put("data", list);
		System.out.println(m.get("data"));
		return writeAjaxResponse(response, JSONUtil.getJson(m));
	}

	/*** 新增协议订单 */
	@RequestMapping("/app/order/saveProtocolOrder")
	@Transactional
	public String saveProtocolOrder(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		Integer userroleId, orderType, specId = null, protocolId = null, fopint = 1, funitId = null, userId;
		String orderString, addressDeliverString, addressReceiptString = null, carTypeId = null, otherId = null, fremark = null, versionCode = null, fsystem = "";
		HashMap<String, Object> map = new HashMap<String, Object>();
		
		Util_UserOnline useronline = ServerContext.getUseronline().get(request.getSession().getId());
		if(useronline == null ){
			map.put("success", "false");
			map.put("msg", "登录超时,请重新登录！");
			return writeAjaxResponse(response, JSONUtil.getJson(map));
		}
		userroleId = useronline.getFuserId();
		// 停止接单开始
		/*SimpleDateFormat formatTemp = new SimpleDateFormat("yyyy-MM-dd");
		CL_Refuse refuse = refuseDao.getById(1);
		Date startTime = refuse.getFstart_time();//停止接单开始时间
		Date endTime = refuse.getFend_time();//停止接单结束时间
		
		if (startTime != null) {
			String start = formatTemp.format(startTime);
			String end = formatTemp.format(endTime);
			Date now = new Date();// 当前时间
			if (!(refuse.getFkey() != null
					&& refuse.getFkey().equals("allowPerson")
					&& refuse.getFvalues() != null && !"".equals(refuse.getFvalues()) && refuse.getFvalues()
					.contains(request.getParameter("id")))) {
				if (startTime.before(now) && endTime.after(now)) {
					map.put("success", "false");
					map.put("msg", start + "至" + end + "期间停止接单!");
					return writeAjaxResponse(response, JSONUtil.getJson(map));
				}
			}

		}*/
		HashMap<String, Object> tm = null;
		try {
			tm = RedisUtil.IsSystemDefend(0,userroleId);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		if(tm != null){
			return writeAjaxResponse(response, JSONUtil.getJson(tm));
		}
		// 停止接单结束
		
		/*if (request.getParameter("id") == null
				|| "".equals(request.getParameter("id"))) {
			map.put("success", "false");
			map.put("msg", "请先登录！");
			return writeAjaxResponse(response, JSONUtil.getJson(map));
		} else {
			userroleId = Integer.parseInt(request.getParameter("id"));
		}*/
		// 判断用户是否欠费
		BigDecimal balance = userRoleDao.getById(userroleId).getFbalance();

		if (balance.compareTo(new BigDecimal(0)) == -1) {
			return this.poClient(response, false, "请先结清您欠的费用！");
		}

		// APP强制更新；
		/*HashMap<String, Util_UserOnline> useronline = ServerContext
				.getUseronline();
		if (request.getSession().getId() == null
				|| request.getSession().getId().equals("")) {
			map.put("success", "false");
			map.put("msg", "登录超时！");
			return writeAjaxResponse(response, JSONUtil.getJson(map));
		}
		versionCode = useronline.get(request.getSession().getId().toString())
				.getVersionCode();*/
		versionCode = useronline.getVersionCode();
		if (versionCode == null || "".equals(versionCode)) {
			map.put("success", "false");
			map.put("msg", "请重新登录更新APP后再下单，谢谢合作！");
			return writeAjaxResponse(response, JSONUtil.getJson(map));
		} else {
//			BigDecimal bVersionCode = new BigDecimal(versionCode);
			/*fsystem = useronline.get(request.getSession().getId().toString())
					.getFsystem();*/
			fsystem = useronline.getFsystem();
			int ftype = 0 ;//0 安卓 1 ios
			if(fsystem!=null && fsystem.startsWith("ios")){
				ftype =  1 ;
			}
			
			if (CacheUtilByCC.isOldVersion(versionCode, ftype)) {
				map.put("success", "false");
				if (fsystem.startsWith("ios")) {
					map.put("msg", "请去苹果商店下载更新，谢谢合作！");
				} else {
					map.put("msg", "请重新登录更新APP后再下单，谢谢合作！");
				}
				return writeAjaxResponse(response, JSONUtil.getJson(map));
			}
		}

		/**
		 * 20160530业务再次确认个人认证跳过允许下单; 20160803因国家规定货运APP必须实名，再次控制未认证不许下单;
		 * 新增下单前认证审核通过判断，开始;
		 */
		CL_Order order = new CL_Order();
		if (!fsystem.startsWith("ios")) {
			order.setFordesource("andriod_" + versionCode);
		} else {
			order.setFordesource(fsystem + "_" + versionCode);
		}
		CL_UserRole userinfo = userRoleDao.getById(userroleId);
		if (userinfo.isSub()) {
			userroleId = userinfo.getFparentid();
		} else {
			if (userinfo.isPassIdentify()) {
				order.setIdentifyType(3);
			} else {
				List<CL_Identification> identifications = identificationDao
						.getStatusByUserRoleId(userroleId);
				if (identifications.size() > 0) {
					map.put("success", "true");
					// order.setIdentifyType());//认证状态值：3正常下单;0 跳到认证界面;1
					// 直接提示拨打客服电话通过认证; 2 跳到认证结果界面
					map.put("data", "{\"identifyType\":\""
							+ identifications.get(0).getStatus() + "\"}");
					return writeAjaxResponse(response, JSONUtil.getJson(map));
				} else {
					return writeAjaxResponse(response, "请先认证啊喂~");
				}
			}
			// 新增下单前认证审核通过判断，结束;
		}

		if (request.getParameter("fremark") == null
				|| "".equals(request.getParameter("fremark"))) {
			fremark = request.getParameter("fremark");
		} else {
			fremark = request.getParameter("fremark").replaceAll(
					"[\\ud800\\udc00-\\udbff\\udfff\\ud800-\\udfff]", "");
		}

		if (request.getParameter("protocolId") == null
				|| "".equals(request.getParameter("protocolId"))) {
			map.put("success", "false");
			map.put("msg", "该用户不是协议用户");
			return writeAjaxResponse(response, JSONUtil.getJson(map));
		} else {
			protocolId = Integer.parseInt(request.getParameter("protocolId"));
		}
		CL_Protocol protocol = protocolDao.getById(protocolId);
		if (protocol.getStatus() == 0) {
			map.put("success", "false");
			map.put("msg", "该协议已经失效");
			return writeAjaxResponse(response, JSONUtil.getJson(map));
		}
		if (request.getParameter("orderType") == null
				|| "".equals(request.getParameter("orderType"))) {
			map.put("success", "false");
			map.put("msg", "无法获知订单类型！");
			return writeAjaxResponse(response, JSONUtil.getJson(map));
		} else {
			orderType = Integer.parseInt(request.getParameter("orderType"));
		}
		if (orderType == 1 || orderType == 3) {
			if (request.getParameter("specId") == null
					|| "".equals(request.getParameter("specId"))
					|| request.getParameter("specId") == "null") {
				map.put("success", "false");
				map.put("msg", "请选择车厢！");
				return writeAjaxResponse(response, JSONUtil.getJson(map));
			} else {
				specId = Integer.parseInt(request.getParameter("specId"));
			}
			if (request.getParameter("carTypeId") == null
					|| "".equals(request.getParameter("carTypeId"))
					|| request.getParameter("carTypeId") == "null") {
				map.put("success", "false");
				map.put("msg", "请选择车型！");
				return writeAjaxResponse(response, JSONUtil.getJson(map));
			} else {
				carTypeId = request.getParameter("carTypeId");
			}
			otherId = request.getParameter("otherId");
		}
		if (request.getParameter("order") == null
				|| "".equals(request.getParameter("order"))) {
			map.put("success", "false");
			map.put("msg", "请先录入订单信息！");
			return writeAjaxResponse(response, JSONUtil.getJson(map));
		} else {
			orderString = request.getParameter("order");
		}
		if (request.getParameter("fopint") == null
				|| "".equals(request.getParameter("fopint"))) {
			fopint = 1;
		} else {
			fopint = Integer.valueOf(request.getParameter("fopint").toString());
		}
		if (request.getParameter("addressDeliver") == null
				|| "".equals(request.getParameter("addressDeliver"))) {
			map.put("success", "false");
			map.put("msg", "请先选择发货地址！");
			return writeAjaxResponse(response, JSONUtil.getJson(map));
		} else {
			addressDeliverString = request.getParameter("addressDeliver");
		}

		JSONArray jsonsorder = JSONArray.fromObject(orderString);
		// CL_Order order =new CL_Order();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		order.setType(jsonsorder.getJSONObject(0).getInt("type"));// 订单类型

		if (order.getType() == 2) {
			map.put("success", "false");
			map.put("msg", "系统已经停止零单服务！");
			return writeAjaxResponse(response, JSONUtil.getJson(map));
		}

		order.setGoodsTypeId(jsonsorder.getJSONObject(0).get("goodsTypeId")
				.toString());// 货物类型ID
		order.setGoodsTypeName(jsonsorder.getJSONObject(0).get("goodsTypeName")
				.toString());// 货物类型名称
		order.setCreateTime(new Date());
		try {
			Date loadTime = format.parse(jsonsorder.getJSONObject(0)
					.get("loadedTime").toString());
			long time = loadTime.getTime() - order.getCreateTime().getTime();
			long ss = time - 60 * 60000;
			if (ss >= 0) {
				order.setLoadedTime(format.parse(jsonsorder.getJSONObject(0)
						.get("loadedTime").toString()));
			} else {
				map.put("success", "false");
				map.put("msg", "装车时间必须大于创建时间一小时！");
				return writeAjaxResponse(response, JSONUtil.getJson(map));
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}

		if (protocol.getType() != 3) {
			order.setMileage(new BigDecimal(jsonsorder.getJSONObject(0)
					.get("mileage").toString()).setScale(2,
					BigDecimal.ROUND_HALF_UP));// 里程
			if (request.getParameter("addressReceipt") == null
					|| "".equals(request.getParameter("addressReceipt"))) {
				map.put("success", "false");
				map.put("msg", "请先选择收货地址！");
				return writeAjaxResponse(response, JSONUtil.getJson(map));
			} else {
				addressReceiptString = request.getParameter("addressReceipt");

				// lancher修改，增加下单前判断地址是否依旧有效功能------------begin-------------------
				JSONArray jsAddressDel = JSONArray
						.fromObject(addressDeliverString);
				JSONArray jsAddressRec = JSONArray
						.fromObject(addressReceiptString);
				// 协议整车包天没有收货地址
				if (order.getType() != 3) {
					JSONObject jdel = jsAddressDel.getJSONObject(0);
					// 取出发货地址Id
					if (jdel.get("id") != null && !"".equals(jdel.get("id"))) {
						int addressDelId = Integer.parseInt(jdel.get("id")
								.toString());
						// 加个if查询数据库中有无地址
						if (addressdao.isExist(addressDelId) > 0) {
							for (int i = 0; i < jsAddressRec.size(); i++) {
								JSONObject jrec = jsAddressRec.getJSONObject(i);
								// 取出收货地址ID
								int addressRecId = Integer.parseInt(jrec.get(
										"id").toString());
								// 判断地址在数据库中是否被删
								if (addressdao.isExist(addressRecId) > 0) {
									// 存在就是有效地址，继续循环
								} else {
									map.put("success", "false");
									map.put("msg", "地址已失效，请重新录入！");
									return writeAjaxResponse(response,
											JSONUtil.getJson(map));
								}
							}
						} else {
							map.put("success", "false");
							map.put("msg", "地址已失效，请重新录入！");
							return writeAjaxResponse(response,
									JSONUtil.getJson(map));
						};
					}
				}
				// lancher修改，增加下单前判断地址是否依旧有效功能------------end-------------------

				// 判断收货地址是否为多条，是则跳过--------BEGIN--------BY LANCHER
				if (jsAddressRec.size() == 1) {
					String vmi_user_fid = ServerContext.getUseronline()
							.get(request.getSession().getId()).getFuserid();
					CL_UserRole user = userRoleDao.getByVmiUserFidAndRoleId(
							vmi_user_fid, 1);
					Integer fcustomer_id = user.getId();
					Integer faddressRec_id = Integer.parseInt(jsAddressRec
							.getJSONObject(0).get("id").toString());
					Integer faddressDel_id = Integer.parseInt(jsAddressDel
							.getJSONObject(0).get("id").toString());
					BigDecimal fmileage = distanceDao.getMileage(fcustomer_id,
							faddressDel_id, faddressRec_id);
					if (fmileage != null && !"".equals(fmileage)) {
						order.setMileage(fmileage);
					}
				}
				// 判断收货地址是否为多条，是则跳过--------END--------BY LANCHER
			}
		}

		if (userinfo.isSub()) {
			order.setCreator(userroleId);// 创建人
			order.setSubUserId(userinfo.getId());// 创建人
			order.setSubName(userinfo.getVmiUserName());
			order.setOperator(userinfo.getId());// 操作人ID
		} else {
			order.setCreator(userroleId);// 创建人
			order.setOperator(userroleId);// 操作人ID
		}

		order.setProtocolId(protocolId);// 协议表IDf
		order.setFremark(fremark);
		order.setFopint(fopint);
		order.setProtocolType(1);
		if (protocol.getFunitId() != null) {
			order.setFunitId(protocol.getFunitId());
		}
		// ============================================协议计算运费=============开始=====================================//
		String serve = order.getFincrementServe();// 订单增值服务
		if (order.getType() == 1) {// 整车计算运费
			if (protocol != null) {
			
				//新需求逻辑代码块---------------------------begin
				/*if (order.getMileage().compareTo(protocol.getStartKilometre()) == 1) {
					int s = 0;
					BigDecimal outM = order.getMileage().subtract(protocol.getStartKilometre()); //超出公里
					if(protocol.getCarSpecId() == 3){//4.2米
						if (outM.subtract(new BigDecimal(15)).compareTo(BigDecimal.ZERO) <= 0) {// 5-20公里
							s = 1;
						} else if (outM.subtract(new BigDecimal(45)).compareTo(BigDecimal.ZERO) <= 0) {// 20-50公里
							s = 2;
						} else {// 50公里以上
							s = 3;
						}
					} else if (protocol.getCarSpecId() == 5) {//6.8米
						if (outM.subtract(new BigDecimal(30)).compareTo(BigDecimal.ZERO) <= 0) {// 20-50公里
							s = 1;
						} else {// 50公里以上
							s = 2;
						}
					} else {
						map.put("success", "false");
						map.put("msg", "无效车型！");
						return writeAjaxResponse(response,JSONUtil.getJson(map));
					}
					
					BigDecimal opintprice = new BigDecimal(0);//超出点数价格计算
					if (fopint > protocol.getFopint()) {
						Integer foutopint = fopint - protocol.getFopint();
						opintprice = protocol.getFoutopint().multiply(new BigDecimal(foutopint));
					}
					
					BigDecimal dis = new BigDecimal(1);
					if (protocol.getFdiscount() != null) {
						dis = protocol.getFdiscount();// 折扣
					} else {
						map.put("success", "false");
						map.put("msg", "协议有误，请联系客服！");//整车协议，必有折扣
						return writeAjaxResponse(response,JSONUtil.getJson(map));
					}
					
					BigDecimal startP = protocol.getStartPrice();//起步价
					BigDecimal out_5_20_K = protocol.getOut5_20_kilometre();//5-20公里单价(包含20公里)
					BigDecimal out_20_50_K = protocol.getOut20_50_kilometre();//20-50公里单价(包含50公里)
					BigDecimal out_50_K = protocol.getOut50_kilometre();//50公里单价
					String fincrementServe = order.getFincrementServe();//增值服务
					if(fincrementServe == null){
						fincrementServe = "";
					}
					BigDecimal fee = BigDecimal.ZERO;
					if(fincrementServe.indexOf("装卸") != -1){//有装卸
						if(protocol.getCarSpecId() == 3){
							switch (s) {
							case 1://5-20
								fee = startP.add(outM.multiply(out_5_20_K)).add(opintprice).multiply(dis).add(protocol.getFadd_service()).setScale(2, BigDecimal.ROUND_HALF_UP);
								break;
							case 2://20-50
								fee = startP.add(new BigDecimal(15).multiply(out_5_20_K)).add(order.getMileage().subtract(new BigDecimal(20)).multiply(out_20_50_K)).add(opintprice).multiply(dis).add(protocol.getFadd_service()).setScale(2, BigDecimal.ROUND_HALF_UP);
								break;
							case 3://50-
								fee = startP.add(new BigDecimal(15).multiply(out_5_20_K)).add(new BigDecimal(30).multiply(out_20_50_K)).add(order.getMileage().subtract(new BigDecimal(50)).multiply(out_50_K)).add(opintprice).multiply(dis).add(protocol.getFadd_service()).setScale(2, BigDecimal.ROUND_HALF_UP);
								break;
								
							default:
								fee = new BigDecimal(-1);//坏账
							}
						} else {
							switch (s) {
							case 1://20-50
								fee = startP.add(outM.multiply(out_20_50_K)).add(opintprice).multiply(dis).add(protocol.getFadd_service()).setScale(2, BigDecimal.ROUND_HALF_UP);
								break;
							case 2://50-
								fee = startP.add(new BigDecimal(30).multiply(out_20_50_K)).add(order.getMileage().subtract(new BigDecimal(50)).multiply(out_50_K)).add(opintprice).multiply(dis).add(protocol.getFadd_service()).setScale(2, BigDecimal.ROUND_HALF_UP);
								break;
								
							default:
								fee = new BigDecimal(-1);//坏账
							}
						}
						
					} else {
						if(protocol.getCarSpecId() == 3){
							switch (s) {
							case 1://5-20
								fee = startP.add(outM.multiply(out_5_20_K)).add(opintprice).multiply(dis).setScale(2, BigDecimal.ROUND_HALF_UP);
								break;
							case 2://20-50
								fee = startP.add(new BigDecimal(15).multiply(out_5_20_K)).add(order.getMileage().subtract(new BigDecimal(20)).multiply(out_20_50_K)).add(opintprice).multiply(dis).setScale(2, BigDecimal.ROUND_HALF_UP);
								break;
							case 3://50-
								fee = startP.add(new BigDecimal(15).multiply(out_5_20_K)).add(new BigDecimal(30).multiply(out_20_50_K)).add(order.getMileage().subtract(new BigDecimal(50)).multiply(out_50_K)).add(opintprice).multiply(dis).setScale(2, BigDecimal.ROUND_HALF_UP);
								break;
								
							default:
								fee = new BigDecimal(-1);//坏账
							}
						} else {
							switch (s) {
							case 1://20-50
								fee = startP.add(outM.multiply(out_20_50_K)).add(opintprice).multiply(dis).setScale(2, BigDecimal.ROUND_HALF_UP);
								break;
							case 2://50-
								fee = startP.add(new BigDecimal(30).multiply(out_20_50_K)).add(order.getMileage().subtract(new BigDecimal(50)).multiply(out_50_K)).add(opintprice).multiply(dis).setScale(2, BigDecimal.ROUND_HALF_UP);
								break;
								
							default:
								fee = new BigDecimal(-1);//坏账
							}
						}
						
					}
					
					if(fee == new BigDecimal(-1)){
						map.put("success", "false");
						map.put("msg", "坏账，请联系客服！");
						return writeAjaxResponse(response,JSONUtil.getJson(map));
					}
					order.setFreight(fee);
					order.setForiginfreight(order.getFreight());
					
				} else {
					if (protocol.getFdiscount() != null) {
						BigDecimal dis = protocol.getFdiscount();//折扣
						BigDecimal opintprice = new BigDecimal(0);//超出点数
						if (fopint > protocol.getFopint()) {
							Integer foutopint = fopint - protocol.getFopint();
							opintprice = protocol.getFoutopint().multiply(
									new BigDecimal(foutopint));
						}
						BigDecimal fre = opintprice.add(protocol
								.getStartPrice());
						if (serve != null) {
							if (serve.indexOf("装卸") != -1) {
								order.setFreight(fre.multiply(dis)
										.add(protocol.getFadd_service())
										.setScale(2, BigDecimal.ROUND_HALF_UP));
								order.setForiginfreight(order.getFreight());
							} else {
								order.setFreight(fre.multiply(dis).setScale(2,
										BigDecimal.ROUND_HALF_UP));
								order.setForiginfreight(order.getFreight());
							}
						} else {
							order.setFreight(fre.multiply(dis).setScale(2,
									BigDecimal.ROUND_HALF_UP));
							order.setForiginfreight(order.getFreight());
						}
					} else {
						map.put("success", "false");
						map.put("msg", "协议有误，请联系客服！");//整车协议，必有折扣
						return writeAjaxResponse(response,JSONUtil.getJson(map));
					}
				}*/
				//新需求逻辑代码块---------------------------end by lancher
				
				// 实际运输距离大于规则起步公里数:运费=(规则起步价+(实际距离-规则起步公里数)×规则公里单价)
				if (order.getMileage().compareTo(protocol.getStartKilometre()) == 1) {
					BigDecimal km = order.getMileage().subtract(
							protocol.getStartKilometre());// 减法
					BigDecimal fee1 = km.multiply(protocol.getOutKilometre());// 乘法
					BigDecimal opintprice = new BigDecimal(0);
					if (fopint > protocol.getFopint()) {
						Integer foutopint = fopint - protocol.getFopint();
						opintprice = protocol.getFoutopint().multiply(
								new BigDecimal(foutopint));
					}
					if (protocol.getFdiscount() != null) {
						BigDecimal dis = protocol.getFdiscount();// 折扣
						BigDecimal fre = protocol.getStartPrice().add(fee1)
								.add(opintprice); // 协议折扣运费
						if (serve != null) {
							if (serve.indexOf("装卸") != -1) {// 客户有选装卸服务 待改
								order.setFreight(fre.multiply(dis)
										.add(protocol.getFadd_service_1())
										.setScale(2, BigDecimal.ROUND_HALF_UP));
								// 设入原运费,以配合运费调整
								order.setForiginfreight(order.getFreight());
								order.setFtotalFreight(order.getFreight());
							} else {
								order.setFreight(fre.multiply(dis).setScale(2,
										BigDecimal.ROUND_HALF_UP));
								// 设入原运费,以配合运费调整
								order.setForiginfreight(order.getFreight());
								order.setFtotalFreight(order.getFreight());
							}
						} else {
							order.setFreight(fre.multiply(dis).setScale(2,
									BigDecimal.ROUND_HALF_UP));
							// 设入原运费,以配合运费调整
							order.setForiginfreight(order.getFreight());
							order.setFtotalFreight(order.getFreight());
						}
					} else {
						if (serve != null) {
							if (serve.indexOf("装卸") != -1) {// 客户有选装卸服务 待改
								order.setFreight(protocol.getStartPrice()
										.add(fee1).add(opintprice)
										.add(protocol.getFadd_service_1())
										.setScale(2, BigDecimal.ROUND_HALF_UP));// 加法
																				// 无折扣情况下的运费
								order.setForiginfreight(order.getFreight());// 加法
								order.setFtotalFreight(order.getFreight());
							} else {
								order.setFreight(protocol.getStartPrice()
										.add(fee1).add(opintprice)
										.setScale(2, BigDecimal.ROUND_HALF_UP));// 加法
																				// 无折扣情况下的运费
								order.setForiginfreight(order.getFreight());// 加法
								order.setFtotalFreight(order.getFreight());
							}
						} else {
							order.setFreight(protocol.getStartPrice().add(fee1)
									.add(opintprice)
									.setScale(2, BigDecimal.ROUND_HALF_UP));// 加法
																			// 无折扣情况下的运费
							order.setForiginfreight(order.getFreight());// 加法
							order.setFtotalFreight(order.getFreight());
							// 无折扣情况下的运费
						}
					}
					// 实际运输距离小于/等于规则起步公里数:运费=规则起步价
				} else if (order.getMileage().compareTo(
						protocol.getStartKilometre()) == -1
						|| order.getMileage().compareTo(
								protocol.getStartKilometre()) == 0) {
					if (protocol.getFdiscount() != null) {
						BigDecimal dis = protocol.getFdiscount();
						BigDecimal opintprice = new BigDecimal(0);
						if (fopint > protocol.getFopint()) {
							Integer foutopint = fopint - protocol.getFopint();
							opintprice = protocol.getFoutopint().multiply(
									new BigDecimal(foutopint));
						}
						BigDecimal fre = opintprice.add(protocol
								.getStartPrice());
						if (serve != null) {
							if (serve.indexOf("装卸") != -1) {//待改
								order.setFreight(fre.multiply(dis)
										.add(protocol.getFadd_service_1())
										.setScale(2, BigDecimal.ROUND_HALF_UP));
								order.setForiginfreight(order.getFreight());
								order.setFtotalFreight(order.getFreight());
							} else {
								order.setFreight(fre.multiply(dis).setScale(2,
										BigDecimal.ROUND_HALF_UP));
								order.setForiginfreight(order.getFreight());
								order.setFtotalFreight(order.getFreight());
							}
						} else {
							order.setFreight(fre.multiply(dis).setScale(2,
									BigDecimal.ROUND_HALF_UP));
							order.setForiginfreight(order.getFreight());
							order.setFtotalFreight(order.getFreight());
						}
					} else {
						BigDecimal opintprice = new BigDecimal(0);
						if (fopint > protocol.getFopint()) {
							Integer foutopint = fopint - protocol.getFopint();
							opintprice = protocol.getFoutopint().multiply(
									new BigDecimal(foutopint));
						}
						if (serve != null) {
							if (serve.indexOf("装卸") != -1) {//待改
								order.setFreight(protocol.getStartPrice()
										.add(opintprice)
										.add(protocol.getFadd_service_1())
										.setScale(2, BigDecimal.ROUND_HALF_UP));
								order.setForiginfreight(order.getFreight());
								order.setFtotalFreight(order.getFreight());
							} else {
								order.setFreight(protocol.getStartPrice()
										.add(opintprice)
										.setScale(2, BigDecimal.ROUND_HALF_UP));
								order.setForiginfreight(order.getFreight());
								order.setFtotalFreight(order.getFreight());
							}
						} else {
							order.setFreight(protocol.getStartPrice()
									.add(opintprice)
									.setScale(2, BigDecimal.ROUND_HALF_UP));
							order.setForiginfreight(order.getFreight());
							order.setFtotalFreight(order.getFreight());
						}
					}
				}
			} else {
				map.put("success", "false");
				map.put("msg", "所选车厢,规则遗失,请联系客服");
				return writeAjaxResponse(response, JSONUtil.getJson(map));
			}
		} else if (order.getType() == 2) {// 零担计算运费
			map.put("success", "false");
			map.put("msg", "零担已失效,请联系客服");
			return writeAjaxResponse(response, JSONUtil.getJson(map));
			/*if (jsonsorder.getJSONObject(0).get("length").toString().length() > 9) {
				map.put("success", "false");
				map.put("msg", "太长啦");
				return writeAjaxResponse(response, JSONUtil.getJson(map));
			}
			order.setLength(new BigDecimal(jsonsorder.getJSONObject(0)
					.get("length").toString()));// 长度
			if (new BigDecimal(jsonsorder.getJSONObject(0).get("famount")
					.toString()).compareTo(new BigDecimal(0)) == 1) {
				order.setFamount(new BigDecimal(jsonsorder.getJSONObject(0)
						.get("famount").toString()));
			}
			if (protocol != null) {
				BigDecimal opintprice = new BigDecimal(0);
				if (fopint > protocol.getFopint()) {
					Integer foutopint = fopint - protocol.getFopint();
					opintprice = protocol.getFoutopint().multiply(
							new BigDecimal(foutopint));
				}
				BigDecimal price = protocol.getStartPrice();
				if (order.getMileage().compareTo(protocol.getStartKilometre()) == 1) {
					BigDecimal km = order.getMileage().subtract(
							protocol.getStartKilometre());// 减法
					BigDecimal feel = km.multiply(protocol.getOutKilometre());// 乘法
					price = price.add(feel);
				}
				if (order.getFamount().compareTo(protocol.getStartNumber()) == 1) {
					BigDecimal number = order.getFamount().subtract(
							protocol.getStartNumber());// 减法
					BigDecimal feelNumber = number.multiply(protocol
							.getOutNumprice());// 乘法
					price = price.add(feelNumber);
				}
				if (serve != null) {
					if (serve.indexOf("装卸") != -1) {
						order.setFreight(price.add(opintprice)
								.add(protocol.getFadd_service())
								.setScale(2, BigDecimal.ROUND_HALF_UP));
						order.setForiginfreight(order.getFreight());
					} else {
						order.setFreight(price.add(opintprice).setScale(2,
								BigDecimal.ROUND_HALF_UP));
						order.setForiginfreight(order.getFreight());
					}
				} else {
					order.setFreight(price.add(opintprice).setScale(2,
							BigDecimal.ROUND_HALF_UP));
					order.setForiginfreight(order.getFreight());
				}
			} else {
				map.put("success", "false");
				map.put("msg", "零担规则遗失,请联系客服");
				return writeAjaxResponse(response, JSONUtil.getJson(map));
			}*/
		} else if (order.getType() == 3) {// 包天计费规则
			if (protocol != null) {
				order.setFreight(protocol.getTimePrice().setScale(2,
						BigDecimal.ROUND_HALF_UP));
				order.setForiginfreight(order.getFreight());
				order.setFtotalFreight(order.getFreight());
			} else {
				map.put("success", "false");
				map.put("msg", "包天规则遗失,请联系客服");
				return writeAjaxResponse(response, JSONUtil.getJson(map));
			}
		}
		order.setNumber(CacheUtilByCC.getOrderNumber(new String("CL_Order"),
				new String("O"), 8));
		order.setStatus(9); // 新状态 确认订单
		JSONArray addressDel = JSONArray.fromObject(addressDeliverString);
		JSONArray addressRec = JSONArray.fromObject(addressReceiptString);

		/** 计算运费 Start */
		// order.setFdriverfee(order.getFreight().multiply(CalcTotalField.calDriverFee(order.getFreight())).setScale(1,BigDecimal.ROUND_HALF_UP));
		/** 计算运费 End */

		this.orderDao.save(order);
		// ============================================整车计算运费=============结束=====================================//
		// =====================
		// 增加订单车辆明细===============================开始=====================================//
		if (order.getType() == 1 || order.getType() == 3) {
			String[] carTypeString = carTypeId.split(",");
			if (otherId != null && !"".equals(otherId)
					&& !otherId.contains("null")) {
				String[] carOtherString = otherId.split(",");
				for (int m = 0; m < carOtherString.length; m++) {
					for (int n = 0; n < carTypeString.length; n++) {
						CL_OrderCarDetail orderCarDetail1 = new CL_OrderCarDetail();
						orderCarDetail1.setOrderId(order.getId());
						orderCarDetail1.setCarSpecId(specId);
						// if(Integer.valueOf(carTypeString[n])>22){
						// System.out.println("**********************数据有问题************************"+order.getNumber());
						// map.put("success", "false");
						// map.put("msg","车辆类型数据有误！");
						// return writeAjaxResponse(response,
						// JSONUtil.getJson(map));
						// }
						orderCarDetail1.setCarTypeId(Integer
								.valueOf(carTypeString[n]));
						orderCarDetail1.setCarOtherId(Integer
								.valueOf(carOtherString[m]));
						orderCarDetail1.setCreateTime(new Date());
						orderCarDetailDao.save(orderCarDetail1);
					}
				}
			} else {
				for (int i = 0; i < carTypeString.length; i++) {
					CL_OrderCarDetail orderCarDetail2 = new CL_OrderCarDetail();
					orderCarDetail2.setOrderId(order.getId());
					orderCarDetail2.setCarSpecId(specId);
					// if(Integer.valueOf(carTypeString[i])>22){
					// System.out.println("**********************数据有问题************************"+order.getNumber());
					// map.put("success", "false");
					// map.put("msg","车辆类型数据有误！");
					// return writeAjaxResponse(response,
					// JSONUtil.getJson(map));
					// }
					orderCarDetail2.setCarTypeId(Integer
							.valueOf(carTypeString[i]));
					orderCarDetail2.setCreateTime(new Date());
					orderCarDetailDao.save(orderCarDetail2);
				}
			}
		}

		// =====================
		// 增加订单车辆明细===============================结束=====================================//
		this.createDetail(addressDel, order.getId(), 1);
		if (protocol.getType() != 3) {
			this.createDetail(addressRec, order.getId(), 2);
		}
		order.setFreight(order.getFreight().setScale(2,
				BigDecimal.ROUND_HALF_UP));
		order.setForiginfreight(order.getFreight());
		order.setFtotalFreight(order.getFreight());

		if (userRoleDao.getById(userroleId).isProtocol()) {
			// 根据卸货地址数判断是否可以支持运费到付 BY CC 2016-05-17 START
			if (addressRec.size() == 1 && order.getType() != 3) {
				order.setPayMethod("0,1,2,3,4,5");
			} else {
				order.setPayMethod("0,1,2,3,5");
			}

			// 根据卸货地址数判断是否可以支持运费到付 BY CC 2016-05-17 END
			// order.setPayMethod("0,1,2,3,4,5");//2016-05-17 CC BAK
		} else {
			// 根据卸货地址数判断是否可以支持运费到付 BY CC 2016-05-17 START
			if (addressRec.size() == 1 && order.getType() != 3) {
				order.setPayMethod("0,1,2,3,4");
			} else {
				order.setPayMethod("0,1,2,3");
			}
			// 根据卸货地址数判断是否可以支持运费到付 BY CC 2016-05-17 end
			// order.setPayMethod("0,1,2,3,4");//2016-05-17 CC BAK
		}
		map.put("success", "true");
		map.put("data", order);
		return writeAjaxResponse(response, JSONUtil.getJson(map));
	}

	/**
	 * 字符串转换unicode
	 */
	// public String stringToUnicode(String string) {
	// StringBuffer unicode = new StringBuffer();
	// for (int i = 0; i < string.length(); i++) {
	// // 取出每一个字符
	// char c = string.charAt(i);
	// // 转换为unicode
	// unicode.append("\\u" + Integer.toHexString(c));
	// }
	// return unicode.toString();
	// }
	//
	// private String Emoji2unicode(String s)
	// {
	// String result="";
	// for(int i=0;i<s.length();i++)
	// {
	// s.matches(regex)
	// }
	// return result;
	// }
	//
	//

	/**** 获取支付方式 ****/
	@RequestMapping("/app/order/getUserPayType")
	public String getUserPayType(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String number = request.getParameter("number"); // 订单号
		String fbusinessType = request.getParameter("fbusinessType"); // 支付方式

		// 充值判断
		if (!String_Custom.noNull(fbusinessType)) {
			if (String_Custom.noNumber(fbusinessType))
				return this.poClient(response, false, "错误的业务类型");

			int businessType = Integer.parseInt(fbusinessType);
			if (businessType == 1) {
				return this.poClient(response, true,
						"[{\"payMethod\":\"1,2,3\"}]");
			}
		}

		if (String_Custom.noNull(number))
			return this.poClient(response, false, "订单编号未获取到");

		// 获取用户id
		Integer userroleid = ServerContext.getUseronline()
				.get(request.getSession().getId().toString()).getFuserId();
		System.out.println(userroleid);

		// 根据订单编号 获取订单信息
		CL_Order order = orderDao.getByNumber(number);
		if (order == null)
			return this.poClient(response, false, "获取不到该订单信息");

		// 用户返回数据
		HashMap<String, Object> map = new HashMap<String, Object>();
		int protocolType = order.getProtocolType();

		String payMethod2 = "";
		if (protocolType == 0) {
			if (this.orderDetailDao.getByOrderId(order.getId(), 2).size() == 1) {
				payMethod2 = "0,1,2,3,4";
			} else {
				payMethod2 = "0,1,2,3";
			}
		} else if (protocolType == 1) {
			if (userRoleDao.getById(order.getCreator()).isProtocol()) {
				// 根据卸货地址数判断是否可以支持运费到付 BY CC 2016-05-17 START
				if (this.orderDetailDao.getByOrderId(order.getId(), 2).size() == 1
						&& order.getType() != 3) {
					payMethod2 = "0,1,2,3,4,5";
				} else {
					payMethod2 = "0,1,2,3,5";
				}
			} else {
				// 根据卸货地址数判断是否可以支持运费到付 BY CC 2016-05-17 START
				if (this.orderDetailDao.getByOrderId(order.getId(), 2).size() == 1
						&& order.getType() != 3) {
					payMethod2 = "0,1,2,3,4";
				} else {
					payMethod2 = "0,1,2,3";
				}

			}
		}
		
		/*
		BigDecimal actualAmount = BigDecimal.ZERO;
		BigDecimal serve_1 = order.getServe_1() == null?BigDecimal.ZERO:order.getServe_1();//装货费
		BigDecimal serve_2 = order.getServe_2() == null?BigDecimal.ZERO:order.getServe_2();//卸货费
*/		if (String_Custom.noNull(fbusinessType)) {
			/*if(order.getFispass_audit() != null && order.getFispass_audit() == 0){
				actualAmount = order.getForiginfreight().add(serve_1).add(serve_2);
			} else {
				actualAmount = order.getFreight().add(serve_1).add(serve_2);
			}
			//先订单优惠券再计算增值服务优惠券，避免订单优惠券直减太多直接将运费减成0
			if (order.getCouponsId() != null
					&& order.getCouponsId() != 0) {//订单优惠券
				//新逻辑：判断优惠金额是否超过运费----------------begin
				if(actualAmount.compareTo(order.getDiscount()) <= 0){
					actualAmount = BigDecimal.ZERO;
					payMethod2 = "0";//该需求只允许余额支付
				} else {
					//新逻辑：判断优惠金额是否超过运费----------------end
					actualAmount = actualAmount.subtract(order.getDiscount());
				}
			}
			if (order.getCouponsDetailId() != null
					&& order.getCouponsDetailId() != 0) {//增值服务优惠券
				CL_CouponsDetail detail = this.couponsDetailDao.getById(order.getCouponsDetailId());
				if(detail.getFaddserviceName().indexOf("装货") != -1){
					actualAmount = actualAmount.subtract(serve_1);
				} else if (detail.getFaddserviceName().indexOf("卸货") != -1) {
					actualAmount = actualAmount.subtract(serve_2);
				}
			}*/
			if(order.getFtotalFreight().compareTo(BigDecimal.ZERO) == 0){
				payMethod2 = "0";//该需求只允许余额支付
			}
			map.put("actualAmount", order.getFtotalFreight());
		} else {

			int businessType = Integer.parseInt(fbusinessType);
			if (businessType == 1) {
				payMethod2 = payMethod2.replace("0,", "");
				payMethod2 = payMethod2.replace(",4", "");
				payMethod2 = payMethod2.replace(",5", "");
			} else if (businessType == 2) {
				payMethod2 = payMethod2.replace(",4", "");
			}
		}
		map.put("payMethod", payMethod2);

		List<HashMap<String, Object>> list = new ArrayList<>();
		list.add(map);
		return this.poClient(response, true, JSONUtil.getJson(list));
	}

	/*** 获取确认订单信息 */
	@RequestMapping("/app/order/getOrderConfirm")
	public String getOrderConfirm(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		BigDecimal serveMoney_1 = new BigDecimal(0);//装货费
		BigDecimal serveMoney_2 = new BigDecimal(0);//卸货费
		String number = request.getParameter("number"); // 订单号

		if (String_Custom.noNull(number))
			return this.poClient(response, false, "订单编号未获取到");

		// 获取用户id
		Integer userroleid = ServerContext.getUseronline()
				.get(request.getSession().getId().toString()).getFuserId();
		System.out.println(userroleid);

		// 根据订单编号 获取订单信息
		CL_Order order = orderDao.getByNumber(number);
		if (order == null)
			return this.poClient(response, false, "获取不到该订单信息");

		// 用户返回数据
		HashMap<String, Object> map = new HashMap<String, Object>();

		int protocolType = order.getProtocolType();
		map.put("protocolType", protocolType); // 是否协议订单
		map.put("orderNumber", order.getNumber()); // 订单编号
		map.put("orderFreight", order.getFreight()); // 金额
		map.put("orderMileage", order.getMileage()); // 公里数
		map.put("orderId", order.getId()); // 订单id
		map.put("type", order.getType()); // 类型1-整车2-零担3-包天
		
		if(order.getType() != 3){
			if(protocolType == 1){
				CL_Protocol protocol = protocolDao.getById(order.getProtocolId());
				serveMoney_1 = protocol.getFadd_service_1() == null?BigDecimal.ZERO:protocol.getFadd_service_1();
				serveMoney_2 = protocol.getFadd_service_2() == null?BigDecimal.ZERO:protocol.getFadd_service_2();
			} else {
				List<CL_Rule> rulist = ruleDao.getBySpec(orderCarDetailDao.getByOrderId(order.getId()).get(0).getCarSpecId());
				if(rulist.size() > 0){
					CL_Rule rule = rulist.get(0);
					serveMoney_1 = rule.getFadd_service_1() == null?BigDecimal.ZERO:rule.getFadd_service_1();
					serveMoney_2 = rule.getFadd_service_2() == null?BigDecimal.ZERO:rule.getFadd_service_2();
				} else {
					return this.poClient(response, false, "未获取到该车型规则");
				}
			}
		}
		map.put("stevedoredFee_1", serveMoney_1);//装货费
		map.put("stevedoredFee_2", serveMoney_2);//卸货费
		if (order.getType() != 3) {
			// 增值服务返回ø
			JSONArray joa = new JSONArray();
			String[] title;
			String[] context;
			int a = 0;
			Integer specId = orderCarDetailDao.getByOrderId(order.getId()).get(0).getCarSpecId();
			if(specId == 6){
				title = new String[]{"回单原件", "上楼", "代收货款"};
				context = new String[]{"线下协商、支付","线下协商、支付", "免费"};
				a = 1;
			} else {
				title = new String[]{"装货", "卸货", "回单原件", "上楼", "代收货款"};
				context = new String[]{"装货费"+serveMoney_1+"元","卸货费"+serveMoney_2+"元", "线下协商、支付",
						"线下协商、支付", "免费"};
			}
			for (int i = 0; i < title.length; i++) {
				JSONObject JO = new JSONObject();
				JO.put("id", i+a);
				JO.put("title", title[i]);
				JO.put("context", context[i]);
				JO.put("isc", 0);
				joa.add(i, JO);
			}
			// 只有
			if (this.orderDetailDao.getByOrderId(order.getId(), 2).size() != 1) {
				joa.remove(title.length - 1);
			}
			map.put("server", joa);
		}
		List<HashMap<String, Object>> list = new ArrayList<>();
		list.add(map);
		return this.poClient(response, true, JSONUtil.getJson(list));

	}

	/*
	 * 银行卡支付校验
	 */
	@RequestMapping("/app/order/payOrderBank")
	// @Transactional
	public String payOrderBank(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String number = request.getParameter("number"); // 订单号
		String fbankId = request.getParameter("fbankId"); // 银行卡id
		String fverifyCheckCode = request.getParameter("fverifyCheckCode"); // payOrder银行支付返回参数
		String fverifyCode = request.getParameter("fverifyCode"); // 校验码
		String ftype = request.getParameter("ftype"); // (1-订单支付银行卡校验2-充值银行卡校验3-追加货款)

		if (fverifyCheckCode == null || fverifyCheckCode.length() <= 0) {
			return this.poClient(response, false, "银行支付返回参数为空！");
		}

		String msg = String_Custom.parametersEmpty(new String[]{
				fverifyCheckCode, fverifyCode, ftype});
		if (msg.length() > 0)
			return this.poClient(response, false, msg);
		if (String_Custom.noNull(ftype))
			return this.poClient(response, false, "缺少类型参数");

		int fcusid = ServerContext.getUseronline()
				.get(request.getSession().getId().toString()).getFuserId();
		// 根据订单编号 获取订单信息
		String responeData2 = "";
		JSONObject jo2 = new JSONObject();
		switch (ftype) {
			case "1" : {
				
				/*
				 * start判断装车时间是否在一小时内
				 */
				CL_Order order = orderDao.getByNumber(number);
				if (order == null)
					return this.poClient(response, false, "获取不到该订单信息");
				
				Date loadTime = order.getLoadedTime();
				if((loadTime.getTime()-new Date().getTime()) < 60000*60){
					return this.poClient(response, false, "距离装车时间已不足一小时，请重新下单");
				}
				/*
				 * end判断装车时间是否在一小时内
				 */
				
				// 读取是否有相关支付订单生成
				List<CL_FinanceStatement> fstatement = financeStatementDao
						.getforderId(number, 3);
				if (fstatement.size() > 1)
					return this.poClient(response, false, "该订单生成多条支付记录，已作废");

				CL_Bank userBank = bankDao.getBankInfo(
						Integer.parseInt(fbankId), fcusid, 2);
				if (userBank == null)
					return this.poClient(response, false, "获取不到该银行卡");

				BigDecimal actuaAmount = order.getFreight();//支付金额
				
				BigDecimal serveMoney_1 = order.getServe_1() == null?BigDecimal.ZERO:order.getServe_1();//装货费
				BigDecimal serveMoney_2 = order.getServe_2() == null?BigDecimal.ZERO:order.getServe_2();//卸货费
				
				Integer couponsId = order.getCouponsId();//订单
				Integer couponsDetailId = order.getCouponsDetailId();//增值
				if(couponsId != null && couponsId != 0){
					actuaAmount = actuaAmount.subtract(order.getDiscount());
				}
				if (couponsDetailId != null && couponsDetailId != 0) {
					CL_CouponsDetail detail = this.couponsDetailDao.getById(couponsDetailId);
					if(detail.getFaddserviceName().indexOf("装货") != -1){
						actuaAmount = actuaAmount.add(serveMoney_2);
					} else if (detail.getFaddserviceName().indexOf("卸货") != -1) {
						actuaAmount = actuaAmount.add(serveMoney_1);
					}
				}

				LinkedHashMap<String, Object> bankModel = new LinkedHashMap<>();
				bankModel.put("orderNumber", fstatement.get(0).getNumber()); // 订单编号
				bankModel.put("payerId", MD5Util.getMD5String(fcusid + "ylhy")
						.substring(0, 30));// 付款人
				bankModel.put("bankCardBindId", userBank.getNumber()); // 银行卡绑定ID
				bankModel.put("payAmount", actuaAmount + ""); // 支付金额
				bankModel.put("verifyCheckCode", fverifyCheckCode);
				bankModel.put("verifyCode", fverifyCode);
				bankModel.put("notificationURL", "");

				HashMap<String, Object> param = new HashMap<>();
				param.put("fbusinessType", 0);
				param.put("fcusid", fcusid);
				param.put("userDao", userRoleDao);
				param.put("FinanceDao", financeStatementDao);
				responeData2 = PayUtil.payOrderToPaySystem_CheckOutBank(param,
						bankModel);
				jo2 = JSONObject.fromObject(responeData2);

			}

				break;
			case "2" :
			case "3": {

				// 根据订单编号 获取订单信息
				List<CL_FinanceStatement> finance = financeStatementDao
						.getByNumber(number);
				if (finance == null || finance.size() == 0)
					return this.poClient(false, "获取不到该订单信息");
				if (finance.size() > 1)
					return this.poClient(false, "存在");

				CL_Bank userBank = bankDao.getBankInfo(
						Integer.parseInt(fbankId), fcusid, 2);
				if (userBank == null)
					return this.poClient(false, "获取不到该银行卡");

				BigDecimal actuaAmount = finance.get(0).getFamount();

				LinkedHashMap<String, Object> bankModel = new LinkedHashMap<>();
				bankModel.put("orderNumber", number); // 订单编号
				bankModel.put("payerId", MD5Util.getMD5String(fcusid + "ylhy")
						.substring(0, 30));// 付款人
				bankModel.put("bankCardBindId", userBank.getNumber()); // 银行卡绑定ID
				bankModel.put("payAmount", actuaAmount + ""); // 充值金额
				bankModel.put("verifyCheckCode", fverifyCheckCode);
				bankModel.put("verifyCode", fverifyCode);
				bankModel.put("notificationURL", "");

				HashMap<String, Object> param = new HashMap<>();
				param.put("fbusinessType", 0);
				param.put("fcusid", fcusid);
				param.put("userDao", userRoleDao);
				param.put("FinanceDao", financeStatementDao);
				responeData2 = PayUtil.payOrderToPaySystem_CheckOutBank(param,
						bankModel);
				jo2 = JSONObject.fromObject(responeData2);

			}
				break;
//			case "3" : {
//				// 根据订单编号 获取订单信息
//				List<CL_FinanceStatement> finance = financeStatementDao
//						.getByNumber(number);
//				if (finance == null || finance.size() == 0)
//					return this.poClient(false, "获取不到该订单信息");
//				if (finance.size() > 1)
//					return this.poClient(false, "存在");
//
//				CL_Bank userBank = bankDao.getBankInfo(
//						Integer.parseInt(fbankId), fcusid, 2);
//				if (userBank == null)
//					return this.poClient(false, "获取不到该银行卡");
//
//				BigDecimal actuaAmount = finance.get(0).getFamount();
//
//				LinkedHashMap<String, Object> bankModel = new LinkedHashMap<>();
//				bankModel.put("orderNumber", number); // 订单编号
//				bankModel.put("payerId", MD5Util.getMD5String(fcusid + "ylhy")
//						.substring(0, 30));// 付款人
//				bankModel.put("bankCardBindId", userBank.getNumber()); // 银行卡绑定ID
//				bankModel.put("payAmount", actuaAmount + ""); // 充值金额
//				bankModel.put("verifyCheckCode", fverifyCheckCode);
//				bankModel.put("verifyCode", fverifyCode);
//				bankModel.put("notificationURL", "");
//
//				HashMap<String, Object> param = new HashMap<>();
//				param.put("fbusinessType", 0);
//				param.put("fcusid", fcusid);
//				param.put("userDao", userRoleDao);
//				param.put("FinanceDao", financeStatementDao);
//				responeData2 = PayUtil.payOrderToPaySystem_CheckOutBank(param,
//						bankModel);
//				jo2 = JSONObject.fromObject(responeData2);
//			}
//				break;
			default :
				break;
		}

		if ("true".equals(jo2.get("success").toString())) {
			return this.poClient(response, true);
		} else {
			return this.poClient(response, false, jo2.get("msg").toString());
		}

	}

	/*** 支付 */
	@RequestMapping("/app/order/payOrder")
	// @Transactional
	public String payOrder(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String number = request.getParameter("number"); // 订单号
		String fpayType = request.getParameter("fpayType"); // 支付方式(0余额1支付宝2微信3银联4运费到付5月结)
		String fotherType = request.getParameter("fotherType"); // 合并支付 第三方支付方式
		String fpaypassword = request.getParameter("fpaypassword"); // 合并支付
																	// 第三方支付方式
		String fbankId = request.getParameter("fbankId"); // 银行卡id

		// 必不为空判断
		String msg = String_Custom.parametersEmpty(new String[]{number,
				fpayType});
		if (msg.length() != 0)
			return this.poClient(response, false, msg);

		// 根据订单编号 获取订单信息
		CL_Order order = orderDao.getByNumber(number);
		if (order == null)
			return this.poClient(response, false, "获取不到该订单信息");
		
		// 判断装车时间是否在一小时内
		Date loadTime = order.getLoadedTime();
		if ((loadTime.getTime() - new Date().getTime()) < 60000 * 60) {
			return this.poClient(response, false, "距离装车时间已不足一小时，请重新下单");
		}

		// 判断订单是否状态
		if (order.getStatus() != 1)
			return this.poClient(response, false, "该订单不符合支付条件");

		// 判断支付方式格式
		if (String_Custom.noNumber(fpayType))
			return this.poClient(response, false, "支付方式错误");

		int payType = Integer.parseInt(fpayType);

		// 获取用户id
		// Integer fcusid = 2362; // 测试使用
		Integer fcusid = ServerContext.getUseronline()
				.get(request.getSession().getId().toString()).getFuserId();
		System.out.println(fcusid);
		CL_UserRole role = userRoleDao.getById(fcusid);
		// 判断支付密码是否正确
		if (payType == 0) {

			if (fotherType != null && fotherType.equals("3")) {
				// 合并支付 银行卡支付 不校验密码
			} else {
				// 验证支付密码是否正确
				if (String_Custom.noNull(fpaypassword))
					return this.poClient(response, false, "支付密码不可为空!");
				// 判断用户输入支付密码是否正确

				if (!fpaypassword.equals(role.getFpaypassword()))
					return this.poClient(response, false, "支付密码错误!");

			}
		}

		// 判断支付方式是否正确
		String payMethod = "";
		int protocolType = order.getProtocolType(); // 获取订单是否协议订单
		if (protocolType == 0) {
			if (this.orderDetailDao.getByOrderId(order.getId(), 2).size() == 1) {
				payMethod = "0,1,2,3,4";
			} else {
				payMethod = "0,1,2,3";
			}
		} else if (protocolType == 1) {
			if (role.isProtocol()) {
				// 根据卸货地址数判断是否可以支持运费到付 BY CC 2016-05-17 START
				if (this.orderDetailDao.getByOrderId(order.getId(), 2).size() == 1
						&& order.getType() != 3) {
					payMethod = "0,1,2,3,4,5";
				} else {
					payMethod = "0,1,2,3,5";
				}
			} else {
				// 根据卸货地址数判断是否可以支持运费到付 BY CC 2016-05-17 START
				if (this.orderDetailDao.getByOrderId(order.getId(), 2).size() == 1
						&& order.getType() != 3) {
					payMethod = "0,1,2,3,4";
				} else {
					payMethod = "0,1,2,3";
				}

			}
		}

		// 判断支付方式
		if (payMethod.indexOf(fpayType) < 0)
			return this.poClient(response, false, "支付方式不合法！");

		// 获取实际支付金额
		BigDecimal actuaAmount = order.getFtotalFreight();
		BigDecimal payAmount = actuaAmount;
		
		CL_CouponsDetail detail = this.couponsDetailDao.getById(order
				.getCouponsDetailId());//增值
		CL_CouponsDetail detail2 = this.couponsDetailDao.getById(order
				.getCouponsId());//订单
		if(detail2 != null){
			if (detail2.getIsOverdue() == 1)
				return this.poClient(response, false, "该优惠券已过期，请重新下单");
		}
		if (detail != null) {
			if (detail.getIsOverdue() == 1)
				return this.poClient(response, false, "该优惠券已过期，请重新下单");
		}
		
		/*// 减完好运券之后的金额（订单真实支付金额）
		CL_CouponsDetail detail = this.couponsDetailDao.getById(order
				.getCouponsDetailId());//增值
		CL_CouponsDetail detail2 = this.couponsDetailDao.getById(order
				.getCouponsId());//订单
		if(detail2 != null){
			if (detail2.getIsOverdue() == 1)
				return this.poClient(response, false, "该优惠券已过期，请重新下单");
			if(actuaAmount.compareTo(order.getDiscount()) < 0){
				actuaAmount = BigDecimal.ZERO;
			} else {
				actuaAmount = actuaAmount.subtract(order.getDiscount());
			}
		}
		if (detail != null) {
			if (detail.getIsOverdue() == 1)
				return this.poClient(response, false, "该优惠券已过期，请重新下单");
			
			if(detail.getFaddserviceName().indexOf("装货") != -1){
				actuaAmount = actuaAmount.subtract(order.getServe_1());
			} else if (detail.getFaddserviceName().indexOf("卸货") != -1) {
				actuaAmount = actuaAmount.subtract(order.getServe_2());
			}
		}*/

		// 混合支付之后的金额（真实支付金额）
		if (payType == 0 && !String_Custom.noNull(fotherType)) {
			if (String_Custom.noNumber(fotherType))
				return this.poClient(response, false, "第三方支付方式错误!");

			payType = Integer.parseInt(fotherType);

			payAmount = actuaAmount.subtract(role.getFbalance());
		} else {
			payAmount = actuaAmount;
		}

		// 读取相关支付订单生成
		List<CL_FinanceStatement> fstatement = financeStatementDao.getforderId(
				order.getNumber(), payType);
		String FNumber = ""; // 支付明细订单号
		if (fstatement.size() == 0 && (payType == 0 || payType == 5)) {
			// 生成新的支付明细订单
			// CL_UserRole userrole = userRoleDao.getById(fcusid);
			//
			// BigDecimal blance = userrole.getFbalance();
			// if (blance == null)
			// blance = new BigDecimal("0.0");
			FNumber = CacheUtilByCC.getOrderNumber("cl_finance_statement", "L",
					8);

			CL_FinanceStatement statement = new CL_FinanceStatement();
			statement.setFcreateTime(new Date());
			statement.setNumber(FNumber);
			statement.setFrelatedId(order.getId().toString());
			statement.setForderId(order.getNumber());
			statement.setFbusinessType(1); // 下单支付
			if(payType == 4 || payType == 5){
				statement.setFamount(order.getFreight().add(order.getServe_1() == null?BigDecimal.ZERO:order.getServe_1()).add(order.getServe_2() == null?BigDecimal.ZERO:order.getServe_2()));
			} else {
				statement.setFamount(payAmount);
			}
			statement.setFtype(-1);
			statement.setFuserroleId(fcusid);
			statement.setFuserid(fcusid.toString());
			statement.setFpayType(payType);
			statement.setFremark(order.getNumber() + "订单支付");
			statement.setFbalance(new BigDecimal("0"));
//			statement.setFbalance(role.getFbalance());//修改
			statement.setFreight(actuaAmount);

			statement.setFstatus(0);
			if (payType == 5)
				statement.setFstatus(1); // 月结

			financeStatementDao.save(statement);
			fstatement.add(statement);
		} else if (fstatement.size() == 2) {
			// 存在 混合支付和正常支付同时 根据金额进行判断用户操作类型
			// 如果有相同金额则请求FNumber用此 不存在 则报错
			if (fstatement.get(0).getFamount().compareTo(payAmount) == 0)
				FNumber = fstatement.get(0).getNumber();
			else if (fstatement.get(1).getFamount().compareTo(payAmount) == 0) {
				FNumber = fstatement.get(1).getNumber();
				fstatement.set(0, fstatement.get(0));
			}

			else
				return this.poClient(response, false, "该订单已报废，请重新下单！");
		}

		String respPPayURL = ""; // 第三方请求返回链接

		/***************** 对接支付系统所需参数 ******************************/
		LinkedHashMap<String, Object> modellist = new LinkedHashMap<>();
		modellist.put("orderNumber", ""); // 订单编号
		modellist.put("orderCreateTime", String_Custom.getPayOrderCreateTime());
		modellist.put("payerId", MD5Util.getMD5String(fcusid + "ylhy")
				.substring(0, 30));
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

		/******* 保存支付方式 ***********/
		order.setFpayMethod(payType);

		switch (payType) {
			case 0 : {
				orderDao.update(order);
				OrderMsg ordermsg = new OrderMsg();
				ordermsg.setPayState("1"); // 支付成功
				ordermsg.setServiceProvider("0");// 余额支付
				ordermsg.setServiceProviderType("0");// 余额支付;
				ordermsg.setOrder(fstatement.get(0).getNumber());
				int status = financeStatementDao.updateBusinessType(ordermsg);
				if (status == 0)
					return this.poClient(response, false,
							"请确认余额是否充足或订单是否已支付!");
			}
				break;
			case 1 : {
				orderDao.update(order);
				// 支付宝
				modellist.put("serviceProvider", "ALI"); // 支付方式
				modellist.put("serviceProviderType", "SDK"); // 支付方
				param.put("fbusinessType", 1);
				param.put("ftype", -1); // 支出
				param.put("fpayType", 1);

				respPPayURL = PayUtil.payOrderToPaySystem_ZFBWX(param,
						modellist);
			}
				break;
			case 2 : {
				orderDao.update(order);
				// 微信
				modellist.put("serviceProvider", "WX"); // 支付方式
				modellist.put("serviceProviderType", "SDK"); // 支付方
				param.put("fbusinessType", 1);
				param.put("ftype", -1); // 支出
				param.put("fpayType", 2);

				respPPayURL = PayUtil.payOrderToPaySystem_ZFBWX(param,
						modellist);
			}
				break;
			case 3 : {
				orderDao.update(order);
				// 银联
				// 判断银行卡id 是否有效
				if (String_Custom.noNull(fbankId))
					return this.poClient(response, false, "银联支付缺少必要参数");
				if (String_Custom.noNumber(fbankId))
					return this.poClient(response, false, "银联id不正确");

				CL_Bank userBank = bankDao.getBankInfo(
						Integer.parseInt(fbankId), fcusid, 2);
				if (userBank == null)
					return this.poClient(response, false, "获取不到该银行卡");
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

				modellist.put("serviceProvider", "BANK"); // 支付方
				modellist.put("serviceProviderType", "PAB"); // 支付方
				modellist.put("bankCardBindId", userBank.getNumber()); // 支付方
				param.put("fpayType", 3);
				param.put("fbusinessType", 1);
				param.put("ftype", -1); // 支出

				respPPayURL = PayUtil
						.payOrderToPaySystem_Bank(param, modellist);

			}
				break;
			case 4 : {
				// 查询是否拥有好运券 新增逻辑 运费到付月结 不允许使用好运券
				if (detail != null) {//增值
					order.setCouponsDetailId(null);
					order.setSubtract(null);
					detail.setIsUse(0);
					couponsDetailDao.update(detail);
				}
				if (detail2 != null) {//订单
					order.setCouponsId(null);
					order.setDiscount(null);
					detail2.setIsUse(0);
					couponsDetailDao.update(detail2);
				}

				// 运费到付 1.判断用户是否有权限到付 2.更新订单信息
				order.setStatus(2);
				order.setIsFreightCollect(1);
				order.setFtotalFreight(order.getFreight().add(order.getServe_1() == null?BigDecimal.ZERO:order.getServe_1()).add(order.getServe_2() == null?BigDecimal.ZERO:order.getServe_2()));
				orderDao.update(order);
			}
				break;
			case 5 : {
				// 查询是否拥有好运券 新增逻辑 运费到付月结 不允许使用好运券
				if (detail != null) {
					// 拥有好运券 取消好运券使用
					order.setCouponsDetailId(null);
					detail.setIsUse(0);
					couponsDetailDao.update(detail);
					order.setSubtract(null);
				}
				if (detail2 != null) {
					order.setCouponsId(null);
					detail2.setIsUse(0);
					couponsDetailDao.update(detail2);
					order.setDiscount(null);
				}
				// 月结
				order.setStatus(2);
				order.setFtotalFreight(order.getFreight().add(order.getServe_1() == null?BigDecimal.ZERO:order.getServe_1()).add(order.getServe_2() == null?BigDecimal.ZERO:order.getServe_2()));
				
				orderDao.update(order);
			}
				break;
			default :
				break;
		}

		if (payType == 1 || payType == 2 || payType == 3) {
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
			} catch (StringIndexOutOfBoundsException e) {
				return this.poClient(response, false, "支付系统异常");
			}
		}

		if (fstatement.size() == 0)
			fstatement = financeStatementDao.getforderId(order.getNumber(),
					payType);

		switch (payType) {
			case 1 :
				respPPayURL = "{\"number\":\"" + order.getNumber()
						+ "\",\"url\":\""
						+ StringEscapeUtils.escapeJava(respPPayURL) + "\"}";
				break;
			case 2 :
				respPPayURL = "{\"number\":\"" + order.getNumber()
						+ "\",\"url\":" + respPPayURL + "}";
				break;
			case 3 :
				respPPayURL = "{\"number\":\"" + order.getNumber()
						+ "\",\"url\":\"" + respPPayURL + "\"}";
				break;
		}

		return this.poClient(response, true, respPPayURL);
		// // Gson bb=new Gson();
		// //
		// // OrderMsg abc= bb.fromJson(respPPayURL, OrderMsg.class);
		// if (respPPayURL.length() == 0)
		// return this.poClient(response, true);
		// return this.poClient(response, true, "\"" +
		// StringEscapeUtils.escapeJava(respPPayURL) + "\"");

	}

	/*** 更新订单 及 支付订单生成 *///待改
	@RequestMapping("/app/order/saveConfirmOrder")
	@Transactional
	public String saveConfirmOrder(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String number = request.getParameter("number"); // 订单号
		String collectMoney = request.getParameter("collectMoney"); // 代收货款(当增值服务为代收货款需传输此参数)
		String service = request.getParameter("service"); // 增值服务
		String couponID = request.getParameter("couponID"); // 订单优惠券id
		String couponDetailID = request.getParameter("couponDetailID"); // 增值服务优惠券id
		String actualAmount = request.getParameter("actualAmount"); // 实付金额
		String serveMoney1 = request.getParameter("stevedoredFee_1");//装货费
		String serveMoney2 = request.getParameter("stevedoredFee_2");//卸货费
		
		if(serveMoney1 != null && serveMoney1.equals("0.0")){
			serveMoney1 = null;
		}
		if(serveMoney2 != null && serveMoney2.equals("0.0")){
			serveMoney2 = null;
		}
		// 必不为空判断
				String msg = String_Custom.parametersEmpty(new String[]{number,
						actualAmount});
				if (msg.length() != 0)
					return this.poClient(response, false, msg);

				// 根据订单编号 获取订单信息
				CL_Order order = orderDao.getByNumber(number);
				if (order == null)
					return this.poClient(response, false, "获取不到该订单信息");

				// 判断订单是否状态
				if (order.getStatus() != 9)
					return this.poClient(response, false, "该订单已完成确认");
		
		BigDecimal serveMoneys = new BigDecimal(0);// 增值优惠券减的钱
		if (couponDetailID != null) {
			CL_CouponsDetail detail1 = couponsDetailDao.getById(Integer
					.parseInt(couponDetailID));
			// 优惠券合理性判断

			if (detail1.getIsUse() == 1) {
				return this.poClient(response, false, "优惠券已使用");
			} else if (detail1.getIsOverdue() == 1) {
				return this.poClient(response, false, "优惠券已过期");
			} else if (detail1.getStartTime().after(new Date())){//优惠券使用期的尚未开始
				return this.poClient(response, false, "该优惠券尚未可以使用");
			} else {

				List<CL_Order> orderlist = orderDao
						.getBycouponsDetailId(Integer.parseInt(couponDetailID));
				for (CL_Order temporder : orderlist) {
					if (temporder.getStatus() > 0 && temporder.getStatus() != 9) {
						if (temporder.getStatus() == 1) {
							return this.poClient(response, false, "被订单"
									+ temporder.getNumber() + "暂用,请取消该订单!");
						} else if (temporder.getStatus() != 6) {
							return this.poClient(response, false, "已使用");
						}
					} else {
						temporder.setCouponsDetailId(null);
						orderDao.update(temporder);
					}
				}

				CL_Coupons cp = this.couponsDao.getById(detail1.getCouponsId());
				if (cp == null) {
					return this.poClient(response, false, "不存在对应好运券");
				}
			}

			String addserviceName = detail1.getFaddserviceName();
			if((service == null?"":service).indexOf(addserviceName) == -1){
				return this.poClient(response, false, "请选择优惠券对应的增值服务");
			}
				if (addserviceName.indexOf("装货") != -1) {
						serveMoneys = serveMoneys.add(new BigDecimal(serveMoney1));
					order.setSubtract(new BigDecimal(serveMoney1));
				} else if (addserviceName.indexOf("卸货") != -1) {
						serveMoneys = serveMoneys.add(new BigDecimal(serveMoney2));
					order.setSubtract(new BigDecimal(serveMoney2));
				}/* else {
					if (serveMoney1 != null && !"".equals(serveMoney1)
							&& serveMoney2 != null && !"".equals(serveMoney2)) {
						serveMoneys = serveMoneys.add(
								new BigDecimal(serveMoney1)).add(
								new BigDecimal(serveMoney2));
					} else if (serveMoney1 != null && !"".equals(serveMoney1)
							&& (serveMoney2 == null || "".equals(serveMoney2))) {
						serveMoneys = serveMoneys.add(new BigDecimal(
								serveMoney1));
					} else if ((serveMoney1 == null || "".equals(serveMoney1))
							&& serveMoney2 != null && !"".equals(serveMoney2)) {
						serveMoneys = serveMoneys.add(new BigDecimal(
								serveMoney2));
					}
				}*/
			
		}

		BigDecimal couponNumber = new BigDecimal("0");//订单优惠券减少的金额
		if(couponID != null && couponID.length() != 0){

			CL_CouponsDetail detail3 = this.couponsDetailDao.getById(Integer
					.parseInt(couponID));
			if (detail3.getFdiscount() != null) {
				couponNumber = order.getFreight().subtract(order.getFreight().multiply(detail3.getFdiscount().divide(new BigDecimal(100)))).setScale(2, BigDecimal.ROUND_HALF_UP);
				order.setDiscount(couponNumber);// 减的钱 = 运费 - 计算折扣后的钱
			} else {
				couponNumber = detail3.getFsubtract();
				order.setDiscount(couponNumber);
			}
		}
		
		// 增值服务合法性判断
		if (!String_Custom.noNull(service)) {
			// 多个地址不包含代收货款 判断
			if (this.orderDetailDao.getByOrderId(order.getId(), 2).size() != 1) {
				if (service.indexOf("代收货款") == 0)
					return this.poClient(response, false, "多个收获地址不存在代收货款增值服务");
			}
			// 判断如果有代收货款 则判断代收金额
			if (service.indexOf("代收货款") != -1) {
				if(String_Custom.noNull(collectMoney))
					return this.poClient(response, false,"代收金额不能为空");
				if (String_Custom.noFloat(collectMoney))
					return this.poClient(response, false, "缺少代收金额或格式不正确");
				if (order.getProtocolType() == 0) {
					if (new BigDecimal(collectMoney).compareTo(new BigDecimal(
							10000)) > 0) {
						return this.poClient(response, false,
								"临时用车限额不能大于10000元！");
					}
				} else {
					if (new BigDecimal(collectMoney).compareTo(new BigDecimal(
							20000)) > 0) {
						return this.poClient(response, false,
								"临时用车限额不能大于20000元！");
					}
				}
				order.setCollection(1);// 包含代收货款
				order.setPurposeAmount(new BigDecimal(collectMoney));
			}
			
			if (service.indexOf("装货") != -1 && service.indexOf("卸货") != -1) {//待改
				order.setUpload(1);
				//新逻辑
				if(order.getProtocolId() != null){
					CL_Protocol protocol = protocolDao.getById(order.getProtocolId());
					order.setServe_1(protocol.getFadd_service_1());
					order.setServe_2(protocol.getFadd_service_2());
				} else {
					List<CL_Rule> rulist = ruleDao.getBySpec(order.getCarSpec());
					order.setServe_1(rulist.get(0).getFadd_service_1());
					order.setServe_2(rulist.get(0).getFadd_service_2());
				}
			} else if (service.indexOf("装货") != -1 && service.indexOf("卸货") == -1) {
				order.setUpload(1);
				if(order.getProtocolId() != null){
					CL_Protocol protocol = protocolDao.getById(order.getProtocolId());
					order.setServe_1(protocol.getFadd_service_1());
				} else {
					List<CL_Rule> rulist = ruleDao.getBySpec(order.getCarSpec());
					order.setServe_1(rulist.get(0).getFadd_service_1());
				}
			} else if (service.indexOf("装货") == -1 && service.indexOf("卸货") != -1) {
				order.setUpload(1);
				if(order.getProtocolId() != null){
					CL_Protocol protocol = protocolDao.getById(order.getProtocolId());
					order.setServe_2(protocol.getFadd_service_2());
				} else {
					List<CL_Rule> rulist = ruleDao.getBySpec(order.getCarSpec());
					order.setServe_2(rulist.get(0).getFadd_service_2());
				}
			}
			order.setFincrementServe(service);
		}

		
		// 优惠券合理性判断
		if (!String_Custom.noNull(couponID)) {
			if (String_Custom.noNull(couponID))
				return this.poClient(response, false, "优惠券ID不正确");

			// 判断优惠券是否可用
			CL_CouponsDetail detail = this.couponsDetailDao.getById(Integer
					.parseInt(couponID));
			if (detail.getIsUse() == 1) {
				return this.poClient(response, false, "优惠券已使用");
			} else if (detail.getIsOverdue() == 1) {
				return this.poClient(response, false, "优惠券已过期");
			} else if (detail.getStartTime().after(new Date())){//优惠券使用期的尚未开始
				return this.poClient(response, false, "该优惠券尚未可以使用");
			} else {
				
				// 20160906 cd 新增待付款订单校验后的好运券使用控制;
				List<CL_Order> orderlist = orderDao
						.getBycouponsDetailId(Integer.parseInt(couponID));
				for (CL_Order temporder : orderlist) {
					if (temporder.getStatus() > 0 && temporder.getStatus() != 9) {
						if (temporder.getStatus() == 1)
						{
							return this.poClient(response, false, "被订单"+temporder.getNumber()+"暂用,请取消该订单!");
						} /*else {
							return this.poClient(response, false, "已使用");
						}*/
						else if (temporder.getStatus() != 6){
							return this.poClient(response, false, "已使用");
						}
					} else {
						//还原好运券为未使用
//						detail.setIsUse(0);
//						couponsDetailDao.update(detail);
						temporder.setCouponsDetailId(null);
						orderDao.update(temporder);
					}
				}
				// 20160906 cd 新增待付款订单校验后的好运券使用控制;

				CL_Coupons cp = this.couponsDao.getById(detail.getCouponsId());
				if (cp != null) {
					// 增加提前记录订单好运券
//					if (request.getParameter("orderId") != null
//							&& !"".equals(request.getParameter("orderId"))
//							&& !"null".equals(request.getParameter("orderId"))) {
//						int orderid = new Integer(
//								request.getParameter("orderId"));
//						orderDao.updatecouponsdetailByOrderId(
//								Integer.parseInt(couponID), orderid);
//					}
				} else {
					return this.poClient(response, false, "不存在对应好运券");
				}
			}

		}
		
		//新逻辑，允许用过优惠券后，支付金额为0---------------------begin
		if(String_Custom.noNull(couponID)){
			if(new BigDecimal(actualAmount).compareTo(BigDecimal.ZERO) <= 0){
				return this.poClient(response, false, "支付金额异常，请联系客服!");
			}
		} else {
			if(new BigDecimal(actualAmount).compareTo(BigDecimal.ZERO) < 0){
				return this.poClient(response, false, "支付金额异常，请联系客服!");
			}
		}
		//新逻辑，允许用过优惠券后，支付金额为0---------------------end
		
		// 判断实际支付金额是否正确
		//以下代码，哪位仁兄接手，千万别问丁爷这是谁写的，要脸 = =。
		if (new BigDecimal(actualAmount).compareTo(BigDecimal.ZERO) > 0 && order.getFreight().compareTo(couponNumber) >= 0) {//正常订单才走以下逻辑，因为好运券将实付金额减成0的不走

			if ((couponID == null || couponID.length() == 0) && (couponDetailID == null || couponDetailID.length() == 0)) {
				// 没有使用优惠券
				if (order.getFreight().add(serveMoney1 == null?BigDecimal.ZERO:new BigDecimal(serveMoney1)).add(serveMoney2 == null?BigDecimal.ZERO:new BigDecimal(serveMoney2)).compareTo(new BigDecimal(actualAmount)) != 0)
					return this.poClient(response, false, "实际支付金额错误1!");

			} else {//fuck 第一次怀疑自己数学太他妈差了，写不出通用的，智能强行if一波了 =.=！（单装卸有增值优惠券）
				if(((serveMoney1 == null && serveMoney2 != null) || (serveMoney2 == null && serveMoney1 != null)) && serveMoneys.compareTo(BigDecimal.ZERO) == 0 && couponID == null){
					if (order.getFreight().compareTo(new BigDecimal(actualAmount).add(couponNumber).add(serveMoneys)) != 0)
						return this.poClient(response, false, "实际支付金额错误2!");
				} else if (((serveMoney1 == null && serveMoney2 != null) || (serveMoney2 == null && serveMoney1 != null)) && serveMoneys.compareTo(BigDecimal.ZERO) == 0 && couponID != null && couponDetailID != null) {
					if (order.getFreight().compareTo(new BigDecimal(actualAmount).add(couponNumber).add(serveMoneys).subtract(serveMoney1 == null?BigDecimal.ZERO:new BigDecimal(serveMoney1)).subtract(serveMoney2 == null?BigDecimal.ZERO:new BigDecimal(serveMoney2))) != 0 && couponNumber.compareTo(order.getFreight()) < 0)
						return this.poClient(response, false, "实际支付金额错误3!");
				}  else if (((serveMoney1 == null && serveMoney2 != null) || (serveMoney2 == null && serveMoney1 != null)) && serveMoneys.compareTo(BigDecimal.ZERO) == 0 && couponID != null && couponDetailID == null) {
					if (order.getFreight().compareTo(new BigDecimal(actualAmount).add(couponNumber).add(serveMoneys).subtract(serveMoney1 == null?BigDecimal.ZERO:new BigDecimal(serveMoney1)).subtract(serveMoney2 == null?BigDecimal.ZERO:new BigDecimal(serveMoney2))) != 0)
						return this.poClient(response, false, "实际支付金额错误4!");
				} else {//其他
					if(order.getFreight().compareTo(couponNumber) < 0){
						if(new BigDecimal(actualAmount).compareTo((serveMoney1 == null?BigDecimal.ZERO:new BigDecimal(serveMoney1)).add(serveMoney2 == null?BigDecimal.ZERO:new BigDecimal(serveMoney2)).subtract(serveMoneys)) != 0){
							return this.poClient(response, false, "实际支付金额错误5!");
						}
					} else {
						if (order.getFreight().compareTo(new BigDecimal(actualAmount).add(couponNumber).add(serveMoneys).subtract(serveMoney1 == null?BigDecimal.ZERO:new BigDecimal(serveMoney1)).subtract(serveMoney2 == null?BigDecimal.ZERO:new BigDecimal(serveMoney2))) != 0)
							return this.poClient(response, false, "实际支付金额错误6!");
					}
				}
			}
		} else {
			/*if(((serveMoney1 == null && serveMoney2 != null) || (serveMoney2 == null && serveMoney1 != null)) && serveMoneys.compareTo(BigDecimal.ZERO) == 0){
				if (order.getFreight().compareTo(new BigDecimal(actualAmount).add(couponNumber).add(serveMoneys)) != 0)
					return this.poClient(response, false, "实际支付金额错误!");
			} else {//其他
				if(order.getFreight().compareTo(couponNumber) < 0){
					if(new BigDecimal(actualAmount).compareTo((serveMoney1 == null?BigDecimal.ZERO:new BigDecimal(serveMoney1)).add(serveMoney2 == null?BigDecimal.ZERO:new BigDecimal(serveMoney2)).subtract(serveMoneys)) != 0){
						return this.poClient(response, false, "实际支付金额错误!");
					}
				}
				if (order.getFreight().compareTo(new BigDecimal(actualAmount).add(couponNumber).add(serveMoneys).subtract(serveMoney1 == null?BigDecimal.ZERO:new BigDecimal(serveMoney1)).subtract(serveMoney2 == null?BigDecimal.ZERO:new BigDecimal(serveMoney2))) != 0)
					return this.poClient(response, false, "实际支付金额错误!");
			}*/
			if(new BigDecimal(actualAmount).compareTo((serveMoney1 == null?BigDecimal.ZERO:new BigDecimal(serveMoney1)).add(serveMoney2 == null?BigDecimal.ZERO:new BigDecimal(serveMoney2)).subtract(serveMoneys)) != 0){
				return this.poClient(response, false, "实际支付金额错误7!");
			}
		}
		order.setFtotalFreight(new BigDecimal(actualAmount));//实际付费已通过后台校验比对，没驳回则赋值

		// 1.更新订单状态
		// 保存订单增值服务;
		order.setStatus(1);// 订单确认 --代付款
		if (couponDetailID != null) {
			CL_CouponsDetail detail1 = couponsDetailDao.getById(Integer.parseInt(couponDetailID));
			detail1.setIsUse(1);
			couponsDetailDao.update(detail1);
			order.setCouponsDetailId(Integer.parseInt(couponDetailID));
		}
		if(couponID != null){
			CL_CouponsDetail detail = this.couponsDetailDao.getById(Integer.parseInt(couponID));
			// 保存优惠券使用状态更新状态
			detail.setIsUse(1);
			couponsDetailDao.update(detail);
			order.setCouponsId(Integer.parseInt(couponID));
		}
		int orderUpdateSuccess = orderDao.update(order);
		if (orderUpdateSuccess == 0)
			return this.poClient(response, false, "更新订单状态失败");
		//只在全部成功时更新优惠券的状态
		// 2.插入支付明细
		// 获取当前用户余额
		// CL_UserRole role = userRoleDao.getById(userroleid);
		// if (role == null)
		// return this.poClient(response, false, "未获取到用户");
		//
		// role.getFbalance().toString();

		// String financeNumber =
		// CacheUtilByCC.getOrderNumber("cl_finance_statement", "L", 8);
		// CL_FinanceStatement statement = new CL_FinanceStatement();
		// statement.setFcreateTime(new Date());
		// statement.setNumber(financeNumber);
		// statement.setFrelatedId(order.getId().toString());
		// statement.setForderId(order.getNumber());
		// statement.setFbusinessType(4);
		// statement.setFamount(new BigDecimal(actualAmount));
		// statement.setFtype(1);
		// statement.setFuserroleId(userroleid);
		// statement.setFuserid(vmiid);
		// statement.setFremark("订单支付");
		// statement.setFbalance(new BigDecimal("0"));
		// statement.setFstatus(0);
		//
		// int success = iFinanceStatementDao.save(statement);
		//
		// if (success == 0)
		// return this.poClient(response, false, "生成支付订单失败！");

		
		HashMap<String, String> map = new HashMap<>();
		map.put("number", number);
		map.put("couponNumber", actualAmount);

		return this.poClient(response, true, JSONUtil.getJson(map));
	}

	/*** 新增订单 */
	@RequestMapping("/app/order/saveOrder")
	@Transactional
	public String saveOrder(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		Integer userroleId, orderType = null, specId = null, fopint = 1;
		String orderString, addressDeliverString, addressReceiptString, carTypeId = null, otherId = null, fremark = null, versionCode = null, fsystem = "";
		BigDecimal vod = new BigDecimal(0);
		BigDecimal weh = new BigDecimal(0);
		BigDecimal vodtoWeh = new BigDecimal(0);
		HashMap<String, Object> map = new HashMap<String, Object>();

		Util_UserOnline useronline = ServerContext.getUseronline().get(request.getSession().getId());
		if(useronline == null ){
			map.put("success", "false");
			map.put("msg", "登录超时,请重新登录！");
			return writeAjaxResponse(response, JSONUtil.getJson(map));
		}
		userroleId = useronline.getFuserId();
		// 停止接单开始
		/*SimpleDateFormat formatTemp = new SimpleDateFormat("yyyy-MM-dd");
		CL_Refuse refuse = refuseDao.getById(1);
		Date startTime = refuse.getFstart_time();// 停止接单开始时间
		Date endTime = refuse.getFend_time();// 停止接单结束时间
		if (startTime != null) {
			String start = formatTemp.format(startTime);
			String end = formatTemp.format(endTime);
			Date now = new Date();// 当前时间
			if (!(refuse.getFkey() != null
					&& refuse.getFkey().equals("allowPerson")
					&& refuse.getFvalues() != null && !"".equals(refuse.getFvalues()) && refuse.getFvalues()
					.contains(request.getParameter("id")))) {
				if (startTime.before(now) && endTime.after(now)) {
					map.put("success", "false");
					map.put("msg", start + "至" + end + "期间停止接单!");
					return writeAjaxResponse(response, JSONUtil.getJson(map));
				}
			}
		}*/
		HashMap<String, Object> tm = null;
		try {
			tm = RedisUtil.IsSystemDefend(0,userroleId);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		if(tm != null){
			return writeAjaxResponse(response, JSONUtil.getJson(tm));
		}
		// 停止接单结束

		/*if (request.getParameter("id") == null
				|| "".equals(request.getParameter("id"))) {
			map.put("success", "false");
			map.put("msg", "请先登录！");
			return writeAjaxResponse(response, JSONUtil.getJson(map));
		} else {
			userroleId = Integer.parseInt(request.getParameter("id"));
		}*/

		// 判断用户是否欠费
		BigDecimal balance = userRoleDao.getById(userroleId).getFbalance();

		if (balance.compareTo(new BigDecimal(0)) == -1) {
			return this.poClient(response, false, "请先结清您欠的费用！");
		}

		// APP强制更新；
		/*HashMap<String, Util_UserOnline> useronline = ServerContext
				.getUseronline();
		if (request.getSession().getId() == null
				|| request.getSession().getId().equals("")) {
			map.put("success", "false");
			map.put("msg", "登录超时！");
			return writeAjaxResponse(response, JSONUtil.getJson(map));
		}
		versionCode = useronline.get(request.getSession().getId().toString())
				.getVersionCode();*/
		
		versionCode = useronline.getVersionCode();
		if (versionCode == null || "".equals(versionCode)) {
			map.put("success", "false");
			map.put("msg", "请重新登录更新APP后再下单，谢谢合作！");
			return writeAjaxResponse(response, JSONUtil.getJson(map));
		} else {
//			BigDecimal bVersionCode = new BigDecimal(versionCode);
			/*fsystem = useronline.get(request.getSession().getId().toString())
					.getFsystem();*/
			fsystem = useronline.getFsystem();
			int ftype = 0 ;//0 安卓 1 ios
			if(fsystem!=null && fsystem.startsWith("ios")){
				ftype =  1 ;
			}
			
			if (CacheUtilByCC.isOldVersion(versionCode, ftype)) {
				map.put("success", "false");
				if (fsystem.startsWith("ios")) {
					map.put("msg", "请去苹果商店下载更新，谢谢合作！");
				} else {
					map.put("msg", "请重新登录更新APP后再下单，谢谢合作！");
				}
				return writeAjaxResponse(response, JSONUtil.getJson(map));
			}
		}

		/**
		 * 20160530业务再次确认个人认证跳过允许下单; 20160803因国家规定货运APP必须实名，再次控制未认证不许下单;
		 * 新增下单前认证审核通过判断，开始;
		 */
		CL_Order order = new CL_Order();
		if (!fsystem.startsWith("ios")) {
			order.setFordesource("andriod_" + versionCode);
		} else {
			order.setFordesource(fsystem + "_" + versionCode);
		}
		CL_UserRole userinfo = userRoleDao.getById(userroleId);
		if (userinfo.isSub()) {
			userroleId = userinfo.getFparentid();
		} else {
			if (userinfo.isPassIdentify()) {
				order.setIdentifyType(3);
			} else {
				List<CL_Identification> identifications = identificationDao
						.getStatusByUserRoleId(userroleId);
				if (identifications.size() > 0) {
					map.put("success", "true");
					// order.setIdentifyType());//认证状态值：3正常下单;0 跳到认证界面;1
					// 直接提示拨打客服电话通过认证; 2 跳到认证结果界面
					map.put("data", "{\"identifyType\":\""
							+ identifications.get(0).getStatus() + "\"}");
					return writeAjaxResponse(response, JSONUtil.getJson(map));
				} else {
					return writeAjaxResponse(response, "请先认证啊喂~");
				}

			}
			// 新增下单前认证审核通过判断，结束;
		}

		if (request.getParameter("fremark") == null
				|| "".equals(request.getParameter("fremark"))) {
			fremark = request.getParameter("fremark");
		} else {
			fremark = request.getParameter("fremark").replaceAll(
					"[\\ud800\\udc00-\\udbff\\udfff\\ud800-\\udfff]", "");
		}
		// getRequestParameter(request, response, map,
		// "无法获知订单类型！","orderType",orderType);
		if (request.getParameter("orderType") == null
				|| "".equals(request.getParameter("orderType"))) {
			map.put("success", "false");
			map.put("msg", "无法获知订单类型！");
			return writeAjaxResponse(response, JSONUtil.getJson(map));
		} else {
			orderType = Integer.parseInt(request.getParameter("orderType"));
		}
		if (request.getParameter("fopint") == null
				|| "".equals(request.getParameter("fopint"))) {
			fopint = 1;
		} else {
			fopint = Integer.valueOf(request.getParameter("fopint").toString());
		}

		if (orderType == 1) {
			if (request.getParameter("specId") == null
					|| "".equals(request.getParameter("specId"))) {
				map.put("success", "false");
				map.put("msg", "请选择车厢！");
				return writeAjaxResponse(response, JSONUtil.getJson(map));
			} else {
				specId = Integer.parseInt(request.getParameter("specId"));
			}
			if (request.getParameter("carTypeId") == null
					|| "".equals(request.getParameter("carTypeId"))) {
				map.put("success", "false");
				map.put("msg", "请选择车型！");
				return writeAjaxResponse(response, JSONUtil.getJson(map));
			} else {
				carTypeId = request.getParameter("carTypeId");
			}
			otherId = request.getParameter("otherId");
		}
		if (request.getParameter("order") == null
				|| "".equals(request.getParameter("order"))) {
			map.put("success", "false");
			map.put("msg", "请先录入订单信息！");
			return writeAjaxResponse(response, JSONUtil.getJson(map));
		} else {
			orderString = request.getParameter("order");
		}
		if (request.getParameter("addressDeliver") == null
				|| "".equals(request.getParameter("addressDeliver"))) {
			map.put("success", "false");
			map.put("msg", "请先选择发货地址！");
			return writeAjaxResponse(response, JSONUtil.getJson(map));
		} else {
			addressDeliverString = request.getParameter("addressDeliver");
		}

		JSONArray jsonsorder = JSONArray.fromObject(orderString);
		// CL_Order order =new CL_Order();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		order.setType(jsonsorder.getJSONObject(0).getInt("type"));// 订单类型

		if (order.getType() == 2) {
			map.put("success", "false");
			map.put("msg", "系统已经停止零单服务！");
			return writeAjaxResponse(response, JSONUtil.getJson(map));
		}

		order.setGoodsTypeId(jsonsorder.getJSONObject(0).get("goodsTypeId")
				.toString());// 货物类型ID
		order.setGoodsTypeName(jsonsorder.getJSONObject(0).get("goodsTypeName")
				.toString());// 货物类型名称
		if (jsonsorder.getJSONObject(0).get("volume") != null
				&& !"".equals(jsonsorder.getJSONObject(0).get("volume")
						.toString())) {
			if (jsonsorder.getJSONObject(0).get("volume").toString().length() > 9) {
				map.put("success", "false");
				map.put("msg", "体积过大！");
				return writeAjaxResponse(response, JSONUtil.getJson(map));
			}
			vod = new BigDecimal(jsonsorder.getJSONObject(0).get("volume")
					.toString());// 体积
			vodtoWeh = vod.multiply(new BigDecimal(200));
		}
		order.setVolume(vod);// 体积
		if (jsonsorder.getJSONObject(0).get("weight") != null
				&& !"".equals(jsonsorder.getJSONObject(0).get("weight")
						.toString())) {
			if (jsonsorder.getJSONObject(0).get("weight").toString().length() > 9) {
				map.put("success", "false");
				map.put("msg", "重量过大！");
				return writeAjaxResponse(response, JSONUtil.getJson(map));
			}
			weh = new BigDecimal(jsonsorder.getJSONObject(0).get("weight")
					.toString());// 重量
		}
		order.setWeight(weh);
		// weh=weh.multiply(new BigDecimal(1000)); 重量 *1000按公斤算
		if (jsonsorder.getJSONObject(0).get("length").toString().length() > 9) {
			map.put("success", "false");
			map.put("msg", "长度过大！");
			return writeAjaxResponse(response, JSONUtil.getJson(map));
		}
		order.setLength(new BigDecimal(jsonsorder.getJSONObject(0)
				.get("length").toString()));// 长度
		order.setCreateTime(new Date());
		try {
			Date loadTime = format.parse(jsonsorder.getJSONObject(0)
					.get("loadedTime").toString());
			long time = loadTime.getTime() - order.getCreateTime().getTime();
			long ss = time - 60 * 60000;
			if (ss >= 0) {
				order.setLoadedTime(format.parse(jsonsorder.getJSONObject(0)
						.get("loadedTime").toString()));
			} else {
				map.put("success", "false");
				map.put("msg", "装车时间必须大于创建时间一小时！");
				return writeAjaxResponse(response, JSONUtil.getJson(map));
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		if (jsonsorder.getJSONObject(0).get("mileage").toString() != null
				&& "".equals(jsonsorder.getJSONObject(0).get("mileage")
						.toString())) {
			map.put("success", "false");
			map.put("msg", "请输入里程数！");
			return writeAjaxResponse(response, JSONUtil.getJson(map));
		} else {
			order.setMileage(new BigDecimal(jsonsorder.getJSONObject(0)
					.get("mileage").toString()).setScale(1,
					BigDecimal.ROUND_HALF_UP));// 里程
		}

		if (request.getParameter("addressReceipt") == null
				|| "".equals(request.getParameter("addressReceipt"))
				|| request.getParameter("addressReceipt").equals("[]")) {
			map.put("success", "false");
			map.put("msg", "请先选择收货地址！");
			return writeAjaxResponse(response, JSONUtil.getJson(map));
		} else {
			addressReceiptString = request.getParameter("addressReceipt");

			// lancher修改，增加下单前判断地址是否依旧有效功能------------begin-------------------
			JSONArray jsAddressDel = JSONArray.fromObject(addressDeliverString);
			JSONArray jsAddressRec = JSONArray.fromObject(addressReceiptString);
			JSONObject jdel = jsAddressDel.getJSONObject(0);
			// 取出发货地址Id
			if (jdel.get("id") != null && !"".equals(jdel.get("id"))) {
				int addressDelId = Integer.parseInt(jdel.get("id").toString());
				// 加个if查询数据库中有无地址
				if (addressdao.isExist(addressDelId) > 0) {
					for (int i = 0; i < jsAddressRec.size(); i++) {
						JSONObject jrec = jsAddressRec.getJSONObject(i);
						// 取出收货地址ID
						int addressRecId = Integer.parseInt(jrec.get("id")
								.toString());
						// 判断地址在数据库中是否被删
						if (addressdao.isExist(addressRecId) > 0) {
							// 存在就是有效地址，继续循环
						} else {
							map.put("success", "false");
							map.put("msg", "地址已失效，请重新录入！");
							return writeAjaxResponse(response,
									JSONUtil.getJson(map));
						}
					}
				} else {
					map.put("success", "false");
					map.put("msg", "地址已失效，请重新录入！");
					return writeAjaxResponse(response, JSONUtil.getJson(map));
				};
			}

			// lancher修改，增加下单前判断地址是否依旧有效功能------------end-------------------

			// 判断收货地址是否为多条，是则跳过--------BEGIN--------BY LANCHER
			if (jsAddressRec.size() == 1) {
				String vmi_user_fid = ServerContext.getUseronline()
						.get(request.getSession().getId()).getFuserid();
				CL_UserRole user = userRoleDao.getByVmiUserFidAndRoleId(
						vmi_user_fid, 1);
				Integer fcustomer_id = user.getId();
				Integer faddressRec_id = Integer.parseInt(jsAddressRec
						.getJSONObject(0).get("id").toString());
				Integer faddressDel_id = Integer.parseInt(jsAddressDel
						.getJSONObject(0).get("id").toString());
				BigDecimal fmileage = distanceDao.getMileage(fcustomer_id,
						faddressDel_id, faddressRec_id);
				if (fmileage != null && !"".equals(fmileage)) {
					order.setMileage(fmileage);
				}
			}
			// 判断收货地址是否为多条，是则跳过--------END--------BY LANCHER
		}

		if (userinfo.isSub()) {
			order.setCreator(userroleId);// 创建人
			order.setSubUserId(userinfo.getId());// 创建人
			order.setSubName(userinfo.getVmiUserName());
			order.setOperator(userinfo.getId());// 操作人ID

		} else {
			order.setCreator(userroleId);// 创建人
			order.setOperator(userroleId);// 操作人ID
		}

		order.setFremark(fremark);// 备注
		order.setFopint(fopint);// 点数
		order.setProtocolType(0);// 非协议

		// ============================================计算运费=============开始=====================================//
		if (order.getType() == 1) {// 整车计算运费
			CL_Rule rule = this.ruleDao.getZhengche(specId);
			if (rule != null) {
				BigDecimal opintprice = new BigDecimal(0);//超出点数计费
				Integer foutopint = 0;
				if (fopint > rule.getFopint()) {
					foutopint = fopint - rule.getFopint();
					opintprice = rule.getOutfopint().multiply(
							new BigDecimal(foutopint));
				}
				opintprice = rule.getOutfopint().multiply(
						new BigDecimal(foutopint));
				
				if (order.getMileage().compareTo(rule.getStartKilometre()) == 1) {
					BigDecimal km = order.getMileage().subtract(rule.getStartKilometre());// 超出公里
					int s = 0;
					if(rule.getCarSpecId() == 3){//4.2米
						if (km.subtract(new BigDecimal(15)).compareTo(BigDecimal.ZERO) <= 0) {// 5-20公里
							s = 1;
						} else if (km.subtract(new BigDecimal(45)).compareTo(BigDecimal.ZERO) <= 0) {// 20-50公里
							s = 2;
						} else {// 50公里以上
							s = 3;
						}
					} else if (rule.getCarSpecId() == 5) {//6.8米
						if (km.subtract(new BigDecimal(30)).compareTo(BigDecimal.ZERO) <= 0) {// 20-50公里
							s = 1;
						} else {// 50公里以上
							s = 2;
						}
					} else {
						map.put("success", "false");
						map.put("msg", "无效车型！");
						return writeAjaxResponse(response,JSONUtil.getJson(map));
					}
					
					BigDecimal startP = rule.getStartPrice();//起步价
					BigDecimal out_5_20_K = rule.getKilometre5_20_price();//5-20公里单价(包含20公里)
					BigDecimal out_20_50_K = rule.getKilometre20_50_price();//20-50公里单价(包含50公里)
					BigDecimal out_50_K = rule.getKilometre50_price();//50公里单价
					BigDecimal fee = BigDecimal.ZERO;
					
						if(rule.getCarSpecId() == 3){
							switch (s) {
							case 1://5-20
								fee = startP.add(km.multiply(out_5_20_K)).add(opintprice).setScale(2, BigDecimal.ROUND_HALF_UP);
								break;
							case 2://20-50
								fee = startP.add(new BigDecimal(15).multiply(out_5_20_K)).add(order.getMileage().subtract(new BigDecimal(20)).multiply(out_20_50_K)).add(opintprice).setScale(2, BigDecimal.ROUND_HALF_UP);
								break;
							case 3://50-
								fee = startP.add(new BigDecimal(15).multiply(out_5_20_K)).add(new BigDecimal(30).multiply(out_20_50_K)).add(order.getMileage().subtract(new BigDecimal(50)).multiply(out_50_K)).add(opintprice).setScale(2, BigDecimal.ROUND_HALF_UP);
								break;
								
							default:
								fee = new BigDecimal(-1);//坏账
							}
						} else {
							switch (s) {
							case 1://20-50
								fee = startP.add(km.multiply(out_20_50_K)).add(opintprice).setScale(2, BigDecimal.ROUND_HALF_UP);
								break;
							case 2://50-
								fee = startP.add(new BigDecimal(30).multiply(out_20_50_K)).add(order.getMileage().subtract(new BigDecimal(50)).multiply(out_50_K)).add(opintprice).setScale(2, BigDecimal.ROUND_HALF_UP);
								break;
								
							default:
								fee = new BigDecimal(-1);//坏账
							}
						}
						
					if(fee == new BigDecimal(-1)){
						map.put("success", "false");
						map.put("msg", "坏账，请联系客服！");
						return writeAjaxResponse(response,JSONUtil.getJson(map));
					}
					order.setFreight(fee);
					order.setForiginfreight(order.getFreight());
					order.setFtotalFreight(order.getFreight());
					
				} else {
						order.setFreight(rule.getStartPrice().add(opintprice)
								.setScale(2, BigDecimal.ROUND_HALF_UP));
						order.setForiginfreight(order.getFreight());
						order.setFtotalFreight(order.getFreight());
				}
				
				/*// 实际运输距离大于规则起步公里数:运费=(规则起步价+(实际距离-规则起步公里数)×规则公里单价)
				if (order.getMileage().compareTo(rule.getStartKilometre()) == 1) {
					BigDecimal km = order.getMileage().subtract(
							rule.getStartKilometre());// 减法
					BigDecimal fee1 = km.multiply(rule.getKilometrePrice());// 乘法
					if (serve != null) {
						if (serve.indexOf("装卸") != -1) {
							order.setFreight(rule.getStartPrice().add(fee1)
									.add(opintprice)
									.add(rule.getFadd_service())
									.setScale(2, BigDecimal.ROUND_HALF_UP));// 加法
							order.setForiginfreight(order.getFreight());// 加法
						} else {
							order.setFreight(rule.getStartPrice().add(fee1)
									.add(opintprice)
									.setScale(2, BigDecimal.ROUND_HALF_UP));// 加法
							order.setForiginfreight(order.getFreight());// 加法
						}
					} else {
						order.setFreight(rule.getStartPrice().add(fee1)
								.add(opintprice)
								.setScale(2, BigDecimal.ROUND_HALF_UP));// 加法
						order.setForiginfreight(order.getFreight());// 加法
					}
					// 实际运输距离小于/等于规则起步公里数:运费=规则起步价
				} else if (order.getMileage().compareTo(
						rule.getStartKilometre()) == -1
						|| order.getMileage().compareTo(
								rule.getStartKilometre()) == 0) {
					if (serve != null) {
						if (serve.indexOf("装卸") != -1) {
							order.setFreight(rule.getStartPrice()
									.add(opintprice)
									.add(rule.getFadd_service())
									.setScale(2, BigDecimal.ROUND_HALF_UP));
							order.setForiginfreight(order.getFreight());
						} else {
							order.setFreight(rule.getStartPrice()
									.add(opintprice)
									.setScale(2, BigDecimal.ROUND_HALF_UP));
							order.setForiginfreight(order.getFreight());
						}
					} else {
						order.setFreight(rule.getStartPrice().add(opintprice)
								.setScale(2, BigDecimal.ROUND_HALF_UP));
						order.setForiginfreight(order.getFreight());
					}
				}*/
			} else {
				map.put("success", "false");
				map.put("msg", "所选车厢,规则遗失,请联系客服");
				return writeAjaxResponse(response, JSONUtil.getJson(map));
			}
		} else if (order.getType() == 2) {
			map.put("success", "false");
			map.put("msg", "暂不支持零担,请联系客服");
			return writeAjaxResponse(response, JSONUtil.getJson(map));
			/*// 零担计算运费
			// CL_Rule ruleVol =this.ruleDao.getLingdanForVolume();
			// ruleWeh =this.ruleDao.getLingdanForWeight();
			// BigDecimal opintprice=new BigDecimal(0);
			// BigDecimal Wehopintprice=new BigDecimal(0);
			// Integer foutopint=0;
			// if(fopint>ruleVol.getFopint()){
			// foutopint=fopint-ruleVol.getFopint();
			// opintprice=ruleVol.getOutfopint().multiply(new
			// BigDecimal(foutopint));
			// }
			// if(fopint>ruleWeh.getFopint()){
			// foutopint=fopint-ruleWeh.getFopint();
			// Wehopintprice=ruleWeh.getOutfopint().multiply(new
			// BigDecimal(foutopint));
			//
			// }
			// Integer foutopint=fopint+1-ruleVol.getFopint();
			// BigDecimal opintprice=ruleVol.getOutfopint().multiply(new
			// BigDecimal(foutopint));
			// if(ruleVol!=null && ruleWeh!=null){
			// BigDecimal volfee =
			// ruleVol.getStartPrice().add(opintprice).add(order.getVolume().subtract(new
			// BigDecimal("1")).multiply(ruleVol.getOutPrice()));
			// BigDecimal wehfee =
			// ruleWeh.getStartPrice().add(Wehopintprice).add(order.getWeight().subtract(new
			// BigDecimal("1")).multiply(ruleWeh.getOutPrice()));
			// //按体积计算运费大于按重量计算的运费
			// if(order.getVolume().compareTo(new BigDecimal("1"))==-1 &&
			// order.getWeight().compareTo(new BigDecimal("1"))==-1){
			// if(ruleVol.getStartPrice().compareTo(ruleWeh.getStartPrice())==1){
			// order.setFreight(ruleVol.getStartPrice().add(opintprice).setScale(1,
			// BigDecimal.ROUND_HALF_UP));
			// }else{
			// order.setFreight(ruleWeh.getStartPrice().add(Wehopintprice).setScale(1,
			// BigDecimal.ROUND_HALF_UP));
			// }
			// }else if(volfee.compareTo(wehfee)==1){
			// order.setCollection(2);
			// order.setFreight(volfee.setScale(1, BigDecimal.ROUND_HALF_UP));
			// }else{
			// order.setCollection(1);
			// order.setFreight(wehfee.setScale(1, BigDecimal.ROUND_HALF_UP));
			// }
			// }else{
			// map.put("success", "false");
			// map.put("msg","零担规则遗失,请联系客服");
			// return writeAjaxResponse(response, JSONUtil.getJson(map));
			// }
			// 2016-6-18 新需求 按重量（公斤）区间来算 价格 BY twr start
			CL_Rule ruleV = this.ruleDao.getLingdanForVolume();
			CL_Rule ruleW = this.ruleDao.getLingdanForWeight();
			BigDecimal finalWeight = new BigDecimal(0);
			if (vodtoWeh.compareTo(weh) <= 0) {
				finalWeight = weh;
				order.setCollection(1); // 按重量计算 为1
			} else if (vodtoWeh.compareTo(weh) == 1) {
				finalWeight = vodtoWeh;
				order.setCollection(2); // 按体积计算 为2

			}
			// finalWeight=finalWeight.subtract(new BigDecimal(75));//保底公斤
			if (finalWeight.compareTo(new BigDecimal(75)) <= 0) {// 保底公斤
				if (serve != null) {
					if (serve.indexOf("装卸") != -1) {
						order.setFreight(new BigDecimal(30).add(ruleW
								.getFadd_service()));
						order.setForiginfreight(order.getFreight());
					} else {
						order.setFreight(new BigDecimal(30));
						order.setForiginfreight(order.getFreight());
					}
				} else {
					order.setFreight(new BigDecimal(30));
					order.setForiginfreight(order.getFreight());
				}
			} else {
				if (serve != null) {
					if (serve.indexOf("装卸") != -1) {
						if (finalWeight.compareTo(new BigDecimal(500)) <= 0) {
							order.setFreight(finalWeight.multiply(
									new BigDecimal("0.4")).add(
									ruleW.getFadd_service()));// 0.4
							// 500公斤以下
							order.setForiginfreight(order.getFreight());// 0.4
																		// 500公斤以下
						}
						if (finalWeight.compareTo(new BigDecimal(700)) <= 0
								&& finalWeight.compareTo(new BigDecimal(500)) == 1) {
							order.setFreight(finalWeight.multiply(
									new BigDecimal("0.36")).add(
									ruleW.getFadd_service()));// 0.4*0.9
							// 500-700公斤
							order.setForiginfreight(order.getFreight());// 0.4*0.9
																		// 500-700公斤
						}
						if (finalWeight.compareTo(new BigDecimal(1000)) <= 0
								&& finalWeight.compareTo(new BigDecimal(700)) == 1) {
							order.setFreight(finalWeight.multiply(
									new BigDecimal("0.32")).add(
									ruleW.getFadd_service()));// 0.4*0.8
							// 700-900公斤
							order.setForiginfreight(order.getFreight());// 0.4*0.8
																		// 700-900公斤
						}
						if (finalWeight.compareTo(new BigDecimal(1000)) == 1) {
							order.setFreight(finalWeight.multiply(
									new BigDecimal("0.28")).add(
									ruleW.getFadd_service()));// 0.4*0.7
							// 1000公斤以上
							order.setForiginfreight(order.getFreight());// 0.4*0.7
																		// 1000公斤以上
						}
					} else {
						if (finalWeight.compareTo(new BigDecimal(500)) <= 0) {
							order.setFreight(finalWeight
									.multiply(new BigDecimal("0.4")));// 0.4
																		// 500公斤以下
							order.setForiginfreight(order.getFreight());// 0.4
																		// 500公斤以下
						}
						if (finalWeight.compareTo(new BigDecimal(700)) <= 0
								&& finalWeight.compareTo(new BigDecimal(500)) == 1) {
							order.setFreight(finalWeight
									.multiply(new BigDecimal("0.36")));// 0.4*0.9
																		// 500-700公斤
							order.setForiginfreight(order.getFreight());// 0.4*0.9
																		// 500-700公斤
						}
						if (finalWeight.compareTo(new BigDecimal(1000)) <= 0
								&& finalWeight.compareTo(new BigDecimal(700)) == 1) {
							order.setFreight(finalWeight
									.multiply(new BigDecimal("0.32")));// 0.4*0.8
																		// 700-900公斤
							order.setForiginfreight(order.getFreight());// 0.4*0.8
																		// 700-900公斤
						}
						if (finalWeight.compareTo(new BigDecimal(1000)) == 1) {
							order.setFreight(finalWeight
									.multiply(new BigDecimal("0.28")));// 0.4*0.7
																		// 1000公斤以上
							order.setForiginfreight(order.getFreight());// 0.4*0.7
																		// 1000公斤以上
						}
					}
				} else {
					if (finalWeight.compareTo(new BigDecimal(500)) <= 0) {
						order.setFreight(finalWeight.multiply(new BigDecimal(
								"0.4")));// 0.4
						// 500公斤以下
						order.setForiginfreight(order.getFreight());// 0.4
																	// 500公斤以下
					}
					if (finalWeight.compareTo(new BigDecimal(700)) <= 0
							&& finalWeight.compareTo(new BigDecimal(500)) == 1) {
						order.setFreight(finalWeight.multiply(new BigDecimal(
								"0.36")));// 0.4*0.9
						// 500-700公斤
						order.setForiginfreight(order.getFreight());// 0.4*0.9
						// 500-700公斤
					}
					if (finalWeight.compareTo(new BigDecimal(1000)) <= 0
							&& finalWeight.compareTo(new BigDecimal(700)) == 1) {
						order.setFreight(finalWeight.multiply(new BigDecimal(
								"0.32")));// 0.4*0.8
						// 700-900公斤
						order.setForiginfreight(order.getFreight());// 0.4*0.8
						// 700-900公斤
					}
					if (finalWeight.compareTo(new BigDecimal(1000)) == 1) {
						order.setFreight(finalWeight.multiply(new BigDecimal(
								"0.28")));// 0.4*0.7
						// 1000公斤以上
						order.setForiginfreight(order.getFreight());// 0.4*0.7
						// 1000公斤以上
					}

				}
			}
		*/}
		// 2016-6-18 新需求 按重量（公斤）区间来算 价格 BY twr end
		order.setNumber(CacheUtilByCC.getOrderNumber(new String("CL_Order"),
				new String("O"), 8));
		order.setStatus(9);// 新状态 待确认状态
		JSONArray addressDel = JSONArray.fromObject(addressDeliverString);
		JSONArray addressRec = JSONArray.fromObject(addressReceiptString);

		/** 计算运费 Start */
		// order.setFdriverfee(order.getFreight().multiply(CalcTotalField.calDriverFee(order.getFreight())).setScale(1,BigDecimal.ROUND_HALF_UP));
		/** 计算运费 End */
		this.orderDao.save(order);
		// ============================================整车计算运费=============结束=====================================//
		// =====================
		// 增加订单车辆明细===============================开始=====================================//
		if (order.getType() == 1) {
			String[] carTypeString = carTypeId.split(",");
			if (otherId != null && !"".equals(otherId)
					&& !otherId.contains("null")) {
				String[] carOtherString = otherId.split(",");
				for (int m = 0; m < carOtherString.length; m++) {
					for (int n = 0; n < carTypeString.length; n++) {
						CL_OrderCarDetail orderCarDetail1 = new CL_OrderCarDetail();
						orderCarDetail1.setOrderId(order.getId());
						orderCarDetail1.setCarSpecId(specId);
						// if(Integer.valueOf(carTypeString[n])>22){
						// System.out.println("**********************数据有问题************************"+order.getNumber());
						// map.put("success", "false");
						// map.put("msg","车辆类型数据有误！");
						// return writeAjaxResponse(response,
						// JSONUtil.getJson(map));
						// }
						orderCarDetail1.setCarTypeId(Integer
								.valueOf(carTypeString[n]));
						orderCarDetail1.setCarOtherId(Integer
								.valueOf(carOtherString[m]));
						orderCarDetail1.setCreateTime(new Date());
						orderCarDetailDao.save(orderCarDetail1);
					}
				}
			} else {
				for (int i = 0; i < carTypeString.length; i++) {
					CL_OrderCarDetail orderCarDetail2 = new CL_OrderCarDetail();
					orderCarDetail2.setOrderId(order.getId());
					orderCarDetail2.setCarSpecId(specId);
					// if(Integer.valueOf(carTypeString[i])>22){
					// System.out.println("**********************数据有问题************************"+order.getNumber());
					// map.put("success", "false");
					// map.put("msg","车辆类型数据有误！");
					// return writeAjaxResponse(response,
					// JSONUtil.getJson(map));
					// }
					orderCarDetail2.setCarTypeId(Integer
							.valueOf(carTypeString[i]));
					orderCarDetail2.setCreateTime(new Date());
					orderCarDetailDao.save(orderCarDetail2);
				}
			}
		}

		// =====================
		// 增加订单车辆明细===============================结束=====================================//
		this.createDetail(addressDel, order.getId(), 1);
		this.createDetail(addressRec, order.getId(), 2);
		order.setFreight(order.getFreight().setScale(2,
				BigDecimal.ROUND_HALF_UP));
		order.setForiginfreight(order.getFreight());
		order.setFtotalFreight(order.getFreight());
		if (addressRec.size() > 1) {
			order.setPayMethod("0,1,2,3");
		} else {
			order.setPayMethod("0,1,2,3,4");
		}
		map.put("success", "true");
		map.put("data", order);
		return writeAjaxResponse(response, JSONUtil.getJson(map));
	}

	// 创建订单明细发货
	public void createDetail(JSONArray address, Integer orderId,
			Integer detailType) {
		Integer ranInt = null;
		String ranString = null;
		for (int i = 0; i < address.size(); i++) {
			ranInt = new Random().nextInt(900000) + 100000;
			ranString = ranInt.toString();
			JSONObject jdel = address.getJSONObject(i);
			CL_OrderDetail detail = new CL_OrderDetail();
			detail.setOrderId(orderId);
			detail.setDetailType(detailType);
			detail.setLinkman(jdel.get("linkman").toString());
			detail.setPhone(jdel.get("phone").toString());
			detail.setAddressName(jdel.get("addressName").toString());
			detail.setLongitude(jdel.get("longitude").toString());
			detail.setLatitude(jdel.get("latitude").toString());
			detail.setAddressId(jdel.get("id").toString());
			detail.setSnumber(i + 1);
			detail.setSecurityCode(ranString);
			detail.setPass(0);
			detail.setFstatus(0);//默认卸货与提货未确认
			this.orderDetailDao.save(detail);
		}
	}

	/*** 设为常用订单 */
	@RequestMapping("/app/order/setCommon")
	public String setCommon(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		Integer orderId, isCommon;
		HashMap<String, Object> map = new HashMap<String, Object>();
		if (request.getParameter("orderId") == null
				|| "".equals(request.getParameter("orderId"))) {
			map.put("success", "false");
			map.put("msg", "数据有误请联系客服");
			return writeAjaxResponse(response, JSONUtil.getJson(map));
		} else {
			orderId = Integer.parseInt(request.getParameter("orderId"));
		}
		if (request.getParameter("isCommon") == null
				|| "".equals(request.getParameter("isCommon"))) {
			map.put("success", "false");
			map.put("msg", "设为常用订单失败请联系客服");
			return writeAjaxResponse(response, JSONUtil.getJson(map));
		} else {
			isCommon = Integer.parseInt(request.getParameter("isCommon"));
		}
		this.orderDao.updateByUserId(orderId, isCommon);
		HashMap<String, Object> m = new HashMap<String, Object>();
		m.put("success", "true");
		m.put("msg", "设置成功！");
		return writeAjaxResponse(response, JSONUtil.getJson(m));
	}

	/** 根据订单状态筛选订单 */
	@RequestMapping("/app/order/filtrateOrder")
	public String filtrateOrder(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		Integer status, pageNum, pageSize, type;
		HashMap<String, Object> map = new HashMap<String, Object>();
		if (request.getParameter("status") == null
				|| "".equals(request.getParameter("status"))) {
			map.put("success", "false");
			map.put("msg", "数据有误请联系客服");
			return writeAjaxResponse(response, JSONUtil.getJson(map));
		} else {
			status = Integer.parseInt(request.getParameter("status"));
		}
		if (request.getParameter("pageNum") == null
				|| "".equals(request.getParameter("pageNum"))) {
			map.put("success", "false");
			map.put("msg", "无法获知第几页");
			return writeAjaxResponse(response, JSONUtil.getJson(map));
		} else {
			pageNum = Integer.parseInt(request.getParameter("pageNum"));
		}
		if (request.getParameter("type") == null
				|| "".equals(request.getParameter("type"))) {
			map.put("success", "false");
			map.put("msg", "数据有误请联系客服");
			return writeAjaxResponse(response, JSONUtil.getJson(map));
		} else {
			type = Integer.parseInt(request.getParameter("type"));
		}
		if (request.getParameter("pageSize") == null
				|| "".equals(request.getParameter("pageSize"))) {
			map.put("success", "false");
			map.put("msg", "无法获知每页显示多少条");
			return writeAjaxResponse(response, JSONUtil.getJson(map));
		} else {
			pageSize = Integer.parseInt(request.getParameter("pageSize"));
		}
		if (orderQuery == null) {
			orderQuery = newQuery(OrderQuery.class, null);
		}
		if (pageNum != null) {
			orderQuery.setPageNumber(pageNum);
		}
		if (pageSize != null) {
			orderQuery.setPageSize(pageSize);
		}
		if (status != null) {
			orderQuery.setStatus(status);
		}
		if (type != null) {
			orderQuery.setType(type);
		}
		Page<CL_Order> page = orderDao.findPage(orderQuery);
		HashMap<String, Object> m = new HashMap<String, Object>();
		m.put("success", "true");
		m.put("total", page.getTotalCount());
		m.put("data", page.getResult());
		return writeAjaxResponse(response, JSONUtil.getJson(m));
	}

	/** APP删除订单 */
	@RequestMapping("/app/order/delorder")
	public String delorder(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		Integer orderId, userType;
		HashMap<String, Object> map = new HashMap<String, Object>();
		if (request.getParameter("orderId") == null
				|| "".equals(request.getParameter("orderId"))) {
			map.put("success", "false");
			map.put("msg", "数据有误请联系客服");
			return writeAjaxResponse(response, JSONUtil.getJson(map));
		} else {
			orderId = Integer.parseInt(request.getParameter("orderId"));
		}
		if (request.getParameter("userType") == null
				|| "".equals(request.getParameter("userType"))) {
			map.put("success", "false");
			map.put("msg", "数据有误请联系客服");
			return writeAjaxResponse(response, JSONUtil.getJson(map));
		} else {
			userType = Integer.parseInt(request.getParameter("userType"));
		}
		CL_Order order = this.orderDao.getById(orderId);
		if (order.getFdelOrder() != 0) {
			order.setFdelOrder(3);
			this.orderDao.update(order);
		}
		if (order.getFdelOrder() == 0) {
			switch (userType) {
				case 1 :
					order.setFdelOrder(1);
					this.orderDao.update(order);
					break;
				case 2 :
					order.setFdelOrder(2);
					this.orderDao.update(order);
					break;
			}
		}
		map.put("success", "true");
		map.put("msg", "修改成功");
		return writeAjaxResponse(response, JSONUtil.getJson(map));

	}

	/**
	 * 车主抢单
	 */
	@RequestMapping("/app/driver/ownerGrab")
//	@Lock("lock.com.pc.appInterface.order.AppOrderControll.ownerGrab")
	public  String ownerGrab(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Integer userId, orderId;
		HashMap<String, Object> map = new HashMap<String, Object>();
		if (request.getParameter("userId") == null
				|| "".equals(request.getParameter("userId"))) {
			map.put("success", "false");
			map.put("msg", "未登录！");
			return writeAjaxResponse(response, JSONUtil.getJson(map));
		} else {
			userId = Integer.valueOf(request.getParameter("userId"));
		}
		if (request.getParameter("orderId") == null
				|| "".equals(request.getParameter("orderId"))) {
			map.put("success", "false");
			map.put("msg", "数据有误请联系客服");
			return writeAjaxResponse(response, JSONUtil.getJson(map));
		} else {
			orderId = Integer.parseInt(request.getParameter("orderId"));
		}
		// 判断订单是否为未抢状态
		CL_Order orderInfo = orderDao.IsExistIdByStatus(orderId);
		if (orderInfo != null && !orderInfo.equals("")) {
			orderDao.updateByOrderId(userId, userId, orderId, 3);// 运输中
			// ====抢单成功后向货主发送验证码==//
			List<CL_OrderDetail> detailList = new ArrayList<>();
			if (orderInfo.getType() == 3) {
				detailList = orderDetailDao.getByOrderId(orderId, 1);
			} else {
				detailList = orderDetailDao.getByOrderId(orderId, 2);
			}
			CL_UserRole userRole = userRoleDao.getById(userId);

			// 子帐号新建的订单推送给子帐号;
			CL_UserRole userRole1 = null;
			if (orderDao.getById(orderId).getSubUserId() != null
					&& !orderDao.getById(orderId).getSubUserId().equals("")) {
				userRole1 = userRoleDao.getById(orderDao.getById(orderId)
						.getSubUserId());
			} else {
				userRole1 = userRoleDao.getById(orderDao.getById(orderId)
						.getCreator());
			}

			List<CL_Car> cars = cardao.getByUserRoleId(userId);
			DongjingClient djcn = ServerContext.createVmiClient();
			// String msgString1="您的订单已被抢,为您服务的
			// %s,电话:"+userRole.getVmiUserPhone()+",车牌号:%s,客服:0577-85391111,更多优惠下载手机APP,http://fir.im/d1q4";
			String msgString1 = "您的订单已被抢,为您服务的 %s,电话:"
					+ userRole.getVmiUserPhone() + ",车牌号:%s,客服:0577-85391111";
			// String msgString="您的货物在运输途中,为您服务的
			// %s,电话:"+userRole.getVmiUserPhone()+",车牌号:"+cars.get(0).getCarNum()+",客服:0577-85391111,更多优惠下载手机APP,http://fir.im/d1q4";
			String msgString = "您的货物在运输途中,为您服务的 %s,电话:"
					+ userRole.getVmiUserPhone() + ",车牌号:"
					+ cars.get(0).getCarNum() + ",客服:0577-85391111";
			djcn.setMethod("getDeatilCode");
			djcn.setRequestProperty("ftel", userRole1.getVmiUserPhone());
			djcn.setRequestProperty("fname", cars.get(0).getDriverName());
			djcn.setRequestProperty("fcode", cars.get(0).getCarNum());
			djcn.setRequestProperty("msgString", msgString1);
			djcn.SubmitData();
			for (CL_OrderDetail detail : detailList) {
				djcn.setMethod("getDeatilCode");
				djcn.setRequestProperty("ftel", detail.getPhone());
				djcn.setRequestProperty("fname", cars.get(0).getDriverName());
				djcn.setRequestProperty("fcode", detail.getSecurityCode());
				djcn.setRequestProperty("msgString", msgString);
				djcn.SubmitData();
			}
			map.put("success", "true");
			map.put("msg", "抢单成功！");
		} else {
			map.put("success", "false");
			map.put("msg", "该单已经被抢！");
		}
		return writeAjaxResponse(response, JSONUtil.getJson(map));
	}

	/**
	 * 循环单列表
	 * 货主我的订单列表
	 * @throws ParseException
	 */
	@RequestMapping("/app/order/cycleorder")
	public String cycleorder(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ParseException {
		int userId = 0, type = 0, pageNum = 0, pageSize = 0, isCommon = 0, status = 0;
		HashMap<String, Object> map = new HashMap<String, Object>();
		// if(request.getParameter("userId")==null ||
		// "".equals(request.getParameter("userId"))){
		// map.put("success", "false");
		// map.put("msg","未登录！");
		// return writeAjaxResponse(response, JSONUtil.getJson(map));
		// }else{
		// userId = Integer.parseInt(request.getParameter("userId"));
		// }
		userId = getRequestParameter(request, response, map, "未登录！", "userId",
				userId);
		status = getRequestParameter(request, response, map, "无法获知订单状态",
				"status", status);
		pageSize = getRequestParameter(request, response, map, "无法获知每页显示多少条",
				"pagesize", pageSize);
		pageNum = getRequestParameter(request, response, map, "无法获知第几页",
				"pagenum", pageNum);
		// if(request.getParameter("status")==null ||
		// "".equals(request.getParameter("status"))){
		// map.put("success", "false");
		// map.put("msg","无法获知订单状态");
		// return writeAjaxResponse(response, JSONUtil.getJson(map));
		// }else{
		// status = Integer.parseInt(request.getParameter("status"));
		// }
		// if(request.getParameter("pagenum")==null ||
		// "".equals(request.getParameter("pagenum"))){
		// map.put("success", "false");
		// map.put("msg","无法获知第几页");
		// return writeAjaxResponse(response, JSONUtil.getJson(map));
		// }else{
		// pageNum = Integer.valueOf(request.getParameter("pagenum"));
		// }
		// if(request.getParameter("pagesize")==null ||
		// "".equals(request.getParameter("pagesize"))){
		// map.put("success", "false");
		// map.put("msg","无法获知每页显示多少条");
		// return writeAjaxResponse(response, JSONUtil.getJson(map));
		// }else{
		// pageSize = Integer.valueOf(request.getParameter("pagesize"));
		// }

		orderQuery = newQuery(OrderQuery.class, null);

		HashMap<String, Util_UserOnline> useronline = ServerContext
				.getUseronline();
		if (request.getSession().getId() == null
				|| request.getSession().getId().equals("")) {
			map.put("success", "false");
			map.put("msg", "登录超时！");
			return writeAjaxResponse(response, JSONUtil.getJson(map));
		}
		if (useronline.get(request.getSession().getId().toString()).isSub()) {
			orderQuery.setSubUserId(useronline.get(
					request.getSession().getId().toString()).getFsubId());
		} else {
			orderQuery.setCreator(userId);
		}

		if (request.getParameter("type") != null
				&& !"".equals(request.getParameter("type"))) {
			orderQuery.setType(Integer.parseInt(request.getParameter("type")));
		}
		if (request.getParameter("isCommon") != null
				&& !"".equals(request.getParameter("isCommon"))) {
			orderQuery.setIsCommon(Integer.parseInt(request
					.getParameter("isCommon")));
		}
		orderQuery.setPageNumber(pageNum);
		orderQuery.setPageSize(pageSize);
		orderQuery.setStatus(null);
		orderQuery.setHuoLoadOrder(status);
		Page<CL_Order> page = orderDao.findPage(orderQuery);
		List<CL_Order> list = page.getResult();
		for (CL_Order order : list) {
			// 判断运费调整是否有审批，或是通过审批 2016/11/30 by lancher
			if (order.getFispass_audit() == null
					|| order.getFispass_audit() == 0) {
				order.setFreight(order.getForiginfreight());
			} else {
				order.setFreight(order.getFreight());
			}
			CL_Protocol pro = new CL_Protocol();
			if (order.getProtocolId() != null && order.getProtocolId() != 0) {
				pro = protocolDao.getById(order.getProtocolId());
				if (pro != null) {
					order.setProtocol(pro);
				}
			}
			CL_OrderDetail detailOne = this.orderDetailDao
					.getByOrderIdForDeliverAddress(order.getId());
			if (detailOne == null) {
				map.put("success", "false");
				map.put("msg", "订单编号为: " + order.getNumber() + "   数据遗失请联系客服");
				return writeAjaxResponse(response, JSONUtil.getJson(map));
			}
			order.setTakephone(detailOne.getPhone());
			order.setTakelinkman(detailOne.getLinkman());
			order.setTakeAddress(detailOne.getAddressName());
			if (order.getType() != 3) {
				CL_OrderDetail detailTwo = this.orderDetailDao
						.getByOrderIdForConsigneeAddress(order.getId());
				if (detailTwo == null) {
					map.put("success", "false");
					map.put("msg", "订单编号为: " + order.getNumber()
							+ "  数据遗失请联系客服");
					return writeAjaxResponse(response, JSONUtil.getJson(map));
				}
				order.setRecphone(detailTwo.getPhone());
				order.setReclinkman(detailTwo.getLinkman());
				order.setRecAddress(detailTwo.getAddressName());
			}
			List<Util_Option> ups = optionDao.getCarTypeByOrderIdName(order
					.getId());
			String carSpecName = "", carOtherName = "";
			Integer carSpecId = null;
			if (ups.size() > 0) {
				carSpecId = ups.get(0).getOptionId();
				carSpecName = ups.get(0).getOptionName();
				carOtherName = ups.get(0).getOptionCarOtherName();
			}
			for (Util_Option option : ups) {
				if (option.getOptionCarTypeName() != null
						&& !option.getOptionCarTypeName().equals("")) {
					if (!carSpecName.contains(option.getOptionCarTypeName())) {
						carSpecName = carSpecName + " "
								+ option.getOptionCarTypeName();
					}
				}

				if (carOtherName != null
						&& "".equals(carOtherName)
						&& !carOtherName.contains(option
								.getOptionCarOtherName())
						&& !"".equals(option.getOptionCarOtherName())
						&& option.getOptionCarOtherName() != null) {
					carOtherName = carOtherName + " "
							+ option.getOptionCarOtherName();
				}
			}
			if (carOtherName == null || "".equals(carOtherName)) {
				carSpecName = carSpecName + " " + order.getGoodsTypeName();
			} else if (carOtherName != null && !"".equals(carOtherName)) {
				carSpecName = carSpecName + " " + carOtherName + " "
						+ order.getGoodsTypeName();
			}
			order.setCarTypeName(carSpecName);
			order.setCarSpecId(carSpecId);
			BigDecimal AllCost = BigDecimal.ZERO;
			List<CL_Addto> adds = iaddtoDao.getByOrderId(order.getId());
			if (adds.size() > 0) {
				for (CL_Addto ad : adds) {
					AllCost = AllCost.add(ad.getFcost());
				}
			}
			order.setAllCost(AllCost);
			// List<Util_Option> gbbspec =
			// this.optionDao.getCarSpecByOrderId(order.getId());
			// if(carspec.size()>0){
			// String carSpec =null;
			// carSpec = carspec.get(0).getOptionName()+" ";
			// List<Util_Option> cartype =
			// this.optionDao.getCarTypeByOrderId(order.getId());
			// String carType="";
			// for(Util_Option option :cartype){
			// carType = carType+","+option.getOptionName();
			// }
			// order.setCarTypeName(carSpec+" "+carType);
			// }

		}
		map.put("success", "true");
		map.put("total", page.getTotalCount());
		map.put("data", page.getResult());
		return writeAjaxResponse(response, JSONUtil.getJson(map));
	}

	/**
	 * 车辆位置 20160825 APP未使用
	 */
	@RequestMapping("/app/order/carposition")
	public String carposition(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		Integer userId, status, pageNum, pageSize;
		HashMap<String, Object> map = new HashMap<String, Object>();
		if (request.getParameter("userId") == null
				|| "".equals(request.getParameter("userId"))) {
			map.put("success", "false");
			map.put("msg", "未登录！");
			return writeAjaxResponse(response, JSONUtil.getJson(map));
		} else {
			userId = Integer.parseInt(request.getParameter("userId"));
		}
		if (request.getParameter("status") == null
				|| "".equals(request.getParameter("status"))) {
			map.put("success", "false");
			map.put("msg", "无法获知追踪状态！");
			return writeAjaxResponse(response, JSONUtil.getJson(map));
		} else {
			status = Integer.parseInt(request.getParameter("status"));
		}
		if (request.getParameter("pagenum") == null
				|| "".equals(request.getParameter("pagenum"))) {
			map.put("success", "false");
			map.put("msg", "无法获知第几页");
			return writeAjaxResponse(response, JSONUtil.getJson(map));
		} else {
			pageNum = Integer.valueOf(request.getParameter("pagenum"));
		}
		if (request.getParameter("pagesize") == null
				|| "".equals(request.getParameter("pagesize"))) {
			map.put("success", "false");
			map.put("msg", "无法获知每页显示多少条");
			return writeAjaxResponse(response, JSONUtil.getJson(map));
		} else {
			pageSize = Integer.valueOf(request.getParameter("pagesize"));
		}
		orderQuery = newQuery(OrderQuery.class, null);
		orderQuery.setCreator(userId);
		orderQuery.setStatus(status);
		orderQuery.setPageNumber(pageNum);
		orderQuery.setPageSize(pageSize);
		Page<CL_Order> page = orderDao.findPage(orderQuery);
		List<CL_Order> list = page.getResult();
		for (CL_Order order : list) {
			CL_OrderDetail detailOne = this.orderDetailDao
					.getByOrderIdForDeliverAddress(order.getId());
			if (detailOne == null) {
				map.put("success", "false");
				map.put("msg", "订单编号为: " + order.getNumber() + "   数据遗失请联系客服");
				return writeAjaxResponse(response, JSONUtil.getJson(map));
			}
			order.setTakephone(detailOne.getPhone());
			order.setTakelinkman(detailOne.getLinkman());
			order.setTakeAddress(detailOne.getAddressName());
			CL_OrderDetail detailTwo = this.orderDetailDao
					.getByOrderIdForConsigneeAddress(order.getId());
			if (detailTwo == null) {
				map.put("success", "false");
				map.put("msg", "订单编号为: " + order.getNumber() + "  数据遗失请联系客服");
				return writeAjaxResponse(response, JSONUtil.getJson(map));
			}
			order.setRecphone(detailTwo.getPhone());
			order.setReclinkman(detailTwo.getLinkman());
			order.setRecAddress(detailTwo.getAddressName());
			if (order.getUserRoleId() != null) {
				order.setOrderDriverName(cardao.getCarTypeByUserRoleId(
						order.getUserRoleId()).getDriverName());
			}
		}
		map.put("success", "true");
		map.put("total", page.getTotalCount());
		map.put("data", list);
		return writeAjaxResponse(response, JSONUtil.getJson(map));
	}

	/** 取消订单 */
	@RequestMapping("/app/order/cancelorder")
	public String cancelorder(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		HashMap<String, Object> map = new HashMap<String, Object>();
		Integer userId, orderId;
		if (request.getParameter("userId") == null
				|| "".equals(request.getParameter("userId"))) {
			map.put("success", "false");
			map.put("msg", "未登录！");
			return writeAjaxResponse(response, JSONUtil.getJson(map));
		} else {
			userId = Integer.valueOf(request.getParameter("userId"));
		}
		if (request.getParameter("orderId") == null
				|| "".equals(request.getParameter("orderId"))) {
			map.put("success", "false");
			map.put("msg", "数据有误请联系客服");
			return writeAjaxResponse(response, JSONUtil.getJson(map));
		} else {
			orderId = Integer.parseInt(request.getParameter("orderId"));
		}
		CL_Order order = this.orderDao.getById(orderId);
		// 将发送退款请求放到服务端，确保支付退款与业务系统状态一致 BY CC 2016-03-04 START
		if (order.getStatus() != 1 && order.getStatus() != 9) {
			map.put("success", "false");
			map.put("msg", "该订单状态无法取消");
			return writeAjaxResponse(response, JSONUtil.getJson(map));
		}
		// 将发送退款请求放到服务端，确保支付退款与业务系统状态一致 BY CC 2016-03-04 END

		// List<CL_FinanceStatement> fstatement =
		// financeStatementDao.getforderId(order.getNumber());
		// if (fstatement.size() == 0)
		// return this.poClient(response, false, "未找到此订单相关支付信息");

		/***************** 连接支付服务查询订单当前状态 ******************************/

		// LinkedHashMap<String, Object> modellist = new LinkedHashMap<>();
		// modellist.put("orderNumber", fstatement.get(0).getNumber()); // 订单编号
		// modellist.put("notificationURL", "");
		//
		// HashMap<String, Object> param = new HashMap<>();
		// String responeData = PayUtil.orderValidationToPaySystem(param,
		// modellist);
		// try {
		// JSONObject jo = JSONObject.fromObject(responeData);
		// if ("true".equals(jo.get("success").toString())) {
		// return this.poClient(response, false, "该订单已完成支付，不能取消");
		// } else {
		// String code = jo.get("code").toString();
		// if (!"1002".equals(code)) // 订单已取消 允许取消
		// return this.poClient(response, false, "该订单正在处理中，不能取消");
		// }
		// } catch (StringIndexOutOfBoundsException e) {
		// return this.poClient(response, false, "支付系统异常");
		// }

		/***********************************************/

		// /******************给货主退款*****************************/
		// // 获取实际支付金额
		// BigDecimal actuaAmount = order.getFreight();
		//
		// // 减完好运券之后的金额（订单真实支付金额）
		// CL_CouponsDetail detail =
		// this.couponsDetailDao.getById(order.getCouponsDetailId());
		// if (detail != null) {
		// CL_Coupons cp = this.couponsDao.getById(detail.getCouponsId());
		// if (cp != null) {
		// actuaAmount = actuaAmount.subtract(cp.getDollars());
		// }
		// }
		//
		// String FNumber = CacheUtilByCC.getOrderNumber("cl_finance_statement",
		// "L", 8);
		//
		// CL_FinanceStatement statement = new CL_FinanceStatement();
		// statement.setFcreateTime(new Date());
		// statement.setNumber(FNumber);
		// statement.setFrelatedId(order.getId().toString());
		// statement.setForderId(order.getNumber());
		// statement.setFbusinessType(8); // 订单取消
		// statement.setFamount(actuaAmount);
		// statement.setFtype(1);
		// statement.setFuserroleId(fcusid);
		// statement.setFuserid(fcusid.toString());
		// statement.setFpayType(0);
		// statement.setFremark(order.getNumber() + "订单取消");
		// statement.setFbalance(new BigDecimal("0"));
		// statement.setFstatus(0);
		// statement.setFreight(actuaAmount);
		//
		// financeStatementDao.save(statement);
		//
		// OrderMsg ordermsg = new OrderMsg();
		// ordermsg.setPayState("1"); // 支付成功
		// ordermsg.setServiceProvider("0");// 余额支付;
		// ordermsg.setServiceProviderType("0");// 余额支付;
		// ordermsg.setOrder(FNumber);
		// int status = financeStatementDao.updateBusinessType(ordermsg);
		// if (status == 0)
		// return this.poClient(response, false, "退款失败!请联系客服!");
		// respPPayURL = "{\"number\":\"" + add.getFid().toString() + "\"}";

		// return this.poClient(response, true);
		// 20160721 cd APP手动取消前，先进行订单支付校验，解决订单支付成功后由于异常导致订单没有充值记录及余额问题；
		// HashMap<String, Object> m = orderDao.autoPayOrderFreight(order);
		// if (m.get("payed") != null && m.get("payed").equals("true")) {
		// map.put("success", "false");
		// map.put("msg", "订单已支付成功，不能取消！");
		// return writeAjaxResponse(response, JSONUtil.getJson(map));
		// }

		// 归还服务优惠券
		if (order.getCouponsDetailId() != null
				&& order.getCouponsDetailId() != 0) {
			CL_CouponsDetail detail = this.couponsDetailDao.getById(order
					.getCouponsDetailId());
			if (detail != null) {
				detail.setIsUse(0);
				couponsDetailDao.update(detail);
			}
		}
		// 归还订单优惠券
		if (order.getCouponsId() != null
				&& order.getCouponsId() != 0) {
			CL_CouponsDetail detail = this.couponsDetailDao.getById(order
					.getCouponsId());
			if (detail != null) {
				detail.setIsUse(0);
				couponsDetailDao.update(detail);
			}
		}

		order.setCouponsDetailId(null);
		order.setCouponsId(null);
		order.setStatus(6);
		order.setOperator(userId);
		this.orderDao.update(order);
		map.put("success", "true");
		map.put("msg", "操作成功！");
		
		Integer fcusid = ServerContext.getUseronline()
				.get(request.getSession().getId().toString()).getFuserId();
		List<CL_Umeng_Push> umengList = iumeng.getUserUmengRegistration(fcusid);
		if (umengList .size() > 0) {
			// 获取到用户多台设备
			for (CL_Umeng_Push cl_Umeng_Push : umengList) {
				Demo.SendUmeng(cl_Umeng_Push.getFdevice(), "您的订单："+order.getNumber()+"已取消，请注意!", cl_Umeng_Push.getFdeviceType());
			}
			messageDao.save(Demo.savePushMessage("订单取消","订单:+"+order.getNumber()+"已取消!",fcusid,umengList.get(0).getFuserType()));
		}
		
		return writeAjaxResponse(response, JSONUtil.getJson(map));
	}

	/** 再次下单接口 */
	@RequestMapping("/app/order/againorder")
	public String againorder(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		Integer userId, orderId;
		HashMap<String, Object> map = new HashMap<String, Object>();
		
		if (request.getParameter("userId") == null
				|| "".equals(request.getParameter("userId"))) {
			map.put("success", "false");
			map.put("msg", "未登录！");
			return writeAjaxResponse(response, JSONUtil.getJson(map));
		} else {
			userId = Integer.parseInt(request.getParameter("userId"));
		}
		if (request.getParameter("orderId") == null
				|| "".equals(request.getParameter("orderId"))) {
			map.put("success", "false");
			map.put("msg", "数据有误请联系客服");
			return writeAjaxResponse(response, JSONUtil.getJson(map));
		} else {
			orderId = Integer.parseInt(request.getParameter("orderId"));
		}
		CL_Order order = this.orderDao.getById(orderId);
		// order.setTakeList(this.orderDetailDao.getByOrderId(orderId, 1));
		// 新增adddressId别名id
		order.setTakeList(this.orderDetailDao.getByNewOrderId(orderId, 1));
		// order.setRecList(this.orderDetailDao.getByOrderId(orderId, 2));
		order.setRecList(this.orderDetailDao.getByNewOrderId(orderId, 2));
		order.setCarList(this.orderCarDetailDao.getByOrderId(orderId));
		// order.setCarList(this.orderCarDetailDao.getByNewOrderId(orderId));
		map.put("success", "true");
		map.put("data", order);
		return writeAjaxResponse(response, JSONUtil.getJson(map));
	}

	/**
	 * 车主抢单列表
	 * 
	 * @throws ParseException
	 */
	@RequestMapping("/app/driver/grabOrderLoad")
	public String grabOrderLoad(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ParseException {
		HashMap<String, Object> map = new HashMap<String, Object>();
		try {
			RedisUtil.Lock("lock.com.pc.appInterface.order.app.driver.grabOrderLoad");

			Integer userId, pageNum, pageSize;
			int effectiveDistance = 10;
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//			HashMap<String, Object> map = new HashMap<String, Object>();
			if (request.getParameter("userId") == null
					|| "".equals(request.getParameter("userId"))) {
				map.put("success", "false");
				map.put("msg", "未登录！");
				return writeAjaxResponse(response, JSONUtil.getJson(map));
			} else {
				userId = Integer.valueOf(request.getParameter("userId"));
			}
			if (request.getParameter("pagenum") == null
					|| "".equals(request.getParameter("pagenum"))) {
				map.put("success", "false");
				map.put("msg", "无法获知第几页");
				return writeAjaxResponse(response, JSONUtil.getJson(map));
			} else {
				pageNum = Integer.valueOf(request.getParameter("pagenum"));
			}
			if (request.getParameter("pagesize") == null
					|| "".equals(request.getParameter("pagesize"))) {
				map.put("success", "false");
				map.put("msg", "无法获知每页显示多少条");
				return writeAjaxResponse(response, JSONUtil.getJson(map));
			} else {
				pageSize = Integer.valueOf(request.getParameter("pagesize"));
			}
			List<CL_Car> carlist = this.cardao.getByUserRoleId(userId);
			if (carlist.size() <= 0) {
				map.put("success", "false");
				map.put("msg", "请先认证车辆信息!");
				return writeAjaxResponse(response, JSONUtil.getJson(map));
			}
			orderQuery = newQuery(OrderQuery.class, null);
			if (pageNum != null) {
				orderQuery.setPageNumber(pageNum);
			}
			if (pageSize != null) {
				orderQuery.setPageSize(pageSize);
			}
			orderQuery.setStatus(2);
			orderQuery.setFdelOrder(0);
			Page<CL_Order> page = orderDao.findPage(orderQuery);
			List<CL_Order> list = page.getResult();

			int total = page.getTotalCount();
			System.out.println("共" + page.getTotalCount() + "条订单");
			/***
			 * BY CC 2016-03-05 1、list要从大到小移除，否则有可能出现移除时越界和数据错位错误,
			 * 2、需要剔除要车时间小于当前时间的订单
			 */
			/***
			 * 通过BigDecimal的compareTo方法来进行比较. 返回的结果是int类型,-1表示小于,0是等于,1是大于.
			 */
			for (int i = list.size() - 1; i >= 0; i--) {
				// 临时零担不参与抢单
				if (list.get(i).getType() == 2) {
					list.remove(i);
					total = total - 1;
					continue;
				}

				// 临时整车不参与抢单
				if (list.get(i).getType() == 1) {
					list.remove(i);
					total = total - 1;
					continue;
				}
				Date d1 = df.parse(df.format(new Date()));
				if (list.get(i).getLoadedTime().compareTo(new Date()) == -1
						|| list.get(i).getLoadedTime().compareTo(new Date()) == 0) {
					System.out.println("派车大于当前事件移除");
					list.remove(i);
					total = total - 1;
					continue;
				}

				// 先判断时间、好运司机判断，可以减少很多数据库取数 BY CC 2016-05-21 START
				if (carlist.get(0).getFluckDriver() == null
						|| carlist.get(0).getFluckDriver() == 0) {
					if (list.get(i).getProtocolType() == 1) {
						list.remove(i);
						total = total - 1;
						continue;
					}
					long time = d1.getTime()
							- list.get(i).getFpayTime().getTime();
					long ss = time / 1000;
					long systime = 10;
					// / 三分钟 抢单时间 by twr
					if (!(Math.abs(ss - systime) <= 180 && Math.abs(ss
							- systime) >= 10)) {
						list.remove(i);
						total = total - 1;
						continue;
					}
				}
				// 好运司机抢单列表
				if (carlist.get(0).getFluckDriver() == 1) {
					if (list.get(i).getProtocolType() == 1) {
						list.remove(i);
						total = total - 1;
						continue;
					}
					long time = d1.getTime()
							- list.get(i).getFpayTime().getTime();
					long ss = time / 1000;
					if (!(Math.abs(ss) <= 180 && Math.abs(ss) >= 0)) {
						list.remove(i);
						total = total - 1;
						continue;
					}
				}
				// 先判断时间、好运司机判断，可以减少很多数据库取数 BY CC 2016-05-21 END
				// 通过订单流水号ID,查询订单对应的所需车型(去掉重复)
				List<Util_Option> option = this.optionDao
						.getCarTypeByOrderId(list.get(i).getId());
				if (option.size() > 0) {
					if (option.size() == 1) {
						if (!("任意车型").equals(option.get(0).getOptionName())) {
							if (!option.get(0).getOptionId()
									.equals(carlist.get(0).getCarType())) {
								System.out.println("单选车型：订单所需车型和司机对应的车型不匹配");
								list.remove(i);
								total = total - 1;
								continue;
							}
						} else if (("任意车型").equals(option.get(0)
								.getOptionName())
								&& !option.get(0).getId()
										.equals(carlist.get(0).getCarSpecId())) {
							list.remove(i);
							total = total - 1;
							continue;
						}
					} else {
						List<Integer> carIds = new ArrayList<Integer>();
						for (int m = 0; m < option.size(); m++) {
							carIds.add(option.get(m).getOptionId());
						}
						if (!carIds.contains(carlist.get(0).getCarType())) {
							System.out.println("多选车型：订单所需车型和司机对应的车型不匹配");
							list.remove(i);
							total = total - 1;
							continue;
						}
					}
				}
				CL_OrderDetail detail = this.orderDetailDao
						.getByOrderIdForDeliverAddress(list.get(i).getId());
				// 司机经纬度需要替换 记得！！
				String[] st = null;
				String Longitude = "";
				String Latitude = "";
				st = ServerContext.getDriverPosition().get(
						Integer.toString(userId));
				if (st != null && st.length > 0) {
					Longitude = st[0];
					Latitude = st[1];
				} else {
					list.remove(i);
					total = total - 1;
					continue;
				}
				BigDecimal straightStretch = LatitudeLongitudeDI.GetDistance(
						Double.parseDouble(detail.getLatitude()),
						Double.parseDouble(detail.getLongitude()),
						Double.parseDouble(Latitude),
						Double.parseDouble(Longitude));
				if ((new BigDecimal(effectiveDistance))
						.compareTo(straightStretch) == -1) {
					System.out.println("==========相距超出10公里===========移除订单："
							+ list.get(i).getNumber());
					list.remove(i);
					total = total - 1;
					continue;
				}
				// 先判断时间、好运司机判断，可以减少很多数据库取数 BY CC 2016-05-21 START 先注释了
				// 这里要过滤 时间 和好运司机 还有协议订单
				// if(carlist.get(0).getFluckDriver()==null||carlist.get(0).getFluckDriver()==0){
				// if(list.get(i).getProtocolType()==1){
				// list.remove(i);
				// total=total-1;
				// continue;
				// }
				// long time= d1.getTime()-list.get(i).getFpayTime().getTime();
				// long ss=time/1000;
				// long systime=10;
				// if(!(Math.abs(ss-systime)<=60&&Math.abs(ss-systime)>=10)){
				// list.remove(i);
				// total=total-1;
				// continue;
				// }
				// }
				// //好运司机抢单列表
				// if(carlist.get(0).getFluckDriver()==1){
				// if(list.get(i).getProtocolType()==1){
				// list.remove(i);
				// total=total-1;
				// continue;
				// }
				// long time= d1.getTime()-list.get(i).getFpayTime().getTime();
				// long ss=time/1000;
				// if(!(Math.abs(ss)<=60&&Math.abs(ss)>=0)){
				// list.remove(i);
				// total=total-1;
				// continue;
				// }
				// }
				// 先判断时间、好运司机判断，可以减少很多数据库取数 BY CC 2016-05-21 END
			}

			for (int i = 0; i < list.size(); i++) {
				CL_OrderDetail detail2 = this.orderDetailDao
						.getByOrderIdForDeliverAddress(list.get(i).getId());
				CL_OrderDetail detailTwo = this.orderDetailDao
						.getByOrderIdForConsigneeAddress(list.get(i).getId());
				list.get(i).setTakeAddress(detail2.getAddressName());
				if (detailTwo != null) {
					list.get(i).setRecAddress(detailTwo.getAddressName());
				}
				List<Util_Option> carspec = this.optionDao
						.getCarSpecByOrderId(list.get(i).getId());
				if (carspec.size() > 0) {
					String carSpec = null;
					carSpec = carspec.get(0).getOptionName() + " ";
					List<Util_Option> cartype = this.optionDao
							.getCarTypeByOrderId(list.get(i).getId());
					if (list.get(i).getType() == 1) {
						String carType = "";
						for (Util_Option option : cartype) {
							carType = carType + "," + option.getOptionName();
						}
						list.get(i).setCarTypeName(carSpec + " " + carType);
					}
				}
			}
			// 支持价格区间管理费用功能 ,在订单和追加费用表中增加司机运费字段，直接取数返回给前端 BY CC 2016-05-18 START
			for (int m = 0; m < list.size(); m++) {
				List<CL_Addto> adds = iaddtoDao.getByOrderId(list.get(m)
						.getId());
				BigDecimal AllCost = new BigDecimal(0);
				if (adds.size() > 0) {
					for (CL_Addto ad : adds) {
						AllCost = AllCost.add(ad.getFcost());
					}
				}
				list.get(m).setAllCost(
						AllCost.setScale(2, BigDecimal.ROUND_HALF_UP));
				list.get(m).setFreight(
						list.get(m).getFdriverfee()
								.setScale(2, BigDecimal.ROUND_HALF_UP));
				list.get(m).setForiginfreight(
						list.get(m).getFdriverfee()
								.setScale(2, BigDecimal.ROUND_HALF_UP));
			}
			// 支持价格区间管理费用功能 ,在订单和追加费用表中增加司机运费字段，直接取数返回给前端 BY CC 2016-05-18 END

			// CL_Rule rule = this.ruleDao.getOneByType();
			// if(rule ==null){
			// map.put("success", "false");
			// map.put("msg","司机计费规则丢失,请联系客服！");
			// return writeAjaxResponse(response, JSONUtil.getJson(map));
			// }else{
			// for(int m =0;m<list.size();m++){
			// List<CL_Addto> adds=iaddtoDao.getByOrderId(list.get(m).getId());
			// BigDecimal AllCost=new BigDecimal(0);
			// if(adds.size()>0){
			// for(CL_Addto ad:adds){
			// AllCost=AllCost.add(ad.getFcost());
			// }
			// }
			// list.get(m).setAllCost(AllCost.multiply(rule.getStartPrice()).setScale(0,BigDecimal.ROUND_HALF_UP));
			// list.get(m).setFreight(list.get(m).getFreight().multiply(rule.getStartPrice()).setScale(0,
			// BigDecimal.ROUND_HALF_UP));
			// }
			// }
			map.put("success", "true");
			map.put("total", total);
			map.put("data", list);
			System.out.println("----------------------------------------");
			System.out.println(JSONUtil.getJson(map));
		} catch (Exception e) {
			map.put("success", "false");
			map.put("total", "0");
			map.put("data", new ArrayList());
		} finally {
			RedisUtil
					.del("lock.com.pc.appInterface.order.app.driver.grabOrderLoad");
		}
		return writeAjaxResponse(response, JSONUtil.getJson(map));
	}

	/** 货主订单跟踪 */
	@RequestMapping("/app/shipper/getCarposition")
	public String getCarposition(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		Integer userId;
		HashMap<String, Object> map = new HashMap<String, Object>();
		if (request.getParameter("userId") == null
				|| "".equals(request.getParameter("userId"))) {
			map.put("success", "false");
			map.put("msg", "未登录！");
			return writeAjaxResponse(response, JSONUtil.getJson(map));
		} else {
			userId = Integer.valueOf(request.getParameter("userId"));
		}
		String[] st = null;
		st = ServerContext.getDriverPosition().get(Integer.toString(userId));
		if (st != null && st.length > 0) {
			String Longitude = st[0];
			String Latitude = st[1];
			map.put("success", "true");
			map.put("data", "[{\"Longitude\":\"" + Longitude
					+ "\",\"Latitude\":\"" + Latitude + "\"}]");
		} else {
			map.put("success", "false");
			map.put("msg", "未查询到司机位置");
		}
		return writeAjaxResponse(response, JSONUtil.getJson(map));
	}

	/*** 用户反馈 新建-保存 */
	@RequestMapping("/app/feedback/feedbackSave")
	public String feedbackSave(HttpServletRequest request,
			HttpServletResponse response, @ModelAttribute CL_Feedback feedback)
			throws Exception {
		HashMap<String, Object> map = new HashMap<String, Object>();
		if (request.getParameter("content") == null
				|| "".equals(request.getParameter("content"))) {
			map.put("success", "false");
			map.put("msg", "请先录入反馈信息！");
			return writeAjaxResponse(response, JSONUtil.getJson(map));
		}
		feedback.setCreateTime(new Date());
		feedback.setCreatorId(Integer.parseInt(request.getParameter("userId")
				.toString()));
		feedback.setContent(filterEmoji(request.getParameter("content")));
		// feedback.setContent(request.getParameter("content"));
		feedback.setFphone(request.getParameter("fphone"));
		this.userRoleDao.saveFeedback(feedback);
		map.put("success", "true");
		return writeAjaxResponse(response, JSONUtil.getJson(map));
	}

	// 去掉特殊字符（表情）
	private String filterEmoji(String source) {
		if (source != null) {
			Pattern emoji = Pattern
					.compile(
							"[\ud83c\udc00-\ud83c\udfff]|[\ud83d\udc00-\ud83d\udfff]|[\u2600-\u27ff]",
							Pattern.UNICODE_CASE | Pattern.CASE_INSENSITIVE);
			Matcher emojiMatcher = emoji.matcher(source);
			if (emojiMatcher.find()) {
				source = emojiMatcher.replaceAll("");
				return source;
			}
			return source;
		}
		return source;
	}

	/*** 获取用户已成单次数、被评价次数 */
	@RequestMapping("/app/order/getEndOrderCount")
	public String getEndOrderCount(HttpServletRequest request,
			HttpServletResponse response, @ModelAttribute CL_Feedback feedback)
			throws Exception {
		Integer userId;
		HashMap<String, Object> map = new HashMap<String, Object>();
		try {
			if (request.getParameter("userId") == null
					|| "".equals(request.getParameter("userId"))) {
				map.put("success", "false");
				map.put("msg", "未登录！");
				return writeAjaxResponse(response, JSONUtil.getJson(map));
			} else {
				userId = Integer.valueOf(request.getParameter("userId"));
			}
			CL_UserRole userRole = userRoleDao.getById(userId);
			map.put("success", "true");
			map.put("data",
					"[{\"endOrderTimes\":\"" + userRole.getEndOrderTimes()
							+ "\",\"rateTimes\":\"" + userRole.getRateTimes()
							+ "\"}]");
			return writeAjaxResponse(response, JSONUtil.getJson(map));
		} catch (Exception e) {
			map.put("success", "false");
			map.put("msg", "未登录！");
			return writeAjaxResponse(response, JSONUtil.getJson(map));
		}
	}

	//到达离开确认
	@RequestMapping("/app/order/TimeChangeByCar")
	public String TimeChangeByCar(HttpServletRequest request,
			HttpServletResponse response, @ModelAttribute CL_Feedback feedback)
			throws Exception {
		Integer orderId, type;
		HashMap<String, Object> map = new HashMap<String, Object>();
		if (request.getParameter("type") == null
				|| "".equals(request.getParameter("type"))) {
			map.put("success", "false");
			map.put("msg", "类型有误！");
			return writeAjaxResponse(response, JSONUtil.getJson(map));
		} else {
			type = Integer.valueOf(request.getParameter("type").toString());
		}
		if (request.getParameter("orderId") == null
				|| "".equals(request.getParameter("orderId"))) {
			map.put("success", "false");
			map.put("msg", "订单有误！");
			return writeAjaxResponse(response, JSONUtil.getJson(map));
		} else {
			orderId = Integer.valueOf(request.getParameter("orderId")
					.toString());
		}
		if (type == 1) {
			orderDao.updateByArriveTime(orderId, new Date());
		}
		if (type == 2) {
			orderDao.updateByLeaveTime(orderId, new Date());
		}
		map.put("success", "true");
		map.put("msg", "操作成功");
		return writeAjaxResponse(response, JSONUtil.getJson(map));
	}

	/** 锁定金额查询(一星期) */
	@RequestMapping("/app/order/lockedAmount")
	public String LockedAmount(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Integer urid = 0;
		HashMap<String, Object> map = new HashMap<String, Object>();
		if (request.getParameter("urid") == null
				|| "".equals(request.getParameter("urid"))) {
			map.put("success", "false");
			map.put("msg", "用户ID为空！");
			return writeAjaxResponse(response, JSONUtil.getJson(map));
		} else {
			urid = Integer.parseInt(request.getParameter("urid"));
		}
		// 根据司机ID查询订单表中所有的金额，条件大于当前日期减7
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar c = Calendar.getInstance();
		c.add(Calendar.DATE, -7);
		// Date fcopTime = c.getTime();
		Date fcopTime = sdf.parse((sdf.format(c.getTime())));
		String lockedMoney = orderDao.getLockedAmount(urid, fcopTime);
		map.put("success", "true");
		map.put("data", lockedMoney);
		return writeAjaxResponse(response, JSONUtil.getJson(map));
	}

	/**
	 * 电子回单上传接口
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/app/order/receiptSave")
	public String receiptSave(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		request.setCharacterEncoding("UTF-8");
		Integer userId, orderId, orderDetailId;
		HashMap<String, Object> map = new HashMap<String, Object>();
		if (request.getParameter("userId") == null || "".equals(request.getParameter("userId"))) {
			map.put("success", "false");
			map.put("msg", "未登录！");
			return writeAjaxResponse(response, JSONUtil.getJson(map));
		} else {
			userId = Integer.valueOf(request.getParameter("userId").toString());
		}
		if (request.getParameter("orderId") == null || "".equals(request.getParameter("orderId"))) {
			map.put("success", "false");
			map.put("msg", "订单数据有误,请联系客服！");
			return writeAjaxResponse(response, JSONUtil.getJson(map));
		} else {
			orderId = Integer.valueOf(request.getParameter("orderId").toString());
		}
		if (request.getParameter("orderDetailId") == null || "".equals(request.getParameter("orderDetailId"))) {
			map.put("success", "false");
			map.put("msg", "订单数据ID有误,请联系客服");
			return writeAjaxResponse(response, JSONUtil.getJson(map));
		} else {
			orderDetailId = Integer.parseInt(request.getParameter("orderDetailId"));
		}
		CL_OrderDetail orderDetail = orderDetailDao.getById(orderDetailId);
		if (orderDetail.getFstatus() == 0) {
			/*
			 * List<Cl_Upload>
			 * cupde=iuploadDao.getByOrderIdAndByMode(orderDetailId,
			 * "cl_receiptSave"); if(cupde.size()>0){
			 * iuploadDao.deleteByMode(cupde.get(0).getId(),
			 * cupde.get(0).getModelName()); }
			 */
			request.setCharacterEncoding("UTF-8");
			try {
				MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
				// 表单中对应的文件名；
				Map<String, MultipartFile> fileMap = multipartRequest
						.getFileMap();
				if(fileMap.entrySet().size() > 3){
					return this.poClient(response, false, "只允许上传三张");
				}
				for (Map.Entry<String, MultipartFile> item : fileMap.entrySet()) {
					MultipartFile multifile = item.getValue();
					SimpleDateFormat sf = new SimpleDateFormat("yyMMddHHmmssSSS");
					String fname = sf.format(new Date()) + "-" + multifile.getOriginalFilename();
					try {
						// 新增附件信息
						Cl_Upload up = new Cl_Upload();
						up.setName(fname);
						up.setUrl(FastDFSUtil.upload_could_change_file_url(multifile.getBytes(),FilenameUtils.getExtension(fname)));
						up.setParentId(orderDetailId);
						up.setRemark("回单上传,订单号:" + orderDao.getById(orderId).getNumber());
						up.setCreateTime(new Date());
						up.setModelName("cl_receiptSave");
						up.setCreateId(userId);
						this.iuploadDao.save(up);
						orderDetail.setFstatus(1);
						orderDetail.setFarrived_time(new Date());
						orderDetailDao.update(orderDetail);
					} catch (Exception e) {
						throw new RuntimeException(e);
					}
				}
				map.put("success", "true");
				map.put("msg", "操作成功！");
				return writeAjaxResponse(response, JSONUtil.getJson(map));
			} catch (Exception e) {
				throw new RuntimeException(e);
			}

		} else {
			map.put("success", "false");
			map.put("msg", "请勿重复操作！");
			return writeAjaxResponse(response, JSONUtil.getJson(map));
		}

	}
	
	/**
	 * 货主查看回单
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/app/order/receiptView")
	public String receiptView(HttpServletRequest request,
			HttpServletResponse response) {
		JSONArray jo = new JSONArray();
		String orderId = request.getParameter("orderId");
		String msg = String_Custom.parametersEmpty(new String[] { orderId });
		if (msg.length() != 0) {
			return this.poClient(response, false, msg);
		}
		List<CL_OrderDetail> orderDetailList = orderDetailDao
				.getByPCOrderId(Integer.parseInt(orderId));
		if (orderDetailList.size() > 0) {
			for (int i = 0; i < orderDetailList.size(); i++) {
				List<Cl_Upload> uploads = iuploadDao.getByOrderIdAndByMode(
						orderDetailList.get(i).getId(), "cl_receiptSave");
				if (uploads.size() > 0) {
					for (int j = 0; j < uploads.size(); j++) {
						jo.add(uploads.get(j));
					}
				}
			}
		}
		HashMap<String, Object> m = new HashMap<String, Object>();
		m.put("data", jo);
		m.put("success", "true");
		return writeAjaxResponse(response, JSONUtil.getJson(m));
	}

	/** 查询司机排名前三 */
	@RequestMapping("/app/order/carRankingLoad")
	public String carRankingLoad(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HashMap<String, Object> map = new HashMap<String, Object>();
		List<CL_CarRanking> rankingList = icarRankingDao.getAll();
		if (rankingList.size() > 0) {
			for (CL_CarRanking ras : rankingList) {
				ras.setFname(ras.getFname().substring(0, 1) + "师傅");
				ras.setFnumber(ras.getFnumber().substring(0,
						ras.getFnumber().length() - 2)
						+ "**");
				ras.setType(999);// 用于抢单列表特殊 显示
			}
		}
		map.put("success", "true");
		map.put("data", rankingList);
		System.out.println("-----------------返回排名----------------------");
		System.out.println(JSONUtil.getJson(map));
		return writeAjaxResponse(response, JSONUtil.getJson(map));
	}

	/** 保存订单增值服务 */
//	@RequestMapping("/app/order/fincrementServeSave")
//	public String fincrementServeSave(HttpServletRequest request,
//			HttpServletResponse response) throws Exception {
//		HashMap<String, String> m = new HashMap<String, String>();
//		try {
//			// 保存订单增值服务;
//			if (request.getParameter("orderId") != null
//					&& !"".equals(request.getParameter("orderId"))
//					&& !"null".equals(request.getParameter("orderId"))) {
//				int orderid = new Integer(request.getParameter("orderId"));
//				String payType = request.getParameter("payType");
//				CL_Order order = this.orderDao.getById(orderid);
//				order.setFpayMethod(new Integer(payType));
//				HashMap<String, Object> map = orderDao
//						.autoPayOrderFreight(order);
//				if (map.get("type").equals("0")) {
//					m.put("success", "true");
//					m.put("data", "{\"type\":\"0\"}");
//					return writeAjaxResponse(response, JSONUtil.getJson(m));
//				} else if (map.get("type").equals("1")) {
//					BigDecimal purposeAmount = new BigDecimal(0);
//					String fincrementServe = request
//							.getParameter("fincrementServe");
//					if (request.getParameter("fincrementServe") != null
//							&& !"".equals(request
//									.getParameter("fincrementServe"))) {
//						order.setFincrementServe(request
//								.getParameter("fincrementServe"));
//					}
//
//					if (request.getParameter("purposeAmount") != null
//							&& !"".equals(request.getParameter("purposeAmount"))) {
//						order.setPurposeAmount(BigDecimal.valueOf(Double
//								.parseDouble(request.getParameter(
//										"purposeAmount").toString())));
//					}
//					orderDao.update(order);
//					m.put("success", "true");
//					m.put("data", "{\"type\":\"1\"}");
//				}
//			}
//
//		} catch (Exception e) {
//			m.put("success", "false");
//			return writeAjaxResponse(response, JSONUtil.getJson(m));
//		}
//
//		return writeAjaxResponse(response, JSONUtil.getJson(m));
//	}

}