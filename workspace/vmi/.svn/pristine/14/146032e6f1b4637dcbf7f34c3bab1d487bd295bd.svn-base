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
import Com.Dao.Finance.IBalancepriceDao;
import Com.Dao.System.IProductdefDao;
import Com.Entity.Finance.Balanceprice;
import Com.Entity.Finance.Balancepricecustprices;
import Com.Entity.Finance.Balancepriceprices;
import Com.Entity.System.Useronline;

@Controller
public class BalancepriceController {
	Logger log = LoggerFactory.getLogger(BalancepriceController.class);
	@Resource
	private IBalancepriceDao balancepriceDao;
	
	@Resource
	private IProductdefDao productdefDao;
//
//	@Resource
//	private IDeliverapplyDao deliverapplyDao; 
//
//	
	@RequestMapping("/GetBalancepricepricesList")
	public String GetBalancepricepricesList(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		String sql = "select c.fid,c.fparentid,c.flowerlimit,c.fupperlimit,c.fprice,c.fremark,c.fstartdate,c.fenddate,c.fiseffect from t_bal_balancepriceprices c left join t_bal_balanceprice e on c.fparentid=e.fid ";
		
		ListResult result;
		reponse.setCharacterEncoding("utf-8");
		try {
			result = balancepriceDao.QueryFilterList(sql, request);
			reponse.getWriter().write(JsonUtil.result(true,"",result));
		} catch (DJException e) {
			reponse.getWriter().write(JsonUtil.result(false,e.getMessage(),"",""));
		}
	
		return null;
	}
	
	@RequestMapping("/GetBalancepricecustpricesList")
	public String GetBalancepricecustpricesList(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		String sql = "select s.fid,s.fparentid,s.flowerlimit,s.fupperlimit,s.fprice,s.fcustomerid,s.fremark,s.fstartdate,s.fenddate,s.fiseffect,c.fname fcustomername from t_bal_balancepricecustprices s left join t_bd_customer c on c.fid=s.fcustomerid left join t_bal_balanceprice e on s.fparentid=e.fid ";
		
		ListResult result;
		reponse.setCharacterEncoding("utf-8");
		try {
			result = balancepriceDao.QueryFilterList(sql, request);
			reponse.getWriter().write(JsonUtil.result(true,"",result));
		} catch (DJException e) {
			reponse.getWriter().write(JsonUtil.result(false,e.getMessage(),"",""));
		}
	
		return null;
	}
	
	@RequestMapping(value="/SaveBalanceprice")
	public  String SaveBalanceprice(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		
	String result="";
	try{
	String userid = ((Useronline)request.getSession().getAttribute("Useronline")).getFuserid();
	String productid=request.getParameter("fproductid");
	String isHistory= productdefDao.QueryIsHistory("('"+productid+"')");
	if(!"false".equals(isHistory))
	{
		throw new DJException(isHistory);
	}
	Balanceprice bprice=(Balanceprice)request.getAttribute("Balanceprice");
	List<Balancepriceprices> prices=(ArrayList<Balancepriceprices>)request.getAttribute("Balancepriceprices");
	List<Balancepricecustprices> custprices=(ArrayList<Balancepricecustprices>)request.getAttribute("Balancepricecustprices");
	if(prices.size()==0)
	{
		result = JsonUtil.result(false,"请至少设置一条结算价格", "", "");
	}else
	{
		balancepriceDao.ExecSaveBalanceprice(bprice, prices, custprices);
		result = "{success:true,msg:'客户保存成功!'}";
	}

	}
	catch(Exception e)
	{
		result = JsonUtil.result(false,e.getMessage(), "", "");
	}
	reponse.setCharacterEncoding("utf-8");
	reponse.getWriter().write(result);
		return null;

	}

	
	@RequestMapping("/getBalancepriceInfo")
	public String getBalancepriceInfo(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		reponse.setCharacterEncoding("utf-8");
		try {
			String userid = ((Useronline)request.getSession().getAttribute("Useronline")).getFuserid();
			String username=((Useronline)request.getSession().getAttribute("Useronline")).getFusername();
			String fid = request.getParameter("fid");
			String sql ="SELECT p.fid,d.fid fproductid_fid,d.fname fproductid_fname ,ifnull(p.fcreatorid,'"+userid+"') fcreatorid ,ifnull(u1.fname,'"+username+"') username,ifnull(p.fcreatetime,now()) as fcreatetime,ifnull(p.fbalancetype, 2) fbalancetype "+
					" FROM t_pdt_productdef  d " +
					"left join t_bal_balanceprice p on d.fid=p.fproductid " +
					"left join t_sys_user u1 on  u1.fid=p.fcreatorid " +
					" where d.fid='"+fid+"'";
			List<HashMap<String,Object>> result= balancepriceDao.QueryBySql(sql);
			reponse.getWriter().write(JsonUtil.result(true,"","",result));
		} catch (Exception e) {
			reponse.getWriter().write(JsonUtil.result(false, e.getMessage(), "", ""));
		}
		return null;
	}


}