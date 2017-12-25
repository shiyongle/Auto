/*
 * CPS-VMI-wangc
 */

package com.service.saledeliverdetail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dao.IBaseDao;
import com.dao.saledeliverdetail.SaledeliverentryDao;
import com.model.saledeliverdetail.FTUSaledeliverEntry;
import com.service.IBaseManagerImpl;

@Service("saledeliverentryManager")
@Transactional
public class SaledeliverentryManagerImpl extends IBaseManagerImpl<FTUSaledeliverEntry,java.lang.String> implements SaledeliverentryManager{
	@Autowired
	private SaledeliverentryDao saledeliverentryDao;

	@Override
	protected IBaseDao<FTUSaledeliverEntry, String> getEntityDao() {
		return saledeliverentryDao;
	}

	
	
}
