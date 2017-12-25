package Com.Dao.System;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.Query;
import org.springframework.stereotype.Service;

import com.dongjing.api.DongjingClient;

import Com.Base.Dao.BaseDao;
import Com.Base.Util.DJException;
import Com.Base.Util.ServerContext;
import Com.Base.Util.params;
import Com.Entity.System.SysUser;
import Com.Entity.System.UserCustomer;
import Com.Entity.System.UserRole;
import Com.Entity.System.UserSupplier;
import Com.Entity.System.Userip;
import Com.Entity.System.Useronline;
import Com.Entity.order.Schemedesign;

@Service("SysUserDao")
public class SysUserDao extends BaseDao implements ISysUserDao {

	/**
	 * 函数说明：获得一条的信息 参数说明： ID 返回值：对象
	 */
	@Override
	public SysUser Query(String fid) {
		return this.getHibernateTemplate().get(
				SysUser.class, fid);
	}

	/**
	 * 函数说明：查询的所有信息 参数说明： 集合 返回值：
	 */
	@Override
	public List QuerySysUsercls(String hql) {
		// String sql = " FROM Products where  " + fieldname + "  like '% " +
		// value + " %' " + " ORDER BY gameNameCn " ;
		return this.getHibernateTemplate().find(hql);

		
		
	}

	@Override
	public HashMap<String, Object> ExecSave(SysUser info) {
		HashMap<String, Object> params = new HashMap<>();
		if (info.getFid().isEmpty()) {
			info.setFid(this.CreateUUid());
			info.setFeffect(0);
			info.setFcreatetime(new Date());
			this.saveOrUpdate(info);
		}
		else
		{
//			info.setFeffect(0);
			this.saveOrUpdate(info);
		}
		params.put("success", true);
		return params;
	}
	
	@Override
	public HashMap<String, Object> ExecSaveUserip(Userip uinfo) {
		HashMap<String, Object> params = new HashMap<>();
		if (uinfo.getFid().isEmpty()) {
			uinfo.setFid(this.CreateUUid());
			uinfo.setFefected(0);
			uinfo.setFcreatetime(new Date());
			this.saveOrUpdate(uinfo);
		}
		else
		{
			uinfo.setFefected(0);
			this.saveOrUpdate(uinfo);
		}
		params.put("success", true);
		return params;
	}
	@Override
	public SysUser ExecCheckLogin(HttpServletRequest request) {
		
		String sessionid=request.getSession().getId();
		if (!sessionid.isEmpty())
		{
			this.ExecBySql("delete from t_sys_useronline  where Fsessionid='"+sessionid+"'");
			ServerContext.delUseronline(sessionid);
		}
//		if(request.getParameter("L").length()==0)
//		{
//			
//		}
//		try {
//			String[] DongJingL= decrypt(request.getParameter("L").replace("返回","\r").replace("换行","\n").replace("制表","\t").replace("退格", "\b")).split(",");
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			throw new DJException("参数错误！");
//		}
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		Query q = getSession().createQuery(" FROM SysUser where (fname=:fname or ftel=:ftel or femail=:femail) and fpassword=:fpassword and feffect=0");
		q.setString("fname", username);
		q.setString("ftel", username);
		q.setString("femail", username);
		q.setString("fpassword", password);
		List<SysUser> slist=q.list();
		if (slist.size()>0)
		{
			return slist.get(0);
		}
		return null;
	}

	@Override
	public void ExecChangePWD(HttpServletRequest request) throws DJException {
		// TODO Auto-generated method stub
		String foldpassword = request.getParameter("foldpassword");
		String fpassword = request.getParameter("fpassword");
		String fuserid=((Useronline)request.getSession().getAttribute("Useronline")).getFuserid();
		Query q = getSession().createQuery(" FROM SysUser where fid=:fid and fpassword=:fpassword ");
		q.setString("fid", fuserid);
		q.setString("fpassword", foldpassword);
		List<SysUser> slist=q.list();
		if (slist.size()>0)
		{
			params p=new params();
			p.setString("fid", fuserid);
			p.setString("fpassword", fpassword);
			String sql="update t_sys_user set fpassword=:fpassword where fid=:fid";
			this.ExecBySql(sql, p);
//			q = getSession().createSQLQuery();
//			
//			q.executeUpdate();
		}
		else
		{
			throw new DJException("原密码错误！");
		}
		
	}
	
	@Override
	public void saveUserSupplier(String userID, String[] tArrayofFid) {
		String sql;
		for (int i = 0; i < tArrayofFid.length ; i++) {
			sql = "SELECT fid FROM t_bd_usersupplier where FUSERID = :userid and fSUPPLIERID = :fsupplierid ;";//SELECT * 
			params paramsT1 = new params();
			paramsT1.setString("userid", userID);
			paramsT1.setString("fsupplierid", tArrayofFid[i]);

			if (this.QueryBySql(sql, paramsT1).size() == 0) {
				UserSupplier info=new UserSupplier();
				info.setFid(this.CreateUUid());
				info.setFsupplierid( tArrayofFid[i]);
				info.setFuserid(userID);
				this.saveOrUpdate(info);
			}
		}
	}
	
	@Override //原：用户关联客户 ，（多个用户存在相同的管理子账户客户，批量更新用户关联客户关系） ;现：不批量处理，只有操作的用户改变关联关系
	public void saveUserCustomer(String userID, String[] tArrayofFid) {
		String sql;
//		String[] users = {userID};
//		sql = "select group_concat(fid) users from t_sys_user where fcustomerid !='' and fcustomerid = (select fcustomerid from t_sys_user where fid='"+userID+"')";
//		List<HashMap<String, Object>> list = this.QueryBySql(sql);
//		if(list.get(0).get("users")!=null){
//			users = list.get(0).get("users").toString().split(",");
//		}
		UserCustomer info;
//		for(String u : users){
//			if(u.equals(userID)){
//				System.out.println(u);
//			}
			for (int i = 0; i < tArrayofFid.length ; i++) {
				sql = "SELECT fid FROM t_bd_usercustomer where FUSERID = :userid and FCUSTOMERID = :customerid ;";//SELECT * 
				params paramsT1 = new params();
				paramsT1.setString("userid", userID);
				//原：paramsT1.setString("userid", u);
				paramsT1.setString("customerid", tArrayofFid[i]);
				
				if (this.QueryBySql(sql, paramsT1).size() == 0) {
					info=new UserCustomer();
					info.setFid(CreateUUid());
					info.setFcustomerid( tArrayofFid[i]);
					info.setFuserid(userID);
					//原info.setFuserid(u);
					saveOrUpdate(info);
				}
			}
//		}
	}

	@Override
	public void saveUserRole(String userID, String[] tArrayofFid) {
		String sql;
		for (int i = 0; i < tArrayofFid.length ; i++) {
			sql = "SELECT fid FROM t_sys_userrole where FUSERID = :userid and froleid = :froleid ;";//SELECT * 
			
			params paramsT1 = new params();
			paramsT1.setString("userid", userID);
			paramsT1.setString("froleid", tArrayofFid[i]);

			if (this.QueryBySql(sql, paramsT1).size() == 0) {
				UserRole info=new UserRole();
				info.setFid(CreateUUid());
				info.setFroleid( tArrayofFid[i]);
				info.setFuserid(userID);
				saveOrUpdate(info);
			}
		}
	}

	@Override
	public void saveSubUser(SysUser subUser, SysUser superUser) {
		this.saveOrUpdate(subUser);
		String fid = subUser.getFid();
		String sql = "select group_concat(fcustomerid) customerids from t_bd_usercustomer where fuserid = '"+superUser.getFid()+"'";
		List<HashMap<String, Object>> list = this.QueryBySql(sql);
		if(list.get(0).get("customerids") != null){
			String[] customerids = list.get(0).get("customerids").toString().split(",");
			UserCustomer uc;
			for(String s : customerids){
				if(!this.QueryExistsBySql("select 1 from t_bd_usercustomer where fcustomerid='"+s+"' and fuserid='"+fid+"'")){
					uc = new UserCustomer();
					uc.setFid(this.CreateUUid());
					uc.setFcustomerid(s);
					uc.setFuserid(fid);
					System.out.println(uc);
				}
			}
		}
	}

	@Override //原：用户关联客户 ，（多个用户存在相同的管理子账户客户，批量删除用户关联客户关系） ;现：不批量处理，只有操作的用户删除关联关系
	public void DelUserCustomer(String fidcls, String userid) { 
//		String sql = "select fid userids from t_sys_user where fcustomerid !='' and fcustomerid=(select fcustomerid from t_sys_user where fid='"+userid+"')";
//		List<HashMap<String, String>> list = this.QueryBySql(sql);
//		if(list.size()==0){
			this.ExecBySql("delete from t_bd_usercustomer where FUSERID= '"+userid+"'  and FCUSTOMERID in "+fidcls);
//		}else{
//			sql = "delete from t_bd_usercustomer where fuserid in ("+sql+") and fcustomerid in "+fidcls;
//			this.ExecBySql(sql);
//		}
	}
	
	@Override
	public void ExecChangeMSG(HttpServletRequest request) throws DJException {
		String fuserid=((Useronline)request.getSession().getAttribute("Useronline")).getFuserid();
		String femail = request.getParameter("userMail");
		String ftel = request.getParameter("userTel");
		params p=new params();
		p.setString("fid", fuserid);
		p.setString("femail", femail);
		p.setString("ftel", ftel);
		String sql="update t_sys_user set femail=:femail,ftel=:ftel where fid=:fid";
		this.ExecBySql(sql, p);
	}
	
	public void addDefaultFriendGroup(HttpServletRequest request, SysUser info){
		String fimid = "";
		String ftype ="";
		if(info!=null && info.getFimid()!=null && !info.getFimid().equals("")){
			fimid = info.getFimid().toString();
			ftype = info.getFtype().toString();
			
		}else{
			return ;
		}
		
			DongjingClient c=new DongjingClient();
			c.setURL("http://202.107.222.99:8066/ichat/cgi/");
			if(ftype.equals("0")){//客户
				c.setMethod("friend_add");
				c.setRequestProperty("account",""+fimid);
				c.setRequestProperty("friend",  "120");//设计服务：夏添
				c.SubmitData();
			}

			c.setMethod("friend_add");
			c.setRequestProperty("account",""+fimid);
			c.setRequestProperty("friend",  "498");//纸板服务：高丹
			c.SubmitData();
			
			c.setMethod("friend_add");
			c.setRequestProperty("account",""+fimid);
			c.setRequestProperty("friend",  "159");//纸箱服务：陈金菊
			c.SubmitData();
			
			c.setMethod("friend_add");
			c.setRequestProperty("account",""+fimid);
			c.setRequestProperty("friend",  "450");//客服服务：叶海凡
			c.SubmitData();
	}
	
}
