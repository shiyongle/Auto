package Com.Dao.System;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import Com.Base.Dao.IBaseDao;
import Com.Base.Util.DJException;
import Com.Entity.System.Mainmenuitem;

public interface IMainMenuDao extends IBaseDao {
	public Mainmenuitem Query(String fid);
    public  List QueryMainmenuItemcls(String hql);
    public HashMap<String,Object> ExecSave(Mainmenuitem info) throws DJException;
    public void DelMainmenuitem(HttpServletRequest request) throws DJException;

}
