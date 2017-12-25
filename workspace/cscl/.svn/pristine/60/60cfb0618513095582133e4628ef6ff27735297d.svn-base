package com.pc.pcWeb.pcWebProtocol;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.pc.controller.BaseController;
import com.pc.dao.UserRole.impl.UserRoleDao;
import com.pc.dao.addto.IaddtoDao;
import com.pc.dao.financeStatement.IFinanceStatementDao;
import com.pc.dao.order.IorderDao;
import com.pc.dao.orderDetail.IorderDetailDao;
import com.pc.dao.protocol.IprotocolDao;
import com.pc.dao.select.IUtilOptionDao;
import com.pc.model.CL_Protocol;
import com.pc.model.Util_Option;
import com.pc.util.JSONUtil;
import com.pc.util.ServerContext;

@Controller
public class pcWebProtocolController  extends BaseController {
	@Resource
	private IprotocolDao  protocolDao;
	@Resource
	private IUtilOptionDao  optionDao;
	@Resource
	private IorderDetailDao orderDetailDao;
	@Resource
	private IorderDao  orderDao;
	@Resource
	private IaddtoDao iaddtoDao;
	@Resource
	private IFinanceStatementDao statementDao;
	@Resource
	private UserRoleDao userRoleDao;

	/***查询用户协议信息*/
	@RequestMapping("/pcWeb/protocol/findProtocol")
	public String findProtocol(HttpServletRequest request,HttpServletResponse response) throws IOException{
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
		List<CL_Protocol> pros = protocolDao.getByUserId(userId);
		m.put("success", "true");
		m.put("data", pros);
		return writeAjaxResponse(response, JSONUtil.getJson(m));
	}

	/***查询零担单位表信息*/
	@RequestMapping("/pcWeb/protocol/findUnit")
	public String findUnit(HttpServletRequest request,HttpServletResponse response) throws IOException{
		List<Util_Option> list = optionDao.getByUnit();
		HashMap<String, Object> m = new HashMap<String, Object>();
//		HashMap<String, Object> map = new HashMap<String, Object>();
		m.put("success", "true");
		m.put("data", list);
		return writeAjaxResponse(response, JSONUtil.getJson(m));
	}

	/***APP查询订单 支付方式和增值服务方式*/
	/*@RequestMapping("/pcWeb/protocol/payInfoAndServer")
	public String payInfoAndServer(HttpServletRequest request,HttpServletResponse response) throws IOException{
		HashMap<String,Object> m =new HashMap<String,Object>();
		HashMap<String,Object> map =new HashMap<String,Object>();
		List<Map<String,Object>> list=new ArrayList<>();
		Integer orderId=null ,protocolType=null;
        String type="";
		String checkDate="",checkUrl ="";
//		String	payDate="",payDateUrl ="" ;
	    type=request.getParameter("type");
		if(!"1".equals(type)){
			if(request.getParameter("orderId")==null || "".equals(request.getParameter("orderId"))){
				m.put("success", "false");
				m.put("msg","订单有误！");
				return writeAjaxResponse(response, JSONUtil.getJson(m));
			}else{
				orderId = Integer.valueOf(request.getParameter("orderId"));
			}
			CL_Order  ord=orderDao.getById(orderId);
			protocolType=ord.getProtocolType();
			map.put("orderNumber", ord.getNumber());
			map.put("orderFreight", ord.getFreight());
			map.put("orderMileage", ord.getMileage());
		    map.put("orderId",ord.getId());
	    	if(request.getParameter("checkDate")==null || "".equals(request.getParameter("checkDate"))){
				map.put("success", "false");
				map.put("msg","校验参数有问题，请重新支付,数据有误");
				return  writeAjaxResponse(response, JSONUtil.getJson(map));
			}else{
				checkDate=request.getParameter("checkDate");
			}
			if(request.getParameter("checkUrl")==null || "".equals(request.getParameter("checkUrl"))){
				map.put("success", "false");
				map.put("msg","校验参数有问题，请重新支付,数据有误");
				return  writeAjaxResponse(response, JSONUtil.getJson(map));
			}else{
				checkUrl=request.getParameter("checkUrl");
			}
			if(ServerContext.cancelOrderPay(checkUrl, checkDate)){
				orderDao.updateStatusByOrderId(orderId, 2);
				String fuserid=userRoleDao.getById(ord.getCreator()).getVmiUserFid();
				statementDao.saveStatement(ord.getId().toString(), ord.getNumber(), 1, ord.getFreight(), -1, ord.getCreator(), ord.getFpayMethod(), fuserid);
				map.put("success", "false");
				map.put("msg",ServerContext.getMsg());
				return  writeAjaxResponse(response, JSONUtil.getJson(map));
			}
			JSONArray JOArry=new JSONArray();
			String [] title= new String[]{"装卸","电子回单","回单原件","上楼","代收货款"};
			String [] context=new String []{"线下协商、支付","免费","线下协商、支付","线下协商、支付","免费"};
			for(int i=0;i<title.length;i++){
				JSONObject  JO=new JSONObject();
				JO.put("id", i);
				JO.put("title", title[i]);
				JO.put("context",context[i]);
				JO.put("isc", 0);
				JOArry.add(i,JO);
			}
			if(protocolType==0){
				//根据卸货地址数判断是否可以支持运费到付     BY  CC  2016-05-17 START
				if(this.orderDetailDao.getByOrderId(orderId, 2).size()==1)
				{
					map.put("payMethod","0,1,2,3,4");
				}
				else
				{
					map.put("payMethod","0,1,2,3");
				}
				//根据卸货地址数判断是否可以支持运费到付     BY  CC  2016-05-17 START
				//map.put("payMethod","0,1,2,3,4");//2016-05-17 CC BAK
				map.put("server",JOArry);
				list.add(map);
				m.put("success", "true");
				m.put("data", list);
				return writeAjaxResponse(response, JSONUtil.getJson(m));
			}	
			if(protocolType==1){
				//map.put("server","[{\"id\":\"0\",\"title\":\"装卸\",\"context\":\"线下协商、支付\",\"isc\":\"0\"},{\"id\":\"1\",\"title\":\"电子回单\",\"context\":\"免费\",\"isc\":\"0\"},{\"id\":\"2\",\"title\":\"回单原件\",\"context\":\"免费\",\"isc\":\"0\"},{\"id\":\"3\",\"title\":\"上楼\",\"context\":\"线下协商、支付\",\"isc\":\"0\"},{\"id\":\"4\",\"title\":\"代收货款\",\"context\":\"免费\",\"isc\":\"0\"}]");
				map.put("server",JOArry);
				if(userRoleDao.getById(ord.getCreator()).isProtocol())
				{
					//根据卸货地址数判断是否可以支持运费到付     BY  CC  2016-05-17 START
					if(this.orderDetailDao.getByOrderId(orderId, 2).size()==1)
					{
						map.put("payMethod","0,1,2,3,4,5");
					}
					else
					{
						map.put("payMethod","0,1,2,3,5");
					}
					//根据卸货地址数判断是否可以支持运费到付     BY  CC  2016-05-17 END
					//map.put("payMethod","0,1,2,3,4,5");//2016-05-17 CC BAK
			   		
				  }
				  else{
					//根据卸货地址数判断是否可以支持运费到付     BY  CC  2016-05-17 START
						if(this.orderDetailDao.getByOrderId(orderId, 2).size()==1)
						{
							map.put("payMethod","0,1,2,3,4");
						}
						else
						{
							map.put("payMethod","0,1,2,3");
						}
						//根据卸货地址数判断是否可以支持运费到付     BY  CC  2016-05-17 END
						//map.put("payMethod","0,1,2,3,4");//2016-05-17 CC BAK
						
				  }
			    }
				list.add(map);
				m.put("success", "true");
				m.put("data", list);
				return writeAjaxResponse(response, JSONUtil.getJson(m));
		}
	    if("1".equals(type)){
	    	String payNumber="PP"+CacheUtil.getPayNumber(iaddtoDao);
			map.put("payNumber", payNumber);
	    	map.put("payMethod","0,1,2,3");
			list.add(map);
			m.put("success", "true");
			m.put("data", list);
			return writeAjaxResponse(response, JSONUtil.getJson(m));
	    }
		m.put("success", "false");
		m.put("msg","订单有误！");
		return writeAjaxResponse(response, JSONUtil.getJson(m));
	}*/
	
	/*** 追加费用**/
	/*@RequestMapping("/pcWeb/protocol/pcWebaddPay")
	public String pcWebaddPay(HttpServletRequest request,HttpServletResponse response) throws IOException{
		Integer orderId = null ,payMethod=null,userId=null;
		BigDecimal  fcost;
		String   payNumber=null, fremark="" ,checkDate2="" , checkUrl="" ;
	    String  checkDate="",payDate=""  ,payDateUrl ="" ;
		
		HashMap<String,Object> map =new HashMap<String,Object>();
		if(!ServerContext.getUseronline().containsKey(request.getSession().getId().toString())){
	    	map.put("success", "false");
			map.put("msg","未登录！");
			return writeAjaxResponse(response, JSONUtil.getJson(map));
		//if(ServerContext.getUseronline().get(request.getSession().getId())!=null)
	    }
	    System.out.println(ServerContext.getUseronline().get(request.getSession().getId().toString()).getFuserId());
	   userId =ServerContext.getUseronline().get(request.getSession().getId().toString()).getFuserId();
		if(request.getParameter("orderId")==null||"".equals(request.getParameter("orderId"))){
			map.put("success", "false");
			map.put("msg","请先选择订单");
			return  writeAjaxResponse(response, JSONUtil.getJson(map));
		}
		else{
			orderId=Integer.parseInt(request.getParameter("orderId"));
		}
		if(request.getParameter("payDateUrl")==null||"".equals(request.getParameter("payDateUrl"))){
			map.put("success", "false");
			map.put("msg","payDateUrl为空");
			return  writeAjaxResponse(response, JSONUtil.getJson(map));
		}
		else{
			payDateUrl=request.getParameter("payDateUrl").toString();
		}
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
		if(iaddtoDao.getByNumber(payNumber).size()>=1){
			map.put("success", "false");
			map.put("msg","订单号重复,请重新操作!");
			return  writeAjaxResponse(response, JSONUtil.getJson(map));
		}
		if(request.getParameter("fremark")==null || "".equals(request.getParameter("fremark"))){
			//备注允许为空  BY CC 2016-05-02  START
			fremark="";
			//备注允许为空  BY CC 2016-05-02  END
//			map.put("success", "false");
//			map.put("msg","支付方式有问题");
//			return  writeAjaxResponse(response, JSONUtil.getJson(map));
		}else{
			fremark=request.getParameter("fremark").toString();
		}
	
		   if(request.getParameter("checkUrl")==null || "".equals(request.getParameter("checkUrl"))){
				  map.put("success", "false");
				  map.put("msg","校验参数有问题，请重新支付,数据有误");
				  return  writeAjaxResponse(response, JSONUtil.getJson(map));
		   }else{
			   checkUrl=request.getParameter("checkUrl");
		    }
			   //校验第三方支付充值操作是否成功 ，余额支付不需要校验  BY CC  start
			  
		   //校验第三方支付充值操作是否成功 ，余额支付不需要校验  BY CC  start
		   if(payMethod!=0&&payMethod!=3)
		   {
			   if(request.getParameter("checkDate2")==null || "".equals(request.getParameter("checkDate2"))){
					  map.put("success", "false");
					  map.put("msg","校验充值参数有问题，请重新支付,数据有误");
					  return  writeAjaxResponse(response, JSONUtil.getJson(map));
			   }else{
				   checkDate2=request.getParameter("checkDate2");
			   }
			   if(!ServerContext.cancelOrderPay(checkUrl, checkDate2)){
					  map.put("success", "false");
					  map.put("msg",ServerContext.getMsg());
					  return  writeAjaxResponse(response, JSONUtil.getJson(map));
			   }
		   }
		   if(request.getParameter("checkDate")==null || "".equals(request.getParameter("checkDate"))){
				  map.put("success", "false");
				  map.put("msg","校验参数有问题，请重新支付,数据有误");
				  return  writeAjaxResponse(response, JSONUtil.getJson(map));
		   }else{
			   checkDate=request.getParameter("checkDate");
		   }
		   if(ServerContext.cancelOrderPay(checkUrl, checkDate)){
				if(iaddtoDao.getByNumber(payNumber).size()>=1){
				 String	newpayNumber=CacheUtil.getPayNumber(iaddtoDao);
					CL_Addto add1=new CL_Addto();
					add1.setFcreatTime(new Date());
					add1.setFstatus(1);
					add1.setFpayNumber(newpayNumber);
					add1.setForderId(orderId);
					add1.setFcost(fcost);
					add1.setFcreateor(userId);
					add1.setFpayMethod(payMethod);
					add1.setFremark(fremark);
					this.iaddtoDao.save(add1);
				}
				else{
					iaddtoDao.updateByNumber(payNumber, 1);
				}
				  map.put("success", "false");
				  map.put("msg","该订单已经付款");
				  return  writeAjaxResponse(response, JSONUtil.getJson(map));
		   }
     	CL_Addto add=new CL_Addto();
			add.setFcreatTime(new Date());
			add.setFstatus(0);
			add.setFpayNumber(payNumber);
			add.setForderId(orderId);
			add.setFcost(fcost);
			add.setFcreateor(userId);
			add.setFpayMethod(payMethod);
			add.setFremark(fremark);
			this.iaddtoDao.save(add);  
		if(request.getParameter("payDate")==null || "".equals(request.getParameter("payDate"))){
			map.put("success", "false");
			map.put("msg","支付系统未支付成功，请重新支付,数据有误");
			return  writeAjaxResponse(response, JSONUtil.getJson(map));
		}else{
			payDate=request.getParameter("payDate");
		}
		if(!ServerContext.cancelOrderPay(payDateUrl, payDate)){
			map.put("success", "false");
			map.put("msg","支付系统未支付成功，请重新支付");
			return  writeAjaxResponse(response, JSONUtil.getJson(map));
		}
		this.iaddtoDao.updateById(add.getFid(), 1);
		*//**添加明细 Start*//*
		CL_UserRole user=userRoleDao.getById(add.getFcreateor());
		String userid="";
		if(user!=null)
		{
			userid=user.getVmiUserFid();
		}
		int orderid=add.getForderId();
		String ordernum="";
		CL_Order order=orderDao.getById(orderid);
		if(order!=null)
		{
			ordernum=order.getNumber();
		}
		statementDao.saveStatement(add.getFid().toString(), ordernum, 2, add.getFcost(), -1, add.getFcreateor(), add.getFpayMethod(), userid);
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
}
