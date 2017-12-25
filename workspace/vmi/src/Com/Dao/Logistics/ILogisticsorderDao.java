package Com.Dao.Logistics;

import javax.servlet.http.HttpServletRequest;

import Com.Base.Dao.IBaseDao;

public interface ILogisticsorderDao extends IBaseDao{

	void saveOrUpdateLogisticsOrder(HttpServletRequest request);

}
