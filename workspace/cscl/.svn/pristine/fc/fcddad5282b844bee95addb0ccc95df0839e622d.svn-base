package com.pc.dao.orderDetail;

import java.util.List;

import com.pc.dao.aBase.IBaseDao;
import com.pc.model.CL_OrderDetail;

public interface IorderDetailDao extends IBaseDao<CL_OrderDetail, java.lang.Integer> {
	
	public List<CL_OrderDetail> getByPCOrderId(Integer orderId);
	
	public List<CL_OrderDetail> getByOrderId(Integer orderId,Integer detailType);
	
	public List<CL_OrderDetail> getByOrderId(Integer orderId);
	
	/*** 通过订单ID获取订单明细记录中发货地址为第一条的订单明细记录*/
	public CL_OrderDetail getByOrderIdForDeliverAddress(Integer orderId);
	
	/*** 通过订单ID获取订单明细记录中收货地址为最后一条的订单明细记录*/
	public CL_OrderDetail getByOrderIdForConsigneeAddress(Integer orderId);
   /*** 通过订单ID和订单明细类型 获得总行数*/
    public int findCount(Integer orderId,Integer detailType);
    /** 通过订单明细ID 和 随机码 获得订单明细*/
    public CL_OrderDetail getByIdAndCode(Integer orderDetailId,String securityCode);
    /** 通过订单明细ID 更新 是否通过验证 */
    public int  updatePass(Integer orderDetailId,Integer pass);
    /*** 通过订单ID获取订单明细记录中所有卸货地址的订单明细记录*/
    public List<CL_OrderDetail> getByPCOrderIdForDeliver(Integer orderId);
    
    //为防止有出入，专门为app再次下单接口改一个方法 BY LANCHER
    public List<CL_OrderDetail> getByNewOrderId(Integer orderId,Integer detailType);
}
