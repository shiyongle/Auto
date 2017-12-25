package com.dao.user;

import java.util.List;
import java.util.Map;

import com.dao.IBaseDao;
import com.model.PageModel;
import com.model.system.Syscfg;
import com.model.user.TSysUser;

public interface TSysUserDao extends IBaseDao<TSysUser,java.lang.Integer> {
	
	public TSysUser login(String fname,String ftel, String fpassword);
	
	public TSysUser weblogin(String fname,String ftel, String fpassword,Integer type);
	/*** 用户名是否已被占用*/
	public boolean isUnique(String fname);
	
	/*** 通过账号和手机号获取对象*/
	public TSysUser getUser(String name,String tel);
	
	public List<TSysUser> getByName(String name);
	
	public List<Syscfg> getSyscfg(String supplierid);
	
	public TSysUser getByUserId(String userid);
	
	public PageModel<TSysUser> getSubByPid(String where,Object[] queryParams, Map<String, String> orderby, int pageNo,int maxResult);
	
	public boolean isPhoneUnique(String ftel,String fid);
	
	public boolean isNameUnique(String fname,String fid);
}
