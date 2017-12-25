package com.pc.dao.couponsActivity;

import java.math.BigDecimal;
import java.util.List;

import cn.org.rapid_framework.page.Page;
import cn.org.rapid_framework.page.PageRequest;

import com.pc.dao.aBase.IBaseDao;
import com.pc.model.CL_CouponsActivity;

public interface ICouponsActivityDao extends IBaseDao<CL_CouponsActivity, Integer> {
	
	public Page<CL_CouponsActivity> singleFindPage(PageRequest pr);
	
	//获取过期的活动
	public List<Integer> getBeOverdueCouponsActivity();
	
	//改为过期
	public int updateCoupons(Integer[] ids);
	
	public Number singleFindPageCount(Integer creator, String fissueUser);
	
	//通过充值的金额获取符合区间的优惠活动
	public List<CL_CouponsActivity> getActivityByTopUpDollars(BigDecimal dollars);
	
	//通过活动金额查找优惠活动
	public List<CL_CouponsActivity> getActivityByDollars(BigDecimal dollars);
}
