package Com.Dao.Finance;

import java.util.HashMap;

import org.springframework.stereotype.Service;

import Com.Base.Dao.BaseDao;
import Com.Entity.Finance.Balancepriceprices;
@Service("balancepriceprices")
public class BalancepricepricesDao extends BaseDao implements IBalancepricepricesDao {


	@Override
	public HashMap<String, Object> ExecSave(Balancepriceprices cust) {
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
	public Balancepriceprices Query(String fid) {
		// TODO Auto-generated method stub
		return this.getHibernateTemplate().get(
				Balancepriceprices.class, fid);
	}



}
