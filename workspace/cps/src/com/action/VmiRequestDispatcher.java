package com.action;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;

import com.model.user.TSysUser;
import com.model.userCustomer.UserCustomer;
import com.model.useronline.Useronline;
import com.service.user.TSysUserManager;
import com.service.userCustomer.UserCustomerManager;
import com.service.useronline.TsysUseronlineManager;
import com.util.Constant;

public class VmiRequestDispatcher extends BaseAction {
	private static final long serialVersionUID = 3893759465857282465L;
	
	@Autowired
	private TsysUseronlineManager tsysUseronlineManager;
	@Autowired
	private TSysUserManager userManager;
	@Autowired
	private UserCustomerManager userCustomerManager;
	
	/***从CPS-VMI系统切换至VMI系统
	 * @throws IOException */
	public String redispathcher() throws IOException{
		HttpServletRequest request =ServletActionContext.getRequest();
//		String url = new String("http://127.0.0.1:8080/vmi/singlelogin.do?sessionid="+request.getSession().getId());
		String url = new String("http://127.0.0.1:8080/singlelogin.do?sessionid="+request.getSession().getId());
	    HttpServletResponse response = ServletActionContext.getResponse();
	    try {
	    	session.clear();
			request.getSession().removeAttribute("cps_user");
			request.getSession().removeAttribute(Constant.SESSION_USERID);
			request.getSession().removeAttribute(Constant.SESSION_USER_CUSTOMERID);
			response.sendRedirect(url);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/***从VMI系统切换至CPS-VMI系统*/
	public String cpsvmiDispathcher(){
		HttpServletRequest request =ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		String sessionid=request.getParameter("sessionid").toString();
		String url = request.getParameter("url").toString();
		String newsessionid=request.getSession().getId();
		List<Useronline> userline= this.tsysUseronlineManager.findByHql("FROM Useronline where fsessionid='"+sessionid+"'");
		if(userline.size()>0){
			String sql = "delete from t_sys_useronline where fsessionid='" + sessionid + "'";
			this.tsysUseronlineManager.ExecBySql(sql);
			
			Useronline newuoinfo=new Useronline();
				newuoinfo.setFid(this.tsysUseronlineManager.CreateUUid());
				newuoinfo.setFbrowser(userline.get(0).getFbrowser());
				newuoinfo.setFip(userline.get(0).getFip());
				newuoinfo.setFlastoperatetime(userline.get(0).getFlastoperatetime());
				newuoinfo.setFlogintime(userline.get(0).getFlogintime());
				newuoinfo.setFsessionid(newsessionid);
				newuoinfo.setFsystem(userline.get(0).getFsystem());
				newuoinfo.setFuserid(userline.get(0).getFuserid());
				newuoinfo.setFusername(userline.get(0).getFusername());
			 this.tsysUseronlineManager.save(newuoinfo);
			//向CPS-VMI端request中增加用户信息
			TSysUser currentUser = this.userManager.get(userline.get(0).getFuserid());
			session.put("user", currentUser);
			request.getSession().setAttribute("cps_user",currentUser);
			request.getSession().setAttribute(Constant.SESSION_USERID, currentUser.getFid());
			UserCustomer uc =this.userCustomerManager.getByUserId(currentUser.getFid());
			if(uc !=null){
				getRequest().getSession().setAttribute(Constant.SESSION_USER_CUSTOMERID, uc.getFcustomerid());
			}
//			String url1 = new String("/index.jsp");
			String url1 = new String("/cps/index.jsp");
			if(!url.isEmpty()){
				url1 = url;
			}
			try {
				response.sendRedirect(url1);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}else{
//			String url2 = new String("/user_logon.net");
			String url2 = new String("/cps/user_logon.net");
			try {
				response.sendRedirect(url2);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

}
