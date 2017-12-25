package Com.Dao.System;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;

import Com.Base.Dao.IBaseDao;
import Com.Entity.System.Custproduct;
import Com.Entity.System.Mainmenuitem;
import Com.Entity.System.Useronline;

public interface ICustproductDao extends IBaseDao {
	public Custproduct Query(String fid);
    public  List QuerySysUsercls(String hql);
    public HashMap<String,Object> ExecSave(Custproduct info);
    public Useronline ExecCheckLogin(HttpServletRequest request);
    public boolean ExecCustrelation(JSONArray jsa);
    public void ExecdeteleString(String entrysql,String sql,String fparentsql);
    public void ExecAddUserRelationCust(String userid, String[] tArrayofFid);
    public void ExecDeleteUserRelationCust(String userid, String fidcls);
    
    public String ExecBuildCusProductChilrenJson(HttpServletRequest request) throws UnsupportedEncodingException;
    
    public String ExecSelectCreateAndUpdateCountForColumn(HttpServletRequest request);
    public void ExecNumberofSQL(List list);
    
    /**
     * 新建对应产品及其关联关系
     *
     * @param request
     * @throws UnsupportedEncodingException
     *
     * @date 2014-11-7 下午2:15:13  (ZJZ)
     */
    void ExecCreateCorrespondingProductRel(HttpServletRequest request, Custproduct custproduct) throws UnsupportedEncodingException;
	public void DelCustProduct(String fidcls);
	
	void ExecUploadCustProductImg(HttpServletRequest request) throws Exception;
	
	void ExecDeleteCustProductAccessorys(HttpServletRequest request) throws Exception;
	
	void ExecCustpEnabled(String fids);
	
	void ExecCustpDisable(String fids);
	
	public String ExecDelFile(String id);
	
}
