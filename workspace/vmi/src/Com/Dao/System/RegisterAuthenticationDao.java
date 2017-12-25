package Com.Dao.System;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import Com.Base.Dao.BaseDao;
import Com.Base.Util.DJException;
import Com.Base.Util.ServerContext;
import Com.Base.Util.UploadFile;
import Com.Base.Util.params;
import Com.Base.Util.mySimpleUtil.lvcsmsrel.LVCSMSFacade;
import Com.Entity.System.Customer;
import Com.Entity.System.Mainmenuitem;
import Com.Entity.System.Productreqallocationrules;
import Com.Entity.System.Supplier;
import Com.Entity.System.SysUser;
import Com.Entity.System.UserCustomer;
import Com.Entity.System.UserRole;
import Com.Entity.System.UserSupplier;
import Com.Entity.System.Userdiary;
import Com.Entity.System.Useronline;
import Com.Entity.System.Userpermission;

@Service
public class RegisterAuthenticationDao extends BaseDao implements IRegisterAuthenticationDao {

	@Resource
	IBaseSysDao baseSysDao;


/**
 * 根据账号名称 创建客户与用户信息  用户信息表，客户信息表，用户关联客户表，用户登录信息，用户系统信息
 * @param name
 */
@Override
public void saveRegisterUserAndCustomerInfo(HttpServletRequest request) {
	String name=request.getParameter("name");
	Customer customer=new Customer();
	SysUser sysUser = new SysUser();
	sysUser.setFid(this.CreateUUid());
	sysUser.setFname(name);
	sysUser.setFcustomername(name);
//	sysUser.setFpassword(Md5(request.getParameter("lvc"));//密码后面赋值
	sysUser.setFpassword("cf79ae6addba60ad018347359bd144d2");	//8888
	sysUser.setFtel(name);
	sysUser.setFcreatetime(new Date());
	customer.setFid(this.CreateUUid());
	customer.setFnumber(ServerContext.getNumberHelper().getNumber("t_bd_customer", "KH", 3, false));
	customer.setFname(name);
	customer.setFphone(name);
	customer.setFcreatorid(sysUser.getFid());
	customer.setFlastupdateuserid(sysUser.getFid());
	customer.setFcreatetime(new Date());
	customer.setFlastupdatetime(new Date());
//	customer.setFisinvited(1);
	//同时生成制造商
	Supplier supplier=new Supplier();
	supplier.setFid(customer.getFid());
	supplier.setFnumber(customer.getFnumber());
	supplier.setFname(customer.getFname());//名称
	supplier.setFcreatetime(customer.getFcreatetime());
	supplier.setFcreatorid(customer.getFcreatorid());
	supplier.setFlastupdatetime(customer.getFlastupdatetime());
	supplier.setFlastupdateuserid(customer.getFlastupdateuserid());
	supplier.setFtel(customer.getFphone());
	
	sysUser.setFcustomerid(customer.getFid());//用户与客户的关联  判断客户是用户建的？？
	UserCustomer userCustomer = new UserCustomer();
	userCustomer.setFid(this.CreateUUid());
	userCustomer.setFcustomerid(customer.getFid());
	userCustomer.setFuserid(sysUser.getFid());
	//用户关联制造商
	UserSupplier userSupplier = new UserSupplier();
	userSupplier.setFid(this.CreateUUid());
	userSupplier.setFsupplierid(supplier.getFid());
	userSupplier.setFuserid(sysUser.getFid());
	
	Userpermission permiss=new Userpermission();//送货凭证
	permiss.setFid(this.CreateUUid());
	permiss.setFresourcesid("c445c5dd-f316-11e4-bd7e-00ff6b42e1e5");
	permiss.setFpath("VMI系统菜单!客户平台!送货凭证");
	permiss.setFuserid(sysUser.getFid());
	this.saveOrUpdate(permiss);
	
	if(!QueryExistsBySql("select fid from t_sys_userpermission  where fpath ='VMI系统菜单!制造商平台' and fuserid='030480f3-4645-11e5-8107-00ffa1e6e961'")){//角色中有没有上级目录就新增
		permiss=new Userpermission();//产品档案
		permiss.setFid(this.CreateUUid());
		permiss.setFresourcesid("0c1f437c-1f32-11e3-bcbc-60a44c5bbef3");
		permiss.setFpath("VMI系统菜单!制造商平台");
		permiss.setFuserid(sysUser.getFid());
		this.saveOrUpdate(permiss);
	}
	
	    permiss=new Userpermission();//产品档案
		permiss.setFid(this.CreateUUid());
		permiss.setFresourcesid("75f8d45f-0f20-11e5-9395-00ff61c9f2e3");
		permiss.setFpath("VMI系统菜单!制造商平台!产品档案");
		permiss.setFuserid(sysUser.getFid());
		this.saveOrUpdate(permiss);
	
	 permiss=new Userpermission();//客户管理
		permiss.setFid(this.CreateUUid());
		permiss.setFresourcesid("38961fcc-0dcf-11e5-9395-00ff61c9f2e3");
		permiss.setFpath("VMI系统菜单!制造商平台!客户管理");
		permiss.setFuserid(sysUser.getFid());
		this.saveOrUpdate(permiss);
		
		permiss = new Userpermission();//地址管理
		permiss.setFid(this.CreateUUid());
		permiss.setFresourcesid("8a5953bf-14ff-11e5-be46-00ff61c9f2e3");
		permiss.setFpath("VMI系统菜单!制造商平台!地址管理");
		permiss.setFuserid(sysUser.getFid());
		this.saveOrUpdate(permiss);

	UserRole userRole = new UserRole();
	userRole.setFid(this.CreateUUid());
	userRole.setFroleid("030480f3-4645-11e5-8107-00ffa1e6e961");	//分配角色 新注册用户  原角色保留给之前注册的
//	userRole.setFroleid("d3156f1a-3b1d-11e5-9a90-00ffa1e6e961");	//分配角色 初始权限注册
	userRole.setFuserid(sysUser.getFid());
	this.saveOrUpdate(userRole);
	this.saveOrUpdate(sysUser);
	this.saveOrUpdate(customer);
	this.saveOrUpdate(supplier);
	this.saveOrUpdate(userCustomer);
	this.saveOrUpdate(userSupplier);
	this.ExecBySql("update t_sys_user set fpassword=md5('"+request.getParameter("lvc")+"') where fid='"+sysUser.getFid()+"'");
	ExecloginAction(request,sysUser);
}

//用户登录信息，用户系统信息保存session
	private void ExecloginAction(HttpServletRequest request,SysUser sysUser){

		Useronline info = new Useronline();
		info.setFid(CreateUUid());
		info.setFuserid(sysUser.getFid());
		info.setFlogintime(new Date());
		info.setFlastoperatetime(new Date());
		info.setFusername(sysUser.getFname());
		info.setFsessionid(request.getSession().getId());
		info.setFip(request.getRemoteAddr());
		
		//关于用户的系统日志，
		Userdiary userdiaryinfo=new Userdiary();
		userdiaryinfo.setFid(CreateUUid());
		userdiaryinfo.setFusername(sysUser.getFname());
		userdiaryinfo.setFip(request.getRemoteAddr());
		userdiaryinfo.setFuserid(sysUser.getFid()); 
		userdiaryinfo.setFremark("登录成功");
		userdiaryinfo.setFsessionid(request.getSession().getId());
		userdiaryinfo.setFlogintime(new Date());
		userdiaryinfo.setFlastoperatetime(new Date());
		
		String headinfo = request.getHeader("User-Agent").toUpperCase();
		if(headinfo.indexOf("(")>=0)
		{
			String[] strInfo = headinfo.substring(
					headinfo.indexOf("(") + 1, headinfo.indexOf(")") - 1)
					.split(";");
			if ((headinfo.indexOf("MSIE")) > -1) {
				info.setFbrowser(strInfo[1].trim());
				info.setFsystem(strInfo[2].trim());
			} else {
				String[] str = headinfo.split(" ");
				if (headinfo.indexOf("NAVIGATOR") < 0
						&& headinfo.indexOf("FIREFOX") > -1) {
					info.setFbrowser(str[str.length - 1].trim());
					info.setFsystem(strInfo[0].trim());
				} else if ((headinfo.indexOf("OPERA")) > -1) {
					info.setFbrowser(str[0].trim());
					info.setFsystem(strInfo[0].trim());
				} else if (headinfo.indexOf("CHROME") < 0
						&& headinfo.indexOf("SAFARI") > -1) {
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
		}
		else
		{
			//没有括号的是IOS系统，直接赋值    BY   CC 2015-05-08
			info.setFbrowser("IOS Browser");
			info.setFsystem("IOS");
		}
		this.saveOrUpdate(info);
		this.saveOrUpdate(userdiaryinfo);
		request.getSession().setAttribute("Useronline", info);
	}

	@Override
	public void saveUserManageUser(SysUser userinfo,Customer customer,String sql) {
		// TODO Auto-generated method stub
		this.Update(userinfo);
		if(!StringUtils.isEmpty(sql)){
			this.ExecBySql(sql);
		}
		if(customer!=null) this.saveOrUpdate(customer);
	}

	
	/**
	 * 删除客户产品附件
	 */
	@Override
	public String ExecDelAuthenticationFile(String ids) {
		List<HashMap<String, Object>> list = null;
		String message = null,sql="";
		if(StringUtils.isEmpty(ids)) return null;
		for(String id : ids.split(",")){
				UploadFile.delFile(id);
				sql = "select fid from t_ord_productdemandfile where fbid = '"+id+"'";
				list = this.QueryBySql(sql);
				if(list.size()>0){
					UploadFile.delFile(list.get(0).get("fid")+"");
				}
		}
		return message;
	}

	@Override
	public String ExecDelFileByFparentid(String fparentid) {
		// TODO Auto-generated method stub
		String sql = "select fid from t_ord_productdemandfile where fparentid =:fparentid";
		String fidcls = null;
		params p = new params();
		p.put("fparentid", fparentid);
		List<HashMap<String, String>> list = this.QueryBySql(sql, p);
		for(HashMap<String, String> map : list){
			if(fidcls == null){
				fidcls = map.get("fid");
			}else{
				fidcls += ","+map.get("fid");
			}
		}
		return this.ExecDelAuthenticationFile(fidcls);
	}

	@Override
	public void saveCustomerAndFsupplier(Customer c) {
		// TODO Auto-generated method stub
		this.saveOrUpdate(c);//保存客户
		Supplier s=(Supplier)Query(Supplier.class, c.getFid());
		if(s!=null)
		{
			if(c.getFisinternalcompany()!=null&&c.getFisinternalcompany()==1){//个人认证，已认证
				s.setFname(c.getFname());//客户名称
				s.setFartificialperson(c.getFname());//法人代表
				s.setFbarcode(c.getFbarcode());//身份证号
				
			}else//企业认证
			{
				s.setFname(c.getFname());//客户名称
				s.setFartificialperson(c.getFartificialperson());//法人代表
				s.setFtaxregisterno(c.getFtxregisterno());//注册号
				s.setFtel(c.getFartificialpersonphone());//法人代表电话
			}
			this.saveOrUpdate(s);//保存制造商
			
		}
//现	认证后 不分配角色
//		//认证成功 分配角色
//		if(c.getFisinternalcompany()!=null&&c.getFisinternalcompany()==1){
//			
//			List<SysUser> syslist=this.QueryByHql("from SysUser  s where fcustomerid='"+c.getFid()+"'");
//			//用户认证通过，则关联着该客户的用户 增加认证角色
//			for(SysUser user : syslist){
//				UserRole userRole = new UserRole();
//				userRole.setFid(this.CreateUUid());
//				userRole.setFroleid("3db434cb-3b1e-11e5-9a90-00ffa1e6e961");	//分配角色 认证成功 账号分析
//				userRole.setFuserid(user.getFid());
//				this.saveOrUpdate(userRole);
//			}
//		}
		
	}

	
//	
//	@Override
//	public HashMap<String, Object> ExecSaveCustomerAndRole(Customer c) {
//		// TODO Auto-generated method stub
//		HashMap<String, Object> params = new HashMap<>();
//		if (c.getFid().isEmpty()) {
//			c.setFid(this.CreateUUid());
//		}
//		this.saveOrUpdate(c);
//		
//		List<SysUser> syslist=this.QueryByHql("from SysUser  s where fcustomerid='"+c.getFid()+"'");
//		if(c.getFisinternalcompany()!=null&&c.getFisinternalcompany()==1)//已认证
//		{
//			//用户认证通过，则关联着该客户的用户 增加认证角色
//			for(SysUser s : syslist){
//				UserRole userRole = new UserRole();
//				userRole.setFid(this.CreateUUid());
//				userRole.setFroleid("3db434cb-3b1e-11e5-9a90-00ffa1e6e961");	//分配角色 认证成功 账号分析
//				userRole.setFuserid(s.getFid());
//				this.saveOrUpdate(userRole);
//			}
//			
//		}else//认证为否，则取消分配角色
//		{
//			for(SysUser s : syslist){
//				this.ExecBySql(String.format("delete from t_sys_userrole where FUSERID='%s' and FROLEID='3db434cb-3b1e-11e5-9a90-00ffa1e6e961'",s.getFid()));
//			}
//		}
//		params.put("success", true);
//		return params;
//	}


	@Override
	public void ExecSaveUserpermission(String fid, String fischeck,String userid) {
		
		List<Mainmenuitem> menuitems=(List<Mainmenuitem>)this.QueryByHql(" from Mainmenuitem where fid in "+fid);
		List<HashMap<String, Object>> demandList = QueryBySql("select quote(froleid) userid from t_sys_userrole where fuserid='"+userid+"'");
			StringBuilder userids=new StringBuilder("'"+userid+"'");
			for(HashMap<String, Object> m : demandList){
				userids.append(","+m.get("userid"));
			}
		String	fparntPath="";
		for(Mainmenuitem item :menuitems){
			fparntPath=item.getFpath().substring(0,item.getFpath().lastIndexOf('!'));
			if(StringUtils.isEmpty(fparntPath)||"VMI系统菜单".equals(fparntPath)) throw new DJException("数据有误");
		if(QueryExistsBySql(String.format("select fid from t_sys_userpermission where fresourcesid='%s' and fuserid='%s'",item.getFid(),userid)))
		{
			if("false".equals(fischeck)){ 
				
				ExecBySql(String.format("delete from t_sys_userpermission where fresourcesid='%s' and fuserid='%s'",item.getFid(),userid));
				//注册角色中有或用户权限中有，则不新增上级目录
				if(!QueryExistsBySql(String.format("select fid from t_sys_userpermission  where fpath like'%s' and fuserid in( %s) and fresourcesid<>'%s'",fparntPath+"!%",userids,item.getFid())))
				ExecBySql(String.format("delete from t_sys_userpermission where fpath ='%s' and fuserid='%s'",fparntPath,userid));
			}

		}else
		{
			if("true".equals(fischeck)) {//注册角色中有或用户权限中有，则不新增上级目录
				if(!QueryExistsBySql(String.format("select fid from t_sys_userpermission where fresourcesid='%s' and fuserid in( %s )",item.getFparentid(),userids)))
				{
					Userpermission permission=new Userpermission();
					permission.setFuserid(userid);
					permission.setFresourcesid(item.getFparentid());
					permission.setFpath(fparntPath);
					permission.setFid(CreateUUid());
					this.saveOrUpdate(permission);
				}
				Userpermission permission=new Userpermission();
				permission.setFuserid(userid);
				permission.setFresourcesid(item.getFid());
				permission.setFpath(item.getFpath());
				permission.setFid(CreateUUid());
				this.saveOrUpdate(permission);
			}
		}
		}
	
		
	}
	
}
