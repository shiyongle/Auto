package Com.Dao.System;

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

import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.stereotype.Service;

import Com.Base.Dao.BaseDao;
import Com.Base.Dao.IBaseDao;
import Com.Base.Util.DJException;
import Com.Base.Util.ListResult;
import Com.Base.Util.ServerContext;
import Com.Base.Util.UploadFile;
import Com.Base.Util.params;
import Com.Base.Util.Excel.BaseExcelDataSupport;
import Com.Base.Util.Excel.CombineNameExcelDataSupport;
import Com.Base.Util.Excel.ExcelDataSupport;
import Com.Base.Util.Excel.ExcelUtils;
import Com.Base.Util.Excel.MultiExcelDataSupport;
import Com.Entity.System.Custproduct;
import Com.Entity.System.Custrelation;
import Com.Entity.System.Custrelationentry;
import Com.Entity.System.ExcelData;
import Com.Entity.System.ExcelDataEntry;
import Com.Entity.System.ExcelDataTypeEntry;
import Com.Entity.System.Productdef;
import Com.Entity.System.Useronline;
import Com.Entity.order.Productdemandfile;
@Service("excelDataDao")
public class ExcelDataDao extends BaseDao implements IExcelDataDao {

	@Override
	public void saveExcelData(HttpServletRequest request) {
		ExcelData obj = (ExcelData) request.getAttribute("ExcelData");
		List<ExcelDataEntry> list = (List<ExcelDataEntry>) request.getAttribute("ExcelDataEntry");
		List<ExcelDataTypeEntry> list1 = (List<ExcelDataTypeEntry>) request.getAttribute("ExcelDataTypeEntry");
		String userid = ((Useronline)request.getSession().getAttribute("Useronline")).getFuserid();
		String sql;
		if(obj.getFid()==null||obj.getFid().equals("")){
			if(obj.getFcustomerid()==null){
				sql = "select 1 from t_bd_exceldata where fcustomerid is null";
				if(this.QueryExistsBySql(sql)){
					throw new DJException("默认模板已存在，不能重复添加！");
				}
			}
			sql = "select 1 from t_bd_exceldata where fcustomerid='"+obj.getFcustomerid()+"'";
			if(this.QueryExistsBySql(sql)){
				throw new DJException("此客户已设置Excel模版，不能重复设置！");
			}
			obj.setFid(this.CreateUUid());
			obj.setFnumber(ServerContext.getNumberHelper().getNumber("t_bd_exceldata", "b", 4,false));
			obj.setFcreatetime(new Date());
			obj.setFcreatorid(userid);
		}else{
			if(obj.getFcustomerid()==null){
				sql = "select 1 from t_bd_exceldata where fcustomerid is null and fid !='"+obj.getFid()+"'";
				if(this.QueryExistsBySql(sql)){
					throw new DJException("默认模板已存在，不能重复添加！");
				}
			}
			sql = "select 1 from t_bd_exceldata where fcustomerid='"+obj.getFcustomerid()+"' and fid !='"+obj.getFid()+"'";
			if(this.QueryExistsBySql(sql)){
				throw new DJException("此客户已设置Excel模版，不能重复设置！");
			}
			sql = "delete from t_bd_exceldataentry where fparentid=:fparentid";
			params p=new params();
			p.put("fparentid", obj.getFid());
			this.ExecBySql(sql, p);
			sql = "delete from t_bd_exceldatatypeentry where fparentid=:fparentid";
			this.ExecBySql(sql, p);
		}
		this.saveOrUpdate(obj);
		for(ExcelDataEntry entry : list){
			entry.setFid(this.CreateUUid());
			entry.setFparentid(obj.getFid());
			this.saveOrUpdate(entry);
		}
		for(ExcelDataTypeEntry entry : list1){
			entry.setFid(this.CreateUUid());
			entry.setFparentid(obj.getFid());
			this.saveOrUpdate(entry);
		}
	}

	@Override
	public void DelExcelDataInfo(String fid) {
		fid = "('"+fid.replace(",", "','")+"')";
		String sql = "delete from t_bd_exceldata where fid in "+fid;
		this.ExecBySql(sql);
		sql = "delete from t_bd_exceldataentry where fparentid in "+fid;
		this.ExecBySql(sql);
	}

	@Override
	public int saveImportExcel(HttpServletRequest request) throws IOException{
		String fcustomerId = request.getParameter("action");
		List<Productdemandfile> fileList = (List<Productdemandfile>) request.getAttribute("excel_uploadFiles");
		List<Sheet> sheets = ExcelUtils.getSheets(fileList);
		if(sheets.size()==0){
			throw new DJException("您未上传文件！");
		}
		String hql = "from ExcelData where fcustomerid='"+fcustomerId+"'";
		List<ExcelData> list =  this.QueryByHql(hql);
		if(list.size()==0){
			hql = "from ExcelData where fcustomerid is null";
			list = this.QueryByHql(hql);
			if(list.size()==0){
				throw new DJException("默认Excel模板不存在，请联系平台创建！");
			}
		}
		ExcelData excelData = list.get(0);
		ExcelDataSupport excelDataSupport;
		int currentRow;
		String fuserId = ((Useronline) request.getSession().getAttribute("Useronline")).getFuserid();
		HashMap<String, String> info = new HashMap<>();
		info.put("fcustomerid", fcustomerId);
		info.put("fcreatorid", fuserId);
		int count = 0;
		for(Sheet sheet: sheets){
			excelDataSupport = getExcelDataSupport(excelData, this);
			for(Row row: sheet){
				currentRow = row.getRowNum()+1;
				if(excelDataSupport.isStart(currentRow)){
					excelDataSupport.init();
				}
				for(Cell cell: row){
					excelDataSupport.parseCell(cell);
				}
				excelDataSupport.processRowData();
			}
			count += excelDataSupport.insertData(info);
		}
		changeFileState(fileList);	// 更改文件状态为正确导入
		return count;
	}

	@Override
	public List<Productdemandfile> saveUploadFile(HttpServletRequest request) {
		HashMap<String,String> map = new HashMap<>();
		map.put("fparentid", request.getParameter("action"));
		map.put("ftype", "订单导入");
		List<Productdemandfile> files = UploadFile.upload(request, map);
		if(files.size()==0){
			throw new DJException("您未上传文件！");
		}
		request.setAttribute("excel_uploadFiles", files);
		return files;
	}

	private void changeFileState(List<Productdemandfile> fileList) {
		for(Productdemandfile file: fileList){
			file.setFcharacter("1");	// 导入成功
			this.Update(file);
		}
	}
	public ExcelDataSupport getExcelDataSupport(ExcelData excelData,IBaseDao baseDao){
		String sql = "select 1 from t_bd_exceldataentry where fparentid='"+excelData.getFid()+"'";
		if(this.QueryExistsBySql(sql+" and fdatacolumn like '%\\_%'")){
			return new MultiExcelDataSupport(excelData,baseDao); 
		}else if(this.QueryExistsBySql(sql+" and (fdatacolumn like '%&%' or ffixedcell like '%&%')")){
			return new CombineNameExcelDataSupport(excelData,baseDao);
		}
		return new BaseExcelDataSupport(excelData,baseDao);
	}
/**
 * 客户产品管理Excel上传
 */
	@Override
	public String saveCustExcel(HttpServletRequest request) throws IOException {
		String fcustomerId = request.getParameter("action");
		Sheet sheet = ExcelUtils.getSheet(request);
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
					throw new DJException("上传Excel格式不正确");
				}
			}
			if(currentRow>1){
				cit = row.cellIterator();
				List<String> list = saveCellCust(cit,fcustomerId);
				if(list!=null){
					try{
					boolean m = saveListToTable(fcustomerId,list,request);
					if(m==true){
						length+=1;
					}
					}catch(Exception e){
						e.printStackTrace();
					}
				}
//				else{
//					throw new DJException("请重新检验Excel中的必填项");
//				}
			}
		}
//		if(length==0){
//			throw new DJException("请重新检验Excel");
//		}
		String result = "";
		if(length==currentRow-1){
			result = "操作成功，共导入"+length+"条数据!";
		}else{
			result = "操作成功，共导入"+length+"条数据！还有"+(currentRow-length-1)+"条数据,因为产品名称相同或名称和规格为空没有导入！";//currentRow-length-1 除去标题计算导入产品的总条数。
		}
		return result;
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
	 * 传入集合参数
	 */
	public boolean saveListToTable(String fcustomerId,List<String> list,HttpServletRequest request)throws IOException, ParseException {
		try {
			String sql1 = "select * from cusproduct_treegrid_view where fname='"+list.get(0)+"' and fcustomerid='"+fcustomerId+"'";
			ListResult result1 = this.QueryFilterList(sql1, request);
			String sql2 = "select d.fid  FROM t_pdt_productdef d left join t_sys_user u1 on  u1.fid=d.fcreatorid left join t_sys_user u2 on  u2.fid=d.flastupdateuserid where d.fname='"+list.get(0)+"' and d.fcustomerid='"+fcustomerId+"'";
			ListResult result2 = this.QueryFilterList(sql2, request);
			if(result1.getTotal().equals("0") && result2.getTotal().equals("0")){
				Custproduct info = new Custproduct();
				info.setFid(this.CreateUUid());
				info.setFcustomerid(fcustomerId);
				info.setFcreatetime(new Date());
				info.setFeffect(1);
				info.setFtype(1);
				info.setFname(list.get(0));
				info.setFnumber(list.get(1));
				info.setFspec(list.get(2));
				info.setFcharactername(list.get(3));
				info.setFmaterial(list.get(4));
				info.setFtilemodel(list.get(5));
				info.setForderunit(list.get(6));
				info.setFdescription(list.get(7));
				// 新建产品
				Productdef product = saveProductForExcel(info,request);
				// 新建对应关系
				Custrelation custrelation = new Custrelation();
				
				custrelation.setFid(CreateUUid());
				custrelation.setFcustomerid(info.getFcustomerid());
				custrelation.setFcustproductid(info.getFid());
				this.saveOrUpdate(custrelation);
				Custrelationentry custrelationentry = new Custrelationentry();
				
				custrelationentry.setFid(CreateUUid());
				custrelationentry.setFparentid(custrelation.getFid());
				custrelationentry.setFproductid(product.getFid());
				
				custrelationentry.setFamount(1);
				
				saveOrUpdate(custrelationentry);
				info.setFrelationed(1);
				info.setFproductid(product.getFid());
				this.saveOrUpdate(info);
				return true;
			}
		} catch (DJException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	@Override
	public Productdef saveProductForExcel(Custproduct custproduct,HttpServletRequest request) {
		String userid = ((Useronline)request.getSession().getAttribute("Useronline")).getFuserid();
		String sql="select fsupplierid from ( select fsupplierid  from t_bd_usersupplier where fuserid='"+userid+"'"+
				" union select c.fsupplierid from  t_sys_userrole r "+
				" left join t_bd_rolesupplier c on r.froleid=c.froleid where r.fuserid='"+userid+"' ) s where fsupplierid is not null " ;
		List<HashMap<String,Object>> list=this.QueryBySql(sql);
		if(list.size()==0){
			throw new DJException("当前账号没有制造商！");
		}
		Productdef product = new Productdef();

		product.setFnumber(custproduct.getFnumber());
		product.setFname(custproduct.getFname());
		product.setFcharacter(custproduct.getFspec());
		product.setForderunitid(custproduct.getForderunit());
		product.setFcharacter(custproduct.getFspec());
		product.setFcharacterid(custproduct.getFcharacterid());
		product.setFtilemodelid(custproduct.getFtilemodel());
		product.setFmaterialcode(custproduct.getFmaterial());

		product.setFdescription(custproduct.getFdescription());
		product.setFcustomerid(custproduct.getFcustomerid());
		product.setFid(CreateUUid());
		product.setFcreatetime(custproduct.getFcreatetime());
		product.setFlastupdatetime(custproduct.getFlastupdatetime());
		product.setFeffect(1);//20150618 产品档案导入生成的产品为启用;
		product.setFcreatorid(custproduct.getFcreatorid());
		product.setFsupplierid((String)list.get(0).get("fsupplierid"));
		this.saveOrUpdate(product);
		return product;
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
        return stringBuffer.toString();  
          
    }  
	public  String excelCharaterDeal(String str){  
       String[] val = {"-","_","/"};//定义特殊字符  
       for(String i:val){  
           str=toToken(str, i);  
       }  
       return str;  
   }  
	@Override
	  public   String SaveJxlExcel(InputStream in,HttpServletRequest request){  
		  String fcustomerId = request.getParameter("action");
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
	                    			throw new DJException("上传Excel格式不正确");
	                    		}
	                    		continue;
                        	}
	                    	List<String> list  = new ArrayList<>();
	                        //对每个单元格进行循环  
	                        for(int k=1;k<cells.length;k++){//从第2列开始
	                        	if(StringUtils.isEmpty(cells[1].getContents())||StringUtils.isEmpty(cells[3].getContents())){//包装物名称和规格为必填项，为空时跳过
	                        		continue;
	                        	}
	                            String cellValue = cells[k].getContents();  
	                            //特殊字符处理  
	                            cellValue=excelCharaterDeal(cellValue);  
	                            list.add(cellValue);
//	                            stringBuffer.append(cellValue+"\t");  
	                        }  
	                        if(list.size()>0){
	                        	try {
	                        		boolean m = saveListToTable(fcustomerId,list,request);
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
				result = "操作成功，共导入"+length+"条数据！还有"+(rowNum-length-1)+"条数据,因为产品名称相同或名称和规格为空没有导入！";//currentRow-length-1 除去标题计算导入产品的总条数。
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
			public String saveCustExcel(InputStream in,HttpServletRequest request) throws IOException {
				String fcustomerId = request.getParameter("action");
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
							throw new DJException("上传Excel格式不正确");
						}
					}
					if(currentRow>1){
						cit = row.cellIterator();
						List<String> list = saveCellCust(cit,fcustomerId);
						if(list!=null){
							try{
							boolean m = saveListToTable(fcustomerId,list,request);
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
					result = "操作成功，共导入"+length+"条数据！还有"+(currentRow-length-1)+"条数据,因为产品名称相同或名称和规格为空没有导入！";//currentRow-length-1 除去标题计算导入产品的总条数。
				}
				return result;
			}
}
