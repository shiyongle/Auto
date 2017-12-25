package Com.Dao.order;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Service;

import Com.Base.Dao.BaseDao;
import Com.Base.Util.DJException;
import Com.Base.Util.ServerContext;
import Com.Base.Util.SpringContextUtils;
import Com.Entity.order.Deliverapply;
import Com.Entity.order.Mystock;
import Com.Entity.order.Saleorder;
import Com.Entity.order.SchemeDesignEntry;
@Service("MystockDao")
public class MystockDao extends BaseDao implements IMystockDao {
	private ISaleOrderDao saleOrderDao = (ISaleOrderDao)SpringContextUtils.getBean("saleOrderDao");
	@Override
	public void saveMyStockInBulk(List<Mystock> myStocks,String userid) {
		String fcustomerid = null;
		String sql="select fcustomerid from ( select fcustomerid  from t_bd_usercustomer where fuserid='"+userid+"'"+
				" union select c.fcustomerid from  t_sys_userrole r "+
				" left join t_bd_rolecustomer c on r.froleid=c.froleid where r.fuserid='"+userid+"' )  s where fcustomerid is not null " ;
		List<HashMap<String,Object>> list=this.QueryBySql(sql);
		if(list.size()>1){
			throw new DJException("当前用户是管理员,不能操作！");
		}else if(list.size()==1){
			fcustomerid = list.get(0).get("fcustomerid").toString();
		}else if(list.size()<1){
			throw new DJException("当前用户没有关联客户,请联系平台业务部！");
		}
		for(Mystock mystock : myStocks){
			mystock.setFid(this.CreateUUid());
			mystock.setFnumber(ServerContext.getNumberHelper().getNumber("mystock", "B", 4, false));
			mystock.setFcreateid(userid);
			mystock.setFcreatetime(new Date());
			mystock.setFcustomerid(fcustomerid);
			mystock.setFstate(0);
			this.saveOrUpdate(mystock);
		}
	}
	@Override
	public Mystock Query(String fid) {
		// TODO Auto-generated method stub
		return this.getHibernateTemplate().get(
				Mystock.class, fid);
	}
	//要货管理下单;
	@Override
		public HashMap<String, Object> ExecMyStockorder(ArrayList<Saleorder> solist,
				String ordersql, String deliverid) throws Exception {
			// TODO Auto-generated method stub
			HashMap<String, Object> params = new HashMap<>();
			
			/*for(int i=0;i<solist.size();i++){
				saleOrderDao.ExecSave(solist.get(i));
			}
			*/
			if(ordersql.length()>0){
				ExecBySql(ordersql);
			}
			/*
			List<HashMap<String,Object>> deliverslist = QueryBySql("select fid from mystock where fid in (" + deliverid +")");
			for(int j=0;j<deliverslist.size();j++){//修改要货申请状态
//				deliverapplyDao.updateDeliverapplyStateByDelivers(deliverslist.get(j).get("fid").toString(), 2);
				this.ExecBySql("update mystock set fordered=1 where fid ='"+deliverslist.get(j).get("fid").toString()+"'");
			}*/
			//生成生产订单，（单个制造商，则生成制造商订单，库存信息）
			saleOrderDao.ExecProductPlanAndStorebalanceBySingel(solist);
			
			params.put("success", true);
			return params;
		}
	@Override
	public void ExecSave(List<Deliverapply> list,List<SchemeDesignEntry> entryList) {
		for (int i = 0; i < list.size(); i++) {
			Deliverapply order = list.get(i);
			this.saveOrUpdate(order);
		}
		for(SchemeDesignEntry entry : entryList){
			this.saveOrUpdate(entry);
		}
	}
}
