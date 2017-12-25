package Com.Controller.Inv;

import java.io.IOException;
import java.math.BigDecimal;
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
import Com.Base.Util.ExcelUtil;
import Com.Base.Util.JsonUtil;
import Com.Base.Util.ListResult;
import Com.Dao.Inv.IOutWarehouseDao;
import Com.Dao.Inv.IStorebalanceDao;
import Com.Entity.Inv.Outwarehouse;
import Com.Entity.Inv.Storebalance;
import Com.Entity.System.Useronline;


@Controller
public class OutWarehouseController {
	Logger log = LoggerFactory.getLogger(OutWarehouseController.class);
	@Resource
	private IOutWarehouseDao outWarehouseDao;
	@Resource
	private IStorebalanceDao storebalanceDao; 

	@RequestMapping(value = "/SaveOutWarehouse")
	public String SaveOutWarehouse(HttpServletRequest request,
			HttpServletResponse reponse) throws Exception {
		String result = "";
		Outwarehouse pinfo = null;
		try {
			String userid = ((Useronline) request.getSession().getAttribute(
					"Useronline")).getFuserid();
			String fid = request.getParameter("fid");
			if (fid != null && !"".equals(fid)) {
				pinfo = outWarehouseDao.Query(fid);
				pinfo.setFlastupdatetime(new Date());
				pinfo.setFlastupdateuserid(userid);
				if(pinfo.getFaudited()!=null && pinfo.getFaudited()==1){
					reponse.setCharacterEncoding("utf-8");
					reponse.getWriter().write(JsonUtil.result(false,"已审核不能修改!", "", ""));
					return null;
				}
			} else {
				pinfo = new Outwarehouse();
				pinfo.setFid(fid);
				pinfo.setFcreatorid(userid);
				pinfo.setFcreatetime(new Date());
			}
			pinfo.setFnumber(request.getParameter("fnumber"));
			pinfo.setFwarehouseid(request.getParameter("fwarehouseid"));
			pinfo.setFwarehousesiteid(request.getParameter("fwarehousesiteid"));
			pinfo.setFproductid(request.getParameter("fproductid"));
			pinfo.setFsaleorderid(request.getParameter("fsaleorderid"));
			pinfo.setFissueenums(request.getParameter("fissueenums"));
			pinfo.setFremak(request.getParameter("fremak"));
			pinfo.setFoutqty(new BigDecimal(request.getParameter("foutqty")));
			pinfo.setFaudited(0);
			HashMap<String, Object> params = outWarehouseDao.ExecSave(pinfo);
			if (params.get("success") == Boolean.TRUE) {
				result = JsonUtil.result(true,"保存成功!", "", "");
			} else {
				result = JsonUtil.result(false,"保存失败!", "", "");
			}
		} catch (Exception e) {
			result = JsonUtil.result(false,e.getMessage(), "", "");
		}
		reponse.setCharacterEncoding("utf-8");
		reponse.getWriter().write(result);
		return null;

	}

	@RequestMapping(value = "/GetOutWarehouse")
	public String GetOutWarehouse(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		reponse.setCharacterEncoding("utf-8");
		try {
			String sql="select o.fid, o.fnumber,s.fid forderentryid,s.forderid fsaleorderid,ifnull(s.fnumber,'') planfnumber,ifnull(c.fname,'') custfname,o.fproductid,ifnull(p.fname,'') pfname,"
					+"o.foutqty,o.fwarehouseid,ifnull(w1.fname,'') wfname1,o.fwarehousesiteid,ifnull(w2.fname,'') wfname2,o.fremak,o.fissueenums,ifnull(u1.fname,'') as cfname,"
					+"ifnull(u2.fname,'') as lfname,o.fcreatetime,o.flastupdatetime,o.faudited,o.fauditorid,ifnull(u3.fname,'') fauditor,ifnull(o.faudittime,'') faudittime "
					+"from t_inv_outwarehouse o "
					+"left join t_ord_productplan s on s.fid=o.fproductplanid "
					+"left join t_bd_customer c on c.fid=s.fcustomerid "
					+"left join t_pdt_productdef p on o.fproductid=p.fid "
					+"left join t_bd_warehouse w1 on w1.fid=o.fwarehouseid "
					+"left join t_bd_warehousesites w2 on w2.fid=o.fwarehousesiteid "
					+"left join t_sys_user  u1 on u1.fid= o.fcreatorid "
					+"left join t_sys_user  u2 on u2.fid= o.flastupdateuserid left join t_sys_user u3 on u3.fid= o.fauditorid ";
			ListResult result = outWarehouseDao.QueryFilterList(sql, request);
			reponse.getWriter().write(JsonUtil.result(true, "", result));
		} catch (DJException e) {
			// TODO Auto-generated catch block
			reponse.getWriter().write(
					JsonUtil.result(false, e.getMessage(), "", ""));
		}
		return null;
	}

	@RequestMapping(value = "/GetOutWarehouseInfo")
	public String GetOutWarehouseInfo(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		reponse.setCharacterEncoding("utf-8");
		try {
			String sql="select o.fid, o.fnumber,o.fsaleorderid fsaleorderid_fid,ifnull(s.fnumber,'') fsaleorderid_fnumber,o.fproductid fproductid_fid,ifnull(p.fname,'') fproductid_fname, "
					+"o.foutqty,o.fwarehouseid fwarehouseid_fid,ifnull(w1.fname,'') fwarehouseid_fname,o.fwarehousesiteid fwarehousesiteid_fid,ifnull(w2.fname,'') fwarehousesiteid_fname,o.fremak,o.fissueenums ,o.faudited,o.fauditorid,ifnull(u3.fname,'') fauditor,ifnull(o.faudittime,'') faudittime "
					+"from t_inv_outwarehouse o  "
					+"left join t_ord_saleorder s on s.fid=o.fsaleorderid "
					+"left join t_pdt_productdef p on o.fproductid=p.fid "
					+"left join t_bd_warehouse w1 on w1.fid=o.fwarehouseid "
					+"left join t_bd_warehousesites w2 on w2.fid=o.fwarehousesiteid left join t_sys_user u3 on u3.fid= o.fauditorid ";
			String fid = request.getParameter("fid");
			if (fid != null && !"".equals(fid)) {
				sql += " where o.fid='" + fid + "'";
			}
			List<HashMap<String, Object>> sList = outWarehouseDao.QueryBySql(sql);
			reponse.getWriter().write(JsonUtil.result(true, "", "", sList));

		} catch (Exception e) {
			// TODO Auto-generated catch block
			reponse.getWriter().write(JsonUtil.result(false, e.getMessage(), "", ""));
		}
		return null;
	}

	@RequestMapping(value = "/DeleteOutWarehouse")
	public String DeleteOutWarehouse(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		String result = "";
		String fidcls = request.getParameter("fidcls");
		fidcls="('"+fidcls.replace(",","','")+"')";
		try {
			String hql = "Delete FROM Outwarehouse where faudited=0 and fid in " + fidcls;
			outWarehouseDao.ExecByHql(hql);
			result = JsonUtil.result(true,"删除成功!", "", "");
			reponse.setCharacterEncoding("utf-8");
		} catch (Exception e) {
			result = JsonUtil.result(false,e.getMessage(), "", "");
			log.error("DelUserList error", e);
		}
		reponse.getWriter().write(result);
		return null;

	}
	
	@RequestMapping(value = "/outWarehousetoexcel")
	public String outWarehousetoexcel(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		try {
			String sql="select o.fid, o.fnumber '编号',o.fsaleorderid '订单ID',ifnull(s.fnumber,'') '订单编号',ifnull(c.fname,'') '客户名称',o.fproductid '产品ID',ifnull(p.fname,'')  '产品名称',"
					+"o.foutqty '数量',o.fwarehouseid '仓库ID',ifnull(w1.fname,'') '仓库名称',o.fwarehousesiteid '库位ID',ifnull(w2.fname,'') '库位名称',o.fremak '备注',o.fissueenums '出库类型' ,ifnull(u1.fname,'') as'创建人',"
					+"ifnull(u2.fname,'') as'修改人',o.fcreatetime '创建时间 ',o.flastupdatetime '修改时间',o.faudited 审核,ifnull(u3.fname,'') 审核人,ifnull(o.faudittime,'') 审核时间 "
					+"from t_inv_outwarehouse o "
					+"left join t_ord_saleorder s on s.fid=o.fsaleorderid "
					+"left join t_bd_customer c on c.fid=s.fcustomerid "
					+"left join t_pdt_productdef p on o.fproductid=p.fid "
					+"left join t_bd_warehouse w1 on w1.fid=o.fwarehouseid "
					+"left join t_bd_warehousesites w2 on w2.fid=o.fwarehousesiteid "
					+"left join t_sys_user  u1 on u1.fid= o.fcreatorid "
					+"left join t_sys_user  u2 on u2.fid= o.flastupdateuserid left join t_sys_user u3 on u3.fid= o.fauditorid ";
			ListResult result = outWarehouseDao.QueryFilterList(sql, request);
			ExcelUtil.toexcel(reponse,result);
		} catch (DJException e) {
			// TODO Auto-generated catch block
			reponse.getWriter().write(
					JsonUtil.result(false, e.getMessage(), "", ""));
		}

		return null;

	}
	
	@RequestMapping(value = "/auditowh")
	public String auditowh(HttpServletRequest request,
			HttpServletResponse reponse) throws Exception {
		String result = "";
		Outwarehouse pinfo = null;
		try {
			String userid = ((Useronline) request.getSession().getAttribute("Useronline")).getFuserid();
			String fid = request.getParameter("fid");
			if (fid != null && !"".equals(fid)) {
				pinfo = outWarehouseDao.Query(fid);
				if(pinfo.getFaudited()==1){
					throw new DJException("已审核无需审核!");
				}else{
					pinfo.setFid(fid);
					pinfo.setFauditorid(userid);
					pinfo.setFaudited(1);
					pinfo.setFaudittime(new Date());
				}
			} else {
				throw new DJException("未保存不能审核!");
			}
			
			createStorebalance(request,reponse,userid,pinfo,1);
//			StorebalanceController sb = new StorebalanceController();
//			sb.SaveStorebalance(request, reponse);
			
			HashMap<String, Object> params = outWarehouseDao.ExecSave(pinfo);
			if (params.get("success") == Boolean.TRUE) {
				result = JsonUtil.result(true,"审核成功!", "", "");
			} else {
				result = JsonUtil.result(false,"审核失败!", "", "");
			}
		} catch (DJException e) {
			result = JsonUtil.result(false,e.getMessage(), "", "");
		}
		reponse.setCharacterEncoding("utf-8");
		reponse.getWriter().write(result);
		return null;
	}
	
	private void createStorebalance(HttpServletRequest request,
			HttpServletResponse reponse,String userid,Outwarehouse pinfo, int t) throws Exception {
		// TODO Auto-generated method stub
		Storebalance sbinfo = null;
		String fproductid = request.getParameter("fproductid");
		String fwarehouseid = request.getParameter("fwarehouseid");
		String fwarehousesiteid = request.getParameter("fwarehousesiteid");
		String fdescription = request.getParameter("fdescription");
		String fsaleorderid = request.getParameter("fsaleorderid");
		String forderentryid = request.getParameter("forderentryid");
		BigDecimal foutqty = new BigDecimal(request.getParameter("foutqty"));
		
		String sql = "SELECT w.fid,w.FWarehouseID,w.FWarehouseSiteID,w.FProductID,w.fdescription,w.fcreatorid,w.fcreatetime,w.fupdateuserid,w.fupdatetime,w.finqty,w.foutqty,w.fbalanceqty FROM t_inv_storebalance w where w.FWarehouseID='"+fwarehouseid+"' and w.FWarehouseSiteID='"+fwarehousesiteid+"' and w.FProductID='"+fproductid+"' and w.fsaleorderid='"+fsaleorderid+"' and w.forderentryid='"+forderentryid+"' ";
		List sb = storebalanceDao.QueryBySql(sql);
		
		String fid = "";
		DJException rsException = null;
		
		if(sb.size() == 1 ){
			fid = ((HashMap)sb.get(0)).get("fid").toString();
			sbinfo = storebalanceDao.Query(fid);
			
			if (t==1) {
				if(new BigDecimal(sbinfo.getFbalanceqty()).subtract(foutqty).compareTo(new BigDecimal(0))>=0){
					sbinfo.setFoutqty((new BigDecimal(sbinfo.getFoutqty()).add(foutqty)).divide(new BigDecimal(1),0,BigDecimal.ROUND_UP).intValue());
					sbinfo.setFbalanceqty((new BigDecimal(sbinfo.getFbalanceqty()).subtract(foutqty)).divide(new BigDecimal(1),0,BigDecimal.ROUND_UP).intValue());
					sbinfo.setFupdatetime(new Date());
					sbinfo.setFupdateuserid(userid);
					storebalanceDao.saveOrUpdate(sbinfo);
				}else{
					rsException = new DJException("库存不足,不能审核！");
					throw rsException ;
				}
			}else if(t==0){
				if(sbinfo.getFoutqty()>0){
					sbinfo.setFoutqty((new BigDecimal(sbinfo.getFoutqty()).subtract(foutqty)).divide(new BigDecimal(1),0,BigDecimal.ROUND_UP).intValue());
					sbinfo.setFbalanceqty((new BigDecimal(sbinfo.getFbalanceqty()).add(foutqty)).divide(new BigDecimal(1),0,BigDecimal.ROUND_UP).intValue());
					sbinfo.setFupdatetime(new Date());
					sbinfo.setFupdateuserid(userid);
					storebalanceDao.saveOrUpdate(sbinfo);
				}else{
					rsException = new DJException("出库未审核,不能执行操作！");
					throw rsException;
				}
			}
		}else if(sb.size() == 0 ){
			rsException = new DJException("没有入库记录,不能执行操作！");
			throw rsException;
		}else if(sb.size() > 1 ){
			rsException = new DJException("库存数据多条记录异常！");
			throw rsException;
		}
		
		List<HashMap<String,Object>> qtylist= storebalanceDao.QueryBySql("SELECT ifnull(sum(w.fbalanceqty),0) fbalanceqty,ifnull(sum(w.finqty),0) finqty FROM t_inv_storebalance w where w.FProductID='"+fproductid+"' and w.fsaleorderid='"+fsaleorderid+"' and w.forderentryid='"+forderentryid+"' ");
		BigDecimal fstockinqty = new BigDecimal(0);
		BigDecimal fstoreqty = new BigDecimal(0);
		if (qtylist.size()>0) {
			HashMap qtyinfo = qtylist.get(0); 
			fstoreqty = new BigDecimal(qtyinfo.get("fbalanceqty").toString());
			fstockinqty = new BigDecimal(qtyinfo.get("finqty").toString());
		}
		sql = "update t_ord_saleorder set fstoreqty = "+fstoreqty+",fstockinqty = "+fstockinqty+" where fid='"+forderentryid+"' ";
		storebalanceDao.ExecBySql(sql);
		
		qtylist= storebalanceDao.QueryBySql("SELECT ifnull(sum(w.fbalanceqty),0) fbalanceqty,ifnull(sum(w.finqty),0) finqty FROM t_inv_storebalance w where w.FProductID='"+fproductid+"' and w.fsaleorderid='"+fsaleorderid+"' and w.forderentryid='"+forderentryid+"' and fproductplanID='"+pinfo.getFproductplanid()+"'");
		fstockinqty = new BigDecimal(0);
		fstoreqty = new BigDecimal(0);
		if (qtylist.size()>0) {
			HashMap qtyinfo = qtylist.get(0); 
			fstoreqty = new BigDecimal(qtyinfo.get("fbalanceqty").toString());
			fstockinqty = new BigDecimal(qtyinfo.get("finqty").toString());
		}
		sql = "update t_ord_productplan set fstoreqty = "+fstoreqty+",fstockinqty = "+fstockinqty+" where fid='"+pinfo.getFproductplanid()+"' ";
		storebalanceDao.ExecBySql(sql);
	}

	@RequestMapping(value = "/unauditowh")
	public String unauditowh(HttpServletRequest request,
			HttpServletResponse reponse) throws Exception {
		String result = "";
		Outwarehouse pinfo = null;
		try {
			String userid = ((Useronline) request.getSession().getAttribute(
					"Useronline")).getFuserid();
			String fid = request.getParameter("fid");
			if (fid != null && !"".equals(fid)) {
				pinfo = outWarehouseDao.Query(fid);
				if(pinfo.getFaudited()==0){
					throw new DJException("未审核无需反审核!'");
				}else{
					pinfo.setFid(fid);
					pinfo.setFauditorid(null);
					pinfo.setFaudited(0);
					pinfo.setFaudittime(null);
				}
			} else {
				throw new DJException("未保存不能反审核!");
			}
			
//			StorebalanceController sb = new StorebalanceController();
//			sb.SaveStorebalance(request, reponse);
			createStorebalance(request,reponse,userid,pinfo,0);
			
			HashMap<String, Object> params = outWarehouseDao.ExecSave(pinfo);
			if (params.get("success") == Boolean.TRUE) {
				result = JsonUtil.result(true,"反审核成功!", "", "");
			} else {
				result = JsonUtil.result(false,"反审核失败!", "", "");
			}
		} catch (DJException e) {
			result = JsonUtil.result(false,e.getMessage(), "", "");
		}
		reponse.setCharacterEncoding("utf-8");
		reponse.getWriter().write(result);
		return null;
	}

}