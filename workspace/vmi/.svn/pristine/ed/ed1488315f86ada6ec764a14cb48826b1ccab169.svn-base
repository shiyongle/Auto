package Com.Dao.order;

import java.util.List;

import org.apache.tomcat.util.http.fileupload.FileItem;

import Com.Base.Dao.IBaseDao;
import Com.Entity.order.Firstproductdemand;
import Com.Entity.order.Productstructure;

public interface IFirstproductdemandDao extends IBaseDao{

	public void saveProductFile(String fparentid, List<FileItem> list,
			String fpath);
	public void ExecAuditFproductdemand(Firstproductdemand finfo,String userid);
	public  boolean closeProductDemand(String fid,IBaseDao b);
	void updateUserConcat(String userid, String flinkphone);
	public void ExecNewproductdemand();	
	public void ExecRcvproductdemand();
	public void ExecUnconfirmscheme();
	public void SaveFirstproduct(Firstproductdemand f,List<Productstructure> productList);
}
