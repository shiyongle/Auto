package com.pc.dao.couponsActivity.impl;



import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Service;

import cn.org.rapid_framework.page.Page;
import cn.org.rapid_framework.page.PageRequest;

import com.pc.dao.aBase.impl.BaseDao;
import com.pc.dao.couponsActivity.ICouponsActivityDao;
import com.pc.model.CL_CouponsActivity;

@Service("couponsActivityDao")
public class CouponsActivityDaoImpl extends BaseDao<CL_CouponsActivity, Integer> implements
		ICouponsActivityDao {

	@Override
	public String getIbatisSqlMapNamespace(){
		return "CL_CouponsActivity";
	}

	@Override
	public Page<CL_CouponsActivity> singleFindPage(PageRequest pr) {
		return this.pageQuery(getIbatisSqlMapNamespace() + ".singleFindPage", pr);
	}

	@Override
	public List<Integer> getBeOverdueCouponsActivity() {
		return this.getSqlSession().selectList(getIbatisSqlMapNamespace() + ".getBeOverdueCouponsActivity");
	}

	@Override
	public int updateCoupons(Integer[] ids) {
		return this.getSqlSession().update(getIbatisSqlMapNamespace() + ".updateCoupons", ids);
	}

	@Override
	public Number singleFindPageCount(Integer creator, String fissueUser) {
		HashMap<String, Object> m = new HashMap<>();
		m.put("creator", creator);
		m.put("fissueUser", fissueUser);
		return this.getSqlSession().selectOne(getIbatisSqlMapNamespace() + ".singleFindPageCount", m);
	}

	@Override
	public List<CL_CouponsActivity> getActivityByTopUpDollars(BigDecimal dollars) {
		return this.getSqlSession().selectList(getIbatisSqlMapNamespace() + ".getActivityByTopUpDollars", dollars);
	}
	
	@Override
	public List<CL_CouponsActivity> getActivityByDollars(BigDecimal dollars) {
		return this.getSqlSession().selectList(getIbatisSqlMapNamespace() + ".getActivityByDollars", dollars);
	}

}
