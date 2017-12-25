package Com.Controller.System;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.gson.JsonObject;

import Com.Base.Util.DJException;
import Com.Base.Util.ExcelUtil;
import Com.Base.Util.JsonUtil;
import Com.Base.Util.ListResult;
import Com.Base.Util.params;
import Com.Base.Util.mySimpleUtil.MySimpleToolsZ;
import Com.Dao.System.IBaseSysDao;
import Com.Dao.System.IProductdefDao;
import Com.Dao.System.ISProductMaterialDao;
import Com.Entity.System.SupplierDeliverTime;
import Com.Entity.System.Useronline;
import Com.Entity.order.Productdemandfile;
@Controller
public class SProductMaterialController {
	Logger log = LoggerFactory.getLogger(RoleController.class);
	@Resource
	private ISProductMaterialDao SProductMaterialDao;
	@Resource
	private IBaseSysDao baseSysDao;
	@Resource
	IProductdefDao pdtDao;
	
	@RequestMapping("/GetSpMatInfo")
	public String GetSpMatInfo(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		String sql = "select * from t_bd_sproductmaterial where 1=1 ";		
		try {
			List list  = SProductMaterialDao.QueryBySql(sql);			
			reponse.getWriter().write(JsonUtil.result(true, "", list.size()+"",list));
		} catch (DJException e) {
			reponse.getWriter().write(JsonUtil.result(false,e.getMessage(),"",""));
		}
		return null;
	}
	
	@RequestMapping("/GetSpMatInfobyID")
	public String GetSpMatInfobyID(HttpServletRequest request,
			HttpServletResponse response) throws IOException {		
		String fid = request.getParameter("fid");
		String sql = "select  * from  t_bd_sproductmaterial where fid = '"+fid+"'";
		ListResult result;
		try {
			result = SProductMaterialDao.QueryFilterList(sql, request);
			response.getWriter().write(JsonUtil.result(true,"",result));
		} catch (DJException e) {
			response.getWriter().write(JsonUtil.result(false,e.getMessage(),"",""));
		}
		return null;

	}
	
	/**
	 * 新增产品时获取id
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/getSProductId")
	public String getSProductId(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		try {
			String productId = SProductMaterialDao.CreateUUid();
			response.getWriter().write(JsonUtil.result(true, productId, "",""));
		} catch (DJException e) {
			response.getWriter().write(JsonUtil.result(false,e.getMessage(),"",""));
		}
		return null;
	}
	/**
	 * 手机端新增产品时获取id
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/getSProductIdForPhone")
	public String getSProductIdForPhone(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		try {
			String productId = SProductMaterialDao.CreateUUid();
			response.getWriter().write("{\"success\": true,\"data\":[{\"productId\":\""+productId+"\"}]}");
		} catch (DJException e) {
			response.getWriter().write(JsonUtil.result(false,e.getMessage(),"",""));
		}
		return null;
	}
	
	@RequestMapping("/getMaterialOfSProduct")
	public String getMaterialOfSProduct(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		try {
			String fcustpdtid = request.getParameter("fcustpdtid");
			if(StringUtils.isEmpty(fcustpdtid)){
				throw new DJException("客户ID不存在！");
			}
			String sql = "select fid,fsupplierid,fsuppliername,fmatetialid,fmaterialname,fcustpdtid from t_bd_sproductmaterial where fcustpdtid =:fcustpdtid";
			params p = new params();
			p.put("fcustpdtid", fcustpdtid);
			List<HashMap<String, Object>> list = SProductMaterialDao.QueryBySql(sql, p);
			response.getWriter().write(JsonUtil.result(true, "", "",list));
		} catch (DJException e) {
			response.getWriter().write(JsonUtil.result(false,e.getMessage(),"",""));
		}
		return null;
	}
	
	@RequestMapping("/saveSProduct")
	public String saveSProduct(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		try {
			SProductMaterialDao.saveSProduct(request);
			response.getWriter().write(JsonUtil.result(true, "保存成功！", "",""));
		} catch (DJException e) {
			response.getWriter().write(JsonUtil.result(false,e.getMessage(),"",""));
		}
		return null;
	}
	
    /**
     * 制造商客户产品列表显示
     * @param request
     * @param reponse
     * @return
     * @throws IOException
     */
    @RequestMapping("/GetQuickProductforSupplierList")
    public String GetQuickProductforSupplierList(HttpServletRequest request,
		        HttpServletResponse reponse) throws IOException {
		      String userid = ((Useronline) request.getSession().getAttribute("Useronline")).getFuserid();
		      if(request.getParameter("sort")==null){
		    	  request.setAttribute("djsort", "c.fiscommon desc,c.fcreatetime desc");
		      }
		      String sql="";
		      ListResult result;
		      try {
		    	  String fsupplierid=baseSysDao.getCurrentSupplierid(userid);
		    	  sql="SELECT f.fid productid,c.fid,DATE_FORMAT(IFNULL(c.fcreatetime,f.fcreatetime),'%Y-%m-%d %H:%i') fcreatetime,c.fname productName,c.fmaterial,c.fspec productSpec, c.fdescription,c.fcustomerid,c.flastorderfamount recentlyOrderCount,c.flastordertime recentlyOrderDate FROM t_bd_custproduct c inner JOIN t_pdt_productdef  f ON c.fproductid=f.fid where  c.feffect=1 and ifnull(c.fcharacterid,'')='' and f.fsupplierid='"+fsupplierid+"'";
		    	  result = SProductMaterialDao.QueryFilterList(sql, request);
		    	  reponse.getWriter().write(JsonUtil.result(true,"",result));
		      } catch (DJException e) {
		        reponse.getWriter().write(JsonUtil.result(false,e.getMessage(),"",""));
		      }
		    
		      return null;
	}
    /**
     * 获取不是内盒信息的产品档案
     * @param request
     * @param reponse
     * @return
     * @throws IOException
     */
    @RequestMapping("/getSupCusProductOfBoxBoard")
    public String getSupCusProductOfBoxBoard(HttpServletRequest request,
		        HttpServletResponse reponse) throws IOException {
		      String userid = ((Useronline) request.getSession().getAttribute("Useronline")).getFuserid();
		      if(request.getParameter("sort")==null){
		    	  request.setAttribute("djsort", "c.fiscommon desc,c.fcreatetime desc");
		      }
		      String sql="";
		      ListResult result;
		      try {
		    	  String fsupplierid=baseSysDao.getCurrentSupplierid(userid);
		    	  sql="SELECT f.fisboardcard,f.fid productid,c.fid,c.fname productName,c.fspec productSpec, c.fdescription,c.fcustomerid,c.flastorderfamount recentlyOrderCount,c.flastordertime recentlyOrderDate FROM t_bd_custproduct c inner JOIN t_pdt_productdef  f ON c.fproductid=f.fid where  c.feffect=1 and f.fisboardcard != 0 and ifnull(c.fcharacterid,'')='' and f.fsupplierid='"+fsupplierid+"'";
		    	  result = SProductMaterialDao.QueryFilterList(sql, request);
		    	  reponse.getWriter().write(JsonUtil.result(true,"",result));
		      } catch (DJException e) {
		        reponse.getWriter().write(JsonUtil.result(false,e.getMessage(),"",""));
		      }
		      return null;
	}
    
    /**
     * 制造商客户产品列表显示用于苹果端
     * @param request
     * @param reponse
     * @return
     * @throws IOException
     * @throws ServletException 
     */
    @RequestMapping("/GetQuickProductforSupplierListForPhone")
    public String GetQuickProductforSupplierListForPhone(HttpServletRequest request,
		        HttpServletResponse reponse) throws IOException, ServletException {
//    			reponse.sendRedirect(request.getContextPath()+"/GetQuickProductforSupplierListPhone.do");
    			request.getRequestDispatcher("GetQuickProductforSupplierListPhone.do").forward(request,reponse);
		     return null;
	}
    
    
    
    
    
    
	@RequestMapping("/getSProductInfo")
	public String getSProductInfo(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		try {
			String cpid = request.getParameter("fid");
			String sql = "select f.fid ffileid,f.fname ffilename,pdt.fid,cp.fid fcustpdtid,pdt.fprice,pdt.fname,pdt.fcharacter,pdt.fboxmodelid,pdt.flayer,pdt.fdescription,pdt.fboxlength,pdt.fboxwidth,pdt.fboxheight,";
			sql +="  pdt.fboardlength,pdt.fboardwidth,pdt.ftilemodelid,pdt.fstavetype,pdt.fseries,pdt.fhstaveexp,pdt.fhformula,";
			sql +="  pdt.fhformula0,pdt.fhformula1,pdt.fvstaveexp,pdt.fvformula,pdt.fvformula0,pdt.fdefine1,pdt.fdefine2,pdt.fmtsupplierid,";
			sql +="  pdt.ftmodelspec,pdt.fcbnumber,pdt.fybnumber,pdt.fpackway,pdt.fpinamount,pdt.fcblocation,pdt.fpaperspec,pdt.ffacespec,pdt.ffylinamount,pdt.fisboardcard,pdt.fmaterialcode,pdt.fmaterialinfo,";
			sql +="  pdt.fdefine3,pdt.fprintcolor,pdt.fworkproc,pdt.fcustomerid,pdt.fdescription1,pdt.fdescription2,pdt.fmaterialfid fmaterialfid_fid,pdt.fqueryname fmaterialfid_fqueryname,pdt.fqueryname fqueryname";
			sql +=" from  t_bd_custproduct cp left join t_pdt_productdef pdt on cp.fproductid = pdt.fid left join t_ord_productdemandfile f on f.fid=pdt.ffileid";
			sql +=" where pdt.fid ='"+cpid+"'";
			List<HashMap<String,Object>> result= SProductMaterialDao.QueryBySql(sql);
			response.getWriter().write(JsonUtil.result(true,"","",result));
		} catch (Exception e) {
			response.getWriter().write(JsonUtil.result(false, e.getMessage(), "", ""));
		}
		return null;
	}
	
	@RequestMapping("/delSpdtMaterial")
	public String delSpdtMaterial(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		try {
			String fid = request.getParameter("fid");
			SProductMaterialDao.Delete("t_bd_sproductmaterial", fid);			
			response.getWriter().write(JsonUtil.result(true, "删除成功！", "",""));
		} catch (DJException e) {
			response.getWriter().write(JsonUtil.result(false,e.getMessage(),"",""));
		}
		return null;
	}
	/**
	 * 产品档案上传版面
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/saveFileOfSProduct")
	public String saveFileOfSProduct(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		try {
			Productdemandfile file = SProductMaterialDao.saveFileOfSProduct(request);
			response.getWriter().write(JsonUtil.result(true, file.getFid()+"_"+file.getFname(), "",""));
		} catch (DJException e) {
			response.getWriter().write(JsonUtil.result(false,e.getMessage(),"",""));
		}
		return null;
	}
	
	//2015-06-19  纸板订单列表快速保存到客户产品
	@RequestMapping("/saveBoardToCustpdt")
	public String saveBoardtoCustpdt(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		try {
			SProductMaterialDao.saveBoardtoCustpdt(request);
			response.getWriter().write(JsonUtil.result(true, "保存客户产品成功！", "",""));
		} catch (DJException e) {
			response.getWriter().write(JsonUtil.result(false,e.getMessage(),"",""));
		}
		return null;
	}
	
	//2015-06-19  纸板订单快速关联客户产品
	@RequestMapping("updateCustpdtByboard")
	public void updateCustpdtByboard(HttpServletRequest request,HttpServletResponse response) throws IOException{
		try {
			SProductMaterialDao.updateCustpdtByboard(request);
			response.getWriter().write(JsonUtil.result(true,"关联客户产品成功！","",""));
		} catch (DJException e) {
			// TODO: handle exception
			response.getWriter().write(JsonUtil.result(false,e.getMessage(),"",""));
		}
	}

	@RequestMapping("getSupplierCustStatementList")
	public void getSupplierCustStatementList(HttpServletRequest request,HttpServletResponse response) throws IOException{
		try {//AND p.`FCREATORID` IS NOT NULL AND c.fcreatetime IS NOT NULL
			String sql ="SELECT s.`FNAME` AS fsuppliername,COUNT(1) AS fproductcount,MIN(IFNULL(c.fcreatetime,p.`FCREATETIME`)) minfcreatetime,MAX(IFNULL(c.fcreatetime,p.`FCREATETIME`)) maxfcreatetime,COUNT(DISTINCT c.`FCUSTOMERID`) fcustomercount FROM  t_bd_custproduct c INNER JOIN t_pdt_productdef p  ON c.fproductid = p.fid LEFT JOIN `t_sys_supplier` s ON p.`fsupplierid` = s.fid where s.`FNAME` IS NOT NULL  GROUP BY p.`fsupplierid` ";
			ListResult result = SProductMaterialDao.QueryFilterList(sql, request);
			response.getWriter().write(JsonUtil.result(true,"",result));
		} catch (DJException e) {
			// TODO: handle exception
			response.getWriter().write(JsonUtil.result(false,e.getMessage(),"",""));
		}
	}
	@RequestMapping("/ExcelSupplierCustStatementList")
	public String ExcelSupplierCustStatementList(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
	
		try {
			String sql = "SELECT s.`FNAME` AS fsuppliername,COUNT(1) AS fproductcount,MIN(IFNULL(c.fcreatetime,p.`FCREATETIME`)) minfcreatetime,MAX(IFNULL(c.fcreatetime,p.`FCREATETIME`)) maxfcreatetime,COUNT(DISTINCT c.`FCUSTOMERID`) fcustomercount FROM  t_bd_custproduct c INNER JOIN t_pdt_productdef p  ON c.fproductid = p.fid LEFT JOIN `t_sys_supplier` s ON p.`fsupplierid` = s.fid where s.`FNAME` IS NOT NULL AND p.`FCREATORID` IS NOT NULL AND c.fcreatetime IS NOT NULL GROUP BY p.`fsupplierid` ";
//			request.setAttribute("djsort", "sa.fid,sa.fnumber desc,sd.fsequence");
			request.setAttribute("nolimit", "true");
			switch (MySimpleToolsZ.getMySimpleToolsZ().judgeSearchType(request)) {
			case MySimpleToolsZ.TIME_SEARCH:
				sql += MySimpleToolsZ.getMySimpleToolsZ().buildDateBetweenSQLFragment(request);
				break;
			}
			
			ListResult result = MySimpleToolsZ.getMySimpleToolsZ().buildExcelListResult(sql, request, SProductMaterialDao);
			
			List<String> order = MySimpleToolsZ.gainDataIndexList(request);
			
			ExcelUtil.toexcel(response, result,order);
		} catch (DJException e) {
			response.getWriter().write(
					JsonUtil.result(false, e.getMessage(), "", ""));
		}

		return null;
	}
	/**
	 * 获取制造商的交期设置
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/getSupplierDeliverTimeConfig")
	public String getSupplierDeliverTimeConfig(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String userid = ((Useronline) request.getSession().getAttribute("Useronline")).getFuserid();
		try {
			String fsupplierid=baseSysDao.getCurrentSupplierid(userid);
			String sql = "select fid,fdays,fdefaultdays from t_sys_supplierdelivertime where fsupplierid = '"+fsupplierid+"'";
			HashMap<String, String> map = baseSysDao.getMapData(sql);
			if(map == null){
				map = new HashMap<>();
			}
			map.put("success", "true");
			map.put("fsupplierid", fsupplierid);
			response.getWriter().write(JSONObject.fromObject(map).toString());
		} catch (DJException e) {
			response.getWriter().write(JsonUtil.result(false, e.getMessage(), "", ""));
		}
		return null;
	}
	
	@RequestMapping("/saveSupplierDeliverTimeConfig")
	public String saveSupplierDeliverTimeConfig(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		SupplierDeliverTime obj = (SupplierDeliverTime) request.getAttribute("SupplierDeliverTime");
		try {
			SProductMaterialDao.saveSupplierDeliverTimeConfig(obj);
			response.getWriter().write(JsonUtil.result(true, "保存成功！", "",""));
		} catch (DJException e) {
			response.getWriter().write(JsonUtil.result(false, e.getMessage(), "", ""));
		}
		return null;
	}
	
}
