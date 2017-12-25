package Com.Dao.order;

import Com.Base.Dao.IBaseDao;
import Com.Entity.order.Appraise;

public interface IAppraiseDao extends IBaseDao{
	public void saveAppraiseInfo(Appraise appraise);
}
