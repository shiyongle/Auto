package Com.Controller.order;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.Sheet;
import jxl.Workbook;
import jxl.format.Alignment;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.tomcat.util.http.fileupload.FileItem;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItemFactory;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;
import org.hibernate.util.StringHelper;
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
import Com.Base.Util.params;
import Com.Base.Util.Excel.ExcelHelper;
import Com.Base.Util.Excel.ExcelHelperAuxiliaryConvenience;
import Com.Base.Util.mySimpleUtil.MySimpleToolsZ;
import Com.Controller.System.RoleController;
import Com.Dao.Inv.IStorebalanceDao;
import Com.Dao.System.IBaseSysDao;
import Com.Dao.System.IVmipdtParamDao;
import Com.Dao.order.IDeliverorderDao;
import Com.Dao.order.IDeliversDao;
import Com.Dao.order.IProductPlanDao;
import Com.Dao.order.ISaleOrderDao;
import Com.Entity.Inv.Storebalance;
import Com.Entity.System.Useronline;
import Com.Entity.order.Cusdelivers;
import Com.Entity.order.Deliverorder;
import Com.Entity.order.Delivers;
import Com.Entity.order.Mystock;
import Com.Entity.order.ProductPlan;
import Com.Entity.order.Saleorder;

@Controller
public class DeliversController {
	Logger log = LoggerFactory.getLogger(RoleController.class);
	@Resource
	private IDeliversDao DeliversDao;
	@Resource
	private ISaleOrderDao saleOrderDao;
	@Resource
	private IVmipdtParamDao VmipdtParamDao;
	@Resource
	private IStorebalanceDao storebalanceDao; 
	@Resource
	private IDeliverorderDao DeliverorderDao; 
	@Resource
	private IProductPlanDao ProductPlanDao; 
	@Resource
	private IBaseSysDao baseSysDao;
	private boolean assginflag=false;
	
	@RequestMapping("/GetRPTUIData")
	public String GetRPTUIData(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		String sql = "select distinct a.fnumber fapplynum,c.fname fcustpdtname,a.famount,d.fnumber fdeliversnum,d.fordernumber,d.fordered,v.fproductQty from t_ord_deliverapply a left join t_bd_custproduct c on c.fid=a.fcusproductid left join t_ord_delivers d on d.fapplayid=a.fid left join t_ord_deliverorder dl on dl.fdeliversid = d.fid left join productQty_view v on v.fproductid=d.fproductid where v.fproductqty>0 and dl.fouted=1 "+DeliversDao.QueryFilterByUser(request, "a.fcustomerid", null);
		
		ListResult result;
		reponse.setCharacterEncoding("utf-8");
		try {
			result = DeliversDao.QueryFilterList(sql, request);
			reponse.getWriter().write(JsonUtil.result(true,"",result));
		} catch (DJException e) {
			reponse.getWriter().write(JsonUtil.result(false,e.getMessage(),"",""));
		}
	
		return null;
	}
	
	@RequestMapping("/GetDeliversList")
	public String GetDeliversList(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		String fcustomerid = request.getParameter("fcustomerid");
		String sql = "select d.fpcmordernumber,d.fapplynumber, d.fordermanid,d.fproductid,ifnull(f.fname,'') pname,ifnull(u3.fname,'') forderman,ifnull(d.fordertime,'') fordertime,ifnull(d.fordered,0) fordered,u1.fname as fcreator,u2.fname as flastupdater,c.fname as fcustname,cpdt.fname cutpdtname,d.fid,d.fcreatorid,d.fcreatetime,d.fupdateuserid,d.fupdatetime,d.fnumber,d.fcustomerid,d.fcusproductid,date_format(d.farrivetime,'%Y-%m-%d %T') farrivetime,d.flinkman,d.flinkphone,d.famount,d.faddress,d.fdescription,fsaleorderid,ifnull(fordernumber,'') fordernumber,forderentryid,d.fimportEAS,d.fimportEASuserid,d.fimportEAStime,ifnull(d.fouted,0) fouted,d.foutorid,d.fouttime,ifnull(d.falloted,0) falloted,d.ftype,ifnull(pd.fcharacter,'') fcharacter,sup.FNAME fsupplier from t_ord_delivers d left join t_sys_user u1 on u1.fid=d.fcreatorid left join t_sys_user u2 on u2.fid=d.fupdateuserid left join t_sys_user u3 on u3.fid=d.fordermanid left join t_bd_customer c on c.fid=d.fcustomerid left join t_bd_custproduct cpdt on cpdt.fid=d.fcusproductid  left join t_pdt_productdef f on f.fid=d.fproductid left join t_ord_schemedesignentry pd on pd.fid=d.ftraitid LEFT JOIN t_sys_supplier sup ON sup.fid = d.fsupplierid where 1=1 ";
		if (fcustomerid!=null&&!"".equals(fcustomerid)) {
			sql += " and d.fcustomerid='" + fcustomerid + "'";
		}
		ListResult result;
		reponse.setCharacterEncoding("utf-8");
		try {
//			request.setAttribute("nocount", "nocount");
			result = DeliversDao.QueryFilterList(sql, request);
//			result.setTotal("10000");
			reponse.getWriter().write(JsonUtil.result(true,"",result));
		} catch (DJException e) {
			reponse.getWriter().write(JsonUtil.result(false,e.getMessage(),"",""));
		}
	
		return null;
	}
	
	@RequestMapping("/GetGenerateDeliversList")
	public String GetGenerateDeliversList(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		ListResult result;
		reponse.setCharacterEncoding("utf-8");
		
		try {
			//String sql = "select c.fname as fcustname,cpdt.fname cutpdtname,cpdt.fid fcusproductid,d.fid,d.fnumber,c.fid fcustomerid,date_format(d.farrivetime,'%Y-%m-%d %T') farrivetime,d.flinkman,d.flinkphone,d.famount,d.faddress,a.fid faddressid,d.fdescription,ifnull(d.fisread,0) fisread from t_ord_cusdelivers d left join t_bd_custproduct cpdt on cpdt.fname=d.fproductname left join t_bd_customer c on c.fid=cpdt.FCUSTOMERID left join t_bd_address a on a.fname=d.faddress ";
//			String sql ="select c.fname as fcustname,d.freqdate,d.fmaktx as fmaktx,d.freqaddress freqaddress,cpdt.fname cutpdtname,cpdt.fid fcusproductid,d.fid,d.fnumber,c.fid fcustomerid,date_format(d.farrivetime,'%Y-%m-%d %T') farrivetime,d.flinkman,d.flinkphone,d.famount,a.fdetailaddress as faddress,a.fid faddressid,d.fdescription,ifnull(d.fisread,0) fisread from ";
			String sql ="select c2.fproductmatching,cpdt.feffect,d.ftype,c.fid as fcustid,c.fname as fcustname,d.freqdate,d.fmaktx as fmaktx,replace(replace(replace(REPLACE(ifnull(d.freqaddress,''),'\"','\\\\\"'),'\\r\\n',' '),'\\r',' '),'\\n',' ') freqaddress,cpdt.fname cutpdtname,cpdt.fid fcusproductid,d.fid,d.fnumber,c.fid fcustomerid,u.fname creator,date_format(d.fcreatetime,'%Y-%m-%d %T') fcreatetime,date_format(d.farrivetime,'%Y-%m-%d %T') farrivetime,d.flinkman,replace(REPLACE(ifnull(d.flinkphone,''),'\"','\\\\\"'),'--','-') flinkphone,d.famount,a.fdetailaddress as faddress,a.fid faddressid,replace(replace(replace(REPLACE(ifnull(d.fdescription,''),'\"','\\\\\"'),'\\r\\n',' '),'\\r',' '),'\\n',' ') fdescription,ifnull(d.fisread,0) fisread, sup.fname fsupplier,d.fsupplierid,d.fmaksupplier from ";
			sql=sql+" t_ord_cusdelivers d left join t_bd_custproduct cpdt on cpdt.fid=d.fcusproduct left join t_sys_user u on u.fid=d.fcreatorid left join t_bd_customer c on c.fid=d.FCUSTOMERID left join t_bd_address a on a.fid=d.faddress left join t_bd_custproduct c2 on c2.fname = d.fmaktx left join t_sys_supplier sup ON sup.`FID` = d.fsupplierid where fisread=0 ";
			sql = sql + DeliversDao.QueryFilterByUser(request, "d.FCUSTOMERID", "");
			request.setAttribute("djgroup", "d.fid");
			result = DeliversDao.QueryFilterList(sql, request);
			reponse.getWriter().write(JsonUtil.result(true,"",result));
		} catch (DJException e) {
			reponse.getWriter().write(JsonUtil.result(false,e.getMessage(),"",""));
		}
	
		return null;
	}
	
	@RequestMapping(value = "/GenerateDeliversToExcel")
	public String generateDeliversToExcel(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		try {
			String sql = " SELECT CASE d.ftype WHEN 1 THEN '备货' ELSE '要货' END AS 类型 ,c.fname 客户名称 ,d.freqdate 发放配送时间,d.fmaktx 发放产品名称,REPLACE(REPLACE(REPLACE(REPLACE(IFNULL(d.freqaddress,''),'\"','\\\\\"'),'\\r\\n',' '),'\\r',' '),'\\n',' ') 发放地址,cpdt.fname 客户产品,d.fnumber 申请单号,u.fname 导入人,DATE_FORMAT(d.fcreatetime,'%Y-%m-%d %T') 导入时间,DATE_FORMAT(d.farrivetime,'%Y-%m-%d %T') 配送时间,d.flinkman 发放人,REPLACE(REPLACE(IFNULL(d.flinkphone,''),'\"','\\\\\"'),'--','-') 联系电话,d.famount 发放数量,a.fdetailaddress 配送地址,REPLACE(REPLACE(REPLACE(REPLACE(IFNULL(d.fdescription,''),'\"','\\\\\"'),'\\r\\n',' '),'\\r',' '),'\\n',' ') 备注,CASE IFNULL(d.fisread,0) WHEN 1 THEN '是' ELSE '否' END AS 读取 FROM ";
			 sql = sql+" t_ord_cusdelivers d LEFT JOIN t_bd_custproduct cpdt ON cpdt.fid=d.fcusproduct LEFT JOIN t_sys_user u ON u.fid=d.fcreatorid LEFT JOIN t_bd_customer c ON c.fid=d.FCUSTOMERID LEFT JOIN t_bd_address a ON a.fid=d.faddress LEFT JOIN t_bd_custproduct c2 ON c2.fname = d.fmaktx where fisread=0 ";
			 sql = sql + DeliversDao.QueryFilterByUser(request, "d.FCUSTOMERID", "");
			 if(request.getParameterMap().containsKey("myids")){
					String fids = request.getParameter("myids");
					fids="('"+fids.replace(",","','")+"')";
					sql = sql+" and d.fid in "+fids;
			 }
			ListResult result = DeliversDao.QueryFilterList(sql, request);
			result.setTotal("GenerateDelivers");
			ExcelUtil.toexcel(reponse,result);
		} catch (DJException e) {
			reponse.getWriter().write(
					JsonUtil.result(false, e.getMessage(), "", ""));
		}

		return null;

	}
	
	@RequestMapping(value = "/newGenerateDeliversToExcel")
	public String newGenerateDeliversToExcel(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		try {
			String sql ="select c2.fproductmatching,cpdt.feffect,case d.ftype when 0 then '要货' else '备货' end as ftype,c.fid as fcustid,c.fname as fcustname,d.freqdate,d.fmaktx as fmaktx,replace(replace(replace(REPLACE(ifnull(d.freqaddress,''),'\"','\\\\\"'),'\\r\\n',' '),'\\r',' '),'\\n',' ') freqaddress,cpdt.fname cutpdtname,cpdt.fid fcusproductid,d.fid,d.fnumber,c.fid fcustomerid,u.fname creator,date_format(d.fcreatetime,'%Y-%m-%d %T') fcreatetime,date_format(d.farrivetime,'%Y-%m-%d %T') farrivetime,d.flinkman,replace(REPLACE(ifnull(d.flinkphone,''),'\"','\\\\\"'),'--','-') flinkphone,d.famount,a.fdetailaddress as faddress,a.fid faddressid,replace(replace(replace(REPLACE(ifnull(d.fdescription,''),'\"','\\\\\"'),'\\r\\n',' '),'\\r',' '),'\\n',' ') fdescription, case ifnull(d.fisread,0) when 0 then '否' else '是'  end as fisread, sup.fname fsupplier from ";
			sql=sql+" t_ord_cusdelivers d left join t_bd_custproduct cpdt on cpdt.fid=d.fcusproduct left join t_sys_user u on u.fid=d.fcreatorid left join t_bd_customer c on c.fid=d.FCUSTOMERID left join t_bd_address a on a.fid=d.faddress left join t_bd_custproduct c2 on c2.fname = d.fmaktx left join t_sys_supplier sup ON sup.`FID` = d.fsupplierid where fisread=0 ";
			sql = sql + DeliversDao.QueryFilterByUser(request, "d.FCUSTOMERID", "");
			String fids = request.getParameter("myids");
			if(!StringUtils.isEmpty(fids)){
				fids="('"+fids.replace(",","','")+"')";
				sql = sql+" and d.fid in "+fids;
				
			}
			request.setAttribute("djgroup", "d.fid");
			/* if(request.getParameterMap().containsKey("myids")){
					String fids = request.getParameter("myids");
			 }*/
				ListResult result = MySimpleToolsZ.getMySimpleToolsZ().buildExcelListResult(sql, request, DeliversDao);				
				List<String> order = MySimpleToolsZ.gainDataIndexList(request);				
				toexcel(reponse, result, order);
		} catch (DJException e) {
			reponse.getWriter().write(
					JsonUtil.result(false, e.getMessage(), "", ""));
		}

		return null;

	}
	
	@RequestMapping(value="/SaveGenerateDelivers")
	public  String SaveGenerateDelivers(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
	String result = "";
	try{
//		String[][] verifyNullStr =new String[][]{{"fdeliverorderId","运货单号"},{"fremark","备注"}};
//		DataUtil.verifyNotNullAndDataAndPermissions(verifyNullStr,
//				new String[0][0], request, null, null,
//				DeliversDao);
		String fid = request.getParameter("fid");
		String sql="";
		String faddress = "";
		String cutpdtname = "";
		if(request.getParameter("faddress")!=null){
			DataUtil.verifyNotNullAndDataAndPermissions(new String[][]{{"fid","标识"}},
					new String[][]{{"faddress", "t_bd_address","fcustomerid",null},{"fid","t_ord_cusdelivers","fcustomerid",null}}, request, 
					DeliversDao);
			sql="update t_ord_cusdelivers set faddress ='"+request.getParameter("faddress")+"' where fid = '"+fid+"'";
		}
		if(request.getParameter("cusproduct")!=null){
			DataUtil.verifyNotNullAndDataAndPermissions(new String[][]{{"fid","标识"}},
					new String[][]{{"cusproduct", "t_bd_custproduct","fcustomerid",null},{"fid","t_ord_cusdelivers","fcustomerid",null}}, request,
					DeliversDao);
			sql="update t_ord_cusdelivers set fcusproduct ='"+request.getParameter("cusproduct")+"' where fid = '"+fid+"'";
		}
		DeliversDao.ExecBySql(sql);
		result = JsonUtil.result(true,"保存成功", "", "");
	}
	catch(Exception e)
	{
		result = JsonUtil.result(false,e.getMessage(), "", "");
	}
	reponse.setCharacterEncoding("utf-8");
	reponse.getWriter().write(result);
		return null;

	}
	
	@RequestMapping("/uploadFile")
	public String uploadFile(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		reponse.setCharacterEncoding("utf-8");
		request.setCharacterEncoding("utf-8");
		String error = "";
		int maxSize = 50 * 1024 * 1024; // 单个上传文件大小的上限
		DiskFileItemFactory factory = new DiskFileItemFactory(); // 基于磁盘文件项目创建一个工厂对象
		ServletFileUpload upload = new ServletFileUpload(factory); // 创建一个新的文件上传对象
		try {
			@SuppressWarnings("unchecked")
			List<FileItem> list = upload.parseRequest(request);// 解析上传请求
			Iterator<FileItem> itr = list.iterator();// 枚举方法
			while (itr.hasNext()) {
				FileItem item = itr.next(); // 获取FileItem对象
				if (!item.isFormField()) {// 判断是否为文件域
					if (item.getName() != null && !item.getName().equals("")) {// 判断是否选择了文件
						long upFileSize = item.getSize(); // 上传文件的大小
						String fileName = item.getName(); // 获取文件名
						if (upFileSize > maxSize) {
							error = "您上传的文件太大，请选择不超过50M的文件";
							throw new DJException(error);
//							break;
						}
						// request.getSession().getServletContext().getRealPath("/img");//上传到服务器路径
//						String uploadPath = "d:/";  //自定义路径
						String uploadPath = request.getSession().getServletContext().getRealPath("/");
						File file = new File(uploadPath, "Excel上传.xls"); 
						if(file.exists()){
							file.delete();
						}
						InputStream is = item.getInputStream();
						int buffer = 1024; // 定义缓冲区的大小
						int length = 0;
						byte[] b = new byte[buffer];
						FileOutputStream fos = new FileOutputStream(file);
						while ((length = is.read(b)) != -1) {
							fos.write(b, 0, length); // 向文件输出流写读取的数据
						}
						fos.close();
						Thread.sleep(1000); // 线程休眠1秒
					} else {
						error = "没有选择上传文件！";
						throw new DJException(error);
					}
				}
			}
		} catch (Exception e) {
			error = "上传文件出现错误：" + e.getMessage();
		}
		if (!"".equals(error)) {
			reponse.getWriter().write("{success:false,msg:'" + error + "'");
		} else {
			importCusdelivers(request,reponse);
			String execsql="update t_ord_cusdelivers cusd,t_bd_custproduct cpd set cusd.fcusproduct=cpd.fid where cusd.fisread=0 and ifnull(cusd.fcusproduct,'')='' and cusd.fmaktx=cpd.fname ";
			DeliversDao.ExecBySql(execsql);
			 execsql="update t_ord_cusdelivers cusd,t_bd_address ad set cusd.faddress=ad.fid where cusd.fisread=0 and ifnull(cusd.faddress,'')='' and cusd.freqaddress=ad.fname ";
			 DeliversDao.ExecBySql(execsql);
			reponse.getWriter().write("{success:true,msg:'文件上传成功!'}");
		}
		return null;
	}
	
	private void importCusdelivers(HttpServletRequest request,HttpServletResponse reponse) throws IOException {
		// TODO Auto-generated method stub
		Workbook rwb = null;

		try {

			/**
			 * 相对于项目的根目录
			 */
			String sourcefile = request.getSession().getServletContext().getRealPath("/Excel上传.xls");

			File ft = new File(sourcefile);
			final String fcustomerid = request.getParameter("action");
			// 构建Workbook对象, 只读Workbook对象
			// 直接从本地文件创建Workbook
			// 从输入流创建Workbook
			// InputStream is = new FileInputStream(ft);
			rwb = Workbook.getWorkbook(ft);

			Sheet rs = rwb.getSheet(0);

//			Cusdelivers[] cuss = getCusdeliversBySheet(rs);

			ExcelHelper.insertExcelInDbFacade(Cusdelivers.class, rs,  new ExcelHelperAuxiliaryConvenience() {
				
				@Override
				public String createId() {
					// TODO Auto-generated method stub
					return DeliversDao.CreateUUid();
				}

				@Override
				public void saveOrupdateObj(Object obj) {
					// TODO Auto-generated method stub
					Cusdelivers ct = (Cusdelivers)obj;
					ct.setFcustomerid(fcustomerid);
					ct.setFid(createId());
//					ct.setFreqdate(new Date());
					if(ct.getFarrivetime()!=null){
						Date arrivetime = ct.getFarrivetime();
						arrivetime.setHours(arrivetime.getHours()-8);
						ct.setFarrivetime(arrivetime);
						ct.setFreqdate(arrivetime);
					}
					DeliversDao.saveOrUpdate(obj);
				}

				@Override
				public void insertAction(String sql, Map<String, Object> map) {
					// TODO Auto-generated method stub
		
							params p = new params((HashMap<String, Object>) map);
							DeliversDao.ExecBySql(sql, p);
					
				}
			});
//			reponse.getWriter().write(JsonUtil.result(true, "", "", ""));
			
		} catch (Exception e) {
//			reponse.getWriter().write(JsonUtil.result(false, e.getMessage(), "", ""));
			throw new DJException(e.getMessage());
		} finally {
			if (rwb != null) {
				rwb.close();

			}
		}
	}
//	@RequestMapping(value="/SaveDelivers")
//	public  String SaveDelivers(HttpServletRequest request,
//			HttpServletResponse reponse) throws IOException {
//	String result = "";
//	Delivers vinfo=null;
//	try{
//	String userid = ((Useronline)request.getSession().getAttribute("Useronline")).getFuserid();
//	String fid=request.getParameter("fid");
//	if(fid!=null&&!"".equals(fid))
//	{
//		vinfo=DeliversDao.Query(fid);
//		if(vinfo.getFordered()==1){
//			throw new DJException("已下单不能修改!");
//		}
//		vinfo.setFnumber(request.getParameter("fnumber"));
//	}else
//	{
//		vinfo=new Delivers();
////		vinfo.setFid(DeliversDao.GetEASid("57C24810"));
//		vinfo.setFid(fid);
//		vinfo.setFnumber(ServerContext.getNumberHelper().getNumber("t_ord_delivers","DV",4,false));
////		vinfo.setFnumber(DeliversDao.getFnumber("t_ord_delivers","DV",4));
//		vinfo.setFcreatorid(userid);
//		vinfo.setFcreatetime(new Date());
//		vinfo.setFordermanid(null);
//		vinfo.setFordered(0);
//		vinfo.setFouted(0);
//		vinfo.setFalloted(0);
//		vinfo.setFordertime(null);
//		
//	}
//	vinfo.setFupdatetime(new Date());
//	vinfo.setFupdateuserid(userid);
//	vinfo.setFcustomerid(request.getParameter("fcustomerid"));
//	vinfo.setFcusproductid(request.getParameter("fcusproductid"));
//	vinfo.setFaddressid(request.getParameter("faddressid"));
//	vinfo.setFaddress(request.getParameter("faddress"));
//	DateFormat f = new SimpleDateFormat("yyyy-MM-dd HH");
//	vinfo.setFarrivetime(!request.getParameter("farrivetime").isEmpty() ? f.parse(request.getParameter("farrivetime").toString()) : null);
//	vinfo.setFlinkman(request.getParameter("flinkman"));
//	vinfo.setFlinkphone(request.getParameter("flinkphone"));
//
//	
//	if(request.getParameter("famount")!=null){
//		vinfo.setFamount(new Integer(request.getParameter("famount")));
//	}else{
//		vinfo.setFamount(0);
//	}
//	vinfo.setFdescription(request.getParameter("fdescription"));
//	
//	HashMap<String, Object> params = DeliversDao.ExecSave(vinfo);
//	System.out.println(params);
//	if (params.get("success") == Boolean.TRUE) {
//		result = JsonUtil.result(true,"保存成功!", "", "");
//	} else {
//		result = JsonUtil.result(false,"保存失败!", "", "");
//	}
//	}
//	catch(Exception e)
//	{
//		result = JsonUtil.result(false,e.getMessage(), "", "");
//	}
//	reponse.setCharacterEncoding("utf-8");
//	reponse.getWriter().write(result);
//		return null;
//
//	}

	
//	@RequestMapping("/DelDeliversList")
//	public String DelDeliversList(HttpServletRequest request,
//			HttpServletResponse reponse) throws IOException {
//		String result = "";
//		String fidcls = request.getParameter("fidcls");
//		fidcls="('"+fidcls.replace(",","','")+"')";
//		try {
//			String hql = "Delete FROM Delivers where fordered=0 and falloted = 0 and fid in " + fidcls;
//
//			DeliversDao.ExecByHql(hql);
//			result = JsonUtil.result(true,"删除成功!", "", "");
//			reponse.setCharacterEncoding("utf-8");
//		} catch (Exception e) {
//			result = JsonUtil.result(false,e.getMessage(), "", "");
//			log.error("DelDeliversList error", e);
//		}
//		reponse.getWriter().write(result);
//		return null;
//	}
	
	@RequestMapping("/getDeliversInfo")
	public String getDeliversInfo(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		reponse.setCharacterEncoding("utf-8");
		try {
			String fid = request.getParameter("fid");
//			String sql = "select u1.fname as fcreator,u2.fname as flastupdater,c.fname as fcustomerid_fname,cpdt.fname fcusproductid_fname,d.fid,d.fcreatorid,d.fcreatetime,d.fupdateuserid,d.fupdatetime,d.fnumber,d.fcustomerid fcustomerid_fid,d.fcusproductid fcusproductid_fid,d.farrivetime,d.flinkman,d.flinkphone,d.famount,d.faddress,d.fdescription ,d.faddressid faddressid_fid,ifnull(a.fname,'') faddressid_fname from t_ord_delivers d left join t_sys_user u1 on  u1.fid=d.fcreatorid left join t_sys_user u2 on  u2.fid=d.fupdateuserid left join t_bd_customer c on c.fid=d.fcustomerid left join t_bd_custproduct cpdt on cpdt.fid=d.fcusproductid  left join t_bd_address a on a.fid=d.faddressid where d.fid = '"+request.getParameter("fid")+"'" ;
			String sql = "select fid,fnumber,famount ,fproductid from t_ord_delivers where fid='"+fid+"'";
			List<HashMap<String,Object>> result= DeliversDao.QueryBySql(sql);
			reponse.getWriter().write(JsonUtil.result(true,"","",result));
		} catch (Exception e) {
			reponse.getWriter().write(JsonUtil.result(false, e.getMessage(), "", ""));
		}
		return null;
	}

//	@RequestMapping("/AddDelivers")
//	public String AddDelivers(HttpServletRequest request,
//			HttpServletResponse reponse) throws IOException {
//		reponse.setCharacterEncoding("utf-8");
//		reponse.getWriter().write(JsonUtil.result(true,"","",""));
//		return null;
//
//	}
	
	@RequestMapping(value = "/deliverstoExcel")
	public String deliverstoExcel(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		try {
			String sql = "select u1.fname as 创建人,u2.fname as 修改人,c.fname as 客户名称,cpdt.fname 客户产品名称,d.fid,d.fcreatetime 创建时间,d.fupdatetime 修改时间,d.fnumber 采购订单号,d.fcustomerid,d.fcusproductid,date_format(d.farrivetime,'%Y-%m-%d') 送达时间,d.flinkman 联系人,d.flinkphone 联系电话,d.famount 配送数量,d.faddress 配送地址,d.fdescription 备注  from t_ord_delivers d left join t_sys_user u1 on  u1.fid=d.fcreatorid left join t_sys_user u2 on  u2.fid=d.fupdateuserid left join t_bd_customer c on c.fid=d.fcustomerid left join t_bd_custproduct cpdt on cpdt.fid=d.fcusproductid ";
//			String fcustomerid = request.getParameter("fcustomerid");
//			if (!"".equals(fcustomerid)&&fcustomerid!=null) {
//				sql += " where d.fcustomerid='" + fcustomerid + "'";
//			}
			ListResult result;
			result = DeliversDao.QueryFilterList(sql, request);
			ExcelUtil.toexcel(reponse,result);
//			InputStream inputStream = new FileInputStream(ExcelUtil.toexcel(result));
//			OutputStream os = reponse.getOutputStream();
//			byte[] b = new byte[1024];
//			int length;
//			while ((length = inputStream.read(b)) > 0) {
//				os.write(b, 0, length);
//			}
//			inputStream.close();
		} catch (DJException e) {
			// TODO Auto-generated catch block
			reponse.getWriter().write(
					JsonUtil.result(false, e.getMessage(), "", ""));
		}

		return null;

	}

//	@RequestMapping(value = "/order")
//	public String order(HttpServletRequest request,HttpServletResponse reponse) throws Exception {
//		if(running){
//			return null;
//		}
//		running = true;
//		String result = "";
//		Delivers pinfo = null;
//		try {
//			String userid = ((Useronline) request.getSession().getAttribute(
//					"Useronline")).getFuserid();
//			String fid = request.getParameter("fid");
//			if (fid != null && !"".equals(fid)) {
//				pinfo = DeliversDao.Query(fid);
//				if(pinfo.getFordered()==1){
//					throw new DJException("已下单不能再次下单!");
//				}else{
//					pinfo.setFid(fid);
//					pinfo.setFordermanid(userid);
//					pinfo.setFordered(1);
//					pinfo.setFordertime(new Date());
//				}
//			} else {
//				throw new DJException("未保存不能下单!");
//			}
//			
////			Class.forName("oracle.jdbc.driver.OracleDriver");
////			Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@192.168.0.198:1521:orcl", "dbo_update", "dbo_update");
//			Connection conn=DeliversDao.GetEASConn().GetConn(request);
////			String orderEntryid = saleOrderDao.GetEASid("BF9A2C0D");
//			String orderid = saleOrderDao.GetEASid("A8114365");
//			try {
//				String fnumber = request.getParameter("fnumber");
//				String fcusproductid = request.getParameter("fcusproductid");
//				String flinkman = request.getParameter("flinkman");
//				String flinkphone = request.getParameter("flinkphone");
//				String vmicustomerid = request.getParameter("fcustomerid");
//				int famount = 0;
//				if(request.getParameter("famount")!=null){
//					famount = Integer.valueOf(request.getParameter("famount"));
//				}
////				DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//				DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
//				Date farrivetime = df.parse(request.getParameter("farrivetime"));
//				String faddress = request.getParameter("faddress");
//				
//				String sql = "select fid,feasproductid from t_pdt_productdef where fid in (select fproductdefid from t_sys_custrelationproduct where fcustid =:fcustid )";
//				params p=new params();
//				p.put("fcustid",fcusproductid);
//				List<HashMap<String,Object>> data= DeliversDao.QueryBySql(sql, p);
////				if (!data.isEmpty()) {
////					JSONArray j = JSONArray.fromObject(data);
//					
//					PreparedStatement stmt = null;
//					
//					BigDecimal stock = new BigDecimal(0);
//					
////					for (int i = 0; i < j.size(); i++) {
////					if (j.size()>0) {//客户产品只有一个对应的产品ID;
//					if (data.size()>0) {//客户产品只有一个对应的产品ID;
////						JSONObject o = j.getJSONObject(0);
//						HashMap o = data.get(0);
//						String easproductid = "";
//						String vmiproductid = ""; 
//						if(o.get("feasproductid")!=null){
//							easproductid = o.get("feasproductid").toString();
//							vmiproductid = o.get("fid").toString();
//						}
//						int ftype ;
//						int fnewtype ;
//						String isassemble = "";
//						String EAScustomerid = "";
//						try{
//							ResultSet rs = null;
//							stmt = conn.prepareStatement("select d.fid,d.fassemble,d.ftype,d.fnewtype,d.fcustomerid,d.feffect,c.fstate from t_pdt_productdef d left join T_BAL_CooperateLogger c on c.fcustomerid=d.fcustomerid where d.fid='"+ easproductid + "' and  c.FCreateTime in (select max(c.FCreateTime) from T_BAL_CooperateLogger c where c.fcustomerid = d.fcustomerid) ");
//							ResultSet rst = stmt.executeQuery();
//							if(rst.next()){
//								
//								if(rst.getString("fstate").equals("2")){
//									throw new DJException("客户帐户已关闭，无法自动下单！");
//								}
//								if(rst.getString("feffect").equals("0")){
//									throw new DJException("产品被禁用，无法自动下单！");
//								}
//								
//								isassemble = rst.getString("fassemble");
//								ftype = rst.getInt("ftype");
//								fnewtype = rst.getInt("fnewtype");
//								EAScustomerid = rst.getString("fcustomerid");
//								
//								//库存数量
//								if(isassemble.equals("1") || ftype==1){
//									stmt = conn.prepareStatement("select NVL(sum(b.ffbalanceqty),0) balanceqty  from t_inv_storebalance b where b.ffcustomerid='"+EAScustomerid+"' and b.ffproductid='"+easproductid+"' ");
//									rs = stmt.executeQuery();
//									if (rs.next())
//									{
//											stock=rs.getBigDecimal("balanceqty");
//									}
//										
//									//在生产数量
//									stmt = conn.prepareStatement("select NVL(sum(case when en.FManufQty<NVL(en.fstockinqty,0) then 0 else en.FManufQty-NVL(en.fstockinqty,0) end),0) unAmount from t_ord_saleorder s inner join t_ord_saleorderentry en on en.fparentid=s.fid where exists (select fid from t_inv_productininform g where g.forderentryid=en.fid and g.fclosed=0) and s.fcustomerid='"+EAScustomerid+"' and en.fproductid='"+easproductid+"' ");
//									rs = stmt.executeQuery();
//									if (rs.next())
//									{
//											stock=stock.add(rs.getBigDecimal("unAmount"));
//											if(stock.compareTo(new BigDecimal(0))<0){
//												stock=new BigDecimal(0);
//											}
//									}
//									
//								}else{
//									//套装
//									String productIDs = "";
//									stmt = conn.prepareStatement("select d.fid,d.fcombination from T_PDT_ProductDefProducts pd left join t_pdt_productdef d on d.fid=pd.fproductid where fparentid = '"+easproductid+"' ");
//									rs = stmt.executeQuery();
//									while(rs.next()){
//										
//											if(rs.getString("fcombination") != null && rs.getString("fcombination").equals("0")){
//												if(productIDs.length()>0){
//													productIDs = productIDs + "','" + rs.getString("fid");
//												}else{
//													productIDs = "('" + rs.getString("fid");
//												}
//											}
//									}
//									if(productIDs.length()==0){
//										productIDs = "('" ;
//									}
//									productIDs = productIDs + "')";
//
//									
//									//套装库存总量
//									StringBuffer oql = new StringBuffer("select NVL(min(qty),0) Qty from (select t.fproductid,NVL(sum(t1.ffbalanceqty/t.famount),0)+sum((case when t2.fmanufqty<NVL(t2.fstockinqty,0) then 0 else t2.fmanufqty-NVL(t2.fstockinqty,0) end)/t.famount) qty from T_PDT_ProductDefProducts t ");
//									oql.append("left join t_inv_storebalance t1 on t.fproductid=t1.ffproductid left join t_ord_saleorderentry t2 on t2.fproductid=t.fproductid where exists (select fid from t_inv_productininform g where g.forderentryid=t2.fid and g.fclosed=0) and t.fparentid='").append(easproductid).append("' and t.fproductid in ").append(productIDs).append(" group by t.fproductid) ");
//									stmt = conn.prepareStatement(oql.toString());
//									rs = stmt.executeQuery();
//									if (rs.next())
//									{
//											stock=rs.getBigDecimal("Qty");
//									}
//								}
//								
//								//库存需要减去未审核销售出单的数量;
//								stmt = conn.prepareStatement("select NVL(sum(sn.famount),0) famount from t_tra_saledeliver s inner join t_tra_saledeliverentry sn on sn.fparentid=s.fid where s.faudited=0 and s.fcustomerid='"+EAScustomerid+"' and sn.fproductid='"+easproductid+"' ");
//								rs = stmt.executeQuery();
//								if (rs.next())
//								{
//										stock=stock.subtract(rs.getBigDecimal("famount"));
//								}
//								
//							}else{
//								throw new DJException("EAS系统没有对应的产品下单！");
//							}
//						}catch(Exception e){
//							throw new DJException(e.getMessage());
//						}
//						
//						int balanceqty = stock.divide(new BigDecimal(1), 0, BigDecimal.ROUND_DOWN).intValue();
//						String deliverNum = getFnumber(2,conn,stmt);
//						if(famount>balanceqty){//要货数量大于EAS系统库存数量按VMI安全库存下单数量生成VMI订单;
//							int orderamount = 0;
//							sql = "select forderamount from t_pdt_vmiproductparam where fcustomerid =:fcustomerid and fproductid =:fproductid ";
////							sql = "select forderamount from t_pdt_vmiproductparam where fcustomerid = '"+vmicustomerid+"' and fproductid = '"+vmiproductid+"' ";
//							p=new params();
//							p.put("fcustomerid",vmicustomerid);
//							p.put("fproductid",vmiproductid);
//							List<HashMap<String,Object>> vmipdtparamlist = VmipdtParamDao.QueryBySql(sql, p);
////							List<HashMap<String,Object>> vmipdtparamlist = VmipdtParamDao.QueryBySql(sql);
////							if (!vmipdtparamlist.isEmpty()) {
////								JSONArray js = JSONArray.fromObject(vmipdtparamlist);
////								if (js.size()>0) {
////									JSONObject vmipdtparam = js.getJSONObject(0);
////									if(vmipdtparam.get("forderamount")!=null){
////										orderamount = vmipdtparam.get("forderamount").toString();
////									}else{
////										throw new DJException("安全库存没有此客户对应的产品记录！");
////									}
////								}
//							if(vmipdtparamlist.size()>0){
//								HashMap vmipdtparam = vmipdtparamlist.get(0);
//								if(vmipdtparam.get("forderamount")!=null){
//									orderamount = new Integer(vmipdtparam.get("forderamount").toString());
//								}
//								else{
////									throw new DJException("安全库存没有此客户对应的产品记录！");
//									orderamount = famount;
//								}
//							}else{
////								throw new DJException("安全库存没有此客户对应的产品记录！");
//								orderamount = famount;
//							}
//							
//							if(ftype==1){
//								Saleorder soinfo = new Saleorder();
//								String orderentryid = saleOrderDao.GetEASid("BF9A2C0D");
//								String ordernumber = getFnumber(1,conn,stmt); 
//								soinfo.setFid(orderentryid);
//								soinfo.setForderid(orderid);
//								soinfo.setFcreatorid(userid);
//								soinfo.setFcreatetime(new Date());
//								soinfo.setFnumber(ordernumber);
//								soinfo.setFproductdefid(easproductid);
//								soinfo.setFentryProductType(0);
//								
//								pinfo.setFordernumber(ordernumber+"-1");
//								pinfo.setForderentryid(orderentryid);
//							
//								soinfo.setFlastupdatetime(new Date());
//								soinfo.setFlastupdateuserid(userid);
//								soinfo.setFamount(orderamount);
//								soinfo.setFcustomerid(EAScustomerid);
//								soinfo.setFcustproduct(fcusproductid);
//								soinfo.setFarrivetime(farrivetime);
//								soinfo.setFbizdate(new Date());
//								soinfo.setFaudited(0);
//								soinfo.setFauditorid(null);
//								soinfo.setFaudittime(null);
//								soinfo.setFamountrate(1);
//								
//								soinfo.setFordertype(fnewtype);
//								soinfo.setFseq(1);
//								soinfo.setFimportEas(0);
//								
//								HashMap<String, Object> params = saleOrderDao.ExecSave(soinfo);
//								if (params.get("success") == Boolean.FALSE) {
//									throw new DJException("生成订单失败！");
//								}
//								
//							}else{
//								//套装
//								// 一次性获取所有级次的：套装+子产品
//								// 再按顺序加载即可
//
//								List list = getAllProductSuit(easproductid);
//								HashMap subInfo = null;
//								String fordernumber = getFnumber(1,conn,stmt);
//
//								for (int i = 0; i < list.size(); i++)
//								{
//									subInfo = (HashMap) list.get(i);
//									
//									Saleorder soinfo = new Saleorder();
//									if(i==0){
//										soinfo.setFamount(orderamount);
//										soinfo.setFproductdefid(easproductid);
//										soinfo.setFsuitProductId(easproductid);
//										pinfo.setFordernumber(fordernumber+"-1");
//										pinfo.setForderentryid(subInfo.get("orderEntryID").toString());
//										
//									}else{
//										
//										soinfo.setFparentOrderEntryId(subInfo.get("ParentOrderEntryId").toString());
//										soinfo.setFamount(orderamount * new Integer(subInfo.get("amountRate").toString()));
//										soinfo.setFproductdefid(subInfo.get("fid").toString());
//										
//									}
//									
//									soinfo.setFordertype(new Integer(subInfo.get("fnewtype").toString()));
//									
//									soinfo.setFentryProductType(new Integer(subInfo.get("entryProductType").toString()));
//									soinfo.setFid(subInfo.get("orderEntryID").toString());
//									soinfo.setFseq((i+1));
//									soinfo.setFimportEas(0);
//									soinfo.setFamountrate(new Integer(subInfo.get("amountRate").toString()));
//									soinfo.setForderid(orderid);
//									soinfo.setFcreatorid(userid);
//									soinfo.setFcreatetime(new Date());
//									soinfo.setFlastupdatetime(new Date());
//									soinfo.setFlastupdateuserid(userid);
//									soinfo.setFnumber(fordernumber);
//									soinfo.setFcustomerid(EAScustomerid);
//									soinfo.setFcustproduct(fcusproductid);
//									soinfo.setFarrivetime(farrivetime);
//									soinfo.setFbizdate(new Date());
//									soinfo.setFaudited(0);
//									soinfo.setFauditorid(null);
//									soinfo.setFaudittime(null);
//									
//									HashMap<String, Object> params = saleOrderDao.ExecSave(soinfo);
//									if (params.get("success") == Boolean.FALSE) {
//										throw new DJException("生成订单失败！");
//									}
//								}
//								
//							}
//							pinfo.setFsaleorderid(orderid);
//						}else{
//							//要货数量小于EAS系统库存数量直接生成EAS系统的配送单;
//							stmt = conn.prepareStatement("select s.fid orderid,s.fnumber,en.fid entryid,en.fseq from t_ord_saleorder s left join t_ord_saleorderentry en  on en.fparentid=s.fid where s.fisclosed=0 and en.fisclosed=0 and en.finvalided=0 and s.fcustomerid='"+EAScustomerid+"' and en.fproductid='"+easproductid+"' and exists (select fid from t_inv_storebalance b where b.fforderentryid=en.fid and b.ffbalanceqty>0 ) order by s.fcreatetime desc ");
//							ResultSet easOrder = stmt.executeQuery();
//							String easorderid = "";
//							String easentryid = "";
//							if (easOrder.next())
//							{
//								easorderid = easOrder.getString("orderid");
//								easentryid = easOrder.getString("entryid");
//							}
//							
//							stmt = conn.prepareStatement("select fid from t_bd_address a where a.fdetailaddress_l2 = '"+ faddress + "' ");
//							ResultSet address = stmt.executeQuery();
//							String addressid = "";
//							if(address.next()){
//								addressid = address.getString("fid");
//							}
//							pinfo.setFsaleorderid(easorderid);
//							pinfo.setFordernumber(easOrder.getString("fnumber")+"-"+easOrder.getString("fseq"));
//							pinfo.setForderentryid(easentryid);
//							stmt = conn.prepareStatement("insert into t_ord_delivers (fid,fnumber,fcustomerid,ftype,fsaleorderid,forderentryid,CFPSADRESS,Faddressid,flinkman,fphone,famount,frealamount,farrivedate,fcreatorid,fcreatetime,flastupdateuserid,flastupdatetime,fcontrolunitid,ffromorder,fmatched,feffect,fissued,ftranslated,freceived,fifcancel,cfsaleorgunitid) values ('"+fid+"','"+deliverNum+"','"+EAScustomerid+"',1,'"+easorderid+"','"+easentryid+"','"+faddress+"','"+addressid+"','"+flinkman+"','"+flinkphone+"','"+famount+"','"+famount+"',to_date('"+df.format(farrivetime)+"','yyyy-mm-dd'),'00000000-0000-0000-0000-00000000000013B7DE7F',sysdate,'00000000-0000-0000-0000-00000000000013B7DE7F',sysdate,'00000000-0000-0000-0000-000000000000CCE7AED4',1,0,1,0,0,0,0,'O9P5KwEPEADgAAdDwKgCZ8znrtQ=' )");
//							stmt.execute();
////							conn.commit();
//						}
//					}else{
//						throw new DJException("客户产品没有关联产品！");
//					}
////				}
////				reponse.getWriter().write(JsonUtil.result(true,"","",result));
//			} catch (Exception e) {
//				conn.rollback();
//				DeliversDao.GetEASConn().CloseConn(request);
//				throw new DJException(e.getMessage());
//			}
//			
//			HashMap<String, Object> params = DeliversDao.ExecSave(pinfo);
//			if (params.get("success") == Boolean.TRUE) {
//				result = JsonUtil.result(true,"下单成功!", "", "");
//				conn.commit();
//			} else {
//				result = JsonUtil.result(false,"下单失败!", "", "");
//			}
//		} catch (Exception e) {
//			result = JsonUtil.result(false,e.getMessage(), "", "");
//		}
//		reponse.setCharacterEncoding("utf-8");
//		reponse.getWriter().write(result);
//		running = false;
//		return null;
//	}
	
	/**
	 * 生成要货申请;
	 */
	@RequestMapping(value = "/generateDelivers")
	public String generateDelivers(HttpServletRequest request,HttpServletResponse reponse) throws IOException, SQLException, DJException, ParseException {
		reponse.setCharacterEncoding("utf-8");
		String result = "";
		
		try{
			String fids = request.getParameter("fids").replaceAll(",","','");
			if(fids.length()<=0)
			{
				throw new DJException("请选中记录");
			}
			DataUtil.verifyNotNullAndDataAndPermissions(null,
					new String[][]{{"fids", "t_ord_cusdelivers", "fcustomerid",null}}, request,
					DeliversDao);
			fids="('"+fids+"')";
			String userid = ((Useronline)request.getSession().getAttribute("Useronline")).getFuserid();
			String fnumber = "";
			String fcusproductid = "";
			String flinkman = "";
			String flinkphone = "";
			int famount = 0;
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			String farrivetime = "";
			String faddress = "";
			String faddressid = "";
			String fcustomerid = "";
			String fdescription = "";
			String fcusfid="";
			String fwerkname="";
			String fordernumber = "";
			
			ArrayList<String> generateSQL = new ArrayList<String>();
			
			String sql = "select d.fsupplierid,d.fmaktx,d.ftype,d.fcreatorid,d.fwerkname,d.fid cusdeliversid,d.fcusproduct,d.fid,d.fnumber,d.fcustomerid,date_format(d.farrivetime,'%Y-%m-%d %T') farrivetime,a.flinkman,a.fphone as flinkphone,d.famount,a.fdetailaddress faddress,a.fid faddressid,d.fdescription,ifnull(d.fisread,0) fisread,fcusfid from t_ord_cusdelivers d inner join t_bd_address a on a.fid=d.faddress where d.fisread=0 and d.fid in "+fids;
			List<HashMap<String,Object>> resultCol = DeliversDao.QueryBySql(sql);
			String cusdeliversID ="(";
			Mystock mystock = new Mystock();
			for(int i=0;i<resultCol.size();i++){
				HashMap resultinfo = resultCol.get(i);
				famount = 0;
				cusdeliversID += "'"+resultinfo.get("cusdeliversid")+"'";
				if(i<(resultCol.size()-1)){
					cusdeliversID +=  "," ;
				}
				String sqls = "select 1 from t_ord_cusdelivers cu inner join t_bd_custproduct cp on cp.fid=cu.fcusproduct where cp.feffect=0 and cu.fcusproduct ='"+resultinfo.get("fcusproduct")+"'";
				if(DeliversDao.QueryExistsBySql(sqls)){
					throw new DJException("该产品已经禁用不能生成！");
				}
				if(resultinfo.get("fcreatorid")!=null && !resultinfo.get("fcreatorid").equals("")){
					userid = resultinfo.get("fcreatorid").toString();
				}
				
				DateFormat f = new SimpleDateFormat("yyyy-MM-dd HH");
				if(resultinfo.get("farrivetime")!=null){
					farrivetime = resultinfo.get("farrivetime").toString();
				}
				
				if(resultinfo.get("flinkman")!=null){
					flinkman = resultinfo.get("flinkman").toString();
				}
				if(resultinfo.get("fwerkname")!=null){
					fwerkname = resultinfo.get("fwerkname").toString();
				}
				if(resultinfo.get("fcusfid")!=null){
					fcusfid = resultinfo.get("fcusfid").toString();
				}
				if(resultinfo.get("flinkphone")!=null){
					flinkphone = resultinfo.get("flinkphone").toString();
				}
				
//				if(resultinfo.get("fnumber")!=null){
					fnumber = ServerContext.getNumberHelper().getNumber("t_ord_deliverapply", "Y", 4, false);
//				}
				if(resultinfo.get("fcusproduct")!=null){
					fcusproductid = resultinfo.get("fcusproduct").toString();
				}else{
					throw new DJException("客户产品未找到，请检查");
				}
				
				faddress = resultinfo.get("faddress").toString();
				faddressid = resultinfo.get("faddressid").toString();
				fcustomerid = resultinfo.get("fcustomerid").toString();
				
				if(resultinfo.get("famount")!=null){
					famount = new Integer(resultinfo.get("famount").toString());
				}
				
				if(resultinfo.get("fdescription")!=null){
					fdescription = resultinfo.get("fdescription").toString();
				}
				if(resultinfo.get("fnumber")!=null){
					fordernumber = resultinfo.get("fnumber").toString();
				}
				String supplierid = resultinfo.get("fsupplierid")==null?"":(String)resultinfo.get("fsupplierid");
				if((Integer)resultinfo.get("ftype")==0){//生成要货申请
					sql = "INSERT INTO t_ord_deliverapply (fsupplierid,fcusfid,fiscreate,fid,fcreatorid,fcreatetime,fupdateuserid,fupdatetime,fnumber,fcustomerid,fcusproductid,farrivetime,flinkman,flinkphone,famount,faddress,fdescription,fordered,fordermanid,fordertime,fsaleorderid,fordernumber,forderentryid,fimportEAS,fimportEASuserid,fimportEAStime,fouted,foutorid,fouttime,faddressid,fEASdeliverid,fistoPlan,fplanTime,fplanNumber,fplanid,fwerkname,falloted,fordesource) " +
							"VALUES ('"+supplierid+"','"+fcusfid+"',0,'" +
							DeliversDao.CreateUUid() +
							"','" + userid +
							"',CURRENT_TIMESTAMP,'" +
							userid +
							"',CURRENT_TIMESTAMP,'" +
							fnumber +
							"','" +
							fcustomerid +
							"','" +
							fcusproductid +
							"','" +
							farrivetime +
							"','" +
							flinkman +
							"','" +
							flinkphone +
							"','" +
							famount +
							"','" +
							faddress +
							"','" +
							fdescription +
							"',0,null,null,null,'"+ fordernumber +"',null,0,null,null,0,null,null,'" +
							faddressid +
							"',null,0,null,null,null,'"+fwerkname+"',0,'desktops')";
					
					generateSQL.add(sql);
					
				}else if((Integer)resultinfo.get("ftype")==1){//生成我的备货
					fnumber = ServerContext.getNumberHelper().getNumber("mystock", "B", 4, false);
					mystock.setFid(DeliversDao.CreateUUid());
					mystock.setFcreateid(userid);
					mystock.setFcreatetime(new Date());
					mystock.setFcustomerid(fcustomerid);
					mystock.setFcustproductid(fcusproductid);
					mystock.setFremark(fdescription);
					mystock.setFfinishtime(f.parse(farrivetime));
					mystock.setFconsumetime(f.parse(farrivetime));
					mystock.setFisconsumed(0);
					mystock.setFnumber(fnumber);
					mystock.setFplanamount(famount);
					mystock.setFpcmordernumber(fordernumber);
					mystock.setFstate(0);
					mystock.setFsupplierid((String)resultinfo.get("fsupplierid"));
					DeliversDao.saveOrUpdate(mystock);
				}
				//点击“生成”时，系统自动记录匹配产品到客户产品。fmaktx
				sql = "update t_bd_custproduct set fproductmatching ='"+resultinfo.get("fmaktx")+"' where fid ='"+fcusproductid+"'";
				DeliversDao.ExecBySql(sql);
			}
			
			if(resultCol.size()==0){
				throw new DJException("地址未找到，请检查！");
			}
			
			cusdeliversID = cusdeliversID + ")" ;
			
			DeliversDao.ExecGenerateSQL(generateSQL,cusdeliversID);
			
		}catch(Exception e){
			reponse.getWriter().write(JsonUtil.result(false,"生成要货申请失败！" + e.getMessage(), "", ""));
			return null;
		}
		
		reponse.getWriter().write(JsonUtil.result(true, "生成要货申请成功！", "", ""));
		return null;

	}
	
	/**
	 * delivers导入EAS;
	 */
//	@RequestMapping(value = "/deliversImportEAS")
//	public String deliversImportEAS(HttpServletRequest request,HttpServletResponse reponse) throws IOException, SQLException, DJException, ParseException {
//		reponse.setCharacterEncoding("utf-8");
//		String result = "";
//		String fid = request.getParameter("fid");
//		String fnumber = request.getParameter("fnumber");
//		String fcusproductid = request.getParameter("fcusproductid");
//		String flinkman = request.getParameter("flinkman");
//		String flinkphone = request.getParameter("flinkphone");
//		String famount = request.getParameter("famount");
//		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
//		Date farrivetime = df.parse(request.getParameter("farrivetime"));
//		String faddress = request.getParameter("faddress");
//		String fsaleorderid = request.getParameter("fsaleorderid");
//		String fordernumber = request.getParameter("fordernumber");
//		String forderentryid = request.getParameter("forderentryid");
//		String fcustomerid = request.getParameter("fcustomerid");
//		ServerContext svct = new ServerContext();
//		String EASdeliversID = svct.GetEASid("57C24810");
//		//判断EAS系统是否有该配送单记录的订单ID、分录ID,没有则提示需要先导入订单才能导入配送单;
//		String sql = "select s.fid from t_ord_saleorder s inner join t_ord_saleorderentry en on en.fparentid = s.fid where s.fid =:fsaleorderid )";
//		params p=new params();
//		p.put("fsaleorderid",fsaleorderid);
//		List<HashMap<String,Object>> orderlist= DeliversDao.QueryBySql(sql, p);
//		if (orderlist.size()<0) {
//			throw new DJException("VMI协同平台系统订单数据未导入EAS，请先导入订单！");
//		}
//		
//		Connection conn=ServerContext.getOracleHelper().GetConn(request);
//		PreparedStatement stmt = null;
//		try {
//			stmt = conn.prepareStatement("select fid from t_bd_address a where a.fdetailaddress_l2 = '"+ faddress + "' ");
//			ResultSet address = stmt.executeQuery();
//			String addressid = "";
//			if(address.next()){
//				addressid = address.getString("fid");
//			}
//			stmt = conn.prepareStatement("insert into t_ord_delivers (fid,fnumber,fcustomerid,ftype,fsaleorderid,forderentryid,CFPSADRESS,Faddressid,flinkman,fphone,famount,frealamount,farrivedate,fcreatorid,fcreatetime,flastupdateuserid,flastupdatetime,fcontrolunitid,ffromorder,fmatched,feffect,fissued,ftranslated,freceived,fifcancel,cfsaleorgunitid) values ('"+EASdeliversID+"','"+fnumber+"','"+fcustomerid+"',1,'"+fsaleorderid+"','"+forderentryid+"','"+faddress+"','"+addressid+"','"+flinkman+"','"+flinkphone+"','"+famount+"','"+famount+"',to_date('"+df.format(farrivetime)+"','yyyy-mm-dd'),'00000000-0000-0000-0000-00000000000013B7DE7F',sysdate,'00000000-0000-0000-0000-00000000000013B7DE7F',sysdate,'00000000-0000-0000-0000-000000000000CCE7AED4',1,0,1,0,0,0,0,'O9P5KwEPEADgAAdDwKgCZ8znrtQ=' )");
//			stmt.execute();
//		} catch (Exception e) {
//			conn.rollback();
//			ServerContext.getOracleHelper().CloseConn(request);
//			reponse.getWriter().write(JsonUtil.result(false,"导入数据失败！" + e.getMessage(), "", ""));
//			return null;
//		}
//		
//		try{
//			String deliverssql = "SET SQL_SAFE_UPDATES=0 ";
//			DeliversDao.ExecBySql(deliverssql);
//			deliverssql = "update t_ord_delivers set fimportEas=1,fimportEasuserid='"
//					+ ((Useronline) request.getSession().getAttribute("Useronline")).getFuserid()
//					+ "',fimportEastime=now(),feasdeliverid='"+EASdeliversID+"' where fid = '" + fid +"'";
//			DeliversDao.ExecBySql(deliverssql);
//		}catch(Exception e){
//			reponse.getWriter().write(JsonUtil.result(false, "导入数据失败,更新要货申请导入信息错误！", "", ""));
//			return null;
//		}
//		
//		reponse.getWriter().write(JsonUtil.result(true, "要货申请导入EAS成功", "", ""));
//		conn.commit();
//		return null;
//
//	}
//	

	/**
	 * delivers发货;
	 */
//	@RequestMapping(value = "/deliversSend")
//	public String deliversSend(HttpServletRequest request,
//			HttpServletResponse reponse) throws IOException, SQLException,
//			DJException, ParseException {
//		String fdeliverids = request.getParameter("fdeliverid");
//		reponse.setCharacterEncoding("utf-8");
//		try {
//			String deliverssql="select fid from t_ord_delivers where fordered=0 and fid in "+fdeliverids;
//			List<HashMap<String, Object>> delivers=DeliversDao.QueryBySql(deliverssql);
//			if(delivers.size()>0)
//			{
//				reponse.getWriter().write(JsonUtil.result(false, "要货信息未下单，不能发货！", "", ""));
//				return null;
//			}
//			deliverssql="select fid from t_ord_delivers where fouted=1 and fid in "+fdeliverids;
//			delivers=DeliversDao.QueryBySql(deliverssql);
//			if(delivers.size()>0)
//			{
//				reponse.getWriter().write(JsonUtil.result(false, "已经发货，不能再发货！", "", ""));
//				return null;
//			}
//		    deliverssql = "update t_ord_delivers set fouted=1,foutorid='"
//					+ ((Useronline) request.getSession().getAttribute(
//							"Useronline")).getFuserid()
//					+ "',fouttime=now() where fid in "+fdeliverids;
//			DeliversDao.ExecBySql(deliverssql);
//			reponse.getWriter().write(JsonUtil.result(true, "发货成功", "", ""));
//		} catch (Exception e) {
//			reponse.getWriter().write(JsonUtil.result(false, "发货失败！", "", ""));
//		}
//		return null;
//
//	}
//	
	
	/**
	 * delivers出库;
	 */
//	@RequestMapping(value = "/deliversOut")
//	public String deliversOut(HttpServletRequest request,HttpServletResponse reponse) throws IOException, SQLException, DJException, ParseException {
//		reponse.setCharacterEncoding("utf-8");
//		String result = "";
//		String FWarehouseID = request.getParameter("FWarehouseID");
//		String FWarehouseSiteID = request.getParameter("FWarehouseSiteID");
//		String forderid = request.getParameter("forderid");
//		String forderEntryid = request.getParameter("forderEntryid");
//		String fdeliverid = request.getParameter("fdeliverid");
//		String fproductid = "";
//		int famount = new Integer(request.getParameter("famount"));
//		String fordertype = "";
//		try{
//			Delivers delivers = DeliversDao.Query(fdeliverid);
//			if(delivers.getFordered()==0){
//				throw new DJException("未下单不能出库！");
//			}
//			if(delivers.getFouted()==1){
//				throw new DJException("已出库不能重复出库！");
//			}
//			
//			String oql = "select fproductdefid,famount,fordertype from t_ord_saleorder s where fid = '"+forderEntryid+"' ";
//			List<HashMap<String,Object>> orderlist= DeliversDao.QueryBySql(oql);
//			if(orderlist.size()>0){
//				HashMap orderinfo = orderlist.get(0);
//				fproductid = orderinfo.get("fproductdefid").toString();
//				fordertype = orderinfo.get("fordertype").toString();
//			}
//			
//			if(fordertype.equals("2") || fordertype.equals("4")){
//				//套装订单;
//				
//			}else{
//				//普通订单;
//				//VMI系统库存必须大于等于要货申请库存数量;
//				String sql = "SELECT ifnull(sum(w.fbalanceqty),0) fbalanceqty FROM test.t_inv_storebalance w where w.FProductID='"+fproductid+"' and w.fsaleorderid='"+forderid+"' and w.forderentryid='"+forderEntryid+"' and w.fwarehouseId='"+FWarehouseID+"' and w.fwarehouseSiteId='"+FWarehouseSiteID+"' ";
//				List<HashMap<String,Object>> balancelist= DeliversDao.QueryBySql(sql);
//				if (balancelist.size()<0) {
//					throw new DJException("没有库存不能出库！");
//				}else{
//					HashMap qtyinfo = balancelist.get(0);
//					if(qtyinfo.get("fbalanceqty").toString().equals("0")){
//						throw new DJException("没有库存不能出库！");
//					}
//					sql = "SELECT fid FROM t_inv_storebalance w where w.FProductID='"+fproductid+"' and w.fsaleorderid='"+forderid+"' and w.forderentryid='"+forderEntryid+"' and w.fwarehouseId='"+FWarehouseID+"' ";
//					List<HashMap<String,Object>> sblist= DeliversDao.QueryBySql(sql);
//					outbalance(sblist,famount);}
//			}
//		
//			String deliverssql = "SET SQL_SAFE_UPDATES=0 ";
//			DeliversDao.ExecBySql(deliverssql);
//			deliverssql = "update t_ord_delivers set fouted=1,foutorid='"
//					+ ((Useronline) request.getSession().getAttribute("Useronline")).getFuserid()
//					+ "',fouttime=now() where fid = '" + fdeliverid +"'";
//			DeliversDao.ExecBySql(deliverssql);
//		}catch(Exception e){
//			reponse.getWriter().write(JsonUtil.result(false, "出库失败："+e.getMessage(), "", ""));
//			return null;
//		}
//		reponse.getWriter().write(JsonUtil.result(true, "要货申请出库成功", "", ""));
//		return null;
//	}
	
	private void outbalance(List<HashMap<String, Object>> sblist , int famount) {
		// TODO Auto-generated method stub
		int bigRest = famount;
		int bigThisTotalQty = 0;
		for (int i=0;i<sblist.size();i++)
		{
			HashMap sbinfo = sblist.get(i);
			String sbId = sbinfo.get("fid").toString();
			Storebalance sbInfo = storebalanceDao.Query(sbId);

			int bigEachLocationQty = sbInfo.getFbalanceqty();
			if (bigEachLocationQty <= 0)
			{
				continue;//库存余额为零，则不从该库位上出库。
			}
			bigRest = bigRest - bigEachLocationQty;
			if ( bigRest < 0 )// >=0。此时该库位已经可以满足出库要求。
			{
				bigThisTotalQty = bigThisTotalQty + famount;
				sbInfo.setFoutqty(sbInfo.getFoutqty()+(bigEachLocationQty + bigRest));// 出库增加
				sbInfo.setFbalanceqty(bigRest);
				//isb.update(new ObjectUuidPK(sbId), sbInfo);
				storebalanceDao.ExecSave(sbInfo);// 更新库存余额表
				//idao.updateBatch(new ObjectUuidPK(sbId), sbInfo);// 更新库存余额表
//				addQtyOfLocation(map,parentKey,locationId,bigIssueQty);
				break;
			}
			else
			// <0
			{
				bigThisTotalQty = bigThisTotalQty + bigEachLocationQty;
//				sbInfo.setActionType(StoreActionType.SA_OUT);
				sbInfo.setFoutqty(sbInfo.getFoutqty() + bigEachLocationQty);// 出库增加
				sbInfo.setFbalanceqty(0);// 该库位已经没有库存，要从下一个库位上继续出库。
				//isb.update(new ObjectUuidPK(sbId), sbInfo);
				storebalanceDao.ExecSave(sbInfo);// 更新库存余额表
				//idao.updateBatch(new ObjectUuidPK(sbId), sbInfo);
//				addQtyOfLocation(map,parentKey,locationId,bigEachLocationQty);
			}
		}
	}
	
	/*
	 * 根据VMI平台库存下单
	 */
//	@RequestMapping(value = "/VMIorder")
//	public String VMIorder(HttpServletRequest request,
//			HttpServletResponse reponse) throws Exception {
//		if(running){
//			return null;
//		}
//		running = true;
//		String result = "";
//		Delivers pinfo = null;
//		try {
//			String userid = ((Useronline) request.getSession().getAttribute(
//					"Useronline")).getFuserid();
//			String fid = request.getParameter("fid");
//			if (fid != null && !"".equals(fid)) {
//				pinfo = DeliversDao.Query(fid);
//				if(pinfo.getFordered()==1){
//					throw new DJException("已下单不能再次下单!");
//				}else{
//					pinfo.setFid(fid);
//					pinfo.setFordermanid(userid);
//					pinfo.setFordered(1);
//					pinfo.setFordertime(new Date());
//				}
//			} else {
//				throw new DJException("未保存不能下单!");
//			}
//			
////			Class.forName("oracle.jdbc.driver.OracleDriver");
////			Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@192.168.0.198:1521:orcl", "dbo_update", "dbo_update");
////			Connection conn=DeliversDao.GetEASConn().GetConn(request);
////			String orderEntryid = saleOrderDao.GetEASid("BF9A2C0D");
////			String orderid = saleOrderDao.GetEASid("A8114365");
//			String orderid = saleOrderDao.CreateUUid();
//			try {
//				String fnumber = request.getParameter("fnumber");
//				String fcusproductid = request.getParameter("fcusproductid");
//				String flinkman = request.getParameter("flinkman");
//				String flinkphone = request.getParameter("flinkphone");
//				String vmicustomerid = request.getParameter("fcustomerid");
//				int famount = 0;
//				if(request.getParameter("famount")!=null){
//					famount = Integer.valueOf(request.getParameter("famount"));
//				}
////				DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//				DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
//				Date farrivetime = df.parse(request.getParameter("farrivetime"));
//				String faddress = request.getParameter("faddress");
//				
//				String sql = "select fid from t_pdt_productdef where fid in (select fproductdefid from t_sys_custrelationproduct where fcustid =:fcustid )";
//				params p=new params();
//				p.put("fcustid",fcusproductid);
//				List<HashMap<String,Object>> data= DeliversDao.QueryBySql(sql, p);
////				if (!data.isEmpty()) {
////					JSONArray j = JSONArray.fromObject(data);
//					
//					PreparedStatement stmt = null;
//					
//					BigDecimal stock = new BigDecimal(0);
//					
////					for (int i = 0; i < j.size(); i++) {
////					if (j.size()>0) {//客户产品只有一个对应的产品ID;
//					if (data.size()>0) {//客户产品只有一个对应的产品ID;
////						JSONObject o = j.getJSONObject(0);
//						HashMap o = data.get(0);
//						String fproductid = "";
////						String vmiproductid = ""; 
//						if(o.get("fid")!=null){
//							fproductid = o.get("fid").toString();
////							vmiproductid = o.get("fid").toString();
//						}
////						int ftype ;
//						int fnewtype = 3;
//						String isassemble = "0";
//						String EAScustomerid = "";
//						try{
//							ResultSet rs = null;
////							stmt = conn.prepareStatement("select d.fid,d.fassemble,d.ftype,d.fnewtype,d.fcustomerid,d.feffect,c.fstate from t_pdt_productdef d left join T_BAL_CooperateLogger c on c.fcustomerid=d.fcustomerid where d.fid='"+ fproductid + "' and  c.FCreateTime in (select max(c.FCreateTime) from T_BAL_CooperateLogger c where c.fcustomerid = d.fcustomerid) ");
////							ResultSet rst = stmt.executeQuery();
//							sql = "select d.fid,d.fassemble,d.fnewtype,d.fcustomerid,d.feffect from t_pdt_productdef d where fid = :fid ";
//							p=new params();
//							p.put("fid",fproductid);
//							List<HashMap<String,Object>> pdtlist= DeliversDao.QueryBySql(sql, p);
//							if(pdtlist.size()>0){
//								HashMap pdtinfo = pdtlist.get(0);
//								if(pdtinfo.get("feffect").equals("0")){
//									throw new DJException("产品被禁用，无法自动下单！");
//								}
//								if(pdtinfo.get("fassemble")!=null){
//									isassemble = pdtinfo.get("fassemble").toString();
//								}
////								ftype = new Integer(rst.get("ftype").toString());
//								if(pdtinfo.get("fnewtype")!=null && !pdtinfo.get("fnewtype").toString().equals("")){
//									fnewtype = new Integer(pdtinfo.get("fnewtype").toString());
//								}
//								
//								EAScustomerid = pdtinfo.get("fcustomerid").toString();
//								
//								//库存数量
//								if(isassemble.equals("1") || (fnewtype!=2 && fnewtype!=4)){
//									sql = "select ifnull(sum(b.fbalanceqty),0) balanceqty  from t_inv_storebalance b where b.fcustomerid= :fcustomerid and b.fproductid= :fproductid ";
//									p=new params();
//									p.put("fcustomerid",vmicustomerid);
//									p.put("fproductid",fproductid);
//									List<HashMap<String,Object>> sblist= DeliversDao.QueryBySql(sql, p);
//									if(sblist.size()>0){
//										HashMap sbinfo = sblist.get(0);
//										stock=new BigDecimal(sbinfo.get("balanceqty").toString());
//									}
//										
//									//在生产数量
//									sql = "select ifnull(sum(case when s.famount<ifnull(s.fstockinqty,0) then 0 else s.famount-ifnull(s.fstockinqty,0) end),0) unAmount from t_ord_saleorder s where faffirmed=1 and s.fcustomerid = :fcustomerid and s.fproductdefid = :fproductid ";
//									p=new params();
//									p.put("fcustomerid",vmicustomerid);
//									p.put("fproductid",fproductid);
//									List<HashMap<String,Object>> unsblist= DeliversDao.QueryBySql(sql, p);
//									if(unsblist.size()>0){
//										HashMap unsbinfo = unsblist.get(0);
//										stock=stock.add(new BigDecimal(unsbinfo.get("unAmount").toString()));
//									}
//									
//								}else{
//									//套装
//									String productIDs = "";
//									sql = "select d.fid,d.fcombination from T_PDT_ProductDefProducts pd left join t_pdt_productdef d on d.fid=pd.fproductid where fparentid = :fproductid ";
//									p=new params();
//									p.put("fproductid",fproductid);
//									List<HashMap<String,Object>> suitpdtlist= DeliversDao.QueryBySql(sql, p);
//									for(int i=0;i<suitpdtlist.size();i++){
//										HashMap suitpdtinfo = suitpdtlist.get(i);
//										if(suitpdtinfo.get("fcombination")!=null && suitpdtinfo.get("fcombination").toString().equals("0")){
//											if(productIDs.length()>0){
//												productIDs = productIDs + "','" + rs.getString("fid");
//											}else{
//												productIDs = "('" + rs.getString("fid");
//											}
//										}
//									}
//									if(productIDs.length()==0){
//										productIDs = "('" ;
//									}
//									productIDs = productIDs + "')";
//
//									
//									//套装库存总量
//									StringBuffer oql = new StringBuffer("select ifnull(min(qty),0) Qty from (select t.fproductid,ifnull(sum(t1.fbalanceqty/t.famount),0)+sum((case when t2.famount<ifnull(t2.fstockinqty,0) then 0 else t2.famount-ifnull(t2.fstockinqty,0) end)/t.famount) qty from T_PDT_ProductDefProducts t ");
//									oql.append("left join t_inv_storebalance t1 on t.fproductid=t1.fproductid left join t_ord_saleorder t2 on t2.fproductdefid=t.fproductid where t2.faffirmed=1 and t.fparentid = '").append(fproductid).append("' and t.fproductid in ").append(productIDs).append(" group by t.fproductid) a");
//									List<HashMap<String,Object>> suitsblist= DeliversDao.QueryBySql(oql.toString());
//									if(suitsblist.size()>0){
//										HashMap suitsbinfo = suitpdtlist.get(0);
//										stock=new BigDecimal(suitsbinfo.get("Qty").toString());
//									}
//								}
//								
//								//库存需要减去已导入EAS系统且没出库的要货数量;
//								sql = "select ifnull(sum(d.famount),0) famount from t_ord_delivers d where d.fimportEAS = 1 and fouted = 0 and d.fcustomerid =:fcustomerid and d.fcusproductid=:fcusproductid ";
//								p=new params();
//								p.put("fcustomerid",vmicustomerid);
//								p.put("fcusproductid",fcusproductid);
//								List<HashMap<String,Object>> dlvlist= DeliversDao.QueryBySql(sql, p);
//								if(dlvlist.size()>0){
//									HashMap dlvinfo = dlvlist.get(0);
//									stock=stock.subtract(new BigDecimal(dlvinfo.get("famount").toString()));
//								}
//							}else{
//								throw new DJException("EAS系统没有对应的产品下单！");
//							}
//						}catch(Exception e){
//							throw new DJException(e.getMessage());
//						}
//						
//						int balanceqty = stock.divide(new BigDecimal(1), 0, BigDecimal.ROUND_DOWN).intValue();
////						String deliverNum = getFnumber(2,conn,stmt);
//						if(famount>balanceqty){//要货数量大于EAS系统库存数量按VMI安全库存下单数量生成VMI订单;
//							int orderamount = 0;
//							sql = "select forderamount from t_pdt_vmiproductparam where fcustomerid =:fcustomerid and fproductid =:fproductid ";
////							sql = "select forderamount from t_pdt_vmiproductparam where fcustomerid = '"+vmicustomerid+"' and fproductid = '"+vmiproductid+"' ";
//							p=new params();
//							p.put("fcustomerid",vmicustomerid);
//							p.put("fproductid",fproductid);
//							List<HashMap<String,Object>> vmipdtparamlist = VmipdtParamDao.QueryBySql(sql, p);
////							List<HashMap<String,Object>> vmipdtparamlist = VmipdtParamDao.QueryBySql(sql);
////							if (!vmipdtparamlist.isEmpty()) {
////								JSONArray js = JSONArray.fromObject(vmipdtparamlist);
////								if (js.size()>0) {
////									JSONObject vmipdtparam = js.getJSONObject(0);
////									if(vmipdtparam.get("forderamount")!=null){
////										orderamount = vmipdtparam.get("forderamount").toString();
////									}else{
////										throw new DJException("安全库存没有此客户对应的产品记录！");
////									}
////								}
//							if(vmipdtparamlist.size()>0){
//								HashMap vmipdtparam = vmipdtparamlist.get(0);
//								if(vmipdtparam.get("forderamount")!=null){
//									orderamount = new Integer(vmipdtparam.get("forderamount").toString());
//								}
//								else{
////									throw new DJException("安全库存没有此客户对应的产品记录！");
//									orderamount = famount;
//								}
//							}else{
////								throw new DJException("安全库存没有此客户对应的产品记录！");
//								orderamount = famount;
//							}
//							String fordernumber = saleOrderDao.getFnumber("t_ord_saleorder", "Z",4);
//							if(fnewtype!=2 && fnewtype!=4){
//								Saleorder soinfo = new Saleorder();
//								String orderEntryid = saleOrderDao.CreateUUid();
//								soinfo.setFid(orderEntryid);
//								soinfo.setForderid(orderid);
//								soinfo.setFcreatorid(userid);
//								soinfo.setFcreatetime(new Date());
//								soinfo.setFnumber(fordernumber);
//								soinfo.setFproductdefid(fproductid);
//								soinfo.setFentryProductType(0);
//								
//								pinfo.setFordernumber(fordernumber+"-1");
//								pinfo.setForderentryid(orderEntryid);
//							
//								soinfo.setFlastupdatetime(new Date());
//								soinfo.setFlastupdateuserid(userid);
//								soinfo.setFamount(orderamount);
//								soinfo.setFcustomerid(EAScustomerid);
//								soinfo.setFcustproduct(fcusproductid);
//								soinfo.setFarrivetime(farrivetime);
//								soinfo.setFbizdate(new Date());
//								soinfo.setFaudited(0);
//								soinfo.setFauditorid(null);
//								soinfo.setFaudittime(null);
//								soinfo.setFamountrate(1);
//								
//								soinfo.setFordertype(fnewtype);
//								soinfo.setFseq(1);
//								soinfo.setFimportEas(0);
//								
//								HashMap<String, Object> params = saleOrderDao.ExecSave(soinfo);
//								if (params.get("success") == Boolean.FALSE) {
//									throw new DJException("生成订单失败！");
//								}
//								
//							}else{
//								//套装
//								// 一次性获取所有级次的：套装+子产品
//								// 再按顺序加载即可
//
//								List list = getAllProductSuit(fproductid);
//								HashMap subInfo = null;
//
//								for (int i = 0; i < list.size(); i++)
//								{
//									subInfo = (HashMap) list.get(i);
//									
//									Saleorder soinfo = new Saleorder();
//									if(i==0){
//										soinfo.setFamount(orderamount);
//										soinfo.setFproductdefid(fproductid);
//										soinfo.setFsuitProductId(fproductid);
//										pinfo.setFordernumber(fordernumber+"-1");
//										pinfo.setForderentryid(subInfo.get("orderEntryID").toString());
//										
//									}else{
//										
//										soinfo.setFparentOrderEntryId(subInfo.get("ParentOrderEntryId").toString());
//										soinfo.setFamount(orderamount * new Integer(subInfo.get("amountRate").toString()));
//										soinfo.setFproductdefid(subInfo.get("fid").toString());
//										
//									}
//									
//									soinfo.setFordertype(new Integer(subInfo.get("fnewtype").toString()));
//									
//									soinfo.setFentryProductType(new Integer(subInfo.get("entryProductType").toString()));
//									soinfo.setFid(subInfo.get("orderEntryID").toString());
//									soinfo.setFseq((i+1));
//									soinfo.setFimportEas(0);
//									soinfo.setFamountrate(new Integer(subInfo.get("amountRate").toString()));
//									soinfo.setForderid(orderid);
//									soinfo.setFcreatorid(userid);
//									soinfo.setFcreatetime(new Date());
//									soinfo.setFlastupdatetime(new Date());
//									soinfo.setFlastupdateuserid(userid);
//									soinfo.setFnumber(fordernumber);
//									soinfo.setFcustomerid(EAScustomerid);
//									soinfo.setFcustproduct(fcusproductid);
//									soinfo.setFarrivetime(farrivetime);
//									soinfo.setFbizdate(new Date());
//									soinfo.setFaudited(0);
//									soinfo.setFauditorid(null);
//									soinfo.setFaudittime(null);
//									
//									HashMap<String, Object> params = saleOrderDao.ExecSave(soinfo);
//									if (params.get("success") == Boolean.FALSE) {
//										throw new DJException("生成订单失败！");
//									}
//								}
//								
//							}
//							pinfo.setFsaleorderid(orderid);
//						}else{
//							//要货数量小于VMI系统库存数量从订单中找有库存的订单反写到配送单;
//							sql = "select s.fid orderentryid,s.forderid,s.fnumber from t_ord_saleorder s where s.fstockinqty > 0 and fcustomerid =:fcustomerid and fproductdefid =:fproductid ";
//							p=new params();
//							p.put("fcustomerid",vmicustomerid);
//							p.put("fproductid",fcusproductid);
//							List<HashMap<String,Object>> ordlist= DeliversDao.QueryBySql(sql, p);
//							if(ordlist.size()>0){
//								HashMap ordinfo = ordlist.get(0);
//								String saleorderid = "";
//								String orderentryid = "";
//								String ordernumber = "";
//								saleorderid = ordinfo.get("forderid").toString();
//								orderentryid = ordinfo.get("orderentryid").toString();
//								ordernumber = ordinfo.get("fnumber").toString();
//								sql = "SET SQL_SAFE_UPDATES=0 ";
//								saleOrderDao.ExecBySql(sql);
//								sql = "UPDATE `t_ord_delivers` SET `fsaleorderid`='" + saleorderid +"',`fordernumber`='" + ordernumber +"',`forderentryid`='" + orderentryid +"' WHERE `fid` = '" + fid +"'";
//								DeliversDao.ExecBySql(sql);
//							}
//							
//						}
//					}else{
//						throw new DJException("客户产品没有关联产品！");
//					}
////				}
////				reponse.getWriter().write(JsonUtil.result(true,"","",result));
//			} catch (Exception e) {
////				conn.rollback();
//				//ServerContext.getOracleHelper().CloseConn(request);
//				throw new DJException(e.getMessage());
//			}
//			
//			HashMap<String, Object> params = DeliversDao.ExecSave(pinfo);
//			if (params.get("success") == Boolean.TRUE) {
//				result = JsonUtil.result(true,"下单成功!", "", "");
////				conn.commit();
//			} else {
//				result = JsonUtil.result(false,"下单失败!", "", "");
//			}
//		} catch (Exception e) {
//			result = JsonUtil.result(false,e.getMessage(), "", "");
//		}
//		reponse.setCharacterEncoding("utf-8");
//		reponse.getWriter().write(result);
//		running = false;
//		return null;
//	}
	
	/*
	 * 根据VMI平台库存下单
	 */
	@RequestMapping(value = "/VMIorder")
	public String VMIorder(HttpServletRequest request,
			HttpServletResponse reponse) throws Exception {
		synchronized (this.getClass()){
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd"); 
		String result = "";
		Delivers pinfo = null;
		try {
			String userid = ((Useronline) request.getSession().getAttribute("Useronline")).getFuserid();
			String fid = "";
			String fcustomeridd=request.getParameter("fcustomerid");
			if(fcustomeridd==null||"".equals(fcustomeridd))
			{
			  throw new DJException("错误 ！请选择一条记录进行操作！");
			}
			String sql = "select * from t_ord_delivers where ifnull(fordered,0) = 0 and ifnull(ftype,0) <> 1 and ifnull(falloted,0)=0 and fcustomerid='"+fcustomeridd+"' order by fproductid,fsupplierid,farrivetime ";//相同产品相同制造商，相近配送时间用于合并
			List<HashMap<String,Object>> deliverslist = DeliversDao.QueryBySql(sql);
			HashMap map = new HashMap();
			for(int i=0;i<deliverslist.size();i++){
				HashMap deliversinfo = deliverslist.get(i);
				fid = deliversinfo.get("fid").toString();
				if (fid != null && !"".equals(fid)) {
					//获取一条要货管理记录pinfo
					pinfo = DeliversDao.Query(fid);
				}
				try {
					String fcusproductid = pinfo.getFcusproductid();
					String fcustomerid = pinfo.getFcustomerid();
					int famount = pinfo.getFamount();
					Date farrivetime = pinfo.getFarrivetime();
							String fproductid = "";
							if(pinfo.getFproductid()==null && StringHelper.isEmpty(pinfo.getFproductid()))
							{
								DeliversDao.ExecBySql("update t_ord_delivers set fordernumber='"+"没有对应产品！"+"' where fid = '"+fid+"'");
								continue;
							}
								//获取该要货管理的产品ID
								fproductid =pinfo.getFproductid();
								//有父产品，且为通知的，不需要下单
								sql="select t1.fid from t_pdt_productdefproducts t inner join t_pdt_vmiproductparam t1 on t.fparentid=t1.fproductid  where t.fproductid='"+fproductid+"' and t1.ftype=0 ";
								List<HashMap<String,Object>> parentvmilist= DeliversDao.QueryBySql(sql);
								if(parentvmilist.size()>0)
								{
									DeliversDao.ExecBySql("update t_ord_delivers set fordernumber='"+"存在通知类型的套装，不需要下单！"+"' where fid = '"+fid+"'");
									continue;
								}
							int fnewtype = 0;
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
									if(vmipdtparamlist.size()>0)		//有安全库存则计算产品库存;
									{
										//非安全库存转安全库存时提交上次非安全库存的订单
										if(map.size()>0)
										{
											createMergerOrder(map.get("fproductid").toString(),new Integer(map.get("fnewtype").toString())  ,userid,map.get("fcustomerid").toString(),map.get("fcusproductid")==null?"":map.get("fcusproductid").toString(),new Integer(map.get("famount").toString()),(Date)map.get("farrivetime"),map.get("Deliverid").toString(),(String)map.get("fsupplierid"),(String)map.get("fpcmordernumber"));
											map.clear();
										}
										
									}else	//安全库存 不存在则直接新建订单;
									{
										if((Integer)pdtinfo.get("feffect")==0)
										{
										DeliversDao.ExecBySql("update t_ord_delivers set fordernumber='"+"该产品已禁用！"+"' where fid = '"+fid+"'");
											continue;
										}else if((Integer)pdtinfo.get("fishistory")==1)
										{
											DeliversDao.ExecBySql("update t_ord_delivers set fordernumber='"+"历史版本产品！"+"' where fid = '"+fid+"'");
											continue;
										}else{
										//合并没有安全库存订单;
										int tempAmt = 0 ;
										String Deliverid = "";
										
										if(map.size()==0){
											map.put("famount", famount);
											map.put("fproductid", fproductid);
											Deliverid = "'"+fid+"'";
											map.put("Deliverid", Deliverid);
											map.put("fnewtype", fnewtype);
											map.put("fcustomerid", fcustomerid);
											map.put("fcusproductid", fcusproductid);
											map.put("farrivetime", farrivetime);
											map.put("fsupplierid", pinfo.getFsupplierid());
											map.put("fpcmordernumber", pinfo.getFpcmordernumber());
										}else if(map.get("fproductid").equals(fproductid)&&StringUtils.equals(pinfo.getFsupplierid(), (String) map.get("fsupplierid"))&&df.format(farrivetime).equals(df.format(map.get("farrivetime")))){//对对应同一个产品的要货管理进行合并 //产品，制造商，配送时间进行合并
											tempAmt = new Integer(map.get("famount").toString());
											map.put("famount", tempAmt+famount);
											Deliverid = map.get("Deliverid").toString() + ",'"+fid+"'";
											map.put("Deliverid", Deliverid);
										}else{
											createMergerOrder(map.get("fproductid").toString(),(Integer)map.get("fnewtype"),userid,map.get("fcustomerid").toString(),(map.get("fcusproductid")==null?"":map.get("fcusproductid").toString()),(Integer)map.get("famount"),(Date)map.get("farrivetime"),map.get("Deliverid").toString(),(String)map.get("fsupplierid"),(String)map.get("fpcmordernumber"));
											map.put("famount", famount);
											map.put("fproductid", fproductid);
											Deliverid = "'"+fid+"'";
											map.put("Deliverid", Deliverid);
											map.put("fnewtype", fnewtype);
											map.put("fcustomerid", fcustomerid);
											map.put("fcusproductid", fcusproductid);
											map.put("farrivetime", farrivetime);
											map.put("fsupplierid", pinfo.getFsupplierid());
											map.put("fpcmordernumber", pinfo.getFpcmordernumber());
										}
										
										continue;
										}
									}
								}else{
									DeliversDao.ExecBySql("update t_ord_delivers set fordernumber='"+"未关联产品！"+"' where fid = '"+fid+"'");
									continue;
								}
				} catch (DJException e) {
					throw new DJException(e.getMessage());
				}
			}
			if(map.size()>0)
			{
				createMergerOrder(map.get("fproductid").toString(),(Integer)map.get("fnewtype"),userid,map.get("fcustomerid").toString(),(map.get("fcusproductid")==null?"":map.get("fcusproductid").toString()),(Integer)map.get("famount"),(Date)map.get("farrivetime"),map.get("Deliverid").toString(),(String)map.get("fsupplierid"),(String)map.get("fpcmordernumber"));
				map.clear();
			}
			result = JsonUtil.result(true,"下单成功!", "", "");
		} catch (Exception e) {
			result = JsonUtil.result(false,"下单失败! '" + e.getMessage()+"'", "", "");
		}
		reponse.setCharacterEncoding("utf-8");
		reponse.getWriter().write(result);
		}
		return null;		
		
	}
	/*
	 * 配送单分配
	 */
	@RequestMapping(value = "/deliversAllot")
	public String deliversAllot(HttpServletRequest request,HttpServletResponse reponse) throws Exception {
		reponse.setCharacterEncoding("utf-8");

		try {
			String fcustomerid=request.getParameter("fcustomerid");
			if(fcustomerid==null||"".equals(fcustomerid))
			{
			  throw new DJException("错误 ！请选择一条记录进行操作！");
			}
			String userid = ((Useronline) request.getSession().getAttribute("Useronline")).getFuserid();
//			String sql="select  distinct '"+userid+"' as loginuserid,d.fpcmordernumber,d.famount,d.fid,d.fnumber,d.fcusproductid,d.fproductid,d.fcustomerid,d.farrivetime,d.flinkman,d.flinkphone,d.faddress,d.fdescription,d.faddressid,d.fbalanceunitprice,d.fcusfid,if(ifnull(d.fsupplierid,'')<>'',d.fsupplierid,c.fsupplierid) fsupplierid,if(ifnull(d.fsupplierid,'')<>'','100',c.fproportion) fproportion,ifnull(p.ftype,3) as vmitype,d.ftype dtype from t_ord_delivers d left join t_bd_productcycle c on c.fproductdefid=d.fproductid left join t_pdt_vmiproductparam p on p.fproductid=d.fproductid and c.fsupplierid=p.fsupplierid";
//			sql=sql+" where ifnull(d.falloted,0) = 0 and d.ftype<>1 and d.fcustomerid='" +fcustomerid+
//					"' order by d.farrivetime,d.fid";
		String sql="SELECT  '"+userid+"'  AS loginuserid,IFNULL(p.ftype, 3) AS vmitype, d.fpcmordernumber,d.famount, d.fid, d.fnumber,d.fcusproductid,d.fproductid, d.fcustomerid, d.farrivetime, d.flinkman, d.flinkphone,d.faddress,d.fdescription, d.faddressid, d.fbalanceunitprice,d.fcusfid,d.fsupplierid,d.fproportion, d.dtype "
			+" FROM (SELECT DISTINCT d.fpcmordernumber,d.famount, d.fid, d.fnumber,d.fcusproductid,d.fproductid, d.fcustomerid, d.farrivetime, d.flinkman, d.flinkphone,d.faddress,d.fdescription, d.faddressid, d.fbalanceunitprice,d.fcusfid,IF( IFNULL(d.fsupplierid, '') <> '' AND  d.fsupplierid<>'null', d.fsupplierid,c.fsupplierid) fsupplierid,IF( IFNULL(d.fsupplierid, '') <> '' AND  d.fsupplierid<>'null', '100',c.fproportion) fproportion,d.ftype dtype "
			+" FROM t_ord_delivers d LEFT JOIN t_bd_productcycle c ON c.fproductdefid = d.fproductid WHERE IFNULL(d.falloted, 0) = 0 AND d.ftype <> 1  AND d.fcustomerid = '"+fcustomerid+"') d "
			+"  LEFT JOIN t_pdt_vmiproductparam p ON p.fproductid = d.fproductid AND d.fsupplierid = p.fsupplierid "
			+" ORDER BY d.farrivetime,d.fid ";
			List<HashMap<String,Object>> predeliverslist= DeliversDao.QueryBySql(sql);
			List<List<HashMap<String,Object>>> dealdatas=new ArrayList<>();
			List<HashMap<String,Object>> deliverslist = new ArrayList<>();
			HashMap<String,Object> info;
			String lastfid="";
			if(predeliverslist.size()<=0)
			{
				throw new DJException("没有可分配的要货管理");
			}
			for(int i=0;i<predeliverslist.size();i++)
			{
				info=predeliverslist.get(i);
				if(lastfid.equals(""))
				{
					deliverslist=new ArrayList<>();
					deliverslist.add(info);
				}
				else if(!info.get("fid").equals(lastfid))
				{
					dealdatas.add(deliverslist);
					deliverslist=new ArrayList<>();
					deliverslist.add(info);
				}
				else
				{
					deliverslist.add(info);
				}
				lastfid=info.get("fid").toString();
			}
			//将剩下的放入需要处理的数据中
			dealdatas.add(deliverslist);
			for(int i=0;i<dealdatas.size();i++)
			{
				deliverslist=dealdatas.get(i);
				try {
					DeliversDao.ExecAllot(deliverslist);
				} catch (DJException e) {
					DeliversDao.ExecBySql("update t_ord_delivers set fordernumber='"+e.getMessage()+"' where fid = '"+deliverslist.get(0).get("fid").toString()+"'");
				}
				
			}
			reponse.getWriter().write(JsonUtil.result(true,"分配成功!", "", ""));
		} catch (DJException e) {
			// TODO: handle exception
			reponse.getWriter().write(JsonUtil.result(false,"分配失败! '" + e.getMessage()+"'", "", ""));
		}
		
		return null;
		
//		String result = "";
//		Delivers pinfo = null;
//		try {
//			String userid = ((Useronline) request.getSession().getAttribute("Useronline")).getFuserid();
//			String fid = "",vmiparams="";
//			int counts=0,type=0;
////			String sql = "select * from t_ord_delivers where ifnull(falloted,0) = 0 order by fcusproductid,fcreatetime ";
//			String sql = "select d.fid,d.fproductid,count(c.fsupplierid) counts, case when count(c.fsupplierid)=1 then concat(c.fsupplierid,',',ifnull(ftype,3)) else '' end fsupplieridd "
//					+ " from t_ord_delivers d "
//					+ " left join t_bd_productcycle c on c.fproductdefid=d.fproductid "
//					+ " left join t_pdt_vmiproductparam p on p.fproductid=d.fproductid and c.fsupplierid=p.fsupplierid "
//					+ " where ifnull(d.falloted,0) = 0 "
//					+ " group by d.fid "
//					+ " order by d.fcusproductid,d.fcreatetime";
//			List<HashMap<String,Object>> deliverslist = DeliversDao.QueryBySql(sql);
//			String userID = ((Useronline) request.getSession().getAttribute("Useronline")).getFuserid();
//			HashMap map = new HashMap();
//			for(int i=0;i<deliverslist.size();i++){
//				HashMap deliversinfo = deliverslist.get(i);
//				fid = deliversinfo.get("fid").toString();
//				counts=Integer.parseInt(deliversinfo.get("counts").toString());
//				vmiparams=deliversinfo.get("fsupplieridd").toString();
//				try {
//					if(counts==1&&!vmiparams.isEmpty())
//					{
//						if (fid != null && !"".equals(fid)) {
//							pinfo = DeliversDao.Query(fid);
//						}
//						type=Integer.parseInt(vmiparams.split(",")[1]);
////						String fnumber = pinfo.getFnumber();
//						String fcusproductid = pinfo.getFcusproductid();
////						String flinkman = pinfo.getFlinkman();
////						String flinkphone = pinfo.getFlinkphone();
//						String fcustomerid = pinfo.getFcustomerid();
//						int famount = pinfo.getFamount();
////						String faddress = pinfo.getFaddress();
//						params p=null;
//						BigDecimal stock = new BigDecimal(0);
//						String fproductid = "";
//						if(deliversinfo.get("fproductid")==null || StringHelper.isEmpty( deliversinfo.get("fproductid").toString()))
//						{
//							DeliversDao.ExecBySql("update t_ord_delivers set fordernumber='"+"没有对应的产品！"+"' where fid = '"+fid+"'");
//							continue;
//						}
//						fproductid = deliversinfo.get("fproductid").toString();//o.get("fid").toString();
//						int fnewtype = 0;
//						String isassemble = "0";
//						sql = "select d.fid,d.fassemble,d.fnewtype,d.fcustomerid,d.feffect from t_pdt_productdef d where fid = '"+fproductid+"' ";
//						List<HashMap<String,Object>> pdtlist= DeliversDao.QueryBySql(sql);
//						if(pdtlist.size()>0){
//							HashMap pdtinfo = pdtlist.get(0);
//							if(pdtinfo.get("fnewtype")!=null){
//								fnewtype = new Integer(pdtinfo.get("fnewtype").toString());
//							}
//							if(type==1)//下单类型为订单
//							{
//								
//								sql = "select ifnull(sum(ifnull(b.fbalanceqty,0)),0) balanceqty from t_inv_storebalance b where b.fproductid='"+fproductid+"' and ftype=1 ";
//								List<HashMap<String,Object>> sblist= DeliversDao.QueryBySql(sql);
//								if(sblist.size()>0){
//									HashMap sbinfo = sblist.get(0);
//									stock=new BigDecimal(sbinfo.get("balanceqty").toString());
//								}
//								//库存需要减去已关联制造商订单且未出库的配送单总数量;
//								
//								sql = "select ifnull(sum(d.famount),0) famount from t_ord_productplan pp inner join t_ord_deliverorder d on d.fplanid=pp.fid inner join t_inv_storebalance b on d.fstorebalanceid=b.fid where pp.FPRODUCTDEFID=:fproductid and d.fcustomerid = :fcustomerid and d.fouted = 0 and b.ftype=1";
//								p=new params();
//								p.put("fcustomerid",fcustomerid);
//								p.put("fproductid",fproductid);
//								List<HashMap<String,Object>> unsblist= DeliversDao.QueryBySql(sql, p);
//								if(unsblist.size()>0){
//									HashMap unsbinfo = unsblist.get(0);
//									stock=stock.subtract(new BigDecimal(unsbinfo.get("famount").toString()));
//								}
//								int balanceqty = stock.divide(new BigDecimal(1), 0, BigDecimal.ROUND_DOWN).intValue();
//								if(balanceqty>=famount){
//									//sql = "select b.fproductplanID,b.forderentryid,sum((ifnull(b.fbalanceqty,0)-ifnull(a.famount,0))) fbalanceqty from t_inv_storebalance b left join (select forderid,fordertype from t_ord_saleorder where fseq=1 ) s on s.forderid=b.fsaleorderid left join (select ifnull(sum(d.famount),0) famount,pp.fid,pp.fcreatetime from t_ord_productplan pp inner join t_ord_deliverorder d on d.fplanid=pp.fid where ifnull(d.fouted,0) = 0 and pp.FPRODUCTDEFID=:fproductid and d.fcustomerid = :fcustomerid group by pp.fid ) a on b.fproductplanid=a.fid where ifnull(s.fordertype,3)<>2 and ifnull(s.fordertype,3)<>4 and ifnull(b.fbalanceqty,0)>0 and b.fproductid='"+fproductid+"' group by b.fproductplanID,b.forderentryid  order by a.fcreatetime,b.forderentryid,b.fproductplanId";
//									//sql = " select b.fproductplanID,b.forderentryid,(ifnull(b.fbalanceqty,0)-ifnull(a.famount,0)) fbalanceqty from ( select fproductplanID ,forderentryid,FPRODUCTID,fsaleorderid, sum(ifnull(fbalanceqty,0))  fbalanceqty  from  t_inv_storebalance  where FPRODUCTID=:fproductid group by fproductplanID ) b left join (select forderid,fordertype from t_ord_saleorder where fseq=1 ) s on s.forderid=b.fsaleorderid left join (select ifnull(sum(d.famount),0) famount,pp.fid,pp.fcreatetime from t_ord_productplan pp inner join t_ord_deliverorder d on d.fplanid=pp.fid where ifnull(d.fouted,0) = 0 and pp.FPRODUCTDEFID=:fproductid and d.fcustomerid = :fcustomerid group by pp.fid ) a on b.fproductplanid=a.fid where ifnull(s.fordertype,3)<>2 and ifnull(s.fordertype,3)<>4 and ifnull(b.fbalanceqty,0)>0 and b.fproductid=:fproductid  order by a.fcreatetime,b.forderentryid,b.fproductplanId"; 
//									sql = " select b.fproductplanID,b.forderentryid,(ifnull(b.fbalanceqty,0)-ifnull(a.famount,0)) fbalanceqty from ( select t_inv_storebalance.fproductplanID ,t_inv_storebalance.forderentryid,t_inv_storebalance.FPRODUCTID,t_inv_storebalance.fsaleorderid, sum(ifnull(t_inv_storebalance.fbalanceqty,0))  fbalanceqty,t_ord_productplan.fcreatetime  from  t_inv_storebalance left join t_ord_productplan on t_inv_storebalance.fproductplanID=t_ord_productplan.fid where t_inv_storebalance.FPRODUCTID=:fproductid and  ifnull(t_inv_storebalance.ftype,0)=1 group by fproductplanID ) b  left join (select ifnull(sum(d.famount),0) famount,pp.fid,pp.fcreatetime from t_ord_productplan pp inner join t_ord_deliverorder d on d.fplanid=pp.fid where ifnull(d.fouted,0) = 0 and pp.FPRODUCTDEFID=:fproductid and d.fcustomerid = :fcustomerid group by pp.fid ) a on b.fproductplanid=a.fid where ifnull(b.fbalanceqty,0)>0 and b.fproductid=:fproductid  order by b.fcreatetime,b.forderentryid,b.fproductplanId";
//									p=new params();
//									p.put("fproductid",fproductid);
//									p.put("fcustomerid",fcustomerid);
//									List<HashMap<String,Object>> balancelist= DeliversDao.QueryBySql(sql, p);
//									pinfo.setFordernumber(DeliversOutBalance(balancelist,famount,pinfo,userID));
//									pinfo.setFalloted(1);
//	//								pinfo.setFordered(1);
//									DeliversDao.ExecSave(pinfo);
//								}else{
//									pinfo.setFordernumber("没有库存分配 ！");
//									DeliversDao.ExecSave(pinfo);
//	//									DeliversDao.ExecBySql("update t_ord_delivers set fordernumber='"+"未关联产品！"+"' where fid = '"+fid+"'");
//									continue;
//								}
//							}
//							else if(type==0)//下单类型为通知
//							{
//								//查询该产品的制造商类型为通知的库存量（结存表的库存量-已分配未发货的数量）
//								sql="select  b.fid, (ifnull(b.fbalanceqty,0)-ifnull(a.famount,0)) fbalanceqty  "+
//									"from t_inv_storebalance b "+
//									"left join  ( select s.fid,ifnull(sum(r.famount),0) famount from  t_ord_deliverorder r "+
//									"inner join t_inv_storebalance s on s.fid=r.fstorebalanceid  "+
//									"where  ifnull(r.fouted,0) = 0 and r.fcustomerid=:fcustomerid and s.fproductid=:fproductid and s.FSUPPLIERID=:fsupplerid and s.ftype=0 "+
//									"group by s.fid ) a on a.fid=b.fid  "+
//									"where b.FSUPPLIERID=:fsupplerid and b.fproductid=:fproductid and b.ftype=0 and  b.fbalanceqty>0 ";
//								p=new params();
//								p.put("fcustomerid",fcustomerid);
//								p.put("fproductid",fproductid);
//								p.put("fsupplerid",vmiparams.split(",")[0]);
//								List<HashMap<String,Object>> blist= DeliversDao.QueryBySql(sql, p);
//								
//								if(blist.size()>0)
//								{
//									stock=new BigDecimal(blist.get(0).get("fbalanceqty").toString());
//									
//								}
//								int balanceqty = stock.divide(new BigDecimal(1), 0, BigDecimal.ROUND_DOWN).intValue();
//								if(balanceqty>0&&balanceqty>=famount){
//								ProductPlan pplaninfo=new ProductPlan();
//								pplaninfo.setFsupplierid(vmiparams.split(",")[0]);
//								createDeliverorder(pinfo,famount,pplaninfo,userID,blist.get(0).get("fid").toString(), pinfo.getFnumber());
//								pinfo.setFalloted(1);
//								
//								DeliversDao.ExecSave(pinfo);
//								}else{
//									DeliversDao.ExecBySql("update t_ord_delivers set fordernumber='"+"没有库存分配！"+"' where fid = '"+fid+"'");
//									continue;
//								}
//								
//							}else//type==3为安全库存不存在！
//							{
//								DeliversDao.ExecBySql("update t_ord_delivers set fordernumber='"+"请设置该产品安全库存"+"' where fid = '"+fid+"'");
//								continue;
//							}
//						}else{
////							pinfo.setFordernumber("未关联产品！");
////							DeliversDao.ExecSave(pinfo);
//							DeliversDao.ExecBySql("update t_ord_delivers set fordernumber='"+"未关联产品！"+"' where fid = '"+fid+"'");
//							continue;
//						}
//						
//					}else if(counts>1){//多个供应商
//						
//						DeliversDao.ExecBySql("update t_ord_delivers set fordernumber='"+"多家制造商制造,请手动分配"+"' where fid = '"+fid+"'");
//						continue;
//						
//						
//						
//						
//					}else
//					{
//					DeliversDao.ExecBySql("update t_ord_delivers set fordernumber='"+"生产比例有误！"+"' where fid = '"+fid+"'");
//					continue;
//					}
//				} catch (Exception e) {
//					throw new DJException(e.getMessage());
//				}
////				pinfo.setFordermanid(userid);
////				pinfo.setFordered(1);
////				pinfo.setFordertime(new Date());
////				HashMap<String, Object> params = DeliversDao.ExecSave(pinfo);
//			}
//			result = JsonUtil.result(true,"分配成功!", "", "");
//		} catch (Exception e) {
//			result = JsonUtil.result(false,"分配失败! '" + e.getMessage()+"'", "", "");
//		}
//		reponse.setCharacterEncoding("utf-8");
//		reponse.getWriter().write(result);
//		return null;
	}
	
	
	
	
//	@RequestMapping(value = "/deliversAllot")
//	public String deliversAllot(HttpServletRequest request,HttpServletResponse reponse) throws Exception {
//		String result = "";
//		Delivers pinfo = null;
//		try {
//			String userid = ((Useronline) request.getSession().getAttribute("Useronline")).getFuserid();
//			String fid = "";
//			
////			String sql = "select * from t_ord_delivers where fordered = 0 ";
//			String sql = "select * from t_ord_delivers where ifnull(falloted,0) = 0 order by fcusproductid,fcreatetime ";
//			List<HashMap<String,Object>> deliverslist = DeliversDao.QueryBySql(sql);
//			String userID = ((Useronline) request.getSession().getAttribute("Useronline")).getFuserid();
//			HashMap map = new HashMap();
//			for(int i=0;i<deliverslist.size();i++){
//				HashMap deliversinfo = deliverslist.get(i);
//				fid = deliversinfo.get("fid").toString();
//				if (fid != null && !"".equals(fid)) {
//					pinfo = DeliversDao.Query(fid);
//				}
//				try {
//					String fnumber = pinfo.getFnumber();
//					String fcusproductid = pinfo.getFcusproductid();
//					String flinkman = pinfo.getFlinkman();
//					String flinkphone = pinfo.getFlinkphone();
//					String fcustomerid = pinfo.getFcustomerid();
//					int famount = pinfo.getFamount();
////					DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
////					Date farrivetime = pinfo.getFarrivetime();
//					String faddress = pinfo.getFaddress();
//					
////					sql = "select fid from t_pdt_productdef where fid in (select fproductdefid from t_sys_custrelationproduct where fcustid =:fcustid )";
//					params p=new params();
////					p.put("fcustid",fcusproductid);
////					List<HashMap<String,Object>> data= DeliversDao.QueryBySql(sql, p);
////						if (data.size()>0) //客户产品只有一个对应的产品ID;
////						{
//							BigDecimal stock = new BigDecimal(0);
////							HashMap o = data.get(0);
//							String fproductid = "";
////							if(o.get("fid")!=null){
//							if(deliversinfo.get("fproductid")==null || StringHelper.isEmpty( deliversinfo.get("fproductid").toString()))
//							{
//								DeliversDao.ExecBySql("update t_ord_delivers set fordernumber='"+"没有对应的产品！"+"' where fid = '"+fid+"'");
//								continue;
//							}
//								fproductid = deliversinfo.get("fproductid").toString();//o.get("fid").toString();
////							}
//							int fnewtype = 0;
//							String isassemble = "0";
////							String EAScustomerid = "";
//								ResultSet rs = null;
//								sql = "select d.fid,d.fassemble,d.fnewtype,d.fcustomerid,d.feffect from t_pdt_productdef d where fid = '"+fproductid+"' ";
//								List<HashMap<String,Object>> pdtlist= DeliversDao.QueryBySql(sql);
//								if(pdtlist.size()>0){
//									HashMap pdtinfo = pdtlist.get(0);
//									if(pdtinfo.get("fnewtype")!=null){
//										fnewtype = new Integer(pdtinfo.get("fnewtype").toString());
//									}
//									/*
//									 * 套装产品在结存中已经有库存，分配的时候不需要重新计算库存
//									 */
////									//不包含套装子件的普通产品订单库存数量
////									if((fnewtype!=2 && fnewtype!=4)){
////										sql = "select ifnull(sum(ifnull(b.fbalanceqty,0)),0) balanceqty from t_inv_storebalance b left join (select forderid,fordertype from t_ord_saleorder where fseq=1 ) s on s.forderid=b.fsaleorderid where ifnull(s.fordertype,3)<>2 and ifnull(s.fordertype,3)<>4 and b.fproductid='"+fproductid+"' ";
////										List<HashMap<String,Object>> sblist= DeliversDao.QueryBySql(sql);
////										if(sblist.size()>0){
////											HashMap sbinfo = sblist.get(0);
////											stock=new BigDecimal(sbinfo.get("balanceqty").toString());
////										}
////									}else{
////										//套装
////										String productIDs = "";
////										sql = "select d.fid,d.fcombination from T_PDT_ProductDefProducts pd left join t_pdt_productdef d on d.fid=pd.fproductid where fparentid = :fproductid ";
////										p=new params();
////										p.put("fproductid",fproductid);
////										List<HashMap<String,Object>> suitpdtlist= DeliversDao.QueryBySql(sql, p);
////										for(int j=0;j<suitpdtlist.size();j++){
////											HashMap suitpdtinfo = suitpdtlist.get(j);
////											if(suitpdtinfo.get("fcombination")!=null && suitpdtinfo.get("fcombination").toString().equals("0")){
////												if(productIDs.length()>0){
////													productIDs = productIDs + "','" + rs.getString("fid");
////												}else{
////													productIDs = "('" + rs.getString("fid");
////												}
////											}
////										}
////										if(productIDs.length()==0){
////											productIDs = "('" ;
////										}
////										productIDs = productIDs + "')";
////
////										
////										//套装库存总量
////										StringBuffer oql = new StringBuffer("select min(ifnull(qty,0)) Qty from (select t.fproductid,ifnull(sum(t1.fbalanceqty/t.famount),0)+sum((case when t2.famount<ifnull(t2.fstockinqty,0) then 0 else t2.famount-ifnull(t2.fstockinqty,0) end)/t.famount) qty from T_PDT_ProductDefProducts t ");
////										oql.append("left join t_inv_storebalance t1 on t.fproductid=t1.fproductid left join t_ord_saleorder t2 on t2.fproductdefid=t.fproductid where t2.faffirmed=1 and t.fparentid = '").append(fproductid).append("' and t.fproductid in ").append(productIDs).append(" group by t.fproductid) a");
////										List<HashMap<String,Object>> suitsblist= DeliversDao.QueryBySql(oql.toString());
////										if(suitsblist.size()>0){
////											HashMap suitsbinfo = suitpdtlist.get(0);
////											stock=new BigDecimal(suitsbinfo.get("Qty").toString());
////										}
////									}
//									//sql = "select ifnull(sum(ifnull(b.fbalanceqty,0)),0) balanceqty from t_inv_storebalance b left join (select forderid,fordertype from t_ord_saleorder where fseq=1 ) s on s.forderid=b.fsaleorderid where ifnull(s.fordertype,3)<>2 and ifnull(s.fordertype,3)<>4 and b.fproductid='"+fproductid+"' ";
//									sql = "select ifnull(sum(ifnull(b.fbalanceqty,0)),0) balanceqty from t_inv_storebalance b where b.fproductid='"+fproductid+"' ";
//									List<HashMap<String,Object>> sblist= DeliversDao.QueryBySql(sql);
//									if(sblist.size()>0){
//										HashMap sbinfo = sblist.get(0);
//										stock=new BigDecimal(sbinfo.get("balanceqty").toString());
//									}
//									//库存需要减去已关联制造商订单且未出库的配送单总数量;
//									sql = "select ifnull(sum(d.famount),0) famount from t_ord_productplan pp inner join t_ord_deliverorder d on d.fplanid=pp.fid where pp.FPRODUCTDEFID=:fproductid and d.fcustomerid = :fcustomerid and d.fouted = 0";
//									p=new params();
//									p.put("fcustomerid",fcustomerid);
//									p.put("fproductid",fproductid);
//									List<HashMap<String,Object>> unsblist= DeliversDao.QueryBySql(sql, p);
//									if(unsblist.size()>0){
//										HashMap unsbinfo = unsblist.get(0);
//										stock=stock.subtract(new BigDecimal(unsbinfo.get("famount").toString()));
//									}
//									
//									int balanceqty = stock.divide(new BigDecimal(1), 0, BigDecimal.ROUND_DOWN).intValue();
//									if(balanceqty>=famount){
//										//sql = "select b.fproductplanID,b.forderentryid,sum((ifnull(b.fbalanceqty,0)-ifnull(a.famount,0))) fbalanceqty from t_inv_storebalance b left join (select forderid,fordertype from t_ord_saleorder where fseq=1 ) s on s.forderid=b.fsaleorderid left join (select ifnull(sum(d.famount),0) famount,pp.fid,pp.fcreatetime from t_ord_productplan pp inner join t_ord_deliverorder d on d.fplanid=pp.fid where ifnull(d.fouted,0) = 0 and pp.FPRODUCTDEFID=:fproductid and d.fcustomerid = :fcustomerid group by pp.fid ) a on b.fproductplanid=a.fid where ifnull(s.fordertype,3)<>2 and ifnull(s.fordertype,3)<>4 and ifnull(b.fbalanceqty,0)>0 and b.fproductid='"+fproductid+"' group by b.fproductplanID,b.forderentryid  order by a.fcreatetime,b.forderentryid,b.fproductplanId";
//										//sql = " select b.fproductplanID,b.forderentryid,(ifnull(b.fbalanceqty,0)-ifnull(a.famount,0)) fbalanceqty from ( select fproductplanID ,forderentryid,FPRODUCTID,fsaleorderid, sum(ifnull(fbalanceqty,0))  fbalanceqty  from  t_inv_storebalance  where FPRODUCTID=:fproductid group by fproductplanID ) b left join (select forderid,fordertype from t_ord_saleorder where fseq=1 ) s on s.forderid=b.fsaleorderid left join (select ifnull(sum(d.famount),0) famount,pp.fid,pp.fcreatetime from t_ord_productplan pp inner join t_ord_deliverorder d on d.fplanid=pp.fid where ifnull(d.fouted,0) = 0 and pp.FPRODUCTDEFID=:fproductid and d.fcustomerid = :fcustomerid group by pp.fid ) a on b.fproductplanid=a.fid where ifnull(s.fordertype,3)<>2 and ifnull(s.fordertype,3)<>4 and ifnull(b.fbalanceqty,0)>0 and b.fproductid=:fproductid  order by a.fcreatetime,b.forderentryid,b.fproductplanId"; 
//										sql = " select b.fproductplanID,b.forderentryid,(ifnull(b.fbalanceqty,0)-ifnull(a.famount,0)) fbalanceqty from ( select t_inv_storebalance.fproductplanID ,t_inv_storebalance.forderentryid,t_inv_storebalance.FPRODUCTID,t_inv_storebalance.fsaleorderid, sum(ifnull(t_inv_storebalance.fbalanceqty,0))  fbalanceqty,t_ord_productplan.fcreatetime  from  t_inv_storebalance left join t_ord_productplan on t_inv_storebalance.fproductplanID=t_ord_productplan.fid where t_inv_storebalance.FPRODUCTID=:fproductid group by fproductplanID ) b  left join (select ifnull(sum(d.famount),0) famount,pp.fid,pp.fcreatetime from t_ord_productplan pp inner join t_ord_deliverorder d on d.fplanid=pp.fid where ifnull(d.fouted,0) = 0 and pp.FPRODUCTDEFID=:fproductid and d.fcustomerid = :fcustomerid group by pp.fid ) a on b.fproductplanid=a.fid where ifnull(b.fbalanceqty,0)>0 and b.fproductid=:fproductid  order by b.fcreatetime,b.forderentryid,b.fproductplanId";
//										p=new params();
//										p.put("fproductid",fproductid);
//										p.put("fcustomerid",fcustomerid);
//										List<HashMap<String,Object>> balancelist= DeliversDao.QueryBySql(sql, p);
//										pinfo.setFordernumber(DeliversOutBalance(balancelist,famount,pinfo,userID));
//										pinfo.setFalloted(1);
////										pinfo.setFordered(1);
//										DeliversDao.ExecSave(pinfo);
//									}else{
//										pinfo.setFordernumber("没有库存分配 ！");
//										DeliversDao.ExecSave(pinfo);
////											DeliversDao.ExecBySql("update t_ord_delivers set fordernumber='"+"未关联产品！"+"' where fid = '"+fid+"'");
//										continue;
//									}
//								}else{
////									pinfo.setFordernumber("未关联产品！");
////									DeliversDao.ExecSave(pinfo);
//									DeliversDao.ExecBySql("update t_ord_delivers set fordernumber='"+"未关联产品！"+"' where fid = '"+fid+"'");
//									continue;
//								}
////						}else{
//////							pinfo.setFordernumber("未关联产品！");
////							DeliversDao.ExecBySql("update t_ord_delivers set fordernumber='"+"未关联产品！"+"' where fid = '"+fid+"'");
////							continue;
////						}
//				} catch (Exception e) {
//					throw new DJException(e.getMessage());
//				}
////				pinfo.setFordermanid(userid);
////				pinfo.setFordered(1);
////				pinfo.setFordertime(new Date());
////				HashMap<String, Object> params = DeliversDao.ExecSave(pinfo);
//			}
//			result = JsonUtil.result(true,"分配成功!", "", "");
//		} catch (Exception e) {
//			result = JsonUtil.result(false,"分配失败! '" + e.getMessage()+"'", "", "");
//		}
//		reponse.setCharacterEncoding("utf-8");
//		reponse.getWriter().write(result);
//		return null;
//	}
	
	
	private String DeliversOutBalance(List<HashMap<String, Object>> sblist , int famount, Delivers pinfo, String userID) {
		// TODO Auto-generated method stub
		int bigRest = famount;
		int bigThisTotalQty = 0;
		String ordernumber = "";
		for (int i=0;i<sblist.size();i++)
		{
			if(bigRest<=0)
			{
				break;
			}
			HashMap sbinfo = sblist.get(i);
			int bigEachLocationQty = new Integer(sbinfo.get("fbalanceqty").toString());
			if (bigEachLocationQty <= 0)
			{
				continue;//库存余额为零，则不从该库位上出库。
			}
			String fproductplanID = sbinfo.get("fproductplanID").toString();
			ProductPlan pplaninfo=ProductPlanDao.Query(fproductplanID);
			if(!ordernumber.equals(""))
			{
				ordernumber=ordernumber+","+pplaninfo.getFnumber();
			}
			else
			{
				ordernumber=pplaninfo.getFnumber();
			}
			String forderentryID = sbinfo.get("forderentryid").toString();
//			Storebalance sbInfo = storebalanceDao.Query(sbId);
//			int bigEachLocationQty = sbInfo.getFbalanceqty();
			bigRest = bigRest - bigEachLocationQty;
			if ( bigRest < 0 )// >=0。此时该库位已经可以满足出库要求。
			{
//				bigThisTotalQty = bigThisTotalQty + famount;
//				sbInfo.setFoutqty(sbInfo.getFoutqty()+(bigEachLocationQty + bigRest));// 出库增加
//				sbInfo.setFbalanceqty(bigRest*(-1));
//				storebalanceDao.ExecSave(sbInfo);// 更新库存余额表
				
				createDeliverorder(pinfo,bigEachLocationQty + bigRest,pplaninfo,userID,forderentryID,pplaninfo.getFnumber());
				break;
			}
			else
				// <0
			{
//				bigThisTotalQty = bigThisTotalQty + bigEachLocationQty;
//				sbInfo.setFoutqty(sbInfo.getFoutqty() + bigEachLocationQty);// 出库增加
//				sbInfo.setFbalanceqty(0);// 该库位已经没有库存，要从下一个库位上继续出库。
//				storebalanceDao.ExecSave(sbInfo);// 更新库存余额表
				
				createDeliverorder(pinfo,bigEachLocationQty,pplaninfo,userID,forderentryID,pplaninfo.getFnumber());
			}
		}
		return ordernumber;
	}
	
	private void createDeliverorder(Delivers pinfo, int famount, ProductPlan pplaninfo, String userID, String forderentryID,String ordernumber) {
		// TODO Auto-generated method stub
		Deliverorder deliverorderinfo = new Deliverorder();
		deliverorderinfo.setFamount(famount);
		deliverorderinfo.setFbalanceunitprice(pinfo.getFbalanceunitprice()==null?new BigDecimal("0.00"):pinfo.getFbalanceunitprice());
		deliverorderinfo.setFbalanceprice(deliverorderinfo.getFbalanceunitprice().multiply(new BigDecimal(famount)).divide(new BigDecimal(1), 4, BigDecimal.ROUND_HALF_UP));
		deliverorderinfo.setFpurchaseunitprice(pplaninfo.getPerpurchaseprice()==null?new BigDecimal("0.00"):pplaninfo.getPerpurchaseprice());
		deliverorderinfo.setFpurchaseprice(deliverorderinfo.getFpurchaseunitprice().multiply(new BigDecimal(famount)).divide(new BigDecimal(1), 4, BigDecimal.ROUND_HALF_UP));
		deliverorderinfo.setFproductid(pinfo.getFproductid());
		deliverorderinfo.setFaddress(pinfo.getFaddress());
		deliverorderinfo.setFaddressid(pinfo.getFaddressid());
		deliverorderinfo.setFalloted(1);
		deliverorderinfo.setFarrivetime(pinfo.getFarrivetime());
		deliverorderinfo.setFcreatetime(new Date());
		deliverorderinfo.setFcreatorid(userID);
		deliverorderinfo.setFcusproductid(pinfo.getFcusproductid());
		deliverorderinfo.setFcustomerid(pinfo.getFcustomerid());
		deliverorderinfo.setFdeliversId(pinfo.getFid());
		deliverorderinfo.setFdescription(pinfo.getFdescription());
		deliverorderinfo.setFeasdeliverid(pinfo.getFeasdeliverid());
		deliverorderinfo.setFid(DeliverorderDao.CreateUUid());
		deliverorderinfo.setFimportEas(0);
		deliverorderinfo.setFimportEastime(null);
		deliverorderinfo.setFimportEasuserid(null);
		deliverorderinfo.setFlinkman(pinfo.getFlinkman());
		deliverorderinfo.setFlinkphone(pinfo.getFlinkphone());
		deliverorderinfo.setFnumber(ServerContext.getNumberHelper().getNumber("t_ord_deliverorder", "D", 4, false));
//		deliverorderinfo.setFnumber(DeliverorderDao.getFnumber("t_ord_deliverorder", "D",4));
//		deliverorderinfo.setForderentryid(pplaninfo.getFid());
		deliverorderinfo.setFordernumber(ordernumber);
//		deliverorderinfo.setFplanid(pplaninfo.getFid());
		deliverorderinfo.setFouted(0);
//		deliverorderinfo.setFsaleorderid(pplaninfo.getForderid());
		deliverorderinfo.setFupdatetime(new Date());
		deliverorderinfo.setFupdateuserid(userID);
		deliverorderinfo.setFsupplierId(pplaninfo.getFsupplierid());
		if(pplaninfo.getFid()!=null&&!pplaninfo.getFid().isEmpty()){
			deliverorderinfo.setForderentryid(pplaninfo.getFid());
			deliverorderinfo.setFplanid(pplaninfo.getFid());
			deliverorderinfo.setFsaleorderid(pplaninfo.getForderid());
			String sql="select fid from t_inv_storebalance where fproductplanID='"+pplaninfo.getFid()+"' and ftype=1 and fbalanceqty>0";
			List<HashMap<String,Object>> blistss= DeliversDao.QueryBySql(sql);
			if(blistss.size()>0){
				deliverorderinfo.setFstorebalanceid(blistss.get(0).get("fid").toString());	
			}
		}else
		{
			pinfo.setFordernumber(deliverorderinfo.getFnumber());
			deliverorderinfo.setFstorebalanceid(forderentryID);
		}
		
		DeliverorderDao.ExecSave(deliverorderinfo);
	}
	
	/*
	 * 配送单取消分配
	 */
	@RequestMapping(value = "/deliversUnAllot")
	public String deliversUnAllot(HttpServletRequest request,HttpServletResponse reponse) throws Exception {
		String result = "";
		String fidcls = request.getParameter("fidcls");
		try {
			DataUtil.verifyNotNullAndDataAndPermissions(new String[][]{{"fidcls","所选记录"}},
					new String[][]{{"fidcls", "t_ord_delivers","fcustomerid", null}}, request,
					DeliversDao);
			fidcls="'"+fidcls.replace(",","','")+"'";
			String sql = "select fnumber,fid FROM t_ord_deliverorder where (fouted = 1  or fimporteas=1 or fassembleQty>0)  and fdeliversId in (" + fidcls +")";
			List<HashMap<String,Object>> deliverorderlist= DeliversDao.QueryBySql(sql);
			if(deliverorderlist.size()>0){
				throw new DJException("配送单:'"+deliverorderlist.get(0).get("fnumber")+"'已发货或已部分提货或已经汇入物流系统不能取消分配！");
			}
		
//		    sql = "select fnumber,fid FROM t_ord_deliverorder where ifnull(ftraitid,'')<>''  and fdeliversId in (" + fidcls +")";
//			deliverorderlist= DeliversDao.QueryBySql(sql);
//			if(deliverorderlist.size()>0){
//				throw new DJException("配送单:'"+deliverorderlist.get(0).get("fnumber")+"'是一次性配送信息，不能取消分配！");
//			}
//			
//			DeliversDao.ExecBySql("set SQL_SAFE_UPDATES = 0 ");
//			String hql = "Delete FROM t_ord_deliverorder where fouted = 0 and fdeliversId in (" + fidcls +")";
//			DeliversDao.ExecBySql(hql);
//			
//			DeliversDao.ExecBySql("update t_ord_delivers set falloted = 0,Fordered=0,fordernumber='' where fid in (" + fidcls +")");
			DeliversDao.ExecUnAllot(fidcls);
			
			result = JsonUtil.result(true,"配送单取消分配成功!", "", "");
			reponse.setCharacterEncoding("utf-8");
		} catch (Exception e) {
			result = JsonUtil.result(false,e.getMessage(), "", "");
			log.error("DelDeliversList error", e);
		}
		reponse.getWriter().write(result);
		return null;
	}
	/**
	 * 此函数的主要功能是生成“生产订单”和改写要货申请和要货管理的状态
	 */
	private void createMergerOrder(String fproductid, int fnewtype, String userid, String fcustomerid, String fcusproductid, Integer famount, Date farrivetime, String deliverid,String fsupplierid,String fpcmordernumber) throws Exception {
		String orderid = saleOrderDao.CreateUUid();
		String fordernumber = ServerContext.getNumberHelper().getNumber("t_ord_saleorder", "Z",4,false);
		ArrayList<Saleorder> solist = new ArrayList<Saleorder>();
		String sql = null;//	sql = "select forderamount from t_pdt_vmiproductparam where fcustomerid = '"+fcustomerid+"' and fproductid = '"+fproductid+"' ";
		if(fnewtype!=2 && fnewtype!=4){
			//找该子件有没有套装，且套装有安全库存且为通知类型
			sql = "select 1 from t_pdt_vmiproductparam where ftype=0 and fcustomerid = '"+fcustomerid+"' "+
			"and fproductid in (select fparentid from t_pdt_productdefproducts where fproductid = '"+fproductid+"') ";
			List<HashMap<String, Object>> li = DeliversDao.QueryBySql(sql);
			if(li.size()>0){
				DeliversDao.ExecBySql("update t_ord_delivers set fordernumber='当前产品的套件为通知类型，不能下单！' where fid in ("+deliverid+")");
				return;
			}
			Saleorder soinfo = new Saleorder();
			String orderEntryid = saleOrderDao.CreateUUid();
			soinfo.setFid(orderEntryid);
			soinfo.setForderid(orderid);
			soinfo.setFcreatorid(userid);
			soinfo.setFcreatetime(new Date());
			soinfo.setFnumber(fordernumber);
			soinfo.setFproductdefid(fproductid);
			soinfo.setFentryProductType(0);
			soinfo.setFtype(1);
			soinfo.setFlastupdatetime(new Date());
			soinfo.setFlastupdateuserid(userid);
			soinfo.setFamount(famount);
			soinfo.setFcustomerid(fcustomerid);
			soinfo.setFcustproduct(fcusproductid);
			soinfo.setFarrivetime(farrivetime);
			soinfo.setFbizdate(new Date());
			soinfo.setFamountrate(1);
			soinfo.setFordertype(1);
			soinfo.setFseq(1);
			soinfo.setFsupplierid(fsupplierid);
			soinfo.setFaudited(1);//改为已审核
			soinfo.setFauditorid(userid);
			soinfo.setFaudittime(new Date());
			soinfo.setFpcmordernumber(fpcmordernumber);
//			soinfo.setFdescription(fpcmordernumber);
//			HashMap<String, Object> params = saleOrderDao.ExecSave(soinfo);
			
//			String ordersql = "SET SQL_SAFE_UPDATES=0 ";
//			DeliversDao.ExecBySql(ordersql);
			DateFormat f = new SimpleDateFormat("yyyy-MM-dd");
			String ordersql = "update t_ord_delivers set fordered=1,fordermanid='"+userid+"',fordertime='"+f.format(new Date())+"',fsaleorderid='"+orderid+"'," +
					"fordernumber='"+(fordernumber+"-1")+"',forderentryid='"+orderEntryid+"' where fid in (" + deliverid +")";
//			DeliversDao.ExecBySql(ordersql);
			
			solist.add(soinfo);
			//生成订单，更改要货管理和要货申请状态
			HashMap<String, Object> params = DeliversDao.ExecVMIorder(solist, ordersql, deliverid);
			
			if (params.get("success") == Boolean.FALSE) {
				throw new DJException("生成订单失败！");
			}
			
		}else{
			//套装
			// 一次性获取所有级次的：套装+子产品
			// 再按顺序加载即可
			String productinfo = "select 1 from t_pdt_productdefproducts p where p.fparentid in (select fproductid from t_pdt_productdefproducts where fparentid = '"+fproductid+"')";
			if(DeliversDao.QueryBySql(productinfo).size()>0){
				DeliversDao.ExecBySql("update t_ord_delivers set fordernumber='"+"套装产品不能超过3级！"+"' where fid in ("+deliverid+")");
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
						DeliversDao.ExecBySql("update t_ord_delivers set fordernumber='当前产品的子件为通知类型，不能下单！' where fid in ("+deliverid+")");
						return;
					}
					soinfo.setFparentOrderEntryId(subInfo.get("ParentOrderEntryId").toString());
					soinfo.setFamount(famount * new Integer(subInfo.get("amountRate").toString()));
					soinfo.setFproductdefid(subInfo.get("fid").toString());
					
				}
				soinfo.setFsupplierid(fsupplierid);
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
				soinfo.setFaudited(1);//改为已审核
				soinfo.setFauditorid(userid);
				soinfo.setFaudittime(new Date());
				soinfo.setFallot(0);
				soinfo.setFpcmordernumber(fpcmordernumber);
//				soinfo.setFdescription(fpcmordernumber);
//				HashMap<String, Object> params = saleOrderDao.ExecSave(soinfo);
				solist.add(soinfo);
				
			}
			
//			String ordersql = "SET SQL_SAFE_UPDATES=0 ";
//			DeliversDao.ExecBySql(ordersql);
			DateFormat f = new SimpleDateFormat("yyyy-MM-dd");
			String ordersql = "update t_ord_delivers set fordered=1,fordermanid='"+userid+"',fordertime='"+f.format(new Date())+"',fsaleorderid='"+orderid+"'," +
					"fordernumber='"+(fordernumber+"-1")+"',forderentryid='"+orderentryid+"' where fid in (" + deliverid +")";
//			DeliversDao.ExecBySql(ordersql);
			
			HashMap<String, Object> params = DeliversDao.ExecVMIorder(solist, ordersql, deliverid);
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
		String sql = "select fnewtype,FCombination,FAssemble from t_pdt_productdef where fid = '"+fproductid+"'";
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
			if(productrs.get("FAssemble")!=null){
				productInfo.put("FAssemble", productrs.get("FAssemble").toString());
			}else{
				productInfo.put("FAssemble", null);
			}
			
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
//			while (productrs.next())
//			{
//				stmt = conn.prepareStatement("select fid,fnewtype,ftype,FCombination,FAssemble from t_pdt_productdef where fid = '"+productrs.getString("FProductID")+"'");
//				ResultSet subproductrs = stmt.executeQuery();
//				if (subproductrs.next())
//				{
//					HashMap subInfo = new HashMap();
//					subInfo.put("fid",subproductrs.getString("fid"));
//					subInfo.put("amountRate",productrs.getInt("FAmount") * new Integer(productInfo.get("amountRate").toString()));
//					subInfo.put("fnewtype", subproductrs.getString("fnewtype"));
//					subInfo.put("ftype", subproductrs.getString("ftype"));
//					subInfo.put("FCombination", subproductrs.getString("FCombination"));
//					subInfo.put("FAssemble", subproductrs.getString("FAssemble"));
//					//子件的“分录的产品类型”
//					subInfo.put("entryProductType",subEntryProductType);
//					if(isassemble){
//						subInfo.put("FisassembleSuitSub", Boolean.TRUE);
//					}else{
//						subInfo.put("FisassembleSuitSub", Boolean.FALSE);
//					}
//					
//					getProductSuit(conn,stmt,subInfo,list,orderEntryID);
//				}
//			}
			
		}
	}
	
	@RequestMapping(value = "/saveAssginOrder")
	public String saveAssginOrder(HttpServletRequest request,
			HttpServletResponse reponse) throws Exception {
		
		
		try {
			String userid = ((Useronline)request.getSession().getAttribute("Useronline")).getFuserid();
			Delivers delivers=(Delivers)request.getAttribute("Delivers");
			List<Storebalance> storebalances=(ArrayList<Storebalance>)request.getAttribute("Storebalance");
			DataUtil.verifyNotNullAndDataAndPermissions(new String[][]{{"fid","标识"}},
					new String[][]{{"fid", "t_ord_delivers","fcustomerid", null}}, request, 
					DeliversDao);
			delivers=DeliversDao.Query(delivers.getFid());
			if(delivers!=null && delivers.getFalloted()!=null && delivers.getFalloted()==1)
			{
				throw new DJException("该要货信息已经分配！");
			}
			if(storebalances.size()==0)
			{
				throw new DJException("错误,没有选择指定");
			}

			int totalDelivernums=0;
			for(int i=0;i<storebalances.size();i++)
			{
				Storebalance sinfo=storebalances.get(i);
//				if(StringHelper.isEmpty(sinfo.getFcreatorid()))//通知类型
//				{
//					DataUtil.verifyNotNullAndDataAndPermissionsByValue(new String[][]{{sinfo.getFid(),"库存表标识"},{sinfo.getFsupplierID(),"制造商标识"}},
//							new String[][]{{"fid", "t_inv_storebalance", null,"fsupplierID",sinfo.getFid()},{"fsupplierID", "t_sys_supplier", null,"fid",sinfo.getFsupplierID()}}, request,
//							DeliversDao);
//				}
//				else{//订单类型
//					
//					DataUtil.verifyNotNullAndDataAndPermissionsByValue(new String[][]{{sinfo.getFid(),"库存表标识"},{sinfo.getFsupplierID(),"制造商标识"},{sinfo.getFproductplanId(),"制造商订单标识"},{sinfo.getFsaleorderid(),"生产订单标识"},{sinfo.getForderentryid(),"生产子分录标识"}},
//							new String[][]{{"fid", "t_inv_storebalance", null,"fsupplierID",sinfo.getFid()},{"fsupplierID", "t_sys_supplier", null,"fid",sinfo.getFsupplierID()},{"fproductplanId", "t_ord_productplan", "fcustomerid","fsupplierid",sinfo.getFproductplanId()},{"fproductplanId", "t_ord_productplan", "fcustomerid","fsupplierid",sinfo.getFproductplanId()}}, request,
//							DeliversDao);
//					String sql="SELECT count(*) as countt FROM t_ord_saleorder where forderid = '%s' and fid='%s'"+ DeliversDao.QueryFilterByUser(request, "fcustomerid", null);
//					sql = String.format(sql, sinfo.getFsaleorderid(), sinfo.getForderentryid());
//					List<HashMap<String, Object>> sList = DeliversDao.QueryBySql(sql);
//					if(((BigInteger)sList.get(0).get("countt")).intValue()==0)
//					{
//						throw new DJException("错误,数据校验有误，请确认是否有客户相应权限,或生产订单标识错误!");
//					}
//				}
				if(!DataUtil.positiveIntegerCheck(sinfo.getFbalanceqty()+"")){
					throw new DJException("错误,分配数量必须大于0");
				}
				
				totalDelivernums=totalDelivernums+sinfo.getFbalanceqty();
				if(i==storebalances.size()-1)
				{
					if(totalDelivernums!=delivers.getFamount()){
						throw new DJException("错误,分配数量和必须为"+delivers.getFamount());
					}
				}
				
			}

			delivers.setFcreatorid(userid);
			DeliversDao.ExecAssginAllot(delivers,storebalances);
			
			
			
			reponse.getWriter().write(JsonUtil.result(true, "保存成功", "", ""));
		} catch (DJException e) {
			reponse.getWriter().write(
					JsonUtil.result(false, "保存失败!" + e.getMessage(), "", ""));
		}
		
		return null;
	}
	
	
	
//	@RequestMapping(value = "/saveAssginOrder")
//	public String saveAssginOrder(HttpServletRequest request,
//			HttpServletResponse reponse) throws Exception {
//		if(assginflag)
//		{
//			throw new DJException("系统正忙，请稍后再试......");
//		}
//		assginflag=true;
//		try {
//			HashMap<String, Object> params;
//			String json = request.getReader().readLine();
//			JSONArray jsonA = getJsonArrayByS(json);
//			String userid = ((Useronline) request.getSession().getAttribute(
//					"Useronline")).getFuserid();
//			Delivers sinfo=null;
//			String number="";
//				for (int i = 0; i < jsonA.size(); i++) {
//					String fproductplanid=((JSONObject)jsonA.get(i)).getString("fproductplanid");
//					if(sinfo==null)
//					{
//						String deliverid=((JSONObject)jsonA.get(i)).getString("deliverid");
//						sinfo=DeliversDao.Query(deliverid);
//						if(sinfo!=null&& sinfo.getFalloted()!=null && sinfo.getFalloted()==1)
//						{
//							throw new DJException("该要货信息已经分配！");
//						}
//					}
//					ProductPlan pinfo=ProductPlanDao.Query(fproductplanid);
//				
//					if(!number.contains(pinfo.getFnumber()))
//					{
//						if(i>0)
//						{
//							number+=",";
//						}
//						number+=pinfo.getFnumber();
//					}
//					int famount=Integer.valueOf(((JSONObject)jsonA.get(i)).getString("famount"));
//					createDeliverorder(sinfo, famount,pinfo, userid,pinfo.getFparentorderid(),pinfo.getFnumber());
//				}
//				sinfo.setFalloted(1);
//				sinfo.setFordernumber(number);
//				HashMap<String, Object> paramss =DeliversDao.ExecSave(sinfo);
//				reponse.getWriter().write(
//				JsonUtil.result(true, "保存成功", "", ""));
//				
//		
//
//		} catch (Exception e) {
//			reponse.getWriter().write(
//					JsonUtil.result(false, "保存失败!" + e.toString(), "", ""));
//		}
//		assginflag=false;
//		return null;
//	}
	
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
	
	
	@RequestMapping("/getAssginDeliversInfo")
	public String getAssginDeliversInfo(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		reponse.setCharacterEncoding("utf-8");
		try {
			String fid = request.getParameter("fid");
			String sql="select d.fid,d.fnumber,d.famount,d.fproductid as FPRODUCTDEFID,d.falloted from t_ord_delivers d where d.fid='"+fid+"'";
			List<HashMap<String,Object>> result= DeliversDao.QueryBySql(sql);
			
			if(result.get(0).get("falloted")!=null&&"1".equals(result.get(0).get("falloted")))
			{
				throw new DJException("分配失败！该要货信息已经分配");
			}
			reponse.getWriter().write(JsonUtil.result(true,"","",result));
		} catch (Exception e) {
			reponse.getWriter().write(JsonUtil.result(false, e.getMessage(), "", ""));
		}
		return null;
	}
	
	
	
	
	@RequestMapping("/getDeliverapplyHistoryList")
	public String getDeliverapplyHistoryList(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		ListResult result;
		reponse.setCharacterEncoding("utf-8");
		
		try {
			//String sql = "select c.fname as fcustname,cpdt.fname cutpdtname,cpdt.fid fcusproductid,d.fid,d.fnumber,c.fid fcustomerid,date_format(d.farrivetime,'%Y-%m-%d %T') farrivetime,d.flinkman,d.flinkphone,d.famount,d.faddress,a.fid faddressid,d.fdescription,ifnull(d.fisread,0) fisread from t_ord_cusdelivers d left join t_bd_custproduct cpdt on cpdt.fname=d.fproductname left join t_bd_customer c on c.fid=cpdt.FCUSTOMERID left join t_bd_address a on a.fname=d.faddress ";
			String sql ="select c.fname as fcustname,d.freqdate,d.fmaktx as fmaktx,d.freqaddress freqaddress,cpdt.fname cutpdtname,cpdt.fid fcusproductid,d.fid,d.fnumber,c.fid fcustomerid,date_format(d.farrivetime,'%Y-%m-%d %T') farrivetime,d.flinkman,d.flinkphone,d.famount,a.fname as faddress,a.fid faddressid,d.fdescription,ifnull(d.fisread,0) fisread from ";
			sql=sql+" t_ord_cusdelivers d left join t_bd_custproduct cpdt on cpdt.fid=d.fcusproduct left join t_bd_customer c on c.fid=d.FCUSTOMERID left join t_bd_address a on a.fid=d.faddress where fisread=1";
			result = DeliversDao.QueryFilterList(sql, request);
			reponse.getWriter().write(JsonUtil.result(true,"",result));
		} catch (DJException e) {
			reponse.getWriter().write(JsonUtil.result(false,e.getMessage(),"",""));
		}
		
		return null;
	}
	
	@RequestMapping(value = "/deliverapplyHistoryoExcel")
	public String deliverapplyHistoryoExcel(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		try {
			
			request.setAttribute("nolimit", 0);
			String sql ="select c.fname 客户名称,d.freqdate 发放配送时间 ,d.fmaktx 发放产品名称 ,d.freqaddress 发放地址,cpdt.fname 客户产品,d.fnumber 申请单号,date_format(d.farrivetime,'%Y-%m-%d %T') 配送时间,d.flinkman 发放人,d.flinkphone 联系电话,d.famount 发放数量,a.fname as 配送地址,d.fdescription 备注 ,ifnull(d.fisread,0) 读取 from ";
			sql=sql+" t_ord_cusdelivers d left join t_bd_custproduct cpdt on cpdt.fid=d.fcusproduct left join t_bd_customer c on c.fid=d.FCUSTOMERID left join t_bd_address a on a.fid=d.faddress where fisread=1";
			ListResult result;
			result = DeliversDao.QueryFilterList(sql, request);
			ExcelUtil.toexcel(reponse,result);
			
		} catch (DJException e) {
			// TODO Auto-generated catch block
			reponse.getWriter().write(
					JsonUtil.result(false, e.getMessage(), "", ""));
		}
		
		return null;
		
	}
	/**
	 * 
	 * 
	 * @param request
	 * @param reponse
	 * @return
	 * @throws IOException
	 * 
	 * @date 2014-3-12 下午1:22:04 (ZJZ)
	 */
	@RequestMapping("/selectCustomerByUser")
	public String selectCustomerByUser(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		reponse.setCharacterEncoding("utf-8");
		try {
			String userid = ((Useronline) request.getSession().getAttribute(
					"Useronline")).getFuserid();
			String sql = " SELECT tbu.FCUSTOMERID as customer, tbc.fname as customerName FROM t_bd_usercustomer tbu left join t_bd_customer tbc on tbu.FCUSTOMERID = tbc.fid where FUSERID = '%s' ";
			sql = String.format(sql, userid);
			List<HashMap<String, Object>> result = DeliversDao.QueryBySql(sql);
			if (result.size() == 1) {
				reponse.getWriter()
				.write(JsonUtil.result(true, "", "", result));
			} else {
				
				reponse.getWriter().write(JsonUtil.result(true, "", "", ""));
			}
			
		} catch (Exception e) {
			reponse.getWriter().write(
					JsonUtil.result(false, e.getMessage(), "", ""));
		}
		return null;
	}
	
	/**
	 * 
	 * 
	 * @param request
	 * @param reponse
	 * @return
	 * @throws IOException
	 * 
	 * @date 2014-3-12 下午1:22:04 (ZJZ)
	 */
	@RequestMapping("/countBalanceqtyByCusproduct")
	public String countBalanceqtyByCusproduct(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		reponse.setCharacterEncoding("utf-8");
		try {
			String cusproduct = request.getParameter("cusproduct");
			
			String sql = " SELECT sum(fbalanceqty) as balanceqty FROM t_inv_storebalance where fProductID = (SELECT FPRODUCTID FROM t_pdt_custrelationentry where FPARENTID = (SELECT FID FROM t_pdt_custrelation where FCUSTPRODUCTID = '%s')) ";
			sql = String.format(sql, cusproduct);
			List<HashMap<String, Object>> result = DeliversDao.QueryBySql(sql);
			if (result.get(0).get("balanceqty") == null) {
				result.get(0).put("balanceqty", 0);
			}
			reponse.getWriter().write(JsonUtil.result(true, "", "", result));
		} catch (Exception e) {
			reponse.getWriter().write(
					JsonUtil.result(false, e.getMessage(), "", ""));
		}
		return null;
	}
	
	/**
	 * 手动分配和自动分配的总入口
	 * @param request
	 * @param reponse
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/deliversToAllot")
	public String deliversToAllot(HttpServletRequest request,
			HttpServletResponse reponse) throws Exception {
		synchronized (this.getClass()){
		try {
			String allottype = request.getParameter("allottype");
			if(allottype!=null&&!"".equals(allottype))
			{
				if("0".equals(allottype))
					deliversAllot(request,reponse);
				else if("1".equals(allottype))
					saveAssginOrder(request,reponse);
			}
			else
			{
				throw new DJException("传参有误");
			}
			
		} catch (DJException e) {
			reponse.getWriter().write(
					JsonUtil.result(false, "保存失败!" + e.getMessage(), "", ""));
		}
		}
		
		return null;
	}
	
	
	@RequestMapping("/selectAddressByCustomer")
	public String selectAddressByCustomer(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		reponse.setCharacterEncoding("utf-8");
		try {
			String userid = ((Useronline) request.getSession().getAttribute(
					"Useronline")).getFuserid();
			int type=new Integer(request.getParameter("ftype")==null?"0":request.getParameter("ftype"));//0是查客户与地址；1是专门查地址
			List<HashMap<String, Object>> result=null,list=null;
			
			String sql ="";
			if(type==0)
			{
			 sql = " SELECT tbu.FCUSTOMERID as customer, tbc.fname as customerName FROM t_bd_usercustomer tbu left join t_bd_customer tbc on tbu.FCUSTOMERID = tbc.fid where FUSERID = '%s' ";
			sql = String.format(sql, userid);
			 result = DeliversDao.QueryBySql(sql);
			 if(result.size()==1){
			 sql="select c.fid as customer, c.fname as customerName,d.fid address,d.fname addressname,d.flinkman,d.fdetailaddress,d.fphone from t_bd_customer c left join t_bd_address d on d.fcustomerid=c.fid where d.fcustomerid='"+result.get(0).get("customer")+"'";
			  list=DeliversDao.QueryBySql(sql);
			 }
			}
			else
			{
				String fcustomerid=request.getParameter("fcustomerid");
				sql="select d.fid address,d.fname addressname,d.flinkman,d.fdetailaddress,d.fphone from  t_bd_address d where d.fcustomerid='"+fcustomerid+"'";
				 list=DeliversDao.QueryBySql(sql);
			}
			 if(list!=null&&list.size()==1)
			 {
				 result=list;
			 }
			
			if (result!=null&&result.size() == 1) {
				reponse.getWriter()
				.write(JsonUtil.result(true, "", "", result));
			} else {
				
				reponse.getWriter().write(JsonUtil.result(true, "", "", ""));
			}
			
		} catch (Exception e) {
			reponse.getWriter().write(
					JsonUtil.result(false, e.getMessage(), "", ""));
		}
		return null;
	}
	@RequestMapping("downloadFile")
	public void downloadFile(HttpServletResponse response) throws IOException{
		response.setCharacterEncoding("utf-8");
		String path = "D:\\tomcat\\要货申请导入格式.xls";
		String name = path.substring(path.lastIndexOf("\\")+1);
			InputStream in = null;
			try {
				in = new FileInputStream(path);
			} catch (FileNotFoundException e) {
				throw new DJException("此附件文件不存在，无法下载！");
			}
			response.setContentType("application/x-msdownload");
			response.addHeader("Content-Disposition", "attachment; filename=\"" + new String(name.getBytes("UTF-8"),"iso-8859-1")+ "\"");
			OutputStream out = response.getOutputStream();
			byte[] bytes = new byte[1024];
			int len = 0;
			while((len = in.read(bytes,0,1024))!=-1){
				out.write(bytes, 0, len);
			}
			out.flush();
			in.close();
	}
	
	/**
	 * 
	 * 
	 * @param request
	 * @param reponse
	 * @return
	 * @throws IOException
	 * 
	 * @date 2014-3-12 下午1:22:04 (ZJZ)
	 */
	@RequestMapping("/countBalanceqtyByCusproductandCustomer")
	public String countBalanceqtyByCusproductandCustomer(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		reponse.setCharacterEncoding("utf-8");
		try {
			String cusproduct = request.getParameter("cusproduct");
			String customerid = request.getParameter("customerid");
			String ftraitid =(StringUtils.isEmpty(request.getParameter("ftraitid"))==true?"000000000000":request.getParameter("ftraitid"));
			
			String sql="SELECT sum(fbalanceqty) as balanceqty,fproductid from t_inv_storebalance where  fProductID=( select fproductid from ( "
					+"	select  r.fproductid ,ifnull(r.fproductid,0) fistrue from t_pdt_productrelationentry e  left join t_pdt_productrelation r on e.fparentid=r.fid  	left join  t_pdt_productdef f on f.fid=r.fproductid   	left join t_pdt_productrelationentry e2 on e2.fparentid=r.fid  "
					+"	where ifnull(f.fishistory,0)=0 and ifnull(f.feffect,0)=1 and  e.fcustproductid='%s' and r.fcustomerid='%s'	group by  r.fid having count(e2.fid) =1 LIMIT 0,1 "
					+"	union select e.fproductid,0 fistrue from  t_pdt_custrelation  r left join t_pdt_custrelationentry e on e.fparentid=r.fid left join  t_pdt_productdef  d on d.fid=e.fproductid"
					+"	 where r.fcustproductid='%s' and r.fcustomerid='%s'  and ifnull(d.fishistory,0)=0 and ifnull(d.feffect,0)=1	LIMIT 0,1 ) s 	order by fproductid,fistrue ) or  ftraitid='%s' ";
			sql = String.format(sql, cusproduct,customerid,cusproduct,customerid,ftraitid);
			List<HashMap<String, Object>> result = DeliversDao.QueryBySql(sql);
			
			if (result.get(0).get("balanceqty") == null) {
				result.get(0).put("balanceqty", 0);
			}else{
				int balanceqty = Integer.valueOf(result.get(0).get("balanceqty")+"");
				if(balanceqty!=0){
					sql = "select sum(fusedqty) fusedqty from t_inv_usedstorebalance where fproductid='"+result.get(0).get("fproductid")+"'";
					Object val = ((HashMap<String, Object>)DeliversDao.QueryBySql(sql).get(0)).get("fusedqty");
					if(val!=null){
					int	fusedqty = Integer.valueOf(val+"");
						if(fusedqty<0){
							fusedqty = 0 ;
						}
						result.get(0).put("balanceqty",(balanceqty-fusedqty)<0?0:balanceqty-fusedqty);
					}
				}
				
			}
			reponse.getWriter().write(JsonUtil.result(true, "", "", result));
		} catch (Exception e) {
			reponse.getWriter().write(
					JsonUtil.result(false, e.getMessage(), "", ""));
		}
		return null;
	}
	
	public static void toexcel(HttpServletResponse response, ListResult result,
			List<String> order) throws DJException {
		response.setContentType("multipart/form-data");
		try {
			response.setHeader("Content-Disposition", "attachment;fileName="+ new String("要货申请导入".getBytes("UTF-8"),"iso-8859-1")+ ".xls");
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		WritableWorkbook book = null;
		try {
			book = Workbook.createWorkbook(response.getOutputStream());
			WritableSheet sheet = book.createSheet("数据", 0);
			
			List<HashMap<String, Object>> list = result.getData();
			
			Set<String> columns = list.get(0).keySet();
			
			WritableFont bold = new WritableFont(WritableFont.createFont("宋体"),18,WritableFont.BOLD);
			WritableCellFormat headerFormat = new WritableCellFormat(
					bold);
			headerFormat.setAlignment(Alignment.CENTRE);
			sheet.mergeCells(0,0, columns.size(),0);
			//sheet.addCell(new Label(0, 0, "aa",headerFormat));
			
			for (String name : columns) {
				sheet.addCell(new Label(0, 1, "序号",new WritableCellFormat(new WritableFont(WritableFont.createFont("宋体"),14))));
				Label label = new Label(order.indexOf(name)+1, 1, name,new WritableCellFormat(new WritableFont(WritableFont.createFont("宋体"),14)));
				sheet.addCell(label);

			}
			int rowNum = 2;
			int number = 0;
			int columnWidth = 0;
			BigDecimal famount = BigDecimal.ZERO;
			BigDecimal fprices = BigDecimal.ZERO;
			for (HashMap<String, Object> data : list) {
				sheet.addCell(new Label(0, rowNum,String.valueOf(++number)));
				for (String name : columns) {
					sheet.setColumnView(++columnWidth, 16);
					Label label = new Label(order.indexOf(name)+1, rowNum,
							data.get(name) == null ? "" : data.get(name)
									.toString(),new WritableCellFormat(new WritableFont(WritableFont.createFont("宋体"),12)));
					sheet.addCell(label);
				}
				rowNum++;
			}
			book.write();
		} catch (IOException | WriteException e) {
			throw new DJException("导出失败!");
		} finally {
			try {
				book.close();
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
	}
	@RequestMapping("/getSupplierByCustomer")
	public String getSupplierByCustomer(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		try {
			String fcustomerid = request.getParameter("fcustomerid");
			String SQL = "select distinct e1.fid,e1.fname,e1.fnumber from t_pdt_productreqallocationrules e"
					+" left join t_sys_supplier e1 on e.fsupplierid=e1.fid where 1=1 and e.fcustomerid='"+fcustomerid+"'";
			List<HashMap<String, Object>> result = DeliversDao.QueryBySql(SQL);
			response.getWriter().write(JsonUtil.result(true, "","", result));
		} catch (DJException e) {
			response.getWriter().write(JsonUtil.result(false,e.getMessage(),"",""));
		}
		return null;
	}
	@RequestMapping("/updateCusDelivers")
	public void updateCusDelivers(HttpServletRequest request,HttpServletResponse response) throws IOException{
		try {
			String fid = request.getParameter("fid");
			fid = fid.replace(",", "','");
			String fsupplierid = request.getParameter("fsupplierid");
			String sql = "update t_ord_cusdelivers set fsupplierid='"+fsupplierid+"' where fid in ('"+fid+"')";
			DeliversDao.ExecBySql(sql);
			response.getWriter().write(JsonUtil.result(true, "修改成功","", ""));
		} catch (DJException e) {
			// TODO: handle exception
			response.getWriter().write(JsonUtil.result(true, "修改失败","", ""));
		}
	}
}
	
