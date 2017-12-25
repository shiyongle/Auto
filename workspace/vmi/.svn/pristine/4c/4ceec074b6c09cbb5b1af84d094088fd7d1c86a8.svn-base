package Com.Dao.System;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import Com.Base.Dao.BaseDao;
import Com.Base.Util.DJException;
import Com.Base.Util.ListResult;
import Com.Entity.System.SupplierJudgeProjectentry;

@Service
public class SupplierJudgeProjectentryDao extends BaseDao implements
		ISupplierJudgeProjectentryDao {

	private static final String FHIGH_LIMI_LOW_LIMIT = "下限 大于等于 上限！请修改";
	private static final String NO_BRANCHING = "没有分路，请至少填写一条";
	private static final String SELECT_BASE = " SELECT fid, fhighLimit, flowLimit, fscore, fparentID  FROM t_sys_supplierJudgeProjectentry ";

	@Override
	public HashMap<String, Object> ExecSave(
			SupplierJudgeProjectentry supplierJudgeProjectentry) {
		// TODO Auto-generated method stub
		HashMap<String, Object> params = new HashMap<>();

		if (supplierJudgeProjectentry.getFid().isEmpty()) {

			supplierJudgeProjectentry.setFid(this.CreateUUid());

		}

		this.saveOrUpdate(supplierJudgeProjectentry);

		params.put("success", true);
		return params;
	}

	@Override
	public SupplierJudgeProjectentry Query(String fid) {
		// TODO Auto-generated method stub
		return this.getHibernateTemplate().get(
				SupplierJudgeProjectentry.class, fid);
	}

	@Override
	public ListResult QueryselectSupplierJudgeProjectentrys(
			HttpServletRequest request) {
		// TODO Auto-generated method stub

		String sql = SELECT_BASE;

		ListResult lrT = QueryFilterList(sql, request);

		return lrT;
	}

	@Override
	public ListResult QueryselectSupplierJudgeProjectentryByID(
			HttpServletRequest request, String id) {
		// TODO Auto-generated method stub

		String sql = SELECT_BASE + " where fid = '%s' ";

		sql = String.format(sql, id);

		ListResult lrT = QueryFilterList(sql, request);

		return lrT;

		// return null;
	}

	@Override
	public void ExecDeleteSupplierJudgeProjectentrys(String[] ids) {
		// TODO Auto-generated method stub

		for (String id : ids) {

			SupplierJudgeProjectentry spjm = Query(id);

			Delete(spjm);

		}

	}

	@Override
	public void ExecSaveSupplierJudgeProjectentryS(
			String supplierjudgeprojectID, HttpServletRequest request) {
		// TODO Auto-generated method stub
		
		ArrayList<SupplierJudgeProjectentry> supplierJudgeProjectentrys = (ArrayList) request
				.getAttribute("SupplierJudgeProjectentry");

		if (supplierJudgeProjectentrys.size() == 0) {
			
			throw new DJException(NO_BRANCHING);
			
		}
		
		for (SupplierJudgeProjectentry supplierJudgeProjectentryT : supplierJudgeProjectentrys) {

			if (supplierJudgeProjectentryT.getFhighLimit() <= supplierJudgeProjectentryT.getFlowLimit()) {
				
				throw new DJException(FHIGH_LIMI_LOW_LIMIT);
				
			}
			
			supplierJudgeProjectentryT.setFparentId(supplierjudgeprojectID);

			ExecSave(supplierJudgeProjectentryT);

		}
	}

}
