package Com.Base.Util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import Com.Dao.System.ISysUserDao;

public class SpringContextUtils implements ApplicationContextAware{
	private static ApplicationContext contex;
	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		SpringContextUtils.contex=applicationContext;
		ISysUserDao ISysUDao = (ISysUserDao) getBean("SysUserDao");
		try {
//			ServerContext.getOracleHelper().Destroyed();
			if(ISysUDao!=null)
			{
				ISysUDao.ExecBySql("delete from t_sys_useronline");
				ServerContext.delAllUseronline();
			}
		} catch (DJException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public static Object getBean(String key) {
        if (contex ==null) return null;
        return contex.getBean(key);
    }
}
