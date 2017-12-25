package Com.Dao.System;

import java.rmi.RemoteException;

import javax.servlet.http.HttpServletRequest;

import Com.Base.Dao.IBaseDao;
import Com.Entity.System.Customer;
import Com.Entity.System.SysUser;

public interface ISupCustomerInfoDao extends IBaseDao {

	void saveCustomerInfo(Customer customer, String userid);

	String ExecSendMessageForCustomers(String fidcls, String userid, String way) throws RemoteException;

	void ExecdeleteForCustomers(String fidcls, String userid);
}
