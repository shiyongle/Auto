package Com.Dao.order;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.tomcat.util.http.fileupload.FileItem;

import Com.Base.Dao.IBaseDao;
import Com.Entity.System.Productdef;
import Com.Entity.order.Productstructure;
import Com.Entity.order.SchemeDesignEntry;
import Com.Entity.order.Schemedesign;

public interface ISchemeDesignDao extends IBaseDao{
	public void saveschemedesignFile( List<FileItem> list,HashMap<String, String> map);

	public void saveSchemeDesign(Schemedesign f,
			List<SchemeDesignEntry> entryList,List<Productstructure> productList);

	public void ExecSDOrder(HttpServletRequest request, Schemedesign sdinfo, ArrayList<SchemeDesignEntry> sdentry, Productdef productdefinfo);

	public void DelSchemeDesign(String fid);

	public void ExecCancelSchemeDesignOrder(String fid);
	
	public void ExecUnReceiveProductdemand(String fidcls,int isExit);
	public void ExecUnaffirm(String fid,List<HashMap<String,Object>> productlist,List<HashMap<String,Object>> entrylist);

	String ExecGenerateDelivery(HttpServletRequest request);
	
	String ExecUndoGenerateDelivery(HttpServletRequest request);
	
	public void ExecreceiveProductdemand(String userid,String ficls,String fsupplierid);

	String productToCustproduct(String productid);
	
}
