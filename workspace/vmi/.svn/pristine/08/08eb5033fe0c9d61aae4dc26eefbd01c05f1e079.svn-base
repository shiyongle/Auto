package Com.Dao.order;

import java.util.HashMap;
import org.springframework.stereotype.Service;

import Com.Base.Dao.BaseDao;
import Com.Entity.order.Deliverratio;
@Service("deliverratioDao")
public class DeliverratioDao extends BaseDao implements IDeliverratioDao {

	@Override
	public HashMap<String, Object> ExecSave(Deliverratio dlv) {
		// TODO Auto-generated method stub
		HashMap<String, Object> params = new HashMap<>();
		if (dlv.getFid().isEmpty()) {
			dlv.setFid(this.CreateUUid());
		}
			this.saveOrUpdate(dlv);
		
		params.put("success", true);
		return params;
	}

	@Override
	public Deliverratio Query(String fid) {
		// TODO Auto-generated method stub
		return this.getHibernateTemplate().get(
				Deliverratio.class, fid);
	}

}
