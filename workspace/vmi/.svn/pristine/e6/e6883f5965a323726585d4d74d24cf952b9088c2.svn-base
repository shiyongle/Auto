package Com.Dao.order;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import javax.annotation.Resource;
import org.hibernate.util.StringHelper;
import org.springframework.stereotype.Service;
import Com.Base.Dao.BaseDao;
import Com.Base.Util.ServerContext;
import Com.Controller.System.VmipdtParamController;
import Com.Dao.System.IBaseSysDao;
import Com.Entity.Inv.Storebalance;
import Com.Entity.System.Simplemessage;
import Com.Entity.System.Supplier;
import Com.Entity.order.ProductPlan;
import Com.Entity.order.Saleorder;

@Service
public class ComplexDao extends BaseDao implements IComplexDao {

	@Resource
	IBaseSysDao baseSysDao;
	@Resource
	VmipdtParamController vmipdtParamController;
	@Resource
	ISaleOrderDao saleOrderDao;
	/**
	 * 历史版本和禁用产品
	 * 
	 */
	@Override
	public void ExecVmiplan(String userid) {
		String sql = "select '' msg,0 fbalanceqty,0 deliversAmt, ifnull(e.ftype,0) ftype,e.fsupplierId fsupplierid,e.fid,customer.fid as fcustomerid,productdef.fassemble,"
				+ "productdef.fiscombinecrosssubs,productdef.fid as fproductid,ifnull(productdef.feffect,0) feffect,productdef.fishistory,"
				+ "e.fminstock,e.forderamount,productdef.fnewtype,productdef.fname as productname "
				+ "from t_pdt_vmiproductparam e,t_pdt_productdef productdef,t_bd_customer customer where "
				+ "customer.fid=productdef.fcustomerid and productdef.fid=e.fproductid";
		List<HashMap<String, Object>> stockList = this.QueryBySql(sql);
		List<HashMap<String, Object>> isHistoryList = baseSysDao.getSubList(stockList, "fishistory", 1);
		List<HashMap<String, Object>> noEffectList = baseSysDao.getSubList(stockList, "feffect", 0);
		stockList.removeAll(isHistoryList);
		stockList.removeAll(noEffectList);
		handleStock(stockList,userid);					// 处理正常的安全库存产品
		handleHistoryStock(isHistoryList);				// 处理历史版本的安全库存产品
		handleNoEffectStock(noEffectList);				// 处理禁用的安全库存产品
	}
	private void handleNoEffectStock(List<HashMap<String, Object>> list) {
		String fidcls = baseSysDao.getIds(list);
		if(fidcls != null){
			String inCondition = baseSysDao.getCondition(fidcls);
			String msg = "未下单，信息:禁用产品不能下单！";
			String sql = "update t_pdt_vmiproductparam set fdescription= '"+msg+"' where fdescription != '"+msg+"' and fid"+inCondition;
			this.ExecBySql(sql);
		}
	}
	private void handleHistoryStock(List<HashMap<String, Object>> list) {
		String fidcls = baseSysDao.getIds(list);
		if(fidcls != null){
			String inCondition = baseSysDao.getCondition(fidcls);
			String msg = "未下单，信息:历史版本产品不能下单！";
			String sql = "update t_pdt_vmiproductparam set fdescription= '"+msg+"' where fdescription != '"+msg+"' and fid"+inCondition;
			this.ExecBySql(sql);
		}
	}
	// 处理正常的安全库存
	private void handleStock(List<HashMap<String, Object>> stockList, String userid) {
		List<Object> beans = new ArrayList<>();
		List<HashMap<String, Object>> orderStockList = baseSysDao.getSubList(stockList, "ftype", 1);	// 获取产品为订单类型的安全库存
		List<HashMap<String, Object>> noticeStockList = baseSysDao.getSubList(stockList, "ftype", 0);	// 获取产品为通知类型的安全库存
		gainBalanceqty(orderStockList,noticeStockList);					// 获取库存量
		gainDeliversAmount(stockList);									// 获取当前要货管理量
		beans.addAll(doHandleStock1(orderStockList,userid));			// 处理订单类型产品，生成生产订单
		beans.addAll(doHandleStock2(noticeStockList,userid));			// 处理通知类型产品，发送信息
		for(Object obj: beans){											// 持久化操作
			if(obj instanceof String){
				this.ExecBySql((String) obj);
			}else{
				this.saveOrUpdate(obj);
			}
		}
	}
	// 处理订单类型产品，生成生产订单、制造商订单、结存记录
	private List<Object> doHandleStock1(List<HashMap<String, Object>> orderStockList, String userid) {
		int minstock;
		int balanceqty;
		int deliversAmt;
		String fnewtype;
		String msg;
		List<Object> beans = new ArrayList<>();
		List<Saleorder> saleOrderList;
		Saleorder saleorder;
		ProductPlan productPlan;
		Storebalance storebalance;
		String sql;
		for(HashMap<String, Object> map: orderStockList){
			saleorder = null;
			fnewtype = map.get("fnewtype").toString();
			msg = (String) map.get("msg");
			minstock = Integer.valueOf(map.get("fminstock").toString());
			balanceqty = Integer.valueOf(map.get("fbalanceqty").toString());
			deliversAmt = Integer.valueOf(map.get("deliversAmt").toString());
			if("1".equals(fnewtype)|| "3".equals(fnewtype)){			// 处理普通纸箱
				if((minstock != 0 && minstock > balanceqty)|| (minstock == 0 && deliversAmt > 0 && deliversAmt > balanceqty)){
					saleorder = getSaleOrder(map, userid);
					msg = "已下单，信息：" + msg;
				}else{
					msg = "未下单，信息：" + msg;
				}
			}else if("2".equals(fnewtype)|| "4".equals(fnewtype)){		// 处理套装纸箱
				if ((minstock != 0 && minstock > balanceqty) ||(minstock == 0 && deliversAmt > balanceqty
						&& deliversAmt > 0)){
					saleOrderList = getSaleOrders(map,userid);
					saleorder = saleOrderList.remove(0);
					beans.addAll(saleOrderList);
					msg = "下单正常，库存数量为" + balanceqty;
				}else{
					msg = "库存大于最小值，不用下单";
				}
			}else{
				continue;
			}
			if(saleorder != null && saleorder.getFtype()==1){			// 正常的生产订单
				productPlan = getProductPlan(saleorder);
				storebalance = getStorebalance(saleorder,productPlan);
				beans.add(productPlan);
				beans.add(storebalance);
				beans.add(saleorder);
			}
			sql = "update t_pdt_vmiproductparam set fdescription='"+msg+"' where fid='"+map.get("fid")+"'";
			beans.add(sql);
		}
		return beans;
	}
	
	// 处理通知类型产品，发送信息
	private List<Object> doHandleStock2(List<HashMap<String, Object>> noticeStockList, String userid) {
		String sql;
		String supplierid;
		String content;
		String msg;
		int minstock;
		int balanceqty;
		int deliversAmt;
		List<Object> beans = new ArrayList<>();
		for(HashMap<String, Object> map: noticeStockList){
			content = null;
			supplierid = (String) map.get("fsupplierid");
			if("39gW7X9mRcWoSwsNJhU12TfGffw=".equals(supplierid)){	// 跳过“东经”制造商
				continue;
			}
			minstock = Integer.valueOf(map.get("fminstock").toString());
			balanceqty = Integer.valueOf(map.get("fbalanceqty").toString());
			deliversAmt = Integer.valueOf(map.get("deliversAmt").toString());
			msg = (String) map.get("msg");
			if(minstock > balanceqty && minstock != 0){
				content = "产品：" + map.get("productname")+ "库存不足，请及时入库; " + msg;
				msg = "已发通知，信息：" + msg;
			}else if (minstock == 0 && deliversAmt > balanceqty
					&& deliversAmt > 0) {
				content = "最小库存量为0，产品：" + map.get("productname")+ "，所有要货数量大于实际库存，请及时备货; 当前产品所有要货数量:"+deliversAmt+","+msg;
				msg = "已发通知，信息：" + msg;
			}else{
				msg = "不用通知，信息：" + msg;
			}
			if(content != null){
				beans.addAll(getSimpleMessages(supplierid,userid,content));
			}
			sql = "update t_pdt_vmiproductparam set fdescription='"+msg+"' where fid='"+map.get("fid")+"'";
			beans.add(sql);
		}
		return beans;
	}
	/**
	 * 获取制造商关联的所有用户的消息
	 * @param supplierid
	 * @param userid
	 * @param content
	 * @return
	 */
	private List<Simplemessage> getSimpleMessages(String supplierid,
			String userid, String content) {
		Simplemessage msgObj;
		List<Simplemessage> beans = new ArrayList<>();
		String sql = "select fuserid from t_bd_usersupplier where fsupplierid=?";
		List<HashMap<String, Object>> userList = this.QueryBySql(sql,supplierid);
		for(HashMap<String, Object> map: userList){
			msgObj = new Simplemessage();
			msgObj.setFid(this.CreateUUid());
			msgObj.setFcontent(content);
			msgObj.setFhaveReaded(0);
			msgObj.setFrecipient((String)map.get("fuserid"));
			msgObj.setFremark(null);
			msgObj.setFsender(userid);
			msgObj.setFtime(new Date());
			beans.add(msgObj);
		}
		return beans;
	}
	/**
	 * 普通纸箱生成生产订单
	 * @param map
	 * @param userid
	 * @return
	 */
	private Saleorder getSaleOrder(HashMap<String, Object> map,
			String userid) {
		Date now = new Date();
		Calendar time = Calendar.getInstance();
		time.set(Calendar.MINUTE, 0);
		time.set(Calendar.SECOND, 0);
		time.set(Calendar.DATE, time.getTime().getDate() + 3);
		time.set(Calendar.HOUR_OF_DAY, 9);
		Date arriveDate = time.getTime();
		Saleorder soinfo = new Saleorder();
		soinfo.setFtype(1);
		soinfo.setFid(this.CreateUUid());
		soinfo.setForderid(this.CreateUUid());
		soinfo.setFcreatorid(userid);
		soinfo.setFcreatetime(now);
		soinfo.setFlastupdatetime(now);
		soinfo.setFlastupdateuserid(userid);
		soinfo.setFproductdefid((String) map.get("fproductid"));
		soinfo.setFentryProductType(0);
		soinfo.setFamount(Integer.parseInt(map.get("forderamount").toString()));
		soinfo.setFcustomerid((String) map.get("fcustomerid"));
		soinfo.setFarrivetime(arriveDate);
		soinfo.setFbizdate(now);
		soinfo.setFaudited(1);
		soinfo.setFauditorid(userid);
		soinfo.setFaudittime(now);
		soinfo.setFamountrate(1);
		soinfo.setFordertype(1);
		soinfo.setFallot(0);
		soinfo.setFstockinqty(0);
		soinfo.setFstockoutqty(0);
		soinfo.setFstoreqty(0);
		soinfo.setFseq(1);
		soinfo.setFimportEas(0);
		soinfo.setFnumber(ServerContext.getNumberHelper().getNumber("t_ord_saleorder", "Z", 4, false));
		soinfo.setFassemble("1".equals(map.get("FAssemble"))?1:0);
		soinfo.setFiscombinecrosssubs("1".equals(map.get("fiscombinecrosssubs"))?1:0);
		soinfo.setFsupplierid((String) map.get("fsupplierid"));
		Supplier supplier = (Supplier) this.Query(Supplier.class, soinfo.getFsupplierid());
		soinfo.setSupplier(supplier);
		if(StringHelper.isEmpty(supplier.getFwarehouseid()) || StringHelper.isEmpty(supplier.getFwarehousesiteid())){
			soinfo.setFdescription("制造商:"+supplier.getFname()+"没有设置默认仓库或库位");
			soinfo.setFtype(2);
		}else{
			soinfo.setFallot(1);
			soinfo.setFallotorid(userid); 
			soinfo.setFallottime(new Date());
		}
		return soinfo;
	}
	
	/**
	 * 获取套装产品的生产订单
	 * @param productInfoList
	 * @return
	 */
	private List<Saleorder> getSaleOrders(HashMap<String, Object> map,String userid) {
		HashMap<String, Object> productInfo;
		Saleorder soinfo;
		List<Saleorder> saleOrderList = new ArrayList<>();
		int orderamount = Integer.parseInt(map.get("forderamount").toString());
		String orderid = this.CreateUUid();
		String productid = (String) map.get("fproductid");
		String customerid = (String) map.get("fcustomerid");
		String supplierid = (String) map.get("fsupplierid");
		String fordernumber = ServerContext.getNumberHelper()
				.getNumber("t_ord_saleorder", "Z", 4, false);
		Date now = new Date();
		Calendar time = Calendar.getInstance();
		time.set(Calendar.MINUTE, 0);
		time.set(Calendar.SECOND, 0);
		time.set(Calendar.DATE, time.getTime().getDate() + 3);
		time.set(Calendar.HOUR_OF_DAY, 9);
		Date arriveDate = time.getTime();
		Supplier supplier = (Supplier) this.Query(Supplier.class, supplierid);
		List<HashMap<String, Object>> productInfoList = vmipdtParamController.getAllProductSuit(productid);
		for (int k = 0; k < productInfoList.size(); k++) {
			productInfo = (HashMap<String, Object>) productInfoList.get(k);
			soinfo = new Saleorder();
			soinfo.setFassemble("1".equals(map.get("FAssemble"))?1:0);
			soinfo.setFiscombinecrosssubs("1".equals(map.get("fiscombinecrosssubs"))?1:0);
			if (k == 0) {
				soinfo.setFamount(orderamount);
				soinfo.setFproductdefid(productid);
				soinfo.setFsuitProductId(productid);
				soinfo.setSupplier(supplier);
			} else {
				soinfo.setFparentOrderEntryId(productInfo.get(
						"ParentOrderEntryId").toString());
				soinfo.setFamount(orderamount
						* new Integer(productInfo.get(
								"amountRate").toString()));
				soinfo.setFproductdefid(productInfo.get("fid")
						.toString());
			}
			soinfo.setFtype(1);
			soinfo.setFordertype(2);
			soinfo.setFentryProductType(new Integer(productInfo.get("entryProductType").toString()));
			soinfo.setFid((String)productInfo.get("orderEntryID"));
			soinfo.setFseq((k + 1));
			soinfo.setFimportEas(0);
			soinfo.setFamountrate(new Integer(productInfo.get("amountRate").toString()));
			soinfo.setForderid(orderid);
			soinfo.setFcreatorid(userid);
			soinfo.setFcreatetime(now);
			soinfo.setFlastupdatetime(now);
			soinfo.setFlastupdateuserid(userid);
			soinfo.setFnumber(fordernumber);
			soinfo.setFcustomerid(customerid);
			soinfo.setFarrivetime(arriveDate);
			soinfo.setFbizdate(now);
			soinfo.setFaudited(1);
			soinfo.setFauditorid(userid);
			soinfo.setFaudittime(now);
			soinfo.setFallot(0);
			soinfo.setFsupplierid(supplierid);
			if(StringHelper.isEmpty(supplier.getFwarehouseid()) || StringHelper.isEmpty(supplier.getFwarehousesiteid())){
				soinfo.setFdescription("制造商:"+supplier.getFname()+"没有设置默认仓库或库位");
				soinfo.setFtype(2);
			}else{
				soinfo.setFallot(1);
				soinfo.setFallotorid(userid); 
				soinfo.setFallottime(new Date());
			}
			saleOrderList.add(soinfo);
		}
		return saleOrderList;
	}
		
	private Storebalance getStorebalance(Saleorder saleorder,
			ProductPlan productPlan) {
		Supplier supplier = saleorder.getSupplier();
		Storebalance sbinfo=new Storebalance();
		Date now = new Date();
		String userid = saleorder.getFcreatorid();
		sbinfo.setFbalanceqty(0);
		sbinfo.setFcreatetime(now);
		sbinfo.setFcreatorid(userid);
		sbinfo.setFcustomerid(saleorder.getFcustomerid());
		sbinfo.setFid(CreateUUid());
		sbinfo.setFinqty(0);
		sbinfo.setForderentryid(saleorder.getFid());
		sbinfo.setFoutqty(0);
		sbinfo.setFproductId(saleorder.getFproductdefid());
		sbinfo.setFproductplanId(productPlan.getFid());
		sbinfo.setFsaleorderid(saleorder.getForderid());
		sbinfo.setFsupplierID(productPlan.getFsupplierid());
		sbinfo.setFtype(1);
		sbinfo.setFupdatetime(now);
		sbinfo.setFupdateuserid(userid);
		sbinfo.setFwarehouseId(supplier.getFwarehouseid());
		sbinfo.setFwarehouseSiteId(supplier.getFwarehousesiteid());
		return sbinfo;
	}
	
	private ProductPlan getProductPlan(Saleorder soinfo) {
		BigDecimal[] purchaseprices = saleOrderDao.getPurchasePrices(soinfo.getFamount(),
				soinfo.getFproductdefid(), soinfo.getFsupplierid());
		if (purchaseprices == null) {
			//暂时不检查是否有价格   BY CC 2013-12-09
			purchaseprices=new BigDecimal[]{new BigDecimal("0.00"),new BigDecimal("0.00")};
		}
		ProductPlan p = new ProductPlan();
		p.setFtype(soinfo.getFtype());
		p.setPerpurchaseprice(purchaseprices[0]);
		p.setPurchaseprice(purchaseprices[1]);
		p.setFid(CreateUUid());
		p.setFnumber(ServerContext.getNumberHelper().getNumber("t_ord_productplan", "P",4,false));
		p.setFparentorderid(soinfo.getFid());// 设置父订单分录ID
		p.setFseq(soinfo.getFseq());// 设置父订单分录
		p.setFcreatorid(soinfo.getFcreatorid());
		p.setFcreatetime(soinfo.getFcreatetime());
		p.setFlastupdateuserid(soinfo.getFlastupdateuserid());
		p.setFlastupdatetime(soinfo.getFlastupdatetime());
		p.setFcustomerid(soinfo.getFcustomerid());
		p.setFcustproduct(soinfo.getFcustproduct());
		p.setFarrivetime(soinfo.getFarrivetime());
		p.setFbizdate(soinfo.getFbizdate());
		p.setFamount(soinfo.getFamount());// 设置生产数量
		p.setFordertype(soinfo.getFordertype());
		p.setFsuitProductId(soinfo.getFsuitProductId());
		p.setFparentOrderEntryId(soinfo.getFparentOrderEntryId());
		p.setForderid(soinfo.getForderid());
		p.setFimportEas(soinfo.getFimportEas());
		p.setFproductdefid(soinfo.getFproductdefid());
		p.setFsupplierid(soinfo.getFsupplierid());// 设置供应商;
		p.setFentryProductType(soinfo.getFentryProductType());
		p.setFimportEastime(soinfo.getFimportEastime());
		p.setFamountrate(soinfo.getFamountrate());
		p.setFaffirmed(soinfo.getFaffirmed());
		p.setFstockinqty(soinfo.getFstockinqty());
		p.setFstockoutqty(soinfo.getFstockoutqty());
		p.setFstoreqty(soinfo.getFstoreqty());
		p.setFassemble(soinfo.getFassemble());
		p.setFiscombinecrosssubs(soinfo.getFiscombinecrosssubs());
		p.setFeasorderid(soinfo.getFeasorderid());
		p.setFeasorderentryid(soinfo.getFeasorderentryid());
		p.setFcloseed(0);
		p.setFstate(0);
		p.setFdescription(soinfo.getFdescription());
		p.setFaudited(1);
		p.setFauditorid(soinfo.getFcreatorid());
		p.setFaudittime(new Date());
		p.setFpcmordernumber(soinfo.getFpcmordernumber());
		return p;
	}
	

	private void gainDeliversAmount(List<HashMap<String, Object>> stockList) {
		// 此处条件按原来的代码，未加fsupplierid
		String condition = baseSysDao.getCondition(baseSysDao.getIds(stockList,"fproductid"));
		String fproductid;
		String sql = "select fproductid,sum(ifnull(famount,0)) amount from t_ord_delivers where (falloted=0 or falloted is null) and (ftype!=1 or ftype is null) and fproductid"+condition + " group by fproductid";
		List<HashMap<String, Object>> list = this.QueryBySql(sql);
		for(HashMap<String, Object> map: stockList){
			fproductid = (String) map.get("fproductid");
			for(HashMap<String, Object> subMap: list){
				if(fproductid.equals(subMap.get("fproductid"))){
					map.put("deliversAmt", subMap.get("amount"));
					break;
				}
			}
		}
	}
	private void gainBalanceqty(List<HashMap<String, Object>> orderStockList,
			List<HashMap<String, Object>> noticeStockList) {
		int fnewtype;
		/*通知类型的套装
		List<HashMap<String, Object>> suitNoticeStockList = new ArrayList<>();*/
		List<HashMap<String, Object>> list = new ArrayList<>(orderStockList);		// 保存订单类型的产品和通知类型的普通产品
		for(HashMap<String, Object> map: noticeStockList){
			fnewtype = Integer.parseInt(map.get("fnewtype").toString());
			if(fnewtype==1 || fnewtype==3){
				list.add(map);
			}/*else if(fnewtype==2 || fnewtype==4){
				suitNoticeStockList.add(map);
			}*/
		}
		if(list.size()>0){
			gainBalanceqty1(list);
		}
		/*通知类型的套装，目前安全库存中没有这种产品，暂不做处理
		 * 原先的逻辑可查看VmipdtParamController中的vmi下单代码
		if(suitNoticeStockList.size()>0){
			gainBalanceqty2(suitNoticeStockList);
		}*/
	}
	/*
	 * 对订单类型和普通的通知类型，计算库存
	 */
	private void gainBalanceqty1(List<HashMap<String, Object>> orderStockList) {
		StringBuilder sb = new StringBuilder("(");
		for(HashMap<String, Object> map: orderStockList){
			sb.append(" (e.fsupplierid='"+map.get("fsupplierid")+"' and e.fproductid='"+map.get("fproductid")+"') or");
		}
		String condition = sb.substring(0, sb.length()-2)+") ";
		int balanceQty;
		int allotQty;
		String temp;
		// 查结存表,库存量
		String sql = "select fsupplierid,fproductid,sum(ifnull(fbalanceqty,0)) fbalanceqty,sum(ifnull(fallotqty,0)) fallotqty  from t_inv_storebalance e where"+condition+"group by fsupplierid,fproductid";
		List<HashMap<String, Object>> balanceList = this.QueryBySql(sql);
		for(HashMap<String, Object> map: balanceList){
			balanceQty = Integer.parseInt(map.get("fbalanceqty").toString());
			allotQty = Integer.parseInt(map.get("fallotqty").toString());
			map.put("fbalanceqty", balanceQty+allotQty);
			map.put("msg", "库存数量：" + balanceQty + ";调拨在途数量："+allotQty+";");
		}
		combineData(orderStockList, balanceList);
		// 查制造商订单表，正在生产的数量
		sql = "select fsupplierid,fproductdefid fproductid,sum(case when famount<fstockinqty then 0 else famount-ifnull(fstockinqty,0) end ) fbalanceqty from t_ord_productplan e where faudited=1 and fcloseed =0 and fboxtype=0 and"+condition.replaceAll("fproductid", "fproductdefid")+"group by fsupplierid,fproductid";
		balanceList = this.QueryBySql(sql);
		for(HashMap<String, Object> map: balanceList){
			map.put("msg", "在生产数量："+map.get("fbalanceqty")+";");
		}
		combineData(orderStockList, balanceList);
		// 查占用量表，库存中已被其它订单占用的量
		sql = "select e.fsupplierid,e.fproductid,sum(fusedqty) fbalanceqty from t_inv_usedstorebalance u inner join t_ord_deliverorder e on u.fdeliverorderid=e.fid where u.fusedqty>0 and"+condition+"group by fsupplierid,fproductid";
		balanceList = this.QueryBySql(sql);
		for(HashMap<String, Object> map: balanceList){
			temp = map.get("fbalanceqty").toString();
			map.put("msg", "待发货数量："+temp+";");
			map.put("fbalanceqty", "-"+temp);
		}
		combineData(orderStockList, balanceList);
	}
	
	
	private void combineData(List<HashMap<String, Object>> orderStockList,
			List<HashMap<String, Object>> balanceList) {
		if(balanceList.size()==0){
			return;
		}
		int balanceQty;
		String fsupplierid;
		String fproductid;
		for(HashMap<String, Object> map: orderStockList){
			fsupplierid = (String) map.get("fsupplierid");
			fproductid = (String) map.get("fproductid");
			balanceQty = Integer.parseInt(map.get("fbalanceqty").toString());
			for(HashMap<String, Object> subMap: balanceList){
				if(fsupplierid.equals(subMap.get("fsupplierid"))&&fproductid.equals(subMap.get("fproductid"))){
					map.put("fbalanceqty", balanceQty+Integer.parseInt(subMap.get("fbalanceqty").toString()));
					map.put("msg", map.get("msg")+""+subMap.get("msg"));
					break;
				}
			}
		}
	}
	
}
