package com.pc.dao.order.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.pc.appInterface.api.DongjingClient;
import com.pc.dao.UserRole.IUserRoleDao;
import com.pc.dao.aBase.impl.BaseDao;
import com.pc.dao.addto.IaddtoDao;
import com.pc.dao.couponsDetail.ICouponsDetailDao;
import com.pc.dao.financeStatement.IFinanceStatementDao;
import com.pc.dao.order.IorderDao;
import com.pc.model.CL_CouponsDetail;
import com.pc.model.CL_Order;
import com.pc.model.CL_UserRole;
import com.pc.util.Base64;
import com.pc.util.ServerContext;
import com.pc.util.pay.PayUtil;

import cn.org.rapid_framework.page.Page;
import cn.org.rapid_framework.page.PageRequest;
import net.sf.json.JSONObject;

/**
 * 订单
 * @author zzm
 *
 */
@Service("orderDao")
public class OrderDao extends BaseDao<CL_Order,java.lang.Integer> implements IorderDao{
	@Resource
	private ICouponsDetailDao couponsDetailDao;
	@Resource
	private IUserRoleDao userRoleDao;
	@Resource
 	private IFinanceStatementDao financeStatementDao;
	@Resource
	private IaddtoDao iaddtoDao;
	
	@Override
	public String getIbatisSqlMapNamespace() {
		return "CL_Order";
	}
	
	@Override
	public Page<CL_Order> findUploadPage(PageRequest query) {
		return this.pageQuery(getIbatisSqlMapNamespace() + ".findUploadPage", query);
	}

	@Override
	public int updateByUserId(Integer orderId, Integer isCommon) {
	HashMap<String,Object> map =new HashMap<String,Object>();
		map.put("orderId", orderId);
		map.put("isCommon", isCommon);
		return this.getSqlSession().update(getIbatisSqlMapNamespace()+".updateByUserId",map);
	}

	@Override
	public java.util.List<CL_Order> getIdByStatus(Integer status) {
		// TODO Auto-generated method stub
		return this.getSqlSession().selectList(getIbatisSqlMapNamespace()+".getIdByStatus",status);
	}

	@Override
	public String getLockedAmount(Integer userRoleId, Date fcopTime) {
		HashMap<String,Object> map =new HashMap<String,Object>();
		map.put("userRoleId", userRoleId);
		map.put("fcopTime", fcopTime);
		if(this.getSqlSession().selectOne(getIbatisSqlMapNamespace()+".getLockedAmount",map)==null)
		{
			return "0";
		}
		return this.getSqlSession().selectOne(getIbatisSqlMapNamespace()+".getLockedAmount",map).toString();
	}

	@Override
	public List<CL_Order> getByUserRoleIdAndStatus(Integer userRoleId, Integer status) {
		HashMap<String,Object> map =new HashMap<String,Object>();
		map.put("userRoleId", userRoleId);
		map.put("status", status);
		return this.getSqlSession().selectList(getIbatisSqlMapNamespace()+".getByUserRoleIdAndStatus",map);
	}
	
	@Override
	public CL_Order IsExistIdByStatus(Integer id) {
		// TODO Auto-generated method stub
		return this.getSqlSession().selectOne(getIbatisSqlMapNamespace()+".IsExistIdByStatus", id);
	}

	@Override
	public int updateByOrderId(Integer userRoleId, Integer operator, Integer orderId,Integer status) {
	HashMap<String,Object> map =new HashMap<String,Object>();
		map.put("userRoleId", userRoleId);
		map.put("operator", operator);
		map.put("orderId", orderId);
		map.put("status", status);
		return this.getSqlSession().update(getIbatisSqlMapNamespace()+".updateByOrderId",map);
	}

	@Override
	public int updateByNumber(String orderNum,Integer status) {
		HashMap<String,Object> map =new HashMap<String,Object>();
			map.put("orderNum", orderNum);
			map.put("status", status);
		return this.getSqlSession().update(getIbatisSqlMapNamespace()+".updateByNumber",map);
	}



	@Override
	public CL_Order getByNumber(String number) {
		return  this.getSqlSession().selectOne(getIbatisSqlMapNamespace()+".getByNumber",number);
	}



	@Override
	public int updateStatusByOrderId(Integer orderId, Integer status) {
		HashMap<String,Object> map =new HashMap<String,Object>();
		 map.put("orderId", orderId);
		 map.put("status", status);
		return this.getSqlSession().update(getIbatisSqlMapNamespace()+".updateStatusByOrderId",map);
	}

	@Override
	public CL_Order getByNowTime(Date date) {
		HashMap<String,Object> map =new HashMap<String,Object>();
		 map.put("nowTime", date);
		return this.getSqlSession().selectOne(getIbatisSqlMapNamespace()+".getByNowTime",map);
	}



	@Override
	public List<CL_Order> getOrdersByStatus(Integer status) {
		return this.getSqlSession().selectList(getIbatisSqlMapNamespace()+".getOrdersByStatus",status);
	}



	@Override
	public CL_Order IsExistIdByRating(Integer id) {
		return  this.getSqlSession().selectOne(getIbatisSqlMapNamespace()+".IsExistIdByRating",id);
	}



	@Override
	public Map<Object, Object> getMaxNOBytable(String table, String NO,Integer length) {
		HashMap<String,Object> map =new HashMap<String,Object>();
		 map.put("talbe", table);
		 map.put("No", NO);
		 map.put("length", length);
		return this.getSqlSession().selectOne(getIbatisSqlMapNamespace()+".getMaxNOBytable",map);
	}

	@Override
	public Map<Object, Object> getAppVersion(String VersionCode,Integer ftype) {
		HashMap<String,Object> map =new HashMap<String,Object>();
		 map.put("VersionCode", VersionCode);
		 map.put("ftype", ftype);
		return this.getSqlSession().selectOne(getIbatisSqlMapNamespace()+".getAppVersion",map);
	}

	@Override
	public int updateServerById(CL_Order order) {
		return this.getSqlSession().update(getIbatisSqlMapNamespace()+".updateServerById",order);
	}



	@Override
	public int updateByArriveTime(Integer orderId, Date date) {
		HashMap<String,Object> map =new HashMap<String,Object>();
		 map.put("orderId", orderId);
		 map.put("farrivePickUpTime", date);
		return this.getSqlSession().update(getIbatisSqlMapNamespace()+".updateByArriveTime",map);
	}



	@Override
	public int updateByLeaveTime(Integer orderId, Date date) {
		HashMap<String,Object> map =new HashMap<String,Object>();
		 map.put("orderId", orderId);
		 map.put("fleavePickUpTime", date);
		return this.getSqlSession().update(getIbatisSqlMapNamespace()+".updateByLeaveTime",map);
	
	}

	@Override
	public int updateStatusOnline(Integer id, Integer fonlinePay, Date date) {
		HashMap<String,Object> map =new HashMap<String,Object>();
		 map.put("id", id);
		 map.put("fonlinePay", fonlinePay);
		 map.put("fcopTime", date);
		return  this.getSqlSession().update(getIbatisSqlMapNamespace()+".updateStatusOnline",map);
	}

	@Override
	public int getOrderCountByProtocol(Integer userId, Integer protocolId) {
		HashMap<String,Integer> map =new HashMap<String,Integer>();
		 map.put("userId", userId);
		 map.put("protocolId", protocolId);
		return this.getSqlSession().selectOne(getIbatisSqlMapNamespace()+".getOrderCountByProtocol",map);
	}

	@Override
	public CL_Order IsExistIdByRatingCar(Integer id) {
		return this.getSqlSession().selectOne(getIbatisSqlMapNamespace()+".IsExistIdByRatingCar",id);
	}

	@Override
	public int updateFratingByOrderId(Integer orderId, Integer frating) {
		HashMap<String,Integer> map =new HashMap<String,Integer>();
		 map.put("orderId", orderId);
		 map.put("frating", frating);
		return this.getSqlSession().update(getIbatisSqlMapNamespace()+".updateFratingByOrderId",map);
	}

	@Override
	public List<CL_Order> getByCreator() {
		return this.getSqlSession().selectList(getIbatisSqlMapNamespace()+".getByCreator");
	}
	
	
	
	/***根据orderid更新司机总费用 */
	@Override
    public int updateDriverFeeById(Integer id,BigDecimal fdriverfee)
	{
		HashMap<String, Object> map=new HashMap<String,Object>();
		map.put("id", id);
		map.put("fdriverfee", fdriverfee);
		return this.getSqlSession().update(getIbatisSqlMapNamespace()+".updateDriverFeeById",map);
	}

	@Override
	public int updateStatusByOrderIdAndTask(Integer id, Integer status) {
		HashMap<String, Object> map=new HashMap<String,Object>();
		map.put("orderId",id);
		map.put("status", status);
		return this.getSqlSession().update(getIbatisSqlMapNamespace()+".updateStatusByOrderIdAndTask",map);
	}

	@Override
	public int updateofflinePayByOrderId(Integer id, Integer fofflinePay) {
		// TODO Auto-generated method stub
		HashMap<String, Object> map=new HashMap<String,Object>();
		map.put("id", id);
		map.put("fofflinePay", fofflinePay);
		return this.getSqlSession().update(getIbatisSqlMapNamespace()+".updateofflinePayByOrderId",map);
	}

	@Override
	public int updatecouponsdetailByOrderId(Integer couponsdetatilid, Integer orderid) {
		// TODO Auto-generated method stub
		HashMap<String, Object> map=new HashMap<String,Object>();
		map.put("couponsdetatilid", couponsdetatilid);
		map.put("orderid", orderid);
		return this.getSqlSession().update(getIbatisSqlMapNamespace()+".updatecouponsdetailByOrderId",map);
	}

	@Override
	public CL_Order getByIdAndCreator(Integer id, Integer creator) {
		HashMap<String, Object> map=new HashMap<String,Object>();
		map.put("id", id);
		map.put("creator", creator);
		return this.getSqlSession().selectOne(getIbatisSqlMapNamespace()+".getByIdAndCreator",map);
	}

//	@Override
	/**自动扣除异常订单运费*/
//	public synchronized HashMap<String,Object> autoPayOrderFreight(CL_Order ord) {
//		HashMap<String,Object> m = new HashMap<String,Object>();
////		if(ord.getFpayMethod()!=5){
//			String ordnumber = ord.getNumber();
//			boolean ispay = false;
//				if(ord.getStatus()!=1){
//					m.put("success", "true");
//					m.put("msg","该订单已经付款！");
//					m.put("type", "0");
//					return m;
//				}
//
//				 if(ord.getFpayMethod()==null){
//					 m.put("success", "true");
//					 m.put("type", "1");
//					 return m;
//				 }
//				 
//				//支付校验
//				JSONObject jo;
//				JSONObject jo1;
//				String UserOrderPayOver=ServerContext.getBaseurl()+"/Action_Pay/DJPay/Pay/UserOrderPayOverAll";
////				UserOrderPayOver = UserOrderPayOver +"?&XDEBUG_SESSION_START=17402";
//				jo = new JSONObject();
//				jo1 = new JSONObject();
//				jo1.put("Number", ordnumber);
//				jo.put("data", jo1);
//				DongjingClient PayClient=new DongjingClient();
//				PayClient.setRequestProperty("data", Base64.encode(jo.toString().getBytes()));
////				ispay = PayClient.SubmitUrlData(UserOrderPayOver);
//				
//			//订单扣款改状态
//			if(ispay){
//				try {
//					m = payOrderFreight(ord);
////					m=couponsDetailDao.PayOrderFreight(ord);
//				} catch (Exception e) {
//			    	m.put("success", "true");
//	  	     	    m.put("type", "1");
//				}
//			}else{
//				m.put("success", "true");
//  	     	    m.put("msg", "订单未支付！");
//	     	    m.put("type", "1");
//  	 		    return m;
//			}
////		}
//		return m;
//	}
	
	/**扣除异常订单运费*/
//	private HashMap<String, Object> payOrderFreight(CL_Order ord) throws Exception {
//		HashMap<String, Object> m = new HashMap<String, Object>();
//		/*BigDecimal d = new BigDecimal(0);
//
//		CL_CouponsDetail cdetail = null;
//		
//		if (ord.getCouponsDetailId() != null
//				&& !"".equals(ord.getCouponsDetailId())) {
//			cdetail = couponsDetailDao.getById(ord.getCouponsDetailId());
//			if (cdetail != null) {
//				if (cdetail.getIsUse() == 1 || cdetail.getIsOverdue() == 1) {
//					m.put("success", "true");
//					m.put("msg", "好运券已使用！");
//					m.put("type", "0");
//					return m;
//				}
//				d = cdetail.getDollars();
//			}
//		}
//		
//		JSONObject ob = new JSONObject();
//		String o = "";
//		CL_UserRole user = userRoleDao.getById(ord.getCreator());
//		o = PayUtil.UserOrderPay(ord.getNumber(), user.getVmiUserPhone(), ord.getFreight().subtract(d).toString());
//		ob = JSONObject.fromObject(o);
//
//		if (ob.get("success").equals("true") || (ob.get("success").equals("false") && ob.get("msg").equals("此订单有支付过"))) {
//
//			ord.setStatus(2);
//			ord.setFpayTime(new Date());
//			this.update(ord);
//
//			if (cdetail != null) {
//				cdetail.setIsUse(1);
//				couponsDetailDao.update(cdetail);
//			}
//
//			financeStatementDao.saveStatement(ord.getId().toString(),
//					ord.getNumber(), 1, ord.getFreight(), -1, ord.getCreator(),
//					ord.getFpayMethod(), user.getVmiUserFid());
//
//			m.put("success", "true");
//			m.put("type", "0");
//			m.put("payed", "true");
//			m.put("msg", "订单支付成功！");
//		}else{
//			m.put("success", "true");
//			m.put("type", "0");
//			m.put("msg", ob.get("msg"));
//		}*/
//
//		return m;
//	}

	@Override
	public List<CL_Order> getBycouponsDetailId(Integer couponsdetatilid) {
		// TODO Auto-generated method stub
		HashMap<String, Object> map=new HashMap<String,Object>();
		map.put("couponsDetailId", couponsdetatilid);
		return this.getSqlSession().selectList(getIbatisSqlMapNamespace()+".getBycouponsDetailId",map);
	}

	@Override
	public Date findOperateTime(Integer userRoleId, Integer status) {
		// TODO Auto-generated method stub
		HashMap<String, Object> map = new HashMap<String,Object>();
		map.put("userRoleId", userRoleId);
		map.put("status", status);
		return this.getSqlSession().selectOne(getIbatisSqlMapNamespace()+".findOperateTime",map);
	}

	@Override
	public int getByUserRoleId(Integer userRoleId, Integer creatorId) {
		// TODO Auto-generated method stub
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("userRoleId", userRoleId);
		map.put("creatorId", creatorId);
		return this.getSqlSession().selectOne(getIbatisSqlMapNamespace()+".getByUserRoleId",map);
	}

	@Override
	public int getUnfinishByUserRoleId(Integer userRoleId) {
		// TODO Auto-generated method stub
		return this.getSqlSession().selectOne(getIbatisSqlMapNamespace()+".getUnfinishByUserRoleId",userRoleId);
	}

	@Override
	public int getNumByRuleSpec(Integer spec) {
		// TODO Auto-generated method stub
		return this.getSqlSession().selectOne(getIbatisSqlMapNamespace()+".getNumByRuleSpec",spec);
	}
	
}
