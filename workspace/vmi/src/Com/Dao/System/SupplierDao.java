package Com.Dao.System;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.Query;
import org.springframework.stereotype.Service;

import Com.Base.Dao.BaseDao;
import Com.Entity.System.Supplier;
import Com.Entity.System.Useronline;

@Service("SupplierDao")
public class SupplierDao extends BaseDao implements ISupplierDao {

	/**
	 * 函数说明：获得一条的信息 参数说明： ID 返回值：对象
	 */
	@Override
	public Supplier Query(String fid) {
		return this.getHibernateTemplate().get(
				Supplier.class, fid);
	}

	/**
	 * 函数说明：查询的所有信息 参数说明： 集合 返回值：
	 */
	@Override
	public List QuerySysUsercls(String hql) {
		// String sql = " FROM Products where  " + fieldname + "  like '% " +
		// value + " %' " + " ORDER BY gameNameCn " ;
		return this.getHibernateTemplate().find(hql);

		
		
	}

	@Override
	public HashMap<String, Object> ExecSave(Supplier info) {
		HashMap<String, Object> params = new HashMap<>();
		if (info.getFid().isEmpty()) {
			info.setFid(this.CreateUUid());
//			info.setFeffect(0);
			info.setFcreatetime(new Date());
			this.saveOrUpdate(info);
		}
		else
		{
//			info.setFeffect(0);
			this.saveOrUpdate(info);
		}
		params.put("success", true);
		return params;
	}

	@Override
	public Useronline ExecCheckLogin(HttpServletRequest request) {
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		Query q = getSession().createQuery(" FROM SysUser where fname=:fname and fpassword=:fpassword ");
		q.setString("fname", username);
		q.setString("fpassword", password);
		List<Supplier> slist=q.list();
		if (slist.size()>0)
		{
			Useronline info=new Useronline();
			info.setFid(this.CreateUUid());
			info.setFuserid(slist.get(0).getFid());
			info.setFlogintime(new Date());
			info.setFlastoperatetime(new Date());
			info.setFusername(slist.get(0).getFname());
			info.setFsessionid(request.getSession().getId());
			this.saveOrUpdate(info);
			return info;
		}
		return null;
	}

	

}
