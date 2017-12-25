package com.dao.designschemes;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;
import com.dao.IBaseDaoImpl;
import com.model.PageModel;
import com.model.designschemes.Designschemes;
import com.util.Params;

@SuppressWarnings("unchecked")
@Repository("designSchemesDao")
public class DesignSchemesDaoImpl extends IBaseDaoImpl<Designschemes,java.lang.String> implements DesignSchemesDao {
	@Override
	public PageModel<Designschemes> findBySql(final String where,
			final Object[] queryParams, final Map<String, String> orderby, final int pageNo,
			final int maxResult) {
		final PageModel<Designschemes> pageModel = new PageModel<Designschemes>();
		pageModel.setPageNo(pageNo);
		pageModel.setPageSize(maxResult);
		this.getHibernateTemplate().execute(new HibernateCallback() {
			@Override
			public Object doInHibernate(Session session) throws HibernateException, SQLException {			
				StringBuilder sql = new StringBuilder().append(" select ")
						.append(" ds.fid                as fid,")
						.append(" ds.fname              as fname,")
						.append(" ds.fcreatorid            as fcreatorid,")
						.append(" u.fname              AS fcreator,")
						.append(" u.feffect              as feffect,")
						.append(" ds.fdescription        AS fdescription,")
						.append(" ds.fsupplierid         as fsupplierid,")
						.append(" ds.fauthtype              as fauthtype,")
						.append(" ds.fsuppliername         as fsuppliername,")
						.append(" ds.ftype              as ftype,")
						.append(" ds.fcreatetime              as fcreatetime,")
						.append(" ds.fstate              as fstate")
						.append(" FROM t_ord_designschemes ds ")
						.append(" LEFT JOIN t_sys_user u ON u.fid = ds.fcreatorid ")
						.append(where == null ? "" : where)
						.append(createOrderBy(orderby));
				Query query = session.createSQLQuery(sql.toString())
						.addScalar("fid")
						.addScalar("fname")
						.addScalar("fcreatorid")
						.addScalar("fcreator")
						.addScalar("feffect")
						.addScalar("fdescription")
						.addScalar("fsupplierid")
						.addScalar("fauthtype")
						.addScalar("fsuppliername")
						.addScalar("ftype")
						.addScalar("fcreatetime")
						.addScalar("fstate");
				setQueryParams2(query,queryParams);
				query.setResultTransformer(Transformers.aliasToBean(Designschemes.class));
				List<Designschemes> list = query.setFirstResult(getFirstResult(pageNo, maxResult)).setMaxResults(maxResult).list();
				sql = new StringBuilder().append("select count(*)")
						.append(" FROM t_ord_designschemes ds ")
						.append(" LEFT JOIN t_sys_user u ON u.fid = ds.fcreatorid ")
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

	/** 
	 *设计师产品列表 
	 */
	@Override
	public PageModel<Designschemes> findAllschemeSql(final String WHERE,final Object[] queryParams, final Map<String, String> orderby, final int pageNo,final int maxResult) {
		final PageModel<Designschemes> pageModel = new PageModel<Designschemes>();
		pageModel.setPageNo(pageNo);
		pageModel.setPageSize(maxResult);
		this.getHibernateTemplate().execute(new HibernateCallback() {
			@Override
			public Object doInHibernate(Session session) throws HibernateException, SQLException {			
				StringBuilder sql = new StringBuilder().append(" select ")
						.append(" fid,fname,fdescription,ftype,fsuppliername,fcreatetime ")
						.append(" from t_ord_designschemes ")
						.append(WHERE == null ? "" : WHERE)
						.append(createOrderBy(orderby));
				Query query = session.createSQLQuery(sql.toString())
						.addScalar("fid")
						.addScalar("fname")
						.addScalar("fdescription")
						.addScalar("ftype")
						.addScalar("fsuppliername")
						.addScalar("fcreatetime");
				setQueryParams2(query,queryParams);
				query.setResultTransformer(Transformers.aliasToBean(Designschemes.class));
				List<Designschemes> LIST = query.setFirstResult(getFirstResult(pageNo, maxResult)).setMaxResults(maxResult).list();				
				sql = new StringBuilder().append(" select count(1) from t_ord_designschemes ")
						.append(WHERE == null ? "" : WHERE);
				query = session.createSQLQuery(sql.toString());
				setQueryParams2(query,queryParams);
				pageModel.setTotalRecords(Integer.valueOf(query.uniqueResult().toString()));
				pageModel.setList(LIST);
				return null;
			}
		});
		return pageModel;
	}

	@Override
	public void deleteScheme(String id) {
		Designschemes scheme=this.get(id);
		if(scheme!=null)
		{
			this.delete(scheme);
		}
	}

	@Override
	public void updateshelves(String id) {
		Designschemes scheme=this.get(id);
		if(scheme!=null)
		{
			if(scheme.getFstate()==1)
			{
				scheme.setFstate(0);
			}else if(scheme.getFstate()==0)
			{
				scheme.setFstate(1);
			}
			this.update(scheme);
		}
	}

	@Override
	public void updateImgDesc(HashMap<String,String> imgmap){
		Params params = new Params();
		for (Entry<String, String> entry : imgmap.entrySet()) {	
			params.getData().clear();
			String sql ="update t_ord_schemefiles set fdescription=:fdescription where fid =:fid ";
			params.put("fdescription", entry.getValue());
			params.put("fid", entry.getKey());
			this.ExecBySql(sql, params);
		}
	}
}
