package com.dao;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.Query;

import com.model.PageModel;
import com.util.Params;



public interface IBaseDao<E, PK extends Serializable> {
	/*** 加载实体对象*/
	public E get(Serializable entityId);
	/*** 获取FID*/
	public String CreateUUid();
	/*** 保存数据*/
	public void save(Object obj);
	/*** 保存或修改数据*/
	public void saveOrUpdate(Object obj);
	/*** 修改数据*/
	public void update(Object obj);
	/*** 删除数据*/
	public void delete(Object obj);
	/*** 通过参数表以及主键删数据*/
	public void delete(String table,String fid);
	
	/*** 通过HQL语句获取对象*/
	public List<E> findByHql(String hql);
	public List<E> findByHql(String hql, Object[] queryParams);
	
	public List QueryBySql(String sql);
	public List QueryBySql(String sql,Params p);
	
	public List QueryByHql(String hql);
	public List QueryByHql(String hql,Object[] queryParams);
	
	public boolean QueryExistsBySql(String sql);
	public boolean QueryExistsBySql(String sql, Params param);
	
	public int ExecBySql(String sql);
	public int ExecBySql(String sql,Params p);

	/*** 使用HQL语句操作*/
	public Object uniqueResult(String hql, Object[] queryParams);
	
	/*** 获取总信息数*/
	public long getCount(String sql,Object[] queryParams);
	/*** 普通分页操作*/
	public PageModel<E> find(int pageNo, int pageSize);
	/*** 分页且带有查询条件的查询方法*/
	public PageModel<E> find(int pageNo, int pageSize,String where, Object[] queryParams);
	/*** 按指定条件排序分页方法*/
	public PageModel<E> find(int pageNo, int pageSize,Map<String, String> orderby);
	/*** 按指定条件分页和排序的分页方法*/
	public PageModel<E> find(String where, Object[] queryParams,Map<String, String> orderby, int pageNo, int pageSize);
	
	public String getNumber(String table, String startstr, int length,boolean istemp);
	public Object Query(Class classname, String fid);
	String getStringValue(String sql, String fieldName);
	public String getCondition(String fidcls) throws Exception;
	public String getFidclsBySql(String... sql);
	/** 用户的"只查看自己"过滤方法; */
	public String QueryFilterByUserofuser(String field,String fmark);

}

