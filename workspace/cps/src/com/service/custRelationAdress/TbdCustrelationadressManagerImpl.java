/*
 * CPS-VMI-wangc
 */

package com.service.custRelationAdress;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dao.IBaseDao;
import com.dao.custRelationAdress.TbdCustrelationadressDao;
import com.model.custRelationAdress.CustRelationAdress;
import com.service.IBaseManagerImpl;

@Service("tbdCustrelationadressManager")
@Transactional
public class TbdCustrelationadressManagerImpl extends IBaseManagerImpl<CustRelationAdress,java.lang.String> implements TbdCustrelationadressManager{

	private TbdCustrelationadressDao tbdCustrelationadressDao;

	public void setTbdCustrelationadressDao(TbdCustrelationadressDao dao) {
		this.tbdCustrelationadressDao = dao;
	}
	
	public IBaseDao<CustRelationAdress, java.lang.String> getEntityDao() {
		return this.tbdCustrelationadressDao;
	}
	
}
