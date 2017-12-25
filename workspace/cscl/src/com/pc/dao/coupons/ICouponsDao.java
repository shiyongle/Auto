package com.pc.dao.coupons;

import java.util.HashMap;
import java.util.List;
import cn.org.rapid_framework.page.Page;
import cn.org.rapid_framework.page.PageRequest;
import com.pc.dao.aBase.IBaseDao;
import com.pc.model.CL_Coupons;
import com.pc.model.CL_Order;
import com.pc.query.coupons.CL_CouponsQuery;

public interface ICouponsDao extends IBaseDao<CL_Coupons, java.lang.Integer>{
	
	public Page<CL_Coupons> singleFindPage(PageRequest pr);
	
	/***批量有效/失效*/
	public void updatepl(Integer effective,Integer[] ids);
	
	/***批量删除*/
	public void deletepl(Integer[] ids);
	
	/*** 页末-合计*/
    public List<CL_Coupons> getFooter(CL_CouponsQuery couponsQuery);
    
    /***通过兑换码获取好运券类型为1：业务员优惠券的好运券**/
    public List<CL_Coupons> getByRedeemCode(String redeemCode);
    
    /***通过兑换码获取好运券类型为1：业务员优惠券的好运券-获取有效的好运券**/
    public CL_Coupons getByRedeemCodeForACTIVE(String redeemCode);
    
    /***每天晚上去查询一下过期的好运券*/
    public List<Integer> getBeOverdueCoupons();
    
    /***将好运券的isOverdue 更改为1过期*/
	public void updateCoupons(Integer[] ids);
	//查重名
	public Number getNumByCouponsName(String fcouponsName);
}
