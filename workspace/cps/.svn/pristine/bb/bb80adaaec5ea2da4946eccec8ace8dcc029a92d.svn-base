package com.dao.firstproductdemand;

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
import com.model.firstproductdemand.Firstproductdemand;

/*
 * CPS-VMI-wangc
 */

@SuppressWarnings("unchecked")
@Repository("tordFirstproductdemandDao")
public class TordFirstproductdemandDaoImpl extends IBaseDaoImpl<Firstproductdemand,java.lang.String> implements TordFirstproductdemandDao{

	@Override
	public PageModel<Firstproductdemand> findBySql(final String where,final Object[] queryParams, final Map<String, String> orderby, final int pageNo,final int maxResult,final String...t_name) {
		final PageModel<Firstproductdemand> pageModel = new PageModel<Firstproductdemand>();
		pageModel.setPageNo(pageNo);
		pageModel.setPageSize(maxResult);
		this.getHibernateTemplate().execute(new HibernateCallback() {
			@Override
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				//2016-3-21 lxx
				String tablename="t_ord_firstproductdemand";
				if(t_name.length!=0&&t_name[0]!=null)
				{
					System.out.println("三个月前数据的表名更改为:"+t_name[0]);
					tablename=t_name[0];
				}
				StringBuilder sql = new StringBuilder().append(" select ")
									.append(" if(u2.fqq='','1718733793',u2.fqq)     as  fqq,")
									.append(" c.fname                      as  customerName,")
									.append(" f.fid                        as  fid,")
									.append(" f.fname                      as  fname,")
									.append(" f.fnumber                    as  fnumber,")
									.append(" u1.fname                     as fcreatid,")
									.append(" f.fcreatetime                as fcreatetime,")
									.append(" ifnull(u2.fname,'设计咨询')          AS freceiver,")
									.append(" ifnull(u2.ftel, '0577-59395101')          AS freceiverTel,")
									.append(" ifnull(f.freceivetime, '')   as freceivetimeString,")
									.append(" ifnull(u3.fname, '')         AS fauditorid,")
									.append(" ifnull(date_format(f.fauditortime,'%Y-%m-%d'), '')   AS fauditortimeString,")
									.append(" ifnull(date_format(f.fauditortime,'%H:%i:%s'), '')   AS fTimeString,")
									.append(" date_format(f.foverdate,'%Y-%m-%d %H:%i') as foverdateString,")
									.append(" date_format(f.farrivetime,'%Y-%m-%d %H:%i') as farrivetimeString,")
									.append(" f.fsupplierid                as fsupplierid,")
									.append(" sp.fname                     as supplierName,")
									.append(" ifnull(f.fcostneed, '')      as fcostneed,")
									.append(" f.fiszhiyang                 as fiszhiyang,")
									.append(" f.fdescription               as fdescription,")
									.append(" f.isfauditor                 as isfauditor,")
									.append(" f.freceived                  as freceived,")
									.append(" f.falloted                   as falloted,")
									.append(" ifnull(f.fisaccessory, '')   as fisaccessoryString,")
									//.append(" ifnull(f.fstate, '') 		   as fstate,")
									.append(" case f.`fstate` when '已发布' then '未接收' else ifnull(f.fstate, '') end as fstate,")
									//2015-10-28 显示接收人名字
									//.append(" f.flinkman                   as flinkman,")
									.append(" u2.fname  as flinkman ,")
									//2015-10-26  根据状态不同联系方式不同
								   // .append(" f.flinkphone                 as flinkphone,")
									.append(" case f.fstate when '存草稿' then '0577-59395101' when '已发布' then '0577-59395101' else  u2.ftel end as flinkphone ,")
									.append(" f.fisdemandpackage           as fisdemandpackage,")
									.append(" f.fpurordernumber           as fpurordernumber,")
									.append(" f.fdesignproviderid           as fdesignproviderid,")
									.append(" f.fresourceschemeid           as fresourceschemeid,")
									.append(" f.fgroupid                   as fgroupid")
									.append(" FROM "+tablename+" f ")
									.append(" LEFT JOIN t_sys_user u1 ON u1.fid = f.fcreatid ")
									.append(" LEFT JOIN t_sys_user u2 ON u2.fid = f.freceiver ")
									.append(" LEFT JOIN t_sys_user u3 ON u3.fid = f.fauditorid ")
									.append(" LEFT JOIN t_bd_customer c ON c.fid = f.fcustomerid ")
									.append(" LEFT JOIN t_sys_supplier sp ON sp.fid = f.fsupplierid ")
									.append(where == null ? "" : where)
//									.append(where == null ? "where 1=1 "+QueryFilterByUserofuser("f.fcreatid","and") : where+QueryFilterByUserofuser("f.fcreatid","and"))
									.append(createOrderBy(orderby));
				Query query = session.createSQLQuery(sql.toString())
									.addScalar("fqq")
									.addScalar("customerName")
									.addScalar("fid")
									.addScalar("fname")
									.addScalar("fnumber")
									.addScalar("fcreatid")
									.addScalar("fcreatetime")
									.addScalar("freceiver")
									.addScalar("freceiverTel")
									.addScalar("freceivetimeString")
									.addScalar("fauditorid")
									.addScalar("fauditortimeString")
									.addScalar("fTimeString")
									.addScalar("foverdateString")
									.addScalar("farrivetimeString")
									.addScalar("fsupplierid")
									.addScalar("supplierName")
									.addScalar("fcostneed")
									.addScalar("fiszhiyang")
									.addScalar("fdescription")
									.addScalar("isfauditor")
									.addScalar("freceived")
									.addScalar("falloted")
									.addScalar("fisaccessoryString")
									.addScalar("fstate")
									.addScalar("flinkman")
									.addScalar("flinkphone")
									.addScalar("fisdemandpackage")
									.addScalar("fpurordernumber")
									.addScalar("fdesignproviderid")
									.addScalar("fresourceschemeid")
									.addScalar("fgroupid");
				setQueryParams2(query,queryParams);
				query.setResultTransformer(Transformers.aliasToBean(Firstproductdemand.class));
				List<Firstproductdemand> list = query.setFirstResult(getFirstResult(pageNo, maxResult)).setMaxResults(maxResult).list();
							 sql = new StringBuilder().append("select count(*)")
									 .append(" FROM "+tablename+" f ")
									 .append(" LEFT JOIN t_sys_user u1 ON u1.fid = f.fcreatid ")
									 .append(" LEFT JOIN t_sys_user u2 ON u2.fid = f.freceiver ")
									 .append(" LEFT JOIN t_sys_user u3 ON u3.fid = f.fauditorid ")
									 .append(" LEFT JOIN t_bd_customer c ON c.fid = f.fcustomerid ")
									 .append(" LEFT JOIN t_sys_supplier sp ON sp.fid = f.fsupplierid ")
									 .append(where == null ? "" : where);
//									 .append(where == null ? "where 1=1 "+QueryFilterByUserofuser("f.fcreatid","and") : where+QueryFilterByUserofuser("f.fcreatid","and"));
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
	public List<Firstproductdemand> execlBySql(final String where,final Object[] queryParams, final Map<String, String> orderby,final String...t_name) {
		List<Firstproductdemand> pul =(List<Firstproductdemand>) this.getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				//2016-3-22 lxx 历史数据分离
				String tablename="t_ord_firstproductdemand";
				if(t_name.length!=0&&t_name[0]!=null)
				{
					tablename=t_name[0];
				}
				StringBuilder sql = new StringBuilder().append(" select ")
									.append(" ifnull(u2.fqq,'1718733793')                       as  fqq,")
									.append(" c.fname                      as  customerName,")
									.append(" f.fid                        as  fid,")
									.append(" f.fname                      as  fname,")
									.append(" f.fnumber                    as  fnumber,")
									.append(" u1.fname                     as fcreatid,")
									.append(" f.fcreatetime                as fcreatetime,")
									.append(" ifnull(u2.fname,'设计咨询')          AS freceiver,")
									.append(" ifnull(u2.ftel, '0577-59395101')          AS freceiverTel,")
									.append(" ifnull( date_format(f.freceivetime,'%Y-%m-%d %H:%i'), '')   as freceivetimeString,")
									.append(" ifnull(u3.fname, '')         AS fauditorid,")
									.append(" ifnull(date_format(f.fauditortime,'%Y-%m-%d %H:%i'), '')   AS fauditortimeString,")
									.append(" ifnull(date_format(f.fauditortime,'%H:%i:%s'), '')   AS fTimeString,")
									.append(" date_format(f.foverdate,'%Y-%m-%d %H:%i') as foverdateString,")
									.append(" date_format(f.farrivetime,'%Y-%m-%d %H:%i') as farrivetimeString,")
									.append(" f.fsupplierid                as fsupplierid,")
									.append(" sp.fname                     as supplierName,")
									.append(" ifnull(f.fcostneed, '')      as fcostneed,")
									.append(" f.fiszhiyang                 as fiszhiyang,")
									.append(" f.fdescription               as fdescription,")
									.append(" f.isfauditor                 as isfauditor,")
									.append(" f.freceived                  as freceived,")
									.append(" f.falloted                   as falloted,")
									.append(" ifnull(f.fisaccessory, '')   as fisaccessoryString,")
									.append(" ifnull(f.fstate, '') 		   as fstate,")
									.append(" f.flinkman                   as flinkman,")
									.append(" f.flinkphone                 as flinkphone,")
									.append(" f.famount                    as famount,")
									.append(" f.fdescription               as fdescription,")
									.append(" f.fisdemandpackage           as fisdemandpackage,")
									.append(" f.fpurordernumber           as fpurordernumber,")
									.append(" f.fgroupid                   as fgroupid,")
									.append(" f.fresourceschemeid                   as fresourceschemeid")
									.append(" FROM "+tablename+" f ")
									.append(" LEFT JOIN t_sys_user u1 ON u1.fid = f.fcreatid ")
									.append(" LEFT JOIN t_sys_user u2 ON u2.fid = f.freceiver ")
									.append(" LEFT JOIN t_sys_user u3 ON u3.fid = f.fauditorid ")
									.append(" LEFT JOIN t_bd_customer c ON c.fid = f.fcustomerid ")
									.append(" LEFT JOIN t_sys_supplier sp ON sp.fid = f.fsupplierid ")
									.append(where == null ? "" : where)
//									.append(where == null ? "where 1=1 "+QueryFilterByUserofuser("f.fcreatid","and") : where+QueryFilterByUserofuser("f.fcreatid","and"))
									.append(createOrderBy(orderby));
				Query query = session.createSQLQuery(sql.toString())
									.addScalar("fqq")
									.addScalar("customerName")
									.addScalar("fid")
									.addScalar("fname")
									.addScalar("fnumber")
									.addScalar("fcreatid")
									.addScalar("fcreatetime")
									.addScalar("freceiver")
									.addScalar("freceiverTel")
									.addScalar("freceivetimeString")
									.addScalar("fauditorid")
									.addScalar("fauditortimeString")
									.addScalar("fTimeString")
									.addScalar("foverdateString")
									.addScalar("farrivetimeString")
									.addScalar("fsupplierid")
									.addScalar("supplierName")
									.addScalar("fcostneed")
									.addScalar("fiszhiyang")
									.addScalar("fdescription")
									.addScalar("isfauditor")
									.addScalar("freceived")
									.addScalar("falloted")
									.addScalar("fisaccessoryString")
									.addScalar("fstate")
									.addScalar("flinkman")
									.addScalar("flinkphone")
									.addScalar("famount")
									.addScalar("fdescription")
									.addScalar("fisdemandpackage")
									.addScalar("fpurordernumber")
									.addScalar("fgroupid")
									.addScalar("fresourceschemeid");
				setQueryParams2(query,queryParams);
				query.setResultTransformer(Transformers.aliasToBean(Firstproductdemand.class));
			   return  query.list();
			}
		});
		return pul;
	}	
}
