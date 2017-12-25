package com.pc.dao.rating.impl;


import java.util.HashMap;

import org.springframework.stereotype.Service;

import cn.org.rapid_framework.page.Page;
import cn.org.rapid_framework.page.PageRequest;

import com.pc.dao.aBase.impl.BaseDao;
import com.pc.dao.rating.IratingDao;
import com.pc.model.CL_Rating;

/**
 * 
 * @author twr
 *
 */
@Service("RatingDao")
public class RatingDao extends BaseDao<CL_Rating,java.lang.Integer> implements IratingDao{

	@Override
	public String getIbatisSqlMapNamespace() {
		return "CL_Rating";
	}

	@Override
	public CL_Rating getByOrderNumAndType(String orderNum,Integer ratingType) {
		HashMap<String,Object> map =new HashMap<String,Object>();
		map.put("orderNum", orderNum);
		map.put("ratingType", ratingType);
		return this.getSqlSession().selectOne(getIbatisSqlMapNamespace()+".getByOrderNumAndType",map);
	}

	@Override
	public Page<CL_Rating> findRatePage(PageRequest query) {
		return this.pageQuery(getIbatisSqlMapNamespace() + ".findRatePage", query);
	}
 
	 
}
