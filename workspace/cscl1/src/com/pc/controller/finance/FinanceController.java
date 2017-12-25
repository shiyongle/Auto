package com.pc.controller.finance;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.SheetSettings;
import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.Colour;
import jxl.format.VerticalAlignment;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import cn.org.rapid_framework.page.Page;
import com.pc.controller.BaseController;
import com.pc.dao.UserRole.impl.UserRoleDao;
import com.pc.dao.finance.impl.FinanceDaoImpl;
import com.pc.dao.financeStatement.impl.FinanceStatementDaoImpl;
import com.pc.model.CL_Finance;
import com.pc.query.finance.CL_FinanceQuery;
import com.pc.util.JSONUtil;

@Controller
public class FinanceController extends BaseController {
	protected static final String LIST_JSP = "/pages/pc/finance/withdraw_list.jsp";
	protected static final String REJECT_JSP = "/pages/pc/finance/withdraw_reject.jsp";
	protected static final String PAY_JSP = "/pages/pc/finance/financePay.jsp";
	protected static final String BIIL_LIST = "/pages/pc/finance/billList.jsp";
	
	@Resource
	private FinanceDaoImpl financeDao;
	@Resource
	private FinanceStatementDaoImpl statementDao;
	
	private CL_FinanceQuery financeQuery;
	@Resource
	private UserRoleDao userRoleDao;

	public CL_FinanceQuery getFinanceQuery() {
		return financeQuery;
	}

	public void setFinanceQuery(CL_FinanceQuery financeQuery) {
		this.financeQuery = financeQuery;
	}

	@RequestMapping("/finance/list")
	public String list(HttpServletRequest request, HttpServletResponse reponse)
			throws Exception {
		return LIST_JSP;
	}
	
	@RequestMapping("/finance/billList")
	public String billList(HttpServletRequest request, HttpServletResponse reponse)
			throws Exception {
		return BIIL_LIST;
	}
	
	@RequestMapping("/finance/rejectload")
	public String rejectload(HttpServletRequest request, HttpServletResponse reponse,
			Integer fid,String famount,String fuserId)
			throws Exception {
		request.setAttribute("fid", fid);
		request.setAttribute("famount", famount);
		request.setAttribute("fuserId", fuserId);
		return REJECT_JSP;
	}

	// 编辑功能
	@RequestMapping("/finance/update")
	public String update(HttpServletRequest request, HttpServletResponse response,
			Integer fid,String famount,String fuserId) throws Exception {
//		CL_Finance finance = this.financeDao.getById(id);
		request.setAttribute("fid", fid);
		request.setAttribute("famount", famount);
		request.setAttribute("fuserId", fuserId);
		return PAY_JSP;
	}

	@RequestMapping("/finance/load")
	public String load(HttpServletRequest request, HttpServletResponse reponse,@ModelAttribute CL_FinanceQuery financeQuery)
			throws Exception {
		String pageNum = request.getParameter("page");
		String pageSize = request.getParameter("rows");
		String fusername=request.getParameter("fusername");
		String fstate=request.getParameter("fstate");
		if (financeQuery == null) {
			financeQuery = newQuery(CL_FinanceQuery.class, null);
		}
		if (pageNum != null) {
			financeQuery.setPageNumber(Integer.parseInt(pageNum));
		}
		if (pageSize != null) {
			financeQuery.setPageSize(Integer.parseInt(pageSize));
		}
		if(fstate !=null && !fstate.equals("")){
			financeQuery.setFstate(Integer.parseInt(fstate));
		}
		financeQuery.setFusername(null);
		if(fusername !=null && !fusername.equals("请选择")){
			financeQuery.setFusername(fusername);
		}
		financeQuery.setSortColumns("fs.fstate,fs.fcreate_time DESC");
		Page<CL_Finance> page = financeDao.findPage(financeQuery);
		HashMap<String, Object> m = new HashMap<String, Object>();
		m.put("total", page.getTotalCount());
		m.put("rows", page.getResult());
		return writeAjaxResponse(reponse, JSONUtil.getJson(m));
	}

	// 提现支付功能，加入支付流水号，处理方式信息(传入处理人,ID,处理时间)
	@RequestMapping("/finance/pay")
	public String pay(HttpServletRequest request,
			HttpServletResponse reponse, CL_Finance finance) throws Exception {
		//人工输入流水号
		finance.setFhandlerId(request.getSession().getAttribute("userRoleId").toString());
		finance.setFpaymentTime(new Date());
		finance.setFstate(1);
		financeDao.update(finance);
		/** 支付成功，添加明细表 Start */
//		CL_UserRole role=userRoleDao.getById(finance.getFuserId());
//		String userId="";
//		if(role!=null)
//		{
//			userId=role.getVmiUserFid();
//		}
//		statementDao.saveStatement(finance.getFid().toString(), null, 5, finance.getFamount(), -1, finance.getFuserId(), 0, userId);
		/** 支付成功，添加明细表 End */
		return writeAjaxResponse(reponse, "success");
	}
	
	/**导出excel**/
	@RequestMapping("/finance/exportExecl")
	public String exportExecl(HttpServletRequest request,HttpServletResponse reponse,@ModelAttribute CL_FinanceQuery financeQuery,Integer[] ids) throws Exception{
		boolean ises =true;
		/***************/
		Map<String, Object> result = new HashMap<String, Object>();
		WritableWorkbook wwb = null;
		OutputStream os = null;
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
		String fileName = "cps_"+format.format(new Date())+".xls";
		String path  = FinanceController.class.getResource("").toURI().getPath();
		path = path.substring(0,path.lastIndexOf("/WEB-INF"))+"/excel/"+fileName;
		try {
			File file = new File(path);
			if(!file.isFile()){
				file.createNewFile();
			}
			os = new FileOutputStream(file);
			wwb = Workbook.createWorkbook(os);
			WritableSheet wsheet = wwb.createSheet("司机提现", 0);//创建一个工作页，第一个参数的页名，第二个参数表示该工作页在excel中处于哪一页
			SheetSettings ss = wsheet.getSettings();
            ss.setVerticalFreeze(2);  // 设置行冻结前2行
            WritableFont font1 =new WritableFont(WritableFont.createFont("微软雅黑"), 10 ,WritableFont.BOLD);
            WritableFont font2 =new WritableFont(WritableFont.createFont("微软雅黑"), 9 ,WritableFont.NO_BOLD);
            WritableCellFormat wcf = new WritableCellFormat(font1);	  //设置样式，字体
	            wcf.setAlignment(Alignment.CENTRE);                   //平行居中
	            wcf.setVerticalAlignment(VerticalAlignment.CENTRE);   //垂直居中
            WritableCellFormat wcf2 = new WritableCellFormat(font2);  //设置样式，字体
	            wcf2.setBackground(Colour.LIGHT_ORANGE);
	            wcf2.setAlignment(Alignment.CENTRE);                  //平行居中
	            wcf2.setVerticalAlignment(VerticalAlignment.CENTRE);  //垂直居中
	            wcf2.setWrap(true);  
            wsheet.mergeCells( 0 , 0 , 14 , 0 ); // 合并单元格  
            Label titleLabel = new Label( 0 , 0 , " 同城物流调度平台 ",wcf);
            wsheet.addCell(titleLabel);
            wsheet.setRowView(0, 1000); // 设置第一行的高度
            int[] headerArrHight = {7,20,15,10,10,10,20,25,15,30,10,20,10,25};
            String headerArr[] = {"序号","申请时间","提现单号","司机名称","申请金额","提现方式","支付宝帐号","银行卡帐号","开户行","开户行所在地","处理人","支付时间","处理方式","支付流水号"};
            for (int i = 0; i < headerArr.length; i++) {
            	wsheet.addCell(new Label( i , 1 , headerArr[i],wcf));
            	wsheet.setColumnView(i, headerArrHight[i]);
            }
            if (financeQuery == null) {
            	financeQuery = newQuery(CL_FinanceQuery.class, null);
    		}
            if(financeQuery.getFusername()==""||"请选择".equals(financeQuery.getFusername()))
            {
            	financeQuery.setFusername(null);
            }
//            financeQuery.setType(1);
            if(ids!=null){
            	financeQuery.setIds(ids);
            }
    		List<CL_Finance> list = this.financeDao.find(financeQuery);
    		int conut = 2;
    		for(int i=0;i<list.size();i++){
    			jxl.write.NumberFormat nf2 = new jxl.write.NumberFormat("0.00");//设置数字格式
    			jxl.write.WritableCellFormat wcfN2 = new jxl.write.WritableCellFormat(nf2);//设置表单格式   
    			wcfN2.setBackground(Colour.LIGHT_ORANGE);
    			wcfN2.setAlignment(Alignment.CENTRE);                  //平行居中
    			wcfN2.setVerticalAlignment(VerticalAlignment.CENTRE);  //垂直居中
    			wsheet.addCell(new Label( 0 , conut ,String.valueOf(i+1),wcf2));
    			wsheet.addCell(new Label( 1 , conut ,list.get(i).getCreateTimeString(),wcf2));
    			wsheet.addCell(new Label( 2 , conut ,list.get(i).getNumber(),wcf2));
    			wsheet.addCell(new Label( 3 , conut ,list.get(i).getFusername(),wcf2));
    			wsheet.addCell(new jxl.write.Number(4,conut,Double.parseDouble(list.get(i).getFamount().toString()), wcfN2));
    			wsheet.addCell(new Label( 5 , conut ,withdrawType(list.get(i).getFwithdrawType().toString()) ,wcf2));
    			wsheet.addCell(new Label( 6 , conut ,list.get(i).getFalipayId() ,wcf2));
    			wsheet.addCell(new Label( 7 , conut ,list.get(i).getFbankAccount() ,wcf2));
    			wsheet.addCell(new Label( 8 , conut ,list.get(i).getFbankName() ,wcf2));
    			wsheet.addCell(new Label( 9 , conut ,list.get(i).getFbankAddress() ,wcf2));
    			wsheet.addCell(new Label( 10 , conut ,list.get(i).getFhandlername() ,wcf2));
    			wsheet.addCell(new Label( 11 , conut ,list.get(i).getPaymentTimeString() ,wcf2));
    			wsheet.addCell(new Label( 12 , conut ,list.get(i).getFtreatment() ,wcf2));
    			wsheet.addCell(new Label( 13 , conut ,list.get(i).getFserialNum() ,wcf2));
    			conut++;
    		}
			wwb.write();
			os.flush();
		} catch (Exception e) {
			ises =false;
			e.printStackTrace();
		}finally{
			try {
				if(wwb != null){
					wwb.close();
				}
				if(os != null){
					os.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		result.put("url", fileName);
		if(ises==true){
			result.put("success", true);
		}else{
			result.put("success", false);
		}
		return writeAjaxResponse(reponse,JSONUtil.getJson(result));
	}
	
	public String withdrawType(String value){
		 if(value.equals("1")){
			return "支付宝";
		}else if(value.equals("2")) {
			return "银行转帐";
		}else
		{
			return "提现方式未知";
		}
	}
}
