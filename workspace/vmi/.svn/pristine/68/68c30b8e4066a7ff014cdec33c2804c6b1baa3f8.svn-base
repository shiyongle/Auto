package Com.Dao.System;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import Com.Base.Dao.BaseDao;
import Com.Base.Util.DJException;
import Com.Base.Util.ServerContext;
import Com.Base.Util.mySimpleUtil.lvcsmsrel.LVCSMSFacade;
import Com.Entity.System.Customer;
import Com.Entity.System.Productreqallocationrules;
import Com.Entity.System.SysUser;
import Com.Entity.System.UserCustomer;
import Com.Entity.System.UserRole;
import Com.Entity.System.Userdiary;
import Com.Entity.System.Useronline;

@Service
public class SupCustomerInfoDao extends BaseDao implements ISupCustomerInfoDao {

	@Resource
	IBaseSysDao baseSysDao;
	@Override
	public void saveCustomerInfo(Customer customer, String userid) {
		Date now = new Date();
		String hql = "",updatehql="";
		if(StringUtils.isEmpty(customer.getFid())){
			if(this.QueryExistsBySql("select 1 from t_bd_customer where fname='"+customer.getFname()+"'")){
				throw new DJException("当前客户名称已存在，请更改！"); 
			}
			String fsupplierid = baseSysDao.getCurrentSupplierid(userid);
			customer.setFid(this.CreateUUid());
			customer.setFcreatorid(userid);
			customer.setFnumber(ServerContext.getNumberHelper().getNumber("t_bd_customer", "KH", 3, false));
			customer.setFlastupdateuserid(userid);
			customer.setFcreatetime(now);
			customer.setFlastupdatetime(now);
			customer.setFisinvited(0);
			Productreqallocationrules rules = new Productreqallocationrules();
			rules.setFid(this.CreateUUid());
			rules.setFcustomerid(customer.getFid());
			rules.setFsupplierid(fsupplierid);
			rules.setFcreatorid(userid);
			rules.setFcreatetime(now);
			/*if(this.QueryExistsBySql("select 1 from t_sys_user where fname='"+customer.getFphone()+"'")){
				throw new DJException("此手机号已存在，请更改！");
			}*/
			this.saveOrUpdate(customer);
			this.saveOrUpdate(rules);
		}else {
			
//			if(!userid.equals(custObj.getFcreatorid())){
//				throw new DJException("此客户信息不是您创建，无法更改！");
//			}
//			custObj.setFname(customer.getFname());
//			custObj.setFphone(customer.getFphone());
//			custObj.setFdescription(customer.getFdescription());
			customer.setFlastupdatetime(now);
			customer.setFlastupdateuserid(userid);
			this.saveOrUpdate(customer);
		}
		/*SysUser sysUser = null;
		List<SysUser> sysUserList =null;
		hql = "from SysUser where fname='"+customer.getFphone()+"' or ftel ='"+customer.getFphone()+"'";
		sysUserList = this.QueryByHql(hql);
		if(fisupdate==1 && sysUserList.size()==0){
			List<SysUser> updatesysUserList = this.QueryByHql(updatehql);
			if(updatesysUserList.size()>0){//要替换手机号则把用户名称，电话替换。
			sysUser=updatesysUserList.get(0);
			sysUser.setFtel(customer.getFphone());
			sysUser.setFname(customer.getFphone());
			this.saveOrUpdate(sysUser);
			}else //未创建账号，则新建
			{
				fisupdate=0;
			}
		}
		if(fisupdate==0){
		if(sysUserList.size()==0){		//如果用户已存在，要么是编辑操作，要么此用户已关联其它客户，为异常操作，不作处理。
			sysUser = new SysUser();
			sysUser.setFid(this.CreateUUid());
			sysUser.setFname(customer.getFphone());
			sysUser.setFpassword("21218cca77804d2ba1922c33e0151105");	//888888
			sysUser.setFcustomername(customer.getFname());
			sysUser.setFtel(customer.getFphone());
			sysUser.setFcreatetime(now);
			this.saveOrUpdate(sysUser);
			UserCustomer userCustomer = new UserCustomer();
			userCustomer.setFid(this.CreateUUid());
			userCustomer.setFcustomerid(customer.getFid());
			userCustomer.setFuserid(sysUser.getFid());
			UserRole userRole = new UserRole();
			userRole.setFid(this.CreateUUid());
			userRole.setFroleid("77ef3ba0-0db4-11e5-9395-00ff61c9f2e3");	//终端客户
			userRole.setFuserid(sysUser.getFid());
			this.saveOrUpdate(userCustomer);
			this.saveOrUpdate(userRole);
			customer.setFisinvited(0);
			this.saveOrUpdate(customer);
		}
		}*/
	}
	@Override
	public String ExecSendMessageForCustomers(String fidcls,String userid,String way) throws RemoteException {
		String fsupplierName = baseSysDao.getStringValue("t_sys_user", userid, "fcustomername");
		SysUser sysUser = null;
		UserCustomer userCustomer;
		UserRole userRole;
		int size;
		List<SysUser> users = new ArrayList<>();
		for(String fid : fidcls.split(",")){
			Customer customer = (Customer) this.Query(Customer.class, fid);
			String hql = "from SysUser where fcustomerid = '"+fid+"'";
			List<SysUser> sysUserList = this.QueryByHql(hql);
			size = sysUserList.size();
			if(size==0){		//如果用户已存在，要么是编辑操作，要么此用户已关联其它客户，为异常操作，不作处理。
				sysUser = new SysUser();
				sysUser.setFid(this.CreateUUid());
//				if(StringUtils.isEmpty(customer.getFphone())){
					sysUser.setFname(customer.getFname());
//				}else{
//					sysUser.setFname(customer.getFphone());
//				}
				sysUser.setFpassword("cf79ae6addba60ad018347359bd144d2");	//8888
				sysUser.setFcustomerid(fid);
				sysUser.setFcustomername(customer.getFname());
				sysUser.setFtel(customer.getFphone());
				sysUser.setFcreatetime(new Date());
				sysUser.setCustomer(customer);
				userCustomer = new UserCustomer();
				userCustomer.setFid(this.CreateUUid());
				userCustomer.setFcustomerid(customer.getFid());
				userCustomer.setFuserid(sysUser.getFid());
				userRole = new UserRole();
				userRole.setFid(this.CreateUUid());
				userRole.setFroleid("77ef3ba0-0db4-11e5-9395-00ff61c9f2e3");	//终端客户
				userRole.setFuserid(sysUser.getFid());
				this.saveOrUpdate(userCustomer);
				this.saveOrUpdate(userRole);
			}else if(size==1){
				sysUser = sysUserList.get(0);
				if(StringUtils.isEmpty(sysUser.getFtel())){
					sysUser.setFtel(customer.getFphone());
				}
			}
			try {
				this.saveOrUpdate(sysUser);
			} catch (Exception e) {
				throw new DJException("帐号"+sysUser.getFname()+"已存在，无法在平台创建用户");
			}
			users.add(sysUser);
		}
		String message;
		String msg = "您好！我司已升级了接单系统，请登录 www.olcps.com ，您的帐号：%s ，密码：8888。\r\n";
		if(!StringUtils.isEmpty(way)){
			switch (way) {
			case "1":
				msg += "网址http://www.olcps.com";
				break;
			case "2":
				msg += "安卓版点击http://www.olcps.com/android/indexcps.html";
				break;
			case "3":
				msg += "苹果版点击http://t.cn/R2nUgF1";
				break;
			default:
				break;
			}
			msg += "【"+fsupplierName+"】";
			for(SysUser user: users){
				if(user.getFstate()==1 && !StringUtils.isEmpty(user.getCustomer().getFphone())){
					try {
						message = String.format(msg, user.getFname());
						LVCSMSFacade.sendMessage(message, user.getCustomer().getFphone());
						this.ExecBySql("update t_bd_customer set fisinvited=1 where fid='"+user.getFcustomerid()+"'");
					} catch (RemoteException e) {
						throw new DJException("发送短信失败！");
					}
				}
			}
		}else{	//邀请信息的操作,只能操作一位客户
			if(users.size()>0){
				this.ExecBySql("update t_bd_customer set fisinvited=1 where fid='"+users.get(0).getFcustomerid()+"'");
			}
		}
		if(users.size()>0){
			return users.get(0).getFname() +"_"+fsupplierName;
		}else{
			return null;
		}
	}

@Override
	public void ExecdeleteForCustomers(String fidcls,String userid)
	{
		
		List<Customer> list=this.QueryByHql(" from  Customer where fid in" +fidcls);
		String supplierid = baseSysDao.getCurrentSupplierid(userid);
		Customer c=null;
		List<SysUser> u=null;
		for(int i=0;i<list.size();i++)
		{
			c=list.get(i);
			this.ExecBySql("delete from t_pdt_productreqallocationrules where fcustomerid='"+c.getFid()+"' and fsupplierid = '"+supplierid+"'");//删除分配规则
			if(!this.QueryExistsBySql("select 1 from t_pdt_productreqallocationrules where fcustomerid='"+c.getFid()+"'")){
				this.Delete(c);//删客户
				this.ExecBySql("delete from t_bd_usercustomer where fcustomerid='"+c.getFid()+"'");//删除账号关联客户
				String hql = "from SysUser where fcustomerid = '"+c.getFid()+"'";
				u= this.QueryByHql(hql);
				if(u!=null&&u.size()>0){
					if(!this.QueryExistsBySql("select fid from t_bd_usercustomer where fuserid='"+u.get(0).getFid()+"' and fcustomerid<>'"+c.getFid()+"'"))//有其他三级厂引用
					{
						this.Delete(u.get(0));//删除用户
						this.ExecBySql("delete from t_sys_userrole where fuserid = '"+u.get(0).getFid()+"'");	//删除角色
					}
				}
			}
		}
		
	}



	
}
