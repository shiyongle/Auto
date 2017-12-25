package com.pc.controller.financeVerify;

import com.pc.controller.BaseController;
import org.springframework.stereotype.Controller;
//import java.math.BigDecimal;
//import java.util.HashMap;
//import javax.annotation.Resource;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import org.springframework.transaction.annotation.Transactional;
//import org.springframework.web.bind.annotation.ModelAttribute;
//import org.springframework.web.bind.annotation.RequestMapping;
//import cn.org.rapid_framework.page.Page;
//import com.pc.dao.UserRole.impl.UserRoleDao;
//import com.pc.dao.financeBill.impl.FinanceBillDao;
//import com.pc.dao.financeReceive.impl.FinanceReceiptDaoImpl;
//import com.pc.model.CL_FinanceBill;
//import com.pc.model.CL_FinanceReceipt;
//import com.pc.query.FinanceBillQuery;
//import com.pc.query.financeReceive.CL_FinanceReceiptQuery;
//import com.pc.util.JSONUtil;

@Controller
public class FinanceVerifyController extends BaseController {
//	protected static final String LIST_JSP = "/pages/pc/finance/receivables.jsp";
//	protected static final String VERIFICATION_JSP = "/pages/pc/finance/verification.jsp";
	
//	@Resource
//	private FinanceReceiptDaoImpl receiptDao;
//	@Resource
//	private UserRoleDao userRoleDao;
//	@Resource
//	private FinanceBillDao billDao;

//	@RequestMapping("/receipt/list")
//	public String list(HttpServletRequest request, HttpServletResponse reponse)
//			throws Exception {
//		return LIST_JSP;
//	}
//	
//	@RequestMapping("/receipt/loadverify")
//	public String loadverify(HttpServletRequest request, HttpServletResponse reponse,Integer fid,Integer fuserId,String fusername,BigDecimal famount,BigDecimal fremainAmount)
//			throws Exception {
//		fusername=new String(fusername.getBytes("ISO-8859-1"),"UTF-8");
//		request.setAttribute("fid", fid);
//		request.setAttribute("fuserId", fuserId);
//		request.setAttribute("famount", famount);
//		request.setAttribute("fusername", fusername);
//		request.setAttribute("fremainAmount", fremainAmount);
//		return VERIFICATION_JSP;
//	}
//		
//	@RequestMapping("/receipt/load")
//	public String load(HttpServletRequest request, HttpServletResponse reponse,@ModelAttribute CL_FinanceReceiptQuery receiptQuery)
//			throws Exception {
//		String pageNum = request.getParameter("page");
//		String pageSize = request.getParameter("rows");
//		if (receiptQuery == null) {
//			receiptQuery = newQuery(CL_FinanceReceiptQuery.class, null);
//		}
//		if (pageNum != null) {
//			receiptQuery.setPageNumber(Integer.parseInt(pageNum));
//		}
//		if (pageSize != null) {
//			receiptQuery.setPageSize(Integer.parseInt(pageSize));
//		}
//		if(receiptQuery.getFuserId() ==null || receiptQuery.getFuserId()==0|| receiptQuery.getFuserId()==-1){
//			receiptQuery.setFuserId(null);
//		}
//		receiptQuery.setSortColumns("fr.fcreate_time desc");
//		Page<CL_FinanceReceipt> page = receiptDao.findPage(receiptQuery);
//		HashMap<String, Object> m = new HashMap<String, Object>();
//		m.put("total", page.getTotalCount());
//		m.put("rows", page.getResult());
//		return writeAjaxResponse(reponse, JSONUtil.getJson(m));
//	}
//	
//	
//	@RequestMapping("/verify/loadBill")
//	public String loadBill(HttpServletRequest request, HttpServletResponse reponse,@ModelAttribute FinanceBillQuery billQuery)
//			throws Exception {
//		int fuserid=Integer.parseInt(request.getParameter("fuserId"));
//		String pageNum = request.getParameter("page");
//		String pageSize = request.getParameter("rows");
//		if (billQuery == null) {
//			billQuery = newQuery(FinanceBillQuery.class, null);
//		}
//		if (pageNum != null) {
//			billQuery.setPageNumber(Integer.parseInt(pageNum));
//		}
//		if (pageSize != null) {
//			billQuery.setPageSize(Integer.parseInt(pageSize));
//		}
//		billQuery.setFcustId(fuserid);
//		billQuery.setFverification(0);
//		billQuery.setSortColumns("fbillDate");
//		Page<CL_FinanceBill> page = billDao.findPage(billQuery);
//		HashMap<String, Object> m = new HashMap<String, Object>();
//		m.put("total", page.getTotalCount());
//		m.put("rows", page.getResult());
//		return writeAjaxResponse(reponse, JSONUtil.getJson(m));
//	}
//
//	// 核销
//	@Transactional
//	@RequestMapping("/verify/verification")
//	public String verification(HttpServletRequest request,HttpServletResponse reponse) throws Exception {
//		int fbillid=Integer.parseInt(request.getParameter("fbillId"));//账单ID
//		int freceiveid=Integer.parseInt(request.getParameter("freceiveId"));//收款表ID
//		CL_FinanceBill bill=billDao.getById(fbillid);
//		CL_FinanceReceipt receipt=receiptDao.getById(freceiveid);
//		BigDecimal unpay=bill.getFunPayAmount();//账单应付金额
//		BigDecimal remain=receipt.getFremainAmount();//收款未核销金额
//		if(remain.compareTo(unpay)==1||remain.compareTo(unpay)==0)//收款金额大于等于账单应付金额
//		{
//			bill.setFunPayAmount(new BigDecimal(0));//账单应付金额为0
//			bill.setFverification(true);//状态改成已结清
//			receipt.setFremainAmount(remain.subtract(unpay));//收款减去相应金额
//		}else if(remain.compareTo(unpay)==-1)
//		{
//			bill.setFunPayAmount(unpay.subtract(remain));//账单应付金额为0
//			receipt.setFremainAmount(new BigDecimal(0));//收款减去相应金额
//		}
//		billDao.update(bill);
//		receiptDao.update(receipt);
//		remain=receipt.getFremainAmount();
//		HashMap<String, Object> m = new HashMap<String, Object>();
//		m.put("success", "true");
//		m.put("remain", remain);
////		request.setAttribute("remain", remain);
//		return writeAjaxResponse(reponse, JSONUtil.getJson(m));
//	}
}
