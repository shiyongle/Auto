package com.pc.dao.userVoucher.impl;

import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.org.rapid_framework.page.Page;
import cn.org.rapid_framework.page.PageRequest;

import com.pc.dao.aBase.impl.BaseDao;
import com.pc.dao.coupons.ICouponsDao;
import com.pc.dao.userVoucher.IuserVoucherDao;
import com.pc.model.CL_Coupons;
import com.pc.model.CL_UserVoucher;
import com.pc.query.coupons.CL_CouponsQuery;

@Service("userVoucherDao")
public class userVoucherDaoImpl extends BaseDao<CL_UserVoucher,java.lang.Integer> implements IuserVoucherDao{
	
	@Override
	public String getIbatisSqlMapNamespace() {
		return "CL_UserVoucher";
	}

	@Override
	public int updateByStatus(Integer fid, Integer fstatus, Integer fcreator) {
		 HashMap<String, Object> map= new HashMap<>();
		 map.put("fid", fid);
		 map.put("fstatus", fstatus);
		 map.put("fcreator", fcreator);
		return this.getSqlSession().update(getIbatisSqlMapNamespace()+".updateByStatus",map);
	}

	@Override
	public CL_UserVoucher getByIdAndFcreator(Integer fid, Integer fcreator) {
		 HashMap<String, Object> map= new HashMap<>();
		 map.put("fid", fid);
		 map.put("fcreator", fcreator);
		return this.getSqlSession().selectOne(getIbatisSqlMapNamespace()+".getByIdAndFcreator",map);

	}

	@Override
	public List<CL_UserVoucher> getByStatusInTast() {
		return this.getSqlSession().selectList(getIbatisSqlMapNamespace()+".getByStatusInTast");
	}

	@Override
	public int updateVouCherDetail(Integer[] ids) {
		 HashMap<String, Object> map= new HashMap<>();
		 map.put("ids", ids);
		return this.getSqlSession().update(getIbatisSqlMapNamespace()+".updateVouCherDetail",map);
	}

	@Override
	public int updateVouCherStatusDetail(Integer[] ids) {
		 HashMap<String, Object> map= new HashMap<>();
		 map.put("ids", ids);
		return this.getSqlSession().update(getIbatisSqlMapNamespace()+".updateVouCherStatusDetail",map);
	}

	@Override
	public CL_UserVoucher getByForderNumber(String forderNumber,
			Integer fcreator) {
		 HashMap<String, Object> map= new HashMap<>();
		 map.put("forderNumber", forderNumber);
		 map.put("fcreator",fcreator);
		return this.getSqlSession().selectOne(getIbatisSqlMapNamespace()+".getByForderNumber",map);
	}

} 
