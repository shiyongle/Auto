package Com.Dao.System;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import Com.Base.Dao.BaseDao;
import Com.Base.Util.DJException;
import Com.Base.Util.DataUtil;
import Com.Base.Util.JsonUtil;
import Com.Entity.System.Address;
import Com.Entity.System.CustRelationAdress;
import Com.Entity.System.SysUser;
import Com.Entity.System.Useraddress;
@Service
public class UseraddressDao extends BaseDao implements IUseraddressDao {
	@Resource
	private ICustRelationAdressDao CustRelationAdressDao;
	
	@Resource
	private IUseraddressDao useraddressDao;
	
	@Resource
	private IAddressDao addressDao;
	
	@Override
	public HashMap<String, Object> ExecSave(Useraddress useraddress) {
		// TODO Auto-generated method stub
		HashMap<String, Object> params = new HashMap<>();
		if (useraddress.getFid().isEmpty()) {
			useraddress.setFid(this.CreateUUid()); 
		}
		this.saveOrUpdate(useraddress);

		params.put("success", true);
		return params;
	}

	@Override
	public Useraddress Query(String fid) {
		// TODO Auto-generated method stub
		return this.getHibernateTemplate()
				.get(Useraddress.class, fid);
	}

	@Override
	public void ExecAddUserAddressItem(HttpServletRequest request) {
		// TODO Auto-generated method stub
		
		String fidcls = request.getParameter("fidcls");
		fidcls="('"+fidcls.replace(",","','")+"')";
		String userID = request.getParameter("myobjectid");
		
		String[] tArrayofFid = DataUtil.InStrToArray(fidcls);
		
		for (int i = 0; i < tArrayofFid.length; i++) {
			
			String sql = " select count(*) from t_bd_useraddress where fuserid = '%s' and faddress = '%s' ";
			
			sql = String.format(sql, userID, tArrayofFid[i]);
			
			if (QueryCountBySql(sql).equals("0")) {
				
				Useraddress useraddress = new Useraddress("", userID, tArrayofFid[i],0);
				
				ExecSave(useraddress);
			}
			
		}
		
	}

	@Override
	public void ExecDelUserCustomer(HttpServletRequest request) {
		// TODO Auto-generated method stub
		
		String fidcls = request.getParameter("fidcls");
		fidcls="('"+fidcls.replace(",","','")+"')";
		String userID = request.getParameter("myobjectid");
		
		String sql = " delete from t_bd_useraddress where fid in %s ";
		
		sql = String.format(sql, fidcls);
		
		ExecBySql(sql);
		
	}

	@Override
	public List<Useraddress> getUserAdList(String userid) {
		// TODO Auto-generated method stub
		String hql = "  FROM Useraddress WHERE fuserid = '%s'";
		hql = String.format(hql, userid);
		return QueryByHql(hql);
	}

	@Override
	public void ExecUserAddressDft(HashMap hash) {
			String fadid =hash.get("fadid").toString();
			String fcdid = hash.get("fcdid").toString();
			String userid = hash.get("userid").toString();
			List<Useraddress> userList = getUserAdList(userid);
			String sql = "";
			//2015-04-27 设为默认时,用户地址无记录则插入
			//2015-06-02 fcdid 如果在Useraddress中那么，账户肯定设置了只查看自己，则按照新逻辑
			String hql = "from Useraddress where fid = '"+fcdid+"'";
			List<Useraddress> list = this.QueryByHql(hql);
			if(list.size()>0){
				this.ExecBySql("UPDATE t_bd_useraddress SET fdefault = 0 WHERE fuserid ='"+userid+"' ");
				Useraddress udInfo = list.get(0);
				udInfo.setFaddefault(1);
				this.saveOrUpdate(udInfo);
			}
			else{
				if(userList.size()==0){
					Useraddress udInfo = new Useraddress();
					udInfo.setFid(CreateUUid());
					udInfo.setFaddress(fadid);
					udInfo.setFuserid(userid);
					udInfo.setFaddefault(1);
					saveOrUpdate(udInfo);
				}
				else{				
					sql = "update t_bd_useraddress set faddress='" + fadid+ "',fdefault = 1 where fuserid = '" + userid + "' ";
					ExecBySql(sql);	
				}
				//默认后地址也启用
				sql = "update t_bd_custrelationadress set feffect= 1 where fid = '"+fcdid+"' ";
				ExecBySql(sql);	
			}
		}

	@Override
	public void DelUserToAddress(HashMap hashmap) {
		String fadid =hashmap.get("fadid").toString();
		String fcdid = hashmap.get("fcdid").toString();
		String userid = hashmap.get("userid").toString();
		String strsql = hashmap.get("strsql").toString();
		ExecBySql("delete from t_bd_custrelationadress where fid ='"+fcdid+"'");
		ExecBySql("delete from t_bd_useraddress where fuserid = '"+userid+"' and faddress='"+fadid+"'");
		//2015-04-27  删除后客户关联地址中记录唯一,则设为默认地址
		List<HashMap<String, Object>>  addressList = QueryBySql("select * from t_bd_CustRelationAdress where 1=1 and feffect = 1 "+strsql);
		if(addressList.size()==1){
			for (HashMap<String, Object> map : addressList) {
				Useraddress udInfo = new Useraddress();
				udInfo.setFid(CreateUUid());
				udInfo.setFuserid(userid);
				udInfo.setFaddress(map.get("faddressid").toString());
				saveOrUpdate(udInfo);
			}
		}
	}

	@Override
	public HashMap<String, Object> ExecSaveMyAddress(HashMap hash) {
		// TODO Auto-generated method stub
		//1.客户关联地址表
		HashMap<String, Object> params = new HashMap<>();
		String userid = hash.get("userid").toString();
		Address ainfo = (Address)hash.get("Address");
		addressDao.ExecSave(ainfo);
		String sql="select fcustomerid fid from ( select fcustomerid  from t_bd_usercustomer where fuserid='"+userid+"'"+
				" union select c.fcustomerid from  t_sys_userrole r "+
				" left join t_bd_rolecustomer c on r.froleid=c.froleid where r.fuserid='"+userid+"' ) s "+
				" left join t_bd_customer cust ON cust.fid = s.fcustomerid where fcustomerid is not null " ;
		List<HashMap<String,Object>> list = useraddressDao.QueryBySql(sql);
		if(list.size()>0){
			for(int i = 0;i<list.size();i++){			
				System.out.println(list.get(i).get("fid").toString());
				CustRelationAdress cuToadInfo = new CustRelationAdress();
				cuToadInfo.setFid(CustRelationAdressDao.CreateUUid());
				cuToadInfo.setFcustomerid(list.get(i).get("fid").toString());
				cuToadInfo.setFaddressid(ainfo.getFid());				
				CustRelationAdressDao.saveOrUpdate(cuToadInfo);
			}	
		}
		else{
			CustRelationAdress cuToadInfo = new CustRelationAdress();
			cuToadInfo.setFid(CustRelationAdressDao.CreateUUid());
			cuToadInfo.setFcustomerid(ainfo.getFcustomerid());
			cuToadInfo.setFaddressid(ainfo.getFid());				
			CustRelationAdressDao.saveOrUpdate(cuToadInfo);
		}
		//2.用户地址表处理,用户地址默认只有一条记录
		//2015-06-02  用户是否只查看自己帐号，是的话Useraddress允许多条存在
		SysUser user  = (SysUser)this.Query(SysUser.class, userid);
		if(user.getFisreadonly()==1){ 
			Useraddress uadrInfo = new Useraddress();
			uadrInfo.setFaddress(ainfo.getFid());
			uadrInfo.setFuserid(userid);
			uadrInfo.setFid(useraddressDao.CreateUUid());
			useraddressDao.saveOrUpdate(uadrInfo);
		}
		else{
			List<Useraddress> userList = this.getUserAdList(userid);
			if(userList.size()>0){					
				Useraddress uadrInfo = userList.get(0);
				uadrInfo.setFaddress(ainfo.getFid());
				this.saveOrUpdate(uadrInfo);
			}
			else{
				Useraddress uadrInfo = new Useraddress();
				uadrInfo.setFaddress(ainfo.getFid());
				uadrInfo.setFuserid(userid);
				uadrInfo.setFid(useraddressDao.CreateUUid());
				useraddressDao.saveOrUpdate(uadrInfo);
			}	
		}
		params.put("success", true);
		return params;
	}
}
