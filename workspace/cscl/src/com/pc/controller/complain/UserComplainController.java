package com.pc.controller.complain;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
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

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.org.rapid_framework.page.Page;

import com.pc.controller.BaseController;
import com.pc.controller.usercustomer.UserCustomerController;
import com.pc.dao.Car.ICarDao;
import com.pc.dao.UserRole.IUserRoleDao;
import com.pc.dao.addto.IaddtoDao;
import com.pc.dao.financeStatement.IFinanceStatementDao;
import com.pc.dao.order.IorderDao;
import com.pc.dao.orderDetail.IorderDetailDao;
import com.pc.dao.usercomplain.IComplainEntryDao;
import com.pc.dao.usercomplain.IUserComplainDao;
import com.pc.dao.usercustomer.IUserCustomerDao;
import com.pc.model.CL_Addto;
import com.pc.model.CL_Car;
import com.pc.model.CL_Complain;
import com.pc.model.CL_ComplainEntry;
import com.pc.model.CL_FinanceStatement;
import com.pc.model.CL_Order;
import com.pc.model.CL_OrderDetail;
import com.pc.model.CL_UserCustomer;
import com.pc.model.CL_UserRole;
import com.pc.query.financeStatement.FinanceStatementQuery;
import com.pc.query.usercomplain.UserComplainQuery;
import com.pc.util.CacheUtilByCC;
import com.pc.util.DJException;
import com.pc.util.JSONUtil;
import com.pc.util.pay.OrderMsg;

@Controller
public class UserComplainController extends BaseController {
	@Resource
	private IorderDao orderDao;
	@Resource
	private IUserRoleDao userRoleDao;
	@Resource
	private IUserComplainDao userComplainDao;
	@Resource
	private ICarDao carDao;
	@Resource
	private IorderDetailDao orderDetailDao;
	@Resource
	private IFinanceStatementDao financeStatementDao;
	@Resource
	private IComplainEntryDao complainentryDao;
	@Resource
	private IaddtoDao addtoDao;
	@Resource
	private IUserCustomerDao customerDao;
	
	protected static final String LIST_JSP= "/pages/pc/complain/complain_list.jsp";
	protected static final String CREATE_JSP = "/pages/pc/complain/addComplainOrder.jsp";
	protected static final String EDIT_JSP = "/pages/pc/complain/editComplainOrder.jsp";
	protected static final String DEAL_JSP = "/pages/pc/complain/dealComplainOrder.jsp";
	protected static final String REJECTLIST_JSP = "/pages/pc/complain/rejectList.jsp";
	protected static final String AUDIT_LIST_JSP = "/pages/pc/complain/auditList.jsp";
	protected static final String VIEW_JSP = "/pages/pc/complain/viewComplain.jsp";
	
	protected static final String APPENDCOMPLAIN_JSP = "/pages/pc/complain/appendComplainOrder.jsp";
	protected static final String PUBLICKEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDl5nbvmL8Q8tYGcJAgwS4qdqp2" +
			"Rwme5FKaR+11vXy89Biu8ruF/KmdS4pk+4gEmoPuHLFc6V6VQ77CgtpgboBDjveU" +
			"n3HnsN1N2LH/hmn8gDvw+0e7lLDFVEGC6L8d9z+yj0zGe0XMDeEW5zJlVCA2FOYq" +
			"oQAOkIntynv/nfyP6wIDAQAB";

	protected static final String PERSONALPUBLICKEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCcu9tqg+XyDB7qygFxn4UQu00T" +
			"WrnbQmIDUnE8zhDf9ZuCr5Czil1XVR4ApnpcVUTTXHoW2WpzBw1gm53OdKjgPc2Q" +
			"yRq+ROlo7NhYCRFan+b4p+RqGL+U+alMH1zv1Q+LSgQP6QF9loKAsC3i70KdBw4G" +
			"n7K8fTzoY8PtMqHlSwIDAQAB";
	
	/*** 表单提交日期绑定*/
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH");//写上你要的日期格式
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }
	
	@RequestMapping("/usercomplain/list")
	public String list(HttpServletRequest request, HttpServletResponse reponse) throws Exception {
		return LIST_JSP;		
	}
	//财务审核投诉界面
	@RequestMapping("/usercomplain/auditList")
	public String auditList(HttpServletRequest request,HttpServletResponse response) throws Exception{
		return AUDIT_LIST_JSP;
	}
	
	@RequestMapping("/usercomplain/add")
	public String add(HttpServletRequest request, HttpServletResponse reponse) throws Exception {
		return CREATE_JSP;
	}
	
	/**
	 * 跳转到追加投诉页面
	 * @param request
	 * @param reponse
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/usercomplain/appendcomplain")
	public String appendcomplain(HttpServletRequest request, HttpServletResponse reponse) throws Exception {
		String fid = request.getParameter("fid");
		String orderId = request.getParameter("orderId");
		request.setAttribute("fparentid",Integer.parseInt(fid));
		UserComplainQuery ucquery = newQuery(UserComplainQuery.class, null);
		ucquery.setForderId(Integer.parseInt(orderId));
		List<Map<String,Object>> orderList = userComplainDao.getOrderDropdown(ucquery);
		if(orderList.size()>0){			
			Map<String,Object> map = orderList.get(0);
			request.setAttribute("forderNum",map.get("forderNum").toString());
			request.setAttribute("fdriverName",map.get("fdriverName").toString());
			request.setAttribute("fuserName",map.get("fuserName").toString());
			request.setAttribute("shipperId",Integer.parseInt(map.get("shipperId").toString()));
			request.setAttribute("driverId",Integer.parseInt(map.get("driverId").toString()));
		}
		return APPENDCOMPLAIN_JSP;
	}
	
	@RequestMapping("/usercomplain/edit")
	public String edit(HttpServletRequest request, HttpServletResponse reponse,Integer id) throws Exception {
		CL_Complain ccinfo = userComplainDao.getById(id);
		UserComplainQuery ucquery =  new UserComplainQuery();
		ucquery.setForderId(ccinfo.getForderId());
		List<Map<String,Object>> orderList = userComplainDao.getOrderDropdown(ucquery);
		if(orderList.size()>0){
			ccinfo.setFuserName(orderList.get(0).get("fuserName").toString());
			ccinfo.setFdriverName(orderList.get(0).get("fdriverName").toString());
			CL_Order order = orderDao.getById(ccinfo.getForderId());
			CL_UserRole customer = userRoleDao.getById((order.getCreator()));
			CL_UserRole driver = userRoleDao.getById(order.getUserRoleId());
			ccinfo.setCusBalance(customer.getFbalance());//客户余额
			ccinfo.setDriBalance(driver.getFbalance());//司机余额
		}
		request.setAttribute("ccinfo", ccinfo);
		return EDIT_JSP;
	}
	
	/**
	 * 查看投诉受理的订单
	 * @param request
	 * @param reponse
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/usercomplain/view")
	public String view(HttpServletRequest request, HttpServletResponse reponse,Integer id) throws Exception {
		CL_Complain ccinfo = userComplainDao.getById(id);
		UserComplainQuery ucquery =  new UserComplainQuery();
		ucquery.setForderId(ccinfo.getForderId());
		List<Map<String,Object>> orderList = userComplainDao.getOrderDropdown(ucquery);
		if(orderList.size()>0){
			ccinfo.setFuserName(orderList.get(0).get("fuserName").toString());
			ccinfo.setFdriverName(orderList.get(0).get("fdriverName").toString());
			CL_Order order = orderDao.getById(ccinfo.getForderId());
			CL_UserRole customer = userRoleDao.getById(order.getCreator());
			CL_UserRole driver = userRoleDao.getById(order.getUserRoleId());
			ccinfo.setCusBalance(customer.getFbalance());
			ccinfo.setDriBalance(driver.getFbalance());
		}
		request.setAttribute("ccinfo", ccinfo);
		return VIEW_JSP;
	}
	
	@RequestMapping("/usercomplain/load")
	public String load(HttpServletRequest request, HttpServletResponse reponse,@ModelAttribute UserComplainQuery ucquery) throws Exception {
		String pageNum = request.getParameter("page");
		String pageSize = request.getParameter("rows");
		if (ucquery == null) {
			ucquery = newQuery(UserComplainQuery.class, null);
		}
		if (pageNum != null) {
			ucquery.setPageNumber(Integer.parseInt(pageNum));
		}
		if (pageSize != null) {
			ucquery.setPageSize(Integer.parseInt(pageSize));
		}
		Page<CL_Complain> page = userComplainDao.findPage(ucquery);
		HashMap<String, Object> m = new HashMap<String, Object>();
		m.put("total", page.getTotalCount());
		List<CL_Complain> resList = page.getResult();
		for(CL_Complain com:resList){
			ucquery.setForderId(com.getForderId());
			List<Map<String,Object>> orderList = userComplainDao.getOrderDropdown(ucquery);
			if(orderList.size()>0){
				Map<String,Object> map = orderList.get(0);
				com.setForderNum(map.get("forderNum").toString());
				com.setFuserName(map.get("fuserName")==null?"":map.get("fuserName").toString());
				com.setFdriverName(map.get("fdriverName")==null?"":map.get("fdriverName").toString());
				com.setFluckDriver(map.get("fluckDriver")!=null?map.get("fluckDriver").toString():"0");
			}
		}
		m.put("rows", page.getResult());
		return writeAjaxResponse(reponse, JSONUtil.getJson(m));
	}
	
	@RequestMapping("/usercomplain/saveComplain")
	public String  saveInfo(HttpServletRequest request,HttpServletResponse reponse,@ModelAttribute CL_Complain cominfo) throws Exception{
		cominfo.setFcreator(Integer.parseInt(request.getSession().getAttribute("userRoleId").toString()));
		cominfo.setFcreateTime(new Date());
		cominfo.setFisdeal(0);
		this.userComplainDao.save(cominfo);
		CL_ComplainEntry cpeinfo;
		int fparentid = cominfo.getFid();
		BigDecimal ZERO = BigDecimal.ZERO;
		BigDecimal hzfineAmount = cominfo.getFshipperFineAmount()!=null?cominfo.getFshipperFineAmount():ZERO;
		BigDecimal sjfineAmount = cominfo.getFdriverFineAmount()!=null?cominfo.getFdriverFineAmount():ZERO;
		BigDecimal sjrewardAmount = cominfo.getFdriverRewardAmount()!=null?cominfo.getFdriverRewardAmount():ZERO;
		int forderId = cominfo.getForderId();
		CL_Order coinfo = orderDao.getById(forderId);
		if(hzfineAmount.compareTo(ZERO)>0){
			cpeinfo = buildComplainEntry(fparentid,0,hzfineAmount,coinfo,cominfo.getFshipperRemark(),false);
			cpeinfo.setFcreator(Integer.parseInt(request.getSession().getAttribute("userRoleId").toString()));
			cpeinfo.setFcreateTime(new Date());
			complainentryDao.save(cpeinfo);
		}
		if(sjfineAmount.compareTo(ZERO)>0){
			cpeinfo = buildComplainEntry(fparentid,0,sjfineAmount,coinfo,cominfo.getFdriverRemark(),true);
			cpeinfo.setFcreator(Integer.parseInt(request.getSession().getAttribute("userRoleId").toString()));
			cpeinfo.setFcreateTime(new Date());
			complainentryDao.save(cpeinfo);
		}
		if(sjrewardAmount.compareTo(ZERO)>0){
			cpeinfo = buildComplainEntry(fparentid,1,sjrewardAmount,coinfo,cominfo.getFdriverRemark(),true);
			cpeinfo.setFcreator(Integer.parseInt(request.getSession().getAttribute("userRoleId").toString()));
			cpeinfo.setFcreateTime(new Date());
			complainentryDao.save(cpeinfo);
		}
		return writeAjaxResponse(reponse, "success");
	}
	
	@RequestMapping("/usercomplain/updateComplain")
	public String  updateInfo(HttpServletRequest request,HttpServletResponse reponse,@ModelAttribute CL_Complain cominfo) throws Exception{
		cominfo.setFcreator(Integer.parseInt(request.getSession().getAttribute("userRoleId").toString()));
		cominfo.setFcreateTime(new Date());
		this.userComplainDao.update(cominfo);
		return writeAjaxResponse(reponse, "success");
	}
	
	// 删除功能
	@RequestMapping("/usercomplain/del")
	@Transactional(propagation = Propagation.REQUIRED)
	public String del(HttpServletRequest request, HttpServletResponse reponse,Integer[] ids) throws Exception {
		for (Integer id : ids) {
			CL_Complain ccinfo = userComplainDao.getById(id);
			if(ccinfo.getFisdeal()==1){
				return writeAjaxResponse(reponse, "false");
			}
			userComplainDao.deleteById(id);
		}
		return writeAjaxResponse(reponse, "success");
	}
	
	
	//受理
	@RequestMapping("/usercomplain/audit")
	public String audit(HttpServletRequest request,
			HttpServletResponse response, Integer id) throws Exception {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		CL_Complain complain = userComplainDao.getById(id);
		CL_Order orinfo = orderDao.getById(complain.getForderId());
		CL_Car carinfo = carDao.getByUserRoleId(orinfo.getUserRoleId()).get(0);
		CL_UserRole userinfo = userRoleDao.getById(orinfo.getCreator());
		CL_UserRole driverinfo = userRoleDao.getById(orinfo.getUserRoleId());
		CL_UserCustomer cus = customerDao.getByUrid(userinfo.getId()).get(0);
		// 客户余额
		complain.setCusBalance(userinfo.getFbalance());
		// 司机余额
		complain.setDriBalance(driverinfo.getFbalance());

		List<CL_OrderDetail> odtlist = orderDetailDao.getByPCOrderId(complain
				.getForderId());
		// 订单编号
		complain.setOrderNumber(orinfo.getNumber());
		// 投诉类型
		complain.setComplainType(type(complain.getFcomplainType()));
		// 订单类型
		complain.setOrderType(orinfo.getType() == 1 ? "整车" : "零担");
		// 订单装车时间
		complain.setOrderLoadTime(df.format(orinfo.getLoadedTime()));
		// 订单货物类型
		complain.setOrderGoodType(orinfo.getGoodsTypeName());
		if (carinfo != null) {
			// 承运车型
			complain.setCarType(carinfo.getCarTypeName());
			// 司机名称、电话
			complain.setDriverName(carinfo.getDriverName());
			complain.setDriverPhone(driverinfo.getVmiUserPhone());
		}
		// 增值服务
		if (orinfo.getFincrementServe() != null) {
			complain.setOrderAddServer(orinfo.getFincrementServe()
					.replace("1", "装卸").replace("2", "电子回单").replace("3", "上楼")
					.replace("4", "代收货款"));
		}
		// 里程
		complain.setOrderMileage(orinfo.getMileage().toString());
		// 发货人运费计算
		FinanceStatementQuery fsquery = new FinanceStatementQuery();
		fsquery.setForderid(orinfo.getNumber());
		/*List<CL_FinanceStatement> finlist = financeStatementDao.find(fsquery);
		if (finlist.size() > 0) {
			BigDecimal pay = BigDecimal.ZERO;
			for (CL_FinanceStatement fsinfo : finlist) {
				// 1下单支付2补交货款 3运营异常4订单完成5提现6充值
				if (fsinfo.getFbusinessType() < 3 && fsinfo.getFstatus() == 1 
						&& fsinfo.getFuserroleId() == orinfo.getCreator()) {
					pay = pay.add(fsinfo.getFamount());
				}
			}
			// 运费
*/			complain.setOrderPayAmount(orinfo.getFtotalFreight().toString());
//		}
		// 客户类型
		complain.setUserType(orinfo.getProtocolType() == 1 ? "协议客户" : "");
		complain.setUserName(cus.getFname());
		complain.setUserPhone(userinfo.getVmiUserPhone());
		if (odtlist != null && odtlist.size() > 0) {
			for (CL_OrderDetail ordInfo : odtlist) {
				// 提货点 1，发货人 2，收货方
				if (ordInfo.getDetailType() == 1) {
					complain.setSendAddress(ordInfo.getAddressName());
				}
				if (ordInfo.getDetailType() == 2) {
					complain.setReceiveAddress(ordInfo.getAddressName());
					complain.setReceiveUser(ordInfo.getLinkman());
					complain.setReceivePhone(ordInfo.getPhone());
				}
			}
		}
		request.setAttribute("complain", complain);
		return DEAL_JSP;
	}
	
	// 受理
	@RequestMapping("/usercomplain/deal")
	public String deal(HttpServletRequest request, HttpServletResponse reponse) throws DJException {
		int cus = 0;
		int fisdeal = Integer.parseInt(request.getParameter("fisdeal"));//受理状态
		CL_Complain ccinfo = userComplainDao.getById(Integer.parseInt(request.getParameter("fid")));
		HashMap<String, Object> map = new HashMap<String, Object>();
		HashMap<String, Object> param = new HashMap<String, Object>();
		String exceptionStr = "";
		if(fisdeal==1){//审核通过
//************************************如果需要系统操众余额则开放********************************************				
			CL_Order coinfo = orderDao.getById(ccinfo.getForderId());
			//货主
			CL_UserRole hzinfo = userRoleDao.getById(coinfo.getCreator());
			//司机
			CL_UserRole sjinfo = userRoleDao.getById(coinfo.getUserRoleId());	
			String sjUsename = sjinfo.getVmiUserPhone();
			String hzUsename = hzinfo.getVmiUserPhone();
			
			//货主扣款
			//返还金额
			BigDecimal hzfineAmount = ccinfo.getFshipperFineAmount()!=null?ccinfo.getFshipperFineAmount():BigDecimal.ZERO;
			/*List<CL_Addto> addto=addtoDao.getByOrderId(coinfo.getId());//查询当前所有支付成功的追加费用
			BigDecimal AllCost=coinfo.getFreight();//初始费用取订单费用，总费=订单运费+所有追加运费  BY CC 2016-05-18
			if(addto.size()>0){
				for(CL_Addto ad:addto){
		    		AllCost=AllCost.add(ad.getFcost());//运费加追加费用=客户总费用
		    	}
			}*/
			//2016-5-20   货主扣款  -> 实际是退款    总运费 - 扣款金额     
			BigDecimal calAmount = coinfo.getFtotalFreight().subtract(hzfineAmount);//实付费用-返还金额
			//返还给货主的金额  应该大于0
			if(calAmount.compareTo(BigDecimal.ZERO) < 0){
				exceptionStr = "退返运费不能大于订单运费！";
			}
			//2016年6月7日 逻辑调整为退返运费
			calAmount = hzfineAmount;
			
			//司机扣款
			BigDecimal sjfineAmount = ccinfo.getFdriverFineAmount()!=null?ccinfo.getFdriverFineAmount():BigDecimal.ZERO;		
			//司机返利
			BigDecimal sjrewardAmount = ccinfo.getFdriverRewardAmount()!=null?ccinfo.getFdriverRewardAmount():BigDecimal.ZERO;
			
			//金额大于0 小于 1000控制    提高安全性	
			if(unNormal(sjfineAmount) || unNormal(sjrewardAmount) || unNormal(calAmount)){
				exceptionStr = "当笔处理金额必须大于0小于1000！";			
			}	
			if(!"".equals(exceptionStr)){
				map.put("success", "false");
				map.put("msg", exceptionStr);
				return writeAjaxResponse(reponse,JSONUtil.getJson(map));
			}
			//2016年6月2日   统一先扣款，再返利，防止返利了款没扣成
				if(sjfineAmount.compareTo(BigDecimal.ZERO)>0){//司机扣款
					CL_FinanceStatement statement = this.sta(ccinfo, sjinfo, sjfineAmount, -1);
					financeStatementDao.save(statement);
					OrderMsg orderMsg = new OrderMsg();
					orderMsg.setOrder(statement.getNumber());
					orderMsg.setPayState("1");
					orderMsg.setServiceProviderType("0");
					orderMsg.setServiceProvider("0");
					orderMsg.setUserId(Integer.parseInt(request.getSession().getAttribute("userRoleId").toString()));
					param.put("complain", ccinfo);
					cus = financeStatementDao.updateBusinessType(orderMsg,param);
					if(sjinfo.getFbalance().compareTo(sjfineAmount) < 0){
						map.put("success", "false");
						map.put("msg", "司机余额不足");
						return writeAjaxResponse(reponse,JSONUtil.getJson(map));
					}
					if(cus == 0){
						map.put("success", "false");
						map.put("msg", "审核失败");
						return writeAjaxResponse(reponse,JSONUtil.getJson(map));
					}
				}
				if(calAmount.compareTo(BigDecimal.ZERO)>0){//货主返利
					CL_FinanceStatement statement = this.sta(ccinfo, hzinfo, calAmount, 1);
					financeStatementDao.save(statement);
					OrderMsg orderMsg = new OrderMsg();
					orderMsg.setOrder(statement.getNumber());
					orderMsg.setPayState("1");
					orderMsg.setServiceProviderType("0");
					orderMsg.setServiceProvider("0");
					orderMsg.setUserId(Integer.parseInt(request.getSession().getAttribute("userRoleId").toString()));
					param.put("complain", ccinfo);
					cus = financeStatementDao.updateBusinessType(orderMsg,param);
					if(cus == 0){
						map.put("success", "false");
						map.put("msg", "审核失败");
						return writeAjaxResponse(reponse,JSONUtil.getJson(map));
					}
				}
				if(sjrewardAmount.compareTo(BigDecimal.ZERO)>0){//司机返利			
					CL_FinanceStatement statement = this.sta(ccinfo, sjinfo, sjrewardAmount, 1);
					financeStatementDao.save(statement);
					OrderMsg orderMsg = new OrderMsg();
					orderMsg.setOrder(statement.getNumber());
					orderMsg.setPayState("1");
					orderMsg.setServiceProviderType("0");
					orderMsg.setServiceProvider("0");
					orderMsg.setUserId(Integer.parseInt(request.getSession().getAttribute("userRoleId").toString()));
					param.put("complain", ccinfo);
					cus = financeStatementDao.updateBusinessType(orderMsg,param);				
				}
				if(cus == 1){
					exceptionStr="审核成功！";
					map.put("success", "success");
				} else {
					exceptionStr="审核失败！";
					map.put("success", "false");
				}
		}else {//驳回
			String fremark = request.getParameter("fremark");
			ccinfo.setFremark(fremark);
			ccinfo.setFisdeal(2);
			exceptionStr="已驳回！";
			ccinfo.setFdealMan(Integer.parseInt(request.getSession().getAttribute("userRoleId").toString()));
			ccinfo.setFdealTime(new Date());
			userComplainDao.update(ccinfo);
			map.put("success", "success");
		}
		map.put("msg", exceptionStr);
		return writeAjaxResponse(reponse,JSONUtil.getJson(map));
	}
	
	@RequestMapping("/usercomplain/getOrderDropdown")
	public String getOrderDropdown(HttpServletRequest request, HttpServletResponse reponse) throws Exception {
		 List<Map<String, Object>> List = userComplainDao.getOrderDropdown();
		 return writeAjaxResponse(reponse, JSONUtil.getJson(List)); 
	}
			 
	//根据订单号找到 投诉加载的信息
	@RequestMapping("/usercomplain/loadOrderById")
	public String loadOrderById(HttpServletRequest request, HttpServletResponse reponse) throws Exception {
		//List<HashMap<String,Object>> resList = new ArrayList<HashMap<String,Object>> ();
		SimpleDateFormat  df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		HashMap<String,Object> resmap = new HashMap<String,Object>();
		int orderId = Integer.parseInt(request.getParameter("orderId"));
		List<CL_OrderDetail> odtlist = orderDetailDao.getByPCOrderId(orderId);
		CL_Order orinfo = orderDao.getById(orderId);
		CL_UserRole customer = userRoleDao.getById(orinfo.getCreator());
		CL_UserRole driver = userRoleDao.getById(orinfo.getUserRoleId());
		CL_Car carinfo = carDao.getByUserRoleId(driver.getId()).get(0);
		CL_UserCustomer userinfo = customerDao.getByUrid(customer.getId()).get(0);
		resmap.put("cusBalance", customer.getFbalance());
		resmap.put("driBalance", driver.getFbalance());
		//订单编号
		resmap.put("orderNumber", orinfo.getNumber());
		//订单类型
		resmap.put("orderType", orinfo.getType()==1?"整车":"零担");
		//订单装车时间
		resmap.put("orderLoadTime", df.format(orinfo.getLoadedTime()));
		//订单货物类型
		resmap.put("orderGoodType", orinfo.getGoodsTypeName());
		if(carinfo!=null){
			//承运车型
			resmap.put("carType",carinfo.getCarTypeName());
			//司机名称、电话
			resmap.put("driverName", carinfo.getDriverName());
			resmap.put("driverPhone",driver.getVmiUserPhone());
		}
		//增值服务
		resmap.put("orderAddServer",orinfo.getFincrementServe());
		//里程
		resmap.put("orderMileage", orinfo.getMileage());
		
		//发货人运费计算
		FinanceStatementQuery fsquery = new FinanceStatementQuery();
//		fsquery.setForderid(orinfo.getNumber());
//		List<CL_FinanceStatement> finlist = financeStatementDao.find(fsquery);
//		if(finlist.size()>0){
//			BigDecimal pay = BigDecimal.ZERO;
//			for(CL_FinanceStatement fsinfo:finlist){
//				//1下单支付2补交货款   3运营异常4订单完成5提现6充值
//				if(fsinfo.getFbusinessType()<3 && fsinfo.getFstatus() == 1 && fsinfo.getFuserroleId()==orinfo.getCreator()){
//					pay = pay.add(fsinfo.getFamount());
//				}
//			}
//			//运费
			resmap.put("orderPayAmount", orinfo.getFtotalFreight());
//		}
		//客户类型
		resmap.put("userType",orinfo.getProtocolType()==1?"协议客户":"");
		resmap.put("userName", userinfo.getFname());
		resmap.put("userPhone", customer.getVmiUserPhone());
		if(odtlist!=null && odtlist.size()>0){
			for(CL_OrderDetail ordInfo:odtlist){
				//提货点 1，发货人  2，收货方
				if(ordInfo.getDetailType()==1){
					resmap.put("sendAddress", ordInfo.getAddressName());
				}
				if(ordInfo.getDetailType()==2){
					resmap.put("receiveAddress",ordInfo.getAddressName());
					resmap.put("receiveUser",ordInfo.getLinkman());
					resmap.put("receivePhone",ordInfo.getPhone());
				}
			}
		}		
		return writeAjaxResponse(reponse, JSONUtil.getJson(resmap));
	}	
	
	
	/**
	 * 异常扣款参数调用支付接口参数构建
	 */
	

	//构建投诉明细通用方法1-------isdriver为true表示相关方位司机
	private CL_ComplainEntry buildComplainEntry(int fparentid,int ftype,BigDecimal famount,CL_Order coinfo,String fremark,boolean isdriver ){
		CL_ComplainEntry cpeinfo = new CL_ComplainEntry();
		cpeinfo.setFparentid(fparentid);
		cpeinfo.setFtype(ftype);
		cpeinfo.setFamount(famount);
		cpeinfo.setFuserid(coinfo.getCreator());
		cpeinfo.setFremark(fremark);
		if(isdriver){
			cpeinfo.setFuserid(coinfo.getUserRoleId());
		}		
		return cpeinfo;
	}
	
    //构建投诉明细通用方法2-------isdriver为true表示相关方位司机
	private CL_ComplainEntry buildComplainEntry(int fparentid,int ftype,BigDecimal famount,int fuserid,String fremark,boolean isdriver ){
		CL_ComplainEntry cpeinfo = new CL_ComplainEntry();
		cpeinfo.setFparentid(fparentid);
		cpeinfo.setFtype(ftype);
		cpeinfo.setFamount(famount);
		cpeinfo.setFuserid(fuserid);	
		cpeinfo.setFremark(fremark);
		return cpeinfo;
	}
	
	//2016-5-9        追加投诉 
	//2016/12/20	已修改，逻辑待确认，by lancher
	//追加投诉暂不开放  2016/12/24 by lancher
	/*@RequestMapping("/usercomplain/addComplainEntry")
	public String addComplainEntry(HttpServletRequest request,
			HttpServletResponse reponse) throws Exception {
		int cus = 0;
		String exceptionStr;
		String fparentid = request.getParameter("fparentid");
		String shipperId = request.getParameter("shipperId");
		String driverId = request.getParameter("driverId");
		BigDecimal fdriverF = BigDecimal.ZERO;
		BigDecimal fdriverR = BigDecimal.ZERO;
		BigDecimal fuserF = BigDecimal.ZERO;
		// 司机追加扣款
		String fdriverAddFAmount = request.getParameter("fdriverAddFAmount");
		// 司机追加返利
		String fdriverAddRAmount = request.getParameter("fdriverAddRAmount");
		// 货主追加扣款
		String fuserAddFAmount = request.getParameter("fuserAddFAmount");
		// 司机备注
		String fdriverAddRemark = request.getParameter("fdriverAddRemark");
		// 货主备注
		String fuserAddRemark = request.getParameter("fuserAddRemark");
		CL_Complain cpinfo = userComplainDao.getById(Integer
				.parseInt(fparentid));
		CL_Order coinfo = orderDao.getById(cpinfo.getForderId());
		CL_UserRole userinfo = userRoleDao.getById(Integer.parseInt(shipperId));
		CL_UserRole driverinfo = userRoleDao
				.getById(Integer.parseInt(driverId));

		CL_ComplainEntry cpeinfo;

		// 2016年6月2日 统一先扣款，再返利，防止返利了款没扣成---------货主追加扣款，司机扣款，司机返利
		if (unNormal(fuserF) || unNormal(fdriverR) || unNormal(fdriverF)) {
			exceptionStr = "当笔处理金额必须大于0小于1000！";
			return writeAjaxResponse(reponse, "{\"flag\":\"false\",\"msg\":\""
					+ exceptionStr + "\"}");
		}
		if (fuserF.compareTo(BigDecimal.ZERO) > 0) {
			// 逻辑需要再问清楚，客户到底是返利还是扣款
			CL_FinanceStatement statement = this.sta(coinfo, userinfo, fuserF,
					-1);//新增审批时时返利，追加时时扣款
			financeStatementDao.save(statement);
			OrderMsg orderMsg = new OrderMsg();
			orderMsg.setOrder(statement.getNumber());
			orderMsg.setPayState("1");
			orderMsg.setServiceProviderType("0");
			cus = financeStatementDao.updateBusinessType(orderMsg);
		}
		if (fdriverF.compareTo(BigDecimal.ZERO) > 0) {
			CL_FinanceStatement statement = this.sta(coinfo, driverinfo,
					fdriverF, -1);
			financeStatementDao.save(statement);
			OrderMsg orderMsg = new OrderMsg();
			orderMsg.setOrder(statement.getNumber());
			orderMsg.setPayState("1");
			orderMsg.setServiceProviderType("0");
			cus = financeStatementDao.updateBusinessType(orderMsg);
		}
		if (fdriverR.compareTo(BigDecimal.ZERO) > 0) {
			CL_FinanceStatement statement = this.sta(coinfo, driverinfo,
					fdriverR, 1);
			financeStatementDao.save(statement);
			OrderMsg orderMsg = new OrderMsg();
			orderMsg.setOrder(statement.getNumber());
			orderMsg.setPayState("1");
			orderMsg.setServiceProviderType("0");
			cus = financeStatementDao.updateBusinessType(orderMsg);
		}
		if (cus == 1) {
			
			// 2016年6月2日 统一先扣款，再返利，防止返利了款没扣成---------货主追加扣款，司机扣款，司机返利
			if (fuserAddFAmount != null && !"".equals(fuserAddFAmount)) {
				fuserF = new BigDecimal(fuserAddFAmount);
				cpeinfo = buildComplainEntry(Integer.parseInt(fparentid), 1,
						fuserF, Integer.parseInt(shipperId), fuserAddRemark, true);
				cpeinfo.setFcreator(Integer.parseInt(request.getSession()
						.getAttribute("userRoleId").toString()));
				cpeinfo.setFcreateTime(new Date());
				complainentryDao.save(cpeinfo);
			}
			if (fdriverAddFAmount != null && !"".equals(fdriverAddFAmount)) {
				fdriverF = new BigDecimal(fdriverAddFAmount);
				cpeinfo = buildComplainEntry(Integer.parseInt(fparentid), 0,
						fdriverF, Integer.parseInt(driverId), fdriverAddRemark,
						true);
				cpeinfo.setFcreator(Integer.parseInt(request.getSession()
						.getAttribute("userRoleId").toString()));
				cpeinfo.setFcreateTime(new Date());
				complainentryDao.save(cpeinfo);
			}
			if (fdriverAddRAmount != null && !"".equals(fdriverAddRAmount)) {
				fdriverR = new BigDecimal(fdriverAddRAmount);
				cpeinfo = buildComplainEntry(Integer.parseInt(fparentid), 1,
						fdriverR, Integer.parseInt(driverId), fdriverAddRemark,
						true);
				cpeinfo.setFcreator(Integer.parseInt(request.getSession()
						.getAttribute("userRoleId").toString()));
				cpeinfo.setFcreateTime(new Date());
				complainentryDao.save(cpeinfo);
			}
			
			// 更新表头
			cpinfo.setFdriverFineAmount(cpinfo.getFdriverFineAmount() == null ? fdriverF
					: cpinfo.getFdriverFineAmount().add(fdriverF));
			cpinfo.setFdriverRewardAmount(cpinfo.getFdriverRewardAmount() == null ? fdriverR
					: cpinfo.getFdriverRewardAmount().add(fdriverR));
			cpinfo.setFshipperFineAmount(cpinfo.getFshipperFineAmount() == null ? fuserF
					: cpinfo.getFshipperFineAmount().add(fuserF));
			userComplainDao.updateAcount(cpinfo);
			
			exceptionStr = "追加成功！";
			return writeAjaxResponse(reponse, "{\"flag\":\"success\",\"msg\":\""
					+ exceptionStr + "\"}");
		} else {
			exceptionStr = "追加失败！";
			return writeAjaxResponse(reponse, "{\"flag\":\"false\",\"msg\":\""
					+ exceptionStr + "\"}");
		}
	}*/
	
	//2016-5-10  根据投诉头ID获得所有明细信息     
	@RequestMapping("/usercomplain/getEntrysByParentId")
	public String  getEntrysByParentId(HttpServletRequest request,HttpServletResponse reponse) throws Exception{
		String fid = request.getParameter("fid");
		List<Map<String,Object>> resList = userComplainDao.getEntrysByParentId(Integer.parseInt(fid));
		return writeAjaxResponse(reponse, JSONUtil.getJson(resList));
	}
	
	
	protected boolean unNormal(BigDecimal amount){
		boolean flag = false;
		if(amount.compareTo(new BigDecimal("1000"))>0 || amount.compareTo(BigDecimal.ZERO)<0){
			flag = true;
		}
		return flag;
	}
	
	public String type(int type){
		switch (type) {
		case -1:
			return "请选择";
		case 1:
			return "行为不良";
		case 2:
			return "服务不好";
		case 3:
			return "货物破损";
		case 4:
			return "货物丢失";
		case 5:
			return "车辆迟到";
		case 6:
			return "回单异常(货主)";
		case 7:
			return "回单异常(司机)";
		case 8:
			return "其他";
		default:
			return "新增未定";
		}
	}
	
	/**导出excel**/
	@RequestMapping("/usercomplain/exportExecl")
	public String exportExecl(HttpServletRequest request,HttpServletResponse reponse,@ModelAttribute UserComplainQuery ucquery,Integer[] ids) throws Exception{
		boolean ises =true;
		/***************/
		Map<String, Object> result = new HashMap<String, Object>();
		WritableWorkbook wwb = null;
		OutputStream os = null;
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
		String fileName = "cps_"+format.format(new Date())+".xls";
		String path  = UserCustomerController.class.getResource("").toURI().getPath();
		path = path.substring(0,path.lastIndexOf("/WEB-INF"))+"/excel/"+fileName;
		try {
			File file = new File(path);
			if(!file.isFile()){
				file.createNewFile();
			}
			os = new FileOutputStream(file);
			wwb = Workbook.createWorkbook(os);
			WritableSheet wsheet = wwb.createSheet("投诉受理", 0);//创建一个工作页，第一个参数的页名，第二个参数表示该工作页在excel中处于哪一页
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
            int[] headerArrHight = {7,25,10,10,25,10,10,10,15,15,30,10,10,35,35};
            String headerArr[] = {"序号","订单编号","投诉人","是否受理","司机","好运司机","账号禁用","账号冻结","扣除金额","返利金额","货主","退返运费","好运券发放","投诉记录","沟通记录"};
            for (int i = 0; i < headerArr.length; i++) {
            	wsheet.addCell(new Label( i , 1 , headerArr[i],wcf));
            	wsheet.setColumnView(i, headerArrHight[i]);
            }
            if (ucquery == null) {
            	ucquery = newQuery(UserComplainQuery.class, null);
    		}
            /*ucquery.setSortColumns("fcreateTime DESC ");*/
    		List<CL_Complain> list = this.userComplainDao.find(ucquery);
    		
    		
    		for(CL_Complain com:list){
    			ucquery.setForderId(com.getForderId());
    			List<Map<String,Object>> orderList = userComplainDao.getOrderDropdown(ucquery);
    			if(orderList.size()>0){
    				Map<String,Object> map = orderList.get(0);
    				com.setForderNum(map.get("forderNum").toString());
    				com.setFuserName(map.get("fuserName").toString());
    				com.setFdriverName(map.get("fdriverName").toString());
    				com.setFluckDriver(map.get("fluckDriver")!=null?map.get("fluckDriver").toString():"0");
    			}
    		}
    		
    		
    		int conut = 2;
    		for(int i=0;i<list.size();i++){
    			jxl.write.NumberFormat nf2 = new jxl.write.NumberFormat("0.00");//设置数字格式
    			jxl.write.WritableCellFormat wcfN2 = new jxl.write.WritableCellFormat(nf2);//设置表单格式   
    			wcfN2.setBackground(Colour.LIGHT_ORANGE);
    			wcfN2.setAlignment(Alignment.CENTRE);                  //平行居中
    			wcfN2.setVerticalAlignment(VerticalAlignment.CENTRE);  //垂直居中
    			wsheet.addCell(new Label( 0 , conut ,String.valueOf(i+1),wcf2));
    			wsheet.addCell(new Label( 1 , conut ,list.get(i).getForderNum(),wcf2));
    			wsheet.addCell(new Label( 2 , conut ,list.get(i).getFcomplainUserName(),wcf2));
    			wsheet.addCell(new Label( 3 , conut ,list.get(i).getFisdeal()==1?"是":"否",wcf2));
    			wsheet.addCell(new Label( 4 , conut ,list.get(i).getFdriverName(),wcf2));
    			wsheet.addCell(new Label( 5 , conut ,"0".equals(list.get(i).getFluckDriver())?"否":"是",wcf2));
    			wsheet.addCell(new Label( 6 , conut ,list.get(i).isFdriverAccountDisable()?"是":"否" ,wcf2));
    			wsheet.addCell(new Label( 7 , conut ,list.get(i).isFdriverFreezeDisable()?"是":"否" ,wcf2));
    			wsheet.addCell(new Label( 8 , conut ,list.get(i).getFdriverFineAmount()==null?"":list.get(i).getFdriverFineAmount().toString() ,wcf2));
    			wsheet.addCell(new Label( 9 , conut ,list.get(i).getFdriverRewardAmount()==null?"":list.get(i).getFdriverRewardAmount().toString() ,wcf2));
    			wsheet.addCell(new Label( 10 , conut ,list.get(i).getFuserName() ,wcf2));
    			wsheet.addCell(new Label( 11 , conut ,list.get(i).getFshipperFineAmount()==null?"":list.get(i).getFshipperFineAmount().toString() ,wcf2));
    			wsheet.addCell(new Label( 12 , conut ,list.get(i).getFshipperRewardCouponsAmount()==null?"":list.get(i).getFshipperRewardCouponsAmount().toString() ,wcf2));
    			wsheet.addCell(new Label( 13 , conut ,list.get(i).getFcomplainContent() ,wcf2));
    			wsheet.addCell(new Label( 14 , conut ,list.get(i).getFcomplainCommunicateContent() ,wcf2));
    			conut++;
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
	
	//异常驳回
	@RequestMapping("/usercomplain/reject")
	public String reject(HttpServletRequest request,HttpServletResponse response)throws Exception{
		return REJECTLIST_JSP;
	}
	
	@RequestMapping("/usercomplain/rejectList")
	public String rejectLoad(HttpServletRequest request,HttpServletResponse response,@ModelAttribute UserComplainQuery ucquery)throws Exception{
		String pageNum = request.getParameter("page");
		String pageSize = request.getParameter("rows");
		if (ucquery == null) {
			ucquery = newQuery(UserComplainQuery.class, null);
		}
		if (pageNum != null) {
			ucquery.setPageNumber(Integer.parseInt(pageNum));
		}
		if (pageSize != null) {
			ucquery.setPageSize(Integer.parseInt(pageSize));
		}
		ucquery.setSortColumns("cc.fisdeal = 2");
		Page<CL_Complain> page = userComplainDao.findPage(ucquery);
		HashMap<String, Object> m = new HashMap<String, Object>();
		m.put("total", page.getTotalCount());
		List<CL_Complain> resList = page.getResult();
		for(CL_Complain com:resList){
			ucquery.setForderId(com.getForderId());
			List<Map<String,Object>> orderList = userComplainDao.getOrderDropdown(ucquery);
			if(orderList.size()>0){
				Map<String,Object> map = orderList.get(0);
				com.setForderNum(map.get("forderNum").toString());
				com.setFuserName(map.get("fuserName")==null?"":map.get("fuserName").toString());
				com.setFdriverName(map.get("fdriverName")==null?"":map.get("fdriverName").toString());
				com.setFluckDriver(map.get("fluckDriver")!=null?map.get("fluckDriver").toString():"0");
			}
		}
		m.put("rows", page.getResult());
		return writeAjaxResponse(response, JSONUtil.getJson(m));
	}
	
	/**
	 * 生成明细公共方法
	 * @param order调整运费的订单
	 * @param user被调整的用户
	 * @param fee调整的费用
	 * @param ftype收支情况
	 * @return
	 */
	public CL_FinanceStatement sta(CL_Complain complain,CL_UserRole user,BigDecimal fee,int ftype){
		CL_FinanceStatement statement = new CL_FinanceStatement();
		String number = CacheUtilByCC.getOrderNumber("cl_finance_statement", "L", 8);
		statement.setNumber(number);
		statement.setFrelatedId(complain.getFid()+"");
		statement.setForderId(complain.getOrderNumber());
		statement.setFbusinessType(3);
		statement.setFamount(fee);
		statement.setFtype(ftype);
		statement.setFuserroleId(user.getId());
		statement.setFuserid(user.getVmiUserFid());
		statement.setFpayType(0);
		statement.setFbalance(BigDecimal.ZERO);
		statement.setFcreateTime(new Date());
		statement.setFreight(fee);
		statement.setFremark("投诉受理");
		return statement;
	}
	
	
}
	
