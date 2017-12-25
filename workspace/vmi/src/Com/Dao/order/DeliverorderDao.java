package Com.Dao.order;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.util.StringHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import Com.Base.Dao.BaseDao;
import Com.Base.Util.DJException;
import Com.Base.Util.ServerContext;
import Com.Base.Util.SpringContextUtils;
import Com.Base.Util.params;
import Com.Base.data.LogAction;
import Com.Controller.order.DeliverapplyController;
import Com.Controller.order.DeliverorderController;
import Com.Controller.traffic.TruckassembleController;
import Com.Dao.Inv.IOutWarehouseDao;
import Com.Dao.Inv.IStorebalanceDao;
import Com.Dao.System.IBaseSysDao;
import Com.Dao.System.IProductdefDao;
import Com.Dao.System.ISupplierDao;
import Com.Dao.System.ISyscfgDao;
import Com.Dao.traffic.ITruckassembleDao;
import Com.Entity.Inv.Outwarehouse;
import Com.Entity.Inv.Storebalance;
import Com.Entity.Inv.Usedstorebalance;
import Com.Entity.System.Productdef;
import Com.Entity.System.Supplier;
import Com.Entity.order.Deliverapply;
import Com.Entity.order.Deliverorder;
import Com.Entity.order.Delivers;
import Com.Entity.order.OrderState;
import Com.Entity.order.ProductPlan;
import Com.Entity.traffic.Saledeliver;
import Com.Entity.traffic.Saledeliverentry;
import Com.Entity.traffic.Truckassemble;
import Com.Entity.traffic.Truckassembleentry;

@Service("DeliverorderDao")
public class DeliverorderDao extends BaseDao implements IDeliverorderDao {
	
	Logger log = LoggerFactory.getLogger(DeliverorderDao.class);
	
	private IDeliverapplyDao deliverapplyDao = (IDeliverapplyDao)SpringContextUtils.getBean("deliverapplyDao");
	@Resource
	private ISyscfgDao syscfgDao;
	@Resource
	private IBaseSysDao baseSysDao;
	boolean isReceiving;
	
	@Override
	public HashMap<String, Object> ExecSave(Deliverorder dlv) {
		// TODO Auto-generated method stub
		HashMap<String, Object> params = new HashMap<>();
		if (dlv.getFid().isEmpty()) {
			dlv.setFid(this.CreateUUid());
//			this.saveOrUpdate(addr);
		}
//		else{
//			this.Update(addr);
//		}
			this.saveOrUpdate(dlv);
		
		params.put("success", true);
		return params;
	}

	@Override
	public Deliverorder Query(String fid) {
		// TODO Auto-generated method stub
		return (Deliverorder) this.getHibernateTemplate().get(
				Deliverorder.class, fid);
	}
	
	/**
	 * 未使用
	 */
	public void ExecDeliverorder(ArrayList<HashMap<String, String>> deliverorderlist, Connection conn, PreparedStatement stmt) throws Exception {
		// TODO Auto-generated method stub
		for(int i=0;i<deliverorderlist.size();i++){
    		stmt = conn.prepareStatement(deliverorderlist.get(i).get("EASdeliversql"));
			stmt.execute();
			ExecBySql(deliverorderlist.get(i).get("VMIdeliversql"));
			
			//反写要货申请状态为装配状态;
//			deliverapplyDao.updateDeliverapplyStateByDelivers(deliverorderlist.get(i).get("deliversID").toString(), 4);
			conn.commit();
		}
	}
	
	public HashMap<String, Object> ExecCreateTruckassemble(HashMap<String, Object> params,int type) throws Exception {
		// TODO Auto-generated method stub
		if(type == 1){	//生成提货单、出库单
			List<HashMap<String, Object>> deliverordercls = (List<HashMap<String, Object>>)params.get("deliverordercls");
			String userid = params.get("userid").toString();
			String fids = params.get("fids").toString();
			String isrealType = params.get("isrealType").toString();
			int realamount = new Integer(params.get("frealamount").toString());
			
			Truckassemble Tinfo = null;
			Truckassembleentry Tninfo = null;
			int fseq = 1;
			String saledeliverID = "";
			String sql = "";
			boolean isreal = false;
			int assembleamount = 0;
			String id = "";
			for (int i = 0; i < deliverordercls.size(); i++) {
				HashMap<String, Object> deliverorderinfo = deliverordercls.get(i);
				String deliverorderID = deliverorderinfo.get("fid").toString();
				
//				pdpinfo = ProductPlanDao.Query(deliverorderinfo.get("fplanid").toString());
				String fproductspec = "";
				String fmaterial = "";
				String fproductid = deliverorderinfo.get("fproductid").toString();
				BigDecimal farea = new BigDecimal(0);
				
				IProductdefDao productdefDao = (IProductdefDao) SpringContextUtils.getBean("productdefDao");
				Productdef pdtinfo = productdefDao.Query(fproductid);
				if(pdtinfo!=null){
					fproductspec = pdtinfo.getFboxlength()+"×"+pdtinfo.getFboxwidth()+"×"+pdtinfo.getFboxheight();
					String sqls = "select CONCAT_WS('x',a.`FBOXLENGTH`,a.`FBOXWIDTH`,a.`FBOXHEIGHT`) fproductspec,CONCAT_WS('x',a.`fmateriallength`,a.`fmaterialwidth`) fmaterial  from t_ord_deliverorder d left join t_ord_deliverapply a on d.fdeliversID=a.fid where d.fboxtype=1 and d.fid='"+deliverorderID+"'";
					List<HashMap<String,Object>> list = this.QueryBySql(sqls);
					if(list.size()>0){
						fproductspec = (String)list.get(0).get("fproductspec");//纸板规格
						fmaterial = (String)list.get(0).get("fmaterial");//下料规格
					}
					
					if(!StringUtils.isEmpty(pdtinfo.getFarea())){
						farea = pdtinfo.getFarea();
					}
					
				}
				String flinkman = deliverorderinfo.get("flinkman").toString();
				String flinkphone = deliverorderinfo.get("flinkphone").toString();
				BigDecimal famount = new BigDecimal(0);
				int amount = new Integer(deliverorderinfo.get("famount").toString());
				int fassembleqty = deliverorderinfo.get("fassembleQty") == null
						|| deliverorderinfo.get("fassembleQty").equals("") ? 0
						: new Integer(deliverorderinfo.get("fassembleQty").toString());
				if(isrealType.equals("1")){
					famount = new BigDecimal(realamount);
					assembleamount = fassembleqty + realamount ;
					if(amount == assembleamount){
						isreal = true;
					}else if(amount < assembleamount){
						throw new DJException("配送单实配数量之和不能大于配送数量！");
					}
				}else{
					realamount = amount-fassembleqty;
					famount = new BigDecimal(realamount);
				}
								
				farea = farea.multiply(famount);

				String supplierid = "";
				if(deliverorderinfo.get("fsupplierId")!=null && !deliverorderinfo.get("fsupplierId").equals("")){
					supplierid = deliverorderinfo.get("fsupplierId").toString();
				}
				
				ISupplierDao SupplierDao = (ISupplierDao) SpringContextUtils.getBean("SupplierDao");
				Supplier sp = (Supplier)SupplierDao.Query(supplierid);
				String addressid = deliverorderinfo.get("faddressid").toString();
				
				String customerid = deliverorderinfo.get("fcustomerid").toString();
				
				ITruckassembleDao truckassembleDao = (ITruckassembleDao) SpringContextUtils.getBean("TruckassembleDao");
				String assembleentryid ;
				if(Tinfo==null){
//					sql = "select t.fid,max(en.fseq) fseq from t_tra_truckassemble t left join t_tra_truckassembleentry en on en.fparentid=t.fid where t.faudited=0 and t.FTYPE=0 and t.FSUPPLIER='" + supplierid + "' group by t.fid";
					sql = "select fid from t_tra_truckassemble t WHERE t.faudited = 0 AND t.FTYPE = 0 AND t.FSUPPLIER = '" + supplierid + "'";
					List<HashMap<String,Object>> list= this.QueryBySql(sql);
					if(list.size()>0){
						Tinfo = truckassembleDao.Query(list.get(0).get("fid").toString());
						fseq = getTruckassembleEntrySeq(Tinfo.getFid());
					}else{
					
						Tinfo = new Truckassemble();
						Tinfo.setFid(this.CreateUUid());
						Tinfo.setFcreatorid(userid);
						Tinfo.setFcreatetime(new Date());
						Tinfo.setFbizdate(new Date());
						Tinfo.setFnumber(ServerContext.getNumberHelper().getNumber("t_tra_truckassemble", "ST",4,false));//TruckassembleDao.getFnumber("t_tra_truckassemble", "ST",4)
						Tinfo.setFlastupdatetime(new Date());
						Tinfo.setFlastupdateuserid(userid);
						//Tinfo.setFtruckid(Tinfo.getFtruckid());
	//					Tinfo.setFauditorid(null);
	//					Tinfo.setFauditdate(null);
						Tinfo.setFaudited(0);
						Tinfo.setFsupplier(supplierid);
						Tinfo.setFtype(0);
						this.saveOrUpdate(Tinfo);
				    }
				}
				
				sql = "select fid from t_tra_truckassembleentry where fparentid ='"+Tinfo.getFid()+"' and fouted = 0 and fdeliverorderid = '"+deliverorderID+"' ";
				List<HashMap<String,Object>> tnlist= this.QueryBySql(sql);
				if(tnlist.size()>0){
					assembleentryid = tnlist.get(0).get("fid").toString();
					ExecBySql("update t_tra_truckassembleentry set famount=famount+"+realamount+" where fid = '" + assembleentryid +"'");
				}else{
					Tninfo = new Truckassembleentry();
					Tninfo.setFparentid(Tinfo.getFid());
					Tninfo.setFamount(famount);
					Tninfo.setFdeliverorderid(deliverorderID);
					
					//2014-2-23 自运逻辑改为已提货,且直接生成出库单;
					Tninfo.setFdeliveryed(1);
//					Tninfo.setFdeliveryed(0);
					
					Tninfo.setFdeliveryaddress(sp.getFaddress());
					Tninfo.setFdelivery(sp.getFname());
					Tninfo.setFdeliveryphone(sp.getFtel());
					assembleentryid = this.CreateUUid();
					Tninfo.setFid(assembleentryid);
					Tninfo.setFproductid(fproductid);
					Tninfo.setFproductspec(fproductspec);
					Tninfo.setFreceiveaddress(addressid);
					Tninfo.setFreceiver(flinkman);
					Tninfo.setFreceiverphone(flinkphone);
					Tninfo.setFcustomerid(customerid);
					Tninfo.setFremark(deliverorderinfo.get("fdescription")!=null?deliverorderinfo.get("fdescription").toString():"");
					Tninfo.setFsaleorderid(deliverorderinfo.get("fplanid")!=null?deliverorderinfo.get("fplanid").toString():"");
					Tninfo.setFseq(fseq);
					fseq = fseq+1;
					Tninfo.setFsupplierid(supplierid);
					
					this.saveOrUpdate(Tninfo);
			    }
				
				//2014-2-23 配送单自运发货时增加生成出库单;
				int seq=1;
				//查出库单表头
				sql = "select fid from t_tra_saledeliver where faudited=0 and fassembleid='"+Tinfo.getFid()+"' and faddressid='"+addressid+"' and fsupplier='"+supplierid+"'";
				List<HashMap<String,Object>> saledelivercls=this.QueryBySql(sql);
				if(saledelivercls.size()>0)
				{
					saledeliverID = saledelivercls.get(0).get("fid").toString();
					seq = getSaleDeliverEntrySeq(saledeliverID);
				}
				else
				{
					Saledeliver sdinfo = new Saledeliver();
					saledeliverID=this.CreateUUid();
					sdinfo.setFid(saledeliverID);
					sdinfo.setFcreatorid(userid);
					sdinfo.setFcreatetime(new Date());
					sdinfo.setFbizdate(new Date());
					sdinfo.setFnumber(ServerContext.getNumberHelper().getNumber("t_tra_saledeliver", "DJ",4,false));
					sdinfo.setFlastupdatetime(new Date());
					sdinfo.setFlastupdateuserid(userid);
					sdinfo.setFtruckid(Tinfo.getFtruckid());
					sdinfo.setFaddressid(addressid);
					sdinfo.setFcustomerid(customerid);
					sdinfo.setFauditorid(null);
					sdinfo.setFauditdate(null);
					sdinfo.setFaudited(0);
					sdinfo.setFassembleid(Tinfo.getFid());
					sdinfo.setFtype(Tinfo.getFtype());
					sdinfo.setFsupplier(supplierid);
					id += saledeliverID;
					if(i<deliverordercls.size()-1){
						id += ",";
					}
					params.put("saledeliverID",id);
					this.saveOrUpdate(sdinfo);	
				}
				
				sql = "select fid from t_tra_saledeliverentry where fparentid ='"+saledeliverID+"' and fassembleentryid = '"+assembleentryid+"' and fdeliverorderid = '"+deliverorderID+"' ";
				tnlist= this.QueryBySql(sql);
				if(tnlist.size()>0){
					ExecBySql("update t_tra_saledeliverentry set famount=famount+"+realamount+",frealamount=famount where fid = '" + tnlist.get(0).get("fid").toString() +"'");
				}else{
					Saledeliverentry sdninfo = new Saledeliverentry();
					sdninfo.setFparentid(saledeliverID);
					sdninfo.setFamount(famount);
					sdninfo.setFrealamount(sdninfo.getFamount());
					sdninfo.setFdeliverorderid(deliverorderID);
					sdninfo.setFdelivery(sp.getFname());
					sdninfo.setFdeliveryaddress(sp.getFaddress());
					sdninfo.setFdeliveryphone(sp.getFtel());
					sdninfo.setFid(this.CreateUUid());
					sdninfo.setFproductid(fproductid);
					sdninfo.setFproductspec(fproductspec);
					sdninfo.setFmaterialspec(fmaterial);
					sdninfo.setFreceiveaddress(addressid);
					sdninfo.setFreceiver(flinkman);
					sdninfo.setFreceiverphone(flinkphone);
					sdninfo.setFremark(null);
					sdninfo.setFsaleorderid(deliverorderinfo.get("fplanid")!=null?deliverorderinfo.get("fplanid").toString():"");
				    sdninfo.setFseq(seq);
					sdninfo.setFsupplierid(supplierid);
					sdninfo.setFassembleentryid(assembleentryid);
					sdninfo.setFisreceipts(0);
					this.saveOrUpdate(sdninfo);	
				}
				
				//反写要货申请状态为装配状态;
				/*List<HashMap<String,Object>> delivers = QueryBySql("select 1 from t_ord_deliverorder where fouted = 1 and fdeliversId = '" + deliverorderinfo.get("fdeliversID").toString() + "' ");
				if(delivers.size()<1){
					if(isrealType.equals("1")){
						if(isreal){
							deliverapplyDao.updateDeliverapplyStateByDelivers(deliverorderinfo.get("fdeliversID").toString(), 4);					
						}
					}else{
						deliverapplyDao.updateDeliverapplyStateByDelivers(deliverorderinfo.get("fdeliversID").toString(), 4);
					}
				}*/
				
			}
			
			if(isrealType.equals("1")){
				if(isreal){
					ExecBySql("update t_ord_deliverorder set fmatched=1,fassembleQty="+assembleamount+",fdelivertype=1 where fid = " + fids);
				}else{
					ExecBySql("update t_ord_deliverorder set fmatched=0,fassembleQty="+assembleamount+",fdelivertype=1 where fid = " + fids);
				}
			}else{
				ExecBySql("update t_ord_deliverorder set fmatched=1,fassembleQty=famount,fdelivertype=1 where fid in " + fids);
			}
//			params.put("success", true);
//			return params;
		}
		//未使用
		if(type == 2){	//收回配送单
			ArrayList<String> updatecol = (ArrayList<String>)params.get("updatecol");
			for(int j=0;j<updatecol.size();j++){
				String updatesql = updatecol.get(j).toString();
				if(updatesql.length()>0){
					ExecBySql(updatesql);
				}
			}
			//反写要货申请状态为分配状态;
			if(params.get("deliversid")!=null && !params.get("deliversid").toString().equals("")){
				String deliversid = params.get("deliversid").toString();
				if(deliversid.contains(",")){
					String[] deliversIDs = deliversid.split(",");
					for(int k=0;k<deliversIDs.length;k++){
						deliverapplyDao.updateDeliverapplyStateByDelivers(deliversIDs[k], 3);
					}
				}else{
					deliverapplyDao.updateDeliverapplyStateByDelivers(deliversid, 3);
				}
			}
			
		}
		params.put("success", true);
		return params;
	}
	@Override
	public int getSaleDeliverEntrySeq(String saledeliverID) {
		String sql;
		int seq;
		List<HashMap<String, Object>> list;
		sql = "select max(fseq) fseq from t_tra_saledeliverentry where fparentid = '"+saledeliverID+"'";
		list=this.QueryBySql(sql);
		if(list.size()==0){
			throw new DJException("出库单分录丢失！");
		}
		seq= new Integer(list.get(0).get("fseq").toString())+1;
		return seq;
	}
	
	public int getTruckassembleEntrySeq(String truckassembleID) {
		String sql;
		int seq;
		List<HashMap<String, Object>> list;
		sql = "select max(fseq) fseq from t_tra_truckassembleentry where fparentid = '"+truckassembleID+"'";
		list=this.QueryBySql(sql);
		if(list.size()==0){
			throw new DJException("出库单分录丢失！");
		}
		seq= new Integer(list.get(0).get("fseq").toString())+1;
		return seq;
	}

	public void ExecSave(List<Deliverorder> vmi) {
		Usedstorebalance usb=null;
		List<Usedstorebalance> list = null;
		String hql;
		Deliverorder order = null;
		 for(int i=0;i<vmi.size();i++)
		 {
			 order=vmi.get(i);
			 this.saveOrUpdate(order);
			 //保存占用表
			 if(i==0){
				 hql = "from Usedstorebalance where fdeliverorderid = '"+order.getFid()+"'";
				 list = this.QueryByHql(hql);
				 for(Usedstorebalance u : list){
					 u.setFusedqty(order.getFamount()*u.getFratio());
					 this.saveOrUpdate(u);
				 }
			 }else{
				 for(Usedstorebalance u : list){
					 try {
						usb = (Usedstorebalance) u.clone();
					 } catch (CloneNotSupportedException e) {
						throw new RuntimeException(e);
					 }
					 usb.setFid(this.CreateUUid());
					 usb.setFdeliverorderid(order.getFid());
					 usb.setFusedqty(order.getFamount()*u.getFratio());
					 this.saveOrUpdate(usb);
				 }
			 }
		 }
	}

	@Override
	public void ExecUpdateSQL(String updatesql) {
		// TODO Auto-generated method stub
		this.ExecBySql(updatesql);
		
	}
	/**
	 * 替换订单
	 */
	@Override
	public void ExecUpdateAllot(Deliverorder deliverorder, Storebalance storebalance) {
		String oldNumber = deliverorder.getFordernumber();
		String newNumber;
		String sql = null,insertusql="";
		String fissuit=storebalance.getFupdateuserid();//fissuit是否是套装;fftype通知还是订单类型;是否套装通过Fupdateuserid传递值
		int fftype=storebalance.getFtype();//fftype通知还是订单类型
		String fdeliversID = deliverorder.getFdeliversId();
		sql = "select fordernumber,fnumber from t_ord_delivers where fid='"+fdeliversID+"'";
		List<HashMap<String, String>> list= this.QueryBySql(sql);
		String fordernumber=list.get(0).get("fordernumber");
		if(StringHelper.isEmpty(storebalance.getFcreatorid()))//结存表的下单类型为通知getFcreatorid 实质存的是 制造商编码
		{
			newNumber=list.get(0).get("fnumber");//为要货管理的编码
		} else
		{
			newNumber=storebalance.getFcreatorid();//制造商编码
		}
		sql = "update t_ord_deliverorder set fsaleorderid=:fsaleorderid,forderentryid=:forderentryid,fplanid=:fplanid,fsupplierId=:fsupplierId"
				+",fstorebalanceid=:fstorebalanceid,fordernumber=:fordernumber,fplanNumber=:fplanNumber where fid='"+deliverorder.getFid()+"'";
		params p = new params();
		p.setString("fsaleorderid", storebalance.getFsaleorderid());
		p.setString("forderentryid", storebalance.getForderentryid());
		p.setString("fplanid", storebalance.getFproductplanId());
		p.setString("fsupplierId", storebalance.getFsupplierID());
		p.setString("fstorebalanceid",fftype==1?storebalance.getFid():"");
		p.setString("fordernumber", newNumber);
		p.setString("fplanNumber", newNumber);
		this.ExecBySql(sql, p);
		
				//先删除原有的占有表，在新增
				sql="delete from  t_inv_usedstorebalance where fdeliverorderid='"+deliverorder.getFid()+"'";
				this.ExecBySql(sql);
				
				if(fftype==1||"0".equals(fissuit))
				{
					insertusql="INSERT INTO t_inv_usedstorebalance (fid,fratio,fusedqty,fdeliverorderid,fstorebalanceid,fproductid,ftype ) VALUES( ";
					insertusql=insertusql+"'"+CreateUUid()+"',";//<{fid: }>,
					insertusql=insertusql+1+",";//<{fratio: }>,
					insertusql=insertusql+storebalance.getFbalanceqty();//<{fusedqty: }>,
					insertusql=insertusql+",'"+deliverorder.getFid()+"',";//<{fdeliverorderid: }>,????
					insertusql=insertusql+"'"+storebalance.getFid()+"',";//<{fstorebalanceid: }>,
					insertusql=insertusql+"'"+deliverorder.getFproductid()+"',";//<{fproductid: }>,
					insertusql=insertusql+fftype+")";//<{ftype: }>,		
					ExecBySql(insertusql);
				}
				else if("1".equals(fissuit)&&fftype==0)
				{
					sql="select p.fproductid,sbl.fid as fstorebalanceid"
						+" from  t_pdt_productdefproducts  p "
						+" left join  t_inv_storebalance sbl on sbl.fproductid=p.fproductid and sbl.ftype=0 and  sbl.fsupplierid='"+storebalance.getFsupplierID()+"'"
						+" where p.fparentid='"+deliverorder.getFproductid()+"' and ifnull(sbl.fid,'')=''";
					List<HashMap<String,Object>> storeslist=QueryBySql(sql);
					if(storeslist.size()>0)	{
					throw new DJException("该制造商的套装产品，其子件没有存在库存记录，请先调整");
					}

					sql = "select p.fproductid,p.famount as ratio,ifnull(sbl.fbalanceqty+sbl.fallotqty,0) as fbalanceqty,sbl.fid as fstorebalanceid,sbl.fsupplierid as FSUPPLIERID "
							+ " from  t_pdt_productdefproducts  p "
							+ " left join  t_inv_storebalance sbl on sbl.fproductid=p.fproductid and sbl.ftype=0 and  sbl.fsupplierid='"
							+ storebalance.getFsupplierID()
							+ "'"
							+ " where p.fparentid='"
							+ deliverorder.getFproductid()
							+ "' ";
					storeslist = QueryBySql(sql);
				
				//新增库存占用量表
				for(int m=0;m<storeslist.size();m++)
				{
					insertusql="INSERT INTO t_inv_usedstorebalance (fid,fratio,fusedqty,fdeliverorderid,fstorebalanceid,fproductid,ftype ) VALUES( ";
					insertusql=insertusql+"'"+CreateUUid()+"',";//<{fid: }>,
					insertusql=insertusql+new BigDecimal(storeslist.get(m).get("ratio").toString())+",";//<{fratio: }>,
					insertusql=insertusql+new BigDecimal(storeslist.get(m).get("ratio").toString()).multiply(new BigDecimal(storebalance.getFbalanceqty()));//<{fusedqty: }>,
					insertusql=insertusql+",'"+deliverorder.getFid()+"',";//<{fdeliverorderid: }>,????
					insertusql=insertusql+"'"+storeslist.get(m).get("fstorebalanceid")+"',";//<{fstorebalanceid: }>,
					insertusql=insertusql+"'"+storeslist.get(m).get("fproductid")+"',";//<{fproductid: }>,
					insertusql=insertusql+"0)";//<{ftype: }>,		
					ExecBySql(insertusql);
				}
				}
		sql = "update t_ord_delivers set fordernumber='"+(fordernumber == null?newNumber:fordernumber.replaceFirst(oldNumber, newNumber))+"' where fid='"+fdeliversID+"'";
		this.ExecBySql(sql);
	}
	
	public HashMap<String, Object> ExecCreateTruckassemble(HashMap<String, Object> params, HashMap<String, Object> enrtyparams)
		    throws Exception
		  {
		    List batchfids = new ArrayList();
		    List fullyfids = new ArrayList();
		    List overfids = new ArrayList();
		    List deliverordercls = (List)params.get("deliverordercls");
		    String userid = params.get("userid").toString();

		    String supplierid = params.get("fsupplierid").toString();
		    Truckassemble Tinfo = null;
		    Truckassembleentry Tninfo = null;
		    int fseq = 1;
		    String saledeliverID = "";
		    String sql = "";

		    ISupplierDao SupplierDao = (ISupplierDao)SpringContextUtils.getBean("SupplierDao");
		    Supplier sp = SupplierDao.Query(supplierid);

		    for (int i = 0; i < deliverordercls.size(); i++) {
				  HashMap deliverorderinfo = (HashMap)deliverordercls.get(i);
				  String deliverorderID = deliverorderinfo.get("fid").toString();
				  String fproductspec = "";
				  String fmaterial = "";
				  String fproductid = deliverorderinfo.get("fproductid").toString();
				  BigDecimal farea = new BigDecimal(0);
				  IProductdefDao productdefDao = (IProductdefDao)SpringContextUtils.getBean("productdefDao");
				  Productdef pdtinfo = productdefDao.Query(fproductid);
				  if (pdtinfo != null) {
				    fproductspec = pdtinfo.getFboxlength() + "×" + pdtinfo.getFboxwidth() + "×" + pdtinfo.getFboxheight();
					String sqls = "select CONCAT_WS('x',a.`FBOXLENGTH`,a.`FBOXWIDTH`,a.`FBOXHEIGHT`) fproductspec,CONCAT_WS('x',a.`fmateriallength`,a.`fmaterialwidth`) fmaterial  from t_ord_deliverorder d left join t_ord_deliverapply a on d.fdeliversID=a.fid where d.fboxtype=1 and d.fid='"+deliverorderID+"'";
					List<HashMap<String,Object>> list = this.QueryBySql(sqls);
					if(list.size()>0){
						fproductspec = (String)list.get(0).get("fproductspec");//纸板规格
						fmaterial = (String)list.get(0).get("fmaterial");//下料规格
					}
				    farea = pdtinfo.getFarea();
				  }
				  String flinkman = deliverorderinfo.get("flinkman").toString();
				  String flinkphone = deliverorderinfo.get("flinkphone").toString();
				  BigDecimal famount = new BigDecimal(0);
				  int amount = new Integer(deliverorderinfo.get("famount").toString()).intValue();
				  int fassembleQty = (deliverorderinfo.get("fassembleQty") == null) || (deliverorderinfo.get("fassembleQty").equals("")) ? 0 : new Integer(deliverorderinfo.get("fassembleQty").toString()).intValue();
				  famount = new BigDecimal(enrtyparams.get(deliverorderID).toString());
				  int assembleamount = fassembleQty + famount.intValue();
				  if (amount == assembleamount) {
					  
					  fullyfids.add(deliverorderID);
				    
				  }else if(amount < assembleamount){
//				    if (amount < assembleamount) {
//			        	throw new DJException("配送单实配数量之和不能大于配送数量！");
//			        }
					  overfids.add(deliverorderID);
					  
				  } else {
				
					  batchfids.add(deliverorderID);
				  }
				  farea = farea.multiply(famount);
				  String addressid = deliverorderinfo.get("faddressid").toString();
				  String customerid = deliverorderinfo.get("fcustomerid").toString();
				  ITruckassembleDao truckassembleDao = (ITruckassembleDao)SpringContextUtils.getBean("TruckassembleDao");
				
				  if (Tinfo == null)
				  {
				    Tinfo = new Truckassemble();
				    Tinfo.setFid(CreateUUid());
				    Tinfo.setFcreatorid(userid);
				    Tinfo.setFcreatetime(new Date());
				    Tinfo.setFbizdate(new Date());
				    Tinfo.setFnumber(ServerContext.getNumberHelper().getNumber("t_tra_truckassemble", "ST", 4, false));
				Tinfo.setFlastupdatetime(new Date());
				Tinfo.setFlastupdateuserid(userid);
				Tinfo.setFtruckid(params.get("flicensenum") == null ? "" : params.get("flicensenum").toString());
				Tinfo.setFdriverid(params.get("fdriver") == null ? "" : params.get("fdriver").toString());
				Tinfo.setFexternalNum(params.get("fsaleid") == null ? "" : params.get("fsaleid").toString());
				Tinfo.setFphone(params.get("ftelephone") == null ? "" : params.get("ftelephone").toString());
				    Tinfo.setFaudited(Integer.valueOf(0));
				    Tinfo.setFsupplier(supplierid);
				    Tinfo.setFtype(Integer.valueOf(1));
				    saveOrUpdate(Tinfo);
				  }
				
				  Tninfo = new Truckassembleentry();
				  Tninfo.setFparentid(Tinfo.getFid());
				  Tninfo.setFamount(famount);
				  Tninfo.setFdeliverorderid(deliverorderID);
				
				  Tninfo.setFdeliveryed(Integer.valueOf(1));
				  Tninfo.setFdeliveryaddress(sp.getFaddress());
				  Tninfo.setFdelivery(sp.getFname());
				  Tninfo.setFdeliveryphone(sp.getFtel());
				  String assembleentryid = CreateUUid();
				  Tninfo.setFid(assembleentryid);
				  Tninfo.setFproductid(fproductid);
				  Tninfo.setFproductspec(fproductspec);
				  Tninfo.setFreceiveaddress(addressid);
				  Tninfo.setFreceiver(flinkman);
				  Tninfo.setFreceiverphone(flinkphone);
				  Tninfo.setFcustomerid(customerid);
				  Tninfo.setFremark(deliverorderinfo.get("fdescription") != null ? deliverorderinfo.get("fdescription").toString() : "");
				  Tninfo.setFsaleorderid(deliverorderinfo.get("fplanid") != null ? deliverorderinfo.get("fplanid").toString() : "");
				  Tninfo.setFseq(Integer.valueOf(fseq));
				  fseq++;
				  Tninfo.setFsupplierid(supplierid);
				
				  saveOrUpdate(Tninfo);
				
				  int seq = 1;
				
				  sql = " select t.fid from t_tra_saledeliver t where faudited=0 and fassembleid='" + Tinfo.getFid() + "' and faddressid='" + addressid + "' and fsupplier='" + supplierid + "'";
				  List saledelivercls = QueryBySql(sql);
				  if (saledelivercls.size() > 0)
				  {
				    saledeliverID = ((HashMap)saledelivercls.get(0)).get("fid").toString();
				    seq = getSaleDeliverEntrySeq(saledeliverID);
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
				    sdinfo.setFtruckid(Tinfo.getFtruckid());
				    sdinfo.setFaddressid(addressid);
				    sdinfo.setFcustomerid(customerid);
				    sdinfo.setFauditorid(null);
				    sdinfo.setFauditdate(null);
				    sdinfo.setFaudited(Integer.valueOf(0));
				    sdinfo.setFassembleid(Tinfo.getFid());
				    sdinfo.setFtype(Tinfo.getFtype());
				    sdinfo.setFsupplier(supplierid);
				    saveOrUpdate(sdinfo);
				  }
				  Saledeliverentry sdninfo = new Saledeliverentry();
				  sdninfo.setFparentid(saledeliverID);
				  sdninfo.setFamount(famount);
				  sdninfo.setFrealamount(sdninfo.getFamount());
				  sdninfo.setFdeliverorderid(deliverorderID);
				  sdninfo.setFdelivery(sp.getFname());
				  sdninfo.setFdeliveryaddress(sp.getFaddress());
				  sdninfo.setFdeliveryphone(sp.getFtel());
				  sdninfo.setFid(CreateUUid());
				  sdninfo.setFproductid(fproductid);
				  sdninfo.setFproductspec(fproductspec);
				  sdninfo.setFreceiveaddress(addressid);
				  sdninfo.setFreceiver(flinkman);
				  sdninfo.setFreceiverphone(flinkphone);
				  sdninfo.setFremark(null);
				  sdninfo.setFsaleorderid(deliverorderinfo.get("fplanid") != null ? deliverorderinfo.get("fplanid").toString() : "");
				  sdninfo.setFseq(Integer.valueOf(seq));
				  sdninfo.setFsupplierid(supplierid);
				  sdninfo.setFassembleentryid(assembleentryid);
				  sdninfo.setFisreceipts(Integer.valueOf(0));
				  saveOrUpdate(sdninfo);
				  
				/*List<HashMap<String,Object>> delivers = QueryBySql("select 1 from t_ord_deliverorder where fouted = 1 and fdeliversId = '" + deliverorderinfo.get("fdeliversID").toString() + "' ");
				if(delivers.size()<1){
					if ((fullyfids.size()>0&&fullyfids.contains(deliverorderID)) || overfids.size()>0&&overfids.contains(deliverorderID))
					{
					    this.deliverapplyDao.updateDeliverapplyStateByDelivers(deliverorderinfo.get("fdeliversID").toString(), 4);
					}
				}*/
				
				if (batchfids.size()>0&&batchfids.contains(deliverorderID)) {
				   ExecBySql("update t_ord_deliverorder set fmatched=0,fassembleQty=" + assembleamount + " where fid ='" + deliverorderID + "'");
				}
				
				if (overfids.size()>0&&overfids.contains(deliverorderID)) {
					   ExecBySql("update t_ord_deliverorder set fmatched=1,fassembleQty=" + assembleamount + " where fid ='" + deliverorderID + "'");
				}
		    }
		    
		    if(fullyfids.size()>0){
			    String fidcls = "('" + fullyfids.toString().substring(1,fullyfids.toString().length()-1 ).replace(", ", "','") + "')";
			    ExecBySql("update t_ord_deliverorder set fmatched=1,fassembleQty=famount where fid in " + fidcls);
		    }
		    
		    params.put("success", Boolean.valueOf(true));
		    return params;
		  }

	@Override
	public String ExecCreatTruckassembleByCondition(HttpServletRequest request, HttpServletResponse reponse) throws Exception {
		// TODO Auto-generated method stub
		
		DeliverorderController deliverorderController = (DeliverorderController) SpringContextUtils.getBean("deliverorderController");
		String result = "";
		List list = new ArrayList();
		if (Integer.parseInt((String) syscfgDao.ExecGainCfgByFkey(request, "autoDepart").getData().get(0).get("fvalue")) == 1) {
			//自运，发车
			result = deliverorderController.doCreatTruckassemble(request, reponse,list);
			
			 depart(request);
			 
		}else {
			//自运
			result = deliverorderController.doCreatTruckassemble(request, reponse,list);
			
		}
		return result;
	}

	//发车
	public void depart(HttpServletRequest request) throws Exception {
		String deliverorderfids = request.getParameter("fidcls");
			String fids="('"+deliverorderfids.replace(",","','")+"')";
			TruckassembleController truckassembleController = (TruckassembleController) SpringContextUtils.getBean("truckassembleController");
			
			String hql = " select distinct t.fparentid fparentid from t_tra_Truckassembleentry t left join t_tra_truckassemble tk on tk.fid=t.fparentid where tk.faudited=0 and t.fdeliverorderid in  " + fids;
			
			List<HashMap<String,String>> truckassembleIds = QueryBySql(hql);
			
			for (HashMap<String,String> id : truckassembleIds) {
				
				truckassembleController.actionAuditById(request, id.get("fparentid"));
				
			}
		
	}
		
	//生成提货 ，又自动发货
	public HashMap<String, Object> ExecCreateTruckassembleSDK(HashMap<String, Object> params, HashMap<String, Object> enrtyparams)
		    throws Exception
		  {
			String saleid = params.get("fsaleid") == null ? "" : params.get("fsaleid").toString();
			if(QueryExistsBySql("select fid from t_tra_truckassemble where fexternalNum = '"+saleid+"'")){
				params.put("success", Boolean.valueOf(true));
			    return params;
			}
			
			IStorebalanceDao  storebalanceDao= (IStorebalanceDao)SpringContextUtils.getBean("StorebalanceDao");
			IOutWarehouseDao outWarehouseDao = (IOutWarehouseDao)SpringContextUtils.getBean("outWarehouseDao");
			List batchfids = new ArrayList();
		    List fullyfids = new ArrayList();
		    List overfids = new ArrayList();
		    List<Truckassemble> tlist=new ArrayList<Truckassemble>();
		    List<Saledeliver> slist=new ArrayList<Saledeliver>();
		    List deliverordercls = (List)params.get("deliverordercls");
		    String userid = params.get("userid").toString();

		    String usersupplierid = params.get("fsupplierid").toString();
		    Truckassemble Tinfo = null;
		    Truckassembleentry Tninfo = null;
		    int fseq = 1;
		    String saledeliverID = "";
		    String sql = "",forderEntryid="",fproductplanid="";

		    ISupplierDao SupplierDao = (ISupplierDao)SpringContextUtils.getBean("SupplierDao");
		    HashMap deliverorderinfo;
		    for (int i = 0; i < deliverordercls.size(); i++) {
				  deliverorderinfo = (HashMap)deliverordercls.get(i);
				  String deliverorderID = deliverorderinfo.get("fid").toString();
				  Supplier sp = SupplierDao.Query(deliverorderinfo.get("fsupplierId").toString());
				  String supplierid=sp.getFid();
				  int ftype=new Integer(deliverorderinfo.get("ftype").toString());
				  String fproductspec = "";
				  String fmaterial = "";
				  String fproductid = deliverorderinfo.get("fproductid").toString();
				  BigDecimal farea = new BigDecimal(0);
				  IProductdefDao productdefDao = (IProductdefDao)SpringContextUtils.getBean("productdefDao");
				  Productdef pdtinfo = productdefDao.Query(fproductid);
				  if (pdtinfo != null) {
				    fproductspec = pdtinfo.getFboxlength() + "×" + pdtinfo.getFboxwidth() + "×" + pdtinfo.getFboxheight();
					String sqls = "select CONCAT_WS('x',a.`FBOXLENGTH`,a.`FBOXWIDTH`,a.`FBOXHEIGHT`) fproductspec,CONCAT_WS('x',a.`fmateriallength`,a.`fmaterialwidth`) fmaterial  from t_ord_deliverorder d left join t_ord_deliverapply a on d.fdeliversID=a.fid where d.fboxtype=1 and d.fid='"+deliverorderID+"'";
					List<HashMap<String,Object>> list = this.QueryBySql(sqls);
					if(list.size()>0){
						fproductspec = (String)list.get(0).get("fproductspec");//纸板规格
						fmaterial = (String)list.get(0).get("fmaterial");//下料规格
					}
				    if(!StringUtils.isEmpty(pdtinfo.getFarea())){
						farea = pdtinfo.getFarea();
					}
				  }
				  String flinkman = deliverorderinfo.get("flinkman").toString();
				  String flinkphone = deliverorderinfo.get("flinkphone").toString();
				  BigDecimal famount = new BigDecimal(0);
				  int amount = new Integer(deliverorderinfo.get("famount").toString()).intValue();
				  int fassembleQty = (deliverorderinfo.get("fassembleQty") == null) || (deliverorderinfo.get("fassembleQty").equals("")) ? 0 : new Integer(deliverorderinfo.get("fassembleQty").toString()).intValue();
				  famount = new BigDecimal(enrtyparams.get(deliverorderID).toString());
				  int assembleamount = fassembleQty + famount.intValue();
				  if (amount == assembleamount) {
					  
					  fullyfids.add(deliverorderID);
				    
				  }else if(amount < assembleamount){
//				    if (amount < assembleamount) {
//			        	throw new DJException("配送单实配数量之和不能大于配送数量！");
//			        }
					  overfids.add(deliverorderID);
					  
				  } else {
				
					  batchfids.add(deliverorderID);
				  }
				  farea = farea.multiply(famount);
				  String addressid = deliverorderinfo.get("faddressid").toString();
				  String customerid = deliverorderinfo.get("fcustomerid").toString();
				  ITruckassembleDao truckassembleDao = (ITruckassembleDao)SpringContextUtils.getBean("TruckassembleDao");
				
				  if (Tinfo == null)
				  {
				    Tinfo = new Truckassemble();
				    Tinfo.setFid(CreateUUid());
				    Tinfo.setFcreatorid(userid);
				    Tinfo.setFcreatetime(new Date());
				    Tinfo.setFbizdate(new Date());
//				    Tinfo.setFnumber(ServerContext.getNumberHelper().getNumber("t_tra_truckassemble", "ST", 4, false));
				    Tinfo.setFlastupdatetime(new Date());
			    	Tinfo.setFlastupdateuserid(userid);
			    	Tinfo.setFtruckid(params.get("flicensenum") == null ? "" : params.get("flicensenum").toString());
			    	Tinfo.setFdriverid(params.get("fdriver") == null ? "" : params.get("fdriver").toString());
			    	Tinfo.setFexternalNum(params.get("fsaleid") == null ? "" : params.get("fsaleid").toString());
			    	Tinfo.setFphone(params.get("ftelephone") == null ? "" : params.get("ftelephone").toString());
				    Tinfo.setFaudited(Integer.valueOf(1));//修改为1
				    Tinfo.setFsupplier(usersupplierid);//提货单的供应商
				    Tinfo.setFtype(Integer.valueOf(1));
				    saveOrUpdate(Tinfo);
				    tlist.add(Tinfo);
				  }
				
				  Tninfo = new Truckassembleentry();
				  Tninfo.setFparentid(Tinfo.getFid());
				  Tninfo.setFamount(famount);
				  Tninfo.setFdeliverorderid(deliverorderID);
				
				  Tninfo.setFdeliveryed(Integer.valueOf(1));
				  Tninfo.setFdeliveryaddress(sp.getFaddress());
				  Tninfo.setFdelivery(sp.getFname());
				  Tninfo.setFdeliveryphone(sp.getFtel());
				  String assembleentryid = CreateUUid();
				  Tninfo.setFid(assembleentryid);
				  Tninfo.setFproductid(fproductid);
				  Tninfo.setFproductspec(fproductspec);
				  Tninfo.setFreceiveaddress(addressid);
				  Tninfo.setFreceiver(flinkman);
				  Tninfo.setFreceiverphone(flinkphone);
				  Tninfo.setFcustomerid(customerid);
				  Tninfo.setFremark(deliverorderinfo.get("fdescription") != null ? deliverorderinfo.get("fdescription").toString() : "");
				  Tninfo.setFsaleorderid(deliverorderinfo.get("fplanid") != null ? deliverorderinfo.get("fplanid").toString() : "");
				  Tninfo.setFseq(Integer.valueOf(fseq));
				  fseq++;
				  Tninfo.setFsupplierid(supplierid);//配送信息中的供应商
				  Tninfo.setFouted(1);//发车
				  Tninfo.setFoutor(userid);//发车
				  Tninfo.setFouttime(new Date());//发车
				  saveOrUpdate(Tninfo);
				
				  int seq = 1;
				
				  sql = " select t.fid from t_tra_saledeliver t where faudited=1 and fassembleid='" + Tinfo.getFid() + "' and faddressid='" + addressid + "' and fsupplier='" + supplierid + "'";
				  List saledelivercls = QueryBySql(sql);
				  if (saledelivercls.size() > 0)
				  {
				    saledeliverID = ((HashMap)saledelivercls.get(0)).get("fid").toString();
				    seq = getSaleDeliverEntrySeq(saledeliverID);
				  }
				  else
				  {
				    Saledeliver sdinfo = new Saledeliver();
				    saledeliverID = CreateUUid();
				    sdinfo.setFid(saledeliverID);
				    sdinfo.setFcreatorid(userid);
				    sdinfo.setFcreatetime(new Date());
				    sdinfo.setFbizdate(new Date());
//				    sdinfo.setFnumber(ServerContext.getNumberHelper().getNumber("t_tra_saledeliver", "DJ", 4, false));
				    sdinfo.setFlastupdatetime(new Date());
				    sdinfo.setFlastupdateuserid(userid);
				    sdinfo.setFtruckid(Tinfo.getFtruckid());
				    sdinfo.setFaddressid(addressid);
				    sdinfo.setFcustomerid(customerid);
				    sdinfo.setFauditorid(userid);//发车
				    sdinfo.setFauditdate(new Date());//发车
				    sdinfo.setFaudited(Integer.valueOf(1));//发车
				    sdinfo.setFassembleid(Tinfo.getFid());
				    sdinfo.setFtype(Tinfo.getFtype());
				    sdinfo.setFsupplier(supplierid);
				    saveOrUpdate(sdinfo);
				    slist.add(sdinfo);
				  }
				  Saledeliverentry sdninfo = new Saledeliverentry();
				  sdninfo.setFparentid(saledeliverID);
				  sdninfo.setFamount(famount);
				  sdninfo.setFrealamount(sdninfo.getFamount());
				  sdninfo.setFdeliverorderid(deliverorderID);
				  sdninfo.setFdelivery(sp.getFname());
				  sdninfo.setFdeliveryaddress(sp.getFaddress());
				  sdninfo.setFdeliveryphone(sp.getFtel());
				  sdninfo.setFid(CreateUUid());
				  sdninfo.setFproductid(fproductid);
				  sdninfo.setFproductspec(fproductspec);
				  sdninfo.setFmaterialspec(fmaterial);
				  sdninfo.setFreceiveaddress(addressid);
				  sdninfo.setFreceiver(flinkman);
				  sdninfo.setFreceiverphone(flinkphone);
				  sdninfo.setFremark(null);
				  sdninfo.setFsaleorderid(deliverorderinfo.get("fplanid") != null ? deliverorderinfo.get("fplanid").toString() : "");
				  sdninfo.setFseq(Integer.valueOf(seq));
				  sdninfo.setFsupplierid(supplierid);
				  sdninfo.setFassembleentryid(assembleentryid);
				  sdninfo.setFisreceipts(Integer.valueOf(0));
				  saveOrUpdate(sdninfo);
				  //发车、*
				  //配送类型为补单类型，不扣库存
				  if("1".equals(ftype)){
						
						continue;
					}
				//扣除库存先根据配送ID查找占用量记录表;
					List<HashMap<String,Object>> usedstorebalancelist= this.QueryBySql("SELECT fid, fratio, fusedqty, fdeliverorderid, fstorebalanceid, fproductid, ftype FROM t_inv_usedstorebalance where fdeliverorderid = '" + deliverorderID + "'");
					if(usedstorebalancelist.size()==0){
						throw new DJException("占用库存表数据异常！");
					}
					int famountt=famount.divide(new BigDecimal(1), 0, BigDecimal.ROUND_UP).intValue();
					for(int k=0;k<usedstorebalancelist.size();k++){
						int usedqty = new Integer(usedstorebalancelist.get(k).get("fusedqty").toString());
						int ratio = new Integer(usedstorebalancelist.get(k).get("fratio").toString());
						String usedsbid = usedstorebalancelist.get(k).get("fid").toString();
						String sbid = usedstorebalancelist.get(k).get("fstorebalanceid").toString();
						Storebalance sbinfo = storebalanceDao.Query(sbid);
						
						if(sbinfo.getFbalanceqty()-famountt*ratio<0){
							throw new DJException("生成的提货单中,配送单号:"+this.Query(deliverorderID).getFnumber()+"的产品库存数量不足，不能发车！");
						}
						ExecBySql("update t_inv_usedstorebalance set fusedqty = "+(usedqty-famountt*ratio)+" where fid ='" + usedsbid + "'");
						sbinfo.setFbalanceqty(sbinfo.getFbalanceqty()-famountt*ratio);
						sbinfo.setFoutqty(sbinfo.getFoutqty()+famountt*ratio);
						storebalanceDao.ExecSave(sbinfo);
						forderEntryid = sbinfo.getForderentryid();
						fproductplanid = sbinfo.getFproductplanId();
						//生成出库记录
						Outwarehouse Outwarehouseinfo = new Outwarehouse();
//						Outwarehouseinfo.setFnumber(ServerContext.getNumberHelper().getNumber("t_inv_outwarehouse", "U", 4,false));
						Outwarehouseinfo.setForderentryid(forderEntryid);
						Outwarehouseinfo.setFid(CreateUUid());
						Outwarehouseinfo.setFproductplanid(fproductplanid);
						Outwarehouseinfo.setFcreatorid(userid);
						Outwarehouseinfo.setFcreatetime(new Date());
						Outwarehouseinfo.setFlastupdatetime(null);
						Outwarehouseinfo.setFlastupdateuserid(null);
						Outwarehouseinfo.setFoutqty(new BigDecimal(famountt*ratio));
						Outwarehouseinfo.setFwarehouseid(sp.getFwarehouseid());
						Outwarehouseinfo.setFwarehousesiteid(sp.getFwarehousesiteid());
						Outwarehouseinfo.setFproductid(fproductid);
						Outwarehouseinfo.setFremak(null);
						Outwarehouseinfo.setFsaleorderid(sbinfo.getFsaleorderid());
						Outwarehouseinfo.setFaudited(1);
						Outwarehouseinfo.setFauditorid(userid);
						Outwarehouseinfo.setFaudittime(new Date());
						Outwarehouseinfo.setFsupplierid(supplierid);
						outWarehouseDao.ExecSave(Outwarehouseinfo);
						
						//更新生产订单
						if(forderEntryid!=null && !forderEntryid.equals("")){
							sql = "update t_ord_saleorder set fstoreqty = fstoreqty - "
									+ famountt*ratio
									+ ",fstockoutqty = fstockoutqty + "
									+ famountt*ratio
									+ " where fid='"
									+ forderEntryid
									+ "' ";
							ExecBySql(sql);
						}
						//更新制造商订单
						if(fproductplanid!=null && !fproductplanid.equals("")){
							sql = "update t_ord_productplan set fstoreqty = fstoreqty - "
									+ famountt*ratio
									+ ",fstockoutqty = fstockoutqty + "
									+ famountt*ratio
									+ " where fid='"
									+ fproductplanid
									+ "' ";
							ExecBySql(sql);
						}
							
					}

					
				//更新配送信息 发车 对应的发货类型--自运1还是协同发货2，出库时间实时更新 fstate 1：部分 2：全部发货
				if (batchfids.size()>0&&batchfids.contains(deliverorderID)) {//部分发货
				   ExecBySql("update t_ord_deliverorder set fmatched=0,fassembleQty=" + assembleamount + ",fdelivertype=2,fstate=1,fouttime=now(),foutQty=ifnull(foutQty,0)+"+Tninfo.getFamount()+" where fid ='" + deliverorderID + "'");
				}
				
				if (overfids.size()>0&&overfids.contains(deliverorderID)) {//全部发货
					   ExecBySql("update t_ord_deliverorder set fmatched=1,fassembleQty=" + assembleamount + ",fstate=2,fdelivertype=2,fouted=1,foutorid='"+userid+"',fouttime=now(),foutQty=ifnull(foutQty,0)+"+Tninfo.getFamount()+" where fid ='" + deliverorderID + "'");
				}
		    }
		    
		    if(fullyfids.size()>0){//全部发货
			    String fidcls = "('" + fullyfids.toString().substring(1,fullyfids.toString().length()-1 ).replace(", ", "','") + "')";
			    ExecBySql("update t_ord_deliverorder set fmatched=1,fassembleQty=famount ,fdelivertype=2,fouted=1,fstate=2,foutorid='"+userid+"',fouttime=now(),foutQty= famount where fid in " + fidcls);
		    }
		    //发车 更新要货申请状态
		    
		    List allfids=new ArrayList();
		    allfids.addAll(batchfids);
		    allfids.addAll(fullyfids);
		    allfids.addAll(overfids);
		    String delivercls = "('" + allfids.toString().substring(1,allfids.toString().length()-1 ).replace(", ", "','") + "')";

			//反写要货申请状态为发车状态;
//			List<HashMap<String,Object>> delivers = QueryBySql("select sum(ifnull(d.foutQty,0)) outQty,d.fdeliversId,ifnull(fcount,0) fcount from t_ord_deliverorder d left join (select count(1) fcount,fdeliversId from t_ord_deliverorder where fouted = 0 group by fdeliversId) a on a.fdeliversId=d.fdeliversId where d.fid in " + delivercls + " group by d.fdeliversId ");
//			BigDecimal foutQty = new BigDecimal(0);
//			for(int j=0;j<delivers.size();j++){
//				if(delivers.get(j).get("fcount").toString().equals("0")){
//					deliverapplyDao.updateDeliverapplyStateByDelivers(delivers.get(j).get("fdeliversId").toString(), 6);
//				}else {
//					deliverapplyDao.updateDeliverapplyStateByDelivers(delivers.get(j).get("fdeliversId").toString(), 5);
//				}
//				
//				//cd 20141122 修改反写要货申请新增的实发数量
//				foutQty = new BigDecimal(delivers.get(j).get("outQty").toString());
//				List<HashMap<String,Object>> deliverratios = QueryBySql("select fdeliverappid,fdeliverapplynum from t_ord_deliverratio where fdeliverid='" + delivers.get(j).get("fdeliversId").toString() + "' ");
//				for(int k=0;k<deliverratios.size();k++){
//					foutQty = foutQty.multiply(new BigDecimal(deliverratios.get(k).get("fdeliverapplynum").toString()));
//					ExecBySql("update t_ord_deliverapply set foutQty='"+foutQty+"' where fid = '"+deliverratios.get(k).get("fdeliverappid").toString()+"'");
//				}
//			}
		    //增加纸板发货功能
//		    List<HashMap<String,Object>> delivers = QueryBySql("select d.fboxtype,sum(ifnull(d.foutQty,0)) outQty,d.fdeliversId,ifnull(fcount,0) fcount from t_ord_deliverorder d left join (select count(1) fcount,fdeliversId from t_ord_deliverorder where fouted = 0 group by fdeliversId) a on a.fdeliversId=d.fdeliversId where d.fid in " + delivercls + " group by d.fdeliversId ");
		    List<HashMap<String,Object>> delivers = QueryBySql("select d.fboxtype,sum(ifnull(d.foutQty,0)) outQty,d.fdeliversId,MIN(d.fouted) fouted from t_ord_deliverorder d  where d.fid in " + delivercls + " group by d.fdeliversId ");
			BigDecimal foutQty = new BigDecimal(0);
			for(int j=0;j<delivers.size();j++){
				if("1".equals(delivers.get(j).get("fboxtype").toString())){//纸板
					if(((HashMap)delivers.get(j)).get("fouted").toString().equals("1")){
						
						deliverapplyDao.updateDeliverapplyState(delivers.get(j).get("fdeliversId").toString(), 6,true);
					}else {
						deliverapplyDao.updateDeliverapplyState(delivers.get(j).get("fdeliversId").toString(), 5,true);
					}
					//修改反写要货申请新增的实发数量
					ExecBySql("update t_ord_deliverapply set fouttime=now(), foutQty='"+delivers.get(j).get("outQty")+"' where fid = '"+delivers.get(j).get("fdeliversId")+"'");
				}else{//纸箱
					if(((HashMap)delivers.get(j)).get("fouted").toString().equals("1")){
						
						deliverapplyDao.updateDeliverapplyStateByDelivers(delivers.get(j).get("fdeliversId").toString(), 6);
					}else {
						deliverapplyDao.updateDeliverapplyStateByDelivers(delivers.get(j).get("fdeliversId").toString(), 5);
					}
					//cd 20141122 修改反写要货申请新增的实发数量
					foutQty = new BigDecimal(delivers.get(j).get("outQty").toString());
					List<HashMap<String,Object>> deliverratios = QueryBySql("select fdeliverappid,fdeliverapplynum from t_ord_deliverratio where fdeliverid='" + delivers.get(j).get("fdeliversId").toString() + "' ");
					for(int k=0;k<deliverratios.size();k++){
						foutQty = foutQty.multiply(new BigDecimal(deliverratios.get(k).get("fdeliverapplynum").toString()));
						ExecBySql("update t_ord_deliverapply set foutQty='"+foutQty+"' where fid = '"+deliverratios.get(k).get("fdeliverappid").toString()+"'");
					}
				}
			}
			
			for( int i=0;i<tlist.size();i++)
			{
				Truckassemble tinfo=tlist.get(i);
				tinfo.setFnumber(ServerContext.getNumberHelper().getNumber("t_tra_truckassemble", "ST", 4, false));
				this.saveOrUpdate(tinfo);
			}
			for( int i=0;i<slist.size();i++)
			{
				Saledeliver sinfo=slist.get(i);
				sinfo.setFnumber(ServerContext.getNumberHelper().getNumber("t_tra_saledeliver", "DJ", 4, false));
				this.saveOrUpdate(sinfo);
			}
		    
		    params.put("success", Boolean.valueOf(true));
		    return params;
		  }
	
	@Override
	public void ExecDoPlaceCarboardOrder(List<Deliverapply> deliverapplys,
			List<ProductPlan> pps, List<Deliverorder> deliverOrders, List<Storebalance> storebalances, List<Usedstorebalance> usedstorebalances) {
		// TODO Auto-generated method stub
		
		for (int i = 0; i < deliverapplys.size(); i++) {
			
			saveOrUpdate(deliverapplys.get(i));
			saveOrUpdate(pps.get(i));
			saveOrUpdate(deliverOrders.get(i));
			
			saveOrUpdate(storebalances.get(i));
			saveOrUpdate(usedstorebalances.get(i));
						
		}
		
	}

	@Override
	public void ExecUpdateState(List<String> fidsA, int fstate,
			String userid, List<String> fidsA2) {
		// TODO Auto-generated method stub

		
		for (String fid : fidsA) {
			
			Deliverapply da = (Deliverapply) Query(Deliverapply.class, fid);
			
			da.setFstate(fstate);
			
			if (fstate == 1) {
				
				da.setFreceiptTime(new Date());
				da.setFrecipient(userid);
				
			}else {
				
				da.setFreceiptTime(null);
				da.setFrecipient("");
				da.setFplanNumber("");
				
				
			}
			
			saveOrUpdate(da);
			
		}
		
		//当是下单后未确认而 取消分配时，删除生成制造商订单和配送单
		if (fidsA2 != null && fstate == 0) {
						
			String sql;
			for (String id : fidsA2) {
				
				sql = "select fid from t_ord_productplan where fdeliverapplyid ='"+id+"'";
				String productPlanId = baseSysDao.getStringValue(sql, "fid");
				this.ExecBySql("delete from t_inv_storebalance where fproductplanid ='"+productPlanId+"'");
				sql = "select fid from t_ord_deliverorder where fdeliversId ='"+id+"'";
				String deliverOrderId = baseSysDao.getStringValue(sql, "fid");
				this.ExecBySql("delete from t_inv_usedstorebalance where fdeliverorderid ='"+deliverOrderId+"'");
				
				String hql = String.format(" delete from ProductPlan where fdeliverapplyid = '%s' ",id) ;
				
				String hql2 = String.format(" delete from Deliverorder where fdeliversId = '%s' ", id);
				
				ExecByHql(hql);
				ExecByHql(hql2);
				
			}
			
		}
		
	}

	@Override
	public void ExecCloseDeliverorder(String fidcls) throws Exception {
		// TODO Auto-generated method stub
		String sql = "update t_ord_deliverorder set fclosed=1 where fclosed=0 and fid in "
				+ fidcls;
		this.ExecBySql(sql);
		sql="update t_inv_usedstorebalance set fusedqty=0 where fdeliverorderid in "+fidcls;
		this.ExecBySql(sql);
		
	}

	/**
	 * 纸板订单自动接收的方法
	 */
	@Override
	public void ExecReceiveBoardOrders() {
		if(isReceiving){
			return;
		}
		isReceiving = true;
		String hql = "from Deliverapply where fboxtype=1 and fstate=0 and TIMESTAMPDIFF(MINUTE,fcreatetime,NOW())>5";
		try{
			List<Deliverapply> list = this.QueryByHql(hql);
			String userid = "3c3c9f29-64b9-11e4-bdb9-00ff6b42e1e5"; // administrator
			for(Deliverapply deliverapply: list){
				deliverapply.setFreceiptTime(new Date());
				deliverapply.setFrecipient(userid);
				deliverapply.setFstate(3);
				ProductPlan productPlan = getProductPlan(deliverapply,userid);
				deliverapply.setFplanNumber(productPlan.getFnumber());
				deliverapply.setFplanid(productPlan.getFid());
				Deliverorder deliverorder = getDeliverOrder(deliverapply,userid);
				deliverorder.setFplanNumber(productPlan.getFnumber());
				Storebalance storebalance = getStoreBalance(deliverapply,userid);
				Usedstorebalance usedstorebalance = getUsedStoreBalance(deliverapply,userid);
				usedstorebalance.setFdeliverorderid(deliverorder.getFid());
				usedstorebalance.setFstorebalanceid(storebalance.getFid());
				usedstorebalance.setFusedqty(deliverorder.getFamount());
				this.saveOrUpdate(deliverapply);
				this.saveOrUpdate(productPlan);
				this.saveOrUpdate(deliverorder);
				this.saveOrUpdate(storebalance);
				this.saveOrUpdate(usedstorebalance);
			}
		}finally{
			isReceiving = false;
		}
	}
	
	@Override
	public void ExecReceiveBoardOrders(String fidcls, String userid) {
		if(isReceiving){
			throw new DJException("订单正在自动接收，请刷新查看订单状态！");
		}
		isReceiving = true;
		try{
				String[] fids = fidcls.split(",");
				Deliverapply deliverapply;
				for(String fid: fids){
					deliverapply = (Deliverapply) this.Query(Deliverapply.class, fid);
					if(deliverapply==null || deliverapply.getFstate()!=0){
						continue;
					}
					deliverapply.setFreceiptTime(new Date());
					deliverapply.setFrecipient(userid);
					deliverapply.setFstate(3);
					ProductPlan productPlan = getProductPlan(deliverapply,userid);
					deliverapply.setFplanNumber(productPlan.getFnumber());
					deliverapply.setFplanid(productPlan.getFid());
					Deliverorder deliverorder = getDeliverOrder(deliverapply,userid);
					deliverorder.setFplanNumber(productPlan.getFnumber());
					Storebalance storebalance = getStoreBalance(deliverapply,userid);
					Usedstorebalance usedstorebalance = getUsedStoreBalance(deliverapply,userid);
					usedstorebalance.setFdeliverorderid(deliverorder.getFid());
					usedstorebalance.setFstorebalanceid(storebalance.getFid());
					usedstorebalance.setFusedqty(deliverorder.getFamount());
					this.saveOrUpdate(deliverapply);
					this.saveOrUpdate(productPlan);
					this.saveOrUpdate(deliverorder);
					this.saveOrUpdate(storebalance);
					this.saveOrUpdate(usedstorebalance);
				}
		}finally{
			isReceiving = false;
		}
	}

	private Usedstorebalance getUsedStoreBalance(Deliverapply deliverapply,
			String userid) {
		Usedstorebalance usedstorebalance = new Usedstorebalance(CreateUUid());
		usedstorebalance.setFproductid(deliverapply.getFmaterialfid());
		usedstorebalance.setFratio(1);
		usedstorebalance.setFtype(1);
		return usedstorebalance;
	}

	private Storebalance getStoreBalance(Deliverapply deliverapply,
			String userid) {
		Storebalance storebalance = new Storebalance();
		storebalance.setFcustomerid(deliverapply.getFcustomerid());
		storebalance.setFdescription(deliverapply.getFdescription());
		storebalance.setFproductId(deliverapply.getFmaterialfid());
		storebalance.setFproductplanId(deliverapply.getFplanid());
		storebalance.setFsupplierID(deliverapply.getFsupplierid());
		storebalance.setFid(this.CreateUUid());
		storebalance.setFinqty(0);
		storebalance.setFallotqty(0);
		storebalance.setFbalanceqty(0);
		storebalance.setFcreatetime(new Date());
		storebalance.setFcreatorid(userid);
		storebalance.setFoutqty(0);
		storebalance.setFtype(1);
		storebalance.setFupdatetime(new Date());
		storebalance.setFupdateuserid(userid);
		String hql = " select fwarehouseid, fwarehousesiteid from Supplier where fid = '"+deliverapply.getFsupplierid()+"'";
		List<Object[]> fwarehouseidAndFwarehousesiteids = this.QueryByHql(hql);
		storebalance.setFwarehouseId((String)(fwarehouseidAndFwarehousesiteids.get(0))[0]);
		storebalance.setFwarehouseSiteId((String)(fwarehouseidAndFwarehousesiteids.get(0))[1]);
		return storebalance;
	}

	private Deliverorder getDeliverOrder(Deliverapply deliverapply,
			String userid) {
		Deliverorder deliverorder = new Deliverorder();
		deliverorder.setFid(this.CreateUUid());
		deliverorder.setFnumber(ServerContext.getNumberHelper().getNumber(
				"t_ord_deliverorder", "D", 4, false));
		deliverorder.setFaddress(deliverapply.getFaddress());
		deliverorder.setFaddressid(deliverapply.getFaddressid());
		deliverorder.setFamount(deliverapply.getFamountpiece());
		deliverorder.setFarrivetime(deliverapply.getFarrivetime());
		deliverorder.setFcusfid(deliverapply.getFcusfid());
		deliverorder.setFcusproductid(deliverapply.getFmaterialfid());
		deliverorder.setFcustomerid(deliverapply.getFcustomerid());
		deliverorder.setFdeliversId(deliverapply.getFid());
		deliverorder.setFdescription(deliverapply.getFdescription());
		deliverorder.setFeasdeliverid(deliverapply.getFeasdeliverid());
		deliverorder.setFimportEas(deliverapply.getFimportEas());
		deliverorder.setFimportEastime(deliverapply.getFimportEastime());
		deliverorder.setFimportEasuserid(deliverapply.getFimportEasuserid());
		deliverorder.setFistoPlan(deliverapply.getFistoPlan());
		deliverorder.setFlinkman(deliverapply.getFlinkman());
		deliverorder.setFlinkphone(deliverapply.getFlinkphone());
		deliverorder.setFordered(deliverapply.getFordered());
		deliverorder.setForderentryid(deliverapply.getForderentryid());
		deliverorder.setFordermanid(deliverapply.getFordermanid());
		deliverorder.setFordertime(deliverapply.getFordertime());
		deliverorder.setFoutorid(deliverapply.getFoutorid());
		deliverorder.setFoutQty(deliverapply.getFoutQty());
		deliverorder.setFouttime(deliverapply.getFouttime());
		deliverorder.setFplanid(deliverapply.getFplanid());
		deliverorder.setFplanNumber(deliverapply.getFplanNumber());
		deliverorder.setFplanTime(deliverapply.getFplanTime());
		deliverorder.setFproductid(deliverapply.getFmaterialfid());
		deliverorder.setFsupplierId(deliverapply.getFsupplierid());
		deliverorder.setFtraitid(deliverapply.getFtraitid());
		deliverorder.setFtype(deliverapply.getFtype());
		deliverorder.setFordernumber(deliverapply.getFplanNumber());
		deliverorder.setFdescription(deliverapply.getFdescription());
		deliverorder.setFalloted(0);
		deliverorder.setFcreatetime(new Date());
		deliverorder.setFcreatorid(userid);
		deliverorder.setFouted(0);
		deliverorder.setFupdatetime(new Date());
		deliverorder.setFupdateuserid(userid);
		deliverorder.setFboxtype((byte) 1);
		deliverorder.setFaudited(1);
		deliverorder.setFauditorid(userid);
		deliverorder.setFaudittime(new Date());
		return deliverorder;
	}

	private ProductPlan getProductPlan(Deliverapply deliverapply, String userid) {
		ProductPlan productPlan = new ProductPlan();
		productPlan.setFsupplierid(deliverapply.getFsupplierid());
		productPlan.setFproductdefid(deliverapply.getFmaterialfid());
		productPlan.setFcustomerid(deliverapply.getFcustomerid());
		productPlan.setFamount(deliverapply.getFamountpiece());
		productPlan.setFarrivetime(deliverapply.getFarrivetime());
		productPlan.setFdeliverapplyid(deliverapply.getFid());
		productPlan.setFdescription(deliverapply.getFdescription());
		productPlan.setFid(this.CreateUUid());
		productPlan.setFnumber(ServerContext.getNumberHelper().getNumber(
				"t_ord_productplan", "P", 4, false));
		productPlan.setFboxtype(1);
		productPlan.setFtype(1);
		productPlan.setFstate(0);
		productPlan.setFcloseed(0);
		productPlan.setFaffirmed(0);
		productPlan.setFaudited(1);
		productPlan.setFaudittime(new Date());
		productPlan.setFauditorid(userid);
		productPlan.setFcreatorid(userid);
		productPlan.setFcreatetime(new Date());
		return productPlan;
	}

	@Override
	public void ExecUnReceiveBoardOrders(String fidcls) {
		String[] fids = fidcls.split(",");
		Deliverapply deliverapply;
		for(String fid: fids){
			deliverapply = (Deliverapply) this.Query(Deliverapply.class, fid);
			if(deliverapply==null || deliverapply.getFstate()!=3){
				continue;
			}
			String sql = "delete from t_ord_productplan where fdeliverapplyid = '"+fid+"'";
			this.ExecBySql(sql);
			sql = "select fid from t_ord_deliverorder where fdeliversId ='"+fid+"'";
			String deliverOrderId = baseSysDao.getStringValue(sql, "fid");
			sql = "delete from t_ord_deliverorder where fdeliversId = '"+fid+"'";
			this.ExecBySql(sql);
			sql = "delete from t_inv_storebalance where fproductplanid = '"+deliverapply.getFplanid()+"'";
			this.ExecBySql(sql);
			sql = "delete from t_inv_usedstorebalance where fdeliverorderid = '"+deliverOrderId+"'";
			this.ExecBySql(sql);
			deliverapply.setFreceiptTime(null);
			deliverapply.setFrecipient("");
			deliverapply.setFstate(0);
			deliverapply.setFplanNumber("");
			deliverapply.setFplanid("");
			this.saveOrUpdate(deliverapply);
		}
	}
	@Override
	public String ExecBatchCreatTruckassembleByCondition(HttpServletRequest request, HttpServletResponse reponse) throws Exception {
		// TODO Auto-generated method stub
		
		DeliverorderController deliverorderController = (DeliverorderController) SpringContextUtils.getBean("deliverorderController");
		String result = "";
		List list = new ArrayList();
		if (Integer.parseInt((String) syscfgDao.ExecGainCfgByFkey(request, "autoDepart").getData().get(0).get("fvalue")) == 1) {
			//自运，发车 合为一个方法
			result = deliverorderController.doBatchCreatTruckassemble(request, reponse,list);
			Batchdepart(request);
//			result=deliverorderController.doBatchCreatTruckassembleNew(request, reponse);
			
		}else {
			//自运
			result = deliverorderController.doBatchCreatTruckassemble(request, reponse,list);
			
		}
		return result;
	}
	
	//批量发车
	public void Batchdepart(HttpServletRequest request) throws Exception {
		List<Deliverorder> dlist = (List<Deliverorder>)request.getAttribute("Deliverorder");
		String fids= "('";
		for(int i = 0;i<dlist.size();i++){
			fids += dlist.get(i).getFid();
			if(i<dlist.size()-1){
				fids += "','";
			}
		}
		fids +="')";
		TruckassembleController truckassembleController = (TruckassembleController) SpringContextUtils.getBean("truckassembleController");
		
		String hql = " select distinct t.fparentid fparentid from t_tra_Truckassembleentry t left join t_tra_truckassemble tk on tk.fid=t.fparentid where tk.faudited=0 and t.fdeliverorderid in  " + fids;
		
		List<HashMap<String,String>> truckassembleIds = QueryBySql(hql);
		for (HashMap<String,String> id : truckassembleIds) {
			
			truckassembleController.actionAuditById(request, id.get("fparentid"));
			
		}
	}
	
	
	
	public HashMap<String, Object> ExecCreateTruckassembleNew(HashMap<String, Object> params) throws Exception {

		List<HashMap<String, Object>> deliverordercls = (List<HashMap<String, Object>>) params.get("deliverordercls");// 配送单集合
		HashMap<String, Integer> deliverordermap = (HashMap<String, Integer>) params.get("deleiverordermap");// 批量选择配送
		String userid = params.get("userid").toString();
		String fids = params.get("fids").toString();
		String sql = "", saledeliverID = "";// 当前地址 出库单id
		Truckassemble Tinfo = null;
		int fseq = 1, seq = 1,assembleamount=0;// fseq 提货单分录 seq出库单分录
		List<Saledeliver> slist = new ArrayList<Saledeliver>();// 出提单
	    List fullyfids = new ArrayList();//全部发货的配送
		Supplier sp = null;
		for (HashMap<String, Object> deliverorderinfo : deliverordercls) {
			String deliverorderID = deliverorderinfo.get("fid").toString();
			if (sp == null) {// 判断一次
				String supplierid = StringUtils.isEmpty(deliverorderinfo.get("fsupplierId")) ? "" : (String) deliverorderinfo.get("fsupplierId");
				sp = (Supplier)Query(Supplier.class,supplierid);
				if (sp == null) throw new DJException("制造商不存在！");
			}
			String addressid = deliverorderinfo.get("faddressid").toString();
			String customerid = deliverorderinfo.get("fcustomerid").toString();
			String flinkman = (String) deliverorderinfo.get("flinkman");
			String flinkphone = (String) deliverorderinfo.get("flinkphone");
			String fproductspec = "";
			String fmaterial = "";
			String fproductid = (String) deliverorderinfo.get("fproductid");
//			BigDecimal farea = new BigDecimal(0);//面积
			
			Productdef pdtinfo = (Productdef) Query(Productdef.class,fproductid);
			if (pdtinfo != null) {
				fproductspec = (pdtinfo.getFboxlength() == null ? 0 : pdtinfo.getFboxlength())+ "×"+ (pdtinfo.getFboxwidth() == null ? 0 : pdtinfo.getFboxwidth())+ "×"+ (pdtinfo.getFboxheight() == null ? 0 : pdtinfo.getFboxheight());
				String sqls = "select CONCAT_WS('x',a.`FBOXLENGTH`,a.`FBOXWIDTH`,a.`FBOXHEIGHT`) fproductspec,CONCAT_WS('x',a.`fmateriallength`,a.`fmaterialwidth`) fmaterial  from t_ord_deliverorder d inner join t_ord_deliverapply a on d.fdeliversID=a.fid where d.fboxtype=1 and d.fid='"+ deliverorderID + "'";
				List<HashMap<String, Object>> list = this.QueryBySql(sqls);
				if (list.size() > 0) {
					fproductspec = (String) list.get(0).get("fproductspec");// 纸板规格
					fmaterial = (String) list.get(0).get("fmaterial");// 下料规格
				}
//				if (!StringUtils.isEmpty(pdtinfo.getFarea())) {
//					farea = pdtinfo.getFarea();
//				}

			}
			//计算数量
			BigDecimal famount = new BigDecimal(0);
			int amount = new Integer(deliverorderinfo.get("famount").toString());// 配送数量
			int fassembleqty = StringUtils.isEmpty(deliverorderinfo.get("fassembleQty")) ? 0 : new Integer(deliverorderinfo.get("fassembleQty").toString());//提货数量
			if (deliverordermap.containsKey(deliverorderID)) {
				famount = new BigDecimal(deliverordermap.get(deliverorderID));//配送数量
				assembleamount = fassembleqty+ deliverordermap.get(deliverorderID);// 提货数量+发车数量
				if (amount < assembleamount) {
					throw new DJException("配送单实配数量之和不能大于配送数量！");
				} else if (amount == assembleamount)// /批量更新配送订单
				{
					 fullyfids.add(deliverorderID);
				}
			} else {
				famount = new BigDecimal((amount - fassembleqty));// 批量送 则  // 当前数量=数量-提货数量
			}
//			farea = farea.multiply(famount);
			
			
			if (Tinfo == null) {
				Tinfo = new Truckassemble();
				Tinfo.setFid(this.CreateUUid());
				Tinfo.setFcreatorid(userid);
				Tinfo.setFcreatetime(new Date());
				Tinfo.setFbizdate(new Date());
				Tinfo.setFlastupdatetime(new Date());
				Tinfo.setFlastupdateuserid(userid);
				Tinfo.setFaudited(1);// 发车后动作
				Tinfo.setFsupplier(sp.getFid());
				Tinfo.setFtype(0);
				// this.saveOrUpdate(Tinfo);//最后新增
			}
			Truckassembleentry	Tninfo = new Truckassembleentry();
			Tninfo.setFparentid(Tinfo.getFid());
			Tninfo.setFamount(famount);
			Tninfo.setFdeliverorderid(deliverorderID);
			// 2014-2-23 自运逻辑改为已提货,且直接生成出库单;
			Tninfo.setFdeliveryed(1);
			Tninfo.setFdeliveryaddress(sp.getFaddress());
			Tninfo.setFdelivery(sp.getFname());
			Tninfo.setFdeliveryphone(sp.getFtel());
			Tninfo.setFid( this.CreateUUid());
			Tninfo.setFproductid(fproductid);
			Tninfo.setFproductspec(fproductspec);
			Tninfo.setFreceiveaddress(addressid);
			Tninfo.setFreceiver(flinkman);
			Tninfo.setFreceiverphone(flinkphone);
			Tninfo.setFcustomerid(customerid);
			Tninfo.setFremark(deliverorderinfo.get("fdescription") != null ? (String) deliverorderinfo.get("fdescription") : "");
			Tninfo.setFsaleorderid(deliverorderinfo.get("fplanid") != null ? (String) deliverorderinfo.get("fplanid") : "");
			Tninfo.setFseq(fseq);
			fseq = fseq + 1;
			Tninfo.setFsupplierid(sp.getFid());
			Tninfo.setFouted(1);// 发车
			Tninfo.setFoutor(userid);// 发车
			Tninfo.setFouttime(new Date());// 发车
			this.save(Tninfo);

			// 2014-2-23 配送单自运发货时增加生成出库单;

			if(slist.size()==0||!slist.get(slist.size()-1).getFaddressid().equals(addressid)||!slist.get(slist.size()-1).getFcustomerid().equals(customerid))
			{
				Saledeliver sdinfo = new Saledeliver();
				saledeliverID = this.CreateUUid();
				sdinfo.setFid(saledeliverID);
				sdinfo.setFcreatorid(userid);
				sdinfo.setFcreatetime(new Date());
				sdinfo.setFbizdate(new Date());
				// sdinfo.setFnumber(ServerContext.getNumberHelper().getNumber("t_tra_saledeliver", "DJ",4,false));
				sdinfo.setFlastupdatetime(new Date());
				sdinfo.setFlastupdateuserid(userid);
				sdinfo.setFtruckid(Tinfo.getFtruckid());
				sdinfo.setFaddressid(addressid);
				sdinfo.setFcustomerid(customerid);
				sdinfo.setFauditorid(userid);// 发车
				sdinfo.setFauditdate(new Date());// 发车
				sdinfo.setFaudited(Integer.valueOf(1));// 发车
				sdinfo.setFassembleid(Tinfo.getFid());
				sdinfo.setFtype(Tinfo.getFtype());
				sdinfo.setFsupplier(sp.getFid());
				seq = 1;
				// this.saveOrUpdate(sdinfo); //最后新增提交
				slist.add(sdinfo);
			}

			Saledeliverentry sdninfo = new Saledeliverentry();
			sdninfo.setFparentid(saledeliverID);
			sdninfo.setFamount(famount);
			sdninfo.setFrealamount(sdninfo.getFamount());
			sdninfo.setFdeliverorderid(deliverorderID);
			sdninfo.setFdelivery(sp.getFname());
			sdninfo.setFdeliveryaddress(sp.getFaddress());
			sdninfo.setFdeliveryphone(sp.getFtel());
			sdninfo.setFid(this.CreateUUid());
			sdninfo.setFproductid(fproductid);
			sdninfo.setFproductspec(fproductspec);
			sdninfo.setFmaterialspec(fmaterial);
			sdninfo.setFreceiveaddress(addressid);
			sdninfo.setFreceiver(flinkman);
			sdninfo.setFreceiverphone(flinkphone);
			sdninfo.setFremark(null);
			sdninfo.setFsaleorderid(Tninfo.getFsaleorderid());
			sdninfo.setFseq(seq++);
			sdninfo.setFsupplierid(sp.getFid());
			sdninfo.setFassembleentryid(Tninfo.getFid());
			sdninfo.setFisreceipts(0);
			this.save(sdninfo);

			// 发车、*
			// 配送类型为补单类型，不扣库存
			if ("1".equals(deliverorderinfo.get("ftype").toString())) {

				continue;
			}

			int famountint = famount.divide(new BigDecimal(1), 0,BigDecimal.ROUND_UP).intValue();// 配送数量的整数
			// 扣除库存先根据配送ID查找占用量记录表;
			List<Usedstorebalance> usedstorebalancelist = this.QueryByHql(" FROM Usedstorebalance where fdeliverorderid = '"+ deliverorderID + "'");
			if (usedstorebalancelist.size() == 0) {
				throw new DJException("占用库存表数据异常！");
			}
			for (Usedstorebalance usedbalance:usedstorebalancelist) {
				int usedqty = usedbalance.getFusedqty() == null ? 0:usedbalance.getFusedqty();
				int ratio = usedbalance.getFratio() == null ? 1 : usedbalance.getFratio();
				Storebalance sbinfo = (Storebalance) Query(Storebalance.class,usedbalance.getFstorebalanceid());
				if (sbinfo.getFbalanceqty() - famountint * ratio < 0) {
					throw new DJException("生成的提货单中,配送单号:"+ this.Query(deliverorderID).getFnumber()+ "的产品库存数量不足，不能发车！");
				}
				usedbalance.setFusedqty(usedqty - famountint * ratio);
				this.Update(usedbalance);

				// 修改库存
				sbinfo.setFbalanceqty(sbinfo.getFbalanceqty() - famountint* ratio);
				sbinfo.setFoutqty(sbinfo.getFoutqty() + famountint * ratio);
				this.Update(sbinfo);
				// 生成出库记录
				Outwarehouse Outwarehouseinfo = new Outwarehouse();//编码由数据库自动生成
				Outwarehouseinfo.setForderentryid(sbinfo.getForderentryid());
				Outwarehouseinfo.setFid(CreateUUid());
				Outwarehouseinfo.setFproductplanid(sbinfo.getFproductplanId());
				Outwarehouseinfo.setFcreatorid(userid);
				Outwarehouseinfo.setFcreatetime(new Date());
				Outwarehouseinfo.setFlastupdatetime(null);
				Outwarehouseinfo.setFlastupdateuserid(null);
				Outwarehouseinfo.setFoutqty(new BigDecimal(famountint * ratio));
				Outwarehouseinfo.setFwarehouseid(sp.getFwarehouseid());
				Outwarehouseinfo.setFwarehousesiteid(sp.getFwarehousesiteid());
				Outwarehouseinfo.setFproductid(fproductid);
				Outwarehouseinfo.setFremak(null);
				Outwarehouseinfo.setFsaleorderid(sbinfo.getFsaleorderid());
				Outwarehouseinfo.setFaudited(1);
				Outwarehouseinfo.setFauditorid(userid);
				Outwarehouseinfo.setFaudittime(new Date());
				Outwarehouseinfo.setFsupplierid(sp.getFid());
				this.save(Outwarehouseinfo);

				// 更新生产订单
				if (!StringUtils.isEmpty(sbinfo.getForderentryid())) {
					sql = "update t_ord_saleorder set fstoreqty = fstoreqty - "
							+ famountint * ratio
							+ ",fstockoutqty = fstockoutqty + " + famountint
							* ratio + " where fid='"
							+ sbinfo.getForderentryid() + "' ";
					ExecBySql(sql);
				}
				// 更新制造商订单
				if (!StringUtils.isEmpty(sbinfo.getFproductplanId())) {
					sql = "update t_ord_productplan set fstoreqty = fstoreqty - "
							+ famountint
							* ratio
							+ ",fstockoutqty = fstockoutqty + "
							+ famountint
							* ratio
							+ " where fid='"
							+ sbinfo.getFproductplanId() + "' ";
					ExecBySql(sql);
				}
				
			}

			 if (!fullyfids.contains(deliverorderID))//部分发货 
				 ExecBySql("update t_ord_deliverorder set fmatched=0,fassembleQty=" + assembleamount + ",fdelivertype=1,fstate=1,fouttime=now(),foutQty=ifnull(foutQty,0)+"+Tninfo.getFamount()+" where fid ='" + deliverorderID + "'");
		}

		if (deliverordermap.isEmpty())// 全部发货，没有勾选指定行，则直接全部更新
		{
			ExecBySql("update t_ord_deliverorder set fmatched=1,fassembleQty=famount ,fdelivertype=1,fouted=1,fstate=2,foutorid='"+ userid+ "',fouttime=now(),foutQty= famount where fid in " + fids);
		}
		else if(fullyfids.size()>0){//全部发货
			    String fidcls = "('" + fullyfids.toString().substring(1,fullyfids.toString().length()-1 ).replace(", ", "','") + "')";
			    ExecBySql("update t_ord_deliverorder set fmatched=1,fassembleQty=famount ,fdelivertype=1,fouted=1,fstate=2,foutorid='"+userid+"',fouttime=now(),foutQty= famount where fid in " + fidcls);
		 }
		// 增加纸板发货功能
		List<HashMap<String, Object>> delivers = QueryBySql("select d.fboxtype,sum(ifnull(d.foutQty,0)) outQty,d.fdeliversId,MIN(d.fouted) fouted from t_ord_deliverorder d  where d.fid in "+ fids + " group by d.fdeliversId ");
		BigDecimal foutQty = new BigDecimal(0);
		for(HashMap<String, Object> deliversinfo :delivers){
			foutQty = new BigDecimal(deliversinfo.get("outQty").toString());
			int fstate="1".equals(deliversinfo.get("fouted").toString())?6:5;
			if ("1".equals(deliversinfo.get("fboxtype").toString())) {// 纸板 
					TruckcreateOrderStateInfo((String)deliversinfo.get("fdeliversId"),fstate,foutQty, true);// 修改反写要货申请新增的实发数量,状态
				
			} else {// 纸箱
				List<HashMap<String, Object>> deliverratios = QueryBySql("select fdeliverappid,fdeliverapplynum from t_ord_deliverratio where fdeliverid='"+ deliversinfo.get("fdeliversId") + "' ");
				for (HashMap<String, Object> deliverratioinfo:deliverratios) {
					foutQty = foutQty.multiply(new BigDecimal(deliverratioinfo.get("fdeliverapplynum").toString()));
					TruckcreateOrderStateInfo((String)deliverratioinfo.get("fdeliverappid"),fstate,foutQty,true);// 修改反写要货申请新增的实发数量,状态
				}
			}
		}

		if (Tinfo != null) {
			Tinfo.setFnumber(ServerContext.getNumberHelper().getNumber(
					"t_tra_truckassemble", "ST", 4, false));
			this.save(Tinfo);// 提交提货单
		}
		StringBuffer saledeliverid = new StringBuffer();
		for (int i = 0; i < slist.size(); i++)// 提交出库单
		{
			Saledeliver sinfo = slist.get(i);
			sinfo.setFnumber(ServerContext.getNumberHelper().getNumber(
					"t_tra_saledeliver", "DJ", 4, false));
			this.save(sinfo);
			if (i == slist.size() - 1) {
				saledeliverid.append(sinfo.getFid());
			} else {
				saledeliverid.append(sinfo.getFid() + ",");
			}
		}

		params.put("success", Boolean.valueOf(true));
		params.put("saledeliverid", saledeliverid);
		return params;

	}

		//更新要货申请数据，以及状态信息
		private void TruckcreateOrderStateInfo(String id,int state,BigDecimal foutqty ,boolean forward){
			ExecBySql("update t_ord_deliverapply set fouttime=now(), foutQty='"+foutqty+"', fstate="+state+" where fid = '"+id+"'");
			if(forward){
				OrderState obj = new OrderState();
				obj.setFid(this.CreateUUid());
				obj.setFdeliverapplyid(id);
				obj.setFstate(state);
				obj.setFcreatetime(new Date());
				this.save(obj);
			}
		}

		@Override
		public void ExecAuditImportEAS(String orderids,String userid) {
			// TODO Auto-generated method stub
			String sql = "update t_ord_deliverorder set faudited=1,fauditorid='"
					+ userid
					+ "',faudittime=now() where faudited=0 and fid in "
					+ orderids;
			int count=this.ExecBySql(sql);
			if(count>0){
			sql = "update t_ord_deliverorder set fimportEas=1,fdelivertype=2 where fsupplierId='39gW7X9mRcWoSwsNJhU12TfGffw=' and fimportEAS=0  and ftype <>1 and fassembleQty=0 and faudited=1 and  fid in "+ orderids;
			this.ExecBySql(sql);
			}
		}
		
		
		
	
}
