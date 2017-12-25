package Com.Dao.System;

import java.util.HashMap;

import org.springframework.stereotype.Service;

import Com.Base.Dao.BaseDao;
import Com.Entity.System.Productrelation;
@Service("productRelationDao")
public class ProductRelationDao extends BaseDao implements IProductRelationDao {

	@Override
	public HashMap<String, Object> ExecSave(Productrelation cust) {
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
	public Productrelation Query(String fid) {
		// TODO Auto-generated method stub
		return this.getHibernateTemplate().get(
				Productrelation.class, fid);
	}

}
