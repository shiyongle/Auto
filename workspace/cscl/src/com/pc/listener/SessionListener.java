package com.pc.listener;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import com.pc.util.ServerContext;


public class SessionListener implements HttpSessionListener {

	@Override
	public void sessionCreated(HttpSessionEvent event) {
		// TODO Auto-generated method stub
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent event) {
		// TODO Auto-generated method stub		
		//20151020同步清除Session缓存;
		try {
			ServerContext.delUseronline(event.getSession().getId());
		} catch (Exception e) {
			// TODO: handle exception
		}
		
	
	}

}
