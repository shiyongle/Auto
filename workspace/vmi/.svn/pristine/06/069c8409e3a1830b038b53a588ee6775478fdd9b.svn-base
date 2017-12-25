package Com.Dao.System;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import Com.Base.Dao.IBaseDao;
import Com.Base.Util.ListResult;

import Com.Entity.System.Syscfg;

public interface ISyscfgDao extends IBaseDao {

	
	HashMap<String, Object> ExecSave(Syscfg syscfg);

	Syscfg Query(String fid);
	
	void ExecSaveSyscfgs (HttpServletRequest req);
	
	ListResult ExecBuildReponseJson (HttpServletRequest req);

	@Deprecated
	ListResult ExecGainOrderWayCfg(HttpServletRequest request);

	ListResult ExecGainCfgByFkey(HttpServletRequest request, String fkey);

	void saveBoardOrderCfg(String userId, boolean add);

	void ExecSaveSyscfgsBysKeyAndValue(HttpServletRequest request);
	
}
