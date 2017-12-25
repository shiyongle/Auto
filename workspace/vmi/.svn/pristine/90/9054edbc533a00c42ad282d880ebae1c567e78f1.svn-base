package Com.Dao.System;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import Com.Base.Dao.IBaseDao;
import Com.Base.Util.ListResult;
import Com.Entity.System.SupplierJudgeProjectentry;

public interface ISupplierJudgeProjectentryDao extends IBaseDao {
	HashMap<String, Object> ExecSave(SupplierJudgeProjectentry supplierJudgeProjectentry);

	SupplierJudgeProjectentry Query(String fid);
	

	ListResult QueryselectSupplierJudgeProjectentrys(HttpServletRequest request);
	
	ListResult QueryselectSupplierJudgeProjectentryByID(HttpServletRequest request, String id);
	
	void ExecDeleteSupplierJudgeProjectentrys(String[] ids);
	
	/**
	 * 保存多个分路
	 *
	 * @param supplierjudgeprojectID
	 * @param request
	 * @date 2014-4-23 上午10:51:05  (ZJZ)
	 */
	void ExecSaveSupplierJudgeProjectentryS(String supplierjudgeprojectID, HttpServletRequest request);
}
