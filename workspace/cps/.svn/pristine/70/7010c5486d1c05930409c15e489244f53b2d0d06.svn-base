/*
 * CPS-VMI-wangc
 */

package com.service.useraddress;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dao.IBaseDao;
import com.dao.useraddress.TbdUseraddressDao;
import com.model.useraddress.Useraddress;
import com.service.IBaseManagerImpl;

@Service("tbdUseraddressManager")
@Transactional
public class TbdUseraddressManagerImpl extends IBaseManagerImpl<Useraddress,java.lang.String> implements TbdUseraddressManager{

	private TbdUseraddressDao tbdUseraddressDao;

	public void setTbdUseraddressDao(TbdUseraddressDao dao) {
		this.tbdUseraddressDao = dao;
	}
	
	public IBaseDao<Useraddress, java.lang.String> getEntityDao() {
		return this.tbdUseraddressDao;
	}
	
	
	
}
