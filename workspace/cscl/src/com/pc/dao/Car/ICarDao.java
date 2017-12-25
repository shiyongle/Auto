package com.pc.dao.Car;




import java.util.List;

import com.pc.dao.aBase.IBaseDao;
import com.pc.model.CL_Car;
/***
 * wangc 
 * */
public interface ICarDao extends IBaseDao<CL_Car, java.lang.Integer>{

		
   /***根据userRoleId获取司机资料 */
	public List<CL_Car> getByUserRoleId(Integer id);

	/***根据userId获取车型 */
	public CL_Car getCarTypeByUserRoleId(Integer UserRoleId);
	
	/**根据car_type_id获取user_role_id**/
	public List<CL_Car> getUrIdByCarType(Integer carType);
	
	/**根据CarSpecId获取车辆信息**/
	public List<CL_Car> getByCarSpecId(Integer carSpecId);
	/***根据规格ID和车型ID获取车辆信息*/
	public List<CL_Car> getByCarSpecIdAndTypeId(Integer carSpecId,Integer carTypeId);
	
	/*** 根据ID 设置好运司机 或者取消好运司机**/
	public int updateLuckDr(CL_Car car);
	
	public List<CL_Car> getAllCar();
}
