package com.pc.dao.couponRule;



import com.pc.dao.aBase.IBaseDao;
import com.pc.model.CL_CouponRule;

public interface IcouponRuleDao extends IBaseDao<CL_CouponRule, java.lang.Integer> {
    /**通过指定数据跟新*/
	public int updateById(CL_CouponRule coupon);
	
}
