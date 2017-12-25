package Com.Dao.System;

import Com.Base.Dao.IBaseDao;
import Com.Entity.System.MaterialLimit;
import Com.Entity.System.ProducePlan;

public interface IProducePlanDao extends IBaseDao{

	void saveProducePlan(ProducePlan producePlan, String userid);

	void DelProducePlan(String fid);

	void saveMaterialLimit(MaterialLimit materialLimit);

}
