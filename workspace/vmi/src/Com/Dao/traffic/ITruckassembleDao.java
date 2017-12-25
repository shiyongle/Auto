package Com.Dao.traffic;

import java.util.HashMap;

import Com.Base.Dao.IBaseDao;
import Com.Entity.traffic.Truckassemble;

public interface ITruckassembleDao extends IBaseDao {
	  public HashMap<String,Object> ExecSave(Truckassemble Tassemble);
	  public Truckassemble Query(String fid);
	  public HashMap<String, Object> ExecTruckassemble(HashMap<String, Object> params,int type) throws Exception ;
}
