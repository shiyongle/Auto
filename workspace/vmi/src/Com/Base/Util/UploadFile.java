package Com.Base.Util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.tomcat.util.http.fileupload.FileItem;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItemFactory;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;
import org.hibernate.util.StringHelper;

import Com.Dao.order.IProductdemandfileDao;
import Com.Entity.order.Productdemandfile;

public class UploadFile {
@Resource
private static  IProductdemandfileDao  productdemandfileDao  = (IProductdemandfileDao) SpringContextUtils.getBean("productdemandfileDao");
/**
 * 上传附件
 * @param request
 * @param parentid 跟附件关联的ID
 * @param resize 废用，为兼容以前的代码
 * @return 单文件上传时的文件id
 */
public static String[] upload(HttpServletRequest request,String parentid,boolean resize){
	
	ServletFileUpload upload = new ServletFileUpload(new DiskFileItemFactory()); // 创建一个新的文件上传对象
	upload.setHeaderEncoding("utf-8");//避免中文名乱码
	SimpleDateFormat dfs = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
	SimpleDateFormat df = new SimpleDateFormat("yyyy-MM");//设置日期格式
	String fid = null;
	String fids = null;
	String fileType;
	try {
		List<FileItem> list  = upload.parseRequest(request);
		if(StringHelper.isEmpty(parentid)){
			throw new DJException("关联附件ID不能为空！");
		}
		
		File file = new File(request.getServletContext().getRealPath("/"));//获取当前路径
		String fpath = 	file.getParent()+"/vmifile/"+df.format(new Date())+"/"+dfs.format(new Date());//构造按当前日期保存文件的路径 yyyy-MM-dd
		String fname;//文件名
		file = new File(fpath);
		if(!file.exists()){
			file.mkdirs();
		}
		String fileName;
		int index;
		for(FileItem item : list){
			if(!item.isFormField()){
				fname = item.getName();
				if(StringUtils.isEmpty(fname) || item.getSize()==0){
					continue;
				}
				if(item.getSize()>10 * 1024 * 1024){
					throw new DJException("您上传的文件太大，请选择不超过10M的文件");
				}
				fname = fname.substring(item.getName().lastIndexOf("\\")+1);//IE上传文件 文件名前带有路径
				index = fname.lastIndexOf(".");
				fileType = index>0?fname.substring(fname.lastIndexOf(".")):"";
				try {
					fid = productdemandfileDao.CreateUUid();
					fileName = fid+fileType;
					saveProductDemanfile(fid,fname,fpath+"/"+fileName,parentid);
					file = new File(fpath,fileName);
					item.write(file);
					if(ImageUtil.isImage(fname)){
						ImageUtil.saveSmallImage(file);
					}
				} catch (Exception e) {
					throw new RuntimeException(e);
				}
				fids = fids==null ? fid : fids+"_"+fid;
			
			
			}else{
				try {
					request.setAttribute(item.getFieldName(), item.getString("utf-8"));
				} catch (UnsupportedEncodingException e) {
					throw new RuntimeException(e);
				}
			}
		}
	} catch (FileUploadException e) {
		throw new RuntimeException(e);
	}
	return fids==null?null:fids.split("_");
}
/**
 * 上传附件
 * @param request
 * @param parentid 跟附件关联的ID
 * @return 单文件上传时的文件id
 */
public static String[] upload(HttpServletRequest request,String parentid){
	return upload(request,parentid,false);
}
/**
 * 文件上传 需要往附件表传其他参数
 * @param request
 * @param map 附件表参数
 */
public static List<Productdemandfile> upload(HttpServletRequest request,Map<String,String> map){
	return upload(request,map,10);
}

public static List<Productdemandfile> upload(HttpServletRequest request,Map<String,String> map,int filesize){
	ServletFileUpload upload = new ServletFileUpload(new DiskFileItemFactory()); // 创建一个新的文件上传对象
	upload.setHeaderEncoding("utf-8");//避免中文名乱码
	SimpleDateFormat dfs = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
	SimpleDateFormat df = new SimpleDateFormat("yyyy-MM");//设置日期格式
	List<Productdemandfile> fileList = new ArrayList<>();
	try {
		if(StringHelper.isEmpty((String)map.get("fparentid"))){
			throw new DJException("关联附件ID不能为空！");
		}
		List<FileItem> list  = upload.parseRequest(request);
		File file = new File(request.getServletContext().getRealPath("/"));//获取当前路径
		String fpath = 	file.getParent()+"/vmifile/"+df.format(new Date())+"/"+dfs.format(new Date());//构造按当前日期保存文件的路径 yyyy-MM-dd
		String fname;//文件名
		String fid;
		int index;
		if(filesize==0){
			filesize = 10;
		}
		for(FileItem item : list){
			if(!item.isFormField() && item.getSize()>0){
				if(item.getSize()>filesize * 1024 * 1024){
					throw new DJException("您上传的文件太大，请选择不超过10M的文件");
				}
				file = new File(fpath);
				if(!file.exists()){
					file.mkdirs();
				}
				fname = item.getName();
				
				fid = productdemandfileDao.CreateUUid();
				index = fname.lastIndexOf(".");
				fpath = fpath+"/" + fid+ (index>0?fname.substring(index):"");
				try {
					file = new File(fpath);
					item.write(file);
					fname = fname.substring(item.getName().lastIndexOf("\\")+1);//IE上传文件 文件名前带有路径
					if(ImageUtil.isImage(fname)){
						ImageUtil.saveSmallImage(file);
					}
					Productdemandfile pdfile = saveProductDemanfile(fid,fname,fpath,map);
					fileList.add(pdfile);
				} catch (Exception e) {
					throw new RuntimeException(e);
				}
			}
		}
	} catch (FileUploadException e) {
		throw new RuntimeException(e);
	}
	return fileList;
	
}
/**
 * 文件上传 需要往附件表传其他参数 认证
 * @param request
 * @param map 附件表参数
 * @param filesize 文件大小
 * @param resize被废弃，此方法为兼容历史代码
 */
public static List upload(HttpServletRequest request,Map map,int filesize,boolean resize){
	List<Productdemandfile> productdemandfileList = upload(request,map,filesize);
	List<HashMap<String,String>> fileList = new ArrayList<>();
	HashMap<String,String> filemap;
	String path;
	for(Productdemandfile fileObj: productdemandfileList){
		filemap = new HashMap<>();
		path = fileObj.getFpath();
		path = path.substring(path.indexOf("/vmifile")).replace("vmifile", "smallvmifile");
		filemap.put("fid",fileObj.getFid());
		filemap.put("imgUrl",fileObj.getFpath());
		fileList.add(filemap);
	}
	return fileList;
}
/**
 * 附件下载
 * @param response
 * @param fid 附件ID
 */
public static void download(HttpServletResponse response,String fid){
	try {
		productdemandfileDao.downloadProductdemandFile(response, fid);
	} catch (IOException e) {
		e.printStackTrace();
	}
}
/**
 * 附件删除
 * @param fid 附件ID 多个ID用逗号隔开
 */
public static void delFile(String fids){
	try {
		File file;
		String sql;
		List<HashMap<String,Object>> list;
		String[] fid = fids.replace("'","").split(",");
		String path;
		for(String id : fid){
			sql = "select * from t_ord_productdemandfile where fid ='"+id+"'";
			list = productdemandfileDao.QueryBySql(sql);
			if(list.size()>0){
				path = (String)list.get(0).get("fpath");
				file = new File(path);
				if(file.exists()){
					file.delete();
				}
				file = new File(getSmallImagePath(path));
				if(file.exists()){
					file.delete();
				}
				sql = "delete from t_ord_productdemandfile where fid ='"+id+"'";
				productdemandfileDao.ExecBySql(sql);//删除附件表信息
			}
			
		}
	} catch (DJException e) {
		// TODO: handle exception
	}
}

public static void delFileByParentId(String fid){
	String sql = "select fid from t_ord_productdemandfile where fparentid =:fparentid";
	String fidcls = null;
	params p = new params();
	p.put("fparentid", fid);
	List<HashMap<String, String>> list = productdemandfileDao.QueryBySql(sql, p);
	for(HashMap<String, String> map : list){
		if(fidcls == null){
			fidcls = map.get("fid");
		}else{
			fidcls += ","+map.get("fid");
		}
	}
	if(fidcls!=null){
		delFile(fidcls);
	}
}
private static void saveProductDemanfile(String fid,String fname,String fpath,String parentid){
	Productdemandfile pro = new Productdemandfile();
	pro.setFid(fid);
	pro.setFname(fname);
	pro.setFpath(fpath);
	pro.setFparentid(parentid);
	pro.setFcreatetime(new Date());
	productdemandfileDao.saveOrUpdate(pro);
}

private static void saveProductDemanfile(String fid,String fname,String fpath,String parentid,String fbid){
	Productdemandfile pro = new Productdemandfile();
	pro.setFid(fid);
	pro.setFname(fname);
	pro.setFpath(fpath);
	pro.setFparentid(parentid);
	pro.setFcreatetime(new Date());
	pro.setFbid(fbid);
	productdemandfileDao.saveOrUpdate(pro);
}
private static Productdemandfile saveProductDemanfile(String fid,String fname,String fpath,Map map){
	Productdemandfile pro = new Productdemandfile();
	pro.setFid(fid);
	pro.setFname(fname);
	pro.setFpath(fpath);
	pro.setFparentid((String)map.get("fparentid"));
	pro.setFcreatetime(new Date());
	pro.setFdescription((String)map.get("fdescription"));
	pro.setFtype((String)map.get("ftype"));
	productdemandfileDao.saveOrUpdate(pro);
	return pro;
}

private static Productdemandfile saveProductDemanfile(String fid,String fname,String fpath,String fbid,Map map){
	Productdemandfile pro = new Productdemandfile();
	pro.setFid(fid);
	pro.setFname(fname);
	pro.setFpath(fpath);
	pro.setFparentid("#"+(String)map.get("fparentid"));
	pro.setFcreatetime(new Date());
	pro.setFdescription((String)map.get("fdescription"));
	pro.setFtype((String)map.get("ftype"));
	pro.setFbid(fbid);
	productdemandfileDao.saveOrUpdate(pro);
	return pro;
}
public static String getFilePath(HttpServletRequest request,String fid) throws IOException{
	try {
		String sql = "select * from t_ord_productdemandfile where fid = '"+fid+"'";
		List<HashMap<String,Object>> list = productdemandfileDao.QueryBySql(sql);
		String path = (String)list.get(0).get("fpath");
		if(path.lastIndexOf("vmifile")==-1){
			path = "file/schemedesign/"+ path.substring(path.lastIndexOf("/") + 1);
			return path;
		}else{
			String newpath = "/"+path.substring(path.lastIndexOf("vmifile"));
			return newpath;
		}
		
	} catch (DJException e) {
		// TODO: handle exception
		return null;
	}
}

/**
 * 根据客户产品获取最新图片路径
 * @param request
 * @param fid
 * @return
 * @throws IOException
 */
public static String getQuickFilePath(HttpServletRequest request,String fid) throws IOException{
	try {
		String sql=" SELECT d.fid,IFNULL(f.fpath,d.fpath) fpath FROM t_ord_productdemandfile   d  LEFT JOIN t_ord_productdemandfile f ON d.fid=f.fbid WHERE d.fparentid='"+fid+"' ORDER BY d.fcreatetime desc";
//		String sql = "select * from t_ord_productdemandfile where fparentid = '"+fid+"' order by  fcreatetime desc";
		List<HashMap<String,Object>> list = productdemandfileDao.QueryBySql(sql);
		if(list.size()==0)
		{
			return "";
		}
		String path = (String)list.get(0).get("fpath");
		return getFilePathByPath(path);
		
	} catch (DJException e) {
		// TODO: handle exception
		return null;
	}
}

/**
 * 根据路径构造路径
 * @param request
 * @param fid
 * @return
 * @throws IOException
 */
public  static String getFilePathByPath(String path)
{
	if("".equals(path)||path==null)
	{
		return "";
	}
	if(path.lastIndexOf("vmifile")==-1){
		path = "file/schemedesign/"+ path.substring(path.lastIndexOf("/") + 1);
		return path;
	}else{
		String newpath = "/"+path.substring(path.lastIndexOf("vmifile"));
		return newpath;
	}
}

/** 
 * 复制单个文件 
 * @param oldPath String 原文件路径 如：c:/fqf.txt 
 * @param newPath String 复制后路径 如：f:/fqf.txt 
 * @return boolean 
 * @throws IOException 
 */ 
public static void copyFile(String oldPath, String newPath) throws IOException {
	InputStream in = null;
	OutputStream out = null;
	int len = 0;
	try {
		try {
			in = new FileInputStream(oldPath);
			out = new FileOutputStream(newPath);
		} catch (FileNotFoundException e) {
			throw new DJException("文件不存在！");
		} //读入原文件 
		byte[] buffer = new byte[1024];
		while ((len = in.read(buffer)) != -1) {
			out.write(buffer, 0, len);
		}
	}finally {
		if(in!=null){
			in.close();
		}
		if(out!=null){
			out.close();
		}
	}
} 
/**
 * 获取小图片的路径
 * @param fpath
 * @param isCreate	如果小图片不存在，则创建
 * @return
 */
public static String getSmallImagePath(String fpath,boolean isCreate){
	String newPath = fpath.replace("vmifile", "smallvmifile");
	if(isCreate){
		File newFile = new File(newPath);
		if(!newFile.exists()){
			try {
				ImageUtil.saveSmallImage(new File(fpath));
			} catch (Exception e) {
				throw new DJException("图片获取失败！");
			}
		}
	}
	return newPath;
}

public static String getSmallImagePath(String fpath){
	return getSmallImagePath(fpath,false);
}

}


