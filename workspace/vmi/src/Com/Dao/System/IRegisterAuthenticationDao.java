
package Com.Dao.System;

import java.rmi.RemoteException;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import Com.Base.Dao.IBaseDao;
import Com.Entity.System.Customer;
import Com.Entity.System.SysUser;

public interface IRegisterAuthenticationDao extends IBaseDao {

	void saveUserManageUser(SysUser userinfo,Customer customer,String sql);
	 void saveRegisterUserAndCustomerInfo(HttpServletRequest request) ;
	 void saveCustomerAndFsupplier(Customer c);
	 String ExecDelAuthenticationFile(String ids);
	 String ExecDelFileByFparentid(String fparentid);
//	 HashMap<String, Object> ExecSaveCustomerAndRole(Customer c);
	 void ExecSaveUserpermission(String fid,String fischeck,String userid);
	 
}
