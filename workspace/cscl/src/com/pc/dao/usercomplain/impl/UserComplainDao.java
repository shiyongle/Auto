package com.pc.dao.usercomplain.impl;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import cn.org.rapid_framework.page.PageRequest;

import com.pc.dao.aBase.impl.BaseDao;
import com.pc.dao.usercomplain.IUserComplainDao;
import com.pc.model.CL_Complain;
import com.pc.query.usercomplain.UserComplainQuery;
@Service("userComplainDao")
public class UserComplainDao extends BaseDao<CL_Complain, java.lang.Integer> implements IUserComplainDao {
	@Override
	public String getIbatisSqlMapNamespace() {
		return "CL_Complain";
	}

	@Override
	public List<Map<String, Object>> getOrderDropdown() {
		// TODO Auto-generated method stub
		List list = getSqlSession().selectList(getIbatisSqlMapNamespace()+".getOrderDropdown");
		return list;
	}

	@Override
	public List<Map<String, Object>> getOrderDropdown(UserComplainQuery ucquery) {
		// TODO Auto-generated method stub
		List list = getSqlSession().selectList(getIbatisSqlMapNamespace()+".getOrderDropdown",ucquery);
		return list;
	}

	@Override
	public int updateAcount(CL_Complain cpinfo) {
		// TODO Auto-generated method stub
		return this.getSqlSession().update(getIbatisSqlMapNamespace()+".updateAmount",cpinfo);
	}
	
	@Override
	public List<Map<String, Object>> getEntrysByParentId(Integer fid) {
		// TODO Auto-generated method stub
		List list = getSqlSession().selectList(getIbatisSqlMapNamespace()+".getEntrysByParentId", fid);
		return list;
	}

}
