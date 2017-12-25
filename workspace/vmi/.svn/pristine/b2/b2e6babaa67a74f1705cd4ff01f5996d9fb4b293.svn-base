package Com.Controller.order;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.commons.lang.StringUtils;
import org.hibernate.util.StringHelper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import Com.Base.Util.DJException;
import Com.Base.Util.ExcelUtil;
import Com.Base.Util.JsonUtil;
import Com.Base.Util.ListResult;
import Com.Base.Util.mySimpleUtil.MySimpleToolsZ;
import Com.Dao.order.IOrderappraiseDao;
import Com.Entity.System.Useronline;
import Com.Entity.order.Orderappraise;

@Controller
public class OrderappraiseController {
	@Resource
	IOrderappraiseDao orderappraiseDao;
	@RequestMapping("getOrderAppraiseListByfid")
	public void getOrderAppraiseListByfid(HttpServletRequest request,HttpServletResponse response) throws IOException{
		String fid = request.getParameter("fid");
		try {
			String sql="select o.fid,o.fcreatorid,o.fcreatetime,o.fupdateuserid, o.fupdatetime,o.fcustomerid,fdeliverappraise,fqualityappraise,fserviceappraise,fmultipleappraise,o.fdescription,o.fappraiseman,o.fappraisetime ,o.fdeliverorderid fdeliverorderid_fid ,o.fordernumber fdeliverorderid_fnumber,o.fordernumber,o.fsupplierid fsupplierId_fid,s.fname fsupplierId_fname,o.fordertype,o.ftype from t_ord_orderappraise o left join t_sys_supplier s on s.fid=o.fsupplierid  where o.fid='"+fid+"'";
			List result = orderappraiseDao.QueryBySql(sql);
			response.getWriter().write(JsonUtil.result(true, "","",result));
		} catch (DJException e) {
			// TODO: handle exception
			response.getWriter().write(JsonUtil.result(false, e.getMessage(),"",""));
		}
	}
	@RequestMapping("saveOrUpdateOrderAppraise")
	public void saveOrUpdateOrderAppraise(HttpServletRequest request,HttpServletResponse response) throws IOException{
		String userid = ((Useronline) request.getSession().getAttribute(
				"Useronline")).getFuserid();
		try {
			Orderappraise f = (Orderappraise)request.getAttribute("Orderappraise");
			if(StringHelper.isEmpty(f.getFid()))
			{
				f.setFcreatetime(new Date());
				f.setFcreatorid(userid);
				f.setFid(orderappraiseDao.CreateUUid());
				f.setFappraisetime(new Date());
			}
				f.setFupdatetime(new Date());
				f.setFupdateuserid(userid);
				f.setFappraiseman(userid);
			
			orderappraiseDao.saveOrUpdate(f);
			response.getWriter().write(JsonUtil.result(true,"保存成功","",""));
		} catch (DJException e) {
			// TODO: handle exception
			response.getWriter().write(JsonUtil.result(false,e.getMessage(),"",""));
		}
	}
	@RequestMapping("getDeliverorderByfidForappraise")
	public void getDeliverorderByfidForappraise(HttpServletRequest request,HttpServletResponse response) throws IOException{
		String fid = request.getParameter("fid");
		try {
			String sql ="select  d.fid fdeliverorderid_fid ,d.fnumber fdeliverorderid_fnumber,d.fsupplierid fsupplierid_fid,s.fname fsupplierid_fname ,d.fcustomerid,d.fboxtype fordertype ,0 ftype   from t_ord_deliverorder d left join t_sys_supplier s on s.fid=d.fsupplierid  where d.fid='"+fid+"'";
			ListResult result = orderappraiseDao.QueryFilterList(sql, request);
			if(result.getData().size()==0){
			sql ="select  e.fid fdeliverorderid_fid,d.fnumber fdeliverorderid_fnumber,d.fsupplierid fsupplierid_fid,s.fname fsupplierid_fname ,d.fcustomer fcustomerid,0 fordertype, 1 ftype FROM t_ftu_saledeliverentry  e inner JOIN t_ftu_saledeliver d ON e.fparentid=d.fid left JOIN t_sys_supplier s ON s.fid=d.fsupplierid where e.fid='"+fid+"'";
			result = orderappraiseDao.QueryFilterList(sql, request);
			}
			response.getWriter().write(JsonUtil.result(true, "",result));
		} catch (DJException e) {
			// TODO: handle exception
			response.getWriter().write(JsonUtil.result(false, e.getMessage(),"",""));
		}
	}

	@RequestMapping("/GetorderParisesList")
	public String GetorderParisesList(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		reponse.setCharacterEncoding("utf-8");
	String sql="SELECT o.fid,o.fordertype fboxtype,c.fname fcustomername,s.fname fsuppliername,o.fordernumber delivernumber,o.fdeliverappraise,o.fqualityappraise,o.fserviceappraise,o.fmultipleappraise,o.fdescription,u.fname fparisename,date_format(fappraisetime, '%Y-%m-%d %H:%i') fappraisetime,date_format(o.fupdatetime, '%Y-%m-%d %H:%i') fupdatetime"
		+" FROM t_ord_orderappraise o"
		+" left join t_sys_supplier s on s.fid=o.fsupplierid"
		+" left join t_bd_customer c on c.fid=o.fcustomerid"
	//	+" left join t_ord_deliverorder  d on d.fid=o.fdeliverorderid"
		+" left join t_sys_user u on u.fid=o.fappraiseman where 1=1 ";
		sql=sql +orderappraiseDao.QueryFilterByUser(request, "o.fcustomerid", null);
		String Defaultfilter = request.getParameter("Defaultfilter");
		if (StringUtils.isEmpty(Defaultfilter)||"[]".equals(Defaultfilter)||JSONArray.fromObject(Defaultfilter).size()==1)//没有设置过滤或者只设置了按制造商过滤没有按时间过滤时，默认本月
		{
			sql=sql+" and date_format(o.fappraisetime,'%Y-%m')=date_format(now(),'%Y-%m') ";
		}
//		if(request.getParameter("filter")==null)
		ListResult result;
		try {
			result = orderappraiseDao.QueryFilterList(sql, request);
			reponse.getWriter().write(JsonUtil.result(true, "", result));
		} catch (DJException e) {
			reponse.getWriter().write(
					JsonUtil.result(false, e.getMessage(), "", ""));
		}

		return null;
	}
	
	@RequestMapping("/GetAverageorderParisesOfCurMothData")
	public String GetAverageorderParisesOfCurMothData(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		String begindate=request.getParameter("beginTime");
		String datestring="";
		if(begindate==null)
		{
			datestring=" and date_format(fappraisetime,'%Y-%m')=date_format(now(),'%Y-%m')";
		}else
		{
			datestring= MySimpleToolsZ.getMySimpleToolsZ().buildDateBetweenSQLFragment(request);
		}
		String customersql=""+orderappraiseDao.QueryFilterByUser(request, "o.fcustomerid", null);
		String sql="SELECT distinct s.fname fsuppliername,ifnull(r.faveragedeliver,0) faveragedeliver,ifnull(r.faveragequality,0) faveragequality,ifnull(r.faverageservice,0) faverageservice,ifnull(r.faveragemultiple,0) faveragemultiple ,o.fsupplierid  FROM t_pdt_productreqallocationrules o left join (" +
			"select fsupplierid,sum(fdeliverappraise)/count(fdeliverappraise) faveragedeliver,sum(fqualityappraise)/count(fqualityappraise) faveragequality,sum(fserviceappraise)/count(fserviceappraise) faverageservice,sum(fmultipleappraise)/count(fmultipleappraise) faveragemultiple"
			+" from t_ord_orderappraise o  "
			+" where 1=1 "+datestring+customersql;
		sql+=" group by fsupplierid ) r on o.fsupplierid=r.fsupplierid " +
			"left join t_sys_supplier s on s.fid=o.fsupplierid where 1=1 "+customersql;
		ListResult result;
		reponse.setCharacterEncoding("utf-8");
		try {
			result = orderappraiseDao.QueryFilterList(sql, request);
			reponse.getWriter().write(JsonUtil.result(true, "", result));
		} catch (DJException e) {
			reponse.getWriter().write(
					JsonUtil.result(false, e.getMessage(), "", ""));
		}

		return null;
	}
	
	@RequestMapping(value = "/OrderParisesListtoexcel")
	public String OrderParisestoexcel(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		try {
		
			String sql="SELECT case o.fordertype when 1 then '纸板订单' else '纸箱订单'end  订单类型,c.fname 客户名称,s.fname 制造商名称,o.fordernumber 配送单号,o.fdeliverappraise 交期满意度,o.fqualityappraise 质量满意度,o.fserviceappraise 服务满意度,o.fmultipleappraise 综合满意度,ifnull(o.fdescription,'') 描述 ,u.fname 评价人,date_format(fappraisetime, '%Y-%m-%d %H:%i') 评价时间,date_format(o.fupdatetime, '%Y-%m-%d %H:%i') 修改时间"
					+" FROM t_ord_orderappraise o"
					+" left join t_sys_supplier s on s.fid=o.fsupplierid"
					+" left join t_bd_customer c on c.fid=o.fcustomerid"
					+" left join t_ord_deliverorder  d on d.fid=o.fdeliverorderid"
					+" left join t_sys_user u on u.fid=o.fappraiseman where 1=1 ";
			sql=sql +orderappraiseDao.QueryFilterByUser(request, "o.fcustomerid", null);
			String Defaultfilter = request.getParameter("Defaultfilter");
			if (StringUtils.isEmpty(Defaultfilter)||"[]".equals(Defaultfilter)||JSONArray.fromObject(Defaultfilter).size()==1)
			{
				sql=sql+" and date_format(o.fappraisetime,'%Y-%m')=date_format(now(),'%Y-%m') ";
			}
			ListResult result= orderappraiseDao.QueryFilterList(sql, request);
			ExcelUtil.toexcel(reponse,result,"订单评价明细表");
		} catch (DJException e) {
			// TODO Auto-generated catch block
			reponse.getWriter().write(
					JsonUtil.result(false, e.getMessage(), "", ""));
		}

		return null;

	}
	
	
	@RequestMapping("/GetorderParisesSupplierList")
	public String GetorderParisesSupplierList(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		reponse.setCharacterEncoding("utf-8");
	String sql="SELECT o.fid,o.fordertype fboxtype,c.fname fcustomername,s.fname fsuppliername,o.fordernumber delivernumber,o.fdeliverappraise,o.fqualityappraise,o.fserviceappraise,o.fmultipleappraise,o.fdescription,u.fname fparisename,date_format(fappraisetime, '%Y-%m-%d %H:%i') fappraisetime,date_format(o.fupdatetime, '%Y-%m-%d %H:%i') fupdatetime"
		+" FROM t_ord_orderappraise o"
		+" left join t_sys_supplier s on s.fid=o.fsupplierid"
		+" left join t_bd_customer c on c.fid=o.fcustomerid"
		//+" left join t_ord_deliverorder  d on d.fid=o.fdeliverorderid"
		+" left join t_sys_user u on u.fid=o.fappraiseman where 1=1 ";
		sql=sql +orderappraiseDao.QueryFilterByUser(request, null, "o.fsupplierid");
		String Defaultfilter = request.getParameter("Defaultfilter");
		if (StringUtils.isEmpty(Defaultfilter)||"[]".equals(Defaultfilter)||JSONArray.fromObject(Defaultfilter).size()==1)
		{
			sql=sql+" and date_format(o.fappraisetime,'%Y-%m')=date_format(now(),'%Y-%m') ";
		}
		ListResult result;
		try {
			result = orderappraiseDao.QueryFilterList(sql, request);
			reponse.getWriter().write(JsonUtil.result(true, "", result));
		} catch (DJException e) {
			reponse.getWriter().write(
					JsonUtil.result(false, e.getMessage(), "", ""));
		}

		return null;
	}
	
	@RequestMapping(value = "/OrderParisesSupplierListtoexcel")
	public String OrderParisesSupplierListtoexcel(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		try {
		
			String sql="SELECT case o.fordertype when 1 then '纸板订单' else '纸箱订单'end  订单类型,c.fname 客户名称,s.fname 制造商名称,o.fordernumber 配送单号,o.fdeliverappraise 交期满意度,o.fqualityappraise 质量满意度,o.fserviceappraise 服务满意度,o.fmultipleappraise 综合满意度,ifnull(o.fdescription,'') 描述 ,u.fname 评价人,date_format(fappraisetime, '%Y-%m-%d %H:%i') 评价时间,date_format(o.fupdatetime, '%Y-%m-%d %H:%i') 修改时间"
					+" FROM t_ord_orderappraise o"
					+" left join t_sys_supplier s on s.fid=o.fsupplierid"
					+" left join t_bd_customer c on c.fid=o.fcustomerid"
				//	+" left join t_ord_deliverorder  d on d.fid=o.fdeliverorderid"
					+" left join t_sys_user u on u.fid=o.fappraiseman where 1=1 ";
			sql=sql +orderappraiseDao.QueryFilterByUser(request, null, "o.fsupplierid");
			String Defaultfilter = request.getParameter("Defaultfilter");
			if (StringUtils.isEmpty(Defaultfilter)||"[]".equals(Defaultfilter)||JSONArray.fromObject(Defaultfilter).size()==1)
			{
				sql=sql+" and date_format(o.fappraisetime,'%Y-%m')=date_format(now(),'%Y-%m') ";
			}
			ListResult result= orderappraiseDao.QueryFilterList(sql, request);
			ExcelUtil.toexcel(reponse,result,"订单评价明细表");
		} catch (DJException e) {
			// TODO Auto-generated catch block
			reponse.getWriter().write(
					JsonUtil.result(false, e.getMessage(), "", ""));
		}

		return null;

	}
	
	
	@RequestMapping("/GetAverageorderParisesSupplierOfCurMothData")
	public String GetAverageorderParisesSupplierOfCurMothData(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		
		String begindate=request.getParameter("beginTime");
		String datestring="";
		if(begindate==null)
		{
			datestring=" and date_format(fappraisetime,'%Y-%m')=date_format(now(),'%Y-%m')";
		}else
		{
			datestring= MySimpleToolsZ.getMySimpleToolsZ().buildDateBetweenSQLFragment(request);
		}
		String suppliersql=""+orderappraiseDao.QueryFilterByUser(request, null, "o.fsupplierid");
		String sql="SELECT distinct c.fname fcustomername,ifnull(r.faveragedeliver,0) faveragedeliver,ifnull(r.faveragequality,0) faveragequality,ifnull(r.faverageservice,0) faverageservice,ifnull(r.faveragemultiple,0) faveragemultiple ,o.fcustomerid  FROM t_pdt_productreqallocationrules o left join (" +
			"select fcustomerid,sum(fdeliverappraise)/count(fdeliverappraise) faveragedeliver,sum(fqualityappraise)/count(fqualityappraise) faveragequality,sum(fserviceappraise)/count(fserviceappraise) faverageservice,sum(fmultipleappraise)/count(fmultipleappraise) faveragemultiple"
			+" from t_ord_orderappraise o  "
			+" where 1=1 "+datestring+suppliersql;
		sql+=" group by fcustomerid ) r on o.fcustomerid=r.fcustomerid " +
			"left join t_bd_customer c on c.fid=o.fcustomerid where 1=1 "+suppliersql;
		ListResult result;
		reponse.setCharacterEncoding("utf-8");
		try {
			result = orderappraiseDao.QueryFilterList(sql, request);
			sql="select '所有客户'fcustomername,ifnull(sum(fdeliverappraise) / count(fdeliverappraise),0) faveragedeliver,ifnull(sum(fqualityappraise) / count(fqualityappraise),0) faveragequality,ifnull(sum(fserviceappraise) / count(fserviceappraise),0) faverageservice,ifnull(sum(fmultipleappraise) / count(fmultipleappraise),0) faveragemultiple,'' fcustomerid "
					+" from t_ord_orderappraise o where exists(select s.fsupplierid,s.fcustomerid from t_pdt_productreqallocationrules s where s.fcustomerid=o.fcustomerid and s.fsupplierid=o.fsupplierid) "+datestring +suppliersql;
			List<HashMap<String,Object>> list=orderappraiseDao.QueryBySql(sql);
			result.getData().add(0,list.get(0));
			result.setTotal(result.getTotal()+1);
			//			result.getData().add(0, element)
			reponse.getWriter().write(JsonUtil.result(true, "", result));
		} catch (DJException e) {
			reponse.getWriter().write(
					JsonUtil.result(false, e.getMessage(), "", ""));
		}

		return null;
	}
	
	
}
