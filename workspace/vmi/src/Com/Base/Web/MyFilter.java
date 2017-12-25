package Com.Base.Web;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.metadata.ClassMetadata;

import Com.Base.Util.JsonUtil;
import Com.Base.Util.ServerContext;
import Com.Base.Util.SpringContextUtils;
import Com.Base.Util.params;
import Com.Base.data.WrapperResponse;
import Com.Dao.System.ISysUserDao;

public class MyFilter implements Filter {
	private ISysUserDao sysUserDao;
	private Map<String, ClassMetadata> entitycls;
	
	private static String[] ALLOWED_URLS = new String[]{"uploadAndroidVmiLog.do","noteVerify.do","hasValidateCode.do","getValidateCode.do","findAnswerByTime.do","gainNotesBase.do","saveNoteAnswerForBoard.do","saveNoteForBoard.do"};
	
//	private static Set<String> ALLOWED_URLS_SET = new HashSet<>();
//	
//	static{
//		
//		for (String url : ALLOWED_URLS) {
//			ALLOWED_URLS_SET.add(url);
//		}
//		
//		
//	}
	
	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		// TODO Auto-generated method stub
		HttpServletRequest temprequest = (HttpServletRequest) request;
		String url = temprequest.getRequestURI().toString();
		response.setCharacterEncoding("utf-8");
		request.setCharacterEncoding("utf-8");
		response = new WrapperResponse((HttpServletResponse)response);
		/*if (url.startsWith("/vmi/"))
		{
			HttpServletResponse resp=(HttpServletResponse)response;
			resp.sendRedirect("/");
            return;	
		}*/
		sysUserDao = (ISysUserDao) SpringContextUtils
				.getBean("SysUserDao");
		if(entitycls==null)
		{
			entitycls=sysUserDao.getAllClassMetadata();
		}
		if (url.contains(".do")) {
			/**
			 * 后面3个验证码用?后面添加的变成一次性登录码，todo，改成常量数组
			 */
			if (url.contains("login.do")||url.contains("loginActionAPP.do") || url.contains("singlelogin.do") || url.contains("loginIMcalled.do") || url.contains("download.do") || url.contains("logonAction.do")||url.contains("gainVCImg.do")|| url.contains("validateVCode.do")|| url.contains("gainValidateVCodeState.do")||url.contains("gainLoginValidateVCode.do")||url.contains("changePWByLVC.do")||url.contains("gainLoginValidateVCodeTel.do")||url.contains("submitRegister.do")) {
				chain.doFilter(request, response);
			} 
			else if (isAllowedWithoutLogin(url)) {
				
				chain.doFilter(request, response);
			}
			
			else {
				ISysUserDao sysUserDao = (ISysUserDao) SpringContextUtils
						.getBean("SysUserDao");
				params params = sysUserDao.QueryCheckSessionid(temprequest);
				if (params.getBoolean("success")) {

					Set<String> set=request.getParameterMap().keySet();
					Set<String> entityset=entitycls.keySet();
					Iterator<String> it = set.iterator();
					Iterator<String> entityit ;
					String key="";
					String entitykey="";
					while (it.hasNext()) {
						key = it.next();
						entityit=entityset.iterator();
						String data=request.getParameter(key);
						data=data.replaceAll("[*]","X").replaceAll("--","-").replaceAll("'","");
						for(int i=0;i<ServerContext.getSpecialstr().length;i++)
						{
							if(data.contains(ServerContext.getSpecialstr()[i]))
							{
								response.getWriter().write(
										JsonUtil.result(false, "不能包含特殊字符：", "", ""));
								return;
							}
						}
						while(entityit.hasNext())
						{
							entitykey=entityit.next();
							if(entitykey.endsWith("."+key))
							{
//								String data=request.getParameter(key);
								if(data.startsWith("["))
								{
									request.setAttribute(key, JsonUtil.jsTolist(data, entitykey));
								}
								else if(data.startsWith("{"))
								{
									request.setAttribute(key,JsonUtil.jsToObject(data, entitykey));
								}
									
							}
							
						}
						//Map<String, ClassMetadata>  a=sysUserDao.getAllClassMetadata();
					}
					chain.doFilter(request, response);
				} else {
					if (!url.contains("index.do") && ("您没有此权限!".equals(params.getString("msg")) || "超时,请重新登录!".equals(params.getString("msg")) || (request.getParameter("AppType")!=null &&request.getParameter("AppType").equals("dongjingSDK"))))
					{
						response.getWriter().write(
								JsonUtil.result(false, params.getString("msg"), "", ""));
					}
					else
					{
						String path=temprequest.getContextPath();
						HttpServletResponse resp=(HttpServletResponse)response;
						resp.sendRedirect(path+"/login.do");
		                return;		
					}
//					response.setCharacterEncoding("utf-8");
//					response.getWriter().write(
//							JsonUtil.result(false, params.getString("msg"), "", ""));
//					return;
	                
					// JsonUtil.result(false, "登录超时", "", "")
					// "<script type=\"text/javascript\">Ext.MessageBox.alert('错误', obj.msg);</script>"
				}
				
			}
		} else {
			chain.doFilter(request, response);
		}
	}

	
	private boolean isAllowedWithoutLogin(String url) {
		// TODO Auto-generated method stub

		for (String urlT : ALLOWED_URLS) {
			
			if (url.contains(urlT)) {
				
				return true;
				
			}
			
		}

		return false;
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub

	}

}
