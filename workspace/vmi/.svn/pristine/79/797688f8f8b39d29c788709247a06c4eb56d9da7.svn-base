package Com.Dao.System;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.tomcat.util.http.fileupload.FileItem;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItemFactory;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;
import org.springframework.stereotype.Service;

import Com.Base.Dao.BaseDao;
import Com.Base.Util.DJException;
import Com.Base.Util.ListResult;

import Com.Entity.System.Productdraw;

@Service
public class ProductdrawDao extends BaseDao implements IProductdrawDao {

	/**
	 * 路径，对应的路径必须存在
	 */
	public static final String FILE_IMAGE_PRODUCT = "\\file\\image\\product\\";
	
	public static final int SAVE_PRODUCT_SUCCESS = 1;
	public static final int SAVE_PRODUCT_PART_SUCCESS = 2; 
	public static final int SAVE_PRODUCT_FAILURE = 0;

	/**
	 * 图片的最大大小限制，单位Bit
	 */
	private static final int MAX_SIZE = 10 * 1024 * 1024;

	@Override
	public Productdraw Query(String fid) {
		// TODO Auto-generated method stub
		return this.getHibernateTemplate().get(Productdraw.class,
				fid);
	}

	@Override
	public HashMap<String, Object> ExecSave(Productdraw info) {
		// TODO Auto-generated method stub
		HashMap<String, Object> params = new HashMap<>();
		if (info.getFid().isEmpty()) {
			info.setFid(this.CreateUUid());
		}
		this.saveOrUpdate(info);

		params.put("success", true);
		return params;
	}

	//抽出的必要性不大，还没看到复用的必要性
//	@Override
//	public boolean ExecSaveProudctImage(String productID, String fdrawnNo,
//			String fversion, FileItem fileItem) {
//		// TODO Auto-generated method stub
//
//		Productdraw pdt = new Productdraw("", productID, "", fdrawnNo, fversion);
//
//		return false;
//	}

	@Override
	public int ExecSaveProudctImageFacade(HttpServletRequest request)
			throws Exception {
		// TODO Auto-generated method stub

		String error = "";
		
		DiskFileItemFactory factory = new DiskFileItemFactory(); // 基于磁盘文件项目创建一个工厂对象
		ServletFileUpload upload = new ServletFileUpload(factory); // 创建一个新的文件上传对象

		List<FileItem> list = upload.parseRequest(request);// 解析上传请求

		Iterator<FileItem> itr = list.iterator();// 枚举方法
		
		while (itr.hasNext()) {

			FileItem item = itr.next(); // 获取FileItem对象
			
			if (!item.isFormField()) {// 判断是否为文件域
				if (item.getName() != null && !item.getName().equals("")) {// 判断是否选择了文件
					
					String productID = request.getParameter("fproductID");
					//url上传时，解决中文乱码问题。
					String drawnNo = new String(request.getParameter("fdrawnNo").getBytes("ISO-8859-1"),"UTF-8");
					String version = new String(request.getParameter("fversion").getBytes("ISO-8859-1"),"UTF-8");
					
					
					long upFileSize = item.getSize(); // 上传文件的大小
					if (upFileSize > MAX_SIZE) {
						error = "您上传的图片太大，请选择不超过10M的文件";
						
						throw new DJException(error);
					}
					
					String name = item.getName();
					String extname = name.substring(name.lastIndexOf(".") + 1)
							.toLowerCase();
					
					if (",jpg,gif,png,bmp,".indexOf("," + extname + ",") >= 0) {
						
						//保存
						
						//创建唯一名称
						UUID uuid = UUID.randomUUID();
						
						String filename = uuid + "." + extname;
						String loadpath = request.getSession()
								.getServletContext()
								.getRealPath(FILE_IMAGE_PRODUCT);
						
						File fNew = new File(loadpath, filename);
						
						// 获得父目录
						File parent = fNew.getParentFile();

						// 不存在就创建父目录
						if (parent != null && !parent.exists()) {
							parent.mkdirs();
						}
						
						item.write(fNew);

						//保存对象 
						
						Productdraw pdt = new Productdraw("", productID, "file/image/product/" + filename, drawnNo, version);
						
						ExecSave(pdt);
						
					} else {
						throw new DJException("错误的文件类型。"); 
					}
				} else {
					error = "没有选择上传文件！";
					throw new DJException(error);
				}
			}
		}

		return SAVE_PRODUCT_SUCCESS;
	}

	@Override
	public ListResult ExecselectProductdrawsByProduct(String productID, HttpServletRequest req) {
		// TODO Auto-generated method stub
		
		String sql = " SELECT * FROM t_pdt_productdraw ";
		
		//配合前端方法所做的修改
		if (productID != null && !productID.trim().isEmpty()) {
				
			sql = " SELECT * FROM t_pdt_productdraw where fproductID = '%s' ";
			
			sql = String.format(sql, productID);
			
		}
		
		return QueryFilterList(sql, req);
	}

	@Override
	public String ExecselectImageByID(String id) {
		// TODO Auto-generated method stub
		
		Productdraw pdT = Query(id);
	
		String relativePath = gainRelativePath(pdT.getFimagePath());
		
		return relativePath;
	}

	private String gainRelativePath(String fimagePath) {
		// TODO Auto-generated method stub
		
		String pathT = fimagePath;
		
//		if (!fimagePath.startsWith("vmi")) { 
//			
//			pathT = fimagePath.substring(fimagePath.indexOf("webapps\\") + "webapps\\".length());
//			
//		}
		
		return pathT;
	}

	@Override
	public void ExecDeleteProudctDraws(String[] ids, HttpServletRequest request) {
		// TODO Auto-generated method stub
		
		for (String idT : ids) {
			
			Productdraw pdT = Query(idT);
			
			deletePOandFile(pdT, request);
			
		}
		
	}

	private void deletePOandFile(Productdraw pdT, HttpServletRequest request) {
		// TODO Auto-generated method stub
		
		String path = pdT.getFimagePath();
		
		//获取文件名
		//String fileName = path.substring("file\\\\image\\\\product\\\\".length());
		String fileName = path.substring(path.lastIndexOf("/")+1);
		String loadpath = request.getSession()
				.getServletContext()
				.getRealPath(FILE_IMAGE_PRODUCT);
		
		File fileT = new File(loadpath + "\\" + fileName);
		
		fileT.delete();
		
		Delete(pdT);
		
	}
}
