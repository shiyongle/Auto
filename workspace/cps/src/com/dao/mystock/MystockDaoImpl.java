package com.dao.mystock;

import java.sql.SQLException;
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
import com.model.mystock.Mystock;
@Repository("mystockDao")
public class MystockDaoImpl extends IBaseDaoImpl<Mystock, java.lang.Integer> implements MystockDao {

	@Override
	public PageModel<Mystock> findBySql(final String where,final Object[] queryParams, final Map<String, String> orderby, final int pageNo,final int maxResult) {
		final PageModel<Mystock> pageModel = new PageModel<Mystock>();
		pageModel.setPageNo(pageNo);
		pageModel.setPageSize(maxResult);
		this.getHibernateTemplate().execute(new HibernateCallback() {
			@SuppressWarnings("unchecked")
			@Override
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				StringBuilder sql = new StringBuilder().append("select ")
									.append(" m.fid AS fid,")
									.append(" m.fplanamount AS fplanamount,")
									.append(" m.ffinishtime as ffinishtime,")
									.append(" m.fconsumetime as fconsumetime,")
									.append(" m.fsupplierid as fsupplierid,")
									.append("m.fordernumber as fordernumber,")
									.append(" s.fname as suppliername ,")
									.append(" s.ftel as  supplierPhone ,")
									.append(" m.fpcmordernumber as fpcmordernumber,")
									.append(" m.fnumber as fnumber,")
									.append(" m.fstate as fstate,")
									.append(" m.fcustproductid as fcustproductid,")
									.append(" c.fname as fpdtname,")
									.append(" c.FSPEC as fpdtspec,")
									.append(" m.fremark as fremark,")
									.append(" m.faveragefamount as faveragefamount")
									.append(" FROM mystock m")
									.append(" INNER JOIN t_bd_custproduct c ON c.fid = m.fcustproductid")
									.append(" INNER JOIN t_sys_supplier s ON s.fid = m.fsupplierid")
//									.append(" INNER JOIN t_bd_usercustomer tus on tus.FCUSTOMERID = m.fcustomerid")
//									.append(" INNER JOIN t_sys_user tsy on tsy.FID = tus.FUSERID")
									.append(where == null ? "where 1=1 "+QueryFilterByUserofuser("m.fcreateid","and") : where+QueryFilterByUserofuser("m.fcreateid","and"))
									.append(createOrderBy(orderby));
				Query query = session.createSQLQuery(sql.toString())
									.addScalar("fid")
									.addScalar("fplanamount")
									.addScalar("ffinishtime")
									.addScalar("fconsumetime")
									.addScalar("fsupplierid")
									.addScalar("fordernumber")
									.addScalar("suppliername")
									.addScalar("supplierPhone")
									.addScalar("fpcmordernumber")
									.addScalar("fnumber")
									.addScalar("fstate")
									.addScalar("fcustproductid")
									.addScalar("fpdtname")
									.addScalar("fpdtspec")
									.addScalar("fremark")
									.addScalar("faveragefamount");
				setQueryParams2(query,queryParams);
				query.setResultTransformer(Transformers.aliasToBean(Mystock.class));
				List<Mystock> list = query.setFirstResult(getFirstResult(pageNo, maxResult)).setMaxResults(maxResult).list();
							 sql = new StringBuilder().append("select count(*)")
									 .append(" FROM mystock m")
										.append(" INNER JOIN t_bd_custproduct c ON c.fid = m.fcustproductid")
										.append(" INNER JOIN t_sys_supplier s ON s.fid = m.fsupplierid")
										//.append(" INNER JOIN t_bd_usercustomer tus on tus.FCUSTOMERID = m.fcustomerid")
										//.append(" INNER JOIN t_sys_user tsy on tsy.FID = tus.FUSERID")
										.append(where == null ? "where 1=1 "+QueryFilterByUserofuser("m.fcreateid","and") : where+QueryFilterByUserofuser("m.fcreateid","and"));
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
	public List<Mystock> execlBySql(final String where,final Object[] queryParams, final Map<String, String> orderby) {
		List<Mystock> pul =(List<Mystock>) this.getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(final Session session) throws HibernateException, SQLException {
				StringBuilder sql = new StringBuilder().append("select ")
									.append(" m.fid AS fid,")
									.append(" m.fplanamount AS fplanamount,")
									.append(" m.ffinishtime as ffinishtime,")
									.append(" m.fconsumetime as fconsumetime,")
									.append(" m.fsupplierid as fsupplierid,")
									.append("m.fordernumber as fordernumber,")
									.append(" s.fname as suppliername ,")
									.append(" s.ftel as supplierPhone ,")
									.append(" m.fpcmordernumber as fpcmordernumber,")
									.append(" m.fnumber as fnumber,")
									.append(" m.fstate as fstate,")
									.append(" m.fcustproductid as fcustproductid,")
									.append(" c.fname as fpdtname,")
									.append(" c.FSPEC as fpdtspec,")
									.append(" m.fremark as fremark,")
									.append(" m.faveragefamount as faveragefamount")
									.append(" FROM mystock m")
									.append(" INNER JOIN t_bd_custproduct c ON c.fid = m.fcustproductid")
									.append(" INNER JOIN t_sys_supplier s ON s.fid = m.fsupplierid")
									.append(" INNER JOIN t_bd_usercustomer tus on tus.FCUSTOMERID = m.fcustomerid")
									.append(" INNER JOIN t_sys_user tsy on tsy.FID = tus.FUSERID")
//									.append(where == null ? "" : where)
									.append(where == null ? "where 1=1 "+QueryFilterByUserofuser("m.fcreateid","and") : where+QueryFilterByUserofuser("m.fcreateid","and"))
									.append(createOrderBy(orderby));
				Query query = session.createSQLQuery(sql.toString())
									.addScalar("fid")
									.addScalar("fplanamount")
									.addScalar("ffinishtime")
									.addScalar("fconsumetime")
									.addScalar("fsupplierid")
									.addScalar("fordernumber")
									.addScalar("suppliername")
									.addScalar("supplierPhone")
									.addScalar("fpcmordernumber")
									.addScalar("fnumber")
									.addScalar("fstate")
									.addScalar("fcustproductid")
									.addScalar("fpdtname")
									.addScalar("fpdtspec")
									.addScalar("fremark")
									.addScalar("faveragefamount");
				setQueryParams2(query,queryParams);
				query.setResultTransformer(Transformers.aliasToBean(Mystock.class));
			   return  query.list();
			}
		});
		return pul;
	}
	
}
