package com.pc.controller.report;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;

import java.lang.String;
import java.math.BigDecimal;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.pc.controller.BaseController;
import com.pc.dao.Car.ICarDao;
import com.pc.dao.coupons.ICouponsDao;
import com.pc.dao.couponsDetail.ICouponsDetailDao;
import com.pc.dao.financeStatement.IFinanceStatementDao;
import com.pc.dao.report.IReportDao;
import com.pc.model.CL_Car;
import com.pc.model.CL_Coupons;
import com.pc.model.CL_CouponsDetail;
import com.pc.model.CL_FinanceStatement;
import com.pc.query.financeStatement.FinanceStatementQuery;
import com.pc.query.report.ReportQuery;
import com.pc.util.CalcTotalField;
import com.pc.util.JSONUtil;

@Controller
public class ReportController extends BaseController {
	protected static final String CUSTORDERREPORT_JSP= "/pages/pc/reportCenter/custOrderReport.jsp";
	protected static final String FREIGHTREPORT_JSP = "/pages/pc/reportCenter/freightReport.jsp";
	//新用户月账单
	protected static final String NEWUSERBILLREPORT_JSP= "/pages/pc/reportCenter/NewUserBillReport.jsp";
	/*** 表单提交日期绑定*/
	@Resource
	private IReportDao reportDao; 
	@Resource
	private ICarDao carDao;
	@Resource
	private IFinanceStatementDao financeStatementDao;
	@Resource
	private ICouponsDetailDao couponsDetailDao;
	@Resource
	private ICouponsDao couponsDao;
	
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");//写上你要的日期格式
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }
    
	@RequestMapping("/report/custOrderReport")
	public String list_report1(HttpServletRequest request, HttpServletResponse reponse)
			throws Exception {
		return CUSTORDERREPORT_JSP;
	}
	@RequestMapping("/report/freightReport")
	public String list_report2(HttpServletRequest request, HttpServletResponse reponse)
			throws Exception {
		return FREIGHTREPORT_JSP;
	}
	@RequestMapping("/report/loadUserBillReport")
	public String list_report3(HttpServletRequest request, HttpServletResponse reponse)
			throws Exception {
		return NEWUSERBILLREPORT_JSP;
	}
	
	@RequestMapping("/report/mxCustOrderCarList")
	public String mxCustOrderCarList(HttpServletResponse reponse){
		List<CL_Car> carList = carDao.getAllCar();
		return writeAjaxResponse(reponse, JSONUtil.getJson(carList));	
	}
	@RequestMapping("/report/custMonthOrderList")
	public String custMonthOrderList(HttpServletRequest request, HttpServletResponse reponse,ReportQuery reportquery)
			throws Exception {
		List<Map<String, Object>> list = reportDao.findRptMonthPage(".findRptMonthPage", reportquery);
		HashMap<String, Object> m = new HashMap<String, Object>();
		m.put("total",list.size());
		m.put("rows", list);
		return writeAjaxResponse(reponse, JSONUtil.getJson(m));	
	}
	
	//客户订单运费报表
		@RequestMapping("/report/custOrderOMxList")
		public String  custOrderOMxList(HttpServletRequest request, HttpServletResponse reponse,ReportQuery reportquery)
				throws Exception {
			reportquery.setSortColumns("co.create_time");
			List<Map<String, Object>> list = reportDao.findRptPage(".findRptPage", reportquery);
			for(Map<String, Object> map : list){
				BigDecimal fuserPay = BigDecimal.ZERO;
				BigDecimal fdriverEarn = BigDecimal.ZERO;
				BigDecimal fcpDollars = BigDecimal.ZERO;
				BigDecimal fdranorm=BigDecimal.ZERO;
				BigDecimal fuserranorm=BigDecimal.ZERO;
				
				
				//订单id
				String  orderNumber = map.get("orderNumber").toString();
				//货主id
				int couserid = Integer.parseInt(map.get("couserid").toString());
				//司机id
				int fcaruserid = Integer.parseInt(map.get("fcaruserid")==null?"0":map.get("fcaruserid").toString());
				FinanceStatementQuery fsquery= new FinanceStatementQuery();
				//根据订单号查到 这笔订单的所有收支明细数据
				fsquery.setForderid(orderNumber);
				List<CL_FinanceStatement> finlist = financeStatementDao.find(fsquery);
				for(CL_FinanceStatement fsinfo:finlist){
					// 业务类型是下单支付、追加货款   
					if(fsinfo.getFbusinessType()==1 || fsinfo.getFbusinessType()==2 ) {
						//收支明细用户是订单对应的货主
						if(fsinfo.getFuserroleId()==couserid){
							// 类型是负数，界面要先是正数所以取反
							fuserPay = fuserPay.add(fsinfo.getFamount().multiply(new BigDecimal(-fsinfo.getFtype())));	
						}
					}
					if(fsinfo.getFbusinessType()==3){
						if(fsinfo.getFuserroleId()==couserid){
							// 类型是负数，界面要先是正数所以取反
							fuserranorm=fuserranorm.add(fsinfo.getFamount().multiply(new BigDecimal(fsinfo.getFtype())));
						}
						if(fsinfo.getFuserroleId()==fcaruserid){
							fdranorm=fdranorm.add(fsinfo.getFamount().multiply(new BigDecimal(fsinfo.getFtype())));
						}
					}
					 
				}
				//货到付款走线下,支付明细中没有记录,要取订单上的运费
				if(fuserPay.compareTo(BigDecimal.ZERO)==0){
					fuserPay = new BigDecimal(map.get("freight").toString());
				}
				fdriverEarn=new BigDecimal(map.get("fdriverfee").toString());
				//fdriverEarn = fuserPay.multiply(new BigDecimal("0.95"));
			 //	fdriverEarn = fuserPay.multiply(CalcTotalField.calDriverFee(fuserPay)).setScale(1,BigDecimal.ROUND_HALF_UP);
				fcpDollars = fcpDollars.add(map.get("subtract") == null?BigDecimal.ZERO:new BigDecimal(map.get("subtract").toString())).add(map.get("discount") == null?BigDecimal.ZERO:new BigDecimal(map.get("discount").toString()));
				map.put("fcpDollars", fcpDollars);
				map.put("fuserPay", fuserPay);
				map.put("fdriverEarn", fdriverEarn);
				map.put("fuserranorm", fuserranorm);
				map.put("fdranorm", fdranorm);
				
			}
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("total",list.size());
			map.put("rows", list);
			return writeAjaxResponse(reponse, JSONUtil.getJson(map));	
		}	
	
//	@RequestMapping("/report/custOrderMxList")
//	public String  custOrderMxList(HttpServletRequest request, HttpServletResponse reponse,ReportQuery reportquery)
//			throws Exception {
//		List<Map<String, Object>> list = reportDao.findRptPage(".findRptPage", reportquery);
//		for(Map<String, Object> map : list){
//			BigDecimal fuserPay = BigDecimal.ZERO;
//			BigDecimal fdriverEarn = BigDecimal.ZERO;
//			//订单id
//			String  orderNumber = map.get("orderNumber").toString();
//			//货主id
//			int couserid = Integer.parseInt(map.get("couserid").toString());
//			//司机id
//			int fcaruserid = Integer.parseInt(map.get("fcaruserid").toString());
//			FinanceStatementQuery fsquery= new FinanceStatementQuery();
//			//根据订单号查到 这笔订单的所有收支明细数据
//			fsquery.setForderid(orderNumber);
//			List<CL_FinanceStatement> finlist = financeStatementDao.find(fsquery);
//			for(CL_FinanceStatement fsinfo:finlist){
//				// 类型是月结    
//				if(fsinfo.getFpayType()==5) {
//					//收支明细用户是订单对应的货主
//					if(fsinfo.getFuserroleId()==couserid){
//						// 类型是负数，界面要先是正数所以取反
//						fuserPay = fuserPay.add(fsinfo.getFamount().multiply(new BigDecimal(-fsinfo.getFtype())));	
//					}
//					//收支明细用户是订单对应的司机
//					else if(fsinfo.getFuserroleId()==fcaruserid){					
//						fdriverEarn = fdriverEarn.add(fsinfo.getFamount().multiply(new BigDecimal(fsinfo.getFtype())));	
//					}
//				}
//			}
//			map.put("fuserPay", fuserPay);
//			map.put("fdriverEarn", fdriverEarn);
//		}
//		HashMap<String, Object> map = new HashMap<String, Object>();
//		map.put("total",list.size());
//		map.put("rows", list);
//		//合计通过前端计算,此处的合计 不仅仅是汇总会费这么简单
//		List<Map<String, String>> totallist = new ArrayList<Map<String, String>>();
//		HashMap<String, String> resMap = CalcTotalField.docalTotal(list, new String[]{"freight"});
//			totallist.add(resMap);
//			resMap.put("createTime", "合计");
//			map.put("footer",totallist);
//		return writeAjaxResponse(reponse, JSONUtil.getJson(map));	
//	}	
    
	@RequestMapping("/report/findUserMonthBillList")
	public String findUserMonthBillList(HttpServletRequest request, HttpServletResponse reponse,ReportQuery reportquery)
			throws Exception {
		List<Map<String, Object>> list = reportDao.findUserMonthBillPage(".loadUserBillReport", reportquery);
		HashMap<String, Object> m = new HashMap<String, Object>();
		m.put("total",list.size());
		m.put("rows", list);
		return writeAjaxResponse(reponse, JSONUtil.getJson(m));	
	}	
	
	@RequestMapping("/report/findDriverFreightList")
	public String findDriverFreightList(HttpServletRequest request, HttpServletResponse reponse,ReportQuery reportquery)
			throws Exception {
		List<Map<String, Object>> list = reportDao.findDriverFreightPage(".findDriverFreightPage", reportquery);
		HashMap<String, Object> m = new HashMap<String, Object>();
		m.put("total",list.size());
		m.put("rows", list);
		return writeAjaxResponse(reponse, JSONUtil.getJson(m));	
	}	

}
