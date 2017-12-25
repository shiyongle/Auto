package com.pc.dao.menupermission.impl;



import org.springframework.stereotype.Service;



import com.pc.dao.aBase.impl.BaseDao;
import com.pc.dao.menupermission.IMenuPermissionDao;
import com.pc.model.CL_MenuPermission;
@Service("menuPermissionDao")
public class MenuPermissionDao extends BaseDao<CL_MenuPermission,java.lang.Integer> implements IMenuPermissionDao {
	@Override
	public String getIbatisSqlMapNamespace() {
		return "CL_MenuPermission";
	}

}
