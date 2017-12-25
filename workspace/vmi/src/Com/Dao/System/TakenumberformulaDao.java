package Com.Dao.System;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import Com.Base.Dao.BaseDao;
import Com.Base.Util.ListResult;
import Com.Entity.System.Takenumberformula;
@Service("takenumberformulaDao")
public class TakenumberformulaDao extends BaseDao implements ITakenumberformulaDao {

	private static final String SELECT_BASE = " SELECT fid, fnumber, fname, fsqlStatement FROM t_sys_takenumberformula ";

	@Override
	public HashMap<String, Object> ExecSave(Takenumberformula takenumberformula) {
		// TODO Auto-generated method stub
		HashMap<String, Object> params = new HashMap<>();
		if (takenumberformula.getFid().isEmpty()) {
			takenumberformula.setFid(this.CreateUUid());
		}
			this.saveOrUpdate(takenumberformula);
		
		params.put("success", true);
		return params;
	}

	@Override
	public Takenumberformula Query(String fid) {
		// TODO Auto-generated method stub
		return (Takenumberformula) this.getHibernateTemplate().get(
				Takenumberformula.class, fid);
	}

	@Override
	public ListResult QueryselectTakenumberformulas(HttpServletRequest request) {
		// TODO Auto-generated method stub
		
		String sql = SELECT_BASE;
		
		ListResult lrT = QueryFilterList(sql, request);
		
		return lrT;
	}

	@Override
	public ListResult QueryselectTakenumberformulaByID(
			HttpServletRequest request, String id) {
		// TODO Auto-generated method stub
		
		String sql = SELECT_BASE + " where fid = '%s' ";
		
		sql = String.format(sql, id);
		
		ListResult lrT = QueryFilterList(sql, request);
		
		return lrT;
	}

	@Override
	public void ExecDeleteTakenumberformulas(String[] ids) {
		// TODO Auto-generated method stub
		
		for ( String id : ids ) {
			
			Takenumberformula tnf = Query(id);
			
			Delete(tnf);
			
		}
		
	}


}
