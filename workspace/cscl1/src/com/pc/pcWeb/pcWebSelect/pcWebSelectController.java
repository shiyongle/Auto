package com.pc.pcWeb.pcWebSelect;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.org.rapid_framework.page.Page;

import com.pc.controller.BaseController;
import com.pc.dao.UserRole.IUserRoleDao;
import com.pc.dao.select.impl.UtilOptionDao;
import com.pc.model.CL_UserRole;
import com.pc.model.Util_Option;
import com.pc.query.select.selectQuery;
import com.pc.util.JSONUtil;
import com.pc.util.ServerContext;
@Controller
public class pcWebSelectController extends BaseController{
	
	@Resource
	private UtilOptionDao optionDao;
	@Resource
	private IUserRoleDao iuserRoleDao;
	private selectQuery selQuery;
 
	public selectQuery getSelQuery() {
		return selQuery;
	}



	public void setSelQuery(selectQuery selQuery) {
		this.selQuery = selQuery;
	}



	//获取所有车辆规格
	@RequestMapping("/pcWeb/select/getAllCarType")
	public String getAllCarType(HttpServletRequest request,HttpServletResponse reponse){
		List<Util_Option> options= optionDao.getAllCarType();
		Util_Option o = new Util_Option();
		o.setOptionId(-1);
		o.setOptionName("请选择");
		options.add(0, o);
		return writeAjaxResponse(reponse,JSONUtil.getJson(options));
	}
	
 
	
	//根据规格来获取对应的车型
	@RequestMapping("/pcWeb/select/getAllCarSpecByCarType")
	public String getAllCarSpecByCarType(HttpServletRequest request,HttpServletResponse reponse,Integer optionTemp){
		List<Util_Option> lst= optionDao.getAllCarSpecByCarType(optionTemp);
		for(Util_Option ol :lst){
			if(("任意车型").equals(ol.getOptionName())){
				lst.remove(ol);
				break;
			}
		}
		Util_Option o = new Util_Option();
		o.setOptionId(-1);
		o.setOptionName("请选择");
		lst.add(0, o);
		return writeAjaxResponse(reponse,JSONUtil.getJson(lst));
	}
	
	
	//获取所有车辆规格
	@RequestMapping("/pcWeb/select/getDriverByCarTypeId")
	public String getDriverByCarTypeId(HttpServletRequest request,HttpServletResponse reponse,Integer optionTemp,Integer carSpecId){
		List<Util_Option> options= optionDao.getDriverByCarTypeId(optionTemp,carSpecId);
		Util_Option o = new Util_Option();
		o.setOptionId(-1);
		o.setOptionName("请选择");
		options.add(0, o);
		return writeAjaxResponse(reponse,JSONUtil.getJson(options));
	}
	//获取所有货主
	@RequestMapping("/pcWeb/select/getByRoleId")
	public String getByRoleId(HttpServletRequest request,HttpServletResponse reponse){
		List<Util_Option> options= optionDao.getByRoleId();
		Util_Option o = new Util_Option();
		o.setOptionId(-1);
		o.setOptionName("请选择");
		options.add(0, o);
		return writeAjaxResponse(reponse,JSONUtil.getJson(options));
	}

	//获取所有货主
	@RequestMapping("/pcWeb/select/getAll")
	public String getAll(HttpServletRequest request,HttpServletResponse reponse){
		List<Util_Option> options= optionDao.getAll();
		Util_Option o = new Util_Option();
		o.setOptionId(-1);
		o.setOptionName("请选择");
		options.add(0, o);
		return writeAjaxResponse(reponse,JSONUtil.getJson(options));
	}
	
	//获取所有提交认证用户
		@RequestMapping("/pcWeb/select/getByIdentify")
		public String getByIdentify(HttpServletRequest request,HttpServletResponse reponse){
			List<Util_Option> options= optionDao.getByIdentify();
			Util_Option o = new Util_Option();
			o.setOptionId(-1);
			o.setOptionName("请选择");
			options.add(0, o);
			return writeAjaxResponse(reponse,JSONUtil.getJson(options));
		}
	//获取所有用户
	@RequestMapping("/pcWeb/select/getByRoleCarId")
	public String getByRoleCarId(HttpServletRequest request,HttpServletResponse reponse){
		List<Util_Option> options= optionDao.getByRoleCarId();
		Util_Option o = new Util_Option();
		o.setOptionId(-1);
		o.setOptionName("请选择");
		options.add(0, o);
		return writeAjaxResponse(reponse,JSONUtil.getJson(options));
	}
	
	
	//获取所有零担类型单位
	@RequestMapping("/pcWeb/select/getByOtherTypeByUnit")
	public String getByOtherTypeByUnit(HttpServletRequest request,HttpServletResponse reponse){
		List<Util_Option> options= optionDao.getByOtherTypeByUnit();
		Util_Option o = new Util_Option();
		o.setOptionId(-1);
		o.setOptionName("请选择");
		options.add(0, o);
		return writeAjaxResponse(reponse,JSONUtil.getJson(options));
	}
	
	//获取所有货主
	@RequestMapping("/pcWeb/select/getAllConsignor")
	public String getAllConsignor(HttpServletRequest request,HttpServletResponse reponse,Integer couponsId){
		List<Util_Option> options= optionDao.getAllConsignor(couponsId);
		if(options.size()>0){
			Util_Option o = new Util_Option();
			o.setOptionId(-1);
			o.setOptionName("请选择");
			options.add(0, o);
			Util_Option o2 = new Util_Option();
			o2.setOptionId(0);
			o2.setOptionName("全部货主");
			options.add(1, o2);
		}else{
			Util_Option o = new Util_Option();
			o.setOptionId(-1);
			o.setOptionName("请选择");
			options.add(0, o);
		}
		return writeAjaxResponse(reponse,JSONUtil.getJson(options));
	}
	
	//获取所有客户下拉框
	@RequestMapping("/pcWeb/select/getAllCustId")
	public String getAllCustId(HttpServletRequest request,HttpServletResponse reponse){
		List<Util_Option> options= optionDao.getAllCustId();
		Util_Option o = new Util_Option();
		o.setOptionId(-1);
		o.setOptionName("请选择");
		options.add(0, o);
		return writeAjaxResponse(reponse,JSONUtil.getJson(options));
	}
	
	//获取所有完成订单下拉框
	@RequestMapping("/pcWeb/select/getFinishOrders")
	public String getFinishOrders(HttpServletRequest request,HttpServletResponse reponse){
		List<Util_Option> options= optionDao.getFinishOrders();
		Util_Option o = new Util_Option();
		o.setOptionId(-1);
		o.setOptionName("请选择");
		options.add(0, o);
		return writeAjaxResponse(reponse,JSONUtil.getJson(options));
	}
	
	//所有司机下拉框
	@RequestMapping("/pcWeb/select/getAllDrivers")
	public String getAllDrivers(HttpServletRequest request,HttpServletResponse reponse){
		List<Util_Option> options= optionDao.getAllDrivers();
		Util_Option o = new Util_Option();
		o.setOptionId(-1);
		o.setOptionName("请选择");
		options.add(0, o);
		return writeAjaxResponse(reponse,JSONUtil.getJson(options));
	}
	 //根据司机或者货主 返回的 订单每月数量
	@RequestMapping("/pcWeb/select/getOrderCountById")
	 public  String getOrderCountById(HttpServletRequest request,HttpServletResponse reponse,Integer type){
		this.selQuery=newQuery(selectQuery.class, null);
		Integer userroleId;
		Integer creator=null,userRoleId=null;
		HashMap<String, Object> map=new HashMap<String, Object>();
		if(!ServerContext.getUseronline().containsKey(request.getSession().getId().toString())){
	    	map.put("success", "false");
			map.put("msg","未登录！");
			return writeAjaxResponse(reponse, JSONUtil.getJson(map));
		//if(ServerContext.getUseronline().get(request.getSession().getId())!=null)
	    }
		
	    System.out.println(ServerContext.getUseronline().get(request.getSession().getId().toString()).getFuserId());
	    userroleId =ServerContext.getUseronline().get(request.getSession().getId().toString()).getFuserId();
	    if(type==null){
	    	map.put("success", "false");
			map.put("msg","类型有误!");
			return writeAjaxResponse(reponse, JSONUtil.getJson(map));
	    }
		if(type==1){
			  creator=userroleId;
		}else if(type==2){
			  userRoleId=userroleId;
		}
		if(creator!=null){
			this.selQuery.setCreator(creator);
		}
		if(userRoleId!=null){
			this.selQuery.setUserRoleId(userRoleId);
		}
		List<Util_Option> options= optionDao.find(selQuery);
		LinkedHashMap<String, Object> maps=mouth();
	     for(Util_Option op:options){
	    	 maps.put(""+op.getOptionId(), op.getOptionNum());
		}
		map.put("success", "true");
		map.put("data", maps);
		return writeAjaxResponse(reponse,JSONUtil.getJson(map));
	}
	
	public LinkedHashMap<String, Object> mouth(){
	    LinkedHashMap<String, Object> map=new LinkedHashMap<>();
		for(int i=1;i<13;i++){
			map.put(""+i, 0);
		}
		 
         return map;		
	}
	
	 //根据司机或者货主 返回的  成单次数和被评价次数
		@RequestMapping("/pcWeb/select/userEndOrder")
		 public  String userEndOrder(HttpServletRequest request,HttpServletResponse reponse){
			Integer userroleId;
			HashMap<String, Object> map=new HashMap<String, Object>();
			HashMap<String, Object> maps=new HashMap<String,Object>();
			if(!ServerContext.getUseronline().containsKey(request.getSession().getId().toString())){
		    	map.put("success", "false");
				map.put("msg","未登录！");
				return writeAjaxResponse(reponse, JSONUtil.getJson(map));
			//if(ServerContext.getUseronline().get(request.getSession().getId())!=null)
		    }
			
		    System.out.println(ServerContext.getUseronline().get(request.getSession().getId().toString()).getFuserId());
		    userroleId =ServerContext.getUseronline().get(request.getSession().getId().toString()).getFuserId();
		    CL_UserRole userRole=iuserRoleDao.getById(userroleId);
		    maps.put("endOrder",  userRole.getEndOrderTimes());//已成单数
		    maps.put("rateTimes", userRole.getRateTimes());//评价次数
			map.put("success", "true");
			map.put("data",maps);
			return writeAjaxResponse(reponse,JSONUtil.getJson(map));
		}
		
		 //根据司机或者货主 返回的 订单类型 分组
		@RequestMapping("/pcWeb/select/findOrderByType")
	    public String findOrderByType(HttpServletRequest request,HttpServletResponse reponse,Integer type){
			Integer userroleId;
			HashMap<String, Object> map=new HashMap<String, Object>();
			if(!ServerContext.getUseronline().containsKey(request.getSession().getId().toString())){
		    	map.put("success", "false");
				map.put("msg","未登录！");
				return writeAjaxResponse(reponse, JSONUtil.getJson(map));
			//if(ServerContext.getUseronline().get(request.getSession().getId())!=null)
		    }
		   System.out.println(ServerContext.getUseronline().get(request.getSession().getId().toString()).getFuserId());
		   userroleId =ServerContext.getUseronline().get(request.getSession().getId().toString()).getFuserId();
		   this.selQuery=newQuery(selectQuery.class, null);
			 if(type==1){
				 selQuery.setCreator(userroleId);
			 }else if(type==2){
				 selQuery.setUserRoleId(userroleId);
			 }
			 Page<Util_Option> pages=optionDao.findOrderByType(selQuery);
			 LinkedHashMap<String, Object> ms=new LinkedHashMap<>();
			 ms.put("整车", 0);
			 ms.put("零担",0);
			 ms.put("包天", 0);
			 for(Util_Option ut:pages){
				 switch (ut.getOptionId()) {
				case 1:
					 ms.put("整车", ut.getOptionNum());
					break;
				case 2:
					 ms.put("零担", ut.getOptionNum());
					break;
				case 3:
					 ms.put("包天", ut.getOptionNum());
					break;
				default:
					 map.put("success", "false");
					 map.put("msg","查询有误!");
					 return writeAjaxResponse(reponse, JSONUtil.getJson(map));
				}
				 
			 }
			 map.put("success", "true");
			 map.put("data",ms);
			 return writeAjaxResponse(reponse, JSONUtil.getJson(map));
		}
	
}



