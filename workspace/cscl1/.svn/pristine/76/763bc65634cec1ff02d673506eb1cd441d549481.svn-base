package com.pc.dao.financeBill;

import java.util.List;
import java.util.Map;

import cn.org.rapid_framework.page.Page;
import cn.org.rapid_framework.page.PageRequest;

import com.pc.dao.aBase.IBaseDao;
import com.pc.model.CL_FinanceBill;

public interface IFinanceBillDao extends IBaseDao<CL_FinanceBill, java.lang.Integer>{	
	public List<CL_FinanceBill> findAllPage(PageRequest pr);
	public List<Map<String,Object>> getLastMonthBill(String statementName);
	public Page<CL_FinanceBill> CusBillfindPage(PageRequest pr);

}
