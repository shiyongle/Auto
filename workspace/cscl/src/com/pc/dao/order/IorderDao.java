package com.pc.dao.order;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.org.rapid_framework.page.Page;
import cn.org.rapid_framework.page.PageRequest;

import com.pc.dao.aBase.IBaseDao;
import com.pc.model.CL_Addto;
import com.pc.model.CL_Order;

public interface IorderDao extends IBaseDao<CL_Order, java.lang.Integer> {
	/**跟新订单表根据订单编号*/
	public int updateByNumber(String orderNum,Integer status);
	/**修改是否常用信息根据订单编号*/
	public int updateByUserId(Integer orderId, Integer isCommon);
	/***根据订单号跟新订单的增值服务*/
	public int updateServerById(CL_Order order);
	
	/**根据状态获取所有订单Id*/
	public List<CL_Order> getIdByStatus(Integer status);
	
	
	/**根据订单ID和创建者*/
	public CL_Order getByIdAndCreator(Integer id,Integer creator);
	
	/**根据userRoleId以及订单状态搜索订单*/
	public List<CL_Order> getByUserRoleIdAndStatus(Integer userRoleId,Integer status);
	
	/**判断当前订单是否被抢，存在则可以继续抢单*/
	public CL_Order IsExistIdByStatus(Integer id);
	
	/**修改订单的用户角色及状态(值：被抢状态，抢单车主ID）*/
	public int updateByOrderId(Integer userRoleId,Integer operator,Integer orderId,Integer status);
	
	/**根据订单编号获得订单*/
	public CL_Order getByNumber(String number);
	
	/**根据订单Id修改订单状态*/
	public int updateStatusByOrderId(Integer orderId,Integer status);
	/** 获取当天订单号最大的订单对象*/
	public CL_Order getByNowTime(Date date);
	/**获取所有派车中的订单*/
	public List<CL_Order> getOrdersByStatus(Integer status);
	
	
	/***根据orderId 查询可以评价订单 id*/
	public  CL_Order  IsExistIdByRating(Integer id);
	
	/**根据table获取最大的编码 BY CC 2016-04-27	 */
	public  Map<Object, Object>  getMaxNOBytable(String table, String NO,Integer length);
	
	/**获取App最新版本 BY cd 2016-09-16	 */
	public  Map<Object, Object> getAppVersion(String bVersionCode,Integer ftype);
	
    /***根据 orderId 跟新 到达提货点时间 **/
	public int updateByArriveTime (Integer orderId,Date date);
    /***根据 orderId 跟新 离开提货点时间 **/
	public int updateByLeaveTime (Integer orderId,Date date);
	/** 根据司机ID获取一星期的锁定金额 */
	public String getLockedAmount(Integer userRoleId, Date fcopTime);
	/***根据ID跟新 订单完成时间和支付（线上线下）*/
	public int updateStatusOnline(Integer id,Integer fonlinePay,Date date);
	/**删除规则时先判断规则是否被订单关联**/
	public int getOrderCountByProtocol(Integer userId,Integer protocolId);
	/***判断订单车主是否评价	 */
    public CL_Order IsExistIdByRatingCar(Integer id  );
    
    /***车主评价直接跟新 订单状态*/
    public int   updateFratingByOrderId(Integer orderId,Integer frating);
    
    public Page<CL_Order> findUploadPage(PageRequest query);
    /**查询用户所有为待付款的用户订单  */
    public List<CL_Order> getByCreator();

    /***根据orderid更新司机总费用 */
    public int updateDriverFeeById(Integer id,BigDecimal fdriverfee);
    
    /**定时器专用跟新*/
    public int updateStatusByOrderIdAndTask(Integer id ,Integer status);
    
    /**更新是否运费上缴*/
    public int updateofflinePayByOrderId(Integer id ,Integer fofflinePay);
    
    /**记录订单下单好运券ID*/
    public int updatecouponsdetailByOrderId(Integer couponsdetatilid, Integer orderid);
    
    /**自动扣除异常订单运费*/
//    public HashMap<String,Object> autoPayOrderFreight(CL_Order order);
    
    /**查询使用好运券的订单  */
    public List<CL_Order> getBycouponsDetailId(Integer couponsdetatilid);
    
    /**查询被调度的司机还在运输中的最早时间 */
    public Date findOperateTime(Integer userRoleId,Integer status);
    
    /**查找司机或者客户是否有订单记录  */
    public int getByUserRoleId(Integer userRoleId, Integer creatorId);
    
    //查询司机是否有未完成的订单
    public int getUnfinishByUserRoleId(Integer userRoleId);
    
    //查询通用司机规则有无被使用
    public int getNumByRuleSpec(Integer spec);
    
    //查询该用户所有使用了优惠券的订单
    public List<CL_Order> getAllCouponsOrder(Integer userId);
    
}
