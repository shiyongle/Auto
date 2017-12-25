package com.action.productdemandfile;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;

import com.action.BaseAction;
import com.model.custproduct.TBdCustproduct;
import com.model.firstproductdemand.Firstproductdemand;
import com.model.productdemandfile.Productdemandfile;
import com.opensymphony.xwork2.ModelDriven;
import com.service.custproduct.TBdCustproductManager;
import com.service.firstproductdemand.TordFirstproductdemandManager;
import com.service.productdemandfile.ProductdemandfileManager;
import com.util.Constant;
import com.util.ImageUtil;
import com.util.JSONUtil;

@SuppressWarnings("unchecked")
public class ProductdemandfileAction extends BaseAction implements ModelDriven<Productdemandfile>{

	/***
	 *<p>Description: </p>
	 *<p>Company: CPS-TEAM</p> 
	 * @author WANGC
	 * @date 2015-8-4 下午4:51:01
	 */
	private static final long serialVersionUID = -2741786329397208729L;
	private Productdemandfile productdemandfile=new Productdemandfile();
	@Autowired
	private ProductdemandfileManager productdemandfileManager;
	@Autowired
	private TordFirstproductdemandManager tordFirstproductdemandManager;
	@Autowired
	private TBdCustproductManager tbdCustproductManager;

	public Productdemandfile getProductdemandfile() {
		return productdemandfile;
	}

	public void setProductdemandfile(Productdemandfile productdemandfile) {
		this.productdemandfile = productdemandfile;
	}

	@Override
	public Productdemandfile getModel() {
		return productdemandfile;
	}

	private File file;//上传的文件  
	private String fileFileName;//文件的名称  如上传的文件是a.png 则fileuploadFileName值为"a.png"  
	private String fileContentType;//文件的类型  如上传的是png格式的图片，则fileuploadContentType值为"image/png"
	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public String getFileFileName() {
		return fileFileName;
	}

	public void setFileFileName(String fileFileName) {
		this.fileFileName = fileFileName;
	}

	public String getFileContentType() {
		return fileContentType;
	}

	public void setFileContentType(String fileContentType) {
		this.fileContentType = fileContentType;
	}

	/*** 通过客户产品主键获取图片路径*/
	public String getPictureUrl(){
		String  fid=getRequest().getParameter("fid"); 
		String path = null;
		List<Productdemandfile> ls = this.productdemandfileManager.getByParentId(fid);
		List<String> url =new ArrayList<String>();
		String basePath = getRequest().getScheme()+"://"+getRequest().getServerName()+":"+getRequest().getServerPort();

		if(ls.size() >0){
			for(int i=0;i<ls.size();i++){
				path = (ls.get(i).getFpath()).replace("vmifile", "smallvmifile");
				path =basePath + path.split("webapps")[1];
				url.add(path);
			}
		}else{
			path = basePath + "/vmifile/defaultpic.png";
			url.add(path);
		}
		HashMap<String, Object> m = new HashMap<String, Object>();
		m.put("pathUrl", url.get(0));
		if(ls.size() >0){
			m.put("list", url);
		}
		return writeAjaxResponse(JSONUtil.getJson(m));
	}


	/*** 图片上传
	 * @throws IOException */
	public String uploadImg() throws Exception{
		String parentid =getRequest().getParameter("fid");
		String fid =this.productdemandfileManager.uploadImgImpl(parentid,file,fileFileName);
		return writeAjaxResponse(fid);
	}
	/*** 图片上传
	 * @throws IOException */
	public String uploadImg_P() throws Exception{
		String parentid =getRequest().getParameter("fid");
		String fid =this.productdemandfileManager.uploadImgImpl(parentid,file,fileFileName);
		HashMap<String, Object> map = new HashMap<String, Object>();
		if(fid!=null){
			map.put("success", true);
			map.put("fid", fid);
			map.put("pname", fileFileName);
		}else{
			map.put("success", false);
		}
		return writeAjaxResponse(JSONUtil.getJson(map));
	}


	/*** 身份证上传
	 * @throws IOException */
	public String uploadPhoto() throws Exception{
		String parentid =getRequest().getParameter("fid");
		String fid =this.productdemandfileManager.uploadPhotoImpl(parentid,file,fileFileName);
		String basePath = getRequest().getScheme()+"://"+getRequest().getServerName()+":"+getRequest().getServerPort();
		HashMap<String, Object> map = new HashMap<String, Object>();
		if(fid !=null){
			List<Productdemandfile> ls = this.productdemandfileManager.getByParentId(parentid);
			map.put("success", true);
			if(ls.size() >0){
				String path = (ls.get(0).getFpath()).replace("vmifile", "smallvmifile");
				path =basePath + path.split("webapps")[1];
				map.put("pathUrl", path);
			}
		}else{
			map.put("success", false);
		}
		return writeAjaxResponse(JSONUtil.getJson(map));
	}


	/***图片删除*/
	public String deleteImg(){
		String fid =getRequest().getParameter("fid");
		this.productdemandfileManager.deleteImgImpl(fid);
		return writeAjaxResponse("success");
	}

	public String loadByParentId(){
		List<Productdemandfile> ls = this.productdemandfileManager.getByParentId(id);
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("data", ls);
		return writeAjaxResponse(JSONUtil.getJson(map));
	}



	public void downProductdemandFile() throws Exception{
		String fid =getRequest().getParameter("fid");
		HttpServletResponse response=getResponse();
		Productdemandfile p = this.productdemandfileManager.get(fid);
		getResponse().setContentType("text/html;charset=utf-8");
		if(p!=null){
			InputStream in = null;

			try {
				in = new FileInputStream(p.getFpath());
			} catch (FileNotFoundException e) {

				response.getWriter().write("此附件文件不存在，无法下载！");
				return;
			}
			response.setContentType("application/x-msdownload");
			response.addHeader("Content-Disposition", "attachment; filename=\"" + new String(p.getFname().getBytes("UTF-8"),"iso-8859-1") + "\"");
			OutputStream out = response.getOutputStream();
			byte[] bytes = new byte[1024];
			int len = 0;
			while((len = in.read(bytes,0,1024))!=-1){
				out.write(bytes, 0, len);
			}
			out.flush();
			in.close();
		}
		else
		{ 
			response.getWriter().write("附件fid有误,无法下载");
			return;
		}

	}

	public void downProductdemandFilebyid(String id) throws Exception{
		HttpServletResponse response=getResponse();
		Productdemandfile p = this.productdemandfileManager.get(id);
		getResponse().setContentType("text/html;charset=utf-8");
		if(p!=null){
			InputStream in = null;

			try {
				in = new FileInputStream(p.getFpath());
			} catch (FileNotFoundException e) {

				response.getWriter().write("此附件文件不存在，无法下载！");
				return;
			}
			response.setContentType("application/x-msdownload");
			response.addHeader("Content-Disposition", "attachment; filename=\"" + new String(p.getFname().getBytes("UTF-8"),"iso-8859-1") + "\"");
			OutputStream out = response.getOutputStream();
			byte[] bytes = new byte[1024];
			int len = 0;
			while((len = in.read(bytes,0,1024))!=-1){
				out.write(bytes, 0, len);
			}
			out.flush();
			in.close();
		}
		else
		{ 
			response.getWriter().write("确认是否上传附件,无法下载");
			return;
		}

	}


	//	public void downProductdemandFiles() throws Exception{
	//		String pfid =getRequest().getParameter("pfid");
	//		//2015-10-29 暂支持图片下载，详细附件到详情界面下载
	//		List<Productdemandfile> listfile = productdemandfileManager.getImagesByParentId(pfid);
	//		if(listfile.size()>0){
	//			this.downProductdemandFilebyid(listfile.get(0).getFid());
	//		}else
	//		{
	//			 getResponse().setContentType("text/html;charset=utf-8");
	//			 getResponse().getWriter().write("没有附件");
	//			return;
	//		}
	//	}

	// 2015-10-31 打包下载方法入口
	public void downProductdemandFiles() throws Exception{
		HttpServletResponse response=getResponse();
		getResponse().setContentType("text/html;charset=utf-8");
		String pfid =getRequest().getParameter("pfid");
		//根据 ftype 找实体
		String typeinfo =getRequest().getParameter("ftype");
		String filename = null;
		if(typeinfo.equals("ksinfo") || typeinfo.equals("ddinfo")){
			TBdCustproduct cpinfo = tbdCustproductManager.get(pfid);
			filename = cpinfo.getFname();
		}
		else if(typeinfo.equals("pdinfo")){
			Firstproductdemand fdinfo = tordFirstproductdemandManager.get(pfid);
			//方案名称
			filename = fdinfo.getFname();
		}
		else{
			response.getWriter().write("抱歉暂时不支持此类型文件下载");
			return;			
		}
		List<Productdemandfile> listfile = tordFirstproductdemandManager.getFilesbyParentid(pfid);
		//String basePath = getRequest().getScheme()+"://"+getRequest().getServerName()+":"+getRequest().getServerPort();
		if(listfile.size()> 1){
			//创建文件夹
			SimpleDateFormat dfs = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM");			
			File file = new File(ServletActionContext.getServletContext().getRealPath("/"));
			String path = 	file.getParent()+"/vmifile/"+df.format(new Date())+"/"+dfs.format(new Date())+"/zip";
			file = new File(path);
			if(!file.exists()){
				file.mkdirs();
			}
			//创建文件
			String fpath=path+"/"+filename+".zip";
			File filezip = new File(fpath);
			if (!filezip.exists()){   
				filezip.createNewFile();   
			}
			response.reset();
			//创建文件输出流
			FileOutputStream fous = new FileOutputStream(filezip);   
			/**打包的方法我们会用到ZipOutputStream这样一个输出流,
			 * 所以这里我们把输出流转换一下*/
			ZipOutputStream zipOut = new ZipOutputStream(fous);
			/**这个方法接受的就是一个所要打包文件的集合，
			 * 还有一个ZipOutputStream*/
			zipFile(listfile, zipOut);
			zipOut.close();
			fous.close();
			downloadZip(filezip,response);
		}
		//2015-10-31 单个文件下载调用原方法
		else if(listfile.size()==1){
			this.downProductdemandFilebyid(listfile.get(0).getFid());
		}
		else{
			response.getWriter().write("附件fid有误,无法下载");
			return;			
		}
	}

	/**
	 * 把接受的全部文件打成压缩包 
	 * @param List<File>;  
	 * @param org.apache.tools.zip.ZipOutputStream  
	 */
	public static void zipFile (List files,ZipOutputStream outputStream) {
		int size = files.size();
		for(int i = 0; i < size; i++) {
			Productdemandfile file = (Productdemandfile) files.get(i);
			zipeverypdFile(file, outputStream);
		}
	}


	/**  
	 * 根据输入的文件与输出流对文件进行打包
	 * @param File
	 * @param org.apache.tools.zip.ZipOutputStream
	 */
	public static void zipeveryFile(File inputFile,ZipOutputStream ouputStream) {
		try {
			/**
			 * 如果是目录的话这里是不采取操作的， 至于目录的打包正在研究中
			 */
			if (inputFile.isFile()) {
				FileInputStream IN = new FileInputStream(inputFile);
				BufferedInputStream bins = new BufferedInputStream(IN, 512);
				// org.apache.tools.zip.ZipEntry
				ZipEntry entry = new ZipEntry(inputFile.getName());
				ouputStream.putNextEntry(entry);
				// 向压缩文件中输出数据
				int nNumber;
				byte[] buffer = new byte[512];
				while ((nNumber = bins.read(buffer)) != -1) {
					ouputStream.write(buffer, 0, nNumber);
				}
				// 关闭创建的流对象
				bins.close();
				IN.close();
			} else {
				try {
					File[] files = inputFile.listFiles();
					for (int i = 0; i < files.length; i++) {
						zipeveryFile(files[i], ouputStream);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	/**  
	 * 重载了 zipeveryFile 方法
	 */
	public static void zipeverypdFile(Productdemandfile fileObj,ZipOutputStream ouputStream) {
		try {
			File inputFile = new File(fileObj.getFpath());
			if(inputFile.exists()){
				/**
				 * 如果是目录的话这里是不采取操作的， 至于目录的打包正在研究中
				 */
				if (inputFile.isFile()) {
					FileInputStream IN = new FileInputStream(inputFile);
					BufferedInputStream bins = new BufferedInputStream(IN, 512);
					// org.apache.tools.zip.ZipEntry
					ZipEntry entry = new ZipEntry(fileObj.getFname());
					ouputStream.putNextEntry(entry);
					// 向压缩文件中输出数据
					int nNumber;
					byte[] buffer = new byte[512];
					while ((nNumber = bins.read(buffer)) != -1) {
						ouputStream.write(buffer, 0, nNumber);
					}
					// 关闭创建的流对象
					bins.close();
					IN.close();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void downloadZip(File file,HttpServletResponse response) throws Exception{	    	
		response.setContentType("text/html;charset=utf-8");
		InputStream in = new BufferedInputStream(new FileInputStream(file.getPath()));
		response.setContentType("application/x-msdownload");
		response.addHeader("Content-Disposition", "attachment; filename=\"" + new String(file.getName().getBytes("UTF-8"),"iso-8859-1") + "\"");
		OutputStream out = response.getOutputStream();
		byte[] bytes = new byte[1024];
		int len = 0;
		while((len = in.read(bytes,0,1024))!=-1){
			out.write(bytes, 0, len);
		}
		out.flush();
		in.close();
	}

	/**
	 * 通过id获取文件流
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public String getFileSource() throws IOException {
		String fid = getRequest().getParameter("fid");
		InputStream in = null;
		OutputStream out = null;
		try {
			//				Productdemandfile obj = (Productdemandfile) dao.Query(Productdemandfile.class, fid);
			Productdemandfile obj = (Productdemandfile) productdemandfileManager.get(fid);
			if(obj==null){
				throw new Exception("图纸不存在！");
			}else if(!ImageUtil.isImage(obj.getFname())){
				throw new Exception("文件不是图片类型！");
			}
			in = new FileInputStream(getSmallImagePath(obj.getFpath(),true));
			out = getResponse().getOutputStream();
			IOUtils.copy(in, out);
			in.close();
		} catch (Exception e) {
			getResponse().getWriter().write("");
		}
		return null;
	}

	/**
	 * 通过id获取文件流
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception 
	 */
	public String getFileSource2() throws Exception {
		downProductdemandFile();
		return null;
	}

	/**
	 * 通过父id获取文件流
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public String getFileSourceByParentId() throws IOException {

		String fid = getRequest().getParameter("fid");
		InputStream in = null;
		OutputStream out = null;
		String path = null;
		try {
			Productdemandfile obj = null;
			//				String hql = "from Productdemandfile where fparentid = '"+fid+"'";
			//				List<Productdemandfile> list = dao.QueryByHql(hql);
			List<Productdemandfile> list = productdemandfileManager.getByParentId(fid);
			list = extractImageList(list);
			if(list.size()>0){
				obj = list.get(0);
				path = getSmallImagePath(obj.getFpath(),true);
			}else{
				path = new File(ServletActionContext.getServletContext().getRealPath("")).getParent() + "/vmifile/defaultpic.png";
				if(!new File(path).exists()){
					path = ServletActionContext.getServletContext().getRealPath("") + "/vmifile/defaultpic.png";
				}
			}
			in = new FileInputStream(path);
			out = getResponse().getOutputStream();
			IOUtils.copy(in, out);
			in.close();
		} catch (Exception e) {
			getResponse().getWriter().write("图纸不存在！");
		}
		return null;
	}
	/**
	 * 获取默认图片
	 * @return
	 * @throws IOException
	 */
	public String getDefaultImg() throws IOException{
		String path = new File(ServletActionContext.getServletContext().getRealPath("")).getParent() + "/vmifile/defaultpic.png";
		if(!new File(path).exists()){
			path = ServletActionContext.getServletContext().getRealPath("") + "/vmifile/defaultpic.png";
		}
		OutputStream out = getResponse().getOutputStream();
		InputStream in = new FileInputStream(path);
		try {
			IOUtils.copy(in, out);
		}finally{
			in.close();
		}
		return null;
	}

	/**
	 * 提取是图片的文件列表
	 * @param list
	 * @return
	 */
	public List<Productdemandfile> extractImageList(List<Productdemandfile> list) {
		List<Productdemandfile> result = new ArrayList<>();
		for(Productdemandfile file: list){
			if(ImageUtil.isImage(file.getFname())){
				result.add(file);
			}
		}
		return result;
	}

	public static String getSmallImagePath(String fpath,boolean isCreate) throws Exception{
		String newPath = fpath.replace("vmifile", "smallvmifile");
		if(isCreate){
			File newFile = new File(newPath);
			if(!newFile.exists()){
				try {
					ImageUtil.saveSmallImage(new File(fpath));
				} catch (Exception e) {
					throw new Exception("图片获取失败！");
				}
			}
		}
		return newPath;
	}

	/***明星方案（作品）图片上传
	 * @throws IOException */
	public String schemesuploadImg() throws Exception{
		String parentid =getRequest().getParameter("fid");
		String fid =this.productdemandfileManager.schemesuploadImgImpl(parentid,file,fileFileName);
		return writeAjaxResponse(fid);
	}

	/***明星方案（作品）图片删除*/
	public String schemedeleteImg(){
		String fid =getRequest().getParameter("fid");
		this.productdemandfileManager.schemedeleteImgImpl(fid);
		return writeAjaxResponse("success");
	}

	/**
	 * 通过父id获取文件流
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public String getSchemeFileByParentId() throws IOException {

		String fid = getRequest().getParameter("fid");
		InputStream in = null;
		OutputStream out = null;
		String path = null;
		try {
			Productdemandfile obj = null;
			List<HashMap<String,String>> list = productdemandfileManager.getBySchemeId(fid);
			//方案作品肯定是图片，不需要判断
			//list = extractImageList(list);
			if(list.size()>0){
				path = list.get(0).get("fpath");
				path = getSmallImagePath(path,true);
			}else{
				path = new File(ServletActionContext.getServletContext().getRealPath("")).getParent() + "/vmifile/schemefile/defaultpic.png";
				if(!new File(path).exists()){
					path = ServletActionContext.getServletContext().getRealPath("") + "/vmifile/schemefile/defaultpic.png";
				}
			}
			in = new FileInputStream(path);
			out = getResponse().getOutputStream();
			IOUtils.copy(in, out);
			in.close();
		} catch (Exception e) {
			getResponse().getWriter().write("图纸不存在！");
		}
		return null;
	}

	/**
	 * 通过图片id获取文件流
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public String getFileByImageId() throws IOException {

		String fid = getRequest().getParameter("fid");
		InputStream in = null;
		OutputStream out = null;
		String path = null;
		try {
			Productdemandfile obj = null;
			List<HashMap<String,String>> list = productdemandfileManager.getByImageId(fid);
			//方案作品肯定是图片，不需要判断
			//list = extractImageList(list);
			if(list.size()>0){
				path = list.get(0).get("fpath");
				path = getSmallImagePath(path,true);
			}else{
				path = new File(ServletActionContext.getServletContext().getRealPath("")).getParent() + "/vmifile/schemefile/defaultpic.png";
				if(!new File(path).exists()){
					path = ServletActionContext.getServletContext().getRealPath("") + "/vmifile/schemefile/defaultpic.png";
				}
			}
			in = new FileInputStream(path);
			out = getResponse().getOutputStream();
			IOUtils.copy(in, out);
			in.close();
		} catch (Exception e) {
			getResponse().getWriter().write("图纸不存在！");
		}
		return null;
	}


	/***
	 * 图片上传
	 * 
	 * @throws IOException
	 */
	public String uploadImgDesignerCertificateInfo() throws Exception {
		String parentid = (String) getRequest().getSession().getAttribute(
				Constant.SESSION_USERID);
		String fid = this.productdemandfileManager.uploadImgImpl(
				parentid, file, fileFileName, "设计服务商认证");
		return writeAjaxResponse(fid);
	}

	/**
	 * 通过图片id获取原图文件流
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public String getoriginFileByImageId() throws IOException {

		String fid = getRequest().getParameter("fid");
		InputStream in = null;
		OutputStream out = null;
		String path = null;
		try {
			Productdemandfile obj = null;
			List<HashMap<String,String>> list = productdemandfileManager.getByImageId(fid);
			//方案作品肯定是图片，不需要判断
			//list = extractImageList(list);
			if(list.size()>0){
				path = list.get(0).get("fpath");
			}else{
				path = new File(ServletActionContext.getServletContext().getRealPath("")).getParent() + "/vmifile/schemefile/defaultpic.png";
				if(!new File(path).exists()){
					path = ServletActionContext.getServletContext().getRealPath("") + "/vmifile/schemefile/defaultpic.png";
				}
			}			
			in = new FileInputStream(path);
			out = getResponse().getOutputStream();
			IOUtils.copy(in, out);
			in.close();
		} catch (Exception e) {
			getResponse().getWriter().write("图纸不存在！");
		}
		return null;
	}
}
