package Com.Controller.Logistics;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import Com.Base.Util.DJException;
import Com.Base.Util.JsonUtil;
import Com.Base.Util.ListResult;
import Com.Dao.Logistics.ILogisticsCarDao;
import Com.Dao.System.IBaseSysDao;
import Com.Entity.Logistics.LogisticsCarinfo;
import Com.Entity.System.Useronline;
@Controller
public class LogisticsCarController {
	Logger log = LoggerFactory.getLogger(LogisticsCarController.class);
	@Resource
	private ILogisticsCarDao logisticsCarDao;

	@Resource
	private IBaseSysDao baseSysDao;
	
	@RequestMapping("/GetLogisticsCarinfoList")
	public String GetLogisticsCarinfoList(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		 String sql =  "select fid,fcargotype,fcartype,flasted from t_bd_logisticscarinfo where 1=1 "+logisticsCarDao.QueryFilterByUser(request, "fcustomerid", null);
		 try {
			 ListResult result= logisticsCarDao.QueryFilterList(sql, request);
			reponse.setCharacterEncoding("utf-8");
			reponse.getWriter().write(JsonUtil.result(true, "", result));
		} catch (DJException e) {
			reponse.getWriter().write( 
					JsonUtil.result(false, e.getMessage(), "", ""));
		}

		return null;

	}

	@RequestMapping(value = "/SaveLogisticsCarinfo")
	public String SaveLogisticsCarinfo(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		String result = ""; 
		LogisticsCarinfo ainfo = (LogisticsCarinfo)request.getAttribute("LogisticsCarinfo");
		try {
			String userid = ((Useronline) request.getSession().getAttribute(
					"Useronline")).getFuserid();
			if(StringUtils.isEmpty(ainfo.getFcustomerid()))
			{	
				String fcustomerid=baseSysDao.getCurrentCustomerid(userid);
				if("".equals(fcustomerid))
				{
					 throw new DJException("该账号没有关联任何客户，请联系客服进行设置");
				}
				ainfo.setFcustomerid(fcustomerid);
			}
			String sql="select 1 from t_bd_logisticscarinfo where  fcargotype='%s' and fcartype='%s' and fid<>'%s' and fcustomerid='%s'";
			sql=sql.format(sql,ainfo.getFcargotype(),ainfo.getFcartype(),ainfo.getFid(),ainfo.getFcustomerid());
			if(logisticsCarDao.QueryExistsBySql(sql))  throw new DJException("该记录已存在");
			
			if(StringUtils.isEmpty(ainfo.getFid()))
			{
				ainfo.setFid(logisticsCarDao.CreateUUid());
				ainfo.setFcreatorid(userid);
				ainfo.setFcreatetime(new Date());
			}
			ainfo.setFlastupdatetime(new Date());
			ainfo.setFlastupdateuserid(userid);
			HashMap<String, Object> params = logisticsCarDao.ExecSave(ainfo);
			System.out.println(params);
			if (params.get("success") == Boolean.TRUE) {
				result = JsonUtil.result(true, "保存成功!", "", "");
			} else {
				result = JsonUtil.result(false, "保存失败!", "", "");
			}
		} catch (Exception e) {
			result = JsonUtil.result(false, e.getMessage(), "", "");
		}
		reponse.setCharacterEncoding("utf-8");
		reponse.getWriter().write(result);
		return null;

	}

	
	@RequestMapping("/DelLogisticsCarinfo")
	public String DelLogisticsCarinfo(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		String result = "";
		String fidcls = request.getParameter("fidcls");
		fidcls="('"+fidcls.replace(",","','")+"')";
		
			try {
				String hql = "Delete FROM  LogisticsCarinfo where fid in " + fidcls;
				logisticsCarDao.ExecByHql(hql);
				result = JsonUtil.result(true, "删除成功!", "", "");
				reponse.setCharacterEncoding("utf-8");
			} catch (Exception e) {
				result = JsonUtil.result(false, e.getMessage(), "", "");
				log.error("DelLogisticsCarinfo error", e);
			}
		
		reponse.getWriter().write(result);
		return null;
	}

	@RequestMapping("/getLogisticsCarInfo")
	public String getLogisticsCarInfo(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		reponse.setCharacterEncoding("utf-8");
		try {
			String sql = " select fid,fcargotype,fcartype,fcreatorid,fcreatetime,fcustomerid,flasted from  t_bd_logisticscarinfo where fid='"
					+ request.getParameter("fid") + "'";
			List<HashMap<String, Object>> sList = logisticsCarDao.QueryBySql(sql);
			reponse.getWriter().write(JsonUtil.result(true, "", "", sList));

		} catch (Exception e) {
			reponse.getWriter().write( 
					JsonUtil.result(false, e.toString(), "", ""));
		}
		return null;
	}

	
	
}
