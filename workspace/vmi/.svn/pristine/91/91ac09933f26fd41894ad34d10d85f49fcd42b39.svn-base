package Com.Dao.System;

import java.util.HashMap;
import javax.servlet.http.HttpServletRequest;

import org.apache.tomcat.util.http.fileupload.FileUploadException;
import Com.Base.Dao.IBaseDao;
import Com.Base.Util.DJException;
import Com.Base.Util.ListResult;
import Com.Entity.System.Productdraw;

/**
 * 
 * 
 * 
 * @author ZJZ (447338871@qq.com, any Question)
 * @date 2014-3-31 上午10:44:14
 */
public interface IProductdrawDao extends IBaseDao {
	Productdraw Query(String fid);

	HashMap<String, Object> ExecSave(Productdraw info);


//	/**
//	 * 保存产品图片,单个方法，
//	 *
//	 * @param productID
//	 * @param fdrawnNo
//	 * @param fversion
//	 * @param fileItem
//	 * @return 成功?
//	 *
//	 * @date 2014-3-31 上午11:07:59  (ZJZ)
//	 */
//	boolean ExecSaveProudctImage(String productID, String fdrawnNo, String fversion, FileItem fileItem);

	
	/**
	 * 保存产品图片门面，可保存多个图片?
	 *
	 * @param HttpServletRequest
	 * @return
	 * @throws FileUploadException 
	 * @throws Exception 
	 *
	 * @date 2014-3-31 上午11:10:13  (ZJZ)
	 */
	int ExecSaveProudctImageFacade(HttpServletRequest req) throws DJException, FileUploadException, Exception;

	/**
	 * 
	 *
	 * @param productID
	 * @param req
	 * @return
	 *
	 * @date 2014-4-3 下午2:37:36  (ZJZ)
	 */
	ListResult ExecselectProductdrawsByProduct(String productID, HttpServletRequest req);
	
	/**
	 * 获得图片路径，用的是Productdraws的主键
	 *
	 * @param id，Productdraws的主键
	 * @return，路径
	 *
	 * @date 2014-4-3 下午2:38:31  (ZJZ)
	 */
	String ExecselectImageByID(String id);
	
	/**
	 * 删除图片，同时删除文件
	 *
	 * @param ids
	 * @param request TODO
	 *
	 * @date 2014-4-4 上午10:52:11  (ZJZ)
	 */
	void ExecDeleteProudctDraws(String[] ids, HttpServletRequest request);
}
