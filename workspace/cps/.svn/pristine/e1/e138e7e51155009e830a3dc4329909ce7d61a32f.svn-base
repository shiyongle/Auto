package com.service.userdiary;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dao.IBaseDao;
import com.dao.userdiary.TsysUserdiaryDao;
import com.model.userdiary.Userdiary;
import com.service.IBaseManagerImpl;

@Service("tsysUserdiaryManager")
@Transactional
public class TsysUserdiaryManagerImpl extends IBaseManagerImpl<Userdiary,java.lang.String> implements TsysUserdiaryManager{

	private TsysUserdiaryDao tsysUserdiaryDao;

	public void setTsysUserdiaryDao(TsysUserdiaryDao dao) {
		this.tsysUserdiaryDao = dao;
	}
	
	public IBaseDao<Userdiary, java.lang.String> getEntityDao() {
		return this.tsysUserdiaryDao;
	}
	
}
