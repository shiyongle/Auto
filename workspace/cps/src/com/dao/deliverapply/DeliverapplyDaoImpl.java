package com.dao.deliverapply;

import java.io.InputStream;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;

import com.dao.IBaseDaoImpl;
import com.model.PageModel;
import com.model.deliverapply.CustBoardLabel;
import com.model.deliverapply.Deliverapply;
import com.model.produceplan.ProducePlan;
import com.model.productdef.CustBoardFormula;
import com.model.productdef.Productdef;
import com.model.supplierboardplanconfig.Supplierboardplanconfig;
import com.util.StringUitl;
@SuppressWarnings("unchecked")
@Repository("deliverapplyDao")
public class DeliverapplyDaoImpl extends IBaseDaoImpl<Deliverapply, java.lang.Integer> implements DeliverapplyDao {

	@Override
	public PageModel<Deliverapply> findBySql(final String where,final Object[] queryParams, final Map<String, String> orderby, final int pageNo,final int maxResult,final String...t_name) {
		final PageModel<Deliverapply> pageModel = new PageModel<Deliverapply>();
		pageModel.setPageNo(pageNo);
		pageModel.setPageSize(maxResult);
		this.getHibernateTemplate().execute(new HibernateCallback() {
			@Override
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				//2016-3-21 lxx
				String tablename="t_ord_deliverapply_card_mv";
				if(t_name.length!=0&&t_name[0]!=null)
				{
					System.out.println("三个月前数据的表名更改为:"+t_name[0]);
					tablename=t_name[0];
				}
				StringBuilder sql = new StringBuilder().append(" select ")
									.append(" mv.fcustomerid   as fcustomerid,")
									.append(" mv.fid 		   as fid,")
									.append(" mv.fcusproductid as fcusproductid,")
									.append(" mv._custpdtname  as fpdtname,")
									.append(" mv._spec         as fpdtspec,")
									.append(" mv.famount       as famount,")
									.append(" mv.foutqty       as foutQty,")
									.append(" date_format(mv.farrivetime,'%Y-%m-%d %H:%i') as farrivetimeString,")
									.append(" mv.faddressid    as faddressid,")
									.append(" mv.faddress      as faddress,")
									.append(" mv._suppliername as suppliername,")
									.append(" sp.ftel as supplierPhone,")
									.append(" mv.fordernumber  as fordernumber,")
									.append(" mv.fnumber       as fnumber,")
									.append(" date_format(mv.fcreatetime,'%Y-%m-%d %H:%i') as placeOrderTime,")
									.append(" mv.fstate        as fstate,")
									.append(" mv.fdescription  as fdescription")
									.append(" FROM "+tablename+" mv ")
									.append(" LEFT JOIN t_sys_supplier sp on sp.fid = mv.fsupplierid")
								//	.append(" INNER JOIN t_bd_usercustomer tus on tus.FCUSTOMERID = mv.fcustomerid")
									//.append(" INNER JOIN t_sys_user tsy on tsy.fid = tus.FUSERID")
//									.append(where == null ? "" : where)
									.append(where == null ? "where 1=1 "+QueryFilterByUserofuser("mv.fcreatorid","and") : where+QueryFilterByUserofuser("mv.fcreatorid","and"))
									.append(createOrderBy(orderby));
				Query query = session.createSQLQuery(sql.toString())
									.addScalar("fcustomerid")
									.addScalar("fid")
									.addScalar("fcusproductid")
									.addScalar("fpdtname")
									.addScalar("fpdtspec")
									.addScalar("famount")
									.addScalar("foutQty")
									.addScalar("farrivetimeString")
									.addScalar("faddressid")
									.addScalar("faddress")
									.addScalar("suppliername")
									.addScalar("supplierPhone")
									.addScalar("fordernumber")
									.addScalar("fnumber")
									.addScalar("placeOrderTime")
									.addScalar("fstate")
									.addScalar("fdescription");
				setQueryParams2(query,queryParams);
				query.setResultTransformer(Transformers.aliasToBean(Deliverapply.class));
				List<Deliverapply> list = query.setFirstResult(getFirstResult(pageNo, maxResult)).setMaxResults(maxResult).list();
							 sql = new StringBuilder().append("select count(*)")
									 .append(" FROM "+tablename+" mv ")
									 .append(" LEFT JOIN t_sys_supplier sp on sp.fid = mv.fsupplierid")
									// .append(" INNER JOIN t_bd_usercustomer tus on tus.FCUSTOMERID = mv.fcustomerid")
									 //.append(" INNER JOIN t_sys_user tsy on tsy.fid = tus.FUSERID")
							         .append(where == null ? "where 1=1 "+QueryFilterByUserofuser("mv.fcreatorid","and") : where+QueryFilterByUserofuser("mv.fcreatorid","and"));
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
	public List<Deliverapply> ExecBySql(final String where,final Object[] queryParams, final Map<String, String> orderby) {
		List<Deliverapply> pul =(List<Deliverapply>) this.getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				StringBuilder sql = new StringBuilder().append(" select ")
								.append(" mv.fcustomerid   as fcustomerid,")
								.append(" mv.fid 		   as fid,")
								.append(" mv.fcusproductid as fcusproductid,")
								.append(" mv._custpdtname  as fpdtname,")
								.append(" mv._spec         as fpdtspec,")
								.append(" mv.famount       as famount,")
								.append(" mv.foutqty       as foutQty,")
								.append( "mv.farrivetime   as farrivetime,")
								.append(" mv.faddressid    as faddressid,")
								.append(" mv.faddress      as faddress,")
								.append(" mv._suppliername as suppliername,")
								.append(" sp.ftel 		   as supplierPhone,")
								.append(" mv.fordernumber  as fordernumber,")
								.append(" mv.fnumber       as fnumber,")
								.append(" date_format(mv.fcreatetime,'%Y-%m-%d %H:%i') as placeOrderTime,")
								.append(" mv.fstate        as fstate,")
								.append(" mv.fdescription  as fdescription")
								.append(" FROM t_ord_deliverapply_card_mv mv ")
								.append(" LEFT JOIN t_sys_supplier sp on sp.fid = mv.fsupplierid")
								.append(" INNER JOIN t_bd_usercustomer tus on tus.FCUSTOMERID = mv.fcustomerid")
								.append(" INNER JOIN t_sys_user tsy on tsy.fid = tus.FUSERID")
								.append(where == null ? "where 1=1 "+QueryFilterByUserofuser("mv.fcreatorid","and") : where+QueryFilterByUserofuser("mv.fcreatorid","and"))
								.append(createOrderBy(orderby));
			Query query = session.createSQLQuery(sql.toString())
								.addScalar("fcustomerid")
								.addScalar("fid")
								.addScalar("fcusproductid")
								.addScalar("fpdtname")
								.addScalar("fpdtspec")
								.addScalar("famount")
								.addScalar("foutQty")
								.addScalar("farrivetime")
								.addScalar("faddressid")
								.addScalar("faddress")
								.addScalar("suppliername")
								.addScalar("supplierPhone")
								.addScalar("fordernumber")
								.addScalar("fnumber")
								.addScalar("placeOrderTime")
								.addScalar("fstate")
								.addScalar("fdescription");
				setQueryParams2(query,queryParams);
				query.setResultTransformer(Transformers.aliasToBean(Deliverapply.class));
			   return  query.list();
			}
		});
		return pul;
	}
	
	/**
	 * 查询要货申请状态
	 */
	public List<Deliverapply> getDeliverapplyInfo(final String where,final Object[] queryParams, final Map<String, String> orderby,final String...t_name) {
		List<Deliverapply> pul =(List<Deliverapply>) this.getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				//2016-3-24 lxx
				String tablename="t_ord_deliverapply_card_mv";
				if(t_name.length!=0&&t_name[0]!=null)//可选参数有值并且不等于空
				{
					tablename=t_name[0];
				}
				StringBuilder sql = new StringBuilder().append(" select ")
								.append(" mv.fcustomerid   as fcustomerid,")
								.append(" mv.fid 		   as fid,")
								.append(" mv.fcusproductid as fcusproductid,")
								.append(" mv._custpdtname  as fpdtname,")
								.append(" mv._spec         as fpdtspec,")
								.append(" mv.famount       as famount,")
								.append(" mv.foutqty       as foutQty,")
								.append(" CONCAT(date_format(mv.farrivetime ,'%Y-%m-%d'),' ',IF(DATE_FORMAT(mv.farrivetime,'%p')='AM','上午','下午'))   as farrivetimeString,")
								.append(" mv.faddressid    as faddressid,")
								.append(" mv.faddress      as faddress,")
								.append(" mv._suppliername as suppliername,")
								.append(" mv.fordernumber  as fordernumber,")
								.append(" mv.fnumber       as fnumber,")
								.append(" mv.fcreatetime as fcreatetime,")
								.append(" mv.fstate        as fstate,")
								.append(" mv.fdescription  as fdescription,")
								.append(" mv.flinkman  as flinkman,")
								.append(" mv.flinkphone  as flinkphone")
								.append(" FROM "+tablename+" mv ")
								.append(where == null ? "where 1=1 "+QueryFilterByUserofuser("mv.fcreatorid","and") : where+QueryFilterByUserofuser("mv.fcreatorid","and"))
								.append(createOrderBy(orderby));
			Query query = session.createSQLQuery(sql.toString())
								.addScalar("fcustomerid")
								.addScalar("fid")
								.addScalar("fcusproductid")
								.addScalar("fpdtname")
								.addScalar("fpdtspec")
								.addScalar("famount")
								.addScalar("foutQty")
								.addScalar("farrivetimeString")
								.addScalar("faddressid")
								.addScalar("faddress")
								.addScalar("suppliername")
								.addScalar("fordernumber")
								.addScalar("fnumber")
								.addScalar("fcreatetime")
								.addScalar("fstate")
								.addScalar("fdescription")
								.addScalar("flinkman")
								.addScalar("flinkphone");
				setQueryParams2(query,queryParams);
				query.setResultTransformer(Transformers.aliasToBean(Deliverapply.class));
			   return  query.list();
			}
		});
		return pul;
	}
	
	
	
	@Override
	public PageModel<Deliverapply> findBoardBySql(final String where,final Object[] queryParams, final Map<String, String> orderby, final int pageNo,final int maxResult,final String...t_name) {
		final PageModel<Deliverapply> pageModel = new PageModel<Deliverapply>();
		pageModel.setPageNo(pageNo);
		pageModel.setPageSize(maxResult);
		this.getHibernateTemplate().execute(new HibernateCallback() {
			@Override
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				//三个月前数据  2016-3-21 lxx
				String tablename="t_ord_deliverapply_board_mv";
				if(t_name.length!=0&&t_name[0]!=null)
				{
					System.out.println("三个月前数据的表名更改为:"+t_name[0]);
					tablename=t_name[0];
				}
				StringBuilder sql = new StringBuilder().append(" select ")
//									.append(" mv.fcustomerid   as fcustomerid,")
									.append(" mv.fid 		   as fid,")
									.append(" mv.faddress as faddress,")
									.append(" mv._materialname  as fpdtname,")
									.append(" mv.famount       as famount,")
									.append(" mv.famountpiece  as famountpiece,")
									.append(" date_format(farrivetime,'%Y-%m-%d') as farrivetimeString,")
									.append(" mv.flabel as flabel, ")
									.append(" mv.fdescription fdescription,")
									.append(" mv._suppliername as suppliername,")
									.append(" mv.fstavetype as fstavetype,")
									.append(" mv.fseries  as fseries,")
									.append(" mv.fnumber       as fnumber,")
									.append(" mv.fboxmodel  as fboxmodel,")
									.append(" mv.fboxwidth     as fboxwidth,")
									.append(" mv.fboxheight    as fboxheight,")
									.append(" mv.fboxlength    as fboxlength,")
									.append(" mv.fmateriallength  as fmateriallength,")
									.append(" mv.fmaterialwidth   as fmaterialwidth,")
									.append(" mv.fvline       as fvline,")
									.append(" mv.fhline       as fhline,")
									.append(" mv.fstate        as fstate")
									.append(" FROM "+tablename+" mv ")
								//	.append(" INNER JOIN t_bd_usercustomer tus on tus.FCUSTOMERID = mv.fcustomerid")
								//	.append(" INNER JOIN t_sys_user tsy on tsy.fid = tus.FUSERID")
									.append(where == null ? "where 1=1 "+QueryFilterByUserofuser("mv.fcreatorid","and") : where+QueryFilterByUserofuser("mv.fcreatorid","and"))
									.append(createOrderBy(orderby));
				Query query = session.createSQLQuery(sql.toString())
//									.addScalar("fcustomerid")
									.addScalar("fid")
									.addScalar("faddress")
									.addScalar("fpdtname")
									.addScalar("famount")
									.addScalar("famountpiece")
									.addScalar("farrivetimeString")
									.addScalar("flabel")
									.addScalar("fdescription")
									.addScalar("suppliername")
									.addScalar("fstavetype")
									.addScalar("fseries")
									.addScalar("fnumber")
									.addScalar("fboxmodel")
									.addScalar("fboxwidth")
									.addScalar("fboxheight")
									.addScalar("fboxlength")
									.addScalar("fmateriallength")
									.addScalar("fmaterialwidth")
									.addScalar("fvline")
									.addScalar("fhline")
									.addScalar("fstate");
				setQueryParams2(query,queryParams);
				query.setResultTransformer(Transformers.aliasToBean(Deliverapply.class));
				List<Deliverapply> list = query.setFirstResult(getFirstResult(pageNo, maxResult)).setMaxResults(maxResult).list();
				sql = new StringBuilder().append("select count(*)")
									 .append(" FROM t_ord_deliverapply_board_mv mv ")
									// .append(" INNER JOIN t_bd_usercustomer tus on tus.FCUSTOMERID = mv.fcustomerid")
									//.append(" INNER JOIN t_sys_user tsy on tsy.fid = tus.FUSERID")
							         .append(where == null ? "where 1=1 "+QueryFilterByUserofuser("mv.fcreatorid","and") : where+QueryFilterByUserofuser("mv.fcreatorid","and"));
			   query = session.createSQLQuery(sql.toString());
			   setQueryParams2(query,queryParams);
			   pageModel.setTotalRecords(Integer.valueOf(query.uniqueResult().toString()));
		//	   pageModel.setTotalRecords(list.size()>=maxResult?500:(pageNo-1)*maxResult+list.size());  
			   pageModel.setList(list);
			   return null;
			}
		});
		return pageModel;
	}
	
	/**
	 * 获取 纸板要货申请，不查总数 纸板导出、订单详情界面用到
	 */
	public List<Deliverapply> getDeliverapplyBoards(final String where,final Object[] queryParams, final Map<String, String> orderby,final String...t_name) {
		List<Deliverapply> pul =(List<Deliverapply>) this.getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				String tablename="t_ord_deliverapply_board_mv";
				if(t_name.length!=0&&t_name[0]!=null)
				{
					tablename=t_name[0];
				}
				StringBuilder sql = new StringBuilder().append(" select ")
						.append(" mv.fid 		   as fid,")
						.append(" mv._materialname  as fpdtname,")
						.append(" mv.famount       as famount,")
						.append(" mv.famountpiece  as famountpiece,")
						.append(" date_format(farrivetime,'%Y-%m-%d') as farrivetimeString,")
						.append(" mv._suppliername as suppliername,")
						.append(" mv.fstavetype as fstavetype,")
						.append(" mv.fseries  as fseries,")
						.append(" mv.fnumber       as fnumber,")
						.append(" mv.fboxmodel  as fboxmodel,")
						.append(" mv.fboxwidth     as fboxwidth,")
						.append(" mv.fboxheight    as fboxheight,")
						.append(" mv.fboxlength    as fboxlength,")
						.append(" mv.fmateriallength  as fmateriallength,")
						.append(" mv.fmaterialwidth   as fmaterialwidth,")
						.append(" mv.fvline       as fvline,")
						.append(" mv.fhline       as fhline,")
						.append(" mv.fhformula1    as fhformula1,")
						.append(" mv.fhformula    as fhformula,")
						.append(" mv.fvformula    as fvformula,")
						.append(" mv.fdefine1    as fdefine1,")
						.append(" mv.fdefine2    as fdefine2,")
						.append(" mv.fdefine3   as fdefine3,")
						.append(" mv.flabel       as flabel,")
						.append(" mv.flinkman     as flinkman,")
						.append(" mv.flinkphone   as flinkphone,")
						.append(" mv.faddress     as faddress,")
						.append(" mv.fdescription  as fdescription,")
						.append(" mv.foutQty  as foutQty,")
						.append(" mv.fcreatetime  as fcreatetime,")
						.append(" mv.freceiptTime  as freceiptTime,")
						.append(" mv.fouttime  as fouttime,")
						.append(" mv.fplanid  as fplanid,")
						.append(" mv.fstate        as fstate")
						.append(" FROM "+tablename+" mv ")
						.append(" INNER JOIN t_bd_usercustomer tus on tus.FCUSTOMERID = mv.fcustomerid")
						.append(" INNER JOIN t_sys_user tsy on tsy.fid = tus.FUSERID")
						.append(where == null ? "where 1=1 "+QueryFilterByUserofuser("mv.fcreatorid","and") : where+QueryFilterByUserofuser("mv.fcreatorid","and"))
						.append(createOrderBy(orderby));
						Query query = session.createSQLQuery(sql.toString())
						.addScalar("fid")
						.addScalar("fpdtname")
						.addScalar("famount")
						.addScalar("famountpiece")
						.addScalar("farrivetimeString")
						.addScalar("suppliername")
						.addScalar("fstavetype")
						.addScalar("fseries")
						.addScalar("fnumber")
						.addScalar("fboxmodel")
						.addScalar("fboxwidth")
						.addScalar("fboxheight")
						.addScalar("fboxlength")
						.addScalar("fmateriallength")
						.addScalar("fmaterialwidth")
						.addScalar("fvline")
						.addScalar("fhline")
						.addScalar("fhformula1")
						.addScalar("fhformula")
						.addScalar("fvformula")
						.addScalar("fdefine1")
						.addScalar("fdefine2")
						.addScalar("fdefine3")
						.addScalar("flabel")
						.addScalar("flinkman")
						.addScalar("flinkphone")
						.addScalar("faddress")
						.addScalar("fdescription")
						.addScalar("foutQty")
						.addScalar("fcreatetime")
						.addScalar("freceiptTime")
						.addScalar("fouttime")
						.addScalar("fplanid")
						.addScalar("fstate");
			setQueryParams2(query,queryParams);
			query.setResultTransformer(Transformers.aliasToBean(Deliverapply.class));
			return query.list();
			}
		});
		return pul;
	}
	public String getCondition(String fidcls) throws Exception{
		if(StringUitl.isNullOrEmpty(fidcls)){
			throw new Exception("ID不存在！");
		}
		String condition;
		if(fidcls.split(",").length==1){
			condition = " = '"+fidcls+"' ";
		}else{
			condition = " in ('"+fidcls.replace(",", "','")+"') ";
		}
		return condition;
	}
	@Override
	public void ExecUpdateBoardStateToCreate(String fidcls) throws Exception{
		// TODO Auto-generated method stub
		Productdef pinfo;
		String pName;
		List<ProducePlan> plans;
		ProducePlan planInfo=null;
		Calendar cday;
		DateFormat dateFormat;
		String con = getCondition(fidcls);
		String sql = "select fid,fmaterialfid,farrivetime arrivetime,CONVERT(DATE_FORMAT(farrivetime,'%Y-%m-%d'),DATETIME) farrivetime from t_ord_deliverapply where fid" + con;
		List<HashMap<String, Object>> deliverapplys = this.QueryBySql(sql);
		for(HashMap<String, Object> deliverapply: deliverapplys){
			String farrivetime = deliverapply.get("arrivetime").toString();
			String fnumber = this.getNumber("t_ord_deliverapply","NB", 4, true);
			String productId = (String) deliverapply.get("fmaterialfid");
			Date arrivetime = (Date) deliverapply.get("farrivetime");
			pinfo=(Productdef)this.Query(Productdef.class, productId);
			pName = pinfo.getFname()+" /"+pinfo.getFlayer()+pinfo.getFtilemodelid();
			if(pinfo.getFeffect()==0){
				throw new Exception("材料“"+pName+"”已禁用，无法下单！");
			}
			plans=this.QueryByHql("from ProducePlan p where p.fproductid='"+productId+"' and p.fsupplierid='"+pinfo.getFsupplierid()+"'");
			if(plans!=null&&plans.size()>0){
				planInfo=plans.get(0);
			}
			try {
				cday = getFirstArrivetimeDate(planInfo,pinfo);
				if(new Integer(pinfo.getFnewtype())==2)//裱胶类型 默认+2
				{
					cday.add(Calendar.DAY_OF_MONTH, 1);
				}else if(pinfo.getFeffected()==0){
					cday.add(Calendar.DAY_OF_MONTH, -1);
				}
			} catch (Exception e) {
				throw new Exception("排产日期计算出错，请上报平台更改！");
			}
			if(arrivetime.before(cday.getTime())){
				dateFormat = new SimpleDateFormat("yyyy-MM-dd HH");
				cday.add(Calendar.DAY_OF_MONTH, 1);
				cday.set(Calendar.HOUR_OF_DAY, 0);
				if(pinfo.getFeffected()==0||pinfo.getFlayer()==1) cday.set(Calendar.HOUR_OF_DAY, 17);
				farrivetime = dateFormat.format(cday.getTime());
//					sql = "update t_ord_deliverapply set farrivetime = '"+dateFormat.format(cday.getTime())+"' where fid = '"+deliverapply.get("fid")+"'";
//					this.ExecBySql(sql);
			}
			sql = "update t_ord_deliverapply set fstate=0,fcreatetime=now(),farrivetime='"+farrivetime+"',fnumber='"+fnumber+"' where fstate=7 and fid = '"+deliverapply.get("fid")+"'";
			this.ExecBySql(sql);
		}
	}
	public  Calendar getFirstArrivetimeDate(ProducePlan planInfo,Productdef definfo)throws Exception 
	{
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		int whilecount=0;
		List<Supplierboardplanconfig> configlist=this.QueryByHql("  from Supplierboardplanconfig where fsupplierid='"+definfo.getFsupplierid()+"'");
		if(configlist.size()>0){
			if(new java.sql.Time(c.getTime().getTime()).toString().compareTo(configlist.get(0).getForderlasttime().toString())>=0)
			{
				c.add(Calendar.DAY_OF_MONTH, 1);
			}
		}
		if(definfo.getFeffected()==0&& new Integer(definfo.getFnewtype())!=2)//裱胶类型材料暂不分白夜班
		{
			c.add(Calendar.DAY_OF_MONTH, 1);
		}
		if(planInfo==null)
		{
			return c;
		}
		int currentday=c.get(Calendar.DAY_OF_MONTH);//当前日
		List<Integer> list=new ArrayList<Integer>();//存放开机或不开机时间
		if(planInfo.getFisweek()==0)//1 按周+不开机算；0为按开机时间算
		{
			int maxday=c.getActualMaximum(Calendar.DAY_OF_MONTH);//当月最大日期
			String[] produceDays = planInfo.getFday().split("_");
			for(String s: produceDays){
				list.add(Integer.valueOf(s));
			}
			 if(list.contains(currentday))
			 {
				return c;
			 }else
			 {
				 while(whilecount<4)
				 {
					 for(int i=0;i<list.size();i++)
					 {
						if(currentday<=list.get(i)&&list.get(i)<=maxday)
						{
							c.set(Calendar.DAY_OF_MONTH, list.get(i));//设置时间
							return c;
						}
					 }
					 c.add(Calendar.MONTH, 1);
					 currentday=1;
					 maxday=c.getActualMaximum(Calendar.DAY_OF_MONTH);
					 whilecount++;
				 }
			 }
		}else//按周
		{
			int currentweek=c.get(Calendar.DAY_OF_WEEK)-1;//0-6 当前星期几;c中存1-7 星期日-到星期六
			String fnoday = "_"+planInfo.getFnoday()+"_";
			String[] week= planInfo.getFweek().split("");
			List<Calendar> dayList = new ArrayList<>();
			Calendar calendar;
			for(String day: week){
				if("".equals(day)){
					continue;
				}
				calendar = Calendar.getInstance();
				calendar.setTime(c.getTime());
			//	calendar.setTime(new Date());
				calendar.add(Calendar.DAY_OF_MONTH, Integer.valueOf(day)-currentweek);
				dayList.add(calendar);
			}
			while(true){
				for(Calendar cr: dayList){
					if(cr.getTimeInMillis()>=c.getTimeInMillis()&& fnoday.indexOf("_"+cr.get(Calendar.DAY_OF_MONTH)+"_")==-1){
						return cr;
					}else{
						cr.add(Calendar.WEEK_OF_YEAR,1);
					}
				}
			}
		}
		throw new Exception("计算排产日出错！");
	
	}
	
	public void saveBoardSignleDeliverapply(Deliverapply deliverapply) throws Exception{
		try {
			DateFormat f = new SimpleDateFormat("yyyy-MM-dd");
			Productdef pinfo=(Productdef)this.Query(Productdef.class, deliverapply.getFmaterialfid());
			if(pinfo.getFeffect()==0){
				throw new Exception("此纸板材料已禁用，无法下单！");
			}
			List<ProducePlan> plans=this.QueryByHql("from ProducePlan p where p.fproductid='"+ deliverapply.getFmaterialfid()+"' and p.fsupplierid='"+deliverapply.getFsupplierid()+"'");
			ProducePlan planInfo=null;
			if(plans!=null&&plans.size()>0){
				planInfo=plans.get(0);
			}
			Calendar cday;
			String sql;
						
			try {
				cday = getFirstArrivetimeDate(planInfo,pinfo);
			} catch (Exception e) {
				throw new Exception("排产日期计算出错，请上报平台更改！");
			}
			String fristday=f.format(cday.getTime());
			if(new Integer(pinfo.getFnewtype())==2)
			{
				//裱胶生产周期48小时
				cday.add(Calendar.DAY_OF_MONTH, 1);//普默认+1
			}
			else if(pinfo.getFeffected()==0)//早班
			{
				cday.add(Calendar.DAY_OF_MONTH, -1);//普默认-1 早班取排产日当天
			}
			
			//通过修改属性文件  临时设置不排产日
			ArrayList nodaylist = new ArrayList();
			Properties prop = new Properties();
			InputStream in = this.getClass().getResourceAsStream("/boardsth.properties");
			//加载属性列表
			prop.load(in);
			Iterator<String> it=prop.stringPropertyNames().iterator();
			while (it.hasNext()) {
				String key = it.next();
				if(key.equals("notproductDate")){
					System.out.println(prop.getProperty(key));
					nodaylist = new ArrayList<String>(Arrays.asList(prop.getProperty(key).split(",")));
				}			
			}
			in.close();
			if(nodaylist==null||!nodaylist.contains(f.format(new Date())))//1-3下单交期不判断排产时间
			{
			if(deliverapply.getFarrivetime().before(cday.getTime())){
				throw new Exception("该材料:"+pinfo.getFname()+",首排产日为"+fristday+",请根据首排产选择配送时间");
			}
			}
			//放假
			Calendar caltime=Calendar.getInstance();  
			caltime.setTime(deliverapply.getFarrivetime());
			//送达时间 是放假停机，加一天
			while(nodaylist!=null&&nodaylist.contains(f.format(deliverapply.getFarrivetime())))
			{
				caltime.add(Calendar.DAY_OF_MONTH, 1);
				deliverapply.setFarrivetime(caltime.getTime());
			}		
			
			if(pinfo.getFeffected()==0 || pinfo.getFlayer()==1){ 
				caltime.set(Calendar.HOUR_OF_DAY, 17);
				deliverapply.setFarrivetime(caltime.getTime());		
			}
			if(StringUitl.isNullOrEmpty(deliverapply.getFmaterialfid())){
				throw new Exception("材料不能为空！");
			}
			if(StringUitl.isNullOrEmpty(deliverapply.getFaddressid())){
				throw new Exception("地址不能为空！");
			}
			if(StringUitl.isNullOrEmpty(deliverapply.getFarrivetime())){
				throw new Exception("配送时间不能为空！");
			}
			if(StringUitl.isNullOrEmpty(deliverapply.getFboxmodel())){
				throw new Exception("箱型不能为空！");
			}
			if(StringUitl.isNullOrEmpty(deliverapply.getFmateriallength()) || StringUitl.isNullOrEmpty(deliverapply.getFmaterialwidth())){
				throw new Exception("下料规格长度不能为空！");
			}
			if(StringUitl.isNullOrEmpty(deliverapply.getFstavetype())){
				throw new Exception("压线方式不能为空！");
			}
			if(StringUitl.isNullOrEmpty(deliverapply.getFseries())){
				throw new Exception("成型方式不能为空！");
			}
			if(deliverapply.getFamount()==null || deliverapply.getFamount()==0){
				throw new Exception("配送数量不能为空！");
			}
			//2015-06-29 片数控制
			if(deliverapply.getFseries().equals("连做")){
				deliverapply.setFamountpiece(deliverapply.getFamount());
			}
			if(deliverapply.getFseries().equals("不连做")){
				deliverapply.setFamountpiece(deliverapply.getFamount()*2);
			}

			BigDecimal materialLength = deliverapply.getFmateriallength();
			BigDecimal materialWidth = deliverapply.getFmaterialwidth();
			float mLength = materialLength.floatValue();
			float mWidth = materialWidth.floatValue();
			if(mLength<=0.5 || mWidth<=0.5){
				throw new Exception("纸板下料规格的长宽必须大于0.5！");
			}
			String boxModel = deliverapply.getFboxmodel().toString();			
			if("1".equals(boxModel)){
				if(StringUitl.isNullOrEmpty(deliverapply.getFhformula()+"") || StringUitl.isNullOrEmpty(deliverapply.getFvformula()+"") ||StringUitl.isNullOrEmpty(deliverapply.getFhformula1()+"")){
					throw new Exception("公式的接舌和系数不能为空！");
				}
			}
			if("2".equals(boxModel) ||"3".equals(boxModel) ||"4".equals(boxModel) ||"5".equals(boxModel) ||"6".equals(boxModel) ||"8".equals(boxModel)){
				if(StringUitl.isNullOrEmpty(deliverapply.getFhformula()+"")){
					throw new Exception("横向公式的接舌不能为空！");
				}
			}
			if("2".equals(boxModel) ||"3".equals(boxModel) ||"4".equals(boxModel) ||"5".equals(boxModel) ||"8".equals(boxModel)){
				if(StringUitl.isNullOrEmpty(deliverapply.getFvformula()+"")){
					throw new Exception("纵向公式的系数不能为空！");
				}
			}
			
			BigDecimal boxLength = deliverapply.getFboxlength();
			BigDecimal boxWidth = deliverapply.getFboxwidth();
			BigDecimal boxHeight = deliverapply.getFboxheight();
			float bLength = boxLength!=null?boxLength.floatValue():0;
			float bWidth = boxWidth!=null?boxWidth.floatValue():0;
			float bHeight = boxHeight!=null?boxHeight.floatValue():0;
			if(!"不压线".equals(deliverapply.getFstavetype())&&!"0".equals(boxModel)){
				if(bHeight==0 || bWidth==0 || bLength==0){
					throw new Exception("纸箱规格不能为空！");
				}
			}else if("不压线".equals(deliverapply.getFstavetype()) && deliverapply.getFseries().equals("不连做")){
				throw new Exception("不压线的成型方式只能为连做！");
			}
			if(bLength!=0){
				if("1".equals(boxModel)){
					if(bLength<5 || bWidth<5 || bHeight<5){
						throw new Exception("普通箱型规格过小,请联系制造商！");
					}
				}else{
					if(bLength<1 || bWidth<1 || bHeight<1){
						throw new Exception("所选箱型规格过小,请联系制造商！");
					}
				}
				if(bWidth-bLength>0.5){
					throw new Exception("纸箱规格的宽度不能大于长度，请更改！");
				}
			}
			if(boxLength==null){
				deliverapply.setFboxlength(BigDecimal.ZERO);
			}
			if(boxWidth==null){
				deliverapply.setFboxwidth(BigDecimal.ZERO);
			}
			if(boxHeight==null){
				deliverapply.setFboxheight(BigDecimal.ZERO);
			}
			String fid = deliverapply.getFid();
			if(!StringUitl.isNullOrEmpty(fid)){
				sql = "select fstate from t_ord_deliverapply where fid = '"+fid+"'";
				List<HashMap<String, Object>> list = this.QueryBySql(sql);
				if(list.size()==0){
					throw new Exception("订单ID错误！");
				}
				String state = list.get(0).get("fstate")+"";
				if(!"0".equals(state) && !"7".equals(state)){
					throw new Exception("已接收订单无法更改！");
				}
			}
			if(StringUitl.isNullOrEmpty(fid)){
				deliverapply.setFid(this.CreateUUid());
				deliverapply.setFcreatetime(new Date());
				deliverapply.setFnumber(this.getNumber("t_ord_deliverapply", "NB", 4, true));
				deliverapply.setFiscreate(0);
			}
			deliverapply.setFupdatetime(new Date());
			deliverapply.setFboxtype(1);
			deliverapply.setFtype("0");
			if(StringUitl.isNullOrEmpty(deliverapply.getFaddressid())){
				throw new Exception("地址不能为空！");
			}
			sql = "select fname,flinkman,fphone from t_bd_address where fid = '"+deliverapply.getFaddressid()+"'";
			List<HashMap<String, Object>> addressList = this.QueryBySql(sql);
			if(addressList.size()==0){
				throw new Exception("地址ID不存在！");
			}
			deliverapply.setFaddress(addressList.get(0).get("fname")+"");
			deliverapply.setFlinkman(addressList.get(0).get("flinkman")+"");
			deliverapply.setFlinkphone(addressList.get(0).get("fphone")+"");
			//构造公式匹配eas产品
			if(!deliverapply.getFstavetype().equals("不压线")){
				createStaveexp(deliverapply);
			}
			//2016-3-30  by les 其它箱型   压线是两位小数控制，否则和前台落料不一致
			if(deliverapply.getFboxmodel()==0 & !deliverapply.getFstavetype().equals("不压线")){
				ScriptEngine engine=new ScriptEngineManager().getEngineByName("JavaScript"); 					
				try {
					Object o = engine.eval(deliverapply.getFvline());
					BigDecimal result= new BigDecimal(o.toString()); 
					if(deliverapply.getFmaterialwidth().compareTo(result)!=0){
						deliverapply.setFmaterialwidth(result);
					}
				} catch (ScriptException e) {
					e.printStackTrace();
				}					
			}
			this.saveOrUpdate(deliverapply);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}
	
	public static void createStaveexp(Deliverapply deliverapply){
		BigDecimal hformula = BigDecimal.ZERO;
		BigDecimal hformula1 = BigDecimal.ZERO;
		BigDecimal vformula = BigDecimal.ZERO;
		int boxmodel = deliverapply.getFboxmodel();
		if(!StringUitl.isNullOrEmpty(deliverapply.getFhformula())){
			hformula = deliverapply.getFhformula();
		}
		if(!StringUitl.isNullOrEmpty(deliverapply.getFhformula1())){
			hformula1 = deliverapply.getFhformula1();
		}
		if(!StringUitl.isNullOrEmpty(deliverapply.getFvformula())){
			vformula = deliverapply.getFvformula();
		}
		
		//横向公式构建
		String hstaveexp = "";
		if(deliverapply.getFseries().equals("不连做")){
			hstaveexp = "[x]+[l]+[w-x1]";
		}
		else{
			hstaveexp = "[x]+[l]+[w]+[l]+[w-x1]";
		}
	   	if(hformula1.compareTo(BigDecimal.ZERO) != 0){
    		hstaveexp = hstaveexp.replace("x1", hformula1+"");
    	}
    	else{
    		hstaveexp = hstaveexp.replace("[w-x1]", "[w]");
    	}
    	if(hformula.compareTo(BigDecimal.ZERO) != 0){
    		hstaveexp = hstaveexp.replace("x",hformula+"");
    	}
    	else{
    		hstaveexp = hstaveexp.replace("[x]+", "");
    	}
    	hstaveexp = hstaveexp.replaceAll("\\.0\\]", "]");
    	switch(boxmodel){
    	 case 1 : 
    	 case 2 : 
    	 case 3 :
    	 case 4 : 
    	 case 5 : 
    	 case 6 : 
    	 case 8 : deliverapply.setFhstaveexp(hstaveexp);break;
    	 case 7 : deliverapply.setFhstaveexp("[h]+[l]+[h]");break;
    	 }
		//纵向公式构建
		String vstaveexp = "";
		switch(boxmodel){		
			//普通
			case 1: 
				vstaveexp = "[(w+y)/2]+[h]+[(w+y)/2]";
				break;
			//全包
			case 2:  
				vstaveexp = "[w-y]+[h]+[w-y]";
				break;
			//半包
			case 3:
				vstaveexp = "[w]+[h]+[y]";
				break;
			//有底无盖
			case 4 :
				vstaveexp = "[h]+[(w+y)/2]";
				break;
			//有盖无底
			case 5 :
				vstaveexp = "[(w+y)/2]+[h]+[0]";
				break;
			//围框
			case 6 :
				vstaveexp = "[h]";
				break;
			//天地盖	
			case 7 :
				vstaveexp = "[h]+[w]+[h]";
				break;		
			//立体箱
			case 8 :
				BigDecimal define1 = BigDecimal.ZERO;
				BigDecimal define2 = BigDecimal.ZERO;
				BigDecimal define3 = BigDecimal.ZERO;
				if(!StringUitl.isNullOrEmpty(deliverapply.getFdefine1())){
					define1 = deliverapply.getFdefine1();
				}
				if(!StringUitl.isNullOrEmpty(deliverapply.getFdefine2())){
					define2 = deliverapply.getFdefine2();
				}
				if(!StringUitl.isNullOrEmpty(deliverapply.getFdefine3())){
					define3 = deliverapply.getFdefine3();
				}
				vstaveexp = "[(w-y1)/2]+[y2]+[y3]+[h]+[(w+y)/2]";
//				define1 = deliverapply.getFboxwidth().add(vformula).divide(new BigDecimal("2")).subtract(define2.add(define3));
//				define1 = deliverapply.getFboxwidth().subtract(define1.multiply(new BigDecimal("2")));
				define1 = define2.add(define3).multiply(new BigDecimal("2")).subtract(vformula);
				deliverapply.setFdefine1(deliverapply.getFboxwidth().subtract(define1).divide(new BigDecimal("2")));
				vstaveexp = vstaveexp.replace("y1", define1+"").replace("y2", define2+"").replace("y3", define3+"");
		}
		vstaveexp = vstaveexp.replace("y", vformula+"");
		//vstaveexp = vstaveexp.replaceAll("w\\+0\\)|w-0\\)", "w");
		//vstaveexp = vstaveexp.replaceAll("w[+-]0(\\.0)?", "w");
		// $2匹配 第二个()中的值
		vstaveexp = vstaveexp.replaceAll("w[+-]0(\\.0)?([\\)\\]])", "w$2");
		//有底无盖特殊处理保证eas选到产品
		if(boxmodel==4){
			vstaveexp = vstaveexp.replace("[h]+[(w)/2]", "[h]+[w/2]");
		}
		deliverapply.setFvstaveexp(vstaveexp);
	}

	@Override
	public void saveCustBoardFormula(Deliverapply deliverapply) {
		CustBoardFormula obj = new CustBoardFormula();
		short layer = Short.valueOf(deliverapply.getLayer());
		if("不压线".equals(deliverapply.getFstavetype())){
			return;
		}
		obj.setFlayer(layer);
		obj.setFid(this.CreateUUid());
		obj.setFboxmodel((short)(deliverapply.getFboxmodel()+0));
		obj.setFcustomerid(deliverapply.getFcustomerid());
		obj.setFdefine1(deliverapply.getFdefine1().floatValue());
		obj.setFdefine2(deliverapply.getFdefine2().floatValue());
		obj.setFdefine3(deliverapply.getFdefine3().floatValue());
		obj.setFhformula(deliverapply.getFhformula().floatValue());
		obj.setFhformula1(deliverapply.getFhformula1().floatValue());
		obj.setFvformula(deliverapply.getFvformula().floatValue());
		String sql = "delete from t_ord_custboardformula where fcustomerid='"+obj.getFcustomerid()+"' and fboxmodel="+obj.getFboxmodel()+" and flayer="+obj.getFlayer();
		this.ExecBySql(sql);
		this.saveOrUpdate(obj);
	}

	@Override
	public void saveCustBoardLabel(Deliverapply deliverapply) {
		String customerid = deliverapply.getFcustomerid();
		String flabel = deliverapply.getFlabel();
		flabel = flabel==null?flabel:flabel.trim();
		if(StringUitl.isNullOrEmpty(flabel)){
			return;
		}
		CustBoardLabel obj = null;
		String hql = "from CustBoardLabel where fcustomerid='"+customerid+"' and fname='"+flabel+"'";
		List<CustBoardLabel> list = this.QueryByHql(hql);
		if(list.size()>0){
			obj = (CustBoardLabel) list.get(0);
		}
		if(obj==null){
			obj = new CustBoardLabel();
			obj.setFid(this.CreateUUid());
			obj.setFcustomerid(customerid);
			obj.setFname(flabel);
		}
		obj.setFcreatetime(new Date());
		this.saveOrUpdate(obj);
	}
	
}
