package com.service.user;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.dao.IBaseDao;
import com.dao.customer.CustomerDao;
import com.dao.supplier.SupplierDao;
import com.dao.user.TSysUserDao;
import com.dao.userCustomer.UserCustomerDao;
import com.dao.userSupplier.TbdUsersupplierDao;
import com.dao.userdiary.TsysUserdiaryDao;
import com.dao.useronline.TsysUseronlineDao;
import com.dao.userpermission.TsysUserpermissionDao;
import com.dao.userrole.TsysUserroleDao;
import com.model.PageModel;
import com.model.customer.Customer;
import com.model.supplier.Supplier;
import com.model.system.Syscfg;
import com.model.user.TSysUser;
import com.model.userCustomer.UserCustomer;
import com.model.userSupplier.UserSupplier;
import com.model.userdiary.Userdiary;
import com.model.useronline.Useronline;
import com.model.userpermission.Userpermission;
import com.model.userrole.UserRole;
import com.service.IBaseManagerImpl;
import com.util.Constant;
import com.util.MD5Util;
import com.util.Params;

@SuppressWarnings("unchecked")
@Service("userManager")
@Transactional
public  class TSysUserManagerImpl extends IBaseManagerImpl<TSysUser,java.lang.Integer> implements TSysUserManager  {
	
	@Autowired
	private TSysUserDao userDao;
	@Autowired
	private UserCustomerDao userCustomerDao;
	@Autowired
	private CustomerDao customerDao;
	@Autowired
	private SupplierDao supplierDao;
	@Autowired
	private TsysUserpermissionDao tsysUserpermissionDao;
	@Autowired
	private TbdUsersupplierDao tbdUsersupplierDao;
	@Autowired
	private TsysUserroleDao tsysUserroleDao;
	@Autowired
	private TsysUseronlineDao tsysUseronlineDao;
	@Autowired
	private TsysUserdiaryDao tsysUserdiaryDao;
	
	@Override
	protected IBaseDao<TSysUser, java.lang.Integer> getEntityDao() {
		return this.userDao;
	}
	
	@Override
	@Transactional(readOnly=true)
	public TSysUser login(String fname,String ftel, String fpassword) {
		return this.userDao.login(fname, ftel,fpassword);
	}
	
	@Override
	@Transactional(readOnly=true)
	public TSysUser weblogin(String fname,String ftel, String fpassword,Integer type) {
		return this.userDao.weblogin(fname, ftel,fpassword,type);
	}
	
	@Override
	@Transactional(propagation=Propagation.NOT_SUPPORTED,readOnly=true)
	public boolean isUnique(String fname) {
		return this.userDao.isUnique(fname);
	}

	@Override
	@Transactional(propagation=Propagation.NOT_SUPPORTED,readOnly=true)
	public TSysUser getUser(String name, String tel) {
		return this.userDao.getUser(name, tel);
	}
	
	/*** 手机号是否已被占用*/
	@Override
	@Transactional(readOnly=true)
	public boolean isExistsForTel(String tel){
		String hql = String.format(" select fid from TSysUser where fname = '%s' or ftel='%s'", tel,tel);
		List<TSysUser> fids = this.getEntityDao().findByHql(hql);
		if(fids.size()>0){
			return true;
		}else{
			return false;
		}
	}
	

	/***注册新增用户信息-自动产生客户,制造商信息*/
	@Override
	public void saveImpl(TSysUser user) {
		this.userDao.save(user);
		String number =this.customerDao.getNumber("t_bd_customer", "KH", 3, false);
		/*** 自动产生客户信息*/
		Customer customer = new  Customer();
			customer.setFid(this.userDao.CreateUUid());
			customer.setFname(user.getFname());
			customer.setFcreatorid(user.getFid());
			customer.setFcreatetime(new Date());
			customer.setFlastupdateuserid(user.getFid());
			customer.setFlastupdatetime(new Date());
			customer.setFphone(user.getFtel());
			customer.setFnumber(number);
		this.customerDao.save(customer);
		/*** 自动产生用户客户信息*/
		UserCustomer uc =new UserCustomer();
			uc.setFid(this.userDao.CreateUUid());
			uc.setFcustomerid(customer.getFid());
			uc.setFuserid(user.getFid());
		this.userCustomerDao.save(uc);
		/*** 自动产生制造商信息*/
		Supplier supplier = new Supplier();
			supplier.setFid(this.userDao.CreateUUid());
			supplier.setFname(user.getFname());
			supplier.setFcreatorid(user.getFid());
			supplier.setFcreatetime(new Date());
			supplier.setFlastupdateuserid(user.getFid());
			supplier.setFlastupdatetime(new Date());
			supplier.setFtel(user.getFtel());
			supplier.setFnumber(number);
		this.supplierDao.save(supplier);
		/*** 用户关联制造商*/
		UserSupplier userSupplier = new UserSupplier();
			userSupplier.setFid(this.CreateUUid());
			userSupplier.setFsupplierid(supplier.getFid());
			userSupplier.setFuserid(user.getFid());
		this.tbdUsersupplierDao.save(userSupplier);
		/*** 默认先分配东经制造商*/
		String insertSql = " INSERT INTO t_pdt_productreqallocationrules (fid,fcustomerid,fsupplierid,fisstock,fbacthstock) VALUES( ";
		insertSql += " '"+this.userDao.CreateUUid()+"', '"+uc.getFcustomerid()+"' ,'39gW7X9mRcWoSwsNJhU12TfGffw=',1,0) ";
		this.ExecBySql(insertSql);
		/*** 权限信息*/
		Userpermission permiss=new Userpermission();//送货凭证
		permiss.setFid(this.CreateUUid());
		permiss.setFresourcesid("c445c5dd-f316-11e4-bd7e-00ff6b42e1e5");
		permiss.setFpath("VMI系统菜单!客户平台!送货凭证");
		permiss.setFuserid(user.getFid());
		this.tsysUserpermissionDao.saveOrUpdate(permiss);
		
		if(!QueryExistsBySql("select fid from t_sys_userpermission  where fpath ='VMI系统菜单!制造商平台' and fuserid='030480f3-4645-11e5-8107-00ffa1e6e961'")){//角色中有没有上级目录就新增
			permiss=new Userpermission();//产品档案
			permiss.setFid(this.CreateUUid());
			permiss.setFresourcesid("0c1f437c-1f32-11e3-bcbc-60a44c5bbef3");
			permiss.setFpath("VMI系统菜单!制造商平台");
			permiss.setFuserid(user.getFid());
			this.tsysUserpermissionDao.saveOrUpdate(permiss);
		}
		permiss=new Userpermission();//产品档案
			permiss.setFid(this.CreateUUid());
			permiss.setFresourcesid("75f8d45f-0f20-11e5-9395-00ff61c9f2e3");
			permiss.setFpath("VMI系统菜单!制造商平台!产品档案");
			permiss.setFuserid(user.getFid());
		this.tsysUserpermissionDao.saveOrUpdate(permiss);
		
		permiss=new Userpermission();//客户管理
			permiss.setFid(this.CreateUUid());
			permiss.setFresourcesid("38961fcc-0dcf-11e5-9395-00ff61c9f2e3");
			permiss.setFpath("VMI系统菜单!制造商平台!客户管理");
			permiss.setFuserid(user.getFid());
		this.tsysUserpermissionDao.saveOrUpdate(permiss);
		permiss = new Userpermission();//地址管理
			permiss.setFid(this.CreateUUid());
			permiss.setFresourcesid("8a5953bf-14ff-11e5-be46-00ff61c9f2e3");
			permiss.setFpath("VMI系统菜单!制造商平台!地址管理");
			permiss.setFuserid(user.getFid());
		this.tsysUserpermissionDao.saveOrUpdate(permiss);
		UserRole userRole = new UserRole();
		userRole.setFid(this.CreateUUid());
		userRole.setFroleid("030480f3-4645-11e5-8107-00ffa1e6e961");	//分配角色 新注册用户  原角色保留给之前注册的
		//userRole.setFroleid("d3156f1a-3b1d-11e5-9a90-00ffa1e6e961");	//分配角色 初始权限注册
		userRole.setFuserid(user.getFid());
		this.tsysUserroleDao.saveOrUpdate(userRole);
		//模拟登陆
		if(uc !=null){
			ServletActionContext.getRequest().getSession().setAttribute(Constant.SESSION_USER_CUSTOMERID, uc.getFcustomerid());
		}
		ExecloginAction(user);
	}
	
	/*** 用户登录信息，用户系统信息保存session*/
	private void ExecloginAction(TSysUser sysUser){
			Useronline info = new Useronline();
			info.setFid(CreateUUid());
			info.setFuserid(sysUser.getFid());
			info.setFlogintime(new Date());
			info.setFlastoperatetime(new Date());
			info.setFusername(sysUser.getFname());
			info.setFsessionid(ServletActionContext.getRequest().getSession().getId());
			info.setFip(ServletActionContext.getRequest().getRemoteAddr());
			//关于用户的系统日志，
			Userdiary userdiaryinfo=new Userdiary();
			userdiaryinfo.setFid(CreateUUid());
			userdiaryinfo.setFusername(sysUser.getFname());
			userdiaryinfo.setFip(ServletActionContext.getRequest().getRemoteAddr());
			userdiaryinfo.setFuserid(sysUser.getFid()); 
			userdiaryinfo.setFremark("登录成功");
			userdiaryinfo.setFsessionid(ServletActionContext.getRequest().getSession().getId());
			userdiaryinfo.setFlogintime(new Date());
			userdiaryinfo.setFlastoperatetime(new Date());
			String headinfo = ServletActionContext.getRequest().getHeader("User-Agent").toUpperCase();
			if(headinfo.indexOf("(")>=0){
				String[] strInfo = headinfo.substring(headinfo.indexOf("(") + 1, headinfo.indexOf(")") - 1).split(";");
				if ((headinfo.indexOf("MSIE")) > -1) {
					info.setFbrowser(strInfo[1].trim());
					info.setFsystem(strInfo[2].trim());
				} else {
					String[] str = headinfo.split(" ");
					if (headinfo.indexOf("NAVIGATOR") < 0 && headinfo.indexOf("FIREFOX") > -1) {
						info.setFbrowser(str[str.length - 1].trim());
						info.setFsystem(strInfo[0].trim());
					} else if ((headinfo.indexOf("OPERA")) > -1) {
						info.setFbrowser(str[0].trim());
						info.setFsystem(strInfo[0].trim());
					} else if (headinfo.indexOf("CHROME") < 0 && headinfo.indexOf("SAFARI") > -1) {
						info.setFbrowser(str[str.length - 1].trim());
						info.setFsystem(strInfo[0].trim());
					} else if (headinfo.indexOf("CHROME") > -1) {
						info.setFbrowser(str[str.length - 2].trim());
						info.setFsystem(strInfo[0].trim());
					} else if (headinfo.indexOf("NAVIGATOR") > -1) {
						info.setFbrowser(str[str.length - 1].trim());
						info.setFsystem(strInfo[0].trim());
					} else {
						info.setFbrowser("Unknown Browser");
						info.setFsystem("Unknown OS");
					}
				}
			}else{
				//没有括号的是IOS系统，直接赋值    BY   CC 2015-05-08
				info.setFbrowser("IOS Browser");
				info.setFsystem("IOS");
			}
			this.tsysUseronlineDao.saveOrUpdate(info);
			this.tsysUserdiaryDao.saveOrUpdate(userdiaryinfo);
		}


	@Override
	public void setpwd(String pwd,String fid) {
		this.userDao.ExecBySql("update t_sys_user set fpassword=md5('"+pwd+"') where fid='"+fid+"'");
	}


	@Override
	public TSysUser insertLineImpl(String yzm, String phone,String linkman) {
		TSysUser user = new TSysUser();
		 	user.setFid(this.userDao.CreateUUid());
		 	user.setFname(phone);
		 	user.setFpassword(MD5Util.getMD5String(yzm));
		 	user.setFtel(phone);
		 	user.setFtype(0);
		 	user.setFcreatetime(new Date());
		 	user.setFcustomername("");
		 	user.setFemail("");
		 	user.setFeffect(0);
		 	user.setFimid(null);
		this.saveImpl(user); 	
		
		/*this.userDao.save(user);
		String number =this.customerDao.getNumber("t_bd_customer", "KH", 3, false);
		*//*** 自动产生客户信息*//*
		Customer customer = new  Customer();
			customer.setFid(this.userDao.CreateUUid());
			customer.setFname(user.getFname());
			customer.setFcreatorid(user.getFid());
			customer.setFcreatetime(new Date());
			customer.setFlastupdateuserid(user.getFid());
			customer.setFlastupdatetime(new Date());
			customer.setFphone(user.getFtel());
			customer.setFnumber(number);
		this.customerDao.save(customer);
		*//*** 自动产生用户客户信息*//*
		UserCustomer uc =new UserCustomer();
			uc.setFid(this.userDao.CreateUUid());
			uc.setFcustomerid(customer.getFid());
			uc.setFuserid(user.getFid());
		this.userCustomerDao.save(uc);*/
		return user;
	}


	@Override
	public void updateSupplierAndCustomerName(String fid,String fname) {
		// TODO Auto-generated method stub
		Params p = new Params();
		List<HashMap<String,Object>> clist = this.QueryBySql("select fcustomerid from t_bd_usercustomer where fuserid='"+fid+"'", p);
		List<HashMap<String,Object>> slist = this.QueryBySql("select fsupplierid from t_bd_usersupplier where fuserid='"+fid+"'", p);
		if(clist.size()==1){
			Customer c = customerDao.get(clist.get(0).get("fcustomerid").toString());
			c.setFname(fname);
			this.update(c);
		}
		if(slist.size()==1){
			Supplier s = supplierDao.get(slist.get(0).get("fsupplierid").toString());
			s.setFname(fname);
			this.update(s);
		}
	}

	/**
	 * 获取某个制造商的所有配置
	 */
	@Override
	public List<Syscfg> getSuppliserCfg(String supplierid) {
		
		return userDao.getSyscfg(supplierid);
//		return userDao.QueryByHql("from Syscfg where fuser = '"+supplierid+"'");
	}
	@Override
	public Syscfg getSuppliserCfg(String supplierid,String fkey){
		List<Syscfg> list = getSuppliserCfg(supplierid);
		for(Syscfg obj: list){
			if(fkey.equals(obj.getFkey())){
				return obj;
			}
		}
		return null;
	}
	/**
	 * 查询制造商是不是自动接收订单
	 * 0- 手动接收 （默认值）
	 * 1-自动接收
	 */
	@Override
	public int getSupplierCardAutoReceiveCfg(String supplierid){
		int config = 0;
		List<Syscfg> list = getSuppliserCfg(supplierid);
		for(Syscfg syscfg: list){
			if("card_auto_receive".equals(syscfg.getFkey()) && "1".equals(syscfg.getFvalue())){
				config = 1;
			}
		}
		return config;
	}
	@Override
	public String getUserParam(String userid,String key){
		String hql = "from Syscfg where fuser='"+userid+"' and fkey = '"+key+"'";
		List<Syscfg> list = (List<Syscfg>) userDao.QueryByHql(hql);
		if(list.size()!=0){
			return list.get(0).getFvalue();
		}else{
			return null;
		}
	}


	@Override
	public List<TSysUser> getByName(String name) {
		return userDao.getByName(name);
	}
	
	@Override
	public TSysUser getByUserId(String userid) {
		return userDao.getByUserId(userid);
	}
	
	/**
	 * 新增子账户信息
	 * 自动产生客户,制造商信息
	 */
	public void saveSubAccount(TSysUser user)
	{
		if(StringUtils.isEmpty(user.getFid())){//新增
			user.setFid(this.CreateUUid());
			this.userDao.save(user);
			String parentid=ServletActionContext.getRequest().getSession().getAttribute(Constant.SESSION_USERID).toString();
			/*** 找到父账户关联的客户信息*/
			Object[] queryParams=new Object[]{parentid};
			List<UserCustomer> puclist = this.userCustomerDao.findByHql(" from UserCustomer where fuserid=?",queryParams);
			//List<Customer> customer =  this.customerDao.findByHql("from Customer where fcreatorid=?", queryParams);
			if(puclist.size()!=0)
			{
				UserCustomer ucinfo=puclist.get(0);
				/*** 将父账户的客户信息自动与子账户进行绑定*/
				UserCustomer uc =new UserCustomer();
			    uc.setFid(this.userDao.CreateUUid());
				uc.setFcustomerid(ucinfo.getFcustomerid());
				uc.setFuserid(user.getFid());
				this.userCustomerDao.save(uc);
			}
			/*** 找到父账户关联的制造商信息*/
			List<UserSupplier> pusList = tbdUsersupplierDao.findByHql(" from UserSupplier where fuserid=?",queryParams);
			//List<Supplier> supplier = this.supplierDao.findByHql("from Supplier where fcreatorid=?", queryParams);
			if(pusList.size()!=0)
			{
				UserSupplier usinfo=pusList.get(0);
				/*** 将父账户关联的制造商自动与子账户进行绑定*/
				UserSupplier userSupplier = new UserSupplier();
				userSupplier.setFid(this.CreateUUid());
				userSupplier.setFsupplierid(usinfo.getFsupplierid());
				userSupplier.setFuserid(user.getFid());
				this.tbdUsersupplierDao.save(userSupplier);
			}
		}
	}

	/**
	 * param:当前登录的Id
	 * return:当前用户下的子账户
	 */
	@Override
	public PageModel<TSysUser> getSubByPid(String where,Object[] queryParams, Map<String, String> orderby, int pageNo,int maxResult) {
		return userDao.getSubByPid(where,queryParams,orderby,pageNo,maxResult);
	}

	@Override
	public boolean isPhoneUnique(String ftel,String fid){
		return userDao.isPhoneUnique(ftel,fid);
	}
	
	@Override
	public boolean isNameUnique(String fname,String fid){
		return userDao.isNameUnique(fname,fid);
	}

}
