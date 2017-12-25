package com.pc.dao.distance;

import java.math.BigDecimal;

import com.pc.dao.aBase.IBaseDao;
import com.pc.model.CL_Distance;

public interface IDistanceDao extends IBaseDao<CL_Distance, java.lang.Integer>{
	
	/**
	 * 查找对应的固定距离
	 * @param fcustomer_id 客户id
	 * @param faddressDel_id 发货地址id
	 * @param faddressRec_id 卸货地址id
	 * @return
	 */
	public BigDecimal getMileage(Integer fcustomer_id,Integer faddressDel_id,Integer faddressRec_id);
	
	
	/**
	 * 判断该客户是否记录过相同的路线
	 * @param fcustomer_id
	 * @param faddressDel_id
	 * @param faddressRec_id
	 * @return
	 */
	public int isExist(Integer fcustomer_id,Integer faddressDel_id,Integer faddressRec_id);
}
