package com.pc.dao.financeReceive.impl;

import org.springframework.stereotype.Service;
import cn.org.rapid_framework.page.Page;
import cn.org.rapid_framework.page.PageRequest;
import com.pc.dao.aBase.impl.BaseDao;
import com.pc.dao.financeReceive.IFinanceReceiptDao;
import com.pc.model.CL_FinanceReceipt;

@SuppressWarnings("unchecked")
@Service("financeReceiptDao")
public class FinanceReceiptDaoImpl extends BaseDao<CL_FinanceReceipt,java.lang.Integer> implements IFinanceReceiptDao{

	@Override
	public String getIbatisSqlMapNamespace() {
		return "CL_FinanceReceipt";
	}

	@Override
	public Page<CL_FinanceReceipt> findPage(PageRequest pr){
		return this.pageQuery(getIbatisSqlMapNamespace() + ".findPage", pr);
	}

//	@Override
//	public int getMaxId()
//	{
//		return this.getSqlSession().selectOne(getIbatisSqlMapNamespace()+".getMaxId");
//	}

}
