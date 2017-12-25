package Com.Base.Util;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import Com.Base.Dao.IBaseDao;

public class DataUtil {

	private static final String CHECK_IS_WRONG = "数据校验有误，%s错误！";

	private static final String CHECK_IS_WRONG_PERMISSION = "数据校验有误，请确认是否有%s%s相应权限,或%s错误！";

	private static final String CHECK_IS_WRONG_CUSTOMER = "客户";
	private static final String CHECK_IS_WRONG_SUPPLIER = "、制造商";
	private static final String NOT_NULL_TIP = "%s为空，请修改！";

	public final static String numberFormatOneZero = "###.0";
	public final static DecimalFormat dfOneZero = new DecimalFormat(
			numberFormatOneZero);
	public static String dFOneZero(BigDecimal num) {
		if (null == num)
			return "0.0";
		return dfOneZero.format(num);
	}

	public static String dFOneZero(Object num) {
		if (null == num)
			return "0.0";
		BigDecimal bdecimal = null;
		if (num instanceof String) {
			String strNum = (String) num;
			if (strNum.compareToIgnoreCase("null") == 0) {
				return "0.0";
			}
			bdecimal = new BigDecimal(strNum);
		} else if (num instanceof BigDecimal) {
			bdecimal = (BigDecimal) num;
		} else {
			bdecimal = (BigDecimal) num;
		}
		return dfOneZero.format(bdecimal);
	}

	public static String[] InStrToArray(String fidcls) {
		String[] tArrayofFid;
		if (fidcls.startsWith("(")) {
			tArrayofFid = fidcls.substring(1, fidcls.length() - 1).split(",");

		} else {
			tArrayofFid = fidcls.split(",");
		}

		for (int i = 0; i < tArrayofFid.length; i++) {
			if (tArrayofFid[i].startsWith("'")) {
				tArrayofFid[i] = (String) tArrayofFid[i].subSequence(1,
						tArrayofFid[i].length() - 1);
			}
		}

		return tArrayofFid;
	}

	/**
	 * 将字符串改变为jsonArray对象
	 * 
	 * @param s
	 * @return
	 */
	public static JSONArray getJsonArrayByS(String s) {

		String dataT = s;

		JSONArray jsa = null;

		if (dataT.charAt(0) == '{') {
			dataT = "[" + dataT + "]";
		}

		jsa = JSONArray.fromObject(dataT);

		return jsa;
	}

	/**
	 * 手动外键约束.与IBaseDao耦合
	 * 
	 * @param table
	 * @param foreignIdField
	 *            外键字段
	 * @param foreignId
	 *            外键值
	 * @param baseDao
	 * @return
	 * 
	 *         合法性
	 * 
	 * @date 2013-11-20 上午9:57:03 (ZJZ)
	 */
	public static boolean isNoIlleForeignKeyConstraint(String table,
			String foreignIdField, String foreignId, IBaseDao baseDao) {

		boolean r = false;

		String sql = "SELECT count(*) as countt FROM %s where %s = '%s' ";

		sql = String.format(sql, table, foreignIdField, foreignId);

		List<HashMap<String, Object>> sList = baseDao.QueryBySql(sql);

		int n = ((BigInteger) sList.get(0).get("countt")).intValue();

		r = n == 0 ? true : false;

		return r;
	}

	/**
	 * 外键控制的门面调用，与DataUtil.isNoIlleForeignKeyConstraint耦合。
	 * 
	 * @param table
	 * @param foreignIdField
	 * @param baseDao
	 * @param fids
	 *            客户端自有拓展而来的ids参数
	 * @return 全部合法性
	 * 
	 * @date 2013-11-20 上午10:29:41 (ZJZ)
	 */
	public static boolean isforeignKeyConstraintLegal(String table,
			String foreignIdField, IBaseDao baseDao, String fids) {
		boolean r = true;

		String[] fidclsArray = DataUtil.InStrToArray(fids);

		for (String id : fidclsArray) {

			if (!DataUtil.isNoIlleForeignKeyConstraint(table, foreignIdField,
					id, baseDao)) {
				r = false;
				return false;
			}

		}

		return r;
	}

	/**
	 * 数据校验的方法，与DataUtil.isCorrectDataCheck耦合。
	 * 
	 * @param table
	 *            进行数据校验的数据库表名；
	 * @param cid
	 *            table客户字段名,table没有客户字段则为null；
	 * @param sid
	 *            table制造商字段名,table没有制造商字段则为null；
	 * @param fids
	 *            客户端request的fidcls参数及外部接口传入的参数；
	 * @return 全部合法性？
	 * 
	 * @date 2014-05-27 cd，
	 * 
	 */
	public static boolean isCorrectDataCheck(HttpServletRequest request,
			String table, String cid, String sid, IBaseDao baseDao, String fid) {

		String sql = "SELECT count(*) as countt FROM %s where fid = '%s' "
				+ baseDao.QueryFilterByUser(request, cid, sid);

		sql = String.format(sql, table, fid);

		List<HashMap<String, Object>> sList = baseDao.QueryBySql(sql);

		int n = ((BigInteger) sList.get(0).get("countt")).intValue();

		// 简化
		return n > 0;
	}

	/**
	 * 数据校验的方法，与DataUtil.isCorrectDataCheck耦合。
	 * 
	 * @param table
	 *            进行数据校验的数据库表名；
	 * @param cid
	 *            table客户字段名,table没有客户字段则为null；
	 * @param sid
	 *            table制造商字段名,table没有制造商字段则为null；
	 * @param fids
	 *            客户端request的fidcls参数及外部接口传入的参数；
	 * @return 全部合法？
	 * 
	 * @date 2014-05-27 cd
	 */
	public static boolean DataCheck(HttpServletRequest request, String table,
			String cid, String sid, IBaseDao baseDao, String fids) {

		if (fids == null || fids.equals("")) {
			// r = false;
			return false;
		}
		String[] fidclsArray = DataUtil.InStrToArray(fids);

		for (String id : fidclsArray) {

			if (!DataUtil.isCorrectDataCheck(request, table, cid, sid, baseDao,
					id)) {
				// r = false;
				return false;
			}

		}

		return true;
	}

	/**
	 * 外键权限校验方法，直接抛出错误，无需自己构建字符串，直接调用即可
	 * 
	 * @param request
	 * @param customer
	 *            ，id
	 * @param supplier
	 *            ，id
	 * @param baseDao
	 *            ，具体实现
	 * @param table
	 *            ，外键引用表
	 * @param fidsName
	 *            ，id参数名
	 * 
	 * @date 2014-5-28 上午10:25:57 (ZJZ)
	 */
	public static void verifyUserAndDate(HttpServletRequest request,
			String customer, String supplier, IBaseDao baseDao, String table,
			String fidsName) {
		String tip;

		tip = buildTipString(customer, supplier, fidsName);

		if (!DataUtil.DataCheck(request, table, customer, supplier, baseDao,
				request.getParameter(fidsName))) {
			throw new DJException(tip);
		}

	}

	private static String buildTipString(String customer, String supplier,
			String fidsName) {
		String tip;

		// 考虑为空字符串的情况
		if (customer != null) {
			//空字符串才进行转换
			customer = customer.equals("") ? null : customer;
		}

		if (supplier != null) {

			supplier = supplier.equals("") ? null : supplier;

		}

		if (customer == null && supplier == null) {

			tip = DataUtil.CHECK_IS_WRONG;

			tip = String.format(tip, fidsName);

		} else {

			tip = DataUtil.CHECK_IS_WRONG_PERMISSION;

			String tipCus = customer == null ? ""
					: DataUtil.CHECK_IS_WRONG_CUSTOMER;
			String tipSup = supplier == null ? ""
					: DataUtil.CHECK_IS_WRONG_SUPPLIER;

			tip = String.format(tip, tipCus, tipSup, fidsName);

		}
		return tip;
	}

	/**
	 * 验证非空约束，直抛错误，调用即可。
	 * 
	 * @param fieldValue
	 *            ，值
	 * @param filedNameForUser
	 *            ，给用户看的名称
	 * 
	 * @date 2014-5-28 上午10:37:50 (ZJZ)
	 */
	public static void verifyNotNull(String fieldValue, String filedNameForUser) {

		if (fieldValue == null || fieldValue.trim().equals("")) {

			throw new DJException(String.format(DataUtil.NOT_NULL_TIP,
					filedNameForUser));

		}

	}

	/**
	 * 验证非空约束
	 * 
	 * @param map
	 */
	public static void verifyNotNull(HashMap<String, String> map) {
		Set<String> set = map.keySet();
		for (String s : set) {
			verifyNotNull(s, map.get(s));
		}
	}

	/**
	 * 统一验证非空和外键
	 * 
	 * @param fieldsNameNamesForUser
	 *            ，0 是字段名，1 i给用户看的名称（可以不设置）
	 * @param fFiledsTables
	 *            元素顺序:[0]fidsName外键名, [1]table表名, [2]customer客户字段,
	 *            [3]supplier制造商字段
	 * @param request
	 * @param baseDao
	 * 
	 * @date 2014-6-11 上午10:08:42 (ZJZ)
	 */
	public static void verifyNotNullAndDataAndPermissions(
			String[][] fieldsNameNamesForUser, String[][] fFiledsTables,
			HttpServletRequest request, IBaseDao baseDao) {

		verifyNotNull(fieldsNameNamesForUser, request);

		if (fFiledsTables != null) {
			for (String[] ele : fFiledsTables) {
				DataUtil.verifyUserAndDate(request, ele[2], ele[3], baseDao,
						ele[1], ele[0]);
			}

		}

	}

	/**
	 * 通过数组构建hashMap
	 * 
	 * @param key
	 * @param value
	 * @return
	 * 
	 * @date 2014-5-28 上午11:11:37 (ZJZ)
	 */
	public static <T, E> Map<T, E> bulidHashMapByArrays(T[] key, E[] value) {

		Map<T, E> mapT = new HashMap<>();

		int i = 0;
		for (T keyT : key) {

			mapT.put(keyT, value[i++]);

		}

		return mapT;
	}

	/**
	 * 校验传入的字符串是正整数
	 * 
	 * @param num
	 * @return
	 */
	public static boolean positiveIntegerCheck(String num) {
		if (num == null) {
			return false;
		}
		Pattern pattern = Pattern.compile("^\\+?[1-9][0-9]*$");
		Matcher matcher = pattern.matcher(num.trim());
		return matcher.matches();
	}
	/**
	 *  不能负数且大于0的小数保留小数点一位,可以存null
	 * @param num
	 * @return
	 */
	public static boolean positiveDoubleCheck(BigDecimal num) {
		if (num == null) {
			return true;
		}
		Pattern pattern = Pattern.compile("(([1-9][\\d]*)(\\.[\\d]{1})?)|(0\\.[\\d]{1})");
		Matcher matcher = pattern.matcher(num.toString());
		return matcher.matches();
	}
	
	

	/**
	 * 校验传入的字符串是整数
	 * 
	 * @param num
	 * @return
	 */
	public static boolean IntegerCheck(String num) {
		if (num == null) {
			return false;
		}
		Pattern pattern = Pattern.compile("^\\-?[1-9][0-9]*$");
		Matcher matcher = pattern.matcher(num.trim());
		return matcher.matches();
	}
	/**
	 * 校验传入的字符串是数字类型
	 * 
	 * @param num
	 * @return
	 */
	public static boolean numberCheck(String num) {
		if (num == null) {
			return false;
		}
		Pattern pattern = Pattern.compile("^\\d+(\\.\\d)?\\d*$");
		Matcher matcher = pattern.matcher(num.trim());
		return matcher.matches();
	}

	/**
	 * 校验日期时间的格式为 2014-06-04 01:02:03.000 或 2014-06-04
	 * 
	 * @param time
	 * @return
	 */
	public static boolean dateFormatCheck(String time) {
		if (time == null) {
			return false;
		}
		Pattern pattern = Pattern
				.compile("^\\d{4}-\\d{2}-\\d{2}([\\sT]\\d{2}:\\d{2}:\\d{2}(\\.\\d{1,3})?)?$");
		Matcher matcher = pattern.matcher(time);
		return matcher.matches();
	}

	/**
	 * 校验日期时间的格式为 2014-06-04 01:02:03.000或 2014-06-04
	 * future为true,则必须大于当前时间或大于等于当前日期
	 * 
	 * @param time
	 * @param future
	 * @return
	 */
	public static boolean dateFormatCheck(String time, boolean future) {
		if (!dateFormatCheck(time)) {
			return false;
		}
		SimpleDateFormat sdf = null;
		Date date = null;
		Date now = null;
		if (future) {
			if (time.length() > 10) {
				time = time.replace("T", " ");
				sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				if (time.contains(".")) {
					time = time.substring(0, time.lastIndexOf("."));
				}
				try {
					date = sdf.parse(time);
				} catch (ParseException e) {
					e.printStackTrace();
				}
				return date.getTime() > new Date().getTime();
			} else {
				sdf = new SimpleDateFormat("yyyy-MM-dd");
				try {
					now = sdf.parse(sdf.format(new Date()));
					date = sdf.parse(time);
				} catch (ParseException e) {
					e.printStackTrace();
				}
				return date.getTime() >= now.getTime();
			}
		}
		return true;
	}

	/***
	 * 统一验证非空和外键
	 * 
	 * @param fieldsNameNamesForUser
	 * @param fFiledsTables
	 *            元素顺序:[0]fidsName外键名, [1]table表名, [2]customer客户字段,
	 *            [3]supplier制造商字段, [4]fidsValue外键值
	 * @param request
	 * @param baseDao
	 */
	public static void verifyNotNullAndDataAndPermissionsByValue(
			String[][] fieldsNameNamesForUser, String[][] fFiledsTables,
			HttpServletRequest request, IBaseDao baseDao) {

//		verifyNotNull(fieldsNameNamesForUser, request);
		if (fieldsNameNamesForUser != null) {

			for (String[] ele : fieldsNameNamesForUser) {

				if (ele.length == 2) {
					DataUtil.verifyNotNull(ele[0], ele[1]);

				} else if (ele.length == 1) {
					DataUtil.verifyNotNull(request.getParameter(ele[0]), ele[0]);

				}

			}

		}
		if (fFiledsTables != null) {
			for (String[] ele : fFiledsTables) {
				DataUtil.verifyUserAndDateByValue

				(request, ele[2], ele[3], baseDao, ele[1], ele[0], ele[4]);
			}

		}

	}

	private static void verifyNotNull(String[][] fieldsNameNamesForUser,
			HttpServletRequest request) {
		if (fieldsNameNamesForUser != null) {

			for (String[] ele : fieldsNameNamesForUser) {

				if (ele.length == 2) {
					DataUtil.verifyNotNull(request.getParameter(ele[0]), ele[1]);

				} else if (ele.length == 1) {

					DataUtil.verifyNotNull(request.getParameter(ele[0]), ele[0]);

				}

			}

		}
	}

	/**
	 * 外键权限校验方法，直接抛出错误，无需自己构建字符串，直接调用即可
	 * 
	 * @param request
	 * @param customer
	 *            ，id
	 * @param supplier
	 *            ，id
	 * @param baseDao
	 *            ，具体实现
	 * @param table
	 *            ，外键引用表
	 * @param fidsName
	 *            ，id参数名
	 * @param fidsValue
	 *            ，传入值
	 */
	public static void verifyUserAndDateByValue(HttpServletRequest request,
			String customer, String supplier, IBaseDao baseDao, String table,
			String fidsName, String fidsValue) {

		String tip;

		tip = buildTipString(customer, supplier, fidsName);

		if (!DataUtil.DataCheck(request, table, customer, supplier, baseDao,
				fidsValue)) {
			throw new DJException(tip);
		}

	}
	
	/**
	 * 查询数据库中字段值是否已存在
	 * @param tableName 表名
	 * @param fid		主键
	 * @param map		字段名和字段值的映射
	 * @param baseDao
	 * @param where		条件
	 */
	public static void checkExist(String tableName,String fid,HashMap<String, String> map,IBaseDao baseDao,String where){
		Set<String> set = map.keySet();
		String sql,val,fieldName="数据字段";
		for(String s : set){
			if("fname".equals(s)){
				fieldName="名称";
			}else if("fnumber".equals(s)){
				fieldName="编号";
			}
			val = map.get(s);
			if(val==null||"".equals(val)){
				throw new DJException(fieldName+"不能为空！");
			}
			sql = "select 1 from "+tableName+" where "+s+" = '"+val+"'";
			if(fid !=null &&!"".equals(fid)){	//修改操作
				sql += " and fid != '"+fid+"'";
			}
			if(where!=null){
				sql += " and "+where;
			}
			List<?> list = baseDao.QueryBySql(sql);
			if(list.size()>0){
				throw new DJException("此"+fieldName+"已存在,请重新操作！");
			}
		}
	}
	
	
	
	/**
	 * 校验日期时间的格式为 2014-06-04 01:02:03.000或 2014-06-04
	 * future为true,则必须大于当前时间或大于等于当前日期
	 * 
	 * @param time
	 * @param future
	 * @return
	 */
	public static boolean dateFormatCheck(String time, boolean future,int days) {
		if (!dateFormatCheck(time)) {
			return false;
		}
		SimpleDateFormat sdf = null;
		Date date = null;
		Date now=null;
		Calendar nowdar = Calendar.getInstance();
		nowdar.setTime(new Date());
		nowdar.add(Calendar.DAY_OF_MONTH, days);
		if (future) {
			if (time.length() > 10) {
				time = time.replace("T", " ");
				sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				if (time.contains(".")) {
					time = time.substring(0, time.lastIndexOf("."));
				}
				try {
					date = sdf.parse(time);
				} catch (ParseException e) {
					e.printStackTrace();
				}
				return date.getTime() >nowdar.getTimeInMillis();
			} else {
				sdf = new SimpleDateFormat("yyyy-MM-dd");
				try {
					now = sdf.parse(sdf.format(nowdar.getTime()));
					date = sdf.parse(time);
				} catch (ParseException e) {
					e.printStackTrace();
				}
				return date.getTime() >= now.getTime();
			}
		}
		return true;
	}
	
	

}