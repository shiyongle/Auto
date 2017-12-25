package com.pc.dao.addto.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.apache.http.util.TextUtils;
import org.springframework.stereotype.Service;

import com.pc.appInterface.api.DongjingClient;
import com.pc.dao.UserRole.IUserRoleDao;
import com.pc.dao.aBase.impl.BaseDao;
import com.pc.dao.addto.IaddtoDao;
import com.pc.dao.financeStatement.impl.FinanceStatementDaoImpl;
import com.pc.dao.order.IorderDao;
import com.pc.model.CL_Addto;
import com.pc.model.CL_Order;
import com.pc.model.CL_UserRole;
import com.pc.util.Base64;
import com.pc.util.CalcTotalField;
import com.pc.util.ServerContext;
import com.pc.util.pay.PayUtil;
@Service("addtoDao")
public class addtoDao extends  BaseDao<CL_Addto, java.lang.Integer> implements IaddtoDao{
	@Resource
	private IUserRoleDao userRoleDao;
	@Resource
	private FinanceStatementDaoImpl financeStatementDao;
	@Resource
	private IorderDao  orderDao;
	
	@Override
	public String getIbatisSqlMapNamespace() {
		return "CL_Addto";
	}

	@Override
	public CL_Addto getByNowTime(Date date) {
	  HashMap<String,Object> map =new HashMap<String,Object>();
	  map.put("nowTime", date);
	  return this.getSqlSession().selectOne(getIbatisSqlMapNamespace()+".getByNowTime",map);
	}

	@Override
	public int updateByIdApp(Integer id, Integer fstatus,BigDecimal fcost,Integer payMethod) {
		  HashMap<String,Object> map =new HashMap<String,Object>();
		  map.put("id", id);
		  map.put("fstatus", fstatus);
		  map.put("fcost", fcost);
		  map.put("fpayMethod", payMethod);
		  return this.getSqlSession().update(getIbatisSqlMapNamespace()+".updateByIdApp",map);
	}

	@Override
	public int updateById(Integer id, Integer fstatus) {
		  HashMap<String,Object> map =new HashMap<String,Object>();
		  map.put("id", id);
		  map.put("fstatus", fstatus);
		  return this.getSqlSession().update(getIbatisSqlMapNamespace()+".updateById",map);
	}

	@Override
	public int updateByNumberApp(String fpatNumber, Integer fstatus,BigDecimal fcost,Integer payMethod) {
		  HashMap<String,Object> map =new HashMap<String,Object>();
		  map.put("fpatNumber", fpatNumber);
		  map.put("fstatus", fstatus);
		  map.put("fcost", fcost);
		  map.put("fpayMethod", payMethod);
		  return this.getSqlSession().update(getIbatisSqlMapNamespace()+".updateByNumberApp",map);

	}

	@Override
	public int updateByNumber(String fpatNumber, Integer fstatus) {
		  HashMap<String,Object> map =new HashMap<String,Object>();
		  map.put("fpatNumber", fpatNumber);
		  map.put("fstatus", fstatus);
		  return this.getSqlSession().update(getIbatisSqlMapNamespace()+".updateByNumber",map);

	}

	@Override
	public List<CL_Addto> getByNumber(String fpayNumber) {
		return this.getSqlSession().selectList(getIbatisSqlMapNamespace()+".getByNumber",fpayNumber);
	}

	@Override
	public List<CL_Addto> getByOrderId(Integer id) {
		return  this.getSqlSession().selectList(getIbatisSqlMapNamespace()+".getByOrderId",id);
	}
	
	@Override
	public int updateDriverfee(Integer id,BigDecimal fdriverfee) {
		HashMap<String, Object> map =new HashMap<String,Object>();
		map.put("id", id);
		map.put("fdriverfee", fdriverfee);
		return  this.getSqlSession().update(getIbatisSqlMapNamespace()+".updateDriverfee",map);
	}

//	@Override
	/**自动扣除异常追加订单运费*/
//	public synchronized HashMap<String, Object> autoPayAddtoFreight(CL_Addto addto) {
//		HashMap<String,Object> m = new HashMap<String,Object>();
//		
//		/*String payNumber = addto.getFpayNumber();
//		boolean ispay = false;
//		if(addto.getFstatus()==1){
//			m.put("success", "true");
//			m.put("msg","该订单异常追加运费付款成功！");
//			m.put("type", "0");
//			return m;
//		}
//			 
//		//支付校验
//		JSONObject jo;
//		JSONObject jo1;
//		String UserOrderPayOver=ServerContext.getBaseurl()+"/Action_Pay/DJPay/Pay/UserOrderPayOverAll";
////		UserOrderPayOver = UserOrderPayOver +"?&XDEBUG_SESSION_START=17402";
//		jo = new JSONObject();
//		jo1 = new JSONObject();
//		jo1.put("Number", payNumber);
//		jo.put("data", jo1);
//		DongjingClient PayClient=new DongjingClient();
//		PayClient.setRequestProperty("data", Base64.encode(jo.toString().getBytes()));
//		ispay = PayClient.SubmitUrlData(UserOrderPayOver);
//		String payData = PayClient.SubmitUrlData(UserOrderPayOver);     //请求错误
//		JSONObject jsonobject = JSONObject.fromObject(payData);
//		ispay=jsonobject.getBoolean("success");
//		JSONObject json = (JSONObject)jsonobject.get("data");
//		ispay = false;
//		//订单扣款改状态
//		if(ispay){
//			String paytype = json.has("PayType") ? json.getString("PayType"): "";
//			String payMoney = json.has("Money") ? json.getString("Money") : "0";
//			addto.setFcost(new BigDecimal(payMoney));
//			addto.setFpayMethod(2);
//			if (TextUtils.isEmpty(paytype)) {
//			} else if (paytype.contains("微信")) {
//				addto.setFpayMethod(2);
//			} else if (paytype.contains("银")) {
//				addto.setFpayMethod(3);
//			} else if (paytype.contains("支付宝")) {
//				addto.setFpayMethod(1);
//			}
//			try {
//				m=payAddtoFreight(addto);
//			} catch (Exception e) {
//		    	m.put("success", "true");
//  	     	    m.put("type", "0");
//			}
//		}else{
//			m.put("success", "true");
//     	    m.put("msg", "订单未支付！");
//     	    m.put("type", "0");
// 		    return m;
//		}*/
//		return m;
//	}

	/**扣除异常追加订单运费*/
	private HashMap<String, Object> payAddtoFreight(CL_Addto add) throws Exception {
		HashMap<String, Object> m = new HashMap<String, Object>();
		BigDecimal d = new BigDecimal(0);
		JSONObject ob = new JSONObject();
		String o = "";
		CL_UserRole user = userRoleDao.getById(add.getFcreateor());
//		o = PayUtil.UserOrderPay(add.getFpayNumber(),
//				user.getVmiUserPhone(),
//				String.valueOf(add.getFcost().doubleValue()));
		ob = JSONObject.fromObject(o);
		if (ob.get("success").equals("true") || (ob.get("success").equals("false") && ob.get("msg").equals("此订单有支付过"))) {

			add.setFstatus(1);
			this.update(add);

			int orderid = add.getForderId();
			String ordernum = "";
			CL_Order order = orderDao.getById(orderid);
			if (order != null) {
				ordernum = order.getNumber();
			}

			financeStatementDao.saveStatement(add.getFid().toString(),
					ordernum, 2, add.getFcost(), -1, add.getFcreateor(),
					add.getFpayMethod(), add.getFcreateor().toString());

			/*** 追加费用计算司机运费 Start */
			List<CL_Addto> addto = this.getByOrderId(orderid);// 查询当前所有支付成功的追加费用
			BigDecimal AllCost = order.getFreight();// 初始费用取订单费用，总费=订单运费+所有追加运费
													// BY CC 2016-05-18
			if (addto.size() > 0) {
				for (CL_Addto ad : addto) {
					AllCost = AllCost.add(ad.getFcost());
				}
			}
			// AllCost=AllCost.add(order.getFreight()).setScale(1,BigDecimal.ROUND_HALF_UP);//所有追加费用加上订单原始运费
			// 根据总价格获取管理费比例 BY CC 2016-05-18
			BigDecimal discount = CalcTotalField.calDriverFee(AllCost.setScale(
					1, BigDecimal.ROUND_HALF_UP));
			// 循环修改追加费用司机费用 BY CC 2016-05-18
			for (CL_Addto ad : addto) {
				this.updateDriverfee(
						ad.getFid(),
						ad.getFcost().multiply(discount)
								.setScale(1, BigDecimal.ROUND_HALF_UP));
			}
			/*orderDao.updateDriverFeeById(
					orderid,
					order.getFreight().multiply(discount)
							.setScale(1, BigDecimal.ROUND_HALF_UP));// 更新订单表中司机的总运费
*/			/*** 追加费用计算司机运费 End */

			m.put("success", "true");
			m.put("type", "1");
			m.put("payed", "true");
			m.put("msg", "订单支付成功！");
		}else{
			m.put("success", "false");
			m.put("type", "0");
			m.put("fcost", add.getFcost());
			m.put("msg", ob.get("msg"));
		}

		return m;
	}

	@Override
	public List<CL_Addto> getNonPayment() {
			return this.getSqlSession().selectList(getIbatisSqlMapNamespace()+".getNonPayment");
		}

	@Override
	public List<CL_Addto> getNonPaymentByOrderID(Integer orderId) {
		HashMap<String, Object> map =new HashMap<String,Object>();
		map.put("forderId", orderId);
		return this.getSqlSession().selectList(getIbatisSqlMapNamespace()+".getNonPaymentByOrderID",map);
	}

	@Override
	public int updateStatusByFidAndTask(Integer fid, Integer fstatus) {
		HashMap<String, Object> map=new HashMap<String,Object>();
		map.put("fid",fid);
		map.put("fstatus", fstatus);
		return this.getSqlSession().update(getIbatisSqlMapNamespace()+".updateStatusByFidAndTask",map);
	}
	
}
