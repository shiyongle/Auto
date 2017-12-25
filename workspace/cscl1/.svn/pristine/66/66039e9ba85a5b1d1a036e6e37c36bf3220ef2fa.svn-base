package com.pc.dao.aBase.impl;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.org.rapid_framework.page.Page;
import cn.org.rapid_framework.page.PageRequest;

import com.pc.dao.aBase.IBaseDao;

/*** 
 * wangc
 * */
//@Transactional
public class BaseDao<E, PK extends Serializable> extends SqlSessionDaoSupport implements IBaseDao<E, PK> {
	
	protected Log log = LogFactory.getLog(getClass());
	
	public String getIbatisSqlMapNamespace() {
		throw new RuntimeException("尚未实现");
	}
	
	public String getFindByPrimaryKeyStatement() {
		return getIbatisSqlMapNamespace() + ".getById";
	}
	/***通过主键获取对象**/
	@Transactional(propagation=Propagation.NOT_SUPPORTED,readOnly=true)
	public E getById(PK primaryKey) {
		@SuppressWarnings("unchecked")
		E object = (E) getSqlSession().selectOne(getFindByPrimaryKeyStatement(), primaryKey);
		return object;
	}
	
	
	public String getDeleteStatement() {
		return getIbatisSqlMapNamespace() + ".delete";
	}
	
	
	/***通过主键删除对象*/
	@Transactional(propagation=Propagation.REQUIRED)
	public int deleteById(PK id) {
		return getSqlSession().delete(getDeleteStatement(), id);
	}
	
	
	public String getInsertStatement() {
		return getIbatisSqlMapNamespace() + ".insert";
	}
	
	/***插入对象*/
	@Transactional(propagation=Propagation.REQUIRED) 
	public int save(E entity) {
		return getSqlSession().insert(getInsertStatement(), entity);
	}
	
	
	public String getUpdateStatement() {
		return getIbatisSqlMapNamespace() + ".update";
	}
	
	/*** 更新数据*/
	@Transactional(propagation=Propagation.REQUIRED)
	public int update(E entity) {
		return getSqlSession().update(getUpdateStatement(), entity);
	}
	
	
	public String getCountStatementForPaging(String statementName) {
		return statementName + "Count";
	}
	
	
	public String getFindStatement() {
		return getIbatisSqlMapNamespace() + ".find";
	}
	
	@SuppressWarnings("unchecked")
	protected List<E> listQuery(String statementName, PageRequest pageRequest) {
		List list = getSqlSession().selectList(statementName, pageRequest);
		return list;
	}
	
	@Transactional(propagation=Propagation.NOT_SUPPORTED,readOnly=true)
	public List<E> find(PageRequest query) {
		return listQuery(getFindStatement(), query);
	}
	
	
	protected List<E> listQuery(String statementName, PageRequest pageRequest, int limit) {
		List<E> list = getSqlSession().selectList(statementName, pageRequest, new RowBounds(0, limit));
		return list;
	}
	@Transactional(propagation=Propagation.NOT_SUPPORTED,readOnly=true)
	public List<E> find(PageRequest query, int limit) {
		return listQuery(getFindStatement(), query, limit);
	}
	
	public String getFindPageStatement() {
		return getIbatisSqlMapNamespace() + ".findPage";
	}
	
	@SuppressWarnings("unchecked")
	protected Page pageQuery(String statementName, PageRequest pageRequest) {
		return pageQuery(getSqlSession(), statementName, getCountStatementForPaging(statementName), pageRequest);
	}
	
	@SuppressWarnings("unchecked")
	public static Page pageQuery(SqlSession sqlSession, String statementName, String countStatementName, PageRequest pageRequest) {

		Number totalCount = (Number) sqlSession.selectOne(countStatementName, pageRequest);
		if (totalCount == null || totalCount.longValue() <= 0) {
			return new Page(pageRequest, 0);
		}

		Page page = new Page(pageRequest, totalCount.intValue());

		RowBounds rowBounds = new RowBounds(page.getFirstResult(), page.getPageSize());
		List list = sqlSession.selectList(statementName, pageRequest, rowBounds);
		page.setResult(list);

		return page;
	}
	
	@Transactional(propagation=Propagation.NOT_SUPPORTED,readOnly=true)
	public Page<E> findPage(PageRequest query) {
		return pageQuery(getFindPageStatement(), query);
	}

}
