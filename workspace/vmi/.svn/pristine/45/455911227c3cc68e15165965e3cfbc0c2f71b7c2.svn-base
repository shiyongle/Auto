package Com.Dao.Finance;

import java.util.HashMap;

import org.springframework.stereotype.Service;

import Com.Base.Dao.BaseDao;
import Com.Entity.Finance.Purchaseourprice;
@Service("purchaseourpriceDao")
public class PurchaseourpriceDao extends BaseDao implements IPurchaseourpriceDao {


	@Override
	public HashMap<String, Object> ExecSave(Purchaseourprice cust) {
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
	public Purchaseourprice Query(String fid) {
		// TODO Auto-generated method stub
		return this.getHibernateTemplate().get(
				Purchaseourprice.class, fid);
	}



}
