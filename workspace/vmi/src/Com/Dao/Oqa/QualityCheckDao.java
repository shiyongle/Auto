package Com.Dao.Oqa;

import java.util.HashMap;

import org.springframework.stereotype.Service;

import Com.Base.Dao.BaseDao;
import Com.Entity.Oqa.QualityCheck;
@Service("qualityCheckDao")
public class QualityCheckDao extends BaseDao implements IQualityCheckDao {

	@Override
	public HashMap<String, Object> ExecSave(QualityCheck cyc) {
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
	public QualityCheck Query(String fid) {
		// TODO Auto-generated method stub
		return this.getHibernateTemplate().get(
				QualityCheck.class, fid);
	}

}
