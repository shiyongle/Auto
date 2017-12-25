package com.service.supplier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dao.IBaseDao;
import com.dao.supplier.SupplierDao;
import com.model.supplier.Supplier;
import com.service.IBaseManagerImpl;
@Service("supplierManager")
@Transactional
public class SupplierManagerImpl extends IBaseManagerImpl<Supplier, java.lang.Integer> implements SupplierManager {

	@Autowired
	private SupplierDao supplierDao;
	@Override
	protected IBaseDao<Supplier, Integer> getEntityDao() {
		return this.supplierDao;
	}


}
