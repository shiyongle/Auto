package com.pc.dao.Car.impl;


import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Service;

import com.pc.dao.Car.ICarDao;
import com.pc.dao.aBase.impl.BaseDao;
import com.pc.model.CL_Car;
/*** wangc
 * 
 * */
@Service("carDao")
public class CarDao extends BaseDao<CL_Car,java.lang.Integer> implements ICarDao{
	
	@Override
	public String getIbatisSqlMapNamespace() {
		return "CL_Car";
	}

	@Override
	public  List<CL_Car>  getByUserRoleId(Integer id) {
		return this.getSqlSession().selectList(getIbatisSqlMapNamespace()+".getByUserRoleId",id);
	}

	@Override
	public  CL_Car  getCarTypeByUserRoleId(Integer UserRoleId) {
		return this.getSqlSession().selectOne(getIbatisSqlMapNamespace()+".getCarTypeByUserRoleId",UserRoleId);
	}

	@Override
	public List<CL_Car> getUrIdByCarType(Integer carType) {
		return this.getSqlSession().selectList(getIbatisSqlMapNamespace()+".getUrIdByCarType",carType);
	}
	
	@Override
	public List<CL_Car> getAllCar(){
		return this.getSqlSession().selectList(getIbatisSqlMapNamespace()+".getAllCar");
	}

	@Override
	public List<CL_Car> getByCarSpecId(Integer carSpecId) {
		return this.getSqlSession().selectList(getIbatisSqlMapNamespace()+".getByCarSpecId",carSpecId);
	}

	@Override
	public List<CL_Car> getByCarSpecIdAndTypeId(Integer carSpecId,Integer carTypeId) {
		HashMap<String,Object> map = new HashMap<String,Object>();
		map.put("carSpecId", carSpecId);
		map.put("carTypeId", carTypeId);
		return this.getSqlSession().selectList(getIbatisSqlMapNamespace()+".getByCarSpecIdAndTypeId",map);
	}

	@Override
	public int updateLuckDr(CL_Car car) {
	 
		return this.getSqlSession().update(getIbatisSqlMapNamespace()+".updateLuckDr",car);
	}
}
