package Com.Dao.Finance;

import java.util.HashMap;

import org.springframework.stereotype.Service;

import Com.Base.Dao.BaseDao;
import Com.Entity.Finance.Purchasesupplierprice;

@Service("purchasesupplierpriceDao")
public class PurchasesupplierpriceDao extends BaseDao implements
		IPurchasesupplierpriceDao {

	@Override
	public HashMap<String, Object> ExecSave(Purchasesupplierprice cust) {
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
	public Purchasesupplierprice Query(String fid) {
		// TODO Auto-generated method stub
		return this.getHibernateTemplate().get(
				Purchasesupplierprice.class, fid);
	}

}