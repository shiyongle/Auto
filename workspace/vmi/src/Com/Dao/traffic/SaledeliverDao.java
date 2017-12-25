package Com.Dao.traffic;

import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.stereotype.Service;

import Com.Base.Dao.BaseDao;
import Com.Entity.traffic.Saledeliver;
import Com.Entity.traffic.Saledeliverentry;

@Service("SaledeliverDao")
public class SaledeliverDao extends BaseDao implements ISaledeliverDao {

	@Override
	public HashMap<String, Object> ExecSave(Saledeliver Sdeliver) {
		// TODO Auto-generated method stub
		HashMap<String, Object> params = new HashMap<>();
		if (Sdeliver.getFid().isEmpty()) {
			Sdeliver.setFid(this.CreateUUid());
		}
			this.saveOrUpdate(Sdeliver);
		
		params.put("success", true);
		return params;
	}

	@Override
	public Saledeliver Query(String fid) {
		// TODO Auto-generated method stub
		return (Saledeliver) this.getHibernateTemplate().get(
				Saledeliver.class, fid);
	}
	
	@Override
	public HashMap<String, Object> ExecSaledeliver(HashMap<String, Object> params,int type) throws Exception {
		// TODO Auto-generated method stub
		if(type == 1){			//保存出库单
			Saledeliver Tinfo = (Saledeliver) params.get("Tinfo");
			ExecSave(Tinfo);
			ExecByHql("Delete FROM Saledeliverentry where fparentid = '" + Tinfo.getFid() +"'");
			ArrayList tnlist = (ArrayList) params.get("tnlist");
			for(int i=0;i<tnlist.size();i++){
				Saledeliverentry tninfo = ((Saledeliverentry)tnlist.get(i));
				if(tninfo.getFid().isEmpty()){
					tninfo.setFid(this.CreateUUid());
				}
				tninfo.setFparentid(Tinfo.getFid());
				this.saveOrUpdate(tninfo);
			}
			
		}else if(type == 2){	//删除出库单
			String fids = params.get("fidcls").toString();
			if(fids!=null && !fids.equals("")){
				String hql = "Delete FROM Saledeliver where fid in " + fids +"";
				ExecByHql(hql);
				ExecByHql("Delete FROM Saledeliverentry where fparentid in " + fids +"");
			}
			
		}
		
		params.put("success", true);
		return params;
	}

	@Override
	public void UpdateSaledeliverentry(Saledeliverentry entry) {
		// TODO Auto-generated method stub
		this.saveOrUpdate(entry);
		
	}

}
