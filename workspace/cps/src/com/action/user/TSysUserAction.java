package com.action.user;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import com.action.BaseAction;
import com.model.certificate.DesignerCertificateInfo;
import com.model.PageModel;
import com.model.customer.Customer;
import com.model.productdemandfile.Productdemandfile;
import com.model.supplier.Supplier;
import com.model.system.Syscfg;
import com.model.user.TSysUser;
import com.model.userCustomer.UserCustomer;
import com.model.userdiary.Userdiary;
import com.model.useronline.Useronline;
import com.opensymphony.xwork2.ModelDriven;
import com.service.certificate.DesignerCertificateManager;
import com.service.customer.CustomerManager;
import com.service.productdemandfile.ProductdemandfileManager;
import com.service.supplier.SupplierManager;
import com.service.user.TSysUserManager;
import com.service.userCustomer.UserCustomerManager;
import com.service.userSupplier.TbdUsersupplierManager;
import com.service.useronline.TsysUseronlineManager;
import com.util.Constant;
import com.util.DJException;
import com.util.JSONUtil;
import com.util.LVCSMSFacade;
import com.util.MD5Util;
import com.util.StringUitl;

/**
 * 用户Action
 */
@Scope("prototype")
@Controller("userAction")
public class TSysUserAction extends BaseAction implements ModelDriven<TSysUser>{
	private static final long serialVersionUID = 1L;
	protected static final String CENTER_JSP   = "/pages/user/u_center.jsp";
	protected static final String RZ_JSP  = "/pages/user/u_rz.jsp";
	protected static final String RZ_P_JSP = "/pages/user/u_rz_p.jsp";
	protected static final String RZ_E_JSP = "/pages/user/u_rz_e.jsp";
	protected static final String U_ADDRESS_JSP = "/pages/user/u_address.jsp";
	protected static final String U_PWD_JSP = "/pages/user/u_pwd.jsp";
	protected static final String U_MESSAGE_JSP = "/pages/user/u_message.jsp";
	protected static final String U_ARGUMENT_JSP = "/pages/user/u_argument.jsp";
	protected static final String U_INFO_JSP = "/pages/user/u_info.jsp";
	protected static final String NEW_CENTER_JSP   = "/pages/user/new_centerinfo.jsp";
	protected static final String CHANGETEL_JSP   = "/pages/user/changeTel.jsp";
	protected static final String USERPARAM_JSP   = "/pages/userparam/userParam.jsp";
	protected static final String DESIGNER_AUTH_JSP   = "/pages/user/designer_authenticate.jsp";
	protected static final String RZ_D_JSP = "/pages/user/u_rz_d.jsp";
	
	private TSysUser user=new TSysUser();
	@Autowired
	private TSysUserManager userManager;
	@Autowired
	private UserCustomerManager userCustomerManager;
	@Autowired
	private CustomerManager customerManager;
	@Autowired
	private ProductdemandfileManager productdemandfileManager;
	@Autowired
	private TsysUseronlineManager tsysUseronlineManager;
	@Autowired
	private DesignerCertificateManager designerCertificateManager;
	@Autowired
	private TbdUsersupplierManager userSupplierManager;
	@Autowired
	private SupplierManager supplierManager;
	private  String keyword;
	private PageModel<TSysUser> pageModel;// 分页组件
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	public PageModel<TSysUser> getPageModel() {
		return pageModel;
	}
	public void setPageModel(PageModel<TSysUser> pageModel) {
		this.pageModel = pageModel;
	}
	
	public TSysUser getUser() {
		return user;
	}
	public void setUser(TSysUser user) {
		this.user = user;
	}
	
	@Override
	public TSysUser getModel() {
		return user;
	}
	
	/*** 进入登录界面*/
	public String login() throws Exception{
		return LOGIN;
	}
	/**
	 * 验证登录时的验证码
	 * @return null
	 */
	public String checkIdentCode(){
		/**TODO:获取验证码*/
		String rand=(String) getRequest().getSession().getAttribute("rand");
		String identCode=getRequest().getParameter("identCode");//获取用户输入的验证码
		if (!rand.equals(identCode)) {
			return writeAjaxResponse("fail");
		}else {
			return writeAjaxResponse("success");
		}
	}
	/*** 校验登录用户是否存在*/
	public String checkUser() {
		TSysUser loginUser = this.userManager.login(user.getFname(),user.getFtel(), user.getFpassword());
		if(loginUser !=null){
			//2016-4-2 by lu 用户禁用控制
			if(loginUser.getFisreadonly()==1 && loginUser.getFeffect()==1){
				return writeAjaxResponse("uneffect");
			}
			return writeAjaxResponse("success");
		}else{
			return writeAjaxResponse("failure");
		}
	}
	/*** 登录操作*/
	public String logon() throws Exception{
		TSysUser loginUser = this.userManager.login(user.getFname(),user.getFtel(), user.getFpassword());
		if(loginUser != null){
			//登录成功,新增在线用户信息
			Useronline info = new Useronline();
				info.setFid(this.tsysUseronlineManager.CreateUUid());
				info.setFuserid(loginUser.getFid());
				info.setFlogintime(new Date());
				info.setFlastoperatetime(new Date());
				info.setFusername(loginUser.getFname());
				info.setFsessionid(ServletActionContext.getRequest().getSession().getId());
				info.setFip(ServletActionContext.getRequest().getRemoteAddr());
				String headinfo = getRequest().getHeader("User-Agent").toUpperCase();
				if(headinfo.indexOf("(")>=0){
					String[] strInfo = headinfo.substring(headinfo.indexOf("(") + 1, headinfo.indexOf(")") - 1).split(";");
					if ((headinfo.indexOf("MSIE")) > -1) {
						info.setFbrowser(strInfo[1].trim());
						info.setFsystem(strInfo[2].trim());
					} else {
						String[] str = headinfo.split(" ");
						if (headinfo.indexOf("NAVIGATOR") < 0 && headinfo.indexOf("FIREFOX") > -1) {
							info.setFbrowser(str[str.length - 1].trim());
							info.setFsystem(strInfo[0].trim());
						} else if ((headinfo.indexOf("OPERA")) > -1) {
							info.setFbrowser(str[0].trim());
							info.setFsystem(strInfo[0].trim());
						} else if (headinfo.indexOf("CHROME") < 0 && headinfo.indexOf("SAFARI") > -1) {
							info.setFbrowser(str[str.length - 1].trim());
							info.setFsystem(strInfo[0].trim());
						} else if (headinfo.indexOf("CHROME") > -1) {
							info.setFbrowser(str[str.length - 2].trim());
							info.setFsystem(strInfo[0].trim());
						} else if (headinfo.indexOf("NAVIGATOR") > -1) {
							info.setFbrowser(str[str.length - 1].trim());
							info.setFsystem(strInfo[0].trim());
						} else {
							info.setFbrowser("Unknown Browser");
							info.setFsystem("Unknown OS");
						}
					}
				}else{
					info.setFbrowser("IOS Browser");
					info.setFsystem("IOS");
				}
			this.tsysUseronlineManager.save(info);
			//2015-12-29  登录日志
			Userdiary ud = new Userdiary();
			ud.setFid(tsysUseronlineManager.CreateUUid());
			ud.setFusername(loginUser.getFname());
			ud.setFip(info.getFip());
			ud.setFuserid(loginUser.getFid()); 
			ud.setFremark("2.0登录成功");		
			ud.setFsessionid(info.getFbrowser());
			ud.setFlogintime(new Date());
			ud.setFlastoperatetime(new Date()); 
			this.tsysUseronlineManager.save(ud);
			
			if(loginUser.getFtype() == 0 ||loginUser.getFtype() == 3){
				UserCustomer uc =this.userCustomerManager.getByUserId(loginUser.getFid());
				session.put("user", loginUser);
				getRequest().getSession().setAttribute("cps_user",loginUser);
				getRequest().getSession().setAttribute(Constant.SESSION_USERID, loginUser.getFid());
				//if(uc !=null){
					getRequest().getSession().setAttribute(Constant.SESSION_USER_CUSTOMERID, uc==null?"":uc.getFcustomerid());
				//}
		}else{
				HttpServletRequest request =ServletActionContext.getRequest();
				String url = new String("/vmi/singlelogin.do?sessionid="+request.getSession().getId());
//				String url = new String("/singlelogin.do?sessionid="+request.getSession().getId());
			    HttpServletResponse response = ServletActionContext.getResponse();
			    response.sendRedirect(url);
			    return null;
			}
		}else{
			return LOGIN;
		}
		return INDEX;
	}
	
	
	
	/*** 窗口登录操作*/
	public String wlogon() throws Exception{
		TSysUser loginUser = this.userManager.login(user.getFname(),user.getFtel(), user.getFpassword());
		HashMap<String,Object> map = new HashMap<String,Object>();
 		if(loginUser != null){
			//登录成功,新增在线用户信息
			Useronline info = new Useronline();
				info.setFid(this.tsysUseronlineManager.CreateUUid());
				info.setFuserid(loginUser.getFid());
				info.setFlogintime(new Date());
				info.setFlastoperatetime(new Date());
				info.setFusername(loginUser.getFname());
				info.setFsessionid(ServletActionContext.getRequest().getSession().getId());
				info.setFip(ServletActionContext.getRequest().getRemoteAddr());
				String headinfo = getRequest().getHeader("User-Agent").toUpperCase();
				if(headinfo.indexOf("(")>=0){
					String[] strInfo = headinfo.substring(headinfo.indexOf("(") + 1, headinfo.indexOf(")") - 1).split(";");
					if ((headinfo.indexOf("MSIE")) > -1) {
						info.setFbrowser(strInfo[1].trim());
						info.setFsystem(strInfo[2].trim());
					} else {
						String[] str = headinfo.split(" ");
						if (headinfo.indexOf("NAVIGATOR") < 0 && headinfo.indexOf("FIREFOX") > -1) {
							info.setFbrowser(str[str.length - 1].trim());
							info.setFsystem(strInfo[0].trim());
						} else if ((headinfo.indexOf("OPERA")) > -1) {
							info.setFbrowser(str[0].trim());
							info.setFsystem(strInfo[0].trim());
						} else if (headinfo.indexOf("CHROME") < 0 && headinfo.indexOf("SAFARI") > -1) {
							info.setFbrowser(str[str.length - 1].trim());
							info.setFsystem(strInfo[0].trim());
						} else if (headinfo.indexOf("CHROME") > -1) {
							info.setFbrowser(str[str.length - 2].trim());
							info.setFsystem(strInfo[0].trim());
						} else if (headinfo.indexOf("NAVIGATOR") > -1) {
							info.setFbrowser(str[str.length - 1].trim());
							info.setFsystem(strInfo[0].trim());
						} else {
							info.setFbrowser("Unknown Browser");
							info.setFsystem("Unknown OS");
						}
					}
				}else{
					info.setFbrowser("IOS Browser");
					info.setFsystem("IOS");
				}
			this.tsysUseronlineManager.save(info);
			//2015-12-29  登录日志
			Userdiary ud = new Userdiary();
			ud.setFid(tsysUseronlineManager.CreateUUid());
			ud.setFusername(loginUser.getFname());
			ud.setFip(info.getFip());
			ud.setFuserid(loginUser.getFid()); 
			ud.setFremark("2.0登录成功");		
			ud.setFsessionid(info.getFbrowser());
			ud.setFlogintime(new Date());
			ud.setFlastoperatetime(new Date()); 
			this.tsysUseronlineManager.save(ud);
			
			if(loginUser.getFtype() == 0||loginUser.getFtype() == 3){
				UserCustomer uc =this.userCustomerManager.getByUserId(loginUser.getFid());
				session.put("user", loginUser);
				getRequest().getSession().setAttribute("cps_user",loginUser);
				getRequest().getSession().setAttribute(Constant.SESSION_USERID, loginUser.getFid());
//				if(uc !=null){
					getRequest().getSession().setAttribute(Constant.SESSION_USER_CUSTOMERID, uc==null?"": uc.getFcustomerid());
//				}
				map.put("url", "index.jsp");
				map.put("type", 1);
			}else{
				HttpServletRequest request =ServletActionContext.getRequest();
				String url = new String("vmi/singlelogin.do?sessionid="+request.getSession().getId());
//				String url = new String("singlelogin.do?sessionid="+request.getSession().getId());
			    map.put("url", url);
			    map.put("type", 2);
			}
		}else{
			 map.put("url", "login.jsp");
			 map.put("type", 1);
		}
 		return writeAjaxResponse(JSONUtil.getJson(map));
	}
	
	
	
	
	/**** 退出 操作*/
	public String logout() throws Exception{
		if(session != null && session.size() > 0){
			String sessionid = getRequest().getSession().getId();
			String sql="delete from t_sys_useronline where fsessionid='"+sessionid+"'";
			this.tsysUseronlineManager.ExecBySql(sql);
			session.clear();
			getRequest().getSession().removeAttribute("cps_user");
		}
		return INDEX;
	}
	/*** 注册跳转*/
	public String reg() throws Exception{
		return REG;
	}
	
	/**小登陆窗口跳转注册（新版）*/
	public String smallReg() throws Exception{
		return "smallreg";
	}
	
	/**** 用户注册保存*/
	public String save(){
		user.setFid(this.userManager.CreateUUid());
		user.setFpassword(MD5Util.getMD5String(user.getFpassword()));
		user.setFcreatetime(new Date());
		user.setFcustomername(user.getFname());
		user.setFemail("");
		this.userManager.saveImpl(user);
		session.put("user", user);
		getRequest().getSession().setAttribute("cps_user",user);
		getRequest().getSession().setAttribute(Constant.SESSION_USERID, user.getFid());
//		return REG_SUCCESS;
		return INDEX;
	}
	
	/**** 用户注册保存Ajax*/
	public String saveAjax(){
		if(this.userManager.isUnique(user.getFname())){
			String name = user.getFname();
			String identCode = getRequest().getParameter("identCode");
			LVCSMSFacade lVCSMSFacade = LVCSMSFacade.getLVCSMSFacade(userManager);
			if(lVCSMSFacade.isTheRightLVC(name, identCode,true)){
				user.setFid(this.userManager.CreateUUid());
				user.setFpassword(MD5Util.getMD5String(user.getFpassword()));
				user.setFcreatetime(new Date());
				user.setFcustomername(user.getFname());
				user.setFemail("");
				this.userManager.saveImpl(user);
				session.put("user", user);
				getRequest().getSession().setAttribute("cps_user",user);
				getRequest().getSession().setAttribute(Constant.SESSION_USERID, user.getFid());
				return writeAjaxResponse("success");//注册成功
			} else {
				return writeAjaxResponse("identcode_fail");//验证码验证失败
			}
		} else {
			return writeAjaxResponse("usercheck_fail");//用户重复验证失败
		}
		
	}
	
	/*** 密码找回*/
	public String findPwd(){
		return UNPWD;
	}
	
	public String smallFindPwd(){
		return "smallfidpwd";
	}
	
	/*** 判断注册用户是否已存在*/
	public String isUnique(){
		boolean isUnique =this.userManager.isUnique(getRequest().getParameter("fname"));
		if(isUnique==false){
			return writeAjaxResponse("success");//存在
		}else{
			return writeAjaxResponse("failure");//不存在
		}
	}
	/*** 判断注册用户手机信息*/
	public String getTelInfo(){
		TSysUser user = this.userManager.getUser(getRequest().getParameter("fname"), getRequest().getParameter("ftel"));
		HashMap<String,Object> map =new HashMap<String,Object>();
		if(user !=null){
			map.put("success", "success");//存在
			map.put("fid", user.getFid());
		}else{
			map.put("success", "failure");//不存在
		}
		return writeAjaxResponse(JSONUtil.getJson(map));
	}
	
	
	public String update(){
		System.out.println("-------------------");
		TSysUser u = this.userManager.get(user.getFid());
		u.setFpassword(user.getFpassword());
		this.userManager.update(u);
		System.out.println("+++++++++++++++++++");
		HashMap<String,Object> map =new HashMap<String,Object>();
		map.put("success", "success");
		return writeAjaxResponse(JSONUtil.getJson(map));
	}
	/*检验用户输入的手机号是否被注册*/
	public String checkFtel(){
		String tel = getRequest().getParameter("ftel");
		if(this.userManager.isExistsForTel(tel)){
			return writeAjaxResponse("fail");
		}else {
			return writeAjaxResponse("success");
		}
	}
	/*发送验证码到手机*/
	public String getMobileCode() throws IOException{
		String name = getRequest().getParameter("fname");
		String tel = getRequest().getParameter("ftel");
		if(this.userManager.isExistsForTel(tel)){
			return writeAjaxResponse("fail");
		}else{
			LVCSMSFacade lVCSMSFacade = LVCSMSFacade.getLVCSMSFacade(userManager);
			System.out.println("*****************************************************************开始向手机发送验证码****开始************************************");
			lVCSMSFacade.sendLVCToGoalPhone(name, tel);
			System.out.println("*****************************************************************开始向手机发送验证码****结束************************************");
			return writeAjaxResponse("success");
		}
	}
	
	//校验用户输入的验证码
	public String valiCode() throws IOException{
		String name = getRequest().getParameter("fname");
		String identCode = getRequest().getParameter("identCode");
		LVCSMSFacade lVCSMSFacade = LVCSMSFacade.getLVCSMSFacade(userManager);
		if(lVCSMSFacade.isTheRightLVC(name, identCode,false)){
			return writeAjaxResponse("success");
		}else {
			return writeAjaxResponse("fail");
		}
	}
	
	public String lineDes(){
		final String identCode = getRequest().getParameter("identCode");
		final String linkphone = getRequest().getParameter("linkphone");
		final String phones[]=new String[] {linkphone};
		String linkman = getRequest().getParameter("linkman");
		final LVCSMSFacade lVCSMSFacade = LVCSMSFacade.getLVCSMSFacade(userManager);
		if(lVCSMSFacade.isTheRightLVC(linkphone, identCode,false)){
			TSysUser loginUser =this.userManager.insertLineImpl(identCode,linkphone,linkman);
			Useronline info = new Useronline();
			info.setFid(this.tsysUseronlineManager.CreateUUid());
			info.setFuserid(loginUser.getFid());
			info.setFlogintime(new Date());
			info.setFlastoperatetime(new Date());
			info.setFusername(loginUser.getFname());
			info.setFsessionid(ServletActionContext.getRequest().getSession().getId());
			info.setFip(ServletActionContext.getRequest().getRemoteAddr());
			String headinfo = getRequest().getHeader("User-Agent").toUpperCase();
			if(headinfo.indexOf("(")>=0){
				String[] strInfo = headinfo.substring(headinfo.indexOf("(") + 1, headinfo.indexOf(")") - 1).split(";");
				if ((headinfo.indexOf("MSIE")) > -1) {
					info.setFbrowser(strInfo[1].trim());
					info.setFsystem(strInfo[2].trim());
				} else {
					String[] str = headinfo.split(" ");
					if (headinfo.indexOf("NAVIGATOR") < 0 && headinfo.indexOf("FIREFOX") > -1) {
						info.setFbrowser(str[str.length - 1].trim());
						info.setFsystem(strInfo[0].trim());
					} else if ((headinfo.indexOf("OPERA")) > -1) {
						info.setFbrowser(str[0].trim());
						info.setFsystem(strInfo[0].trim());
					} else if (headinfo.indexOf("CHROME") < 0 && headinfo.indexOf("SAFARI") > -1) {
						info.setFbrowser(str[str.length - 1].trim());
						info.setFsystem(strInfo[0].trim());
					} else if (headinfo.indexOf("CHROME") > -1) {
						info.setFbrowser(str[str.length - 2].trim());
						info.setFsystem(strInfo[0].trim());
					} else if (headinfo.indexOf("NAVIGATOR") > -1) {
						info.setFbrowser(str[str.length - 1].trim());
						info.setFsystem(strInfo[0].trim());
					} else {
						info.setFbrowser("Unknown Browser");
						info.setFsystem("Unknown OS");
					}
				}
			}else{
				info.setFbrowser("IOS Browser");
				info.setFsystem("IOS");
			}
			this.tsysUseronlineManager.save(info);
			session.put("user", loginUser);
			getRequest().getSession().setAttribute("cps_user",loginUser);
			getRequest().getSession().setAttribute(Constant.SESSION_USERID, loginUser.getFid());
			UserCustomer uc =this.userCustomerManager.getByUserId(loginUser.getFid());
			if(uc !=null){
				getRequest().getSession().setAttribute(Constant.SESSION_USER_CUSTOMERID, uc.getFcustomerid());
			}
			//2015-10-25  发送信息（帐号，默认密码）
			new Thread(){
				public void run() {
					try {
						lVCSMSFacade.sendMessage("您的帐号为:"+linkphone+"默认密码为:"+identCode, phones);
					} catch (RemoteException e) {
						e.printStackTrace();
					}
				};
			}.start();
			return writeAjaxResponse("success");
		}else {
			return writeAjaxResponse("fail");
		}
	}
	
	/*** 用户中心*/
	public String ucenter(){
		return CENTER_JSP;
	}
	
	/*** 用户资料*/
	public String new_ucenter(){
		String userId = getRequest().getSession().getAttribute(Constant.SESSION_USERID).toString();
		TSysUser user = this.userManager.get(userId);
		getRequest().getSession().setAttribute("TSysUser",user);
		
		//获取认证信息
		this.setDesignerCertificateInfo(userId);
		
		Supplier supplier = null;
		String supplierId = userSupplierManager.getSupplierId(userId);
		if(supplierId!= null){
			supplier = supplierManager.get(supplierId);
		}
		getRequest().getSession().setAttribute("supplier",supplier);
		return NEW_CENTER_JSP;
	}
	
	/*** 修改手机号*/
	public String changeTel(){
		String userId = getRequest().getSession().getAttribute(Constant.SESSION_USERID).toString();
		TSysUser user = this.userManager.get(userId);
		getRequest().getSession().setAttribute("TSysUser",user);
		return CHANGETEL_JSP;
	}
	
	/*** 申请认证*/
	public String certificate(){
		this.identification();
		String customerId = getRequest().getSession().getAttribute(Constant.SESSION_USER_CUSTOMERID).toString();
		Customer customer = this.customerManager.get(customerId);
		getRequest().getSession().setAttribute("customer",customer);
		return RZ_JSP;
	}
	/*** 个人认证*/
	public String p_certificate(){
		this.identification();
		String customerId = getRequest().getSession().getAttribute(Constant.SESSION_USER_CUSTOMERID).toString();
		Customer customer = this.customerManager.get(customerId);
		getRequest().getSession().setAttribute("customer",customer);
		return RZ_P_JSP;
	}
	/*** 企业认证*/
	public String e_certificate(){
		this.identification();
		return RZ_E_JSP;
	}
	/*** 我的地址*/
	public String my_address(){
		this.identification();
		return U_ADDRESS_JSP;
	}
	/*** 基础资料*/
	public String base_info(){
		String userId = getRequest().getSession().getAttribute(Constant.SESSION_USERID).toString();
		TSysUser user = this.userManager.get(userId);
		getRequest().getSession().setAttribute("TSysUser",user);
		this.identification();
		return U_INFO_JSP;
	}
	/*** 用户参数*/
	public String u_param(){
		this.identification();
		return U_ARGUMENT_JSP;
	}
	/*** 修改密码*/
	public String update_pwd(){
		this.identification();
		String userId = getRequest().getSession().getAttribute(Constant.SESSION_USERID).toString();
		TSysUser user = this.userManager.get(userId);
		getRequest().getSession().setAttribute("TSysUser",user);
		return U_PWD_JSP;
	}
	/*** 站内信*/
	public String web_iemial(){
		this.identification();
		return U_MESSAGE_JSP;
	}
	
	/*** 是否认证*/
	public void identification(){
		String customerId = (String)getRequest().getSession().getAttribute(Constant.SESSION_USER_CUSTOMERID);
		if(StringUitl.isNullOrEmpty(customerId))return;
		Customer customer = this.customerManager.get(customerId);
		//当前登录用户未认证
		if(customer.getFisinternalcompany()==0){
			this.productdemandfileManager.deleteImgByParentid(customer.getFid());
		}
	}
	public String setPwd(){
		HashMap<String,Object> map =new HashMap<String,Object>();
		TSysUser setu = this.userManager.get(user.getFid());
		String oldpwd =MD5Util.getMD5String(user.getFoldpassword());
		if((oldpwd).equals(setu.getFpassword())){//原始密码正确
			this.userManager.setpwd(user.getFpassword(), user.getFid());
			if(session != null && session.size() > 0){
				session.clear();
				getRequest().getSession().removeAttribute("cps_user");
				getRequest().getSession().invalidate();
				getRequest().getSession().removeAttribute(Constant.SESSION_USERID);
				getRequest().getSession().removeAttribute(Constant.SESSION_USER_CUSTOMERID);
			}
			map.put("success", "success");
		}else{
			map.put("success", "failure");
		}
		return writeAjaxResponse(JSONUtil.getJson(map));
	}
	
	//2015-11-09  联系方式更改
	public String setTel(){
		HashMap<String,Object> map =new HashMap<String,Object>();
		userManager.ExecBySql(" update t_sys_user set ftel='"+user.getFtel()+"' where fid = '"+user.getFid()+"'");
		map.put("success", "success");
		return writeAjaxResponse(JSONUtil.getJson(map));
	}
	
	public String saveUserInfo(){
		HashMap<String,Object> map =new HashMap<String,Object>();
		TSysUser u = this.userManager.get(user.getFid());
		LVCSMSFacade lVCSMSFacade = LVCSMSFacade.getLVCSMSFacade(userManager);
		System.out.println("**************提交表单******************");
		/*System.out.println(user.getFname());
		System.out.println(user.getIdentCode());
		System.out.println(lVCSMSFacade.isTheRightLVC(user.getFname(), user.getIdentCode()));*/
		if(user.getIdentCode()!=null && !("").equals(user.getIdentCode())){
			if(lVCSMSFacade.isTheRightLVC(user.getFname(), user.getIdentCode(),false)){
				u.setFfax(user.getFfax());
				u.setFemail(user.getFemail());
				u.setFphone(user.getFphone());
				u.setFtel(user.getFtel());
				u.setFqq(user.getFqq());
				u.setFcustomername(user.getFcustomername());
				this.userManager.update(u);
				this.userManager.updateSupplierAndCustomerName(user.getFid(),user.getFname());
				map.put("success", "success");
			}else{
				map.put("success", "failure");
			}
		}else{
			u.setFfax(user.getFfax());
			u.setFemail(user.getFemail());
			u.setFphone(user.getFphone());
			u.setFtel(user.getFtel());
			u.setFqq(user.getFqq());
			u.setFcustomername(user.getFcustomername());
			this.userManager.update(u);
			this.userManager.updateSupplierAndCustomerName(user.getFid(),user.getFcustomername());
			map.put("success", "success");
		}
		return writeAjaxResponse(JSONUtil.getJson(map));
	}
	
	/*发送验证码到手机*/
	public String getMobileCodeByfindPassword() throws IOException{
		String name = getRequest().getParameter("fname");
		String tel = getRequest().getParameter("ftel");
		LVCSMSFacade lVCSMSFacade = LVCSMSFacade.getLVCSMSFacade(userManager);
		System.out.println("*****************************************************************开始向手机发送验证码****开始************************************");
		lVCSMSFacade.sendLVCToGoalPhone(name, tel);
		System.out.println("*****************************************************************开始向手机发送验证码****结束************************************");
		return writeAjaxResponse("success");
	}
	
	public String userparam(){
		String userId = (String) getRequest().getSession().getAttribute(Constant.SESSION_USERID);
		String supplierid = userSupplierManager.getSupplierId(userId);
		if(supplierid == null){
			writeAjaxResponse(userSupplierManager.alertMsg("您不是制造商，没有权限操作。如有疑问，请联系平台！"));
		}
		int cardAutoReceiveCfg = userManager.getSupplierCardAutoReceiveCfg(supplierid);
		setRequestAttribute("cardAutoReceiveCfg", cardAutoReceiveCfg);
		String sendMessageCfg =userManager.getUserParam(userId,"send_message");
		setRequestAttribute("sendMessageCfg", (sendMessageCfg!=null&&"1".equals(sendMessageCfg))?1:0);
		return USERPARAM_JSP;
	}
	
	public String saveCardAutoReceiveCfg(){
		String value = getRequest().getParameter("value");
		if(! ("1".equals(value) || "0".equals(value)) ){
			return writeAjaxResponse("传参错误！");
		}
		String userId = (String) getRequest().getSession().getAttribute(Constant.SESSION_USERID);
		String supplierid = userSupplierManager.getSupplierId(userId);
		Syscfg obj = userManager.getSuppliserCfg(supplierid, "card_auto_receive");
		if(obj == null){
			obj = new Syscfg(userManager.CreateUUid());
			obj.setFkey("card_auto_receive");
			obj.setFuser(supplierid);
		}
		obj.setFvalue(value);
		userManager.saveOrUpdate(obj);
		writeAjaxResponse("success");
		return null;
	}
	
	
	/**
	 * 加载子账户(分页及模糊查询)
	 */
	public String loadSubAccount()
	{
		String where = "where su.fparentid=?";
		Object[] params = null;
		Map<String, String> orderby =new HashMap<String, String>();
		List<Object> paramls = new ArrayList<Object>();
		paramls.add(getRequest().getSession().getAttribute(Constant.SESSION_USERID).toString());
		if(keyword !=null&&!"".equals(keyword)){
			where = where +" and (su.FNAME like ? or su.FTEL like ? )";
			paramls.add("%"+keyword+"%");
			paramls.add("%"+keyword+"%");
		}
		params =(Object[])paramls.toArray(new Object[paramls.size()]);
		orderby.put("su.fcreatetime", "desc");//通过创建时间排序
		pageModel = this.userManager.getSubByPid(where, params, orderby, pageNo, pageSize);
		List<TSysUser> list = pageModel.getList();
		HashMap<String, Object> m = new HashMap<String, Object>();
		m.put("list", list);
		m.put("totalRecords", pageModel.getTotalRecords());//总条数
		m.put("pageNo", pageModel.getPageNo());//第几页
		m.put("pageSize", pageModel.getPageSize());//每页显示多少条
		return writeAjaxResponse(JSONUtil.getJson(m));
	}
	
	/**
	 * 保存或修改子账户基本信息
	 * @return
	 */
	public String editSubAccount()
	{
		//父账户
		TSysUser tsu=this.userManager.get(getRequest().getSession().getAttribute(Constant.SESSION_USERID).toString());
		String subId=user.getFid();//子账户ID
		if(subId!=null&&!"".equals(subId))//修改
		{
			Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
			Matcher m = p.matcher(user.getFtel());
			if(!m.matches())
			{
				return writeAjaxResponse("tel_invalid");
			}
			TSysUser u=this.userManager.get(subId);//获取子账户
			if(user.getFname()!=u.getFname())
			{
				if(!this.userManager.isNameUnique(user.getFname(),u.getFid()))
				{
					return writeAjaxResponse("nameunique_fail");
				}
				u.setFname(user.getFname());
			}
			if(user.getFtel()!=u.getFtel())
			{
				if(!this.userManager.isPhoneUnique(user.getFtel(),u.getFid()))
				{
					return writeAjaxResponse("telunique_fail");
				}
				u.setFtel(user.getFtel());
			}
			this.userManager.update(u);
			return writeAjaxResponse("updatesuccess");
		}else//新增
		{
			if(!this.userManager.isNameUnique(user.getFname(),null))
			{
				return writeAjaxResponse("nameunique_fail");
			}
			else{
				Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
				Matcher m = p.matcher(user.getFtel());
				if(!m.matches())
				{
					return writeAjaxResponse("tel_invalid");
				}else if(!this.userManager.isPhoneUnique(user.getFtel(),null))
				{
					return writeAjaxResponse("telunique_fail");
				}
				String password=user.getFtel().substring(user.getFtel().length()-6, user.getFtel().length());
				user.setFname(user.getFname());
				user.setFpassword(MD5Util.getMD5String(password));
				user.setFeffect(0);//1为已禁用账户，0为默认的正常账户
				user.setFisreadonly(1);//子账号只查看自己
				user.setFcustomername(user.getFname());
				user.setFemail("");
				user.setFtype(3);
				user.setFtel(user.getFtel());
				user.setFcreatetime(new Date());
				user.setFparentid(tsu.getFid());//父账户Id，即当前登录账户
				this.userManager.saveSubAccount(user);//保存
				return writeAjaxResponse("savesuccess");
			}
		}
	}
	
	/**
	 * 修改子账户的启用禁用状态
	 * @return
	 */
	public String editSubState()
	{
		String fid=getRequest().getParameter("id");
		TSysUser u=this.userManager.get(fid);
		if(u!=null)
		{
			if(u.getFeffect()==0)
			{
				u.setFeffect(1);
			}else if(u.getFeffect()==1)
			{
				u.setFeffect(0);
			}
			this.userManager.update(u);
			return writeAjaxResponse("success");
		}
		return null;
	}
	
	/**
	 * 重置密码，设置为手机后6位
	 * @return
	 */
	public String editResetPwd()
	{
		String fid=getRequest().getParameter("id");
		TSysUser u=this.userManager.get(fid);
		if(u!=null)
		{
			String telephone=u.getFtel();//重置密码为手机号后6位
			u.setFpassword(MD5Util.getMD5String(telephone.substring(telephone.length()-6, telephone.length())));
			this.userManager.update(u);
			return writeAjaxResponse("success");
		}
		return null;
	}
	
	public String saveUserCfg(){
		String key = getRequest().getParameter("key");
		String value = getRequest().getParameter("value");
		String userId = (String) getRequest().getSession().getAttribute(Constant.SESSION_USERID);
		if("send_message".equals(key)){
			if(! ("1".equals(value) || "0".equals(value)) ){
				return writeAjaxResponse("传参错误！");
			}
		}
		Syscfg cfg=userManager.getSuppliserCfg(userId,key);
		if(cfg==null)
		{
			cfg = new Syscfg(userManager.CreateUUid());
			cfg.setFkey(key);
			cfg.setFuser(userId);	
		}
		cfg.setFvalue(value);
		userManager.save(cfg);
		writeAjaxResponse("success");
		return null;
	}
	
	/*** 设计服务商认证*/
	public String d_certificate(){
		String userId = (String) getRequest().getSession().getAttribute(Constant.SESSION_USERID);
		this.setDesignerCertificateInfo(userId);
		
		return RZ_D_JSP;
	}
	
	/*** 保存设计服务商认证信息*/
	public String saveDesignerCertificateInfo(){
		String companyname = getRequest().getParameter("fcompanyname");
		String industry = getRequest().getParameter("findustry");
		String files = "";
		String file1 = getRequest().getParameter("file1");
		String file2 = getRequest().getParameter("file2");
		String file3 = getRequest().getParameter("flie3");
		
		String userId = (String) getRequest().getSession().getAttribute(Constant.SESSION_USERID);
		
		if (!StringUitl.isNullOrEmpty(file1)) {
			Productdemandfile f = productdemandfileManager.get(file1);
			if (f!= null && !f.getFparentid().equals(userId)) {
				throw new DJException("参数错误！");
			} else {
				files += file1 + ",";
			}
		}
		if (!StringUitl.isNullOrEmpty(file2)) {
			Productdemandfile f = productdemandfileManager.get(file2);
			if (f!= null && !f.getFparentid().equals(userId)) {
				throw new DJException("参数错误！");
			} else {
				files += file2 + ",";
			}
		}
		if (!StringUitl.isNullOrEmpty(file3)) {
			Productdemandfile f = productdemandfileManager.get(file3);
			if (f!= null && !f.getFparentid().equals(userId)) {
				throw new DJException("参数错误！");
			} else {
				files += file3 + ",";
			}
		}
		if(StringUitl.isNullOrEmpty(companyname)){
			throw new DJException("请输入企业名称！");
		} 
		if(StringUitl.isNullOrEmpty(industry)){
			throw new DJException("请输入所属行业！");
		}
		if(StringUitl.isNullOrEmpty(files)){
			throw new DJException("请上传营业执照、税务登记证、组织机构代码证");
		}
		
		DesignerCertificateInfo info = designerCertificateManager.getDesignerCertificateInfoByUserId(userId);
		if(info == null){ 
			info = new DesignerCertificateInfo();
			info.setFid(designerCertificateManager.CreateUUid());
			info.setFcreatetime(new Date());
		} 
		if(info.getFstatus() != null && info.getFstatus() == 1){
			throw new DJException("您已通过认证！");
		}else if (info.getFstatus() != null && info.getFstatus() == 0){
			throw new DJException("正在审核中，请耐心等待...");
		}
		info.setFcompanyname(companyname);
		info.setFfiles(files);
		info.setFindustry(industry);
		info.setFstatus(0);
		info.setFuserid(userId);
		info.setFupdatetime(new Date());
		
		designerCertificateManager.saveOrUpdate(info);
		//删除多余的认证资料文件
		productdemandfileManager.deleteRedundantCertificateImgImpl(userId, files);
		writeAjaxResponse("success");
		return null;
	}
	
	public void saveSupplier(){
		String userId = getRequest().getSession().getAttribute(Constant.SESSION_USERID).toString();
		String supplierId = userSupplierManager.getSupplierId(userId);
		if(supplierId!= null){
			Supplier supplier = supplierManager.get(supplierId);
			supplier.setFfax(getRequest().getParameter("ffax"));
			supplier.setFtel(getRequest().getParameter("ftel"));
			supplier.setFaddress(getRequest().getParameter("faddress"));
			supplier.setFdescription(getRequest().getParameter("fdescription"));
			supplierManager.update(supplier);
			writeAjaxResponse("success");
		}
	}
	
	private void setDesignerCertificateInfo(String userId){
		DesignerCertificateInfo info = designerCertificateManager.getDesignerCertificateInfoByUserId(userId);

		List<String> images = new ArrayList<>();
		if(info == null){
			info = new DesignerCertificateInfo();
		} else {
			for(String file : info.getFfiles().split(",")){
				Productdemandfile f  = productdemandfileManager.get(file);
				if(f != null){
					images.add(f.getFid());
				}
			}
		}
		getRequest().setAttribute("images", JSONUtil.getJson(images));
		getRequest().setAttribute("designerCertificateInfo", info);
	}
}