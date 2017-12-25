package Com.Controller.order;

import java.io.IOException;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.hibernate.util.StringHelper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import Com.Base.Util.DJException;
import Com.Base.Util.ExcelUtil;
import Com.Base.Util.JsonUtil;
import Com.Base.Util.ListResult;
import Com.Base.Util.ServerContext;
import Com.Dao.System.IVmipdtParamDao;
import Com.Dao.order.IDeliversDao;
import Com.Dao.order.IMystockDao;
import Com.Dao.order.ISaleOrderDao;
import Com.Entity.System.Useronline;
import Com.Entity.order.Deliverapply;
import Com.Entity.order.Mystock;
import Com.Entity.order.Saleorder;
import Com.Entity.order.SchemeDesignEntry;

@Controller
public class MystockController {
	@Resource
	private IMystockDao MystockDao;
	@Resource
	private IDeliversDao DeliversDao;
	@Resource
	private ISaleOrderDao saleOrderDao;
	@Resource
	private IVmipdtParamDao VmipdtParamDao;
	private boolean running = false;
	@RequestMapping("getMystockList")
	public void getMystockList(HttpServletRequest request,HttpServletResponse response) throws IOException{
		try {
			String userid = ((Useronline)request.getSession().getAttribute("Useronline")).getFuserid();
			String addCon ="";
//			String sqls = "select quote(fuserid) userid from t_bd_usertouser where fsuperuserid='"+userid+"'";
//			List<HashMap<String, Object>> demandList = MystockDao.QueryBySql(sqls);
//			if(demandList.size()!=0){//关联用户过滤
//				StringBuilder userids=new StringBuilder("'"+userid+"'");
//				for(HashMap<String, Object> m : demandList){
//					userids.append(","+m.get("userid"));
//				}
//				addCon = " in ("+userids+")";
//			}else{
//				addCon = " = '"+userid+"'";
//			}
			//根据客户产品找产品比例系数
			String sql1 = "SELECT IFNULL(d.feffect, 0) feffect,e.famount, r.fcustproductid,r.fcustomerid FROM t_pdt_custrelation r LEFT JOIN t_pdt_custrelationentry e ON e.fparentid = r.fid LEFT JOIN t_pdt_productdef d ON d.fid = e.fproductid WHERE IFNULL(d.fishistory, 0) = 0 AND feffect  = 1 GROUP BY r.fcustproductid,r.fcustomerid  ";
			String	sql ="select fid,fnumber,fcustproductid,fplanamount,funit,ffinishtime,fconsumetime,fisconsumed,fdescription,fcustproductname,fcreatetime,fcreateid,fcustomerid,cfnumber,fordered,fsaleorderid,fbalanceqty,fcharactername,fcharacterid,cname,fremark,sname,fstate,fsupplierid,mcount,fpcmordernumber from (SELECT m.fpcmordernumber,(fplanamount-IFNULL(finqty,0)*ifnull(l.famount,1)) mcount,cu.fname cname,m.fstate fstate,su.fname sname,su.fid fsupplierid,IFNULL(m.fcharactername,'') fcharactername,m.fcharacterid,IFNULL(s.fbalanceqty,0)*ifnull(l.famount,1) fbalanceqty,m.fsaleorderid,m.fordered,m.fordertime,m.fordermanid,"
						+"m.forderentryid,IFNULL(m.fordernumber,'') fordernumber,m.fid,c.fnumber cfnumber,"
						+"IFNULL(m.fnumber,'') fnumber,IFNULL(c.fname,'') fcustproductname,m.fcustproductid,"
						+"IFNULL(m.fplanamount,'') fplanamount,IFNULL(m.funit,'') funit,IFNULL(m.ffinishtime,'') ffinishtime,"
						+"IFNULL(m.fconsumetime,'') fconsumetime,IFNULL(m.fisconsumed,'') fisconsumed,"
						+"IFNULL(m.fdescription,'') fdescription,m.fcreatetime,m.fcreateid,m.fcustomerid,m.fremark "  
						+"FROM mystock m left join t_bd_customer cu on cu.fid=m.fcustomerid " 
//						+"LEFT JOIN `t_ord_productplan` p  ON m.fsaleorderid = p.forderid LEFT JOIN t_inv_storebalance  s ON p.fid = s.fproductplanID " 
						+"LEFT JOIN t_inv_storebalance s ON m.fsaleorderid = s.fsaleorderid "
						+"LEFT JOIN t_bd_custproduct c ON c.fid = m.fcustproductid left join t_sys_supplier su on su.fid=m.fsupplierid left join ("+sql1+") l on l.fcustproductid = m.fcustproductid and l.fcustomerid = m.fcustomerid"
//						+" where TO_DAYS(NOW()) - TO_DAYS(m.fcreatetime) <= 100 and m.fcreateid"+addCon+MystockDao.QueryFilterByUser(request, "m.fcustomerid", null)+" GROUP BY m.fcustproductid,m.fid UNION "
						+" where TO_DAYS(NOW()) - TO_DAYS(m.fcreatetime) <= 100 "+MystockDao.QueryFilterByUserofuser(request,"m.fcreateid","and")+MystockDao.QueryFilterByUser(request, "m.fcustomerid", null)+" GROUP BY m.fcustproductid,m.fid UNION "
						+"SELECT '' fpcmordernumber,'' mcount,cu.fname cname,'3' fstate,'' sname,'' fsupplierid,st.fcharacter AS fcharactername,st.fid AS fcharacterid,IFNULL(s.fbalanceqty,0) fbalanceqty,'' AS  fsaleorderid,'' fordered,'' fordertime,'' fordermanid,"
						+"'' forderentryid,'' fordernumber,'' fid,c.fnumber cfnumber,'' fnumber,IFNULL(c.fname,'') fcustproductname,c.fid fcustproductid,st.fentryamount fplanamount,'' funit"
						+",'' ffinishtime,'' fconsumetime,st.fallot fisconsumed,'' fdescription,sd.fcreatetime,sd.fcreatorid,sd.fcustomerid,'' fremark FROM `t_ord_schemedesignentry` st "
						+"LEFT JOIN `t_ord_schemedesign` sd ON sd.fid=st.fparentid left join t_bd_customer cu on cu.fid=sd.fcustomerid  LEFT JOIN (select FID,fcreatorid,fcreatetime,fupdateuserid,fupdatetime,fcustomerid,fProductID,finqty,foutqty,fbalanceqty,FWarehouseID,FWarehouseSiteID,fdescription,fsaleorderid,forderentryid,fproductplanID,FSUPPLIERID,FTYPE,fallotqty,ftraitid from t_inv_storebalance where ftraitid is not null) s ON st.fid = s.ftraitid "
						+"inner JOIN t_bd_custproduct c ON c.fcharacterid = st.fid where "
//						+"TO_DAYS(NOW()) - TO_DAYS(sd.fcreatetime) <= 500 and sd.fcreatorid"+addCon+MystockDao.QueryFilterByUser(request, "sd.fcustomerid", null)+"GROUP BY sd.fid,st.fid) a where 1=1";
						+"TO_DAYS(NOW()) - TO_DAYS(sd.fcreatetime) <= 500 "+MystockDao.QueryFilterByUserofuser(request,"sd.fcreatorid","and")+MystockDao.QueryFilterByUser(request, "sd.fcustomerid", null)+"GROUP BY sd.fid,st.fid) a where 1=1";
			ListResult list = MystockDao.QueryFilterList(sql, request);
			response.getWriter().write(JsonUtil.result(true, "",list));
		} catch (DJException e) {
			// TODO: handle exception
			response.getWriter().write(JsonUtil.result(false,e.getMessage(),"",""));
		}
		
	}
	@RequestMapping("getMyStockOrderList")
	public void getMyStockOrderList(HttpServletRequest request,HttpServletResponse response) throws IOException{
		try {
			String sql = "select ifnull(m.fcharactername,'') fcharactername,m.fcharacterid,ifnull(s.fbalanceqty,0) fbalanceqty,m.fsaleorderid,m.fordered,m.fordertime,m.fordermanid," +
					"m.forderentryid,ifnull(m.fordernumber,'') fordernumber,m.fid,c.fnumber cfnumber," +
					"ifnull(m.fnumber,'') fnumber,ifnull(c.fname,'') fcustproductname,m.fcustproductid," +
					"ifnull(m.fplanamount,'') fplanamount,ifnull(m.funit,'') funit,ifnull(m.ffinishtime,'') ffinishtime," +
					"ifnull(m.fconsumetime,'') fconsumetime,ifnull(m.fisconsumed,'') fisconsumed," +
					"ifnull(m.fdescription,'') fdescription,m.fcreatetime,m.fcreateid,m.fcustomerid,cu.fname cfname,c.fspec fspec,m.fremark " +
					"from mystock m " +
//					"LEFT JOIN `t_ord_productplan` p  ON m.fsaleorderid = p.forderid " 
//					" LEFT JOIN t_inv_storebalance s ON p.fid = s.fproductplanID " +
					" LEFT JOIN t_inv_storebalance s    ON m.fsaleorderid = s.fsaleorderid  " +
					"left join t_bd_custproduct c on c.fid = m.fcustproductid " +
					"left join t_bd_customer cu on cu.fid = m.fcustomerid " +
					"where 1=1";
			ListResult list = MystockDao.QueryFilterList(sql, request);
			response.getWriter().write(JsonUtil.result(true, "",list));
		} catch (DJException e) {
			// TODO: handle exception
			response.getWriter().write(JsonUtil.result(false,e.getMessage(),"",""));
		}
	}
	@RequestMapping("saveOrUpdateMystock")
	public void saveOrUpdateMystock(HttpServletRequest request,HttpServletResponse response) throws ParseException, IOException{
		String userid = ((Useronline) request.getSession().getAttribute(
				"Useronline")).getFuserid();
		Mystock mystock = (Mystock)request.getAttribute("Mystock");
		try {
			
			String sql="select fcustomerid from ( select fcustomerid  from t_bd_usercustomer where fuserid='"+userid+"'"+
					" union select c.fcustomerid from  t_sys_userrole r "+
					" left join t_bd_rolecustomer c on r.froleid=c.froleid where r.fuserid='"+userid+"' )  s where fcustomerid is not null " ;
			List<HashMap<String,Object>> list=MystockDao.QueryBySql(sql);
			if(list.size()>1){
				throw new DJException("当前用户是管理员,不能操作！");
			}else if(list.size()==1){
				mystock.setFcustomerid(list.get(0).get("fcustomerid").toString());
			}else if(list.size()<1){
				throw new DJException("当前用户没有关联客户,请联系平台业务部！");
			}
			sql = "SELECT 1 FROM `t_ord_schemedesignentry` WHERE fid = (SELECT fcharacterid FROM t_bd_custproduct where fid ='"+mystock.getFcustproductid()+"') and fallot=0";
			list=MystockDao.QueryBySql(sql);
			if(list.size()>0){
				throw new DJException("该特性产品未消耗完毕!");
			}
			if(mystock.getFid()==null||"".equals(mystock.getFid())){
				mystock.setFid(MystockDao.CreateUUid());
				mystock.setFnumber(ServerContext.getNumberHelper().getNumber("mystock", "B", 4, false));
				mystock.setFcreatetime(new Date());
				mystock.setFcreateid(userid);
				mystock.setFstate(0);
			}else{
				sql = "select 1 from mystock where fid = '"+mystock.getFid()+"' and fordered=1";
				list = MystockDao.QueryBySql(sql);
				if(list.size()==1){
					throw new DJException("已经下单的,不能操作！");
				}
			}
			MystockDao.saveOrUpdate(mystock);
			response.getWriter().write(JsonUtil.result(true, "保存成功","",""));
		} catch (DJException e) {
//			 TODO: handle exception
			response.getWriter().write(JsonUtil.result(false, e.getMessage(),"",""));
		}
	}
	@RequestMapping("delMystock")
	public void delMystock(HttpServletRequest request,HttpServletResponse response) throws IOException{
		String fids = request.getParameter("fids");
		try {
			String  sql = "select 1 from mystock where fid in "+fids+" and fordered=1";
			List<HashMap<String,Object>> list = MystockDao.QueryBySql(sql);
			if(list.size()>0){
				throw new DJException("错误 ！已经下单的不能删除！");
			}
			sql = "delete from mystock where fid in "+fids;
			MystockDao.ExecBySql(sql);
			response.getWriter().write(JsonUtil.result(true, "删除成功", "",""));
		} catch (DJException e) {
			// TODO: handle exception
			response.getWriter().write(JsonUtil.result(false, e.getMessage(), "",""));
		}
	}
	@RequestMapping("exportMystockList")
	public void exportMystockList(HttpServletRequest request,HttpServletResponse response) throws IOException{
		try {
			String userid = ((Useronline)request.getSession().getAttribute("Useronline")).getFuserid();
			request.setAttribute("nolimit", "");
//			String sql = "select m.fnumber 申请单号,c.fname 包装物品名称,m.fplanamount 计划数量,m.funit 单位,m.ffinishtime 要求完成时间,m.fconsumetime 预计消耗时间,m.fisconsumed 是否消耗完毕,m.fdescription 备注  from mystock m left join t_bd_custproduct c on c.fid = m.fcustproductid where 1=1 "+MystockDao.QueryFilterByUserofuser(request,"m.fcreateid","and")+MystockDao.QueryFilterByUser(request, "m.fcustomerid", null);
			String sql1 = "SELECT IFNULL(d.feffect, 0) feffect,e.famount, r.fcustproductid,r.fcustomerid FROM t_pdt_custrelation r LEFT JOIN t_pdt_custrelationentry e ON e.fparentid = r.fid LEFT JOIN t_pdt_productdef d ON d.fid = e.fproductid WHERE IFNULL(d.fishistory, 0) = 0 AND feffect  = 1 GROUP BY r.fcustproductid,r.fcustomerid  ";
			String	sql ="select fnumber 备货单号,cname 客户名称,fpcmordernumber,fcustproductname 包装物品名称,cfnumber 包装物品编号,sname 制造商名称,fplanamount 计划数量,fcharactername 特性,funit 单位,fcreatetime 创建时间,ffinishtime 要求完成时间,fconsumetime 预计消耗时间,fdescription 备注  from (SELECT m.fpcmordernumber,cu.fname cname,m.fstate fstate,su.fname sname,su.fid fsupplierid,IFNULL(m.fcharactername,'') fcharactername,m.fcharacterid,m.fsaleorderid,m.fordered,m.fordertime,m.fordermanid,"
						+"m.forderentryid,IFNULL(m.fordernumber,'') fordernumber,m.fid,c.fnumber cfnumber,"
						+"IFNULL(m.fnumber,'') fnumber,IFNULL(c.fname,'') fcustproductname,m.fcustproductid,"
						+"IFNULL(m.fplanamount,'') fplanamount,IFNULL(m.funit,'') funit,IFNULL(m.ffinishtime,'') ffinishtime,"
						+"IFNULL(m.fconsumetime,'') fconsumetime,IFNULL(m.fisconsumed,'') fisconsumed,"
						+"IFNULL(m.fdescription,'') fdescription,m.fcreatetime,m.fcreateid,m.fcustomerid,m.fremark "  
						+"FROM mystock m left join t_bd_customer cu on cu.fid=m.fcustomerid  "
						+"LEFT JOIN t_bd_custproduct c ON c.fid = m.fcustproductid left join t_sys_supplier su on su.fid=m.fsupplierid"
//						+" where TO_DAYS(NOW()) - TO_DAYS(m.fcreatetime) <= 100 and m.fcreateid"+addCon+MystockDao.QueryFilterByUser(request, "m.fcustomerid", null)+" GROUP BY m.fcustproductid,m.fid UNION "
						+" where TO_DAYS(NOW()) - TO_DAYS(m.fcreatetime) <= 100 "+MystockDao.QueryFilterByUserofuser(request,"m.fcreateid","and")+MystockDao.QueryFilterByUser(request, "m.fcustomerid", null)+" GROUP BY m.fcustproductid,m.fid) a where 1=1";
			ListResult list = MystockDao.QueryFilterList(sql, request);
			ExcelUtil.toexcel(response, list);
		} catch (DJException e) {
			// TODO: handle exception
			response.getWriter().write(JsonUtil.result(false,e.getMessage(), "",""));
		}
	}
	/**
	 * 本月备货
	 * 上月备货
	 */
	@RequestMapping("getPlanamount")
	public void getPlanamount(HttpServletRequest request,HttpServletResponse response) throws IOException{
		try {
			String sql = "SELECT IFNULL(SUM(fplanamount),0) fplanamount,1 AS ftype FROM mystock WHERE DATE_FORMAT(fcreatetime, '%Y-%m-%d') >= DATE_FORMAT(DATE_SUB(CURDATE(), INTERVAL 30 day),'%Y-%m-%d') "+MystockDao.QueryFilterByUser(request, "fcustomerid", null)
						+" UNION"
						+" SELECT IFNULL(SUM(fplanamount),0) fplanamount,2 AS ftype FROM mystock WHERE DATE_FORMAT(fcreatetime, '%Y-%m-%d') < DATE_FORMAT(DATE_SUB(CURDATE(), INTERVAL 30 day),'%Y-%m-%d') and DATE_FORMAT(fcreatetime, '%Y-%m-%d') >= DATE_FORMAT(DATE_SUB(CURDATE(), INTERVAL 60 day),'%Y-%m-%d') "+MystockDao.QueryFilterByUser(request, "fcustomerid", null)+" ORDER BY ftype";
			List<HashMap<String,Object>> list = MystockDao.QueryBySql(sql);
			response.getWriter().write(JsonUtil.result(true, "", "",list));
		} catch (DJException e) {
			// TODO: handle exception
			response.getWriter().write(JsonUtil.result(false,e.getMessage(), "",""));
		}
	}
	/**
	 * 柱形图
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("getPlanamountBycustproduct")
	public void getPlanamountBycustproduct(HttpServletRequest request,HttpServletResponse response) throws IOException{
		String fcustproductid = request.getParameter("fcustproductid");
		try {
			String sql = "select fplanamount,SUBSTRING(fcreatetime,1,10)  fcreatetime,fcreatetime as fcreatetimes from mystock where fcustproductid = '"+fcustproductid+"' ORDER BY fcreatetimes DESC LIMIT 0, 7";
			
			List list = MystockDao.QueryBySql(sql);
			
			response.getWriter().write(JsonUtil.result(true, "", "",list));
		} catch (DJException e) {
			// TODO: handle exception
			response.getWriter().write(JsonUtil.result(false,e.getMessage(), "",""));
		}
	}
	/**
	 * 批量新增的保存
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("saveMyStockInBulk")
	public void saveMyStockInBulk(HttpServletRequest request,HttpServletResponse response) throws IOException{
		String userid = ((Useronline)request.getSession().getAttribute("Useronline")).getFuserid();
		List<Mystock> myStocks = (List<Mystock>) request.getAttribute("Mystock");
		try {
			MystockDao.saveMyStockInBulk(myStocks,userid);
			response.getWriter().write(JsonUtil.result(true, "操作成功！", "",""));
		} catch (DJException e) {
			response.getWriter().write(JsonUtil.result(false, e.getMessage(), "",""));
		}
	}
	/**
	 * 根据客户产品获取配送数量和配送时间
	 * @throws IOException 
	 */
	@RequestMapping("getDeliverOrderList")
	public void getDeliverOrderList(HttpServletRequest request,HttpServletResponse response) throws IOException{
//		 String fcusproductid = request.getParameter("fcusproductid");
//		 if(fcusproductid!=null&&!"".equals(fcusproductid)){
//			 fcusproductid = "'"+fcusproductid+"'";
//		 }
//		String Defaultfilter = request.getParameter("Defaultfilter");
//		request.setAttribute("djsort","farrivetime desc");
		String fsaleorderid = request.getParameter("fsaleorderid");
		String fcharacterid = request.getParameter("fcharacterid");
		String sql = null;
		 try {
			 if(("".equals(fsaleorderid)||fsaleorderid==null)&&("".equals(fcharacterid))||fcharacterid==null){
				 //sql = "SELECT fid,famount,farrivetime FROM t_ord_deliverorder where 1 = 2"+MystockDao.QueryFilterByUser(request, "fcustomerid", null);
				 sql = "SELECT d.* FROM mystock m INNER JOIN `t_ord_productplan` p  ON m.`fsaleorderid` = p.forderid  INNER JOIN `t_ord_deliverorder` d ON p.fid = d.`fplanid` where 1=2";
				}else{
					if(fcharacterid!=null&&!"".equals(fcharacterid)&&!"null".equals(fcharacterid)){
						sql = "SELECT d.* FROM t_ord_schemedesignentry m INNER JOIN `t_ord_deliverorder` d ON m.fid=d.ftraitid where m.fid='"+fcharacterid+"'"+MystockDao.QueryFilterByUser(request, "d.fcustomerid", null);
						
					}else{
						sql = "SELECT d.* FROM mystock m INNER JOIN `t_ord_productplan` p  ON m.`fsaleorderid` = p.forderid  INNER JOIN `t_ord_deliverorder` d ON p.fid = d.`fplanid` where 1=1 and m.fsaleorderid='"+fsaleorderid+"'"+MystockDao.QueryFilterByUser(request, "d.fcustomerid", null);
					}
					
				}
			ListResult list = MystockDao.QueryFilterList(sql, request);
			response.getWriter().write(JsonUtil.result(true, "",list));
		} catch (DJException e) {
			// TODO: handle exception
			response.getWriter().write(JsonUtil.result(false, e.getMessage(), "",""));
		}
	}
	/**
	 * 我的备货下单
	 * @throws IOException 
	 */
	@RequestMapping("myStockOrder")
	public String myStockOrder(HttpServletRequest request,HttpServletResponse response) throws IOException{
		synchronized (this.getClass()){
		if(running){
			return null;
		}
		running = true;
		String result = "";
		Mystock pinfo = null;
		try {
			String userid = ((Useronline) request.getSession().getAttribute("Useronline")).getFuserid();
			String fid = "";
			String fcustomeridd=request.getParameter("fcustomerid");
			if(fcustomeridd==null||"".equals(fcustomeridd))
			{
			  throw new DJException("错误 ！请选择一条记录进行操作！");
			}
			String sql = "select * from mystock where ifnull(fordered,0) = 0  and fcustomerid='"+fcustomeridd+"' order by fcustproductid";
			List<HashMap<String,Object>> myStock = MystockDao.QueryBySql(sql);
			HashMap map = new HashMap();
			for(int i=0;i<myStock.size();i++){
				HashMap myStockinfo = myStock.get(i);
				fid = myStockinfo.get("fid").toString();
				if (fid != null && !"".equals(fid)) {
					//获取一条我的备货记录pinfo
					pinfo = MystockDao.Query(fid);
				}
				try {
					String fcusproductid = pinfo.getFcustproductid();//客户产品ID
					String fcustomerid = pinfo.getFcustomerid();//客户ID
					int famount = pinfo.getFplanamount();//计划数量
					Date farrivetime = pinfo.getFfinishtime();//要求完成时间
					/*
					 * 根据客户产品查出产品信息
					 */
					sql=" select r.fid,e2.famount,r.fproductid,case when (f.fnewtype='4'or f.fnewtype='2')  then 2 else 1 end as fnewtype,count(e2.fid) counts from t_pdt_productrelationentry e "+
							" left join t_pdt_productrelation r on e.fparentid=r.fid "+
							" left join  t_pdt_productdef f on f.fid=r.fproductid  " +
							" left join t_pdt_productrelationentry e2 on e2.fparentid=r.fid "+
							" where ifnull(f.fishistory,0)=0 and ifnull(f.feffect,0)=1 and   e.fcustproductid='"+fcusproductid+"' and r.fcustomerid='"+fcustomerid+"'";
					sql+="group by r.fid order by fnewtype,counts";
					List<HashMap<String,Object>> ptypelist= MystockDao.QueryBySql(sql);//查询与该客户产品相关的产品类型，产品等	
					if(ptypelist.size()>0){
						if("1".equals(ptypelist.get(0).get("counts").toString())){
							myStockOrder(ptypelist,map,pinfo,userid);
						}else{
							MystockDao.ExecBySql("update mystock set fordernumber='"+"当前客户产品关联多个产品,不能操作！"+"' where fid = '"+fid+"'");
							continue;
						}
						
					}else
					{
						 sql=" select e.famount,e.fproductid,ifnull(d.fishistory,0) fishistory,ifnull(d.feffect,0) dfeffect from  t_pdt_custrelation  r "+
								" left join t_pdt_custrelationentry e on e.fparentid=r.fid left join  t_pdt_productdef  d on d.fid=e.fproductid "+
								" where r.fcustproductid='"+fcusproductid+"' and r.fcustomerid='"+fcustomerid+"'   order by d.fishistory desc";
						 ptypelist= MystockDao.QueryBySql(sql);
						 if(ptypelist.size()>0){
							myStockOrder(ptypelist,map,pinfo,userid);
						 }else{
							 if(map.size()==0){
									MystockDao.ExecBySql("update mystock set fordernumber='"+"该产品未关联！"+"' where fid = '"+fid+"'");
								}
						 }
					}
					
				} catch (Exception e) {
					throw new DJException(e.getMessage());
				}
			}
			if(map.size()>0)
			{
				createMergerOrder(map,userid);
				map.clear();
			}
			result = JsonUtil.result(true,"下单成功!", "", "");
		} catch (Exception e) {
			result = JsonUtil.result(false,"下单失败! '" + e.getMessage()+"'", "", "");
		}
		response.setCharacterEncoding("utf-8");
		response.getWriter().write(result);
		running = false;
		
		}
		return null;
	}
	private void myStockOrder(List<HashMap<String,Object>> ptypelist,HashMap map,Mystock pinfo,String userid) throws Exception{
		String fid = pinfo.getFid();
		String fcusproductid = pinfo.getFcustproductid();//客户产品ID
		String fcustomerid = pinfo.getFcustomerid();//客户ID
		String fsupplierid = pinfo.getFsupplierid();//制造商ID
		int famount = pinfo.getFplanamount()/Integer.parseInt(ptypelist.get(0).get("famount").toString());//计划数量除以客户产品跟产品的系数
		Date farrivetime = pinfo.getFfinishtime();//要求完成时间
		String fproductid = ptypelist.get(0).get("fproductid").toString();
		if(fproductid==null && StringHelper.isEmpty(fproductid))
		{
			MystockDao.ExecBySql("update mystock set fordernumber='"+"没有对应产品！"+"' where fid = '"+fid+"'");
			
			return;
		}
//			fproductid =pinfo.getFproductid();
			//有父产品，且为通知的，不需要下单
			String sql="select t1.fid from t_pdt_productdefproducts t inner join t_pdt_vmiproductparam t1 on t.fparentid=t1.fproductid  where t.fproductid='"+fproductid+"' and t1.ftype=0 ";
			List<HashMap<String,Object>> parentvmilist= DeliversDao.QueryBySql(sql);
			if(parentvmilist.size()>0)
			{
				MystockDao.ExecBySql("update mystock set fordernumber='"+"存在通知类型的套装，不需要下单！"+"' where fid = '"+fid+"'");
//				continue;
				return;
			}
			int fnewtype = 0;
			ResultSet rs = null;
			sql = "select d.fid,d.fassemble,d.fnewtype,d.fcustomerid,d.feffect,d.fishistory from t_pdt_productdef d where fid = '"+fproductid+"' ";
			List<HashMap<String,Object>> pdtlist= DeliversDao.QueryBySql(sql);
			if(pdtlist.size()>0){
				//获取一产品信息pdtinfo
				HashMap pdtinfo = pdtlist.get(0);
				
				if(pdtinfo.get("fnewtype")!=null){
					fnewtype = new Integer(pdtinfo.get("fnewtype").toString());
				}
				
				sql = "select forderamount from t_pdt_vmiproductparam where fcustomerid = '"+fcustomerid+"' and fproductid = '"+fproductid+"' ";
				List<HashMap<String,Object>> vmipdtparamlist = VmipdtParamDao.QueryBySql(sql);
				if(vmipdtparamlist.size()>0)		//有安全库存则不需要下单;
				{                                                                                                                                                   
					//非安全库存转安全库存时提交上次非安全库存的订单
//					if(map.size()>0)
//					{
//						createMergerOrder(map.get("fproductid").toString(),new Integer(map.get("fnewtype").toString())  ,userid,map.get("fcustomerid").toString(),map.get("fcusproductid")==null?"":map.get("fcusproductid").toString(),new Integer(map.get("famount").toString()),(Date)map.get("farrivetime"),map.get("Deliverid").toString());
//						map.clear();
//					}
					MystockDao.ExecBySql("update mystock set fordernumber='"+"该产品已经备货，不需要下单！"+"' where fid = '"+fid+"'");
//					continue;
					return;
				}else	//安全库存 不存在则直接新建订单;
				{
					if((Integer)pdtinfo.get("feffect")==0)
					{
						MystockDao.ExecBySql("update mystock set fordernumber='"+"该产品已禁用！"+"' where fid = '"+fid+"'");
//						continue;
					return;
					}else if((Integer)pdtinfo.get("fishistory")==1)
					{
						MystockDao.ExecBySql("update mystock set fordernumber='"+"历史版本产品！"+"' where fid = '"+fid+"'");
//						continue;
						return;
					}else{
					//合并没有安全库存订单;
					int tempAmt = 0 ;
					String myStockid = "";
					
					if(map.size()==0){
						map.put("famount", famount);
						map.put("fproductid", fproductid);
						myStockid = "'"+fid+"'";
						map.put("myStockid", myStockid);
						map.put("fnewtype", fnewtype);
						map.put("fcustomerid", fcustomerid);
						map.put("fcusproductid", fcusproductid);
						map.put("farrivetime", farrivetime);
						map.put("fsupplierid",fsupplierid);
					}
//					else if(map.get("fproductid").equals(fproductid)){//对对应同一个产品的我的备货进行合并
//						tempAmt = new Integer(map.get("famount").toString());
//						map.put("famount", tempAmt+famount);
//						myStockid = map.get("myStockid").toString() + ",'"+fid+"'";
//						map.put("myStockid", myStockid);
//					}
					else{
//						createMergerOrder(map.get("fproductid").toString(),(Integer)map.get("fnewtype"),userid,map.get("fcustomerid").toString(),(map.get("fcusproductid")==null?"":map.get("fcusproductid").toString()),(Integer)map.get("famount"),(Date)map.get("farrivetime"),map.get("Deliverid").toString());
						createMergerOrder(map,userid);
						
						map.put("famount", famount);
						map.put("fproductid", fproductid);
						myStockid = "'"+fid+"'";
						map.put("myStockid", myStockid);
						map.put("fnewtype", fnewtype);
						map.put("fcustomerid", fcustomerid);
						map.put("fcusproductid", fcusproductid);
						map.put("farrivetime", farrivetime);
						map.put("fsupplierid",fsupplierid);
					}
//					continue;
					}
				}
			}else{
				MystockDao.ExecBySql("update mystock set fordernumber='"+"未关联产品！"+"' where fid = '"+fid+"'");
//				continue;
				return;
			}
	}
	private void createMergerOrder(HashMap map,String userid) throws Exception {
		// TODO Auto-generated method stub
		String fproductid = map.get("fproductid").toString();
		String fsupplierid = (String)map.get("fsupplierid");
		int fnewtype = (Integer)map.get("fnewtype");
		String fcustomerid = map.get("fcustomerid").toString();
		String fcusproductid = (map.get("fcusproductid")==null?"":map.get("fcusproductid").toString());
		int famount = (Integer)map.get("famount");
		Date farrivetime = (Date)map.get("farrivetime");
		String myStockid = map.get("myStockid").toString();
		String orderid = saleOrderDao.CreateUUid();
		String fordernumber = ServerContext.getNumberHelper().getNumber("t_ord_saleorder", "Z",4,false);
//		String fordernumber = saleOrderDao.getFnumber("t_ord_saleorder", "Z",4);
		ArrayList<Saleorder> solist = new ArrayList<Saleorder>();
		String sql = null;//	sql = "select forderamount from t_pdt_vmiproductparam where fcustomerid = '"+fcustomerid+"' and fproductid = '"+fproductid+"' ";
		if(fnewtype!=2 && fnewtype!=4){
			//找该子件有没有套装，且套装有安全库存且为通知类型
			sql = "select 1 from t_pdt_vmiproductparam where ftype=0 and fcustomerid = '"+fcustomerid+"' "+
			"and fproductid in (select fparentid from t_pdt_productdefproducts where fproductid = '"+fproductid+"') ";
			List<HashMap<String, Object>> li = DeliversDao.QueryBySql(sql);
			if(li.size()>0){
				MystockDao.ExecBySql("update mystock set fordernumber='当前产品的套件为通知类型，不能下单！' where fid in ("+myStockid+")");
				return;
			}
			Saleorder soinfo = new Saleorder();//生产订单
			String orderEntryid = saleOrderDao.CreateUUid();
			soinfo.setFid(orderEntryid);
			soinfo.setForderid(orderid);
			soinfo.setFcreatorid(userid);
			soinfo.setFcreatetime(new Date());
			soinfo.setFnumber(fordernumber);
			soinfo.setFproductdefid(fproductid);
			soinfo.setFentryProductType(0);
			
//			pinfo.setFordernumber(fordernumber+"-1");
//			pinfo.setForderentryid(orderEntryid);
			soinfo.setFtype(1);
			soinfo.setFlastupdatetime(new Date());
			soinfo.setFlastupdateuserid(userid);
			soinfo.setFamount(famount);
			soinfo.setFcustomerid(fcustomerid);
			soinfo.setFcustproduct(fcusproductid);
			soinfo.setFarrivetime(farrivetime);
			soinfo.setFbizdate(new Date());
			soinfo.setFaudited(1);
			soinfo.setFauditorid(userid);
			soinfo.setFaudittime(new Date());
			soinfo.setFamountrate(1);
			
			soinfo.setFassemble(0);
			soinfo.setFiscombinecrosssubs(0);
			
			soinfo.setFordertype(1);
			soinfo.setFseq(1);
			soinfo.setFimportEas(0);
			soinfo.setFallot(0);
			
			soinfo.setFsupplierid(fsupplierid);

//			HashMap<String, Object> params = saleOrderDao.ExecSave(soinfo);			
//			String ordersql = "SET SQL_SAFE_UPDATES=0 ";
//			DeliversDao.ExecBySql(ordersql);
			DateFormat f = new SimpleDateFormat("yyyy-MM-dd");
			//往原表反写数据
			String ordersql = "update mystock set fordered=1,fordermanid='"+userid+"',fordertime='"+f.format(new Date())+"',fsaleorderid='"+orderid+"'," +
					"fordernumber='"+(fordernumber+"-1")+"',forderentryid='"+orderEntryid+"',fstate=1 where fid in (" + myStockid +")";
//			DeliversDao.ExecBySql(ordersql);
			
			solist.add(soinfo);
			//生成订单，更改要货管理和要货申请状态
			HashMap<String, Object> params = MystockDao.ExecMyStockorder(solist, ordersql, myStockid);
			
			if (params.get("success") == Boolean.FALSE) {
				throw new DJException("生成订单失败！");
			}
			
		}else{
			//套装
			// 一次性获取所有级次的：套装+子产品
			// 再按顺序加载即可
			String productinfo = "select 1 from t_pdt_productdefproducts p where p.fparentid in (select fproductid from t_pdt_productdefproducts where fparentid = '"+fproductid+"')";
			if(DeliversDao.QueryBySql(productinfo).size()>0){
				MystockDao.ExecBySql("update mystock set fordernumber='"+"套装产品不能超过3级！"+"' where fid in ("+myStockid+")");
				return;
			}
			List list = getAllProductSuit(fproductid);
			HashMap subInfo = null;
			String orderentryid = "";
			for (int k = 0; k < list.size(); k++)
			{
				subInfo = (HashMap) list.get(k);
				
				Saleorder soinfo = new Saleorder();
				
				if(subInfo.get("FAssemble")!=null && subInfo.get("FAssemble").toString().equals("1")){
					soinfo.setFassemble(1);
				}else{
					soinfo.setFassemble(0);
				}
				
				if(subInfo.get("fiscombinecrosssubs")!=null && subInfo.get("fiscombinecrosssubs").toString().equals("1")){
					soinfo.setFiscombinecrosssubs(1);
				}else{
					soinfo.setFiscombinecrosssubs(0);
				}
				
				if(k==0){
					soinfo.setFamount(famount);
					soinfo.setFproductdefid(fproductid);
					soinfo.setFsuitProductId(fproductid);
//					pinfo.setFordernumber(fordernumber+"-1");
//					pinfo.setForderentryid(subInfo.get("orderEntryID").toString());
					orderentryid = subInfo.get("orderEntryID").toString();
					
				}else{
					sql = "select 1 from t_pdt_vmiproductparam where ftype=0 and fcustomerid = '"+fcustomerid+"' and fproductid  = '"+subInfo.get("fid")+"' ";
					List<HashMap<String, Object>> li = DeliversDao.QueryBySql(sql);
					if(li.size()>0){
						MystockDao.ExecBySql("update mystock set fordernumber='当前产品的子件为通知类型，不能下单！' where fid in ("+myStockid+")");
						return;
					}
					soinfo.setFparentOrderEntryId(subInfo.get("ParentOrderEntryId").toString());
					soinfo.setFamount(famount * new Integer(subInfo.get("amountRate").toString()));
					soinfo.setFproductdefid(subInfo.get("fid").toString());
					
				}
				soinfo.setFtype(1);
				soinfo.setFordertype(2);
				soinfo.setFentryProductType(new Integer(subInfo.get("entryProductType").toString()));
				soinfo.setFid(subInfo.get("orderEntryID").toString());
				soinfo.setFseq((k+1));
				soinfo.setFimportEas(0);
				soinfo.setFamountrate(new Integer(subInfo.get("amountRate").toString()));
				soinfo.setForderid(orderid);
				soinfo.setFcreatorid(userid);
				soinfo.setFcreatetime(new Date());
				soinfo.setFlastupdatetime(new Date());
				soinfo.setFlastupdateuserid(userid);
				soinfo.setFnumber(fordernumber);
				soinfo.setFcustomerid(fcustomerid);
				soinfo.setFcustproduct(fcusproductid);
				soinfo.setFarrivetime(farrivetime);
				soinfo.setFbizdate(new Date());
				soinfo.setFaudited(1);
				soinfo.setFauditorid(userid);
				soinfo.setFaudittime(new Date());
				soinfo.setFsupplierid(fsupplierid);
				soinfo.setFallot(0);
				
//				HashMap<String, Object> params = saleOrderDao.ExecSave(soinfo);
				solist.add(soinfo);
				
			}
			
//			String ordersql = "SET SQL_SAFE_UPDATES=0 ";
//			DeliversDao.ExecBySql(ordersql);
			DateFormat f = new SimpleDateFormat("yyyy-MM-dd");
			String ordersql = "update mystock set fordered=1,fordermanid='"+userid+"',fordertime='"+f.format(new Date())+"',fsaleorderid='"+orderid+"'," +
					"fordernumber='"+(fordernumber+"-1")+"',forderentryid='"+orderentryid+"',fstate=1 where fid in (" + myStockid +")";
//			DeliversDao.ExecBySql(ordersql);
			
			HashMap<String, Object> params = MystockDao.ExecMyStockorder(solist, ordersql, myStockid);
			if (params.get("success") == Boolean.FALSE) {
				throw new DJException("生成订单失败！");
			}
		}
//		pinfo.setFsaleorderid(orderid);
	}
	
	/**
	 * 获取多级套装+子件，并且对“分录的产品类型”赋值
	 */
	protected List getAllProductSuit(String fproductid) throws Exception
	{
//		ProductDefCollection productCols = new ProductDefCollection();
		List list = new ArrayList();
//		StringBuffer oql = new StringBuffer("select *,Products.*,Products.product.* where id ='").append(id).append("'");
//		ProductDefInfo productInfo = getProductDefInfo(oql.toString());
//		stmt = conn.prepareStatement("select fnewtype,ftype,FCombination,FAssemble from t_pdt_productdef where fid = '"+fproductid+"'");
//		ResultSet productrs = stmt.executeQuery();
		String sql = "select fnewtype,ifnull(FCombination,0) FCombination,ifnull(FAssemble,0) FAssemble from t_pdt_productdef where fid = '"+fproductid+"'";
		List<HashMap<String,Object>> pdlist= DeliversDao.QueryBySql(sql);
		if(pdlist.size()>0){
			HashMap productrs = pdlist.get(0);
			HashMap productInfo = new HashMap();
			productInfo.put("fid",fproductid);
			productInfo.put("amountRate",new Integer(1));
			productInfo.put("entryProductType","1");
			productInfo.put("ParentOrderEntryId",null);
			if(productrs.get("fnewtype")!=null){
				productInfo.put("fnewtype", productrs.get("fnewtype").toString());
			}else{
				productInfo.put("fnewtype", "0");
			}
			
			if(productrs.get("FCombination")!=null){
				productInfo.put("FCombination", productrs.get("FCombination").toString());
			}else{
				productInfo.put("FCombination", "0");
			}
			
			productInfo.put("FAssemble", productrs.get("FAssemble").toString());
			productInfo.put("fiscombinecrosssubs", new Integer(0));
			getProductSuit(productInfo,list,null);
		}
		
		return list;
	}
	
	private void getProductSuit(HashMap productInfo,List list,String parentEntryId) throws Exception
	{
		boolean isSuit = (productInfo.get("fnewtype").equals("2") || productInfo.get("fnewtype").equals("4"));
		String orderEntryID = saleOrderDao.CreateUUid();
		productInfo.put("orderEntryID",orderEntryID);
		if(!isSuit){
			//如果非套装，自己的分录ID，父分录Id取传入的参数，直接放入Collection
//			BOSUuid orderEntryID = BOSUuid.create(new SaleOrderEntryInfo().getBOSType());
			productInfo.put("ParentOrderEntryId",parentEntryId);
			list.add(productInfo);
		}
		else{
			//如果是套装，自己的分录Id，父分录Id，总套为null，非总套取参数，先把自己放入Collection，再循环递归调用自己的子件
			
			//如果不是首次进入递归，套装的“分录的产品类型”== 非总套
			if(productInfo.get("entryProductType").toString().equals("1")){
				//nothing
			}
			else{
				productInfo.put("entryProductType","2");
				productInfo.put("ParentOrderEntryId",parentEntryId);
			}
			
			list.add(productInfo);
			
			//子件“分录的产品类型”
			String subEntryProductType = "";
			boolean isassemble = false;
			if(productInfo.get("FCombination")!=null && productInfo.get("FCombination").toString().equals("1")){
				//preSuitProductType = 1;
				subEntryProductType = "7";	//合并下料子件
			}
			else if(productInfo.get("FAssemble")!=null && productInfo.get("FAssemble").toString().equals("1")){
				//preSuitProductType = 2;
				subEntryProductType = "6";	//组装套装子件
				isassemble = true;
			}
			else {
				//preSuitProductType = 0;
				subEntryProductType = "5";	//普通套装子件
			}
			
//			ProductDefProductCollection subCols =  productInfo.getProducts();
//			stmt = conn.prepareStatement("select FAmount,FProductID from T_PDT_ProductDefProducts where Fparentid = '"+productInfo.get("fid")+"'");
//			ResultSet productrs = stmt.executeQuery();
			String sql = "select FAmount,FProductID from T_PDT_ProductDefProducts where Fparentid = '"+productInfo.get("fid")+"'";
			List<HashMap<String,Object>> pdlist= DeliversDao.QueryBySql(sql);
			for(int i=0;i<pdlist.size();i++){
				HashMap productrs = pdlist.get(i);
				
//				stmt = conn.prepareStatement("select fid,fnewtype,ftype,FCombination,FAssemble from t_pdt_productdef where fid = '"+productrs.get("FProductID")+"'");
//				ResultSet subproductrs = stmt.executeQuery();
				sql = "select fid,fnewtype,FCombination,FAssemble,fiscombinecrosssubs from t_pdt_productdef where fid = '"+productrs.get("FProductID")+"'";
				List<HashMap<String,Object>> pdslist= DeliversDao.QueryBySql(sql);
				if(pdslist.size()>0){
					HashMap subproductrs = pdslist.get(0);
					HashMap subInfo = new HashMap();
					subInfo.put("fid",subproductrs.get("fid"));
					subInfo.put("amountRate",new Integer(productrs.get("FAmount").toString()) * new Integer(productInfo.get("amountRate").toString()));
					if(subproductrs.get("fnewtype")!=null){
						subInfo.put("fnewtype", subproductrs.get("fnewtype").toString());
					}else{
						subInfo.put("fnewtype", "0");
					}
					
					if(subproductrs.get("FCombination")!=null){
						subInfo.put("FCombination", subproductrs.get("FCombination").toString());
					}else{
						subInfo.put("FCombination", "0");
					}
					
					subInfo.put("FAssemble", subproductrs.get("FAssemble").toString());
					subInfo.put("fiscombinecrosssubs", subproductrs.get("fiscombinecrosssubs").toString());
					//子件的“分录的产品类型”
					subInfo.put("entryProductType",subEntryProductType);
					if(isassemble){
						subInfo.put("FisassembleSuitSub", Boolean.TRUE);
					}else{
						subInfo.put("FisassembleSuitSub", Boolean.FALSE);
					}
					
					getProductSuit(subInfo,list,orderEntryID);
				}
				
			}
			
		}
	}
	/**
	 * 如果配送数量总和等于计划数量 消耗完毕
	 * 计划数量-配送数量=0 消耗完毕
	 * @param request
	 * @param response
	 */
	@RequestMapping("myStockIsconsumed")
	public void myStockIsconsumed(HttpServletRequest request,HttpServletResponse response){
		int fisconsumed = 0;
		int fstate = 1;//初始状态为 已安排下单
		try {
			String sql = "SELECT m.fstate,IFNULL(s.fbalanceqty, 0) fbalanceqty,m.fcustproductid,m.fcustomerid,m.fplanamount fplanamount,m.fsaleorderid FROM mystock m "+
						"INNER JOIN t_ord_productplan p  ON m.fsaleorderid = p.forderid " +
						"LEFT JOIN t_inv_storebalance s ON p.fid = s.fproductplanID "
						+"WHERE 1 = 1 GROUP BY m.fsaleorderid";
			List<HashMap<String,Object>> list = MystockDao.QueryBySql(sql);
			for(int i =0;i<list.size();i++){
				
//				int fusedqty = Integer.parseInt(list.get(i).get("fusedqty").toString());//占用量
				int fplanamount = Integer.parseInt(list.get(i).get("fbalanceqty").toString());//库存量
				if(fplanamount==0){
					fisconsumed = 1;//以消耗完毕
					fstate = 2;//状态改为 已结束备货
				}else{
					fisconsumed = 0;
					fstate = Integer.parseInt(list.get(i).get("fstate").toString());
				}
				MystockDao.ExecBySql("update mystock set fisconsumed="+fisconsumed+",fstate="+fstate+" where fsaleorderid = '"+list.get(i).get("fsaleorderid")+"'");
			}
		} catch (DJException e) {
			// TODO: handle exception
		}
	}
//	public void ltc(HttpServletRequest request,HttpServletResponse response){
//		String fcustomerid = request.getParameter("fcustomerid");
//		try {
//			String sql=" select r.fid,e2.famount,r.fproductid,case when (f.fnewtype='4'or f.fnewtype='2')  then 2 else 1 end as fnewtype,count(e2.fid) counts from t_pdt_productrelationentry e "+
//					" left join t_pdt_productrelation r on e.fparentid=r.fid "+
//					" left join  t_pdt_productdef f on f.fid=r.fproductid  " +
//					" left join t_pdt_productrelationentry e2 on e2.fparentid=r.fid "+
//					" where ifnull(f.fishistory,0)=0 and ifnull(f.feffect,0)=1 and   e.fcustproductid='"+fcusproductid+"' and r.fcustomerid='"+fcustomerid+"'";
//			sql+="group by r.fid order by fnewtype,counts";
//			List<HashMap<String,Object>> ptypelist= MystockDao.QueryBySql(sql);//查询与该客户产品相关的产品类型，产品等	
//			if(ptypelist.size()>0){
//				if("1".equals(ptypelist.get(0).get("counts").toString())){
//				}else{
//					MystockDao.ExecBySql("update mystock set fordernumber='"+"当前客户产品关联多个产品,不能操作！"+"' where fid = '"+fid+"'");
//					continue;
//				}
//				
//			}else
//			{
//				 sql=" select e.famount,e.fproductid,ifnull(d.fishistory,0) fishistory,ifnull(d.feffect,0) dfeffect from  t_pdt_custrelation  r "+
//						" left join t_pdt_custrelationentry e on e.fparentid=r.fid left join  t_pdt_productdef  d on d.fid=e.fproductid "+
//						" where r.fcustproductid='"+fcusproductid+"' and r.fcustomerid='"+fcustomerid+"'   order by d.fishistory desc";
//				 ptypelist= MystockDao.QueryBySql(sql);
//				 if(ptypelist.size()>0){
//				 }
//			}
//		} catch (DJException e) {
//			// TODO: handle exception
//		}
//	}
	//我的备货 生成配送
		@RequestMapping(value="/SaveMyStockOrder")
		public  String SaveMyStockOrder(HttpServletRequest request,
				HttpServletResponse reponse) throws IOException {
			
			try {
				String userid = ((Useronline) request.getSession().getAttribute(
						"Useronline")).getFuserid();
				List<Deliverapply> list= null;
//				DateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String customerid="";
				String sql="select fcustomerid from ( select fcustomerid  from t_bd_usercustomer where fuserid='"+userid+"'"+
						" union select c.fcustomerid from  t_sys_userrole r "+
						" left join t_bd_rolecustomer c on r.froleid=c.froleid where r.fuserid='"+userid+"' )  s where fcustomerid is not null " ;
				List custlist=MystockDao.QueryBySql(sql);
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
				for(int i=0;i<list.size();i++){
					deliverapply = list.get(i);
//					if(deliverapply.getFamount()==0){
//						continue;
//					}
					if(deliverapply.getFamount()<1){
						throw new DJException("配送数量不能小于1！");
					}
					if(deliverapply.getFcusproductid()==null || deliverapply.getFcusproductid().equals("")){
						throw new DJException("包装物名称不能为空！");
					}
					if(deliverapply.getFaddressid()==null || deliverapply.getFaddressid().equals("")){
						throw new DJException("送货地址不能为空！");
					}
					if(!StringUtils.isEmpty(deliverapply.getFtraitid())){
						entry = (SchemeDesignEntry) MystockDao.Query(SchemeDesignEntry.class,deliverapply.getFtraitid());
						if(entry!=null){
							if(entry.getFallot()==0){
								allowAmount=entry.getFentryamount()-entry.getFrealamount();
								if(deliverapply.getFamount()>allowAmount){
									throw new DJException("该特性产品的剩余数量为"+allowAmount+"，配送数量不能大于此数量！");
								}else{
									if(deliverapply.getFamount()==allowAmount){
										entry.setFallot(1);
										entry.setFrealamount(entry.getFentryamount());
									}else{
										entry.setFrealamount(entry.getFrealamount()+deliverapply.getFamount());
									}
									entryList.add(entry);
								}
							}else{
								throw new DJException("该特性产品已消耗完毕!");
							}
						}
						else{
							deliverapply.setFtraitid("");
						}
					}
					deliverapply.setFid(MystockDao.CreateUUid());
					deliverapply.setFnumber(ServerContext.getNumberHelper().getNumber("t_ord_deliverapply", "Y", 4, false));
					deliverapply.setFcreatorid(userid);
					deliverapply.setFcreatetime(new Date());
					deliverapply.setFiscreate(0);
					deliverapply.setFupdatetime(new Date());
					deliverapply.setFupdateuserid(userid);
					deliverapply.setFcustomerid(customerid);
				}
				MystockDao.ExecSave(list,entryList);
				reponse.getWriter().write(JsonUtil.result(true, "保存成功！", "", ""));
			} catch (Exception e) {
				reponse.getWriter().write(
						JsonUtil.result(false, "保存失败，" + e.getMessage(), "", ""));
			}

			return null;
		}
		
		/**
		 * 纸箱订单和备货的 备货列表数据
		 * @param request
		 * @param response
		 * @throws IOException
		 */
		@RequestMapping("getQuickMystockList")
  		public void getQuickMystockList(HttpServletRequest request,HttpServletResponse response) throws IOException{
  			try {
  				String userid = ((Useronline)request.getSession().getAttribute("Useronline")).getFuserid();
  				if(request.getParameter("sort")==null){
  					request.setAttribute("djsort", "m.fnumber desc");
  				}
  				String sql="select  m.fid,m.fplanamount, m.ffinishtime,m.fconsumetime,m.fsupplierid,s.fname suppliername, m.fpcmordernumber,m.fnumber,m.fstate,m.fcustproductid,c.fname custname,c.FSPEC fspec,m.fremark FROM mystock m "
  							+" inner join  t_bd_custproduct c ON c.fid= m.fcustproductid"
  							+" inner join t_sys_supplier s ON s.fid=m.fsupplierid where 1=1 "+MystockDao.QueryFilterByUserofuser(request,"m.fcreateid","and")+MystockDao.QueryFilterByUser(request, "m.fcustomerid", null);
  				ListResult list = MystockDao.QueryFilterList(sql, request);
  				response.getWriter().write(JsonUtil.result(true, "",list));
  			} catch (DJException e) {
  				// TODO: handle exception
  				response.getWriter().write(JsonUtil.result(false,e.getMessage(),"",""));
  			}
  			
  		}
		/***
		 * 获取备货信息
		 * @param request
		 * @param reponse
		 * @return
		 * @throws IOException
		 */
		@RequestMapping("/getQuickMyStockInfo")
		public String getQuickMyStockInfo(HttpServletRequest request,
				HttpServletResponse reponse) throws IOException {
			reponse.setCharacterEncoding("utf-8");
			try {
				String fid = request.getParameter("fid");
				if(MystockDao.QueryExistsBySql("select 1 from mystock where fid ='"+fid+"' and ( fordered=1 or fstate>0)"))
				{
					throw new DJException("已接收订单不能修改");
				}
				String sql = "SELECT fid,fnumber,fcustproductid,fplanamount,IF(IFNULL(funit,'')='','只(套)/片',funit) funit, ffinishtime,fconsumetime,fisconsumed,fcreatetime,fcreateid,fcustomerid,fremark,fsupplierid,fstate,fpcmordernumber,if(faveragefamount=0,'',faveragefamount) faveragefamount,fcharactername,fcharacterid,fsaleorderid,fordered,fordernumber,fordertime,fordermanid,forderentryid FROM mystock where fid = '"+fid+"'" ;
				List<HashMap<String,Object>> result= MystockDao.QueryBySql(sql);
				reponse.getWriter().write(JsonUtil.result(true,"","",result));
			} catch (Exception e) {
				reponse.getWriter().write(JsonUtil.result(false, e.getMessage(), "", ""));
			}
			return null;
		}
		
		/**
		 * 未接收的 备货 可以删除，已接收不能删除
		 * @param request
		 * @param response
		 * @throws IOException
		 */
		@RequestMapping("delQuickMystock")
		public void delQuickMystock(HttpServletRequest request,HttpServletResponse response) throws IOException{
			String fids = request.getParameter("fidcls");
			fids="('"+fids.replace(",","','")+"')";
			try {
				String  sql = "select 1 from mystock where fid in "+fids+" and ( fordered=1 or fstate>0)";
				List<HashMap<String,Object>> list = MystockDao.QueryBySql(sql);
				if(list.size()>0){
					throw new DJException("已接收订单不能删除！");
				}
				sql = "delete from mystock where fid in "+fids;
				MystockDao.ExecBySql(sql);
				response.getWriter().write(JsonUtil.result(true, "删除成功", "",""));
			} catch (DJException e) {
				// TODO: handle exception
				response.getWriter().write(JsonUtil.result(false, e.getMessage(), "",""));
			}
		}
		
		/**
		 * 保存 备货信息
		 * @param request
		 * @param response
		 * @throws ParseException
		 * @throws IOException
		 */
		@RequestMapping("saveOrUpdateQuickMystock")
		public void saveOrUpdateQuickMystock(HttpServletRequest request,HttpServletResponse response) throws ParseException, IOException{

			try{
				//fdescription 不赋值无用
			Mystock mystock = (Mystock)request.getAttribute("Mystock");
			if(MystockDao.QueryExistsBySql("select 1 from mystock where fid = '"+mystock.getFid()+"' and  (fordered=1 or fstate>0)")) throw new DJException("已接收订单不能修改");
			MystockDao.saveOrUpdate(mystock);
				response.getWriter().write(JsonUtil.result(true, "保存成功","",""));
			} catch (DJException e) {
//				 TODO: handle exception
				response.getWriter().write(JsonUtil.result(false, e.getMessage(),"",""));
			}
		}
}