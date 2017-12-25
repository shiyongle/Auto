package com.dao.productdemandfile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.dao.IBaseDaoImpl;
import com.model.productdemandfile.Productdemandfile;
import com.util.ImageUtil;
import com.util.Params;

@SuppressWarnings("unchecked")
@Repository("productdemandfileDao")
public class ProductdemandfileDaoImpl extends IBaseDaoImpl<Productdemandfile, java.lang.Integer> implements ProductdemandfileDao{

	
	/**
	 * 获取所有附件
	 */
	@Override
	public List<Productdemandfile> getByParentId(String fid) {
		return getByParentId(fid,false);
	}

	/**
	 * 获取所有图片附件
	 */
	@Override
	public List<Productdemandfile> getByParentId(String fid,Boolean isImage) {
		String hql = "from Productdemandfile where fparentid= ? order by fcreatetime desc"; 
		Query query = this.getSessionFactory().getCurrentSession().createQuery(hql);  
	    query.setString(0, fid);  
	    List<Productdemandfile> list = query.list(); 
	    if(isImage){
	    	list = extractImageList(list);
	    }
		return list;
	}
	/**
	 * 提取是图片的文件列表
	 * @param list
	 * @return
	 */
	private List<Productdemandfile> extractImageList(List<Productdemandfile> list) {
		List<Productdemandfile> result = new ArrayList<>();
		for(Productdemandfile file: list){
			int lastIndex =file.getFname().lastIndexOf(".");
			if(lastIndex >-1){
				String filetype = file.getFname().substring(lastIndex+1);
				if(ImageUtil.isImage(filetype)){
					result.add(file);
				}
			}
		}
		return result;
	}
	
	/*** 通过schemeId对象获取文件*/
	@Override
	public List getBySchemeId(String fid) {
		String sql = "select fid,fdescription,fpath from t_ord_schemefiles where fparentid=:fid";
		Params params = new Params();
		params.setString("fid", fid);
		List<HashMap<String,String>> list = this.QueryBySql(sql, params);
		return list;
	}	
	
	/*** 通过imgId对象获取文件*/
	@Override
	public List getByImageId(String fid) {
		String sql = "select fid,fdescription,fpath from t_ord_schemefiles where fid=:fid";
		Params params = new Params();
		params.setString("fid", fid);
		List<HashMap<String,String>> list = this.QueryBySql(sql, params);
		return list;
	}	
}
