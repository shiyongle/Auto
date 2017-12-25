package com.pc.dao.identification;

import java.util.List;

import cn.org.rapid_framework.page.Page;
import cn.org.rapid_framework.page.PageRequest;

import com.pc.dao.aBase.IBaseDao;
import com.pc.model.CL_FinanceStatement;
import com.pc.model.CL_Identification;

public interface IidentificationDao extends IBaseDao<CL_Identification, java.lang.Integer> {

	public Page<CL_Identification> findHuoPage(PageRequest pr);
	
	/***通过用户角色流水号和认证类型、获取认证信息*/
	public List<CL_Identification> getByUserRoleIdAndType(Integer userRoleId,Integer type);
	
	/***用于根据UserRoleId查询状态*/
	public List<CL_Identification> getStatusByUserRoleId(Integer userRoleId);
		
	public List<CL_Identification> getByUserRoleId(Integer userRoleId);

	/**删除  通过 认证表的 user_role_Id和 type  删除车主信息*  主要用于后台车辆管理删除*/
	public int deleteByRuleId(Integer userRoleId,Integer type);
	
	/**通过 认证表的user_role_id 倒序查询 用户信息 主要用于查询认证状态接口
	 */
    public List<CL_Identification>	getByUserRoleIdAndRz(Integer userRoleId);
	
	/**查询待审核的记录**/
	public List<CL_Identification> getIdentificationsByStatus(Integer type);

	/**通过用户角色号和认证状态获取认证信息 主要是用于货主跳过认证的时候*/
	public CL_Identification getByUserRoleIdAndStatus(Integer userRoleId,Integer status);
	
	/**
	 * 通过司机和客户的用户id删除认证
	 * @param userRoleId
	 * @return
	 */
	public int deleteByUserRoleId(Integer userRoleId);
	
	
}
