package com.service.address;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dao.IBaseDao;
import com.dao.address.AddressDao;
import com.dao.custRelationAdress.TbdCustrelationadressDao;
import com.dao.user.TSysUserDao;
import com.dao.useraddress.TbdUseraddressDao;
import com.model.PageModel;
import com.model.address.Address;
import com.model.custRelationAdress.CustRelationAdress;
import com.model.user.TSysUser;
import com.model.useraddress.Useraddress;
import com.service.IBaseManagerImpl;
@Service("addressManager")
@Transactional
public class AddressManagerImpl extends IBaseManagerImpl<Address, java.lang.Integer> implements AddressManager{
	
	@Autowired
	private AddressDao addressDao;
	@Autowired
	private TSysUserDao userDao;
	@Autowired
	private TbdCustrelationadressDao tbdCustrelationadressDao;
	@Autowired
	private TbdUseraddressDao tbdUseraddressDao;
	@Override
	protected IBaseDao<Address, Integer> getEntityDao() {
		return this.addressDao;
	}
	@Override
	public List<Address> getByCustomerId(String customerId,String userid) {
		return this.addressDao.getByCustomerId(customerId,userid);
	}
	@Override
	public List<Address> getByCustomerId(String customerId) {
		return this.addressDao.getByCustomerId(customerId);
	}
	@Override
	public PageModel<Address> findBySql(String where,Object[] queryParams, Map<String, String> orderby, int pageNo,int maxResult) {
		return this.addressDao.findBySql(where, queryParams, orderby, pageNo, maxResult);
	}
	@Override
	public List<Address> getById(String where, Object[] queryParams) {
		return this.addressDao.getById(where, queryParams);
	}
	@Override
	public void isEnabledImpl(List<Address> ls, String isenable){
		for(int i =0;i<ls.size();i++){
				String sql = String.format(" UPDATE `t_bd_custrelationadress` SET `feffect`='%s' WHERE `fid`='%s'  ",isenable, ls.get(i).getCdid());
				this.addressDao.ExecBySql(sql);
		}
	}
	
//	@Override
//	public void saveImpl(Address address){
//		this.addressDao.save(address);
//		CustRelationAdress ca = new CustRelationAdress();
//			 ca.setFid(this.CreateUUid());
//			 ca.setFaddressid(address.getFid());
//			 ca.setFcustomerid(address.getFcustomerid());
//			 ca.setFeffect(1);//启用
//		 this.tbdCustrelationadressDao.save(ca);
//	}
	
	//2015-12-30  新增地址逻辑修改
	
	@Override
	public void saveImpl(Address address){
		this.addressDao.save(address);
		//老平台会在自己的子帐号下所有客户都加一个地址，当前只给当前用户关联的客户加（新平台,不存在1——N情况）
		CustRelationAdress cuToadInfo = new CustRelationAdress();
		cuToadInfo.setFid(this.CreateUUid());
		cuToadInfo.setFcustomerid(address.getFcustomerid());
		cuToadInfo.setFaddressid(address.getFid());				
		this.saveOrUpdate(cuToadInfo);
		//用户地址表处理,用户地址默认只有一条记录
		//用户是否只查看自己帐号，是的话Useraddress允许多条存在
		TSysUser user  = this.userDao.get(address.getFcreatorid());
		if(user.getFisreadonly()==1){ 
			Useraddress uadrInfo = new Useraddress();
			uadrInfo.setFaddress(address.getFid());
			uadrInfo.setFuserid(address.getFcreatorid());
			uadrInfo.setFid(this.CreateUUid());
			uadrInfo.setFaddefault(0);
			this.saveOrUpdate(uadrInfo);
		}
		else{
			List<Useraddress> userList = this.getUserAdList(address.getFcreatorid());
			//老逻辑是新增的地址就直接默认  不合理
			if(userList.size() == 0){					
				Useraddress uadrInfo = new Useraddress();
				uadrInfo.setFaddress(address.getFid());
				uadrInfo.setFuserid(address.getFcreatorid());
				uadrInfo.setFid(this.CreateUUid());
				this.saveOrUpdate(uadrInfo);
			}
		}
	}
	
	@Override
	public void deleteImpl(String[] ids) {
		for(String id:ids){
			String sql = String.format(" delete from `t_bd_custrelationadress`  WHERE `faddressid`='%s'  ",id);
			String sql1 = String.format(" delete from `t_bd_useraddress` WHERE `faddress`='%s'  ",id);
			this.tbdCustrelationadressDao.ExecBySql(sql);
			this.tbdCustrelationadressDao.ExecBySql(sql1);
		}
	}
	
	public List<Useraddress> getUserAdList(String userid) {
		String hql = "  FROM Useraddress WHERE fuserid = ?";
		return this.tbdUseraddressDao.QueryByHql(hql,new Object[]{userid});
	}
	
	public void isDefaultImpl(List<Address> list,String userId){
		if(list.size()>0){
			String fadid = list.get(0).getFid();
			String fcdid = list.get(0).getCdid();
			List<Useraddress> userList = getUserAdList(userId);
			String sql = "";
			Useraddress ls = this.tbdUseraddressDao.get(fcdid);
			if(ls!=null){
				this.ExecBySql("UPDATE t_bd_useraddress SET fdefault = 0 WHERE fuserid ='"+userId+"' ");
				ls.setFaddefault(1);
				this.saveOrUpdate(ls);
			}else{
				if(userList.size()==0){
					Useraddress udInfo = new Useraddress();
					udInfo.setFid(CreateUUid());
					udInfo.setFaddress(fadid);
					udInfo.setFuserid(userId);
					udInfo.setFaddefault(1);
					saveOrUpdate(udInfo);
				}else{				
					sql = "update t_bd_useraddress set faddress='" + fadid+ "',fdefault = 1 where fuserid = '" + userId + "' ";
					ExecBySql(sql);	
				}
				sql = "update t_bd_custrelationadress set feffect= 1 where fid = '"+fcdid+"' ";
				ExecBySql(sql);	
			}
		}
	}
	
	
	@Override
	public void setDefaultImpl(String adid, String userId) {
		String sql = "";
		TSysUser user  = this.userDao.get(userId);
		if(user.getFisreadonly()==1){
			//只查看自己地址肯定有在 t_bd_useraddress，除非乱切换至帐号 
			//sql = "update t_bd_useraddress set faddress='" + adid+ "',fdefault = 1 where fuserid = '" + userId + "' ";
			sql = "update t_bd_useraddress set fdefault = (faddress='"+adid+"') where fuserid = '" + userId + "'";
			ExecBySql(sql);	
		}
		else{
			sql = "delete from t_bd_useraddress  where fuserid = '" + userId + "'  ";
			ExecBySql(sql);	
			Useraddress udInfo = new Useraddress();
			udInfo.setFid(CreateUUid());
			udInfo.setFaddress(adid);
			udInfo.setFuserid(userId);
			udInfo.setFaddefault(1);
			saveOrUpdate(udInfo);
		}
	}

}
