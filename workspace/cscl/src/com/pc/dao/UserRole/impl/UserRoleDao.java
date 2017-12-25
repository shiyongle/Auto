package com.pc.dao.UserRole.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import cn.org.rapid_framework.page.Page;
import cn.org.rapid_framework.page.PageRequest;

import com.pc.dao.UserRole.IUserRoleDao;
import com.pc.dao.aBase.impl.BaseDao;
import com.pc.model.CL_Feedback;
import com.pc.model.CL_Message;
import com.pc.model.CL_PayLogs;
import com.pc.model.CL_UserDiary;
import com.pc.model.CL_UserRole;
/*** wangc
 * 
 * */
@Service("userRoleDao")
public class UserRoleDao extends BaseDao<CL_UserRole,java.lang.Integer> implements IUserRoleDao{
	
	
	@Override
	public String getIbatisSqlMapNamespace() {
		return "CL_UserRole";
	}
	
	public String getFeedbackSqlMapNamespace() {
		return "CL_Feedback";
	}
	
	public String getUserDiarySqlMapNamespace(){
		return "CL_UserDiary";
	}
	
	public String getPayLogsSqlMapNamespace(){
		return "CL_PayLogs";
	}
	
	public List<CL_UserRole> getByVmiUserFid(String vmiUserFid){
		return this.getSqlSession().selectList(getIbatisSqlMapNamespace()+".getByVmiUserFid", vmiUserFid);
	}

	@Override
	public CL_UserRole  getByVmiUserFidAndRoleId(String vmiUserFid,Integer roleId) {
	  HashMap<String, Object> map=new HashMap<String,Object>();
	    map.put("vmiUserFid", vmiUserFid);
	    map.put("roleId", roleId);
		return  this.getSqlSession().selectOne(getIbatisSqlMapNamespace()+".getByVmiUserFidAndRoleId",map);
	}
	
	@Override
	public CL_UserRole  getByPhoneAndRoleId(String ftel,Integer roleId) {
		HashMap<String, Object> map=new HashMap<String,Object>();
		map.put("ftel", ftel);
		map.put("roleId", roleId);
		return  this.getSqlSession().selectOne(getIbatisSqlMapNamespace()+".getByPhoneAndRoleId",map);
	}

	@Override
	public List<Integer> getByVmiUserName(String searchKey) {
		return  this.getSqlSession().selectList(getIbatisSqlMapNamespace()+".getByVmiUserName",searchKey);
	}

	@Override
	public List<CL_UserRole> getByVmiUserNameAccurate(String vmiUserName) {
		return  this.getSqlSession().selectList(getIbatisSqlMapNamespace()+".getByVmiUserNameAccurate",vmiUserName);
	}

	@Override
	public List<CL_UserRole> getByRoleId(Integer roleId) {
		return this.getSqlSession().selectList(getIbatisSqlMapNamespace()+".getByRoleId",roleId);
	}
	
	/** 管理帐号所有子帐号查询*/
	@Override
	public Page<CL_UserRole> getByFparentid(PageRequest urQuery) {
		return pageQuery(getIbatisSqlMapNamespace()+".getByFparentid", urQuery);
	}
	
	/** 修改用户银行信息 仅司机	 */
	@Override
	public int updateAcount(CL_UserRole role) {
		return this.getSqlSession().update(getIbatisSqlMapNamespace()+".updateAcount",role);
	}
   
	@Override
	public int saveFeedback(CL_Feedback feedback) {
		// TODO Auto-generated method stub
		return this.getSqlSession().insert(getFeedbackSqlMapNamespace()+".insert",feedback);
	}

	/** 修改用户成单次数	 */
	public int updateTimes(CL_UserRole role) {
		// TODO Auto-generated method stub
		return this.getSqlSession().update(getIbatisSqlMapNamespace()+".updateTimes",role);
	}
	
	/** 修改用户协议标记 */
	@Override
	public int updateProtocol(CL_UserRole role) {
		// TODO Auto-generated method stub
		return this.getSqlSession().update(getIbatisSqlMapNamespace()+".updateProtocol",role);
	}
	
	/** 修改用户认证标记 */
	@Override
	public int updateIsPassIdentify(CL_UserRole role) {
		// TODO Auto-generated method stub
		return this.getSqlSession().update(getIbatisSqlMapNamespace()+".updateIsPassIdentify",role);
	}
	
	/**货主下拉表数据源**/
	@Override
	public List<Map<String, Object>> getComboxGridData(String searchKey) {
		// TODO Auto-generated method stub
		Map map = new HashMap();
		map.put("searchKey", searchKey);
		return this.getSqlSession().selectList(getIbatisSqlMapNamespace()+".getComboxGridData",map);
	}

	@Override
	public List<CL_UserRole> getByFpassword(String vmiUserName, String fpassword) {
		// TODO Auto-generated method stub
		Map map = new HashMap();
		map.put("vmiUserName", vmiUserName);
		map.put("fpassword", fpassword);
		return  this.getSqlSession().selectList(getIbatisSqlMapNamespace()+".getByFpassword",map);
	}

	/** 子帐号手机号唯一校验*/
	@Override
	public List<CL_UserRole> getByPhone(String vmiUserPhone) {
		// TODO Auto-generated method stub
		return this.getSqlSession().selectList(getIbatisSqlMapNamespace()+".getByPhone", vmiUserPhone);
	}

	/** 子帐号修改	 */
	@Override
	public int updateSubNum(CL_UserRole role) {
		// TODO Auto-generated method stub
		return this.getSqlSession().update(getIbatisSqlMapNamespace()+".updateSubNum",role);
	}

	/** 子帐号删除	 */
	@Override
	public int deleteSubNum(CL_UserRole role) {
		// TODO Auto-generated method stub
		return this.getSqlSession().update(getIbatisSqlMapNamespace()+".deleteSubNum",role);
	}

	@Override
	public List<CL_UserRole> getByFparentidAndSub(Integer fparentid, Integer id) {
		// TODO Auto-generated method stub
		Map map = new HashMap();
		map.put("fparentid", fparentid);
		map.put("id", id);
		return this.getSqlSession().selectList(getIbatisSqlMapNamespace()+".getByFparentidAndSub", map);
	}

	@Override
	public Page<CL_UserRole> findPage_cusname(PageRequest query) {
		// TODO Auto-generated method stub
		return pageQuery(getIbatisSqlMapNamespace()+".findPage_cusname", query);
	}

	@Override
	public int saveUserDiary(CL_UserDiary userDiary) {
		// TODO Auto-generated method stub
		return this.getSqlSession().insert(getUserDiarySqlMapNamespace()+".insert",userDiary);
	}

	@Override
	public int savePayLogs(CL_PayLogs payLogs) {
		// TODO Auto-generated method stub
		return this.getSqlSession().insert(getPayLogsSqlMapNamespace()+".insert",payLogs);
	}
	
	
}
