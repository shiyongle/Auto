/*
 * 网蓝
 */

package com.service.productplan;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.util.StringHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dao.IBaseDao;
import com.dao.productplan.ProductplanDao;
import com.dao.supplier.SupplierDao;
import com.dao.system.SimplemessageDao;
import com.model.PageModel;
import com.model.deliverapply.Deliverapply;
import com.model.orderState.OrderState;
import com.model.productplan.OrderInOutBean;
import com.model.productplan.Outwarehouse;
import com.model.productplan.ProductPlan;
import com.model.productplan.Productindetail;
import com.model.productplan.Supplierindetail;
import com.model.storebalance.Storebalance;
import com.model.supplier.Supplier;
import com.model.user.TSysUser;
import com.service.IBaseManagerImpl;
import com.util.JPushUtilNew;
import com.util.Params;


@SuppressWarnings("unchecked")
@Service("productplanManager")
@Transactional(rollbackFor = Exception.class)
public class ProductplanManagerImpl extends IBaseManagerImpl<ProductPlan,java.lang.String> implements ProductplanManager{
	@Autowired
	private ProductplanDao productplanDao;
	@Autowired
	private SimplemessageDao simplemessageDao;
	@Autowired
	private SupplierDao supplierDao;
	
	public IBaseDao<ProductPlan, java.lang.String> getEntityDao() {
		return this.productplanDao;
	}

	@Override
	public PageModel<HashMap<String, Object>> findBySql(String where,
			Object[] queryParams, Map<String, String> orderby, int pageNo,
			int maxResult ,String...t_name) {
		// TODO Auto-generated method stub
		return productplanDao.findBySql(where, queryParams, orderby, pageNo, maxResult,t_name);
	}

	@Override
	public List<ProductPlan> getProductplanList(String where,
			Object[] queryParams, Map<String, String> orderby,boolean...history) {
		// TODO Auto-generated method stub
		return productplanDao.getProductplanList(where, queryParams, orderby,history);
	}

	@Override
	public void updateReceiveState(String fid, String userid) {
		productplanDao.updateReceiveState(fid, userid);
	}

	@Override
	public void updateUnReceiveState(String fid,TSysUser user) throws Exception{
		Params p=new Params();
//		String sql = "select 1 from t_ord_productplan where fid in (%s) and ifnull(faffirmed,0)=0";
//		if(this.QueryExistsBySql(String.format(sql,fid))){
		String sql = "select 1 from t_ord_productplan where fid in (:fid) and ifnull(faffirmed,0)=0";
		p.put("fid", fid);
		if(this.QueryExistsBySql(sql,p)){
			throw new Exception("该订单未接收");
		}
//		sql = "SELECT 1 FROM t_ord_productplan where fimportEas=1 and fissync=1 and fid in(%s)";
//		if(this.QueryExistsBySql(String.format(sql,fid))){
		sql = "SELECT 1 FROM t_ord_productplan where fimportEas=1 and fissync=1 and fid in(:fid)";
		if(this.QueryExistsBySql(sql,p)){
			throw new Exception("订单已导入接口不能取消接收,请先从接口退回！");
		}
//		 sql = "SELECT 1 FROM t_ord_productplan where fcloseed=1 and fid in(%s)";
//		if(this.QueryExistsBySql(String.format(sql,fid))){
		 sql = "SELECT 1 FROM t_ord_productplan where fcloseed=1 and fid in(:fid)";
		if(this.QueryExistsBySql(sql,p)){
			throw new Exception("订单已关闭不能取消接收！");
		}
		
//		sql = "SELECT 1 FROM t_ord_productplan where fcreateboard=1 and fid in(%s)";
//		if(this.QueryExistsBySql(String.format(sql,fid))){
		sql = "SELECT 1 FROM t_ord_productplan where fcreateboard=1 and fid in(:fid)";
		if(this.QueryExistsBySql(sql,p)){
			throw new Exception("订单已生成纸板采购,不允许取消接收！");
		}
		
//		sql = "SELECT fid FROM t_inv_productindetail where fproductplanid in(%s)";
//		if(this.QueryExistsBySql(String.format(sql,fid))){
		sql = "SELECT fid FROM t_inv_productindetail where fproductplanid in(:fid)";
		if(this.QueryExistsBySql(sql,p)){
			throw new Exception("已入库订单不能取消接收!");
		}
		//增加直接取消快速下单;			
//		sql="SELECT QUOTE(fid) fid FROM t_ord_deliverapply WHERE IFNULL(fmaterialfid,'')<>'' and  fiscreate=1 and fplanid in (%s)";
//		List<HashMap<String, Object>> custList=this.QueryBySql(String.format(sql,fid),p);
		sql="SELECT QUOTE(fid) fid FROM t_ord_deliverapply WHERE IFNULL(fmaterialfid,'')<>'' and  fiscreate=1 and fplanid in (:fid)";
		List<HashMap<String, Object>> custList=this.QueryBySql(sql,p);
		String cids="";//符合记录fid
		StringBuilder custids=new StringBuilder();
		for(HashMap<String, Object> m : custList){
			custids.append(m.get("fid")+",");
		}
		cids = custids.toString();
		HashMap<String, String>  map= new HashMap<String, String>();
		map.put("productplanid",fid);
		if(!cids.equals("")){
			cids="("+cids.substring(0, cids.length()-1)+")";
		    sql="select forderid ID from t_ord_productplan where fdeliverapplyid in "+cids;//获取orderid
			String forderid=productplanDao.getCondition(productplanDao.getFidclsBySql(sql));
			 if(this.QueryExistsBySql("select fid from t_ord_deliverorder where (fouted = 1  or fimporteas=1 or fassembleQty>0) and fsaleorderid "+forderid)){
				throw new Exception("配送信息已发货或已经装配或已导入,不能取消！");
			 }
			 //删除占用表
			 sql = "SELECT fid ID FROM t_inv_storebalance s where  s.fsaleorderid  "+forderid;
			 String storeid = productplanDao.getCondition(productplanDao.getFidclsBySql(sql));
			map.put("applyfid", cids);
			map.put("orderfid",forderid);
			map.put("storeid", storeid);
		}
		productplanDao.updateUnreceiveState(map);
		if(map.containsKey("applyfid")){//快速下单生成的推送消息
			 //取消订单推送消息
			sql=" SELECT _custpdtname,_suppliername,_spec,c.famount,ifnull(u.fname,'') fname,us.fuserid,c.fnumber FROM t_ord_deliverapply_card_mv c  LEFT JOIN t_bd_usercustomer us ON c.fcustomerid=us.fcustomerid  INNER JOIN t_sys_user u ON u.fid=us.fuserid WHERE  c.fid IN "+map.get("applyfid");
			p.getData().clear();
			List<HashMap<String, Object>> appinfo =this.QueryBySql(sql,p);
					for(HashMap<String, Object> userinfo:appinfo){
						String fcontent = "尊敬的"+userinfo.get("fname")+"用户！制造商'"+userinfo.get("_suppliername")+"'取消了该订单'"+userinfo.get("fnumber")+"'(产品:"+userinfo.get("_custpdtname")+";规格:"+userinfo.get("_spec")+";数量 "+userinfo.get("famount")+"),详细内容请到'我的订单'中查看!";
						HashMap<String, String> messagemap = new HashMap<String, String>();
						messagemap.put("fcontent", fcontent);
						messagemap.put("frecipientid", (String)userinfo.get("fuserid"));
						messagemap.put("ftype", "3");
						messagemap.put("fsender",user.getFid());
						simplemessageDao.MessageProjectEvaluationWithSender(messagemap);
							fcontent="\""+user.getFname()+"\"已取消单号为"+userinfo.get("fnumber")+"的订单，请查收";
							JPushUtilNew.SendPushToAll((String)userinfo.get("fname"),fcontent);//新版推送消息
							JPushUtilNew.SendPushToIOS((String)userinfo.get("fname"),fcontent);//新版推送消息
						
					}
				
		}
	}

	@Override
	public String orderInOut(ProductPlan productplan,OrderInOutBean params) {
		Supplier supplier = getSuppier(productplan);
		if(params.isInStock()){		//入库
			updateStoreBalance(productplan,supplier,params);
			updateSaleOrder(productplan,params);
			updateProductPlan(productplan,params);
			updateDelivers(productplan);
			saveProductInDetail(productplan,supplier,params);
			return null;
		}else{
			String error = execOutStore(productplan.getFid(),params.getStockOut(),supplier.getFwarehouseid(),
					supplier.getFwarehousesiteid(),params.getUserid(),productplan.getFproductdefid(),
					productplan.getForderid(),productplan.getFparentorderid());
			if(error==null){
				saveOutwarehouse(productplan,supplier,params);
				return null;
			}else{
				return error;
			}
		}
	}

	private String execOutStore(String fproductplanid, int amt,
			String FWarehouseID, String FWarehouseSiteID, String userid,
			String fproductid, String forderid, String forderEntryid) {

		String sql = "SELECT ifnull(w.fbalanceqty,0) fbalanceqty,fwarehousesiteid,fid FROM t_inv_storebalance w where ifnull(w.fbalanceqty,0)>0 and w.fwarehouseId='"
				+ FWarehouseID
				+ "' and fproductplanID='" + fproductplanid + "'";
		List<HashMap<String, Object>> balancelist = productplanDao.QueryBySql(sql);
		if(balancelist.size()<=0)
		{
			sql="select fnumber from t_ord_productplan where fid ='"+fproductplanid+"'";
			balancelist = productplanDao.QueryBySql(sql);
			return "制造商订单‘"+balancelist.get(0).get("fnumber").toString()+"’库存不足";
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
						sql+=" where w.fid='"+fbalanceid+"' ";
						ExecBySql(sql);
					}
					/*else
					{
						sql = "update t_inv_storebalance w set w.fbalanceqty = w.fbalanceqty - "+fbalanceqty+",w.foutqty = w.foutqty + "+fbalanceqty;
					}*/
					
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
				sql+=" where w.fid='"+fbalanceid+"' ";
				ExecBySql(sql);
			}
			/*else
			{
				sql = "update t_inv_storebalance w set w.fbalanceqty = w.fbalanceqty - "+fbalanceqty+",w.foutqty = w.foutqty + "+fbalanceqty;
			}*/
			
			sumamt=sumamt-fbalanceqty.intValue();
		}
		if(sumamt>0)
		{
			sql="select fnumber from t_ord_productplan where fid ='"+fproductplanid+"'";
			balancelist = productplanDao.QueryBySql(sql);
			return "制造商订单‘"+balancelist.get(0).get("fnumber").toString()+"’库存不足,库存数量："+(amt-sumamt);
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
		return null;
	}
	
	private void saveOutwarehouse(ProductPlan productplan, Supplier supplier,
			OrderInOutBean params) {
		Outwarehouse obj = new Outwarehouse();
		obj.setFid(CreateUUid());
		obj.setFcreatetime(new Date());
		obj.setFlastupdatetime(null);
		obj.setFlastupdateuserid(null);
		obj.setFremak("");
		obj.setFaudited(1);
		obj.setFaudittime(new Date());
		obj.setForderentryid(productplan.getFparentorderid());
		obj.setFproductplanid(productplan.getFid());
		obj.setFcreatorid(params.getUserid());
		obj.setFoutqty(new BigDecimal(params.getStockOut()));
		obj.setFwarehouseid(supplier.getFwarehouseid());
		obj.setFwarehousesiteid(supplier.getFwarehousesiteid());
		obj.setFproductid(productplan.getFproductdefid());
		obj.setFsaleorderid(productplan.getForderid());
		obj.setFauditorid(params.getUserid());
		obj.setFsupplierid(supplier.getFid());
		obj.setFtraitid("");
		save(obj);
	}

	private void saveProductInDetail(ProductPlan productplan,
			Supplier supplier,OrderInOutBean params) {
		Productindetail obj = new Productindetail();
		obj.setFnumber(getNumber("t_inv_productindetail", "R", 4,false));
		obj.setFid(CreateUUid());
		obj.setFcreatorid(params.getUserid());
		obj.setFcreatetime(new Date());
		obj.setFaudittime(new Date());
		obj.setFupdatetime(null);
		obj.setFupdateuserid(null);
		obj.setFdescription("");
		obj.setFtype(1);
		obj.setFaudited(1);
		obj.setFauditorid(params.getUserid());
		obj.setFsupplierid(supplier.getFid());
		obj.setForderentryid(productplan.getFparentorderid());
		obj.setFproductplanid(productplan.getFid());
		obj.setFinqty(new BigDecimal(params.getStockIn()));
		obj.setFwarehouseId(supplier.getFwarehouseid());
		obj.setFwarehouseSiteId(supplier.getFwarehousesiteid());
		obj.setFproductId(productplan.getFproductdefid());
		obj.setFsaleOrderId(productplan.getForderid());
		obj.setFtraitid("");
		this.save(obj);
		
		if("04d8efb5-5f3a-11e4-bdb9-00ff6b42e1e5".equals(productplan.getFcustomerid())){//客户为东经包装的制造商订单才添加接口入库明细表
			Supplierindetail su = new Supplierindetail();
			su.setFid(this.CreateUUid());
			su.setFpcmordernumber(productplan.getFpcmordernumber());
			su.setFsupplierid(supplier.getFname());
			su.setFamount(params.getStockIn());
			su.setFinqty(params.getStockIn());
			su.setFintime(new Date());
			su.setFstate(0);
			this.save(su);
		}
	}

	private void updateDelivers(ProductPlan productplan) {
		int STATE = 4;
		String sql = "select group_concat(quote(fapplayid)) fids from t_ord_delivers where fid='"+productplan.getForderid()+"'";
		String fids = productplanDao.getStringValue(sql,"fids");
		if(fids != null){
			sql = "from Deliverapply where fid in ("+fids+")";
			List<Deliverapply> deliverapplyList = productplanDao.QueryByHql(sql);
			for(Deliverapply deliverapply: deliverapplyList){
				deliverapply.setFstate(STATE);
				createOrderStateInfo(deliverapply.getFid(), STATE, true);
				this.save(deliverapply);
			}
		}
	}
	
	private void createOrderStateInfo(String fdeliverapplyid,int state,boolean forward){
		if(forward == true){
			OrderState obj = new OrderState();
			obj.setFid(this.CreateUUid());
			obj.setFdeliverapplyid(fdeliverapplyid);
			obj.setFstate(state);
			obj.setFcreatetime(new Date());
			this.save(obj);
		}else{
			String sql = "delete from t_ord_orderstate where fdeliverapplyid='"+fdeliverapplyid+"' and fstate >"+state;
			this.ExecBySql(sql);
		}
	}

	private void updateProductPlan(ProductPlan productplan,OrderInOutBean params) {
		int amount = params.getStockIn();
		int fstate = productplan.getFstockinqty()+amount>=productplan.getFamount()? 3 : 2;
		productplan.setFstate(fstate);	//3-全部入库
		productplan.setFstoreqty(productplan.getFstoreqty() + amount);
		productplan.setFstockinqty(productplan.getFstockinqty() + amount);
		productplan.setFcloseed(fstate==3?1:0);
		productplan.setFisfinished(fstate==3?1:0);
		this.update(productplan);
	}

	private void updateSaleOrder(ProductPlan productplan, OrderInOutBean params) {
		String id = productplan.getFparentorderid();
		if(!StringUtils.isEmpty(id)){
			String sql = "update t_ord_saleorder set fstoreqty = ifnull(fstoreqty,0)+"+params.getStockIn()+",fstockinqty = fstockinqty+"+params.getStockIn()+" where fid='"+id+"' ";
			ExecBySql(sql);
		}
	}

	private void updateStoreBalance(ProductPlan productplan,
			Supplier supplier,OrderInOutBean params) {
		Storebalance storeBalance;
		String hql = "from Storebalance where fproductplanId = '"+productplan.getFid()+"' and fwarehouseId='"+supplier.getFwarehouseid()+"' and fwarehouseSiteId = '"+supplier.getFwarehousesiteid()+"'";
		List<Storebalance> storebalanceList = (List<Storebalance>) productplanDao.QueryByHql(hql);
		if(storebalanceList.size() != 0){
			storeBalance = storebalanceList.get(0);
			storeBalance.setFinqty(storeBalance.getFinqty()+params.getStockIn());
			storeBalance.setFbalanceqty(storeBalance.getFbalanceqty()+params.getStockIn());
		}else{
			storeBalance = new Storebalance();
			storeBalance.setFid(CreateUUid());
			storeBalance.setFcreatetime(new Date());
			storeBalance.setFupdatetime(null);
			storeBalance.setFupdateuserid(null);
			storeBalance.setFdescription("");
			storeBalance.setFoutqty(0);
			storeBalance.setFallotqty(0);
			storeBalance.setFtype(1);	//1为订单类型
			storeBalance.setFcreatorid(params.getUserid());
			storeBalance.setFinqty(params.getStockIn());
			storeBalance.setFproductplanId(productplan.getFid());
			storeBalance.setFbalanceqty(params.getStockIn());
			storeBalance.setFwarehouseId(supplier.getFwarehouseid());
			storeBalance.setFwarehouseSiteId(supplier.getFwarehousesiteid());
			storeBalance.setFproductId(productplan.getFproductdefid());
			storeBalance.setFsaleorderid(productplan.getForderid());
			storeBalance.setForderentryid(productplan.getFparentorderid());
			storeBalance.setFsupplierID(supplier.getFid()); 
			storeBalance.setFtraitid("");
		}
		this.saveOrUpdate(storeBalance);
	}

	private Supplier getSuppier(ProductPlan productplan) {
		Supplier supplier = supplierDao.get(productplan.getFsupplierid());
		if(StringUtils.isEmpty(supplier.getFwarehouseid())){
			supplier.setFwarehouseid("478a9181-0dc4-11e5-9395-00ff61c9f2e3");
			supplier.setFwarehousesiteid("5315e691-0dc4-11e5-9395-00ff61c9f2e3");
		}
		return supplier;
	}

	@Override
	public HashMap<String, Object> getProductplanById(String id,
			boolean... t_name) {
		try
		{
			String tablename="t_ord_productplan";
			if(t_name.length!=0&t_name[0]==true)
			{
				tablename+="_h";
			}
			String sql="select * from "+tablename+" where fid=:fid";
			Params param=new Params();
			param.put("fid", id);
			List<HashMap<String, Object>> list=this.productplanDao.QueryBySql(sql, param);
			if(list!=null)
			{
				return list.get(0);
			}
		}
		catch(Exception e)
		{
			System.out.print(e.toString());
		}
		return null;
	}


}