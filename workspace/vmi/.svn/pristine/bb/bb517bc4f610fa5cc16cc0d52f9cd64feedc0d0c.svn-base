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
import Com.Entity.System.Role;
import Com.Entity.System.RoleCustomer;
import Com.Entity.System.RoleSupplier;
import Com.Entity.System.UserRole;
import Com.Entity.System.Useronline;
import Com.Dao.System.IRoleDao;

@Controller
public class RoleController {
	Logger log = LoggerFactory.getLogger(RoleController.class);
	@Resource
	private IRoleDao RoleDao;
	
	@RequestMapping("/saveRole")
	public String saveRole(HttpServletRequest request , HttpServletResponse response) throws IOException { 
		String result = "";
		String fid = request.getParameter("fid");
		
		try {
			DateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String userid = ((Useronline)request.getSession().getAttribute("Useronline")).getFuserid();
			request.setCharacterEncoding("utf-8");
			response.setCharacterEncoding("utf-8");
			if(fid==null || fid.equals("")){
				
				Role info = new Role();			
				info.setFid(request.getParameter("fid"));
				info.setFname(request.getParameter("fname"));
				info.setFnumber(request.getParameter("fnumber"));
				info.setFdescription(request.getParameter("fdescription"));
				info.setFisdefrole(new Integer(0));
				info.setFtype(new Integer(20));
				info.setFcreateid(userid);
				info.setFcratetime(new Date());
				info.setFlastupdateid(userid);
				info.setFlastupdatetime(new Date());
				HashMap<String, Object> params = RoleDao.ExecSave(info);
				if (params.get("success") == Boolean.TRUE) {
					result = "{success:true,msg:'角色保存成功!'}";
				} else {
					
				}
			}else{
				String fname = request.getParameter("fname");
				String fnumber = request.getParameter("fnumber");
				String fdescription = request.getParameter("fdescription");
				try {
					String sql = "Update t_sys_role set fname='" + fname
							+ "',fnumber='" + fnumber
							+ "', fdescription='" + fdescription
							+ "',flastupdateid='" + userid
							+ "', flastupdatetime='" + f.format(new Date())
							+ "' where fid ='" + fid + "'";
					RoleDao.ExecBySql(sql);
					response.setCharacterEncoding("utf-8");
					result = "{success:true,msg:'角色修改成功!'}";
				} catch (Exception e) {
					result = "{success:false,msg:'" + e.toString().replaceAll("'", "")
							+ "'}";// e.getCause().getCause().toString()
				}
			}
		} catch (Exception e) {

			result = "{success:false,msg:'"
			// +
			// (e.getCause().getCause()==null?e.getCause().toString():e.getCause().getCause().toString().replaceAll("'",
			// ""))
					+ e.toString() + "'}";// e.getCause().getCause().toString()
		}
		response.getWriter().write(result);
		return null;
	}
	
	@RequestMapping("/DelRoleList")
	public String DelUserList(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		String result = "";
		String fidcls = request.getParameter("fidcls");
		fidcls="('"+fidcls.replace(",","','")+"')";
		try {
			//待定是否需要进行确认使用的角色不让删除!
//			String hql = " FROM Role where fid in " + fidcls;
//			List<RoleDao> sList = roledao.QuerySysUsercls(hql);
//			if (sList.size() > 0) {
//				result = "{success:false,msg:'不能删除已使用的角色!'}";
//			} else {
			String hql = "Delete FROM Role where fid in " + fidcls;
			RoleDao.ExecByHql(hql);
				result = "{success:true,msg:'删除成功!'}";
//			}
			reponse.setCharacterEncoding("utf-8");
		} catch (Exception e) {
			result = "{success:false,msg:'" + e.toString().replaceAll("'", "")
					+ "'}";// e.getCause().getCause().toString()
			log.error("DelUserList error", e);
		}
		reponse.getWriter().write(result);

		return null;
	}
	
	@RequestMapping("/GetRoleList")
	public String GetRoleList(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		String result = "";
		String sql = "select fid,fname,fnumber,fisdefrole,ftype,fcreateid,fcratetime,fdescription,flastupdateid,flastupdatetime FROM t_sys_role ";
			
		ListResult Result = new ListResult();
		try {
			Result = RoleDao.QueryFilterList(sql, request);
			reponse.setCharacterEncoding("utf-8");
			reponse.getWriter().write(JsonUtil.result(true, "", Result));

		} catch (DJException e) {
			reponse.setCharacterEncoding("utf-8");
			reponse.getWriter().write(
					JsonUtil.result(false, e.getMessage(), "", ""));

		}
//		List<HashMap<String, Object>> sList = RoleDao.QueryBySql(sql);
//		reponse.setCharacterEncoding("utf-8");
//		reponse.getWriter().write(JsonUtil.result(true,"",RoleDao.QueryCountBySql(sql),sList));

		return null;

	}
	@RequestMapping("/GetRoleInfo")
	public String GetRoleInfo(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String sql = "select fid,fname,fnumber,fisdefrole,ftype,fcreateid,fcratetime,fdescription,flastupdateid,flastupdatetime FROM t_sys_role where fid = '"
				+ request.getParameter("fid") +"'";
		List<HashMap<String, Object>> sList = RoleDao.QueryBySql(sql);
		response.setCharacterEncoding("utf-8");
		response.getWriter().write(JsonUtil.result(true,"","1",sList));
		return null;
	}
	
	
	@RequestMapping(value = "/roletoexcel")
	public String roletoexcel(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {

		try {
			String sql = "select fid,fname '名称',fnumber '编码',fdescription '描述',fisdefrole '默认角色',fcratetime '创建时间'  FROM t_sys_role ";
			ListResult result = RoleDao.QueryFilterList(sql, request);
			ExcelUtil.toexcel(reponse, result);
		} catch (DJException e) {
			// TODO Auto-generated catch block
			reponse.getWriter().write(
					JsonUtil.result(false, e.getMessage(), "", ""));
		}

		return null;

	}
	
	@RequestMapping("/AddRoleUser")
	public String AddRoleUser(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		 reponse.setCharacterEncoding("utf-8");
		try
		{
			String sql = "";
			String fidcls = request.getParameter("fidcls");
			fidcls="('"+fidcls.replace(",","','")+"')";
			String froleid = request.getParameter("myobjectid");
			String[] tArrayofFid = DataUtil.InStrToArray(fidcls);
			for (int i = 0; i < tArrayofFid.length ; i++) {
				sql = "SELECT fid FROM t_sys_userrole where froleid = :froleid and fuserid = :fuserid ;";//SELECT * 
				params paramsT1 = new params();
				paramsT1.setString("froleid", froleid);
				paramsT1.setString("fuserid", tArrayofFid[i]);

				if (this.RoleDao.QueryBySql(sql, paramsT1).size() == 0) {
					UserRole info=new UserRole();
					info.setFid(RoleDao.CreateUUid());
					info.setFuserid( tArrayofFid[i]);
					info.setFroleid(froleid);
					RoleDao.saveOrUpdate(info);
				}
			}
			reponse.getWriter().write(JsonUtil.result(true, "分配成功", "",""));
		}
		catch(Exception e)
		{
			reponse.getWriter().write(JsonUtil.result(false, e.getMessage(), "",""));
		}
		
		return null;
	}
	

		@RequestMapping("/DelRoleUser")
		public String DelRoleUser(HttpServletRequest request,
				HttpServletResponse reponse) throws IOException {
			reponse.setCharacterEncoding("utf-8");
			try
			{
				String sql = "delete from t_sys_userrole where froleid= :froleid  and fuserid in ";
				String fidcls = request.getParameter("fidcls");
				fidcls="('"+fidcls.replace(",","','")+"')";
				String froleid = request.getParameter("myobjectid");
				sql = sql + fidcls;
				params paramsT = new params();
				paramsT.setString("froleid", froleid);
				RoleDao.ExecBySql(sql, paramsT);
				reponse.getWriter().write(JsonUtil.result(true, "取消成功","",""));
			}
			catch(Exception e)
			{
				reponse.getWriter().write(JsonUtil.result(false,e.getMessage(),"",""));
			}
			return null;
		}

	@RequestMapping("/GetRoleUserList")
	public String GetRoleUserList(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		
		String sql = "SELECT c.FNAME as fname, c.fcustomername as fcustomername, c.FID as fid FROM t_sys_userrole l, t_sys_user c where l.fuserid =  c.FID ";
		ListResult result;
		
		reponse.setCharacterEncoding("utf-8");
		try {
			result = this.RoleDao.QueryFilterList(sql, request);
			reponse.getWriter().write(JsonUtil.result(true,"",result));
		} catch (DJException e) {
			reponse.getWriter().write(JsonUtil.result(false,e.getMessage(),"",""));
		}
	

		return null;

	}
	
	
	@RequestMapping("/AddRoleSupplier")
	public String AddRoleSupplier(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		 reponse.setCharacterEncoding("utf-8");
		try
		{
			String sql = "";
			String fidcls = request.getParameter("fidcls");
			fidcls="('"+fidcls.replace(",","','")+"')";
			String froleid = request.getParameter("myobjectid");
			String[] tArrayofFid = DataUtil.InStrToArray(fidcls);
			for (int i = 0; i < tArrayofFid.length ; i++) {
				sql = "SELECT fid FROM t_bd_rolesupplier where froleid = :froleid and fsupplierid = :fsupplierid ;";//SELECT * 
				params paramsT1 = new params();
				paramsT1.setString("froleid", froleid);
				paramsT1.setString("fsupplierid", tArrayofFid[i]);

				if (this.RoleDao.QueryBySql(sql, paramsT1).size() == 0) {
					RoleSupplier info=new RoleSupplier();
					info.setFid(RoleDao.CreateUUid());
					info.setFsupplierid( tArrayofFid[i]);
					info.setFroleid(froleid);
					RoleDao.saveOrUpdate(info);
				}
			}
			reponse.getWriter().write(JsonUtil.result(true, "分配成功", "",""));
		}
		catch(Exception e)
		{
			reponse.getWriter().write(JsonUtil.result(false, e.getMessage(), "",""));
		}
		
		return null;
	}
	

		@RequestMapping("/DelRoleSupplier")
		public String DelRoleSupplier(HttpServletRequest request,
				HttpServletResponse reponse) throws IOException {
			reponse.setCharacterEncoding("utf-8");
			try
			{
				String sql = "delete from t_bd_rolesupplier where froleid= :froleid  and fsupplierid in ";
				String fidcls = request.getParameter("fidcls");
				fidcls="('"+fidcls.replace(",","','")+"')";
				String froleid = request.getParameter("myobjectid");
				sql = sql + fidcls;
				params paramsT = new params();
				paramsT.setString("froleid", froleid);
				RoleDao.ExecBySql(sql, paramsT);
				reponse.getWriter().write(JsonUtil.result(true, "取消成功","",""));
			}
			catch(Exception e)
			{
				reponse.getWriter().write(JsonUtil.result(false,e.getMessage(),"",""));
			}
			return null;
		}

	@RequestMapping("/GetRoleSupplierList")
	public String GetRoleSupplierList(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		
		String sql = "SELECT c.FNAME as fname, c.fnumber as fnumber, c.FID as fid ,c.fcreatetime as fcreatetime,c.fusedstatus fusedstatus FROM t_bd_rolesupplier l, t_sys_supplier c where l.fsupplierid =  c.FID ";
		ListResult result;
		
		reponse.setCharacterEncoding("utf-8");
		try {
			result = this.RoleDao.QueryFilterList(sql, request);
			reponse.getWriter().write(JsonUtil.result(true,"",result));
		} catch (DJException e) {
			reponse.getWriter().write(JsonUtil.result(false,e.getMessage(),"",""));
		}
	

		return null;

	}
	
	
	@RequestMapping("/AddRoleCustomer")
	public String AddRoleCustomer(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		 reponse.setCharacterEncoding("utf-8");
		try
		{
			String sql = "";
			String fidcls = request.getParameter("fidcls");
			fidcls="('"+fidcls.replace(",","','")+"')";
			String froleid = request.getParameter("myobjectid");
			String[] tArrayofFid = DataUtil.InStrToArray(fidcls);
			for (int i = 0; i < tArrayofFid.length ; i++) {
				sql = "SELECT fid FROM t_bd_rolecustomer where froleid = :froleid and fcustomerid = :fcustomerid ;";//SELECT * 
				params paramsT1 = new params();
				paramsT1.setString("froleid", froleid);
				paramsT1.setString("fcustomerid", tArrayofFid[i]);

				if (this.RoleDao.QueryBySql(sql, paramsT1).size() == 0) {
					RoleCustomer info=new RoleCustomer();
					info.setFid(RoleDao.CreateUUid());
					info.setFcustomerid( tArrayofFid[i]);
					info.setFroleid(froleid);
					RoleDao.saveOrUpdate(info);
				}
			}
			reponse.getWriter().write(JsonUtil.result(true, "分配成功", "",""));
		}
		catch(Exception e)
		{
			reponse.getWriter().write(JsonUtil.result(false, e.getMessage(), "",""));
		}
		
		return null;
	}
	

		@RequestMapping("/DelRoleCustomer")
		public String DelRoleCustomer(HttpServletRequest request,
				HttpServletResponse reponse) throws IOException {
			reponse.setCharacterEncoding("utf-8");
			try
			{
				String sql = "delete from t_bd_rolecustomer where froleid= :froleid  and fcustomerid in ";
				String fidcls = request.getParameter("fidcls");
				fidcls="('"+fidcls.replace(",","','")+"')";
				String froleid = request.getParameter("myobjectid");
				sql = sql + fidcls;
				params paramsT = new params();
				paramsT.setString("froleid", froleid);
				RoleDao.ExecBySql(sql, paramsT);
				reponse.getWriter().write(JsonUtil.result(true, "取消成功","",""));
			}
			catch(Exception e)
			{
				reponse.getWriter().write(JsonUtil.result(false,e.getMessage(),"",""));
			}
			return null;
		}

	@RequestMapping("/GetRoleCustomerList")
	public String GetRoleCustomerList(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		
		String sql = "SELECT c.FNAME as fname, c.fnumber as fnumber, c.FID as fid ,c.fcreatetime as fcreatetime,c.faddress faddress FROM t_bd_rolecustomer l, t_bd_customer c where l.fcustomerid =  c.FID ";
		ListResult result;
		
		reponse.setCharacterEncoding("utf-8");
		try {
			result = this.RoleDao.QueryFilterList(sql, request);
			reponse.getWriter().write(JsonUtil.result(true,"",result));
		} catch (DJException e) {
			reponse.getWriter().write(JsonUtil.result(false,e.getMessage(),"",""));
		}
	

		return null;

	}
}
