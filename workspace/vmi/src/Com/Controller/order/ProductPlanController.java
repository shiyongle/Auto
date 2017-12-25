package Com.Controller.order;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
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
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import Com.Base.Util.DJException;
import Com.Base.Util.ExcelUtil;
import Com.Base.Util.JsonUtil;
import Com.Base.Util.ListResult;
import Com.Base.Util.UploadFile;
import Com.Base.Util.mySimpleUtil.MySimpleToolsZ;
import Com.Dao.System.IProductdefDao;
import Com.Dao.order.IProductPlanDao;
import Com.Dao.order.IProductdemandfileDao;
import Com.Entity.System.Useronline;

@Controller
public class ProductPlanController {
	Logger log = LoggerFactory.getLogger(ProductPlanController.class);
	@Resource
	private IProductPlanDao productPlanDao;
	@Resource
	private IProductdefDao productdefDao;
	@Resource
	private IProductdemandfileDao productdemandfileDao;
	private boolean isImportEASing=false;
	@RequestMapping(value = "/GetProductPlans")
	public String GetProductPlans(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		reponse.setCharacterEncoding("utf-8"); 
		try { 
//			String sql="SELECT p.fid,p.fcreatetime,ifnull(u1.fname,'') cfname,p.fnumber,p.fcustomerid,ifnull(c.fname,'') cname1,p.fcusproductid,ifnull(cp.fname,'') cname2,p.fproductid, ifnull(f.fname,'') fname,p.farrivetime,p.famount,p.falloted,p.fallottime,p.fbizdate, ifnull(u2.fname,'') afname "+
//						" FROM (select *from t_ord_productplan order by fnumber) p left join t_sys_user u1 on u1.fid=p.fcreatorid "+
//						" left join t_bd_customer c on c.fid=p.fcustomerid left join t_bd_custproduct cp on cp.fid=p.fcusproductid "+
//						" left join t_pdt_productdef f on f.fid=p.fproductid left join t_sys_user u2 on u2.fid=p.fcreatorid where 1=1 "+productPlanDao.QueryFilterByUser(request, "c.fid",null);
			String sql = "select d.ftype,d.fid ,d.fnumber,c.fname as cname,ifnull(p.fname,'') as pname,ifnull(f.fname,'') as fname,ifnull(s.fname,'') as sname,p.fspec,d.farrivetime,d.fbizdate,d.famount,d.flastupdateuserid,u2.fname u2_fname,d.flastupdatetime,d.fcreatetime,d.fcreatorid,u1.fname u1_fname,d.faudited,d.fauditorid,ifnull(u3.fname,'') fauditor,ifnull(d.faudittime,'') faudittime,d.fordertype,d.fsuitProductID,d.fseq,d.fparentOrderEntryId,d.forderid,d.fimportEAS,d.fproductdefid,d.fentryProductType,d.fstockoutqty,d.fstockinqty,d.fstoreqty,d.faffirmed,d.fassemble,d.fiscombinecrosssubs,r.fnumber salefnumber,d.fparentorderid,ifnull(d.fcloseed,0) as fcloseed,d.fisfinished FROM (select * from t_ord_productplan ) d left join t_sys_user u1 on  u1.fid=d.fcreatorid left join t_sys_user u2 on u2.fid=d.flastupdateuserid left join t_bd_customer c on c.fid=d.fcustomerid left join t_bd_custproduct p on p.fid=d.fcustproduct left join t_sys_user u3 on u3.fid=d.fauditorid left join t_pdt_productdef f on f.fid=d.fproductdefid left join t_sys_supplier s on s.fid=d.fsupplierid left join t_ord_saleorder r on r.fid=d.fparentorderid where 1=1 "+productPlanDao.QueryFilterByUser(request, "c.fid", "s.fid");
			ListResult result=productPlanDao.QueryFilterList(sql, request);
			reponse.getWriter().write(JsonUtil.result(true, "", result));
		} catch (DJException e) {
			// TODO Auto-generated catch block
			reponse.getWriter().write(
					JsonUtil.result(false, e.getMessage(), "", ""));
		}

		return null;

	}
	
	@RequestMapping(value = "/GetAuditedProductPlans")
	public String GetAuditedProductPlans(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		reponse.setCharacterEncoding("utf-8"); 
//		String name = request.getParameter("name");
//		String value =request.getParameter("value");
		try { 
//			String sql="SELECT p.fid,p.fcreatetime,ifnull(u1.fname,'') cfname,p.fnumber,p.fcustomerid,ifnull(c.fname,'') cname1,p.fcusproductid,ifnull(cp.fname,'') cname2,p.fproductid, ifnull(f.fname,'') fname,p.farrivetime,p.famount,p.falloted,p.fallottime,p.fbizdate, ifnull(u2.fname,'') afname "+
//						" FROM (select *from t_ord_productplan order by fnumber) p left join t_sys_user u1 on u1.fid=p.fcreatorid "+
//						" left join t_bd_customer c on c.fid=p.fcustomerid left join t_bd_custproduct cp on cp.fid=p.fcusproductid "+
//						" left join t_pdt_productdef f on f.fid=p.fproductid left join t_sys_user u2 on u2.fid=p.fcreatorid where 1=1 "+productPlanDao.QueryFilterByUser(request, "c.fid",null);
			String sql = "select IFNULL(d.fdescription,'') fdescription,f.fid fproductid,d.ftype,d.fid ,d.fnumber,c.fname as cname,ifnull(p.fname,'') as pname,ifnull(f.fname,'') as fname,ifnull(s.fname,'') as sname,p.fspec,d.farrivetime,d.fbizdate,d.famount,d.flastupdateuserid,u2.fname u2_fname,d.flastupdatetime,d.fcreatetime,d.fcreatorid,u1.fname u1_fname,d.faudited,d.fauditorid,ifnull(u3.fname,'') fauditor,ifnull(d.faudittime,'') faudittime,d.fordertype,d.fsuitProductID,d.fseq,d.fparentOrderEntryId,d.forderid,d.fimportEAS,d.fproductdefid,d.fentryProductType,d.fstockoutqty,d.fstockinqty,d.fstoreqty,d.faffirmed,d.fassemble,d.fiscombinecrosssubs,r.fnumber salefnumber,d.fparentorderid,ifnull(d.fcloseed,0) as fcloseed FROM (select * from t_ord_productplan ) d left join t_sys_user u1 on  u1.fid=d.fcreatorid left join t_sys_user u2 on u2.fid=d.flastupdateuserid left join t_bd_customer c on c.fid=d.fcustomerid left join t_bd_custproduct p on p.fid=d.fcustproduct left join t_sys_user u3 on u3.fid=d.fauditorid left join t_pdt_productdef f on f.fid=d.fproductdefid left join t_sys_supplier s on s.fid=d.fsupplierid left join t_ord_saleorder r on r.fid=d.fparentorderid where 1=1 and  ifnull(d.faffirmed,0)<>1 and ifnull(d.faudited,0)=1 and d.fboxtype <> 1 "+productPlanDao.QueryFilterByUser(request, "c.fid", "s.fid");
//			if(name!=null&&!"".equals(name)){
//				if(value!=null&&!"".equals(value)){
//					value =  new String(value.toString().getBytes("ISO-8859-1"), "UTF-8");
//					sql += " and "+name+" like '%"+value+"%'";
//				}
//			}
//			sql = MySimpleToolsZ.getMySimpleToolsZ().buildMySearchBoxMixedTypeSQL(request, sql);
			
			ListResult result=productPlanDao.QueryFilterList(sql, request);
			reponse.getWriter().write(JsonUtil.result(true, "", result));
		} catch (DJException e) {
			// TODO Auto-generated catch block
			reponse.getWriter().write(
					JsonUtil.result(false, e.getMessage(), "", ""));
		}

		return null;

	}

	@RequestMapping(value = "/auditProductplan")
	public String auditProductplan(HttpServletRequest request,
			HttpServletResponse reponse) throws Exception {
		String result = "";
		// Saleorder pinfo = null;
		try {
			String userid = ((Useronline) request.getSession().getAttribute(
					"Useronline")).getFuserid();
			String fidcls = request.getParameter("fidcls");
			fidcls="('"+fidcls.replace(",","','")+"')";
			String sql = "select 1 from t_ord_productplan where faudited=0 and fid in "
					+ fidcls;
			List<HashMap<String, Object>> data = productPlanDao.QueryBySql(sql);
			if (data.size() > 0) {
				sql = "update t_ord_productplan set faudited=1,fauditorid='"
						+ userid
						+ "',faudittime=now(),fstate=0 where faudited=0 and fid in "
						+ fidcls;
				productPlanDao.ExecBySql(sql);
			} else {
				throw new DJException("没有需要审核的订单,无需审核！");
			}
			// }else {
			// throw new DJException("未保存不能审核！");
			// }
			result = JsonUtil.result(true, "审核成功！", "", "");
			// HashMap<String, Object> params = saleOrderDao.ExecSave(pinfo);
			// if (params.get("success") == Boolean.TRUE) {
			// result = JsonUtil.result(true,"审核成功!", "", "");
			// } else {
			// result = JsonUtil.result(false,"审核失败!", "", "");
			// }
		} catch (Exception e) {
			result = "{success:false,msg:'" + e.getMessage() + "'}";
		}
		reponse.setCharacterEncoding("utf-8");
		reponse.getWriter().write(result);
		return null;
	}

	@RequestMapping(value = "/unauditProductplan")
	public String unauditProductplan(HttpServletRequest request,
			HttpServletResponse reponse) throws Exception {
		String result = "";
		// Saleorder pinfo = null;
		try {
			String userid = ((Useronline) request.getSession().getAttribute(
					"Useronline")).getFuserid();
			// String fid = request.getParameter("fid");
			String fidcls = request.getParameter("fidcls");
			fidcls="('"+fidcls.replace(",","','")+"')";
			String sql = "select 1 from t_ord_productplan where faudited = 1 and fid in "
					+ fidcls;
			List<HashMap<String, Object>> data = productPlanDao.QueryBySql(sql);
			if (data.size() == 0) {
				throw new DJException("请选择已审核订单反审核！");
			}
			// if (fid != null && !"".equals(fid)) {
			// pinfo = saleOrderDao.Query(fid);
			// if(pinfo.getFaudited()==0){
			// throw new DJException("未审核无需反审核！");
			// }else if(pinfo.getFallot()==1)
			// {
			// throw new DJException("已分配不能反审核！");
			// }else if(pinfo.getFimportEas()==1){
			// throw new DJException("已导入EAS的订单不能反审核！");
			// }
			else {
				sql = "update t_ord_productplan set faudited=0,fauditorid=null"
						+ ",faudittime=null,fstate=null where faudited=1 and fid in "
						+ fidcls;
				productPlanDao.ExecBySql(sql);
				// pinfo.setFid(fid);
				// pinfo.setFauditorid(null);
				// pinfo.setFaudited(0);
				// pinfo.setFaudittime(null);
			}
			// } else {
			// throw new DJException("未保存不能反审核！");
			// }

			result = JsonUtil.result(true, "反审核成功！", "", "");
			// HashMap<String, Object> params = saleOrderDao.ExecSave(pinfo);
			// if (params.get("success") == Boolean.TRUE) {
			// result = JsonUtil.result(true,"反审核成功!", "", "");
			// } else {
			// result = JsonUtil.result(false,"反审核失败!", "", "");
			// }
		} catch (Exception e) {
			result = JsonUtil.result(false, e.getMessage(), "", "");
		}
		reponse.setCharacterEncoding("utf-8");
		reponse.getWriter().write(result);
		return null;
	}
	
	@RequestMapping(value = "/GetOrderInProductplan")
	public String GetOrderInProductplan(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		reponse.setCharacterEncoding("utf-8");
		try {
//			String sql="SELECT p.fid,p.fcreatetime,ifnull(u1.fname,'') cfname,p.fnumber,p.fcustomerid,ifnull(c.fname,'') cname1,p.fcusproductid,ifnull(cp.fname,'') cname2,p.fproductid, ifnull(f.fname,'') fname,p.farrivetime,p.famount,p.falloted,p.fallottime,p.fbizdate, ifnull(u2.fname,'') afname "+
//						" FROM (select *from t_ord_productplan order by fnumber) p left join t_sys_user u1 on u1.fid=p.fcreatorid "+
//						" left join t_bd_customer c on c.fid=p.fcustomerid left join t_bd_custproduct cp on cp.fid=p.fcusproductid "+
//						" left join t_pdt_productdef f on f.fid=p.fproductid left join t_sys_user u2 on u2.fid=p.fcreatorid where 1=1 "+productPlanDao.QueryFilterByUser(request, "c.fid",null);
			String sql = "select d.ftype,d.fid ,d.fnumber,c.fname as cname,ifnull(p.fname,'') as pname,ifnull(f.fname,'') as fname,ifnull(s.fname,'') as sname,p.fspec,d.farrivetime,d.fbizdate,d.famount,d.flastupdateuserid,u2.fname u2_fname,d.flastupdatetime,d.fcreatetime,d.fcreatorid,u1.fname u1_fname,d.faudited,d.fauditorid,ifnull(u3.fname,'') fauditor,ifnull(d.faudittime,'') faudittime,d.fordertype,d.fsuitProductID,d.fseq,d.fparentOrderEntryId,d.forderid,d.fimportEAS,d.fproductdefid,d.fentryProductType,d.fstockoutqty,d.fstockinqty,d.fstoreqty,d.faffirmed,d.fassemble,d.fiscombinecrosssubs,r.fnumber salefnumber,d.fparentorderid,ifnull(d.fcloseed,0) as fcloseed FROM (select * from t_ord_productplan ) d left join t_sys_user u1 on  u1.fid=d.fcreatorid left join t_sys_user u2 on u2.fid=d.flastupdateuserid left join t_bd_customer c on c.fid=d.fcustomerid left join t_bd_custproduct p on p.fid=d.fcustproduct left join t_sys_user u3 on u3.fid=d.fauditorid left join t_pdt_productdef f on f.fid=d.fproductdefid left join t_sys_supplier s on s.fid=d.fsupplierid left join t_ord_saleorder r on r.fid=d.fparentorderid where 1=1 and ifnull(d.fcloseed,0)=0 "+productPlanDao.QueryFilterByUser(request, "c.fid", "s.fid");
			ListResult result=productPlanDao.QueryFilterList(sql, request);
			reponse.getWriter().write(JsonUtil.result(true, "", result));
		} catch (DJException e) {
			// TODO Auto-generated catch block
			reponse.getWriter().write(
					JsonUtil.result(false, e.getMessage(), "", ""));
		}

		return null;

	}
	/**
	 * 制造商入库
	 *
	 * @param request
	 * @param reponse
	 * @return
	 * @throws IOException
	 *
	 * @date 2014-10-20 下午4:07:45 
	 */
	@RequestMapping(value = "/GetAffirmOrderInProductplan")
	public String GetAffirmOrderInProductplan(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		reponse.setCharacterEncoding("utf-8");
		try {
//			String sql="SELECT p.fid,p.fcreatetime,ifnull(u1.fname,'') cfname,p.fnumber,p.fcustomerid,ifnull(c.fname,'') cname1,p.fcusproductid,ifnull(cp.fname,'') cname2,p.fproductid, ifnull(f.fname,'') fname,p.farrivetime,p.famount,p.falloted,p.fallottime,p.fbizdate, ifnull(u2.fname,'') afname "+
//						" FROM (select *from t_ord_productplan order by fnumber) p left join t_sys_user u1 on u1.fid=p.fcreatorid "+
//						" left join t_bd_customer c on c.fid=p.fcustomerid left join t_bd_custproduct cp on cp.fid=p.fcusproductid "+
//						" left join t_pdt_productdef f on f.fid=p.fproductid left join t_sys_user u2 on u2.fid=p.fcreatorid where 1=1 "+productPlanDao.QueryFilterByUser(request, "c.fid",null);
			String sql = "select IFNULL(d.fdescription,'') fdescription,d.ftype,d.fid ,d.fnumber,c.fname as cname,ifnull(p.fname,'') as pname,ifnull(f.fname,'') as fname,ifnull(s.fname,'') as sname,p.fspec,d.farrivetime,d.fbizdate,d.famount,d.flastupdateuserid,u2.fname u2_fname,d.flastupdatetime,d.fcreatetime,d.fcreatorid,u1.fname u1_fname,d.faudited,d.fauditorid,ifnull(u3.fname,'') fauditor,ifnull(d.faudittime,'') faudittime,d.fordertype,d.fsuitProductID,d.fseq,d.fparentOrderEntryId,d.forderid,d.fimportEAS,d.fproductdefid,d.fentryProductType,ifnull(d.fstockoutqty,'') fstockoutqty,d.fstockinqty,d.fstoreqty,d.faffirmed,d.fassemble,d.fiscombinecrosssubs,r.fnumber salefnumber,d.fparentorderid,ifnull(d.fcloseed,0) as fcloseed FROM (select * from t_ord_productplan ) d left join t_sys_user u1 on  u1.fid=d.fcreatorid left join t_sys_user u2 on u2.fid=d.flastupdateuserid left join t_bd_customer c on c.fid=d.fcustomerid left join t_bd_custproduct p on p.fid=d.fcustproduct left join t_sys_user u3 on u3.fid=d.fauditorid left join t_pdt_productdef f on f.fid=d.fproductdefid left join t_sys_supplier s on s.fid=d.fsupplierid left join t_ord_saleorder r on r.fid=d.fparentorderid where 1=1 and ifnull(d.faffirmed,0)=1 and ifnull(d.fcloseed,0)=0 and d.fboxtype<>1"+productPlanDao.QueryFilterByUser(request, "c.fid", "s.fid");
			
//			sql = MySimpleToolsZ.getMySimpleToolsZ().buildMySearchBoxMixedTypeSQL(request, sql);
			
			ListResult result=productPlanDao.QueryFilterList(sql, request);
			reponse.getWriter().write(JsonUtil.result(true, "", result));
		} catch (DJException e) {
			// TODO Auto-generated catch block
			reponse.getWriter().write(
					JsonUtil.result(false, e.getMessage(), "", ""));
		}

		return null;

	}
	
	@RequestMapping(value = "/productplantoexcel")
	public String productplantoexcel(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		try {
			String sql = "select d.fid ,d.fnumber 编码,c.fname  客户名称,ifnull(p.fname,'') as 客户产品名称,ifnull(f.fname,'') 产品名称,ifnull(s.fname,'') 供应商名称 ,ifnull(p.fspec,'') 规格 ,d.farrivetime 送达时间,d.fbizdate 业务时间,d.famount 数量,u2.fname  修改人,ifnull(d.flastupdatetime,'') 修改时间,d.fcreatetime 创建时间,u1.fname 创建人,d.faudited 审核,ifnull(u3.fname,'') 审核人,ifnull(d.faudittime,'') 审核时间,d.fordertype 订单类型,d.fsuitProductID ,d.fseq 订单分录,d.fparentOrderEntryId,d.forderid,d.fimportEAS 导入EAS,d.fstockoutqty 出库,d.fstockinqty 入库,d.fstoreqty 存量,d.faffirmed 确认,r.fnumber 订单编号 FROM (select * from t_ord_productplan ) d left join t_sys_user u1 on  u1.fid=d.fcreatorid left join t_sys_user u2 on u2.fid=d.flastupdateuserid left join t_bd_customer c on c.fid=d.fcustomerid left join t_bd_custproduct p on p.fid=d.fcustproduct left join t_sys_user u3 on u3.fid=d.fauditorid left join t_pdt_productdef f on f.fid=d.fproductdefid left join t_sys_supplier s on s.fid=d.fsupplierid left join t_ord_saleorder r on r.fid=d.fparentorderid where 1=1 "+productPlanDao.QueryFilterByUser(request, "c.fid", "s.fid");
			ListResult result= productPlanDao.QueryFilterList(sql, request);
			ExcelUtil.toexcel(reponse,result);
		} catch (DJException e) {
			// TODO Auto-generated catch block
			reponse.getWriter().write(
					JsonUtil.result(false, e.getMessage(), "", ""));
		}

		return null;

	}
	
//	@RequestMapping(value = "/deleteProductPlan")
//	public String deleteProductPlan(HttpServletRequest request,
//			HttpServletResponse reponse) throws IOException {
//
//		String result = "";
//		String fidcls = request.getParameter("fidcls");
//		try {
//			String hql = "Delete FROM ProductPlan where fid in " + fidcls;
//			productPlanDao.ExecByHql(hql);
//			result = "{success:true,msg:'删除成功!'}";
//			reponse.setCharacterEncoding("utf-8");
//		} catch (Exception e) {
//			result = "{success:false,msg:'" + e.toString().replaceAll("'", "")
//					+ "'}";
//			log.error("DelUserList error", e);
//		}
//		reponse.getWriter().write(result);
//		return null;
//
//	}
	
	@RequestMapping(value = "/orderclose")
	public String orderclose(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {

		String result = "";
		String fidcls = request.getParameter("fidcls");
		fidcls="('"+fidcls.replace(",","','")+"')";
		
		try {
//			String hql = "select * from t_inv_storebalance where fbalanceqty > 0 and fproductplanid in " + fidcls;
//			List<HashMap<String,Object>> data= productPlanDao.QueryBySql(hql);
//			if (data.size()>0)
//			{
//				throw new DJException("有库存的制造商订单不能关闭！"); 
//			}
//			String sql = "select 1 from t_ord_productplan where fstate=1 and fid in " + fidcls;
			//已确认，入库数量为0，完成为否的不允许手动关闭，提示联系制造商商处理
			String sql = "select 1 from t_ord_productplan where faffirmed=1 and fisfinished=0 and fstockinqty=0 and fid in "+fidcls;
			if(productPlanDao.QueryExistsBySql(sql)){
//				throw new DJException("已确认的订单不能关闭！"); 
				throw new DJException("联系制造商处理！"); 

			}
			String hql = "update t_ord_productplan set fcloseed = 1 where fid in " + fidcls;
			productPlanDao.ExecBySql(hql);
			result = "{success:true,msg:'关闭成功!'}";
			reponse.setCharacterEncoding("utf-8");
		} catch (Exception e) {
			result = "{success:false,msg:'" + e.getMessage().toString().replaceAll("'", "")
					+ "'}";
			log.error("DelUserList error", e);
		}
		reponse.getWriter().write(result);
		return null;

	}

	@RequestMapping(value = "/UnOrderclose")
	public String UnOrderclose(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {

		String result = "";
		String fidcls = request.getParameter("fidcls");
		fidcls="('"+fidcls.replace(",","','")+"')";
		try {
			List<HashMap<String,Object>> checkvmiproduct= productPlanDao.QueryBySql("select fid,fnumber from t_ord_productplan opp where exists(select fid from t_pdt_vmiproductparam pvp where opp.fproductdefid=pvp.fproductid and opp.fsupplierid=pvp.fsupplierid and pvp.ftype=0 ) and fid in "+fidcls);
			if(checkvmiproduct.size()>0)
			{
				throw new DJException("单号："+checkvmiproduct.get(0).get("fnumber")+"是通知类型的产品，不能反关闭");
			}
			String hql = "update t_ord_productplan set fcloseed = 0 where fid in " + fidcls;
			productPlanDao.ExecBySql(hql);
			result = "{success:true,msg:'反关闭成功!'}";
			reponse.setCharacterEncoding("utf-8");
		} catch (Exception e) {
			result = "{success:false,msg:'" + e.getMessage().replaceAll("'", "")
					+ "'}";
			log.error("DelUserList error", e);
		}
		reponse.getWriter().write(result);
		return null;

	}
	
	/**
	 * 导入EAS--套装订单必须全部导入EAS系统;
	 * @param request
	 * @param reponse
	 * @return
	 * @throws IOException
	 * @throws SQLException 
	 * @throws DJException 
	 */
	/*@RequestMapping(value = "/ImportEAS")
	public String ImportEAS(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException, SQLException, DJException {
		reponse.setCharacterEncoding("utf-8");	
		if (isImportEASing)
		{
			reponse.getWriter().write(JsonUtil.result(false,"系统正忙，请稍后再试......." , "", ""));
			return null;
		}
		isImportEASing=true;
		String result = "";
		String fidcls = request.getParameter("fidcls");
		fidcls="('"+fidcls.replace(",","','")+"')";
		Connection conn=ServerContext.getOracleHelper().GetConn(request);
		PreparedStatement stmt = null;
		String forderidcls = "";
		String deliverid = "";
		String EASorderID = "";
		ArrayList<String> orderentryidcol = new ArrayList<String>();
		String EASorderentryID = "";
		String EASdeliversID = "";
		try {
			String userid = ((Useronline) request.getSession().getAttribute("Useronline")).getFuserid();
			String forderid = "";
			String forderentryid = "";
			int fseq;
			String fnumber = "";
			String fcustomerid = "";
			String farrivetime;
			String fincometime;
			int famount;
			int fordertype;
			String fsuitProductId = "";
			String fparentOrderEntryId = "";
			String fproductdefid = "";
			String fsupplierid = "";
			int fentryProductType=0;
			int fbrdboxtype;
			int fisassemblesuitsub=0;
			int famountrate;
			
			String deliverNum = "";
			String faddress = "";
			String flinkman = "";
			String flinkphone = "";
			
			//需从EAS查找的数据;
			String fsalemanid = "";
			String funitid = "";
			String favgarea = "";
			String fproductspec = "";
			String fmaterialspec = "";
			String fbalancespec = "";
			
			String sql = "SELECT forderid,fid,fseq,fnumber,fcustomerid,farrivetime,famount,fordertype,fsuitProductId,fparentOrderEntryId,fproductdefid,fsupplierid,fentryProductType,fimportEas,fimportEASuserid,fimportEastime,famountrate FROM t_ord_productplan where fsupplierid='39gW7X9mRcWoSwsNJhU12TfGffw=' and fid in " + fidcls +" and ifnull(fimportEAS,0)=0 order by forderid,fseq ";
			List<HashMap<String,Object>> data= productPlanDao.QueryBySql(sql);
			HashMap forderids = new HashMap();
			ArrayList<String> insertOrdersql = new ArrayList<String>();
			ArrayList<String> insertEntrysql = new ArrayList<String>();
			ArrayList<String> updatesql = new ArrayList<String>();
//			boolean issuit = false;
			if(data.size()==0){
				throw new DJException("没有'东力纸箱'制造商订单需要导入!");
			}
			for (int i = 0; i < data.size(); i++) {
				HashMap o = data.get(i);
				
				forderid = o.get("forderid").toString();
				forderentryid = o.get("fid").toString();
				fseq = new Integer(o.get("fseq").toString());
				fnumber = o.get("fnumber").toString();
				fcustomerid = o.get("fcustomerid").toString();
				DateFormat f = new SimpleDateFormat("yyyy-MM-dd");
				farrivetime = f.format((Date)o.get("farrivetime"));
				DateFormat fm = new SimpleDateFormat("yyyy-MM-dd HH");
				Date tempfincometime = fm.parse(o.get("farrivetime").toString());
				tempfincometime.setHours(16);
				fincometime = fm.format(tempfincometime);
				
				famount = new Integer(o.get("famount").toString());
				int fnewtype = new Integer(o.get("fordertype").toString());
//				if(fnewtype == 1 || fnewtype == 2){
//					fbrdboxtype = 1;	//纸板订单
//				}else{
					fbrdboxtype = 2;	//纸箱订单
//				}
					
				//2014-3-20修改为平台新增的EAS产品ID;
				fproductdefid = o.get("fproductdefid").toString();
				Productdef pdtinfo = productdefDao.Query(fproductdefid);
				
				if(fnewtype == 2){
					fordertype = 2;		//套装订单
					importSuitorder(userid,forderids,o,conn,stmt,pdtinfo.getFeasproductId());
					continue;
				}else{
					fordertype = 1;		//普通订单
				}
				
				if(o.get("fsuitProductId")!=null){
					fsuitProductId = o.get("fsuitProductId").toString();
				}
				
				if(o.get("fparentOrderEntryId")!=null){
					fparentOrderEntryId = o.get("fparentOrderEntryId").toString();
				}
				
				if(o.get("fsupplierid")==null){
					throw new DJException("供应商为空订单不能导入EAS系统！");
				}else{
					fsupplierid = o.get("fsupplierid").toString();
				}
				
				fentryProductType = new Integer(o.get("fentryProductType").toString());
				//是否组装套子件;
				if(fentryProductType == 6){
					fisassemblesuitsub = 1;
				}else{
					fisassemblesuitsub = 0;
				}
				famountrate = new Integer(o.get("famountrate").toString());
				
				//从EAS系统查找数据;
				stmt = conn.prepareStatement("select cb.fmateriallength cblength,cb.fmaterialwidth cbwidth,ct.fcutlength,ct.fcutwidth ,d.fid,d.Forderunitid,d.Farea,d.fmateriallength,d.fmaterialwidth ,d.fboxlength,d.fboxwidth,d.fboxheight ,d.fboardlength,d.fboardwidth ,cs.fpersonid fperson from t_pdt_productdef d left join T_BD_CustomerSaleInfo t on isnull(d.fcustomerid,'"+fcustomerid+"')=t.fcustomerid left join T_BD_CustomerSaler cs on t.Fid = cs.FCustomerSaleId left join T_PDT_CardboardWorkProc cb on cb.fproductid=d.fid left join T_PDT_CutWorkProc ct on ct.fproductid=d.fid where d.fid='"+pdtinfo.getFeasproductId()+"' ");
				ResultSet easOrder = stmt.executeQuery();
				if (easOrder.next())
				{
					fsalemanid = easOrder.getString("fperson");
					funitid = easOrder.getString("Forderunitid");
					favgarea = easOrder.getString("Farea");
					if(easOrder.getString("fboxlength")!=null && easOrder.getBigDecimal("fboxlength").compareTo(new BigDecimal(0))!=0){
						fproductspec = DataUtil.dFOneZero(easOrder.getString("fboxlength")) + "×" + DataUtil.dFOneZero(easOrder.getString("fboxwidth")) + "×" + DataUtil.dFOneZero(easOrder.getString("fboxheight"));
					}else if(easOrder.getString("fboardlength")!=null && easOrder.getBigDecimal("fboardlength").compareTo(new BigDecimal(0))!=0){
						fproductspec = DataUtil.dFOneZero(easOrder.getString("fboardlength")) + "×" + DataUtil.dFOneZero(easOrder.getString("fboardwidth"));
					}else{
						fproductspec = DataUtil.dFOneZero(easOrder.getString("fboxlength")) + "×" + DataUtil.dFOneZero(easOrder.getString("fboxwidth")) + "×" + DataUtil.dFOneZero(easOrder.getString("fboxheight"));
					}
					fbalancespec = DataUtil.dFOneZero(easOrder.getString("fmateriallength")) + "×" + DataUtil.dFOneZero(easOrder.getString("fmaterialwidth"));
					
					//落料规格优先取下料工序规格，如果空则取裁切工序规格，如果还空则取产品落料规格;
					if(easOrder.getString("cblength")!=null && easOrder.getBigDecimal("cblength").compareTo(new BigDecimal(0))!=0){
						fmaterialspec = DataUtil.dFOneZero(easOrder.getString("cblength")) + "×" + DataUtil.dFOneZero(easOrder.getString("cbwidth"));
					}else if(easOrder.getString("fcutlength")!=null && easOrder.getBigDecimal("fcutlength").compareTo(new BigDecimal(0))!=0){
						fmaterialspec = DataUtil.dFOneZero(easOrder.getString("fcutlength")) + "×" + DataUtil.dFOneZero(easOrder.getString("fcutwidth"));
					}else{
						fmaterialspec = fbalancespec;
					}
				}
				
				ServerContext svct = new ServerContext();
				EASorderID = svct.GetEASid("A8114365");
				EASorderentryID = svct.GetEASid("BF9A2C0D");
				for(int j=0;j<orderentryidcol.size();j++){
					if(orderentryidcol.get(j).equalsIgnoreCase(EASorderentryID)){
						EASorderentryID = svct.GetEASid("BF9A2C0D");
						j=0;
					}
				}
				orderentryidcol.add(EASorderentryID);
//				EASdeliversID = svct.GetEASid("57C24810");
				if(forderids.size()==0 || !forderids.containsKey(o.get("forderid").toString())){
					
					//新增EAS订单
					StringBuffer orderinfo = new StringBuffer("insert into t_ord_saleorder (fid,fnumber,fbizdate,fcreatorid,fcreatetime,flastupdateuserid,flastupdatetime,fcompanyid,fcontrolunitid,fsaleorgunitid,fcustomerid,fsalemanid,faudited,fordertype,fschemed,fsuitproductid,Funwonted,Fsample,fisclosed,fbrdboxtype,fistomainplan,fissendback,fisvirtual,fisboxorder) values ");
					orderinfo.append("('"+EASorderID+"','"+fnumber+"',sysdate,'00000000-0000-0000-0000-00000000000013B7DE7F',sysdate,'00000000-0000-0000-0000-00000000000013B7DE7F',sysdate,'O9P5KwEPEADgAAdDwKgCZ8znrtQ=','00000000-0000-0000-0000-000000000000CCE7AED4','O9P5KwEPEADgAAdDwKgCZ8znrtQ=','"+fcustomerid+"','"+fsalemanid+"',0,'"+fordertype+"',0,'"+fsuitProductId+"',0,0,0,'"+fbrdboxtype+"',0,0,0,0)");
					insertOrdersql.add(orderinfo.toString());
//					stmt = conn.prepareStatement(orderinfo.toString());
//					stmt.execute();
				}
				
				//新增EAS订单分录
				StringBuffer entryinfo = new StringBuffer("insert into t_ord_saleorderentry (fseq,fid,fparentid,funitid,fproductid,fplusbound,fnegabound,famount,fincometime,farrivedate,farrivetime,fschemed,fprove,fspecies,favgarea,famountrate,fproductspec,fmaterialspec,fbalancespec,funitarea2,fservicetime,fstockprice,fbalanceprice,fextended,fsample,fmanufqty,fisclose,fisclosed,fgaunitarea,ftestsum ");
				entryinfo.append(",fareaprice,funitprice,ftrafficprice,fcardbrdplantype,fiscbschemed,fiscombine,fisschedule,fistomainplan,fisinnerscheme,fisfaceplateplan,fisboxoutplan,fismainsuit,fentryproducttype,fparentorderentryid,ffinished,fentrynumber,fisprintorderfee,ffinishedamt,fparententrynumber,funitarea,fismanulschedule,fchangearrived,fisassemblesuitsub,finvalided,fsalorderaudit,fchecked,fscanflag) ");
				entryinfo.append("values ('"+fseq+"','"+EASorderentryID+"','"+EASorderID+"','"+funitid+"','"+pdtinfo.getFeasproductId()+"',0,0,'"+famount+"',to_date('"+fincometime+"','yyyy-mm-dd hh24'),to_date('"+farrivetime+"','yyyy-mm-dd'),19,0,0,0,'"+favgarea+"','"+famountrate+"','"+fproductspec+"','"+fmaterialspec+"','"+fbalancespec+"',0,to_date('"+farrivetime+"','yyyy-mm-dd'),0,0,0,0,'"+famount+"',0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,'"+fentryProductType+"','"+fparentOrderEntryId+"',0,'"+fnumber+(fseq>9?fseq:("0"+fseq))+"',0,0,'"+fnumber+"','"+favgarea+"',0,0,'"+fisassemblesuitsub+"',0,0,0,0) ");
				insertEntrysql.add(entryinfo.toString());
//				stmt = conn.prepareStatement(entryinfo.toString());
//				stmt.execute();
				
				//更新订单导入状态及反写EAS订单ID及订单分录ID;
//				String ordersql = "SET SQL_SAFE_UPDATES=0 ";
//				productPlanDao.ExecBySql(ordersql);
				String ordersql = "";
				ordersql = "update t_ord_productplan set fimportEas=1,fimportEasuserid='"+ userid 
						+ "',fimportEastime=now(),feasorderid = '"+EASorderID+"',feasorderentryid = '"+EASorderentryID+"' where fid = '" + forderentryid +"'";
				
				updatesql.add(ordersql);
//				productPlanDao.ExecBySql(ordersql);
				
				forderids.put(o.get("fid").toString(), null);
				forderids.put(o.get("forderid").toString(), null);
			}
			
			HashMap<String, Object> params = new HashMap<String, Object>();
			if(insertEntrysql!=null)
			{
				params.put("insertOrdersql", insertOrdersql);
				params.put("insertEntrysql", insertEntrysql);
				params.put("updatesql", updatesql);
				productPlanDao.ExecProductPlanDao(stmt,conn,params,1);
			}
		} catch (Exception e) {
			conn.rollback();
			ServerContext.getOracleHelper().CloseConn(request);
			isImportEASing=false;
			reponse.getWriter().write(JsonUtil.result(false,"导入数据失败！" + e.getMessage(), "", ""));
			return null;
		}
		conn.commit();
		ServerContext.getOracleHelper().CloseConn(request);
		isImportEASing=false;
		reponse.getWriter().write(JsonUtil.result(true, "订单导入EAS成功", "", ""));

		return null;

	}*/
//
//	private void importSuitorder(String userid, HashMap forderids, HashMap o, Connection conn, PreparedStatement stmt, String fproductdefid) throws Exception {
//		// TODO Auto-generated method stub
//		//套装
//		// 一次性获取所有级次的：套装+子产品
//		// 再按顺序加载即可
//		ServerContext svct = new ServerContext();
//		String fEASorderid = ServerContext.GetEASid("A8114365");
//		
//		int fseq;
//		String fcustomerid = "";
//		String farrivetime;
//		String fincometime;
//		int famount;
//		int fordertype = 2;
//		String fsuitProductId = "";
//		String fparentOrderEntryId = "";
//		String fparententrynumber = "";
//		String fsupplierid = "";
//		int fentryProductType=0;
//		int fbrdboxtype;
//		int fisassemblesuitsub=0;
//		int famountrate = 1;
//		
//		String deliverNum = "";
//		String faddress = "";
//		String flinkman = "";
//		String flinkphone = "";
//		
//		fcustomerid = o.get("fcustomerid").toString();
//		DateFormat f = new SimpleDateFormat("yyyy-MM-dd");
//		farrivetime = f.format((Date)o.get("farrivetime"));
//		DateFormat fm = new SimpleDateFormat("yyyy-MM-dd HH");
//		Date tempfincometime = fm.parse(o.get("farrivetime").toString());
//		tempfincometime.setHours(16);
//		fincometime = fm.format(tempfincometime);
//		String forderentryid = o.get("fid").toString();
//		fsupplierid = o.get("fsupplierid").toString();
//		famount = new Integer(o.get("famount").toString());
//		
//		int fnewtype = new Integer(o.get("fordertype").toString());
////		if(fnewtype == 1 || fnewtype == 2){
////			fbrdboxtype = 1;	//纸板订单
////		}else{
//			fbrdboxtype = 2;	//纸箱订单
////		}
//		
//		//需从EAS查找的数据;
//		String fsalemanid = "";
//		String funitid = "";
//		String favgarea = "";
//		String fproductspec = "";
//		String fmaterialspec = "";
//		String fbalancespec = "";
//		
//		List list = getAllProductSuit(svct,conn,stmt,fproductdefid);
//		HashMap subInfo = null;
////		String fordernumber = getFnumber(1,conn,stmt);
////		String fordernumber = productPlanDao.getFnumber("t_ord_productplan", "P", 4);
//		String fordernumber = o.get("fnumber").toString();
//		
//		ArrayList<String> insertOrdersql = new ArrayList<String>();
//		ArrayList<String> insertEntrysql = new ArrayList<String>();
//		ArrayList<String> updatesql = new ArrayList<String>();
//		if(list.size()==0){
//			throw new DJException("EAS系统没有对应的产品ID!");
//		}
//		for (int i = 0; i < list.size(); i++)
//		{
//			subInfo = (HashMap) list.get(i);
//			
//			fseq = i+1;
//			fentryProductType = new Integer(subInfo.get("entryProductType").toString());
//			famountrate = new Integer(subInfo.get("amountRate").toString());
//			//是否组装套子件;
//			if(fentryProductType == 6){
//				fisassemblesuitsub = 1;
//			}else{
//				fisassemblesuitsub = 0;
//			}
//			
//			if(i==0){
//				fsuitProductId = fproductdefid;
//			}else{
//				fparentOrderEntryId = subInfo.get("ParentOrderEntryId").toString();
//				fparententrynumber = fordernumber+(fseq>9?fseq:("0"+fseq));
//			}
//			int subamount = famount;
//			subamount = subamount * new Integer(subInfo.get("amountRate").toString());
//			fproductdefid = subInfo.get("fid").toString();
//			
//			//从EAS系统查找数据;
//			stmt = conn.prepareStatement("select cb.fmateriallength cblength,cb.fmaterialwidth cbwidth,ct.fcutlength,ct.fcutwidth ,d.fid,d.Forderunitid,d.Farea,d.fmateriallength,d.fmaterialwidth ,d.fboxlength,d.fboxwidth,d.fboxheight ,d.fboardlength,d.fboardwidth ,cs.fpersonid fperson from t_pdt_productdef d left join T_BD_CustomerSaleInfo t on d.fcustomerid=t.fcustomerid left join T_BD_CustomerSaler cs on t.Fid = cs.FCustomerSaleId left join T_PDT_CardboardWorkProc cb on cb.fproductid=d.fid left join T_PDT_CutWorkProc ct on ct.fproductid=d.fid where d.fid='"+fproductdefid+"' ");
//			ResultSet easOrder = stmt.executeQuery();
//			if (easOrder.next())
//			{
//				fsalemanid = easOrder.getString("fperson");
//				funitid = easOrder.getString("Forderunitid");
//				favgarea = easOrder.getString("Farea");
//				if(easOrder.getString("fboxlength")!=null && easOrder.getBigDecimal("fboxlength").compareTo(new BigDecimal(0))!=0){
//					fproductspec = DataUtil.dFOneZero(easOrder.getString("fboxlength")) + "×" + DataUtil.dFOneZero(easOrder.getString("fboxwidth")) + "×" + DataUtil.dFOneZero(easOrder.getString("fboxheight"));
//				}else if(easOrder.getString("fboardlength")!=null && easOrder.getBigDecimal("fboardlength").compareTo(new BigDecimal(0))!=0){
//					fproductspec = DataUtil.dFOneZero(easOrder.getString("fboardlength")) + "×" + DataUtil.dFOneZero(easOrder.getString("fboardwidth"));
//				}else{
//					fproductspec = DataUtil.dFOneZero(easOrder.getString("fboxlength")) + "×" + DataUtil.dFOneZero(easOrder.getString("fboxwidth")) + "×" + DataUtil.dFOneZero(easOrder.getString("fboxheight"));
//				}
//				fbalancespec = DataUtil.dFOneZero(easOrder.getString("fmateriallength")) + "×" + DataUtil.dFOneZero(easOrder.getString("fmaterialwidth"));
//				
//				//落料规格优先取下料工序规格，如果空则取裁切工序规格，如果还空则取产品落料规格;
//				if(easOrder.getString("cblength")!=null && easOrder.getBigDecimal("cblength").compareTo(new BigDecimal(0))!=0){
//					fmaterialspec = DataUtil.dFOneZero(easOrder.getString("cblength")) + "×" + DataUtil.dFOneZero(easOrder.getString("cbwidth"));
//				}else if(easOrder.getString("fcutlength")!=null && easOrder.getBigDecimal("fcutlength").compareTo(new BigDecimal(0))!=0){
//					fmaterialspec = DataUtil.dFOneZero(easOrder.getString("fcutlength")) + "×" + DataUtil.dFOneZero(easOrder.getString("fcutwidth"));
//				}else{
//					fmaterialspec = fbalancespec;
//				}
//			}
//			
////			EASdeliversID = svct.GetEASid("57C24810");
//			if(forderids.size()==0 || !forderids.containsKey(o.get("forderid").toString())){
//				
//				//新增EAS订单
//				StringBuffer orderinfo = new StringBuffer("insert into t_ord_saleorder (fid,fnumber,fbizdate,fcreatorid,fcreatetime,flastupdateuserid,flastupdatetime,fcompanyid,fcontrolunitid,fsaleorgunitid,fcustomerid,fsalemanid,faudited,fordertype,fschemed,fsuitproductid,Funwonted,Fsample,fisclosed,fbrdboxtype,fistomainplan,fissendback,fisvirtual,fisboxorder) values ");
//				orderinfo.append("('"+fEASorderid+"','"+fordernumber+"',sysdate,'00000000-0000-0000-0000-00000000000013B7DE7F',sysdate,'00000000-0000-0000-0000-00000000000013B7DE7F',sysdate,'O9P5KwEPEADgAAdDwKgCZ8znrtQ=','00000000-0000-0000-0000-000000000000CCE7AED4','O9P5KwEPEADgAAdDwKgCZ8znrtQ=','"+fcustomerid+"','"+fsalemanid+"',0,'"+fordertype+"',0,'"+fsuitProductId+"',0,0,0,'"+fbrdboxtype+"',0,0,0,0)");
//				insertOrdersql.add(orderinfo.toString());
////				stmt = conn.prepareStatement(orderinfo.toString());
////				stmt.execute();
//			}
//			
//			//新增EAS订单分录
//			StringBuffer entryinfo = new StringBuffer("insert into t_ord_saleorderentry (fseq,fid,fparentid,funitid,fproductid,fplusbound,fnegabound,famount,fincometime,farrivedate,farrivetime,fschemed,fprove,fspecies,favgarea,famountrate,fproductspec,fmaterialspec,fbalancespec,funitarea2,fservicetime,fstockprice,fbalanceprice,fextended,fsample,fmanufqty,fisclose,fisclosed,fgaunitarea,ftestsum ");
//			entryinfo.append(",fareaprice,funitprice,ftrafficprice,fcardbrdplantype,fiscbschemed,fiscombine,fisschedule,fistomainplan,fisinnerscheme,fisfaceplateplan,fisboxoutplan,fismainsuit,fentryproducttype,fparentorderentryid,ffinished,fentrynumber,fisprintorderfee,ffinishedamt,fparententrynumber,funitarea,fismanulschedule,fchangearrived,fisassemblesuitsub,finvalided,fsalorderaudit,fchecked,fscanflag) ");
//			entryinfo.append("values ('"+fseq+"','"+subInfo.get("orderEntryID").toString()+"','"+fEASorderid+"','"+funitid+"','"+fproductdefid+"',0,0,'"+subamount+"',to_date('"+fincometime+"','yyyy-mm-dd hh24'),to_date('"+farrivetime+"','yyyy-mm-dd'),19,0,0,0,'"+favgarea+"','"+famountrate+"','"+fproductspec+"','"+fmaterialspec+"','"+fbalancespec+"',0,to_date('"+farrivetime+"','yyyy-mm-dd'),0,0,0,0,'"+subamount+"',0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,"+((subInfo.get("entryProductType").toString().equals("1"))?"1":"0")+",'"+fentryProductType+"','"+fparentOrderEntryId+"',0,'"+fordernumber+(fseq>9?fseq:("0"+fseq))+"',0,0,'"+fparententrynumber+"','"+favgarea+"',0,0,'"+fisassemblesuitsub+"',0,0,0,0) ");
//			insertEntrysql.add(entryinfo.toString());
////			stmt = conn.prepareStatement(entryinfo.toString());
////			stmt.execute();
//			if(subInfo.get("entryProductType").toString().equals("1"))
//			{
//				String ordersql ="update t_ord_productplan set fimportEas=1,fimportEasuserid='" + userid
//						+ "',fimportEastime=now(),feasorderid = '"+fEASorderid+"',feasorderentryid = '"+subInfo.get("orderEntryID").toString()+"' where fid = '" + forderentryid +"'";
//				updatesql.add(ordersql);
////				productPlanDao.ExecBySql(ordersql);
//			}
//			forderids.put(o.get("fid").toString(), null);
//			forderids.put(o.get("forderid").toString(), null);
//		
//		}
//		
//		HashMap<String, Object> params = new HashMap<String, Object>();
//		if(insertEntrysql!=null)
//		{
//			params.put("insertOrdersql", insertOrdersql);
//			params.put("insertEntrysql", insertEntrysql);
//			params.put("updatesql", updatesql);
//			productPlanDao.ExecProductPlanDao(stmt,conn,params,1);
//		}
//	}
//	
//	/**
//	 * 获取多级套装+子件，并且对“分录的产品类型”赋值
//	 * @param svct 
//	 */
//	protected List getAllProductSuit(ServerContext svct, Connection conn,PreparedStatement stmt,String easproductid) throws Exception
//	{
////		ProductDefCollection productCols = new ProductDefCollection();
//		List list = new ArrayList();
////		StringBuffer oql = new StringBuffer("select *,Products.*,Products.product.* where id ='").append(id).append("'");
////		ProductDefInfo productInfo = getProductDefInfo(oql.toString());
//		stmt = conn.prepareStatement("select fnewtype,ftype,FCombination,FAssemble from t_pdt_productdef where fid = '"+easproductid+"'");
//		ResultSet productrs = stmt.executeQuery();
//		ArrayList<String> orderentryidcol = new ArrayList<String>();
//		if (productrs.next())
//		{
//			HashMap productInfo = new HashMap();
//			productInfo.put("fid",easproductid);
//			productInfo.put("amountRate",new Integer(1));
//			productInfo.put("entryProductType","1");
//			productInfo.put("ParentOrderEntryId",null);
//			productInfo.put("fnewtype", productrs.getString("fnewtype"));
//			productInfo.put("ftype", productrs.getString("ftype"));
//			productInfo.put("FCombination", productrs.getString("FCombination"));
//			productInfo.put("FAssemble", productrs.getString("FAssemble"));
//			getProductSuit(orderentryidcol,svct,conn,stmt,productInfo,list,null);
//		}
//		
//		return list;
//	}
//	
//	private void getProductSuit(ArrayList<String> orderentryidcol,ServerContext svct, Connection conn,PreparedStatement stmt,HashMap productInfo,List list,String parentEntryId) throws Exception
//	{
//		boolean isSuit = (productInfo.get("fnewtype").equals("2") || productInfo.get("fnewtype").equals("4") || productInfo.get("ftype").equals("2"));
//		String orderEntryID = ServerContext.GetEASid("BF9A2C0D");
//		for(int j=0;j<orderentryidcol.size();j++){
//			if(orderentryidcol.get(j).equalsIgnoreCase(orderEntryID)){
//				orderEntryID = ServerContext.GetEASid("BF9A2C0D");
//				j=0;
//			}
//		}
//		orderentryidcol.add(orderEntryID);
//		productInfo.put("orderEntryID",orderEntryID);
//		if(!isSuit){
//			//如果非套装，自己的分录ID，父分录Id取传入的参数，直接放入Collection
////			BOSUuid orderEntryID = BOSUuid.create(new SaleOrderEntryInfo().getBOSType());
//			productInfo.put("ParentOrderEntryId",parentEntryId);
//			list.add(productInfo);
//		}
//		else{
//			//如果是套装，自己的分录Id，父分录Id，总套为null，非总套取参数，先把自己放入Collection，再循环递归调用自己的子件
//
//			//如果不是首次进入递归，套装的“分录的产品类型”== 非总套
//			if(productInfo.get("entryProductType").toString().equals("1")){
//				//nothing
//			}
//			else{
//				productInfo.put("entryProductType","2");
//				productInfo.put("ParentOrderEntryId",parentEntryId);
//			}
//			
//			list.add(productInfo);
//			
//			//子件“分录的产品类型”
//			String subEntryProductType = "";
//			boolean isassemble = false;
//			if(productInfo.get("FCombination")!=null && productInfo.get("FCombination").toString().equals("1")){
//				//preSuitProductType = 1;
//				subEntryProductType = "7";	//合并下料子件
//			}
//			else if(productInfo.get("FAssemble")!=null && productInfo.get("FAssemble").toString().equals("1")){
//				//preSuitProductType = 2;
//				subEntryProductType = "6";	//组装套装子件
//				isassemble = true;
//			}
//			else {
//				//preSuitProductType = 0;
//				subEntryProductType = "5";	//普通套装子件
//			}
//			
////			ProductDefProductCollection subCols =  productInfo.getProducts();
//			stmt = conn.prepareStatement("select FAmount,FProductID from T_PDT_ProductDefProducts where Fparentid = '"+productInfo.get("fid")+"'");
//			ResultSet productrs = stmt.executeQuery();
//			while (productrs.next())
//			{
//				stmt = conn.prepareStatement("select fid,fnewtype,ftype,FCombination,FAssemble from t_pdt_productdef where fid = '"+productrs.getString("FProductID")+"'");
//				ResultSet subproductrs = stmt.executeQuery();
//				if (subproductrs.next())
//				{
//					HashMap subInfo = new HashMap();
//					subInfo.put("fid",subproductrs.getString("fid"));
//					subInfo.put("amountRate",productrs.getInt("FAmount") * new Integer(productInfo.get("amountRate").toString()));
//					subInfo.put("fnewtype", subproductrs.getString("fnewtype"));
//					subInfo.put("ftype", subproductrs.getString("ftype"));
//					subInfo.put("FCombination", subproductrs.getString("FCombination"));
//					subInfo.put("FAssemble", subproductrs.getString("FAssemble"));
//					//子件的“分录的产品类型”
//					subInfo.put("entryProductType",subEntryProductType);
//					if(isassemble){
//						subInfo.put("FisassembleSuitSub", Boolean.TRUE);
//					}else{
//						subInfo.put("FisassembleSuitSub", Boolean.FALSE);
//					}
//					
//					getProductSuit(orderentryidcol,svct,conn,stmt,subInfo,list,orderEntryID);
//				}
//			}
//			
//		}
//	}
	
	@RequestMapping(value = "/GetExceptionOrders")
	public String GetExceptionOrders(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		reponse.setCharacterEncoding("utf-8");
		try {
			String sql = "select d.fid ,d.fnumber,c.fname as cname,ifnull(p.fname,'') as pname,ifnull(f.fname,'') as fname,ifnull(s.fname,'') as sname,p.fspec,d.farrivetime,d.fbizdate,d.famount,d.flastupdateuserid,u2.fname u2_fname,d.flastupdatetime,d.fcreatetime,d.fcreatorid,u1.fname u1_fname,d.faudited,d.fauditorid,ifnull(u3.fname,'') fauditor,ifnull(d.faudittime,'') faudittime,d.fordertype,d.fsuitProductID,d.fseq,d.fparentOrderEntryId,d.forderid,d.fimportEAS,d.fproductdefid,d.fentryProductType,d.fstockoutqty,d.fstockinqty,d.fstoreqty,d.faffirmed,d.fassemble,d.fiscombinecrosssubs,r.fnumber salefnumber,d.fparentorderid,ifnull(d.fcloseed,0) as fcloseed FROM (select * from t_ord_productplan ) d left join t_sys_user u1 on  u1.fid=d.fcreatorid left join t_sys_user u2 on u2.fid=d.flastupdateuserid left join t_bd_customer c on c.fid=d.fcustomerid left join t_bd_custproduct p on p.fid=d.fcustproduct left join t_sys_user u3 on u3.fid=d.fauditorid left join t_pdt_productdef f on f.fid=d.fproductdefid left join t_sys_supplier s on s.fid=d.fsupplierid left join t_ord_saleorder r on r.fid=d.fparentorderid  where d.fcloseed=0 and d.farrivetime<now() and d.fstockinqty<d.famount ";
			ListResult result=productPlanDao.QueryFilterList(sql, request);
			reponse.getWriter().write(JsonUtil.result(true, "", result));
		} catch (DJException e) {
			// TODO Auto-generated catch block
			reponse.getWriter().write(
					JsonUtil.result(false, e.getMessage(), "", ""));
		}

		return null;

	}
	
	//收回制造商订单;
	/*@RequestMapping(value = "/productplanCancelImport")
	public String deliverorderCancelImport(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		String result = "";
		Connection conn=null;
		try {
			conn=ServerContext.getOracleHelper().GetConn(request);
			PreparedStatement stmt = null;
			
			if(isImportEASing)
			{
				throw new DJException("系统正在执行收回制造商订单，请稍后再试.....");
			}
			isImportEASing=true;
			reponse.setCharacterEncoding("utf-8");
			String fids = request.getParameter("fidcls");
			fids="('"+fids.replace(",","','")+"')";
		
			ProductPlan pdpinfo;
			String sql = " select fseq,fnumber,fid,feasorderid,feasorderentryid from t_ord_productplan where ifnull(fimportEas,0)=1 and fid in " + fids;
			List<HashMap<String, Object>> productplancls = productPlanDao.QueryBySql(sql);
			ArrayList<String> updatecol = new ArrayList<String>();
			String updatesql = "";
			if(productplancls.size()==0){
				throw new DJException("请选择已导入的制造商订单收回!");
			}
			for(int i=0;i<productplancls.size();i++)
			{
				// 第三方东经EAS系统是否有该配送单;
				stmt = conn.prepareStatement("select fid from t_ord_saleorder where fid='"+ productplancls.get(i).get("feasorderid") + "' ");
				ResultSet orderRs = stmt.executeQuery();
				if (orderRs.next()) {
					throw new DJException("东经EAS系统存在该制造商订单"+productplancls.get(i).get("fnumber")+"，收回请先删除东经EAS系统制造商订单！");
				}
				
				stmt = conn.prepareStatement("select fid from t_ord_saleorderentry where fid='"+ productplancls.get(i).get("feasorderentryid") + "' ");
				orderRs = stmt.executeQuery();
				if (orderRs.next()) {
					throw new DJException("东经EAS系统存在该制造商订单分录"+productplancls.get(i).get("fnumber")+"-"+productplancls.get(i).get("fseq")+"，收回请先删除东经EAS系统制造商订单分录！");
				}
				
				updatesql = "update t_ord_productplan set fimportEas=0,fimportEasuserid='',fimportEastime=null where fid='"+ productplancls.get(i).get("fid") + "' ";
				updatecol.add(updatesql);
			}
			
			HashMap<String, Object> params = new HashMap<String, Object>();
			if(updatecol!=null)
			{
				params.put("updatecol", updatecol);
				productPlanDao.ExecProductPlanDao(null,null,params,2);
			}
			result = JsonUtil.result(true,"收回制造商订单成功!", "", "");
			isImportEASing=false;
		} catch (Exception e) {
			result = "{success:false,msg:'" + e.getMessage() + "'}";
			if(!e.getMessage().startsWith("系统正在执行收回制造商订单，请稍后再试"))
			{
				isImportEASing=false;
			}
		}
		reponse.setCharacterEncoding("utf-8");
		reponse.getWriter().write(result);
		return null;

	}*/
	
	
	/**
	 * 
	 *
	 * @param request
	 * @param reponse
	 * @return
	 * @throws IOException
	 *
	 * @date 2014-9-25 上午10:39:36  (ZJZ)
	 */
	@RequestMapping(value = "/GetCustProductList")
	public String GetCustProductList(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		reponse.setCharacterEncoding("utf-8"); 
		try { 
		String fid=request.getParameter("fid");
			String sql =" select r.fcustproductid,r.famount,c.fname,c.fnumber,c.fspec from t_pdt_productrelation  e left join t_pdt_productrelationentry r on e.fid=r.fparentid left join t_bd_custproduct c on c.fid=r.fcustproductid  "+
					" left join t_ord_productplan p on p.fproductdefid=e.fproductid and p.fcustomerid=e.fcustomerid " +
					"where p.fid='"+fid+"'";
			List result=productPlanDao.QueryBySql(sql);
			if(result.size()<1)
			{
				sql=" select 1/r.famount famount ,e.fcustproductid,c.fname,c.fnumber,c.fspec from  t_pdt_custrelationentry  r left join t_pdt_custrelation e on r.fparentid=e.fid left join t_bd_custproduct c on c.fid=e.fcustproductid "+
			" left join t_ord_productplan p on p.fproductdefid=r.fproductid and p.fcustomerid=e.fcustomerid  " +
			"where p.fid='"+fid+"'" ;
				
				result=productPlanDao.QueryBySql(sql);
			}
			 if(result.size()<1)
		     {
				 reponse.getWriter().write(
							JsonUtil.result(false, "该产品没有对应的客户产品", "", ""));
		     }else{
		    	 reponse.getWriter().write(JsonUtil.result(true, "", "",result));
		     }

		} catch (DJException e) {
			// TODO Auto-generated catch block
			reponse.getWriter().write(
					JsonUtil.result(false, e.getMessage(), "", ""));
		}

		return null;

	}
	
/**
 * 制造商订单确认
 *
 * @param request
 * @param reponse
 * @return
 * @throws IOException
 *
 * @date 2014-10-29 下午1:44:42  
 */
	@RequestMapping(value = "/GetAuditedOrderProductPlans")
	public String GetAuditedOrderProductPlans(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		reponse.setCharacterEncoding("utf-8"); 
		try { 
			String sql = "select  p.fnumber pnumber,ss.fcharacter,substring(d.fcreatetime,1,16) fcreatetime,d.fstate,substring(d.faffirmtime,1,16) faffirmtime,u.fname faffirmer,ifnull(d.fdescription,'') fdescription,d.ftype,d.fid ,d.fnumber,c.fname as cname,ifnull(p.fname,'') as pname,ifnull(f.fname,'') as fname,ifnull(s.fname,'') as sname,p.fspec,substring(d.farrivetime,1,16) farrivetime,substring(d.fbizdate,1,16) fbizdate,d.famount,d.faudited,d.fordertype,d.fsuitProductID,d.fparentOrderEntryId,d.forderid,d.fimportEAS,d.fproductdefid,d.fentryProductType,d.fstockoutqty,d.fstockinqty,d.fstoreqty,d.faffirmed,d.fassemble,d.fiscombinecrosssubs,d.fparentorderid,ifnull(d.fcloseed,0) as fcloseed,ifnull(d.fboxlength,'') fboxlength,ifnull(d.fboxwidth,'') fboxwidth,ifnull(d.fboxheight,'') fboxheight,ifnull(pd.fname,'') fschemename,d.fpcmordernumber  FROM t_ord_productplan  d  left join t_bd_customer c on c.fid=d.fcustomerid left join t_bd_custproduct p on p.fid=d.fcustproduct  left join t_pdt_productdef f on f.fid=d.fproductdefid left join t_sys_supplier s on s.fid=d.fsupplierid left join t_ord_saleorder r on r.fid=d.fparentorderid left join t_ord_schemedesign pd on d.fschemedesignid=pd.fid left join t_sys_user u on u.fid=d.faffirmer LEFT JOIN `t_ord_schemedesignentry` ss ON ss.fparentid=d.fschemedesignid where 1=1 and ifnull(d.faudited,0)=1 and d.fboxtype<>1 "
					+ productPlanDao.QueryFilterByUser(request, null, "s.fid");

//			sql = MySimpleToolsZ.getMySimpleToolsZ().buildMySearchBoxMixedTypeSQL(request, sql);
			request.setAttribute("djsort","d.fcreatetime desc,d.fnumber");
			ListResult result=productPlanDao.QueryFilterList(sql, request);
			reponse.getWriter().write(JsonUtil.result(true, "", result));
		} catch (DJException e) {
			// TODO Auto-generated catch block
			reponse.getWriter().write(
					JsonUtil.result(false, e.getMessage(), "", ""));
		}

		return null;

	}
	
	/**
	 * 成品箱订单
	 *
	 * @param request
	 * @param reponse
	 * @return
	 * @throws IOException
	 *
	 * @date 2014-10-29 下午1:44:42  
	 */
		@RequestMapping(value = "/GetFinishedProductBoxOrdersList")
		public String GetFinishedProductBoxOrdersList(HttpServletRequest request,
				HttpServletResponse reponse) throws IOException {
			reponse.setCharacterEncoding("utf-8"); 
			try { 
				String sql = "select p.fisupdate,c.fid fcustomerid,d.fcreateboard,f.fid productid,da.fordernumber,da.faddress,ss.fcharacter,substring(d.fcreatetime,1,16) fcreatetime,d.fstate,substring(d.faffirmtime,1,16) faffirmtime,u.fname faffirmer,ifnull(da.fdescription,'') fdescription,d.ftype,d.fid ,d.fnumber,c.fname as cname,ifnull(p.fname,'') as pname,ifnull(f.fname,'') as fname,ifnull(s.fname,'') as sname,p.fspec,substring(d.farrivetime,1,16) farrivetime,substring(d.fbizdate,1,16) fbizdate,d.famount,d.faudited,d.fordertype,d.fsuitProductID,d.fparentOrderEntryId,d.forderid,d.fimportEAS,d.fproductdefid,d.fentryProductType,d.fstockoutqty,d.fstockinqty,d.fstoreqty,d.faffirmed,d.fassemble,d.fiscombinecrosssubs,d.fparentorderid,ifnull(d.fcloseed,0) as fcloseed,ifnull(d.fboxlength,'') fboxlength,ifnull(d.fboxwidth,'') fboxwidth,ifnull(d.fboxheight,'') fboxheight,ifnull(pd.fname,'') fschemename,d.fpcmordernumber  FROM t_ord_productplan d left join t_bd_customer c on c.fid=d.fcustomerid left join t_bd_custproduct p on p.fid=d.fcustproduct left join t_pdt_productdef f on f.fid=d.fproductdefid left join t_sys_supplier s on s.fid=d.fsupplierid left join t_ord_saleorder r on r.fid=d.fparentorderid left join t_ord_schemedesign pd on d.fschemedesignid=pd.fid left join t_sys_user u on u.fid=d.faffirmer LEFT JOIN `t_ord_schemedesignentry` ss ON ss.fparentid=d.fschemedesignid "
						+ "left join t_ord_deliverapply da on da.fid=d.fdeliverapplyid and da.fboxtype<>1 where d.fboxtype<>1 and ifnull(d.faudited,0)=1 "
						+ productPlanDao.QueryFilterByUser(request, null, "s.fid");

//				sql = MySimpleToolsZ.getMySimpleToolsZ().buildMySearchBoxMixedTypeSQL(request, sql);
				request.setAttribute("djsort","d.fcreatetime desc,d.fnumber");
				if("[]".equals(request.getParameter("Defaultfilter"))){
					sql += " and d.fcreatetime>=CURRENT_DATE()";
				}
				ListResult result=productPlanDao.QueryFilterList(sql, request);
				reponse.getWriter().write(JsonUtil.result(true, "", result));
			} catch (DJException e) {
				// TODO Auto-generated catch block
				reponse.getWriter().write(
						JsonUtil.result(false, e.getMessage(), "", ""));
			}

			return null;

		}
	
	@RequestMapping(value = "/AffirmOrderProductplantoexect")
	public String AffirmOrderProductplantoexect(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		try {
			String sql = "select d.ftype 订单类型 ,d.fnumber 制造商编码,c.fname as 客户名称,ifnull(p.fname,'') as 客户产品名称,ifnull(f.fname,'') as 产品名称,ifnull(s.fname,'') as 供应商名称 ,ifnull(p.fspec,'') 规格,d.farrivetime 送达时间 ,d.fbizdate 业务时间,d.famount 数量,d.faudited 审核,d.fordertype 产品类型,d.fimportEAS 导入EAS,d.fstockoutqty 出库,d.fstockinqty 入库,d.fstoreqty 存量,d.faffirmed 确认,ifnull(d.fcloseed,0) as 关闭 FROM (select * from t_ord_productplan ) d   left join t_bd_customer c on c.fid=d.fcustomerid left join t_bd_custproduct p on p.fid=d.fcustproduct  left join t_pdt_productdef f on f.fid=d.fproductdefid left join t_sys_supplier s on s.fid=d.fsupplierid left join t_ord_saleorder r on r.fid=d.fparentorderid where 1=1 and ifnull(d.faffirmed,0)<>1 and ifnull(d.faudited,0)=1 "+productPlanDao.QueryFilterByUser(request, "c.fid", "s.fid");
			ListResult result= productPlanDao.QueryFilterList(sql, request);
			ExcelUtil.toexcel(reponse,result);
		} catch (DJException e) {
			// TODO Auto-generated catch block
			reponse.getWriter().write(
					JsonUtil.result(false, e.getMessage(), "", ""));
		}

		return null;

	}
	//我的业务导出
	@RequestMapping(value = "/AffirmedOrderProductplantoexect")
	public String AffirmedOrderProductplantoexect(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		try {
			request.setAttribute("nolimit", 0);
			String sql = "select ifnull(d.fcreateboard,0) fcreateboard,f.fid productid,da.fordernumber,da.faddress,ss.fcharacter,substring(d.fcreatetime,1,16) fcreatetime,ifnull(d.fstate,0) fstate,substring(d.faffirmtime,1,16) faffirmtime,u.fname faffirmer,ifnull(da.fdescription,'') fdescription,d.ftype,d.fid ,d.fnumber,c.fname as cname,ifnull(p.fname,'') as pname,ifnull(f.fname,'') as fname,ifnull(s.fname,'') as sname,p.fspec,substring(d.farrivetime,1,16) farrivetime,substring(d.fbizdate,1,16) fbizdate,d.famount,d.faudited,d.fordertype,d.fsuitProductID,d.fparentOrderEntryId,d.forderid,d.fimportEAS,d.fproductdefid,d.fentryProductType,d.fstockoutqty,d.fstockinqty,d.fstoreqty,d.faffirmed,d.fassemble,d.fiscombinecrosssubs,d.fparentorderid,ifnull(d.fcloseed,0) as fcloseed,ifnull(d.fboxlength,'') fboxlength,ifnull(d.fboxwidth,'') fboxwidth,ifnull(d.fboxheight,'') fboxheight,ifnull(pd.fname,'') fschemename,d.fpcmordernumber  FROM t_ord_productplan d left join t_bd_customer c on c.fid=d.fcustomerid left join t_bd_custproduct p on p.fid=d.fcustproduct left join t_pdt_productdef f on f.fid=d.fproductdefid left join t_sys_supplier s on s.fid=d.fsupplierid left join t_ord_saleorder r on r.fid=d.fparentorderid left join t_ord_schemedesign pd on d.fschemedesignid=pd.fid left join t_sys_user u on u.fid=d.faffirmer LEFT JOIN `t_ord_schemedesignentry` ss ON ss.fparentid=d.fschemedesignid "
					+ "left join t_ord_deliverapply da on da.fid=d.fdeliverapplyid and da.fboxtype<>1 where d.fboxtype<>1 and ifnull(d.faudited,0)=1 "
					+ productPlanDao.QueryFilterByUser(request, null, "s.fid");
			request.setAttribute("nolimit", "true");
			request.setAttribute("djsort","d.fcreatetime desc,d.fnumber");
			switch (MySimpleToolsZ.getMySimpleToolsZ().judgeSearchType(request)) {
			case MySimpleToolsZ.TIME_SEARCH:
				sql += MySimpleToolsZ.getMySimpleToolsZ().buildDateBetweenSQLFragment(request);
				break;
			}
			if("[]".equals(request.getParameter("Defaultfilter"))){
				sql += " and d.fcreatetime>=CURRENT_DATE()";
			}
			ListResult result = MySimpleToolsZ.getMySimpleToolsZ().buildExcelListResult(sql, request, productPlanDao);
			String stateMap[] = new String[]{"未接收","已接收","已接收","已接收"};
			String map[] = new String[]{"未采购","暂存采购","线下采购"};
			List<HashMap<String, Object>> data = result.getData();
			for (HashMap<String, Object> hashMap : data) {
				
				hashMap.put("订单状态", stateMap[Integer.valueOf(hashMap.get("订单状态").toString())]);
				hashMap.put("生成纸板", map[Integer.valueOf(hashMap.get("生成纸板").toString())]);
				
			}
			List<String> order = MySimpleToolsZ.gainDataIndexList(request);
			
			ExcelUtil.toexcel(response, result,order);
			
		} catch (DJException e) {
			// TODO Auto-generated catch block
			response.getWriter().write(
					JsonUtil.result(false, e.getMessage(), "", ""));
		}

		return null;

	}
	
	//纸箱订单导出
		@RequestMapping(value = "/OrderAffirmedtoexect")
		public String OrderAffirmedtoexect(HttpServletRequest request,
				HttpServletResponse response) throws IOException {
			try {
				request.setAttribute("nolimit", 0);
//				String sql = "select ifnull(d.fcreateboard,0) fcreateboard,f.fid productid,da.fordernumber,da.faddress,ss.fcharacter,substring(d.fcreatetime,1,16) fcreatetime,ifnull(d.fstate,0) fstate,substring(d.faffirmtime,1,16) faffirmtime,u.fname faffirmer,ifnull(da.fdescription,'') fdescription,d.ftype,d.fid ,d.fnumber,c.fname as cname,ifnull(p.fname,'') as pname,ifnull(f.fname,'') as fname,ifnull(s.fname,'') as sname,p.fspec,substring(d.farrivetime,1,16) farrivetime,substring(d.fbizdate,1,16) fbizdate,d.famount,d.faudited,d.fordertype,d.fsuitProductID,d.fparentOrderEntryId,d.forderid,d.fimportEAS,d.fproductdefid,d.fentryProductType,d.fstockoutqty,d.fstockinqty,d.fstoreqty,d.faffirmed,d.fassemble,d.fiscombinecrosssubs,d.fparentorderid,ifnull(d.fcloseed,0) as fcloseed,ifnull(d.fboxlength,'') fboxlength,ifnull(d.fboxwidth,'') fboxwidth,ifnull(d.fboxheight,'') fboxheight,ifnull(pd.fname,'') fschemename,d.fpcmordernumber  FROM t_ord_productplan d left join t_bd_customer c on c.fid=d.fcustomerid left join t_bd_custproduct p on p.fid=d.fcustproduct left join t_pdt_productdef f on f.fid=d.fproductdefid left join t_sys_supplier s on s.fid=d.fsupplierid left join t_ord_saleorder r on r.fid=d.fparentorderid left join t_ord_schemedesign pd on d.fschemedesignid=pd.fid left join t_sys_user u on u.fid=d.faffirmer LEFT JOIN `t_ord_schemedesignentry` ss ON ss.fparentid=d.fschemedesignid "
//						+ "left join t_ord_deliverapply da on da.fid=d.fdeliverapplyid and da.fboxtype<>1 where d.fboxtype<>1 and ifnull(d.faudited,0)=1 "
//						+ productPlanDao.QueryFilterByUser(request, null, "s.fid");
				String sql = "select   p.fnumber pnumber,ss.fcharacter,substring(d.fcreatetime,1,16) fcreatetime,d.fstate,substring(d.faffirmtime,1,16) faffirmtime,u.fname faffirmer,ifnull(d.fdescription,'') fdescription,ifnull(d.ftype,0) ftype,d.fid ,d.fnumber,c.fname as cname,ifnull(p.fname,'') as pname,ifnull(f.fname,'') as fname,ifnull(s.fname,'') as sname,p.fspec,substring(d.farrivetime,1,16) farrivetime,substring(d.fbizdate,1,16) fbizdate,d.famount,d.faudited,d.fordertype,d.fsuitProductID,d.fparentOrderEntryId,d.forderid,ifnull(d.fimportEAS,0) fimportEAS,d.fproductdefid,d.fentryProductType,d.fstockoutqty,d.fstockinqty,d.fstoreqty,ifnull(d.faffirmed,0) faffirmed,d.fassemble,d.fiscombinecrosssubs,d.fparentorderid,ifnull(d.fcloseed,0) as fcloseed,ifnull(d.fboxlength,'') fboxlength,ifnull(d.fboxwidth,'') fboxwidth,ifnull(d.fboxheight,'') fboxheight,ifnull(pd.fname,'') fschemename,d.fpcmordernumber  FROM t_ord_productplan  d  left join t_bd_customer c on c.fid=d.fcustomerid left join t_bd_custproduct p on p.fid=d.fcustproduct  left join t_pdt_productdef f on f.fid=d.fproductdefid left join t_sys_supplier s on s.fid=d.fsupplierid left join t_ord_saleorder r on r.fid=d.fparentorderid left join t_ord_schemedesign pd on d.fschemedesignid=pd.fid left join t_sys_user u on u.fid=d.faffirmer LEFT JOIN `t_ord_schemedesignentry` ss ON ss.fparentid=d.fschemedesignid where 1=1 and ifnull(d.faudited,0)=1 and d.fboxtype<>1 "
						+ productPlanDao.QueryFilterByUser(request, null, "s.fid");
				request.setAttribute("nolimit", "true");
				request.setAttribute("djsort","d.fcreatetime desc,d.fnumber");
				switch (MySimpleToolsZ.getMySimpleToolsZ().judgeSearchType(request)) {
				case MySimpleToolsZ.TIME_SEARCH:
					sql += MySimpleToolsZ.getMySimpleToolsZ().buildDateBetweenSQLFragment(request);
					break;
				}
				
				ListResult result = MySimpleToolsZ.getMySimpleToolsZ().buildExcelListResult(sql, request, productPlanDao);
				String stateMap[] = new String[]{"待确认","已确认","部分入库","全部入库"};
				String map[] = new String[]{"否","是"};
				String map1[] = new String[]{"初始化","正常",""};
				String map2[] = new String[]{"","普通","套装",""};
				List<HashMap<String, Object>> data = result.getData();
				for (HashMap<String, Object> hashMap : data) {
					if(hashMap.containsKey("订单状态")) hashMap.put("订单状态", stateMap[Integer.valueOf(hashMap.get("订单状态").toString())]);
					if(hashMap.containsKey("确认"))hashMap.put("确认", map[Integer.valueOf(hashMap.get("确认").toString())]);
					if(hashMap.containsKey("导入EAS"))hashMap.put("导入EAS", map[Integer.valueOf(hashMap.get("导入EAS").toString())]);
					if(hashMap.containsKey("审核"))hashMap.put("审核", map[Integer.valueOf(hashMap.get("审核").toString())]);
					if(hashMap.containsKey("产品类型"))hashMap.put("产品类型", map2[Integer.valueOf(hashMap.get("产品类型").toString())]);
					if(hashMap.containsKey("订单类型"))hashMap.put("订单类型", map1[Integer.valueOf(hashMap.get("订单类型").toString())]);
				}
				List<String> order = MySimpleToolsZ.gainDataIndexList(request);
				
				ExcelUtil.toexcel(response, result,order);
				
			} catch (DJException e) {
				// TODO Auto-generated catch block
				response.getWriter().write(
						JsonUtil.result(false, e.getMessage(), "", ""));
			}

			return null;

		}
	/**
	 * 制造商出库
	 *
	 * @param request
	 * @param reponse
	 * @return
	 * @throws IOException
	 *
	 * @date 2014-10-20 下午4:10:17  
	 */
	@RequestMapping(value = "/GetOutProductPlans")
	public String GetOutProductPlans(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		reponse.setCharacterEncoding("utf-8"); 
		try { 
			String sql = "select IFNULL(d.fdescription,'') fdescription,d.ftype,d.fid ,d.fnumber,c.fname as cname,ifnull(p.fname,'') as pname,ifnull(f.fname,'') as fname,ifnull(s.fname,'') as sname,p.fspec,d.farrivetime,d.fbizdate,d.famount,d.flastupdateuserid,u2.fname u2_fname,d.flastupdatetime,d.fcreatetime,d.fcreatorid,u1.fname u1_fname,d.faudited,d.fauditorid,ifnull(u3.fname,'') fauditor,ifnull(d.faudittime,'') faudittime,d.fordertype,d.fsuitProductID,d.fseq,d.fparentOrderEntryId,d.forderid,d.fimportEAS,d.fproductdefid,d.fentryProductType,d.fstockoutqty,d.fstockinqty,d.fstoreqty,d.faffirmed,d.fassemble,d.fiscombinecrosssubs,r.fnumber salefnumber,d.fparentorderid,ifnull(d.fcloseed,0) as fcloseed FROM (select * from t_ord_productplan ) d left join t_sys_user u1 on  u1.fid=d.fcreatorid left join t_sys_user u2 on u2.fid=d.flastupdateuserid left join t_bd_customer c on c.fid=d.fcustomerid left join t_bd_custproduct p on p.fid=d.fcustproduct left join t_sys_user u3 on u3.fid=d.fauditorid left join t_pdt_productdef f on f.fid=d.fproductdefid left join t_sys_supplier s on s.fid=d.fsupplierid left join t_ord_saleorder r on r.fid=d.fparentorderid where 1=1  and  (d.fstoreqty>0 or d.fstoreqty<>0) and d.fboxtype<>1 "
					+ productPlanDao.QueryFilterByUser(request, null, "s.fid");

			// sql =
			// MySimpleToolsZ.getMySimpleToolsZ().buildMySearchBoxMixedTypeSQL(request,
			// sql);
			
			ListResult result=productPlanDao.QueryFilterList(sql, request);
			reponse.getWriter().write(JsonUtil.result(true, "", result));
		} catch (DJException e) {
			// TODO Auto-generated catch block
			reponse.getWriter().write(
					JsonUtil.result(false, e.getMessage(), "", ""));
		}

		return null;

	}
	
	@RequestMapping(value = "/GetSupplierStoreBalanceRpt")
	public String GetSupplierStoreBalanceRpt(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		reponse.setCharacterEncoding("utf-8");
		try {
			String sql = "select cname,fproductdefid,fname,fnumber,pid fid,pspec,forderunitid,fsupplierid,"
					+ "fstoreqty,ifnull(d.famount,0) fcommitted,ifnull(a.famount,0) fproduce,lname from "
					+ "(select p.fid,c.fname cname,p.fproductdefid ,f.fname fname,p.fnumber pid,f.fnumber fnumber,"
					+ "f.forderunitid,p.fsupplierid,l.fname lname,sum(fstoreqty) fstoreqty,"
					+ "case when ifnull(f.fboxlength,'')='' then "
					+ "concat(f.fmateriallength,'x',f.fmaterialwidth) when f.fboxlength=0 "
					+ "then concat(f.fmateriallength,'x',f.fmaterialwidth) "
					+ "else concat(f.fboxlength,'x',f.fboxwidth,'x',f.fboxheight) end pspec "
					+ "from t_ord_productplan  p "
					+ "left join  t_pdt_productdef f on f.fid=p.fproductdefid "
					+ "left join t_bd_customer c on p.fcustomerid=c.fid "
					+ "left join  t_sys_supplier l on l.fid=p.fsupplierid "
					+ "group by p.fproductdefid,p.fsupplierid order by p.fproductdefid) pp "
//					+ "left join (SELECT fcusproductid,famount,fplanid FROM t_ord_deliverorder where fmatched=1 and fouted=0) d on pp.fid=d.fplanid "
					+ "left join (SELECT sum(famount) famount,fproductid,fsupplierId spid FROM t_ord_deliverorder where fouted=0 and fclosed=0 group by fproductid,fsupplierId) d on d.fproductid=pp.fproductdefid and d.spid=pp.fsupplierid "
//					+ "left join (SELECT fid,famount FROM t_ord_productplan where faffirmed=1 and fstockinqty=0) a on a.fid=pp.fid "
					+ "left join (SELECT fsupplierid fspid,fproductdefid fpdtid,sum(famount) famount FROM t_ord_productplan where faffirmed=1 and fstockinqty=0 group by fsupplierid,fproductdefid) a on a.fspid=pp.fsupplierid and a.fpdtid= pp.fproductdefid "
					+ "where 1=1"
					+ productPlanDao.QueryFilterByUser(request, null,
							"fsupplierid");
			ListResult result = productPlanDao.QueryFilterList(sql, request);
			reponse.getWriter().write(JsonUtil.result(true, "", result));
		} catch (DJException e) {
			// TODO Auto-generated catch block
			reponse.getWriter().write(
					JsonUtil.result(false, e.getMessage(), "", ""));
		}

		return null;

	}

	@RequestMapping(value = "/GetSupplierStoreBalanceEdit")
	public String GetSupplierStoreBalanceEdit(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		reponse.setCharacterEncoding("utf-8");
		try {
			String sqlA = "select c.fname cname,ppp.fname pname,pp.fnumber pnumber,case when ifnull(ppp.fboxlength,'')='' then concat(ppp.fmateriallength,'x',ppp.fmaterialwidth) when ppp.fboxlength=0 then concat(ppp.fmateriallength,'x',ppp.fmaterialwidth)   else concat(ppp.fboxlength,'x',ppp.fboxwidth,'x',ppp.fboxheight) end pspec,case when ifnull(p.fproductplanid,'')='' then '调整入库' else '入库' end ftype,p.finqty outIn,p.fcreatetime pcreatetime,u.fname uname from t_inv_productindetail p left join t_ord_productplan pp on p.fproductplanid = pp.fid left join t_bd_customer c on pp.fcustomerid = c.fid left join t_pdt_productdef ppp on p.fproductid = ppp.fid left join t_sys_user u on u.fid=p.fcreatorid where 1=1 ";
			String sqlB = "select c.fname cname,ppp.fname pname,pp.fnumber pnumber,case when ifnull(ppp.fboxlength,'')='' then concat(ppp.fmateriallength,'x',ppp.fmaterialwidth) when ppp.fboxlength=0 then concat(ppp.fmateriallength,'x',ppp.fmaterialwidth)   else concat(ppp.fboxlength,'x',ppp.fboxwidth,'x',ppp.fboxheight) end pspec,case when ifnull(o.fproductplanid,'') = '' then '调整出库' else '销售出库' end ftype,o.foutqty outIn,o.fcreatetime pcreatetime,u.fname uname from  t_inv_outwarehouse o left join t_ord_productplan pp on o.fproductplanid = pp.fid left join t_bd_customer c on pp.fcustomerid = c.fid left join t_pdt_productdef ppp on o.fproductid = ppp.fid left join t_sys_user u on u.fid=o.fcreatorid where 1=1 ";
			sqlA = sqlA + productPlanDao.QueryFilterByUser(request, "pp.fcustomerid", "pp.fsupplierid");
			sqlB = sqlB + productPlanDao.QueryFilterByUser(request, "pp.fcustomerid", "pp.fsupplierid");
			String sql = "select * from ("+sqlA +" union "+ sqlB+") b where 1=1";
			ListResult result = productPlanDao.QueryFilterList(sql, request);
			reponse.getWriter().write(JsonUtil.result(true, "", result));
		} catch (DJException e) {
			// TODO Auto-generated catch block
			reponse.getWriter().write(
					JsonUtil.result(false, e.getMessage(), "", ""));
		}
		return null;
	}
	
	@RequestMapping(value = "/GetSupplierStoreBalancetoexct")
	public String GetSupplierStoreBalancetoexct(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		try {
//			request.setAttribute("nolimit", 0);
			String sql=" select fname 产品名称,lname  制造商名称 ,fstockoutqty 出库,fstockinqty 入库 ,fstoreqty 存量 "
					+" from (select p.fproductdefid ,f.fname fname,p.fsupplierid,l.fname lname,sum(fstockoutqty) fstockoutqty,sum(fstockinqty) fstockinqty,sum(fstoreqty) fstoreqty "
					+" from t_ord_productplan  p left join  t_pdt_productdef f on f.fid=p.fproductdefid left join  t_sys_supplier l on l.fid=p.fsupplierid group by p.fproductdefid,p.fsupplierid order by p.fproductdefid) pp"
					+" where 1=1 "+productPlanDao.QueryFilterByUser(request, null, "fsupplierid");		
			ListResult result= productPlanDao.QueryFilterList(sql, request);
			ExcelUtil.toexcel(reponse,result);
		} catch (DJException e) {
			// TODO Auto-generated catch block
			reponse.getWriter().write(
					JsonUtil.result(false, e.getMessage(), "", ""));
		}

		return null;

	}
	
	@RequestMapping(value = "/GetOutProductPlanstoexct")
	public String GetOutProductPlanstoexct(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		try {
			String sql = "select d.ftype 订单类型,d.fnumber 制造商编码,c.fname 客户名称,ifnull(p.fname,'') 客户产品名称,ifnull(f.fname,'') 产品名称,ifnull(s.fname,'') 供应商名称 ,ifnull(p.fspec,'') 规格,d.farrivetime 送达时间,d.fbizdate 业务时间 ,d.famount 数量, d.faudited  审核,d.fordertype 产品类型,d.fimportEAS 导入EAS ,d.fstockoutqty 出库 ,d.fstockinqty 入库 ,d.fstoreqty 存量,d.faffirmed 确认,ifnull(d.fcloseed,0) 关闭  FROM (select * from t_ord_productplan ) d  left join t_bd_customer c on c.fid=d.fcustomerid left join t_bd_custproduct p on p.fid=d.fcustproduct  left join t_pdt_productdef f on f.fid=d.fproductdefid left join t_sys_supplier s on s.fid=d.fsupplierid left join t_ord_saleorder r on r.fid=d.fparentorderid where 1=1  and  (d.fstoreqty>0 or d.fstoreqty<>0) "+productPlanDao.QueryFilterByUser(request, "c.fid", "s.fid");
			ListResult result= productPlanDao.QueryFilterList(sql, request);
			ExcelUtil.toexcel(reponse,result);
		} catch (DJException e) {
			// TODO Auto-generated catch block
			reponse.getWriter().write(
					JsonUtil.result(false, e.getMessage(), "", ""));
		}

		return null;

	}
	
	/**
	 * 导入EAS--套装订单必须全部导入EAS系统;
	 * @param request
	 * @param reponse
	 * @return
	 * @throws IOException
	 * @throws SQLException 
	 * @throws DJException 
	 */
	@RequestMapping(value = "/ImportEAS")
	public String ImportEAS(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException, SQLException, DJException {
		reponse.setCharacterEncoding("utf-8");	
		if (isImportEASing)
		{
			reponse.getWriter().write(JsonUtil.result(false,"系统正忙，请稍后再试......." , "", ""));
			return null;
		}
		try{
			isImportEASing=true;
			String result = "";
			String fidcls = request.getParameter("fidcls");
			fidcls="('"+fidcls.replace(",","','")+"')";
			String sqls = "select 1 from t_ord_productplan where fid in "+fidcls+" and fcloseed=1";
			if(productPlanDao.QueryExistsBySql(sqls)){
				throw new DJException("已关闭的不能导入接口");
			}
			sqls = "select 1 from t_ord_productplan where fid in "+fidcls+" and fstate=0";
			if(productPlanDao.QueryExistsBySql(sqls)){
				throw new DJException("订单未接收不能导入接口");
			}
			String	ordersql = "update t_ord_productplan set fimportEas=1 where  ifnull(fimportEas,0)=0 and fid in " + fidcls ;
			productPlanDao.ExecBySql(ordersql);
			productPlanDao.ExecBySql("update t_ord_deliverorder set fimportEas=1 where fboxtype = 1 and fplanid in"+fidcls);//纸板制造商订单导入EAS的时候，配送单也需导入
			isImportEASing=false;
			reponse.getWriter().write(JsonUtil.result(true, "订单导入成功", "", ""));
		} catch (Exception e) {
			isImportEASing=false;
			reponse.getWriter().write(JsonUtil.result(false,"导入数据失败！" + e.getMessage(), "", ""));
		}
		

		return null;

	}
	
	
	
	//收回制造商订单;
	@RequestMapping(value = "/productplanCancelImport")
	public String deliverorderCancelImport(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		String result = "";
		try {
			if(isImportEASing)
			{
				throw new DJException("系统正在执行收回制造商订单，请稍后再试.....");
			}
			isImportEASing=true;
			reponse.setCharacterEncoding("utf-8");
			String fids = request.getParameter("fidcls");
			fids="('"+fids.replace(",","','")+"')";
			String sql = " select fid from t_ord_productplan where ifnull(fimportEas,0)=1 and fid in " + fids;
			List<HashMap<String, Object>> productplancls = productPlanDao.QueryBySql(sql);
			if(productplancls.size()==0){
				throw new DJException("请选择已导入的制造商订单收回!");
			}
			String updatesql = "update t_ord_productplan set fimportEas=0,fissync=0 where ifnull(fimportEas,0)=1 and  fid in " +fids;
			productPlanDao.ExecBySql(updatesql);
			result = JsonUtil.result(true,"收回制造商订单成功!", "", "");
			isImportEASing=false;
		} catch (Exception e) {
			result = "{success:false,msg:'" + e.getMessage() + "'}";
			if(!e.getMessage().startsWith("系统正在执行收回制造商订单，请稍后再试"))
			{
				isImportEASing=false;
			}
		}
		reponse.setCharacterEncoding("utf-8");
		reponse.getWriter().write(result);
		return null;

	}
	@RequestMapping("GetProductfile")
	public void GetProductfile(HttpServletRequest request,
			HttpServletResponse response) throws IOException{
		String fid = request.getParameter("fid");
		try {
			String sql = "select * from t_ord_productdemandfile where fparentid = '"+fid+"'";
			ListResult list = productPlanDao.QueryFilterList(sql, request);
//			if(list.size()>0){
//				productdemandfileDao.downloadProductdemandFile(response,list.get(0).get("fid").toString());
//			}
			
//			for (HashMap<String,Object> recordT : list.getData()) {
//				String path = recordT.get("fimagePath").toString();
//				String newPath = path.substring(path.lastIndexOf("file/"));
//				recordT.put("fimagePath", newPath);
//			}
			
//			sql = MySimpleToolsZ.getMySimpleToolsZ().buildMySearchBoxMixedTypeSQL(request, sql);
			
			response.getWriter().write(JsonUtil.result(true, "", list));
		} catch (DJException e) {
			// TODO: handle exception
			response.getWriter().write(JsonUtil.result(false, e.getMessage(),"", ""));
		}
	}
	/**
	 * 根据方案ID查询对应的特性产品的附件
	 * @throws IOException 
	 */
	@RequestMapping("getProductFile")
	public void getProductFile(HttpServletRequest request,HttpServletResponse response) throws IOException{
		String fid = request.getParameter("fid");
		try {
			String sql = "select * from t_ord_productplan where fid='"+fid+"'";
			List<HashMap<String,Object>> list = productPlanDao.QueryBySql(sql);
			if(list.size()>0){
				if(!StringUtils.isEmpty(list.get(0).get("fschemedesignid"))){
					sql = "SELECT fid,fname,SUBSTRING(fpath,18) fpath FROM t_ord_productdemandfile WHERE fparentid IN(SELECT p.fid FROM t_pdt_productdef p INNER JOIN t_ord_schemedesignentry ss ON p.fcharacterid=ss.fid where ss.fparentid='"+list.get(0).get("fschemedesignid")+"')";
					list = productPlanDao.QueryBySql(sql);
				}else{
					sql = "SELECT fid,fname,SUBSTRING(fpath,18) fpath FROM t_ord_productdemandfile WHERE fparentid='"+list.get(0).get("fproductdefid")+"'";
					list = productPlanDao.QueryBySql(sql);
				}
			}
			response.getWriter().write(JsonUtil.result(true, "","", list));
		} catch (DJException e) {
			// TODO: handle exception
			response.getWriter().write(JsonUtil.result(false, e.getMessage(),"", ""));
		}
	}
	
	@RequestMapping("/GetProductplanofBanlances")
	public String GetProductplanofBanlances(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
//		request.setAttribute("djsort","p.fcreatetime desc");
		request.setAttribute("djsort","p.ffnumber desc");
		String sql = "select p.fid,p.fnumber,f.fname ,fcharacter,p.famount,p.fstoreqty,p.fallotnum, case when fstoreqty = 0 then 0 else if(p.famount-fallotnum > fstoreqty,fstoreqty,p.famount-fallotnum) end balanceqty "
				+ " from ("
				+ " select p.fid,p.fnumber,substring(p.fnumber,2) ffnumber,p.famount,0 fallotnum, ifnull(p.fstoreqty,0) fstoreqty,p.fproductdefid,p.fsupplierid from t_ord_productplan p where not EXISTS  ( select * from (select distinct p.fid from  t_ord_deliverorder o  left join t_ord_productplan p on o.fplanid=p.fid where   ifnull(fplanid, '') <> '' and foutqty > 0) s where  p.fid=s.fid )"
				+ " union "
				+ " select p.fid,p.fnumber,substring(p.fnumber,2) ffnumber,p.famount, sum(foutqty) fallotnum, ifnull(p.fstoreqty,0) fstoreqty,p.fproductdefid,p.fsupplierid from  t_ord_deliverorder o left join t_ord_productplan p on o.fplanid=p.fid where   ifnull(fplanid, '') <> '' and foutqty > 0 group by fplanid having sum(foutqty)<p.famount ) p"
				+ " left join t_pdt_productdef f ON f.fid = p.fproductdefid where 1=1";
		sql=sql +productPlanDao.QueryFilterByUser(request, null, "p.fsupplierid");
		ListResult result;
		reponse.setCharacterEncoding("utf-8");
		try {
			result = productPlanDao.QueryFilterList(sql, request);
			reponse.getWriter().write(JsonUtil.result(true, "", result));
		} catch (DJException e) {
			reponse.getWriter().write(
					JsonUtil.result(false, e.getMessage(), "", ""));
		}

		return null;
	}
	
	@RequestMapping(value = "/ProductplanofBanlancestoexcel")
	public String ProductplanofBanlancestoexcel(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		try {
			request.setAttribute("djsort","p.ffnumber desc");
//			request.setAttribute("djsort","p.fcreatetime desc");
			String sql = "select p.fnumber 制造商订单号,f.fname 包装物名称,fcharacter 规格,p.famount 订单数量 ,fallotnum 发货数量,p.fstoreqty 实时库存,case when fstoreqty=0 then 0 else if(p.famount-fallotnum>fstoreqty,fstoreqty,p.famount-fallotnum)end 结算库存  "
					+ " from ("
					+ " select p.fid,p.fnumber,substring(p.fnumber,2) ffnumber,p.famount,0 fallotnum, ifnull(p.fstoreqty,0) fstoreqty,p.fproductdefid,p.fsupplierid from t_ord_productplan p where not EXISTS  ( select * from (select distinct p.fid from  t_ord_deliverorder o  left join t_ord_productplan p on o.fplanid=p.fid where   ifnull(fplanid, '') <> '' and foutqty > 0) s where  p.fid=s.fid )"
					+ " union "
					+ " select p.fid,p.fnumber,substring(p.fnumber,2) ffnumber,p.famount, sum(foutqty) fallotnum, ifnull(p.fstoreqty,0) fstoreqty,p.fproductdefid,p.fsupplierid from  t_ord_deliverorder o left join t_ord_productplan p on o.fplanid=p.fid where   ifnull(fplanid, '') <> '' and foutqty > 0 group by fplanid having sum(foutqty)<p.famount ) p"
					+ " left join t_pdt_productdef f ON f.fid = p.fproductdefid where 1=1";
			sql=sql +productPlanDao.QueryFilterByUser(request, null, "p.fsupplierid");
			ListResult result= productPlanDao.QueryFilterList(sql, request);
			ExcelUtil.toexcel(reponse,result,"结算库存明细表");
		} catch (DJException e) {
			// TODO Auto-generated catch block
			reponse.getWriter().write(
					JsonUtil.result(false, e.getMessage(), "", ""));
		}

		return null;

	}
	
	

	@RequestMapping("/GetProductplanofBanlancesDetails")
	public String GetProductplanofBanlancesDetails(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
	String fid=request.getParameter("fid");
		String sql="select p.fid,p.fnumber,f.fname,f.fcharacter,s.ftype,s.famount,u.fname fcreator,ifnull(date_format(s.fcreatetime,'%Y-%m-%d %H:%s'),'') fcreatetime from (" 
				+" select fproductplanid,'入库' ftype ,finqty famount,fcreatorid,fcreatetime from t_inv_productindetail  "
				+" where fproductplanid='"+fid+"'"
				+" union "
				+" select fproductplanid,'出库' ftype,foutqty famount,fcreatorid,fcreatetime  from t_inv_outwarehouse  	" 
				+" where fproductplanid='"+fid+"' ) s left join t_ord_productplan p  on p.fid=s.fproductplanid"
				+" left join t_pdt_productdef f on f.fid=p.fproductdefid left join t_sys_user u  on u.fid=s.fcreatorid";
		ListResult result;
		reponse.setCharacterEncoding("utf-8");
		try {
			result = productPlanDao.QueryFilterList(sql, request);
			reponse.getWriter().write(JsonUtil.result(true, "", result));
		} catch (DJException e) {
			reponse.getWriter().write(
					JsonUtil.result(false, e.getMessage(), "", ""));
		}

		return null;
	}
	@RequestMapping(value = "/GetCProductPlanList")
	public String GetCProductPlanList(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		reponse.setCharacterEncoding("utf-8"); 
		try { 
			String andsql = "";
			String defaultfilter = request.getParameter("defaultfilter");
			String Defaultmaskstring = request.getParameter("defaultmaskstring");
			if(!StringUtils.isEmpty(defaultfilter)){

				JSONArray j = JSONArray.fromObject(defaultfilter);
				for (int i = j.size() - 1; i >= 0; i--) {
					JSONObject o = j.getJSONObject(i);
					defaultfilter = "";
					if (o.getString("CompareType").contains("like")) {
						defaultfilter = o.getString("myfilterfield") + " "
								+ o.getString("CompareType") + " '%"
								+ o.getString("value")+"%'";
						Defaultmaskstring = Defaultmaskstring.replace("#" + i,
								defaultfilter);
					
					} else if (o.getString("CompareType").contains("null")) {
						defaultfilter = o.getString("myfilterfield") + " "
								+ o.getString("CompareType") + " ";
						Defaultmaskstring = Defaultmaskstring.replace("#" + i,
								defaultfilter);
					} else {
						defaultfilter = o.getString("myfilterfield") + " "
								+ o.getString("CompareType") + " '%"
								+ o.getString("value") +"%'";
						Defaultmaskstring = Defaultmaskstring.replace("#" + i,
								defaultfilter);
					}
				} 
			}
		if(StringUtils.isEmpty(Defaultmaskstring)){
			Defaultmaskstring = " d.fstate != 3 and d.fcloseed != 1";
		}
			request.setAttribute("djsort", "d.fnumber desc");
			String sql = "SELECT da.fdescription fdescription,u2.fname faffirmer,d.`faffirmtime`,d.flastupdatetime,d.flastupdateuserid,d.faffirmed,d.fcloseed,d.fid,d.fnumber,IFNULL(s.fname, '') AS sname,c.fname AS cname,d.ftype,d.fstate,concat(f.fname,' ',f.fnumber,' ','/',f.flayer,f.ftilemodelid) AS fname,da.`fboxmodel`,da.`fboxlength`,da.`fboxheight`,da.`fboxwidth`,da.`fmateriallength`,"+
						" da.`fmaterialwidth`,da.`fstavetype`,da.`fvformula`,da.`fhformula`,da.`fseries`,da.`famount`,da.`famountpiece`,d.`fstockinqty`,d.`fstockoutqty`,da.`farrivetime`,d.fimportEAS,d.fcreatetime,u1.fname fcreatorid FROM"+
						" t_ord_productplan d LEFT JOIN t_sys_user u2 ON u2.fid = d.`faffirmer` LEFT JOIN t_sys_user u1 ON u1.fid = d.fcreatorid LEFT JOIN t_bd_customer c ON c.fid = d.fcustomerid  LEFT JOIN t_sys_supplier s "+
						" ON s.fid = d.fsupplierid INNER JOIN t_ord_deliverapply da ON da.fid=d.fdeliverapplyid LEFT JOIN t_pdt_productdef f ON f.fid = da.`fmaterialfid` where 1 = 1  AND d.fboxtype = 1 and("+Defaultmaskstring+")"+productPlanDao.QueryFilterByUser(request, null, "s.fid");
			ListResult result=productPlanDao.QueryFilterList(sql, request);
			reponse.getWriter().write(JsonUtil.result(true, "", result));
		} catch (DJException e) {
			// TODO Auto-generated catch block
			reponse.getWriter().write(
					JsonUtil.result(false, e.getMessage(), "", ""));
		}

		return null;

	}
	@RequestMapping(value = "/extportCProductPlanList")
	public String extportCProductPlanList(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		reponse.setCharacterEncoding("utf-8"); 
		try { 
			String sql = "SELECT d.fnumber 制造商订单号,IFNULL(s.fname, '') AS 制造商,c.fname AS 客户名称,CASE d.ftype  WHEN 1 THEN '客户订单' ELSE '平台订单' END 订单来源,d.fstate 订单状态,IFNULL(f.fname, '') AS 材料,da.`fboxmodel` 箱型,CONCAT_WS('x',da.`fboxlength`, da.`fboxwidth`,da.`fboxheight`)  纸箱规格,CONCAT_WS('x',da.`fmateriallength`, da.`fmaterialwidth`)  下料规格,"+
						" da.`fstavetype` 压线方式,da.`fvformula` 压线公式长,da.`fhformula` 压线公式高,da.`fseries` 成型方式,da.`famount` 配送数量只,da.`famountpiece` 配送数量片,d.`fstockinqty` 入库数量,d.`fstockoutqty` 出库数量,da.`farrivetime` 配送时间,case d.fimportEAS when 1 then '是' else '否' end 导入接口,d.fcreatetime 创建时间,u1.fname  创建人 FROM"+
						" t_ord_productplan d LEFT JOIN t_sys_user u1 ON u1.fid = d.fcreatorid LEFT JOIN t_bd_customer c ON c.fid = d.fcustomerid  LEFT JOIN t_sys_supplier s "+
						" ON s.fid = d.fsupplierid INNER JOIN t_ord_deliverapply da ON da.fid=d.fdeliverapplyid LEFT JOIN t_pdt_productdef f ON f.fid = da.`fmaterialfid` where 1 = 1  AND d.fboxtype = 1 "+productPlanDao.QueryFilterByUser(request, "c.fid", "s.fid");
			request.setAttribute("notlimit", 0);
			ListResult result=productPlanDao.QueryFilterList(sql, request);
			ExcelUtil.toexcel(reponse, result);
//			reponse.getWriter().write(JsonUtil.result(true, "", result));
		} catch (DJException e) {
			// TODO Auto-generated catch block
			reponse.getWriter().write(
					JsonUtil.result(false, e.getMessage(), "", ""));
		}

		return null;

	}
	@RequestMapping(value = "/GetOrderProductTable")
	public String GetOrderProductTable(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		reponse.setCharacterEncoding("utf-8"); 
		try { 
			String sql = "select * from (select s.fnumber,s.fname,count(s.fname) fcount,sum(d.famount) fsum,d.faffirmtime,d.fcreatetime from t_ord_productplan d left join t_sys_supplier s on d.fsupplierid=s.fid where 1=1 ";
			switch (MySimpleToolsZ.getMySimpleToolsZ().judgeSearchType(request)) {
			case MySimpleToolsZ.TIME_SEARCH:
				sql += MySimpleToolsZ.getMySimpleToolsZ().buildDateBetweenSQLFragment(request);
				break;
			}
			sql += " group by s.fid) a where 1=1";
//			request.setAttribute("djgroup", "s.fid");
			ListResult result=productPlanDao.QueryFilterList(sql, request);
			reponse.getWriter().write(JsonUtil.result(true, "", result));
		} catch (DJException e) {
			// TODO Auto-generated catch block
			reponse.getWriter().write(
					JsonUtil.result(false, e.getMessage(), "", ""));
		}

		return null;

	}
	@RequestMapping(value = "/ExcelOrderProductTable")
	public void ExcelOrderProductTable(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException{
		try {
			String sql = "select * from (select s.fnumber,s.fname,count(s.fname) fcount,sum(d.famount) fsum,d.faffirmtime from t_ord_productplan d left join t_sys_supplier s on d.fsupplierid=s.fid where 1=1 ";
			switch (MySimpleToolsZ.getMySimpleToolsZ().judgeSearchType(request)) {
			case MySimpleToolsZ.TIME_SEARCH:
				sql += MySimpleToolsZ.getMySimpleToolsZ().buildDateBetweenSQLFragment(request);
				break;
			}
			sql += " group by s.fid) a where 1=1";
			
			ListResult result = MySimpleToolsZ.getMySimpleToolsZ().buildExcelListResult(sql, request, productPlanDao);
			
			//处理渲染
//			String[] stateMap =new ["1","2"];
			List<HashMap<String, Object>> data = result.getData();
			
//			for (HashMap<String, Object> hashMap : data) {
//				
//				hashMap.put("订单状态", stateMap.get(hashMap.get("订单状态")));
//				
//			}
			

			List<String> order = MySimpleToolsZ.gainDataIndexList(request);
			
			ExcelUtil.toexcel(reponse, result, order);
		} catch (DJException e) {
			// TODO: handle exception
			reponse.getWriter().write(
					JsonUtil.result(false, e.getMessage(), "", ""));
		}
	}
	@RequestMapping("productPlanToDeliversBoard")
	public void productPlanToDeliversBoard(HttpServletRequest request,HttpServletResponse response) throws IOException{
		try {
			String str = productPlanDao.ExecToDeliversBoard(request);
			response.getWriter().write(JsonUtil.result(true,"生成纸板订单成功！","",str));
		} catch (DJException e) {
			// TODO: handle exception
			response.getWriter().write(JsonUtil.result(false,e.getMessage(),"",""));
		}
	}
	
	/**
	 * 我的业务打印查询
	 * @throws IOException 
	 */
	@RequestMapping("getPrintInfoWithProductplan")
	public void getPrintInfoWithProductplan(HttpServletRequest request,HttpServletResponse response) throws IOException{
		String fidcls = request.getParameter("fid");
		fidcls="('"+fidcls.replace(",","','")+"')";
		try {
			String sql = "SELECT d.fid ,d.fcustproduct, s.fname  suppliername,c.fname customername,d.fnumber,f.fname productname,IFNULL(f.fcharacter,'') fcharacter,d.famount, CONCAT(SUBSTRING(d.farrivetime,1,10),' ',IF(DATE_FORMAT(d.farrivetime,'%p')='AM','上午','下午'))farrivetime,IFNULL(fqueryname,'') materialname,IFNULL(sm.fname,'') materialsuppliername,SUBSTRING(mp.farrivetime,1,10) materialfarrivetime ,CONCAT(f.FMATERIALLENGTH,'X',f.FMATERIALWIDTH,' ',IFNULL(f.fseries,'')) boxpie,f.fhformula,f.fvformula,f.fprintcolor,ifnull(f.fworkproc,'') fworkproc,IF(ifnull(f.fpackway,'')='','',CONCAT('打包方式:',f.fpackway)) fpackway, IF(ifnull(f.fcbnumber,'')='','',CONCAT('冲版编号:',f.fcbnumber)) fcbnumber,IF(ifnull(f.fybnumber,'')='','',CONCAT('印刷编号:',f.fybnumber)) fybnumber,concat(if(f.fdescription='','',concat('产品要求:<br/>',f.fdescription,'<br/><br>')),IF(d.fdescription='','',CONCAT('订单要求：<br/>',d.fdescription))) fdescription "
					+" FROM t_ord_productplan d"
					+" INNER JOIN t_pdt_productdef f ON f.fid=d.fproductdefid "
					+" INNER JOIN t_sys_supplier s ON s.fid=d.fsupplierid"
					+" INNER JOIN t_bd_customer c ON c.fid=d.fcustomerid"
					+" LEFT JOIN t_sys_supplier sm ON sm.fid=f.fmtsupplierid"
					+" LEFT JOIN t_ord_deliverapply mp ON mp.fid=d.fdeliversboardid"
					+" WHERE d.fid in "+fidcls;
			List<HashMap<String,Object>> list = productPlanDao.QueryBySql(sql);
			ArrayList workpros =new ArrayList();
			String stateMap[] = new String[]{"fworkproc","fpackway","fcbnumber","fybnumber"};
			String fworkproc="";
			for (HashMap<String, Object> record : list) 
			{
					fworkproc="";
					for(String value:stateMap)
					{
						if(!"".equals((String)record.get(value))){
							workpros.add((String)record.get(value));
						}
					}
					//构造工艺路线
					for(int i=0;i<workpros.size();i++)
					{
						if(i==2&&i!=workpros.size())
						{
							fworkproc+="<br/>";
						}else if(i>0&&i!=workpros.size())
						{
							fworkproc+=",";
						}
						fworkproc+=workpros.get(i);
					}
				record.put("fworkproc",fworkproc);
				String pathT = UploadFile.getQuickFilePath(request,(String)record.get("fcustproduct"));
				record.put("imgpath",StringUtils.isEmpty(pathT)?"":pathT);
			}
	
			response.getWriter().write(JsonUtil.result(true, "",""+list.size(), list));
		} catch (DJException e) {
			// TODO: handle exception
			response.getWriter().write(JsonUtil.result(false, e.getMessage(),"", ""));
		}
	}
	
	/**
	 * 新“我的业务”数据来源
	 * @date 2015-07-20 13:34:57
	 */
		@RequestMapping(value = "/GetMyProductBoxOrdersList")
		public String  GetMyProductBoxOrdersList(HttpServletRequest request,
				HttpServletResponse reponse) throws IOException {
			reponse.setCharacterEncoding("utf-8"); 
			try { 
				String sql = "select pp.fid fid,pp.fnumber ppnumber,pp.fcreatetime ppcreatetime,pp.fstate,cust.fname custname,cpdt.fid cpid,";
				sql += " pdt.fisboardcard,IFNULL(pdt.fname,'') pdtname,cpdt.fspec pdtspec,IFNULL(mpdt.fname,'') mpdtname,";
				sql += " pp.famount amount,DATE_FORMAT(pp.farrivetime,'%Y-%m-%d %p') arrivetime,IFNULL(sup.fname, '') supname,";
				sql += " pdt.fid pdtid,IFNULL(pdt.fmaterialinfo,'') materialinfo,case when pdt.fmateriallength = 0 THEN '' else CONCAT_WS('X',pdt.fmateriallength,pdt.fmaterialwidth) end as materiaspec,";
				sql += " IFNULL(pdt.fseries,'') pdtseries,IFNULL(pdt.fprintcolor,'') pdtcolor,IFNULL(pdt.fworkproc,'') pdtworkproc,";
				sql += " IFNULL(cpdt.fdescription,'') pdtdesc,pp.fcreateboard createboard,IFNULL(da.faddress,'') address,pp.fpcmordernumber ordernumber";
				sql += " from t_ord_productplan pp LEFT JOIN t_bd_customer cust ON cust.fid = pp.fcustomerid ";
				sql += " LEFT JOIN t_bd_custproduct cpdt ON cpdt.fid = pp.fcustproduct LEFT JOIN t_pdt_productdef pdt ";
				sql += " ON pdt.fid = pp.fproductdefid LEFT JOIN t_pdt_productdef mpdt ON mpdt.fid = pdt.fmaterialfid";
				sql += " LEFT JOIN t_sys_supplier sup ON sup.fid = mpdt.fsupplierid LEFT JOIN t_ord_deliverapply da ";
				sql += " ON da.fid = pp.fdeliverapplyid AND da.fboxtype <> 1 ";
				sql += " where pp.fboxtype <> 1 AND IFNULL(pp.faudited, 0) = 1 ";
				request.setAttribute("djsort","pp.fcreatetime desc,pp.fnumber");
				if("[]".equals(request.getParameter("Defaultfilter"))){
					sql += " and pp.fcreatetime>=CURRENT_DATE()";
				}
				ListResult result=productPlanDao.QueryFilterList(sql, request);
				reponse.getWriter().write(JsonUtil.result(true, "", result));
			} catch (DJException e) {
				reponse.getWriter().write(
						JsonUtil.result(false, e.getMessage(), "", ""));
			}
			return null;
		}
		
		/**
		 * @date 2015-07-24  纸箱信息是否维护
		 * 产品中fisboardcard    -1: 非产品档案维护的产品;        0:内盒信息         1：纸箱信息
		 */
			@RequestMapping(value = "/Getpdtboardcard")
			public String  Getpdtboardcard(HttpServletRequest request,
					HttpServletResponse reponse) throws IOException {
				String fid = request.getParameter("pdtid");
				try { 
					String sql = "select fisboardcard FROM t_pdt_productdef where fid = '"+fid+"'";
					List<HashMap<String, Object>> data= productPlanDao.QueryBySql(sql);
					reponse.getWriter().write(JsonUtil.result(true, "",data.size()+"",data));
				} catch (DJException e) {
					reponse.getWriter().write(JsonUtil.result(false, e.getMessage(), "", ""));
				}
				return null;
			}
}
