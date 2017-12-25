package Com.Dao.System;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import Com.Base.Dao.BaseDao;
import Com.Base.Util.DJException;
import Com.Base.Util.params;
import Com.Entity.System.Mainmenuitem;


@Service("MainMenuDao")
public class MainMenuDao extends BaseDao implements IMainMenuDao {

	/**
	 * 函数说明：获得一条的信息 参数说明： ID 返回值：对象
	 */
	@Override
	public Mainmenuitem Query(String fid) {
		return this.getHibernateTemplate().get(
				Mainmenuitem.class, fid);
	}

	/**
	 * 函数说明：查询的所有信息 参数说明： 集合 返回值：
	 */
	@Override
	public List QueryMainmenuItemcls(String hql) {
		// String sql = " FROM Products where  " + fieldname + "  like '% " +
		// value + " %' " + " ORDER BY gameNameCn " ;
		return this.getHibernateTemplate().find(hql);
	}

	@Override
	public HashMap<String, Object> ExecSave(Mainmenuitem info) throws DJException {
		HashMap<String, Object> params = new HashMap<>();
		if (info.getFid().isEmpty()) {
			info.setFpath(info.getFpath()+"!"+info.getFname());
			List<Mainmenuitem> slist=this.QueryByHql("select t.fid from Mainmenuitem t where t.fpath='"+info.getFpath()+"'");
			if (slist.size()>0)
			{
				throw new DJException("菜单路径重复,不能保存");
			}
			Mainmenuitem parentinfo = this.Query(info.getFparentid());
			parentinfo.setFisleaf(false);
			this.saveOrUpdate(parentinfo);
			info.setFid(this.CreateUUid());
			this.saveOrUpdate(info);
		}
		else
		{
			List<HashMap<String,Object>> slist=this.QueryBySql("select fpath from t_sys_mainmenuitem where fid ='"+info.getFid()+"'");
			String oldpath = slist.get(0).get("fpath").toString();
			int i=info.getFpath().lastIndexOf("!");
			String path=info.getFpath().substring(0,i+1)+info.getFname();
			if(this.QueryExistsBySql("select t.fid from t_sys_mainmenuitem t where t.fpath='"+path+"' and fid<>'"+info.getFid()+"'" ))
			{
				throw new DJException("菜单路径重复,不能保存");
			}
			info.setFpath(path);
			this.saveOrUpdate(info);
			this.ExecBySql("update t_sys_mainmenuitem set fpath=REPLACE(fpath, '"+oldpath+"!', '"+path+"!')");
			this.ExecBySql("update t_sys_button set fpath=REPLACE(fpath, '"+oldpath+"!', '"+path+"!')");
			this.ExecBySql("update t_sys_userpermission set fpath=REPLACE(fpath, '"+oldpath+"!', '"+path+"!')");
		}
		params.put("success", true);
		return params;
	}

	@Override
	public void DelMainmenuitem(HttpServletRequest request) throws DJException {
		// TODO Auto-generated method stub
		String fid = request.getParameter("fid");
		params p=new params();
		p.setString("fid", fid);
		int fcount=this.QueryCountBySql("select fid from t_sys_mainmenuitem where fparentid=:fid ",p);
		if(fcount>0)
		{
			throw new DJException("不能删除存在子目录的菜单");
		}
		fcount=this.QueryCountBySql("select fid from t_sys_button where fparentid=:fid ",p);
		if(fcount>0)
		{
			throw new DJException("不能删除存在功能项的菜单");
		}
		fcount=this.QueryCountBySql("select fid from t_sys_userpermission where fresourcesid=:fid ",p);
		if(fcount>0)
		{
			throw new DJException("该菜单已经被分配权限，不能删除");
		}
		this.Delete("t_sys_mainmenuitem", fid);
	}

}
