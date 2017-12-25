package Com.Dao.System;

import java.util.List;

import org.springframework.stereotype.Service;

import Com.Base.Dao.BaseDao;
import Com.Base.Util.DJException;
import Com.Entity.System.CustRelationAdress;

@Service("CustRelationAdressDao")
public class CustRelationAdressDao extends BaseDao implements ICustRelationAdressDao {

	@Override
	public void ExecAddCustRelationAdress(String[] fid,String id) {
		// TODO Auto-generated method stub
		String sql="";
		for (int i = 0; i < fid.length ; i++) {
			sql = "select 1 from t_bd_custrelationadress where faddressid='%s' and fcustomerid='%s'";
			sql = String.format(sql, fid[i],id);
			List list =this.QueryBySql(sql);
			if(list.size()==0){
				CustRelationAdress ra = new CustRelationAdress();
				ra.setFaddressid(fid[i]);
				ra.setFid(this.CreateUUid());
				ra.setFcustomerid(id);
				saveOrUpdate(ra);
			}else{
				throw new DJException("该地址已经添加过了！");
			}
			
		}
	}

	@Override
	public void ExecDelCustRelationAdress(String[] fid,String id) {
		// TODO Auto-generated method stub
		
	}

	
}
