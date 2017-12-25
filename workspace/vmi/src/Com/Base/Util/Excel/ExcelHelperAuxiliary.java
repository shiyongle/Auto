package Com.Base.Util.Excel;

import java.util.List;
import java.util.Map;

/**
 * 命令类
 * 
 * @author ZJZ
 * 
 */
public interface ExcelHelperAuxiliary {
	void insertInDb(String sql, String idName, List<Map<String, Object>> list);

	/**
	 * 在此写入产生id方法。<br/>
	 * sql直插时必须实现
	 * 
	 * @return
	 */
	String createId();

	/**
	 * 再此写入具体的操作数据库方法，用sql直插。注意，是单次插入操作。<br/>
	 * sql直插时必须实现
	 * 
	 * @param map
	 */
	void insertAction(String sql, Map<String, Object> map);

	/**
	 * 用hibernate保存时重新。在次加入主键和保存的实际方法。
	 * 
	 * @param obj
	 */
	void saveOrupdateObj(Object obj);
}
