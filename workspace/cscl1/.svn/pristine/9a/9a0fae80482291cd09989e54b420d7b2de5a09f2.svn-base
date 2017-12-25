package com.pc.dao.couponRule.impl;


import java.math.BigDecimal;
import java.util.HashMap;

import org.springframework.stereotype.Service;

import com.pc.dao.aBase.impl.BaseDao;
import com.pc.dao.couponRule.IcouponRuleDao;
import com.pc.model.CL_CouponRule;

/**
 *  
 * @author twr
 *
 */
@Service("couponRuleDao")
public class couponRuleDao extends BaseDao<CL_CouponRule,java.lang.Integer> implements IcouponRuleDao{

	@Override
	public String getIbatisSqlMapNamespace() {
		return "CL_CouponRule";
	}

	@Override
	public int updateById(CL_CouponRule coupon) {
		return this.getSqlSession().update(getIbatisSqlMapNamespace()+".updateById",coupon);
	}

	 
 
 
}
