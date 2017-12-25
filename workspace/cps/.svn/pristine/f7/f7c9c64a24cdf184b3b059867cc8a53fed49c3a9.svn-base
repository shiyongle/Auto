package com.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import com.dao.IBaseDao;
import com.model.PageModel;
import com.util.Params;


@Transactional
public abstract class IBaseManagerImpl<E, PK extends Serializable> implements IBaseManager<E, PK> {
	
	protected abstract IBaseDao<E, PK> getEntityDao();
	
	/*** 加载实体对象*/
	public E get(Serializable entityId){
		return this.getEntityDao().get(entityId);
	}
	
	/*** 获取FID*/
	@Transactional(readOnly = true)
	public String CreateUUid(){
		return getEntityDao().CreateUUid();
	}
	/*** 保存数据*/
	@Transactional
	public void save(Object obj){
		 getEntityDao().save(obj);
	}
	/*** 保存或修改数据*/
	@Transactional
	public void saveOrUpdate(Object obj){
		 getEntityDao().saveOrUpdate(obj);
	}
	/*** 修改数据*/
	@Transactional
	public void update(Object obj){
		 getEntityDao().update(obj);
	}
	/*** 删除数据*/
	@Transactional
	public void delete(Object obj){
		getEntityDao().delete(obj);
	}
	/*** 通过参数表以及主键删数据*/
	@Transactional
	public void delete(String table,String fid){
		getEntityDao().delete(table,fid);
	}
	@Transactional
	public int ExecBySql(String sql){
		return getEntityDao().ExecBySql(sql);
	}
	@Transactional
	public int ExecBySql(String sql,Params param){
		return getEntityDao().ExecBySql(sql,param);
	}
	
	/*** 通过HQL语句获取对象*/
	@Override
	@Transactional(readOnly=true)
	public List<E> findByHql(String hql){
		return getEntityDao().findByHql(hql);
	}
	@Override
	@Transactional(readOnly=true)
	public List QueryBySql(String sql,Params p){
		return getEntityDao().QueryBySql(sql,p);
	}
	
	/*** 获取总信息数*/
	@Override
	@Transactional(readOnly=true)
	public long getCount(String sql,Object[] queryParams){
		return getEntityDao().getCount(sql,queryParams);
	}
	/*** 普通分页操作*/
	@Override
	@Transactional(readOnly=true)
	public PageModel<E> find(int pageNo, int pageSize){
		return getEntityDao().find(pageNo,pageSize);
	}
	/*** 搜索信息分页方法*/
	@Override
	@Transactional(readOnly=true)
	public PageModel<E> find(int pageNo, int pageSize,String where, Object[] queryParams){
		return getEntityDao().find(pageNo,pageSize,where,queryParams);
	}
	/*** 按指定条件排序分页方法*/
	@Override
	@Transactional(readOnly=true)
	public PageModel<E> find(int pageNo, int pageSize,Map<String, String> orderby){
		return getEntityDao().find(pageNo,pageSize,orderby);
	}
	/*** 按指定条件分页和排序的分页方法*/
	@Override
	@Transactional(readOnly=true)
	public PageModel<E> find(String where, Object[] queryParams,Map<String, String> orderby, int pageNo, int pageSize){
		return getEntityDao().find(where,queryParams,orderby,pageNo,pageSize);
	}
	@Override
	@Transactional(readOnly=true)
	public boolean QueryExistsBySql(String sql){
		return getEntityDao().QueryExistsBySql(sql);
	}
	@Override
	@Transactional(readOnly=true)
	public boolean QueryExistsBySql(String sql,Params param){
		return getEntityDao().QueryExistsBySql(sql,param);
	}
	@Override
	@Transactional(readOnly=true)
	public String getNumber(String table, String startstr, int length,boolean istemp){
		return getEntityDao().getNumber(table,startstr,length,istemp);
	}
	@Override
	public String alertMsg(String msg){
		return "<script>alert('"+msg+"');history.back(-1);</script>";
	}
}
