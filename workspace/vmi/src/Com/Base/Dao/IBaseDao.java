package Com.Base.Dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.metadata.ClassMetadata;

import Com.Base.Util.DJException;
import Com.Base.Util.ListResult;
import Com.Base.Util.params;

public interface IBaseDao {
	public String CreateUUid();
	public void saveOrUpdate(Object o);
	public void save(Object o);
	public void Update(Object o);
	public void Delete(Object o);
	public List QueryBySql(String sql);
	public List QueryByHql(String hql);
	public void ExecByHql(String hql);
	public int ExecBySql(String sql);
	public String QueryCountBySql(String sql);
    public params QueryCheckSessionid(HttpServletRequest request);
    public void Delete(String talbe,String fid);
    public int ExecBySql(String sql,params p);
    public int QueryCountBySql(String sql,params p);
    public ListResult QueryFilterList(String sql,HttpServletRequest request) throws DJException;
    public List QueryBySql(String sql,params p);
    public String QueryFilterByUser(HttpServletRequest request,String cid,String sid);
    public String getFnumber(String table,String startstr,int length);
    public Map<String, ClassMetadata> getAllClassMetadata();
    public boolean QueryExistsBySql(String sql);
    public Object Query(String classname,String fid);
    public Object Query(Class classname,String fid);
    public String QueryFilterByUserofuser(HttpServletRequest request,String field,String fmark);
	List<HashMap<String, Object>> QueryBySql(String sql, Object[] params);
	public int getUserTypeInfo(String userid);
	public Object QueryByHqlObjct(String hql,params p) ;
}
