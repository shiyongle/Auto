package Com.Dao.Inv;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.hibernate.util.StringHelper;
import org.springframework.stereotype.Service;

import Com.Base.Dao.BaseDao;
import Com.Base.Util.DJException;
import Com.Base.Util.SpringContextUtils;
import Com.Base.Util.params;
import Com.Dao.System.IBaseSysDao;
import Com.Dao.order.IDeliverapplyDao;
import Com.Dao.order.IProductPlanDao;
import Com.Entity.Inv.Storebalance;
import Com.Entity.System.Useronline;
import Com.Entity.order.ProductPlan;
@Service("StorebalanceDao")
public class StorebalanceDao extends BaseDao implements IStorebalanceDao {

	private IProductPlanDao ProductPlanDao=(IProductPlanDao) SpringContextUtils.getBean("productPlanDao");
	@Resource
	private IDeliverapplyDao deliverapplyDao;
	@Resource
	private IBaseSysDao baseSysDao;
	@Override
	public HashMap<String, Object> ExecSave(Storebalance cyc) {
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
	public Storebalance Query(String fid) {
		// TODO Auto-generated method stub
		return (Storebalance) this.getHibernateTemplate().get(
				Storebalance.class, fid);
	}

	/**
	 * Q:参数过多
	 */
	@Override
	public void ExecInStore(String fproductplanid, int amt,
			String FWarehouseID, String FWarehouseSiteID, String userid,
			String fproductid, String forderid, String forderEntryid, String supplierid,String ftraitid) {
		// TODO Auto-generated method stub
		if (amt<=0)
		{
			return;
		}
		String sqls = "select 1 from t_ord_productplan where fid='"+fproductplanid+"' and fboxtype=1";
		if(!this.QueryExistsBySql(sqls)){
			if(StringHelper.isEmpty(forderEntryid) || StringHelper.isEmpty(forderid)
					|| StringHelper.isEmpty(fproductid) ||StringHelper.isEmpty(userid) || StringHelper.isEmpty(fproductplanid)|| StringHelper.isEmpty(FWarehouseID) || StringHelper.isEmpty(FWarehouseSiteID))
			{
				throw new DJException("参数不正确!");
			}
		}
		String sql = "SELECT fid,fallotqty FROM t_inv_storebalance w where w.fwarehouseId='"+FWarehouseID+"' and w.fwarehouseSiteId='"+FWarehouseSiteID+"' and fproductplanID='"+fproductplanid+"'";
		if(ftraitid!=null && !ftraitid.equals(""))
		{
			sql=sql+" and w.ftraitid='"+ftraitid+"'";
		}
		List<HashMap<String,Object>> balancelist= QueryBySql(sql);
		int isallot=0;//是否调拨标记 1:为调拨
		if(userid.contains("普通调拨"))
		{
			userid=userid.split(",")[0];
			isallot=1;
		}
		//新增
		if (balancelist.size()<=0) {
			if(isallot==1)
			{
				throw (new DJException("不存在库存记录，不能调拨！"));
			}
			Storebalance sbinfo = new Storebalance();
			sbinfo.setFid(CreateUUid());
			sbinfo.setFproductplanId(fproductplanid);
			sbinfo.setFcreatorid(userid);
			sbinfo.setFcreatetime(new Date());
			sbinfo.setFupdatetime(null);
			sbinfo.setFupdateuserid(null);
			sbinfo.setFinqty(amt);
			sbinfo.setFoutqty(0);
			sbinfo.setFbalanceqty(amt);
			sbinfo.setFwarehouseId(FWarehouseID);
			sbinfo.setFwarehouseSiteId(FWarehouseSiteID);
			sbinfo.setFproductId(fproductid);
			sbinfo.setFdescription(null);
			sbinfo.setFsaleorderid(forderid);
			sbinfo.setForderentryid(forderEntryid);
			
			//1为订单类型
			sbinfo.setFtype(1);
			
			sbinfo.setFsupplierID(supplierid); 
			sbinfo.setFallotqty(0);
			sbinfo.setFtraitid(ftraitid);
			ExecSave(sbinfo);
		}else{
			//修改
			HashMap balanceinfo = balancelist.get(0);
			String fid = balanceinfo.get("fid").toString();
			if(isallot==1)
			{
				int allotnum=setFallotQty(new Integer(balanceinfo.get("fallotqty").toString()),amt);
				sql = "update t_inv_storebalance w set w.fbalanceqty = w.fbalanceqty + "+amt+",w.finqty = w.finqty + "+amt+",w.fallotqty="+allotnum+" where w.fid='"+fid+"' ";
			}else
			{
			sql = "update t_inv_storebalance w set w.fbalanceqty = w.fbalanceqty + "+amt+",w.finqty = w.finqty + "+amt+" where w.fid='"+fid+"' ";
			}
			ExecBySql(sql);
		}
		
//		List<HashMap<String,Object>> qtylist= QueryBySql("SELECT ifnull(sum(w.fbalanceqty),0) fbalanceqty,ifnull(sum(w.finqty),0) finqty FROM t_inv_storebalance w where w.forderentryid='"+forderEntryid+"' ");
//		BigDecimal fstockinqty = new BigDecimal(0);
//		BigDecimal fstoreqty = new BigDecimal(0);
//		if (qtylist.size()>0) {
//			HashMap qtyinfo = qtylist.get(0); 
//			fstoreqty = new BigDecimal(qtyinfo.get("fbalanceqty").toString());
//			fstockinqty = new BigDecimal(qtyinfo.get("finqty").toString());
//		}				
		if(forderEntryid!=null&&!"".equals(forderEntryid)){
		sql = "update t_ord_saleorder set fstoreqty = ifnull(fstoreqty,0)+"+amt+",fstockinqty = fstockinqty+"+amt+" where fid='"+forderEntryid+"' ";
		ExecBySql(sql);
		}
//		
//		qtylist= QueryBySql("SELECT ifnull(sum(w.fbalanceqty),0) fbalanceqty,ifnull(sum(w.finqty),0) finqty FROM t_inv_storebalance w where w.fproductplanID='"+fproductplanid+"'");
//		fstockinqty = new BigDecimal(0);
//		fstoreqty = new BigDecimal(0);
//		if (qtylist.size()>0) {
//			HashMap qtyinfo = qtylist.get(0); 
//			fstoreqty = new BigDecimal(qtyinfo.get("fbalanceqty").toString());
//			fstockinqty = new BigDecimal(qtyinfo.get("finqty").toString());
//		}	
		int fstate;
		int mount = 0;
		sql = "select ifnull(famount,0) famount,ifnull(fstockinqty,0) fstockinqty from t_ord_productplan where fid='"+fproductplanid+"'";
		List<HashMap<String,Object>> list = this.QueryBySql(sql);
		mount = (Integer.parseInt(list.get(0).get("famount").toString())-Integer.parseInt(list.get(0).get("fstockinqty").toString()));
		if(amt<mount){//入库数量小于（数量-已入库数量）
			fstate = 2;//"部分入库"
		}else{
			fstate = 3;//"全部入库";
		}
		
		//状态为已入库，订单未关闭状态，并且完成为是
		sql = "update t_ord_productplan set  fstate="+fstate+",fstoreqty = fstoreqty+"+amt+",fstockinqty = fstockinqty+"+amt+",fcloseed="+(fstate==3?1:0)+",fisfinished="+(fstate==3?1:0)+" where fid='"+fproductplanid+"' ";
		ExecBySql(sql);
		if(!StringUtils.isEmpty(fproductplanid)){//更新纸板的已入库状态
		sql="update t_ord_deliverapply set fstate=4 where fplanid='"+fproductplanid+"' and fboxtype=1  ";
		ExecBySql(sql);
		}
		sql = "select fid from t_ord_delivers where fsaleorderid ='"+forderid+"'";
		HashMap<String, String> map = baseSysDao.getMapData(sql);
		if(map!=null){		//安全库存下单，生产订单跟要货管理没关联
			deliverapplyDao.updateDeliverapplyStateByDelivers(map.get("fid"), 4);
		}
	}

	@Override
	public void ExecOutStore(String fproductplanid, int amt,
			String FWarehouseID, String FWarehouseSiteID, String userid,
			String fproductid, String forderid, String forderEntryid,String ftraitid) {
		// TODO Auto-generated method stub
		int isallot=0;//是否调拨标记 1:为调拨
		if(userid.contains("普通调拨"))
		{
			userid=userid.split(",")[0];
			isallot=1;
		}
		if (amt<=0)
		{
			throw new DJException("出库数量为0!");
		}
		String sqls = "select 1 from t_ord_productplan where fid='"+fproductplanid+"' and fboxtype=1";
		if(!this.QueryExistsBySql(sqls)){
		if(StringHelper.isEmpty(forderEntryid) || StringHelper.isEmpty(forderid)
				|| StringHelper.isEmpty(fproductid) ||StringHelper.isEmpty(userid) || StringHelper.isEmpty(fproductplanid)|| StringHelper.isEmpty(FWarehouseID) )
		{
			throw new DJException("参数不正确!");
		}
		}
		String sql = "SELECT ifnull(w.fbalanceqty,0) fbalanceqty,fwarehousesiteid,fid FROM t_inv_storebalance w where ifnull(w.fbalanceqty,0)>0 and w.fwarehouseId='"
				+ FWarehouseID
				+ "' and fproductplanID='" + fproductplanid + "'";
		if(ftraitid!=null && !ftraitid.equals(""))
		{
			sql=sql+" and w.ftraitid='"+ftraitid+"'";
		}
		List<HashMap<String, Object>> balancelist = QueryBySql(sql);
		if(balancelist.size()<=0)
		{
			sql="select fnumber from t_ord_productplan where fid ='"+fproductplanid+"'";
			balancelist = QueryBySql(sql);
			throw new DJException("制造商订单‘"+balancelist.get(0).get("fnumber").toString()+"’库存不足");
		}
		int sumamt=amt;
		BigDecimal fbalanceqty;
		String fbalanceid="";
		HashMap<String, Object> balanceinfo;
		//有指定库位的先扣库位的
		if(!StringHelper.isEmpty(FWarehouseSiteID))
		{
			for(int i=0;i<balancelist.size();i++)
			{
				balanceinfo= balancelist.get(i);
				if(balanceinfo.get("fwarehousesiteid").equals(FWarehouseSiteID))
				{
					fbalanceqty=new BigDecimal(balanceinfo.get("fbalanceqty").toString());
					fbalanceid=balanceinfo.get("fid").toString();
					if(fbalanceqty.compareTo(new BigDecimal(sumamt))>=0)
					{
						sql = "update t_inv_storebalance w set w.fbalanceqty = w.fbalanceqty - "+amt+",w.foutqty = w.foutqty + "+amt;
						if(isallot==1)
						{
							sql=sql+" ,fallotqty=fallotqty+"+amt;
						}
					}
					else
					{
						sql = "update t_inv_storebalance w set w.fbalanceqty = w.fbalanceqty - "+fbalanceqty+",w.foutqty = w.foutqty + "+fbalanceqty;
						if(isallot==1)
						{
							sql=sql+" ,fallotqty=fallotqty+"+fbalanceqty;
						}
					}
					sql+=" where w.fid='"+fbalanceid+"' ";
					ExecBySql(sql);
					sumamt=sumamt-fbalanceqty.intValue();
					balancelist.remove(i);
					break;
				}
			}
		}
		//指定库位库存不扣，从其他库位扣库存；
		for(int i=0;i<balancelist.size();i++)
		{
			if(sumamt<=0)
			{
				break;
			}
			balanceinfo= balancelist.get(i);
			fbalanceqty=new BigDecimal(balanceinfo.get("fbalanceqty").toString());
			fbalanceid=balanceinfo.get("fid").toString();
			if(fbalanceqty.compareTo(new BigDecimal(sumamt))>=0)
			{
				sql = "update t_inv_storebalance w set w.fbalanceqty = w.fbalanceqty - "+sumamt+",w.foutqty = w.foutqty + "+sumamt;
				if(isallot==1)
				{
					sql=sql+" ,fallotqty=fallotqty+"+sumamt;
				}
				
			}
			else
			{
				sql = "update t_inv_storebalance w set w.fbalanceqty = w.fbalanceqty - "+fbalanceqty+",w.foutqty = w.foutqty + "+fbalanceqty;
				if(isallot==1)
				{
					sql=sql+" ,fallotqty=fallotqty+"+fbalanceqty;
				}
			}
			sql+=" where w.fid='"+fbalanceid+"' ";
			ExecBySql(sql);
			sumamt=sumamt-fbalanceqty.intValue();
		}
		if(sumamt>0)
		{
			sql="select fnumber from t_ord_productplan where fid ='"+fproductplanid+"'";
			balancelist = QueryBySql(sql);
			throw new DJException("制造商订单‘"+balancelist.get(0).get("fnumber").toString()+"’库存不足,库存数量："+(amt-sumamt));
		}
		if(forderEntryid!=null&&!"".equals(forderEntryid)){
		sql = "update t_ord_saleorder set fstoreqty = fstoreqty - "
				+ amt
				+ ",fstockoutqty = fstockoutqty + "
				+ amt
				+ " where fid='"
				+ forderEntryid
				+ "' ";
		ExecBySql(sql);
		}
		//20150529增加异常出库时，更改订单状态为2(部分入库);
		sql = "update t_ord_productplan set fstate=2,fstoreqty = fstoreqty - " 
				+ amt
				+ ",fstockoutqty = fstockoutqty + "
				+ amt
				+ " where fid='"
				+ fproductplanid
				+ "' ";
		ExecBySql(sql);
		
	}

	@Override
	public void ExecOutStoreTZ(int amt, String FWarehouseID,
			String FWarehouseSiteID, String userid, String fproductid, String supplierid) {
		// TODO Auto-generated method stub
		String sql="select * from t_inv_storebalance where fwarehouseid='"+FWarehouseID+"' and fwarehousesiteid='"+FWarehouseSiteID+"' and fsupplierid='"+supplierid+"' and fproductid='"+fproductid+"' and ftype=0";
		List<HashMap<String, Object>> balancelist = QueryBySql(sql);
		if(balancelist.size()<=0)
		{
			balancelist=QueryBySql("select fname from t_pdt_productdef where fid='"+fproductid+"'");
			throw new DJException("产品:"+balancelist.get(0).get("fname").toString()+"库存不足,库存数量：0");
		}
		else if(balancelist.size()==1)
		{
			BigDecimal fbalanceqty=new BigDecimal(balancelist.get(0).get("fbalanceqty").toString());
			if(fbalanceqty.compareTo(new BigDecimal(amt))>=0)
			{
				ExecBySql("update t_inv_storebalance w set w.fbalanceqty = w.fbalanceqty - "+amt+",w.foutqty = w.foutqty + "+amt+" where w.fid='"+balancelist.get(0).get("FID").toString()+"' ");
			}
			else
			{
				balancelist=QueryBySql("select fname from t_pdt_productdef where fid='"+fproductid+"'");
				throw new DJException("产品:"+balancelist.get(0).get("fname").toString()+"库存不足,库存数量："+fbalanceqty.toString());
			}
		}
		else
		{
			balancelist=QueryBySql("select fname from t_pdt_productdef where fid='"+fproductid+"'");
			throw new DJException("数据错误;产品:"+balancelist.get(0).get("fname").toString()+"存在多条通知类型的结存");
		}
		
	}

	/**
	 *  调拨数量控制
	 * @param storebalanceT
	 * @param theSetNumber
	 */
	public int setFallotQty(int fallotnum,
			Integer theSetNumber)
	{
		int allotQty =0;
		if (theSetNumber >= 0) {
			allotQty=fallotnum-theSetNumber;
			if(allotQty<0)
			{
				allotQty=0;
//				allotQty=fallotnum;
//				throw (new DJException("调拨数据异常！"));
			}
		}else
		{
			allotQty=fallotnum+theSetNumber;
		}
		 return allotQty;
	}
}
