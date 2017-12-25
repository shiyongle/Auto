package com.pc.dao.clUpload;

import java.util.List;

import com.pc.dao.aBase.IBaseDao;
import com.pc.model.Cl_Upload;

public interface IuploadDao extends IBaseDao<Cl_Upload, java.lang.Integer>{
		  
	public Number getTotal(Integer type);
	public List<Cl_Upload> indexappByType(Integer type);
	public List<Cl_Upload> getCl_UploadsByParentId(Integer parentId);
	
	/**根据  parentId 和 modelName 查上传**/
	public List<Cl_Upload>  getByOrderIdAndByMode(Integer  parentId,String modelName);
	
	/**根据  parentId 和 modelName 删除上传**/
    public int deleteByMode(Integer id,String modelName);
   
   /**
    * 删除司机或者客户时删除图片资料
    * @param deleteByParentId
    * @return
    */
    public int deleteByParentId(Integer parent_id);
    
    /**
     * 一个用户一天之内只能上传3次错误日志
     * @param create_id
     * @param model_name
     * @param create_time
     * @return
     */
    public Number getLogByUser(Integer create_id,String model_name,String create_time1,String create_time2);
    
}
