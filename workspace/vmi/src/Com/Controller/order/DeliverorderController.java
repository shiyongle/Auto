package Com.Controller.order;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.CellView;
import jxl.Workbook;
import jxl.format.Alignment;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

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
import Com.Base.Util.ServerContext;
import Com.Base.Util.SpringContextUtils;
import Com.Base.Util.mySimpleUtil.MySimpleToolsZ;
import Com.Base.data.LogAction;
import Com.Controller.System.RoleController;
import Com.Dao.Inv.IOutWarehouseDao;
import Com.Dao.Inv.IStorebalanceDao;
import Com.Dao.System.IBaseSysDao;
import Com.Dao.System.IProductdefDao;
import Com.Dao.System.ISupplierDao;
import Com.Dao.System.ISyscfgDao;
import Com.Dao.System.IVmipdtParamDao;
import Com.Dao.order.IDeliverorderDao;
import Com.Dao.order.IProductPlanDao;
import Com.Dao.order.ISaleOrderDao;
import Com.Dao.traffic.ITruckassembleDao;
import Com.Entity.Inv.Storebalance;
import Com.Entity.System.Useronline;
import Com.Entity.order.Deliverorder;
import Com.Entity.order.ProductPlan;
import Com.Entity.order.Saleorder;

@Controller
public class DeliverorderController { 
	private static final String TIME_FIELD = "farrivetime"; 
	Logger log = LoggerFactory.getLogger(RoleController.class);
	@Resource
	private IDeliverorderDao DeliverorderDao;
	@Resource
	private ISaleOrderDao saleOrderDao;
	@Resource
	private IVmipdtParamDao VmipdtParamDao;
	@Resource
	private IStorebalanceDao storebalanceDao;
	@Resource
	private IProductPlanDao ProductPlanDao;
	@Resource
	private ISupplierDao SupplierDao;
	@Resource
	private ITruckassembleDao TruckassembleDao;
	@Resource(name = "outWarehouseDao")
	private IOutWarehouseDao outWarehouseDao;
	@Resource
	private IProductdefDao productdefDao;
	@Resource
	private ISyscfgDao syscfgDao;
	@Resource
	private IBaseSysDao baseSysDao;
	private boolean iscreatTruckassemble=false;

	@RequestMapping("/GetDeliverorderList")
	public String GetDeliverorderList(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
//		String sql = "select d.fordermanid,u3.fname forderman,d.fordertime,d.fordered,u1.fname as fcreator,u2.fname as flastupdater,c.fname as fcustname,cpdt.fname cutpdtname,d.fid,d.fcreatorid,d.fcreatetime,d.fupdateuserid,d.fupdatetime,d.fnumber,d.fcustomerid,d.fcusproductid,date_format(d.farrivetime,'%Y-%m-%d %T') farrivetime,d.flinkman,d.flinkphone,d.famount,d.faddress,d.fdescription,fsaleorderid,ifnull(fordernumber,'') fordernumber,forderentryid,d.fimportEAS,d.fimportEASuserid,d.fimportEAStime,d.fouted,d.foutorid,d.fouttime,d.faudited,ifnull(d.faudittime,'') faudittime,ifnull(ad.fname,'') fauditor ,fmatched,ifnull(s.fname,'') suppliername  from t_ord_deliverorder d left join t_sys_user u1 on u1.fid=d.fcreatorid left join t_sys_user u2 on u2.fid=d.fupdateuserid left join t_sys_user u3 on u3.fid=d.fordermanid left join t_bd_customer c on c.fid=d.fcustomerid left join t_bd_custproduct cpdt on cpdt.fid=d.fcusproductid left join t_sys_user ad on ad.fid=d.fauditorid  left join t_ord_productplan p on p.fid=d.forderentryid  left join t_sys_supplier s on s.fid=p.fsupplierid  where 1=1 ";
		String sql = "select d.fpcmordernumber,d.fordermanid,u3.fname forderman,d.fordertime,d.fordered,u1.fname as fcreator,u2.fname as flastupdater,c.fname as fcustname,IF(d.fboxtype=0,cpdt.fname,CONCAT(e.fname,' ',e.fnumber,' ','/',e.flayer,e.ftilemodelid)) cutpdtname,d.fid,d.fcreatorid,d.fcreatetime,d.fupdateuserid,d.fupdatetime,d.fnumber,d.fcustomerid,d.fcusproductid,date_format(d.farrivetime,'%Y-%m-%d %T') farrivetime,d.flinkman,d.flinkphone,d.famount,ifnull(d.fassembleQty,0) fassembleQty,ifnull(d.foutQty,0) foutQty,d.faddress,d.fdescription,fsaleorderid,ifnull(fordernumber,'') fordernumber,forderentryid,d.fimportEAS,d.fimportEASuserid,d.fimportEAStime,d.fouted,d.foutorid,d.fouttime,d.faudited,ifnull(d.faudittime,'') faudittime,ifnull(ad.fname,'') fauditor ,fmatched,ifnull(s.fname,'') suppliername,d.ftype ftype,ifnull(pd.fcharacter,'') fcharacter,d.fclosed  from t_ord_deliverorder d left join t_sys_user u1 on u1.fid=d.fcreatorid left join t_sys_user u2 on u2.fid=d.fupdateuserid left join t_sys_user u3 on u3.fid=d.fordermanid left join t_bd_customer c on c.fid=d.fcustomerid left join t_bd_custproduct cpdt on cpdt.fid=d.fcusproductid left join t_sys_user ad on ad.fid=d.fauditorid  left join t_sys_supplier s on s.fid=d.fsupplierid left join t_ord_schemedesignentry pd on pd.fid=d.ftraitid LEFT JOIN t_pdt_productdef e ON d.fproductid = e.fid  where 1=1 and d.fboxtype=0";
		sql=sql +saleOrderDao.QueryFilterByUser(request, "d.fcustomerid", "d.fsupplierid");
		ListResult result;
		reponse.setCharacterEncoding("utf-8");
		try {
			result = DeliverorderDao.QueryFilterList(sql, request);
			reponse.getWriter().write(JsonUtil.result(true, "", result));
		} catch (DJException e) {
			reponse.getWriter().write(
					JsonUtil.result(false, e.getMessage(), "", ""));
		}

		return null;
	}
	
	@RequestMapping("/GetDeliverorderListMV")
	public String GetDeliverorderListMV(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String fcustomerid = request.getParameter("fcustomerid");
		String sql = "select fpcmordernumber,_creator fcreator,_lastupdater flastupdater,_custname fcustname,_custpdtname cutpdtname,faudited,fid,fcreatetime,fupdatetime,fnumber,fcustomerid,date_format(farrivetime,'%Y-%m-%d %T') farrivetime,flinkman,flinkphone,famount,ifnull(fassembleQty,0) fassembleQty,ifnull(foutQty,0) foutQty,faddress,fdescription,fsaleorderid,fordernumber,forderentryid,fimportEAS,fimportEAStime,fouted,faudittime,_auditor fauditor,fmatched,_suppliername suppliername,ftype,_character fcharacter,fclosed  from t_ord_deliverorder_mv where fboxtype=0";
		if(StringUtils.isEmpty(fcustomerid)){
			sql=sql +saleOrderDao.QueryFilterByUser(request, "fcustomerid", "fsupplierid");
		}else{
			sql = sql + " and fcustomerid='"+fcustomerid+"' " + saleOrderDao.QueryFilterByUser(request, null, "fsupplierid");
		}
		ListResult result;
		try {
			result = DeliverorderDao.QueryFilterList(sql, request);
			response.getWriter().write(JsonUtil.result(true, "", result));
		} catch (DJException e) {
			response.getWriter().write(
					JsonUtil.result(false, e.getMessage(), "", ""));
		}
		return null;
	}
	

	@RequestMapping("/GetSDeliverorderList")
	public String GetSDeliverorderList(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		String sql = "select d.fpcmordernumber,d.fordermanid,u3.fname forderman,d.fordertime,d.fordered,u1.fname as fcreator,u2.fname as flastupdater,c.fname as fcustname,cpdt.fname cutpdtname,d.fid,d.fcreatorid,d.fcreatetime,d.fupdateuserid,d.fupdatetime,d.fnumber,d.fcustomerid,d.fcusproductid,date_format(d.farrivetime,'%Y-%m-%d %T') farrivetime,d.flinkman,d.flinkphone,d.famount,d.faddress,d.fdescription,fsaleorderid,ifnull(fordernumber,'') fordernumber,forderentryid,d.fimportEAS,d.fimportEASuserid,d.fimportEAStime,d.fouted,d.foutorid,d.fouttime,d.faudited,ifnull(d.faudittime,'') faudittime,ifnull(ad.fname,'') fauditor ,fmatched,d.ftype,ifnull(pdef.fcharacter,'') fcharacter from t_ord_deliverorder d left join t_sys_user u1 on u1.fid=d.fcreatorid left join t_sys_user u2 on u2.fid=d.fupdateuserid left join t_sys_user u3 on u3.fid=d.fordermanid left join t_bd_customer c on c.fid=d.fcustomerid left join t_bd_custproduct cpdt on cpdt.fid=d.fcusproductid left join t_sys_user ad on ad.fid=d.fauditorid left join t_pdt_productdef pdef on d.fproductid = pdef.fid where ifnull(d.faudited,0)=1";
		sql=sql +saleOrderDao.QueryFilterByUser(request, "d.fcustomerid", "d.fsupplierid");
		ListResult result;
		reponse.setCharacterEncoding("utf-8");
		try {
			result = DeliverorderDao.QueryFilterList(sql, request);
			reponse.getWriter().write(JsonUtil.result(true, "", result));
		} catch (DJException e) {
			reponse.getWriter().write(
					JsonUtil.result(false, e.getMessage(), "", ""));
		}

		return null;
	}
	@RequestMapping("/selectDeliverorderInfo")
	public String selectDeliverorderInfo(HttpServletRequest request,HttpServletResponse response) throws IOException {
		String fid = request.getParameter("fid");
		String sql = "select * from t_ord_deliverorder where fid='"+fid+"'";
		List<HashMap<String, Object>> result;
		try{
			result = DeliverorderDao.QueryBySql(sql);
			response.getWriter().write(JsonUtil.result(true,"","",result));
		}catch(DJException e){
			response.getWriter().write(JsonUtil.result(false, e.getMessage(), "",""));
		}
		return null;
	}
	

	@RequestMapping("/GetDeliverorderSendList")
	public String GetDeliverorderSendList(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
//		String sql = "select d.fordermanid,u3.fname forderman,d.fordertime,d.fordered,u1.fname as fcreator,u2.fname as flastupdater,c.fname as fcustname,cpdt.fname cutpdtname,d.fid,d.fcreatorid,d.fcreatetime,d.fupdateuserid,d.fupdatetime,d.fnumber,d.fcustomerid,d.fcusproductid,date_format(d.farrivetime,'%Y-%m-%d %T') farrivetime,d.flinkman,d.flinkphone,d.famount,d.faddress,d.fdescription,fsaleorderid,ifnull(fordernumber,'') fordernumber,forderentryid,d.fimportEAS,d.fimportEASuserid,d.fimportEAStime,d.fouted,d.foutorid,d.fouttime from t_ord_deliverorder d left join t_sys_user u1 on u1.fid=d.fcreatorid left join t_sys_user u2 on u2.fid=d.fupdateuserid left join t_sys_user u3 on u3.fid=d.fordermanid left join t_bd_customer c on c.fid=d.fcustomerid left join t_bd_custproduct cpdt on cpdt.fid=d.fcusproductid where fouted=0 ";
		String sql = "select d.fpcmordernumber,pdef.fname productname,d.fordermanid,u3.fname forderman,d.fordertime,d.fordered,u1.fname as fcreator,u2.fname as flastupdater,c.fname as fcustname,cpdt.fname cutpdtname,d.fid,d.fcreatorid,d.fcreatetime,d.fupdateuserid,d.fupdatetime,d.fnumber,d.fcustomerid,d.fcusproductid,date_format(d.farrivetime,'%Y-%m-%d %T') farrivetime,d.flinkman,d.flinkphone,d.famount,d.faddress,d.fdescription,fsaleorderid,ifnull(fordernumber,'') fordernumber,forderentryid,d.fimportEAS,d.fimportEASuserid,d.fimportEAStime,d.fmatched,d.fouted,d.foutorid,d.fouttime,d.faudited,ifnull(d.faudittime,'') faudittime,ifnull(ad.fname,'') fauditor from t_ord_deliverorder d left join t_sys_user u1 on u1.fid=d.fcreatorid left join t_sys_user u2 on u2.fid=d.fupdateuserid left join t_sys_user u3 on u3.fid=d.fordermanid left join t_bd_customer c on c.fid=d.fcustomerid left join t_bd_custproduct cpdt on cpdt.fid=d.fcusproductid left join t_pdt_productdef pdef on pdef.fid=d.fproductid left join t_sys_user ad on ad.fid=d.fauditorid  where fouted=0 ";
		sql=sql +saleOrderDao.QueryFilterByUser(request, "d.fcustomerid", "d.fsupplierid");
		ListResult result;
		reponse.setCharacterEncoding("utf-8");
		try {
			result = DeliverorderDao.QueryFilterList(sql, request);
			reponse.getWriter().write(JsonUtil.result(true, "", result));
		} catch (DJException e) {
			reponse.getWriter().write(
					JsonUtil.result(false, e.getMessage(), "", ""));
		}

		return null;
	}
	
	@RequestMapping("/GetDeliverorderSendListMV")
	public String GetDeliverorderSendListMV(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String sql = "select fpcmordernumber,_productname productname,fordermanid,_orderman forderman,fordertime,fordered,_creator fcreator,_lastupdater flastupdater,_custname fcustname,_custpdtname cutpdtname,fid,fcreatorid,fcreatetime,fupdateuserid,fupdatetime,fnumber,fcustomerid,fcusproductid,date_format(farrivetime,'%Y-%m-%d %T') farrivetime,flinkman,flinkphone,famount,faddress,fdescription,fsaleorderid,ifnull(fordernumber,'') fordernumber,forderentryid,fimportEAS,fimportEASuserid,fimportEAStime,fmatched,fouted,foutorid,fouttime,faudited,ifnull(faudittime,'') faudittime,_auditor fauditor from t_ord_deliverorder_mv d where fouted=0 ";
		sql=sql +saleOrderDao.QueryFilterByUser(request, "fcustomerid", "fsupplierid");
		try {
			ListResult result = DeliverorderDao.QueryFilterList(sql, request);
			response.getWriter().write(JsonUtil.result(true, "", result));
		} catch (DJException e) {
			response.getWriter().write(
					JsonUtil.result(false, e.getMessage(), "", ""));
		}

		return null;
	}
	
	@RequestMapping("/GetSDeliverorderSendList")
	public String GetSDeliverorderSendList(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
//		String sql = "select d.fordermanid,u3.fname forderman,d.fordertime,d.fordered,u1.fname as fcreator,u2.fname as flastupdater,c.fname as fcustname,cpdt.fname cutpdtname,d.fid,d.fcreatorid,d.fcreatetime,d.fupdateuserid,d.fupdatetime,d.fnumber,d.fcustomerid,d.fcusproductid,date_format(d.farrivetime,'%Y-%m-%d %T') farrivetime,d.flinkman,d.flinkphone,d.famount,d.faddress,d.fdescription,fsaleorderid,ifnull(fordernumber,'') fordernumber,forderentryid,d.fimportEAS,d.fimportEASuserid,d.fimportEAStime,d.fouted,d.foutorid,d.fouttime from t_ord_deliverorder d left join t_sys_user u1 on u1.fid=d.fcreatorid left join t_sys_user u2 on u2.fid=d.fupdateuserid left join t_sys_user u3 on u3.fid=d.fordermanid left join t_bd_customer c on c.fid=d.fcustomerid left join t_bd_custproduct cpdt on cpdt.fid=d.fcusproductid where fouted=0 ";
		String sql = "select d.fpcmordernumber,pdef.fname productname,d.fordermanid,u3.fname forderman,d.fordertime,d.fordered,u1.fname as fcreator,u2.fname as flastupdater,c.fname as fcustname,cpdt.fname cutpdtname,d.fid,d.fcreatorid,d.fcreatetime,d.fupdateuserid,d.fupdatetime,d.fnumber,d.fcustomerid,d.fcusproductid,date_format(d.farrivetime,'%Y-%m-%d %T') farrivetime,d.flinkman,d.flinkphone,d.famount,ifnull(d.fassembleQty,0) fassembleQty,ifnull(d.foutQty,0) foutQty,d.faddress,d.fdescription,fsaleorderid,ifnull(fordernumber,'') fordernumber,forderentryid,d.fimportEAS,d.fimportEASuserid,d.fimportEAStime,d.fmatched,d.fouted,d.foutorid,d.fouttime,d.faudited,ifnull(d.faudittime,'') faudittime,ifnull(ad.fname,'') fauditor,d.ftype,ifnull(pdef.fcharacter,'') fcharacter from t_ord_deliverorder d left join t_sys_user u1 on u1.fid=d.fcreatorid left join t_sys_user u2 on u2.fid=d.fupdateuserid left join t_sys_user u3 on u3.fid=d.fordermanid left join t_bd_customer c on c.fid=d.fcustomerid left join t_bd_custproduct cpdt on cpdt.fid=d.fcusproductid left join t_pdt_productdef pdef on pdef.fid=d.fproductid left join t_sys_user ad on ad.fid=d.fauditorid  where fouted=0 and  ifnull(d.faudited,0)=1 ";
		sql=sql +saleOrderDao.QueryFilterByUser(request, "d.fcustomerid", "d.fsupplierid");
		ListResult result;
		reponse.setCharacterEncoding("utf-8");
		try {
			result = DeliverorderDao.QueryFilterList(sql, request);
			reponse.getWriter().write(JsonUtil.result(true, "", result));
		} catch (DJException e) {
			reponse.getWriter().write(
					JsonUtil.result(false, e.getMessage(), "", ""));
		}

		return null;
	}
	
	@RequestMapping(value = "/SaveDeliverorder")
	public String SaveDeliverorder(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		String result = "";
		Deliverorder vinfo = null;
		try {
			String userid = ((Useronline) request.getSession().getAttribute(
					"Useronline")).getFuserid();
			String fid = request.getParameter("fid");
			if (fid != null && !"".equals(fid)) {
				vinfo = DeliverorderDao.Query(fid);
				if (vinfo.getFordered() == 1) {
					throw new DJException("已下单不能修改!");
				}
				vinfo.setFnumber(request.getParameter("fnumber"));
			} else {
				vinfo = new Deliverorder();
				// vinfo.setFid(DeliversDao.GetEASid("57C24810"));
				vinfo.setFid(fid);
				vinfo.setFnumber(DeliverorderDao.getFnumber("t_ord_delivers",
						"DV", 4));
				vinfo.setFcreatorid(userid);
				vinfo.setFcreatetime(new Date());
				vinfo.setFordermanid(null);
				vinfo.setFordered(0);
				vinfo.setFouted(0);
				vinfo.setFordertime(null);

			}
			
			DataUtil.verifyNotNullAndDataAndPermissions(null,
					new String[][] {
							{ "fcustomerid", "t_bd_customer", "", "" },
							{ "fcusproductid", "t_bd_custproduct", "FCUSTOMERID", "" } }, request,
							DeliverorderDao);
			
			vinfo.setFupdatetime(new Date());
			vinfo.setFupdateuserid(userid);
			vinfo.setFcustomerid(request.getParameter("fcustomerid"));
			vinfo.setFcusproductid(request.getParameter("fcusproductid"));
			vinfo.setFaddressid(request.getParameter("faddressid"));
			vinfo.setFaddress(request.getParameter("faddress"));
			
			if(!DataUtil.dateFormatCheck(request.getParameter(TIME_FIELD), true)){
				throw new DJException("送达日期格式错误或小于当前时间！");
			}
			DateFormat f = new SimpleDateFormat("yyyy-MM-dd");
			vinfo.setFarrivetime(f.parse(request.getParameter(TIME_FIELD).toString()));
			
			vinfo.setFlinkman(request.getParameter("flinkman"));
			vinfo.setFlinkphone(request.getParameter("flinkphone"));

			if(!DataUtil.positiveIntegerCheck(request.getParameter("famount"))){
				throw new DJException("要货申请数量必须大于0！");
			}
			vinfo.setFamount(new Integer(request.getParameter("famount")));
				
			vinfo.setFdescription(request.getParameter("fdescription"));

			HashMap<String, Object> params = DeliverorderDao.ExecSave(vinfo);
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

	@RequestMapping("/DelDeliverorderList")
	public String DelDeliverorderList(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		String result = "";
		String fidcls = request.getParameter("fidcls");
		fidcls="('"+fidcls.replace(",","','")+"')";
		try {
			String hql = "Delete FROM Deliverorder where fordered=0 and fid in "
					+ fidcls;
			DeliverorderDao.ExecByHql(hql);
			result = JsonUtil.result(true, "删除成功!", "", "");
			reponse.setCharacterEncoding("utf-8");
		} catch (Exception e) {
			result = JsonUtil.result(false, e.getMessage(), "", "");
			log.error("DelDeliverorderList error", e);
		}
		reponse.getWriter().write(result);
		return null;
	}

	@RequestMapping("/getDeliverorderInfo")
	public String getDeliverorderInfo(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		reponse.setCharacterEncoding("utf-8");
		try {
			String fid = request.getParameter("fid");
			String sql = "select u1.fname as fcreator,u2.fname as flastupdater,c.fname as fcustomerid_fname,cpdt.fname fcusproductid_fname,d.fid,d.fcreatorid,d.fcreatetime,d.fupdateuserid,d.fupdatetime,d.fnumber,d.fcustomerid fcustomerid_fid,d.fcusproductid fcusproductid_fid,date_format(d.farrivetime,'%Y-%m-%d') farrivetime,d.flinkman,d.flinkphone,d.famount,d.faddress,d.fdescription ,d.faddressid faddressid_fid,ifnull(a.fname,'') faddressid_fname from t_ord_delivers d left join t_sys_user u1 on  u1.fid=d.fcreatorid left join t_sys_user u2 on  u2.fid=d.fupdateuserid left join t_bd_customer c on c.fid=d.fcustomerid left join t_bd_custproduct cpdt on cpdt.fid=d.fcusproductid  left join t_bd_address a on a.fid=d.faddressid where d.fid = '"
					+ request.getParameter("fid") + "'";
			List<HashMap<String, Object>> result = DeliverorderDao
					.QueryBySql(sql);
			reponse.getWriter().write(JsonUtil.result(true, "", "", result));
		} catch (Exception e) {
			reponse.getWriter().write(
					JsonUtil.result(false, e.getMessage(), "", ""));
		}
		return null;
	}

	@RequestMapping("/AddDeliverorder")
	public String AddDeliverorder(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		reponse.setCharacterEncoding("utf-8");
		reponse.getWriter().write(JsonUtil.result(true, "", "", ""));
		return null;

	}

	@RequestMapping(value = "/deliverordertoExcel")
	public String deliverordertoExcel(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		try {
			request.setAttribute("nolimit", 0);
			String sql="select u1.fname as 创建人,u2.fname as 修改人,c.fname as 客户名称,cpdt.fname 客户产品名称,d.fcreatetime 创建时间,d.fupdatetime 修改时间,d.fnumber 配送单号,date_format(d.farrivetime,'%Y-%m-%d %T') 配送时间,d.flinkman 联系人,d.flinkphone 联系电话,d.famount 数量,d.faddress 配送地址,d.fdescription 备注,ifnull(fordernumber,'') 订单编号,d.fimportEAS 导入EAS,d.fouted 是否发货,d.faudited 是否审核 ,ifnull(d.faudittime,'') 审核时间,ifnull(ad.fname,'') 审核人 ,fmatched 是否配货,ifnull(s.fname,'') 供应商名称 from t_ord_deliverorder d left join t_sys_user u1 on u1.fid=d.fcreatorid left join t_sys_user u2 on u2.fid=d.fupdateuserid left join t_sys_user u3 on u3.fid=d.fordermanid left join t_bd_customer c on c.fid=d.fcustomerid left join t_bd_custproduct cpdt on cpdt.fid=d.fcusproductid left join t_sys_user ad on ad.fid=d.fauditorid  left join t_sys_supplier s on s.fid=d.fsupplierid  where 1=1 ";
//			String sql = "select pdef.fname 产品名称 ,u1.fname 创建人,u2.fname 修改人,c.fname 客户名称,cpdt.fname 客户产品,d.fcreatetime 创建时间,d.fupdatetime 修改时间,d.fnumber 配送单号,date_format(d.farrivetime,'%Y-%m-%d %T') 配送时间,d.flinkman 联系人,d.flinkphone 联系电话,d.famount 数量,d.faddress 配送地址,d.fdescription 备注 ,ifnull(fordernumber,'') 制造商单号,d.fimportEAS 是否导入,d.fmatched 是否配货 ,d.fouted 是否发货,d.faudited 是否审核,ifnull(d.faudittime,'') 审核时间,ifnull(ad.fname,'') 审核人,ifnull(s.fname,'') 制造商名称  from t_ord_deliverorder d left join t_sys_user u1 on u1.fid=d.fcreatorid left join t_sys_user u2 on u2.fid=d.fupdateuserid left join t_sys_user u3 on u3.fid=d.fordermanid left join t_bd_customer c on c.fid=d.fcustomerid left join t_bd_custproduct cpdt on cpdt.fid=d.fcusproductid left join t_pdt_productdef pdef on pdef.fid=d.fproductid left join t_sys_user ad on ad.fid=d.fauditorid  left join t_ord_productplan p on p.fid=d.forderentryid  left join t_sys_supplier s on s.fid=p.fsupplierid where 1=1 ";
			sql=sql +saleOrderDao.QueryFilterByUser(request, "d.fcustomerid", "d.fsupplierid");
//			String sql = "select u1.fname as 创建人,u2.fname as 修改人,c.fname as 客户名称,cpdt.fname 客户产品名称,d.fid,d.fcreatetime 创建时间,d.fupdatetime 修改时间,d.fnumber 采购订单号,d.fcustomerid,d.fcusproductid,date_format(d.farrivetime,'%Y-%m-%d') 送达时间,d.flinkman 联系人,d.flinkphone 联系电话,d.famount 配送数量,d.faddress 配送地址,d.fdescription 备注  from t_ord_delivers d left join t_sys_user u1 on  u1.fid=d.fcreatorid left join t_sys_user u2 on  u2.fid=d.fupdateuserid left join t_bd_customer c on c.fid=d.fcustomerid left join t_bd_custproduct cpdt on cpdt.fid=d.fcusproductid  where 1=1 ";
//			sql=sql +saleOrderDao.QueryFilterByUser(request, "d.fcustomerid", "d.fsupplierid");
			// String fcustomerid = request.getParameter("fcustomerid");
			// if (!"".equals(fcustomerid)&&fcustomerid!=null) {
			// sql += " where d.fcustomerid='" + fcustomerid + "'";
			// }
			request.setAttribute("nocount", true);
			ListResult result;
			result = DeliverorderDao.QueryFilterList(sql, request);
			ExcelUtil.toexcel(reponse, result);
			// InputStream inputStream = new
			// FileInputStream(ExcelUtil.toexcel(result));
			// OutputStream os = reponse.getOutputStream();
			// byte[] b = new byte[1024];
			// int length;
			// while ((length = inputStream.read(b)) > 0) {
			// os.write(b, 0, length);
			// }
			// inputStream.close();
		} catch (DJException e) {
			// TODO Auto-generated catch block
			reponse.getWriter().write(
					JsonUtil.result(false, e.getMessage(), "", ""));
		}

		return null;

	}
	
	@RequestMapping(value = "/DeliverorderSendtoExcel")
	public String DeliverorderSendtoExcel(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		try {
//			request.setAttribute("nolimit", 0);
			String sql="select pdef.fname 产品名称,u1.fname as 创建人,u2.fname as 修改人,c.fname as 客户名称,cpdt.fname 客户产品名称,d.fcreatetime 创建时间,d.fupdatetime 修改时间,d.fnumber 配送单号,date_format(d.farrivetime,'%Y-%m-%d %T') 配送时间,d.flinkman 联系人,d.flinkphone 联系电话,d.famount 数量,d.faddress 配送地址,d.fdescription 备注,ifnull(fordernumber,'') 订单编号,d.fimportEAS 导入EAS,d.fouted 是否发货,d.faudited 是否审核 ,ifnull(d.faudittime,'') 审核时间,ifnull(ad.fname,'') 审核人 ,fmatched 是否配货,ifnull(s.fname,'') 供应商名称 from t_ord_deliverorder d left join t_sys_user u1 on u1.fid=d.fcreatorid left join t_sys_user u2 on u2.fid=d.fupdateuserid left join t_sys_user u3 on u3.fid=d.fordermanid left join t_bd_customer c on c.fid=d.fcustomerid left join t_bd_custproduct cpdt on cpdt.fid=d.fcusproductid left join t_pdt_productdef pdef on pdef.fid=d.fproductid left join t_sys_user ad on ad.fid=d.fauditorid  left join t_sys_supplier s on s.fid=d.fsupplierid  where fouted=0 ";
//			String sql = "select pdef.fname 产品名称 ,u1.fname 创建人,u2.fname 修改人,c.fname 客户名称,cpdt.fname 客户产品,d.fcreatetime 创建时间,d.fupdatetime 修改时间,d.fnumber 配送单号,date_format(d.farrivetime,'%Y-%m-%d %T') 配送时间,d.flinkman 联系人,d.flinkphone 联系电话,d.famount 数量,d.faddress 配送地址,d.fdescription 备注 ,ifnull(fordernumber,'') 制造商单号,d.fimportEAS 是否导入,d.fmatched 是否配货 ,d.fouted 是否发货,d.faudited 是否审核,ifnull(d.faudittime,'') 审核时间,ifnull(ad.fname,'') 审核人,ifnull(s.fname,'') 制造商名称  from t_ord_deliverorder d left join t_sys_user u1 on u1.fid=d.fcreatorid left join t_sys_user u2 on u2.fid=d.fupdateuserid left join t_sys_user u3 on u3.fid=d.fordermanid left join t_bd_customer c on c.fid=d.fcustomerid left join t_bd_custproduct cpdt on cpdt.fid=d.fcusproductid left join t_pdt_productdef pdef on pdef.fid=d.fproductid left join t_sys_user ad on ad.fid=d.fauditorid  left join t_ord_productplan p on p.fid=d.forderentryid  left join t_sys_supplier s on s.fid=p.fsupplierid where 1=1 ";
			sql=sql +saleOrderDao.QueryFilterByUser(request, "d.fcustomerid", "d.fsupplierid");
//			String sql = "select u1.fname as 创建人,u2.fname as 修改人,c.fname as 客户名称,cpdt.fname 客户产品名称,d.fid,d.fcreatetime 创建时间,d.fupdatetime 修改时间,d.fnumber 采购订单号,d.fcustomerid,d.fcusproductid,date_format(d.farrivetime,'%Y-%m-%d') 送达时间,d.flinkman 联系人,d.flinkphone 联系电话,d.famount 配送数量,d.faddress 配送地址,d.fdescription 备注  from t_ord_delivers d left join t_sys_user u1 on  u1.fid=d.fcreatorid left join t_sys_user u2 on  u2.fid=d.fupdateuserid left join t_bd_customer c on c.fid=d.fcustomerid left join t_bd_custproduct cpdt on cpdt.fid=d.fcusproductid  where 1=1 ";
//			sql=sql +saleOrderDao.QueryFilterByUser(request, "d.fcustomerid", "d.fsupplierid");
			// String fcustomerid = request.getParameter("fcustomerid");
			// if (!"".equals(fcustomerid)&&fcustomerid!=null) {
			// sql += " where d.fcustomerid='" + fcustomerid + "'";
			// }
			ListResult result;
			result = DeliverorderDao.QueryFilterList(sql, request);
			ExcelUtil.toexcel(reponse, result);
			// InputStream inputStream = new
			// FileInputStream(ExcelUtil.toexcel(result));
			// OutputStream os = reponse.getOutputStream();
			// byte[] b = new byte[1024];
			// int length;
			// while ((length = inputStream.read(b)) > 0) {
			// os.write(b, 0, length);
			// }
			// inputStream.close();
		} catch (DJException e) {
			// TODO Auto-generated catch block
			reponse.getWriter().write(
					JsonUtil.result(false, e.getMessage(), "", ""));
		}

		return null;

	}
	
	@RequestMapping(value = "/sdeliverordertoExcel")
	public String sdeliverordertoExcel(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		try {
			String sql = "select pdef.fname 产品名称 ,u1.fname 创建人,u2.fname 修改人,c.fname 客户名称,cpdt.fname 客户产品,d.fcreatetime 创建时间,d.fupdatetime 修改时间,d.fnumber 配送单号,date_format(d.farrivetime,'%Y-%m-%d %T') 配送时间,d.flinkman 联系人,d.flinkphone 联系电话,d.famount 数量,d.faddress 配送地址,d.fdescription 备注 ,ifnull(fordernumber,'') 制造商单号,d.fimportEAS 是否导入,d.fmatched 是否配货 ,d.fouted 是否发货,d.faudited 是否审核,ifnull(d.faudittime,'') 审核时间,ifnull(ad.fname,'') 审核人  from t_ord_deliverorder d left join t_sys_user u1 on u1.fid=d.fcreatorid left join t_sys_user u2 on u2.fid=d.fupdateuserid left join t_sys_user u3 on u3.fid=d.fordermanid left join t_bd_customer c on c.fid=d.fcustomerid left join t_bd_custproduct cpdt on cpdt.fid=d.fcusproductid left join t_pdt_productdef pdef on pdef.fid=d.fproductid left join t_sys_user ad on ad.fid=d.fauditorid  where  ifnull(d.faudited,0)=1 ";
			sql=sql +saleOrderDao.QueryFilterByUser(request, "d.fcustomerid", "d.fsupplierid");
			// String fcustomerid = request.getParameter("fcustomerid");
			// if (!"".equals(fcustomerid)&&fcustomerid!=null) {
			// sql += " where d.fcustomerid='" + fcustomerid + "'";
			// }
			ListResult result;
			result = DeliverorderDao.QueryFilterList(sql, request);
			ExcelUtil.toexcel(reponse, result);
			// InputStream inputStream = new
			// FileInputStream(ExcelUtil.toexcel(result));
			// OutputStream os = reponse.getOutputStream();
			// byte[] b = new byte[1024];
			// int length;
			// while ((length = inputStream.read(b)) > 0) {
			// os.write(b, 0, length);
			// }
			// inputStream.close();
		} catch (DJException e) {
			// TODO Auto-generated catch block
			reponse.getWriter().write(
					JsonUtil.result(false, e.getMessage(), "", ""));
		}

		return null;

	}
	
	@RequestMapping(value = "/sdeliverordersendtoExcel")
	public String sdeliverordersendtoExcel(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		try {
			request.setAttribute("nolimit", 0);
			String sql = "select pdef.fname 产品名称 ,u1.fname 创建人,u2.fname 修改人,c.fname 客户名称,cpdt.fname 客户产品,d.fcreatetime 创建时间,d.fupdatetime 修改时间,d.fnumber 配送单号,date_format(d.farrivetime,'%Y-%m-%d %T') 配送时间,d.flinkman 联系人,d.flinkphone 联系电话,d.famount 数量,d.faddress 配送地址,d.fdescription 备注 ,ifnull(fordernumber,'') 制造商单号,d.fimportEAS 是否导入,d.fmatched 是否配货 ,d.fouted 是否发货,d.faudited 是否审核,ifnull(d.faudittime,'') 审核时间,ifnull(ad.fname,'') 审核人  from t_ord_deliverorder d left join t_sys_user u1 on u1.fid=d.fcreatorid left join t_sys_user u2 on u2.fid=d.fupdateuserid left join t_sys_user u3 on u3.fid=d.fordermanid left join t_bd_customer c on c.fid=d.fcustomerid left join t_bd_custproduct cpdt on cpdt.fid=d.fcusproductid left join t_pdt_productdef pdef on pdef.fid=d.fproductid left join t_sys_user ad on ad.fid=d.fauditorid  where fouted=0 and  ifnull(d.faudited,0)=1 ";
			sql=sql +saleOrderDao.QueryFilterByUser(request, "d.fcustomerid", "d.fsupplierid");
		
			ListResult result;
			result = DeliverorderDao.QueryFilterList(sql, request);
			ExcelUtil.toexcel(reponse, result);
			// InputStream inputStream = new
			// FileInputStream(ExcelUtil.toexcel(result));
			// OutputStream os = reponse.getOutputStream();
			// byte[] b = new byte[1024];
			// int length;
			// while ((length = inputStream.read(b)) > 0) {
			// os.write(b, 0, length);
			// }
			// inputStream.close();
		} catch (DJException e) {
			// TODO Auto-generated catch block
			reponse.getWriter().write(
					JsonUtil.result(false, e.getMessage(), "", ""));
		}

		return null;

	}
	
	

	/**
	 * delivers发货;
	 */
	@RequestMapping(value = "/deliverorderSend")
	public String deliverorderSend(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException, SQLException,
			DJException, ParseException {
		String fdeliverids = request.getParameter("fidcls");
		reponse.setCharacterEncoding("utf-8");
		try {
			fdeliverids="('"+fdeliverids.replace(",","','")+"')";
			String deliverssql  = "select fid from t_ord_deliverorder where fouted=1 and fid in "
					+ fdeliverids;
			List<HashMap<String, Object>> delivers = DeliverorderDao.QueryBySql(deliverssql);
			if (delivers.size() > 0) {
				reponse.getWriter().write(
						JsonUtil.result(false, "已经发货，不能再发货！", "", ""));
				return null;
			}
			deliverssql = "update t_ord_deliverorder set fouted=1,fmatched=1,foutorid='"
					+ ((Useronline) request.getSession().getAttribute(
							"Useronline")).getFuserid()
					+ "',fouttime=now() where fid in " + fdeliverids;
			DeliverorderDao.ExecBySql(deliverssql);
			reponse.getWriter().write(JsonUtil.result(true, "发货成功", "", ""));
		} catch (Exception e) {
			reponse.getWriter().write(JsonUtil.result(false, "发货失败！", "", ""));
		}
		return null;

	}

	/**
	 * delivers出库;
	 */
	@RequestMapping(value = "/deliverorderOut")
	public String deliverorderOut(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException, SQLException,
			DJException, ParseException {
		reponse.setCharacterEncoding("utf-8");
		String result = "";
		String FWarehouseID = request.getParameter("FWarehouseID");
		String FWarehouseSiteID = request.getParameter("FWarehouseSiteID");
		String forderid = request.getParameter("forderid");
		String forderEntryid = request.getParameter("forderEntryid");
		String fdeliverid = request.getParameter("fdeliverid");
		String fproductid = "";
		int famount = new Integer(request.getParameter("famount"));
		String fordertype = "";
		try {
			Deliverorder delivers = DeliverorderDao.Query(fdeliverid);
			if (delivers.getFordered() == 0) {
				throw new DJException("未下单不能出库！");
			}
			if (delivers.getFouted() == 1) {
				throw new DJException("已出库不能重复出库！");
			}

			String oql = "select fproductdefid,famount,fordertype from t_ord_saleorder s where fid = '"
					+ forderEntryid + "' ";
			List<HashMap<String, Object>> orderlist = DeliverorderDao
					.QueryBySql(oql);
			if (orderlist.size() > 0) {
				HashMap orderinfo = orderlist.get(0);
				fproductid = orderinfo.get("fproductdefid").toString();
				fordertype = orderinfo.get("fordertype").toString();
			}

			if (fordertype.equals("2") || fordertype.equals("4")) {
				// 套装订单;

			} else {
				// 普通订单;
				// VMI系统库存必须大于等于要货申请库存数量;
				String sql = "SELECT ifnull(sum(w.fbalanceqty),0) fbalanceqty FROM test.t_inv_storebalance w where w.FProductID='"
						+ fproductid
						+ "' and w.fsaleorderid='"
						+ forderid
						+ "' and w.forderentryid='"
						+ forderEntryid
						+ "' and w.fwarehouseId='"
						+ FWarehouseID
						+ "' and w.fwarehouseSiteId='"
						+ FWarehouseSiteID
						+ "' ";
				List<HashMap<String, Object>> balancelist = DeliverorderDao
						.QueryBySql(sql);
				if (balancelist.size() < 0) {
					throw new DJException("没有库存不能出库！");
				} else {
					HashMap qtyinfo = balancelist.get(0);
					if (qtyinfo.get("fbalanceqty").toString().equals("0")) {
						throw new DJException("没有库存不能出库！");
					}
					sql = "SELECT fid FROM t_inv_storebalance w where w.FProductID='"
							+ fproductid
							+ "' and w.fsaleorderid='"
							+ forderid
							+ "' and w.forderentryid='"
							+ forderEntryid
							+ "' and w.fwarehouseId='" + FWarehouseID + "' ";
					List<HashMap<String, Object>> sblist = DeliverorderDao
							.QueryBySql(sql);
					outbalance(sblist, famount);
				}
			}

			String deliverssql = "SET SQL_SAFE_UPDATES=0 ";
			DeliverorderDao.ExecBySql(deliverssql);
			deliverssql = "update t_ord_delivers set fouted=1,foutorid='"
					+ ((Useronline) request.getSession().getAttribute(
							"Useronline")).getFuserid()
					+ "',fouttime=now() where fid = '" + fdeliverid + "'";
			DeliverorderDao.ExecBySql(deliverssql);
		} catch (Exception e) {
			reponse.getWriter().write(
					JsonUtil.result(false, "出库失败：" + e.getMessage(), "", ""));
			return null;
		}
		reponse.getWriter().write(JsonUtil.result(true, "要货申请出库成功", "", ""));
		return null;
	}

	private void outbalance(List<HashMap<String, Object>> sblist, int famount) {
		// TODO Auto-generated method stub
		int bigRest = famount;
		int bigThisTotalQty = 0;
		for (int i = 0; i < sblist.size(); i++) {
			HashMap sbinfo = sblist.get(i);
			String sbId = sbinfo.get("fid").toString();
			Storebalance sbInfo = storebalanceDao.Query(sbId);

			int bigEachLocationQty = sbInfo.getFbalanceqty();
			if (bigEachLocationQty <= 0) {
				continue;// 库存余额为零，则不从该库位上出库。
			}
			bigRest = bigRest - bigEachLocationQty;
			if (bigRest < 0)// >=0。此时该库位已经可以满足出库要求。
			{
				bigThisTotalQty = bigThisTotalQty + famount;
				sbInfo.setFoutqty(sbInfo.getFoutqty()
						+ (bigEachLocationQty + bigRest));// 出库增加
				sbInfo.setFbalanceqty(bigRest);
				// isb.update(new ObjectUuidPK(sbId), sbInfo);
				storebalanceDao.ExecSave(sbInfo);// 更新库存余额表
				// idao.updateBatch(new ObjectUuidPK(sbId), sbInfo);// 更新库存余额表
				// addQtyOfLocation(map,parentKey,locationId,bigIssueQty);
				break;
			} else
			// <0
			{
				bigThisTotalQty = bigThisTotalQty + bigEachLocationQty;
				// sbInfo.setActionType(StoreActionType.SA_OUT);
				sbInfo.setFoutqty(sbInfo.getFoutqty() + bigEachLocationQty);// 出库增加
				sbInfo.setFbalanceqty(0);// 该库位已经没有库存，要从下一个库位上继续出库。
				// isb.update(new ObjectUuidPK(sbId), sbInfo);
				storebalanceDao.ExecSave(sbInfo);// 更新库存余额表
				// idao.updateBatch(new ObjectUuidPK(sbId), sbInfo);
				// addQtyOfLocation(map,parentKey,locationId,bigEachLocationQty);
			}
		}
	}

	/*
	 * 根据VMI平台库存下单
	 */
	// @RequestMapping(value = "/VMIorder")
	// public String VMIorder(HttpServletRequest request,
	// HttpServletResponse reponse) throws Exception {
	// if(running){
	// return null;
	// }
	// running = true;
	// String result = "";
	// Delivers pinfo = null;
	// try {
	// String userid = ((Useronline) request.getSession().getAttribute(
	// "Useronline")).getFuserid();
	// String fid = request.getParameter("fid");
	// if (fid != null && !"".equals(fid)) {
	// pinfo = DeliversDao.Query(fid);
	// if(pinfo.getFordered()==1){
	// throw new DJException("已下单不能再次下单!");
	// }else{
	// pinfo.setFid(fid);
	// pinfo.setFordermanid(userid);
	// pinfo.setFordered(1);
	// pinfo.setFordertime(new Date());
	// }
	// } else {
	// throw new DJException("未保存不能下单!");
	// }
	//
	// // Class.forName("oracle.jdbc.driver.OracleDriver");
	// // Connection conn =
	// DriverManager.getConnection("jdbc:oracle:thin:@192.168.0.198:1521:orcl",
	// "dbo_update", "dbo_update");
	// // Connection conn=DeliversDao.GetEASConn().GetConn(request);
	// // String orderEntryid = saleOrderDao.GetEASid("BF9A2C0D");
	// // String orderid = saleOrderDao.GetEASid("A8114365");
	// String orderid = saleOrderDao.CreateUUid();
	// try {
	// String fnumber = request.getParameter("fnumber");
	// String fcusproductid = request.getParameter("fcusproductid");
	// String flinkman = request.getParameter("flinkman");
	// String flinkphone = request.getParameter("flinkphone");
	// String vmicustomerid = request.getParameter("fcustomerid");
	// int famount = 0;
	// if(request.getParameter("famount")!=null){
	// famount = Integer.valueOf(request.getParameter("famount"));
	// }
	// // DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	// DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
	// Date farrivetime = df.parse(request.getParameter("farrivetime"));
	// String faddress = request.getParameter("faddress");
	//
	// String sql =
	// "select fid from t_pdt_productdef where fid in (select fproductdefid from t_sys_custrelationproduct where fcustid =:fcustid )";
	// params p=new params();
	// p.put("fcustid",fcusproductid);
	// List<HashMap<String,Object>> data= DeliversDao.QueryBySql(sql, p);
	// // if (!data.isEmpty()) {
	// // JSONArray j = JSONArray.fromObject(data);
	//
	// PreparedStatement stmt = null;
	//
	// BigDecimal stock = new BigDecimal(0);
	//
	// // for (int i = 0; i < j.size(); i++) {
	// // if (j.size()>0) {//客户产品只有一个对应的产品ID;
	// if (data.size()>0) {//客户产品只有一个对应的产品ID;
	// // JSONObject o = j.getJSONObject(0);
	// HashMap o = data.get(0);
	// String fproductid = "";
	// // String vmiproductid = "";
	// if(o.get("fid")!=null){
	// fproductid = o.get("fid").toString();
	// // vmiproductid = o.get("fid").toString();
	// }
	// // int ftype ;
	// int fnewtype = 3;
	// String isassemble = "0";
	// String EAScustomerid = "";
	// try{
	// ResultSet rs = null;
	// // stmt =
	// conn.prepareStatement("select d.fid,d.fassemble,d.ftype,d.fnewtype,d.fcustomerid,d.feffect,c.fstate from t_pdt_productdef d left join T_BAL_CooperateLogger c on c.fcustomerid=d.fcustomerid where d.fid='"+
	// fproductid +
	// "' and  c.FCreateTime in (select max(c.FCreateTime) from T_BAL_CooperateLogger c where c.fcustomerid = d.fcustomerid) ");
	// // ResultSet rst = stmt.executeQuery();
	// sql =
	// "select d.fid,d.fassemble,d.fnewtype,d.fcustomerid,d.feffect from t_pdt_productdef d where fid = :fid ";
	// p=new params();
	// p.put("fid",fproductid);
	// List<HashMap<String,Object>> pdtlist= DeliversDao.QueryBySql(sql, p);
	// if(pdtlist.size()>0){
	// HashMap pdtinfo = pdtlist.get(0);
	// if(pdtinfo.get("feffect").equals("0")){
	// throw new DJException("产品被禁用，无法自动下单！");
	// }
	// if(pdtinfo.get("fassemble")!=null){
	// isassemble = pdtinfo.get("fassemble").toString();
	// }
	// // ftype = new Integer(rst.get("ftype").toString());
	// if(pdtinfo.get("fnewtype")!=null &&
	// !pdtinfo.get("fnewtype").toString().equals("")){
	// fnewtype = new Integer(pdtinfo.get("fnewtype").toString());
	// }
	//
	// EAScustomerid = pdtinfo.get("fcustomerid").toString();
	//
	// //库存数量
	// if(isassemble.equals("1") || (fnewtype!=2 && fnewtype!=4)){
	// sql =
	// "select ifnull(sum(b.fbalanceqty),0) balanceqty  from t_inv_storebalance b where b.fcustomerid= :fcustomerid and b.fproductid= :fproductid ";
	// p=new params();
	// p.put("fcustomerid",vmicustomerid);
	// p.put("fproductid",fproductid);
	// List<HashMap<String,Object>> sblist= DeliversDao.QueryBySql(sql, p);
	// if(sblist.size()>0){
	// HashMap sbinfo = sblist.get(0);
	// stock=new BigDecimal(sbinfo.get("balanceqty").toString());
	// }
	//
	// //在生产数量
	// sql =
	// "select ifnull(sum(case when s.famount<ifnull(s.fstockinqty,0) then 0 else s.famount-ifnull(s.fstockinqty,0) end),0) unAmount from t_ord_saleorder s where faffirmed=1 and s.fcustomerid = :fcustomerid and s.fproductdefid = :fproductid ";
	// p=new params();
	// p.put("fcustomerid",vmicustomerid);
	// p.put("fproductid",fproductid);
	// List<HashMap<String,Object>> unsblist= DeliversDao.QueryBySql(sql, p);
	// if(unsblist.size()>0){
	// HashMap unsbinfo = unsblist.get(0);
	// stock=stock.add(new BigDecimal(unsbinfo.get("unAmount").toString()));
	// }
	//
	// }else{
	// //套装
	// String productIDs = "";
	// sql =
	// "select d.fid,d.fcombination from T_PDT_ProductDefProducts pd left join t_pdt_productdef d on d.fid=pd.fproductid where fparentid = :fproductid ";
	// p=new params();
	// p.put("fproductid",fproductid);
	// List<HashMap<String,Object>> suitpdtlist= DeliversDao.QueryBySql(sql, p);
	// for(int i=0;i<suitpdtlist.size();i++){
	// HashMap suitpdtinfo = suitpdtlist.get(i);
	// if(suitpdtinfo.get("fcombination")!=null &&
	// suitpdtinfo.get("fcombination").toString().equals("0")){
	// if(productIDs.length()>0){
	// productIDs = productIDs + "','" + rs.getString("fid");
	// }else{
	// productIDs = "('" + rs.getString("fid");
	// }
	// }
	// }
	// if(productIDs.length()==0){
	// productIDs = "('" ;
	// }
	// productIDs = productIDs + "')";
	//
	//
	// //套装库存总量
	// StringBuffer oql = new
	// StringBuffer("select ifnull(min(qty),0) Qty from (select t.fproductid,ifnull(sum(t1.fbalanceqty/t.famount),0)+sum((case when t2.famount<ifnull(t2.fstockinqty,0) then 0 else t2.famount-ifnull(t2.fstockinqty,0) end)/t.famount) qty from T_PDT_ProductDefProducts t ");
	// oql.append("left join t_inv_storebalance t1 on t.fproductid=t1.fproductid left join t_ord_saleorder t2 on t2.fproductdefid=t.fproductid where t2.faffirmed=1 and t.fparentid = '").append(fproductid).append("' and t.fproductid in ").append(productIDs).append(" group by t.fproductid) a");
	// List<HashMap<String,Object>> suitsblist=
	// DeliversDao.QueryBySql(oql.toString());
	// if(suitsblist.size()>0){
	// HashMap suitsbinfo = suitpdtlist.get(0);
	// stock=new BigDecimal(suitsbinfo.get("Qty").toString());
	// }
	// }
	//
	// //库存需要减去已导入EAS系统且没出库的要货数量;
	// sql =
	// "select ifnull(sum(d.famount),0) famount from t_ord_delivers d where d.fimportEAS = 1 and fouted = 0 and d.fcustomerid =:fcustomerid and d.fcusproductid=:fcusproductid ";
	// p=new params();
	// p.put("fcustomerid",vmicustomerid);
	// p.put("fcusproductid",fcusproductid);
	// List<HashMap<String,Object>> dlvlist= DeliversDao.QueryBySql(sql, p);
	// if(dlvlist.size()>0){
	// HashMap dlvinfo = dlvlist.get(0);
	// stock=stock.subtract(new BigDecimal(dlvinfo.get("famount").toString()));
	// }
	// }else{
	// throw new DJException("EAS系统没有对应的产品下单！");
	// }
	// }catch(Exception e){
	// throw new DJException(e.getMessage());
	// }
	//
	// int balanceqty = stock.divide(new BigDecimal(1), 0,
	// BigDecimal.ROUND_DOWN).intValue();
	// // String deliverNum = getFnumber(2,conn,stmt);
	// if(famount>balanceqty){//要货数量大于EAS系统库存数量按VMI安全库存下单数量生成VMI订单;
	// int orderamount = 0;
	// sql =
	// "select forderamount from t_pdt_vmiproductparam where fcustomerid =:fcustomerid and fproductid =:fproductid ";
	// // sql =
	// "select forderamount from t_pdt_vmiproductparam where fcustomerid = '"+vmicustomerid+"' and fproductid = '"+vmiproductid+"' ";
	// p=new params();
	// p.put("fcustomerid",vmicustomerid);
	// p.put("fproductid",fproductid);
	// List<HashMap<String,Object>> vmipdtparamlist =
	// VmipdtParamDao.QueryBySql(sql, p);
	// // List<HashMap<String,Object>> vmipdtparamlist =
	// VmipdtParamDao.QueryBySql(sql);
	// // if (!vmipdtparamlist.isEmpty()) {
	// // JSONArray js = JSONArray.fromObject(vmipdtparamlist);
	// // if (js.size()>0) {
	// // JSONObject vmipdtparam = js.getJSONObject(0);
	// // if(vmipdtparam.get("forderamount")!=null){
	// // orderamount = vmipdtparam.get("forderamount").toString();
	// // }else{
	// // throw new DJException("安全库存没有此客户对应的产品记录！");
	// // }
	// // }
	// if(vmipdtparamlist.size()>0){
	// HashMap vmipdtparam = vmipdtparamlist.get(0);
	// if(vmipdtparam.get("forderamount")!=null){
	// orderamount = new Integer(vmipdtparam.get("forderamount").toString());
	// }
	// else{
	// // throw new DJException("安全库存没有此客户对应的产品记录！");
	// orderamount = famount;
	// }
	// }else{
	// // throw new DJException("安全库存没有此客户对应的产品记录！");
	// orderamount = famount;
	// }
	// String fordernumber = saleOrderDao.getFnumber("t_ord_saleorder", "Z",4);
	// if(fnewtype!=2 && fnewtype!=4){
	// Saleorder soinfo = new Saleorder();
	// String orderEntryid = saleOrderDao.CreateUUid();
	// soinfo.setFid(orderEntryid);
	// soinfo.setForderid(orderid);
	// soinfo.setFcreatorid(userid);
	// soinfo.setFcreatetime(new Date());
	// soinfo.setFnumber(fordernumber);
	// soinfo.setFproductdefid(fproductid);
	// soinfo.setFentryProductType(0);
	//
	// pinfo.setFordernumber(fordernumber+"-1");
	// pinfo.setForderentryid(orderEntryid);
	//
	// soinfo.setFlastupdatetime(new Date());
	// soinfo.setFlastupdateuserid(userid);
	// soinfo.setFamount(orderamount);
	// soinfo.setFcustomerid(EAScustomerid);
	// soinfo.setFcustproduct(fcusproductid);
	// soinfo.setFarrivetime(farrivetime);
	// soinfo.setFbizdate(new Date());
	// soinfo.setFaudited(0);
	// soinfo.setFauditorid(null);
	// soinfo.setFaudittime(null);
	// soinfo.setFamountrate(0);
	//
	// soinfo.setFordertype(fnewtype);
	// soinfo.setFseq(1);
	// soinfo.setFimportEas(0);
	//
	// HashMap<String, Object> params = saleOrderDao.ExecSave(soinfo);
	// if (params.get("success") == Boolean.FALSE) {
	// throw new DJException("生成订单失败！");
	// }
	//
	// }else{
	// //套装
	// // 一次性获取所有级次的：套装+子产品
	// // 再按顺序加载即可
	//
	// List list = getAllProductSuit(fproductid);
	// HashMap subInfo = null;
	//
	// for (int i = 0; i < list.size(); i++)
	// {
	// subInfo = (HashMap) list.get(i);
	//
	// Saleorder soinfo = new Saleorder();
	// if(i==0){
	// soinfo.setFamount(orderamount);
	// soinfo.setFproductdefid(fproductid);
	// soinfo.setFsuitProductId(fproductid);
	// pinfo.setFordernumber(fordernumber+"-1");
	// pinfo.setForderentryid(subInfo.get("orderEntryID").toString());
	//
	// }else{
	//
	// soinfo.setFparentOrderEntryId(subInfo.get("ParentOrderEntryId").toString());
	// soinfo.setFamount(orderamount * new
	// Integer(subInfo.get("amountRate").toString()));
	// soinfo.setFproductdefid(subInfo.get("fid").toString());
	//
	// }
	//
	// soinfo.setFordertype(new Integer(subInfo.get("fnewtype").toString()));
	//
	// soinfo.setFentryProductType(new
	// Integer(subInfo.get("entryProductType").toString()));
	// soinfo.setFid(subInfo.get("orderEntryID").toString());
	// soinfo.setFseq((i+1));
	// soinfo.setFimportEas(0);
	// soinfo.setFamountrate(new Integer(subInfo.get("amountRate").toString()));
	// soinfo.setForderid(orderid);
	// soinfo.setFcreatorid(userid);
	// soinfo.setFcreatetime(new Date());
	// soinfo.setFlastupdatetime(new Date());
	// soinfo.setFlastupdateuserid(userid);
	// soinfo.setFnumber(fordernumber);
	// soinfo.setFcustomerid(EAScustomerid);
	// soinfo.setFcustproduct(fcusproductid);
	// soinfo.setFarrivetime(farrivetime);
	// soinfo.setFbizdate(new Date());
	// soinfo.setFaudited(0);
	// soinfo.setFauditorid(null);
	// soinfo.setFaudittime(null);
	//
	// HashMap<String, Object> params = saleOrderDao.ExecSave(soinfo);
	// if (params.get("success") == Boolean.FALSE) {
	// throw new DJException("生成订单失败！");
	// }
	// }
	//
	// }
	// pinfo.setFsaleorderid(orderid);
	// }else{
	// //要货数量小于VMI系统库存数量从订单中找有库存的订单反写到配送单;
	// sql =
	// "select s.fid orderentryid,s.forderid,s.fnumber from t_ord_saleorder s where s.fstockinqty > 0 and fcustomerid =:fcustomerid and fproductdefid =:fproductid ";
	// p=new params();
	// p.put("fcustomerid",vmicustomerid);
	// p.put("fproductid",fcusproductid);
	// List<HashMap<String,Object>> ordlist= DeliversDao.QueryBySql(sql, p);
	// if(ordlist.size()>0){
	// HashMap ordinfo = ordlist.get(0);
	// String saleorderid = "";
	// String orderentryid = "";
	// String ordernumber = "";
	// saleorderid = ordinfo.get("forderid").toString();
	// orderentryid = ordinfo.get("orderentryid").toString();
	// ordernumber = ordinfo.get("fnumber").toString();
	// sql = "SET SQL_SAFE_UPDATES=0 ";
	// saleOrderDao.ExecBySql(sql);
	// sql = "UPDATE `t_ord_delivers` SET `fsaleorderid`='" + saleorderid
	// +"',`fordernumber`='" + ordernumber +"',`forderentryid`='" + orderentryid
	// +"' WHERE `fid` = '" + fid +"'";
	// DeliversDao.ExecBySql(sql);
	// }
	//
	// }
	// }else{
	// throw new DJException("客户产品没有关联产品！");
	// }
	// // }
	// // reponse.getWriter().write(JsonUtil.result(true,"","",result));
	// } catch (Exception e) {
	// // conn.rollback();
	// //ServerContext.getOracleHelper().CloseConn(request);
	// throw new DJException(e.getMessage());
	// }
	//
	// HashMap<String, Object> params = DeliversDao.ExecSave(pinfo);
	// if (params.get("success") == Boolean.TRUE) {
	// result = JsonUtil.result(true,"下单成功!", "", "");
	// // conn.commit();
	// } else {
	// result = JsonUtil.result(false,"下单失败!", "", "");
	// }
	// } catch (Exception e) {
	// result = JsonUtil.result(false,e.getMessage(), "", "");;
	// }
	// reponse.setCharacterEncoding("utf-8");
	// reponse.getWriter().write(result);
	// running = false;
	// return null;
	// }

	// /*
	// * 根据VMI平台库存下单
	// */
	// @RequestMapping(value = "/VMIorder")
	// public String VMIorder(HttpServletRequest request,
	// HttpServletResponse reponse) throws Exception {
	// // if(running){
	// // return null;
	// // }
	// // SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	// // String fnumber="";
	// // String result="";
	// // String sql="";
	// // String userid = ((Useronline)
	// request.getSession().getAttribute("Useronline")).getFuserid();
	// // String fproductid="";
	// // Integer fnewtype=0;
	// // String fcustomerid="";
	// // String fcusproductid="";
	// // Integer famount=0;
	// // Date farrivetime=null;
	// // String Deliverid="";
	// // try {
	// //
	// sql=" update t_ord_delivers t set fordernumber='客户产品没有关联产品' where t.fordered = 0 and not exists (select 1 from t_sys_custrelationproduct t1 where t1.fcustid=t.fcusproductid) ";
	// // DeliversDao.ExecBySql(sql);
	// //
	// sql="select t.fcustomerid,t.fcusproductid,max(t.farrivetime) as farrivetime,sum(t.famount) as  famount,pdef.fid as productid,pdef.fassemble,pdef.fnewtype from t_ord_delivers t,t_sys_custrelationproduct t1,t_pdt_productdef pdef where t.fordered = 0 and t1.fproductdefid=pdef.fid  and t1.fcustid=t.fcusproductid ";
	// //
	// sql=sql+" and not exists( select 1 from t_pdt_vmiproductparam t2 where t1.fproductdefid=t2.fproductid)  group by t.fcustomerid,t.fcusproductid,pdef.fid,pdef.fassemble,pdef.fnewtype";
	// // List<HashMap<String,Object>> data= DeliversDao.QueryBySql(sql);
	// // for (int i=0;i<data.size();i++) //生成合并计划
	// // {
	// // fproductid=data.get(i).get("productid").toString();
	// // fnewtype=Integer.parseInt(data.get(i).get("fnewtype").toString());
	// // fcustomerid=data.get(i).get("productid").toString();
	// // fcusproductid=data.get(i).get("productid").toString();
	// // famount=Integer.parseInt(data.get(i).get("famount").toString());
	// // farrivetime=sdf.parse(data.get(i).get("farrivetime").toString());
	// // Deliverid=data.get(i).get("productid").toString();
	// // ProductPlan productplaninfo=new ProductPlan();
	// // productplaninfo.setFalloted(0);
	// // productplaninfo.setFamount(famount);
	// // productplaninfo.setFarrivetime(farrivetime);
	// // productplaninfo.setFcreatetime(new Date());
	// // productplaninfo.setFcreatorid(userid);
	// // productplaninfo.setFcustomerid(fcustomerid);
	// // productplaninfo.setFcustproduct(fcusproductid);
	// // productplaninfo.setFproductid(fproductid);
	// // productplaninfo.setFid(DeliversDao.CreateUUid());
	// // fnumber=DeliversDao.getFnumber("t_ord_productplan", "P",4);
	// // productplaninfo.setFnumber(fnumber);
	// // DeliversDao.saveOrUpdate(productplaninfo);
	// // //反写要货申请
	// //
	// //
	// //
	// //
	// //
	// //
	// //createMergerOrder(fproductid,fnewtype,userid,fcustomerid,fcusproductid,famount,farrivetime,Deliverid);
	// // }
	// //
	// sql="select t.fcustomerid,t.fcusproductid,t.farrivetime,t.famount,pdef.fid as productid,pdef.fassemble,pdef.fnewtype from t_ord_delivers t,t_sys_custrelationproduct t1,t_pdt_productdef pdef where t.fordered = 0 and t1.fproductdefid=pdef.fid  and t1.fcustid=t.fcusproductid ";
	// //
	// sql=sql+" and exists( select 1 from t_pdt_vmiproductparam t2 where t1.fproductdefid=t2.fproductid)";
	// // data= DeliversDao.QueryBySql(sql);
	// // for (int i=0;i<data.size();i++) //有安全库存的订单
	// // {
	// //
	// // }
	// //
	// //
	// //
	// //
	// // }
	// // catch(Exception e)
	// // {
	// // result = JsonUtil.result(false,"下单失败! '" + e.getMessage()+"'", "",
	// "");
	// // }
	// // reponse.setCharacterEncoding("utf-8");
	// // reponse.getWriter().write(result);
	// // running = false;
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	// if(running){
	// return null;
	// }
	// running = true;
	// String result = "";
	// Deliverorder pinfo = null;
	// try {
	// String userid = ((Useronline)
	// request.getSession().getAttribute("Useronline")).getFuserid();
	// String fid = "";
	//
	// // String sql = "select * from t_ord_delivers where fordered = 0 ";
	// String sql =
	// "select * from t_ord_delivers where fordered = 0 order by fcusproductid ";
	// List<HashMap<String,Object>> deliverslist =
	// DeliverorderDao.QueryBySql(sql);
	// HashMap map = new HashMap();
	// for(int i=0;i<deliverslist.size();i++){
	// HashMap deliversinfo = deliverslist.get(i);
	// fid = deliversinfo.get("fid").toString();
	// if (fid != null && !"".equals(fid)) {
	// pinfo = DeliverorderDao.Query(fid);
	// }
	// try {
	// String fnumber = pinfo.getFnumber();
	// String fcusproductid = pinfo.getFcusproductid();
	// String flinkman = pinfo.getFlinkman();
	// String flinkphone = pinfo.getFlinkphone();
	// String fcustomerid = pinfo.getFcustomerid();
	// int famount = pinfo.getFamount();
	// // DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
	// // Date farrivetime = pinfo.getFarrivetime();
	// //送达时间更改为当前时间+两天;
	// Calendar arriveDate = Calendar.getInstance();
	// arriveDate.setTime(new Date());
	// arriveDate.add(Calendar.DATE, 2);
	// Date farrivetime = arriveDate.getTime();
	// String faddress = pinfo.getFaddress();
	//
	// sql =
	// "select fid from t_pdt_productdef where fid in (select fproductdefid from t_sys_custrelationproduct where fcustid =:fcustid )";
	// params p=new params();
	// p.put("fcustid",fcusproductid);
	// List<HashMap<String,Object>> data= DeliverorderDao.QueryBySql(sql, p);
	// if (data.size()>0) //客户产品只有一个对应的产品ID;
	// {
	// BigDecimal stock = new BigDecimal(0);
	// HashMap o = data.get(0);
	// String fproductid = "";
	// if(o.get("fid")!=null){
	// fproductid = o.get("fid").toString();
	// }
	// int fnewtype = 0;
	// String isassemble = "0";
	// // String EAScustomerid = "";
	// ResultSet rs = null;
	// sql =
	// "select d.fid,d.fassemble,d.fnewtype,d.fcustomerid,d.feffect from t_pdt_productdef d where fid = '"+fproductid+"' ";
	// List<HashMap<String,Object>> pdtlist= DeliverorderDao.QueryBySql(sql);
	// if(pdtlist.size()>0){
	// HashMap pdtinfo = pdtlist.get(0);
	// if(pdtinfo.get("fnewtype")!=null){
	// fnewtype = new Integer(pdtinfo.get("fnewtype").toString());
	// }
	//
	// sql =
	// "select forderamount from t_pdt_vmiproductparam where fcustomerid = '"+fcustomerid+"' and fproductid = '"+fproductid+"' ";
	// List<HashMap<String,Object>> vmipdtparamlist =
	// VmipdtParamDao.QueryBySql(sql);
	// if(vmipdtparamlist.size()>0) //有安全库存则计算产品库存;
	// {
	// //非安全库存转安全库存时提交上次非安全库存的订单
	// if(map.size()>0)
	// {
	// createMergerOrder(map.get("fproductid").toString(),new
	// Integer(map.get("fnewtype").toString())
	// ,userid,map.get("fcustomerid").toString(),map.get("fcusproductid").toString(),new
	// Integer(map.get("famount").toString()),(Date)map.get("farrivetime"),map.get("Deliverid").toString());
	// map.clear();
	// }
	//
	// HashMap vmipdtparam = vmipdtparamlist.get(0);
	//
	// //不包含套装子件的普通产品订单库存数量
	// if((fnewtype!=2 && fnewtype!=4)){
	// sql =
	// "select sum(ifnull(b.fbalanceqty,0)) balanceqty from t_inv_storebalance b left join (select forderid,fordertype from t_ord_saleorder where fseq=1 ) s on s.forderid=b.fsaleorderid where ifnull(s.fordertype,3)<>2 and ifnull(s.fordertype,3)<>4 and b.fproductid='"+fproductid+"' ";
	// List<HashMap<String,Object>> sblist= DeliverorderDao.QueryBySql(sql);
	// if(sblist.size()>0){
	// HashMap sbinfo = sblist.get(0);
	// stock=new BigDecimal(sbinfo.get("balanceqty").toString());
	// }
	//
	// //在生产数量
	// sql =
	// "select ifnull(sum(case when s.famount<ifnull(s.fstockinqty,0) then 0 else s.famount-ifnull(s.fstockinqty,0) end),0) unAmount from t_ord_saleorder s where faffirmed=1 and s.fcustomerid = :fcustomerid and s.fproductdefid = :fproductid ";
	// p=new params();
	// p.put("fcustomerid",fcustomerid);
	// p.put("fproductid",fproductid);
	// // @SuppressWarnings("unchecked")
	// List<HashMap<String,Object>> unsblist= DeliverorderDao.QueryBySql(sql,
	// p);
	// if(unsblist.size()>0){
	// HashMap unsbinfo = unsblist.get(0);
	// stock=stock.add(new BigDecimal(unsbinfo.get("unAmount").toString()));
	// }
	//
	// }else if(isassemble.equals("1")){
	// sql =
	// "select sum(ifnull(b.fbalanceqty,0)) balanceqty  from t_inv_storebalance b left join (select forderid,fordertype,fid from t_ord_saleorder where fseq=1 ) s on s.forderid=b.fsaleorderid where ifnull(s.fid,b.forderentryid) = b.forderentryid and b.fproductid= :fproductid ";
	// p=new params();
	// p.put("fproductid",fproductid);
	// List<HashMap<String,Object>> sblist= DeliverorderDao.QueryBySql(sql, p);
	// if(sblist.size()>0){
	// HashMap sbinfo = sblist.get(0);
	// stock=new BigDecimal(sbinfo.get("balanceqty").toString());
	// }
	//
	// //在生产数量
	// sql =
	// "select ifnull(sum(case when s.famount<ifnull(s.fstockinqty,0) then 0 else s.famount-ifnull(s.fstockinqty,0) end),0) unAmount from t_ord_saleorder s where faffirmed=1 and s.fcustomerid = :fcustomerid and s.fproductdefid = :fproductid ";
	// p=new params();
	// p.put("fcustomerid",fcustomerid);
	// p.put("fproductid",fproductid);
	// List<HashMap<String,Object>> unsblist= DeliverorderDao.QueryBySql(sql,
	// p);
	// if(unsblist.size()>0){
	// HashMap unsbinfo = unsblist.get(0);
	// stock=stock.add(new BigDecimal(unsbinfo.get("unAmount").toString()));
	// }
	//
	// }else{
	// //套装
	// String productIDs = "";
	// sql =
	// "select d.fid,d.fcombination from T_PDT_ProductDefProducts pd left join t_pdt_productdef d on d.fid=pd.fproductid where fparentid = :fproductid ";
	// p=new params();
	// p.put("fproductid",fproductid);
	// List<HashMap<String,Object>> suitpdtlist= DeliverorderDao.QueryBySql(sql,
	// p);
	// for(int j=0;j<suitpdtlist.size();j++){
	// HashMap suitpdtinfo = suitpdtlist.get(j);
	// if(suitpdtinfo.get("fcombination")!=null &&
	// suitpdtinfo.get("fcombination").toString().equals("0")){
	// if(productIDs.length()>0){
	// productIDs = productIDs + "','" + rs.getString("fid");
	// }else{
	// productIDs = "('" + rs.getString("fid");
	// }
	// }
	// }
	// if(productIDs.length()==0){
	// productIDs = "('" ;
	// }
	// productIDs = productIDs + "')";
	//
	//
	// //套装库存总量
	// StringBuffer oql = new
	// StringBuffer("select min(ifnull(qty,0)) Qty from (select t.fproductid,ifnull(sum(t1.fbalanceqty/t.famount),0)+sum((case when t2.famount<ifnull(t2.fstockinqty,0) then 0 else t2.famount-ifnull(t2.fstockinqty,0) end)/t.famount) qty from T_PDT_ProductDefProducts t ");
	// oql.append("left join t_inv_storebalance t1 on t.fproductid=t1.fproductid left join t_ord_saleorder t2 on t2.fproductdefid=t.fproductid where t2.faffirmed=1 and t.fparentid = '").append(fproductid).append("' and t.fproductid in ").append(productIDs).append(" group by t.fproductid) a");
	// List<HashMap<String,Object>> suitsblist=
	// DeliverorderDao.QueryBySql(oql.toString());
	// if(suitsblist.size()>0){
	// HashMap suitsbinfo = suitpdtlist.get(0);
	// stock=new BigDecimal(suitsbinfo.get("Qty").toString());
	// }
	// }
	//
	// //库存需要减去已导入EAS系统且没出库的要货数量;
	// sql =
	// "select sum(ifnull(d.famount,0)) famount from t_ord_delivers d where ifnull(fouted,0) = 0 and d.fcustomerid =:fcustomerid and d.fcusproductid=:fcusproductid ";
	// p=new params();
	// p.put("fcustomerid",fcustomerid);
	// p.put("fcusproductid",fcusproductid);
	// List<HashMap<String,Object>> dlvlist= DeliverorderDao.QueryBySql(sql, p);
	// if(dlvlist.size()>0){
	// HashMap dlvinfo = dlvlist.get(0);
	// stock=stock.subtract(new BigDecimal(dlvinfo.get("famount").toString()));
	// }
	//
	// int balanceqty = stock.divide(new BigDecimal(1), 0,
	// BigDecimal.ROUND_DOWN).intValue();
	// if(balanceqty>=famount){
	// //有库存数量则从有库存的订单中取最早订单反写到配送单;
	// sql =
	// "select s.fid orderentryid,s.forderid,s.fnumber from t_ord_productplan s where s.fstockinqty > 0 and fcustomerid ='"+fcustomerid+"' and fproductdefid ='"+fproductid+"' order by s.fcreatetime";
	// List<HashMap<String,Object>> ordlist= DeliverorderDao.QueryBySql(sql);
	// if(ordlist.size()>0){
	// HashMap ordinfo = ordlist.get(0);
	// pinfo.setFsaleorderid(ordinfo.get("forderid").toString());
	// pinfo.setForderentryid(ordinfo.get("orderentryid").toString());
	// pinfo.setFordernumber(ordinfo.get("fnumber").toString());
	// pinfo.setFordermanid(userid);
	// pinfo.setFordered(1);
	// pinfo.setFordertime(new Date());
	// HashMap<String, Object> params = DeliverorderDao.ExecSave(pinfo);
	//
	// // DateFormat f = new SimpleDateFormat("yyyy-MM-dd");
	// // String pinfosql =
	// "update t_ord_delivers set fordered=1,fordermanid='"+userid+"',fordertime='"+f.format(new
	// Date())+"',fsaleorderid='"+ordinfo.get("forderid").toString()+"'," +
	// //
	// "fordernumber='"+ordinfo.get("fnumber").toString()+"',forderentryid='"+ordinfo.get("orderentryid").toString()+"' where fid = '"
	// + fid +"'";
	// // DeliversDao.ExecBySql(pinfosql);
	// }else{
	// pinfo.setFordernumber("有订单在生产！");
	// DeliverorderDao.ExecSave(pinfo);
	// //
	// DeliverorderDao.ExecBySql("update t_ord_delivers set fordernumber='"+"未关联产品！"+"' where fid = '"+fid+"'");
	// continue;
	// }
	// }else{
	// pinfo.setFordernumber("没有库存下单");
	// DeliverorderDao.ExecSave(pinfo);
	// //
	// DeliversDao.ExecBySql("update t_ord_delivers set fordernumber='"+"未关联产品！"+"' where fid = '"+fid+"'");
	// continue;
	// }
	// }else //安全库存 不存在则直接新建订单;
	// {
	// //合并没有安全库存订单;
	// int tempAmt = 0 ;
	// String Deliverid = "";
	//
	// if(map.size()==0){
	// map.put("famount", famount);
	// map.put("fproductid", fproductid);
	// Deliverid = "'"+fid+"'";
	// map.put("Deliverid", Deliverid);
	// map.put("fnewtype", fnewtype);
	// map.put("fcustomerid", fcustomerid);
	// map.put("fcusproductid", fcusproductid);
	// map.put("farrivetime", farrivetime);
	// }else if(map.get("fproductid").equals(fproductid)){
	// tempAmt = new Integer(map.get("famount").toString());
	// map.put("famount", tempAmt+famount);
	// Deliverid = map.get("Deliverid").toString() + ",'"+fid+"'";
	// map.put("Deliverid", Deliverid);
	// }else{
	// createMergerOrder(map.get("fproductid").toString(),(Integer)map.get("fnewtype"),userid,map.get("fcustomerid").toString(),map.get("fcusproductid").toString(),(Integer)map.get("famount"),(Date)map.get("farrivetime"),map.get("Deliverid").toString());
	//
	// map.put("famount", famount);
	// map.put("fproductid", fproductid);
	// Deliverid = "'"+fid+"'";
	// map.put("Deliverid", Deliverid);
	// map.put("fnewtype", fnewtype);
	// map.put("fcustomerid", fcustomerid);
	// map.put("fcusproductid", fcusproductid);
	// map.put("farrivetime", farrivetime);
	// }
	//
	// if(i==(deliverslist.size()-1) )
	// {
	// createMergerOrder(fproductid,fnewtype,userid,fcustomerid,fcusproductid,(Integer)map.get("famount"),farrivetime,map.get("Deliverid").toString());
	// }
	// continue;
	// }
	// }else{
	// // pinfo.setFordernumber("未关联产品！");
	// // DeliverorderDao.ExecSave(pinfo);
	// DeliverorderDao.ExecBySql("update t_ord_delivers set fordernumber='"+"未关联产品！"+"' where fid = '"+fid+"'");
	// continue;
	// }
	// }else{
	// // pinfo.setFordernumber("未关联产品！");
	// DeliverorderDao.ExecBySql("update t_ord_delivers set fordernumber='"+"未关联产品！"+"' where fid = '"+fid+"'");
	// continue;
	// }
	// } catch (Exception e) {
	// throw new DJException(e.getMessage());
	// }
	// // pinfo.setFordermanid(userid);
	// // pinfo.setFordered(1);
	// // pinfo.setFordertime(new Date());
	// // HashMap<String, Object> params = DeliversDao.ExecSave(pinfo);
	// }
	// result = JsonUtil.result(true,"下单成功!", "", "");
	// } catch (Exception e) {
	// result = JsonUtil.result(false,"下单失败! '" + e.getMessage()+"'", "", "");;
	// }
	// reponse.setCharacterEncoding("utf-8");
	// reponse.getWriter().write(result);
	// running = false;
	// return null;
	// }

	private void createMergerOrder(String fproductid, int fnewtype,
			String userid, String fcustomerid, String fcusproductid,
			Integer famount, Date farrivetime, String deliverid)
			throws Exception {
		// TODO Auto-generated method stub
		String orderid = saleOrderDao.CreateUUid();
		String fordernumber = saleOrderDao
				.getFnumber("t_ord_saleorder", "Z", 4);
		if (fnewtype != 2 && fnewtype != 4) {
			Saleorder soinfo = new Saleorder();
			String orderEntryid = saleOrderDao.CreateUUid();
			soinfo.setFid(orderEntryid);
			soinfo.setForderid(orderid);
			soinfo.setFcreatorid(userid);
			soinfo.setFcreatetime(new Date());
			soinfo.setFnumber(fordernumber);
			soinfo.setFproductdefid(fproductid);
			soinfo.setFentryProductType(0);

			// pinfo.setFordernumber(fordernumber+"-1");
			// pinfo.setForderentryid(orderEntryid);

			soinfo.setFlastupdatetime(new Date());
			soinfo.setFlastupdateuserid(userid);
			soinfo.setFamount(famount);
			soinfo.setFcustomerid(fcustomerid);
			soinfo.setFcustproduct(fcusproductid);
			soinfo.setFarrivetime(farrivetime);
			soinfo.setFbizdate(new Date());
			soinfo.setFaudited(0);
			soinfo.setFauditorid(null);
			soinfo.setFaudittime(null);
			soinfo.setFamountrate(0);

			soinfo.setFassemble(0);
			soinfo.setFiscombinecrosssubs(0);

			soinfo.setFordertype(fnewtype);
			soinfo.setFseq(1);
			soinfo.setFimportEas(0);

			HashMap<String, Object> params = saleOrderDao.ExecSave(soinfo);

			String ordersql = "SET SQL_SAFE_UPDATES=0 ";
			DeliverorderDao.ExecBySql(ordersql);
			DateFormat f = new SimpleDateFormat("yyyy-MM-dd");
			ordersql = "update t_ord_delivers set fordered=1,fordermanid='"
					+ userid + "',fordertime='" + f.format(new Date())
					+ "',fsaleorderid='" + orderid + "'," + "fordernumber='"
					+ (fordernumber + "-1") + "',forderentryid='"
					+ orderEntryid + "' where fid in (" + deliverid + ")";
			DeliverorderDao.ExecBySql(ordersql);

			if (params.get("success") == Boolean.FALSE) {
				throw new DJException("生成订单失败！");
			}

		} else {
			// 套装
			// 一次性获取所有级次的：套装+子产品
			// 再按顺序加载即可

			List list = getAllProductSuit(fproductid);
			HashMap subInfo = null;
			String orderentryid = "";
			for (int k = 0; k < list.size(); k++) {
				subInfo = (HashMap) list.get(k);

				Saleorder soinfo = new Saleorder();

				if (subInfo.get("FAssemble") != null
						&& subInfo.get("FAssemble").toString().equals("1")) {
					soinfo.setFassemble(1);
				} else {
					soinfo.setFassemble(0);
				}

				if (subInfo.get("fiscombinecrosssubs") != null
						&& subInfo.get("fiscombinecrosssubs").toString()
								.equals("1")) {
					soinfo.setFiscombinecrosssubs(1);
				} else {
					soinfo.setFiscombinecrosssubs(0);
				}

				if (k == 0) {
					soinfo.setFamount(famount);
					soinfo.setFproductdefid(fproductid);
					soinfo.setFsuitProductId(fproductid);
					// pinfo.setFordernumber(fordernumber+"-1");
					// pinfo.setForderentryid(subInfo.get("orderEntryID").toString());
					orderentryid = subInfo.get("orderEntryID").toString();

				} else {

					soinfo.setFparentOrderEntryId(subInfo.get(
							"ParentOrderEntryId").toString());
					soinfo.setFamount(famount
							* new Integer(subInfo.get("amountRate").toString()));
					soinfo.setFproductdefid(subInfo.get("fid").toString());

				}

				soinfo.setFordertype(new Integer(subInfo.get("fnewtype")
						.toString()));

				soinfo.setFentryProductType(new Integer(subInfo.get(
						"entryProductType").toString()));
				soinfo.setFid(subInfo.get("orderEntryID").toString());
				soinfo.setFseq((k + 1));
				soinfo.setFimportEas(0);
				soinfo.setFamountrate(new Integer(subInfo.get("amountRate")
						.toString()));
				soinfo.setForderid(orderid);
				soinfo.setFcreatorid(userid);
				soinfo.setFcreatetime(new Date());
				soinfo.setFlastupdatetime(new Date());
				soinfo.setFlastupdateuserid(userid);
				soinfo.setFnumber(fordernumber);
				soinfo.setFcustomerid(fcustomerid);
				soinfo.setFcustproduct(fcusproductid);
				soinfo.setFarrivetime(farrivetime);
				soinfo.setFbizdate(new Date());
				soinfo.setFaudited(0);
				soinfo.setFauditorid(null);
				soinfo.setFaudittime(null);

				HashMap<String, Object> params = saleOrderDao.ExecSave(soinfo);
				if (params.get("success") == Boolean.FALSE) {
					throw new DJException("生成订单失败！");
				}
			}

			String ordersql = "SET SQL_SAFE_UPDATES=0 ";
			DeliverorderDao.ExecBySql(ordersql);
			DateFormat f = new SimpleDateFormat("yyyy-MM-dd");
			ordersql = "update t_ord_delivers set fordered=1,fordermanid='"
					+ userid + "',fordertime='" + f.format(new Date())
					+ "',fsaleorderid='" + orderid + "'," + "fordernumber='"
					+ (fordernumber + "-1") + "',forderentryid='"
					+ orderentryid + "' where fid in (" + deliverid + ")";
			DeliverorderDao.ExecBySql(ordersql);

		}
		// pinfo.setFsaleorderid(orderid);
	}

	/**
	 * 获取多级套装+子件，并且对“分录的产品类型”赋值
	 */
	protected List getAllProductSuit(String fproductid) throws Exception {
		// ProductDefCollection productCols = new ProductDefCollection();
		List list = new ArrayList();
		// StringBuffer oql = new
		// StringBuffer("select *,Products.*,Products.product.* where id ='").append(id).append("'");
		// ProductDefInfo productInfo = getProductDefInfo(oql.toString());
		// stmt =
		// conn.prepareStatement("select fnewtype,ftype,FCombination,FAssemble from t_pdt_productdef where fid = '"+fproductid+"'");
		// ResultSet productrs = stmt.executeQuery();
		String sql = "select fnewtype,FCombination,FAssemble from t_pdt_productdef where fid = '"
				+ fproductid + "'";
		List<HashMap<String, Object>> pdlist = DeliverorderDao.QueryBySql(sql);
		if (pdlist.size() > 0) {
			HashMap productrs = pdlist.get(0);
			HashMap productInfo = new HashMap();
			productInfo.put("fid", fproductid);
			productInfo.put("amountRate", new Integer(1));
			productInfo.put("entryProductType", "1");
			productInfo.put("ParentOrderEntryId", null);
			if (productrs.get("fnewtype") != null) {
				productInfo.put("fnewtype", productrs.get("fnewtype")
						.toString());
			} else {
				productInfo.put("fnewtype", "0");
			}

			if (productrs.get("FCombination") != null) {
				productInfo.put("FCombination", productrs.get("FCombination")
						.toString());
			} else {
				productInfo.put("FCombination", "0");
			}

			productInfo.put("FAssemble", productrs.get("FAssemble").toString());
			productInfo.put("fiscombinecrosssubs", new Integer(0));
			getProductSuit(productInfo, list, null);
		}

		return list;
	}

	private void getProductSuit(HashMap productInfo, List list,
			String parentEntryId) throws Exception {
		boolean isSuit = (productInfo.get("fnewtype").equals("2") || productInfo
				.get("fnewtype").equals("4"));
		String orderEntryID = saleOrderDao.CreateUUid();
		productInfo.put("orderEntryID", orderEntryID);
		if (!isSuit) {
			// 如果非套装，自己的分录ID，父分录Id取传入的参数，直接放入Collection
			// BOSUuid orderEntryID = BOSUuid.create(new
			// SaleOrderEntryInfo().getBOSType());
			productInfo.put("ParentOrderEntryId", parentEntryId);
			list.add(productInfo);
		} else {
			// 如果是套装，自己的分录Id，父分录Id，总套为null，非总套取参数，先把自己放入Collection，再循环递归调用自己的子件

			// 如果不是首次进入递归，套装的“分录的产品类型”== 非总套
			if (productInfo.get("entryProductType").toString().equals("1")) {
				// nothing
			} else {
				productInfo.put("entryProductType", "2");
				productInfo.put("ParentOrderEntryId", parentEntryId);
			}

			list.add(productInfo);

			// 子件“分录的产品类型”
			String subEntryProductType = "";
			boolean isassemble = false;
			if (productInfo.get("FCombination") != null
					&& productInfo.get("FCombination").toString().equals("1")) {
				// preSuitProductType = 1;
				subEntryProductType = "7"; // 合并下料子件
			} else if (productInfo.get("FAssemble") != null
					&& productInfo.get("FAssemble").toString().equals("1")) {
				// preSuitProductType = 2;
				subEntryProductType = "6"; // 组装套装子件
				isassemble = true;
			} else {
				// preSuitProductType = 0;
				subEntryProductType = "5"; // 普通套装子件
			}

			// ProductDefProductCollection subCols = productInfo.getProducts();
			// stmt =
			// conn.prepareStatement("select FAmount,FProductID from T_PDT_ProductDefProducts where Fparentid = '"+productInfo.get("fid")+"'");
			// ResultSet productrs = stmt.executeQuery();
			String sql = "select FAmount,FProductID from T_PDT_ProductDefProducts where Fparentid = '"
					+ productInfo.get("fid") + "'";
			List<HashMap<String, Object>> pdlist = DeliverorderDao
					.QueryBySql(sql);
			for (int i = 0; i < pdlist.size(); i++) {
				HashMap productrs = pdlist.get(i);

				// stmt =
				// conn.prepareStatement("select fid,fnewtype,ftype,FCombination,FAssemble from t_pdt_productdef where fid = '"+productrs.get("FProductID")+"'");
				// ResultSet subproductrs = stmt.executeQuery();
				sql = "select fid,fnewtype,FCombination,FAssemble,fiscombinecrosssubs from t_pdt_productdef where fid = '"
						+ productrs.get("FProductID") + "'";
				List<HashMap<String, Object>> pdslist = DeliverorderDao
						.QueryBySql(sql);
				if (pdslist.size() > 0) {
					HashMap subproductrs = pdslist.get(0);
					HashMap subInfo = new HashMap();
					subInfo.put("fid", subproductrs.get("fid"));
					subInfo.put("amountRate",
							new Integer(productrs.get("FAmount").toString())
									* new Integer(productInfo.get("amountRate")
											.toString()));
					if (subproductrs.get("fnewtype") != null) {
						subInfo.put("fnewtype", subproductrs.get("fnewtype")
								.toString());
					} else {
						subInfo.put("fnewtype", "0");
					}

					if (subproductrs.get("FCombination") != null) {
						subInfo.put("FCombination",
								subproductrs.get("FCombination").toString());
					} else {
						subInfo.put("FCombination", "0");
					}

					subInfo.put("FAssemble", subproductrs.get("FAssemble")
							.toString());
					subInfo.put("fiscombinecrosssubs",
							subproductrs.get("fiscombinecrosssubs").toString());
					// 子件的“分录的产品类型”
					subInfo.put("entryProductType", subEntryProductType);
					if (isassemble) {
						subInfo.put("FisassembleSuitSub", Boolean.TRUE);
					} else {
						subInfo.put("FisassembleSuitSub", Boolean.FALSE);
					}

					getProductSuit(subInfo, list, orderEntryID);
				}

			}
			// while (productrs.next())
			// {
			// stmt =
			// conn.prepareStatement("select fid,fnewtype,ftype,FCombination,FAssemble from t_pdt_productdef where fid = '"+productrs.getString("FProductID")+"'");
			// ResultSet subproductrs = stmt.executeQuery();
			// if (subproductrs.next())
			// {
			// HashMap subInfo = new HashMap();
			// subInfo.put("fid",subproductrs.getString("fid"));
			// subInfo.put("amountRate",productrs.getInt("FAmount") * new
			// Integer(productInfo.get("amountRate").toString()));
			// subInfo.put("fnewtype", subproductrs.getString("fnewtype"));
			// subInfo.put("ftype", subproductrs.getString("ftype"));
			// subInfo.put("FCombination",
			// subproductrs.getString("FCombination"));
			// subInfo.put("FAssemble", subproductrs.getString("FAssemble"));
			// //子件的“分录的产品类型”
			// subInfo.put("entryProductType",subEntryProductType);
			// if(isassemble){
			// subInfo.put("FisassembleSuitSub", Boolean.TRUE);
			// }else{
			// subInfo.put("FisassembleSuitSub", Boolean.FALSE);
			// }
			//
			// getProductSuit(conn,stmt,subInfo,list,orderEntryID);
			// }
			// }

		}
	}
	
	
	@RequestMapping("/GetFoutedDeliverorderList")
	public String GetFoutedDeliverorderList(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		request.setAttribute("djsort","d.fcreatetime desc");
		//		String sql = "select d.fordermanid,u3.fname forderman,d.fordertime,d.fordered,u1.fname as fcreator,u2.fname as flastupdater,c.fname as fcustname,cpdt.fname cutpdtname,d.fid,d.fcreatorid,d.fcreatetime,d.fupdateuserid,d.fupdatetime,d.fnumber,d.fcustomerid,d.fcusproductid,date_format(d.farrivetime,'%Y-%m-%d %T') farrivetime,d.flinkman,d.flinkphone,d.famount,d.faddress,d.fdescription,fsaleorderid,ifnull(fordernumber,'') fordernumber,forderentryid,d.fimportEAS,d.fimportEASuserid,d.fimportEAStime,d.fouted,d.foutorid,d.fouttime from t_ord_deliverorder d left join t_sys_user u1 on u1.fid=d.fcreatorid left join t_sys_user u2 on u2.fid=d.fupdateuserid left join t_sys_user u3 on u3.fid=d.fordermanid left join t_bd_customer c on c.fid=d.fcustomerid left join t_bd_custproduct cpdt on cpdt.fid=d.fcusproductid where d.fouted=1 ";
		String sql="select c.fname as fcustname,cpdt.fname cutpdtname,d.fid,d.fnumber,date_format(d.farrivetime,'%Y-%m-%d %T') farrivetime,d.flinkman,d.flinkphone,d.famount,d.faddress,d.fdescription,fsaleorderid,ifnull(fordernumber,'') fordernumber,d.fouted from t_ord_deliverorder d left join t_bd_customer c on c.fid=d.fcustomerid left join t_bd_custproduct cpdt on cpdt.fid=d.fcusproductid where d.fouted=1 ";
//		sql=sql +saleOrderDao.QueryFilterByUser(request, "d.fcustomerid", "d.fsupplierid");
		sql=sql +saleOrderDao.QueryFilterByUser(request, "d.fcustomerid", null);

		ListResult result;
		reponse.setCharacterEncoding("utf-8");
		try {
			result = DeliverorderDao.QueryFilterList(sql, request);
			reponse.getWriter().write(JsonUtil.result(true, "", result));
		} catch (DJException e) {
			reponse.getWriter().write(
					JsonUtil.result(false, e.getMessage(), "", ""));
		}

		return null;
	}

	/**
	 * 新出库
	 * @param request
	 * @param reponse
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/updateAndAddEXWarehouse")
	public String updateAndAddEXWarehouse(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		reponse.setCharacterEncoding("utf-8");
		
		boolean r = false;
		
		
		try {
			r = outEXWarehouse(request);
			reponse.getWriter().write(JsonUtil.result(r, "", "", ""));
		} catch (Exception e) {
			// TODO: handle exception
			r = false;
			reponse.getWriter().write(JsonUtil.result(r, e.getMessage(), "", ""));
		}
		
		return null; 
	}
	
	/**
	 * 新出库
	 * @author Administrator ,zjz
	 * @param request
	 * @return
	 */
	private boolean outEXWarehouse(HttpServletRequest request) {
		// TODO Auto-generated method stub
		boolean r = false;
		
		EXWarehouseItem[] eXWarehouseItems = buildEXWarehouseItems(request);
		String userID = ((Useronline) request.getSession().getAttribute(
				"Useronline")).getFuserid();
		String ftraitid=request.getParameter("ftraitid")==null?"":request.getParameter("ftraitid");
		
		int i = 0;
		for (EXWarehouseItem exWarehouseItem : eXWarehouseItems) {
			i++;
			String fid = exWarehouseItem.getFid();
			
			Deliverorder d = DeliverorderDao.Query(fid);
			String planId = d.getFplanid();
			
			String warehouse = exWarehouseItem.getWarehouse();
			String storageLocation = exWarehouseItem.getStorageLocation();
			int count = exWarehouseItem.getCount();
			
//			outWarehouseDao.saveOutWarehouse(planId, count, warehouse, storageLocation, userID,ftraitid, true);
//			
			/**
			 * 全部完成才true
			 */
			if (i == eXWarehouseItems.length) {
				r = true;
			}
			
		}
		
		return r;
	}

	private EXWarehouseItem[] buildEXWarehouseItems(HttpServletRequest request) {
		// TODO Auto-generated method stub
		JSONArray jsa = DataUtil.getJsonArrayByS(request.getParameter("data"));
		
		EXWarehouseItem[] eXWarehouseItems = new EXWarehouseItem[jsa.size()];
		
		for (int i = 0; i < jsa.size(); i++) {
			
			JSONObject jso = jsa.getJSONObject(i);
			String fid = jso.getString("fid");
			String warehouse = jso.getString("warehouse");
			String storageLocation = jso.getString("storageLocation");
			int count = jso.getInt("count");
			
			EXWarehouseItem item = new EXWarehouseItem(fid, warehouse, storageLocation, count);
			eXWarehouseItems[i] = item;
		}
		
		return eXWarehouseItems;
	}
	
	/**
	 * 出库订单bo
	 * @author Administrator
	 *
	 */
	private class EXWarehouseItem {
		
		private String fid;
		private String warehouse;
		private String storageLocation;
		private int count;
		
		public EXWarehouseItem(String fid, String warehouse,
				String storageLocation, int count) {
			super();
			this.fid = fid;
			this.warehouse = warehouse;
			this.storageLocation = storageLocation;
			this.count = count;
		}


		public String getFid() {
			return fid;
		}
		public void setFid(String fid) {
			this.fid = fid;
		}
		public String getWarehouse() {
			return warehouse;
		}
		public void setWarehouse(String warehouse) {
			this.warehouse = warehouse;
		}
		public String getStorageLocation() {
			return storageLocation;
		}
		public void setStorageLocation(String storageLocation) {
			this.storageLocation = storageLocation;
		}
		public int getCount() {
			return count;
		}
		public void setCount(int count) {
			this.count = count;
		}
		
		
	}
	
	@RequestMapping(value = "/creatTruckassemble")
	public String creatTruckassemble(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		String result = "";
		List list = new ArrayList();
		try {
			
			result = doCreatTruckassemble(request, reponse,list);
			
			
			
		} catch (Exception e) {
			result = "{success:false,msg:'" + e.getMessage() + "'}";
			if(!e.getMessage().startsWith("系统正在执行装配，请稍后再试"))
			{
				iscreatTruckassemble=false;
			}
		}finally{
			iscreatTruckassemble=false;
		}
		reponse.setCharacterEncoding("utf-8");
		reponse.getWriter().write(result);
		return null;

	}


	public synchronized String doCreatTruckassemble(HttpServletRequest request,
			HttpServletResponse reponse,List<HashMap<String,Object>> li) throws Exception {
		String result = "";
		String userid = ((Useronline) request.getSession().getAttribute("Useronline")).getFuserid();
		String sql = "select FISFILTER from t_sys_user where FID='"+userid+"' ";
		List<HashMap<String,Object>> list= TruckassembleDao.QueryBySql(sql);
		if (list.get(0).get("FISFILTER")!=null && list.get(0).get("FISFILTER").toString().equals("1")) {
			throw new DJException("管理用户不能自运发货！");
		}
		
		sql = "select fsupplierid from t_bd_usersupplier where fuserid='"+userid+"' ";
		list= TruckassembleDao.QueryBySql(sql);
		if (list.size()!=1) {
			throw new DJException("管理用户不能自运发货！");
		}
		
//		if(iscreatTruckassemble)
//		{
//			throw new DJException("系统正在执行装配，请稍后再试.....");
//		}
//		iscreatTruckassemble=true;
			reponse.setCharacterEncoding("utf-8");
			String fids = request.getParameter("fidcls");
			fids="('"+fids.replace(",","','")+"')";
			int isrealType = 0;
			int realamount = 0;
			if("1".equals(request.getParameter("ftype"))){
				isrealType = 1 ;
				if(!DataUtil.positiveIntegerCheck(request.getParameter("frealamount"))){
					throw new DJException("实配数量必须大于0!");
				}
				realamount = new Integer(request.getParameter("frealamount"));
			}
			
			sql = " select * from t_ord_deliverorder where ( ifnull(fmatched,0)=1 or ifnull(faudited,0)=0 or FimportEas=1) and fid in " + fids;
			List<HashMap<String, Object>> deliverordercls = DeliverorderDao.QueryBySql(sql);
			if(deliverordercls.size()>0)
			{
				throw new DJException("部分配送信息未审核、已经配货、已导入接口，不能配货！");
			}
			 sql = " select * from t_ord_deliverorder where ifnull(fmatched,0)=0 and fid in " + fids;
			 deliverordercls = DeliverorderDao.QueryBySql(sql);
			 
			HashMap<String, Object> params = new HashMap<String, Object>();
			params.put("deliverordercls", deliverordercls);
			params.put("userid", ((Useronline) request.getSession().getAttribute("Useronline")).getFuserid());
			params.put("fids", fids);
			params.put("isrealType", isrealType);
			params.put("frealamount", realamount);
			HashMap<String, Object> results = DeliverorderDao.ExecCreateTruckassemble(params,1);
			if(results.get("success")==Boolean.TRUE){
				li.add(results);
				result = JsonUtil.result(true,"自运发货成功!", "", li);
			}else{
				result = JsonUtil.result(false,"自运发货失败!", "", "");
			}
		
		return result;
		
	}
	
	
	
	@RequestMapping("/getSplitDeliverorderInfo")
	public String getSplitDeliverorderInfo(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		reponse.setCharacterEncoding("utf-8");
		try {
			String fid = request.getParameter("fid");
			String sql="select d.fid,d.fnumber,d.famount,ifnull(d.fassembleQty,0) fassembleQty,ifnull(d.foutQty,0) foutQty from t_ord_deliverorder d where d.fid='"+fid+"' and ifnull(fimportEAS,0)=0 and ifnull(fouted,0)=0 and ifnull(fmatched,0)=0 and ifnull(faudited,0)=0 and ftype<>1";
			List<HashMap<String,Object>> result= DeliverorderDao.QueryBySql(sql);
		
			if(result.size()==0)
			{
				throw new DJException("该配送信息已审核或已导入或已配货或已发货或补单类型,不能拆分");
			}
			reponse.getWriter().write(JsonUtil.result(true,"","",result));
		} catch (Exception e) {
			reponse.getWriter().write(JsonUtil.result(false, e.getMessage(), "", ""));
		}
		return null;
	}
	
	

	@RequestMapping(value = "/saveSplitOrder")
	public String saveSplitOrder(HttpServletRequest request,
			HttpServletResponse reponse) throws Exception {


		String json = request.getReader().readLine();

		JSONArray jsonA = getJsonArrayByS(json);
		
		try {
			Deliverorder sinfo=null;
			List<Deliverorder> list=new ArrayList<Deliverorder>();
			
				for (int i = 0; i < jsonA.size(); i++) {
		
					int famount=Integer.valueOf(((JSONObject)jsonA.get(i)).getString("famount"));
					if(sinfo==null)
					{
						String deliverid=((JSONObject)jsonA.get(i)).getString("fid");
						sinfo=DeliverorderDao.Query(deliverid);
						if(sinfo==null|| sinfo.getFimportEas()==1 || sinfo.getFmatched()==1||sinfo.getFouted()==1||sinfo.getFaudited()==1||"1".equals(sinfo.getFtype()))
						{
							throw new DJException("该要货信息已审核或已经导入EAS或已配货或已发货或补单类型，不能拆分！");
						}
					}else
					{
						sinfo=(Deliverorder)sinfo.clone();
						sinfo.setFid(DeliverorderDao.CreateUUid());
						sinfo.setFnumber(ServerContext.getNumberHelper().getNumber("t_ord_deliverorder", "D", 4, false));
					}
					sinfo.setFamount(famount);
					list.add(sinfo);
			
					
				}
				DeliverorderDao.ExecSave(list);
				reponse.getWriter().write(JsonUtil.result(true, "保存成功", "", ""));
		} catch (DJException e) {
			reponse.getWriter().write(
					JsonUtil.result(false, "保存失败!" + e.getMessage(), "", ""));
		}

		return null;
	}
	
	private JSONArray getJsonArrayByS(String s) {

		JSONObject jso = JSONObject.fromObject(s);
		
		String dataT = jso.getString("data");
		
		JSONArray jsa = null;

		if (dataT.charAt(0) == '{') {
			dataT = "[" + dataT + "]";
		}

		jsa = JSONArray.fromObject(dataT);

		return jsa;
	}
	
	@RequestMapping(value = "/auditDeliverorder")
	public String auditDeliverorder(HttpServletRequest request,
			HttpServletResponse reponse) throws Exception {
		String result = "";
		// Saleorder pinfo = null;
		try {
			String userid = ((Useronline) request.getSession().getAttribute(
					"Useronline")).getFuserid();
			String orderids = request.getParameter("fidcls");
			orderids="('"+orderids.replace(",","','")+"')";
			DeliverorderDao.ExecAuditImportEAS(orderids,userid);
			
			result = JsonUtil.result(true, "审核成功！", "", "");
		} catch (Exception e) {
			result = "{success:false,msg:'" + e.getMessage() + "'}";
		}
		reponse.setCharacterEncoding("utf-8");
		reponse.getWriter().write(result);
		return null;
	}

	@RequestMapping(value = "/unauditDeliverorder")
	public String unauditDeliverorder(HttpServletRequest request,
			HttpServletResponse reponse) throws Exception {
		String result = "";
		try {
			String userid = ((Useronline) request.getSession().getAttribute(
					"Useronline")).getFuserid();
			String orderids = request.getParameter("fidcls");
			orderids="('"+orderids.replace(",","','")+"')";
			String sql = "select fid from t_ord_deliverorder where (fissync = 1 or fassembleQty>0 ) and faudited=1 and fid in "
					+ orderids;
			List<HashMap<String, Object>> data = saleOrderDao.QueryBySql(sql);
			if (data.size() > 0) {
				throw new DJException("配送信息已经导入到EAS或已配货，不能反审核！");
			}
			else {
				sql = "update t_ord_deliverorder set faudited=0,fauditorid=null"
						+ ",faudittime=null,fimportEas=0,fdelivertype=0 where faudited=1 and fid in "
						+ orderids;
				DeliverorderDao.ExecUpdateSQL(sql);	
			}
			result = JsonUtil.result(true, "反审核成功！", "", "");
		} catch (Exception e) {
			result = JsonUtil.result(false, e.getMessage(), "", "");
		}
		reponse.setCharacterEncoding("utf-8");
		reponse.getWriter().write(result);
		return null;
	}
	/**
	 * 更换订单
	 */
	@RequestMapping(value = "/updateAssginOrder")
	public String updateAssignOrder(HttpServletRequest request,
			HttpServletResponse reponse) throws Exception {
		String fid = request.getParameter("fid");
		List<Storebalance> storebalances=(ArrayList<Storebalance>)request.getAttribute("Storebalance");
		String msg=null;
		try {
			Deliverorder deliverorder = DeliverorderDao.Query(fid);
			if(deliverorder.getFaudited()==1){
				msg="该配送订单已审核";
			}else if(deliverorder.getFmatched()==1){
				msg="该配送订单已分配";
			}else if(deliverorder.getFimportEas()==1){
				msg="该配送订单已导入EAS";
			}else if("1".equals(deliverorder.getFtype())){
				msg="补单类型不能操作";
			}
			else if(deliverorder.getFtraitid()!=null&&!"".equals(deliverorder.getFtraitid()))
			{
				msg="一次性配送信息不能操作";
			}else{
				DeliverorderDao.ExecUpdateAllot(deliverorder,storebalances.get(0));
				msg="更改成功";
			}
			reponse.getWriter().write(JsonUtil.result(true, msg, "", ""));
		} catch (Exception e) {
			reponse.getWriter().write(
					JsonUtil.result(false, "更改失败!" + e.toString(), "", ""));
		}
		return null;
	}
	
	
	/**
	 * delivers导入EAS;
	 */
	
	@RequestMapping(value = "/deliverorderImportEAS")
	public String deliverorderImportEAS(HttpServletRequest request,
			HttpServletResponse reponse) {
//		Connection conn=null;
		try {
			String userid = ((Useronline) request.getSession().getAttribute("Useronline")).getFuserid();
//			conn=ServerContext.getOracleHelper().GetConn(request);
//			PreparedStatement stmt = null;
			reponse.setCharacterEncoding("utf-8");
			String fids = request.getParameter("fidcls");
			fids="('"+fids.replace(",","','")+"')";
		
			ProductPlan pdpinfo;
			String	sql = " select fid from t_ord_deliverorder where ifnull(fimportEAS,0)=0 and ifnull(faudited,0)=1 and ifnull(ftype,0)<>1 and ifnull(fassembleQty,0)=0 and fid in "
					+ fids;
			List<HashMap<String, Object>> deliverordercls = DeliverorderDao.QueryBySql(sql);
			if(deliverordercls.size()==0){
				throw new DJException("请选择（未导入，已审核，不是补单类型，提货数量为0）的配送单!");
			}
			String VMIdeliversql = "update t_ord_deliverorder set fimportEas=1,fdelivertype=2 where ifnull(fimportEAS,0)=0 and ifnull(faudited,0)=1 and ifnull(ftype,0)<>1 and ifnull(fassembleQty,0)=0 and fid in "+ fids;
					
			DeliverorderDao.ExecBySql(VMIdeliversql);
			reponse.getWriter().write(
					JsonUtil.result(true, "配送订单导入接口成功", "", ""));
		} catch (Exception e) {
			try {
				reponse.getWriter().write(JsonUtil.result(false, "配送订单导入失败！"+e.getMessage(), "", ""));
			
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
		}
		return null;
	}
	
	//收回配送单;
		@RequestMapping(value = "/deliverorderCancelImport")
		public String deliverorderCancelImport(HttpServletRequest request,
				HttpServletResponse reponse) throws IOException {
			String result = "";
			try {
				
				if(iscreatTruckassemble)
				{
					throw new DJException("系统正在执行收回配送单，请稍后再试.....");
				}
				iscreatTruckassemble=true;
				reponse.setCharacterEncoding("utf-8");
				String fids = request.getParameter("fidcls");
				fids="('"+fids.replace(",","','")+"')";
				String sql = " select fid from t_ord_deliverorder where ifnull(fimportEas,0)=1 and fid in " + fids;
				List<HashMap<String, Object>> deliverordercls = DeliverorderDao.QueryBySql(sql);

				if(deliverordercls.size()==0){
					throw new DJException("请选择已导入的配送单收回!");
				}
				String VMIdeliversql = "update t_ord_deliverorder set fimportEas=0,fissync=0 where ifnull(fimportEAS,0)=1 and fid in "+ fids;
				DeliverorderDao.ExecBySql(VMIdeliversql);
				result = JsonUtil.result(true,"收回配送单成功!", "", "");
				iscreatTruckassemble=false;
			} catch (Exception e) {
				result = "{success:false,msg:'" + e.getMessage() + "'}";
				if(!e.getMessage().startsWith("系统正在执行收回配送单，请稍后再试"))
				{
					iscreatTruckassemble=false;
				}
			}
			reponse.setCharacterEncoding("utf-8");
			reponse.getWriter().write(result);
			return null;

		}
	
		
		/**
		 * 我的收货
		 *
		 * @param request
		 * @param reponse
		 * @return
		 * @throws IOException
		 *
		 * @date 2014-9-18 下午4:01:44  (ZJZ)
		 */
		@RequestMapping("/selectFoutedDeliverorders")
		public String selectFoutedDeliverorders(HttpServletRequest request,
				HttpServletResponse reponse) throws IOException {	
			String userid = ((Useronline)request.getSession().getAttribute("Useronline")).getFuserid();
			String addCon = "";
			addCon=saleOrderDao.QueryFilterByUserofuser(request,"da.fcreatorid ","or");
			String sqls = "select 1 from t_bd_userrelationcustp where fuserid='"+userid+"'";
			List list = saleOrderDao.QueryBySql(sqls);
			if(list.size()>0){
				sqls = " cpdt.fid IN (SELECT fcustproductid FROM t_bd_userrelationcustp WHERE fuserid = '"+userid+"')" ;
				if(addCon.length()>0){
					sqls +=addCon ;
				}
			}else{
				
					sqls=(addCon.length()==0?"1=1":"1=2"+addCon);
			}
//			String sql = "select s.fname fsuppliername,d.fpcmordernumber,c.FNAME fcustname,  case d.fboxtype when 1 then f.fname else  cpdt.fname end cutpdtname, cpdt.fnumber pfnumber ,d.fid, date_format(d.fouttime, '%Y-%m-%d %H:%i') farrivetime, d.famount, d.fdescription, d.fnumber saleorderNumber, cpdt.FORDERUNIT forderunit, d.faddress faddress,d.fboxtype,  case d.fboxtype when 1 then  concat_ws('*',y.fboxwidth,y.fboxlength, y.fboxheight) else f1.fcharacter end fboxspec,concat_ws('*',y.fmateriallength,y.fmaterialwidth) fmaterialspec from t_ord_deliverorder d left join t_sys_supplier s on s.fid=d.fsupplierid left join t_bd_customer c ON c.fid = d.fcustomerid left join t_bd_custproduct cpdt ON cpdt.fid = d.fcusproductid  left join    (select fid, concat(f.fname,' ',f.fnumber,'','/',f.flayer,f.ftilemodelid) fname from t_pdt_productdef f) f  on d.fcusproductid=f.fid left join t_ord_deliverapply  y on y.fid=d.fdeliversid LEFT JOIN (SELECT SUBSTRING_INDEX(fapplayid, ',', 1) fapplayid,fid FROM t_ord_delivers) de ON d.fdeliversID= de.fid LEFT JOIN t_ord_deliverapply da ON de.fapplayid = da.fid  left join t_pdt_productdef  f1 on f1.fid=d.fproductid where ifnull(d.fouted,0) = 1 " ;
			String sql = "SELECT  d.fid,su.fname fsuppliername,c.fname fcustname,d.fboxtype,d.fnumber saleorderNumber,d.fpcmordernumber,CASE d.fboxtype WHEN 1 THEN f.fname ELSE cpdt.fname END cutpdtname,cpdt.fnumber pfnumber,CASE d.fboxtype WHEN 1 THEN CONCAT_WS('*',y.fboxwidth,y.fboxlength,y.fboxheight)  ELSE CONCAT_WS('*',f1.fboxwidth,f1.fboxlength,f1.fboxheight)  END fboxspec,CONCAT_WS('*',y.fmateriallength,y.fmaterialwidth) fmaterialspec,st.famount,cpdt.FORDERUNIT forderunit,d.fouttime farrivetime,d.faddress faddress,d.fdescription FROM `t_tra_saledeliverentry` st LEFT JOIN `t_tra_saledeliver` s ON s.fid = st.`FPARENTID` "+
						"LEFT JOIN t_ord_deliverorder d ON d.fid=st.`FDELIVERORDERID` LEFT JOIN t_ord_deliverapply Y ON y.fid = d.fdeliversid LEFT JOIN t_bd_custproduct cpdt ON cpdt.fid = d.fcusproductid LEFT JOIN (SELECT fid,CONCAT(f.fname,' ',f.fnumber,'','/',f.flayer,f.ftilemodelid) fname FROM  t_pdt_productdef f) f ON d.fcusproductid = f.fid LEFT JOIN t_bd_customer c ON c.fid = s.FCUSTOMERID LEFT JOIN t_sys_supplier su ON su.fid=s.fsupplier LEFT JOIN t_pdt_productdef f1 ON st.FPRODUCTID = f1.fid  where s.FAUDITED=1 ";
			//执行远程查询  
			if (request.getParameter("condictionValue") != null) {
				
				return selectFoutedDeliverordersByCondition(request, reponse,addCon,sqls,sql);
			} else if (request.getParameter("conditionDateField") != null) {
				
				return selectFoutedDeliverordersByDateCondition(request, reponse,addCon,sqls,sql);
				
			}
			
//			sql+="and ("+sqls+"or"+addCon+")"+saleOrderDao.QueryFilterByUser(request, "d.fcustomerid", null) + MySimpleToolsZ.getMySimpleToolsZ().buildDateBETWEENMonthSqlF(3, "d.farrivetime");
			sql+="and ("+sqls+")"+saleOrderDao.QueryFilterByUser(request, "s.fcustomerid", null) + MySimpleToolsZ.getMySimpleToolsZ().buildDateBETWEENMonthSqlF(3, "d.farrivetime");
			request.setAttribute("djsort", "d.farrivetime DESC");
			ListResult result;
			reponse.setCharacterEncoding("utf-8");
			try {
				result = DeliverorderDao.QueryFilterList(sql, request);
				reponse.getWriter().write(JsonUtil.result(true, "",result)); 
			} catch (DJException e) {
				reponse.getWriter().write(
						JsonUtil.result(false, e.getMessage(), "", ""));
			}

			return null;
		}
		
//		@RequestMapping("/selectFoutedDeliverordersMV")
//		public String selectFoutedDeliverordersMV(HttpServletRequest request,
//				HttpServletResponse response) throws IOException {
//			String userid = ((Useronline)request.getSession().getAttribute("Useronline")).getFuserid();
//			String sql = "SELECT cpdt.fnumber pfnumber,st.fid sdentryid,st.famount,st.faffirmed,cpdt.FORDERUNIT forderunit,_suppliername fsuppliername,_custname fcustname,d.fid,d.fboxtype,d.fnumber saleorderNumber,d.fpcmordernumber,_custpdtname cutpdtname,_spec fboxspec,_mspec fmaterialspec,d.fouttime farrivetime,d.faddress faddress,d.fdescription " +
//					"FROM `t_tra_saledeliverentry` st INNER JOIN t_ord_deliverorder_mv d ON d.fid = st.`FDELIVERORDERID` LEFT JOIN t_bd_custproduct cpdt ON cpdt.fid = d.fcusproductid ";
//			sql += "where 1 = 1 " + saleOrderDao.QueryFilterByUser(request, "d.fcustomerid", null);
//			String fidcls = baseSysDao.getFidclsBySql("SELECT fcustproductid ID FROM t_bd_userrelationcustp WHERE fuserid = '"+userid+"'");
//			if(!"".equals(fidcls)){
//				sql += " and cpdt.fid " + baseSysDao.getCondition(fidcls);
//			}
//			String defaultFilter = request.getParameter("Defaultfilter");
//			if(defaultFilter == null || !defaultFilter.contains("fouttime")){
//				sql += " and d.fouttime BETWEEN DATE_SUB(NOW(), INTERVAL 3 MONTH) and NOW()";
//			}
//			request.setAttribute("djsort", "d.fouttime DESC");
//			try {
//				ListResult result = DeliverorderDao.QueryFilterList(sql, request);
//				response.getWriter().write(JsonUtil.result(true, "",result));
//			} catch (DJException e) {
//				response.getWriter().write(
//						JsonUtil.result(false, e.getMessage(), "", ""));
//			}
//			return null;
//		}
		
		
		
		@RequestMapping("/selectFoutedDeliverordersMV")
		public String selectFoutedDeliverordersMV(HttpServletRequest request,
				HttpServletResponse response) throws IOException {
			String userid = ((Useronline)request.getSession().getAttribute("Useronline")).getFuserid();
			String timefilter=getFilterDateValue(request,"farrivetime");
			String sql="select sdentryid,fsupplierid,fsuppliername,fboxtype,saleorderNumber,fpcmordernumber,cutpdtname,fboxspec,fmaterialspec,famount,date_format(farrivetime,'%Y-%m-%d %H:%i') farrivetime,faddress,fdescription,faffirmed,forderunit,fcustoermid,fcustname,fid from ( ";
			 sql+=	"SELECT sd.fid sdentryid,fsupplierid,fsuppliername,0 fboxtype,sa.fnumber saleorderNumber ,'' fpcmordernumber,p.fname cutpdtname,p.fspec fboxspec, '' fmaterialspec,sd.famount famount, sa.fprinttime farrivetime, sa.fcustAddress  faddress,sd.fdescription  fdescription,sd.faffirmed faffirmed,p.funit forderunit,sa.fcustomer fcustoermid,c.fname fcustname ,sd.fid fid  "
						+" FROM t_ftu_saledeliver  sa LEFT JOIN t_ftu_saledeliverentry sd ON sd.fparentid=sa.fid LEFT JOIN t_bd_custproduct  p ON sd.fftuproductid = p.fid left join t_bd_customer c on sa.fcustomer=c.fid where 1=1 and sa.fstate<>1 "+(StringUtils.isEmpty(timefilter)?"":("and sa.fprinttime"+timefilter))+saleOrderDao.QueryFilterByUser(request, "sa.fcustomer",null);
			sql+=" union ";
			sql+= " SELECT st.fid sdentryid,st.fsupplierid,_suppliername fsuppliername,d.fboxtype,d.fnumber saleorderNumber,d.fpcmordernumber,_custpdtname cutpdtname,_spec fboxspec,_mspec fmaterialspec ,st.famount famount,d.fouttime farrivetime,d.faddress faddress,d.fdescription ,st.faffirmed,cpdt.FORDERUNIT forderunit,d.fcustomerid  fcustoermid,_custname fcustname,d.fid fid" +
					" FROM t_ord_deliverorder_mv d INNER JOIN t_tra_saledeliverentry st ON d.fid = st.`FDELIVERORDERID` LEFT JOIN t_bd_custproduct cpdt ON cpdt.fid = d.fcusproductid ";
			sql += "where 1 = 1 "+ (StringUtils.isEmpty(timefilter)?"":("and d.fouttime"+timefilter)) + saleOrderDao.QueryFilterByUser(request, "d.fcustomerid", null);
			String fidcls = baseSysDao.getFidclsBySql("SELECT fcustproductid ID FROM t_bd_userrelationcustp WHERE fuserid = '"+userid+"'");
			if(!"".equals(fidcls)){
				sql += " and cpdt.fid " + baseSysDao.getCondition(fidcls);
			}
		 sql+=" ) t where 1=1";
//			String defaultFilter = request.getParameter("Defaultfilter");
//			if(defaultFilter == null || !defaultFilter.contains("fouttime")){
//				sql += " and d.fouttime BETWEEN DATE_SUB(NOW(), INTERVAL 3 MONTH) and NOW()";
//			}
			
			request.setAttribute("djsort", "farrivetime DESC ,saleorderNumber DESC");
			try {
				ListResult result = DeliverorderDao.QueryFilterList(sql, request);
				response.getWriter().write(JsonUtil.result(true, "",result));
			} catch (DJException e) {
				response.getWriter().write(
						JsonUtil.result(false, e.getMessage(), "", ""));
			}
			return null;
		}
		
		
		private String getFilterDateValue(HttpServletRequest request,String name){
			
			String Defaultfilter = request.getParameter("Defaultfilter");
			String Defaultmaskstring = request.getParameter("Defaultmaskstring");
			if(!Defaultfilter.contains(name)){return "";}
			String firstdate="";
			String flastdate="";
			if (Defaultfilter != null && Defaultfilter != ""
					&& Defaultmaskstring != null && Defaultmaskstring != "") {
				JSONArray j = JSONArray.fromObject(Defaultfilter);
				for (int i = j.size() - 1; i >= 0; i--) {
					JSONObject o = j.getJSONObject(i);
					Defaultfilter = "";
					if (!o.has("myfilterfield")
							|| o.getString("myfilterfield").isEmpty()
							|| !o.has("CompareType")
							|| o.getString("CompareType").isEmpty()
							|| !o.has("value")) {
						throw new DJException("过滤条件设置有误");
					}
				
					if(o.getString("myfilterfield").contains(name)&&o.getString("CompareType").contains("<")){
						flastdate="'"+o.getString("value")+"'";
					}
					if(o.getString("myfilterfield").contains(name)&&o.getString("CompareType").contains(">")){
						firstdate="'"+o.getString("value")+"'";
					}
				}
				// JSONArray a = JSONArray.fromObject(filter);
			}
			return "".equals(firstdate)?"":" BETWEEN "+firstdate+" and " +flastdate;
		}
		/*
		 * 我的收货增加确认功能;
		 */
		@RequestMapping("/updateFoutedDeliverordersFaffirmed")
		public String updateFoutedDeliverordersFaffirmed(HttpServletRequest request,
				HttpServletResponse response) throws IOException {
			
			JSONArray jsa = JSONArray.fromObject(request.getParameter("fids"));
			String faffirmed = request.getParameter("goalFieldPropValue");
			String ids = "";
			for (int i = 0; i < jsa.size(); i++) {
				
				String id = jsa.getString(i);
				
				if(ids.equals("")){
					ids = "'"+id+"'";
				}else{
					ids = ids + ",'"+id+"'"; 
				}
			}
			try {
				baseSysDao.ExecBySql("update t_tra_saledeliverentry set faffirmed = "+faffirmed+" where fid in ("+ids+")");
				baseSysDao.ExecBySql("update t_ftu_saledeliverentry set faffirmed = "+faffirmed+" where fid in ("+ids+")");
				response.getWriter().write(JsonUtil.result(true, "确认","",""));
			} catch (DJException e) {
				response.getWriter().write(
						JsonUtil.result(false, e.getMessage(), "", ""));
			}
			return null;
		}
		
		/**
		 * 我的收货明细
		 * @param request
		 * @param reponse
		 * @return
		 * @throws IOException
		 */
		@RequestMapping("/selectMyFoutedDeliverorders")
		public String selectMyFoutedDeliverorders(HttpServletRequest request,
				HttpServletResponse reponse) throws IOException {	
			String userid = ((Useronline)request.getSession().getAttribute("Useronline")).getFuserid();
			String addCon = "";
			addCon=saleOrderDao.QueryFilterByUserofuser(request,"da.fcreatorid ","or");
			String sqls = "select 1 from t_bd_userrelationcustp where fuserid='"+userid+"'";
			List list = saleOrderDao.QueryBySql(sqls);
			if(list.size()>0){
				sqls = " cpdt.fid IN (SELECT fcustproductid FROM t_bd_userrelationcustp WHERE fuserid = '"+userid+"')" ;
				if(addCon.length()>0){
					sqls +=addCon ;
				}
			}else{
				
					sqls=(addCon.length()==0?"1=1":"1=2"+addCon);
			}
			String sql = "select s.fname fsuppliername,d.fpcmordernumber,c.FNAME fcustname,cpdt.fname cutpdtname, d.fid, cpdt.fnumber pfnumber, date_format(d.fouttime, '%Y-%m-%d %H:%i') farrivetime, d.famount, d.fdescription, d.fnumber saleorderNumber, cpdt.FORDERUNIT forderunit, d.faddress faddress, u1.fname fcreator from t_ord_deliverorder d left join t_sys_supplier s on s.fid=d.fsupplierid left join t_bd_customer c ON c.fid = d.fcustomerid left join t_bd_custproduct cpdt ON cpdt.fid = d.fcusproductid LEFT JOIN (SELECT SUBSTRING_INDEX(fapplayid, ',', 1) fapplayid,fid FROM t_ord_delivers) de ON d.fdeliversID= de.fid LEFT JOIN t_ord_deliverapply da ON de.fapplayid = da.fid left join t_sys_user u1 on u1.fid=da.fcreatorid where d.fouted = 1 " ;
			//执行远程查询  
			if (request.getParameter("condictionValue") != null) {
				
				return selectFoutedDeliverordersByCondition(request, reponse,addCon,sqls,sql);
			} else if (request.getParameter("conditionDateField") != null) {
				
				return selectFoutedDeliverordersByDateCondition(request, reponse,addCon,sqls,sql);
				
			}
			
//			sql+="and ("+sqls+"or"+addCon+")"+saleOrderDao.QueryFilterByUser(request, "d.fcustomerid", null) + MySimpleToolsZ.getMySimpleToolsZ().buildDateBETWEENMonthSqlF(3, "d.farrivetime");
			sql+="and ("+sqls+")"+saleOrderDao.QueryFilterByUser(request, "d.fcustomerid", null) + MySimpleToolsZ.getMySimpleToolsZ().buildDateBETWEENMonthSqlF(3, "d.farrivetime");
			request.setAttribute("djsort", "farrivetime DESC");
			ListResult result;
			reponse.setCharacterEncoding("utf-8");
			try {
				result = DeliverorderDao.QueryFilterList(sql, request);
				reponse.getWriter().write(JsonUtil.result(true, "",result)); 
			} catch (DJException e) {
				reponse.getWriter().write(
						JsonUtil.result(false, e.getMessage(), "", ""));
			}

			return null;
		}

		private String selectFoutedDeliverordersByDateCondition(
				HttpServletRequest request, HttpServletResponse reponse,String addCon,String sqls,String sql) throws IOException {

			// TODO Auto-generated method stub
			
//			request.setAttribute("djsort","farrivetime desc");
			
			reponse.setCharacterEncoding("utf-8");
			request.setCharacterEncoding("utf-8");
			
//			String field = request.getParameter("conditionDateField");
//			
//			String startDate =  request.getParameter("beginTime");
//			String endDate =  request.getParameter("endTime");
			
			
			try {
				 if(addCon.equals("")){
					 addCon = "''";
				 }
//				String sql = BASE_SQL + " and "+ field +" BETWEEN ' " + startDate + "' AND '" +endDate+ "' " 
//						+ saleOrderDao.QueryFilterByUser(request, "d.fcustomerid", null);
				request.setAttribute("djsort", "farrivetime DESC");
				String sql1 =  sql + MySimpleToolsZ.getMySimpleToolsZ().buildDateBetweenSQLFragment(request) 
						+"and ("+sqls+" or "+addCon+")"+ saleOrderDao.QueryFilterByUser(request, "d.fcustomerid", null);
				 
				ListResult result = storebalanceDao.QueryFilterList(sql1, request); 
				
//				List result = storebalanceDao.QueryBySql(sql);
				
//				reponse.getWriter().write(JsonUtil.result(true, "", result.size() + "", result));
				
				reponse.getWriter().write(JsonUtil.result(true, "", result));
			} catch (DJException e) {
				// TODO Auto-generated catch block
				reponse.getWriter().write( 
						JsonUtil.result(false, e.getMessage(), "", ""));
			}
			return null;
		
		
		}


		private String selectFoutedDeliverordersByCondition(
				HttpServletRequest request, HttpServletResponse reponse,String sqls,String addCon,String sql) throws IOException {
			// TODO Auto-generated method stub
			
			reponse.setCharacterEncoding("utf-8");
			request.setCharacterEncoding("utf-8");
			String userid = ((Useronline)request.getSession().getAttribute("Useronline")).getFuserid();
//			request.setAttribute("djsort","farrivetime desc");
			
//			String field = request.getParameter("condictionField");
//			
//			String value =request.getParameter("condictionValue");
//			value =  new String(value.toString().getBytes("ISO-8859-1"), "UTF-8");
			
			try {
				
//				String sql = BASE_SQL + " and " + field + " like '%" + value + "%' "
//						+ saleOrderDao.QueryFilterByUser(request, "d.fcustomerid", null);
				
				String sql1 = sql + MySimpleToolsZ.getMySimpleToolsZ().buildMySearchBoxSQLFragment(request) 
						+"and ("+sqls+"or"+addCon+")"+ saleOrderDao.QueryFilterByUser(request, "d.fcustomerid", null)  + MySimpleToolsZ.getMySimpleToolsZ().buildDateBETWEENMonthSqlF(3, "d.farrivetime");
//				String sqls = "select 1 from t_bd_userrelationcustp where fuserid='"+userid+"'";
//				List list = saleOrderDao.QueryBySql(sqls);
//				if(list.size()>0){
//					sql += " and cpdt.fid IN (SELECT fcustproductid FROM t_bd_userrelationcustp WHERE fuserid = '"+userid+"')";
//				}
			
				
				ListResult result = storebalanceDao.QueryFilterList(sql1, request);
				
//				List result = storebalanceDao.QueryBySql(sql);
				
//				reponse.getWriter().write(JsonUtil.result(true, "", result.size() + "", result));
				
				reponse.getWriter().write(JsonUtil.result(true, "", result));
			} catch (DJException e) {
				// TODO Auto-generated catch block
				reponse.getWriter().write( 
						JsonUtil.result(false, e.getMessage(), "", ""));
			}
			return null;
		
		}
		
		
		/**
		 * 我的收货,折线图数据
		 *
		 * @param request
		 * @param reponse
		 * @return
		 * @throws IOException
		 *
		 * @date 2014-9-20(ZJZ)
		 */
		@RequestMapping("/selectFoutedDeliverorderQtyAndDate")
		public String selectFoutedDeliverorderQtyAndDate(HttpServletRequest request,
				HttpServletResponse reponse) throws IOException {  
			
//			String sql = " SELECT date_format(fcreatetime, '%Y-%m-%d') fcreatetime, sum(famount) as famount FROM t_ord_deliverorder where fouted = 1 and fcreatetime BETWEEN DATE_SUB(NOW(), INTERVAL 1 MONTH) and NOW() GROUP BY DATE_FORMAT(fcreatetime, '%Y-%m-%d') order by fcreatetime desc  ";
//
//			sql=sql +saleOrderDao.QueryFilterByUser(request, "fcustomerid", null);

			
			String sql = MySimpleToolsZ.getMySimpleToolsZ().buildLineSql("t_ord_deliverorder", "famount", "farrivetime", saleOrderDao.QueryFilterByUser(request, "fcustomerid", null), false);
			
			List result;
			reponse.setCharacterEncoding("utf-8");
			
			try {
				result = DeliverorderDao.QueryBySql(sql);
				
				MySimpleToolsZ.getMySimpleToolsZ().sortListByDateDesc(result, "farrivetime");
				
//				Collections.sort(result.getData(), new Comparator<HashMap<String, Object>>() {
//
//					@Override
//					public int compare(HashMap<String, Object> o1, HashMap<String, Object> o2) {
//						// TODO Auto-generated method stub 
//						
//						DateFormat df = new SimpleDateFormat("yyyy-MM-dd"); 
//						
//						Date date1 = null;
//						Date date2 = null;
//						try {
//							
//							date1 = df.parse((String)o1.get("fcreatetime"));
//							
//							date2 = df.parse((String)o2.get("fcreatetime"));
//							
//						} catch (ParseException e) {
//							// TODO Auto-generated catch block
//							e.printStackTrace();
//						}
//						
//						return date1.compareTo(date2); 
//					}
//			
//				});
				
				
				reponse.getWriter().write(JsonUtil.result(true, "",result.size() + "", result));
			} catch (DJException e) {
				reponse.getWriter().write(
						JsonUtil.result(false, e.getMessage(), "", ""));
			}

			return null;
		}
		
	@RequestMapping("excelActualdeliverrpt")
	public void excelActualdeliverrpt(HttpServletRequest request,
			HttpServletResponse reponse){
		String userid = ((Useronline)request.getSession().getAttribute("Useronline")).getFuserid();
		try {
			String sql = "select cpdt.fname 包装物名称,cpdt.fnumber 包装物编号, date_format(d.farrivetime, '%Y-%m-%d %H:%i') 出库时间, d.famount 出库数量, d.fdescription 备注, d.fnumber  采购订单号, cpdt.FORDERUNIT 单位, d.faddress 收货地址  from t_ord_deliverorder d left join t_bd_customer c ON c.fid = d.fcustomerid left join t_bd_custproduct cpdt ON cpdt.fid = d.fcusproductid where d.fouted = 1 ";
			String addCon = "";
			String sqls = "select quote(fuserid) userid from t_bd_usertouser where fsuperuserid='"+userid+"'";
			List<HashMap<String, Object>> demandList = saleOrderDao.QueryBySql(sqls);
			if(demandList.size()!=0){//关联用户过滤
				StringBuilder userids=new StringBuilder("'"+userid+"'");
				for(HashMap<String, Object> m : demandList){
					userids.append(","+m.get("userid"));
				}
				addCon = "  d.fcreatorid in ("+userids+")";
			}else{
				addCon = "  d.fcreatorid = '"+userid+"'";
			}
			
			 sqls = "select 1 from t_bd_userrelationcustp where fuserid='"+userid+"'";
			List list = saleOrderDao.QueryBySql(sqls);
			if(list.size()>0){
				sqls = " cpdt.fid IN (SELECT fcustproductid FROM t_bd_userrelationcustp WHERE fuserid = '"+userid+"')";
			}else{
				sqls = " 1=2 ";
			}
			sql=sql +"and ("+sqls+"or"+addCon+")"+saleOrderDao.QueryFilterByUser(request, "d.fcustomerid", null) + MySimpleToolsZ.getMySimpleToolsZ().buildDateBETWEENMonthSqlF(3, "d.farrivetime") 
					+ MySimpleToolsZ.getMySimpleToolsZ().buildOrderBySQLFragment(TIME_FIELD);
			request.setAttribute("nolimit", "");
			ListResult lists = saleOrderDao.QueryFilterList(sql, request);
			
		ExcelUtil.toexcel(reponse, lists);
		} catch (DJException e) {
			// TODO: handle exception
		}
	}

/**
	 * 制造商平台的 出入库明细
	 */
	@RequestMapping("orderAccessRecord")
	public String orderAccessRecord(HttpServletRequest request , HttpServletResponse response)throws IOException{
		String sqlA = "select c.fname cname,ppp.fname pname,pp.fnumber  pnumber,case when ifnull(ppp.fboxlength,'')='' then concat(ppp.fmateriallength,'x',ppp.fmaterialwidth) when ppp.fboxlength=0 then concat(ppp.fmateriallength,'x',ppp.fmaterialwidth)   else concat(ppp.fboxlength,'x',ppp.fboxwidth,'x',ppp.fboxheight) end pspec,case when ifnull(p.fproductplanid,'')='' then '调整入库' else '入库' end ftype,p.finqty outIn,p.fcreatetime pcreatetime,u.fname uname from t_inv_productindetail p left join t_ord_productplan pp on p.fproductplanid = pp.fid left join t_bd_customer c on pp.fcustomerid = c.fid left join t_pdt_productdef ppp on p.fproductid = ppp.fid left join t_sys_user u on u.fid=p.fcreatorid where 1=1 ";
		String sqlB = "select c.fname cname,ppp.fname pname,pp.fnumber pnumber,case when ifnull(ppp.fboxlength,'')='' then concat(ppp.fmateriallength,'x',ppp.fmaterialwidth) when ppp.fboxlength=0 then concat(ppp.fmateriallength,'x',ppp.fmaterialwidth)   else concat(ppp.fboxlength,'x',ppp.fboxwidth,'x',ppp.fboxheight) end pspec,case when ifnull(o.fproductplanid,'') = '' then '调整出库' else '销售出库' end ftype,o.foutqty outIn,o.fcreatetime pcreatetime,u.fname uname from  t_inv_outwarehouse o left join t_ord_productplan pp on o.fproductplanid = pp.fid left join t_bd_customer c on pp.fcustomerid = c.fid left join t_pdt_productdef ppp on o.fproductid = ppp.fid left join t_sys_user u on u.fid=o.fcreatorid where 1=1 ";
		sqlA = sqlA + saleOrderDao.QueryFilterByUser(request, null, "pp.fsupplierid");
		//"pp.fsupplierid"
		sqlB = sqlB + saleOrderDao.QueryFilterByUser(request, null, "pp.fsupplierid");
		String sql = "select * from ("+sqlA +" union "+ sqlB+") b where 1=1";
		ListResult result;
		if(request.getParameter("conditionDateField")!=null){
			return orderAccessRecordByCondition(request , response,sql);
		}
		response.setCharacterEncoding("utf-8");
		try{
		result = DeliverorderDao.QueryFilterList(sql, request);
		response.getWriter().write(JsonUtil.result(true, "", result));
		}catch(DJException e){
			response.getWriter().write(JsonUtil.result(false, e.getMessage(), "",""));
		}
		return null;
	}
	private String orderAccessRecordByCondition(HttpServletRequest request , HttpServletResponse response,String sql)throws IOException{
		response.setCharacterEncoding("utf-8");
		request.setCharacterEncoding("utf-8");
		try {
			String sql1 = sql + MySimpleToolsZ.getMySimpleToolsZ().buildDateBetweenSQLFragment(request);
			ListResult result = DeliverorderDao.QueryFilterList(sql1, request);
			response.getWriter().write(JsonUtil.result(true, "", result));
		} catch (DJException e) {
			// TODO: handle exception
			response.getWriter().write(JsonUtil.result(false, e.getMessage(), "",""));
		}
		return null;
	}
	
	/**
	 * 出入库明细导出Excel
	 */
	@RequestMapping(value = "/outIntoexcel")
	public String productplantoexcel(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		try {
			String sqlA = "select c.fname 客户名称,ppp.fname 包装物名称,pp.fnumber  制造订单号,case when ifnull(ppp.fboxlength,'')='' then concat(ppp.fmateriallength,'x',ppp.fmaterialwidth) when ppp.fboxlength=0 then concat(ppp.fmateriallength,'x',ppp.fmaterialwidth)   else concat(ppp.fboxlength,'x',ppp.fboxwidth,'x',ppp.fboxheight) end 规格,case when ifnull(p.fproductplanid,'')='' then '调整入库' else '入库' end 类型,p.finqty 出入库数量,p.fcreatetime 出入库日期,u.fname 操作人 from t_inv_productindetail p left join t_ord_productplan pp on p.fproductplanid = pp.fid left join t_bd_customer c on pp.fcustomerid = c.fid left join t_pdt_productdef ppp on p.fproductid = ppp.fid left join t_sys_user u on u.fid=p.fcreatorid where 1=1 ";
			String sqlB = "select c.fname 客户名称,ppp.fname 包装物名称,pp.fnumber 制造订单号,case when ifnull(ppp.fboxlength,'')='' then concat(ppp.fmateriallength,'x',ppp.fmaterialwidth) when ppp.fboxlength=0 then concat(ppp.fmateriallength,'x',ppp.fmaterialwidth)   else concat(ppp.fboxlength,'x',ppp.fboxwidth,'x',ppp.fboxheight) end 规格,case when ifnull(o.fproductplanid,'') = '' then '调整出库' else '销售出库' end 类型,o.foutqty 出入库数量,o.fcreatetime 出入库日期,u.fname 操作人 from  t_inv_outwarehouse o left join t_ord_productplan pp on o.fproductplanid = pp.fid left join t_bd_customer c on pp.fcustomerid = c.fid left join t_pdt_productdef ppp on o.fproductid = ppp.fid left join t_sys_user u on u.fid=o.fcreatorid where 1=1 ";
			sqlA = sqlA + saleOrderDao.QueryFilterByUser(request, "pp.fcustomerid", "pp.fsupplierid");
			sqlB = sqlB + saleOrderDao.QueryFilterByUser(request, "pp.fcustomerid", "pp.fsupplierid");
			String sql = "select * from ("+sqlA +" union "+ sqlB+") b where 1=1";
			ListResult result= DeliverorderDao.QueryFilterList(sql, request);
			ExcelUtil.toexcel(reponse,result);
		} catch (DJException e) {
			// TODO Auto-generated catch block
			reponse.getWriter().write(
					JsonUtil.result(false, e.getMessage(), "", ""));
		}

		return null;

	}
	
	@RequestMapping("/creatTruckassembleByCondition")
	public String creatTruckassembleByCondition(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		reponse.setCharacterEncoding("utf-8");
		String result = ""; 
		try {
		
			result = DeliverorderDao.ExecCreatTruckassembleByCondition(request,reponse);
			
			reponse.getWriter().write(result);
//			reponse.getWriter().write(JsonUtil.result(true, "", result));
		} catch (Exception e) {
			reponse.getWriter().write(
					JsonUtil.result(false, e.getMessage(), "", ""));
		}
		return null;
	}
	@RequestMapping("/GetMyDeliveryList")
	public String GetMyDeliveryList(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		request.setAttribute("djsort", "d.fcreatetime desc");
		String sql = "SELECT s.fname sname,IFNULL(st.fbalanceqty,0) fbalanceqty,d.fsupplierid,d.fdelivertype,d.fstate fstate,CASE  d.`fboxtype` WHEN 1 THEN '' ELSE  pdef.fnumber END pfnumber,CASE d.`fboxtype`  WHEN 1 THEN CONCAT_WS('x',dp.`FBOXLENGTH`,dp.`FBOXWIDTH`,dp.`FBOXHEIGHT`) ELSE CONCAT_WS('x',pdef.`FBOXLENGTH`,pdef.`FBOXWIDTH`,pdef.`FBOXHEIGHT`) END cfspec,d.fpcmordernumber,CASE d.fboxtype WHEN 1 THEN CONCAT(pdef.fname,' ',pdef.fnumber,' ','/',pdef.flayer,pdef.ftilemodelid) ELSE pdef.fname END productname, d.fordermanid,d.fordertime,d.fordered,c.fname AS fcustname,d.fid,d.fcreatorid,d.fcreatetime,d.fupdateuserid,d.fupdatetime,d.fnumber,d.fcustomerid,d.fcusproductid, DATE_FORMAT(d.farrivetime, '%Y-%m-%d %T') farrivetime,d.flinkman,d.flinkphone,d.famount,IFNULL(d.fassembleQty, 0) fassembleQty,IFNULL(d.foutQty, 0) foutQty, d.faddress,d.fdescription,d.fsaleorderid,IFNULL(d.fordernumber, '') fordernumber,d.forderentryid,d.fimportEAS,d.fimportEASuserid,d.fimportEAStime,d.fmatched, d.fouted,d.foutorid,d.fouttime,d.ftype , d.`fboxtype`,CONCAT_WS('x',dp.`fmateriallength`,dp.`fmaterialwidth`) fmaterial FROM t_ord_deliverorder d LEFT JOIN t_bd_customer c ON c.fid = d.fcustomerid LEFT JOIN t_pdt_productdef pdef ON pdef.fid = d.fproductid LEFT JOIN t_ord_deliverapply dp ON d.`fdeliversID`=dp.`fid` LEFT JOIN t_inv_usedstorebalance ust  ON ust.fdeliverorderid = d.fid LEFT JOIN `t_inv_storebalance` st ON st.fid=ust.fstorebalanceid left join t_sys_supplier s on s.fid= d.fsupplierid where 1 = 1";
		sql=sql +saleOrderDao.QueryFilterByUser(request, null, "d.fsupplierid");
		ListResult result;
		try {
			result = DeliverorderDao.QueryFilterList(sql, request);
			reponse.getWriter().write(JsonUtil.result(true, "", result));
			request.getSession().setAttribute("exportMyDeliveryList", result);
		} catch (DJException e) {
			reponse.getWriter().write(
					JsonUtil.result(false, e.getMessage(), "", ""));
		}

		return null;
	}
	
	@RequestMapping("/GetMyDeliveryListMV")
	public String GetMyDeliveryListMV(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		request.setAttribute("djsort", "fcreatetime desc");
		String sql = "SELECT _suppliername sname,_balanceqty fbalanceqty,fsupplierid,fdelivertype,fstate,_productnumber pfnumber,_spec cfspec,fpcmordernumber,_productname productname,fordermanid,fordertime,fordered,_custname fcustname,fid,fcreatetime,fupdatetime,fnumber,fcustomerid,fcusproductid, DATE_FORMAT(farrivetime, '%Y-%m-%d %T') farrivetime,flinkman,flinkphone,famount,IFNULL(fassembleQty, 0) fassembleQty,IFNULL(foutQty, 0) foutQty,faddress,fdescription,fsaleorderid,fordernumber,forderentryid,fimportEAS,fimportEASuserid,fimportEAStime,fmatched,fouted,foutorid,fouttime,ftype ,`fboxtype`,_mspec fmaterial,_custpdtspec custpdtspec FROM t_ord_deliverorder_mv where 1 = 1 ";
		sql=sql +saleOrderDao.QueryFilterByUser(request, null, "fsupplierid");
		ListResult result;
		try {
			result = DeliverorderDao.QueryFilterList(sql, request);
			response.getWriter().write(JsonUtil.result(true, "", result));
			request.getSession().setAttribute("exportMyDeliveryList", result);
		} catch (DJException e) {
			response.getWriter().write(
					JsonUtil.result(false, e.getMessage(), "", ""));
		}
		return null;
	}
	
	@RequestMapping("/getSaledeliver")
	public void getSaledeliver(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException{
		String fids = request.getParameter("fids");
		try {
			String sql = "SELECT s.fid FROM t_tra_saledeliver s LEFT JOIN t_tra_saledeliverentry ss ON ss.fparentid=s.FID LEFT JOIN t_ord_deliverorder d ON ss.fdeliverorderid=d.fid WHERE d.fid in('"+fids+"')";
			List list = DeliverorderDao.QueryBySql(sql);
			reponse.getWriter().write(JsonUtil.result(true, "","", list));
		} catch (DJException e) {
			// TODO: handle exception
			reponse.getWriter().write(
					JsonUtil.result(false, e.getMessage(), "", ""));
		}
	}
	@RequestMapping("/GainCfgByFkey")
	public void GainCfgByFkey(HttpServletRequest request,HttpServletResponse response) throws IOException{
		try {
			int fouted = Integer.parseInt((String) syscfgDao.ExecGainCfgByFkey(request, "autoDepart").getData().get(0).get("fvalue"));
			String result = "";
			if(fouted==1){
				result = JsonUtil.result(true, "","","");
			}else{
				result = JsonUtil.result(false, "","","");
			}
			response.getWriter().write(result);
		} catch (DJException e) {
			// TODO: handle exception
			response.getWriter().write(
					JsonUtil.result(false, e.getMessage(), "", ""));
		}
	}
	@RequestMapping("/GetMyDeliveryInfo")
	public String GetMyDeliveryInfo(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		String fid = request.getParameter("fid");
		String sql = "select  d.fdelivertype,CASE d.fboxtype WHEN 1 THEN dp.fstate ELSE da.fstate END fstate,pdef.fnumber pfnumber,CASE d.fboxtype WHEN 1  THEN CONCAT_WS('x',dp.`FBOXLENGTH`,dp.`FBOXWIDTH`,dp.`FBOXHEIGHT`)  ELSE CONCAT_WS('x',pdef.`FBOXLENGTH`,pdef.`FBOXWIDTH`,pdef.`FBOXHEIGHT`)  END  cfspec,d.fpcmordernumber,pdef.fname productname,d.fordermanid,u3.fname forderman,d.fordertime,d.fordered,u1.fname as fcreator,u2.fname as flastupdater,c.fname as fcustname,cpdt.fname cutpdtname,d.fid,d.fcreatorid,d.fcreatetime,d.fupdateuserid,d.fupdatetime,d.fnumber,d.fcustomerid,d.fcusproductid,date_format(d.farrivetime,'%Y-%m-%d %T') farrivetime,d.flinkman,d.flinkphone,d.famount,ifnull(d.fassembleQty,0) fassembleQty,ifnull(d.foutQty,0) foutQty,d.faddress,d.fdescription,d.fsaleorderid,ifnull(d.fordernumber,'') fordernumber,d.forderentryid,d.fimportEAS,d.fimportEASuserid,d.fimportEAStime,d.fmatched,d.fouted,d.foutorid,d.fouttime,d.faudited,ifnull(d.faudittime,'') faudittime,ifnull(ad.fname,'') fauditor,d.ftype from t_ord_deliverorder d left join t_sys_user u1 on u1.fid=d.fcreatorid left join t_sys_user u2 on u2.fid=d.fupdateuserid left join t_sys_user u3 on u3.fid=d.fordermanid left join t_bd_customer c on c.fid=d.fcustomerid left join t_bd_custproduct cpdt on cpdt.fid=d.fcusproductid left join t_pdt_productdef pdef on pdef.fid=d.fproductid left join t_sys_user ad on ad.fid=d.fauditorid  LEFT JOIN (SELECT SUBSTRING_INDEX(fapplayid, ',', 1) fapplayid,fid FROM t_ord_delivers) de ON d.fdeliversID = de.fid LEFT JOIN t_ord_deliverapply da ON de.fapplayid = da.fid LEFT JOIN t_ord_deliverapply dp  ON dp.fid=d.`fdeliversID` where 1=1 and d.fid='"+fid+"'";
		sql=sql +saleOrderDao.QueryFilterByUser(request, "d.fcustomerid", "d.fsupplierid");
		List result;
		reponse.setCharacterEncoding("utf-8");
		try {
			result = DeliverorderDao.QueryBySql(sql);
			reponse.getWriter().write(JsonUtil.result(true, "","", result));
		} catch (DJException e) {
			reponse.getWriter().write(
					JsonUtil.result(false, e.getMessage(), "", ""));
		}

		return null;
	}
	@RequestMapping("/ExportMyDelivery")
	public String ExportMyDelivery(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		ListResult result;
		result = (ListResult) request.getSession().getAttribute("exportMyDeliveryList");
		List<HashMap<String, Object>> datas = result.getData();
		for(HashMap<String, Object> data: datas){
			if("1".equals(String.valueOf(data.get("fboxtype")))){
				data.put("fboxtype","纸板订单");
			}else{
				data.put("fboxtype", "纸箱订单");
			}
			if("1".equals(String.valueOf(data.get("fdelivertype")))){
				data.put("fdelivertype","自运发货");
			}else{
				data.put("fdelivertype","协同发货");
			}
		}
		HashMap<String, String> map = new HashMap<>();
		List<String> order = new ArrayList<>();
		map.put("fordernumber", "制造商订单号");
		order.add("fordernumber");
		map.put("fnumber", "配送单号");
		order.add("fnumber");
		map.put("fboxtype", "订单类型");
		order.add("fboxtype");
		map.put("productname", "包装物名称");
		order.add("productname");
		map.put("pfnumber", "包装物编号");
		order.add("pfnumber");
		map.put("fcustname", "客户名称");
		order.add("fcustname");
		map.put("flinkman", "联系人");
		order.add("flinkman");
		map.put("flinkphone", "联系电话");
		order.add("flinkphone");
		map.put("cfspec", "纸箱规格");
		order.add("cfspec");
		map.put("fmaterial", "下料规格");
		order.add("fmaterial");
		map.put("faddress", "配送地址");
		order.add("faddress");
		map.put("farrivetime", "配送时间");
		order.add("farrivetime");
		map.put("famount", "配送数量");
		order.add("famount");
		map.put("fassembleQty", "实际配送数量");
		order.add("fassembleQty");
		map.put("fdescription", "备注");
		order.add("fdescription");
		map.put("fouttime", "实际配送时间");
		order.add("fouttime");
		map.put("fdelivertype", "发货类型");
		order.add("fdelivertype");
		ExcelUtil.toexcel(response, result,map,order);
		return null;
	}

	@RequestMapping(value = "/closeDeliverorder")
	public String closeDeliverorder(HttpServletRequest request,
			HttpServletResponse reponse) throws Exception {
		String result = "";
		try {
			String fidcls = request.getParameter("fidcls");
			fidcls="('"+fidcls.replace(",","','")+"')";
			if(DeliverorderDao.QueryExistsBySql("select fid from t_ord_deliverorder where fclosed=1 and fid in "+fidcls))
			{
				throw new DJException("请选择未关闭的配送单关闭!");
			}
			DeliverorderDao.ExecCloseDeliverorder(fidcls);
			result = JsonUtil.result(true, "关闭成功！", "", "");
		} catch (Exception e) {
			result = "{success:false,msg:'" + e.getMessage() + "'}";
		}
		reponse.setCharacterEncoding("utf-8");
		reponse.getWriter().write(result);
		return null;
	}
	@RequestMapping("/creatBatchTruckassembleByCondition")
	public String creatBatchTruckassembleByCondition(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		reponse.setCharacterEncoding("utf-8");
		String result = ""; 
		try {
			result = DeliverorderDao.ExecBatchCreatTruckassembleByCondition(request,reponse);
			reponse.getWriter().write(result);
		} catch (Exception e) {
			reponse.getWriter().write(
					JsonUtil.result(false, e.getMessage(), "", ""));
		}
		return null;
	}
	//批量自运
	public synchronized String doBatchCreatTruckassemble(HttpServletRequest request,
			HttpServletResponse reponse,List<HashMap<String,Object>> li) throws Exception {
		String result = "";
		String userid = ((Useronline) request.getSession().getAttribute("Useronline")).getFuserid();
		String sql = "select FISFILTER from t_sys_user where FID='"+userid+"' ";
		List<HashMap<String,Object>> list= TruckassembleDao.QueryBySql(sql);
		if (list.get(0).get("FISFILTER")!=null && list.get(0).get("FISFILTER").toString().equals("1")) {
			throw new DJException("管理用户不能自运发货！");
		}
		
		sql = "select fsupplierid from t_bd_usersupplier where fuserid='"+userid+"' ";
		list= TruckassembleDao.QueryBySql(sql);
		if (list.size()!=1) {
			throw new DJException("管理用户不能自运发货！");
		}
		List<Deliverorder> dlist = (List<Deliverorder>)request.getAttribute("Deliverorder");
		for(Deliverorder de : dlist){
			reponse.setCharacterEncoding("utf-8");
			String fids = "('"+de.getFid()+"')";
			int isrealType = 0;
			int realamount = 0;
			if("1".equals(request.getParameter("ftype"))){
				isrealType = 1 ;
				if(!DataUtil.positiveIntegerCheck(String.valueOf(de.getFamount()))){
					throw new DJException("实配数量必须大于0!");
				}
				realamount = de.getFamount();
			}
			
			sql = " select * from t_ord_deliverorder where ( ifnull(fmatched,0)=1 or ifnull(faudited,0)=0 or FimportEas=1) and fid in " + fids;
			List<HashMap<String, Object>> deliverordercls = DeliverorderDao.QueryBySql(sql);
			if(deliverordercls.size()>0)
			{
				throw new DJException("部分配送信息未审核、已经配货、已导入接口，不能配货！");
			}
			 sql = " select * from t_ord_deliverorder where ifnull(fmatched,0)=0 and fid in " + fids;
			 deliverordercls = DeliverorderDao.QueryBySql(sql);
			 
			HashMap<String, Object> params = new HashMap<String, Object>();
			params.put("deliverordercls", deliverordercls);
			params.put("userid", ((Useronline) request.getSession().getAttribute("Useronline")).getFuserid());
			params.put("fids", fids);
			params.put("isrealType", isrealType);
			params.put("frealamount", realamount);
			HashMap<String, Object> results = DeliverorderDao.ExecCreateTruckassemble(params,1);
			if(results.get("success")==Boolean.TRUE){
				li.add(results);
				result = JsonUtil.result(true,"自运发货成功!", "", li);
			}else{
				result = JsonUtil.result(false,"自运发货失败!", "", "");
			}
		
		}
		String id = "";
		for(int i = 0;i<li.size();i++){
			HashMap<String, Object> saledeliverID = li.get(i);
			id += (String)saledeliverID.get("saledeliverID");
			if(i<li.size()-1){
				id += ",";
			}
		}
		String results = result.substring(0,result.lastIndexOf("]")+1)+",\"saledeliverIDs\":\""+id+"\"}" ;
		return results;
		
	}
	@LogAction("纸板订单自动接收的方法")
	@RequestMapping("execReceiveBoardOrders")
	public String execReceiveBoardOrders(HttpServletRequest request,
			HttpServletResponse response) {
		DeliverorderDao.ExecReceiveBoardOrders();
		return null;
	}
	
	
	
	
	@RequestMapping("/selectFoutedDeliverordersMVByCustomer")
	public String selectFoutedDeliverordersMVByCustomer(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String userid = ((Useronline)request.getSession().getAttribute("Useronline")).getFuserid();
		
		String Defaultfilter = request.getParameter("Defaultfilter");
		String fsupplierid=request.getParameter("fsupplierid");
		String ftimetype="fouttime";
		if(Defaultfilter.contains("farrivetime")) ftimetype="farrivetime";
		String  timefilter=getFilterDateValue(request,ftimetype);//送达时间
		String sql="";
		if("fouttime".equals(ftimetype)){//选择出库时间查询
			
			sql=	"SELECT sd.fid sdentryid,fsupplierid, 0 fboxtype, sa.fnumber saleorderNumber,  p.fname cutpdtname, p.fspec fboxspec, '' fmaterialspec, sd.famount famount,DATE_FORMAT(sa.fprinttime, '%Y-%m-%d %H:%i') fouttime, sd.fdescription fdescription,p.funit forderunit  "
					+" FROM t_ftu_saledeliver  sa LEFT JOIN t_ftu_saledeliverentry sd ON sd.fparentid=sa.fid LEFT JOIN t_bd_custproduct  p ON sd.fftuproductid = p.fid  where 1=1 and sa.fstate<>1 and fsupplierid='"+fsupplierid+"'"+(StringUtils.isEmpty(timefilter)?"":("and sa.fprinttime "+timefilter))+saleOrderDao.QueryFilterByUser(request, "sa.fcustomer",null);
			sql+=" union ";
			sql+="select  st.fid sdentryid,st.fsupplierid,d.fboxtype ,d.fnumber saleorderNumber,_custpdtname cutpdtname,_spec fboxspec, _mspec fmaterialspec,st.famount famount,DATE_FORMAT(d.fouttime, '%Y-%m-%d %H:%i') fouttime, d.fdescription, cpdt.FORDERUNIT forderunit "+
					" FROM t_ord_deliverorder_mv d INNER JOIN t_tra_saledeliverentry st ON d.fid = st.`FDELIVERORDERID` LEFT JOIN t_bd_custproduct cpdt ON cpdt.fid = d.fcusproductid ";
			sql += "where 1 = 1 and st.fsupplierid='"+fsupplierid+"'"+(StringUtils.isEmpty(timefilter)?"":("and d.fouttime "+timefilter)) + saleOrderDao.QueryFilterByUser(request, "d.fcustomerid", null);
		}
		else//选择配送时间查询
		{
			sql="select  st.fid sdentryid,st.fsupplierid,d.fboxtype ,d.fnumber saleorderNumber,_custpdtname cutpdtname,_spec fboxspec, _mspec fmaterialspec,st.famount famount,DATE_FORMAT(d.farrivetime, '%Y-%m-%d %H:%i') fouttime, d.fdescription, cpdt.FORDERUNIT forderunit "+
				" FROM t_ord_deliverorder_mv d INNER JOIN t_tra_saledeliverentry st ON d.fid = st.`FDELIVERORDERID` LEFT JOIN t_bd_custproduct cpdt ON cpdt.fid = d.fcusproductid ";
			sql += "where 1 = 1 and st.fsupplierid='"+fsupplierid+"'"+(StringUtils.isEmpty(timefilter)?"":("and d.farrivetime "+timefilter)) + saleOrderDao.QueryFilterByUser(request, "d.fcustomerid", null);
		}
		
		String fidcls = baseSysDao.getFidclsBySql("SELECT fcustproductid ID FROM t_bd_userrelationcustp WHERE fuserid = '"+userid+"'");
		if(!"".equals(fidcls)){
			sql += " and cpdt.fid " + baseSysDao.getCondition(fidcls);
		}
		request.setAttribute("djsort", ftimetype+" DESC");
		try {
			ListResult result = DeliverorderDao.QueryFilterList(sql, request);
			response.getWriter().write(JsonUtil.result(true, "",result));
		} catch (DJException e) {
			response.getWriter().write(
					JsonUtil.result(false, e.getMessage(), "", ""));
		}
		return null;
	}
	
	
	@RequestMapping("/ExcelDeliverStatementList")
	public String ExcelDeliverStatementList(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
	
		try {
			String userid = ((Useronline)request.getSession().getAttribute("Useronline")).getFuserid();
			String header = request.getParameter("header");
			String str=new String(header.getBytes("ISO-8859-1"),"UTF-8");
			String sql = "select fcustomername from t_sys_user where fid='"+userid+"'";
			String fsupplier = baseSysDao.getStringValue(sql, "fcustomername");
			request.setAttribute("nolimit","true");
			
			String Defaultfilter = request.getParameter("Defaultfilter");
			String ftimetype="fouttime";
			if(Defaultfilter.contains("farrivetime")) ftimetype="farrivetime";
			String  timefilter=getFilterDateValue(request,ftimetype);//送达时间
			String fsupplierid=request.getParameter("fsupplierid");
			if("fouttime".equals(ftimetype)){//选择出库时间查询
				
				sql=	"SELECT sd.fid sdentryid,fsupplierid, '纸箱订单' fboxtype, sa.fnumber saleorderNumber,  p.fname cutpdtname, p.fspec fboxspec, '' fmaterialspec, sd.famount famount,DATE_FORMAT(sa.fprinttime, '%Y-%m-%d %H:%i') fouttime, sd.fdescription fdescription,p.funit forderunit  "
						+" FROM t_ftu_saledeliver  sa LEFT JOIN t_ftu_saledeliverentry sd ON sd.fparentid=sa.fid LEFT JOIN t_bd_custproduct  p ON sd.fftuproductid = p.fid  where 1=1 and sa.fstate<>1 and fsupplierid='"+fsupplierid+"'"+(StringUtils.isEmpty(timefilter)?"":("and sa.fprinttime "+timefilter))+saleOrderDao.QueryFilterByUser(request, "sa.fcustomer",null);
				sql+=" union ";
				sql+="select  st.fid sdentryid,st.fsupplierid,if(d.fboxtype=0,'纸箱订单','纸板订单') fboxtype ,d.fnumber saleorderNumber,_custpdtname cutpdtname,_spec fboxspec, _mspec fmaterialspec,st.famount famount,DATE_FORMAT(d.fouttime, '%Y-%m-%d %H:%i') fouttime, d.fdescription, cpdt.FORDERUNIT forderunit "+
						" FROM t_ord_deliverorder_mv d INNER JOIN t_tra_saledeliverentry st ON d.fid = st.`FDELIVERORDERID` LEFT JOIN t_bd_custproduct cpdt ON cpdt.fid = d.fcusproductid ";
				sql += "where 1 = 1 and st.fsupplierid='"+fsupplierid+"'"+(StringUtils.isEmpty(timefilter)?"":("and d.fouttime "+timefilter)) + saleOrderDao.QueryFilterByUser(request, "d.fcustomerid", null);
			}
			else//选择配送时间查询
			{
				sql="select  st.fid sdentryid,st.fsupplierid,if(d.fboxtype=0,'纸箱订单','纸板订单') fboxtype ,d.fnumber saleorderNumber,_custpdtname cutpdtname,_spec fboxspec, _mspec fmaterialspec,st.famount famount,DATE_FORMAT(d.farrivetime, '%Y-%m-%d %H:%i') fouttime, d.fdescription, cpdt.FORDERUNIT forderunit "+
					" FROM t_ord_deliverorder_mv d INNER JOIN t_tra_saledeliverentry st ON d.fid = st.`FDELIVERORDERID` LEFT JOIN t_bd_custproduct cpdt ON cpdt.fid = d.fcusproductid ";
				sql += "where 1 = 1 and st.fsupplierid='"+fsupplierid+"'"+(StringUtils.isEmpty(timefilter)?"":("and d.farrivetime "+timefilter)) + saleOrderDao.QueryFilterByUser(request, "d.fcustomerid", null);
			}
			
			String fidcls = baseSysDao.getFidclsBySql("SELECT fcustproductid ID FROM t_bd_userrelationcustp WHERE fuserid = '"+userid+"'");
			if(!"".equals(fidcls)){
				sql += " and cpdt.fid " + baseSysDao.getCondition(fidcls);
			}
			request.setAttribute("djsort", ftimetype+" DESC");
			
			
			ListResult result = MySimpleToolsZ.getMySimpleToolsZ().buildExcelListResult(sql, request, DeliverorderDao);
			
			List<String> order = MySimpleToolsZ.gainDataIndexList(request);
			
			deliverordertoexcel(reponse, result, order,str,fsupplier);
		} catch (DJException e) {
			reponse.getWriter().write(
					JsonUtil.result(false, e.getMessage(), "", ""));
		}

		return null;
	}
	
	public static void deliverordertoexcel(HttpServletResponse response, ListResult result,
			List<String> order,String header,String fsupplier) throws DJException {
		WritableWorkbook book = null;
		try {
			response.setContentType("multipart/form-data");
			response.setHeader("Content-Disposition",
					"attachment;fileName=" + URLEncoder.encode(header,"utf-8")+ ".xls");
			book = Workbook.createWorkbook(response.getOutputStream());
			WritableSheet sheet = book.createSheet("数据", 0);
			
			List<HashMap<String, Object>> list = result.getData();
			
			Set<String> columns = list.get(0).keySet();
			
			WritableFont bold = new WritableFont(WritableFont.createFont("宋体"),18,WritableFont.BOLD);
			WritableCellFormat headerFormat = new WritableCellFormat(
					bold);
			headerFormat.setAlignment(Alignment.CENTRE);
			sheet.mergeCells(0,0, columns.size(),0);
			sheet.addCell(new Label(0, 0, header,headerFormat));//标题
			
			
			WritableCellFormat colsStyle = new WritableCellFormat(new WritableFont(WritableFont.createFont("宋体"),14));
			colsStyle.setAlignment(Alignment.CENTRE);//列标题样式
			colsStyle.setBorder(jxl.format.Border.ALL,jxl.format.BorderLineStyle.THIN);
			
			WritableCellFormat contentStyle = new WritableCellFormat(new WritableFont(WritableFont.createFont("宋体"),12));
			contentStyle.setAlignment(Alignment.CENTRE);//内容样式
			contentStyle.setBorder(jxl.format.Border.ALL,jxl.format.BorderLineStyle.THIN);
			
			for (String name : columns) {
				sheet.addCell(new Label(0, 1, "序号",colsStyle));
				Label label = new Label(order.indexOf(name)+1, 1, name,colsStyle);
				sheet.addCell(label);
			}
			int rowNum = 2;
			int number = 0;
			BigDecimal famount = BigDecimal.ZERO;
			BigDecimal fprices = BigDecimal.ZERO;
			CellView cellView = new CellView();  
		    cellView.setAutosize(true); //设置自动大小
		    
			for (HashMap<String, Object> data : list) {
				sheet.addCell(new Label(0, rowNum,String.valueOf(++number),contentStyle));
				for (String name : columns) {
					Label label = new Label(order.indexOf(name)+1, rowNum,
							data.get(name) == null ? "" : data.get(name)
									.toString(),contentStyle);
					
					
					if("出库/配送时间".equals(name)||"纸箱规格".equals(name)||"下料规格".equals(name)||"收货单号".equals(name)||"订单类型".equals(name)){
						sheet.setColumnView(order.indexOf(name)+1, 20);
						sheet.addCell(label);
					}else if("收货数量".equals(name)){
						sheet.setColumnView(order.indexOf(name)+1, 13);
						sheet.addCell(new jxl.write.Number(order.indexOf(name)+1, rowNum,StringUtils.isEmpty(data.get(name))?0:Double.valueOf(data.get(name).toString()),contentStyle));
					}else if("单位".equals(name)){
						sheet.setColumnView(order.indexOf(name)+1, 10);
						sheet.addCell(label);
					}else if("产品名称".equals(name)||"备注".equals(name)){
						sheet.setColumnView(order.indexOf(name)+1,35);
						sheet.addCell(label);
					}else{
						sheet.setColumnView(order.indexOf(name)+1, cellView);
						sheet.addCell(label);
					}
				}
				rowNum++;
			}
			WritableCellFormat totalStyle = new WritableCellFormat(new WritableFont(WritableFont.createFont("宋体"),14,WritableFont.BOLD));
			totalStyle.setAlignment(Alignment.CENTRE);
			sheet.mergeCells(columns.size()-1,rowNum+2, columns.size(),rowNum+2);
			sheet.addCell(new Label(columns.size()-1, rowNum+2,fsupplier,totalStyle));
			book.write();
		} catch (IOException | WriteException e) {
			throw new DJException("导出失败!");
		} finally {
			try {
				book.close();
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
	}
	
	
	
	//批量自运，新   自运只针对一个制造商
		public synchronized String doBatchCreatTruckassembleNew(HttpServletRequest request,
				HttpServletResponse reponse) throws Exception {
		String result = "";
		reponse.setCharacterEncoding("utf-8");
		String userid = ((Useronline) request.getSession().getAttribute(
				"Useronline")).getFuserid();
		String sql = "select FISFILTER from t_sys_user where FID='" + userid
				+ "' ";
		List<HashMap<String, Object>> list = TruckassembleDao.QueryBySql(sql);
		if (list.get(0).get("FISFILTER") != null
				&& list.get(0).get("FISFILTER").toString().equals("1")) {
			throw new DJException("管理用户不能自运发货！");
		}

		sql = "select fsupplierid from t_bd_usersupplier where fuserid='"
				+ userid + "' ";
		list = TruckassembleDao.QueryBySql(sql);
		if (list.size() != 1) {
			throw new DJException("管理用户不能自运发货！");
		}
		String fsupplierid = list.get(0).get("fsupplierid").toString();
		List<Deliverorder> dlist = (List<Deliverorder>) request.getAttribute("Deliverorder");// 批量送
		HashMap<String, Integer> deleiverordermap = new HashMap<String, Integer>();
		StringBuffer deliversid = new StringBuffer("(");
		if(dlist!=null){
			for (Deliverorder de : dlist) {
				if (!DataUtil.positiveIntegerCheck(String.valueOf(de.getFamount()))) {
					throw new DJException("实配数量必须大于0!");
				}
				if(deleiverordermap.containsKey(de.getFid()))
				{
					deleiverordermap.put(de.getFid(), deleiverordermap.get(de.getFid())+de.getFamount());
				}
				else
				{
					deliversid.append("'" + de.getFid() + "',");
					deleiverordermap.put(de.getFid(), de.getFamount());
				}
				
			}
			deliversid.deleteCharAt(deliversid.length()-1).append(")");
		}


		if (deleiverordermap.isEmpty())// 不是通过批量选择，是通过全选
		{
			deliversid.append("'"+request.getParameter("fidcls").replace(",", "','") + "')");// 全选
		}

		sql = " select fid from t_ord_deliverorder where ( fmatched=1 or faudited=0 or FimportEas=1) and fid in "+ deliversid;
		List<HashMap<String, Object>> deliverordercls = DeliverorderDao
				.QueryBySql(sql);
		if (deliverordercls.size() > 0) {
			throw new DJException("部分配送信息未审核、已经配货、已导入接口，不能配货！");
		}
		sql = " select fid from t_ord_deliverorder where fsupplierid<>'"
				+ fsupplierid + "' and fid in " + deliversid;
		deliverordercls = DeliverorderDao.QueryBySql(sql);
		if (deliverordercls.size() > 0) {
			throw new DJException("不能选择其他制造商的配送");
		}
		sql = " select fid,fproductid,flinkman,flinkphone,famount,fassembleQty,fsupplierId,faddressid,fcustomerid,fdescription,fplanid,ftype from t_ord_deliverorder where fmatched=0 and fid in "
				+ deliversid + " order by fcustomerid, faddressid";
		deliverordercls = DeliverorderDao.QueryBySql(sql);

		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("deliverordercls", deliverordercls);
		params.put("userid", userid);
		params.put("fids", deliversid);
		params.put("deleiverordermap", deleiverordermap);
		HashMap<String, Object> results = DeliverorderDao
				.ExecCreateTruckassembleNew(params);// 自动提货发车
		if (results.get("success") == Boolean.TRUE) {
			result = JsonUtil.result(true, "自运发货成功!", "", "");
		} else {
			result = JsonUtil.result(false, "自运发货失败!", "", "");
		}
		result = result.substring(0, result.lastIndexOf("}"))+ ",\"saledeliverIDs\":\"" + results.get("saledeliverid")+ "\"}";
		return result;

		}
		@RequestMapping("/creatBatchTruckassembleByConditionNew")
		public String creatBatchTruckassembleByConditionNew(HttpServletRequest request,
				HttpServletResponse reponse) throws IOException {
			reponse.setCharacterEncoding("utf-8");
			String result = ""; 
			try {
//				result = DeliverorderDao.ExecBatchCreatTruckassembleByCondition(request,reponse);				
				List list = new ArrayList();
				if (Integer.parseInt((String) syscfgDao.ExecGainCfgByFkey(request, "autoDepart").getData().get(0).get("fvalue")) == 1) {
					//自运，发车 合为一个方法
					result=this.doBatchCreatTruckassembleNew(request, reponse);
					
				}else {
					//自运
					result = this.doBatchCreatTruckassemble(request, reponse,list);
					
				}
				reponse.getWriter().write(result);
			} catch (Exception e) {
				reponse.getWriter().write(
						JsonUtil.result(false, e.getMessage(), "", ""));
			}
			return null;
		}
	
}

