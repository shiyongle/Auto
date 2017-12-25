package com.service.productdemandfile;

import java.io.File;
import java.io.IOException;
import java.util.List;

import com.model.productdemandfile.Productdemandfile;
import com.service.IBaseManager;

public interface ProductdemandfileManager extends IBaseManager<Productdemandfile, java.lang.Integer> {
	
	/*** 通过ParentId对象获取文件*/
	public List<Productdemandfile> getByParentId(String fid);
	/*** 通过schemeId对象获取文件*/
	public List getBySchemeId(String fid);
	public List getByImageId(String fid);
	/*** 图片上传
	 * @throws IOException */
	public String uploadImgImpl(String parentid,File fileupload,String fileuploadFileName) throws IOException;
	/*** (方案)作品图片上传
	 * @throws IOException */
	public String schemesuploadImgImpl(String parentid,File fileupload,String fileuploadFileName) throws IOException;
	/*** 图片上传
	 * @throws IOException */
	public String uploadPhotoImpl(String parentid,File fileupload,String fileuploadFileName) throws IOException;
	
	/*** 图片删除*/
	public void deleteImgImpl(String fid);
	/*** (方案)作品图片删除
	 * @throws IOException */
	public void schemedeleteImgImpl(String fid);
	
	public void deleteImgByParentid(String fparentid);
	List<Productdemandfile> getImagesByParentId(String fid);
	
	public void delFileByParentId(String parentids);
	
	public String uploadImgImpl(String parentid, File fileupload,String fileuploadFileName, String type) throws IOException;
	public void deleteRedundantCertificateImgImpl(String parentId, String usedFids);


	public void deleteSchemeImgList(String fid);

}
