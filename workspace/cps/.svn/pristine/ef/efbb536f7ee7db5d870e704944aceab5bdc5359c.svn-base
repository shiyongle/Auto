package com.dao;

import java.io.Serializable;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.model.PageModel;
import com.model.user.TSysUser;
import com.model.useronline.Useronline;
import com.service.useronline.TsysUseronlineManager;
import com.util.Constant;
import com.util.GenericsUtils;
import com.util.Params;
/**
 * DAO支持类
 */

@SuppressWarnings("unchecked")
public class IBaseDaoImpl<E, PK extends Serializable>  extends HibernateDaoSupport implements IBaseDao<E, PK>{
	@Autowired
	private TsysUseronlineManager tsysUseronlineManager;
	
	private String datestr = "";
	// 泛型的类型
	protected Class<E> entityClass = GenericsUtils.getGenericType(this.getClass());
	
    @Resource(name = "sessionFactory")
    public void setSessionFacotry(SessionFactory sessionFacotry) {  
        super.setSessionFactory(sessionFacotry);  
    }  
     
	@Override
	public E get(Serializable entityId) {
		return (E) this.getSessionFactory().getCurrentSession().get(entityClass, entityId);
	}


	@Override
	public String CreateUUid() {
		Query q = this.getSessionFactory().getCurrentSession().createSQLQuery("SELECT uuid() fid FROM t_sys_mainmenuitem  limit 1");
		List<String> sList = q.list();
		return sList.get(0).toString();
	}

	@Override
	public void save(Object obj) {
		this.getSessionFactory().getCurrentSession().save(obj);
		this.getSessionFactory().getCurrentSession().flush();
	}

	@Override
	public void saveOrUpdate(Object obj) {
		this.getSessionFactory().getCurrentSession().saveOrUpdate(obj);
		this.getSessionFactory().getCurrentSession().flush();
	}

	@Override
	public void update(Object obj) {
		this.getSessionFactory().getCurrentSession().update(obj);
		this.getSessionFactory().getCurrentSession().flush();
	}

	@Override
	public void delete(Object obj) {
		this.getSessionFactory().getCurrentSession().delete(obj);
		this.getSessionFactory().getCurrentSession().flush();
	}
	
	@Override
	public int ExecBySql(String sql){
		Query q = this.getSessionFactory().getCurrentSession().createSQLQuery(sql);
		return q.executeUpdate();
	}
	@Override
	public int ExecBySql(String sql,Params p){
		System.out.println("delete语句："+sql);
		Query q = this.getSessionFactory().getCurrentSession().createSQLQuery(sql);
		Set<String> set = p.keySet();
		Iterator<String> it = set.iterator();
		while (it.hasNext()) {
			String key = it.next();
			if(p.get(key) instanceof String[])
			{
			q.setParameterList(key,(String[])p.get(key));
			}else{
			q.setString(key, p.get(key).toString());
			}
		}
		return q.executeUpdate();
	}
	
	/*** 通过参数表以及主键删数据*/
	@Override
	public void delete(String table,String fid){
		Params params=new Params();
		String sql = "delete from  "+ table + " where fid =:fid";
//		params.put("table", table);
		params.put("fid", fid);
		this.ExecBySql(sql,params);
//		String sql = "delete from " + table + " where fid ='" + fid + "'";
//		this.ExecBySql(sql);
	}

	/*** 通过HQL语句获取对象*/
	@Override
	public List<E> findByHql(String hql) {
		return this.getHibernateTemplate().find(hql);
	}

	@Override
	public List findByHql(final String hql, final Object[] queryParams) {
			Query query = this.getSessionFactory().getCurrentSession().createQuery(hql);//执行查询
				setQueryParams(query, queryParams);//设置查询参数
				return query.list();
	}
	
	@Override
	public List QueryByHql(String hql) {
		return this.getHibernateTemplate().find(hql);
	}
	

	@Override
	public List QueryByHql(final String hql,final Object[] queryParams) {
		
//		Query query = this.getSessionFactory().getCurrentSession().createQuery(hql);//执行查询
//		setQueryParams(query, queryParams);//设置查询参数
//		return query.list();
		return this.getHibernateTemplate().find(hql, queryParams);
	}
	
	
	@Override
	public List QueryBySql(String sql) {
		Query q = this.getSessionFactory().getCurrentSession().createSQLQuery(sql).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		List<HashMap<String, Object>> sList = q.list();
		return sList;
	}

	@Override
	public List QueryBySql(String sql,Params p){
		Query q = this.getSessionFactory().getCurrentSession().createSQLQuery(sql).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		Set<String> set = p.keySet();
		Iterator<String> it = set.iterator();
		while (it.hasNext()) {
			String key = it.next();
			q.setString(key, p.get(key)==null?null: p.get(key).toString());//2016-03-31 纸板下单null报错
		}
		List<HashMap<String, Object>> sList = q.list();
		return sList;
	}
	
	/*** 对query中的参数赋值*/
	protected void setQueryParams(Query query, Object[] queryParams){
		if(queryParams!=null && queryParams.length>0){
			if(queryParams.length ==1){
				query.setParameter(0, queryParams[0]);
			}else{
				for(int i=0; i<queryParams.length; i++){
					if(i ==2){
						query.setParameter(i, queryParams[i]);
					}else{
						query.setParameter(i, "%"+queryParams[i]+"%");
					}
				}
			}
		}
	}
	
	/*** 对query中的参数赋值*/
	protected void setQueryParams2(Query query, Object[] queryParams){
		if(queryParams!=null && queryParams.length>0){
			if(queryParams.length ==1){
				query.setParameter(0, queryParams[0]);
			}else{
				for(int i=0; i<queryParams.length; i++){
						query.setParameter(i, queryParams[i]);
				}
			}
		}
	}
	
	/*** 使用HQL语句操作*/
	@Override
	public Object uniqueResult(final String hql, final Object[] queryParams) {
		return this.getHibernateTemplate().execute(new HibernateCallback() {
			@Override
			public Object doInHibernate(Session session) throws HibernateException,SQLException {
				Query query = session.createQuery(hql);//执行查询
				setQueryParams(query, queryParams);//设置查询参数
				return query.uniqueResult();
			}
		});
	}
	
	/**
	 * 获取分页查询中结果集的起始位置
	 * @param pageNo 第几页
	 * @param maxResult 页面显示的记录数
	 */
	protected int getFirstResult(int pageNo,int maxResult){
		int firstResult = (pageNo-1) * maxResult;
		return firstResult < 0 ? 0 : firstResult;
	}
	
	/**
	 * 创建排序HQL语句
	 * @param orderby
	 * @return 排序字符串
	 */
	protected String createOrderBy(Map<String, String> orderby){
		StringBuffer sb = new StringBuffer("");
		if(orderby != null && orderby.size() > 0){
			sb.append(" order by ");
			for(String key : orderby.keySet()){
				sb.append(key).append(" ").append(orderby.get(key)).append(",");
			}
			sb.deleteCharAt(sb.length() - 1);
		}
		return sb.toString();
	}
	
	/*** 获取总条数*/
	@Override
	public long getCount(String sql,Object[] queryParams) {
		String hql = "select count(*) from " + GenericsUtils.getGenericName(this.entityClass) + sql;
		return (Long)uniqueResult(hql,queryParams);
	}

	/*** 普通分页操作*/
	@Override
	public PageModel<E> find(int pageNo, int pageSize) {
		return find(null, null, null, pageNo, pageSize);
	}

	/*** 搜索信息分页方法*/
	@Override
	public PageModel<E> find(int pageNo, int pageSize, String where,Object[] queryParams) {
		return find(where, queryParams, null, pageNo, pageSize);
	}

	/*** 按指定条件排序分页方法*/
	@Override
	public PageModel<E> find(int pageNo, int pageSize,Map<String, String> orderby) {
		return find(null, null, orderby, pageNo, pageSize);
	}


	@Override
	public PageModel<E> find(final String where, final Object[] queryParams,final Map<String, String> orderby, final int pageNo, final int pageSize) {
		final PageModel<E> pageModel = new PageModel<E>();//实例化分页对象
		pageModel.setPageNo(pageNo);//设置当前页数
		pageModel.setPageSize(pageSize);//设置每页显示记录数
		this.getHibernateTemplate().execute(new HibernateCallback() {//执行内部方法
			@Override
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				String hql = new StringBuffer().append("from ")
								.append(GenericsUtils.getGenericName(entityClass))
								.append(" ")
								.append(where == null ? "" : where)
								.append(createOrderBy(orderby)).toString();
				Query query = session.createQuery(hql);
				setQueryParams(query,queryParams);
				List<E> list = null;
				// 如果maxResult<0，则查询所有
				if(pageSize < 0 && pageNo < 0){
					list = query.list();
				}else{
					list = query.setFirstResult(getFirstResult(pageNo, pageSize)).setMaxResults(pageSize).list();
					hql = new StringBuffer().append("select count(*) from ")
									.append(GenericsUtils.getGenericName(entityClass))
									.append(" ")
									.append(where == null ? "" : where).toString();
					query = session.createQuery(hql);
					setQueryParams(query,queryParams);
					int totalRecords = ((Long) query.uniqueResult()).intValue();
					pageModel.setTotalRecords(totalRecords);
				}
				pageModel.setList(list);
				return null;
			}
		});
		return pageModel;
	}
	
	private void checkdatetime(){
		Date nowdate = new Date();
		SimpleDateFormat df = new SimpleDateFormat("yyMMdd");
		String nowdatestr = df.format(nowdate);
		HashMap<String, Integer> TempNumbercls = new HashMap<>();
		HashMap<String, Integer> Numbercls = new HashMap<>();
		if(!nowdatestr.equals(datestr)){
			datestr=nowdatestr;
			Set<String> keys = Numbercls.keySet();
			Iterator<String> it = keys.iterator();
			while (it.hasNext()) {
				Numbercls.put(it.next(), 0);
			}
			Set<String> tempkeys = TempNumbercls.keySet();
			Iterator<String> tempit = tempkeys.iterator();
			while (tempit.hasNext()) {
				TempNumbercls.put(tempit.next(), 0);
			}
		}
	}
	
	private int getDataBaseNumber(String table, String nowString, int length)
	{
		String sql="select max(fnumber) fnumber from "+table+"  where fnumber like '"+nowString+"%' and LENGTH(fnumber)="+(nowString.length()+length);
		Params p = new Params();
		List<HashMap<String, Object>> sList= this.QueryBySql(sql,p);
		if(sList.size()>0){
			if(sList.get(0).get("fnumber")!=null){
				String fnumber=sList.get(0).get("fnumber").toString();
				return Integer.parseInt(fnumber.substring(fnumber.length()-length, fnumber.length()));
			}
		}
		return 0;
	}

	public String getNumber(String table, String startstr, int length,boolean istemp) {
		HashMap<String, Integer> TempNumbercls = new HashMap<>();
		HashMap<String, Integer> Numbercls = new HashMap<>();
		checkdatetime();
		String key =table+startstr+length;
		String nowString=startstr+datestr;
		int num;
		if (istemp) {
			if(TempNumbercls.containsKey(key)){
				num=TempNumbercls.get(key)+1;
			}else{
				num=getDataBaseNumber(table, nowString, length)+1;
			}
			TempNumbercls.put(key, num);
			return nowString+"000000".substring(0,length-(num+"").length())+num;
		}else{
			if(Numbercls.containsKey(key)){
				num=Numbercls.get(key)+1;
			}else{
				num=getDataBaseNumber(table, nowString, length)+1;
			}
			Numbercls.put(key, num);
			return nowString+"000000".substring(0,length-(num+"").length())+num;
		}
	}

	@Override
	public boolean QueryExistsBySql(String sql) {
		if(sql.indexOf("limit")==-1){
			sql += " limit 0,1";
		}
		List<HashMap<String, Object>> sList = this.QueryBySql(sql);
		return sList.size()>0;
	}
	@Override
	public boolean QueryExistsBySql(String sql,Params param) {
		if(sql.indexOf("limit")==-1){
			sql += " limit 0,1";
		}
		List<HashMap<String, Object>> sList = this.QueryBySql(sql,param);
		return sList.size()>0;
	}
	
	



	
	@Override
	public Object Query(Class classname, String fid) {
		// TODO Auto-generated method stub
		return this.getHibernateTemplate().get(classname, fid);
	}
	/**
	 * 查询字段值
	 * @param sql
	 * @param fieldName
	 * @return
	 */
	@Override
	public String getStringValue(String sql,String fieldName){
		HashMap map = getMapData(sql);
		if(map==null || map.get(fieldName)==null){
			return null;
		}
		return map.get(fieldName)+ "";
	}
	public HashMap<String, String> getMapData(String sql){
		List<HashMap<String, String>> list = this.QueryBySql(sql);
		if(list.size()==0){
			return null;
		}
		return list.get(0);
	}
	@Override//构造fid拼接
	public String getCondition (String fidcls) throws Exception{
		if(StringUtils.isEmpty(fidcls)){
			throw new Exception("ID不存在！");
		}
		String condition;
		if(fidcls.split(",").length==1){
			condition = " = '"+fidcls+"' ";
		}else{
			condition = " in ('"+fidcls.replace(",", "','")+"') ";
		}
		return condition;
	}
	@Override//获取ID集合
	public String getFidclsBySql(String... sql){
		Set<String> set = new HashSet<>();
		List<HashMap<String, Object>> list;
		for(String s : sql){
			list = this.QueryBySql(s);
			if(list.size()!=0){
				for(HashMap<String, Object> map : list){
					set.add(map.get("ID").toString());
				}
			}
		}
		if(set.size()==0){
			return "";
		}
		StringBuilder sb = new StringBuilder();
		for(String s : set){
			sb.append(s+",");
		}
		String fidcls = sb.toString();
		return fidcls.substring(0, fidcls.length()-1);
	}
	
	/**
	 * 用户的"只查看自己"过滤方法;
	 * **/
	public String QueryFilterByUserofuser(String field,String fmark){
		String userid = ServletActionContext.getRequest().getSession().getAttribute(Constant.SESSION_USERID).toString();
		TSysUser userinfo=(TSysUser) this.getHibernateTemplate().get(TSysUser.class, userid);
		String strsql="";
		String sfmark="";
		if(StringUtils.isEmpty(fmark))
		{
			sfmark=" and "; 
		}else
		{
			sfmark=fmark;
		}
		if(userinfo.getFisreadonly()==0) return "";
		if(field!=null && !field.equals(""))
		{
			String sqls = "select quote(fuserid) userid from t_bd_usertouser where fsuperuserid='"+userid+"'";
			List<HashMap<String, Object>> demandList = QueryBySql(sqls);
			if(demandList.size()!=0){//关联用户过滤
				StringBuilder userids=new StringBuilder("'"+userid+"'");
				for(HashMap<String, Object> m : demandList){
					userids.append(","+m.get("userid"));
				}
				strsql = field +" in ("+userids+")";
			}else{
				strsql = field+ " = '"+userid+"'";
			}
		}
		if(strsql.equals("")){
			return "";
		}
		return " "+sfmark +" ("+strsql+")";
	}

	
}
