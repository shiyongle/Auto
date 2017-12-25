package Com.Dao.Inv;

import java.util.HashMap;

import org.springframework.stereotype.Service;

import Com.Base.Dao.BaseDao;
import Com.Entity.Inv.Warehouse;
@Service("WarehouseDao")
public class WarehouseDao extends BaseDao implements IWarehouseDao {

	@Override
	public HashMap<String, Object> ExecSave(Warehouse cyc) {
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
	public Warehouse Query(String fid) {
		// TODO Auto-generated method stub
		return this.getHibernateTemplate().get(
				Warehouse.class, fid);
	}

}
