package Com.Dao.System;

import Com.Base.Dao.IBaseDao;

public interface IUserToUserDao extends IBaseDao {

	int ExecAddUserToUser(String userID, String fidcls);

	void ExecDelUserToUser(String userID, String fidcls);

}
