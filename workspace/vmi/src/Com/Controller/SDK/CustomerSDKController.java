package Com.Controller.SDK;

import java.io.IOException;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import Com.Base.Util.JsonUtil;
import Com.Base.Util.ListResult;
import Com.Dao.System.ICustomer;


@Controller
public class CustomerSDKController {
	Logger log = LoggerFactory.getLogger(CustomerSDKController.class);
	@Resource
	private ICustomer CustomerDao;

	@RequestMapping(value = "/GetCusSDK")
	public String GetCusSDK(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		reponse.setCharacterEncoding("utf-8");
		try {
			String sql=" select fid from t_bd_customer ";
			request.setAttribute("nolimit", true);
			ListResult result=CustomerDao.QueryFilterList(sql, request);
			reponse.getWriter().write(JsonUtil.result(true, "", result));
		} catch (Exception e) {
			reponse.getWriter().write(
					JsonUtil.result(false, e.getMessage(), "", ""));
		}
		return null;

	}
	@RequestMapping(value = "/ImpCusSDK")
	public String ImpCusSDK(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		String result = "";
		reponse.setCharacterEncoding("utf-8");
		try {
			CustomerDao.ExecImpCusSDK(request);
			result = "{success:true,msg:'成功'}";
		} catch (Exception e) {
			result = "{success:false,msg:'" + e.toString() + "'}";
		}
		
		reponse.getWriter().write(result);

		return null;

	}
	
	@RequestMapping(value = "/SaveCustaccountbalanceSDK")
	public String SaveCustaccountbalanceSDK(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		String result = "";
		reponse.setCharacterEncoding("utf-8");
		try {
			String customerid = request.getParameter("customerid")==null?"": request.getParameter("customerid").toString();
			if(!customerid.isEmpty()){
				CustomerDao.ExecSaveCustaccountbalanceSDK(customerid);
			}
			
			result = "{success:true,msg:'成功'}";
		} catch (Exception e) {
			result = "{success:false,msg:'" + e.toString() + "'}";
		}
		
		reponse.getWriter().write(result);

		return null;

	}
	
}
