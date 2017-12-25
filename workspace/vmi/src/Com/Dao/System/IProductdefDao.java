package Com.Dao.System;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Com.Base.Dao.IBaseDao;
import Com.Entity.System.Productdef;

public interface IProductdefDao extends IBaseDao {
	  public HashMap<String,Object> ExecSave(Productdef cust);
	  public Productdef Query(String fid);
	  
	  /**
	   * 根据返回的字符串来判断，false就是历史版本，不能操作。
	   *
	   * @param fid
	   * @return
	   *
	   * @date 2014-4-8 下午1:42:20  
	   */
	  public String QueryIsHistory(String fid);
	  
	  /**
	   * 处理产品的历史版本，会直接返回对应的json回复<br\>
	   * 在true时直接返回null，结束方法即可
	   *
	   *
	   * @param fid
	   * @param req
	   * @return 是历史版本
	 * @throws IOException 
	   *
	   * @date 2014-4-8 下午2:48:52  (ZJZ)
	   */
	  boolean dealHistoryProduct(String fid, HttpServletResponse req) throws IOException;
	  
	  /**
	   * 根据返回的字符串来判断，false就是历史版本，不能操作。
	   *
	   * @param fid
	   * @return
	   *
	   * @date 2014-4-8 下午1:42:20
	   */
	  public boolean isHistoryProductVersion(String fid);
	  
	  void ExecDealWithUploadFile (HttpServletRequest request) throws Exception;
	  
	  void ExecDeleteProductAccessorys (HttpServletRequest request) throws Exception;
	int saveExcel(HttpServletRequest request) throws IOException;
	public void ExecAddCommonMaterial(String fids, String fsupplierid,
			String fcustomerid);
}
