package com.pc.controller.distance;

import java.util.Date;
import java.util.HashMap;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.org.rapid_framework.page.Page;

import com.pc.controller.BaseController;
import com.pc.dao.UserRole.impl.UserRoleDao;
import com.pc.dao.address.impl.AddressDao;
import com.pc.dao.distance.impl.DistanceDao;
import com.pc.model.CL_Distance;
import com.pc.model.CL_UserRole;
import com.pc.query.distance.CL_DistanceQuery;
import com.pc.util.JSONUtil;
import com.pc.util.RedisUtil;
import com.pc.util.ServerContext;

@Controller
public class DistanceController extends BaseController {
	
	@Resource
	private DistanceDao distanceDao;
	
	@Resource
	private UserRoleDao userRoleDao;
	
	@Resource
	private AddressDao addressDao;
	
	protected static final String FIXED_DISTANCE = "/pages/pc/distance/fixed_distance.jsp";
	protected static final String DISTANCE_SAVE = "/pages/pc/distance/distanceSave.jsp";
	protected static final String DISTANCE_EDIT = "/pages/pc/distance/distanceEdit.jsp";
	
	@RequestMapping("/distance/fixed_distance")
	public String list(HttpServletRequest request,HttpServletResponse response)
			throws Exception{
		return FIXED_DISTANCE;
	}
	
	@RequestMapping("/distance/distanceSave")
	public String save(HttpServletRequest request,HttpServletResponse response)
			throws Exception{
		return DISTANCE_SAVE;
	}
	
	@RequestMapping("/distance/distanceEdit")
	public String edit(HttpServletRequest request,HttpServletResponse response,Integer id)
			throws Exception{
		CL_Distance distance = this.distanceDao.getById(id);
		request.setAttribute("distance", distance);
		return DISTANCE_EDIT;
	}
	
	//固定距离加载
	@RequestMapping("/distance/load")
	public String load(HttpServletRequest request,HttpServletResponse response,@ModelAttribute CL_DistanceQuery distanceQuery)
			throws Exception{
		String pageNum = request.getParameter("page");
		String pageSize = request.getParameter("rows");
		if(distanceQuery == null){
			distanceQuery = newQuery(CL_DistanceQuery.class, null);
		}
		if(pageNum != null){
			distanceQuery.setPageNumber(Integer.parseInt(pageNum));
		}
		if(pageSize != null){
			distanceQuery.setPageSize(Integer.parseInt(pageSize));
		}
		Page<CL_Distance> page = distanceDao.findPage(distanceQuery);
		HashMap<String, Object> m = new HashMap<String, Object>();
		System.out.println(page.getResult().toString());
		m.put("total", page.getTotalCount());
		m.put("rows", page.getResult());
		return writeAjaxResponse(response, JSONUtil.getJson(m));
	}
	
	//新增固定地址
	@RequestMapping("/distance/save")
	public String save(HttpServletRequest request,
			HttpServletResponse response, @ModelAttribute CL_Distance distance) {
		try {
			RedisUtil.LockMethod("lock.com.pc.controller.distance.save");

			// 判断该用户是否创建过相同的路线
			int num = distanceDao.isExist(distance.getFcustomer_id(),
					distance.getFaddressDel_id(), distance.getFaddressRec_id());
			if (num < 1) {
				String vmi_user_fid = ServerContext.getUseronline()
						.get(request.getSession().getId()).getFuserid();
				CL_UserRole user = userRoleDao.getByVmiUserFidAndRoleId(
						vmi_user_fid, 3);
				distance.setFcreater_id(user.getId());
				distance.setFcreate_time(new Date());
				distance.setFeditor_id(user.getId());
				distance.setFedit_time(new Date());
				distanceDao.save(distance);
				return writeAjaxResponse(response, "success");
			} else {
				return writeAjaxResponse(response, "false");
			}
		} catch (Exception e) {
			return writeAjaxResponse(response, "false");
		} finally {
			RedisUtil.del("lock.com.pc.controller.distance.save");
		}
	}
	
	//删除
	@RequestMapping("/distance/del")
	@Transactional(propagation = Propagation.REQUIRED)
	public String del(HttpServletRequest request,HttpServletResponse response,Integer[] ids)
			throws Exception{
		for (Integer id : ids){
			distanceDao.deleteById(id);
		}
		return writeAjaxResponse(response, "success");
	}
	
	//编辑
	@RequestMapping("/distance/edit")
	public String edit(HttpServletRequest request,HttpServletResponse response,@ModelAttribute CL_Distance distance)
			throws Exception{
		String vmi_user_fid = ServerContext.getUseronline().get(request.getSession().getId()).getFuserid();
		CL_UserRole user = userRoleDao.getByVmiUserFidAndRoleId(vmi_user_fid, 3);
		distance.setFeditor_id(user.getId());
		distance.setFedit_time(new Date());
		distanceDao.update(distance);
		return writeAjaxResponse(response, "success");
	}
	
	
	
	
}
