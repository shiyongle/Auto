package Com.Dao.System;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import Com.Base.Dao.IBaseDao;
import Com.Entity.System.SysUser;

public interface IBaseSysDao extends IBaseDao {

	Object getProductsByCustProducts(String fidcls, int rv);

	Object getCustProductsByProducts(String fidcls, int rv);

	void delProducts(String fidcls);

	int getStocksOfCustProduct(String id);

	String getCurrentUserId(HttpServletRequest request);

	HashMap<String, Object> getProductAndStocksOfCustProduct(String id);

	String getStringValue(String tableName, String fid, String fieldName);

	HashMap<String, String> getMapData(String sql);

	String getCondition(String fidcls);

	String getStringValue(String sql, String fieldName);

	String getFidclsBySql(String... sql);

	String getCurrentCustomerid(HttpServletRequest request);

	String getCurrentCustomerid(String userid);

	String getCurrentSupplierid(String userid);

	SysUser getCurrentUser(HttpServletRequest request);

	boolean isAdministrator(String userid) throws IOException;

	List<HashMap<String, Object>> getSubList(
			List<HashMap<String, Object>> list, String key, Object value);

	String getIds(List<HashMap<String, Object>> list);

	String getIds(List<HashMap<String, Object>> list, String key);

	String getValueFromDefaultfilter(HttpServletRequest request, String field);

}
