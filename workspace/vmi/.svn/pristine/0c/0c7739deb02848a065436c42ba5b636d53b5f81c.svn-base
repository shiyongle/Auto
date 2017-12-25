package Com.Controller.System;

import java.io.IOException;
import java.io.InputStream;
import java.io.PushbackInputStream;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.POIXMLDocument;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.tomcat.util.http.fileupload.FileItem;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItemFactory;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import Com.Base.Util.DJException;
import Com.Base.Util.JsonUtil;
import Com.Base.Util.ListResult;
import Com.Base.Util.UploadFile;
import Com.Controller.order.DeliversController;
import Com.Dao.System.IExcelDataDao;
import Com.Entity.System.SysUser;
import Com.Entity.System.Useronline;
import Com.Entity.order.Productdemandfile;

@Controller
public class ExcelDataController {
	
	@Resource
	private IExcelDataDao excelDataDao;
	@Resource
	private DeliversController deliversController;
	
	@RequestMapping("saveExcelData")
	public String saveExcelData(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		try {
			excelDataDao.saveExcelData(request);
			response.getWriter().write(JsonUtil.result(true, "保存成功！", "",""));
		} catch (DJException e) {
			response.getWriter().write(JsonUtil.result(false, e.getMessage(), "",""));
		}
		return null;
	}
	
	@RequestMapping("getExcelDataList")
	public String getExcelDataList(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		request.setAttribute("djsort", "e.fcustomerid");
		String sql = "select e.fid,e.fnumber,e.fendtext,e.ftarget,e.fcreatetime,ifnull(e1.fname,'') fcustomername,e2.fname fcreatorid"
					+" from t_bd_exceldata e left join t_bd_customer e1 on e.fcustomerid=e1.fid"
					+" left join t_sys_user e2 on e.fcreatorid=e2.fid";
		try {
			ListResult result = excelDataDao.QueryFilterList(sql, request);
			response.getWriter().write(JsonUtil.result(true, "", result));
		} catch (DJException e) {
			response.getWriter().write(JsonUtil.result(false, e.getMessage(), "",""));
		}
		return null;
	}
	
	
	@RequestMapping("getExcelDataInfo")
	public String getExcelDataInfo(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String fid = request.getParameter("fid");
		String sql = "select e.fid,e.fnumber,e.ftarget,e.fstartrow,e.fendtext,e.fcreatetime,e.fcreatorid,e1.fid fcustomerid_fid,e1.fname fcustomerid_fname"
					+" from t_bd_exceldata e left join t_bd_customer e1 on e.fcustomerid=e1.fid where e.fid='"+fid+"'";
		try {
			List<HashMap<String, Object>> list = excelDataDao.QueryBySql(sql);
			response.getWriter().write(JsonUtil.result(true, "", "",list));
		} catch (DJException e) {
			response.getWriter().write(JsonUtil.result(false, e.getMessage(), "", ""));
		}
		return null;
	}
	
	@RequestMapping("delExcelDataInfo")
	public String delExcelDataInfo(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String fid =  request.getParameter("fidcls");
		try {
			excelDataDao.DelExcelDataInfo(fid);
			response.getWriter().write(JsonUtil.result(true, "删除成功！", "",""));
		} catch (DJException e) {
			response.getWriter().write(JsonUtil.result(false, e.getMessage(), "",""));
		}
		return null;
	}
	
	/**
	 * 查询分录
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("getExcelDataEntryList")
	public String getExcelDataEntryList(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		request.setAttribute("djsort","findex");
		String sql = "select ftargetfieldname,fdatacolumn,ffixedcell,ffixedvalue,findex,ftargetfieldvalue from t_bd_exceldataentry";
		try {
			ListResult result = excelDataDao.QueryFilterList(sql, request);
			response.getWriter().write(JsonUtil.result(true, "", result));
		} catch (DJException e) {
			response.getWriter().write(JsonUtil.result(false, e.getMessage(), "",""));
		}
		return null;
	}
	/**
	 * 查询类型分录
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("getExcelDataTypeEntryList")
	public String getExcelDataTypeEntryList(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		request.setAttribute("djsort", "findex");
		String sql = "select ftype,fname,fsetvalue,fsettext,findex from t_bd_exceldatatypeentry";
		try {
			ListResult result = excelDataDao.QueryFilterList(sql, request);
			response.getWriter().write(JsonUtil.result(true, "", result));
		} catch (DJException e) {
			response.getWriter().write(JsonUtil.result(false, e.getMessage(), "",""));
		}
		return null;
	}
	
	/**
	 * 查询用户关联的客户
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/getMyCustomerList")
	public String getMyCustomerList(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String userid = ((Useronline)request.getSession().getAttribute("Useronline")).getFuserid();
		String sql = null;
		SysUser userInfo=(SysUser) excelDataDao.Query(SysUser.class, userid);
		if(userInfo.getFisfilter()==1){
			sql = "select fid,fname,fnumber from t_bd_customer e1";
		}else{
			sql = "SELECT e1.fname as fname, e1.fnumber as fnumber, e1.fid as fid from t_bd_usercustomer e left join t_bd_customer e1 on e.fcustomerid = e1.fid where e.fuserid='"+userid+"'";
		}
		ListResult result;
		try {
			result = excelDataDao.QueryFilterList(sql, request);
			response.getWriter().write(JsonUtil.result(true,"",result));
		} catch (DJException e) {
			response.getWriter().write(JsonUtil.result(false,e.getMessage(),"",""));
		}
		return null;
	}
	
	@RequestMapping("saveUploadExcelData")
	public String saveUploadExcelData(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			excelDataDao.saveUploadFile(request);
			int len = excelDataDao.saveImportExcel(request);
			String sql = "update t_ord_cusdelivers cusd,t_bd_address ad set cusd.faddress=ad.fid where cusd.fisread=0 and ifnull(cusd.faddress,'')='' and cusd.freqaddress=ad.fname";
			excelDataDao.ExecBySql(sql);
			response.getWriter().write(JsonUtil.result(true, "操作成功，共导入"+len+"条数据！", "",""));
		} catch (DJException e) {
			response.getWriter().write(JsonUtil.result(false, e.getMessage(), "",""));
		}
		return null;
	}
	@RequestMapping("saveUploadCustExcelData")
	public String saveUploadCustExcelData(HttpServletRequest request,
			HttpServletResponse response)throws IOException{
		try {
			String result = excelDataDao.saveCustExcel(request);
			response.getWriter().write(JsonUtil.result(true, result, "",""));
		} catch (DJException e) {
			response.getWriter().write(JsonUtil.result(false, e.getMessage(), "",""));
		}
		
		return null;
	}
	
	@RequestMapping("saveUploadCustExcelDataToJxlOrPoi")
	public String saveUploadCustExcelDataToJxlOrPoi(HttpServletRequest request,
			HttpServletResponse response)throws IOException{
		try {
			String result = "";
			DiskFileItemFactory factory = new DiskFileItemFactory(); 
			ServletFileUpload upload = new ServletFileUpload(factory);
			List<FileItem> fileItemList = null;
			try {
				fileItemList = upload.parseRequest(request);
			} catch (FileUploadException e) {
				throw new DJException("文件上传失败，请检查文件格式！");
			}
			InputStream in = null;
			for(FileItem item :fileItemList){
				if(!item.isFormField()){
					if(item.getSize()>100 * 1024 * 1024){
						throw new DJException("您上传的文件太大，请选择不超过100M的文件！");
					}
					in = item.getInputStream();
					break;
				}
			}
			if(in==null){
				return null;
			}
			if (!in.markSupported()) {
		        in = new PushbackInputStream(in, 8);
		    }
		    if (POIFSFileSystem.hasPOIFSHeader(in)) {
//		    	 new HSSFWorkbook(in);
		    	result = excelDataDao.SaveJxlExcel(in,request);//03版本的用jxl
		    }
		    if (POIXMLDocument.hasOOXMLHeader(in)) {
		        result = excelDataDao.saveCustExcel(in,request);//07的还是用poi
		    }
			response.getWriter().write(JsonUtil.result(true, result, "",""));
		} catch (DJException e) {
			response.getWriter().write(JsonUtil.result(false, e.getMessage(), "",""));
		}
		
		return null;
	}
}
