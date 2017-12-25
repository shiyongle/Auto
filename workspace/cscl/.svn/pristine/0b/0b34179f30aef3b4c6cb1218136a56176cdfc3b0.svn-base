package com.pc.controller.financeBill;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.org.rapid_framework.page.Page;

import com.pc.controller.BaseController;
import com.pc.dao.UserRole.impl.UserRoleDao;
import com.pc.dao.financeBill.IFinanceBillDao;
import com.pc.dao.financeStatement.IFinanceStatementDao;
import com.pc.model.CL_FinanceBill;
import com.pc.model.CL_UserRole;
import com.pc.query.FinanceBillQuery;
import com.pc.query.bill.BillQuery;
import com.pc.util.Base64;
import com.pc.util.JSONUtil;
import com.pc.util.ServerContext;


@Controller
public class FinanceBillController extends BaseController {
	@Resource
	private IFinanceBillDao financeBillDao;
	
	@Resource
	private UserRoleDao userRoleDao;
	
	@Resource
	private IFinanceStatementDao financeStatementDao;
	
	protected static final String CUSTMONTHBILL_JSP= "/pages/pc/finance/custMonthBill.jsp";

	@RequestMapping("/financebill/list")
	public String list(HttpServletRequest request, HttpServletResponse reponse)
			throws Exception {
		return CUSTMONTHBILL_JSP;
	}
	
	@RequestMapping("/financebill/load")
	public String load(HttpServletRequest request, HttpServletResponse reponse,FinanceBillQuery fbquery) throws Exception {
		HashMap<String, Object> m = new HashMap<String, Object>();
		String pageNum = request.getParameter("page");
		String pageSize = request.getParameter("rows");
		if (fbquery == null) {
			fbquery = newQuery(FinanceBillQuery.class, null);
		}
		if (pageNum != null) {
			fbquery.setPageNumber(Integer.parseInt(pageNum));
		}
		if (pageSize != null) {
			fbquery.setPageSize(Integer.parseInt(pageSize));
		}
		Page<CL_FinanceBill> page = financeBillDao.CusBillfindPage(fbquery);
		if(page.getTotalCount() > 0){
			for (CL_FinanceBill bill : page.getResult()) {
				bill.setFunPayAmount(bill.getFbillAmount());
				bill.setFverification(false);
			}
		}
		m.put("total", page.getTotalCount());
		m.put("rows", page.getResult());
		return writeAjaxResponse(reponse, JSONUtil.getJson(m));
	}

	//加载账单
	@RequestMapping("/financebill/billLoad")
	public String load(HttpServletRequest request, HttpServletResponse reponse,@ModelAttribute BillQuery BillQuery)
			throws Exception {
		String pageNum = request.getParameter("page");
		String pageSize = request.getParameter("rows");
		if (BillQuery == null) {
			BillQuery = newQuery(BillQuery.class, null);
		}
		if (pageNum != null) {
			BillQuery.setPageNumber(Integer.parseInt(pageNum));
		}
		if (pageSize != null) {
			BillQuery.setPageSize(Integer.parseInt(pageSize));
		}
		
		Page<CL_UserRole> page = userRoleDao.findPage_cusname(BillQuery);
		HashMap<String, Object> m = new HashMap<String, Object>();
		if(page.getTotalCount()<1){
			m.put("success", false);
			return writeAjaxResponse(reponse,JSONUtil.getJson(m));
		}
		
		/*CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpPost post = new HttpPost(ServerContext.getBaseurl()+"/Action_Pay/DJPay/Pay/PT_UserMoney?&XDEBUG_SESSION_START=17402");
		List list =new ArrayList();
		String g = JSONArray.fromObject(page.getResult()).toString();
		//System.out.println(g);
		byte[] s = g.getBytes("utf-8");
		String ss= Base64.encode(s);
		//System.out.println(ss);
        list.add(new BasicNameValuePair("data",ss)); 
        StringEntity entity = new UrlEncodedFormEntity(list,"utf-8");
        post.setEntity(entity);
		CloseableHttpResponse response =  httpClient.execute(post);
	    String string =EntityUtils.toString(response.getEntity());
	    System.out.println("--string--"+string);
	    
	    JSONObject js = JSONObject.fromObject(string);
	    //System.out.println(js);
	    response.close();
	    httpClient.close();
		boolean b = js.getBoolean("success");
		Object obj = js.get("data");
		int total = page.getTotalCount();
		//System.out.println("obj="+obj);
		JSONArray jarray = JSONArray.fromObject(obj);*/
//	    if(b){
	    	m.put("total", page.getTotalCount());
	    	m.put("rows", page.getResult());
	    	System.out.println(JSONUtil.getJson(m));
	    	return writeAjaxResponse(reponse, JSONUtil.getJson(m));
//	    } else {
//	    	m.put("success", false);
//			return writeAjaxResponse(reponse,JSONUtil.getJson(m));
//		}
	}
}
