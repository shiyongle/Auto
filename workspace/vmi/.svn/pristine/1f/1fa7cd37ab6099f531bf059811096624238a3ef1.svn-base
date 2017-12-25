package Com.Base.Util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;

import com.sun.org.apache.bcel.internal.generic.ARRAYLENGTH;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

public class ExcelUtil {
	public static void toexcel(HttpServletResponse response,ListResult result) throws DJException {
		response.setContentType("multipart/form-data");
		response.setHeader("Content-Disposition", "attachment;fileName="+UUID.randomUUID()+ ".xls");
		if(result.getTotal() != null && !"".equals(result.getTotal())  && result.getTotal().equals("GenerateDelivers")){	
			try {
				response.setHeader("Content-Disposition", "attachment;fileName="+ new String("要货申请导入".getBytes("UTF-8"),"iso-8859-1")+ ".xls");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		WritableWorkbook book = null;
		try {
			book = Workbook.createWorkbook(response.getOutputStream());
			WritableSheet sheet = book.createSheet("数据", 0);
			List<HashMap<String, Object>> slist = result.getData();
			Set<String> set = slist.get(0).keySet();
			Iterator<String> it = set.iterator();
			int colunmnum = 0, rownum = 1;
			while (it.hasNext()) {
				String key = it.next();
				Label label = new Label(colunmnum,0 , key);
				sheet.addCell(label);
				colunmnum++;
			}
			for (HashMap<String, Object> o : slist) {
				colunmnum=0;
				it = set.iterator();
				while (it.hasNext()) {
					String key = it.next();
					if(o.get(key)!=null)
					{
						Label label = new Label(colunmnum, rownum, o.get(key).toString());
						sheet.addCell(label);
					}
					colunmnum++;
				}
				rownum++;
			}
			book.write();
			book.close();
		} catch (IOException | WriteException e) {
			e.printStackTrace();
			throw new DJException("导出失败!");
		}
	}
	
	
	public static void toexcel(HttpServletResponse response,ListResult result,Map<String, String> map) throws DJException{
		response.setContentType("multipart/form-data");
		response.setHeader("Content-Disposition", "attachment;fileName="+UUID.randomUUID()+".xls");
		WritableWorkbook book = null;
		List<String> order = new ArrayList<>();
		try {
			book = Workbook.createWorkbook(response.getOutputStream());
			WritableSheet sheet = book.createSheet("数据", 0);
			List<HashMap<String, Object>> list = result.getData();
			Set<String> columns = list.get(0).keySet();
			int columnNum = 0;
			for(String name: columns){
				if(map.containsKey(name)){
					Label label = new Label(columnNum++,0 , map.get(name));
					order.add(name);
					sheet.addCell(label);
				}
			}
			int rowNum = 1;
			for(HashMap<String, Object> data : list){
				for(String name: columns){
					if(map.containsKey(name)&& data.get(name)!=null){
						Label label = new Label(order.indexOf(name), rowNum,data.get(name).toString());
						sheet.addCell(label);
					}
				}
				rowNum++;
			}
			book.write();
		}catch(IOException | WriteException e){
			throw new DJException("导出失败!");
		}finally{
			try {
				book.close();
			}catch(Exception e){
				throw new RuntimeException(e);
			}
		}
	}
	/**
	 * 排序的导出
	 * @param response
	 * @param result
	 * @param map
	 * @param order	列的顺序
	 * @throws DJException
	 */
	public static void toexcel(HttpServletResponse response,ListResult result,Map<String, String> map,List<String> order) throws DJException{
		response.setContentType("multipart/form-data");
		response.setHeader("Content-Disposition", "attachment;fileName="+UUID.randomUUID()+".xls");
		WritableWorkbook book = null;
		try {
			book = Workbook.createWorkbook(response.getOutputStream());
			WritableSheet sheet = book.createSheet("数据", 0);
			List<HashMap<String, Object>> list = result.getData();
			Set<String> columns = list.get(0).keySet();
			for(String name: columns){
				if(map.containsKey(name)){
					Label label = new Label(order.indexOf(name),0 , map.get(name));
					sheet.addCell(label);
				}
			}
			int rowNum = 1;
			for(HashMap<String, Object> data : list){
				for(String name: columns){
					if(map.containsKey(name)&& data.get(name)!=null){
						Label label = new Label(order.indexOf(name), rowNum,data.get(name).toString());
						sheet.addCell(label);
					}
				}
				rowNum++;
			}
			book.write();
		}catch(IOException | WriteException e){
			throw new DJException("导出失败!");
		}finally{
			try {
				book.close();
			}catch(Exception e){
				throw new RuntimeException(e);
			}
		}
	}
	
	
	/**
	 * 排序的导出,没有过滤器
	 * @param response
	 * @param result
	 * @param order	列的顺序
	 * @throws DJException
	 */
	public static void toexcel(HttpServletResponse response, ListResult result,
			List<String> order) throws DJException {
		response.setContentType("multipart/form-data");
		response.setHeader("Content-Disposition",
				"attachment;fileName=" + UUID.randomUUID() + ".xls");
		WritableWorkbook book = null;
		try {
			book = Workbook.createWorkbook(response.getOutputStream());
			WritableSheet sheet = book.createSheet("数据", 0);
			
			List<HashMap<String, Object>> list = result.getData();
			
			Set<String> columns = list.get(0).keySet();

			for (String name : columns) {

				Label label = new Label(order.indexOf(name), 0, name);
				sheet.addCell(label);

			}
			int rowNum = 1;
			for (HashMap<String, Object> data : list) {
				for (String name : columns) {

					Label label = new Label(order.indexOf(name), rowNum,
							data.get(name) == null ? "" : data.get(name)
									.toString());
					sheet.addCell(label);

				}
				rowNum++;
			}
			book.write();
		} catch (IOException | WriteException e) {
			throw new DJException("导出失败!");
		} finally {
			try {
				book.close();
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
	}
	
	public static String toexcel(HttpServletResponse reponse,ListResult result,String str) throws DJException {
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddhhmmss");
		String med = str+df.format(new Date());
		String filename = med+ ".xls";
		String filepath = "d:/" + filename;
		try {
			reponse.setContentType("multipart/form-data");
			
			reponse.setHeader("Content-Disposition", "attachment;fileName="+URLEncoder.encode(filename,"utf-8"));
			File os = new File(filepath);
			WritableWorkbook book = Workbook.createWorkbook(os);
			WritableSheet sheet = book.createSheet("数据", 0);
			List<HashMap<String, Object>> slist = result.getData();
			HashMap<String, Object> h = slist.get(0);
			Set<String> set = h.keySet();
			Iterator<String> it = set.iterator();
			int colunmnum = 0, rownum = 1;
			while (it.hasNext()) {
				String key = it.next();
				Label label = new Label(colunmnum,0 , key);
				sheet.addCell(label);
				colunmnum++;
			}
			colunmnum = 0;
			for (HashMap<String, Object> o : slist) {
				it = set.iterator();
				while (it.hasNext()) {
					String key = it.next();
					if(o.get(key)!=null)
					{
						Label label = new Label(colunmnum, rownum, o.get(key)
								.toString());
						sheet.addCell(label);
					}
					colunmnum++;
				}
				colunmnum=0;
				rownum++;
			}
			book.write();
			book.close();
			InputStream inputStream = new FileInputStream(filepath);
			byte[] b = new byte[1024];
			int length;
			while ((length = inputStream.read(b)) > 0) {
				reponse.getOutputStream().write(b, 0, length);
			}
			inputStream.close();
			os.delete();
		} catch (Exception e) {
			throw new DJException(e.getMessage());
		}
		return filepath;
	}

	
	
}
