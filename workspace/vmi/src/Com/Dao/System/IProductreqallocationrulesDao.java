package Com.Dao.System;

import java.util.HashMap;
import java.util.List;

import Com.Base.Dao.IBaseDao;
import Com.Entity.System.Productreqallocationrules;

public interface IProductreqallocationrulesDao extends IBaseDao {
	public HashMap<String, Object> ExecSave(
			Productreqallocationrules productreqallocationrules);

	public Productreqallocationrules Query(String fid);
	
	public void ExecAllot(String fcustomerid,String userid);
	
	void ExecAllot(String fcustomerid,String userid, String fid);
	
	public void ExecTraitAllot(HashMap<String,Object> list,String userid,HashMap<String,Object> address,HashMap<String,Object> slist);
	
	public void ExecTraitunAllot(List<HashMap<String,Object>> list,int famount,String ftraitid,String ffirstproductid);
	
	public void ExecCreateMessageInfo(String sql);
}
