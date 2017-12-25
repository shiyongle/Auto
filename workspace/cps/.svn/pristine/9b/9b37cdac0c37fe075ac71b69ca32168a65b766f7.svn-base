package com.service.excel;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import jxl.Workbook;
import jxl.read.biff.BiffException;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dao.IBaseDao;
import com.dao.productdef.ProductdefDao;
import com.model.custproduct.Custrelation;
import com.model.custproduct.Custrelationentry;
import com.model.custproduct.TBdCustproduct;
import com.model.productdef.Productdef;
import com.service.IBaseManagerImpl;
import com.util.StringUitl;
import com.util.excel.ExcelUtils;



@Service("excelDataManagerImpl")
@Transactional(rollbackFor = Exception.class)
public class ExcelDataManagerImpl extends IBaseManagerImpl implements ExcelDataManager {

	@Autowired
	private ProductdefDao productdefDao;
	@Override
	  public  String SaveJxlExcel(InputStream in,String fcustomerId,String userid) throws Exception {  
		  int length = 0;
		  int rowNum = 0;
//	        StringBuffer stringBuffer = new StringBuffer();  
	        Workbook workBook = null;  
	        try {  
	            //构造Workbook（工作薄）对象  
	            workBook=Workbook.getWorkbook(in);  
	        } catch (BiffException e) {  
	            e.printStackTrace();  
	        } catch (IOException e) {  
	            e.printStackTrace();  
			} catch (ArrayIndexOutOfBoundsException e) {  
		          e.printStackTrace();  
		    } 
	        if(workBook==null)  
	            return null;  
	          
	        //获得了Workbook对象之后，就可以通过它得到Sheet（工作表）对象了  
	        jxl.Sheet[] sheet = workBook.getSheets();  
	          
	        if(sheet!=null&&sheet.length>0){  
	            //对每个工作表进行循环  
//	            for(int i=0;i<sheet.length;i++){  
	                //得到当前工作表的行数  
	            	rowNum = sheet[0].getRows();//取第一个工作表  
	                for(int j=0;j<rowNum;j++){
	                    //得到当前行的所有单元格  
	                    jxl.Cell[] cells = sheet[0].getRow(j);  
	                    if(cells!=null&&cells.length>0){  
	                    	if(j==0){
	                    		boolean verify = custFormVerify(cells);
	                    		if(!verify){
	                    			throw new Exception("上传Excel格式不正确");
	                    		}
	                    		continue;
                        	}
	                    	List<String> list  = new ArrayList<>();
	                        //对每个单元格进行循环  
	                        for(int k=1;k<cells.length;k++){//从第2列开始
	                        	if(StringUitl.isNullOrEmpty(cells[1].getContents())||StringUitl.isNullOrEmpty(cells[3].getContents())){//包装物名称和规格为必填项，为空时跳过
	                        		continue;
	                        	}
	                            String cellValue = cells[k].getContents();  
	                            //特殊字符处理  老系统处理，新系统暂不处理
	                          //  cellValue=excelCharaterDeal(cellValue);  
	                            list.add(cellValue);
//	                            stringBuffer.append(cellValue+"\t");  
	                        }  
	                        if(list.size()>0){
	                        	try {
	                        		boolean m = productdefDao.saveListToTable(fcustomerId,list,userid);
	                        		if(m==true){
	            						length+=1;
	            					}
								} catch (Exception e) {
									// TODO: handle exception
									e.printStackTrace();
								}
	                        }
//	                    }  
//	                    stringBuffer.append("\r\n");  
	                }  
//	                stringBuffer.append("\r\n");  
	            }  
	        }  
	        //最后关闭资源，释放内存  
	        workBook.close();  
			String result = "";
			if(length==rowNum-1){
				result = "操作成功，共导入"+length+"条数据!";
			}else{
				result = "操作成功，共导入"+length+"条数据！还有"+(rowNum-length-1<0?0:rowNum-length-1)+"条数据,因为产品名称相同或名称和规格为空没有导入！";//currentRow-length-1 除去标题计算导入产品的总条数。
			}
			
			return result;
	    }  
	  
	/**
	 * 检验在客户产品管理上传EXCEL格式是否正确
	 * @param cit
	 */
	public  boolean custFormVerify(jxl.Cell[] cell){
		String[] name = {"编号","* 包装物名称","包装物编号","* 规格","特性","材料","楞型","单位","描述"};
		List<String> list = new ArrayList<String>();
		for(int i=0;i<cell.length;i++){
			String a = cell[i].getContents();
			list.add(a);
		}
		if(list.size()==0){
			return false;
		}
		for (int i = 0; i < name.length; i++) {
			String m = name[i];
			if(!list.get(i).equals(m)){
				return false;
			}
		}
		return true;
		
	}
		/**
		 * 客户产品管理Excel上传
		 */
			@Override
			public String saveCustExcel(InputStream in,String fcustomerId,String userid) throws Exception {
				Sheet sheet = ExcelUtils.getWorkBook(in).getSheetAt(0);
				Iterator<Row> rit = sheet.rowIterator();
				Row row;
				int currentRow = 0;
				int length = 0;
				Iterator<Cell> cit;
				while(rit.hasNext()){
					row = rit.next();
					currentRow += 1;
					if(currentRow==1){
						cit = row.cellIterator();
						boolean verify = custFormVerify(cit);
						if(verify==false){
							throw new Exception("上传Excel格式不正确");
						}
					}
					if(currentRow>1){
						cit = row.cellIterator();
						List<String> list = saveCellCust(cit,fcustomerId);
						if(list!=null){
							try{
							boolean m = productdefDao.saveListToTable(fcustomerId,list,userid);
							if(m==true){
								length+=1;
							}
							}catch(Exception e){
								e.printStackTrace();
							}
						}
					}
				}
				String result = "";
				if(length==currentRow-1){
					result = "操作成功，共导入"+length+"条数据!";
				}else{
					result = "操作成功，共导入"+length+"条数据！还有"+(currentRow-length-1<0?0:currentRow-length-1)+"条数据,因为产品名称相同或名称和规格为空没有导入！";//currentRow-length-1 除去标题计算导入产品的总条数。
				}
				return result;
			}
			/**
			 * 检验在客户产品管理上传EXCEL格式是否正确
			 * @param cit
			 */
			public boolean custFormVerify(Iterator<Cell> cit){
				String[] name = {"编号","* 包装物名称","包装物编号","* 规格","特性","材料","楞型","单位","描述"};
				List<String> list = new ArrayList<String>();
				while(cit.hasNext()){
					String a = ExcelUtils.getStringCellValue(cit.next());
					list.add(a);
				}
				if(list.size()==0){
					return false;
				}
				for (int i = 0; i < name.length; i++) {
					String m = name[i];
					if(!list.get(i).equals(m)){
						return false;
					}
				}
				return true;
				
			}
			/**
			 * 将客户产品管理上传Excel的单元格保存下来
			 * @param cit
			 * @param fcustomerId
			 * @return
			 */
			public List<String> saveCellCust(Iterator<Cell> cit,String fcustomerId){
				int i = 0;
				List<String> list = new ArrayList<String>();
				while(cit.hasNext()){
					i=i+1;
					String a = ExcelUtils.getStringCellValue(cit.next());
					if(a.equals("") && i<4 && i>1&&i!=3){
						return null;
					}
					if(i==2||i==4){
						if(StringUtils.isEmpty(a)){
							return null;
						}
					}
					if(i>1){
						list.add(a);
					}
				}
				

				return list;
			}
			public  String excelCharaterDeal(String str){  
			       String[] val = {"-","_","/"};//定义特殊字符  
			       for(String i:val){  
			           str=toToken(str, i);  
			       }  
			       return str;  
			   }  
			  public  String toToken(String s, String val) {  
			        if (s == null || s.trim().equals("")) {  
			            return s;  
			        }  
			        if (val == null || val.equals("")) {  
			            return s;  
			        }  
			        StringBuffer stringBuffer = new StringBuffer();  
			        String[] result = s.split(val);  
			         for (int x=0; x<result.length; x++){  
			             stringBuffer.append(" ").append(result[x]);  
			         }  
			        return stringBuffer.toString().trim();  
			          
			    }  
			  

				protected IBaseDao getEntityDao() {
					// TODO Auto-generated method stub
					return null;
				}
				
}
