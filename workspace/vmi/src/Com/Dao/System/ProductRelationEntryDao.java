package Com.Dao.System;

import java.util.HashMap;

import org.springframework.stereotype.Service;

import Com.Base.Dao.BaseDao;
import Com.Entity.System.Productrelationentry;
@Service("productRelationEntryDao")
public class ProductRelationEntryDao extends BaseDao implements IProductRelationEntryDao {

	@Override
	public HashMap<String, Object> ExecSave(Productrelationentry cust) {
		// TODO Auto-generated method stub
		HashMap<String, Object> params = new HashMap<>();
		if (cust.getFid().isEmpty()) {
			cust.setFid(this.CreateUUid());
		}
			this.saveOrUpdate(cust);
		
		params.put("success", true);
		return params;
	}

	@Override
	public Productrelationentry Query(String fid) {
		// TODO Auto-generated method stub
		return this.getHibernateTemplate().get(
				Productrelationentry.class, fid);
	}

	@Override
	public void ExecdeteleString(String entrysql,String sql) {
		// TODO Auto-generated method stub
//		Query q = this.getSessionFactory().getCurrentSession().createSQLQuery(entrysql);
//		q.executeUpdate();
		this.ExecBySql(entrysql);
		this.ExecBySql(sql);
		
	}

}
