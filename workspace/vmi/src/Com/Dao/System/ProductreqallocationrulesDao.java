package Com.Dao.System;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;

import Com.Base.Dao.BaseDao;
import Com.Base.Util.DJException;
import Com.Base.Util.ServerContext;
import Com.Entity.System.Productreqallocationrules;
@Service
public class ProductreqallocationrulesDao extends BaseDao implements IProductreqallocationrulesDao {

	
	@Resource
	private ISimplemessageDao simplemessageDao;
	
	@Override
	public HashMap<String, Object> ExecSave(Productreqallocationrules productreqallocationrules) {
		// TODO Auto-generated method stub
		HashMap<String, Object> params = new HashMap<>();
		
		if (productreqallocationrules.getFid().isEmpty()) {
			
			productreqallocationrules.setFid(this.CreateUUid());
			
		}
	
		this.saveOrUpdate(productreqallocationrules);
		
		params.put("success", true);
		return params;
	}

	@Override
	public Productreqallocationrules Query(String fid) {
		// TODO Auto-generated method stub
		return this.getHibernateTemplate().get(
				Productreqallocationrules.class, fid);
	}

	@Override
	public void ExecAllot(String fcustomerid,String userid) {
		// TODO Auto-generated method stub
		
		String sql="select fid,fsupplierid from t_pdt_productreqallocationrules where fcustomerid='"+fcustomerid+"'";
		List<HashMap<String, Object>> rlist=this.QueryBySql(sql);
		if(rlist.size()==1)
		{
			String fsupplierid=rlist.get(0).get("fsupplierid")==null?"":rlist.get(0).get("fsupplierid").toString();
			if(!"".equals(fsupplierid))
			{
			 
			 
			  if("39gW7X9mRcWoSwsNJhU12TfGffw=".equals(fsupplierid))//需求分配给东经时，创建提示消息
			  {
				  sql=" select f.fid,f.fname,c.fname cname from t_ord_firstproductdemand f  inner join t_bd_customer c on c.fid=f.fcustomerid where f.fcustomerid='"+ fcustomerid+"' and f.isfauditor=1 and ifnull(f.falloted,0)=0 ";
				  ExecCreateMessageInfo(sql);
			  }
			  this.ExecBySql("update  t_ord_firstproductdemand  set falloted=1,fallotor='"+userid+"',fallottime=now(),fsupplierid='"+fsupplierid+"',fstate='已分配' where fcustomerid='"+ fcustomerid+"' and isfauditor=1 and ifnull(falloted,0)=0 ");
			}
		}else if(rlist.size()>1){
			throw new DJException("多用户");
		}
		
	}

	@Override
	public void ExecAllot(String fcustomerid,String userid, String fid) {
		// TODO Auto-generated method stub
		
		String sql="select fid,fsupplierid from t_pdt_productreqallocationrules where fcustomerid='"+fcustomerid+"'";
		List<HashMap<String, Object>> rlist=this.QueryBySql(sql);
		if(rlist.size()==1)
		{
			String fsupplierid=rlist.get(0).get("fsupplierid")==null?"":rlist.get(0).get("fsupplierid").toString();
			if(!"".equals(fsupplierid))
			{
				
				  if("39gW7X9mRcWoSwsNJhU12TfGffw=".equals(fsupplierid))//需求分配给东经时，创建提示消息
				  {
					  sql=" select f.fid,f.fname,c.fname cname from t_ord_firstproductdemand f  inner join t_bd_customer c on c.fid=f.fcustomerid where f.fid in ('%s') and f.fcustomerid='"+ fcustomerid+"' and f.isfauditor=1 and ifnull(f.falloted,0)=0 ";
					  sql = String.format(sql, fid);
					ExecCreateMessageInfo(sql);
				  }
				
				String sql1 = "update  t_ord_firstproductdemand  set falloted=1,fallotor='"+userid+"',fallottime=now(),fsupplierid='"+fsupplierid+"',fstate='已分配' where fid in ('%s') and fcustomerid='"+ fcustomerid+"' and isfauditor=1 and ifnull(falloted,0)=0 ";
				
				sql1 = String.format(sql1, fid);
				
			  this.ExecBySql(sql1);
			  
			
			}
		}
		
		
	}

	
	@Override
	public void ExecTraitAllot(HashMap<String,Object> traitinfo,String userid,HashMap<String,Object> addlist,HashMap<String,Object> slist) {
		// TODO Auto-generated method stub
		String insertsql="",insertusql="",appid="",deliversid="",appnumber="",deliverorderid="";
		//要货申请的创建
		insertsql="INSERT INTO t_ord_deliverapply (fid,fcreatorid,fcreatetime,fupdateuserid,fupdatetime,fnumber,fcustomerid,fcusproductid,farrivetime,flinkman,flinkphone,famount,faddress,fdescription,fordered,fordermanid,fordertime,fsaleorderid,fordernumber,forderentryid,fimportEAS,fimportEASuserid,fimportEAStime,fouted,foutorid,fouttime,faddressid,feasdeliverid,";
		insertsql=insertsql+"fistoPlan,fplanTime,fplanNumber,fplanid,falloted,fiscreate,fcusfid,fstate,ftype,ftraitid) VALUES( ";
		appid=CreateUUid();
		insertsql=insertsql+"'"+appid+"',";//<{fid: }>,
		insertsql=insertsql+"'"+userid+"',";//<{fcreatorid: }>,
		insertsql=insertsql+"now(),";//<{fcreatetime: }>,
		insertsql=insertsql+"'"+userid+"',";//<{fupdateuserid: }>,
		insertsql=insertsql+"now(),";//<{fupdatetime: }>,
		appnumber=ServerContext.getNumberHelper().getNumber("t_ord_deliverapply", "Y", 4, false);
		insertsql=insertsql+"'"+appnumber+"',";//<{fnumber: }>,
		insertsql=insertsql+"'"+traitinfo.get("fcustomerid")+"',";//<{fcustomerid: }>,
		insertsql=insertsql+"'4a1f4969-2d87-11e4-bdb9-00ff6b42e1e5',";//<{fcusproductid: }>,
		insertsql=insertsql+"'"+traitinfo.get("foverdate")+"',";//<{farrivetime: }>,
		insertsql=insertsql+"'"+addlist.get("flinkman")+"',";//<{flinkman: }>,
		insertsql=insertsql+"'"+addlist.get("flinkphone")+"',";//<{flinkphone: }>,
		insertsql=insertsql+""+traitinfo.get("frealamount")+",";//<{famount: }>,
		insertsql=insertsql+"'"+addlist.get("faddress")+"',";//<{faddress: }>,
		insertsql=insertsql+"'"+traitinfo.get("fdescription")+"',";//<{fdescription: }>,
		insertsql=insertsql+"1,";//<{fordered: 0}>,
		insertsql=insertsql+"'',";//<{fordermanid: }>,
		insertsql=insertsql+"null,";//<{fordertime: }>,
		insertsql=insertsql+"'',";//<{fsaleorderid: }>,
		insertsql=insertsql+"'',";//<{fordernumber: }>,
		insertsql=insertsql+"'',";//<{forderentryid: }>,
		insertsql=insertsql+"0,";//<{fimportEAS: 0}>,
		insertsql=insertsql+"'',";//<{fimportEASuserid: }>,
		insertsql=insertsql+"null,";//<{fimportEAStime: }>,
		insertsql=insertsql+"0,";//<{fouted: 0}>,
		insertsql=insertsql+"'',";//<{foutorid: }>,
		insertsql=insertsql+"null,";//<{fouttime: }>,
		insertsql=insertsql+"'"+addlist.get("faddressid")+"',";//<{faddressid: }>,
		insertsql=insertsql+"'',";//<{feasdeliverid: }>,
		insertsql=insertsql+"0,";//<{fistoPlan: 0}>,
		insertsql=insertsql+"null,";//<{fplanTime: }>,
		insertsql=insertsql+"'',";//<{fplanNumber: }>,
		insertsql=insertsql+"'',";//<{fplanid: }>,
		insertsql=insertsql+"0,";//<{falloted: 0}>,
		insertsql=insertsql+"1,";//<{fiscreate: 0}>,
		insertsql=insertsql+"'',";//<{fcusfid: }>,
		insertsql=insertsql+"3,";//<{fstate: }>,
		insertsql=insertsql+"0,";//<{ftype: }>,
		insertsql=insertsql+"'"+traitinfo.get("efid")+"')";//<{ftraitid: }>,efid
		ExecBySql(insertsql);
		
		
		
		
		//要货管理
		insertsql="INSERT INTO t_ord_delivers (fid,fcreatorid,fcreatetime,fupdateuserid,fupdatetime,fnumber,fcustomerid,fcusproductid,farrivetime,flinkman,flinkphone,famount,faddress,fdescription,fordered,fordermanid,fordertime,fsaleorderid,fordernumber,forderentryid,fimportEAS,fimportEASuserid,fimportEAStime,fouted,foutorid,fouttime,faddressid,";
		insertsql=insertsql+"falloted,feasdeliverid,fproductid,fapplayid,fapplynumber,fbalanceprice,fbalanceunitprice,fcusfid,ftype,ftraitid) VALUES( ";
		deliversid=CreateUUid();
		insertsql=insertsql+"'"+deliversid+"',";//<{fid: }>,
		insertsql=insertsql+"'"+userid+"',";//<{fcreatorid: }>,
		insertsql=insertsql+"now(),";//<{fcreatetime: }>,
		insertsql=insertsql+"'"+userid+"',";//<{fupdateuserid: }>,
		insertsql=insertsql+"now(),";//<{fupdatetime: }>,
		insertsql=insertsql+"'"+ServerContext.getNumberHelper().getNumber("t_ord_delivers", "DV", 4, false)+"',";//<{fnumber: }>,
		insertsql=insertsql+"'"+traitinfo.get("fcustomerid")+"',";//<{fcustomerid: }>,
		insertsql=insertsql+"'4a1f4969-2d87-11e4-bdb9-00ff6b42e1e5',";//<{fcusproductid: }>,
		insertsql=insertsql+"'"+traitinfo.get("foverdate")+"',";//<{farrivetime: }>,
		insertsql=insertsql+"'"+addlist.get("flinkman")+"',";//<{flinkman: }>,
		insertsql=insertsql+"'"+addlist.get("flinkphone")+"',";//<{flinkphone: }>,
		insertsql=insertsql+""+traitinfo.get("frealamount")+",";//<{famount: }>,
		insertsql=insertsql+"'"+addlist.get("faddress")+"',";//<{faddress: }>,
		insertsql=insertsql+"'"+traitinfo.get("fdescription")+"',";//<{fdescription: }>,
		insertsql=insertsql+"1,";//<{fordered: 0}>,
		insertsql=insertsql+"'',";//<{fordermanid: }>,
		insertsql=insertsql+"null,";//<{fordertime: }>,
		insertsql=insertsql+"'',";//<{fsaleorderid: }>,
		insertsql=insertsql+"'"+slist.get("fnumber")+"',";//<{fordernumber: }>,
		insertsql=insertsql+"'',";//<{forderentryid: }>,
		insertsql=insertsql+"0,";//<{fimportEAS: 0}>,
		insertsql=insertsql+"'',";//<{fimportEASuserid: }>,
		insertsql=insertsql+"null,";//<{fimportEAStime: }>,
		insertsql=insertsql+"0,";//<{fouted: 0}>,
		insertsql=insertsql+"'',";//<{foutorid: }>,
		insertsql=insertsql+"null,";//<{fouttime: }>,
		insertsql=insertsql+"'"+addlist.get("faddressid")+"',";//<{faddressid: }>,
		insertsql=insertsql+"1,";//<{falloted: 0}>,
		insertsql=insertsql+"'',";//<{feasdeliverid: }>,
		insertsql=insertsql+"'769baec7-2d87-11e4-bdb9-00ff6b42e1e5',";//<{fproductid: }>,
		insertsql=insertsql+"'"+appid+"',";//<{fapplayid: }>,
		insertsql=insertsql+"'"+appnumber+"',";//<{fapplynumber: }>,
		insertsql=insertsql+"0,";//fbalanceprice
		insertsql=insertsql+"0,";//fbalanceunitprice
		insertsql=insertsql+"'',";//<{fcusfid: }>,
		insertsql=insertsql+"0,";//<{ftype: }>,
		insertsql=insertsql+"'"+traitinfo.get("efid")+"')";//<{ftraitid: }>,efid
		ExecBySql(insertsql);
		
		//要货申请与要货管理关系创建
		insertsql="INSERT INTO t_ord_deliverratio(fid,fdeliverappid,fdeliverid,fcustid,fdelivernum,fdeliverapplynum ) VALUES(";
		insertsql=insertsql+"'"+CreateUUid()+"',";//<{fid: }>,
		insertsql=insertsql+"'"+appid+"',";//<{fdeliverappid: }>,
		insertsql=insertsql+"'"+deliversid+"',";//<{fdeliverid: }>,
		insertsql=insertsql+"'',";//<{fcustid: }>,
		insertsql=insertsql+"1,";//<{fdelivernum: }>,
		insertsql=insertsql+"1)";//<{fdeliverapplynum: }>,
		ExecBySql(insertsql);
		
		
		
		//配送信息
		insertsql="INSERT INTO t_ord_deliverorder (fid,fcreatorid,fcreatetime,fupdateuserid,fupdatetime,fnumber,fcustomerid,fcusproductid,farrivetime,flinkman,flinkphone,famount,faddress,fdescription,fordered,fordermanid,fordertime,fsaleorderid,fordernumber,forderentryid,fimportEAS,fimportEASuserid,fimportEAStime,fouted,foutorid,fouttime,faddressid,feasdeliverid,";
		insertsql=insertsql+"fistoPlan,fplanTime,fplanNumber,fplanid,falloted,fdeliversID,fsupplierId,fbalanceprice,fpurchaseprice,fbalanceunitprice,fpurchaseunitprice,fproductid,fcusfid,fmatched,faudited,fauditorid,faudittime,fstorebalanceid,ftype,fassembleQty,foutQty,fissync,ftraitid) VALUES( ";
		deliverorderid=CreateUUid();
		insertsql=insertsql+"'"+deliverorderid+"',";//<{fid: }>,
		insertsql=insertsql+"'"+userid+"',";//<{fcreatorid: }>,
		insertsql=insertsql+"now(),";//<{fcreatetime: }>,
		insertsql=insertsql+"'"+userid+"',";//<{fupdateuserid: }>,
		insertsql=insertsql+"now(),";//<{fupdatetime: }>,
		insertsql=insertsql+"'"+ServerContext.getNumberHelper().getNumber("t_ord_deliverorder", "D", 4, false)+"',";//<{fnumber: }>,
		insertsql=insertsql+"'"+traitinfo.get("fcustomerid")+"',";//<{fcustomerid: }>,
		insertsql=insertsql+"'4a1f4969-2d87-11e4-bdb9-00ff6b42e1e5',";//<{fcusproductid: }>,
		insertsql=insertsql+"'"+traitinfo.get("foverdate")+"',";//<{farrivetime: }>,
		insertsql=insertsql+"'"+addlist.get("flinkman")+"',";//<{flinkman: }>,
		insertsql=insertsql+"'"+addlist.get("flinkphone")+"',";//<{flinkphone: }>,
		insertsql=insertsql+""+traitinfo.get("frealamount")+",";//<{famount: }>,
		insertsql=insertsql+"'"+addlist.get("faddress")+"',";//<{faddress: }>,
		insertsql=insertsql+"'"+traitinfo.get("fdescription")+"',";//<{fdescription: }>,
		insertsql=insertsql+"0,";//<{fordered: 0}>,
		insertsql=insertsql+"'',";//<{fordermanid: }>,
		insertsql=insertsql+"null,";//<{fordertime: }>,
		insertsql=insertsql+"'"+slist.get("forderid")+"',";//<{fsaleorderid: }>,
		insertsql=insertsql+"'"+slist.get("fnumber")+"',";//<{fordernumber: }>,
		insertsql=insertsql+"'"+slist.get("forderentryid")+"',";//<{forderentryid: }>,
		insertsql=insertsql+"0,";//<{fimportEAS: 0}>,
		insertsql=insertsql+"'',";//<{fimportEASuserid: }>,
		insertsql=insertsql+"null,";//<{fimportEAStime: }>,
		insertsql=insertsql+"0,";//<{fouted: 0}>,
		insertsql=insertsql+"'',";//<{foutorid: }>,
		insertsql=insertsql+"null,";//<{fouttime: }>,
		insertsql=insertsql+"'"+addlist.get("faddressid")+"',";//<{faddressid: }>,
		insertsql=insertsql+"'',";//<{feasdeliverid: }>,
		insertsql=insertsql+"0,";//<{fistoPlan: 0}>,
		insertsql=insertsql+"null,";//<{fplanTime: }>,
		insertsql=insertsql+"'"+slist.get("fnumber")+"',";//<{fplanNumber: }>,
		insertsql=insertsql+"'"+slist.get("pid")+"',";//<{fplanid: }>,
		insertsql=insertsql+"0,";//<{falloted: 0}>,
		insertsql=insertsql+"'"+deliversid+"',";//<{fdeliversID: }>,
		insertsql=insertsql+"'"+traitinfo.get("fsupplierid")+"',";//<{fsupplierId: }>,
		insertsql=insertsql+"0,";//<{fbalanceprice: 0.0000}>,
		insertsql=insertsql+"0,";//需要考虑通知类型的产品先默认0，后续再修改<{fpurchaseprice: 0.0000}>,
		insertsql=insertsql+"0,";//<{fbalanceunitprice: 0.0000}>,
		insertsql=insertsql+"0,";//需要考虑通知类型的产品先默认0，后续再修改<{fpurchaseunitprice: 0.0000}>,
		insertsql=insertsql+"'769baec7-2d87-11e4-bdb9-00ff6b42e1e5',";//<{fproductid: }>,
		insertsql=insertsql+"'',";//<{fcusfid: }>,
		insertsql=insertsql+"0,";//<{fmatched: }>,
		insertsql=insertsql+"0,";//<{faudited: 0}>,
		insertsql=insertsql+"'',";//<{fauditorid: }>,
		insertsql=insertsql+"null,";//<{faudittime: }>,
		insertsql=insertsql+"'"+slist.get("sid")+"',";//<{fstorebalanceid: }>
		insertsql=insertsql+"0,";//<{ftype: }>
		insertsql=insertsql+"0,";//<{fassembleQty: }>
		insertsql=insertsql+"0,";//<{foutQty: }>
		insertsql=insertsql+"0,";//<{fissync: }>
		insertsql=insertsql+"'"+traitinfo.get("efid")+"')";//<{ftraitid: }>
		ExecBySql(insertsql);
		
		
		//新增占用量数据
		insertusql="INSERT INTO t_inv_usedstorebalance (fid,fratio,fusedqty,fdeliverorderid,fstorebalanceid,fproductid,ftype ) VALUES( ";
		insertusql=insertusql+"'"+CreateUUid()+"',";//<{fid: }>,
		insertusql=insertusql+1+",";//<{fratio: }>,
		insertusql=insertusql+traitinfo.get("frealamount").toString();//<{fusedqty: }>,
		insertusql=insertusql+",'"+deliverorderid+"',";//<{fdeliverorderid: }>,????
		insertusql=insertusql+"'"+slist.get("sid")+"',";//<{fstorebalanceid: }>,
		insertusql=insertusql+"'769baec7-2d87-11e4-bdb9-00ff6b42e1e5',";//<{fproductid: }>,
		insertusql=insertusql+"1)";//<{ftype: }>,		
		ExecBySql(insertusql);
		
		if("1".equals(traitinfo.get("eftype").toString())){
		insertsql="update t_ord_schemedesignentry set frealamount=frealamount+"+traitinfo.get("frealamount").toString()+" where fid='"+traitinfo.get("efid").toString()+"'";
		ExecBySql(insertsql);
		}else
		{
			insertsql="update t_ord_schemedesignentry set frealamount=fentryamount,fallot=1 where fid='"+traitinfo.get("efid").toString()+"'";
			ExecBySql(insertsql);
			
			insertsql=" update t_ord_firstproductdemand set fstate='已生成要货' where fid ='"+traitinfo.get("ffirstproductid")+"'";
			ExecBySql(insertsql);
		}		 
		
	}

	@Override
	public void ExecTraitunAllot(List<HashMap<String,Object>> list,int famount,String ftraitid,String ffirstproductid) {
		// TODO Auto-generated method stub
		String deletesql="";
		for(int j=0;j<list.size();j++)
		{
			if(list.get(j).get("fdeliversid")==null||list.get(j).get("fdeliverappid")==null)
			{
				throw new DJException("生成的要货记录不存在，取消失败");
			}
		String deliversid=list.get(j).get("fdeliversid").toString();
		String fdeliverappid=list.get(j).get("fdeliverappid").toString();
	    deletesql="delete from t_inv_usedstorebalance where exists( select fid from t_ord_deliverorder o where t_inv_usedstorebalance.fdeliverorderid=o.fid and o.fdeliversID='"+deliversid+"') ";
		ExecBySql(deletesql);
		deletesql="delete from t_ord_deliverorder where fdeliversID='"+deliversid+"'";
		ExecBySql(deletesql);
		deletesql="delete from t_ord_deliverratio where fdeliverid='"+deliversid+"' and fdeliverappid='"+fdeliverappid+"'";
		ExecBySql(deletesql);
		deletesql="delete from t_ord_delivers where fid='"+deliversid+"'";
		ExecBySql(deletesql);
		deletesql="delete from t_ord_deliverapply where fid='"+fdeliverappid+"'";
		ExecBySql(deletesql);
		}
		if(famount==0)
		{
			deletesql="update t_ord_schemedesignentry set frealamount=0,fallot=0 where fid='"+ftraitid+"'";
			ExecBySql(deletesql);
		}
		else
		{
			deletesql="update t_ord_schemedesignentry set frealamount="+famount+",fallot=0 where fid='"+ftraitid+"'";
			ExecBySql(deletesql);
		}
		
		deletesql="update t_ord_firstproductdemand set fstate='已下单' where fid ='"+ffirstproductid+"'";
		ExecBySql(deletesql);
		
		
	}
	@Override
	public void ExecCreateMessageInfo(String sql)
	{
		List<HashMap<String, Object>> rlist=this.QueryBySql(sql);
		for(int j=0;j<rlist.size();j++)
		{
			String content = "尊敬的徐丽丹用户！客户:"+rlist.get(j).get("cname")+"的'"+rlist.get(j).get("fname")+"'需求已分配给东经，请接收。";
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("fcontent", content);
			map.put("frecipientid","2803d655-55a2-11e4-bdb9-00ff6b42e1e5" );//徐丽丹
			map.put("ftype", "0");//2
			simplemessageDao.MessageProjectEvaluation(map);
		}
	}

}
