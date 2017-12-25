package Com.Dao.order;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import Com.Base.Dao.IBaseDao;
import Com.Entity.System.Custproduct;
import Com.Entity.order.FTUSaledeliver;
import Com.Entity.order.FTUSaledeliverEntry;


public interface IFTUDao extends IBaseDao {

	List<HashMap<String, Object>> getFtuRelationName(
			HttpServletRequest request, int i) throws IOException;

	void saveFTUSaleDeliver(FTUSaledeliver saledeliver,
			List<Custproduct> productList);

	void saveFTUprint(String fid);

	void saveNewFTUSaleDeliver(FTUSaledeliver saledeliver,
			List<FTUSaledeliverEntry> FTUSaledeliverEntry,String userid);


}
