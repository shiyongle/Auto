package com.pc.dao.financeStatement.impl;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.org.rapid_framework.page.Page;
import cn.org.rapid_framework.page.PageRequest;

import com.pc.controller.Payment.PaymentCallbackController;
import com.pc.dao.UserRole.IUserRoleDao;
import com.pc.dao.aBase.impl.BaseDao;
import com.pc.dao.addto.IaddtoDao;
import com.pc.dao.bank.IbankDao;
import com.pc.dao.couponsActivity.ICouponsActivityDao;
import com.pc.dao.couponsDetail.ICouponsDetailDao;
import com.pc.dao.finance.IFinanceDao;
import com.pc.dao.financeStatement.IFinanceStatementDao;
import com.pc.dao.message.ImessageDao;
import com.pc.dao.order.impl.OrderDao;
import com.pc.dao.umeng.IUMengPushDao;
import com.pc.dao.userBalance.impl.UserBalanceDao;
import com.pc.dao.usercomplain.IUserComplainDao;
import com.pc.model.CL_Addto;
import com.pc.model.CL_Complain;
import com.pc.model.CL_CouponsActivity;
import com.pc.model.CL_CouponsDetail;
import com.pc.model.CL_Finance;
import com.pc.model.CL_FinanceStatement;
import com.pc.model.CL_Order;
import com.pc.model.CL_PayLogs;
import com.pc.model.CL_Umeng_Push;
import com.pc.model.CL_UserBalance;
import com.pc.model.CL_UserRole;
import com.pc.util.CacheUtilByCC;
import com.pc.util.RedisUtil;
import com.pc.util.String_Custom;
import com.pc.util.pay.OrderMsg;
import com.pc.util.pay.PayUtil;
import com.pc.util.push.Demo;

/**
 * 收支明细
 */

@Service("financeStatementDao")
public class FinanceStatementDaoImpl extends BaseDao<CL_FinanceStatement, java.lang.Integer>
		implements IFinanceStatementDao {
	
	private Logger logger = Logger.getLogger(PaymentCallbackController.class);

	@Resource
	private IUserRoleDao userRoleDao;
	@Resource
	private OrderDao orderDao;
	@Resource
	private IaddtoDao iaddtoDao;
	@Autowired
	private IbankDao ibankDao;
	@Resource
	private UserBalanceDao balanceDao;
	@Resource
	private IUserComplainDao userComplainDao;
	@Resource
	private ICouponsDetailDao icouponsDetailDao;
	@Resource
	private ICouponsActivityDao activityDao;

	@Resource
	private IUMengPushDao iumeng;
	@Resource
	private IFinanceDao financeDao;
	@Resource
	private ImessageDao messageDao;

	@Override
	public String getIbatisSqlMapNamespace() {
		return "CL_FinanceStatement";
	}
	
	

	@Override
	public Page<CL_FinanceStatement> findPage(PageRequest pr) {
		return this.pageQuery(getIbatisSqlMapNamespace() + ".findPage", pr);
	}

	@Override
	public CL_FinanceStatement getByNowTime(Date date) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("nowTime", date);
		return this.getSqlSession().selectOne(getIbatisSqlMapNamespace() + ".getByNowTime", map);
	}

	@Override
	public CL_FinanceStatement getByRelatedId(String frelatedId, int fpayType) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("frelatedId", frelatedId);
		if (fpayType != 999)
			map.put("fpayType", fpayType);
		List<CL_FinanceStatement> list = this.getSqlSession().selectList(getIbatisSqlMapNamespace() + ".getByRelatedId",
				map);
		if (list != null && list.size() != 0) {
			return list.get(0);
		} else {
			return null;
		}
	}

	@Override
	public int saveStatement(String frelatedId, String forderId, int fbusinessType, BigDecimal famount, int ftype,
			int fuserroleId, int fpayType, String fuserid, String... ftime) {
		CL_FinanceStatement statement = new CL_FinanceStatement();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		statement.setFcreateTime(new Date());
		try {
			if (ftime != null && ftime.length != 0) {
				statement.setFcreateTime(sdf.parse(ftime[0]));
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		statement.setNumber(CacheUtilByCC.getOrderNumber("cl_finance_statement", "L", 8));
		statement.setFrelatedId(frelatedId);
		statement.setForderId(forderId);
		statement.setFbusinessType(fbusinessType);
		statement.setFamount(famount);
		statement.setFtype(ftype);
		statement.setFuserroleId(fuserroleId);
		statement.setFuserid(fuserid);
		statement.setFpayType(fpayType);
		statement.setFbalance(BigDecimal.ZERO);
		return this.save(statement);
	}

	@Override
	public List<CL_FinanceStatement> getByUserId(Integer userroleid) {
		return this.getSqlSession().selectList(getIbatisSqlMapNamespace() + ".getByUserId", userroleid);
	}

	// 计算前三名收支明细的司机 by twr
	@Override
	public List<CL_FinanceStatement> getByUserIdAndCar(Date date) {
		return this.getSqlSession().selectList(getIbatisSqlMapNamespace() + ".getByUserIdAndCar", date);
	}

	@Override
	public List<CL_FinanceStatement> getByTypeAndBusType(Integer ftype, Integer fbusinessType, Integer userroleid) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("fbusinessType", fbusinessType);
		map.put("ftype", ftype);
		map.put("userroleid", userroleid);
		return this.getSqlSession().selectList(getIbatisSqlMapNamespace() + ".getByTypeAndBusType", map);
	}

	@Override
	public List<Map<String, Object>> findRechargePage(PageRequest pageRequest) {
		List list = getSqlSession().selectList(getIbatisSqlMapNamespace() + ".findRechargePage", pageRequest);
		return list;
	}

	@Override
	public Page<Map<String, Object>> findRechargePage1(PageRequest pageRequest) {
		return this.pageQuery(getIbatisSqlMapNamespace() + ".findRechargePage", pageRequest);
	}

	@Override
	public Page<CL_FinanceStatement> findPagePays(PageRequest pageRequest) {
		return this.pageQuery(getIbatisSqlMapNamespace() + ".findPagePays", pageRequest);
	}

	@Override
	public List<CL_FinanceStatement> findPays(PageRequest query) {
		return this.listQuery(getIbatisSqlMapNamespace() + ".findPays", query);
	}

	@Override
	public Page<CL_FinanceStatement> findPageInfo(PageRequest pageRequest) {
		return this.pageQuery(getIbatisSqlMapNamespace() + ".findPageFromPayInfo", pageRequest);
		/*
		 * HashMap<String, Object> map = new HashMap<String, Object>();
		 * map.put("startLimit", startLimit-1); map.put("fcusid", fcusid);
		 * map.put("endLimit", endLimit); List<CL_FinanceStatement> listTemp=
		 * this.getSqlSession().selectList(getIbatisSqlMapNamespace() +
		 * ".findPageFromPayInfo", map); int count=listTemp.size();
		 * Page<CL_FinanceStatement> temp=new Page<>(1, count,
		 * (Integer)this.getSqlSession().selectOne(getIbatisSqlMapNamespace() +
		 * ".findPageFromPayInfoCount", map), listTemp); return temp;
		 */
	}

	@Override
	public List<CL_FinanceStatement> getforderId(String forderId) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("forderId", forderId);
		return this.getSqlSession().selectList(getIbatisSqlMapNamespace() + ".getByForderId", map);
	}

	@Override
	public List<CL_FinanceStatement> getforderId(String forderId, int fpaytype, int fbusinesstype) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("forderId", forderId);
		if (fpaytype != 99)
			map.put("fpaytype", fpaytype);
		map.put("fbusinesstype", fbusinesstype);
		return this.getSqlSession().selectList(getIbatisSqlMapNamespace() + ".getByForderId", map);
	}

	@Override
	public List<CL_FinanceStatement> getforderId(String frelatedId, int fbusinessType, String forderId, int fpayType,
			BigDecimal famount) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("frelatedId", frelatedId);
		map.put("fbusinesstype", fbusinessType);
		map.put("forderId", forderId);
		map.put("fpaytype", fpayType);
		map.put("famount", famount);
		return this.getSqlSession().selectList(getIbatisSqlMapNamespace() + ".getByForderId", map);
	}

	@Override
	public List<CL_FinanceStatement> getforderId(String forderId, int fpaytype) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("forderId", forderId);
		map.put("fpaytype", fpaytype);
		return this.getSqlSession().selectList(getIbatisSqlMapNamespace() + ".getByForderId", map);
	}

	@Override
	public List<CL_FinanceStatement> getByNumber(String number) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("number", number);
		return this.getSqlSession().selectList(getIbatisSqlMapNamespace() + ".getByNumber", map);
	}

	public int updateBusinessType(OrderMsg ordermsg) {
		return this.updateBusinessType(ordermsg, null);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.pc.dao.financeStatement.IFinanceStatementDao#updateBusinessType(com.
	 * pc.util.pay.OrderMsg) 业务订单状态更新及余额更新通用方法;
	 */
	@Override
	public int updateBusinessType(OrderMsg ordermsg, HashMap<String, Object> param) {
		try {
			RedisUtil.Lock("lock.com.pc.dao.financeStatement.impl.FinanceStatementDaoImpl.updateBusinessType");
			return updateBusinessTypeTrans(ordermsg, param);
		} catch (Exception e) {
			logger.info("回调崩溃信息：" + e.getMessage());
			return 0;
		} finally {
			RedisUtil.del("lock.com.pc.dao.financeStatement.impl.FinanceStatementDaoImpl.updateBusinessType");
		}
		
	}
	
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	private int updateBusinessTypeTrans(OrderMsg ordermsg, HashMap<String, Object> param) {
		CL_FinanceStatement statement = null;
		int resultStatus = 0;
		List<CL_FinanceStatement> statementlist = this.getByNumber(ordermsg
				.getOrder());
		if (statementlist.size() <= 0) {
			writePayErrorLog(statement, 1, "支付系统传输错误订单号！");
			return 0;
		}
		statement = statementlist.get(0);
		// start判断是否是定时器
		if (ordermsg.getUserId() != null && ordermsg.getUserId() == -1) {
			if (statement.getFstatus() == 1) {
				return 1;
			} else if (statement.getFstatus() == 0) {
				statement.setFstatus(6);
				this.update(statement);
				if (statement.getFbusinessType() == 2) {
					CL_Addto addto = (CL_Addto) param.get("addto");
					addto.setFstatus(6);
					iaddtoDao.update(addto);
				}
				return 6;
			}else{
				return 6;
			}
		}
		// end判断是否是定时器
		
		// int resultStatus = 0;
		if (statement != null) {

			// 业务类型fbusinessType(1下单支付2补交货款3运营异常4订单完成5提现6充值7转介绍奖励8货主退款9绑卡)
			switch (statement.getFbusinessType()) {
				case 0 : // 余额支付
				{
					// 先查出业务类型对应的对象的用户ID，再用用户ID取余额balance；
					resultStatus = updateBusinessBalance(ordermsg, statement,
							param);
				}
					break;
				case 1 : // 订单支付
					resultStatus = updatePayOrder(ordermsg, statement);
					break;
				case 2 :// 追加货款
					resultStatus = updateAdditionalPayment(ordermsg, statement, param);
					break;
				case 3 :// 投诉
					resultStatus = updateComplaintAdmissibility(ordermsg,
							statement, param);
					break;
				case 4 :// 司机订单完成（司机收入）
					resultStatus = updateDriverCompletes(ordermsg, statement);
					break;
				case 5 :// 司机提现（司机支出）
					if (param == null) {
						resultStatus = updateDriverPresent(ordermsg, statement,
								null);
					} else {
						resultStatus = updateDriverPresent(ordermsg, statement,
								param);
					}
					break;
				case 6 :// 充值
					resultStatus = updateRecharge(ordermsg, statement);
					break;
				case 7 :// 转介绍
					resultStatus = updateReferrals(ordermsg, statement);
					break;
				case 8 :// 货主退款
					resultStatus = updateShipperRefund(ordermsg, statement,
							param);
					break;
				case 9 :// 绑卡-货主
					resultStatus = updateBoundCard(ordermsg, statement);
					break;
				case 10 :// 订单运费计算
					resultStatus = updateFreightAdjustments(ordermsg,
							statement, param);
					break;
				case 11 :// 司机运费上缴
					resultStatus = updateOfflinepay(ordermsg, statement, param);
					break;
				case 12 :// 司机运费线上(代收货款、运费到付)
					resultStatus = updateOnlinePayment(ordermsg, statement);
					break;
				default :
					break;
			}

		}
		return resultStatus;
	}

	/**
	 * 运费上缴
	 * 
	 * @param ordermsg
	 * @param statement
	 * @return
	 */
	private int updateOfflinepay(OrderMsg ordermsg, CL_FinanceStatement statement, HashMap<String, Object> param) {
		int successstua = updateFinanceBalance(ordermsg, statement);
		if (successstua == 1) {
			int id = Integer.parseInt(param.get("id").toString());
			int type = Integer.parseInt(param.get("type").toString());
			orderDao.updateofflinePayByOrderId(id, type);
		} else {
			return 0;
		}

		return 1;
	}

	/**
	 * 余额调整（增琦）
	 * 
	 * @param ordermsg
	 * @param statement
	 */
	private int updateBusinessBalance(OrderMsg ordermsg, CL_FinanceStatement statement, HashMap<String, Object> param) {
		// 调用余额及明细更新队列方法;
		int successstua = updateFinanceBalance(ordermsg, statement);
		int userId = ordermsg.getUserId();// 缓存的用户id
		if (successstua == 1) {
			int is_update = Integer.parseInt(param.get("is_update").toString());
			int result = 0;
			switch (is_update) {
			case 0:// 不做业务动作
				result = 1;
				break;
			case 1:// 运费调整
				CL_Order order = (CL_Order) param.get("order");
				order.setFauditor(userId);
				order.setFaudit_time(new Date());
				order.setFispass_audit(1);
				result = orderDao.update(order);
				break;
			case 2:// 余额调整
				CL_UserBalance balance = (CL_UserBalance) param.get("balance");
				balance.setFauditor(userId);
				balance.setFaudit_time(new Date());
				balance.setFis_pass_identify(1);
				result = balanceDao.update(balance);
				break;
			case 3:// 运费调整

				break;
			case 4:// 运费调整

				break;

			default:
				break;
			}

			CL_UserBalance balance = balanceDao.getById(Integer.parseInt(statement.getFrelatedId()));
			balance.setFauditor(userId);
			balance.setFaudit_time(new Date());
			balance.setFis_pass_identify(1);
			balanceDao.update(balance);
		} else {
			return 0;
		}
		return 1;
	}

	/**
	 * 订单支付回调(凡毅)
	 * 
	 * @param ordermsg
	 * @param statement
	 */
	private int updatePayOrder(OrderMsg ordermsg, CL_FinanceStatement statement) {
		// 调用余额及明细更新队列方法;
		int successstua = updateFinanceBalance(ordermsg, statement);
		if (successstua == 1) {
			CL_Order order = orderDao.getByNumber(statement.getForderId());
			if (order.getStatus() == 1) {
				// 代付款 订单支付
				order.setStatus(2);
				// 支付成功 更新订单表中的状态
				// orderDao.save(order);
				orderDao.update(order);
			} else {
				writePayErrorLog(statement, 1, "支付订单回调，出现错误，状态为" + order.getStatus());
			}
		} else {
			return 0;
		}

		return 1;

	}

	/**
	 * 追加货款(凡毅)
	 * 
	 * @param ordermsg
	 * @param statement
	 */
	private int updateAdditionalPayment(OrderMsg ordermsg, CL_FinanceStatement statement, HashMap<String, Object> param) {
		int successstua = updateFinanceBalance(ordermsg, statement);
		if (successstua == 1) {
			if (String_Custom.noNull(statement.getFrelatedId()))
				return 0;
			CL_Addto add = iaddtoDao.getById(Integer.parseInt(statement.getFrelatedId()));
			add.setFstatus(1);
			add.setFcost(statement.getFamount());
			String orderNum = statement.getForderId();
			CL_Order order = orderDao.getByNumber(orderNum);
			order.setFtotalFreight(order.getFtotalFreight().add(statement.getFamount()));
			this.iaddtoDao.update(add);
			this.orderDao.update(order);
		}

		return successstua;
	}

	/**
	 * 投诉受理（增琦）
	 * 
	 * @param ordermsg
	 * @param statement
	 */
	private int updateComplaintAdmissibility(OrderMsg ordermsg, CL_FinanceStatement statement,
			HashMap<String, Object> param) {
		int successstua = updateFinanceBalance(ordermsg, statement);
		if (successstua == 1) {
			CL_Complain complain = (CL_Complain) param.get("complain");
			complain.setFisdeal(1);
			complain.setFdealMan(ordermsg.getUserId());
			complain.setFdealTime(new Date());
			userComplainDao.update(complain);
			return 1;
		} else {
			return 0;
		}
	}

	/**
	 * 司机完成(凡毅)
	 * 
	 * @param ordermsg
	 * @param statement
	 */
	private int updateDriverCompletes(OrderMsg ordermsg, CL_FinanceStatement statement) {
		int successstua = updateFinanceBalance(ordermsg, statement);
		if (successstua == 1) {
			CL_Order order = orderDao.getByNumber(statement.getForderId());
			if (order.getStatus() == 3) {
				// 司机订单完成
				if (ordermsg.getUserId() == 1) {
					order.setFonlinePay(1);// 线上
				} else if (ordermsg.getUserId() == 2) {
					order.setFonlinePay(2);// 线下
				}
				order.setStatus(4);
				order.setFcopTime(new Date());
				orderDao.update(order);
			} else {
				// 运输中或 完成订单支付

			}
		}
		return successstua;
	}

	/**
	 * 司机提现(凡毅)
	 * 
	 * @param ordermsg
	 * @param statement
	 */
	private int updateDriverPresent(OrderMsg ordermsg, CL_FinanceStatement statement, HashMap<String, Object> param) {
		int successstua = updateFinanceBalance(ordermsg, statement);
		if (successstua == 1) {
			if (param != null) {
				CL_Finance finance = (CL_Finance) param.get("finance");
				finance.setFhandlerId(ordermsg.getUserId().toString());
				financeDao.update(finance);
			}
		}
		return successstua;
	}

	/**
	 * 充值(诗斌)
	 * 
	 * @param ordermsg
	 * @param statement
	 */
	private int updateRecharge(OrderMsg ordermsg, CL_FinanceStatement statement) {
		int successstua = updateFinanceBalance(ordermsg, statement);
		return successstua;
	}

	/**
	 * 转介绍
	 * 
	 * @param ordermsg
	 * @param statement
	 */
	private int updateReferrals(OrderMsg ordermsg, CL_FinanceStatement statement) {
		return 0;
	}

	/**
	 * 货主退款（增琦）
	 * 
	 * @param ordermsg
	 * @param statement
	 */
	private int updateShipperRefund(OrderMsg ordermsg, CL_FinanceStatement statement, HashMap<String, Object> param) {

		int successstua = updateFinanceBalance(ordermsg, statement);
		if (successstua == 1) {
			CL_Order order = (CL_Order) param.get("order");
			Integer couponsId = order.getCouponsDetailId();
			Integer couponsIds = order.getCouponsId();
			if (couponsId != null) {
				CL_CouponsDetail cpsDetail = (CL_CouponsDetail) param.get("cpsDetail");
				cpsDetail.setIsUse(0);
				order.setCouponsDetailId(null);
				icouponsDetailDao.update(cpsDetail);
			}
			if (couponsIds != null) {
				CL_CouponsDetail cpsDetails = (CL_CouponsDetail) param.get("cpsDetails");
				cpsDetails.setIsUse(0);
				order.setCouponsId(null);
				icouponsDetailDao.update(cpsDetails);
			}
			order.setStatus(6);
			order.setOperator(ordermsg.getUserId());
			order.setFoperate_time(new Date());
			this.orderDao.update(order);
			return 1;
		} else {
			return 0;
		}
	}

	/**
	 * 绑定银行卡(诗斌)
	 * 
	 * @param ordermsg
	 * @param statement
	 */
	private int updateBoundCard(OrderMsg ordermsg, CL_FinanceStatement statement) {
		//
		int successstua = updateFinanceBalance(ordermsg, statement);
		return successstua;
	}

	/**
	 * 订单运费规则调整（增琦）
	 * 
	 * @param ordermsg
	 * @param statement
	 */
	private int updateFreightAdjustments(OrderMsg ordermsg, CL_FinanceStatement statement,
			HashMap<String, Object> param) {

		int successstua = updateFinanceBalance(ordermsg, statement);
		if (successstua == 1) {
			int is_update = Integer.parseInt(param.get("is_update").toString());
			if (is_update == 1) {
				CL_Order order = (CL_Order) param.get("order");
				order.setFauditor(ordermsg.getUserId());
				order.setFaudit_time(new Date());
				order.setFispass_audit(1);
				//更新实际支付金额
				if(statement.getFtype() == -1){//支出
					order.setFtotalFreight(order.getFtotalFreight().add(statement.getFamount()));
				} else {//收入
					order.setFtotalFreight(order.getFtotalFreight().subtract(statement.getFamount()));
				}
				orderDao.update(order);
			}
			return 1;
		} else {
			return 0;
		}

	}

	/**
	 * 司机完成 线上支付(蒋凡毅)
	 * 
	 * @param ordermsg
	 * @param statement
	 * @return
	 */
	private int updateOnlinePayment(OrderMsg ordermsg, CL_FinanceStatement statement) {
		int successstua = updateFinanceBalance(ordermsg, statement);
		if (successstua == 1) {
			// 判断是否有运费到付
			// CL_Order order = orderDao.getByNumber(statement.getForderId());
			// if (order.getFpayMethod()==4) {
			// //包含运费到付 重新发起司机转帐请求
			// writePayErrorLog(statement, 1, "线上支付返回，状态为"+order.getStatus());
			// }
		} else {
			return 0;
		}

		return 1;
	}

	/**
	 * 出错日志写入
	 * 
	 * @param statement
	 *            支付明细对象
	 * @param level
	 *            出错级别
	 * @param context
	 *            出错提示内容
	 */
	private void writePayErrorLog(CL_FinanceStatement statement, int level, String context) {
		CL_PayLogs payLogs = new CL_PayLogs();
		payLogs.setFcreationTime(new Date());
		payLogs.setFcreator(statement.getFusername());
		payLogs.setFcreatorID(statement.getFuserroleId());
		payLogs.setFpayUrl("updateFinanceBalance");
		payLogs.setFpayData("出错收支ID：" + statement.getFid());
		payLogs.setFrespone(context);
		payLogs.setFfinanceNum(statement.getNumber());
		payLogs.setFlevel(level);
		userRoleDao.savePayLogs(payLogs);
	}

	/**
	 * 更新收支明细状态 用户余额 （陈丁）
	 * 
	 * @param ordermsg
	 * @param statement
	 * @return
	 */
//	@Transactional(propagation=Propagation.REQUIRES_NEW)
//	private synchronized int updateFinanceBalance(OrderMsg ordermsg, int statementId) {
	private int updateFinanceBalance(OrderMsg ordermsg, CL_FinanceStatement statement) {
		// TODO Auto-generated method stub
		int resutStatus = 1;
//		CL_FinanceStatement statement = new CL_FinanceStatement();
//		try {
//			RedisUtil.Lock("lock.com.pc.dao.financeStatement.impl.FinanceStatementDaoImpl.updateFinanceBalance");

//			statement = this.getById(statementId);
			
			// logger.info("明细number:"+statement.getNumber()+"start");
			// 支付订单已经存在且相同，说明是并发情况忽略；
			if (!String_Custom.noNull(statement.getFpayOrder())
					&& statement.getFpayOrder().equals(ordermsg.getPayOrder())) {
				// 错误日志 级别2
				writePayErrorLog(statement, 2, "支付系统发起多次重复请求！");
				return 0;
			}
			// // 支付订单已经存在但不相同，说明是支付异常；
			// if (!String_Custom.noNull(statement.getFpayOrder())
			// && statement.getFpayOrder().equals(ordermsg.getPayOrder())) {
			// // 错误日志 级别1
			// writePayErrorLog(statement, 1,
			// "支付订单已经存在但不相同,说明是支付异常,系统暂不自动加余额！");
			// return 0;
			// }
			// 状态是否已经是完成
			if (statement.getFstatus() == 1) {
				// 错误日志 级别1
				if ("1".equals(ordermsg.getPayState()))
					writePayErrorLog(statement, 1, "警告:订单已支付，但再次成功支付！");

				return 0;
			}

			// 判断 两边的支付方式
			// 判断 两边的支付金额

			String contentsOfTheThrust = ""; // 推送内容
			String contentsTitle="";
			statement.setFpayOrder(ordermsg.getPayOrder());// 存储支付系统的支付
			CL_UserRole user = userRoleDao.getById(statement.getFuserroleId());
			user.setVmiUserName(user.getFname());//因sql中将vmiusername用了别名fname，而在update时取的是vmi，导致最终vmiusername为空，因涉及多处代码，姑且重新设置避免二次错误
			BigDecimal balance = user.getFbalance();

			Integer payState = Integer.parseInt(ordermsg.getPayState());
			if (payState == 2) {
				// 出错日志 3
				writePayErrorLog(statement, 3, "非混合支付 存在 支付状态2");
				return 0;
			} else if (payState == 3) {
				statement.setFstatus(2);// 明细状态(0:支付中 1:成功 2:失败 3:退款成功)
				statement.setFbalance(balance);
				this.update(statement);
				resutStatus = 0;
				contentsTitle="订单支付";
				contentsOfTheThrust = statement.getForderId() + "订单支付失败，原因："
						+ ordermsg.getErrorMsg();
				// return 0;
			} else {
				// 判断是否存在业务状态(充值、绑卡手续费0.1)
				if (statement.getFrelatedId() == null
						|| statement.getFrelatedId().length() == 0) {
					// 改状态 增加余额
					user.setFbalance(balance.add(statement.getFamount()));
					if (userRoleDao.update(user) == 1) {
						statement.setFstatus(1);// 明细状态(0:支付中 1:成功 2:失败 3:退款成功)
						statement.setFbalance(balance);
						this.update(statement);
					}

					if (statement.getFbusinessType() == 6) {
						contentsTitle="充值";
						contentsOfTheThrust = "您充值的" + statement.getFamount()
								+ "元，已到账，请注意查收";
					} else if (statement.getFbusinessType() == 9) {
						contentsTitle="绑卡";
						contentsOfTheThrust = "您绑卡成功，0.01元已到您的余额";
					} else {
						writePayErrorLog(statement, 4, "暂不知 什么业务类型走这里过");
						return 0;
					}

					// return 1;
				} else {

					// 特殊状态 司机完成按钮 线上支付
					if (statement.getFbusinessType() == 12
							|| statement.getFbusinessType() == 11) {

						if (this.findFrelatedIdList(
								Integer.valueOf(statement.getFrelatedId()),
								statement.getFbusinessType(),
								statement.getFuserroleId(),
								statement.getFtype()) > 0) {
							writePayErrorLog(statement, 2,"司机订单完成 线上重复支付（运费到付、代收货款）");
							
							statement.setFremark("运费倒付线上重复支付！");
							statement.setFstatus(4);// 明细状态(0:支付中 1:成功 2:失败// 3:退款成功4重复支付6已取消)
							statement.setFbalance(balance);
							this.update(statement);
							return 0;
						}
						// 修改订单状态
						statement.setFstatus(1);// 明细状态(0:支付中 1:成功 2:失败
						// 3:退款成功4重复支付6已取消)
						statement.setFbalance(balance);
						this.update(statement);
						return 1;
					}

					if (this.findFrelatedIdList(
							Integer.valueOf(statement.getFrelatedId()),
							statement.getFbusinessType(),
							statement.getFuserroleId(), statement.getFtype()) > 0) {// 已有相关联frelated_id对应已成功的记录

						if (statement.getFpayType() == 0) {
							statement.setFstatus(6);// 明细状态(0:支付中 1:成功 2:失败
													// 3:退款成功4重复支付6已取消)
							statement.setFbalance(balance);
							this.update(statement);
							// 错误日志级别 4 ---运营端 同接口多次并发 导致的
							writePayErrorLog(statement, 4, "运营端 同接口多次并发 导致的");
							return 0;
						}

						user.setFbalance(balance.add(statement.getFamount()));

						if (userRoleDao.update(user) == 1) {
							statement.setFstatus(4);// 明细状态(0:支付中 1:成功 2:失败
													// 3:退款成功4重复支付)
							statement.setFbalance(balance);
							this.update(statement);
							HashMap<String, Object> toParm = new HashMap<>();
							toParm.put("famount", statement.getFamount());
							toParm.put("fbalance", statement.getFbalance());
							toParm.put("fbusinessType",
									statement.getFbusinessType());
							toParm.put("forderId", statement.getForderId());
							toParm.put("fpayType", statement.getFpayType());
							toParm.put("freight", statement.getFreight());
							toParm.put("fpayOrder", statement.getFpayOrder());
							toParm.put("frelatedId", statement.getFrelatedId());
							toParm.put("fremark", statement.getFremark());
							toParm.put("fstatus", "3");
							toParm.put("fcusid", statement.getFuserroleId());
							toParm.put("FinanceDao", this);

							PayUtil.createPayDetails(toParm,
									statement.getFuserid());
						}

						resutStatus = 0;
						contentsTitle="订单支付";
						contentsOfTheThrust = "重复支付的订单费用，已返回"
								+ statement.getFamount() + "元到您的余额";
						// return 0;
					} else {

						// 存在业务状态 判断是否混合支付
						BigDecimal freight = statement.getFreight(); // 订单需支付金额
						BigDecimal famount = statement.getFamount(); // 实际支付金额
						if (freight.compareTo(famount) == 0) {

							// 非混合支付
							// 成功
							statement.setFstatus(1);
							if (statement.getFtype() == 1) { // 收
								user.setFbalance(balance.add(statement
										.getFamount()));
								userRoleDao.update(user);
							} else {
								// 支
								if (ordermsg.getServiceProvider().equals("0")) {
									if (statement.getFbusinessType() == 0) {
										if (balance
												.compareTo(new BigDecimal(0)) == -1)
											return 0; // 余额不能为负
									} else {
										if (statement.getFbusinessType() != 10 && balance.compareTo(statement
												.getFamount()) == -1)
											return 0; // 余额不足不能付款
									}
									user.setFbalance(balance.subtract(statement
											.getFamount()));
									userRoleDao.update(user);
								}
							}
							statement.setFbalance(balance);
							this.update(statement);
							switch (statement.getFbusinessType()) {
								case 0 :
									contentsTitle="余额调整";
									contentsOfTheThrust = "您的余额被调整，请核实，有疑问请联系客服";
									break;
								case 1 :
									contentsTitle="订单支付";
									contentsOfTheThrust = "您的订单已支付成功,订单号为"
											+ statement.getForderId();
									break;
								case 2 :
									contentsTitle="订单追加 ";
									contentsOfTheThrust = "您订单追加的费用"
											+ statement.getFamount()
											+ "元已成功,订单号为"
											+ statement.getForderId();
									break;
								case 3 :
									contentsTitle="异常受理 ";
									contentsOfTheThrust = "您有一笔投诉已受理，详情请查询支付明细";
									break;
								case 4 :
								case 11 :
									contentsTitle="订单运费";
									contentsOfTheThrust = "您的订单运费"
											+ statement.getFamount()
											+ "元已到账,订单号为"
											+ statement.getForderId() + "请查收!";
									break;
								case 5 :
									contentsTitle="提现申请";
									contentsOfTheThrust = "您的提现申请已提交，正在审查中...";
									break;
								case 6 :
									contentsTitle="充值";
									contentsOfTheThrust = "您充值的"
											+ statement.getFamount()
											+ "元，已到账，请注意查收";
									break;
								case 8 :
									contentsTitle="订单取消";
									contentsOfTheThrust = "您的订单取消成功，运费"
											+ statement.getFamount()
											+ "元，已退回到您的余额，请注意查收";
									break;
								case 10 :
									contentsTitle="运费调整";
									contentsOfTheThrust = "您的订单"+ statement.getForderId() +"运费已调整，如有疑问请联系客服";
									break;
								default :
									break;
							}
							// return 1;

						} else if (freight.compareTo(famount) == 1) {
							BigDecimal shouldBalance = statement.getFreight()
									.subtract(statement.getFamount());

							if (statement.getFtype() == 1) {
								// 出错日志 级别1
								writePayErrorLog(statement, 1,
										"混合支付 居然有请求加钱的 被人攻击了");
								return 0;
							}

							if (shouldBalance.compareTo(balance) <= 0) {// 混合支付,余额足够扣除

								statement.setFstatus(1);// 明细状态(0:支付中 1:成功 2:失败
														// 3:退款成功)
								statement.setFbalance(balance);
								this.update(statement);

								user.setFbalance(balance
										.subtract(shouldBalance));
								userRoleDao.update(user);

								// 增加余额支付明细
								HashMap<String, Object> toParm = new HashMap<>();
								toParm.put("famount", shouldBalance);
								toParm.put("fbalance", balance);
								toParm.put("fbusinessType",
										statement.getFbusinessType());
								toParm.put("forderId", statement.getForderId());
								toParm.put("fpayType", "0");
								toParm.put("freight", statement.getFreight());
								toParm.put("fpayOrder",
										statement.getFpayOrder());
								toParm.put("frelatedId",
										statement.getFrelatedId());
								toParm.put("fremark", statement.getFremark());
								toParm.put("ftype", -1);
								toParm.put("fstatus", "1");
								toParm.put("fcusid", statement.getFuserroleId());
								toParm.put("FinanceDao", this);

								PayUtil.createPayDetails(toParm,
										statement.getFuserid());
								if (statement.getFbusinessType() == 1)
								{
									contentsTitle="订单支付";
									contentsOfTheThrust = "您的订单已支付成功,订单号为"
											+ statement.getForderId();
								}
								// return 1;
							} else {// 混合支付,余额不够扣除,订单失败

								user.setFbalance(balance.add(statement
										.getFamount()));
								if (userRoleDao.update(user) == 1) {
									statement.setFstatus(2);// 明细状态(0:支付中 1:成功
															// 2:失败3:退款成功)
									statement.setFbalance(balance);
									this.update(statement);
									HashMap<String, Object> toParm = new HashMap<>();
									toParm.put("famount",
											statement.getFamount());
									toParm.put("fbalance", balance);
									toParm.put("fbusinessType",
											statement.getFbusinessType());
									toParm.put("forderId",
											statement.getForderId());
									toParm.put("fpayType",
											statement.getFpayType());
									toParm.put("freight",
											statement.getFreight());
									toParm.put("fpayOrder",
											statement.getFpayOrder());
									toParm.put("frelatedId",
											statement.getFrelatedId());
									toParm.put("fremark",
											statement.getFremark());
									toParm.put("fstatus", "3");
									toParm.put("fcusid",
											statement.getFuserroleId());
									toParm.put("FinanceDao", this);

									PayUtil.createPayDetails(toParm,
											statement.getFuserid());
								}

								resutStatus = 0;
								if (statement.getFbusinessType() == 1)
								{
									contentsTitle="订单支付";
									contentsOfTheThrust = "您的混合支付失败,第三方支付成功的金额"
											+ statement.getFamount()
											+ "元已返还到您的余额,订单号为"
											+ statement.getForderId() + ""
											+ "原因为：使用余额扣款部分，余额不足";
								}
									
								// return 0;
							}

						} else {
							// 出错日志 级别1
							writePayErrorLog(statement, 1, "订单支付金额 居然 大于订单运费");
							return 0;
						}
					}

				}
			}

			// 推送
			// CsclPushUtil.SendPushToAllSound(statement.getFuserroleId() + "",
			// contentsOfTheThrust);
			sendUmengToService(user.getId(),contentsTitle, contentsOfTheThrust);
			
			//充值送优惠券推送
		if (statement.getFbusinessType() == 6) {// 充值送优惠券
			List<CL_CouponsActivity> acticityList = activityDao
					.getActivityByTopUpDollars(statement.getFamount());
			BigDecimal MaxDecimal = BigDecimal.ZERO;// 最接近充值金额的区间
			BigDecimal MaxDecimal2 = BigDecimal.ZERO;
			BigDecimal MaxDecimal3 = BigDecimal.ZERO;// 最接近最大区间的区间
			BigDecimal MinDecimal = acticityList.get(0).getFtopUpDollars();// 先随便定义一个最小的区间
			BigDecimal subDecimal = BigDecimal.ZERO;// 金额减去最大区间剩余的钱
			int activityCount = 0;
			if (acticityList.size() > 0) {
				for (CL_CouponsActivity couponsActivity : acticityList) {
					if (couponsActivity.getFtopUpDollars().compareTo(MaxDecimal) > 0) {
						MaxDecimal = couponsActivity.getFtopUpDollars();
					}
					if (couponsActivity.getFtopUpDollars()
							.compareTo(MinDecimal) < 0) {
						MinDecimal = couponsActivity.getFtopUpDollars();
					}
				}
				// 通过最接近充值金额的活动满足金额查所有这个金额的活动
				List<CL_CouponsActivity> actList = activityDao
						.getActivityByDollars(MaxDecimal);
				activityCount = actList.size();
				sendUmengToService(user.getId(), "充值送",
						"您充值" + statement.getFamount() + "元,获赠" + MaxDecimal
								+ "元优惠券" + activityCount + "张",1);
				for (CL_CouponsActivity couponsActivity : actList) {
					couponsActivity.setFid(null);
					couponsActivity.setFactivityType(2);
					couponsActivity.setFissueUser(user.getVmiUserPhone());
					activityDao.save(couponsActivity);
				}

				MaxDecimal2 = MaxDecimal;//最大区间
				subDecimal = statement.getFamount().subtract(MaxDecimal);// 充值金额减去最大区间剩下的钱
				if (subDecimal.compareTo(MaxDecimal) < 0) {//剩下的钱比最大区间小
					while (subDecimal.compareTo(MinDecimal) >= 0) {// 剩下的钱大于最小区间
						for (CL_CouponsActivity couponsActivity : acticityList) {// 求之后的最大区间
							if (couponsActivity.getFtopUpDollars().compareTo(
									MaxDecimal2) < 0
									&& couponsActivity.getFtopUpDollars()
											.compareTo(MaxDecimal3) > 0) {
								MaxDecimal3 = couponsActivity
										.getFtopUpDollars();
							}
						}
						MaxDecimal2 = MaxDecimal3;
						if(subDecimal.subtract(MaxDecimal3).compareTo(BigDecimal.ZERO) >= 0){
							subDecimal = subDecimal.subtract(MaxDecimal3);
							// 通过最接近充值金额的活动满足金额查所有这个金额的活动
							List<CL_CouponsActivity> actList1 = activityDao
									.getActivityByDollars(MaxDecimal3);
							activityCount = actList.size();
							sendUmengToService(user.getId(), "充值送", "您充值"
									+ statement.getFamount() + "元,获赠" + MaxDecimal3
									+ "元优惠券" + activityCount + "张",1);
							for (CL_CouponsActivity couponsActivity : actList1) {
								couponsActivity.setFid(null);
								couponsActivity.setFactivityType(2);
								couponsActivity.setFissueUser(user.getVmiUserPhone());
								activityDao.save(couponsActivity);
							}
						}
					}

				} else if (subDecimal.compareTo(MaxDecimal) == 0) {
					sendUmengToService(user.getId(), "充值送",
							"您充值" + statement.getFamount() + "元,获赠"
									+ MaxDecimal + "元优惠券" + activityCount + "张",1);
					for (CL_CouponsActivity couponsActivity : actList) {
						couponsActivity.setFid(null);
						couponsActivity.setFactivityType(2);
						couponsActivity.setFissueUser(user.getVmiUserPhone());
						activityDao.save(couponsActivity);
					}
					
				} else {
					BigDecimal divide = subDecimal.divide(MinDecimal).setScale(
							0, BigDecimal.ROUND_HALF_DOWN);// 取商
					BigDecimal remain = subDecimal.remainder(MinDecimal);// 取余
					sendUmengToService(
							user.getId(),
							"充值送",
							"您充值"
									+ statement.getFamount()
									+ "元,获赠"
									+ MaxDecimal
									+ "元优惠券"
									+ new BigDecimal(activityCount)
											.multiply(divide) + "张",1);
					for (int i = 0; i < divide.intValue(); i++) {
						for (CL_CouponsActivity couponsActivity : actList) {
							couponsActivity.setFid(null);
							couponsActivity.setFactivityType(2);
							couponsActivity.setFissueUser(user.getVmiUserPhone());
							activityDao.save(couponsActivity);
						}
					}
					while (remain.compareTo(MinDecimal) > 0) {// 剩下的钱大于最小区间
						for (CL_CouponsActivity couponsActivity : acticityList) {// 求之后的最大区间
							if (couponsActivity.getFtopUpDollars().compareTo(
									MaxDecimal2) < 0
									&& couponsActivity.getFtopUpDollars()
											.compareTo(MaxDecimal3) > 0) {
								MaxDecimal3 = couponsActivity
										.getFtopUpDollars();
							}
						}
						MaxDecimal2 = MaxDecimal3;
						if (remain.subtract(MaxDecimal3).compareTo(BigDecimal.ZERO) >= 0) {
							remain = remain.subtract(MaxDecimal3);
							// 通过最接近充值金额的活动满足金额查所有这个金额的活动
							List<CL_CouponsActivity> actList2 = activityDao
									.getActivityByDollars(MaxDecimal3);
							activityCount = actList.size();
							sendUmengToService(user.getId(), "充值送", "您充值"
									+ statement.getFamount() + "元,获赠"
									+ MaxDecimal3 + "元优惠券" + activityCount
									+ "张",1);
							for (CL_CouponsActivity couponsActivity : actList2) {
								couponsActivity.setFid(null);
								couponsActivity.setFactivityType(2);
								couponsActivity.setFissueUser(user.getVmiUserPhone());
								activityDao.save(couponsActivity);
							}
						}
					}
				}

			}
		}
			// logger.info("明细number:"+statement.getNumber()+"end");
//		} catch (Exception e) {
//			// 出错日志 级别1
//			writePayErrorLog(statement, 1, "回调崩溃信息：" + e.getMessage());
//			return 0;
//		} finally {
//			RedisUtil
//					.del("lock.com.pc.dao.financeStatement.impl.FinanceStatementDaoImpl.updateFinanceBalance");
//		}
		return resutStatus;

	}

	/**
	 * 推送服务
	 * 
	 * @param fuserid
	 *            推送给的用户id
	 * @param msg
	 *            推送内容
	 */
	public void sendUmengToService(int userid, String title,String msg) {

		List<CL_Umeng_Push> umengList = iumeng.getUserUmengRegistration(userid);
		if (umengList .size() > 0) {
			// 获取到用户多台设备
			for (CL_Umeng_Push cl_Umeng_Push : umengList) {
				Demo.SendUmeng(cl_Umeng_Push.getFdevice(), msg, cl_Umeng_Push.getFdeviceType());
			}
			messageDao.save(Demo.savePushMessage(title,msg, userid, umengList.get(0).getFuserType()));
		}
	}
	
	/**
	 * 推送服务
	 * 
	 * @param fuserid
	 *            推送给的用户id
	 * @param msg
	 *            推送内容
	 * @param businessType
	 *            业务类型：1充值送
	 *
	 */
	public void sendUmengToService(int userid, String title,String msg, int businessType) {
		
		List<CL_Umeng_Push> umengList = iumeng.getUserUmengRegistration(userid);
		if (umengList .size() > 0) {
			// 获取到用户多台设备
			for (CL_Umeng_Push cl_Umeng_Push : umengList) {
				Demo.SendUmeng(cl_Umeng_Push.getFdevice(), msg, cl_Umeng_Push.getFdeviceType());
			}
			messageDao.save(Demo.savePushMessage(title,msg, userid, umengList.get(0).getFuserType(), businessType));
		}
	}
	

	@Override
	public long findFrelatedIdList(Integer frelatedId, Integer fbusinesstype, int userid, int ftype) {
		HashMap<String, Integer> map = new HashMap<String, Integer>();
		map.put("frelatedId", frelatedId);
		map.put("fbusinesstype", fbusinesstype);
		map.put("userid", userid);
		map.put("ftype", ftype);
		return this.getSqlSession().selectOne(getIbatisSqlMapNamespace() + ".findFrelatedIdList", map);
	}

	@Override
	public List<CL_FinanceStatement> getByOrderIdAndBusBusinessType(String forderId, Integer fbusinessType) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("forderId", forderId);
		map.put("fbusinessType", fbusinessType);
		return this.getSqlSession().selectList(getIbatisSqlMapNamespace() + ".getByOrderIdAndBusBusinessType", map);
	}

	@Override
	public List<CL_FinanceStatement> getByUserAndType(Integer userId, Integer type) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("userId", userId);
		map.put("type", type);
		return this.getSqlSession().selectList(getIbatisSqlMapNamespace() + ".getByUserAndType", map);
	}

	@Override
	public List<CL_FinanceStatement> getByWait() {
		return this.getSqlSession().selectList(getIbatisSqlMapNamespace() + ".getByWait");
	}

	
}
