package com.service.useronline;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dao.IBaseDao;
import com.dao.useronline.TsysUseronlineDao;
import com.model.useronline.Useronline;
import com.service.IBaseManagerImpl;

@Service("tsysUseronlineManager")
@Transactional
public class TsysUseronlineManagerImpl extends IBaseManagerImpl<Useronline,java.lang.String> implements TsysUseronlineManager{
	@Autowired
	private TsysUseronlineDao tsysUseronlineDao;

	
	public IBaseDao<Useronline, java.lang.String> getEntityDao() {
		return this.tsysUseronlineDao;
	}
	
}
