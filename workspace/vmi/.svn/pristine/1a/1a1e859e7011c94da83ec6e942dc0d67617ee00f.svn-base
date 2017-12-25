package Com.Dao.System;

import org.springframework.stereotype.Service;

import Com.Base.Dao.BaseDao;

@Service("ListQueryDao")
public class ListQueryDao extends BaseDao implements IListQueryDao {

	@Override
	public void ExecDelListQuery(String fid) {
		// TODO Auto-generated method stub
		this.ExecBySql("delete from t_sys_listquery where fid='"+fid+"'");
		this.ExecBySql("delete from t_sys_listqueryentry where fparentid='"+fid+"'");		
	}


}
