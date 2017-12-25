package Com.Controller.Inv;

import java.io.IOException;
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
import Com.Base.Util.JsonUtil;
import Com.Base.Util.ListResult;
import Com.Dao.Inv.IProductInvDao;
import Com.Dao.Inv.IStorebalanceDao;
import Com.Dao.System.ISysUserDao;
import Com.Entity.System.Supplier;
import Com.Entity.System.SysUser;
import Com.Entity.System.Useronline;

@Controller
public class ProductInvControl {

	public static final String WAREHOUSE_SITE_IS_UNSET = "库位未设置";
	public static final String WAREHOUSE_IS_UNSET = "仓库未设置";
	public static final String THERE_IS_TOO_MANY_SUPPLIER = "当前用户关联太多制造商";
	public static final String THERE_IS_NO_SUPPLIER = "当前用户没有关联制造商";

	Logger log = LoggerFactory.getLogger(productindetailController.class);
	@Resource
	private IStorebalanceDao storebalanceDao; 
	@Resource
	private ISysUserDao SysUserDao;
	@Resource
	private IProductInvDao productInvDao;

	@RequestMapping(value = "/getManufacturersProductInventoryControls")
	public String getManufacturersProductInventoryControls(
			HttpServletRequest request, HttpServletResponse reponse)
			throws IOException {

		reponse.setCharacterEncoding("utf-8");

		try { 

//			String sql = " select spplr.fname supplier,tpvpp.fid, tpvpp.FPRODUCTID, tppd.fname , tppd.FCHARACTER ,tpvpp.FMINSTOCK, tpvpp.FORDERAMOUNT , ifnull(a.fbalanceqty,0)  as stock,ifnull(a.lockamt,0) as theLock,  ifnull(a.fbalanceqty,0) -ifnull(a.lockamt,0) as availableQuantity FROM t_pdt_vmiproductparam tpvpp left join t_sys_supplier spplr on spplr.fid=tpvpp.fsupplierId left join t_pdt_productdef tppd on tpvpp.FPRODUCTID = tppd.fid left join (select sbl.fproductid,ifnull(sum(odo.famount),0) lockamt, ifnull(sbl.fbalanceqty,0) as fbalanceqty from t_inv_storebalance sbl left join t_ord_deliverorder odo on sbl.fid =odo.fstorebalanceid and odo.fouted=0 where sbl.ftype=0 and sbl.fbalanceqty>0 group by sbl.fproductid,sbl.fbalanceqty ) a on tpvpp.FPRODUCTID=a.fproductid  where  tpvpp.ftype=0 "
//			String sql = "select spplr.fname supplier,tpvpp.fid, tpvpp.FPRODUCTID, tppd.fname , tppd.FCHARACTER ,tpvpp.FMINSTOCK, tpvpp.FORDERAMOUNT , ifnull(a.fbalanceqty,0)  as stock,ifnull(a.lockamt,0) as theLock,  ifnull(a.fbalanceqty,0) -ifnull(a.lockamt,0) as availableQuantity FROM t_pdt_vmiproductparam tpvpp left join t_sys_supplier spplr on spplr.fid=tpvpp.fsupplierId left join t_pdt_productdef tppd on tpvpp.FPRODUCTID = tppd.fid left join (select sbl.fsupplierid,sbl.fproductid,ifnull(sum(odo.famount),0) lockamt, ifnull(sbl.fbalanceqty,0) as fbalanceqty from t_inv_storebalance sbl left join t_ord_deliverorder odo  on sbl.fid =odo.fstorebalanceid and odo.fouted=0 and odo.ftype <> 1 where sbl.ftype=0 and sbl.fbalanceqty>0 group by sbl.fproductid,sbl.fbalanceqty,sbl.fsupplierid ) a on tpvpp.FPRODUCTID=a.fproductid and tpvpp.fsupplierid=a.fsupplierid where  tpvpp.ftype=0"
//					+ storebalanceDao.QueryFilterByUser(request, null,
//							"tpvpp.fsupplierid"); 
//
//			String sql = "select spplr.fname supplier,tpvpp.fid, tpvpp.FPRODUCTID, tppd.fname , "
//					+ " tppd.FCHARACTER ,tpvpp.FMINSTOCK, tpvpp.FORDERAMOUNT ,"
//					+ " ifnull(suit.fbalanceqtys,0)+ifnull(a.fbalanceqty,0)  as stock,ifnull(suitlockamt,0)+ifnull(a.lockamt,0) as theLock,  "
//					+ " ifnull(suit.fbalanceqtys,0)+ifnull(a.fbalanceqty,0) -ifnull(suitlockamt,0)-ifnull(a.lockamt,0) as availableQuantity "
//					+ " FROM t_pdt_vmiproductparam tpvpp "
//					+ " left join t_sys_supplier spplr on spplr.fid=tpvpp.fsupplierId "
//					+ " left join t_pdt_productdef tppd on tpvpp.FPRODUCTID = tppd.fid"
//					+ " left join usedbalanceqty_view a on tpvpp.FPRODUCTID=a.fproductid and tpvpp.fsupplierid=a.fsupplierid"
//					+ " left join ("
//					+ " select p.fparentid,s.fsupplierid,min(ifnull(s.fbalanceqty,0)/famount) fbalanceqtys ,min(ifnull(s.lockamt,0)/famount) suitlockamt "
//					+ " from  t_pdt_productdefproducts  p"
//					+ " left join usedbalanceqty_view s"
//					+ "  on s.fproductid=p.fproductid"
//					+ " group by p.fparentid,s.fsupplierid ) suit on suit.fparentid=tpvpp.FPRODUCTID  and suit.fsupplierid=tpvpp.fsupplierId"
//					+ " where  tpvpp.ftype=0 "
//					+ storebalanceDao.QueryFilterByUser(request, null,
//							"tpvpp.fsupplierid");
			
//			String sql = "	select spplr.fname supplier,tpvpp.fid, tpvpp.FPRODUCTID, tppd.fname , tppd.FCHARACTER ,tpvpp.FMINSTOCK, tpvpp.FORDERAMOUNT ,ifnull(suit.fbalanceqtys,0)+ifnull(a.fbalanceqty,0)  as stock,ifnull(suit.fbalanceqtys,0)-ifnull(suit.suitavailableQuantity,0)+ifnull(a.lockamt,0) as theLock ,ifnull(a.fbalanceqty,0)-ifnull(a.lockamt,0)+ifnull(suit.suitavailableQuantity,0) as availableQuantity "
//					+ "	 FROM t_pdt_vmiproductparam tpvpp  "
//					+ "	 left join t_sys_supplier spplr on spplr.fid=tpvpp.fsupplierId "
//					+ "	 left join t_pdt_productdef tppd on tpvpp.FPRODUCTID = tppd.fid"
//					+ "	left join usedbalanceqty_view a on tpvpp.FPRODUCTID=a.fproductid and tpvpp.fsupplierid=a.fsupplierid"
//					+ "	 left join ( "
//					+ "	  select p.fparentid,y.fsupplierid,min(ifnull(s.fbalanceqty,0)/famount) fbalanceqtys ,	min((ifnull(s.fbalanceqty,0)-ifnull(s.lockamt,0))/famount) suitavailableQuantity "
//					+ "	 from  t_pdt_productdefproducts  p "
//					+ "	 left join t_bd_productcycle  y on y.fproductdefid=p.fproductid"
//					+ "	 left join usedbalanceqty_view s on s.fproductid=y.fproductdefid and y.fsupplierid=s.fsupplierid"
//					+ "	 group by p.fparentid,y.fsupplierid  "
//					+ "	 ) suit on suit.fparentid=tpvpp.FPRODUCTID  and suit.fsupplierid=tpvpp.fsupplierId "
//					+ "	where tpvpp.ftype=0 "
			
			
			/*
			 * cd 20150122查询超时,注释;
			 * String sql=" select spplr.fname supplier,tpvpp.fid, tpvpp.FPRODUCTID, tppd.fname , tppd.FCHARACTER ,tpvpp.FMINSTOCK, tpvpp.FORDERAMOUNT ,ifnull(suit.fbalanceqtys,0)+ifnull(a.fbalanceqty,0)  as stock,ifnull(a.fallotqty,0)+ifnull(suit.fallotqtys,0) fallotqty,ifnull(suit.fbalanceqtys,0)+ifnull(suit.fallotqtys,0)-ifnull(suit.suitavailableQuantity,0)+ifnull(a.lockamt,0) as theLock,ifnull(a.fbalanceqty,0)+ifnull(a.fallotqty,0)-ifnull(a.lockamt,0)+ifnull(suit.suitavailableQuantity,0) as availableQuantity "
					+ "	 FROM t_pdt_vmiproductparam tpvpp  "
					+ "	 left join t_sys_supplier spplr on spplr.fid=tpvpp.fsupplierId "
					+ "	 left join t_pdt_productdef tppd on tpvpp.FPRODUCTID = tppd.fid"
					+ "	left join usedbalanceqty_view a on tpvpp.FPRODUCTID=a.fproductid and tpvpp.fsupplierid=a.fsupplierid"
					+ "	 left join ( "
					+ "	   select p.fparentid,y.fsupplierid,min(ifnull(s.fbalanceqty,0)/famount) fbalanceqtys ,min(ifnull(s.fallotqty,0)/famount) fallotqtys,	min((ifnull(s.fbalanceqty,0)+ifnull(s.fallotqty,0)-ifnull(s.lockamt,0))/famount) suitavailableQuantity "
					+ "	 from  t_pdt_productdefproducts  p "
					+ "	 left join t_bd_productcycle  y on y.fproductdefid=p.fproductid"
					+ "	 left join usedbalanceqty_view s on s.fproductid=y.fproductdefid and y.fsupplierid=s.fsupplierid"
					+ "	 group by p.fparentid,y.fsupplierid  "
					+ "	 ) suit on suit.fparentid=tpvpp.FPRODUCTID  and suit.fsupplierid=tpvpp.fsupplierId "
					+ "	where tpvpp.ftype=0 "
			* cd 20150122查询超时,注释;	
			*/
			String sql="select spplr.fname supplier,tpvpp.fid,tpvpp.FPRODUCTID,tppd.fname,tpvpp.FORDERAMOUNT,tpvpp.FMINSTOCK,ifnull(b.fbalanceqty, 0) as stock,ifnull(b.fallotqty, 0) fallotqty,ifnull(us.lockamt, 0) as theLock,ifnull(b.fbalanceqty, 0) - ifnull(us.lockamt, 0) as availableQuantity "
					+ "FROM t_pdt_vmiproductparam tpvpp left join t_sys_supplier spplr ON spplr.fid = tpvpp.fsupplierId left join t_pdt_productdef tppd ON tpvpp.FPRODUCTID = tppd.fid left join t_inv_storebalance b on b.fproductid=tpvpp.FPRODUCTID and b.FTYPE = 0 and b.fbalanceqty > 0 and b.fsupplierid = tpvpp.fsupplierId "
					+ "left join (select fProductID,ifnull(sum(fusedqty), 0) AS lockamt from t_inv_usedstorebalance where ftype = 0 group by fproductid) us on us.fProductID=tpvpp.FPRODUCTID and us.lockamt > 0 "
					+ "	where tpvpp.ftype=0 "
					+ storebalanceDao.QueryFilterByUser(request, null,
							"tpvpp.fsupplierid");
			ListResult result = storebalanceDao.QueryFilterList(sql, request);

			reponse.getWriter().write(JsonUtil.result(true, "", result));
		} catch (DJException e) {
			// TODO Auto-generated catch block
			reponse.getWriter().write(
					JsonUtil.result(false, e.getMessage(), "", ""));
		}
		return null;
	}

	@RequestMapping(value = "/doUpdateInBalanceAndWarehouseInOut")
	public String doUpdateInBalanceAndWarehouseInOut(
			HttpServletRequest request, HttpServletResponse reponse)
			throws Exception {

		reponse.setCharacterEncoding("utf-8");

		String user = ((Useronline) request.getSession().getAttribute(
				"Useronline")).getFuserid();
		
		String fproductID = request.getParameter("fproductID");

		Integer theSetNumber = new Integer(request.getParameter("theSetNumber"));

		try {
			SysUser userinfo=SysUserDao.Query(user);
			if(userinfo.getFisfilter()==1)
			{
				throw new DJException("管理帐号，不能操作");
			}
			String[] warehouserAndSiteAndSuppliers = findWAREHOUSEandSITE(
					request, reponse);
			List<HashMap<String, Object>> checksupplier=productInvDao.QueryBySql(" select fid from t_bd_productcycle where fproductdefid='"+fproductID+"' and fsupplierid='"+warehouserAndSiteAndSuppliers[2]+"' ");
			if(checksupplier.size()<=0)
			{
				throw new DJException("用户关联的制造商和产品的制造商不匹配，不能操作");
			}
			checksupplier=productInvDao.QueryBySql(" select p.fid,ifnull(f.fnewtype,0) fnewtype ,p.fproductid,'1' as famount from t_pdt_vmiproductparam  p left join t_pdt_productdef f on f.fid=p.fproductid where p.fproductid='"+fproductID+"' and p.fsupplierid='"+warehouserAndSiteAndSuppliers[2]+"' and p.ftype=0  ");
			if(checksupplier.size()<=0)
			{
				throw new DJException("该产品为订单管理类型，请刷新列表");
			}
			int producttype=Integer.valueOf(checksupplier.get(0).get("fnewtype").toString());
			if(producttype==0)
			{
				throw new DJException("该产品类型有误");
			}
			if(producttype==2||producttype==4)
			{
				
				List<HashMap<String, Object>> productlists=productInvDao.QueryBySql("select fproductid,famount from t_pdt_productdefproducts where fparentid='"+fproductID+"'");
				if(productlists.size()>0)
				{
					checksupplier.clear();
					checksupplier.addAll(productlists);
				}
			}
			productInvDao.updateWarehouseInOutAndStorebalance(checksupplier, user,
					theSetNumber, warehouserAndSiteAndSuppliers[0],
					warehouserAndSiteAndSuppliers[1],
					warehouserAndSiteAndSuppliers[2]);

			reponse.getWriter().write(JsonUtil.result(true, "", "", ""));

		} catch (Exception e) {
			// TODO Auto-generated catch block
			reponse.getWriter().write( 
					JsonUtil.result(false, e.getMessage(), "", ""));
		}

		return null;
	}

	private String[] selectWAREHOUSEandSITEBysupplierID(String supplierID) {
		// TODO Auto-generated method stub

		String hql = " from  Supplier s where s.fid = '%s' ";

		hql = String.format(hql, supplierID);

		Supplier supplierT = (Supplier) storebalanceDao.QueryByHql(hql).get(0);

		String[] warehouseParam = new String[] { supplierT.getFwarehouseid(),
				supplierT.getFwarehousesiteid() };

		// 空值报错
		if (warehouseParam[0] == null || warehouseParam[0].equals("")) {

			throw (new DJException(WAREHOUSE_IS_UNSET));

		}

		if (warehouseParam[1] == null || warehouseParam[1].equals("")) {

			throw (new DJException(WAREHOUSE_SITE_IS_UNSET));

		}

		return warehouseParam;
	}

	private String selectSupplierByUser(String userId) throws Exception {
		// TODO Auto-generated method stub

		String supplierID = null;

		String sql = " SELECT FSUPPLIERID FROM t_bd_usersupplier where FUSERID = '%s' ";

		sql = String.format(sql, userId);

		List<HashMap<String, Object>> listT = storebalanceDao.QueryBySql(sql);

		if (listT.size() > 1) {

			throw (new DJException(THERE_IS_TOO_MANY_SUPPLIER));

		} else if (listT.size() == 0) {

			throw (new DJException(THERE_IS_NO_SUPPLIER));

		} else {

			supplierID = (String) listT.get(0).get("FSUPPLIERID"); 
		}

		return supplierID;
	}

	private String[] findWAREHOUSEandSITE(HttpServletRequest request,
			HttpServletResponse reponse) throws Exception {

		reponse.setCharacterEncoding("utf-8");

		String userId = ((Useronline) request.getSession().getAttribute(
				"Useronline")).getFuserid();

	

			String supplierID = selectSupplierByUser(userId);

			String[] wAREHOUSEandSITE = selectWAREHOUSEandSITEBysupplierID(supplierID);

			StringBuilder sb = new StringBuilder();

			sb.append(supplierID);

			// 用逗号隔开的参数

			return new String[] { wAREHOUSEandSITE[0], wAREHOUSEandSITE[1],
					supplierID };

		

	}
}
