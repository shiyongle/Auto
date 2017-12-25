package com.dao.productdemandfile;

import java.util.List;

import com.dao.IBaseDao;
import com.model.productdemandfile.Productdemandfile;

public interface ProductdemandfileDao extends IBaseDao<Productdemandfile, java.lang.Integer>{
	
	/*** 通过ParentId对象获取文件*/
	public List<Productdemandfile> getByParentId(String fid);
	/*** 通过schemeId对象获取文件*/
	public List getBySchemeId(String fid);
	List<Productdemandfile> getByParentId(String fid, Boolean isImage);
	public List getByImageId(String fid);
}
