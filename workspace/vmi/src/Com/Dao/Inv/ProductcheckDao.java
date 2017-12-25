
package Com.Dao.Inv;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import Com.Base.Dao.BaseDao;
import Com.Base.Util.DJException;
import Com.Entity.Inv.Productcheck;
import Com.Entity.Inv.Productcheckitem;
import Com.Entity.Inv.Storebalance;

@Service
public class ProductcheckDao extends BaseDao implements IProductcheckDao {


	@Override 
	public HashMap<String, Object> ExecSave( Productcheck po) {
		// TODO Auto-generated method stub
		HashMap<String, Object> params = new HashMap<>();
		if (po.getFid().isEmpty()) {
			po.setFid(this.CreateUUid());
		}
		this.saveOrUpdate(po);

		params.put("success", true);
		return params;
	}

	@Override
	public  Productcheck Query(String fid) {
		// TODO Auto-generated method stub
		return this.getHibernateTemplate().get(
				 Productcheck.class, fid);
	}
	
	@Override
	public void ExecCalibrationTheInventory(HttpServletRequest req) {
		// TODO Auto-generated method stub
		
		String fids = req.getParameter("fids");
		
		String hql = "from Productcheckitem p where p.fproductcheckid in %s ";
		
		hql = String.format(hql, fids);
		
		List<Productcheckitem> productcheckitems = QueryByHql(hql);
		
		int iterateCount = 0;
		
		for (Productcheckitem productcheckitem : productcheckitems) {
			
			
				String hql2 = " from Storebalance where fproductId = '%s' ";
				
				hql2 = String.format(hql2, productcheckitem.getFproductid());
				
				List<Storebalance> storebalances = QueryByHql(hql2);
				
				Storebalance storebalanceT = storebalances.get(0);
				
				storebalanceT.setFbalanceqty(productcheckitem.getFqty());
				
				saveOrUpdate(storebalanceT);
				
				iterateCount++;
				
		}
		
		if (iterateCount != productcheckitems.size()) {
			
			throw new DJException("没有全部完成");
			
		} 
		
		String productDemandids  = fids.substring(1, fids.length()-1).replace("'","");
		String[] productDemandid = productDemandids.split(",");
		for(String fid : productDemandid){
			//反写
			Productcheck productcheck = Query(fid);
			
			productcheck.setFstate((byte) 1);
			
			ExecSave(productcheck);
		}
	}
	
}
