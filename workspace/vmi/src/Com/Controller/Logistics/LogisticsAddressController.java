package Com.Controller.Logistics;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils.Collections;

import Com.Base.Util.DJException;
import Com.Base.Util.DataUtil;
import Com.Base.Util.JsonUtil;
import Com.Base.Util.ListResult;
import Com.Base.Util.params;
import Com.Base.Util.mySimpleUtil.MySimpleToolsZ;
import Com.Dao.Logistics.ILogisticsAddressDao;
import Com.Dao.System.IAddressDao;
import Com.Dao.System.IBaseSysDao;
import Com.Dao.System.ICustRelationAdressDao;
import Com.Dao.quickOrder.IQuickOrderDao;
import Com.Entity.Logistics.LogisticsAddress;
import Com.Entity.System.Address;
import Com.Entity.System.CustRelationAdress;
import Com.Entity.System.Useronline;
@Controller
public class LogisticsAddressController {
	Logger log = LoggerFactory.getLogger(LogisticsAddressController.class);
	@Resource
	private ILogisticsAddressDao logisticsAddressDao;

	@Resource
	private IBaseSysDao baseSysDao;
	
	@RequestMapping("/GetLogisticsAddressList")
	public String GetLogisticsAddressList(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		 String sql =  "select fid,flinkman,fphone,fname,flasted from t_bd_logisticsaddress where ftype=0 "+logisticsAddressDao.QueryFilterByUser(request, "fcustomerid", null);
		 try {
			 ListResult result= logisticsAddressDao.QueryFilterList(sql, request);
			reponse.setCharacterEncoding("utf-8");
			reponse.getWriter().write(JsonUtil.result(true, "", result));
		} catch (DJException e) {
			reponse.getWriter().write( 
					JsonUtil.result(false, e.getMessage(), "", ""));
		}

		return null;

	}

	@RequestMapping(value = "/SaveLogisticsAddress")
	public String SaveLogisticsAddress(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		String result = ""; 
		LogisticsAddress ainfo = (LogisticsAddress)request.getAttribute("LogisticsAddress");
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
			String sql="select 1 from t_bd_logisticsaddress where ftype=%d and  fname='%s' and flinkman='%s' and fphone='%s' and fid<>'%s' and fcustomerid='%s'";
			sql=sql.format(sql,ainfo.getFtype(), ainfo.getFname(),ainfo.getFlinkman(),ainfo.getFphone(),ainfo.getFid(),ainfo.getFcustomerid());
			if(logisticsAddressDao.QueryExistsBySql(sql))  throw new DJException("该记录已存在");

			if(StringUtils.isEmpty(ainfo.getFid()))
			{
				ainfo.setFid(logisticsAddressDao.CreateUUid());
				ainfo.setFcreatorid(userid);
				ainfo.setFcreatetime(new Date());
			}
			ainfo.setFlastupdatetime(new Date());
			ainfo.setFlastupdateuserid(userid);
			HashMap<String, Object> params = logisticsAddressDao.ExecSave(ainfo);
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

	
	@RequestMapping("/DelLogisticsAddress")
	public String DelLogisticsAddress(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		String result = "";
		String fidcls = request.getParameter("fidcls");
		fidcls="('"+fidcls.replace(",","','")+"')";
		
			try {
				String hql = "Delete FROM  LogisticsAddress where fid in " + fidcls;
				logisticsAddressDao.ExecByHql(hql);
				result = JsonUtil.result(true, "删除成功!", "", "");
				reponse.setCharacterEncoding("utf-8");
			} catch (Exception e) {
				result = JsonUtil.result(false, e.getMessage(), "", "");
				log.error("DelLogisticsAddress error", e);
			}
		
		reponse.getWriter().write(result);
		return null;
	}

	@RequestMapping("/getLogisticsAddressInfo")
	public String getLogisticsAddressInfo(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		reponse.setCharacterEncoding("utf-8");
		try {
			String sql = " select fid,fname,flinkman,fphone,fcreatorid,fcreatetime,ftype,fcustomerid,flasted from  t_bd_logisticsaddress where fid='"
					+ request.getParameter("fid") + "'";
			List<HashMap<String, Object>> sList = logisticsAddressDao.QueryBySql(sql);
			reponse.getWriter().write(JsonUtil.result(true, "", "", sList));

		} catch (Exception e) {
			reponse.getWriter().write( 
					JsonUtil.result(false, e.toString(), "", ""));
		}
		return null;
	}

	
	@RequestMapping("/GetConsigneeAddressList")
	public String GetConsigneeAddressList(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		 String sql =  "select fid,flinkman,fphone,fname,flasted from t_bd_logisticsaddress where ftype=1 "+logisticsAddressDao.QueryFilterByUser(request, "fcustomerid", null);
		 try {
			 ListResult result= logisticsAddressDao.QueryFilterList(sql, request);
			reponse.setCharacterEncoding("utf-8");
			reponse.getWriter().write(JsonUtil.result(true, "", result));
		} catch (DJException e) {
			reponse.getWriter().write( 
					JsonUtil.result(false, e.getMessage(), "", ""));
		}

		return null;

	}
	
}
