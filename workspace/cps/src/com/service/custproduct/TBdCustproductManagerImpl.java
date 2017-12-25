package com.service.custproduct;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dao.IBaseDao;
import com.dao.custproduct.TBdCustproductDao;
import com.model.PageModel;
import com.model.custproduct.TBdCustproduct;
import com.model.supplier.Supplier;
import com.service.IBaseManagerImpl;
import com.util.Constant;
import com.util.Params;
@Service("custproductManager")
@Transactional
public class TBdCustproductManagerImpl extends IBaseManagerImpl<TBdCustproduct, java.lang.String> implements TBdCustproductManager {

	@Autowired
	private TBdCustproductDao custproductDao;
	
	@Override
	protected IBaseDao<TBdCustproduct, java.lang.String> getEntityDao() {
		return this.custproductDao;
	}

	@Override
	public PageModel<TBdCustproduct> findBySql(String where,Object[] queryParams, Map<String, String> orderby, int pageNo,int maxResult) {
		return this.custproductDao.findBySql(where, queryParams, orderby, pageNo, maxResult);
	}
	/*** 删除*/
	@Override
	public void deleteImpl(String fid) {
		this.custproductDao.delete("t_bd_custproduct", fid);
	}
	
	/*** 通过用户id,客户产品id返回客户产品对象*/
	@Override
	public List<TBdCustproduct> getById(String userId,String cusproductId ){
		return  this.custproductDao.getById(userId,cusproductId);
	}
	
	/*** 获取供应商*/
	@Override
	public List<Supplier> getBySupplier(String userId){
		return  this.custproductDao.getBySupplier(userId);
	}
	
	/*** 根据需求ID查询所有客户产品列表及附件*/
	@Override
	public List<HashMap<String, HashMap<String, Object>>> getProductWithfp( String id) {
		List<HashMap<String, HashMap<String, Object>>> result = new ArrayList<>();
		List<HashMap<String, Object>> productList;
		List<HashMap<String, Object>> fileList;
		
		HashMap map;
		String cpid = "";
		Params params=new Params();
		String sql = "select  fid,fname,fmaterialcodeid,concat_ws('*',fboxlength,fboxwidth,fboxheight) fspec,subProductAmount  from t_ord_productstructure where schemedesignid =:id";
		params.put("id", id);
		productList = this.custproductDao.QueryBySql(sql,params);
			if(productList.size()>0){
				map = new HashMap<>();
				if(productList.get(0).get("fid")!=null && !productList.get(0).get("fid").equals("")){
					cpid = productList.get(0).get("fid").toString();
				}
				map.put("productfname",productList.get(0).get("fname"));
				map.put("fmaterialcode",productList.get(0).get("fmaterialcodeid"));
				map.put("fspec",productList.get(0).get("fspec"));
				map.put("subProductAmount",productList.get(0).get("subProductAmount"));
				params=new Params();
				params.put("fpid", cpid);
				 sql="select fid,fname,fpath from t_ord_productdemandfile where fparentid=:fpid";
					fileList = this.custproductDao.QueryBySql(sql,params);
					map.put("files", fileList);
					result.add(map);
			}
			if(!cpid.equals("")){
				params=new Params();
				params.put("fpaid", cpid);
				sql = "select  fid,fname,fmaterialcodeid,concat_ws('*',fboxlength,fboxwidth,fboxheight) fspec,subProductAmount  from t_ord_productstructure where fparentid = :fpaid";
				productList = this.custproductDao.QueryBySql(sql,params);
					for(int i=0;i<productList.size();i++){
						map = new HashMap<>();
						if(productList.get(i).get("fid")!=null && !productList.get(i).get("fid").equals("")){
							cpid = productList.get(i).get("fid").toString();
						}
						map.put("productfname",productList.get(i).get("fname"));
						map.put("fmaterialcode",productList.get(i).get("fmaterialcodeid"));
						map.put("fspec",productList.get(i).get("fspec"));
						map.put("subProductAmount",productList.get(i).get("subProductAmount"));
						sql="select fid,fname,fpath from t_ord_productdemandfile where fparentid=:cpid";
						params=new Params();
						params.put("cpid", cpid);	
						fileList = this.custproductDao.QueryBySql(sql,params);
						map.put("files", fileList);
						result.add(map);
					}
			}
	return result;
}

	@Override
	public List<HashMap<String, Object>> getCustproductStock(
			String fid,String supplierid) {
		return this.custproductDao.getCustproductStock(fid,supplierid);
	}

}
