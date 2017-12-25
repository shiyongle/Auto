package Com.Base.Web;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import Com.Base.Util.DJException;
import Com.Base.Util.ServerContext;
import Com.Base.Util.SpringContextUtils;
import Com.Dao.System.ISysUserDao;

public class SessionListener implements HttpSessionListener {

	@Override
	public void sessionCreated(HttpSessionEvent event) {
		// TODO Auto-generated method stub
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent event) {
		// TODO Auto-generated method stub
		ISysUserDao ISysUDao = (ISysUserDao) SpringContextUtils
				.getBean("SysUserDao");
			try {
//				ServerContext.getOracleHelper().CloseConn(event.getSession().getId());
			} catch (DJException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		String sessionid = event.getSession().getId();
		String sql="delete from t_sys_useronline where fsessionid='"+sessionid+"'";
		ISysUDao.ExecBySql(sql);
		
		//20151020同步清除Session缓存;
		ServerContext.delUseronline(sessionid);
	
	}

}
