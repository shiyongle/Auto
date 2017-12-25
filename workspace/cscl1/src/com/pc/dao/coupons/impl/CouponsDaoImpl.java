package com.pc.dao.coupons.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.org.rapid_framework.page.Page;
import cn.org.rapid_framework.page.PageRequest;

import com.pc.dao.aBase.impl.BaseDao;
import com.pc.dao.coupons.ICouponsDao;
import com.pc.model.CL_Coupons;
import com.pc.query.coupons.CL_CouponsQuery;

@Service("couponsDao")
public class CouponsDaoImpl extends BaseDao<CL_Coupons,java.lang.Integer> implements ICouponsDao{
	
	@Override
	public String getIbatisSqlMapNamespace() {
		return "CL_Coupons";
	}

	@Override
	@Transactional
	public void updatepl(Integer effective, Integer[] ids) {
		HashMap<String,Object> map = new HashMap<String,Object>();
		map.put("isEffective", effective);
		map.put("ids", ids);
		this.getSqlSession().update(getIbatisSqlMapNamespace()+".updatepl", map);
	}

	@Override
	@Transactional
	public void deletepl(Integer[] ids) {
		HashMap<String,Object> map = new HashMap<String,Object>();
		map.put("ids", ids);
		this.getSqlSession().update(getIbatisSqlMapNamespace()+".deletepl", map);
		
	}

	@Override
	public List<CL_Coupons> getFooter(CL_CouponsQuery couponsQuery) {
		return getSqlSession().selectList(getIbatisSqlMapNamespace() + ".getFooter", couponsQuery);
	}

	@Override
	public List<CL_Coupons> getByRedeemCode(String redeemCode) {
		return getSqlSession().selectList(getIbatisSqlMapNamespace() + ".getByRedeemCode", redeemCode);
	}

	@Override
	public CL_Coupons getByRedeemCodeForACTIVE(String redeemCode) {
		return getSqlSession().selectOne(getIbatisSqlMapNamespace() + ".getByRedeemCodeForACTIVE", redeemCode);
	}

	@Override
	public Page<CL_Coupons> singleFindPage(PageRequest pr) {
		return this.pageQuery(getIbatisSqlMapNamespace() + ".singleFindPage", pr);
	}

	@Override
	public List<Integer> getBeOverdueCoupons() {
		return this.getSqlSession().selectList(getIbatisSqlMapNamespace()+".getBeOverdueCoupons");
	}

	@Override
	public void updateCoupons(Integer[] ids) {
		HashMap<String,Object> map = new HashMap<String,Object>();
		map.put("ids", ids);
		this.getSqlSession().update(getIbatisSqlMapNamespace()+".updateCoupons", map);
	}

	@Override
	public Number getNumByCouponsName(String fcouponsName) {
		return this.getSqlSession().selectOne(getIbatisSqlMapNamespace()+".getNumByCouponsName", fcouponsName);
	}
}
