package com.pc.dao.finance;

import java.util.Date;
import java.util.List;

import cn.org.rapid_framework.page.Page;
import cn.org.rapid_framework.page.PageRequest;
import com.pc.dao.aBase.IBaseDao;
import com.pc.model.CL_Finance;

public interface IFinanceDao extends IBaseDao<CL_Finance, java.lang.Integer>{
	
	public Page<CL_Finance> findPage(PageRequest pr);
	
	public CL_Finance getByNowTime(Date date) ;
	
	public int getMaxId();

	public List<CL_Finance> getbyUseridAndState(Integer userroleid);
	
}
