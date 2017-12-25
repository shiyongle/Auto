package com.pc.appInterface.address;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.org.rapid_framework.page.Page;

import com.pc.controller.BaseController;
import com.pc.dao.address.impl.AddressDao;
import com.pc.model.CL_Address;
import com.pc.query.address.AddressQuery;
import com.pc.util.JSONUtil;
import com.pc.util.ServerContext;
@Controller
public class AppAddressController extends BaseController {
	@Resource
	private AddressDao addressDao;
	private AddressQuery addressQuery;
	
	public AddressQuery getAddressQuery() {
		return addressQuery;
	}

	public void setAddressQuery(AddressQuery addressQuery) {
		this.addressQuery = addressQuery;
	}
	
	
	/***查询地址信息*/
	@RequestMapping("/app/query/loadAddress")
	public String loadAddress(HttpServletRequest request,HttpServletResponse response) throws IOException{
		Integer userRoleId,type,pageNum ,pageSize;
		HashMap<String,Object> map =new HashMap<String,Object>();
		if(!ServerContext.getUseronline().containsKey(request.getSession().getId().toString())){
	    	map.put("success", "false");
			map.put("msg","未登录！");
			return writeAjaxResponse(response, JSONUtil.getJson(map));
		//if(ServerContext.getUseronline().get(request.getSession().getId())!=null)
	    }
		
	    System.out.println(ServerContext.getUseronline().get(request.getSession().getId().toString()).getFuserId());
	    userRoleId =ServerContext.getUseronline().get(request.getSession().getId().toString()).getFuserId();
	    HashMap<String, Object> m = new HashMap<String, Object>();
	    if(userRoleId==null || userRoleId.equals("")){
	    	m.put("success", "true");
			m.put("total", 0);
			m.put("data", new ArrayList<>());
			return writeAjaxResponse(response, JSONUtil.getJson(m));
	    }
		
		if(request.getParameter("type")==null || "".equals(request.getParameter("type"))){
	    	map.put("success", "false");
			map.put("msg","未登录！");
			return writeAjaxResponse(response, JSONUtil.getJson(map));
	    }else{
	    	type = Integer.valueOf(request.getParameter("type"));
	    }
		if(request.getParameter("pagenum")==null || "".equals(request.getParameter("pagenum"))){
	    	map.put("success", "false");
			map.put("msg","无法获知第几页");
			return writeAjaxResponse(response, JSONUtil.getJson(map));
	    }else{
	    	pageNum = Integer.valueOf(request.getParameter("pagenum"));
	    }
		if(request.getParameter("pagesize")==null || "".equals(request.getParameter("pagesize"))){
	    	map.put("success", "false");
			map.put("msg","无法获知每页显示多少条");
			return writeAjaxResponse(response, JSONUtil.getJson(map));
	    }else{
	    	pageSize = Integer.valueOf(request.getParameter("pagesize"));
	    }
		if (addressQuery == null) {
			addressQuery = newQuery(AddressQuery.class, null);
		}
		if (pageNum != null) {
			addressQuery.setPageNumber(pageNum);
		}
		if (pageSize != null) {
			addressQuery.setPageSize(pageSize);
		}
		addressQuery.setUserRoleId(userRoleId);
		addressQuery.setType(type);
		Page<CL_Address> page = addressDao.findPage(addressQuery);
//		HashMap<String, Object> m = new HashMap<String, Object>();
			m.put("success", "true");
			m.put("total", page.getTotalCount());
			m.put("data", page.getResult());
			System.out.println(m.get("data"));
		return writeAjaxResponse(response, JSONUtil.getJson(m));
	}
	
	/*** APP-新增地址 */
	@RequestMapping("/app/insert/address")
	public String address(HttpServletRequest request,HttpServletResponse response) throws IOException{
		HashMap<String,Object> map =new HashMap<String,Object>();
		Integer userRoleId,type ;
		String linkman,linkphone = null,linkaddress = null,latitude = null,longitude = null;
		if(!ServerContext.getUseronline().containsKey(request.getSession().getId().toString())){
	    	map.put("success", "false");
			map.put("msg","未登录！");
			return writeAjaxResponse(response, JSONUtil.getJson(map));
		//if(ServerContext.getUseronline().get(request.getSession().getId())!=null)
	    }
		
	    System.out.println(ServerContext.getUseronline().get(request.getSession().getId().toString()).getFuserId());
	    userRoleId =ServerContext.getUseronline().get(request.getSession().getId().toString()).getFuserId();
		if(request.getParameter("type")==null || "".equals(request.getParameter("type"))){
	    	map.put("success", "false");
			map.put("msg","无法获知是收获地址还是发货地址！");
			return writeAjaxResponse(response, JSONUtil.getJson(map));
	    }else{
	    	type = Integer.valueOf(request.getParameter("type"));
	    }
		if(request.getParameter("linkman")==null || "".equals(request.getParameter("linkman"))){
	    	map.put("success", "false");
			map.put("msg","联系人不能为空！");
			return writeAjaxResponse(response, JSONUtil.getJson(map));
	    }else{
	    	linkman = request.getParameter("linkman");
	    }
	    if(request.getParameter("linkphone")==null || "".equals(request.getParameter("linkphone"))){
	    	map.put("success", "false");
			map.put("msg","联系人电话不能为空！");
			return writeAjaxResponse(response, JSONUtil.getJson(map));
	    }else{
	    	linkphone = request.getParameter("linkphone");
	    }
	    if(request.getParameter("linkaddress")==null || "".equals(request.getParameter("linkaddress"))){
	    	map.put("success", "false");
			map.put("msg","联系地址不能为空！");
			return writeAjaxResponse(response, JSONUtil.getJson(map));
	    }else{
	    	linkaddress = request.getParameter("linkaddress");
	    }
	    if(request.getParameter("latitude")==null || "".equals(request.getParameter("latitude"))){
	    	map.put("success", "false");
			map.put("msg","经度不能为空！");
			return writeAjaxResponse(response, JSONUtil.getJson(map));
	    }else{
	    	latitude = request.getParameter("latitude");
	    }
	    if(request.getParameter("longitude")==null || "".equals(request.getParameter("longitude"))){
	    	map.put("success", "false");
			map.put("msg","纬度不能为空！");
			return writeAjaxResponse(response, JSONUtil.getJson(map));
	    }else{
	    	longitude = request.getParameter("longitude");
	    }
	    CL_Address ad  = new CL_Address();
	    ad.setType(type);
	    ad.setUserRoleId(userRoleId);
	    ad.setAddressName(linkaddress.replaceAll("[\\ud800\\udc00-\\udbff\\udfff\\ud800-\\udfff]", ""));
	    ad.setLinkMan(linkman);
	    ad.setLinkPhone(linkphone);
	    ad.setLatitude(latitude);
	    ad.setLongitude(longitude);
	    ad.setCreateTime(new Date());
	    ad.setLastEditTime(new Date());
	    this.addressDao.save(ad);
	    map.put("success", "true");
		map.put("msg","操作成功！");
		return writeAjaxResponse(response, JSONUtil.getJson(map));
	}
	
	/*** APP-修改*/
	@RequestMapping("/app/appaddress/update")
	public String update(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		HashMap<String, Object> map = new HashMap<String, Object>();
		// 禁止地址修改
		// map.put("success", "false");
		// map.put("msg","地址不能修改哦！");
		// return writeAjaxResponse(response, JSONUtil.getJson(map));
		Integer userRoleId, id;
		String linkman, linkphone = null, linkaddress = null, latitude = null, longitude = null;
		if (!ServerContext.getUseronline().containsKey(
				request.getSession().getId().toString())) {
			map.put("success", "false");
			map.put("msg", "未登录！");
			return writeAjaxResponse(response, JSONUtil.getJson(map));
		}

		System.out.println(ServerContext.getUseronline()
				.get(request.getSession().getId().toString()).getFuserId());
		userRoleId = ServerContext.getUseronline()
				.get(request.getSession().getId().toString()).getFuserId();
		if (request.getParameter("id") == null
				|| "".equals(request.getParameter("id"))) {
			map.put("success", "false");
			map.put("msg", "请填写修改数据！");
			return writeAjaxResponse(response, JSONUtil.getJson(map));
		} else {
			id = Integer.valueOf(request.getParameter("id").toString());
		}
		if (request.getParameter("linkman") == null
				|| "".equals(request.getParameter("linkman"))) {
			map.put("success", "false");
			map.put("msg", "联系人不能为空！");
			return writeAjaxResponse(response, JSONUtil.getJson(map));
		} else {
			linkman = request.getParameter("linkman");
		}
		if (request.getParameter("linkphone") == null
				|| "".equals(request.getParameter("linkphone"))) {
			map.put("success", "false");
			map.put("msg", "联系人电话！");
			return writeAjaxResponse(response, JSONUtil.getJson(map));
		} else {
			linkphone = request.getParameter("linkphone");
		}
		/*
		 * if(request.getParameter("linkaddress")==null ||
		 * "".equals(request.getParameter("linkaddress"))){ map.put("success",
		 * "false"); map.put("msg","联系人不能为空！"); return
		 * writeAjaxResponse(response, JSONUtil.getJson(map)); }else{
		 * linkaddress = request.getParameter("linkaddress"); }
		 * if(request.getParameter("latitude")==null ||
		 * "".equals(request.getParameter("latitude"))){ map.put("success",
		 * "false"); map.put("msg","纬度不能为空！"); return
		 * writeAjaxResponse(response, JSONUtil.getJson(map)); }else{ latitude =
		 * request.getParameter("latitude"); }
		 * if(request.getParameter("longitude")==null ||
		 * "".equals(request.getParameter("longitude"))){ map.put("success",
		 * "false"); map.put("msg","经度不能为空！"); return
		 * writeAjaxResponse(response, JSONUtil.getJson(map)); }else{ longitude
		 * = request.getParameter("longitude"); }
		 */
		CL_Address ad = addressDao.getById(id);
		// ad.setAddressName(linkaddress.replaceAll("[\\ud800\\udc00-\\udbff\\udfff\\ud800-\\udfff]",
		// ""));
		ad.setLinkMan(linkman);
		ad.setLinkPhone(linkphone);
		// ad.setLatitude(latitude);
		// ad.setLongitude(longitude);
		ad.setLastEditTime(new Date());
		this.addressDao.update(ad);
		map.put("success", "true");
		map.put("msg", "操作成功！");
		return writeAjaxResponse(response, JSONUtil.getJson(map));
	}
	
	/*** APP-删除地址 */
	@RequestMapping("/app/appaddress/delete")
	public String delete(HttpServletRequest request,HttpServletResponse response) throws IOException{
		HashMap<String,Object> map =new HashMap<String,Object>();
		Integer userRoleId,id;
		if(!ServerContext.getUseronline().containsKey(request.getSession().getId().toString())){
	    	map.put("success", "false");
			map.put("msg","未登录！");
			return writeAjaxResponse(response, JSONUtil.getJson(map));
		//if(ServerContext.getUseronline().get(request.getSession().getId())!=null)
	    }
	    if(request.getParameter("id")==null || "".equals(request.getParameter("id"))){
	    	map.put("success", "false");
			map.put("msg","请先选择修改数据");
			return writeAjaxResponse(response, JSONUtil.getJson(map));
	    }else{
	    	id = Integer.valueOf(request.getParameter("id"));
	    }
		this.addressDao.deleteById(id);
	    map.put("success", "true");
		map.put("msg","操作成功！");
		return writeAjaxResponse(response, JSONUtil.getJson(map));
	}
	
	
	/*** APP-编辑读取地址 */
	@RequestMapping("/app/appaddress/edit")
	public String edit(HttpServletRequest request,HttpServletResponse response) throws IOException{
		HashMap<String,Object> map =new HashMap<String,Object>();
		Integer userRoleId,id;
		if(!ServerContext.getUseronline().containsKey(request.getSession().getId().toString())){
	    	map.put("success", "false");
			map.put("msg","未登录！");
			return writeAjaxResponse(response, JSONUtil.getJson(map));
		//if(ServerContext.getUseronline().get(request.getSession().getId())!=null)
	    }
	    if(request.getParameter("id")==null || "".equals(request.getParameter("id"))){
	    	map.put("success", "false");
			map.put("msg","请先选择修改数据");
			return writeAjaxResponse(response, JSONUtil.getJson(map));
	    }else{
	    	id = Integer.valueOf(request.getParameter("id"));
	    }
		CL_Address add=this.addressDao.getById(id);
	    map.put("success", "true");
		map.put("msg","操作成功！");
		map.put("data", add);
		return writeAjaxResponse(response, JSONUtil.getJson(map));
	}
}
