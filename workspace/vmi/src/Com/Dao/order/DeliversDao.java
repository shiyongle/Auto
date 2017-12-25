package Com.Dao.order;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.util.StringHelper;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import Com.Base.Dao.BaseDao;
import Com.Base.Util.DJException;
import Com.Base.Util.ServerContext;
import Com.Base.Util.SpringContextUtils;
import Com.Dao.System.IBaseSysDao;
import Com.Entity.Inv.Storebalance;
import Com.Entity.Inv.Usedstorebalance;
import Com.Entity.order.Deliverorder;
import Com.Entity.order.Delivers;
import Com.Entity.order.Saleorder;
@Service("DeliversDao")
public class DeliversDao extends BaseDao implements IDeliversDao {
	private ISaleOrderDao saleOrderDao = (ISaleOrderDao)SpringContextUtils.getBean("saleOrderDao");
	private IDeliverapplyDao deliverapplyDao = (IDeliverapplyDao)SpringContextUtils.getBean("deliverapplyDao");
	@Resource
	private IBaseSysDao baseSysDao;
	@Override
	public HashMap<String, Object> ExecSave(Delivers dlv) {
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
	public Delivers Query(String fid) {
		// TODO Auto-generated method stub
		return this.getHibernateTemplate().get(
				Delivers.class, fid);
	}
	
	@Override
	public void ExecGenerateSQL(ArrayList<String> generateSQL, String cusdeliversID) throws Exception {
		// TODO Auto-generated method stub
		for(int i=0;i<generateSQL.size();i++){
			ExecBySql(generateSQL.get(i));
		}
		ExecBySql("update t_ord_cusdelivers set fisread=1 where fid in " + cusdeliversID);
	}

	@Override
	public void ExecAllot(List<HashMap<String, Object>> deliverslist)
			throws Exception {
		// TODO Auto-generated method stub
		String sql="",bsql="";
		String insertsql="",insertusql="";
		String deliverordernumber="",deliverorderid=""; 
		String deliverordernumbers="";//用于回写要货管理订单号
		BigDecimal storeamt=new BigDecimal("0.0");
		BigDecimal unallotamt =new BigDecimal(deliverslist.get(0).get("famount").toString());
		BigDecimal deliverorderamt=new BigDecimal("0.0");
		
		String fid=deliverslist.get(0).get("fid").toString();
		String createuserid=deliverslist.get(0).get("loginuserid").toString();
		String fnumber=deliverslist.get(0).get("fnumber").toString();
		String fcusproductid=deliverslist.get(0).get("fcusproductid")==null?"":deliverslist.get(0).get("fcusproductid").toString();
		String fproductid=deliverslist.get(0).get("fproductid").toString();
		String fcustomerid=deliverslist.get(0).get("fcustomerid").toString();
		String farrivetime=deliverslist.get(0).get("farrivetime").toString();
		int vmitype=new Integer(deliverslist.get(0).get("vmitype").toString());//安全库存的类型；1 订单；2通知；3；不明确
		String fpcmordernumber = (String)deliverslist.get(0).get("fpcmordernumber");
		if(StringUtils.isEmpty(fpcmordernumber)){
			fpcmordernumber = "";
		}
		if(fproductid==null || StringHelper.isEmpty(fproductid))
		{
			throw new DJException("没有对应的产品");
		}
		if(deliverslist.size()==1)
		{ 
			if(deliverslist.get(0).get("fsupplierid")==null || StringHelper.isEmpty(deliverslist.get(0).get("fsupplierid").toString()))
			{
				throw new DJException("该产品没有设置制造商配额");
			}
			if(vmitype==3)
			{
				sql = " select b.fid from t_pdt_productdefproducts s "
						+ " left join t_inv_storebalance b on b.fproductid=s.fproductid "
						+ " where   s.fproductid='" + fproductid
						+ "'  and b.ftype=0 and fsupplierid='"
						+ deliverslist.get(0).get("fsupplierid").toString()
						+ "'";
				List<HashMap<String, Object>> lists = QueryBySql(sql);
				if (lists.size() > 0) {
					vmitype=0;
				} else {
					vmitype=1;
				}

			}
			//订单类型fsupplierid
		    if(vmitype==1)
			{
				//查询可用库存量
		    sql=" SELECT isb.fbalanceqty+isb.fallotqty-IFNULL(s.fusedqty,0) unlockedamt,isb.fid fstorebalanceid,opp.forderid,opp.fnumber AS fordernumber, isb.fsaleorderid,isb.forderentryid,isb.fproductplanID,isb.FSUPPLIERID FROM t_inv_storebalance  isb  "
		    	+" LEFT JOIN (SELECT SUM(e.fusedqty) fusedqty,e.fstorebalanceid FROM t_inv_usedstorebalance e WHERE  e.fusedqty>0  AND e.ftype=1 and e.fproductid='"+fproductid+"' GROUP BY e.fstorebalanceid ) s ON s.fstorebalanceid=isb.fid "
		    	+" LEFT JOIN t_ord_productplan opp ON opp.fid=isb.fproductplanid "
		    	+" WHERE isb.fproductid='"+fproductid+"' AND  isb.fsupplierid='"+deliverslist.get(0).get("fsupplierid").toString()+"' group by isb.fid having unlockedamt>0 order by isb.fcreatetime ";
		    	List<HashMap<String,Object>> storebalancelist= QueryBySql(sql);
				if(storebalancelist.size()<=0)
				{
					throw new DJException("库存不足");
				}
				//有结存数量
				else
				{
					//循环库存并生成配送信息
					for (int i=0;i<storebalancelist.size();i++)
					{
						storeamt=new BigDecimal(storebalancelist.get(i).get("unlockedamt").toString());
						if(unallotamt.compareTo(new BigDecimal("0.0"))>0)
						{
							if(unallotamt.compareTo(storeamt)<=0)
							{
								deliverorderamt=unallotamt;
								unallotamt=new BigDecimal("0.0");
							}
							else
							{
								deliverorderamt=storeamt;
								unallotamt=unallotamt.subtract(storeamt);
							}
							//生成制造商订单配送信息
							deliverorderid=CreateUUid();
							insertsql="INSERT INTO t_ord_deliverorder (fid,fcreatorid,fcreatetime,fupdateuserid,fupdatetime,fnumber,fcustomerid,fcusproductid,farrivetime,flinkman,flinkphone,famount,faddress,fdescription,fordered,fordermanid,fordertime,fsaleorderid,fordernumber,forderentryid,fimportEAS,fimportEASuserid,fimportEAStime,fouted,foutorid,fouttime,faddressid,feasdeliverid,";
							insertsql=insertsql+"fistoPlan,fplanTime,fplanNumber,fplanid,falloted,fdeliversID,fsupplierId,fbalanceprice,fpurchaseprice,fbalanceunitprice,fpurchaseunitprice,fproductid,fcusfid,fmatched,faudited,fauditorid,faudittime,fstorebalanceid,ftype,fpcmordernumber) VALUES( ";
							insertsql=insertsql+"'"+deliverorderid+"',";//<{fid: }>,
							insertsql=insertsql+"'"+createuserid+"',";//<{fcreatorid: }>,
							insertsql=insertsql+"now(),";//<{fcreatetime: }>,
							insertsql=insertsql+"'"+createuserid+"',";//<{fupdateuserid: }>,
							insertsql=insertsql+"now(),";//<{fupdatetime: }>,
							insertsql=insertsql+"'"+ServerContext.getNumberHelper().getNumber("t_ord_deliverorder", "D", 4, false)+"',";//<{fnumber: }>,
							insertsql=insertsql+"'"+fcustomerid+"',";//<{fcustomerid: }>,
							insertsql=insertsql+"'"+fcusproductid+"',";//<{fcusproductid: }>,
							insertsql=insertsql+"'"+farrivetime+"',";//<{farrivetime: }>,
							insertsql=insertsql+"'"+deliverslist.get(0).get("flinkman")+"',";//<{flinkman: }>,
							insertsql=insertsql+"'"+deliverslist.get(0).get("flinkphone")+"',";//<{flinkphone: }>,
							insertsql=insertsql+""+deliverorderamt+",";//<{famount: }>,
							insertsql=insertsql+"'"+deliverslist.get(0).get("faddress")+"',";//<{faddress: }>,
							insertsql=insertsql+"'"+deliverslist.get(0).get("fdescription")+"',";//<{fdescription: }>,
							insertsql=insertsql+"0,";//<{fordered: 0}>,
							insertsql=insertsql+"'',";//<{fordermanid: }>,
							insertsql=insertsql+"null,";//<{fordertime: }>,
							insertsql=insertsql+"'"+storebalancelist.get(i).get("forderid")+"',";//<{fsaleorderid: }>,
							insertsql=insertsql+"'"+storebalancelist.get(i).get("fordernumber")+"',";//<{fordernumber: }>,
							insertsql=insertsql+"'"+storebalancelist.get(i).get("forderentryid")+"',";//<{forderentryid: }>,
							insertsql=insertsql+"0,";//<{fimportEAS: 0}>,
							insertsql=insertsql+"'',";//<{fimportEASuserid: }>,
							insertsql=insertsql+"null,";//<{fimportEAStime: }>,
							insertsql=insertsql+"0,";//<{fouted: 0}>,
							insertsql=insertsql+"'',";//<{foutorid: }>,
							insertsql=insertsql+"null,";//<{fouttime: }>,
							insertsql=insertsql+"'"+deliverslist.get(0).get("faddressid")+"',";//<{faddressid: }>,
							insertsql=insertsql+"'',";//<{feasdeliverid: }>,
							insertsql=insertsql+"0,";//<{fistoPlan: 0}>,
							insertsql=insertsql+"null,";//<{fplanTime: }>,
							if(StringHelper.isEmpty(deliverordernumbers))
							{
								deliverordernumbers=(String)storebalancelist.get(i).get("fordernumber");
							}
							else
							{
								deliverordernumbers=deliverordernumbers+","+storebalancelist.get(i).get("fordernumber");
							}
							insertsql=insertsql+"'"+storebalancelist.get(i).get("fordernumber")+"',";//<{fplanNumber: }>,
							insertsql=insertsql+"'"+storebalancelist.get(i).get("fproductplanID")+"',";//<{fplanid: }>,
							insertsql=insertsql+"0,";//<{falloted: 0}>,
							insertsql=insertsql+"'"+fid+"',";//<{fdeliversID: }>,
							insertsql=insertsql+"'"+storebalancelist.get(i).get("FSUPPLIERID")+"',";//<{fsupplierId: }>,
							insertsql=insertsql+""+deliverslist.get(0).get("fbalanceunitprice")+"*"+unallotamt+",";//<{fbalanceprice: 0.0000}>,
							insertsql=insertsql+"0,";//需要考虑通知类型的产品先默认0，后续再修改<{fpurchaseprice: 0.0000}>,
							insertsql=insertsql+""+deliverslist.get(0).get("fbalanceunitprice")+",";//<{fbalanceunitprice: 0.0000}>,
							insertsql=insertsql+"0,";//需要考虑通知类型的产品先默认0，后续再修改<{fpurchaseunitprice: 0.0000}>,
							insertsql=insertsql+"'"+fproductid+"',";//<{fproductid: }>,
							insertsql=insertsql+"'"+deliverslist.get(0).get("fcusfid")+"',";//<{fcusfid: }>,
							insertsql=insertsql+"0,";//<{fmatched: }>,
							insertsql=insertsql+"0,";//<{faudited: 0}>,
							insertsql=insertsql+"'',";//<{fauditorid: }>,
							insertsql=insertsql+"null,";//<{faudittime: }>,
							insertsql=insertsql+"'"+storebalancelist.get(i).get("fstorebalanceid")+"',";//<{fstorebalanceid: }>
							insertsql=insertsql+"'"+deliverslist.get(0).get("dtype").toString()+"',";//<{ftype: }>
							insertsql=insertsql+"'"+fpcmordernumber+"')";//采购订单号
							ExecBySql(insertsql);
							//新增占用量数据
							insertusql="INSERT INTO t_inv_usedstorebalance (fid,fratio,fusedqty,fdeliverorderid,fstorebalanceid,fproductid,ftype ) VALUES( ";
							insertusql=insertusql+"'"+CreateUUid()+"',";//<{fid: }>,
							insertusql=insertusql+1+",";//<{fratio: }>,
							insertusql=insertusql+deliverorderamt;//<{fusedqty: }>,
							insertusql=insertusql+",'"+deliverorderid+"',";//<{fdeliverorderid: }>,????
							insertusql=insertusql+"'"+storebalancelist.get(i).get("fstorebalanceid")+"',";//<{fstorebalanceid: }>,
							insertusql=insertusql+"'"+fproductid+"',";//<{fproductid: }>,
							insertusql=insertusql+"1)";//<{ftype: }>,		
							ExecBySql(insertusql);
						
							
						}
						else
						{
							break;
						}
					}
					//如果库存都分配完了还有未分配的数量表示库存不足
					if(unallotamt.compareTo(new BigDecimal("0.0"))>0)
					{
						throw new DJException("库存不足");
					}
				}
			}
			//通知类型 de 配送信息的 storebalanceid为空
			else if (vmitype==0)
			{
				
				sql="select fid from t_pdt_productdefproducts where fparentid='"+fproductid+"'";
				List<HashMap<String,Object>> productsList= QueryBySql(sql);
				if(productsList.size()>0)//套装产品，有子件
				{
					sql = "select p.fparentid,y.fsupplierid,min((ifnull(s.fbalanceqty,0)+ifnull(s.fallotqty,0)-ifnull(s.lockamt,0))/famount) fbalanceqtys "
							+ "	 from  t_pdt_productdefproducts  p "
							+ "  left join t_bd_productcycle  y on y.fproductdefid=p.fproductid"
							+ "  left join usedbalanceqty_view s on s.fproductid=y.fproductdefid and y.fsupplierid=s.fsupplierid"
							+ "	 where y.fsupplierid='"
							+ deliverslist.get(0).get("fsupplierid").toString()
							+ " 'and p.fparentid='"
							+ fproductid
							+ " '"
							+ "	group by p.fparentid,y.fsupplierid  ";
//					sql = "select p.fparentid,min((ifnull(aq.fbalanceqty,0)-ifnull(aq.lockamt,0))/famount) fbalanceqtys from  t_pdt_productdefproducts  p left join ("
//							+ " select p.fproductid,ifnull(sbl.fbalanceqty,0) as fbalanceqty,ifnull(sum(odo.fusedqty),0) lockamt"
//							+ " from  t_pdt_productdefproducts  p "
//							+ " left join  t_inv_storebalance sbl on sbl.fproductid=p.fproductid"
//							+ " left join t_inv_usedstorebalance  odo  on sbl.fid =odo.fstorebalanceid and p.fproductid=odo.fproductid"
//							+ " and odo.fusedqty>0 and odo.ftype=0"
//							+ " where sbl.ftype=0 and sbl.fbalanceqty>0 and sbl.fsupplierid='"
//							+ deliverslist.get(0).get("fsupplierid").toString()
//							+ "' and p.fparentid='"
//							+ fproductid
//							+ "'"
//							+ " group by p.fproductid,sbl.fbalanceqty)"
//							+ " aq on aq.fproductid=p.fproductid "
//							+ " where  p.fparentid='"
//							+ fproductid
//							+ "'"
//							+ " group by p.fparentid ";	
					
					bsql="select p.fproductid,p.famount as ratio,ifnull(sbl.fbalanceqty+sbl.fallotqty,0) as fbalanceqty,sbl.fid as fstorebalanceid,sbl.fsupplierid as FSUPPLIERID "
							+" from  t_pdt_productdefproducts  p "
							+" left join  t_inv_storebalance sbl on sbl.fproductid=p.fproductid"
							+" where p.fparentid='"+fproductid+"' and sbl.ftype=0 and  sbl.fsupplierid='"+deliverslist.get(0).get("fsupplierid").toString()+"' and (sbl.fbalanceqty+sbl.fallotqty)>0";
												
					
				}else//普通没有子件
				{
					sql = "select fproductid,ifnull(fbalanceqty-lockamt,0)  fbalanceqtys from ( "
							+ " select sbl.fproductid,ifnull(sbl.fbalanceqty+sbl.fallotqty,0) as fbalanceqty,ifnull(sum(odo.fusedqty),0) lockamt "
							+ " from  t_inv_storebalance sbl "
							+ " left join t_inv_usedstorebalance  odo  on sbl.fid =odo.fstorebalanceid and sbl.fproductid=odo.fproductid "
							+ " and odo.fusedqty>0 and odo.ftype=0 "
							+ " where sbl.ftype=0 and (sbl.fbalanceqty+sbl.fallotqty)>0 and sbl.fsupplierid='"+deliverslist.get(0).get("fsupplierid").toString()+"' and sbl.fproductid='"+fproductid+"'"
							+ " group by sbl.fproductid,sbl.fbalanceqty ) aq";
				
					bsql="select sbl.fproductid,'1' as ratio,ifnull(sbl.fbalanceqty+sbl.fallotqty,0) as fbalanceqty,sbl.fid as fstorebalanceid,sbl.fsupplierid as FSUPPLIERID from  t_inv_storebalance sbl where sbl.fproductid='"+fproductid+"' and sbl.ftype=0 and  sbl.fsupplierid='"+deliverslist.get(0).get("fsupplierid").toString()+"' and (fbalanceqty+fallotqty)>0";
				
				}
				List<HashMap<String,Object>> balancelist=QueryBySql(sql);
				if(balancelist.size()<=0)
				{
					throw new DJException("库存不足");
				}
				else if(balancelist.size()==1)
				{
					storeamt=new BigDecimal(balancelist.get(0).get("fbalanceqtys").toString());
					if(unallotamt.compareTo(storeamt)<=0)
					{
						List<HashMap<String,Object>> storebalancelist=QueryBySql(bsql);
						if(storebalancelist.size()==0)
						{
							throw new DJException("库存不足");
						}
						//生成通知类型配送信息
						deliverorderid=CreateUUid();
						insertsql="INSERT INTO t_ord_deliverorder (fid,fcreatorid,fcreatetime,fupdateuserid,fupdatetime,fnumber,fcustomerid,fcusproductid,farrivetime,flinkman,flinkphone,famount,faddress,fdescription,fordered,fordermanid,fordertime,fsaleorderid,fordernumber,forderentryid,fimportEAS,fimportEASuserid,fimportEAStime,fouted,foutorid,fouttime,faddressid,feasdeliverid,";
						insertsql=insertsql+"fistoPlan,fplanTime,fplanNumber,fplanid,falloted,fdeliversID,fsupplierId,fbalanceprice,fpurchaseprice,fbalanceunitprice,fpurchaseunitprice,fproductid,fcusfid,fmatched,faudited,fauditorid,faudittime,fstorebalanceid,ftype,fpcmordernumber) VALUES( ";
						insertsql=insertsql+"'"+deliverorderid+"',";//<{fid: }>,
						insertsql=insertsql+"'"+createuserid+"',";//<{fcreatorid: }>,
						insertsql=insertsql+"now(),";//<{fcreatetime: }>,
						insertsql=insertsql+"'"+createuserid+"',";//<{fupdateuserid: }>,
						insertsql=insertsql+"now(),";//<{fupdatetime: }>,
						deliverordernumber=ServerContext.getNumberHelper().getNumber("t_ord_deliverorder", "D", 4, false);
						if(StringHelper.isEmpty(deliverordernumbers))
						{
							deliverordernumbers=deliverordernumber;
						}
						else
						{
							deliverordernumbers=deliverordernumbers+","+deliverordernumber;
						}
						insertsql=insertsql+"'"+deliverordernumber+"',";//<{fnumber: }>,
						insertsql=insertsql+"'"+fcustomerid+"',";//<{fcustomerid: }>,
						insertsql=insertsql+"'"+fcusproductid+"',";//<{fcusproductid: }>,
						insertsql=insertsql+"'"+farrivetime+"',";//<{farrivetime: }>,
						insertsql=insertsql+"'"+deliverslist.get(0).get("flinkman")+"',";//<{flinkman: }>,
						insertsql=insertsql+"'"+deliverslist.get(0).get("flinkphone")+"',";//<{flinkphone: }>,
						insertsql=insertsql+""+unallotamt+",";//<{famount: }>,
						insertsql=insertsql+"'"+deliverslist.get(0).get("faddress")+"',";//<{faddress: }>,
						insertsql=insertsql+"'"+deliverslist.get(0).get("fdescription")+"',";//<{fdescription: }>,
						insertsql=insertsql+"0,";//<{fordered: 0}>,
						insertsql=insertsql+"'',";//<{fordermanid: }>,
						insertsql=insertsql+"null,";//<{fordertime: }>,
						insertsql=insertsql+"'',";//<{fsaleorderid: }>,
						insertsql=insertsql+"'"+fnumber+"',";//<{fordernumber: }>,
						insertsql=insertsql+"'',";//<{forderentryid: }>,
						insertsql=insertsql+"0,";//<{fimportEAS: 0}>,
						insertsql=insertsql+"'',";//<{fimportEASuserid: }>,
						insertsql=insertsql+"null,";//<{fimportEAStime: }>,
						insertsql=insertsql+"0,";//<{fouted: 0}>,
						insertsql=insertsql+"'',";//<{foutorid: }>,
						insertsql=insertsql+"null,";//<{fouttime: }>,
						insertsql=insertsql+"'"+deliverslist.get(0).get("faddressid")+"',";//<{faddressid: }>,
						insertsql=insertsql+"'',";//<{feasdeliverid: }>,
						insertsql=insertsql+"0,";//<{fistoPlan: 0}>,
						insertsql=insertsql+"null,";//<{fplanTime: }>,
						insertsql=insertsql+"'"+fnumber+"',";//<{fplanNumber: }>,
						insertsql=insertsql+"'',";//<{fplanid: }>,
						insertsql=insertsql+"0,";//<{falloted: 0}>,
						insertsql=insertsql+"'"+fid+"',";//<{fdeliversID: }>,
						insertsql=insertsql+"'"+storebalancelist.get(0).get("FSUPPLIERID")+"',";//<{fsupplierId: }>,
						insertsql=insertsql+""+deliverslist.get(0).get("fbalanceunitprice")+"*"+unallotamt+",";//<{fbalanceprice: 0.0000}>,
						insertsql=insertsql+"0,";//需要考虑通知类型的产品先默认0，后续再修改<{fpurchaseprice: 0.0000}>,
						insertsql=insertsql+""+deliverslist.get(0).get("fbalanceunitprice")+",";//<{fbalanceunitprice: 0.0000}>,
						insertsql=insertsql+"0,";//需要考虑通知类型的产品先默认0，后续再修改<{fpurchaseunitprice: 0.0000}>,
						insertsql=insertsql+"'"+fproductid+"',";//<{fproductid: }>,
						insertsql=insertsql+"'"+deliverslist.get(0).get("fcusfid")+"',";//<{fcusfid: }>,
						insertsql=insertsql+"0,";//<{fmatched: }>,
						insertsql=insertsql+"0,";//<{faudited: 0}>,
						insertsql=insertsql+"'',";//<{fauditorid: }>,
						insertsql=insertsql+"null,";//<{faudittime: }>,
						insertsql=insertsql+"'',";//<{fstorebalanceid: }>
						insertsql=insertsql+"'"+deliverslist.get(0).get("dtype").toString()+"',";//<{ftype: }>
						insertsql=insertsql+"'"+fpcmordernumber+"')";//采购订单号
						ExecBySql(insertsql);
						//新增库存占用量表
						for(int m=0;m<storebalancelist.size();m++)
						{
							insertusql="INSERT INTO t_inv_usedstorebalance (fid,fratio,fusedqty,fdeliverorderid,fstorebalanceid,fproductid,ftype ) VALUES( ";
							insertusql=insertusql+"'"+CreateUUid()+"',";//<{fid: }>,
							insertusql=insertusql+new BigDecimal(storebalancelist.get(m).get("ratio").toString())+",";//<{fratio: }>,
							insertusql=insertusql+new BigDecimal(storebalancelist.get(m).get("ratio").toString()).multiply(unallotamt);//<{fusedqty: }>,
							insertusql=insertusql+",'"+deliverorderid+"',";//<{fdeliverorderid: }>,????
							insertusql=insertusql+"'"+storebalancelist.get(m).get("fstorebalanceid")+"',";//<{fstorebalanceid: }>,
							insertusql=insertusql+"'"+storebalancelist.get(m).get("fproductid")+"',";//<{fproductid: }>,
							insertusql=insertusql+"0)";//<{ftype: }>,		
							ExecBySql(insertusql);
						}
						
						
					}
					else
					{
						throw new DJException("库存不足");
					}	
				}
				else
				{
					throw new DJException("库存不足");
				}		
				
				//查询可用库存量
			/*	sql="select isb.fid as fstorebalanceid,isb.fbalanceqty-ifnull(sum(odo.famount),0) unlockedamt,isb.fsaleorderid,isb.forderentryid,isb.fproductplanID,isb.FSUPPLIERID from t_inv_storebalance isb left join t_ord_deliverorder odo on isb.fid=fstorebalanceid and odo.fouted=0   and odo.ftype<>1 ";
				sql=sql+" where isb.FSUPPLIERID='"+deliverslist.get(0).get("fsupplierid").toString()+"' and isb.fProductID='"+fproductid+"' and isb.FTYPE=0  group by isb.fid,isb.fbalanceqty  having unlockedamt>0 order by isb.fcreatetime ";
				List<HashMap<String,Object>> storebalancelist= QueryBySql(sql);
				if(storebalancelist.size()<=0)
				{
					throw new DJException("库存不足");
				}
				//只有条才正确
				else if (storebalancelist.size()==1)
				{
					storeamt=new BigDecimal(storebalancelist.get(0).get("unlockedamt").toString());
					if(unallotamt.compareTo(storeamt)<=0)
					{
						//生成通知类型配送信息
						insertsql="INSERT INTO t_ord_deliverorder (fid,fcreatorid,fcreatetime,fupdateuserid,fupdatetime,fnumber,fcustomerid,fcusproductid,farrivetime,flinkman,flinkphone,famount,faddress,fdescription,fordered,fordermanid,fordertime,fsaleorderid,fordernumber,forderentryid,fimportEAS,fimportEASuserid,fimportEAStime,fouted,foutorid,fouttime,faddressid,feasdeliverid,";
						insertsql=insertsql+"fistoPlan,fplanTime,fplanNumber,fplanid,falloted,fdeliversID,fsupplierId,fbalanceprice,fpurchaseprice,fbalanceunitprice,fpurchaseunitprice,fproductid,fcusfid,fmatched,faudited,fauditorid,faudittime,fstorebalanceid,ftype) VALUES( ";
						insertsql=insertsql+"'"+CreateUUid()+"',";//<{fid: }>,
						insertsql=insertsql+"'"+createuserid+"',";//<{fcreatorid: }>,
						insertsql=insertsql+"now(),";//<{fcreatetime: }>,
						insertsql=insertsql+"'"+createuserid+"',";//<{fupdateuserid: }>,
						insertsql=insertsql+"now(),";//<{fupdatetime: }>,
						deliverordernumber=ServerContext.getNumberHelper().getNumber("t_ord_deliverorder", "D", 4, false);
						if(StringHelper.isEmpty(deliverordernumbers))
						{
							deliverordernumbers=deliverordernumber;
						}
						else
						{
							deliverordernumbers=deliverordernumbers+","+deliverordernumber;
						}
						insertsql=insertsql+"'"+deliverordernumber+"',";//<{fnumber: }>,
						insertsql=insertsql+"'"+fcustomerid+"',";//<{fcustomerid: }>,
						insertsql=insertsql+"'"+fcusproductid+"',";//<{fcusproductid: }>,
						insertsql=insertsql+"'"+farrivetime+"',";//<{farrivetime: }>,
						insertsql=insertsql+"'"+deliverslist.get(0).get("flinkman")+"',";//<{flinkman: }>,
						insertsql=insertsql+"'"+deliverslist.get(0).get("flinkphone")+"',";//<{flinkphone: }>,
						insertsql=insertsql+""+unallotamt+",";//<{famount: }>,
						insertsql=insertsql+"'"+deliverslist.get(0).get("faddress")+"',";//<{faddress: }>,
						insertsql=insertsql+"'"+deliverslist.get(0).get("fdescription")+"',";//<{fdescription: }>,
						insertsql=insertsql+"0,";//<{fordered: 0}>,
						insertsql=insertsql+"'',";//<{fordermanid: }>,
						insertsql=insertsql+"null,";//<{fordertime: }>,
						insertsql=insertsql+"'',";//<{fsaleorderid: }>,
						insertsql=insertsql+"'"+fnumber+"',";//<{fordernumber: }>,
						insertsql=insertsql+"'',";//<{forderentryid: }>,
						insertsql=insertsql+"0,";//<{fimportEAS: 0}>,
						insertsql=insertsql+"'',";//<{fimportEASuserid: }>,
						insertsql=insertsql+"null,";//<{fimportEAStime: }>,
						insertsql=insertsql+"0,";//<{fouted: 0}>,
						insertsql=insertsql+"'',";//<{foutorid: }>,
						insertsql=insertsql+"null,";//<{fouttime: }>,
						insertsql=insertsql+"'"+deliverslist.get(0).get("faddressid")+"',";//<{faddressid: }>,
						insertsql=insertsql+"'',";//<{feasdeliverid: }>,
						insertsql=insertsql+"0,";//<{fistoPlan: 0}>,
						insertsql=insertsql+"null,";//<{fplanTime: }>,
						insertsql=insertsql+"'"+fnumber+"',";//<{fplanNumber: }>,
						insertsql=insertsql+"'',";//<{fplanid: }>,
						insertsql=insertsql+"0,";//<{falloted: 0}>,
						insertsql=insertsql+"'"+fid+"',";//<{fdeliversID: }>,
						insertsql=insertsql+"'"+storebalancelist.get(0).get("FSUPPLIERID")+"',";//<{fsupplierId: }>,
						insertsql=insertsql+""+deliverslist.get(0).get("fbalanceunitprice")+"*"+unallotamt+",";//<{fbalanceprice: 0.0000}>,
						insertsql=insertsql+"0,";//需要考虑通知类型的产品先默认0，后续再修改<{fpurchaseprice: 0.0000}>,
						insertsql=insertsql+""+deliverslist.get(0).get("fbalanceunitprice")+",";//<{fbalanceunitprice: 0.0000}>,
						insertsql=insertsql+"0,";//需要考虑通知类型的产品先默认0，后续再修改<{fpurchaseunitprice: 0.0000}>,
						insertsql=insertsql+"'"+fproductid+"',";//<{fproductid: }>,
						insertsql=insertsql+"'"+deliverslist.get(0).get("fcusfid")+"',";//<{fcusfid: }>,
						insertsql=insertsql+"0,";//<{fmatched: }>,
						insertsql=insertsql+"0,";//<{faudited: 0}>,
						insertsql=insertsql+"'',";//<{fauditorid: }>,
						insertsql=insertsql+"null,";//<{faudittime: }>,
						insertsql=insertsql+"'"+storebalancelist.get(0).get("fstorebalanceid")+"',";//<{fstorebalanceid: }>
						insertsql=insertsql+"'"+deliverslist.get(0).get("dtype").toString()+"')";//<{ftype: }>
						ExecBySql(insertsql);
					}
					else
					{
						throw new DJException("库存不足");
					}
				}
				else
				{
					throw new DJException("存在多条通知类型的结存数据");
				}*/
			}else
			{
				throw new DJException("未知的下单类型");
			}
			
		}
		else
		{
			throw new DJException("多家制造商制造,请手动分配");
		}
		//最后修改要货管理标志
		ExecBySql("update t_ord_delivers set fordernumber='"+deliverordernumbers+"',Falloted=1,Fordered=1 where fid = '"+fid+"'");
		
		//要货管理分配同时更新要货申请状态;
		deliverapplyDao.updateDeliverapplyStateByDelivers(fid, 3);
		
	}

	
	@Override
	public void ExecAssginAllot(Delivers delivers, List<Storebalance> storebalances)
			throws Exception {
		
		String sql="";
		String insertsql="",insertusql="";
		//deliverordernumber 配送信息编码，deliverordernumbers 生成配送后的 订单编码（反写到要货管理）；ordernumber订单编码
		String deliverordernumber="",deliverordernumbers="",ordernumber="",deliverorderid; 
		String fissuit="0";//fissuit是否是套装;fftype通知还是订单类型
		int fftype=0;//fftype通知还是订单类型
		int type=0;//下单类型
		String fpcmordernumber = delivers.getFpcmordernumber();
		if(StringHelper.isEmpty(fpcmordernumber)){
			fpcmordernumber = "";
		}
		Storebalance storebalance=null;
		for(int i=0;i<storebalances.size();i++)
		{
			storebalance=storebalances.get(i);
			fissuit=storebalance.getFupdateuserid();//是否套装通过Fupdateuserid传递值
			fftype=storebalance.getFtype();
	
		
			//确定配送信息的订单编号
			if(StringHelper.isEmpty(storebalance.getFcreatorid()))//结存表的下单类型为通知getFcreatorid 实质存的是 制造商编码
			{
				ordernumber=delivers.getFnumber();//为要货管理的编码
			}else
			{
				ordernumber=storebalance.getFcreatorid();//制造商编码
				type=1;//订单类型
			}
			deliverorderid=CreateUUid();
			insertsql="INSERT INTO t_ord_deliverorder (fid,fcreatorid,fcreatetime,fupdateuserid,fupdatetime,fnumber,fcustomerid,fcusproductid,farrivetime,flinkman,flinkphone,famount,faddress,fdescription,fordered,fordermanid,fordertime,fsaleorderid,fordernumber,forderentryid,fimportEAS,fimportEASuserid,fimportEAStime,fouted,foutorid,fouttime,faddressid,feasdeliverid,";
			insertsql=insertsql+"fistoPlan,fplanTime,fplanNumber,fplanid,falloted,fdeliversID,fsupplierId,fbalanceprice,fpurchaseprice,fbalanceunitprice,fpurchaseunitprice,fproductid,fcusfid,fmatched,faudited,fauditorid,faudittime,fstorebalanceid,ftype,fpcmordernumber) VALUES( ";
			insertsql=insertsql+"'"+deliverorderid+"',";//<{fid: }>,
			insertsql=insertsql+"'"+delivers.getFcreatorid()+"',";//<{fcreatorid: }>,
			insertsql=insertsql+"now(),";//<{fcreatetime: }>,
			insertsql=insertsql+"'"+delivers.getFcreatorid()+"',";//<{fupdateuserid: }>,
			insertsql=insertsql+"now(),";//<{fupdatetime: }>,
			deliverordernumber=ServerContext.getNumberHelper().getNumber("t_ord_deliverorder", "D", 4, false);
			insertsql=insertsql+"'"+deliverordernumber+"',";//<{fnumber: }>,
			//要货管理的 订单bian'hao
			if(StringHelper.isEmpty(deliverordernumbers))
			{
				deliverordernumbers=type==1?ordernumber:deliverordernumber;
			}
			else
			{
				deliverordernumbers=deliverordernumbers+","+(type==1?ordernumber:deliverordernumber);
			}
			
			insertsql=insertsql+"'"+delivers.getFcustomerid()+"',";//<{fcustomerid: }>,
			insertsql=insertsql+"'"+delivers.getFcusproductid()+"',";//<{fcusproductid: }>,
			insertsql=insertsql+"'"+delivers.getFarrivetime()+"',";//<{farrivetime: }>,
			insertsql=insertsql+"'"+delivers.getFlinkman()+"',";//<{flinkman: }>,
			insertsql=insertsql+"'"+delivers.getFlinkphone()+"',";//<{flinkphone: }>,
			insertsql=insertsql+""+storebalance.getFbalanceqty()+",";//<{famount: }>,
			insertsql=insertsql+"'"+delivers.getFaddress()+"',";//<{faddress: }>,
			insertsql=insertsql+"'"+delivers.getFdescription()+"',";//<{fdescription: }>,
			insertsql=insertsql+"0,";//<{fordered: 0}>,
			insertsql=insertsql+"'',";//<{fordermanid: }>,
			insertsql=insertsql+"null,";//<{fordertime: }>,
			insertsql=insertsql+"'"+(type==0?"":storebalance.getFsaleorderid())+"',";//<{fsaleorderid: }>,
			insertsql=insertsql+"'"+ordernumber+"',";//<{fordernumber: }>,
			insertsql=insertsql+"'"+(type==0?"":storebalance.getForderentryid())+"',";//<{forderentryid: }>,
			insertsql=insertsql+"0,";//<{fimportEAS: 0}>,
			insertsql=insertsql+"'',";//<{fimportEASuserid: }>,
			insertsql=insertsql+"null,";//<{fimportEAStime: }>,
			insertsql=insertsql+"0,";//<{fouted: 0}>,
			insertsql=insertsql+"'',";//<{foutorid: }>,
			insertsql=insertsql+"null,";//<{fouttime: }>,
			insertsql=insertsql+"'"+delivers.getFaddressid()+"',";//<{faddressid: }>,
			insertsql=insertsql+"'',";//<{feasdeliverid: }>,
			insertsql=insertsql+"0,";//<{fistoPlan: 0}>,
			insertsql=insertsql+"null,";//<{fplanTime: }>,
			insertsql=insertsql+"'"+ordernumber+"',";//<{fplanNumber: }>,
			insertsql=insertsql+"'"+(type==0?"":storebalance.getFproductplanId())+"',";//<{fplanid: }>,
			insertsql=insertsql+"0,";//<{falloted: 0}>,
			insertsql=insertsql+"'"+delivers.getFid()+"',";//<{fdeliversID: }>,
			insertsql=insertsql+"'"+storebalance.getFsupplierID()+"',";//<{fsupplierId: }>,
			insertsql=insertsql+""+delivers.getFbalanceunitprice()+"*"+storebalance.getFbalanceqty()+",";//<{fbalanceprice: 0.0000}>,
			insertsql=insertsql+"0,";//需要考虑通知类型的产品先默认0，后续再修改<{fpurchaseprice: 0.0000}>,
			insertsql=insertsql+""+delivers.getFbalanceunitprice()+",";//<{fbalanceunitprice: 0.0000}>,
			insertsql=insertsql+"0,";//需要考虑通知类型的产品先默认0，后续再修改<{fpurchaseunitprice: 0.0000}>,
			insertsql=insertsql+"'"+delivers.getFproductid()+"',";//<{fproductid: }>,
			insertsql=insertsql+"'"+delivers.getFcusfid()+"',";//<{fcusfid: }>,
			insertsql=insertsql+"0,";//<{fmatched: }>,
			insertsql=insertsql+"0,";//<{faudited: 0}>,
			insertsql=insertsql+"'',";//<{fauditorid: }>,
			insertsql=insertsql+"null,";//<{faudittime: }>,
			insertsql=insertsql+"'"+("1".equals(delivers.getFtype())?"":(fftype==1?storebalance.getFid():""))+"',";//<{fstorebalanceid: }>
			insertsql=insertsql+"'"+delivers.getFtype()+"',";//<{ftype: }>
			insertsql=insertsql+"'"+fpcmordernumber+"')";//采购订单号
			ExecBySql(insertsql);
			//补单类型不能生成占用表
			if(!"1".equals(delivers.getFtype())){
			if(fftype==1||"0".equals(fissuit))
			{
				insertusql="INSERT INTO t_inv_usedstorebalance (fid,fratio,fusedqty,fdeliverorderid,fstorebalanceid,fproductid,ftype ) VALUES( ";
				insertusql=insertusql+"'"+CreateUUid()+"',";//<{fid: }>,
				insertusql=insertusql+1+",";//<{fratio: }>,
				insertusql=insertusql+storebalance.getFbalanceqty();//<{fusedqty: }>,
				insertusql=insertusql+",'"+deliverorderid+"',";//<{fdeliverorderid: }>,????
				insertusql=insertusql+"'"+storebalance.getFid()+"',";//<{fstorebalanceid: }>,
				insertusql=insertusql+"'"+delivers.getFproductid()+"',";//<{fproductid: }>,
				insertusql=insertusql+fftype+")";//<{ftype: }>,		
				ExecBySql(insertusql);
			}
			else if("1".equals(fissuit)&&fftype==0)
			{
				
				sql="select p.fproductid,sbl.fid as fstorebalanceid"
					+" from  t_pdt_productdefproducts  p "
					+" left join  t_inv_storebalance sbl on sbl.fproductid=p.fproductid and sbl.ftype=0 and  sbl.fsupplierid='"+storebalance.getFsupplierID()+"'"
					+" where p.fparentid='"+delivers.getFproductid()+"' and ifnull(sbl.fid,'')=''";
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
						+ delivers.getFproductid()
						+ "' ";
				storeslist = QueryBySql(sql);
			
			//新增库存占用量表
			for(int m=0;m<storeslist.size();m++)
			{
				insertusql="INSERT INTO t_inv_usedstorebalance (fid,fratio,fusedqty,fdeliverorderid,fstorebalanceid,fproductid,ftype ) VALUES( ";
				insertusql=insertusql+"'"+CreateUUid()+"',";//<{fid: }>,
				insertusql=insertusql+new BigDecimal(storeslist.get(m).get("ratio").toString())+",";//<{fratio: }>,
				insertusql=insertusql+new BigDecimal(storeslist.get(m).get("ratio").toString()).multiply(new BigDecimal(storebalance.getFbalanceqty()));//<{fusedqty: }>,
				insertusql=insertusql+",'"+deliverorderid+"',";//<{fdeliverorderid: }>,????
				insertusql=insertusql+"'"+storeslist.get(m).get("fstorebalanceid")+"',";//<{fstorebalanceid: }>,
				insertusql=insertusql+"'"+storeslist.get(m).get("fproductid")+"',";//<{fproductid: }>,
				insertusql=insertusql+"0)";//<{ftype: }>,		
				ExecBySql(insertusql);
			}
			}
			}
			
		}
		
		//最后修改要货管理标志
		ExecBySql("update t_ord_delivers set fordernumber='"+deliverordernumbers+"',Falloted=1,Fordered=1 where fid = '"+delivers.getFid()+"'");
		
		//要货管理指定订单修改要货申请为已分配状态;
		deliverapplyDao.updateDeliverapplyStateByDelivers(delivers.getFid(), 3);
	}
	
	@Override
	public void ExecUnAllot( String fidcls ) throws Exception {

//		ExecBySql("set SQL_SAFE_UPDATES = 0 ");
		String sql = "select fid ID from t_ord_deliverorder where  fdeliversId in (" + fidcls +") and fouted = 0";
		String inCondition = baseSysDao.getCondition(baseSysDao.getFidclsBySql(sql));
		ExecBySql("delete from t_inv_usedstorebalance where fdeliverorderid "+inCondition);

		
		sql = "Delete FROM t_ord_deliverorder where fouted = 0 and fdeliversId in (" + fidcls +")";
		ExecBySql(sql);
		
		
		ExecBySql("update t_ord_delivers set falloted = 0,Fordered=0,fordernumber='' where fid in (" + fidcls +")");
		//删除占用表信息
		
//		ExecBySql("delete  from  t_inv_usedstorebalance where fdeliverorderid")


		List<HashMap<String,Object>> deliverslist = QueryBySql("select fid,forderentryid from t_ord_delivers where fid in (" + fidcls +")");
		for(int j=0;j<deliverslist.size();j++){
			int fstate = 1;
			if(deliverslist.get(j).get("forderentryid")!=null && !deliverslist.get(j).get("forderentryid").toString().equals("")){
					fstate=2;
			}
			deliverapplyDao.updateDeliverapplyStateByDelivers(deliverslist.get(j).get("fid").toString(), fstate,false);
		}
	}

	//要货管理下单;
	@Override
	public HashMap<String, Object> ExecVMIorder(ArrayList<Saleorder> solist,
			String ordersql, String deliverid) throws Exception {
		// TODO Auto-generated method stub
		HashMap<String, Object> params = new HashMap<>();
		
		
		
//		for(int i=0;i<solist.size();i++){
//			saleOrderDao.ExecSave(solist.get(i));
//		}
		
		if(ordersql.length()>0){
			ExecBySql(ordersql);
		}
		//生成生产订单，（单个制造商，则生成制造商订单，库存信息）
		HashMap map=saleOrderDao.ExecProductPlanAndStorebalanceBySingel(solist);
		
		if(map!=null&&map.containsKey("sbinfo"))//生成库存信息，反写要货申请状态为已分配,库存表存制造商订单编号于备注中
		{
			createDeliverorder(deliverid,(Storebalance)map.get("sbinfo"),map.get("fnumber").toString());
		}else //没有生成库存信息，反写要货申请状态为已生产
		{
			List<HashMap<String,Object>> deliverslist = QueryBySql("select fid from t_ord_delivers where fid in (" + deliverid +")");
			for(int j=0;j<deliverslist.size();j++){
				deliverapplyDao.updateDeliverapplyStateByDelivers(deliverslist.get(j).get("fid").toString(), 2);
		}		
		}
		params.put("success", true);
		return params;
	}
	
	
	/**
	 * 根据要货管理id，结存信息，制造商编号生成配送信息
	 * @param fdeliverid
	 * @param sbinfo 
	 * @param planfnumber
	 */
	private void createDeliverorder(String fdeliverid,Storebalance sbinfo,String planfnumber)
	{
		Delivers deliverinfo=null;
		List<Delivers> deliverslist = QueryByHql("select t from Delivers t where t.fid in (" + fdeliverid +")");
		for(int i=0;i<deliverslist.size();i++)
		{
		 deliverinfo=deliverslist.get(i);
		//创建配送信息
		 Deliverorder dinfo  = new Deliverorder();
		dinfo.setFid(CreateUUid());
		dinfo.setFcreatorid(sbinfo.getFcreatorid());
		dinfo.setFcreatetime(new Date());
		dinfo.setFupdatetime(new Date());
		dinfo.setFupdateuserid(sbinfo.getFcreatorid());
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
		dinfo.setFordernumber(planfnumber);
		dinfo.setForderentryid(sbinfo.getForderentryid());
		dinfo.setFimportEas(0);
		dinfo.setFouted(0);
		dinfo.setFplanNumber(planfnumber);
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
		ExecBySql("update t_ord_delivers set  fordernumber='"+planfnumber+"',Falloted=1 where fid = '"+deliverinfo.getFid()+"'");
		}
	}
}
