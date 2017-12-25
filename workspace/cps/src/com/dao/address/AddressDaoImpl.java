package com.dao.address;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.hibernate.type.Type;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;
import com.dao.IBaseDaoImpl;
import com.model.PageModel;
import com.model.address.Address;
import com.model.user.TSysUser;

@SuppressWarnings("unchecked")
@Repository("addressDaoDao")
public class AddressDaoImpl extends IBaseDaoImpl<Address, java.lang.Integer> implements AddressDao {

	@Override
	public List<Address> getByCustomerId(final String customerId,final String userid) {
		TSysUser user  = (TSysUser) this.Query(TSysUser.class, userid);
		List<Object> ls =new ArrayList<Object>();
		final StringBuilder sb = new StringBuilder();
		if(user.getFisreadonly()==1){
			sb.append("SELECT s.fid,s.fname,s.fdetailaddress FROM t_bd_useraddress ud " );
//			sb.append("LEFT JOIN t_bd_address s ON s.fid=ud.faddress WHERE ud.fuserid='"+userid+"' order by ud.fdefault desc ");					
			sb.append("LEFT JOIN t_bd_address s ON s.fid=ud.faddress WHERE ud.fuserid=? order by ud.fdefault desc ");					
			ls.add(userid);
		}
		else{
			sb.append("SELECT s.fid,s.fname,s.fdetailaddress,'1' AS typeaddress FROM t_bd_useraddress ud ");
//			sb.append("LEFT JOIN t_bd_address s ON s.fid=ud.faddress WHERE ud.fuserid= '"+userid+"' AND ud.fdefault=1  ");
			sb.append("LEFT JOIN t_bd_address s ON s.fid=ud.faddress WHERE ud.fuserid= ? AND ud.fdefault=1  ");
			sb.append("UNION  ");
			sb.append("SELECT s.fid,s.fname,s.fdetailaddress,'0' AS typeaddress FROM t_bd_custrelationadress c LEFT JOIN t_bd_address s  ");
//			sb.append("ON s.fid=c.faddressid WHERE c.feffect=1 AND c.fcustomerid= '"+customerId+"' ");
//			sb.append(" AND NOT EXISTS (SELECT fid FROM t_bd_useraddress WHERE fuserid= '"+userid+"' AND faddress = s.fid AND fdefault=1) ");
			sb.append("ON s.fid=c.faddressid WHERE c.feffect=1 AND c.fcustomerid= ? ");
			sb.append(" AND NOT EXISTS (SELECT fid FROM t_bd_useraddress WHERE fuserid= ? AND faddress = s.fid AND fdefault=1) ");
			sb.append(" ORDER BY typeaddress DESC ");
			ls.add(userid);
			ls.add(customerId);
			ls.add(userid);
		}
		final Object[] param=(Object[])ls.toArray(new Object[ls.size()]);
		List<Address> address =(List<Address>) this.getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Query query = session.createSQLQuery(sb.toString())
									.addScalar("fid")
									.addScalar("fname")
									.addScalar("fdetailaddress");
				setQueryParams2(query, param);
//				query.setParameter(0, userid);
				query.setResultTransformer(Transformers.aliasToBean(Address.class));
			   return  query.list();
			}
		});
		return address;
	}
	@Override
	public List<Address> getByCustomerId(final String customerId) {
		List<Address> address =(List<Address>) this.getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				StringBuilder sql = new StringBuilder().append(" select ")
									.append(" addr.FID   as fid,")
									.append(" addr.fname   as fname,")
									.append(" (CONCAT(addr.FDETAILADDRESS,'   联系人:',addr.flinkman,'  联系电话:',addr.fphone)) as fdetailaddress")
									.append(" FROM t_bd_address addr ")
									.append(" WHERE addr.FCUSTOMERID = ?" );
				Query query = session.createSQLQuery(sql.toString())
									.addScalar("fid")
									.addScalar("fname")
									.addScalar("fdetailaddress");
				query.setParameter(0, customerId);
				query.setResultTransformer(Transformers.aliasToBean(Address.class));
			   return  query.list();
			}
		});
		return address;
	}
	@Override
	public PageModel<Address> findBySql(final String where,final Object[] queryParams, final Map<String, String> orderby, final int pageNo,final int maxResult) {
		TSysUser user  = (TSysUser) this.Query(TSysUser.class, where);
		StringBuilder sb = new StringBuilder("");
		if(user.getFisreadonly()==1){
			//2015-12-30  CONVERT 函数统一int类型，否则默认会是 java.math.BigInteger,禁用地址暂未显示
			sb.append("SELECT ad.fid fid,ud.fid cdid,cu.FNAME customerName,ad.FDETAILADDRESS fdetailaddress,ad.FLINKMAN flinkman,ad.FPHONE fphone,2 AS feffect,ifnull(UD.fdefault,0) AS fdefault  ");
			sb.append("FROM t_bd_useraddress ud JOIN t_bd_address ad  ON ad.FID = ud.faddress  ");
			sb.append("JOIN t_bd_usercustomer  uc ON uc.FUSERID = ud.fuserid  ");
			sb.append("JOIN t_bd_customer cu ON cu.fid = uc.FCUSTOMERID  ");
			sb.append("JOIN t_sys_user u ON u.FID = ud.fuserid ");
			sb.append("where  u.FID='"+where+"' ");	
		}
		else{
			sb.append("SELECT ad.fid fid,cd.fid cdid,cu.FNAME customerName,ad.FDETAILADDRESS fdetailaddress,ad.FLINKMAN flinkman,ad.FPHONE fphone, CONVERT(cd.feffect,SIGNED) as feffect, ");
			sb.append("CASE  WHEN IFNULL(ud.fdefault,0) = 1 THEN 1 ELSE 0 END fdefault ");
			sb.append("FROM t_bd_custrelationadress cd ");
			sb.append("JOIN t_bd_address ad  ON ad.FID = cd.faddressid ");
			sb.append("JOIN t_bd_usercustomer  uc ON uc.FCUSTOMERID = cd.fcustomerid ");
			sb.append("JOIN t_bd_customer cu ON cu.fid = uc.FCUSTOMERID ");
			sb.append("JOIN t_sys_user u ON u.FID = uc.FUSERID ");
			sb.append("LEFT JOIN t_bd_useraddress ud ON ud.faddress= cd.faddressid and ud.fuserid = u.FID ");
			sb.append("where u.FID='"+where+"' and cd.feffect = 1 ");	
		}
		final String querysb = sb.toString();
		sb = sb.replace(0, sb.indexOf("FROM "), "select count(1) ");
		final String querytosb = sb.toString();
		final PageModel<Address> pageModel = new PageModel<Address>();
		pageModel.setPageNo(pageNo);
		pageModel.setPageSize(maxResult);
		this.getHibernateTemplate().execute(new HibernateCallback() {
			@Override
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Query query = session.createSQLQuery(querysb)
									.addScalar("fid")
									.addScalar("cdid")
									.addScalar("customerName")
									.addScalar("fdetailaddress")
									.addScalar("flinkman")
									.addScalar("fphone")
									.addScalar("feffect")
									.addScalar("fdefault");
				setQueryParams2(query,queryParams);
				query.setResultTransformer(Transformers.aliasToBean(Address.class));
				List<Address> list = query.setFirstResult(getFirstResult(pageNo, maxResult)).setMaxResults(maxResult).list();

			   query = session.createSQLQuery(querytosb);
			   setQueryParams2(query,queryParams);
			   pageModel.setTotalRecords(Integer.valueOf(query.uniqueResult().toString()));
			   pageModel.setList(list);
			   return null;
			}
		});
		return pageModel;
	}

	@Override
	public List<Address> getById(final String where, final Object[] queryParams) {
		List<Address> pul =(List<Address>) this.getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				StringBuilder sql = new StringBuilder().append(" select ")
									.append(" ad.fid fid,")
									.append(" cd.fid cdid,")
									.append(" cu.FNAME customerName,")
									.append(" ad.FDETAILADDRESS fdetailaddress,")
									.append(" ad.FLINKMAN flinkman,")
									.append(" ad.FPHONE fphone,")
									.append(" cd.feffect feffect,")
									.append(" CASE WHEN IFNULL(ud.fdefault, 0) = 1 THEN 1 ELSE 0 END  fdefault")
									.append(" FROM t_bd_custrelationadress cd ")
									.append(" JOIN t_bd_address ad ON ad.FID = cd.faddressid")
									.append(" JOIN t_bd_usercustomer uc ON uc.FCUSTOMERID = cd.fcustomerid")
									.append(" JOIN t_bd_customer cu ON cu.fid = uc.FCUSTOMERID")
									.append(" JOIN t_sys_user u ON u.FID = uc.FUSERID")
									.append(" LEFT JOIN t_bd_useraddress ud ON ud.faddress = cd.faddressid AND ud.fuserid = u.FID")
									.append(where == null ? "" : where);
				Query query = session.createSQLQuery(sql.toString())
									.addScalar("fid")
									.addScalar("cdid")
									.addScalar("customerName")
									.addScalar("fdetailaddress")
									.addScalar("flinkman")
									.addScalar("fphone")
									.addScalar("feffect")
									.addScalar("fdefault");
				setQueryParams2(query,queryParams);
				query.setResultTransformer(Transformers.aliasToBean(Address.class));
			   return  query.list();
			}
		});
		return pul;
	}
	
}
