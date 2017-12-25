package com.dao.schemedesign;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;

import com.dao.IBaseDaoImpl;
import com.dao.system.SimplemessageDao;
import com.model.PageModel;
import com.model.custproduct.TBdCustproduct;
import com.model.deliverapply.Deliverapply;
import com.model.firstproductdemand.Firstproductdemand;
import com.model.productdef.Custproductproducts;
import com.model.productdef.Productdef;
import com.model.productdef.ProductdefProducts;
import com.model.productdef.Productrelation;
import com.model.productdef.Productrelationentry;
import com.model.productdemandfile.Productdemandfile;
import com.model.productplan.ProductPlan;
import com.model.schemedesign.Productstructure;
import com.model.schemedesign.Saleorder;
import com.model.schemedesign.SchemeDesignEntry;
import com.model.schemedesign.Schemedesign;
import com.model.storebalance.Storebalance;
import com.model.supplier.Supplier;
import com.util.ServerContext;

/*
 * CPS-VMI-wangc
 */

@SuppressWarnings("unchecked")
@Repository("schemedesignDao")
public class SchemedesignDaoImpl extends IBaseDaoImpl<Schemedesign,java.lang.String> implements SchemedesignDao{
	@Autowired
	SimplemessageDao simplemessageDao;
	@Override
	public PageModel<Schemedesign> findBySql(final String where,final Object[] queryParams, final Map<String, String> orderby, final int pageNo,final int maxResult) {
		final PageModel<Schemedesign> pageModel = new PageModel<Schemedesign>();
		pageModel.setPageNo(pageNo);
		pageModel.setPageSize(maxResult);
		this.getHibernateTemplate().execute(new HibernateCallback() {
			@Override
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				StringBuilder sql = new StringBuilder().append(" select ")
									.append(" u1.fqq                as fqq,")
									.append(" u3.fname              as fauditorid,")
									.append(" s.faudited            as faudited,")
									.append(" sc.fid                as sfid,")
									.append(" u1.ftype              as ftype,")
									.append(" s.fid                 as fid,")
									.append(" s.fcreatorid          as fcreatorid,")
									.append(" u1.fname              AS fcreator,")
									.append(" ifnull(date_format(s.fcreatetime,'%Y-%m-%d'), '')   AS crateTime_ymr,")
									.append(" ifnull(date_format(s.fcreatetime,'%H:%i:%s'), '')   AS crateTime_sfm,")
									.append(" s.fname               as fname,")
									.append(" s.fnumber             AS fnumber,")
									.append(" s.fdescription        AS fdescription,")
									.append(" s.ffirstproductid     AS ffirstproductid,")
									.append(" s.fcustomerid         as fcustomerid,")
									.append(" c.fname               as customerName,")
									.append(" s.fsupplierid         as fsupplierid,")
									.append(" sp.fname              as supplierName,")
									.append(" s.fconfirmed          as fconfirmed,")
									.append(" u2.fname              as fconfirmer,")
									.append(" ifnull(date_format(s.fconfirmtime,'%Y-%m-%d'), '')   AS confirmTime_ymr,")
									.append(" ifnull(date_format(s.fconfirmtime,'%H:%i:%s'), '')   AS confirmTime_sfm,")
									.append(" s.fgroupid            as fgroupid")
									.append(" FROM t_ord_schemedesign s ")
									.append(" LEFT JOIN t_sys_user u1 ON u1.fid = s.fcreatorid ")
									.append(" LEFT JOIN ( SELECT fid, fparentid FROM t_ord_schemedesignentry GROUP BY fparentid ) sc ON s.fid = sc.fparentid ")
									.append(" LEFT JOIN t_sys_user u2 ON u2.fid = s.fconfirmer ")
									.append(" LEFT JOIN t_sys_user u3 ON u3.fid = s.fauditorid ")
									.append(" LEFT JOIN t_bd_customer c ON c.fid = s.fcustomerid ")
									.append(" LEFT JOIN t_sys_supplier sp ON sp.fid = s.fsupplierid ")
									.append(where == null ? "" : where)
									.append(createOrderBy(orderby));
				Query query = session.createSQLQuery(sql.toString())
									.addScalar("fqq")
									.addScalar("fauditorid")
									.addScalar("faudited")
									.addScalar("sfid")
									.addScalar("ftype")
									.addScalar("fid")
									.addScalar("fcreatorid")
									.addScalar("fcreator")
									.addScalar("crateTime_ymr")
									.addScalar("crateTime_sfm")
									.addScalar("fname")
									.addScalar("fnumber")
									.addScalar("fdescription")
									.addScalar("ffirstproductid")
									.addScalar("fcustomerid")
									.addScalar("customerName")
									.addScalar("fsupplierid")
									.addScalar("supplierName")
									.addScalar("fconfirmed")
									.addScalar("fconfirmer")
									.addScalar("confirmTime_ymr")
									.addScalar("confirmTime_sfm")
									.addScalar("fgroupid");
				setQueryParams2(query,queryParams);
				query.setResultTransformer(Transformers.aliasToBean(Schemedesign.class));
				List<Schemedesign> list = query.setFirstResult(getFirstResult(pageNo, maxResult)).setMaxResults(maxResult).list();
							 sql = new StringBuilder().append("select count(*)")
									 .append(" FROM t_ord_schemedesign s ")
									 .append(" LEFT JOIN t_sys_user u1 ON u1.fid = s.fcreatorid ")
									 .append(" LEFT JOIN ( SELECT fid, fparentid FROM t_ord_schemedesignentry GROUP BY fparentid ) sc ON s.fid = sc.fparentid ")
									 .append(" LEFT JOIN t_sys_user u2 ON u2.fid = s.fconfirmer ")
									 .append(" LEFT JOIN t_sys_user u3 ON u3.fid = s.fauditorid ")
									 .append(" LEFT JOIN t_bd_customer c ON c.fid = s.fcustomerid ")
									 .append(" LEFT JOIN t_sys_supplier sp ON sp.fid = s.fsupplierid ")
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
	public int updateDesigner(String fids,String designerid) {
		return this.ExecBySql(String.format("update t_ord_firstproductdemand set freceiver='%s', freceivetime=now(), freceived=1, fstate='已接收' where fid ='%s'",designerid,fids));
	}

	@Override
	public PageModel<HashMap<String, Object>> findByFirstdemand(final String where,
			final Object[] queryParams,final  Map<String, String> orderby, final int pageNo,
			final int maxResult,final String...t_name) {
		final PageModel<HashMap<String, Object>> pageModel = new PageModel<HashMap<String, Object>>();
		pageModel.setPageNo(pageNo);
		pageModel.setPageSize(maxResult);
		this.getHibernateTemplate().execute(new HibernateCallback() {
			@Override
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				//2016-3-22 lxx
				String tablename="t_ord_firstproductdemand";
				if(t_name.length!=0&&t_name[0]!=null)
				{
					System.out.println("三个月前数据的表名更改为:"+t_name[0]);
					tablename=t_name[0];
				}
				StringBuilder sql = new StringBuilder().append("select ")
									.append(" u1.fqq, ")
									.append(" f.fstate,")
									.append(" f.fid,")
									.append("f.fname,")
									.append("c.fname cname,")
									.append("f.fnumber,")
/*									.append("s.count,")
*/									.append(" IFNULL(f.fdescription, '') fdescription,")
									.append(" date_format(f.fauditortime,'%Y-%m-%d %H:%i') fauditortime,")
									.append(" ifnull(f.flinkman,'') flinkman,")
									.append(" ifnull(f.flinkphone,'') flinkphone,")
									.append(" u2.fname AS freceiver,")
									.append(" ifnull(f.fisdemandpackage,0)  fisdemandpackage")
									.append(" FROM "+tablename+" f")
									.append(" LEFT JOIN t_sys_user u1 ON u1.fid=f.fcreatid ")
									.append(" LEFT JOIN t_bd_customer c ON f.fcustomerid = c.fid ")
									.append(" LEFT JOIN t_sys_user u2 ON u2.fid=f.freceiver ")
/*									.append("left join (select count(1) count,sum(fconfirmed),ffirstproductid from t_ord_schemedesign group by ffirstproductid having count(1)=sum(fconfirmed)) s on f.fid=s.ffirstproductid ")
*/									.append(where == null ? "" : where)
									.append(createOrderBy(orderby));
				Query query = session.createSQLQuery(sql.toString());
				setQueryParams2(query,queryParams);
				query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				if(maxResult>0){
				List<HashMap<String,Object>> list = query.setFirstResult(getFirstResult(pageNo, maxResult)).setMaxResults(maxResult).list();
							 sql = new StringBuilder().append("select count(*)")
										.append(" FROM "+tablename+" f")
										.append(" LEFT JOIN t_sys_user u1 ON u1.fid=f.fcreatid ")
										.append(" LEFT JOIN t_bd_customer c ON f.fcustomerid = c.fid ")
										.append(" LEFT JOIN t_sys_user u2 ON u2.fid=f.freceiver ")
	/*									.append("left join (select count(1) count,sum(fconfirmed),ffirstproductid from t_ord_schemedesign group by ffirstproductid having count(1)=sum(fconfirmed)) s on f.fid=s.ffirstproductid ")
	*/									.append(where == null ? "" : where);
			   query = session.createSQLQuery(sql.toString());
			   setQueryParams2(query,queryParams);
			   pageModel.setTotalRecords(Integer.valueOf(query.uniqueResult().toString()));
			   pageModel.setList(list);
				}else//maxResult 小于 不分页查询 不查总数
				{
					List<HashMap<String,Object>> list = query.list();
					pageModel.setList(list);
				}
			   return null;
			}
		});
		return pageModel;
	}

	@Override
	public PageModel<HashMap<String, Object>> findBySchemedesign(final String where,
			final Object[] queryParams,final  Map<String, String> orderby, final int pageNo,
			final int maxResult,final boolean...t_name) {
		final PageModel<HashMap<String, Object>> pageModel = new PageModel<HashMap<String, Object>>();
		pageModel.setPageNo(pageNo);
		pageModel.setPageSize(maxResult);
		this.getHibernateTemplate().execute(new HibernateCallback() {
			
			@Override
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				String tablename="t_ord_schemedesign";
				if(t_name.length!=0&t_name[0])
				{
					tablename+="_h";
				}
				StringBuilder sql = new StringBuilder().append("select ")
									.append(" uu.fqq, ")
									.append(" c.fname fcustomer,")
									.append(" s.fid,")
									.append("s.fname,")
									.append("s.fnumber,")
									.append("sc.fid entryfid ,")//有特性id
									.append("sp.fname fdesginsupplier,")
									.append("u1.fname fcreator,")
									.append(" date_format(s.fcreatetime,'%Y-%m-%d %H:%i') fcreatetime,")
									.append("u3.fname  fauditor,")
									.append("ifnull(s.faudited,0)  faudited,")
									.append("s.fconfirmed,")
									.append(" u2.fname fconfirmer,")
									.append(" date_format(s.fconfirmtime,'%Y-%m-%d %H:%i') fconfirmtime ")
									.append("  FROM "+tablename+" s")
									.append(" LEFT JOIN t_sys_user u1 ON u1.fid=s.fcreatorid ")
									.append(" LEFT JOIN (SELECT fid,fparentid FROM t_ord_schemedesignentry GROUP BY fparentid) sc  ON s.fid=sc.fparentid ")
									.append(" left join t_sys_user u3 on s.fauditorid=u3.fid ")
									.append(" LEFT JOIN t_bd_customer c ON c.fid=s.fcustomerid ")
									.append(" LEFT JOIN t_sys_supplier sp ON sp.fid=s.fsupplierid ")
									.append(" left join t_sys_user u2 on u2.fid = s.fconfirmer  ")
									.append(" left join t_ord_firstproductdemand f on s.ffirstproductid=f.fid ")
									.append(" left join t_sys_user uu on uu.fid=f.fcreatid ")
									.append(where == null ? "" : where)
									.append(createOrderBy(orderby));
				Query query = session.createSQLQuery(sql.toString());
				setQueryParams2(query,queryParams);
				query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				List<HashMap<String,Object>> list = query.setFirstResult(getFirstResult(pageNo, maxResult)).setMaxResults(maxResult).list();
							 sql = new StringBuilder().append("select count(*)")
									 .append("  FROM t_ord_schemedesign s")
										/*.append(" LEFT JOIN t_sys_user u1 ON u1.fid=s.fcreatorid ")*/
	/*									.append(" LEFT JOIN (SELECT fid,fparentid FROM t_ord_schemedesignentry GROUP BY fparentid) sc  ON s.fid=sc.fparentid ")
	*/								/*	.append(" left join t_sys_user u3 on s.fauditorid=u3.fid ")
										.append(" LEFT JOIN t_bd_customer c ON c.fid=s.fcustomerid ")
										.append(" LEFT JOIN t_sys_supplier sp ON sp.fid=s.fsupplierid ")
										.append(" left join t_sys_user u2 on u2.fid = s.fconfirmer  ")*/
										.append(" left join t_ord_firstproductdemand f on s.ffirstproductid=f.fid ")
										/*.append(" left join t_sys_user uu on uu.fid=f.fcreatid ")*/
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
	public String schemeSave(Schemedesign scinfo,List<SchemeDesignEntry> scentryList, List<Productstructure> pdtlist) {
		String sql = "delete from t_ord_schemedesignentry where fparentid = '"+scinfo.getFid()+"'";
		if(scentryList!=null && scentryList.size()>0){		
			this.ExecBySql(sql);
			int amount = 0;
			for(SchemeDesignEntry e : scentryList){
				if(e.getFid()==null||"".equals(e.getFid())){
					e.setFid(this.CreateUUid());
				}
				e.setFparentid(scinfo.getFid());
				this.saveOrUpdate(e);
				amount += e.getFentryamount();
			}
			//保存方案
			if(amount>0){
				scinfo.setFamount(amount);
			}
		}else//循环产品自动审核
		{
			if(scinfo.getFaudited()==null||scinfo.getFaudited()==0){
			scinfo.setFaudited(1);
			scinfo.setFaudittime(new Date());
			scinfo.setFauditorid("3c3c9f29-64b9-11e4-bdb9-00ff6b42e1e5");
			}
		}
		if(scinfo.getFcreatetime()==null)scinfo.setFcreatetime(new Date());
		this.saveOrUpdate(scinfo);
		//增加产品和客户产品以及它们的关联
		Productstructure p = null;
		sql = "SELECT fid FROM t_ord_productstructure where schemedesignid = '"+scinfo.getFid()+"'";
		//先清空此方案所有产品
		List<HashMap<String, Object>> list = this.QueryBySql(sql);
		if(list.size()!=0){
			sql = "SELECT fid FROM t_ord_productstructure  where 1=1 AND fparentid='"+list.get(0).get("fid")+"'";
			List<HashMap<String, Object>> subProducts = this.QueryBySql(sql);
			list.addAll(subProducts);
		}
		for(int j = 0;j<list.size();j++){
			this.ExecBySql("delete from t_ord_productstructure where fid ='"+list.get(j).get("fid")+"'");
		}
		//重新构造方案产品
		if(pdtlist !=null && pdtlist.size()>0){
			String fuuid = "".equals(pdtlist.get(0).getFid())?this.CreateUUid():pdtlist.get(0).getFid();
			for(int i = 0;i<pdtlist.size();i++){
				p = pdtlist.get(i);
				if(p.getFid()==null||"".equals(p.getFid())){
					if(i==0){
//						p.setFid(fuuid);
//						p.setSchemedesignid(scinfo.getFid());
//						p.setSubProductAmount(1);
					}else{
						p.setFid(this.CreateUUid());
					}
				}
				if(i==0){
					p.setFid(fuuid);
					p.setSchemedesignid(scinfo.getFid());
					p.setSubProductAmount(1);
				}
				p.setFcustomerid(scinfo.getFcustomerid());
				p.setFversion("1.0");
				if(i>0){
					p.setFparentid(fuuid);
				}
				this.saveOrUpdate(p);
			}
		}		
		sql = "update t_ord_firstproductdemand set fstate = '已设计' where fid = '"+scinfo.getFfirstproductid()+"' and fstate = '已接收'";
		this.ExecBySql(sql);
		//方案保存成功新增推送消息记录;
		if(!this.QueryExistsBySql("select fid from t_ord_schemedesign where fid = '"+scinfo.getFid()+"'")){
			sql = "SELECT f.fnumber,f.fname,ifnull(u.fname,'') fauditor,u.fid frecipientid FROM t_ord_firstproductdemand f left join t_sys_user u on u.fid=f.fauditorid where f.fid = '"+scinfo.getFfirstproductid()+"'";
			HashMap<String, Object> fpdinfo = (HashMap<String, Object>)QueryBySql(sql).get(0);
			String fcontent = "尊敬的"+fpdinfo.get("fauditor")+"用户！您的'"+fpdinfo.get("fname")+"'需求已经添加方案。";
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("fcontent", fcontent);
			map.put("frecipientid", (String)fpdinfo.get("frecipientid"));
			map.put("ftype", "2");
			simplemessageDao.MessageProjectEvaluationWithSender(map);
		}	
		return "success";
	}

	@Override
	public String affirmSchemeDesignByid(String userid,String fid) {
		// TODO Auto-generated method stub
		//取出所有方案中新增的产品结构
		String sql = "";
		Firstproductdemand fdinfo = new Firstproductdemand();
		sql = "select ffirstproductid from t_ord_schemedesign where fid = "+fid;
		List<HashMap<String,Object>> list =  this.QueryBySql(sql);
		if(list.size()>0){
			fdinfo = (Firstproductdemand) this.Query(Firstproductdemand.class, list.get(0).get("ffirstproductid").toString());
		}
		sql = "select * from t_ord_productstructure where schemedesignid = "+fid;
		List<HashMap<String,Object>> productlist = this.QueryBySql(sql);//查产品信息
		if(productlist.size()>0){
			sql = "SELECT  * FROM t_ord_productstructure  where 1=1 AND fparentid='"+productlist.get(0).get("fid")+"'";
			List<HashMap<String,Object>> subProducts = this.QueryBySql(sql);
			productlist.addAll(subProducts);
		}
		//取出所有方案中上传的附件信息
		sql = "select * from t_ord_productdemandfile where fparentid = "+fid;
		List<HashMap<String,Object>> filelist = this.QueryBySql(sql);
		
		//取出所有方案中新增的特性分录
		sql = "select * from t_ord_schemedesignentry where fparentid = "+fid;
		List<HashMap<String,Object>> entrylist = this.QueryBySql(sql);//查特性信息
		
		if(productlist.size()>0 && entrylist.size()>0){
			return "一个方案不能同时新增特性和产品";
		}
		//高端设计 只有产品和附件
		if(fdinfo.getFisdemandpackage()==0){
			//高端设计的确认直接更改状态
			sql = "update t_ord_schemedesign set fconfirmed=1,fconfirmer ='"+userid+"',fconfirmtime = now() where fid="+fid;//更改方案设计为以确定
			this.ExecBySql(sql);
		}
		//需求包 有（产品/特性）+附件其中一种
		else{
			doAffirm(userid,fid,productlist,entrylist,filelist);
		}
		fdinfo.setFstate("确认方案");
		this.saveOrUpdate(fdinfo);
		return "success";
	}
	
	public void doAffirm(String userid,String id,List<HashMap<String,Object>> productlist,List<HashMap<String,Object>> entrylist,List<HashMap<String,Object>> filelist){
		//高端设计  没特性只有产品
		Productdef p = null;
		TBdCustproduct cp = null;
		Productdemandfile pdf = null;
		Productrelation pr = null;
		Productrelationentry pre = null;
		ProductdefProducts pp = null;
		Custproductproducts cpp;
		String custfid = "";
		String pfid = "";
		Schemedesign scinfo = this.get(id.replaceAll("'",""));
		//确认后生成产品、客户产品、等其它关联关系
		if(productlist !=null && productlist.size()>0){
			for(int i =0;i<productlist.size();i++){
				p = new Productdef();
				p.setFnewtype("3");
				p.setSchemedesignid((String)productlist.get(i).get("schemedesignid"));
				//若size=1，为普通产品；新增时，父产品和普通件存在schemedesignid,子产品没有
				if(productlist.get(i).get("schemedesignid")!=null&&!"".equals(productlist.get(i).get("schemedesignid"))&&productlist.size()>1){//父产品
					p.setFnewtype("4");
				}else if(i>0){ //子产品设置schemedesignid为1
					p.setSchemedesignid("1");
				}
				p.setFid(this.CreateUUid());//待测试
				if(i==0){
					pfid = p.getFid();
				}
				p.setFnumber(productlist.get(i).get("fnumber").toString());
				p.setFcustomerid(productlist.get(i).get("fcustomerid").toString());
				p.setFname(productlist.get(i).get("fname").toString());
				p.setFversion(productlist.get(i).get("fversion").toString());
				p.setFboxlength(new BigDecimal(productlist.get(i).get("fboxlength").toString()));
				p.setFboxheight(new BigDecimal(productlist.get(i).get("fboxheight").toString()));
				p.setFboxwidth(new BigDecimal(productlist.get(i).get("fboxwidth").toString()));
				p.setFmaterialcodeid(productlist.get(i).get("fmaterialcodeid").toString());
				p.setFcreatetime(new Date());
				p.setFlastupdatetime(new Date());
				p.setFlastupdateuserid(userid);
				p.setFcreatorid(userid);
				p.setSubProductAmount(new Integer(productlist.get(i).get("subProductAmount").toString()));
				p.setFissynctoson(0);//FREJECT FISSEVENLAYER FISCOMBINECROSS FCHROMATICPRECISION
				p.setFreject(0);
				p.setFissevenlayer(0);
				p.setFiscombinecross(0);
				p.setFchromaticprecision(new BigDecimal(5.0000000000));
				this.saveOrUpdate(p);//新增产品信息
				
				cp = new TBdCustproduct();
				cp.setFid(this.CreateUUid());
				cp.setFtype(1);
				if(i==0){
					custfid = cp.getFid();
					if(productlist.size()>1){
						cp.setFtype(0);
					}
				}
				cp.setFcreatetime(new Date());
				cp.setFlastupdatetime(new Date());
				cp.setFlastupdateuserid(p.getFcustomerid());
				String spec = productlist.get(i).get("fboxlength")+"*"+productlist.get(i).get("fboxwidth")+"*"+productlist.get(i).get("fboxheight");
				cp.setFspec(spec);
				cp.setFname(p.getFname());
				cp.setFnumber(p.getFnumber());
				cp.setFcustomerid(p.getFcustomerid());
				cp.setFrelationed(2);						//客户产品设置为2，便于在客户产品列表关联产品和修改时控制
				cp.setFproductid(p.getFid());
				this.saveOrUpdate(cp);//新增客户产品信息
				if(i==0 && filelist!=null){
					for(int j = 0;j<filelist.size();j++){
						//产品跟附件的关联关系
						pdf = new Productdemandfile();
						pdf.setFid(this.CreateUUid());
						pdf.setFname(filelist.get(j).get("fname").toString());
						pdf.setFpath(filelist.get(j).get("fpath").toString());
						pdf.setFparentid(p.getFid());
						this.saveOrUpdate(pdf);
						
						//客户产品跟附件的关联关系
						pdf = new Productdemandfile();
						pdf.setFid(this.CreateUUid());
						pdf.setFname(filelist.get(j).get("fname").toString());
						pdf.setFpath(filelist.get(j).get("fpath").toString());
						pdf.setFparentid(cp.getFid());
						this.saveOrUpdate(pdf);
					}
				}
				//产品关联客户关系
				pr = new Productrelation();
				pr.setFid(this.CreateUUid());
				pr.setFcustomerid(p.getFcustomerid());
				pr.setFproductid(p.getFid());
				this.saveOrUpdate(pr);
				
				//记录客户产品关系
				pre = new Productrelationentry();
				pre.setFid(this.CreateUUid());
				pre.setFamount(1);
				pre.setFparentid(pr.getFid());
				pre.setFcustproductid(cp.getFid());
				this.saveOrUpdate(pre);
				if(i>0){
					    //增加产品和子产品关联
						pp = new ProductdefProducts();
						pp.setFid(this.CreateUUid());
						pp.setFparentid(pfid);//套装ID
						pp.setFproductid(p.getFid());//存产品ID
						pp.setFamount(new Integer(productlist.get(i).get("subProductAmount").toString()));
						this.saveOrUpdate(pp);
					
						//增加子客户产品关联
						cpp = new Custproductproducts();
						cpp.setFamount(new Integer(productlist.get(i).get("subProductAmount").toString()));
						cpp.setFcustproductid(cp.getFid());
						cpp.setFid(this.CreateUUid());
						cpp.setFparentid(custfid);
						this.saveOrUpdate(cpp);
				}
			}
		}
		
		//有特性的 根据方案信息生成产品
		if(entrylist!=null && entrylist.size()>0){
			for(int i =0;i<entrylist.size();i++){
				p = new Productdef();
				p.setFnewtype("3");
				p.setSchemedesignid(entrylist.get(i).get("fparentid").toString());
				p.setFid(this.CreateUUid());
				p.setFcustomerid(scinfo.getFcustomerid());
				p.setFname(scinfo.getFname());
				p.setFboxlength(new BigDecimal(scinfo.getFboxlength().toString()));
				p.setFboxheight(new BigDecimal(scinfo.getFboxheight().toString()));
				p.setFboxwidth(new BigDecimal(scinfo.getFboxwidth().toString()));
				p.setFmaterialcodeid(scinfo.getFmaterial());
				p.setFtilemodelid(scinfo.getFtilemodel());
				p.setFcreatetime(new Date());
				p.setFcreatorid(userid);
				p.setFcharacterid(entrylist.get(i).get("fid").toString());
				p.setFcharactername(entrylist.get(i).get("fcharacter").toString());
				p.setFissynctoson(0);//FREJECT FISSEVENLAYER FISCOMBINECROSS FCHROMATICPRECISION
				p.setFreject(0);
				p.setFissevenlayer(0);
				p.setFiscombinecross(0);
				p.setFnumber("");
				p.setFchromaticprecision(new BigDecimal(5.0000000000));
				this.saveOrUpdate(p);//新增产品信息				
				String spec = scinfo.getFboxlength()+"*"+scinfo.getFboxwidth()+"*"+scinfo.getFboxheight();
				cp = new TBdCustproduct();
				cp.setFid(this.CreateUUid());
				cp.setFcreatetime(new Date());
				cp.setFtype(1);
				cp.setFspec(spec);
				cp.setFname(p.getFname());
				cp.setFnumber("");
				cp.setFcustomerid(p.getFcustomerid());
				cp.setFcharacterid(entrylist.get(i).get("fid").toString());
				cp.setFcharactername(entrylist.get(i).get("fcharacter").toString());
				cp.setFrelationed(2);//客户产品设置为2，便于在客户产品列表关联产品和修改时控制
				cp.setFproductid(p.getFid());
				this.saveOrUpdate(cp);//新增客户产品信息
				if(filelist!=null){
					for(int j = 0;j<filelist.size();j++){
						pdf = new Productdemandfile();
						pdf.setFid(this.CreateUUid());
						pdf.setFname(filelist.get(j).get("fname").toString());
						pdf.setFpath(filelist.get(j).get("fpath").toString());
						pdf.setFparentid(p.getFid());
						this.saveOrUpdate(pdf);
						
						pdf = new Productdemandfile();
						pdf.setFid(this.CreateUUid());
						pdf.setFname(filelist.get(j).get("fname").toString());
						pdf.setFpath(filelist.get(j).get("fpath").toString());
						pdf.setFparentid(cp.getFid());
						this.saveOrUpdate(pdf);
					}	
				}
				pr = new Productrelation();
				pr.setFid(this.CreateUUid());
				pr.setFcustomerid(p.getFcustomerid());
				pr.setFproductid(p.getFid());
				this.saveOrUpdate(pr);
				
				pre = new Productrelationentry();
				pre.setFid(this.CreateUUid());
				pre.setFamount(1);
				pre.setFparentid(pr.getFid());
				pre.setFcustproductid(cp.getFid());
				this.saveOrUpdate(pre);
			}
			
			ArrayList<SchemeDesignEntry> sdentry = (ArrayList<SchemeDesignEntry>) this.QueryByHql("from SchemeDesignEntry where fparentid = "+id);
			ArrayList<Productdef> productdefCol = (ArrayList<Productdef>)this.QueryByHql("from Productdef where schemedesignid = "+id);
			Productdef productdefinfo = null;
			if(productdefCol.size()>0){
				productdefinfo = productdefCol.get(0);
			}
			//根据特性信息,生成订单，制造商订单，结存
			execSDOrder(userid,scinfo,sdentry,productdefinfo);
			//自动生成配送
			//已经关闭 不执行
			if(this.QueryExistsBySql("select 1 from t_ord_firstproductdemand where fid ='"+scinfo.getFfirstproductid()+"'  and fstate ='关闭' ")){
				return;
			}
			//已确认不执行
			if(scinfo.getFconfirmed()==1){
				return;
			}
			for(int i =0;i<entrylist.size();i++){				
				execGenerateDelivery(userid,scinfo,entrylist.get(i).get("fid").toString());	
			}			
		}
		String sql = "update t_ord_schemedesign set fconfirmed=1,fconfirmer ='"+userid+"',fconfirmtime = now() where fid="+id;//更改方案设计为以确定
		this.ExecBySql(sql);
		
	
	}

	public synchronized void execSDOrder(String userid,Schemedesign sdinfo, ArrayList<SchemeDesignEntry> sdentry, Productdef productdefinfo){
		String fordernumber = this.getNumber("t_ord_saleorder", "Z",4,false);
		String productid = "769baec7-2d87-11e4-bdb9-00ff6b42e1e5";
		String supplierid = sdinfo.getFsupplierid();
		String schemedesignid = sdinfo.getFid();

		String fcustomerid = sdinfo.getFcustomerid();
		String fsaleorderid = CreateUUid();
		String fProductplanid = CreateUUid();
		String forderid = CreateUUid();

		Saleorder soinfo = new Saleorder();
		soinfo.setFid(fsaleorderid);
		soinfo.setForderid(forderid);
		soinfo.setFcreatorid(userid);
		soinfo.setFcreatetime(new Date());
		soinfo.setFnumber(fordernumber);
		soinfo.setFproductdefid(productid);
		soinfo.setFentryProductType(0);

		soinfo.setFtype(1);
		soinfo.setFlastupdatetime(new Date());
		soinfo.setFlastupdateuserid(userid);
		int amt = 0;
		SchemeDesignEntry sdentryinfo;
		Storebalance storebalance;
		String hql = " from  Supplier s where s.fid = '%s' ";
		hql = String.format(hql, supplierid);
		Supplier supplier = (Supplier) this.QueryByHql(hql).get(0);
		String warehouseid = supplier.getFwarehouseid();
		String warehousesiteid = supplier.getFwarehousesiteid();
		
		for(int i=0;i<sdentry.size();i++){
			sdentryinfo = sdentry.get(i);
			String sdentryid = sdentryinfo.getFid();
			amt = amt + (sdentryinfo.getFentryamount());
			//生成结存记录;
			storebalance = new Storebalance("");
			storebalance.setFcreatorid(userid);
			storebalance.setFproductId(productid);
			storebalance.setFcreatetime(new Date());
			
			storebalance.setFproductplanId(fProductplanid);
			storebalance.setFcustomerid(fcustomerid);
			storebalance.setFsaleorderid(forderid);
			storebalance.setForderentryid(forderid);
			storebalance.setFtraitid(sdentryid);
			storebalance.setFwarehouseId(warehouseid);
			storebalance.setFwarehouseSiteId(warehousesiteid);
			storebalance.setFsupplierID(supplierid);
			storebalance.setFtype(1);
			storebalance.setFallotqty(0);
			storebalance.setFbalanceqty(0);
			storebalance.setFinqty(0);
			storebalance.setFoutqty(0);
			storebalance.setFid(this.CreateUUid());
			this.saveOrUpdate(storebalance);
		}
//		if(amt == 0 ){
//			throw new DJException("特性的方案设计数量不能为空！");
//		}

		soinfo.setFamount(amt);
		soinfo.setFcustomerid(fcustomerid);
		soinfo.setFcustproduct("4a1f4969-2d87-11e4-bdb9-00ff6b42e1e5");
		soinfo.setFarrivetime(sdinfo.getFoverdate());
		soinfo.setFbizdate(new Date());
		soinfo.setFaudited(1);
		soinfo.setFauditorid(userid);
		soinfo.setFaudittime(new Date());
		soinfo.setFamountrate(1);		
		soinfo.setFassemble(0);
		soinfo.setFiscombinecrosssubs(0);	
		soinfo.setFordertype(1);
		soinfo.setFseq(1);
		soinfo.setFimportEas(0);
		soinfo.setFallot(1);

		this.saveOrUpdate(soinfo);

		ProductPlan p = new ProductPlan();
		p.setFid(fProductplanid);
		p.setFdescription(sdinfo.getFdescription());
		p.setFschemedesignid(schemedesignid);
		p.setFboxlength(sdinfo.getFboxlength());
		p.setFboxwidth(sdinfo.getFboxwidth());
		p.setFboxheight(sdinfo.getFboxheight());
		p.setFtype(soinfo.getFtype());
		p.setPerpurchaseprice(new BigDecimal(0));
		p.setPurchaseprice(new BigDecimal(0));	
		p.setFnumber(ServerContext.getNumberHelper().getNumber(this,"t_ord_productplan", "M", 4, false));
		p.setFparentorderid(fsaleorderid);// 设置父订单分录ID
		p.setFseq(soinfo.getFseq());// 设置父订单分录
		p.setFcreatorid(soinfo.getFcreatorid());
		p.setFcreatetime(soinfo.getFcreatetime());
		p.setFlastupdateuserid(soinfo.getFlastupdateuserid());
		p.setFlastupdatetime(soinfo.getFlastupdatetime());
		p.setFcustomerid(soinfo.getFcustomerid());
		p.setFcustproduct(soinfo.getFcustproduct());
		p.setFarrivetime(soinfo.getFarrivetime());
		p.setFbizdate(soinfo.getFbizdate());
		p.setFamount(amt);// 设置生产数量
		p.setFaudited(1);
		p.setFauditorid(soinfo.getFcreatorid());
		p.setFaudittime(new Date());
		p.setFordertype(soinfo.getFordertype());
		p.setFsuitProductId(soinfo.getFsuitProductId());
		p.setFparentOrderEntryId(soinfo.getFparentOrderEntryId());
		p.setForderid(forderid);
		p.setFimportEas(soinfo.getFimportEas());
		p.setFproductdefid(soinfo.getFproductdefid());
		p.setFcustomerid(fcustomerid);
		p.setFsupplierid(supplierid);// 设置供应商
		p.setFentryProductType(soinfo.getFentryProductType());
		p.setFimportEastime(soinfo.getFimportEastime());
		p.setFamountrate(soinfo.getFamountrate());
		p.setFaffirmed(0);
		p.setFstockinqty(soinfo.getFstockinqty());
		p.setFstockoutqty(soinfo.getFstockoutqty());
		p.setFstoreqty(soinfo.getFstoreqty());
		p.setFassemble(soinfo.getFassemble());
		p.setFiscombinecrosssubs(soinfo.getFiscombinecrosssubs());
		p.setFeasorderid(soinfo.getFeasorderid());
		p.setFeasorderentryid(soinfo.getFeasorderentryid());
		p.setFcloseed(0);
		this.saveOrUpdate(p);
		//更改状态
		sdinfo.setFordered(1);
		this.saveOrUpdate(sdinfo);		
	}
	
	public void execGenerateDelivery(String userid,Schemedesign scinfo,String scentryid){
		Firstproductdemand demandT = (Firstproductdemand) Query(Firstproductdemand.class,scinfo.getFfirstproductid());
		SchemeDesignEntry schemeDesignEntryT = (SchemeDesignEntry) Query(SchemeDesignEntry.class, scentryid);
		if(schemeDesignEntryT.getFallot()==1){
			return;
		}
		int famount = schemeDesignEntryT.getFentryamount()-schemeDesignEntryT.getFrealamount();
		if(famount==0){
			return;
		}			
		Deliverapply deliverapplyT = new Deliverapply();
		String sql = "SELECT ad.fid,ad.fname,ad.fdetailaddress,ad.fnumber,ad.flinkman,ad.fphone FROM t_bd_address ad JOIN t_bd_useraddress ud ON ud.`faddress` = ad.`FID` where ud.`fuserid` = '"+demandT.getFcreatid()+"'";
		List<HashMap<String, Object>> sList = this.QueryBySql(sql);		
		//only 1 address,如果该客户关联的地址只有一个，者直接将地址赋值给要货申请，如果有多个者生成的要货申请中的地址不赋值；
		if (sList.size() == 1) {
			deliverapplyT.setFlinkman((String)sList.get(0).get("flinkman"));
			deliverapplyT.setFlinkphone((String)sList.get(0).get("fphone"));
			deliverapplyT.setFaddress((String)sList.get(0).get("fdetailaddress")); 
			deliverapplyT.setFaddressid((String)sList.get(0).get("fid"));				
		}			
		deliverapplyT.setFcreatorid(demandT.getFcreatid());
		deliverapplyT.setFcreatetime(new Date());
		deliverapplyT.setFupdateuserid(userid);
		deliverapplyT.setFupdatetime(new Date());
		deliverapplyT.setFnumber(this.getNumber("t_ord_deliverapply", "Y", 4, false));			
		deliverapplyT.setFcustomerid(scinfo.getFcustomerid());			
		//没有对应客户产品时会出错 
		deliverapplyT.setFcusproductid((String)(QueryByHql(String.format(" select fid from TBdCustproduct where fcharacterid = '%s' ", scentryid)).get(0)));
		deliverapplyT.setFarrivetime(scinfo.getFoverdate());			
		deliverapplyT.setFamount(famount);			
		deliverapplyT.setFdescription(scinfo.getFdescription());
		deliverapplyT.setFordered(0);
		deliverapplyT.setFordermanid("");			
		deliverapplyT.setFordertime(null);
		deliverapplyT.setFsaleorderid("");
		deliverapplyT.setFordernumber(schemeDesignEntryT.getFpurchasenumber());
		deliverapplyT.setForderentryid("");
		deliverapplyT.setFimportEas(0);			
		deliverapplyT.setFimportEasuserid("");
		deliverapplyT.setFimportEastime(null);
		deliverapplyT.setFouted(0);
		deliverapplyT.setFoutorid("");
		deliverapplyT.setFouttime(null);					
		deliverapplyT.setFeasdeliverid("");
		deliverapplyT.setFistoPlan(0);
		deliverapplyT.setFplanTime(null);
		deliverapplyT.setFplanNumber("");			
		deliverapplyT.setFplanid("");
		deliverapplyT.setFalloted(0);
		deliverapplyT.setFiscreate(0);
		deliverapplyT.setFcusfid("");
		deliverapplyT.setFstate(0);			
		deliverapplyT.setFtype("0");
		deliverapplyT.setFtraitid(schemeDesignEntryT.getFid());
		deliverapplyT.setFcharacter(schemeDesignEntryT.getFcharacter());			
		//设置制造商为东经
		deliverapplyT.setFsupplierid("39gW7X9mRcWoSwsNJhU12TfGffw=");			
		deliverapplyT.setFid(CreateUUid());			
		saveOrUpdate(deliverapplyT);			
		//修改分路状态
		String insertsql="update t_ord_schemedesignentry set frealamount=fentryamount,fallot=1 where fid='"+scinfo.getFid()+"'";
		ExecBySql(insertsql);		
	}
}
