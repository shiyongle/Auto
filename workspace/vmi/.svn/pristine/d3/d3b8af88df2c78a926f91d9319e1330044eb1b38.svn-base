package Com.Controller.System;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import Com.Base.Util.DJException;
import Com.Base.Util.DataUtil;
import Com.Base.Util.ExcelUtil;
import Com.Base.Util.JsonUtil;
import Com.Base.Util.ListResult;
import Com.Base.Util.params;
import Com.Dao.System.IButtonDao;
import Com.Dao.System.IMainMenuDao;
import Com.Dao.System.ISysUserDao;
import Com.Entity.System.Button;
import Com.Entity.System.Mainmenuitem;
import Com.Entity.System.SysUser;
import Com.Entity.System.Useronline;
@Controller
public class SubCustomerController {
	Logger log = LoggerFactory.getLogger(SysUserController.class);
	@Resource
	private ISysUserDao sysUserDao;
	@Resource
	private IMainMenuDao mainMenuDao;
	@Resource
	private IButtonDao buttonDao;
	
	private SysUser getUserInfo(HttpServletRequest request) {
		Useronline useronline = (Useronline)request.getSession().getAttribute("Useronline");
		return sysUserDao.Query(useronline.getFuserid());
	}
	
	/**
	 * 获取子用户列表
	 * @param request
	 * @param reponse
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/getSubUserList")
	public String getSubUserList(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		SysUser userinfo = getUserInfo(request);
		String fcustomerid = userinfo.getFcustomerid();
		if("".equals(fcustomerid)){
			response.getWriter().write(JsonUtil.result(false, "客户管理员才能操作！", "",""));
			return null;
		}
		String sql = "select fid,fname,fpassword,feffect,fcustomername,femail,ftel,fcreatetime  FROM t_sys_user where fcustomerid='"+fcustomerid+"' and fid != '"+userinfo.getFid()+"'";
		try {
			ListResult result = sysUserDao.QueryFilterList(sql, request);
			response.getWriter().write(JsonUtil.result(true, "", result));
		} catch (DJException e) {
			response.getWriter().write(
					JsonUtil.result(false, e.getMessage(), "", ""));
		}
		return null;
	}
	/**
	 * 保存子用户
	 * @param request
	 * @param reponse
	 * @return
	 * @throws IOException
	 * @throws ParseException 
	 */
	@RequestMapping(value ="/SaveSubUser")
	public String SaveSubUser(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException, ParseException {
		
		SysUser userinfo = getUserInfo(request);
		String sql,result = "";
		String fcustomerid = userinfo.getFcustomerid();
		if(fcustomerid==null||"".equals(fcustomerid)){
			result = "您不具备管理权限，无法新增用户，请先向管理员或东经平台申请！";
			reponse.getWriter().write(JsonUtil.result(false, result, "",""));
			return null;
		}
		String fid = request.getParameter("fid");
		String fname = request.getParameter("fname");
		String fpassword = request.getParameter("fpassword");
		HashMap<String, String> map = new HashMap<>();
		DateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			map.put(fname, "用户名称");
			map.put(fpassword,"密码");
			DataUtil.verifyNotNull(map);
			map.clear();
			map.put("fname", fname);
			DataUtil.checkExist("t_sys_user", fid, map, sysUserDao,"fcustomerid = '"+userinfo.getFcustomerid()+"'");
			sql = "select md5('"+fpassword+"') pwd";
			List<HashMap<String, Object>> list = sysUserDao.QueryBySql(sql);
			fpassword = list.get(0).get("pwd").toString();
			SysUser user = new SysUser();
			if(fid==null||"".equals(fid)){
				fid = sysUserDao.CreateUUid();
			}
			user.setFid(fid);
			user.setFname(fname);
			user.setFpassword(fpassword);
			user.setFcustomername(userinfo.getFcustomername());
			user.setFemail(request.getParameter("femail"));
			user.setFtel(request.getParameter("ftel"));
			user.setFcreatetime(!request.getParameter("fcreatetime").isEmpty() ? f.parse(request.getParameter("fcreatetime")) : new Date());
			user.setFcustomerid(userinfo.getFcustomerid());
			user.setFsupplierid(request.getParameter("fsupplierid"));
			user.setFtype(userinfo.getFtype());
			user.setFeffect(0);
//			sysUserDao.saveOrUpdate(user);
			sysUserDao.saveSubUser(user,userinfo);
			result = "{success:true,msg:'保存成功!'}";
		} catch (DJException e) {
			result = "{success:false,msg:'"+ e.getMessage() + "'}";
		}
		reponse.getWriter().write(result);
		return null;
	}
	/**
	 * 获取用户信息
	 * @param request
	 * @param reponse
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/getSubUserInfo")
	public String getSubUserInfo(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		try {
			String fid = request.getParameter("fid");
			String sql = "SELECT u.fid,u.fname,u.fcustomerid as fcustomerid_fid,ifnull(c.fname,'') as fcustomerid_fname,"
					+ "u.fsupplierid as fsupplierid_fid,ifnull(s.fname,'') as fsupplierid_fname ,u.fpassword,u.feffect,u.fcustomername,u.femail,u.ftel,u.fcreatetime,u.fisfilter,u.ftype "
					+ "FROM t_sys_user  u left join t_bd_customer c on c.fid= u.fcustomerid left join t_sys_supplier s on s.fid=u.fsupplierid"
					+ " where u.fid =:fid";
			params p = new params();
			p.put("fid", fid);
			List<HashMap<String, Object>> result = sysUserDao.QueryBySql(sql, p);
			reponse.getWriter().write(JsonUtil.result(true, "", "", result));
		} catch (DJException e) {
			reponse.getWriter().write(
					JsonUtil.result(false, e.getMessage(), "", ""));
		}

		return null;

	}
	/**
	 * 删除子用户
	 * @param request
	 * @param reponse
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/delSubUserList")
	public String delSubUserList(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		String result = "";
		String fidcls = request.getParameter("fidcls");
		fidcls="('"+fidcls.replace(",","','")+"')";
		try {
			String hql = " FROM SysUser where feffect=1 and fid in " + fidcls;
			List<SysUser> sList = sysUserDao.QuerySysUsercls(hql);
			if (sList.size() > 0) {
				result = "{success:false,msg:'不能删除已经启用的用户!'}";
			} else {
				hql = "Delete FROM SysUser where fid in " + fidcls;
				sysUserDao.ExecByHql(hql);
				result = "{success:true,msg:'删除成功!'}";
			}
		} catch (DJException e) {
			result = "{success:false,msg:'" + e.toString().replaceAll("'", "")+ "'}";
			log.error("delSubUserList error", e);
		}
		reponse.getWriter().write(result);
		return null;
	}
	/**
	 * 导出子用户列表
	 * @param request
	 * @param reponse
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/subUserExcel")
	public String subUserExcel(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		SysUser userinfo = getUserInfo(request);
		try {
			String sql = "select fcreatetime 创建时间,ftel 电话 ,femail 电子邮件,fcustomername 客户名称,if(feffect=0,'否','是') 是否启用,fname 用户名  FROM t_sys_user where fcustomerid = '"+userinfo.getFcustomerid()+"' and fid != '"+userinfo.getFid()+"'";
			ListResult result = sysUserDao.QueryFilterList(sql, request);
			ExcelUtil.toexcel(reponse, result);
		} catch (DJException e) {
			reponse.getWriter().write(
					JsonUtil.result(false, e.getMessage(), "", ""));
		}
		return null;
	}
	/**
	 * 启用或禁用子用户
	 * @param request
	 * @param reponse
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/effectSubUserList")
	public String effectSubUserList(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		SysUser userinfo = getUserInfo(request);
		String result = "";
		String fidcls = request.getParameter("fidcls");
		fidcls="('"+fidcls.replace(",","','")+"')";
		try {
			String effect = request.getParameter("feffect");
			if(!DataUtil.numberCheck(effect)){
				throw new DJException("参数传递有误！");
			}
			int feffect = Integer.parseInt(effect);
			String hql = "Update SysUser set feffect=" + feffect
					+ " where fid in " + fidcls +" and fcustomerid = '"+userinfo.getFcustomerid()+"'";
			sysUserDao.ExecByHql(hql);
			result = "{success:true,msg:'" + (feffect == 1 ? "启用" : "禁用")+ "成功!'}";
		} catch (DJException e) {
			result = "{success:false,msg:'" + e.toString().replaceAll("'", "")+ "'}";
		}
		reponse.getWriter().write(result);
		return null;
	}
	/**
	 * 修改子用户密码
	 * @param request
	 * @param reponse
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/saveSubUserpwd")
	public String saveSubUserpwd(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		String result = "";
		String fid = request.getParameter("fid");
		String fpassword = request.getParameter("fpassword");
		try {
			DataUtil.verifyNotNull(fpassword, "密码");
			String sql = "Update T_SYS_USER set fpassword='" + fpassword
					+ "' where fid ='" + fid + "'";
			sysUserDao.ExecBySql(sql);
			reponse.setCharacterEncoding("utf-8");
			result = "{success:true,msg:'密码设置成功!'}";
		} catch (DJException e) {
			result = "{success:false,msg:'" + e.toString().replaceAll("'", "")+ "'}";
		}
		reponse.getWriter().write(result);
		return null;
	}
	/**
	 * 获取当前用户权限信息
	 * @param request
	 * @param reponse
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/getCurrentUserPermissionList")
	public String getCurrentUserPermissionList(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		String fid = request.getParameter("fid");
		String userid = getUserInfo(request).getFid();
		String hql = "";
		Mainmenuitem mainmenuitem = mainMenuDao.Query(fid);
		try {
			if (mainmenuitem.isleaf()) {
				hql = "SELECT t FROM Button t where fparentid='" + fid + "' and exists( FROM Userpermission t1 where t1.fresourcesid=t.fid and t1.fuserid='"+userid+"') ";
				List<Button> sList = buttonDao.QueryByHql(hql);
				reponse.getWriter().write(getSysButtonJSON(sList));
			} else {
				hql = "SELECT t FROM Mainmenuitem t where fparentid='" + fid+ "' and exists( FROM Userpermission t1 where t1.fresourcesid=t.fid and t1.fuserid='"+userid+"') "
						+ " order by forder";
				List<Mainmenuitem> sList = buttonDao.QueryByHql(hql);
				reponse.getWriter().write(getMainmenuJSON(sList));
			}
		} catch (DJException e) {
			reponse.getWriter().write(
					JsonUtil.result(false, e.toString(), "", ""));

		}
		return null;
	}
	
	/**
	 * 获取子用户权限信息
	 * @param request
	 * @param reponse
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/getSubUserPermissionList")
	public String getSubUserPermissionList(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		String fid = request.getParameter("fid");
		String userid = request.getParameter("userid");
		String hql = "";
		Mainmenuitem mainmenuitem = mainMenuDao.Query(fid);
		try {
			if (mainmenuitem.isleaf()) {
				hql = "SELECT t FROM Button t where fparentid='" + fid + "' and exists( FROM Userpermission t1 where t1.fresourcesid=t.fid and t1.fuserid='"+userid+"') ";
				List<Button> sList = buttonDao.QueryByHql(hql);
				reponse.getWriter().write(getSysButtonJSON(sList));
			} else {
				hql = "SELECT t FROM Mainmenuitem t where fparentid='" + fid+ "' and exists( FROM Userpermission t1 where t1.fresourcesid=t.fid and t1.fuserid='"+userid+"') "
						+ " order by forder";
				List<Mainmenuitem> sList = buttonDao.QueryByHql(hql);
				reponse.getWriter().write(getMainmenuJSON(sList));
			}
		} catch (DJException e) {
			reponse.getWriter().write(
					JsonUtil.result(false, e.toString(), "", ""));

		}
		return null;

	}
	
	@RequestMapping("/delSubUserPermission")
	public String delSubUserPermission(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		String permissionID = request.getParameter("permissionID");
		String userid = request.getParameter("userID");
		try {
			buttonDao.ExecDelPermission(permissionID, userid);
			reponse.getWriter().write(
					JsonUtil.result(true,"分配成功!", "", ""));
		} catch (Exception e) {
			reponse.getWriter().write(
					JsonUtil.result(false,e.getMessage(), "", ""));
		}
		return null;

	}
	/**
	 * 子用户添加权限
	 * @param request
	 * @param reponse
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/saveSubUserPermission")
	public String saveSubUserPermission(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		String permissionID = request.getParameter("permissionID");
		String userid = request.getParameter("userID");
		try {
			buttonDao.ExecSetPermission(permissionID, userid);
			reponse.getWriter().write(
					JsonUtil.result(true,"分配成功!", "", ""));
		} catch (Exception e) {
			reponse.getWriter().write(
					JsonUtil.result(false,e.getMessage(), "", ""));
		}
		return null;

	}
	/**
	 * 查询当前管理员用户的所有角色
	 * @param request
	 * @param reponse
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("getAllRoleOfUser")
	public String getAllRoleOfUser(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		SysUser userinfo = getUserInfo(request);
		String sql = "select fid,fname,fnumber from t_sys_role where fcustomerid = '"+userinfo.getFcustomerid()+"'";
		ListResult result;
		try {
			result = sysUserDao.QueryFilterList(sql, request);
			reponse.getWriter().write(JsonUtil.result(true,"",result));
		} catch (DJException e) {
			reponse.getWriter().write(JsonUtil.result(false,e.getMessage(),"",""));
		}
		return null;
	}
	/**
	 * 查询某一个子用户的所有角色
	 * @param request
	 * @param reponse
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("getAllRoleOfSubUser")
	public String getAllRoleOfSubUser(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		String sql = "select r.fid,fname,fnumber from t_sys_role r,t_sys_userrole ur where r.fid=ur.froleid ";
		ListResult result;
		try {
			result = sysUserDao.QueryFilterList(sql, request);
			reponse.getWriter().write(JsonUtil.result(true,"",result));
		} catch (DJException e) {
			reponse.getWriter().write(JsonUtil.result(false,e.getMessage(),"",""));
		}
		return null;
	}
	/**
	 * 为子用户添加角色
	 * @param request
	 * @param reponse
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/addRoleToSubUser")
	public String addRoleToSubUser(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		try
		{
			String fidcls = request.getParameter("fidcls");
			fidcls="('"+fidcls.replace(",","','")+"')";
			String userID = request.getParameter("myobjectid");
			if(!DataUtil.DataCheck(request, "t_sys_user", null, null, sysUserDao, userID)){
				throw new DJException("参数传递错误，用户ID不存在！");
			}
			if(!DataUtil.DataCheck(request, "t_sys_role", null, null, sysUserDao, fidcls)){
				throw new DJException("参数传递错误，角色ID不存在！");
			}
			String[] tArrayofFid = DataUtil.InStrToArray(fidcls);
			sysUserDao.saveUserRole(userID, tArrayofFid);
			reponse.getWriter().write(JsonUtil.result(true, "分配成功", "",""));
		}
		catch(DJException e)
		{
			reponse.getWriter().write(JsonUtil.result(false, e.getMessage(), "",""));
		}
		return null;
	}
	/**
	 * 从子用户删除角色
	 * @param request
	 * @param reponse
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/delRoleFromSubUser")
	public String delRoleFromSubUser(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		String fidcls = request.getParameter("fidcls");
		fidcls="('"+fidcls.replace(",","','")+"')";
		String userID = request.getParameter("myobjectid");
		String sql = "delete from t_sys_userrole where FUSERID=:userid  and froleid in ";
		sql = sql + fidcls;
		params paramsT = new params();
		paramsT.setString("userid", userID);
		try
		{
			sysUserDao.ExecBySql(sql, paramsT);
			reponse.getWriter().write(JsonUtil.result(true, "取消成功","",""));
		}
		catch(DJException e)
		{
			reponse.getWriter().write(JsonUtil.result(false,e.getMessage(),"",""));
		}
		return null;
	}
	
	private String getSysButtonJSON(List<Button> sList) {
		if (sList.size()<1)
		{
			return "";
		}
		String result = "[";
		int index = 0;
		for (Button info : sList) {
			if (index > 0) {
				result = result + ",";
			}
			result = result + "{fid:\"" + info.getFid() + "\",fparentid:\""
					+ info.getFparentid() + "\",fisleaf:2,fname:\""
					+ info.getFname()+ "\",fpath:\"" + info.getFpath() + "\",text:\"" + info.getFname()
					+ "\",leaf:true}";
			index++;
		}
		return result+"]";

	}

	private String getMainmenuJSON(List<Mainmenuitem> sList) {
		if (sList.size()<1)
		{
			return "";
		}
		String result = "[";
		int index = 0;
		for (Mainmenuitem info : sList) {
			if (index > 0) {
				result = result + ",";
			}
			result = result + "{fid:\"" + info.getFid() + "\",forder:"
					+ info.getForder() + ",fparentid:\"" + info.getFparentid()
					+ "\",flevel:" + info.getFlevel()
					+ ",fisleaf:"
					+ info.getFisleaf() + ",fname:\"" + info.getFname()
					+ "\",text:\"" + info.getFname()+ "\",fpath:\"" + info.getFpath() + "\",furl:\""
					+ info.getFurl() + "\",leaf:false}";
			index++;
		}
		return result+"]";
		
	}
	
}
