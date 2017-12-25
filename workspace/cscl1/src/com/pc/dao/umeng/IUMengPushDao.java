package com.pc.dao.umeng;

import java.util.List;

import com.pc.dao.aBase.IBaseDao;
import com.pc.model.CL_Umeng_Push;

public interface IUMengPushDao extends IBaseDao<CL_Umeng_Push, java.lang.Integer> {
	/**通过用户ID查该用户所有设备*/
	public List<CL_Umeng_Push> getUserUmengRegistration(Integer fuserid);
	
	/**通过用户ID和类型查该用户指定设备*/
	public List<CL_Umeng_Push> getUserUmengRegistration(Integer fuserid,Integer ftype);
	
	public List<CL_Umeng_Push> getUserPhone(String fuserPhone);
	
	public List<CL_Umeng_Push> getUserPhone(String fuserPhone,Integer ftype);
}
