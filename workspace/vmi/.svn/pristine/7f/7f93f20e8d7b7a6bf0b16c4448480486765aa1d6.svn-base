
package Com.Dao.Inv;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.util.StringHelper;
import org.springframework.stereotype.Service;

import Com.Base.Dao.BaseDao;
import Com.Base.Util.DJException;
import Com.Base.Util.ServerContext;
import Com.Base.Util.SpringContextUtils;
import Com.Dao.order.IProductPlanDao;
import Com.Entity.Inv.Outwarehouse;
import Com.Entity.Inv.Storebalance;
import Com.Entity.System.Useronline;
import Com.Entity.order.ProductPlan;

@Service("outWarehouseDao")
public class OutWarehouseDao extends BaseDao implements IOutWarehouseDao {
	private IProductPlanDao ProductPlanDao = (IProductPlanDao) SpringContextUtils
			.getBean("productPlanDao");
	private IStorebalanceDao storebalanceDao = (IStorebalanceDao) SpringContextUtils
			.getBean("StorebalanceDao");

	@Override
	public HashMap<String, Object> ExecSave(Outwarehouse cyc) {
		// TODO Auto-generated method stub
		HashMap<String, Object> params = new HashMap<>();
		if (cyc.getFid().isEmpty()) {
			cyc.setFid(this.CreateUUid());
		}
		this.saveOrUpdate(cyc);

		params.put("success", true);
		return params;
	}

	@Override
	public Outwarehouse Query(String fid) {
		// TODO Auto-generated method stub
		return this.getHibernateTemplate().get(
				Outwarehouse.class, fid);
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
				sbInfo.setFbalanceqty(bigRest * (-1));
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

	// 计算套装库存;
	private void calculateSuitQty(String forderid, String FWarehouseID,
			String FWarehouseSiteID, String userID) {
		// TODO Auto-generated method stub
		String fproductid = "";
		String forderEntryid = "";
		BigDecimal inqty = new BigDecimal(0);
		String sql = "";
		Storebalance sbinfo;
		List<HashMap<String, Object>> orderlist = QueryBySql("SELECT fid,fproductdefid,famountrate,fentryProductType,fassemble,fiscombinecrosssubs,fordertype FROM t_ord_saleorder where forderid='"
				+ forderid + "' order by fseq");
		BigDecimal minQty = new BigDecimal(999999999); // 套装库存;
		String suitOrderentryID = ""; // 套装主分录;
		String suitPdtid = ""; // 套装主套产品;
		BigDecimal amoutrate = new BigDecimal(1); // 套装分录系数;
		int fentryProductType;
		int fassemble;
		int fiscombinecrosssubs;
		int fordertype;

		for (int l = 0; l < orderlist.size(); l++) {
			HashMap orderinfo = orderlist.get(l);
			if (l == 0) {
				suitOrderentryID = orderinfo.get("fid").toString();
				suitPdtid = orderinfo.get("fproductdefid").toString();
				continue;
			}
			fentryProductType = new Integer(orderinfo.get("fentryProductType")
					.toString());
			fassemble = new Integer(orderinfo.get("fassemble").toString());
			fiscombinecrosssubs = new Integer(orderinfo.get(
					"fiscombinecrosssubs").toString());
			fordertype = new Integer(orderinfo.get("fordertype").toString());

			// 套装订单有入库的产品类型：组装套,普通套子件,非组装且交叉合并套子件的合并套子件;
			// if(((fordertype == 2 || fordertype == 4 ) && fassemble==1) ||
			// fentryProductType==5 || (fentryProductType==7 &&
			// fiscombinecrosssubs==0)){
			fproductid = orderinfo.get("fproductdefid").toString();
			forderEntryid = orderinfo.get("fid").toString();
			amoutrate = new BigDecimal(orderinfo.get("famountrate").toString());
			List<HashMap<String, Object>> fbalancelist = QueryBySql("SELECT ifnull(sum(w.fbalanceqty),0) fbalanceqty FROM t_inv_storebalance w where w.FProductID='"
					+ fproductid
					+ "' and w.fsaleorderid='"
					+ forderid
					+ "' and w.forderentryid='"
					+ forderEntryid
					+ "' and w.fwarehouseId='"
					+ FWarehouseID
					+ "' and w.fwarehouseSiteId='" + FWarehouseSiteID + "' ");
			HashMap fbalanceinfo = fbalancelist.get(0);
			inqty = new BigDecimal(fbalanceinfo.get("fbalanceqty").toString())
					.divide(amoutrate, 0, BigDecimal.ROUND_DOWN);
			if (minQty.compareTo(inqty) > 0) {
				minQty = inqty;
			}
			// }
		}

		// 插入套装订单主分录结存表;
		sql = "SELECT fid FROM t_inv_storebalance w where w.FProductID='"
				+ suitPdtid + "' and w.fsaleorderid='" + forderid
				+ "' and w.forderentryid='" + suitOrderentryID
				+ "' and w.fwarehouseId='" + FWarehouseID
				+ "' and w.fwarehouseSiteId='" + FWarehouseSiteID + "' ";
		List<HashMap<String, Object>> suitbalancelist = QueryBySql(sql);
		if (suitbalancelist.size() <= 0) {
			sbinfo = new Storebalance();
			sbinfo.setFid(storebalanceDao.CreateUUid());
			sbinfo.setFcreatorid(userID);
			sbinfo.setFcreatetime(new Date());
			sbinfo.setFupdatetime(null);
			sbinfo.setFupdateuserid(null);
			sbinfo.setFinqty(minQty.intValue());
			sbinfo.setFoutqty(0);
			sbinfo.setFbalanceqty(minQty.intValue());
			sbinfo.setFwarehouseId(FWarehouseID);
			sbinfo.setFwarehouseSiteId(FWarehouseSiteID);
			sbinfo.setFproductId(suitPdtid);
			sbinfo.setFdescription(null);
			sbinfo.setFsaleorderid(forderid);
			sbinfo.setForderentryid(suitOrderentryID);
			storebalanceDao.ExecSave(sbinfo);
		} else {
			HashMap suitbalanceinfo = suitbalancelist.get(0);
			String suitsbfid = suitbalanceinfo.get("fid").toString();
			sql = "update t_inv_storebalance w set w.fbalanceqty = " + minQty
					+ ",w.finqty =" + minQty + ",fupdateuserid = '" + userID
					+ "',fupdatetime = CURRENT_TIMESTAMP where w.fid='"
					+ suitsbfid + "' ";
			ExecBySql(sql);
		}

		List<HashMap<String, Object>> suitOrderlist = QueryBySql("SELECT ifnull(sum(w.fbalanceqty),0) fbalanceqty,ifnull(sum(w.finqty),0) finqty FROM t_inv_storebalance w where w.FProductID='"
				+ suitPdtid
				+ "' and w.fsaleorderid='"
				+ forderid
				+ "' and w.forderentryid='" + suitOrderentryID + "' ");
		if (suitOrderlist.size() > 0) {
			HashMap suitOrderinfo = suitOrderlist.get(0);
			minQty = new BigDecimal(suitOrderinfo.get("fbalanceqty").toString());
			inqty = new BigDecimal(suitOrderinfo.get("finqty").toString());
		}
		sql = "update t_ord_saleorder set fstoreqty = " + minQty
				+ ",fstockinqty = " + inqty + " where fid='" + suitOrderentryID
				+ "' ";
		ExecBySql(sql);
	}

	@Override
	public void saveOutWarehouse(HttpServletRequest request)
			throws DJException, Exception {
		// TODO Auto-generated method stub
		String userID = ((Useronline) request.getSession().getAttribute(
				"Useronline")).getFuserid();
		String FWarehouseID = request.getParameter("FWarehouseID");
		String FWarehouseSiteID = request.getParameter("FWarehouseSiteID");
		String fproductplanid = request.getParameter("fplanid");
		BigDecimal famount = new BigDecimal(request.getParameter("famount"));
		String ftraitid=request.getParameter("ftraitid")==null?"":request.getParameter("ftraitid");
		saveOutWarehouse(fproductplanid, famount.intValue(), FWarehouseID, FWarehouseSiteID, userID,ftraitid, true);
//		int fentryProductType = 0;
//		if (request.getParameter("fentryProductType") != null
//				&& !request.getParameter("fentryProductType").equals("")) {
//			fentryProductType = new Integer(
//					request.getParameter("fentryProductType"));
//		}
//		int fassemble = 0;
//		if (request.getParameter("fassemble") != null
//				&& !request.getParameter("fassemble").equals("")
//				&& !request.getParameter("fassemble").equals("null")) {
//			fassemble = new Integer(request.getParameter("fassemble"));
//		}
//		int fiscombinecrosssubs = 0;
//		if (request.getParameter("fiscombinecrosssubs") != null
//				&& !request.getParameter("fiscombinecrosssubs").equals("")
//				&& !request.getParameter("fiscombinecrosssubs").equals("null")) {
//			fiscombinecrosssubs = new Integer(
//					request.getParameter("fiscombinecrosssubs"));
//		}
		
	}

	@Override
	public void saveOutWarehouse(String fproductplanid, int amt,
			String FWarehouseID, String FWarehouseSiteID, String userid,
			String fproductid, String forderid, String forderEntryid,String fsupplierid,String ftraitid,
			boolean audited) {
		// TODO Auto-generated method stub
		String sql = "select fid from t_pdt_vmiproductparam where fproductid = '"+fproductid+"' and FSUPPLIERID = '"+fsupplierid+"' and FTYPE = 0 ";
		List<HashMap<String, Object>> vmiproductparamcls=QueryBySql(sql);
		if (vmiproductparamcls.size()>0)
		{
			throw new DJException("该产品、制造商、下单类型为通知！");
		}
		if(audited)
		{
			storebalanceDao.ExecOutStore(fproductplanid, amt, FWarehouseID, FWarehouseSiteID, userid, fproductid, forderid, forderEntryid,ftraitid);
		}
		if(userid.contains("普通调拨"))
		{
			userid=userid.split(",")[0];
		}
		Outwarehouse Outwarehouseinfo = new Outwarehouse();
//		Outwarehouseinfo.setFnumber(ServerContext.getNumberHelper().getNumber("t_inv_outwarehouse", "U", 4,false));
		Outwarehouseinfo.setForderentryid(forderEntryid);
		Outwarehouseinfo.setFid(CreateUUid());
		Outwarehouseinfo.setFproductplanid(fproductplanid);
		Outwarehouseinfo.setFcreatorid(userid);
		Outwarehouseinfo.setFcreatetime(new Date());
		Outwarehouseinfo.setFlastupdatetime(null);
		Outwarehouseinfo.setFlastupdateuserid(null);
		Outwarehouseinfo.setFoutqty(new BigDecimal(amt));
		Outwarehouseinfo.setFwarehouseid(FWarehouseID);
		Outwarehouseinfo.setFwarehousesiteid(FWarehouseSiteID);
		Outwarehouseinfo.setFproductid(fproductid);
		Outwarehouseinfo.setFremak(null);
		Outwarehouseinfo.setFsaleorderid(forderid);
		Outwarehouseinfo.setFaudited(audited?1:0);
		Outwarehouseinfo.setFauditorid(audited?userid:"");
		Outwarehouseinfo.setFaudittime(audited?new Date():null);
		Outwarehouseinfo.setFsupplierid(fsupplierid);
		Outwarehouseinfo.setFtraitid(ftraitid);
		ExecSave(Outwarehouseinfo);
	}

	@Override
	public void saveOutWarehouse(String fproductplanid, int amt,
			String FWarehouseID, String FWarehouseSiteID, String userid,String ftraitid,
			boolean audited) {
		// TODO Auto-generated method stub
		if(fproductplanid==null || StringHelper.isEmpty(fproductplanid))
		{
			throw new DJException("下单类型为订单，订单ID不能为空！");
		}
		ProductPlan pplaninfo = ProductPlanDao.Query(fproductplanid);
		String fordertype = pplaninfo.getFordertype().toString();
		String forderid = pplaninfo.getForderid();
		String forderEntryid = pplaninfo.getFparentorderid();
		String fproductid = pplaninfo.getFproductdefid();
		if (pplaninfo.getFaffirmed() == null || pplaninfo.getFaffirmed() == 0) {
			throw new DJException("供应商未确认不能出库！");
		}
		saveOutWarehouse(fproductplanid, amt, FWarehouseID, FWarehouseSiteID, userid, fproductid, forderid, forderEntryid,pplaninfo.getFsupplierid(),ftraitid,audited);
	}

	@Override
	public void saveOutWarehouseTZ(int amt, String FWarehouseID,
			String FWarehouseSiteID, String userid, String fproductid,
			String fsupplierid, boolean audited) {
		// TODO Auto-generated method stub
		String sql = "select fid from t_pdt_vmiproductparam where fproductid = '"+fproductid+"' and FSUPPLIERID = '"+fsupplierid+"' and FTYPE = 0 ";
		List<HashMap<String, Object>> vmiproductparamcls=QueryBySql(sql);
		if (vmiproductparamcls.size()<=0)
		{
			throw new DJException("该产品、制造商、下单类型为订单！");
		}
		if(audited)
		{
			storebalanceDao.ExecOutStoreTZ(amt, FWarehouseID, FWarehouseSiteID, userid, fproductid, fsupplierid);;
		}
		Outwarehouse Outwarehouseinfo = new Outwarehouse();
//		Outwarehouseinfo.setFnumber(ServerContext.getNumberHelper().getNumber("t_inv_outwarehouse", "U", 4,false));
		Outwarehouseinfo.setFid(CreateUUid());
		Outwarehouseinfo.setFcreatorid(userid);
		Outwarehouseinfo.setFcreatetime(new Date());
		Outwarehouseinfo.setFlastupdatetime(null);
		Outwarehouseinfo.setFlastupdateuserid(null);
		Outwarehouseinfo.setFoutqty(new BigDecimal(amt));
		Outwarehouseinfo.setFwarehouseid(FWarehouseID);
		Outwarehouseinfo.setFwarehousesiteid(FWarehouseSiteID);
		Outwarehouseinfo.setFproductid(fproductid);
		Outwarehouseinfo.setFremak(null);
		Outwarehouseinfo.setFaudited(audited?1:0);
		Outwarehouseinfo.setFauditorid(audited?userid:"");
		Outwarehouseinfo.setFaudittime(audited?new Date():null);
		Outwarehouseinfo.setFsupplierid(fsupplierid);
		ExecSave(Outwarehouseinfo);
	}

}
