package com.pc.pcWeb.userRole;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.org.rapid_framework.page.Page;

import com.pc.appInterface.api.DongjingClient;
import com.pc.controller.BaseController;
import com.pc.dao.UserRole.impl.UserRoleDao;
import com.pc.dao.identification.impl.IdentificationDao;
import com.pc.dao.usercustomer.impl.UserCustomerDao;
import com.pc.model.CL_Identification;
import com.pc.model.CL_UserCustomer;
import com.pc.model.CL_UserDiary;
import com.pc.model.CL_UserRole;
import com.pc.model.Util_Option;
import com.pc.model.Util_UserOnline;
import com.pc.query.userRole.UserRoleQuery;
import com.pc.util.JSONUtil;
import com.pc.util.MD5Util;
import com.pc.util.RedisUtil;
import com.pc.util.ServerContext;
import com.pc.util.pay.PayUtil;

@Controller
public class pcWebuserRoleController extends BaseController {
	protected static final String LIST_JSP= "/pages/pcWeb/login/login_in.jsp";
	protected static final String INDEX_JSP= "/pages/pcWeb/menu/menu_center.jsp";
	protected static final String EDIT_JSP= "/pages/pcWeb/login/login_in.jsp";
	protected static final String identity_driver_JSP= "/pages/pcWeb/register_iden/identity_driver.jsp";//司机认证界面
	protected static final String identity_person_JSP= "/pages/pcWeb/register_iden/identity_person.jsp";//货主认证界面
	protected static final String customer_JSP= "/pages/pcWeb/index/index_customer.jsp";//货主（个人）
	protected static final String customer_qy_JSP= "/pages/pcWeb/index/index_customer_qy.jsp";//货主（企业）
	protected static final String customer_driver_JSP= "/pages/pcWeb/index/index_customer_driver.jsp";//司机
	
	@Resource
	private UserRoleDao userRoleDao;
	@Resource
	private UserCustomerDao userCustDao;
	@Resource
	private IdentificationDao identificationDao ;
//	private UserRoleQuery userRoleQuery;
	
//	public UserRoleQuery getUserRoleQuery() {
//		return userRoleQuery;
//	}
//	public void setUserRoleQuery(UserRoleQuery userRoleQuery) {
//		this.userRoleQuery = userRoleQuery;
//	}
	
	//列表界面
	@RequestMapping("pcWeb/user/login")
	public String list(HttpServletRequest request,HttpServletResponse reponse) throws Exception{
		return LIST_JSP;
	}
	
	//加载列表数据
	@RequestMapping("pcWeb/user/load")
	public String load(HttpServletRequest request,HttpServletResponse reponse,@ModelAttribute UserRoleQuery userRoleQuery) throws Exception{
		String pageNum = request.getParameter("page");
		String pageSize = request.getParameter("rows");
		String vmiUserName = request.getParameter("vmiUserName");
		
		if (userRoleQuery == null) {
			userRoleQuery = newQuery(UserRoleQuery.class, null);
		}
		if (pageNum != null) {
			userRoleQuery.setPageNumber(Integer.parseInt(pageNum));
		}
		if (pageSize != null) {
			userRoleQuery.setPageSize(Integer.parseInt(pageSize));
		}
		if(vmiUserName!=null){
			userRoleQuery.setVmiUserName(vmiUserName);
		}
		//调度平台用户,只查询运营角色
//		userRoleQuery.setRoleId(3);
		Page<CL_UserRole> page = userRoleDao.findPage(userRoleQuery);
		HashMap<String, Object> m = new HashMap<String, Object>();
			m.put("total", page.getTotalCount());
			m.put("rows", page.getResult());
		return writeAjaxResponse(reponse, JSONUtil.getJson(m));
	}
	
	//加载子帐号列表数据
	@RequestMapping("pcWeb/user/loadSubNum")
	public String loadSubNum(HttpServletRequest request,HttpServletResponse response,@ModelAttribute UserRoleQuery userRoleQuery) throws Exception{
		String pageNum = request.getParameter("page");
		String pageSize = request.getParameter("rows");
		
		HashMap<String, Object> m = new HashMap<String, Object>();
		HashMap<String, Util_UserOnline> useronline = ServerContext.getUseronline();
		if(request.getSession().getId()==null || request.getSession().getId().equals("")){
			m.put("success", "false");
			m.put("msg","登录超时！");
			return writeAjaxResponse(response, JSONUtil.getJson(m));
		}
		Util_UserOnline uoinfo = useronline.get(request.getSession().getId().toString());
		
		if (userRoleQuery == null) {
			userRoleQuery = newQuery(UserRoleQuery.class, null);
		}
		if (pageNum != null) {
			userRoleQuery.setPageNumber(Integer.parseInt(pageNum));
		}
		if (pageSize != null) {
			userRoleQuery.setPageSize(Integer.parseInt(pageSize));
		}
		
		userRoleQuery.setFparentid(uoinfo.getFuserId());
		
		Page<CL_UserRole> page = userRoleDao.getByFparentid(userRoleQuery);
		
			m.put("total", page.getTotalCount());
			m.put("rows", page.getResult());
		return writeAjaxResponse(response, JSONUtil.getJson(m));
	}
	
	
	//校验输入的用户和密码是否存在
	@RequestMapping("pcWeb/user/checkuser")
	public String checkuser(HttpServletRequest request,HttpServletResponse reponse)throws Exception{
		Integer userId=null;
		String fname= request.getParameter("fname");
		System.out.println(fname);
		String fpassword= request.getParameter("fpassword");
		System.out.println(fpassword);
		DongjingClient a =ServerContext.createVmiClient();
		a.setMethod("csclLogon");
		a.setRequestProperty("fname", fname);
		a.setRequestProperty("fpassword", fpassword);
		a.SubmitData();
		JSONObject jbsc =net.sf.json.JSONObject.fromObject(a.getResponse().getResultString());
		if(("false").equals(jbsc.get("success"))){
			return writeAjaxResponse(reponse,JSONUtil.getJson("failure"));
		}else{
			HashMap<String,Object> m =new HashMap<String,Object>();
			String fid = JSONObject.fromObject(JSONArray.fromObject(jbsc.get("userlist")).get(0)).get("fid").toString();
			String fvmiName=JSONObject.fromObject(JSONArray.fromObject(jbsc.get("userlist")).get(0)).get("fname").toString();
			String fvmiPhone=JSONObject.fromObject(JSONArray.fromObject(jbsc.get("userlist")).get(0)).get("ftel").toString();
			CL_UserRole userRole= userRoleDao.getByVmiUserFidAndRoleId(fid, 3);
		   if(userRole==null){
			 CL_UserRole userRole1=new CL_UserRole();
			   userRole1.setCreateTime(new Date());
			   userRole1.setVmiUserFid(fid);
			   userRole1.setVmiUserName(fvmiName);
			   userRole1.setRoleId(3);
			   userRole1.setVmiUserPhone(fvmiPhone);
			   userRoleDao.save(userRole1);
		   } else if(userRole.getVmiUserName()==null || "".equals(userRole.getVmiUserName())){
			  userRole.setVmiUserName(fvmiName);
			  userRole.setVmiUserPhone(fvmiPhone);
			  userRoleDao.update(userRole);
		  }
		  //增加APP登录状态验证，通过缓存记录sessionID，后面接口验证缓存中是否有对应的sessionid，有表示已经登录   BY CC 2016-02-24 START
		  userId=userRoleDao.getByVmiUserFidAndRoleId(fid, 3).getId();
		  Util_UserOnline useronline=new Util_UserOnline();
		  useronline.setFsessionid(request.getSession().getId().toString());
		  useronline.setFuserid(fid);
		  useronline.setFusername(fname);
		  useronline.setFlogintime(new Date());
		  useronline.setFuserId(userId);
		  ServerContext.setUseronline(request.getSession().getId().toString(), useronline);
			  //增加APP登录状态验证，通过缓存记录sessionID，后面接口验证缓存中是否有对应的sessionid，有表示已经登录   BY CC 2016-02-24 END
		  m.put("success", "success");
		  m.put("fid", fid);
		  return writeAjaxResponse(reponse,JSONUtil.getJson(m));
		}
	}

	@RequestMapping("pcWeb/index_pcWeb")
	public String pcWeblogin(@ModelAttribute("user") CL_UserRole user,HttpServletRequest request) throws Exception{
		HttpSession session=request.getSession();
		if(user.getFname()!=null){
			String username=user.getFname();
			username=new String(username.getBytes("ISO-8859-1"),"utf-8");
			session.setAttribute("username", username);
			Util_UserOnline uoinfo = ServerContext.getUseronline().get(request.getSession().getId().toString());
			session.setAttribute("isSub", uoinfo.isSub());//当前登录用户是否为子帐号 
			session.setAttribute("isPassIdentify", uoinfo.getFuserHuoId());
			if(uoinfo.getFuserHuoId()!=null){
				if(uoinfo.getFuserHuoId()==1 || uoinfo.getFuserHuoId()==2 || uoinfo.getFuserHuoId()==3){
					return customer_JSP;
				}else if(uoinfo.getFuserHuoId()==4){
					return customer_JSP;
				}else if(uoinfo.getFuserHuoId()==5){
					return customer_qy_JSP;
				}
			}

			if(uoinfo.getFuserCarId()!=null){
				if(uoinfo.getFuserCarId()==1){
					return identity_driver_JSP;
				}else if(uoinfo.getFuserCarId()==2){
					return customer_driver_JSP;
				}
			}
			
			List<CL_UserRole> ls = this.userRoleDao.getByVmiUserNameAccurate(username);
//			if(ls.size()>0){
//				session.setAttribute("userId", ls.get(0).getVmiUserFid());//用户ID
//				session.setAttribute("userRoleId", ls.get(0).getId());//当前登录用户对应的角色ID
//			}
			// modify by les 2016-4-23  修正userRoleId 为运营角色
			if(ls.size()>0){
				session.setAttribute("userId", ls.get(0).getVmiUserFid());//用户ID				
				for(CL_UserRole ur : ls){
					if(ur.getRoleId()==3){
						session.setAttribute("userRoleId", ur.getId());//当前登录用户对应的角色ID
						break;
					}
				}
			}
		}
		return INDEX_JSP;
	}
	
	@RequestMapping("pcWeb/logout")
	public void logout(HttpServletRequest request,HttpServletResponse reponse) throws Exception{
		 HttpSession session=request.getSession();
		 //增加APP登录状态验证，通过缓存记录sessionID，后面接口验证缓存中是否有对应的sessionid，有表示已经登录   BY CC 2016-02-24 START
		ServerContext.delUseronline(session.getId().toString());
		 //增加APP登录状态验证，通过缓存记录sessionID，后面接口验证缓存中是否有对应的sessionid，有表示已经登录   BY CC 2016-02-24 end
		if(session.getAttribute("username")!= null){
		     session.invalidate();
		}
		  PrintWriter out = reponse.getWriter();
   	     out.write("<script type='text/javascript'>window.parent.frames.location.href = '../pages/pcWeb/login/login_in.jsp';</script>");
	}
	
	@RequestMapping("pcWeb/user/queryCarUser")
	public String queryCarUser(HttpServletRequest request,HttpServletResponse reponse,@ModelAttribute UserRoleQuery userRoleQuery){
		//只查询是车主的用户
		if (userRoleQuery == null) {
			userRoleQuery = newQuery(UserRoleQuery.class, null);
		}
		userRoleQuery.setRoleId(2);
		if(request.getParameter("roleid")!=null && "3".equals(request.getParameter("roleid"))){
			userRoleQuery.setRoleId(3);
		}
		List<CL_UserRole> ls = this.userRoleDao.find(userRoleQuery);
		List<Util_Option> options = new ArrayList<Util_Option>();
		if(ls.size()>0){
			
			 //直接取CL_UserRole表后加的字段vmi_user_name为name
			/*String fids ="";
			for(CL_UserRole user:ls){
				fids=fids+"'"+user.getVmiUserFid()+"',";
			}
			DongjingClient a =ServerContext.createVmiClient();
			a.setMethod("csclGetUserlist");
			a.setRequestProperty("fids", "WHERE fid IN ("+fids.substring(0,fids.length()-1)+")");
			a.SubmitData();
			JSONObject jbsc =net.sf.json.JSONObject.fromObject(a.getResponse().getResultString());
			JSONArray array = JSONArray.fromObject(jbsc.get("userlist"));
			for (int i =0;i<array.size();i++) {
				  ls.get(i).setFname(JSONObject.fromObject(array.get(i)).get("fname").toString());
				  ls.get(i).setVmiUserName(JSONObject.fromObject(array.get(i)).get("fname").toString());
				  ls.get(i).setVmiUserPhone(JSONObject.fromObject(array.get(i)).get("ftel").toString());
			}*/
			for(CL_UserRole userrole:ls){
				Util_Option o1 = new Util_Option();
				o1.setOptionId(userrole.getId());
				o1.setOptionName(userrole.getFname());
				options.add(o1);
			}
			Util_Option o = new Util_Option();
			o.setOptionName("请选择");
			o.setOptionId(-1);
			o.setOptionVal("请选择");
			options.add(0, o);
			return writeAjaxResponse(reponse,JSONUtil.getJson(options));
		}else{
			return writeAjaxResponse(reponse,JSONUtil.getJson(options));
		}
	}
	
	@RequestMapping("pcWeb/user/queryHuoUser")
	public String queryHuoUser(HttpServletResponse reponse,@ModelAttribute UserRoleQuery userRoleQuery){
		//只查询是货主的用户
		if (userRoleQuery == null) {
			userRoleQuery = newQuery(UserRoleQuery.class, null);
		}
		userRoleQuery.setRoleId(1);
		List<CL_UserRole> ls = this.userRoleDao.find(userRoleQuery);
		List<Util_Option> options = new ArrayList<Util_Option>();
		if(ls.size()>0){
			String fids ="";
			for(CL_UserRole user:ls){
				fids=fids+"'"+user.getVmiUserFid()+"',";
			}
			DongjingClient a =ServerContext.createVmiClient();
			a.setMethod("csclGetUserlist");
			a.setRequestProperty("fids", "WHERE fid IN ("+fids.substring(0,fids.length()-1)+")");
			a.SubmitData();
			JSONObject jbsc =net.sf.json.JSONObject.fromObject(a.getResponse().getResultString());
			JSONArray array = JSONArray.fromObject(jbsc.get("userlist"));
			for (int i =0;i<array.size();i++) {
				  ls.get(i).setFname(JSONObject.fromObject(array.get(i)).get("fname").toString());
				  ls.get(i).setVmiUserName(JSONObject.fromObject(array.get(i)).get("fname").toString());
				  ls.get(i).setVmiUserPhone(JSONObject.fromObject(array.get(i)).get("ftel").toString());
			}
			for(CL_UserRole userrole:ls){
				Util_Option o1 = new Util_Option();
				o1.setOptionId(userrole.getId());
				o1.setOptionName(userrole.getFname());
				options.add(o1);
			}
			Util_Option o = new Util_Option();
			o.setOptionName("请选择");
			o.setOptionId(-1);
			o.setOptionVal("请选择");
			options.add(0, o);
			return writeAjaxResponse(reponse,JSONUtil.getJson(options));
		}else{
			return writeAjaxResponse(reponse,JSONUtil.getJson(options));
		}
	}
	
	@RequestMapping("pcWeb/user/editAccount")
	public String editAccount(HttpServletRequest request,HttpServletResponse reponse,Integer id){
		CL_UserRole role = this.userRoleDao.getById(id);
		request.setAttribute("role", role);
		return EDIT_JSP;
	}
	
	@RequestMapping("pcWeb/user/updateAccount")
	public String updateAccount(HttpServletRequest request,HttpServletResponse reponse,@ModelAttribute CL_UserRole role){
		this.userRoleDao.updateAcount(role);
		return writeAjaxResponse(reponse, "success");
	}
	
	

	/**
	 * 转介绍注册接口
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("pcWeb/user/inviteReg")
	public String inviteReg(HttpServletRequest request,HttpServletResponse response){
		HashMap<String,Object> map =new HashMap<String,Object>();
		String ftel="",fcode="",password="",repassword="",fuserroleid="",fuserid="";
		if(request.getParameter("ftel")==null || "".equals(request.getParameter("ftel"))){
			map.put("success", "false");
			map.put("msg","手机号不能为空！");
			return writeAjaxResponse(response, JSONUtil.getJson(map));
		}else{
			ftel = request.getParameter("ftel");
		}
		if(request.getParameter("fuserroleid")==null || "".equals(request.getParameter("fuserroleid"))){
			map.put("success", "false");
			map.put("msg","用户角色ID不能为空！");
			return writeAjaxResponse(response, JSONUtil.getJson(map));
		}else{
			fuserroleid = request.getParameter("fuserroleid");
			CL_UserRole role=userRoleDao.getById(Integer.parseInt(fuserroleid));
			if(role!=null)
			{
				fuserid=role.getVmiUserFid();
			}else
			{
				fuserid="0";
			}
		}

		if(request.getParameter("fcode")==null || "".equals(request.getParameter("fcode"))){
			map.put("success", "false");
			map.put("msg","手机验证码不能为空！");
			return writeAjaxResponse(response, JSONUtil.getJson(map));
		}else{
			fcode = request.getParameter("fcode");
		}

		if(request.getParameter("password")==null || "".equals(request.getParameter("password"))){
			map.put("success", "false");
			map.put("msg","用户密码不能为空！");
			return writeAjaxResponse(response, JSONUtil.getJson(map));
		}else{
			password = request.getParameter("password");
		}

		if(request.getParameter("repassword")==null || "".equals(request.getParameter("repassword"))||!request.getParameter("repassword").equals(password)){
			map.put("success", "false");
			map.put("msg","两次密码输入不一致！");
			return writeAjaxResponse(response, JSONUtil.getJson(map));
		}else{
			repassword = request.getParameter("repassword");
		}
		DongjingClient djcn = ServerContext.createVmiClient();
		djcn.setMethod("inviteReg");
		djcn.setRequestProperty("fpassword", password);
		djcn.setRequestProperty("fuserroleid", fuserroleid);
		djcn.setRequestProperty("frepassword", repassword);
		djcn.setRequestProperty("ftel", ftel);
		djcn.setRequestProperty("fuserid", fuserid);
		djcn.setRequestProperty("fcode", fcode);
		djcn.SubmitData();
		net.sf.json.JSONObject jo = net.sf.json.JSONObject.fromObject(djcn.getResponse().getResultString());
		if("true".equals(jo.get("success"))){
//			CL_UserRole csclUser = new CL_UserRole();
//			JSONArray array = JSONArray.fromObject(jo.get("data"));
//			csclUser.setRoleId(0);
//			csclUser.setVmiUserFid(JSONObject.fromObject(array.get(0)).get("fid").toString());
//			csclUser.setVmiUserName(JSONObject.fromObject(array.get(0)).get("fname").toString());
//			csclUser.setVmiUserPhone(JSONObject.fromObject(array.get(0)).get("ftel").toString());
//			csclUser.setCreateTime(new Date());
//			this.userRoleDao.save(csclUser);
			map.put("success", "true");
			map.put("msg","注册成功！");
		}// 根据 vmi方法  reg 返回的msg 来判断是什么原因导致注册失败
		else if("验证码校验失败！".equals(jo.get("msg"))){
			map.put("success", "false");
			map.put("msg","验证码校验失败validefail！");
		}
		else {
			map.put("success", "false");
			map.put("msg",jo.get("msg"));
		}
		return writeAjaxResponse(response, JSONUtil.getJson(map));
	}
	
	//获取或者下拉列表数据源
	@RequestMapping("pcWeb/user/combogrid")
	public String getComboxGridData(HttpServletRequest request,HttpServletResponse response) throws UnsupportedEncodingException{
		String searchKey = "";
		if(request.getParameter("q")!=null){			
			searchKey = request.getParameter("q");
			searchKey=new String(searchKey.getBytes("ISO-8859-1"),"utf-8");
		}
		List<Map<String, Object>> resList = this.userRoleDao.getComboxGridData(searchKey);
		return writeAjaxResponse(response, JSONUtil.getJson(resList));
	}
	
	//设置帐号月结
	@RequestMapping("pcWeb/user/updateUserProtocol")
	public String editMonthPay(HttpServletRequest request,HttpServletResponse reponse,Integer id){
		CL_UserRole role = this.userRoleDao.getById(id);		
		if(role.isProtocol()){
			role.setProtocol(false);
		}else{
			role.setProtocol(true);
		}
		userRoleDao.updateProtocol(role);
		return writeAjaxResponse(reponse, "success");
	}
	
	
	//注册;
		@RequestMapping("/pcWeb/user/reg")
		public String reg(HttpServletRequest request,HttpServletResponse response) throws Exception{
			HashMap<String,Object> map =new HashMap<String,Object>();
			String fname="",ftel="",ftype="",fpassword="",identCode="";
			if(request.getParameter("ftel")==null || "".equals(request.getParameter("ftel"))){
				map.put("success", "false");
				map.put("msg","手机号不能为空！");
				return writeAjaxResponse(response, JSONUtil.getJson(map));
			}else{
				ftel = request.getParameter("ftel");
			}
			
//			if(request.getParameter("fname")==null || "".equals(request.getParameter("fname"))){
//				map.put("success", "false");
//				map.put("msg","用户名不能为空！");
//				return writeAjaxResponse(response, JSONUtil.getJson(map));
				fname = ftel;
//			}else{
//				fname = request.getParameter("fname");
//			}
			if(request.getParameter("ftype")==null || "".equals(request.getParameter("ftype"))){
				map.put("success", "false");
				map.put("msg","类型不能为空！");
				return writeAjaxResponse(response, JSONUtil.getJson(map));
			}else{
				ftype = request.getParameter("ftype");
			}

			if(request.getParameter("fpassword")==null || "".equals(request.getParameter("fpassword"))){
				map.put("success", "false");
				map.put("msg","用户密码不能为空！");
				return writeAjaxResponse(response, JSONUtil.getJson(map));
			}else{
				fpassword = request.getParameter("fpassword");
			}
			if(request.getParameter("identCode")==null || "".equals(request.getParameter("identCode"))){
		    	map.put("success", "false");
				map.put("msg","验证码不能为空！");
				return writeAjaxResponse(response,JSONUtil.getJson(map));
		    }else{
		    	identCode = request.getParameter("identCode");
		    }
			DongjingClient djcn = ServerContext.createVmiClient();
			djcn.setMethod("reg");
			djcn.setRequestProperty("fname", fname);
			djcn.setRequestProperty("ftel", ftel);
			djcn.setRequestProperty("fpassword", fpassword);
			djcn.setRequestProperty("ftype", "0");
			djcn.setRequestProperty("identCode", identCode);
			djcn.SubmitData();
			net.sf.json.JSONObject jo = net.sf.json.JSONObject.fromObject(djcn.getResponse().getResultString());
			if("true".equals(jo.get("success"))){
				CL_UserRole csclUser = null;
				JSONArray array = JSONArray.fromObject(jo.get("userlist"));
				csclUser = this.userRoleDao.getByVmiUserFidAndRoleId(JSONObject.fromObject(array.get(0)).get("fid").toString(), new Integer(ftype));
				if(csclUser==null){
					csclUser = new CL_UserRole();
					csclUser.setRoleId(new Integer(ftype));
					csclUser.setVmiUserFid(JSONObject.fromObject(array.get(0)).get("fid").toString());
					csclUser.setVmiUserName(JSONObject.fromObject(array.get(0)).get("fname").toString());
					csclUser.setVmiUserPhone(JSONObject.fromObject(array.get(0)).get("ftel").toString());
					csclUser.setCreateTime(new Date());
					csclUser.setFpaypassword(JSONObject.fromObject(array.get(0)).get("fpassword").toString());
					csclUser.setFbalance(new BigDecimal(0));
					this.userRoleDao.save(csclUser);
					
					Util_UserOnline useronline = new Util_UserOnline();
					useronline.setFsessionid(request.getSession().getId().toString());
					useronline.setFuserid(JSONObject.fromObject(array.get(0)).get("fid").toString());
					useronline.setFusername(fname);
					useronline.setFlogintime(new Date());
				    useronline.setFuserId(csclUser.getId());
				    useronline.setSub(false);
					useronline.setFsubId(null);
					useronline.setFsubTel(null);
					ServerContext.setUseronline(request.getSession().getId().toString(), useronline);
				}
				map.put("success", "true");
				map.put("msg","注册成功！");
				map.put("data", "[{\"id\":\""+csclUser.getId()+"\",\"fname\":\""+fname+"\"}]");
			}// 根据 vmi方法  reg 返回的msg 来判断是什么原因导致注册失败
			else if("该手机账号已被注册！".equals(jo.get("msg"))){
				map.put("success", "false");
				map.put("msg","该手机账号已被注册！");
			}
			else if("该用户名已被注册！".equals(jo.get("msg"))){
				map.put("success", "false");
				map.put("msg","该用户名已被注册！");
			}else if("验证码校验失败！".equals(jo.get("msg"))){
				map.put("success", "false");
				map.put("msg","验证码校验失败！");
			}
			else {
				map.put("success", "false");
				map.put("msg",jo.get("msg"));
			}
			return writeAjaxResponse(response, JSONUtil.getJson(map));
		}
		/**
		 * 注册时，获取验证码;
		 * **/
		@RequestMapping("/pcWeb/user/getRegValidateVCodeTel")
		public String getRegValidateVCodeTel(HttpServletRequest request,HttpServletResponse response) throws Exception{
			HashMap<String,Object> map =new HashMap<String,Object>();
			try {
				String tel = ""; 
				if(request.getParameter("ftel")!=null && !"".equals(request.getParameter("ftel"))){
					tel = request.getParameter("ftel");
				}else{
					map.put("success", "false");
					map.put("msg","请输入手机号");
					return writeAjaxResponse(response, JSONUtil.getJson(map));
				}

				DongjingClient djcn = ServerContext.createVmiClient();
				djcn.setMethod("getRegValidateVCodeTel");
				djcn.setRequestProperty("ftel", tel);
				djcn.SubmitData();
				net.sf.json.JSONObject jo = net.sf.json.JSONObject.fromObject(djcn.getResponse().getResultString());
				if("true".equals(jo.get("success"))){
					map.put("success", "true");
					map.put("msg",jo.get("msg"));
				}else{
					map.put("success", "false");
					map.put("msg",jo.get("msg"));
				}
				return writeAjaxResponse(response, JSONUtil.getJson(map));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				map.put("success", "false");
				map.put("msg","验证码发送失败！");
				return writeAjaxResponse(response, JSONUtil.getJson(map));
			}
		}
		/**用户注册验证码校验**/
		@RequestMapping("/pcWeb/user/regValiCode")
		public String regValiCode(HttpServletRequest request,HttpServletResponse response) throws IOException{
			HashMap<String,Object> map =new HashMap<String,Object>();
			String ftel = "",identCode=""; 
			if(request.getParameter("ftel")!=null && !"".equals(request.getParameter("ftel"))){
				ftel = request.getParameter("ftel");
			}else{
				map.put("success", "false");
				map.put("msg","请输入手机号");
				return writeAjaxResponse(response, JSONUtil.getJson(map));
			}

			if(request.getParameter("identCode")!=null && !"".equals(request.getParameter("identCode"))){
				identCode = request.getParameter("identCode");
			}else{
				map.put("success", "false");
				map.put("msg","请输入验证码");
				return writeAjaxResponse(response, JSONUtil.getJson(map));
			}
			DongjingClient djcn = ServerContext.createVmiClient();
			djcn.setMethod("regValiCode");
			djcn.setRequestProperty("ftel", ftel);
			djcn.setRequestProperty("identCode", identCode);
			djcn.SubmitData();
			net.sf.json.JSONObject jo = net.sf.json.JSONObject.fromObject(djcn.getResponse().getResultString());
			if("true".equals(jo.get("success"))){
				map.put("success", "true");
				map.put("msg",jo.get("msg"));
			}else{
				map.put("success", "false");
				map.put("msg",jo.get("msg"));
			}
			return writeAjaxResponse(response, JSONUtil.getJson(map));
		}
     
		/***PC第一步登录
		 *  查询登录用户有几种角色
		 *  若只有1种角色(司机或者货主),则判断其是否认证
		 *  若存在2中角色(既是司机又是货主),则调用app第二步
		 *  返回服务器loginStatus：
		 *  	为1时，跳到选择 角色的页面，
		 *      为2时，跳到 首页
		 *      为3时，跳到车主认证界面
		 *      为4时，跳到司机认证界面
		 * **/
		@RequestMapping("/pcWeb/user/logon")
		public String logon(HttpServletRequest request, HttpServletResponse response)
				throws Exception {
			HashMap<String, Object> map = new HashMap<String, Object>();
			String fname = "", fpassword = "";
			CL_UserDiary userDiary = new CL_UserDiary();
			if (request.getParameter("fname") == null
					|| "".equals(request.getParameter("fname"))) {
				map.put("success", "false");
				map.put("msg", "用户名不能为空！");
				return writeAjaxResponse(response, JSONUtil.getJson(map));
			} else {
				fname = request.getParameter("fname");
			}

			if (request.getParameter("fpassword") == null
					|| "".equals(request.getParameter("fpassword"))) {
				map.put("success", "false");
				map.put("msg", "用户密码不能为空！");
				return writeAjaxResponse(response, JSONUtil.getJson(map));
			} else {
				fpassword = request.getParameter("fpassword");
			}
			
			List<CL_UserRole> urlist = userRoleDao.getByFpassword(fname, fpassword);
			JSONArray jsonarray = new JSONArray();
			if(urlist.size()>0){
				CL_UserRole urinfo = urlist.get(0);
				int urId = urinfo.getId();
				
				//服务器维护判断开始
				/*if(RedisUtil.get("UpdateStartTime")!=null && RedisUtil.get("UpdateEndTime")!=null ){
					SimpleDateFormat formatTemp = new SimpleDateFormat("yyyy-MM-dd HH:mm");
					Date now = new Date();// 当前时间
					if (!(RedisUtil.get("allowPerson").contains(urId+""))) {
						if (formatTemp.parse(RedisUtil.get("UpdateStartTime")).before(now) && formatTemp.parse(RedisUtil.get("UpdateEndTime")).after(now)) {
							map.put("success", "false");
							map.put("msg", RedisUtil.get("UpdateStartTime") + "至" + RedisUtil.get("UpdateEndTime") + RedisUtil.get("contentkey"));
							return writeAjaxResponse(response, JSONUtil.getJson(map));
						}
					}
				}*/
				HashMap<String, Object> tm = null;
				tm = RedisUtil.IsSystemDefend(1,urId);
				if(tm != null){
					tm.put("success", "false2");
					return writeAjaxResponse(response, JSONUtil.getJson(tm));
				}
				//服务器维护判断结束
				
				Util_UserOnline useronline = new Util_UserOnline();
				useronline.setFsessionid(request.getSession().getId().toString());
				useronline.setFuserid(urinfo.getVmiUserFid());
				useronline.setFusername(fname);
				useronline.setFlogintime(new Date());
//				useronline.setVersionCode(versionCode);
//				useronline.setFsystem(fsystem);
				useronline.setFuserId(urinfo.getFparentid());
//				useronline.setFuserCarId(urId);
				useronline.setFuserHuoId(5);
				useronline.setSub(urinfo.isSub());
				useronline.setFsubId(urId);
				useronline.setFsubTel(urinfo.getVmiUserPhone());
				useronline.setVmiUserPhone(urinfo.getFparentTel());
				ServerContext.setUseronline(request.getSession().getId().toString(), useronline);
				JSONObject jsono = new JSONObject();
				jsono.put("vmiuserfid", urinfo.getVmiUserFid());
				jsono.put("vmiUserPhone",urinfo.getFparentTel());
				jsono.put("loginStatus", "2");
				jsono.put("ueserid", urId);
				jsono.put("rzty", "2");
				jsono.put("carType", urinfo.getFparentid());
				jsono.put("customername", urinfo.getVmiUserName());
				//增加是否子帐号用于判断协议下单；
				jsono.put("isSub", urinfo.isSub());
				jsonarray.add(jsono);
				map.put("success", "true");
				map.put("msg", "登录成功！");
				map.put("data", jsonarray.toString());
				
				userDiary.setFuserId(urId);
				userDiary.setFuserName(urinfo.getVmiUserName());
				userDiary.setFloginTime(new Date());
				userDiary.setFlastOperateTime(new Date());
				userDiary.setFsessionId(request.getSession().getId().toString());
				userRoleDao.saveUserDiary(userDiary);
				
			}else{
				DongjingClient djcn = ServerContext.createVmiClient();
				djcn.setMethod("csclLogon");
				djcn.setRequestProperty("fname", fname);
				djcn.setRequestProperty("fpassword", fpassword);
				djcn.SubmitData();
				net.sf.json.JSONObject jo = net.sf.json.JSONObject.fromObject(djcn.getResponse().getResultString());
				if ("true".equals(jo.get("success"))) {
					JSONArray array = JSONArray.fromObject(jo.get("userlist"));
					String vmiUserFid = JSONObject.fromObject(array.get(0)).get("fid").toString();
					String vmiUserName= JSONObject.fromObject(array.get(0)).get("fname").toString();
					String vmiUserPhone=JSONObject.fromObject(array.get(0)).get("ftel").toString();
//					PayUtil.UserCreate(vmiUserPhone,vmiUserFid);
					// 增加APP登录状态验证，通过缓存记录sessionID，后面接口验证缓存中是否有对应的sessionid，有表示已经登录 BY
					// CC 2016-02-24 START
					CL_UserRole  huoU=userRoleDao.getByVmiUserFidAndRoleId(vmiUserFid, 1);
					CL_UserRole  carU=userRoleDao.getByVmiUserFidAndRoleId(vmiUserFid, 2);
					Util_UserOnline useronline = new Util_UserOnline();
					useronline.setFsessionid(request.getSession().getId().toString());
					useronline.setFuserid(vmiUserFid);
					useronline.setFusername(fname);
					useronline.setFlogintime(new Date());
					useronline.setSub(false);
					useronline.setFsubId(null);
					useronline.setFsubTel(null);
					useronline.setVmiUserPhone(vmiUserPhone);
					
					if(carU!=null){
						map.put("success", "false1");
						map.put("msg", "司机不可登录");
						return writeAjaxResponse(response, JSONUtil.getJson(map));
//						useronline.setFuserCarId(1);
//						useronline.setFuserId(carU.getId());
					}
					if(huoU!=null){
						useronline.setFuserId(huoU.getId());
						useronline.setFuserHuoId(1);
					}
					
					//服务器维护判断开始
					/*if(RedisUtil.get("UpdateStartTime")!=null && RedisUtil.get("UpdateEndTime")!=null ){
						SimpleDateFormat formatTemp = new SimpleDateFormat("yyyy-MM-dd HH:mm");
						Date now = new Date();// 当前时间
						if (!(RedisUtil.get("allowPerson").contains(useronline.getFuserId()+""))) {
							if (formatTemp.parse(RedisUtil.get("UpdateStartTime")).before(now) && formatTemp.parse(RedisUtil.get("UpdateEndTime")).after(now)) {
								map.put("success", "false");
								map.put("msg", RedisUtil.get("UpdateStartTime") + "至" + RedisUtil.get("UpdateEndTime") + RedisUtil.get("contentkey"));
								return writeAjaxResponse(response, JSONUtil.getJson(map));
							}
						}
					}*/
					HashMap<String, Object> tm = null;
					tm = RedisUtil.IsSystemDefend(1,useronline.getFuserId());
					if(tm != null){
						tm.put("success", "false2");
						return writeAjaxResponse(response, JSONUtil.getJson(tm));
					}
					//服务器维护判断结束
					
					ServerContext.setUseronline(
							request.getSession().getId().toString(), useronline);
					// 增加APP登录状态验证，通过缓存记录sessionID，后面接口验证缓存中是否有对应的sessionid，有表示已经登录 BY
					// CC 2016-02-24 END
					List<CL_UserRole> ls = this.userRoleDao.getByVmiUserFid(vmiUserFid);
	
					// 修改登录返回数据，同时返回用户所有角色，由前端处理显示货主还是车主界面 BY CC 2016-02-24 START
					// String resultdata="[{";
//					JSONArray jsonarray = new JSONArray();
					if (ls.size() > 0) {
						map.put("success", "true");
						map.put("msg", "登录成功！");
						for (int i = 0; i < ls.size(); i++) {
							CL_UserRole userrole =ls.get(i);
							if(userrole.isSub()){
								userrole.setFparentTel(vmiUserPhone);
								userrole.setVmiUserName(userrole.getFname());
								this.userRoleDao.update(userrole);
								continue;
							}else{
								userrole.setVmiUserName(vmiUserName);
								userrole.setVmiUserPhone(vmiUserPhone);
							}
							this.userRoleDao.update(userrole);
							//平台角色 不登录APP端
							if(ls.get(i).getRoleId()==3)
							{
								continue;
							}
							JSONObject jsono = new JSONObject();
							List<CL_Identification> indent = this.identificationDao.getByUserRoleId(ls.get(i).getId());
							if (indent.size() > 0) {
								if (indent.get(0).getRoleId() == 2) {
									if (indent.get(0).getStatus() == 3) {
										jsono.put("loginStatus", "2");// 车主已认证
										useronline.setFuserCarId(2);
									}/*else if(indent.get(0).getStatus()==2){
										jsono.put("loginStatus", "3");// //被驳回 5 // （为兼容老版本 使用3  新版本 被驳回使用5）
									}*/
									else {
//										jsono.put("loginStatus", "3");// 车主未认证
										useronline.setFuserCarId(1);
									}
//									jsono.put("rzty", "0");
								} else if (indent.get(0).getRoleId() == 1) {
									int m = 0;
									int t = 0;// 认证类型
									if (indent.get(0).getType() == 1) {
										// 货主企业认证
										if (indent.get(0).getStatus() == 3) {
											m = 2;// 货主企业认证已认证
											t = 2;
											useronline.setFuserHuoId(5);
										}else if(indent.get(0).getStatus()==2){
											m = 3;//被驳回 5 // （为兼容老版本 使用3  新版本 被驳回使用5）
											if(indent.get(0).getPassIdentify()==1){
												t = 1;//已存在个人身份认证通过
												useronline.setFuserHuoId(4);
											}
										}
										else {
											m = 3;// 货主企业未认证
											if(indent.get(0).getPassIdentify()==1){
												t = 1;//已存在个人身份认证通过
												useronline.setFuserHuoId(4);
											}
										}
	
									} else if (indent.get(0).getType() == 2) {
										// 货主个人认证
										if (indent.get(0).getStatus() == 3) {
											m = 2;// 货主个人认证已认证
											t = 1;
											useronline.setFuserHuoId(4);
										} else if (indent.get(0).getStatus() == 0) {
											m = 2;// 货主个人认证跳过
											useronline.setFuserHuoId(1);
										}else if(indent.get(0).getStatus()==2){
											m = 3;//被驳回（为兼容老版本 使用3  新版本 被驳回使用5）
											useronline.setFuserHuoId(2);
										}
										else {
											m = 3;// 货主个人未认证
											useronline.setFuserHuoId(3);
										}
									}
									jsono.put("loginStatus", m + "");
									jsono.put("rzty", t + "");
								} else {
									map.put("success", "false");
									map.put("msg", "数据遗失,请联系客服");
								}
								jsono.put("vmiuserfid", vmiUserFid);
								jsono.put("vmiUserPhone", vmiUserPhone);
								jsono.put("ueserid", ls.get(i).getId());
								jsono.put("carType", ls.get(i).getRoleId());
	//							jsono.put("customername", indent.get(0).getName()==null?"":indent.get(0).getName());
								/*** 前端传递客户简称 Start */
								List<CL_UserCustomer> uclist=userCustDao.getByUrid(ls.get(i).getId());
								CL_UserCustomer uc=new CL_UserCustomer();
								if(uclist.size()!=0)
								{
									uc=uclist.get(0);
								}
								jsono.put("customername", uc.getFsimplename()==null?indent.get(0).getName()==null?"":indent.get(0).getName():uclist.get(0).getFsimplename());
								/*** 前端传递客户简称 End */
								
								jsonarray.add(jsono);
	
							} else {
								jsono.put("vmiuserfid", vmiUserFid);
								jsono.put("vmiUserPhone",vmiUserPhone);
								jsono.put("ueserid", ls.get(i).getId());
								jsono.put("carType", ls.get(i).getRoleId());
								jsono.put("loginStatus", "4");
								jsono.put("rzty", "0");
								jsono.put("customername", "");
								jsonarray.add(jsono);
							}
							userDiary.setFuserId(ls.get(i).getId());
							userDiary.setFuserName(ls.get(i).getVmiUserName());
							userDiary.setFloginTime(new Date());
							userDiary.setFlastOperateTime(new Date());
							userDiary.setFsessionId(request.getSession().getId().toString());
							userRoleDao.saveUserDiary(userDiary);
							
						}
	
					} 
					//如果平台人员自己登录 则新建一条默认为货主的
					if(ls.size()==0 || (ls.size()==1 &&ls.get(0).getRoleId()==3))
					{
						JSONObject jsono = new JSONObject();
						CL_UserRole userRole=new CL_UserRole();
						userRole.setCreateTime(new Date());
						userRole.setVmiUserFid(vmiUserFid);
						userRole.setRoleId(1);
						userRole.setVmiUserName(vmiUserName);
						userRole.setVmiUserPhone(vmiUserPhone);
						userRoleDao.save(userRole);
						userRole=userRoleDao.getByVmiUserFidAndRoleId(vmiUserFid, 1);
						
						//20160808 cd 平台运营第一次登录下面两个缓存字段会空;
						useronline.setFuserId(userRole.getId());
						useronline.setFuserHuoId(1);
						
						jsono.put("vmiuserfid", vmiUserFid);
						jsono.put("vmiUserPhone",vmiUserPhone);
						jsono.put("loginStatus", "4");
						jsono.put("ueserid", userRole.getId());
						jsono.put("rzty", "0");
						jsono.put("carType", userRole.getRoleId());
						jsono.put("customername", "");
						jsonarray.add(jsono);
						map.put("success", "true");
						map.put("msg", "登录成功！");
						
						userDiary.setFuserId(userRole.getId());
						userDiary.setFuserName(vmiUserName);
						userDiary.setFloginTime(new Date());
						userDiary.setFlastOperateTime(new Date());
						userDiary.setFsessionId(request.getSession().getId().toString());
						userRoleDao.saveUserDiary(userDiary);
	
					}
					if (map.get("success").equals("true")) {
						map.put("data", jsonarray.toString());
					}
					// 修改登录返回数据，同时返回用户所有角色，由前端处理显示货主还是车主界面 BY CC 2016-02-24 END
	
					// 原来代码备份 start
					// if(ls.size()>1){
					// map.put("success", "true");
					// map.put("msg","请先选择角色！");
					// int n=1;//两种角色
					// map.put("data",
					// "[{\"loginStatus\":\""+n+"\",\"id\":\""+ls.get(0).getId()+"\",\"vmiuserfid\":\""+vmiUserFid+"\"}]");
					// }else if(ls.size() ==1){
					// List<CL_Identification> indent
					// =this.identificationDao.getByUserRoleId(ls.get(0).getId());
					// /***===========status 0:跳过,1:待审核,2:已驳回,3:通过=========****/
					// if(indent.size()>0){
					// map.put("success", "true");
					// map.put("msg","登录成功！");
					// int m = 0;
					// int t = 0;//认证类型
					// if(indent.get(0).getRoleId()==2){//车主认证信息
					// if(indent.get(0).getStatus()==3){
					// m=2;//车主已认证
					// t = 0;
					// }else{
					// m=3;//车主未认证
					// }
					// }else if(indent.get(0).getRoleId()==1 &&
					// indent.get(0).getType()==1){//货主企业认证
					// if(indent.get(0).getStatus()==3){
					// m=2;//货主企业认证已认证
					// t = 1;
					// }else{
					// m=3;//货主企业未认证
					// }
					//
					// }else if(indent.get(0).getRoleId()==1 &&
					// indent.get(0).getType()==2){//货主个人认证
					// if(indent.get(0).getStatus()==3){
					// m=2;//货主个人认证已认证
					// t = 2;
					// }else if(indent.get(0).getStatus()==0){
					// m=2;//货主个人认证跳过
					// t = 0;
					// }else{
					// m=3;//货主个人未认证
					// }
					// t =2;
					// }else{
					// map.put("success", "false");
					// map.put("msg","数据遗失,请联系客服");
					// }
					// map.put("data",
					// "[{\"loginStatus\":\""+m+"\",\"id\":\""+ls.get(0).getId()+"\",\"rztype\":\""+t+"\",\"vmiuserfid\":\""+vmiUserFid+"\"}]");
					// }else{
					// map.put("success", "true");
					// map.put("msg","登录成功！");
					// map.put("data",
					// "[{\"loginStatus\":\""+4+"\",\"id\":\""+ls.get(0).getId()+"\",\"vmiuserfid\":\""+vmiUserFid+"\"}]");
					// }
					//
					// }else{
					// map.put("success", "false");
					// map.put("msg","数据遗失,请联系客服");
					// }
					// 原来代码备份 end
	
				} else {
					map.put("success", "false");
					map.put("msg", jo.get("msg"));
				}
				
				}
			System.out.println(map.get("success"));
			System.out.println(map.get("msg"));
			System.out.println(map.get("data"));
	
			return writeAjaxResponse(response, JSONUtil.getJson(map));
		}
		
		/**
		 * 子帐号新增
		 * @param request
		 * @param response
		 * @return
		 * @throws Exception
		 */
		@RequestMapping("/pcWeb/user/subNumsave")
		public String subNumsave(HttpServletRequest request,HttpServletResponse response) throws Exception{
			HashMap<String,Object> map =new HashMap<String,Object>();
			String fname="",ftel="",ftype="",fpassword="",identCode="";
			if(request.getParameter("vmiUserPhone")==null || "".equals(request.getParameter("vmiUserPhone"))){
				map.put("success", "false");
				map.put("msg","手机号不能为空！");
				return writeAjaxResponse(response, JSONUtil.getJson(map));
			}else{
				ftel = request.getParameter("vmiUserPhone");
			}
			
			if(request.getParameter("vmiUserName")==null || "".equals(request.getParameter("vmiUserName"))){
				map.put("success", "false");
				map.put("msg","用户名不能为空！");
				return writeAjaxResponse(response, JSONUtil.getJson(map));
			}else{
				fname = request.getParameter("vmiUserName");
			}

			if(request.getParameter("fpassword")==null || "".equals(request.getParameter("fpassword"))){
				map.put("success", "false");
				map.put("msg","用户密码不能为空！");
				return writeAjaxResponse(response, JSONUtil.getJson(map));
			}else{
				fpassword = MD5Util.getMD5String(request.getParameter("fpassword"));
			}
			
			HashMap<String, Util_UserOnline> useronline = ServerContext.getUseronline();
			if(request.getSession().getId()==null || request.getSession().getId().equals("")){
				map.put("success", "false");
				map.put("msg","登录超时！");
				return writeAjaxResponse(response, JSONUtil.getJson(map));
			}
			Util_UserOnline uoinfo = useronline.get(request.getSession().getId().toString());
			if(uoinfo.isSub()){
				map.put("success", "false");
				map.put("msg","子帐号不能操作！");
				return writeAjaxResponse(response, JSONUtil.getJson(map));
			}
			
			List<CL_UserRole> urTellist = userRoleDao.getByPhone(ftel);
			if(urTellist.size()>0){
				map.put("success", "false");
				map.put("msg","该手机号用户已存在！");
				return writeAjaxResponse(response, JSONUtil.getJson(map));
			}
			
			List<CL_UserRole> urNamelist = userRoleDao.getByVmiUserNameAccurate(fname);
			if(urNamelist.size()>0){
				map.put("success", "false");
				map.put("msg","该用户名已存在！");
				return writeAjaxResponse(response, JSONUtil.getJson(map));
			}
			
			List<CL_Identification> indent = this.identificationDao.getByUserRoleId(uoinfo.getFuserId());
			if(indent!=null && indent.size()>0){
				if (indent.get(0).getType() == 1 && indent.get(0).getStatus() == 3) {
					CL_UserRole csclUser = new CL_UserRole();
					csclUser.setRoleId(1);
					csclUser.setVmiUserFid(uoinfo.getFuserid());
					csclUser.setVmiUserName(fname);
					csclUser.setVmiUserPhone(ftel);
					csclUser.setCreateTime(new Date());
					csclUser.setFpassword(MD5Util.getMD5String(fpassword));
					csclUser.setSub(true);
					csclUser.setFparentid(uoinfo.getFuserId());
					csclUser.setFparentTel(uoinfo.getVmiUserPhone());
					
					this.userRoleDao.save(csclUser);
					map.put("success", "true");
					map.put("msg","子帐号新增成功！");
					map.put("data", "[{\"id\":\""+csclUser.getId()+"\",\"fname\":\""+fname+"\"}]");
				}else{
					map.put("success", "false");
					map.put("msg","非企业认证，不能新增子帐号！");
				}
			}
			else{
				map.put("success", "false");
				map.put("msg","未认证帐号，不能新增子帐号！");
			}
		
			return writeAjaxResponse(response, JSONUtil.getJson(map));
		}
		
		/**
		 * 子帐号修改
		 * @param request
		 * @param response
		 * @return
		 * @throws Exception
		 */
		@RequestMapping("/pcWeb/user/updateSubNum")
		public String updateSubNum(HttpServletRequest request,HttpServletResponse response) throws Exception{
			HashMap<String,Object> map =new HashMap<String,Object>();
			String fname="",ftel="",fpassword="";
			int id ,userId;
			if(!ServerContext.getUseronline().containsKey(request.getSession().getId().toString())){
				map.put("success", "false");
				map.put("msg","未登录！");
				return writeAjaxResponse(response, JSONUtil.getJson(map));
			//if(ServerContext.getUseronline().get(request.getSession().getId())!=null)
		    }
		   userId =ServerContext.getUseronline().get(request.getSession().getId().toString()).getFuserId();
		
			if(request.getParameter("vmiUserPhone")!=null && !"".equals(request.getParameter("vmiUserPhone"))){
				ftel = request.getParameter("vmiUserPhone");
			}
		   	 
			if(request.getParameter("vmiUserName")!=null && !"".equals(request.getParameter("vmiUserName"))){
				fname = request.getParameter("vmiUserName");
			}

			if(request.getParameter("fpassword")!=null && !"".equals(request.getParameter("fpassword"))){
				
				fpassword =MD5Util.getMD5String(request.getParameter("fpassword")); 
			}

			if(request.getParameter("id")!=null && !"".equals(request.getParameter("id"))){
				
				id = new Integer(request.getParameter("id"));
			}else{
				map.put("success", "false");
				map.put("msg","操作失败,id不能为空！");
				return writeAjaxResponse(response, JSONUtil.getJson(map));
			}
			
			if(fname.equals("") && ftel.equals("") && fpassword.equals("")){
				map.put("success", "false");
				map.put("msg","操作失败,无修改内容！");
				return writeAjaxResponse(response, JSONUtil.getJson(map));
			}
			
			List<CL_UserRole> urTellist = userRoleDao.getByPhone(ftel);
			if(urTellist.size()==1){
				if(urTellist.get(0).getFparentid()!=userId){
					map.put("success", "false");
					map.put("msg","该手机号码已有企业用户管理！");
					return writeAjaxResponse(response, JSONUtil.getJson(map));
				}

			}
			List<CL_UserRole> urNamelist = userRoleDao.getByVmiUserNameAccurate(fname);
			if(urNamelist.size()>0){
				if(urNamelist.get(0).getFparentid()!=userId){
					map.put("success", "false");
					map.put("msg","该用户已有企业用户管理！");
					return writeAjaxResponse(response, JSONUtil.getJson(map));
				}
			}
			
			CL_UserRole subinfo = userRoleDao.getById(id);
			if(!fname.equals("")){
				subinfo.setVmiUserName(fname);
			}
			if(!ftel.equals("")){
				subinfo.setVmiUserPhone(ftel);
			}
			if(!fpassword.equals("")){
				subinfo.setFpassword(fpassword);
			}
			userRoleDao.updateSubNum(subinfo);
			map.put("success", "true");
			map.put("msg","子帐号修改成功！");
			return writeAjaxResponse(response, JSONUtil.getJson(map));
		}
		
		/**
		 * 子帐号删除
		 * @param request
		 * @param response
		 * @return
		 * @throws Exception
		 */
		@RequestMapping("/pcWeb/user/deleteSubNum")
		public String deleteSubNum(HttpServletRequest request,HttpServletResponse response) throws Exception{
			HashMap<String,Object> map =new HashMap<String,Object>();
			int id ;

			if(request.getParameter("id")!=null && !"".equals(request.getParameter("id"))){
				
				id = new Integer(request.getParameter("id"));
			}else{
				map.put("success", "false");
				map.put("msg","操作失败,id不能为空！");
				return writeAjaxResponse(response, JSONUtil.getJson(map));
			}
			
			HashMap<String, Util_UserOnline> useronline = ServerContext.getUseronline();
			if(request.getSession().getId()==null || request.getSession().getId().equals("")){
				map.put("success", "false");
				map.put("msg","登录超时！");
				return writeAjaxResponse(response, JSONUtil.getJson(map));
			}
			Util_UserOnline uoinfo = useronline.get(request.getSession().getId().toString());
			List<CL_UserRole> urlist = userRoleDao.getByFparentidAndSub(uoinfo.getFuserId(),id);
			if(urlist.size()>0){
				userRoleDao.deleteSubNum(urlist.get(0));
			}
			map.put("success", "true");
			map.put("msg","子帐号删除成功！");
			return writeAjaxResponse(response, JSONUtil.getJson(map));
		}
		
}
