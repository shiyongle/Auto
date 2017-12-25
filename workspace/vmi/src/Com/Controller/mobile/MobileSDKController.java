package Com.Controller.mobile;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import sun.misc.BASE64Decoder;

import Com.Base.Util.DJException;
import Com.Base.Util.JsonUtil;
import Com.Base.Util.ListResult;
import Com.Base.Util.ServerContext;
import Com.Controller.System.SysUserController;
import Com.Dao.System.ICertificateDao;
import Com.Dao.System.ISysUserDao;
import Com.Dao.order.IAppraiseDao;
import Com.Entity.System.Certificate;
import Com.Entity.System.SysUser;
import Com.Entity.System.Syscfg;
import Com.Entity.System.Userdiary;
import Com.Entity.System.Useronline;
import Com.Entity.order.Appraise;

@Controller
public class MobileSDKController {
	Logger log = LoggerFactory.getLogger(MobileSDKController.class);
	@Resource
	private ISysUserDao SysUserDao;
	/**
	 * APP登陆口
	 * @param request
	 * @param reponse
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/loginActionAPP")
	public String loginActionAPP(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException { // , Mainmenuitem
		String result = "";
		//关于用户的系统日志，
		Userdiary userdiaryinfo=new Userdiary();
		try {
			// 核对用户名和密码
			SysUser userinfo = SysUserDao.ExecCheckLogin(request);
			userdiaryinfo.setFid(SysUserDao.CreateUUid());
			userdiaryinfo.setFusername(request.getParameter("username")==null?"空用户名":request.getParameter("username"));
			userdiaryinfo.setFip(request.getRemoteAddr());
			if (userinfo != null) {
				userdiaryinfo.setFuserid(userinfo.getFid()); 
				// 生成用户在线信息
				Useronline info = new Useronline();
				info.setFid(SysUserDao.CreateUUid());
				info.setFuserid(userinfo.getFid());
				info.setFlogintime(new Date());
				info.setFlastoperatetime(new Date());
				info.setFusername(userinfo.getFname());
				String sessionid = request.getSession().getId();
				info.setFsessionid(sessionid);
				info.setFip(request.getRemoteAddr());
				String headinfo = request.getHeader("User-Agent").toUpperCase();
				//苹果系统没有带括号，兼容苹果系统修改     BY CC  2015-05-08 start
				if(headinfo.indexOf("(")>=0)
				{
					String[] strInfo = headinfo.substring(
							headinfo.indexOf("(") + 1, headinfo.indexOf(")") - 1)
							.split(";");
					if ((headinfo.indexOf("MSIE")) > -1) {
						info.setFbrowser(strInfo[1].trim());
						info.setFsystem(strInfo[2].trim());
					} else {
						String[] str = headinfo.split(" ");
						if (headinfo.indexOf("NAVIGATOR") < 0
								&& headinfo.indexOf("FIREFOX") > -1) {
							info.setFbrowser(str[str.length - 1].trim());
							info.setFsystem(strInfo[0].trim());
						} else if ((headinfo.indexOf("OPERA")) > -1) {
							info.setFbrowser(str[0].trim());
							info.setFsystem(strInfo[0].trim());
						} else if (headinfo.indexOf("CHROME") < 0
								&& headinfo.indexOf("SAFARI") > -1) {
							info.setFbrowser(str[str.length - 1].trim());
							info.setFsystem(strInfo[0].trim());
						} else if (headinfo.indexOf("CHROME") > -1) {
							info.setFbrowser(str[str.length - 2].trim());
							info.setFsystem(strInfo[0].trim());
						} else if (headinfo.indexOf("NAVIGATOR") > -1) {
							info.setFbrowser(str[str.length - 1].trim());
							info.setFsystem(strInfo[0].trim());
						} else {
							info.setFbrowser("Unknown Browser");
							info.setFsystem("Unknown OS");
						}
					}
				}
				else
				{
					//没有括号的是IOS系统，直接赋值    BY   CC 2015-05-08
					info.setFbrowser("IOS Browser");
					info.setFsystem("IOS");
				}
				SysUserDao.saveOrUpdate(info);
				//移动端检验是否是纸箱、纸板用户
				int usertype=0;
				if(request.getParameter("version")==null) throw new DJException("非手机端登陆");
				usertype=SysUserDao.getUserTypeInfo(info.getFuserid());
				result = JsonUtil.result(true, "", "", "{\"username\":\""
						+ info.getFusername() + "\",\"sessionid\":\""
						+ request.getSession().getId() + "\",\"usertype\":\""+usertype+"\"}");
				request.getSession().setAttribute("Useronline", info);
				userdiaryinfo.setFremark("登录成功");	
			} else {
				userdiaryinfo.setFremark("登录失败:用户名或密码错误");
				result = "{\"success\":false,\"msg\":\"用户名或密码错误！\"}";
			}

		} catch (Exception e) {
			userdiaryinfo.setFremark("登录失败:"+e.getMessage());
			result = "{\"success\":false,\"msg\":'"+ e.getMessage() + "'}";
		}
		String sql = "update t_sys_user set fstate=0 where fname='"+request.getParameter("username")+"'";
		SysUserDao.ExecBySql(sql);
		
		userdiaryinfo.setFsessionid(request.getSession().getId());
		userdiaryinfo.setFlogintime(new Date());
		userdiaryinfo.setFlastoperatetime(new Date());
		SysUserDao.saveOrUpdate(userdiaryinfo);
		reponse.getWriter().write(result);
		return null;
	}
}
