package com.pc.pcWeb.pcWebOrderDetail;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.pc.controller.BaseController;
import com.pc.dao.Car.ICarDao;
import com.pc.dao.Car.impl.CarDao;
import com.pc.dao.UserRole.IUserRoleDao;
import com.pc.dao.addto.IaddtoDao;
import com.pc.dao.carRanking.IcarRankingDao;
import com.pc.dao.clUpload.IuploadDao;
import com.pc.dao.coupons.impl.CouponsDaoImpl;
import com.pc.dao.couponsDetail.impl.CouponsDetailDaoImpl;
import com.pc.dao.financeStatement.IFinanceStatementDao;
import com.pc.dao.financeStatement.impl.FinanceStatementDaoImpl;
import com.pc.dao.identification.impl.IdentificationDao;
import com.pc.dao.message.impl.MessageDao;
import com.pc.dao.order.IorderDao;
import com.pc.dao.order.impl.OrderDao;
import com.pc.dao.orderCarDetail.IorderCarDetailDao;
import com.pc.dao.orderCarDetail.impl.OrderCarDetailDao;
import com.pc.dao.orderDetail.IorderDetailDao;
import com.pc.dao.orderDetail.impl.OrderDetailDao;
import com.pc.dao.protocol.IprotocolDao;
import com.pc.dao.rating.IratingDao;
import com.pc.dao.rule.IRuleDao;
import com.pc.dao.select.IUtilOptionDao;
import com.pc.model.CL_Addto;
import com.pc.model.CL_Car;
import com.pc.model.CL_CouponsDetail;
import com.pc.model.CL_Order;
import com.pc.model.CL_OrderDetail;
import com.pc.model.CL_Rating;
import com.pc.model.CL_UserRole;
import com.pc.model.Util_Option;
import com.pc.util.JSONUtil;
import com.pc.util.ServerContext;
@Controller
public class pcWebOrderDeatilControll extends BaseController {
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
	private IUserRoleDao userRoleDao;
	@Resource
 	private IFinanceStatementDao iFinanceStatementDao;
	
	protected static final String ORDER_DEATIL="/pages/pcWeb/order_detail/order_detail.jsp";
	

	/***查询货主订单详情信息*  */
	@RequestMapping("/pcWeb/orderDetail/pcWebloadHuoOrderDetail")
	public String pcWebloadHuoOrderDetail(HttpServletRequest request,HttpServletResponse response) throws Exception{
		Integer orderId,userroleId;
		BigDecimal AllCost=new BigDecimal(0);//货主补交运费合计 BY CC 
		BigDecimal AllCostD=new BigDecimal(0);//司机补交运费合计 BY CC
		HashMap<String,Object> map =new HashMap<String,Object>();
		if(request.getParameter("orderId")==null || "".equals(request.getParameter("orderId"))){
			map.put("success", "false");
			map.put("msg","订单数据有误,请联系客服");
			return writeAjaxResponse(response, JSONUtil.getJson(map));
		}else{
			orderId = Integer.parseInt(request.getParameter("orderId"));
		}
		if(!ServerContext.getUseronline().containsKey(request.getSession().getId().toString())){
	    	map.put("success", "false");
			map.put("msg","未登录！");
			return writeAjaxResponse(response, JSONUtil.getJson(map));
		//if(ServerContext.getUseronline().get(request.getSession().getId())!=null)
	    }
	    System.out.println(ServerContext.getUseronline().get(request.getSession().getId().toString()).getFuserId());
	    userroleId =ServerContext.getUseronline().get(request.getSession().getId().toString()).getFuserId();
	   CL_UserRole user=iuserRoleDao.getById(userroleId);
		CL_Order order=orderDao.getById(orderId);
		order.setTakeList(orderDetailDao.getByOrderId(orderId,1));
		order.setRecList(orderDetailDao.getByOrderId(orderId,2));
//		CL_Rule rule = this.ruleDao.getOneByType();
//		if(rule ==null){
//			map.put("success", "false");
//			map.put("msg","司机计费规则丢失,请联系客服！");
//			return writeAjaxResponse(response, JSONUtil.getJson(map));
//		}
		List<CL_Addto> adds=iaddtoDao.getByOrderId(orderId);
		String AllNumber="";
		//事先计算货主运费和司机运费   BY CC 20160518
		
		if(adds.size()>0){
			for(CL_Addto ad:adds){
				if(ad.getNumber()!=null&&!"".equals(ad.getNumber())){
					AllNumber+=ad.getNumber()+",";
				}
				AllCost=AllCost.add(ad.getFcost());
//				AllCostD=AllCostD.add(ad.getFdriverfee());
			}
		}
		if(!"".equals(AllNumber)){
			AllNumber=AllNumber.substring(0,AllNumber.length()-1);
		}	  
		order.setAllNumber(AllNumber);
//		order.setChargingrule(CalcTotalField.calDriverFee(AllCost.add(order.getFreight())));
		if(user.getRoleId()==2){ //车主
			order.setAllCost(AllCostD.setScale(1,BigDecimal.ROUND_HALF_UP));
			order.setFreight(order.getFdriverfee().setScale(1,BigDecimal.ROUND_HALF_UP));
		}
		if(user.getRoleId()==1){//货主
//			AllCost=AllCost.setScale(0,BigDecimal.ROUND_HALF_UP);
			order.setAllCost(AllCost.setScale(1,BigDecimal.ROUND_HALF_UP));
		}
//		order.setAllCost(AllCost.setScale(0,BigDecimal.ROUND_HALF_UP));
		if(order.getType()!=2){
			List<Util_Option>  ups=optionDao.getCarTypeByOrderIdName(orderId);
			if(ups.size()<=0){
				map.put("success", "false");
				map.put("msg","订单数据有误,所需车型没有,请联系客服");
				return writeAjaxResponse(response, JSONUtil.getJson(map));
			}
			String carSpecName=ups.get(0).getOptionName();
			String carOtherName=ups.get(0).getOptionCarOtherName();
			for(Util_Option option :ups){
				//增加订单再次下单未选择车型导致车型错误问题修改;
				if(option.getOptionCarTypeName()!=null && !option.getOptionCarTypeName().equals("")){
					if(!carSpecName.contains(option.getOptionCarTypeName())&&option.getOptionCarTypeName()!=null &&!"".equals(option.getOptionCarTypeName())){
						carSpecName = carSpecName+" "+option.getOptionCarTypeName();
					}
				}
				if(carOtherName!=null && "".equals(carOtherName)&&!carOtherName.contains(option.getOptionCarOtherName())&&!"".equals(option.getOptionCarOtherName())&&option.getOptionCarOtherName()!=null){
					carOtherName=carOtherName+" "+option.getOptionCarOtherName();
				}
			}
			order.setCarSpecName(carSpecName);
			order.setCarTypeName(carOtherName);

		}  
		if(order.getUserRoleId()!=null){
			List<CL_Car> car=carDao.getByUserRoleId(order.getUserRoleId());
			if(car.size()>0){
				order.setOrderDriverphone(car.get(0).getCarFtel());
				order.setOrderDriverCarNumber(car.get(0).getCarNum());
				order.setOrderDriverCarType(car.get(0).getCarTypeName());
				order.setOrderDriverName(car.get(0).getDriverName());
			}
		}
		CL_Rating rat=iratingDao.getByOrderNumAndType(order.getNumber(), 0);
		if(rat!=null){
			if(rat.getRatingType()==0){
				BigDecimal a,rate = new BigDecimal(0);
				a = rat.getService().add(rat.getComplete()).add(rat.getTimeliness());
				rate = new BigDecimal(3);
				a=a.divide(rate,0,BigDecimal.ROUND_HALF_UP);
				order.setAverage(a);
			}
			if(rat.getRatingType()==1){
				order.setAverage(rat.getRatingScore());
			}
		}
		
		//20160718 cd 订单支付时选择了好运券就会记录到订单,所以只有已付款的才在订单详情显示
		if(order.getStatus()>1){
			if(order.getCouponsDetailId()!=null){
				CL_CouponsDetail cou=couponsDetailDao.getById(order.getCouponsDetailId());
				order.setDollars(cou.getDollars());
			}
		}
		
		HashMap<String, Object> m = new HashMap<String, Object>();
		m.put("success", "true");
		request.getSession().setAttribute("data", order);
		return ORDER_DEATIL;
	}
	/**取消订单*/
	@RequestMapping("/pcWeb/order/pcWebcancelorder")
	public String pcWebcancelorder(HttpServletRequest request,HttpServletResponse response) throws IOException{
		HashMap<String,Object> map =new HashMap<String,Object>();
		Integer userroleId,orderId;
		if(!ServerContext.getUseronline().containsKey(request.getSession().getId().toString())){
	    	map.put("success", "false");
			map.put("msg","未登录！");
			return writeAjaxResponse(response, JSONUtil.getJson(map));
		//if(ServerContext.getUseronline().get(request.getSession().getId())!=null)
	    }
//	    System.out.println(ServerContext.getUseronline().get(request.getSession().getId().toString()).getFuserId());
	    userroleId =ServerContext.getUseronline().get(request.getSession().getId().toString()).getFuserId();
		
		if(request.getParameter("orderId")==null||"".equals(request.getParameter("orderId"))){
			map.put("success", "false");
			map.put("msg","数据有误请联系客服");
			return writeAjaxResponse(response, JSONUtil.getJson(map));
		}
		else{
			orderId=Integer.parseInt(request.getParameter("orderId"));
		}
		CL_Order order = this.orderDao.getById(orderId);
		//将发送退款请求放到服务端，确保支付退款与业务系统状态一致  BY CC 2016-03-04 START
		if(order.getStatus()!=1 )
		{
			map.put("success", "false");
			map.put("msg","该订单不能取消");
			return writeAjaxResponse(response, JSONUtil.getJson(map));
		}
		//将发送退款请求放到服务端，确保支付退款与业务系统状态一致  BY CC 2016-03-04 END	
		
		//20160721 cd APP手动取消前，先进行订单支付校验，解决订单支付成功后由于异常导致订单没有充值记录及余额问题；
//		HashMap<String,Object> m = orderDao.autoPayOrderFreight(order);
//		if(m.get("payed")!=null && m.get("payed").equals("true")){
//			map.put("success", "false");
//			map.put("msg", "订单已支付成功，不能取消！");
//			return writeAjaxResponse(response, JSONUtil.getJson(map));
//		}
		
		order.setStatus(6);
		order.setOperator(userroleId);
		this.orderDao.update(order);
		map.put("success", "true");
		map.put("msg", "操作成功！");
		return writeAjaxResponse(response, JSONUtil.getJson(map));
	}
	
	
	/**根据订单查订单明细*/
	@RequestMapping("/pcWeb/order/pcWebDeatilByOrderId")
	public String pcWebDeatilByOrderId(HttpServletRequest request,HttpServletResponse response) throws IOException{
		HashMap<String,Object> map =new HashMap<String,Object>();
		Integer userroleId,orderId,type;
		if(!ServerContext.getUseronline().containsKey(request.getSession().getId().toString())){
	    	map.put("success", "false");
			map.put("msg","未登录！");
			return writeAjaxResponse(response, JSONUtil.getJson(map));
		//if(ServerContext.getUseronline().get(request.getSession().getId())!=null)
	    }
	    System.out.println(ServerContext.getUseronline().get(request.getSession().getId().toString()).getFuserId());
	    userroleId =ServerContext.getUseronline().get(request.getSession().getId().toString()).getFuserId();
		if(request.getParameter("orderId")==null||"".equals(request.getParameter("orderId"))){
			map.put("success", "false");
			map.put("msg","数据有误请联系客服");
			return writeAjaxResponse(response, JSONUtil.getJson(map));
		}
		else{
			orderId=Integer.parseInt(request.getParameter("orderId"));
		}	
		if(request.getParameter("type")==null||"".equals(request.getParameter("type"))){
			map.put("success", "false");
			map.put("msg","数据有误请联系客服");
			return writeAjaxResponse(response, JSONUtil.getJson(map));
		}
		else{
			type=Integer.parseInt(request.getParameter("type"));
		}
		
		List<CL_OrderDetail> orderDeatil = this.orderDetailDao.getByOrderId(orderId,type);
		if(orderDeatil.size()>0){
			map.put("success", "true");
			map.put("data", orderDeatil);
		}else{
			map.put("success", "false");
			map.put("msg","数据有误请联系客服");
		}
		return writeAjaxResponse(response, JSONUtil.getJson(map));
	}
}
