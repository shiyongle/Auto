package Com.Dao.System;

import java.util.HashMap;

import Com.Base.Dao.IBaseDao;
import Com.Entity.System.Certificate;

public interface ICertificateDao extends IBaseDao {
	  public HashMap<String,Object> ExecSave(Certificate add);
		public Certificate Query(String fid);
}
