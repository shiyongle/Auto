package Com.Controller.order;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import Com.Base.Util.DJException;
import Com.Base.Util.DataUtil;
import Com.Base.Util.ExcelUtil;
import Com.Base.Util.JsonUtil;
import Com.Base.Util.ListResult;
import Com.Base.Util.ServerContext;
import Com.Base.Util.mySimpleUtil.MySimpleToolsZ;
import Com.Base.data.LogAction;
import Com.Dao.System.IBaseSysDao;
import Com.Dao.System.ICustproductDao;
import Com.Dao.System.IProductdefDao;
import Com.Dao.order.IDeliverapplyDao;
import Com.Dao.order.IDeliverratioDao;
import Com.Dao.order.IDeliversDao;
import Com.Entity.System.Customer;
import Com.Entity.System.ProducePlan;
import Com.Entity.System.Productdef;
import Com.Entity.System.Supplierboardplanconfig;
import Com.Entity.System.Useronline;
import Com.Entity.order.Deliverapply;
import Com.Entity.order.Delivers;
import Com.Entity.order.Saleorder;
import Com.Entity.order.SchemeDesignEntry;

@Controller
public class DeliverapplyController {
	Logger log = LoggerFactory.getLogger(DeliverapplyController.class);
	@Resource
	private IDeliversDao DeliversDao;
	@Resource
	private ICustproductDao CustproductDao;
	@Resource
	private IProductdefDao productdefDao ;
	@Resource
	private IDeliverratioDao deliverratioDao;
//	@Resource
//	private ISaleOrderDao saleOrderDao;
	@Resource
	private IDeliverapplyDao deliverapplyDao; 
	@Resource
	private IBaseSysDao baseSysDao;
	
	private static final String BASE_SQL_CUS_DELIVER_DETAIL = " SELECT sfname,fordesource, dcreatetime,fboxtype,fcustomerName,fcustomerNumber, sum(famount) famount , count(*) fcount FROM t_ord_deliverapply_cus_detail_view ";
	private static final List<String> nodaylist =new ArrayList<String>(Arrays.asList("2015-10-01","2015-10-02","2015-10-03"));

	@RequestMapping("/GetDeliverapplyList")
	public String GetDeliverapplyList(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
//		String sql = "select ifnull(d.fwerkname,'') fwerkname,u1.fname as fcreator,u2.fname as flastupdater,c.fname as fcustname,cpdt.fname cutpdtname,d.fid,d.fcreatorid,d.fcreatetime,d.fupdateuserid,d.fupdatetime,d.fnumber,d.fcustomerid,d.fcusproductid,date_format(d.farrivetime,'%Y-%m-%d %T') farrivetime,d.flinkman,d.flinkphone,d.famount,d.faddress,d.fdescription,fsaleorderid,ifnull(fordernumber,'') fordernumber,forderentryid,ifnull(d.fiscreate,0) fiscreate,d.fstate,d.ftype ftype,ifnull(pd.fcharacter,'') fcharacter from t_ord_deliverapply d left join t_sys_user u1 on u1.fid=d.fcreatorid left join t_sys_user u2 on u2.fid=d.fupdateuserid  left join t_bd_customer c on c.fid=d.fcustomerid left join t_bd_custproduct cpdt on cpdt.fid=d.fcusproductid left join t_ord_schemedesignentry pd on d.ftraitid=pd.fid ";
		String sql = "select d.fordernumber,ifnull(d.fwerkname,'') fwerkname,u1.fname as fcreator,u2.fname as flastupdater,c.fname as fcustname,cpdt.fname cutpdtname,d.fid,d.fcreatorid,d.fcreatetime,d.fupdateuserid,d.fupdatetime,d.fnumber,d.fcustomerid,d.fcusproductid,date_format(d.farrivetime,'%Y-%m-%d %T') farrivetime,d.flinkman,d.flinkphone,d.famount,d.faddress,d.fdescription,fsaleorderid,ifnull(fordernumber,'') fordernumber,forderentryid,ifnull(d.fiscreate,0) fiscreate,d.fstate,d.ftype ftype,ifnull(d.fcharacter,'') fcharacter,ifnull(ftraitid,'') ftraitid,d.foutQty from t_ord_deliverapply d left join t_sys_user u1 on u1.fid=d.fcreatorid left join t_sys_user u2 on u2.fid=d.fupdateuserid  left join t_bd_customer c on c.fid=d.fcustomerid left join t_bd_custproduct cpdt on cpdt.fid=d.fcusproductid where d.fboxtype=0";
		ListResult result;
		reponse.setCharacterEncoding("utf-8");
		try {
			result = deliverapplyDao.QueryFilterList(sql, request);
			reponse.getWriter().write(JsonUtil.result(true,"",result));
		} catch (DJException e) {
			reponse.getWriter().write(JsonUtil.result(false,e.getMessage(),"",""));
		}
	
		return null;
	}
	
	@RequestMapping("/GetDeliverapplyListMV")
	public String GetDeliverapplyListMV(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		request.setAttribute("nocount", "nocount");
		String fcustomerid = request.getParameter("fcustomerid");
//		String sql = "select fordernumber,fwerkname,_creator fcreator,_lastupdater flastupdater,_custname fcustname,_custpdtname cutpdtname,fid,fcreatetime,fupdatetime,fnumber,fcustomerid,fcusproductid,date_format(farrivetime,'%Y-%m-%d %T') farrivetime,flinkman,flinkphone,famount,faddress,fdescription,fsaleorderid,fordernumber,forderentryid,ifnull(fiscreate,0) fiscreate,fstate,ftype,fcharacter,ftraitid,foutQty,_suppliername fsupplier from t_ord_deliverapply_card_mv";
		String sql = "SELECT c.fisupdate,fordernumber,fwerkname,_creator fcreator,_lastupdater flastupdater,_custname fcustname,_custpdtname cutpdtname,mv.fid,mv.fcreatetime,fupdatetime,mv.fnumber, "+
							"mv.fcustomerid,fcusproductid,DATE_FORMAT(farrivetime,'%Y-%m-%d %T') farrivetime,flinkman,flinkphone,famount,faddress,mv.fdescription,fsaleorderid,fordernumber, "+
							"forderentryid,IFNULL(fiscreate,0) fiscreate,mv.fstate,mv.ftype,mv.fcharacter,mv.ftraitid,mv.foutQty,_suppliername fsupplier FROM t_ord_deliverapply_card_mv mv LEFT JOIN  "+
							"t_bd_custproduct c ON mv.fcusproductid=c.fid ";
		if(!StringUtils.isEmpty(fcustomerid)){
			sql += " where mv.fcustomerid = '"+fcustomerid+"'";
		}
		ListResult result;
		try {
			result = deliverapplyDao.QueryFilterList(sql, request);
			result.setTotal("10000");
			response.getWriter().write(JsonUtil.result(true,"",result));
		} catch (DJException e) {
			response.getWriter().write(JsonUtil.result(false,e.getMessage(),"",""));
		}
		return null;
	}
	
	@RequestMapping("/GetDeliverapplyCustList")
	public String GetDeliverapplyCustList(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		request.setAttribute("djsort","d.fcreatetime desc"); 
		String userid = ((Useronline)request.getSession().getAttribute("Useronline")).getFuserid();
//		String sql = "select ifnull(d.fwerkname,'') fwerkname,u1.fname as fcreator,u2.fname as flastupdater,c.fname as fcustname,cpdt.fname cutpdtname,ifnull(cpdt.fspec,'') fspec,d.fid,d.fcreatorid,d.fcreatetime,d.fupdateuserid,d.fupdatetime,d.fnumber,d.fcustomerid,d.fcusproductid,date_format(d.farrivetime,'%Y-%m-%d %H') farrivetime,d.flinkman,d.flinkphone,d.famount,d.faddress,d.fdescription,fsaleorderid,ifnull(fordernumber,'') fordernumber,forderentryid,ifnull(d.fiscreate,0) fiscreate,d.fstate,d.ftype ftype,ifnull(pd.fcharacter,'') fcharacter from t_ord_deliverapply d left join t_sys_user u1 on u1.fid=d.fcreatorid left join t_sys_user u2 on u2.fid=d.fupdateuserid  left join t_bd_customer c on c.fid=d.fcustomerid left join t_bd_custproduct cpdt on cpdt.fid=d.fcusproductid left join t_bd_userrelationcustp  p on p.fcustproductid=d.fcusproductid left join t_ord_schemedesignentry pd on pd.fid=d.ftraitid where  if( 0=(select count(*) from t_bd_userrelationcustp where fuserid='"+userid+"' ),ifnull(p.fuserid,'1')='1',ifnull(p.fuserid,'1')<>'1'and fuserid='"+userid+"')" 
		String sql = "select ifnull(d.fwerkname,'') fwerkname,u1.fname as fcreator,u2.fname as flastupdater,c.fname as fcustname,cpdt.fname cutpdtname,ifnull(cpdt.fspec,'') fspec,d.fid,d.fcreatorid,d.fcreatetime,d.fupdateuserid,d.fupdatetime,d.fnumber,d.fcustomerid,d.fcusproductid,date_format(d.farrivetime,'%Y-%m-%d %H') farrivetime,d.flinkman,d.flinkphone,d.famount,d.faddress,d.fdescription,fsaleorderid,ifnull(fordernumber,'') fordernumber,forderentryid,ifnull(d.fiscreate,0) fiscreate,d.fstate,d.ftype ftype,ifnull(d.fcharacter,'') fcharacter from t_ord_deliverapply d left join t_sys_user u1 on u1.fid=d.fcreatorid left join t_sys_user u2 on u2.fid=d.fupdateuserid  left join t_bd_customer c on c.fid=d.fcustomerid left join t_bd_custproduct cpdt on cpdt.fid=d.fcusproductid left join t_bd_userrelationcustp  p on p.fcustproductid=d.fcusproductid  where  if( 0=(select count(*) from t_bd_userrelationcustp where fuserid='"+userid+"' ),ifnull(p.fuserid,'1')='1',ifnull(p.fuserid,'1')<>'1'and fuserid='"+userid+"')" 
		+ deliverapplyDao.QueryFilterByUser(request, "d.fcustomerid", null);
		ListResult result;
		reponse.setCharacterEncoding("utf-8");
		try {
			result = deliverapplyDao.QueryFilterList(sql, request);
			reponse.getWriter().write(JsonUtil.result(true,"",result));
		} catch (DJException e) {
			reponse.getWriter().write(JsonUtil.result(false,e.getMessage(),"",""));
		}
	
		return null;
	}
	
	@RequestMapping(value="/SaveDeliverapply")
	public  String SaveDeliverapply(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
	String result = "";
	Deliverapply vinfo=null;
	DateFormat f = new SimpleDateFormat("yyyy-MM-dd HH");
	
	try{
	String userid = ((Useronline)request.getSession().getAttribute("Useronline")).getFuserid();
	String fid=request.getParameter("fid");
//	String sql="select fid from t_ord_deliverapply where fiscreate=0 and fcustomerid=:fcustomerid and fcusproductid=:fcusproductid and farrivetime=:farrivetime and faddressid=:faddressid  and fid <>:dfid";
//	params p =new params();
//	p.put("fcustomerid",request.getParameter("fcustomerid") );
//	p.put("fcusproductid",request.getParameter("fcusproductid") );
//	p.put("farrivetime",request.getParameter("farrivetime").toString().substring(0,13)+":00:00");
//	p.put("faddressid",request.getParameter("faddressid") );
//	p.put("dfid",fid);
//	List list=deliverapplyDao.QueryBySql(sql, p);
//	if(list.size()>0){
//		throw new DJException("客户，客户产品，送达时间,地址一致的要货申请不能存在!");
//	}
	if(fid!=null&&!"".equals(fid))
	{
		vinfo=deliverapplyDao.Query(fid);
		if(vinfo == null){
			throw new DJException("当前修改记录FID无效！");
		}
		if(vinfo.getFiscreate()==1){
			throw new DJException("已生成要货管理不能修改!");
		}
		vinfo.setFnumber(request.getParameter("fnumber"));
	}else
	{
		vinfo=new Deliverapply();
		vinfo.setFid(deliverapplyDao.CreateUUid());
		vinfo.setFnumber(ServerContext.getNumberHelper().getNumber("t_ord_deliverapply", "Y", 4, false));
		vinfo.setFcreatorid(userid);
		vinfo.setFcreatetime(new Date());
		vinfo.setFiscreate(0);
	
	}
	vinfo.setFupdatetime(new Date());
	vinfo.setFupdateuserid(userid);
	
	SchemeDesignEntry entry;
	List<SchemeDesignEntry> entryList = new ArrayList<>();
	List<Deliverapply> applist= new ArrayList<>();
			DataUtil.verifyNotNullAndDataAndPermissions(null,
					new String[][] {
							{ "fcustomerid", "t_bd_customer", "", "" },
							{ "fcusproductid", "t_bd_custproduct", "FCUSTOMERID", "" } }, request,
					deliverapplyDao);

	vinfo.setFcustomerid(request.getParameter("fcustomerid"));
	if(vinfo.getFtraitid()!=null&&!"".equals(vinfo.getFtraitid()))//之前是有特性--要货申请修改
	{
		entry = (SchemeDesignEntry) deliverapplyDao.Query(SchemeDesignEntry.class,vinfo.getFtraitid());
		if(entry!=null){
			if(!vinfo.getFcusproductid().equals(request.getParameter("fcusproductid")))//客户产品发生变化，更新特性表数据，反写
			{
				entry.setFrealamount(entry.getFrealamount()-vinfo.getFamount());
				entry.setFallot(0);
				entryList.add(entry);
			}
			else if(vinfo.getFamount()!=new Integer(request.getParameter("famount")))//客户产品没有改变，数量发生改变，把数量全部还原
			{
				entry.setFrealamount(entry.getFrealamount()-vinfo.getFamount());
				entry.setFallot(0);
				entryList.add(entry);
			}
		}
		
	}
	vinfo.setFcusproductid(request.getParameter("fcusproductid"));
	vinfo.setFaddressid(request.getParameter("faddressid"));
	
	vinfo.setFaddress(request.getParameter("faddress"));
	
	if(!DataUtil.dateFormatCheck(request.getParameter("farrivetime"), true)){
		throw new DJException("送达日期格式错误或小于当前时间！");
	}
	vinfo.setFarrivetime(f.parse(request.getParameter("farrivetime").toString()));
	
	vinfo.setFlinkman(request.getParameter("flinkman"));
	vinfo.setFlinkphone(request.getParameter("flinkphone"));

	if(!DataUtil.positiveIntegerCheck(request.getParameter("famount"))){
		throw new DJException("要货申请数量必须大于0！");
	}
	vinfo.setFamount(new Integer(request.getParameter("famount")));
		
	vinfo.setFdescription(request.getParameter("fdescription"));
	vinfo.setFcharacter(request.getParameter("fcharacter"));
	vinfo.setFtraitid(request.getParameter("ftraitid"));
	vinfo.setFordernumber(request.getParameter("fordernumber"));
	//2015-06-06 增加制造商 lu
	vinfo.setFsupplierid(request.getParameter("fsupplierid"));
	if(StringUtils.isEmpty(vinfo.getFsupplierid())&&!"KNG83wEeEADgDmm4wKgCZ78MBA4=".equals(vinfo.getFcustomerid())){
		throw new DJException("制造商不能为空！");
	}
	int allowAmount=0;
	if(!StringUtils.isEmpty(vinfo.getFtraitid())){
		entry = (SchemeDesignEntry) deliverapplyDao.Query(SchemeDesignEntry.class,vinfo.getFtraitid());
		if(entry!=null){
		if(entryList.size()>0&&entryList.get(0).getFid().equals(entry.getFid()))//相同客户产品，数量不同的处理方式 
		{
			entry=entryList.get(0);
			entryList.remove(entry);
		}
		if(entry.getFallot()==0){
			allowAmount=entry.getFentryamount()-entry.getFrealamount();
			if(vinfo.getFamount()>allowAmount){
				throw new DJException("该特性产品的剩余数量为"+allowAmount+"，配送数量不能大于此数量！");
			}else{
				if(vinfo.getFamount()==allowAmount){
					entry.setFallot(1);
				}
				entry.setFrealamount(entry.getFrealamount()+vinfo.getFamount());
				entryList.add(entry); 
			}
		
		}else{
			vinfo.setFtraitid("");
		}
		}
	}
	applist.add(vinfo);
	deliverapplyDao.ExecSave(applist,entryList);
//	HashMap<String, Object> params = deliverapplyDao.ExecSave(vinfo);
//	System.out.println(params);
//	if (params.get("success") == Boolean.TRUE) {
		result = JsonUtil.result(true,"保存成功!", "", "");
//	} else {
//		result = JsonUtil.result(false,"保存失败!", "", "");
//	}
	}
	catch(Exception e)
	{
		result = JsonUtil.result(false,e.getMessage(), "", "");
	}
	reponse.setCharacterEncoding("utf-8");
	reponse.getWriter().write(result);
		return null;

	}
	
	//循环订单批量新增，当前用户关联客户地址只有一个才返回结果;
	@RequestMapping("/GetAddressListByuser")
	public String GetAddressListByuser(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		String sql = "select tba.fid,tba.fdetailaddress,tba.fnumber,tba.flinkman,tba.fphone,tba.fname from t_bd_Address tba where 1=1 ";
		sql = sql + deliverapplyDao.QueryFilterByUser(request,"fcustomerid","");
		ListResult result;
		reponse.setCharacterEncoding("utf-8");
		try {
			result = deliverapplyDao.QueryFilterList(sql, request);
			if(result.getData().size()==1){
				reponse.getWriter().write(JsonUtil.result(true, "", result));
			}else{
				reponse.getWriter().write(JsonUtil.result(false, "", "", ""));
			}
		} catch (DJException e) {
			reponse.getWriter().write(JsonUtil.result(false, e.getMessage(), "", ""));
		}

		return null;

	}

	//批量新增要货申请;
	@RequestMapping(value="/SavebatchDeliverapply")
	public  String SavebatchDeliverapply(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		
		try {
			String userid = ((Useronline) request.getSession().getAttribute(
					"Useronline")).getFuserid();
			List<Deliverapply> list= null;
//			DateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String customerid="";
			String sql="select fcustomerid from ( select fcustomerid  from t_bd_usercustomer where fuserid='"+userid+"'"+
					" union select c.fcustomerid from  t_sys_userrole r "+
					" left join t_bd_rolecustomer c on r.froleid=c.froleid where r.fuserid='"+userid+"' )  s where fcustomerid is not null " ;
			List custlist=deliverapplyDao.QueryBySql(sql);
			if(custlist.size()==1)
			{
				HashMap map=(HashMap)custlist.get(0);
				customerid =map.get("fcustomerid").toString();
			}else{
				throw new DJException("当前帐号存在多个客户,不能执行操作！");
			}
			
			list = (ArrayList<Deliverapply>)request.getAttribute("Deliverapply");
			if(list.size()<=0){
				throw new DJException("请至少填写一条记录再保存！");
			}
			Deliverapply deliverapply;
			SchemeDesignEntry entry;
			List<SchemeDesignEntry> entryList = new ArrayList<>();
			int allowAmount;
			HashMap<String, Integer> map = new HashMap<>();
			int deliverapplyAmount;
			for(int i=0;i<list.size();i++){
				deliverapply = list.get(i);
				if(deliverapply.getFamount()<1){
					throw new DJException("配送数量不能小于1！");
				}
				if(deliverapply.getFcusproductid()==null || deliverapply.getFcusproductid().equals("")){
					throw new DJException("包装物名称不能为空！");
				}
				if(deliverapply.getFaddressid()==null || deliverapply.getFaddressid().equals("")){
					throw new DJException("送货地址不能为空！");
				}
				if(StringUtils.isEmpty(deliverapply.getFsupplierid())&&!"KNG83wEeEADgDmm4wKgCZ78MBA4=".equals(customerid)){
					throw new DJException("制造商不能为空！");
				}
				if(!StringUtils.isEmpty(deliverapply.getFtraitid())){
					entry = (SchemeDesignEntry) deliverapplyDao.Query(SchemeDesignEntry.class,deliverapply.getFtraitid());
					if(entry!=null){
						if(entry.getFallot()==0){
							allowAmount=entry.getFentryamount()-entry.getFrealamount();
							deliverapplyAmount=map.containsKey(deliverapply.getFcusproductid())?map.get(deliverapply.getFcusproductid()):0;
							deliverapplyAmount += deliverapply.getFamount();
							if(deliverapplyAmount>allowAmount){
								throw new DJException("特性为“"+deliverapply.getFcharacter()+"”的产品的剩余数量为"+allowAmount+"，配送数量不能大于此数量！");
							}else{
								if(deliverapplyAmount==allowAmount){
									entry.setFallot(1);
									entry.setFrealamount(entry.getFentryamount());
								}else{
									entry.setFrealamount(entry.getFrealamount()+deliverapplyAmount);
								}
								for(SchemeDesignEntry entryObj : entryList){
									if(entryObj.getFid().equals(entry.getFid())){
										entryList.remove(entryObj);
										break;
									}
								}
								entryList.add(entry);
							}
							map.put(deliverapply.getFcusproductid(), deliverapplyAmount);
						}else{
							deliverapply.setFtraitid("");
						}
					}
					else{
						deliverapply.setFtraitid("");
					}
				}
				deliverapply.setFid(deliverapplyDao.CreateUUid());
				deliverapply.setFnumber(ServerContext.getNumberHelper().getNumber("t_ord_deliverapply", "Y", 4, false));
				deliverapply.setFcreatorid(userid);
				deliverapply.setFcreatetime(new Date());
				deliverapply.setFiscreate(0);
				deliverapply.setFupdatetime(new Date());
				deliverapply.setFupdateuserid(userid);
				deliverapply.setFcustomerid(customerid);
			}
			deliverapplyDao.ExecSave(list,entryList);
			reponse.getWriter().write(JsonUtil.result(true, "保存成功！", "", ""));
		} catch (Exception e) {
			reponse.getWriter().write(
					JsonUtil.result(false, "保存失败，" + e.getMessage(), "", ""));
		}

		return null;
	}
	//2015年6月6日 批量新增要货申请 by lu为不影响原先逻辑，重新拷贝了一份保存方法
	@RequestMapping(value="/SavebatchDeliverapplyLu")
	public  String SavebatchDeliverapplyLu(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		
		try {
			String userid = ((Useronline) request.getSession().getAttribute(
					"Useronline")).getFuserid();
			List<Deliverapply> list= null;
//			DateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			list = (ArrayList<Deliverapply>)request.getAttribute("Deliverapply");
			if(list.size()<=0){
				throw new DJException("请至少填写一条记录再保存！");
			}
			Deliverapply deliverapply;
			SchemeDesignEntry entry;
			List<SchemeDesignEntry> entryList = new ArrayList<>();
			int allowAmount;
			HashMap<String, Integer> map = new HashMap<>();
			int deliverapplyAmount;
			for(int i=0;i<list.size();i++){
				deliverapply = list.get(i);
				if(deliverapply.getFamount()<1){
					throw new DJException("配送数量不能小于1！");
				}
				if(deliverapply.getFcusproductid()==null || deliverapply.getFcusproductid().equals("")){
					throw new DJException("包装物名称不能为空！");
				}
				if(deliverapply.getFaddressid()==null || deliverapply.getFaddressid().equals("")){
					throw new DJException("送货地址不能为空！");
				}
				if(StringUtils.isEmpty(deliverapply.getFsupplierid())&&!"KNG83wEeEADgDmm4wKgCZ78MBA4=".equals(deliverapply.getFcustomerid())){
					throw new DJException("制造商不能为空！");
				}
				if(!StringUtils.isEmpty(deliverapply.getFtraitid())){
					entry = (SchemeDesignEntry) deliverapplyDao.Query(SchemeDesignEntry.class,deliverapply.getFtraitid());
					if(entry!=null){
						if(entry.getFallot()==0){
							allowAmount=entry.getFentryamount()-entry.getFrealamount();
							deliverapplyAmount=map.containsKey(deliverapply.getFcusproductid())?map.get(deliverapply.getFcusproductid()):0;
							deliverapplyAmount += deliverapply.getFamount();
							if(deliverapplyAmount>allowAmount){
								throw new DJException("特性为“"+deliverapply.getFcharacter()+"”的产品的剩余数量为"+allowAmount+"，配送数量不能大于此数量！");
							}else{
								if(deliverapplyAmount==allowAmount){
									entry.setFallot(1);
									entry.setFrealamount(entry.getFentryamount());
								}else{
									entry.setFrealamount(entry.getFrealamount()+deliverapplyAmount);
								}
								for(SchemeDesignEntry entryObj : entryList){
									if(entryObj.getFid().equals(entry.getFid())){
										entryList.remove(entryObj);
										break;
									}
								}
								entryList.add(entry);
							}
							map.put(deliverapply.getFcusproductid(), deliverapplyAmount);
						}else{
							deliverapply.setFtraitid("");
						}
					}
					else{
						deliverapply.setFtraitid("");
					}
				}
				deliverapply.setFid(deliverapplyDao.CreateUUid());
				deliverapply.setFnumber(ServerContext.getNumberHelper().getNumber("t_ord_deliverapply", "Y", 4, false));
				deliverapply.setFcreatorid(userid);
				deliverapply.setFcreatetime(new Date());
				deliverapply.setFiscreate(0);
				deliverapply.setFupdatetime(new Date());
				deliverapply.setFupdateuserid(userid);
			}
			deliverapplyDao.ExecSave(list,entryList);
			reponse.getWriter().write(JsonUtil.result(true, "保存成功！", "", ""));
		} catch (Exception e) {
			reponse.getWriter().write(
					JsonUtil.result(false, "保存失败，" + e.getMessage(), "", ""));
		}

		return null;
	}
	
	@RequestMapping("/DelDeliverapplyList")
	public String DelDeliversList(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		String result = "";
		String fidcls = request.getParameter("fidcls");
		fidcls="('"+fidcls.replace(",","','")+"')";
		try {
			deliverapplyDao.DelDeliverapply(fidcls);
			result = JsonUtil.result(true,"删除成功!", "", "");
			reponse.setCharacterEncoding("utf-8");
		} catch (Exception e) {
			result = JsonUtil.result(false,e.getMessage(), "", "");
			log.error("DelDeliversList error", e);
		}
		reponse.getWriter().write(result);
		return null;
	}
	
	@RequestMapping("/getDeliverapplyInfo")
	public String getDeliverapplyInfo(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		reponse.setCharacterEncoding("utf-8");
		try {
			String fid = request.getParameter("fid");
			String sql = "select d.fordernumber,u1.fname as fcreator,u2.fname as flastupdater,c.fname as fcustomerid_fname,cpdt.fname fcusproductid_fname,d.fid,d.fcreatorid,d.fcreatetime,d.fupdateuserid,d.fupdatetime,d.fnumber,d.fcustomerid fcustomerid_fid,d.fcusproductid fcusproductid_fid,d.farrivetime,d.flinkman,d.flinkphone,d.famount,d.faddress,d.fdescription ,d.faddressid faddressid_fid,ifnull(a.fname,'') faddressid_fname,d.ftraitid,d.fcharacter,d.fsupplierid from t_ord_deliverapply d left join t_sys_user u1 on  u1.fid=d.fcreatorid left join t_sys_user u2 on  u2.fid=d.fupdateuserid left join t_bd_customer c on c.fid=d.fcustomerid left join t_bd_custproduct cpdt on cpdt.fid=d.fcusproductid  left join t_bd_address a on a.fid=d.faddressid where d.fid = '"+request.getParameter("fid")+"'" ;
			List<HashMap<String,Object>> result= deliverapplyDao.QueryBySql(sql);
			reponse.getWriter().write(JsonUtil.result(true,"","",result));
		} catch (Exception e) {
			reponse.getWriter().write(JsonUtil.result(false, e.getMessage(), "", ""));
		}
		return null;
	}
	
	@RequestMapping("/getSplitdeliverapplyInfo")
	public String getSplitdeliverapplyInfo(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		reponse.setCharacterEncoding("utf-8");
		try {
			String fid = request.getParameter("fid");
			String sql = "select d.fid,d.fnumber,d.famount,farrivetime from t_ord_deliverapply d where d.fid='"+fid+"' and ifnull(d.fiscreate,0)=0 ";
			List<HashMap<String,Object>> result= deliverapplyDao.QueryBySql(sql);
			if(result.size()==0)
			{
				throw new DJException("该要货申请已生成要货管理，不能拆分！");
			}
			reponse.getWriter().write(JsonUtil.result(true,"","",result));
		} catch (Exception e) {
			reponse.getWriter().write(JsonUtil.result(false, e.getMessage(), "", ""));
		}
		return null;
	}

	//拆分要货申请
	@RequestMapping(value = "/saveSplitDeliverapply")
	public String saveSplitDeliverapply(HttpServletRequest request,
			HttpServletResponse reponse) throws Exception {

		HashMap<String, Object> params;

		String json = request.getReader().readLine();

		JSONArray jsonA = getJsonArrayByS(json);
		
		try {
			String userid = ((Useronline) request.getSession().getAttribute(
					"Useronline")).getFuserid();
			Deliverapply sinfo=null;
			String number="";
			List<Deliverapply> list=new ArrayList<Deliverapply>();
			DateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			int totalamount = 0;
			int amount = 0;
				for (int i = 0; i < jsonA.size(); i++) {
					
					if(!DataUtil.positiveIntegerCheck(((JSONObject)jsonA.get(i)).getString("famount"))){
						throw new DJException("要货申请数量必须大于0！");
					}
					
					if(!DataUtil.dateFormatCheck(((JSONObject)jsonA.get(i)).getString("farrivetime"), true)){
						throw new DJException("送达日期格式错误或小于当前时间！");
					}
					
					if(i==0){
						totalamount=new Integer(((JSONObject)jsonA.get(i)).getString("ftotalnum"));
						
						DataUtil.verifyNotNullAndDataAndPermissionsByValue(null,new String[][] {
								{ "要货申请ID","t_ord_deliverapply","fcustomerid","", ((JSONObject)jsonA.get(i)).getString("fid") } 
								}, request, deliverapplyDao);
					}
					
					int famount=Integer.valueOf(((JSONObject)jsonA.get(i)).getString("famount"));
					Date farrivetime=f.parse(((JSONObject)jsonA.get(i)).getString("farrivetime").replace('T', ' '));
					if(sinfo==null)
					{
						String deliverapplyid=((JSONObject)jsonA.get(i)).getString("fid");
						sinfo=deliverapplyDao.Query(deliverapplyid);
						if(sinfo==null|| sinfo.getFiscreate()==1)
						{
							throw new DJException("该要货申请已经生成，不能拆分！");
						}
					}else
					{
						sinfo=(Deliverapply)sinfo.clone();
						sinfo.setFid(deliverapplyDao.CreateUUid());
						sinfo.setFnumber(ServerContext.getNumberHelper().getNumber("t_ord_deliverapply", "Y", 4, false));
					}
					amount += famount;
					sinfo.setFamount(famount);
					sinfo.setFarrivetime(farrivetime);
					list.add(sinfo);
			
				}
				if(totalamount!=amount){
					throw new DJException("该要货申请拆分数量总数必须等于"+totalamount+"！");
				}
				deliverapplyDao.ExecSave(list);
				reponse.getWriter().write(JsonUtil.result(true, "保存成功！", "", ""));
		} catch (Exception e) {
			reponse.getWriter().write(
					JsonUtil.result(false, "保存失败，" + e.getMessage(), "", ""));
		}

		return null;
	}
	
	private JSONArray getJsonArrayByS(String s) {

		JSONObject jso = JSONObject.fromObject(s);
		
		String dataT = jso.getString("data");
		
		JSONArray jsa = null;

		if (dataT.charAt(0) == '{') {
			dataT = "[" + dataT + "]";
		}

		jsa = JSONArray.fromObject(dataT);

		return jsa;
	}

	@RequestMapping(value = "/boarddeliverapplytoExcel")
	public String boarddeliverapplytoExcel(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		try {
			String sql = " select fnumber 申请订单号, _custpdtname 包装物名称, _custpdtnumber 包装物编号, famount 配送数量, _orderunit 单位, date_format(farrivetime, '%Y-%m-%d %H:%i') 配送时间, fstate 订单状态, flinkman 联系人, flinkphone 联系电话, faddress 配送地址, fdescription 备注 from t_ord_deliverapply_card_mv d where 1=1";
			request.setAttribute("nolimit","");
			request.setAttribute("nocount", true);
			ListResult result;
			result = deliverapplyDao.QueryFilterList(sql, request);
			ExcelUtil.toexcel(reponse,result);
		} catch (DJException e) {
			reponse.getWriter().write(
					JsonUtil.result(false, e.getMessage(), "", ""));
		}

		return null;

	}
	
	//取消生成
	@RequestMapping(value = "/CanceltoCreate")
	public String CanceltoCreate(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		reponse.setCharacterEncoding("utf-8");
		try {
			String fidcls = request.getParameter("fidcls");
			
			DataUtil.verifyNotNullAndDataAndPermissionsByValue(null,new String[][] {
					{ "要货申请ID","t_ord_deliverapply","fcustomerid","", fidcls } 
					}, request, deliverapplyDao);
			
			String fidcol="";
			String hql="";
				String [] fids=fidcls.split(",");
				for(int j=0;j<fids.length;j++)
				{
					if(j!=0)
					{
						hql+=" or ";
					}
					hql+="  fapplayid like '%"+ fids[j]+"%'";
				}
			
			List<Delivers> Deliverscls=deliverapplyDao.QueryByHql("select t FROM Delivers t  where   "+hql);
			for(int i=0;i<Deliverscls.size();i++)
			{
				
				
				if(Deliverscls.get(i).getFalloted()!=null && Deliverscls.get(i).getFalloted()==1)
				{
						throw new DJException("该要货申请已经分配!");
				}
				if(!StringUtils.isEmpty(Deliverscls.get(i).getForderentryid()))
				{
					Saleorder  sinfo=(Saleorder)deliverapplyDao.Query(Saleorder.class,Deliverscls.get(i).getForderentryid());
					if(sinfo!=null){
						throw new DJException("生产订单("+sinfo.getFnumber()+")未删除,不能取消,请先删除生产订单");
					}
				}
				if(i>=1){
					fidcol+=",";
				}
				fidcol+=Deliverscls.get(i).getFapplayid();
			}
			fidcol="('"+fidcol.replace(",","','")+"')";
			List resultlist=deliverapplyDao.QueryBySql("select fid from t_ord_deliverapply where fiscreate=0 and fid in "+fidcol);
			if(resultlist.size()>0)
			{
				throw new DJException("其中一申请未生成要货管理!");
			}
			
			deliverapplyDao.ExecCanceltoCreate(Deliverscls,fidcol);
			reponse.getWriter().write(JsonUtil.result(true,"取消成功！", "", ""));
		} catch (Exception e) {
			reponse.getWriter().write(JsonUtil.result(false, e.getMessage(), "", ""));
		}
		return null;

	}
	
	//指定生成
	@RequestMapping(value = "/AssigntoCreate")
	public String AssigntoCreate(HttpServletRequest request,
			HttpServletResponse reponse) throws Exception {
		try {
			String userid = ((Useronline)request.getSession().getAttribute("Useronline")).getFuserid();
			String fid = request.getParameter("fidcls");
			String fidcls="('"+fid.replace(",","','")+"')";
			
			if(!DataUtil.positiveIntegerCheck(request.getParameter("famount"))){
				throw new DJException("要货申请数量必须大于0！");
			}
			
			DataUtil.verifyNotNullAndDataAndPermissionsByValue(null,new String[][] {
					{ "要货申请ID", "t_ord_deliverapply", "fcustomerid", "", fidcls },
					{ "产品ID", "t_pdt_productdef", "fcustomerid", "", request.getParameter("fproductid") }	
					}, request, deliverapplyDao);
			
			String fproductid = request.getParameter("fproductid");
			
			int famount =Integer.valueOf(request.getParameter("famount"));
			
			
			String sql=" select fid,fnumber  from t_ord_deliverapply where  fdescription like '%补单%' and ftype<>1  and fid in "+fidcls;
			List<HashMap<String,Object>> clist=deliverapplyDao.QueryBySql(sql);
			if(clist.size()>0)
			{
				throw new DJException("生成失败！"+clist.get(0).get("fnumber")+"备注为补单，类型不是补单，不能操作！");
			}
		    sql ="SELECT distinct CONCAT (fcustomerid,',',farrivetime,',',faddressid,',',ftype) cid,fiscreate FROM t_ord_deliverapply where fid in "+fidcls;
			List<HashMap<String,Object>> result= deliverapplyDao.QueryBySql(sql);
			if(result.size()!=1)
			{
				throw new DJException("所选申请单已经生成或送货地址或客户或送达时间或类型不一致!");
			}
			if((Integer)result.get(0).get("fiscreate")==1)
			{
				throw new DJException("所选申请单已经生成要货管理");
			}
			
			sql="select * from  t_ord_deliverapply where fid in "+fidcls ;
			result= deliverapplyDao.QueryBySql(sql);
			for(int i=0;i<result.size();i++)
			{
				int  curamount=(Integer)result.get(i).get("famount");
				int rate=curamount%famount;
				if(rate!=0)
				{
					throw new DJException("该要货申请数量与生成数量的比例不为整数！");
				}
				result.get(i).put("ratio", curamount/famount);
			}
			
			deliverapplyDao.ExecCreatedelivers(fproductid, result, userid, famount);

//			deliverapplyDao.ExecUpateDeliverapply(fidcls,dcol, rcol,1);
//			sql = "update t_ord_deliverapply set fiscreate=1,fstate=1  where fiscreate=0 and fid in " + fidcls;
//			deliverapplyDao.ExecBySql(sql);
			
			reponse.getWriter().write(JsonUtil.result(true,"生成成功","",""));
		} catch (DJException e) {
			reponse.getWriter().write(JsonUtil.result(false, e.getMessage(), "", ""));
		}
		return null;

	}
	
	//生成管理
	@RequestMapping("/CreatetoDelivers")
	public String CreatetoDelivers(HttpServletRequest request,
			HttpServletResponse reponse) throws Exception {
		synchronized (this.getClass()){
			try {
				String fcustomerid=request.getParameter("fcustomerid");
				if(fcustomerid==null||"".equals(fcustomerid))
				{
				  throw new DJException("错误 ！请选择一条记录进行操作！");
				}
				String sql=" select fid,fnumber  from t_ord_deliverapply where fiscreate=0 and fdescription like '%补单%' and ftype<>1 and fcustomerid='"+fcustomerid+"'";
				List<HashMap<String,Object>> clist=deliverapplyDao.QueryBySql(sql);
				if(clist.size()>0)
				{
					throw new DJException("生成失败！"+clist.get(0).get("fnumber")+"备注为补单，类型不是补单，不能操作！");
				}
				String userid = ((Useronline)request.getSession().getAttribute("Useronline")).getFuserid();
				sql=" select *,CONCAT (fcustomerid,',',farrivetime,',',faddressid,',',ftype) fdetail  from t_ord_deliverapply where fiscreate=0 and ifnull(faddressid, '') <> '' and fcustomerid='"+fcustomerid+"' and fboxtype=0 AND IFNULL(fmaterialfid,'')='' order by fcusproductid" ;
				List<HashMap<String,Object>> deliverresult = deliverapplyDao.QueryBySql(sql);
				deliverapplyDao.ExecCreateToDelivers(deliverresult,userid,fcustomerid);
				reponse.getWriter().write(JsonUtil.result(true, "生成成功！", "", ""));
			} catch (DJException e) {
				reponse.getWriter().write(JsonUtil.result(false, e.getMessage(), "", ""));
			}
		}
		return null;
	}
	
	//更改状态
	@RequestMapping("updateDeliversType")
	public String updateDeliversType(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		try {
			DataUtil.verifyNotNullAndDataAndPermissionsByValue(null,new String[][] {
					{ "要货申请ID", "t_ord_deliverapply", "fcustomerid", "", request.getParameter("fids") }
					}, request, deliverapplyDao);
			
			String fids = " ('"+request.getParameter("fids").replace(",", "','")+"')";
			
			String ftype = request.getParameter("ftype");
			String sql = "update t_ord_deliverapply set ftype='"+ftype+"' where fid in"+fids + deliverapplyDao.QueryFilterByUser(request, "fcustomerid", null);
		
			deliverapplyDao.ExecBySql(sql);
			response.getWriter().write(JsonUtil.result(true, "更改状态成功！", "", ""));
		} catch (DJException e) {
			response.getWriter().write(JsonUtil.result(false, "更改状态失败！", "",""));
		}
		return null;
	}
	


	@RequestMapping(value="/SaveCustDeliverapply")
	public  String SaveCustDeliverapply(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
	String result = "";
	Deliverapply vinfo=null;
	DateFormat f = new SimpleDateFormat("yyyy-MM-dd HH");
//	DateFormat f = new SimpleDateFormat("yyyy-MM-dd");
	try{
	String userid = ((Useronline)request.getSession().getAttribute("Useronline")).getFuserid();
	String fid=request.getParameter("fid");
	if(fid!=null&&!"".equals(fid))
	{
		vinfo=deliverapplyDao.Query(fid);
		if(vinfo == null){
			throw new DJException("当前修改记录FID无效！");
		}
		if(vinfo.getFiscreate()==1){
			throw new DJException("已生成要货管理不能修改!");
		}
		vinfo.setFnumber(request.getParameter("fnumber"));
	}else
	{
		vinfo=new Deliverapply();
		vinfo.setFid(deliverapplyDao.CreateUUid());
		vinfo.setFnumber(ServerContext.getNumberHelper().getNumber("t_ord_deliverapply", "Y", 4, false));
		vinfo.setFcreatorid(userid);
		vinfo.setFcreatetime(new Date());
		vinfo.setFiscreate(0);
		
	}
	vinfo.setFordernumber(request.getParameter("fordernumber"));
	vinfo.setFupdatetime(new Date());
	vinfo.setFupdateuserid(userid);
	List<SchemeDesignEntry> entryList = new ArrayList<>();
	List<Deliverapply> applist= new ArrayList<>();
	SchemeDesignEntry entry;
			DataUtil.verifyNotNullAndDataAndPermissions(null,
					new String[][] {
							{ "fcustomerid", "t_bd_customer", "", "" },
							{ "fcusproductid", "t_bd_custproduct", "FCUSTOMERID", "" } }, request,
					deliverapplyDao);

	vinfo.setFcustomerid(request.getParameter("fcustomerid"));
	
	if(vinfo.getFtraitid()!=null&&!"".equals(vinfo.getFtraitid()))//之前是有特性--要货申请修改
	{
		entry = (SchemeDesignEntry) deliverapplyDao.Query(SchemeDesignEntry.class,vinfo.getFtraitid());
		if(!vinfo.getFcusproductid().equals(request.getParameter("fcusproductid")))//客户产品发生变化，更新特性表数据，反写
		{
			entry.setFrealamount(entry.getFrealamount()-vinfo.getFamount());
			entry.setFallot(0);
			entryList.add(entry);
		}
		else if(vinfo.getFamount()!=new Integer(request.getParameter("famount")))//客户产品没有改变，数量发生改变，把数量全部还原
		{
			entry.setFrealamount(entry.getFrealamount()-vinfo.getFamount());
			entry.setFallot(0);
			entryList.add(entry);
		}
	}
	vinfo.setFcusproductid(request.getParameter("fcusproductid"));
	vinfo.setFsupplierid(request.getParameter("fsupplierid"));
	if(StringUtils.isEmpty(vinfo.getFsupplierid())&&!"KNG83wEeEADgDmm4wKgCZ78MBA4=".equals(vinfo.getFcustomerid())){
		throw new DJException("制造商不能为空！");
	}
	vinfo.setFaddressid(request.getParameter("faddressid"));
	
	vinfo.setFaddress(request.getParameter("faddress"));
	
	if(!DataUtil.dateFormatCheck(request.getParameter("farrivedate"), true)){
		throw new DJException("送达日期格式错误或小于当前时间！");
	}
//	vinfo.setFarrivetime(f.parse(request.getParameter("farrivetime").toString()));
	String farrivetime=request.getParameter("farrivedate")+" "+("1".equals(request.getParameter("farrivettime"))?"09":"14");
	vinfo.setFarrivetime(f.parse(farrivetime));
	vinfo.setFlinkman(request.getParameter("flinkman"));
	vinfo.setFlinkphone(request.getParameter("flinkphone"));

	if(!DataUtil.positiveIntegerCheck(request.getParameter("famount"))){
		throw new DJException("要货申请数量必须大于0！");
	}
	vinfo.setFamount(new Integer(request.getParameter("famount")));
		
	vinfo.setFdescription(request.getParameter("fdescription"));
	vinfo.setFcharacter(request.getParameter("fcharacter"));
	vinfo.setFtraitid(request.getParameter("ftraitid"));
	int allowAmount=0;
	
	if(!StringUtils.isEmpty(vinfo.getFtraitid())){
		entry = (SchemeDesignEntry) deliverapplyDao.Query(SchemeDesignEntry.class,vinfo.getFtraitid());
		if(entry==null)
		{
			throw new DJException("该特性产品数据异常，不能保存");
		}
		if(entryList.size()>0&&entryList.get(0).getFid().equals(entry.getFid()))//相同客户产品，数量不同的处理方式 
		{
			entry=entryList.get(0);
			entryList.remove(entry);
		}
		if(entry.getFallot()==0){
			allowAmount=entry.getFentryamount()-entry.getFrealamount();
			if(vinfo.getFamount()>allowAmount){
				throw new DJException("该特性产品的剩余数量为"+allowAmount+"，配送数量不能大于此数量！");
			}else{
				if(vinfo.getFamount()==allowAmount){
					entry.setFallot(1);
				}
				entry.setFrealamount(entry.getFrealamount()+vinfo.getFamount());
				entryList.add(entry); 
			}
		}else{
			vinfo.setFtraitid("");
		}
	}
	applist.add(vinfo);
	deliverapplyDao.ExecSave(applist,entryList);
//	HashMap<String, Object> params = deliverapplyDao.ExecSave(vinfo);
//	System.out.println(params);
//	if (params.get("success") == Boolean.TRUE) {
		result = JsonUtil.result(true,"保存成功!", "", "");
//	} else {
//		result = JsonUtil.result(false,"保存失败!", "", "");
//	}
	}
	catch(Exception e)
	{
		result = JsonUtil.result(false,e.getMessage(), "", "");
	}
	reponse.setCharacterEncoding("utf-8");
	reponse.getWriter().write(result);
		return null;

	}
	
	
	@RequestMapping("/getCustDeliverapplyInfo")
	public String getCustDeliverapplyInfo(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		reponse.setCharacterEncoding("utf-8");
		try {
			String fid = request.getParameter("fid");
			String sql = "select d.fordernumber,u1.fname as fcreator,u2.fname as flastupdater,c.fname as fcustomerid_fname,cpdt.fname fcusproductid_fname,d.fid,d.fcreatorid,d.fcreatetime,d.fupdateuserid,d.fupdatetime,d.fnumber,d.fcustomerid fcustomerid_fid,d.fcusproductid fcusproductid_fid,date_format(d.farrivetime,'%Y-%m-%d') farrivedate,  case   when date_format(d.farrivetime,'%H')>12 then 2 else 1 end farrivettime,d.flinkman,d.flinkphone,d.famount,d.faddress,d.fdescription ,d.faddressid faddressid_fid,ifnull(a.fname,'') faddressid_fname,d.ftraitid,d.fcharacter,d.fsupplierid from t_ord_deliverapply d left join t_sys_user u1 on  u1.fid=d.fcreatorid left join t_sys_user u2 on  u2.fid=d.fupdateuserid left join t_bd_customer c on c.fid=d.fcustomerid left join t_bd_custproduct cpdt on cpdt.fid=d.fcusproductid  left join t_bd_address a on a.fid=d.faddressid where d.fid = '"+request.getParameter("fid")+"'" ;
			List<HashMap<String,Object>> result= deliverapplyDao.QueryBySql(sql);
			reponse.getWriter().write(JsonUtil.result(true,"","",result));
		} catch (Exception e) {
			reponse.getWriter().write(JsonUtil.result(false, e.getMessage(), "", ""));
		}
		return null;
	}
	
	/**
	 * 循环订单
	 *
	 * @param request
	 * @param reponse
	 * @return
	 * @throws IOException
	 *
	 * @date 2014-9-23 上午10:16:53  (ZJZ)
	 */
	@RequestMapping("/selectDeliverapplyCusts")
	public String selectDeliverapplyCusts(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		
		ListResult result;
		try {
			String sql = buildSql(request);
			request.setAttribute("djsort","d.fcreatetime desc");
			result = deliverapplyDao.QueryFilterList(sql, request);
			deliverapplyDao.addStateInfo(result.getData());
			reponse.getWriter().write(JsonUtil.result(true,"",result));
		} catch (DJException e) {
			reponse.getWriter().write(JsonUtil.result(false,e.getMessage(),"","")); 
		}
	
		return null;
	}
	
	@RequestMapping("/selectDeliverapplyCustsMV")
	public String selectDeliverapplyCustsMV(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		
		ListResult result;
		try {
			String userid = ((Useronline)request.getSession().getAttribute("Useronline")).getFuserid();
			String sql = "select _suppliername fsuppliername,_custpdtnumber,_orderunit forderunit,_creator fcreator,_lastupdater flastupdater,_custname,_custpdtname,_character fcharacter,_spec fspec,fwerkname,fid,date_format(fcreatetime,'%Y-%m-%d %H:%i') fcreatetime,fnumber,fcustomerid,fcusproductid,date_format(farrivetime,'%Y-%m-%d %H:%i') farrivetime,flinkman,flinkphone,famount,faddress,fdescription,fsaleorderid,fordernumber,forderentryid,ifnull(fiscreate,0) fiscreate,fstate,ftype,ftraitid,foutQty from t_ord_deliverapply_card_mv d where 1=1 ";
			if(deliverapplyDao.QueryExistsBySql("select 1 from t_bd_userrelationcustp where fuserid='"+userid+"'")){
				sql += " and fcusproductid IN (SELECT fcustproductid FROM t_bd_userrelationcustp WHERE fuserid = '"+userid+"')";
			}
			sql += deliverapplyDao.QueryFilterByUserofuser(request,"fcreatorid","and")+ deliverapplyDao.QueryFilterByUser(request, "fcustomerid", null);
			request.setAttribute("djsort","fcreatetime desc");
			result = deliverapplyDao.QueryFilterList(sql, request);
			deliverapplyDao.addStateInfo(result.getData());
			response.getWriter().write(JsonUtil.result(true,"",result));
		} catch (DJException e) {
			response.getWriter().write(JsonUtil.result(false,e.getMessage(),"","")); 
		}
	
		return null;
	}

	private String buildSql(HttpServletRequest request) throws UnsupportedEncodingException {
		String userid = ((Useronline)request.getSession().getAttribute("Useronline")).getFuserid();
//		String addCon = "";
//		String sqls = "select quote(fuserid) userid from t_bd_usertouser where fsuperuserid='"+userid+"'";
//		List<HashMap<String, Object>> demandList = deliverapplyDao.QueryBySql(sqls);
//		if(demandList.size()!=0){//关联用户过滤
//			StringBuilder userids=new StringBuilder("'"+userid+"'");
//			for(HashMap<String, Object> m : demandList){
//				userids.append(","+m.get("userid"));
//			}
//			addCon = "d.fcreatorid in ("+userids+")";
//		}else{
//			addCon = "d.fcreatorid = '"+userid+"'";
//		}
//		addCon=deliverapplyDao.QueryFilterByUserofuser(request,"d.fcreatorid","or");
//
//		String sqls = "select 1 from t_bd_userrelationcustp where fuserid='"+userid+"'";
//		List list = deliverapplyDao.QueryBySql(sqls);//客户产品过滤
//		if(list.size()>0){
//			sqls = " cpdt.fid IN (SELECT fcustproductid FROM t_bd_userrelationcustp WHERE fuserid = '"+userid+"')";
//			if(addCon.length()>0){
//				sqls +=addCon ;
//			}
//		}else{
////			sqls = "1=2";
//			sqls=(addCon.length()==0?"1=1":"1=2"+addCon);
//		}
////		String sql = "select cpdt.fnumber cusfnumber, cpdt.fspec fspec, cpdt.FORDERUNIT forderunit, ifnull(d.fwerkname,'') fwerkname,u1.fname as fcreator,u2.fname as flastupdater,c.fname as fcustname,cpdt.fname cutpdtname,ifnull(cpdt.fspec,'') fspec,d.fid,d.fcreatorid,date_format(d.fcreatetime,'%Y-%m-%d %H:%i') fcreatetime ,d.fupdateuserid,d.fupdatetime,d.fnumber,d.fcustomerid,d.fcusproductid,date_format(d.farrivetime,'%Y-%m-%d %H:%i') farrivetime,d.flinkman,d.flinkphone,d.famount,d.faddress,d.fdescription,fsaleorderid,ifnull(fordernumber,'') fordernumber,forderentryid,ifnull(d.fiscreate,0) fiscreate,d.fstate,d.ftype ftype,ifnull(pd.fcharacter,'') fcharacter,ifnull(pd.fid,'') ftraitid from t_ord_deliverapply d left join t_sys_user u1 on u1.fid=d.fcreatorid left join t_sys_user u2 on u2.fid=d.fupdateuserid  left join t_bd_customer c on c.fid=d.fcustomerid left join t_bd_custproduct cpdt on cpdt.fid=d.fcusproductid left join t_bd_userrelationcustp  p on p.fcustproductid=d.fcusproductid left join t_ord_schemedesignentry pd on pd.fid=d.ftraitid where (if( 0=(select count(*) from t_bd_userrelationcustp where fuserid='"+userid+"' ),ifnull(p.fuserid,'1')='1',ifnull(p.fuserid,'1')<>'1'and fuserid='"+userid+"') or "+addCon+")" 
////				+ deliverapplyDao.QueryFilterByUser(request, "d.fcustomerid", null);
//		String sql = "select s.fname fsuppliername, cpdt.fnumber cusfnumber,cpdt.FORDERUNIT forderunit, ifnull(d.fwerkname,'') fwerkname,u1.fname as fcreator,u2.fname as flastupdater,c.fname as fcustname,cpdt.fname cutpdtname,ifnull(cpdt.fspec,'') fspec,d.fid,d.fcreatorid,date_format(d.fcreatetime,'%Y-%m-%d %H:%i') fcreatetime ,d.fupdateuserid,d.fupdatetime,d.fnumber,d.fcustomerid,d.fcusproductid,date_format(d.farrivetime,'%Y-%m-%d %H:%i') farrivetime,d.flinkman,d.flinkphone,d.famount,d.faddress,d.fdescription,fsaleorderid,ifnull(fordernumber,'') fordernumber,forderentryid,ifnull(d.fiscreate,0) fiscreate,d.fstate,d.ftype ftype,ifnull(pd.fcharacter,'') fcharacter,ifnull(pd.fid,'') ftraitid,d.foutQty from t_ord_deliverapply d left join t_sys_user u1 on u1.fid=d.fcreatorid left join t_sys_user u2 on u2.fid=d.fupdateuserid  left join t_bd_customer c on c.fid=d.fcustomerid left join t_bd_custproduct cpdt on cpdt.fid=d.fcusproductid left join t_bd_userrelationcustp  p on p.fcustproductid=d.fcusproductid left join t_ord_schemedesignentry pd on pd.fid=d.ftraitid left join t_sys_supplier s on d.fsupplierid=s.fid where ("+sqls+") and d.fboxtype=0 " 
//				+ deliverapplyDao.QueryFilterByUser(request, "d.fcustomerid", null);
		String sql = "select _suppliername fsuppliername,_custpdtnumber,_orderunit forderunit,_creator fcreator,_lastupdater flastupdater,_custname,_custpdtname,_character fcharacter,_spec fspec,fwerkname,fid,date_format(fcreatetime,'%Y-%m-%d %H:%i') fcreatetime,fnumber,fcustomerid,fcusproductid,date_format(farrivetime,'%Y-%m-%d %H:%i') farrivetime,flinkman,flinkphone,famount,faddress,fdescription,fsaleorderid,fordernumber,forderentryid,ifnull(fiscreate,0) fiscreate,fstate,ftype,ftraitid,foutQty from t_ord_deliverapply_card_mv d where 1=1 ";
		if(deliverapplyDao.QueryExistsBySql("select 1 from t_bd_userrelationcustp where fuserid='"+userid+"'")){
			sql += " and fcusproductid IN (SELECT fcustproductid FROM t_bd_userrelationcustp WHERE fuserid = '"+userid+"')";
		}
		sql += deliverapplyDao.QueryFilterByUserofuser(request,"fcreatorid","and")+ deliverapplyDao.QueryFilterByUser(request, "fcustomerid", null);
		
//		
//		switch (MySimpleToolsZ.getMySimpleToolsZ().judgeSearchType(request)) {
//		case MySimpleToolsZ.COMMON_SEARCH:
//			sql += MySimpleToolsZ.getMySimpleToolsZ().buildMySearchBoxSQLFragment(request);
//			
//			break;
//			
//		case MySimpleToolsZ.TIME_SEARCH:
//			sql += MySimpleToolsZ.getMySimpleToolsZ().buildDateBetweenSQLFragment(request);
//			break;
//
//		default:
			
//			sql += " and d.farrivetime > date_sub(now(), interval 2 month)";
			
//			break;
//		}
		
//		sql += MySimpleToolsZ.getMySimpleToolsZ().buildDateBETWEENMonthSqlF(2, "d.farrivetime");
		
//		sql += " order by d.fnumber desc ";
		
		return sql;
	}
	
	/**
	 * 循环订单折线图
	 *
	 * @param request
	 * @param reponse
	 * @return
	 * @throws IOException
	 *
	 * @date 2014-9-23 上午10:16:53  (ZJZ)
	 */
	@RequestMapping("/selectDeliverapplyCusLineV")
	public String selectDeliverapplyCusLineV(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		
		ListResult result = new ListResult();
		
		try {
			
			String sql = MySimpleToolsZ.getMySimpleToolsZ().buildLineSql("t_ord_deliverapply", "famount", "fcreatetime", deliverapplyDao.QueryFilterByUser(request, "fcustomerid", null), false);
			
			List<HashMap<String, Object>> recorders = deliverapplyDao.QueryBySql(sql);
			
			result.setData(recorders);
			result.setTotal(recorders.size() + "");
			
			MySimpleToolsZ.getMySimpleToolsZ().sortListByDateDesc(result.getData(), "fcreatetime");
			
			reponse.getWriter().write(JsonUtil.result(true,"",result));
			
		} catch (DJException e) {  
			reponse.getWriter().write(JsonUtil.result(false,e.getMessage(),"","")); 
		}
	
		return null;
	}
	
	@RequestMapping(value = "/CustdeliverapplytoExcel")
	public String CustdeliverapplytoExcel(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
//		String userid = ((Useronline) request.getSession().getAttribute(
//				"Useronline")).getFuserid();
		try {
			
//			String addCon = "";
//			String sqls = "select quote(fuserid) userid from t_bd_usertouser where fsuperuserid='"+userid+"'";
//			List<HashMap<String, Object>> demandList = deliverapplyDao.QueryBySql(sqls);
//			if(demandList.size()!=0){//关联用户过滤
//				StringBuilder userids=new StringBuilder("'"+userid+"'");
//				for(HashMap<String, Object> m : demandList){
//					userids.append(","+m.get("userid"));
//				}
//				addCon = "d.fcreatorid in ("+userids+")";
//			}else{
//				addCon = "d.fcreatorid = '"+userid+"'";
//			}
//			
//			sqls = "select 1 from t_bd_userrelationcustp where fuserid='"+userid+"'";
//			List list = deliverapplyDao.QueryBySql(sqls);//客户产品过滤
//			if(list.size()>0){
//				sqls = " cpdt.fid IN (SELECT fcustproductid FROM t_bd_userrelationcustp WHERE fuserid = '"+userid+"')";
//			}else{
//				sqls = "1=2";
//			}
//			String sql = "select d.fnumber 申请订单号, cpdt.fname 包装物名称, cpdt.fnumber 包装物编号, d.famount 配送数量, cpdt.FORDERUNIT 单位, date_format(d.farrivetime, '%Y-%m-%d %H:%i') 配送时间, d.fstate 订单状态, d.flinkman 联系人, d.flinkphone 联系电话, d.faddress 配送地址, d.fdescription 备注 from t_ord_deliverapply d left join t_sys_user u1 on u1.fid=d.fcreatorid left join t_sys_user u2 on u2.fid=d.fupdateuserid  left join t_bd_customer c on c.fid=d.fcustomerid left join t_bd_custproduct cpdt on cpdt.fid=d.fcusproductid left join t_bd_userrelationcustp  p on p.fcustproductid=d.fcusproductid left join t_ord_schemedesignentry pd on pd.fid=d.ftraitid where  ("+sqls+" or "+addCon+")" 
//					+ deliverapplyDao.QueryFilterByUser(request, "d.fcustomerid", null);
//			sql += " and d.farrivetime > date_sub(now(), interval 2 month)";
//			request.setAttribute("nolimit","");
//			ListResult result;
//			result = deliverapplyDao.QueryFilterList(sql, request);
//			ExcelUtil.toexcel(reponse,result);
			
					
			String sql = buildSql(request);
			request.setAttribute("djsort","d.fcreatetime desc");
			request.setAttribute("nolimit","true");
			
			ListResult result = MySimpleToolsZ.getMySimpleToolsZ().buildExcelListResult(sql, request, deliverapplyDao);
			
			//处理渲染
			Map<Integer, String> stateMap = buildRendererRelMap();
			List<HashMap<String, Object>> data = result.getData();
			
			for (HashMap<String, Object> hashMap : data) {
				
				hashMap.put("订单状态", stateMap.get(hashMap.get("订单状态")));
				
			}
			

			List<String> order = MySimpleToolsZ.gainDataIndexList(request);
			
			ExcelUtil.toexcel(reponse, result, order);
			
		} catch (DJException e) {
			reponse.getWriter().write(
					JsonUtil.result(false, e.getMessage(), "", ""));
		}

		return null;

	}

	private Map<Integer, String> buildRendererRelMap() {
		int[] stateKey = new int[]{0,1,2,3,4,5,6};
		
		String[] stateValueText = new String[]{"未接收","已接收","已接收","已接收","已入库","部分发货","全部发货"};
		
		Map<Integer, String> stateMap = new HashMap<>();
		
		for (int i = 0; i < stateKey.length; i++) {
			stateMap.put(stateKey[i], stateValueText[i]);
		}
		return stateMap;
	}
	
	@RequestMapping(value = "/getSupplierForDeliverApply")
	public String getSupplierForDeliverApply(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		try {
			String userId = baseSysDao.getCurrentUserId(request);
			String sql = "select distinct e1.fid,e1.fname,e1.fnumber from t_pdt_productreqallocationrules e"
					+" left join t_sys_supplier e1 on e.fsupplierid=e1.fid where 1=1 "+deliverapplyDao.QueryFilterByUser(request, "e.fcustomerid", null) +" order by CONVERT(e1.fname USING gbk) asc" ;
			List<HashMap<String, Object>> result = deliverapplyDao.QueryBySql(sql);
			response.getWriter().write(JsonUtil.result(true, "","", result));
		} catch (DJException e) {
			response.getWriter().write(JsonUtil.result(false,e.getMessage(),"",""));
		}
		return null;
	}
	
	
	
	//批量新增要货申请;
		@RequestMapping(value="/SaveBoardbatchDeliverapply")
		public  String SaveBoardbatchDeliverapply(HttpServletRequest request,
				HttpServletResponse reponse) throws IOException {
			
			try {
				String userid = ((Useronline) request.getSession().getAttribute(
						"Useronline")).getFuserid();
				List<Deliverapply> list= null;
				DateFormat f = new SimpleDateFormat("yyyy-MM-dd");
				String customerid="";
				String sql="select fcustomerid from ( select fcustomerid  from t_bd_usercustomer where fuserid='"+userid+"'"+
						" union select c.fcustomerid from  t_sys_userrole r "+
						" left join t_bd_rolecustomer c on r.froleid=c.froleid where r.fuserid='"+userid+"' )  s where fcustomerid is not null " ;
				List custlist=deliverapplyDao.QueryBySql(sql);
				if(custlist.size()==1)
				{
					HashMap map=(HashMap)custlist.get(0);
					customerid =map.get("fcustomerid").toString();
				}else{
					throw new DJException("当前帐号存在多个客户,不能执行操作！");
				}
				
				list = (ArrayList<Deliverapply>)request.getAttribute("Deliverapply");
				if(list.size()<=0){
					throw new DJException("请至少填写一条记录再保存！");
				}
				Deliverapply deliverapply;
				for(int i=0;i<list.size();i++){
					deliverapply = list.get(i);
					if(StringUtils.isEmpty(deliverapply.getFsupplierid()))
					{
						throw new DJException("制造商不能为空");
					}
					if(StringUtils.isEmpty(deliverapply.getFmaterialfid()))
					{
						throw new DJException("物料不能为空");
					}
					if(StringUtils.isEmpty(deliverapply.getFstavetype()))
					{
						throw new DJException("压线方式不能为空");
					}
					if(StringUtils.isEmpty(deliverapply.getFhformula()+""))
					{
						throw new DJException("横向压线公式不能为空");
					}
					if(StringUtils.isEmpty(deliverapply.getFvformula()+""))
					{
						throw new DJException("纵向压线公式不能为空");
					}
					if(!DataUtil.positiveIntegerCheck(deliverapply.getFamountpiece()+""))
					{
						throw new DJException("配送数量(片)填写错误");
					}
					if(deliverapply.getFarrivetime()==null)
					{
						throw new DJException("配送时间不能为空");
					}
					if(StringUtils.isEmpty(deliverapply.getFseries()))
					{
						throw new DJException("成型方式不能为空");
					}
					if(!DataUtil.positiveIntegerCheck(deliverapply.getFamount()+""))
					{
						throw new DJException("配送数量(只)填写错误");
					}
					if(deliverapply.getFaddressid()==null || deliverapply.getFaddressid().equals("")){
						throw new DJException("送货地址不能为空！");
					}
					if((deliverapply.getFboxheight()==null||deliverapply.getFboxlength()==null||deliverapply.getFboxwidth()==null)&&(deliverapply.getFmateriallength()==null||deliverapply.getFmaterialwidth()==null))
					{
						throw new DJException("纸箱规格与下料规格两项必填写一项");
					}
					if(!DataUtil.positiveDoubleCheck(deliverapply.getFboxheight())||!DataUtil.positiveDoubleCheck(deliverapply.getFboxlength())||!DataUtil.positiveDoubleCheck(deliverapply.getFboxwidth())||!DataUtil.positiveDoubleCheck(deliverapply.getFmateriallength())||!DataUtil.positiveDoubleCheck(deliverapply.getFmaterialwidth()))
					{
						throw new DJException("纸箱规格或下料规格中有负数或等于0");
					}					
					Productdef pinfo=(Productdef)deliverapplyDao.Query(Productdef.class, deliverapply.getFmaterialfid());//物料
					List<ProducePlan> plans=deliverapplyDao.QueryByHql(" from ProducePlan p where p.fproductid='"+ deliverapply.getFmaterialfid()+"' and p.fsupplierid='"+deliverapply.getFsupplierid()+"'");
					ProducePlan planInfo=null;
					if(plans!=null&&plans.size()>0)planInfo=plans.get(0);
//					if(plans==null||plans.size()==0) throw new DJException(pinfo.getFname()+"材料没有对应的排产计划");
					Calendar cday=getFirstArrivetimeDate(planInfo,pinfo);//计算排产期
					if(deliverapply.getFarrivetime().before(cday.getTime())){
						throw new DJException("该材料:"+pinfo.getFname()+",首排产日为"+f.format(cday.getTime())+",请根据首排产选择配送时间");
					}
					if(StringUtils.isEmpty(deliverapply.getFid())){
						deliverapply.setFid(deliverapplyDao.CreateUUid());
						deliverapply.setFnumber(ServerContext.getNumberHelper().getNumber("t_ord_deliverapply", "ZB", 4, false));
						deliverapply.setFcreatorid(userid);
						deliverapply.setFcreatetime(new Date());
						deliverapply.setFiscreate(0);
						deliverapply.setFupdatetime(new Date());
						deliverapply.setFupdateuserid(userid);
						deliverapply.setFcustomerid(customerid);
						deliverapply.setFboxtype(1);
						deliverapply.setFstate(0);
						deliverapply.setFtype("0");

					}else
					{
						deliverapply.setFupdatetime(new Date());
						deliverapply.setFupdateuserid(userid);
					}
				}
				deliverapplyDao.ExecSave(list);
				reponse.getWriter().write(JsonUtil.result(true, "保存成功！", "", ""));
			} catch (Exception e) {
				reponse.getWriter().write(
						JsonUtil.result(false, "保存失败，" + e.getMessage(), "", ""));
			}

			return null;
		}
		
		/**
		 * 此方法已转移至其它类中，废弃
		 */		
		@RequestMapping("/saveBoardSingleDeliverapply")
		public  String saveBoardSignleDeliverapply(HttpServletRequest request,
				HttpServletResponse response) throws IOException {
			String userid = ((Useronline) request.getSession().getAttribute(
					"Useronline")).getFuserid();
			try {
				DateFormat f = new SimpleDateFormat("yyyy-MM-dd");
				String customerid = baseSysDao.getCurrentCustomerid(userid);
				Deliverapply deliverapply = (Deliverapply) request.getAttribute("Deliverapply");
				Productdef pinfo=(Productdef)deliverapplyDao.Query(Productdef.class, deliverapply.getFmaterialfid());
				if(pinfo.getFeffect()==0){
					throw new DJException("此纸板材料已禁用，无法下单！");
				}
				List<ProducePlan> plans=deliverapplyDao.QueryByHql("from ProducePlan p where p.fproductid='"+ deliverapply.getFmaterialfid()+"' and p.fsupplierid='"+deliverapply.getFsupplierid()+"'");
				ProducePlan planInfo=null;
				if(plans!=null&&plans.size()>0){
					planInfo=plans.get(0);
				}
				Calendar cday;
				String sql;
				
				
				try {
					cday = getFirstArrivetimeDate(planInfo,pinfo);
				} catch (Exception e) {
					throw new DJException("排产日期计算出错，请上报平台更改！");
				}
				String fristday=f.format(cday.getTime());
				if(new Integer(pinfo.getFnewtype())==2)
				{
					cday.add(Calendar.DAY_OF_MONTH, 1);//普默认+1
//					fristday=fristday+"(裱胶生产周期48小时)";
				}
				else if(pinfo.getFeffected()==0)//早班
				{
					cday.add(Calendar.DAY_OF_MONTH, -1);//普默认-1 早班取排产日当天
				}
				if(nodaylist==null||!nodaylist.contains(f.format(new Date())))//1-3下单交期不判断排产时间
				{
				if(deliverapply.getFarrivetime().before(cday.getTime())){
					throw new DJException("该材料:"+pinfo.getFname()+",首排产日为"+fristday+",请根据首排产选择配送时间");
				}
				}
				//放假
				while(nodaylist!=null&&nodaylist.contains(f.format(deliverapply.getFarrivetime())))
				{
					deliverapply.getFarrivetime().setDate(deliverapply.getFarrivetime().getDate()+1);
				}
				if(pinfo.getFeffected()==0) deliverapply.getFarrivetime().setHours(17);
				if(pinfo.getFlayer()==1) deliverapply.getFarrivetime().setHours(17);
				if(StringUtils.isEmpty(deliverapply.getFmaterialfid())){
					throw new DJException("材料不能为空！");
				}
				if(StringUtils.isEmpty(deliverapply.getFaddressid())){
					throw new DJException("地址不能为空！");
				}
				BigDecimal materialLength = deliverapply.getFmateriallength();
				BigDecimal materialWidth = deliverapply.getFmaterialwidth();
				if(materialLength==null){
					throw new DJException("下料规格长度不能为空！");
				}
				if(materialWidth==null){
					throw new DJException("下料规格宽度不能为空！");
				}
				float mLength = materialLength.floatValue();
				float mWidth = materialWidth.floatValue();
				if(mLength<=0.5 || mWidth<=0.5){
					throw new DJException("纸板下料规格的长宽必须大于0.5！");
				}
				if(StringUtils.isEmpty(deliverapply.getFarrivetime()+"")){
					throw new DJException("配送时间不能为空！");
				}
				String boxModel = deliverapply.getFboxmodel()+"";
				if(StringUtils.isEmpty(boxModel)){
					throw new DJException("箱型不能为空！");
				}
				if("1".equals(boxModel)){
					if(StringUtils.isEmpty(deliverapply.getFhformula()+"") || StringUtils.isEmpty(deliverapply.getFvformula()+"") ||StringUtils.isEmpty(deliverapply.getFhformula1()+"")){
						throw new DJException("公式的接舌和系数不能为空！");
					}
				}
				if("2".equals(boxModel) ||"3".equals(boxModel) ||"4".equals(boxModel) ||"5".equals(boxModel) ||"6".equals(boxModel) ||"8".equals(boxModel)){
					if(StringUtils.isEmpty(deliverapply.getFhformula()+"")){
						throw new DJException("横向公式的接舌不能为空！");
					}
				}
				if("2".equals(boxModel) ||"3".equals(boxModel) ||"4".equals(boxModel) ||"5".equals(boxModel) ||"8".equals(boxModel)){
					if(StringUtils.isEmpty(deliverapply.getFvformula()+"")){
						throw new DJException("纵向公式的系数不能为空！");
					}
				}
				if(StringUtils.isEmpty(deliverapply.getFstavetype())){
					throw new DJException("压线方式不能为空！");
				}
				if(StringUtils.isEmpty(deliverapply.getFseries())){
					throw new DJException("成型方式不能为空！");
				}
				if(StringUtils.isEmpty(deliverapply.getFamount()+"")){
					throw new DJException("配送数量不能为空！");
				}
				if(deliverapply.getFamountpiece()==null || deliverapply.getFamountpiece()==0){
					throw new DJException("配送数量片数不能为空！");
				}
				if(StringUtils.isEmpty(deliverapply.getFamountpiece()+"")){
					throw new DJException("配送数量片数不能为空！");
				}
				//2015-06-29 片数控制
				if(deliverapply.getFseries().equals("连做")){
					deliverapply.setFamountpiece(deliverapply.getFamount());
				}
				if(deliverapply.getFseries().equals("不连做")){
					deliverapply.setFamountpiece(deliverapply.getFamount()*2);
				}
				//2015-06-29 片数控制
				BigDecimal boxLength = deliverapply.getFboxlength();
				BigDecimal boxWidth = deliverapply.getFboxwidth();
				BigDecimal boxHeight = deliverapply.getFboxheight();
				if(!"不压线".equals(deliverapply.getFstavetype())){
					if(StringUtils.isEmpty(boxHeight+"")){
						throw new DJException("纸箱高度不能为空！");
					}
					if(StringUtils.isEmpty(boxWidth+"")){
						throw new DJException("纸箱宽度不能为空！");
					}
					if(StringUtils.isEmpty(boxLength+"")){
						throw new DJException("纸箱长度不能为空！");
					}
					if(deliverapply.getFboxmodel()!=0){
						if(StringUtils.isEmpty(deliverapply.getFvstaveexp())){
							throw new DJException("纵向公式不能为空！");
						}
						if(StringUtils.isEmpty(deliverapply.getFhstaveexp())){
							throw new DJException("横向公式不能为空！");
						}
					}
				}
				if(boxLength!=null&&boxWidth!=null&&boxHeight!=null){
					float bLength = boxLength.floatValue();
					float bWidth = boxWidth.floatValue();
					float bHeight = boxHeight.floatValue();
					if(bLength!=0&&bWidth!=0&&bHeight!=0){
						if(bLength<=0.5||bWidth<=0.5||bHeight<=0.5){
							throw new DJException("纸板的长宽高必须大于0.5");
						}
						if(bWidth-bLength>0.5){
							throw new DJException("纸箱规格的宽度不能大于长度，请更改！");
						}
					}
				}
				if(boxLength==null){
					deliverapply.setFboxlength(BigDecimal.ZERO);
				}
				if(boxWidth==null){
					deliverapply.setFboxwidth(BigDecimal.ZERO);
				}
				if(boxHeight==null){
					deliverapply.setFboxheight(BigDecimal.ZERO);
				}
				String fid = deliverapply.getFid();
				if(!StringUtils.isEmpty(fid)){
					sql = "select fstate from t_ord_deliverapply where fid = '"+fid+"'";
					List<HashMap<String, Object>> list = deliverapplyDao.QueryBySql(sql);
					if(list.size()==0){
						throw new DJException("订单ID错误！");
					}
					String state = list.get(0).get("fstate")+"";
					if(!"0".equals(state) && !"7".equals(state)){
						throw new DJException("已接收订单无法更改！");
					}
				}
				if(StringUtils.isEmpty(fid)){
					deliverapply.setFid(deliverapplyDao.CreateUUid());
					deliverapply.setFcreatorid(userid);
					deliverapply.setFcreatetime(new Date());
					deliverapply.setFnumber(ServerContext.getNumberHelper().getNumber("t_ord_deliverapply", "ZB", 4, false));
					deliverapply.setFiscreate(0);
//					int state = deliverapplyDao.QueryExistsBySql("select 1 from t_sys_syscfg where fkey='boardOrderCreate' and fuser='"+userid+"'")? 7 : 0;
//					deliverapply.setFstate(state);
				}
				deliverapply.setFupdatetime(new Date());
				deliverapply.setFupdateuserid(userid);
				deliverapply.setFcustomerid(customerid);
				deliverapply.setFboxtype(1);
				deliverapply.setFtype("0");
				if(StringUtils.isEmpty(deliverapply.getFaddressid())){
					throw new DJException("地址不能为空！");
				}
				sql = "select fname,flinkman,fphone from t_bd_address where fid = '"+deliverapply.getFaddressid()+"'";
				List<HashMap<String, Object>> addressList = deliverapplyDao.QueryBySql(sql);
				if(addressList.size()==0){
					throw new DJException("地址ID不存在！");
				}
				deliverapply.setFaddress(addressList.get(0).get("fname")+"");
				deliverapply.setFlinkman(addressList.get(0).get("flinkman")+"");
				deliverapply.setFlinkphone(addressList.get(0).get("fphone")+"");
				//2015-05-15 横向公式异常多出()处理
				if(deliverapply.getFboxmodel().intValue()==1){
					if(deliverapply.getFhstaveexp().contains("(")){
						deliverapply.setFhstaveexp(deliverapply.getFhstaveexp().replace("(", "").replace(")", ""));
					}
				}				
				//2015-05-18有盖无底的箱型,为了保证能匹配到eas产品,后台直接将纵向公式+[0]
				if(deliverapply.getFboxmodel().intValue()==5){
					deliverapply.setFvstaveexp(deliverapply.getFvstaveexp()+"+[0]");
				}
				
				/**
				 * 来源添加
				 */
//				if (request.getParameter("fordesource") != null && !request.getParameter("isFromAndroid").equals(Deliverapply.DESKTOPS)) {
//					deliverapply.setFordesource(request.getParameter("fordesource"));
//				}
				
				//2015-07-24 手机下单横纵公式 临时做转化处理
				deliverapply.setFvstaveexp(deliverapply.getFvstaveexp().replace("[w-0]", "[w]"));
				deliverapply.setFhstaveexp(deliverapply.getFhstaveexp().replace("[w-0]", "[w]"));
				deliverapply.setFhstaveexp(deliverapply.getFhstaveexp().replace("[w/2]+[h]+[w/2]", "[(w)/2]+[h]+[(w)/2]"));
				deliverapply.setFvstaveexp(deliverapply.getFvstaveexp().replace("[w/2]+[h]+[w/2]", "[(w)/2]+[h]+[(w)/2]"));
				deliverapplyDao.saveBoardDeliverapply(deliverapply);
				response.getWriter().write(JsonUtil.result(true, "保存成功！","", ""));
			} catch (DJException e) {
				response.getWriter().write(
						JsonUtil.result(false, "保存失败，" + e.getMessage(), "", ""));
			}
			return null;
		}
		
		@RequestMapping("/updateBoardStateToCreate")
		public  String updateBoardStateToCreate(HttpServletRequest request,
				HttpServletResponse response) throws Exception {
			String fidcls = request.getParameter("fidcls");
			try {
				int count = deliverapplyDao.ExecUpdateBoardStateToCreate(fidcls);
				response.getWriter().write(JsonUtil.result(true, "操作成功，共提交"+count+"条记录！", "",""));
			} catch (DJException e) {
				response.getWriter().write(JsonUtil.result(false, e.getMessage(), "", ""));
			}
			return null;
		}
		
		/**
		 * 查询纸板订单信息
		 * @param request
		 * @param response
		 * @return
		 * @throws IOException
		 */
		@RequestMapping(value="/selectBoardSignleDeliverapply")
		public  String selectBoardSignleDeliverapply(HttpServletRequest request,
				HttpServletResponse response) throws IOException {
			try {
				String fid = request.getParameter("fid");
				String sql = "SELECT e.flabel,e.fiscommonorder,e.fvstaveexp,e.fhstaveexp,e2.flayer,e.fhformula1,e.fhformula,e.fvformula,e.fdefine1,e.fdefine2,e.fdefine3,e.fmaterialfid fmaterialfid_fid,e2.fname fmaterialfid_fname,e2.flayer fmaterialfid_flayer,e2.ftilemodelid fmaterialfid_ftilemodelid, e.fiscreate,e.fstate,e.fid,e.fcreatorid,e.fcreatetime,e.fnumber,e.fsupplierid fsupplierid,e.fboxmodel,e.fmateriallength,e.fmaterialwidth,e.fseries,e.fhline,date_format(e.farrivetime,'%Y-%m-%d') farrivetime,e.fstavetype,e.fboxlength,e.fboxwidth,e.fboxheight,e.famount,e.famountpiece,e.fvline,e.faddressid faddressid_fid,e.faddress faddressid_fname,e.fdescription FROM t_ord_deliverapply e" +
						" left join t_sys_supplier e1 on e.fsupplierid=e1.fid" +
						" left join t_pdt_productdef e2 on e.fmaterialfid=e2.fid";
				sql += " where e.fid = '"+fid+"'";
				List<HashMap<String,Object>> result= deliverapplyDao.QueryBySql(sql);
				response.getWriter().write(JsonUtil.result(true,"","",result));
			} catch (DJException e) {
				response.getWriter().write(JsonUtil.result(false, e.getMessage(), "", ""));
			}
			return null;
		}
		/**
		 * 查询客户一个月内下单的纸板产品
		 * @param request
		 * @param response
		 * @return
		 * @throws IOException
		 */
		@RequestMapping(value="/selectOrderedBoardDeliverapply")
		public  String selectOrderedBoardDeliverapply(HttpServletRequest request,
				HttpServletResponse response) throws IOException {
			String userid = ((Useronline) request.getSession().getAttribute(
					"Useronline")).getFuserid();
			try {
				String sql="select fcustomerid from ( select fcustomerid  from t_bd_usercustomer where fuserid='"+userid+"'"+
						" union select c.fcustomerid from  t_sys_userrole r "+
						" left join t_bd_rolecustomer c on r.froleid=c.froleid where r.fuserid='"+userid+"' )  s where fcustomerid is not null " ;
				List<HashMap<String, Object>> custList=deliverapplyDao.QueryBySql(sql);
				String customerid = null;
				if(custList.size()==1)
				{
					HashMap<String,Object> map=custList.get(0);
					customerid =map.get("fcustomerid").toString();
				}else{
					throw new DJException("当前帐号存在多个客户,不能执行操作！");
				}
				sql = "select distinct fmaterialfid from t_ord_deliverapply where fboxtype=1 and fcustomerid='"+customerid+"' and fcreatetime > DATE_SUB(NOW(),INTERVAL 1 MONTH) ORDER BY fcreatetime DESC";
				List<HashMap<String, String>> list = deliverapplyDao.QueryBySql(sql);
				StringBuilder sb = new StringBuilder("");
				String msg = "";
				if(list.size()>0){
					for(HashMap<String, String> map: list){
						sb.append(map.get("fmaterialfid")+",");
					}
					msg = sb.substring(0, sb.length()-1);
				}
				response.getWriter().write(JsonUtil.result(true, msg, "",""));
			} catch (DJException e) {
				response.getWriter().write(JsonUtil.result(false, e.getMessage(), "", ""));
			}
			return null;
		}
		
		/**
		 * 查询客户公式的前一次下单的值
		 * @param request
		 * @param response
		 * @return
		 * @throws IOException
		 */
		@RequestMapping(value="/selectBoardDeliverapplyFormulaDefaultValue")
		public  String selectBoardDeliverapplyFormulaDefaultValue(HttpServletRequest request,
				HttpServletResponse response) throws IOException {
			try {
				String customerid = baseSysDao.getCurrentCustomerid(request);
				String sql = "select fboxmodel,flayer,fhformula,fhformula1,fvformula,fdefine1,fdefine2,fdefine3 from t_ord_custboardformula where fcustomerid = '"+customerid+"'";
				List<HashMap<String, Object>> data = deliverapplyDao.QueryBySql(sql);
				response.getWriter().write(JsonUtil.result(true, "","",data));
			} catch (DJException e) {
				response.getWriter().write(JsonUtil.result(false, e.getMessage(), "", ""));
			}
			return null;
		}
		
		@RequestMapping(value="/getCustomerLabelList")
		public  String getCustomerLabelList(HttpServletRequest request,
				HttpServletResponse response) throws IOException {
			try {
				String customerid = baseSysDao.getCurrentCustomerid(request);
				String label = request.getParameter("labelName");
				if(!StringUtils.isEmpty(label)){
					label=new String(label.getBytes("ISO-8859-1"),"UTF-8");
				}
				String queryCon = StringUtils.isEmpty(label)?"": (" and fname like '%"+label.trim()+"%'");
//				String sql = "select fid,fname from t_ord_custboardlabel where fcustomerid = '"+customerid+"'"+queryCon+" order by fcreatetime desc limit 0,50";
				String sql = "select fid,fname from t_ord_custboardlabel where fcustomerid = '"+customerid+"'"+queryCon+" ORDER BY CONVERT( fname USING gbk ),fcreatetime desc  limit 0,50";
				List<HashMap<String, Object>> data = deliverapplyDao.QueryBySql(sql);
				response.getWriter().write(JsonUtil.result(true, "","",data));
			} catch (DJException e) {
				response.getWriter().write(JsonUtil.result(false, e.getMessage(), "", ""));
			}
			return null;
		}
		
		@RequestMapping("/delCustomerLabelInfo")
		public  String delCustomerLabelInfo(HttpServletRequest request,
				HttpServletResponse response) throws IOException {
			try {
				String labelName = request.getParameter("labelName");
				String msg = "";
				if(StringUtils.isEmpty(labelName)){
					return null;
				}
				String customerid = baseSysDao.getCurrentCustomerid(request);
				if(deliverapplyDao.ExecDelCustomerLabelById(customerid,labelName)>0){
					msg = "删除成功！";
				}
				response.getWriter().write(JsonUtil.result(true, msg,"",""));
			} catch (DJException e) {
				response.getWriter().write(JsonUtil.result(false, e.getMessage(), "", ""));
			}
			return null;
		}
		
		
		@RequestMapping("/selectBoardDeliverapplyCusts")
		public String selectBoardDeliverapplyCusts(HttpServletRequest request,
				HttpServletResponse reponse) throws IOException {
			
			ListResult result;
			try {
				String query = request.getParameter("query");
				String sql = "select pp.fstockinqty,d.fiscommonorder,d.fdescription,s.fname fsuppliername,  u1.fname as fcreator,c.fname  as fcustname,d.fid,d.fcreatorid,date_format(d.fcreatetime,'%Y-%m-%d %H:%i') fcreatetime ,d.fnumber,d.fcustomerid,date_format(d.farrivetime,'%Y-%m-%d') farrivetime,d.flinkman,d.flinkphone,d.famount,d.faddress,d.fstate,d.foutQty,concat(f.fname,' ','/',f.flayer,f.ftilemodelid) fmaterialname,d.fboxmodel,d.fboxlength,d.fboxwidth,d.fboxheight,d.fmateriallength,d.fmaterialwidth,d.fstavetype,d.fvline,d.fhline,d.famountpiece,d.fseries,if(ifnull(d.fouttime,'')='','',date_format(d.fouttime,'%Y-%m-%d')) fouttime" +BoardbuildSql(request);
				if(!StringUtils.isEmpty(query)){
					query = JsonUtil.decodeUnicode(query);
					if(query.indexOf("___=")==-1){
						sql += " and (s.fname like '%"+query+"%' or f.fname like '%"+query+"%' or d.fdescription like '%"+query+"%'";
						if(NumberUtils.isDigits(query)){
							sql += " or d.famount="+query;
						}
						sql += ")";
					}else{
						query = query.replaceAll("___=", "*");
						sql += " and (concat(d.fboxlength,'*',d.fboxwidth,'*',d.fboxheight) like '%"+query+"%' or concat(d.fmateriallength,'*',d.fmaterialwidth) like '%"+query+"%')";
					}
				}
				request.setAttribute("djsort","d.fcreatetime desc");
				result = deliverapplyDao.QueryFilterList(sql, request);
//				deliverapplyDao.addStateInfo(result.getData());
				reponse.getWriter().write(JsonUtil.result(true,"",result));
			} catch (DJException e) {
				reponse.getWriter().write(JsonUtil.result(false,e.getMessage(),"","")); 
			}
		
			return null;
		}
		
		@RequestMapping("/selectBoardDeliverapplyCustsMV")
		public String selectBoardDeliverapplyCustsMV(HttpServletRequest request,
				HttpServletResponse response) throws IOException {
			ListResult result;
			try {
				String query = request.getParameter("query");
				String sql = "select fwerkname,fiscommonorder,fcreatetime createtime,_stockinqty fstockinqty, _materialname fmaterialname,_suppliername fsuppliername,_custname fcustname,_creator fcreator,flabel,fiscommonorder,fdescription,fid,date_format(fcreatetime,'%Y-%m-%d %H:%i') fcreatetime ,fnumber,fcustomerid,date_format(farrivetime,'%Y-%m-%d') farrivetime,flinkman,flinkphone,famount,faddress,fstate,foutQty,fboxmodel,fboxlength,fboxwidth,fboxheight,fmateriallength,fmaterialwidth,fstavetype,fvline,fhline,famountpiece,fseries,if(fouttime is null,'',date_format(fouttime,'%Y-%m-%d')) fouttime,fordesource from t_ord_deliverapply_board_mv where fstate!=7 ";
				sql += deliverapplyDao.QueryFilterByUserofuser(request,"fcreatorid","and") + deliverapplyDao.QueryFilterByUser(request, "fcustomerid", null);
				if(!StringUtils.isEmpty(query)){
					query = JsonUtil.decodeUnicode(query);
					if(query.indexOf("___=")==-1){
						sql += " and (_materialname like '%"+query+"%' or _suppliername like '%"+query+"%' or fdescription like '%"+query+"%' or flabel like '%"+query+"%' or fnumber like '%"+query+"%'";
						if(NumberUtils.isDigits(query)){
							sql += " or famount="+query;
						}
						sql += ")";
					}else{
						query = query.replaceAll("___=", "*");
						sql += " and (concat(fboxlength,'*',fboxwidth,'*',fboxheight) like '%"+query+"%' or concat(fmateriallength,'*',fmaterialwidth) like '%"+query+"%')";
					}
				}
				request.setAttribute("djsort","createtime desc");
				result = deliverapplyDao.QueryFilterList(sql, request);
				response.getWriter().write(JsonUtil.result(true,"",result));
			} catch (DJException e) {
				response.getWriter().write(JsonUtil.result(false,e.getMessage(),"","")); 
			}
		
			return null;
		}
		
		@RequestMapping("/selectTempBoardDeliverapplyCustsMV")
		public String selectTempBoardDeliverapplyCustsMV(HttpServletRequest request,
				HttpServletResponse response) throws IOException {
			ListResult result;
			try {
				String sql = "select flabel,_stockinqty fstockinqty, _materialname fmaterialname,_suppliername fsuppliername,_custname fcustname,_creator fcreator,fiscommonorder,fdescription,fid,fcreatetime ,fnumber,fcustomerid,date_format(farrivetime,'%Y-%m-%d') farrivetime,flinkman,flinkphone,famount,faddress,fstate,foutQty,fboxmodel,fboxlength,fboxwidth,fboxheight,fmateriallength,fmaterialwidth,fstavetype,fvline,fhline,famountpiece,fseries,if(fouttime is null,'',date_format(fouttime,'%Y-%m-%d')) fouttime,fordesource from t_ord_deliverapply_board_mv where fstate=7 ";
				sql += deliverapplyDao.QueryFilterByUserofuser(request,"fcreatorid","and") + deliverapplyDao.QueryFilterByUser(request, "fcustomerid", null);
				request.setAttribute("djsort","fcreatetime");
				result = deliverapplyDao.QueryFilterList(sql, request);
				response.getWriter().write(JsonUtil.result(true,"",result));
			} catch (DJException e) {
				response.getWriter().write(JsonUtil.result(false,e.getMessage(),"","")); 
			}
		
			return null;
		}

		private String BoardbuildSql(HttpServletRequest request) throws UnsupportedEncodingException {
			String addCon = "";
			addCon=deliverapplyDao.QueryFilterByUserofuser(request,"d.fcreatorid","and");
			String sql = " from t_ord_deliverapply d left join t_ord_productplan pp on d.fplanid=pp.fid left join t_sys_user u1 on u1.fid=d.fcreatorid   left join t_bd_customer c on c.fid=d.fcustomerid left join t_pdt_productdef f on f.fid=d.fmaterialfid left join t_sys_supplier s on d.fsupplierid=s.fid where d.fboxtype=1 " +addCon 
					+ deliverapplyDao.QueryFilterByUser(request, "d.fcustomerid", null);
			
			return sql;
		}
		
		@LogAction("订单删除：纸板订单删除")
		@RequestMapping("/DelBoardDeliverapplyList")
		public String DelBoardDeliverapplyList(HttpServletRequest request,
				HttpServletResponse reponse) throws IOException {
			String result = "";
			String fidcls = request.getParameter("fidcls");
			fidcls="('"+fidcls.replace(",","','")+"')";
			try {
				if(deliverapplyDao.QueryExistsBySql("select fid from t_ord_deliverapply where fstate<>0 and fstate<>7 and fboxtype=1 and fid in "+fidcls))
				{
					throw new DJException("已生成订单，不允许删除");
				}
				//2015-09-29 by 记录纸板删除日志
				List<Deliverapply> dalists = deliverapplyDao.QueryByHql(" from Deliverapply where fid in "+fidcls);
				if(dalists.size()>0){
					String logData = "";
					for ( Deliverapply dainfo : dalists){
						Customer custInfo = (Customer)deliverapplyDao.Query(Customer.class, dainfo.getFcustomerid());
						String desc = ";产品规格："+(dainfo.getFstavetype().equals("不压线") ?  dainfo.getFmateriallength()+"X"+dainfo.getFmaterialwidth() : dainfo.getFboxlength()+"X"+dainfo.getFboxwidth()+"X"+dainfo.getFboxheight());
						logData += custInfo.getFname()+desc+";片数"+dainfo.getFamount();
					}
					request.setAttribute("logData", logData);
				}
				//2015-09-29 by 记录纸板删除日志
				
				deliverapplyDao.ExecBySql("delete from t_ord_deliverapply where fboxtype=1 and (fstate=0 or fstate=7) and fid in "+fidcls);
				if(deliverapplyDao.QueryExistsBySql("select 1 from t_ord_productplan where fdeliversboardid in "+fidcls)){//我的业务生成的纸板订单删除时需要，更改我的业务状态
					deliverapplyDao.ExecBySql("update  t_ord_productplan set fcreateboard=0 where fdeliversboardid in "+fidcls);
				}
				result = JsonUtil.result(true,"删除成功!", "", "");
			} catch (Exception e) {
				result = JsonUtil.result(false,e.getMessage(), "", "");
				log.error("DelDeliversList error", e);
			}
			reponse.getWriter().write(result);
			return null;
		}
		
		@RequestMapping(value = "/deliverapplytoExcel")
		public String deliverstoExcel(HttpServletRequest request,
				HttpServletResponse reponse) throws IOException {
			String userid = ((Useronline) request.getSession().getAttribute(
					"Useronline")).getFuserid();
			try {				
				String sql = "select s.fname 制造商,  u1.fname as 创建人,c.fname as 客户名称,date_format(d.fcreatetime,'%Y-%m-%d %H:%i') 创建时间 ,d.fnumber 申请单号,date_format(d.farrivetime,'%Y-%m-%d') 配送时间,d.flinkman 联系人,d.flinkphone 联系电话,d.famount '配送数量(只)',d.faddress 配送地址,d.fstate 状态,d.foutQty '实际配送数量(片)',concat(f.fname,' ',f.fnumber,' ','/',f.flayer,f.ftilemodelid)   材料,d.fboxmodel 箱型,d.fboxlength 长,d.fboxwidth 宽,d.fboxheight 高,d.fmateriallength 总长,d.fmaterialwidth 总高,d.fstavetype 跑线方式,d.fvformula 纵向公式,d.fhformula 横向公式,d.famountpiece '配送数量(片)',d.fseries 成型方式,if(ifnull(d.fouttime,'')='','',date_format(d.fouttime,'%Y-%m-%d')) 实际配送时间,pp.fstockinqty 入库数量/片,d.fdescription 特殊要求   " +BoardbuildSql(request);
				request.setAttribute("nolimit","");
				ListResult result;
				result = deliverapplyDao.QueryFilterList(sql, request);
				ExcelUtil.toexcel(reponse, result, "纸板订单");
			} catch (DJException e) {
				// TODO Auto-generated catch block
				reponse.getWriter().write(
						JsonUtil.result(false, e.getMessage(), "", ""));
			}

			return null;

		
		}
		
		@RequestMapping(value = "/deliverapplytoExcelMV")
		public String deliverapplytoExcelMV(HttpServletRequest request,
				HttpServletResponse response) throws IOException {
			try {
				String sql = "select fordernumber 采购订单号,fwerkname 制造部,_creator 创建人,_lastupdater 更改人,_custname 客户,_custpdtname 客户产品,fnumber 申请单号,date_format(farrivetime,'%Y-%m-%d %T') 配送时间,flinkman 联系人,flinkphone 联系电话,famount '配送数量(只)',faddress 配送地址,fdescription 特殊要求,CASE ftype WHEN 0 THEN '正常' WHEN 1 THEN '补单' WHEN 2 THEN '补货' ELSE '' END 类型,CASE fiscreate WHEN 0 THEN '否' WHEN 1 THEN '是' END '生成',CASE fstate WHEN 0 THEN '已创建'  WHEN 1 THEN '已接收'  WHEN 2 THEN '已排产'  WHEN 3 THEN '已排产'  WHEN 4 THEN '已入库'  WHEN 5 THEN '部分发货' WHEN 6 THEN '全部发货'  ELSE '' END 状态,fcharacter 特性名称,foutQty 实发数量  from t_ord_deliverapply_card_mv mv";
				request.setAttribute("nolimit","");
				request.setAttribute("nocount",true);
				ListResult result = deliverapplyDao.QueryFilterList(sql, request);
				ExcelUtil.toexcel(response, result, "纸板订单");
			} catch (DJException e) {
				response.getWriter().write(
						JsonUtil.result(false, e.getMessage(), "", ""));
			}
			return null;
		}
		
		
		@RequestMapping("/getUpdateBoardApplyList")
		public String getUpdateBoardApplyList(HttpServletRequest request,
				HttpServletResponse reponse) throws IOException {
			String fidcls = request.getParameter("fidcls");
			fidcls="('"+fidcls.replace(",","','")+"')";
			ListResult result;
			try {
				String sql = "select s.fname fsuppliername,concat(f.fname,' ',f.fnumber,' ','/',f.flayer,f.ftilemodelid)  fmaterialname,d.fid,d.fcreatorid,d.fcreatetime,d.fupdateuserid,d.fupdatetime,d.fnumber,d.fcustomerid,d.fcusproductid,d.farrivetime,d.flinkman,d.flinkphone,d.famount,d.faddress,d.fdescription,d.fordered,d.fordermanid,d.fordertime,d.fsaleorderid,d.fordernumber,d.forderentryid,d.fimportEas,d.fimportEasuserid,d.fimportEastime,d.fouted,d.foutorid,d.fouttime,d.faddressid,d.feasdeliverid,d.fistoPlan,d.fplanTime,d.fplanNumber,d.fplanid,d.falloted,d.fiscreate,d.fcusfid,d.fstate,d.ftype,d.ftraitid,d.fcharacter,d.foutQty,d.fsupplierid,d.fmaterialfid,d.fboxmodel,d.fboxlength,d.fboxwidth,d.fboxheight,d.fmateriallength,d.fmaterialwidth,d.fhformula,d.fvformula,d.fseries,d.fstavetype,d.famountpiece,d.fboxtype "
						+ "from t_ord_deliverapply d   left join t_pdt_productdef f on f.fid=d.fmaterialfid left join t_sys_supplier s on d.fsupplierid=s.fid where d.fboxtype=1 and d.fid in" +fidcls;
				request.setAttribute("djsort","d.fcreatetime desc");
				result = deliverapplyDao.QueryFilterList(sql, request);
				reponse.getWriter().write(JsonUtil.result(true,"",result));
			} catch (DJException e) {
				reponse.getWriter().write(JsonUtil.result(false,e.getMessage(),"","")); 
			}
		
			return null;
		}
		/**
		 * 生成纸板订单的配送时间
		 * @param deliverapply
		 * @param product
		 * @return
		 */
		public Date getBoardOrderArriveTime(Productdef product) {
			Calendar cday;
			List<ProducePlan> plans =deliverapplyDao.QueryByHql(" from ProducePlan p where p.fproductid='"+ product.getFmaterialfid()+"' and p.fsupplierid='"+product.getFmtsupplierid()+"'");
			try {
				Productdef pdef=(Productdef)deliverapplyDao.Query(Productdef.class,product.getFmaterialfid());
				cday = this.getFirstArrivetimeDate(plans.get(0),pdef);//计算排产期
				cday.set(Calendar.HOUR_OF_DAY, 0);
				cday.set(Calendar.MINUTE, 0);
				cday.set(Calendar.SECOND, 0);
				if(new Integer(pdef.getFnewtype())==2)//裱胶类型
				{
					cday.add(Calendar.DAY_OF_MONTH, 2);
				}else if(pdef.getFeffected()==1)//夜班
				{
					cday.add(Calendar.DAY_OF_MONTH, 1);
				}else if(pdef.getFeffected()==0){
					cday.set(Calendar.HOUR_OF_DAY, 17);
				}
			} catch (Exception e) {
				cday = Calendar.getInstance();
				cday.add(Calendar.DAY_OF_MONTH, 1);
			}
			return cday.getTime();
		}
		
		/**
		 * 计算该材料首次排产期日
		 * @param planInfo 材料计划信息
		 * @return
		 * @throws Exception
		 */
		public Calendar getFirstArrivetimeDate(ProducePlan planInfo,Productdef definfo)throws Exception 
		{
			Calendar c = Calendar.getInstance();
			c.setTime(new Date());
			int whilecount=0;
			List<Supplierboardplanconfig> configlist=deliverapplyDao.QueryByHql("  from Supplierboardplanconfig where fsupplierid='"+definfo.getFsupplierid()+"'");
			if(configlist.size()>0){
				if(new java.sql.Time(c.getTime().getTime()).toString().compareTo(configlist.get(0).getForderlasttime().toString())>=0)
				{
					c.add(Calendar.DAY_OF_MONTH, 1);
				}
			}
			if(definfo.getFeffected()==0&& new Integer(definfo.getFnewtype())!=2)//裱胶类型材料暂不分白夜班
			{
				c.add(Calendar.DAY_OF_MONTH, 1);
			}
			if(planInfo==null)
			{
				return c;
			}
			int currentday=c.get(Calendar.DAY_OF_MONTH);//当前日
			List<Integer> list=new ArrayList<Integer>();//存放开机或不开机时间
			if(planInfo.getFisweek()==0)//1 按周+不开机算；0为按开机时间算
			{
				int maxday=c.getActualMaximum(Calendar.DAY_OF_MONTH);//当月最大日期
				String[] produceDays = planInfo.getFday().split("_");
				for(String s: produceDays){
					list.add(Integer.valueOf(s));
				}
				 if(list.contains(currentday))
				 {
					return c;
				 }else
				 {
					 while(whilecount<4)
					 {
						 for(int i=0;i<list.size();i++)
						 {
							if(currentday<=list.get(i)&&list.get(i)<=maxday)
							{
								c.set(Calendar.DAY_OF_MONTH, list.get(i));//设置时间
								return c;
							}
						 }
						 c.add(Calendar.MONTH, 1);
						 currentday=1;
						 maxday=c.getActualMaximum(Calendar.DAY_OF_MONTH);
						 whilecount++;
					 }
				 }
			}else//按周
			{
				int currentweek=c.get(Calendar.DAY_OF_WEEK)-1;//0-6 当前星期几;c中存1-7 星期日-到星期六
				String fnoday = "_"+planInfo.getFnoday()+"_";
				String[] week= planInfo.getFweek().split("");
				List<Calendar> dayList = new ArrayList<>();
				Calendar calendar;
				for(String day: week){
					if("".equals(day)){
						continue;
					}
					calendar = Calendar.getInstance();
					calendar.setTime(c.getTime());
				//	calendar.setTime(new Date());
					calendar.add(Calendar.DAY_OF_MONTH, Integer.valueOf(day)-currentweek);
					dayList.add(calendar);
				}
				while(true){
					for(Calendar cr: dayList){
						if(cr.getTimeInMillis()>=c.getTimeInMillis()&& fnoday.indexOf("_"+cr.get(Calendar.DAY_OF_MONTH)+"_")==-1){
							return cr;
						}else{
							cr.add(Calendar.WEEK_OF_YEAR,1);
						}
					}
				}
			}
			throw new DJException("计算排产日出错！");
		
		}
		
		private boolean IsStopDate(Calendar c,DateFormat f)
		{
			if(nodaylist==null||nodaylist.size()==0) return false;
			return nodaylist.contains(f.format(c.getTime()));
			
		}

		@RequestMapping(value = "/getSupplierAndAddressByUser")
		public String getSupplierForBoardUser(HttpServletRequest request,
				HttpServletResponse response) throws IOException {
			try {
				String userId = baseSysDao.getCurrentUserId(request);
				List<HashMap<String, Object>> result=new ArrayList<HashMap<String, Object>>();
				String sql="";
				if(!deliverapplyDao.QueryExistsBySql("select fid from t_ord_deliverapply where fboxtype=1 and fcreatorid='"+userId+"' " +deliverapplyDao.QueryFilterByUser(request, "fcustomerid", null)))
				{
					List<HashMap<String, Object>> countresult=null;
					sql="select s.fid fsupplier_fid,s.fname fsupplier_fname from t_pdt_productreqallocationrules  r left join t_sys_supplier s on s.fid=r.fsupplierid where 1=1 "+deliverapplyDao.QueryFilterByUser(request, "fcustomerid", null);
					countresult = deliverapplyDao.QueryBySql(sql);
					 if(countresult.size()==1)
					 {
						 result.add(countresult.get(0));
					 }
					 sql="select fid faddress_fid,fname faddress_fname,flinkman faddress_flinkman,fphone faddress_fphone  from t_bd_address  where 1=1 "+deliverapplyDao.QueryFilterByUser(request, "fcustomerid", null);
					 countresult = deliverapplyDao.QueryBySql(sql); 
					 if(countresult.size()==1)
					 {
						if(result!=null&&result.size()>0)
						{
							HashMap map=result.get(0);
							map.put("faddress_fid", countresult.get(0).get("faddress_fid"));
							map.put("faddress_fname", countresult.get(0).get("faddress_fname"));
							map.put("faddress_flinkman", countresult.get(0).get("faddress_flinkman"));
							map.put("faddress_fphone", countresult.get(0).get("faddress_fphone"));
						}else
						{
							 result.add(countresult.get(0));
						}
					 }
					
				}else
				{
					 sql = "select  s.fname fsupplier_fname, o.fsupplierid fsupplier_fid,o.faddressid faddress_fid,o.flinkman faddress_flinkman,o.flinkphone faddress_fphone,o.faddress  faddress_fname from t_ord_deliverapply o left join t_sys_supplier s on s.fid=o.fsupplierid where o.fboxtype=1 and o.fcreatorid='"+userId+"' order by o.fupdatetime desc,o.fnumber desc " ;
					 result = deliverapplyDao.QueryBySql(sql);
				}
				
				response.getWriter().write(JsonUtil.result(true, "","", result));
			} catch (DJException e) {
				response.getWriter().write(JsonUtil.result(false,e.getMessage(),"",""));
			}
			return null;
		}
		
		@RequestMapping("/GetBoardSupplierByUserList")
		public String GetBoardSupplierByUserList(HttpServletRequest request,
				HttpServletResponse reponse) throws IOException {
			reponse.setCharacterEncoding("utf-8");
			try {
				String result = "";
				String sql = "select fid,fname FROM t_sys_supplier where 1=1 "+deliverapplyDao.QueryFilterByUser(request, null, "fid");
				ListResult lresult;

				lresult = deliverapplyDao.QueryFilterList(sql, request);
				List<HashMap<String, Object>> sList = lresult.getData();
				reponse.getWriter().write(JsonUtil.result(true, "", "", sList));
			} catch (DJException e) {
				// TODO Auto-generated catch block
				reponse.getWriter().write(
						JsonUtil.result(false, e.getMessage(), "", ""));
			}

			return null;

		}
		
		
		 /**
			 * 客户要货明细
			 * 
			 * @param request
			 * @param reponse
			 * @return
			 * @throws IOException
			 * 
			 * @date 2015-2-5 下午4:25:19 (ZJZ)
			 */
			@RequestMapping("/gainCusDeliverapplyRep")
			public String gainCusDeliverapplyRep(HttpServletRequest request,
					HttpServletResponse reponse) throws IOException {

				String Defaultfilter = request.getParameter("Defaultfilter");
				
				JSONArray jsa = JSONArray.fromObject(Defaultfilter);
				
				String countSql;
				
				if (jsa.size() != 0) {
					
					String startDate = jsa.getJSONObject(0).getString("value");
					String endDate = jsa.getJSONObject(1).getString("value");
					
					countSql = " select count(*) countT from ( "
							+ BASE_SQL_CUS_DELIVER_DETAIL
							+ String.format(" where dcreatetime between '%s' and '%s' ",
									startDate, endDate)
							+ " group by  fcustomerID,fboxtype,fordesource ) tableT ";

				} else {
					
					countSql = " select count(*) countT from ( "
							+ BASE_SQL_CUS_DELIVER_DETAIL
							+ " group by  fcustomerID,fboxtype,fordesource ) tableT ";
								
				}
			
				try {

					request.setAttribute("djgroup", "fcustomerID,fboxtype,fordesource");
					request.setAttribute("djsort", "fboxtype,fcustomerName desc");

					String sql = BASE_SQL_CUS_DELIVER_DETAIL;

					ListResult result = deliverapplyDao.QueryFilterList(sql, request);
				
					String count = ((BigInteger) ((HashMap<String, Object>) deliverapplyDao
							.QueryBySql(countSql)
							.get(0)).get("countT")).toString();
					
					
					result.setTotal(count);

					reponse.getWriter().write(JsonUtil.result(true, "", result));
				} catch (DJException e) {
					reponse.getWriter().write(
							JsonUtil.result(false, e.getMessage(), "", ""));
				}

				return null;
			}
			
			@RequestMapping("/GetFirstDateofMaterial")
			public String GetFirstDateofMaterial(HttpServletRequest request,
					HttpServletResponse reponse) throws Exception {
				reponse.setCharacterEncoding("utf-8");
				String fmaterialfid=request.getParameter("fmaterialfid");
				String fsupplierid =request.getParameter("fsupplierid");
				DateFormat f = new SimpleDateFormat("yyyy-MM-dd");
				try {
					HashMap map=new HashMap();
					if(nodaylist!=null&&nodaylist.contains(f.format(new Date())))//1-3下单交期为5号
					{
						List list=new ArrayList();
						map.put("farrivetime","2015-10-05");
						list.add(map);
						reponse.getWriter().write(JsonUtil.result(true, "", "1",list));
					}
					else{
					Productdef pd=(Productdef)deliverapplyDao.Query(Productdef.class,fmaterialfid);
					if(pd==null) throw new DJException("材料有误");
					List<ProducePlan> plans=deliverapplyDao.QueryByHql(" from ProducePlan p where p.fproductid='"+ fmaterialfid+"' and p.fsupplierid='"+fsupplierid+"'");
					ProducePlan pinfo=null;
					if(plans!=null&&plans.size()>0) pinfo=plans.get(0);
					Calendar cday=getFirstArrivetimeDate(pinfo,pd);//计算排产期 白班就取最近排产期
					if(new Integer(pd.getFnewtype())==2)//裱胶类型 默认+2
					{
						cday.add(Calendar.DAY_OF_MONTH, 2);
					}else if(pd.getFeffected()==1){//夜班 
						cday.add(Calendar.DAY_OF_MONTH, 1);//普默认+1
					}
					
					while(IsStopDate(cday,f))
					{
						cday.add(Calendar.DAY_OF_MONTH, 1);
					}
					List<HashMap<String,Object>> list=new ArrayList<HashMap<String,Object>>();
					map.put("farrivetime",f.format(cday.getTime()));
					list.add(map);
					reponse.getWriter().write(JsonUtil.result(true, "", "1",list));
					}
				} catch (DJException e) {
					// TODO Auto-generated catch block
					reponse.getWriter().write(
							JsonUtil.result(false, e.getMessage(), "", ""));
				}

				return null;

			}
			@RequestMapping("/setCommonorder")
			public void setCommonorder(HttpServletRequest request,
					HttpServletResponse reponse) throws IOException{
				try {
					String type = request.getParameter("type");
					String fids = request.getParameter("fids");
					fids = "'"+fids.replace(",", "','")+"'";
					
					String userid = ((Useronline)request.getSession().getAttribute("Useronline")).getFuserid();
					String addCon = "";
//					addCon=deliverapplyDao.QueryFilterByUserofuser(request,"fcreatorid","and");
					
					String sql;
					if("1".equals(type)){
						sql = "select 1 from t_ord_deliverapply where fiscommonorder = 1 "+addCon+deliverapplyDao.QueryFilterByUser(request, "fcustomerid", null);
						List list = deliverapplyDao.QueryBySql(sql);
						if(deliverapplyDao.QueryExistsBySql(sql+" and fid in("+fids+")")){
							throw new DJException("该订单已是常用订单！");
						}
						if(list.size()>=10){
							throw new DJException("常用订单最多为10个！");
						}else{
							if((fids.split(",").length+list.size())>10){
								throw new DJException("常用订单最多为10个！已有常用订单："+list.size()+"个！");
							}
						}
						sql = String.format("update t_ord_deliverapply set fiscommonorder = %s where fid in(%s)",type,fids);
						deliverapplyDao.ExecBySql(sql);
					}else{
						sql = "select 1 from t_ord_deliverapply where fiscommonorder = 0 and fid in("+fids+")"+addCon+deliverapplyDao.QueryFilterByUser(request, "fcustomerid", null);
						if(deliverapplyDao.QueryExistsBySql(sql)){
							throw new DJException("该订单已为不常用订单！");
						}
						sql = String.format("update t_ord_deliverapply set fcommonBoardOrder='',fiscommonorder = %s where fid in(%s)",type,fids);
						deliverapplyDao.ExecBySql(sql);
					}
				
					
					reponse.getWriter().write(JsonUtil.result(true, "", "",""));
				} catch (DJException e) {
					// TODO: handle exception
					reponse.getWriter().write(JsonUtil.result(false, e.getMessage(), "",""));
				}
			}
			@RequestMapping("/selectCommonorderDeliverapply")
			public String selectCommonorderDeliverapply(HttpServletRequest request,
					HttpServletResponse reponse) throws IOException {
				
				ListResult result;
				try {
					String userid = ((Useronline)request.getSession().getAttribute("Useronline")).getFuserid();
					String addCon = "";
					addCon=deliverapplyDao.QueryFilterByUserofuser(request,"e.fcreatorid","and");
					
					String sql = "SELECT e.flabel,e.fcommonBoardOrder,concat(e2.fname,' ',' ','/',e2.flayer,e2.ftilemodelid)  fmaterialname,e.fvstaveexp,e.fhstaveexp,e2.flayer,e.fhformula1,e.fhformula,e.fvformula,e.fdefine1,e.fdefine2,e.fdefine3,e.fmaterialfid fmaterialfid_fid,e2.fname fmaterialfid_fname,e2.flayer fmaterialfid_flayer,e2.ftilemodelid fmaterialfid_ftilemodelid, e.fiscreate,e.fstate,e.fid,e.fcreatorid,e.fcreatetime,e.fnumber,e1.fname fsupplierid_fname, e.fsupplierid fsupplierid,e.fboxmodel,e.fmateriallength,e.fmaterialwidth,e.fseries,e.fhline,date_format(e.farrivetime,'%Y-%m-%d') farrivetime,e.fstavetype,e.fboxlength,e.fboxwidth,e.fboxheight,e.famount,e.famountpiece,e.fvline,e.faddressid faddressid_fid,e.faddress faddressid_fname,e.fdescription FROM t_ord_deliverapply e" +
							" left join t_sys_supplier e1 on e.fsupplierid=e1.fid" +
							" left join t_pdt_productdef e2 on e.fmaterialfid=e2.fid";
					sql += " where 1=1 and e.fiscommonorder=1 "+addCon+ deliverapplyDao.QueryFilterByUser(request, "e.fcustomerid", null)+" limit 0,10";
//					String sql = "select d.fhline,d.fvline,d.faddressid,d.fsupplierid,d.fiscommonorder,d.fdescription,u1.fname as fcreator,c.fname  as fcustname,d.fid,d.fcreatorid,date_format(d.fcreatetime,'%Y-%m-%d %H:%i') fcreatetime ,d.fnumber,d.fcustomerid,date_format(d.farrivetime,'%Y-%m-%d') farrivetime,d.flinkman,d.flinkphone,d.famount,d.faddress,d.fstate,d.foutQty,concat(f.fname,' ',f.fnumber,' ','/',f.flayer,f.ftilemodelid) fmaterialname,f.fid fmaterialid,d.fboxmodel,d.fboxlength,d.fboxwidth,d.fboxheight,d.fmateriallength,d.fmaterialwidth,d.fstavetype,d.fvformula,d.fhformula,d.famountpiece,d.fseries,if(ifnull(d.fouttime,'')='','',date_format(d.fouttime,'%Y-%m-%d')) fouttime" +BoardbuildSql(request);
					
					
					
					List<HashMap<String,Object>> listresult = deliverapplyDao.QueryBySql(sql);
//					String sqls;
//					List<HashMap<String,Object>> list ;
//					for(int i = 0;i<listresult.size();i++){
//						sqls = "select e.fmaterialfid,count(1) num from t_ord_deliverapply e where 1=1 and e.fmaterialfid in('"+listresult.get(i).get("fmaterialfid_fid")+"')"+addCon+ deliverapplyDao.QueryFilterByUser(request, "e.fcustomerid", null);//+" group by e.fmaterialfid";;
//						list = deliverapplyDao.QueryBySql(sqls);
////						for(HashMap<String,Object> record : list){
////							if(listresult.get(i).get("fmaterialfid_fid").equals(record.get("fmaterialfid"))){
//								listresult.get(i).put("counts",list.get(0).get("num"));
////							}
////						}
//					}
//					deliverapplyDao.addStateInfo(result.getData());
					reponse.getWriter().write(JsonUtil.result(true,"","",listresult));
				} catch (DJException e) {
					reponse.getWriter().write(JsonUtil.result(false,e.getMessage(),"","")); 
				}
			
				return null;
			}
			@RequestMapping("setCommonBoardOrderName")
			public void setCommonBoardOrderName(HttpServletRequest request,
					HttpServletResponse reponse) throws IOException{
				try {
					String fid = request.getParameter("fid"); 
					String fname = request.getParameter("name");
					String sql = String.format("update t_ord_deliverapply set fcommonBoardOrder = '%s' where fid = '%s'",fname,fid);
					deliverapplyDao.ExecBySql(sql);
					reponse.getWriter().write(JsonUtil.result(true,"常用订单命名成功","",""));
				} catch (DJException e) {
					// TODO: handle exception
					reponse.getWriter().write(JsonUtil.result(false,"常用订单保存错误！","","")); 
				}
			}
			
			@RequestMapping(value = "/DeliverapplyCusRpttoExcel")
			public String DeliverapplyCusRpttoExcel(HttpServletRequest request,
					HttpServletResponse reponse) throws IOException {
				try {
					String sql = deliverapplyDao.ExcelSql(request);
					request.setAttribute("djsort","fcreatetime desc");
					request.setAttribute("nolimit","true");
					request.setAttribute("Defaultfilter","");
					
					ListResult result = deliverapplyDao.buildExcelListResult(sql, request, deliverapplyDao);
					
					//处理渲染
					String stateMap[] = new String[]{"纸箱订单","纸板订单"};
					HashMap map=new HashMap();
					map.put("desktops", "网页");
					map.put("android", "安卓");
					map.put("ios", "苹果");
					List<HashMap<String, Object>> data = result.getData();
					
					for (HashMap<String, Object> hashMap : data) {
						
						hashMap.put("订单类型", stateMap[(int) hashMap.get("订单类型")]);
						hashMap.put("来源订单类型", map.get(hashMap.get("来源订单类型")));
						
					}
					

					List<String> order = MySimpleToolsZ.gainDataIndexList(request);
					
					ExcelUtil.toexcel(reponse, result, order);
					
				} catch (DJException e) {
					reponse.getWriter().write(
							JsonUtil.result(false, e.getMessage(), "", ""));
				}

				return null;

			}
			
			@RequestMapping(value = "/getCustaccountbalanceInfo")
			public String getCustaccountbalanceInfo(HttpServletRequest request,
					HttpServletResponse reponse) throws IOException {
				try {
					String userid = ((Useronline)request.getSession().getAttribute("Useronline")).getFuserid();
					List list=deliverapplyDao.QueryBySql("select fcustomerid from t_bd_usercustomer where fuserid='"+userid+"'");
					
					if(list.size()==1){
						HashMap map=(HashMap)list.get(0);
						list=deliverapplyDao.QueryBySql("select fid from t_bd_custaccountbalance where fisread=0 and fcustomerid='"+map.get("fcustomerid")+"'");
						if(list.size()>0){
							reponse.getWriter().write(
									JsonUtil.result(true, ((Useronline)request.getSession().getAttribute("Useronline")).getFusername()+"，您的账户余额不足！",  "", ""));	
							deliverapplyDao.ExecBySql("update t_bd_custaccountbalance set fisread=1 where fid='"+((HashMap)list.get(0)).get("fid")+"'");
							return null;
						}
					}
					
					reponse.getWriter().write(
							JsonUtil.result(false, "", "", ""));
					
				} catch (DJException e) {
					reponse.getWriter().write(
							JsonUtil.result(false, e.getMessage(), "", ""));
				}

				return null;

			}
			@RequestMapping(value="/getCustomerByUser")
			public  String getCustomerByUser(HttpServletRequest request,
					HttpServletResponse response) throws IOException {
				try {
					String userid = ((Useronline) request.getSession().getAttribute(
							"Useronline")).getFuserid();
					String sql="select fcustomerid,fname from ( select u.fcustomerid,cu.fname  from t_bd_usercustomer u left join t_bd_customer cu on u.fcustomerid=cu.fid where fuserid='"+userid+"'"+
							" union select c.fcustomerid,cu.fname from  t_sys_userrole r "+
							" left join t_bd_rolecustomer c on r.froleid=c.froleid left join t_bd_customer cu on c.fcustomerid=cu.fid where r.fuserid='"+userid+"' )  s where fcustomerid is not null " ;
					List<HashMap<String, Object>> custList=deliverapplyDao.QueryBySql(sql);
					response.getWriter().write(JsonUtil.result(true, "","",custList));
				} catch (DJException e) {
					response.getWriter().write(JsonUtil.result(false, e.getMessage(), "", ""));
				}
				return null;
			}
			
			@RequestMapping(value="/checkBdDeliverapply")
			public  String checkBdDeliverapply(HttpServletRequest request,
					HttpServletResponse response) throws IOException {
				String userid = ((Useronline) request.getSession().getAttribute(
						"Useronline")).getFuserid();
				try {
					String customerid = baseSysDao.getCurrentCustomerid(userid);
					Deliverapply deliverapply = (Deliverapply) request.getAttribute("Deliverapply");
					deliverapply.setFcustomerid(customerid);
					String fid = deliverapply.getFid();
					//2015-08-19  新增的订单此处加上连个控制   1：同规格，此材料不同于历史材料提示    2：同规格，数量，材料已经下过提示
					if(StringUtils.isEmpty(fid)){
						String checkSql = "";
						String mtid = deliverapply.getFmaterialfid();
						 //1：同规格，此材料不同于历史材料提示
						if(!"不压线".equals(deliverapply.getFstavetype())){
							checkSql = "select DISTINCT t._materialname,t.fmaterialfid from  t_ord_deliverapply_board_mv t where t.fboxlength ="+deliverapply.getFboxlength()+" ";
							checkSql += " and t.fboxwidth = "+deliverapply.getFboxwidth()+" and  t.fboxheight =  "+deliverapply.getFboxheight()+" ";
							checkSql += " and t.fcustomerid = '"+deliverapply.getFcustomerid()+"' ";
						}
						else{
							checkSql = "select DISTINCT t._materialname,t.fmaterialfid from  t_ord_deliverapply_board_mv t where ";
							checkSql += "  t.fmateriallength = "+deliverapply.getFmateriallength()+" and  t.fmaterialwidth =  "+deliverapply.getFmaterialwidth()+" ";
							checkSql += " and t.fcustomerid = '"+deliverapply.getFcustomerid()+"'  and t.fstavetype = '不压线' ";
						}
						List<HashMap<String, Object>> list = deliverapplyDao.QueryBySql(checkSql);
						if(list.size() == 1 && !list.get(0).get("fmaterialfid").equals(mtid)){
							throw new DJException("该规格只做过"+list.get(0).get("_materialname")+"，是否继续！");
						}
						//同规格做个多个材料，判断此单材料
						if(list.size() > 1){
							Boolean flag = true;
							for(int i=0;i<list.size();i++){
								if(list.get(i).get("fmaterialfid").equals(mtid)){
									flag = false;
								}
							}
							if(flag){
								throw new DJException("该规格没做过此种材料，是否继续！");
							}
						}
//						else if(list.size()>1){
//							throw new DJException("该规格做过不同的材料，是否继续！");
//						}
						 //2：同规格，数量，材料已经下过提示
						if(!"不压线".equals(deliverapply.getFstavetype())){
							checkSql = "select t.fid from  t_ord_deliverapply_board_mv t where t.fboxlength ="+deliverapply.getFboxlength()+" ";
							checkSql += " and t.fboxwidth = "+deliverapply.getFboxwidth()+" and  t.fboxheight =  "+deliverapply.getFboxheight()+" ";
							checkSql += " and t.fcustomerid = '"+deliverapply.getFcustomerid()+"' and t.fmaterialfid= '"+deliverapply.getFmaterialfid()+"' ";
							checkSql += " and t.famount = "+deliverapply.getFamount()+" and t.fcreatetime >=CURRENT_DATE() ";
						}
						else{
							checkSql = "select t.fid from  t_ord_deliverapply_board_mv t where  ";
							checkSql += "  t.fmateriallength = "+deliverapply.getFmateriallength()+" and  t.fmaterialwidth =  "+deliverapply.getFmaterialwidth()+" ";
							checkSql += " and t.fcustomerid = '"+deliverapply.getFcustomerid()+"' and t.fmaterialfid= '"+deliverapply.getFmaterialfid()+"' ";
							checkSql += " and t.famount = "+deliverapply.getFamount()+" and t.fstavetype = '不压线'  and t.fcreatetime >=CURRENT_DATE() ";
						}	
						List<HashMap<String, Object>> slist = deliverapplyDao.QueryBySql(checkSql);
						if(slist.size()>0){
							throw new DJException("此规格今天已有同样的订单，是否继续！");
						}
					}
					//2015-08-19  新增的订单此处加上连个控制   1：同规格，此材料不同于历史材料提示    2：同规格，数量，材料已经下过
				}
				catch (DJException e) {
					response.getWriter().write(JsonUtil.result(true, e.getMessage(), "", "[{\"flag\":1,\"msg\": \""+e.getMessage()+"\"}]"));
					return null;
				}
				response.getWriter().write(JsonUtil.result(true, "", "", "[{\"flag\":0}]"));
				return null;
			}
}

 	
