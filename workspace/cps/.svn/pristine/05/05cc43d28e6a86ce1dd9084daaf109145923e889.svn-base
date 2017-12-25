package com.dao.customer;

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
import com.model.customer.Customer;
import com.util.Params;
@Repository("customerDao")
public class CustomerDaoImpl extends IBaseDaoImpl<Customer, java.lang.Integer> implements CustomerDao {

	@Override
	public String getCustomerByUserid(String fid) {
		// TODO Auto-generated method stub
		Params params=new Params();
//		String sql="select fcustomerid,cust.`FNAME` fname from ( select fcustomerid  from t_bd_usercustomer where fuserid='"+fid+"'"+
//			     " union select c.fcustomerid from  t_sys_userrole r "+
//			     " left join t_bd_rolecustomer c on r.froleid=c.froleid where r.fuserid='"+fid+"' ) s "+
//			     " left join t_bd_customer cust ON cust.fid = s.fcustomerid where fcustomerid is not null " ;
		String sql="select fcustomerid,cust.`FNAME` fname from ( select fcustomerid  from t_bd_usercustomer where fuserid=:fuid"+
				" union select c.fcustomerid from  t_sys_userrole r "+
				" left join t_bd_rolecustomer c on r.froleid=c.froleid where r.fuserid=:fid ) s "+
				" left join t_bd_customer cust ON cust.fid = s.fcustomerid where fcustomerid is not null " ;
		params.put("fuid", fid);
		params.put("fid", fid);
		@SuppressWarnings("unchecked")
		List<HashMap<String,Object>> c = this.QueryBySql(sql,params);
		
		return String.valueOf(c.get(0).get("fcustomerid"));
	}
	@Override
	public HashMap<String,String>  getSupplierByUserid(String fid) { 
		HashMap<String,String> map = new HashMap<String,String>();
		Params params=new Params();
		String sql="select fsupplierid from ( select fsupplierid  from t_bd_usersupplier where fuserid=:fuid"+
				" union select c.fsupplierid from  t_sys_userrole r "+
				" left join t_bd_rolesupplier c on r.froleid=c.froleid where r.fuserid=:fid ) s where fsupplierid is not null " ;
		params.put("fuid", fid);
		params.put("fid", fid);
		@SuppressWarnings("unchecked")
		List<HashMap<String,Object>> c = this.QueryBySql(sql,params);
		String hql = " select fname from Supplier where fid ='"+c.get(0).get("fsupplierid")+"'";	
		List list = this.QueryByHql(hql);	
		map.put("fsupplierid",  String.valueOf(c.get(0).get("fsupplierid")));
		map.put("fsuppliername", list.get(0).toString());
		return map;
	}	

	@Override
	public PageModel<HashMap<String, Object>> findBySql(final String where,final Object[] queryParams,final  Map<String, String> orderby, final int pageNo,final int maxResult) {
		final PageModel<HashMap<String, Object>> pageModel = new PageModel<HashMap<String, Object>>();
		pageModel.setPageNo(pageNo);
		pageModel.setPageSize(maxResult);
		this.getHibernateTemplate().execute(new HibernateCallback() {
			@SuppressWarnings("unchecked")
			@Override
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
							StringBuilder sql = new StringBuilder().append(" select ")
									.append(" c.fid,")
									.append(" ifnull(c.fname,'') fname,")
									.append(" ifnull(c.flinkman,'') flinkman,")
									.append(" ifnull(c.fphone,'') fphone,")
									.append(" c.fcreatetime,")
									.append("  ifnull(c.fdescription,'') fdescription,")
									.append("  ifnull(ad.fname,'') addressname")
									.append(" FROM t_bd_customer c ")
									.append(" LEFT JOIN (SELECT fid,fcustomerid,faddressid FROM t_bd_custrelationadress cu  GROUP BY cu.`fcustomerid` HAVING COUNT(cu.fid)<=1 ) cust ON c.fid=cust.fcustomerid")
									.append(" LEFT JOIN t_pdt_productreqallocationrules sup ON sup.fcustomerid=c.fid left join t_bd_address ad on ad.fid= cust.faddressid")
									.append(where == null ? "" : where)
									.append(createOrderBy(orderby));
				Query query = session.createSQLQuery(sql.toString());
				setQueryParams2(query,queryParams);
				query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				List<HashMap<String, Object>> list = query.setFirstResult(getFirstResult(pageNo, maxResult)).setMaxResults(maxResult).list();
							 sql = new StringBuilder().append("select count(*) ")
									.append(" FROM t_bd_customer c ")
									.append(" LEFT JOIN (SELECT fid,fcustomerid,faddressid FROM t_bd_custrelationadress cu  GROUP BY cu.`fcustomerid` HAVING COUNT(cu.fid)<=1 ) cust ON c.fid=cust.fcustomerid")
									.append(" LEFT JOIN t_pdt_productreqallocationrules sup ON sup.fcustomerid=c.fid left join t_bd_address ad on ad.fid= cust.faddressid")
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
	@Override
	public void delCustomer(String customerid,String userid,String fsupplierid) throws Exception{
		try {
			Params param=new Params();
			for(String fid : customerid.split(",")){
//				String sql ="select fid from t_pdt_productreqallocationrules where fcustomerid='"+fid+"'";
				String sql ="select fid from t_pdt_productreqallocationrules where fcustomerid=:fid";
				param.put("fid", fid);
				List<HashMap<String,Object>> list = this.QueryBySql(sql,param);
				if(list.size()>1){
					throw new Exception("该客户已被其他制造商关联,不能删除！");
				}
//				sql = "select fisinvited from t_bd_customer where fid='"+fid+"'";
				sql = "select fisinvited from t_bd_customer where fid=:fid";
				list = this.QueryBySql(sql,param);
				if("1".equals(list.get(0).get("fisinvited"))){
					throw new Exception("该客户已被邀请,不能删除！");
				}
//				sql="select fid from t_ftu_saledeliver where fcustomer ='"+fid+"' and fstate != 1";
				sql="select fid from t_ftu_saledeliver where fcustomer =:fid and fstate != 1";
				if(this.QueryExistsBySql(sql,param)){
					throw new Exception("该客户已经生成送货凭证，不能删除");
				}
//				if(this.QueryExistsBySql("select fid from t_ord_deliverapply where fcustomerid ='"+fid+"'"))
				if(this.QueryExistsBySql("select fid from t_ord_deliverapply where fcustomerid =:fid",param))
				{
					throw new Exception("该客户已经要货，不能删除");
				}
//				sql = "select faddressid from t_bd_custrelationadress where fcustomerid='"+fid+"'";
				sql = "select faddressid from t_bd_custrelationadress where fcustomerid=:fid";
				list = this.QueryBySql(sql,param);
				if(list.size()>0){//如果有地址 删除是所有的地址
//					sql = "delete from t_bd_custrelationadress where fcustomerid='"+fid+"'";
					sql = "delete from t_bd_custrelationadress where fcustomerid=:fid";
					this.ExecBySql(sql,param);
					String addressid = "";
					for(int i = 0;i<list.size();i++){
						addressid += list.get(i).get("faddressid");
						if(i<list.size()-1){
							addressid += ",";
						}
					}
					addressid = addressid.replaceAll(",", "','");
					param.getData().clear();
//					sql = "delete from t_bd_address where fid in('"+addressid+"')";
					sql = "delete from t_bd_address where fid in(:addr)";
					param.put("addr", addressid);
					this.ExecBySql(sql,param);
				}
				param.getData().clear();
//				sql = "delete from t_pdt_productreqallocationrules where fcustomerid='"+fid+"' and fsupplierid='"+fsupplierid+"'";
				sql = "delete from t_pdt_productreqallocationrules where fcustomerid=:fcid and fsupplierid=:fsid";
				param.put("fcid", fid);
				param.put("fsid", fsupplierid);
				this.ExecBySql(sql,param);//删除选择供应商
				param.getData().clear();
//				sql = "delete from t_bd_customer where fid='"+fid+"'";
				sql = "delete from t_bd_customer where fid=:fid";
				param.put("fid", fid);
				this.ExecBySql(sql,param);//删除客户信息
			}
		} catch (Exception e) {
			// TODO: handle exception
			throw new Exception(e.getMessage());
		}
		
	}
	
}
