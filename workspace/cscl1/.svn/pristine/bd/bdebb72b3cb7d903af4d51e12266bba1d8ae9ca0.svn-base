package com.pc.dao.finance.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Service;
import cn.org.rapid_framework.page.Page;
import cn.org.rapid_framework.page.PageRequest;
import com.pc.dao.aBase.impl.BaseDao;
import com.pc.dao.finance.IFinanceDao;
import com.pc.model.CL_Finance;

@Service("financeDao")
public class FinanceDaoImpl extends BaseDao<CL_Finance,java.lang.Integer> implements IFinanceDao{

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

}
