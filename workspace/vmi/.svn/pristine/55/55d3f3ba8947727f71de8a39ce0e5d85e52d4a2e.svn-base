package Com.Controller.SDK;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

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
import Com.Dao.SDK.IProductdefSDKDao;

@Controller
public class ProductdefSDKController {
	Logger log = LoggerFactory.getLogger(ProductdefSDKController.class);
	@Resource
	private IProductdefSDKDao ProductdefSDKDao;
//	@Resource
//	private IProductRelationDao productRelationDao;
//	@Resource
//	private IProductRelationEntryDao productRelationEntryDao;
	/**
	 * 建议字段命名一致性，这样就能避免很多不必要的操作
	 */
	private Map<String, String> storeToDbTMap = new HashMap<>();
	
	
	@RequestMapping(value = "/ImpPdtChildSDK")
	public String ImpPdtChildSDK(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		String result = "";
		reponse.setCharacterEncoding("utf-8");
		try {
			ProductdefSDKDao.ExecImpPdtChildSDK(request);
			result = "{success:true,msg:'成功'}";
		} catch (Exception e) {
			result = "{success:false,msg:'" + e.toString() + "'}";
		}
		
		reponse.getWriter().write(result);

		return null;

	}
	
	@RequestMapping(value = "/ImpPdtSDK")
	public String ImpPdtSDK(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		String result = "";
		reponse.setCharacterEncoding("utf-8");
		try {
			ProductdefSDKDao.ExecImpPdtSDK(request);
			result = "{success:true,msg:'成功'}";
		} catch (Exception e) {
			result = "{success:false,msg:'" + e.toString() + "'}";
		}
		
		reponse.getWriter().write(result);

		return null;

	}
	@RequestMapping(value = "/getStockProductList")
	public void getStockProductList(HttpServletRequest request,
			HttpServletResponse response)throws IOException{
		response.setCharacterEncoding("utf-8");
		try {
			String sql = "SELECT tppd.fid,tpvpp.forderamount,tppd.feasproductid, tppd.fname,tppd.fnumber, tppd.fversion,tppd.fmaterialcode, tppd.fmaterialcodeid," 
				+"tppd.fboxmodelid,tppd.ftilemodelid,IFNULL(tppd.fboardlength,0) fboardlength,IFNULL(tppd.fboardwidth,0) fboardwidth,IFNULL(tppd.fboxlength,0) fboxlength,IFNULL(tppd.fboxwidth,0) fboxwidth,IFNULL(tppd.fboxheight,0) fboxheight,"
				+"IFNULL(tppd.fmateriallength,0) fmateriallength,IFNULL(tppd.fmaterialwidth,0) fmaterialwidth,tppd.fhstaveexp,tppd.fvstaveexp,tppd.farea "
				+"FROM t_pdt_vmiproductparam tpvpp " 
				+"LEFT JOIN t_pdt_productdef tppd ON tpvpp.FPRODUCTID = tppd.fid " 
				+" where  tpvpp.ftype=0 "
				+ProductdefSDKDao.QueryFilterByUser(request, null,
					"tpvpp.fsupplierid"); 
			request.setAttribute("nolimit", true);
			ListResult result = ProductdefSDKDao.QueryFilterList(sql, request);
			response.getWriter().write(JsonUtil.result(true, "", result));
		} catch (DJException e) {
			// TODO: handle exception
			response.getWriter().write(JsonUtil.result(false,e.getMessage(), "",""));
		}
	}
//	/**
//	更新制造商表fimportEAS=1
//	 * @throws IOException 
//	 */
//	@RequestMapping(value = "/updateProductPlan")
//	public void updateProductPlan(HttpServletRequest request,
//			HttpServletResponse response) throws IOException{
//		String result = "";
//		String fid = request.getParameter("fid");
//		try {
//			String sql = "update t_ord_productplan set fimportEAS=1 where fid='"+fid+"'";
//			ProductdefSDKDao.ExecBySql(sql);
//			result = "{success:true,msg:'成功'}";
//		} catch (DJException e) { 
//			// TODO: handle exception
//			result = "{success:false,msg:'" + e.getMessage() + "'}";
//		}
//		response.getWriter().write(result);
//		
//	}
}
