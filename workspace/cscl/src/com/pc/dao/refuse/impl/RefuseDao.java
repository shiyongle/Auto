package com.pc.dao.refuse.impl;

import org.springframework.stereotype.Service;

import com.pc.dao.aBase.impl.BaseDao;
import com.pc.dao.refuse.IrefuseDao;
import com.pc.model.CL_Order;
import com.pc.model.CL_Refuse;

/**
 *@author lancher
 *@date 2017年1月14日 下午3:14:09
 */
@Service("refuseDao")
public class RefuseDao extends BaseDao<CL_Refuse, Integer> implements IrefuseDao {
	@Override
	public String getIbatisSqlMapNamespace() {
		return "CL_Refuse";
	}
	
	@Override
	public CL_Refuse IsExistIdByType(Integer ftype) {
		// TODO Auto-generated method stub
		return this.getSqlSession().selectOne(getIbatisSqlMapNamespace()+".IsExistIdByType", ftype);
	}
	
	@Override
	public CL_Refuse getusername(String[] fvalues) {
		// TODO Auto-generated method stub
		return this.getSqlSession().selectOne(getIbatisSqlMapNamespace()+".getusername", fvalues);
	}
}
