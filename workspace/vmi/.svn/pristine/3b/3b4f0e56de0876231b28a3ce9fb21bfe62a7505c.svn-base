package Com.Base.Util.Excel;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import jxl.BooleanCell;
import jxl.Cell;
import jxl.DateCell;
import jxl.NumberCell;
import jxl.Sheet;

/**
 * Excel助手类,和Java Excel API耦合
 * 
 * @author ZJZ,447338871@qq.com
 * @date 2013-11-11上午11:12:34
 * @version v0.1
 */
public class ExcelHelper {

	/**
	 * sql直插请用这个。 但要还需要额外编写po类支持。（不能是内部类，因为内部类用一个隐藏的属性$this，会导致方法失败）
	 * 
	 * @param class1
	 *            ，元类对象。其中的po对象要与数据库表中的属性一一对应，包括类型和顺序！！且一般没必要包括主键，
	 *            因为主键名是通过参数直接传入的。
	 * @param rs
	 *            ，excel单表，与vo对象属性一一对应，包括类型和顺序！！
	 * @param tableNAme
	 *            ，表名
	 * @param idName
	 *            ，id名
	 * @param eha
	 *            ，接口的实现类。一般要实现createId（），insertAction(String sql, Map<String,
	 *            Object> map)
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	public static void insertExcelInDbFacade(Class<?> class1, Sheet rs,
			String tableNAme, String idName, ExcelHelperAuxiliary eha)
			throws InstantiationException, IllegalAccessException {
		List<Map<String, Object>> list = getParamsArray(class1, rs);
		String sql = buildInsertSql(tableNAme, idName, class1);
		eha.insertInDb(sql, idName, list);

	}

	/**
	 * 用hibernate的持久化。<h1>建议使用</h1>
	 * 
	 * @param class1
	 *            ，元类对象，可以使用hibernate的po（持久对象）
	 * @param rs
	 *            ，excel单表，但必须和po属性一一对应，包括类型和顺序！！（无用属性可以隐藏，最后一个属性列一定要赋值！！）
	 * 
	 * @param eha
	 *            ，接口的实现类
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	public static void insertExcelInDbFacade(Class<?> class1, Sheet rs,
			ExcelHelperAuxiliary eha) throws InstantiationException,
			IllegalAccessException {

		List<Object> objs = buildObjs(class1, rs);

		for (int i = 0; i < objs.size(); i++) {

			eha.saveOrupdateObj(objs.get(i));

		}

	}

	private static List<Object> buildObjs(Class<?> class1, Sheet rs)
			throws InstantiationException, IllegalAccessException {
		// TODO Auto-generated method stub
		int eLength = rs.getRows() - 1;

		List<Object> objs = new ArrayList<>(eLength);

		for (int i = 0; i < eLength; i++) {
			objs.add(buildObj(class1, rs.getRow(i + 1)));
		}

		//把空值全删了
		Iterator<Object> sListIterator = objs.iterator();

		while (sListIterator.hasNext()) {
			Object e = sListIterator.next();

			if (e == null) {
				sListIterator.remove();
			}
		}

		return objs;
	}

	/**
	 * 构建sql插入语句，一般无需调用
	 * 
	 * @param tableNAme
	 *            ，表名
	 * @param idName
	 *            ，id名
	 * @param class1
	 *            ，实体类的元类
	 * @return
	 */
	public static String buildInsertSql(String tableNAme, String idName,
			Class<?> class1) {
		// TODO Auto-generated method stub

		StringBuilder sb = new StringBuilder();

		Field[] fields = class1.getDeclaredFields();

		sb.append("INSERT INTO ");
		sb.append(tableNAme);
		sb.append(" ( " + idName + ",");

		for (int i = 0; i < fields.length; i++) {
			if (i == fields.length - 1) {
				sb.append(fields[i].getName() + ")");
			} else {
				sb.append(fields[i].getName() + ",");
			}
		}

		sb.append(" VALUES ");

		sb.append(" ( :" + idName + ",");

		for (int i = 0; i < fields.length; i++) {
			if (i == fields.length - 1) {
				sb.append(":" + fields[i].getName() + ")");
			} else {
				sb.append(":" + fields[i].getName() + ",");
			}
		}

		return sb.toString();
	}

	/**
	 * 获得参数列表，主要用于配合系统中的param对象。一般无需调用
	 * 
	 * @param class1
	 *            ，
	 * @param rs
	 *            ，Excel的单表
	 * @return,不包括fid的实体类的参数，主要用于配合系统中的param对象。
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	public static List<Map<String, Object>> getParamsArray(Class<?> class1,
			Sheet rs) throws InstantiationException, IllegalAccessException {
		// TODO Auto-generated method stub
		int eLength = rs.getRows() - 1;

		List<Map<String, Object>> r = new ArrayList<>();

		Object[] objs = new Object[eLength];

		for (int i = 0; i < objs.length; i++) {
			objs[i] = buildObj(class1, rs.getRow(i + 1));
		}

		for (int i = 0; i < eLength; i++) {
			r.add(getParamMap(objs[i]));
		}

		return r;
	}

	private static Map<String, Object> getParamMap(Object object)
			throws IllegalArgumentException, IllegalAccessException {
		// TODO Auto-generated method stub
		Map<String, Object> r = new HashMap<String, Object>();

		Class<?> class1 = object.getClass();

		Field[] fields = class1.getDeclaredFields();

		for (int i = 0; i < fields.length; i++) {

			fields[i].setAccessible(true);

			if (fields[i].getType() == Date.class) {

				SimpleDateFormat sdf = new SimpleDateFormat(
						"yyyy-MM-dd'T'HH:mm:ss.SSS");
				String datet = sdf.format(fields[i].get(object));

				r.put(fields[i].getName(), datet);

			} else if (fields[i].getType() == Boolean.class) {
				int n = (boolean) fields[i].get(object) ? 1 : 0;

				r.put(fields[i].getName(), n);
			} else {
				r.put(fields[i].getName(), fields[i].get(object));
			}

		}

		return r;
	}

	private static Object buildObj(Class<?> class1, Cell[] row)
			throws InstantiationException, IllegalAccessException {
		// TODO Auto-generated method stub
		Object obj = class1.newInstance();

		Field[] fields = class1.getDeclaredFields();

		// 长度不等则返回null
		// if (fields.length != row.length) {
		// return null;
		// }

		int emptyCount = 0;

		for (int i = 0; i < row.length; i++) {

			fields[i].setAccessible(true);

			// 如果是空值，直接跳过
			String contentT = row[i].getContents();

			if (contentT == null || contentT.equals("")) {

				emptyCount++;

				continue;

			}

			if (Number.class.isAssignableFrom(fields[i].getType())) {

				NumberCell cell = (NumberCell) row[i];

				if (fields[i].getType() == Integer.class) {

					fields[i].set(obj, (int) (cell.getValue()));

				} else if (fields[i].getType() == Float.class) {

					fields[i].set(obj, (float) (cell.getValue()));

				} else if (fields[i].getType() == Double.class) {

					fields[i].set(obj, cell.getValue());

				}

			} else if (Date.class.isAssignableFrom(fields[i].getType())) {
				DateCell cell = (DateCell) row[i];
				fields[i].set(obj, cell.getDate());
			} else if (Boolean.class.isAssignableFrom(fields[i].getType())) {
				BooleanCell cell = (BooleanCell) row[i];
				fields[i].set(obj, cell.getValue());
			} else {
				fields[i].set(obj, row[i].getContents());
			}
		}

		if (emptyCount == row.length) { 

			obj = null;

		}

		return obj;
	}

}
