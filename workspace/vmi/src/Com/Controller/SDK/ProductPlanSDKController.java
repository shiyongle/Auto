package Com.Controller.SDK;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import Com.Base.Util.DJException;
import Com.Base.Util.JsonUtil;
import Com.Base.Util.ListResult;
import Com.Dao.System.IProductdefDao;
import Com.Dao.order.IProductPlanDao;
import Com.Dao.order.ISaleOrderDao;

@Controller
public class ProductPlanSDKController {
	Logger log = LoggerFactory.getLogger(ProductPlanSDKController.class);
	@Resource
	private IProductPlanDao productPlanDao;
	@Resource
	private IProductdefDao productdefDao;
	@Resource
	private ISaleOrderDao saleOrderDao;
	/**
	 * 订单信息接口
	 * @param request
	 * @param reponse
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/getOrderInfoSDK")
	public String getOrderInfoSDK(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		reponse.setCharacterEncoding("utf-8");
		try {
			//String sql = "select cus.fname as fcustomername,productplan.fdescription,productplan.fid orderid,date_format(productplan.farrivetime,'%Y-%m-%d %T') farrivetime,productplan.famount,ifnull(pdef.feasproductid,'') feasproductid,pdef.fid fvmiproductid,productplan.fnumber,concat(pdef.fname,'(',ifnull(pdef.fversion,''),')') as fname,productplan.fcustomerid,IFNULL(pdef.fboardlength, 0) fboardlength,IFNULL(pdef.fboardwidth, 0) fboardwidth,IFNULL(productplan.fboxlength, 0) fboxlength,IFNULL(productplan.fboxwidth, 0) fboxwidth,IFNULL(productplan.fboxheight, 0) fboxheight,ifnull(productplan.feasorderentryid,'') feasorderentryid,ifnull(productplan.fschemedesignid,'') fschemedesignid,IFNULL(sd.ftemplateproduct,'') ftemplateproduct,IFNULL(sd.ftemplatenumber,'') ftemplatenumber from t_ord_productplan productplan inner join t_pdt_productdef pdef on pdef.fid=productplan.fproductdefid left join t_bd_customer cus on cus.fid=productplan.fcustomerid LEFT JOIN t_ord_schemedesign sd ON productplan.fschemedesignid = sd.fid where ifnull(productplan.faffirmed, 0) = 1 and productplan.fimportEAS = 1 and ifnull(productplan.fissync,0) = 0 and ifnull(productplan.fcloseed, 0) = 0 "+productPlanDao.QueryFilterByUser(request, "productplan.fcustomerid", "productplan.fsupplierid"); 
			String sql = "select cus.fname as fcustomername,productplan.fdescription,productplan.fid orderid,date_format(productplan.farrivetime, '%Y-%m-%d %T') farrivetime,productplan.famount,ifnull(pdef.feasproductid, '') feasproductid,pdef.fid fvmiproductid,productplan.fboxtype,productplan.fnumber, case when productplan.fschemedesignid is null or productplan.fschemedesignid='' then pdef.fnumber else IFNULL(sd.ftemplatenumber, '') end fproductnumber,case when productplan.fschemedesignid is null or productplan.fschemedesignid='' then ";
			sql=sql+"  case when productplan.fboxtype=0 then concat(pdef.fname, '(', ifnull(pdef.fversion, ''), ')') else ifnull(concat(pdef.fname,'-',pdef.ftilemodelid),'无材料') end else IFNULL(sd.ftemplateproduct, '') end as fname,productplan.fcustomerid,case when productplan.fboxtype=0 then IFNULL(pdef.fboardlength, 0) else IFNULL(dap.fmateriallength,0) end fboardlength,case when productplan.fboxtype=0 then IFNULL(pdef.fboardwidth, 0) else IFNULL(dap.fmaterialwidth,0) end fboardwidth,case when productplan.fboxtype=0 then IFNULL(productplan.fboxlength, 0) else IFNULL(dap.fboxlength,0) end fboxlength,case when productplan.fboxtype=0 ";
			sql=sql+" then IFNULL(productplan.fboxwidth, 0) else IFNULL(dap.fboxwidth,0) end fboxwidth,case when productplan.fboxtype=0 then IFNULL(productplan.fboxheight, 0) else IFNULL(dap.fboxheight,0) end fboxheight,ifnull(productplan.feasorderentryid, '') feasorderentryid,ifnull(productplan.fschemedesignid, '') fschemedesignid,case when dap.fboxmodel=1 then '普通' when dap.fboxmodel=2 then '全包' when dap.fboxmodel=3 then '半包' when dap.fboxmodel=4 then '有底无盖'  when dap.fboxmodel=5 then '有盖无底'   when dap.fboxmodel=6 then '围框'   when dap.fboxmodel=7 then '天地盖'   when dap.fboxmodel=8 then '立体箱'   when dap.fboxmodel=0 then '其它'  else '箱型错误' end fboxmodel,dap.fseries,dap.fstavetype,case when dap.fvline='无' then '' else dap.fvline end fvformula,case when dap.fhline ='无' then '' else dap.fhline end fhformula,pdef.ftilemodelid,dap.fvstaveexp,dap.fhstaveexp,pdef.flayer,pdef.fnewtype ";
			sql=sql+"from t_ord_productplan productplan inner join t_pdt_productdef pdef ON pdef.fid = productplan.fproductdefid left join t_bd_customer cus ON cus.fid = productplan.fcustomerid LEFT JOIN t_ord_schemedesign sd ON productplan.fschemedesignid = sd.fid left join t_ord_deliverapply dap on dap.fid=productplan.fdeliverapplyid where productplan.faffirmed = 1 and productplan.fimportEAS = 1 AND (productplan.fissync = 0 or productplan.fissync is null) AND (productplan.fcloseed=0 or productplan.fcloseed is null)";
			sql=sql+productPlanDao.QueryFilterByUser(request, "productplan.fcustomerid", "productplan.fsupplierid");
			ListResult result=productPlanDao.QueryFilterList(sql, request);
			reponse.getWriter().write(JsonUtil.result(true, "", result));
		} catch (DJException e) {
			reponse.getWriter().write(
					JsonUtil.result(false, e.getMessage(), "", ""));
		}
		return null;
	}
	
	/*
	 * 成品箱数据统计;
	 */
	@RequestMapping(value = "/getStatisticsOfProductplan")
	public void getStatisticsOfProductplan(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException{
		try {
			String fsupplierid=productPlanDao.QueryFilterByUser(request,null,"fsupplierid");
			String sql = "SELECT COUNT(DISTINCT fcustomerid) fcustomercount,COUNT(fid) ordercount, ifnull(SUM(famount),0)  totalfamount,1 datetype FROM t_ord_productplan WHERE   TO_DAYS(fcreatetime) = TO_DAYS(NOW()) "+fsupplierid
					+" UNION SELECT COUNT(DISTINCT fcustomerid) fcustomercount,COUNT(fid) ordercount, ifnull(SUM(famount),0)  totalfamount,2 datetype FROM t_ord_productplan WHERE TO_DAYS(NOW())-TO_DAYS(fcreatetime) = 1  "+fsupplierid
					+" UNION SELECT COUNT(DISTINCT fcustomerid) fcustomercount,COUNT(fid) ordercount, ifnull(SUM(famount),0)  totalfamount,3 datetype FROM t_ord_productplan WHERE DATE_FORMAT(fcreatetime, '%Y%m') = DATE_FORMAT(CURDATE() , '%Y%m')"+fsupplierid;
			List result=productPlanDao.QueryBySql(sql);
			reponse.getWriter().write(JsonUtil.result(true, "",result.size()+"" ,result));
		} catch (DJException e) {
			// TODO Auto-generated catch block
			reponse.getWriter().write(
					JsonUtil.result(false, e.getMessage(), "", ""));
		}
	}
	
	/**
	 * 订单特性接口
	 * @param request
	 * @param reponse
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/getOrderTraitsSDK")
	public String getOrderTraitsSDK(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		reponse.setCharacterEncoding("utf-8");
		String fid = request.getParameter("fid");
		try {
			String sql = "select fid,fentryamount,fcharacter from t_ord_schemedesignentry where fparentid='"+fid+"' "; 
			ListResult result=productPlanDao.QueryFilterList(sql, request);
			reponse.getWriter().write(JsonUtil.result(true, "", result));
		} catch (DJException e) {
			reponse.getWriter().write(
					JsonUtil.result(false, e.getMessage(), "", ""));
		}
		return null;
	}
	/**
	 * 订单附件信息
	 * @param request
	 * @param reponse
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/getOrderDrawingSSDK")
	public String getOrderDrawingSSDK(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		reponse.setCharacterEncoding("utf-8");
		String fid = request.getParameter("fid");
		try {
			String sql = "select fid,fname from t_ord_productdemandfile  where fparentid='"+fid+"' "; 
			ListResult result=productPlanDao.QueryFilterList(sql, request);
			reponse.getWriter().write(JsonUtil.result(true, "", result));
		} catch (DJException e) {
			reponse.getWriter().write(
					JsonUtil.result(false, e.getMessage(), "", ""));
		}
		return null;
	}
	/**
	更新制造商表fimportEAS=1
	 * @throws IOException 
	 */
	@RequestMapping(value = "/ImportedProductPlanSDK")
	public void ImportedProductPlan(HttpServletRequest request,
			HttpServletResponse response) throws IOException{
		String result = "";
		String fid = request.getParameter("fid");
		String sql="";
		try {
			sql="select fid from t_ord_productplan productplan  where productplan.fid='"+fid+"' and ifnull(productplan.faffirmed, 0) = 1 and  productplan.fimportEAS = 1 and ifnull(productplan.fissync,0) = 0 and ifnull(productplan.fcloseed, 0) = 0 ";
			List<HashMap<String,Object>> deliverordercls= productPlanDao.QueryBySql(sql);
			if(deliverordercls.size()<=0)
			{
				throw new DJException("该订单不能导入接口数据库");
			}
			sql = "update t_ord_productplan set fissync=1 where fid='"+fid+"'";
			productPlanDao.ExecBySql(sql);
			result = "{success:true,msg:'成功'}";
		} catch (DJException e) { 
			// TODO: handle exception
			result = "{success:false,msg:'" + e.getMessage() + "'}";
		}
		response.getWriter().write(result);
		
	}
	/**
	 * 订单信息退回接口
	 * @param request
	 * @param reponse
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/backOrderInfoSDK")
	public String backOrderInfoSDK(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		reponse.setCharacterEncoding("utf-8");
		String result = "";
		String fid = request.getParameter("fid");
		try {
			if("".equals(fid)||fid==null){
				throw new DJException("ID不能为空！");
			}
			String sql = "SELECT fid FROM t_ord_productplan where fcloseed=1 and fid ='" +fid +"'";
			if(saleOrderDao.QueryExistsBySql(sql)){
				throw new DJException("订单已关闭不能退回！");
			}
			sql = "SELECT fid FROM t_ord_productplan WHERE IFNULL(fstockinqty,0)>0 and fid ='"+fid+"'"; 
			if(saleOrderDao.QueryExistsBySql(sql)){
				result = "{success:false,msg:'该订单有入库数量不能退回！'}";
			}else{
//				sql = "update t_ord_productplan set fissync=0,fimportEAS=0,faffirmed=0,fstate=0,faffirmtime=null,faffirmer='' where fid='"+fid+"'";
				saleOrderDao.ExecUnAffirmImport(fid);
				result = "{success:true,msg:'成功'}";
			}
			reponse.getWriter().write(result);
		} catch (DJException e) {
			reponse.getWriter().write(
					JsonUtil.result(false, e.getMessage(), "", ""));
		}
		return null;
	}
}
