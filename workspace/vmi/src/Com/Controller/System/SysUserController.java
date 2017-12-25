package Com.Controller.System;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import sun.misc.BASE64Decoder;
import Com.Base.Util.DJException;
import Com.Base.Util.DataUtil;
import Com.Base.Util.ExcelUtil;
import Com.Base.Util.JsonUtil;
import Com.Base.Util.ListResult;
import Com.Base.Util.ServerContext;
import Com.Base.Util.params;
import Com.Base.Util.mySimpleUtil.MySimpleToolsZ;
import Com.Base.Util.mySimpleUtil.RandomValidateCode;
import Com.Base.Util.mySimpleUtil.ValidateCode;
import Com.Base.Util.mySimpleUtil.lvcsmsrel.VCLoginHelper;
import Com.Dao.System.ICertificateDao;
import Com.Dao.System.ISysUserDao;
import Com.Entity.System.Certificate;
import Com.Entity.System.SysUser;
import Com.Entity.System.Syscfg;
import Com.Entity.System.Userdiary;
import Com.Entity.System.Userip;
import Com.Entity.System.Useronline;

@Controller
public class SysUserController {
	Logger log = LoggerFactory.getLogger(SysUserController.class);
	@Resource
	private ISysUserDao SysUserDao;
	@Resource
	private ICertificateDao CertificateDao;

	@RequestMapping(value = "/SaveSysUser", method = RequestMethod.POST)
	public String SaveSysUser(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException { // , Mainmenuitem
		String result = "";
		DateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		boolean isAdd = false;
		try {
			String fid = "";
			fid = request.getParameter("fid");
			HashMap<String, String> map = new HashMap<>();
			map.put(request.getParameter("fname"), "用户名称");
			SysUser info = new SysUser();
			//cd2014-10-17 修改用户资料时,密码是不能修改的,只有新增才进行空判断;
			if(fid.isEmpty()){
				map.put(request.getParameter("fpassword"),"密码");
				info.setFpassword(request.getParameter("fpassword"));
				isAdd = true;
			}
			map.put(request.getParameter("fcustomername"),"客户名称");
			map.put(request.getParameter("ftype"), "用户类型");
			DataUtil.verifyNotNull(map);
			
			//cd2014-10-17用户名称不能重复;
//			if(SysUserDao.QueryExistsBySql("select 1 from t_sys_user where fid <> '"+fid+"' and fname = '"+request.getParameter("fname")+"'")){
//				throw new DJException("用户名称不能重复！");
//			}
			//zqz 20150715  用户不能重复
			if(MySimpleToolsZ.isTheRightUserNamePhoneEmailByfid(request.getParameter("fname"), fid,SysUserDao )) throw new DJException("用户名称不能重复");

			//cd2014-10-17用户手机不能重复;
			if(request.getParameter("ftel")!=null && !request.getParameter("ftel").equals("")){
//				if(SysUserDao.QueryExistsBySql("select 1 from t_sys_user where fid <> '"+fid+"' and ftel = '"+request.getParameter("ftel")+"'")){
//					throw new DJException("用户手机不能重复！");
//				}
				//zqz 20150715  用户手机不能重复
				if(MySimpleToolsZ.isTheRightUserNamePhoneEmailByfid(request.getParameter("ftel"), fid,SysUserDao )) throw new DJException("用户手机不能重复！");
			}
			//zqz 20150715  邮箱不能重复
			if(request.getParameter("femail")!=null && !request.getParameter("femail").equals("")){
				if(MySimpleToolsZ.isTheRightUserNamePhoneEmailByfid(request.getParameter("femail"), fid,SysUserDao )) throw new DJException("邮箱不能重复！");
			}
			if(!DataUtil.numberCheck(request.getParameter("ftype"))){
				throw new DJException("用户类型有误！");
			}
			
			info.setFid(fid);
			info.setFname(request.getParameter("fname"));
			info.setFcustomername(request.getParameter("fcustomername"));
			info.setFemail(request.getParameter("femail"));
			info.setFtel(request.getParameter("ftel"));
			info.setFphone(request.getParameter("fphone"));
			info.setFfax(request.getParameter("ffax"));
			info.setFqq(request.getParameter("fqq"));
			info.setFcreatetime(!request.getParameter("fcreatetime").isEmpty() ? f
					.parse(request.getParameter("fcreatetime")) : null);
			// info.setFcustomerid(request.getParameter("fcustomerid")!=null?request.getParameter("fcustomerid"):"");
			// info.setFsupplierid(request.getParameter("fsupplierid")!=null?request.getParameter("fsupplierid"):"");
			info.setFcustomerid(request.getParameter("fcustomerid"));
			info.setFsupplierid(request.getParameter("fsupplierid"));
			
			String fisfilter=request.getParameter("fisfilter");
			if (fisfilter != null&& "1".equals(fisfilter)) {
				info.setFisfilter(1);
			} else {
				info.setFisfilter(0);
			}
			String fisreadonly=request.getParameter("fisreadonly");
			if (fisreadonly != null && "1".equals(fisreadonly)) {
				info.setFisreadonly(1);
			} else {
				info.setFisreadonly(0);
			}
			String ftype=request.getParameter("ftype");
			if (ftype != null && "1".equals(ftype)) {
				info.setFtype(1);
			}else if (ftype != null && "2".equals(ftype)) {
				info.setFtype(2);
			} else if (ftype != null && "3".equals(ftype)){
				info.setFtype(3);
			}
			else {
				info.setFtype(0);
			}
			if(request.getParameter("fimid")!=null && !request.getParameter("fimid").equals("")){
				info.setFimid(Integer.valueOf(request.getParameter("fimid").toString()));
			}
			if(!StringUtils.isEmpty(request.getParameter("feffect"))){
				info.setFeffect(Integer.valueOf(request.getParameter("feffect").toString()));
			}
			if(!StringUtils.isEmpty(request.getParameter("fstate"))){
				info.setFstate(Integer.valueOf(request.getParameter("fstate").toString()));
			}else
			{
				info.setFstate(0);//除通过客户管理的新建，其他为已登陆
			}
			HashMap<String, Object> params = SysUserDao.ExecSave(info);

			if (params.get("success") == Boolean.TRUE) {
				result = "{success:true,msg:'保存成功!'}";
				if(isAdd){
					//增加客户、制造商在线系统数据库的好友列表数据;
					SysUserDao.addDefaultFriendGroup(request,info);
				}
			} else {

			}

		} catch (Exception e) {

			result = "{success:false,msg:'"
			// +
			// (e.getCause().getCause()==null?e.getCause().toString():e.getCause().getCause().toString().replaceAll("'",
			// ""))
					+ e.getMessage() + "'}";// e.getCause().getCause().toString()
		}
		reponse.getWriter().write(result);
		return null;
	}
	
	/*
	 * 提供客户自行修改;
	 */
	@RequestMapping(value = "/SaveCustomerUser", method = RequestMethod.POST)
	public String SaveCustomerUser(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException { // , Mainmenuitem
		try {
			String fid = request.getParameter("fid");
			String fcustomername = request.getParameter("fcustomername");
			String femail = request.getParameter("femail");
			String ftel = request.getParameter("ftel");
			String fphone = request.getParameter("fphone");
			String ffax = request.getParameter("ffax");
			String sql = String.format("update t_sys_user set fcustomername='%s',FEMAIL='%s',FTEL='%s',fphone='%s',ffax='%s' where fid='%s'",fcustomername,femail,ftel,fphone,ffax,fid);
			SysUserDao.ExecBySql(sql);
			reponse.getWriter().write(JsonUtil.result(true,"保存成功！","",""));
		} catch (Exception e) {
			reponse.getWriter().write(JsonUtil.result(false,"保存失败！","",""));
		}
		
		return null;
	}

	// 用户IP保存方法;
	@RequestMapping(value = "/SaveSysUserIP", method = RequestMethod.POST)
	public String SaveSysUserIP(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		String result = "";
		DateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String userid = ((Useronline) request.getSession().getAttribute(
				"Useronline")).getFuserid();
		try {
			request.setCharacterEncoding("utf-8");
			reponse.setCharacterEncoding("utf-8");
			Userip Useripinfo = new Userip();
			Useripinfo.setFid(request.getParameter("fid"));
			Useripinfo.setFuserid(request.getParameter("FUSERID"));
			Useripinfo.setFip(request.getParameter("FIP"));
			Useripinfo
					.setFcreatetime(request.getParameter("FCREATETIME") != null ? f
							.parse(request.getParameter("FCREATETIME"))
							: new Date());
			Useripinfo.setFcreatorid(userid);
			Useripinfo.setFefector(request.getParameter("FEFECTOR"));
			Useripinfo
					.setFefecttime(request.getParameter("FEFECTTIME") != null ? f
							.parse(request.getParameter("FEFECTTIME")) : null);
			HashMap<String, Object> params = SysUserDao
					.ExecSaveUserip(Useripinfo);
			if (params.get("success") == Boolean.TRUE) {
				result = "{success:true,msg:'保存成功!'}";
			} else {

			}
		} catch (Exception e) {

			result = "{success:false,msg:'"
			// +
			// (e.getCause().getCause()==null?e.getCause().toString():e.getCause().getCause().toString().replaceAll("'",
			// ""))
					+ e.getMessage() + "'}";// e.getCause().getCause().toString()
		}
		reponse.getWriter().write(result);
		return null;
	}

	private static String GetSkey() {
		return String.valueOf(Math.PI).substring(0, 16);
	}

	// 解密
	private static String decrypt(String sSrc) throws Exception {
		try {
			// byte[] raw = sKey.getBytes("ASCII");
			byte[] raw = GetSkey().getBytes();
			SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			// IvParameterSpec iv = new IvParameterSpec(ivParameter.getBytes());
			IvParameterSpec iv = new IvParameterSpec(GetSkey().getBytes());
			cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
			byte[] encrypted1 = new BASE64Decoder().decodeBuffer(sSrc);// 先用base64解密
			byte[] original = cipher.doFinal(encrypted1);
			String originalString = new String(original, "utf-8");
			return originalString;
		} catch (Exception ex) {
			return null;
		}
	}

	/*
	 * 调用IM验证
	 * web to IM
	 */
		@RequestMapping("/loginIMcalled")
		public String loginIMcalled(HttpServletRequest request,
				HttpServletResponse reponse) throws IOException { // , Mainmenuitem
			
			String result = "";
			String sessionid=request.getParameter("sessionid").toString();
			
			if("89cf75B9C0F1B6A831C399E269775a28".equals(request.getParameter("API_AUTH_KEY"))){
				List<Useronline> uolist= SysUserDao.QueryByHql("FROM Useronline where fsessionid='"+sessionid+"'");
				if(uolist.size()>0)
				{
					Useronline olduoinfo=uolist.get(0);
					String userid = olduoinfo.getFuserid();
					
					String newsessionid=request.getSession().getId();
					
					String sql = "delete from t_sys_useronline where fsessionid='" + newsessionid + "'";
					SysUserDao.ExecBySql(sql);
					
					Useronline newuoinfo=new Useronline();
					newuoinfo.setFid(SysUserDao.CreateUUid());
					newuoinfo.setFbrowser(olduoinfo.getFbrowser());
					newuoinfo.setFip(olduoinfo.getFip());
					newuoinfo.setFlastoperatetime(olduoinfo.getFlastoperatetime());
					newuoinfo.setFlogintime(olduoinfo.getFlogintime());
					newuoinfo.setFsessionid(newsessionid);
					newuoinfo.setFsystem(olduoinfo.getFsystem());
					newuoinfo.setFuserid(userid);
					newuoinfo.setFusername(olduoinfo.getFusername());
					SysUserDao.saveOrUpdate(newuoinfo);
					request.getSession().setAttribute("Useronline", newuoinfo);
					
					List<SysUser> slist = SysUserDao.QueryByHql(" FROM SysUser where fid='"+userid+"' ");
					SysUser userinfo = slist.get(0);
					
					result = JsonUtil.result(true, "", "", "{\"username\":\""
							+ olduoinfo.getFusername() + "\",\"sessionid\":\""
							+ newsessionid + "\",\"IMid\":\""+userinfo.getFimid()+"\"}");
					
				}else{
					result = "{\"success\":false,\"msg\":\"session无效！\"}";
				}
			}
			
			reponse.getWriter().write(result);
			
			return null;
		}
		
	/*
	 * 单点登录验证
	 * IM to web
	 */
	@RequestMapping("/singlelogin")
	public String singlelogin(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException { // , Mainmenuitem
		String sessionid=request.getParameter("sessionid").toString();
		String newsessionid=request.getSession().getId();
		List<Useronline> uolist= SysUserDao.QueryByHql("FROM Useronline where fsessionid='"+sessionid+"'");
		if(uolist.size()>0)
		{
			Useronline olduoinfo=uolist.get(0);
			String userid = olduoinfo.getFuserid();
			
			String sql = "delete from t_sys_useronline where fsessionid='" + sessionid + "'";
			SysUserDao.ExecBySql(sql);
			ServerContext.delUseronline(sessionid);
				
			Useronline newuoinfo=new Useronline();
			newuoinfo.setFid(SysUserDao.CreateUUid());
			newuoinfo.setFbrowser(olduoinfo.getFbrowser());
			newuoinfo.setFip(olduoinfo.getFip());
			newuoinfo.setFlastoperatetime(olduoinfo.getFlastoperatetime());
			newuoinfo.setFlogintime(olduoinfo.getFlogintime());
			newuoinfo.setFsessionid(newsessionid);
			newuoinfo.setFsystem(olduoinfo.getFsystem());
			newuoinfo.setFuserid(userid);
			newuoinfo.setFusername(olduoinfo.getFusername());
			SysUserDao.saveOrUpdate(newuoinfo);
			request.getSession().setAttribute("Useronline", newuoinfo);
			
			//添加进session缓存
			ServerContext.setUseronline(newsessionid, newuoinfo);
			
			String path=request.getContextPath();
			reponse.sendRedirect(path+"/index.do");
			return null;
		}
		else
		{
			String path=request.getContextPath();
			reponse.sendRedirect(path+"/login.do");
			return null;
		}
	}
		
	//根据sessionid获取用户名
	@RequestMapping("/getusernamebysession")
	public String getusernamebysession(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException { // , Mainmenuitem
		String sessionid = request.getSession().getId();
		String result = "";
		String sql  = " select fusername FROM t_sys_useronline where fsessionid='"+sessionid+"' ";
		List<HashMap<String, Object>> sList = SysUserDao.QueryBySql(sql);
		if(sList.size()>0)
		{
			result = "{success:true,username:'" + sList.get(0).get("fusername").toString()
					+ "'}";
		}
		else
		{
			result = "{success:false}";
		}
		reponse.setCharacterEncoding("utf-8");
		reponse.getWriter().write(result);
		return null;
	}
	
	@RequestMapping("/logonAction")
	public String logonAction(HttpServletRequest request,
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
			
			
			/*
			 * 验证码校验
			 * 在线沟通IM 或 系统解锁  跳过验证码校验
			 */
			checkValidateCode(userinfo,request);
			/*if(!((request.getParameter("ftype")!=null && request.getParameter("ftype").equals("IM")) 
					|| (request.getParameter("type") != null && request.getParameter("type").equals("unlock")))){
				verifyThereAreManNotMachine(session);
			}
			
			RandomValidateCode rc = RandomValidateCode.gainRandomValidateCode(session);*/
			
			if (userinfo != null) {
				
				//首次登陆短信验证(新用户)
//				String sql = "select 1 from t_sys_user where fstate=1 and fname='"+request.getParameter("username")+"'";
//				if(SysUserDao.QueryExistsBySql(sql)){
//					throw new DJException("首次登陆系统需验证手机！");
//				}
				userdiaryinfo.setFuserid(userinfo.getFid()); 
				//增加安全控件配置功能    BY  CC  2014-07-25 start
				boolean needL=false;
				List<Syscfg> syscfgcls=SysUserDao.QueryByHql(" from Syscfg where fuser = '"+userinfo.getFid()+"' and fkey='safeWidget' ");
				if(syscfgcls.size()>0)
				{
					if(syscfgcls.get(0).getFvalue().equals("1"))
					{
						needL=true;
					}
				}
				//增加安全控件配置功能    BY  CC  2014-07-25 end
				// 验证机器码
				if (userinfo.getFid().equals(
						"3f6112db-a952-11e2-90fc-60a44c5bbef3") || !needL) {

				} else
					//if(request.getParameter("type")==null)
				{
					if (request.getParameter("L") == null
							|| request.getParameter("L").length() == 0) {
						throw new DJException("序列号错误");
					} else {
						String[] DongJingL = decrypt(
								request.getParameter("L").replace("返回", "\r")
										.replace("换行", "\n")
										.replace("制表", "\t")
										.replace("退格", "\b")).split(",");
						if(DongJingL[1].contains("错误"))
						{
							throw new DJException("参数错误");
						}
						List<Certificate> certlist = CertificateDao
								.QueryByHql(" FROM Certificate where fuserid='"
										+ userinfo.getFid() + "' and fserial='"
										+ DongJingL[1] + "'");
						if (certlist.size() > 0) {
							Certificate certinfo = certlist.get(0);
							if (certinfo.getFaudited() == 0) {
								throw new DJException("申请还在审核中,请稍等!也可联系客服：0577-55575282");
							}
							certinfo.setFlastlogintime(new Date());
							CertificateDao.saveOrUpdate(certinfo);
						} else {
							if (request.getParameter("isapply") != null
									&& request.getParameter("isapply").equals(
											"1")) {
								// 保存验证信息
								Certificate certinfo = new Certificate();
								certinfo.setFcreatetime(new Date());
								certinfo.setFid(CertificateDao.CreateUUid());
								certinfo.setFserial(DongJingL[1]);
								certinfo.setFuserid(userinfo.getFid());
								certinfo.setFusername(userinfo.getFname());
								CertificateDao.saveOrUpdate(certinfo);
								throw new DJException(
										"申请成功，请等待客服审核!也可联系客服：0577-55575282");
							} else {
								throw new DJException("该用户不允许在这台电脑登录");
							}
						}
					}
				}
				// 生成用户在线信息
				Useronline info = new Useronline();
				info.setFid(SysUserDao.CreateUUid());
				info.setFuserid(userinfo.getFid());
				info.setFlogintime(new Date());
				info.setFlastoperatetime(new Date());
				info.setFusername(userinfo.getFname());
//				info.setFusername(userdiaryinfo.getFusername());
				String sessionid = request.getSession().getId();
				info.setFsessionid(sessionid);
				info.setFip(request.getRemoteAddr());

				String headinfo = request.getHeader("User-Agent").toUpperCase();
				//苹果系统没有带括号，兼容苹果系统修改     BY CC  2015-05-08 start
				if(headinfo.indexOf("(")>=0)
				{
					// StringTokenizer st = new StringTokenizer(headinfo,";");
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
				//苹果系统没有带括号，兼容苹果系统修改     BY CC  2015-05-08 end
				SysUserDao.saveOrUpdate(info);
				String redirectUrl = "";
				if(userinfo.getFtype()!=0 
//						||  SysUserDao.QueryExistsBySql("select fid from t_bd_usersupplier where fuserid = '''"+userinfo.getFid()+"'")
					){
					//2015-10-09新增在线用户缓存信息
					ServerContext.setUseronline(sessionid, info);
				}else{//如果只有客户角色则直接跳转深圳平台;
					redirectUrl = "/vdispatcher/cpsvmiDispathcher.net?url=&sessionid="+sessionid;
//					redirectUrl = "/cps-vmi/vdispatcher/cpsvmiDispathcher.net?url=&sessionid="+sessionid;
				}
				
				//移动端检验是否是纸箱、纸板用户
				String usertype="";
				if(request.getParameter("version")!=null){
					usertype=",\"usertype\":\""+SysUserDao.getUserTypeInfo(info.getFuserid())+"\"";
				}
				result = JsonUtil.result(true, "", "", "{\"username\":\""
						+ info.getFusername() + "\",\"redirectUrl\":\"" + redirectUrl + "\",\"sessionid\":\""
						+ request.getSession().getId() + "\",\"IMid\":\""+userinfo.getFimid()+"\""+usertype+"}");
				request.getSession().setAttribute("Useronline", info);
				userdiaryinfo.setFremark("登录成功");
				
//				 rc.setVerified(true);
				
			} else {
				userdiaryinfo.setFremark("登录失败:用户名或密码错误");
				result = "{\"success\":false,\"msg\":\"用户名或密码错误！\"}";
				
//			    rc.setVerified(false);
			}

		} catch (Exception e) {
			userdiaryinfo.setFremark("登录失败:"+e.getMessage());
			result = "{\"success\":false,\"msg\":'"
			// +
			// (e.getCause().getCause()==null?e.getCause().toString():e.getCause().getCause().toString().replaceAll("'",
			// ""))
					+ e.getMessage() + "'}";// e.getCause().getCause().toString()
		}
		String sql = "update t_sys_user set fstate=0 where fname='"+request.getParameter("username")+"'";
		SysUserDao.ExecBySql(sql);
		
		userdiaryinfo.setFsessionid(request.getSession().getId());
		userdiaryinfo.setFlogintime(new Date());
		userdiaryinfo.setFlastoperatetime(new Date());
		SysUserDao.saveOrUpdate(userdiaryinfo);
//		reponse.addCookie(new Cookie("test", "test1"));
		reponse.getWriter().write(result);
		return null;
	}
	
/*
 * 跳转深圳客户端平台页面;
 */
@RequestMapping("/logonNewAction")
public String logonNewAction(HttpServletRequest request,
		HttpServletResponse reponse) throws IOException {
	
	String url=request.getParameter("url").toString();
	String sessionid=request.getSession().getId();
	
	String redirectUrl = "/vdispatcher/cpsvmiDispathcher.net?url="+url+"&sessionid="+sessionid;
//	String redirectUrl = "/cps-vmi/vdispatcher/cpsvmiDispathcher.net?url="+url+"&sessionid="+sessionid;
	//删除session缓存
	ServerContext.delUseronline(sessionid);
	String result = JsonUtil.result(true, "", "", "{\"redirectUrl\":\"" + redirectUrl +"\"}");
	reponse.getWriter().write(result);
	return null;
}

	private void checkValidateCode(SysUser userinfo, HttpServletRequest request) {
//		在线沟通IM 或 系统解锁  跳过验证码校验
		if("IM".equals(request.getParameter("ftype")) || "unlock".equals(request.getParameter("type"))||request.getParameter("version")!=null){
			return;
		}
		HttpSession session = request.getSession();
		ValidateCode validateCode = (ValidateCode) session.getAttribute(ValidateCode.RANDOMCODEKEY);
		if(validateCode == null){
			validateCode = new ValidateCode();
			session.setAttribute(ValidateCode.RANDOMCODEKEY, validateCode);
		}
		if(userinfo != null){	//帐号密码验证成功
			if(validateCode.getCode()!=null){	//存在验证码
				if(validateCode.getCode().equalsIgnoreCase((request.getParameter("code")))){	//验证成功
					validateCode.clearCode();
				}else{
					validateCode.reclocking();
					throw new DJException("验证码错误！");
				}
			}
		}else{
			validateCode.addCount();
		}
	}

	
	@RequestMapping("/CheckSession")
	public String CheckSession(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		String sessionid = request.getParameter("sessionid");
		params params = SysUserDao.QueryCheckSessionid(request);
		String result = "";
		if (params.get("success") == Boolean.TRUE) {
			result = "{success:true}";
		} else {
			result = "{success:false,msg:'" + params.get("msg").toString()
					+ "'}";
		}
		reponse.setCharacterEncoding("utf-8");
		reponse.getWriter().write(result);
		return null;

	}

	@RequestMapping("/GetUserList")
	public String GetUserList(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		String result = "";
		String sql = "select fid,fname,feffect,fcustomername,femail,ftel,fcreatetime,fisfilter  FROM t_sys_user ";
		ListResult Result = new ListResult();
		try {
			Result = SysUserDao.QueryFilterList(sql, request);
			reponse.setCharacterEncoding("utf-8");
			reponse.getWriter().write(JsonUtil.result(true, "", Result));

		} catch (DJException e) {
			reponse.setCharacterEncoding("utf-8");
			reponse.getWriter().write(
					JsonUtil.result(false, e.getMessage(), "", ""));

		}

		return null;

	}
	/**
	 * 查询用户列表界面
	 * @param request
	 * @param reponse
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("getSysUserList")
	public String getSysUserList(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		String sql = "select u.fqq,u.fid,u.fname,fpassword,feffect,fcustomername,u.femail,u.ftel,u.fcreatetime,fisfilter,fisreadonly,c.fname fcustomerid  FROM t_sys_user u left join t_bd_customer c on u.fcustomerid = c.fid ";
		ListResult Result = new ListResult();
		try {
			Result = SysUserDao.QueryFilterList(sql, request);
			reponse.setCharacterEncoding("utf-8");
			reponse.getWriter().write(JsonUtil.result(true, "", Result));

		} catch (DJException e) {
			reponse.setCharacterEncoding("utf-8");
			reponse.getWriter().write(
					JsonUtil.result(false, e.getMessage(), "", ""));

		}

		return null;

	}
	// 用户IP列表查询方法;
	@RequestMapping("/GetUserIPList")
	public String GetUserIPList(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		String result = "";
		String sql = "SELECT u.fid,u.FUSERID,u1.fname fuser,u.FIP,u.FCREATORID,u2.fname fcreator,u.FCREATETIME,u.FEFECTOR,ifnull(u3.fname,'') efector,ifnull(u.FEFECTTIME,'') FEFECTTIME,u.FEFECTED FROM t_sys_userip u left join t_sys_user u1 on u1.fid=u.FUSERID left join t_sys_user u2 on u2.fid=u.fcreatorid left join t_sys_user u3 on u3.fid=u.FEFECTOR ";
		ListResult Result = new ListResult();
		try {
			Result = SysUserDao.QueryFilterList(sql, request);
			reponse.setCharacterEncoding("utf-8");
			reponse.getWriter().write(JsonUtil.result(true, "", Result));

		} catch (DJException e) {
			reponse.setCharacterEncoding("utf-8");
			reponse.getWriter().write(
					JsonUtil.result(false, e.getMessage(), "", ""));

		}

		return null;

	}

	// 用户IP编辑界面查询方法;
	@RequestMapping("/GetUserIPInfo")
	public String GetUserIPInfo(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		reponse.setCharacterEncoding("utf-8");
		try {
			String fid = request.getParameter("fid");
			String sql = "SELECT u.fid,u.FUSERID FUSERID_fid,u1.fname FUSERID_fname,u.FIP,u.FCREATORID,u2.fname fcreator,u.FCREATETIME,u.FEFECTOR,ifnull(u3.fname,'') efector,ifnull(u.FEFECTTIME,'') FEFECTTIME,u.FEFECTED FROM t_sys_userip u left join t_sys_user u1 on u1.fid=u.FUSERID left join t_sys_user u2 on u2.fid=u.fcreatorid left join t_sys_user u3 on u3.fid=u.FEFECTOR "
					+ "where u.fid =:fid ";
			params p = new params();
			p.put("fid", fid);
			List<HashMap<String, Object>> result = SysUserDao
					.QueryBySql(sql, p);
			reponse.getWriter().write(JsonUtil.result(true, "", "", result));
		} catch (Exception e) {
			reponse.getWriter().write(
					JsonUtil.result(false, e.getMessage(), "", ""));
		}

		return null;

	}

	@RequestMapping("/GetUserInfo")
	public String GetUserInfo(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		// String fid = request.getParameter("fid");
		// String hql = " FROM SysUser where fid ='" + fid + "'";
		// List<SysUser> sList = SysUserDao.QuerySysUsercls(hql);
		// String result = "";
		// result = "{success:true,data:" + GetJSON(sList) + "}";
		// reponse.setCharacterEncoding("utf-8");
		// reponse.getWriter().write(result);
		reponse.setCharacterEncoding("utf-8");
		try {
			String fid = request.getParameter("fid");
			String sql = "SELECT u.fqq, u.feffect,u.fstate,u.fphone,u.ffax,u.fimid,u.fid,u.fname,u.fcustomerid as fcustomerid_fid,ifnull(c.fname,'') as fcustomerid_fname,"
					+ "u.fsupplierid as fsupplierid_fid,ifnull(s.fname,'') as fsupplierid_fname,u.feffect,u.fcustomername,u.femail,u.ftel,u.fcreatetime,u.fisfilter,u.fisreadonly,u.ftype "
					+ "FROM t_sys_user  u left join t_bd_customer c on c.fid= u.fcustomerid left join t_sys_supplier s on s.fid=u.fsupplierid"
					+ " where u.fid =:fid";
			params p = new params();
			p.put("fid", fid);
			List<HashMap<String, Object>> result = SysUserDao
					.QueryBySql(sql, p);
			reponse.getWriter().write(JsonUtil.result(true, "", "", result));
		} catch (Exception e) {
			reponse.getWriter().write(
					JsonUtil.result(false, e.getMessage(), "", ""));
		}

		return null;

	}

	@RequestMapping("/EffectUserList")
	public String EffectUserList(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		String result = "";
		String fidcls = request.getParameter("fidcls");
		fidcls = "('" + fidcls.replace(",", "','") + "')";
//		int feffect = Integer.parseInt(request.getParameter("feffect"));
		try {
			String effect = request.getParameter("feffect");
			if(!DataUtil.numberCheck(effect)){
				throw new DJException("参数传递有误！");
			}
			int feffect = Integer.parseInt(effect);
			String hql = "Update SysUser set feffect=" + feffect
					+ " where fid in " + fidcls;
			SysUserDao.ExecByHql(hql);
			result = "{success:true,msg:'" + (feffect == 0 ? "启用" : "禁用")
					+ "成功!'}";
			reponse.setCharacterEncoding("utf-8");
		} catch (Exception e) {
			result = "{success:false,msg:'" + e.toString().replaceAll("'", "")
					+ "'}";// e.getCause().getCause().toString()
		}
		reponse.getWriter().write(result);

		return null;

	}

	// 用户IP启用禁用方法;
	@RequestMapping("/EffectUserIPList")
	public String EffectUserIPList(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		String result = "";
		String fidcls = request.getParameter("fidcls");
		fidcls = "('" + fidcls.replace(",", "','") + "')";
//		int feffect = Integer.parseInt(request.getParameter("feffect"));
		String userid = ((Useronline) request.getSession().getAttribute(
				"Useronline")).getFuserid();
		try {
			String effect = request.getParameter("feffect");
			if(!DataUtil.numberCheck(effect)){
				throw new DJException("参数传递有误！");
			}
			int feffect = Integer.parseInt(effect);
			String sql = "";
			if (feffect == 0) {
				sql = "Update t_sys_Userip set fefected='" + feffect
						+ "',fefecttime=null ,fefector='' where fid in "
						+ fidcls;
			} else {
				sql = "Update t_sys_Userip set fefected='" + feffect
						+ "',fefecttime=CURRENT_TIMESTAMP,fefector='" + userid
						+ "' where fid in " + fidcls;
			}

			SysUserDao.ExecBySql(sql);
			result = "{success:true,msg:'" + (feffect == 1 ? "启用" : "禁用")
					+ "成功!'}";
			reponse.setCharacterEncoding("utf-8");
		} catch (Exception e) {
			result = "{success:false,msg:'" + e.toString().replaceAll("'", "")
					+ "'}";// e.getCause().getCause().toString()
		}
		reponse.getWriter().write(result);

		return null;

	}

	@RequestMapping("/ChangeSysUserpwd")
	public String ChangeSysUserpwd(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		String result = "";
		reponse.setCharacterEncoding("utf-8");
		try {
			SysUserDao.ExecChangePWD(request);
			reponse.setCharacterEncoding("utf-8");
			result = "{\"success\":true,\"msg\":\"密码设置成功!\"}";
		} catch (Exception e) {
			result = JsonUtil.result(false, e.getMessage(), "", "");
		}
		reponse.getWriter().write(result);

		return null;

	}

	@RequestMapping("/SaveSysUserpwd")
	public String SaveSysUserpwd(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		String result = "";
		String fid = request.getParameter("fid");
		String fpassword = request.getParameter("fpassword");
		try {
			DataUtil.verifyNotNull(fpassword, "密码");
			String sql = "Update T_SYS_USER set fpassword='" + fpassword
					+ "' where fid ='" + fid + "'";
			SysUserDao.ExecBySql(sql);
			reponse.setCharacterEncoding("utf-8");
			result = "{success:true,msg:'密码设置成功!'}";
		} catch (Exception e) {
			result = "{success:false,msg:'" + e.toString().replaceAll("'", "")
					+ "'}";// e.getCause().getCause().toString()
		}
		reponse.getWriter().write(result);

		return null;

	}

	@RequestMapping("/DelUserList")
	public String DelUserList(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		String result = "";
		String fidcls = request.getParameter("fidcls");
		fidcls = "('" + fidcls.replace(",", "','") + "')";
		try {
			String hql = " FROM SysUser where feffect=1 and fid in " + fidcls;
			List<SysUser> sList = SysUserDao.QuerySysUsercls(hql);
			if (sList.size() > 0) {
				result = "{success:false,msg:'不能删除已经启用的用户!'}";
			} else {
				hql = "Delete FROM SysUser where fid in " + fidcls;
				SysUserDao.ExecByHql(hql);
				result = "{success:true,msg:'删除成功!'}";
			}
			reponse.setCharacterEncoding("utf-8");
		} catch (Exception e) {
			result = "{success:false,msg:'" + e.toString().replaceAll("'", "")
					+ "'}";// e.getCause().getCause().toString()
			log.error("DelUserList error", e);
		}
		reponse.getWriter().write(result);

		return null;

	}

	// 用户IP删除方法;
	@RequestMapping("/DelUserIPList")
	public String DelUserIPList(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		String result = "";
		String fidcls = request.getParameter("fidcls");
		fidcls = "('" + fidcls.replace(",", "','") + "')";
		try {
			String hql = " FROM Userip where fefected=1 and fid in " + fidcls;
			List<SysUser> sList = SysUserDao.QuerySysUsercls(hql);
			if (sList.size() > 0) {
				result = "{success:false,msg:'不能删除已经启用的用户!'}";
			} else {
				hql = "Delete FROM Userip where fid in " + fidcls;
				SysUserDao.ExecByHql(hql);
				result = "{success:true,msg:'删除成功!'}";
			}
			reponse.setCharacterEncoding("utf-8");
		} catch (Exception e) {
			result = "{success:false,msg:'" + e.toString().replaceAll("'", "")
					+ "'}";// e.getCause().getCause().toString()
			log.error("DelUserIPList error", e);
		}
		reponse.getWriter().write(result);

		return null;

	}

	private String GetJSON(List<SysUser> sList) {
		DateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String result = "";
		int index = 0;
		for (SysUser info : sList) {
			if (index > 0) {
				result = result + ",";
			}
			result = result + "{fid:'" + info.getFid() + "',fname:'"
					+ info.getFname() + "',fpassword:'" + info.getFpassword()
					+ "',feffect:'" + (info.getFeffect() == 1 ? "是" : "否")
					+ "',fcustomername:'" + info.getFcustomername()
					+ "',femail:'" + info.getFemail() + "',ftel:'"
					+ info.getFtel() + "',fcreatetime:'"
					+ f.format(info.getFcreatetime()) + "'}";
			index++;
		}
		return result;

	}

	/**
	 * insert into link table user customer
	 * 
	 * @param request
	 * @param reponse
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/AddUserSupplier")
	public String AddUserSupplier(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		try
		{
			String sql = "";
			String fidcls = request.getParameter("fidcls");
			fidcls="('"+fidcls.replace(",","','")+"')";
			String userID = request.getParameter("myobjectid");
			if(!DataUtil.DataCheck(request, "t_sys_user", null, null, SysUserDao, userID)){
				throw new DJException("参数传递错误，用户ID不存在！");
			}
			String[] tArrayofFid = DataUtil.InStrToArray(fidcls);
			for(String s:tArrayofFid){
				if(!DataUtil.DataCheck(request, "t_sys_supplier", null, null, SysUserDao, s)){
					throw new DJException("参数传递错误，制造商ID不存在！");
				}
			}
			SysUserDao.saveUserSupplier(userID, tArrayofFid);
			reponse.getWriter().write(JsonUtil.result(true, "分配成功", "",""));
		}
		catch(Exception e)
		{
			reponse.getWriter().write(JsonUtil.result(false, e.getMessage(), "",""));
		}
		
		return null;
	}

	/**
	 * deleteUserCustomer table user customer
	 * 
	 * @param request
	 * @param reponse
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/DelUserSupplier")
	public String DelUserSupplier(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		reponse.setCharacterEncoding("utf-8");
		try {
			String sql = "delete from t_bd_usersupplier where FUSERID= :userid  and fsupplierid in ";
			String fidcls = request.getParameter("fidcls");
			fidcls = "('" + fidcls.replace(",", "','") + "')";
			String userID = request.getParameter("myobjectid");
			sql = sql + fidcls;
			params paramsT = new params();
			paramsT.setString("userid", userID);
			SysUserDao.ExecBySql(sql, paramsT);
			reponse.getWriter().write(JsonUtil.result(true, "取消成功", "", ""));
		} catch (Exception e) {
			reponse.getWriter().write(
					JsonUtil.result(false, e.getMessage(), "", ""));
		}
		return null;
	}

	/**
	 * get Customer by id
	 * 
	 * @param request
	 * @param reponse
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/GetUserSupplierList")
	public String GetUserSupplierList(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		String sql = "SELECT c.FNAME as fname, c.fnumber as fnumber, c.FID as fid ,c.fcreatetime as fcreatetime,c.fusedstatus fusedstatus FROM t_bd_usersupplier l, t_sys_supplier c where l.fsupplierid =  c.FID ";
		ListResult result;

		reponse.setCharacterEncoding("utf-8");
		try {
			result = this.SysUserDao.QueryFilterList(sql, request);
			reponse.getWriter().write(JsonUtil.result(true, "", result));
		} catch (DJException e) {
			reponse.getWriter().write(
					JsonUtil.result(false, e.getMessage(), "", ""));
		}

		return null;

	}

	@RequestMapping(value = "/sysuseroexcel")
	public String sysuseroexcel(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {

		try {
			String sql = "select fid,fname 用户名,feffect 是否启用,fcustomername 客户名称 ,femail 电子邮件,ftel 电话,fcreatetime 创建时间  FROM t_sys_user ";
			ListResult result = SysUserDao.QueryFilterList(sql, request);
			ExcelUtil.toexcel(reponse, result);
			// InputStream inputStream = new
			// FileInputStream(ExcelUtil.toexcel(result));
			// OutputStream os = reponse.getOutputStream();
			// byte[] b = new byte[1024];
			// int length;
			// while ((length = inputStream.read(b)) > 0) {
			// os.write(b, 0, length);
			// }
			// inputStream.close();
		} catch (DJException e) {
			reponse.getWriter().write(
					JsonUtil.result(false, e.getMessage(), "", ""));
		}

		return null;

	}

	@RequestMapping("/AddUserCustomer")
	public String AddUserCustomer(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		try
		{
			String sql = "";
			String fidcls = request.getParameter("fidcls");
			fidcls="('"+fidcls.replace(",","','")+"')";
			String userID = request.getParameter("myobjectid");
			if(!DataUtil.DataCheck(request, "t_sys_user", null, null, SysUserDao, userID)){
				throw new DJException("参数传递错误，用户ID不存在！");
			}
			String[] tArrayofFid = DataUtil.InStrToArray(fidcls);
			for(String s:tArrayofFid){
				if(!DataUtil.DataCheck(request, "t_bd_customer", null, null, SysUserDao, s)){
					throw new DJException("参数传递错误，客户ID不存在！");
				}
			}
			SysUserDao.saveUserCustomer(userID, tArrayofFid);
			reponse.getWriter().write(JsonUtil.result(true, "分配成功", "",""));
		}
		catch(Exception e)
		{
			reponse.getWriter().write(JsonUtil.result(false, e.getMessage(), "",""));
		}
		
		return null;
	}

	/**
	 * deleteUserCustomer table user customer
	 * 
	 * @param request
	 * @param reponse
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/DelUserCustomer")
	public String DelUserCustomer(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		reponse.setCharacterEncoding("utf-8");
		try
		{
			String fidcls = request.getParameter("fidcls");
			fidcls="('"+fidcls.replace(",","','")+"')";
			String userID = request.getParameter("myobjectid");
			SysUserDao.DelUserCustomer(fidcls, userID);
			reponse.getWriter().write(JsonUtil.result(true, "取消成功","",""));
		}
		catch(Exception e)
		{
			reponse.getWriter().write(JsonUtil.result(false,e.getMessage(),"",""));
		}
		return null;
	}

	/**
	 * get Customer by id
	 * 
	 * @param request
	 * @param reponse
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/GetUserCustomerList")
	public String getCustomerListByUserIdNow(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {

		String sql = "SELECT c.FNAME as fname, c.FNUMBER as fnumber, c.FID as fid FROM t_bd_usercustomer l, t_bd_customer c where l.FCUSTOMERID =  c.FID ";
		ListResult result;

		reponse.setCharacterEncoding("utf-8");
		try {
			result = this.SysUserDao.QueryFilterList(sql, request);
			reponse.getWriter().write(JsonUtil.result(true, "", result));
		} catch (DJException e) {
			reponse.getWriter().write(
					JsonUtil.result(false, e.getMessage(), "", ""));
		}

		return null;

	}

	@RequestMapping("/AddUserRole")
	public String AddUserRole(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		try
		{
			String sql = "";
			String fidcls = request.getParameter("fidcls");
			fidcls="('"+fidcls.replace(",","','")+"')";
			String userID = request.getParameter("myobjectid");
			if(!DataUtil.DataCheck(request, "t_sys_user", null, null, SysUserDao, userID)){
				throw new DJException("参数传递错误，用户ID不存在！");
			}
			String[] tArrayofFid = DataUtil.InStrToArray(fidcls);
			for(String s:tArrayofFid){
				if(!DataUtil.DataCheck(request, "t_sys_role", null, null, SysUserDao, s)){
					throw new DJException("参数传递错误，角色ID不存在！");
				}
			}
			SysUserDao.saveUserRole(userID, tArrayofFid);
			reponse.getWriter().write(JsonUtil.result(true, "分配成功", "",""));
		}
		catch(Exception e)
		{
			reponse.getWriter().write(JsonUtil.result(false, e.getMessage(), "",""));
		}
		
		return null;
	}

	@RequestMapping("/DelUserRole")
	public String DelUserRole(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		reponse.setCharacterEncoding("utf-8");
		try {
			String sql = "delete from t_sys_userrole where FUSERID=:userid  and froleid in ";
			String fidcls = request.getParameter("fidcls");
			fidcls = "('" + fidcls.replace(",", "','") + "')";
			String userID = request.getParameter("myobjectid");
			sql = sql + fidcls;
			params paramsT = new params();
			paramsT.setString("userid", userID);
			SysUserDao.ExecBySql(sql, paramsT);
			reponse.getWriter().write(JsonUtil.result(true, "取消成功", "", ""));
		} catch (Exception e) {
			reponse.getWriter().write(
					JsonUtil.result(false, e.getMessage(), "", ""));
		}
		return null;
	}

	@RequestMapping("/GetUserRoleList")
	public String GetUserRoleList(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		String sql = "SELECT c.FNAME as fname, c.FNUMBER as fnumber, c.FID as fid ,c.fdescription  as fdescription FROM t_sys_userrole l, t_sys_role c where l.froleid =  c.FID ";
		ListResult result;
		reponse.setCharacterEncoding("utf-8");
		try {
			result = this.SysUserDao.QueryFilterList(sql, request);
			reponse.getWriter().write(JsonUtil.result(true, "", result));
		} catch (DJException e) {
			reponse.getWriter().write(
					JsonUtil.result(false, e.getMessage(), "", ""));
		}

		return null;

	}

	/**
	 * 在线用户展示
	 * 
	 * @param request
	 * @param reponse
	 * @return
	 * @throws IOException
	 * 
	 * @glossographer ZJZ
	 * @date 2013-11-15 下午4:37:02
	 */
	@RequestMapping("/getUserOnlineList")
	public String getUserOnlineList(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		String sql = "SELECT fid, fuserid, flogintime, flastoperatetime, fusername,fip,fsystem,fbrowser FROM t_sys_useronline ";
		ListResult result;
		reponse.setCharacterEncoding("utf-8");
		try {
			/**
			 * 这里也有实现过滤操作
			 */
			result = this.SysUserDao.QueryFilterList(sql, request);
			reponse.getWriter().write(JsonUtil.result(true, "", result));
		} catch (DJException e) {
			reponse.getWriter().write(
					JsonUtil.result(false, e.getMessage(), "", ""));
		}

		return null;
	}
	
	@RequestMapping("/CertUnAudited")
	public String CertUnAudited(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		reponse.setCharacterEncoding("utf-8");
		try {
			String fidcls = request.getParameter("fidcls");
			if(fidcls.isEmpty())
			{
				throw new DJException("至少选中一行记录!");
			}
			Useronline Useronlineinfo=(Useronline) request.getSession().getAttribute(
					"Useronline");
			fidcls = "('" + fidcls.replace(",", "','") + "')";
			String sql="update t_sys_certificate set faudited=0,fauditor='',fauditedtime=null,fauditorid='' where FID in "+fidcls;
			SysUserDao.ExecBySql(sql);
			reponse.getWriter().write(
					JsonUtil.result(true,"反审核成功", "", ""));
		} catch (DJException e) {
			reponse.getWriter().write(
					JsonUtil.result(false, e.getMessage(), "", ""));
		}

		return null;
	}
	@RequestMapping("/CertAudited")
	public String CertAudited(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		reponse.setCharacterEncoding("utf-8");
		try {
			String fidcls = request.getParameter("fidcls");
			if(fidcls.isEmpty())
			{
				throw new DJException("至少选中一行记录!");
			}
			Useronline Useronlineinfo=(Useronline) request.getSession().getAttribute(
					"Useronline");
			fidcls = "('" + fidcls.replace(",", "','") + "')";
			String sql="update t_sys_certificate set faudited=1,fauditor='"+Useronlineinfo.getFusername()+"',fauditedtime=NOW(),fauditorid='"+Useronlineinfo.getFuserid()+"' where FID in "+fidcls;
			SysUserDao.ExecBySql(sql);
			reponse.getWriter().write(
					JsonUtil.result(true,"审核成功", "", ""));
		} catch (DJException e) {
			reponse.getWriter().write(
					JsonUtil.result(false, e.getMessage(), "", ""));
		}
		
		return null;
	}
	@RequestMapping("/getCertList")
	public String getCertList(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		String sql = "select fid,fusername,fcreatetime,fauditor,fauditedtime,fserial,flastlogintime,fremark,faudited from t_sys_certificate ";
		ListResult result;
		reponse.setCharacterEncoding("utf-8");
		try {
			/**
			 * 这里也有实现过滤操作
			 */
			result = this.SysUserDao.QueryFilterList(sql, request);
			reponse.getWriter().write(JsonUtil.result(true, "", result));
		} catch (DJException e) {
			reponse.getWriter().write(
					JsonUtil.result(false, e.getMessage(), "", ""));
		}

		return null;
	}

	/**
	 * 
	 * 在线用户删除
	 * 
	 * @param request
	 * @param reponse
	 * @return
	 * @throws IOException
	 * 
	 * @glossographer ZJZ
	 * @date 2013-11-15 下午4:37:20
	 */
	@RequestMapping("/kickUserOnline")
	public String kickUserOnline(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		reponse.setCharacterEncoding("utf-8");
		try {
			String sql = "delete from t_sys_useronline where fid in ";

			String fidcls = request.getParameter("fidcls");
			fidcls = "('" + fidcls.replace(",", "','") + "')";

			sql = sql + fidcls;

			SysUserDao.ExecBySql(sql);
			
			//2015-10-09同时删除登录缓存记录
			ServerContext.delUseronline(request.getSession().getId());

			reponse.getWriter().write(JsonUtil.result(true, "踢出成功", "", ""));
		} catch (Exception e) {
			reponse.getWriter().write(
					JsonUtil.result(false, e.getMessage(), "", ""));
		}
		return null;
	}
	
	@RequestMapping("/GetUserLoginLog")
	public String GetUserLoginLog(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		String sdate = request.getParameter("sdate");
		String ctype = request.getParameter("ctype");
		String lie="",join="",sql = "";
		if("cstmr".equals(ctype)){
			lie+=",count(distinct urc.fcustomerid) times";
			join="left join t_bd_usercustomer urc on urc.fuserid = ud.fuserid ";
		}else{
			lie+=",count(1) times";
		}
		ListResult result;
		reponse.setCharacterEncoding("utf-8");

		switch (sdate) {
		case "day":
			sql = "select * from (select left(ud.flogintime,10) date"+lie+" from t_sys_userdiary ud "+join
					+ "where ud.flogintime>=date_sub(left(now(),10),interval 1 month)and exists "
					+ "(select fuserid from t_bd_usercustomer urc where urc.fuserid=ud.fuserid "
					+ "group by fuserid having count(1)=1)"
					+ "group by left(ud.flogintime,10)"
					+ "order by left(ud.flogintime,10)) a where 1=1 ";
			break;
		case "week":
			sql = "select * from (select CONCAT(left(ud.flogintime,4),'.',weekofyear(ud.flogintime),'周') date"+lie+" from t_sys_userdiary ud "
					+join+ "where ud.flogintime>=date_sub(left(now(),10),interval 6 month)and exists "
					+ "(select fuserid from t_bd_usercustomer urc where urc.fuserid=ud.fuserid "
					+ "group by fuserid having count(1)=1)"
					+ "group by weekofyear(ud.flogintime)"
					+ "order by weekofyear(ud.flogintime)) a where 1=1 ";
			break;
		case "month":
			sql = "select * from (select left(ud.flogintime,7) date"+lie+" from t_sys_userdiary ud "
					+join+ "where ud.flogintime>=date_sub(left(now(),10),interval 1 year)and exists "
					+ "(select fuserid from t_bd_usercustomer urc where urc.fuserid=ud.fuserid "
					+ "group by fuserid having count(1)=1)"
					+ "group by left(ud.flogintime,7)"
					+ "order by left(ud.flogintime,7)) a where 1=1 ";
			break;
		}

		try {
			result = this.SysUserDao.QueryFilterList(sql, request);
			List<HashMap<String, Object>> data = result.getData();
			LinkedHashMap<String, Object> flag = new LinkedHashMap<String, Object>();
			for (HashMap<String, Object> d : data) {
				flag.put(d.get("date")+"", 0);
			}
			Date dateNow = new Date();
			Date dateFrom;
			int index = 0;
			
			switch (sdate) {
			case "day":

				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

				Calendar cl = Calendar.getInstance();
				cl.setTime(dateNow);
				cl.add(Calendar.MONTH, -1);
				dateFrom = cl.getTime();

				LinkedHashMap<String, Integer> h = new LinkedHashMap<String, Integer>();
				for (Date c = dateFrom; c.before(dateNow);) {
					if (!flag.containsKey(sdf.format(c))) {
						h.put(sdf.format(c), index);
					}

					Calendar c2 = Calendar.getInstance();
					c2.setTime(c);
					c2.add(Calendar.DAY_OF_YEAR, 1);
					c = c2.getTime();
					index++;
				}
				if (!flag.containsKey(sdf.format(dateNow))) {
					h.put(sdf.format(dateNow), index);
				}

				Iterator it = h.keySet().iterator();
				while (it.hasNext()) {
					String n = (String) it.next();
					int m = h.get(n);
					HashMap<String, Object> hm = new HashMap<String, Object>();
					hm.put("date", n);
					hm.put("times", 0);
					data.add(m, hm);
				}

				result.setTotal(index + 1 + "");
				break;
			case "week":break;
			case "month":
				SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM");
				Calendar c2 = Calendar.getInstance();
				c2.setTime(dateNow);
				c2.add(Calendar.YEAR, -1);
				dateFrom = c2.getTime();

				LinkedHashMap<String, Integer> lhm = new LinkedHashMap<String, Integer>();
				for (Date c = dateFrom; c.before(dateNow);) {
					if (!flag.containsKey(sdf1.format(c))) {
						lhm.put(sdf1.format(c), index);
					}

					Calendar c3 = Calendar.getInstance();
					c3.setTime(c);
					c3.add(Calendar.MONTH, 1);
					c = c3.getTime();
					index++;
				}
				if (!flag.containsKey(sdf1.format(dateNow))) {
					lhm.put(sdf1.format(dateNow), index);
				}

				Iterator it1 = lhm.keySet().iterator();
				while (it1.hasNext()) {
					String n = (String) it1.next();
					int m = lhm.get(n);
					HashMap<String, Object> hm = new HashMap<String, Object>();
					hm.put("date", n);
					hm.put("times", 0);
					data.add(m, hm);
				}

				result.setTotal(index + 1 + "");
				break;
			}

			reponse.getWriter().write(JsonUtil.result(true, "", result));
		} catch (DJException e) {
			reponse.getWriter().write(
					JsonUtil.result(false, e.getMessage(), "", ""));
		}
		return null;
	}

	@RequestMapping("/GetUserLoginLogGrid")
	public String GetUserLoginLogGrid(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		try {
			String ctype = request.getParameter("ctype");
			String a = "";
			String b = "";
			if ("cstmr".equals(ctype)) {
				a = "(select fname,count(fname) count,flogintime FROM";
				b = "group by fname) b where 1=1";
			}
			String sql = "select * from "
					+ a
					+ "(select fusername,flogintime,fip,fbrowser,fremark,fname "
					+ "from t_sys_userdiary ud left join t_bd_usercustomer urc on urc.fuserid = ud.fuserid "
					+ "left join t_bd_customer c on urc.fcustomerid=c.fid where exists "
					+ "(select fuserid from t_bd_usercustomer urc where urc.fuserid=ud.fuserid "
					+ "group by  fuserid  having count(1)=1) order by flogintime desc) a where 1=1 ";
			ListResult result;
			reponse.setCharacterEncoding("utf-8");
			String sdate = request.getParameter("sdate");
			String date = new String(request.getParameter("date").getBytes(
					"ISO-8859-1"), "UTF-8");
			if (sdate.contains("week")) {
				int year = Integer.parseInt(date.substring(0, 4));
				int week = Integer.parseInt(date.substring(5,
						date.lastIndexOf("周")));
				Calendar c = Calendar.getInstance();
				c.set(year, 0, 1);
				c.add(Calendar.WEEK_OF_YEAR, week - 1);
				c.add(Calendar.DAY_OF_YEAR, 2 - c.get(Calendar.DAY_OF_WEEK));
				Date dayfrom = c.getTime();
				c.add(Calendar.DAY_OF_YEAR, 7);
				Date daylast = c.getTime();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				sql += " and flogintime > '" + sdf.format(dayfrom)
						+ "' and flogintime < '" + sdf.format(daylast) + "'";
			} else {
				sql += " and flogintime like '" + date + "%'";
			}
			sql += b;
			result = this.SysUserDao.QueryFilterList(sql, request);
			reponse.getWriter().write(JsonUtil.result(true, "", result));
		} catch (DJException e) {
			reponse.getWriter().write(
					JsonUtil.result(false, e.getMessage(), "", ""));
		}
		return null;
	}
	/**
	 * 得到当前用户的邮箱和手机
	 */
	@RequestMapping("/getolduserMSG")
	public String getolduserMSG(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		String fuserid=((Useronline)request.getSession().getAttribute("Useronline")).getFuserid();
		String sql = "select femail,ftel from t_sys_user where fid = '"+fuserid+"'";
		ListResult result;
		reponse.setCharacterEncoding("utf-8");
		try {
			result = this.SysUserDao.QueryFilterList(sql, request);
			reponse.getWriter().write(JsonUtil.result(true,"",result));
		} catch (DJException e) {
			reponse.getWriter().write(JsonUtil.result(false,e.getMessage(),"",""));
		}
		
		return null;
	}
	/**
	 * 修改用户邮箱和手机
	 * @param request
	 * @param reponse
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/ChangeSysUserMsg")
	public String ChangeSysUserMsg(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		String result = "";
		reponse.setCharacterEncoding("utf-8");
		try {
			SysUserDao.ExecChangeMSG(request);
			reponse.setCharacterEncoding("utf-8");
			result = "{success:true,msg:'用户信息修改成功!'}";
		} catch (Exception e) {
			result = JsonUtil.result(false, e.getMessage(), "", "");
		}
		reponse.getWriter().write(result);

		return null;

	}
	@RequestMapping("getUserPrintList")
	public String getUserPrintList(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		String userid = ((Useronline)request.getSession().getAttribute("Useronline")).getFuserid();
		String sql = "select u.fphone,u.ffax,u.fid,u.fname,fpassword,feffect,fcustomername,u.femail,u.ftel,u.fcreatetime,fisfilter,fisreadonly,c.fname fcustomerid  FROM t_sys_user u left join t_bd_customer c on u.fcustomerid = c.fid where u.fid='"+userid+"'";
		ListResult Result = new ListResult();
		try {
			Result = SysUserDao.QueryFilterList(sql, request);
			reponse.setCharacterEncoding("utf-8");
			reponse.getWriter().write(JsonUtil.result(true, "", Result));

		} catch (DJException e) {
			reponse.setCharacterEncoding("utf-8");
			reponse.getWriter().write(
					JsonUtil.result(false, e.getMessage(), "", ""));

		}

		return null;

	}
	/*
	 * 客户查看用户信息
	 */
	@RequestMapping("/GetCustUserInfo")
	public String GetCustUserInfo(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		reponse.setCharacterEncoding("utf-8");
		try {
			String fid = request.getParameter("fid");
			String sql = "SELECT u.fphone,u.ffax,u.fimid,u.fid,u.fname,u.fcustomerid as fcustomerid_fid,ifnull(c.fname,'') as fcustomerid_fname,"
					+ "u.fsupplierid as fsupplierid_fid,ifnull(s.fname,'') as fsupplierid_fname,u.feffect,u.fcustomername,u.femail,u.ftel,u.fcreatetime,u.fisfilter,u.fisreadonly,u.ftype "
					+ "FROM t_sys_user  u left join t_bd_customer c on c.fid= u.fcustomerid left join t_sys_supplier s on s.fid=u.fsupplierid"
					+ " where u.fid =:fid";
			params p = new params();
			p.put("fid", fid);
			List<HashMap<String, Object>> result = SysUserDao
					.QueryBySql(sql, p);
			reponse.getWriter().write(JsonUtil.result(true, "", "", result));
		} catch (Exception e) {
			reponse.getWriter().write(
					JsonUtil.result(false, e.getMessage(), "", ""));
		}

		return null;

	}
	@RequestMapping("/noteVerify")
	public void noteVerify(HttpServletRequest request,HttpServletResponse response) throws IOException{
		String userName = request.getParameter("username");
		try {
			if(StringUtils.isEmpty(request.getParameter("code"))){
				throw new DJException("请输入验证码！");
			}
			int Ncode = Integer.valueOf(request.getParameter("code"));
			boolean noteCode =  VCLoginHelper.getvCLoginHelper().loginByLVC(userName,Ncode);
			if(noteCode){
				String sql = "update t_sys_user set fstate = 0 where fname='"+userName+"' and fstate=1";
				SysUserDao.ExecBySql(sql);
				response.getWriter().write(JsonUtil.result(true, "", "", ""));
			}else{
				response.getWriter().write(JsonUtil.result(false, "验证码错误！", "", ""));
			}
		} catch (DJException e) {
			// TODO: handle exception
			response.getWriter().write(
					JsonUtil.result(false, e.getMessage(), "", ""));
		}
	
	}
	
	
	
}