package com.pc.dao.userBalance.impl;



import org.springframework.stereotype.Service;

import com.pc.dao.aBase.impl.BaseDao;
import com.pc.dao.userBalance.IuserBalanceDao;
import com.pc.model.CL_UserBalance;

@Service("balanceDao")
public class UserBalanceDao extends BaseDao<CL_UserBalance, java.lang.Integer> implements IuserBalanceDao {
	
	@Override
	public String getIbatisSqlMapNamespace() {
		return "CL_UserBalance";
	}

	/*@Override
	public int updateAudit(Integer fauditor, Date faudit_time,
			Integer fis_pass_identify) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("fauditor", fauditor);
		map.put("faudit_time", faudit_time);
		map.put("fis_pass_identify", fis_pass_identify);
		return this.getSqlSession().update(getIbatisSqlMapNamespace()+".updateAudit",map);
	}*/
	

}
