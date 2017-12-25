package com.pc.controller.financeStatement;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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
import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.org.rapid_framework.page.Page;

import com.pc.controller.BaseController;
import com.pc.dao.Car.impl.CarDao;
import com.pc.dao.UserRole.impl.UserRoleDao;
import com.pc.dao.addto.IaddtoDao;
import com.pc.dao.addto.impl.addtoDao;
import com.pc.dao.couponsDetail.ICouponsDetailDao;
import com.pc.dao.finance.impl.FinanceDaoImpl;
import com.pc.dao.financeStatement.IFinanceStatementDao;
import com.pc.dao.financeStatement.impl.FinanceStatementDaoImpl;
import com.pc.dao.order.impl.OrderDao;
import com.pc.model.CL_CouponsDetail;
import com.pc.model.CL_Finance;
import com.pc.model.CL_FinanceStatement;
import com.pc.model.CL_Order;
import com.pc.model.CL_UserRole;
import com.pc.query.financeStatement.FinanceStatementQuery;
import com.pc.util.Base64;
import com.pc.util.JSONUtil;
import com.pc.util.RSA;
import com.pc.util.ServerContext;

/**
 * 收支明细
 */

@Controller
public class FinanceStatementController extends BaseController {
	protected static final String PUBLICKEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDl5nbvmL8Q8tYGcJAgwS4qdqp2" +
			"Rwme5FKaR+11vXy89Biu8ruF/KmdS4pk+4gEmoPuHLFc6V6VQ77CgtpgboBDjveU" +
			"n3HnsN1N2LH/hmn8gDvw+0e7lLDFVEGC6L8d9z+yj0zGe0XMDeEW5zJlVCA2FOYq" +
			"oQAOkIntynv/nfyP6wIDAQAB";

	protected static final String PERSONALPUBLICKEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCcu9tqg+XyDB7qygFxn4UQu00T" +
			"WrnbQmIDUnE8zhDf9ZuCr5Czil1XVR4ApnpcVUTTXHoW2WpzBw1gm53OdKjgPc2Q" +
			"yRq+ROlo7NhYCRFan+b4p+RqGL+U+alMH1zv1Q+LSgQP6QF9loKAsC3i70KdBw4G" +
			"n7K8fTzoY8PtMqHlSwIDAQAB";
	
	protected static final String LIST_JSP = "/pages/pc/finance/statement.jsp";
	protected static final String DRIVERLIST_JSP = "/pages/pc/finance/driverStatement.jsp";
	protected static final String RECHARGELIST_JSP = "/pages/pc/finance/recharge_list.jsp";

	@Resource
	private FinanceStatementDaoImpl statementDao;
	@Resource
	private UserRoleDao userRoleDao;
	@Resource
	private IFinanceStatementDao financeStatementDao;
	@Resource
	private OrderDao orderDao;
	@Resource
	private addtoDao addDao;
	@Resource
	private FinanceDaoImpl financeDao;
	@Resource
	private CarDao carDao;
	@Resource
	private ICouponsDetailDao icouponsDetailDao;
	@Resource
	private IaddtoDao addtoDao;
	
	@RequestMapping("/financeStatement/list")
	public String list(HttpServletRequest request, HttpServletResponse reponse)
			throws Exception {
		return LIST_JSP;
	}


	@RequestMapping("/financeStatement/driverlist")
	public String driverlist(HttpServletRequest request, HttpServletResponse reponse)
			throws Exception {
		return DRIVERLIST_JSP;
	}
	
	@RequestMapping("/financeStatement/rechargelist")
	public String rechargelist(HttpServletRequest request, HttpServletResponse reponse)
			throws Exception {
		return RECHARGELIST_JSP;
	}

	/**
	 * 货主明细
	 */
	@RequestMapping("/financeStatement/load")
	public String load(HttpServletRequest request, HttpServletResponse reponse,@ModelAttribute FinanceStatementQuery statementQuery)
			throws Exception {
		String pageNum = request.getParameter("page");
		String pageSize = request.getParameter("rows");
		//角色id
		String username=request.getParameter("fusername");
		if (statementQuery == null) {
			statementQuery = newQuery(FinanceStatementQuery.class, null);
		}
		if (pageNum != null) {
			statementQuery.setPageNumber(Integer.parseInt(pageNum));
		}
		if (pageSize != null) {
			statementQuery.setPageSize(Integer.parseInt(pageSize));
		}
		statementQuery.setRoleid(1);
		statementQuery.setFusername(null);
		if(!"请选择".equals(username)&&username!=null)
		{
			statementQuery.setFusername(username);
		}
		statementQuery.setFstatus(1);
		statementQuery.setSortColumns("bt.fcreate_time desc");
		Page<CL_FinanceStatement> page = statementDao.findPage(statementQuery);
		HashMap<String, Object> m = new HashMap<String, Object>();
		m.put("total", page.getTotalCount());
		m.put("rows", page.getResult());
		return writeAjaxResponse(reponse, JSONUtil.getJson(m));
	}


	/**
	 * 车主明细表
	 */
	@RequestMapping("/financeStatement/driverload")
	public String driverload(HttpServletRequest request, HttpServletResponse reponse,@ModelAttribute FinanceStatementQuery statementQuery)
			throws Exception {
		HashMap<String, Object> m = new HashMap<String, Object>();
		String pageNum = request.getParameter("page");
		String pageSize = request.getParameter("rows");
		//角色id
		String username=request.getParameter("fusername");
		if (statementQuery == null) {
			statementQuery = newQuery(FinanceStatementQuery.class, null);
		}
		if (pageNum != null) {
			statementQuery.setPageNumber(Integer.parseInt(pageNum));
		}
		if (pageSize != null) {
			statementQuery.setPageSize(Integer.parseInt(pageSize));
		}
		statementQuery.setRoleid(2);
		statementQuery.setFusername(null);
		if(!"请选择".equals(username)&&username!=null)
		{
			statementQuery.setFusername(username);
		}
		statementQuery.setFstatus(1);
		statementQuery.setSortColumns("bt.fcreate_time desc");
		
		int fuserid;
		try{
			fuserid = Integer.valueOf(request.getParameter("fuserid"));
		}catch(Exception e){
			m.put("total", 0);
			m.put("rows", new ArrayList<>());
			return writeAjaxResponse(reponse, JSONUtil.getJson(m));
		}
		CL_UserRole user=userRoleDao.getById(fuserid);
		if(user.getRoleId() != 2){
			m.put("total", 0);
			m.put("rows", new ArrayList<>());
			return writeAjaxResponse(reponse, JSONUtil.getJson(m));
		}
		if(user!=null&&!user.equals("null"))
		{
			if(user.getVmiUserPhone()!=null&&!user.getVmiUserPhone().isEmpty())
			{
				/*String requestDataString="";
				String resString="";
				String Developer = RSA.encrypt("CS",PUBLICKEY);
				String hzUsenameE = RSA.encrypt(user.getVmiUserPhone(),PERSONALPUBLICKEY);
				requestDataString = "{\"data\": {\"Developer\": \""+Developer+"\",\"Usename\": \""+hzUsenameE+"\",\"vmiUserPhone\": \""+user.getVmiUserPhone()+"\"}}";
				requestDataString = Base64.encode(requestDataString.getBytes());
				resString = ServerContext.userMoneyList(requestDataString);
				if(!resString.equals("")){
					
				}
				JSONObject jsonobject = JSONObject.fromObject(resString);
				for(int i=0;i<jsonobject.getJSONArray("data").size();i++)
				{
					JSONObject subinfo=jsonobject.getJSONArray("data").getJSONObject(i);
					DongjingResult info=new DongjingResult();
					Set<String> set=subinfo.keySet();
					Iterator<String> it = set.iterator();
					while (it.hasNext()) {
						String key=it.next();
						info.put(key, subinfo.getString(key));
					}
					Resultitems.add(info);
				}
				
				if("true".equals(jsonobject.get("success").toString()))
				{
					JSONObject js = JSONObject.fromObject(JSONUtil.getJson(jo));
				}*/
				statementQuery.setFuserPhone(user.getVmiUserPhone());
				statementQuery.setSortColumns("bt.fcreate_time desc");
				Page<CL_FinanceStatement> page = statementDao.findPage1(statementQuery);
				m.put("total", page.getTotalCount());
				m.put("rows", page.getResult());
			}
		}
		
//		Page<CL_FinanceStatement> page = statementDao.findPage(statementQuery);
		//订单运费构建
//		Map<String,BigDecimal> map = new HashMap<String,BigDecimal>();
//		for(CL_FinanceStatement info :page.getResult()){			
////			根据订单号查到 这笔订单的所有收支明细数据
////			FinanceStatementQuery fsquery= new FinanceStatementQuery();
////			fsquery.setForderid(info.getForderId());
////			List<CL_FinanceStatement> finlist = financeStatementDao.find(fsquery);	
////			if(info.getFbusinessType()==1 || info.getFbusinessType()==2) {
////				//订单金额  就是 下单支付+追加货款
////				if(map.containsKey(info.getForderId())){
////					BigDecimal oldmoney = new BigDecimal(map.get(info.getForderId()).toString());
////					map.put(info.getForderId(),oldmoney.add(info.getFamount()));
////				}
////				else{
////					map.put(info.getForderId(),info.getFamount());
////				}	
////			}
//			//直接通过运费调整功能的司机明细没有对应的订单号，需加控制--------------by lancher
//			if(info.getFrelatedId() != null && !info.getFrelatedId().isEmpty() ){
//				//司机收支明细没有 1 2的记录 ,只能根据订单找运费
//				CL_Order coinfo = orderDao.getById(Integer.parseInt(info.getFrelatedId()));
//				if(coinfo!=null && coinfo.getFpayMethod()!=null){
//					List<CL_Addto> addto=addtoDao.getByOrderId(coinfo.getId());
//					BigDecimal AllCost=coinfo.getFreight();
//					BigDecimal faddNumber = BigDecimal.ZERO;
//					if(addto.size()>0){
//						for(CL_Addto ad:addto){
//							faddNumber=faddNumber.add(ad.getFcost());
//						}
//					}
//					AllCost=AllCost.add(faddNumber);
//					info.setForderyMoney(AllCost);
//				}
//				
//			} else {
//				info.setForderyMoney(BigDecimal.ZERO);
//			}
//			
//			
//			
//		}
		
		return writeAjaxResponse(reponse, JSONUtil.getJson(m));
	}

	/**
	 * 车主业务金额查询
	 */
	@RequestMapping("/statement/loadDriverBill")
	public String loadDriverBill(HttpServletRequest request,HttpServletResponse reponse,Integer userroleid) throws Exception{
		HashMap<String, Object> map=new HashMap<String,Object>();
		BigDecimal fdriverfee=new BigDecimal(0);//运费收入(运费+追加费用)
		BigDecimal freceive=new BigDecimal(0);//其它收入(eg.异常，跑空费)
		BigDecimal fwithdraw=new BigDecimal(0);//已提现金额(已处理+待处理)
		BigDecimal fbalance=new BigDecimal(0);//当前剩余总余额
		BigDecimal flocked=new BigDecimal(0);//锁定金额
		BigDecimal favailable=new BigDecimal(0);//可提现金额
		BigDecimal fpay=new BigDecimal(0);//其它支出
//		List<CL_Order> order=orderDao.getByUserRoleIdAndStatus(userroleid, 5);
		CL_UserRole user=userRoleDao.getById(userroleid);
		if(user!=null&&!user.equals("null"))
		{
//			/**运费收入*/
			/*if(order!=null)
			{
				for (CL_Order clorder : order) {//查询该车主的所有订单
					fdriverfee=fdriverfee.add(clorder.getFdriverfee());
//					List<CL_Addto> addlist=addDao.getByOrderId(clorder.getId());
//					for (CL_Addto cl_Addto : addlist) {
//						fdriverfee=fdriverfee.add(cl_Addto.getFdriverfee());
//					}
				}
			}*/
			List<CL_FinanceStatement> statementlist=financeStatementDao.getByUserId(userroleid);
			if(statementlist!=null)
			{
				for (CL_FinanceStatement statement : statementlist) {
					int businessType = statement.getFbusinessType();
					int type = statement.getFtype();
					int status = statement.getFstatus();
					/**运费收入*/
					if(businessType == 4 && status == 1){
						fdriverfee = fdriverfee.add(statement.getFamount());
					}
					
					/**异常收入*/
					if((businessType==3 || businessType == 10 || businessType == 5) && type == 1 && status == 1)//异常费用
					{
						freceive=freceive.add(statement.getFamount());
					}
					if((businessType==3 || businessType == 10) && type == -1 && status == 1)//异常费用
					{
						fpay=fpay.add(statement.getFamount());
					}
				}
			}
			/**已提现金额*/
			List<CL_Finance> financelist=financeDao.getbyUseridAndState(userroleid);
			if(financelist!=null)
			{
				for (CL_Finance finance : financelist) {
					fwithdraw=fwithdraw.add(finance.getFamount());
				}
			}
			/**当前剩余总余额*/
			//			if(car.getDriverPhone()!=null&&!car.getDriverPhone().isEmpty())
			if(user.getVmiUserPhone()!=null&&!user.getVmiUserPhone().isEmpty())
			{
				/*String requestDataString="";
				String resString="";
				JSONObject jo;
				String Developer = RSA.encrypt("CS",PUBLICKEY);
				String hzUsenameE = RSA.encrypt(user.getVmiUserPhone(),PERSONALPUBLICKEY);
				requestDataString = "{\"data\": {\"Developer\": \""+Developer+"\",\"Usename\": \""+hzUsenameE+"\"}}";
				requestDataString = Base64.encode(requestDataString.getBytes());
				resString = ServerContext.UsePayBalance(requestDataString);
				jo = JSONObject.fromObject(resString);
				if("true".equals(jo.get("success").toString()))
				{
					fbalance =new BigDecimal(jo.get("data").toString());
				}*/
				fbalance = user.getFbalance();


			}
			/**锁定金额 */
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
			Calendar c = Calendar.getInstance();  
			c.add(Calendar.DATE, - 7);  
			//        Date fcopTime = c.getTime();
			Date fcopTime = sdf.parse((sdf.format(c.getTime())));
			flocked=flocked.add(new BigDecimal(orderDao.getLockedAmount(userroleid, fcopTime)));
			/**可提现金额 */
			favailable=fbalance.subtract(flocked);
		}
		map.put("fdriverfee", fdriverfee);
		map.put("freceive", freceive);
		map.put("fwithdraw", fwithdraw);
		map.put("fbalance", fbalance);
		map.put("flocked", flocked);
		map.put("favailable", favailable);
		map.put("fpay", fpay);
		return writeAjaxResponse(reponse,JSONUtil.getJson(map));
	}

	//新建 保存明细
	@RequestMapping("/statement/save")
	public String save(HttpServletRequest request,HttpServletResponse reponse) throws Exception{
		String frelatedId=request.getParameter("frelatedId");
		String forderId=request.getParameter("forderId");
		int fbusinessType=Integer.parseInt(request.getParameter("fbusinessType"));
		BigDecimal famount=new BigDecimal(request.getParameter("famount"));
		int ftype=Integer.parseInt(request.getParameter("ftype"));
		String fuserid=request.getParameter("fuserid");
		int fuserroleId=Integer.parseInt(request.getParameter("fuserroleId"));
		int fpayType=Integer.parseInt(request.getParameter("fpayType"));
		statementDao.saveStatement(frelatedId,forderId, fbusinessType, famount, ftype, fuserroleId, fpayType,fuserid);
		return writeAjaxResponse(reponse, "success");
	}



	/**
	 * 货主业务金额查询 BY twr
	 */
	@RequestMapping("/statement/loadHuoBill")
	public String loadHuoBill(HttpServletRequest request,HttpServletResponse reponse,Integer userroleid) throws Exception{
		HashMap<String, Object> map=new HashMap<String,Object>();
		BigDecimal fcbalance=new BigDecimal(0);//充值金额
		BigDecimal fgift=new BigDecimal(0);//充值送的金额 
		BigDecimal fbalance=new BigDecimal(0);//当前剩余总余额
		BigDecimal fcostFreight=new BigDecimal(0);//支出运费金额
		BigDecimal fgoodRoll=new BigDecimal(0);//好运卷金额
		BigDecimal fpay=new BigDecimal(0);//其它支出
		BigDecimal freceive=new BigDecimal(0);//收入金额
		CL_UserRole user=userRoleDao.getById(userroleid);
		if(user!=null&&!user.equals("null"))
		{
			/**运费支出*/
			List<CL_FinanceStatement>  statementList=financeStatementDao.getByUserId(userroleid);//特殊字段 99表示 1，2货主收入
			if(statementList!=null)  {
				for(CL_FinanceStatement find:statementList){
					
					int status = find.getFstatus();
					int businessType = find.getFbusinessType();
					int type = find.getFtype();
					BigDecimal amount = find.getFamount();
					
					if((businessType==1 || businessType==2) && type==-1 && status == 1){
						fcostFreight=fcostFreight.add(amount);/**运费支出*/
					}
					if((businessType==3|| businessType==2) && type==1 && status == 1)//异常费用 收入
					{
						freceive=freceive.add(amount);
					}
					if((businessType==3|| businessType==2) && type==-1 && status == 1)//异常费用 支出
					{
						fpay=fpay.add(amount);
					}
					if(businessType==6 && status == 1)//充值收入
					{
						fcbalance=fcbalance.add(amount);
					}
					if(businessType==7 && status == 1)//充值送收入
					{
						fgift=fgift.add(amount);
					}
				}
			}
			/**用户使用过的好运卷*//*
			 * 优惠券重构，该逻辑已不适用
			List<CL_CouponsDetail> couList=this.icouponsDetailDao.getByUserRoleId(userroleid, 1);
			for(CL_CouponsDetail cos:couList){
				fgoodRoll=fgoodRoll.add(cos.getDollars());
			}*/
			//查出该用户所有有优惠券的订单
			List<CL_Order> orlist = orderDao.getAllCouponsOrder(userroleid);
			for (CL_Order order : orlist) {
				fgoodRoll=fgoodRoll.add(order.getSubtract() == null?BigDecimal.ZERO:order.getSubtract()).add(order.getDiscount() == null?BigDecimal.ZERO:order.getDiscount());
			}
			
			/**当前剩余总余额*/
			//			if(car.getDriverPhone()!=null&&!car.getDriverPhone().isEmpty())
			if(user.getVmiUserPhone()!=null&&!user.getVmiUserPhone().isEmpty())
			{
				/*String requestDataString="";
				String resString="";
				JSONObject jo;
				String Developer = RSA.encrypt("CS",PUBLICKEY);
				String hzUsenameE = RSA.encrypt(user.getVmiUserPhone(),PERSONALPUBLICKEY);
				requestDataString = "{\"data\": {\"Developer\": \""+Developer+"\",\"Usename\": \""+hzUsenameE+"\"}}";
				requestDataString = Base64.encode(requestDataString.getBytes());
				resString = ServerContext.UsePayBalance(requestDataString);
				jo = JSONObject.fromObject(resString);
				if(jo==null || jo.equals(""))
				{
					throw new Exception("LK！支付又挂了");
				}
				if("true".equals(jo.get("success").toString()))
				{
					fbalance =new BigDecimal(jo.get("data").toString());
				}*/
				fbalance = user.getFbalance();
			}
		}	
		map.put("fcbalance",fcbalance);
		map.put("freceive", freceive);
		map.put("fcostFreight", fcostFreight);
		map.put("fbalance", fbalance);
		map.put("fgoodRoll", fgoodRoll);
		map.put("fgift", fgift);
		map.put("fpay", fpay);
		return writeAjaxResponse(reponse,JSONUtil.getJson(map));
	}

	
	@RequestMapping("/financeStatement/loadRecharge")
	public String loadRecharge(HttpServletRequest request, HttpServletResponse reponse,@ModelAttribute FinanceStatementQuery statementQuery)
			throws Exception {
		String pageNum = request.getParameter("page");
		String pageSize = request.getParameter("rows");
		if (statementQuery == null) {
			statementQuery = newQuery(FinanceStatementQuery.class, null);
		}
		if (pageNum != null) {
			statementQuery.setPageNumber(Integer.parseInt(pageNum));
		}
		if (pageSize != null) {
			statementQuery.setPageSize(Integer.parseInt(pageSize));
		}
//		statementQuery.setFaddMoney(0);
		statementQuery.setSortColumns("bt.fcreate_time desc");
		Page<Map<String, Object>> page = statementDao.findRechargePage1(statementQuery);
		HashMap<String, Object> m = new HashMap<String, Object>();
		m.put("total", page.getTotalCount());
		m.put("rows", page.getResult());
//		List<Map<String, Object>> list = statementDao.findRechargePage(statementQuery);
//		m.put("total", list.size());
//		m.put("rows", list);
		return writeAjaxResponse(reponse, JSONUtil.getJson(m));
	}

	/**司机导出excel**/
	@RequestMapping("/financeStatement/exportExecl")
	public String exportExecl(HttpServletRequest request,HttpServletResponse reponse,@ModelAttribute FinanceStatementQuery statementQuery,Integer[] ids) throws Exception{
		boolean ises =true;
		String fusername=request.getParameter("fusername");
		/***************/
		Map<String, Object> result = new HashMap<String, Object>();
		WritableWorkbook wwb = null;
		OutputStream os = null;
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
		String fileName = "cps_"+format.format(new Date())+".xls";
		String path  = FinanceStatementController.class.getResource("").toURI().getPath();
		path = path.substring(0,path.lastIndexOf("/WEB-INF"))+"/excel/"+fileName;
		try {
			File file = new File(path);
			if(!file.isFile()){
				file.createNewFile();
			}
			os = new FileOutputStream(file);
			wwb = Workbook.createWorkbook(os);
			WritableSheet wsheet = wwb.createSheet("收支明细", 0);//创建一个工作页，第一个参数的页名，第二个参数表示该工作页在excel中处于哪一页
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
			int[] headerArrHight = {7,30,25,25,10,10,10,25,10,25};
			String headerArr[] = {"序号","创建时间","交易号","相关单号","业务类型","金额","收/支","司机名称","支付类型","备注"};
			for (int i = 0; i < headerArr.length; i++) {
				wsheet.addCell(new Label( i , 1 , headerArr[i],wcf));
				wsheet.setColumnView(i, headerArrHight[i]);
			}
			if (statementQuery == null) {
				statementQuery = newQuery(FinanceStatementQuery.class, null);
			}
			//            financeQuery.setType(1);
			/*if(ids!=null){
				statementQuery.setIds(ids);
			}
			statementQuery.setFusername(null);
			if(fusername !=null && !fusername.equals("请选择")){
				statementQuery.setFusername(fusername);
			}*/
			List<CL_FinanceStatement> list;
			int fuserid;
			try{
				fuserid = Integer.valueOf(request.getParameter("fuserid"));
			}catch(Exception e){
				result.put("success", false);
				result.put("msg", "请先选择司机帐号!");
				return writeAjaxResponse(reponse,JSONUtil.getJson(result));
			}
			CL_UserRole user=userRoleDao.getById(fuserid);
			if(user.getRoleId() != 2){
				result.put("success", false);
				result.put("msg", "请先选择司机帐号!");
				return writeAjaxResponse(reponse,JSONUtil.getJson(result));
			}
			if(user!=null&&!user.equals("null"))
			{
				if(user.getVmiUserPhone()!=null&&!user.getVmiUserPhone().isEmpty())
				{
					/*statementQuery.setFusername(user.getVmiUserPhone());
					statementQuery.setSortColumns("bt.fcreate_time desc");
					list = statementDao.findPays(statementQuery);*/
					statementQuery.setIds(ids);
					statementQuery.setFstatus(1);//只查询有效的记录
					statementQuery.setSortColumns("bt.fcreate_time desc");
					list = statementDao.find1(statementQuery);
				}else{
					result.put("success", false);
					result.put("msg", "请先选择司机帐号!");
					return writeAjaxResponse(reponse,JSONUtil.getJson(result));
				}
			}else{
				result.put("success", false);
				result.put("msg", "请先选择司机帐号!");
				return writeAjaxResponse(reponse,JSONUtil.getJson(result));
			}
			
//			List<CL_FinanceStatement> list = this.statementDao.find(statementQuery);
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
				wsheet.addCell(new Label( 3 , conut ,(list.get(i).getForderId() == null || "".equals(list.get(i).getForderId()))?list.get(i).getFrelatedId():list.get(i).getForderId(),wcf2));
				wsheet.addCell(new Label( 4 , conut ,businessType(list.get(i).getFbusinessType()) ,wcf2));
				wsheet.addCell(new jxl.write.Number(5,conut,Double.parseDouble(list.get(i).getFamount().toString()), wcfN2));
				wsheet.addCell(new Label( 6 , conut ,inOutType(list.get(i).getFtype()) ,wcf2));
				wsheet.addCell(new Label( 7 , conut ,list.get(i).getDriverName() ,wcf2));
				wsheet.addCell(new Label( 8 , conut ,payType(list.get(i).getFpayType()) ,wcf2));
				if(list.get(i).getCfstate() != null){
					wsheet.addCell(new Label( 9 , conut ,state(list.get(i).getCfstate()) ,wcf2));
				} else {
					wsheet.addCell(new Label( 9 , conut ,state(3) ,wcf2));
				}
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
			result.put("msg", "操作失败!");
		}
		return writeAjaxResponse(reponse,JSONUtil.getJson(result));
	}
	
	/**客户导出excel**/
	@RequestMapping("/financeStatement/exportExeclCus")
	public String exportExeclCus(HttpServletRequest request,HttpServletResponse reponse,@ModelAttribute FinanceStatementQuery statementQuery,Integer[] ids) throws Exception{
		boolean ises =true;
		String fusername=request.getParameter("fusername");
		/***************/
		Map<String, Object> result = new HashMap<String, Object>();
		WritableWorkbook wwb = null;
		OutputStream os = null;
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
		String fileName = "cps_"+format.format(new Date())+".xls";
		String path  = FinanceStatementController.class.getResource("").toURI().getPath();
		path = path.substring(0,path.lastIndexOf("/WEB-INF"))+"/excel/"+fileName;
		try {
			File file = new File(path);
			if(!file.isFile()){
				file.createNewFile();
			}
			os = new FileOutputStream(file);
			wwb = Workbook.createWorkbook(os);
			WritableSheet wsheet = wwb.createSheet("收支明细", 0);//创建一个工作页，第一个参数的页名，第二个参数表示该工作页在excel中处于哪一页
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
			int[] headerArrHight = {7,30,25,25,10,10,10,25,10,25};
			String headerArr[] = {"序号","创建时间","交易号","相关单号","业务类型","金额","收/支","客户名称","支付类型","备注"};
			for (int i = 0; i < headerArr.length; i++) {
				wsheet.addCell(new Label( i , 1 , headerArr[i],wcf));
				wsheet.setColumnView(i, headerArrHight[i]);
			}
			if (statementQuery == null) {
				statementQuery = newQuery(FinanceStatementQuery.class, null);
			}
			//            financeQuery.setType(1);
			if(ids!=null){
				statementQuery.setIds(ids);
			}
			statementQuery.setFusername(null);
			if(fusername !=null && !fusername.equals("请选择")){
				statementQuery.setFusername(fusername);
			}
			statementQuery.setFstatus(1);//只查询有效的记录
			statementQuery.setSortColumns("bt.fcreate_time desc");
			List<CL_FinanceStatement> list = this.statementDao.find(statementQuery);
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
				wsheet.addCell(new Label( 3 , conut ,(list.get(i).getForderId() == null || "".equals(list.get(i).getForderId()))?list.get(i).getFrelatedId():list.get(i).getForderId(),wcf2));
				wsheet.addCell(new Label( 4 , conut ,businessType(list.get(i).getFbusinessType()) ,wcf2));
				wsheet.addCell(new jxl.write.Number(5,conut,Double.parseDouble(list.get(i).getFamount().toString()), wcfN2));
				wsheet.addCell(new Label( 6 , conut ,inOutType(list.get(i).getFtype()) ,wcf2));
				wsheet.addCell(new Label( 7 , conut ,list.get(i).getFname() ,wcf2));
				wsheet.addCell(new Label( 8 , conut ,payType(list.get(i).getFpayType()) ,wcf2));
				wsheet.addCell(new Label( 9 , conut ,list.get(i).getFremark() ,wcf2));
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

	public String businessType(int value){
		if(value==1){
			return "下单支付";
		}else if(value==2) {
			return "补交货款";
		}else if(value==3) {
			return "运营异常";
		}else if(value==4) {
			return "订单完成";
		}else if(value==5) {
			return "提现";
		}else if(value==6) {
			return "充值";
		}else if(value==7) {
			return "转介绍奖励";
		}else if(value==8) {
			return "货主退款";
		}else if(value==9) {
			return "绑卡";
		}else if(value==10) {
			return "运费调整";
		}else if(value==11) {
			return "运费上缴";
		}else if(value==12) {
			return "运费线上";
		}else if(value==0) {
			return "余额调整";
		}else{
			return "未知";
		}
	}
	public String inOutType(int value){
		if(value==1){
			return "收入";
		}else if(value==-1) {
			return "支出";
		}else{
			return "未知";
		}
	}
	public String payType(int value){
		if(value==0){
			return "余额";
		}else if(value==1) {
			return "支付宝";
		}
		else if(value==2) {
			return "微信";
		}else if(value==3) {
			return "银联";
		}else if(value==4) {
			return "运费到付";
		}else if(value==5) {
			return "月结";
		}else{
			return "未知";
		}
	}
	public String state(int value){
		if(value==0){
			return "待处理";
		} else if (value==1) {
			return "成功";
		} else if(value==2){
			return "已驳回";
		} else {
			return "";
		}
	}


	
	/**导出excel**/
	@RequestMapping("/financeStatement/exportRechargeExecl")
	public String exportRechargeExecl(HttpServletRequest request,HttpServletResponse reponse,@ModelAttribute FinanceStatementQuery statementQuery,Integer[] ids) throws Exception{
		boolean ises =true;
//		String fusername=request.getParameter("fusername");
		/***************/
		Map<String, Object> result = new HashMap<String, Object>();
		WritableWorkbook wwb = null;
		OutputStream os = null;
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
		String fileName = "cps_"+format.format(new Date())+".xls";
		String path  = FinanceStatementController.class.getResource("").toURI().getPath().substring(1);
		path = path.substring(0,path.lastIndexOf("/WEB-INF"))+"/excel/"+fileName;
		try {
			File file = new File(path);
			if(!file.isFile()){
				file.createNewFile();
			}
			os = new FileOutputStream(file);
			wwb = Workbook.createWorkbook(os);
			WritableSheet wsheet = wwb.createSheet("收支明细", 0);//创建一个工作页，第一个参数的页名，第二个参数表示该工作页在excel中处于哪一页
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
			int[] headerArrHight = {7,40,20,30,25,10,10,25};
			String headerArr[] = {"序号","客户名称","业务员","部门","联系方式","预充值金额","充值送金额","充值时间"};
			for (int i = 0; i < headerArr.length; i++) {
				wsheet.addCell(new Label( i , 1 , headerArr[i],wcf));
				wsheet.setColumnView(i, headerArrHight[i]);
			}
			if (statementQuery == null) {
				statementQuery = newQuery(FinanceStatementQuery.class, null);
			}

			statementQuery.setFaddMoney(-1);//充值送金额为0的数据也导出  2016/12/5 by lancher
			statementQuery.setSortColumns(" bt.fcreate_time desc");
			if(ids!=null){
				statementQuery.setIds(ids);
			}
//			statementQuery.setFusername(null);
//			if(fusername !=null && !fusername.equals("请选择")){
//				statementQuery.setFusername(fusername);
//			}
			List<Map<String,Object>> list = this.statementDao.findRechargePage(statementQuery);
			int conut = 2;
			for(int i=0;i<list.size();i++){
				jxl.write.NumberFormat nf2 = new jxl.write.NumberFormat("0.00");//设置数字格式
				jxl.write.WritableCellFormat wcfN2 = new jxl.write.WritableCellFormat(nf2);//设置表单格式   
				wcfN2.setBackground(Colour.LIGHT_ORANGE);
				wcfN2.setAlignment(Alignment.CENTRE);                  //平行居中
				wcfN2.setVerticalAlignment(VerticalAlignment.CENTRE);  //垂直居中
				wsheet.addCell(new Label( 0 , conut ,String.valueOf(i+1),wcf2));
				wsheet.addCell(new Label( 1 , conut ,list.get(i).get("fcustName")==null?"":list.get(i).get("fcustName").toString(),wcf2));
				wsheet.addCell(new Label( 2 , conut ,list.get(i).get("fsaleMan")==null?"":list.get(i).get("fsaleMan").toString(),wcf2));
				wsheet.addCell(new Label( 3 , conut ,list.get(i).get("fmanDept")==null?"":list.get(i).get("fmanDept").toString(),wcf2));
				wsheet.addCell(new Label( 4 , conut ,list.get(i).get("userPhone")==null?"":list.get(i).get("userPhone").toString() ,wcf2));
				wsheet.addCell(new jxl.write.Number(5,conut,Double.parseDouble(list.get(i).get("fmoney")==null?"0":list.get(i).get("fmoney").toString()), wcfN2));
				wsheet.addCell(new jxl.write.Number(6,conut,Double.parseDouble(list.get(i).get("faddMoney")==null?"0":list.get(i).get("faddMoney").toString()), wcfN2));
				wsheet.addCell(new Label( 7 , conut ,list.get(i).get("fcreateTime")==null?"":list.get(i).get("fcreateTime").toString() ,wcf2));
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



}
