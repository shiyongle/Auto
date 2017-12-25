package Com.Dao.Logistics;

import java.io.File;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import Com.Base.Dao.BaseDao;
@Service("CarDao")
public class CarDao extends BaseDao implements ICarDao{

	@Override
	public void DelCar(String fid){
		File file;
		String sql = "";
		for(String id : fid.split(",")){
			sql = "select fpath from t_ord_productdemandfile where fparentid='"+id+"'";
			List<HashMap<String,Object>> list = this.QueryBySql(sql);
			if(list.size()>0){
				file = new File((String)list.get(0).get("fpath"));
				if(file.exists()){
					file.delete();//删除上传的附件文件
				}
			}
			sql = "delete from t_ord_productdemandfile where fparentid ='"+fid+"'";
			this.ExecBySql(sql);//删除附件表信息
			
			sql = "delete from t_ord_car where fid ='"+id+"'";
			this.ExecBySql(sql);//删除车辆表信息
		}
	}
}
