package Com.Dao.System;

import java.util.HashMap;

import org.springframework.stereotype.Repository;
import Com.Base.Dao.BaseDao;
import Com.Entity.System.CusWinCfg;

@Repository("cusWinCfgDao")
public class CusWinCfgDao extends BaseDao implements ICusWinCfgDao {

	@Override
	public HashMap<String, Object> ExecSave(CusWinCfg cust) {
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
	public CusWinCfg Query(String fid) {
		// TODO Auto-generated method stub
		return this.getHibernateTemplate()
				.get(CusWinCfg.class, fid);
	}

}
