package Com.Controller.order;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.biff.StringHelper;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.util.StringUtil;
import org.apache.tomcat.util.http.fileupload.FileItem;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItemFactory;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.dongjing.api.DongjingClient;
import com.sun.xml.internal.ws.message.StringHeader;

import Com.Base.Util.DJException;
import Com.Base.Util.JsonUtil;
import Com.Base.Util.ListResult;
import Com.Base.Util.ServerContext;
import Com.Base.Util.UploadFile;
import Com.Base.Util.params;
import Com.Dao.order.IFirstproductdemandDao;
import Com.Dao.order.IProductdemandfileDao;
import Com.Dao.order.IProductstructureDao;
import Com.Dao.order.ISaleOrderDao;
import Com.Dao.order.ISchemeDesignDao;
import Com.Entity.System.Custproduct;
import Com.Entity.System.Productdef;
import Com.Entity.System.ProductdefProducts;
import Com.Entity.System.Productrelation;
import Com.Entity.System.Productrelationentry;
import Com.Entity.System.Supplier;
import Com.Entity.System.SysUser;
import Com.Entity.System.Useronline;
import Com.Entity.order.Productstructure;
import Com.Entity.order.SchemeDesignEntry;
import Com.Entity.order.Schemedesign;

@Controller
public class SchemeDesignController {
	public static final String RELATIVE_PATH = File.separatorChar+"file"+File.separatorChar+"schemedesign";
	@Resource
	private ISaleOrderDao saleOrderDao;
	@Resource
	private IProductdemandfileDao productdemandfileDao;
	@Resource
	private ISchemeDesignDao SchemeDesignDao;
	@Resource
	private IProductstructureDao ProductstructureDao;
	@Resource
	private IFirstproductdemandDao firstproductdemandDao;
	@RequestMapping("getSchemeDesignList")
	public void getSchemeDesignList(HttpServletRequest request,HttpServletResponse response) throws IOException{
		String firstproductid = request.getParameter("firstproductid");		
		String sql = "SELECT  ifnull(uu.fqq,'') fqq,u3.fname  fauditorid,s.faudited,sc.fid sfid,s.fgroupid,u2.ftype utype,u2.fname coname,s.fid,s.fcreatorid,u1.fname fcreator,s.fcreatetime,s.fname,s.fnumber,replace(replace(replace(ifnull(s.fdescription,''),'\\r\\n','<br/>'),'\\r','<br/>'),'\\n','<br/>') fdescription,s.ffirstproductid,s.fcustomerid,c.fname fcustomer,s.fsupplierid,sp.fname fsupplier,s.fconfirmed,s.fconfirmer,s.fconfirmtime FROM t_ord_schemedesign s "
				+" LEFT JOIN t_sys_user u1 ON u1.fid=s.fcreatorid LEFT JOIN (SELECT fid,fparentid FROM `t_ord_schemedesignentry` GROUP BY fparentid) sc  ON s.fid=sc.`fparentid` left join t_sys_user u3 on s.fauditorid=u3.fid"
				+" LEFT JOIN t_bd_customer c ON c.fid=s.fcustomerid LEFT JOIN t_sys_supplier sp ON sp.fid=s.fsupplierid left join t_sys_user u2 on u2.fid = s.fconfirmer left join t_ord_firstproductdemand f on s.ffirstproductid=f.fid left join t_sys_user uu on uu.fid=f.fcreatid  where 1=1 " ;
				sql = sql + SchemeDesignDao.QueryFilterByUser(request,"s.fcustomerid","s.fsupplierid");
		try {
			ListResult list = SchemeDesignDao.QueryFilterList(sql, request);
			response.getWriter().write(JsonUtil.result(true, "", list));
		} catch (DJException e) {
			response.getWriter().write(JsonUtil.result(false, e.getMessage(), "",""));
		}
	}
	
	//方案下单List查询已确认的数据,且必须有特性;
	@RequestMapping("getSchemeDesignOrderList")
	public void getSchemeDesignOrderList(HttpServletRequest request,HttpServletResponse response) throws IOException{
		request.setAttribute("djsort","s.fcreatetime desc");
		String sql = " SELECT s.fid,s.fcreatorid,u1.fname fcreator,s.fcreatetime,s.fname,s.fnumber,s.fdescription,s.ffirstproductid,s.fcustomerid,c.fname fcustomer,s.fsupplierid,sp.fname fsupplier,s.fconfirmed,s.fconfirmer,s.fconfirmtime,s.fordered FROM t_ord_schemedesign s "
				+" LEFT JOIN t_sys_user u1 ON u1.fid=s.fcreatorid"
				+" LEFT JOIN t_bd_customer c ON c.fid=s.fcustomerid LEFT JOIN t_sys_supplier sp ON sp.fid=s.fsupplierid where s.fconfirmed=1 and exists(select 1 from t_ord_schemedesignentry where fparentid=s.fid)";
				sql = sql + SchemeDesignDao.QueryFilterByUser(request,null,"s.fsupplierid") ;
		try {
			ListResult list = SchemeDesignDao.QueryFilterList(sql, request);
			response.getWriter().write(JsonUtil.result(true, "", list));
		} catch (DJException e) {
			response.getWriter().write(JsonUtil.result(false, e.getMessage(), "",""));
		}
	}
	
	@RequestMapping("DelSchemeDesignList")
	public void DelSchemeDesignList(HttpServletRequest request,HttpServletResponse response) throws IOException{
		response.setCharacterEncoding("utf-8");
		String fid =  request.getParameter("fidcls");
		try {
			SchemeDesignDao.DelSchemeDesign(fid);
			response.getWriter().write(JsonUtil.result(true, "删除成功", "", ""));
		} catch (DJException e) {
			response.getWriter().write(JsonUtil.result(false, e.getMessage(), "", ""));
		}
	}
	@RequestMapping("SaveOrupdateSchemeDesign")
	public void SaveOrupdateSchemeDesign(HttpServletRequest request,HttpServletResponse response) throws IOException, ParseException{
		String userid = ((Useronline) request.getSession().getAttribute("Useronline")).getFuserid();
		Schemedesign f = (Schemedesign)request.getAttribute("Schemedesign");
		List<Productstructure> productList =  (List<Productstructure>) request.getAttribute("Productstructure");
		List<SchemeDesignEntry> entryList = (List<SchemeDesignEntry>) request.getAttribute("SchemeDesignEntry");
		if(f==null){
			response.getWriter().write(JsonUtil.result(false, "保存失败，数据异常！", "", ""));
			return;
		}
		try {
			if(firstproductdemandDao.closeProductDemand(f.getFfirstproductid(), SchemeDesignDao)){
				throw new DJException("已关闭需求不能新增方案！");
			}
			
			if(SchemeDesignDao.QueryExistsBySql("select 1 from t_ord_firstproductdemand where ifnull(freceived,0)=0  and fid = '" + f.getFfirstproductid()+"' ")){
				response.getWriter().write(JsonUtil.result(false, "保存失败，未接收不能新增！", "", ""));
				return;
			}
			if(SchemeDesignDao.QueryExistsBySql("select 1 from t_ord_SchemeDesign where fname = '"+f.getFname()+"' and fid != '"+f.getFid()+"'")){
				throw new DJException("此方案名称已存在，请重新填写方案名称！");
			}
			if(!SchemeDesignDao.QueryExistsBySql("select 1 from t_ord_SchemeDesign where fid = '"+f.getFid()+"'")){
				f.setFcreatorid(userid);
				f.setFcreatetime(new Date());
			}else if(SchemeDesignDao.QueryExistsBySql("select 1 from t_ord_SchemeDesign where fconfirmed = 1 and fid = '"+f.getFid()+"' ")){
					response.getWriter().write(JsonUtil.result(false, "保存失败，已确认的方案设计不能修改！", "", ""));
					return;
			}
			if(entryList.size()>0&&productList.size()>0){
				throw new DJException("一个方案不能同时新增特性和产品！");
			}
//			if(entryList.size()==0&&productList.size()==0){
//				throw new DJException("请填写特性或产品！");
//			}
			
			SchemeDesignDao.saveSchemeDesign(f,entryList,productList);
			
			
			
			response.getWriter().write(JsonUtil.result(true,"保存成功","",""));
		} catch (DJException e) {
			response.getWriter().write(JsonUtil.result(false,e.getMessage(),"",""));
		}
	}
	/*
	 * 方案设计下单
	 */
	@RequestMapping("SchemeDesignOrder")
	public void SchemeDesignOrder(HttpServletRequest request,HttpServletResponse response) throws IOException, ParseException{
		try {
			String fid = request.getParameter("fid");
			if(SchemeDesignDao.QueryExistsBySql("select 1 from t_ord_productplan where fschemedesignid = '"+ fid +"'")){
				throw new DJException("该方案设计已经下单，不能重复下单！");
			}
			Schemedesign sdinfo = (Schemedesign) SchemeDesignDao.QueryByHql("from Schemedesign where fid = '"+ fid +"'").get(0);
			if(sdinfo.getFconfirmed()==0){
				throw new DJException("该方案未确认，不能下单！");
			}
			ArrayList<SchemeDesignEntry> sdentry = (ArrayList<SchemeDesignEntry>) SchemeDesignDao.QueryByHql("from SchemeDesignEntry where fparentid = '"+ fid +"'");
			ArrayList<Productdef> productdefCol = (ArrayList<Productdef>)SchemeDesignDao.QueryByHql("from Productdef where schemedesignid = '"+ fid +"'");
			Productdef productdefinfo = null;
			if(productdefCol.size()>0){
				productdefinfo = (Productdef)productdefCol.get(0);
			}
			
//			if(SchemeDesignDao.QueryExistsBySql("select 1 from t_ord_SchemeDesign where fconfirmed = 0 and fid = '"+sdinfo.getFid()+"' ")){
//				response.getWriter().write(JsonUtil.result(false, "方案设计下单失败，未确认的方案设计不能下单！", "", ""));
//				return;
//			}
			
			SchemeDesignDao.ExecSDOrder(request,sdinfo,sdentry,productdefinfo);
			
			response.getWriter().write(JsonUtil.result(true,"方案设计下单成功","",""));
		} catch (DJException e) {
			response.getWriter().write(JsonUtil.result(false,e.getMessage(),"",""));
		}
	}
	
	@RequestMapping("cancelSchemeDesignOrder")
	public String cancelSchemeDesignOrder(HttpServletRequest request,HttpServletResponse response) throws IOException{
		String fid = request.getParameter("fid");
		try {
			SchemeDesignDao.ExecCancelSchemeDesignOrder(fid);
			response.getWriter().write(JsonUtil.result(true, "操作成功！", "",""));
		} catch (DJException e) {
			response.getWriter().write(JsonUtil.result(false, e.getMessage(), "",""));
		}
		return null;
	}
	/**
	 * 获取方案设计信息
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("getSchemeDesignInfo")
	public void getSchemeDesignInfo(HttpServletRequest request,HttpServletResponse response) throws IOException{
		String fid = request.getParameter("fid");
		try {
			String sql = "SELECT s.fgroupid,s.ftemplateproduct,s.ftemplatenumber,s.fid,s.fcreatorid,u1.fname fcreator,s.fcreatetime,s.fname,s.fnumber,replace(replace(replace(REPLACE(ifnull(s.fdescription,''),'\"','\\\\\"'),'\\r\\n','<br/>'),'\\r','<br/>'),'\\n','<br/>') fdescription,s.ffirstproductid,s.fconfirmed,s.fconfirmer,s.fconfirmtime,s.fcustomerid,s.fsupplierid,s.foverdate,s.fboxlength,s.fboxwidth,s.fboxheight,s.fmaterial,s.ftilemodel,s.famount FROM t_ord_schemedesign s LEFT JOIN t_sys_user u1 ON u1.fid=s.fcreatorid where s.fid ='"+fid+"'";
			List<HashMap<String, Object>> list = SchemeDesignDao.QueryBySql(sql);
			response.getWriter().write(JsonUtil.result(true,"","", list));
		} catch (DJException e) {
			response.getWriter().write(JsonUtil.result(false,e.getMessage(),"",""));
		}
		
	}
	
	/**
	 * 查询已分配的产品需求;
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("getAllotedFirstproductList")
	public void getAllotedFirstproductList(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		request.setAttribute("djsort", "fauditortime desc");
		String sql = "select * from (SELECT u1.fqq,f.fgroupid,f.fstate,f.fid,f.fname,c.fname cname,f.fnumber,u1.fname AS fcreatid,f.fcreatetime,"
				+ "u2.fname AS freceiver,ifnull(u2.ftel,'') AS freceiverTel,f.freceivetime,u3.fname AS fauditorid,"
				+ "f.fauditortime,date_format(f.foverdate,'%Y-%m-%d %H:%i') foverdate,"
				+ "f.fcostneed,f.fiszhiyang,"
				+ "replace(replace(replace(REPLACE(ifnull(f.fdescription,''),'\"','\\\"'),'\\r\\n','<br/>'),'\\r','<br/>'),'\\n','<br/>') fdescription,"
				+ "f.isfauditor,f.freceived,f.falloted,f.fcustomerid,f.fsupplierid,"
				+ "ifnull(f.flinkphone,'') flinkphone,ifnull(f.flinkman,'') flinkman,fisaccessory,if(fisaccessory=true,'是','否') isaccessory,ifnull(s.count,0) count,fisdemandpackage "
				+ "FROM t_ord_firstproductdemand f "
				+ "LEFT JOIN t_sys_user u1 ON u1.fid=f.fcreatid "
				+ "LEFT JOIN t_bd_customer c ON f.fcustomerid = c.fid "
				+ "LEFT JOIN t_sys_user u2 ON u2.fid=f.freceiver "
				+ "LEFT JOIN t_sys_user u3 ON u3.fid=f.fauditorid "
				+ "left join (select count(1) count,sum(fconfirmed),ffirstproductid from t_ord_schemedesign group by ffirstproductid having count(1)=sum(fconfirmed)"
				+ ") s on f.fid=s.ffirstproductid) a "
				+ "where isfauditor = 1 "
				+ SchemeDesignDao.QueryFilterByUser(request, null,
						"fsupplierid");

		try {
			ListResult list = SchemeDesignDao.QueryFilterList(sql, request);
			response.getWriter().write(JsonUtil.result(true, "", list));
			request.getSession().setAttribute("AllotedFirstproductListQuery", sql);
		} catch (DJException e) {
			response.getWriter().write(
					JsonUtil.result(false, e.getMessage(), "", ""));
		}
	}
	
	/**
	 * 接收
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("receiveProductdemand")
	public void receiveProductdemand(HttpServletRequest request,HttpServletResponse response) throws IOException{
		response.setCharacterEncoding("utf-8");
		String fids = request.getParameter("FresultID");
		String userid =  request.getParameter("FdesignerID");//((Useronline) request.getSession().getAttribute("Useronline")).getFuserid();
		String fsupplierid =  request.getParameter("fsupplierid");
		try {
//			SysUser uinfo=(SysUser)firstproductdemandDao.Query(SysUser.class, userid);
//			if(StringUtils.isEmpty(uinfo.getFqq()))
//			{
//				throw new DJException("请先在用户中心维护QQ信息(企业QQ)");
//			}
			String productDemandids  = fids.substring(1, fids.length()-1).replace("'","");
			String[] productDemandid = productDemandids.split(",");
			for(String fid : productDemandid){
				if(firstproductdemandDao.closeProductDemand(fid,SchemeDesignDao)){
					throw new DJException("该需求已经关闭,不能接收！");
				}
			}
			
//			String sql = "update t_ord_firstproductdemand set fstate='已接收',freceived=1,freceivetime= now(),freceiver='"+userid+"'"+" where fid in "+fids;
//			SchemeDesignDao.ExecBySql(sql);
			SchemeDesignDao.ExecreceiveProductdemand(userid, fids,fsupplierid);
			
			response.getWriter().write(JsonUtil.result(true,"成功","",""));
			
		} catch (DJException e) {
			response.getWriter().write(JsonUtil.result(false,e.getMessage(),"",""));
		}
	}
	/**
	 * 取消接收
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("UnReceiveProductdemand")
	public void UnReceiveProductdemand(HttpServletRequest request,HttpServletResponse response) throws IOException{
		response.setCharacterEncoding("utf-8");
		String fids = request.getParameter("fids");
		String userid = ((Useronline) request.getSession().getAttribute(
				"Useronline")).getFuserid();
		try {
			String productDemandids  = fids.substring(1, fids.length()-1).replace("'","");
			String[] productDemandid = productDemandids.split(",");
			for(String fid : productDemandid){
				if(firstproductdemandDao.closeProductDemand(fid,SchemeDesignDao)){
					throw new DJException("该需求已经关闭,不能取消接收！");
				}
			}
			int isExit=0;//是否存在设计方案
			String sql = "select 1 from t_ord_schemedesign where ffirstproductid in "+fids; 
			if(SchemeDesignDao.QueryExistsBySql(sql)){
				 isExit=1;
				sql="select 1 from t_ord_schemedesign where ifnull(fconfirmed,0)=1 and ffirstproductid in "+fids;
				if(SchemeDesignDao.QueryExistsBySql(sql))
				{
					throw new DJException("存在已确认的方案设计,不能取消接收！");
				}
					
			}
			
//			ifnull(fconfirmed
//			sql = "update t_ord_firstproductdemand set fstate='已分配',freceived=0,freceivetime= null,freceiver=null where fstate='已接收' and fid in "+fids;
//			SchemeDesignDao.ExecBySql(sql);
			SchemeDesignDao.ExecUnReceiveProductdemand(fids,isExit);
			response.getWriter().write(JsonUtil.result(true,"成功","",""));
		} catch (DJException e) {
			response.getWriter().write(JsonUtil.result(false,e.getMessage(),"",""));
		}
	}
	/**
	 * 上传附件
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 * @throws FileUploadException
	 */
	@RequestMapping("uploadSchemedesignFile")
	public String uploadSchemedesignFile(HttpServletRequest request,HttpServletResponse response) throws IOException, FileUploadException{
		String fparentid = request.getParameter("fparentid");
		if(SchemeDesignDao.QueryExistsBySql("select 1 from t_ord_SchemeDesign where fconfirmed = 1 and fid = '"+fparentid+"' ")){
			response.getWriter().write(JsonUtil.result(false, "已确认的方案设计不能上传附件！", "", ""));
			return null;
		}
		String ftype = new String(request.getParameter("ftype").getBytes("ISO-8859-1"),"UTF-8");
		String fdescription = new String(request.getParameter("fdescription").getBytes("ISO-8859-1"),"UTF-8");
//		String fname = "";
//		fname = new String(request.getParameter("fname").getBytes("ISO-8859-1"),"UTF-8");
		String msg = null;
//		String fpath = request.getServletContext().getRealPath("/file/Maigao");
		String fpath = request.getServletContext().getRealPath(RELATIVE_PATH).replace("\\", "/");
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("fparentid", fparentid);
		map.put("ftype", ftype);
		map.put("fdescription", fdescription);
		map.put("fpath", fpath);
//		map.put("fname", fname);
		
		try {
			
//			ServletFileUpload upload = new ServletFileUpload(new DiskFileItemFactory()); // 创建一个新的文件上传对象
//			upload.setHeaderEncoding("utf-8");//避免中文名乱码
//			List<FileItem> list  = upload.parseRequest(request);
//			SchemeDesignDao.saveschemedesignFile(list,map);
			UploadFile.upload(request, map);
			msg = "附件上传成功！";
		} catch (DJException e) {
			msg =  e.getMessage();
		}
		response.getWriter().write(JsonUtil.result(true,msg, "",""));
		return null;
	}
//	@RequestMapping("getMSaleOrderList")
//	public String getMSaleOrderList(HttpServletRequest request,HttpServletResponse response) throws IOException{
//		try {
//			
//			String sql = "select ELT(fstate+1,'正常','出图接收','客户确认') fstate,m.fid fid,m.fnumber fnumber,cp.fname fcusproductname,c.fname fcustomername,fboxlength,fboxwidth,fboxheight,fmateriallength,fmaterialwidth,flimitlength,flimitwidth,fboardlength,fboardwidth,fhstaveexp,fvstaveexp,fhformula,fvformula,fvolume,farea,farrivetime from t_ord_msaleorder m left join t_bd_custproduct cp on m.fcusproductid=cp.fid left join t_bd_customer c on m.fcustomerid=c.fid where 1=1"+saleOrderDao.QueryFilterByUser(request,"m.fcustomerid","null");
//			ListResult sList = saleOrderDao.QueryFilterList(sql, request);
//			request.getSession().setAttribute("Export_MSaleOrder", sList);
//			response.getWriter().write(JsonUtil.result(true, "", sList));
//		} catch (DJException e) {
//			response.getWriter().write(JsonUtil.result(false, e.getMessage(), "",""));
//		}
//		return null;
//	}
	
	/**
	 * 获取附件目录
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("getSDProductdemandfileList")
	public void getSDProductdemandfileList(HttpServletRequest request,HttpServletResponse response) throws IOException{
		String parentid = request.getParameter("fid");
		String sql = "select fid,fname,fpath,fparentid,ftype,fdescription from t_ord_Productdemandfile ";
		if(parentid!=null && !parentid.equals("")){
			sql = sql + "where fparentid='"+parentid+"' ";
		}else{
			sql = sql + "where 1=1 ";
		}
		try {
			ListResult list = productdemandfileDao.QueryFilterList(sql, request);
			if(list.getData().size()>0){
				
				response.getWriter().write(JsonUtil.result(true, "", list));
			}else{
				response.getWriter().write(JsonUtil.result(false, "错误", "", ""));
			}
		} catch (DJException e) {
			response.getWriter().write(JsonUtil.result(false, e.getMessage(), "", ""));
		}
	}
	
	/**
	 * 下载附件
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 * @throws FileUploadException
	 */
	@RequestMapping("downloadSchemeDesignFile")
	public String downloadSchemeDesignFile(HttpServletRequest request,HttpServletResponse response) throws IOException, FileUploadException{
		String fid = request.getParameter("fid");
		try {
//			productdemandfileDao.downloadProductdemandFile(response,fid);
			UploadFile.download(response, fid);
		} catch (DJException e) {
			response.getOutputStream().write(e.getMessage().getBytes("UTF-8"));
		}
		return null;
	}
	/**
	 * 删除附件
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("delSchemeDesignfile")
	public void delSchemeDesignfile(HttpServletRequest request,HttpServletResponse response) throws IOException{
		String fid = request.getParameter("fid");
		String fpath = request.getParameter("fpath");
		String fparentid = request.getParameter("fparentid");
		try {
			if(SchemeDesignDao.QueryExistsBySql("select 1 from t_ord_SchemeDesign where fconfirmed = 1 and fid = '"+fparentid+"' ")){
				throw new DJException("已确认的方案设计不能删除附件！");
			}
//			File file = null;
//			file = new File(fpath);
//			file.delete();
//			String sql = "delete from  t_ord_productdemandfile  where fid = '"+fid+"'";
//			productdemandfileDao.ExecBySql(sql);
			UploadFile.delFile(fid);
			response.getWriter().write(JsonUtil.result(true, "删除成功","",""));
		} catch (DJException e) {
			response.getOutputStream().write(e.getMessage().getBytes());
		}
	}
	
	/**
	 *  获取主键ID
	 *  获取编码
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("getSDFidFnumber")
	public void getSDFidFnumber(HttpServletRequest request,HttpServletResponse response) throws IOException{
		Map map = new HashMap();
		List list = new ArrayList();
		map.put("fid", SchemeDesignDao.CreateUUid());
		map.put("fnumber", ServerContext.getNumberHelper().getNumber("t_ord_SchemeDesign", "SD", 3, false));
		list.add(map);
		response.getWriter().write(JsonUtil.result(true, "", "",list));
	}
	
	/**
	 *  获取设计师下拉数据
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("GetDesignerlist")
	public void GetDesignerlist(HttpServletRequest request,HttpServletResponse response) throws IOException{
		String sql = "select fid,fname,femail,ftel,fqq from t_sys_user where ftype = 3  ";
		try {
			ListResult list = productdemandfileDao.QueryFilterList(sql, request);
			if(list.getData().size()>0){
				
				response.getWriter().write(JsonUtil.result(true, "", list));
			}else{
				response.getWriter().write(JsonUtil.result(false, "错误", "", ""));
			}
		} catch (DJException e) {
			response.getWriter().write(JsonUtil.result(false, e.getMessage(), "", ""));
		}
	}
	
	/**
	 * 查询方案设计关联的产品
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("getSchemeDesignProducts")
	public String getSchemeDesignProducts(HttpServletRequest request,HttpServletResponse response) throws IOException{
//		String schemedesignid = request.getParameter("schemedesignid");
		try {
			String sql = "SELECT p.fid,p.fname,p.fnumber,p.fversion,p.fcustomerid,p.fsupplierid,p.fmaterialcodeid,p.fboxlength,p.fboxwidth,p.fboxheight,c.fname fcustomername,p.subProductAmount "+
					"FROM t_ord_productstructure p LEFT JOIN t_bd_customer c ON c.fid = p.fcustomerid where 1 = 1 ";
			ListResult result = SchemeDesignDao.QueryFilterList(sql, request);
			List<HashMap<String, Object>> list = result.getData();
			if(list.size()!=0){
				sql = "SELECT p.fid,p.fname,p.fnumber,p.fversion,p.fcustomerid,p.fsupplierid,p.fmaterialcodeid,p.fboxlength,p.fboxwidth,p.fboxheight,p.subProductAmount,c.fname fcustomername "+
						"FROM t_ord_productstructure p LEFT JOIN t_bd_customer c ON c.fid = p.fcustomerid where 1=1 AND p.fparentid='"+list.get(0).get("fid")+"'";
				List<HashMap<String, Object>> subProducts = SchemeDesignDao.QueryBySql(sql);
				list.addAll(subProducts);
			}
			response.getWriter().write(JsonUtil.result(true, "", "", list));
		} catch (DJException e) {
			response.getWriter().write(JsonUtil.result(false, e.getMessage(), "",""));
		}
		return null;
	}
	/**
	 * 获取方案设计分录
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("getSchemeDesignEntry")
	public String getSchemeDesignEntry(HttpServletRequest request,HttpServletResponse response) throws IOException{
		String sql = "select fid,fpurchasenumber,fentryamount,fcharacter from t_ord_schemedesignentry";
		try {
			ListResult list = SchemeDesignDao.QueryFilterList(sql, request);
			response.getWriter().write(JsonUtil.result(true, "", list));
		} catch (DJException e) {
			response.getWriter().write(JsonUtil.result(false, e.getMessage(), "", ""));
		}
		return null;
	}
	/**
	 * 查询产品需求关联的客户
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("getCustomerIdByFirstProduct")
	public String getCustomerIdByFirstProduct(HttpServletRequest request,HttpServletResponse response) throws IOException{
		String fid = request.getParameter("fid");
		String sql = "select fcustomerid,e1.fname fcustomername from t_ord_firstproductdemand e left join t_bd_customer e1 on e.fcustomerid=e1.fid where e.fid = :fid";
		params p = new params();
		p.put("fid", fid);
		try {
			List<HashMap<String, Object>> list = SchemeDesignDao.QueryBySql(sql, p);
			if(list.size()==0){
				throw new DJException("产品需求ID有误");
			}
			response.getWriter().write(JsonUtil.result(true, "", "",list));
		} catch (DJException e) {
			response.getWriter().write(JsonUtil.result(false, e.getMessage(), "",""));
		}
		return null;
	}
	/**
	 * 查询产品是否已在要货申请中使用，或在安全库存中存在
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("productIsUsedBySupplier")
	public String productIsUsedBySupplier(HttpServletRequest request,HttpServletResponse response) throws IOException{
		String fid = request.getParameter("fid");//产品id
		String custProducts = null;
		String sql,wh;
		try {
			sql = "select group_concat(quote(fproductid)) products from t_pdt_productdefproducts where fparentid = :fid";
			params p = new params();
			p.put("fid", fid);
			List<HashMap<String, Object>> list = SchemeDesignDao.QueryBySql(sql, p);
			if(list.get(0).get("products")==null){	//没有子产品
				wh = " ='"+fid+"'";
			}else{
				wh = " in ("+list.get(0).get("products")+",'"+fid+"')";
			}
			sql = "select e.fcustproductid from t_pdt_productrelationentry e left join t_pdt_productrelation e1 on e.fparentid=e1.fid where e1.fproductid"+wh;
			sql = "select group_concat(quote(fcustproductid)) cs from ("+sql+") u";
			list = SchemeDesignDao.QueryBySql(sql);
			if(list.get(0).get("cs")!=null){ //null表示没有关联客户产品
				custProducts = list.get(0).get("cs").toString();
				sql = "select 1 from t_ord_deliverapply_card_mv where fcusproductid in ("+custProducts+")";
				if(SchemeDesignDao.QueryExistsBySql(sql)){
					throw new DJException("已有产品已在要货申请中使用，不能删除！");
				}
			}
			sql = "select 1 from t_pdt_vmiproductparam where fproductid "+wh;
			if(SchemeDesignDao.QueryExistsBySql(sql)){
				throw new DJException("已有产品在安全库存中存在，不能删除！");
			}
			response.getWriter().write("{success:true}");
		} catch (DJException e) {
			response.getWriter().write(JsonUtil.result(false, e.getMessage(), "", ""));
		}
		return null;
	}
	@RequestMapping("AffirmSchemeDesign")
	public void AffirmSchemeDesign(HttpServletRequest request,HttpServletResponse response) throws IOException{
		synchronized (this.getClass()){
		String userid = ((Useronline) request.getSession().getAttribute(
				"Useronline")).getFuserid();
		String sql = null;
		String fids = request.getParameter("fids");
		String[] fid = fids.split(",");
		String  ischaracter = "false";
		int num = 0;
		try {
			sql = "select ffirstproductid from t_ord_schemedesign where fid in("+fids+")";
			List<HashMap<String,Object>> l = SchemeDesignDao.QueryBySql(sql);
			for(HashMap<String, Object> productid : l){
				if(firstproductdemandDao.closeProductDemand(productid.get("ffirstproductid").toString(), SchemeDesignDao)){
					throw new DJException("你选择的方案对应的需求已关闭,不能操作！");
				}
			}
		
			sql = "select 1 from t_ord_schemedesign where fconfirmed =1 and fid in ("+fids+")";
			List list = SchemeDesignDao.QueryBySql(sql);
			
			if(list.size()>0){
				throw new DJException("你选择的方案有已确认的,不能再次确认！");
			}
			
			for(int i =0;i<fid.length;i++){
				sql = "select * from t_ord_productstructure where schemedesignid = "+fid[i];
				List<HashMap<String,Object>> productlist = SchemeDesignDao.QueryBySql(sql);//查产品信息
				if(productlist.size()!=0){
					sql = "SELECT  * FROM t_ord_productstructure  where 1=1 AND fparentid='"+productlist.get(0).get("fid")+"'";
					List<HashMap<String,Object>> subProducts = SchemeDesignDao.QueryBySql(sql);
					productlist.addAll(subProducts);
				}
				
				sql = "select * from t_ord_productdemandfile where fparentid = "+fid[i];
				List<HashMap<String,Object>> filelist = SchemeDesignDao.QueryBySql(sql);
				
				
				
				sql = "select * from t_ord_schemedesignentry where fparentid = "+fid[i];
				List<HashMap<String,Object>> entrylist = SchemeDesignDao.QueryBySql(sql);//查特性信息
				if(entrylist.size()>0){
					num++;
					if(num==fid.length){
						ischaracter = "true";
					}
				}
				if(entrylist.size()>0&&productlist.size()>0){
					throw new DJException("一个方案不能同时新增特性和产品！");
				}
//				if(entrylist.size()==0&&productlist.size()==0){
//					throw new DJException("请填写特性或产品！");
//				}
				//成成产品和客户产品,产品关联表等  并生成生成订单,制造商订单,库存信息
				ProductstructureDao.ExecaffirmSchemeDesign(request,fid[i],userid,productlist,entrylist,filelist);
				
		
			
			}

			response.getWriter().write(JsonUtil.result(true,"确认成功!", ischaracter, ""));
		} catch (DJException e) {
			// TODO: handle exception
			response.getWriter().write(JsonUtil.result(false,e.getMessage(), "", ""));
		}
		}
	}
	
	@RequestMapping("UnAffirmSchemeDesign")
	public void UnAffirmSchemeDesign(HttpServletRequest request,HttpServletResponse response) throws IOException{
		String fids = request.getParameter("fids");
		String[] fid = fids.split(",");
		try {
			String sql = "select ffirstproductid from t_ord_schemedesign where fid in("+fids+")";
			List<HashMap<String,Object>> l = SchemeDesignDao.QueryBySql(sql);
			for(HashMap<String, Object> productid : l){
				if(firstproductdemandDao.closeProductDemand(productid.get("ffirstproductid").toString(), SchemeDesignDao)){
					throw new DJException("你选择的方案对应的需求已关闭,不能操作！");
				}
			}
			for(int i =0;i<fid.length;i++){
				
				sql = "select * from t_ord_productstructure where schemedesignid = "+fid[i];
				List<HashMap<String,Object>> list = SchemeDesignDao.QueryBySql(sql);//查产品信息
				if(list.size()>0){
					sql = "SELECT  * FROM t_ord_productstructure  where 1=1 AND fparentid='"+list.get(0).get("fid")+"'";
					List<HashMap<String,Object>> subProducts = SchemeDesignDao.QueryBySql(sql);
					list.addAll(subProducts);
				}
				sql = "select * from t_ord_schemedesignentry where fparentid = "+fid[i];
				List<HashMap<String,Object>> list1 = SchemeDesignDao.QueryBySql(sql);//查特性信息
				SchemeDesignDao.ExecUnaffirm(fid[i],list,list1);//判断制造商订单是否审核 是否生成要货申请
				if(list.size()>0){
					ProductstructureDao.Delproduct(list,fid[i]);//删除产品结构数据
				}else if(list1.size()>0){
					ProductstructureDao.Delproduct(list1,fid[i]);//删除特性所有数据
				}else 
				{
					ProductstructureDao.Delproduct(null,fid[i]);//没有特性，没有产品的方案 只反审核确认状态
				}
				
				
			
			}
			response.getWriter().write(JsonUtil.result(true,"取消确认成功!", "", ""));
		} catch (Exception e) {
			// TODO: handle exception
			response.getWriter().write(JsonUtil.result(false,e.getMessage(), "", ""));
		}
		}
	
	
	/**
	 * 生成配送
	 *
	 * @param request
	 * @param response
	 * @throws IOException
	 *
	 * @date 2014-10-9 下午1:46:35  (ZJZ)
	 */
	@RequestMapping("/generateDelivery")
	public void generateDelivery(HttpServletRequest request,HttpServletResponse response) throws IOException{
		
	
		try {
			String msg = SchemeDesignDao.ExecGenerateDelivery(request);
			
			response.getWriter().write(JsonUtil.result(true,msg, "", ""));
		} catch (Exception e) {
			// TODO: handle exception
			response.getWriter().write(JsonUtil.result(false,e.getMessage(), "", ""));
		}
		}
	
	/**
	 * 取消生成配送
	 *
	 * @param request
	 * @param response
	 * @throws IOException
	 *
	 * @date 2014-10-9 下午1:46:35  (ZJZ)
	 */
	@RequestMapping("/undoGenerateDelivery")
	public void undoGenerateDelivery(HttpServletRequest request,HttpServletResponse response) throws IOException{
		
	
		try {
			String msg = SchemeDesignDao.ExecUndoGenerateDelivery(request);
			
			
			response.getWriter().write(JsonUtil.result(true,msg, "", ""));
		} catch (Exception e) {
			// TODO: handle exception
			response.getWriter().write(JsonUtil.result(false,e.getMessage(), "", ""));
		}
		}
	/**
	 * 已确认 未生成配送的特性方案
	 * @param request
	 * @param response
	 * @throws IOException 
	 */
	@RequestMapping("getSchemeDesignNumsRpt")
	public void getSchemeDesignNumsRpt(HttpServletRequest request,HttpServletResponse response) throws IOException{
		try {
			String sql = "select * from (SELECT u2.`FNAME` fconfirmer,su.`FNAME` sname,c.`FNAME` cname,s.fid,s.`fname`,s.`fnumber`,u1.`FNAME` uname,s.`fcreatetime`,s.`fconfirmed`,s.`fconfirmtime` FROM t_ord_schemedesign s LEFT JOIN t_ord_schemedesignentry ss ON s.fid=ss.fparentid LEFT JOIN `t_bd_customer` c ON c.`fid`=s.`fcustomerid` LEFT JOIN `t_sys_user` u1 ON u1.fid=s.fcreatorid LEFT JOIN t_sys_user u2 ON u2.`FID` = s.`fconfirmer` LEFT JOIN `t_sys_supplier` su ON su.`FID`=s.`fsupplierid` where ss.fallot=0 AND s.fconfirmed=1 GROUP BY s.fid) a where 1=1";
			ListResult result = SchemeDesignDao.QueryFilterList(sql, request);
			response.getWriter().write(JsonUtil.result(true,"",result));
		} catch (DJException e) {
			// TODO: handle exception
			response.getWriter().write(JsonUtil.result(false,e.getMessage(), "", ""));
		}
	}
	
////	@RequestMapping("/test1")
//	public void reciverGroup(String fid)
//			throws IOException {
//		DongjingClient c=new DongjingClient();
//		c.setURL("http://192.168.2.116:8066/ichat/cgi");
//		c.setMethod("group_create");
//		c.setRequestProperty("summary", "test");
//		c.setRequestProperty("name", "test");
//		c.setRequestProperty("founder", "test");
//		if(c.GetData())
//		{
//		  String r=	c.getResponse().getResultString();
//		  String sql ="update t_ord_schemedesign"
//		}
//		
//	}
	@RequestMapping("SaveOrupdateSchemeDesignAudit")
	public void SaveOrupdateSchemeDesignAudit(HttpServletRequest request,HttpServletResponse response) throws IOException, ParseException{
		String userid = ((Useronline) request.getSession().getAttribute("Useronline")).getFuserid();
		Schemedesign f = (Schemedesign)request.getAttribute("Schemedesign");
		List<Productstructure> productList =  (List<Productstructure>) request.getAttribute("Productstructure");
		List<SchemeDesignEntry> entryList = (List<SchemeDesignEntry>) request.getAttribute("SchemeDesignEntry");
		if(f==null){
			response.getWriter().write(JsonUtil.result(false, "保存失败，数据异常！", "", ""));
			return;
		}
		try {
			if(firstproductdemandDao.closeProductDemand(f.getFfirstproductid(), SchemeDesignDao)){
				throw new DJException("已关闭需求不能新增方案！");
			}
			
			if(SchemeDesignDao.QueryExistsBySql("select 1 from t_ord_firstproductdemand where freceived=0 and fid = '" + f.getFfirstproductid()+"' ")){
				response.getWriter().write(JsonUtil.result(false, "保存失败，未接收不能新增！", "", ""));
				return;
			}
			if(SchemeDesignDao.QueryExistsBySql("select 1 from t_ord_SchemeDesign where fname = '"+f.getFname()+"' and fid != '"+f.getFid()+"'")){
				throw new DJException("此方案名称已存在，请重新填写方案名称！");
			}
			if(!SchemeDesignDao.QueryExistsBySql("select 1 from t_ord_SchemeDesign where fid = '"+f.getFid()+"'")){
				f.setFcreatorid(userid);
				f.setFcreatetime(new Date());
			}else if(SchemeDesignDao.QueryExistsBySql("select 1 from t_ord_SchemeDesign where fconfirmed = 1 and fid = '"+f.getFid()+"' ")){
					response.getWriter().write(JsonUtil.result(false, "保存失败，已确认的方案设计不能修改！", "", ""));
					return;
			}
			//2015-10-24 方案可不新增产品与特性
			if(entryList.size()>0&&productList.size()>0){
				throw new DJException("一个方案不能同时新增特性和产品！");
			}
//			if(entryList.size()==0&&productList.size()==0){
//				throw new DJException("请填写特性或产品！");
//			}
			f.setFaudited(1);
			f.setFauditorid(userid);
			f.setFaudittime(new Date());
			SchemeDesignDao.saveSchemeDesign(f,entryList,productList);
			
			
			
			response.getWriter().write(JsonUtil.result(true,"审核成功","",""));
		} catch (DJException e) {
			response.getWriter().write(JsonUtil.result(false,e.getMessage(),"",""));
		}
	}
	
	/**
	 * 获取选择的需求对应的供应商
	 * @param request
	 * @param response
	 * @throws IOException 
	 */
	@RequestMapping("getFirstdemandSupplierid")
	public void getFirstdemandSupplierid(HttpServletRequest request,HttpServletResponse response) throws IOException{
		try {
			String fids=request.getParameter("fids");
			String sql="SELECT DISTINCT f.fsupplierid,s.fname fsuppliername FROM t_ord_firstproductdemand f LEFT JOIN t_sys_supplier  s  ON s.fid= f.fsupplierid  WHERE f.fid IN "+fids;
			List<HashMap<String,Object>> list=SchemeDesignDao.QueryBySql(sql);
			if(list.size()>1||StringUtils.isEmpty((String)list.get(0).get("fsupplierid")))
			{
				list=SchemeDesignDao.QueryBySql("select fid fsupplierid,fname  fsuppliername from t_sys_supplier where fid='39gW7X9mRcWoSwsNJhU12TfGffw='");
			}
			response.getWriter().write(JsonUtil.result(true,"","1",list));
			return ;
		} catch (DJException e) {
			// TODO: handle exception
			response.getWriter().write(JsonUtil.result(false,e.getMessage(), "", ""));
		}
	}
}


