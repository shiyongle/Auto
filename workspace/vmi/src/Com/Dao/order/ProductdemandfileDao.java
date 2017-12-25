package Com.Dao.order;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Service;

import Com.Base.Dao.BaseDao;
import Com.Base.Util.DJException;
import Com.Entity.order.Productdemandfile;
@Service("productdemandfileDao")
public class ProductdemandfileDao extends BaseDao implements IProductdemandfileDao{
	@Override
	public void downloadProductdemandFile(HttpServletResponse response,String fid) throws IOException{
//		response.setCharacterEncoding("utf-8");
		Productdemandfile p = this.getHibernateTemplate().get(Productdemandfile.class, fid);
		if(p!=null){
			InputStream in = null;
			try {
				in = new FileInputStream(p.getFpath());
			} catch (FileNotFoundException e) {
				throw new DJException("此附件文件不存在，无法下载！");
			}
			response.setContentType("application/x-msdownload");
			response.addHeader("Content-Disposition", "attachment; filename=\"" + new String(p.getFname().getBytes("UTF-8"),"iso-8859-1") + "\"");
			OutputStream out = response.getOutputStream();
			byte[] bytes = new byte[1024];
			int len = 0;
			while((len = in.read(bytes,0,1024))!=-1){
				out.write(bytes, 0, len);
			}
			out.flush();
			in.close();
		}else{
			throw new DJException("错误！附件ID不存在！");
		}
	}
}
