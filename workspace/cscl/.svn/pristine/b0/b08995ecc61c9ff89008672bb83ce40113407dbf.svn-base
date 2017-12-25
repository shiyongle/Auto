package com.pc.dao.addto;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.pc.dao.aBase.IBaseDao;
import com.pc.model.CL_Addto;

public interface IaddtoDao extends  IBaseDao<CL_Addto, java.lang.Integer>{
	 /*** 拿到最大编号 流水号*/
	public CL_Addto getByNowTime(Date date);

	/*** 根据Number修改 追加费用支付是否成功*/
	public int updateByNumberApp(String fpatNumber,Integer fstatus,BigDecimal fcost,Integer payMethod);
	/*** 根据Id修改 追加费用支付是否成功*/
	public int updateByIdApp(Integer id,Integer fstatus,BigDecimal fcost,Integer payMethod);
	
	/*** 根据Number修改 追加费用支付是否成功*/
	public int updateByNumber(String fpatNumber,Integer fstatus);
	/*** 根据Id修改 追加费用支付是否成功*/
	public int updateById(Integer id,Integer fstatus);
	
	/*** 根据number 查数据库只有唯一数据*/
	public List<CL_Addto> getByNumber(String fpayNumber);
	
	
	/****根据 orderId 获得所有 成功支付的*/
	public  List<CL_Addto> getByOrderId(Integer id);
	
	public int updateDriverfee(Integer id,BigDecimal fdriverfee);
    
    /**自动扣除异常追加订单运费*/
//    public HashMap<String,Object> autoPayAddtoFreight(CL_Addto addto);
	
    /**查询用户所有为待付款的用户订单  */
    public List<CL_Addto> getNonPayment();
	
    /**根据订单ID查询未付款的追加运费单  */
    public List<CL_Addto> getNonPaymentByOrderID(Integer orderId);

    /**定时器专用跟新*/
    public int updateStatusByFidAndTask(Integer fid, Integer fstatus);
}
