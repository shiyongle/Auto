
package com.service.userpermission;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dao.IBaseDao;
import com.dao.userpermission.TsysUserpermissionDao;
import com.model.userpermission.Userpermission;
import com.service.IBaseManagerImpl;

@Service("tsysUserpermissionManager")
@Transactional
public class TsysUserpermissionManagerImpl extends IBaseManagerImpl<Userpermission,java.lang.String> implements TsysUserpermissionManager{
	@Autowired
	private TsysUserpermissionDao tsysUserpermissionDao;
	
	public IBaseDao<Userpermission, java.lang.String> getEntityDao() {
		return this.tsysUserpermissionDao;
	}
	
}
