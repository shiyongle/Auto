package com.pc.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.http.HttpServlet;

import com.pc.util.DJException;
import com.pc.util.ServerContext;


public class MyServletContextListener extends HttpServlet implements ServletContextListener {

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
//		ISysUserDao ISysUDao = (ISysUserDao) SpringContextUtils
//				.getBean("SysUserDao");
		try {
			ServerContext.ServerContext();
		} catch (DJException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}

}
