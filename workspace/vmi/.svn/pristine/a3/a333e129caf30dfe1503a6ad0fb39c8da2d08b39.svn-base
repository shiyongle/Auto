package Com.Dao.Finance;

import java.util.HashMap;

import org.springframework.stereotype.Service;

import Com.Base.Dao.BaseDao;
import Com.Entity.Finance.Balancepricecustprices;
@Service("balancepricecustprices")
public class BalancepricecustpricesDao extends BaseDao implements IBalancepricecustpricesDao {

	@Override
	public HashMap<String, Object> ExecSave(Balancepricecustprices cust) {
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
	public Balancepricecustprices Query(String fid) {
		// TODO Auto-generated method stub
		return this.getHibernateTemplate().get(
				Balancepricecustprices.class, fid);
	}

	

}
