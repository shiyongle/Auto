package com.dao.menu;

import java.util.List;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;
import com.dao.IBaseDaoImpl;
import com.model.menu.Menu;


@Repository("menuDao")
public class MenuDaoImpl extends IBaseDaoImpl<Menu, java.lang.String> implements MenuDao {

	@Override
	public List<Menu> findAll() {
		List<Menu> list =super.findByHql("from Menu order by forder");
		if(list!=null)
		{
			return list;
		}
		return null;
	}

	@Override
	public Menu findById(String fid)
	{
		String hql = "from Menu where fid = ?"; 
		Query query = this.getSessionFactory().getCurrentSession().createQuery(hql);  
		query.setString(0, fid);  
		@SuppressWarnings("unchecked")
		List<Menu> list = query.list(); 
		if(list != null && list.size() > 0){
			return list.get(0);
		}
		return null;  
	}

	@Override
	public List<Menu> findByDisId(String ids) {
		{
			//根据配置表中找到的多个fmenu(即禁用),not in找到启用的菜单
			String hql = "from Menu where fid not in (select fmenu from UserMenu where fuid=:ids))"; 
			Query query = this.getSessionFactory().getCurrentSession().createQuery(hql);  
			query.setParameter("ids", ids); 
			@SuppressWarnings("unchecked")
			List<Menu> list = query.list(); 
			if(list != null && list.size() > 0){
				return list;
			}
			return null;  
		}
	}
}
