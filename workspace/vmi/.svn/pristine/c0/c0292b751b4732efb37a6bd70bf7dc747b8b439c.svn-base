package Com.Dao.System;

import java.util.HashMap;

import org.springframework.stereotype.Service;

import Com.Base.Dao.BaseDao;
import Com.Entity.System.Productcycle;
@Service("productCycleDao")
public class ProductCycleDao extends BaseDao implements IProductCycleDao {

	@Override
	public HashMap<String, Object> ExecSave(Productcycle cyc) {
		// TODO Auto-generated method stub
		HashMap<String, Object> params = new HashMap<>();
		if (cyc.getFid().isEmpty()) {
			cyc.setFid(this.CreateUUid());
		}
			this.saveOrUpdate(cyc);
		
		params.put("success", true);
		return params;
	}

	@Override
	public Productcycle Query(String fid) {
		// TODO Auto-generated method stub
		return this.getHibernateTemplate().get(
				Productcycle.class, fid);
	}

}
