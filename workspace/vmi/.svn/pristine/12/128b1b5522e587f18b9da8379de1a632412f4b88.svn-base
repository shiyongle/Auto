package Com.Controller.Finance;

import java.io.IOException;
import java.util.ArrayList;
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
import Com.Dao.Finance.IPurchasepriceDao;
import Com.Dao.System.IProductdefDao;
import Com.Entity.Finance.Purchaseourprice;
import Com.Entity.Finance.Purchaseprice;
import Com.Entity.Finance.Purchasesupplierprice;
import Com.Entity.System.Useronline;

@Controller
public class PurchasepriceController {
	Logger log = LoggerFactory.getLogger(PurchasepriceController.class);

	@Resource
	private IPurchasepriceDao purchasepriceDao;
	
	
	@Resource
	private IProductdefDao productdefDao;

	//
	// @Resource
	// private IDeliverapplyDao deliverapplyDao;
	//
	//
	@RequestMapping("/getPurchaseourprices")
	public String getPurchaseourprices(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		String sql = "select c.fid,c.fparentid,c.flowerlimit,c.fupperlimit,c.fprice,c.fremark,c.fstartdate,c.fenddate,c.fiseffect from t_bal_purchaseourprice c left join t_bal_purchaseprice e on c.fparentid=e.fid ";

		ListResult result;
		reponse.setCharacterEncoding("utf-8");
		try {
			result = purchasepriceDao.QueryFilterList(sql, request);
			reponse.getWriter().write(JsonUtil.result(true, "", result));
		} catch (DJException e) {
			reponse.getWriter().write(
					JsonUtil.result(false, e.getMessage(), "", ""));
		}

		return null;
	}

	@RequestMapping("/getPurchasesupplierprices")
	public String getPurchasesupplierprices(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		String sql = "select s.fid,s.fparentid,s.flowerlimit,s.fupperlimit,s.fprice,s.fsupplierId,s.fremark,s.fstartdate,s.fenddate,s.fiseffect,c.fname fsuppliername from t_bal_purchasesupplierprice s left join t_sys_supplier c on c.fid=s.FsupplierID left join t_bal_purchaseprice e on s.fparentid=e.fid ";

		ListResult result;
		reponse.setCharacterEncoding("utf-8");
		try {
			result = purchasepriceDao.QueryFilterList(sql, request);
			reponse.getWriter().write(JsonUtil.result(true, "", result));
		} catch (DJException e) {
			reponse.getWriter().write(
					JsonUtil.result(false, e.getMessage(), "", ""));
		}

		return null;
	}

	@RequestMapping(value = "/savePurchaseprice") 
	public String savePurchaseprice(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {

		String result = "";
		try { 
			String userid = ((Useronline) request.getSession().getAttribute(
					"Useronline")).getFuserid();
			String productid=request.getParameter("fproductid");
			String isHistory= productdefDao.QueryIsHistory("('"+productid+"')");
			if(!"false".equals(isHistory))
			{
				throw new DJException(isHistory);
			}
			Purchaseprice bprice = (Purchaseprice) request
					.getAttribute("Purchaseprice"); 
			List<Purchaseourprice> prices = (ArrayList<Purchaseourprice>) request
					.getAttribute("Purchaseourprice");
			List<Purchasesupplierprice> custprices = (ArrayList<Purchasesupplierprice>) request
					.getAttribute("Purchasesupplierprice");
			if (prices.size()==0) {
				result = JsonUtil.result(false, "请选择设置采购价格", "", "");
				reponse.getWriter().write(result);
				return null;
			}
			
			purchasepriceDao.ExecSaveBalanceprice(bprice, prices, custprices);
			result = "{success:true,msg:'保存成功!'}";

		} catch (Exception e) {
			result = JsonUtil.result(false, e.getMessage(), "", "");
		} 
		reponse.setCharacterEncoding("utf-8");
		reponse.getWriter().write(result);
		return null; 

	}

	@RequestMapping("/getPurchaseprice")
	public String getPurchaseprice(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		reponse.setCharacterEncoding("utf-8");
		try {
			String userid = ((Useronline) request.getSession().getAttribute(
					"Useronline")).getFuserid();
			String username = ((Useronline) request.getSession().getAttribute(
					"Useronline")).getFusername();
			
			String fid = request.getParameter("fid");
			
			String sql = "SELECT p.fid,d.fid fproductid_fid,d.fname fproductid_fname ,ifnull(p.FPurchaseTYPE, 2) fpurchaseType ,ifnull(p.fcreatorid,'"
					+ userid
					+ "') fcreatorid ,ifnull(u1.fname,'"
					+ username
					+ "') username,ifnull(p.fcreatetime,now()) as fcreatetime  "
					+ " FROM t_pdt_productdef  d "
					+ "left join t_bal_purchaseprice p on d.fid=p.fproductid "
					+ "left join t_sys_user u1 on  u1.fid=p.fcreatorid "
					+ " where d.fid='" + fid + "'";
			
			List<HashMap<String, Object>> result = purchasepriceDao
					.QueryBySql(sql);
			
			reponse.getWriter().write(JsonUtil.result(true, "", "", result));
			
		} catch (Exception e) {
			reponse.getWriter().write(
					JsonUtil.result(false, e.getMessage(), "", ""));
		}
		return null;
	}

}