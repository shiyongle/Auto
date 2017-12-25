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
import Com.Dao.Finance.ICustBalancepriceDao;
import Com.Entity.Finance.Custbalanceprice;
import Com.Entity.Finance.Custbalancepricecustprices;
import Com.Entity.Finance.Custbalancepriceprices;
import Com.Entity.System.Useronline;

@Controller
public class CustBalancepriceController {
	Logger log = LoggerFactory.getLogger(CustBalancepriceController.class);
	@Resource
	private ICustBalancepriceDao CustbalancepriceDao;
//
//	@Resource
//	private IDeliverapplyDao deliverapplyDao; 
//
//	
	@RequestMapping("/GetCustBalancepricepricesList")
	public String GetCustBalancepricepricesList(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		String sql = "select c.fid,c.fparentid,c.flowerlimit,c.fupperlimit,c.fprice,c.fremark,c.fstartdate,c.fenddate,c.fiseffect from t_bal_Custbalancepriceprices c left join t_bal_Custbalanceprice e on c.fparentid=e.fid ";
		
		ListResult result;
		reponse.setCharacterEncoding("utf-8");
		try {
			result = CustbalancepriceDao.QueryFilterList(sql, request);
			reponse.getWriter().write(JsonUtil.result(true,"",result));
		} catch (DJException e) {
			reponse.getWriter().write(JsonUtil.result(false,e.getMessage(),"",""));
		}
	
		return null;
	}
	
	@RequestMapping("/GetCustBalancepricecustpricesList")
	public String GetCustBalancepricecustpricesList(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		String sql = "select s.fid,s.fparentid,s.flowerlimit,s.fupperlimit,s.fprice,s.fcustomerid,s.fremark,s.fstartdate,s.fenddate,s.fiseffect,c.fname fcustomername from t_bal_Custbalancepricecustprices s left join t_bd_customer c on c.fid=s.fcustomerid left join t_bal_Custbalanceprice e on s.fparentid=e.fid ";
		
		ListResult result;
		reponse.setCharacterEncoding("utf-8");
		try {
			result = CustbalancepriceDao.QueryFilterList(sql, request);
			reponse.getWriter().write(JsonUtil.result(true,"",result));
		} catch (DJException e) {
			reponse.getWriter().write(JsonUtil.result(false,e.getMessage(),"",""));
		}
	
		return null;
	}
	
	@RequestMapping(value="/SaveCustBalanceprice")
	public  String SaveBalanceprice(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		
	String result="";
	try{
	String userid = ((Useronline)request.getSession().getAttribute("Useronline")).getFuserid();
	Custbalanceprice bprice=(Custbalanceprice)request.getAttribute("Custbalanceprice");
	List<Custbalancepriceprices> prices=(ArrayList<Custbalancepriceprices>)request.getAttribute("Custbalancepriceprices");
	List<Custbalancepricecustprices> custprices=(ArrayList<Custbalancepricecustprices>)request.getAttribute("Custbalancepricecustprices");
	if(prices.size()==0)
	{
		result = JsonUtil.result(false,"请至少设置一条结算价格", "", "");
	}else
	{
		CustbalancepriceDao.ExecSaveBalanceprice(bprice, prices, custprices);
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

	
	@RequestMapping("/getCustBalancepriceInfo")
	public String getCustBalancepriceInfo(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		reponse.setCharacterEncoding("utf-8");
		try {
			String userid = ((Useronline)request.getSession().getAttribute("Useronline")).getFuserid();
			String username=((Useronline)request.getSession().getAttribute("Useronline")).getFusername();
			String fid = request.getParameter("fid");
			String sql ="SELECT p.fid,d.fid fcustproductid_fid,d.fname fcustproductid_fname ,ifnull(p.fcreatorid,'"+userid+"') fcreatorid ,ifnull(u1.fname,'"+username+"') username,ifnull(p.fcreatetime,now()) as fcreatetime,ifnull(p.fbalancetype, 2) fbalancetype "+
					" FROM t_bd_custproduct d " +
					"left join t_bal_Custbalanceprice p on d.fid=p.FCUSTPRODUCTID " +
					"left join t_sys_user u1 on  u1.fid=p.fcreatorid " +
					" where d.fid='"+fid+"'";
			List<HashMap<String,Object>> result= CustbalancepriceDao.QueryBySql(sql);
			reponse.getWriter().write(JsonUtil.result(true,"","",result));
		} catch (Exception e) {
			reponse.getWriter().write(JsonUtil.result(false, e.getMessage(), "", ""));
		}
		return null;
	}


}