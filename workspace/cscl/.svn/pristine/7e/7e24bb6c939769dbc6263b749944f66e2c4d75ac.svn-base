package com.pc.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class SpringContextUtils implements ApplicationContextAware{
	private static ApplicationContext contex;
	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		SpringContextUtils.contex=applicationContext;
		//初始化编码规则类  BY CC 2016-04-27
		CacheUtilByCC.init();
		
	}
	public static Object getBean(String key) {
        if (contex ==null) return null;
        return contex.getBean(key);
    }
}
