package com.pc.dao.bank.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.pc.dao.aBase.impl.BaseDao;
import com.pc.dao.bank.IbankDao;
import com.pc.model.CL_Bank;

@Service("bankDao")
public class bankDaoImpl extends BaseDao<CL_Bank, java.lang.Integer> implements IbankDao {

	@Override
	public String getIbatisSqlMapNamespace() {
		return "CL_Bank";
	}

	@Override
	public int updateByStatus(Integer fdefault, Integer feffect,String number, Integer fid, Integer fuserId) {
		HashMap<String, Object> map = new HashMap<>();
		map.put("fdefault", fdefault);
		map.put("feffect", feffect);
		map.put("number", number);
		map.put("fid", fid);
		map.put("fuserId", fuserId);
		return this.getSqlSession().update(getIbatisSqlMapNamespace() + ".updateByStatus", map);
	}

	@Override
	public CL_Bank getByUserIdAndFcardNumber(String fcardNumber, Integer fuserId) {
		HashMap<String, Object> map = new HashMap<>();
		map.put("fcardNumber", fcardNumber);
		map.put("fuserId", fuserId);
		return this.getSqlSession().selectOne(getIbatisSqlMapNamespace() + ".getByUserIdAndFcardNumber", map);
	}

	@Override
	public int updateByFdel(Integer fdel, Integer fid, Integer fuserId) {
		HashMap<String, Object> map = new HashMap<>();
		map.put("fdel", fdel);
		map.put("fid", fid);
		map.put("fuserId", fuserId);
		return this.getSqlSession().update(getIbatisSqlMapNamespace() + ".updateByFdel", map);
	}

	@Override
	public CL_Bank getByIdAndFcreator(Integer fid, Integer fuserId) {
		HashMap<String, Object> map = new HashMap<>();
		map.put("fuserId", fuserId);
		map.put("fid", fid);
		return this.getSqlSession().selectOne(getIbatisSqlMapNamespace() + ".getByIdAndFcreator", map);
	}

	@Override
	public CL_Bank getByUserIdAndFcardNumberAndV(String fcardNumber, Integer fuserId) {
		HashMap<String, Object> map = new HashMap<>();
		map.put("fcardNumber", fcardNumber);
		map.put("fuserId", fuserId);
		return this.getSqlSession().selectOne(getIbatisSqlMapNamespace() + ".getByUserIdAndFcardNumberAndV", map);
	}

	@Override
	public List<Map<String, Object>> getByUserList(Integer fuserId, Integer feffect) {
		HashMap<String, Object> map = new HashMap<>();
		map.put("fuserId", fuserId);
		map.put("feffect", 2);
		if (feffect == 0)
			map.put("feffect", null);

		return this.getSqlSession().selectList(getIbatisSqlMapNamespace() + ".getBankList", map);
	}

	@Override
	public int removeUserBank(String fids, Integer fuserId) {
		HashMap<String, Object> map = new HashMap<>();
		map.put("fids", fids);
		map.put("fuserId", fuserId);
		return this.getSqlSession().update(getIbatisSqlMapNamespace() + ".removeUserBank", map);
	}

	@Override
	public CL_Bank getBankInfo(Integer fid,Integer fuserId,Integer feffect){
		HashMap<String, Object> map = new HashMap<>();
		map.put("fid", fid);
		map.put("fuserId", fuserId);
		map.put("feffect", feffect);
		return this.getSqlSession().selectOne(getIbatisSqlMapNamespace() + ".getBankInfo", map);
	}

}
