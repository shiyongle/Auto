package Com.Dao.System;

import org.springframework.stereotype.Service;

import Com.Base.Dao.BaseDao;
import Com.Base.Util.DJException;
import Com.Entity.System.Queryconfig;

@Service("QueryConfigDao")
public class QueryConfigDao extends BaseDao implements IQueryConfigDao {

	@Override
	public void ExecSave(Queryconfig info) throws DJException {
		// TODO Auto-generated method stub
		if (info.getFid().isEmpty()) {
			info.setFid(this.CreateUUid());
			this.saveOrUpdate(info);
		}
		else
		{
			this.saveOrUpdate(info);
		}
	}

	

}
