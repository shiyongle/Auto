package Com.Dao.Inv;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import Com.Base.Dao.IBaseDao;
import Com.Base.Util.DJException;
import Com.Entity.Inv.Productindetail;

public interface IproductindetailDao extends IBaseDao {
	  public HashMap<String,Object> ExecSave(Productindetail cyc);
	  public Productindetail Query(String fid);
	  public void saveproductindetail(HttpServletRequest request) throws DJException;
	  public void saveproductindetail(String fproductplanid,int amt,String FWarehouseID,String FWarehouseSiteID,String userid,String fproductid,String forderid,String forderEntryid,String fsupplierid,String ftraitid,boolean audited);
}
