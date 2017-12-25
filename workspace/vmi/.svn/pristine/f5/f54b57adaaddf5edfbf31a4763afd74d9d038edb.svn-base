package Com.Dao.System;

import org.springframework.stereotype.Service;

import Com.Base.Dao.BaseDao;
import Com.Entity.System.UserToUser;
@Service("userToUserDao")
public class UserToUserDao extends BaseDao implements IUserToUserDao {

	@Override
	public int ExecAddUserToUser(String userID, String fidcls) {
		String[] fids = fidcls.split(",");
		UserToUser obj;
		int i=0;
		for(String fid : fids){
			if(this.QueryExistsBySql("select 1 from t_bd_usertouser where fuserid='"+fid+"' and fsuperuserid='"+userID+"'")){
				continue;
			}
			obj = new UserToUser();
			obj.setFid(this.CreateUUid());
			obj.setFuserid(fid);
			obj.setFsuperuserid(userID);
			this.saveOrUpdate(obj);
			i++;
		}
		return i;
	}

	@Override
	public void ExecDelUserToUser(String userID, String fidcls) {
		fidcls = "('"+fidcls.replace(",", "','")+"')";
		String sql = "delete from t_bd_usertouser where fsuperuserid='"+userID+"' and fuserid in "+fidcls;
		this.ExecBySql(sql);
	}

}
