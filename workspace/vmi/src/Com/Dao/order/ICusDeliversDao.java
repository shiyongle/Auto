package Com.Dao.order;

import javax.servlet.http.HttpServletRequest;

import Com.Base.Dao.IBaseDao;
import Com.Entity.order.Cusdelivers;

public interface ICusDeliversDao extends IBaseDao {
	    public void ExecExportZTcusdelivers() throws Exception ;
		public void ExecImportZTcusdelivers() throws Exception ;
		public Cusdelivers Query(String fid);
		public void ExecImpCusDeliversSDK(HttpServletRequest request);
		public void ExecImportcusdelivers() throws Exception ;
}
