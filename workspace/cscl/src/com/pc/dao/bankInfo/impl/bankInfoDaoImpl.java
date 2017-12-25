package com.pc.dao.bankInfo.impl;

import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Service;

import com.pc.dao.aBase.impl.BaseDao;
import com.pc.dao.bankInfo.IbankInfoDao;
import com.pc.model.CL_Bank_Info;

@Service("bankInfoDao")
public class bankInfoDaoImpl extends BaseDao<CL_Bank_Info,java.lang.Integer> implements IbankInfoDao{

	@Override
	public String getIbatisSqlMapNamespace() {
		return "CL_Bank_Info";
	}
	
	
	@Override
	public List<CL_Bank_Info> getByBankInfo(String BankCardNumber,boolean isLike)
	{
		 HashMap<String, Object> map=new HashMap<>();
		 map.put("fcardIndex", BankCardNumber);
		 String selectMethods=".getByBankInfoCode";
		 if(isLike)
			 selectMethods=".getByBankInfoLikeCode";
		 return this.getSqlSession().selectList(getIbatisSqlMapNamespace()+selectMethods, map);
	}
}
