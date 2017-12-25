/*
 * 网蓝
 */

package com.service.userrole;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dao.IBaseDao;
import com.dao.userrole.TsysUserroleDao;
import com.model.userrole.UserRole;
import com.service.IBaseManagerImpl;

@Service("tsysUserroleManager")
@Transactional
public class TsysUserroleManagerImpl extends IBaseManagerImpl<UserRole,java.lang.String> implements TsysUserroleManager{
	
	@Autowired
	private TsysUserroleDao tsysUserroleDao;

	
	public IBaseDao<UserRole, java.lang.String> getEntityDao() {
		return this.tsysUserroleDao;
	}
	
}
