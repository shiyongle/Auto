package com.pc.appInterface.coupons;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.org.rapid_framework.page.Page;

import com.pc.controller.BaseController;
import com.pc.dao.UserRole.impl.UserRoleDao;
import com.pc.dao.clUpload.impl.UploadDao;
import com.pc.dao.coupons.ICouponsDao;
import com.pc.dao.couponsActivity.impl.CouponsActivityDaoImpl;
import com.pc.dao.couponsDetail.ICouponsDetailDao;
import com.pc.dao.message.ImessageDao;
import com.pc.dao.order.impl.OrderDao;
import com.pc.dao.orderDetail.impl.OrderDetailDao;
import com.pc.model.CL_Coupons;
import com.pc.model.CL_CouponsActivity;
import com.pc.model.CL_CouponsDetail;
import com.pc.model.CL_Order;
import com.pc.model.Cl_Upload;
import com.pc.query.coupons.CL_CouponsQuery;
import com.pc.query.couponsActivity.CouponsActivityQuery;
import com.pc.query.couponsDetail.CL_CouponsDetailQuery;
//import com.pc.util.CsclPushUtil;
import com.pc.util.JSONUtil;
import com.pc.util.ServerContext;
import com.pc.util.String_Custom;

@Controller
public class AppCouponsController extends BaseController {
	@Resource
	private ICouponsDao couponsDao;
	@Resource
	private ImessageDao messageDao;
	@Resource
	private ICouponsDetailDao couponsDetailDao;
	@Resource
	private OrderDao orderDao;
	@Resource
	private OrderDetailDao orderDetailDao;
	@Resource
	private CouponsActivityDaoImpl activityDao;
	@Resource
	private UserRoleDao userRoleDao;
	@Resource
	private UploadDao uploadDao;

	private CL_CouponsQuery couponsQuery;
	private CL_CouponsDetailQuery couponsDetailQuery;

	public CL_CouponsDetailQuery getCouponsDetailQuery() {
		return couponsDetailQuery;
	}

	public void setCouponsDetailQuery(CL_CouponsDetailQuery couponsDetailQuery) {
		this.couponsDetailQuery = couponsDetailQuery;
	}

	public CL_CouponsQuery getCouponsQuery() {
		return couponsQuery;
	}

	public void setCouponsQuery(CL_CouponsQuery couponsQuery) {
		this.couponsQuery = couponsQuery;
	}

	/**
	 * 优惠券弹窗
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/app/coupons/couponsUpView")
	public String couponsUpView(HttpServletRequest request, HttpServletResponse response){
		HashMap<String, Object> map = new HashMap<String, Object>();
		Integer userroleid;
		if (request.getParameter("userId") == null || "".equals(request.getParameter("userId"))) {
			map.put("success", "false");
			map.put("msg", "请先登录");
			return writeAjaxResponse(response, JSONUtil.getJson(map));
		} else {
			userroleid = Integer.parseInt(request.getParameter("userId"));
		}
		String userPhone = userRoleDao.getById(userroleid).getVmiUserPhone();
		Number count = activityDao.singleFindPageCount(userroleid, userPhone);
		if(count.intValue() > 0){
			List<Cl_Upload> uploadList = uploadDao.indexappByType(3);
			if(uploadList.size() > 0){
				Cl_Upload upload = uploadList.get(0);
				map.put("success", "true");
				map.put("data", upload);
			} else {
				Cl_Upload upload = new Cl_Upload();
				map.put("success", "true");
				map.put("data", upload);
			}
			return writeAjaxResponse(response, JSONUtil.getJson(map));
		} else {
			map.put("success", "false");
			return writeAjaxResponse(response, JSONUtil.getJson(map));
		}
	}
	
	
	/*** 好运券列表 */
	@RequestMapping("/app/coupons/getOrderCoupons")
	@Transactional
	public String getOrderCoupons(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String number = request.getParameter("number");
		String type = request.getParameter("type");//订单1 增值服务2

		String msg = String_Custom.parametersEmpty(new String[]{number,type});
		if (msg.length() != 0)
			return this.poClient(response, false, msg);

		// 获取用户id
		Integer userroleid = ServerContext.getUseronline().get(request.getSession().getId().toString()).getFuserId();

		// 根据订单编号 获取订单信息
		CL_Order order=orderDao.getByNumber(number);
		if(order==null)
			return this.poClient(response, false, "获取不到该订单信息");
		
		String area = null,carSpecId = null;
		if(number != null && !"".equals(number)){
			carSpecId = order.getCarSpec()+"";
			area = orderDetailDao.getByOrderId(order.getId(), 1).get(0).getAddressName().replace(" ", "").substring(6, 8);//类似“浙江省温州市瓯海区”，就截取“瓯海”
		}
		
		BigDecimal money = order.getFreight();
		
		List<CL_CouponsDetail> effectiveList=couponsDetailDao.getEffective2(userroleid,0,0,Integer.parseInt(type));
		
		Iterator<CL_CouponsDetail> iterator = effectiveList.iterator();
		while (iterator.hasNext()) {
			CL_CouponsDetail value = iterator.next();
			if(money.compareTo(value.getDollars()) ==-1)
				value.setStatus(1);//不符金额合条件的，用以前端灰显
			if(value.getFcarSpecId().indexOf(carSpecId) == -1)
				value.setStatus(1);//不符合车型条件的，用以前端灰显
			if(value.getFarea().indexOf(area) == -1)
				value.setStatus(1);//不符合地址条件的，用以前端灰显
			if(value.getFdiscount() != null){
				value.setSub(order.getFreight().subtract(order.getFreight().multiply(value.getFdiscount().divide(new BigDecimal(100)))).setScale(2, BigDecimal.ROUND_HALF_UP));
			} else {
				value.setSub(value.getFsubtract());
			}//前端取最大面额优惠券的时候要建立在status不是1的情况下
	    }  
		return this.poClient(response, true, JSONUtil.getJson(effectiveList), effectiveList.size());
	}

	/*** 好运券列表 */
	@RequestMapping("/app/coupons/load")
	public String load(HttpServletRequest request, HttpServletResponse response, @ModelAttribute CL_CouponsDetailQuery couponsDetailQuery) throws IOException {
		Integer userroleid;
		// 获取用户id
		userroleid = ServerContext.getUseronline().get(request.getSession().getId().toString()).getFuserId();

		String pagenum = request.getParameter("pagenum");
		String pagesize = request.getParameter("pagesize");
		String isUse = request.getParameter("isUse");
		String isOverDue = request.getParameter("isOverDue");
		String type = request.getParameter("type");
		String service = request.getParameter("service");
		String number = request.getParameter("number");

		if (String_Custom.noNull(pagesize))
			pagesize = "10";

		String pIsEmpty = String_Custom.parametersEmpty(new String[] { pagenum }, "缺少必要参数");
		if (pIsEmpty.length() != 0)
			return this.poClient(response, false, pIsEmpty);

		if (String_Custom.noNull(isOverDue))
			isOverDue = null;

		// 合法性检测
		if (String_Custom.noNumber(pagenum))
			return this.poClient(response, false, "页数错误!");
		if (String_Custom.noNumber(pagesize))
			return this.poClient(response, false, "页数数量错误!");

		couponsDetailQuery = newQuery(CL_CouponsDetailQuery.class, null);
		couponsDetailQuery.setPageNumber(Integer.parseInt(pagenum));
		couponsDetailQuery.setPageSize(Integer.parseInt(pagesize));
		if (isUse != null) {
			if (String_Custom.noNumber(isUse))
				return this.poClient(response, false, "isUse错误!");
			couponsDetailQuery.setIsUse(Integer.parseInt(isUse));
		}
		if (isOverDue != null) {
			if (String_Custom.noNumber(isOverDue))
				return this.poClient(response, false, "isOverDue错误!");
			couponsDetailQuery.setIsOverdue(Integer.parseInt(isOverDue));
		}
		if(type != null && !"".equals(type)){
			couponsDetailQuery.setFtype(Integer.parseInt(type));
		}
		if(number != null && !"".equals(number)){
			CL_Order order = orderDao.getByNumber(number);
			String carSpecId = order.getCarSpec()+"";
			String area = orderDetailDao.getByOrderId(order.getId(), 1).get(0).getAddressName().replace(" ", "").substring(6, 8);//类似“浙江省温州市瓯海区”，就截取“瓯海”
			
			BigDecimal money = order.getFreight();
			
			List<CL_CouponsDetail> effectiveList=couponsDetailDao.getEffective2(userroleid,0,0,Integer.parseInt(type));
			
			Iterator<CL_CouponsDetail> iterator = effectiveList.iterator();
			while (iterator.hasNext()) {
				CL_CouponsDetail value = iterator.next();
				if(money.compareTo(value.getDollars()) ==-1)
					value.setStatus(1);//不符金额合条件的，用以前端灰显
				if(value.getFcarSpecId().indexOf(carSpecId) == -1)
					value.setStatus(1);//不符合车型条件的，用以前端灰显
				if(value.getFarea().indexOf(area) == -1)
					value.setStatus(1);//不符合地址条件的，用以前端灰显
				if(value.getFdiscount() != null){
					value.setSub(order.getFreight().subtract(order.getFreight().multiply(value.getFdiscount().divide(new BigDecimal(100)))).setScale(2, BigDecimal.ROUND_HALF_UP));
				} else {
					value.setSub(value.getFsubtract());
				}//前端取最大面额优惠券的时候要建立在status不是1的情况下
		    }  
			return this.poClient(response, true, JSONUtil.getJson(effectiveList), effectiveList.size());
			
		} else {
			/*if("2".equals(type)){
			if(service == null || "".equals(service)){
				couponsDetailQuery.setFaddserviceName(" ");
			} else {
			}
		}*/
			couponsDetailQuery.setFaddserviceName(service);
			couponsDetailQuery.setCreator(userroleid);
			Page<CL_CouponsDetail> page = couponsDetailDao.findPage(couponsDetailQuery);
			return this.poClient(response, true, JSONUtil.getJson(page.getResult()), page.getTotalCount());
		}
	}

	/*** 优惠券领取列表 */
	@RequestMapping("/app/coupons/loadIndexCoupons")
	public String loadIndexCoupons(HttpServletRequest request, HttpServletResponse response, @ModelAttribute CouponsActivityQuery activityQuery) throws IOException {
		HashMap<String, Object> map = new HashMap<String, Object>();
		Integer userroleid, pageNum, pageSize;
		if (request.getParameter("userroleid") == null || "".equals(request.getParameter("userroleid"))) {
			map.put("success", "false");
			map.put("msg", "请先登录");
			return writeAjaxResponse(response, JSONUtil.getJson(map));
		} else {
			userroleid = Integer.parseInt(request.getParameter("userroleid"));
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
		activityQuery = newQuery(CouponsActivityQuery.class, null);
		if (pageNum != null) {
			activityQuery.setPageNumber(pageNum);
		}
		if (pageSize != null) {
			activityQuery.setPageSize(pageSize);
		}
		String userPhone = userRoleDao.getById(userroleid).getVmiUserPhone();
		if(userPhone == null || userPhone.length() == 0){
			activityQuery.setFissueUser("kong");//如果手机号为空则，强行置成无理字符串
		} else {
			activityQuery.setFissueUser(userPhone);//查询该用户的手机号是否在发放人群中
		}
		activityQuery.setCreator(userroleid);
		Page<CL_CouponsActivity> page = activityDao.singleFindPage(activityQuery);//查询用户未领取过的活动
		if(page.getTotalCount() == 0){
			map.put("success", "true");
			map.put("total", 0);
		} else {
			map.put("success", "true");
			map.put("total", page.getTotalCount());
			map.put("data", page.getResult());
		}
		return writeAjaxResponse(response, JSONUtil.getJson(map));
	}

	
	/**
	 * 领取优惠券
	 * @param request
	 * @param response
	 * @param id 活动的id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/app/coupons/oneKeyReceive")
	public String oneKeyReceive(HttpServletRequest request,
			HttpServletResponse response, Integer id) throws Exception {
		DateFormat fmtDateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar calendar = Calendar.getInstance();//初始化日历
		HashMap<String, Object> map = new HashMap<String, Object>();
		Integer userroleid;
		if (request.getParameter("userroleid") == null
				|| "".equals(request.getParameter("userroleid"))) {
			map.put("success", "false");
			map.put("msg", "请先登录");
			return writeAjaxResponse(response, JSONUtil.getJson(map));
		} else {
			userroleid = Integer.parseInt(request.getParameter("userroleid"));
		}

		if (couponsDetailDao.getCountByUserCoupon(userroleid, id) > 0) {
			map.put("success", "false");
			map.put("msg", "请勿重复领取");
			return writeAjaxResponse(response, JSONUtil.getJson(map));
		}
		CL_CouponsActivity activity = activityDao.getById(id);// 得到优惠券活动
		CL_Coupons coupons = couponsDao.getById(activity.getFcouponsId());// 得到优惠券模板
		CL_CouponsDetail d = new CL_CouponsDetail();
		d.setCouponsId(coupons.getId());
		d.setCouponsActivityId(id);
		d.setIsUse(0);// 未使用
		d.setIsOverdue(0);// 未过期
		d.setCreator(userroleid);
		d.setCreateTime(new Date());
		d.setDollars(activity.getFdollars());// 满足金额条件
		d.setFcarSpecId(activity.getFcarSpecId());
		d.setFarea(activity.getFarea());// 满足金额条件
		d.setFtype(coupons.getFtype());// 优惠券类型
		if (coupons.getFtype() == 1) {// 订单优惠券
			d.setFdiscount(coupons.getFdiscount());// 折扣
			d.setFsubtract(coupons.getFsubtract());// 直减
		} else {// 增值服务券
			d.setFaddserviceName(coupons.getFaddserviceName());
		}
		if (activity.getFgetType() != null) {
			if (activity.getFgetType() == 1) {// 领取后次日生效
				calendar.setTime(new Date(fmtDateTime.parse(fmtDateTime.format(new Date()).substring(0, 10)
						+ " 00:00:00").getTime()));
				calendar.add(Calendar.DAY_OF_MONTH, 1);
				d.setStartTime(calendar.getTime());
				calendar.add(Calendar.DAY_OF_MONTH, activity.getFuseTime());
				d.setEndTime(calendar.getTime());
			} else {// 领取后即时生效
				calendar.setTime(new Date());
				d.setStartTime(calendar.getTime());
				calendar.add(Calendar.DAY_OF_MONTH, activity.getFuseTime());
				d.setEndTime(calendar.getTime());
			}
		} else {
			d.setStartTime(activity.getFuseStartTime());
			d.setEndTime(activity.getFuseEndTime());
		}
		this.couponsDetailDao.save(d);
		map.put("success", "true");
		map.put("msg", "操作成功！");

		return writeAjaxResponse(response, JSONUtil.getJson(map));
	}

	/** 计算选完优惠券的运费 **/
	@RequestMapping("/app/coupons/getYfee")
	public String getYfee(HttpServletRequest request, HttpServletResponse response) throws IOException {
		HashMap<String, Object> map = new HashMap<String, Object>();
		BigDecimal fee;
		Integer couponsdetatilid;
		if (request.getParameter("fee") == null || "".equals(request.getParameter("fee"))) {
			map.put("success", "false");
			map.put("msg", "没有运费！");
			return writeAjaxResponse(response, JSONUtil.getJson(map));
		} else {
			fee = new BigDecimal(request.getParameter("fee"));
		}
		if (request.getParameter("id") == null || "".equals(request.getParameter("id"))) {
			map.put("success", "false");
			map.put("msg", "未选取优惠券");
			return writeAjaxResponse(response, JSONUtil.getJson(map));
		} else {
			couponsdetatilid = Integer.parseInt(request.getParameter("id"));
		}
		CL_CouponsDetail detail = this.couponsDetailDao.getById(couponsdetatilid);
		if(detail == null){
			map.put("success", "false");
			map.put("msg", "不存在该优惠券");
			return writeAjaxResponse(response, JSONUtil.getJson(map));
		}
		if (detail.getIsUse() == 1) {
			map.put("success", "false");
			map.put("msg", "已使用");
			return writeAjaxResponse(response, JSONUtil.getJson(map));
		} else if (detail.getIsOverdue() == 1) {
			map.put("success", "false");
			map.put("msg", "已过期");
			return writeAjaxResponse(response, JSONUtil.getJson(map));
		} else if (detail.getStartTime().after(new Date())){//优惠券使用期的尚未开始
			map.put("success", "false");
			map.put("msg", "该优惠券尚未可以使用");
			return writeAjaxResponse(response, JSONUtil.getJson(map));
		} else {
			
			// 20160906 cd 新增待付款订单校验后的好运券使用控制;
			List<CL_Order> orderlist = orderDao.getBycouponsDetailId(couponsdetatilid);
			for (CL_Order order : orderlist) {
				if (order.getStatus() > 0 && order.getStatus() != 9) {
					if (order.getStatus() == 1)
					{
						return this.poClient(response, false, "被订单"+order.getNumber()+"暂用,请取消该订单!");
					} else {
						return this.poClient(response, false, "已使用");
					}
				} else {
					//还原好运卷为未使用
//					detail.setIsUse(0);
//					couponsDetailDao.update(detail);
					order.setCouponsDetailId(null);
					orderDao.update(order);
				}
			}
			// 20160906 cd 新增待付款订单校验后的好运券使用控制;
				map.put("success", "true");
				if(detail.getFtype() == 1){
					if(detail.getFdiscount() != null){
						map.put("data", fee.subtract(fee.multiply(detail.getFdiscount().divide(new BigDecimal(100)))).setScale(2, BigDecimal.ROUND_HALF_UP));
					} else {
						map.put("data", detail.getFsubtract());
					}
				} else {
					map.put("success", "false");
					map.put("msg", "不存在对应优惠券");
				}

				// 增加提前记录订单好运券
				/*if (request.getParameter("orderId") != null && !"".equals(request.getParameter("orderId"))
						&& !"null".equals(request.getParameter("orderId"))) {
					int orderid = new Integer(request.getParameter("orderId"));
					orderDao.updatecouponsdetailByOrderId(couponsdetatilid, orderid);
				}*/
		}
		return writeAjaxResponse(response, JSONUtil.getJson(map));
	}
	
	/**
	 * 获取增值服务
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/app/coupons/getService")
	public String getService(HttpServletRequest request, HttpServletResponse response) throws IOException {
		HashMap<String, Object> map = new HashMap<String, Object>();
		Integer couponsdetatilid;
		if (request.getParameter("id") == null || "".equals(request.getParameter("id"))) {
			map.put("success", "false");
			map.put("msg", "未选取优惠券");
			return writeAjaxResponse(response, JSONUtil.getJson(map));
		} else {
			couponsdetatilid = Integer.parseInt(request.getParameter("id"));
		}
		CL_CouponsDetail detail = this.couponsDetailDao.getById(couponsdetatilid);
		if(detail == null){
			map.put("success", "false");
			map.put("msg", "不存在该优惠券");
			return writeAjaxResponse(response, JSONUtil.getJson(map));
		}
		if (detail.getIsUse() == 1) {
			map.put("success", "false");
			map.put("msg", "已使用");
			return writeAjaxResponse(response, JSONUtil.getJson(map));
		} else if (detail.getIsOverdue() == 1) {
			map.put("success", "false");
			map.put("msg", "已过期");
			return writeAjaxResponse(response, JSONUtil.getJson(map));
		} else if (detail.getStartTime().after(new Date())){//优惠券使用期的尚未开始
			map.put("success", "false");
			map.put("msg", "该优惠券尚未可以使用");
			return writeAjaxResponse(response, JSONUtil.getJson(map));
		} else {
			List<CL_Order> orderlist = orderDao.getBycouponsDetailId(couponsdetatilid);
			for (CL_Order order : orderlist) {
				if (order.getStatus() > 0 && order.getStatus() != 9) {
					if (order.getStatus() == 1)
					{
						return this.poClient(response, false, "被订单"+order.getNumber()+"暂用,请取消该订单!");
					} else {
						return this.poClient(response, false, "已使用");
					}
				} else {
					order.setCouponsDetailId(null);
					orderDao.update(order);
				}
			}
			
			map.put("success", "true");
			map.put("data", "免费" + detail.getFaddserviceName());
		}
		
		return writeAjaxResponse(response, JSONUtil.getJson(map));
	}
	
	/**
	 * 查询充值送活动
	 * @param response
	 * @param request
	 * @return
	 */
	@RequestMapping("/app/coupons/getTopUpActivity")
	public String getTopUpActivity(HttpServletResponse response, HttpServletRequest request){
		HashMap<String, Object> map = new HashMap<String, Object>();
		List<CL_CouponsActivity> list = activityDao.getAllTopUpActivity(1,1);
		map.put("success", true);
		map.put("data", list);
		return writeAjaxResponse(response, JSONUtil.getJson(map));
	}
	
	
}
