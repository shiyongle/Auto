package Com.Base.Dao;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.metadata.ClassMetadata;
import org.hibernate.transform.Transformers;
import org.hibernate.util.StringHelper;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.web.bind.annotation.RequestMapping;

import Com.Base.Util.DJException;
import Com.Base.Util.ListResult;
import Com.Base.Util.ServerContext;
import Com.Base.Util.SpringContextUtils;
import Com.Base.Util.params;
import Com.Entity.System.SysUser;
import Com.Entity.System.Useronline;

public class BaseDao extends HibernateDaoSupport implements IBaseDao {
	public BaseDao() {
		this.setSessionFactory((SessionFactory) SpringContextUtils
				.getBean("sessionFactory"));
//		try {
//			ServerContext.getOracleHelper().GetBaseConn();
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (DJException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		try {
//			if (oraclehelper==null)
//			{
//				oraclehelper=new OracleHelper();
//			}
//		} catch (DJException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
	}

	
	
	@Override
	public void Update(Object o) {
		// TODO Auto-generated method stub
		this.getSessionFactory().getCurrentSession().update(o);
		this.getSessionFactory().getCurrentSession().flush();
	}

	@Override
	public void Delete(Object o) {
		// TODO Auto-generated method stub
		this.getSessionFactory().getCurrentSession().delete(o);
		this.getSessionFactory().getCurrentSession().flush();
	}

	@Override
	public List QueryBySql(String sql) {
		// TODO Auto-generated method stub
		Query q = this.getSessionFactory().getCurrentSession().createSQLQuery(sql).setResultTransformer(
				Transformers.ALIAS_TO_ENTITY_MAP);
		List<HashMap<String, Object>> sList = q.list();
		return sList;
	}
	
	@Override
	public List<HashMap<String, Object>> QueryBySql(String sql,Object... params){
		Query sqlQuery = this.getSessionFactory().getCurrentSession().createSQLQuery(sql).setResultTransformer(
				Transformers.ALIAS_TO_ENTITY_MAP);
		int len = params.length;
		Object param;
		for(int i=0;i<len;i++){
			param = params[i];
			if(param instanceof String){
				sqlQuery.setString(i, (String)param);
			}else if(param instanceof BigDecimal){
				sqlQuery.setBigDecimal(i, (BigDecimal) param);
			}else if(param instanceof Integer){
				sqlQuery.setInteger(i, (int) param);
			}else if(param instanceof Double){
				sqlQuery.setDouble(i, (double) param);
			}
		}
		return sqlQuery.list();
	}

	@Override
	public String CreateUUid() {
		Query q = this.getSessionFactory().getCurrentSession().createSQLQuery(
				"SELECT uuid() fid FROM t_sys_mainmenuitem  limit 1");
		List<String> sList = q.list();
		return sList.get(0).toString();
		// TODO Auto-generated method stub
	}

	@Override
	public void saveOrUpdate(Object o) {
		// TODO Auto-generated method stub
		this.getSessionFactory().getCurrentSession().saveOrUpdate(o);
		this.getSessionFactory().getCurrentSession().flush();
	}
	
	@Override
	public void save(Object o) {
		// TODO Auto-generated method stub
		this.getSessionFactory().getCurrentSession().save(o);
		this.getSessionFactory().getCurrentSession().flush();
	}
	

	@Override
	public void ExecByHql(String hql) {
		Query q = this.getSessionFactory().getCurrentSession().createQuery(hql);
		q.executeUpdate();
		this.getSessionFactory().getCurrentSession().flush();
	}

	@Override
	public int ExecBySql(String sql) {
		Query q = this.getSessionFactory().getCurrentSession().createSQLQuery(sql);
		return q.executeUpdate();
	}

	@Override
	public String QueryCountBySql(String sql) {
		// TODO Auto-generated method stub
		String countsql = sql.toUpperCase();
		int begin = countsql.indexOf("FROM");
		int end = countsql.indexOf("LIMIT");
		if (end == -1) {
			countsql = countsql.substring(begin);
		} else {
			countsql = countsql.substring(begin, end);
		}
		countsql = "select count(1) " + countsql;
		Query q = this.getSessionFactory().getCurrentSession().createSQLQuery(countsql);
		List<java.math.BigInteger> sList = q.list();
		return sList.get(0).intValue() + "";
	}

	@Override
	public params QueryCheckSessionid(HttpServletRequest request) {
		// TODO Auto-generated method stub
		params result = new params();
		String sessionid = request.getSession().getId();
		
		HashMap<String, Useronline>  userOnlineMap = ServerContext.getUseronline();
		Useronline useronline = null;
		if(userOnlineMap.containsKey(sessionid)){
			useronline = userOnlineMap.get(sessionid);
		}
		/*Query q = this.getSessionFactory().getCurrentSession().createQuery(
				" FROM Useronline where fsessionid = :fsessionid");
		q.setString("fsessionid", sessionid);
		List<Useronline> slist = q.list();
		
		if (slist.size()>0 && slist.get(0).getFusername().equals("EAS"))
		{
			 result.setBoolean("success", true);
			 return result;
		}
		*/
		
		if (useronline != null && useronline.getFusername().equals("EAS"))
		{
			 result.setBoolean("success", true);
			 return result;
		}
		
//		if (slist.size() > 0) {
		if (useronline != null) {
			// result.setBoolean("success", true);
//			String userid = slist.get(0).getFuserid();
			String userid = useronline.getFuserid();
//			String url = request.getRequestURI().toString().replaceAll("/vmi/", "");
			String url = request.getRequestURI().toString().substring(1).replaceAll("vmi/", "");
			List<HashMap<String, Object>> spermission;
			Query q = this.getSessionFactory().getCurrentSession()
					.createSQLQuery(
							"select fid from t_sys_button where fbuttonaction like :buttonaction");
			q.setString("buttonaction", "%" + url + "%");
			spermission = q.list();
			if (spermission.size() > 0) {
				String checksql = "select t_sys_userpermission.fid from t_sys_userpermission inner join t_sys_button ";
				checksql = checksql
						+ " on t_sys_userpermission.fresourcesid=t_sys_button.fid ";
				checksql = checksql
						+ " where  (t_sys_userpermission.fuserid=:userid or t_sys_userpermission.fuserid in (select froleid from t_sys_userrole where fuserid=:userid)) ";
				checksql = checksql
						+ " and t_sys_button.fbuttonaction like :buttonaction ";
				q = this.getSessionFactory().getCurrentSession().createSQLQuery(checksql);

				q.setString("buttonaction", "%" + url + "%");
				q.setString("userid", userid);

				spermission = q.list();
				if (spermission.size() > 0
						|| userid
								.equals("0f20f5bf-a80b-11e2-b222-60a44c5bbef3")) {
					result.setBoolean("success", true);
				} else {
					result.setBoolean("success", false);
					result.put("msg", "您没有此权限!");
				}
			} else {
				result.setBoolean("success", true);
			}
		} else {
			result.setBoolean("success", false);
			result.put("msg", "超时,请重新登录!");
		}
		return result;
	}

	// protected SessionFactory sessionFactory=null;
	// protected Session session=null;
	// public BaseDao(){
	// sessionFactory=new Configuration().configure().buildSessionFactory();
	// session =sessionFactory.openSession();
	// }
	// public String CreateUUid(){
	// List<Object[]> sList=query("select uuid() ");
	// for (Object[] o : sList) {
	// return o[0].toString();
	// }
	// return "";
	//
	// }
	//
	// public ArrayList query(String sql){
	//
	// Transaction tx=session.beginTransaction();
	// Query q=null;
	// try
	// {
	//
	// q=session.createSQLQuery(sql);
	// //q=session.createSQLQuery(sql).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
	// return (ArrayList) q.list();
	// }
	// catch(Exception ex)
	// {
	// tx.rollback();
	// ex.printStackTrace();
	// }
	// finally
	// {
	// if (session!=null)
	// {
	// session.close();
	// }
	// }
	// return null;
	// }
	//
	/*** 使用HQL语句操作*/
	public Object QueryByHqlObjct(String hql,params p) {
				Query query =  this.getSessionFactory().getCurrentSession().createQuery(hql);//执行查询
				if(p!=null){
				Set<String> set = p.keySet();
				Iterator<String> it = set.iterator();
				while (it.hasNext()) {
					String key = it.next();
					query.setString(key.toUpperCase(), p.get(key).toString());
				}
				}
				return query.uniqueResult();
	}
	
	@Override
	public List QueryByHql(String hql) {
		// TODO Auto-generated method stub
		return this.getHibernateTemplate().find(hql);
	}

	@Override
	public void Delete(String table, String fid) {
		// TODO Auto-generated method stub
		String sql = "delete from " + table + " where fid ='" + fid + "'";
		this.ExecBySql(sql);
	}

	@Override
	public int ExecBySql(String sql, params p) {
		// TODO Auto-generated method stub
		Query q = this.getSessionFactory().getCurrentSession().createSQLQuery(sql);
		Set<String> set = p.keySet();
		Iterator<String> it = set.iterator();
		while (it.hasNext()) {
			String key = it.next();
			q.setString(key, p.get(key).toString());
		}
		return q.executeUpdate();
	}

	@Override
	public int QueryCountBySql(String sql, params p) {
		String countsql = sql.toUpperCase();
		int begin = countsql.indexOf(" FROM ");
		int end = countsql.lastIndexOf(" LIMIT ");
		if (end == -1) {
			countsql = countsql.substring(begin);
		} else {
			countsql = countsql.substring(begin, end);
		}
		countsql = "select count(1) " + countsql;
		Query q = this.getSessionFactory().getCurrentSession().createSQLQuery(countsql);
		Set<String> set = p.keySet();
		Iterator<String> it = set.iterator();
		while (it.hasNext()) {
			String key = it.next();
			q.setString(key.toUpperCase(), p.get(key).toString());
		}
		List<java.math.BigInteger> sList = q.list();
		return sList.get(0).intValue();
	}

	@Override
	public ListResult QueryFilterList(String sql, HttpServletRequest request)
			throws DJException {
		ListResult lresult = new ListResult();
		String start = request.getParameter("start")==null?"0":request.getParameter("start");
		String limit = request.getParameter("limit")==null?"50":request.getParameter("limit");
		String sortsql="";
		String groupsql = "";
		if(request.getAttribute("nolimit")!=null)
		{
			start="0";limit="999999999";//原 start="",limit=""
		}
		sortsql =request.getAttribute("djsort")==null?"":request.getAttribute("djsort").toString();
		groupsql =request.getAttribute("djgroup")==null?"":request.getAttribute("djgroup").toString();
		if(sortsql.length()<=0)
		{
			JSONArray sortjsonarray = JSONArray.fromObject(request.getParameter("sort")==null?"[]":request.getParameter("sort").toString());
			for(int i=0;i<sortjsonarray.size();i++)
			{
				JSONObject o = sortjsonarray.getJSONObject(i);
				if(!StringHelper.isEmpty(sortsql))
				{
					sortsql=sortsql+",";
				}
				else
				{
					sortsql=o.getString("property")+" "+o.getString("direction");
				}
					
			}
		}
		params p = new params();
		if (!sql.contains("where")) {
			sql = sql + " WHERE 1=1 ";
		}
		String filter = request.getParameter("filter");
		if (filter != null) {
			JSONArray j = JSONArray.fromObject(filter);
			filter = "";
			for (int i = 0; i < j.size(); i++) {
				if (!filter.isEmpty()) {
					filter = filter + " and ";
				}
				JSONObject o = j.getJSONObject(i);
				if (!o.containsKey("field")) {
					break;
				}

				String fielddd = o.getString("field");// 传递的参数
				if (fielddd.indexOf("_") != -1) {
					fielddd = fielddd.replace("_", ".");
				}
				if (("string").equals(o.getString("type"))) {
					filter = filter + " " + fielddd + " like :"
							+ o.getString("field");
					p.put(o.getString("field"), "%" + o.getString("value")
							+ "%");
				} else if (("datetime").equals(o.getString("type"))) {

					String fieled = o.getString("field");
					if ("<".equals(o.getString("comparison"))) {
						fieled += "endtime";
					} else if (">".equals(o.getString("comparison"))) {
						fieled += "starttime";
					} else {
						fieled += "equalstime";
					}
					filter = filter + " " + fielddd + o.getString("comparison")
							+ ":" + fieled;
					p.put(fieled, o.getString("value"));
				} else if (("date").equals(o.getString("type"))) {
					String fieled = o.getString("field");
					if ("<".equals(o.getString("comparison"))) {
						fieled += "endtime";
					} else if (">".equals(o.getString("comparison"))) {
						fieled += "starttime";
					} else {
						fieled += "equalstime";
					}
					filter = filter + " " + fielddd + o.getString("comparison")
							+ ":" + fieled;
					p.put(fieled, o.getString("value"));
				} else if (("boolean").equals(o.getString("type"))) {

					filter = filter + "  " + fielddd + "=:"
							+ o.getString("field");
					p.put(o.getString("field"),
							o.getString("value").equals("true") ? 1 : 0);
				} else if (("list").equals(o.getString("type"))) {
					String field = o.getString("field");
					filter = filter + " " + fielddd + " in (";
					String[] values = o.getString("value").split(",");
					for (int z = 0; z < values.length; z++) {
						if (z != 0) {
							filter += ",";
						}
						filter += ":" + field + z;
						p.put(field + z, values[z]);
					}
					filter += ")";

				} else if (("numeric").equals(o.getString("type"))) {
					String fieled = o.getString("field");
					if ("<".equals(o.getString("comparison"))) {
						fieled += "less";
					} else if (">".equals(o.getString("comparison"))) {
						fieled += "greater";
					} else {
						fieled += "equals";
					}
					filter = filter + " " + fielddd + o.getString("comparison")
							+ ":" + fieled;
					p.put(fieled, o.getString("value"));
				}

			}
			// JSONArray a = JSONArray.fromObject(filter);
		}
		String Defaultfilter = request.getParameter("Defaultfilter");
		String Defaultmaskstring = request.getParameter("Defaultmaskstring");
		String Cusfilter = request.getParameter("Cusfilter");
		String Maskstring = request.getParameter("Maskstring");
		String Merge = request.getParameter("Merge");
		if (Defaultfilter != null && Defaultfilter != ""
				&& Defaultmaskstring != null && Defaultmaskstring != "") {
			JSONArray j = JSONArray.fromObject(Defaultfilter);
			for (int i = j.size() - 1; i >= 0; i--) {
				JSONObject o = j.getJSONObject(i);
				Defaultfilter = "";
				if (!o.has("myfilterfield")
						|| o.getString("myfilterfield").isEmpty()
						|| !o.has("CompareType")
						|| o.getString("CompareType").isEmpty()
						|| !o.has("value")) {
					throw new DJException("过滤条件设置有误");
				}
				if (o.getString("CompareType").contains("like")) {
					Defaultfilter = o.getString("myfilterfield") + " "
							+ o.getString("CompareType") + " :D"+i
							+ o.getString("myfilterfield");
					Defaultmaskstring = Defaultmaskstring.replace("#" + i,
							Defaultfilter);
					p.put("D"+i+o.getString("myfilterfield"),
							"%" + o.getString("value") + "%");
				} else if (o.getString("CompareType").contains("null")) {
					Defaultfilter = o.getString("myfilterfield") + " "
							+ o.getString("CompareType") + " ";
					Defaultmaskstring = Defaultmaskstring.replace("#" + i,
							Defaultfilter);
				} else {
					Defaultfilter = o.getString("myfilterfield") + " "
							+ o.getString("CompareType") + " :D"+i
							+ o.getString("myfilterfield");
					Defaultmaskstring = Defaultmaskstring.replace("#" + i,
							Defaultfilter);
					p.put("D"+i+o.getString("myfilterfield"), o.getString("value"));
				}
			}
			// JSONArray a = JSONArray.fromObject(filter);
		}
		if (Cusfilter != null && Cusfilter != "" && Maskstring != null
				&& Maskstring != "") {
			JSONArray j = JSONArray.fromObject(Cusfilter);
			for (int i = j.size() - 1; i >= 0; i--) {
				JSONObject o = j.getJSONObject(i);
				Cusfilter = "";
				if (!o.has("myfilterfield")
						|| o.getString("myfilterfield").isEmpty()
						|| !o.has("CompareType")
						|| o.getString("CompareType").isEmpty()
						|| !o.has("value")) {
					throw new DJException("过滤条件设置有误");
				}
//				Defaultfilter = o.getString("myfilterfield") + " "
//						+ o.getString("CompareType") + " :"
//						+ o.getString("myfilterfield");
//				Maskstring = Maskstring.replace(("#" + i), Defaultfilter);
				if (o.getString("CompareType").contains("like")) {
					Cusfilter = o.getString("myfilterfield") + " "
							+ o.getString("CompareType") + " :C"+i
							+ o.getString("myfilterfield");
					Maskstring = Maskstring.replace("#" + i,
							Cusfilter);
					p.put("C"+i+o.getString("myfilterfield"),
							"%" + o.getString("value") + "%");
				} else if (o.getString("CompareType").contains("null")) {
					Cusfilter = o.getString("myfilterfield") + " "
							+ o.getString("CompareType") + " ";
					Maskstring = Maskstring.replace("#" + i,
							Defaultfilter);
				} else {
					Cusfilter = o.getString("myfilterfield") + " "
							+ o.getString("CompareType") + " :C"+i
							+ o.getString("myfilterfield");
					Maskstring = Maskstring.replace("#" + i,
							Defaultfilter);
					p.put("C"+i+o.getString("myfilterfield"), o.getString("value"));
				}
			}
			// JSONArray a = JSONArray.fromObject(filter);
		}

		if (filter != null && !filter.isEmpty()) {
			sql = sql + " and  (" + filter + ") ";
		}
		if (Defaultmaskstring != null && !Defaultmaskstring.isEmpty()) {
			sql = sql + " and  (" + Defaultmaskstring + ") ";
		}
		if (Maskstring != null && !Maskstring.isEmpty()) {
			if (Merge == null || Merge.isEmpty()) {
				sql = sql + " and  (" + Maskstring + ") ";
			} else {
				sql = sql + " " + Merge + "  (" + Maskstring + ") ";
			}
		}
//		if(request.getAttribute("nocount")==null){
//			//lresult.setTotal(QueryCountBySql(sql, p) + "");
//			lresult.setTotal("5000");
//		}
		if(!groupsql.isEmpty())
		{
			sql=sql+" group by "+groupsql;
		}
		if(!sortsql.isEmpty())
		{
			sql=sql+" order by "+sortsql;
		}
		if (!start.isEmpty() && !limit.isEmpty()) {
			sql = sql + " limit " + start + "," + (Integer.parseInt(limit)+1) ;//后一页还有没有数据  BY  CC
		}
		List<HashMap<String, Object>> sList=QueryBySql(sql, p);

//		if(request.getAttribute("nocount")==null){
			//lresult.setTotal(QueryCountBySql(sql, p) + "");
		if (!start.isEmpty() && !limit.isEmpty()) {// 0806 nolimit 导出报错问题
		if(sList.size()==0)
			{
				lresult.setTotal(start);
			}
			else if(sList.size()==Integer.parseInt(limit))//如果数据和每页数量一样，表示后一页没有数据了。总数等于开始行+每页数量   BY  CC
			{
				lresult.setTotal((Integer.parseInt(start)+Integer.parseInt(limit))+"");
			}
			else if(sList.size()<Integer.parseInt(limit))//如果
			{
				lresult.setTotal((Integer.parseInt(start)+sList.size())+"");
			}
			else
			{
				lresult.setTotal("5000");
			}
//		}
		if(sList.size()>Integer.parseInt(limit))
		{
			sList.remove(Integer.parseInt(limit));//如果数据大与每页行数，说明后一页还有数据，剔除最后行数据    BY CC
		}	
		}
		lresult.setData(sList);
		
		return lresult;
	}

	@Override
	public List QueryBySql(String sql, params p) {
		Query q = this.getSessionFactory().getCurrentSession().createSQLQuery(sql).setResultTransformer(
				Transformers.ALIAS_TO_ENTITY_MAP);
		Set<String> set = p.keySet();
		Iterator<String> it = set.iterator();
		while (it.hasNext()) {
			String key = it.next();
			q.setString(key, p.get(key).toString());
		}
		List<HashMap<String, Object>> sList = q.list();
		return sList;
	}

	/// cid:查询客户的字段，不过滤则null;sid查询供应商的字段，不过滤为null
		@Override
		public String QueryFilterByUser(HttpServletRequest request,String cid,String sid)
		{
			String userid = ((Useronline)request.getSession().getAttribute("Useronline")).getFuserid();
			
			SysUser userinfo=this.getHibernateTemplate().get(
						SysUser.class, userid);
			String strsql="";
			String customerid="";
			String supplierid="";
//			String isFitler=request.getParameter("isFitler");//是否过滤
//			if(isFitler!=null)
//			{
//				return "";
//			}
			if(userinfo.getFisfilter()==1) return "";
			if(cid!=null && !cid.equals(""))
			{
				String sql="select fcustomerid from ( select fcustomerid  from t_bd_usercustomer where fuserid='"+userid+"'"+
						" union select c.fcustomerid from  t_sys_userrole r "+
						" left join t_bd_rolecustomer c on r.froleid=c.froleid where r.fuserid='"+userid+"' )  s where fcustomerid is not null " ;
				List list=this.QueryBySql(sql);
				customerid=GetResultSet(list,"fcustomerid");
				strsql= cid+" in ("+customerid+")  and "+cid+" <>''";
			}
			if( sid!=null && !sid.equals(""))
			{
				String sql="select fsupplierid from ( select fsupplierid  from t_bd_usersupplier where fuserid='"+userid+"'"+
						" union select c.fsupplierid from  t_sys_userrole r "+
						" left join t_bd_rolesupplier c on r.froleid=c.froleid where r.fuserid='"+userid+"' ) s where fsupplierid is not null " ;
				List list=this.QueryBySql(sql);
				supplierid=GetResultSet(list,"fsupplierid");
				if(cid==null)
				{
					strsql=sid+" in("+supplierid+") and "+sid+" <>''";
				}else
				{
					strsql+=" or "+sid+" in("+supplierid+") and "+sid+" <>''";
				}
			}
			if(strsql.equals("")){
				return "";
			}
			return " and ("+strsql+")";
		}
		private String GetResultSet(List list,String field)
		{
			String ids="";
			if(list.size()>0)
			{
				for(int i=0;i<list.size();i++)
				{
					HashMap map=(HashMap)list.get(i);
					ids+="'"+map.get(field)+"'";
					if(i<list.size()-1)
					{
						ids+=",";
					}
				}
			}
			else
			{
				ids="''";
			}
			return ids;
		}
		/*
		 * table 指查询表,startstr 表示编码前缀（C1308240001,startstr为“C”），length流水号位数.最长默认流水号为6位
		 */
		@Override
		public String getFnumber(String table,String startstr,int length)
		{
			String fnumber;
			Date nowdate=new Date();
			 SimpleDateFormat df=new SimpleDateFormat("yyMMdd");
			String nowString=startstr+df.format(nowdate);
			String sql="select fnumber from "+table+"  where fnumber like :datenow" +
					" order by fnumber desc limit 1";
			params p = new params();
			p.put("datenow",nowString+"%" );
			List<HashMap<String, Object>> sList= this.QueryBySql(sql, p);
			if(sList.size()>0)
			{
				if(sList.get(0).get("fnumber")==null)
				{
					fnumber=nowString+"000000".substring(0,length)+"1";
				}
				else
				{
					String value=(String)sList.get(0).get("fnumber");
					int num=Integer.parseInt(value.substring(value.length()-length, value.length()))+1;
					fnumber=nowString+"000000".substring(0,length-(num+"").length())+num;
				}
//				String value=(String)sList.get(0).get("fnumber");
//				int num=0;
//				try{
//					num=Integer.valueOf(value);
//					num=Integer.valueOf(value.substring(8))+1;
//				}catch(Exception e)
//				{
//					num=Integer.valueOf(value.substring(9))+1;
//				}
//				String value2=num+"";
//				fnumber=nowString+"000000".substring(0,length-value2.length())+num;
			}
			else
			{
				fnumber=nowString+"000000".substring(0,length-1)+"1";
			}
			return fnumber;
		}



		@Override
		public Map<String, ClassMetadata> getAllClassMetadata() {
			// TODO Auto-generated method stub
			return this.getSessionFactory().getAllClassMetadata();
		}



		@Override
		public boolean QueryExistsBySql(String sql) {
			// TODO Auto-generated method stub
			if(sql.indexOf("limit")==-1){
				sql += " limit 0,1";
			}
			List<HashMap<String, Object>> sList = QueryBySql(sql);
			return sList.size()>0;
		}



		@Override
		public Object Query(String classname, String fid) {
			// TODO Auto-generated method stub
			return this.getHibernateTemplate().get(classname, fid);
		}
		
		@Override
		public Object Query(Class classname, String fid) {
			// TODO Auto-generated method stub
			return this.getHibernateTemplate().get(classname, fid);
		}
		/**
		 * 
		 * @param request
		 * @param field 查询的字段

		 * @return 有值返回值，无值就返回 （ 1=1）
		 */
		public String QueryFilterByUserofuser(HttpServletRequest request,String field,String fmark)
		{
			
			String userid = ((Useronline)request.getSession().getAttribute("Useronline")).getFuserid();
			SysUser userinfo=(SysUser) this.getHibernateTemplate().get(
						SysUser.class, userid);
			String strsql="";
			String sfmark="";
			if(StringUtils.isEmpty(fmark))
			{
				sfmark=" and ";
			}else
			{
				sfmark=fmark;
			}
			if(userinfo.getFisreadonly()==0) return "";
			if(field!=null && !field.equals(""))
			{
				String sqls = "select quote(fuserid) userid from t_bd_usertouser where fsuperuserid='"+userid+"'";
				List<HashMap<String, Object>> demandList = QueryBySql(sqls);
				if(demandList.size()!=0){//关联用户过滤
					StringBuilder userids=new StringBuilder("'"+userid+"'");
					for(HashMap<String, Object> m : demandList){
						userids.append(","+m.get("userid"));
					}
					strsql = field +" in ("+userids+")";
				}else{
					strsql = field+ " = '"+userid+"'";
				}
			}
			
			
			if(strsql.equals("")){
				return "";
			}
			return " "+sfmark +" ("+strsql+")";
		}
		
		
		

		public int getUserTypeInfo(String userid){
				String sql="SELECT quote(froleid) froleid FROM t_sys_UserRole  WHERE fuserid='"+userid+"'";
				List<HashMap<String, Object>> roleidList = this.QueryBySql(sql);
				StringBuilder userids=new StringBuilder("'"+userid+"'");
				if(roleidList.size()!=0){//关联用户过滤
					for(HashMap<String, Object> m : roleidList){
						userids.append(","+m.get("froleid"));
					}
				}
				sql="SELECT sum(DISTINCT IF(FRESOURCESID='6755469e-cbaa-11e4-a8a2-00ff6b42e1e5' ,1,2)) ftype  FROM t_sys_Userpermission WHERE fuserid in ("+userids.toString()+")AND  FRESOURCESID IN('6755469e-cbaa-11e4-a8a2-00ff6b42e1e5','661d00e1-6a50-11e4-bdb9-00ff6b42e1e5','1728ba31-0d2a-11e5-9395-00ff61c9f2e3')";//纸板订单、我的订单
				List<HashMap<String, Object>> usertype = this.QueryBySql(sql);
				int usetype=usertype.get(0).get("ftype")==null?0:((BigDecimal)usertype.get(0).get("ftype")).intValue();
				//纸箱客户2 纸板客户 1 ，纸板纸箱客户3 ,0为其他
				return usetype;

		}
}
