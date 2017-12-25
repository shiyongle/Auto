package com.util.excel;

import java.io.FileInputStream;
import java.io.InputStream;
import java.io.PushbackInputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.poi.POIXMLDocument;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.model.productdemandfile.Productdemandfile;


public class ExcelUtils {

	/**
	 * 获取工作簿对象
	 * @param inp
	 * @return
	 * @throws Exception
	 * @throws InvalidFormatException
	 */
	public static Workbook getWorkBook(InputStream inp) throws Exception{
		if (!inp.markSupported()) {
	        inp = new PushbackInputStream(inp, 8);
	    }
	    if (POIFSFileSystem.hasPOIFSHeader(inp)) {
	    	return new HSSFWorkbook(inp);
	    }
	    if (POIXMLDocument.hasOOXMLHeader(inp)) {
	        try {
				return new XSSFWorkbook(OPCPackage.open(inp));
			} catch (InvalidFormatException e) {
				throw new Exception("你的excel版本目前无法解析");
			}
	    }
	    return null;
	}
	/**
	 * 获取上传的第一个工作簿对象
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public static Workbook getWorkBook(HttpServletRequest request) throws Exception{
		DiskFileItemFactory factory = new DiskFileItemFactory(); 
		ServletFileUpload upload = new ServletFileUpload(factory);
		List<FileItem> fileItemList = null;
		try {
			fileItemList = upload.parseRequest(request);
		} catch (FileUploadException e) {
			throw new Exception("文件上传失败，请检查文件格式！");
		}
		InputStream in = null;
		for(FileItem item :fileItemList){
			if(!item.isFormField()){
				if(item.getSize()>100 * 1024 * 1024){
					throw new Exception("您上传的文件太大，请选择不超过100M的文件！");
				}
				in = item.getInputStream();
				break;
			}
		}
		if(in==null){
			return null;
		}
		return getWorkBook(in);
	}
	/**
	 * 多文件上传时获取多工作簿对象
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public static List<Workbook> getWorkBooks(HttpServletRequest request) throws Exception{
		DiskFileItemFactory factory = new DiskFileItemFactory(); 
		ServletFileUpload upload = new ServletFileUpload(factory);
		List<FileItem> fileItemList = null;
		List<Workbook> workbookList = new ArrayList<>();
		try {
			fileItemList = upload.parseRequest(request);
		} catch (FileUploadException e) {
			throw new Exception("文件上传失败，请检查文件格式！");
		}
		for(FileItem item :fileItemList){
			if(!item.isFormField()){
				if(item.getSize()>100 * 1024 * 1024){
					throw new Exception("您上传的文件太大，请选择不超过100M的文件！");
				}
				workbookList.add(getWorkBook(item.getInputStream()));
			}
		}
		
		return workbookList;
	}
	/**
	 * 获取第一个工作表
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public static Sheet getSheet(HttpServletRequest request) throws Exception{
		Workbook book = getWorkBook(request);
		try {
			Sheet sheet = book.getSheetAt(0);
			return sheet;
		} catch (Exception e) {
			throw new Exception("你的excel版本目前无法解析");
		}
	}
	/**
	 * 获取多个工作表
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public static List<Sheet> getSheets(HttpServletRequest request) throws Exception{
		List<Workbook> books = getWorkBooks(request);
		List<Sheet> sheets = new ArrayList<>();
		try {
			for(Workbook book: books){
				sheets.add(book.getSheetAt(0));
			}
			return sheets;
		} catch (Exception e) {
			throw new Exception("你的excel版本目前无法解析");
		}
	}
/**
	 * 获取多个工作表
	 * @param fileList
	 * @return
	 */
	/*public static List<Sheet> getSheets(List<Productdemandfile> fileList) throws Exception {
		List<Workbook> books = getWorkBooks(fileList);
		List<Sheet> sheets = new ArrayList<>();
		try {
			for(Workbook book: books){
				sheets.add(book.getSheetAt(0));
			}
			return sheets;
		} catch (Exception e) {
			throw new Exception("你的excel版本目前无法解析");
		}
	}*/
	/**
	 * 获取多个工作簿对象
	 * @param fileList
	 * @return
	 */
	/*private static List<Workbook> getWorkBooks(List<Productdemandfile> fileList)throws Exception {
		List<Workbook> workbookList = new ArrayList<>();
		for(Productdemandfile fileObj :fileList){
			try {
				workbookList.add(getWorkBook(new FileInputStream(fileObj.getFpath())));
			}catch (Exception e) {
				throw new Exception("文件解析失败！");
			}
		}
		return workbookList;
	}*/
	/**
	 * 获取单元格引用
	 * @param cell
	 * @return
	 */
	public static String getCellReference(Cell cell){
		int r = cell.getRowIndex()+1;
		int c = cell.getColumnIndex();
		String result="";
		boolean doub = false;
		do{
			if(doub){
				c-=1;
			}
			result = (char)(c%26+65) + result;
			doub = true;
		}while((c/=26)>0);
		return result+r;
	}
	
	
	/**
	 * 获取单元格的文本值
	 * @param cell
	 * @return
	 */
	public static String getStringCellValue(Cell cell) {
        String strCell = "";
        switch (cell.getCellType()) {
        case Cell.CELL_TYPE_STRING:
            strCell = cell.getStringCellValue();
            break;
        case Cell.CELL_TYPE_NUMERIC:
            double value = cell.getNumericCellValue();
            if(DateUtil.isCellDateFormatted(cell)){
            	Date date = cell.getDateCellValue();
            	Calendar c = Calendar.getInstance();
            	c.setTime(date);
            	strCell = c.get(Calendar.YEAR) + "-" + (c.get(Calendar.MONTH) + 1)+ "-" + c.get(Calendar.DAY_OF_MONTH);
            }else{
            	strCell = String.valueOf(value);
            }
            break;
        case Cell.CELL_TYPE_BOOLEAN:
            strCell = String.valueOf(cell.getBooleanCellValue());
            break;
        case Cell.CELL_TYPE_BLANK:
            strCell = "";
            break;
        default:
            strCell = "";
            break;
        }
        if (strCell.equals("") || strCell == null) {
            return "";
        }
        return strCell;
    }
	/**
	 * 将字符串转换成日期
	 * @param val
	 * @return
	 */
	public static Date convertStringToDate(String val) throws Exception{
		if("".equals(val)){
			return null;
		}
		System.out.println("val: "+val);
		String[] dateFormat={"yyyy-MM-dd HH:mm:ss","yyyy-MM-dd HH:mm","yyyy-MM-dd","yyyy年MM月dd日"};
		SimpleDateFormat sdf = null;
		Date date = null;
		for(String s : dateFormat){
			sdf= new SimpleDateFormat(s);
			try {
				date = sdf.parse(val);
			} catch (ParseException e) {
				
			}
			if(date!=null){
				break;
			}
		}
		if(date==null){
			Calendar c = Calendar.getInstance();
			val = c.get(Calendar.YEAR)+'年'+val;
			try {
				date = sdf.parse(val);
			} catch (ParseException e) {
				throw new Exception("日期格式不正确，请更改！");
			}
		}
		return date;
	}
	

}
