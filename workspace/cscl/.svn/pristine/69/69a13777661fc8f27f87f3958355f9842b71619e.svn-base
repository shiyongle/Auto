package com.pc.dao.clUpload.impl;

import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Service;

import com.pc.dao.aBase.impl.BaseDao;
import com.pc.dao.clUpload.IuploadDao;
import com.pc.model.Cl_Upload;

/**
 * 
 * @author zzm
 *service的注解是typealice的autowire
 */
@Service("uploadDao")
public class UploadDao extends BaseDao<Cl_Upload,java.lang.Integer> implements IuploadDao{
	
	@Override
	public String getIbatisSqlMapNamespace() {
		return "Cl_Upload";
	}
	@Override
	public Number getTotal(Integer type) {
		return  this.getSqlSession().selectOne(getIbatisSqlMapNamespace()+".getTotal",type);
	}
	@Override
	public List<Cl_Upload> indexappByType(Integer type) {
		return this.getSqlSession().selectList(getIbatisSqlMapNamespace()+".indexappByType", type);
	}

	@Override
	public List<Cl_Upload> getCl_UploadsByParentId(Integer parentId) {
		return this.getSqlSession().selectList(getIbatisSqlMapNamespace()+".getCl_UploadsByParentId", parentId);
	}
	@Override
	public List<Cl_Upload> getByOrderIdAndByMode(Integer parentId, String modelName) {
	    HashMap<String, Object> map=new HashMap<String,Object>();
	    map.put("parentId", parentId);
	    map.put("modelName", modelName);
		return this.getSqlSession().selectList(getIbatisSqlMapNamespace()+".getByOrderIdAndByMode", map);
	}
	@Override
	public int deleteByMode(Integer id, String modelName) {
		  HashMap<String, Object> map=new HashMap<String,Object>();
		    map.put("id", id);
		    map.put("modelName", modelName);
			return this.getSqlSession().delete(getIbatisSqlMapNamespace()+".deleteByMode", map);
	}
	@Override
	public int deleteByParentId(Integer parent_id) {
		return this.getSqlSession().delete(getIbatisSqlMapNamespace()+".deleteByParentId",parent_id);
	}
	
	
	@Override
	public Number getLogByUser(Integer create_id, String model_name,
			String create_time1,String create_time2) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("create_id", create_id);
		map.put("model_name", model_name);
		map.put("create_time1", create_time1);
		map.put("create_time2", create_time2);
		return this.getSqlSession().selectOne(getIbatisSqlMapNamespace()+".getLogByUser", map);
	}
	 
}
