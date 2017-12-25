package com.pc.appInterface.carLine;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.pc.controller.BaseController;
import com.pc.dao.Car.impl.CarDao;
import com.pc.dao.carLine.impl.CarLineDao;
import com.pc.dao.factory.impl.FactoryDao;
import com.pc.dao.order.impl.OrderDao;
import com.pc.model.CL_Car;
import com.pc.model.CL_CarLine;
import com.pc.model.CL_Factory;
import com.pc.util.JSONUtil;
import com.pc.util.LatitudeLongitudeDI;
import com.pc.util.RedisUtil;

@Controller
public class AppCarLineController extends BaseController {

	@Resource
	private OrderDao orderDao;

	@Resource
	private FactoryDao factoryDao;

	@Resource
	private CarLineDao carLineDao;

	@Resource
	private CarDao carDao;

	@RequestMapping("/app/carLine/sign")
	public String sign(HttpServletRequest request, HttpServletResponse response) {
		Integer userRoleId;
		CL_CarLine carLine;
		String longitude, latitude;
		int a = 0;
		HashMap<String, Object> m = new HashMap<>();
		longitude = request.getParameter("longitude");// 经度
		latitude = request.getParameter("latitude");// 纬度

		if (request.getParameter("id") == null
				|| "".equals(request.getParameter("id"))) {
			m.put("success", "false");
			m.put("msg", "请先登录！");
			return writeAjaxResponse(response, JSONUtil.getJson(m));
		} else {
			userRoleId = Integer.parseInt(request.getParameter("id"));
		}
		
		try {
			RedisUtil.Lock(userRoleId+"");
		
			int orderNum = orderDao.getUnfinishByUserRoleId(userRoleId);
			if (orderNum > 0) {
				m.put("success", "false");
				m.put("msg", "签到不成功，有订单未完成");
				return writeAjaxResponse(response, JSONUtil.getJson(m));
			}
	
			// 目前工厂少，就这样咯，以后工厂多了，1：高德有区域编码，移动跟后台记录该编码，搜索的时候可以缩小范围。2：地址有省市区，可以让移动端传参过来，同理。3：等到公司的工厂足够多，代码需要优化了，估计也不知道是哪个猴年马月了，反正肯定不是我来优化--------------BY
			// LANCHER
			List<CL_Factory> list = factoryDao.getAllUseFactory();
			for (int i = 0; i < list.size(); i++) {
				// 计算司机与工厂的直线距离
				BigDecimal straightStretch = LatitudeLongitudeDI
						.GetDistance(
								Double.parseDouble(list.get(i).getFlatitude()
										.toString()),
								Double.parseDouble(list.get(i).getFlongitude()
										.toString()), Double.parseDouble(latitude),
								Double.parseDouble(longitude));
				if (straightStretch.compareTo(new BigDecimal(0.8)) <= 0) {
					a = list.get(i).getFactory_id();
				}
			}
			if (a == 0) {
				m.put("success", "false");
				m.put("msg", "签到不成功，未到指定区域");
				return writeAjaxResponse(response, JSONUtil.getJson(m));
			}
	
			List<CL_CarLine> carlist = carLineDao.getByUserId(userRoleId);//新增了查询条件，查到的是排队中的车辆
			if (carlist.size() == 0) {
				carLine = new CL_CarLine();
				carLine.setFdriver_id(userRoleId);
				carLine.setFactory_id(a);
				carLine.setFstatus(1);
				carLine.setFsign_time(new Date());
				carLine.setFremark("");
				carLineDao.save(carLine);
			} else {
				/*carLine = carlist.get(0);
				if (carLine.getFstatus() != null && carLine.getFstatus() == 1) {
					m.put("success", "false");
					m.put("msg", "签到不成功，不得重复签到");
					return writeAjaxResponse(response, JSONUtil.getJson(m));
				} else {
					carLine.setFactory_id(a);
					carLine.setFstatus(1);
					carLine.setFsign_time(new Date());
					carLine.setFremark("");
					carLineDao.update(carLine);
				}*/
				
				//改！
				m.put("success", "false");
				m.put("msg", "签到不成功，不得重复签到");
				return writeAjaxResponse(response, JSONUtil.getJson(m));
			}
	
			// 查询该司机在同区域and车型签到的队伍中的名次
			List<CL_CarLine> cl = new ArrayList<CL_CarLine>();
			if(carLine.getFcar_line_id() == null){
				cl = carLineDao.getByUserId(userRoleId);
				carLine.setFcar_line_id(cl.get(0).getFcar_line_id());
			}
			CL_Car car = carDao.getByUserRoleId(userRoleId).get(0);
			List<Integer> listSort = carLineDao.findSort(car.getActiveArea(),
					car.getCarSpecId());
			Integer sort, carSort;
			for (int i = 0; i < listSort.size(); i++) {
				carSort = listSort.get(i);
				if (carSort.equals(carLine.getFcar_line_id())) {
					sort = i + 1;
					carLine.setSort(sort);
				}
			}
			carLine.setActiveArea(car.getArea());
			carLine.setCarSpec(car.getCarSpecId());
			m.put("success", "true");
			m.put("msg", "签到成功");
			m.put("data", carLine);
		} catch (Exception e) {
			m.put("success", "false");
			m.put("msg", "签到不成功！");
		} finally {
			RedisUtil.del(userRoleId+"");
		}
		return writeAjaxResponse(response, JSONUtil.getJson(m));
	}

	@RequestMapping("/app/carLine/detection")
	public String detection(HttpServletRequest request,
			HttpServletResponse response) {
		Integer userRoleId;
		HashMap<String, Object> m = new HashMap<>();
		CL_CarLine carLine = new CL_CarLine();
		if (request.getParameter("id") == null
				|| "".equals(request.getParameter("id"))) {
			m.put("success", "false");
			m.put("msg", "请先登录！");
			return writeAjaxResponse(response, JSONUtil.getJson(m));
		} else {
			userRoleId = Integer.parseInt(request.getParameter("id"));
		}

		// 查询该司机在同区域and车型签到的队伍中的名次
//		List<CL_CarLine> carlist = carLineDao.getByUserId(userRoleId);
		//改！
		List<CL_CarLine> carlist = carLineDao.getByUserId2(userRoleId);
		if(carlist.size() == 0){
			carLine.setFstatus(5);
			m.put("success", "true");
			m.put("msg", "请签到！");
			m.put("data", carLine);
			return writeAjaxResponse(response, JSONUtil.getJson(m));
		}
		carLine = carlist.get(0);
		if(carLine.getFstatus() == 3){
			carLine.setFstatus(3);//告诉前端该司机已配货
			m.put("success", "true");
			m.put("msg", "该送货啦！");
			m.put("data", carLine);
			return writeAjaxResponse(response, JSONUtil.getJson(m));
		} 
		if (carLine.getFstatus() == 2) {
			carLine.setFstatus(4);//告诉前端该司机已被取消
			m.put("success", "true");
			m.put("msg", "请到工厂签到！");
			m.put("data", carLine);
			return writeAjaxResponse(response, JSONUtil.getJson(m));
		} 
		CL_Car car = carDao.getByUserRoleId(userRoleId).get(0);
		List<Integer> listSort = carLineDao.findSort(car.getActiveArea(),
				car.getCarSpecId());
		Integer sort, carSort;
		for (int i = 0; i < listSort.size(); i++) {
			carSort = listSort.get(i);
			if (carSort.equals(carLine.getFcar_line_id())) {
				sort = i + 1;
				carLine.setSort(sort);
			}
		}

		carLine.setActiveArea(car.getArea());

		List<CL_CarLine> list = carLineDao.getByUserId(userRoleId);
		if (list.size() > 0) {
			CL_CarLine line = list.get(0);
			if (line.getFstatus() == 1) {
				carLine.setFstatus(2);
				m.put("success", "true");
				m.put("msg", "无法签到：排队中");
				m.put("data", carLine);
				return writeAjaxResponse(response, JSONUtil.getJson(m));
			}
		}
		int count = orderDao.getUnfinishByUserRoleId(userRoleId);
		if (count > 0) {
			carLine.setFstatus(2);
			m.put("success", "true");
			m.put("msg", "无法签到：有订单未完成");
			m.put("data", carLine);
			return writeAjaxResponse(response, JSONUtil.getJson(m));
		} else {
			carLine.setFstatus(1);
			m.put("success", "true");
			m.put("msg", "允许签到");
			m.put("data", carLine);
			return writeAjaxResponse(response, JSONUtil.getJson(m));
		}
	}

}
