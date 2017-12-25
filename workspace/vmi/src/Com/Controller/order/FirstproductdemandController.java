package Com.Controller.order;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.hibernate.util.StringHelper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import Com.Base.Dao.IBaseDao;
import Com.Base.Util.DJException;
import Com.Base.Util.ExcelUtil;
import Com.Base.Util.JsonUtil;
import Com.Base.Util.ListResult;
import Com.Base.Util.ServerContext;
import Com.Base.Util.UploadFile;
import Com.Base.Util.params;
import Com.Base.Util.mySimpleUtil.MySimpleToolsZ;
import Com.Dao.System.IBaseSysDao;
import Com.Dao.System.ICustomer;
import Com.Dao.System.IProductreqallocationrulesDao;
import Com.Dao.order.IFirstproductdemandDao;
import Com.Dao.order.IProductdemandfileDao;
import Com.Dao.order.ISchemeDesignDao;
import Com.Dao.order.SchemeDesignDao;
import Com.Entity.System.Useronline;
import Com.Entity.order.Firstproductdemand;
import Com.Entity.order.Productstructure;
import Com.Entity.order.SchemeDesignEntry;
import Com.Entity.order.Schemedesign;

@Controller
public class FirstproductdemandController {
	public static final String RELATIVE_PATH = File.separatorChar+"file"+File.separatorChar+"schemedesign";
	@Resource
	private ICustomer customerDao;
	@Resource
	private IProductdemandfileDao productdemandfileDao;
	@Resource
	private IFirstproductdemandDao firstproductdemandDao;
	@Resource
	private ISchemeDesignDao schemeDesignDao;
	@Resource
	private IBaseSysDao baseSysDao;

	@Resource
	private IProductreqallocationrulesDao productreqallocationrulesDao;

	@RequestMapping("getFirstproductdemandList")
	public void getFirstproductdemandList(HttpServletRequest request,HttpServletResponse response) throws IOException{

		String userid = ((Useronline) request.getSession().getAttribute("Useronline")).getFuserid();
		String addCon;
		try {
//			String sql = "select quote(fuserid) userid from t_bd_usertouser where fsuperuserid='"+userid+"'";
//			List<HashMap<String, Object>> demandList = firstproductdemandDao.QueryBySql(sql);
//			if(demandList.size()!=0){
//				StringBuilder userids=new StringBuilder("'"+userid+"'");
//				for(HashMap<String, Object> m : demandList){
//					userids.append(","+m.get("userid"));
//				}
//				addCon = "f.fcreatid in ("+userids+")";
//			}else{
//				addCon = "f.fcreatid = '"+userid+"'";
//			}
			
			request.setAttribute("djsort","f.isfauditor,f.fauditortime desc");
			String sql =" SELECT u2.fqq, c.fname cname, f.fid,f.fname,f.fnumber,u1.fname AS fcreatid,f.fcreatetime,ifnull(u2.fname,'') AS freceiver,ifnull(u2.ftel,'') AS freceiverTel,ifnull(f.freceivetime,'') freceivetime,ifnull(u3.fname,'') AS fauditorid,ifnull(f.fauditortime,'') as fauditortime,date_format(f.foverdate,'%Y-%m-%d %H:%i') foverdate,f.fsupplierid,sp.fname fsuppliername,"
					+" ifnull(f.fcostneed,'') fcostneed,f.fiszhiyang, replace(replace(replace(REPLACE(ifnull(f.fdescription,''),'\"','\\\"'),'\\r\\n','<br/>'),'\\r','<br/>'),'\\n','<br/>') fdescription,f.isfauditor,f.freceived,f.falloted,ifnull(f.fisaccessory,'') fisaccessory,ifnull(f.fstate,'') fstate,f.flinkman,f.flinkphone,f.fgroupid FROM `t_ord_firstproductdemand` f LEFT JOIN t_sys_user u1 ON u1.fid=f.fcreatid"
					+" LEFT JOIN t_sys_user u2 ON u2.fid=f.freceiver LEFT JOIN t_sys_user u3 ON u3.fid=f.fauditorid left join t_bd_customer c on c.fid=f.fcustomerid left join t_sys_supplier sp on sp.fid=f.fsupplierid where 1=1 "+firstproductdemandDao.QueryFilterByUserofuser(request,"f.fcreatid","and")+firstproductdemandDao.QueryFilterByUser(request, "f.fcustomerid", null);
			ListResult list = firstproductdemandDao.QueryFilterList(sql, request);
			response.getWriter().write(JsonUtil.result(true, "", list));
		} catch (DJException e) {
			// TODO: handle exception
			response.getWriter().write(JsonUtil.result(false, e.getMessage(), "",""));
		}
	}
	@RequestMapping("getAllFistproductdemand")
	public void getAllFistproductdemand(HttpServletRequest request,HttpServletResponse response) throws IOException{
		try {
			request.setAttribute("djsort","f.isfauditor,f.fauditortime desc");
			String sql =" SELECT c.fname cname, f.fid,f.fname,f.fnumber,u1.fname AS fcreatid,f.fcreatetime,ifnull(u2.fname,'') AS freceiver,ifnull(u2.ftel,'') AS freceiverTel,ifnull(f.freceivetime,'') freceivetime,ifnull(u3.fname,'') AS fauditorid,ifnull(f.fauditortime,'') as fauditortime,date_format(f.foverdate,'%Y-%m-%d %H:%i') foverdate,"
					+" ifnull(f.fcostneed,'') fcostneed,f.fiszhiyang, replace(replace(replace(REPLACE(ifnull(f.fdescription,''),'\"','\\\"'),'\\r\\n','<br/>'),'\\r','<br/>'),'\\n','<br/>') fdescription,f.isfauditor,f.freceived,f.falloted,ifnull(f.fisaccessory,'') fisaccessory,ifnull(f.fstate,'') fstate,f.flinkman,f.flinkphone,f.fsupplierid,sp.fname fsuppliername FROM `t_ord_firstproductdemand` f LEFT JOIN t_sys_user u1 ON u1.fid=f.fcreatid"
					+" LEFT JOIN t_sys_user u2 ON u2.fid=f.freceiver LEFT JOIN t_sys_user u3 ON u3.fid=f.fauditorid left join t_bd_customer c on c.fid=f.fcustomerid left join t_sys_supplier sp on sp.fid=f.fsupplierid where 1=1 ";
			ListResult list = firstproductdemandDao.QueryFilterList(sql, request);
			response.getWriter().write(JsonUtil.result(true, "", list));
		} catch (DJException e) {
			// TODO: handle exception
			response.getWriter().write(JsonUtil.result(false, e.getMessage(), "",""));
		}
	}
	
	@RequestMapping("DelFirstproductdemandList")
	public void DelFirstproductdemandList(HttpServletRequest request,HttpServletResponse response) throws IOException{
		response.setCharacterEncoding("utf-8");
		String fid =  request.getParameter("fidcls");
		String[] id = fid.split(",");
		fid = "('"+fid.replaceAll(",","','")+"')";
		File file;
		try {
			for(String productDemandid : id){
				if(firstproductdemandDao.closeProductDemand(productDemandid, firstproductdemandDao)){
					throw new DJException("已经关闭的需求，不能操作！");
				}
			}
			String sql = "delete from t_ord_firstproductdemand where fid in"+fid;
			firstproductdemandDao.ExecBySql(sql);
			//删除时 检查有没附件 删除附件和记录
			sql = "select * from t_ord_productdemandfile where fparentid in"+fid;
			List<HashMap<String,Object>> list = productdemandfileDao.QueryBySql(sql);
			if(list.size()>0){
				for(HashMap<String, Object> map : list){
					file = new File(map.get("fpath").toString());
					if(file!=null&&!"".equals(file)){
						file.delete();
					}
					sql = "delete from  t_ord_productdemandfile where fparentid  in "+fid;
					productdemandfileDao.ExecBySql(sql);
				}
			}
			response.getWriter().write(JsonUtil.result(true, "删除成功", "", ""));
		} catch (DJException e) {
			// TODO: handle exception
			response.getWriter().write(JsonUtil.result(false, e.getMessage(), "", ""));
		}
	}
	@RequestMapping("SaveOrupdateFirstproductdemand")
	public void SaveOrupdateFirstproductdemand(HttpServletRequest request,HttpServletResponse response) throws IOException, ParseException{
		response.setCharacterEncoding("utf-8");
		String sql;
		String userid = ((Useronline) request.getSession().getAttribute(
				"Useronline")).getFuserid();
		Firstproductdemand f = (Firstproductdemand)request.getAttribute("Firstproductdemand");
		try {
			
			
			if(("关闭").equals(f.getFstate())){
				throw new DJException("已关闭的包装需求不能操作！");
			}

			
			if(f.getFreceived()){
				throw new DJException("已接收的不能保存！");
			}
		
			if(f.getFalloted()){
				throw new DJException("已分配的不能保存！");
			}
			if(("已发布").equals(f.getFstate())){
				throw new DJException("已发布的包装需求不能操作！");
			}
			if(!StringUtils.isEmpty(f.getFname())){
				if(productdemandfileDao.QueryExistsBySql("select 1 from t_ord_firstproductdemand where fname = '"+f.getFname()+"' and fid != '"+f.getFid()+"'")){
					throw new DJException("需求名称已存在，不允许保存！");
				}
			}
			sql="select fcustomerid from ( select fcustomerid  from t_bd_usercustomer where fuserid='"+userid+"'"+
					" union select c.fcustomerid from  t_sys_userrole r "+
					" left join t_bd_rolecustomer c on r.froleid=c.froleid where r.fuserid='"+userid+"' )  s where fcustomerid is not null " ;
			List<HashMap<String,Object>> list=customerDao.QueryBySql(sql);
			if(list.size()>1){
				throw new DJException("当前用户是管理员,不能操作！");
			}else if(list.size()==1){
				f.setFcustomerid(list.get(0).get("fcustomerid").toString());
			}else if(list.size()<1){
				throw new DJException("当前用户没有关联客户,请联系平台业务部！");
			}
			sql = "select 1 from t_ord_Productdemandfile where fparentid ='"+f.getFid()+"'";//是否有附件
			List result = productdemandfileDao.QueryBySql(sql);
			if(result.size()>0){
				f.setFisaccessory(true);
			}else{
				f.setFisaccessory(false);
			}
			if(!productdemandfileDao.QueryExistsBySql("select 1 from t_ord_firstproductdemand where fid ='"+f.getFid()+"'")){
				f.setFcreatid(userid);
				f.setFallottime(null);
				f.setFreceivetime(null);
				f.setFcreatetime(new Date());
				f.setFupdatetime(null);
				f.setFstate("存草稿");
			}else{
				f.setFupdateuserid(userid);
				f.setFupdatetime(new Date());
				f.setFstate("存草稿");
			}
			
			firstproductdemandDao.saveOrUpdate(f);
			response.getWriter().write(JsonUtil.result(true,"保存成功","",""));
		} catch (DJException e) {
			// TODO: handle exception
			response.getWriter().write(JsonUtil.result(false,e.getMessage(),"",""));
		}
		
		
	}
	@RequestMapping("getFirstproductdemandInfo")
	public void getFirstproductdemandInfo(HttpServletRequest request,HttpServletResponse response) throws IOException{
		response.setCharacterEncoding("utf-8");
		String fid = request.getParameter("fid");
		try {
			String sql = "select f.fpreproductdemandid,f.fsupplierid,f.foverdate,f.farrivetime,f.fid,f.fname,f.famount,f.fnumber,f.fstate,f.flinkman,f.flinkphone,f.fisaccessory,s.fid fsupplierid_sfid,s.fname fsupplierid_sfname,u1.fname as fcreatids,f.fcreatid,f.fcreatetime,ifnull(u2.fname,'') as fupdateuserid,ifnull(u3.fname,'') as fauditorid,f.fupdatetime,f.fauditortime,replace(replace(replace(REPLACE(ifnull(f.fdescription,''),'\"','\\\"'),'\\r\\n','<br/>'),'\\r','<br/>'),'\\n','<br/>') fdescription from t_ord_firstproductdemand f LEFT JOIN t_sys_user u1 ON u1.fid=f.fcreatid"
					+" LEFT JOIN t_sys_user u2 ON u2.fid=f.fupdateuserid LEFT JOIN t_sys_user u3 ON u3.fid=f.fauditorid LEFT JOIN t_ord_boxpile b ON f.fboxpileid=b.fid left join t_sys_supplier s on s.fid = f.fsupplierid where f.fid ='"+fid+"'";
			List list = firstproductdemandDao.QueryBySql(sql);
			response.getWriter().write(JsonUtil.result(true,"","", list));
		} catch (DJException e) {
			response.getWriter().write(JsonUtil.result(false,e.getMessage(),"",""));
		}
		
	}
	/**
	 * 审核
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("fauditorFproductdemand")
	public void fauditorFproductdemand(HttpServletRequest request,HttpServletResponse response) throws IOException{
		response.setCharacterEncoding("utf-8");
		String fids = request.getParameter("fids");
		String userid = ((Useronline) request.getSession().getAttribute(
				"Useronline")).getFuserid();
		try {
			
			
			audit(fids, userid);
			
			allocation(fids, userid);
		
			response.getWriter().write(JsonUtil.result(true,"成功","",""));
		} catch (DJException e) {
			// TODO: handle exception
			response.getWriter().write(JsonUtil.result(false,e.getMessage(),"",""));
		}
	}

	private void allocation(String fidcls1, String userid) {
		String fidcls = fidcls1;
		
		
		String sql="select flinkphone,fcustomerid,fsupplierid,fid from t_ord_firstproductdemand where ifnull(falloted,0)=0 and ifnull(fcustomerid,'')<>'' and fid in " +fidcls;
		List<HashMap<String,Object>> clist=productreqallocationrulesDao.QueryBySql(sql);
		if(clist.size()<=0)
		{
			throw new DJException("没有可以分配的需求,请检查是否所选的已经分配或客户为空");
		}
		String flinkphone = null;
		for(int i=0;i<clist.size();i++)
		{
			if(i==0){
				flinkphone = (String)clist.get(i).get("flinkphone");
			}
			if(StringHelper.isEmpty((String)clist.get(i).get("fsupplierid"))){
				productreqallocationrulesDao.ExecAllot(clist.get(i).get("fcustomerid").toString(),userid, clist.get(i).get("fid").toString());
			}else{
				sql = "update  t_ord_firstproductdemand  set falloted=1,fallotor='"+userid+"',fallottime=now(),fsupplierid='"+clist.get(i).get("fsupplierid")+"',fstate='已分配' where fid in ('%s') and fcustomerid='"+ clist.get(i).get("fcustomerid")+"' and isfauditor=1 and ifnull(falloted,0)=0 ";
				
				sql = String.format(sql, clist.get(i).get("fid"));
				
				
				if("39gW7X9mRcWoSwsNJhU12TfGffw=".equals(clist.get(i).get("fsupplierid")))//需求分配给东经时，创建提示消息
				 {
					String sqll=" select f.fid,f.fname,c.fname cname from t_ord_firstproductdemand f  inner join t_bd_customer c on c.fid=f.fcustomerid where f.fid in ('"+clist.get(i).get("fid")+"') and f.fcustomerid='"+ clist.get(i).get("fcustomerid")+"' and f.isfauditor=1 and ifnull(f.falloted,0)=0 ";
					 productreqallocationrulesDao.ExecCreateMessageInfo(sqll);
				 }
				
				productreqallocationrulesDao.ExecBySql(sql);
				 
				
			}
	
		}
		firstproductdemandDao.updateUserConcat(userid, flinkphone);
	}

	private void audit(String fids, String userid) {
		
//		String sql = "select fname,fstate,ifnull(falloted,0) falloted,fdescription, from t_ord_firstproductdemand where fid in"+fids;
		String sql="select d.fid,d.fname,d.fstate,ifnull(d.falloted,0) falloted,d.fdescription, count(f.fid ) counts from t_ord_firstproductdemand d left join  t_ord_Productdemandfile f on  f.fparentid=d.fid where d.fid in "+fids+" group by d.fid ";
		List<HashMap<String,Object>> list = firstproductdemandDao.QueryBySql(sql);
		for(int i =0;i<list.size();i++){
			if("".equals(list.get(i).get("fname"))){
				throw new DJException("需求未完成编辑。请填写需求名称。");
			}
			if("".equals(list.get(i).get("flinkphone"))){
				throw new DJException("需求未完成编辑。请填写联系电话。");
			}
			if("".equals(list.get(i).get("fdescription"))&& new Integer(list.get(i).get("counts").toString())==0){
				throw new DJException("需求未完成编辑。请上传附件或添加描述。");
			}
			if("关闭".equals(list.get(i).get("fstate"))){
				throw new DJException("已关闭的包装需求不能操作！");
			}
			if("已发布".equals(list.get(i).get("fstate"))){
				throw new DJException("需求已发布，不能重复发布。");
			}
			if(list.get(i).get("falloted").toString().equals("1")){
				throw new DJException("已分配的不能发布！");
			}
		}
		 sql = "update t_ord_firstproductdemand set fstate='已发布',isfauditor=1,fauditortime= now(),fauditorid='"+userid+"'"+" where fid in "+fids;
		firstproductdemandDao.ExecBySql(sql);
	}
	/**
	 * 反审核
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("unfauditorFproductdemand")
	public void unfauditorFproductdemand(HttpServletRequest request,HttpServletResponse response) throws IOException{
		response.setCharacterEncoding("utf-8");
		String fids = request.getParameter("fids");
		String sql;
		String userid = ((Useronline) request.getSession().getAttribute(
				"Useronline")).getFuserid();
		try {
			
			unallotruletoproductdemandP(fids);
			unfauditorFproductdemandP(fids);
			
			

			response.getWriter().write(JsonUtil.result(true,"成功","",""));
		} catch (DJException e) {
			// TODO: handle exception
			response.getWriter().write(JsonUtil.result(false,e.getMessage(),"",""));
		}
	}

	private void unallotruletoproductdemandP(String fids) {
		String fidcls = fids;
		
		String sql="select fid,fstate,ifnull(freceived,0) freceived from t_ord_firstproductdemand where ifnull(falloted,0)=1 and fid in " +fidcls;
		List<HashMap<String,Object>> clist=productreqallocationrulesDao.QueryBySql(sql);
		
		for(int i =0;i<clist.size();i++){
			if("关闭".equals(clist.get(i).get("fstate"))){
				throw new DJException("已关闭的包装需求不能操作！");
			}
			if("已完成".equals(clist.get(i).get("fstate"))){
				throw new DJException("需求已完成，不能取消发布。");
			}
			if("1".equals(clist.get(i).get("freceived").toString())){
				throw new DJException("需求已接收，不能取消发布");
			}
		}
		if(clist.size()>0)
		{
			 sql = "update  t_ord_firstproductdemand  set falloted=0,fallotor='',fallottime=null,fstate='已发布' where ifnull(falloted,0)=1 and fid in "+ fidcls ;

			 productreqallocationrulesDao.ExecBySql(sql);
//			throw new DJException("没有可以取消分配的需求");
		}
		
	}

	private void unfauditorFproductdemandP(String fids) {
		String sql;
		sql = "select ifnull(falloted,0) falloted,fstate,ifnull(freceived,0) freceived from t_ord_firstproductdemand where fid in"+fids;
		List<HashMap<String,Object>> list = firstproductdemandDao.QueryBySql(sql);
		for(int i =0;i<list.size();i++){
			if("关闭".equals(list.get(i).get("fstate"))){
				throw new DJException("已关闭的包装需求不能操作！");
			}
			if("已完成".equals(list.get(i).get("fstate"))){
				throw new DJException("需求已完成，不能取消发布。");
			}
			if("1".equals(list.get(i).get("freceived").toString())){
				throw new DJException("需求已接收，不能取消发布");
			}
			
		}
		
		sql = "update t_ord_firstproductdemand set fstate='存草稿',isfauditor=0,fauditortime= null,fauditorid=null where fid in "+fids;
		firstproductdemandDao.ExecBySql(sql);
		
		
		
		
	}
	/**
	 * 上传附件
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("uploadProductFile")
	public String uploadProductFile(HttpServletRequest request,HttpServletResponse response) throws Exception{
//		response.setContentType("text/json; charset=utf-8");
		String fparentid = request.getParameter("parentid");
		String result = "";
		String fpath = request.getServletContext().getRealPath(File.separatorChar+"file"+File.separatorChar+"Maigao").replace("\\", "/");
		try {
			
			String fid = productdemandfileDao.CreateUUid();
			
//			MyMultiFileUploadHelper.getMyMultiFileUploadHelper().upload("file\\productdemand",fid+fname.substring(fname.lastIndexOf(".")),request);
			String sql = "select falloted from t_ord_firstproductdemand where fid ='"+fparentid+"'";
			List<HashMap<String,Object>> list1 = firstproductdemandDao.QueryBySql(sql);
			if(list1.size()>0){
				if(list1.get(0).get("falloted")!=null&&!"".equals(list1.get(0).get("falloted"))){
					if(list1.get(0).get("falloted").toString().equals("true")){
						throw new DJException("已分配的不能上传附件");
					}
				}
			}
//			String fpath = request.getServletContext().getRealPath(File.separatorChar+
//				"file"+File.separatorChar+"productdemand"+File.separatorChar+fid+fname.substring(fname.lastIndexOf("."))).replace("\\", "/");
//			ServletFileUpload upload = new ServletFileUpload(new DiskFileItemFactory()); // 创建一个新的文件上传对象
//			upload.setHeaderEncoding("utf-8");//避免中文名乱码
//			List<FileItem> list  = upload.parseRequest(request);
			UploadFile.upload(request, fparentid);
//			firstproductdemandDao.saveProductFile(fparentid,list,fpath);
//			Productdemandfile p = new Productdemandfile();
//			p.setFid(productdemandfileDao.CreateUUid());
//			p.setFname(fname);
//			p.setFparentid(fparentid);
//			p.setFpath(fpath);
//			productdemandfileDao.saveOrUpdate(p);
			sql = "update t_ord_firstproductdemand set fisaccessory=1 where fid='"+fparentid+"'";
			firstproductdemandDao.ExecBySql(sql);//需求存在后在添加附件时，改为有附件
			result = "{success:true,msg:'附件上传成功!'}";
			response.getWriter().write(result);
		} catch (DJException e) {
			response.getWriter().write(JsonUtil.result(false, e.getMessage(), "",""));	
		}
		
		return null;
	}
	
	
	
	
	
	
	
	
	
	/**
	 * 获取附件目录
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("getProductdemandfileList")
	public void getProductdemandFileList(HttpServletRequest request,HttpServletResponse response) throws IOException{
//		String parentid = request.getParameter("fparentid");
		String sql = "select fid,fname,fpath,fparentid from t_ord_Productdemandfile";
		try {
			ListResult list = productdemandfileDao.QueryFilterList(sql, request);
			response.getWriter().write(JsonUtil.result(true, "", list));
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
	@RequestMapping("downloadProductdemandFile")
	public String downloadProductdemandFile(HttpServletRequest request,HttpServletResponse response) throws IOException, FileUploadException{
		String fid = request.getParameter("fid");
		try {
//			productdemandfileDao.downloadProductdemandFile(response,fid);
			UploadFile.download(response, fid);
		} catch (DJException e) {
			response.getOutputStream().write(e.getMessage().getBytes());
		}
		return null;
	}
	/**
	 * 删除附件
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("delProductdemandfile")
	public void delProductdemandfile(HttpServletRequest request,HttpServletResponse response) throws IOException{
		String fid = request.getParameter("fid");
		String fparentid = request.getParameter("fparentid");
		String fpath = request.getParameter("fpath");
		try {
//			File file = null;
			String sql  = "select falloted from t_ord_firstproductdemand where fid ='"+fparentid+"'";
				List<HashMap<String,Object>> list1 = firstproductdemandDao.QueryBySql(sql);
				if(list1.size()>0){
					if(list1.get(0).get("falloted")!=null&&!"".equals(list1.get(0).get("falloted"))){
						if(list1.get(0).get("falloted").toString().equals("true")){
							throw new DJException("已分配的不能删除附件！");
						}
					}
				}
//				file = new File(fpath);
//					file.delete();
//				sql = "delete from  t_ord_productdemandfile  where fid = '"+fid+"'";
//				productdemandfileDao.ExecBySql(sql);
				UploadFile.delFile(fid);
				sql = "select 1 from t_ord_productdemandfile where fparentid = '"+fparentid+"'";
				List list = productdemandfileDao.QueryBySql(sql);
				if(list.size()==0){//删除完附件时，改为无附件
					sql = "update t_ord_firstproductdemand set fisaccessory=0 where fid='"+fparentid+"'";
					firstproductdemandDao.ExecBySql(sql);
				}
			response.getWriter().write(JsonUtil.result(true, "删除成功","",""));
		} catch (DJException e) {
			// TODO: handle exception
			response.getWriter().write(JsonUtil.result(false,e.getMessage(),"",""));
		}
	}
	/**
	 * 获取主键ID,编码,当前登陆用户
	 * @throws IOException 
	 */
	@RequestMapping("getCreateidAndFnumber")
	public void getCreateidAndFnumber(HttpServletRequest request,HttpServletResponse response) throws IOException{
		Useronline userInfo = (Useronline) request.getSession().getAttribute("Useronline");
		String username = userInfo.getFusername();
		String sql = "select ifnull(ftel,'') ftel from t_sys_user where fid = '"+userInfo.getFuserid()+"'";
		String tel = ((HashMap<String, Object>)productdemandfileDao.QueryBySql(sql).get(0)).get("ftel").toString();
		Map map = new HashMap();
		List list = new ArrayList();
		map.put("username", username);
		map.put("tel", tel);
		map.put("fid", productdemandfileDao.CreateUUid());
		map.put("fnumber", ServerContext.getNumberHelper().getNumber("t_ord_firstproductdemand", "F", 4, false));
		list.add(map);
		response.getWriter().write(JsonUtil.result(true, "", "",list));
	}
	/**
	 * 根据包装需求获取方案设计
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("getProductSchemeDesignList")
	public void getProductSchemeDesignList(HttpServletRequest request,HttpServletResponse response) throws IOException{
		//String firstproductid = request.getParameter("firstproductid");
		String userid = ((Useronline)request.getSession().getAttribute("Useronline")).getFuserid();
		try {
//			String sql="select fcustomerid from ( select fcustomerid  from t_bd_usercustomer where fuserid='"+userid+"'"+
//					" union select c.fcustomerid from  t_sys_userrole r "+
//					" left join t_bd_rolecustomer c on r.froleid=c.froleid where r.fuserid='"+userid+"' )  s where fcustomerid is not null " ;
//			List<HashMap<String,Object>> list=customerDao.QueryBySql(sql);
//			if(list.size()==1){
//				String sql = "SELECT s.fid,s.fcreatorid,s.fcreatetime,s.fname,s.fcustomerid,s.fsupplierid,s.fnumber,s.fdescription,s.ffirstproductid,s.fconfirmed FROM t_ord_schemedesign s  where s.ffirstproductid='"+firstproductid+"'";
			String sql = "SELECT u1.fqq,u3.fname fauditorid,s.faudited,sc.fid sfid,u1.ftype utype,s.fid,s.fcreatorid,u1.fname fcreator,s.fcreatetime,s.fname,s.fnumber,s.fdescription,s.ffirstproductid,s.fcustomerid,c.fname fcustomer,s.fsupplierid,sp.fname fsupplier,s.fconfirmed,s.fconfirmer,s.fconfirmtime,s.fgroupid FROM t_ord_schemedesign s "
					+" LEFT JOIN t_sys_user u1 ON u1.fid=s.fcreatorid LEFT JOIN (SELECT fid,fparentid FROM `t_ord_schemedesignentry` GROUP BY fparentid) sc  ON s.fid=sc.`fparentid` left join t_sys_user u3 ON u3.fid=s.fauditorid"
					+" LEFT JOIN t_bd_customer c ON c.fid=s.fcustomerid LEFT JOIN t_sys_supplier sp ON sp.fid=s.fsupplierid where 1=1  AND (s.faudited = 1 OR IFNULL(sc.fid,1)=1)"+firstproductdemandDao.QueryFilterByUser(request, "s.fcustomerid", null);
				ListResult result = firstproductdemandDao.QueryFilterList(sql, request);
				response.getWriter().write(JsonUtil.result(true,"",result));
//			}else{
//				throw new DJException("当前用户是管理员或没有关联客户！");
//			}
			
		} catch (DJException e) {
			// TODO: handle exception
			response.getWriter().write(JsonUtil.result(false,e.getMessage(),"",""));
		}
	}
	/**
	 * 确认方案设计
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("Fconfirmed")
	public void Fconfirmed(HttpServletRequest request,HttpServletResponse response) throws IOException{
		String fids = request.getParameter("fids");
		String fparentid = request.getParameter("fparentid");
		try {
			this.getFalloted(fparentid);
			
			String sql = "update t_ord_schemedesign set fconfirmed=1 where fid in"+fids;
			schemeDesignDao.ExecBySql(sql);
//			this.updateStateByScheme(fparentid);
			response.getWriter().write(JsonUtil.result(true,"确认成功","",""));
		} catch (DJException e) {
			// TODO: handle exception
			response.getWriter().write(JsonUtil.result(false,e.getMessage(),"",""));
		}
	}
	/**
	 * 取消确认方案设计
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("UnFconfirmed")
	public void UnFconfirmed(HttpServletRequest request,HttpServletResponse response) throws IOException{
		String fids = request.getParameter("fids");
		String fparentid = request.getParameter("fparentid");
		try {
			this.getFalloted(fparentid);
		
			String sql = "select 1 from t_ord_schemedesign where fordered = 1 and fid in "+fids;
			List list = firstproductdemandDao.QueryBySql(sql);
			if(list.size()>0){
				throw new DJException("已经下单的不能取消确认！");
			}
			sql = "update t_ord_schemedesign set fconfirmed=0 where fid in"+fids;
			schemeDesignDao.ExecBySql(sql);
//			this.updateStateByScheme(fparentid);
			response.getWriter().write(JsonUtil.result(true,"取消确认成功","",""));
		} catch (DJException e) {
			// TODO: handle exception
			response.getWriter().write(JsonUtil.result(false,e.getMessage(),"",""));
		}
	}
	/**
	 * 更新包装需求状态
	 * @param fparentid 需求ID
	 * @param state 状态
	 */
	public static void updateState(String fparentid,String state, IBaseDao iBaseDao){
		String sql = "update t_ord_firstproductdemand set fstate='"+state+"' where fid ='"+fparentid+"'";
		iBaseDao.ExecBySql(sql);
	}
	/**
	 * 根据方案设计更新需求状态
	 * @param parentid
	 */
	public void updateStateByScheme(String parentid){
		String sql = "select fconfirmed from t_ord_schemedesign where ffirstproductid = '"+parentid+"'";
		List<HashMap<String,Object>> list1 = firstproductdemandDao.QueryBySql(sql);
		int n = 0;
		String state = null;
		for(int j =0;j<list1.size();j++){
			state = "方案设计";
			if("0".equals(list1.get(j).get("fconfirmed").toString())){
				n++;
				state = "部分方案确认";
				if(n==list1.size()){
					state = "方案设计";
				}
			}else{
				state = "方案确认";
				if(n>0){
					state = "部分方案确认";
				}
			}
		}
		FirstproductdemandController.updateState(parentid, state,firstproductdemandDao);
	}
	
	public void getFalloted(String fparentid){
		String sql  = "select falloted,fstate from t_ord_firstproductdemand where fid ='"+fparentid+"'";
		List<HashMap<String,Object>> list1 = firstproductdemandDao.QueryBySql(sql);
		if(list1.size()>0){
			if(list1.get(0).get("falloted")!=null&&!"".equals(list1.get(0).get("falloted"))){
				if("关闭".equals(list1.get(0).get("fstate"))){
					throw new DJException("已关闭的包装需求不能操作！");
				}
				if(list1.get(0).get("falloted").toString().equals("false")){
					throw new DJException("包装需求未分配！");
				}
				
			}
		}
	}
	/**
	 * 新增状态下 添加附件后关闭窗口，删除所有附件
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("getFileByfid")
	public void getFileByfid(HttpServletRequest request,HttpServletResponse response) throws IOException{
		String fparentid = request.getParameter("fparentid");
//		String fpath = request.getParameter("fpath");
		String sql;
		File file = null;
		try {
			sql = "select fpath from t_ord_productdemandfile where fparentid = '"+fparentid+"'";
			List<HashMap<String,Object>> list = productdemandfileDao.QueryBySql(sql);
			for(int i =0;i<list.size();i++){
				file = new File(list.get(i).get("fpath").toString());
				file.delete();
			}
			sql = "delete from  t_ord_productdemandfile  where fparentid = '"+fparentid+"'";
			productdemandfileDao.ExecBySql(sql);
			response.getWriter().write(JsonUtil.result(true, "", "",""));
		} catch (DJException e) {
			// TODO: handle exception
			response.getWriter().write(JsonUtil.result(false,e.getMessage(), "",""));
		}
	}
	/**
	 * 导出
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("exportProductDemand")
	public void exportProductDemand(HttpServletRequest request,HttpServletResponse response) throws IOException{
		try {
			request.setAttribute("nolimit",true);
			String sql = (String) request.getSession().getAttribute("AllotedFirstproductListQuery");
			ListResult result = (ListResult) firstproductdemandDao.QueryFilterList(sql, request);
			Map<String, String> map = new HashMap<>();
			map.put("fnumber", "需求编号");
			map.put("cname", "客户名称");
			map.put("fname", "需求名称");
			map.put("fstate", "需求状态");
			map.put("foverdate", "方案入库日期");
			map.put("fdescription", "需求描述");
			map.put("fauditortime", "发布时间");
			map.put("flinkman", "联系人");
			map.put("flinkphone", "联系电话");
			map.put("isaccessory", "是否有附件");
			map.put("freceiver", "设计师名称");
			ExcelUtil.toexcel(response,result,map);
		} catch (DJException e) {
			response.getWriter().write(JsonUtil.result(false,e.getMessage(), "",""));
		}
	}
	/**
	 * 关闭
	 * @param request fid
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("closeProductDemand")
	public void closeProductDemand(HttpServletRequest request,HttpServletResponse response) throws IOException{
		String fids = request.getParameter("fids");
		String userid = ((Useronline) request.getSession().getAttribute(
				"Useronline")).getFuserid();
		try {
			String sql = "select * from t_ord_firstproductdemand where fid in"+fids;
			List<HashMap<String,Object>> list = firstproductdemandDao.QueryBySql(sql);
			for(HashMap<String, Object> productDemand : list){
				if("关闭".equals(productDemand.get("fstate"))){
					throw new DJException("该需求已经关闭！");
				}
				sql = "update t_ord_firstproductdemand set fstate = '关闭',fclosetime=now(),fcloseuserid='%s',fcloseprevstate='%s' where fid ='"+productDemand.get("fid")+"'";
				sql = String.format(sql, userid,productDemand.get("fstate"));
				firstproductdemandDao.ExecBySql(sql);
			}
			
			response.getWriter().write(JsonUtil.result(true,"关闭成功", "",""));
		} catch (DJException e) {
			// TODO: handle exception
			response.getWriter().write(JsonUtil.result(false,e.getMessage(), "",""));
		}
	}
	/**
	 * 反关闭
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("uncloseProductDemand")
	public void uncloseProductDemand(HttpServletRequest request,HttpServletResponse response) throws IOException{
		String fids = request.getParameter("fids");
		try {
			String sql = "select * from t_ord_firstproductdemand where fid in"+fids;
			List<HashMap<String,Object>> list = firstproductdemandDao.QueryBySql(sql);
			for(HashMap<String, Object> productDemand : list){
				if("关闭".equals(productDemand.get("fstate"))){
					sql  = "update t_ord_firstproductdemand set fstate='%s',fclosetime=null,fcloseuserid='',fcloseprevstate='' where fid ='%s'";
					sql = String.format(sql, productDemand.get("fcloseprevstate"),productDemand.get("fid"));
					firstproductdemandDao.ExecBySql(sql);
				}else{
					throw new DJException("该需求没有关闭！");
				}
			}
			
			response.getWriter().write(JsonUtil.result(true,"取消关闭成功", "",""));
		} catch (DJException e) {
			// TODO: handle exception
			response.getWriter().write(JsonUtil.result(false,e.getMessage(), "",""));
		}
	}
	
	
	
	/**
	 * 编辑状态发布
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("editauditorFproductdemand")
	public void editauditorFproductdemand(HttpServletRequest request,HttpServletResponse response) throws IOException{
		response.setCharacterEncoding("utf-8");
//		String fids = request.getParameter("fids");
		String userid = ((Useronline) request.getSession().getAttribute(
				"Useronline")).getFuserid();
		String sql="";
		try {

				Firstproductdemand f = (Firstproductdemand)request.getAttribute("Firstproductdemand");
					if("".equals(f.getFname()))
					{
						throw new DJException("需求未完成编辑。请填写需求名称。");
					}
					if(productdemandfileDao.QueryExistsBySql("select 1 from t_ord_firstproductdemand where fname = '"+f.getFname()+"' and fid != '"+f.getFid()+"'")){
						throw new DJException("需求名称已存在，请修改后发布！");
					}
					if(("关闭").equals(f.getFstate())){
						throw new DJException("已关闭的包装需求不能操作！");
					}
					
					if(f.getFreceived()){
						throw new DJException("已接收的不能发布！");
					}
					if(f.getFalloted()){
						throw new DJException("已分配的不能发布！");
					}
					if(("已发布").equals(f.getFstate())){
						throw new DJException("需求已发布，不能重复发布。");
					}
					
					sql="select fcustomerid from ( select fcustomerid  from t_bd_usercustomer where fuserid='"+userid+"'"+
							" union select c.fcustomerid from  t_sys_userrole r "+
							" left join t_bd_rolecustomer c on r.froleid=c.froleid where r.fuserid='"+userid+"' )  s where fcustomerid is not null " ;
					List<HashMap<String,Object>> list=customerDao.QueryBySql(sql);
					if(list.size()>1){
						throw new DJException("当前用户是管理员,不能操作！");
					}else if(list.size()==1){
						f.setFcustomerid(list.get(0).get("fcustomerid").toString());
					}else if(list.size()<1){
						throw new DJException("当前用户没有关联客户,请联系平台业务部！");
					}
					sql = "select 1 from t_ord_Productdemandfile where fparentid ='"+f.getFid()+"'";//是否有附件
					List result = productdemandfileDao.QueryBySql(sql);
					if(result.size()==0&&"".equals(f.getFdescription()))
					{
						throw new DJException("需求未完成编辑。请上传附件或添加描述。");
					}
					if(result.size()>0){
						f.setFisaccessory(true);
					}else{
						f.setFisaccessory(false);
					}
					if(!productdemandfileDao.QueryExistsBySql("select 1 from t_ord_firstproductdemand where fid ='"+f.getFid()+"'")){
						f.setFcreatid(userid);
						f.setFallottime(null);
						f.setFreceivetime(null);
						f.setFcreatetime(new Date());
						f.setFupdatetime(null);
						
					}else{
						f.setFupdateuserid(userid);
						f.setFupdatetime(new Date());
					}
					if("".equals(f.getFcustomerid()))
					{
						throw new DJException("客户不能为空！");
					}
					firstproductdemandDao.ExecAuditFproductdemand(f, userid);
			
		
			response.getWriter().write(JsonUtil.result(true,"成功","",""));
		} catch (DJException e) {
			// TODO: handle exception
			response.getWriter().write(JsonUtil.result(false,e.getMessage(),"",""));
		}
		catch (Exception e) {
			response.getWriter().write(JsonUtil.result(false,e.getMessage(),"",""));
		}
	}

	@RequestMapping("updateStateBySupplierOver")
	public void updateStateBySupplierOver(HttpServletRequest request,HttpServletResponse response) throws IOException{
		response.setCharacterEncoding("utf-8");
		String fids = request.getParameter("fids");
		int ftype = new Integer(request.getParameter("ftype"));
		String[] productid = fids.split(",");
		fids="('"+fids.replace(",","','")+"')";

		try {
			for(String fid : productid){
				if(firstproductdemandDao.closeProductDemand(fid, firstproductdemandDao)){
					throw new DJException("需求已经关闭，不能操作");
				}
			}
			
			String sql="select 1 from t_ord_firstproductdemand where fstate<>'已设计' and fid in "+fids;
			
			if(ftype==0)//方案未确认，继续操作，则跳过
			{
				if(firstproductdemandDao.QueryExistsBySql(sql))
				{
					throw new DJException("需求必须为已设计，才能操作");
				}
				
				sql="select 1 from t_ord_schemedesign where ifnull(fconfirmed,0)=0 and ffirstproductid in "+fids;
	
				if(firstproductdemandDao.QueryExistsBySql(sql))
				{
					throw new DJException("该需求有未确认的方案，是否继续操作。");
				}
			}
			sql = "update t_ord_firstproductdemand set fstate='已完成' where fstate='已设计' and  fid in "+fids;
			firstproductdemandDao.ExecBySql(sql);

			response.getWriter().write(JsonUtil.result(true,"成功","",""));
		} catch (DJException e) {
			// TODO: handle exception
			response.getWriter().write(JsonUtil.result(false,e.getMessage(),"",""));
		}
		catch (Exception e) {
			response.getWriter().write(JsonUtil.result(false,e.getMessage(),"",""));
		}
	}
	
	@RequestMapping("updateStateBySupplierUnOver")
	public void updateStateBySupplierUnOver(HttpServletRequest request,HttpServletResponse response) throws IOException{
		response.setCharacterEncoding("utf-8");
		String fids = request.getParameter("fids");
		String[] productid = fids.split(",");
		fids="('"+fids.replace(",","','")+"')";

		try {
			for(String fid : productid){
				if(firstproductdemandDao.closeProductDemand(fid, firstproductdemandDao)){
					throw new DJException("需求已经关闭，不能操作");
				}
			}
			String  sql="select 1 from t_ord_firstproductdemand where fstate<>'已完成' and fid in "+fids;
			if(firstproductdemandDao.QueryExistsBySql(sql))
			{
				throw new DJException("需求状态不是已完成，不能取消完成");
			}
			 sql = "update t_ord_firstproductdemand set fstate='已设计' where fstate='已完成' and fid in "+fids;
			firstproductdemandDao.ExecBySql(sql);

			response.getWriter().write(JsonUtil.result(true,"成功","",""));
		} catch (DJException e) {
			// TODO: handle exception
			response.getWriter().write(JsonUtil.result(false,e.getMessage(),"",""));
		}
		catch (Exception e) {
			response.getWriter().write(JsonUtil.result(false,e.getMessage(),"",""));
		}
		
		
	}
	@RequestMapping("getSupplierByUser")
	public void getSupplierByUser(HttpServletRequest request,HttpServletResponse response) throws IOException{
		String userid = ((Useronline)request.getSession().getAttribute("Useronline")).getFuserid();
		try {
			String sql="select fcustomerid from ( select fcustomerid  from t_bd_usercustomer where fuserid='"+userid+"'"+
					" union select c.fcustomerid from  t_sys_userrole r "+
					" left join t_bd_rolecustomer c on r.froleid=c.froleid where r.fuserid='"+userid+"' )  s where fcustomerid is not null " ;
			List<HashMap<String,Object>> list=customerDao.QueryBySql(sql);
			if(list.size()==1){
				sql =" select tpprr.fid fid, tpprr.fcustomerid fcustomerid, tpprr.fsupplierid sfid,tpprr.fcreatorid fcreatorid,tpprr.fcreatetime fcreatetime, tbc.fname fcustomername, tss.fname sfname,tsu.fname fcreatorname  from t_pdt_productreqallocationRules tpprr left join t_bd_customer tbc on tpprr.fcustomerid = tbc.fid left join t_sys_supplier tss on tpprr.fsupplierid = tss.fid left join t_sys_user tsu on tpprr.fcreatorid = tsu.fid where  tbc.fid='"+list.get(0).get("fcustomerid")+"'";
			
			}else{
				sql =" select tpprr.fid fid, tpprr.fcustomerid fcustomerid, tpprr.fsupplierid sfid,tpprr.fcreatorid fcreatorid,tpprr.fcreatetime fcreatetime, tbc.fname fcustomername, tss.fname sfname,tsu.fname fcreatorname  from t_pdt_productreqallocationRules tpprr left join t_bd_customer tbc on tpprr.fcustomerid = tbc.fid left join t_sys_supplier tss on tpprr.fsupplierid = tss.fid left join t_sys_user tsu on tpprr.fcreatorid = tsu.fid";
			}
			ListResult result = firstproductdemandDao.QueryFilterList(sql, request);
			response.getWriter().write(JsonUtil.result(true,"",result));
		} catch (DJException e) {
			// TODO: handle exception
			response.getWriter().write(JsonUtil.result(false,e.getMessage(),"",""));
		}
		
	}
	/**
	 * 根据需求分配规则查找制造商
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "getSupplierListOfCustomer")
	public String getSupplierListOfCustomer(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String sql = "select distinct e1.fid,e1.fname from t_pdt_productreqallocationrules e"
				+" left join t_sys_supplier e1 on e.fsupplierid=e1.fid where 1=1 "+firstproductdemandDao.QueryFilterByUser(request, "e.fcustomerid", "e.fsupplierid")+" order by CONVERT(e1.fname USING gbk) asc ";
		try {
			List<HashMap<String, Object>> result = firstproductdemandDao.QueryBySql(sql);
			response.getWriter().write(JsonUtil.result(true, "",String.valueOf(result.size()), result));
		} catch (DJException e) {
			response.getWriter().write(JsonUtil.result(false,e.getMessage(),"",""));
		}
		return null;
	}
	
	
	
	/**
	 * 根据需求分配规则查找制造商 用于在线设计
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "getSupplierListOfCustomerByDesign")
	public String getSupplierListOfCustomerByDesign(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String fcustomeridSql=StringUtils.isEmpty(request.getParameter("fcustomerid"))?"":" and fcustomerid='"+request.getParameter("fcustomerid")+"'";
		String sql = "select distinct e1.fid,e1.fname from t_pdt_productreqallocationrules e"
				+" left join t_sys_supplier e1 on e.fsupplierid=e1.fid where 1=1 "+fcustomeridSql+" order by CONVERT(e1.fname USING gbk) asc ";
		try {
			List<HashMap<String, Object>> result = firstproductdemandDao.QueryBySql(sql);
			response.getWriter().write(JsonUtil.result(true, "",String.valueOf(result.size()), result));
		} catch (DJException e) {
			response.getWriter().write(JsonUtil.result(false,e.getMessage(),"",""));
		}
		return null;
	}
	/**
	 * 更改产品需求的制造商
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "updateSupplierOfProductDemand")
	public String updateSupplierOfProductDemand(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String fid = request.getParameter("fid");
		String fsupplierid = request.getParameter("fsupplierid");
		try {
			if(StringUtils.isEmpty(fid)||StringUtils.isEmpty(fsupplierid)){
				throw new DJException("数据传输异常,需求ID或制造商ID不存在！");
			}
			String sql = "update t_ord_firstproductdemand set fsupplierid = :fsupplierid where fid = :fid and isfauditor = 0";
			params p = new params();
			p.put("fsupplierid", fsupplierid);
			p.put("fid", fid);
			firstproductdemandDao.ExecBySql(sql, p);
			response.getWriter().write(JsonUtil.result(true, "成功！", "",""));
		} catch (DJException e) {
			response.getWriter().write(JsonUtil.result(false, e.getMessage(), "",""));
		}
		return null;
	}
/**
* 我的需求导出
* @param request
* @param response
* @throws IOException
*/
@RequestMapping("exportProductDemandList")
public void exportProductDemandList(HttpServletRequest request,HttpServletResponse response) throws IOException{
try {
//	String sql = "SELECT f.fid,f.fnumber 需求编号,f.fname 需求名称, f.fiszhiyang 是否制样,f.foverdate 方案入库日期,f.fdescription 需求描述,u1.fname AS 创建人,f.fcreatetime 创建时间,ifnull(u3.fname,'') AS 审核人,ifnull(f.fauditortime,'') as 审核时间"
//			+" FROM `t_ord_firstproductdemand` f LEFT JOIN t_sys_user u1 ON u1.fid=f.fcreatid"
//			+" LEFT JOIN t_sys_user u2 ON u2.fid=f.freceiver LEFT JOIN t_sys_user u3 ON u3.fid=f.fauditorid where 1=1 "+firstproductdemandDao.QueryFilterByUser(request, "f.fcustomerid", null);
//	
	
	String sql = "SELECT f.fnumber '需求编号',c.fname 客户名称,f.fname '需求名称',f.fstate '需求状态',date_format(f.foverdate,'%Y-%m-%d %H:%i') '方案入库日期',replace(replace(replace(REPLACE(ifnull(f.fdescription,''),'\"','\\\"'),'\\r\\n','<br/>'),'\\r','<br/>'),'\\n','<br/>') '需求描述',f.fauditortime '发布时间',ifnull(f.flinkman,'') '联系人',ifnull(f.flinkphone,'') '联系电话',if(fisaccessory=true,'是','否') '是否有附件',u2.fname AS '设计师名称'"
		    +"  FROM t_ord_firstproductdemand f LEFT JOIN t_sys_user u1 ON u1.fid=f.fcreatid"
		    +" LEFT JOIN t_bd_customer c ON f.fcustomerid = c.fid"
		    +" LEFT JOIN t_sys_user u2 ON u2.fid=f.freceiver LEFT JOIN t_sys_user u3 ON u3.fid=f.fauditorid where 1 = 1 "
		    + customerDao.QueryFilterByUser(request,"f.fcustomerid","f.fsupplierid");
	
//	ListResult result = new ListResult();
//	
//	result.setData(firstproductdemandDao.QueryBySql(sql));
//	
	request.setAttribute("nolimit", 0);
	ListResult result = firstproductdemandDao.QueryFilterList(sql, request);
	
	ExcelUtil.toexcel(response,result);
} catch (DJException e) {
	// TODO: handle exception
	response.getWriter().write(JsonUtil.result(false,e.getMessage(), "",""));
}
}


@RequestMapping("getVmiResponseDetails")
public void getVmiResponseDetails(HttpServletRequest request,HttpServletResponse response) throws IOException{

	try {

		request.setAttribute("djsort","t1.fauditortime desc,t1.fname ");
		String sql ="select t1.fid, t1.fname demandname,t1.fauditortime ,t1.fallottime,t1.freceivetime,s.fname fsuppliername ,t2.fname schemename,t2.fcreatetime,t2.fconfirmtime,u.fname freceiver,cus.fname customername,u2.fname schemetorname,TIMESTAMPDIFF(MINUTE,t1.fauditortime,t1.fallottime) vmitime,TIMESTAMPDIFF(MINUTE,t1.fallottime,t1.freceivetime) suppliertime, TIMESTAMPDIFF(MINUTE,t1.freceivetime,t2.fcreatetime) designtime,TIMESTAMPDIFF(MINUTE,t2.fcreatetime,t2.fconfirmtime) confirmonlinetime,ifnull(f.planepic,0) planepic,ifnull(f.strcutpic,0) strcutpic "
				+" from t_ord_firstproductdemand t1 "
				+" left join  t_ord_schemedesign t2 on t1.fid=t2.ffirstproductid"
				+" left join (select sum(case f.ftype when '版面图'then 1 else 0 end) planepic,sum(case f.ftype when '结构图'then 1 else 0 end) strcutpic,f.fparentid from t_ord_productdemandfile f group by  f.fparentid ) f on f.fparentid=t2.fid"
				+" left join t_sys_user u on t1.freceiver=u.fid " 
				+" left join t_bd_customer cus on t1.fcustomerid=cus.fid"
				+" left join t_sys_supplier s on s.fid=t1.fsupplierid"
				+" left join t_sys_user u2 on u2.fid=t2.fcreatorid where 1=1 and t1.isfauditor=1 ";
//				+firstproductdemandDao.QueryFilterByUser(request, "f.fcustomerid", null);
		ListResult list = firstproductdemandDao.QueryFilterList(sql, request);
		response.getWriter().write(JsonUtil.result(true, "", list));
	} catch (DJException e) {
		// TODO: handle exception
		response.getWriter().write(JsonUtil.result(false, e.getMessage(), "",""));
	}
}


@RequestMapping(value = "/vmiResponseDetailstoexcel")
public String vmiResponseDetailstoexcel(HttpServletRequest request,
		HttpServletResponse reponse) throws IOException {
	try {
	
		request.setAttribute("djsort","t1.fauditortime desc,t1.fname ");
		String sql ="select t1.fid, t1.fname demandname,t1.fauditortime ,t1.fallottime,t1.freceivetime,s.fname fsuppliername ,t2.fname schemename,t2.fcreatetime,t2.fconfirmtime,u.fname freceiver,cus.fname customername,u2.fname schemetorname,TIMESTAMPDIFF(MINUTE,t1.fauditortime,t1.fallottime) vmitime,TIMESTAMPDIFF(MINUTE,t1.fallottime,t1.freceivetime) suppliertime, TIMESTAMPDIFF(MINUTE,t1.freceivetime,t2.fcreatetime) designtime,TIMESTAMPDIFF(MINUTE,t2.fcreatetime,t2.fconfirmtime) confirmonlinetime,ifnull(f.planepic,0) planepic,ifnull(f.strcutpic,0) strcutpic "
				+" from t_ord_firstproductdemand t1 "
				+" left join  t_ord_schemedesign t2 on t1.fid=t2.ffirstproductid"
				+" left join (select sum(case f.ftype when '版面图'then 1 else 0 end) planepic,sum(case f.ftype when '结构图'then 1 else 0 end) strcutpic,f.fparentid from t_ord_productdemandfile f group by  f.fparentid ) f on f.fparentid=t2.fid"
				+" left join t_sys_user u on t1.freceiver=u.fid " 
				+" left join t_bd_customer cus on t1.fcustomerid=cus.fid"
				+" left join t_sys_supplier s on s.fid=t1.fsupplierid"
				+" left join t_sys_user u2 on u2.fid=t2.fcreatorid where 1=1 and t1.isfauditor=1 ";
		request.setAttribute("nolimit", "true");
		ListResult result = MySimpleToolsZ.getMySimpleToolsZ().buildExcelListResult(sql, request, firstproductdemandDao);
		
		//处理渲染
//		String[] stateMap = new String[]{""};
//		List<HashMap<String, Object>> data = result.getData();
		
//		for (HashMap<String, Object> hashMap : data) {
//			
//			hashMap.put("订单状态", stateMap.get(hashMap.get("订单状态")));
//			
//		}
		

		List<String> order = MySimpleToolsZ.gainDataIndexList(request);
		
		ExcelUtil.toexcel(reponse, result, order);
//		ExcelUtil.toexcel(reponse,result,"需求响应明细表");
	} catch (DJException e) {
		// TODO Auto-generated catch block
		reponse.getWriter().write(
				JsonUtil.result(false, e.getMessage(), "", ""));
	}

	return null;

}
@RequestMapping(value = "/productDemanPrint")
public void productDemanPrint(HttpServletRequest request,
		HttpServletResponse reponse) throws IOException{
	String fid = request.getParameter("fids");
	try {
		fid = "'"+fid.replaceAll(",", "','")+"'";
		String sql = "SELECT f.fid,GROUP_CONCAT(pdf.`fname`) filename,s.fname sname,f.fname,f.famount,f.fcreatetime,f.farrivetime,f.flinkman,f.flinkphone,f.fdescription FROM `t_ord_firstproductdemand` f LEFT JOIN `t_ord_productdemandfile` pdf ON pdf.`fparentid`=f.`fid` LEFT JOIN `t_sys_supplier` s ON s.fid=f.`fsupplierid` where f.fid in("+fid+") group by f.fid";
		List list = firstproductdemandDao.QueryBySql(sql);
		reponse.getWriter().write(
				JsonUtil.result(true, "", "",list));
	} catch (DJException e) {
		// TODO: handle exception
		reponse.getWriter().write(
				JsonUtil.result(false, "", "",""));
	}
}
/**
 *  需求方案确认后新增产品
 * @param request
 * @param response
 * @throws IOException
 * @throws ParseException
 */
@RequestMapping("SaveOrupdateFirstProduct")
public void SaveOrupdateFirstProduct(HttpServletRequest request,HttpServletResponse response) throws IOException, ParseException{
	String userid = ((Useronline) request.getSession().getAttribute("Useronline")).getFuserid();
	Firstproductdemand f = (Firstproductdemand)request.getAttribute("Firstproductdemand");
	List<Productstructure> productList =  (List<Productstructure>) request.getAttribute("Productstructure");
	if(f==null){
		response.getWriter().write(JsonUtil.result(false, "保存失败，数据异常！", "", ""));
		return;
	}
	try {
		 if(!firstproductdemandDao.QueryExistsBySql("select 1 from t_ord_SchemeDesign where fconfirmed = 1 and ffirstproductid= '"+f.getFid()+"' ")){
				response.getWriter().write(JsonUtil.result(false, "保存失败，确认的方案设计才能编辑！", "", ""));
				return;
		}
		//方案未确认 不可以新增
		if(firstproductdemandDao.closeProductDemand(f.getFid(), firstproductdemandDao)){
			throw new DJException("已关闭的需求不能编辑产品！");
		}
		
		if(firstproductdemandDao.QueryExistsBySql("select 1 from t_ord_firstproductdemand where freceived=0 and fid = '" + f.getFid()+"' ")){
			response.getWriter().write(JsonUtil.result(false, "保存失败，未接收不能新增！", "", ""));
			return;
		}
	
		if(productList.size()==0){
			throw new DJException("请填写产品！");
		}
		
		firstproductdemandDao.SaveFirstproduct(f, productList);
		response.getWriter().write(JsonUtil.result(true,"保存成功","",""));
	} catch (DJException e) {
		response.getWriter().write(JsonUtil.result(false,e.getMessage(),"",""));
	}
}



/**
 *  获取主键ID
 *  获取编码
 * @param request
 * @param response
 * @throws IOException
 */
@RequestMapping("getSDproductfid")
public void getSDFidFnumber(HttpServletRequest request,HttpServletResponse response) throws IOException{
	Map map = new HashMap();
	List list = new ArrayList();
	map.put("fid", firstproductdemandDao.CreateUUid());
	list.add(map);
	response.getWriter().write(JsonUtil.result(true, "", "",list));
}

/**
 * 获取需求产品信息
 * @param request
 * @param response
 * @throws IOException
 */
@RequestMapping("getFirstProductInfo")
public void getSchemeDesignInfo(HttpServletRequest request,HttpServletResponse response) throws IOException{
	String fid = request.getParameter("fid");
	try {
		String sql = "";
		if(firstproductdemandDao.QueryExistsBySql("select fid from t_ord_productstructure where schemedesignid='"+fid+"'"))
		{
			sql="SELECT p.schemedesignid fid,p.fcustomerid fcustomerid_fid,c.fname fcustomerid_fname,p.fsupplierid  from t_ord_productstructure p left join t_bd_customer c on c.fid=p.fcustomerid  left join t_sys_supplier s on s.fid=p.fsupplierid where p.schemedesignid='"+fid+"' and fparentid is null ";
		}else
		{
			sql="SELECT p.fid,p.fcustomerid fcustomerid_fid,c.fname fcustomerid_fname from t_ord_firstproductdemand p left join t_bd_customer c on c.fid=p.fcustomerid where p.fid='"+fid+"'";

		}
		List<HashMap<String, Object>> list = firstproductdemandDao.QueryBySql(sql);
		response.getWriter().write(JsonUtil.result(true,"","", list));
	} catch (DJException e) {
		response.getWriter().write(JsonUtil.result(false,e.getMessage(),"",""));
	}
	
}

/**
 * 删除附件
 * @param request
 * @param response
 * @throws IOException
 */
@RequestMapping("delFirstProductfile")
public void delFirstProductfile(HttpServletRequest request,HttpServletResponse response) throws IOException{
	String fid = request.getParameter("fid");
	String fpath = request.getParameter("fpath");
	String fparentid = request.getParameter("fparentid");
	try {
		String firstfid=request.getParameter("firstfid");
		 Firstproductdemand firstinfo=(Firstproductdemand)firstproductdemandDao.Query(Firstproductdemand.class, firstfid);
		 if( !StringUtils.isEmpty(firstinfo.getFstate())&&("已关闭".equals(firstinfo.getFstate())||"已完成".equals(firstinfo.getFstate())))
			{
			 throw new DJException("需求已关闭或已完成，不能上传附件");
			}
		UploadFile.delFile(fid);
		response.getWriter().write(JsonUtil.result(true, "删除成功","",""));
	} catch (DJException e) {
		response.getOutputStream().write(e.getMessage().getBytes());
	}
}

/**
 * 获取需求状态，已确认可编辑，已关闭、已完成 不可编辑
 * @param request
 * @param response
 * @throws IOException
 */
@RequestMapping("getFirstProductisEdit")
public void getFirstProductisEdit(HttpServletRequest request,HttpServletResponse response) throws IOException{
	String fid = request.getParameter("fid");
	try {
		String fstate="edit";
		 if(!firstproductdemandDao.QueryExistsBySql("select 1 from t_ord_SchemeDesign where fconfirmed = 1 and ffirstproductid= '"+fid+"' ")){
				response.getWriter().write(JsonUtil.result(false, "确认的方案设计才能编辑！", "", ""));
				return;
		}
		//方案未确认 不可以新增
		 Firstproductdemand firstinfo=(Firstproductdemand)firstproductdemandDao.Query(Firstproductdemand.class, fid);
		if( !StringUtils.isEmpty(firstinfo.getFstate())&&("已关闭".equals(firstinfo.getFstate())||"已完成".equals(firstinfo.getFstate())))
		{
			fstate="view";
		}
		response.getWriter().write(JsonUtil.result(true, fstate,"",""));
	} catch (DJException e) {
		response.getOutputStream().write(e.getMessage().getBytes());
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
@RequestMapping("uploadFirstProductFile")
public String uploadFirstProductFile(HttpServletRequest request,HttpServletResponse response) throws IOException, FileUploadException{
	String fparentid = request.getParameter("fparentid");
	String firstfid=request.getParameter("firstfid");
	 Firstproductdemand firstinfo=(Firstproductdemand)firstproductdemandDao.Query(Firstproductdemand.class, firstfid);
	 if( !StringUtils.isEmpty(firstinfo.getFstate())&&("已关闭".equals(firstinfo.getFstate())||"已完成".equals(firstinfo.getFstate())))
		{
		 throw new DJException("需求已关闭或已完成，不能上传附件");
		}
	String ftype = new String(request.getParameter("ftype").getBytes("ISO-8859-1"),"UTF-8");
	String fdescription = new String(request.getParameter("fdescription").getBytes("ISO-8859-1"),"UTF-8");
//	String fname = "";
//	fname = new String(request.getParameter("fname").getBytes("ISO-8859-1"),"UTF-8");
	String msg = null;
//	String fpath = request.getServletContext().getRealPath("/file/Maigao");
	String fpath = request.getServletContext().getRealPath(RELATIVE_PATH).replace("\\", "/");
	HashMap<String, String> map = new HashMap<String, String>();
	map.put("fparentid", fparentid);
	map.put("ftype", ftype);
	map.put("fdescription", fdescription);
	map.put("fpath", fpath);
//	map.put("fname", fname);
	
	try {
		UploadFile.upload(request, map);
		msg = "附件上传成功！";
	} catch (DJException e) {
		msg =  e.getMessage();
	}
	response.getWriter().write(JsonUtil.result(true,msg, "",""));
	return null;
}



/**
 * 查询要删除的产品是否有附件，并且状态是否允许删除
 * @param request
 * @param response
 * @return
 * @throws IOException
 */
@RequestMapping("getproductIsDeleteFile")
public String getproductIsDeleteFile(HttpServletRequest request,HttpServletResponse response) throws IOException{
	String fid = request.getParameter("fid");//产品id
	String firstfid= request.getParameter("firstfid");//需求名称
	try {
		 Firstproductdemand firstinfo=(Firstproductdemand)firstproductdemandDao.Query(Firstproductdemand.class, firstfid);
		if( !StringUtils.isEmpty(firstinfo.getFstate())&&("已关闭".equals(firstinfo.getFstate())||"已完成".equals(firstinfo.getFstate())))
		{
			 throw new DJException("需求已关闭或已完成，不能操作");
		}
		UploadFile.delFileByParentId(fid);
		response.getWriter().write("{success:true}");
	} catch (DJException e) {
		response.getWriter().write(JsonUtil.result(false, e.getMessage(), "", ""));
	}
	return null;
}


}






