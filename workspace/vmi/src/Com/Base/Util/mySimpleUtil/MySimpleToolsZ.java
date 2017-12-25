package Com.Base.Util.mySimpleUtil;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays; 
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.tomcat.util.http.fileupload.FileItem;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItemFactory;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;
import org.hibernate.metadata.ClassMetadata;

import Com.Base.Dao.BaseDao;
import Com.Base.Dao.IBaseDao;
import Com.Base.Util.DJException;
import Com.Base.Util.ListResult;
import Com.Base.Util.file.MyMultiFileUploadHelper;
import Com.Entity.System.Useronline;
import Com.Entity.order.Productdemandfile;

/**
 * 简单零散的工具方法，避免和系统冲突,单独列个类
 * 
 * 
 * @author ZJZ (447338871@qq.com, any Question)
 * @date 2014-9-19 下午3:24:37
 */
public class MySimpleToolsZ {

	private static final String EXCEL_EXPORT_VIEW_T = "excel_export_view_t";

	public static final String ACCESSORY_ID = "accessoryId";

	private Map<String, ClassMetadata> entitycls;
	
	
	public static interface FileRelActions {
		
		boolean dealWithFileSuffix(String suffix);
		
	}
	
	//id 集合
	public static final Set<String> ADMIN_IDS = new HashSet<>();
	
	//管理员id在此添加
	public static final String[] ADMIN_IDS_A = new String[] {
			"3f6112db-a952-11e2-90fc-60a44c5bbef3",
			"3f6112db-a952-11e2-90fc-60a44c5bbef3" };

	// 单例
	private static MySimpleToolsZ mySimpleToolsZ;

	public static final int NO_SEARCH = 0;
	public static final int COMMON_SEARCH = 1;
	public static final int TIME_SEARCH = 2;
	public static final int MIXED_SEARCH = 3;

	static  {
		
		for (String id : ADMIN_IDS_A) {
			
			ADMIN_IDS.add(id);
			
		}
		
	}
	
	public synchronized static MySimpleToolsZ getMySimpleToolsZ() {

		if (mySimpleToolsZ == null) {

			mySimpleToolsZ = new MySimpleToolsZ();

		}

		return mySimpleToolsZ;
	}

	private MySimpleToolsZ() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * 构建日期条件sql片段
	 * 
	 * @param request
	 * @return
	 * 
	 * @date 2014-10-11 下午4:16:34 (ZJZ)
	 */
	public String buildDateBetweenSQLFragment(HttpServletRequest request) {

		String field = request.getParameter("conditionDateField");

		String startDate = request.getParameter("beginTime");
		String endDate = request.getParameter("endTime") + " 23:59:59";

		String sqlF = " and %s between '%s' and '%s' ";

		sqlF = String.format(sqlF, field, startDate, endDate);

		return sqlF;
	}

	/**
	 * 构建模糊查询sql片段
	 * 
	 * @param request
	 * @return
	 * @throws UnsupportedEncodingException
	 * 
	 * @date 2014-10-11 下午4:16:58 (ZJZ)
	 */
	public String buildMySearchBoxSQLFragment(HttpServletRequest request)
			throws UnsupportedEncodingException {

		String field = request.getParameter("condictionField");

		return buildMySearchBoxSQLFragment(request, field);
	}

	/**
	 * 构建模糊查询sql片段，自己传入field。不常用,也不建议使用，仅单个查询时易用。
	 * 
	 * @param request
	 * @param field
	 * @return
	 * @throws UnsupportedEncodingException
	 * 
	 * @date 2014-10-11 下午4:17:46 (ZJZ)
	 */
	public String buildMySearchBoxSQLFragment(HttpServletRequest request,
			String field) throws UnsupportedEncodingException {

		String sqlF = " and %s like '%s' ";

		String value = request.getParameter("condictionValue");
		value = dealWithChinese(value);

		sqlF = String.format(sqlF, field, "%" + value + "%");

		return sqlF;
	}

	/**
	 * 设置请求和响应为 utf-8
	 * 
	 * @param request
	 * @param reponse
	 * @throws UnsupportedEncodingException
	 * 
	 * @date 2014-11-7 下午5:00:37 (ZJZ)
	 */
	public void setRequestAndReponseToUTF8(HttpServletRequest request,
			HttpServletResponse reponse) throws UnsupportedEncodingException {
		reponse.setCharacterEncoding("utf-8");
		request.setCharacterEncoding("utf-8");
	}

	/**
	 * 针对请求传来可能中文乱码而设计
	 * 
	 * @param value
	 *            ，中文
	 * @return
	 * @throws UnsupportedEncodingException
	 * 
	 * @date 2014-11-7 下午4:58:47 (ZJZ)
	 */
	public String dealWithChinese(String value)
			throws UnsupportedEncodingException {
		value = new String(value.toString().getBytes("ISO-8859-1"), "UTF-8");
		return value;
	}
	
	/**
	 * 构建模糊查询sql片段
	 * 
	 * @param request
	 * @param field
	 * @return
	 * @throws UnsupportedEncodingException
	 * 
	 * @date 2014-10-11 下午4:17:46 (ZJZ)
	 */
	public String buildMySearchBoxMixedTypeSQLFragment(
			HttpServletRequest request) throws UnsupportedEncodingException {

		StringBuilder sbF = new StringBuilder(" and ( ");

		String sqlF = " %s like '%s' ";

		String condictionFieldsS = request.getParameter("condictionFields");

		String[] condictionFields = condictionFieldsS.split(",");

		String value = request.getParameter("condictionValue");
		value = dealWithChinese(value);

		for (String condictionField : condictionFields) {

			if (!condictionField.equals(condictionFields[0])) {

				sbF.append(" or ");

			}

			sbF.append(String.format(sqlF, condictionField, "%" + value + "%"));

		}

		sbF.append(" ) ");

		return sbF.toString();
	}

	/**
	 * 构建模糊查询sql
	 * 
	 * @param request
	 * @param baseSql
	 * @return
	 * @throws UnsupportedEncodingException
	 * 
	 * @date 2014-10-29 下午2:52:32 (ZJZ)
	 */
	public String buildMySearchBoxMixedTypeSQL(HttpServletRequest request,
			String baseSql) throws UnsupportedEncodingException {

		if (judgeSearchType(request) == MySimpleToolsZ.MIXED_SEARCH) {

			baseSql += buildMySearchBoxMixedTypeSQLFragment(request);

		}

		return baseSql;
	}

	/**
	 * 构建排序sql片段，降序
	 * 
	 * @param field
	 * @return
	 * 
	 * @date 2014-10-11 下午4:18:08 (ZJZ)
	 */
	public String buildOrderBySQLFragment(String field) {

		String sqlF = " order by %s desc ";

		sqlF = String.format(sqlF, field);

		return sqlF;
	}

	/**
	 * 判断查询的执行类型
	 * 
	 * @param request
	 * @return
	 * 
	 * @date 2014-10-11 下午4:18:26 (ZJZ)
	 */
	public int judgeSearchType(HttpServletRequest request) {

		// 执行远程查询
		if (request.getParameter("condictionValue") != null) {
			if (request.getParameter("condictionFields") != null) {

				return MIXED_SEARCH;

			} else if (request.getParameter("condictionField") != null) {

				return COMMON_SEARCH;

			}

		} else if (request.getParameter("conditionDateField") != null) {

			return TIME_SEARCH;

		}

		return NO_SEARCH;

	}

	/**
	 * 按日期降序排序列表
	 * 
	 * @param list
	 * @param field
	 * 
	 * @date 2014-10-11 下午4:18:48 (ZJZ)
	 */
	public void sortListByDateDesc(List<HashMap<String, Object>> list,
			final String field) {

		Collections.sort(list, new Comparator<HashMap<String, Object>>() {

			@Override
			public int compare(HashMap<String, Object> o1,
					HashMap<String, Object> o2) {
				// TODO Auto-generated method stub

				DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

				Date date1 = null;
				Date date2 = null;
				try {

					date1 = df.parse((String) o1.get(field));

					date2 = df.parse((String) o2.get(field));

				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				return date1.compareTo(date2);
			}
		});

	}

	/**
	 * 构建折线图sql
	 * 
	 * @param table
	 * @param fieldAmount
	 * @param fieldDate
	 * @param condition
	 *            ，
	 * @param giveConditionAnd
	 *            ,是否添加and语句，一般是condition为系统过滤条件时才设置为false
	 * @return
	 * 
	 * @date 2014-10-11 下午4:19:31 (ZJZ)
	 */
	public String buildLineSql(String table, String fieldAmount,
			String fieldDate, String condition, boolean giveConditionAnd) {

		String dateT = " DATE_FORMAT(" + fieldDate + ", '%Y-%m-%d') ";

		StringBuilder sb = new StringBuilder(" SELECT  ");
		sb.append(dateT + fieldDate + " , ");
		sb.append(" sum(" + fieldAmount + ") as " + fieldAmount + " FROM ");
		sb.append(table);
		sb.append(" where  " + fieldDate
				+ " BETWEEN DATE_SUB(NOW(), INTERVAL 6 DAY) and NOW() ");

		if (condition != null && !condition.trim().equals("")) {

			if (giveConditionAnd) {

				sb.append(" and  " + condition);

			} else {

				sb.append(condition);

			}

		}

		sb.append(" GROUP BY " + dateT);
		sb.append(" order by " + fieldDate + " desc");

		return sb.toString();

	}

	/**
	 * 构建月数限制sql
	 * 
	 * @param count
	 * @param field
	 * @return
	 * 
	 * @date 2014-10-11 下午4:19:52 (ZJZ)
	 */
	public String buildDateBETWEENMonthSqlF(int count, String field) {

		String sql = " and %s BETWEEN DATE_SUB(NOW(), INTERVAL %s MONTH) and NOW() ";

		sql = String.format(sql, field, count);

		return sql;

	}

	/**
	 * 
	 * 表界面的sql构建，
	 * 
	 * @param request
	 * @param baseSql
	 * @param monthCount
	 *            ,-1就不增加月份限制
	 * @param timeFilterField
	 * @param orderField
	 *            ，排序字段
	 * @return
	 * @throws UnsupportedEncodingException
	 * 
	 * @date 2014-9-28 下午5:17:10 (ZJZ)
	 */
	public String buildBaseSql(HttpServletRequest request, String baseSql,
			int monthCount, String timeFilterField, String orderField)
			throws UnsupportedEncodingException {
		String sql = baseSql;

		sql = buildBaseSqlT(request, sql);

		if (monthCount != -1) {
			sql += MySimpleToolsZ.getMySimpleToolsZ()
					.buildDateBETWEENMonthSqlF(monthCount, timeFilterField);
		}
		if (orderField != null) {

			sql += buildOrderBySQLFragment(orderField);

		}

		return sql;
	}

	/**
	 * 构建基本查询sql，配合前端的搜索组件使用
	 * 
	 * @param request
	 * @param baseSql
	 * @param timeFilterField
	 * @return
	 * @throws UnsupportedEncodingException
	 * 
	 * @date 2014-10-11 下午4:15:57 (ZJZ)
	 */
	public String buildBaseSql(HttpServletRequest request, String baseSql,
			String timeFilterField) throws UnsupportedEncodingException {
		String sql = baseSql;

		sql = buildBaseSqlT(request, sql);

		return sql;
	}

	private String buildBaseSqlT(HttpServletRequest request, String sql)
			throws UnsupportedEncodingException {
		switch (MySimpleToolsZ.getMySimpleToolsZ().judgeSearchType(request)) {
		case MySimpleToolsZ.COMMON_SEARCH:
			sql += MySimpleToolsZ.getMySimpleToolsZ()
					.buildMySearchBoxSQLFragment(request);
			break;

		case MySimpleToolsZ.TIME_SEARCH:
			sql += MySimpleToolsZ.getMySimpleToolsZ()
					.buildDateBetweenSQLFragment(request);
			break;

		default:
			break;
		}
		return sql;
	}

	/**
	 * 
	 * 
	 * @param relativePath
	 *            ,相对路径
	 * @param pathWithName
	 *            ，有文件名的绝对路径
	 * @return
	 * 
	 * @date 2014-11-17 上午10:15:04 (ZJZ)
	 */
	public String getRelativePathWithFileName(String relativePath,
			String pathWithName) {

		return relativePath
				+ pathWithName.substring(pathWithName.lastIndexOf("/") + 1);

	}

	/**
	 * 保存文件方法，同时可以获取参数.
	 * 
	 * @param request
	 * @param fparentid
	 * @param relativePath
	 *            ,exa,file/product.
	 * @param baseDao
	 *            ,保存实体
	 * @return,参数map
	 * @throws FileUploadException
	 * @throws UnsupportedEncodingException
	 * 
	 * @date 2014-11-18 下午5:09:37 (ZJZ)
	 */
	public Map<String, String> saveFileAndGainParamsMap(
			HttpServletRequest request, String fparentid, String relativePath,
			BaseDao baseDao) throws FileUploadException,
			UnsupportedEncodingException {

		Map<String, String> params = new HashMap<String, String>();

		String fid = "-1";
		String fname = null;

		ServletFileUpload upload = new ServletFileUpload(
				new DiskFileItemFactory()); // 创建一个新的文件上传对象
		upload.setHeaderEncoding("utf-8");// 避免中文名乱码
		List<FileItem> list = upload.parseRequest(request);

		File file = null;

		String absolutePath = request.getServletContext().getRealPath(
				relativePath);

		for (FileItem item : list) {
			if (!item.isFormField()) {
				if (item.getSize() > 10 * 1024 * 1024) {
					throw new DJException("您上传的文件太大，请选择不超过10M的文件");
				}

				file = new File(absolutePath);
				if (!file.exists()) {
					file.mkdirs();
				}

				fname = item.getName();

				fname = dealWithChinese(fname);

				fid = baseDao.CreateUUid();

				String fpath = absolutePath + "/" + fid
						+ fname.substring(fname.lastIndexOf("."));

				// 保存
				try {
					item.write(new File(fpath));
				} catch (Exception e) {
					throw new RuntimeException(e);
				}

				fname = fname.substring(item.getName().lastIndexOf("/") + 1);

				Productdemandfile obj;

				obj = new Productdemandfile();

				obj.setFid(fid);
				obj.setFname(fname);
				obj.setFpath(relativePath + "/" + fid
						+ fname.substring(fname.lastIndexOf(".")));
				obj.setFparentid(fparentid);

				baseDao.saveOrUpdate(obj);

			} else if (item.isFormField()) {

				params.put(item.getFieldName(),
						dealWithChinese(item.getString()));

			}
		}

		params.put(ACCESSORY_ID, fid);

		return params;
	}
	
	/**
	 * 普通的保存文件
	 *
	 * @param request
	 * @param fileName，文件名称
	 * @param relativePath，相对路径
	 * @param baseDao
	 * @return
	 * @throws FileUploadException
	 * @throws UnsupportedEncodingException
	 *
	 * @date 2015-5-13 下午2:19:55  (ZJZ)
	 */
	public static void saveFile(HttpServletRequest request,
			String fileName, String relativePath,FileRelActions action)
			throws FileUploadException, UnsupportedEncodingException {

		Map<String, String> params = new HashMap<String, String>();

		String fname = null;

		ServletFileUpload upload = new ServletFileUpload(
				new DiskFileItemFactory()); // 创建一个新的文件上传对象

		upload.setHeaderEncoding("utf-8");// 避免中文名乱码

		List<FileItem> list = upload.parseRequest(request);

		File file = null;

		String absolutePath = request.getServletContext().getRealPath(
				relativePath);

		for (FileItem item : list) {
			if (!item.isFormField()) {
				if (item.getSize() > 10 * 1024 * 1024) {
					throw new DJException("您上传的文件太大，请选择不超过10M的文件");
				}

				file = new File(absolutePath);
				if (!file.exists()) {
					file.mkdirs();
				}

				fname = item.getName();

				fname = getMySimpleToolsZ().dealWithChinese(fname);

				
				String fileSuffix = fname.substring(fname.lastIndexOf(".") + 1);
				
				if(!action.dealWithFileSuffix(fileSuffix)){
					
					return ;
					
				};
				
				String fpath = absolutePath + "/" + fileName + "."
						+ fileSuffix;

				// 保存
				try {
					item.write(new File(fpath));
				} catch (Exception e) {
					throw new RuntimeException(e);
				}

				fname = fname.substring(item.getName().lastIndexOf("/") + 1);

			} else if (item.isFormField()) {

				params.put(item.getFieldName(),
						getMySimpleToolsZ().dealWithChinese(item.getString()));

			}
		}

	}

	public static void main(String[] args) {
		
		String fname = "abc.text";
				
		String fileSuffix = fname.substring(fname.lastIndexOf(".") + 1);
		
		System.out.println(fileSuffix);
		
	}
	
	/**
	 * 获取当前用户id
	 * 
	 * @param request
	 * @return
	 * 
	 * @date 2014-11-18 上午10:28:22 (ZJZ)
	 */
	public String getCurrentUserId(HttpServletRequest request) {

		return ((Useronline) request.getSession().getAttribute("Useronline"))
				.getFuserid();

	}

	/**
	 * 是管理员？
	 *
	 * @param request
	 * @return
	 *
	 * @date 2015-1-17 上午10:07:24  (ZJZ)
	 */
	public boolean isAdmin(HttpServletRequest request) {

		return ADMIN_IDS.contains(getCurrentUserId(request));

	}
	
	/**
	 * build the union file name with the suffix
	 * 
	 * @param baseDao
	 * @param fileName
	 * @return
	 * 
	 * @date 2014-11-22 下午2:27:15 (ZJZ)
	 */
	public String buildUnionFileName(BaseDao baseDao, String fileName) {

		return baseDao.CreateUUid() + fileName.substring(fileName.indexOf("."));

	}

	// 
	
	/**
	 * 多文件上传组件时用这个
	 *
	 * @param request
	 * @param baseDao
	 * @param relativePath，"file/product" such like this
	 * @throws Exception
	 *
	 * @date 2014-11-22 下午2:42:02  (ZJZ)
	 */
	public void saveFileAndRecordItForMultiFileUpload(
			HttpServletRequest request, BaseDao baseDao, String relativePath, String parentId)
			throws Exception {

		// 保存文件
		MyMultiFileUploadHelper myMultiFileUploadHelper = MyMultiFileUploadHelper
				.getMyMultiFileUploadHelper();

		String fileName = MySimpleToolsZ.getMySimpleToolsZ().dealWithChinese(
				request.getParameter("name"));

		String unionFileName = buildUnionFileName(baseDao, fileName);

		myMultiFileUploadHelper.upload(relativePath, unionFileName, request);

		// 保存实体
		Productdemandfile productdemandfile = new Productdemandfile();

		productdemandfile.setFname(fileName);
		productdemandfile.setFpath(request.getServletContext()
				.getRealPath(relativePath + "/").replace("\\", "/")
				+ "/" + unionFileName);
		productdemandfile.setFid(baseDao.CreateUUid());
		productdemandfile.setFcreatetime(new Date());
		productdemandfile.setFparentid(parentId);

		baseDao.saveOrUpdate(productdemandfile);

	}
	
	/**
	 * 获取当前用户关联的客户ids
	 *
	 * @param request
	 * @param dao
	 * @return
	 *
	 * @date 2014-11-27 上午11:01:28  (ZJZ)
	 */
	public List<String> gainCurrentUserRelCustmer(HttpServletRequest request, IBaseDao dao) {
		
		String userid = getCurrentUserId(request);
		
		String hql = " select fcustomerid from UserCustomer where fuserid = '%s' ";
		
		List<String> result = dao.QueryByHql( String.format(hql, userid));
		
		return result;
		
	}
	
	/**
	 * excel基于基本的sql导出方法。
	 * 
	 * 
	 * @param selectSql，store的查询sql
	 * @param request
	 * @param dao
	 * @return
	 *
	 * @date 2014-12-16 上午9:29:48  (ZJZ)
	 */
	public ListResult buildExcelListResult(String selectSql ,HttpServletRequest request, IBaseDao dao) {
		
		String colums = request.getParameter("columns");
		
		JSONArray columsJsoa = JSONArray.fromObject(colums);
		
		ListResult resultT = dao.QueryFilterList(selectSql, request);
		
		ListResult resultR = new ListResult();
		
		resultR.setTotal(resultT.getTotal());
		
		//索引map用于构建中文表头
		Map<String, String> dateIndexMap = new HashMap<>();
		
		int sizeT = columsJsoa.size();
		
		for (int i = 0; i < sizeT; i++) {
			
			JSONObject jso = columsJsoa.getJSONObject(i);

			dateIndexMap.put(jso.getString("dataIndex"), jso.getString("text"));
			
		}

		
		List<HashMap<String, Object>> dates = resultT.getData();
		
		List<HashMap<String, Object>> datesR = new ArrayList<>();
		
		Set<String> keys = dateIndexMap.keySet();
		
		for (HashMap<String, Object> item : dates) {
			
			HashMap<String, Object> goalItem = new HashMap<>();
									
			for (String key : keys) {
				
				goalItem.put(dateIndexMap.get(key), item.get(key));
				
			}
			
			datesR.add(goalItem);
			
		}
		
		resultR.setData(datesR);
		
		return resultR;
		
	}
	
	/**
	 * 获取data index对应文本的顺序，和前端配套使用
	 *
	 * @param request
	 * @return 一般是中文组成的表头数组
	 *
	 * @date 2015-3-9 下午4:35:43  (ZJZ)
	 */
	public static List<String> gainDataIndexList(HttpServletRequest request) {
		
		List<String> dataIndexs = new ArrayList<>();
		
		String colums = request.getParameter("columns");
		
		JSONArray columsJsoa = JSONArray.fromObject(colums);
		
		for (int i = 0; i < columsJsoa.size(); i++) {
			
			dataIndexs.add(columsJsoa.getJSONObject(i).getString("text"));
			
		}
		
		return dataIndexs;
	}
	
	/**
	 * 通过类简写获得全名
	 *
	 * @param simpleName
	 * @param baseDao
	 * @return
	 *
	 * @date 2014-12-23 上午10:42:55  (ZJZ)
	 */
	public synchronized String gainFullNameBySimpleName(String simpleName, IBaseDao baseDao) {
		
		if(entitycls==null)
		{
			entitycls=baseDao.getAllClassMetadata();
		}
		
		Set<String> entityset=entitycls.keySet();
		
		for (String entitykey : entityset) {
			
			if (entitykey.endsWith("."+simpleName)){
				
				return entitykey;
				
			}
			
		}
		
		return null;
		
	}
	
	/**
	 * 转换首字母为小写
	 *
	 * @param s
	 * @return
	 *
	 * @date 2014-12-23 下午1:57:04  (ZJZ)
	 */
	public static String translateSToFirstLowerCase(String s) {

		

		
		char[] chars = new char[1];
		chars[0] = s.charAt(0);
		String temp = new String(chars);
		if (chars[0] >= 'A' && chars[0] <= 'Z') {
			return (s.replaceFirst(temp, temp.toLowerCase()));
		} else {

			return s;

		}

	}
	
	/**
	 * 根据账户名称获取其id，没有的话就返回-1
	 *
	 * @param name
	 * @param dao
	 * @return
	 *
	 * @date 2015-5-7 下午1:16:24  (ZJZ)
	 */
	public static String isTheRightUserName(String name, IBaseDao dao) {
		
		String hql = String.format(
				" select fid from SysUser where fname = '%s'", name);
		
		List<String> fids = dao.QueryByHql(hql);
		
		if (fids.size() == 0) {
			
			return "-1";
			
		}
		
		return fids.get(0);
		
	}
	
	
	/**
	 * 根据名称，查询对应客户是否存在
	 *
	 * @param name
	 * @param dao
	 * @return
	 */
	public static String isTheRightCustomer(String name, IBaseDao dao) {
		
		String hql = String.format(
				" select fid from Customer where fname = '%s'", name);
		
		List<String> fids = dao.QueryByHql(hql);
		
		if (fids.size() == 0) {
			
			return "-1";
			
		}
		
		return fids.get(0);
		
	}
	
	/**
	 * 根据账户名称获取其id，没有的话就返回-1  用户保存手机号，账户，邮箱唯一
	 *
	 * @param name
	 * @param dao
	 * @return
	 *
	 */
	public static String isTheRightUserNamePhone(String name, IBaseDao dao) {
		
		String hql = String.format(
				" select fid from SysUser where fname = '%s' or ftel='%s'", name,name);
		
		List<String> fids = dao.QueryByHql(hql);
		
		if (fids.size() == 0) {
			
			return "-1";
			
		}
		
		return fids.get(0);
		
	}
	
	
public static String isTheRightUserNamePhoneEmail(String name, IBaseDao dao) {
		
		String hql = String.format(
				" select fid from SysUser where fname = '%s' or ftel='%s' or femail='%s'", name,name,name);
		List<String> fids = dao.QueryByHql(hql);
		
		if (fids.size() == 0) {
			
			return "-1";
			
		}
		
		return fids.get(0);
		
	}

public static boolean isTheRightUserNamePhoneEmailByfid(String name,String fid, IBaseDao dao) {
	
	String sql = String.format(
			" select fid from t_sys_user where (fname = '%s' or ftel='%s' or femail='%s') and fid<>'%s'", name,name,name,fid);
	return  dao.QueryExistsBySql(sql);
	
}
	


/**
 * 根据名称，fid,查询对应客户是否存在
 *
 * @param name
 * @param dao
 * @return
 */
public static boolean isTheRightCustomerByFid(String name,String fid, IBaseDao dao) {
	
	String sql = String.format(
			" select fid from t_bd_customer where fname = '%s' and fid<>'%s'", name,fid);
	return  dao.QueryExistsBySql(sql);
}

}
