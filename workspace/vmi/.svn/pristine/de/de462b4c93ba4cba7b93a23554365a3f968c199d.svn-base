package Com.Dao.order;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import Com.Base.Dao.IBaseDao;
import Com.Entity.Inv.Storebalance;
import Com.Entity.order.Delivers;
import Com.Entity.order.Saleorder;

public interface IDeliversDao extends IBaseDao {
	  	public HashMap<String,Object> ExecSave(Delivers vmi);
		public Delivers Query(String fid);
		public void ExecGenerateSQL(ArrayList<String> generateSQL, String cusdeliversID) throws Exception ;
		public void ExecAllot( List<HashMap<String,Object>> deliverslist) throws Exception ;
		public void ExecAssginAllot(Delivers delivers,List<Storebalance> storebalances) throws Exception ;
		public void ExecUnAllot( String fidcls ) throws Exception ;
		public HashMap<String,Object> ExecVMIorder( ArrayList<Saleorder> solist , String ordersql, String deliverid ) throws Exception ;
}
