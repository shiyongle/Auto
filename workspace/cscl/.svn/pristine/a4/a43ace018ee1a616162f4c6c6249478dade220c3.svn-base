package com.pc.pcWeb.pcWebRating;


import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.pc.controller.BaseController;
import com.pc.dao.UserRole.IUserRoleDao;
import com.pc.dao.order.IorderDao;
import com.pc.dao.rating.IratingDao;
import com.pc.model.CL_Order;
import com.pc.model.CL_Rating;
import com.pc.model.CL_UserRole;
import com.pc.util.JSONUtil;
import com.pc.util.ServerContext;

@Controller
public class pcWebRatingController  extends BaseController {
	@Resource
	private IratingDao iratingDao;
	@Resource
	private IorderDao iorderDao;
	@Resource
	private IUserRoleDao iuserRoleDao;
	 
	protected static final String RATING_EVALUATE= "/pages/pcWeb/evaluate/evaluate.jsp";
	protected static final String RATING_EVALUATE1= "/pages/pcWeb/evaluate/evaluate1.jsp";
	protected static final String RATING_LOOK= "/pages/pcWeb/evaluate/evaluate_look.jsp";
	
	/**根据类型跳转*/
	@RequestMapping("/pcWeb/rating/pcWebEvaluate")
	public String pcWebEvaluate(HttpServletRequest request,HttpServletResponse response,Integer orderId) throws IOException{
	 HashMap<String, Object> map=new HashMap<String,Object>();
	 Integer userId;
	 if(!ServerContext.getUseronline().containsKey(request.getSession().getId().toString())){
	    	map.put("success", "false");
			map.put("msg","未登录！");
			return writeAjaxResponse(response, JSONUtil.getJson(map));
		//if(ServerContext.getUseronline().get(request.getSession().getId())!=null)
	    }
	    System.out.println(ServerContext.getUseronline().get(request.getSession().getId().toString()).getFuserId());
	    userId =ServerContext.getUseronline().get(request.getSession().getId().toString()).getFuserId();
	   CL_UserRole user=iuserRoleDao.getById(userId);
	   if(orderId==null){
	    	map.put("success", "false");
		    map.put("msg","订单id有误！");
			 return writeAjaxResponse(response, JSONUtil.getJson(map));
	   }
	   CL_Order ord=iorderDao.getByIdAndCreator(orderId, userId);
	   request.getSession().setAttribute("orderNumber", ord.getNumber());
	   request.getSession().setAttribute("orderId", orderId);
	    if(user.getRoleId()==1){
	    	request.getSession().setAttribute("ratingType", 0);
	    	request.getSession().setAttribute("orderDriverName", ord.getOrderDriverName());
	    	request.getSession().setAttribute("orderDriverCarNumber", ord.getOrderDriverCarNumber());
	    	return RATING_EVALUATE;
	    }else if(user.getRoleId()==2){
	    	request.getSession().setAttribute("ratingType", 1);
	    	request.getSession().setAttribute("creatorName", ord.getCreatorName());
	    	return RATING_EVALUATE1;
	    }else{
	    	map.put("success", "false");
			map.put("msg","登录帐号类型有误");
			return writeAjaxResponse(response, JSONUtil.getJson(map));
	    }
	   
	}
	
	
	
	/**保存评价*/
	@RequestMapping("/pcWeb/rating/pcWebratingSave")
	public String pcWebratingSave(HttpServletRequest request,HttpServletResponse response) throws IOException{
		HashMap<String, Object> map = new HashMap<String, Object>();
	    String  name,carNum,orderNum,remark;
	    Integer  estimate,ratingType,orderId,userId;
		if(!ServerContext.getUseronline().containsKey(request.getSession().getId().toString())){
	    	map.put("success", "false");
			map.put("msg","未登录！");
			return writeAjaxResponse(response, JSONUtil.getJson(map));
		//if(ServerContext.getUseronline().get(request.getSession().getId())!=null)
	    }
	    System.out.println(ServerContext.getUseronline().get(request.getSession().getId().toString()).getFuserId());
	    userId =ServerContext.getUseronline().get(request.getSession().getId().toString()).getFuserId();
	
		CL_Rating rating =new CL_Rating();
		if(request.getParameter("ratingType")==null || "".equals(request.getParameter("ratingType"))){
	    	map.put("success", "false");
			map.put("msg","评价类型有误请联系客服");
			return writeAjaxResponse(response, JSONUtil.getJson(map));
	    }
		else{
		      ratingType=Integer.parseInt(request.getParameter("ratingType")); 
			  rating.setRatingType(ratingType);
		}
		if(request.getParameter("orderNum")==null || "".equals(request.getParameter("orderNum"))){
	    	map.put("success", "false");
			map.put("msg","订单编号有误请联系客服");
			return writeAjaxResponse(response, JSONUtil.getJson(map));
	    }
		else{
			  orderNum= request.getParameter("orderNum"); 
			  rating.setOrderNum(orderNum);
		} 
		if(request.getParameter("orderId")==null || "".equals(request.getParameter("orderId"))){
	    	map.put("success", "false");
			map.put("msg","订单数据有误请联系客服");
			return writeAjaxResponse(response, JSONUtil.getJson(map));
	    }
		else{
			orderId= Integer.valueOf(request.getParameter("orderId").toString()); 
		} 
		if(ratingType==0){
		     if(iorderDao.IsExistIdByRating(orderId)==null){
	        	map.put("success", "false");
	 			map.put("msg","该订单状态不能评价,请联系客服!");
	 			return writeAjaxResponse(response, JSONUtil.getJson(map));
	        }
		}
		if(ratingType==1){
			if(iorderDao.IsExistIdByRatingCar(orderId)!=null){
				map.put("success", "false");
	 			map.put("msg","该订单状态不能评价,请联系客服!");
	 			return writeAjaxResponse(response, JSONUtil.getJson(map));
			}
		}
		if(request.getParameter("ratingScore")==null || "".equals(request.getParameter("ratingScore"))){
			rating.setRatingScore(new BigDecimal(0));
	    }
		else{
			rating.setRatingScore(new BigDecimal(request.getParameter("ratingScore").toString()));
		}
		if(ratingType!=1){
		     rating.setService(new BigDecimal(request.getParameter("service").toString()));
		      rating.setTimeliness(new BigDecimal(request.getParameter("timeliness").toString()));
		      rating.setComplete(new BigDecimal(request.getParameter("complete").toString()));
			if(request.getParameter("carNum")==null || "".equals(request.getParameter("carNum"))){
		    	map.put("success", "false");
				map.put("msg","车牌号有误请联系客服");
				return writeAjaxResponse(response, JSONUtil.getJson(map));
		    }
			else{
				 carNum=  request.getParameter("carNum");
				  rating.setCarNum(carNum);
			}
		}
		if(request.getParameter("name")==null || "".equals(request.getParameter("name"))){
	    	map.put("success", "false");
			map.put("msg","姓名有误请联系客服");
			return writeAjaxResponse(response, JSONUtil.getJson(map));
	    }
		else{
		   	name=  request.getParameter("name");
			 rating.setName(name);
		}
		if(request.getParameter("remark")==null || "".equals(request.getParameter("remark"))){
			rating.setRemark(null);
		}
		else{
			remark=  request.getParameter("remark") ; 
			rating.setRemark(remark);
		}
		if(request.getParameter("estimate")==null || "".equals(request.getParameter("estimate"))){
	    	map.put("success", "false");
			map.put("msg","时间有误请联系客服");
			return writeAjaxResponse(response, JSONUtil.getJson(map));
	    }
		else{
		  estimate=Integer.parseInt(request.getParameter("estimate"));
		  rating.setEstimate(estimate);
		}
	    rating.setEsTime(new Date());
	    CL_Rating rating1=iratingDao.getByOrderNumAndType(orderNum,ratingType);
	    if(rating1==null){
	    	iratingDao.save(rating);
	    	//如果是司机对货主评价才更新订单状态
	    	if(ratingType==0){
	    		iorderDao.updateByNumber(orderNum, 5);
	    		
	    		/**司机被评价次数更新；*/
	        	 CL_Order orderinfo = iorderDao.getById(orderId);
	        	 CL_UserRole driverinfo = iuserRoleDao.getById(orderinfo.getUserRoleId());
	        	 driverinfo.setRateTimes(driverinfo.getRateTimes()+1);
	        	 iuserRoleDao.updateTimes(driverinfo);
	        	 /**司机被评价次数更新；*/
	        	 
	    	}else{
	    		 iorderDao.updateFratingByOrderId(orderId, 1); 
	    		/**货主被评价次数更新；*/
	        	 CL_Order orderinfo = iorderDao.getById(orderId);
	        	 CL_UserRole shipperinfo = iuserRoleDao.getById(orderinfo.getCreator());
	        	 shipperinfo.setRateTimes(shipperinfo.getRateTimes()+1);
	        	 iuserRoleDao.updateTimes(shipperinfo);
	        	 /**货主被评价次数更新；*/
	    	}
			map.put("success", "true");
		    map.put("msg","评价成功！");
			return writeAjaxResponse(response, JSONUtil.getJson(map));
	    }
	    else{
	    	map.put("success","false");
	    	map.put("msg", "评价失败已经有同类型评价");
	    	return writeAjaxResponse(response, JSONUtil.getJson(map));
	    }
	 
	}
 	/***app评价查询接口*/
 	@RequestMapping("/pcWeb/rating/pcWebloadRating")
 	public String pcWebloadRating(HttpServletRequest request,HttpServletResponse response )throws IOException{
      String orderNum;
      Integer ratingType;
  	HashMap<String, Object> map = new HashMap<String, Object>();
  	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
  	if(request.getParameter("orderNum")==null || "".equals(request.getParameter("orderNum"))){
    	map.put("success", "false");
		map.put("msg","订单编号有误请联系客服");
		return writeAjaxResponse(response, JSONUtil.getJson(map));
    }
  	else{
  		orderNum=request.getParameter("orderNum");
  	}
  	if(request.getParameter("ratingType")==null || "".equals(request.getParameter("ratingType"))){
    	map.put("success", "false");
		map.put("msg","评价类型有误请联系客服");
		return writeAjaxResponse(response, JSONUtil.getJson(map));
    }
  	else{
  		ratingType=Integer.parseInt(request.getParameter("ratingType"));
  	}
 	   CL_Rating rating=iratingDao.getByOrderNumAndType(orderNum, ratingType);
		
 	   if(rating==null){
 	 		map.put("success", "false");
 		    return writeAjaxResponse(response, JSONUtil.getJson(map));
 	   }
 		HashMap<String, Object> m = new HashMap<String, Object>();
 		m.put("success", "true");
		request.getSession().setAttribute("data",rating);
      return RATING_LOOK;
 	}
		
	 
}
