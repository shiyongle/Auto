package Com.Controller.traffic;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import Com.Dao.Inv.IOutWarehouseDao;
import Com.Dao.Inv.IStorebalanceDao;
import Com.Dao.Inv.IproductindetailDao;
import Com.Dao.order.IDeliversDao;
import Com.Dao.order.IProductPlanDao;
import Com.Dao.order.ISaleOrderDao;
import Com.Dao.traffic.ISaledeliverDao;
import Com.Entity.Inv.Storebalance;
import Com.Entity.System.Productcycle;
import Com.Entity.System.Useronline;
import Com.Entity.order.ProductPlan;
import Com.Entity.order.Saleorder;
import Com.Entity.traffic.Saledeliver;
import Com.Entity.traffic.Saledeliverentry;

@Controller
public class SaledeliverController {
	Logger log = LoggerFactory.getLogger(SaledeliverController.class);
	@Resource
	private ISaledeliverDao SaledeliverDao;
	

	@RequestMapping(value = "/SaveSaledeliver")
	public String SaveSaledeliver(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
//		String result = "";
//		DateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//		Saledeliver sdinfo = null;
//		try {
//			String userid = ((Useronline) request.getSession().getAttribute(
//					"Useronline")).getFuserid();
//			String fid = request.getParameter("fid");
//			if (fid != null && !"".equals(fid)) {
//				sdinfo = SaledeliverDao.Query(fid);
//				if(sdinfo!=null && sdinfo.getFaudited()==1){
//					reponse.setCharacterEncoding("utf-8");
//					reponse.getWriter().write(JsonUtil.result(false,"已审核不能修改!", "", ""));
//					return null;
//				}
//			} else {
//				sdinfo = new Saledeliver();
//				sdinfo.setFid(fid);
//				sdinfo.setFcreatorid(userid);
//				sdinfo.setFcreatetime(new Date());
//				sdinfo.setFbizdate(new Date());
//				sdinfo.setFnumber(SaledeliverDao.getFnumber("t_tra_saledeliver", "FH",4));
//			}
//		
//			sdinfo.setFlastupdatetime(new Date());
//			sdinfo.setFlastupdateuserid(userid);
//			sdinfo.setFtruckid(request.getParameter("ftruckid"));
//			sdinfo.setFaddressid(request.getParameter("faddressid"));
//			sdinfo.setFcustomerid(request.getParameter("fcustomerid"));
//			sdinfo.setFauditorid(request.getParameter("fauditorid"));
//			sdinfo.setFauditdate("".equals(request.getParameter("fauditdate"))?null:f.parse(request.getParameter("fauditdate").replace("T"," ")));
//			sdinfo.setFaudited(0);
//			sdinfo.setFassembleid(null);			
////			HashMap<String, Object> params = SaledeliverDao.ExecSave(sdinfo);
//			ArrayList tnlist = (ArrayList)request.getAttribute("Saledeliverentry");
//			HashMap<String, Object> params = new HashMap<String, Object>();
//			params.put("Tinfo", sdinfo);
//			params.put("tnlist", tnlist);
//			SaledeliverDao.ExecSaledeliver(params,1);
//			
//			if (params.get("success") == Boolean.TRUE) {
//				result = JsonUtil.result(true,"保存成功!", "", "");
//			} else {
//				result = JsonUtil.result(false,"保存失败!", "", "");
//			}
//		} catch (Exception e) {
//			result = JsonUtil.result(false,e.getMessage(), "", "");
//		}
		reponse.setCharacterEncoding("utf-8");
		reponse.getWriter().write(JsonUtil.result(false,"不能修改", "", ""));

		return null;

	}
	@RequestMapping(value = "/getCusSaledeliverList")
	public String getCusSaledeliverList(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		reponse.setCharacterEncoding("utf-8");
		try {

			String fid = request.getParameter("fid"); 
			Saledeliver deliverinfo= SaledeliverDao.Query(fid);
			if(deliverinfo.getFaudited()==0)
			{
				reponse.getWriter().write(
						JsonUtil.result(false, "未发车，不能打印！", "", ""));
				return null;
			}
			
			//String sql = " SELECT cp.fname proName, sde.famount / da.fdelivernum * da.fdeliverapplynum count FROM t_tra_saledeliverentry sde left join t_tra_saledeliver sd on sde.FPARENTID = sd.fid left join t_ord_deliverorder dod on sde.FDELIVERORDERID = dod.fid left join t_ord_deliverratio da on da.fdeliverid = dod.fdeliversID left join t_ord_deliverapply dap on dap.fid = da.fdeliverappid left join t_bd_custproduct cp on cp.fid = dap.fcusproductid where sd.fid='%s' ";
			String sql = "  SELECT cp.fnumber productNumber,tbv.fname as customerName,dap.fordernumber as purchaseOrderNo, sd.fcreatetime as createTime ,tss.fnumber as supplierNumber,cp.fname proName, sde.famount / da.fdelivernum * da.fdeliverapplynum count, tss.fname supplierName  FROM t_tra_saledeliverentry sde  left join t_tra_saledeliver sd on sde.FPARENTID = sd.fid  left join t_ord_deliverorder dod on sde.FDELIVERORDERID = dod.fid  left join t_ord_deliverratio da on da.fdeliverid = dod.fdeliversID  left join t_ord_deliverapply dap on dap.fid = da.fdeliverappid  left join t_bd_custproduct cp on cp.fid = dap.fcusproductid  left join t_sys_supplier tss on tss.fid = sd.FSUPPLIER  left join t_bd_customer tbv on tbv.fid = sd.FCUSTOMERID  where sd.fid='%s' ";
			
			sql = String.format(sql, fid);  
			
			ListResult result = SaledeliverDao.QueryFilterList(sql, request);
			reponse.getWriter().write(JsonUtil.result(true, "", result));
		} catch (DJException e) { 
			// TODO Auto-generated catch block
			reponse.getWriter().write(
					JsonUtil.result(false, e.getMessage(), "", ""));
		}

		return null;

	}
	/**
	 * 多条打印用
	 *
	 * @param request
	 * @param reponse
	 * @return
	 * @throws IOException
	 *
	 * @date 2014-1-7 下午2:18:45  (ZJZ)
	 */
	@RequestMapping(value = "/getCusSaledeliverListByIds")
	public String getCusSaledeliverListByIds(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		reponse.setCharacterEncoding("utf-8");
		try {

			String fids = request.getParameter("fids"); 
			fids="('"+fids.replace(",", "','")+"')";
			
			String sql = " SELECT tbv.fname as customerName, sd.fid as idSd , cp.fname proName, sde.famount / da.fdelivernum * da.fdeliverapplynum count, tss.fname supplierName, sd.FNUMBER receiptNo, ttta.FNUMBER as billLadingNo,sd.FTRUCKID as vehicle,dod.faddress as deliveryAddress FROM t_tra_saledeliverentry sde left join t_tra_saledeliver sd on sde.FPARENTID = sd.fid left join t_ord_deliverorder dod on sde.FDELIVERORDERID = dod.fid left join t_ord_deliverratio da on da.fdeliverid = dod.fdeliversID left join t_ord_deliverapply dap on dap.fid = da.fdeliverappid left join t_bd_custproduct cp on cp.fid = dap.fcusproductid left join t_sys_supplier tss on tss.fid = sd.FSUPPLIER left join t_tra_truckassemble ttta on sd.FASSEMBLEID = ttta.fid left join t_bd_customer tbv on tbv.fid = sd.FCUSTOMERID where sd.fid in %s order by sd.fid ";
			
			sql = String.format(sql, fids);
			
			ListResult result = SaledeliverDao.QueryFilterList(sql, request);
			reponse.getWriter().write(JsonUtil.result(true, "", result));
		} catch (DJException e) {
			// TODO Auto-generated catch block
			reponse.getWriter().write( 
					JsonUtil.result(false, e.getMessage(), "", ""));
		}

		return null;

	}
	
	/**
	 * 多条打印用2，@task，2014/02/26 14:38 出库单（送货单）格式调整
	 *
	 * @param request
	 * @param reponse
	 * @return
	 * @throws IOException
	 *
	 * @date 2014-2-26 下午3:39:06  (ZJZ)
	 */
	@RequestMapping(value = "/getCusSaledeliverListByIds2")
	public String getCusSaledeliverListByIds2(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		reponse.setCharacterEncoding("utf-8");
		try {

			String fids = request.getParameter("fids"); 
			fids="('"+fids.replace(",", "','")+"')";
			
			String sql = " SELECT tbv.fname as customerName, sd.fcreatetime as createTime,dap.fordernumber as purchaseOrderNo,tss.fnumber as supplierNumber, sd.fid as idSd ,  CASE dod.fboxtype WHEN 1 THEN p.fname ELSE cp.fname END proName,CASE dod.fboxtype WHEN 1 THEN p.fnumber ELSE cp.fnumber END productNumber,dap.fdescription remark ,CASE dod.fboxtype WHEN 1 then sde.famount ELSE sde.famount / da.fdelivernum * da.fdeliverapplynum END count, tss.fname supplierName, sd.FNUMBER receiptNo, ttta.FNUMBER as billLadingNo,sd.FTRUCKID as vehicle,CONCAT(dod.faddress, ',联系人：', dod.flinkman, case when ifnull(dod. flinkphone,'') = '' then '' else CONCAT(', 电话:',dod. flinkphone) end ) as deliveryAddress  FROM t_tra_saledeliverentry sde  left join t_tra_saledeliver sd on sde.FPARENTID = sd.fid  left join t_ord_deliverorder dod on sde.FDELIVERORDERID = dod.fid  left join t_ord_deliverratio da on da.fdeliverid = dod.fdeliversID  left join t_ord_deliverapply dap on dap.fid = da.fdeliverappid  left join t_bd_custproduct cp on cp.fid = dap.fcusproductid  left join t_sys_supplier tss on tss.fid = sd.FSUPPLIER  left join t_tra_truckassemble ttta on sd.FASSEMBLEID = ttta.fid  left join t_bd_customer tbv on tbv.fid = sd.FCUSTOMERID LEFT JOIN t_pdt_productdef p ON p.fid=dod.fproductid where sd.fid in %s order by sd.fid ";
			
			sql = String.format(sql, fids);
			
			ListResult result = SaledeliverDao.QueryFilterList(sql, request);
			
			
			reponse.getWriter().write(JsonUtil.result(true, "", result));
		} catch (DJException e) {
			// TODO Auto-generated catch block
			reponse.getWriter().write(
					JsonUtil.result(false, e.getMessage(), "", ""));
		}

		return null;

	}
	
	@RequestMapping(value = "/DelSaledeliverList")
	public String DelSaledeliverList(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {

//		String result = "";
//		String fidcls = request.getParameter("fidcls");
//		try {
//			String sql = "select fid from t_tra_Saledeliver t where faudited=1 and fid in " + fidcls;
//			List<HashMap<String,Object>> list= SaledeliverDao.QueryBySql(sql);
//			if (list.size()>0) {
//				throw new DJException("不能删除已审核出库单！");
//			}
//			
//			//因为同时要删除分录所以放到Dao内进行事务保护;
//			HashMap<String, Object> params = new HashMap<String, Object>();
//			params.put("fidcls", fidcls);
//			SaledeliverDao.ExecSaledeliver(params,2);
//			
//			result = JsonUtil.result(true,"删除成功!", "", "");
//			reponse.setCharacterEncoding("utf-8");
//		} catch (Exception e) {
//			result = JsonUtil.result(false,e.getMessage(), "", "");
//			log.error("DelUserList error", e);
//		}
		reponse.setCharacterEncoding("utf-8");
		reponse.getWriter().write( JsonUtil.result(false,"不能删除出库单", "", ""));
		return null;

	}
	
	@RequestMapping(value = "/GetSaledeliverList")
	public String GetSaledeliverList(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		reponse.setCharacterEncoding("utf-8");
		try {
			String sql = "select sd.fid,sd.fnumber,sd.fbizdate,sd.fcreatorid,u.fname fcreator,sd.fcreatetime,sd.flastupdateuserid,u2.fname fupdateuser,sd.flastupdatetime,sd.ftruckid,sd.fassembleid,t.fnumber fassemblenum,sd.fcustomerid,c.fname fcustname,sd.faddressid,a.fdetailaddress faddress,sd.fauditorid,ad.fname fauditor,sd.fauditdate,sd.faudited,sd.ftype  from t_tra_saledeliver sd left join t_sys_user u on  u.fid=sd.fcreatorid left join t_sys_user u2 on u2.fid=sd.flastupdateuserid left join t_sys_user ad on ad.fid=sd.fauditorid left join t_bd_customer c on c.fid=sd.fcustomerid left join t_tra_truckassemble t on t.fid=sd.fassembleid left join t_bd_address a on a.fid=sd.faddressid " +
//			String sql = "SELECT sd.fnumber,d.`fnumber` delivernumber,t.fnumber fassemblenum,c.fname fcustname,p.`FNAME` productname,tt.fproductspec fspec,tt.`FAMOUNT` famount,d.`farrivetime`,a.fdetailaddress faddress,sd.ftype ,sd.fbizdate,sd.fid,sd.fcreatorid,u.fname fcreator,"+
//						" sd.fcreatetime,sd.flastupdateuserid,u2.fname fupdateuser,sd.flastupdatetime,sd.ftruckid,sd.fassembleid,sd.fcustomerid,sd.faddressid,sd.fauditorid,ad.fname fauditor,sd.fauditdate,sd.faudited FROM t_tra_saledeliverentry tt LEFT JOIN t_tra_saledeliver sd"+ 
//						" ON tt.`FPARENTID`=sd.`FID` LEFT JOIN t_ord_deliverorder d ON d.fid = tt.fdeliverorderid  LEFT JOIN t_sys_user u ON u.fid = sd.fcreatorid LEFT JOIN t_sys_user u2 ON u2.fid = sd.flastupdateuserid LEFT JOIN t_sys_user ad ON ad.fid = sd.fauditorid "+
//						" LEFT JOIN t_bd_customer c ON c.fid = sd.fcustomerid LEFT JOIN t_bd_address a ON a.fid = sd.faddressid LEFT JOIN t_tra_truckassemble t ON t.fid = sd.fassembleid LEFT JOIN t_pdt_productdef p ON p.fid = tt.fproductid "+
					"where 1=1 "+SaledeliverDao.QueryFilterByUser(request, null, "sd.fsupplier");
//			request.setAttribute("djgroup","sd.FNUMBER,tt.fid");
			ListResult result = SaledeliverDao.QueryFilterList(sql, request);
			reponse.getWriter().write(JsonUtil.result(true, "", result));
		} catch (DJException e) {
			// TODO Auto-generated catch block
			reponse.getWriter().write(
					JsonUtil.result(false, e.getMessage(), "", ""));
		}

		return null;

	}
	
	@RequestMapping(value = "/GetSaledeliverInfo")
	public String GetSaledeliverInfo(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {

		reponse.setCharacterEncoding("utf-8");
		try {
			String sql = "select sd.fid,sd.fnumber,sd.fbizdate,sd.fcreatorid,u.fname fcreator,sd.fcreatetime,sd.flastupdateuserid,u2.fname fupdateuser,sd.flastupdatetime,sd.ftruckid,sd.fassembleid,t.fnumber fassemblenum,sd.fcustomerid,c.fname fcustname,sd.faddressid,a.fname faddress,sd.fauditorid,ad.fname fauditor,sd.fauditdate,sd.faudited,sd.ftype,IF(sd.ftype=0,'自运发货','协同发货') ftypetext FROM t_tra_saledeliver sd left join t_sys_user u on  u.fid=sd.fcreatorid left join t_sys_user u2 on u2.fid=sd.flastupdateuserid left join t_sys_user ad on ad.fid=sd.fauditorid left join t_bd_customer c on c.fid=sd.fcustomerid left join t_tra_truckassemble t on t.fid=sd.fassembleid left join t_bd_address a on a.fid=sd.faddressid " +
					"where 1=1 and sd.fid='"+request.getParameter("fid")+"'"+SaledeliverDao.QueryFilterByUser(request, "c.fid", "sd.fsupplier");
			ListResult sList = SaledeliverDao.QueryFilterList(sql,request);
			reponse.getWriter().write(JsonUtil.result(true, "", sList));
			
		} catch (Exception e) {
			reponse.getWriter().write(
					JsonUtil.result(false, e.getMessage(), "", ""));
		}
		return null;

	}
	
	@RequestMapping(value = "/GetSaledeliverentry")
	public String GetSaledeliverentry(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		reponse.setCharacterEncoding("utf-8");
		try {
			String sql = "SELECT dl.fpcmordernumber,t.fid,t.fseq,t.fparentid,t.fsaleorderid,t.fmaterialspec,p.fnumber salenumber,dl.fboxtype,fdeliverorderid,dl.fnumber delivernumber,t.fsupplierid,s.fname supplier,t.fproductid,d.fname product,d.fnumber productnumber,"
		+ "fproductspec,t.famount,freceiveaddress,ra.fdetailaddress raddress,freceiver,freceiverphone,fdeliveryaddress,da.fdetailaddress daddress,fdelivery,fdeliveryphone,fremark,frealamount,ifnull(freceiptdate,'') freceiptdate,fisreceipts,ifnull(u.fname,'') freceiptor,cu.fname fcustname " +
		"FROM t_tra_saledeliverentry t left join t_ord_productplan p on p.fid=t.fsaleorderid " +
		"left join t_ord_deliverorder dl on dl.fid=t.fdeliverorderid left join t_pdt_productdef d on d.fid=t.fproductid " +
		"left join t_sys_supplier s on s.fid=t.fsupplierid left join t_bd_address ra on ra.fid=t.freceiveaddress " +
		"left join t_bd_address da on da.fid=t.fdeliveryaddress " +
		" left join t_sys_user u on u.fid=t.freceiptorid " +
		"LEFT JOIN t_bd_customer cu ON cu.fid = dl.fcustomerid "+
		"where 1=1 " ;
			sql=sql+SaledeliverDao.QueryFilterByUser(request, "dl.fcustomerid", "t.fsupplierid");
			//fsupplierid
//		+ "where t.fparentid='" + JSONArray.fromObject(request.getParameter("Defaultfilter")).getJSONObject(0).getString("value") + "'";
//			ListResult result = new ListResult();
//			result.setData(TruckassembleDao.QueryBySql(sql));
//			result.setTotal("");
			ListResult result = SaledeliverDao.QueryFilterList(sql, request);
			reponse.getWriter().write(JsonUtil.result(true, "", result));
		} catch (DJException e) {
			// TODO Auto-generated catch block
			reponse.getWriter().write(
					JsonUtil.result(false, e.getMessage(), "", ""));
		}

		return null;

	}
	
	@RequestMapping(value = "/SaledelivertoExcel")
	public String SaledelivertoExcel(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		try {
			String sql = "SELECT ta.fnumber 出库单编号,t.fseq 出库单分录,p.fnumber  制造商订单号,dl.fnumber 配送单号,s.fname 供应商,d.fname 产品名称,"
					+ "fproductspec,t.famount,freceiveaddress,ra.fname raddress,freceiver,freceiverphone,fdeliveryaddress,da.fdetailaddress daddress,fdelivery,fdeliveryphone,fremark " +
					"FROM t_tra_saledeliver ta left join t_tra_saledeliverentry t on ta.fid=t.fparentid left join t_ord_productplan p on p.fid=t.fsaleorderid " +
					"left join t_ord_deliverorder dl on dl.fid=t.fdeliverorderid left join t_pdt_productdef d on d.fid=t.fproductid " +
					"left join t_sys_supplier s on s.fid=t.fsupplierid left join t_bd_address ra on ra.fid=t.freceiveaddress " +
					"left join t_bd_address da on da.fid=t.fdeliveryaddress where 1=1 " ;
			        sql=sql+SaledeliverDao.QueryFilterByUser(request,"dl.fcustomerid", "t.fsupplierid");
//					+ "where t.fparentid='" + JSONArray.fromObject(request.getParameter("Defaultfilter")).getJSONObject(0).getString("value") + "'";
//						ListResult result = new ListResult();
//						result.setData(TruckassembleDao.QueryBySql(sql));
//						result.setTotal("");
						ListResult result = SaledeliverDao.QueryFilterList(sql, request);
			ExcelUtil.toexcel(reponse,result);
		} catch (DJException e) {
			// TODO Auto-generated catch block
			reponse.getWriter().write(
					JsonUtil.result(false, e.getMessage(), "", ""));
		}

		return null;

	}
	
	@RequestMapping(value = "/auditSaledeliver")
	public String auditSaledeliver(HttpServletRequest request,
			HttpServletResponse reponse) throws Exception {
		String result = "";
//		Saleorder pinfo = null;
//		try {
//			String userid = ((Useronline) request.getSession().getAttribute(
//					"Useronline")).getFuserid();
////			String orderids = request.getParameter("orderids");
////			String sql = "select 1 from t_tra_saledeliver where faudited=0 and forderid in " + orderids;
////			List<HashMap<String,Object>> data= SaledeliverDao.QueryBySql(sql);
////			if (data.size()>0)
////			{
//			String fid = request.getParameter("fid");
//			String sql = "update t_tra_saledeliver set faudited=1,fauditorid='"
//							+ userid
//							+ "',faudittime=now() where faudited=0 and fid = '"
//							+ fid +"'";
//					SaledeliverDao.ExecBySql(sql);
////			}
////			else{
////				throw new DJException("没有需要审核的订单,无需审核！");
////			}
////			}else {
////				throw new DJException("未保存不能审核！");
////			}
//			result = JsonUtil.result(true,"审核成功！", "", "");
////			HashMap<String, Object> params = SaledeliverDao.ExecSave(pinfo);
////			if (params.get("success") == Boolean.TRUE) {
////				result = JsonUtil.result(true,"审核成功!", "", "");
////			} else {
////				result = JsonUtil.result(false,"审核失败!", "", "");
////			}
//		} catch (Exception e) {
//			result = "{success:false,msg:'" + e.getMessage() + "'}";
//		}
		reponse.setCharacterEncoding("utf-8");
		reponse.getWriter().write(JsonUtil.result(false,"已经审核!", "", ""));
		return null;
	}
	
	@RequestMapping(value = "/unauditSaledeliver")
	public String unauditSaledeliver(HttpServletRequest request,
			HttpServletResponse reponse) throws Exception {
		String result = "";
//		Saleorder pinfo = null;
//		try {
//			String userid = ((Useronline) request.getSession().getAttribute(
//					"Useronline")).getFuserid();
//			String fid = request.getParameter("fid");
////			String orderids = request.getParameter("orderids");
////			String sql = "select 1 from t_tra_saledeliver where fallot = 1 and forderid in " + orderids;
////			List<HashMap<String,Object>> data= SaledeliverDao.QueryBySql(sql);
////			if (data.size()>0)
////			{
////				throw new DJException("不能选择已分配的订单反审核！");
////			}
////			else{
//			String sql = "update t_tra_saledeliver set faudited=0,fauditorid=null"
//						+ ",faudittime=null where faudited=1 and fid = "
//						+ fid;
//				SaledeliverDao.ExecBySql(sql);
////			}
//		
//			result = JsonUtil.result(true,"反审核成功！", "", "");
//		} catch (Exception e) {
//			result = JsonUtil.result(false,e.getMessage(), "", "");
//		}
		reponse.setCharacterEncoding("utf-8");
		reponse.getWriter().write(JsonUtil.result(false,"未审核成功！", "", ""));
		return null;
	}
	
	
	@RequestMapping(value = "/updateSaledeliverentry")
	public String updateSaledeliverentry(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {

		reponse.setCharacterEncoding("utf-8");
		try {
			String userid = ((Useronline) request.getSession().getAttribute(
					"Useronline")).getFuserid();
			String realamount = request.getParameter("frealamount");
			if(!DataUtil.positiveIntegerCheck(realamount)){
				throw new DJException("实收数量格式不正确，请填写正整数类型");
			}
			BigDecimal famount= new BigDecimal(realamount);
			String fid=request.getParameter("fid");
			List list=SaledeliverDao.QueryByHql("from Saledeliverentry where fid='"+fid+"'");
			if(list.size()==0){
				throw new DJException("主键不存在！");
			}
			Saledeliverentry entry=(Saledeliverentry)list.get(0);
			entry.setFrealamount(famount);
			entry.setFisreceipts(1);
			entry.setFreceiptdate(new Date());
			entry.setFreceiptorid(userid);
			SaledeliverDao.UpdateSaledeliverentry(entry);
			reponse.getWriter().write(JsonUtil.result(true, "成功", "",""));
			
		} catch (Exception e) {
			reponse.getWriter().write(
					JsonUtil.result(false, e.getMessage(), "", ""));
		}
		return null;

	}
	
	@RequestMapping(value = "/GetTransitdetails")
	public String GetTransitdetails(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		reponse.setCharacterEncoding("utf-8");
		try {
			String sql =" select fid ,cname,fname,fspec,famount,trafficunit,fouttime,supplier from ( SELECT sp.fname supplier,sde.fid,cp.fname cname,f.fname fname ,cp.fspec , sde.famount ,case  when sd.ftype=1 then '东经' else tss.fname end trafficunit,tr.fouttime FROM t_tra_saledeliverentry sde "+
					" left join t_tra_saledeliver sd on sde.FPARENTID = sd.fid left join t_ord_deliverorder dod on sde.FDELIVERORDERID = dod.fid left join t_ord_deliverratio da on da.fdeliverid = dod.fdeliversID  "+
					" left join t_ord_deliverapply dap on dap.fid = da.fdeliverappid left join t_bd_custproduct cp on cp.fid = dap.fcusproductid left join t_sys_supplier tss on tss.fid = sd.FSUPPLIER left join t_tra_truckassembleentry tr on sde.fassembleentryid =tr.fid "+
					" left join t_pdt_productdef f on f.fid=sde.fproductid left join t_sys_supplier sp on sp.fid=dod.fsupplierId where tr.fouted=1 order by fouttime desc) tf  where 1=1 ";
			ListResult result = SaledeliverDao.QueryFilterList(sql, request);
			reponse.getWriter().write(JsonUtil.result(true, "", result));
		} catch (DJException e) {
			// TODO Auto-generated catch block
			reponse.getWriter().write(
					JsonUtil.result(false, e.getMessage(), "", ""));
		}

		return null;

	}
	
	
	@RequestMapping(value = "/TransitDetailsRpttoexcel")
	public String TransitDetailsRpttoexcel(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		reponse.setCharacterEncoding("utf-8");
		try {
			request.setAttribute("nolimit", 0);
			String sql =" select cname 客户产品名称,fname 产品名称,fspec 规格,famount 数量,trafficunit 运输单位,fouttime 发货时间,supplier 制造商 from ( SELECT sp.fname supplier,sde.fid,cp.fname cname,f.fname fname ,cp.fspec , sde.famount ,case  when sd.ftype=1 then '东经' else tss.fname end trafficunit,tr.fouttime FROM t_tra_saledeliverentry sde "+
					" left join t_tra_saledeliver sd on sde.FPARENTID = sd.fid left join t_ord_deliverorder dod on sde.FDELIVERORDERID = dod.fid left join t_ord_deliverratio da on da.fdeliverid = dod.fdeliversID  "+
					" left join t_ord_deliverapply dap on dap.fid = da.fdeliverappid left join t_bd_custproduct cp on cp.fid = dap.fcusproductid left join t_sys_supplier tss on tss.fid = sd.FSUPPLIER left join t_tra_truckassembleentry tr on sde.fassembleentryid =tr.fid "+
					" left join t_pdt_productdef f on f.fid=sde.fproductid left join t_sys_supplier sp on sp.fid=dod.fsupplierId where tr.fouted=1 order by fouttime desc) tf  where 1=1 ";
			ListResult result = SaledeliverDao.QueryFilterList(sql, request);
//			reponse.getWriter().write(JsonUtil.result(true, "", result));
			ExcelUtil.toexcel(reponse,result);
		} catch (DJException e) {
			// TODO Auto-generated catch block
			reponse.getWriter().write(
					JsonUtil.result(false, e.getMessage(), "", ""));
		}

		return null;

	}
}