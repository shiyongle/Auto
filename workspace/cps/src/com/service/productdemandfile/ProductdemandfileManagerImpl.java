package com.service.productdemandfile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dao.IBaseDao;
import com.dao.productdemandfile.ProductdemandfileDao;
import com.model.designschemes.Schemefiles;
import com.model.productdemandfile.Productdemandfile;
import com.service.IBaseManagerImpl;
import com.util.ImageUtil;
import com.util.Params;
import com.util.StringUitl;

@SuppressWarnings("unchecked")
@Service("productdemandfileManager")
@Transactional(rollbackFor = Exception.class)
public class ProductdemandfileManagerImpl extends IBaseManagerImpl<Productdemandfile, java.lang.Integer> implements ProductdemandfileManager {

	@Autowired
	private ProductdemandfileDao productdemandfileDao;
	
	@Override
	protected IBaseDao<Productdemandfile, java.lang.Integer> getEntityDao() {
		return this.productdemandfileDao;
	}
	/*** 通过ParentId对象获取文件*/
	@Override
	public List<Productdemandfile> getByParentId(String fid) {
		return this.productdemandfileDao.getByParentId(fid);
	}
	/*** 通过schemeId对象获取文件*/
	@Override
	public List getBySchemeId(String fid) {
		return this.productdemandfileDao.getBySchemeId(fid);
	}	
	/*** 通过ParentId对象获取文件*/
	@Override
	public List<Productdemandfile> getImagesByParentId(String fid) {
		return this.productdemandfileDao.getByParentId(fid,true);
	}
	/*** 图片上传
	 * @throws IOException */
	@Override
	public String uploadImgImpl(String parentid,File fileupload,String fileuploadFileName) throws IOException {
		return this.uploadImgImpl( parentid, fileupload, fileuploadFileName,  null);
	}
	
	/*** 图片上传
	 * @throws IOException */
	@Override
	public String uploadImgImpl(String parentid,File fileupload,String fileuploadFileName, String type) throws IOException {
		SimpleDateFormat dfs = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM");
		File file = new File(ServletActionContext.getServletContext().getRealPath("/"));
		String path = 	file.getParent()+"/vmifile/"+df.format(new Date())+"/"+dfs.format(new Date());
		file = new File(path);
		if(!file.exists()){
			file.mkdirs();
		}
		String fid = productdemandfileDao.CreateUUid();
		String fname=fileuploadFileName;//图片名称
		String fileType = fname.substring(fname.lastIndexOf("."));//后缀名
		String fpath=path+"/"+fid+fileType;//绝对路径
		Productdemandfile pro = new Productdemandfile();
		pro.setFid(fid);
		pro.setFname(fname);
		pro.setFpath(fpath);
		pro.setFparentid(parentid);
		pro.setFcreatetime(new Date());
		if(!StringUitl.isNullOrEmpty(type)){
			pro.setFtype(type);
		}
		productdemandfileDao.saveOrUpdate(pro);
		file =new File(fpath);
        FileInputStream is = new FileInputStream(fileupload);  
        FileOutputStream os = new FileOutputStream(fpath);  
        IOUtils.copy(is, os);  
        os.flush();  
        IOUtils.closeQuietly(is);  
        IOUtils.closeQuietly(os);  
        ImageUtil.saveSmallImage(file);
        return fid;
	}
	
	
	/*** 图片上传
	 * @throws IOException */
	@Override
	public String uploadPhotoImpl(String parentid,File fileupload,String fileuploadFileName) throws IOException {
		SimpleDateFormat dfs = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM");
		File file = new File(ServletActionContext.getServletContext().getRealPath("/"));
		String path = 	file.getParent()+"/vmifile/"+df.format(new Date())+"/"+dfs.format(new Date());
		file = new File(path);
		if(!file.exists()){
			file.mkdirs();
		}
		String fid = productdemandfileDao.CreateUUid();
		String fname=fileuploadFileName;//图片名称
		String fileType = fname.substring(fname.lastIndexOf("."));//后缀名
		String fpath=path+"/"+fid+fileType;//绝对路径
		Productdemandfile pro = new Productdemandfile();
		pro.setFid(fid);
		pro.setFname(fname);
		pro.setFpath(fpath);
		pro.setFparentid(parentid);
		pro.setFcreatetime(new Date());
		productdemandfileDao.saveOrUpdate(pro);
		file =new File(fpath);
        FileInputStream is = new FileInputStream(fileupload);  
        FileOutputStream os = new FileOutputStream(fpath);  
        IOUtils.copy(is, os);  
        os.flush();  
        IOUtils.closeQuietly(is);  
        IOUtils.closeQuietly(os);  
        ImageUtil.saveSmallImage(file);
        return fid+fileType;
	}
	
	/***通过主键FID删除图片*/
	@Override
	public void deleteImgImpl(String fid) {
		String sql = "select fid from t_ord_productdemandfile where fid =:fid ";
		String fidcls = null;
		Params p = new Params();
		p.put("fid", fid);
		List<HashMap<String, String>> list = this.productdemandfileDao.QueryBySql(sql, p);
		for(HashMap<String, String> map : list){
			if(fidcls == null){
				fidcls = map.get("fid");
			}
		}
		if(fidcls!=null){
			delFile(fidcls);
		}
		
	}
	
	/***通过主键FID删除图片*/
	@Override
	public void deleteRedundantCertificateImgImpl(String parentId, String usedFids) {
		
		Params p = new Params();
		p.put("parentId", parentId);
		File file;
		File smallFile;
		String sql;
		List<HashMap<String,Object>> list;
		sql = "select * from t_ord_productdemandfile where fparentId =:parentId and ftype='设计服务商认证'";
		
		list = productdemandfileDao.QueryBySql(sql,p);
		for(HashMap<String,Object> item : list){
			
			String fid =  (String)item.get("fid");
			if(usedFids.indexOf(fid) <0){
				Params dp = new Params();
				dp.put("fid", fid);
				String path =(String)item.get("fpath");
				file = new File(path);
				if(file.exists()){
					file.delete();
				}
				smallFile = new File(path.replace("vmifile", "smallvmifile"));
				if(smallFile.exists()){
					smallFile.delete();
				}
				sql = "delete from t_ord_productdemandfile where fid =:fid";
				productdemandfileDao.ExecBySql(sql,dp);//删除附件表信息
			}
			
			
		}
		
	}
	
	public  void delFile(String fids){
		try {
			File file;
			File smallFile;
			String sql;
			List<HashMap<String,Object>> list;
			String[] fid = fids.replace("'","").split(",");
			for(String id : fid){
				Params p = new Params();
				p.put("fid", id);
				sql = "select * from t_ord_productdemandfile where fid =:fid";
				list = productdemandfileDao.QueryBySql(sql,p);
				if(list.size()>0){
					String path =(String)list.get(0).get("fpath");
					file = new File(path);
					if(file.exists()){
						file.delete();
					}
					smallFile = new File(path.replace("vmifile", "smallvmifile"));
					if(smallFile.exists()){
						smallFile.delete();
					}
					sql = "delete from t_ord_productdemandfile where fid =:fid";
					productdemandfileDao.ExecBySql(sql,p);//删除附件表信息
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@Override
	public void deleteImgByParentid(String fparentid) {
		String sql = "select fparentid from t_ord_productdemandfile where fparentid =:fparentid ";
		String fidcls = null;
		Params p = new Params();
		p.put("fparentid", fparentid);
		List<HashMap<String, String>> list = this.productdemandfileDao.QueryBySql(sql, p);
		for(HashMap<String, String> map : list){
			if(fidcls == null){
				fidcls = map.get("fparentid");
			}
		}
		if(fidcls!=null){
			delFileByParentId(fidcls);
		}
	}
	
	public void delFileByParentId(String parentids){
		try {
			File file;
			File smallFile;
			String sql;
			List<HashMap<String,Object>> list;
			String[] fid = parentids.replace("'","").split(",");
			for(String id : fid){
				Params p = new Params();
				p.put("fparentid", id);
				sql = "select * from t_ord_productdemandfile where fparentid =:fparentid";
				list = productdemandfileDao.QueryBySql(sql,p);
				if(list.size()>0){
					String path =(String)list.get(0).get("fpath");
					file = new File(path);
					if(file.exists()){
						file.delete();
					}
					smallFile = new File(path.replace("vmifile", "smallvmifile"));
					if(smallFile.exists()){
						smallFile.delete();
					}
					sql = "delete from t_ord_productdemandfile where fparentid =:fparentid";
					productdemandfileDao.ExecBySql(sql,p);//删除附件表信息
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	//2016-4-5 by les删除方案作品（图片）
	public  void delschemeFile(String fids){
		try {
			File file;
			File smallFile;
			String sql;
			List<HashMap<String,Object>> list;
			String[] fid = fids.replace("'","").split(",");
			for(String id : fid){
				Params p = new Params();
				p.put("fid", id);
				sql = "select * from t_ord_schemefiles where fid =:fid";
				list = productdemandfileDao.QueryBySql(sql,p);
				if(list.size()>0){
					String path =(String)list.get(0).get("fpath");
					file = new File(path);
					if(file.exists()){
						file.delete();
					}
					smallFile = new File(path.replace("vmifile", "smallvmifile"));
					if(smallFile.exists()){
						smallFile.delete();
					}
					sql = "delete from t_ord_schemefiles where fid =:fid";
					productdemandfileDao.ExecBySql(sql,p);//删除附件表信息
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	/*** 明星方案（作品）图片上传
	 * @throws IOException */
	@Override
	public String schemesuploadImgImpl(String parentid,File fileupload,String fileuploadFileName) throws IOException {
		SimpleDateFormat dfs = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM");
		File file = new File(ServletActionContext.getServletContext().getRealPath("/"));
		String path = 	file.getParent()+"/vmifile/schemefile/"+df.format(new Date())+"/"+dfs.format(new Date());
		file = new File(path);
		if(!file.exists()){
			file.mkdirs();
		}
		String fid = productdemandfileDao.CreateUUid();
		String fname=fileuploadFileName;//图片名称
		String fileType = fname.substring(fname.lastIndexOf("."));//后缀名
		String fpath=path+"/"+fid+fileType;//绝对路径
		Schemefiles scfile = new Schemefiles();
		scfile.setFid(fid);
		scfile.setFname(fname);
		scfile.setFpath(fpath);
		scfile.setFparentid(parentid);
		scfile.setFcreatetime(new Date());
		productdemandfileDao.saveOrUpdate(scfile);
		file =new File(fpath);
        FileInputStream is = new FileInputStream(fileupload);  
        FileOutputStream os = new FileOutputStream(fpath);  
        IOUtils.copy(is, os);  
        os.flush();  
        IOUtils.closeQuietly(is);  
        IOUtils.closeQuietly(os);  
        ImageUtil.saveSmallImage(file);
        return fid;
	}
	@Override
	/*** 通过图片id明星方案（作品）图片删除**/
	public void schemedeleteImgImpl(String fid) {
		String sql = "select fid from t_ord_schemefiles where fid =:fid ";
		String fidcls = null;
		Params p = new Params();
		p.put("fid", fid);
		List<HashMap<String, String>> list = this.productdemandfileDao.QueryBySql(sql, p);
		for(HashMap<String, String> map : list){
			if(fidcls == null){
				fidcls = map.get("fid");
			}
		}
		if(fidcls!=null){
			delschemeFile(fidcls);
		}
	}
	
	/**
	 * 通过图片的parentid，即明星方案（作品）的id删除此方案的所有图片
	 */
	public void deleteSchemeImgList(String fid)
	{
		try {
			File file;
			File smallFile;
			String sql;
			Params p = new Params();
			p.put("fpid", fid);
			sql = "select * from t_ord_schemefiles where fparentid =:fpid";
			List<HashMap<String, String>> files=this.productdemandfileDao.QueryBySql(sql, p);
			if(files.size()>0){
				for (HashMap<String, String> map : files) {
					String path =map.get("fpath");//删除附件表信息
					file = new File(path);
					if(file.exists()){
						file.delete();
					}
					smallFile = new File(path.replace("vmifile", "smallvmifile"));
					if(smallFile.exists()){
						smallFile.delete();
					}
				}
				sql="delete from t_ord_schemefiles where fparentid=:fpid";
				this.productdemandfileDao.ExecBySql(sql, p); 
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			// TODO: handle exception
		}
	}
	@Override
	public List getByImageId(String fid) {
		return this.productdemandfileDao.getByImageId(fid);
	}
}
