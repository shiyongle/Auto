package com.pc.dao.usercomplain;

import java.util.List;
import java.util.Map;

import cn.org.rapid_framework.page.PageRequest;

import com.pc.dao.aBase.IBaseDao;
import com.pc.model.CL_Complain;
import com.pc.query.usercomplain.UserComplainQuery;

public interface IUserComplainDao extends IBaseDao<CL_Complain, java.lang.Integer>{
	public List<Map<String,Object>> getOrderDropdown();
	public List<Map<String,Object>> getOrderDropdown(UserComplainQuery ucquery);
	public int updateAcount(CL_Complain cpinfo);
	public List<Map<String, Object>> getEntrysByParentId(Integer fid);
}
