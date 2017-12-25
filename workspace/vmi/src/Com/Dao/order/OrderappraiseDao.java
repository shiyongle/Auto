package Com.Dao.order;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Service;

import Com.Base.Dao.BaseDao;
import Com.Entity.order.Orderappraise;
@Service("orderappraiseDao")
public class OrderappraiseDao extends BaseDao implements IOrderappraiseDao {

	@Override
	public HashMap<String, Object> ExecSave(Orderappraise dlv) {
		// TODO Auto-generated method stub
		HashMap<String, Object> params = new HashMap<>();
		if (dlv.getFid().isEmpty()) {
			dlv.setFid(this.CreateUUid());
		}
			this.saveOrUpdate(dlv);
		
		params.put("success", true);
		return params;
	}

	@Override
	public Orderappraise Query(String fid) {
		// TODO Auto-generated method stub
		return this.getHibernateTemplate().get(
				Orderappraise.class, fid);
	}

	@Override
	public void Execautoorderapparises() {
		String sql="select o.fid fdeliverorderid,o.fnumber,ifnull(o.fboxtype,0) fboxtype,o.fcustomerid,o.fsupplierid,a.fid from t_ord_deliverorder o"
				+" left join t_ord_orderappraise a on a.fdeliverorderid=o.fid "
				+" where date_format(fouttime,'%Y-%m-%d')<=date_format(date_sub(curdate(), interval 2 DAY),'%Y-%m-%d')"
				+" and  date_format(o.fcreatetime,'%Y-%m')>='2015-06'and a.fid is null " ;
		List<HashMap<String,Object>> list= this.QueryBySql(sql);
		Orderappraise o;
		for(int i=0;i<list.size();i++)
		{
			 o=new Orderappraise();
			 o.setFid(this.CreateUUid());
			 o.setFcreatorid("3c3c9f29-64b9-11e4-bdb9-00ff6b42e1e5");
			 o.setFcreatetime(new Date());
			 o.setFupdatetime(new Date());
			 o.setFupdateuserid("3c3c9f29-64b9-11e4-bdb9-00ff6b42e1e5");
			 o.setFcustomerid((String)list.get(i).get("fcustomerid"));
			 o.setFsupplierId((String)list.get(i).get("fsupplierid"));
			 o.setFdeliverorderid((String)list.get(i).get("fdeliverorderid"));
			 o.setFappraiseman("3c3c9f29-64b9-11e4-bdb9-00ff6b42e1e5");
			 o.setFappraisetime(new Date());
			 o.setFdeliverappraise(5);
			 o.setFqualityappraise(5);
			 o.setFserviceappraise(5);
			 o.setFmultipleappraise(5);
			 o.setFordertype(((java.math.BigInteger)list.get(i).get("fboxtype")).intValue());
			 o.setFtype(0);
			 o.setFordernumber((String)list.get(i).get("fnumber"));
			 o.setFdescription("");
			 this.saveOrUpdate(o);
			
		}
	}

}
