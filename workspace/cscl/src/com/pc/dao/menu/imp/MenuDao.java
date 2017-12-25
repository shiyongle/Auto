package com.pc.dao.menu.imp;

import java.util.List;

import org.springframework.stereotype.Service;




import com.pc.dao.aBase.impl.BaseDao;
import com.pc.dao.menu.IMenuDao;
import com.pc.model.CL_MenuItem;
import com.pc.model.Util_Option;
import com.pc.util.JSONUtil;

@Service("menuDao")
public class MenuDao extends BaseDao<CL_MenuItem,java.lang.Integer> implements IMenuDao {
	
	@Override
	public String getIbatisSqlMapNamespace() {
		return "CL_MenuItem";
	}
	
	@Override
	public List<CL_MenuItem> getAllotPerssion(Integer urId) {
		return this.getSqlSession().selectList(getIbatisSqlMapNamespace()+ ".getAllotPerssion", urId);

	}

	@Override
	public List<CL_MenuItem> getAllMenuInfo() {
		return this.getSqlSession().selectList(getIbatisSqlMapNamespace()+ ".findAll");
	}

	@Override
	public List<CL_MenuItem> getAllParentMenus() {
		return this.getSqlSession().selectList(getIbatisSqlMapNamespace()+".getAllParentMenus");
	}

	@Override
	public List<CL_MenuItem> getListbyParentid(int fparentid) {
		// TODO Auto-generated method stub
		return this.getSqlSession().selectList(getIbatisSqlMapNamespace()+".getListbyParentid",fparentid);
	}


}
