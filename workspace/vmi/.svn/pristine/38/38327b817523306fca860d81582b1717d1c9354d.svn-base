package Com.Dao.order;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import Com.Base.Dao.IBaseDao;
import Com.Entity.order.Firstproductdemand;

public interface IPreProductDemandDao extends IBaseDao {

	void saveStructureFile(HttpServletRequest request, String fid);

	void savePreProductDemandPlan(HttpServletRequest request);

	void DelPreProductDemandPlan(String fid);

	void DelPreProductDemandStructure(String fid);

	void saveProductDemand(HttpServletRequest request) throws IOException;

	Firstproductdemand handleProductDemand(HttpServletRequest request)
			throws IOException;

	void saveReleaseProductDemand(HttpServletRequest request) throws IOException;


}
