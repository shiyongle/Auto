package com.pc.appInterface;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.pc.controller.BaseController;
import com.pc.dao.UserRole.impl.UserRoleDao;
import com.pc.dao.clUpload.impl.UploadDao;
import com.pc.dao.identification.impl.IdentificationDao;
import com.pc.dao.usercustomer.IUserCustomerDao;
import com.pc.model.CL_Identification;
import com.pc.model.CL_UserCustomer;
import com.pc.model.CL_UserRole;
import com.pc.model.Cl_Upload;
import com.pc.util.ImageUtil;
import com.pc.util.JSONUtil;
import com.pc.util.ServerContext;
import com.pc.util.file.FastDFSUtil;
@Controller
public class AppUploadControll extends BaseController {
	
	@Resource
	private  UploadDao uploadDao;
	@Resource
	private  UserRoleDao userRoleDao;
	@Resource
	private IdentificationDao identificationDao;
	@Resource
	private IUserCustomerDao usercustomerDao;
	
	/***提交认证,并上传文件*/
	@RequestMapping("/app/upload/uploadImg")
	@Transactional(propagation=Propagation.REQUIRED)
	public String uploadImg(HttpServletRequest request,HttpServletResponse response) throws IOException{
		System.out.println("ssssssssssssssssssssssssssssssssssss");
		request.setCharacterEncoding("UTF-8");
		HashMap<String,Object> map =new HashMap<String,Object>();
		    String name=request.getParameter("name");
		    name=new String(name.getBytes("ISO-8859-1"),"UTF-8");
		Integer userId;
		if(request.getParameter("id")==null || "".equals(request.getParameter("id"))){
	    	map.put("success", "false");
			map.put("msg","未登录！");
			return writeAjaxResponse(response, JSONUtil.getJson(map));
	    }else{
	    	userId = Integer.parseInt(request.getParameter("id"));
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
//				String fpath = 	file.getParent()+"/csclfile/"+df.format(new Date())+"/"+dfs.format(new Date());//构造按当前日期保存文件的路径 yyyy-MM-dd
//				String fname = file.getName();//文件名
//				file = new File(fpath);
//				if(!file.exists()){
//					file.mkdirs();
//				}
				int type = Integer.parseInt(request.getParameter("type"));
				//更改角色类型
				CL_UserRole userRole= userRoleDao.getById(userId);
				 userRole.setRoleId(type == -1 ? 2 : 1);
				 //刚注册的用户 认证更新成车主 用到
			     userRoleDao.update(userRole);
				int otherType;
				if(type==1){
					otherType =2;
				}else if(type ==2){
					otherType =1;
				}else{
					otherType =0;
				}
				List<CL_Identification> identification =this.identificationDao.getByUserRoleIdAndType(userId,otherType);
				System.out.println(identification.size());
				if(identification.size()>0){
					identification.get(0).setStatus(4);
					this.identificationDao.update(identification.get(0));
				}
				List<CL_Identification> identification2 =this.identificationDao.getByUserRoleId(userId);
				if(identification2.size()>0){
					for(CL_Identification co:identification2){
						co.setStatus(4);
						this.identificationDao.update(co);
					}
				}
				//新增认证信息
				CL_Identification imodel =new CL_Identification(); 
					imodel.setUserRoleId(Integer.parseInt(request.getParameter("id")));
					if(Integer.parseInt(request.getParameter("type"))==-1){
						imodel.setType(2);
					}else{
						imodel.setType(Integer.parseInt(request.getParameter("type")));
					}
					imodel.setName(new String(request.getParameter("name").getBytes("iso-8859-1"), "utf-8"));
					imodel.setStatus(1);//待审核
					imodel.setCreateTime(new Date());
					this.identificationDao.save(imodel);
				for (Map.Entry<String, MultipartFile> item : fileMap.entrySet()) {
					MultipartFile multifile = item.getValue();
					SimpleDateFormat sf = new SimpleDateFormat("yyMMddHHmmssSSS");
					String fname = multifile.getOriginalFilename();
					try {
						//新增附件信息
						Cl_Upload up = new Cl_Upload();
//						int m =fpath.indexOf("csclfile");
						String newPath = FastDFSUtil.upload_could_change_file_url(multifile.getBytes(),FilenameUtils.getExtension(fname));
							up.setName(fname);
							up.setUrl(newPath);
							up.setParentId(imodel.getId());
							up.setCreateTime(new Date());
							up.setModelName("cl_identification");
							
						
						if(ImageUtil.isImage(fname)){
							File smallfile = ImageUtil.saveSmallImage(multifile, fname);
							up.setSmallurl(FastDFSUtil.upload_could_change_file_url(smallfile));
						}
						this.uploadDao.save(up);
					} catch (Exception e) {
						throw new RuntimeException(e);
					}
				}
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
			map.put("success", "true");
			map.put("msg","操作成功!");
	    }
		System.out.println("************上传打印******************");
		System.out.println(JSONUtil.getJson(map));
		System.out.println("************上传打印******************");
		return writeAjaxResponse(response, JSONUtil.getJson(map));
	}
	
	/***货主-个人认证-跳过*/
	@RequestMapping("/app/upload/nextSkip")
	public String nextSkip(HttpServletRequest request,HttpServletResponse response) throws IOException{
		HashMap<String,Object> map =new HashMap<String,Object>();
		Integer userRoleId,type;
		if(request.getParameter("id")==null || "".equals(request.getParameter("id"))){
	    	map.put("success", "false");
			map.put("msg","请先登录");
			return writeAjaxResponse(response, JSONUtil.getJson(map));
	    }else{
	    	userRoleId = Integer.valueOf(request.getParameter("id"));
	    }
	    if(request.getParameter("type")==null || "".equals(request.getParameter("type"))){
	    	map.put("success", "false");
			map.put("msg","无法获知是企业认证还是个人认证");
			return writeAjaxResponse(response, JSONUtil.getJson(map));
	    }else{
	    	type = Integer.valueOf(request.getParameter("type"));
	    }
	  //新增认证信息
		CL_Identification imodel =new CL_Identification();
			imodel.setUserRoleId(userRoleId);
			imodel.setType(type);
			imodel.setStatus(0);//跳过
			imodel.setCreateTime(new Date());
			this.identificationDao.save(imodel);
	    //2016-4-19 by les start货主跳过认证  生成客户资料信息
		CL_UserCustomer ucmode = new CL_UserCustomer();
		if(usercustomerDao.getByUrid(userRoleId).size()<=0){ //2016-6-6 by twr 判断数据库是否已有客户资料信息  
			int maxId = usercustomerDao.getMaxId()+1;
		    // 0 代表前面补充0      
		    // 4 代表长度为4      
		    // d 代表参数为正数型      
		    String strNumber = "C"+String.format("%08d", maxId);      
		    ucmode.setFnumber(strNumber);
			ucmode.setFname(userRoleDao.getById(userRoleId).getFname());
			ucmode.setFcreateTime(new Date());
			ucmode.setFcreator(userRoleId);
			ucmode.setFuserRoleId(userRoleId);
			usercustomerDao.save(ucmode);
		}
	    //2016-4-19 by les  end 货主跳过认证  生成客户资料信息
		map.put("success", "true");
		map.put("msg","操作成功!");
		return writeAjaxResponse(response, JSONUtil.getJson(map));
	}
	
	
	
	/***提交认证后,查询认证信息***/
	@RequestMapping("/app/upload/queryIdentification")
	public String queryIdentification(HttpServletRequest request,HttpServletResponse response) throws IOException{
		HashMap<String,Object> map =new HashMap<String,Object>();
		Integer userRoleId;
	    if(request.getParameter("id")==null || "".equals(request.getParameter("id"))){
	    	map.put("success", "false");
			map.put("msg","请先登录");
			return writeAjaxResponse(response, JSONUtil.getJson(map));
	    }else{
	    	userRoleId = Integer.valueOf(request.getParameter("id"));
	    }
	    System.out.println("**************************"+userRoleId);
	    List<CL_Identification> rz = this.identificationDao.getByUserRoleIdAndRz(userRoleId);
	    if(rz.size()>0){
	    	map.put("success", "true");
	    	map.put("msg","已认证");
	    	if(rz.get(0).getStatus()==1){//审核中
	    		map.put("data", "[{\"status\":\""+rz.get(0).getStatus()+"\",\"createtime\":\""+rz.get(0).getCreateTimeString()+"\",\"audittime\":\""+rz.get(0).getAuditTimeString()+"\"}]");
	    	}else if(rz.get(0).getStatus()==2){//审核不通过
	    		map.put("data", "[{\"status\":\""+rz.get(0).getStatus()+"\",\"createtime\":\""+rz.get(0).getCreateTimeString()+"\",\"backReason\":\""+rz.get(0).getBackReason()+"\",\"audittime\":\""+rz.get(0).getAuditTimeString()+"\",\"type\":\""+rz.get(0).getType()+"\",\"roletype\":\""+rz.get(0).getRoleId()+"\"}]");

	    	}else if(rz.get(0).getStatus()==3){//审核通过
				map.put("data", "[{\"status\":\""+rz.get(0).getStatus()+"\",\"createtime\":\""+rz.get(0).getCreateTimeString()+"\",\"audittime\":\""+rz.get(0).getAuditTimeString()+"\"}]");
	    	}else if(rz.get(0).getStatus()==0){//跳过
	    		map.put("data", "[{\"status\":\"2\",\"createtime\":\""+rz.get(0).getCreateTimeString()+"\",\"backReason\":\"客户跳过未审核\",\"audittime\":\""+rz.get(0).getCreateTimeString()+"\",\"type\":\""+rz.get(0).getType()+"\",\"roletype\":\""+rz.get(0).getRoleId()+"\"}]");
	    	}
	    	return writeAjaxResponse(response, JSONUtil.getJson(map));
	    }else{
	    	map.put("success", "false");
			map.put("msg","无相关认证信息,请联系客服！");
			return writeAjaxResponse(response, JSONUtil.getJson(map));
	    }
		
	}
	
	/***日志上传接口*/
	@RequestMapping("/app/upload/log")
	@Transactional(propagation=Propagation.REQUIRED)
	public String uploadLog(HttpServletRequest request,HttpServletResponse response) throws IOException{
		request.setCharacterEncoding("UTF-8");
		HashMap<String,Object> map =new HashMap<String,Object>();
	    	request.setCharacterEncoding("UTF-8");
//			ServletFileUpload upload = new ServletFileUpload(new DiskFileItemFactory()); // 创建一个新的文件上传对象
//			upload.setHeaderEncoding("utf-8");//避免中文名乱码
			try {
				MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
				// 表单中对应的文件名；
				Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
//				File file = new File(request.getServletContext().getRealPath("\\"));//获取当前路径
//				String fpath = 	file.getParent()+"/log";
//				System.out.println(fpath);
//				String fname = file.getName();//文件名
				String fname = null;
				for (Map.Entry<String, MultipartFile> item : fileMap.entrySet()) {
					MultipartFile multifile = item.getValue();
					SimpleDateFormat sf = new SimpleDateFormat("yyMMddHHmmssSSS");
					fname = sf.format(new Date())+"-"+multifile.getOriginalFilename();
					try {
						//新增附件信息
						Cl_Upload up = new Cl_Upload();
//						int m =fpath.indexOf("log");
//						String newPath = fpath.substring(m,fpath.length());
						String vmi_id = ServerContext.getUseronline().get(request.getSession().getId()).getFuserid();
						Integer id = userRoleDao.getByVmiUserFid(vmi_id).get(0).getId();
						SimpleDateFormat sdf1 = new SimpleDateFormat("yy-MM-dd 00:00:00");
						SimpleDateFormat sdf2 = new SimpleDateFormat("yy-MM-dd 23:59:59");
						String create_time1 = sdf1.format(new Date());
						String create_time2 = sdf2.format(new Date());
						//控制用户一天之内只能上传3次
						Number num = uploadDao.getLogByUser(id, "log", create_time1, create_time2);
						System.out.println(num);
						if(num.intValue() > 2){
							map.put("success", "false");
							map.put("msg","一天内只能上传3次！");
							return writeAjaxResponse(response, JSONUtil.getJson(map));
						}
						up.setCreateId(id);
						up.setName(fname);
						up.setUrl(FastDFSUtil.upload_could_change_file_url(multifile.getBytes(),FilenameUtils.getExtension(fname)));
						up.setParentId(1);
						up.setCreateTime(new Date());
						up.setModelName("log");
						this.uploadDao.save(up);
//						file = new File(fpath,fname);
//						DataOutputStream out=new DataOutputStream(new FileOutputStream(fpath+"/"+fname));
//						InputStream is=null;
//						try {
//							is = multifile.getInputStream();
//							byte[] b = new byte[is.available()];
//							is.read(b);
//							out.write(b);
//						} catch (Exception e) {
//							// TODO: handle exception
//						} finally {
//							if (is != null) {
//								is.close();
//							}
//							if (out != null) {
//								out.close();
//							}
//						}
						if(ImageUtil.isImage(fname)){
							File smallfile = ImageUtil.saveSmallImage(multifile, fname);
							up.setSmallurl(FastDFSUtil.upload_could_change_file_url(smallfile));
							smallfile.delete();
						}
						this.uploadDao.save(up);
					} catch (Exception e) {
						throw new RuntimeException(e);
					}
				}
//				file = new File(fpath);
//				if(!file.exists()){
//					file.mkdirs();
//				}
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
			map.put("success", "true");
			map.put("msg","操作成功!");
	    
		System.out.println("************上传打印******************");
		System.out.println(JSONUtil.getJson(map));
		System.out.println("************上传打印******************");
		return writeAjaxResponse(response, JSONUtil.getJson(map));
	}
	
	
}
