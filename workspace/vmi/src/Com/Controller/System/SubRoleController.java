package Com.Controller.System;

import java.io.IOException;
import java.text.DateFormat;
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
import Com.Dao.System.IRoleDao;
import Com.Dao.System.ISysUserDao;
import Com.Entity.System.Role;
import Com.Entity.System.SysUser;
import Com.Entity.System.UserRole;
import Com.Entity.System.Useronline;

@Controller
public class SubRoleController {
	Logger log = LoggerFactory.getLogger(SysUserController.class);
	@Resource
	private IRoleDao roleDao;
	@Resource
	private ISysUserDao sysUserDao;
	
	private SysUser getUserInfo(HttpServletRequest request) {
		Useronline useronline = (Useronline)request.getSession().getAttribute("Useronline");
		return sysUserDao.Query(useronline.getFuserid());
	}
	/**
	 * 获取客户子角色
	 * @param request
	 * @param reponse
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/getSubRoleList")
	public String getSubRoleList(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		SysUser userinfo = getUserInfo(request);
		String fcustomerid = userinfo.getFcustomerid();
		if("".equals(fcustomerid)){
			response.getWriter().write(JsonUtil.result(false, "客户管理员才能操作！", "",""));
			return null;
		}
		String sql = "select fid,fname,fnumber,fisdefrole,ftype,fcreateid,fcratetime,fdescription,flastupdateid,flastupdatetime FROM t_sys_role where fcustomerid = '"+userinfo.getFcustomerid()+"'";
		try {
			ListResult Result = roleDao.QueryFilterList(sql, request);
			response.getWriter().write(JsonUtil.result(true, "", Result));
		} catch (DJException e) {
			response.getWriter().write(
					JsonUtil.result(false, e.getMessage(), "", ""));

		}
		return null;
	}
	/**
	 * 保存子角色
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/saveSubRole")
	public String saveSubRole(HttpServletRequest request , HttpServletResponse response) throws IOException {
		String sql,result = "";
		String fid = request.getParameter("fid");
		String fname = request.getParameter("fname");
		String fnumber = request.getParameter("fnumber");
		String fdescription = request.getParameter("fdescription");
		HashMap<String, String> map = new HashMap<>();
		SysUser userinfo = getUserInfo(request);
		String fcustomerid = userinfo.getFcustomerid();
		if(fcustomerid==null||"".equals(fcustomerid)){
			result = "您不具备管理权限，无法新增用户，请先向管理员或东经平台申请！";
			response.getWriter().write(JsonUtil.result(false, result, "",""));
			return null;
		}
		try {
			map.put(fname, "角色名称");
			map.put(fnumber,"角色编号");
			DataUtil.verifyNotNull(map);
			map.clear();
			map.put("fnumber", fnumber);
			DataUtil.checkExist("t_sys_role", fid, map, roleDao, "fcustomerid = '"+userinfo.getFcustomerid()+"'");
			DateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String userid = userinfo.getFid();
			if(fid==null || fid.equals("")){
				Role info = new Role();
				info.setFid("");
				info.setFname(fname);
				info.setFnumber(fnumber);
				info.setFdescription(fdescription);
				info.setFisdefrole(new Integer(0));
				info.setFtype(new Integer(20));
				info.setFcreateid(userid);
				info.setFcratetime(new Date());
				info.setFlastupdateid(userid);
				info.setFlastupdatetime(new Date());
				info.setFcustomerid(fcustomerid);
				HashMap<String, Object> params = roleDao.ExecSave(info);
				if (params.get("success") == Boolean.TRUE) {
					result = "{success:true,msg:'角色保存成功!'}";
				}
			}else{
				sql = "Update t_sys_role set fname='" + fname
							+ "',fnumber='" + fnumber
							+ "', fdescription='" + fdescription
							+ "',flastupdateid='" + userid
							+ "', flastupdatetime='" + f.format(new Date())
							+ "' where fid ='" + fid + "'";
				roleDao.ExecBySql(sql);
				result = "{success:true,msg:'角色修改成功!'}";
			}
		} catch (DJException e) {
			result = "{success:false,msg:'"+ e.getMessage() + "'}";
		}
		response.getWriter().write(result);
		return null;
	}
	/**
	 * 获取子角色信息
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/getSubRoleInfo")
	public String getSubRoleInfo(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String sql = "select fid,fname,fnumber,fisdefrole,ftype,fcreateid,fcratetime,fdescription,flastupdateid,flastupdatetime FROM t_sys_role where fid = '"
				+ request.getParameter("fid") +"'";
		List<HashMap<String, Object>> sList = roleDao.QueryBySql(sql);
		response.getWriter().write(JsonUtil.result(true,"","1",sList));
		return null;
	}
	/**
	 * 删除子角色
	 * @param request
	 * @param reponse
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/delSubRole")
	public String delSubRole(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String fidcls = request.getParameter("fidcls");
		fidcls="('"+fidcls.replace(",","','")+"')";
		try {
			if(roleDao.QueryExistsBySql("select 1 from t_sys_userrole where froleid in "+fidcls)){
				throw new DJException("此角色已经关联用户，无法删除！");
			}
			String hql = "Delete FROM Role where fid in " + fidcls;
			roleDao.ExecByHql(hql);
			response.getWriter().write(JsonUtil.result(true, "操作成功！", "",""));
		} catch (DJException e) {
			response.getWriter().write(JsonUtil.result(false, e.getMessage(), "",""));
		}
		return null;
	}
	/**
	 * 导出子角色excel
	 * @param request
	 * @param reponse
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/subRoleExcel")
	public String subRoleExcel(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		try {
			SysUser userinfo = getUserInfo(request);
			String sql = "select fnumber '编码',fdescription '描述',fisdefrole '默认角色',fcratetime '创建时间',fname '名称'  FROM t_sys_role where fcustomerid = '"+userinfo.getFcustomerid()+"' ";
			ListResult result = roleDao.QueryFilterList(sql, request);
			ExcelUtil.toexcel(reponse, result);
		} catch (DJException e) {
			reponse.getWriter().write(
					JsonUtil.result(false, e.getMessage(), "", ""));
		}
		return null;
	}
	/**
	 * 获取某一角色的所有用户
	 * @param request
	 * @param reponse
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/getAllUserOfRole")
	public String getAllUserOfRole(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		String sql = "SELECT c.FNAME as fname, c.fcustomername as fcustomername, c.FID as fid FROM t_sys_userrole l, t_sys_user c where l.fuserid =  c.FID ";
		ListResult result;
		try {
			result = this.roleDao.QueryFilterList(sql, request);
			reponse.getWriter().write(JsonUtil.result(true,"",result));
		} catch (DJException e) {
			reponse.getWriter().write(JsonUtil.result(false,e.getMessage(),"",""));
		}
		return null;
	}
	/**
	 * 获取当前用户的所有子用户
	 * @param request
	 * @param reponse
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/getAllSubUser")
	public String getAllSubUser(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		SysUser userinfo = getUserInfo(request);
		String sql = "select fname,fcustomername,fid from t_sys_user where fcustomerid = '"+userinfo.getFcustomerid()+"' and fid !='"+userinfo.getFid()+"'";
		ListResult result;
		try {
			result = this.roleDao.QueryFilterList(sql, request);
			reponse.getWriter().write(JsonUtil.result(true,"",result));
		} catch (DJException e) {
			reponse.getWriter().write(JsonUtil.result(false,e.getMessage(),"",""));
		}
		return null;
	}
	/**
	 * 为角色添加子用户
	 * @param request
	 * @param reponse
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/addSubUserForRole")
	public String addSubUserForRole(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		try
		{
			String sql = "";
			String fidcls = request.getParameter("fidcls");
			String froleid = request.getParameter("myobjectid");
			if(!DataUtil.DataCheck(request, "t_sys_user", "", "", roleDao, fidcls)){
				throw new DJException("用户ID有误！");
			}
			if(!DataUtil.DataCheck(request, "t_sys_role", "", "", roleDao, froleid)){
				throw new DJException("角色ID有误！");
			}
			String[] tArrayofFid = fidcls.split(",");
			UserRole info;
			params paramsT1;
			for (int i = 0; i < tArrayofFid.length ; i++) {
				sql = "SELECT fid FROM t_sys_userrole where froleid = :froleid and fuserid = :fuserid ;";
				paramsT1 = new params();
				paramsT1.setString("froleid", froleid);
				paramsT1.setString("fuserid", tArrayofFid[i]);
				if (this.roleDao.QueryBySql(sql, paramsT1).size() == 0) {
					info=new UserRole();
					info.setFid(roleDao.CreateUUid());
					info.setFuserid(tArrayofFid[i]);
					info.setFroleid(froleid);
					roleDao.saveOrUpdate(info);
				}
			}
			reponse.getWriter().write(JsonUtil.result(true, "分配成功", "",""));
		}
		catch(DJException e)
		{
			reponse.getWriter().write(JsonUtil.result(false, e.getMessage(), "",""));
		}
		return null;
	}
	/**
	 * 从某一角色中删除子用户
	 * @param request
	 * @param reponse
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/delSubUserFromRole")
	public String delSubUserFromRole(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		String froleid = request.getParameter("myobjectid");
		String fidcls = request.getParameter("fidcls");
		fidcls="('"+fidcls.replace(",","','")+"')";
		String sql = "delete from t_sys_userrole where froleid= :froleid  and fuserid in ";
		sql = sql + fidcls;
		params paramsT = new params();
		paramsT.setString("froleid", froleid);
		try
		{
			roleDao.ExecBySql(sql, paramsT);
			reponse.getWriter().write(JsonUtil.result(true, "取消成功","",""));
		}
		catch(DJException e)
		{
			reponse.getWriter().write(JsonUtil.result(false,e.getMessage(),"",""));
		}
		return null;
	}
	
}
