package Com.Dao.order;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import Com.Base.Dao.IBaseDao;
import Com.Entity.order.Productstructure;

public  interface IProductstructureDao extends IBaseDao{
	public void SaveOrUpdateProductstructure(List<Productstructure> Productdef,String fid);
	public void ExecaffirmSchemeDesign(HttpServletRequest request,String fids,String userid,List<HashMap<String,Object>> productlist,List<HashMap<String,Object>> entrylist,List<HashMap<String,Object>> filelist);
	public void Delproduct(List<HashMap<String,Object>> productlist,String fids);
}

