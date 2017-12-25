package com.dao.cusprivatedelivers;

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
import com.model.cusprivatedelivers.CusPrivateDelivers;
import com.model.deliverapply.Deliverapply;

/*
 * CPS-VMI-wangc
 */

@Repository("cusprivatedeliversDao")
public class CusprivatedeliversDaoImpl extends IBaseDaoImpl<CusPrivateDelivers,java.lang.String> implements CusprivatedeliversDao{

	@Override
	public PageModel<CusPrivateDelivers> findBySql(final String where,final Object[] queryParams, final Map<String, String> orderby, final int pageNo,final int maxResult) {
		final PageModel<CusPrivateDelivers> pageModel = new PageModel<CusPrivateDelivers>();
		pageModel.setPageNo(pageNo);
		pageModel.setPageSize(maxResult);
		this.getHibernateTemplate().execute(new HibernateCallback() {
			@SuppressWarnings("unchecked")
			@Override
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				StringBuilder sql = new StringBuilder().append(" select ")
						.append(" c.fid  as fid,")
						.append(" c.fcustomerid as fcustomerid,")
						.append(" c.fsupplierid  as fsupplierid,")
						.append(" s.fname as fsuppliername,")
						.append(" c.fcusproduct as  fcusproduct,")
						.append(" c.ftype as ftype,")
						.append(" p.fname as cutpdtname,")
						.append(" p.fspec as fspec,")
						.append(" c.famount  as famount,")
						.append(" c.faddress  as faddress,")
						.append(" ad.fname  as faddressdetail,")
						.append(" DATE_FORMAT(c.farrivetime,'%Y-%m-%d')  as farriveString,")
						.append("  IF(DATE_FORMAT(c.farrivetime, '%p') = 'AM','上午','下午' ) as farrivezone,")
						.append(" DATE_FORMAT(c.freqdate,'%Y-%m-%d')  as fconsumetime")
						.append("  FROM t_ord_cusprivatedelivers c ")
						.append("   LEFT JOIN t_sys_supplier s  ON c.fsupplierid = s.fid  ")
						.append("   LEFT JOIN t_bd_custproduct p    ON p.fid = c.fcusproduct")
						.append("  LEFT JOIN t_bd_address ad ON ad.fid = c.faddress ")
						.append(where == null ? "" : where)
						.append(createOrderBy(orderby));
						Query query = session.createSQLQuery(sql.toString())
								.addScalar("fid")
								.addScalar("fcustomerid")
								.addScalar("fsupplierid")
								.addScalar("fsuppliername")
								.addScalar("fcusproduct")
								.addScalar("ftype")
								.addScalar("cutpdtname")
								.addScalar("fspec")
								.addScalar("famount")
								.addScalar("faddress")
								.addScalar("faddressdetail")
								.addScalar("farriveString")
								.addScalar("farrivezone")
								.addScalar("fconsumetime");
			setQueryParams2(query,queryParams);
			query.setResultTransformer(Transformers.aliasToBean(CusPrivateDelivers.class));
			List<CusPrivateDelivers> list = query.setFirstResult(getFirstResult(pageNo, maxResult)).setMaxResults(maxResult).list();
	
							 sql = new StringBuilder().append("select count(c.fid)")
									 .append(" FROM t_ord_cusprivatedelivers c ")
									 .append(" LEFT JOIN t_bd_custproduct p  ON p.fid = c.fcusproduct")
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
	

}
