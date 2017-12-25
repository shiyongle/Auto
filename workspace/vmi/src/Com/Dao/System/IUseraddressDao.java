package Com.Dao.System;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import Com.Base.Dao.IBaseDao;
import Com.Entity.System.Useraddress;

public interface IUseraddressDao extends IBaseDao {
	  public HashMap<String,Object> ExecSave(Useraddress useraddress);
	  public HashMap<String,Object> ExecSaveMyAddress(HashMap hash);
	  public Useraddress Query(String fid);
	  List<Useraddress> getUserAdList(String userid);
	  void ExecAddUserAddressItem(HttpServletRequest req);
	  void ExecDelUserCustomer(HttpServletRequest req);
	  void ExecUserAddressDft(HashMap hash);
	  void DelUserToAddress(HashMap hash);
}
