package Com.Dao.System;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import Com.Base.Dao.IBaseDao;
import Com.Entity.System.Simplemessagecfg;

public interface ISimplemessagecfgDao extends IBaseDao {
	HashMap<String, Object> ExecSave(Simplemessagecfg simplemessagecfg);

	Simplemessagecfg Query(String fid);
		 
	String ExecSaveSimplemessagecfg(HttpServletRequest request);

	String ExecSaveSimplemessagecfgs(HttpServletRequest request);
}
