package Com.Dao.System;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import Com.Base.Dao.IBaseDao;
import Com.Base.Util.ListResult;

import Com.Entity.System.Supplierjudgeproject;


public interface ISupplierjudgeprojectDao extends IBaseDao {
	HashMap<String, Object> ExecSave(Supplierjudgeproject supplierjudgeproject);

	Supplierjudgeproject Query(String fid);
	
	
	ListResult QueryselectSupplierjudgeprojects(HttpServletRequest request);
	
	ListResult QueryselectSupplierjudgeprojectByID(HttpServletRequest request, String id);
	
	void ExecDeleteSupplierjudgeprojects(String[] ids);
}
