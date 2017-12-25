package Com.Dao.order;

import java.util.HashMap;

import org.springframework.stereotype.Service;

import Com.Base.Dao.BaseDao;
import Com.Entity.order.Conventionalplan;;
@Service("ConventionalPlanDao")
public class ConventionalPlanDao extends BaseDao implements IConventionalPlanDao {

	@Override
	public HashMap<String, Object> ExecSave(Conventionalplan dlv) {
		// TODO Auto-generated method stub
		HashMap<String, Object> params = new HashMap<>();
		if (dlv.getFid().isEmpty()) {
			dlv.setFid(this.CreateUUid());
//			this.saveOrUpdate(addr);
		}
//		else{
//			this.Update(addr);
//		}
			this.saveOrUpdate(dlv);
		
		params.put("success", true);
		return params;
	}

	@Override
	public Conventionalplan Query(String fid) {
		// TODO Auto-generated method stub
		return this.getHibernateTemplate().get(
				Conventionalplan.class, fid);
	}

}
