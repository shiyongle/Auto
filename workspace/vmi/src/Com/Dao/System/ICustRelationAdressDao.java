package Com.Dao.System;

import Com.Base.Dao.IBaseDao;

public interface ICustRelationAdressDao extends IBaseDao {
	
	public void ExecAddCustRelationAdress(String[] fid,String id);
	public void ExecDelCustRelationAdress(String[] fid,String id);
}
