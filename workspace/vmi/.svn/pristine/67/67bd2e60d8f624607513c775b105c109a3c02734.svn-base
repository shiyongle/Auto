package Com.Dao.System;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import Com.Base.Dao.BaseDao;
import Com.Base.Util.ListResult;
import Com.Entity.System.Supplierjudgeproject;
@Service("supplierjudgeprojectDao")
public class SupplierjudgeprojectDao extends BaseDao implements ISupplierjudgeprojectDao {

	private static final String SELECT_BASE = " SELECT tssjp.fid, tssjp.fnumber, tssjp.fname, tssjp.frate, tssjp.fsupplierJudgementID as fsupplierJudgementId, tssjp.ftakeNumberFormulaID as ftakeNumberFormulaId, tstnf.fname as takeNumberFormulaName, tssj.fname as supplierJudgementName FROM t_sys_supplierjudgeproject tssjp  left join t_sys_takenumberformula tstnf on tssjp.ftakeNumberFormulaID = tstnf.fid left join t_sys_supplierJudgement tssj on tssj.fid = tssjp.fsupplierJudgementID ";

	@Override
	public HashMap<String, Object> ExecSave(Supplierjudgeproject supplierjudgeproject) { 
		// TODO Auto-generated method stub
		HashMap<String, Object> params = new HashMap<>();
		if (supplierjudgeproject.getFid().isEmpty()) {
			supplierjudgeproject.setFid(this.CreateUUid());
		}
			this.saveOrUpdate(supplierjudgeproject);
		
		params.put("success", true);
		return params;
	}

	@Override
	public Supplierjudgeproject Query(String fid) {
		// TODO Auto-generated method stub
		return this.getHibernateTemplate().get(
				Supplierjudgeproject.class, fid);
	}

	@Override
	public ListResult QueryselectSupplierjudgeprojects(HttpServletRequest request) {
		// TODO Auto-generated method stub
		
		String sql = SELECT_BASE;
		
		ListResult lrT = QueryFilterList(sql, request);
		
		return lrT;
	}

	@Override
	public ListResult QueryselectSupplierjudgeprojectByID(
			HttpServletRequest request, String id) {
		// TODO Auto-generated method stub
		
		String sql = " SELECT tssjp.fid, tssjp.fnumber, tssjp.fname, tssjp.frate, tssjp.fsupplierJudgementID as fsupplierJudgementId_fid, tssjp.ftakeNumberFormulaID as ftakeNumberFormulaId_fid, tstnf.fname as ftakeNumberFormulaId_fname, tssj.fname as fsupplierJudgementId_fname FROM t_sys_supplierjudgeproject tssjp  left join t_sys_takenumberformula tstnf on tssjp.ftakeNumberFormulaID = tstnf.fid left join t_sys_supplierJudgement tssj on tssj.fid = tssjp.fsupplierJudgementID " + " where tssjp.fid = '%s' ";
		
		sql = String.format(sql, id);
		
		ListResult lrT = QueryFilterList(sql, request);
		
		return lrT;
	}

	@Override
	public void ExecDeleteSupplierjudgeprojects(String[] ids) {
		// TODO Auto-generated method stub
		
		for ( String id : ids ) {
			
			Supplierjudgeproject tnf = Query(id);
			
			Delete(tnf);
			
		}
		
	}


}
