package com.dao.custproduct;

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
import com.model.address.Address;
import com.model.custproduct.TBdCustproduct;
import com.model.supplier.Supplier;
import com.util.Params;
@Repository("custproductDao")
public class TBdCustproductDaoImpl extends IBaseDaoImpl<TBdCustproduct, java.lang.String> implements TBdCustproductDao {
	

	@Override
	public PageModel<TBdCustproduct> findBySql(final String where, final Object[] queryParams,final Map<String, String> orderby, final int pageNo, final int maxResult) {
		final PageModel<TBdCustproduct> pageModel = new PageModel<TBdCustproduct>();
		pageModel.setPageNo(pageNo);
		pageModel.setPageSize(maxResult);
		this.getHibernateTemplate().execute(new HibernateCallback() {
			@SuppressWarnings("unchecked")
			@Override
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				StringBuilder sql = new StringBuilder().append(" select ")
									.append(" pt.FID   as fid,")
									.append(" pt.FNAME as fname,")
									.append(" pt.FSPEC as fspec,")
									.append(" pt.fmaterial as fmaterial,")
									.append(" pt.fcreatetime as fcreatetime,")
									.append(" pt.fcustomerid as fcustomerid,")
									.append(" pt.fiscommon as fiscommon,")
									.append(" tp.FID as supplierId,")
									.append(" tp.FNAME as supplierName,")
									.append(" sum(ts.fbalanceqty) as balanceqty,")
									.append(" pt.flastordertime as flastordertime,")
									.append(" pt.flastorderfamount as flastorderfamount")
									.append(" FROM t_bd_custproduct pt ")
									.append(" INNER JOIN t_bd_usercustomer tuc ON tuc.FCUSTOMERID = pt.FCUSTOMERID")
									.append(" INNER JOIN t_sys_user tu ON tu.FID = tuc.FUSERID")
									.append(" inner JOIN t_pdt_productdef pf ON pt.fproductid = pf.fid ")
									.append(" LEFT JOIN t_sys_supplier tp on tp.fid = pf.fsupplierid ")
									.append(" LEFT JOIN t_inv_storebalance ts on ts.fProductID = pt.fproductid ")
									.append(where == null ? "" : where)
									.append(" GROUP BY pt.FID,pt.FNAME,pt.FSPEC,tp.FNAME,pt.flastordertime,pt.flastorderfamount")
									.append(createOrderBy(orderby));
				Query query = session.createSQLQuery(sql.toString())
									.addScalar("fid")
									.addScalar("fname")
									.addScalar("fspec")
									.addScalar("fmaterial")
									.addScalar("fcreatetime")
									.addScalar("fcustomerid")
									.addScalar("fiscommon")
									.addScalar("supplierId")
									.addScalar("supplierName")
									.addScalar("balanceqty")
									.addScalar("flastordertime")
									.addScalar("flastorderfamount");
				setQueryParams2(query,queryParams);
				query.setResultTransformer(Transformers.aliasToBean(TBdCustproduct.class));
				List<TBdCustproduct> list = query.setFirstResult(getFirstResult(pageNo, maxResult)).setMaxResults(maxResult).list();
							 sql = new StringBuilder().append("select count(*)  from ( select pt.fid")
									 .append(" FROM t_bd_custproduct pt ")
									 .append(" INNER JOIN t_bd_usercustomer tuc ON tuc.FCUSTOMERID = pt.FCUSTOMERID")
									 .append(" INNER JOIN t_sys_user tu ON tu.FID = tuc.FUSERID")
									 .append(" LEFT JOIN t_pdt_productdef pf ON pt.fproductid = pf.fid ")
							         .append(where == null ? "" : where)
							         .append(" GROUP BY pt.FID,pt.FNAME,pt.FSPEC,pf.fsupplierid,pt.flastordertime,pt.flastorderfamount")
							 		 .append(" ) as tab");
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
	public List<TBdCustproduct> getById(final String userId, final String cusproductId) {
		List<TBdCustproduct> pul =(List<TBdCustproduct>) this.getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				StringBuilder sql = new StringBuilder().append(" select ")
									.append(" pt.FID   as fid,")
									.append(" pt.FNAME as fname,")
									.append(" pt.FSPEC as fspec,")
									.append(" pt.fmaterial as fmaterial,")
									.append(" pt.fcreatetime as fcreatetime,")
									.append(" pt.fcustomerid as fcustomerid,")
									.append(" tp.FID as supplierId,")
									.append(" tp.FNAME as supplierName,")
									.append(" sum(ts.fbalanceqty) as balanceqty,")
									.append(" pt.flastordertime as flastordertime,")
									.append(" pt.flastorderfamount as flastorderfamount")
									.append(" FROM t_bd_custproduct pt ")
									.append(" INNER JOIN t_bd_usercustomer tuc ON tuc.FCUSTOMERID = pt.FCUSTOMERID")
									.append(" INNER JOIN t_sys_user tu ON tu.FID = tuc.FUSERID")
									.append(" LEFT JOIN t_pdt_productdef pf ON pt.fproductid = pf.fid ")
									.append(" LEFT JOIN t_sys_supplier tp on tp.fid = pf.fsupplierid ")
									.append(" LEFT JOIN t_inv_storebalance ts on ts.fProductID = pf.FID ")
									.append(" where tu.fid = ? and pt.fid =? and pt.feffect =1  and pt.fcharacterid is null")
									.append(" GROUP BY pt.FID,pt.FNAME,pt.FSPEC,tp.FNAME,pt.flastordertime,pt.flastorderfamount limit 1");
				Query query = session.createSQLQuery(sql.toString())
									.addScalar("fid")
									.addScalar("fname")
									.addScalar("fspec")
									.addScalar("fmaterial")
									.addScalar("fcreatetime")
									.addScalar("fcustomerid")
									.addScalar("supplierId")
									.addScalar("supplierName")
									.addScalar("balanceqty")
									.addScalar("flastordertime")
									.addScalar("flastorderfamount");
				query.setParameter(0, userId);
				query.setParameter(1, cusproductId);
				query.setResultTransformer(Transformers.aliasToBean(TBdCustproduct.class));
			   return  query.list();
			}
		});
		return pul;
	}

	@Override
	public List<Supplier> getBySupplier(final String userId) {
		@SuppressWarnings("unchecked")
		List<Supplier> supplier =(List<Supplier>) this.getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(final Session session) throws HibernateException, SQLException {
				final StringBuilder sql = new StringBuilder().append(" select ")
									.append(" p.FID   as fid,")
									.append(" p.FNAME as fname")
									.append(" from  t_pdt_productreqallocationrules t ")
									.append(" INNER JOIN t_sys_supplier p on t.fsupplierid =p.fid")
									.append(" LEFT JOIN t_bd_usercustomer u on u.FCUSTOMERID =t.fcustomerid")
									.append(" LEFT JOIN t_sys_user s on s.FID = u.FUSERID")
									.append(" WHERE s.FID = ?" );
				final Query query = session.createSQLQuery(sql.toString())
									.addScalar("fid")
									.addScalar("fname");
				query.setParameter(0, userId);
				query.setResultTransformer(Transformers.aliasToBean(Supplier.class));
			   return  query.list();
			}
		});
		return supplier;
	}

	@Override
	public List<HashMap<String, Object>> getCustproductStock(String fid,String supplierid) {
		// TODO Auto-generated method stub
		TBdCustproduct cp = this.get(fid);
		Params params=new Params();
//		String sql="select sum(amount) amount ,sum(fusedqty) fusedqty,sum(fmakingqty) fmakingqty,sum(amount+fmakingqty) fbalanceqty from ("
//			     +" select sum(fbalanceqty) amount,0 fusedqty,0 fmakingqty from  t_inv_storebalance  where  fproductid = '%s' and fsupplierid='%s' "
//			     +" union select 0 amount,sum(fusedqty) fusedqty,0 fmakingqty from t_inv_usedstorebalance left join t_ord_deliverorder o ON o.fid= t_inv_usedstorebalance.fdeliverorderid WHERE fusedqty > 0 AND t_inv_usedstorebalance.fproductid = '%s'   AND fsupplierid='%s' "
//			     +" union select 0 amount,0 fusedqty, sum(case when famount < ifnull(fstockinqty, 0) then 0 else famount - ifnull(fstockinqty, 0) end) fmakingqty "
//			     +" from t_ord_productplan  where fcloseed = 0 and faudited = 1 and fboxtype = 0 and fproductdefid = '%s' and fsupplierid='%s' ) s";
//		sql = String.format(sql, cp.getFproductid(),supplierid,cp.getFproductid(),supplierid,cp.getFproductid(),supplierid);
		String sql="select sum(amount) amount ,sum(fusedqty) fusedqty,sum(fmakingqty) fmakingqty,sum(amount+fmakingqty) fbalanceqty from ("
			     +" select sum(fbalanceqty) amount,0 fusedqty,0 fmakingqty from  t_inv_storebalance  where  fproductid = :fpid and fsupplierid=:fsid "
			     +" union select 0 amount,sum(fusedqty) fusedqty,0 fmakingqty from t_inv_usedstorebalance left join t_ord_deliverorder o ON o.fid= t_inv_usedstorebalance.fdeliverorderid WHERE fusedqty > 0 AND t_inv_usedstorebalance.fproductid = :fpid   AND fsupplierid=:fsid "
			     +" union select 0 amount,0 fusedqty, sum(case when famount < ifnull(fstockinqty, 0) then 0 else famount - ifnull(fstockinqty, 0) end) fmakingqty "
			     +" from t_ord_productplan  where fcloseed = 0 and faudited = 1 and fboxtype = 0 and fproductdefid = :fpid and fsupplierid=:fsid ) s";
		params.put("fpid", cp.getFproductid());
		params.put("fsid", supplierid);
		List<HashMap<String,Object>> result = this.QueryBySql(sql,params);
		return result;
	}
}
