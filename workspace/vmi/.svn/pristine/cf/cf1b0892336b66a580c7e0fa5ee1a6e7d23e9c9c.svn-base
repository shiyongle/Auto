package Com.Dao.traffic;

import Com.Base.Dao.BaseDao;
import Com.Base.Util.DJException;
import Com.Base.Util.NumberHelper;
import Com.Base.Util.ServerContext;
import Com.Base.Util.SpringContextUtils;
import Com.Dao.Inv.IOutWarehouseDao;
import Com.Dao.Inv.IStorebalanceDao;
import Com.Dao.System.ISupplierDao;
import Com.Dao.order.IDeliverapplyDao;
import Com.Dao.order.IDeliverorderDao;
import Com.Dao.order.IDeliversDao;
import Com.Dao.order.IProductPlanDao;
import Com.Entity.Inv.Outwarehouse;
import Com.Entity.Inv.Storebalance;
import Com.Entity.System.Supplier;
import Com.Entity.order.Deliverorder;
import Com.Entity.order.Delivers;
import Com.Entity.traffic.Saledeliver;
import Com.Entity.traffic.Saledeliverentry;
import Com.Entity.traffic.Truckassemble;
import Com.Entity.traffic.Truckassembleentry;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import org.hibernate.util.StringHelper;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Service;

@Service("TruckassembleDao")
public class TruckassembleDao extends BaseDao
  implements ITruckassembleDao
{
  private IStorebalanceDao storebalanceDao = (IStorebalanceDao)SpringContextUtils.getBean("StorebalanceDao");
  private IProductPlanDao ProductPlanDao = (IProductPlanDao)SpringContextUtils.getBean("productPlanDao");
  private ISupplierDao SupplierDao = (ISupplierDao)SpringContextUtils.getBean("SupplierDao");
  private IOutWarehouseDao OutWarehouseDao = (IOutWarehouseDao)SpringContextUtils.getBean("outWarehouseDao");
  private IDeliverapplyDao deliverapplyDao = (IDeliverapplyDao)SpringContextUtils.getBean("deliverapplyDao");
  private IDeliverorderDao deliverorderDao = (IDeliverorderDao)SpringContextUtils.getBean("DeliverorderDao");
  private IDeliversDao deliversDao = (IDeliversDao)SpringContextUtils.getBean("DeliversDao");
  private IOutWarehouseDao outWarehouseDao = (IOutWarehouseDao)SpringContextUtils.getBean("outWarehouseDao");

  public HashMap<String, Object> ExecSave(Truckassemble Tassemble)
  {
    HashMap params = new HashMap();
    if (Tassemble.getFid().isEmpty())
      Tassemble.setFid(CreateUUid());

    saveOrUpdate(Tassemble);

    params.put("success", Boolean.valueOf(true));
    return params;
  }

  public Truckassemble Query(String fid)
  {
    return ((Truckassemble)getHibernateTemplate().get(
      Truckassemble.class, fid));
  }

  public HashMap<String, Object> ExecTruckassemble(HashMap<String, Object> params, int type)
    throws Exception
  {
	    if (type == 1)
	    {
	      Truckassemble Tinfo = (Truckassemble)params.get("Tinfo");
	      ExecSave(Tinfo);
	      ExecByHql("Delete FROM Truckassembleentry where fparentid = '" + Tinfo.getFid() + "'");
	      ArrayList tnlist = (ArrayList)params.get("tnlist");
	      for (int i = 0; i < tnlist.size(); ++i) {
	        Truckassembleentry tninfo = (Truckassembleentry)tnlist.get(i);
	        if (tninfo.getFid().isEmpty())
	          tninfo.setFid(CreateUUid());

	        tninfo.setFparentid(Tinfo.getFid());
	        saveOrUpdate(tninfo);
	      }
	    }
	    else if (type == 2)
	    {
	      String fids = params.get("fidcls").toString();
	      if ((fids != null) && (!(fids.equals("")))) {
	        String hql = "Delete FROM Truckassemble where fid in " + fids;
	        ExecByHql(hql);
	        ExecByHql("Delete FROM Truckassembleentry where fparentid in " + fids);
	      }
	    } else {
	      String userid;
	      if (type == 3)
	      {
	        String assembleid = params.get("assembleid").toString();
	        Truckassemble assembleinfo = Query(assembleid);

	        String entryfid = params.get("entryfid").toString();
	        userid = params.get("userid").toString();
	        HashMap truckassembleentryinfo = null;
	        HashMap Saledeliverinfo = null;
	        String saledeliverID = "";
	        String sql = " select * from t_tra_truckassembleentry t where t.fdeliveryed=0 and fouted = 0 and t.fid = " + entryfid;
	        List truckassembleentrycls = QueryBySql(sql);
	        for (int i = 0; i < truckassembleentrycls.size(); ++i)
	        {
	          truckassembleentryinfo = (HashMap)truckassembleentrycls.get(i);
	          int seq = 1;
	          sql = "select fid from t_tra_saledeliver where faudited=0 and fassembleid='"+assembleid+"' and faddressid='"+truckassembleentryinfo.get("FRECEIVEADDRESS")+"' and fsupplier='"+truckassembleentryinfo.get("FSUPPLIERID")+"'";
	          List saledelivercls = QueryBySql(sql);
	          if (saledelivercls.size() > 0)
	          {
	            saledeliverID = ((HashMap)saledelivercls.get(0)).get("fid").toString();
	            seq = deliverorderDao.getSaleDeliverEntrySeq(saledeliverID);
	          }
	          else
	          {
	            Saledeliver sdinfo = new Saledeliver();
	            saledeliverID = CreateUUid();
	            sdinfo.setFid(saledeliverID);
	            sdinfo.setFcreatorid(userid);
	            sdinfo.setFcreatetime(new Date());
	            sdinfo.setFbizdate(new Date());
	            sdinfo.setFnumber(ServerContext.getNumberHelper().getNumber("t_tra_saledeliver", "DJ", 4, false));
	            sdinfo.setFlastupdatetime(new Date());
	            sdinfo.setFlastupdateuserid(userid);
	            sdinfo.setFtruckid(assembleinfo.getFtruckid());
	            sdinfo.setFaddressid(truckassembleentryinfo.get("freceiveaddress".toUpperCase()).toString());
	            sdinfo.setFcustomerid(truckassembleentryinfo.get("fcustomerid".toUpperCase()).toString());
	            sdinfo.setFauditorid(null);
	            sdinfo.setFauditdate(null);
	            sdinfo.setFaudited(Integer.valueOf(0));
	            sdinfo.setFassembleid(assembleid);
	            sdinfo.setFtype(assembleinfo.getFtype());
	            sdinfo.setFsupplier(truckassembleentryinfo.get("fsupplierid".toUpperCase()).toString());
	            saveOrUpdate(sdinfo);
	          }

	          Saledeliverentry sdninfo = new Saledeliverentry();
	          sdninfo.setFparentid(saledeliverID);
	          sdninfo.setFamount(new BigDecimal(truckassembleentryinfo.get("famount".toUpperCase()).toString()));
	          sdninfo.setFrealamount(sdninfo.getFamount());
	          sdninfo.setFdeliverorderid(truckassembleentryinfo.get("fdeliverorderid".toUpperCase()).toString());
	          sdninfo.setFdelivery(truckassembleentryinfo.get("fdelivery".toUpperCase()).toString());
	          sdninfo.setFdeliveryaddress(truckassembleentryinfo.get("fdeliveryaddress".toUpperCase()).toString());
	          sdninfo.setFdeliveryphone(truckassembleentryinfo.get("fdeliveryphone".toUpperCase()).toString());
	          sdninfo.setFid(this.ProductPlanDao.CreateUUid());
	          sdninfo.setFproductid(truckassembleentryinfo.get("fproductid".toUpperCase()).toString());
	          sdninfo.setFproductspec(truckassembleentryinfo.get("fproductspec".toUpperCase()).toString());
	          sdninfo.setFreceiveaddress(truckassembleentryinfo.get("freceiveaddress".toUpperCase()).toString());
	          sdninfo.setFreceiver(truckassembleentryinfo.get("freceiver".toUpperCase()).toString());
	          sdninfo.setFreceiverphone(truckassembleentryinfo.get("freceiverphone".toUpperCase()).toString());
	          sdninfo.setFremark(null);
	          sdninfo.setFsaleorderid(truckassembleentryinfo.get("fsaleorderid".toUpperCase()).toString());
	          sdninfo.setFseq(Integer.valueOf(seq));
	          sdninfo.setFsupplierid(truckassembleentryinfo.get("fsupplierid".toUpperCase()).toString());
	          sdninfo.setFassembleentryid(truckassembleentryinfo.get("fid".toUpperCase()).toString());

	          sdninfo.setFisreceipts(Integer.valueOf(0));
	          saveOrUpdate(sdninfo);

	          ExecBySql(" update t_tra_truckassembleentry set fdeliveryed=1 where fid='" + truckassembleentryinfo.get("fid".toUpperCase()).toString() + "' ");
	        }

	      }
	      else if (type == 4)
	      {
	        String delAssembleentry = params.get("delAssembleentry").toString();
	        String updatedleiver = params.get("updatedleiver").toString();
	        String checkhasentrysql = params.get("checkhasentrysql").toString();
	        String assembleid = params.get("assembleid").toString();
	        String fdeliverorderids = params.get("fdeliverorderids").toString();

	        if (updatedleiver.length() > 0) {
	          ExecBySql(updatedleiver);
	        }

	        if (delAssembleentry.length() > 0) {
	          ExecBySql(delAssembleentry);
	        }

	        List list = QueryBySql(checkhasentrysql);
	        if (list.size() <= 0) {
	          String hql = "Delete FROM Truckassemble where fid = '" + assembleid + "'";
	          ExecByHql(hql);
	        }

	        List delivers = QueryBySql("select d.fdeliversId,ifnull(fcount,0) fcount from t_ord_deliverorder d left join (select count(1) fcount,fdeliversId from t_ord_deliverorder where fouted = 1 group by fdeliversId) a on a.fdeliversId=d.fdeliversId where d.fid in " + fdeliverorderids + " group by d.fdeliversId ");

	        for (int j = 0; j < delivers.size(); ++j)
	          if (((HashMap)delivers.get(j)).get("fcount").toString().equals("0"))
	            this.deliverapplyDao.updateDeliverapplyStateByDelivers(((HashMap)delivers.get(j)).get("fdeliversId").toString(), 3, false);


	      }
	      else if (type == 5)
	      {
	        String supplierID = params.get("supplierID").toString();
	        String updateTruckassembleEntry = params.get("updateTruckassembleEntry").toString();

	        String assembleid = params.get("assembleid").toString();
	        userid = params.get("userid").toString();

	        String FWarehouseSiteID = "";
	        String fproductplanid = "";
	        String FWarehouseID = "";
	        String fproductid = "";
	        String forderid = "";
	        String forderEntryid = "";
	        String ftype = "";
	        int amt = 0;
	        String fdeliverorderids = "";
	        String deliverorderid = "";

	        String sql = "select r.ftype,e.fseq,e.fid,e.famount,e.fproductid,e.fsaleorderid,e.fsupplierid,e.fdeliverorderid from t_tra_truckassembleentry e left join t_ord_deliverorder r on e.fdeliverorderid=r.fid where e.fouted=0 and e.fparentid ='" + assembleid + "' and e.fsupplierid='" + supplierID + "' ";
	        List list = QueryBySql(sql);
	        HashMap deliversMap = new HashMap();
	        int Unoutqty = 0;
	        for (int i = 0; i < list.size(); ++i) {
	          HashMap data = (HashMap)list.get(i);
	          amt = new BigDecimal(data.get("famount").toString()).divide(new BigDecimal(1), 0, 0).intValue();
	          fproductplanid = data.get("fsaleorderid").toString();
	          Supplier suppllier = this.SupplierDao.Query(data.get("fsupplierid").toString());
	          fproductid = data.get("fproductid").toString();
	          FWarehouseID = suppllier.getFwarehouseid();
	          FWarehouseSiteID = suppllier.getFwarehousesiteid();
	          ftype = data.get("ftype").toString();

	          if ((data.get("fdeliverorderid") == null) || (StringHelper.isEmpty(data.get("fdeliverorderid").toString())))
	            throw new DJException("提货单数据异常，" + assembleid);

	          deliverorderid = data.get("fdeliverorderid").toString();

	          if (fdeliverorderids.equals(""))
	            fdeliverorderids = "'" + deliverorderid + "'";
	          else {
	            fdeliverorderids = fdeliverorderids + ",'" + deliverorderid + "'";
	          }

	          if ("1".equals(ftype))
	          {
	            continue;
	          }

	          Deliverorder deliverorder = this.deliverorderDao.Query(deliverorderid);

	          Delivers delivers = this.deliversDao.Query(deliverorder.getFdeliversId());

	          if (deliversMap.containsKey(deliverorder.getFdeliversId())) {
	            Unoutqty = ((Integer)deliversMap.get(deliverorder.getFdeliversId())).intValue() + amt;
	            deliversMap.put(deliverorder.getFdeliversId(), Integer.valueOf(Unoutqty));
	          } else {
	            Unoutqty = amt;
	            deliversMap.put(deliverorder.getFdeliversId(), Integer.valueOf(Unoutqty));
	          }

	          List usedstorebalancelist = QueryBySql("SELECT fid, fratio, fusedqty, fdeliverorderid, fstorebalanceid, fproductid, ftype FROM t_inv_usedstorebalance where fdeliverorderid = '" + deliverorderid + "'");
	          if (usedstorebalancelist.size() == 0)
	            throw new DJException("占用库存表数据异常！");

	          for (int k = 0; k < usedstorebalancelist.size(); ++k) {
	            int usedqty = new Integer(((HashMap)usedstorebalancelist.get(k)).get("fusedqty").toString()).intValue();
	            int ratio = new Integer(((HashMap)usedstorebalancelist.get(k)).get("fratio").toString()).intValue();
	            String usedsbid = ((HashMap)usedstorebalancelist.get(k)).get("fid").toString();
	            String sbid = ((HashMap)usedstorebalancelist.get(k)).get("fstorebalanceid").toString();

	            Storebalance sbinfo = this.storebalanceDao.Query(sbid);
	            if (sbinfo.getFbalanceqty().intValue() - amt * ratio < 0)
	              throw new DJException("出库单" + data.get("fseq") + "分录库存数量不足，不能发车！");

	            ExecBySql("update t_inv_usedstorebalance set fusedqty = " + (usedqty - amt * ratio) + " where fid ='" + usedsbid + "'");

	            sbinfo.setFbalanceqty(Integer.valueOf(sbinfo.getFbalanceqty().intValue() - amt * ratio));
	            sbinfo.setFoutqty(Integer.valueOf(sbinfo.getFoutqty().intValue() + amt * ratio));
	            this.storebalanceDao.ExecSave(sbinfo);
	            forderEntryid = sbinfo.getForderentryid();
	            fproductplanid = sbinfo.getFproductplanId();

	            Outwarehouse Outwarehouseinfo = new Outwarehouse();
//	            Outwarehouseinfo.setFnumber(ServerContext.getNumberHelper().getNumber("t_inv_outwarehouse", "U", 4, false));
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
	            Outwarehouseinfo.setFaudited(Integer.valueOf(1));
	            Outwarehouseinfo.setFauditorid(userid);
	            Outwarehouseinfo.setFaudittime(new Date());
	            Outwarehouseinfo.setFsupplierid(suppllier.getFid());
	            this.outWarehouseDao.ExecSave(Outwarehouseinfo);

	            if ((forderEntryid != null) && (!(forderEntryid.equals("")))) {
	              sql = "update t_ord_saleorder set fstoreqty = fstoreqty - " + 
	                amt + 
	                ",fstockoutqty = fstockoutqty + " + 
	                amt + 
	                " where fid='" + 
	                forderEntryid + 
	                "' ";
	              ExecBySql(sql);
	            }

	            if ((fproductplanid != null) && (!(fproductplanid.equals("")))) {
	              sql = "update t_ord_productplan set fstoreqty = fstoreqty - " + 
	                amt + 
	                ",fstockoutqty = fstockoutqty + " + 
	                amt + 
	                " where fid='" + 
	                fproductplanid + 
	                "' ";
	              ExecBySql(sql);
	            }

	          }

	        }

	        if (fdeliverorderids.length() > 0)
	        {
	          ExecBySql("update t_ord_deliverorder d,t_tra_truckassembleentry n set d.fstate = case when (ifnull(foutQty,0)+n.famount)>=d.famount then 2 else 1 end,d.fouted = case when (ifnull(foutQty,0)+n.famount)>=d.famount then 1 else 0 end, foutQty = ifnull(foutQty,0)+n.famount, d.foutorid=case when (ifnull(foutQty,0)+n.famount)>=d.famount then '" + userid + "' else '' end,d.fouttime=case when (ifnull(foutQty,0)+n.famount)>=d.famount then now() else NOW() end where n.fdeliverorderid=d.fid and n.fouted=0 and d.fid in (" + fdeliverorderids + ")");
	        }

	        if (updateTruckassembleEntry.length() > 0)
	          ExecBySql(updateTruckassembleEntry);

	        ExecBySql("update t_tra_saledeliver set faudited=1,fauditorid='" + userid + "',fauditdate=now() where faudited=0 and fassembleid = '" + assembleid + "' and FSUPPLIER='" + supplierID + "'");
	        ExecBySql("update t_tra_truckassemble t set t.faudited=1 where fid = '" + assembleid + "' and not exists (select 1 from t_tra_truckassembleentry where fparentid='" + assembleid + "' and fouted=0)");

//	        List delivers = QueryBySql("select d.fboxtype,sum(ifnull(d.foutQty,0)) outQty,d.fdeliversId,ifnull(fcount,0) fcount from t_ord_deliverorder d left join (select count(1) fcount,fdeliversId from t_ord_deliverorder where fouted = 0 group by fdeliversId) a on a.fdeliversId=d.fdeliversId where d.fid in (" + fdeliverorderids + ") group by d.fdeliversId ");
	        List delivers = QueryBySql("select d.fboxtype,sum(ifnull(d.foutQty,0)) outQty,d.fdeliversId,MIN(d.fouted) fouted from t_ord_deliverorder d  where d.fid in (" + fdeliverorderids + ") group by d.fdeliversId ");
	        BigDecimal foutQty = new BigDecimal(0);
	        for (int j = 0; j < delivers.size(); ++j) {
	          if ("1".equals(((HashMap)delivers.get(j)).get("fboxtype").toString())) {//纸板
	            if (((HashMap)delivers.get(j)).get("fouted").toString().equals("1"))
	            {
	              this.deliverapplyDao.updateDeliverapplyState(((HashMap)delivers.get(j)).get("fdeliversId").toString(), 6, true);
	            }
	            else { this.deliverapplyDao.updateDeliverapplyState(((HashMap)delivers.get(j)).get("fdeliversId").toString(), 5, true);
	            }

	            ExecBySql("update t_ord_deliverapply set foutQty='" + ((HashMap)delivers.get(j)).get("outQty") + "' where fid = '" + ((HashMap)delivers.get(j)).get("fdeliversId") + "'");
	          } else {//纸箱
	            if (((HashMap)delivers.get(j)).get("fouted").toString().equals("1"))
	            {
	              this.deliverapplyDao.updateDeliverapplyStateByDelivers(((HashMap)delivers.get(j)).get("fdeliversId").toString(), 6);
	            }
	            else { this.deliverapplyDao.updateDeliverapplyStateByDelivers(((HashMap)delivers.get(j)).get("fdeliversId").toString(), 5);
	            }

	            foutQty = new BigDecimal(((HashMap)delivers.get(j)).get("outQty").toString());
	            List deliverratios = QueryBySql("select fdeliverappid,fdeliverapplynum from t_ord_deliverratio where fdeliverid='" + ((HashMap)delivers.get(j)).get("fdeliversId").toString() + "' ");
	            for (int k = 0; k < deliverratios.size(); ++k) {
	              foutQty = foutQty.multiply(new BigDecimal(((HashMap)deliverratios.get(k)).get("fdeliverapplynum").toString()));
	              ExecBySql("update t_ord_deliverapply set foutQty='" + foutQty + "' where fid = '" + ((HashMap)deliverratios.get(k)).get("fdeliverappid").toString() + "'");
	            }

	          }

	        }

	      }
	      else if (type == 6)
	      {
	        String updateAssembleentry = params.get("updateAssembleentry").toString();
	        String delSaledleiverentry = params.get("delSaledleiverentry").toString();
	        String saledeliverids = params.get("saledeliverids").toString();

	        if (updateAssembleentry.length() > 0) {
	          ExecBySql(updateAssembleentry);
	        }

	        if (delSaledleiverentry.length() > 0) {
	          ExecBySql(delSaledleiverentry);
	        }

	        if (saledeliverids.length() > 0) {
	          String hql = "Delete FROM t_tra_saledeliver where fid in (" + saledeliverids + ")";
	          ExecBySql(hql);
	        }
	      }
	    }

	    params.put("success", Boolean.valueOf(true));
	    return params;
	    }
}