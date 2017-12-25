package Com.Dao.Inv;

import java.util.HashMap;

import org.springframework.stereotype.Service;

import Com.Base.Dao.BaseDao;
import Com.Entity.Inv.Warehousesites;
@Service("WarehouseSitesDao")
public class WarehouseSitesDao extends BaseDao implements IWarehouseSitesDao {

	@Override
	public HashMap<String, Object> ExecSave(Warehousesites cyc) {
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
	public Warehousesites Query(String fid) {
		// TODO Auto-generated method stub
		return this.getHibernateTemplate().get(
				Warehousesites.class, fid);
	}

}
