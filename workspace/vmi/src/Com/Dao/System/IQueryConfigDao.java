package Com.Dao.System;

import Com.Base.Dao.IBaseDao;
import Com.Base.Util.DJException;
import Com.Entity.System.Queryconfig;

public interface IQueryConfigDao extends IBaseDao {
	 public void ExecSave(Queryconfig info) throws DJException;
}
