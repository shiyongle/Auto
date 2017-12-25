package com.dao.userMenu;

import java.util.List;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;
import com.dao.IBaseDaoImpl;
import com.model.usermenu.UserMenu;

@Repository("usermenuDao")
public class UserMenuDaoImpl extends IBaseDaoImpl<UserMenu,java.lang.String> implements UserMenuDao{

	@SuppressWarnings("unchecked")
	@Override
	public UserMenu getIdByInfo(String fuid,String fmenu) {
		String hql = "from UserMenu where fuid =:fuid and fmenu=:fmenu"; 
		Query query = this.getSessionFactory().getCurrentSession().createQuery(hql);  
		query.setParameter("fuid", fuid); 
		query.setParameter("fmenu", fmenu); 
		List<UserMenu> list = query.list(); 
		if(list != null && list.size() > 0){
			return list.get(0);
		}
		return null;
	}


	@SuppressWarnings("unchecked")
	@Override
	public List<UserMenu> getByUserId(String fid) {

		String hql = "from UserMenu where fuid =:fid"; 
		Query query = this.getSessionFactory().getCurrentSession().createQuery(hql);  
		query.setParameter("fid", fid); 
		List<UserMenu> list = query.list(); 
		if(list != null && list.size() > 0){
			return list;
		}
		return null;
	}

	@Override
	public void saveOrupdateUserMenu(UserMenu userMenu,String type) throws Exception {
		String sql = "select * from t_sys_usermenu where fuid='"+userMenu.getFuid()+"' ";
		if("disable".equals(type))
		{
			//禁用
			userMenu.setFid(this.CreateUUid());
			this.save(userMenu);
		}
		else if("able".equals(type))
		{
			if(this.QueryExistsBySql(sql))
			{
				//启用
				this.delete("t_sys_usermenu", userMenu.getFid());
			}

		}
	}

}
