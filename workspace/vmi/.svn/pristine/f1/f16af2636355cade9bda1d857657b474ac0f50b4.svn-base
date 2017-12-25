package Com.Controller.System;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import Com.Base.Util.DataUtil;
import Com.Base.Util.ExcelUtil;
import Com.Base.Util.JsonUtil;
import Com.Base.Util.ListResult;
import Com.Base.Util.UploadFile;
import Com.Base.Util.params;
import Com.Base.Util.mySimpleUtil.MySimpleToolsZ;
import Com.Dao.System.IBaseSysDao;
import Com.Dao.System.ICustproductDao;
import Com.Entity.System.CustRelationProduct;
import Com.Entity.System.Custproduct;
import Com.Entity.System.Useronline;

@Controller
public class CustproductController {
	Logger log = LoggerFactory.getLogger(CustproductController.class);
	@Resource
	private ICustproductDao CustproductDao;
	@Resource
	private IBaseSysDao baseSysDao;
	/**
	 * 建议字段命名一致性，这样就能避免很多不必要的操作
	 */
	private Map<String, String> storeToDbTMap = new HashMap<>();

	public CustproductController() {
		// TODO Auto-generated constructor stub

		storeToDbTMap.put("d_fname", "d.fname");
		storeToDbTMap.put("d_fnumber", "d.fnumber");
	}

	@RequestMapping("/SaveCustproduct")
	public String SaveCustproduct(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException, ParseException { // , Mainmenuitem
		String result = "";
		DateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String userid = ((Useronline)request.getSession().getAttribute("Useronline")).getFuserid();
		try {
			Custproduct info = new Custproduct();
			info.setFid(request.getParameter("fid"));
			info.setFname(request.getParameter("fname"));
			info.setFnumber(request.getParameter("fnumber"));
			info.setFcustomerid(request.getParameter("fcustomerid"));
			info.setFspec(request.getParameter("fspec"));
			info.setForderunit(request.getParameter("forderunit"));
			info.setFdescription(request.getParameter("fdescription"));
			info.setFcreatorid(request.getParameter("fcreatorid").isEmpty() ? userid
					: request.getParameter("fcreatorid"));
			info.setFlastupdatetime(new Date());
			info.setFlastupdateuserid(userid);
			info.setFcreatetime(!request.getParameter("fcreatetime").isEmpty() ? f
					.parse(request.getParameter("fcreatetime")) : new Date());

			info.setFcharactername(request.getParameter("fcharactername"));
			info.setFmaterial(request.getParameter("fmaterial"));
			info.setFtilemodel(request.getParameter("ftilemodel"));
			info.setFtype(1);
			info.setFproductmatching(request.getParameter("fproductmatching"));
			info.setFproductid(request.getParameter("fproductid"));
//			HashMap<String, Object> params = CustproductDao.ExecSave(info);
			
			/*
			 * 20150414 cd 合并客户产品保存方法;
			String sql1 = "select * from cusproduct_treegrid_view where fname='"+info.getFname()+"'or fnumber='"+info.getFnumber()+"'";
			ListResult result1 = CustproductDao.QueryFilterList(sql1, request);
			String sql2 = "select d.fid  FROM t_pdt_productdef d where d.fname='"+info.getFname()+"'or d.fnumber='"+info.getFnumber()+"'";
			ListResult result2 = CustproductDao.QueryFilterList(sql2, request);
			
			
			String ftype = request.getParameter("ftype");
			if(!"1".equals(ftype)){
			
				if(result1.getTotal().equals("0") && result2.getTotal().equals("0")){
				
					CustproductDao.ExecCreateCorrespondingProductRel(request, info);
				
	//			if (params.get("success") == Boolean.TRUE) {
					result = JsonUtil.result(true, "保存成功!", "", "");
	//			} else {
	//				result = JsonUtil.result(false, "保存失败!", "", "");
	//			}
				}else{
					throw new DJException("包装物名称或包装物编码重复");
				}
			}else{//1 为修改时判断
				CustproductDao.ExecCreateCorrespondingProductRel(request, info);
				result = JsonUtil.result(true, "保存成功!", "", "");
			}
			reponse.getWriter().write(result);
			*/
			
			//isCreate=1为新增状态
			if(request.getParameter("isCreate").equals("1")){
				if(CustproductDao.QueryExistsBySql("select id from cusproduct_treegrid_view where fname='"+info.getFname()+"' "+CustproductDao.QueryFilterByUser(request, "fcustomerid", null)) 
						|| CustproductDao.QueryExistsBySql("select fid FROM t_pdt_productdef where fname='"+info.getFname()+"' "+CustproductDao.QueryFilterByUser(request, "fcustomerid", null)))
				{
					throw new DJException("包装物名称不能重复！");
				}
				
				if(CustproductDao.QueryExistsBySql("select id from cusproduct_treegrid_view where fnumber='"+info.getFnumber()+"' "+CustproductDao.QueryFilterByUser(request, "fcustomerid", null)) 
						|| CustproductDao.QueryExistsBySql("select fid FROM t_pdt_productdef where fnumber='"+info.getFnumber()+"' "+CustproductDao.QueryFilterByUser(request, "fcustomerid", null)))
				{
					throw new DJException("包装物编码不能重复！");
				}
			}else{
				if(CustproductDao.QueryExistsBySql("select id from cusproduct_treegrid_view where fname='"+info.getFname()+"' and id <> '"+info.getFid()+"'"+CustproductDao.QueryFilterByUser(request, "fcustomerid", null)))
				{
					throw new DJException("包装物名称不能重复！");
				}
				
				if(CustproductDao.QueryExistsBySql("select id from cusproduct_treegrid_view where fnumber='"+info.getFnumber()+"' and id <> '"+info.getFid()+"'"+CustproductDao.QueryFilterByUser(request, "fcustomerid", null)))
				{
					throw new DJException("包装物编码不能重复！");
				}
			}
			
			CustproductDao.ExecCreateCorrespondingProductRel(request, info);
			
			reponse.getWriter().write(JsonUtil.result(true, "保存成功!", "", ""));
		} catch (DJException e) {
			reponse.getWriter().write(JsonUtil.result(false, e.getMessage(), "", ""));
//			result = JsonUtil.result(false, e.getMessage(), "", "");
		}
		
		return null;
	}

	@RequestMapping("/GetcustomerAll")
	public String GetcustomerAll(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		reponse.setCharacterEncoding("utf-8");
		try {
			String sql = " select fid,fname FROM t_bd_customer where 1=1 "
					+ CustproductDao.QueryFilterByUser(request, "fid", null);
			ListResult lr;
			lr = CustproductDao.QueryFilterList(sql, request);
			reponse.getWriter().write(JsonUtil.result(true, "", lr));
		} catch (DJException e) {
			// TODO Auto-generated catch block
			reponse.getWriter().write(
					JsonUtil.result(false, e.getMessage(), "", ""));
		}

		return null;

	}

	/**
	 * 客户产品
	 * 
	 * @param request
	 * @param reponse
	 * @return
	 * @throws IOException
	 * 
	 * @date 2014-9-25 上午10:43:15
	 */
	@RequestMapping("/GetCustproductList")
	public String GetCustproductList(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		reponse.setCharacterEncoding("utf-8");
		try {
			String result = "";
			String fcustomerid = request.getParameter("fcustomerid");

			// String sql = "";原来的
			// if(fcustomerid == null || fcustomerid.equals("")){
			// sql =
			// "select fid,fnumber,fname,fspec,forderunit,fcustomerid,fdescription,fcreatorid,fcreatetime,flastupdateuserid,flastupdatetime,Frelationed FROM t_bd_Custproduct where 1=1 "+CustproductDao.QueryFilterByUser(request,"fcustomerid"
			// , null);
			// }else{
			// sql =
			// "select fid,fnumber,fname,fspec,forderunit,fcustomerid,fdescription,fcreatorid,fcreatetime,flastupdateuserid,flastupdatetime,Frelationed FROM t_bd_Custproduct where fcustomerid='"+fcustomerid;
			// }

			String sql = " select feffect,fid, fnumber, fname, fspec, ifnull(fcharactername,'') fcharactername, ifnull(fcharacterid,'') fcharacterid, fmaterial, ftilemodel, forderunit, fcustomerid, fdescription, fcreatorid, fcreatetime, flastupdateuserid, flastupdatetime, Frelationed FROM t_bd_Custproduct where 1 = 1";
			if (fcustomerid == null || fcustomerid.equals("")) {
				sql += CustproductDao.QueryFilterByUser(request, "fcustomerid",
						null);
			} else {
				sql += " and fcustomerid='" + fcustomerid+"'";
			}
			request.setAttribute("djsort", "fcreatetime asc");
			// + request.getParameter("start")
			// + ","
			// + request.getParameter("limit");
			// List<HashMap<String, Object>> sList =
			// CustproductDao.QueryBySql(sql);
			ListResult lresult;
			lresult = CustproductDao.QueryFilterList(sql, request);
			List<HashMap<String, Object>> sList = lresult.getData();
			// reponse.getWriter().write(JsonUtil.result(true,"",CustproductDao.QueryCountBySql(sql),sList));
			reponse.getWriter().write(
					JsonUtil.result(true, "", lresult.getTotal(), sList));

		} catch (DJException e) {
			// TODO Auto-generated catch block
			reponse.getWriter().write(
					JsonUtil.result(false, e.getMessage(), "", ""));
		}

		return null;

	}

	@RequestMapping("/GetCustproductInfo")
	public String GetCustproductInfo(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		// String fid = request.getParameter("Custproductfid");
		// String hql = " FROM Custproduct where fid ='" + fid + "'";
		// List<Custproduct> sList = CustproductDao.QuerySysUsercls(hql);
		// String result = "";
		// result = "{success:true,data:" + GetJSON(sList) + "}";
		// reponse.setCharacterEncoding("utf-8");
		// reponse.getWriter().write(result);
		String result = "";
		String sql = "select cp.fproductid,cp.fproductmatching,c.fname fcustomerid_fname,cp.fid,cp.fnumber,cp.fname,IFNULL(fspec,'') fspec,IFNULL(forderunit,'') forderunit,cp.fcustomerid fcustomerid_fid,IFNULL(cp.fdescription,'') fdescription,cp.fcreatorid,cp.fcreatetime,cp.flastupdateuserid,cp.flastupdatetime,IFNULL(cp.fmaterial,'') fmaterial,IFNULL(cp.ftilemodel,'') ftilemodel,IFNULL(cp.fcharactername,'') fcharactername FROM t_bd_Custproduct cp left join t_bd_customer c on c.fid=cp.fcustomerid where cp.fid = '"
				// + request.getParameter("start")o
				// + ","
				// + request.getParameter("limit");
				// + request.getParameter("Custproductfid")+"'";
				+ request.getParameter("fid" + "") + "'";
		List<HashMap<String, Object>> sList = CustproductDao.QueryBySql(sql);
		reponse.setCharacterEncoding("utf-8");
		// reponse.getWriter().write(JsonUtil.result(true,"",CustproductDao.QueryCountBySql(sql),sList));
		reponse.getWriter().write(JsonUtil.result(true, "", "", sList));
		return null;

	}

	// @RequestMapping("/EffectCustproductList")
	// public String EffectCustproductList(HttpServletRequest request,
	// HttpServletResponse reponse) throws IOException {
	// String result = "";
	// String fidcls = request.getParameter("fidcls");
	// int feffect = Integer.parseInt(request.getParameter("feffect"));
	// try {
	// String hql = "Update Custproduct set fusedstatus=" + feffect
	// + " where fid in " + fidcls;
	// CustproductDao.ExecByHql(hql);
	// result = "{success:true,msg:'" + (feffect == 1 ? "启用" : "禁用")
	// + "成功!'}";
	// reponse.setCharacterEncoding("utf-8");
	// } catch (Exception e) {
	// result = "{success:false,msg:'" + e.toString().replaceAll("'", "")
	// + "'}";// e.getCause().getCause().toString()
	// }
	// reponse.getWriter().write(result);
	//
	// return null;
	//
	// }

	
	@RequestMapping("/DelCustproductList")
	public String DelCustproductList(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		String result = "";
		String fidcls = request.getParameter("fidcls");
		
		fidcls="('"+fidcls.replace(",","','")+"')";
		if(DataUtil.isforeignKeyConstraintLegal("t_ord_delivers", "fcusproductid", CustproductDao, fidcls)) {
			try {
				if(CustproductDao.QueryExistsBySql("select 1 from t_ord_deliverapply where fcusproductid in "+fidcls)){
					throw new DJException("选中的客户产品已生成订单，不能删除！");
				}
				CustproductDao.DelCustProduct(fidcls);
				result = JsonUtil.result(true,"删除成功!", "", "");
			} catch (DJException e) {
				result = JsonUtil.result(false,e.getMessage(), "", "");
				log.error("DelCustproductList error", e);
			}
		} else {
			result = "{success:false,msg:'" + "违法外键约束，本实体已被其他地方引用" + "'}";
		}
		
		reponse.getWriter().write(result);

		return null;

	}
	
//	@RequestMapping("/DelCustproductList")
//	public String DelCustproductList(HttpServletRequest request,
//			HttpServletResponse reponse) throws IOException {
//		String result = "";
//		String fidcls;
//		// fidcls = request.getParameter("fidcls");
//
//		fidcls = buildFidcls(request);
//
//		// fidcls="('"+fidcls.replace(",","','")+"')";
//		if (DataUtil.isforeignKeyConstraintLegal("t_ord_delivers",
//				"fcusproductid", CustproductDao, fidcls)) {
//			try {
//				// String hql =
//				// " FROM Custproduct where fusedstatus=1 and fid in " + fidcls;
//				// List<Custproduct> sList =
//				// CustproductDao.QuerySysUsercls(hql);
//				// if (sList.size() > 0) {
//				// result = "{success:false,msg:'不能删除已经启用的用户!'}";
//				// } else {
//				String hql = "Delete FROM Custproduct where fid in " + fidcls;
//				CustproductDao.ExecByHql(hql);
//				result = JsonUtil.result(true, "删除成功!", "", "");
//				// }
//				reponse.setCharacterEncoding("utf-8");
//			} catch (Exception e) {
//				result = JsonUtil.result(false, e.getMessage(), "", "");
//				log.error("DelCustproductList error", e);
//			}
//		} else {
//			result = "{success:false,msg:'" + "违反外键约束，本实体已被其他地方引用" + "'}";
//		}
//
//		reponse.getWriter().write(result);
//
//		return null;
//
//	}

	// @RequestMapping("/GetCustomerTree")
	// public String GetCustomerTree(HttpServletRequest request,
	// HttpServletResponse reponse) throws IOException {
	// String result="{children:[";
	// String sql = "select fid fid,fname text,1 isleaf  FROM t_bd_customer ";
	// List<HashMap<String, Object>> sList = CustproductDao.QueryBySql(sql);
	// result+=JsonUtil.List2Json(sList)+"]}";
	// reponse.setCharacterEncoding("utf-8");
	// reponse.getWriter().write(result);
	//
	// return null;
	//
	// }

	// private String GetJSON(List<Custproduct> sList) {
	// DateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	// String result = "";
	// int index = 0;
	// for (Custproduct info : sList) {
	// if (index > 0) {
	// result = result + ",";
	// }
	// result = result + "{fid:'" + info.getFid() + "',fname:'"
	// + info.getFname() + "',fnumber:'" + info.getFnumber()
	// + "',feffect:'" + (info.getFusedstatus() == 1 ? "是" : "否")
	// + "',fcountry:'" + info.getFcountry()
	// + "',femail:'" + info.getFemail() + "',ftel:'"
	// + info.getFtel() + "',fcreatetime:'"
	// + f.format(info.getFcreatetime()) + "'}";
	// index++;
	// }
	// return result;
	//
	// }

	private String buildFidcls(HttpServletRequest request) {
		// TODO Auto-generated method stub
		StringBuilder sbu = new StringBuilder("('");

		String json = request.getParameter("data");

		JSONArray joa = JSONArray.fromObject(json);

		for (int i = 0; i < joa.size(); i++) {

			String id = joa.getJSONObject(i).getString("id");

			if (i == 0) {

				sbu.append(id + "'");

			} else {

				sbu.append(",'" + id + "'");

			}

		}
		sbu.append(")");

		return sbu.toString();
	}

	@RequestMapping("/AddCustRelationProduct")
	public String AddCustRelationProduct(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		reponse.setCharacterEncoding("utf-8");
		try {
			String sql = "", sqlcount = "";
			String fidcls = request.getParameter("fidcls");
			fidcls = "('" + fidcls.replace(",", "','") + "')";
			String custid = request.getParameter("myobjectid");
			String[] tArrayofFid = DataUtil.InStrToArray(fidcls);
			sqlcount = "SELECT fid FROM t_sys_custrelationproduct where fcustid ='"
					+ custid + "'";// SELECT *
			if (CustproductDao.QueryBySql(sqlcount).size() > 0
					|| tArrayofFid.length > 1) {
				reponse.getWriter().write(
						JsonUtil.result(false, "关联失败,只允许关联一个产品", "", ""));
			} else {
				for (int i = 0; i < tArrayofFid.length; i++) {
					sql = "SELECT fid FROM t_sys_custrelationproduct where fproductdefid = :fproductdefid and fcustid = :fcustid ;";// SELECT
																																	// *

					params paramsT1 = new params();
					paramsT1.setString("fcustid", custid);
					paramsT1.setString("fproductdefid", tArrayofFid[i]);

					if (this.CustproductDao.QueryBySql(sql, paramsT1).size() == 0) {
						CustRelationProduct info = new CustRelationProduct();
						info.setFid(CustproductDao.CreateUUid());
						info.setFcustid(custid);
						info.setFproductdefid(tArrayofFid[i]);
						CustproductDao.saveOrUpdate(info);
					}

					sql = "update t_bd_custproduct set Frelationed = 1 where fid = '"
							+ custid + "' ";
					CustproductDao.ExecBySql(sql);
				}
				reponse.getWriter()
						.write(JsonUtil.result(true, "关联成功", "", ""));
			}
		} catch (Exception e) {
			reponse.getWriter().write(
					JsonUtil.result(false, e.getMessage(), "", ""));
		}

		return null;
	}

	@RequestMapping("/DelCustRelationProduct")
	public String CustRelationProduct(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		reponse.setCharacterEncoding("utf-8");
		try {
			String sql = "delete from t_sys_custrelationproduct where  fcustid = :fcustid   and fproductdefid in ";
			String fidcls = request.getParameter("fidcls");
			fidcls = "('" + fidcls.replace(",", "','") + "')";
			String fcustid = request.getParameter("myobjectid");
			sql = sql + fidcls;
			params paramsT = new params();
			paramsT.setString("fcustid", fcustid);
			CustproductDao.ExecBySql(sql, paramsT);

			sql = "update t_bd_custproduct set Frelationed = 0 where fid = '"
					+ fcustid + "' ";
			CustproductDao.ExecBySql(sql);

			reponse.getWriter().write(JsonUtil.result(true, "取消成功", "", ""));
		} catch (Exception e) {
			reponse.getWriter().write(
					JsonUtil.result(false, e.getMessage(), "", ""));
		}
		return null;
	}

	@RequestMapping("/GetCustRelationProductList")
	public String GetProductRelationCustList(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		String sql = "SELECT c.FNAME as fname, c.FNUMBER as fnumber, c.FID as fid , c.fversion fversion ,c.fcharacter fcharacter,c.fnewtype fnewtype FROM t_sys_custrelationproduct l, t_pdt_productdef c where l.fproductdefid =  c.FID ";
		ListResult result;
		reponse.setCharacterEncoding("utf-8");
		try {
			result = this.CustproductDao.QueryFilterList(sql, request);
			reponse.getWriter().write(JsonUtil.result(true, "", result));
		} catch (DJException e) {
			reponse.getWriter().write(
					JsonUtil.result(false, e.getMessage(), "", ""));
		}

		return null;

	}

	@RequestMapping(value = "/custproducttoexcel")
	public String custproducttoexcel(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		try {
			request.setAttribute("nolimit", 1000);
			String sql = "select fid ,fname 名称,fnumber 编码,ifnull(fspec,'') 规格,ifnull(forderunit,'') 单位,ifnull(fdescription,'')备注,ifnull(flastupdatetime,'') 修改时间, ifnull(fcreatetime,'') 创建时间 ,Frelationed 关联  FROM t_bd_Custproduct where 1=1 ";
			String fcustomerid = request.getParameter("fcustomerid");
			if (!"".equals(fcustomerid) && fcustomerid != null) {
				sql += " where fcustomerid='" + fcustomerid + "'";
			} else {
				sql += CustproductDao.QueryFilterByUser(request, "fcustomerid",
						null);
			}
			ListResult result;
			result = CustproductDao.QueryFilterList(sql, request);
			ExcelUtil.toexcel(reponse, result);
		} catch (DJException e) {
			// TODO Auto-generated catch block
			reponse.getWriter().write(
					JsonUtil.result(false, e.getMessage(), "", ""));
		}

		return null;

	}

	/**
	 * 获取部分剔除的产品
	 * 
	 * @param request
	 * @param reponse
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/getCustProductsWithoutTreeNode")
	public String getCustProductsWithoutTreeNode(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		reponse.setCharacterEncoding("utf-8");
		try {
			// String sql =
			// "select d.fid as d_fid,d.fname as d_fname,d.fnumber as d_fnumber,d.fcustomerid as d_fcustomerid,d.fversion as d_fversion,d.fcharacter d_fcharacter,d.fboxmodelid d_fboxmodelid,d.feffect d_feffect,d.fnewtype d_fnewtype,d.fishistory d_fishistory,d.flastupdateuserid d_flastupdateuserid,u2.fname u2_fname, d.flastupdatetime d_flastupdatetime,d.fcreatetime d_fcreatetime,d.fcreatorid d_fcreatorid,u1.fname u1_fname FROM t_pdt_productdef d left join t_sys_user u1 on  u1.fid=d.fcreatorid left join t_sys_user u2 on  u2.fid=d.flastupdateuserid where d.FID not in ( select FPRODUCTID from t_pdt_productdefproducts UNION  select FPARENTID from t_pdt_productdefproducts) ";

			// String sql =
			// "select d.fid as d_fid,d.fname as d_fname,d.fnumber as d_fnumber,d.fcustomerid as d_fcustomerid,d.fversion as d_fversion,d.fcharacter d_fcharacter,d.fboxmodelid d_fboxmodelid,d.feffect d_feffect,d.fnewtype d_fnewtype,d.fishistory d_fishistory,d.flastupdateuserid d_flastupdateuserid,u2.fname u2_fname, d.flastupdatetime d_flastupdatetime,d.fcreatetime d_fcreatetime,d.fcreatorid d_fcreatorid,u1.fname u1_fname FROM t_pdt_productdef d left join t_sys_user u1 on  u1.fid=d.fcreatorid left join t_sys_user u2 on  u2.fid=d.flastupdateuserid where d.FID <> '%s' and d.FID not in (select FPARENTID from t_pdt_productdefproducts where FPRODUCTID = '%s' union select FPRODUCTID from t_pdt_productdefproducts where FPARENTID = '%s')";

			/**
			 * 剔除有子件的node、本身和直接父子。
			 */
//			String sql = "select d.fid as d_fid,d.fname as d_fname,d.fnumber as d_fnumber,d.fcustomerid as d_fcustomerid,d.fversion as d_fversion,d.fcharacter d_fcharacter,d.fboxmodelid d_fboxmodelid,d.feffect d_feffect,d.fnewtype d_fnewtype,d.fishistory d_fishistory,d.flastupdateuserid d_flastupdateuserid,u2.fname u2_fname, d.flastupdatetime d_flastupdatetime,d.fcreatetime d_fcreatetime,d.fcreatorid d_fcreatorid,u1.fname u1_fname FROM t_pdt_productdef d left join t_sys_user u1 on  u1.fid=d.fcreatorid left join t_sys_user u2 on  u2.fid=d.flastupdateuserid where d.FID not in (select cn.fproductid from t_pdt_custrelationentry cn left join t_pdt_custrelation c on cn.fparentid=c.fid where d.FISHISTORY=0 and c.fcustproductid = '%s' ) ";
			String sqlTest = "select cn.fproductid from t_pdt_custrelationentry cn left join t_pdt_custrelation c on cn.fparentid=c.fid left join t_pdt_productdef p on cn.fproductid = p.fid where p.FISHISTORY=0 and c.fcustproductid = '%s' ";
			String fid = request.getParameter("dbfid");

			String filterCondit = translateToQS(request.getParameter("filter"));

//			sql = String.format(sql, fid, fid, fid);
			sqlTest = String.format(sqlTest, fid);
			ListResult result2 = CustproductDao.QueryFilterList(sqlTest, request);
			String med = " ";
			if(result2.getData().size()!=0){
				med = "'"+result2.getData().get(0).get("fproductid")+"'" ;
				for (int i = 1; i < result2.getData().size(); i++) {
					med = med + ",'"+result2.getData().get(i).get("fproductid")+"'";
				}
			}else{
				med="''";
			}
			String sql = "select d.fid as d_fid,d.fname as d_fname,d.fnumber as d_fnumber,d.fcustomerid as d_fcustomerid,d.fversion as d_fversion,d.fcharacter d_fcharacter,d.fboxmodelid d_fboxmodelid,d.feffect d_feffect,d.fnewtype d_fnewtype,d.fishistory d_fishistory,d.flastupdateuserid d_flastupdateuserid,u2.fname u2_fname, d.flastupdatetime d_flastupdatetime,d.fcreatetime d_fcreatetime,d.fcreatorid d_fcreatorid,u1.fname u1_fname FROM t_pdt_productdef d left join t_sys_user u1 on  u1.fid=d.fcreatorid left join t_sys_user u2 on  u2.fid=d.flastupdateuserid where d.FID not in  "+"("+med+") ";
			ListResult result = null;

			if (filterCondit != null) {

				sql += filterCondit;
				/**
				 * 查询结果不是ListResult的实例？待统一
				 */
				List result1 = CustproductDao.QueryBySql(sql);
				result = new ListResult();
				result.setData(result1);
				result.setTotal(CustproductDao.QueryCountBySql(sql));

			} else {

				result = CustproductDao.QueryFilterList(sql, request);

			}

			reponse.getWriter().write(JsonUtil.result(true, "", result));
		} catch (DJException e) {
			// TODO Auto-generated catch block
			reponse.getWriter().write(
					JsonUtil.result(false, e.getMessage(), "", ""));
		}

		return null;

	}

	/**
	 * 只执行最后的查询
	 * 
	 * @param parameter
	 * @return
	 */
	private String translateToQS(String parameter) {
		// TODO Auto-generated method stub
		String r = null;
		if (parameter != null && !parameter.equals("")) {
			JSONArray jsa = JSONArray.fromObject(parameter);
			JSONObject jso = jsa.getJSONObject(jsa.size() - 1);

			String property = jso.getString("property");
			String value = jso.getString("value");

			r = " and %s like '%s' ";
//			r = String
//					.format(r, storeToDbTMap.get(property), "%" + value + "%");
			String ftable="";
			if(property.indexOf("_")==-1)//产品定义有_
			{
				 ftable=property;
			}else
			{
				ftable=storeToDbTMap.get(property);
			}
			r = String.format(r, ftable, "%" + value + "%");
		}

		return r;
	}

	/**
	 * 响应产品树关系请求,用的是异步加载树（配合sencha 的treepanl），即时请求即查
	 * 
	 * @param request
	 * @param reponse
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/GetCustProductStructTree")
	public String GetCustProductStructTree(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		String result = "{children:[";
		String sql = "select d.fid id,d.fname text, d.fnumber fnumber, f.FAMOUNT as mcount, f.FID as dbId,"
				+ " 1 isleaf"
				+ " from t_pdt_custrelationentry f"
				+ " left join  t_pdt_productdef d  on f.fproductid=d.fid"
				+ " left join  t_pdt_custrelation s  on f.fparentid=s.fid";
		// + " left join   t_bd_custproduct  ss on ss.fid=s.FCUSTPRODUCTID";
		String fparentid = request.getParameter("id");
		if (fparentid != null) {
			String[] ids = fparentid.split(",");
			sql += " where s.FCUSTPRODUCTID='" + ids[0] + "'";
		}
		List<HashMap<String, Object>> sList = CustproductDao.QueryBySql(sql);
		result += JsonUtil.List2Json(sList) + "]}";
		reponse.setCharacterEncoding("utf-8");
		reponse.getWriter().write(result);
		return null;
	}

	/**
	 * treenode数据库数据定义问题？不得已在一个方法中同时处理u和c操作
	 * 
	 * @param request
	 * @param reponse
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/updateAndCreateCustproductTree")
	public String updateAndCreateCustproductTree(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		String result = "";

		boolean succes = true;

		String reqS = request.getParameter("data");

		JSONArray jsa = DataUtil.getJsonArrayByS(reqS);

		String dbId = ((JSONObject) jsa.get(0)).getString("dbId");

		/**
		 * 后台树深度验证，更严谨
		 */
		int depth = Integer.parseInt(((JSONObject) jsa.get(0))
				.getString("depth"));

		if (depth <= 1) {
			if (dbId.equals("")) {
				addCustproductTree(request, reponse);
			} else {
				updateCustproductTree(request, reponse);

			}
		} else {
			reponse.getWriter().write(
					JsonUtil.result(false, "!(depth <= 1)", "", ""));
		}

		return null;
	}

	@RequestMapping("/deleteCustproductTreeNodes")
	public String deleteCustproductTreeNodes(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		reponse.setCharacterEncoding("utf-8");

		String result = "";

		boolean succes = true;

		String reqS = request.getParameter("data");

		JSONArray jsa = DataUtil.getJsonArrayByS(reqS);

		String sql = "";
		String entrysql = "";
		String fparentsql = "";
		String fparentid = "";
		for (int i = 0; i < jsa.size(); i++) {

			String id = ((JSONObject) jsa.get(i)).getString("id");
			String pId = ((JSONObject) jsa.get(i)).getString("parentId");// .split(",")[0]
			String dbId = ((JSONObject) jsa.get(i)).getString("dbId");

			int count = ((JSONObject) jsa.get(i)).getInt("mcount");

			id = translatePID(id);
			pId = translatePID(pId);
			dbId = translatePID(dbId);

			entrysql = "delete from t_pdt_custrelationentry where FID = '"
					+ dbId + "'";

			List<HashMap<String, Object>> list = CustproductDao
					.QueryBySql("select fid from t_pdt_custrelation where FCUSTPRODUCTID='"
							+ pId + "'");
			if (list.size() > 0) {
				fparentid = (String) list.get(0).get("fid");
			}

			sql = "delete FROM t_pdt_custrelation where not EXISTS (select fid from t_pdt_custrelationentry tn where t_pdt_custrelation.fid=tn.fparentid and tn.fid<>'"
					+ dbId
					+ "') and t_pdt_custrelation.fid='"
					+ fparentid
					+ "'";

			try {
				list = CustproductDao
						.QueryBySql("select FCUSTPRODUCTID FROM t_pdt_custrelation where not EXISTS (select fid from t_pdt_custrelationentry tn where t_pdt_custrelation.fid=tn.fparentid and tn.fid<>'"
								+ dbId
								+ "') and t_pdt_custrelation.fid='"
								+ fparentid + "'");
				if (list.size() > 0) {
					fparentsql = "update t_bd_custproduct set Frelationed = 0 where fid='"
							+ list.get(0).get("FCUSTPRODUCTID") + "' ";
				}

				CustproductDao.ExecdeteleString(entrysql, sql, fparentsql);

				// productdefDao.ExecBySql(sql)

			} catch (Exception e) {

				succes = false;
				result = JsonUtil.result(false, e.toString(), "", "");

			}
		}

		if (succes) {
			result = JsonUtil.result(true, "", "", "");
		}

		reponse.getWriter().write(result);

		return null;
	}

	@RequestMapping(value = "/addCustproductTree")
	public String addCustproductTree(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		String result = "";
		String fparentid = "";
		boolean succes = true;

		try {
			String reqS = request.getParameter("data");
			String sql = "";
			String custproductid = "";
			JSONArray jsa = DataUtil.getJsonArrayByS(reqS);
			if (jsa.size() != 1) {
				throw new DJException("选择一个产品关联！");
			}
			boolean flag = CustproductDao.ExecCustrelation(jsa);
			if (flag == false) {
				throw new DJException("只能关联一个产品！");
			}

			// sql =
			// "update t_bd_custproduct set Frelationed = 1 where fid = '"+custproductid+"' ";
			// CustproductDao.ExecBySql(sql);
		} catch (Exception e) {
			succes = false;
			result = JsonUtil.result(false, e.getMessage(), "", "");
		}

		if (succes) {
			result = JsonUtil.result(true, fparentid, "", "");
		}

		reponse.getWriter().write(result);

		return null;
	}

	@RequestMapping(value = "/updateCustproductTree")
	public String updateCustproductTree(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		String result = "";

		boolean succes = true;

		String reqS = request.getParameter("data");

		JSONArray jsa = DataUtil.getJsonArrayByS(reqS);

		for (int i = 0; i < jsa.size(); i++) {

			String id = ((JSONObject) jsa.get(i)).getString("id");
			// String parentId = ((JSONObject)
			// jsa.get(i)).getString("parentId");// .split(",")[0]
			int count = ((JSONObject) jsa.get(i)).getInt("mcount");
			String fid = ((JSONObject) jsa.get(i)).getString("dbId");

			// parentId = translatePID(parentId);
			id = translatePID(id);

			// 添加单引号，原生sql的要求，但尽量避免，原生sql操作是有重大安全漏洞的
			String sql = "UPDATE t_pdt_custrelationentry SET FAMOUNT=:count,FPRODUCTID = :id WHERE  FID = :fid  ;";

			params p = new params();

			p.setString("id", id);
			// p.setString("fid", productdefDao.CreateUUid());
			// p.setString("parentId", parentId);
			p.setInt("count", count);
			p.setString("fid", fid);

			try {
				// if (verifyIdAndPid(id, parentId)) {

				CustproductDao.ExecBySql(sql, p);
				// } else {
				// succes = false;
				// }

				// productdefDao.ExecBySql(sql)

			} catch (Exception e) {

				succes = false;
				result = JsonUtil.result(false, e.toString(), "", "");

			}
		}

		if (succes) {
			result = JsonUtil.result(true, "", "", "");
		}

		reponse.getWriter().write(result);

		return null;
	}

	private String translatePID(String pId) {

		// TODO Auto-generated method stub

		if (pId.split(",").length > 1) {
			pId = pId.split(",")[0];

		}

		return pId;
	}

	/**
	 * 可用于后期拓展，不过没什么必要，备选node已经执行过滤了。
	 * 
	 * @param id
	 * @param pId
	 * @return
	 */
	private boolean verifyIdAndPid(String id, String pId) {
		// TODO Auto-generated method stub
		boolean succes = true;

		// String sql = "select "

		return succes;

	}

	@RequestMapping("/GetRelationCustproductList")
	public String GetRelationCustproductList(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		reponse.setCharacterEncoding("utf-8");
		try {
			String result = "";
			String fcustomerid = request.getParameter("fcustomerid");
			String sql = "";
			ListResult lresult;
			if (fcustomerid == null || fcustomerid.equals("")) {
				sql = "select fid,fnumber,fname,fspec,forderunit,fcustomerid,fdescription,fcreatorid,fcreatetime,flastupdateuserid,flastupdatetime,Frelationed FROM t_bd_Custproduct where 1=1 and feffect=1"
						+ CustproductDao.QueryFilterByUser(request,
								"fcustomerid", null);
			} else {
				sql = "select fid,fnumber,fname,fspec,forderunit,fcustomerid,fdescription,fcreatorid,fcreatetime,flastupdateuserid,flastupdatetime,Frelationed FROM t_bd_Custproduct where fcustomerid='"
						+ fcustomerid + "' and feffect=1";
			}
			String filterCondit = translateCustProductToQS(request
					.getParameter("filter"));
			if (filterCondit != null) {

				sql += filterCondit;
				/**
				 * 查询结果不是ListResult的实例？待统一
				 */
				List result1 = CustproductDao.QueryBySql(sql);
				lresult = new ListResult();
				lresult.setData(result1);
				lresult.setTotal(CustproductDao.QueryCountBySql(sql));

			} else {

				lresult = CustproductDao.QueryFilterList(sql, request);

			}
			// lresult = CustproductDao.QueryFilterList(sql,request);
			// List<HashMap<String, Object>> sList = lresult.getData();
			reponse.getWriter().write(JsonUtil.result(true, "", lresult));
			// reponse.getWriter().write(JsonUtil.result(true,"",lresult.getTotal(),sList));

		} catch (DJException e) {
			// TODO Auto-generated catch block
			reponse.getWriter().write(
					JsonUtil.result(false, e.getMessage(), "", ""));
		}

		return null;

	}

	private String translateCustProductToQS(String parameter) {
		// TODO Auto-generated method stub
		String r = null;
		if (parameter != null && !parameter.equals("")) {
			JSONArray jsa = JSONArray.fromObject(parameter);
			JSONObject jso = jsa.getJSONObject(jsa.size() - 1);

			String property = jso.getString("property");

			String value = jso.getString("value");

			r = " and %s like '%s' ";
			r = String.format(r, property, "%" + value + "%");
		}

		return r;
	}

	@RequestMapping("/AddUserRelationCust")
	public String AddUserRelationCust(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		reponse.setCharacterEncoding("utf-8");
		try {
			String userid = ((Useronline) request.getSession().getAttribute(
					"Useronline")).getFuserid();
			String fidcls = request.getParameter("fidcls");
			fidcls = "('" + fidcls.replace(",", "','") + "')";
			String[] tArrayofFid = DataUtil.InStrToArray(fidcls);
			CustproductDao.ExecAddUserRelationCust(userid, tArrayofFid);
			reponse.getWriter().write(JsonUtil.result(true, "关联成功", "", ""));
		} catch (Exception e) {
			reponse.getWriter().write(
					JsonUtil.result(false, e.getMessage(), "", ""));
		}

		return null;
	}

	@RequestMapping("/DelUserRelationCust")
	public String DelUserRelationCust(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		reponse.setCharacterEncoding("utf-8");
		try {
			String userid = ((Useronline) request.getSession().getAttribute(
					"Useronline")).getFuserid();
			String fidcls = request.getParameter("fidcls");
			fidcls = "('" + fidcls.replace(",", "','") + "')";
			CustproductDao.ExecDeleteUserRelationCust(userid, fidcls);

			reponse.getWriter().write(JsonUtil.result(true, "取消成功", "", ""));
		} catch (Exception e) {
			reponse.getWriter().write(
					JsonUtil.result(false, e.getMessage(), "", ""));
		}
		return null;
	}

	@RequestMapping("/GetUserRelationCustList")
	public String GetUserRelationCustList(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		String userid = ((Useronline) request.getSession().getAttribute(
				"Useronline")).getFuserid();
		String sql = "select  fname,fnumber,c.fid,fspec,fdescription from t_bd_userrelationcustp p,t_bd_custproduct c where p.fcustproductid=c.fid  and p.fuserid='"
				+ userid + "'";
		ListResult result;
		reponse.setCharacterEncoding("utf-8");
		try {
			result = this.CustproductDao.QueryFilterList(sql, request);
			reponse.getWriter().write(JsonUtil.result(true, "", result));
		} catch (DJException e) {
			reponse.getWriter().write(
					JsonUtil.result(false, e.getMessage(), "", ""));
		}

		return null;

	}

	/**
	 * 客户产品
	 * 
	 * @param request
	 * @param reponse
	 * @return
	 * @throws IOException
	 * 
	 * @date 2014-9-27 下午3:07:59 (ZJZ)
	 */
	@RequestMapping("/selectCustproductsInTreeWay")
	public String selectCustproductsInTreeWay(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		reponse.setCharacterEncoding("utf-8");
		
		try {

			reponse.getWriter().write(
					CustproductDao.ExecBuildCusProductChilrenJson(request));

		} catch (DJException e) {
			// TODO Auto-generated catch block
			reponse.getWriter().write(
					JsonUtil.result(false, e.getMessage(), "", ""));
		}

		return null;

	}

	@RequestMapping("/selectAccessoryByCusPId")
	public String selectAccessoryByCusPId(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {

		reponse.setCharacterEncoding("utf-8");
		request.setCharacterEncoding("utf-8");

		String cusProductId = request.getParameter("fcusproductid");

		try {

			String sql = " SELECT fid,fname,fpath FROM t_ord_productdemandfile";
			
			if (cusProductId != null) {
				
				sql += " where fparentid = '%s' ";
				sql = String.format(sql, cusProductId);
				
				
			} else {
				
				sql += " where 1<>1 ";
				
			}
			
			
			ListResult result = CustproductDao.QueryFilterList(sql, request);

			for (HashMap<String, Object> record : result.getData()) {
				
//				String pathT =  (String) record.get("fpath");
//				pathT = pathT.substring(pathT.lastIndexOf("vmifile"));
				String pathT = UploadFile.getFilePath(request,(String)record.get("fid"));
//				pathT = "file/schemedesign/"+ pathT.substring(pathT.lastIndexOf("/") + 1);
				record.put("fpath", pathT);
			}
			
			reponse.getWriter().write(JsonUtil.result(true, "", result));
		} catch (DJException e) {
			// TODO Auto-generated catch block
			reponse.getWriter().write(
					JsonUtil.result(false, e.getMessage(), "", ""));
		}

		return null;

	}
	
	
	@RequestMapping("/selectCreateAndUpdateCountForColumn")
	public String selectCreateAndUpdateCountForColumn(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {

		reponse.setCharacterEncoding("utf-8");
		request.setCharacterEncoding("utf-8");

		try {

			reponse.getWriter().write(CustproductDao.ExecSelectCreateAndUpdateCountForColumn(request));
		} catch (DJException e) {
			// TODO Auto-generated catch block
			reponse.getWriter().write(
					JsonUtil.result(false, e.getMessage(), "", ""));
		}

		return null;

	}
	
	
		
		
		/**
		 * 响应产品树关系请求,用的是异步加载树（配合sencha 的treepanl），即时请求即查
		 * 
		 * @param request
		 * @param reponse
		 * @return
		 * @throws IOException
		 */
		@RequestMapping(value = "/GetCustProductStructTreeInfo")
		public String GetCustProductStructTreeInfo(HttpServletRequest request,
				HttpServletResponse reponse) throws IOException {
			String result = "{children:[";
			String sql="select  distinct CONCAT(s.fid,',',f.fparentid) id,s.fname text, s.fnumber, f.FAMOUNT as mcount, f.FID as dbId,"
				+" case ifnull(s2.fparentid,'') when '' then 1 else 0 end isleaf ,f.fparentid parentId "
				+" from t_bd_custproductproducts f "
				+" left join  t_bd_custproduct s  on f.fcustproductid=s.fid "
				+" left join   t_bd_custproductproducts  s2 on s2.fparentid=s.fid ";
			String fparentid = request.getParameter("id");
			if (fparentid != null) {
				String[] ids = fparentid.split(",");
				sql += " where f.fparentid='" + ids[0] + "'";
			}
			List<HashMap<String, Object>> sList = CustproductDao.QueryBySql(sql);
			result += JsonUtil.List2Json(sList) + "]}";
			reponse.setCharacterEncoding("utf-8");
			reponse.getWriter().write(result);
			return null;
		}
		
		
		/**
		 * treenode数据库数据定义问题？不得已在一个方法中同时处理u和c操作
		 * 
		 * @param request
		 * @param reponse
		 * @return
		 * @throws IOException
		 */
		@RequestMapping(value = "/updateAndCreateCustProductStructTree")
		public String updateAndCreateCustProductStructTree(HttpServletRequest request,
				HttpServletResponse reponse) throws IOException {


			String reqS = request.getParameter("data");

			JSONArray jsa = DataUtil.getJsonArrayByS(reqS);

			String dbId = ((JSONObject) jsa.get(0)).getString("dbId");

			/**
			 * 后台树深度验证，更严谨
			 */
			int depth = Integer.parseInt(((JSONObject) jsa.get(0))
					.getString("depth"));

			String parentId = ((JSONObject) jsa.get(0)).getString("parentId")
					.toString();
			parentId = translatePID(parentId);
		
				if (depth <= 1) {
					if (dbId.equals("")) {
						addCustProductStructTree(request, reponse);
					} else {
						updateCustProductStructTree(request, reponse);

					}
				} else {
					reponse.getWriter().write(
							JsonUtil.result(false, "!(depth <= 1)", "", ""));
				}

			return null;
		}
		@RequestMapping(value = "/addCustProductStructTree")
		public String addCustProductStructTree(HttpServletRequest request,
				HttpServletResponse reponse) throws IOException {
			String result = "";

			boolean succes = true;

			String reqS = request.getParameter("data");

			JSONArray jsa = DataUtil.getJsonArrayByS(reqS);
			List addlists=new ArrayList();
			try {
			for (int i = 0; i < jsa.size(); i++) {

				String id = ((JSONObject) jsa.get(i)).getString("id");
				String pId = ((JSONObject) jsa.get(i)).getString("parentId");// .split(",")[0]
				int count = ((JSONObject) jsa.get(i)).getInt("mcount");

				// 添加单引号，原生sql的要求，但尽量避免，原生sql操作是有重大安全漏洞的
				String sql = "INSERT INTO t_bd_custproductproducts (FID, FCUSTPRODUCTID, FPARENTID, FAMOUNT) VALUES (\'%s\', \'%s\', \'%s\', %d) ";
				pId = translatePID(pId);
				id = translatePID(id);
				if(i==0){
				if(IsTraitidCustProduct(pId))
				{
					succes = false;
					result = JsonUtil.result(false, "包装需求产生的产品不能操作", "", "");
					break;
				}
				if(CustproductDao.QueryExistsBySql("SELECT 1 FROM t_bd_custproductproducts where FCUSTPRODUCTID = '"+pId+"'"))
				{
					succes = false;
					result = JsonUtil.result(false, "目标产品不能为子产品", "", "");
					break;
				}
				}

						String fsql = String.format(sql,CustproductDao.CreateUUid(), id, pId, count);
						addlists.add(fsql);
						if(i==jsa.size()-1)
						{
							addlists.add("update t_bd_custproduct set  ftype=0 where fid='"+pId+"'");
							CustproductDao.ExecNumberofSQL(addlists);
						}
				
			}
			} catch (Exception e) {

				succes = false;
				result = JsonUtil.result(false, e.toString(), "", "");

			}
			if (succes) {
			
				result = JsonUtil.result(true, "", "", "");
			}

			reponse.getWriter().write(result);

			return null;
		}
		
		
		@RequestMapping(value = "/updateCustProductStructTree")
		public String updateCustProductStructTree(HttpServletRequest request,
				HttpServletResponse reponse) throws IOException {
			String result = "";

			boolean succes = true;

			String reqS = request.getParameter("data");

			JSONArray jsa = DataUtil.getJsonArrayByS(reqS);

			for (int i = 0; i < jsa.size(); i++) {

				String id = ((JSONObject) jsa.get(i)).getString("id");
				String pId = ((JSONObject) jsa.get(i)).getString("parentId");// .split(",")[0]
				int count = ((JSONObject) jsa.get(i)).getInt("mcount");
				String fid = ((JSONObject) jsa.get(i)).getString("dbId");

				pId = translatePID(pId);
				id = translatePID(id);

				// 添加单引号，原生sql的要求，但尽量避免，原生sql操作是有重大安全漏洞的
				String sql = "UPDATE t_bd_custproductproducts SET FAMOUNT=:count, FPARENTID=:pid, FCUSTPRODUCTID = :id WHERE  FID = :fid  ;";
				params p = new params();

				p.setString("id", id);
				// p.setString("fid", productdefDao.CreateUUid());
				p.setString("pid", pId);
				p.setInt("count", count);
				p.setString("fid", fid);
				try {
					if(IsTraitidCustProduct(pId))
					{
						succes = false;
						result = JsonUtil.result(false, "包装需求产生的产品不能操作", "", "");
						break;
					}
					if(CustproductDao.QueryExistsBySql("SELECT 1 FROM t_bd_custproductproducts where FCUSTPRODUCTID = '"+pId+"'"))
					{
						succes = false;
						result = JsonUtil.result(false, "目标产品不能为子产品", "", "");
						break;
					}
					CustproductDao.ExecBySql(sql, p);

				} catch (Exception e) {

					succes = false;
					result = JsonUtil.result(false, e.toString(), "", "");

				}
			}

			if (succes) {
				result = JsonUtil.result(true, "", "", "");
			}

			reponse.getWriter().write(result);

			return null;
		}

		
		@RequestMapping("/deleteCustProductStructTreeNodes")
		public String deleteCustProductStructTreeNodes(HttpServletRequest request,
				HttpServletResponse reponse) throws IOException {
			reponse.setCharacterEncoding("utf-8");

			String result = "";

			boolean succes = true;

			String reqS = request.getParameter("data");

			JSONArray jsa = DataUtil.getJsonArrayByS(reqS);
			String parentId = ((JSONObject) jsa.get(0)).getString("parentId")
					.toString();
			parentId = translatePID(parentId);
			ArrayList list =new ArrayList();

				for (int i = 0; i < jsa.size(); i++) {

					String id = ((JSONObject) jsa.get(i)).getString("id");
					String pId = ((JSONObject) jsa.get(i)).getString("parentId");// .split(",")[0]

					String dbId = ((JSONObject) jsa.get(i)).getString("dbId");

					int count = ((JSONObject) jsa.get(i)).getInt("mcount");

					id = translatePID(id);
					pId = translatePID(pId);
					dbId = translatePID(dbId);

					String sql = "delete from t_bd_custproductproducts where FID = '"+dbId+"'";

					try {
						if(IsTraitidCustProduct(pId))
						{
							succes = false;
							result = JsonUtil.result(false, "包装需求产生的产品不能操作", "", "");
							break;
						}
						list.add(sql);
						
						if (CustproductDao.QueryExistsBySql("select 1 from t_bd_custproductproducts where FPARENTID='"+pId+"' and fid<>'"+dbId+"'")==false)
						{

							list.add("update t_bd_custproduct set  ftype=1 where fid='"+pId+"'");
						}
						CustproductDao.ExecNumberofSQL(list);
					
					} catch (Exception e) {

						succes = false;
						result = JsonUtil.result(false, e.toString(), "", "");

					}
				}

				if (succes) {
					result = JsonUtil.result(true, "", "", "");
				}
			

			reponse.getWriter().write(result);

			return null;
		}

				private  boolean IsTraitidCustProduct(String fid)
				{
					Custproduct cp=CustproductDao.Query(fid);
					
					return cp.getFcharacterid()==null?false:true;
				}
		
		
		/**
		 * 获取部分剔除的产品
		 * 
		 * @param request
		 * @param reponse
		 * @return
		 * @throws IOException
		 */
		@RequestMapping(value = "/getCustProductsWithoutTreeNodeList")
		public String getCustProductsWithoutTreeNodeList(HttpServletRequest request,
				HttpServletResponse reponse) throws IOException {
			reponse.setCharacterEncoding("utf-8");
			try {
				// String sql =
				// "select d.fid as d_fid,d.fname as d_fname,d.fnumber as d_fnumber,d.fcustomerid as d_fcustomerid,d.fversion as d_fversion,d.fcharacter d_fcharacter,d.fboxmodelid d_fboxmodelid,d.feffect d_feffect,d.fnewtype d_fnewtype,d.fishistory d_fishistory,d.flastupdateuserid d_flastupdateuserid,u2.fname u2_fname, d.flastupdatetime d_flastupdatetime,d.fcreatetime d_fcreatetime,d.fcreatorid d_fcreatorid,u1.fname u1_fname FROM t_pdt_productdef d left join t_sys_user u1 on  u1.fid=d.fcreatorid left join t_sys_user u2 on  u2.fid=d.flastupdateuserid where d.FID not in ( select FPRODUCTID from t_pdt_productdefproducts UNION  select FPARENTID from t_pdt_productdefproducts) ";

				// String sql =
				// "select d.fid as d_fid,d.fname as d_fname,d.fnumber as d_fnumber,d.fcustomerid as d_fcustomerid,d.fversion as d_fversion,d.fcharacter d_fcharacter,d.fboxmodelid d_fboxmodelid,d.feffect d_feffect,d.fnewtype d_fnewtype,d.fishistory d_fishistory,d.flastupdateuserid d_flastupdateuserid,u2.fname u2_fname, d.flastupdatetime d_flastupdatetime,d.fcreatetime d_fcreatetime,d.fcreatorid d_fcreatorid,u1.fname u1_fname FROM t_pdt_productdef d left join t_sys_user u1 on  u1.fid=d.fcreatorid left join t_sys_user u2 on  u2.fid=d.flastupdateuserid where d.FID <> '%s' and d.FID not in (select FPARENTID from t_pdt_productdefproducts where FPRODUCTID = '%s' union select FPRODUCTID from t_pdt_productdefproducts where FPARENTID = '%s')";

				/**
				 * 剔除有子件的node、本身和直接父子。
				 */
				String sql="select fid,fnumber,fname,fspec,forderunit,fcustomerid,fdescription,ifnull(fcharactername,'')fcharactername, ifnull(fmaterial,'') fmaterial, ifnull(ftilemodel,'') ftilemodel FROM t_bd_Custproduct d  where fid <> '%s' and fid not in ( select FCUSTPRODUCTID from t_bd_custproductproducts where FPARENTID = '%s' union SELECT distinct FPARENTID FROM t_bd_custproductproducts) and fcustomerid='%s' ";
				String fid = request.getParameter("dbfid");
				String fcustomerid = request.getParameter("fcustomerid");

				String filterCondit = translateToQS(request.getParameter("filter"));

				sql = String.format(sql, fid, fid,fcustomerid);

				ListResult result = null;

				if (filterCondit != null) {

					sql += filterCondit;
					result = CustproductDao.QueryFilterList(sql, request);
				} else {

					result = CustproductDao.QueryFilterList(sql, request);

				}

				reponse.getWriter().write(JsonUtil.result(true, "", result));
			} catch (DJException e) {
				reponse.getWriter().write(
						JsonUtil.result(false, e.getMessage(), "", ""));
			}

			return null;

		}
		/**
		 * 我的订单批量新增之包装物选择查询
		 * @param request
		 * @param reponse
		 * @return
		 * @throws IOException
		 */
		@RequestMapping("/GetCustproductLists")
		public String GetCustproductLists(HttpServletRequest request,
				HttpServletResponse reponse) throws IOException {
			 String userid =
					 ((Useronline)request.getSession().getAttribute("Useronline")).getFuserid();
			try {
				String sql = " select fid, fnumber, fname, fspec, ifnull(fcharactername,'') fcharactername, ifnull(fcharacterid,'') fcharacterid, fmaterial, ftilemodel, forderunit, fcustomerid, fdescription, fcreatorid, fcreatetime, flastupdateuserid, flastupdatetime, Frelationed FROM t_bd_Custproduct where 1 = 1 and feffect=1";
					sql += CustproductDao.QueryFilterByUser(request, "fcustomerid",null);
				String sqls = "SELECT 1 FROM t_bd_userrelationcustp  where fuserid = '"+userid+"'";
				List list = CustproductDao.QueryBySql(sqls);
				if(list.size()>0){
					sql += " and fid in (SELECT fcustproductid FROM t_bd_userrelationcustp  where fuserid = '"+userid+"')";
				}
				request.setAttribute("djsort", "fcreatetime asc");
				ListResult lresult = CustproductDao.QueryFilterList(sql, request);
				List<HashMap<String, Object>> data = lresult.getData();
				List<HashMap<String, Object>> removeData = new ArrayList<>();
				HashMap<String, Object> maps;
				Object val;
				
				for(HashMap<String, Object> map : data){	//我的订单批量新增时查库存量
					int fusedqty = 0;
					int amount;
					maps = baseSysDao.getProductAndStocksOfCustProduct(map.get("fid").toString());
					if(maps==null){
						map.put("balance", 0);
					}else{
						amount = (Integer)maps.get("famount");
						if(amount!=0){
							sql = "select sum(fusedqty) fusedqty from t_inv_usedstorebalance where fproductid='"+maps.get("fproductid").toString()+"'";
							val = ((HashMap<String, Object>)CustproductDao.QueryBySql(sql).get(0)).get("fusedqty");
							if(val!=null){
								fusedqty = Integer.valueOf(val+"");
								if(fusedqty<0){
									fusedqty = 0 ;
								}
								
							}
							map.put("balance", (amount-fusedqty)<0?0:amount-fusedqty);
						}else{
							map.put("balance", 0);
						}
						
					}
//					if((amount=baseSysDao.getStocksOfCustProduct(map.get("fid").toString()))==-1){
////						removeData.add(map);
//						map.put("balance", 0);
//					}else{
//						map.put("balance",amount);
//					}
				}
//				data.removeAll(removeData);
				reponse.getWriter().write(JsonUtil.result(true, "", lresult));
			} catch (DJException e) {
				reponse.getWriter().write(
						JsonUtil.result(false, e.getMessage(), "", ""));
			}

			return null;

		}
		/**
		 * 我的订单新增之包装物选择查询
		 * @param request
		 * @param reponse
		 * @return
		 * @throws IOException
		 */
		@RequestMapping("/selectCustProductList")
		public String selectCustProductList(HttpServletRequest request,
				HttpServletResponse reponse) throws IOException {
			 String userid =
					 ((Useronline)request.getSession().getAttribute("Useronline")).getFuserid();
			try {
				String sql = " select fid, fnumber, fname, fspec, ifnull(fcharactername,'') fcharactername, ifnull(fcharacterid,'') fcharacterid, fmaterial, ftilemodel, forderunit, fcustomerid, fdescription, fcreatorid, fcreatetime, flastupdateuserid, flastupdatetime, Frelationed FROM t_bd_Custproduct where 1 = 1 ";
					sql += CustproductDao.QueryFilterByUser(request, "fcustomerid",null);
				String sqls = "SELECT 1 FROM t_bd_userrelationcustp  where fuserid = '"+userid+"'";
				List list = CustproductDao.QueryBySql(sqls);
				if(list.size()>0){
					sql += "and fid in(SELECT fcustproductid FROM t_bd_userrelationcustp  where fuserid = '"+userid+"')";
				}
				request.setAttribute("djsort", "fcreatetime asc");
				ListResult lresult = CustproductDao.QueryFilterList(sql, request);
				reponse.getWriter().write(JsonUtil.result(true, "", lresult));
			} catch (DJException e) {
				reponse.getWriter().write(
						JsonUtil.result(false, e.getMessage(), "", ""));
			}

			return null;

		}
	
		@RequestMapping("/uploadCustProductImg")
		public String uploadCustProductImg(HttpServletRequest request,
				HttpServletResponse reponse) throws IOException { // , Mainmenuitem
			String result = "";
		
			try {
				

				
				CustproductDao.ExecUploadCustProductImg(request);

				result = JsonUtil.result(true, "保存成功!", "", "");

			} catch (Exception e) {

				result = JsonUtil.result(false, e.getMessage(), "", "");
			}
			reponse.getWriter().write(result); 
			return null;
		}
		
		
		
		@RequestMapping("/deleteCustProductAccessorys")
		public String deleteCustProductAccessorys(HttpServletRequest request,
				HttpServletResponse reponse) throws IOException { // , Mainmenuitem
			String result = "";
			try {
				
				String fids = request.getParameter("fidcls");
				String message = CustproductDao.ExecDelFile(fids);
				result = JsonUtil.result(true, (message==null)?"删除成功!":message, "", "");

			} catch (DJException e) {

				result = JsonUtil.result(false, e.getMessage(), "", "");
			}
			reponse.getWriter().write(result); 
			return null;
		}
		
		@RequestMapping("/gainUUID")
		public String gainUUID(HttpServletRequest request,
				HttpServletResponse reponse) throws IOException { 
			
			String result = "";
		
			try {
				
				String id = CustproductDao.CreateUUid();
				
				List<HashMap<String, Object>> listT = new ArrayList<HashMap<String,Object>>();
				
				HashMap<String, Object> hashMapT = new HashMap<String, Object>();
				
				hashMapT.put("id", id);
				
				listT.add(hashMapT);
				
				result = JsonUtil.result(true, "成功!", "",listT);

			} catch (Exception e) {

				result = JsonUtil.result(false, e.getMessage(), "", "");
			}
			reponse.getWriter().write(result); 
			return null;
		}
		
		
		
		/**
		 * 下单时查询客户产品
		 *
		 * @param request
		 * @param reponse
		 * @return
		 * @throws IOException
		 *
		 * @date 2014-11-26 上午10:48:10  
		 */
		@RequestMapping("/gainCusProductsByIds")
		public String gainCusProductByIds(HttpServletRequest request,
				HttpServletResponse reponse) throws IOException {
			
			String custProductidsS = request.getParameter("fids");
			
			String custProductidsConditon = "('" + custProductidsS.replaceAll(",", "','") + "')";
			
			
			 String userid =
					 ((Useronline)request.getSession().getAttribute("Useronline")).getFuserid();
			try {
				String sql = " select fid, fnumber, fname, fspec, ifnull(fcharactername,'') fcharactername, ifnull(fcharacterid,'') fcharacterid, fmaterial, ftilemodel, forderunit, fcustomerid, fdescription, fcreatorid, fcreatetime, flastupdateuserid, flastupdatetime, Frelationed FROM t_bd_Custproduct where 1 = 1 ";
					sql += CustproductDao.QueryFilterByUser(request, "fcustomerid",null);
				String sqls = "SELECT 1 FROM t_bd_userrelationcustp  where fuserid = '"+userid+"'";
				List list = CustproductDao.QueryBySql(sqls);
				if(list.size()>0){
					sql += "and fid in(SELECT fcustproductid FROM t_bd_userrelationcustp  where fuserid = '"+userid+"')";
				}
				
				//过滤
				sql += " and fid in " + custProductidsConditon;
				
				
				request.setAttribute("djsort", "fcreatetime asc");
				ListResult lresult = CustproductDao.QueryFilterList(sql, request);
				List<HashMap<String, Object>> data = lresult.getData();
				List<HashMap<String, Object>> removeData = new ArrayList<>();
				HashMap<String, Object> maps;
				int amount;
				for(HashMap<String, Object> map : data){	//我的订单批量新增时查库存量
					Object val;
					int fusedqty = 0;
					maps = baseSysDao.getProductAndStocksOfCustProduct(map.get("fid").toString());
					if(maps==null){
						map.put("balance",0);
					}else{

						amount = (Integer)maps.get("famount");
						if(amount!=0){
							sql = "select sum(fusedqty) fusedqty from t_inv_usedstorebalance where fproductid='"+maps.get("fproductid").toString()+"'";
							val = ((HashMap<String, Object>)CustproductDao.QueryBySql(sql).get(0)).get("fusedqty");
							if(val!=null){
								fusedqty = Integer.valueOf(val+"");
								if(fusedqty<0){
									fusedqty = 0 ;
								}
								
							}
							map.put("balance", (amount-fusedqty)<0?0:amount-fusedqty);
						}else{
							map.put("balance", 0);
						}
						
					
					}
//					if((amount=baseSysDao.getStocksOfCustProduct(map.get("fid").toString()))==-1){
//						removeData.add(map);
//					}else{
//						map.put("balance",amount);
//					}
				}
				data.removeAll(removeData);
				reponse.getWriter().write(JsonUtil.result(true, "", lresult));
			} catch (DJException e) {
				reponse.getWriter().write(
						JsonUtil.result(false, e.getMessage(), "", ""));
			}

			return null;

		}
		
		@RequestMapping("/gainCurrentCusID")
		public String gainCurrentCusID(HttpServletRequest request,
				HttpServletResponse reponse) throws IOException {
			ListResult lresult = new ListResult();
		
			try {
				
				List<String> custids = MySimpleToolsZ.getMySimpleToolsZ().gainCurrentUserRelCustmer(request, baseSysDao);
				
				if (custids.size() > 1) {
					
					throw new DJException("所关联的客户多于1个");
					
				} else if (custids.size() == 0) {
					
					throw new DJException("没有关联客户");
					
				}
				
				List<HashMap<String, Object>> listT = new ArrayList<HashMap<String,Object>>();
				
				HashMap<String, Object> mapT = new HashMap<>();
				
				mapT.put("fcustmerid", custids.get(0));
				
				listT.add(mapT);
				
				lresult.setData(listT);
				
				reponse.getWriter().write(JsonUtil.result(true, "", lresult));
			} catch (DJException e) {
				reponse.getWriter().write(
						JsonUtil.result(false, e.getMessage(), "", ""));
			}

			return null;

		}
	@RequestMapping("custProductFeffect")
	public void custProductFeffect(HttpServletRequest request,HttpServletResponse response) throws IOException{
		String fid = request.getParameter("fids");
		String type = request.getParameter("type");
		String result = "";
		try {
			if("0".equals(type)){//0 禁用
				CustproductDao.ExecCustpDisable(fid);
				result = JsonUtil.result(true, "禁用成功!", "", "");
			}else if("1".equals(type)){// 1 启用
				CustproductDao.ExecCustpEnabled(fid);
				result = JsonUtil.result(true, "启用成功!", "", "");
			}else{
				throw new DJException("非法操作！");
			}
			response.getWriter().write(result);
		} catch (DJException e) {
			// TODO: handle exception
			response.getWriter().write(
					JsonUtil.result(false, e.getMessage(), "", ""));
		}
	}
		
		/**
		 * 判断如果‘产品资料’已关联‘客户产品’，则‘客户产品’关联‘产品资料’提示不能关联；
		 */
		@RequestMapping(value = "/getCustProductsRelevance")
		public String getCustProductsRelevance(HttpServletRequest request,
				HttpServletResponse reponse) throws IOException {
			reponse.setCharacterEncoding("utf-8");
			try {
				String fproductid = request.getParameter("fproductid");
				String sql = "select * from t_pdt_productrelation r left join t_pdt_productrelationentry e on r.fid = e.fparentid where r.fproductid ='"+fproductid+"'";

				ListResult result = null;
				
					result = CustproductDao.QueryFilterList(sql,request);
				reponse.getWriter().write(JsonUtil.result(true, "", result));
			} catch (DJException e) {
				// TODO Auto-generated catch block
				reponse.getWriter().write(
						JsonUtil.result(false, e.getMessage(), "", ""));
			}

			return null;

		}
		/**
		 * 判断如果‘客户产品’已关联‘产品资料’，则‘产品资料’关联‘客户产品’提示不能关联；
		 */
		@RequestMapping(value = "/getProductsCustRelevance")
		public String getProductsCustRelevance(HttpServletRequest request,
				HttpServletResponse reponse) throws IOException {
			reponse.setCharacterEncoding("utf-8");
			try {
				String fcustproductid = request.getParameter("fcustproductid");
				String sql = "select * from t_pdt_custrelation r left join  t_pdt_custrelationentry e  on r.fid=e.fparentid where r.fcustproductid = '"+fcustproductid+"'";

				ListResult result = null;
				
					result = CustproductDao.QueryFilterList(sql,request);
				reponse.getWriter().write(JsonUtil.result(true, "", result));
			} catch (DJException e) {
				// TODO Auto-generated catch block
				reponse.getWriter().write(
						JsonUtil.result(false, e.getMessage(), "", ""));
			}

			return null;

		}
		/**
		 * 客户产品模版下载
		 */
		@RequestMapping("downloadCustExcel")
		public void downloadFile(HttpServletResponse response) throws IOException{
			response.setCharacterEncoding("utf-8");
			String path = "D:\\tomcat\\客户产品模版.xls";
			String name = path.substring(path.lastIndexOf("\\")+1);
				InputStream in = null;
				try {
					in = new FileInputStream(path);
				} catch (FileNotFoundException e) {
					throw new DJException("此附件文件不存在，无法下载！");
				}
				response.setContentType("application/x-msdownload");
				response.addHeader("Content-Disposition", "attachment; filename=\"" + new String(name.getBytes("UTF-8"),"iso-8859-1")+ "\"");
				OutputStream out = response.getOutputStream();
				byte[] bytes = new byte[1024];
				int len = 0;
				while((len = in.read(bytes,0,1024))!=-1){
					out.write(bytes, 0, len);
				}
				out.flush();
				in.close();
		}
		/**
		 * 客户产品禁止匹配功能（清除选中客户产品的发放产品匹配字段数据）
		 * @param request
		 * @param reponse
		 * @throws IOException
		 */
		@RequestMapping("cleanProductmatching")
		public void cleanProductmatching(HttpServletRequest request,
				HttpServletResponse reponse) throws IOException{
			try {
				String fids = request.getParameter("fids");
				fids = "'"+fids.replace(",", "','")+"'";
				String sql = "update t_bd_custproduct set fproductmatching = '' where fid in("+fids+")";
				CustproductDao.ExecBySql(sql);
				reponse.getWriter().write(JsonUtil.result(true,"禁止匹配成功！","",""));
			} catch (DJException e) {
				// TODO: handle exception
				reponse.getWriter().write(JsonUtil.result(false,"禁止匹配失败！","",""));
			}
		}
}
