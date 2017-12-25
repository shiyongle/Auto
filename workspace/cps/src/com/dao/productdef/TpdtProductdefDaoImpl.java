package com.dao.productdef;

import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.dao.IBaseDaoImpl;
import com.model.PageModel;
import com.model.custproduct.Custrelation;
import com.model.custproduct.Custrelationentry;
import com.model.custproduct.TBdCustproduct;
import com.model.productdef.CustBoardFormula;
import com.model.productdef.MaterialLimit;
import com.model.productdef.Productdef;
import com.model.supplier.Supplier;
import com.util.Constant;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;
/*
 * CPS-VMI-wangc
 */
@SuppressWarnings("unchecked")
@Repository("productdefDao")
public class TpdtProductdefDaoImpl extends IBaseDaoImpl<Productdef,java.lang.String> implements ProductdefDao{

	@Override
	public PageModel<Productdef> findSupplierCardboardList(final int pageNo,final int pageSize, final String where, final Object[] queryParams) {
		final PageModel<Productdef> pageModel = new PageModel<Productdef>();
		pageModel.setPageNo(pageNo);
		pageModel.setPageSize(pageSize);
		this.getHibernateTemplate().execute(new HibernateCallback() {
			@Override
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				StringBuilder sql = new StringBuilder().append(" select e.fid,e.fname,e.fnumber,e.flayer,e.ftilemodelid,e.fqueryname,e.fid fmaterialid from t_pdt_productdef e ")
						.append(" left join (select fid,fmaterialid from t_sys_commonmaterial where fcustomerid = ? ) e1 on e.fid=e1.fmaterialid ")
						.append(" where e.fboxtype = 1 and e.feffect=1 and e1.fid is null ")
						.append(where == null ? "" : where);
				Query query = session.createSQLQuery(sql.toString())
									.addScalar("fid")
									.addScalar("fname")
									.addScalar("fnumber")
									.addScalar("flayer")
									.addScalar("ftilemodelid")
									.addScalar("fqueryname")
									.addScalar("fmaterialid");
				setQueryParams2(query,queryParams);
				query.setResultTransformer(Transformers.aliasToBean(Productdef.class));
				List<Productdef> list = query.setFirstResult(getFirstResult(pageNo, pageSize)).setMaxResults(pageSize).list();
							 sql = new StringBuilder().append(" select count(1) from t_pdt_productdef e ")
										.append(" left join (select fid,fmaterialid from t_sys_commonmaterial where fcustomerid = ? ) e1 on e.fid=e1.fmaterialid ")
										.append(" where e.fboxtype = 1 and e.feffect=1 and e1.fid is null ")
										.append(where == null ? "" : where);
			   query = session.createSQLQuery(sql.toString());
			   setQueryParams2(query,queryParams);
			   pageModel.setTotalRecords(Integer.valueOf(query.uniqueResult().toString()));
			   pageModel.setList(list);
			   return null;
			}
		});
		return pageModel;
	}

	@Override
	public PageModel<Productdef> findCommonMaterialList(final int pageNo,final int pageSize, final String where, final Object[] queryParams)  {
		final PageModel<Productdef> pageModel = new PageModel<Productdef>();
		pageModel.setPageNo(pageNo);
		pageModel.setPageSize(pageSize);
		this.getHibernateTemplate().execute(new HibernateCallback() {
			@Override
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				StringBuilder sql = new StringBuilder().append(" select e1.fid,e.fname,e.fnumber,e.flayer,e.ftilemodelid,e.fqueryname,e1.fmaterialid from t_pdt_productdef e ")
						.append(" inner join t_sys_commonmaterial e1 on e.fid=e1.fmaterialid where e.fboxtype = 1 and e.feffect=1 ")
						.append(where == null ? "" : where);
				Query query = session.createSQLQuery(sql.toString())
									.addScalar("fid")
									.addScalar("fname")
									.addScalar("fnumber")
									.addScalar("flayer")
									.addScalar("ftilemodelid")
									.addScalar("fqueryname")
									.addScalar("fmaterialid");
				setQueryParams2(query,queryParams);
				query.setResultTransformer(Transformers.aliasToBean(Productdef.class));
				List<Productdef> list = query.setFirstResult(getFirstResult(pageNo, pageSize)).setMaxResults(pageSize).list();
							 sql = new StringBuilder().append(" select count(1) from t_pdt_productdef e ")
										.append(" inner join t_sys_commonmaterial e1 on e.fid=e1.fmaterialid where e.fboxtype = 1 and e.feffect=1 ")
										.append(where == null ? "" : where);
			   query = session.createSQLQuery(sql.toString());
			   setQueryParams2(query,queryParams);
			   pageModel.setTotalRecords(Integer.valueOf(query.uniqueResult().toString()));
			   pageModel.setList(list);
			   return null;
			}
		});
		return pageModel;
	}

	@Override
	public MaterialLimit getMaterilaLimitEntry(String fcustomerid) {
//		List<MaterialLimit> material=this.QueryByHql(String.format("from MaterialLimit where fcustomerid ='%s'",fcustomerid));
		List<MaterialLimit> material=this.QueryByHql("from MaterialLimit where fcustomerid =?",new Object[]{fcustomerid});
		return material.size()>0?material.get(0):null;
	}

	@Override
	public List<CustBoardFormula> getCustBoardFormulaEntry(String fcustomerid) {
		// TODO Auto-generated method stub
//		List<CustBoardFormula> formula=this.QueryByHql(String.format("from CustBoardFormula where fcustomerid ='%s'",fcustomerid));
		List<CustBoardFormula> formula=this.QueryByHql("from CustBoardFormula where fcustomerid =?",new Object[]{fcustomerid});
		return formula;
	}
	
	
	@Override
	public PageModel<Supplier> getBySupplierWithPage(final int pageNo,final int pageSize, final String where, final Object[] queryParams) {
		final PageModel<Supplier> pageModel = new PageModel<Supplier>();
		pageModel.setPageNo(pageNo);
		pageModel.setPageSize(pageSize);
		List<Supplier> supplier =(List<Supplier>) this.getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(final Session session) throws HibernateException, SQLException {
				 StringBuilder sql = new StringBuilder().append(" select ")
									.append(" p.FID   as fid,")
									.append(" p.FNAME as fname")
									.append(" from  t_pdt_productreqallocationrules t ")
									.append(" INNER JOIN t_sys_supplier p on t.fsupplierid =p.fid")
									.append(" LEFT JOIN t_bd_usercustomer u on u.FCUSTOMERID =t.fcustomerid")
									.append(" LEFT JOIN t_sys_user s on s.FID = u.FUSERID")
									.append(where == null ? "" : where);
				 Query query = session.createSQLQuery(sql.toString())
									.addScalar("fid")
									.addScalar("fname");
				query.setResultTransformer(Transformers.aliasToBean(Supplier.class));
				setQueryParams2(query,queryParams);
				List<Supplier> list = query.setFirstResult(getFirstResult(pageNo, pageSize)).setMaxResults(pageSize).list();
				 sql = new StringBuilder().append(" select count(1) from  t_pdt_productreqallocationrules t ")
						 .append(" INNER JOIN t_sys_supplier p on t.fsupplierid =p.fid")
						 .append(" LEFT JOIN t_bd_usercustomer u on u.FCUSTOMERID =t.fcustomerid")
						.append(" LEFT JOIN t_sys_user s on s.FID = u.FUSERID")
							.append(where == null ? "" : where);
				query = session.createSQLQuery(sql.toString());
				setQueryParams2(query,queryParams);
				pageModel.setTotalRecords(Integer.valueOf(query.uniqueResult().toString()));
				pageModel.setList(list);
				return null;
			}
		});
		return pageModel;
	}
	
	
	
	/******************产品档案列表界面*************************/
	@Override
	public PageModel<HashMap<String, Object>> findProductlist(final String where,final Object[] queryParams,final  Map<String, String> orderby, final int pageNo,final int maxResult) {
		final PageModel<HashMap<String, Object>> pageModel = new PageModel<HashMap<String, Object>>();
		pageModel.setPageNo(pageNo);
		pageModel.setPageSize(maxResult);
		this.getHibernateTemplate().execute(new HibernateCallback() {
			@Override
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				StringBuilder sql = new StringBuilder().append(" select ")
									.append(" c.fid,  ")
									.append(" f.fid productid,")
									.append(" DATE_FORMAT(IFNULL(c.fcreatetime, f.fcreatetime),'%Y-%m-%d') fcreatetime, ")
									.append(" c.fname productName,")
									.append(" m.fname customerName,")
									.append("  c.fmaterial,")
									.append(" ifnull(c.fspec,'') productSpec,")
									.append(" ifnull(c.fdescription,'') as fdescription")
									.append(" FROM t_bd_custproduct c ")
									.append(" inner JOIN t_pdt_productdef f ON f.fid  = c.fproductid ")
									.append(" LEFT JOIN t_bd_customer m  ON m.fid = c.fcustomerid")
									.append(where == null ? "" : where)
									.append(createOrderBy(orderby));
				Query query = session.createSQLQuery(sql.toString());
				setQueryParams2(query,queryParams);
				query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				List<HashMap<String, Object>> list = query.setFirstResult(getFirstResult(pageNo, maxResult)).setMaxResults(maxResult).list();
							 sql = new StringBuilder().append("select count(*) ")
										.append(" FROM t_bd_custproduct c ")
										.append(" inner JOIN t_pdt_productdef f ON f.fid  = c.fproductid ")
										.append(" LEFT JOIN t_bd_customer m  ON m.fid = c.fcustomerid")
							         .append(where == null ? "" : where);
			   query = session.createSQLQuery(sql.toString());
			   setQueryParams2(query,queryParams);
			   pageModel.setTotalRecords(Integer.valueOf(query.uniqueResult().toString()));
			   pageModel.setList(list);
			   return null;
			}
		});
		return pageModel;
	}
	
	/**
	 * 导入生成产品
	 */
	public boolean saveListToTable(String fcustomerId,List<String> list,String userid){
		try {
			String sql1 = "select * from cusproduct_treegrid_view where fname='"+list.get(0)+"' and fcustomerid='"+fcustomerId+"'";
			List result1 =this.QueryBySql(sql1);
			String sql2 = "select d.fid  FROM t_pdt_productdef d left join t_sys_user u1 on  u1.fid=d.fcreatorid left join t_sys_user u2 on  u2.fid=d.flastupdateuserid where d.fname='"+list.get(0)+"' and d.fcustomerid='"+fcustomerId+"'";
			List result2 =this.QueryBySql(sql2);
			if(result1.size()==0 && result2.size()==0){
				TBdCustproduct info = new TBdCustproduct();
				info.setFid(CreateUUid());
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
				String sql="select fsupplierid from ( select fsupplierid  from t_bd_usersupplier where fuserid='"+userid+"'"+
						" union select c.fsupplierid from  t_sys_userrole r "+
						" left join t_bd_rolesupplier c on r.froleid=c.froleid where r.fuserid='"+userid+"' ) s where fsupplierid is not null " ;
				List<HashMap<String,Object>> suplist=this.QueryBySql(sql);
				if(suplist.size()==0){
					throw new Exception("当前账号没有制造商！");
				}
				// 新建产品
				Productdef product = saveProductForExcel(info,userid,(String)suplist.get(0).get("fsupplierid"));
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
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	private Productdef saveProductForExcel(TBdCustproduct custproduct,String userid,String fsupplierid){
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
		product.setFsupplierid(fsupplierid);
		saveOrUpdate(product);
		return product;
	}

}
