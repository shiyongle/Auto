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
import Com.Dao.Inv.IStorebalanceDao;
import Com.Dao.Inv.IproductindetailDao;
import Com.Entity.Inv.Productindetail;
import Com.Entity.System.Useronline;

@Controller
public class productindetailController {
	Logger log = LoggerFactory.getLogger(productindetailController.class);
	@Resource
	private IproductindetailDao productindetailDao;
	@Resource
	private IStorebalanceDao storebalanceDao; 

	@RequestMapping(value = "/Saveproductindetail")
	public String Saveproductindetail(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		String result = "";
		Productindetail pinfo = null;
		try {
			String userid = ((Useronline) request.getSession().getAttribute(
					"Useronline")).getFuserid();
			String fid = request.getParameter("fid");
			if (fid != null && !"".equals(fid)) {
				pinfo = productindetailDao.Query(fid);
				pinfo.setFupdatetime(new Date());
				pinfo.setFupdateuserid(userid);
				if(pinfo.getFaudited()!=null && pinfo.getFaudited()==1){
					reponse.setCharacterEncoding("utf-8");
					reponse.getWriter().write(JsonUtil.result(false,"已审核不能修改!", "", ""));
					return null;
				}
			} else {
				pinfo = new Productindetail();
				pinfo.setFid(fid);
				pinfo.setFcreatorid(userid);
				pinfo.setFcreatetime(new Date());
			}
			pinfo.setFname(request.getParameter("fname"));
			pinfo.setFnumber(request.getParameter("fnumber"));
			pinfo.setFsimplename(request.getParameter("fsimplename"));
			pinfo.setFdescription(request.getParameter("fdescription"));
			pinfo.setFproductId(request.getParameter("FProductID"));
			pinfo.setFsaleOrderId(request.getParameter("FSaleOrderID"));
			pinfo.setFwarehouseId(request.getParameter("FWarehouseID"));
			pinfo.setFwarehouseSiteId(request.getParameter("FWarehouseSiteID"));
			pinfo.setFsimplename(request.getParameter("fsimplename"));
			pinfo.setFinqty(new BigDecimal(request.getParameter("finqty")));
			pinfo.setFtype(Integer.valueOf(request.getParameter("ftype")));
			pinfo.setFaudited(0);
			HashMap<String, Object> params = productindetailDao.ExecSave(pinfo);
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

	@RequestMapping(value = "/GetproductindetailList")
	public String GetproductindetailList(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		reponse.setCharacterEncoding("utf-8");
		try {
			String sql = "SELECT w.fid, ifnull(w.fnumber,'') fnumber,ifnull(w.fname,'') fname,ifnull(w.fsimplename,'') fsimplename,w.FWarehouseID,ifnull(wh.fname,'') as whname,w.FWarehouseSiteID,ifnull(whs.fname,'') as whsname,w.FProductID, ifnull(d.fname,'') as pdtname,w.fdescription,w.fcreatorid, u1.fname as cfname,ifnull(u2.fname,'') as lfname ,w.fcreatetime,w.fupdateuserid,ifnull(w.fupdatetime,'') fupdatetime,w.finqty,w.ftype,s.fid forderentryid,s.forderid fsaleorderid,ifnull(s.fnumber,'') as plannumber,w.faudited,w.fauditorid,ifnull(u3.fname,'') fauditor,ifnull(w.faudittime,'') faudittime FROM t_inv_productindetail w left join t_pdt_productdef d on d.fid=w.FProductID left join t_ord_productplan s on s.fid=w.fproductplanid left join t_sys_user  u1 on u1.fid= w.fcreatorid left join t_sys_user u2 on u2.fid= w.fupdateuserid left join t_bd_warehouse wh on wh.fid=w.FWarehouseID left join t_bd_warehousesites whs on whs.fid=w.FWarehouseSiteID left join t_sys_user u3 on u3.fid= w.fauditorid ";
			ListResult result = productindetailDao.QueryFilterList(sql, request);
			reponse.getWriter().write(JsonUtil.result(true, "", result));
		} catch (DJException e) {
			// TODO Auto-generated catch block
			reponse.getWriter().write(
					JsonUtil.result(false, e.getMessage(), "", ""));
		}
		return null;
	}

	@RequestMapping(value = "/GetproductindetailInfo")
	public String GetproductindetailInfo(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		reponse.setCharacterEncoding("utf-8");
		try {
			String sql = "SELECT w.fid, w.fnumber,w.fname,w.fsimplename,w.FWarehouseID FWarehouseID_fid,ifnull(wh.fname,'') as FWarehouseID_fname,w.FWarehouseSiteID FWarehouseSiteID_fid,ifnull(whs.fname,'') as FWarehouseSiteID_fname,w.FProductID FProductID_fid, ifnull(d.fname,'') as FProductID_fname,w.fdescription,w.fcreatorid, u1.fname as cfname,u2.fname as lfname ,w.fcreatetime,w.fupdateuserid,w.fupdatetime,w.finqty,w.ftype,w.FSaleOrderID FSaleOrderID_fid,ifnull(s.fnumber,'') as FSaleOrderID_fnumber,w.faudited,w.fauditorid,ifnull(u3.fname,'') fauditor,ifnull(w.faudittime,'') faudittime FROM t_inv_productindetail w left join t_pdt_productdef d on d.fid=w.FProductID left join t_ord_saleorder s on s.fid=w.FSaleOrderID left join t_sys_user  u1 on u1.fid= w.fcreatorid left join t_sys_user u2 on u2.fid= w.fupdateuserid left join t_bd_warehouse wh on wh.fid=w.FWarehouseID left join t_bd_warehousesites whs on whs.fid=w.FWarehouseSiteID left join t_sys_user u3 on u3.fid= w.fauditorid ";
			String fid = request.getParameter("fid");
			if (fid != null && !"".equals(fid)) {
				sql += " where w.fid='" + fid + "'";
			}
			List<HashMap<String, Object>> sList = productindetailDao.QueryBySql(sql);
			reponse.getWriter().write(JsonUtil.result(true, "", "", sList));

		} catch (Exception e) {
			// TODO Auto-generated catch block
			reponse.getWriter().write(
					JsonUtil.result(false, e.getMessage(), "", ""));
		}
		return null;
	}

	@RequestMapping(value = "/Deleteproductindetail")
	public String Deleteproductindetail(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		String result = "";
		String fidcls = request.getParameter("fidcls");
		fidcls="('"+fidcls.replace(",","','")+"')";
		try {
			String hql = "Delete FROM Productindetail where faudited=0 and fid in " + fidcls;
			productindetailDao.ExecByHql(hql);
			result = JsonUtil.result(true,"删除成功!", "", "");
			reponse.setCharacterEncoding("utf-8");
		} catch (Exception e) {
			result = JsonUtil.result(false,e.getMessage(), "", "");
			log.error("DelUserList error", e);
		}
		reponse.getWriter().write(result);
		return null;

	}
	
	@RequestMapping(value = "/productindetailtoexcel")
	public String productindetailtoexcel(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		try {
			String sql = "SELECT w.fid, w.fnumber 入库编号,w.fname 入库名称,w.fsimplename 简称,w.FWarehouseID,ifnull(wh.fname,'') as 仓库名称,w.FWarehouseSiteID,ifnull(whs.fname,'') as 库位名称,w.FProductID, ifnull(d.fname,'') as 产品名称,w.fdescription 描述,w.fcreatorid, u1.fname as 创建人,u2.fname as 修改人 ,w.fcreatetime 创建时间,w.fupdateuserid,w.fupdatetime 修改时间,w.finqty 入库数量,w.ftype 入库类型,w.FSaleOrderID,ifnull(s.fnumber,'') as 订单编号,w.faudited 审核,ifnull(u3.fname,'') 审核人,ifnull(w.faudittime,'') 审核时间 FROM t_inv_productindetail w left join t_pdt_productdef d on d.fid=w.FProductID left join t_ord_saleorder s on s.fid=w.FSaleOrderID left join t_sys_user  u1 on u1.fid= w.fcreatorid left join t_sys_user u2 on u2.fid= w.fupdateuserid left join t_bd_warehouse wh on wh.fid=w.FWarehouseID left join t_bd_warehousesites whs on whs.fid=w.FWarehouseSiteID left join t_sys_user u3 on u3.fid= w.fauditorid ";
			ListResult result = productindetailDao.QueryFilterList(sql, request);
			ExcelUtil.toexcel(reponse,result);
		} catch (DJException e) {
			// TODO Auto-generated catch block
			reponse.getWriter().write(
					JsonUtil.result(false, e.getMessage(), "", ""));
		}

		return null;

	}
	
	@RequestMapping(value = "/GetWarehousesiteList")
	public String GetWarehousesiteList(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		reponse.setCharacterEncoding("utf-8");
		try {
			String sql = "SELECT s.fid,s.fname,s.fnumber,s.fcreatetime,s.fcreatorid,u1.fname as cfname,s.flastupdateuserid,u2.fname as lfname,s.flastupdatetime,s.faddress,"
					+ " fparentid,ifnull(w.fname,'') as wfname,fremark,finstoreprice,foutstoreprice,farea FROM t_bd_warehousesites  s "
					+ " left join t_bd_warehouse w on w.fid=s.fparentid"
					+ " left join t_sys_user u1 on u1.fid=s.fcreatorid"
					+ " left join t_sys_user u2 on u2.fid=s.flastupdateuserid ";
			ListResult result = productindetailDao.QueryFilterList(sql, request);
			reponse.getWriter().write(JsonUtil.result(true, "", result));
		} catch (DJException e) {
			// TODO Auto-generated catch block
			reponse.getWriter().write(
					JsonUtil.result(false, e.getMessage(), "", ""));
		}
		return null;
	}
	
	//不做审核、反审核调整库存;
//	@RequestMapping(value = "/auditpit")
//	public String auditpit(HttpServletRequest request,
//			HttpServletResponse reponse) throws Exception {
//		String result = "";
//		Productindetail pinfo = null;
//		try {
//			String userid = ((Useronline) request.getSession().getAttribute(
//					"Useronline")).getFuserid();
//			String fid = request.getParameter("fid");
//			if (fid != null && !"".equals(fid)) {
//				pinfo = productindetailDao.Query(fid);
//				if(pinfo.getFaudited()==1){
//					throw new DJException("已审核无需审核!");
//				}else{
//					pinfo.setFid(fid);
//					pinfo.setFauditorid(userid);
//					pinfo.setFaudited(1);
//					pinfo.setFaudittime(new Date());
//				}
//			} else {
//				throw new DJException("未保存不能审核!");
//			}
//			
//			createStorebalance(request,reponse,userid,pinfo,1);
////			StorebalanceController sb = new StorebalanceController();
////			sb.SaveStorebalance(request, reponse);
//			
//			HashMap<String, Object> params = productindetailDao.ExecSave(pinfo);
//			if (params.get("success") == Boolean.TRUE) {
//				result = JsonUtil.result(true,"审核成功!", "", "");
//			} else {
//				result = JsonUtil.result(false,"审核失败!", "", "");
//			}
//		} catch (Exception e) {
//			result = JsonUtil.result(false,e.getMessage(), "", "");
//		}
//		reponse.setCharacterEncoding("utf-8");
//		reponse.getWriter().write(result);
//		return null;
//	}
//	
//	private void createStorebalance(HttpServletRequest request,
//			HttpServletResponse reponse,String userid,Productindetail pinfo, int t) throws Exception {
//		// TODO Auto-generated method stub
//		Storebalance sbinfo = null;
//		String fproductid = pinfo.getFproductId();
//		String fwarehouseid = pinfo.getFwarehouseId();
//		String fwarehousesiteid = pinfo.getFwarehouseSiteId();
//		String fdescription = pinfo.getFdescription();
////		int finqty = Integer.valueOf(request.getParameter("finqty"));
//		String fsaleorderid = pinfo.getFsaleOrderId();
//		String forderentryid = pinfo.getForderentryid();
//		BigDecimal finqty = pinfo.getFinqty();
//		
//		String sql = "SELECT w.fid,w.FWarehouseID,w.FWarehouseSiteID,w.FProductID,w.fdescription,w.fcreatorid,w.fcreatetime,w.fupdateuserid,w.fupdatetime,w.finqty,w.foutqty,w.fbalanceqty FROM t_inv_storebalance w where w.FWarehouseID='"+fwarehouseid+"' and w.FWarehouseSiteID='"+fwarehousesiteid+"' and w.fproductplanId ='"+pinfo.getFproductplanid()+"' ";
//		List sb = storebalanceDao.QueryBySql(sql);
//		
//		String fid = "";
//		Exception rsException = null;
//		
//		if(sb.size() == 1 ){
//			fid = ((HashMap)sb.get(0)).get("fid").toString();
//			sbinfo = storebalanceDao.Query(fid);
//			
//			if (t==0) {
//				if(new BigDecimal(sbinfo.getFbalanceqty()).subtract(finqty).compareTo(new BigDecimal(0))>=0){
//					sbinfo.setFinqty((new BigDecimal(sbinfo.getFinqty()).subtract(finqty)).divide(new BigDecimal(1),0,BigDecimal.ROUND_UP).intValue());
//					sbinfo.setFbalanceqty((new BigDecimal(sbinfo.getFbalanceqty()).subtract(finqty)).divide(new BigDecimal(1),0,BigDecimal.ROUND_UP).intValue());
//					sbinfo.setFupdatetime(new Date());
//					sbinfo.setFupdateuserid(userid);
//					storebalanceDao.saveOrUpdate(sbinfo);
//					
////					sql = "update t_ord_saleorder set fstoreqty = fstoreqty - "+finqty+",fstockinqty = ifnull(fstockoutqty,0) + "+finqty+" where fid='"+forderentryid+"' ";
////					storebalanceDao.ExecBySql(sql);
////					sql = "update t_ord_productplan set fstoreqty = fstoreqty - "+finqty+",fstockinqty = ifnull(fstockoutqty,0) + "+finqty+" where fid='"+forderentryid+"' ";
////					storebalanceDao.ExecBySql(sql);
//				}else{
//					rsException = new DJException("库存不足,不能反审核！");
//					throw rsException ;
//				}
//			}else if(t==1){
//				sbinfo.setFinqty((new BigDecimal(sbinfo.getFinqty()).add(finqty)).divide(new BigDecimal(1),0,BigDecimal.ROUND_UP).intValue());
//				sbinfo.setFbalanceqty((new BigDecimal(sbinfo.getFbalanceqty()).add(finqty)).divide(new BigDecimal(1),0,BigDecimal.ROUND_UP).intValue());
//				sbinfo.setFupdatetime(new Date());
//				sbinfo.setFupdateuserid(userid);
//				storebalanceDao.saveOrUpdate(sbinfo);
//			}
//		}else if(sb.size() == 0 ){
//			if(t==1){
//				sbinfo = new Storebalance();
//				sbinfo.setFid(fid);
//				sbinfo.setFcreatorid(userid);
//				sbinfo.setFcreatetime(new Date());
//				sbinfo.setFupdatetime(null);
//				sbinfo.setFupdateuserid(null);
//				sbinfo.setFinqty(finqty.divide(new BigDecimal(1),0,BigDecimal.ROUND_UP).intValue());
//				sbinfo.setFoutqty(0);
//				sbinfo.setFbalanceqty(finqty.divide(new BigDecimal(1),0,BigDecimal.ROUND_UP).intValue());
//				sbinfo.setFwarehouseId(fwarehouseid);
//				sbinfo.setFwarehouseSiteId(fwarehousesiteid);
//				sbinfo.setFproductId(fproductid);
//				sbinfo.setFdescription(fdescription);
//				sbinfo.setFsaleorderid(fsaleorderid);
//				sbinfo.setForderentryid(forderentryid);
//				sbinfo.setFproductplanId(pinfo.getFproductplanid());
//				storebalanceDao.ExecSave(sbinfo);
//				
//			}else if(t==0){
//				rsException = new DJException("没有库存记录不能反审核！");
//				throw rsException ;
//			}
//		}else if(sb.size() > 1 ){
//			rsException = new DJException("库存数据多条记录异常！");
//			throw rsException;
//		}
//		
//		List<HashMap<String,Object>> qtylist= storebalanceDao.QueryBySql("SELECT ifnull(sum(w.fbalanceqty),0) fbalanceqty,ifnull(sum(w.finqty),0) finqty FROM t_inv_storebalance w where w.FProductID='"+fproductid+"' and w.fsaleorderid='"+fsaleorderid+"' and w.forderentryid='"+forderentryid+"' ");
//		BigDecimal fstockinqty = new BigDecimal(0);
//		BigDecimal fstoreqty = new BigDecimal(0);
//		if (qtylist.size()>0) {
//			HashMap qtyinfo = qtylist.get(0); 
//			fstoreqty = new BigDecimal(qtyinfo.get("fbalanceqty").toString());
//			fstockinqty = new BigDecimal(qtyinfo.get("finqty").toString());
//		}
//		sql = "update t_ord_saleorder set fstoreqty = "+fstoreqty+",fstockinqty = "+fstockinqty+" where fid='"+forderentryid+"' ";
//		storebalanceDao.ExecBySql(sql);
//		
//		qtylist= storebalanceDao.QueryBySql("SELECT ifnull(sum(w.fbalanceqty),0) fbalanceqty,ifnull(sum(w.finqty),0) finqty FROM t_inv_storebalance w where w.FProductID='"+fproductid+"' and w.fsaleorderid='"+fsaleorderid+"' and w.forderentryid='"+forderentryid+"' and fproductplanID='"+pinfo.getFproductplanid()+"'");
//		fstockinqty = new BigDecimal(0);
//		fstoreqty = new BigDecimal(0);
//		if (qtylist.size()>0) {
//			HashMap qtyinfo = qtylist.get(0); 
//			fstoreqty = new BigDecimal(qtyinfo.get("fbalanceqty").toString());
//			fstockinqty = new BigDecimal(qtyinfo.get("finqty").toString());
//		}
//		sql = "update t_ord_productplan set fstoreqty = "+fstoreqty+",fstockinqty = "+fstockinqty+" where fid='"+pinfo.getFproductplanid()+"' ";
//		storebalanceDao.ExecBySql(sql);
//	}
//
//	@RequestMapping(value = "/unauditpit")
//	public String unauditpit(HttpServletRequest request,
//			HttpServletResponse reponse) throws Exception {
//		String result = "";
//		Productindetail pinfo = null;
//		try {
//			String userid = ((Useronline) request.getSession().getAttribute(
//					"Useronline")).getFuserid();
//			String fid = request.getParameter("fid");
//			if (fid != null && !"".equals(fid)) {
//				pinfo = productindetailDao.Query(fid);
//				if(pinfo.getFaudited()==0){
//					throw new DJException("未审核无需反审核!'");
//				}else{
//					pinfo.setFid(fid);
//					pinfo.setFauditorid(null);
//					pinfo.setFaudited(0);
//					pinfo.setFaudittime(null);
//				}
//			} else {
//				throw new DJException("未保存不能反审核!");
//			}
//			
////			StorebalanceController sb = new StorebalanceController();
////			sb.SaveStorebalance(request, reponse);
//			createStorebalance(request,reponse,userid,pinfo,0);
//			
//			HashMap<String, Object> params = productindetailDao.ExecSave(pinfo);
//			if (params.get("success") == Boolean.TRUE) {
//				result = JsonUtil.result(true,"反审核成功!", "", "");
//			} else {
//				result = JsonUtil.result(false,"反审核失败!", "", "");
//			}
//		} catch (Exception e) {
//			result = JsonUtil.result(false,e.getMessage(), "", "");
//		}
//		reponse.setCharacterEncoding("utf-8");
//		reponse.getWriter().write(result);
//		return null;
//	}
	
}