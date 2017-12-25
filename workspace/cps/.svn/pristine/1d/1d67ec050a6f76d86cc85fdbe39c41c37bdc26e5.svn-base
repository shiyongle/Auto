package com.service.statement;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dao.IBaseDao;
import com.dao.statement.StatementDao;
import com.model.statement.Statement;
import com.service.IBaseManagerImpl;

@SuppressWarnings("unchecked")
@Service("statementManager")
@Transactional
public  class StatementManagerImpl extends IBaseManagerImpl<Statement,java.lang.Integer> implements StatementManager  {

	@Autowired
	private StatementDao statementDao;
	
	@Override
	protected IBaseDao<Statement, Integer> getEntityDao() {
		return this.getEntityDao();
	}
	
	public List<Statement> getStatementsByCust(String custId){
		return statementDao.getStatementsByCust(custId);
	}
}
