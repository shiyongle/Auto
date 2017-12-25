package com.dao.user;

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
import com.model.system.Syscfg;
import com.model.user.TSysUser;
import com.util.StringUitl;

@SuppressWarnings("unchecked")
@Repository("userDao")
public class TSysUserDaoImpl extends IBaseDaoImpl<TSysUser,java.lang.Integer> implements TSysUserDao {


	@Override
	public TSysUser login(String fname,String ftel, String fpassword) {
		String hql = "from TSysUser where (fname = ? or ftel = ?) and fpassword= ?"; 
		Query query = this.getSessionFactory().getCurrentSession().createQuery(hql);  
	    query.setString(0, fname);  
	    query.setString(1, StringUitl.isNullOrEmpty(ftel)?fname:ftel);  
	    query.setString(2, fpassword);
	    List<TSysUser> list1 = query.list(); 
	    if(list1 != null && list1.size() > 0){
			return list1.get(0);
		}
	    return null;  
	}
	
	@Override
	public TSysUser weblogin(String fname,String ftel, String fpassword,Integer type) {
		String hql = "from TSysUser where (fname = ? or ftel = ?) and fpassword= ? and ftype= ?"; 
		Query query = this.getSessionFactory().getCurrentSession().createQuery(hql);  
	    query.setString(0, fname);  
	    query.setString(1, ftel);  
	    query.setString(2, fpassword);
	    query.setInteger(3, type);
	    List<TSysUser> list1 = query.list(); 
	    if(list1 != null && list1.size() > 0){
			return list1.get(0);
		}
	    return null;  
	}

	@Override
	public boolean isUnique(String fname) {
		String hql = "from TSysUser where fname = ? or ftel=? or femail=?"; 
		Query query = this.getSessionFactory().getCurrentSession().createQuery(hql);  
	    query.setString(0, fname).setString(1, fname).setString(2, fname);  
		List<TSysUser> list2 = query.list(); 
	    if(list2 != null && list2.size() > 0){
			return false;
		}
	    return true;  
	}

	@Override
	public TSysUser getUser(String name, String tel) {
		String hql = "from TSysUser where fname=? and ftel=?";
		Query query = this.getSessionFactory().getCurrentSession().createQuery(hql);  
		if(name==null || name==""){
			query = this.getSessionFactory().getCurrentSession().createQuery("from TSysUser where ftel=?");  
			query.setString(0, tel);
		}else{
			query.setString(0, name);  
			query.setString(1, tel);
		}
	    List<TSysUser> list3 = query.list(); 
	    if(list3 != null && list3.size() > 0){
			return list3.get(0);
		}
	    return null;  
	}

	@Override
	public List<TSysUser> getByName(String name) {
		String hql = " FROM TSysUser where  fname  like '%" + name + "%' " ;
		return this.getHibernateTemplate().find(hql);
	}

	@Override
	public List<Syscfg> getSyscfg(String supplierid) {
		String hql = "from Syscfg where fuser =? ";
		Query query = this.getSessionFactory().getCurrentSession().createQuery(hql);  
	    query.setString(0, supplierid);  
	    List<Syscfg> list = query.list(); 
	    if(list!=null){
			return list;
		}
		return null;
	}
	
	
	@Override
	public TSysUser getByUserId(String userid) 
	{
		String hql="from TSysUser where fid=?";
		Query query=this.getHibernateTemplate().getSessionFactory().getCurrentSession().createQuery(hql);
		query.setString(0, userid);
		List<TSysUser> list=query.list();
		if(list!=null){
			return list.get(0);
		}
		return null;
	}

	/**
	 * 通过当前登录的父账户id找旗下所有的子账户
	 */
	@Override
	public PageModel<TSysUser> getSubByPid(final String where,final Object[] queryParams, final Map<String, String> orderby, final int pageNo,final int maxResult){
		final PageModel<TSysUser> pageModel = new PageModel<TSysUser>();
		pageModel.setPageNo(pageNo);
		pageModel.setPageSize(maxResult);
		this.getHibernateTemplate().execute(new HibernateCallback() {
			@Override
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				StringBuilder sql = new StringBuilder().append(" select ")
									.append(" su.FID   as fid,")
									.append(" su.FNAME as fname,")
									.append(" su.FTEL as ftel,")
									.append(" su.FEFFECT as feffect,")
									.append(" su.FPARENTID as fparentid,")
									.append(" su.FCREATETIME as fcreatetime")
									.append(" FROM t_sys_user su ")
									.append(where == null ? "" : where)
									.append(createOrderBy(orderby));
				Query query = session.createSQLQuery(sql.toString())
									.addScalar("fid")
									.addScalar("fname")
									.addScalar("ftel")
									.addScalar("feffect")
									.addScalar("fparentid")
									.addScalar("fcreatetime");
				setQueryParams2(query,queryParams);
				query.setResultTransformer(Transformers.aliasToBean(TSysUser.class));
				List<TSysUser> list = query.setFirstResult(getFirstResult(pageNo, maxResult)).setMaxResults(maxResult).list();
						sql = new StringBuilder().append("select count(*)  from ( select su.fid")
									 .append(" FROM t_sys_user su ")
							         .append(where == null ? "" : where)
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
	
	/**
	 * 检测电话号码是否已注册
	 */
	@Override
	public boolean isPhoneUnique(String ftel,String fid) {
		String hql = "from TSysUser where ftel=? and fid!=?"; 
		Query query = this.getSessionFactory().getCurrentSession().createQuery(hql);  
	    query.setString(0, ftel);  
	    query.setString(1, fid);  
		List<TSysUser> list = query.list(); 
	    if(list != null && list.size() > 0){
			return false;
		}
	    return true;  
	}
	
	/**
	 * 检测名字是否有重复
	 */
	@Override
	public boolean isNameUnique(String fname,String fid) {
		String hql = "from TSysUser where fname=? and fid !=? "; 
		Query query = this.getSessionFactory().getCurrentSession().createQuery(hql);  
	    query.setString(0, fname);  
	    query.setString(1, fid);  
		List<TSysUser> list = query.list(); 
	    if(list != null && list.size() > 0){
			return false;
		}
	    return true;  
	}
}
