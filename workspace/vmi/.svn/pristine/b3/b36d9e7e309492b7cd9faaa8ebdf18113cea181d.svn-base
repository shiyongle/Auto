package Com.Controller.Inv;

import java.io.IOException;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import Com.Base.Dao.IBaseDao;
import Com.Base.Util.DJException;
import Com.Base.Util.ExcelUtil;
import Com.Base.Util.JsonUtil;
import Com.Base.Util.ListResult;
import Com.Dao.Inv.IProductcheckitemDao;
import Com.Entity.Inv.Productcheckitem;


@Controller
public class ProductcheckitemController {
	Logger log = LoggerFactory.getLogger(ProductcheckitemController.class);
	@Resource
	private IProductcheckitemDao productcheckitemDao;

	@RequestMapping(value = "/gainProductcheckitems")
	public String gainProductcheckitems(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		
		try {
			
			String fpid = request.getParameter("fproductcheckid");
			
			String sql = " SELECT fid,fproductcheckid,customername,productname,productnumber,forderunitid,fbalanceqty fstoreqty,actualquotation,gainorlossCount,fremark,fproductdefid,fcharacter FROM t_inv_productcheckitem_view ";
			
			if (fpid !=null && !fpid.equals("-1")) {
				
				sql += String.format("where fproductcheckid = '%s'", fpid);
				
			}
			
			ListResult result = productcheckitemDao.QueryFilterList(sql, request);
			reponse.getWriter().write(JsonUtil.result(true, "", result));
		} catch (DJException e) {
			// TODO Auto-generated catch block
			reponse.getWriter().write(
					JsonUtil.result(false, e.getMessage(), "", ""));
		}
		return null;
	}

	@RequestMapping(value = "/saveProductcheckitem")
	public String saveProductcheckitem(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		
		try {

//			Productcheckitem po = buildPo(request, productcheckitemDao);
//			
//			productcheckitemDao.ExecSave(po);
			
			productcheckitemDao.ExecSaveProductcheckitem(request);
			
			reponse.getWriter().write(JsonUtil.result(true, "成功", "",""));
		} catch (DJException e) {
			// TODO Auto-generated catch block
			reponse.getWriter().write(
					JsonUtil.result(false, e.getMessage(), "", ""));
		}
		return null;
	}
	
	@RequestMapping(value = "/updateProductcheckitem")
	public String updateProductcheckitem(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		
		try {

			Productcheckitem po = buildPo(request, productcheckitemDao);
			
			productcheckitemDao.ExecSave(po);
						
			reponse.getWriter().write(JsonUtil.result(true, "成功", "",""));
		} catch (DJException e) {
			// TODO Auto-generated catch block
			reponse.getWriter().write(
					JsonUtil.result(false, e.getMessage(), "", ""));
		}
		return null;
	}
	
	private Productcheckitem buildPo(HttpServletRequest request, IBaseDao dao) {
		String fid = request.getParameter("fid");
		
		//only update here
		Productcheckitem productcheckitem = null;
		
		if (fid != null) {
			
			productcheckitem = productcheckitemDao.Query(fid);
		
			String fremark = request.getParameter("fremark");
			
			if (fremark != null) {
			
				productcheckitem.setFremark(fremark);
			}
			
			String fqty = request.getParameter("fqty");
			
			if (fqty != null) {
				
				productcheckitem.setFqty(Integer.valueOf(fqty));
				
			}
			
		} 
		
		
		
		return productcheckitem; 
	}
	
	@RequestMapping(value = "/makeproductcheckitemtoExcel")
	public String makeproductcheckitemtoExcel(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		reponse.setCharacterEncoding("utf-8");
		try {
			String sql= " SELECT customername '客户名称', productname '包装物名称', productnumber '包装物编号', fcharacter '规格', forderunitid '单位', fbalanceqty '库存数量', actualquotation '实盘数量', gainorlossCount '损益数量', fremark '备注' from t_inv_productcheckitem_view ";
			
			ListResult result = productcheckitemDao.QueryFilterList(sql, request);
			ExcelUtil.toexcel(reponse,result,"产品盘点");
		} catch (DJException e) {
			// TODO Auto-generated catch block
			reponse.getWriter().write(
					JsonUtil.result(false, e.getMessage(), "", ""));
		}

		return null;

	}
	
	@RequestMapping(value = "/deleteProductcheckitemtos")
	public String deleteProductcheckitemtos(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {

		String fids = request.getParameter("fidcls");
		
		String fidsS = "('" + fids.replace(",", "','") + "')";

		try {
			
			String sql = " DELETE FROM t_inv_productcheckitem WHERE fid in %s ";
			
			sql = String.format(sql, fidsS);
			
			productcheckitemDao.ExecBySql(sql);
			
			reponse.getWriter().write(JsonUtil.result(true, "成功!", "", ""));
		} catch (Exception e) {
			reponse.getWriter().write(
					JsonUtil.result(false, e.toString(), "", ""));
		}
		return null;

	}
	
	@RequestMapping(value = "/gianProductsWithStoreqty")
	public String gianProductsWithStoreqty(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {

		reponse.setCharacterEncoding("utf-8");
		try {
			String sql = " SELECT fproductdefid,fsupplierid,customername,productname,productnumber,fcharacter,forderunitid,fstoreqty,fstoreqty actualquotation  FROM t_ord_productplan_storeqty_view "
					+ " where 1 = 1 "
					+ productcheckitemDao.QueryFilterByUser(request, null,
							"fsupplierid");

			ListResult result = productcheckitemDao.QueryFilterList(sql,
					request);
			reponse.getWriter().write(JsonUtil.result(true, "", result));
		} catch (Exception e) {
			reponse.getWriter().write(
					JsonUtil.result(false, e.toString(), "", ""));
		}
		return null;

	}
	
	@RequestMapping(value = "/gainTotalCount")
	public String gainTotalCount(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {


		try {
			String sql = "SELECT sum(fbalanceqty) fbalanceqtySum,sum(actualquotation) actualquotationSum,sum(gainorlossCount) gainorlossCountSum  t_inv_productcheckitem_view "
					+ " where 1 = 1 "
					+ productcheckitemDao.QueryFilterByUser(request, null,
							"fsupplierid");

			ListResult result = productcheckitemDao.QueryFilterList(sql,
					request);
			reponse.getWriter().write(JsonUtil.result(true, "", result));
		} catch (Exception e) {
			reponse.getWriter().write(
					JsonUtil.result(false, e.toString(), "", ""));
		}
		return null;

	}
	
	
}