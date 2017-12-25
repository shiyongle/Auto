package com.action.interceptor;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.service.IBaseManager;
import com.service.useronline.TsysUseronlineManager;

public class SessionListener implements HttpSessionListener {
	
	@Autowired
	private IBaseManager iBaseManager;
	
	@Override
	public void sessionCreated(HttpSessionEvent arg0) {
		System.out.println("监听器begin："+arg0.getSession().getId());

	}

	@Override
	public void sessionDestroyed(HttpSessionEvent arg0) {
		System.out.println("监听器end:"+arg0.getSession().getId());
		String sessionid = arg0.getSession().getId();
		String sql="delete from t_sys_useronline where fsessionid='"+sessionid+"'";
		HttpSession session = arg0.getSession(); 
		WebApplicationContext springCtx = WebApplicationContextUtils.getWebApplicationContext(session.getServletContext()); 
		TsysUseronlineManager  userService = (TsysUseronlineManager)(springCtx.getBean("tsysUseronlineManager")); 
		userService.ExecBySql(sql);
	}

}
