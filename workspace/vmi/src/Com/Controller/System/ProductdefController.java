package Com.Controller.System;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URLDecoder;
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
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import Com.Base.Util.DJException;
import Com.Base.Util.DataUtil;
import Com.Base.Util.ExcelUtil;
import Com.Base.Util.JsonUtil;
import Com.Base.Util.ListResult;
import Com.Base.Util.params;
import Com.Dao.System.BaseSysDao;
import Com.Dao.System.IBaseSysDao;
import Com.Dao.System.IProductRelationDao;
import Com.Dao.System.IProductRelationEntryDao;
import Com.Dao.System.IProductdefDao;
import Com.Entity.System.CustRelationProduct;
import Com.Entity.System.Productdef;
import Com.Entity.System.Productrelation;
import Com.Entity.System.Productrelationentry;
import Com.Entity.System.Useronline;

@Controller
public class ProductdefController {

	/*
	 * 产品附件相对路径
	 */
	public static final String FILE_RELATIVE_PATH = "file/product/";
	
	private static final String 是通知类型的套装子件 = "有库存的通知类型普通产品不能更改为套装";
	private static final String 目标产品不能为子产品 = "目标产品不能为子件";
	private static final String 该产品存在未发货配送单 = "该产品存在未发货配送单，不能修改产品结构";

	Logger log = LoggerFactory.getLogger(ProductdefController.class);
	@Resource
	private IProductdefDao productdefDao;
	@Resource
	private IProductRelationDao productRelationDao;
	@Resource
	private IProductRelationEntryDao productRelationEntryDao;
	@Resource
	private IBaseSysDao baseSysDao;
	/**
	 * 建议字段命名一致性，这样就能避免很多不必要的操作
	 */
	private Map<String, String> storeToDbTMap = new HashMap<>();

	private static final int SUCCESS = 1;
	private static final int IS_LEAF = 2;
	private static final int IS_NOTICE_AND_SUIT_CHILD = 3;
	private static final int IS_UNFILLED_ORDER = 4;

	public ProductdefController() {

		storeToDbTMap.put("d_fname", "d.fname");
		storeToDbTMap.put("d_fnumber", "d.fnumber");
	}

	@RequestMapping(value = "/SaveProductdef")
	public String SaveProductdef(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		String result = "";
		Productdef pinfo = null;
		reponse.setCharacterEncoding("utf-8");
		try {
			String userid = ((Useronline) request.getSession().getAttribute(
					"Useronline")).getFuserid();
			String fid = request.getParameter("fid");
			String paramsT = request.getParameter("paramsT");
			if (fid != null && !"".equals(fid) && paramsT.equals("0")) {
				pinfo = productdefDao.Query(fid);

				if (pinfo != null && pinfo.getFishistory() == 1) {
					reponse.getWriter().write(
							JsonUtil.result(false, "历史版本的产品不能修改!", "", ""));
					return null;
				}
				if (pinfo != null && pinfo.getFaudited() == 1) {
					reponse.getWriter().write(
							JsonUtil.result(false, "已审核不能修改!", "", ""));
					return null;
				}
				pinfo.setFbaseid(request.getParameter("fbaseid"));
			} else {
				pinfo = new Productdef();
				if (paramsT.equals("1")) {
					pinfo.setFbaseid(request.getParameter("fbaseid"));
				}
				pinfo.setFid("");
				pinfo.setFcreatorid(userid);
				pinfo.setFcreatetime(new Date());
				pinfo.setFaudited(0);
			}
			pinfo.setFlastupdatetime(new Date());
			pinfo.setFlastupdateuserid(userid);
			pinfo.setFname(request.getParameter("fname"));
			pinfo.setFnumber(request.getParameter("fnumber"));
			pinfo.setFversion(request.getParameter("fversion"));
			pinfo.setFnewtype(request.getParameter("fnewtype"));
			pinfo.setFcustomerid(request.getParameter("fcustomerid"));
			pinfo.setForderunitid(request.getParameter("forderunitid"));
			pinfo.setFarea(new BigDecimal((request.getParameter("farea"))));
			pinfo.setFmaterialcost(new BigDecimal(request
					.getParameter("fmaterialcost")));
			pinfo.setFmaterialcodeid(request.getParameter("fmaterialcodeid"));
			pinfo.setFtilemodelid(request.getParameter("ftilemodelid"));
			pinfo.setFlayer(Integer.valueOf(request.getParameter("flayer") == null
					|| request.getParameter("flayer").equals("") ? "0"
					: request.getParameter("flayer")));
			pinfo.setFcleartype(request.getParameter("fcleartype"));
			pinfo.setFstavetype(request.getParameter("fstavetype"));
			pinfo.setFhstaveexp(request.getParameter("fhstaveexp"));
			pinfo.setFvstaveexp(request.getParameter("fvstaveexp"));
			if (request.getParameter("fboxlength") != null
					&& !request.getParameter("fboxlength").equals("")) {
				pinfo.setFboxlength(new BigDecimal(request
						.getParameter("fboxlength")));
			} else {
				pinfo.setFboxlength(null);
			}
			if (request.getParameter("fboxwidth") != null
					&& !request.getParameter("fboxwidth").equals("")) {
				pinfo.setFboxwidth(new BigDecimal(request
						.getParameter("fboxwidth")));
			} else {
				pinfo.setFboxwidth(null);
			}
			if (request.getParameter("fboxheight") != null
					&& !request.getParameter("fboxheight").equals("")) {
				pinfo.setFboxheight(new BigDecimal(request
						.getParameter("fboxheight")));
			} else {
				pinfo.setFboxheight(null);
			}
			if (request.getParameter("fboardlength") != null
					&& !request.getParameter("fboardlength").equals("")) {
				pinfo.setFboardlength(new BigDecimal(request
						.getParameter("fboardlength")));
			} else {
				pinfo.setFboardlength(null);
			}
			if (request.getParameter("fboardwidth") != null
					&& !request.getParameter("fboardwidth").equals("")) {
				pinfo.setFboardwidth(new BigDecimal(request
						.getParameter("fboardwidth")));
			} else {
				pinfo.setFboardwidth(null);
			}

			pinfo.setFmateriallength(new BigDecimal(request
					.getParameter("fmateriallength")));
			pinfo.setFmaterialwidth(new BigDecimal(request
					.getParameter("fmaterialwidth")));
			pinfo.setFboxmodelid(request.getParameter("fboxmodelid"));
			pinfo.setFcharacter(request.getParameter("fcharacter"));
			pinfo.setFchromaticprecision(new BigDecimal(request
					.getParameter("fchromaticprecision")));
			pinfo.setFquality(request.getParameter("fquality"));
			pinfo.setFcusproductname(request.getParameter("fcusproductname"));
			pinfo.setFdescription(request.getParameter("fdescription"));
			pinfo.setFmaterialcode(request.getParameter("fmaterialcode"));
			pinfo.setFmodelmethod(request.getParameter("fmodelmethod"));

			String forderprice = request.getParameter("forderprice");
			if (forderprice != null && !"true".equals(forderprice)) {
				pinfo.setForderprice(1);
			} else {
				pinfo.setForderprice(0);
			}
			pinfo.setFeffect(0);
			pinfo.setFishistory(0);
			HashMap<String, Object> params = productdefDao.ExecSave(pinfo);
			if (paramsT.equals("1")) {
				pinfo = productdefDao.Query(fid);
				pinfo.setFishistory(1);
				params = productdefDao.ExecSave(pinfo);
			}
			if (params.get("success") == Boolean.TRUE) {
				if (paramsT.equals("1")) {
					result = "{success:true,msg:'生成新版成功!'}";
				} else {
					result = "{success:true,msg:'产品保存成功!'}";
				}
			} else {
				if (paramsT.equals("1")) {
					result = "{success:true,msg:'生成新版失败!'}";
				} else {
					result = "{success:false,msg:'产品保存失败!'}";
				}
			}
		} catch (DJException e) {
			result = "{success:false,msg:'" + e.toString() + "'}";
		}

		reponse.getWriter().write(result);

		return null;

	}

	@RequestMapping(value = "GetProducts")
	public String GetProducts(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		reponse.setCharacterEncoding("utf-8");
		try {
			String sql = "select ifnull(d.fcharactername,'') fcharactername,d.fcharacterid,d.fid as d_fid,d.fname as d_fname,d.fnumber as d_fnumber,d.fcustomerid as d_fcustomerid,ifnull(d.fversion,'') d_fversion,ifnull(d.fcharacter,'') d_fcharacter,ifnull(d.fboxmodelid,'') d_fboxmodelid,ifnull(d.feffect,'') d_feffect,ifnull(d.fnewtype,'') d_fnewtype,ifnull(d.fishistory,'') d_fishistory,ifnull(d.flastupdateuserid,'') d_flastupdateuserid,ifnull(u2.fname,'') u2_fname, ifnull(u3.fname,'') u3_fname,ifnull(d.faudittime,'') d_faudittime,ifnull(d.faudited,'') as d_faudited,ifnull(d.flastupdatetime,'') d_flastupdatetime,ifnull(d.fcreatetime,'') d_fcreatetime,ifnull(d.fcreatorid,'') d_fcreatorid,ifnull(u1.fname,'') u1_fname,ifnull(d.schemedesignid,'') schemedesignid FROM t_pdt_productdef d left join t_sys_user u1 on  u1.fid=d.fcreatorid left join t_sys_user u2 on  u2.fid=d.flastupdateuserid  left join t_sys_user u3 on  u3.fid=d.fauditorid where 1=1  ";
			String fcustomerid = request.getParameter("fcustomerid");
			if (!"".equals(fcustomerid)) {
				sql += " and d.fcustomerid='" + fcustomerid + "'";
			} else {
				sql += productdefDao.QueryFilterByUser(request,
						"d.fcustomerid", null);
			}
			ListResult result;

			result = productdefDao.QueryFilterList(sql, request);
			reponse.getWriter().write(JsonUtil.result(true, "", result));
		} catch (DJException e) {
			reponse.getWriter().write(
					JsonUtil.result(false, e.getMessage(), "", ""));
		}

		return null;

	}

	@RequestMapping(value = "/GetProduct")
	public String GetProduct(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {

		reponse.setCharacterEncoding("utf-8");
		try {
			String id = request.getParameter("fid");

			/**
			 * 为确保只有一个ID进行查询
			 * 
			 * @author zjz
			 */
			id = translatePID(id);

			String sql = "select d.fid,d.fname,d.fnumber,d.fversion,d.fcharacter,d.fnewtype,d.fboxmodelid,d.fcustomerid  ,d.farea,d.forderunitid,d.fmaterialcodeid,d.fcusproductname,d.fmaterialcost,d.ftilemodelid,d.flayer,d.fcleartype,d.fstavetype,ifnull(d.fhstaveexp,'') fhstaveexp,ifnull(d.fvstaveexp,'') fvstaveexp,d.fboxlength,d.fboxwidth,d.fboxheight,d.fmateriallength,d.fmaterialwidth,d.fboardlength,d.fboardwidth,d.fchromaticprecision,d.fquality,d.fdescription,d.forderprice,d.fmaterialcode,c.fname cname,d.fbaseid,d.fishistory,d.faudited,d.fmodelmethod  FROM t_pdt_productdef d left join t_bd_customer c on c.fid=d.fcustomerid where d.fid='"
					+ id + "'";
			;
			List<HashMap<String, Object>> sList = productdefDao.QueryBySql(sql);
			reponse.getWriter().write(JsonUtil.result(true, "", "", sList));

		} catch (Exception e) {
			reponse.getWriter().write(
					JsonUtil.result(false, e.toString(), "", ""));
		}
		return null;

	}

	/**
	 * 非历史版本fishistory=0产品才能生成新版;
	 */
	@RequestMapping(value = "/GetVersionProduct")
	public String GetVersionProduct(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {

		reponse.setCharacterEncoding("utf-8");
		try {
			String id = request.getParameter("fid");

			id = translatePID(id);

			String sql = "select d.fid,d.fname,d.fnumber,d.fversion,d.fcharacter,d.fnewtype,d.fboxmodelid,d.fcustomerid  ,d.farea,d.forderunitid,d.fmaterialcodeid,d.fcusproductname,d.fmaterialcost,d.ftilemodelid,d.flayer,d.fcleartype,d.fstavetype,d.fhstaveexp,d.fvstaveexp,d.fboxlength,d.fboxwidth,d.fboxheight,d.fmateriallength,d.fmaterialwidth,d.fboardlength,d.fboardwidth,d.fchromaticprecision,d.fquality,d.fdescription,d.forderprice,d.fmaterialcode,c.fname cname,d.fbaseid,d.fishistory,d.faudited  FROM t_pdt_productdef d left join t_bd_customer c on c.fid=d.fcustomerid where d.fishistory=0 and d.fid='"
					+ id + "'";
			;
			List<HashMap<String, Object>> sList = productdefDao.QueryBySql(sql);
			reponse.getWriter().write(JsonUtil.result(true, "", "", sList));
		} catch (Exception e) {
			reponse.getWriter().write(
					JsonUtil.result(false, e.toString(), "", ""));
		}
		return null;

	}

	@RequestMapping(value = "/deleteProducts")
	public String deleteProducts(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {

		String result = "";
		String fidcls = request.getParameter("fidcls");
		String fids = fidcls;
		fidcls = "('" + fidcls.replace(",", "','") + "')";
		if (DataUtil.isforeignKeyConstraintLegal("t_ord_productplan",
				"FPRODUCTDEFID", productdefDao, fidcls)) {
			try {
				String isHistory = productdefDao.QueryIsHistory(fidcls);
				List list = productdefDao.QueryByHql("select 1 from Productdef where (schemedesignid!=null or schemedesignid!='') and fid in"+fidcls);
				if(list.size()>0){
					result = "{success:false,msg:'" + "该产品已经有方案设计,不能删除!" + "'}";
					reponse.getWriter().write(result);
					return null;
				}
				String custIds = (String) baseSysDao.getCustProductsByProducts(fids,BaseSysDao.STRING);
				custIds = "('" + custIds.replace(",", "','") + "')";
				if(productdefDao.QueryExistsBySql("select 1 from t_ord_deliverapply where fcusproductid in "+custIds)){
					result = "{success:false,msg:'" + "选中的产品已生成订单，不能删除！" + "'}";
					reponse.getWriter().write(result);
					return null;
				}
				if (!"false".equals(isHistory)) {
					result = "{success:false,msg:'" + isHistory + "'}";
				} else {
					String hql = "Delete FROM Productdef where fid in "
							+ fidcls;
					productdefDao.ExecByHql(hql);
					result = "{success:true,msg:'删除成功!'}";
				}

			} catch (DJException e) {
				result = "{success:false,msg:'"
						+ e.toString().replaceAll("'", "") + "'}";
				log.error("DelUserList error", e);
			}

		} else {
			result = "{success:false,msg:'" + "违法外键约束，本实体已被其他地方引用" + "'}";
		}

		reponse.getWriter().write(result);
		return null;
	}

	@RequestMapping("/AddProductdef")
	public String AddProductdef(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		reponse.setCharacterEncoding("utf-8");
		reponse.getWriter().write(JsonUtil.result(true, "", "", ""));
		return null;

	}

	@RequestMapping(value = "/GetProductss")
	public String GetProductss(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		reponse.setCharacterEncoding("utf-8");
		try {
			String sql = "select d.fid ,d.fname ,d.fnumber ,d.fversion ,d.fcharacter,d.fboxmodelid ,d.feffect ,d.fnewtype ,d.fishistory ,d.flastupdateuserid ,u2.fname u2_fname,ifnull(u3.fname,'') u3_fname,ifnull(d.faudittime,'') faudittime,d.faudited , d.flastupdatetime ,d.fcreatetime ,d.fcreatorid ,u1.fname u1_fname FROM t_pdt_productdef d left join t_sys_user u1 on  u1.fid=d.fcreatorid left join t_sys_user u2 on  u2.fid=d.flastupdateuserid left join t_sys_user u3 on  u3.fid=d.fauditorid where 1=1";
			String fcustomerid = request.getParameter("fcustomerid");
			if (!"".equals(fcustomerid) && fcustomerid != null) {
				sql += " and d.fcustomerid='" + fcustomerid + "'";
			} else {
				sql += productdefDao.QueryFilterByUser(request,
						"d.fcustomerid", null);
			}
			ListResult result;

			result = productdefDao.QueryFilterList(sql, request);
			reponse.getWriter().write(JsonUtil.result(true, "", result));
		} catch (DJException e) {
			reponse.getWriter().write(
					JsonUtil.result(false, e.getMessage(), "", ""));
		}

		return null;

	}

	@RequestMapping(value = "/producttoexcel")
	public String producttoexcel(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		try {
			String sql = "select d.fid ,d.fname 名称,d.fnumber 编码,ifnull(d.fversion,'') 版本,ifnull(d.fcharacter,'') 特性,ifnull(d.fboxmodelid,'') 箱型结构,ifnull(d.feffect,'') feffect ,ifnull(d.fnewtype,'') 产品类型 ,ifnull(d.fishistory,'') 历史版本 ,ifnull(u2.fname,'') 修改人,ifnull(d.flastupdatetime,'') 修改时间 ,ifnull(d.fcreatetime,'') 创建时间 , ifnull(u1.fname,'') 创建者  FROM t_pdt_productdef d left join t_sys_user u1 on  u1.fid=d.fcreatorid left join t_sys_user u2 on  u2.fid=d.flastupdateuserid where 1=1 ";
			String fcustomerid = request.getParameter("fcustomerid");
			if (!"".equals(fcustomerid) && fcustomerid != null) {
				sql += " where d.fcustomerid='" + fcustomerid + "'";
			} else {
				sql += productdefDao.QueryFilterByUser(request,
						"d.fcustomerid", null);
			}
			ListResult result;
			result = productdefDao.QueryFilterList(sql, request);
			ExcelUtil.toexcel(reponse, result);
		} catch (DJException e) {
			reponse.getWriter().write(
					JsonUtil.result(false, e.getMessage(), "", ""));
		}

		return null;

	}

	@RequestMapping(value = "/GetProductInfo")
	public String GetProductInfo(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {

		reponse.setCharacterEncoding("utf-8");
		try {
			String sql = "select d.fid,d.fname,d.fnumber,d.fversion,d.fcharacter,d.fnewtype,d.fboxmodelid,d.fcustomerid as fcustomerid_fid,d.farea,d.forderunitid,d.fmaterialcodeid,d.fcreatetime ,d.fcusproductname,d.fmaterialcost,d.ftilemodelid,d.flayer,d.fcleartype,d.fstavetype,d.fhstaveexp,d.fvstaveexp,d.fboxlength,d.fboxwidth,d.fboxheight,d.fmateriallength,d.fmaterialwidth,d.fboardlength,d.fboardwidth,d.fchromaticprecision,d.fquality,d.fdescription,d.forderprice,d.fmaterialcode,c.fname as  fcustomerid_fname FROM t_pdt_productdef d left join t_bd_customer c on c.fid=d.fcustomerid where d.fid='"
					+ request.getParameter("fid") + "'";
			;
			List<HashMap<String, Object>> sList = productdefDao.QueryBySql(sql);
			reponse.getWriter().write(JsonUtil.result(true, "", "", sList));
		} catch (Exception e) {
			reponse.getWriter().write(
					JsonUtil.result(false, e.toString(), "", ""));
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
	@RequestMapping(value = "/GetProductStructTree")
	public String GetProductStructTree(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		String result = "{children:[";
		String sql = "select  distinct CONCAT(s.fid,',',f.fparentid) id,s.fname text, s.fnumber, f.FAMOUNT as mcount, f.FID as dbId,"
				+ " case ifnull(s2.fparentid,'') when '' then 1 else 0 end isleaf ,f.fparentid parentId "
				+ " from t_pdt_productdefproducts f"
				+ " left join  t_pdt_productdef s  on f.fproductid=s.fid"
				+ " left join   t_pdt_productdefproducts  s2 on s2.fparentid=s.fid";
		String fparentid = request.getParameter("id");
		if (fparentid != null) {
			String[] ids = fparentid.split(",");
			sql += " where f.fparentid='" + ids[0] + "'";
		}
		List<HashMap<String, Object>> sList = productdefDao.QueryBySql(sql);
		result += JsonUtil.List2Json(sList) + "]}";
		reponse.setCharacterEncoding("utf-8");
		reponse.getWriter().write(result);
		return null;
	}

	@RequestMapping("/AddProductRelationCust")
	public String AddProductRelationCust(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		reponse.setCharacterEncoding("utf-8");
		try {
			String sql = "";
			String fidcls = request.getParameter("fidcls");
			fidcls = "('" + fidcls.replace(",", "','") + "')";
			String productid = request.getParameter("myobjectid");
			String[] tArrayofFid = DataUtil.InStrToArray(fidcls);
			for (int i = 0; i < tArrayofFid.length; i++) {
				sql = "SELECT fid FROM t_sys_custrelationproduct where fproductdefid = :fproductdefid and fcustid = :fcustid ;";// SELECT
																																// *

				params paramsT1 = new params();
				paramsT1.setString("fproductdefid", productid);
				paramsT1.setString("fcustid", tArrayofFid[i]);

				if (this.productdefDao.QueryBySql(sql, paramsT1).size() == 0) {
					CustRelationProduct info = new CustRelationProduct();
					info.setFid(productdefDao.CreateUUid());
					info.setFcustid(tArrayofFid[i]);
					info.setFproductdefid(productid);
					productdefDao.saveOrUpdate(info);
				}
			}
			reponse.getWriter().write(JsonUtil.result(true, "关联成功", "", ""));
		} catch (Exception e) {
			reponse.getWriter().write(
					JsonUtil.result(false, e.getMessage(), "", ""));
		}

		return null;
	}

	@RequestMapping("/DelProductRelationCust")
	public String DelProductRelationCust(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		reponse.setCharacterEncoding("utf-8");
		try {
			String sql = "delete from t_sys_custrelationproduct where fproductdefid=:fproductdefid  and fcustid in ";
			String fidcls = request.getParameter("fidcls");
			fidcls = "('" + fidcls.replace(",", "','") + "')";
			String productid = request.getParameter("myobjectid");
			sql = sql + fidcls;
			params paramsT = new params();
			paramsT.setString("fproductdefid", productid);
			productdefDao.ExecBySql(sql, paramsT);
			reponse.getWriter().write(JsonUtil.result(true, "取消成功", "", ""));
		} catch (Exception e) {
			reponse.getWriter().write(
					JsonUtil.result(false, e.getMessage(), "", ""));
		}
		return null;
	}

	@RequestMapping("/GetProductRelationCustList")
	public String GetProductRelationCustList(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		String sql = "SELECT c.FNAME as fname, c.FNUMBER as fnumber, c.FID as fid , c.fspec fspec ,c.fdescription  as fdescription FROM t_sys_custrelationproduct l, t_bd_Custproduct c where l.fcustid =  c.FID ";
		ListResult result;
		reponse.setCharacterEncoding("utf-8");
		try {
			result = this.productdefDao.QueryFilterList(sql, request);
			reponse.getWriter().write(JsonUtil.result(true, "", result));
		} catch (DJException e) {
			reponse.getWriter().write(
					JsonUtil.result(false, e.getMessage(), "", ""));
		}

		return null;

	}

	@RequestMapping(value = "/auditProductdef")
	public String auditProductdef(HttpServletRequest request,
			HttpServletResponse reponse) throws Exception {
		String result = "";
		Productdef pinfo = null;
		try {
			String userid = ((Useronline) request.getSession().getAttribute(
					"Useronline")).getFuserid();
			String fid = request.getParameter("fid");
			if (fid != null && !"".equals(fid)) {
				pinfo = productdefDao.Query(fid);
				if (pinfo.getFishistory() == 1) {
					throw new DJException("历史版本不能进行审核！");
				} else if (pinfo.getFaudited() == 1) {
					throw new DJException("已审核无需审核！");
				} else {
					pinfo.setFaudited(1);
					pinfo.setFauditorid(userid);
					pinfo.setFaudittime(new Date());
					HashMap<String, Object> params = productdefDao
							.ExecSave(pinfo);
					if (params.get("success") == Boolean.TRUE) {
						result = JsonUtil.result(true, "审核成功!", "", "");
					} else {
						result = JsonUtil.result(false, "审核失败!", "", "");
					}
				}
			} else {
				throw new DJException("未保存不能审核！");
			}
		} catch (Exception e) {
			result = "{success:false,msg:'" + e.getMessage() + "'}";
		}
		reponse.setCharacterEncoding("utf-8");
		reponse.getWriter().write(result);
		return null;
	}

	@RequestMapping(value = "/unauditProductdef")
	public String unauditProductdef(HttpServletRequest request,
			HttpServletResponse reponse) throws Exception {
		String result = "";
		Productdef pinfo = null;
		try {
			String fid = request.getParameter("fid");
			if (fid != null && !"".equals(fid)) {
				pinfo = productdefDao.Query(fid);

				if (pinfo.getFishistory() == 1) {
					throw new DJException("历史版本不能进行反审核！");
				} else if (pinfo.getFaudited() == 0) {
					throw new DJException("未审核无需反审核！");
				} else {
					pinfo.setFauditorid(null);
					pinfo.setFaudited(0);
					pinfo.setFaudittime(null);
					HashMap<String, Object> params = productdefDao
							.ExecSave(pinfo);
					if (params.get("success") == Boolean.TRUE) {
						result = JsonUtil.result(true, "反审核成功!", "", "");
					} else {
						result = JsonUtil.result(false, "反审核失败!", "", "");
					}
				}
			} else {
				throw new DJException("未保存不能反审核！");
			}
		} catch (Exception e) {
			result = JsonUtil.result(false, e.getMessage(), "", "");
		}
		reponse.setCharacterEncoding("utf-8");
		reponse.getWriter().write(result);
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
	@RequestMapping(value = "/getProductsWithoutTreeNode")
	public String getProductsWithoutTreeNode(HttpServletRequest request,
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
			String sql = "select d.fid as d_fid,d.fname as d_fname,d.fnumber as d_fnumber,d.fcustomerid as d_fcustomerid,d.fversion as d_fversion,d.fcharacter d_fcharacter,d.fboxmodelid d_fboxmodelid,d.feffect d_feffect,d.fnewtype d_fnewtype,d.fishistory d_fishistory,d.flastupdateuserid d_flastupdateuserid,u2.fname u2_fname, d.flastupdatetime d_flastupdatetime,d.fcreatetime d_fcreatetime,d.fcreatorid d_fcreatorid,u1.fname u1_fname FROM t_pdt_productdef d left join t_sys_user u1 on  u1.fid=d.fcreatorid left join t_sys_user u2 on  u2.fid=d.flastupdateuserid where d.FID <> '%s' and d.FID not in (select FPARENTID from t_pdt_productdefproducts where FPRODUCTID = '%s' union select FPRODUCTID from t_pdt_productdefproducts where FPARENTID = '%s' union SELECT distinct FPARENTID FROM t_pdt_productdefproducts) ";
			String fid = request.getParameter("dbfid");

			String filterCondit = translateToQS(request.getParameter("filter"));

			sql = String.format(sql, fid, fid, fid);

			ListResult result = null;

			if (filterCondit != null) {

				sql += filterCondit;
				/**
				 * 查询结果不是ListResult的实例？待统一
				 */
//				List result1 = productdefDao.QueryBySql(sql);
//				result = new ListResult();
//				result.setData(result1);
//				result.setTotal(productdefDao.QueryCountBySql(sql));
				
				
				result = productdefDao.QueryFilterList(sql, request);
				
				

			} else {

				result = productdefDao.QueryFilterList(sql, request);

			}

			reponse.getWriter().write(JsonUtil.result(true, "", result));
		} catch (DJException e) {
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
		String r = null;
		if (parameter != null && !parameter.equals("")) {
			JSONArray jsa = JSONArray.fromObject(parameter);
			JSONObject jso = jsa.getJSONObject(jsa.size() - 1);

			String property = jso.getString("property");
			String value = jso.getString("value");

			r = " and %s like '%s' ";
			r = String
					.format(r, storeToDbTMap.get(property), "%" + value + "%");
		}

		return r;
	}

	@RequestMapping(value = "/addProductStructTree")
	public String addProductStructTree(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		String result = "";

		boolean succes = true;

		String reqS = request.getParameter("data");

		JSONArray jsa = DataUtil.getJsonArrayByS(reqS);

		for (int i = 0; i < jsa.size(); i++) {

			String id = ((JSONObject) jsa.get(i)).getString("id");
			String pId = ((JSONObject) jsa.get(i)).getString("parentId");// .split(",")[0]
			int count = ((JSONObject) jsa.get(i)).getInt("mcount");

			// 添加单引号，原生sql的要求，但尽量避免，原生sql操作是有重大安全漏洞的
			String sql = "INSERT INTO t_pdt_productdefproducts (FID, FPRODUCTID, FPARENTID, FAMOUNT) VALUES (\'%s\', \'%s\', \'%s\', %d) ";

			// params p = new params();
			//
			// p.setString("id", id);
			// p.setString("fid", productdefDao.CreateUUid());
			// p.setString("pid", pId);
			// p.setInt("count", count);

			pId = translatePID(pId);
			id = translatePID(id);

			try {
				if (buildVerifyProudctNodeString(pId).equals("true")) {
					String fsql = String.format(sql,
							productdefDao.CreateUUid(), id, pId, count);
					productdefDao.ExecBySql(fsql);
					updateTheProductType(pId, true);

				} else {
					succes = false;

					result = JsonUtil.result(false,
							buildVerifyProudctNodeString(pId), "", "");
				}

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

	/**
	 * 
	 * 
	 * @param pId
	 * @param b
	 *            ,设置为套装? false,设置为产品
	 * 
	 * @date 2014-6-17 下午5:05:52 (ZJZ)
	 */
	private void updateTheProductType(String pId, boolean b) {

		Productdef product = productdefDao.Query(pId);

		String s = b ? "4" : "3";

		// 如果是目标值，就无需修改
		if (product.getFnewtype().equals(s)) {

			return;

		}

		product.setFnewtype(s);

		productdefDao.ExecSave(product);

	}

	@RequestMapping(value = "/updateProductStructTree")
	public String updateProductStructTree(HttpServletRequest request,
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
			String sql = "UPDATE t_pdt_productdefproducts SET FAMOUNT=:count, FPARENTID=:pid, FPRODUCTID = :id WHERE  FID = :fid  ;";

			params p = new params();

			p.setString("id", id);
			// p.setString("fid", productdefDao.CreateUUid());
			p.setString("pid", pId);
			p.setInt("count", count);
			p.setString("fid", fid);

			try {
				if (buildVerifyProudctNodeString(pId).equals("true")) {

					productdefDao.ExecBySql(sql, p);
				} else {
					succes = false;
					result = JsonUtil.result(false,
							buildVerifyProudctNodeString(pId), "", "");
				}

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

	/**
	 * treenode数据库数据定义问题？不得已在一个方法中同时处理u和c操作
	 * 
	 * @param request
	 * @param reponse
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/updateAndCreateProductStructTree")
	public String updateAndCreateProductStructTree(HttpServletRequest request,
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
		String isHistory = productdefDao.QueryIsHistory("('" + parentId + "')");
		if ("false".equals(isHistory)) {
			if (depth <= 1) {
				if (dbId.equals("")) {
					addProductStructTree(request, reponse);
				} else {
					updateProductStructTree(request, reponse);

				}
			} else {
				reponse.getWriter().write(
						JsonUtil.result(false, "!(depth <= 1)", "", ""));
			}
		} else {
			reponse.getWriter()
					.write(JsonUtil.result(false, isHistory, "", ""));
		}

		return null;
	}

	private String translatePID(String pId) {


		if (pId.split(",").length > 1) {
			pId = pId.split(",")[0];

		}

		return pId;
	}

	/**
	 * 
	 * 
	 * @param pId
	 * @return 信息，如果是"true"表示没问题
	 * 
	 * @date 2014-6-17 下午3:46:49 (ZJZ)
	 */
	private String buildVerifyProudctNodeString(String pId) {

		switch (verifyIdAndPid(pId)) {
		case ProductdefController.IS_LEAF:
			return 目标产品不能为子产品;
		case ProductdefController.IS_NOTICE_AND_SUIT_CHILD:
			return 是通知类型的套装子件;

		case ProductdefController.IS_UNFILLED_ORDER:
			return 该产品存在未发货配送单;
		default:

			break;
		}

		return "true";
	}

	/**
	 * 
	 * 保存验证
	 * 
	 * @param pId
	 * 
	 * 
	 * @return 通过？ProductdefController.SUCCESS,1
	 */
	private int verifyIdAndPid(String pId) {

		if (isLeaf(pId)) {

			return ProductdefController.IS_LEAF;

		}

		if (isNoticeSuitChild(pId)) {

			return ProductdefController.IS_NOTICE_AND_SUIT_CHILD;
		}

		if (isUnfilledOrde(pId)) {

			return ProductdefController.IS_UNFILLED_ORDER;

		}

		return ProductdefController.SUCCESS;

	}

	private boolean isUnfilledOrde(String pId) {

		String sql = " SELECT 1 FROM t_ord_deliverorder where fouted = 0 and fproductid = '%s' ";

		sql = String.format(sql, pId);

		int size = productdefDao.QueryBySql(sql).size();

		return size != 0;
	}

	private boolean isNoticeSuitChild(String pId) {

		String sql = "SELECT count(*) as theCount FROM t_inv_storebalance where fproductid = '%s' and ftype = 0 and fbalanceqty <> 0;";

		sql = String.format(sql, pId);

		List<HashMap<String, Object>> result = productdefDao.QueryBySql(sql);

		return !((BigInteger) result.get(0).get("theCount"))
				.equals(BigInteger.ZERO);
	}

	private boolean isLeaf(String pId) {
		// 根据设计，如果是子节点，会在目标table的fproductid增加记录
		String sql = "SELECT count(*) as thisLeafcount FROM t_pdt_productdefproducts where fproductid = '%s'";

		sql = String.format(sql, pId);

		List<HashMap<String, Object>> result = productdefDao.QueryBySql(sql);

		return !((BigInteger) result.get(0).get("thisLeafcount"))
				.equals(BigInteger.ZERO);
	}

	@RequestMapping("/deleteProductStructTreeNodes")
	public String deleteProductStructTreeNodes(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		reponse.setCharacterEncoding("utf-8");

		String result = "";

		boolean succes = true;

		String reqS = request.getParameter("data");

		JSONArray jsa = DataUtil.getJsonArrayByS(reqS);
		String parentId = ((JSONObject) jsa.get(0)).getString("parentId")
				.toString();
		parentId = translatePID(parentId);
		String isHistory = productdefDao.QueryIsHistory("('" + parentId + "')");

		if (isUnfilledOrde(parentId)) {

			result = JsonUtil.result(false, 该产品存在未发货配送单, "", "");

			reponse.getWriter().write(result);
			
			return null;
		}

		// 再加就有点混乱了，故在上面进行未发货的订单判断操作
		if ("false".equals(isHistory)) {

			for (int i = 0; i < jsa.size(); i++) {

				String id = ((JSONObject) jsa.get(i)).getString("id");
				String pId = ((JSONObject) jsa.get(i)).getString("parentId");// .split(",")[0]

				String dbId = ((JSONObject) jsa.get(i)).getString("dbId");

				int count = ((JSONObject) jsa.get(i)).getInt("mcount");

				id = translatePID(id);
				pId = translatePID(pId);
				dbId = translatePID(dbId);

				String sql = "delete from t_pdt_productdefproducts where FID = :dbid";

				params p = new params();

				// p.setString("id", id);
				// p.setString("pid", pId);

				p.setString("dbid", dbId);

				try {

					productdefDao.ExecBySql(sql, p);

					// productdefDao.ExecBySql(sql)

					// 没有子节点时，设置为普通产品
					if (judageWhetherThereISNoChilrenForTheGoalNode(pId)) {
						updateTheProductType(pId, false);
					}

				} catch (Exception e) {

					succes = false;
					result = JsonUtil.result(false, e.toString(), "", "");

				}
			}

			if (succes) {
				result = JsonUtil.result(true, "", "", "");
			}
		} else {
			result = JsonUtil.result(false, isHistory, "", "");
		}

		reponse.getWriter().write(result);

		return null;
	}

	/**
	 * 
	 * 
	 * @param pId
	 * @return 没有子节点？
	 * 
	 * @date 2014-6-17 下午4:45:47 (ZJZ)
	 */
	private boolean judageWhetherThereISNoChilrenForTheGoalNode(String pId) {


		String sql = "SELECT count(*) as theCount FROM t_pdt_productdefproducts where fparentid = '%s';";

		sql = String.format(sql, pId);

		List<HashMap<String, Object>> result = productdefDao.QueryBySql(sql);

		return ((BigInteger) result.get(0).get("theCount"))
				.equals(BigInteger.ZERO);
	}

	@RequestMapping(value = "/effectProducts")
	public String effectProducts(HttpServletRequest request,
			HttpServletResponse reponse) throws Exception {

		String result = "";
		String fidcls = request.getParameter("fidcls");
		fidcls = "('" + fidcls.replace(",", "','") + "')";
		String hql = "";
		try {
			String isHistory = productdefDao.QueryIsHistory(fidcls);
			if (!"false".equals(isHistory)) {
				throw new DJException("启用失败!" + isHistory);
			}
			hql = "select fid from Productdef s  where s.feffect=1  and s.fid in "
					+ fidcls;
			List auditResult = productdefDao.QueryByHql(hql);
			if (auditResult.size() > 0) {
				throw new DJException("启用失败!其中" + auditResult.size()
						+ "款产品已经启用！");
			}
			hql = "update FROM Productdef set feffect=1 where fid in " + fidcls;
			productdefDao.ExecByHql(hql);
			result = "{success:true,msg:'启用成功!'}";
			reponse.setCharacterEncoding("utf-8");
		} catch (Exception e) {
			result = "{success:false,msg:'" + e.getMessage() + "'}";
			log.error("DelUserList error", e);
		}
		reponse.getWriter().write(result);
		return null;

	}

	@RequestMapping(value = "/forbiddenProduct")
	public String forbiddenProduct(HttpServletRequest request,
			HttpServletResponse reponse) throws Exception {

		String result = "";
		String fidcls = request.getParameter("fidcls");
		fidcls = "('" + fidcls.replace(",", "','") + "')";
		String hql = "";
		try {
			String isHistory = productdefDao.QueryIsHistory(fidcls);
			if (!"false".equals(isHistory)) {
				throw new DJException("禁用失败!" + isHistory);
			}
			hql = "select fid from Productdef s  where s.feffect=0  and s.fid in "
					+ fidcls;
			List auditResult = productdefDao.QueryByHql(hql);
			if (auditResult.size() > 0) {
				throw new DJException("禁用失败!其中" + auditResult.size()
						+ "款产品已经禁用！");
			}

			hql = "update FROM Productdef set feffect=0 where fid in " + fidcls;
			productdefDao.ExecByHql(hql);
			result = "{success:true,msg:'禁用成功!'}";
			reponse.setCharacterEncoding("utf-8");
		} catch (Exception e) {
			result = "{success:false,msg:'" + e.getMessage() + "'}";
			log.error("DelUserList error", e);
		}
		reponse.getWriter().write(result);
		return null;

	}

	@RequestMapping(value = "/updateAndCreateProductRelationTree")
	public String updateAndCreateProductRelationTree(
			HttpServletRequest request, HttpServletResponse reponse)
			throws Exception {

		try {
			String reqS = request.getParameter("data");

			JSONArray jsa = DataUtil.getJsonArrayByS(reqS);
			JSONObject obj = (JSONObject) jsa.get(0);
			// String dbId = ((JSONObject) jsa.get(0)).getString("dbId");
			// String dbId = ((JSONObject) jsa.get(0)).getString("id");

			/**
			 * 后台树深度验证，更严谨
			 */
			int depth = Integer.parseInt(((JSONObject) jsa.get(0))
					.getString("depth"));
			String parentId = ((JSONObject) jsa.get(0)).getString("parentId")
					.toString();
			if (parentId.indexOf(',') > 0) {
				parentId = "('" + parentId.substring(0, parentId.indexOf(','))
						+ "')";
			} else {
				parentId = "( select fproductid from t_pdt_productrelation where fid='"
						+ parentId + "')";
			}
			String isHistory = productdefDao.QueryIsHistory(parentId);
			if ("false".equals(isHistory)) {
				if (depth <= 1) {

					if (obj.containsKey("id") == false || obj.getString("id").isEmpty()) {
						addProductRelationTree(request, reponse);

					} else {
						updateProductRelationTree(request, reponse);

					}
				} else {
					reponse.getWriter().write(
							JsonUtil.result(false, "!(depth <= 1)", "", ""));
				}
			} else {
				reponse.getWriter().write(
						JsonUtil.result(false, isHistory, "", ""));
			}
			// reponse.getWriter().write(result);
		} catch (Exception e) {
			reponse.getWriter().write(
					JsonUtil.result(false, "失败：" + e.getMessage(), "", ""));

		}
		return null;
	}

	/**
	 * 
	 * @param parentId
	 *            查询父ID，不存在就新建
	 * @return
	 * @throws Exception
	 */
	private String createProductRelation(String parentId) throws Exception {
		Productrelation rinfo = null;
		String[] values = parentId.split(",");
		try {
			String fcustomerid = values[1];
			String fproductid = values[0];
			String hql = " from Productrelation where fproductid='"
					+ fproductid + "' and fcustomerid='" + fcustomerid + "'";
			List list = productRelationDao.QueryByHql(hql);
			if (list.size() > 0) {
				rinfo = (Productrelation) list.get(0);
			} else {
				rinfo = new Productrelation();
				rinfo.setFcustomerid(fcustomerid);
				rinfo.setFproductid(fproductid);
				rinfo.setFid(productRelationDao.CreateUUid());
				productRelationDao.ExecSave(rinfo);
			}

		} catch (Exception e) {
			throw new DJException("失败！" + e.getMessage());

		}
		return rinfo.getFid();

	}

	@RequestMapping(value = "/addProductStructTree")
	public String addProductRelationTree(HttpServletRequest request,
			HttpServletResponse reponse) throws Exception {
		String result = "";
		String sqlstr = "";
		String fproductid = "";
		String fcustproid = "";
		boolean succes = true;

		String reqS = request.getParameter("data");
		JSONArray jsa = DataUtil.getJsonArrayByS(reqS);
		String pId = "";
		String parentId = ((JSONObject) jsa.get(0)).getString("parentId");// .split(",")[0]
		if (parentId.indexOf(",") > 0) {
			pId = createProductRelation(parentId);
			fproductid = parentId.split(",")[0];
		} else {
			pId = parentId;
		}
		Productrelationentry einfo = null;
		String sql ="";
		for (int i = 0; i < jsa.size(); i++) {
			String custid = ((JSONObject) jsa.get(i)).getString("custid");
			fcustproid += custid;
			if(i< jsa.size()-1){
				fcustproid += ",";
			}

			
			int count = ((JSONObject) jsa.get(i)).getInt("mcount");
			if (parentId.indexOf(",") == -1) {
				sqlstr = "select fid from t_pdt_productrelationentry where fparentid='"
						+ pId + "' and fcustproductid='" + custid + "'";
				List resultset = productRelationEntryDao.QueryBySql(sqlstr);
				if (resultset.size() > 0) {
					continue;
				}
			}

			try {

				einfo = new Productrelationentry();
				einfo.setFid(productRelationEntryDao.CreateUUid());
				einfo.setFparentid(pId);
				einfo.setFamount(count);
				einfo.setFcustproductid(custid);
				productRelationEntryDao.ExecSave(einfo);
			} catch (Exception e) {

				succes = false;
				result = JsonUtil.result(false, e.toString(), "", "");

			}
		}
		sqlstr = "select fid from t_pdt_productrelationentry where fparentid='"
				+ pId + "'";
		if(productRelationEntryDao.QueryExistsBySql(sqlstr)){//每次关联一个时
			sql =  "update t_bd_custproduct set fproductid='' where fid in ('"+fcustproid+"')";
			productRelationEntryDao.ExecBySql(sql);//关联多个客户产品时，去掉客户产品表产品ID
		}
		if(!StringUtils.isEmpty(fproductid)){
			if (fcustproid.indexOf(",") == -1) {
				sql =  "update t_bd_custproduct set fproductid='"+fproductid+"' where fid='"+fcustproid+"'";
				productRelationEntryDao.ExecBySql(sql);//关联一个客户产品时，为客户产品表赋上产品ID
			}else{
				fcustproid = fcustproid.replaceAll(",", "','");
				
				sql =  "update t_bd_custproduct set fproductid='' where fid in ('"+fcustproid+"')";
				productRelationEntryDao.ExecBySql(sql);//关联多个客户产品时，去掉客户产品表产品ID
				
			}
		}
		if (succes) {
			result = JsonUtil.result(true, pId, "", "");
		}

		reponse.getWriter().write(result);

		return null;
	}

	@RequestMapping(value = "/updateProductRelationTree")
	public String updateProductRelationTree(HttpServletRequest request,
			HttpServletResponse reponse) throws Exception {
		String result = "";

		boolean succes = true;

		String reqS = request.getParameter("data");

		JSONArray jsa = DataUtil.getJsonArrayByS(reqS);
		try {
			for (int i = 0; i < jsa.size(); i++) {

				String fid = ((JSONObject) jsa.get(i)).getString("id");
				int count = ((JSONObject) jsa.get(i)).getInt("mcount");
				Productrelationentry einfo = productRelationEntryDao.Query(fid);
				if (einfo == null) {
					throw new DJException("没有该条对应的产品关联信息");
				}

				// einfo.setFparentid(pId);
				einfo.setFamount(count);
				productRelationEntryDao.ExecSave(einfo);
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

	@RequestMapping(value = "/GetProductRelationTree")
	public String GetProductRelationTree(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		String result = "{children:[";
		String sql = " select e.fid id,e.famount mcount,c.fname text, 1 isleaf , e.fid dbId "
				+ " from t_pdt_productrelationentry e left join t_pdt_productrelation n on n.fid=e.fparentid "
				+ "left join t_bd_custproduct c on c.fid=e.fcustproductid";
		String fparentid = request.getParameter("id");
		if (fparentid != null) {
			sql += " where e.fparentid='" + fparentid + "'";
		}
		List<HashMap<String, Object>> sList = productdefDao.QueryBySql(sql);
		result += JsonUtil.List2Json(sList) + "]}";
		reponse.setCharacterEncoding("utf-8");
		reponse.getWriter().write(result);
		return null;
	}

	@RequestMapping(value = "/GetProductRelationInfo")
	public String GetProductRelationInfo(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		reponse.setCharacterEncoding("utf-8");
		try {
			String fproductid = request.getParameter("fid");
			String sql = "select f.fcustomerid, r.fid rid ,c.fname FROM t_pdt_productdef f "
					+ " left join t_bd_customer c on c.fid=f.fcustomerid left join  t_pdt_productrelation r  on f.fid=r.fproductid  and f.fcustomerid=r.fcustomerid "
					+ " where f.fid='" + fproductid + "'";
			ListResult result;

			result = productdefDao.QueryFilterList(sql, request);
			reponse.getWriter().write(JsonUtil.result(true, "", result));
		} catch (DJException e) {
			reponse.getWriter().write(
					JsonUtil.result(false, e.getMessage(), "", ""));
		}

		return null;

	}

	@RequestMapping("/deleteProductRelationentryNode")
	public String deleteProductRelationentryNode(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		reponse.setCharacterEncoding("utf-8");

		String result = "";

		boolean succes = true;

		String reqS = request.getParameter("data");

		JSONArray jsa = DataUtil.getJsonArrayByS(reqS);

		String parentId = ((JSONObject) jsa.get(0)).getString("parentId")
				.toString();
		parentId = "( select fproductid from t_pdt_productrelation where fid='"
				+ parentId + "')";

		String isHistory = productdefDao.QueryIsHistory(parentId);
		if ("false".equals(isHistory)) {

			try {
				for (int i = 0; i < jsa.size(); i++) {

					String id = ((JSONObject) jsa.get(i)).getString("id");
					String pId = ((JSONObject) jsa.get(i))
							.getString("parentId");// .split(",")[0]

					String entrysql = "delete from t_pdt_productrelationentry where FID ='"
							+ id + "'";
					String sql = "delete FROM t_pdt_productrelation where not  EXISTS  (select fid from t_pdt_productrelationentry where t_pdt_productrelation.fid=t_pdt_productrelationentry.fparentid and t_pdt_productrelationentry.fid<>'"
							+ id
							+ "') and t_pdt_productrelation.fid='"
							+ pId
							+ "'";
					productRelationEntryDao.ExecdeteleString(entrysql, sql);
				}
			} catch (Exception e) {

				succes = false;
				result = JsonUtil.result(false, e.toString(), "", "");

			}

			if (succes) {
				result = JsonUtil.result(true, "", "", "");
			}
		} else {
			result = JsonUtil.result(false, isHistory, "", "");
		}

		reponse.getWriter().write(result);

		return null;
	}

	/**
	 * 获取产品附件
	 *
	 * @param request
	 * @param reponse
	 * @return
	 * @throws IOException
	 *
	 * @date 2014-11-7 下午4:56:40  (ZJZ)
	 */
	@RequestMapping(value = "/gainProductAccessory")
	public String gainProductAccessory(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {

		setRequestAndReponseToUTF8(request, reponse);
		
		try {
			String sql = " SELECT fid, fname, fpath, fparentid FROM t_ord_productdemandfile ";
			
			if (request.getParameterValues("fid") != null) {
				
				sql += String.format("where fparentid = '%s'", request.getParameterValues("fid"));  
				
			}
		

			ListResult result = productdefDao.QueryFilterList(sql, request);
			
			for (HashMap<String, Object> record : result.getData()) {
				
				String pathT =  (String) record.get("fpath");
				
//				if (pathT.lastIndexOf("/") != -1) {
					
					pathT = FILE_RELATIVE_PATH + pathT.substring(pathT.lastIndexOf("/") + 1);
					
//				} else {
//					
//					pathT = FILE_RELATIVE_PATH + pathT;
//					
//				}

				record.put("fpath", pathT);
			}
			
			reponse.getWriter().write(JsonUtil.result(true, "", result));
		} catch (Exception e) {
			reponse.getWriter().write(
					JsonUtil.result(false, e.toString(), "", ""));
		}
		return null;

	}

	/**
	 * 上传产品附件
	 *
	 * @param request
	 * @param reponse
	 * @return
	 * @throws IOException
	 *
	 * @date 2014-11-7 下午4:56:40  (ZJZ)
	 */
	@RequestMapping(value = "/uploadProductAccessory")
	public String uploadProductAccessory(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {

		setRequestAndReponseToUTF8(request, reponse);
		
		try {

			
			
			productdefDao.ExecDealWithUploadFile(request);
			
					
			reponse.getWriter().write(JsonUtil.result(true, "成功!", "", ""));
		} catch (Exception e) {
			reponse.getWriter().write(
					JsonUtil.result(false, e.toString(), "", ""));
		}
		return null;

	}
	
	/**
	 * 获取产品附件
	 *
	 * @param request
	 * @param reponse
	 * @return
	 * @throws IOException
	 *
	 * @date 2014-11-7 下午4:56:40  (ZJZ)
	 */
	@RequestMapping(value = "/deleteProductAccessorys")
	public String deleteProductAccessorys(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {

		setRequestAndReponseToUTF8(request, reponse);
		
		String fids = request.getParameter("fids");
		
		String fidsS = "('" + fids.replace(",", "','") + "')";

		try {
			
			String sql = " DELETE FROM t_ord_productdemandfile WHERE fid in %s ";
			
			sql = String.format(sql, fidsS);
			
			productdefDao.ExecBySql(sql);
			
			
			
			reponse.getWriter().write(JsonUtil.result(true, "成功!", "", ""));
		} catch (Exception e) {
			reponse.getWriter().write(
					JsonUtil.result(false, e.toString(), "", ""));
		}
		return null;

	}
	
	private void setRequestAndReponseToUTF8(HttpServletRequest request,
			HttpServletResponse reponse) throws UnsupportedEncodingException {
		reponse.setCharacterEncoding("utf-8");
		request.setCharacterEncoding("utf-8");
	}
	@RequestMapping(value = "/getCardboardList")
	public void getCardboardList(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException{
		try {
			String sql = "select fid,fname,fnumber,flayer,ftilemodelid,fnewtype,feffect,fcreatetime,flastupdatetime from t_pdt_productdef where fboxtype = 1"+productdefDao.QueryFilterByUser(request, null, "fsupplierid");
			ListResult result = productdefDao.QueryFilterList(sql, request);
			reponse.getWriter().write(JsonUtil.result(true, "", result));
		} catch (DJException e) {
			// TODO: handle exception
			reponse.getWriter().write(
					JsonUtil.result(false, e.toString(), "", ""));
		}
	}
	@RequestMapping(value = "/getSupCustCardboard")
	public void getSupCustCardboard(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException{
		try {
		      String userid = ((Useronline) request.getSession().getAttribute( "Useronline")).getFuserid();
		      String sql="select fcustomerid from ( select fcustomerid  from t_bd_usercustomer where fuserid='"+userid+"'"+
		              " union select c.fcustomerid from  t_sys_userrole r "+
		              " left join t_bd_rolecustomer c on r.froleid=c.froleid where r.fuserid='"+userid+"' )  s where fcustomerid is not null " ;
		          List<HashMap<String, Object>> custList=productdefDao.QueryBySql(sql);
		          String customerid = null;
		          if(custList.size()==1)
		          {
		        	  HashMap<String,Object> map=custList.get(0);
		        	  customerid =map.get("fcustomerid").toString();
		          }else{
		        	  throw new DJException("当前帐号存在多个客户,不能执行操作！");
		          }
		          sql = "select e.fid,e.fname,e.fnumber,e.flayer,e.ftilemodelid,e.fsupplierid,concat(e.fname,'/',e.flayer,e.ftilemodelid) fqueryname from t_pdt_productdef e" +
		          " inner join t_sys_commonmaterial e1 on e.fid=e1.fmaterialid where e1.fcustomerid='"+customerid+"'";
		          ListResult result =  productdefDao.QueryFilterList(sql, request);
		          if("0".equals(result.getTotal())){
		        	  sql = "select 1 from t_sys_commonmaterial where fcustomerid ='"+customerid+"' limit 0,1";
		        	  if(!productdefDao.QueryExistsBySql(sql)){    // 若客户没有设置常用材料，查全部
		        		  sql = "select fid,fname,fnumber,flayer,ftilemodelid,feffect,fcreatetime,flastupdatetime,concat(fname,'/',flayer,ftilemodelid) fqueryname from t_pdt_productdef e1 where e1.fboxtype = 1 and e1.feffect=1 ";
		        		  result = productdefDao.QueryFilterList(sql, request);
		                }else{
		                	reponse.getWriter().write(JsonUtil.result(true, "", "",""));
		                	return;
		                }
		              }
		          reponse.getWriter().write(JsonUtil.result(true, "", result));
		} catch (DJException e) {
			reponse.getWriter().write(
					JsonUtil.result(false, e.toString(), "", ""));
		}
	}
	@RequestMapping(value = "/getSupplierTree")
	public void getSupplierTree(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException{
		try {
			String result="{children:[";
			String sql = "select fid id,fname text,1 isleaf from t_sys_supplier where 1=1 "+productdefDao.QueryFilterByUser(request, null, "fid");;
			List<HashMap<String, Object>>  sList = productdefDao.QueryBySql(sql);
			result+=JsonUtil.List2Json(sList)+"]}";
			reponse.getWriter().write(result);
			
		} catch (DJException e) {
			// TODO: handle exception
			reponse.getWriter().write(
					JsonUtil.result(false, e.getMessage(), "", ""));
		}
	}
	@RequestMapping("saveOrUpdateCardboard")
	public void saveOrUpdateCardboard(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException{
		try {
			String userid  = ((Useronline)request.getSession().getAttribute("Useronline")).getFuserid();
			Productdef p = (Productdef)request.getAttribute("Productdef");
			if(StringUtils.isEmpty(p.getFid())){
				p.setFid(productdefDao.CreateUUid());
				p.setFcreatetime(new Date());
				p.setFcreatorid(userid);
			}else{
				p.setFlastupdateuserid(userid);
				p.setFlastupdatetime(new Date());
			}
			p.setFboxtype(1);
			String name = StringUtils.isEmpty(p.getFname())?"":p.getFname()+" ";
			p.setFqueryname(name+p.getFnumber()+"/"+p.getFlayer()+p.getFtilemodelid());
			productdefDao.saveOrUpdate(p);
			reponse.getWriter().write(JsonUtil.result(true, "保存成功", "",""));
		} catch (DJException e) {
			reponse.getWriter().write(
					JsonUtil.result(false, e.getMessage(), "", ""));
		}
	}
	@RequestMapping("getCardboardInfo")
	public void getCardboardInfo(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException{
		try {
			String fid  = request.getParameter("fid");
//			isGenerateDeliverapply(fid);
			String sql = "select feffected, fsupplierid,fid,fname,fnumber,flayer,ftilemodelid,fnewtype,feffect,fcreatetime,flastupdatetime from t_pdt_productdef where fboxtype = 1 and fid='"+fid+"'";
			List list = productdefDao.QueryBySql(sql);
			reponse.getWriter().write(JsonUtil.result(true, "保存成功", "",list));
		} catch (DJException e) {
			// TODO: handle exception
			reponse.getWriter().write(
					JsonUtil.result(false, e.getMessage(), "", ""));
		}
	}
	@RequestMapping("isGenerateDeliverapply")
	public void isGenerateDeliverapply(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException{
		String fid = request.getParameter("fid");
		try {
			fid = fid.replaceAll(",", "','");
			String sql = "SELECT 1 FROM t_ord_deliverapply  WHERE fmaterialfid IN('"+fid+"') AND fboxtype=1";
			if(productdefDao.QueryExistsBySql(sql)){
				throw new DJException("已生成订单不允许删除、修改！");
			}
			reponse.getWriter().write(JsonUtil.result(true, "", "",""));
		} catch (DJException e) {
			// TODO: handle exception
			reponse.getWriter().write(
					JsonUtil.result(false, e.getMessage(), "", ""));
		}
			
	}
	@RequestMapping("delCardboard")
	public void delCardboard(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException{
		try {
			String fid  = request.getParameter("fidcls");
//			String sql = "SELECT 1 FROM t_ord_deliverapply d LEFT JOIN `t_pdt_productdef` p ON d.fmaterialfid=p.`FID` WHERE d.fmaterialfid IN('"+fid+"') AND d.fboxtype=1";
//			if(productdefDao.QueryExistsBySql(sql)){
//				throw new DJException("已生成订单不允许删除、修改！");
//			}
//			isGenerateDeliverapply(fid);
			fid = fid.replaceAll(",", "','");
			String sql = "delete from t_pdt_productdef where fid in('"+fid+"')";
		
			productdefDao.ExecBySql(sql);
			reponse.getWriter().write(JsonUtil.result(true, "删除成功", "",""));
		} catch (DJException e) {
			// TODO: handle exception
			reponse.getWriter().write(
					JsonUtil.result(false,e.getMessage(), "", ""));
		}
	}
	@RequestMapping("exportCardboardList")
	public void exportCardboardList(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException{
		try {
			String sql = "select fname 材料,fnumber 代码,flayer 层数,ftilemodelid 楞型,feffect 是否启用,fcreatetime 创建时间,flastupdatetime 最后修改时间  from t_pdt_productdef where fboxtype = 1";
			request.setAttribute("nolimit", 0);
			ListResult result = productdefDao.QueryFilterList(sql, request);
			ExcelUtil.toexcel(reponse, result);
		} catch (DJException e) {
			// TODO: handle exception
			reponse.getWriter().write(
					JsonUtil.result(false, e.toString(), "", ""));
		}
		
	}
	@RequestMapping("downloadCardboard")
	public void downloadCardboard(HttpServletResponse response,HttpServletRequest request) throws DJException, IOException {
		InputStream in = null;
		try {
			File file = new File(request.getServletContext().getRealPath("/"));
			file = file.getAbsoluteFile().getParentFile();
			in = new FileInputStream(file+"/vmifile/纸板材料excel文件上传模版.xls");
		} catch (FileNotFoundException e) {
			throw new DJException("此附件文件不存在，无法下载！");
		}
		response.setContentType("application/x-msdownload");
		response.addHeader("Content-Disposition", "attachment; filename=\"" + new String("纸板材料excel文件上传模版.xls".getBytes("UTF-8"),"iso-8859-1") + "\"");
		OutputStream out = response.getOutputStream();
		byte[] bytes = new byte[1024];
		int len = 0;
		while((len = in.read(bytes,0,1024))!=-1){
			out.write(bytes, 0, len);
		}
		out.flush();
		in.close();
	}
	@RequestMapping("saveCustExcel")
	public void saveCustExcel(HttpServletRequest request,HttpServletResponse response) throws IOException {
		
		try {
			int length = productdefDao.saveExcel(request);
			response.getWriter().write(JsonUtil.result(true, "操作成功，共导入"+length+"条数据！", "",""));
		} catch (DJException e) {
			// TODO: handle exception
			response.getWriter().write(
					JsonUtil.result(false, e.getMessage(), "", ""));
		}
		
	}
	
	@RequestMapping(value = "/getCardboardListByUser")
	public void getCardboardListByUser(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException{
		try {
			String sql = "select f.fid,f.fname,f.fnumber,f.flayer,f.ftilemodelid,f.feffect,f.fcreatetime,f.flastupdatetime from t_pdt_productdef  f left join t_pdt_productreqallocationrules r on r.fsupplierid=f.fsupplierid where f.fboxtype = 1 and f.feffect=1 " +productdefDao.QueryFilterByUser(request,"r.fcustomerid",null);
			ListResult result = productdefDao.QueryFilterList(sql, request);
			reponse.getWriter().write(JsonUtil.result(true, "", result));
		} catch (DJException e) {
			reponse.getWriter().write(
					JsonUtil.result(false, e.toString(), "", ""));
		}
	}
	/**
	 * 根据产品需求分配规则对应的制造商过滤查纸板材料
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/getCardboardListByAllocationrules")
	public String getCardboardListByAllocationrules(HttpServletRequest request,
			HttpServletResponse response) throws IOException{
		try {
			String sql = "select distinct quote(e1.fid) fid from t_pdt_productreqallocationrules e"
					+" left join t_sys_supplier e1 on e.fsupplierid=e1.fid where 1=1 "+productdefDao.QueryFilterByUser(request, "e.fcustomerid", null);
			List<HashMap<String, Object>> list = productdefDao.QueryBySql(sql);
			StringBuilder con = new StringBuilder("");
			if(list.size()>0){
				con.append("and fsupplierid in (");
				for(HashMap<String, Object> map: list){
					con.append(map.get("fid"));
					con.append(",");
				}
				con.setCharAt(con.length()-1, ')');
			}
			sql = "select fid,fname,fnumber,flayer,ftilemodelid,feffect,fcreatetime,flastupdatetime,fsupplierid from t_pdt_productdef where fboxtype = 1 and feffect=1 " +con;
			ListResult result = productdefDao.QueryFilterList(sql, request);
			response.getWriter().write(JsonUtil.result(true, "", result));
		} catch (DJException e) {
			response.getWriter().write(
					JsonUtil.result(false, e.toString(), "", ""));
		}
		return null;
	}
	
	@RequestMapping(value = "/getCardboardListBySupplier")
	public String getCardboardListBySupplier(HttpServletRequest request,
			HttpServletResponse response) throws IOException{
		try {
			String userid = ((Useronline) request.getSession().getAttribute(
					"Useronline")).getFuserid();
			String sql="select fcustomerid from ( select fcustomerid  from t_bd_usercustomer where fuserid='"+userid+"'"+
					" union select c.fcustomerid from  t_sys_userrole r "+
					" left join t_bd_rolecustomer c on r.froleid=c.froleid where r.fuserid='"+userid+"' )  s where fcustomerid is not null " ;
			List<HashMap<String, Object>> custList=productdefDao.QueryBySql(sql);
			String customerid = null;
			if(custList.size()==1)
			{
				HashMap<String,Object> map=custList.get(0);
				customerid =map.get("fcustomerid").toString();
			}else{
				throw new DJException("当前帐号存在多个客户,不能执行操作！");
			}
			sql = "select e.fid,e.fname,e.fnumber,e.flayer,e.ftilemodelid,e.fsupplierid from t_pdt_productdef e" +
					" inner join t_sys_commonmaterial e1 on e.fid=e1.fmaterialid where e1.fcustomerid='"+customerid+"'";
			ListResult result =  productdefDao.QueryFilterList(sql, request);
			if("0".equals(result.getTotal())){
				String fsupplierid = baseSysDao.getValueFromDefaultfilter(request, "e1.fsupplierid");
				String subSql = "";
				if(fsupplierid !=null){
					subSql =  " and fsupplierid='"+fsupplierid+"'";
				}
				sql = "select 1 from t_sys_commonmaterial where fcustomerid ='"+customerid+"'"+subSql+" limit 0,1";
				if(!productdefDao.QueryExistsBySql(sql)){		// 若客户没有设置常用材料，查全部
					sql = "select fid,fname,fnumber,flayer,ftilemodelid,fsupplierid from t_pdt_productdef e1 where e1.fboxtype = 1 and e1.feffect=1 ";
					result = productdefDao.QueryFilterList(sql, request);
				}else{
					response.getWriter().write(JsonUtil.result(true, "", "",""));
					return null;
				}
			}
			response.getWriter().write(JsonUtil.result(true, "", result));
		} catch (DJException e) {
			response.getWriter().write(
					JsonUtil.result(false, e.toString(), "", ""));
		}
		return null;
	}
	
	/**
	 * 根据制造商查询纸板材料
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = "/getSupplierCardboardList")
	public void getSupplierCardboardList(HttpServletRequest request,
			HttpServletResponse response) throws IOException{
		try {
			String userid = ((Useronline) request.getSession().getAttribute(
					"Useronline")).getFuserid();
			String sql="select fcustomerid from ( select fcustomerid  from t_bd_usercustomer where fuserid='"+userid+"'"+
					" union select c.fcustomerid from  t_sys_userrole r "+
					" left join t_bd_rolecustomer c on r.froleid=c.froleid where r.fuserid='"+userid+"' )  s where fcustomerid is not null " ;
			List<HashMap<String, Object>> custList=productdefDao.QueryBySql(sql);
			String customerid = null;
			if(custList.size()==1)
			{
				HashMap<String,Object> map=custList.get(0);
				customerid =map.get("fcustomerid").toString();
			}else{
				throw new DJException("当前帐号存在多个客户,不能执行操作！");
			}
			String supplierid = request.getParameter("fsupplierid");
			String query = URLDecoder.decode(request.getParameter("query").replace("_", "%"), "utf-8");
			if(StringUtils.isEmpty(supplierid)){
				throw new DJException("传值有误，制造商id为空！");
			}
			sql = "select e.fid,e.fname,e.fnumber,e.flayer,e.ftilemodelid from t_pdt_productdef e " +
					"left join (select fid,fmaterialid from t_sys_commonmaterial where fcustomerid='"+customerid+"') e1 on e.fid=e1.fmaterialid where e.fboxtype = 1 and e.feffect=1" +
					" and e.fsupplierid='"+supplierid+"' and e1.fid is null";
			if(!StringUtils.isEmpty(query)){
				sql += " and (e.fname like '%"+query+"%' or e.flayer like '%"+query+"%' or e.ftilemodelid like '%"+query+"%')";
			}
			ListResult result = productdefDao.QueryFilterList(sql, request);
			response.getWriter().write(JsonUtil.result(true, "", result));
		} catch (DJException e) {
			response.getWriter().write(
					JsonUtil.result(false, e.getMessage(), "", ""));
		}
	}
	
	@RequestMapping(value = "/getCommonMaterialList")
	public void getCommonMaterialList(HttpServletRequest request,
			HttpServletResponse response) throws IOException{
		String supplierid = request.getParameter("fsupplierid");
		String query = URLDecoder.decode(request.getParameter("query").replace("_", "%"), "utf-8");
		try {
			String userid = ((Useronline) request.getSession().getAttribute(
					"Useronline")).getFuserid();
			String sql="select fcustomerid from ( select fcustomerid  from t_bd_usercustomer where fuserid='"+userid+"'"+
					" union select c.fcustomerid from  t_sys_userrole r "+
					" left join t_bd_rolecustomer c on r.froleid=c.froleid where r.fuserid='"+userid+"' )  s where fcustomerid is not null " ;
			List<HashMap<String, Object>> custList=productdefDao.QueryBySql(sql);
			String customerid = null;
			if(custList.size()==1)
			{
				HashMap<String,Object> map=custList.get(0);
				customerid =map.get("fcustomerid").toString();
			}else{
				throw new DJException("当前帐号存在多个客户,不能执行操作！");
			}
			sql = "select e1.fid,e.fname,e.fnumber,e.flayer,e.ftilemodelid from t_pdt_productdef e" +
					" inner join t_sys_commonmaterial e1 on e.fid=e1.fmaterialid where e1.fcustomerid='"+customerid+"' and e1.fsupplierid='"+supplierid+"'";
			if(!StringUtils.isEmpty(query)){
				sql += "and (e.fname like '%"+query+"%' or e.flayer like '%"+query+"%' or e.ftilemodelid like '%"+query+"%')";
			}
			ListResult result = productdefDao.QueryFilterList(sql, request);
			response.getWriter().write(JsonUtil.result(true, "", result));
		} catch (DJException e) {
			response.getWriter().write(
					JsonUtil.result(false, e.getMessage(), "", ""));
		}
	}
	/**
	 * 添加常用材料
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = "/addCommonMaterial")
	public void addCommonMaterial(HttpServletRequest request,
			HttpServletResponse response) throws IOException{
		String fids = request.getParameter("fids");
		String fsupplierid = request.getParameter("fsupplierid");
		String userid = ((Useronline) request.getSession().getAttribute(
				"Useronline")).getFuserid();
		try {
			if(StringUtils.isEmpty(fids)){
				throw new DJException("添加失败，材料Id为空");
			}
			if(StringUtils.isEmpty(fsupplierid)){
				throw new DJException("添加失败，制造商ID为空");
			}
			String sql="select fcustomerid from ( select fcustomerid  from t_bd_usercustomer where fuserid='"+userid+"'"+
					" union select c.fcustomerid from  t_sys_userrole r "+
					" left join t_bd_rolecustomer c on r.froleid=c.froleid where r.fuserid='"+userid+"' )  s where fcustomerid is not null " ;
			List<HashMap<String, Object>> custList=productdefDao.QueryBySql(sql);
			String fcustomerid = null;
			if(custList.size()==1)
			{
				HashMap<String,Object> map=custList.get(0);
				fcustomerid =map.get("fcustomerid").toString();
			}else{
				throw new DJException("当前帐号存在多个客户,不能执行操作！");
			}
			productdefDao.ExecAddCommonMaterial(fids,fsupplierid,fcustomerid);
			response.getWriter().write(JsonUtil.result(true, "添加成功！", "",""));
		} catch (DJException e) {
			response.getWriter().write(
					JsonUtil.result(false, e.getMessage(), "", ""));
		}
	}
	
	@RequestMapping(value = "/removeCommonMaterial")
	public void removeCommonMaterial(HttpServletRequest request,
			HttpServletResponse response) throws IOException{
		String fids = request.getParameter("fids");
		try {
			if(StringUtils.isEmpty(fids)){
				throw new DJException("添加失败，材料Id为空");
			}
			String sql = "delete from t_sys_commonmaterial where fid "+baseSysDao.getCondition(fids);
			productdefDao.ExecBySql(sql);
			response.getWriter().write(JsonUtil.result(true, "取消成功！", "",""));
		} catch (DJException e) {
			response.getWriter().write(
					JsonUtil.result(false, e.getMessage(), "", ""));
		}
	}
	
	//批量处理客户生成的产品规格落料规格为空的数据;
	@RequestMapping(value = "/BatchProcessingProductSpec")
	public void BatchProcessingProductSpec(HttpServletRequest request,
			HttpServletResponse response) throws IOException{
		try {
			List<HashMap<String, Object>> custList=productdefDao.QueryBySql("select replace(replace(replace(cp.fspec,'*','x'),'X','x'),'×','x') fspec,p.fid from t_bd_custproduct cp,t_pdt_productdef p where p.fid=cp.fproductid and ifnull(p.fboxlength,'')='' and ifnull(cp.fspec,'' )<>'' ");
			String fspec = "";
			String fid = "";
			String flength="";
			String fwidth="";
			String fheight="";
			String[] a = null;
			java.util.regex.Pattern pattern=java.util.regex.Pattern.compile("[0-9]{1,14}\\.?[0-9]{0,2}");
	        java.util.regex.Matcher match=pattern.matcher(fheight);
			for(int i =0;i<custList.size();i++){
				fspec = custList.get(i).get("fspec").toString();
				fid = custList.get(i).get("fid").toString();
				a = fspec.split("x");
				if(a.length>=1){
					flength=a[0].trim();
					match = pattern.matcher(flength);
					if(match.matches()==false){
						continue;
					}
					if(a.length>1){
						fwidth=a[1].trim();
						match = pattern.matcher(fwidth);
						if(match.matches()==false){
							continue;
						}
					}
					if(a.length>2){
						fheight = a[2].trim();
						if(fheight.contains(".") && fheight.indexOf(".")<4 && fheight.indexOf(".")>0){
							int j = fheight.indexOf(".");
							fheight=fheight.substring(0,j+2).trim();
							match = pattern.matcher(fheight);
							if(match.matches()==false){
								fheight=fheight.substring(0,j).trim();
								match = pattern.matcher(fheight);
								if(j-1>0){
									if(match.matches()==false){
										fheight=fheight.substring(0,j-1).trim();
										match = pattern.matcher(fheight);
										if(j-2>0){
											if(match.matches()==false){
												fheight=fheight.substring(0,j-2).trim();
											}
										}
									}
								}
							}
						}else{
							int j = fheight.length();
							if(j>3){
								fheight=fheight.substring(0,3).trim();
								match = pattern.matcher(fheight);
								if(match.matches()==false){
									fheight=fheight.substring(0,2).trim();
									match = pattern.matcher(fheight);
									if(match.matches()==false){
										fheight=fheight.substring(0,1).trim();
									}
								}
							}else{
								fheight=fheight.substring(0,j).trim();
								match = pattern.matcher(fheight);
								if(j-1>0){
									if(match.matches()==false){
										fheight=fheight.substring(0,j-1).trim();
										match = pattern.matcher(fheight);
										if(j-2>0){
											if(match.matches()==false){
												fheight=fheight.substring(0,j-2).trim();
											}
										}
									}
								}
							}
						}
					}
				}
				productdefDao.ExecBySql("update t_pdt_productdef set fboxlength='"+flength+"',fboxwidth='"+fwidth+"',fboxheight='"+fheight+"' where fid = '"+fid+"'");
			}
			response.getWriter().write(JsonUtil.result(true, "批量处理成功！", "",""));
		} catch (DJException e) {
			response.getWriter().write(
					JsonUtil.result(false, e.getMessage(), "", ""));
		}
	}
}
