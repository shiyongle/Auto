package Com.Dao.System;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import Com.Base.Dao.BaseDao;
import Com.Base.Util.DJException;
import Com.Base.Util.JsonUtil;
import Com.Base.Util.params;
import Com.Entity.System.Productdef;
import Com.Entity.System.SysUser;
import Com.Entity.System.Useronline;

@Service("BaseSysDao")
public class BaseSysDao extends BaseDao implements IBaseSysDao {
	public static final int STRING = 0;
	public static final int STRINGARRAY = 1;
	public static final int STRINGLIST = 2;
	public static final int MAP = 3;
	public static final int ENTITY = 4;
	public static final int ENTITYLIST = 5;
	
	/**
	 * 查询登录用户ID
	 */
	@Override
	public String getCurrentUserId(HttpServletRequest request){
		return ((Useronline)request.getSession().getAttribute("Useronline")).getFuserid();
	}
	
	@Override
	public SysUser getCurrentUser(HttpServletRequest request){
		String userId = this.getCurrentUserId(request);
		return (SysUser) this.Query(SysUser.class, userId);
	}
	/**
	 * 根据客户产品查询产品
	 * @param fidcls
	 * @param returnValue
	 * @return
	 */
	@Override
	public Object getProductsByCustProducts(String fidcls,int returnValue){
		if(StringUtils.isEmpty(fidcls)){
			return null;
		}
		String condition = getCondition(fidcls);
		String sql1 = "select e1.fproductid ID from t_pdt_productrelationentry e"
				+ " left join t_pdt_productrelation e1 on e.fparentid=e1.fid"
				+ " where e.fcustproductid"+condition;
		String sql2 = "select e.fproductid ID from t_pdt_custrelationentry e"
				+ " left join t_pdt_custrelation e1 on e.fparentid=e1.fid"
				+ " where e1.fcustproductid"+condition;
		String fids = getFidclsBySql(sql1,sql2);
		if("".equals(fids)){
			return null;
		}
		if(returnValue==STRING){
			return fids;
		}
		if(returnValue==ENTITY){
			if(fidcls.indexOf(",")!=-1){
				throw new DJException("返回列表，不能转换为单个实体");
			}
			return this.Query(Productdef.class, fidcls);
		}
		return null;
	}
	/**
	 * 根据产品查找客户产品
	 * @param fidcls
	 * @param returnValue
	 * @return
	 */
	@Override
	public Object getCustProductsByProducts(String fidcls,int returnValue){
		if(StringUtils.isEmpty(fidcls)){
			return null;
		}
		String condition=getCondition(fidcls);
		String sql1 = "select e.fcustproductid ID from t_pdt_productrelationentry e"
				+ " left join t_pdt_productrelation e1 on e.fparentid=e1.fid"
				+ " where e1.fproductid"+condition;
		String sql2 = "select e1.fcustproductid ID from t_pdt_custrelationentry e"
				+ " left join t_pdt_custrelation e1 on e.fparentid=e1.fid"
				+ " where e.fproductid"+condition;
		fidcls = getFidclsBySql(sql1,sql2);
		if(returnValue==STRING){
			return fidcls;
		}
		if(returnValue==ENTITYLIST){
			
		}
		return null;
	}
	/**
	 * 删除产品
	 */
	@Override
	public void delProducts(String fidcls){
		String condition = getCondition(fidcls);
		String condition2;
		String sql = "select fid ID from t_pdt_productrelation where fproductid"+condition;
		String fids = getFidclsBySql(sql);
		if(!"".equals(fids)){
			condition2 = getCondition(fids);
			sql = "delete from t_pdt_productrelation where fid"+condition2;
			this.ExecBySql(sql);
			sql = "delete from t_pdt_productrelationentry where fparentid"+condition2;
			this.ExecBySql(sql);
		}
		sql = "select fparentid ID from t_pdt_custrelationentry where fproductid"+condition;
		fids = getFidclsBySql(sql);
		if(!"".equals(fids)){
			condition2 = getCondition(fids);
			sql = "delete from t_pdt_custrelation where fid"+condition2;
			this.ExecBySql(sql);
			sql = "delete from t_pdt_custrelationentry where fparentid"+condition2;
			this.ExecBySql(sql);
		}
		sql = "delete from t_pdt_productdef where fid"+condition;
		this.ExecBySql(sql);
	}
	/**
	 * 根据客户产品id查询库存量
	 * @param id
	 * @return
	 */
	@Override
	public int getStocksOfCustProduct(String id){
		HashMap<String, Object> map = getProductAndStocksOfCustProduct(id);
		if(map == null){
			return -1;
		}else{
			return (Integer)map.get("famount");
		}
	}
	/**
	 *  根据客户产品id查询对应的产品和库存量
	 *  无特性产品存产品id和数量
	 *  有特性产品存特性id和数量
	 */
	@Override
	public HashMap<String, Object> getProductAndStocksOfCustProduct(String id){
		String sql = "SELECT e1.fid FROM t_bd_custproduct e"
				+" INNER JOIN t_ord_schemedesignentry e1 ON e.fcharacterid = e1.fid AND e1.fallot = 0 AND e.fid = '"+id+"'";
		List<HashMap<String, Object>> list = this.QueryBySql(sql);
		HashMap<String, Object> map;
		int amount = 0;
		Object val;
		boolean bool = true;
		if(list.size()==0){		//无特性产品
			sql = "select e.fproductid,e.famount radio from t_pdt_custrelationentry e"
					+ " left join t_pdt_custrelation e1 on e.fparentid = e1.fid"
					+" INNER JOIN t_pdt_productdef f ON f.fid=e.fproductid AND IFNULL(f.fishistory, 0) = 0 AND IFNULL(f.feffect, 0) = 1 "
					+ " where e1.fcustproductid ='"+id+"'";
			list = this.QueryBySql(sql);
			if(list.size()<1){
				sql = "select e1.fproductid ,e.famount radio from t_pdt_productrelationentry e"
						+ " left join t_pdt_productrelation e1 on e.fparentid=e1.fid"
						+" INNER JOIN t_pdt_productdef f ON f.fid=e1.fproductid AND IFNULL(f.fishistory, 0) = 0 AND IFNULL(f.feffect, 0) = 1 "
						+ " where e.fcustproductid='"+id+"'";
				list = this.QueryBySql(sql);
				if(list.size()==0){
					return null;
				}
				bool = false;
			}
			if(list.size()>1){
				return null;
			}
			map = list.get(0);
			int radio = Integer.valueOf(map.get("radio").toString());
			sql = "select sum(fbalanceqty) amount from t_inv_storebalance where fproductid='"+map.get("fproductid")+"'";
			list = this.QueryBySql(sql);
			val = ((HashMap<String, Object>)this.QueryBySql(sql).get(0)).get("amount");
			if(val!=null){
				amount = Integer.valueOf(val.toString());
			}
			map.put("famount", bool==true?amount/radio:amount*radio);
		}else{
			String fcharacterid = list.get(0).get("fid").toString();
			sql = "select sum(fbalanceqty) amount from t_inv_storebalance where ftraitid='"+fcharacterid+"'";
			list = this.QueryBySql(sql);
			map = list.get(0);
			val = map.get("amount");
			if(val!=null){
				amount = Integer.valueOf(val.toString());
			}
			map.put("ftraitid",fcharacterid);
			map.put("famount", amount);
		}
		return map;
	}
	/**
	 * 根据ID获取字段
	 */
	@Override
	public String getStringValue(String tableName,String fid,String fieldName){
		String sql = "select "+fieldName+" from "+tableName+" where fid=:fid";
		params p = new params();
		p.put("fid", fid);
		List<HashMap<String, Object>> list = this.QueryBySql(sql, p);
		if(list.size()==0){
			return null;
		}else{
			return String.valueOf(list.get(0).get(fieldName));
		}
	}
	/**
	 * 查询字段值
	 * @param sql
	 * @param fieldName
	 * @return
	 */
	@Override
	public String getStringValue(String sql,String fieldName){
		HashMap map = getMapData(sql);
		if(map==null || map.get(fieldName)==null){
			return null;
		}
		return map.get(fieldName)+ "";
	}
	/**
	 * 查询一条记录
	 * 在确定返回一条记录时使用
	 * @param sql
	 * @return
	 */
	@Override
	public HashMap<String, String> getMapData(String sql){
		List<HashMap<String, String>> list = this.QueryBySql(sql);
		if(list.size()==0){
			return null;
		}
		return list.get(0);
	}
	@Override
	public String getCondition(String fidcls){
		if(StringUtils.isEmpty(fidcls)){
			throw new DJException("ID不存在！");
		}
		String condition;
		if(fidcls.split(",").length==1){
			condition = " = '"+fidcls+"' ";
		}else{
			condition = " in ('"+fidcls.replace(",", "','")+"') ";
		}
		return condition;
	}
	@Override
	public String getFidclsBySql(String... sql){
		Set<String> set = new HashSet<>();
		List<HashMap<String, Object>> list;
		for(String s : sql){
			list = this.QueryBySql(s);
			if(list.size()!=0){
				for(HashMap<String, Object> map : list){
					set.add(map.get("ID").toString());
				}
			}
		}
		if(set.size()==0){
			return "";
		}
		StringBuilder sb = new StringBuilder();
		for(String s : set){
			sb.append(s+",");
		}
		String fidcls = sb.toString();
		return fidcls.substring(0, fidcls.length()-1);
	}
	/**
	 * 查询当前登录用户关联的客户
	 */
	@Override
	public String getCurrentCustomerid(HttpServletRequest request){
		String userid = ((Useronline) request.getSession().getAttribute(
				"Useronline")).getFuserid();
		return getCurrentCustomerid(userid);
	}
	/**
	 * 查询当前登录用户关联的客户
	 */
	@Override
	public String getCurrentCustomerid(String userid){
		String sql="select fcustomerid from ( select fcustomerid  from t_bd_usercustomer where fuserid='"+userid+"'"+
				" union select c.fcustomerid from  t_sys_userrole r "+
				" left join t_bd_rolecustomer c on r.froleid=c.froleid where r.fuserid='"+userid+"' )  s where fcustomerid is not null " ;
		List<HashMap<String, Object>> custList=this.QueryBySql(sql);
		String customerid = null;
		if(custList.size()==1)
		{
			HashMap<String,Object> map=custList.get(0);
			customerid =map.get("fcustomerid").toString();
		}else{
			throw new DJException("当前帐号存在多个客户,不能执行操作！");
		}
		return customerid;
	}
	
	/**
	 * 查询当前登录用户关联的制造商
	 */
	@Override
	public String getCurrentSupplierid(String userid){
		String sql="select fsupplierid from ( select fsupplierid  from t_bd_usersupplier where fuserid='"+userid+"'"+
				" union select c.fsupplierid from  t_sys_userrole r "+
				" left join t_bd_rolesupplier c on r.froleid=c.froleid where r.fuserid='"+userid+"' )  s where fsupplierid is not null " ;
		List<HashMap<String, Object>> suppList=this.QueryBySql(sql);
		String supplierid = null;
		if(suppList.size()==1)
		{
			HashMap<String,Object> map=suppList.get(0);
			supplierid =map.get("fsupplierid").toString();
		}else{
			throw new DJException("当前帐号存在多个制造商,不能执行操作！");
		}
		return supplierid;
	}
	/**
	 * 是否是查看全部客户
	 * @param userid
	 * @return
	 * @throws IOException
	 */
	@Override
	public boolean isAdministrator(String userid) throws IOException {
		SysUser sysUser = (SysUser) this.Query(SysUser.class, userid);
		if(sysUser.getFisfilter()==1){
			return true;
		}
		return false;
	}
	/**
	 * 获取符合条件的子集合
	 * @param list
	 * @param key	用于条件判断的键
	 * @param value	 用于条件判断的值
	 * @return
	 */
	@Override
	public List<HashMap<String, Object>> getSubList(List<HashMap<String, Object>> list,String key,Object value){
		List<HashMap<String, Object>> resultList = new ArrayList<>();
		for(HashMap<String, Object> map: list){
			if((map.get(key)+"").equals(value+"")){
				resultList.add(map);
			}
		}
		return resultList;
	}
	/**
	 * 提取list集合中的map中的所有fid,以逗号相连
	 */
	@Override
	public String getIds(List<HashMap<String, Object>> list){
		return getIds(list,"fid");
	}
	/**
	 * 提取list集合中的map中的所有键值,以逗号相连
	 */
	@Override
	public String getIds(List<HashMap<String, Object>> list,String key){
		StringBuilder sb = new StringBuilder();
		for(HashMap<String, Object> map: list){
			sb.append(map.get(key)+",");
		}
		String fidcls = sb.toString();
		int length = fidcls.length();
		return length!=0 ? fidcls.substring(0,length-1) : null;
	}
	/**
	 * 从前端传参的Defaultfilter中提取值
	 * @param request
	 * @param field
	 * @return
	 */
	@Override
	public String getValueFromDefaultfilter(HttpServletRequest request,String field){
		String defaultFilter = request.getParameter("Defaultfilter");
		if(defaultFilter != null){
			JSONArray jsonArray = JSONArray.fromObject(defaultFilter);
			JSONObject jsonObj;
			for (int i = jsonArray.size() - 1; i >= 0; i--) {
				jsonObj = jsonArray.getJSONObject(i);
				if(field.equals(jsonObj.getString("myfilterfield"))){
					return jsonObj.getString("value");
				}
			}
		}
		return null;
	}
}