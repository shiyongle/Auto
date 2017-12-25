package com.pc.dao.report.impl;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.org.rapid_framework.page.PageRequest;

import com.pc.dao.report.IReportDao;

@Service("reportDao")
public class ReportDao extends SqlSessionDaoSupport implements IReportDao{

	public String getIbatisSqlMapNamespace() {
		return "CL_Report";
	}
	@Override
	public List<Map<String, Object>> findRptMonthPage(String statementName,PageRequest pageRequest) {
		// TODO Auto-generated method stub
		List list = getSqlSession().selectList(getIbatisSqlMapNamespace()+statementName, pageRequest);
		return list;
	}
	
	@Override
	public List<Map<String, Object>> findRptPage(String statementName, PageRequest pageRequest) {
		// TODO Auto-generated method stub
		List list = getSqlSession().selectList(getIbatisSqlMapNamespace()+statementName, pageRequest);
		return list;
	}

	@Override
	public int findRptPageCount(String statementName, PageRequest pageRequest) {
		List<String> list = getSqlSession().selectList(statementName, pageRequest);
		if(list!=null&&list.size()>0){
			return Integer.parseInt(list.get(0));
		}
		return 0;
	}
	@Override
	public List<Map<String, Object>> findUserMonthBillPage(String statementName,PageRequest pageRequest) {
		List list = getSqlSession().selectList(getIbatisSqlMapNamespace()+statementName, pageRequest);
		return list;
	}
	@Override
	public List<Map<String, Object>> findDriverFreightPage(String statementName, PageRequest pageRequest) {
		// TODO Auto-generated method stub
		List list = getSqlSession().selectList(getIbatisSqlMapNamespace()+statementName, pageRequest);
		return list;
	}
		

}
