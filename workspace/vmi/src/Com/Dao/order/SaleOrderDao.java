package Com.Dao.order;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.hibernate.util.StringHelper;
import org.springframework.stereotype.Service;

import Com.Base.Dao.BaseDao;
import Com.Base.Util.DJException;
import Com.Base.Util.ServerContext;
import Com.Base.Util.SpringContextUtils;
import Com.Base.Util.params;
import Com.Dao.System.ISupplierDao;
import Com.Entity.Inv.Storebalance;
import Com.Entity.Inv.Usedstorebalance;
import Com.Entity.System.Productcycle;
import Com.Entity.System.Supplier;
import Com.Entity.System.Useronline;
import Com.Entity.order.Deliverapply;
import Com.Entity.order.Deliverorder;
import Com.Entity.order.Delivers;
import Com.Entity.order.ProductPlan;
import Com.Entity.order.Saleorder;
@Service("saleOrderDao")
public class SaleOrderDao extends BaseDao implements ISaleOrderDao {
	private IDeliverapplyDao deliverapplyDao = (IDeliverapplyDao)SpringContextUtils.getBean("deliverapplyDao");
	@Override
	public HashMap<String, Object> ExecSave(Saleorder cust) {
		// TODO Auto-generated method stub
		HashMap<String, Object> params = new HashMap<>();
		if (cust.getFid().isEmpty()) {
			cust.setFid(this.CreateUUid());
		}
			this.saveOrUpdate(cust);
		
		params.put("success", true);
		return params;
	}

	@Override
	public Saleorder Query(String fid) {
		// TODO Auto-generated method stub
		return (Saleorder) this.getHibernateTemplate().get(
				Saleorder.class, fid);
	}

	@Override
	public void ExecAssageSupplier(HttpServletRequest request) throws Exception {
		// TODO Auto-generated method stub

		String userid = ((Useronline) request.getSession().getAttribute(
				"Useronline")).getFuserid();
		String type = request.getParameter("ftype");
		String sql = "";
		if ("0".equals(type)) {		//分配

			// 查询套装订单
			String hhsql = " from Saleorder where fallot=0 and fentryProductType=1 and faudited=1";
			List taoresult = QueryByHql(hhsql);

			//
			if (taoresult.size() > 0) {
				for (int i = 0; i < taoresult.size(); i++) {

					// 套装生成制造商订单时，设置套装及子件标记为已分配
					Saleorder sinfo = (Saleorder) taoresult.get(i);
					String sqll1 = "from Saleorder s  where s.fentryProductType!=1 and s.faudited=0 and s.forderid='"
							+ sinfo.getForderid() + "'";
					List auditResult = QueryByHql(sqll1);
					if (auditResult.size() > 0) {
						continue;
					}
					String sqll = "from Saleorder s  where s.fentryProductType!=1 and s.fallot=1 and s.forderid='"
							+ sinfo.getForderid() + "'";
					List result = QueryByHql(sqll);
					if (result.size() > 0) {
						continue;
					}
					createProductPlan(sinfo, userid,sinfo.getFproductdefid());
					if(sinfo.getFtype()!=2){
						String updatesql = " update t_ord_saleorder set fallot=1 ,fallotorid=:fallotorid,fallottime=now() where fentryProductType!=1 and forderid=:forderid";
						params p = new params();
						p.put("fallotorid", userid);
						// p.put("fallottime", new DateTime());
						p.put("forderid", sinfo.getForderid());
						ExecBySql(updatesql, p);
					}
				}
			}

			// 普通产品生成制造商订单
			String hsql = " from Saleorder where fallot=0 and fentryProductType!=1 and fordertype=1 ";
			List result = QueryByHql(hsql);

			for (int i = 0; i < result.size(); i++) {
				Saleorder sinfo = (Saleorder) result.get(i);
				if (sinfo.getFaudited() == 1) {
					createProductPlan(sinfo, userid,sinfo.getFproductdefid());
				}
			}

			sql = " update t_ord_saleorder t set fallot=1 ,fallotorid='"
					+ userid
					+ "',fallottime=now() where ifnull(fallot,0)=0 and fseq=1 and fordertype=2 and ftype!=2 and exists (select fid from t_ord_productplan where t_ord_productplan.forderid=t.forderid)";
			ExecBySql(sql);
		} else {		//取消分配
			String fids = request.getParameter("fidcls");
			fids="'"+fids.replace(",","','")+"'";
			String shql = " select fid from  t_ord_productplan where  faffirmed=1 and fparentorderid in ( "
					+ fids + ")";
			List affiredReuslt = QueryBySql(shql);
			if (affiredReuslt.size() > 0) {
				throw new DJException("该订单生产的生产计划，已经确认不能取消分配");
			}
			sql = "select forderid,fnumber,fid from t_ord_saleorder where fid in ("+fids+")";
			List<HashMap<String,Object>> list = this.QueryBySql(sql);
			for(int i=0;i<list.size();i++){
				sql = "select 1 from t_ord_productplan where fschemedesignid<>'' and forderid ='"+list.get(i).get("forderid")+"'";
				affiredReuslt = this.QueryBySql(sql);
				if(affiredReuslt.size()>0){
					throw new DJException("订单编号:"+list.get(i).get("fnumber")+"该制造商订单由方案设计下单生成，不能直接取消分配，请在方案设计取消下单！");
				}
				sql="select 1 from t_ord_deliverorder where fsaleorderid  ='"+list.get(i).get("forderid")+"' and forderentryid='"+list.get(i).get("fid")+"'";//订单类型会赋值
				affiredReuslt = this.QueryBySql(sql);
				if(affiredReuslt.size()>0){
					throw new DJException("订单编号:"+list.get(i).get("fnumber")+"对应的制造商订单已被分配到配送信息中，不能直接取消分配,请先在要货管理中取消分配!");
				}
			}
			String hql = " update t_ord_saleorder set fsupplierid=null,fallot=0 ,fallotorid=null,fallottime=null where fid  in ("
					+ fids
					+ ") and exists(select fid from t_ord_productplan where t_ord_saleorder.fid=t_ord_productplan.fparentorderid) ";
			ExecBySql(hql);
			sql = "delete from t_inv_storebalance where fproductplanid in (select fid from  t_ord_productplan where  fparentorderid in ("+fids+"))";
			this.ExecBySql(sql);//删除结存表记录
			String detelesql = " delete from t_ord_productplan where fparentorderid in ( "
					+ fids + " )";
			ExecBySql(detelesql);
			sql = "select forderid from t_ord_saleorder where fid in ("
					+ fids + ") and fordertype=2 ";
			List<HashMap<String, Object>> suitordercls = QueryBySql(sql);
			String suitorderids = "";
			if (suitordercls.size() > 0) {
				for (int i = 0; i < suitordercls.size(); i++) {
					if (i > 0) {
						suitorderids = suitorderids + ",";
					}
					suitorderids = suitorderids
							+ "'"
							+ suitordercls.get(i).get("forderid")
									.toString() + "'";
				}
				sql = " update t_ord_saleorder t set fallot=0 ,fallotorid='',fallottime=null where ifnull(fallot,0)=1 and fordertype=2 and forderid in ("
						+ suitorderids
						+ ") and not exists (select fid from t_ord_productplan where t_ord_productplan.forderid=t.forderid) ";
				ExecBySql(sql);
			}
		}

	}
	/**
	 * 生成制造商订单
	 *
	 * @param sinfo
	 * @param userid
	 * @param fproductdefid
	 * @param message
	 * 需要提示信息?
	 * @throws Exception
	 *
	 * @date 2013-12-6 下午2:04:39  
	 */
	
	private void createProductPlan(Saleorder sinfo, String userid,
			String fproductdefid) {
		ISupplierDao ISysUDao = (ISupplierDao) SpringContextUtils.getBean("SupplierDao");
		if(StringUtils.isEmpty(sinfo.getFsupplierid()))
		{
			String othersql = " from Productcycle where fproductdefid='"+ fproductdefid + "'";
			
			// 产品生产比例
			List cycle = QueryByHql(othersql);
			List<Object> objs = new ArrayList<>();
			for (int j = 0; j < cycle.size(); j++) {
				
				// code here
				
				Productcycle cinfo = (Productcycle) cycle.get(j);
				
				//在这里获得采购价，详见具体方法 
				BigDecimal[] purchaseprices = getPurchasePrices(
						(int) Math.round(sinfo.getFamount()
								* (cinfo.getFproportion() * 0.01)),
								cinfo.getFproductdefid(), sinfo.getFsupplierid()); 
				
				if (purchaseprices == null) {
					//暂时不检查是否有价格   BY CC 2013-12-09
					purchaseprices=new BigDecimal[]{new BigDecimal("0.00"),new BigDecimal("0.00")};
				}
				
				if (cinfo.getFproportion() == null
						|| "".equals(cinfo.getFproportion())) {
					handleSaleOrderException(sinfo,"请先正确设置好生产比例！");
					return;
				}
				Supplier suppinfo=ISysUDao.Query(cinfo.getFsupplierid());
				if(StringHelper.isEmpty(suppinfo.getFwarehouseid()) || StringHelper.isEmpty(suppinfo.getFwarehousesiteid()))
				{
					handleSaleOrderException(sinfo, "制造商:"+suppinfo.getFname()+"没有设置默认仓库或库位");
					return;
				}
				ProductPlan p = new ProductPlan();
				p.setFtype(sinfo.getFtype());
				p.setPerpurchaseprice(purchaseprices[0]);
				p.setPurchaseprice(purchaseprices[1]);
				
				p.setFid(CreateUUid());
				p.setFnumber(ServerContext.getNumberHelper().getNumber("t_ord_productplan", "P",4,false));
				p.setFparentorderid(sinfo.getFid());// 设置父订单分录ID
				p.setFseq(sinfo.getFseq());// 设置父订单分录
				p.setFcreatorid(sinfo.getFcreatorid());
				p.setFcreatetime(sinfo.getFcreatetime());
				p.setFlastupdateuserid(sinfo.getFlastupdateuserid());
				p.setFlastupdatetime(sinfo.getFlastupdatetime());
				p.setFcustomerid(sinfo.getFcustomerid());
				p.setFcustproduct(sinfo.getFcustproduct());
				p.setFarrivetime(sinfo.getFarrivetime());
				p.setFbizdate(sinfo.getFbizdate());
				
				p.setFamount((int) Math.round(sinfo.getFamount()
						* (cinfo.getFproportion() * 0.01)));// 设置生产数量
				
				p.setFordertype(sinfo.getFordertype());
				p.setFsuitProductId(sinfo.getFsuitProductId());
				p.setFparentOrderEntryId(sinfo.getFparentOrderEntryId());
				p.setForderid(sinfo.getForderid());
				p.setFimportEas(sinfo.getFimportEas());
				p.setFproductdefid(sinfo.getFproductdefid());
				p.setFsupplierid(cinfo.getFsupplierid());// 设置供应商
				p.setFentryProductType(sinfo.getFentryProductType());
				p.setFimportEastime(sinfo.getFimportEastime());
				p.setFamountrate(sinfo.getFamountrate());
				p.setFaffirmed(sinfo.getFaffirmed());
				p.setFstockinqty(sinfo.getFstockinqty());
				p.setFstockoutqty(sinfo.getFstockoutqty());
				p.setFstoreqty(sinfo.getFstoreqty());
				p.setFassemble(sinfo.getFassemble());
				p.setFiscombinecrosssubs(sinfo.getFiscombinecrosssubs());
				p.setFeasorderid(sinfo.getFeasorderid());
				p.setFeasorderentryid(sinfo.getFeasorderentryid());
				p.setFcloseed(0);
				p.setFdescription(sinfo.getFdescription());
				p.setFaudited(1);
				p.setFauditorid(sinfo.getFcreatorid());
				p.setFaudittime(new Date());
				p.setFpcmordernumber(sinfo.getFpcmordernumber());
				objs.add(p);
				//直接生成结存表，否者配送信息指定无法看到刚开的订单    BY   CC  20140307
				Storebalance sbinfo=new Storebalance();
				sbinfo.setFbalanceqty(0);
				sbinfo.setFcreatetime(new Date());
				sbinfo.setFcreatorid(userid);
				sbinfo.setFcustomerid(sinfo.getFcustomerid());
				sbinfo.setFid(CreateUUid());
				sbinfo.setFinqty(0);
				sbinfo.setForderentryid(sinfo.getFid());
				sbinfo.setFoutqty(0);
				sbinfo.setFproductId(sinfo.getFproductdefid());
				sbinfo.setFproductplanId(p.getFid());
				sbinfo.setFsaleorderid(sinfo.getForderid());
				sbinfo.setFsupplierID(p.getFsupplierid());
				sbinfo.setFtype(1);
				sbinfo.setFupdatetime(new Date());
				sbinfo.setFupdateuserid(userid);
				
				sbinfo.setFwarehouseId(suppinfo.getFwarehouseid());
				sbinfo.setFwarehouseSiteId(suppinfo.getFwarehousesiteid());
				objs.add(sbinfo);
				
			}
			if (objs.size() > 0) {
				for(Object o : objs){
					this.saveOrUpdate(o);
				}
				sinfo.setFallot(1);
				sinfo.setFallotorid(userid); 
				sinfo.setFallottime(new Date());
				if(sinfo.getFtype()==2){
					sinfo.setFtype(1);
				}
				this.saveOrUpdate(sinfo);
			} else {
				handleSaleOrderException(sinfo, "请先设置好生产比例！");
				return;
			}
		}
		else
		{
			//在这里获得采购价，详见具体方法 
			BigDecimal[] purchaseprices = getPurchasePrices(sinfo.getFamount(),
					sinfo.getFproductdefid(), sinfo.getFsupplierid()); 
			
			if (purchaseprices == null) {
				//暂时不检查是否有价格   BY CC 2013-12-09
				purchaseprices=new BigDecimal[]{new BigDecimal("0.00"),new BigDecimal("0.00")};
			}
			Supplier suppinfo=ISysUDao.Query(sinfo.getFsupplierid());
			if(StringHelper.isEmpty(suppinfo.getFwarehouseid()) || StringHelper.isEmpty(suppinfo.getFwarehousesiteid()))
			{
				handleSaleOrderException(sinfo, "制造商:"+suppinfo.getFname()+"没有设置默认仓库或库位");
				return;
			}
			ProductPlan p = new ProductPlan();
			p.setFtype(sinfo.getFtype());
			p.setPerpurchaseprice(purchaseprices[0]);
			p.setPurchaseprice(purchaseprices[1]);
			
			p.setFid(CreateUUid());
			p.setFnumber(ServerContext.getNumberHelper().getNumber("t_ord_productplan", "P",4,false));
			p.setFparentorderid(sinfo.getFid());// 设置父订单分录ID
			p.setFseq(sinfo.getFseq());// 设置父订单分录
			p.setFcreatorid(sinfo.getFcreatorid());
			p.setFcreatetime(sinfo.getFcreatetime());
			p.setFlastupdateuserid(sinfo.getFlastupdateuserid());
			p.setFlastupdatetime(sinfo.getFlastupdatetime());
			p.setFcustomerid(sinfo.getFcustomerid());
			p.setFcustproduct(sinfo.getFcustproduct());
			p.setFarrivetime(sinfo.getFarrivetime());
			p.setFbizdate(sinfo.getFbizdate());
			
			p.setFamount(sinfo.getFamount());// 设置生产数量
			
			p.setFordertype(sinfo.getFordertype());
			p.setFsuitProductId(sinfo.getFsuitProductId());
			p.setFparentOrderEntryId(sinfo.getFparentOrderEntryId());
			p.setForderid(sinfo.getForderid());
			p.setFimportEas(sinfo.getFimportEas());
			p.setFproductdefid(sinfo.getFproductdefid());
			p.setFsupplierid(sinfo.getFsupplierid());// 设置供应商
			p.setFentryProductType(sinfo.getFentryProductType());
			p.setFimportEastime(sinfo.getFimportEastime());
			p.setFamountrate(sinfo.getFamountrate());
			p.setFaffirmed(sinfo.getFaffirmed());
			p.setFstockinqty(sinfo.getFstockinqty());
			p.setFstockoutqty(sinfo.getFstockoutqty());
			p.setFstoreqty(sinfo.getFstoreqty());
			p.setFassemble(sinfo.getFassemble());
			p.setFiscombinecrosssubs(sinfo.getFiscombinecrosssubs());
			p.setFeasorderid(sinfo.getFeasorderid());
			p.setFeasorderentryid(sinfo.getFeasorderentryid());
			p.setFcloseed(0);
			p.setFdescription(sinfo.getFdescription());
			p.setFaudited(1);
			p.setFauditorid(sinfo.getFcreatorid());
			p.setFaudittime(new Date());
			saveOrUpdate(p);
			//直接生成结存表，否者配送信息指定无法看到刚开的订单    BY   CC  20140307
			Storebalance sbinfo=new Storebalance();
			sbinfo.setFbalanceqty(0);
			sbinfo.setFcreatetime(new Date());
			sbinfo.setFcreatorid(userid);
			sbinfo.setFcustomerid(sinfo.getFcustomerid());
			sbinfo.setFid(CreateUUid());
			sbinfo.setFinqty(0);
			sbinfo.setForderentryid(sinfo.getFid());
			sbinfo.setFoutqty(0);
			sbinfo.setFproductId(sinfo.getFproductdefid());
			sbinfo.setFproductplanId(p.getFid());
			sbinfo.setFsaleorderid(sinfo.getForderid());
			sbinfo.setFsupplierID(p.getFsupplierid());
			sbinfo.setFtype(1);
			sbinfo.setFupdatetime(new Date());
			sbinfo.setFupdateuserid(userid);
			
			sbinfo.setFwarehouseId(suppinfo.getFwarehouseid());
			sbinfo.setFwarehouseSiteId(suppinfo.getFwarehousesiteid());
			saveOrUpdate(sbinfo);
			
			sinfo.setFallot(1);
			sinfo.setFallotorid(userid); 
			sinfo.setFallottime(new Date());
			saveOrUpdate(sinfo);
		}
		
	}

	/**
	 * 
	 * 获取采购单价和和总的采购价
	 * @param famount
	 *            数量
	 * @param fproductdefid
	 *            产品id
	 * @param supplierid 
	 * @return
	 * 无效时返回null值
	 * 
	 * @date 2013-12-5 下午1:05:52 (ZJZ)
	 */
	@Override
	public BigDecimal[] getPurchasePrices(Integer famount, String fproductdefid, String supplierid) {

		BigDecimal perpurchasePrice = getTheBestPrice(famount, fproductdefid, supplierid);

		if (perpurchasePrice == null) {
			
			return null;
			
		}
		
		BigDecimal purchasePrice = perpurchasePrice.multiply(new BigDecimal(famount));

		return new BigDecimal[] { perpurchasePrice,
				purchasePrice};
	}
	/**
	 * 获得最合适的单价
	 * 
	 * @param famount
	 * @param fproductdefid
	 * @param supplierid 
	 * @return
	 * 
	 * @date 2013-12-5 下午2:25:28 (ZJZ)
	 */
	private BigDecimal getTheBestPrice(double famount, String fproductdefid, String supplierid) {
		// 条件：有效，优先供应商，日期，数量限制；最多一条
		String sql = "SELECT pv.fid, pv.fprice as pvfprice FROM purchaseprice_view pv left join t_bal_purchaseprice tbp on pv.FPARENTID = tbp.fid where pv.FISEFFECT = 1 and (pv.FsupplierID IS null or pv.FsupplierID = '%s') and '%s' >= pv.FSTARTDATE and '%s' <= pv.FENDDATE and tbp.FPRODUCTID = '%s' and %f >= FLOWERLIMIT and %f <= FUPPERLIMIT  limit 1  ";

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		String time = sdf.format(new Date()); 

		float ft = (float)(famount);
		
		sql = String.format(sql, supplierid, time, time, fproductdefid, ft, ft);	

		List<HashMap<String, Object>> bestPrices = QueryBySql(sql);

		if (bestPrices.size() == 0) {

			return null;

		}

		// sql = " select * from t_bal_purchasesupplierprice ";
		//
		// if (saleOrderDao
		// .QueryBySql(sql).)
		// 
		BigDecimal t = (BigDecimal) bestPrices.get(0).get("pvfprice");
		// String id = (String) bestPrices.get(0).get("fid");
		//
		// sql = "  ";

		return t; 
	}

	@Override
	public void ExecDeleteSaleOrdersNew(String forderidcls) throws Exception {
		String sql = "delete from t_inv_storebalance where fsaleorderid in ("+forderidcls+")";
		ExecBySql(sql);//删除结存表记录
		sql= " delete from t_ord_productplan where forderid in ( "
				+ forderidcls + " )";
		ExecBySql(sql);//删除制造商订单
		
		sql = "Delete FROM t_ord_saleorder where  forderid in ("
				+ forderidcls + ")";//删除生产订单
		ExecBySql(sql);
		//反写要货申请状态
		List<HashMap<String,Object>> deliverslist = QueryBySql("select fid from t_ord_delivers where fsaleorderid in (" + forderidcls + ")");
		for(int j=0;j<deliverslist.size();j++){
			deliverapplyDao.updateDeliverapplyStateByDelivers(deliverslist.get(j).get("fid").toString(), 1,false);
		}
		sql = "UPDATE `t_ord_delivers` SET `fordered`='0',`fordermanid`=null,`fordertime`=null,`fsaleorderid`=null,`fordernumber`=null,`forderentryid`=null WHERE `fsaleorderid` in ("
				+ forderidcls + ")";//更新要货管理状态
		ExecBySql(sql);
		sql="update mystock set fstate=0,fordered=0,fordernumber=null,fordertime=null,fordermanid =null,forderentryid=null,fsaleorderid=null where fsaleorderid in ("+forderidcls+")";
		ExecBySql(sql);//更改备货状态
	}

	
	@Override
	public void ExecDeleteSaleOrders(HashMap<String, Object> params) throws Exception {
		String deletesaleorder = params.get("deletesaleorder").toString();
		String updatedelivers = params.get("updatedelivers").toString();
		String delivers = params.get("delivers").toString();
		String mystock = params.get("mystock").toString();
		ExecByHql(deletesaleorder);
		
		List<HashMap<String,Object>> deliverslist = QueryBySql(delivers);
		for(int j=0;j<deliverslist.size();j++){
			deliverapplyDao.updateDeliverapplyStateByDelivers(deliverslist.get(j).get("fid").toString(), 1,false);
		}
		ExecBySql(updatedelivers);
		ExecBySql(mystock);
	}

	
	@Override
	public void ExecUpdateImportStateSDK(String fid) {
		String sql = "update t_ord_deliverorder set fissync=1 where fid=:fid";
		params p = new params();
		p.put("fid", fid);
		this.ExecBySql(sql, p);
		sql = "select fdeliversID from t_ord_deliverorder where fid=:fid";
		List<HashMap<String, Object>> list = this.QueryBySql(sql, p);
		//更改要货申请状态为已装配
//		List<HashMap<String,Object>> delivers = QueryBySql("select 1 from t_ord_deliverorder where fouted = 1 and fdeliversId = '" + list.get(0).get("fdeliversID") + "' ");
//		if(delivers.size()<1){
//			deliverapplyDao.updateDeliverapplyStateByDelivers(list.get(0).get("fdeliversID").toString(), 4);
//		}
	}
	
	private void handleSaleOrderException(Saleorder saleorder,String desc){
//		saleorder.setFdescription("<span style='color:#f00'>"+desc+"<span><br/>"+description);
		saleorder.setFdescription(desc);
		saleorder.setFtype(2);
		return;
	}

	@Override
	public void ExecCardboardSupplierAffirm(String fidcls,String userid) {
		String sql = "UPDATE t_ord_productplan SET faffirmed=1,faffirmtime=now(),faffirmer='"+userid+"',fstate=1 WHERE fid in ("
				+ fidcls + ") and faffirmed !=1";
		int count = this.ExecBySql(sql);
		if(count>0){
			sql= "update t_ord_productplan set fimportEas=1 where  ifnull(fimportEas,0)=0 and fsupplierid='39gW7X9mRcWoSwsNJhU12TfGffw=' and fid in (" + fidcls+")" ;
			ExecBySql(sql);
			sql = "update t_ord_deliverapply set fstate=2 where fplanid in ("+ fidcls + ") and fstate=3";
			this.ExecBySql(sql);
			ExecBySql("update t_ord_deliverorder set fimportEas=1 where fboxtype = 1 and fplanid in ("+fidcls+")");//纸板制造商订单导入EAS的时候，配送单也需导入
		}

	}

	@Override
	public void ExecCardboardSupplierUnAffirm(String fidcls) {
		String sql = "UPDATE t_ord_productplan SET faffirmed='0',fstate=0,faffirmtime=null,faffirmer='',fimportEas=0 WHERE fid in ("+ fidcls + ")";
		this.ExecBySql(sql);
		sql = "update t_ord_deliverapply set fstate=3 where fplanid in ("+ fidcls + ") and fstate = 2";
		this.ExecBySql(sql);
	}
	
	
	/*public void createProductPlanBySingel(List<Saleorder> slist,String fdeliverid) {
		if(slist.size()<1) return;
		Saleorder sinfo=slist.get(0);
		String userid=sinfo.getFcreatorid();
		String fproductdefid=sinfo.getFproductdefid();
		ISupplierDao ISysUDao = (ISupplierDao) SpringContextUtils.getBean("SupplierDao");
		if(StringUtils.isEmpty(sinfo.getFsupplierid()))
		{
			String othersql = " from Productcycle where fproductdefid='"+ fproductdefid + "'";
			// 产品生产比例
			List cycle = QueryByHql(othersql);
			if(cycle.size()!=1){
				ExecNocreateproductplan(slist,null,fdeliverid);
				return ;
			}
			String fsupplierid=((Productcycle) cycle.get(0)).getFsupplierid();
			if(StringUtils.isEmpty(fsupplierid))
			{
				ExecNocreateproductplan(slist,"该产品生产比例有误",fdeliverid);
				return ;
			}
			sinfo.setFsupplierid(fsupplierid);
		}
			//在这里获得采购价，详见具体方法 
			BigDecimal[] purchaseprices = getPurchasePrices(sinfo.getFamount(),
					sinfo.getFproductdefid(), sinfo.getFsupplierid()); 
			
			if (purchaseprices == null) {
				//暂时不检查是否有价格   BY CC 2013-12-09
				purchaseprices=new BigDecimal[]{new BigDecimal("0.00"),new BigDecimal("0.00")};
			}
			Supplier suppinfo=ISysUDao.Query(sinfo.getFsupplierid());
			if(StringHelper.isEmpty(suppinfo.getFwarehouseid()) || StringHelper.isEmpty(suppinfo.getFwarehousesiteid()))
			{
				ExecNocreateproductplan(slist,"制造商:"+suppinfo.getFname()+"没有设置默认仓库或库位",fdeliverid);
				return ;
			}
			ProductPlan p = new ProductPlan();
			p.setFtype(sinfo.getFtype());
			p.setPerpurchaseprice(purchaseprices[0]);
			p.setPurchaseprice(purchaseprices[1]);
			
			p.setFid(CreateUUid());
			p.setFnumber(ServerContext.getNumberHelper().getNumber("t_ord_productplan", "P",4,false));
			p.setFparentorderid(sinfo.getFid());// 设置父订单分录ID
			p.setFseq(sinfo.getFseq());// 设置父订单分录
			p.setFcreatorid(sinfo.getFcreatorid());
			p.setFcreatetime(sinfo.getFcreatetime());
			p.setFlastupdateuserid(sinfo.getFlastupdateuserid());
			p.setFlastupdatetime(sinfo.getFlastupdatetime());
			p.setFcustomerid(sinfo.getFcustomerid());
			p.setFcustproduct(sinfo.getFcustproduct());
			p.setFarrivetime(sinfo.getFarrivetime());
			p.setFbizdate(sinfo.getFbizdate());
			p.setFamount(sinfo.getFamount());// 设置生产数量
			p.setFordertype(sinfo.getFordertype());
			p.setFsuitProductId(sinfo.getFsuitProductId());
			p.setFparentOrderEntryId(sinfo.getFparentOrderEntryId());
			p.setForderid(sinfo.getForderid());
			p.setFimportEas(sinfo.getFimportEas());
			p.setFproductdefid(sinfo.getFproductdefid());
			p.setFsupplierid(sinfo.getFsupplierid());// 设置供应商
			p.setFentryProductType(sinfo.getFentryProductType());
			p.setFimportEastime(sinfo.getFimportEastime());
			p.setFamountrate(sinfo.getFamountrate());
			p.setFaffirmed(sinfo.getFaffirmed());
			p.setFstockinqty(sinfo.getFstockinqty());
			p.setFstockoutqty(sinfo.getFstockoutqty());
			p.setFstoreqty(sinfo.getFstoreqty());
			p.setFassemble(sinfo.getFassemble());
			p.setFiscombinecrosssubs(sinfo.getFiscombinecrosssubs());
			p.setFeasorderid(sinfo.getFeasorderid());
			p.setFeasorderentryid(sinfo.getFeasorderentryid());
			p.setFcloseed(0);
			p.setFstate(0);
			p.setFdescription(sinfo.getFdescription());
			p.setFaudited(1);
			p.setFauditorid(sinfo.getFcreatorid());
			p.setFaudittime(new Date());
			//直接生成结存表，否者配送信息指定无法看到刚开的订单    BY   CC  20140307
			Storebalance sbinfo=new Storebalance();
			sbinfo.setFbalanceqty(0);
			sbinfo.setFcreatetime(new Date());
			sbinfo.setFcreatorid(userid);
			sbinfo.setFcustomerid(sinfo.getFcustomerid());
			sbinfo.setFid(CreateUUid());
			sbinfo.setFinqty(0);
			sbinfo.setForderentryid(sinfo.getFid());
			sbinfo.setFoutqty(0);
			sbinfo.setFproductId(sinfo.getFproductdefid());
			sbinfo.setFproductplanId(p.getFid());
			sbinfo.setFsaleorderid(sinfo.getForderid());
			sbinfo.setFsupplierID(p.getFsupplierid());
			sbinfo.setFtype(1);
			sbinfo.setFupdatetime(new Date());
			sbinfo.setFupdateuserid(userid);
			sbinfo.setFwarehouseId(suppinfo.getFwarehouseid());
			sbinfo.setFwarehouseSiteId(suppinfo.getFwarehousesiteid());
			
			saveOrUpdate(p);
			saveOrUpdate(sbinfo);
			Saleorder sinfoc=null;
			for(int z=0;z<slist.size();z++)
			{
					sinfoc=slist.get(z);
					sinfoc.setFallot(1);
					sinfoc.setFallotorid(userid); 
					sinfoc.setFallottime(new Date());
					ExecSave(sinfoc);
			}
			///生成配送信息
			Delivers deliverinfo=null;
			List<Delivers> deliverslist = QueryByHql("select t from Delivers t where t.fid in (" + fdeliverid +")");
			for(int i=0;i<deliverslist.size();i++)
			{
			 deliverinfo=deliverslist.get(i);
			//创建配送信息
			 Deliverorder dinfo  = new Deliverorder();
			dinfo.setFid(CreateUUid());
			dinfo.setFcreatorid(userid);
			dinfo.setFcreatetime(new Date());
			dinfo.setFupdatetime(new Date());
			dinfo.setFupdateuserid(userid);
			dinfo.setFnumber(ServerContext.getNumberHelper().getNumber(
					"t_ord_deliverorder", "D", 4, false));
			dinfo.setFcustomerid(deliverinfo.getFcustomerid());
			dinfo.setFcusproductid(deliverinfo.getFcusproductid());
			dinfo.setFarrivetime(deliverinfo.getFarrivetime());
			dinfo.setFlinkman(deliverinfo.getFlinkman());
			dinfo.setFlinkphone(deliverinfo.getFlinkphone());
			dinfo.setFamount(deliverinfo.getFamount());
			dinfo.setFaddress(deliverinfo.getFaddress());
			dinfo.setFaddressid(deliverinfo.getFaddressid());
			dinfo.setFdescription(deliverinfo.getFdescription());//order不赋值
			dinfo.setFsaleorderid(sbinfo.getFsaleorderid());
			dinfo.setFordernumber(p.getFnumber());
			dinfo.setForderentryid(sbinfo.getForderentryid());
			dinfo.setFimportEas(0);
			dinfo.setFouted(0);
			dinfo.setFplanNumber(p.getFnumber());
			dinfo.setFplanid(sbinfo.getFproductplanId());
			dinfo.setFalloted(0);
			dinfo.setFdeliversId(deliverinfo.getFid());
			dinfo.setFsupplierId(sbinfo.getFsupplierID());
			dinfo.setFbalanceprice(deliverinfo.getFbalanceunitprice().multiply(new BigDecimal(deliverinfo.getFamount())));
			dinfo.setFbalanceunitprice(deliverinfo.getFbalanceunitprice());
			dinfo.setFproductid(deliverinfo.getFproductid());
			dinfo.setFcusfid(deliverinfo.getFcusfid());
			dinfo.setFaudited(0);
			dinfo.setFstorebalanceid(sbinfo.getFid());
			dinfo.setFtype(deliverinfo.getFtype());
			dinfo.setFpcmordernumber(deliverinfo.getFpcmordernumber());
			this.saveOrUpdate(dinfo);
			//占用表信息
			Usedstorebalance usedstorebalance = new Usedstorebalance(this.CreateUUid());
			usedstorebalance.setFdeliverorderid(dinfo.getFid());
			usedstorebalance.setFproductid(deliverinfo.getFproductid());
			usedstorebalance.setFratio(1);
			usedstorebalance.setFstorebalanceid(sbinfo.getFid());
			usedstorebalance.setFtype(1);
			usedstorebalance.setFusedqty(dinfo.getFamount());
			this.saveOrUpdate(usedstorebalance);
			deliverapplyDao.updateDeliverapplyStateByDelivers(deliverinfo.getFid(), 3);//修改状态为已分配
			ExecBySql("update t_ord_delivers set  fordernumber='"+p.getFnumber()+"',Falloted=1 where fid = '"+deliverinfo.getFid()+"'");
			}
	}
	private void ExecNocreateproductplan(List<Saleorder> slist,String error,String deliverid)//不符合直接生成制造商订单，执行更新
	{
		for(int s=0;s<slist.size();s++){
			if(s==0&&error!=null)
			{
				handleSaleOrderException(slist.get(s), error);//为异常订单，反写到描述中
			}
			this.ExecSave(slist.get(s));
		}
		List<HashMap<String,Object>> deliverslist = QueryBySql("select fid from t_ord_delivers where fid in (" + deliverid +")");
		for(int j=0;j<deliverslist.size();j++){
			deliverapplyDao.updateDeliverapplyStateByDelivers(deliverslist.get(j).get("fid").toString(), 2);
		}
	}*/
	/***
	 * 制造商为一个直接生成制造商订单和结存信息
	 * @param slist 生成订单集合
	 *  0：要货管理下单；1：备货下单；2手动新增；3：VMI下单  
	 */
	public HashMap<String, Object> ExecProductPlanAndStorebalanceBySingel(List<Saleorder> slist) {
		if(slist.size()<1) return null;
		Saleorder sinfo=slist.get(0);
		String userid=sinfo.getFcreatorid();
		String fproductdefid=sinfo.getFproductdefid();
		ISupplierDao ISysUDao = (ISupplierDao) SpringContextUtils.getBean("SupplierDao");
		String fsupplierid=sinfo.getFsupplierid();
		if(StringUtils.isEmpty(fsupplierid))
		{
			String othersql = " from Productcycle where fproductdefid='"+ fproductdefid + "'";
			// 产品生产比例
			List cycle = QueryByHql(othersql);
			if(cycle.size()!=1){
				ExecNocreateproductplanAndStorebalance(slist,null);
				return null;
			}
			 fsupplierid=((Productcycle) cycle.get(0)).getFsupplierid();
			if(StringUtils.isEmpty(fsupplierid))
			{
				ExecNocreateproductplanAndStorebalance(slist,"该产品生产比例有误");
				return null;
			}
//			sinfo.setFsupplierid(fsupplierid);
		}
			
			Supplier suppinfo=ISysUDao.Query(fsupplierid);
			if(StringHelper.isEmpty(suppinfo.getFwarehouseid()) || StringHelper.isEmpty(suppinfo.getFwarehousesiteid()))
			{
				ExecNocreateproductplanAndStorebalance(slist,"制造商:"+suppinfo.getFname()+"没有设置默认仓库或库位");
				return null;
			
			}
			//在这里获得采购价，详见具体方法 
			BigDecimal[] purchaseprices = getPurchasePrices(sinfo.getFamount(),
					sinfo.getFproductdefid(), suppinfo.getFid()); 
			
			if (purchaseprices == null) {
				//暂时不检查是否有价格   BY CC 2013-12-09
				purchaseprices=new BigDecimal[]{new BigDecimal("0.00"),new BigDecimal("0.00")};
			}
			ProductPlan p = new ProductPlan();
			p.setFtype(sinfo.getFtype());
			p.setPerpurchaseprice(purchaseprices[0]);
			p.setPurchaseprice(purchaseprices[1]);
			
			p.setFid(CreateUUid());
			p.setFnumber(ServerContext.getNumberHelper().getNumber("t_ord_productplan", "P",4,false));
			p.setFparentorderid(sinfo.getFid());// 设置父订单分录ID
			p.setFseq(sinfo.getFseq());// 设置父订单分录
			p.setFcreatorid(sinfo.getFcreatorid());
			p.setFcreatetime(sinfo.getFcreatetime());
			p.setFlastupdateuserid(sinfo.getFlastupdateuserid());
			p.setFlastupdatetime(sinfo.getFlastupdatetime());
			p.setFcustomerid(sinfo.getFcustomerid());
			p.setFcustproduct(sinfo.getFcustproduct());
			p.setFarrivetime(sinfo.getFarrivetime());
			p.setFbizdate(sinfo.getFbizdate());
			p.setFamount(sinfo.getFamount());// 设置生产数量
			p.setFordertype(sinfo.getFordertype());
			p.setFsuitProductId(sinfo.getFsuitProductId());
			p.setFparentOrderEntryId(sinfo.getFparentOrderEntryId());
			p.setForderid(sinfo.getForderid());
			p.setFimportEas(sinfo.getFimportEas());
			p.setFproductdefid(sinfo.getFproductdefid());
			p.setFsupplierid(suppinfo.getFid());// 设置供应商;
			p.setFentryProductType(sinfo.getFentryProductType());
			p.setFimportEastime(sinfo.getFimportEastime());
			p.setFamountrate(sinfo.getFamountrate());
			p.setFaffirmed(sinfo.getFaffirmed());
			p.setFstockinqty(sinfo.getFstockinqty());
			p.setFstockoutqty(sinfo.getFstockoutqty());
			p.setFstoreqty(sinfo.getFstoreqty());
			p.setFassemble(sinfo.getFassemble());
			p.setFiscombinecrosssubs(sinfo.getFiscombinecrosssubs());
			p.setFeasorderid(sinfo.getFeasorderid());
			p.setFeasorderentryid(sinfo.getFeasorderentryid());
			p.setFcloseed(0);
			p.setFstate(0);
			p.setFdescription(sinfo.getFdescription());
			p.setFaudited(1);
			p.setFauditorid(sinfo.getFcreatorid());
			p.setFaudittime(new Date());
			p.setFpcmordernumber(sinfo.getFpcmordernumber());
			//直接生成结存表，否者配送信息指定无法看到刚开的订单    BY   CC  20140307
			Storebalance sbinfo=new Storebalance();
			sbinfo.setFbalanceqty(0);
			sbinfo.setFcreatetime(new Date());
			sbinfo.setFcreatorid(userid);
			sbinfo.setFcustomerid(sinfo.getFcustomerid());
			sbinfo.setFid(CreateUUid());
			sbinfo.setFinqty(0);
			sbinfo.setForderentryid(sinfo.getFid());
			sbinfo.setFoutqty(0);
			sbinfo.setFproductId(sinfo.getFproductdefid());
			sbinfo.setFproductplanId(p.getFid());
			sbinfo.setFsaleorderid(sinfo.getForderid());
			sbinfo.setFsupplierID(p.getFsupplierid());
			sbinfo.setFtype(1);
			sbinfo.setFupdatetime(new Date());
			sbinfo.setFupdateuserid(userid);
			sbinfo.setFwarehouseId(suppinfo.getFwarehouseid());
			sbinfo.setFwarehouseSiteId(suppinfo.getFwarehousesiteid());
			
			saveOrUpdate(p);
			saveOrUpdate(sbinfo);
			Saleorder sinfoc=null;
			for(int z=0;z<slist.size();z++)
			{
					sinfoc=slist.get(z);
					sinfoc.setFallot(1);
					sinfoc.setFallotorid(userid); 
					sinfoc.setFallottime(new Date());
					ExecSave(sinfoc);
			}
			HashMap<String, Object> ppmap = new HashMap<>();
			ppmap.put("sbinfo",sbinfo);//制造商编号反写到库存中，要货管理下单后需要
			ppmap.put("fnumber", p.getFnumber());//制造商编号反写到库存中，要货管理下单后需要
//			ppmap.put("success", true);
			return ppmap;
	}
	

	
	/***
	 *   不能生成制造商订单则保存生产订单
	 * @param slist
	 * @param error 错误信息
	 */
	private void ExecNocreateproductplanAndStorebalance(List<Saleorder> slist,String error)//不符合直接生成制造商订单，执行更新
	{

		for(int s=0;s<slist.size();s++){
			if(s==0&&error!=null)
			{
				handleSaleOrderException(slist.get(s), error);//为异常订单，反写到描述中
			}
			this.ExecSave(slist.get(s));
		}
	}

	@Override
	public void ExecAffirmAndImportEAS(String fidcls,String userid) {
		// TODO Auto-generated method stub
	String sql = "UPDATE t_ord_productplan SET faffirmed=1,faffirmtime=now(),faffirmer='"+userid+"',fstate=1 WHERE fid in ("
				+ fidcls + ")";
	ExecBySql(sql);
	ExecBySql("update t_ord_deliverapply set fstate = 1 where fplanid in (" + fidcls + ")");
	sql= "update t_ord_productplan set fimportEas=1 where  ifnull(fimportEas,0)=0 and fsupplierid='39gW7X9mRcWoSwsNJhU12TfGffw=' and fid in (" + fidcls+")" ;
	ExecBySql(sql);
	}
/**
 * 纸板纸箱 接口退回 ；纸板更新要货申请状态
 */
	@Override
	public void ExecUnAffirmImport(String fid) {
		
//	String sql = "update t_ord_productplan set fissync=0,fimportEAS=0,faffirmed=0,fstate=0,faffirmtime=null,faffirmer='' where fid ='"+fid+"'";
//	this.ExecBySql(sql);
//	sql = "update t_ord_deliverapply set fstate=3 where fplanid ='"+fid+ "' and fstate = 2 and  fboxtype=1 " ;//纸板更新要货申请状态
//	this.ExecBySql(sql);
//	
		String sql = "select fdeliverapplyid,fboxtype from t_ord_productplan where fid ='"+fid+"'"; 
		List<HashMap<String, Object>> list=this.QueryBySql(sql);
		if(list.size()>0){
			if(list.get(0).get("fboxtype").toString().equals("1"))//纸板订单
			{
				String fdeliverapplyid = list.get(0).get("fdeliverapplyid").toString();
				List<Deliverapply> applyList=this.QueryByHql("from Deliverapply where fid='"+fdeliverapplyid+"' ");
				if(applyList.size()>0)
				{
					Deliverapply apply=applyList.get(0);
					if(apply==null || apply.getFstate()!=2){ //要货申请为已生产状态，
						throw new DJException("平台的要货记录非接收状态，不能退回");
					}
					
					String productplanIds = "";
					List<HashMap<String,Object>> productplanlist =this.QueryBySql("select fid,fboxtype from t_ord_productplan where fdeliverapplyid='"+fdeliverapplyid+"'");
					for(int i=0;i<productplanlist.size();i++){
						if(productplanIds.equals("")){
							productplanIds = "'" + productplanlist.get(i).get("fid").toString() + "'";
						}else{
							productplanIds += ",'" + productplanlist.get(i).get("fid").toString() + "'";
						}
					}
					if(productplanIds.equals("")){
						productplanIds = "''";
					}
					
					sql = "delete from t_ord_productplan where fid in ("+productplanIds+")";
					this.ExecBySql(sql);
					sql = "delete from t_inv_storebalance where fproductplanid in ("+productplanIds+")";
					this.ExecBySql(sql);
					//纸板要货申请与配送一对一
					List<Deliverorder> orderlist =this.QueryByHql("from Deliverorder where fdeliversId='"+apply.getFid()+"'");
					if(orderlist.size()>0){
						Deliverorder deliverorder=orderlist.get(0);
						if( deliverorder.getFassembleQty()>0 || (deliverorder.getFoutQty()==null?0:deliverorder.getFoutQty())>0){
							throw new DJException("该配送订单已提货，不能退回！");
						}
						this.Delete(deliverorder);
						sql = "delete from t_inv_usedstorebalance where fdeliverorderid = '"+deliverorder.getFid()+"'";
						this.ExecBySql(sql);
					}
					apply.setFreceiptTime(null);
					apply.setFrecipient("");
					apply.setFstate(0);
					apply.setFplanNumber("");
					apply.setFplanid("");
					this.Update(apply);
				}
			}else//纸箱订单
			{
				sql = "update t_ord_productplan set fissync=0,fimportEAS=0,faffirmed=0,fstate=0,faffirmtime=null,faffirmer='' where fid ='"+fid+"'";
				this.ExecBySql(sql);
			}
			
		}
	}
	
}
