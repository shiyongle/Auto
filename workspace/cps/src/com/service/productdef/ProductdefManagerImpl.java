/*
 * CPS-VMI-wangc
 */

package com.service.productdef;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.dao.IBaseDao;
import com.dao.customer.CustomerDao;
import com.dao.custproduct.TBdCustproductDao;
import com.dao.productdef.ProductdefDao;
import com.model.PageModel;
import com.model.custproduct.TBdCustproduct;
import com.model.productdef.CustBoardFormula;
import com.model.productdef.MaterialLimit;
import com.model.productdef.Productdef;
import com.model.supplier.Supplier;
import com.service.IBaseManagerImpl;
import com.util.Params;

@Service("productdefManager")
@Transactional
public class ProductdefManagerImpl extends IBaseManagerImpl<Productdef,java.lang.String> implements ProductdefManager{
	@Autowired
	private ProductdefDao productdefDao;
	@Autowired
	private TBdCustproductDao custProductDao;
	@Autowired
	private CustomerDao customerDao;

	public void setTpdtProductdefDao(ProductdefDao dao) {
		this.productdefDao = dao;
	}
	
	public IBaseDao<Productdef, java.lang.String> getEntityDao() {
		return this.productdefDao;
	}

	@Override
	public PageModel<Productdef> findSupplierCardboardList(int pageNo,int pageSize, String where, Object[] queryParams) {
		return productdefDao.findSupplierCardboardList(pageNo, pageSize, where, queryParams);
	}

	@Override
	public PageModel<Productdef> findCommonMaterialList(int pageNo,int pageSize, String where, Object[] queryParams) {
		return productdefDao.findCommonMaterialList(pageNo, pageSize, where, queryParams);
	}

	@Override
	public MaterialLimit getMaterialLmit(String fcustomerid) {
		return productdefDao.getMaterilaLimitEntry(fcustomerid);
	}

	@Override
	public List<CustBoardFormula> getCustBoardFormula(String fcustomerid) {
		return productdefDao.getCustBoardFormulaEntry(fcustomerid);
	}
	
	@Override
	public PageModel<Supplier> getSupplierListByPage(int pageNo, int pageSize, String where,
			Object[] queryParams) {
		return productdefDao.getBySupplierWithPage(pageNo, pageSize, where, queryParams);
	}
	
	//产品档案列表
	public PageModel<HashMap<String, Object>> findProductlist(String where,
			Object[] queryParams, Map<String, String> orderby, int pageNo,
			int maxResult) {
		return productdefDao.findProductlist(where, queryParams, orderby, pageNo, maxResult);
	}

	@Override
	public int execforbiddenproduct(String fidcls) {
		return this.productdefDao.ExecBySql("update t_bd_custproduct set feffect=0 where fid  "+fidcls);
	}

	//产品档案，保存
	@Override
	public String saveProduct(Productdef obj) {
		TBdCustproduct custObj = null;
		Date now = new Date();
		if(StringUtils.isEmpty(obj.getFid())){
			obj.setFid(this.CreateUUid());
			custObj = new TBdCustproduct();
			custObj.setFid(obj.getFcustpdtid());
			custObj.setFcreatetime(now);
			custObj.setFproductid(obj.getFid());
		}else{
//			String hql = "from TBdCustproduct where fproductid = '"+obj.getFid()+"'";
//			List<TBdCustproduct> list = custProductDao.findByHql(hql);
			String hql = "from TBdCustproduct where fproductid = ?";
			List<TBdCustproduct> list = custProductDao.findByHql(hql,new Object[]{obj.getFid()});
			if(list.size()==0){
				return "产品未关联客户产品！";
			}else{
				custObj = list.get(0);
			}
		}
		custObj.setFname(obj.getFname());
		custObj.setFcustomerid(obj.getFcustomerid());
		custObj.setFmaterial(obj.getFmaterialcode());
		custObj.setFdescription(obj.getFdescription());
		custObj.setFspec(obj.getFcharacter());
		custObj.setFprice(obj.getFprice());
		custObj.setFlastupdatetime(now);
		obj.setFeffect(1);
		obj.setFlastupdatetime(now);
		this.saveOrUpdate(custObj);
		this.saveOrUpdate(obj);
		return null;
		//改版面图,此功能目前没用，等日后完善
		/*String ffileid = obj.getFfileid();
		if(!StringUtils.isEmpty(ffileid)){
			String sql = "update t_ord_productdemandfile set ftype='版面图' where fid= '"+ffileid+"'";
			if(this.ExecBySql(sql)>0){
				sql = "update t_ord_productdemandfile set ftype='' where fparentid= '"+custObj.getFid()+"' and fid != '"+ffileid+"'";
				this.ExecBySql(sql);
			}
		}*/
	}

	@Override
	public HashMap<String, Object> getProductInfo(String id) {
		Params p=new Params();
		StringBuilder sql = new StringBuilder();
		sql.append("select f.fid ffileid,f.fname ffilename,pdt.fid,cp.fid fcustpdtid,pdt.fprice,pdt.fname,pdt.fcharacter,pdt.fboxmodelid,pdt.flayer,pdt.fdescription,pdt.fboxlength,pdt.fboxwidth,pdt.fboxheight,");
		sql.append("  pdt.fboardlength,pdt.fboardwidth,pdt.ftilemodelid,pdt.fstavetype,pdt.fseries,pdt.fhstaveexp,pdt.fhformula,");
		sql.append("  pdt.fhformula0,pdt.fhformula1,pdt.fvstaveexp,pdt.fvformula,pdt.fvformula0,pdt.fdefine1,pdt.fdefine2,pdt.fmtsupplierid,");
		sql.append("  pdt.ftmodelspec,pdt.fcbnumber,pdt.fybnumber,pdt.fpackway,pdt.fpinamount,pdt.fcblocation,pdt.fpaperspec,pdt.ffacespec,pdt.ffylinamount,pdt.fisboardcard,pdt.fmaterialcode,pdt.fmaterialinfo,");
		sql.append("  pdt.fdefine3,pdt.fprintcolor,pdt.fworkproc,pdt.fcustomerid,pdt.fdescription1,pdt.fdescription2,pdt.fmaterialfid fmaterialfid_fid,pdt.fqueryname fmaterialfid_fqueryname,pdt.fqueryname fqueryname");
		sql.append(" from  t_bd_custproduct cp left join t_pdt_productdef pdt on cp.fproductid = pdt.fid left join t_ord_productdemandfile f on f.fid=pdt.ffileid");
//		sql.append(" where cp.fid ='"+id+"'");
		sql.append(" where cp.fid =:id");
		p.put("id", id);
		List<HashMap<String,Object>> result= productdefDao.QueryBySql(sql.toString(),p);
		return result.size()>0 ? result.get(0) : null;
	}
	@Override
	public String getCurrentSupplierid(String fuserid) {
		// TODO Auto-generated method stub
//		String sql="select fsupplierid from ( select fsupplierid  from t_bd_usersupplier where fuserid='"+fuserid+"'"+
//				" union select c.fsupplierid from  t_sys_userrole r "+
//				" left join t_bd_rolesupplier c on r.froleid=c.froleid where r.fuserid='"+fuserid+"' )  s where fsupplierid is not null " ;
//		List<HashMap<String, Object>> list = this.productdefDao.QueryBySql(sql);
		Params p=new Params();
		String sql="select fsupplierid from ( select fsupplierid  from t_bd_usersupplier where fuserid=:fuid"+
				" union select c.fsupplierid from  t_sys_userrole r "+
				" left join t_bd_rolesupplier c on r.froleid=c.froleid where r.fuserid=:fuid )  s where fsupplierid is not null " ;
		p.put("fuid", fuserid);
		List<HashMap<String, Object>> list = this.productdefDao.QueryBySql(sql,p);
		if(list.size()==1){
			return list.get(0).get("fsupplierid").toString();
		}
		return null;
	}
	
}
