package com.pc.interceptor;

import java.io.PrintWriter;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.pc.util.JSONUtil;
import com.pc.util.ServerContext;

 
/**
 * <p>Description:登录认证拦截器</p>
 * @author twr
 */
public class LoginInterceptor implements HandlerInterceptor {
	private static final String[] IGNORE_URI = {"index.do","checkuser.do","reg.do","logon.do","getRegValidateVCodeTel.do","regValiCode.do","getFndValidateVCodeTel.do","checkLVC.do","changePWByLVC.do","pcAndAppUuid.do","uuid.do","QRLogin.do","paymentCallback.do","test.do","updateOnlineDateL.do"};

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response,Object handler) throws Exception {
		response.setCharacterEncoding("utf-8");
		request.setCharacterEncoding("utf-8");
		  String url=request.getRequestURI();
		  for (String s : IGNORE_URI) {
            if (url.indexOf(s)>=0) {
               return true;
            }
		  }
		//增加APP登录状态验证，通过缓存记录sessionID，后面接口验证缓存中是否有对应的sessionid，有表示已经登录   BY CC 2016-02-24 START
		  PrintWriter out = response.getWriter();
		  HttpSession session=request.getSession();
		  HashMap<String,Object> map=new HashMap<>();
		  if(session==null || session.getId().equals("")){
			  map.put("success", "false");
			  map.put("msg","登录超时，请重新登录");
			  out.write( JSONUtil.getJson(map));
			  if(url.startsWith("/cscl/pcWeb")){//pcWeb页面跳转首页
				  out.write("<script type='text/javascript'>location.href = '/cscl/pages/pcWeb/login/login_in.jsp';</script>");
			  }
			  else //pc 后台运营跳转首页
			  {
		     	    out.write("<script type='text/javascript'>window.parent.frames.location.href = '../login.jsp';</script>");
			  }
			  return false;  
		  }
		  if(!ServerContext.getUseronline().containsKey(session.getId().toString()))
		  {
			  if(url.startsWith("/cscl/app"))
			  {
				  map.put("success", "false");
				  map.put("msg","登录超时，请重新登录");
				  out.write( JSONUtil.getJson(map));
			  }
			  else if(url.startsWith("/cscl/pcWeb")){//pcWeb页面跳转首页
				  out.write("<script type='text/javascript'>location.href = '/cscl/pages/pcWeb/login/login_in.jsp';</script>");
			  }
			  else //pc 后台运营跳转首页
			  {
		     	    out.write("<script type='text/javascript'>window.parent.frames.location.href = '../login.jsp';</script>");
			  }
			  return false;  
		  }
		  else
		  {        
			  
	 
// 		     String userstr=request.getParameter("userId");
// 		     System.out.println(userstr);
//   		     HashMap<String, Util_UserOnline>  use=ServerContext.getUseronline();
//           	            
//  		     System.out.println(1);
// 		   
//			      
			    
			  /**
					request.getClass().getResource("").getPath();
					Class clazz = request.getClass();
			        Field requestField = clazz.getDeclaredField("request");
			        requestField.setAccessible(true);
			        Object innerRequest = requestField.get(request);//获取到request对象
			        //设置尚未初始化 (否则在获取一些参数的时候，可能会导致不一致)
			        Field field = innerRequest.getClass().getDeclaredField("parametersParsed");
			        field.setAccessible(true);
			        field.setBoolean(innerRequest , false);
			        Field coyoteRequestField = innerRequest.getClass().getDeclaredField("coyoteRequest");
			        coyoteRequestField.setAccessible(true);
			        Object coyoteRequestObject = coyoteRequestField.get(innerRequest);//获取到coyoteRequest对象
			        Field parametersField = coyoteRequestObject.getClass().getDeclaredField("parameters");
			        parametersField.setAccessible(true);
			        Object parameterObject = parametersField.get(coyoteRequestObject);//获取到parameter的对象
			        //获取hashtable来完成对参数变量的修改
			        Field hashTabArrField = parameterObject.getClass().getDeclaredField("paramHashValues");
			        hashTabArrField.setAccessible(true);
			        @SuppressWarnings("unchecked")
			        HashMap<String, ArrayList<String>> mapcc = (HashMap<String, ArrayList<String>>)hashTabArrField.get(parameterObject);
			        ArrayList<String> v=new ArrayList(1);
			        v.add("cc");
			        mapcc.put("userId" , v);//ServerContext.getUseronline().get(session.getId().toString()).getFuserid()
			        //还要修改parametermap值，否则数据不一致   
			        Field parameterMapfield = innerRequest.getClass().getDeclaredField("parameterMap");
			        parameterMapfield.setAccessible(true);
			        HashMap<String, String[]> parameterMapObject =(HashMap<String, String[]>) parameterMapfield.get(innerRequest);//获取到parameter的对象
			        Field parameterMaplockfield = parameterMapObject.getClass().getDeclaredField("locked");
			        parameterMaplockfield.setAccessible(true);
			        //parameterMaplockfield.setBoolean(parameterMapObject , false);
			        parameterMapObject.put("userId", new String[]{"cc"});
			        request.getParameter("userId");
*/
//			  String[] a=new String[]{ServerContext.getUseronline().get(session.getId().toString()).getFuserid()};
//			  request.getParameterMap().put("userId", a);
			  //设置初始参数 by xixi 2016-4-23
//			  String a=ServerContext.getUseronline().get(session.getId().toString()).getFuserid();
//			  request.getServletContext().setInitParameter("userId", a);
			  
		  }
		  return true;
		//增加APP登录状态验证，通过缓存记录sessionID，后面接口验证缓存中是否有对应的sessionid，有表示已经登录   BY CC 2016-02-24 end
		  //原来代码备份 start
//		  String username=(String) session.getAttribute("username");
//		  if(username == null){  
//             //request.getRequestDispatcher("/login.jsp").forward(request, response);  
//             PrintWriter out = response.getWriter();
//     	     out.write("<script type='text/javascript'>window.parent.frames.location.href = '../login.jsp';</script>");
//             return false;  
//          }
//		  return true;
        	 //原来代码备份 end
	}

	
	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1,Object arg2, ModelAndView arg3) throws Exception {
	}

   
	@Override
	public void afterCompletion(HttpServletRequest arg0,HttpServletResponse arg1, Object arg2, Exception arg3) throws Exception {
	}
}
