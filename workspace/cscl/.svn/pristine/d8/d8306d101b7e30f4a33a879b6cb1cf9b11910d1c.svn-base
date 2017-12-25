package com.pc.pcWeb.pcWebupload;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

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
public class pcWebuploadController extends BaseController {
	@Resource
	private  UploadDao uploadDao;
	@Resource
	private  UserRoleDao userRoleDao;
	@Resource
	private IdentificationDao identificationDao;
	@Resource
	private IUserCustomerDao usercustomerDao;
	
	/***提交认证,并上传文件*/
	@RequestMapping("/pcWeb/upload/uploadImg")
	@Transactional(propagation=Propagation.REQUIRED)
	public String uploadImg(HttpServletRequest request,HttpServletResponse response,@RequestParam("fileField") MultipartFile[] files) throws IOException, FileUploadException{
		System.out.println("ssssssssssssssssssssssssssssssssssss");
		request.setCharacterEncoding("UTF-8");
		HashMap<String,Object> map =new HashMap<String,Object>();
	    String name=request.getParameter("fname");
	    name=new String(name.getBytes("ISO-8859-1"),"UTF-8");
	    Integer userId;
		if(!ServerContext.getUseronline().containsKey(request.getSession().getId().toString())){
	    	map.put("success", "false");
			map.put("msg","未登录！");
			return writeAjaxResponse(response, JSONUtil.getJson(map));
		//if(ServerContext.getUseronline().get(request.getSession().getId())!=null)
	    }else{
	    	System.out.println(ServerContext.getUseronline().get(request.getSession().getId().toString()).getFuserId());
	    	userId =ServerContext.getUseronline().get(request.getSession().getId().toString()).getFuserId();
	    	request.setCharacterEncoding("UTF-8");
//			ServletFileUpload upload = new ServletFileUpload(new DiskFileItemFactory()); // 创建一个新的文件上传对象
//			upload.setHeaderEncoding("utf-8");//避免中文名乱码
//			SimpleDateFormat dfs = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
//			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM");//设置日期格式
//			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
			// 表单中对应的文件名；
//			Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
//	List<FileItem> list  = upload.parseRequest(request);
//			File file = new File(request.getServletContext().getRealPath("\\"));//获取当前路径
//			String fpath = 	file.getParent()+"/csclfile/"+df.format(new Date())+"/"+dfs.format(new Date());//构造按当前日期保存文件的路径 yyyy-MM-dd
//			String fname = file.getName();//文件名
//			file = new File(fpath);
//			if(!file.exists()){
//				file.mkdirs();
//			}
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
				imodel.setUserRoleId(userId);
				if(type==-1){
					imodel.setType(2);
				}else{
					imodel.setType(type);
				}
				imodel.setName(name);
				imodel.setStatus(1);//待审核
				imodel.setCreateTime(new Date());
				this.identificationDao.save(imodel);
			for (int i=0;i<files.length;i++) {
				MultipartFile multifile = files[i];
				SimpleDateFormat sf = new SimpleDateFormat("yyMMddHHmmssSSS");
				String  fname = sf.format(new Date())+"-"+new String(multifile.getOriginalFilename().getBytes("ISO-8859-1"),"UTF-8");
				try {
					
					//新增附件信息
					Cl_Upload up = new Cl_Upload();
//					int m =fpath.indexOf("csclfile");
					String newPath = FastDFSUtil.upload_file_url(multifile.getBytes(), FilenameUtils.getExtension(fname));
						up.setName(fname);
						up.setUrl(newPath);
						up.setParentId(imodel.getId());
						up.setCreateTime(new Date());
						up.setModelName("cl_identification");
//					file = new File(fpath,fname);
//					DataOutputStream out=new DataOutputStream(new FileOutputStream(fpath+"/"+fname));
//					InputStream is=null;
//					try {
//						is = multifile.getInputStream();
//						byte[] b = new byte[is.available()];
//						is.read(b);
//						out.write(b);
//					} catch (Exception e) {
//						// TODO: handle exception
//					} finally {
//						if (is != null) {
//							is.close();
//						}
//						if (out != null) {
//							out.close();
//						}
//					}
					if(ImageUtil.isImage(fname)){
						File smallfile = ImageUtil.saveSmallImage(multifile,fname);
						up.setSmallurl(FastDFSUtil.upload_file_url(smallfile));
						smallfile.delete();
					}
					this.uploadDao.save(up);
				} catch (Exception e) {
					throw new RuntimeException(e);
				}
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
	@RequestMapping("/pcWeb/upload/pcWebnextSkip")
	public String pcWebnextSkip(HttpServletRequest request,HttpServletResponse response) throws IOException{
		HashMap<String,Object> map =new HashMap<String,Object>();
		Integer userRoleId,type;
		   Integer userId;
		if(!ServerContext.getUseronline().containsKey(request.getSession().getId().toString())){
		    	map.put("success", "false");
				map.put("msg","未登录！");
				return writeAjaxResponse(response, JSONUtil.getJson(map));
			//if(ServerContext.getUseronline().get(request.getSession().getId())!=null)
		 }else{
	    	userRoleId = ServerContext.getUseronline().get(request.getSession().getId().toString()).getFuserId();
	    }
	    if(request.getParameter("type")==null || "".equals(request.getParameter("type"))){
	    	map.put("success", "false");
			map.put("msg","无法获知是企业认证还是个人认证");
			return writeAjaxResponse(response, JSONUtil.getJson(map));
	    }else{
	    	type = Integer.valueOf(request.getParameter("type"));
	    }
	  //新增认证信息
	    CL_Identification iden=this.identificationDao.getByUserRoleIdAndStatus(userRoleId, 0);
	    if(iden!=null){
	    	map.put("success", "true");
			map.put("msg","该用户已经跳过");
			return writeAjaxResponse(response, JSONUtil.getJson(map));
	    }
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
}
