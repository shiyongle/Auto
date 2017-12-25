package Com.Base.Web;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.http.HttpServlet;

import Com.Base.Util.DJException;
import Com.Base.Util.ServerContext;
import Com.Base.Util.SpringContextUtils;
import Com.Dao.System.ISysUserDao;

public class MyServletContextListener extends HttpServlet implements ServletContextListener {

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
		ISysUserDao ISysUDao = (ISysUserDao) SpringContextUtils
				.getBean("SysUserDao");
		try {
//			ServerContext.getOracleHelper().Destroyed();
			//System.out.println(">>>>>>>>>>>>>>>>服务器停止>>>>>>>>>>>>>>>>>>"); 
//			if(ISysUDao!=null)
//			{
//				ISysUDao.ExecBySql("delete from t_sys_useronline");
//			}
		} catch (DJException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
//		ISysUserDao ISysUDao = (ISysUserDao) SpringContextUtils
//				.getBean("SysUserDao");
		try {
			ServerContext.ServerContext();
		} catch (DJException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}

}
