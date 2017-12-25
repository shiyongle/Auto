package Com.Controller.System;

import org.springframework.stereotype.Service;

import Com.Base.Dao.BaseDao;
import Com.Dao.System.ISysLogDao;

@Service("sysLogDao")
public class SysLogDao extends BaseDao implements ISysLogDao {

}
