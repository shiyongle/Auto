package Com.Dao.order;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.hibernate.util.StringHelper;
import org.springframework.stereotype.Service;

import Com.Base.Dao.BaseDao;
import Com.Base.Dao.IBaseDao;
import Com.Base.Util.DJException;
import Com.Base.Util.JPushUtil;
import Com.Base.Util.ListResult;
import Com.Base.Util.ServerContext;
import Com.Base.Util.SpringContextUtils;
import Com.Base.Util.params;
import Com.Dao.System.IBaseSysDao;
import Com.Dao.System.ISimplemessageDao;
import Com.Dao.System.ISupplierDao;
import Com.Entity.Inv.Storebalance;
import Com.Entity.Inv.Usedstorebalance;
import Com.Entity.System.ProducePlan;
import Com.Entity.System.Productcycle;
import Com.Entity.System.Productdef;
import Com.Entity.System.Simplemessage;
import Com.Entity.System.Supplier;
import Com.Entity.System.Useronline;
import Com.Entity.order.CustBoardFormula;
import Com.Entity.order.CustBoardLabel;
import Com.Entity.order.Deliverapply;
import Com.Entity.order.Deliverorder;
import Com.Entity.order.Deliverratio;
import Com.Entity.order.Delivers;
import Com.Entity.order.OrderState;
import Com.Entity.order.ProductPlan;
import Com.Entity.order.Saleorder;
import Com.Entity.order.SchemeDesignEntry;

@Service("deliverapplyDao")
public class DeliverapplyDao extends BaseDao implements IDeliverapplyDao {

//	private IDeliversDao deliversDao = (IDeliversDao) SpringContextUtils.getBean("DeliversDao");
	@Resource
	IBaseSysDao baseSysDao;
	private ISimplemessageDao SimplemessageDao=(ISimplemessageDao) SpringContextUtils.getBean("simplemessageDao");

	@Override
	public HashMap<String, Object> ExecSave(Deliverapply dlv) {
		// TODO Auto-generated method stub
		HashMap<String, Object> params = new HashMap<>();
		if (dlv.getFid().isEmpty()) {
			dlv.setFid(this.CreateUUid());
		}
		this.saveOrUpdate(dlv);

		params.put("success", true);
		return params;
	}

	public void ExecSave(List<Deliverapply> vmi) {
		for (int i = 0; i < vmi.size(); i++) {
			Deliverapply order = vmi.get(i);
			this.saveOrUpdate(order);
		}
	}
	
	public void ExecSave(List<Deliverapply> list,List<SchemeDesignEntry> entryList) {
		for (int i = 0; i < list.size(); i++) {
			Deliverapply order = list.get(i);
			this.saveOrUpdate(order);
		}
		for(SchemeDesignEntry entry : entryList){
			this.saveOrUpdate(entry);
		}
	}

	@Override
	public Deliverapply Query(String fid) {
		// TODO Auto-generated method stub
		return (Deliverapply) this.getHibernateTemplate().get(
				Deliverapply.class, fid);
	}


	// @Override
	// public void ExecCanceltoCreate(List<Delivers> Deliverscls,
	// List<Deliverapply> Deliverapplycls) {
	// // TODO Auto-generated method stub
	// String
	// fapplayid="('"+Deliverscls.get(0).getFapplayid().toString().replace(",",
	// "','")+"')";
	// this.ExecBySql("delete from t_ord_delivers where  fapplayid like '%"+
	// Deliverapplycls.get(0).getFid()+"%'");
	// this.ExecBySql("update t_ord_deliverapply set fiscreate=0 where fid in "+
	// fapplayid);
	// }

	@Override
	public void ExecCanceltoCreate(List<Delivers> Deliverscls, String fapplayid) {
		// TODO Auto-generated method stub
		this.ExecBySql("update t_ord_deliverapply set fiscreate=0,fstate=0 where fid in "
				+ fapplayid);
		String sql = "delete from t_ord_orderstate where fdeliverapplyid in "+fapplayid+" and fstate >0";
		this.ExecBySql(sql);
		this.ExecBySql("delete from t_ord_deliverratio  where fdeliverappid in "
				+ fapplayid);
		for (int z = 0; z < Deliverscls.size(); z++) {
			this.Delete(Deliverscls.get(z));
		}
		// this.ExecBySql("delete from t_ord_delivers where "+deletehql);

	}

	@Override
	public void updateDeliverapplyStateByDeliverapply(String id, int state) {
		updateDeliverapplyStateByDeliverapply(id,state,true);
	}
	@Override
	public void updateDeliverapplyStateByDeliverapply(String id, int state,boolean bool) {
		Deliverapply delT = Query(id);
		delT.setFstate(state);
		if(state==1 || state==3){
			delT.setFiscreate(1);
		}else if(state==0){
			delT.setFiscreate(0);
		}
		ExecSave(delT);
		createOrderStateInfo(id, state,bool);
	}

	@Override
	public void updateDeliverapplyStateByDelivers(String deliversId, int state) {
		updateDeliverapplyStateByDelivers(deliversId,state,true);
	}
	@Override
	public void updateDeliverapplyStateByDelivers(String deliversId, int state,boolean bool) {
		String deliversIdT = "";
		List<HashMap<String,Object>> delivers = QueryBySql("select fapplayid from t_ord_delivers where fid = '" + deliversId +"'");
		if(delivers.size()>0){
			deliversIdT = delivers.get(0).get("fapplayid").toString();
			if (!deliversIdT.contains(",")) {
				
				updateDeliverapplyStateByDeliverapply(deliversIdT, state,bool);
			} else {
				
				String[] deliversIds = deliversIdT.split(",");
				
				for (int i = 0; i < deliversIds.length; i++) {
					
					updateDeliverapplyStateByDeliverapply(deliversIds[i], state,bool);
				}
				
			}
		}

	}
	@Override
	public void updateDeliverapplyState(String applyId, int state,boolean bool) {
		String deliversIdT = "";
//		List<HashMap<String,Object>> delivers = QueryBySql("select fapplayid from t_ord_delivers where fid = '" + deliversId +"'");
//		if(delivers.size()>0){
//			deliversIdT = delivers.get(0).get("fapplayid").toString();
			if (!applyId.contains(",")) {
				
				updateDeliverapplyStateByDeliverapply(applyId, state,bool);
			} else {
				
				String[] deliversIds = applyId.split(",");
				
				for (int i = 0; i < deliversIds.length; i++) {
					
					updateDeliverapplyStateByDeliverapply(deliversIds[i], state,bool);
				}
				
			}
//		}

	}
	/**
	 * 创建或删除
	 * @param fdeliverapplyid
	 * @param state
	 * @param forward
	 */
	private void createOrderStateInfo(String fdeliverapplyid,int state,boolean forward){
		if(forward == true){
			OrderState obj = new OrderState();
			obj.setFid(this.CreateUUid());
			obj.setFdeliverapplyid(fdeliverapplyid);
			obj.setFstate(state);
			obj.setFcreatetime(new Date());
			this.saveOrUpdate(obj);
		}else{
			if(state==3){	//新加入库状态4
				
			}
			String sql = "delete from t_ord_orderstate where fdeliverapplyid='"+fdeliverapplyid+"' and fstate >"+state;
			this.ExecBySql(sql);
		}
	}
	/**
	 * 生成要货管理
	 * @throws Exception 
	 */
	@Override
	public void ExecCreateToDelivers(List<HashMap<String, Object>> deliverresult,String userid,String fcustomerid) throws Exception {
		List<HashMap<String,Object>> list =new ArrayList<HashMap<String,Object>>();

		//删除其中地址为空的项
		removeEmptyAddress(deliverresult);
	
		while(deliverresult.size()>0){
	
			whatToCreate(deliverresult, deliverresult.get(0), userid, list, "");
			deliverresult.removeAll(list);// 删除已经生成要货的要货申请
			list.clear();

		}
//		String sql = "update t_ord_deliverapply set fiscreate=1,fstate=1  where fiscreate=0 and fcustomerid='" + fcustomerid+"' and ifnull(ftraitid,'')=''";
//		this.ExecBySql(sql);
//		sql = "update t_ord_deliverapply set fiscreate=1,fstate=3  where fiscreate=0 and fcustomerid='" + fcustomerid+"' and ifnull(ftraitid,'')!=''";
//		this.ExecBySql(sql);
	}
	
	private void removeEmptyAddress(List<HashMap<String, Object>> deliverresult) {

		List<HashMap<String, Object>> list = new ArrayList<>();
		for(HashMap<String, Object> map : deliverresult){
			// 核实下，要货申请生成要货管理时，地址为空的是否控制不能生成；
			if(StringUtils.isEmpty((String)map.get("faddressid"))){
				list.add(map);
			}
		}
		list.removeAll(list);
	}

	/***
	 *  根据当前传递的要货申请 去构造生成要货申请
	 * @param curmap  当前要货申请信息
	 * @param userid  用户信息
	 * @param list    存储当前生成的套装或普通的要货申请信息S
	 * @param productid 产品ID，如果套装不符合条件，用来过滤套装产品
	 * @throws Exception 异常
	 */
	private void  whatToCreate(List<HashMap<String, Object>> deliverresult,HashMap<String,Object> curmap,String userid,List<HashMap<String,Object>> list,String productid)throws Exception
	{
		
		
		
		if(curmap.get("ftraitid")!=null&&!"".equals(curmap.get("ftraitid"))){
			createTraitDelivers(curmap,userid);
			list.add(curmap);
			updateDeliverapplyStateByDeliverapply(curmap.get("fid").toString(),3);
			return;
		}
		String prid="";
	    int minnum=Integer.MAX_VALUE;//套装中子件产品最少的组件
		String sql=" select r.fid, r.fproductid,case when (f.fnewtype='4'or f.fnewtype='2')  then 2 else 1 end as fnewtype,count(e2.fid) counts from t_pdt_productrelationentry e "+
					" left join t_pdt_productrelation r on e.fparentid=r.fid "+
					" left join  t_pdt_productdef f on f.fid=r.fproductid  " +
					" left join t_pdt_productrelationentry e2 on e2.fparentid=r.fid "+
					" where ifnull(f.fishistory,0)=0 and ifnull(f.feffect,0)=1 and   e.fcustproductid='"+curmap.get("fcusproductid").toString()+"' and r.fcustomerid='"+curmap.get("fcustomerid").toString()+"'";
					if(productid!=null&&!("").equals(productid))
					{
						sql+=" and r.fproductid  not in ("+productid +")";
					}
				
				sql+="group by r.fid order by fnewtype desc,counts desc ";
		List<HashMap<String,Object>> ptypelist= this.QueryBySql(sql);//查询与该客户产品相关的产品类型，产品等
		if(ptypelist.size()>0)
		{
			//查询当前类型的产品的各个客户产品的数量比例
			 sql="select fproductid,fcustproductid,famount,ftype from (select r.fproductid,e.fcustproductid,e.famount,1 ftype  from t_pdt_productrelationentry e left join t_pdt_productrelation r on r.fid=e.fparentid where  r.fid='"+ptypelist.get(0).get("fid").toString()+"' and fcustproductid='"+curmap.get("fcusproductid").toString()+
					"' union select r.fproductid,e.fcustproductid,e.famount , 2 ftype from t_pdt_productrelationentry e left join t_pdt_productrelation r on r.fid=e.fparentid "+
					" where r.fid='"+ptypelist.get(0).get("fid").toString()+"' and fcustproductid <> '"+curmap.get("fcusproductid").toString()+"') tt order by ftype asc";
			List<HashMap<String,Object>> presult=this.QueryBySql(sql);
			String pid=presult.get(0).get("fproductid").toString();//产品ID
			int value=(Integer)presult.get(0).get("famount");//数量比例
			int totalnum=(Integer)curmap.get("famount");//当前要货申请的数量
			minnum=totalnum/value;
			curmap.put("ratio",value );//存放产品与该客户产品的比例
			list.add(curmap);
			if((Integer)ptypelist.get(0).get("fnewtype")==2)
			{
				
				if(presult.size()==1)
				{
					ExecCreatedelivers(presult.get(0).get("fproductid").toString(),list,userid,minnum);
					
				}else{
				for(int i=1;i<presult.size();i++)//循环该套装产品的各个客户产品关联信息
				{
					
					for(int j=1;j<deliverresult.size();j++)//循环该deliverresult要货申请的信息寻找与之能组成套装的要货申请
					{
						HashMap smap=deliverresult.get(j);
						String detail=smap.get("fdetail").toString();
						String custid=presult.get(i).get("fcustproductid").toString();//客户产品ID
						if(custid.equals(smap.get("fcusproductid").toString())){
							if(curmap.get("fdetail").toString().equals(detail)){//判断配送时间，客户，地址，类型都一致
							
							int cfmount=(Integer)presult.get(i).get("famount");//比例
							int tfmount=(Integer)smap.get("famount");//符合的要货申请的数量
							
							if(tfmount/cfmount==minnum)//最小数量相等则存入list中，存放比例
							{
								smap.put("ratio", cfmount);
								list.add(smap);
								break;
							}
						}
						}
					}
					if(list.size()<i+1)//当前产品的客户产品数量与要货申请符合数量比较，小于则不能生成
					{
						
						list.clear();
						minnum=Integer.MAX_VALUE;
						if(productid!=null&&!("").equals(productid))
						{
							prid=productid+",";
						}
						prid+="'"+presult.get(i).get("fproductid").toString()+"'";
						whatToCreate(deliverresult,curmap,userid,list,prid);//产品套装中的某个客户产品没有申请信息，则跳过，取下一个信息
						break;
						
					}
					if(list.size()==presult.size())//产品套装对应的每个客户产品都有申请信息生成要货管理
					{
						ExecCreatedelivers(presult.get(0).get("fproductid").toString(),list,userid,minnum);
					}
					
				}
				}
			}else if((Integer)ptypelist.get(0).get("fnewtype")==1)
			{
				
				ExecCreatedelivers(pid,list,userid,minnum);
			}
		}else
		{
			
			 sql=" select e.famount,e.fproductid,ifnull(d.fishistory,0) fishistory,ifnull(d.feffect,0) dfeffect from  t_pdt_custrelation  r "+
					" left join t_pdt_custrelationentry e on e.fparentid=r.fid left join  t_pdt_productdef  d on d.fid=e.fproductid "+
					" where r.fcustproductid='"+curmap.get("fcusproductid").toString()+"' and r.fcustomerid='"+curmap.get("fcustomerid").toString()+"'   order by d.fishistory desc";
			 ptypelist= this.QueryBySql(sql);
			 sql = "select fname from t_bd_custproduct where fid='"+curmap.get("fcusproductid")+"'";
			 List<HashMap<String, Object>> li = this.QueryBySql(sql);
			 String fname = li.get(0).get("fname").toString();
			 if(ptypelist.size()>0)
			 {
					if(((java.math.BigInteger)ptypelist.get(0).get("fishistory")).intValue()==1){
						throw new DJException("生成失败！"+fname+"客户产品关联产品存在历史版本");
					}
					if(((java.math.BigInteger)ptypelist.get(0).get("dfeffect")).intValue()==0){
						throw new DJException("生成失败！"+fname+"客户产品关联产品已禁用");
					}
					int value=(Integer)ptypelist.get(0).get("famount");
					int totalnum=(Integer)curmap.get("famount");
					curmap.put("ratio", value);
					list.add(curmap);
					ExecCreatedelivers(ptypelist.get(0).get("fproductid").toString(),list,userid,totalnum/value);
			 }else
			 {
				throw new DJException("生成失败！"+fname+"关联的产品已禁用或是历史版本；或客户产品没有设置相关联产品，请先设置！");
			 }
		}
		
	}
	
	
	private void createTraitDelivers(HashMap<String,Object> curmap,String userid) {
		String ftraitid = curmap.get("ftraitid").toString();
		String sql="select p.fnumber,p.forderid,p.fid pid,p.fsupplierid fsupplierid,s.fid sid,s.forderentryid from t_ord_productplan p inner join t_inv_storebalance s on s.fproductplanid=p.fid where s.ftraitid='"+ftraitid+"'";
		HashMap<String,Object> slist=(HashMap<String, Object>) this.QueryBySql(sql).get(0);
		//要货管理
		String deliversid = this.CreateUUid();
		StringBuilder sb = new StringBuilder();
		sb.append("INSERT INTO t_ord_delivers (fid,fcreatorid,fcreatetime,fupdateuserid,fupdatetime,fnumber,fcustomerid,fcusproductid,farrivetime,flinkman,flinkphone,famount,faddress,fdescription,fordered,fordermanid,fordertime,fsaleorderid,fordernumber,forderentryid,fimportEAS,fimportEASuserid,fimportEAStime,fouted,foutorid,fouttime,faddressid,");
		sb.append("falloted,feasdeliverid,fproductid,fapplayid,fapplynumber,fbalanceprice,fbalanceunitprice,fcusfid,ftype,ftraitid) VALUES( ");
		sb.append("'"+deliversid+"',");//<{fid: }>,
		sb.append("'"+userid+"',");//<{fcreatorid: }>,
		sb.append("now(),");//<{fcreatetime: }>,
		sb.append("'"+userid+"',");//<{fupdateuserid: }>,
		sb.append("now(),");//<{fupdatetime: }>,
		sb.append("'"+ServerContext.getNumberHelper().getNumber("t_ord_delivers", "DV", 4, false)+"',");//<{fnumber: }>,
		sb.append("'"+curmap.get("fcustomerid")+"',");//<{fcustomerid: }>,
		sb.append("'"+curmap.get("fcusproductid")+"',");//<{fcusproductid: }>,//?????
		sb.append("'"+curmap.get("farrivetime")+"',");//<{farrivetime: }>,
		sb.append("'"+curmap.get("flinkman")+"',");//<{flinkman: }>,
		sb.append("'"+curmap.get("flinkphone")+"',");//<{flinkphone: }>,
		sb.append("'"+curmap.get("famount")+"',");//<{famount: }>,
		sb.append("'"+curmap.get("faddress")+"',");//<{faddress: }>,
		sb.append("'"+curmap.get("fdescription")+"',");//<{fdescription: }>,
		sb.append("1,");//<{fordered: 0}>,
		sb.append("'',");//<{fordermanid: }>,
		sb.append("null,");//<{fordertime: }>,
		sb.append("'',");//<{fsaleorderid: }>,
		sb.append("'"+slist.get("fnumber")+"',");//<{fordernumber: }>,
		sb.append("'',");//<{forderentryid: }>,
		sb.append("0,");//<{fimportEAS: 0}>,
		sb.append("'',");//<{fimportEASuserid: }>,
		sb.append("null,");//<{fimportEAStime: }>,
		sb.append("0,");//<{fouted: 0}>,
		sb.append("'',");//<{foutorid: }>,
		sb.append("null,");//<{fouttime: }>,
		sb.append("'"+curmap.get("faddressid")+"',");//<{faddressid: }>,
		sb.append("1,");//<{falloted: 0}>,
		sb.append("'',");//<{feasdeliverid: }>,
		sb.append("'769baec7-2d87-11e4-bdb9-00ff6b42e1e5',");//<{fproductid: }>,
		sb.append("'"+curmap.get("fid")+"',");//<{fapplayid: }>,
		sb.append("'"+curmap.get("fnumber")+"',");//<{fapplynumber: }>,
		sb.append("0,");//fbalanceprice
		sb.append("0,");//fbalanceunitprice
		sb.append("'',");//<{fcusfid: }>,
		sb.append("0,");//<{ftype: }>,
		sb.append("'"+ftraitid+"')");//<{ftraitid: }>,efid
		this.ExecBySql(sb.toString());
		//要货申请与要货管理关系创建
		sb = new StringBuilder();
		sb.append("INSERT INTO t_ord_deliverratio(fid,fdeliverappid,fdeliverid,fcustid,fdelivernum,fdeliverapplynum ) VALUES(");
		sb.append("'"+CreateUUid()+"',");//<{fid: }>,
		sb.append("'"+curmap.get("fid")+"',");//<{fdeliverappid: }>,
		sb.append("'"+deliversid+"',");//<{fdeliverid: }>,
		sb.append("'',");//<{fcustid: }>,
		sb.append("1,");//<{fdelivernum: }>,
		sb.append("1)");//<{fdeliverapplynum: }>,
		this.ExecBySql(sb.toString());
		//配送信息
		sb = new StringBuilder();
		String deliverorderid=CreateUUid();
		sb.append("INSERT INTO t_ord_deliverorder (fid,fcreatorid,fcreatetime,fupdateuserid,fupdatetime,fnumber,fcustomerid,fcusproductid,farrivetime,flinkman,flinkphone,famount,faddress,fdescription,fordered,fordermanid,fordertime,fsaleorderid,fordernumber,forderentryid,fimportEAS,fimportEASuserid,fimportEAStime,fouted,foutorid,fouttime,faddressid,feasdeliverid,");
		sb.append("fistoPlan,fplanTime,fplanNumber,fplanid,falloted,fdeliversID,fsupplierId,fbalanceprice,fpurchaseprice,fbalanceunitprice,fpurchaseunitprice,fproductid,fcusfid,fmatched,faudited,fauditorid,faudittime,fstorebalanceid,ftype,fassembleQty,foutQty,fissync,ftraitid) VALUES( ");
		sb.append("'"+deliverorderid+"',");//<{fid: }>,
		sb.append("'"+userid+"',");//<{fcreatorid: }>,
		sb.append("now(),");//<{fcreatetime: }>,
		sb.append("'"+userid+"',");//<{fupdateuserid: }>,
		sb.append("now(),");//<{fupdatetime: }>,
		sb.append("'"+ServerContext.getNumberHelper().getNumber("t_ord_deliverorder", "D", 4, false)+"',");//<{fnumber: }>,
		sb.append("'"+curmap.get("fcustomerid")+"',");//<{fcustomerid: }>,
		sb.append("'"+curmap.get("fcusproductid")+"',");//<{fcusproductid: }>,
		sb.append("'"+curmap.get("farrivetime")+"',");//<{farrivetime: }>,
		sb.append("'"+curmap.get("flinkman")+"',");//<{flinkman: }>,
		sb.append("'"+curmap.get("flinkphone")+"',");//<{flinkphone: }>,
		sb.append(""+curmap.get("famount")+",");//<{famount: }>,
		sb.append("'"+curmap.get("faddress")+"',");//<{faddress: }>,
		sb.append("'"+curmap.get("fdescription")+"',");//<{fdescription: }>,
		sb.append("0,");//<{fordered: 0}>,
		sb.append("'',");//<{fordermanid: }>,
		sb.append("null,");//<{fordertime: }>,
		sb.append("'"+slist.get("forderid")+"',");//<{fsaleorderid: }>,
		sb.append("'"+slist.get("fnumber")+"',");//<{fordernumber: }>,
		sb.append("'"+slist.get("forderentryid")+"',");//<{forderentryid: }>,
		sb.append("0,");//<{fimportEAS: 0}>,
		sb.append("'',");//<{fimportEASuserid: }>,
		sb.append("null,");//<{fimportEAStime: }>,
		sb.append("0,");//<{fouted: 0}>,
		sb.append("'',");//<{foutorid: }>,
		sb.append("null,");//<{fouttime: }>,
		sb.append("'"+curmap.get("faddressid")+"',");//<{faddressid: }>,
		sb.append("'',");//<{feasdeliverid: }>,
		sb.append("0,");//<{fistoPlan: 0}>,
		sb.append("null,");//<{fplanTime: }>,
		sb.append("'"+slist.get("fnumber")+"',");//<{fplanNumber: }>,
		sb.append("'"+slist.get("pid")+"',");//<{fplanid: }>,
		sb.append("0,");//<{falloted: 0}>,
		sb.append("'"+deliversid+"',");//<{fdeliversID: }>,
		sb.append("'"+slist.get("fsupplierid")+"',");//<{fsupplierId: }>,
		sb.append("0,");//<{fbalanceprice: 0.0000}>,
		sb.append("0,");//需要考虑通知类型的产品先默认0，后续再修改<{fpurchaseprice: 0.0000}>,
		sb.append("0,");//<{fbalanceunitprice: 0.0000}>,
		sb.append("0,");//需要考虑通知类型的产品先默认0，后续再修改<{fpurchaseunitprice: 0.0000}>,
		sb.append("'769baec7-2d87-11e4-bdb9-00ff6b42e1e5',");//<{fproductid: }>,
		sb.append("'',");//<{fcusfid: }>,
		sb.append("0,");//<{fmatched: }>,
		sb.append("0,");//<{faudited: 0}>,
		sb.append("'',");//<{fauditorid: }>,
		sb.append("null,");//<{faudittime: }>,
		sb.append("'"+slist.get("sid")+"',");//<{fstorebalanceid: }>
		sb.append("0,");//<{ftype: }>
		sb.append("0,");//<{fassembleQty: }>
		sb.append("0,");//<{foutQty: }>
		sb.append("0,");//<{fissync: }>
		sb.append("'"+ftraitid+"')");//<{ftraitid: }>
		this.ExecBySql(sb.toString());
		//新增占用量数据
		sb = new StringBuilder();
		sb.append("INSERT INTO t_inv_usedstorebalance (fid,fratio,fusedqty,fdeliverorderid,fstorebalanceid,fproductid,ftype ) VALUES( ");
		sb.append("'"+CreateUUid()+"',");//<{fid: }>,
		sb.append(1+",");//<{fratio: }>,
		sb.append(curmap.get("famount").toString());//<{fusedqty: }>,
		sb.append(",'"+deliverorderid+"',");//<{fdeliverorderid: }>,????
		sb.append("'"+slist.get("sid")+"',");//<{fstorebalanceid: }>,
		sb.append("'769baec7-2d87-11e4-bdb9-00ff6b42e1e5',");//<{fproductid: }>,
		sb.append("1)");//<{ftype: }>,		
		this.ExecBySql(sb.toString());
		
	}

	/***
	 *  生成要货管理
	 * @param productid  产品ID
	 * @param dlmap 当前需要的要货申请集合（生成套装或者普通的要货申请）
	 * @param userid 用户 
	 * @param famount  生成数量
	 * @throws Exception
	 */
	public void ExecCreatedelivers(String productid,List<HashMap<String,Object>>  dlmap,String userid,int famount)throws Exception
	{
		Delivers dinfo =new Delivers();
		dinfo.setFid(this.CreateUUid());
		List list=createdeliveratio(dinfo,dlmap);
		Deliverapply dlinfo=(Deliverapply)list.get(0);
		dinfo.setFcustomerid(dlinfo.getFcustomerid());
		dinfo.setFamount(famount);//数量
		dinfo.setFproductid(productid);
		dinfo.setFbalanceunitprice(getBalanceprice(dinfo));
		dinfo.setFbalanceprice(dinfo.getFbalanceunitprice().multiply(new BigDecimal(famount)).divide(new BigDecimal(1), 4, BigDecimal.ROUND_HALF_UP));
		dinfo.setFnumber(ServerContext.getNumberHelper().getNumber("t_ord_delivers", "DV", 4, false));
		dinfo.setFcreatorid(userid);//用户
		dinfo.setFcreatetime(new Date());
		dinfo.setFupdatetime(new Date());
		dinfo.setFupdateuserid(userid);//用户
		dinfo.setFcusproductid(dlmap.size()>1?null:dlinfo.getFcusproductid());//客户产品
		dinfo.setFaddressid(dlinfo.getFaddressid());
		dinfo.setFaddress(dlinfo.getFaddress());
		dinfo.setFarrivetime(dlinfo.getFarrivetime());
		dinfo.setFlinkman(dlinfo.getFlinkman());
		dinfo.setFlinkphone(dlinfo.getFlinkphone());
		dinfo.setFdescription(dlinfo.getFdescription());//备注
		dinfo.setFapplayid(list.get(1).toString());
//		String appid[] = list.get(1).toString().split(",");
//		if(appid.length==1){
//			dinfo.setFpcmordernumber(dlinfo.getFordernumber());
//		}
		dinfo.setFpcmordernumber(list.get(3).toString());	
		dinfo.setFapplynumber(list.get(2).toString());	
		dinfo.setFtype(dlmap.size()>0?dlmap.get(0).get("ftype").toString():null);
		dinfo.setFsupplierid(dlinfo.getFsupplierid());
//		dinfo.setFcusfid(dlmap.containsKey("custfid")==false?"":dlmap.get("custfid").toString());
		this.saveOrUpdate(dinfo);
		for(HashMap<String, Object> map : dlmap){
			updateDeliverapplyStateByDeliverapply(map.get("fid").toString(),1);
		}
	}

	/**
	 * 生成要货申请与要货管理的比例
	 * @param dinfo  要货管理信息
	 * @param dlmap 要货申请集合（生成套装或者普通的要货申请）
	 * @return
	 * @throws Exception
	 */
	private List createdeliveratio(Delivers dinfo,List<HashMap<String,Object>>  dlmap)throws Exception
	{
		String appid="",fnumber="",fpcmordernumber="";
		Deliverapply ainfo=null;
		Deliverratio rinfo=null;
		 for(int m=0;m<dlmap.size();m++) //如果是套装 则生成多条要货申请与要货管理的比例
		 {
			rinfo=new Deliverratio();
			rinfo.setFdeliverappid(dlmap.get(m).get("fid").toString());
			rinfo.setFdeliverid(dinfo.getFid());
			rinfo.setFid(this.CreateUUid());
			rinfo.setFcustid(dlmap.get(m).get("fcusfid")==null?"":dlmap.get(m).get("fcusfid").toString());
			rinfo.setFdeliverapplynum(new BigDecimal(dlmap.get(m).get("ratio").toString()));
			dlmap.get(m).remove("ratio");
			this.saveOrUpdate(rinfo);
			if(m>0){
				 appid+=",";
				 fnumber+=",";
				if(!fpcmordernumber.equals(dlmap.get(m).get("fordernumber").toString())){fpcmordernumber=""; }
			}else
			{
				ainfo=this.Query(dlmap.get(m).get("fid").toString());
				fpcmordernumber=(dlmap.get(m).get("fordernumber")==null?"":(String)dlmap.get(m).get("fordernumber"));
			}
			appid=appid+dlmap.get(m).get("fid").toString();
			fnumber=fnumber+dlmap.get(m).get("fnumber").toString();
		}
		List list =new ArrayList();//传递给生成要货管理，
		list.add(ainfo);
		list.add(appid);
		list.add(fnumber);
		list.add(fpcmordernumber);
		return list;
		
	}
	
	/**
	 *  计算结算价格
	 * @param dinfo  要货管理
	 * @return
	 * @throws Exception
	 */
	private BigDecimal getBalanceprice(Delivers dinfo) throws Exception
	{
		String sql="select ifnull(c.fprice,s.fprice) price  from t_bal_balanceprice p "+
				" left join (select fprice,fparentid from t_bal_balancepricecustprices" +
				" where fcustomerid='"+dinfo.getFcustomerid()+"' and flowerlimit<="+dinfo.getFamount()+" and fupperlimit>"+dinfo.getFamount()+  " and fstartdate <=curdate() and fenddate>=curdate() and fiseffect=1 "+
				") c on c.fparentid=p.fid "+
				" left join (select fprice,fparentid from t_bal_balancepriceprices where flowerlimit<="+dinfo.getFamount()+" and fupperlimit>"+dinfo.getFamount()+" and fstartdate <=curdate() and fenddate>=curdate() and fiseffect=1  " +
				") s on s.fparentid=p.fid "+
               " where  p.fproductid='"+dinfo.getFproductid()+"'";
		List<HashMap<String,Object>> result= this.QueryBySql(sql);
		if(result.size()==1&&result.get(0).get("price")!=null)
		{
			
			return (BigDecimal)result.get(0).get("price");
		}
		return new BigDecimal("0.00");
		//暂时取消价格设置检查    BY  CC   2013-12-09
		//throw new DJException("请设置该产品:"+productdefDao.Query(dinfo.getFproductid()).getFname()+"的该数量"+dinfo.getFamount()+"区间的结算价格!");
	}

	@Override
	public void DelDeliverapply(String fidcls) {
		String hql = "from Deliverapply where fid in "+fidcls;
		List<Deliverapply> list = this.QueryByHql(hql);

		String sql=null;
		String deliverorderid;
		for(Deliverapply obj : list){
			if(obj.getFiscreate()!=0){//有特性的已生成管理的要货申请
				if(!StringUtils.isEmpty(obj.getFtraitid())){
				
					sql = "select 1 from t_ord_deliverorder where fdeliversid=(select fid from t_ord_delivers where fapplayid='"+obj.getFid()+"') and faudited=1";
					if(this.QueryExistsBySql(sql)){
						throw new DJException("此唛稿产品的配送信息已审核，无法删除！");
					}
					sql = "select group_concat(quote(fid)) fids from t_ord_deliverorder where fdeliversid=(select fid from t_ord_delivers where fapplayid='"+obj.getFid()+"')";
					deliverorderid = "("+((HashMap<String, Object>)this.QueryBySql(sql).get(0)).get("fids")+")";
					sql = "delete from t_inv_usedstorebalance where fdeliverorderid in "+deliverorderid;
					this.ExecBySql(sql);
					sql = "delete from t_ord_deliverorder where fid in "+deliverorderid;
					this.ExecBySql(sql);
					sql = "delete from t_ord_delivers where fapplayid='"+obj.getFid()+"'";
					this.ExecBySql(sql);
					sql = "delete from t_ord_deliverratio where fdeliverappid='"+obj.getFid()+"'";
					this.ExecBySql(sql);
				
				}else{
					throw new DJException("该产品已经生成要货管理，无法删除！");
				}
			}
			if(!StringUtils.isEmpty(obj.getFtraitid())){//有特性的要货申请
				sql = "select 1 from t_ord_schemedesignentry where fallot=1 and fid='"+obj.getFtraitid()+"'";
				List<HashMap<String,Object>> list1 = this.QueryBySql(sql);
				if(list1.size()>0){
					sql = "select 1 from mystock where fcustproductid ='"+obj.getFcusproductid()+"'";
					list1 = this.QueryBySql(sql);
					if(list1.size()>0){
						throw new DJException("该特性产品在备货中已存在，无法删除！");
					}
					sql = "select 1 from t_ord_Deliverapply where (ftraitid = '' OR ftraitid=NULL) and fcusproductid='"+obj.getFcusproductid()+"' and fid not in"+fidcls;
					list1 = this.QueryBySql(sql);
					if(list1.size()>0){
						throw new DJException("该特性产品在要货申请中已存在，无法删除！");
					}
//					sql = "select 1 from t_ord_Deliverapply where (ftraitid = '' OR ftraitid=NULL) and fcusproductid ='"+obj.getFcusproductid()+"'";
//					list1 = this.QueryBySql(sql);
//					if(list1.size()>0){
//						throw new DJException("该特性产品在要货申请中已存在，无法删除！");
//					}
				}
				sql = "update t_ord_schemedesignentry set fallot=0,frealamount=frealamount-"+obj.getFamount()+" where fid='"+obj.getFtraitid()+"'";
				this.ExecBySql(sql);
			}
			sql="delete from t_ord_deliverapply where fid ='"+obj.getFid()+"'";
			this.ExecBySql(sql);
		}
	}

	@Override
	public void addStateInfo(List<HashMap<String, Object>> data) {
		String sql;
		HashMap<String, String> stateData;
		String val;
		for(HashMap<String, Object> map : data){
			sql = "select group_concat(concat(fstate,'=',date_format(fcreatetime,'%Y-%m-%d %H:%i'))) stateinfo from t_ord_orderstate where fdeliverapplyid = '"+map.get("fid")+"'";
			stateData = baseSysDao.getMapData(sql);
			val = stateData.get("stateinfo")!=null ? ","+stateData.get("stateinfo") : "";
			map.put("stateinfo", "0="+map.get("fcreatetime")+val);
		}
	}


	@Override
	public void saveBoardDeliverapply(Deliverapply deliverapply) {
		saveCustBoardFormula(deliverapply);
		saveCustBoardLabel(deliverapply);
		this.saveOrUpdate(deliverapply);
	}

	private void saveCustBoardLabel(Deliverapply deliverapply) {
		String customerid = deliverapply.getFcustomerid();
		String flabel = deliverapply.getFlabel();
		flabel = flabel==null?flabel:flabel.trim();
		if(StringUtils.isEmpty(flabel)){
			return;
		}
		CustBoardLabel obj = null;
		String hql = "from CustBoardLabel where fcustomerid='"+customerid+"' and fname='"+flabel+"'";
		List<CustBoardLabel> list = this.QueryByHql(hql);
		if(list.size()>0){
			obj = (CustBoardLabel) list.get(0);
		}
		if(obj==null){
			obj = new CustBoardLabel();
			obj.setFid(this.CreateUUid());
			obj.setFcustomerid(customerid);
			obj.setFname(flabel);
		}
		obj.setFcreatetime(new Date());
		this.saveOrUpdate(obj);
	}

	private void saveCustBoardFormula(Deliverapply deliverapply) {
		CustBoardFormula obj = new CustBoardFormula();
		short layer = Short.valueOf(baseSysDao.getStringValue("t_pdt_productdef", deliverapply.getFmaterialfid(),"flayer"));
		if(layer%2==0){
			return;
		}
		if("不压线".equals(deliverapply.getFstavetype())){
			return;
		}
		obj.setFlayer(layer);
		obj.setFid(this.CreateUUid());
		obj.setFboxmodel((short)(deliverapply.getFboxmodel()+0));
		obj.setFcustomerid(deliverapply.getFcustomerid());
		obj.setFdefine1(deliverapply.getFdefine1().floatValue());
		obj.setFdefine2(deliverapply.getFdefine2().floatValue());
		obj.setFdefine3(deliverapply.getFdefine3().floatValue());
		obj.setFhformula(deliverapply.getFhformula().floatValue());
		obj.setFhformula1(deliverapply.getFhformula1().floatValue());
		obj.setFvformula(deliverapply.getFvformula().floatValue());
		String sql = "delete from t_ord_custboardformula where fcustomerid='"+obj.getFcustomerid()+"' and fboxmodel="+obj.getFboxmodel()+" and flayer="+obj.getFlayer();
		this.ExecBySql(sql);
		this.saveOrUpdate(obj);
	}

	@Override
	public String ExcelSql(HttpServletRequest request) {
		String Defaultfilter = request.getParameter("Defaultfilter");
		
		JSONArray jsa = JSONArray.fromObject(Defaultfilter);
		
		String countSql = "";
		if (jsa.size() != 0) {
			
			String startDate = jsa.getJSONObject(0).getString("value");
			String endDate = jsa.getJSONObject(1).getString("value");
			
			countSql ="SELECT sfname,fcustomerNumber,fcustomerName,fboxtype, sum(famount)  famount ,count(*)   fcount,fordesource FROM t_ord_deliverapply_cus_detail_view"
					+ String.format(" where dcreatetime between '%s' and '%s' ",
							startDate, endDate)
					+ " group by  fcustomerID,fboxtype,fordesource  order by fboxtype,fcustomerName desc";

		} else{
			countSql ="SELECT sfname,fcustomerNumber,fcustomerName,fboxtype,sum(famount)  famount,fboxtype,count(*)   fcount,fordesource  FROM t_ord_deliverapply_cus_detail_view"
					+ " where 1=1 group by  fcustomerID,fboxtype,fordesource  order by fboxtype,fcustomerName desc";
		}
		return countSql;
	}
	@Override
	public ListResult buildExcelListResult(String selectSql ,HttpServletRequest request, IBaseDao dao) {
		
		String colums = request.getParameter("columns");
		
		JSONArray columsJsoa = JSONArray.fromObject(colums);
		
		List<HashMap<String, Object>> resultT = dao.QueryBySql(selectSql);
		
		ListResult resultR = new ListResult();
		
		resultR.setTotal(String.valueOf(resultT.size()));
		
		//索引map用于构建中文表头
		Map<String, String> dateIndexMap = new HashMap<>();
		
		int sizeT = columsJsoa.size();
		
		for (int i = 0; i < sizeT; i++) {
//			if(i>=2){
				JSONObject jso = columsJsoa.getJSONObject(i);

				dateIndexMap.put(jso.getString("dataIndex"), jso.getString("text"));
//			}
			
			
		}

		
		List<HashMap<String, Object>> dates = resultT;
		
		List<HashMap<String, Object>> datesR = new ArrayList<>();
		
		Set<String> keys = dateIndexMap.keySet();
		
		for (HashMap<String, Object> item : dates) {
			
			HashMap<String, Object> goalItem = new HashMap<>();
									
			for (String key : keys) {
				
				goalItem.put(dateIndexMap.get(key), item.get(key));
				
			}
			
			datesR.add(goalItem);
			
		}
		
		resultR.setData(datesR);
		
		return resultR;
		
	}

	@Override
	public int ExecUpdateBoardStateToCreate(String fidcls) throws Exception {
		Productdef pinfo;
		String pName;
		List<ProducePlan> plans;
		ProducePlan planInfo=null;
		Calendar cday;
		DateFormat dateFormat;
		String con = baseSysDao.getCondition(fidcls);
		String sql = "select fid,fmaterialfid,farrivetime from t_ord_deliverapply where fid" + con;
		List<HashMap<String, Object>> deliverapplys = this.QueryBySql(sql);
		for(HashMap<String, Object> deliverapply: deliverapplys){
			String productId = (String) deliverapply.get("fmaterialfid");
			Date arrivetime = (Date) deliverapply.get("farrivetime");
			pinfo=(Productdef)this.Query(Productdef.class, productId);
			pName = pinfo.getFname()+" /"+pinfo.getFlayer()+pinfo.getFtilemodelid();
			if(pinfo.getFeffect()==0){
				throw new DJException("材料“"+pName+"”已禁用，无法下单！");
			}
			plans=this.QueryByHql("from ProducePlan p where p.fproductid='"+productId+"' and p.fsupplierid='"+pinfo.getFsupplierid()+"'");
			if(plans!=null&&plans.size()>0){
				planInfo=plans.get(0);
			}
			try {
				cday = getFirstArrivetimeDate(planInfo);
			} catch (Exception e) {
				throw new DJException("排产日期计算出错，请上报平台更改！");
			}
			if(arrivetime.before(cday.getTime())){
				dateFormat = new SimpleDateFormat("yyyy-MM-dd");
				cday.add(Calendar.DAY_OF_MONTH, 1);
				sql = "update t_ord_deliverapply set farrivetime = '"+dateFormat.format(cday.getTime())+"' where fid = '"+deliverapply.get("fid")+"'";
				this.ExecBySql(sql);
			}
		}
		sql = "update t_ord_deliverapply set fstate=0 where fstate=7 and fid"+con;
		return this.ExecBySql(sql);
	}
	
	private Calendar getFirstArrivetimeDate(ProducePlan planInfo)throws Exception 
	{
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		int whilecount=0;
		if(planInfo==null)
		{
			return c;
		}
		int currentday=c.get(Calendar.DAY_OF_MONTH);//当前日
		List<Integer> list=new ArrayList<Integer>();//存放开机或不开机时间
		if(planInfo.getFisweek()==0)//1 按周+不开机算；0为按开机时间算
		{
			int maxday=c.getActualMaximum(Calendar.DAY_OF_MONTH);//当月最大日期
			String[] produceDays = planInfo.getFday().split("_");
			for(String s: produceDays){
				list.add(Integer.valueOf(s));
			}
			 if(list.contains(currentday))
			 {
				return c;
			 }else
			 {
				 while(whilecount<4)
				 {
					 for(int i=0;i<list.size();i++)
					 {
						if(currentday<=list.get(i)&&list.get(i)<=maxday)
						{
							c.set(Calendar.DAY_OF_MONTH, list.get(i));//设置时间
							return c;
						}
					 }
					 c.add(Calendar.MONTH, 1);
					 currentday=1;
					 maxday=c.getActualMaximum(Calendar.DAY_OF_MONTH);
					 whilecount++;
				 }
			 }
		}else//按周
		{
			int currentweek=c.get(Calendar.DAY_OF_WEEK)-1;//0-6 当前星期几;c中存1-7 星期日-到星期六
			String fnoday = "_"+planInfo.getFnoday()+"_";
			String[] week= planInfo.getFweek().split("");
			List<Calendar> dayList = new ArrayList<>();
			Calendar calendar;
			for(String day: week){
				if("".equals(day)){
					continue;
				}
				calendar = Calendar.getInstance();
				calendar.setTime(new Date());
				calendar.add(Calendar.DAY_OF_MONTH, Integer.valueOf(day)-currentweek);
				dayList.add(calendar);
			}
			while(true){
				for(Calendar cr: dayList){
					if(cr.getTimeInMillis()>=c.getTimeInMillis()&& fnoday.indexOf("_"+cr.get(Calendar.DAY_OF_MONTH)+"_")==-1){
						return cr;
					}else{
						cr.add(Calendar.WEEK_OF_YEAR,1);
					}
				}
			}
		}
		throw new DJException("计算排产日出错！");
	}

	@Override
	public int ExecDelCustomerLabelById(String customerid,String labelName) {
		labelName = labelName.trim();
		String sql = "delete from t_ord_custboardlabel where fcustomerid=:customerid and fname=:fname";
		params p = new params();
		p.put("customerid", customerid);
		p.put("fname", labelName);
		return this.ExecBySql(sql, p);
	}

	
	/**
	 * 获取多级套装+子件，并且对“分录的产品类型”赋值
	 */
	protected List getAllProductSuit(String fproductid) throws Exception
	{
		List list = new ArrayList();
		String sql = "select fnewtype,FCombination,FAssemble from t_pdt_productdef where fid = '"+fproductid+"'";
		List<HashMap<String,Object>> pdlist= this.QueryBySql(sql);
		if(pdlist.size()>0){
			HashMap productrs = pdlist.get(0);
			HashMap productInfo = new HashMap();
			productInfo.put("fid",fproductid);
			productInfo.put("amountRate",new Integer(1));
			productInfo.put("entryProductType","1");
			productInfo.put("ParentOrderEntryId",null);
			if(productrs.get("fnewtype")!=null){
				productInfo.put("fnewtype", productrs.get("fnewtype").toString());
			}else{
				productInfo.put("fnewtype", "0");
			}
			
			if(productrs.get("FCombination")!=null){
				productInfo.put("FCombination", productrs.get("FCombination").toString());
			}else{
				productInfo.put("FCombination", "0");
			}
			if(productrs.get("FAssemble")!=null){
				productInfo.put("FAssemble", productrs.get("FAssemble").toString());
			}else{
				productInfo.put("FAssemble", null);
			}
			
			productInfo.put("fiscombinecrosssubs", new Integer(0));
			getProductSuit(productInfo,list,null);
		}
		
		return list;
	}
	
	private void getProductSuit(HashMap productInfo,List list,String parentEntryId) throws Exception
	{
		boolean isSuit = (productInfo.get("fnewtype").equals("2") || productInfo.get("fnewtype").equals("4"));
		String orderEntryID = this.CreateUUid();
		productInfo.put("orderEntryID",orderEntryID);
		if(!isSuit){
			//如果非套装，自己的分录ID，父分录Id取传入的参数，直接放入Collection
//			BOSUuid orderEntryID = BOSUuid.create(new SaleOrderEntryInfo().getBOSType());
			productInfo.put("ParentOrderEntryId",parentEntryId);
			list.add(productInfo);
		}
		else{
			//如果是套装，自己的分录Id，父分录Id，总套为null，非总套取参数，先把自己放入Collection，再循环递归调用自己的子件
			
			//如果不是首次进入递归，套装的“分录的产品类型”== 非总套
			if(productInfo.get("entryProductType").toString().equals("1")){
				//nothing
			}
			else{
				productInfo.put("entryProductType","2");
				productInfo.put("ParentOrderEntryId",parentEntryId);
			}
			
			list.add(productInfo);
			
			//子件“分录的产品类型”
			String subEntryProductType = "";
			boolean isassemble = false;
			if(productInfo.get("FCombination")!=null && productInfo.get("FCombination").toString().equals("1")){
				//preSuitProductType = 1;
				subEntryProductType = "7";	//合并下料子件
			}
			else if(productInfo.get("FAssemble")!=null && productInfo.get("FAssemble").toString().equals("1")){
				//preSuitProductType = 2;
				subEntryProductType = "6";	//组装套装子件
				isassemble = true;
			}
			else {
				//preSuitProductType = 0;
				subEntryProductType = "5";	//普通套装子件
			}
			
//			ProductDefProductCollection subCols =  productInfo.getProducts();
//			stmt = conn.prepareStatement("select FAmount,FProductID from T_PDT_ProductDefProducts where Fparentid = '"+productInfo.get("fid")+"'");
//			ResultSet productrs = stmt.executeQuery();
			String sql = "select FAmount,FProductID from T_PDT_ProductDefProducts where Fparentid = '"+productInfo.get("fid")+"'";
			List<HashMap<String,Object>> pdlist= this.QueryBySql(sql);
			for(int i=0;i<pdlist.size();i++){
				HashMap productrs = pdlist.get(i);
				
//				stmt = conn.prepareStatement("select fid,fnewtype,ftype,FCombination,FAssemble from t_pdt_productdef where fid = '"+productrs.get("FProductID")+"'");
//				ResultSet subproductrs = stmt.executeQuery();
				sql = "select fid,fnewtype,FCombination,FAssemble,fiscombinecrosssubs from t_pdt_productdef where fid = '"+productrs.get("FProductID")+"'";
				List<HashMap<String,Object>> pdslist= this.QueryBySql(sql);
				if(pdslist.size()>0){
					HashMap subproductrs = pdslist.get(0);
					HashMap subInfo = new HashMap();
					subInfo.put("fid",subproductrs.get("fid"));
					subInfo.put("amountRate",new Integer(productrs.get("FAmount").toString()) * new Integer(productInfo.get("amountRate").toString()));
					if(subproductrs.get("fnewtype")!=null){
						subInfo.put("fnewtype", subproductrs.get("fnewtype").toString());
					}else{
						subInfo.put("fnewtype", "0");
					}
					
					if(subproductrs.get("FCombination")!=null){
						subInfo.put("FCombination", subproductrs.get("FCombination").toString());
					}else{
						subInfo.put("FCombination", "0");
					}
					
					subInfo.put("FAssemble", subproductrs.get("FAssemble").toString());
					subInfo.put("fiscombinecrosssubs", subproductrs.get("fiscombinecrosssubs").toString());
					//子件的“分录的产品类型”
					subInfo.put("entryProductType",subEntryProductType);
					if(isassemble){
						subInfo.put("FisassembleSuitSub", Boolean.TRUE);
					}else{
						subInfo.put("FisassembleSuitSub", Boolean.FALSE);
					}
					
					getProductSuit(subInfo,list,orderEntryID);
				}
				
			}
		}
	}
	
	/**
	 * 要货管理与要货申请关系表
	 * @param dlinfo
	 * @param dinfo
	 * @return
	 */
	private Deliverratio getDeliverratio(Deliverapply dlinfo, Delivers dinfo) {
		Deliverratio deliveratio=new Deliverratio();
		deliveratio.setFdeliverappid(dlinfo.getFid());
		deliveratio.setFdeliverid(dinfo.getFid());
		deliveratio.setFid(this.CreateUUid());
		deliveratio.setFcustid(dlinfo.getFcusfid()==null?"":dlinfo.getFcusfid());
		deliveratio.setFdeliverapplynum(new BigDecimal(1));
		return deliveratio;
	}
	/**
	 * 要货管理
	 * @param dlinfo
	 * @param userid
	 * @return
	 * @throws Exception 
	 */
	private Delivers getDelivers(Deliverapply dlinfo, String userid) throws Exception {
		Delivers dinfo=new Delivers();
		dinfo.setFid(this.CreateUUid());
		dinfo.setFcustomerid(dlinfo.getFcustomerid());
		dinfo.setFamount(dlinfo.getFamount());//数量
		dinfo.setFproductid(dlinfo.getFmaterialfid());
		dinfo.setFbalanceunitprice(getBalanceprice(dinfo));
		dinfo.setFbalanceprice(dinfo.getFbalanceunitprice().multiply(new BigDecimal(dlinfo.getFamount())).divide(new BigDecimal(1), 4, BigDecimal.ROUND_HALF_UP));
		dinfo.setFnumber(ServerContext.getNumberHelper().getNumber("t_ord_delivers", "DV", 4, false));
		dinfo.setFcreatorid(userid);//用户
		dinfo.setFcreatetime(new Date());
		dinfo.setFupdatetime(new Date());
		dinfo.setFupdateuserid(userid);//用户
		dinfo.setFcusproductid(dlinfo.getFcusproductid());//客户产品
		dinfo.setFaddressid(dlinfo.getFaddressid());
		dinfo.setFaddress(dlinfo.getFaddress());
		dinfo.setFarrivetime(dlinfo.getFarrivetime());
		dinfo.setFlinkman(dlinfo.getFlinkman());
		dinfo.setFlinkphone(dlinfo.getFlinkphone());
		dinfo.setFdescription(dlinfo.getFdescription());//备注
		dinfo.setFapplayid(dlinfo.getFid());
		dinfo.setFpcmordernumber(dlinfo.getFordernumber());	
		dinfo.setFapplynumber(dlinfo.getFnumber());	
		dinfo.setFtype(dlinfo.getFtype());
		dinfo.setFsupplierid(dlinfo.getFsupplierid());
		return dinfo;
	}
	
	/**
	 * 制造商订单
	 * @param slist
	 * @return
	 */
	public ProductPlan getProductPlan(List<Saleorder> slist) {
		if(slist.size()<1) return null;
		Saleorder sinfo=slist.get(0);
		String userid=sinfo.getFcreatorid();
		String fproductdefid=sinfo.getFproductdefid();
		ISupplierDao ISysUDao = (ISupplierDao) SpringContextUtils.getBean("SupplierDao");
		//在这里获得采购价，详见具体方法 
		BigDecimal[] purchaseprices = getPurchasePrices(sinfo.getFamount(),
		sinfo.getFproductdefid(), sinfo.getFsupplierid()); 
			
			if (purchaseprices == null) {
				//暂时不检查是否有价格   BY CC 2013-12-09
				purchaseprices=new BigDecimal[]{new BigDecimal("0.00"),new BigDecimal("0.00")};
			}
			Supplier suppinfo=ISysUDao.Query(sinfo.getFsupplierid());
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
			p.setFaffirmed(1);
			p.setFstockinqty(0);
			p.setFstockoutqty(0);
			p.setFstoreqty(0);
			p.setFassemble(sinfo.getFassemble());
			p.setFiscombinecrosssubs(sinfo.getFiscombinecrosssubs());
			p.setFeasorderid(sinfo.getFeasorderid());
			p.setFeasorderentryid(sinfo.getFeasorderentryid());
			p.setFcloseed(0);
			p.setFstate(1);
			p.setFdescription(sinfo.getFdescription());
			p.setFaudited(1);
			p.setFauditorid(sinfo.getFcreatorid());
			p.setFaudittime(new Date());
			p.setFpcmordernumber(sinfo.getFpcmordernumber());
		return p;
	}
	/**
	 * 生成库存表
	 * @param pinfo
	 * @param sinfo
	 * @return
	 */
	public Storebalance getStorebalance(ProductPlan pinfo,Supplier sinfo) {
		Storebalance sbinfo=new Storebalance();
		sbinfo.setFbalanceqty(0);
		sbinfo.setFcreatetime(new Date());
		sbinfo.setFcreatorid(pinfo.getFcreatorid());
		sbinfo.setFcustomerid(pinfo.getFcustomerid());
		sbinfo.setFid(CreateUUid());
		sbinfo.setFinqty(0);
		sbinfo.setForderentryid(pinfo.getFparentorderid());
		sbinfo.setFoutqty(0);
		sbinfo.setFproductId(pinfo.getFproductdefid());
		sbinfo.setFproductplanId(pinfo.getFid());
		sbinfo.setFsaleorderid(pinfo.getForderid());
		sbinfo.setFsupplierID(pinfo.getFsupplierid());
		sbinfo.setFtype(1);
		sbinfo.setFupdatetime(new Date());
		sbinfo.setFupdateuserid(pinfo.getFcreatorid());
		sbinfo.setFwarehouseId(sinfo.getFwarehouseid());
		sbinfo.setFwarehouseSiteId(sinfo.getFwarehousesiteid());
		return sbinfo;
	}
	
	/**
	 * 生成生产订单
	 * @param dinfo
	 * @param userid
	 * @param fnewtype
	 * @return
	 * @throws Exception
	 */
	private List<Saleorder> getSaleorderList(Delivers dinfo,String userid,int fnewtype) throws Exception
	{
		ArrayList<Saleorder> solist = new ArrayList<Saleorder>();
		String orderid = this.CreateUUid();
		String fordernumber = ServerContext.getNumberHelper().getNumber("t_ord_saleorder", "Z",4,false);
		if(fnewtype!=2 && fnewtype!=4){
			Saleorder soinfo = new Saleorder();
			String orderEntryid = this.CreateUUid();
			soinfo.setFid(orderEntryid);
			soinfo.setForderid(orderid);
			soinfo.setFcreatorid(userid);
			soinfo.setFcreatetime(new Date());
			soinfo.setFnumber(fordernumber);
			soinfo.setFproductdefid(dinfo.getFproductid());
			soinfo.setFentryProductType(0);
			soinfo.setFtype(1);
			soinfo.setFlastupdatetime(new Date());
			soinfo.setFlastupdateuserid(userid);
			soinfo.setFamount(dinfo.getFamount());
			soinfo.setFcustomerid(dinfo.getFcustomerid());
			soinfo.setFcustproduct(dinfo.getFcusproductid());
			soinfo.setFarrivetime(dinfo.getFarrivetime());
			soinfo.setFbizdate(new Date());
			soinfo.setFamountrate(1);
			soinfo.setFordertype(1);
			soinfo.setFseq(1);
			soinfo.setFsupplierid(dinfo.getFsupplierid());
			soinfo.setFaudited(1);//改为已审核
			soinfo.setFauditorid(userid);
			soinfo.setFaudittime(new Date());
			soinfo.setFpcmordernumber(dinfo.getFpcmordernumber());
			soinfo.setFdescription(dinfo.getFdescription());		
			solist.add(soinfo);
		}else{
			//套装
			// 一次性获取所有级次的：套装+子产品
			// 再按顺序加载即可
		
			List list = getAllProductSuit(dinfo.getFproductid());
			HashMap subInfo = null;
			String orderentryid = "";
			for (int k = 0; k < list.size(); k++)
			{
				subInfo = (HashMap) list.get(k);
				
				Saleorder soinfo = new Saleorder();
				
				if(subInfo.get("FAssemble")!=null && subInfo.get("FAssemble").toString().equals("1")){
					soinfo.setFassemble(1);
				}else{
					soinfo.setFassemble(0);
				}
				
				if(subInfo.get("fiscombinecrosssubs")!=null && subInfo.get("fiscombinecrosssubs").toString().equals("1")){
					soinfo.setFiscombinecrosssubs(1);
				}else{
					soinfo.setFiscombinecrosssubs(0);
				}
				
				if(k==0){
					soinfo.setFamount(dinfo.getFamount());
					soinfo.setFproductdefid(dinfo.getFproductid());
					soinfo.setFsuitProductId(dinfo.getFproductid());
					orderentryid = subInfo.get("orderEntryID").toString();	
				}else{
					soinfo.setFparentOrderEntryId(subInfo.get("ParentOrderEntryId").toString());
					soinfo.setFamount(dinfo.getFamount() * new Integer(subInfo.get("amountRate").toString()));
					soinfo.setFproductdefid(subInfo.get("fid").toString());
				}
				soinfo.setFsupplierid(dinfo.getFsupplierid());
				soinfo.setFtype(1);
				soinfo.setFordertype(2);
				soinfo.setFentryProductType(new Integer(subInfo.get("entryProductType").toString()));
				soinfo.setFid(subInfo.get("orderEntryID").toString());
				soinfo.setFseq((k+1));
				soinfo.setFimportEas(0);
				soinfo.setFamountrate(new Integer(subInfo.get("amountRate").toString()));
				soinfo.setForderid(orderid);
				soinfo.setFcreatorid(userid);
				soinfo.setFcreatetime(new Date());
				soinfo.setFlastupdatetime(new Date());
				soinfo.setFlastupdateuserid(userid);
				soinfo.setFnumber(fordernumber);
				soinfo.setFcustomerid(dinfo.getFcustomerid());
				soinfo.setFcustproduct(dinfo.getFcusproductid());
				soinfo.setFarrivetime(dinfo.getFarrivetime());
				soinfo.setFbizdate(new Date());
				soinfo.setFaudited(1);//改为已审核
				soinfo.setFauditorid(userid);
				soinfo.setFaudittime(new Date());
				soinfo.setFallot(0);
				soinfo.setFpcmordernumber(dinfo.getFpcmordernumber());
				soinfo.setFdescription(dinfo.getFdescription());
				solist.add(soinfo);
			}
		}
			return solist;
	}
	
	/**获取价格**/
	private BigDecimal[] getPurchasePrices(Integer famount, String fproductdefid, String supplierid) {

		BigDecimal perpurchasePrice = getTheBestPrice(famount, fproductdefid, supplierid);

		if (perpurchasePrice == null) {
			
			return null;
			
		}
		
		BigDecimal purchasePrice = perpurchasePrice.multiply(new BigDecimal(famount));

		return new BigDecimal[] { perpurchasePrice,
				purchasePrice};
	}
	
	/**获取价格**/
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
		BigDecimal t = (BigDecimal) bestPrices.get(0).get("pvfprice");
		return t; 
	}

	
	/**
	 * 生成配送信息
	 * @param fdeliverid
	 * @param sbinfo 
	 * @param planfnumber
	 */
	private Deliverorder getDeliverorder(Delivers dinfo,Storebalance sbinfo,String planfnumber)
	{

		//创建配送信息
		 Deliverorder order  = new Deliverorder();
		 order.setFid(CreateUUid());
		 order.setFcreatorid(sbinfo.getFcreatorid());
		 order.setFcreatetime(new Date());
		 order.setFupdatetime(new Date());
		 order.setFupdateuserid(sbinfo.getFcreatorid());
		 order.setFnumber(ServerContext.getNumberHelper().getNumber(
				"t_ord_deliverorder", "D", 4, false));
		 order.setFcustomerid(dinfo.getFcustomerid());
		 order.setFcusproductid(dinfo.getFcusproductid());
		 order.setFarrivetime(dinfo.getFarrivetime());
		 order.setFlinkman(dinfo.getFlinkman());
		 order.setFlinkphone(dinfo.getFlinkphone());
		 order.setFamount(dinfo.getFamount());
		 order.setFaddress(dinfo.getFaddress());
		 order.setFaddressid(dinfo.getFaddressid());
		 order.setFdescription(dinfo.getFdescription());//order不赋值
		 order.setFsaleorderid(sbinfo.getFsaleorderid());
		 order.setFordernumber(planfnumber);
		 order.setForderentryid(sbinfo.getForderentryid());
		 order.setFimportEas(0);
		 order.setFouted(0);
		 order.setFplanNumber(planfnumber);
		 order.setFplanid(sbinfo.getFproductplanId());
		 order.setFalloted(0);
		 order.setFdeliversId(dinfo.getFid());
		 order.setFsupplierId(sbinfo.getFsupplierID());
		 order.setFbalanceprice(dinfo.getFbalanceunitprice().multiply(new BigDecimal(dinfo.getFamount())));
		 order.setFbalanceunitprice(dinfo.getFbalanceunitprice());
		 order.setFproductid(dinfo.getFproductid());
		 order.setFcusfid(dinfo.getFcusfid());
		 if(order.getFsupplierId()!=null&&"39gW7X9mRcWoSwsNJhU12TfGffw=".equals(order.getFsupplierId())){
			 order.setFaudited(0);
		 }else//非东经自动审核
		 {
			 order.setFaudited(1);//自动审核
			 order.setFauditorid(sbinfo.getFcreatorid());//自动审核
			 order.setFaudittime(new Date());//自动审核
		 }
		 order.setFstorebalanceid(sbinfo.getFid());
		 order.setFtype(dinfo.getFtype());
		 order.setFpcmordernumber(dinfo.getFpcmordernumber());
		 return order;
	}

/**
 * 生成暂用表
 * @param order
 * @return
 */
	private Usedstorebalance getUsedstorebalance(Deliverorder order)
	{
		Usedstorebalance usedstorebalance = new Usedstorebalance(this.CreateUUid());
		usedstorebalance.setFdeliverorderid(order.getFid());
		usedstorebalance.setFproductid(order.getFproductid());
		usedstorebalance.setFratio(1);
		usedstorebalance.setFstorebalanceid(order.getFstorebalanceid());
		usedstorebalance.setFtype(1);
		usedstorebalance.setFusedqty(order.getFamount());
		return usedstorebalance;
	}
	/*
	 * 快速下单自动生成
	 */
	public void ExecAutoCreateDeliverapply()
	{
		HashMap messages=new HashMap();
		try{
		String userid = "3c3c9f29-64b9-11e4-bdb9-00ff6b42e1e5"; // administrator
		String sql=" select *  from t_ord_deliverapply where fiscreate=0 and fboxtype=0 AND fstate=0 and IFNULL(fmaterialfid,'')<>'' " ;
		List<HashMap<String,Object>> deliverresult =QueryBySql(sql);
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		for(HashMap<String,Object> apply :deliverresult)
		{
			if(StringUtils.isEmpty((String)apply.get("fid"))) continue;
			if(!StringUtils.isEmpty((String)apply.get("ftraitid"))) continue;
			if(StringUtils.isEmpty((String)apply.get("faddressid"))) continue;
			if(StringUtils.isEmpty((String)apply.get("fmaterialfid"))) continue;
			if(StringUtils.isEmpty((String)apply.get("fsupplierid"))) continue;
			if(StringUtils.isEmpty((String)apply.get("fcustomerid"))) continue;
			if(QueryExistsBySql("SELECT fid FROM `t_pdt_vmiproductparam` WHERE fefected=1 and fcustomerid='"+(String)apply.get("fcustomerid")+"'")) continue;//有安全库存跳过
			Productdef f =(Productdef)Query(Productdef.class, (String)apply.get("fmaterialfid"));
			if(f==null)continue;
			if(f.getFeffect()==null||f.getFeffect()==0) continue;//产品没有启用
			if(f.getFishistory()!=null&&f.getFishistory()==1) continue;//产品是历史版本
			int fnewtype=StringUtils.isEmpty(f.getFnewtype())?1:new Integer(f.getFnewtype());
			if(QueryExistsBySql("select 1 from t_pdt_productdefproducts p where p.fparentid in (select fproductid from t_pdt_productdefproducts where fparentid = '"+f.getFid()+"')")) continue;//产品超过三层
			Supplier supplier=(Supplier)Query(Supplier.class, (String)apply.get("fsupplierid"));
//			if(StringUtils.isEmpty(supplier.getFwarehouseid())) continue;//制造商没有设置仓库
//			if(StringUtils.isEmpty(supplier.getFwarehousesiteid())) continue;//制造商没有设置库位
			 if(StringUtils.isEmpty(supplier.getFwarehouseid())) supplier.setFwarehouseid("478a9181-0dc4-11e5-9395-00ff61c9f2e3");
			 if(StringUtils.isEmpty(supplier.getFwarehousesiteid())) supplier.setFwarehousesiteid("5315e691-0dc4-11e5-9395-00ff61c9f2e3");
			///快速下单，有备货产品？跳过？？
//			if(QueryExistsBySql("select t1.fid from t_pdt_productdefproducts t inner join t_pdt_vmiproductparam t1 on t.fparentid=t1.fproductid  where t.fproductid='"+f.getFid()+"' and t1.ftype=0 ")) continue;//存在通知类型的套装，不需要下单！
//			if(!"2".equals(f.getFnewtype())&&!"4".equals(f.getFnewtype()))
//			{
//				if(QueryExistsBySql("select 1 from t_pdt_vmiproductparam where ftype=0 and fcustomerid = '"+(String)apply.get("fcustomerid")+"' and fproductid in (select fparentid from t_pdt_productdefproducts where fproductid = '"+f.getFid()+"') ")); continue;//当前产品的套件为通知类型，不能下单！
//			}
			///sql = "select 1 from t_pdt_vmiproductparam where ftype=0 and fcustomerid = '"+fcustomerid+"' and fproductid  = '"+subInfo.get("fid")+"' ";当前产品的子件为通知类型，不能下单
			Deliverapply dlinfo=this.Query((String)apply.get("fid"));

			//生成管理
			Delivers dinfo =getDelivers(dlinfo,userid);
			//要货管理与要货申请关系
			Deliverratio deliveratio=this.getDeliverratio(dlinfo,dinfo);
			//生产订单
			List<Saleorder> solistArrayList=getSaleorderList(dinfo,userid,fnewtype);
			if(solistArrayList.size()==0) continue;
			//反写要货管理：
			dinfo.setFordered(1);
			dinfo.setFordermanid(userid);
			dinfo.setFordertime(df.parse(df.format(new Date())));
			dinfo.setFsaleorderid(solistArrayList.get(0).getForderid());
			dinfo.setForderentryid(solistArrayList.get(0).getFid());
			dinfo.setFordernumber(solistArrayList.get(0).getFnumber()+"-1");	
			//制造商订单，
			ProductPlan p=getProductPlan(solistArrayList);
			p.setFdeliverapplyid(dlinfo.getFid());//存要货申请id
			p.setFcreatetime(dlinfo.getFcreatetime());//跟要货申请创建时间一致
			
			dlinfo.setFplanid(p.getFid());
			dlinfo.setFplanNumber(p.getFnumber());
			dlinfo.setFstate(1);
			//结存
			Storebalance sbinfo=getStorebalance(p,supplier);
			for(Saleorder s :solistArrayList)
			{
				s.setFallot(1);
				s.setFallotorid(userid); 
				s.setFallottime(new Date());
			}
			//配送单
			Deliverorder order=this.getDeliverorder(dinfo, sbinfo, p.getFnumber());
			//暂存表
			Usedstorebalance usedstorebalance =this.getUsedstorebalance(order);
			//反写要货管理：
			dinfo.setFalloted(1);
			dinfo.setFordernumber(p.getFnumber());		
		
			
			//提交
			this.saveOrUpdate(dlinfo);//要货申请  反写制造商编号,id
			this.saveOrUpdate(dinfo);//要货管理
			this.saveOrUpdate(deliveratio);//要货管理与要货申请关系
			for(Saleorder s :solistArrayList)
			{
				this.saveOrUpdate(s);//保存生产订单
			}
			this.saveOrUpdate(p);//保存制造商
			this.saveOrUpdate(sbinfo);//保存库存
			this.saveOrUpdate(order);//保存配送表
			this.saveOrUpdate(usedstorebalance);//保存暂用表
			this.updateDeliverapplyStateByDelivers(dinfo.getFid(), 3);//修改状态为已分配
			
			if(!"39gW7X9mRcWoSwsNJhU12TfGffw=".equals(supplier.getFid())){//东经不发送消息
			sql = " select us.fuserid,ifnull(u.fname,'') fname from t_bd_usersupplier us inner join t_sys_user u on u.fid=us.fuserid where us.FSUPPLIERID='"+ supplier.getFid() + "' ";
			List<HashMap<String, Object>> usersupplier =QueryBySql(sql);
			sql = "SELECT _custname,_custpdtname,_spec,fcreatorid FROM t_ord_deliverapply_card_mv where fid='"+dlinfo.getFid()+"'";
			HashMap<String, Object> fpdinfo = (HashMap<String, Object>)QueryBySql(sql).get(0);
			for(HashMap<String, Object> userinfo:usersupplier){
				String fcontent = "尊敬的"+userinfo.get("fname")+"用户！客户'"+fpdinfo.get("_custname")+"'已下订单信息(产品:"+fpdinfo.get("_custpdtname")+";规格:"+fpdinfo.get("_spec")+";数量 "+p.getFamount()+"),详细内容请到'我的业务'中查看!";
				HashMap<String, String> map = new HashMap<String, String>();
				map.put("fcontent", fcontent);
				map.put("frecipientid", (String)userinfo.get("fuserid"));
				map.put("ftype", "3");
				map.put("fsender", (String)fpdinfo.get("fcreatorid"));
				SimplemessageDao.MessageProjectEvaluationWithSender(map);
				if(!messages.containsKey(dlinfo.getFcustomerid()+dlinfo.getFsupplierid())){//没有推送过才推送
//					fcontent="["+fpdinfo.get("fcreatorid")+"]收到\""+fpdinfo.get("_custname")+"\"的订单，请查收";
					fcontent="收到\""+fpdinfo.get("_custname")+"\"的订单，请查收";
						JPushUtil.SendPushToAll((String)userinfo.get("fname"),fcontent);
						JPushUtil.SendPushToIOS((String)userinfo.get("fname"),fcontent);
						messages.put(dlinfo.getFcustomerid()+dlinfo.getFsupplierid(), "1");
						
				}
			}
			}
		}
		
		}
		catch(Exception e){
		}
	}
	
	
	/*
	 * 快速下单取消自动生成
	 */
	public  void ExecDeleteCreateDeliverapply(HashMap<String, String> map)  throws Exception 
	{
		String fidcls = map.get("fidcls");
		this.ExecBySql(map.get("productplanSql"));
		this.ExecBySql(map.get("deliverapplySql"));
		
		String sql="select quote(forderid) forderid from t_ord_productplan where fdeliverapplyid in "+fidcls;//获取orderid
		List<HashMap<String, Object>> custList=this.QueryBySql(sql);
		String forderid="";//符合记录fid
		StringBuilder orderids=new StringBuilder();
		for(HashMap<String, Object> m : custList){
			orderids.append(m.get("forderid")+",");
		}
		forderid = orderids.toString();
		forderid="("+forderid.substring(0, forderid.length()-1)+")";
		 if(QueryExistsBySql("select fid from t_ord_deliverorder where (fouted = 1  or fimporteas=1 or fassembleQty>0) and fsaleorderid in"+forderid)){
				throw new DJException("配送信息已发货或已经装配或已导入,不能取消！");
		 }
		 //删除占用表
		 sql = "SELECT fid ID FROM t_inv_storebalance s where  s.fsaleorderid in "+forderid+"";
		 String inCondition = baseSysDao.getCondition(baseSysDao.getFidclsBySql(sql));
		 this.ExecBySql("delete from t_inv_usedstorebalance  WHERE fstorebalanceid "+inCondition);
		 this.ExecBySql("delete from  t_inv_storebalance  WHERE  fsaleorderid in "+forderid);//删除库存表
		 this.ExecBySql("delete from t_ord_deliverorder  WHERE  fsaleorderid in "+forderid);
		 this.ExecBySql("delete from t_ord_productplan  WHERE forderid in "+forderid);
		 this.ExecBySql("delete from t_ord_saleorder  WHERE forderid in "+forderid);
		 this.ExecBySql("delete from t_ord_delivers  WHERE  fsaleorderid in "+forderid);
		 this.ExecBySql("delete from t_ord_deliverratio  WHERE fdeliverappid in "+fidcls);
		 this.ExecBySql("update t_ord_deliverapply set fstate=0 , fiscreate=0 where fid in "+fidcls);
		 this.ExecBySql("delete from t_ord_orderstate where fdeliverapplyid in "+fidcls+" and fstate >0");
		 
		 
	
		  //取消订单推送消息

		sql=" SELECT _custpdtname,_suppliername,_spec,c.famount,ifnull(u.fname,'') fname,us.fuserid,c.fnumber FROM t_ord_deliverapply_card_mv c  LEFT JOIN t_bd_usercustomer us ON c.fcustomerid=us.fcustomerid  INNER JOIN t_sys_user u ON u.fid=us.fuserid WHERE  c.fid IN "+fidcls;
		List<HashMap<String, Object>> appinfo =QueryBySql(sql);
				for(HashMap<String, Object> userinfo:appinfo){
					String fcontent = "尊敬的"+userinfo.get("fname")+"用户！制造商'"+userinfo.get("_suppliername")+"'取消了该订单'"+userinfo.get("fnumber")+"'(产品:"+userinfo.get("_custpdtname")+";规格:"+userinfo.get("_spec")+";数量 "+userinfo.get("famount")+"),详细内容请到'我的订单'中查看!";
					HashMap<String, String> messagemap = new HashMap<String, String>();
					messagemap.put("fcontent", fcontent);
					messagemap.put("frecipientid", (String)userinfo.get("fuserid"));
					messagemap.put("ftype", "3");
					messagemap.put("fsender", map.get("userid"));
					SimplemessageDao.MessageProjectEvaluationWithSender(messagemap);
//					fcontent="["+map.get("userid")+"]\""+map.get("username")+"\"已取消单号为"+userinfo.get("fnumber")+"的订单，请查收";
						fcontent="\""+map.get("username")+"\"已取消单号为"+userinfo.get("fnumber")+"的订单，请查收";
						JPushUtil.SendPushToAll((String)userinfo.get("fname"),fcontent);//极光推送消息
						JPushUtil.SendPushToIOS((String)userinfo.get("fname"),fcontent);
					
				}
			

	}


}
