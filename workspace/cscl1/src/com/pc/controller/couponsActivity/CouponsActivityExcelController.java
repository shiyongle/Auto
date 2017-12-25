package com.pc.controller.couponsActivity;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.pc.controller.BaseController;
import com.pc.model.CL_UserRole;
import com.pc.util.JSONUtil;

@Controller
public class CouponsActivityExcelController extends BaseController {
	
	/**
	 * 解析excel数据
	 * @param request
	 * @param response
	 * @param filePath
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("/couponsActivity/readExecl")
	public String readExcel(HttpServletRequest request, HttpServletResponse response, @RequestParam CommonsMultipartFile file) throws Exception{
		HashMap<String, Object> m = new HashMap<String, Object>();
		Workbook wb = null;
		try {
			InputStream is = file.getInputStream();
			try {
				if (file.getOriginalFilename().endsWith(".xlsx")) {
					wb = new XSSFWorkbook(is);
				} else if (file.getOriginalFilename().endsWith(".xls")) {
					wb = new HSSFWorkbook(is);
				} else {
					m.put("success", "false");
					m.put("msg", "不支持此文件格式");
					return writeAjaxResponse(response, JSONUtil.getJson(m));
				}
			} catch (IOException e) {
				e.printStackTrace();
				m.put("success", "false");
				m.put("msg", "文件解析出错，请确保你的文件是Excel文件");
				return writeAjaxResponse(response, JSONUtil.getJson(m));
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			m.put("success", "false");
			m.put("msg", "文件解析出错，请确保你的文件是Excel文件");
			return writeAjaxResponse(response, JSONUtil.getJson(m));
		}
		Sheet sheet = wb.getSheetAt(0);//获取第一个工作簿
		Row headRow = sheet.getRow(1);//表头
		boolean isRightModel = true;
		String headStr[] = new String[]{"姓名","手机号"};
		if(headRow != null){
			for(int i = 0;i < headStr.length;i++){//遍历列
				Cell cell = headRow.getCell(i);
				cell.setCellType(Cell.CELL_TYPE_STRING);
				if(cell == null || !headStr[i].equals(cell.getStringCellValue())){
					isRightModel = false;
					break;
				}
			}
		}else{
			isRightModel =false;
		}
		if(!isRightModel){
			m.put("success", "false");
			m.put("msg", "模板格式不正确，请录入指定模板");
			return writeAjaxResponse(response, JSONUtil.getJson(m));
		}
		List<CL_UserRole> list = new ArrayList<>();//记录从excel表中读出来的数据
		for(int i = 2;i <= sheet.getLastRowNum();i++){
			Row row = sheet.getRow(i);
			if(row == null){
				continue;
			}
			for(int j = 0; j<2;j++){//把每个单元格的读取模式设置成字符串
				if(row.getCell(j)!=null){
					row.getCell(j).setCellType(Cell.CELL_TYPE_STRING);//玛德说好的转String，特么还有小数点
				}
			}
			CL_UserRole user = new CL_UserRole();
			if(row.getCell(0) != null && row.getCell(0).getStringCellValue().indexOf(".0") != -1){//这样会不会太蠢？
				user.setVmiUserName(row.getCell(0).getStringCellValue().substring(0, row.getCell(0).getStringCellValue().length()-2));//姓名
			} else {
				user.setVmiUserName(row.getCell(0) == null?"":row.getCell(0).getStringCellValue());
			}
			if(row.getCell(1) != null && row.getCell(1).getStringCellValue().indexOf(".0") != -1){
				user.setVmiUserPhone(row.getCell(1).getStringCellValue().substring(0, row.getCell(1).getStringCellValue().length()-2));//手机
			} else if (row.getCell(1) != null && row.getCell(1).getStringCellValue().length() == 11) {
				if(Pattern.matches("^1[\\d]{10}", row.getCell(1).getStringCellValue())){
					user.setVmiUserPhone(row.getCell(1).getStringCellValue());
				} else {
					m.put("success", "false");
					m.put("msg", "第"+(i+1)+"行手机号输入格式不正确，请确认！");
					return writeAjaxResponse(response, JSONUtil.getJson(m));
				}
			}else {
				try {
					user.setVmiUserPhone(row.getCell(1) == null?"":new BigDecimal(row.getCell(1).getStringCellValue()).toPlainString());
				} catch (Exception e2) {
					e2.printStackTrace();
					m.put("success", "false");
					m.put("msg", "第"+(i+1)+"行手机号输入格式不正确，请确认！");
					return writeAjaxResponse(response, JSONUtil.getJson(m));
				}
			}
			if(user.getVmiUserName().length() == 0 && user.getVmiUserPhone().length() == 0){//如果该行记录为空则跳过
				continue;
			}
			list.add(user);
		}
		m.put("success", "true");
		m.put("data", list);
		return writeAjaxResponse(response, JSONUtil.getJson(m));
	}
}
