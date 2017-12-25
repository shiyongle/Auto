package Com.Dao.System;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import jxl.read.biff.BiffException;

import Com.Base.Dao.IBaseDao;
import Com.Entity.System.Customer;

public interface ICustomer extends IBaseDao {
	  public HashMap<String,Object> ExecSave(Customer cust);
	  public Customer Query(String fid);
	   public void ExecImpCusSDK(HttpServletRequest request);
	   public void ExecSaveCustaccountbalanceSDK(String customerid);
	int saveCustomerExcel(HttpServletRequest request) throws IOException;
	public void saveOrUpdateMyCustomer(HttpServletRequest request);
	public void saveOrUpdateAddress(HttpServletRequest request, String customerid);
	public void DelMyCustomer(HttpServletRequest request, String customerid);
	String saveMyCustomerExcel(HttpServletRequest request) throws BiffException,
			IOException;
}
