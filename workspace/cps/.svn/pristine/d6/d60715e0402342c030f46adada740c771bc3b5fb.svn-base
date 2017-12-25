package com.dao.productplan;



import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;

import com.dao.IBaseDaoImpl;
import com.model.PageModel;
import com.model.productplan.ProductPlan;
import com.util.Params;

@Repository("productplanDao")
public class ProductplanDaoImpl extends IBaseDaoImpl<ProductPlan,java.lang.String> implements ProductplanDao{

	@Override
	public PageModel<HashMap<String, Object>> findBySql(final String where,final Object[] queryParams, final Map<String, String> orderby, final int pageNo,final int maxResult,final String...t_name) {
		final PageModel<HashMap<String, Object>> pageModel = new PageModel<HashMap<String, Object>>();
		pageModel.setPageNo(pageNo);
		pageModel.setPageSize(maxResult);
		this.getHibernateTemplate().execute(new HibernateCallback() {
			@SuppressWarnings("unchecked")
			@Override
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				//三个月前数据2016-3-21 lxx
				String tablename="t_ord_productplan";
				if(t_name.length!=0&&t_name[0]!=null)
				{
					System.out.println("三个月前数据的表名更改为:"+t_name[0]);
					tablename=t_name[0];
				}
				StringBuilder sql = new StringBuilder().append("select ")
									.append(" d.fid, ")
									.append(" d.fstate,")
									.append(" d.fnumber,")
									.append(" d.fproductdefid,")
									.append(" ifnull(d.fcustproduct,'') fcustproductid,")
									.append("c.fname AS cname,")
									.append(" IFNULL(da.fdescription, '') fdescription,")
									.append(" da.fordernumber,")
									.append("  da.faddress,")
									.append(" ifnull(f.fname, '') AS fname,")
									.append(" p.fspec,")
									.append("  CONCAT(SUBSTRING(d.farrivetime, 1, 10),'</br>',IF(DATE_FORMAT(d.farrivetime,'%p')='AM','上午','下午')) farrivetime,")
									.append(" d.famount,")
									.append(" d.fstockoutqty,")
									.append("  d.fstockinqty")
									.append(" FROM "+tablename+" d ")
									.append(" LEFT JOIN t_bd_customer c  ON c.fid = d.fcustomerid ")
									.append(" LEFT JOIN t_bd_custproduct p ON p.fid = d.fcustproduct ")
									.append(" LEFT JOIN t_pdt_productdef f  ON f.fid = d.fproductdefid ")
									.append(" LEFT JOIN t_ord_deliverapply_card_mv da ON da.fid = d.fdeliverapplyid  ")
									.append(where == null ? "" : where)
									.append(createOrderBy(orderby));
				Query query = session.createSQLQuery(sql.toString());
				setQueryParams2(query,queryParams);
				query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				List<HashMap<String,Object>> list = query.setFirstResult(getFirstResult(pageNo, maxResult)).setMaxResults(maxResult).list();
							 sql = new StringBuilder().append("select count(*)")
									.append(" FROM "+tablename+" d")
									.append(" LEFT JOIN t_bd_customer c  ON c.fid = d.fcustomerid ")
									.append(" LEFT JOIN t_bd_custproduct p ON p.fid = d.fcustproduct ")
									.append(" LEFT JOIN t_pdt_productdef f  ON f.fid = d.fproductdefid ")
									.append(" LEFT JOIN t_ord_deliverapply_card_mv da ON da.fid = d.fdeliverapplyid  ")
										.append(where == null ? "" : where);
			   query = session.createSQLQuery(sql.toString());
			   setQueryParams2(query,queryParams);
			   pageModel.setTotalRecords(Integer.valueOf(query.uniqueResult().toString()));
			   pageModel.setList(list);
			   return null;
			}
		});
		return pageModel;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ProductPlan> getProductplanList(final String where,final Object[] queryParams,final  Map<String, String> orderby,final boolean...history) {
		List<ProductPlan> ftulist=(List<ProductPlan>)
				this.getHibernateTemplate().execute(new HibernateCallback() {
			@Override
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				String t_name="t_ord_productplan";
				if(history.length!=0&&history[0]==true)
				{
					t_name+="_h";
				}
				StringBuilder sql = new StringBuilder().append("select ")
						.append(" d.fid as fid, ")
						.append(" d.fstate as fstate,")
						.append(" d.fnumber as fnumber,")
						.append(" ifnull(d.fcustproduct,'') as fcustproduct,")
						.append("c.fname AS cname,")
						.append(" IFNULL(da.fdescription, '') as fdescription ,")
						.append(" da.fordernumber as fpcmordernumber,")
						.append("  da.faddress as faddress,")
						.append(" ifnull(f.fname, '') AS fproductname,")
						.append(" p.fspec as fspec,")
						.append("  SUBSTRING(d.farrivetime, 1, 10) as farrivetimeString,")
						.append(" d.famount as famount,")
						.append(" d.fproductdefid as fproductdefid,")
						.append(" d.fstockoutqty as fstockoutqty,")
						.append("  d.fstockinqty as fstockinqty ")
						.append(" FROM "+t_name+" d ")
						.append(" LEFT JOIN t_bd_customer c  ON c.fid = d.fcustomerid ")
						.append(" LEFT JOIN t_bd_custproduct p ON p.fid = d.fcustproduct ")
						.append(" LEFT JOIN t_pdt_productdef f  ON f.fid = d.fproductdefid ")
						.append(" LEFT JOIN t_ord_deliverapply_card_mv da ON da.fid = d.fdeliverapplyid  ")
						.append(where == null ? "" : where)
						.append(createOrderBy(orderby));
				Query query = session.createSQLQuery(sql.toString())
									.addScalar("fid")
									.addScalar("fstate")
									.addScalar("fnumber")
									.addScalar("fcustproduct")
									.addScalar("cname")
									.addScalar("fdescription")
									.addScalar("fpcmordernumber")
									.addScalar("faddress")
									.addScalar("fproductname")
									.addScalar("fspec")
									.addScalar("farrivetimeString")
									.addScalar("famount")
									.addScalar("fproductdefid")
									.addScalar("fstockoutqty")
									.addScalar("fstockinqty");
				setQueryParams2(query,queryParams);
				query.setResultTransformer(Transformers.aliasToBean(ProductPlan.class));
			   return query.list();
			}
		});
		return ftulist;
	}

	@Override
	public void updateReceiveState(String fidcls, String userid) {
		String sql = "UPDATE t_ord_productplan SET faffirmed=1,faffirmtime=now(),faffirmer='"+userid+"',fstate=1 WHERE fid in ("
				+ fidcls + ")";
		ExecBySql(sql);
		//已接收为3
		int updatcount=ExecBySql("update t_ord_deliverapply set fstate = 3 where fplanid in (" + fidcls + ")");
		if(updatcount>0)
		{
			List<HashMap<String,String>> list=this.QueryBySql("select fid from t_ord_deliverapply where fplanid in (" + fidcls + ")");
			for(HashMap<String,String> map:list)
			{
				createOrderStateInfo(map.get("fid"),3,true);
			}	
		}
		sql= "update t_ord_productplan set fimportEas=1 where  ifnull(fimportEas,0)=0 and fsupplierid='39gW7X9mRcWoSwsNJhU12TfGffw=' and fid in (" + fidcls+")" ;
		ExecBySql(sql);
	
	}
	//创建或删除订单状态
	public void createOrderStateInfo(String fdeliverapplyid,int state,boolean forward)
	{
		
		if(forward == true){
			String sql="INSERT INTO t_ord_orderstate(fid,fdeliverapplyid,fstate,fcreatetime) VALUES ('%s','%s','%s',now())";
			sql=String.format(sql, this.CreateUUid(),fdeliverapplyid,state);
			this.ExecBySql(sql);
		}else{
			String sql = "delete from t_ord_orderstate where fdeliverapplyid='"+fdeliverapplyid+"' and fstate >"+state;
			this.ExecBySql(sql);
		}
	}

	@Override
	public void updateUnreceiveState(HashMap<String, String> map) {
		String productplanid=map.get("productplanid");
		Params p =new Params();
		String sql = "UPDATE t_ord_productplan SET faffirmed='0',fstate=0,faffirmtime=null,faffirmer='',fimportEas=0 WHERE fid in (:fid)";
		p.put("fid", productplanid);
		this.ExecBySql(sql, p);
		this.ExecBySql("update t_ord_deliverapply set fstate = 0 where fplanid in (:fid)" ,p);
		if(map.containsKey("applyfid"))
		{
			String fidcls = map.get("applyfid");
			String forderid = map.get("orderfid");
			String storeid= map.get("storeid");
			 //删除占用表
			 this.ExecBySql("delete from t_inv_usedstorebalance  WHERE fstorebalanceid "+storeid);
			 this.ExecBySql("delete from  t_inv_storebalance  WHERE  fsaleorderid  "+forderid);//删除库存表
			 this.ExecBySql("delete from t_ord_deliverorder  WHERE  fsaleorderid  "+forderid);
			 this.ExecBySql("delete from t_ord_productplan  WHERE forderid  "+forderid);
			 this.ExecBySql("delete from t_ord_saleorder  WHERE forderid  "+forderid);
			 this.ExecBySql("delete from t_ord_delivers  WHERE  fsaleorderid  "+forderid);
			 this.ExecBySql("delete from t_ord_deliverratio  WHERE fdeliverappid in "+fidcls);
			 this.ExecBySql("update t_ord_deliverapply set fstate=0 , fiscreate=0,fplanid='',fplanNumber='' where fid in "+fidcls);
			 this.ExecBySql("delete from t_ord_orderstate where fdeliverapplyid in "+fidcls+" and fstate >0");
		}
	}
	

}
