package com.pc.dao.financeStatement;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.org.rapid_framework.page.Page;
import cn.org.rapid_framework.page.PageRequest;

import com.pc.dao.aBase.IBaseDao;
import com.pc.model.CL_FinanceBill;
import com.pc.model.CL_FinanceStatement;
import com.pc.util.pay.OrderMsg;

/**
 * 收支明细
 */

public interface IFinanceStatementDao extends IBaseDao<CL_FinanceStatement, java.lang.Integer>{
	
	public Page<CL_FinanceStatement> findPage(PageRequest pr);
	
	
	public Page<CL_FinanceStatement> findPageInfo(PageRequest pageRequest);
	//保存明细记录
	public int saveStatement(String frelatedId,String forderId, int fbusinessType, BigDecimal famount, int ftype, int fuserroleId, int fpayType,String fuserid,String... ftime);
//	public int saveStatement(String frelatedId,String forderId, int fbusinessType, BigDecimal famount, int ftype, int fuserroleId, int fpayType,String fuserid);
	
	public CL_FinanceStatement getByNowTime(Date date) ;
	
	public CL_FinanceStatement getByRelatedId(String rid,int payType);
	
	//计算前三名收支明细的司机 by twr
	public  List<CL_FinanceStatement> getByUserIdAndCar(Date date);

	public List<CL_FinanceStatement> getByUserId(Integer userroleid);
	
	//根据收支类型和交易类型查找订单明细 by twr
	public List<CL_FinanceStatement> getByTypeAndBusType(Integer ftype,Integer fbusinessType,Integer userroleid);
	
	//充值明细
	public List<Map<String, Object>> findRechargePage(PageRequest pageRequest);
	
	public Page<Map<String, Object>> findRechargePage1(PageRequest pageRequest);

	//直接到支付收支明细
	Page<CL_FinanceStatement> findPagePays(PageRequest pageRequest);
	
	//直接到支付收支明细导出
	public List<CL_FinanceStatement> findPays(PageRequest query);

	//根据明细Number查找订单明细 
	public List<CL_FinanceStatement> getByNumber(String number);

	//更新订单明细、订单状态 及余额增加;
	public int updateBusinessType(OrderMsg orderMsg);
	//更新订单明细、订单状态 及余额增加; 
	public int updateBusinessType(OrderMsg orderMsg,HashMap<String, Object> param);

	//根据forderId
	public List<CL_FinanceStatement> getforderId(String forderId);

	//查询finance表的相关联frelated_id对应已成功的记录
	public long findFrelatedIdList(Integer frelatedId,Integer fbusinesstype,int userid,int ftype);
	
	
	public List<CL_FinanceStatement> getforderId(String forderId,int fpaytypem,int fbusinesstype);
	
	/**
	 * 根据业务number号和支付类型 获取相关支付信息
	 * @param forderId
	 * @param ftype
	 * @return
	 */
	public List<CL_FinanceStatement> getforderId(String forderId,int fpaytype);
	
	
	/**
	 * 柑橘业务number号和支付类型 获取相关支付信息
	 * @param forderId
	 * @param fpaytype
	 * @param famount
	 * @return
	 */
	public List<CL_FinanceStatement> getforderId(String frelatedId, int fbusinessType, String forderId,int fpaytype,BigDecimal famount);
	
	
	
	
	//查追加时用
	public List<CL_FinanceStatement> getByOrderIdAndBusBusinessType(String forderId,Integer fbusinessType);
	
	/**
	 * 查用户的收支总金额
	 * @param userId 用户id
	 * @param type 收1支-1
	 * @return
	 */
	public List<CL_FinanceStatement> getByUserAndType(Integer userId, Integer type);
	
	public List<CL_FinanceStatement> getByWait();
	
	
}