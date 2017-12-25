package com.pc.dao.finance.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pc.dao.aBase.impl.BaseDao;
import com.pc.dao.finance.IFinanceDao;
import com.pc.dao.financeStatement.IFinanceStatementDao;
import com.pc.model.CL_Finance;
import com.pc.model.CL_FinanceStatement;

import cn.org.rapid_framework.page.Page;
import cn.org.rapid_framework.page.PageRequest;

@Service("financeDao")
public class FinanceDaoImpl extends BaseDao<CL_Finance,java.lang.Integer> implements IFinanceDao{

	@Resource
	private IFinanceStatementDao financeStatementDao;
	
	@Override
	public String getIbatisSqlMapNamespace() {
		return "CL_Finance";
	}

	@Override
	public Page<CL_Finance> findPage(PageRequest pr){
		return this.pageQuery(getIbatisSqlMapNamespace() + ".findPage", pr);
	}

	@Override
	public CL_Finance getByNowTime(Date date) {
		HashMap<String,Object> map =new HashMap<String,Object>();
		map.put("nowTime", date);
		return this.getSqlSession().selectOne(getIbatisSqlMapNamespace()+".getByNowTime",map);
	}
	
	@Override
	public int getMaxId()
	{
		return this.getSqlSession().selectOne(getIbatisSqlMapNamespace()+".getMaxId");
	}
	
	@Override
	public List<CL_Finance> getbyUseridAndState(Integer userroleid)
	{
		return this.getSqlSession().selectList(getIbatisSqlMapNamespace()+".getbyUseridAndState",userroleid);
	}

	@Transactional
	public int withdrawSave(CL_Finance finance, CL_FinanceStatement statement) {
		// TODO Auto-generated method stub
		try{
			if(finance.getFstate() == 6){
				this.save(finance);
			} 
			statement.setFrelatedId(finance.getFid()+""); // 提现记录 ID
			financeStatementDao.save(statement);
			return 1;
		}catch(Exception e){
			return 0;
		}
	}

}
