package Com.Base.Util.mySimpleUtil;

import java.util.List;

import Com.Base.Dao.BaseDao;
import Com.Base.Dao.IBaseDao;
import Com.Base.Util.DJException;

/**
 * 根据外键值获取外键，外键相关操作类
 * 
 * 
 * @author ZJZ (447338871@qq.com, any Question)
 * @date 2015-1-3 上午10:12:20
 */
public class MyForeignKeyUntil {

	private static final String 过于模糊的_S_在系统中查找到多个 = "过于模糊的%s，在系统中查找到多个";
	private static final String 无效的_S_在系统中查找不到 = "无效的%s，在系统中查找不到";

	public MyForeignKeyUntil() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * 获取外键值，根据外键表里的某个属性
	 * 
	 * @param dao
	 * @param beanName
	 * @param rawFieldAndValue
	 *            ,1st is field, 2nd is value
	 * @param goalField
	 * @return
	 * 
	 * @date 2015-1-3 上午10:23:44 (ZJZ)
	 */
	public static List<?> selectForeignKeyByRawValue(IBaseDao dao, String beanName,
			String[] rawFieldAndValue, String goalField) {
		// TODO Auto-generated method stub

		String hql = " select %s from %s where %s like '%s' ";

		hql = String.format(hql, goalField, beanName, rawFieldAndValue[0], "%"
				+ rawFieldAndValue[1] + "%");

		List<?> fkeys = dao.QueryByHql(hql);

		return fkeys;

	}

	/**
	 * goalField = "fid";
	 *
	 * @param dao
	 * @param beanName
	 * @param rawFieldAndValue
	 * @return
	 *
	 * @date 2015-1-3 上午10:50:07  (ZJZ)
	 */
	public static List<?> selectForeignKeyByRawValue(IBaseDao dao, String beanName,
			String[] rawFieldAndValue) {
		// TODO Auto-generated method stub
		// 默认值
		String goalField = "fid"; 

		return selectForeignKeyByRawValue(dao, beanName, rawFieldAndValue,
				goalField);

	}
	
	/**
	 * 强制获取唯一的外键，并抛出错误
	 *
	 * @param dao
	 * @param beanName
	 * @param rawFieldAndValue
	 * @param tip，字段对应的中文名称
	 * @param goalField
	 * @return
	 *
	 * @date 2015-1-3 上午11:04:54  (ZJZ)
	 */
	public static Object gainSingleFid(IBaseDao dao, String beanName,
			String[] rawFieldAndValue, String tip,String goalField) {
		// TODO Auto-generated method stub
		
		List<?> fkeys =  selectForeignKeyByRawValue(dao, beanName, rawFieldAndValue, goalField);
		
		int size = fkeys.size();
		
		if (size == 0) {
			
			throw new DJException(String.format(无效的_S_在系统中查找不到, tip));
			
		} else if (size > 1) {
			
			throw new DJException(String.format(过于模糊的_S_在系统中查找到多个, tip));
			
		}
		
		return fkeys.get(0);
		
	}
	
	/**
	 * goalField = "fid";
	 *
	 * @param dao
	 * @param beanName
	 * @param rawFieldAndValue
	 * @param tip
	 * @return
	 *
	 * @date 2015-1-3 上午11:05:27  (ZJZ)
	 */
	public static Object gainSingleFid(BaseDao dao, String beanName,
			String[] rawFieldAndValue, String tip) {
		
		// 默认值
		String goalField = "fid";
		
		return gainSingleFid(dao, beanName, rawFieldAndValue, tip, goalField);
		
	}
}
