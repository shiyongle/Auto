package Com.Dao.order;

import java.util.HashMap;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import Com.Base.Dao.BaseDao;
import Com.Base.Util.ListResult;

import Com.Entity.order.Deliverorderexception;

@Service("deliverorderexceptionDao")
public class DeliverorderexceptionDao extends BaseDao implements
		IDeliverorderexceptionDao {

	public static final String SELECT_BASE_SQL = " SELECT todoe.fid, todoe.fdeliverorderId,todoe.fremark, tod.fnumber FROM t_ord_deliverorderexception todoe left join t_ord_deliverorder tod on tod.fid = todoe.fdeliverorderId ";

	@Override
	public HashMap<String, Object> ExecSave(
			Deliverorderexception deliverorderexception) {
		// TODO Auto-generated method stub

		HashMap<String, Object> params = new HashMap<>();
		if (deliverorderexception.getFid().isEmpty()) {
			deliverorderexception.setFid(this.CreateUUid());
		}
		this.saveOrUpdate(deliverorderexception);

		params.put("success", true);
		return params;

	}

	@Override
	public Deliverorderexception Query(String fid) {
		// TODO Auto-generated method stub
		return this.getHibernateTemplate().get(
				Deliverorderexception.class, fid);
	}

	@Override
	public ListResult selectDeliverorderexceptionList(HttpServletRequest req) {
		// TODO Auto-generated method stub
		String sql = SELECT_BASE_SQL;

		return QueryFilterList(sql, req);
	}

	@Override
	public ListResult selectDeliverorderexceptionListByFid(
			HttpServletRequest req) {
		// TODO Auto-generated method stub

		String sql = SELECT_BASE_SQL + " where todoe.fid = '%s' ";

		sql = String.format(sql, req.getParameter("fid"));

		return QueryFilterList(sql, req);
	}

}
