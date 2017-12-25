package com.action.interceptor;

import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
/***用户登录拦截器*/
public class UserLoginIframeInterceptor extends AbstractInterceptor {

	private static final long serialVersionUID = 6408273474515387051L;

	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		ActionContext context = invocation.getInvocationContext();
		Map<String, Object> session = context.getSession();
		System.out.println("用户是否登录拦截器——iFrame："+ServletActionContext.getRequest().getSession().getId());
		if(session.get("user") != null){
			return invocation.invoke();
		}
		HttpServletResponse response = ServletActionContext.getResponse();
	    PrintWriter out = response.getWriter();
	    out.write("<script type='text/javascript'>window.parent.frames.location.href = '../user_logon.net';</script>");
		return null;
	}

}
