package Com.Dao.System;

import java.util.Date;
import java.util.HashMap;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;
import Com.Base.Dao.BaseDao;
import Com.Base.Util.DJException;
import Com.Entity.System.Address;
import Com.Entity.System.CustRelationAdress;
import Com.Entity.System.Useronline;

@Service("AddressDao")
public class AddressDao extends BaseDao implements IAddressDao {

	@Override
	public HashMap<String, Object> ExecSave(Address addr) {
		// TODO Auto-generated method stub
		HashMap<String, Object> params = new HashMap<>();
		if (addr.getFid().isEmpty()) {
			addr.setFid(this.CreateUUid());
			// this.saveOrUpdate(addr);
		}
		// else{
		// this.Update(addr);
		// }
		this.saveOrUpdate(addr);

		params.put("success", true);
		return params;
	}

	@Override
	public Address Query(String fid) {
		// TODO Auto-generated method stub
		return this.getHibernateTemplate().get(Address.class, fid);
	}

	@Override
	public void ExecImpAddressSDK(HttpServletRequest request) {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
		Address info = (Address) request.getAttribute("Address");
		String userid = ((Useronline) request.getSession().getAttribute(
				"Useronline")).getFuserid();
		if (info == null || info.getFid() == null || info.getFid().equals("")) {
			throw new DJException("地址ID为空");
		}
		if(info.getFcustomerid()==null || info.getFcustomerid().equals(""))
		{
			throw new DJException("客户ID为空");
		}
		String sql = " select fid from t_bd_address where fid ='"
				+ info.getFid() + "' ";
		if (!QueryExistsBySql(sql)) {
			info.setFcreatetime(new Date());
			info.setFcreatorid(userid);
			info.setFlastupdatetime(new Date());
			info.setFlastupdateuserid(userid);
			saveOrUpdate(info);
		}
		if(!QueryExistsBySql("select fid from t_bd_custrelationadress where faddressid='"+info.getFid()+"' and fcustomerid='"+info.getFcustomerid()+"'"))
		{
			CustRelationAdress cusadressinfo=new CustRelationAdress();
			cusadressinfo.setFid(CreateUUid());
			cusadressinfo.setFcustomerid(info.getFcustomerid());
			cusadressinfo.setFaddressid(info.getFid());
			saveOrUpdate(cusadressinfo);
		}
	}

}
