package Com.Dao.order;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import Com.Base.Dao.IBaseDao;
import Com.Entity.order.ProductPlan;

public interface IProductPlanDao extends IBaseDao {
	  public HashMap<String,Object> ExecSave(ProductPlan cust);
	  public ProductPlan Query(String fid);
	  public HashMap<String, Object> ExecProductPlanDao(PreparedStatement stmt, Connection conn,HashMap<String, Object> params,int type) throws Exception ;
	  public String ExecToDeliversBoard(HttpServletRequest request);
}
