package Com.Dao.System;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import Com.Base.Dao.IBaseDao;
import Com.Entity.System.Supplier;
import Com.Entity.System.Useronline;

public interface ISupplierDao extends IBaseDao {
	public Supplier Query(String fid);
    public  List QuerySysUsercls(String hql);
    public HashMap<String,Object> ExecSave(Supplier info);
    public Useronline ExecCheckLogin(HttpServletRequest request);

}
