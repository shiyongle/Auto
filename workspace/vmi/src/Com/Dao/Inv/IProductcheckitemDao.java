package Com.Dao.Inv;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import Com.Base.Dao.IBaseDao;
import Com.Entity.Inv.Productcheckitem;

public interface IProductcheckitemDao extends IBaseDao {
	public HashMap<String,Object> ExecSave(Productcheckitem po);
	public Productcheckitem Query(String fid);
	void ExecSaveProductcheckitem(HttpServletRequest request);
		
}