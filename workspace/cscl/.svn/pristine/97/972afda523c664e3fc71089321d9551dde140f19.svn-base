package com.pc.appInterface.abnormity;


import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.pc.controller.BaseController;
import com.pc.dao.abnormity.IabnormityDao;
import com.pc.dao.addto.IaddtoDao;
import com.pc.dao.clUpload.IuploadDao;
import com.pc.dao.order.IorderDao;
import com.pc.model.CL_Abnormity;
import com.pc.model.Cl_Upload;
import com.pc.util.CacheUtilByCC;
import com.pc.util.ImageUtil;
import com.pc.util.JSONUtil;
import com.pc.util.file.FastDFSUtil;
@Controller
public class AppabnormityController extends BaseController {
	 
	 @Resource
	 private IaddtoDao iaddtoDao;
	 @Resource
	 private IuploadDao iuploadDao;
	 @Resource
	 private IabnormityDao iabnormityDao;
	 @Resource
	 private IorderDao iorderDao;
	  
	 @RequestMapping("/app/abnormity/abnormitySave")
//	 @Transactional(propagation=Propagation.REQUIRED)
     public String abnormitySave(HttpServletRequest request,HttpServletResponse response) throws IOException{
//		 System.out.println("ssssssssssssssssssssssssssssssssssss");
		 request.setCharacterEncoding("UTF-8");
		 Integer userId,ftakeproblem,frecproblem,fcarproblem,orderId,type;
		 HashMap<String,Object> map =new HashMap<String,Object>();
		 if(request.getParameter("userId")==null || "".equals(request.getParameter("userId"))){
		     map.put("success", "false");
			 map.put("msg","未登录！");
			 return writeAjaxResponse(response, JSONUtil.getJson(map));
		 }else{
		    userId=Integer.valueOf(request.getParameter("userId").toString());
		 }
		 if(request.getParameter("orderId")==null || "".equals(request.getParameter("orderId"))){
		     map.put("success", "false");
			 map.put("msg","订单数据有误,请联系客服！");
			 return writeAjaxResponse(response, JSONUtil.getJson(map));
		 }else{
			 orderId=Integer.valueOf(request.getParameter("orderId").toString());
		 }
		 
		 if(request.getParameter("ftakeproblem")==null || "".equals(request.getParameter("ftakeproblem"))){
			 ftakeproblem=0;
		 }else{
			 ftakeproblem=Integer.valueOf(request.getParameter("ftakeproblem").toString());
		 }
		 if(request.getParameter("frecproblem")==null || "".equals(request.getParameter("frecproblem"))){
			 frecproblem=0;
		 }else{
			 frecproblem=Integer.valueOf(request.getParameter("frecproblem").toString());
		 }
		 if(request.getParameter("fcarproblem")==null || "".equals(request.getParameter("fcarproblem"))){
			 fcarproblem=0;
		 }else{
			 fcarproblem=Integer.valueOf(request.getParameter("fcarproblem").toString());
		 }
		 if(request.getParameter("type")==null || "".equals(request.getParameter("type"))){
			    map.put("success", "false");
			    map.put("msg","类型丢失,请联系客服！");
			    return writeAjaxResponse(response, JSONUtil.getJson(map));
		 }else{
			 type=Integer.valueOf(request.getParameter("type").toString());
		 }

			CL_Abnormity abnormity=new CL_Abnormity();
			abnormity.setFtakeproblem(ftakeproblem);
			abnormity.setFcarproblem(fcarproblem);
			abnormity.setFrecproblem(frecproblem);
			abnormity.setFcreateor(userId);
			abnormity.setFcreatTime(new Date());
			abnormity.setForderId(orderId);
			abnormity.setNumber(CacheUtilByCC.getOrderNumber("CL_Abnormity", "A", 8));
			iabnormityDao.save(abnormity);
			if(type==1){
		  	request.setCharacterEncoding("UTF-8");
//			ServletFileUpload upload = new ServletFileUpload(new DiskFileItemFactory()); // 创建一个新的文件上传对象
//			upload.setHeaderEncoding("utf-8");//避免中文名乱码
			SimpleDateFormat dfs = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM");//设置日期格式
			try {	
			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
			// 表单中对应的文件名；
				Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
//				List<FileItem> list  = upload.parseRequest(request);
//				File file = new File(request.getServletContext().getRealPath("\\"));//获取当前路径
//				String fpath = 	file.getParent()+"/csclabnormityfile/"+df.format(new Date())+"/"+dfs.format(new Date());//构造按当前日期保存文件的路径 yyyy-MM-dd
//				String fname = file.getName();//文件名
//				file = new File(fpath);
//				if(!file.exists()){
//					file.mkdirs();
//				}
				for (Map.Entry<String, MultipartFile> item : fileMap.entrySet()) {
					MultipartFile multifile = item.getValue();
					SimpleDateFormat sf = new SimpleDateFormat("yyMMddHHmmssSSS");
					String fname = sf.format(new Date())+"-"+multifile.getOriginalFilename();
					try {
						//新增附件信息
						Cl_Upload up = new Cl_Upload();
//						int m =fpath.indexOf("csclabnormityfile");
//						String newPath = fpath.substring(m,fpath.length());
							up.setName(fname);
							up.setUrl(FastDFSUtil.upload_could_change_file_url(multifile.getBytes(),FilenameUtils.getExtension(fname)));
							up.setParentId(abnormity.getFid());
							up.setCreateTime(new Date());
							up.setModelName("cl_abnormity");
							up.setCreateId(userId);
						
//						file = new File(fpath,fname);
//						DataOutputStream out=new DataOutputStream(new FileOutputStream(fpath+"/"+fname));
//						InputStream is=null;
//						try {
//							is = multifile.getInputStream();
//							byte[] b = new byte[is.available()];
//							is.read(b);
//							out.write(b);
//						} catch (Exception e) {
//							 TODO: handle exception
//						} finally {
//							if (is != null) {
//								is.close();
//							}
//							if (out != null) {
//								out.close();
//							}
//						}
						if(ImageUtil.isImage(fname)){
						File smallfile=ImageUtil.saveSmallAbImage(multifile, fname);
						up.setSmallurl(FastDFSUtil.upload_could_change_file_url(smallfile));
						smallfile.delete();
						}
						this.iuploadDao.save(up);
					} catch (Exception e) {
						throw new RuntimeException(e);
					}
				}
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
			}
			map.put("success", "true");
			map.put("msg","操作成功!");
//			System.out.println("************上传打印******************");
//			System.out.println(JSONUtil.getJson(map));
//			System.out.println("************上传打印******************");
			return writeAjaxResponse(response, JSONUtil.getJson(map));
			
		
  }
		 
}