package Com.Dao.Finance;

import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Service;

import Com.Base.Dao.BaseDao;
import Com.Entity.Finance.Custbalanceprice;
import Com.Entity.Finance.Custbalancepricecustprices;
import Com.Entity.Finance.Custbalancepriceprices;
@Service("CustbalancepriceDao")
public class CustBalancepriceDao extends BaseDao implements ICustBalancepriceDao {

	@Override
	public HashMap<String, Object> ExecSave(Custbalanceprice cust) {
		// TODO Auto-generated method stub
		HashMap<String, Object> params = new HashMap<>();
		if (cust.getFid().isEmpty()) {
			cust.setFid(this.CreateUUid());
		}
			this.saveOrUpdate(cust);
		
		params.put("success", true);
		return params;
	}

	@Override
	public Custbalanceprice Query(String fid) {
		// TODO Auto-generated method stub
		return this.getHibernateTemplate().get(
				Custbalanceprice.class, fid);
	}

	@Override
	public void ExecSaveBalanceprice(Custbalanceprice cust,
			List<Custbalancepriceprices> prices,
			List<Custbalancepricecustprices> custprices) {
		HashMap<String, Object> params = new HashMap<>();
		if (cust.getFid().isEmpty()||"null".equals(cust.getFid())) {
			cust.setFid(this.CreateUUid());
		}else{
		String hql="delete FROM Balancepriceprices where fparentid='" + cust.getFid()+"'";
		this.ExecByHql(hql);
		hql="delete FROM Balancepricecustprices where fparentid='" + cust.getFid()+"'";
		this.ExecByHql(hql);
		}
		
		this.saveOrUpdate(cust);
	
		for(int i=0;i<prices.size();i++)
		{
			Custbalancepriceprices pinfo=prices.get(i);
			if (pinfo.getFid().isEmpty()) {
				pinfo.setFid(this.CreateUUid());
			}
			if(pinfo.getFparentid().isEmpty()){
				pinfo.setFparentid(cust.getFid());
			}
			this.saveOrUpdate(pinfo);
		}
	
		for(int j=0;j<custprices.size();j++)
		{
			Custbalancepricecustprices cinfo=custprices.get(j);
			if (cinfo.getFid().isEmpty()) {
				cinfo.setFid(this.CreateUUid());
			}
			if(cinfo.getFparentid().isEmpty()){
				cinfo.setFparentid(cust.getFid());
			}
			this.saveOrUpdate(cinfo);
		}
	
		
		// TODO Auto-generated method stub
	
	}

}
