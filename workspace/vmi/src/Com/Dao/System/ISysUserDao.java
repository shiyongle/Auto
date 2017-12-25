package Com.Dao.System;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import Com.Base.Dao.IBaseDao;
import Com.Base.Util.DJException;
import Com.Entity.System.SysUser;
import Com.Entity.System.Userip;

public interface ISysUserDao extends IBaseDao {
	public SysUser Query(String fid);
    public  List QuerySysUsercls(String hql);
    public HashMap<String,Object> ExecSave(SysUser info);
    public SysUser ExecCheckLogin(HttpServletRequest request);
    public void ExecChangePWD(HttpServletRequest request) throws DJException;
    public void ExecChangeMSG(HttpServletRequest request) throws DJException;
    public HashMap<String,Object> ExecSaveUserip(Userip info);
	public void saveUserSupplier(String userID, String[] tArrayofFid);
	public void saveUserCustomer(String userID, String[] tArrayofFid);
	public void saveUserRole(String userID, String[] tArrayofFid);
	public void saveSubUser(SysUser user, SysUser userinfo);
	public void DelUserCustomer(String fidcls, String userID);
	public void addDefaultFriendGroup(HttpServletRequest request, SysUser info);
    
}
