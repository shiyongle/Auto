package Com.Dao.quickOrder;

import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Service;

import Com.Base.Dao.BaseDao;
import Com.Entity.order.CusPrivateDelivers;
@Service("cusPrivateDeliversDao")
public class CusPrivateDeliversDao extends BaseDao implements ICusPrivateDeliversDao {

	@Override
	public HashMap<String, Object> ExecSave(CusPrivateDelivers dlv) {
		HashMap<String, Object> params = new HashMap<>();
		if (dlv.getFid().isEmpty()) {
			dlv.setFid(this.CreateUUid());
		}
			this.saveOrUpdate(dlv);
		params.put("success", true);
		return params;
	}

	@Override
	public CusPrivateDelivers Query(String fid) {
		// TODO Auto-generated method stub
		return this.getHibernateTemplate().get(
				CusPrivateDelivers.class, fid);
	}

	@Override
	public void ExecSave(List<CusPrivateDelivers> vmi) {
		// TODO Auto-generated method stub
		
	}

}
