package com.dao.statement;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.dao.IBaseDaoImpl;
import com.model.statement.Statement;

@SuppressWarnings("unchecked")
@Repository("statementDao")
public class StatementDaoImpl extends IBaseDaoImpl<Statement,java.lang.Integer> implements StatementDao {

	public List<Statement> getStatementsByCust(String custId) {
		String hql = "from Statement where fcustomerid = ? order by fmonth desc"; 
		Query query = this.getSessionFactory().getCurrentSession().createQuery(hql);  
	    query.setString(0, custId);
	    List<Statement> list = query.list();
	    return list;
	}

}
