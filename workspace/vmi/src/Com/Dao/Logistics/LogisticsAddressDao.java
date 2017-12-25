package Com.Dao.Logistics;

import java.util.Date;
import java.util.HashMap;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;
import Com.Base.Dao.BaseDao;
import Com.Base.Util.DJException;
import Com.Entity.Logistics.LogisticsAddress;
import Com.Entity.System.Address;
import Com.Entity.System.CustRelationAdress;
import Com.Entity.System.Useronline;

@Service("logisticsAddressDao")
public class LogisticsAddressDao extends BaseDao implements ILogisticsAddressDao {

	@Override
	public HashMap<String, Object> ExecSave(LogisticsAddress addr) {
		// TODO Auto-generated method stub
		HashMap<String, Object> params = new HashMap<>();
		if (addr.getFid().isEmpty()) {
			addr.setFid(this.CreateUUid());
		}
		this.saveOrUpdate(addr);

		params.put("success", true);
		return params;
	}
	@Override
	public LogisticsAddress Query(String fid) {
		// TODO Auto-generated method stub
		return this.getHibernateTemplate().get(LogisticsAddress.class, fid);
	}

	

}
