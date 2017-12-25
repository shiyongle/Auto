package Com.Dao.System;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import Com.Base.Dao.IBaseDao;
import Com.Base.Util.ListResult;
import Com.Entity.System.Takenumberformula;

public interface ITakenumberformulaDao extends IBaseDao {
	HashMap<String, Object> ExecSave(Takenumberformula takenumberformula);

	Takenumberformula Query(String fid);

	ListResult QueryselectTakenumberformulas(HttpServletRequest request);

	ListResult QueryselectTakenumberformulaByID(HttpServletRequest request,
			String id);

	void ExecDeleteTakenumberformulas(String[] ids);
}
