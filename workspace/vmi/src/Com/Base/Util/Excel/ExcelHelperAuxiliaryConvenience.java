package Com.Base.Util.Excel;

import java.util.List;
import java.util.Map;

/**
 * 命令类的部分使用，一般用此类即可。便利类
 * 
 * @author ZJZ
 * 
 */
public abstract class ExcelHelperAuxiliaryConvenience implements
		ExcelHelperAuxiliary {

	@Override
	public void insertInDb(String sql, String idName,
			List<Map<String, Object>> list) {
		// TODO Auto-generated method stub
		for (Map<String, Object> map : list) {
			String st = createId();// fid，自产生的，根据需要修改

			map.put(idName, st);

			// insert();//插入数据库即可
			insertAction(sql, map);
		}
	}
}
