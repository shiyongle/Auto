package Com.Base.Util.mySimpleUtil.excelpaster;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONFunction;
import net.sf.json.JSONObject;

import Com.Base.Dao.BaseDao;
import Com.Base.Util.JsonUtil;
import Com.Base.Util.mySimpleUtil.MySimpleToolsZ;

/**
 * 后台配套批量黏贴
 * 
 * 
 * @author ZJZ (447338871@qq.com, any Question)
 * @date 2014-12-22 下午5:11:31
 */
public class ExcelPasterUntil {

	public ExcelPasterUntil() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * 获取javabean集合
	 * 
	 * @param req
	 * @return
	 * @throws ClassNotFoundException
	 * 
	 * @date 2014-12-22 下午5:12:31 (ZJZ)
	 */
	public static List<?> buildJavaBeans(HttpServletRequest req, BaseDao baseDao)
			throws ClassNotFoundException {

		String javaBeanClassName = req.getParameter("javaBeanClassName");

		String dataItems = req.getParameter("dataItems");

		List<?> objs = JsonUtil.jsTolist(
				dataItems,
				MySimpleToolsZ.getMySimpleToolsZ().gainFullNameBySimpleName(
						javaBeanClassName, baseDao));

		// (List<?>) JSONArray.toCollection(
		// JSONArray.fromObject(dataItems),
		// Class.forName(javaBeanClassName));

		return objs;

	}

	/**
	 * 获取参数map列表
	 *
	 * @param req
	 * @return
	 * @throws ClassNotFoundException
	 *
	 * @date 2015-1-2 下午5:14:27  (ZJZ)
	 */
	public static List<Map<String, Object>> gainKeyValueMap(HttpServletRequest req)
			throws ClassNotFoundException {
		
		List<Map<String, Object>> maps = new ArrayList<>();
		
		String dataItems = req.getParameter("dataItems");
		
		JSONArray jsa = JSONArray.fromObject(dataItems);
		
		for (int i = 0; i < jsa.size(); i++) {
			
			Map<String, Object> mapT = new HashMap<>();
			
			JSONObject jso = jsa.getJSONObject(i);
			
			for (Iterator<String> iterator = jsa.iterator(); iterator.hasNext();) {
				String key = (String) iterator.next();
				
				mapT.put(key, jso.get(key));
				
			}
			
			maps.add(mapT);
		}
		
		return maps;
		
		
	}
	
	/**
	 * 保存beans
	 * 
	 * @param beans
	 * @param baseDao
	 * 
	 * @date 2014-12-23 上午9:00:04 (ZJZ)
	 */
	public static void saveJavaBeans(List<?> beans, BaseDao baseDao) {

		for (Object object : beans) {
			
			baseDao.saveOrUpdate(object);

		}

	}

	/**
	 * 保存门面
	 * 
	 * @param beans
	 * @param baseDao
	 * @param req
	 * @param command
	 * @throws ClassNotFoundException
	 * 
	 * @date 2014-12-23 上午9:00:18 (ZJZ)
	 */
	public static void saveBeansFacade(BaseDao baseDao, HttpServletRequest req,
			ExcelPasterCommand command) throws ClassNotFoundException {
		command.saveBeans(req, baseDao);
	}

	public static void main(String[] args) {
		
		//test template
		
//		@RequestMapping("/batchSaveCustomers")
//		public String batchSaveCustomers(HttpServletRequest request,
//				HttpServletResponse reponse) throws IOException {
//			reponse.setCharacterEncoding("utf-8");
//			try {
//			
//				customerDao.ExecBatchSaveObjs(request, reponse);
//				reponse.getWriter().write(JsonUtil.result(true, "保存成功!", "", ""));
////				reponse.getWriter().write(JsonUtil.result(true, "", result));
//			} catch (Exception e) {
//				reponse.getWriter().write(
//						JsonUtil.result(false, e.getMessage(), "", ""));
//			}
//			return null;
//		}
		
	}
}
