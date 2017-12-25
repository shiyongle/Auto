package com.pc.dao.couponsDetail.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pc.dao.UserRole.IUserRoleDao;
import com.pc.dao.aBase.impl.BaseDao;
import com.pc.dao.coupons.impl.CouponsDaoImpl;
import com.pc.dao.couponsDetail.ICouponsDetailDao;
import com.pc.dao.financeStatement.IFinanceStatementDao;
import com.pc.dao.financeStatement.impl.FinanceStatementDaoImpl;
import com.pc.dao.message.impl.MessageDao;
import com.pc.dao.order.IorderDao;
import com.pc.dao.order.impl.OrderDao;
import com.pc.model.CL_CouponsDetail;
import com.pc.model.CL_Order;
import com.pc.model.CL_UserRole;
import com.pc.util.pay.PayUtil;

/*
 * CPS-VMI-wangc
 */

@Service("couponsDetailDao")
public class CouponsDetailDaoImpl extends BaseDao<CL_CouponsDetail, java.lang.Integer> implements ICouponsDetailDao {

	@Resource
	private CouponsDaoImpl couponsDao;
	@Resource
	private MessageDao messageDao;
	@Resource
	private IorderDao orderDao;
	@Resource
	private IUserRoleDao userRoleDao;

	@Resource
	private FinanceStatementDaoImpl financeStatementDao;

	@Override
	public String getIbatisSqlMapNamespace() {
		return "CL_CouponsDetail";
	}

	@Override
	public CL_CouponsDetail getByCouponsId(Integer couponsId) {
		return this.getSqlSession().selectOne(getIbatisSqlMapNamespace() + ".getByCouponsId", couponsId);
	}
	
	@Override
	public List<CL_CouponsDetail> getByCouponsId2(Integer couponsId) {
		return this.getSqlSession().selectList(getIbatisSqlMapNamespace() + ".getByCouponsId2", couponsId);
	}

	@Override
	public CL_CouponsDetail getByOrderId(Integer forderId) {
		return this.getSqlSession().selectOne(getIbatisSqlMapNamespace() + ".getByOrderId", forderId);
	}

	@Override
	public void updateCouponsDetail(Integer[] ids) {
		this.getSqlSession().update(getIbatisSqlMapNamespace() + ".updateCouponsDetail", ids);
	}

	@Override
	public List<Integer> getBeOverdueCouponsDetail() {
		return this.getSqlSession().selectList(getIbatisSqlMapNamespace() + ".getBeOverdueCouponsDetail");
	}

	@Override
	public int getCountByUserCoupon(Integer userId, Integer couponsActivityId) {
		HashMap<String, Integer> map = new HashMap<String, Integer>();
		map.put("couponsActivityId", couponsActivityId);
		map.put("userId", userId);
		return this.getSqlSession().selectOne(getIbatisSqlMapNamespace() + ".getCountByUserCoupon", map);
	}

	@Override
	public List<CL_CouponsDetail> getByUserRoleId(Integer userRoleId, Integer isUse) {
		HashMap<String, Integer> map = new HashMap<String, Integer>();
		map.put("userRoleId", userRoleId);
		map.put("isUse", isUse);
		return this.getSqlSession().selectList(getIbatisSqlMapNamespace() + ".getByUserRoleId", map);
	}

	@Override
	public List<CL_CouponsDetail> IsExistByUserRoleId(Integer userRoleId) {
		// TODO Auto-generated method stub
		HashMap<String, Integer> map = new HashMap<String, Integer>();
		map.put("userRoleId", userRoleId);
		return this.getSqlSession().selectList(getIbatisSqlMapNamespace() + ".IsExistByUserRoleId", map);
	}

	@Override
	public List<CL_CouponsDetail> getEffective(Integer creator,Integer is_use,Integer is_overdue,Integer type,String area,String carSpecId) {

		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("creator", creator);
		map.put("is_use",is_use);
		map.put("is_overdue", is_overdue);
		map.put("ftype", type);
		map.put("farea", area);
		map.put("fcarSpecId", carSpecId);
		return this.getSqlSession().selectList(getIbatisSqlMapNamespace() + ".getEffective", map);
	}
	
	@Override
	public List<CL_CouponsDetail> getEffective2(Integer creator,Integer is_use,Integer is_overdue,Integer type) {
		
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("creator", creator);
		map.put("is_use",is_use);
		map.put("is_overdue", is_overdue);
		map.put("ftype", type);
		return this.getSqlSession().selectList(getIbatisSqlMapNamespace() + ".getEffective2", map);
	}
}