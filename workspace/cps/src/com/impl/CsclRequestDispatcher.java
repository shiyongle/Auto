package com.impl;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import com.action.BaseAction;
import com.model.user.TSysUser;
import com.model.invite.Invite;
import com.service.invite.InviteManager;
import com.service.user.TSysUserManager;
import com.util.JSONUtil;
import com.util.LVCSMSFacade;
import com.util.MD5Util;

public class CsclRequestDispatcher extends BaseAction {

	private static final long serialVersionUID = -2245197294856858937L;

	@Autowired
	private TSysUserManager userManager;
	@Autowired
	private InviteManager inviteManager;

	/**
	 * 注册时，获取验证码;
	 * **/
	public String getRegValidateVCodeTel(){
		HashMap<String,Object> map =new HashMap<String,Object>();
		try {

			String tel = ""; 
			if(getRequest().getParameter("ftel")!=null && !"".equals(getRequest().getParameter("ftel"))){
				tel = getRequest().getParameter("ftel");
			}else{
				map.put("success", "false");
				map.put("msg","请输入手机号");
				return writeAjaxResponse(JSONUtil.getJson(map));
			}

//			if(this.userManager.isExistsForTel(tel)){
//				map.put("success", "false");
//				map.put("msg","手机号已经注册！");
//				return writeAjaxResponse(JSONUtil.getJson(map));
//			}else{
				LVCSMSFacade lVCSMSFacade = LVCSMSFacade.getLVCSMSFacade(userManager);
				System.out.println("*****************************************************************开始向手机发送验证码****开始************************************");
				lVCSMSFacade.sendLVCToGoalPhone(tel, tel);
				System.out.println("*****************************************************************开始向手机发送验证码****结束************************************");
				map.put("success", "true");
				map.put("msg","验证码发送成功！");
				return writeAjaxResponse(JSONUtil.getJson(map));
//			}

		} catch (IOException e) {
			map.put("success", "false");
			map.put("msg","验证码发送失败！");
			return writeAjaxResponse(JSONUtil.getJson(map));
		}
	}

	/*** 判断注册用户名是否已存在*/
	public String nameIsUnique(){
		boolean isUnique =this.userManager.isUnique(getRequest().getParameter("fname"));
		HashMap<String,Object> map =new HashMap<String,Object>();
		if(isUnique==false){
			map.put("success", "true");//存在
			return writeAjaxResponse(JSONUtil.getJson(map));//存在
		}else{
			map.put("success", "false");//不存在
			return writeAjaxResponse(JSONUtil.getJson(map));//不存在
		}
	}

	/**用户注册验证码校验**/
	public String regValiCode() throws IOException{
		HashMap<String,Object> map =new HashMap<String,Object>();
		String name = getRequest().getParameter("ftel");
		String identCode = getRequest().getParameter("identCode");
		LVCSMSFacade lVCSMSFacade = LVCSMSFacade.getLVCSMSFacade(userManager);
		if(lVCSMSFacade.isTheRightLVC(name, identCode,true)){
			map.put("success", "true");
			map.put("msg","验证码校验成功！");
			return writeAjaxResponse(JSONUtil.getJson(map));
		}else {
			map.put("success", "false");
			map.put("msg","验证码校验失败！");
			return writeAjaxResponse(JSONUtil.getJson(map));
		}
	}

	/**
	 * 找回密码时，获取验证码;
	 * **/
	public String getFndValidateVCodeTel(){
		HashMap<String,Object> map =new HashMap<String,Object>();
		try {
			String tel = "",name = ""; 
			if(getRequest().getParameter("ftel")!=null && !"".equals(getRequest().getParameter("ftel"))){
				tel = getRequest().getParameter("ftel");
			}else{
				map.put("success", "false");
				map.put("msg","请输入手机号");
				return writeAjaxResponse(JSONUtil.getJson(map));
			}

//			if(getRequest().getParameter("fname")!=null && !"".equals(getRequest().getParameter("fname"))){
//				name = getRequest().getParameter("fname");
//			}else{
//				map.put("success", "false");
//				map.put("msg","请输入用户名");
//				return writeAjaxResponse(JSONUtil.getJson(map));
//			}
			TSysUser user = this.userManager.getUser(name, getRequest().getParameter("ftel"));
			if(user !=null){
				name = user.getFname();
				LVCSMSFacade lVCSMSFacade = LVCSMSFacade.getLVCSMSFacade(userManager);
				System.out.println("*****************************************************************开始向手机发送验证码****开始************************************");
				lVCSMSFacade.sendLVCToGoalPhone(name, tel);
				System.out.println("*****************************************************************开始向手机发送验证码****结束************************************");
				map.put("success", "true");
				map.put("msg","验证码发送成功！");
				return writeAjaxResponse(JSONUtil.getJson(map));
			}else{
				map.put("success", "false");
				map.put("msg","请输入注册时的手机号");
				return writeAjaxResponse(JSONUtil.getJson(map));
			}

		} catch (Exception e) {
			map.put("success", "false");
			map.put("msg","验证码发送失败！");
			return writeAjaxResponse(JSONUtil.getJson(map));
		}
	}

	/***找回密码时，验证码校验*/
	public String checkLVC(){
		HashMap<String,Object> map =new HashMap<String,Object>();
		String name = "",tel = "",lvc=""; 
		if(getRequest().getParameter("fname")!=null && !"".equals(getRequest().getParameter("fname"))){
			name = getRequest().getParameter("fname");
		}else{
			map.put("success", "false");
			map.put("msg","请输入用户名");
			return writeAjaxResponse(JSONUtil.getJson(map));
		}

		if(getRequest().getParameter("ftel")!=null && !"".equals(getRequest().getParameter("ftel"))){
			tel = getRequest().getParameter("ftel");
		}else{
			map.put("success", "false");
			map.put("msg","请输入手机号");
			return writeAjaxResponse(JSONUtil.getJson(map));
		}

		if(getRequest().getParameter("lvc")!=null && !"".equals(getRequest().getParameter("lvc"))){
			lvc = getRequest().getParameter("lvc");
		}else{
			map.put("success", "false");
			map.put("msg","请输入验证码");
			return writeAjaxResponse(JSONUtil.getJson(map));
		}
		TSysUser user = this.userManager.getUser(name, tel);

		if(user !=null){
			//验证码校验;
			LVCSMSFacade lVCSMSFacade = LVCSMSFacade.getLVCSMSFacade(userManager);
			if(lVCSMSFacade.isTheRightLVC(name, lvc,false)){
				map.put("success", "true");//验证码校验通过;
				map.put("msg", "验证码校验通过");
			}else{
				map.put("success", "false");////验证码校验未通过;
				map.put("msg", "验证码校验未通过");
			}
		}else{
			map.put("success", "false");////验证码校验未通过;
			map.put("msg", "该手机号未注册！");
		}
		return writeAjaxResponse(JSONUtil.getJson(map));
	}

	
	
	/***找回密码时，验证码校验 无需用户名校验*/
	public String checkLVCNoName(){
		HashMap<String,Object> map =new HashMap<String,Object>();
		String name = "",tel = "",lvc=""; 
		if(getRequest().getParameter("ftel")!=null && !"".equals(getRequest().getParameter("ftel"))){
			tel = getRequest().getParameter("ftel");
		}else{
			map.put("success", "false");
			map.put("msg","请输入手机号");
			return writeAjaxResponse(JSONUtil.getJson(map));
		}
		
		if(getRequest().getParameter("lvc")!=null && !"".equals(getRequest().getParameter("lvc"))){
			lvc = getRequest().getParameter("lvc");
		}else{
			map.put("success", "false");
			map.put("msg","请输入验证码");
			return writeAjaxResponse(JSONUtil.getJson(map));
		}
		try{
			TSysUser user = this.userManager.getUser(name, tel);
			if(user !=null){
				//验证码校验;
				name = user.getFname();
				LVCSMSFacade lVCSMSFacade = LVCSMSFacade.getLVCSMSFacade(userManager);
				if(lVCSMSFacade.isTheRightLVC(name, lvc,false)){
					map.put("success", "true");//验证码校验通过;
					map.put("msg", "验证码校验通过");
				}else{
					map.put("success", "false");////验证码校验未通过;
					map.put("msg", "验证码校验未通过");
				}
			}else{
				map.put("success", "false");////验证码校验未通过;
				map.put("msg", "该手机号未注册！");
			}
		}catch(Exception e){
			map.put("success", "false");////验证码校验未通过;
			map.put("msg", e.getMessage());
		}
		
		return writeAjaxResponse(JSONUtil.getJson(map));
	}
	
	
	/**
	 * 找回密码时，修改密码;
	 * **/
	public String changePWByLVC() throws IOException {
		LVCSMSFacade lVCSMSFacade = LVCSMSFacade.getLVCSMSFacade(userManager);
		HashMap<String,Object> map =new HashMap<String,Object>();
		try {
			String name = "",tel = "",lvc="",pw=""; 
//			if(getRequest().getParameter("fname")!=null && !"".equals(getRequest().getParameter("fname"))){
//				name = getRequest().getParameter("fname");
//			}else{
//				map.put("success", "false");
//				map.put("msg","请输入用户名");
//				return writeAjaxResponse(JSONUtil.getJson(map));
//			}

			if(getRequest().getParameter("ftel")!=null && !"".equals(getRequest().getParameter("ftel"))){
				tel = getRequest().getParameter("ftel");
			}else{
				map.put("success", "false");
				map.put("msg","请输入手机号");
				return writeAjaxResponse(JSONUtil.getJson(map));
			}

			if(getRequest().getParameter("lvc")!=null && !"".equals(getRequest().getParameter("lvc"))){
				lvc = getRequest().getParameter("lvc");
			}else{
				map.put("success", "false");
				map.put("msg","请输入验证码");
				return writeAjaxResponse(JSONUtil.getJson(map));
			}

			if(getRequest().getParameter("password")!=null && !"".equals(getRequest().getParameter("password"))){
				pw = getRequest().getParameter("password");
			}else{
				map.put("success", "false");
				map.put("msg","请输入密码");
				return writeAjaxResponse(JSONUtil.getJson(map));
			}

			TSysUser user = this.userManager.getUser(name, tel);
			if(user !=null){
				name = user.getFname();
			}else{
				map.put("success", "false");
				map.put("msg","请输入注册时的手机号");
				return writeAjaxResponse(JSONUtil.getJson(map));
			}

			if(lVCSMSFacade.changeThePassWord(lvc, name, pw)){
				map.put("success", "true");
				map.put("msg","修改成功，请使用新密码登录！");
				return writeAjaxResponse(JSONUtil.getJson(map));
			}else {
				map.put("success", "false");
				map.put("msg","校验码错误！");
				return writeAjaxResponse(JSONUtil.getJson(map));
			}

		} catch (Exception e) {
			map.put("success", "false");
			map.put("msg","校验码错误！");
			return writeAjaxResponse(JSONUtil.getJson(map));
		}
	}

	/**
	 * 同城物流注册;
	 * 需要传参“用户：fname,密码：fpassword,手机：ftel,同城物流项目用户类型：ftype”
	 * **/
	public String reg(){
		HashMap<String,Object> map =new HashMap<String,Object>();
		String fpassword="",fname="",ftel="",ftype="";
		try {
			    if(getRequest().getParameter("fpassword")==null || "".equals(getRequest().getParameter("fpassword"))){
			    	map.put("success", "false");
					map.put("msg","用户密码不能为空！");
					return writeAjaxResponse(JSONUtil.getJson(map));
			    }else{
			    	fpassword = getRequest().getParameter("fpassword");
			    }
			    
			    if(getRequest().getParameter("fname")==null || "".equals(getRequest().getParameter("fname"))){
			    	map.put("success", "false");
					map.put("msg","用户名不能为空！");
					return writeAjaxResponse(JSONUtil.getJson(map));
			    }else{
			    	fname = getRequest().getParameter("fname");
			    }
			    
			    if(getRequest().getParameter("ftel")==null || "".equals(getRequest().getParameter("ftel"))){
			    	map.put("success", "false");
					map.put("msg","手机号不能为空！");
					return writeAjaxResponse(JSONUtil.getJson(map));
			    }else{
			    	ftel = getRequest().getParameter("ftel");
			    }
			    
			    if(getRequest().getParameter("ftype")==null || "".equals(getRequest().getParameter("ftype"))){
			    	map.put("success", "false");
					map.put("msg","类型不能为空！");
					return writeAjaxResponse(JSONUtil.getJson(map));
			    }else{
			    	ftype = getRequest().getParameter("ftype");
			    }
			    
			    //判断该手机账号已被注册；
			    if(this.userManager.isExistsForTel(ftel)){
					map.put("success", "false");//存在
					map.put("msg","该手机账号已被注册！");
					return writeAjaxResponse(JSONUtil.getJson(map));
				}
			    
			    //判断该用户名已被注册；
				if(this.userManager.isUnique(getRequest().getParameter("fname"))==false){
					map.put("success", "false");//存在
					map.put("msg","该用户名已被注册！");
					return writeAjaxResponse(JSONUtil.getJson(map));
				}

			  TSysUser user=null;
		      if (this.userManager.isExistsForTel(ftel)) {
		        user = this.userManager.getUser(null, ftel);
		        user.setFpassword(MD5Util.getMD5String(getRequest().getParameter("fpassword")));
		        this.userManager.update(user);
		        map.put("success", "true");
		        map.put("msg", "注册成功！");
		        map.put("userlist", user);
		        return writeAjaxResponse(JSONUtil.getJson(map));
		      }
				
		      	user=new TSysUser();
				user.setFid(this.userManager.CreateUUid());
				user.setFpassword(MD5Util.getMD5String(getRequest().getParameter("fpassword")));
				user.setFcreatetime(new Date());
				user.setFname(getRequest().getParameter("fname"));
				user.setFcustomername(getRequest().getParameter("fname"));
				user.setFtel(getRequest().getParameter("ftel"));
				
			 	user.setFtype(Integer.parseInt(ftype));
			 	user.setFcreatetime(new Date());
			 	user.setFcustomername("");
			 	user.setFemail("");
			 	user.setFeffect(0);
			 	user.setFimid(null);
			 	
				this.userManager.saveImpl(user);
				
				map.put("userlist", user);
				map.put("success", "true");
				map.put("msg","注册成功！");
				return writeAjaxResponse(JSONUtil.getJson(map));
				
		} catch (Exception e) {
			// TODO Auto-generated catch block
			map.put("success", "false");//存在
			map.put("msg",e.getMessage());
			return writeAjaxResponse(JSONUtil.getJson(map));
		}
	}

	/**
	 * 同城物流登录;
	 * 需要传参“用户：fname,密码：fpassword,同城物流项目用户类型：ftype”
	 * **/
	public String csclLogon(){
		System.out.println("----------------------------------");
		HashMap<String,Object> map =new HashMap<String,Object>();
		try {
			if(getRequest().getParameter("fpassword")==null || "".equals(getRequest().getParameter("fpassword")) ){
				map.put("success", "false");
				map.put("msg","用户密码不能为空！");
				return writeAjaxResponse(JSONUtil.getJson(map));
			}
			if(getRequest().getParameter("fname")==null || "".equals(getRequest().getParameter("fname"))){
				map.put("success", "false");
				map.put("msg","用户名不能为空！");
				return writeAjaxResponse(JSONUtil.getJson(map));
			}

			// 校验登录用户是否存在
			TSysUser loginUser = userManager.login(getRequest().getParameter("fname"),getRequest().getParameter("fname"), getRequest().getParameter("fpassword"));
			if(loginUser !=null){
				map.put("userlist", loginUser);
				map.put("success", "true");
				map.put("msg","登录成功！");
				return writeAjaxResponse(JSONUtil.getJson(map));
			}else{
				map.put("success", "false");
				map.put("msg","账号密码不正确！");
				return writeAjaxResponse(JSONUtil.getJson(map));
			}
		} catch (Exception e) {
			map.put("success", "false");
			map.put("msg","账号密码不正确！");
			return writeAjaxResponse(JSONUtil.getJson(map));
		}
	}


	/**
	 * 同城物流登录;
	 * 需要传参“用户：fname,密码：fpassword,同城物流项目用户类型：ftype”
	 * **/
	public String csclwebLogon(){
		System.out.println("----------------------------------");
		HashMap<String,Object> map =new HashMap<String,Object>();
		try {
			if(getRequest().getParameter("fpassword")==null || "".equals(getRequest().getParameter("fpassword")) ){
				map.put("success", "false");
				map.put("msg","用户密码不能为空！");
				return writeAjaxResponse(JSONUtil.getJson(map));
			}
			if(getRequest().getParameter("fname")==null || "".equals(getRequest().getParameter("fname"))){
				map.put("success", "false");
				map.put("msg","用户名不能为空！");
				return writeAjaxResponse(JSONUtil.getJson(map));
			}

			// 校验登录用户是否存在
			TSysUser loginUser = userManager.weblogin(getRequest().getParameter("fname"),getRequest().getParameter("fname"), getRequest().getParameter("fpassword"),2);
			if(loginUser !=null){
				map.put("userlist", loginUser);
				map.put("success", "true");
				map.put("msg","登录成功！");
				return writeAjaxResponse(JSONUtil.getJson(map));
			}else{
				map.put("success", "false");
				map.put("msg","账号密码不正确！");
				return writeAjaxResponse(JSONUtil.getJson(map));
			}
		} catch (Exception e) {
			map.put("success", "false");
			map.put("msg","账号密码不正确！");
			return writeAjaxResponse(JSONUtil.getJson(map));
		}
	}

	/**
	 * 同城物流用户查询;
	 * 需要传参“查询条件表达式：where fid in ('')”
	 * **/
	public String csclGetUserlist(){
		HashMap<String,Object> map =new HashMap<String,Object>();
		try {
			String fids = "";
			if(getRequest().getParameter("fids")==null || "".equals(getRequest().getParameter("fids"))){
				map.put("success", "false");
				map.put("msg","用户id不能为空！");
				return writeAjaxResponse(JSONUtil.getJson(map));
			}else{
				fids =getRequest().getParameter("fids");
				System.out.println(fids);
			}
			String hql = "from TSysUser " + fids;
			List<TSysUser> userlist = userManager.findByHql(hql);
			if(userlist != null && userlist.size() > 0){
				map.put("success", "true");
				map.put("userlist", userlist);
				return writeAjaxResponse(JSONUtil.getJson(map));
			}else{
				map.put("success", "false");
				map.put("msg","查询失败！");
				return writeAjaxResponse(JSONUtil.getJson(map));
			}
		} catch (Exception e) {
			map.put("success", "false");
			map.put("msg","查询失败！");
			return writeAjaxResponse(JSONUtil.getJson(map));
		}
	}



	/*** 重新设置密码**/
	public String setPassword() throws IOException {
		HashMap<String,Object> map =new HashMap<String,Object>();
		String fid , oldpassword ,newpassword = null;
		if(getRequest().getParameter("fid")!=null && !"".equals(getRequest().getParameter("fid"))){
			fid = getRequest().getParameter("fid");
		}else{
			map.put("success", "false");
			map.put("msg","请联系数据库管理员");
			return writeAjaxResponse(JSONUtil.getJson(map));
		}
		if(getRequest().getParameter("oldpassword")!=null && !"".equals(getRequest().getParameter("oldpassword"))){
			oldpassword = getRequest().getParameter("oldpassword");
		}else{
			map.put("success", "false");
			map.put("msg","旧密码不允许为空！");
			return writeAjaxResponse(JSONUtil.getJson(map));
		}
		if(getRequest().getParameter("newpassword")!=null && !"".equals(getRequest().getParameter("newpassword"))){
			newpassword = getRequest().getParameter("newpassword");
		}else{
			map.put("success", "false");
			map.put("msg","新密码不允许为空");
			return writeAjaxResponse(JSONUtil.getJson(map));
		}
		//***********业务操作代码*******//
		TSysUser user = this.userManager.get(fid);
		if(user ==null){
			map.put("success", "false");
			map.put("msg","跨库用户不存在！");
			return writeAjaxResponse(JSONUtil.getJson(map));
		}else{
			String oldpassword2 =MD5Util.getMD5String(oldpassword);
			String newpassword2 =MD5Util.getMD5String(newpassword);
			if(oldpassword2.equals(user.getFpassword())){
				user.setFpassword(newpassword2);
				this.userManager.update(user);
				map.put("success", "true");
				map.put("msg","操作成功");
				return writeAjaxResponse(JSONUtil.getJson(map));
			}else{
				map.put("success", "false");
				map.put("msg","旧密码错误！");
				return writeAjaxResponse(JSONUtil.getJson(map));
			}
		}
	}



	/*** 更改手机号**/
	public String changePhone() throws IOException {
		HashMap<String,Object> map =new HashMap<String,Object>();
		String fid , oldphone ,newphone ,password ,icode= null;
		if(getRequest().getParameter("fid")!=null && !"".equals(getRequest().getParameter("fid"))){
			fid = getRequest().getParameter("fid");
		}else{
			map.put("success", "false");
			map.put("msg","请联系数据库管理员");
			return writeAjaxResponse(JSONUtil.getJson(map));
		}
		if(getRequest().getParameter("oldphone")!=null && !"".equals(getRequest().getParameter("oldphone"))){
			oldphone = getRequest().getParameter("oldphone");
		}else{
			map.put("success", "false");
			map.put("msg","旧手机号不允许为空！");
			return writeAjaxResponse(JSONUtil.getJson(map));
		}
		if(getRequest().getParameter("newphone")!=null && !"".equals(getRequest().getParameter("newphone"))){
			newphone = getRequest().getParameter("newphone");
		}else{
			map.put("success", "false");
			map.put("msg","新手机号不允许为空");
			return writeAjaxResponse(JSONUtil.getJson(map));
		}

		if(getRequest().getParameter("password")!=null && !"".equals(getRequest().getParameter("password"))){
			password = getRequest().getParameter("password");
		}else{
			map.put("success", "false");
			map.put("msg","密码不允许为空");
			return writeAjaxResponse(JSONUtil.getJson(map));
		}

		if(getRequest().getParameter("icode")!=null && !"".equals(getRequest().getParameter("icode"))){
			icode = getRequest().getParameter("icode");
		}else{
			map.put("success", "false");
			map.put("msg","验证码不允许为空");
			return writeAjaxResponse(JSONUtil.getJson(map));
		}
		//***********业务操作代码*******//
		if(this.userManager.isExistsForTel(newphone)==false){
			LVCSMSFacade lVCSMSFacade = LVCSMSFacade.getLVCSMSFacade(userManager);
			if(lVCSMSFacade.isTheRightLVC(newphone, icode,true)){
				TSysUser user = this.userManager.get(fid);
				if(user ==null){
					map.put("success", "false");
					map.put("msg","跨库用户不存在！");
					return writeAjaxResponse(JSONUtil.getJson(map));
				}else{
					if(!user.getFtel().equals(oldphone)){
						map.put("success", "false");
						map.put("msg","旧手机号错误！");
						return writeAjaxResponse(JSONUtil.getJson(map));
					}else{
						user.setFtel(newphone);
						this.userManager.update(user);
						map.put("success", "true");
						map.put("msg","操作成功！");
						return writeAjaxResponse(JSONUtil.getJson(map));
					}
				}
			}else {
				map.put("success", "false");
				map.put("msg","验证码校验失败！");
				return writeAjaxResponse(JSONUtil.getJson(map));
			}

		}else{
			map.put("success", "false");
			map.put("msg","新手机已存在！");
			return writeAjaxResponse(JSONUtil.getJson(map));
		}


	}


	/**
	 * 同城物流用户查询;
	 * 需要传参“查询条件表达式：where fid in ('')”
	 * **/
	public String getUserByFname(){
		HashMap<String,Object> map =new HashMap<String,Object>();
		try {
			String fname =null;
			if(getRequest().getParameter("name")==null || "".equals(getRequest().getParameter("name"))){
				map.put("success", "false");
				map.put("msg","用户id不能为空！");
				return writeAjaxResponse(JSONUtil.getJson(map));
			}else{
				fname =getRequest().getParameter("name");
				System.out.println(fname);
			}
			List<TSysUser> userlist = userManager.getByName(fname);
			if(userlist != null && userlist.size() > 0){
				map.put("success", "true");
				map.put("userlist", userlist);
				return writeAjaxResponse(JSONUtil.getJson(map));
			}else{
				map.put("success", "false");
				map.put("msg","查询失败！");
				return writeAjaxResponse(JSONUtil.getJson(map));
			}
		} catch (Exception e) {
			map.put("success", "false");
			map.put("msg","查询失败！");
			return writeAjaxResponse(JSONUtil.getJson(map));
		}
	}


	/**
	 * 注册时，获取验证码;
	 * **/
	public String getDeatilCode(){
		HashMap<String,Object> map =new HashMap<String,Object>();
		try {
			String tel = "",fname="",fcode="",msgString="",fadd=""; 
			if(getRequest().getParameter("ftel")!=null && !"".equals(getRequest().getParameter("ftel"))){
				tel = getRequest().getParameter("ftel");
			}else{
				map.put("success", "false");
				map.put("msg","请输入手机号");
				return writeAjaxResponse(JSONUtil.getJson(map));
			}

			if(getRequest().getParameter("fname")!=null && !"".equals(getRequest().getParameter("fname"))){
				fname = getRequest().getParameter("fname");
			}else{
				map.put("success", "false");
				map.put("msg","请输入用户名");
				return writeAjaxResponse(JSONUtil.getJson(map));
			}
			if(getRequest().getParameter("fcode")!=null && !"".equals(getRequest().getParameter("fcode"))){
				fcode = getRequest().getParameter("fcode");
			}else{
				map.put("success", "false");
				map.put("msg","请输入随机码");
				return writeAjaxResponse(JSONUtil.getJson(map));
			}if(getRequest().getParameter("msgString")!=null && !"".equals(getRequest().getParameter("msgString"))){
				msgString = getRequest().getParameter("msgString");
			}else{
				map.put("success", "false");
				map.put("msg","请输入需要发送的信息");
				return writeAjaxResponse(JSONUtil.getJson(map));
			}
			LVCSMSFacade lVCSMSFacade = LVCSMSFacade.getLVCSMSFacade(userManager);
			System.out.println("*****************************************************************开始向手机发送验证码****开始************************************");
			lVCSMSFacade.sendSecurityCode(fname, tel,fcode,msgString);
			System.out.println("*****************************************************************开始向手机发送验证码****结束************************************");
			map.put("success", "true");
			map.put("msg","验证码发送成功！");
			return writeAjaxResponse(JSONUtil.getJson(map));

		} catch (IOException e) {
			map.put("success", "false");
			map.put("msg","验证码发送失败！");
			return writeAjaxResponse(JSONUtil.getJson(map));
		}
	}


	/**
	 * 同城物流转介绍注册;
	 * Param参数：“密码：fpassword,重复密码：frepassword,手机：ftel,手机验证码：fcode”
	 * **/
	public String inviteReg(){
		HashMap<String,Object> map =new HashMap<String,Object>();
		String fpassword="",ftel="",fuserid="",fuserroleid="";
		String fusername="";
		try {
			if(getRequest().getParameter("fpassword")==null || "".equals(getRequest().getParameter("fpassword"))){
				map.put("success", "false");
				map.put("msg","pwd用户密码不能为空！");
				return writeAjaxResponse(JSONUtil.getJson(map));
			}else{
				fpassword = getRequest().getParameter("fpassword");
			}
			if(getRequest().getParameter("fuserroleid")==null || "".equals(getRequest().getParameter("fuserroleid"))){
				map.put("success", "false");
				map.put("msg","用户角色ID不能为空！");
				return writeAjaxResponse(JSONUtil.getJson(map));
			}else{
				fuserroleid =getRequest().getParameter("fuserroleid");
			}

			if(getRequest().getParameter("frepassword")==null || "".equals(getRequest().getParameter("frepassword"))||!getRequest().getParameter("frepassword").equals(getRequest().getParameter("fpassword"))){
				map.put("success", "false");
				map.put("msg","repwd两次密码输入不一致！");
				return writeAjaxResponse(JSONUtil.getJson(map));
			}

			if(getRequest().getParameter("ftel")==null || "".equals(getRequest().getParameter("ftel"))){
				map.put("success", "false");
				map.put("msg","tel手机号不能为空！");
				return writeAjaxResponse(JSONUtil.getJson(map));
			}else{
				ftel = getRequest().getParameter("ftel");
			}
//			if(getRequest().getParameter("fcode")==null || "".equals(getRequest().getParameter("fcode"))){
//				map.put("success", "false");
//				map.put("msg","code手机验证码不能为空！");
//				return writeAjaxResponse(JSONUtil.getJson(map));
//			}else{
//				fcode = getRequest().getParameter("fcode");
//			}
			if(getRequest().getParameter("fuserid")==null || "".equals(getRequest().getParameter("fuserid"))){
				map.put("success", "false");
				map.put("msg","找不到当前用户！");
				return writeAjaxResponse(JSONUtil.getJson(map));
			}else{
				fuserid = getRequest().getParameter("fuserid");//参数传过来的邀请人ID
				TSysUser user=userManager.getByUserId(fuserid);
				if(user!=null)
				{
					fusername=user.getFname();
				}
			}
			//判断该手机账号已被注册；
			if(this.userManager.isExistsForTel(ftel)){
				map.put("success", "false");//存在
				map.put("msg","registered该手机账号已被注册！");
				return writeAjaxResponse(JSONUtil.getJson(map));
			}
			//验证手机校验码
			LVCSMSFacade lVCSMSFacade = LVCSMSFacade.getLVCSMSFacade(userManager);
			//			if(lVCSMSFacade.isTheRightLVC(ftel, fcode,true)){
			//添加用户表数据
			TSysUser user=new TSysUser();
			user.setFid(this.userManager.CreateUUid());
			user.setFpassword(MD5Util.getMD5String(fpassword));
			user.setFcreatetime(new Date());
			user.setFname(ftel);
			user.setFcustomername(ftel);
			user.setFtel(ftel);
			user.setFtype(2);
			user.setFemail("");
			user.setFeffect(0);
			user.setFimid(null);
			this.userManager.saveImpl(user);
			//添加转介绍表数据
			Invite invite=new Invite();
			invite.setFid(this.userManager.CreateUUid());
			invite.setFuserid(fuserid);
			invite.setFuserroleid(Integer.parseInt(fuserroleid));
			invite.setFinviteeid(user.getFid());//被邀请人新注册的用户ID
			invite.setFcreatetime(new Date());
			invite.setFinviteename(user.getFname());
			invite.setFusername(fusername);
			invite.setFapptype(1);//0包装交易平台 1一路好运
			this.inviteManager.saveInvite(invite);
			map.put("data", user);//返回添加的用户
			map.put("success", "true");
			map.put("msg","注册成功！");
			//			} else {
			//				map.put("msg","fcodefail验证码校验失败！");
			//				map.put("success", "false");
			//			}
			return writeAjaxResponse(JSONUtil.getJson(map));
		} catch (Exception e) {
			map.put("success", "false");//存在
			map.put("msg",e.getMessage());
			return writeAjaxResponse(JSONUtil.getJson(map));
		}
	}

	/**
	 * 同城物流转介绍表查询;
	 * **/
	@SuppressWarnings("deprecation")
	public String getInviteeList()
	{
		HashMap<String,Object> map =new HashMap<String,Object>();
		Date starttime=null,endtime=null;
		Integer isReward=0;//否1是2
		Integer fuserid=0;
		
		try {
			if(getRequest().getParameter("starttime")!=null && !"".equals(getRequest().getParameter("starttime"))){
				starttime=new Date(getRequest().getParameter("starttime").toString());
				System.err.println(starttime);
			}
			if(getRequest().getParameter("endtime")!=null && !"".equals(getRequest().getParameter("endtime"))){
				endtime=new Date(getRequest().getParameter("endtime"));
				System.err.println(endtime);
			}
			isReward=Integer.parseInt(getRequest().getParameter("isReward"));
			fuserid=Integer.parseInt(getRequest().getParameter("fuserid"));
			List<Invite> list=this.inviteManager.getInviteeList(1,starttime,endtime,isReward,fuserid);
			if(list != null && list.size() > 0){
				map.put("success", "true");
				map.put("invitelist", list);
				return writeAjaxResponse(JSONUtil.getJson(map));
			}else{
				map.put("success", "false");
				map.put("msg","查询失败！");
				return writeAjaxResponse(JSONUtil.getJson(map));
			}
		} catch (Exception e) {
			map.put("success", "false");
			map.put("msg","查询失败！");
			return writeAjaxResponse(JSONUtil.getJson(map));
		}
	}

	/**
	 * 同城物流转介绍根据邀请人查询其所有下线;
	 * Param参数：“A方用户ID：fid”
	 * **/
	public String getInviteeListById()
	{
		HashMap<String,Object> map =new HashMap<String,Object>();
		String fid; 
		if(getRequest().getParameter("fid")==null || "".equals(getRequest().getParameter("fid"))){
			map.put("success", "false");
			map.put("msg","邀请人ID不能为空！");
			return writeAjaxResponse(JSONUtil.getJson(map));
		}else{
			fid = getRequest().getParameter("fid");
		}
		List<Invite> list=this.inviteManager.getInviteeListById(fid,1);
		map.put("invitelist", list);
		map.put("success", "true");
		return writeAjaxResponse(JSONUtil.getJson(map));
	}

	/**
	 * 根据用户id查询其上线，即邀请人
	 * Param参数：“B方用户ID：finviteeid”
	 */
	public String getInviter()
	{
		HashMap<String,Object> map =new HashMap<String,Object>();
		String fid; 
		if(getRequest().getParameter("fid")==null || "".equals(getRequest().getParameter("fid"))){
			map.put("success", "false");
			map.put("msg","用户ID不能为空！");
			return writeAjaxResponse(JSONUtil.getJson(map));
		}else{
			fid = getRequest().getParameter("fid");
		}
		Invite invite=this.inviteManager.getInviterIdByuserid(fid,1);
		map.put("invite", invite);
		map.put("success", "true");
		return writeAjaxResponse(JSONUtil.getJson(map));
	}
}