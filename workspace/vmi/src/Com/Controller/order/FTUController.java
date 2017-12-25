package Com.Controller.order;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.CellView;
import jxl.Workbook;
import jxl.format.Alignment;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.gson.JsonArray;

import Com.Base.Util.DJException;
import Com.Base.Util.ExcelUtil;
import Com.Base.Util.JsonUtil;
import Com.Base.Util.ListResult;
import Com.Base.Util.ServerContext;
import Com.Base.Util.mySimpleUtil.MySimpleToolsZ;
import Com.Dao.System.IBaseSysDao;
import Com.Dao.order.IFTUDao;
import Com.Entity.System.Address;
import Com.Entity.System.CustRelationAdress;
import Com.Entity.System.Customer;
import Com.Entity.System.Custproduct;
import Com.Entity.System.Productreqallocationrules;
import Com.Entity.System.Useronline;
import Com.Entity.order.FTUSaledeliver;
import Com.Entity.order.FTUSaledeliverEntry;
import Com.Entity.order.FtuParameter;
import Com.Entity.order.FtuTemplate;

@Controller
public class FTUController {

	@Resource
	private IFTUDao ftuDao;
	@Resource
	private IBaseSysDao baseSysDao;
	
	@RequestMapping("getFtuConcat")
	public String getFtuConcat(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		
//		String number = ftuDao.getFtuFnumber();
		try {
			String userid = ((Useronline)request.getSession().getAttribute("Useronline")).getFuserid();
			String sql="select fsupplierid from ( select fsupplierid  from t_bd_usersupplier where fuserid='"+userid+"'"+
					" union select c.fsupplierid from  t_sys_userrole r "+
					" left join t_bd_rolesupplier c on r.froleid=c.froleid where r.fuserid='"+userid+"' ) s where fsupplierid is not null " ;
			List<HashMap<String,Object>> list=ftuDao.QueryBySql(sql);
			if(list.size()>0){
				String fsupplierid = (String)list.get(0).get("fsupplierid");
				String number = ServerContext.getNumberHelper().getFtuNumber(fsupplierid);
				String fsuppliername = baseSysDao.getStringValue("t_sys_supplier", fsupplierid, "fname");
				sql = "select fphone,ffax,'"+userid+"' fuserid,'"+number+"' fnumber,fcustomername  fcustname,'"+fsupplierid+"' fsupplierid,'"+fsuppliername+"' fsname,fcustomername  fsuppliername from t_sys_user where fid = '"+userid+"'";
				list = ftuDao.QueryBySql(sql);
				response.getWriter().write(JsonUtil.result(true, "", "",list));
			}else{
				response.getWriter().write(JsonUtil.result(false,"当前用户没有关联制造商，请联系管理员！", "",""));
			}
			
		} catch (DJException e) {
			response.getWriter().write(JsonUtil.result(false, e.getMessage(), "",""));
		}
		return null;
	}
	
	@RequestMapping("selectFtuCustomer")
	public String selectFtuCustomer(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		try {
//			List<HashMap<String, Object>> list = ftuDao.getFtuRelationName(request, 0);
			String fname = request.getParameter("fname");
			String sql = "select c.fid,c.fname,c.fphone ftel from t_bd_customer c left join t_pdt_productreqallocationrules p on c.fid=p.fcustomerid where 1=1 "+ftuDao.QueryFilterByUser(request,null,"p.fsupplierid");
			if(!StringUtils.isEmpty(fname)){
				String str=new String(fname.getBytes("ISO-8859-1"),"UTF-8");
				sql += " and c.fname like '%"+str+"%'";
			}
			List list = ftuDao.QueryBySql(sql);
			response.getWriter().write(JsonUtil.result(true, "", "",list));
		} catch (DJException e) {
			response.getWriter().write(JsonUtil.result(false, "", "",e.getMessage()));
		}
		return null;
	}
	@RequestMapping("selectFtuDriver")
	public String selectFtuDriver(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		try {
			List<HashMap<String, Object>> list = ftuDao.getFtuRelationName(request, 1);
			response.getWriter().write(JsonUtil.result(true, "", "",list));
		} catch (DJException e) {
			response.getWriter().write(JsonUtil.result(false, "", "",e.getMessage()));
		}
		return null;
	}
	@RequestMapping("selectFtuClerk")
	public String selectFtuClerk(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		try {
			List<HashMap<String, Object>> list = ftuDao.getFtuRelationName(request, 2);
			response.getWriter().write(JsonUtil.result(true, "", "",list));
		} catch (DJException e) {
			response.getWriter().write(JsonUtil.result(false, "", "",e.getMessage()));
		}
		return null;
	}
	
	@RequestMapping("selectFtuBusNumber")
	public String selectFtuBusNumber(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		try {
			List<HashMap<String, Object>> list = ftuDao.getFtuRelationName(request, 3);
			response.getWriter().write(JsonUtil.result(true, "", "",list));
		} catch (DJException e) {
			response.getWriter().write(JsonUtil.result(false, "", "",e.getMessage()));
		}
		return null;
	}
	
	@RequestMapping("selectProductsByCustomer")
	public String selectProductsByCustomer(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		try {
			String queryName = request.getParameter("fname");
			String fcustomerid = request.getParameter("fcustomerid");
			String sql = "select CONCAT_WS(' / ',c.fname,c.fspec,''+c.fprice) fviewname,c.fcreatetime,c.fproductid,c.fid,c.fname,c.fspec,c.fprice,c.funit,c.fdescription from t_bd_custproduct c left join t_pdt_productdef p on c.fproductid=p.fid where 1=1 and c.fcustomerid='"+fcustomerid+"'"+ftuDao.QueryFilterByUser(request,null,"p.fsupplierid");
			if(!StringUtils.isEmpty(queryName)){
				String str=new String(queryName.getBytes("ISO-8859-1"),"UTF-8");
				sql += String.format("and (c.fname like '%s' or c.fspec like '%s' )","%"+str+"%","%"+str+"%");
			}
//			request.setAttribute("nolimit", "true");
			ListResult result = ftuDao.QueryFilterList(sql, request);
			response.getWriter().write(JsonUtil.result(true,"", result));
		} catch (DJException e) {
			response.getWriter().write(JsonUtil.result(false, "", "",e.getMessage()));
		}
		return null;
	}
	
	@RequestMapping("saveFtuSaleDeliver")
	public String saveFtuSaleDeliver(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String userid = ((Useronline)request.getSession().getAttribute("Useronline")).getFuserid();
		try {
			List<Custproduct> productList = (List<Custproduct>)request.getAttribute("Custproduct");
			if(productList==null){
				throw new DJException("请填写完整产品信息！");
			}
			if(productList.size()<1){
				throw new DJException("请填写产品信息！");
			}
			String ftel = request.getParameter("ftel");					//订单电话
			String ffax = request.getParameter("ffax");					//传真
			String fnumber = request.getParameter("fnumber");			//编号
			String fcustomer = request.getParameter("fcustomer");		//终端客户
			String fphone = request.getParameter("fphone");				//联系电话
			String fclerk = request.getParameter("fclerk");				//开单员
			String fdriver = request.getParameter("fdriver");			//司机
			String fsupplierid = request.getParameter("fsupplierid");	//客户id
			String fsuppliername = request.getParameter("fsuppliername");		//客户名称
			String fcustAddress = request.getParameter("fcustAddress"); //客户地址
			String fbusNumber = request.getParameter("busNumber");//车牌号码
			String fid = request.getParameter("fid");
			
			if(StringUtils.isEmpty(fnumber)){
				throw new DJException("编号不能为空！");
			}
			if(StringUtils.isEmpty(fcustomer)){
				throw new DJException("客户不能为空！");
			}
			
			FTUSaledeliver saledeliver = null;
			if(StringUtils.isEmpty(fid)){
				if(ftuDao.QueryExistsBySql("select 1 from t_ftu_saledeliver where fnumber='"+fnumber+"'"+ftuDao.QueryFilterByUser(request, null,"fsupplierid"))){
					throw new DJException("编号重复，无法保存！");
				}
				saledeliver = new FTUSaledeliver();
				saledeliver.setFid(ftuDao.CreateUUid());
				saledeliver.setFcreatetime(new Date());
				saledeliver.setFcreator(userid);
			}else{
				saledeliver = (FTUSaledeliver)ftuDao.Query(FTUSaledeliver.class, fid);
			}
			
			Customer c = new Customer();
			String sql = "select c.fid from t_bd_customer c left join t_pdt_productreqallocationrules p on c.fid=p.fcustomerid where (c.fname='"+fcustomer+"' or c.fid='"+fcustomer+"')"+ftuDao.QueryFilterByUser(request, null,"p.fsupplierid");
			List<HashMap<String,Object>>map = ftuDao.QueryBySql(sql);
			if(map.size()==0){//客户
				c.setFid(ftuDao.CreateUUid());
				c.setFname(fcustomer);
				c.setFphone(fphone);
				c.setFnumber(fcustomer);
				c.setFcreatetime(new Date());
				ftuDao.saveOrUpdate(c);
				
				Productreqallocationrules p = new Productreqallocationrules();
				p.setFid(ftuDao.CreateUUid());
				p.setFcreatetime(new Date());
				p.setFcreatorid(userid);
				p.setFcustomerid(c.getFid());
				p.setFsupplierid(fsupplierid);
				ftuDao.saveOrUpdate(p);
				
			}else{
				c.setFid((String)map.get(0).get("fid"));
				sql = "update t_bd_customer set fphone='"+fphone+"' where fid='"+c.getFid()+"'";
				ftuDao.ExecBySql(sql);
			}
			sql = "select fid,fname from t_bd_address where (fname ='"+fcustAddress+"' or fid ='"+fcustAddress+"') and fcustomerid='"+c.getFid()+"'";
			map = ftuDao.QueryBySql(sql);
			Address address = new Address();
			if(!StringUtils.isEmpty(fcustAddress)){
				if(map.size()==0){//客户地址
					address.setFid(ftuDao.CreateUUid());
					address.setFname(fcustAddress);
					address.setFcreatetime(new Date());
					address.setFcustomerid(c.getFid());
					address.setFdetailaddress(fcustAddress);
					ftuDao.saveOrUpdate(address);
					
					CustRelationAdress cd = new CustRelationAdress();
					cd.setFid(ftuDao.CreateUUid());
					cd.setFaddressid(address.getFid());
					cd.setFcustomerid(c.getFid());
					cd.setFeffect(1);
					ftuDao.saveOrUpdate(cd);
				}else{
					address.setFname((String)map.get(0).get("fname"));
				}
			}
			
			
			saledeliver.setFsupplierid(fsupplierid);
			saledeliver.setFsuppliername(fsuppliername);
			saledeliver.setFnumber(fnumber);
			saledeliver.setFcustomer(c.getFid());
			saledeliver.setFphone(fphone);
			saledeliver.setFclerk(fclerk);
			saledeliver.setFdriver(fdriver);
			saledeliver.setFfax(ffax);
			saledeliver.setFtel(ftel);
			saledeliver.setFbusNumber(fbusNumber);
			saledeliver.setFcustAddress(address.getFname());
			
			ftuDao.saveFTUSaleDeliver(saledeliver,productList);
			response.getWriter().write(JsonUtil.result(true, saledeliver.getFid(),"" ,""));
		} catch (DJException e) {
			response.getWriter().write(JsonUtil.result(false, e.getMessage(),"", ""));
		}
		return null;
	}
	@RequestMapping("getFTUsaledeliver")
	public void getFTUsaledeliver(HttpServletRequest request,
			HttpServletResponse response) throws IOException{
		try {
			String userid = ((Useronline)request.getSession().getAttribute("Useronline")).getFuserid();
			String sql = "select fcustomername from t_sys_user where fid='"+userid+"'";
			List<HashMap<String,Object>> map = ftuDao.QueryBySql(sql);
			sql ="SELECT sd.string1,sd.string2,sd.string3,sd.string4,sd.string5,sd.string6,sd.string7,sd.string8,sd.string9,sd.string10,'"+map.get(0).get("fcustomername")+"' fcustname,p.fproductid,sa.fcustomer fcustoermid,sa.fcustAddress,sa.fbusNumber,sa.fstate,c.fname fcustomer,sa.fphone,sa.ffax,sa.ftel,sa.fid,fsupplierid,fsuppliername,sa.`fnumber`,p.`fname`,p.`fspec`,p.`funit`,sd.`famount`,CASE sd.famount WHEN 0 THEN p.fprice ELSE IFNULL(sd.fdanjia,ROUND(sd.fprice / sd.famount, 3)) END fdanjia,sd.`fprice` fprice,sd.`fprice` fprices,sa.`fcreatetime`,sa.fclerk,sa.fdriver,sa.fprinttime,sa.fprintcount,sd.`fdescription` FROM `t_ftu_saledeliver` sa LEFT JOIN `t_ftu_saledeliverentry` sd ON sd.`fparentid`=sa.fid LEFT JOIN t_bd_custproduct p ON sd.`fftuproductid` = p.`fid` left join t_bd_customer c on sa.fcustomer=c.fid where 1=1 and sa.fstate<>1"+ftuDao.QueryFilterByUser(request, null,"sa.fsupplierid");
			request.setAttribute("djsort", "sa.fnumber desc,sd.fsequence");
			ListResult list = ftuDao.QueryFilterList(sql, request);
			response.getWriter().write(JsonUtil.result(true, "",list));
		} catch (DJException e) {
			response.getWriter().write(JsonUtil.result(false, e.getMessage(),"", ""));
		}
	}
	@RequestMapping("getFTUproduct")
	public void getFTUproduct(HttpServletRequest request,
			HttpServletResponse response) throws IOException{
		try {
			String fparentid = request.getParameter("saledeliverid");
			String sql = "SELECT p.fid,p.fname,p.fspec,CASE sa.famount WHEN 0 THEN p.fprice ELSE IFNULL(sa.fdanjia,ROUND(sa.fprice / sa.famount, 3)) END fprice,p.funit,sa.fdescription,sa.`famount`,sa.`fprice` fprices,p.fproductid,p.fcreatetime FROM t_bd_custproduct p LEFT JOIN `t_ftu_saledeliverentry` sa ON sa.`fftuproductid`=p.`fid` where 1=1 and p.feffect=1 and sa.fparentid='"+fparentid+"' order by sa.fsequence";
			List list = ftuDao.QueryBySql(sql);
			response.getWriter().write(JsonUtil.result(true, "","",list));
		} catch (DJException e) {
			response.getWriter().write(JsonUtil.result(false, "", "",e.getMessage()));
		}
	}
	
	@RequestMapping("getFTUList")
	public void getFTUList(HttpServletRequest request,
			HttpServletResponse response) throws IOException{
		try {
			String fparentid = request.getParameter("saledeliverid");
			String size = request.getParameter("size");
			int psize = 9999;
			if(!StringUtils.isEmpty(size)){
				psize = Integer.valueOf(size);
			}
			HashMap<String,Object> paramsMap = new HashMap();
			String sql = "";
			String result = "";
			if(!StringUtils.isEmpty(fparentid)){
				String[] fids = fparentid.split(",");
				for(int i = 0;i<fids.length;i++){
					String sqls = "SELECT  p.fid,p.fname,p.fspec,CASE sa.famount WHEN 0 THEN p.fprice ELSE IFNULL(sa.fdanjia,ROUND(sa.fprice / sa.famount, 3)) END fprice,p.funit,sa.fdescription,sa.`famount`,sa.`fprice` fprices,sa.fparentid FROM t_bd_custproduct p LEFT JOIN t_ftu_saledeliverentry sa ON sa.`fftuproductid` = p.`fid`  where sa.fparentid='"+fids[i]+"' order by sa.fsequence";
					List<HashMap<String,Object>> plist = ftuDao.QueryBySql(sqls);
					double fprices = 0;
					int sum = 0;
					for(HashMap<String,Object> p : plist){
						fprices += Double.valueOf(p.get("fprices").toString());//*Double.valueOf(p.get("famount").toString());
						sum += Double.valueOf(p.get("famount").toString());
					}
					String chinesefprices = toRMB(fprices);
					DecimalFormat myformat = new DecimalFormat();
					myformat.applyPattern("##,###.00");
					String fsumprices = "0";
					if(fprices!=0){
						fsumprices = myformat.format(fprices);
					}
					String proresult = JsonUtil.result(true, "","",plist);
					
					String userid = ((Useronline)request.getSession().getAttribute("Useronline")).getFuserid();
					sql = "select fcustomername from t_sys_user where fid='"+userid+"'";
					List<HashMap<String,Object>> map = ftuDao.QueryBySql(sql);//获取用户信息里面的客户名称
					
					 sql = "SELECT '"+map.get(0).get("fcustomername")+"' fcustname,s.fcustAddress,s.fid,s.fsuppliername,s.fsupplierid,s.fnumber,SUBSTR(s.fcreatetime,1,10) fcreatetime,s.fcreator,s.ffax,s.ftel,s.fdriver,s.fclerk,c.fname fcustomer,s.fphone FROM t_ftu_saledeliver s left join t_bd_customer c on c.fid=s.fcustomer where s.fid='"+fids[i]+"'";
					List<HashMap<String,Object>> salelist = ftuDao.QueryBySql(sql);
					if(plist.size()>psize){
						paramsMap = new HashMap();
						int d = (int) Math.ceil((double)plist.size()/psize);//取不小于它的整数
						int start = 0;
						int end = 0;
						for(int x= 0;x<d;x++){
							double fpricess = 0;
							int sums = 0;
							List<HashMap<String,Object>> pplist = ftuDao.QueryBySql(sqls+" limit "+start+","+psize);
							proresult = JsonUtil.result(true, "","",pplist);
							for(HashMap<String,Object> p : pplist){
								fpricess += Double.valueOf(p.get("fprices").toString());//*Double.valueOf(p.get("famount").toString());
								sums += Double.valueOf(p.get("famount").toString());
							}
							 chinesefprices = toRMB(fpricess);
							 fsumprices = "0";
							 if(fprices!=0){
								fsumprices = myformat.format(fpricess);
							}
							 paramsMap.put("psize", pplist.size());
							 paramsMap.put("fsumprices", fsumprices);
							 paramsMap.put("chinesefprices", chinesefprices);
							 paramsMap.put("sum", sums);
							 paramsMap.put("number", (String)salelist.get(0).get("fnumber")+"-"+(x+1));
							 paramsMap.put("plist", proresult.substring(proresult.indexOf("["),proresult.lastIndexOf("]")+1));
							result += List2Json(salelist,paramsMap);
							start += psize;
//							end += psize;
							if(x<d-1){
								result +=",";
							}
						}
						
					}else{
//						paramsMap.put("salelist", salelist);
						paramsMap.put("plist", proresult.substring(proresult.indexOf("["),proresult.lastIndexOf("]")+1));
						paramsMap.put("psize", plist.size());
						paramsMap.put("fsumprices", fsumprices);
						paramsMap.put("chinesefprices", chinesefprices);
						paramsMap.put("sum" ,sum);
						paramsMap.put("number", salelist.get(0).get("fnumber"));
						result += List2Json(salelist,paramsMap);
					}
					if(i<fids.length-1){
						result +=",";
					}
					//更新打印时间和打印次数
					ftuDao.saveFTUprint(fids[i]);
				}
			}
			response.getWriter().write("{\"success\":" + true+ ",\"data\":[" + result + "]}");
		} catch (DJException e) {
			// TODO: handle exception
			response.getWriter().write(JsonUtil.result(false, "", "",e.getMessage()));
		}
	}
	public static String List2Json(List<HashMap<String,Object>> slist,HashMap<String, Object> map) {
		String result = "";
		if (slist.size() < 1) {
			return "";
		}
		int index = 0;
		String val;
		Iterator<String> it;
		HashMap<String, Object> h = slist.get(0);
		Set<String> set = h.keySet();
		for (HashMap<String, Object> o : slist) {
			it = set.iterator();
			if (index > 0) {
				result = result + ",";
			}
			result = result + "{";
			int keyindex = 0;
			while (it.hasNext()) {
				if (keyindex > 0) {
					result = result + ",";
				}
				String key = it.next();
				val = o.get(key)!=null?o.get(key).toString().replace("\r\n", "<br/>").replace("\r", "<br/>").replace("\n", "<br/>").replace("\\", "\\\\").replace("\"", "\\\""):"";
				result = result + "\"" + key + "\"" + ":\"" + val + "\"";
				keyindex++;
			}
			result = result +",\""+"number"+"\""+ ":\"" + map.get("number") + "\""+",\""+"sum"+"\""+ ":\"" + map.get("sum") + "\""+",\""+"fsumprices"+"\""+ ":\"" + map.get("fsumprices") + "\""+",\""+"chinesefprices"+"\""+ ":\"" + map.get("chinesefprices") + "\""+",\""+"psize"+"\""+ ":\"" + map.get("psize") + "\""+ ",\"" + "product" + "\"" + ":"+map.get("plist")+ "}";
			index++;
		}
		return result;
	}
	public static void main(String[] args) {
		DecimalFormat myformat = new DecimalFormat();
		myformat.applyPattern("##,###.000");
		System.out.println(myformat.format(0.0));
	}
	public static String toRMB(double money) {  
	    char[] s1 = {'零', '壹', '贰', '叁', '肆', '伍', '陆', '柒', '捌', '玖'};  
	    char[] s4 = {'分', '角', '元', '拾', '佰', '仟', '万', '拾', '佰', '仟', '亿', '拾', '佰', '仟', '万'};  
	    if(money>100000000){
	    	return "";
	    }
	    String str = String.valueOf(Math.round(money * 100 + 0.00001));  
	    String result = "";  
	   
	    for (int i = 0; i <str.length(); i++) {  
			int n = str.charAt(str.length() - 1 - i) - '0';
			result = s1[n] + "" + s4[i] + result;
		}
	   
//		    result = result.replaceAll("零仟", "零");  
//		    result = result.replaceAll("零佰", "零");  
//		    result = result.replaceAll("零拾", "零");  
//	        result = result.replaceAll("零亿", "亿");  
	        result = result.replaceAll("零万", "万");  
//	        result = result.replaceAll("零元", "零");  
//	        result = result.replaceAll("零角", "零");  
//	        result = result.replaceAll("零分", "零");  
//	   
//	        result = result.replaceAll("零零", "零");  
//	        result = result.replaceAll("零亿", "亿");  
//	        result = result.replaceAll("零零", "零");  
//	        result = result.replaceAll("零万", "万");  
//	        result = result.replaceAll("零零", "零");  
//	        result = result.replaceAll("零元", "元");  
//	        result = result.replaceAll("亿万","亿");  
//	 
//	        result = result.replaceAll("零$", "");  
//	        result = result.replaceAll("元$", "元整");  
//	        result = result.replaceAll("角$", "角整");  
	        String result1 = "";
	        if(result.indexOf("万")>0){
	        	result1 =result.substring(0,result.indexOf("万"));
	   	       result1 = result1.replaceAll("零仟", "零");  
	   	       result1 = result1.replaceAll("零佰", "零");  
	   	       result1 = result1.replaceAll("零拾", "零"); 
	   	       result1 = result1.replaceAll("零零", "零");  
	   	       if("零".equals(result1.substring(result1.length()-1))){
	   	    	   result1 = result1.substring(0,result1.length()-1);
	   	       }
	   	    return result1+result.substring(result.indexOf("万"))+"整"; 
	        }
	    return result+"整";  
	    }
	@RequestMapping("delFTUsaledeliver")
	public void delFTUsaledeliver(HttpServletRequest request,
			HttpServletResponse response) throws IOException{
		try {
			String fids = request.getParameter("fidcls");
			fids = "'"+fids.replaceAll(",", "','")+"'";
			String sql = "update t_ftu_saledeliver set fstate=1 where fid in("+fids+")";
			ftuDao.ExecBySql(sql);
			response.getWriter().write(JsonUtil.result(true,"删除成功","",""));
		} catch (DJException e) {
			// TODO: handle exception
			response.getWriter().write(JsonUtil.result(false,"删除失败","",""));
		}
	}
	@RequestMapping("getCustAddress")
	public void getCustAddress(HttpServletRequest request,
			HttpServletResponse response) throws IOException{
		try {
			String fcustomer = request.getParameter("fcustomerid");
			String sql = "select a.fid,a.fname from t_bd_address a left join t_bd_custrelationadress cu on a.fid=cu.faddressid where cu.fcustomerid='"+fcustomer+"'";
			List list = ftuDao.QueryBySql(sql);
			response.getWriter().write(JsonUtil.result(true,"","",list));
		} catch (DJException e) {
			// TODO: handle exception
			response.getWriter().write(JsonUtil.result(false,e.getMessage(),"",""));
		}
	}
	@RequestMapping("updateFTUstate")
	public void updateFTUstate(HttpServletRequest request,
			HttpServletResponse response) throws IOException{
		try {
			String fnumber = request.getParameter("fnumber");
			fnumber = "'"+fnumber.replaceAll(",", "','")+"'";
			String userid = ((Useronline)request.getSession().getAttribute("Useronline")).getFuserid();
			String sql="select fsupplierid from ( select fsupplierid  from t_bd_usersupplier where fuserid='"+userid+"'"+
					" union select c.fsupplierid from  t_sys_userrole r "+
					" left join t_bd_rolesupplier c on r.froleid=c.froleid where r.fuserid='"+userid+"' ) s where fsupplierid is not null " ;
			List<HashMap<String,Object>> list=ftuDao.QueryBySql(sql);
			if(list.size()==0){
				throw new DJException("请关联制造商！");
			}
			sql = "update t_ftu_saledeliver set fstate=2 where fnumber in("+fnumber+") and fsupplierid ='"+list.get(0).get("fsupplierid")+"'";
			ftuDao.ExecBySql(sql);
			response.getWriter().write(JsonUtil.result(true,"回收成功！","",""));
		} catch (DJException e) {
			// TODO: handle exception
			response.getWriter().write(JsonUtil.result(false,e.getMessage(),"",""));
		}
	}
	
	@RequestMapping("getFTUproductByCustomer")
	public void getFTUproductByCustomer(HttpServletRequest request,
			HttpServletResponse response) throws IOException{
		try {
//			String fcustomerid = request.getParameter("fcustomerid");
			String sql = "SELECT sd.fnumber,sd.fprinttime,p.fid,p.fname,p.fspec,sa.fdanjia fprice,p.funit,sa.fdescription,sa.`famount`,sa.`fprice` fprices FROM t_bd_custproduct p LEFT JOIN `t_ftu_saledeliverentry` sa ON sa.`fftuproductid`=p.`fid` LEFT JOIN `t_ftu_saledeliver` sd ON sa.fparentid=sd.fid where 1=1 "+ftuDao.QueryFilterByUser(request, null,"sd.fsupplierid");;
			request.setAttribute("djsort", "sd.fnumber desc,sa.fsequence");
			ListResult list = ftuDao.QueryFilterList(sql, request);
			response.getWriter().write(JsonUtil.result(true, "",list));
		} catch (DJException e) {
			response.getWriter().write(JsonUtil.result(false, "", "",e.getMessage()));
		}
	}
	@RequestMapping("/ExcelFTUstatementList")
	public String ExcelFTUstatementList(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
	
		try {
			String userid = ((Useronline)request.getSession().getAttribute("Useronline")).getFuserid();
			String header = request.getParameter("header");
			String str=new String(header.getBytes("ISO-8859-1"),"UTF-8");
			String sql = "select fcustomername from t_sys_user where fid='"+userid+"'";
			String fsupplier = baseSysDao.getStringValue(sql, "fcustomername");
			request.setAttribute("nolimit","true");
//			request.setAttribute("djsort","sd.fcreatetime");
			request.setAttribute("djsort", "sd.fnumber,sa.fsequence");
			sql = "SELECT sd.fnumber,DATE_FORMAT(sd.fprinttime,'%Y-%m-%d') fprinttime,p.fid,p.fname,p.fspec,sa.fdanjia fprice,p.funit,sa.fdescription,sa.`famount`,sa.`fprice` fprices FROM t_bd_custproduct p LEFT JOIN `t_ftu_saledeliverentry` sa ON sa.`fftuproductid`=p.`fid` LEFT JOIN `t_ftu_saledeliver` sd ON sa.fparentid=sd.fid where 1=1 ";
			switch (MySimpleToolsZ.getMySimpleToolsZ().judgeSearchType(request)) {
			case MySimpleToolsZ.TIME_SEARCH:
				sql += MySimpleToolsZ.getMySimpleToolsZ().buildDateBetweenSQLFragment(request);
				break;
			}
			
			ListResult result = MySimpleToolsZ.getMySimpleToolsZ().buildExcelListResult(sql, request, ftuDao);
			
			List<String> order = MySimpleToolsZ.gainDataIndexList(request);
			
			toexcel(reponse, result, order,str,fsupplier);
		} catch (DJException e) {
			reponse.getWriter().write(
					JsonUtil.result(false, e.getMessage(), "", ""));
		}

		return null;
	}
	public static void toexcel(HttpServletResponse response, ListResult result,
			List<String> order,String header,String fsupplier) throws DJException {
		WritableWorkbook book = null;
		try {
			response.setContentType("multipart/form-data");
			response.setHeader("Content-Disposition",
					"attachment;fileName=" + URLEncoder.encode(header,"utf-8")+ ".xls");
			book = Workbook.createWorkbook(response.getOutputStream());
			WritableSheet sheet = book.createSheet("数据", 0);
			
			List<HashMap<String, Object>> list = result.getData();
			
			Set<String> columns = list.get(0).keySet();
			
			WritableFont bold = new WritableFont(WritableFont.createFont("宋体"),18,WritableFont.BOLD);
			WritableCellFormat headerFormat = new WritableCellFormat(
					bold);
			headerFormat.setAlignment(Alignment.CENTRE);
			sheet.mergeCells(0,0, columns.size(),0);
			sheet.addCell(new Label(0, 0, header,headerFormat));//标题
			
			
			WritableCellFormat colsStyle = new WritableCellFormat(new WritableFont(WritableFont.createFont("宋体"),14));
			colsStyle.setAlignment(Alignment.CENTRE);//列标题样式
			colsStyle.setBorder(jxl.format.Border.ALL,jxl.format.BorderLineStyle.THIN);
			
			WritableCellFormat contentStyle = new WritableCellFormat(new WritableFont(WritableFont.createFont("宋体"),12));
			contentStyle.setAlignment(Alignment.CENTRE);//内容样式
			contentStyle.setBorder(jxl.format.Border.ALL,jxl.format.BorderLineStyle.THIN);
			
			for (String name : columns) {
				sheet.addCell(new Label(0, 1, "序号",colsStyle));
				Label label = new Label(order.indexOf(name)+1, 1, name,colsStyle);
				sheet.addCell(label);
			}
			int rowNum = 2;
			int number = 0;
			BigDecimal famount = BigDecimal.ZERO;
			BigDecimal fprices = BigDecimal.ZERO;
			CellView cellView = new CellView();  
		    cellView.setAutosize(true); //设置自动大小
		    
			for (HashMap<String, Object> data : list) {
				sheet.addCell(new Label(0, rowNum,String.valueOf(++number),contentStyle));
				for (String name : columns) {
//					sheet.setColumnView(order.indexOf(name)+1, cellView);
					Label label = new Label(order.indexOf(name)+1, rowNum,
							data.get(name) == null ? "" : data.get(name)
									.toString(),contentStyle);
					
					
					if("数量".equals(name)){
						sheet.setColumnView(order.indexOf(name)+1, 10);
						if(!StringUtils.isEmpty(data.get(name))){
							famount = famount.add(new BigDecimal(data.get(name).toString()));
						}
						sheet.addCell(new jxl.write.Number(order.indexOf(name)+1, rowNum,StringUtils.isEmpty(data.get(name))?0:Double.valueOf(data.get(name).toString()),contentStyle));
					}else if("金额".equals(name)){
						sheet.setColumnView(order.indexOf(name)+1, 10);
						if(!StringUtils.isEmpty(data.get(name))){
							fprices = fprices.add(new BigDecimal(data.get(name).toString()));
						}
						sheet.addCell(new jxl.write.Number(order.indexOf(name)+1, rowNum,StringUtils.isEmpty(data.get(name))?0:Double.valueOf(data.get(name).toString()),contentStyle));
					}else if("单价".equals(name)){
						sheet.setColumnView(order.indexOf(name)+1, 10);
						sheet.addCell(new jxl.write.Number(order.indexOf(name)+1, rowNum,StringUtils.isEmpty(data.get(name))?0:Double.valueOf(data.get(name).toString()),contentStyle));
					}else if("产品名称".equals(name)||"出库单编号".equals(name)||"规格".equals(name)){
						sheet.setColumnView(order.indexOf(name)+1, 20);
						sheet.addCell(label);
					}else{
						sheet.setColumnView(order.indexOf(name)+1, cellView);
						sheet.addCell(label);
					}
				}
				rowNum++;
			}
			WritableCellFormat totalStyle = new WritableCellFormat(new WritableFont(WritableFont.createFont("宋体"),14,WritableFont.BOLD));
			totalStyle.setAlignment(Alignment.CENTRE);//合计样式
			
			
			totalStyle.setBorder(jxl.format.Border.ALL,jxl.format.BorderLineStyle.THIN);
			
			sheet.mergeCells(0,rowNum, columns.size(),rowNum);
			sheet.addCell(new Label(0, rowNum,"合计:                       数量："+famount+"                  金额："+fprices,totalStyle));
			
			totalStyle = new WritableCellFormat(new WritableFont(WritableFont.createFont("宋体"),14,WritableFont.BOLD));
			totalStyle.setAlignment(Alignment.CENTRE);
			sheet.mergeCells(columns.size()-1,rowNum+2, columns.size(),rowNum+2);
			sheet.addCell(new Label(columns.size()-1, rowNum+2,fsupplier,totalStyle));
			
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
	@RequestMapping("/ExcelFTUsaledeliverList")
	public String ExcelFTUsaledeliverList(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
	
		try {
			String sql = "SELECT fsuppliername,c.fname fcustomer,p.fproductid,sa.`fnumber`,p.`fname`,p.`fspec`,p.`funit`,sd.`famount`,CASE IFNULL(sd.fdanjia,'')   WHEN '' THEN IFNULL(p.fprice,'') ELSE IFNULL(sd.fdanjia,'') END fdanjia,sd.`fprice` fprices,sd.`fprice` fprice,sa.`fcreatetime`,sa.fclerk,sa.fdriver,sa.fprinttime,sa.fprintcount,sd.`fdescription`,sa.fcustomer fcustoermid,sa.fcustAddress,sa.fbusNumber,sa.fstate,sa.fphone,sa.ffax,sa.ftel,sa.fid,fsupplierid FROM `t_ftu_saledeliver` sa LEFT JOIN `t_ftu_saledeliverentry` sd ON sd.`fparentid`=sa.fid LEFT JOIN (select * from t_bd_custproduct order by fcreatetime desc) p ON sd.`fftuproductid` = p.`fid` left join t_bd_customer c on sa.fcustomer=c.fid where 1=1 and sa.fstate<>1"+ftuDao.QueryFilterByUser(request, null,"sa.fsupplierid");
			request.setAttribute("djsort", "sa.fnumber desc,sd.fsequence");
			request.setAttribute("nolimit", "true");
			switch (MySimpleToolsZ.getMySimpleToolsZ().judgeSearchType(request)) {
			case MySimpleToolsZ.TIME_SEARCH:
				sql += MySimpleToolsZ.getMySimpleToolsZ().buildDateBetweenSQLFragment(request);
				break;
			}
			
			ListResult result = MySimpleToolsZ.getMySimpleToolsZ().buildExcelListResult(sql, request, ftuDao);
			
			List<String> order = MySimpleToolsZ.gainDataIndexList(request);
			
			ExcelUtil.toexcel(response, result,order);
		} catch (DJException e) {
			response.getWriter().write(
					JsonUtil.result(false, e.getMessage(), "", ""));
		}

		return null;
	}
	@RequestMapping("getFTUstatementList")
	public void getFTUstatementList(HttpServletRequest request,
			HttpServletResponse response) throws IOException{
		try {
			String sql = "SELECT s.fname sname,sa.fsuppliername,COUNT(1) ftucount,MAX(sa.fcreatetime) maxfcreatetime,MIN(sa.fcreatetime) minfcreatetime,COUNT(DISTINCT sa.`fcustomer`) fcustomercount FROM `t_ftu_saledeliver` sa LEFT JOIN t_sys_user u ON sa.`fcreator` = u.fid LEFT JOIN `t_bd_customer` c ON sa.`fcustomer` = c.fid left join t_sys_supplier s on s.fid=sa.fsupplierid where sa.fstate != 1 GROUP BY sa.fsupplierid";
			ListResult result = ftuDao.QueryFilterList(sql, request);
			response.getWriter().write(JsonUtil.result(true, "",result));
		} catch (DJException e) {
			// TODO: handle exception
			response.getWriter().write(
					JsonUtil.result(false, e.getMessage(), "", ""));
		}
	}
	@RequestMapping("/ExcelFTUstatementsList")
	public String ExcelFTUstatementsList(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
	
		try {
			String sql = "SELECT s.fname sname,sa.fsuppliername,COUNT(1) ftucount,MAX(sa.fcreatetime) maxfcreatetime,MIN(sa.fcreatetime) minfcreatetime,COUNT(DISTINCT sa.`fcustomer`) fcustomercount FROM `t_ftu_saledeliver` sa LEFT JOIN t_sys_user u ON sa.`fcreator` = u.fid LEFT JOIN `t_bd_customer` c ON sa.`fcustomer` = c.fid LEFT JOIN t_sys_supplier s ON s.fid = sa.fsupplierid  where sa.fstate != 1 GROUP BY sa.fsupplierid";
//			request.setAttribute("djsort", "sa.fid,sa.fnumber desc,sd.fsequence");
			request.setAttribute("nolimit", "true");
			switch (MySimpleToolsZ.getMySimpleToolsZ().judgeSearchType(request)) {
			case MySimpleToolsZ.TIME_SEARCH:
				sql += MySimpleToolsZ.getMySimpleToolsZ().buildDateBetweenSQLFragment(request);
				break;
			}
			
			ListResult result = MySimpleToolsZ.getMySimpleToolsZ().buildExcelListResult(sql, request, ftuDao);
			
			List<String> order = MySimpleToolsZ.gainDataIndexList(request);
			
			ExcelUtil.toexcel(response, result,order);
		} catch (DJException e) {
			response.getWriter().write(
					JsonUtil.result(false, e.getMessage(), "", ""));
		}

		return null;
	}
	@RequestMapping("saveOrupdateFtuParameter")
	public void saveOrupdateFtuParameter(HttpServletRequest request,
			HttpServletResponse response) throws IOException{
		try {
			String userid = ((Useronline)request.getSession().getAttribute("Useronline")).getFuserid();
			List<FtuParameter> listP = (List<FtuParameter>)request.getAttribute("FtuParameter");
			String sql;
			List<HashMap<String,Object>> list;
			for(FtuParameter fp : listP){
				sql = "select fid from t_ftu_parameter where fuserid='"+userid+"' and falias='"+fp.getFalias()+"'";
				list = ftuDao.QueryBySql(sql);
				if(list.size()>0){//已经存了信息时，更新相关内容
					sql = String.format("update t_ftu_parameter set fieldtype=%s,fdecimals=%s,fedit=%s,fcomputationalformula='%s' where fid='%s'",fp.getFieldtype(),fp.getFdecimals(),fp.getFedit(),fp.getFcomputationalformula(),list.get(0).get("fid"));
					ftuDao.ExecBySql(sql);
				}else{
					fp.setFid(ftuDao.CreateUUid());
					fp.setFuserid(userid);
					fp.setFname(fp.getFalias());
					ftuDao.saveOrUpdate(fp);
					
				}
			}
			response.getWriter().write(
					JsonUtil.result(true, "保存成功！", "", ""));
		} catch (DJException e) {
			// TODO: handle exception
			response.getWriter().write(
					JsonUtil.result(false, e.getMessage(), "", ""));
		}
	}
	@RequestMapping("getFtuParameter")
	public void getFtuParameter(HttpServletRequest request,
			HttpServletResponse response) throws IOException{
		try {
			String userid = ((Useronline)request.getSession().getAttribute("Useronline")).getFuserid();
			String sql = "select fid,fsaledeliverentry,fieldtype,ifnull(fisprint,0) fisprint,ifnull(fdecimals,0) fdecimals,ifnull(fedit,0) fedit,ifnull(fcomputationalformula,'') fcomputationalformula,falias,fuserid,fname from t_ftu_parameter where fuserid='"+userid+"'";
			ListResult result = ftuDao.QueryFilterList(sql, request);
			response.getWriter().write(
					JsonUtil.result(true, "",result));
		} catch (DJException e) {
			// TODO: handle exception
			response.getWriter().write(
					JsonUtil.result(false, e.getMessage(), "", ""));
		}
	}
	@RequestMapping("getFtuPrintTemplate")
	public void getFtuPrintTemplate(HttpServletRequest request,
			HttpServletResponse response) throws IOException{
		try {
			String userid = ((Useronline)request.getSession().getAttribute("Useronline")).getFuserid();
			String key = request.getParameter("key");
			String sql = "select ftemplate from t_ftu_template where fuserid='"+userid+"'";
			List<HashMap<String,Object>> list = ftuDao.QueryBySql(sql);
//			String filePath;
			File file = new File(request.getServletContext().getRealPath("/"));//获取当前路径
			String fpath = 	file.getParent()+"/vmifile/printTemplate";//构造按当前日期保存文件的路径 yyyy-MM-dd
			if(list.size()>0){
				fpath =fpath+"//"+list.get(0).get("ftemplate").toString();
			}else{
				fpath = file.getParentFile().getParent()+"\\送货凭证模板"+key+"联.txt";//D:\\tomcat
				if(key==null){
					fpath = file.getParentFile().getParent()+"\\送货凭证模板3联.txt";//D:\\tomcat
				}
			}
			File files=new File(fpath);
			if(files.isFile() && files.exists()){ //判断文件是否存在  
				InputStreamReader read = new InputStreamReader(new FileInputStream(files),"GBK");//考虑到编码格式  
				BufferedReader bufferedReader = new BufferedReader(read);  
				String lineTxt = null;
				while((lineTxt = bufferedReader.readLine()) != null){  
					response.getWriter().write(lineTxt);
				}  
				read.close(); 
			}else{  
				throw new DJException("找不到指定的文件!");
			}  
	
		} catch (DJException e) {
			// TODO: handle exception
			response.getWriter().write(
					JsonUtil.result(false,e.getMessage(),"",""));
		}
	}
	@RequestMapping("saveOrupdateFtuParameters")
	public void saveOrupdateFtuParameters(HttpServletRequest request,
			HttpServletResponse response) throws IOException{
		try {
			String userid = ((Useronline)request.getSession().getAttribute("Useronline")).getFuserid();
			FtuParameter fp = (FtuParameter)request.getAttribute("FtuParameter");
			if(!StringUtils.isEmpty(fp)){
				String sql = "select fid from t_ftu_parameter where fuserid='"+userid+"' and falias='"+fp.getFname()+"'";
				List<HashMap<String,Object>> list = ftuDao.QueryBySql(sql);
				if(list.size()>0){
					sql = String.format("update t_ftu_parameter set falias='%s',fieldtype='%s',fdecimals='%s',fcomputationalformula='%s',fisprint='%s' where falias='%s' and fuserid='%s'",
							fp.getFalias(),fp.getFieldtype(),StringUtils.isEmpty(fp.getFdecimals())?0:fp.getFdecimals(),fp.getFcomputationalformula(),StringUtils.isEmpty(fp.getFisprint())?0:fp.getFisprint(),fp.getFname(),userid);
					ftuDao.ExecBySql(sql);
				}else{
					fp.setFuserid(userid);
					fp.setFid(ftuDao.CreateUUid());
					ftuDao.saveOrUpdate(fp);
				}
			}
			response.getWriter().write(
			JsonUtil.result(true, "保存成功","",""));
		} catch (DJException e) {
			// TODO: handle exception
			response.getWriter().write(
					JsonUtil.result(false,e.getMessage(),"",""));
		}
	}
	@RequestMapping("delFtuParameter")
	public void delFtuParameter(HttpServletRequest request,
			HttpServletResponse response) throws IOException{
		try {
			String userid = ((Useronline)request.getSession().getAttribute("Useronline")).getFuserid();
			String fname = request.getParameter("name");
			String sql = "delete from t_ftu_parameter where fuserid='"+userid+"' and falias='"+fname+"'";
			ftuDao.ExecBySql(sql);
			response.getWriter().write(
					JsonUtil.result(true, "删除成功","",""));
		} catch (DJException e) {
			// TODO: handle exception
			response.getWriter().write(
					JsonUtil.result(false,e.getMessage(),"",""));
		}
	}
	@RequestMapping("savePrintTemplate")
	public void savePrintTemplate(HttpServletRequest request,
			HttpServletResponse response) throws IOException{
		try {
			String userid = ((Useronline)request.getSession().getAttribute("Useronline")).getFuserid();
			String html = request.getParameter("html").replaceAll("ギ", "%").replaceAll("¥","&yen;");
			FtuTemplate ft = new FtuTemplate();
			
			File file = new File(request.getServletContext().getRealPath("/"));//获取当前路径
			String fpath = 	file.getParent()+"/vmifile/printTemplate";//构造按当前日期保存文件的路径 yyyy-MM-dd
			String fileName = ftuDao.CreateUUid()+".txt";//文件名
			
			String sql = "select ftemplate from t_ftu_template where fuserid='"+userid+"'";
			List<HashMap<String,Object>> list = ftuDao.QueryBySql(sql);
			if(list.size()>0){
				fileName = list.get(0).get("ftemplate").toString();
			}
			file = new File(fpath);
			if(!file.exists()){
				file.mkdirs();
			}
			File f = new File(file,fileName);
			if(!f.exists()){
				f.createNewFile();
				ft.setFid(ftuDao.CreateUUid());
				ft.setFuserid(userid);
				ft.setFtemplate(fileName);
				ftuDao.save(ft);
			}
			FileOutputStream txtfile = new FileOutputStream(f);
			PrintStream p = new PrintStream(txtfile);
			p.println(html);
			p.close();
			response.getWriter().write(
					JsonUtil.result(true, "保存成功","",""));
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			response.getWriter().write(
					JsonUtil.result(false,e.getMessage(),"",""));
		}
	}
	@RequestMapping("resetPrintTemplate")
	public void resetPrintTemplate(HttpServletRequest request,
			HttpServletResponse response) throws IOException{
		try {
			String userid = ((Useronline)request.getSession().getAttribute("Useronline")).getFuserid();
			String sql = "select ftemplate from t_ftu_template where fuserid='"+userid+"'";
			List<HashMap<String,Object>> list = ftuDao.QueryBySql(sql);
			if(list.size()>0){
				File file = new File(request.getServletContext().getRealPath("/"));//获取当前路径
				String fpath = 	file.getParent()+"/vmifile/printTemplate";//构造按当前日期保存文件的路径 yyyy-MM-dd
				File files = new File(fpath+"\\"+list.get(0).get("ftemplate"));
				if(files.exists()){
					files.delete();
				}
				sql = "delete from t_ftu_template  where fuserid='"+userid+"'";
				ftuDao.ExecBySql(sql);
			}
			sql = "delete from t_ftu_parameter where fuserid ='"+userid+"'";
			ftuDao.ExecBySql(sql);//还原模板是 删除以前的配置
			sql = "delete from t_sys_syscfg where fuser='" + userid
					+ "' and fkey='thePrintTP'";
			ftuDao.ExecBySql(sql);//删除模板配置2联 3联
			response.getWriter().write(
					JsonUtil.result(true, "重置成功","",""));
		} catch (Exception e) {
			// TODO: handle exception
			response.getWriter().write(
					JsonUtil.result(false,e.getMessage(),"",""));
		}
	}
	@RequestMapping("saveOrUpdateFtusaledeliver")
	public void saveOrUpdateFtusaledeliver(HttpServletRequest request,
			HttpServletResponse response) throws IOException{
		try {
			String sql;
			String userid = ((Useronline)request.getSession().getAttribute("Useronline")).getFuserid();
			List<FTUSaledeliverEntry> fse = (List<FTUSaledeliverEntry>)request.getAttribute("FTUSaledeliverEntry");
			FTUSaledeliver fs = (FTUSaledeliver)request.getAttribute("FTUSaledeliver");
	
			ftuDao.saveNewFTUSaleDeliver(fs,fse,userid);
			response.getWriter().write(JsonUtil.result(true, fs.getFid(),"" ,""));
		} catch (DJException e) {
			// TODO: handle exception
			response.getWriter().write(
					JsonUtil.result(false,e.getMessage(),"",""));
		}
	}
	@RequestMapping("getFtusaledeliver")
	public void getFtusaledeliver(HttpServletRequest request,
			HttpServletResponse response) throws IOException{
		try {
			String fid = request.getParameter("fid");
			String sql = "SELECT sa.fid,CASE sd.famount WHEN 0 THEN p.fprice ELSE IFNULL(sd.fdanjia,ROUND(sd.fprice / sd.famount, 3)) END fdanjia,sd.string1,sd.string2,sd.string3,sd.string4,sd.string5,sd.string6,sd.string7,sd.string8,sd.string9,sd.string10,sa.fsuppliername,sa.fcustAddress,c.fname cname,c.fid fcustomername,sa.fphone,sa.ffax,sa.ftel,sa.`fnumber`,p.`fname` fcusproductname,p.`fspec`,p.`funit`,sd.`famount`,sd.`fprice` fprice,SUBSTR(sa.fcreatetime,1,10) fcreatetime,sa.fclerk,sa.fdriver,sd.`fdescription` FROM `t_ftu_saledeliver` sa LEFT JOIN `t_ftu_saledeliverentry` sd ON sd.`fparentid`=sa.fid LEFT JOIN t_bd_custproduct p ON sd.`fftuproductid` = p.`fid` left join t_bd_customer c on sa.fcustomer=c.fid where 1=1 and sa.fstate<>1 and sa.fid in('"+fid+"')"+ftuDao.QueryFilterByUser(request, null,"sa.fsupplierid")+" order by sd.fsequence";
//			request.setAttribute("djsort", "sa.fnumber desc,sd.fsequence");
			List list = ftuDao.QueryBySql(sql);
			response.getWriter().write(JsonUtil.result(true, "","" ,list));
		} catch (DJException e) {
			// TODO: handle exception
			response.getWriter().write(
					JsonUtil.result(false,e.getMessage(),"",""));
		}
	}
	@RequestMapping("getFtusaledelivers")
	public void getFtusaledelivers(HttpServletRequest request,
			HttpServletResponse response) throws IOException{
		try {
			String fid = request.getParameter("fid");
			String sql = "SELECT sa.fid,CASE sd.famount WHEN 0 THEN p.fprice ELSE IFNULL(sd.fdanjia,ROUND(sd.fprice / sd.famount, 3)) END fdanjia,sd.string1,sd.string2,sd.string3,sd.string4,sd.string5,sd.string6,sd.string7,sd.string8,sd.string9,sd.string10,sa.fsuppliername,sa.fcustAddress,c.fname fcustomername,sa.fphone,sa.ffax,sa.ftel,sa.`fnumber`,p.`fname` fcusproductname,p.`fspec`,p.`funit`,sd.`famount`,sd.`fprice` fprice,SUBSTR(sa.fcreatetime,1,10) fcreatetime,sa.fclerk,sa.fdriver,sd.`fdescription` FROM `t_ftu_saledeliver` sa LEFT JOIN `t_ftu_saledeliverentry` sd ON sd.`fparentid`=sa.fid LEFT JOIN t_bd_custproduct p ON sd.`fftuproductid` = p.`fid` left join t_bd_customer c on sa.fcustomer=c.fid where 1=1 and sa.fstate<>1 and sa.fid in('"+fid+"')"+ftuDao.QueryFilterByUser(request, null,"sa.fsupplierid")+" order by sd.fsequence";
			List list = ftuDao.QueryBySql(sql);
			response.getWriter().write(JsonUtil.result(true, "","" ,list));
		} catch (DJException e) {
			// TODO: handle exception
			response.getWriter().write(
					JsonUtil.result(false,e.getMessage(),"",""));
		}
	}
	@RequestMapping("getPrintFTUList")
	public void getPrintFTUList(HttpServletRequest request,
			HttpServletResponse response) throws IOException{
		try {
			String fparentid = request.getParameter("fids");
			String size = request.getParameter("size");
			int psize = 9999;
			if(!StringUtils.isEmpty(size)){
				psize = Integer.valueOf(size);
			}
			HashMap<String,Object> paramsMap = new HashMap();
			String sql = "";
			String result = "";
			if(!StringUtils.isEmpty(fparentid)){
				String[] fids = fparentid.split(",");
				for(int i = 0;i<fids.length;i++){
					String sqls = "SELECT  sa.famount,sa.fprice,sa.fdescription,ifnull(sa.fdanjia,c.fprice) fdanjia,ifnull(sa.fcusproductname,c.fname) fcusproductname,ifnull(sa.fspec,c.fspec) fspec,ifnull(sa.funit,c.funit) funit,sa.string1,sa.string2,sa.string3,sa.string4,sa.string5,sa.string6,sa.string7,sa.string8,sa.string9,sa.string10 FROM  t_ftu_saledeliverentry sa  left join t_bd_custproduct c ON sa.`fftuproductid`=c.fid where sa.fparentid='"+fids[i]+"' order by sa.fsequence";
					List<HashMap<String,Object>> plist = ftuDao.QueryBySql(sqls);
					double fprices = 0;
					BigDecimal sum =BigDecimal.valueOf(0);
					for(HashMap<String,Object> p : plist){
						try {
							fprices += Double.valueOf(p.get("fprice")==null?"0":p.get("fprice").toString());//*Double.valueOf(p.get("famount").toString());
							sum = sum.add(BigDecimal.valueOf(Double.valueOf(p.get("famount")==null?"0":p.get("famount").toString())));
						} catch (Exception e) {
							// TODO: handle exception
						}
					}
					String chinesefprices = toRMB(fprices);
					DecimalFormat myformat = new DecimalFormat();
					myformat.applyPattern("##,###.00");
					String fsumprices = "0";
					if(fprices!=0){
						fsumprices = myformat.format(fprices);
					}
					String proresult = JsonUtil.result(true, "","",plist);
					
					String userid = ((Useronline)request.getSession().getAttribute("Useronline")).getFuserid();
//					sql = "select fcustomername from t_sys_user where fid='"+userid+"'";
//					List<HashMap<String,Object>> map = ftuDao.QueryBySql(sql);//获取用户信息里面的客户名称
					
					 sql = "SELECT c.fname fcustomername,s.fcustAddress,s.fid,s.fsuppliername,s.fsupplierid,s.fnumber,SUBSTR(s.fcreatetime,1,10) fcreatetime,s.fcreator,s.ffax,s.ftel,s.fdriver,s.fclerk,c.fname fcustomer,s.fphone FROM t_ftu_saledeliver s left join t_bd_customer c on c.fid=s.fcustomer where s.fid='"+fids[i]+"'";
					List<HashMap<String,Object>> salelist = ftuDao.QueryBySql(sql);
					if(plist.size()>psize){
						paramsMap = new HashMap();
						int d = (int) Math.ceil((double)plist.size()/psize);//取不小于它的整数
						int start = 0;
						int end = 0;
						for(int x= 0;x<d;x++){
							double fpricess = 0;
							int sums = 0;
							List<HashMap<String,Object>> pplist = ftuDao.QueryBySql(sqls+" limit "+start+","+psize);
							proresult = JsonUtil.result(true, "","",pplist);
							for(HashMap<String,Object> p : pplist){
								try {
									fpricess += Double.valueOf(p.get("fprice").toString());//*Double.valueOf(p.get("famount").toString());
									sums += Double.valueOf(p.get("famount").toString());
								} catch (Exception e) {
									// TODO: handle exception
								}
							}
							 chinesefprices = toRMB(fpricess);
							 fsumprices = "0";
							 if(fprices!=0){
								fsumprices = myformat.format(fpricess);
							}
							 paramsMap.put("psize", pplist.size());
							 paramsMap.put("fsumprices", fsumprices);
							 paramsMap.put("chinesefprices", chinesefprices);
							 paramsMap.put("sum", sums);
							 paramsMap.put("number", (String)salelist.get(0).get("fnumber")+"-"+(x+1));
							 paramsMap.put("plist", proresult.substring(proresult.indexOf("["),proresult.lastIndexOf("]")+1));
							result += List2Json(salelist,paramsMap);
							start += psize;
							if(x<d-1){
								result +=",";
							}
						}
						
					}else{
						paramsMap.put("plist", proresult.substring(proresult.indexOf("["),proresult.lastIndexOf("]")+1));
						paramsMap.put("psize", plist.size());
						paramsMap.put("fsumprices", fsumprices);
						paramsMap.put("chinesefprices", chinesefprices);
						paramsMap.put("sum" ,sum);
						paramsMap.put("number", salelist.get(0).get("fnumber"));
						result += List2Json(salelist,paramsMap);
					}
					if(i<fids.length-1){
						result +=",";
					}
					//更新打印时间和打印次数
					ftuDao.saveFTUprint(fids[i]);
				}
			}
			response.getWriter().write("{\"success\":" + true+ ",\"data\":[" + result + "]}");
		} catch (DJException e) {
			// TODO: handle exception
			response.getWriter().write(JsonUtil.result(false, "", "",e.getMessage()));
		}
	}
	@RequestMapping("saveAllFtuParams")
	public void saveAllFtuParams(HttpServletRequest request,HttpServletResponse response) throws IOException{
		try {
			String userid = ((Useronline)request.getSession().getAttribute("Useronline")).getFuserid();
			List<FtuParameter> fp = (List<FtuParameter>)request.getAttribute("FtuParameter");
			List list;
			String sql;
			for(FtuParameter p : fp){
				sql = "select 1 from t_ftu_parameter where fname ='"+p.getFname()+"' and fuserid='"+userid+"'";
				list = ftuDao.QueryBySql(sql);
				if(list.size()==0){
					p.setFid(ftuDao.CreateUUid());
					p.setFedit(1);
					p.setFisprint(1);
					p.setFalias(p.getFname());
					p.setFuserid(userid);
					p.setFdecimals(p.getFdecimals()==null?0:p.getFdecimals());
					ftuDao.saveOrUpdate(p);
				}
			}
			response.getWriter().write(JsonUtil.result(true, "","" ,""));	
		} catch (DJException e) {
			// TODO: handle exception
			response.getWriter().write(JsonUtil.result(false, "", "",e.getMessage()));
		}
	}
	@RequestMapping("getFTUunit")
	public void getFTUunit(HttpServletRequest request,HttpServletResponse response) throws IOException{
		try {
			String sql = "select sd.funit fname from t_ftu_saledeliver sa left join t_ftu_saledeliverentry sd on sa.fid=sd.fparentid where 1=1"+ftuDao.QueryFilterByUser(request, null, "sa.fsupplierid")+" GROUP BY sd.funit";
			List list = ftuDao.QueryBySql(sql);
			response.getWriter().write(JsonUtil.result(true, "","" ,list));	
		} catch (DJException e) {
			// TODO: handle exception
			response.getWriter().write(JsonUtil.result(false, "", "",e.getMessage()));
		}
	}
	@RequestMapping("selectProductsByCustomers")
	public String selectProductsByCustomers(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		try {
			String queryName = request.getParameter("fname");
			String fcustomer = request.getParameter("fcustomer");
//			if(!StringUtils.isEmpty(fcustomer)){
//				fcustomer = new String(fcustomer.getBytes("ISO-8859-1"),"UTF-8");
//			}
			String sql = "select CONCAT_WS(' / ',c.fname,c.fspec,''+c.fprice)  fviewname,c.fname fname,c.funit,c.fprice fdanjia,c.fspec,c.fid from t_bd_custproduct c left join t_pdt_productdef p on c.fproductid=p.fid left join t_bd_customer cu on c.fcustomerid=cu.fid where 1=1 and cu.fid='"+fcustomer+"'"+ftuDao.QueryFilterByUser(request,null,"p.fsupplierid");
			if(!StringUtils.isEmpty(queryName)){
				String str=new String(queryName.getBytes("ISO-8859-1"),"UTF-8");
				sql += String.format("and (c.fname like '%s' or c.fspec like '%s' )","%"+str+"%","%"+str+"%");
			}
//			request.setAttribute("nolimit", "true");
			ListResult result = ftuDao.QueryFilterList(sql, request);
			response.getWriter().write(JsonUtil.result(true,"", result));
		} catch (DJException e) {
			response.getWriter().write(JsonUtil.result(false, "", "",e.getMessage()));
		}
		return null;
	}
	@RequestMapping("getFTUCustAddress")
	public void getFTUCustAddress(HttpServletRequest request,
			HttpServletResponse response) throws IOException{
		try {
			String fcustomer = request.getParameter("fcustomer");
//			fcustomer = new String(fcustomer.getBytes("ISO-8859-1"),"UTF-8");
			String sql = "select a.fid,a.fname from t_bd_address a left join t_bd_custrelationadress cu on a.fid=cu.faddressid left join t_pdt_productreqallocationrules p on cu.fcustomerid=p.fcustomerid left join t_bd_customer c on p.fcustomerid = c.fid where c.fid='"+fcustomer+"'"+ftuDao.QueryFilterByUser(request,null,"p.fsupplierid");;
			List list = ftuDao.QueryBySql(sql);
			response.getWriter().write(JsonUtil.result(true,"","",list));
		} catch (DJException e) {
			// TODO: handle exception
			response.getWriter().write(JsonUtil.result(false,e.getMessage(),"",""));
		}
	}
	@RequestMapping("getNumberParams")
	public void getNumberParams(HttpServletRequest request,
			HttpServletResponse response) throws IOException{
		try {
			String userid = ((Useronline)request.getSession().getAttribute("Useronline")).getFuserid();
			String sql = "select fid,fieldtype,fdecimals,fedit,fcomputationalformula,falias,fuserid,fname,fisprint,fsaledeliverentry from t_ftu_parameter where fieldtype =1 and fuserid='"+userid+"'";
			List list = ftuDao.QueryBySql(sql);
			response.getWriter().write(JsonUtil.result(true,"","",list));
		} catch (DJException e) {
			// TODO: handle exception
			response.getWriter().write(JsonUtil.result(false,e.getMessage(),"",""));
		}
	}
	@RequestMapping("downWordByFTU.do")
	public void downWordByFTU(HttpServletRequest request,
			HttpServletResponse response) throws IOException{
		try {
			File file = new File(request.getServletContext().getRealPath("/"));//获取当前路径
			String fpath = 	file.getParentFile().getParent()+"/送货凭证模板操作文档说明.doc";//构造按当前日期保存文件的路径 yyyy-MM-dd

			InputStream in = null;
			try {
				in = new FileInputStream(fpath);
			} catch (FileNotFoundException e) {
				throw new DJException("此附件文件不存在，无法下载！");
			}
			response.setContentType("application/x-msdownload");
//			response.addHeader("Content-Disposition", "attachment; filename=送货凭证模板操作文档说明");//\"" + new String(p.getFname().getBytes("UTF-8"),"iso-8859-1") + "\"");
			response.setHeader("Content-Disposition", "attachment;fileName="+ new String("送货凭证模板操作文档说明".getBytes("UTF-8"),"iso-8859-1")+ ".doc");
			OutputStream out = response.getOutputStream();
			byte[] bytes = new byte[1024];
			int len = 0;
			while((len = in.read(bytes,0,1024))!=-1){
				out.write(bytes, 0, len);
			}
			out.flush();
			in.close();
		
		} catch (DJException e) {
			// TODO: handle exception
			response.getWriter().write(JsonUtil.result(false,e.getMessage(),"",""));
		}
	}
}
