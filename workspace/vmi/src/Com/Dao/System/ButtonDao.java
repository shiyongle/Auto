package Com.Dao.System;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import Com.Base.Dao.BaseDao;
import Com.Base.Util.DJException;
import Com.Base.Util.params;
import Com.Entity.System.Button;
import Com.Entity.System.Mainmenuitem;

@Service("ButtonDao")
public class ButtonDao extends BaseDao implements IButtonDao {

	@Override
	public Button Query(String fid) {
		return this.getHibernateTemplate().get(Button.class, fid);
	}

	@Override
	public void ExecSave(Button info) throws DJException {
		if (info.getFid().isEmpty()) {
			info.setFpath(info.getFpath() + "!" + info.getFname());
			List<Button> slist = this
					.QueryByHql("select t.fid from Button t where t.fpath='"
							+ info.getFpath() + "'");
			if (slist.size() > 0) {
				throw new DJException("菜单路径重复,不能保存");
			}
			info.setFid(this.CreateUUid());
			this.saveOrUpdate(info);
		} else {
			int i = info.getFpath().lastIndexOf("!");
			String path = info.getFpath().substring(0, i + 1) + info.getFname();
			if(this.QueryExistsBySql("select t.fid from t_sys_button t where t.fpath='"
							+ info.getFpath() + "' and fid <>'"+info.getFid()+"'")) {
				throw new DJException("菜单路径重复,不能保存");
			}
			info.setFpath(path);
			this.saveOrUpdate(info);
			this.ExecBySql("update t_sys_userpermission set fpath='"+path+"' where fresourcesid='"+info.getFid()+"'");

		}
	}

	private String GetPathStr(String path) {
		String result = "";
		String s = "";
		String[] str = path.split("!");
		for (int i = 0; i < str.length; i++) {
			if (i > 0) {
				result = result + ",";
				s = s + "!";
			}
			s = s + str[i];
			result = result + "'" + s + "'";
		}

		return result;
	}

	@Override
	public void ExecSetPermission(String PermissionID, String Userid)
			throws DJException {
		List<Mainmenuitem> Mainmenuitemlist = this
				.QueryByHql("from Mainmenuitem where fid = '" + PermissionID
						+ "'");
		if (Mainmenuitemlist.size() > 0) {
			Mainmenuitem info = Mainmenuitemlist.get(0);
			String path = GetPathStr(info.getFpath());
			String sql = "insert into t_sys_userpermission (fid,fuserid,fresourcesid,fpath) select uuid(),'"
					+ Userid + "',fid,fpath from t_sys_mainmenuitem where ";
			sql = sql + " (t_sys_mainmenuitem.fpath in (" + path
					+ ") or t_sys_mainmenuitem.fpath like '" + info.getFpath()
					+ "%') ";
			sql = sql
					+ " and not exists (select fid from t_sys_userpermission where t_sys_userpermission.fresourcesid=t_sys_mainmenuitem.fid and fuserid='"
					+ Userid + "') ";
			this.ExecBySql(sql);
			sql = "insert into t_sys_userpermission (fid,fuserid,fresourcesid,fpath) select uuid(),'"
					+ Userid
					+ "',fid,fpath from t_sys_button where fpath like '"
					+ info.getFpath() + "%' ";
			sql = sql
					+ " and not exists (select fid from t_sys_userpermission where t_sys_userpermission.fresourcesid=t_sys_button.fid and fuserid='"
					+ Userid + "') ";
			this.ExecBySql(sql);
		} else {
			Button info = this.Query(PermissionID);
			String sql = "insert into t_sys_userpermission (fid,fuserid,fresourcesid,fpath) select uuid(),'"
					+ Userid
					+ "',fid,fpath from t_sys_button where fid = '"
					+ PermissionID + "' ";
			sql = sql
					+ " and not exists (select fid from t_sys_userpermission where t_sys_userpermission.fresourcesid=t_sys_button.fid and fuserid='"
					+ Userid + "') ";
			this.ExecBySql(sql);
			sql = "insert into t_sys_userpermission (fid,fuserid,fresourcesid,fpath) select uuid(),'"
					+ Userid + "',fid,fpath from t_sys_mainmenuitem where ";
			sql = sql
					+ " t_sys_mainmenuitem.fpath in ("
					+ GetPathStr(info.getFpath())
					+ ") and not exists (select fid from t_sys_userpermission where t_sys_userpermission.fresourcesid=t_sys_mainmenuitem.fid and fuserid='"
					+ Userid + "') ";
			this.ExecBySql(sql);

		}
	}

	@Override
	public void ExecDelPermission(String PermissionID, String Userid)
			throws DJException {
		List<Mainmenuitem> slist = this
				.QueryByHql("from Mainmenuitem where fid='" + PermissionID
						+ "'");
		if (slist.size() > 0) {
			String sql = "delete from t_sys_userpermission where fuserid=:fuserid and (exists (select fid from t_sys_mainmenuitem where t_sys_userpermission.fresourcesid=t_sys_mainmenuitem.fid and  t_sys_mainmenuitem.fpath like :fpath ) or exists (select fid from t_sys_button where t_sys_userpermission.fresourcesid=t_sys_button.fid and  t_sys_button.fpath like :fpath))";
			params p = new params();
			p.setString("fuserid", Userid);
			p.setString("fpath", slist.get(0).getFpath()+"%");
			this.ExecBySql(sql,p);
		} else {
			String sql = "delete from t_sys_userpermission where fuserid=:fuserid and fresourcesid=:fresourcesid";
			params p = new params();
			p.setString("fuserid", Userid);
			p.setString("fresourcesid", PermissionID);
			this.ExecBySql(sql, p);
		}

	}

	@Override
	public void ExecDelButton(HttpServletRequest request) throws DJException {
		// TODO Auto-generated method stub
				String fid = request.getParameter("fid");
				params p=new params();
				p.setString("fid", fid);
				int fcount=this.QueryCountBySql("select fid from t_sys_userpermission where fresourcesid=:fid ",p);
				if(fcount>0)
				{
					throw new DJException("该功能已经被分配权限，不能删除");
				}
				this.Delete("t_sys_button", fid);
		
	}


	public void ExecRecoverPermissions(String fid) throws DJException {
		// TODO Auto-generated method stub
		
		List<Mainmenuitem> slist = this
				.QueryByHql("from Mainmenuitem where fid='" + fid
						+ "'");
		if (slist.size() > 0) {
			String sql = "delete from t_sys_userpermission where exists (select fid from t_sys_mainmenuitem where t_sys_userpermission.fresourcesid=t_sys_mainmenuitem.fid and  t_sys_mainmenuitem.fpath like :fpath ) or exists (select fid from t_sys_button where t_sys_userpermission.fresourcesid=t_sys_button.fid and  t_sys_button.fpath like :fpath)";
			params p = new params();
			p.setString("fpath", slist.get(0).getFpath()+"%");
			this.ExecBySql(sql,p);
		} else {
			String sql = "delete from t_sys_userpermission where fresourcesid=:fresourcesid";
			params p = new params();
			p.setString("fresourcesid", fid);
			this.ExecBySql(sql, p);
		}
	}

}
