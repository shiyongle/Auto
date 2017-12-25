package com.pc.appInterface;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.pc.appInterface.api.DongjingClient;
import com.pc.controller.BaseController;
import com.pc.dao.Car.impl.CarDao;
import com.pc.dao.UserRole.impl.UserRoleDao;
import com.pc.dao.clUpload.impl.UploadDao;
import com.pc.model.CL_Car;
import com.pc.model.CL_UserRole;
import com.pc.model.Cl_Upload;
import com.pc.util.JSONUtil;
import com.pc.util.ServerContext;

@Controller
public class AppQueryController extends BaseController {
	@Resource
	private  UploadDao uploadDao;
	@Resource
	private  CarDao carDao;
	@Resource
	private UserRoleDao userRoleDao;

	/*** APP-首页轮播图片查询*/
	@RequestMapping("/app/query/loadImg")
	public String loadImg(HttpServletRequest request,HttpServletResponse response) throws IOException{
		HashMap<String,Object> map =new HashMap<String,Object>();
		Integer apptype;
		if(request.getParameter("apptype")==null || "".equals(request.getParameter("apptype"))){
	    	map.put("success", "false");
			map.put("msg","无法获知是车主还是货主");
			return writeAjaxResponse(response, JSONUtil.getJson(map));
	    }else{
	    	apptype = Integer.valueOf(request.getParameter("apptype"));
	    }
		
		List<Cl_Upload> ls =this.uploadDao.indexappByType(apptype);
		String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort();
		if(ls.size()>0){
			String url = "";
			for(Cl_Upload upload :ls){
				map.put("success", "true");
				map.put("msg","后台管理系统未上传图片！");
				url = url+",{\"url\":\""+basePath+upload.getUrl()+"\"}";
			}
			map.put("data", "["+url.substring(1, url.length())+"]");
		}else{
			map.put("success", "false");
			map.put("msg","后台管理系统未上传图片！");
		}
		System.out.println(map.get("data"));
		return writeAjaxResponse(response, JSONUtil.getJson(map));
	}
	
	/*** APP-转介绍二维码链接*/
	@RequestMapping("/app/query/loadQrcode")
	public String loadQrcode(HttpServletRequest request,HttpServletResponse response) throws IOException{
		String urid="";
		HashMap<String,Object> map =new HashMap<String,Object>();
		if(request.getParameter("urid")==null || "".equals(request.getParameter("urid"))){
	    	map.put("success", "false");
			map.put("msg","请先登录");
			return writeAjaxResponse(response, JSONUtil.getJson(map));
	    }else{
	    	urid =request.getParameter("urid");
	    }
//		String sessionid=request.getSession().getId();
//		Util_UserOnline online=ServerContext.getUseronline().get(sessionid);
		map.put("success", "true");
//		map.put("data", "http://192.168.1.14:8080/cscl/pages/app/register/inviteReg.html?fapptype=1&type=invite&urid="+urid);
//		map.put("data", "http://127.0.0.1:8080/cscl/pages/app/register/inviteReg.html?fapptype=1&type=invite&urid="+urid);
		map.put("data", "http://wl.olcps.com:8080/cscl/pages/app/register/inviteReg.html?fapptype=1&type=invite&urid="+urid);
		return writeAjaxResponse(response, JSONUtil.getJson(map));
	}
	

	/** 转介绍获取转介绍列表 */
	@RequestMapping("/app/user/getInviteeList")
	public String getInviteeList(HttpServletRequest request,HttpServletResponse response){
		HashMap<String,Object> map =new HashMap<String,Object>();
		String fapptype=""; 
		if(request.getParameter("fapptype")==null || "".equals(request.getParameter("fapptype"))){
			map.put("success", "false");
			map.put("msg","无法获知App类型！");
			return writeAjaxResponse(response, JSONUtil.getJson(map));
		}else{
			fapptype=request.getParameter("fapptype");
		}
		DongjingClient djcn = ServerContext.createVmiClient();
		djcn.setMethod("getInviteeList");
		djcn.setRequestProperty("fapptype", fapptype);
		djcn.SubmitData();
		net.sf.json.JSONObject jo = net.sf.json.JSONObject.fromObject(djcn.getResponse().getResultString());
		JSONArray array = JSONArray.fromObject(jo.get("invitelist"));
		map.put("data", array);
		map.put("success", "true");
		return writeAjaxResponse(response, JSONUtil.getJson(map));
	}
	
	
	/** 转介绍获取当前用户下线，即被邀请人列表 */
	@RequestMapping("/app/user/getInviteeListById")
	public String getInviteeListById(HttpServletRequest request,HttpServletResponse response){
		HashMap<String,Object> map =new HashMap<String,Object>();
		String fid="", fapptype=""; 
		if(request.getParameter("fid")==null || "".equals(request.getParameter("fid"))){
	    	map.put("success", "false");
			map.put("msg","请先登录！");
			return writeAjaxResponse(response, JSONUtil.getJson(map));
	    }else{
	    	fid=request.getParameter("fid");
	    }
		if(request.getParameter("fapptype")==null || "".equals(request.getParameter("fapptype"))){
	    	map.put("success", "false");
			map.put("msg","无法获知App类型！");
			return writeAjaxResponse(response, JSONUtil.getJson(map));
	    }else{
	    	fapptype=request.getParameter("fapptype");
	    }
		DongjingClient djcn = ServerContext.createVmiClient();
		djcn.setMethod("getInviteeListById");
		djcn.setRequestProperty("fid", fid);
		djcn.setRequestProperty("fapptype", fapptype);
		djcn.SubmitData();
		net.sf.json.JSONObject jo = net.sf.json.JSONObject.fromObject(djcn.getResponse().getResultString());
		JSONArray array = JSONArray.fromObject(jo.get("invitelist"));
		map.put("data", array);
		map.put("success", "true");
		return writeAjaxResponse(response, JSONUtil.getJson(map));
	}
	
	
	/** 转介绍获取当前用户的邀请人 */
	@RequestMapping("/app/user/getInviter")
	public String getInviter(HttpServletRequest request,HttpServletResponse response){
		HashMap<String,Object> map =new HashMap<String,Object>();
		String fid="", fapptype=""; //物流，平台
		if(request.getParameter("fid")==null || "".equals(request.getParameter("fid"))){
	    	map.put("success", "false");
			map.put("msg","请先登录！");
			return writeAjaxResponse(response, JSONUtil.getJson(map));
	    }else{
	    	fid=request.getParameter("fid");
	    }
		if(request.getParameter("fapptype")==null || "".equals(request.getParameter("fapptype"))){
	    	map.put("success", "false");
			map.put("msg","无法获知App类型！");
			return writeAjaxResponse(response, JSONUtil.getJson(map));
	    }else{
	    	fapptype=request.getParameter("fapptype");
	    }
		DongjingClient djcn = ServerContext.createVmiClient();
		djcn.setMethod("getInviter");
		djcn.setRequestProperty("fid", fid);
		djcn.setRequestProperty("fapptype", fapptype);
		djcn.SubmitData();
		net.sf.json.JSONObject jo = net.sf.json.JSONObject.fromObject(djcn.getResponse().getResultString());
		JSONArray array = JSONArray.fromObject(jo.get("invite"));
		String fuserid = JSONObject.fromObject(array.get(0)).get("fuserid").toString();
		String fcreatetime= JSONObject.fromObject(array.get(0)).get("fcreatetime").toString();
		String fbonus=JSONObject.fromObject(array.get(0)).get("fbonus").toString();
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		String createtime=sdf.format(Long.parseLong(fcreatetime.toString()));
		map.put("data", "[{\"fuserid\":\""+fuserid+"\",\"fcreatetime\":\""+createtime+"\",\"fbonus\":\""+fbonus+"\"}]");
		map.put("success", "true");
		return writeAjaxResponse(response, JSONUtil.getJson(map));
	}
	
	
	
	
	/**
	 * 获取当前用户的财务帐号信息
	 */
	@RequestMapping("/app/user/getAccount")
	public String getAccount(HttpServletRequest request,HttpServletResponse response){
		HashMap<String,Object> map =new HashMap<String,Object>();
		Integer urid; 
		if(request.getParameter("urid")==null || "".equals(request.getParameter("urid"))){
	    	map.put("success", "false");
			map.put("msg","请先登录！");
			return writeAjaxResponse(response, JSONUtil.getJson(map));
	    }else{
	    	urid=Integer.parseInt(request.getParameter("urid"));
	    }
		CL_UserRole csclUser = userRoleDao.getById(urid);
		if(csclUser.getRoleId()!=2)
		{
			map.put("msg", "用户角色不是车主");
			map.put("success", "false");
			return writeAjaxResponse(response, JSONUtil.getJson(map));
		}
		List<CL_Car> car=carDao.getByUserRoleId(csclUser.getId());
		if(car==null)
		{
			map.put("msg", "该车主未经过认证");
			map.put("success", "false");
			return writeAjaxResponse(response, JSONUtil.getJson(map));
		}
//		map.put("data", "[{\"username\":\""+(csclUser.getFname()==null?"":csclUser.getFname())+"\",\"alipay\":\""+(csclUser.getAlipayId()==null?"":csclUser.getAlipayId())+"\",\"bankaccount\":\""+(csclUser.getBankAccount()==null?"":csclUser.getBankAccount())+"\",\"bankname\":\""+(csclUser.getBankName()==null?"":csclUser.getBankName())+"\",\"bankaddress\":\""+(csclUser.getBankAddress()==null?"":csclUser.getBankAddress())+"\"}]");
		map.put("data", "[{\"username\":\""+(car.get(0).getDriverName()==null?"":car.get(0).getDriverName())+"\",\"alipay\":\""+(csclUser.getAlipayId()==null?"":csclUser.getAlipayId())+"\",\"bankaccount\":\""+(csclUser.getBankAccount()==null?"":csclUser.getBankAccount())+"\",\"bankname\":\""+(csclUser.getBankName()==null?"":csclUser.getBankName())+"\",\"bankaddress\":\""+(csclUser.getBankAddress()==null?"":csclUser.getBankAddress())+"\"}]");
		map.put("success", "true");
		return writeAjaxResponse(response, JSONUtil.getJson(map));
	}
	
	
	
}
