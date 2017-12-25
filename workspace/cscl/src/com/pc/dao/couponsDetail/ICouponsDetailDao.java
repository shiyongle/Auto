package com.pc.dao.couponsDetail;

import java.util.HashMap;
import java.util.List;

import com.pc.dao.aBase.IBaseDao;
import com.pc.model.CL_CouponsDetail;
import com.pc.model.CL_Order;

/** CPS-VMI */
public interface ICouponsDetailDao extends IBaseDao<CL_CouponsDetail, java.lang.Integer>{
	
	/** 二维码兑换好运券时,通过二维码对应的id,检查是否已存在该对象,若存在则表示已领取,反之*/
	public CL_CouponsDetail getByCouponsId(Integer couponsId);
	
	public List<CL_CouponsDetail> getByCouponsId2(Integer couponsId);
	
	/** 获取过期的好运券*/
	public List<Integer> getBeOverdueCouponsDetail();
	
	/** 将好运券的isOverdue 更改为1过期*/
	public void updateCouponsDetail(Integer[] ids);
	
	 /***好运券  领取判断,防止多次领取*/
	public int getCountByUserCoupon(Integer userId,Integer couponsActivityId);
	
	/**根据用户判断是否送过88元好运卷*/
	public List<CL_CouponsDetail> IsExistByUserRoleId(Integer userRoleId);
	
	/**根据用户获得好运卷*/
	public List<CL_CouponsDetail> getByUserRoleId(Integer userRoleId,Integer isUse);

	/**根据订单ID获得好运卷*/
	public CL_CouponsDetail getByOrderId(Integer forderId);
	
	/***获取条件好运券**/
	public List<CL_CouponsDetail> getEffective(Integer creator,Integer is_use,Integer is_overdue,Integer type,String area,String carSpecId);
	/***获取条件好运券**/
	public List<CL_CouponsDetail> getEffective2(Integer creator,Integer is_use,Integer is_overdue,Integer type);

}