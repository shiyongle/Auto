package Com.Dao.System;

import java.util.HashMap;

import org.springframework.stereotype.Service;

import Com.Base.Dao.BaseDao;
import Com.Entity.System.Certificate;
@Service("CertificateDao")
public class CertificateDao extends BaseDao implements ICertificateDao {

	@Override
	public HashMap<String, Object> ExecSave(Certificate addr) {
		// TODO Auto-generated method stub
		HashMap<String, Object> params = new HashMap<>();
		if (addr.getFid().isEmpty()) {
			addr.setFid(this.CreateUUid());
//			this.saveOrUpdate(addr);
		}
//		else{
//			this.Update(addr);
//		}
			this.saveOrUpdate(addr);
		
		params.put("success", true);
		return params;
	}

	@Override
	public Certificate Query(String fid) {
		// TODO Auto-generated method stub
		return this.getHibernateTemplate().get(
				Certificate.class, fid);
	}

}
