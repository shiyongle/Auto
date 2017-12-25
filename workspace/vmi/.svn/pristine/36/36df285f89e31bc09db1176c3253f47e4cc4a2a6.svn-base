package Com.Dao.Finance;

import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Service;

import Com.Base.Dao.BaseDao;
import Com.Entity.Finance.Balanceprice;
import Com.Entity.Finance.Balancepricecustprices;
import Com.Entity.Finance.Balancepriceprices;
@Service("balancepriceDao")
public class BalancepriceDao extends BaseDao implements IBalancepriceDao {

	@Override
	public HashMap<String, Object> ExecSave(Balanceprice cust) {
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
	public Balanceprice Query(String fid) {
		// TODO Auto-generated method stub
		return this.getHibernateTemplate().get(
				Balanceprice.class, fid);
	}

	@Override
	public void ExecSaveBalanceprice(Balanceprice cust,
			List<Balancepriceprices> prices,
			List<Balancepricecustprices> custprices) {
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
			Balancepriceprices pinfo=prices.get(i);
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
			Balancepricecustprices cinfo=custprices.get(j);
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
