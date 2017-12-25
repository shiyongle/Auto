package com.dao.myDelivery;

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
import com.model.myDelivery.MyDelivery;
@Repository("myDeliveryDao")
public class MyDeliveryDaoImpl extends IBaseDaoImpl<MyDelivery, java.lang.Integer> implements MyDeliveryDao {

	@Override
	public PageModel<MyDelivery> findBySql(final String where1,final String where2,final Object[] queryParams, final Map<String, String> orderby, final int pageNo,final int maxResult) {
		final PageModel<MyDelivery> pageModel = new PageModel<MyDelivery>();
		pageModel.setPageNo(pageNo);
		pageModel.setPageSize(maxResult);
		this.getHibernateTemplate().execute(new HibernateCallback() {
			@SuppressWarnings("unchecked")
			@Override
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				StringBuilder sql = new StringBuilder().append(" select sdentryid,fsupplierid,fsuppliername,fboxtype,saleorderNumber,fpcmordernumber,cutpdtname,fboxspec,fmaterialspec,famount,date_format(farrivetime,'%Y-%m-%d %H:%i') farrivetime,faddress,fdescription,faffirmed,forderunit,fcustoermid,fcustname,fid")
									.append(" FROM ( ")
									.append(" select sd.fid sdentryid,sa.fsupplierid,sa.fsuppliername,0 fboxtype,sa.fnumber saleorderNumber,'' fpcmordernumber,p.fname cutpdtname,p.fspec fboxspec,'' fmaterialspec,sd.famount famount,sa.fprinttime farrivetime,sa.fcustAddress faddress,sd.fdescription fdescription,sd.faffirmed faffirmed,p.funit forderunit,sa.fcustomer fcustoermid,c.fname fcustname,sd.fid fid ")
									.append(" FROM t_ftu_saledeliver sa ")
									.append(" LEFT JOIN t_ftu_saledeliverentry sd ON sd.fparentid = sa.fid ")
									.append(" LEFT JOIN t_bd_custproduct p ON sd.fftuproductid = p.fid ")
									.append(" LEFT JOIN t_bd_customer c ON sa.fcustomer = c.fid ")
									.append(where1 == null ? "" : where1)
									.append(" UNION ")
									.append(" SELECT st.fid sdentryid,st.fsupplierid,_suppliername fsuppliername,d.fboxtype,d.fnumber saleorderNumber,d.fpcmordernumber,_custpdtname cutpdtname,_spec fboxspec,_mspec fmaterialspec,st.famount famount,d.fouttime farrivetime,d.faddress faddress,d.fdescription,st.faffirmed,cpdt.FORDERUNIT forderunit,d.fcustomerid fcustoermid,_custname fcustname,d.fid fid ")
									.append(" FROM t_ord_deliverorder_mv d ")
									.append(" INNER JOIN t_tra_saledeliverentry st ON d.fid = st.FDELIVERORDERID ")
									.append(" LEFT JOIN t_bd_custproduct cpdt ON cpdt.fid = d.fcusproductid ")
									.append(where2 == null ? "" : where2)
									.append(" ) t")
									.append(createOrderBy(orderby));
				Query query = session.createSQLQuery(sql.toString()).addScalar("sdentryid").addScalar("fsupplierid").addScalar("fsuppliername").addScalar("fboxtype").addScalar("saleorderNumber").addScalar("fpcmordernumber").addScalar("cutpdtname").addScalar("fboxspec").addScalar("fmaterialspec").addScalar("famount").addScalar("farrivetime").addScalar("faddress").addScalar("fdescription").addScalar("faffirmed").addScalar("forderunit")
									    .addScalar("fcustoermid").addScalar("fcustname").addScalar("fid");
				setQueryParams2(query,queryParams);
				query.setResultTransformer(Transformers.aliasToBean(MyDelivery.class));
				List<MyDelivery> list = query.setFirstResult(getFirstResult(pageNo, maxResult)).setMaxResults(maxResult).list();
							  sql = new StringBuilder().append(" select count(*)")
									.append(" FROM ( ")
									.append(" select sd.fid sdentryid,sa.fsupplierid,sa.fsuppliername,0 fboxtype,sa.fnumber saleorderNumber,'' fpcmordernumber,p.fname cutpdtname,p.fspec fboxspec,'' fmaterialspec,sd.famount famount,sa.fprinttime farrivetime,sa.fcustAddress faddress,sd.fdescription fdescription,sd.faffirmed faffirmed,p.funit forderunit,sa.fcustomer fcustoermid,c.fname fcustname,sd.fid fid ")
									.append(" FROM t_ftu_saledeliver sa ")
									.append(" LEFT JOIN t_ftu_saledeliverentry sd ON sd.fparentid = sa.fid ")
									.append(" LEFT JOIN t_bd_custproduct p ON sd.fftuproductid = p.fid ")
									.append(" LEFT JOIN t_bd_customer c ON sa.fcustomer = c.fid ")
									.append(where1 == null ? "" : where1)
									.append(" UNION ")
									.append(" SELECT st.fid sdentryid,st.fsupplierid,_suppliername fsuppliername,d.fboxtype,d.fnumber saleorderNumber,d.fpcmordernumber,_custpdtname cutpdtname,_spec fboxspec,_mspec fmaterialspec,st.famount famount,d.fouttime farrivetime,d.faddress faddress,d.fdescription,st.faffirmed,cpdt.FORDERUNIT forderunit,d.fcustomerid fcustoermid,_custname fcustname,d.fid fid ")
									.append(" FROM t_ord_deliverorder_mv d ")
									.append(" INNER JOIN t_tra_saledeliverentry st ON d.fid = st.FDELIVERORDERID ")
									.append(" LEFT JOIN t_bd_custproduct cpdt ON cpdt.fid = d.fcusproductid ")
									.append(where2 == null ? "" : where2)
									.append(" ) t");
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
	public List<MyDelivery> getLine(final String where,final Object[] queryParams,final Map<String, String> orderby) {
		@SuppressWarnings("unchecked")
		List<MyDelivery> pul =(List<MyDelivery>) this.getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(final Session session) throws HibernateException, SQLException {
				final StringBuilder sql = new StringBuilder().append(" SELECT ")
								.append(" DATE_FORMAT(farrivetime, '%Y-%m-%d') farrivetime,")
								.append(" sum(famount) AS famounts ")
								.append(" FROM t_ord_deliverorder ")
								.append(" WHERE farrivetime BETWEEN DATE_SUB(NOW(), INTERVAL 6 DAY) AND NOW() ")
								.append(where == null ? "" : where)
								.append(" GROUP BY DATE_FORMAT(farrivetime, '%Y-%m-%d') ")
								.append(createOrderBy(orderby));
			final Query query = session.createSQLQuery(sql.toString())
								.addScalar("farrivetime")
								.addScalar("famounts");
				setQueryParams2(query,queryParams);
				query.setResultTransformer(Transformers.aliasToBean(MyDelivery.class));
			   return  query.list();
			}
		});
		return pul;
	}

	@Override
	public List<MyDelivery> execlBySql(final String where1, final String where2,final Object[] queryParams, final Map<String, String> orderby) {
		@SuppressWarnings("unchecked")
		List<MyDelivery> pul =(List<MyDelivery>) this.getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				StringBuilder sql = new StringBuilder().append(" select sdentryid,fsupplierid,fsuppliername,fboxtype,saleorderNumber,fpcmordernumber,cutpdtname,fboxspec,fmaterialspec,famount,date_format(farrivetime,'%Y-%m-%d %H:%i') farrivetime,faddress,fdescription,faffirmed,forderunit,fcustoermid,fcustname,fid")
						.append(" FROM ( ")
						.append(" select sd.fid sdentryid,sa.fsupplierid,sa.fsuppliername,0 fboxtype,sa.fnumber saleorderNumber,'' fpcmordernumber,p.fname cutpdtname,p.fspec fboxspec,'' fmaterialspec,sd.famount famount,sa.fprinttime farrivetime,sa.fcustAddress faddress,sd.fdescription fdescription,sd.faffirmed faffirmed,p.funit forderunit,sa.fcustomer fcustoermid,c.fname fcustname,sd.fid fid ")
						.append(" FROM t_ftu_saledeliver sa ")
						.append(" LEFT JOIN t_ftu_saledeliverentry sd ON sd.fparentid = sa.fid ")
						.append(" LEFT JOIN t_bd_custproduct p ON sd.fftuproductid = p.fid ")
						.append(" LEFT JOIN t_bd_customer c ON sa.fcustomer = c.fid ")
						.append(where1 == null ? "" : where1)
						.append(" UNION ")
						.append(" SELECT st.fid sdentryid,st.fsupplierid,_suppliername fsuppliername,d.fboxtype,d.fnumber saleorderNumber,d.fpcmordernumber,_custpdtname cutpdtname,_spec fboxspec,_mspec fmaterialspec,st.famount famount,d.fouttime farrivetime,d.faddress faddress,d.fdescription,st.faffirmed,cpdt.FORDERUNIT forderunit,d.fcustomerid fcustoermid,_custname fcustname,d.fid fid ")
						.append(" FROM t_ord_deliverorder_mv d ")
						.append(" INNER JOIN t_tra_saledeliverentry st ON d.fid = st.FDELIVERORDERID ")
						.append(" LEFT JOIN t_bd_custproduct cpdt ON cpdt.fid = d.fcusproductid ")
						.append(where2 == null ? "" : where2)
						.append(" ) t")
						.append(createOrderBy(orderby));
				Query query = session.createSQLQuery(sql.toString()).addScalar("sdentryid").addScalar("fsupplierid").addScalar("fsuppliername").addScalar("fboxtype").addScalar("saleorderNumber").addScalar("fpcmordernumber").addScalar("cutpdtname").addScalar("fboxspec").addScalar("fmaterialspec").addScalar("famount").addScalar("farrivetime").addScalar("faddress").addScalar("fdescription").addScalar("faffirmed").addScalar("forderunit")
									    .addScalar("fcustoermid").addScalar("fcustname").addScalar("fid");
				setQueryParams2(query,queryParams);
				query.setResultTransformer(Transformers.aliasToBean(MyDelivery.class));
			   return  query.list();
			}
		});
		return pul;
	}

	@Override
	public PageModel<MyDelivery> findBySql(final String where,final Object[] queryParams, final Map<String, String> orderby, final int pageNo,final int maxResult) {
		// TODO Auto-generated method stub
		final PageModel<MyDelivery> pageModel = new PageModel<MyDelivery>();
		pageModel.setPageNo(pageNo);
		pageModel.setPageSize(maxResult);
		this.getHibernateTemplate().execute(new HibernateCallback() {
			@SuppressWarnings("unchecked")
			@Override
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				StringBuilder sql = new StringBuilder();
				StringBuilder mainsql = new StringBuilder();
				sql.append("SELECT * FROM ( ");
				mainsql.append(" SELECT sde.fid sdentryid,sd.fsuppliername fsuppliername,0 fboxtype,sd.fnumber saleorderNumber,cpdt.FNAME cutpdtname,cpdt.FSPEC fboxspec, ");
				mainsql.append(" '' fmaterialspec,CAST(sde.famount AS DECIMAL(28,10)) famounts,date_format(sd.fprinttime,'%Y-%m-%d %H:%i') farrivetime,sd.fcustAddress faddress,sde.fdescription fdescription,sde.faffirmed faffirmed ");
				mainsql.append(" FROM t_ftu_saledeliver sd LEFT JOIN t_ftu_saledeliverentry sde ON sd.fid = sde.fparentid ");
				mainsql.append(" LEFT JOIN t_bd_custproduct cpdt ON cpdt.fid = sde.fftuproductid ");
				mainsql.append(" where sd.fcustomer = ? and sd.fstate<>1 ");
				//2016-4-2 by les子账号过滤
				mainsql.append(QueryFilterByUserofuser("sd.fcreator","and"));
				//2015年11月11日 where有实际条件
				if(!where.equals("")){
					int secondWhere = where.indexOf("龖");
					String where1 = where.substring(0,secondWhere);
					mainsql.append(where1);
				}				
				mainsql.append(" UNION ALL ");
				mainsql.append(" SELECT sde.FID sdentryid,d._suppliername fsuppliername,d.fboxtype fboxtype,d.fnumber saleorderNumber,d._custpdtname cutpdtname,d._spec fboxspec, ");
				mainsql.append(" d._mspec fmaterialspec,sde.famount famounts,date_format(d.fouttime,'%Y-%m-%d %H:%i') farrivetime,d.faddress faddress,d.fdescription fdescription,sde.faffirmed faffirmed ");
				mainsql.append(" FROM t_ord_deliverorder_mv d INNER JOIN t_tra_saledeliverentry sde ON d.fid = sde.fdeliverorderid ");
				mainsql.append(" LEFT JOIN t_bd_custproduct cpdt ON cpdt.FID = d.fcusproductid ");
				mainsql.append("where d.fcustomerid = ? ");
				//2016-4-2 by les子账号过滤
				mainsql.append(QueryFilterByUserofuser("d.fcreatorid","and"));
				if(!where.equals("")){
					int secondWhere = where.indexOf("龖");
					String where2 = where.substring(secondWhere+1);
					mainsql.append(where2);
				}
				sql.append(mainsql.toString());
				sql.append(") t ");
				sql.append(createOrderBy(orderby));
				Query query = session.createSQLQuery(sql.toString())
									.addScalar("sdentryid")
									.addScalar("fsuppliername")
									.addScalar("fboxtype")
									.addScalar("saleorderNumber")
									.addScalar("cutpdtname")
									.addScalar("fboxspec")
									.addScalar("fmaterialspec")
									.addScalar("famounts")
									.addScalar("farrivetime")
									.addScalar("faddress")
									.addScalar("fdescription")
									.addScalar("faffirmed");					
				setQueryParams2(query,queryParams);
				query.setResultTransformer(Transformers.aliasToBean(MyDelivery.class));
				List<MyDelivery> list = query.setFirstResult(getFirstResult(pageNo, maxResult)).setMaxResults(maxResult).list();
				//查询数量暂时不查，默认显示500条		
				/*	 sql = new StringBuilder().append("SELECT COUNT(*) FROM ( ")
									 .append(mainsql.toString())
									 .append(" ) t");
			   query = session.createSQLQuery(sql.toString());
			   setQueryParams2(query,queryParams);
			   pageModel.setTotalRecords(Integer.valueOf(query.uniqueResult().toString()));*/
				pageModel.setTotalRecords(list.size()>=maxResult?500:list.size());
			   pageModel.setList(list);
			   return null;
			}
		});
		return pageModel;
	}

}
