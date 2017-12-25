package com.service.user;

import java.util.List;
import java.util.Map;

import com.model.PageModel;
import com.model.system.Syscfg;
import com.model.user.TSysUser;
import com.service.IBaseManager;

public interface TSysUserManager extends IBaseManager<TSysUser, java.lang.Integer>{
	
	public TSysUser login(String fname, String ftel,String fpassword);
	
	public TSysUser weblogin(String fname, String ftel,String fpassword,Integer type);
	
	public boolean isUnique(String fname);
	
	/*** 通过账号和手机号获取对象*/
	public TSysUser getUser(String name,String tel);
	/*** 手机号是否已被占用*/
	public boolean isExistsForTel(String tel);
	
	/***注册新增用户信息-自动产生客户,制造商信息*/
	public void saveImpl(TSysUser user);
	
	public void setpwd(String pwd,String fid);
	
	/***在线设计-未登录时*/
	public TSysUser insertLineImpl(String yzm,String phone,String linkman);
	public void updateSupplierAndCustomerName(String fid,String fname);

	public List<Syscfg> getSuppliserCfg(String userid);

	int getSupplierCardAutoReceiveCfg(String supplierid);

	Syscfg getSuppliserCfg(String supplierid, String fkey);

	String getUserParam(String userid, String key);
	
	public List<TSysUser> getByName(String name);
	
	public TSysUser getByUserId(String userid);
	
	public void saveSubAccount(TSysUser user);
	
	public PageModel<TSysUser> getSubByPid(String where,Object[] queryParams, Map<String, String> orderby, int pageNo,int maxResult);

	public boolean isPhoneUnique(String ftel,String fid);
	
	public boolean isNameUnique(String fname,String fid);
}
