package com.pc.dao.userVoucher;



import java.util.List;

import com.pc.dao.aBase.IBaseDao;
import com.pc.model.CL_UserVoucher;

public interface IuserVoucherDao extends IBaseDao<CL_UserVoucher, java.lang.Integer>{
	
	/**根据订单Id修改订单状态*/
	public int updateByStatus(Integer orderId,Integer fstatus,Integer fcreator );

	/**根据id和创建人查询充值订单*/
	public CL_UserVoucher getByIdAndFcreator(Integer fid, Integer fcreator );

	 /**查询所以无效的订单*/
	  public List<CL_UserVoucher> getByStatusInTast();

     /**批量修改过期*/
      public int updateVouCherDetail(Integer[] ids);
      
     /**批量校验通过*/
      public int updateVouCherStatusDetail(Integer[] ids);
    
      /**根据充值订单forderNumber找 是否有充值记录      */
      public CL_UserVoucher getByForderNumber(String forderNumber,Integer fcreator);
}



