package Com.Base.Web;

import java.lang.reflect.Method;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import Com.Base.data.LogAction;
import Com.Base.data.WrapperResponse;
import Com.Dao.System.IBaseSysDao;
import Com.Entity.System.SysLog;
import Com.Entity.System.Useronline;

public class LogInterceptor implements MethodInterceptor {
	@Autowired
	IBaseSysDao dao;

	@Override
	public Object invoke(MethodInvocation invocation) throws Throwable {
		Object returnValue = invocation.proceed();
		Method method = invocation.getMethod();
		if(method.isAnnotationPresent(LogAction.class)){
			LogAction annotation = method.getAnnotation(LogAction.class);  
			Object[] args = invocation.getArguments();
			if(args.length!=2){
				saveLog(annotation);
				return returnValue;
			}
			Object arg1 = args[0];
			Object arg2 = args[1];
			if(!(arg1 instanceof HttpServletRequest && arg2 instanceof HttpServletResponse)){
				saveLog(annotation);
				return returnValue;
			}
			HttpServletRequest request = (HttpServletRequest) arg1;
			WrapperResponse response = (WrapperResponse) arg2;
			saveLog(request, response, annotation);
		}
		return returnValue;
	}

	private void saveLog(LogAction annotation) {
		SysLog log = new SysLog();
		log.setFid(dao.CreateUUid());
		log.setFtime(new Date());
		String action = annotation.value();
		log.setFaction(action);
		dao.saveOrUpdate(log);
	}

	private void saveLog(HttpServletRequest request, WrapperResponse response,
			LogAction annotation) {
		String action = (String) request.getAttribute("logAction"); 
		if(StringUtils.isEmpty(action)){
			action = annotation.value();
		}
		Useronline useronline = (Useronline)request.getSession().getAttribute("Useronline");
		SysLog log = new SysLog();
		log.setFid(dao.CreateUUid());
		log.setFip(useronline.getFip());
		log.setFsource(useronline.getFsystem());
		log.setFuser(useronline.getFusername());
		log.setFuserid(useronline.getFuserid());
		log.setFtime(new Date());
		log.setFaction(action);
		String data = (String)request.getAttribute("logData");
		log.setFdata(data!=null?data:"");
		try {
			JSONObject jsonData = JSONObject.fromObject(response.getContent());
			if(!(boolean)jsonData.get("success")){
				log.setFsuccess(0);
				log.setFmessage(jsonData.getString("msg"));
			}
		} catch (Exception e) {
			log.setFmessage("日志记录错误，json格式不正确！！");
		}
		dao.saveOrUpdate(log);
	}
	
	

}
