package Com.Dao.Inv;

import java.util.HashMap;
import org.springframework.stereotype.Service;

import Com.Base.Dao.BaseDao;
import Com.Entity.Inv.Usedstorebalance;
@Service("usedstorebalanceDao")
public class UsedstorebalanceDao extends BaseDao implements IUsedstorebalanceDao {

	
	@Override
	public HashMap<String, Object> ExecSave(Usedstorebalance cyc) {
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
	public Usedstorebalance Query(String fid) {
		// TODO Auto-generated method stub
		return this.getHibernateTemplate().get(
				Usedstorebalance.class, fid);
	}
}
