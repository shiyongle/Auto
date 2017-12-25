package Com.Dao.Finance;

import java.util.HashMap;
import org.springframework.stereotype.Service;

import Com.Base.Dao.BaseDao;
import Com.Entity.Finance.Gathering;

@Service("GatheringDao")
public class GatheringDao extends BaseDao implements IGatheringDao {

	@Override
	public HashMap<String, Object> ExecSave(Gathering ga) {
		// TODO Auto-generated method stub
		HashMap<String, Object> params = new HashMap<>();
		if (ga.getFid().isEmpty()) {
			ga.setFid(this.CreateUUid());
		}
			this.saveOrUpdate(ga);
		
		params.put("success", true);
		return params;
	}

	@Override
	public Gathering Query(String fid) {
		// TODO Auto-generated method stub
		return this.getHibernateTemplate().get(
				Gathering.class, fid);
	}

}
