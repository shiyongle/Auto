package Com.Controller.SDK;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.tomcat.util.http.fileupload.FileItem;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItemFactory;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;
import org.hibernate.util.StringHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.dongjing.api.DongjingClient;

import Com.Base.Util.DJException;
import Com.Base.Util.DataUtil;
import Com.Base.Util.ImageUtil;
import Com.Base.Util.JsonUtil;
import Com.Base.Util.UploadFile;
import Com.Base.Util.params;
import Com.Controller.order.SchemeDesignController;
import Com.Dao.System.IBaseSysDao;
import Com.Dao.order.IDeliverorderDao;
import Com.Dao.order.ISchemeDesignDao;
import Com.Dao.traffic.ITruckassembleDao;
import Com.Entity.System.SysUser;
import Com.Entity.System.Useronline;
import Com.Entity.order.Schemedesign;

@Controller
public class SchemedesignSDKControl {

	Logger log = LoggerFactory.getLogger(SchemedesignSDKControl.class);

	@Resource
	private ITruckassembleDao schemedesignDao;
	
	@Resource
	private IBaseSysDao baseSysDao;
	
	private boolean iscreatTruckassemble=false;
	

	@RequestMapping(value = "/SchemedesignSDKUploadFile")
	public String SchemedesignSDKUploadFile(
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String fparentid = request.getParameter("fparentid");
		if(schemedesignDao.QueryExistsBySql("select 1 from t_ord_SchemeDesign where fconfirmed = 1 and fid = '"+fparentid+"' ")){
			response.getWriter().write(JsonUtil.result(false, "已确认的方案设计不能上传附件！", "", ""));
			return null;
		}
		String ftype = "效果图";
		if(request.getParameter("ftype")!=null && !request.getParameter("ftype").equals("")){
			ftype = new String(request.getParameter("ftype").getBytes("ISO-8859-1"),"UTF-8");
		}
		String fdescription = "";
		if(request.getParameter("fdescription")!=null && !request.getParameter("fdescription").equals("")){
			fdescription = new String(request.getParameter("fdescription").getBytes("ISO-8859-1"),"UTF-8");
		}
		
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("fparentid", fparentid);
		map.put("ftype", ftype);
		map.put("fdescription", fdescription);
		
		try {
//			UploadFile.upload(request, map);
			UploadFile.upload(request,fparentid,true);
			response.getWriter().write(JsonUtil.result(true,"附件上传成功！", "",""));
		} catch (DJException e) {
			response.getWriter().write(JsonUtil.result(false,e.getMessage(), "",""));
		}
		
		return null;
	}
	
	@RequestMapping(value = "/getSchemedesignSDKinfo")
	public String getSchemedesignSDKinfo(
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String designId = request.getParameter("designId");
		String imgName = "JPEG,JPG";
		String IP = InetAddress.getLocalHost().getHostAddress()+":8080";
		String result = "";
		String file = "";
		try {
			
			String sql = "select s.fid,REPLACE(REPLACE(pf.fpath,'D:\\\\tomcat\\\\webapps','"+IP+"'),'D:/tomcat/webapps','"+IP+"') fpath,s.fname,s.fnumber from t_ord_schemedesign s  left join t_ord_productdemandfile pf on s.fid = pf.fparentid where s.fid ='"+designId +"' group by pf.fid order by pf.fcreatetime desc";
			List<HashMap<String,Object>> schemedesignList = schemedesignDao.QueryBySql(sql);
			if(schemedesignList.size()>0){
			
				for(HashMap<String,Object> list : schemedesignList){
					if(!StringUtils.isEmpty((String)list.get("fpath"))){
						String path = (String)list.get("fpath");
						String name = path.substring(path.lastIndexOf(".")+1, path.length()).toUpperCase();
						if((","+imgName+",").contains(","+name+",")){
							file += String.format("{\"url\":\"%s\",\"type\":1}", path)+",";
						}
					}
					
				}
				if(file.length()>0){
					file = file.substring(0,file.lastIndexOf(","));
				}
				
			}
			result = JsonUtil.result(true, "","","{\"fid\":\""
					+ schemedesignList.get(0).get("fid") + "\",\"fname\":\""
					+ schemedesignList.get(0).get("fname") + "\",\"fnumber\":\""+ schemedesignList.get(0).get("fnumber") + "\",\"file\":["+ file + "]}");
			response.getWriter().write(result);
		} catch (DJException e) {
			// TODO: handle exception
			response.getWriter().write(JsonUtil.result(false, e.getMessage(),"",""));
		}
		return null;
	}
	
	@RequestMapping(value = "/OnlineConversation")
	public String OnlineConversation(
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String result = "";
		try {
			
			String userid = ((Useronline) request.getSession().getAttribute("Useronline")).getFuserid();
			
			List<SysUser> userlist = schemedesignDao.QueryByHql("From SysUser where fid = '"+userid+"'");
			
			if(userlist.size()>0){
				
				SysUser userinfo = userlist.get(0);
				
				result = JsonUtil.result(true, "","","{\"fimid\":\""
						+ userinfo.getFimid() + "\",\"sessionid\":\""+ request.getSession().getId() + "\"}");
			}
			response.getWriter().write(result);
		} catch (DJException e) {
			// TODO: handle exception
			response.getWriter().write(JsonUtil.result(false, e.getMessage(),"",""));
		}
		return null;
	}
	
	@RequestMapping(value = "/getDesignListByGroupId")
	public String getDesignListByGroupId(
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String result = "";
		try {
			String sessionid=request.getSession().getId();//request.getParameter("sessionid").toString();
			String groupid=request.getParameter("groupId").toString();
			List<HashMap<String, Object>> rlist=null;
			List<Useronline> uolist= schemedesignDao.QueryByHql("FROM Useronline where fsessionid='"+sessionid+"'");
			if(uolist.size()>0)
			{
				String userid =uolist.get(0).getFuserid();
				SysUser uinfo=(SysUser)schemedesignDao.Query(SysUser.class, userid);
				if(uinfo.getFisreadonly()==1)
				{
					 rlist=getDesignListCols("('"+uinfo.getFid()+"')",groupid);
				}else
				{
					String sqls = "select quote(fuserid) userid from t_bd_usertouser where fsuperuserid='"+uinfo.getFid()+"'";
					List<HashMap<String, Object>> demandList =schemedesignDao.QueryBySql(sqls);
					StringBuilder userids=new StringBuilder("'"+userid+"'");
					if(demandList.size()!=0){//关联用户过滤
						for(HashMap<String, Object> m : demandList){
							userids.append(","+m.get("userid"));
						}
					}
					rlist=getDesignListCols("("+userids+")",groupid);
					
				}
				if(rlist.size()==0)  result = "{\"success\":false,\"msg\":\"没有权限可查看\"}";
				else result=JsonUtil.resultIO(true,"","",rlist);
			}else
			{
				result = "{\"success\":false,\"msg\":\"session无效！\"}";
			}

			response.getWriter().write(result);
		} catch (DJException e) {
			// TODO: handle exception
			response.getWriter().write(JsonUtil.result(false, e.getMessage(),"",""));
		}
		return null;
	}
	private List getDesignListCols(String userid,String groupid)
	{
		List resultlist=new ArrayList();
		HashMap map=null;
		String sql="";
		//设计师
		if(!schemedesignDao.QueryExistsBySql("select fid from t_ord_firstproductdemand where fgroupid='"+groupid+"' and fcreatid in "+ userid))
		{
			 sql ="select fid,fname from t_ord_schemedesign where fgroupid='"+groupid+"' and  fcreatorid in" +userid;
		}else//客户帐号
		{
			 sql ="select fid,fname from t_ord_schemedesign where fgroupid='"+groupid+"'";			
		}
		List<HashMap<String, Object>> slist=schemedesignDao.QueryBySql(sql);
		for(int i=0;i<slist.size();i++)
		{
			map=new HashMap();
			map.put("id", (String)slist.get(i).get("fid"));
			map.put("name", (String)slist.get(i).get("fname"));
			resultlist.add(map);
		}
		return  resultlist;
			
	}
	
	@RequestMapping(value = "/getCustOrderInfoSDKinfo")
	public String getCustOrderInfoSDKinfo(
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		response.setCharacterEncoding("utf-8");
		String fimid = request.getParameter("fimid");
		try {
			StringBuffer sql = new StringBuffer("SELECT u.`FTYPE`,u.`FNAME`,IFNULL(cust.`FNAME`,'无关联客户') AS custname, ");
			sql.append("IFNULL(ad.`FNAME`,'无默认地址') AS address,pp.fnumber as lastorderno, ");
			sql.append("CASE pp.`fboxtype` WHEN 0 THEN pdt.fname ELSE CONCAT(`pdt`.`FNAME`,' ',`pdt`.`FNUMBER`,'','/',`pdt`.`FLAYER`,`pdt`.`FTILEMODELID`) END AS pdtname ");
			sql.append("FROM t_sys_user u LEFT JOIN t_bd_usercustomer  c ON u.`FID` = c.`FUSERID` ");
			sql.append("LEFT JOIN t_bd_customer cust ON cust.fid = c.`FCUSTOMERID`  LEFT JOIN ");
			sql.append("t_bd_useraddress ud ON ud.`fuserid` = u.`FID` LEFT JOIN t_bd_address ad ON ad.`FID` = ud.`faddress` " );
		    sql.append("LEFT JOIN t_ord_productplan pp ON pp.`FCUSTOMERID` = cust.`fid` ");
		    sql.append(" LEFT JOIN t_pdt_productdef pdt ON pdt.fid = pp.`FPRODUCTDEFID` ");
			sql.append("where u.fimid =:fid ORDER BY pp.fcreatetime DESC LIMIT 1 ");
			params p = new params();
			p.put("fid", fimid);
			List<HashMap<String, Object>> result = schemedesignDao.QueryBySql(sql.toString(), p);
//			String resultStr = "";
//			resultStr = Json.Util.result(true, "","","{\"FTYPE\":\""
//					+ result.get(0).get("FTYPE") + "\",\"FNAME\":\""
//					+ result.get(0).get("FNAME") + "\",\"custname\":\""+ result.get(0).get("custname") + "\",\"address\":\""+ result.get(0).get("address")+"\"}");
//			response.getWriter().write(resultStr);
			response.getWriter().write(JsonUtil.result(true, "", "", result));
		} catch (DJException e) {
			// TODO: handle exception
			response.getWriter().write(JsonUtil.result(false, e.getMessage(),"",""));
		}
		return null;
	}	
	
	@RequestMapping(value = "/getCustOrderInfoSDKinfoByGroup")
	public String getCustOrderInfoSDKinfoByGroup(
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		response.setCharacterEncoding("utf-8");
		String gpid = request.getParameter("gpid");
		try {
			StringBuffer sql = new StringBuffer("SELECT pm.`fcustomerid`,u.`FIMID` FROM t_ord_firstproductdemand pm  ");
			sql.append("LEFT JOIN t_sys_user u ON u.`FID` = pm.`fcreatid` where `fgroupid` = '"+gpid+"' ");
			//String FIMID = baseSysDao.getStringValue(sql.toString(),"FIMID");
			String FIMID = "";
			String custid = "";
			//2015-05-09  此处有蹊跷,一定要用?,不然报错		
			HashMap<String, ?> map = baseSysDao.getMapData(sql.toString());
			if(map!=null){
				FIMID = map.get("FIMID")+"";
				custid = map.get("fcustomerid").toString();
			}
			sql.setLength(0);
			sql.append("SELECT '"+FIMID+"' as fimid, cust.`FNAME` AS custname, deo.`fplanNumber` AS lastorderno,deo.`faddress` AS address, ");
			sql.append("CASE deo.`fboxtype` WHEN 0 THEN pdt.fname ELSE CONCAT(`pdt`.`FNAME`,' ',`pdt`.`FNUMBER`,'','/',`pdt`.`FLAYER`,`pdt`.`FTILEMODELID`) END AS pdtname ");
			sql.append("FROM t_ord_deliverorder deo LEFT JOIN t_pdt_productdef pdt ON pdt.`FID` = deo.`fproductid` ");
			sql.append("LEFT JOIN t_bd_customer cust ON deo.`fcustomerid` = cust.`fid` ");
			sql.append("where  cust.`fid` = '"+custid+"' ORDER BY deo.`fcreatetime` DESC LIMIT 1 ");
			List<HashMap<String, Object>> result = schemedesignDao.QueryBySql(sql.toString());
			response.getWriter().write(JsonUtil.result(true, "", "", result));
		} catch (DJException e) {
			// TODO: handle exception
			response.getWriter().write(JsonUtil.result(false, e.getMessage(),"",""));
		}
		return null;
	}	
}