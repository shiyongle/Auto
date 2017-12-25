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

import net.sf.json.JSONArray;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import Com.Base.Util.DJException;
import Com.Base.Util.ExcelUtil;
import Com.Base.Util.JsonUtil;
import Com.Base.Util.ListResult;
import Com.Base.Util.ServerContext;
import Com.Base.Util.params;
import Com.Dao.Inv.IOutWarehouseDao;
import Com.Dao.Inv.IStorebalanceDao;
import Com.Dao.Inv.IproductindetailDao;
import Com.Dao.System.IProductdefDao;
import Com.Dao.System.ISupplierDao;
import Com.Dao.order.IDeliverorderDao;
import Com.Dao.order.IDeliversDao;
import Com.Dao.order.IProductPlanDao;
import Com.Dao.order.ISaleOrderDao;
import Com.Dao.traffic.ISaledeliverDao;
import Com.Dao.traffic.ITruckassembleDao;
import Com.Dao.traffic.SaledeliverDao;
import Com.Entity.Inv.Storebalance;
import Com.Entity.System.Productcycle;
import Com.Entity.System.Productdef;
import Com.Entity.System.Supplier;
import Com.Entity.System.Useronline;
import Com.Entity.order.ProductPlan;
import Com.Entity.order.Saleorder;
import Com.Entity.traffic.Saledeliver;
import Com.Entity.traffic.Saledeliverentry;
import Com.Entity.traffic.Truckassemble;
import Com.Entity.traffic.Truckassembleentry;

@Controller
public class TruckassembleController {
	Logger log = LoggerFactory.getLogger(TruckassembleController.class);
	@Resource
	private ITruckassembleDao TruckassembleDao;
	@Resource
	private ISaledeliverDao SaledeliverDao;
	@Resource
	private IProductdefDao productdefDao;

	@RequestMapping(value = "/SaveTruckassemble")
	public String SaveTruckassemble(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
//		String result = "";
//		DateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//		Truckassemble Tinfo = null;
//		Truckassembleentry Tncols = null;
//		try {
//			String userid = ((Useronline) request.getSession().getAttribute("Useronline")).getFuserid();
//			String fid = request.getParameter("fid");
//			if (fid != null && !"".equals(fid)) {
//				Tinfo = TruckassembleDao.Query(fid);
//			} else {
//				Tinfo = new Truckassemble();
//				Tinfo.setFid(fid);
//				Tinfo.setFcreatorid(userid);
//				Tinfo.setFcreatetime(new Date());
//				Tinfo.setFbizdate(new Date());
//				Tinfo.setFnumber(TruckassembleDao.getFnumber("t_tra_truckassemble", "SN",4));
//			}
//			
//			Tinfo.setFlastupdatetime(new Date());
//			Tinfo.setFlastupdateuserid(userid);
//			Tinfo.setFtruckid(request.getParameter("ftruckid"));
////			HashMap<String, Object> params = TruckassembleDao.ExecSave(Tinfo);
//			ArrayList tnlist = (ArrayList)request.getAttribute("Truckassembleentry");
//			HashMap<String, Object> params = new HashMap<String, Object>();
//			params.put("Tinfo", Tinfo);
//			params.put("tnlist", tnlist);
//			TruckassembleDao.ExecTruckassemble(params,1);
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
		reponse.getWriter().write( JsonUtil.result(false,"提货单不能修改", "", ""));

		return null;

	}

	@RequestMapping(value = "/DelTruckassembleList")
	public String DelTruckassembleList(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {

//		String result = "";
//		String fidcls = request.getParameter("fidcls");
//		try {
//			String sql = "select t.fid from t_tra_truckassemble t where exists (select 1 from t_tra_truckassembleentry tn where tn.FPARENTID=t.fid and FDELIVERYED=1) and t.fid in " + fidcls;
//			List<HashMap<String,Object>> list= TruckassembleDao.QueryBySql(sql);
//			if (list.size()>0) {
//				throw new DJException("不能删除已提货的提货单！");
//			}
//			
//			//因为同时要删除分录所以放到Dao内进行事务保护;
//			HashMap<String, Object> params = new HashMap<String, Object>();
//			params.put("fidcls", fidcls);
//			TruckassembleDao.ExecTruckassemble(params,2);
//			
//			result = JsonUtil.result(true,"删除成功!", "", "");
//			reponse.setCharacterEncoding("utf-8");
//		} catch (Exception e) {
//			result = JsonUtil.result(false,e.getMessage(), "", "");
//			log.error("DelUserList error", e);
//		}
		reponse.setCharacterEncoding("utf-8");
		reponse.getWriter().write( JsonUtil.result(false,"提货单不能删除", "", ""));
		return null;

	}
	
	@RequestMapping(value = "/GetTruckassembleList")
	public String GetTruckassembleList(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		reponse.setCharacterEncoding("utf-8");
		try {
//			String userid = ((Useronline) request.getSession().getAttribute("Useronline")).getFuserid();
//			String sql = "SELECT t.fid,t.fnumber,t.fbizdate,t.fcreatorid,u1.fname creator,t.fcreatetime,t.flastupdateuserid,u2.fname updateuser,t.flastupdatetime,t.ftruckid,t.faudited,t.ftype from (select * from   t_tra_truckassemble order by  faudited asc)  t left join t_sys_user u1 on  u1.fid=t.fcreatorid left join t_sys_user u2 on u2.fid=t.flastupdateuserid where exists (select 1 from t_tra_truckassembleentry n left join t_bd_usersupplier up on up.fsupplierid=n.fsupplierid where n.fparentid=t.fid and up.fuserid='"+userid+"') ";
//			String sql = "SELECT t.fid,t.fnumber,t.fbizdate,t.fcreatorid,u1.fname creator,t.fcreatetime,t.flastupdateuserid,u2.fname updateuser,t.flastupdatetime,t.ftruckid,t.faudited,t.ftype from t_tra_truckassemble t left join t_sys_user u1 on  u1.fid=t.fcreatorid left join t_sys_user u2 on u2.fid=t.flastupdateuserid where exists (select 1 from t_tra_truckassembleentry n where n.fparentid=t.fid " + TruckassembleDao.QueryFilterByUser(request, null, "n.fsupplierid") + " ) " ;
			String sql = "SELECT t.fid,t.fnumber,t.fbizdate,t.fcreatorid,u1.fname creator,t.fcreatetime,t.flastupdateuserid,u2.fname updateuser,t.flastupdatetime,t.ftruckid,t.faudited,t.ftype from t_tra_truckassemble t left join t_sys_user u1 on  u1.fid=t.fcreatorid left join t_sys_user u2 on u2.fid=t.flastupdateuserid inner join (select fparentid from t_tra_truckassembleentry where 1=1 " + TruckassembleDao.QueryFilterByUser(request, null, "fsupplierid") + " group by fparentid ) n on n.fparentid=t.fid" ;
			request.setAttribute("djsort", "faudited asc");
			ListResult result = TruckassembleDao.QueryFilterList(sql, request);
			
//			String sql = "SELECT tm.FID,tm.FNUMBER,tm.FBIZDATE,tm.FCREATORID,tm.FCREATETIME,tm.FTRUCKID,t.FID entryid,t.FSEQ,FDELIVERYED,t.FPARENTID,t.FSALEORDERID," +
//					"p.fnumber salenumber,FDELIVERORDERID,dl.fnumber delivernumber,t.FSUPPLIERID,s.fname supplier,FPRODUCTID,d.fname product,"
//					+ "FPRODUCTSPEC,t.FAMOUNT,FRECEIVEADDRESS,ra.fname raddress,FRECEIVER,FRECEIVERPHONE,FDELIVERYADDRESS,da.fname daddress,FDELIVERY,FDELIVERYPHONE,FREMARK " +
//					"FROM t_tra_truckassemble tm left join t_tra_truckassembleentry t on t.FPARENTID=tm.fid left join t_ord_productplan p on p.fid=t.fsaleorderid " +
//					"left join t_ord_deliverorder dl on dl.fid=t.fdeliverorderid left join t_pdt_productdef d on d.fid=t.fproductid " +
//					"left join t_sys_supplier s on s.fid=t.fsupplierid left join t_bd_address ra on ra.fid=t.freceiveaddress " +
//					"left join t_bd_address da on da.fid=t.fdeliveryaddress ";
//			ListResult result = new ListResult();
//			result.setData(TruckassembleDao.QueryBySql(sql));
//			result.setTotal("");
			
			reponse.getWriter().write(JsonUtil.result(true, "", result));
		} catch (DJException e) {
			// TODO Auto-generated catch block
			reponse.getWriter().write(
					JsonUtil.result(false, e.getMessage(), "", ""));
		}

		return null;

	}
	
	@RequestMapping(value = "/GetTruckassembleInfo")
	public String GetTruckassembleInfo(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {

		reponse.setCharacterEncoding("utf-8");
		try {
			String sql = "SELECT t.fid,t.fnumber,t.fbizdate,t.fcreatorid,u1.fname creator,t.fcreatetime,t.flastupdateuserid,u2.fname updateuser,t.flastupdatetime,t.ftruckid FROM t_tra_truckassemble t left join t_sys_user u1 on  u1.fid=t.fcreatorid left join t_sys_user u2 on u2.fid=t.flastupdateuserid " +
					" where t.fid='"
					+ request.getParameter("fid") + "'";
//			sql=sql +TruckassembleDao.QueryFilterByUser(request,null, "t.fsupplier");
			ListResult sList = TruckassembleDao.QueryFilterList(sql,request);
			reponse.getWriter().write(JsonUtil.result(true, "", sList));
			
		} catch (Exception e) {
			reponse.getWriter().write(
					JsonUtil.result(false, e.getMessage(), "", ""));
		}
		return null;

	}
	
	@RequestMapping(value = "/GetTruckassembleentry")
	public String GetTruckassembleentry(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		reponse.setCharacterEncoding("utf-8");
		try {
			String sql = "SELECT t.fouted,ifnull(t.fouttime,'') fouttime,t.foutor,ifnull(u.fname,'') foutorname,t.fid,t.fseq,fdeliveryed,t.fparentid,t.fsaleorderid,p.fnumber salenumber,fdeliverorderid,dl.fnumber delivernumber,t.fsupplierid,s.fname supplier,t.fproductid,d.fname product,"
		+ "fproductspec,t.famount,freceiveaddress,ra.fdetailaddress raddress,freceiver,freceiverphone,fdeliveryaddress,ifnull(da.fdetailaddress,'') daddress,fdelivery,fdeliveryphone,ifnull(fremark,'') fremark " +
		"FROM t_tra_truckassembleentry t left join t_ord_productplan p on p.fid=t.fsaleorderid left join t_sys_user u on u.fid=t.foutor " +
		"left join t_ord_deliverorder dl on dl.fid=t.fdeliverorderid left join t_pdt_productdef d on d.fid=t.fproductid " +
		"left join t_sys_supplier s on s.fid=t.fsupplierid left join t_bd_address ra on ra.fid=t.freceiveaddress " +
		"left join t_bd_address da on da.fid=t.fdeliveryaddress where 1=1 " ;
			sql=sql +TruckassembleDao.QueryFilterByUser(request,null, "t.fsupplierid");
//		+ "where t.fparentid='" + JSONArray.fromObject(request.getParameter("Defaultfilter")).getJSONObject(0).getString("value") + "'";
//			ListResult result = new ListResult();
//			result.setData(TruckassembleDao.QueryBySql(sql));
//			result.setTotal("");
			ListResult result = TruckassembleDao.QueryFilterList(sql, request);
			reponse.getWriter().write(JsonUtil.result(true, "", result));
		} catch (DJException e) {
			// TODO Auto-generated catch block
			reponse.getWriter().write(
					JsonUtil.result(false, e.getMessage(), "", ""));
		}

		return null;

	}
	
	@RequestMapping("Truckassembletoexcel")
	public String Truckassembletoexcel(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		try {
			String sql = "SELECT ta.fnumber 提货单编号,t.fseq 提货单分录,fdeliveryed 提货,p.fnumber  制造商订单号,dl.fnumber 配送单号,s.fname 供应商,d.fname 产品名称,"
					+ "fproductspec,t.famount,freceiveaddress,ra.fname raddress,freceiver,freceiverphone,fdeliveryaddress,da.fname daddress,fdelivery,fdeliveryphone,fremark " +
					"FROM t_tra_truckassemble ta left join t_tra_truckassembleentry t on ta.fid=t.fparentid left join t_ord_productplan p on p.fid=t.fsaleorderid " +
					"left join t_ord_deliverorder dl on dl.fid=t.fdeliverorderid left join t_pdt_productdef d on d.fid=t.fproductid " +
					"left join t_sys_supplier s on s.fid=t.fsupplierid left join t_bd_address ra on ra.fid=t.freceiveaddress " +
					"left join t_bd_address da on da.fid=t.fdeliveryaddress  where 1=1 " ;
			sql=sql +TruckassembleDao.QueryFilterByUser(request,null, "t.fsupplierid");
//					+ "where t.fparentid='" + JSONArray.fromObject(request.getParameter("Defaultfilter")).getJSONObject(0).getString("value") + "'";
//						ListResult result = new ListResult();
//						result.setData(TruckassembleDao.QueryBySql(sql));
//						result.setTotal("");
						ListResult result = TruckassembleDao.QueryFilterList(sql, request);
			ExcelUtil.toexcel(reponse,result);
		} catch (DJException e) {
			// TODO Auto-generated catch block
			reponse.getWriter().write(
					JsonUtil.result(false, e.getMessage(), "", ""));
		}

		return null;

	}
	
	//车辆提货
	@RequestMapping(value = "/actionTruckassemble")
	public String actionTruckassemble(HttpServletRequest request,
			HttpServletResponse reponse) throws Exception {
		reponse.setCharacterEncoding("utf-8");
		String result = "";
		String assembleid = request.getParameter("assembleID");
		String entryids = request.getParameter("fidcls");
		entryids="'"+entryids.replace(",","','")+"'";
		try {
//			Truckassemble Tinfo = TruckassembleDao.Query(assembleid);
//			if(Tinfo.getFaudited()==1)
//			{
//				reponse.getWriter().write(JsonUtil.result(false,"已经发车，不能再提货","",""));
//				return null;
//			}
			
			String[] entryfid = entryids.split(",");
			for(int i=0;i<entryfid.length;i++){
				HashMap<String, Object> params=new HashMap<>();
				params.put("assembleid", assembleid);
				params.put("entryfid", entryfid[i]);
				params.put("userid", ((Useronline) request.getSession().getAttribute("Useronline")).getFuserid());
				TruckassembleDao.ExecTruckassemble(params, 3);
			}
		
		}catch (Exception e) {
			result = "{success:false,msg:'" + e.getMessage() + "'}";
		}
		reponse.getWriter().write(JsonUtil.result(true,"提货成功","",""));
		return null;
		
		
		
		
		
		
		
		
		
		
		
		
//		String result = "";
//		Saledeliver sdinfo = null;
//		Saledeliverentry sdninfo = null;
//		ArrayList<Saledeliver> sdcol = new ArrayList<Saledeliver>();
//		ArrayList<Saledeliverentry> sdncol = new ArrayList<Saledeliverentry>();
//		try {
//			HashMap<String, Integer> seqMap = new HashMap<String, Integer>();
//			HashMap<String, String> saledeliverIDMap = new HashMap<String, String>();
//			String fid = request.getParameter("fid");
//			Truckassemble Tinfo = TruckassembleDao.Query(fid);
//			ArrayList<Truckassembleentry> tncols = (ArrayList<Truckassembleentry>)request.getAttribute("Truckassembleentry");
//			String userid = ((Useronline) request.getSession().getAttribute("Useronline")).getFuserid();
////			String sql = "SELECT ta.fid,t.fseq,fdeliveryed,p.fnumber productplan,dl.fnumber delivernum,s.fname supplier,d.fname pdtname,"
////					+ "fproductspec,t.famount,freceiveaddress,ra.fname raddress,freceiver,freceiverphone,fdeliveryaddress,da.fname daddress,fdelivery,fdeliveryphone,fremark " +
////					"FROM t_tra_truckassemble ta left join t_tra_truckassembleentry t on ta.fid=t.fparentid left join t_ord_productplan p on p.fid=t.fsaleorderid " +
////					"left join t_ord_deliverorder dl on dl.fid=t.fdeliverorderid left join t_pdt_productdef d on d.fid=t.fproductid " +
////					"left join t_sys_supplier s on s.fid=t.fsupplierid left join t_bd_address ra on ra.fid=t.freceiveaddress " +
////					"left join t_bd_address da on da.fid=t.fdeliveryaddress where ta.fid = '"+fid+"'" ;
////			List<HashMap<String,Object>> data= TruckassembleDao.QueryBySql(sql);
//			String sql = "select t.fid,max(en.fseq) fseq from t_tra_saledeliver t left join t_tra_saledeliverentry en on en.fparentid=t.fid where t.fassembleid='" + fid + "' group by t.fid";
//			List<HashMap<String,Object>> list= TruckassembleDao.QueryBySql(sql);
//			if(list.size()>0){
//				sdinfo = SaledeliverDao.Query(list.get(0).get("fid").toString());
//				saledeliverIDMap.put(sdinfo.getFaddressid(), sdinfo.getFid());
//				seqMap.put(sdinfo.getFid(), (Integer)list.get(0).get("fseq"));
//			}
//			for (int i=0;i<tncols.size();i++)
//			{
//				Truckassembleentry taentry = tncols.get(i);
//				if(taentry.getFdeliveryed()==1){	//跳过已提货分录；
//					continue;
//				}
//				if(saledeliverIDMap.get(taentry.getFreceiveaddress())!=null){
//					sdinfo = SaledeliverDao.Query(saledeliverIDMap.get(taentry.getFreceiveaddress()));
//				}
//				if(saledeliverIDMap.size()>0 && saledeliverIDMap.containsKey(taentry.getFreceiveaddress()) && sdinfo.getFsupplier().equals(taentry.getFsupplierid())){
//					int fseq = seqMap.get(sdninfo.getFparentid())+1;
//					sdninfo = new Saledeliverentry();
//					sdninfo.setFparentid(saledeliverIDMap.get(taentry.getFreceiveaddress()));
//					sdninfo.setFamount(taentry.getFamount());
//					sdninfo.setFdeliverorderid(taentry.getFdeliverorderid());
//					sdninfo.setFdelivery(taentry.getFdelivery());
//					sdninfo.setFdeliveryaddress(taentry.getFdeliveryaddress());
//					sdninfo.setFdeliveryphone(taentry.getFdeliveryphone());
//					sdninfo.setFid(SaledeliverDao.CreateUUid());
//					sdninfo.setFproductid(taentry.getFproductid());
//					sdninfo.setFproductspec(taentry.getFproductspec());
//					sdninfo.setFreceiveaddress(taentry.getFreceiveaddress());
//					sdninfo.setFreceiver(taentry.getFreceiver());
//					sdninfo.setFreceiverphone(taentry.getFreceiverphone());
//					sdninfo.setFremark(taentry.getFremark());
//					sdninfo.setFsaleorderid(taentry.getFsaleorderid());
//					sdninfo.setFseq(fseq);
//					seqMap.put(sdninfo.getFparentid(), fseq);
//					sdninfo.setFsupplierid(taentry.getFsupplierid());
//					sdninfo.setFassembleentryid(taentry.getFid());
//					sdncol.add(sdninfo);
//				}else{
//					sdinfo = new Saledeliver();
//					sdinfo.setFid(SaledeliverDao.CreateUUid());
//					sdinfo.setFcreatorid(userid);
//					sdinfo.setFcreatetime(new Date());
//					sdinfo.setFbizdate(new Date());
//					sdinfo.setFnumber(SaledeliverDao.getFnumber("t_tra_saledeliver", "FH",4));
//					sdinfo.setFlastupdatetime(null);
//					sdinfo.setFlastupdateuserid(null);
//					sdinfo.setFtruckid(Tinfo.getFtruckid());
//					sdinfo.setFaddressid(taentry.getFreceiveaddress());
//					sdinfo.setFcustomerid(productdefDao.Query(taentry.getFproductid()).getFcustomerid());
//					sdinfo.setFauditorid(null);
//					sdinfo.setFauditdate(null);
//					sdinfo.setFaudited(0);
//					sdinfo.setFassembleid(fid);
//					sdinfo.setFtype(Tinfo.getFtype());
//					sdinfo.setFsupplier(taentry.getFsupplierid());
//					sdcol.add(sdinfo);
//					
//					sdninfo = new Saledeliverentry();
//					sdninfo.setFparentid(sdinfo.getFid());
//					sdninfo.setFamount(taentry.getFamount());
//					sdninfo.setFdeliverorderid(taentry.getFdeliverorderid());
//					sdninfo.setFdelivery(taentry.getFdelivery());
//					sdninfo.setFdeliveryaddress(taentry.getFdeliveryaddress());
//					sdninfo.setFdeliveryphone(taentry.getFdeliveryphone());
//					sdninfo.setFid(SaledeliverDao.CreateUUid());
//					sdninfo.setFproductid(taentry.getFproductid());
//					sdninfo.setFproductspec(taentry.getFproductspec());
//					sdninfo.setFreceiveaddress(taentry.getFreceiveaddress());
//					sdninfo.setFreceiver(taentry.getFreceiver());
//					sdninfo.setFreceiverphone(taentry.getFreceiverphone());
//					sdninfo.setFremark(taentry.getFremark());
//					sdninfo.setFsaleorderid(taentry.getFsaleorderid());
//					sdninfo.setFseq(1);
//					sdninfo.setFsupplierid(taentry.getFsupplierid());
//					sdninfo.setFassembleentryid(taentry.getFid());
//					
//					sdncol.add(sdninfo);
//					
//					saledeliverIDMap.put(sdinfo.getFaddressid(), sdinfo.getFid());
//					seqMap.put(sdinfo.getFid(), 1);
//				}
//			}
////			HashMap<String, Object> params = saleOrderDao.ExecSave(pinfo);
//			HashMap<String, Object> params = new HashMap<String, Object>();
//			params.put("fid", fid);
//			params.put("sdcol", sdcol);
//			params.put("sdncol", sdncol);
//			TruckassembleDao.ExecTruckassemble(params,3);
//			
//			if (params.get("success") == Boolean.TRUE) {
//				result = JsonUtil.result(true,"车辆提货成功!", "", "");
//			} else {
//				result = JsonUtil.result(false,"车辆提货失败!", "", "");
//			}
//		} catch (Exception e) {
//			result = "{success:false,msg:'" + e.getMessage() + "'}";
//		}
//		reponse.setCharacterEncoding("utf-8");
//		reponse.getWriter().write(result);
//		return null;
	}

	//取消提货
	@RequestMapping(value = "/cancelTruckassemble")
	public String cancelTruckassemble(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		reponse.setCharacterEncoding("utf-8");
		String result = "";
		String assembleid = request.getParameter("assembleid");
		String entryids = request.getParameter("fidcls");
		entryids="('"+entryids.replace(",","','")+"')";
		try {
			String sql = "select t.fid from t_tra_truckassemble t where faudited=1 and t.fid = '" + assembleid + "'";
			List<HashMap<String,Object>> list= TruckassembleDao.QueryBySql(sql);
			if (list.size()>0) {
				throw new DJException("提货单已发车不能取消提货！");
			}
			sql = "select t.fid from t_tra_truckassembleentry t where fouted=1 and t.fid in "+entryids;
			list= TruckassembleDao.QueryBySql(sql);
			if (list.size()>0) {
				throw new DJException("存在已发车提货单分录,不能取消提货！");
			}
			
			HashMap<String, Object> params = new HashMap<String, Object>();
			String updateAssembleentry = "update t_tra_truckassembleentry set fdeliveryed=0 where fid in "+entryids;
			String delSaledleiverentry = "delete from t_tra_saledeliverentry where fassembleentryid in "+entryids;
			
			sql = "select t.fid from t_tra_saledeliver t where t.fassembleid ='"+assembleid+"'";
			list= TruckassembleDao.QueryBySql(sql);
			String saledeliverids="";
			List<HashMap<String,Object>> entrylist ;
			for (int i=0;i<list.size();i++) {
				HashMap<String,Object> data = list.get(i);
				sql = "select fid from t_tra_saledeliverentry n where n.fparentid='"+data.get("fid").toString()+"' and n.fassembleentryid not in "+entryids;
				entrylist= TruckassembleDao.QueryBySql(sql);
				if(i==0){
					if (entrylist.size()==0) {
						saledeliverids="'" + data.get("fid").toString() +"'" ;
					}
				}else{
					if (entrylist.size()==0) {
						if(!saledeliverids.equals("")){
							saledeliverids=saledeliverids+",'" + data.get("fid").toString() +"'" ;
						}else{
							saledeliverids="'" + data.get("fid").toString() +"'" ;
						}
					}
				}
			}
			
			params.put("updateAssembleentry", updateAssembleentry);
			params.put("delSaledleiverentry", delSaledleiverentry);
			params.put("saledeliverids", saledeliverids);
			TruckassembleDao.ExecTruckassemble(params,6);
			
			if (params.get("success") == Boolean.TRUE) {
				result = JsonUtil.result(true,"取消提货成功!", "", "");
			} else {
				result = JsonUtil.result(false,"取消提货失败!", "", "");
			}
			
		} catch (Exception e) {
			result = JsonUtil.result(false,e.getMessage(), "", "");
			log.error("DelUserList error", e);
		}
		reponse.getWriter().write(result);
		return null;

	}

	//取消配送
	@RequestMapping(value = "/cancelDeliver")
	public String cancelDeliver(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		reponse.setCharacterEncoding("utf-8");
		String result = "";
		try {
			String assembleid = request.getParameter("assembleid");
			String entryids = request.getParameter("entryfidcls");
			String fdeliverorderids = request.getParameter("fdeliverorderfidcls");
			if(entryids.length()<=0)
			{
				throw new DJException("提货单分录ID无效");
			}
			if(fdeliverorderids.length()<=0)
			{
				throw new DJException("配送信息ID无效");
			}
			entryids="('"+entryids.replace(",","','")+"')";
			fdeliverorderids="('"+fdeliverorderids.replace(",","','")+"')";

			if(assembleid.length()<=0)
			{
				throw new DJException("提货单ID无效");
			}
			
//			String sql = "select t.fid from t_tra_truckassemble t where exists (select 1 from t_tra_truckassembleentry tn where tn.FPARENTID=t.fid and FDELIVERYED=1) and t.fid in " + fidcls;
			String sql = "select t.fid from t_tra_truckassembleentry t where fdeliveryed=1 and t.fid in " + entryids;
			List<HashMap<String,Object>> list= TruckassembleDao.QueryBySql(sql);
			if (list.size()>0) {
				throw new DJException("只能取消未提货的单据！");
			}
			sql="select fid from t_tra_truckassemble where ftype=1 and fid ='"+assembleid+"' ";
			list= TruckassembleDao.QueryBySql(sql);
			if (list.size()>0) {
				throw new DJException("东经物流配送的提货单不能取消配送！");
			}
			HashMap<String, Object> params = new HashMap<String, Object>();
			String delAssembleentry = "Delete FROM t_tra_truckassembleentry where fid in "+entryids;
			
			//取消自运发货的配送单时需要修改提货数量;
//			String updatedleiver = "update t_ord_deliverorder set fmatched=0 where fid in "+fdeliverorderids;
			String updatedleiver = "update t_ord_deliverorder d,t_tra_truckassembleentry n set fmatched=0, fassembleQty = fassembleQty - n.famount where n.fdeliverorderid=d.fid and d.fid in "+fdeliverorderids +" and n.fid in "+entryids;
			
			String checkhasentrysql = "select t.fid from t_tra_truckassembleentry t where fparentid ='"+assembleid+"' and fdeliverorderid not in "+entryids;
//			list= TruckassembleDao.QueryBySql(sql);
//			String isDelassemble="0";
//			if (list.size()==0) {
//				isDelassemble="1";
//			}
			params.put("delAssembleentry", delAssembleentry);
			params.put("updatedleiver", updatedleiver);
			params.put("checkhasentrysql", checkhasentrysql);
			params.put("assembleid", assembleid);
			params.put("fdeliverorderids", fdeliverorderids);
			TruckassembleDao.ExecTruckassemble(params,4);
			
			if (params.get("success") == Boolean.TRUE) {
				result = JsonUtil.result(true,"取消配送成功!", "", "");
			} else {
				result = JsonUtil.result(false,"取消配送失败!", "", "");
			}
			
		} catch (Exception e) {
			result = JsonUtil.result(false,e.getMessage(), "", "");
			log.error("DelUserList error", e);
		}
		reponse.getWriter().write(result);
		return null;

	}
	
	//发车
	@RequestMapping(value = "/actionAudit")
	public String actionAudit(HttpServletRequest request,
			HttpServletResponse reponse) throws Exception {
		String result = "";
//		Saleorder pinfo = null;
		
		String fid = request.getParameter("fid");
		try {
			
			result = actionAuditById(request, fid);
			
		} catch (Exception e) {
			result = "{success:false,msg:'" + e.getMessage() + "'}";
		}
		reponse.setCharacterEncoding("utf-8");
		reponse.getWriter().write(result);
		return null;
	}

	public String actionAuditById(HttpServletRequest request, String fid)
			throws Exception {
	    String result;
	    String userid = ((Useronline)request.getSession().getAttribute("Useronline")).getFuserid();
	    String supplierID = "";

	    String sql = "select FISFILTER from t_sys_user where FID='" + userid + "' ";
	    List list = this.TruckassembleDao.QueryBySql(sql);
	    if ((((HashMap)list.get(0)).get("FISFILTER") != null) && (((HashMap)list.get(0)).get("FISFILTER").toString().equals("1"))) {
	      throw new DJException("管理用户不能操作发车！");
	    }

	    sql = "select fsupplierid from t_bd_usersupplier where fuserid='" + userid + "' ";
	    list = this.TruckassembleDao.QueryBySql(sql);
	    if (list.size() != 1)
	      throw new DJException("管理用户不能操作发车！");

	    supplierID = ((HashMap)list.get(0)).get("fsupplierid").toString();

	    sql = "select n.fid from t_tra_truckassembleentry n where n.fparentid='" + fid + "' and n.fdeliveryed = 0 and n.fsupplierid='" + supplierID + "' ";
	    list = this.TruckassembleDao.QueryBySql(sql);
	    if (list.size() > 0) {
	      throw new DJException("存在未提货分录，不能发车！");
	    }

	    sql = "select 1 from t_tra_truckassemble where fid='" + fid + "' and faudited=1 ";
	    list = this.TruckassembleDao.QueryBySql(sql);
	    if (list.size() > 0) {
	      throw new DJException("提货单已发车，不能重复发车！");
	    }

	    String updateSaledelver = "";
	    String updateTruckassemble = "";

	    String updateTruckassembleEntry = "update t_tra_truckassembleentry set fouted=1,foutor='" + userid + "',fouttime=now() where fparentid='" + fid + "' and fsupplierid='" + supplierID + "' ";

	    HashMap params = new HashMap();

	    params.put("supplierID", supplierID);

	    params.put("updateTruckassembleEntry", updateTruckassembleEntry);
	    params.put("assembleid", fid);
	    params.put("userid", userid);
	    this.TruckassembleDao.ExecTruckassemble(params, 5);

	    if (params.get("success") == Boolean.TRUE)
	    {
	      result = JsonUtil.result(true, "发车成功！", "", "");
	    }
	    else result = JsonUtil.result(false, "发车失败!", "", "");

	    return result;
	  }
	
}
