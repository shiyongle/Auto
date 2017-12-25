package Com.Dao.System;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import Com.Base.Dao.IBaseDao;
import Com.Base.Util.ListResult;
import Com.Entity.System.Supplierjudgement;

public interface ISupplierjudgementDao extends IBaseDao {
	HashMap<String, Object> ExecSave(Supplierjudgement supplierjudgement);

	Supplierjudgement Query(String fid);

	ListResult QueryselectSupplierjudgements(HttpServletRequest request);
	
	ListResult QueryselectSupplierjudgementByID(HttpServletRequest request, String id);
	
	void ExecDeleteSupplierjudgements(String[] ids);
	
	void SaveAssessmentResult(HttpServletRequest request) throws Exception ;
}
