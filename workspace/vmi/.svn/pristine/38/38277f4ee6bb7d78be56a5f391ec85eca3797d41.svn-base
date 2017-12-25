package Com.Dao.order;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.http.fileupload.FileItem;

import Com.Base.Dao.IBaseDao;
import Com.Entity.Inv.Storebalance;
import Com.Entity.order.Saleorder;

public interface ISaleOrderDao extends IBaseDao {
	  public HashMap<String,Object> ExecSave(Saleorder cust);
	  public Saleorder Query(String fid);
	  public void ExecAssageSupplier(HttpServletRequest request) throws Exception;
	  public void ExecDeleteSaleOrders(HashMap<String, Object> params) throws Exception;
	  public void ExecDeleteSaleOrdersNew(String orderidcls) throws Exception;
	  public void ExecUpdateImportStateSDK(String fid);
	void ExecCardboardSupplierAffirm(String fidcls, String userid);
	public void ExecCardboardSupplierUnAffirm(String fidcls);
//	public void createProductPlanBySingel(List<Saleorder> slist,String fdeliverid);
	public HashMap<String, Object> ExecProductPlanAndStorebalanceBySingel(List<Saleorder> slist);
	public void ExecAffirmAndImportEAS(String fidcls,String userid);
	public void ExecUnAffirmImport(String fidcls);
	BigDecimal[] getPurchasePrices(Integer famount, String fproductdefid,
			String supplierid);
	
}
