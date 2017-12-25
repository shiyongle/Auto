package com.dao.saledeliver;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;

import sun.misc.FpUtils;


import com.dao.IBaseDaoImpl;
import com.model.PageModel;
import com.model.address.Address;
import com.model.custRelationAdress.CustRelationAdress;
import com.model.customer.Customer;
import com.model.custproduct.TBdCustproduct;
import com.model.deliverapply.Deliverapply;
import com.model.productdef.Productdef;
import com.model.productreqallocationrules.Productreqallocationrules;
import com.model.saledeliver.FTURelation;
import com.model.saledeliver.FTUSaledeliver;
import com.model.saledeliver.FtuParameter;
import com.model.saledeliver.FtuTemplate;
import com.model.saledeliverdetail.FTUSaledeliverEntry;
import com.util.DJException;
import com.util.Params;
import com.util.StringUitl;

/*
 * CPS-VMI-wangc
 */

@Repository("saledeliverDao")
public class SaledeliverDaoImpl extends IBaseDaoImpl<FTUSaledeliver,java.lang.String> implements SaledeliverDao{

	@Override
	public PageModel<FTUSaledeliver> findBySql(final String where,final Object[] queryParams,final  Map<String, String> orderby, final int pageNo,final int maxResult) {
		final PageModel<FTUSaledeliver> pageModel = new PageModel<FTUSaledeliver>();
		pageModel.setPageNo(pageNo);
		pageModel.setPageSize(maxResult);
		this.getHibernateTemplate().execute(new HibernateCallback() {
			@SuppressWarnings("unchecked")
			@Override
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				StringBuilder sql = new StringBuilder().append(" select ")
									.append(" sa.fid           as fid,")
									//.append(" '三级厂'         as  fcustname,")
									.append(" p.fproductid     as fproductid,")
									.append(" sa.fcustomer     as fcustoermid,")
									.append(" sa.fcustAddress  as fcustAddress,")
									.append(" sa.fbusNumber    as fbusNumber,")
									.append(" sa.fstate		   as fstate,")
									.append(" c.fname  		   as fcustomer,")
									.append(" sa.fphone 	   as fphone,")
									.append(" sa.ffax          as ffax,")
									.append(" sa.ftel          as ftel,")
									.append(" sa.fsupplierid   as fsupplierid,")
									.append(" sa.fsuppliername as fsuppliername,")
									.append(" sa.fnumber 	   as fnumber,")
									.append(" p.fname 		   as fname,")
									.append(" p.fspec          as fspec,")
									.append(" p.funit 		   as funit,")
									.append(" sd.famount       as famount,")
									.append(" CASE sd.famount WHEN 0 THEN p.fprice ELSE IFNULL(sd.fdanjia,ROUND(sd.fprice / sd.famount, 3)) END  as fdanjia,")
									.append(" sd.fprice 	   as fprice,")
									.append(" sa.fcreatetime   as fcreatetime,")
									.append(" sa.fclerk 	   as fclerk,")
									.append(" sa.fdriver       as fdriver,")
									.append(" sa.fprinttime    as fprinttime,")
									.append(" sd.fdescription  as fdescription")
									.append(" FROM t_ftu_saledeliver sa ")
									.append(" LEFT JOIN t_ftu_saledeliverentry sd ON sd.fparentid = sa.fid")
									.append(" LEFT JOIN t_bd_custproduct p ON sd.fftuproductid = p.fid")
									.append(" LEFT JOIN t_bd_customer c ON sa.fcustomer = c.fid ")
									.append(" LEFT JOIN t_bd_usersupplier usp on usp.fsupplierid =sa.fsupplierid")
									.append(where == null ? "" : where)
									.append(createOrderBy(orderby));
				Query query = session.createSQLQuery(sql.toString())
									.addScalar("fid")
									//.addScalar("fcustname")
									.addScalar("fproductid")
									.addScalar("fcustoermid")
									.addScalar("fcustAddress")
									.addScalar("fbusNumber")
									.addScalar("fstate")
									.addScalar("fcustomer")
									.addScalar("fphone")
									.addScalar("ffax")
									.addScalar("ftel")
									.addScalar("fsupplierid")
									.addScalar("fsuppliername")
									.addScalar("fnumber")
									.addScalar("fname")
									.addScalar("fspec")
									.addScalar("funit")
									.addScalar("famount")
									.addScalar("fdanjia")
									.addScalar("fprice")
									.addScalar("fcreatetime")
									.addScalar("fclerk")
									.addScalar("fdriver")
									.addScalar("fprinttime")
									.addScalar("fdescription");
				setQueryParams2(query,queryParams);
				query.setResultTransformer(Transformers.aliasToBean(FTUSaledeliver.class));
				List<FTUSaledeliver> list = query.setFirstResult(getFirstResult(pageNo, maxResult)).setMaxResults(maxResult).list();
							 sql = new StringBuilder().append("select count(*) ")
									 .append(" FROM t_ftu_saledeliver sa ")
									 .append(" LEFT JOIN t_ftu_saledeliverentry sd ON sd.fparentid = sa.fid")
									 .append(" LEFT JOIN t_bd_custproduct p ON sd.fftuproductid = p.fid")
									 .append(" LEFT JOIN t_bd_customer c ON sa.fcustomer = c.fid ")
									 .append(" LEFT JOIN t_bd_usersupplier usp on usp.fsupplierid =sa.fsupplierid")
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
	public void updateSyscfg(String fuserid, String fkey, String fvalue){
		String sql = "select 1 from t_sys_syscfg where fkey='"+fkey+"' and fuser='"+fuserid+"'";
		if(this.QueryExistsBySql(sql)){
			sql = "update t_sys_syscfg set fvalue='"+fvalue+"' where fkey='"+fkey+"' and fuser='"+fuserid+"'";
		}else{
			sql = "insert into t_sys_syscfg(fid,fkey,fvalue,fuser) values('"+this.CreateUUid()+"','"+fkey+"','"+fvalue+"','"+fuserid+"')";
		}
		this.ExecBySql(sql);
	}
	@Override
	public void saveOrupdateFtuParameters(List list,FtuParameter fp,String fuserid) {
		// TODO Auto-generated method stub
		String sql = "";
		if(list.size()>0){
			sql = String.format("update t_ftu_parameter set falias='%s',fieldtype='%s',fdecimals='%s',fcomputationalformula='%s',fisprint='%s' where falias='%s' and fuserid='%s'",
					fp.getFalias(),fp.getFieldtype(),StringUitl.isNullOrEmpty(fp.getFdecimals())?0:fp.getFdecimals(),StringUitl.isNullOrEmpty(fp.getFcomputationalformula())?"":fp.getFcomputationalformula(),StringUitl.isNullOrEmpty(fp.getFisprint())?0:fp.getFisprint(),fp.getFname(),fuserid);
			this.ExecBySql(sql);
		}else{
			fp.setFuserid(fuserid);
			fp.setFid(this.CreateUUid());
			this.save(fp);
		}
	}
	@Override
	public void saveAllFtuParams(String userid,List<FtuParameter> fp){
		List list;
		String sql;
		Params pa = new Params();
		for(FtuParameter p : fp){
			sql = "select 1 from t_ftu_parameter where fname ='"+p.getFname()+"' and fuserid='"+userid+"'";
			list = this.QueryBySql(sql, pa);
			if(list.size()==0){
				p.setFid(this.CreateUUid());
				p.setFedit(1);
				p.setFisprint(1);
				p.setFalias(p.getFname());
				p.setFuserid(userid);
				p.setFdecimals(p.getFdecimals()==null?0:p.getFdecimals());
				this.saveOrUpdate(p);
			}
		}
	}
	@Override
	public void savePrintTemplate(String fuserid,String html,File file) throws IOException{
		FtuTemplate ft = new FtuTemplate();
		
		
		String fpath = 	file.getParent()+"/vmifile/printTemplate";//构造按当前日期保存文件的路径 yyyy-MM-dd
		String fileName = this.CreateUUid()+".txt";//文件名
		
		String sql = "select ftemplate from t_ftu_template where fuserid='"+fuserid+"'";
		List<HashMap<String,Object>> list = this.QueryBySql(sql);
		if(list.size()>0){
			fileName = list.get(0).get("ftemplate").toString();
		}
		file = new File(fpath);
		if(!file.exists()){
			file.mkdirs();
		}
		File f = new File(file,fileName);
		if(!f.exists()){
			f.createNewFile();
			ft.setFid(this.CreateUUid());
			ft.setFuserid(fuserid);
			ft.setFtemplate(fileName);
			this.save(ft);
		}
		FileOutputStream txtfile = new FileOutputStream(f);
		PrintStream p = new PrintStream(txtfile);
		p.println(html);
		p.close();
	}
	@Override
	public void resetPrintTemplate(String fuserid,File file)throws IOException{
		String sql = "select ftemplate from t_ftu_template where fuserid='"+fuserid+"'";
		List<HashMap<String,Object>> list = this.QueryBySql(sql);
		if(list.size()>0){
			
			String fpath = 	file.getParent()+"/vmifile/printTemplate";//构造按当前日期保存文件的路径 yyyy-MM-dd
			File files = new File(fpath+"\\"+list.get(0).get("ftemplate"));
			if(files.exists()){
				files.delete();
			}
			sql = "delete from t_ftu_template  where fuserid='"+fuserid+"'";
			this.ExecBySql(sql);
		}
		sql = "delete from t_ftu_parameter where fuserid ='"+fuserid+"'";
		this.ExecBySql(sql);//还原模板是 删除以前的配置
		sql = "delete from t_sys_syscfg where fuser='" + fuserid
				+ "' and fkey='thePrintTP'";
		this.ExecBySql(sql);//删除模板配置2联 3联
	}
	@Override
	public void saveFTUprint(String fid){
		String sql = String.format("update t_ftu_saledeliver set fprinttime=now(),fprintcount=ifnull(fprintcount,0)+1 where fid='%s'",fid);
		this.ExecBySql(sql);
	}
	@Override
	public void saveNewFTUSaleDeliver(String fsupplierid,FTUSaledeliver fs,List<FTUSaledeliverEntry> fse,String userid) throws Exception{
		String sql;
//		String fsuppliername = baseSysDao.getStringValue("select fname from t_sys_supplier where fid ='"+fsupplierid+"'", "fname");
		String fsuppliername = this.getStringValue("select fcustomername from t_sys_user where fid ='"+userid+"'", "fcustomername");
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date currentTime = new Date();
		if(fs!=null){
			FTUSaledeliver ftuSaledeliver  = (FTUSaledeliver)this.Query(FTUSaledeliver.class, fs.getFid());
			if(ftuSaledeliver!=null){//已经有送货凭证时，删产品、送货凭证分录、送货凭证
				sql = "select fftuproductid from t_ftu_saledeliverentry where fparentid='%s'";
				List<HashMap<String,Object>> list = this.QueryBySql(String.format(sql, ftuSaledeliver.getFid()));
				sql = "delete from t_ftu_saledeliverentry where fparentid='%s'";
				this.ExecBySql(String.format(sql, ftuSaledeliver.getFid()));
				sql = "delete from t_ftu_saledeliver where fid='%s'";
				this.ExecBySql(String.format(sql, ftuSaledeliver.getFid()));
			}
			
			Customer c = new Customer();
			if(StringUtils.isEmpty(fs.getFcustomername())){
				throw new Exception("客户不能为空");
			}
			sql = "select c.fid from t_bd_customer c left join t_pdt_productreqallocationrules p on c.fid=p.fcustomerid where c.fname='"+fs.getFcustomername()+"' and p.fsupplierid='"+fsupplierid+"'";
			List<HashMap<String,Object>>map = this.QueryBySql(sql);
			if(map.size()==0){//新增客户
				c.setFid(this.CreateUUid());
				c.setFname(fs.getFcustomername());
				c.setFphone(fs.getFphone());
				c.setFnumber(fs.getFcustomername());
				c.setFcreatetime(new Date());
				this.saveOrUpdate(c);
				
				Productreqallocationrules p = new Productreqallocationrules();
				p.setFid(this.CreateUUid());
				p.setFcreatetime(new Date());
				p.setFcreatorid(userid);
				p.setFcustomerid(c.getFid());
				p.setFsupplierid(fsupplierid);
				this.saveOrUpdate(p);
				
			}else{//有客户时，更新客户信息
				c.setFid((String)map.get(0).get("fid"));
				sql = "update t_bd_customer set fphone='"+fs.getFphone()+"' where fid='"+c.getFid()+"'";
				this.ExecBySql(sql);
			}
			if(!StringUtils.isEmpty(fs.getFcustAddress())){
				sql = "select fid,fname from t_bd_address where fname ='"+fs.getFcustAddress()+"'  and fcustomerid='"+c.getFid()+"'";
				map = this.QueryBySql(sql);
				Address address = new Address();
				if(map.size()==0){//客户地址
					address.setFid(this.CreateUUid());
					address.setFname(fs.getFcustAddress());
					address.setFcreatetime(new Date());
					address.setFcustomerid(c.getFid());
					address.setFdetailaddress(fs.getFcustAddress());
					this.saveOrUpdate(address);
					
					CustRelationAdress cd = new CustRelationAdress();
					cd.setFid(this.CreateUUid());
					cd.setFaddressid(address.getFid());
					cd.setFcustomerid(c.getFid());
					cd.setFeffect(1);
					this.saveOrUpdate(cd);
				}else{
					address.setFname((String)map.get(0).get("fname"));
				}
			}
			
			fs.setFid(this.CreateUUid());
			fs.setFsupplierid(fsupplierid);
			fs.setFsuppliername(fsuppliername);
			fs.setFcreator(userid);
			fs.setFcustomer(c.getFid());
			fs.setFphone(fs.getFphone());
			if(!StringUitl.isNullOrEmpty(ftuSaledeliver)&&!StringUitl.isNullOrEmpty(ftuSaledeliver.getFprinttime())){
				fs.setFprinttime(ftuSaledeliver.getFprinttime());
				fs.setFprintcount(ftuSaledeliver.getFprintcount());
			}
			
			String fparentid = fs.getFid();
			String fcustomerid = fs.getFcustomer();
			int i = 0;
			Productdef p;
			TBdCustproduct cp;
			StringBuffer  cid = new StringBuffer(" ");
			StringBuffer  pid = new StringBuffer(" ");
			this.ExecBySql("delete from t_ftu_saledeliverentry where fparentid='"+fparentid+"'");//保存时先删除所有分录信息
			for (FTUSaledeliverEntry ftuSaledeliverEntry : fse) {
				sql = "select fid,fproductid from t_bd_custproduct where fcustomerid='"+fcustomerid+"' and  fname='"+ftuSaledeliverEntry.getFcusproductname()+"' and fspec='"+ftuSaledeliverEntry.getFspec()+"'";
				String fid = this.getStringValue(sql, "fid");//客户产品ID
				p = new Productdef();
				cp = new TBdCustproduct();
				if(fid==null){
					cp.setFid(this.CreateUUid());
					cp.setFcreatetime(new Date());
					
					p.setFid(this.CreateUUid());
					p.setFcreatetime(new Date());
				}else{
					cp.setFid(fid);
					if(!StringUtils.isEmpty(fid)){
						p.setFid(this.getStringValue(sql, "fproductid"));
						p.setFcreatetime(cp.getFcreatetime());
					}else{
						p.setFid(this.CreateUUid());
						p.setFcreatetime(new Date());
					}
					
				}
				try {
					p.setFprice(BigDecimal.valueOf(ftuSaledeliverEntry.getFdanjia()==null?0:Double.valueOf(ftuSaledeliverEntry.getFdanjia())));
					cp.setFprice(BigDecimal.valueOf(ftuSaledeliverEntry.getFdanjia()==null?0:Double.valueOf(ftuSaledeliverEntry.getFdanjia())));
				} catch (Exception e) {
					// TODO: handle exception
				}
				p.setFname(ftuSaledeliverEntry.getFcusproductname());
				p.setFcharacter(ftuSaledeliverEntry.getFspec());
				p.setFdescription(ftuSaledeliverEntry.getFdescription());
				p.setFcustomerid(fcustomerid);
				p.setFsupplierid(fs.getFsupplierid());
				p.setFeffect(1);
				p.setFeffected(1);
				
				cp.setFcustomerid(fcustomerid);
				cp.setFname(ftuSaledeliverEntry.getFcusproductname());
				cp.setFspec(ftuSaledeliverEntry.getFspec());
				cp.setFproductid(p.getFid());
				cp.setFdescription(ftuSaledeliverEntry.getFdescription());
				cp.setFunit(ftuSaledeliverEntry.getFunit());
				cp.setFordercount(1);
				cp.setFtype(1);
				cp.setFeffect(1);
				if(cid.indexOf(cp.getFid())<0){//相同客户产品ID不能保存
					cid.append(cp.getFid()+",");
					if(pid.indexOf(cp.getFproductid())<0){
						pid.append(cp.getFproductid()+",");
						this.saveOrUpdate(p);//保存产品信息
					}
					this.saveOrUpdate(cp);//保存客户产品信息
				}
				ftuSaledeliverEntry.setFid(this.CreateUUid());
				ftuSaledeliverEntry.setFftuproductid(cp.getFid());
				ftuSaledeliverEntry.setFparentid(fparentid);
				ftuSaledeliverEntry.setFsequence(i++);
				this.saveOrUpdate(ftuSaledeliverEntry);//保存送货凭证分录信息
			}
			this.saveOrUpdate(fs);//保存送货凭证信息
			
			String fcustomer = fs.getFcustomer();
			String fdriver = fs.getFdriver();
			String fclerk = fs.getFclerk();
	//		String fsupplierid = saledeliver.getFsupplierid();
			FTURelation relation;
			//1-司机，2-开单员
			if(!StringUtils.isEmpty(fdriver)&&!this.QueryExistsBySql("select 1 from t_ftu_relation where fsupplierid = '"+fsupplierid+"' and fname='"+fdriver+"' and ftype=1")){
				relation = new FTURelation();
				relation.setFid(this.CreateUUid());
				relation.setFsupplierid(fsupplierid);
				relation.setFname(fdriver);
				relation.setFtype((short)1);
				this.saveOrUpdate(relation);
			}
			if(!StringUtils.isEmpty(fclerk)&&!this.QueryExistsBySql("select 1 from t_ftu_relation where fsupplierid = '"+fsupplierid+"' and fname='"+fclerk+"' and ftype=2")){
				relation = new FTURelation();
				relation.setFid(this.CreateUUid());
				relation.setFsupplierid(fsupplierid);
				relation.setFname(fclerk);
				relation.setFtype((short)2);
				this.saveOrUpdate(relation);
			}
		}
	
	}
	@Override
	public void updateFTUstate(String fid,String userid){
		String sql="select fsupplierid from ( select fsupplierid  from t_bd_usersupplier where fuserid='"+userid+"'"+
				" union select c.fsupplierid from  t_sys_userrole r "+
				" left join t_bd_rolesupplier c on r.froleid=c.froleid where r.fuserid='"+userid+"' ) s where fsupplierid is not null " ;
		List<HashMap<String,Object>> list=this.QueryBySql(sql);
		if(list.size()==0){
			throw new DJException("请关联制造商！");
		}
		sql = "update t_ftu_saledeliver set fstate=2 where fid in("+fid+") and fsupplierid ='"+list.get(0).get("fsupplierid")+"'";
		this.ExecBySql(sql);
	}
	@Override
	public PageModel<HashMap<String, Object>> findBySqlWithRpt(final String where,final Object[] queryParams,final  Map<String, String> orderby, final int pageNo,final int maxResult) {
		final PageModel<HashMap<String, Object>> pageModel = new PageModel<HashMap<String, Object>>();
		pageModel.setPageNo(pageNo);
		pageModel.setPageSize(maxResult);
		this.getHibernateTemplate().execute(new HibernateCallback() {
			@SuppressWarnings("unchecked")
			@Override
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				StringBuilder sql = new StringBuilder().append(" select ")
									.append(" sd.fnumber, ")
									.append(" 	ifnull(date_format(sd.fprinttime,'%Y-%m-%d'),'') fprinttime, ")
									.append(" sa.fid,")
									.append(" p.fname,")
									.append(" ifnull(p.fspec,'') fspec,")
									.append(" sa.fdanjia as fprice,")
									.append(" ifnull(sa.fdescription,'') as fdescription,")
									.append("  sa.famount,")
									.append("  sa.fprice  as fprices ")
									.append(" FROM t_bd_custproduct p ")
									.append(" LEFT JOIN t_ftu_saledeliverentry sa ON sa.fftuproductid = p.fid ")
									.append(" LEFT JOIN t_ftu_saledeliver sd  ON sa.fparentid = sd.fid")
									.append(where == null ? "" : where)
									.append(createOrderBy(orderby));
				Query query = session.createSQLQuery(sql.toString());
				setQueryParams2(query,queryParams);
				query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				List<HashMap<String, Object>> list = query.setFirstResult(getFirstResult(pageNo, maxResult)).setMaxResults(maxResult).list();
							 sql = new StringBuilder().append("select count(*) ")
									 .append(" FROM t_bd_custproduct p ")
									.append(" LEFT JOIN t_ftu_saledeliverentry sa ON sa.fftuproductid = p.fid ")
									.append(" LEFT JOIN t_ftu_saledeliver sd  ON sa.fparentid = sd.fid")
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
	@SuppressWarnings("unchecked")
	public List getFTUstatements(final String where,final Object[] queryParams,final  Map<String, String> orderby) {
		List ftulist=(List<HashMap<String, Object>>)this.getHibernateTemplate().execute(new HibernateCallback() {
			@Override
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				StringBuilder sql = new StringBuilder().append(" select ")
									.append(" sd.fnumber, ")
									.append(" 	ifnull(date_format(sd.fprinttime,'%Y-%m-%d'),'') fprinttime, ")
									.append(" sa.fid,")
									.append(" p.fname,")
									.append(" ifnull(p.fspec,'') fspec,")
									.append(" sa.fdanjia as fprice,")
									.append(" ifnull(sa.fdescription,'') as fdescription,")
									.append("  sa.famount,")
									.append("  sa.fprice  as fprices ")
									.append(" FROM t_bd_custproduct p ")
									.append(" LEFT JOIN t_ftu_saledeliverentry sa ON sa.fftuproductid = p.fid ")
									.append(" LEFT JOIN t_ftu_saledeliver sd  ON sa.fparentid = sd.fid")
									.append(where == null ? "" : where)
									.append(createOrderBy(orderby));
				Query query = session.createSQLQuery(sql.toString());
				setQueryParams2(query,queryParams);
				query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				List<HashMap<String, Object>> list = query.list();
			   return list;
			}
		});
		return ftulist;
	}

	@Override
	public List getSupplieridByUserid(String userid) {
		Params p=new Params();
		String sql="select fsupplierid from ( select fsupplierid  from t_bd_usersupplier where fuserid=:fuserid"+
				" union select c.fsupplierid from  t_sys_userrole r "+
				" left join t_bd_rolesupplier c on r.froleid=c.froleid where r.fuserid=:fuserid )  s where fsupplierid is not null " ;
		p.put("fuserid", userid);
		@SuppressWarnings("unchecked")
		List<HashMap<String, Object>> suppList=this.QueryBySql(sql, p);
		return suppList;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<FTUSaledeliver> getFtulist(final String where,final Object[] queryParams,final  Map<String, String> orderby) {
		List<FTUSaledeliver> ftulist=(List<FTUSaledeliver>)
				this.getHibernateTemplate().execute(new HibernateCallback() {
			@Override
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				StringBuilder sql = new StringBuilder().append(" select ")
									.append(" sa.fid           as fid,")
									.append(" c.fname  		   as fcustomer,")
									.append(" sa.fphone 	   as fphone,")
									.append(" sa.fsuppliername as fsuppliername,")
									.append(" sa.fnumber 	   as fnumber,")
									.append(" p.fname 		   as fname,")
									.append(" ifnull(p.fspec,'')          as fspec,")
									.append(" sd.famount       as famount,")
									.append(" sd.funit       as funit,")
									.append(" CASE sd.famount WHEN 0 THEN p.fprice ELSE IFNULL(sd.fdanjia,ROUND(sd.fprice / sd.famount, 3)) END  as fdanjia,")
									.append(" sd.fprice 	   as fprice,")
									.append(" sa.fcreatetime   as fcreatetime,")
									.append(" sa.fprinttime    as fprinttime,")
									.append(" ifnull(sd.fdescription,'')  as fdescription,")
									.append(" sa.fdriver as fdriver")
									.append(" FROM t_ftu_saledeliver sa ")
									.append(" LEFT JOIN t_ftu_saledeliverentry sd ON sd.fparentid = sa.fid")
									.append(" LEFT JOIN t_bd_custproduct p ON sd.fftuproductid = p.fid")
									.append(" LEFT JOIN t_bd_customer c ON sa.fcustomer = c.fid ")
									.append(" LEFT JOIN t_bd_usersupplier usp on usp.fsupplierid =sa.fsupplierid")
									.append(where == null ? "" : where)
									.append(createOrderBy(orderby));
				Query query = session.createSQLQuery(sql.toString())
									.addScalar("fid")
									.addScalar("fcustomer")
									.addScalar("fphone")
									.addScalar("fsuppliername")
									.addScalar("fnumber")
									.addScalar("fname")
									.addScalar("fspec")
									.addScalar("famount")
									.addScalar("funit")
									.addScalar("fdanjia")
									.addScalar("fprice")
									.addScalar("fcreatetime")
									.addScalar("fprinttime")
									.addScalar("fdescription")
									.addScalar("fdriver");
				setQueryParams2(query,queryParams);
				query.setResultTransformer(Transformers.aliasToBean(FTUSaledeliver.class));
			   return query.list();
			}
		});
		return ftulist;
	}
}