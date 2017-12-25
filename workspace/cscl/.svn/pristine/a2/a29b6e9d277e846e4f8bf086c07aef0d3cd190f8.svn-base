package com.pc.dao.refuse;

import com.pc.dao.aBase.IBaseDao;
import com.pc.model.CL_Order;
import com.pc.model.CL_Refuse;

/**
 *@author lancher
 *@date 2017年1月14日 下午3:12:04
 */
public interface IrefuseDao extends IBaseDao<CL_Refuse, Integer> {
	
	/**判断系统控制类型是否存在，不存在则可以新增*/
	public CL_Refuse IsExistIdByType(Integer ftype);

	/**根据允许用户ID查询允许用户的名称*/
	CL_Refuse getusername(String[] fvalues);
	
}
