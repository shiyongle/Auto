package Com.Dao.System;

import javax.servlet.http.HttpServletRequest;

import Com.Base.Dao.IBaseDao;
import Com.Base.Util.DJException;
import Com.Entity.System.Button;

public interface IButtonDao extends IBaseDao {
	public Button Query(String fid);
	  public void ExecSave(Button info) throws DJException;
	  public void ExecSetPermission(String PermissionID,String Userid) throws DJException;
	  public void ExecDelPermission(String PermissionID,String Userid) throws DJException;
	  public void ExecDelButton(HttpServletRequest request) throws DJException;
	  public void ExecRecoverPermissions(String fid)throws DJException;
	  
}
