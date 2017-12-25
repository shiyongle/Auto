package com.pc.dao.UserRole;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import cn.org.rapid_framework.page.Page;
import cn.org.rapid_framework.page.PageRequest;

import com.pc.dao.aBase.IBaseDao;
import com.pc.model.CL_Feedback;
import com.pc.model.CL_PayLogs;
import com.pc.model.CL_UserDiary;
import com.pc.model.CL_UserRole;
/***
 * wangc 
 * */
public interface IUserRoleDao extends IBaseDao<CL_UserRole, java.lang.Integer>{
	
	/***通过vmi数据库中的用户表主键获取cscl数据库中用户角色信息*/
	public List<CL_UserRole> getByVmiUserFid(String vmiUserFid);
	
	/***通过vmiUserId 和roleId 获取cscl的数据中用户角色信息*/
	public  CL_UserRole  getByVmiUserFidAndRoleId(String vmiUserFid,Integer roleId);

	/***通过vmiUserId 和roleId 获取cscl的数据中用户角色信息*/
	public  CL_UserRole  getByPhoneAndRoleId(String ftel,Integer roleId);
	
	/***通过vmiUserName 获得 cscl的数据库中 id*/
	public List<Integer> getByVmiUserName(String searchKey);
	
	/***通过vmiUserName 精确获取用户角色信息*/
	public List<CL_UserRole> getByVmiUserNameAccurate (String vmiUserName);
	
	
	/***根据userRoleId 获取用户信息
	 */
	public List<CL_UserRole> getByRoleId (Integer roleId);
	
	/** 修改用户银行信息 仅司机	 */
	public int updateAcount(CL_UserRole role);
	
	/** 新增用户反馈	 */
	public int saveFeedback(CL_Feedback feedback);
	
	/** 修改用户成单次数	 */
	public int updateTimes(CL_UserRole role);
	
	/** 修改用户协议标记 */
	public int updateProtocol(CL_UserRole role);
	
	/**货主下拉表数据源**/
	public List<Map<String, Object>> getComboxGridData(String searchKey);

	/** 修改用户认证标记 */
	public int updateIsPassIdentify(CL_UserRole role);

	/** 管理帐号查询所有子帐号 */
	public Page<CL_UserRole> getByFparentid(PageRequest UserRoleQuery);
	
	/** 子帐号登录校验 */
	public List<CL_UserRole> getByFpassword(String vmiUserName,String fpassword);
	
	/** 子帐号手机号唯一校验*/
	public List<CL_UserRole> getByPhone(String vmiUserPhone);
	
	/** 子帐号修改*/
	public int updateSubNum(CL_UserRole role);

	/** 子帐号删除*/
	public int deleteSubNum(CL_UserRole role);
	
	/** 通过管理帐号及子帐号查询*/
	public List<CL_UserRole> getByFparentidAndSub(Integer parentid,Integer id);
	
	/** 客户余额明细用*/
	public Page<CL_UserRole> findPage_cusname(PageRequest query);
	
	/**
	 * 新增用户日志
	 * @param userDiary
	 * @return
	 */
	public int saveUserDiary(CL_UserDiary userDiary);

	/**
	 * 新增pay日志
	 * @param userDiary
	 * @return
	 */
	public int savePayLogs(CL_PayLogs payLogs);
}