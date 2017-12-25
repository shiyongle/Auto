package com.action.interceptor;

import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

/***用户登录拦截器*/
public class UserLoginInterceptor extends AbstractInterceptor {
	private static final long serialVersionUID = 1L;
	
	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		ActionContext context = invocation.getInvocationContext();
		Map<String, Object> session = context.getSession();
		System.out.println("用户是否登录拦截器："+ServletActionContext.getRequest().getSession().getId());
		if(session.get("user") != null){
			return invocation.invoke();
		}
		String url = new String("../user_logon.net");
		HttpServletResponse response = ServletActionContext.getResponse();
	    response.sendRedirect(url);
		return null;
	}
}

