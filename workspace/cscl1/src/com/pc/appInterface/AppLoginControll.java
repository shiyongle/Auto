package com.pc.appInterface;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.pc.appInterface.api.DongjingClient;
import com.pc.controller.BaseController;
import com.pc.dao.UUID.impl.UuidDao;
import com.pc.dao.UserRole.impl.UserRoleDao;
import com.pc.dao.identification.impl.IdentificationDao;
import com.pc.dao.refuse.IrefuseDao;
import com.pc.dao.usercustomer.impl.UserCustomerDao;
import com.pc.model.CL_Identification;
import com.pc.model.CL_Refuse;
import com.pc.model.CL_UserCustomer;
import com.pc.model.CL_UserDiary;
import com.pc.model.CL_UserRole;
import com.pc.model.CL_Uuid;
import com.pc.model.Util_UserOnline;
import com.pc.util.JSONUtil;
import com.pc.util.MD5Util;
import com.pc.util.RedisUtil;
import com.pc.util.ServerContext;

@Controller
public class AppLoginControll extends BaseController{
	@Resource
	private UserRoleDao userRoleDao;
	@Resource
	private  UserCustomerDao userCustDao;
	@Resource
	private IdentificationDao  identificationDao;
	@Resource
	private UuidDao uuidDao;
	@Resource
	private IrefuseDao refuseDao;

	//注册;
	@RequestMapping("/app/user/reg")
	public String reg(HttpServletRequest request,HttpServletResponse response) throws Exception{
		HashMap<String,Object> map =new HashMap<String,Object>();
//		String fname="",ftel="",ftype="",fpassword="",identCode="";
		String fname="",ftel="",ftype="",fpassword="";
		/*if(true){
			map.put("success", "false");
			map.put("msg","注册暂不对外开放！");
			return writeAjaxResponse(response, JSONUtil.getJson(map));
		}*/
		
		if(request.getParameter("ftel")==null || "".equals(request.getParameter("ftel"))){
			map.put("success", "false");
			map.put("msg","手机号不能为空！");
			return writeAjaxResponse(response, JSONUtil.getJson(map));
		}else{
			ftel = request.getParameter("ftel");
		}
//		if(request.getParameter("identCode")==null || "".equals(request.getParameter("identCode"))){
//	    	map.put("success", "false");
//			map.put("msg","验证码不能为空！");
//			return writeAjaxResponse(response,JSONUtil.getJson(map));
//	    }else{
//	    	identCode = request.getParameter("identCode");
//	    }
//		if(request.getParameter("fname")==null || "".equals(request.getParameter("fname"))){
//			map.put("success", "false");
//			map.put("msg","用户名不能为空！");
//			return writeAjaxResponse(response, JSONUtil.getJson(map));
			fname = ftel;
//		}else{
//			fname = request.getParameter("fname");
//		}
 
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

		DongjingClient djcn = ServerContext.createVmiClient();
		djcn.setMethod("reg");
		djcn.setRequestProperty("fname", fname);
		djcn.setRequestProperty("ftel", ftel);
		djcn.setRequestProperty("fpassword", fpassword);
		djcn.setRequestProperty("ftype", ftype);
//		djcn.setRequestProperty("identCode", identCode);
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
				csclUser.setFpaypassword(MD5Util.getMD5String(fpassword));
				this.userRoleDao.save(csclUser);
			}
			map.put("success", "true");
			map.put("msg","注册成功！");
			map.put("data", "[{\"id\":\""+csclUser.getId()+"\",\"fname\":\""+fname+"\"}]");
			
			Util_UserOnline useronline = new Util_UserOnline();
			useronline.setFsessionid(request.getSession().getId().toString());
			useronline.setFuserid(JSONObject.fromObject(array.get(0)).get("fid").toString());
			useronline.setFusername(fname);
			useronline.setFlogintime(new Date());
		    useronline.setFuserId(csclUser.getId());
		    useronline.setSub(false);
			useronline.setFsubId(null);
			useronline.setFsubTel(null);
			ServerContext.setUseronline(
					request.getSession().getId().toString(), useronline);
			
		}// 根据 vmi方法  reg 返回的msg 来判断是什么原因导致注册失败
		else if("该手机账号已被注册！".equals(jo.get("msg"))){
			map.put("success", "false");
			map.put("msg","该手机账号已被注册！");
		}
		else if("该用户名已被注册！".equals(jo.get("msg"))){
			map.put("success", "false");
			map.put("msg","该用户名已被注册！");
		}
		else {
			map.put("success", "false");
			map.put("msg",jo.get("msg"));
		}
		return writeAjaxResponse(response, JSONUtil.getJson(map));
	}

	/***APP第一步登录
	 *  查询登录用户有几种角色
	 *  若只有1种角色(司机或者货主),则判断其是否认证
	 *  若存在2中角色(既是司机又是货主),则调用app第二步
	 *  返回服务器loginStatus：
	 *  	为1时，跳到选择 角色的页面，
	 *      为2时，跳到 首页
	 *      为3时，跳到车主认证界面
	 *      为4时，跳到司机认证界面
	 * **/
	@RequestMapping("/app/user/logon")
	public String logon(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		HashMap<String, Object> map = new HashMap<String, Object>();
		
		String fname = "", fpassword = "",versionCode = "",fsystem="";
		CL_UserDiary userDiary = new CL_UserDiary();
		////记录APP系统版本号;
		if (request.getParameter("versionCode") != null
				&& !"".equals(request.getParameter("versionCode"))) {
			versionCode = request.getParameter("versionCode");
		}
		
		//记录苹果APP系统类型;
		if (request.getParameter("fversion") != null
				&& !"".equals(request.getParameter("fversion"))) {
			fsystem = request.getParameter("fversion");
		}
		
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
		
		List<CL_UserRole> urlist = userRoleDao.getByFpassword(fname, MD5Util.getMD5String(fpassword));
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
				return writeAjaxResponse(response, JSONUtil.getJson(tm));
			}
			//服务器维护判断结束
			
			Util_UserOnline useronline = new Util_UserOnline();
			useronline.setFsessionid(request.getSession().getId().toString());
			useronline.setFuserid(urinfo.getVmiUserFid());
			useronline.setFusername(fname);
			useronline.setFlogintime(new Date());
			useronline.setVersionCode(versionCode);
			useronline.setFsystem(fsystem);
			useronline.setFuserId(urinfo.getFparentid());
			useronline.setFuserCarId(urId);
			useronline.setFuserHuoId(urId);
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
			djcn.setRequestProperty("fpassword", MD5Util.getMD5String(fpassword));
			djcn.SubmitData();
			net.sf.json.JSONObject jo = net.sf.json.JSONObject.fromObject(djcn.getResponse().getResultString());
			if ("true".equals(jo.get("success"))) {
				JSONArray array = JSONArray.fromObject(jo.get("userlist"));
				String vmiUserFid = JSONObject.fromObject(array.get(0)).get("fid").toString();
				String vmiUserName= JSONObject.fromObject(array.get(0)).get("fname").toString();
				String vmiUserPhone=JSONObject.fromObject(array.get(0)).get("ftel").toString();
				String feffect = "0";
				feffect = JSONObject.fromObject(array.get(0)).get("feffect").toString();//是否启用、禁用	1禁用0启用
				if(feffect.equals("1")){
					map.put("success", "false");
					map.put("msg", "用户已经失效，请联系管理员！");
					return writeAjaxResponse(response, JSONUtil.getJson(map));
				}
				
				// 增加APP登录状态验证，通过缓存记录sessionID，后面接口验证缓存中是否有对应的sessionid，有表示已经登录 BY
				// CC 2016-02-24 START
				CL_UserRole  huoU=userRoleDao.getByVmiUserFidAndRoleId(vmiUserFid, 1);
				CL_UserRole  carU=userRoleDao.getByVmiUserFidAndRoleId(vmiUserFid, 2);
				Util_UserOnline useronline = new Util_UserOnline();
				useronline.setFsessionid(request.getSession().getId().toString());
				useronline.setFuserid(vmiUserFid);
				useronline.setFusername(fname);
				useronline.setFlogintime(new Date());
				useronline.setVersionCode(versionCode);
				useronline.setFsystem(fsystem);
				useronline.setSub(false);
				useronline.setFsubId(null);
				useronline.setFsubTel(null);
				useronline.setVmiUserPhone(vmiUserPhone);
				
				if(carU!=null){
					useronline.setFuserCarId(carU.getId());
					useronline.setFuserId(carU.getId());
				}
				if(huoU!=null){
					useronline.setFuserHuoId(huoU.getId());
					useronline.setFuserId(huoU.getId());
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
//				JSONArray jsonarray = new JSONArray();
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
									useronline.setFuserCarId(carU.getId());
									useronline.setFuserId(carU.getId());
								}else if(indent.get(0).getStatus()==2){
									jsono.put("loginStatus", "3");// //被驳回 5 // （为兼容老版本 使用3  新版本 被驳回使用5）
								}
								else {
									jsono.put("loginStatus", "3");// 车主未认证
								}
								jsono.put("rzty", "0");
							} else if (indent.get(0).getRoleId() == 1) {
								int m = 0;
								int t = 0;// 认证类型
								if (indent.get(0).getType() == 1) {
									// 货主企业认证
									if (indent.get(0).getStatus() == 3) {
										m = 2;// 货主企业认证已认证
										t = 2;
									}else if(indent.get(0).getStatus()==2){
										m = 3;//被驳回 5 // （为兼容老版本 使用3  新版本 被驳回使用5）
										if(indent.get(0).getPassIdentify()==1){
											t = 1;//已存在个人身份认证通过
										}
									}
									else {
										m = 3;// 货主企业未认证
										if(indent.get(0).getPassIdentify()==1){
											t = 1;//已存在个人身份认证通过
										}
									}

								} else if (indent.get(0).getType() == 2) {
									// 货主个人认证
									if (indent.get(0).getStatus() == 3) {
										m = 2;// 货主个人认证已认证
										t = 1;
									} else if (indent.get(0).getStatus() == 0) {
										m = 2;// 货主个人认证跳过
									}else if(indent.get(0).getStatus()==2){
										m = 3;//被驳回（为兼容老版本 使用3  新版本 被驳回使用5）
									}
									else {
										m = 3;// 货主个人未认证
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
					useronline.setFuserHuoId(userRole.getId());
					
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
		
//		Logger logger = Logger.getLogger(AppLoginControll.class);
//		logger.info(map.get("msg"));
//		logger.info(map.get("data"));
		
		return writeAjaxResponse(response, JSONUtil.getJson(map));
	}

	/**
	 * 注册时，获取验证码;
	 * **/
	@RequestMapping("/app/user/getRegValidateVCodeTel")
	public String getRegValidateVCodeTel(HttpServletRequest request,HttpServletResponse response) throws Exception{
		HashMap<String,Object> map =new HashMap<String,Object>();
		/*if(true){
			map.put("success", "false");
			map.put("msg","注册暂不对外开放！");
			return writeAjaxResponse(response, JSONUtil.getJson(map));
		}*/
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
	@RequestMapping("/app/user/regValiCode")
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
			//验证码长度需6位
			if(identCode.length() != 6){
				map.put("success", "false");
				map.put("msg","验证码格式不正确");
				return writeAjaxResponse(response, JSONUtil.getJson(map));
			}
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

	/**
	 * 找回密码时，获取验证码;
	 * **/
	@RequestMapping("/app/user/getFndValidateVCodeTel")
	public String getFndValidateVCodeTel(HttpServletRequest request,HttpServletResponse response) throws Exception{
		HashMap<String,Object> map =new HashMap<String,Object>();
		try {
			String tel = "",name = ""; 
			if(request.getParameter("ftel")!=null && !"".equals(request.getParameter("ftel"))){
				tel = request.getParameter("ftel");
			}else{
				map.put("success", "false");
				map.put("msg","请输入手机号");
				return writeAjaxResponse(response, JSONUtil.getJson(map));
			}

//			if(request.getParameter("fname")!=null && !"".equals(request.getParameter("fname"))){
//				name = request.getParameter("fname");
//			}else{
//				map.put("success", "false");
//				map.put("msg","请输入用户名");
//				return writeAjaxResponse(response, JSONUtil.getJson(map));
//			}

			DongjingClient djcn = ServerContext.createVmiClient();
			djcn.setMethod("getFndValidateVCodeTel");
//			djcn.setRequestProperty("fname", name);
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

	/***找回密码时，验证码校验*/
	@RequestMapping("/app/user/checkLVC")
	public String checkLVC(HttpServletRequest request,HttpServletResponse response) throws Exception{
		HashMap<String,Object> map =new HashMap<String,Object>();
		String tel = "",lvc=""; 
		if(request.getParameter("ftel")!=null && !"".equals(request.getParameter("ftel"))){
			tel = request.getParameter("ftel");
		}else{
			map.put("success", "false");
			map.put("msg","请输入手机号");
			return writeAjaxResponse(response, JSONUtil.getJson(map));
		}

		if(request.getParameter("lvc")!=null && !"".equals(request.getParameter("lvc"))){
			lvc = request.getParameter("lvc");
		}else{
			map.put("success", "false");
			map.put("msg","请输入验证码");
			return writeAjaxResponse(response, JSONUtil.getJson(map));
		}
		DongjingClient djcn = ServerContext.createVmiClient();
		djcn.setMethod("checkLVCNoName");
		djcn.setRequestProperty("ftel", tel);
		djcn.setRequestProperty("lvc", lvc);
		djcn.SubmitData();
		net.sf.json.JSONObject jo = net.sf.json.JSONObject.fromObject(djcn.getResponse().getResultString());
		if("true".equals(jo.get("success"))){
			map.put("success", "true");
			map.put("msg",jo.get("msg"));
			request.getSession().setAttribute("lvc", lvc);
			request.getSession().setAttribute("ftel", tel);
			
		}else{
			map.put("success", "false");
			map.put("msg",jo.get("msg"));
		}
		return writeAjaxResponse(response, JSONUtil.getJson(map));
	}

	/**
	 * 找回密码时，修改密码;
	 * **/
	@RequestMapping("/app/user/changePWByLVC")
	public String changePWByLVC(HttpServletRequest request,HttpServletResponse response) throws IOException {
		HashMap<String,Object> map =new HashMap<String,Object>();
		try {
			String name = "",tel = "",lvc="",pw=""; 
//			if(request.getParameter("fname")!=null && !"".equals(request.getParameter("fname"))){
//				name = request.getParameter("fname");
//			}else{
//				map.put("success", "false");
//				map.put("msg","请输入用户名");
//				return writeAjaxResponse(response, JSONUtil.getJson(map));
//			}

			if(request.getParameter("ftel")!=null && !"".equals(request.getParameter("ftel"))){
				tel = request.getParameter("ftel");
			}else{
				map.put("success", "false");
				map.put("msg","请输入手机号");
				return writeAjaxResponse(response, JSONUtil.getJson(map));
			}

			if(request.getParameter("lvc")!=null && !"".equals(request.getParameter("lvc"))){
				lvc = request.getParameter("lvc");
			}else{
				map.put("success", "false");
				map.put("msg","请输入验证码");
				return writeAjaxResponse(response, JSONUtil.getJson(map));
			}

			if(request.getParameter("password")!=null && !"".equals(request.getParameter("password"))){
				pw =MD5Util.getMD5String( request.getParameter("password"));
			}else{
				map.put("success", "false");
				map.put("msg","请输入密码");
				return writeAjaxResponse(response, JSONUtil.getJson(map));
			}

			DongjingClient djcn = ServerContext.createVmiClient();
			djcn.setMethod("changePWByLVC");
			djcn.setRequestProperty("fname", name);
			djcn.setRequestProperty("ftel", tel);
			djcn.setRequestProperty("lvc", lvc);
			djcn.setRequestProperty("password", pw);
			djcn.SubmitData();
			net.sf.json.JSONObject jo = net.sf.json.JSONObject.fromObject(djcn.getResponse().getResultString());
			if("true".equals(jo.get("success"))){
				//修改密码的同时修改支付密码
				List<CL_UserRole> listU = userRoleDao.getByPhone(tel);
				for (CL_UserRole user : listU) {
//					user.setFpaypassword(pw);
					user.setVmiUserName(user.getFname());
					userRoleDao.update(user);
				}
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
			map.put("msg","校验码错误！");
			return writeAjaxResponse(response, JSONUtil.getJson(map));
		}
	}

	/*** 修改密码*/
	@RequestMapping("/app/user/setPassword")
	public String setPassword(HttpServletRequest request,HttpServletResponse response) throws IOException {
		HashMap<String,Object> map =new HashMap<String,Object>();
		Integer id;
		String oldpassword,newpassword;
		if(request.getParameter("id")!=null && !"".equals(request.getParameter("id"))){
			id = Integer.valueOf(request.getParameter("id"));
		}else{
			map.put("success", "false");
			map.put("msg","请先登录！");
			return writeAjaxResponse(response, JSONUtil.getJson(map));
		}

		if(request.getParameter("oldpassword")!=null && !"".equals(request.getParameter("oldpassword"))){
			oldpassword = request.getParameter("oldpassword");
		}else{
			map.put("success", "false");
			map.put("msg","旧密码不允许为空！");
			return writeAjaxResponse(response, JSONUtil.getJson(map));
		}
		if(request.getParameter("newpassword")!=null && !"".equals(request.getParameter("newpassword"))){
			newpassword = request.getParameter("newpassword");
		}else{
			map.put("success", "false");
			map.put("msg","新密码不允许为空！");
			return writeAjaxResponse(response, JSONUtil.getJson(map));
		}
		CL_UserRole userrole =this.userRoleDao.getById(id);
		userrole.setVmiUserName(userrole.getFname());//因sql中将vmiusername用了别名fname，而在update时取的是vmi，导致最终vmiusername为空，因涉及多处代码，姑且重新设置避免二次错误
		DongjingClient djcn = ServerContext.createVmiClient();
		djcn.setMethod("setPassword");
		djcn.setRequestProperty("fid", userrole.getVmiUserFid());
		djcn.setRequestProperty("newpassword", newpassword);
		djcn.setRequestProperty("oldpassword", oldpassword);
		djcn.SubmitData();
		net.sf.json.JSONObject jo = net.sf.json.JSONObject.fromObject(djcn.getResponse().getResultString());
		if("true".equals(jo.get("success"))){
//			userrole.setFpaypassword(MD5Util.getMD5String(newpassword));新需求，支付密码由独立的重置支付密码接口修改
			userRoleDao.update(userrole);
			map.put("success", "true");
			map.put("msg",jo.get("msg"));
		}else{
			map.put("success", "false");
			map.put("msg",jo.get("msg"));
		}
		return writeAjaxResponse(response, JSONUtil.getJson(map));
	}

	/*** 更改手机号*/
	@RequestMapping("/app/user/changePhone")
	public String changePhone(HttpServletRequest request,HttpServletResponse response) throws IOException {
		HashMap<String,Object> map =new HashMap<String,Object>();
		Integer id;
		String oldphone =null;
		String password =null;
		String newphone =null;
		String icode =null;
		if(request.getParameter("id")!=null && !"".equals(request.getParameter("id"))){
			id = Integer.valueOf(request.getParameter("id"));
		}else{
			map.put("success", "false");
			map.put("msg","请先登录！");
			return writeAjaxResponse(response, JSONUtil.getJson(map));
		}

		if(request.getParameter("oldphone")!=null && !"".equals(request.getParameter("oldphone"))){
			oldphone = request.getParameter("oldphone");
		}else{
			map.put("success", "false");
			map.put("msg","旧手机不允许为空！");
			return writeAjaxResponse(response, JSONUtil.getJson(map));
		}

		if(request.getParameter("password")!=null && !"".equals(request.getParameter("password"))){
			password = request.getParameter("password");
		}else{
			map.put("success", "false");
			map.put("msg","旧手机不允许为空！");
			return writeAjaxResponse(response, JSONUtil.getJson(map));
		}

		if(request.getParameter("newphone")!=null && !"".equals(request.getParameter("newphone"))){
			newphone = request.getParameter("newphone");
		}else{
			map.put("success", "false");
			map.put("msg","新手机不允许为空！");
			return writeAjaxResponse(response, JSONUtil.getJson(map));
		}

		if(request.getParameter("icode")!=null && !"".equals(request.getParameter("icode"))){
			icode = request.getParameter("icode");
		}else{
			map.put("success", "false");
			map.put("msg","验证码不允许为空！");
			return writeAjaxResponse(response, JSONUtil.getJson(map));
		}
		if(icode.length() != 6){
			map.put("success", "false");
			map.put("msg","验证码必须6位");
			return writeAjaxResponse(response, JSONUtil.getJson(map));
		}

		CL_UserRole userrole =this.userRoleDao.getById(id);
		DongjingClient djcn = ServerContext.createVmiClient();
		djcn.setMethod("changePhone");
		djcn.setRequestProperty("fid", userrole.getVmiUserFid());
		djcn.setRequestProperty("password", password);
		djcn.setRequestProperty("oldphone", oldphone);
		djcn.setRequestProperty("newphone", newphone);
		djcn.setRequestProperty("icode", icode);
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
	/**app申请另一种身份接口 往现有的cl_user_role 新增数据*/
	@RequestMapping("/app/user/changeidentity")
	public String changeidentity(HttpServletRequest request,HttpServletResponse response) throws IOException {
		String vmiuserId;
		Integer roleId;
		HashMap<String,Object> map =new HashMap<String,Object>();
		CL_UserRole us=new CL_UserRole();
		if(request.getParameter("vmiuserId")==null && "".equals(request.getParameter("vmiuserId"))){
			map.put("success", "false");
			map.put("msg","传入的用户ID有误!");
			return writeAjaxResponse(response, JSONUtil.getJson(map));
		}else{
			vmiuserId=request.getParameter("vmiuserId");
		}
		if(request.getParameter("roleId")==null && "".equals(request.getParameter("roleId"))){
			map.put("success", "false");
			map.put("msg","用户角色数据有误!");
			return writeAjaxResponse(response, JSONUtil.getJson(map));
		}else{
			roleId=Integer.parseInt(request.getParameter("roleId"));
		}
		CL_UserRole userRole=userRoleDao.getByVmiUserFidAndRoleId(vmiuserId, roleId);
		if(userRole==null){
			us.setRoleId(roleId);
			us.setVmiUserFid(vmiuserId);
			us.setCreateTime(new Date());
			userRoleDao.save(us);
			int newid=userRoleDao.getByVmiUserFidAndRoleId(vmiuserId, roleId).getId();
			map.put("success", "true");
			map.put("msg","操作成功!");
			map.put("data",  "[{\"id\":\""+newid+"\" }]");
			return writeAjaxResponse(response, JSONUtil.getJson(map));
		}
		else{
			map.put("success", "false");
			map.put("msg","该用户已经存在另一种身份!");
			return writeAjaxResponse(response, JSONUtil.getJson(map));
		}
	}

	
	/*** 更改银行信息*/
	@RequestMapping("/app/user/changeAccount")
	public String changeAccount(HttpServletRequest request,HttpServletResponse response) throws IOException {
		HashMap<String,Object> map =new HashMap<String,Object>();
		Integer urid;//用户ID
		String alipay ="";//支付宝帐号
		String bankaccount ="";//银行卡帐号
		String bankname ="";//开户行
		String bankaddress ="";//开户行所在地
		if(request.getParameter("urid")!=null && !"".equals(request.getParameter("urid"))){
			urid = Integer.valueOf(request.getParameter("urid"));
		}else{
			map.put("success", "false");
			map.put("msg","请先登录！");
			return writeAjaxResponse(response, JSONUtil.getJson(map));
		}
		CL_UserRole csclUser = userRoleDao.getById(urid);
		if(csclUser.getRoleId()!=2)
		{
			map.put("success", "false");
			map.put("msg", "只有车主可以修改银行卡信息");			
			return writeAjaxResponse(response, JSONUtil.getJson(map));
		}
		if(request.getParameter("alipay")!=null && !"".equals(request.getParameter("alipay"))){
			alipay = request.getParameter("alipay");
			csclUser.setAlipayId(alipay);
		}else{//为传入支付宝帐号
			if(request.getParameter("bankaccount")!=null && !"".equals(request.getParameter("bankaccount"))){
				bankaccount = request.getParameter("bankaccount");
			}else{
				map.put("success", "false");
				map.put("msg","银行账户为空！");
				return writeAjaxResponse(response, JSONUtil.getJson(map));
			}
			if(request.getParameter("bankname")!=null && !"".equals(request.getParameter("bankname"))){
				bankname = request.getParameter("bankname");
			}else{
				map.put("success", "false");
				map.put("msg","开户行为空！");
				return writeAjaxResponse(response, JSONUtil.getJson(map));
			}
			if(request.getParameter("bankaddress")!=null && !"".equals(request.getParameter("bankaddress"))){
				bankaddress = request.getParameter("bankaddress");
			}else{
				map.put("success", "false");
				map.put("msg","开户行所在地！");
				return writeAjaxResponse(response, JSONUtil.getJson(map));
			}
			csclUser.setBankAccount(bankaccount);
			csclUser.setBankName(bankname);
			csclUser.setBankAddress(bankaddress);
		}
		this.userRoleDao.updateAcount(csclUser);
		map.put("success", "true");
		map.put("msg","银行信息修改成功！");
		return writeAjaxResponse(response, JSONUtil.getJson(map));
	}
	
	//扫码
	@RequestMapping("/app/user/pcAndAppUuid")
	public String pcAndAppUuid(HttpServletRequest request,HttpServletResponse response){
		String AppUuid = request.getParameter("AppUuid");
		String AppUname = request.getParameter("AppUname");
		String AppPWD = request.getParameter("AppPWD");
		String AppFid = request.getParameter("AppFid");
		if(AppUuid == null||"".equals(AppUuid) ){
			return writeAjaxResponse(response, "false");
		}else {
			List<CL_Uuid> list = uuidDao.getByUUIDAndFid(AppUuid, Integer.parseInt(AppFid));
			if(list.size()>0){
				CL_Uuid entity = new CL_Uuid();
				entity.setFname(AppUname);
				entity.setFpassword(AppPWD);
				entity.setFid(Integer.parseInt(AppFid));
				entity.setUuid(AppUuid);
				uuidDao.update(entity);
				return writeAjaxResponse(response, "success");
			}else {
				return writeAjaxResponse(response, "false");
			}
		}
	}
	
	/**
	 * 找回支付密码第一步：检测登录密码
	 * @param request
	 * @param response
	 * @return
	 */
	/*@RequestMapping("/app/user/checkLoginPassword")
	public String checkLoginPassword(HttpServletRequest request,HttpServletResponse response){
		HashMap<String, Object> m = new HashMap<String, Object>();
		Integer id;String password,fname;
		if(request.getParameter("id")!=null && !"".equals(request.getParameter("id"))){
			id = Integer.valueOf(request.getParameter("id"));
		}else{
			m.put("success", "false");
			m.put("msg","请先登录！");
			return writeAjaxResponse(response, JSONUtil.getJson(m));
		}
		
		if(request.getParameter("password")!=null && !"".equals(request.getParameter("password"))){
			password = request.getParameter("password");
		}else{
			m.put("success", "false");
			m.put("msg","请先输入密码！");
			return writeAjaxResponse(response, JSONUtil.getJson(m));
		}
		CL_UserRole user = userRoleDao.getById(id);
		fname = user.getFname();
		DongjingClient djcn = ServerContext.createVmiClient();
		djcn.setMethod("csclLogon");
		djcn.setRequestProperty("fname", fname);
		djcn.setRequestProperty("fpassword", MD5Util.getMD5String(password));
		djcn.SubmitData();
		net.sf.json.JSONObject jo = net.sf.json.JSONObject.fromObject(djcn.getResponse().getResultString());
		if(jo == null){
			m.put("success", "false");
			m.put("msg","服务器开小差了啦，请联系客服检举她！");
		} else if ("true".equals(jo.get("success"))) {
			m.put("success", "true");
			m.put("msg","校验成功！");
			m.put("data", user);
		} else {
			m.put("success", "false");
			m.put("msg","密码不正确！");
		}
		return writeAjaxResponse(response, JSONUtil.getJson(m));
	}*/
	
	/**
	 * 找回支付密码时，获取验证码;
	 * **/
	@RequestMapping("/app/user/getFindPayPWDCodeTel")
	public String getFindPayPWDCodeTel(HttpServletRequest request,HttpServletResponse response) throws Exception{
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
			djcn.setMethod("getFndValidateVCodeTel");//cps获取验证码
			djcn.setRequestProperty("ftel", tel);
			djcn.SubmitData();
			net.sf.json.JSONObject jo = net.sf.json.JSONObject.fromObject(djcn.getResponse().getResultString());
			if(jo == null){
				map.put("success", "false");
				map.put("msg","服务器开小差了啦，请联系客服检举她！");
			} else if ("true".equals(jo.get("success"))){
				map.put("success", "true");
				map.put("msg",jo.get("msg"));
			} else {
				map.put("success", "false");
				map.put("msg",jo.get("msg"));
			}
			return writeAjaxResponse(response, JSONUtil.getJson(map));
		} catch (Exception e) {
			map.put("success", "false");
			map.put("msg","验证码发送失败！");
			return writeAjaxResponse(response, JSONUtil.getJson(map));
		}
	}

	/**
	 * 修改支付密码时，验证码校验
	 */
	@RequestMapping("/app/user/payCheckLVC")
	public String payCheckLVC(HttpServletRequest request,HttpServletResponse response) throws Exception{
		HashMap<String,Object> map =new HashMap<String,Object>();
		String tel = "",lvc=""; 
		if(request.getParameter("ftel")!=null && !"".equals(request.getParameter("ftel"))){
			tel = request.getParameter("ftel");
		}else{
			map.put("success", "false");
			map.put("msg","请输入手机号");
			return writeAjaxResponse(response, JSONUtil.getJson(map));
		}

		if(request.getParameter("lvc")!=null && !"".equals(request.getParameter("lvc"))){
			lvc = request.getParameter("lvc");
		}else{
			map.put("success", "false");
			map.put("msg","请输入验证码");
			return writeAjaxResponse(response, JSONUtil.getJson(map));
		}
		DongjingClient djcn = ServerContext.createVmiClient();
		djcn.setMethod("checkLVCNoName");//cps验证码校验
		djcn.setRequestProperty("ftel", tel);
		djcn.setRequestProperty("lvc", lvc);
		djcn.SubmitData();
		net.sf.json.JSONObject jo = net.sf.json.JSONObject.fromObject(djcn.getResponse().getResultString());
		if(jo == null){
			map.put("success", "false");
			map.put("msg","服务器开小差了啦，请联系客服检举她！");
		} else if ("true".equals(jo.get("success"))){
			map.put("success", "true");
			map.put("msg",jo.get("msg"));
		} else {
			map.put("success", "false");
			map.put("msg",jo.get("msg"));
		}
		return writeAjaxResponse(response, JSONUtil.getJson(map));
	}

	/**
	 * 修改密码;
	 * **/
	@RequestMapping("/app/user/changePayPW")
	public String changePayPW(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		HashMap<String, Object> map = new HashMap<String, Object>();
		Integer id;
		String payPassword;
		if (request.getParameter("id") != null
				&& !"".equals(request.getParameter("id"))) {
			id = Integer.parseInt(request.getParameter("id"));
		} else {
			map.put("success", "false");
			map.put("msg", "请登录");
			return writeAjaxResponse(response, JSONUtil.getJson(map));
		}

		if (request.getParameter("payPassword") != null
				&& !"".equals(request.getParameter("payPassword"))) {
			payPassword = request.getParameter("payPassword");
		} else {
			map.put("success", "false");
			map.put("msg", "请输入密码");
			return writeAjaxResponse(response, JSONUtil.getJson(map));
		}

		CL_UserRole user = userRoleDao.getById(id);
		user.setFpaypassword(MD5Util.getMD5String(payPassword));
		user.setVmiUserName(user.getFname());
		userRoleDao.update(user);
		map.put("success", "true");
		map.put("msg", "设置成功，别再忘了喔！");
		return writeAjaxResponse(response, JSONUtil.getJson(map));
	}
	
	/**
	 * 支付密码修改接口
	 * @param request
	 * @param response
	 * @return
	 */
	/*@RequestMapping("/app/user/setPayPassword")
	public String setPayPassword(HttpServletRequest request,HttpServletResponse response){
		HashMap<String, Object> map = new HashMap<String, Object>();
		Integer id;
		String oldPayPassword,newPayPassword;
		if(request.getParameter("id")!=null && !"".equals(request.getParameter("id"))){
			id = Integer.valueOf(request.getParameter("id"));
		}else{
			map.put("success", "false");
			map.put("msg","请先登录！");
			return writeAjaxResponse(response, JSONUtil.getJson(map));
		}

		
		if(request.getParameter("oldpassword")!=null && !"".equals(request.getParameter("oldpassword"))){
			oldPayPassword = request.getParameter("oldpassword");
		}else{
			map.put("success", "false");
			map.put("msg","旧密码不允许为空！");
			return writeAjaxResponse(response, JSONUtil.getJson(map));
		}
		if(request.getParameter("newpassword")!=null && !"".equals(request.getParameter("newpassword"))){
			newPayPassword = request.getParameter("newpassword");
		}else{
			map.put("success", "false");
			map.put("msg","新密码不允许为空！");
			return writeAjaxResponse(response, JSONUtil.getJson(map));
		}
		CL_UserRole userrole =this.userRoleDao.getById(id);
		String payPassword = userrole.getFpaypassword();
		if(MD5Util.getMD5String(oldPayPassword).equals(payPassword)){
			userrole.setVmiUserName(userrole.getFname());//因sql中将vmiusername用了别名fname，而在update时取的是vmi，导致最终vmiusername为空，因涉及多处代码，姑且重新设置避免二次错误
			userrole.setFpaypassword(MD5Util.getMD5String(newPayPassword));
			userRoleDao.update(userrole);
			map.put("success", "true");
			map.put("String", "修改成功！");
		} else {
			map.put("success", "false");
			map.put("String", "旧密码不正确！");
		}
		return writeAjaxResponse(response, JSONUtil.getJson(map));
	}*/
	
}
