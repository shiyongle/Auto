package Com.Dao.System;

import java.util.Date;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import Com.Base.Dao.BaseDao;
import Com.Base.Util.params;
import Com.Entity.System.MaterialLimit;
import Com.Entity.System.ProducePlan;

@Service("producePlanDao")
public class ProducePlanDao extends BaseDao implements IProducePlanDao {

	@Resource
	IBaseSysDao baseSysDao;
	
	@Override
	public void saveProducePlan(ProducePlan producePlan, String userid) {
		
		if(StringUtils.isEmpty(producePlan.getFid())){
			producePlan.setFid(this.CreateUUid());
			producePlan.setFcreatetime(new Date());
		}
		producePlan.setFcreatorid(userid);	//更改人id
		producePlan.setFupdatetime(new Date());
		this.saveOrUpdate(producePlan);
	}

	@Override
	public void DelProducePlan(String fidcls) {
		String sql = "delete from t_pdt_produceplan where fid "+baseSysDao.getCondition(fidcls);
		this.ExecBySql(sql);
	}

	@Override
	public void saveMaterialLimit(MaterialLimit obj) {
		String sql = "delete from t_sys_material_limit where fcustomerid = '"+obj.getFcustomerid()+"'";
		this.ExecBySql(sql);
		obj.setFid(this.CreateUUid());
		this.saveOrUpdate(obj);
	}
	
}
