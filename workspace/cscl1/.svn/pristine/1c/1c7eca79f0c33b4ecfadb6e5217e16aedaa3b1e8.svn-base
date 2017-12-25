package com.pc.appInterface.position;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.pc.controller.BaseController;
import com.pc.dao.Car.ICarDao;
import com.pc.model.CL_Car;
import com.pc.util.JSONUtil;
import com.pc.util.ServerContext;
/***
 * 实时定位
 * */
@Controller
public class AppCarPositionController extends BaseController {
	@Resource
	private ICarDao carDao;
	
	/***查询地址信息*/
	@RequestMapping("/app/car/carPosition")
	public String carPosition(HttpServletRequest request,HttpServletResponse response) throws IOException{
		Integer userId;
		BigDecimal longitude,latitude;
		HashMap<String,Object> map =new HashMap<String,Object>();
		if(request.getParameter("userId")==null || "".equals(request.getParameter("userId"))){
	    	map.put("success", "false");
			map.put("msg","未登录！");
			return writeAjaxResponse(response, JSONUtil.getJson(map));
	    }else{
	    	userId = Integer.valueOf(request.getParameter("userId"));
	    }
		if(request.getParameter("longitude")==null || "".equals(request.getParameter("longitude"))){
	    	map.put("success", "false");
			map.put("msg","无法定位获知经纬度！");
			return writeAjaxResponse(response, JSONUtil.getJson(map));
	    }else{
	    	longitude = new BigDecimal(request.getParameter("longitude"));
//	    	double s=longitude.doubleValue()+0.01;
//	    	longitude = new BigDecimal(s+"");
	    }
		if(request.getParameter("latitude")==null || "".equals(request.getParameter("latitude"))){
	    	map.put("success", "false");
	    	map.put("msg","无法定位获知经纬度！");
			return writeAjaxResponse(response, JSONUtil.getJson(map));
	    }else{
	    	latitude = new BigDecimal(request.getParameter("latitude"));
	    }
		List<CL_Car> ls = this.carDao.getByUserRoleId(userId);
		for(CL_Car car:ls){
			car.setLongitude(longitude);
			car.setLatitude(latitude);
			car.setFposition_time(new Date());
			this.carDao.update(car);
		}

		if(ls.size()>0){
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
			String[] driverPosition = new String[]{longitude.toString(),latitude.toString(),sdf.format(new Date()),
					ls.get(0).getDriverName(),ls.get(0).getCarNum(),String.valueOf(ls.get(0).getFluckDriver()),ls.get(0).getCarSpecName()+":"+ls.get(0).getCarTypeName()};
			ServerContext.getDriverPosition().put(Integer.toString(userId), driverPosition);
		}else{
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
			String[] driverPosition = new String[]{longitude.toString(),latitude.toString(),sdf.format(new Date())};
			ServerContext.getDriverPosition().put(Integer.toString(userId), driverPosition);
		}		
		map.put("success", "true");
		map.put("msg","操作成功！");
		return writeAjaxResponse(response, JSONUtil.getJson(map));
	}
}
