package Com.Dao.order;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import Com.Base.Dao.IBaseDao;

public interface IProductdemandfileDao extends IBaseDao{
	public void downloadProductdemandFile(HttpServletResponse response,String fid)throws IOException;
}
