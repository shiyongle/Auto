package com.pc.controller.upload;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.disk.DiskFileItem;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import cn.org.rapid_framework.page.Page;

import com.pc.controller.BaseController;
import com.pc.dao.clUpload.impl.UploadDao;
import com.pc.model.Cl_Upload;
import com.pc.query.upload.UploadQuery;
import com.pc.util.ImageUtil;
import com.pc.util.JSONUtil;
import com.pc.util.file.FastDFSUtil;

@Controller
public class UploadController extends BaseController{
	protected static final String LIST_JSP= "/pages/pc/upload/upload_list.jsp";
	protected static final String Add_JSP= "/pages/pc/upload/upload_add.jsp";
	protected static final String AddONE_JSP= "/pages/pc/upload/upload_addOne.jsp";
	protected static final String Edit_JSP="/pages/pc/upload/upload_edit.jsp";
	protected static final String UPVIEW_JSP="/pages/pc/upload/upload_upView.jsp";
	@Resource
	private  UploadDao uploadDao;
	private UploadQuery uploadQuery;
    
	
	@RequestMapping("/upload/load")
	public String load(HttpServletRequest request,HttpServletResponse reponse) throws Exception{
		return LIST_JSP;
	}
	@RequestMapping("/upload/add")
	public String add(HttpServletRequest request,HttpServletResponse reponse) throws Exception{
		return Add_JSP;
	}
	@RequestMapping("/upload/addOne")
	public String addOne(HttpServletRequest request,HttpServletResponse reponse) throws Exception{
		return AddONE_JSP;
	}
	@RequestMapping("/upload/addUpView")
	public String addUpView(HttpServletRequest request,HttpServletResponse reponse) throws Exception{
		return UPVIEW_JSP;
	}
 
	//查询列表	
	@RequestMapping("/upload/list")
	public String list(HttpServletRequest request,HttpServletResponse reponse) throws Exception{
		String pageNum = request.getParameter("page");
		String pageSize = request.getParameter("rows");
		if (uploadQuery == null) {
			uploadQuery = newQuery(UploadQuery.class, null);
		}
		if (pageNum != null) {
			uploadQuery.setPageNumber(Integer.parseInt(pageNum));
		}
		if (pageSize != null) {
			uploadQuery.setPageSize(Integer.parseInt(pageSize));
		}
		Page<Cl_Upload> page = uploadDao.findPage(uploadQuery);
		HashMap<String, Object> m = new HashMap<String, Object>();
		m.put("total", page.getTotalCount());
		m.put("rows", page.getResult());
	    request.getSession().setAttribute("count", page.getTotalCount());
		return writeAjaxResponse(reponse, JSONUtil.getJson(m));	 
	}
	     
 	 
    	 
    
	//上传文件
	@RequestMapping("/upload/choosefile")
	public String choosefile(@RequestParam(value = "file", required = false) MultipartFile file,HttpServletRequest request,HttpServletResponse reponse,Integer type,String remark) throws Exception{
		Number num =this.uploadDao.getTotal(type);
		if(request.getParameter("remark")==null || "".equals(request.getParameter("remark"))){
			remark=null; 
		}else{
			remark=remark.replace(" ", "");
		}
		System.out.println(remark);
//	    String path = request.getSession().getServletContext().getRealPath("csclfile");  //取的是Servlet容器对象，相当于tomcat容器了。getRealPath("/") 获取实际路径，“/”指代项目根目录，
		String fileName = file.getOriginalFilename();//获得上传的文件名字
		Long time=System.currentTimeMillis();//获取当前毫秒数
		String str=fileName.substring(0, fileName.indexOf("."));//截取文件名然后用当前时间替换
		fileName=fileName.replace(str, time.toString().substring(0, 11));//替换文件名，防止重复产生冲突
//        File targetFile = new File(path, fileName);
//        int m =path.indexOf("csclfile"); 
		String url=FastDFSUtil.upload_could_change_file_url(file.getBytes(), FilenameUtils.getExtension(fileName));//数据库需要保存的路径
//        if(!targetFile.exists()){  
//            targetFile.mkdirs();  
//        }
		//保存  
//        try {  
//            file.transferTo(targetFile);  
//        } catch (Exception e) {  
//            e.printStackTrace();  
//        }  
		File smallfile = ImageUtil.saveSmallImage(file, fileName);
		String smallurl = FastDFSUtil.upload_could_change_file_url(smallfile);
		smallfile.delete();
		if(num.intValue()<5 && type != 3){
			// 保存成功之后，需要在数据库加入一条数据
			Cl_Upload clu = new Cl_Upload();
			clu.setName(fileName);
			clu.setUrl(url);
			clu.setParentId(0);
			clu.setCreateTime(new Date());
			clu.setCreateId(1);
			clu.setModelName("appindex");
			clu.setType(type);
			clu.setRemark(remark);
			clu.setSmallurl(smallurl);
			int row = this.uploadDao.save(clu);
			request.getSession().setAttribute("imageid", row);
			return writeAjaxResponse(reponse, "success");
		 } else if (type == 3) {
			CommonsMultipartFile cf = (CommonsMultipartFile) file;
			DiskFileItem fi = (DiskFileItem) cf.getFileItem();
			File f = fi.getStoreLocation();
			BufferedImage sourceImg = ImageIO.read(f);
			int width = sourceImg.getWidth();
			int height = sourceImg.getHeight();
			if (width != 600 && height != 800) {
				return writeAjaxResponse(reponse, "faliure1");
			}
			// 保存成功之后，需要在数据库加入一条数据
			Cl_Upload clu = new Cl_Upload();
			clu.setName(fileName);
			clu.setUrl(url);
			clu.setParentId(0);
			clu.setCreateTime(new Date());
			clu.setCreateId(1);
			clu.setModelName("appindex");
			clu.setType(type);
			clu.setRemark(remark);
			clu.setSmallurl(smallurl);
			int row = this.uploadDao.save(clu);
			request.getSession().setAttribute("imageid", row);
			return writeAjaxResponse(reponse, "success");
		}
		else{
			 return  writeAjaxResponse(reponse,  "faliure");
		}
	}
	
	
	//删除数据库图片信息
	@RequestMapping("/upload/deleteImg")
	@Transactional(propagation=Propagation.REQUIRED)
	public String deleteImg(HttpServletRequest request,HttpServletResponse reponse,Integer[] ids) throws Exception{
	 
		for(Integer id:ids){
			 this.uploadDao.deleteById(id);
		}
		return writeAjaxResponse(reponse,"success");
	}
	//加载图片信息
	@RequestMapping("/upload/edit")
		public String edit(HttpServletRequest request,HttpServletResponse reponse) throws Exception{
			return Add_JSP;
		}
	
	
	//更新图片信息
	@RequestMapping("/upload/update")
	public String update(HttpServletRequest request,HttpServletResponse reponse) throws Exception{
		
		return Add_JSP;
	}
	
}
