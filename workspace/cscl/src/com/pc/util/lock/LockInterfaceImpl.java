package com.pc.util.lock;

import java.util.Date;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;
import org.w3c.dom.css.ElementCSSInlineStyle;

import com.pc.util.RedisUtil;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;

@Repository("lockInterfaceImpl")
public class LockInterfaceImpl implements MethodInterceptor {

	@Override
	public Object invoke(MethodInvocation invocation) throws Throwable {
		// 通过方法获取注解 如果没有注解 则lock为空
		Lock lock = AnnotationUtils.findAnnotation(invocation.getMethod(), Lock.class);
		Object returnValue = null;
		//如果有注解则执行下列方法
		if (lock != null) {
			System.out.println(22222);
			//key
			String lockname = lock.value();
			try {
				while (true) {
					Thread.sleep(5);
					//生成锁  如果能set进去返回值为0
					if (RedisUtil.setnx(lockname, new Date().toString()) != 0) {
						//设置锁的时间为10秒
						RedisUtil.pexpire(lockname, 10000L);
						break;
					}
				}returnValue = invocation.proceed();
				
			} finally {
				//最后解锁
				RedisUtil.del(lockname);
			}
		}else{
			returnValue = invocation.proceed();
		}
		return returnValue;
	}

	
	

}
