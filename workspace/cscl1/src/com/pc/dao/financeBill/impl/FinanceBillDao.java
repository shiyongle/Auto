package com.pc.dao.financeBill.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import cn.org.rapid_framework.page.Page;
import cn.org.rapid_framework.page.PageRequest;

import com.pc.dao.aBase.impl.BaseDao;
import com.pc.dao.financeBill.IFinanceBillDao;
import com.pc.model.CL_FinanceBill;

@Service("financeBillDao")
public class FinanceBillDao extends BaseDao<CL_FinanceBill,java.lang.Integer> implements IFinanceBillDao {

	@Override
	public String getIbatisSqlMapNamespace() {
		return "CL_FinanceBill";
	}
 
	@Override
	public List<CL_FinanceBill> findAllPage(PageRequest pr) {
		// TODO Auto-generated method stub
		return this.getSqlSession().selectList(getIbatisSqlMapNamespace()+".findAllPage",pr);
	}

	@Override
	public List<Map<String, Object>> getLastMonthBill(String statementName) {
		// TODO Auto-generated method stub
		return this.getSqlSession().selectList(getIbatisSqlMapNamespace()+"."+statementName);
	}
	
	@Override
	public Page<CL_FinanceBill> CusBillfindPage(PageRequest pr) {
		// TODO Auto-generated method stub
		return pageQuery(getIbatisSqlMapNamespace()+".CusBillfindPage",pr);
	}

}
