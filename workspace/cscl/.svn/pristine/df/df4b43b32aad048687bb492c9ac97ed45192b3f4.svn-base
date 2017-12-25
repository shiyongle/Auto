package com.pc.dao.aBase;

import java.io.Serializable;
import java.util.List;

import org.springframework.dao.DataAccessException;

import cn.org.rapid_framework.page.Page;
import cn.org.rapid_framework.page.PageRequest;

/**
 * @author 王超
 */
public interface IBaseDao<E, PK extends Serializable> {
	//新增
	public int save(E entity) throws DataAccessException;
	//删除
	public int deleteById(PK id) throws DataAccessException;
	//更新
	public int update(E entity) throws DataAccessException;
	//通过id获取对象
	public E getById(PK id) throws DataAccessException;
	//查询满足条件数据
	public List<E> find(PageRequest query);
	//在某一页中查询
	public List<E> find(PageRequest query, int limit);
	//分页查询-自动调用findPageCount
	public Page<E> findPage(PageRequest query);

}