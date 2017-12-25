package Com.Dao.quickOrder;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import Com.Base.Dao.IBaseDao;
import Com.Entity.System.Custproduct;
import Com.Entity.System.Productdef;
import Com.Entity.order.CusPrivateDelivers;
import Com.Entity.order.Cusdelivers;
import Com.Entity.order.Deliverapply;
import Com.Entity.order.Mystock;

public interface IQuickOrderDao extends IBaseDao {

	void ExecCustproductCommon(String fidcls,String fstate);
	public String ExecDelFileQuick(String id);
	public String getCurrentCustomerids(String userid);
	public void ExecUploadQuickCustProductImg(HttpServletRequest request) throws Exception;
	public void ExecSaveQuickDeliverapply(Deliverapply dinfo) throws Exception;
	public void ExecSaveMystockOrDeliverapply(Deliverapply dinfo,Mystock sinfo) throws Exception;
	public void ExecSaveDeliverapplyOrCusdelivers(List<Deliverapply> dlist,List<Mystock> list2, List<Cusdelivers> clist, String userid)throws Exception;
	public void ExecSaveQuickCustproduct(Custproduct c,Productdef f)throws Exception;
	public String getCurrentSupplierfids(String userid);
	public String getCurrentSupplierid(String userid);
	public void ExecSaveCusPrivateDeliversForHolder(List<CusPrivateDelivers> dlist);
	
}
