package Com.Controller.System;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
import Com.Base.Util.JsonUtil;
import Com.Base.Util.ListResult;
import Com.Base.Util.params;
import Com.Dao.System.IQueryConfigDao;
import Com.Entity.System.Queryconfig;

@Controller
public class QueryConfigController {
	Logger log = LoggerFactory.getLogger(QueryConfigController.class);
	@Resource
	private IQueryConfigDao QueryConfigDao;

	@RequestMapping("/SaveQueryConfig")
	public String SaveQueryConfig(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		reponse.setCharacterEncoding("utf-8");
		request.setCharacterEncoding("utf-8");
		String result = "";
		try {
			Queryconfig info = new Queryconfig();
			info.setFid(request.getParameter("fid"));
			info.setFcolumnkey(request.getParameter("fcolumnkey"));
			info.setFcolumnname(request.getParameter("fcolumnname"));
			info.setFfilterable(request
					.getParameter("ffilterable")==null?0:Integer.parseInt(request
					.getParameter("ffilterable")));
			info.setFfilterfield(request.getParameter("ffilterfield"));
			info.setFhide(request.getParameter("fhide")==null?0:Integer.parseInt(request.getParameter("fhide")));
			info.setFpath(request.getParameter("fpath"));
			info.setFseq(request.getParameter("fseq")==null?0:Integer.parseInt(request.getParameter("fseq")));
			info.setFwidth(request.getParameter("fwidth")==null?100:Integer.parseInt(request.getParameter("fwidth")));
			info.setFtype(request.getParameter("ftype"));
			info.setFsql(request.getParameter("fsql"));
			QueryConfigDao.ExecSave(info);
			result = JsonUtil.result(true,"保存成功!", "", "");
		} catch (DJException e) {
			// TODO Auto-generated catch block
			result= JsonUtil.result(false,e.getMessage(), "", "");
		}
		reponse.getWriter().write(result);
		return null;
	}

	@RequestMapping("/GetQueryConfiginfo")
	public String GetQueryConfiginfo(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		reponse.setCharacterEncoding("utf-8");
		try {
			String fid = request.getParameter("fid");
			String sql = "select * from t_sys_queryconfig where fid = '" + fid
					+ "'";
			List<HashMap<String, Object>> result = QueryConfigDao
					.QueryBySql(sql);
			reponse.getWriter().write(JsonUtil.result(true, "", "", result));
		} catch (Exception e) {
			reponse.getWriter().write(
					JsonUtil.result(false, e.getMessage(), "", ""));
		}
		return null;
	}
	@RequestMapping("/GetQueryData")
	public String GetQueryData(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		
		if(request.getParameter("fpath")==null)
		{
			reponse.getWriter().write(JsonUtil.result(false,"没有指定路径","",""));
			return null;
		}
		try
		{
			String fpath=request.getParameter("fpath");
			String sql = "select fsql FROM t_sys_queryconfig  where fpath=:fpath and ftype='sql' ";
			params p = new params();
			p.put("fpath", fpath);
			List<HashMap<String, Object>> result;
			reponse.setCharacterEncoding("utf-8");
			result = QueryConfigDao.QueryBySql(sql, p);
			sql=result.get(0).get("fsql").toString();
			result = QueryConfigDao.QueryBySql(sql);
			reponse.getWriter().write(JsonUtil.result(true, "","", result));
		}
		catch(Exception e)
		{
			reponse.getWriter().write(JsonUtil.result(false,e.getMessage(),"", ""));
		}
		return null;
	}
	@RequestMapping("/GetColumnList")
	public String GetColumnList(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		
		if(request.getParameter("fpath")==null)
		{
			reponse.getWriter().write(JsonUtil.result(false,"没有指定路径","",""));
			return null;
		}
		try
		{
			String fpath=request.getParameter("fpath");
			String sql = "select * FROM t_sys_queryconfig  where fpath=:fpath and ftype='column' order by fseq";
			params p = new params();
			p.put("fpath", fpath);
			List<HashMap<String, Object>> result;
			reponse.setCharacterEncoding("utf-8");
			result = QueryConfigDao.QueryBySql(sql, p);
			reponse.getWriter().write(JsonUtil.result(true, "","", result));
		}
		catch(Exception e)
		{
			reponse.getWriter().write(JsonUtil.result(false,e.getMessage(),"", ""));
		}
		return null;
	}
	
	@RequestMapping("/GetQueryConfigList")
	public String GetQueryConfigList(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		Connection conn = null;
		PreparedStatement stmt = null;
		 ResultSet rs=null; //数据库查询结果集 
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			String connectionUrl = "jdbc:sqlserver://10.10.253.123;database=VMIDB;user=DongjingUser;password=Dong@jing#User";
			conn = DriverManager.getConnection(connectionUrl);
			//外网不通时使用此代码end
			 StringBuffer sql1=new StringBuffer("");
			 sql1.append(" select t.*,werk.NAME fwerkname from dbo.PM_ORDERINFO t inner join T_ORD_CUSDELIVER t1 on t.pkid=t1.ref_id left join VM_WERKLIFNR_VMI werk on werk.CODE=t.WERKS where t1.isread=0");
			 stmt=conn.prepareStatement(sql1.toString());
			 String pkids="";
			 String execsql="";
			 rs=stmt.executeQuery();
			 JsonUtil.result(true,"",rs.getRow()+"",rs);
		} catch (ClassNotFoundException | SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		
		
		
		
		String sql = "select * FROM t_sys_queryconfig where  ftype='column'  ";
		ListResult result;
		reponse.setCharacterEncoding("utf-8");
		try {
			result = QueryConfigDao.QueryFilterList(sql, request);
			reponse.getWriter().write(JsonUtil.result(true, "", result));
		} catch (DJException e) {
			reponse.getWriter().write(
					JsonUtil.result(false, e.getMessage(), "", ""));
		}
		return null;
	}
	@RequestMapping("/DelQueryConfigList")
	public String DelQueryConfigList(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		String result = "";
		String fidcls = request.getParameter("fidcls");
		fidcls="('"+fidcls.replace(",","','")+"')";
		try {
//			String sql = "Delete FROM Queryconfig where fid in "+fidcls;
//			QueryConfigDao.ExecByHql(sql);
//			
			String sql = "Delete FROM t_sys_queryconfig where fid in "+fidcls;
			QueryConfigDao.ExecBySql(sql);
			result = JsonUtil.result(true,"删除成功!", "", "");
			reponse.setCharacterEncoding("utf-8");
		} catch (Exception e) {
			result = JsonUtil.result(false,e.getMessage(), "", "");
			log.error("DelDeliversList error", e);
		}
		reponse.getWriter().write(result);
		return null;
	}

}
