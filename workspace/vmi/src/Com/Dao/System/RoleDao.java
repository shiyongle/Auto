package Com.Dao.System;

import java.util.HashMap;
import org.springframework.stereotype.Service;

import Com.Base.Dao.BaseDao;
import Com.Entity.System.Role;

@Service("RoleDao")
public class RoleDao extends BaseDao implements IRoleDao{
	
	@Override
	public HashMap<String, Object> ExecSave(Role info) {
		HashMap<String, Object> params = new HashMap<>();
		if (info.getFid().isEmpty()) {
			info.setFid(this.CreateUUid());
			this.saveOrUpdate(info);
		}
		params.put("success", true);
		return params;
	}

}
