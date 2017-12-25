package Com.Controller.order;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import Com.Base.Util.DJException;
import Com.Base.Util.ExcelUtil;
import Com.Base.Util.JsonUtil;
import Com.Base.Util.ListResult;
import Com.Base.Util.params;
import Com.Controller.System.RoleController;
import Com.Dao.order.IConventionalPlanDao;
import Com.Entity.System.Useronline;
import Com.Entity.order.Conventionalplan;;

@Controller
public class ConventionalPlanController {
	Logger log = LoggerFactory.getLogger(RoleController.class);
	@Resource
	private IConventionalPlanDao ConventionalPlanDao;
	@RequestMapping("/GetConventionalplanList")
	public String GetConventionalplanList(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		String sql = "select cp.fid,cp.fcreatorid,u1.fname as fcreator,cp.fcreatetime,cp.fupdateuserid,u2.fname as flastupdater,cp.fupdatetime,fcustProductid,ifnull(p.fnumber,'') fcustPdtNumer,ifnull(p.fname,'') fcustPdtName,fplanQty,fplanCycle,ifnull(date_format(fplanDelivery,'%Y-%m-%d %T'),'') fplanDelivery,fplanAmount,cp.fcustomerid,ifnull(c.fname,'') as custname,ifnull(cp.fcustproductName,'') fcustproductName,ifnull(cp.fcustomerName,'') fcustomerName,ifnull(fplanbegintime,'') fplanbegintime,ifnull(fplanendtime,'') fplanendtime,ifnull(cp.fdescription,'') fdescription from t_ppl_ConventionalPlan cp left join t_bd_customer c on c.fid=cp.fcustomerid left join t_sys_user u1 on  u1.fid=cp.fcreatorid left join t_sys_user u2 on  u2.fid=cp.fupdateuserid left join t_bd_custproduct p on p.fid=fcustProductid ";
		
		ListResult result;
		reponse.setCharacterEncoding("utf-8");
		try {
			result = ConventionalPlanDao.QueryFilterList(sql, request);
			reponse.getWriter().write(JsonUtil.result(true,"",result));
		} catch (DJException e) {
			reponse.getWriter().write(JsonUtil.result(false,e.getMessage(),"",""));
		}
	
		return null;
	}
	
	@RequestMapping(value="/SaveConventionalplan")
	public  String SaveConventionalplan(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
	String result = "";
	Conventionalplan vinfo=null;
	try{
	String userid = ((Useronline)request.getSession().getAttribute("Useronline")).getFuserid();
	String fid=request.getParameter("fid");
	if(fid!=null&&!"".equals(fid))
	{
		vinfo=ConventionalPlanDao.Query(fid);
	}else
	{
		vinfo=new Conventionalplan();
		vinfo.setFid(fid);
		vinfo.setFcreatorid(userid);
		vinfo.setFcreatetime(new Date());
	}
	vinfo.setFupdatetime(new Date());
	vinfo.setFupdateuserid(userid);
	vinfo.setFcustProductid(request.getParameter("fcustProductid"));
	
	vinfo.setFplanQty(new BigDecimal(request.getParameter("fplanQty")));
	vinfo.setFplanCycle(new BigDecimal(request.getParameter("fplanCycle")));
	DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	vinfo.setFplanDelivery(!request.getParameter("fplanDelivery").isEmpty() ? df.parse(request.getParameter("fplanDelivery")) : null);
	vinfo.setFplanbegintime(!request.getParameter("fplanbegintime").isEmpty() ? df.parse(request.getParameter("fplanbegintime")) : null);
	vinfo.setFplanendtime(!request.getParameter("fplanendtime").isEmpty() ? df.parse(request.getParameter("fplanendtime")) : null);
	vinfo.setFplanAmount(new BigDecimal(request.getParameter("fplanAmount")));

	vinfo.setFcustomerid(request.getParameter("fcustomerid"));
	vinfo.setFcustomerName(!request.getParameter("fcustomerName").isEmpty() ? request.getParameter("fcustomerName") : "");
	vinfo.setFcustProductName(!request.getParameter("fcustproductName").isEmpty() ? request.getParameter("fcustproductName") : "");
	vinfo.setFdescription(!request.getParameter("fdescription").isEmpty() ? request.getParameter("fdescription") : "");
	HashMap<String, Object> params = ConventionalPlanDao.ExecSave(vinfo);
	System.out.println(params);
	if (params.get("success") == Boolean.TRUE) {
	result = JsonUtil.result(true,"常规计划保存成功!","","");
	}else
	{
	result= JsonUtil.result(false,"常规计划保存失败!","","");
	}
	}
	catch(Exception e)
	{
		result = JsonUtil.result(false,e.getMessage(),"","");
	}
	reponse.setCharacterEncoding("utf-8");
	reponse.getWriter().write(result);
		return null;

	}

	@RequestMapping(value="/addmonthplan")
	public  String addmonthplan(HttpServletRequest request,HttpServletResponse reponse) throws IOException {
		String result = "";
		Conventionalplan vinfo=null;
		try{
			String userid = "0f20f5bf-a80b-11e2-b222-60a44c5bbef3";//导入的创建人为Administrator;
			String fid="";
			String data = request.getParameter("order");
			if (data != null) {
				JSONArray j = JSONArray.fromObject(data);
				for (int i = 0; i < j.size(); i++) {
					JSONObject o = j.getJSONObject(i);
					if (!o.containsKey("fcusproduct") || !o.containsKey("fcustomerName") || !o.containsKey("fplanQty") || !o.containsKey("fplanCycle") || 
							!o.containsKey("fplanDelivery") || !o.containsKey("fplanAmount") || !o.containsKey("fplanbegintime") || 
							!o.containsKey("fplanendtime")) {
						throw new DJException("缺少传递的参数");
					}
			
					String fcustproductName = o.getString("fcusproduct");		// 传递的参数:客户产品名称
					String fcustomerName = o.getString("fcustomerName");		// 传递的参数:客户名称
					String fplanQty = o.getString("fplanQty");					// 传递的参数:计划产量
					String fplanCycle = o.getString("fplanCycle");				// 传递的参数:计划周期
					String fplanDelivery = o.getString("fplanDelivery");		// 传递的参数:预计交期
					String fplanAmount = o.getString("fplanAmount");			// 传递的参数:计划数量
					String fplanbegintime = o.getString("fplanbegintime");		// 传递的参数:计划开始时间
					String fplanendtime = o.getString("fplanendtime");			// 传递的参数:计划结束时间
					
					String fdescription = "";
					if(o.containsKey("fdescription")){
						fdescription = o.getString("fdescription");				// 传递的参数:备注
					}
					
					vinfo=new Conventionalplan();
					vinfo.setFid(fid);
					vinfo.setFcreatorid(userid);
					vinfo.setFcreatetime(new Date());
					
					vinfo.setFupdatetime(new Date());
					vinfo.setFupdateuserid(userid);
					
					vinfo.setFplanQty(new BigDecimal(fplanQty));
					vinfo.setFplanCycle(new BigDecimal(fplanCycle));
					DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					vinfo.setFplanDelivery(!fplanDelivery.isEmpty() ? df.parse(fplanDelivery) : null);
					vinfo.setFplanbegintime(!fplanbegintime.isEmpty() ? df.parse(fplanbegintime) : null);
					vinfo.setFplanendtime(!fplanendtime.isEmpty() ? df.parse(fplanendtime) : null);
					vinfo.setFplanAmount(new BigDecimal(fplanAmount));
			
					vinfo.setFcustomerName(!fcustomerName.isEmpty() ? fcustomerName : "");
					vinfo.setFcustProductName(!fcustproductName.isEmpty() ? fcustproductName : "");
					vinfo.setFdescription(!fdescription.isEmpty() ? fdescription : "");
					HashMap<String, Object> params = ConventionalPlanDao.ExecSave(vinfo);
					
					if (params.get("success") == Boolean.TRUE) {
					result = JsonUtil.result(true,"计划导入成功!","","");
					}else
					{
					throw new DJException(JsonUtil.result(false,"计划导入失败!","",""));
					}
				}
			}
		}
		catch(Exception e)
		{
			result = JsonUtil.result(false,e.getMessage(),"","");
		}
		reponse.setCharacterEncoding("utf-8");
		reponse.getWriter().write(result);
		return null;

	}
	
	@RequestMapping("/DelConventionalplanList")
	public String DelConventionalplanList(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		String result = "";
		String fidcls = request.getParameter("fidcls");
		fidcls="('"+fidcls.replace(",","','")+"')";
		try {
			String hql = "Delete from Conventionalplan where fid in " + fidcls;
			ConventionalPlanDao.ExecByHql(hql);
			result = JsonUtil.result(true,"删除成功!","","");
			reponse.setCharacterEncoding("utf-8");
		} catch (Exception e) {
			result = JsonUtil.result(false,e.getMessage(),"","");
			log.error("DelDeliversList error", e);
		}
		reponse.getWriter().write(result);
		return null;
	}
	
	@RequestMapping("/getConventionalplanInfo")
	public String getConventionalplanInfo(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		reponse.setCharacterEncoding("utf-8");
		try {
			String fid = request.getParameter("fid");
			String sql = "select cp.fid,cp.fcreatorid,u1.fname as fcreator,cp.fcreatetime,cp.fupdateuserid,u2.fname as flastupdater,cp.fupdatetime,ifnull(fcustProductid,'') fcustProductid_fid,ifnull(p.fname,'') fcustProductid_fname,fplanQty,fplanCycle,fplanDelivery,fplanAmount,ifnull(cp.fcustomerid,'') fcustomerid_fid,ifnull(c.fname,'') as fcustomerid_fname,cp.fcustproductName,cp.fcustomerName,ifnull(fplanbegintime,'') fplanbegintime,ifnull(fplanendtime,'') fplanendtime,ifnull(cp.fdescription,'') fdescription from t_ppl_ConventionalPlan cp left join t_bd_customer c on c.fid=cp.fcustomerid left join t_sys_user u1 on  u1.fid=cp.fcreatorid left join t_sys_user u2 on  u2.fid=cp.fupdateuserid left join t_bd_custproduct p on p.fid=fcustProductid where cp.fid =:fid";
			params p=new params();
			p.put("fid",fid);
			List<HashMap<String,Object>> result= ConventionalPlanDao.QueryBySql(sql, p);
			reponse.getWriter().write(JsonUtil.result(true,"","",result));
		} catch (Exception e) {
			reponse.getWriter().write(JsonUtil.result(false, e.getMessage(), "", ""));
		}
		return null;
	}

	@RequestMapping("/AddConventionalplan")
	public String AddConventionalplan(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		reponse.setCharacterEncoding("utf-8");
		reponse.getWriter().write(JsonUtil.result(true,"","",""));
		return null;

	}
	
	@RequestMapping(value = "/ConventionalplantoExcel")
	public String ConventionalplantoExcel(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		try {
			String sql = "select cp.fid 单据ID,cp.fcreatorid 创建人ID,u1.fname as 创建人,cp.fcreatetime 创建时间,cp.fupdateuserid 修改人ID,u2.fname as 修改人,cp.fupdatetime 修改时间,fcustProductid 客户产品ID,p.fnumber 客户产品编码,p.fname 客户产品名称,fplanQty 计划产量,fplanCycle 计划周期,fplanDelivery 预计交期,fplanAmount 计划数量,cp.fcustomerid 客户ID,c.fname as 客户名称,cp.fcustproductName 输入的客户产品,cp.fcustomerName 输入的客户名称,fplanbegintime 计划开始时间,fplanendtime 计划结束时间,ifnull(cp.fdescription,'') 备注 from t_ppl_ConventionalPlan cp left join t_bd_customer c on c.fid=cp.fcustomerid left join t_sys_user u1 on  u1.fid=cp.fcreatorid left join t_sys_user u2 on  u2.fid=cp.fupdateuserid left join t_bd_custproduct p on p.fid=fcustProductid ";
//			String fcustomerid = request.getParameter("fcustomerid");
//			if (!"".equals(fcustomerid)&&fcustomerid!=null) {
//				sql += " where d.fcustomerid='" + fcustomerid + "'";
//			}
			ListResult result;
			result = ConventionalPlanDao.QueryFilterList(sql, request);
			ExcelUtil.toexcel(reponse,result);
//			InputStream inputStream = new FileInputStream(ExcelUtil.toexcel(result));
//			OutputStream os = reponse.getOutputStream();
//			byte[] b = new byte[1024];
//			int length;
//			while ((length = inputStream.read(b)) > 0) {
//				os.write(b, 0, length);
//			}
//			inputStream.close();
		} catch (DJException e) {
			// TODO Auto-generated catch block
			reponse.getWriter().write(
					JsonUtil.result(false, e.getMessage(), "", ""));
		}

		return null;

	}
	
}
