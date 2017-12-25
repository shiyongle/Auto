
package Com.Dao.Inv;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.stereotype.Service;

import Com.Base.Dao.BaseDao;
import Com.Entity.Inv.Productcheckitem;

@Service
public class ProductcheckitemDao extends BaseDao implements IProductcheckitemDao {


	@Override 
	public HashMap<String, Object> ExecSave( Productcheckitem po) {
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
	public  Productcheckitem Query(String fid) {
		// TODO Auto-generated method stub
		return this.getHibernateTemplate().get(
				 Productcheckitem.class, fid);
	}
	
	@Override
	public void ExecSaveProductcheckitem(HttpServletRequest request){
		
		String objsS = request.getParameter("fproductcheck"); 
		
		JSONArray objs = JSONArray.fromObject(objsS);
		
		int size = objs.size();
		
		for (int i = 0; i < size; i++) {
			
			Productcheckitem productcheckitem = new Productcheckitem("");
			
			JSONObject jsonO = objs.getJSONObject(i);
			
			productcheckitem.setFproductcheckid(request.getParameter("fid"));
			productcheckitem.setFid(jsonO.getString("fid"));
			productcheckitem.setFproductid(jsonO.getString("fproductdefid"));
			
			productcheckitem.setFqty(jsonO.getInt("actualquotation"));
			productcheckitem.setFremark(jsonO.getString("fremark"));
			
			ExecSave(productcheckitem);
		}
		
		
	}
}
