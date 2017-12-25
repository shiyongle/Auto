package com.action.excel;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.PushbackInputStream;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.IOUtils;
import org.apache.poi.POIXMLDocument;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.springframework.beans.factory.annotation.Autowired;

import com.action.BaseAction;
import com.service.excel.ExcelDataManager;
import com.util.Constant;
import com.util.JSONUtil;

//文件上传导入
public class ExcelDataAction extends BaseAction {
	

	private static final long serialVersionUID = -4978531527419513509L;
	

	@Autowired
	private ExcelDataManager excelDataManagerImpl;
	private File file;//上传的文件  

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public String saveUploadCustExcelDataToJxlOrPoi(){
		 InputStream in=null;
		try {
			String result = "";
			String userid = getRequest().getSession().getAttribute(Constant.SESSION_USERID).toString();
			String fcustomerid = getRequest().getParameter("fcustomerid");
			if(file==null) throw new Exception("文件上传失败，请检查文件格式！");
			if(file.length()>100 * 1024 * 1024) throw new Exception("您上传的文件太大，请选择不超过100M的文件！");
	        in= new FileInputStream(file);  
			if (!in.markSupported()) {
				in = new PushbackInputStream(in, 8);
		    }
		    if (POIFSFileSystem.hasPOIFSHeader(in)) {
//		    	 new HSSFWorkbook(in);
		    	result = excelDataManagerImpl.SaveJxlExcel(in,fcustomerid,userid);//03版本的用jxl
		    }
		    if (POIXMLDocument.hasOOXMLHeader(in)) {
		        result = excelDataManagerImpl.saveCustExcel(in,fcustomerid,userid);//07的还是用poi
		    }
		return writeAjaxResponse(JSONUtil.result(true, result, "", ""));
		} catch (Exception e) {
			return writeAjaxResponse(JSONUtil.result(false, e.getMessage(), "", ""));
		}finally{
			if(in!=null)
			{
				  IOUtils.closeQuietly(in);  
			}
			 
		}
	}
}
